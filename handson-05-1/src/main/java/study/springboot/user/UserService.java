package study.springboot.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import study.springboot.exception.NotFoundException;

import javax.cache.Cache;
import javax.cache.CacheManager;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
class UserService {
    private final UserRepository repository;
//    @Autowired
//    private CacheManager cacheManager;

    UserService(UserRepository repository) {
        this.repository = repository;
    }

//    @Cacheable(cacheNames="service.user.page", key="'page:' + #pageable?.hashCode() +  ':' + #searchDto?.hashCode()")
    public Page<UserDto> page(Pageable pageable, UserSearchDto searchDto) {
        return repository.findByUsernameContainingAndRolesIn(searchDto.getUsername(), searchDto.getSearchRole(), pageable).map(User::toDto);
    }

    @Transactional
//    @CacheEvict(cacheNames = "service.user.page", allEntries = true)
    public void create(UserDto dto) {
        repository.save(dto.createUser());
    }

//    @Cacheable(cacheNames="service.user.page", key="'find:' + #id")
    public UserDto find(long id) {
        Optional<User> accountOpt = repository.findById(id);
        if(accountOpt.isEmpty()) throw new NotFoundException("account 정보를 찾을 수 없습니다.");
        return accountOpt.get().toDto();
    }

    @Transactional
    public void update(UserDto accountDto) {
        Optional<User> accountOpt = repository.findById(accountDto.id);
        if(accountOpt.isEmpty()) throw new NotFoundException("수정하려는 대상이 없습니다.");
        User account = accountOpt.get();
        account.update(accountDto);
        repository.save(account);
    }

    @Transactional
//    @CacheEvict(cacheNames = "service.user.page", allEntries = true)
    public void delete(Long id) {
        repository.deleteById(id);
    }
}
