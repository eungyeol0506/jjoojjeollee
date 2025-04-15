package app.project.jjoojjeollee.repository;

import app.project.jjoojjeollee.domain.User;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

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

        return user.getNo();
    }

    public User findByNo(Long no){
        return em.find(User.class, no);
    }

//    public User findById(String id){
//
//    }
//
//    public User findByEmail(String email){
//
//    }

}
