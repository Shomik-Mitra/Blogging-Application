package com.blogging.app.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.*;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class UserDto {


    private int id;
    @NotEmpty
    @Size(min = 4,message = "Name should be atleast of 4 character")
    private String name;
    @NotEmpty(message = "Email must not be empty!!")
    @Email(message = "Email must be valid!!")
    private String emailId;
    @NotEmpty
    @Size(min = 5 ,max = 12,message = "Password should be alteast of 4 to 12 characters")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[#$@!%&*?])[A-Za-z\\d#$@!%&*?]{6,}$",message = "Password must be valid")
    private String password;
    @NotEmpty
    private String about;


    private List<RoleDto> roles=new ArrayList<>();
}
