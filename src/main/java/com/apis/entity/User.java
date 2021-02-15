package com.apis.entity;

import com.apis.enums.Gender;
import lombok.*;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name="users")
@Where(clause = "is_deleted=false")
public class User extends BaseEntity{


    private String firstName;
    private String lastName;
    private String userName;
    private String password;
    private boolean enabled;
    private String phone;

    @ManyToOne//(fetch = FetchType.EAGER)
    @JoinColumn(name="role_id")
    private Role role;

    @Enumerated(EnumType.STRING)
    private Gender gender;

//    @OneToMany(mappedBy = "assignedManager", fetch = FetchType.LAZY, cascade = {CascadeType.DETACH, CascadeType.MERGE})
//    List<Project> projects=new ArrayList<>();


}
