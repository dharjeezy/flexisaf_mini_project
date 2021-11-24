package com.example.flexisaf.db.repository;

/**
 * Damilare
 * 24/11/2021
 **/


import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@Ignore
@DataMongoTest()
@RunWith(SpringRunner.class)
@ActiveProfiles("test")
public class BaseRepositoryTest {

    @TestConfiguration
    static class TestMongoConfig{

    }
}
