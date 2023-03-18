package com.serach.blog.model.entity;

import lombok.*;

import javax.persistence.*;

@Getter @Setter @ToString
@Entity @Table
@NoArgsConstructor @AllArgsConstructor
@Builder
public class PopularKeyword extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String keyword;

    @Column(nullable = false)
    @Builder.Default
    private int count = 0;
}
