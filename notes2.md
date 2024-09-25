# Notes

## Relations

### OneToOne
### OneToMany
### ManyToOne

Tabloları ilişkilendirmek için, `Employee` ve `Department` gibi ek bir kolon açılır.

### Steps

#### 1. **Entity Oluşturma**

##### **Employee Entity:**

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
#### 2. **DTO Oluşturma:**
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

#### 3. **Repository Oluşturma:**
* **EmployeeRepository:**
```java
@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
}
```

#### 4. **Service Katmanı:**
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
#### 5. **Controller Katmanı:**
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
### Açıklama:
- **Entity Oluşturma**: `Employee` ve `Department` varlıkları (entities) tanımlanıyor.
- **DTO Oluşturma**: Veritabanından gelen verileri dış dünyaya taşımak için DTO sınıfları oluşturuluyor.
- **Repository**: `JpaRepository` ile veritabanı işlemleri yönetiliyor.
- **Service Katmanı**: İş mantığını içeren sınıf oluşturuluyor.
- **Controller Katmanı**: API isteklerinin yönetildiği sınıf yazılıyor.