/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DepartmentEmployee;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
/**
 *
 * @author 64050030 Kitipum Nornua
 */
public class Main {
    public static void main(String[] args){
        //Add to database
//        Department dep = new Department();
//        dep.setName("IT");
//        persist(dep);
//        
//        Department dep2 = new Department();
//        dep2.setName("HR");
//        persist(dep2);
//        
//        Employee emp = new Employee();
//        emp.setName("John");
//        emp.setJob("Network Admin");
//        emp.setSalary(56789);
//        insertEmployee(findDepById(1), emp);
//        
//        Employee emp2 = new Employee();
//        emp2.setName("Marry");
//        emp2.setJob("HR Manager");
//        emp2.setSalary(46789);
//        insertEmployee(findDepById(2), emp2);
//        
//        Employee emp3 = new Employee();
//        emp3.setName("Henry");
//        emp3.setJob("Programmer");
//        emp3.setSalary(67890);
//        insertEmployee(findDepById(1), emp3);
//        
//        Employee emp4 = new Employee();
//        emp4.setName("Clark");
//        emp4.setJob("HR recruiter");
//        emp4.setSalary(36789);
//        insertEmployee(findDepById(2), emp4);

        //Retreive all from database
        printAllEmployeeById(findAllEmployee());
        printAllEmployeeByDep(findAllDepartment());
        
    }
    
    public static void persist(Object object){
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("DepartmentEmployeePU");
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        
        try{
            em.persist(object);
            em.getTransaction().commit();
        }catch(Exception e){
            e.printStackTrace();
            em.getTransaction().rollback();
        }finally{
            em.close();
        }
    }
    
    public static Department findDepById(int id){
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("DepartmentEmployeePU");
        EntityManager em = emf.createEntityManager();
        Department dep = em.find(Department.class, id);
        em.close();
        return dep;
    }
    
    public static void insertEmployee(Department dep, Employee emp){
        emp.setDepartmentid(dep);
        persist(emp);
    }
    
    public static Employee findEmployeeById(Integer id){
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("DepartmentEmployeePU");
        EntityManager em = emf.createEntityManager();
        Employee emp = em.find(Employee.class, id);
        em.close();
        return emp;
    }
    
    public static List<Employee> findAllEmployee(){
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("DepartmentEmployeePU");
        EntityManager em = emf.createEntityManager();
        List<Employee> empList = (List<Employee>)em.createNamedQuery("Employee.findAll").getResultList();
        em.close();
        return empList;
    }
    
    public static void printAllEmployeeById(List<Employee> employeeList){
        System.out.println("All employee (by ID)");
        System.out.println("-----------------------");
        
        for(Employee emp: employeeList){
            System.out.println("ID: " + emp.getEmployeeid());
            System.out.println("Name: " + emp.getName());
            System.out.println("Job: " + emp.getJob());
            System.out.println("Salary: " + emp.getSalary());
            System.out.println("Department: " + emp.getDepartmentid().getName());
            System.out.println("-----------------------");
        }
    }
    
    
    
    public static List<Department> findAllDepartment(){
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("DepartmentEmployeePU");
        EntityManager em = emf.createEntityManager();
        List<Department> depList = (List<Department>)em.createNamedQuery("Department.findAll").getResultList();
        em.close();
        return depList;
    }
    
    public static void printAllEmployeeByDep(List<Department> departmentList){
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("DepartmentEmployeePU");
        EntityManager em = emf.createEntityManager();
        System.out.println("All employee (by Department)");
        System.out.println("-----------------------");
        
        for(Department dep: departmentList){
            System.out.println("Department ID: " + dep.getDepartmentid() + " Department Name: " + dep.getName());
            System.out.println("-----------------------");
            
            TypedQuery<Employee> query = em.createQuery(
            "SELECT e FROM Employee e WHERE e.departmentid.departmentid = :departmentId", Employee.class);
            query.setParameter("departmentId", dep.getDepartmentid());
            List<Employee> empList = query.getResultList();
            printAllEmployee(empList);
        }
    }
    
    public static void printAllEmployee(List<Employee> employeeList){
        for(Employee emp: employeeList){
            System.out.println("ID: " + emp.getEmployeeid());
            System.out.println("Name: " + emp.getName());
            System.out.println("Job: " + emp.getJob());
            System.out.println("Salary: " + emp.getSalary());
            System.out.println("-----------------------");
        }
    }
    
    
}
