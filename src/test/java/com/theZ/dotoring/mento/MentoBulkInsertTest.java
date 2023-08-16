package com.theZ.dotoring.mento;

import com.theZ.dotoring.app.mento.repository.MentoRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class MentoBulkInsertTest {

    @Autowired
    private MentoRepository mentoRepository;

    @Test
    public void bulkInsert(){

    }
}
