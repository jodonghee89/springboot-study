package study.springboot.account;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.PathBuilderFactory;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.AbstractJPAQuery;
import com.querydsl.jpa.impl.JPAQuery;
import org.apache.logging.log4j.util.Strings;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.Querydsl;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import study.springboot.exception.NotFoundException;

import javax.persistence.EntityManager;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static study.springboot.account.QAccount.account;
import static study.springboot.account.QRole.role;

@Service
@Transactional(readOnly = true)
class AccountService {
    private final AccountRepository repository;

    AccountService(AccountRepository repository) {
        this.repository = repository;
    }

    // TODO


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
