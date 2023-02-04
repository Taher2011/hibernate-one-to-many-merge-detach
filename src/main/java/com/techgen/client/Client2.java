package com.techgen.client;

import java.util.Set;

import com.techgen.entity.Guide;
import com.techgen.entity.Student;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

public class Client2 {

	static EntityManagerFactory entityManagerFactory = null;

	public static void main(String[] args) {

		try {
			entityManagerFactory = Persistence.createEntityManagerFactory("student-guide");

			/* Updating detahced object using merge */
			EntityManager entityManager1 = entityManagerFactory.createEntityManager();
			EntityTransaction transaction1 = entityManager1.getTransaction();
			Guide guide1 = getGuide1(entityManager1, transaction1);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (entityManagerFactory != null && entityManagerFactory.isOpen()) {
				entityManagerFactory.close();
			}
		}
	}

	private static Guide getGuide1(EntityManager entityManager, EntityTransaction transaction) {
		transaction.begin();
		Guide guide = entityManager.find(Guide.class, 2l);
		Set<Student> students = guide.getStudents();
	//	int s = students.size();
		Student student1 = null;
		for (Student nextStudent : students) {
			if (nextStudent.getId() == 1) {
				student1 = nextStudent;
			}
		}
		transaction.commit();
		entityManager.close();
		guide.setSalary(30004);
		student1.setName("Amyy Gills");
		EntityManager entityManager2 = entityManagerFactory.createEntityManager();
		EntityTransaction transaction2 = entityManager2.getTransaction();
		transaction2.begin();
		entityManager2.merge(guide);
		transaction2.commit();
		entityManager2.close();
		return guide;
	}

}
