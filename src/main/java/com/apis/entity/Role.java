package com.apis.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Table;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name="roles")
public class Role extends BaseEntity{

    private String description;

//    @OneToMany(mappedBy="role", fetch = FetchType.LAZY)
//    private List<User> users = new ArrayList<>();

}
