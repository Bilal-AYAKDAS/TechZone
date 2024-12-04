package com.developerteam.techzone.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public enum MessageType {
    NO_RECORD_EXIST("1001","Record not found."),
    GENERAL_EXCEPTION("999","Ther is a general error");
    private String code;
    private String message;
}
