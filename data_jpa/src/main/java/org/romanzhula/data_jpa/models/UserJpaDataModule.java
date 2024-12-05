package org.romanzhula.data_jpa.models;


import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.romanzhula.data_jpa.models.enums.UserState;

import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users_app")
@Entity
public class UserJpaDataModule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long telegramId;

    @CreationTimestamp
    private LocalDateTime firstLoginDateTime;

    private String firstName;

    private String lastName;

    private String userName;

    private String email;

    private Boolean isActive;

    @Enumerated(EnumType.STRING)
    private UserState userState;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserJpaDataModule userJpaDataModule = (UserJpaDataModule) o;

        return Objects.equals(telegramId, userJpaDataModule.telegramId) &&
                Objects.equals(firstLoginDateTime, userJpaDataModule.firstLoginDateTime) &&
                Objects.equals(userName, userJpaDataModule.userName) &&
                Objects.equals(email, userJpaDataModule.email)
        ;
    }

    @Override
    public int hashCode() {
        return Objects.hash(telegramId, firstLoginDateTime, userName, email);
    }

}
