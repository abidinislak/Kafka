package com.eventconsumer.eventconsumer.entity;


import jakarta.persistence.*;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class LibraryEvent {
    @Id
    private String LibraryEventid;
    @Enumerated(EnumType.STRING)
    private LiabraryEventType liabraryEventType;

    @OneToOne(mappedBy = "libraryEvent",cascade = CascadeType.ALL)
    @ToString.Exclude
    private Book book;
}
