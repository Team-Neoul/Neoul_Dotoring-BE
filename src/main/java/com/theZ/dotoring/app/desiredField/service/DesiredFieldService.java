package com.theZ.dotoring.app.desiredField.service;

import com.theZ.dotoring.app.desiredField.model.DesiredField;
import com.theZ.dotoring.app.desiredField.repository.DesiredFieldRepository;
import com.theZ.dotoring.app.desiredField.repository.QueryDesiredFieldRepository;
import com.theZ.dotoring.app.field.model.Field;
import com.theZ.dotoring.app.menti.dto.PageableMentiDTO;
import com.theZ.dotoring.app.mento.dto.PageableMentoDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;


/**
 * DesiredField 관한 비즈니스 로직이 담겨있습니다.
 *
 * @author Sonny
 * @version 1.0
 */
@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class DesiredFieldService {

    private final DesiredFieldRepository desiredFieldRepository;
    private final QueryDesiredFieldRepository queryDesiredFieldRepository;

    /**
     * 인자로 받은 fields 사용하여 DesiredField 엔티티를 생성하여 이를 DB에 저장한 후 반환하는 메서드
     *
     * @param fields
     *
     * @return DesiredField 엔티티들을 반환
     */

    public List<DesiredField> save(List<Field> fields){
        List<DesiredField> desiredFields = DesiredField.createDesiredFields(fields);
        List<DesiredField> savedDesiredFields = desiredFieldRepository.saveAll(desiredFields);
        return savedDesiredFields;
    }


    /**
     * 인자로 받은 mentoId 사용하여 DesiredField 엔티티 행들 중 FK가 mentoId와 같은 것을 제거하는 메서드
     *
     * @param mentoId
     *
     */
    public void deleteAllByMentoId(Long mentoId){
//
//        List<DesiredField> desiredFields = desiredFieldRepository.findByMentoId(mentoId);
//
//        /**
//         *  Mento에서 desiredFields로의 연관관계 해제
//         */
//        desiredFields.stream().forEach(i -> i.getMento().getDesiredFields().clear());
//        desiredFields.stream().forEach(i -> i.getField().getDesiredFields().clear());
        desiredFieldRepository.deleteAllByMento_MentoId(mentoId);
    }

    /**
     * 인자로 받은 mentiId를 사용하여 DesiredField 엔티티 행들 중 FK가 mentiId인 것을 찾아 페이징 처리해주고 이를 반환하는 메서드
     *
     * @param mentiId
     * @param lastMentoId
     * @param size
     *
     * @return pageableMento를 반환
     */
    @Transactional(readOnly = true)
    public PageableMentoDTO findPageableMento(Long mentiId, Long lastMentoId, int size){
        List<String> fieldNames = desiredFieldRepository.findByMentiId(mentiId).stream().map(df -> df.getField().getFieldName()).collect(Collectors.toList());
        PageableMentoDTO pageableMento = queryDesiredFieldRepository.findPageableMento(fieldNames, lastMentoId, size);
        return pageableMento;
    }


    /**
     * 인자로 받은 mentoId를 사용하여 DesiredField 엔티티 행들 중 FK가 mentoId인 것을 찾아 페이징 처리해주고 이를 반환하는 메서드
     *
     * @param mentoId
     * @param lastMentiId
     * @param size
     *
     * @return pageableMenti를 반환
     */
    @Transactional(readOnly = true)
    public PageableMentiDTO findPageableMenti(Long mentoId, Long lastMentiId, int size){
        List<String> fieldNames = desiredFieldRepository.findByMentoId(mentoId).stream().map(df -> df.getField().getFieldName()).collect(Collectors.toList());
        PageableMentiDTO pageableMenti = queryDesiredFieldRepository.findPageableMenti(fieldNames, lastMentiId, size);
        return pageableMenti;
    }


}
