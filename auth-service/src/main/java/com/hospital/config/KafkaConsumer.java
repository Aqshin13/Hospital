package com.hospital.config;

import com.hospital.dto.event.UserCompleteEvent;
import com.hospital.entity.User;
import com.hospital.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import tools.jackson.databind.ObjectMapper;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class KafkaConsumer {


    private final ObjectMapper objectMapper;
    private final UserRepository userRepository;

    @KafkaListener(
            topics = "user-complete.topic",
            groupId = "doctor-service-group"
    )
    public void consume(ConsumerRecord<String, String> record) {
        String payload = record.value();
        UserCompleteEvent event =
                objectMapper.convertValue(payload, UserCompleteEvent.class);
        Optional<User> user = userRepository
                .findById(event.authId());
        User userDB = user.get();
        userDB.setCompleted(true);
        userRepository.save(userDB);

    }


}
