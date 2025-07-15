package com.jobify.jobportal_backend.Utility;

import com.jobify.jobportal_backend.Entity.Sequence;
import com.jobify.jobportal_backend.Exception.JobPortalException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;

@Component
public class Utilities {

    private  MongoOperations mongoOperations;


    public Utilities(MongoOperations mongoOperations) {
        this.mongoOperations = mongoOperations;
    }

    public  Long getNextSequence(String key) throws JobPortalException {

        Query query = new Query(Criteria.where("_id").is(key));
        Update update = new Update();
        update.inc("seq",1);
        FindAndModifyOptions options = new FindAndModifyOptions();
        options.returnNew(true);
        Sequence seq = mongoOperations.findAndModify(query,update,options, Sequence.class);

        if(seq == null) throw new JobPortalException("Unable to get sequence id for key: "+key);

        return seq.getSeq();


    }


    public static String generateOTP(){
        StringBuilder otp= new StringBuilder();

        SecureRandom random= new SecureRandom();

        for(int i=0;i<6;i++){
            otp.append(random.nextInt(10));
        }


        return otp.toString();

    }

}
