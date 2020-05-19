package study.springboot.account;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import study.springboot.exception.NotFoundException;

import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@Service
@Transactional(readOnly = true)
class AccountService {
    private final AccountRepository repository;

    AccountService(AccountRepository repository) {
        this.repository = repository;
    }

    List<AccountDto> list() {
        return repository.findAll().stream().map(Account::toDto).collect(toList());
    }

    public Page<AccountDto> page(Pageable pageable) {
        Page<Account> page = repository.findAll(pageable);
        return page.map(Account::toDto);
    }


    @Transactional
    void create(AccountDto dto) {
        repository.save(dto.createAccount());
    }

    AccountDto find(long id) {
        Optional<Account> accountOpt = repository.findById(id);
        if(accountOpt.isEmpty()) throw new NotFoundException("account 정보를 찾을 수 없습니다.");
        return accountOpt.get().toDto();
    }

    @Transactional
    void update(AccountDto accountDto) {
        Optional<Account> accountOpt = repository.findById(accountDto.id);
        if(accountOpt.isEmpty()) throw new NotFoundException("수정하려는 대상이 없습니다.");
        Account account = accountOpt.get();
        account.update(accountDto);
        repository.save(account);
    }

    @Transactional
    void delete(Long id) {
        repository.deleteById(id);
    }
}
