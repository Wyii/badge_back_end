package src.service;

import org.springframework.data.repository.CrudRepository;
import src.Domain.Record;
import src.Domain.User;

import java.util.List;

/**
 * Created by wyiss on 15/12/24.
 */
public interface RecordRepository extends CrudRepository<Record,Long>{
    List<Record> findByToUser(User user);
}
