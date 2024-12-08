package org.romanzhula.rest_dispatcher.components;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.hashids.Hashids;
import org.springframework.stereotype.Component;


@Log4j
@RequiredArgsConstructor
@Component
public class HashidsDecoder {

    private final Hashids hashids;

    public Long getIdFromString(String idAsString) {
        long[] result = hashids.decode(idAsString);
        int length = result.length;

        if (!idAsString.isEmpty() && length > 0) {
            return result[0];
        }

        return null;
    }

}
