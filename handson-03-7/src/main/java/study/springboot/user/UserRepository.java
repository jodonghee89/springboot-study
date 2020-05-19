package study.springboot.user;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import study.springboot.user.Role.RoleEnum;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface UserRepository extends PagingAndSortingRepository<User, Long> {
    List<User>  findAll();
    Optional<User> findByUsername(String username);
    Page<User> findByUsernameContainingAndRolesIn(Pageable pageable, String username, Collection<RoleEnum> role);


}
