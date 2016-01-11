package src.service;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import src.Domain.Record;
import src.Domain.User;

import java.util.List;

/**
 * Created by wyiss on 15/12/24.
 */
public interface RecordRepository extends CrudRepository<Record,Long>{
    List<Record> findByToUser(String user);

    @Query("select r.toUser,count(r.toUser) from Record r group by r.toUser ")
    List<Record> findAllBadgedUser();
}
