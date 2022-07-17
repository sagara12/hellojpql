package jpql;

import javax.persistence.*;

public class JpaMain {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();
        try {
            Member member = new Member();
            member.setUsername("member1");
            member.setAge(10);
            em.persist(member);
            // 바인딩
            TypedQuery<Member> query = em.createQuery("select m from Member m where m.username = :username", Member.class);
            query.setParameter("username", "member1");
            Member singleResult = query.getSingleResult();
            System.out.println("singleResult = " + singleResult);

            //Query query3= em.createQuery("select m.username, m.age from Member m");
            Member result = query.getSingleResult();
            //Spring data JPA 결과 하나 이상일때나 결과가 하나가 아닐때  -> null, optional
            System.out.println("result = " + result);

            tx.commit();

        }catch (Exception e){
            tx.rollback();
        }finally {
            em.close();
        }
        emf.close();
    }

}
