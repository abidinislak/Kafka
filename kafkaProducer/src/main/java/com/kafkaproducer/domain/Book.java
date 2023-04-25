package com.kafkaproducer.domain;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Book {


    private Integer boodid;

    private String name;

    private String bookAuthor;
}
