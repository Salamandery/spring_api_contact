package com.atomiccodes.contact.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.atomiccodes.contact.model.Contact;
import com.atomiccodes.contact.repository.ContactRepository;

@RestController
@RequestMapping("/contacts")
public class ContactController {

	@Autowired
	private ContactRepository contactRepository;
	
	@GetMapping
	public List<Contact> findAll() {
		return contactRepository.findAll();
	}
	
	@PostMapping
	public ResponseEntity<Contact> addContact(@RequestBody Contact contact) {
		List<Contact> exists = contactRepository.findByEmail(contact.getEmail());

		if (!exists.isEmpty()) {
			return ResponseEntity.notFound().build();
		}

		return ResponseEntity.ok(contactRepository.save(contact));
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Contact> putContact(@PathVariable("id") Long id, @RequestBody Contact contact) {
		Optional<Contact> exists = contactRepository.findById(id);

		if (exists.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		
		BeanUtils.copyProperties(contact, exists.get(), "id");
		
		Contact contactUpdated = contactRepository.save(exists.get());		
		
		return ResponseEntity.ok(contactUpdated);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Contact> deleteContact(@PathVariable("id")  Long id) {
		Optional<Contact> contact = contactRepository.findById(id);
		
		if (contact.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		
		contactRepository.delete(contact.get());
		
		return ResponseEntity.noContent().build();
	}
}
