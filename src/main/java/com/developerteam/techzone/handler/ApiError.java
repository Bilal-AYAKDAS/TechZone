package com.developerteam.techzone.handler;

import com.developerteam.techzone.exception.CustomException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ApiError <E>{
    private Integer status;
    private CustomException<E> exception;
}
