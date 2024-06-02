package telran.java52.person.service;

import java.time.LocalDate;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import telran.java52.person.dao.PersonRepository;
import telran.java52.person.dto.AddressDto;
import telran.java52.person.dto.PersonDto;
import telran.java52.person.dto.exception.PersonNotFoundException;
import telran.java52.person.model.Address;
import telran.java52.person.model.Person;

@Service
@RequiredArgsConstructor
public class PersonServiceImpl implements PersonService {

	final PersonRepository personRepository;
	final ModelMapper modelMapper;
	
	@Transactional
	@Override
	public Boolean addPerson(PersonDto personDto) {
		if (personRepository.existsById(personDto.getId())) {
			return false;
		}
		personRepository.save(modelMapper.map(personDto, Person.class));
		return true;
	}

	@Override
	public PersonDto findPersonById(Integer id) {
		Person person = personRepository.findById(id).orElseThrow(PersonNotFoundException::new);
		return modelMapper.map(person, PersonDto.class);
	}
	
	@Override
	public PersonDto removePerson(Integer id) {
		Person person = personRepository.findById(id).orElseThrow(PersonNotFoundException::new);
		personRepository.delete(person);
		return modelMapper.map(person, PersonDto.class);
	}

	@Override
	public PersonDto updateName(Integer id, String name) {
		Person person = personRepository.findById(id).orElseThrow(PersonNotFoundException::new);
		person.setName(name);
		personRepository.save(person);
		return modelMapper.map(person, PersonDto.class);
	}
	
	@Override
	public PersonDto updateAddress(Integer id, AddressDto addressDto) {
		Person person = personRepository.findById(id).orElseThrow(PersonNotFoundException::new);
		
		String city = addressDto.getCity() != null ? addressDto.getCity() : person.getAddress().getCity();
		String street = addressDto.getStreet() != null ? addressDto.getStreet() : person.getAddress().getStreet();
		Integer building = addressDto.getBuilding() != null ? addressDto.getBuilding() : person.getAddress().getBuilding();
		
		Address address = new Address(city, street, building);
		person.setAddress(address);
		personRepository.save(person);
		return modelMapper.map(person, PersonDto.class);
	}
	
	@Transactional(readOnly = true)
	@Override
	public Iterable<PersonDto> findPersonsByName(String name) {
		return personRepository.findByName(name)
					.map(s -> modelMapper.map(s, PersonDto.class))
					.toList();
	}
	
	@Transactional(readOnly = true)
	@Override
	public Iterable<PersonDto> findPersonsByAges(int minAge, int maxAge) {
		
		LocalDate birthDateFrom = LocalDate.now().minusYears(maxAge + 1);
		LocalDate birthDateTo = LocalDate.now().minusYears(minAge - 1);
		
		return personRepository.findByBirthDateBetween(birthDateFrom, birthDateTo)
				.map(s -> modelMapper.map(s, PersonDto.class))
				.toList();
		
//		return personRepository.findPersonsByAges(minAge, maxAge)
//					.map(s -> modelMapper.map(s, PersonDto.class))
//					.toList();
	}
	
	@Transactional(readOnly = true)
	@Override
	public Iterable<PersonDto> findPersonsByCity(String city) {
		return personRepository.findByAddressCity(city)
				.map(s -> modelMapper.map(s, PersonDto.class))
				.toList();
		
//		return personRepository.findPersonsByCity(city)
//					.map(s -> modelMapper.map(s, PersonDto.class))
//					.toList();
	}

}
