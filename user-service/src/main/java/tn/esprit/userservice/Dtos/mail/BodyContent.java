package tn.esprit.userservice.Dtos.mail;

import lombok.*;
import lombok.experimental.FieldDefaults;
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BodyContent {
    Long id;
    TypeBody type; //  text/plain or  text/html
    String content;
}