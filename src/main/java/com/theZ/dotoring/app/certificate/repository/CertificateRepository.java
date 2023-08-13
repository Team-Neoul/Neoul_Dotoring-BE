package com.theZ.dotoring.app.certificate.repository;

import com.theZ.dotoring.app.certificate.model.Certificate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CertificateRepository extends JpaRepository<Certificate,Long> {

    @Query("SELECT c FROM Certificate c ORDER BY c.createdAt DESC")
    List<Certificate> findAllByOrderByCreatedAtDesc();
}
