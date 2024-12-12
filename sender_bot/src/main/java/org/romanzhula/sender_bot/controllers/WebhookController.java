package org.romanzhula.sender_bot.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.telegram.telegrambots.meta.api.objects.Update;

@RequiredArgsConstructor
@RestController("/api")
public class WebhookController {

    private final MessageHandlerController messageHandlerController;

    @PostMapping("/callback/update")
    public ResponseEntity<?> onUpdateReceived(
            @RequestBody Update update
    ) {
        messageHandlerController.processUpdate(update);

        return ResponseEntity.ok().build();
    }

}
