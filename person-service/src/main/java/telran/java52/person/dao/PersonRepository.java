package telran.java52.person.dao;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Stream;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import telran.java52.person.dto.CityPopulationDto;
import telran.java52.person.model.Person;

public interface PersonRepository extends JpaRepository<Person, Integer> {
	
//	@Query(nativeQuery = true,
//			value = "SELECT * FROM person WHERE city = ?1")
//	Stream<Person> findPersonsByCity(String city);
//	
//	@Query(nativeQuery = true,
//			value = "SELECT * "
//					+ "FROM person "
//					+ "WHERE NOW() BETWEEN DATE_ADD(birth_date, INTERVAL ?1 YEAR) AND"
//					+ " DATE_ADD(DATE_ADD(birth_date, INTERVAL ?2 + 1 YEAR), INTERVAL -1 DAY) ")
//	Stream<Person> findPersonsByAges(int minAge, int maxAge);
	
	@Query("select p from Citizen p where p.name=?1")
	Stream<Person> findByNameIgnoreCase(String name);
	
	@Query("select p from Citizen p where p.address.city=:cityName")
	Stream<Person> findByAddressCity(@Param("cityName") String city);
	
	Stream<Person> findByBirthDateBetween(LocalDate birthDateFrom, LocalDate birthDateTo);
	
	@Query("select new telran.java52.person.dto.CityPopulationDto(p.address.city, count(p)) from Citizen p group by p.address.city order by count(p) desc")
	List<CityPopulationDto> getCitiesPopulation();
	
}
