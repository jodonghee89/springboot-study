package study.springboot.config.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import study.springboot.common.dto.AccountDto;
import study.springboot.user.User;
import study.springboot.user.UserRepository;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class UserDetailsServiceImpl implements UserDetailsService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository repository;

    public UserDetailsServiceImpl(PasswordEncoder passwordEncoder, UserRepository repository) {
        this.passwordEncoder = passwordEncoder;
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<AccountDto> AccountOptional = repository.findByUsername(username).map(User::getAccount);
        if(AccountOptional.isPresent()) {
            AccountDto dto = AccountOptional.get();
            String[] roles =  dto.roles.toArray(new String[dto.roles.size()]);
            return org.springframework.security.core.userdetails.User.builder()
                    .username(dto.username)
                    .password(dto.password)
                    .roles(roles)
                    .build();
        } else {
            throw new UsernameNotFoundException("사용자를 찾을 수 없습니다.");
        }
    }
}

