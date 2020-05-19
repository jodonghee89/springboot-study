package study.springboot.config.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import study.springboot.account.Account;
import study.springboot.account.AccountDto;
import study.springboot.account.AccountRepository;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class UserDetailsServiceImpl implements UserDetailsService {
    private final PasswordEncoder passwordEncoder;
    private final AccountRepository repository;

    public UserDetailsServiceImpl(PasswordEncoder passwordEncoder, AccountRepository repository) {
        this.passwordEncoder = passwordEncoder;
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<AccountDto> AccountOptional = repository.findAll().stream().filter(x -> x.isEqualUsername(username)).map(Account::toDto).findFirst();
        if(AccountOptional.isPresent()) {
            AccountDto m = AccountOptional.get();
            String[] roles =  m.getRoles().toArray(new String[m.getRoles().size()]);
            return org.springframework.security.core.userdetails.User.builder().username(m.getUsername()).password(passwordEncoder.encode(m.getPassword())).roles(roles).build();
        } else {
            throw new UsernameNotFoundException("사용자를 찾을 수 없습니다.");
        }
    }
}

