package study.springboot.account;

import com.querydsl.core.BooleanBuilder;
import org.apache.logging.log4j.util.Strings;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import study.springboot.account.Role.RoleEnum;

import java.util.Collection;
import java.util.List;

// TODO