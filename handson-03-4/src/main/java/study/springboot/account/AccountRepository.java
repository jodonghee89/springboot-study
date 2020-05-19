package study.springboot.account;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface AccountRepository extends PagingAndSortingRepository<Account, Long> {
	List<Account>  findAll();
}