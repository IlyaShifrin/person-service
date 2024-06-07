package telran.java52.person.dao;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Stream;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import telran.java52.person.dto.CityPopulationDto;
import telran.java52.person.model.Child;
import telran.java52.person.model.Employee;
import telran.java52.person.model.Person;

public interface PersonRepository extends JpaRepository<Person, Integer> {
	
	Stream<Person> findByNameIgnoreCase(String name);
	
	Stream<Person> findByAddressCity(@Param("cityName") String city);
	
	Stream<Person> findByBirthDateBetween(LocalDate birthDateFrom, LocalDate birthDateTo);
	
	Stream<Child> findByKindergartenIsNotNull();
	
	Stream<Employee> findBySalaryBetween(int minSalary, int maxSalary);
	
	@Query("select new telran.java52.person.dto.CityPopulationDto(p.address.city, count(p)) from Person p group by p.address.city order by count(p) desc")
	List<CityPopulationDto> getCitiesPopulation();
	
}
