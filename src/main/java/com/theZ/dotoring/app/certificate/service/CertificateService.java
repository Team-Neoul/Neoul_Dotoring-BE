package com.theZ.dotoring.app.certificate.service;

import com.theZ.dotoring.app.certificate.mapper.CertificateMapper;
import com.theZ.dotoring.app.certificate.model.Certificate;
import com.theZ.dotoring.app.certificate.repository.CertificateRepository;
import com.theZ.dotoring.common.FileUtils;
import com.theZ.dotoring.common.MessageCode;
import com.theZ.dotoring.common.UploadFile;
import com.theZ.dotoring.exception.FileSaveFailedException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Certificate에 관한 비즈니스 로직이 담겨있습니다.
 *
 * @author Sonny
 * @version 1.0
 */
@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class CertificateService {

    private final FileUtils fileUtils;
    private final CertificateRepository certificateRepository;

    /**
     * 인자로 받은 certificates를 사용하여 실제 파일은 파일 시스템에 저장하고 파일에 관련된 정보들은 DB에 저장한 후 Certifiacte엔티티들을 반환하는 메서드
     *
     * @param certificates
     *
     * @return certificate 엔티티들을 반환
     */

    @Transactional(rollbackFor = IOException.class)
    public List<Certificate> saveCertifications(List<MultipartFile> certificates) throws IOException {
        List<UploadFile> uploadFiles = fileUtils.storeFiles(certificates);
        List<Certificate> certificateList = CertificateMapper.to(uploadFiles);
        certificateRepository.saveAll(certificateList);
        return certificateList;
    }


    /**
     * 인자로 받은 certificationIds 사용하여 Id가 일치하는 증명서들을 반환하는 메서드
     *
     * @param certificationIds
     *
     * @return certificate 엔티티들을 반환
     */

    public List<Certificate> getCertifications(List<Long> certificationIds){
        return certificateRepository.findAllByOrderByCreatedAtDesc().stream()
                .filter(certification -> certificationIds.contains(certification.getId()))
                .collect(Collectors.toList());
    }


}
