package telran.java52.person.dao;

import java.time.LocalDate;
import java.util.stream.Stream;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

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
	
	Stream<Person> findByAddressCity(String city);
	
	Stream<Person> findByBirthDateBetween(LocalDate birthDateFrom, LocalDate birthDateTo);
	
	Stream<Person> findByName(String name);
}
