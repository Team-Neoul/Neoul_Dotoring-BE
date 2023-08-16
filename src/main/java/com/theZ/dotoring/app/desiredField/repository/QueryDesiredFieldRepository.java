package com.theZ.dotoring.app.desiredField.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.StringPath;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.theZ.dotoring.app.menti.dto.MentiRankDTO;
import com.theZ.dotoring.app.menti.dto.PageableMentiDTO;
import com.theZ.dotoring.app.mento.dto.MentoRankDTO;
import com.theZ.dotoring.app.mento.dto.PageableMentoDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import java.util.List;
import static com.theZ.dotoring.app.desiredField.model.QDesiredField.*;

@Repository
@RequiredArgsConstructor
public class QueryDesiredFieldRepository {

    private final JPAQueryFactory jpaQueryFactory;

    public PageableMentoDTO findPageableMento(List<String> fieldNames, Long lastMentoId, int size){

        StringPath mentoCount = Expressions.stringPath("mentoCount");
        List<MentoRankDTO> rankDTOS = jpaQueryFactory
                .select(Projections.bean(MentoRankDTO.class
                        ,desiredField.mento.mentoId.as("mentoId")
                        , desiredField.count().as("mentoCount")))
                .from(desiredField)
                .where(eqFieldNames(fieldNames), desiredField.mento.mentoId.isNotNull(), lessThanMentoId(lastMentoId))
                .groupBy(desiredField.mento.mentoId)
                .orderBy(mentoCount.desc())
                .limit(size + 1)
                .fetch();

        return makePageableMento(rankDTOS, size);
    }

    private BooleanExpression eqFieldNames(List<String> fieldNames){
        if(fieldNames.size() == 0){
            return null;
        }
        return desiredField.field.fieldName.in(fieldNames);
    }

    private BooleanExpression lessThanMentoId(Long mentoId) {
        if (mentoId == null) {
            return null;
        }
        return desiredField.mento.mentoId.lt(mentoId);
    }

    private PageableMentoDTO makePageableMento(List<MentoRankDTO> mentoRankDTOS, int size) {

        boolean hasNext = false;

        // 조회한 결과 개수가 요청한 페이지 사이즈보다 크면 뒤에 더 있음, next = true
        if (mentoRankDTOS.size() > size) {
            hasNext = true;
            mentoRankDTOS.remove(size);
        }

        return new PageableMentoDTO(mentoRankDTOS,hasNext,size);

    }

    public PageableMentiDTO findPageableMenti(List<String> fieldNames, Long lastMentiId, int size) {

        StringPath mentiCount = Expressions.stringPath("mentiCount");
        List<MentiRankDTO> rankDTOS = jpaQueryFactory
                .select(Projections.bean(MentiRankDTO.class
                        ,desiredField.menti.mentiId.as("mentiId")
                        , desiredField.count().as("mentiCount")))
                .from(desiredField)
                .where(eqFieldNames(fieldNames), desiredField.menti.mentiId.isNotNull(), lessThanMentiId(lastMentiId))
                .groupBy(desiredField.menti.mentiId)
                .orderBy(mentiCount.desc())
                .limit(size + 1)
                .fetch();

        return makePageableMenti(rankDTOS, size);
    }

    private BooleanExpression lessThanMentiId(Long mentiId) {
        if (mentiId == null) {
            return null;
        }
        return desiredField.menti.mentiId.lt(mentiId);
    }

    private PageableMentiDTO makePageableMenti(List<MentiRankDTO> mentiRankDTOS, int size) {

        boolean hasNext = false;

        // 조회한 결과 개수가 요청한 페이지 사이즈보다 크면 뒤에 더 있음, next = true
        if (mentiRankDTOS.size() > size) {
            hasNext = true;
            mentiRankDTOS.remove(size);
        }

        return new PageableMentiDTO(mentiRankDTOS,hasNext,size);

    }
}
