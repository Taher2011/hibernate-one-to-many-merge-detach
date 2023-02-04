package com.techgen.client;

import java.util.Set;

import com.techgen.entity.Guide;
import com.techgen.entity.Student;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

public class Client {

	static EntityManagerFactory entityManagerFactory = null;

	public static void main(String[] args) {

		try {
			entityManagerFactory = Persistence.createEntityManagerFactory("student-guide");

			/* Updating detahced object using merge */
			EntityManager entityManager1 = entityManagerFactory.createEntityManager();
			EntityTransaction transaction1 = entityManager1.getTransaction();
			Guide guide1 = getGuide1(entityManager1, transaction1);
			updateDetachedGuideUsingMerge(entityManager1, transaction1, guide1);

			/* Updating persistent object without merge */
			EntityManager entityManager2 = entityManagerFactory.createEntityManager();
			EntityTransaction transaction2 = entityManager2.getTransaction();
			Guide guide2 = getGuide2(entityManager2, transaction2);
			updatePersistentGuide(entityManager2, transaction2, guide2);

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
		guide.getStudents().size();
		transaction.commit();
		entityManager.close();
		return guide;
	}

	private static void updateDetachedGuideUsingMerge(EntityManager entityManager, EntityTransaction transaction,
			Guide guide) {
		entityManager = entityManagerFactory.createEntityManager();
		transaction = entityManager.getTransaction();
		transaction.begin();
		Set<Student> students = guide.getStudents();
		Student student1 = null;
		Student student2 = null;
		for (Student nextStudent : students) {
			if (nextStudent.getId() == 1) {
				student1 = nextStudent;
			} else if (nextStudent.getId() == 2) {
				student2 = nextStudent;
			}
		}
		guide.setSalary(3000);
		student1.setName("Amyy Gill");
		student2.setName("Johnn Smith");
		entityManager.merge(guide);
		transaction.commit();
		entityManager.close();
	}

	private static Guide getGuide2(EntityManager entityManager, EntityTransaction transaction) {
		transaction.begin();
		Guide guide = entityManager.find(Guide.class, 2l);
		guide.getStudents().size();
		transaction.commit();
		return guide;
	}

	private static void updatePersistentGuide(EntityManager entityManager, EntityTransaction transaction, Guide guide) {
		transaction = entityManager.getTransaction();
		transaction.begin();
		Set<Student> students = guide.getStudents();
		Student student1 = null;
		Student student2 = null;
		for (Student nextStudent : students) {
			if (nextStudent.getId() == 1) {
				student1 = nextStudent;
			} else if (nextStudent.getId() == 2) {
				student2 = nextStudent;
			}
		}
		guide.setSalary(2900);
		student1.setName("Amy Gill");
		student2.setName("John Smith");
		transaction.commit();
		entityManager.close();
	}

}
