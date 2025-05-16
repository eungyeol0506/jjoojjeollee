package app.project.jjoojjeollee.repository;

import app.project.jjoojjeollee.domain.user.User;
import app.project.jjoojjeollee.domain.user.UserProfile;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserRepository {

    private final EntityManager em;

    public Long save(User user) {
        if(user.getNo() == null){
            em.persist(user);
        }
        else{
            em.merge(user);
        }
//        saveProfile(user.getProfile());

        return user.getNo();
    }

    public void saveProfile(UserProfile userProfile) {

        em.persist(userProfile);
    }

    public Optional<User> findByNo(Long no){
        return Optional.ofNullable(em.find(User.class, no));
    }

    public Optional<User> findById(String id){
        return em.createQuery("select u from User u where u.id = :id", User.class)
                 .setParameter("id", id)
                 .getResultStream().findFirst();
    }

    public Optional<User> findByEmail(String email){
        return em.createQuery("select u from User u where u.email = :email", User.class)
                 .setParameter("email",email)
                .getResultStream()
                .findFirst();
    }

    public Optional<User> findWithProfileByNo(Long userNo){
       return em.createQuery(
               "SELECT u FROM User u " +
                       "JOIN FETCH u.profile p " +
                       "LEFT JOIN FETCH p.profileImage " +
                       "WHERE u.no = :userNo", User.class)
                    .setParameter("userNo",userNo)
                    .getResultStream()
                    .findFirst();
    }
}
