package com.kevinpina.hibernatejpa;

import java.util.List;
import java.util.Optional;

import com.kevinpina.hibernatejpa.repository.db.JpaUtil;
import com.kevinpina.hibernatejpa.repository.entities.ClientEntity;
import com.kevinpina.hibernatejpa.services.ClientService;
import com.kevinpina.hibernatejpa.services.ClientServiceImpl;

import jakarta.persistence.EntityManager;

public class ClientCrud {

	public static void main(String[] args) {
		EntityManager em = JpaUtil.getEntitymanagerfactory();
		
		ClientService service = new ClientServiceImpl(em);
		
		System.out.println("List<ClientEntity> ---------------");
		List<ClientEntity> clientEntities = service.list();
		clientEntities.forEach(System.out::println);
		
		System.out.println("\nClientEntity ---------------");
		Optional<ClientEntity> client = service.findById(1L); // Won't execute the SELECT 'cause it is saved in JPA Context before
		client.ifPresent(System.out::println);
		
		System.out.println("\nSave ---------------");
		ClientEntity clientEntityNew = ClientEntity.builder().name("George").surname("Bizet").paymentType("paypal").build();
		service.save(clientEntityNew);
		
		client = service.findById(clientEntityNew.getId()); // Won't execute the SELECT 'cause it is saved in JPA Context before
		client.ifPresent(System.out::println);
		
		System.out.println("\nUpdate ---------------");
		service.findById(clientEntityNew.getId()).ifPresent(c -> {
			c.setPaymentType("wechat");
			service.save(c);
			service.list().forEach(System.out::println);
		});
		
		System.out.println("\nDelete ---------------");
		service.delete(clientEntityNew.getId());
		service.list().forEach(System.out::println);
		
		em.close();
	}

}
