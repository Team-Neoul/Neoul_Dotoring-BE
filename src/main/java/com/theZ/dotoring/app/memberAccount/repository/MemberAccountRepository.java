package com.theZ.dotoring.app.memberAccount.repository;

import com.theZ.dotoring.app.memberAccount.model.MemberAccount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MemberAccountRepository extends JpaRepository<MemberAccount, Long> {
}
