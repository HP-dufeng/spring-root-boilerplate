package example.core.repository;

import example.core.entity.Poll;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;


public interface PollRepository extends PagingAndSortingRepository<Poll, Long> {

}
