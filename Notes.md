# Notes

## Relations

### OneToOne
### OneToMany
### ManyToOne

* Employee - Department gibi ek bir kolon açarak tabloları ilişkilendirir. 

    #### Steps
    ##### * Entities
* Entity içinde Employee class açılır.
  * @Entity
  * @Table(name="employee")
  * Lomboks
  * Long id -@Id, @GeneratedValue
  * String name -@Column(name="name")
  * Department department @ManyToOne

* Entity içinde Departmaent class açılır.
    * @Entity
    * @Table(name="employee")
    * Lomboks
    * Long id -@Id, @GeneratedValue
    * String departmentName -@Column(name="department_name")
* Uygulama çalıştırılır. kolonlar oluşur.
* Tablolara ilgili veriler girilir.

   ##### * Dto
* DtoDepartment class oluşturulur.
  * @NoArgsConstructor
  * @AllArgsConstructor
  * @Data
  * private long id;
  * private String departmentName;
* DtoEmployee class oluşturulur.
  * private Long id;
  * private String name;
  * private DtoDepartment department;

   ##### * Repository 
* EmployeeRepository interface oluşturulur.
  * extends JpaRepository<Employee, Long>
  * @Repository anatasyonu eklenir.

   ##### * Service
* IEmployeeService interface oluşturulur.
  * public List<DtoEmployee> findAllEmployees();
* EmployeeServiceImpl class oluşturulur.
  * IEmployeeService implemente edilir.
  * @Service anatasyonu eklenir.
  * @Autowired 
  * private EmployeeRepository employeeRepository; 
  * method yazılır.

   ##### * Controller
* IEmployeeController interface oluşturulur.
  * public List<DtoEmployee> findAllEmployees(); eklenir
* EmployeeControllerImpl class oluşturulur.
  * @RestController
  * @RequestMapping("rest/api/employee"")
  * IEmployeeController implement edilir.
  * @AutoWired private IEmployeeService employeeService;
  * @GetMapping(path="/list")
  * method override edilir.
    * return employeeService.findAllEmployees()
* Uygulama çalıştırılır.
