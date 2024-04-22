## JPA - Hibernate" (jakarta-ee9-hibernate-jpa)
* feature/01_hibernate-jpa
	- Entity Manager: createQuery(), find(), persist(), merge() and remove()

## JPA - Hibernate "JPQL & HQL" (jakarta-ee9-hibernate-jpa-jpql_hql)
* feature/02_hibernate-jpa-jpql-hql

## JPA - Hibernate "Hibernate Criteria" (jakarta-ee9-hibernate-jpa-hcriterial)
* feature/03_hibernate-jpa-hcriterial
	- EntityManager: CriteriaBuilder, CriteriaQuery<?> query, Root<?> from

## JPA - Hibernate "Hibernate Criteria" (jakarta-ee9-hibernate-jpa-events-lifecycle)
* feature/04_hibernate-jpa-events-lifecycle
	- @PrePersiste @PostPersist, @PreUpdate @PostUpdate, @PreRemove @PostRemove, @Embeddable, @Embedded

## JPA - Hibernate "Associations" (jakarta-ee9-hibernate-jpa-association)
* feature/05_hibernate-jpa-association
	- @OneToMany, @ManyToOne, @OneToOne @ManyToMany

## JPA - Hibernate "Associations and Fetch type Eager and Lazy" (jakarta-ee9-hibernate-jpa-association-fetch-type)
* feature/06_hibernate-jpa-association-fetch-type
	- @OneToMany, @ManyToMany used by default FetchType.Lazy
	- @ManyToOne, @OneToOne used by default FetchType.Eager
	- 2 attributes in an Entity class with @OneToMany(fetch = FetchType.EAGER) will throw an Exception: cannot simultaneously fetch multiple bag [...]
	  In a query we cannot use "SELECT ... LEFT JOIN FETCH c.addressEntities LEFT JOIN FETCH c.invoiceEntities ...;" because it will throw: cannot simultaneously fetch multiple bag [...invoiceEntities, ...addressEntities] because both are @OneToMany.
	  @See for recommendations: FetchLazyOneToManyJoinFetchResultList.java and FetchLazyOneToManyJoinFetchSingleResult.java
	- For @OneToMany and @ManyToMany the Fetch LEFT JOIN works in the same way.
