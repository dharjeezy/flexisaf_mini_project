package com.example.flexisaf;

/**
 * Damilare
 * 24/11/2021
 **/

import java.util.Set;
import org.jeasy.random.EasyRandom;
import org.jeasy.random.EasyRandomParameters;
import org.jeasy.random.FieldPredicates;


import java.time.LocalDate;
import java.util.Currency;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class EntityMocker {

    public static <T> T mock(final Class<T> clazz){
        final EasyRandomParameters easyRandomParameters = easyRandomParameters();
        final EasyRandom generator = new EasyRandom(easyRandomParameters);
        return generator.nextObject(clazz);
    }

    public static  <T> List<T> mock(final Class<T> clazz, final int count){
        final EasyRandomParameters easyRandomParameters = easyRandomParameters();
        final EasyRandom generator = new EasyRandom(easyRandomParameters);
        return generator.objects(clazz, count).collect(Collectors.toList());
    }


    public static  <T> List<T> mockWithoutId(final Class<T> clazz, final int count){
        final EasyRandomParameters easyRandomParameters = easyRandomParameters();
        easyRandomParameters.excludeField(FieldPredicates.named("id"));
        final EasyRandom generator = new EasyRandom(easyRandomParameters);
        return generator.objects(clazz, count).collect(Collectors.toList());
    }

    public static EasyRandomParameters easyRandomParameters(){
        final EasyRandomParameters parameters = new EasyRandomParameters();
        parameters.randomize(String.class, () -> UUID.randomUUID().toString());
        parameters.randomize(Currency.class, () -> Currency.getInstance("NGN"));
        parameters.collectionSizeRange(5, 10);
        parameters.dateRange(LocalDate.now().minusMonths(7), LocalDate.now());
        return parameters;
    }
}

