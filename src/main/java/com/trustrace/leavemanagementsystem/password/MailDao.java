package com.trustrace.leavemanagementsystem.password;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class MailDao {
    @Autowired
    private MongoTemplate mongoTemplate;

    public void save(PasswordResetToken token) {
        mongoTemplate.save(token);
    }

    public Optional<PasswordResetToken> findByToken(String token) {
        return Optional.ofNullable(
                mongoTemplate.findOne(
                        Query.query(Criteria.where("token").is(token)),
                        PasswordResetToken.class
                )
        );
    }

    public void delete(String token) {
        mongoTemplate.remove(Query.query(Criteria.where("token").is(token)), PasswordResetToken.class);
    }
}
