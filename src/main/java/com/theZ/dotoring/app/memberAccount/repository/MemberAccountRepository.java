package com.theZ.dotoring.app.memberAccount.repository;

import com.theZ.dotoring.app.memberAccount.model.MemberAccount;
import com.theZ.dotoring.app.mento.model.Mento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface MemberAccountRepository extends JpaRepository<MemberAccount, Long> {

    Optional<MemberAccount> findByEmail(String email);

    Optional<MemberAccount> findByLoginId(String loginId);

}
