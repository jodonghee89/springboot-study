package study.springboot.user;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import study.springboot.exception.NotFoundException;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
class UserService {
    private final UserRepository repository;

    UserService(UserRepository repository) {
        this.repository = repository;
    }

    Page<UserDto> page(Pageable pageable, UserSearchDto searchDto) {
        return repository.findByUsernameContainingAndRolesIn(searchDto.getUsername(), searchDto.getSearchRole(), pageable).map(User::toDto);
    }


    @Transactional
    public void create(UserDto dto) {
        repository.save(dto.createUser());
    }

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
    public void delete(Long id) {
        repository.deleteById(id);
    }
}
