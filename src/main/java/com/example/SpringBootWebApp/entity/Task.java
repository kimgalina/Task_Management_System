/**
 * The `Task` class represents a task entity in the Spring Boot web application. It includes
 * attributes such as task content, completion status, user ID, and a unique identifier.
 *
 * The class provides methods to access and modify these attributes.
 */

package com.example.SpringBootWebApp.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serial;
import java.io.Serializable;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "tasks")
public class Task implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "content", nullable = false)
    @JsonProperty("content")
    private String taskContent;

    @Column(columnDefinition = "boolean default false")
    @JsonProperty("isDone")
    private Boolean isDone;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User owner;


}