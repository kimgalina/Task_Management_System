/**
 * The `Task` class represents a task entity in the Spring Boot web application. It includes
 * attributes such as task content, completion status, user ID, and a unique identifier.
 *
 * The class provides methods to access and modify these attributes.
 */

package com.example.SpringBootWebApp.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
@Table(name = "tasks")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "content", nullable = false)
    private String taskContent;

    @Column(columnDefinition = "boolean default false")
    private Boolean isDone;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User owner;



}