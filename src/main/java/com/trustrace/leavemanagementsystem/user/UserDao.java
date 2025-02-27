package com.trustrace.leavemanagementsystem.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserDao {
    @Autowired
    private MongoTemplate mt;

    public void saveUser(User user){
        mt.save(user);
    }

    public User getUserByEmail(String email) {
        Query query = new Query(Criteria.where("email").is(email));
        return mt.findOne(query, User.class);
    }

    public List<User> getAllUsers() {
        return mt.findAll(User.class);
    }

    public User getUserById(String id) {
        if(id == null) return null;
        return mt.findById(id, User.class);
    }

    public User createUser(User user) {
        return mt.save(user);
    }

    public boolean deleteUserById(String id) {
        User user = getUserById(id);
        if(user == null) {
            return false;
        }
        mt.remove(user);
        return true;
    }

    public Page<User> getUsersOfPage(int page, int size) {
        Query query = new Query();
        long totalCount = mt.count(query, User.class);

        Pageable pageable = PageRequest.of(page, size);
        query.with(pageable);

        List<User> responses = mt.find(query, User.class);

        return new PageImpl<>(responses, pageable, totalCount);
    }

    public List<User> searchUserByName(String name) {
        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.project("id", "firstName", "lastName", "designation", "department")
                        .andExpression("concat(firstName, ' ', lastName)").as("fullName"),

                Aggregation.match(Criteria.where("fullName").regex(name, "i"))
        );
        AggregationResults<User> results = mt.aggregate(aggregation, "users", User.class);
        return results.getMappedResults();
    }
}
