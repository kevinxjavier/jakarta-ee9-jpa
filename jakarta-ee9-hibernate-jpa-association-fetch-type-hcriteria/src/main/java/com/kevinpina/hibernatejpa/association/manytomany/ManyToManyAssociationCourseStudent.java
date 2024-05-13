package com.kevinpina.hibernatejpa.association.manytomany;

import com.kevinpina.hibernatejpa.repository.db.JpaUtil;
import com.kevinpina.hibernatejpa.repository.entities.CourseEntity;
import com.kevinpina.hibernatejpa.repository.entities.StudentEntity;

import jakarta.persistence.EntityManager;

public class ManyToManyAssociationCourseStudent {

	public static void main(String[] args) {
		
		EntityManager em = JpaUtil.getEntitymanagerfactory();
		
		try {
			em.getTransaction().begin();
			
			StudentEntity studentEntity1 = StudentEntity.builder().name("anna").surname("boron").build();
			StudentEntity studentEntity2 = StudentEntity.builder().name("kevin").surname("pìña").build();
			StudentEntity studentEntity3 = StudentEntity.builder().name("javier").surname("calatrava").build();
			StudentEntity studentEntity4 = StudentEntity.builder().name("franz").surname("schubert").build();
			StudentEntity studentEntity5 = StudentEntity.builder().name("wolfgang").surname("mozart").build();
			
			CourseEntity courseEntity1 = CourseEntity.builder().professor("albert einstein").title("physical").build();
			CourseEntity courseEntity2 = CourseEntity.builder().professor("Werner Heisenberg").title("math").build();
			
			studentEntity1.getCourses().add(courseEntity1);
			studentEntity1.getCourses().add(courseEntity2);
			
			studentEntity2.getCourses().add(courseEntity1);
			
			studentEntity3.getCourses().add(courseEntity1);
			
			studentEntity4.getCourses().add(courseEntity1);
			studentEntity4.getCourses().add(courseEntity2);
			
			studentEntity5.getCourses().add(courseEntity2);
			
			em.persist(studentEntity1);
			em.persist(studentEntity2);
			em.persist(studentEntity3);
			em.persist(studentEntity4);
			em.persist(studentEntity5);
			
			em.getTransaction().commit();
			
			System.out.println(studentEntity1);
			System.out.println(studentEntity2);
			System.out.println(studentEntity3);
			System.out.println(studentEntity4);
			System.out.println(studentEntity5);
			
		}catch (Exception e) {
			em.getTransaction().rollback();
			e.printStackTrace();
		} finally {
			em.close();;
		}
	}
	
}
