package src.service;

import org.springframework.data.repository.CrudRepository;
import src.Domain.Badge;

/**
 * Created by wyiss on 15/12/25.
 */
public interface BadgeRepository extends CrudRepository<Badge,Long> {
    Badge findById(long id);
}
