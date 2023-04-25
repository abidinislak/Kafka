package com.kafkaproducer.domain;


import com.kafkaproducer.utils.LiabraryEventType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LibraryEvent {

    private Integer LibraryEventid;
    private LiabraryEventType liabraryEventType;
    private Book book;
}
