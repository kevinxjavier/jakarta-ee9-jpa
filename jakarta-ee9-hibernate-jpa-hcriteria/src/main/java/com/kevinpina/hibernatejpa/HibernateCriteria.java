package com.kevinpina.hibernatejpa;

import com.kevinpina.hibernatejpa.repository.db.JpaUtil;
import com.kevinpina.hibernatejpa.repository.entities.ClientEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.*;

import java.util.Arrays;
import java.util.List;

public class HibernateCriteria {

    public static void main(String[] args) {
        EntityManager em = JpaUtil.getEntitymanagerfactory();

        CriteriaBuilder criteria = em.getCriteriaBuilder();

        CriteriaQuery<ClientEntity> query = criteria.createQuery(ClientEntity.class);
        Root<ClientEntity> from = query.from(ClientEntity.class);

        query.select(from); // SELECT *
        List<ClientEntity> clients1 = em.createQuery(query).getResultList();

        System.out.println("------------------------------ List<ClientEntity> clients1 ------------------------------");
        clients1.forEach(System.out::println);

        System.out.println("------------------------------ List<ClientEntity> clients1 WHERE EQUAL ------------------------------");
        //query = criteria.createQuery(ClientEntity.class);
        //from = query.from(ClientEntity.class);
        query.select(from).where(criteria.equal(from.get("name"), "kevin"));
        clients1 = em.createQuery(query).getResultList();
        clients1.forEach(System.out::println);

        ParameterExpression<String> nameParam = criteria.parameter(String.class, "nameParam");
        query.select(from).where(criteria.equal(from.get("name"), nameParam));
        clients1 = em.createQuery(query).setParameter("nameParam", "kevin").getResultList();
        clients1.forEach(System.out::println);

        System.out.println("------------------------------ ClientEntity client WHERE EQUAL ------------------------------");
        //query = criteria.createQuery(ClientEntity.class);
        //from = query.from(ClientEntity.class);
        ParameterExpression<Long> idParam = criteria.parameter(Long.class, "idparam");
        query.select(from).where(criteria.equal(from.get("id"), idParam));
        ClientEntity client = em.createQuery(query).setParameter("idparam", 1L).getSingleResult();
        System.out.println(client);

        System.out.println("------------------------------ List<ClientEntity> clients1 WHERE LIKE ------------------------------");
        //query = criteria.createQuery(ClientEntity.class);
        //from = query.from(ClientEntity.class);
        query.select(from).where(criteria.like(from.get("surname"), "%ron%"));
        clients1 = em.createQuery(query).getResultList();
        clients1.forEach(System.out::println);

        ParameterExpression<String> surNameParam = criteria.parameter(String.class, "surnameParam");
        query.select(from).where(criteria.like(criteria.upper(from.get("surname")), criteria.upper(surNameParam)));
        clients1 = em.createQuery(query).setParameter("surnameParam", "%ron%").getResultList();
        clients1.forEach(System.out::println);

        System.out.println("------------------------------ List<ClientEntity> clients1 WHERE BETWEEN ------------------------------");
        //query = criteria.createQuery(ClientEntity.class);
        //from = query.from(ClientEntity.class);
        query.select(from).where(criteria.between(from.get("id"), 3L, 5L));
        clients1 = em.createQuery(query).getResultList();
        clients1.forEach(System.out::println);

        System.out.println("------------------------------ List<ClientEntity> clients1 WHERE IN ------------------------------");
        //query = criteria.createQuery(ClientEntity.class);
        //from = query.from(ClientEntity.class);
        //query.select(from).where(from.get("name").in("kevin", "javier", "anna"));
        //query.select(from).where(from.get("name").in(Arrays.asList("kevin", "javier", "anna")));
        ParameterExpression<List> nameListParam = criteria.parameter(List.class, "namesList");
        query.select(from).where(from.get("name").in(nameListParam));
        clients1 = em.createQuery(query).setParameter("namesList", Arrays.asList("kevin", "javier", "anna")).getResultList();
        clients1.forEach(System.out::println);

        System.out.println("------------------------------ List<ClientEntity> clients1 WHERE >= ------------------------------");
        //query = criteria.createQuery(ClientEntity.class);
        //from = query.from(ClientEntity.class);
        query.select(from).where(criteria.ge(from.get("id"), 5L));
        clients1 = em.createQuery(query).getResultList();
        clients1.forEach(System.out::println);

        System.out.println("------------------------------ List<ClientEntity> clients1 WHERE > ------------------------------");
        //query = criteria.createQuery(ClientEntity.class);
        //from = query.from(ClientEntity.class);
        query.select(from).where(criteria.gt(criteria.length(from.get("name")), 5L));
        clients1 = em.createQuery(query).getResultList();
        clients1.forEach(System.out::println);

        System.out.println("------------------------------ List<ClientEntity> clients1 WHERE <= ------------------------------");
        //query = criteria.createQuery(ClientEntity.class);
        //from = query.from(ClientEntity.class);
        query.select(from).where(criteria.le(criteria.length(from.get("name")), 5L));
        clients1 = em.createQuery(query).getResultList();
        clients1.forEach(System.out::println);

        System.out.println("------------------------------ List<ClientEntity> clients1 Predicates Conjunctions (and) Disfunctions (or) ------------------------------");
        //query = criteria.createQuery(ClientEntity.class);
        //from = query.from(ClientEntity.class);

        Predicate byId = criteria.ge(from.get("id"), 1L);
        Predicate byName = criteria.equal(from.get("name"), "kevin");
        Predicate byPaymentType = criteria.equal(from.get("paymentType"), "visa");
        //query.select(from).where(criteria.and(byName, byPaymentType));
        query.select(from).where(criteria.and(byId, criteria.or(byName, byPaymentType)));
        clients1 = em.createQuery(query).getResultList();
        clients1.forEach(System.out::println);

        System.out.println("------------------------------ List<ClientEntity> clients1 Order By ------------------------------");
        query = criteria.createQuery(ClientEntity.class);
        from = query.from(ClientEntity.class);
        // uncommented previous lines due uses the where before and here we use at the beginning .orderBy() instead of .where()
        query.select(from).orderBy(criteria.asc(from.get("name")), criteria.desc(from.get("surname")));
        clients1 = em.createQuery(query).getResultList();
        clients1.forEach(System.out::println);

        System.out.println("------------------------------ List<String> names ------------------------------");
        CriteriaQuery<String> queryString = criteria.createQuery(String.class);
        from = queryString.from(ClientEntity.class);
        queryString.select(from.get("name"));
        List<String> names = em.createQuery(queryString).getResultList();
        names.forEach(System.out::println);

        System.out.println("------------------------------ List<String> names DISTINCT ------------------------------");
        queryString = criteria.createQuery(String.class);
        from = queryString.from(ClientEntity.class);
        queryString.select(from.get("name")).distinct(true);
        names = em.createQuery(queryString).getResultList();
        names.forEach(System.out::println);

        System.out.println("------------------------------ List<String> UPPER names DISTINCT ------------------------------");
        queryString = criteria.createQuery(String.class);
        from = queryString.from(ClientEntity.class);
        queryString.select(criteria.upper(from.get("name"))).distinct(true);
        names = em.createQuery(queryString).getResultList();
        names.forEach(System.out::println);

        System.out.println("------------------------------ List<String> UPPER, LOWER, CONCAT names DISTINCT ------------------------------");
        queryString = criteria.createQuery(String.class);
        from = queryString.from(ClientEntity.class);
        queryString.select(criteria.upper(criteria.concat(criteria.concat(from.get("name"), " "), from.get("surname")))).distinct(true);
        names = em.createQuery(queryString).getResultList();
        names.forEach(System.out::println);

        System.out.println("------------------------------ List<Object[]> clientsObject MULTISELECT ------------------------------");
        CriteriaQuery<Object[]> queryObject = criteria.createQuery(Object[].class);
        from = queryObject.from(ClientEntity.class);
        queryObject.multiselect(from.get("id"), from.get("name"), from.get("surname"));
        List<Object[]> clients2 = em.createQuery(queryObject).getResultList();
        clients2.forEach(c -> {
            Long id = (Long) c[0];
            String name = (String) c[1];
            String surname = (String) c[2];
            System.out.println("id = " + id + " name = " + name + ", surname = " + surname);
        });

        System.out.println("------------------------------ List<Object[]> clientsObject MULTISELECT WHERE ------------------------------");
        //queryObject = criteria.createQuery(Object[].class);
        //from = queryObject.from(ClientEntity.class);
        queryObject.multiselect(from.get("id"), from.get("name"), from.get("surname")).where(criteria.like(from.get("name"), "%kev%"));
        clients2 = em.createQuery(queryObject).getResultList();
        clients2.forEach(c -> {
            Long id = (Long) c[0];
            String name = (String) c[1];
            String surname = (String) c[2];
            System.out.println("id = " + id + " name = " + name + ", surname = " + surname);
        });

        System.out.println("------------------------------ List<Object[]> clientsObject MULTISELECT WHERE getSingleResult() ------------------------------");
        //queryObject = criteria.createQuery(Object[].class);
        //from = queryObject.from(ClientEntity.class);
        queryObject.multiselect(from.get("id"), from.get("name"), from.get("surname")).where(criteria.equal(from.get("id"), 2L));
        Object[] clientObject = em.createQuery(queryObject).getSingleResult();

        Long id = (Long) clientObject[0];
        String name = (String) clientObject[1];
        String surname = (String) clientObject[2];
        System.out.println("id = " + id + " name = " + name + ", surname = " + surname);

        System.out.println("------------------------------ Long client COUNT(), SUM(), MAX() ------------------------------");
        CriteriaQuery<Long> queryLong = criteria.createQuery(Long.class);
        from = queryLong.from(ClientEntity.class);
        queryLong.select(criteria.count(from));
        //queryLong.select(criteria.count(from.get("id")));
        Long count = em.createQuery(queryLong).getSingleResult();
        System.out.println("count = " + count);

        queryLong.select(criteria.sum(from.get("id")));
        Long sum = em.createQuery(queryLong).getSingleResult();
        System.out.println("sum = " + sum);

        queryLong.select(criteria.max(from.get("id")));
        Long max = em.createQuery(queryLong).getSingleResult();
        System.out.println("max = " + max);

        queryLong.select(criteria.min(from.get("id")));
        Long min = em.createQuery(queryLong).getSingleResult();
        System.out.println("min = " + min);

        System.out.println("------------------------------ Object[] clientObject MULTISELECT COUNT(), SUM(), MAX() ------------------------------");
        queryObject = criteria.createQuery(Object[].class);
        from = queryObject.from(ClientEntity.class);
        // uncommented previous lines due uses the where before and here we use at the beginning .orderBy() instead of .where()
        queryObject.multiselect(criteria.count(from.get("id")), criteria.sum(from.get("id")), criteria.max(from.get("id")), criteria.min(from.get("id")));
        Object[] operations = em.createQuery(queryObject).getSingleResult();
        System.out.println("count = " + (Long)operations[0] + " sum = " + (Long)operations[1] + " max = " + (Long)operations[2] + " min = " + (Long)operations[3]);

        em.close();
    }

}
