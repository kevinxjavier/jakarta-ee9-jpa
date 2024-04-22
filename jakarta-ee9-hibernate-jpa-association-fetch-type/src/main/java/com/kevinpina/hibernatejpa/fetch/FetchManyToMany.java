package com.kevinpina.hibernatejpa.fetch;

import com.kevinpina.hibernatejpa.repository.db.JpaUtil;
import com.kevinpina.hibernatejpa.repository.entities.StudentEntity;
import jakarta.persistence.EntityManager;

import java.util.List;

public class FetchManyToMany {

    // Optimizing the queries JPQL with "LEFT JOIN".

    public static void main(String[] args) {
        EntityManager em = JpaUtil.getEntitymanagerfactory();

        List<StudentEntity> studentEntities = em.createQuery("SELECT s FROM StudentEntity s", StudentEntity.class)
                .getResultList();

        //System.out.println(studentEntities); // 1 Select executes if it is commented!
        // Hibernate: select studentent0_.id as id1_10_, studentent0_.name as name2_10_, studentent0_.surname as surname3_10_ from student studentent0_

        //System.out.println(studentEntities); // 1 Select at least! and 1 select per each student with courses executes if it is uncommented!
        // Hibernate: select studentent0_.id as id1_10_, studentent0_.name as name2_10_, studentent0_.surname as surname3_10_ from student studentent0_
        //Hibernate: select courses0_.student_id as student_1_12_0_, courses0_.course_id as course_i2_12_0_, courseenti1_.id as id1_5_1_, courseenti1_.professor as professo2_5_1_, courseenti1_.title as title3_5_1_ from tbl_student_course courses0_ inner join course courseenti1_ on courses0_.course_id=courseenti1_.id where courses0_.student_id=?
        //Hibernate: select courses0_.student_id as student_1_12_0_, courses0_.course_id as course_i2_12_0_, courseenti1_.id as id1_5_1_, courseenti1_.professor as professo2_5_1_, courseenti1_.title as title3_5_1_ from tbl_student_course courses0_ inner join course courseenti1_ on courses0_.course_id=courseenti1_.id where courses0_.student_id=?
        //Hibernate: select courses0_.student_id as student_1_12_0_, courses0_.course_id as course_i2_12_0_, courseenti1_.id as id1_5_1_, courseenti1_.professor as professo2_5_1_, courseenti1_.title as title3_5_1_ from tbl_student_course courses0_ inner join course courseenti1_ on courses0_.course_id=courseenti1_.id where courses0_.student_id=?
        //Hibernate: select courses0_.student_id as student_1_12_0_, courses0_.course_id as course_i2_12_0_, courseenti1_.id as id1_5_1_, courseenti1_.professor as professo2_5_1_, courseenti1_.title as title3_5_1_ from tbl_student_course courses0_ inner join course courseenti1_ on courses0_.course_id=courseenti1_.id where courses0_.student_id=?


        // INNER JOIN. Will bring only students with courses.
        // LEFT JOIN = LEFT OUTER JOIN. This will bring students event they have or haven't courses.
        // FETCH to populate the data in ClientEntity if we don't put it will appear the columns but without data
        // If we don't use DISTINCT will repeat the student id 1 twice because it has 2 courses
        studentEntities = em.createQuery("SELECT DISTINCT s FROM StudentEntity s LEFT JOIN FETCH s.courses", StudentEntity.class)
                .getResultList();
        System.out.println(studentEntities); // 2 Select always in this way.
        // Hibernate: select studentent0_.id as id1_10_, studentent0_.name as name2_10_, studentent0_.surname as surname3_10_ from student studentent0_
        //Hibernate: select studentent0_.id as id1_10_0_, courseenti2_.id as id1_5_1_, studentent0_.name as name2_10_0_, studentent0_.surname as surname3_10_0_, courseenti2_.professor as professo2_5_1_, courseenti2_.title as title3_5_1_, courses1_.student_id as student_1_12_0__, courses1_.course_id as course_i2_12_0__ from student studentent0_ left outer join tbl_student_course courses1_ on studentent0_.id=courses1_.student_id left outer join course courseenti2_ on courses1_.course_id=courseenti2_.id

        em.close();
    }

}
