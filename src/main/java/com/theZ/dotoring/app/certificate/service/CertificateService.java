package com.theZ.dotoring.app.certificate.service;

import com.theZ.dotoring.app.certificate.mapper.CertificateMapper;
import com.theZ.dotoring.app.certificate.model.Certificate;
import com.theZ.dotoring.app.certificate.repository.CertificateRepository;
import com.theZ.dotoring.common.FileUtils;
import com.theZ.dotoring.common.UploadFile;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class CertificateService {

    private final FileUtils fileUtils;
    private final CertificateRepository certificateRepository;

    public List<Certificate> saveCertifications(List<MultipartFile> certificates) throws IOException {
        List<UploadFile> uploadFiles = fileUtils.storeFiles(certificates);
        List<Certificate> certificateList = CertificateMapper.to(uploadFiles);
        /**
         *  null인지 확인!
         */
        certificateRepository.saveAll(certificateList);
        return certificateList;
    }

    public List<Certificate> getCertifications(List<Long> certificationIds){
        return certificateRepository.findAllByOrderByCreatedAtDesc().stream()
                .filter(certification -> certificationIds.contains(certification.getId()))
                .collect(Collectors.toList());
    }


    /**
     *  증명서를 반환해주는 기능
     *  @Param : String content,
     */


}
