# Task Management System

## Table of Contents:
1. [Introduction](#introduction)
2. [Features](#features)
3. [Requirements](#requirements)
4. [Installation](#installation)
5. [Usage](#usage)
6. [How to Use](#how-to-use)
7. [Code Structure](#code-structure)
8. [Authors](#authors)

## Introduction
The Task Management System is a comprehensive web application built with Spring Boot, a relational database, and front-end markup. It provides a platform for efficient task management within an organization, featuring different account types such as worker, manager, and director.

## Features
- User account management (worker, manager, director)
- Task creation, assignment, and tracking
- Role-based access control
- Database integration for persistent data storage
- Responsive front-end design

## Requirements
- Java Development Kit (JDK) 8 or higher
- Spring Boot framework
- Relational database (e.g., MySQL, PostgreSQL)
- Front-end markup (HTML, CSS, JavaScript)

## Installation
1. Clone the repository: `https://github.com/kimgalina/Task_Management_System.git`
2. Navigate to the project directory: `cd task-management-system`
3. Configure the database settings in `application.properties`.
4. Build and run the application: `./mvnw spring-boot:run`

## Usage
- Access the application through a web browser.
- Log in with your account credentials.
- Explore the dashboard, create tasks, and manage your workload.

## How to Use
1. **Account Types:**
    - Worker: Manages and completes assigned tasks.
    - Manager: Creates tasks, assigns them to workers, and monitors progress.
    - Director: Has access to overall task and account management.

2. **Task Management:**
    - Create tasks with detailed descriptions.
    - Assign tasks to specific workers or leave unassigned for managers to distribute.
    - Track task status and completion.

3. **Role-Based Access:**
    - Workers can view and manage their tasks.
    - Managers can create tasks, assign them, and monitor progress.
    - Directors have access to all aspects of task and account management.

## Code Structure
The project follows a modular structure to enhance maintainability and readability. Key classes include:
- **Application:** Main class for initializing and running the Spring Boot application.
- **Controller:** Manages HTTP requests, handles user input, and interacts with services.
- **Service:** Contains business logic, interacts with the database, and handles task-related operations.
- **Repository:** Manages data access and database interactions.

## Authors
1. [Galina Kim](https://t.me/genriettakim)
2. [Kylychbek Parpiev](https://t.me/vkusnoochennn)
3. [Isa Kasymbek uulu](https://t.me/jes_sues)

Contributions and feedback are appreciated. Feel free to open issues or submit pull requests.