package io.happium.happium_client_service.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
public class Greeting {

    @Getter @Setter private Long id;
    @Getter @Setter private String content;

}
