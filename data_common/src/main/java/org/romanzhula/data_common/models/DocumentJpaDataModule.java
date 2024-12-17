package org.romanzhula.data_common.models;


import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "documents_app")
@Entity
public class DocumentJpaDataModule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String telegramId;

    private String name;

    @OneToOne
    private BinaryJpaDataModule binaryData;

    private String mimeType;

    private Long fileSize;

    private String hashids;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DocumentJpaDataModule that = (DocumentJpaDataModule) o;

        return Objects.equals(telegramId, that.telegramId) &&
                Objects.equals(name, that.name) &&
                Objects.equals(binaryData, that.binaryData) &&
                Objects.equals(mimeType, that.mimeType) &&
                Objects.equals(fileSize, that.fileSize)
        ;
    }

    @Override
    public int hashCode() {
        return Objects.hash(telegramId, name, binaryData, mimeType, fileSize);
    }

}
