package src.service;

import org.springframework.data.repository.CrudRepository;
import src.Domain.User;

import java.util.List;

/**
 * Created by wyiss on 15/12/24.
 */
public interface UserRepository extends CrudRepository<User,Long> {
    User findByName(String lastName);
    User findById(Long id);
}
