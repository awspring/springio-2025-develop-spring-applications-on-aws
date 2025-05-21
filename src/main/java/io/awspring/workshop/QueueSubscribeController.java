package io.awspring.workshop;

import io.awspring.workshop.service.QueueManagingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/queue")
public class QueueSubscribeController {

    private final QueueManagingService queueManagingService;

    public QueueSubscribeController(QueueManagingService queueManagingService) {
        this.queueManagingService = queueManagingService;
    }

    @PostMapping("/subscribe")
    public ResponseEntity<String> subscribe(@RequestBody String queueName) {
        var id = queueManagingService.createAndStartListener(queueName);
        return ResponseEntity.ok("Queue listener started with id: " + id);
    }


    @PostMapping("/unsubscribe")
    public ResponseEntity<String> unsubscribe(@RequestBody String queueName) {
        var id = queueManagingService.stopListener(queueName);
        return ResponseEntity.ok("Queue listener stopped with id: " + id);
    }
}
