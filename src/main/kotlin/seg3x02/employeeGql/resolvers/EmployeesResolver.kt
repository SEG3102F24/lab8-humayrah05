package seg3x02.employeeGql.resolvers

import org.springframework.stereotype.Controller
import org.springframework.data.mongodb.core.MongoOperations
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.MutationMapping
import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.graphql.data.method.annotation.SchemaMapping
import seg3x02.employeeGql.entity.Employee
import seg3x02.employeeGql.repository.EmployeesRepository
import seg3x02.employeeGql.resolvers.types.CreateEmployeeInput
import java.util.*

@Controller
class EmployeesResolver(private val employeesRepository: EmployeesRepository,
                        private val mongoOperations: MongoOperations
) {
    @QueryMapping
    fun employees(): List<Employee> {
        return employeesRepository.findAll()
    }

    @QueryMapping
    fun employeeById(@Argument id: String): Employee? {
        return employeeRepository.findById(id).orElse(null)
    }

     @MutationMapping
    fun addEmployee(
        @Argument name: String,
        @Argument dateOfBirth: String,
        @Argument city: String,
        @Argument salary: Float,
        @Argument gender: String?,
        @Argument email: String
    ): Employee {
        val newEmployee = Employee(name = name, dateOfBirth = dateOfBirth, city = city, salary = salary, gender = gender, email = email)
        return employeeRepository.save(newEmployee)
    }

    @MutationMapping
    fun updateEmployee(
        @Argument name: String,
        @Argument dateOfBirth: String,
        @Argument city: String,
        @Argument salary: Float,
        @Argument gender: String?,
        @Argument email: String
    ): Employee? {
        val employee = employeeRepository.findById(id).orElse(null) ?: return null
        if (name != null) employee.name = name
        if (dateOfBirth != null) employee.dateOfBirth = dateOfBirth
        if (city != null) employee.city = city
        if (salary != null) employee.salary = salary
        if (gender != null) employee.gender = gender
        if (email != null) employee.email = email
        return employeeRepository.save(employee)
    }

    @MutationMapping
    fun deleteEmployee(@Argument id: String): Employee? {
        val employee = employeeRepository.findById(id).orElse(null) ?: return null
        employeeRepository.delete(employee)
        return employee
    }
}

