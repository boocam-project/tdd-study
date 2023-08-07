package com.swger.tddstudy.member.repository;

import com.swger.tddstudy.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member,Long> {
    Optional<Member> findByUsername(String  username);

    @Override
    <S extends Member> S save(S entity);
}
