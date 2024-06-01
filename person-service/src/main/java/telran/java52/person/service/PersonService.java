package telran.java52.person.service;

import telran.java52.person.dto.AddressDto;
import telran.java52.person.dto.PersonDto;

public interface PersonService {
	
	Boolean addPerson(PersonDto personDto);
	
	PersonDto findPersonById(Integer id);
	
	PersonDto removePerson(Integer id);
	
	PersonDto updateName(Integer id, String name);
	
	PersonDto updateAddress(Integer id, AddressDto addressDto);
	
	Iterable<PersonDto> findPersonsByName(String name);
	
	Iterable<PersonDto> findPersonsByAges(int minAge, int maxAge);
	
	Iterable<PersonDto> findPersonsByCity(String city);
	
}
