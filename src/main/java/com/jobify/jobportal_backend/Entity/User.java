package com.jobify.jobportal_backend.Entity;

import com.jobify.jobportal_backend.DTOs.AccountType;
import com.jobify.jobportal_backend.DTOs.UserDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;



@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "users")
public class User {
    @Id
    private Long id;
    private String name;

    @Indexed(unique = true)
    private String email;
    private String password;

    private AccountType accountType;


    public UserDto toDto(){
        return  new UserDto(this.id,this.name,this.email,this.password,this.accountType);
    }


}
