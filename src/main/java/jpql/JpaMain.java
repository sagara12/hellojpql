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
                Team team1 = new Team();
                team1.setName("teamA");
                em.persist(team1);

                Team team2 = new Team();
                team2.setName("teamB");
                em.persist(team2);

                Member member1 = new Member();
                member1.setUsername("회원1");
                member1.setTeam(team1);
                em.persist(member1);

                Member member2 = new Member();
                member2.setUsername("회원2");
                member2.setTeam(team1);
                em.persist(member2);

                Member member3 = new Member();
                member3.setUsername("회원3");
                member3.setTeam(team2);
                em.persist(member3);

            em.flush();
            em.clear();

            String query = "select m From Member m join fetch m.team";
            
            List<Member> result = em.createQuery(query, Member.class).getResultList();

            for (Member member : result) {

                System.out.println("member.getUsername() = " + member.getUsername() + "," + "member.getTeam().getName() = "+ member.getTeam().getName());
                //회원 1 팀A (SQL)
                //회원 2 팀A (영속성)     -->  이런 경우 N+1 문제
                //회원 3 팀B (SQL)
            }

            /*//명시적 조인을 해서 별칭을 얻고 별칭을 통해 탐색(실무에서는 묵시적 조인 쓰면 안됨)
            String query = "select m.username From Team t join t.member m";*/
/*            String query =
                          "select " +
                                   "case when m.age <= 10 then '학생요금' "+
                                   "     when m.age >= 60 then '경로요금' "+
                                   "     else '일반요금' " +
                                   "end "+
                                   "from Member m";

            List<String> result = em.createQuery(query, String.class).getResultList();
            for (String s : result) {
                System.out.println("s = " + s);
            }*/
           /* String query = "select m from Member m inner join m.team t";
            List<Member> result = em.createQuery(query, Member.class).getResultList();*/
            /*em.createQuery("select m from Member m order by m.age desc", Member.class)
                    .setFirstResult(1)
                    .setMaxResults(10)
                    .getResultList();*/


            /* List<MemberDTO> resultList= em.createQuery("select new jpql.MemberDTO (m.username, m.age) from Member m", MemberDTO.class).getResultList();

             MemberDTO memberDTO = resultList.get(0);
            System.out.println("memberDTO.getUsername() = " + memberDTO.getUsername());
            System.out.println("memberDTO.getAge() = " + memberDTO.getAge());*/
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
