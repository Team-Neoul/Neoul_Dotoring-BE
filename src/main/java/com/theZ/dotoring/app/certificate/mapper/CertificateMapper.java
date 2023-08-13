package com.theZ.dotoring.app.certificate.mapper;

import com.theZ.dotoring.app.certificate.model.Certificate;
import com.theZ.dotoring.common.UploadFile;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class CertificateMapper {

    // entity로 변환 : to
    public static List<Certificate> to(List<UploadFile> uploadFiles){

        if(uploadFiles == null && uploadFiles.size() >0){
            return null;
        }

        return IntStream.range(0, uploadFiles.size())
                .mapToObj(i -> Certificate.builder()
                        .originalFileName(uploadFiles.get(i).getOriginalFileName())
                        .saveFileName(uploadFiles.get(i).getStoreFileName())
                        .build())
                .collect(Collectors.toList());
    }

}
