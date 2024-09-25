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
