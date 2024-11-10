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
        return employeesRepository.findById(id).orElse(null)
    }

     @MutationMapping
    fun addEmployee(@Argument("createEmployeeInput") input: CreateEmployeeInput) : Employee{
        if (input.name != null) {
        val employee = Employee(input.name, input.dateOfBirth, input.city, input.salary, input.gender, input.email)
        employee.id = UUID.randomUUID().toString()
        employeesRepository.save(employee)
        return employee
        } else {
            throw Exception("Invalid input")
        }
    }
    

    @MutationMapping
    fun updateEmployee(
        @Argument id: String,
        @Argument name: String,
        @Argument dateOfBirth: String?,
        @Argument city: String?,
        @Argument salary: Float?,
        @Argument gender: String?,
        @Argument email: String?
    ): Employee? {
        val employee = employeesRepository.findById(id).orElse(null) ?: return null
        if (dateOfBirth != null) employee.dateOfBirth = dateOfBirth
        if (city != null) employee.city = city
        if (salary != null) employee.salary = salary
        if (gender != null) employee.gender = gender
        if (email != null) employee.email = email
        return employeesRepository.save(employee)
    }

    @MutationMapping
    fun deleteEmployee(@Argument id: String): Employee? {
        val employee = employeesRepository.findById(id).orElse(null) ?: return null
        employeesRepository.delete(employee)
        return employee
    }
}

