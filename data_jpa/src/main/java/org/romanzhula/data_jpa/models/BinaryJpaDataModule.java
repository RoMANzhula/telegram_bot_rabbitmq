package org.romanzhula.data_jpa.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.Arrays;
import java.util.Objects;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "binary_data")
@Entity
public class BinaryJpaDataModule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private byte[] convertedFile;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BinaryJpaDataModule that = (BinaryJpaDataModule) o;

        return Objects.deepEquals(convertedFile, that.convertedFile);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(convertedFile);
    }
}
