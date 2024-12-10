package org.romanzhula.data_jpa.objects;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MailDataJpaDataModule {

    private String id;

    private String emailAddress;

}
