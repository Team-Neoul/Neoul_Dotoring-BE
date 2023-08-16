package com.theZ.dotoring.util;

import com.theZ.dotoring.app.mento.model.Mento;
import org.jeasy.random.EasyRandom;
import org.jeasy.random.EasyRandomParameters;

public class MentoFixtureFactory {

    static public Mento create(){
        EasyRandomParameters parameters = new EasyRandomParameters();
        return new EasyRandom(parameters).nextObject(Mento.class);
    }

    static public Mento create(Long seed){
        EasyRandomParameters parameters = new EasyRandomParameters().seed(seed);
        return new EasyRandom(parameters).nextObject(Mento.class);
    }

}
