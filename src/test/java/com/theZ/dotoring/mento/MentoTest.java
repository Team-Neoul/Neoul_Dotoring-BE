package com.theZ.dotoring.mento;

import com.theZ.dotoring.util.MentoFixtureFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.stream.LongStream;

public class MentoTest {

    @DisplayName("EasyRandom 라이브러리를 통해 만든 Mento 객체 생성 테스트")
    @Test
    public void createMento_test(){
        LongStream.range(0,10)
                .mapToObj(i -> MentoFixtureFactory.create(i))
                .forEach(mento -> {
                    System.out.println(mento.getNickname());
                });
    }
}
