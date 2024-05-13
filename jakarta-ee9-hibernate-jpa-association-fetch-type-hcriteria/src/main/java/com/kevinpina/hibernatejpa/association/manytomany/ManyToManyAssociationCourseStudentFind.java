package com.kevinpina.hibernatejpa.association.manytomany;

import com.kevinpina.hibernatejpa.repository.db.JpaUtil;
import com.kevinpina.hibernatejpa.repository.entities.CourseEntity;
import com.kevinpina.hibernatejpa.repository.entities.StudentEntity;

import jakarta.persistence.EntityManager;

public class ManyToManyAssociationCourseStudentFind {

	public static void main(String[] args) {

		EntityManager em = JpaUtil.getEntitymanagerfactory();
		
		ManyToManyAssociationCourseStudent.main(new String[]{});
		
		try {
			em.getTransaction().begin();
			
			StudentEntity studentEntity1 = em.find(StudentEntity.class, 3L);
			StudentEntity studentEntity2 = em.find(StudentEntity.class, 5L);
			
			//CourseEntity courseEntity1 = CourseEntity.builder().professor("salvador dalí").title("painter").build();
			//CourseEntity courseEntity2 = CourseEntity.builder().professor("julio cesar").title("military sciences").build();

			//studentEntity1.getCourses().add(courseEntity1);

			//studentEntity2.getCourses().add(courseEntity1);
			//studentEntity2.getCourses().add(courseEntity2);

			CourseEntity courseEntity1 = em.find(CourseEntity.class, 2L);
			CourseEntity courseEntity2 = em.find(CourseEntity.class, 1L);
			
			studentEntity1.getCourses().add(courseEntity1);
			
			studentEntity2.getCourses().add(courseEntity2);
			
			// No necessary the em.persist() because studentEntity1 and studentEntity2 is already in the context due the em.find().
			
			em.getTransaction().commit();
			
			System.out.println(studentEntity1);
			System.out.println(studentEntity2);

			// Deleting only the relationship between Student and Course not the Course
			em.getTransaction().begin();

			CourseEntity courseEntityDelete1 = em.find(CourseEntity.class, 2L);

			studentEntity1.getCourses().remove(courseEntityDelete1);

			em.getTransaction().commit();

			System.out.println(courseEntityDelete1);
			System.out.println(studentEntity1);

		} catch (Exception e) {
			em.getTransaction().rollback();
			e.printStackTrace();
		} finally {
			em.close();
		}

	}

}
