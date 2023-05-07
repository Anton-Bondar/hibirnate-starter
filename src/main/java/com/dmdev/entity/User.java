package com.dmdev.entity;

import com.dmdev.converter.BirthdayConverter;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "users", schema = "public")
public final class User {
    @Id
    private String username;
    private String firstname;
    private String lastname;

//    @Convert(converter = BirthdayConverter.class)
    @Column(name = "birth_date")
    private Birthday birthDate;
    @Enumerated(EnumType.STRING)
    private Role role;
    @JdbcTypeCode(SqlTypes.JSON)
    private String info;
}
