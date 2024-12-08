package org.romanzhula.data_jpa.models;


import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "photos_app")
@Entity
public class PhotoJpaDataModule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String telegramId;

    @OneToOne
    private BinaryJpaDataModule binaryData;

    private Integer fileLength;

    private String hashids;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PhotoJpaDataModule that = (PhotoJpaDataModule) o;

        return Objects.equals(telegramId, that.telegramId) &&
                Objects.equals(binaryData, that.binaryData) &&
                Objects.equals(fileLength, that.fileLength)
        ;
    }

    @Override
    public int hashCode() {
        return Objects.hash(telegramId, binaryData, fileLength);
    }

}
