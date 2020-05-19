package study.springboot.user;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.PagingAndSortingRepository;
import study.springboot.user.Role.RoleEnum;

import javax.persistence.QueryHint;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface UserRepository extends PagingAndSortingRepository<User, Long> {
    List<User> findAll();

    Optional<User> findByUsername(String username);

    @Cacheable(cacheNames = "repository.user.page", key="'page:' + #pageable?.hashCode() +  ':' + #searchDto?.hashCode()")
    @Query("select distinct u from User u join u.roles r where u.username like %?1% and r in (?2)")
    Page<User> findByUsernameContainingAndRolesIn(String username, Collection<RoleEnum> role, Pageable pageable);

    @Override
    @CacheEvict(cacheNames = "repository.user.page", allEntries = true)
    User save(User user);

    @Override
    @CacheEvict(cacheNames = "repository.user.page", allEntries = true)
    void deleteById(Long id);

    @Override
    @CacheEvict(cacheNames = "repository.user.page", allEntries = true)
    void delete(User user);
}
