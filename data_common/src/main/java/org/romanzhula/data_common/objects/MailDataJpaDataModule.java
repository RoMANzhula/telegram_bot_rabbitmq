package org.romanzhula.data_common.objects;

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
