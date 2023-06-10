package com.aueb.issues.web.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class CommentDTO {
    private String content;
    private LocalDateTime dateTime;
    private UserDTO user;
}

