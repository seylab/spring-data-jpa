# Notes

## Relations

### OneToOne
### OneToMany
### ManyToOne

Tabloları ilişkilendirmek için, `Employee` ve `Department` gibi ek bir kolon açılır.

#### Steps

##### 1. **Entity Oluşturma**

###### **Employee Entity:**

```java
@Entity
@Table(name = "employee")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    
    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;
}
```
* **Department Entity:**

```java
@Entity
@Table(name = "department")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String departmentName;
}
```
##### 2. **DTO Oluşturma:**
* **DtoDepartment:**
```java
@NoArgsConstructor
@AllArgsConstructor
@Data
public class DtoDepartment {
    private Long id;
    private String departmentName;
}
```
* **DtoEmployee:**
```java
@NoArgsConstructor
@AllArgsConstructor
@Data
public class DtoEmployee {
    private Long id;
    private String name;
    private DtoDepartment department;
}
```

##### 3. **Repository Oluşturma:**
* **EmployeeRepository:**
```java
@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
}
```

##### 4. **Service Katmanı:**
* **IEmployeeService:**
```java
public interface IEmployeeService {
    List<DtoEmployee> findAllEmployees();
}
```

* **EmployeeServiceImpl:**
```java
@Service
public class EmployeeServiceImpl implements IEmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public List<DtoEmployee> findAllEmployees() {
        // İş mantığı burada yer alır
        // Employee -> DtoEmployee dönüşümü ve repository'den verilerin çekilmesi
    }
}
```
##### 5. **Controller Katmanı:**
* **IEmployeeController:**

```java
public interface IEmployeeController {
    List<DtoEmployee> findAllEmployees();
}
```
* **EmployeeControllerImpl:**
```java
@RestController
@RequestMapping("/rest/api/employee")
public class EmployeeControllerImpl implements IEmployeeController {

    @Autowired
    private IEmployeeService employeeService;

    @GetMapping(path = "/list")
    public List<DtoEmployee> findAllEmployees() {
        return employeeService.findAllEmployees();
    }
}

```
Uygulama çalıştırılır.
##### Açıklama:
- **Entity Oluşturma**: `Employee` ve `Department` varlıkları (entities) tanımlanıyor.
- **DTO Oluşturma**: Veritabanından gelen verileri dış dünyaya taşımak için DTO sınıfları oluşturuluyor.
- **Repository**: `JpaRepository` ile veritabanı işlemleri yönetiliyor.
- **Service Katmanı**: İş mantığını içeren sınıf oluşturuluyor.
- **Controller Katmanı**: API isteklerinin yönetildiği sınıf yazılıyor.

### ManyToMany

Tabloları ilişkilendirmek için `Student` ve `Course` arasında `ManyToMany` ilişkisi kurulur.

#### Steps

##### 1. **Entity Oluşturma**

* **Student Entity:**

```java
@Entity
@Table(name = "student")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @ManyToMany
    @JoinTable(
        name = "student_course",
        joinColumns = @JoinColumn(name = "student_id"),
        inverseJoinColumns = @JoinColumn(name = "course_id")
    )
    private List<Course> courses;
}
```
* **Course  Entity:** 
```java
@Entity
@Table(name = "course")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @ManyToMany(mappedBy = "courses")
    private List<Student> students;
}
```
##### 2. **DTO Oluşturma:**
* **DtoCourse:**
```java
@NoArgsConstructor
@AllArgsConstructor
@Data
public class DtoCourse {
    private long id;
    private String name;

    @JsonInclude(JsonInclude.Include.NON_EMPTY) // Boş ve null olan dersleri atla
    private List<DtoStudent> students = new ArrayList<>();
}

```
* **DtoStudent**
```java
@NoArgsConstructor
@AllArgsConstructor
@Data
public class DtoStudent {
    private Integer id;
    private String name;

    @JsonInclude(JsonInclude.Include.NON_EMPTY) // Boş ve null olan öğrencileri atla
    private List<DtoCourse> courses = new ArrayList<>();
}
```

##### 3. **Repository Oluşturma:**
* **StudentRepository:**
```java
@Repository
public interface StudentRepository extends JpaRepository<Student, Integer> {
}
```
* **CourseRepository:**
```java
@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
}
```
##### 4. **Service Katmanı:**
* **IStudentService:**
```java
public interface IStudentService {
    public DtoStudent getStudentById(Integer id);
}
```
* **StudentServiceImpl:**
```java
@Service
public class StudentServiceImpl implements IStudentService {

    @Autowired
    private StudentRepository studentRepository;

    @Override
    public DtoStudent getStudentById(Integer id); {
        DtoStudent dtoStudent = new DtoStudent();
        Optional<Student> optional = studentRepository.findStudentById(id);
        if (optional.isEmpty()) {
            return null;
        }
        Student dbStudent = optional.get();
        BeanUtils.copyProperties(dbStudent, dtoStudent);

        if (dbStudent.getCourses()!=null && !dbStudent.getCourses().isEmpty()) {
            for (Course course : dbStudent.getCourses()) {
                DtoCourse dtoCourse = new DtoCourse();
                BeanUtils.copyProperties(course, dtoCourse);
                dtoStudent.getCourses().add(dtoCourse);
            }
        }
        return dtoStudent;
    }
}
```
* **ICourseService:**
```java
public interface ICourseService {
    List<DtoCourse> getAllCourses();
    DtoCourse getCourseById(Long id);
}
```
* **CourseServiceImpl:**
```java
@Service
public class CourseServiceImpl implements ICourseService {

    @Autowired
    private CourseRepository courseRepository;

    @Override
    public DtoCourse getCourseById(Long id) {
        DtoCourse dtoCourse = new DtoCourse();
        Optional<Course> optional = courseRepository.findById(id);
        if (optional.isEmpty()) {
            return null;
        }
        Course dbCourse = optional.get();
        BeanUtils.copyProperties(dbCourse, dtoCourse);

        if (dbCourse.getStudents() != null && !dbCourse.getStudents().isEmpty()) {
            for (Student student : dbCourse.getStudents()) {
                DtoStudent dtoStudent = new DtoStudent();
                BeanUtils.copyProperties(student, dtoStudent);
                dtoCourse.getStudents().add(dtoStudent);
            }
        }
        return dtoCourse;
    }
}
```

##### 5. **Controller Katmanı:**
* **IStudentController:**
```java
public interface IStudentController {
    DtoStudent getStudentById(Integer id);
}
```
* **StudentControllerImpl:**
```java
@RestController
@RequestMapping("/rest/api/student")
public class StudentControllerImpl implements IStudentController {

    @Autowired
    private IStudentService studentService;

    @GetMapping(path = "list/{id}")
    @Override
    public DtoStudent getStudentById(@PathVariable(name = "id") Integer id) {
        return studentService.getStudentById(id);
    }
}
```
* **ICourseController:**
```java
public interface ICourseController {
    List<DtoCourse> getAllCourses();
}
```
* **CourseControllerImpl:**
```java
@RestController
@RequestMapping("/rest/api/course")
public class CourseControllerImpl implements ICourseController {

    @Autowired
    private ICourseService courseService;

    @GetMapping(path = "/list")
    public List<DtoCourse> findAllCourses() {
        return courseService.getAllCourses();
    }
}
```
## application.properties
Spring Boot uygulamalarında yapılandırma ayarları genellikle `application.properties` dosyasına yazılır. 
Bu dosya, uygulamanın çeşitli bileşenlerine değer atamak için kullanılır. 

Örnek olarak:

```properties
# Lesson_38 'application.properties'.md
app.name=MyApplication
app.version=1.0.0
app.author=John Doe
```
Yukarıdaki dosyada, ``app.name``, ``app.version``, ve ``app.author`` gibi anahtar-değer çiftleri tanımlanmıştır. Bu değerler, Spring içerisinde farklı bileşenlerde kullanılabilir.

### 1. ``@PropertySource`` Anotasyonu

``@PropertySource``, belirtilen bir dosyadan özellik (property) yüklemek için kullanılır. 
Genellikle bir ``@Configuration`` sınıfında yer alır ve ``application.properties`` 
dışında farklı bir özellik dosyasını eklemek için kullanılır.

Örnek kullanım:
```java
@Configuration
@PropertySource("classpath:custom.properties")
public class AppConfig {
    // Özellik dosyasından değerleri kullanmak için kullanılacak sınıf
}
```
Bu anotasyon ile ``custom.properties`` adlı dosyadaki değerler uygulamaya yüklenir ve daha sonra ``@Value`` ile erişilebilir hale gelir.

Not: Eğer Spring Boot kullanıyorsanız, varsayılan olarak ``application.properties`` dosyasını 
eklemenize gerek yoktur. ``@PropertySource`` sadece ek yapılandırma dosyaları için kullanılır.

### 2. ``@Value`` Anotasyonu

``@Value``, ``application.properties`` veya diğer özellik dosyalarındaki anahtar-değer çiftlerini doğrudan sınıf 
alanlarına atamak için kullanılır. Bu, sınıf içindeki herhangi bir alana dışarıdan gelen yapılandırma değerini 
enjekte etmenin basit bir yoludur.

Örnek kullanım:
````java
@Component
public class AppProperties {

    @Value("${app.name}")
    private String appName;

    @Value("${app.version}")
    private String appVersion;

    @Value("${app.author}")
    private String appAuthor;

    public void printProperties() {
        System.out.println("App Name: " + appName);
        System.out.println("App Version: " + appVersion);
        System.out.println("App Author: " + appAuthor);
    }
}
````
* **Açıklama:**

``@Value("${app.name}")``: ``application.properties`` dosyasındaki app.name değerini alır ve appName değişkenine atar. 

``@Value("${app.version}")``: ``application.properties`` dosyasındaki app.version değerini alır ve appVersion değişkenine atar.

``@Value("${app.author}")``: ``application.properties`` dosyasındaki app.author değerini alır ve appAuthor değişkenine atar.
Uygulama çalıştırıldığında, printProperties metodu bu değerleri konsola yazdırır.

### Örnek ``application.properties`` ve Kullanım:

#### ``application.properties``
````properties
app.name=ExampleApp
app.version=2.1.0
app.author=Jane Doe
````

#### Java Class

````java
@Component
public class AppConfig {

    @Value("${app.name}")
    private String appName;

    @Value("${app.version}")
    private String appVersion;

    @Value("${app.author}")
    private String appAuthor;

    public void displayConfig() {
        System.out.println("Application Name: " + appName);
        System.out.println("Version: " + appVersion);
        System.out.println("Author: " + appAuthor);
    }
}
````

#### Output
```yaml
Application Name: ExampleApp
Version: 2.1.0
Author: Jane Doe
```

### 3. Varsayılan Değer Atama
Eğer bir özellik yapılandırma dosyasında bulunmuyorsa, ``@Value`` anotasyonu ile varsayılan bir değer atanabilir. 
Bu durumda, o anahtar yapılandırma dosyasında tanımlı değilse bile varsayılan değer kullanılacaktır.

Örnek:
```java
@Value("${app.description:Default description}")
private String appDescription;
```
Eğer app.description değeri ``application.properties`` dosyasında yoksa, Default description değeri atanır.

### Özet:
``application.properties`` dosyası, Spring Boot uygulamalarında yapılandırma ayarları için kullanılır.
``@PropertySource``, dış özellik dosyalarını yüklemek için kullanılır.
``@Value``, özellik dosyalarından alınan değerleri sınıf alanlarına enjekte eder.
