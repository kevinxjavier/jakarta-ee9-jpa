package com.kevinpina.hibernatejpa.fetch;

import com.kevinpina.hibernatejpa.repository.db.JpaUtil;
import com.kevinpina.hibernatejpa.repository.entities.ClientEntity;
import jakarta.persistence.EntityManager;

public class FetchLazyOneToManyJoinFetchSingleResult {

    // Optimizing the queries JPQL with "LEFT JOIN".

    public static void main(String[] args) {
        EntityManager em = JpaUtil.getEntitymanagerfactory();

        // LEFT JOIN = LEFT OUTER JOIN
        // FETCH to populate the data in ClientEntity if we don't put it will appear the columns but without data
        String sql = "SELECT c FROM ClientEntity c LEFT JOIN FETCH c.addressEntities LEFT JOIN FETCH c.documentEntity WHERE c.id = :id";
        ClientEntity clientEntity = em.createQuery(sql, ClientEntity.class)
                .setParameter("id", 1L)
                .getSingleResult();

        /*
            NOTE 1: We cannot use  @OneToMany(fetch = FetchType.EAGER) twice in the same class because it will throw: cannot simultaneously fetch multiple bag [...invoiceEntities, ...addressEntities]

                class ClientEntity {
                    @OneToMany()
                    List<InvoiceEntity> invoiceEntities;

                    @OneToMany()
                    List<AddressEntity> addressEntities;
                }

            NOTE 2: In a query "SELECT c FROM ClientEntity c LEFT JOIN FETCH c.addressEntities LEFT JOIN FETCH c.documentEntity WHERE c.id = :id";
                we cannot use "SELECT ... LEFT JOIN FETCH c.addressEntities LEFT JOIN FETCH c.invoiceEntities ...;" because it will throw: cannot simultaneously fetch multiple bag [...invoiceEntities, ...addressEntities]
                because both are @OneToMany.

                Recommendation:
                    A) For that case it is better have 2 queries:
                        List<ClientEntity> clientAddressesEntities = em.createQuery("SELECT c FROM ClientEntity c LEFT JOIN FETCH c.addressEntities LEFT JOIN FETCH c.documentEntity WHERE c.id = :id";, ClientEntity.class).getResultList();
                        List<ClientEntity> clientInvoicesEntities = em.createQuery("SELECT c FROM ClientEntity c LEFT JOIN FETCH c.invoiceEntities LEFT JOIN FETCH c.documentEntity WHERE c.id = :id";, ClientEntity.class).getResultList();

                    B) Or if it is a small database but still not recommended, change one of the @OneToMany for Set<> instead if List<>.
                        List<ClientEntity> clientAddressesInvoicesEntities = em.createQuery("SELECT c FROM ClientEntity c LEFT JOIN FETCH c.addressEntities LEFT JOIN FETCH c.invoiceEntities LEFT JOIN FETCH c.documentEntity WHERE c.id = :id", ClientEntity.class).getResultList();

                        class ClientEntity {
                            @OneToMany()
                            List<InvoiceEntity> invoiceEntities;

                            @OneToMany()
                            Set<AddressEntity> addressEntities;
                        }

        */

        // When sql = "SELECT c FROM ClientEntity c WHERE c.id = :id"; will execute 3 select queries:
        // Hibernate: select cliententi0_.id as id1_4_, cliententi0_.created as created2_4_, cliententi0_.modified as modified3_4_, cliententi0_.name as name4_4_, cliententi0_.payment_type as payment_5_4_, cliententi0_.role_id as role_id7_4_, cliententi0_.surname as surname6_4_ from client cliententi0_ where cliententi0_.id=?
        // Hibernate: select documenten0_.id as id1_7_3_, documenten0_.client_id as client_i3_7_3_, documenten0_.type as type2_7_3_, cliententi1_.id as id1_4_0_, cliententi1_.created as created2_4_0_, cliententi1_.modified as modified3_4_0_, cliententi1_.name as name4_4_0_, cliententi1_.payment_type as payment_5_4_0_, cliententi1_.role_id as role_id7_4_0_, cliententi1_.surname as surname6_4_0_, addressent2_.id_client as id_clien1_11_5_, addressent3_.id as id_addre2_11_5_, addressent3_.id as id1_0_1_, addressent3_.number as number2_0_1_, addressent3_.streetName as streetna3_0_1_, roleentity4_.id as id1_9_2_, roleentity4_.description as descript2_9_2_, roleentity4_.typeRole as typerole3_9_2_ from document documenten0_ left outer join client cliententi1_ on documenten0_.client_id=cliententi1_.id left outer join tbl_client_address addressent2_ on cliententi1_.id=addressent2_.id_client left outer join address addressent3_ on addressent2_.id_address=addressent3_.id left outer join role roleentity4_ on cliententi1_.role_id=roleentity4_.id where documenten0_.client_id=?
        // Hibernate: select addressent0_.id_client as id_clien1_11_0_, addressent0_.id_address as id_addre2_11_0_, addressent1_.id as id1_0_1_, addressent1_.number as number2_0_1_, addressent1_.streetName as streetna3_0_1_ from tbl_client_address addressent0_ inner join address addressent1_ on addressent0_.id_address=addressent1_.id where addressent0_.id_client=?

        // When sql = "SELECT c FROM ClientEntity c LEFT JOIN FETCH c.addressEntities WHERE c.id = :id"; will execute 2 select queries:
        // Hibernate: select cliententi0_.id as id1_4_0_, addressent2_.id as id1_0_1_, cliententi0_.created as created2_4_0_, cliententi0_.modified as modified3_4_0_, cliententi0_.name as name4_4_0_, cliententi0_.payment_type as payment_5_4_0_, cliententi0_.role_id as role_id7_4_0_, cliententi0_.surname as surname6_4_0_, addressent2_.number as number2_0_1_, addressent2_.streetName as streetna3_0_1_, addressent1_.id_client as id_clien1_11_0__, addressent1_.id_address as id_addre2_11_0__ from client cliententi0_ left outer join tbl_client_address addressent1_ on cliententi0_.id=addressent1_.id_client left outer join address addressent2_ on addressent1_.id_address=addressent2_.id where cliententi0_.id=?
        // Hibernate: select documenten0_.id as id1_7_3_, documenten0_.client_id as client_i3_7_3_, documenten0_.type as type2_7_3_, cliententi1_.id as id1_4_0_, cliententi1_.created as created2_4_0_, cliententi1_.modified as modified3_4_0_, cliententi1_.name as name4_4_0_, cliententi1_.payment_type as payment_5_4_0_, cliententi1_.role_id as role_id7_4_0_, cliententi1_.surname as surname6_4_0_, addressent2_.id_client as id_clien1_11_5_, addressent3_.id as id_addre2_11_5_, addressent3_.id as id1_0_1_, addressent3_.number as number2_0_1_, addressent3_.streetName as streetna3_0_1_, roleentity4_.id as id1_9_2_, roleentity4_.description as descript2_9_2_, roleentity4_.typeRole as typerole3_9_2_ from document documenten0_ left outer join client cliententi1_ on documenten0_.client_id=cliententi1_.id left outer join tbl_client_address addressent2_ on cliententi1_.id=addressent2_.id_client left outer join address addressent3_ on addressent2_.id_address=addressent3_.id left outer join role roleentity4_ on cliententi1_.role_id=roleentity4_.id where documenten0_.client_id=?

        // When sql = "SELECT c FROM ClientEntity c LEFT JOIN FETCH c.addressEntities LEFT JOIN FETCH c.documentEntity WHERE c.id = :id"; will execute 1 select query:
        // Hibernate: select cliententi0_.id as id1_4_0_, addressent2_.id as id1_0_1_, documenten3_.id as id1_7_2_, cliententi0_.created as created2_4_0_, cliententi0_.modified as modified3_4_0_, cliententi0_.name as name4_4_0_, cliententi0_.payment_type as payment_5_4_0_, cliententi0_.role_id as role_id7_4_0_, cliententi0_.surname as surname6_4_0_, addressent2_.number as number2_0_1_, addressent2_.streetName as streetna3_0_1_, addressent1_.id_client as id_clien1_11_0__, addressent1_.id_address as id_addre2_11_0__, documenten3_.client_id as client_i3_7_2_, documenten3_.type as type2_7_2_ from client cliententi0_ left outer join tbl_client_address addressent1_ on cliententi0_.id=addressent1_.id_client left outer join address addressent2_ on addressent1_.id_address=addressent2_.id left outer join document documenten3_ on cliententi0_.id=documenten3_.client_id where cliententi0_.id=?


        // If we call getAddressEntities() or getDocumentEntity() will execute another SELECTS to bring them when is FetchType.LAZY
        // otherwise if we put FetchType.EAGER will execute another select automatically to bring them.
        // System.out.println(clientEntity.getAddressEntities());

        em.close();
        // Because we put FetchType.EAGER in ClientEntity ... @OneToMany(fetch = FetchType.EAGER, ...) List<AddressEntity> addressEntities; will not throw after em.close() the: could not initialize proxy - no Session
        // But in this case won't throw the: could not initialize proxy - no Session. Event if we erased FetchType.EAGER in addressEntities because we are using LEFT JOIN to bring all the data.
        System.out.println(clientEntity.getAddressEntities());
        System.out.println(clientEntity.getDocumentEntity());
    }

}
