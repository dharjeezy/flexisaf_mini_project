package com.example.flexisaf.db.repository;

import com.example.flexisaf.db.model.Student;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.repository.support.PageableExecutionUtils;

/**
 * Damilare
 * 22/11/2021
 **/
public class CustomStudentRepositoryImpl implements CustomStudentRepository {

    private MongoTemplate mongoTemplate;

    public CustomStudentRepositoryImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public Page<Student> findByFilters(HashMap<String, Object> filters, LocalDateTime from, LocalDateTime to, Pageable pageable) {
        Query query = new Query();
        for (String filter : filters.keySet()) {
            if (filters.get(filter) instanceof Collection)
                query.addCriteria(Criteria.where(filter).in((Collection<?>) filters.get(filter)));
            else
                query.addCriteria(Criteria.where(filter).is(filters.get(filter)));
        }

        //apply date filters
        if (from != null && to != null) {
            query.addCriteria(Criteria.where("createdDateTime").lte(to).gte(from));
        } else if (from != null) {
            query.addCriteria(Criteria.where("createdDateTime").gte(from));
        } else if (to != null) {
            query.addCriteria(Criteria.where("createdDateTime").lte(to));
        }
        query.with(pageable);

        List<Student> businessTransactionList = mongoTemplate.find(query, Student.class);
        return PageableExecutionUtils.getPage(businessTransactionList, pageable,
                () -> mongoTemplate.count(query, Student.class));
    }

}
