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

// TODO