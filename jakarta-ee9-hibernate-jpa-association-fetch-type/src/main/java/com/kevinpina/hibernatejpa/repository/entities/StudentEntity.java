package com.kevinpina.hibernatejpa.repository.entities;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "student")
public class StudentEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String name;
	private String surname;
	
	// We don't use CascadeType.REMOVE because will remove the Course assign to this student, so it leaves the other students orphans with this course.
	// Will create the same as lines 52 but personalized.
	// joinColumns: main foreign key, will be student_id.
	// inverseJoinColumns: secondary foreign key, will be course_id.
	// uniqueConstraints: defines the columns that have constraints. In this case the student_id and course_id.
	@ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}) // Using Cascade means that this class is the principal in the relationship like the mappedBy.
	@JoinTable(name = "tbl_student_course", joinColumns = @JoinColumn(name = "student_id"), inverseJoinColumns = @JoinColumn(name = "course_id"), uniqueConstraints = @UniqueConstraint(columnNames = {"student_id", "course_id"})) // @See ClientEntity.addressEntities
	// create table tbl_student_course (student_id bigint not null, course_id bigint not null)
	// alter table tbl_student_course add constraint UK1qiirgkicnr44w42b47w55ph unique (student_id, course_id)
	// alter table tbl_student_course add constraint FK7f68hyvs6v074n8yb7w13fe94 foreign key (course_id) references course (id)
	// alter table tbl_student_course add constraint FKfi0rtogclaaa16ftycb58r1ih foreign key (student_id) references student (id)
	
	@Builder.Default // Lets you configure default values for your fields when using @Builder.
	private List<CourseEntity> courses = new ArrayList<>();
	// create table student_course (StudentEntity_id bigint not null, courses_id bigint not null)

//	public StudentEntity() {
//		courses = new ArrayList<>();
//	}

}
