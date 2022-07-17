package jpql;

import javax.persistence.*;
import java.util.List;

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

             List<MemberDTO> resultList= em.createQuery("select new jpql.MemberDTO (m.username, m.age) from Member m", MemberDTO.class).getResultList();

             MemberDTO memberDTO = resultList.get(0);
            System.out.println("memberDTO.getUsername() = " + memberDTO.getUsername());
            System.out.println("memberDTO.getAge() = " + memberDTO.getAge());
            /* Object o= resultList.get(0);
             Object [] result  = (Object[]) o;
            System.out.println("username = " + result[0]);
            System.out.println("age = " + result[1]);*/


           /* // 바인딩
            TypedQuery<Member> query = em.createQuery("select m from Member m where m.username = :username", Member.class);
            query.setParameter("username", "member1");
            Member singleResult = query.getSingleResult();
            System.out.println("singleResult = " + singleResult);

            //Query query3= em.createQuery("select m.username, m.age from Member m");
            Member result = query.getSingleResult();
            //Spring data JPA 결과 하나 이상일때나 결과가 하나가 아닐때  -> null, optional
            System.out.println("result = " + result);*/

            tx.commit();

        }catch (Exception e){
            tx.rollback();
        }finally {
            em.close();
        }
        emf.close();
    }

}
