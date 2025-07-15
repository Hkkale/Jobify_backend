package com.jobify.jobportal_backend.Entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Document(collection = "sequence")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Sequence {


    @Id
    private String id ;
    private Long seq ;
}
