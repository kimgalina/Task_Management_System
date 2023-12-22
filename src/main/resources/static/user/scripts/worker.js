// Waiting for the full document to load
document.addEventListener("DOMContentLoaded", async function () {
    // Obtaining elements of the task list and the new task input field
    const taskList = document.getElementById("taskList");
    const loadingIndicator = document.querySelector(".loadingIndicator");
    const newTaskTextElement = document.getElementById("newTaskText");
    // Obtaining elements for working with the modal window
    const openModalBtn = document.getElementById("open-modal-btn");
    const closeModalBtn = document.getElementById("close-my-modal-btn");
    const modal = document.getElementById("my-modal");
    async function loadTasks() {
        try {
            // Simulating a delay of 3 seconds (3000 milliseconds)
            await new Promise(resolve => setTimeout(resolve, 3000));

            const currentUrl = window.location.href;
            const tasksUrl = `${currentUrl}/get-tasks`;

            const response = await fetch(tasksUrl, {
                method: 'GET',
            });

            if (response.ok) {
                // Получаем задачи с сервера
                const tasks = await response.json();

                // Hide loading indicator
                loadingIndicator.style.display = 'none';

                // Очищаем текущий список задач
                taskList.innerHTML = "";

                // Отображаем новый список задач
                tasks.forEach(task => {
                    const taskContent = task.taskContent;
                    const isDone = task.done;

                    // Используем переменные в вашем коде
                    console.log("Task Content:", taskContent);
                    console.log("Is Done:", isDone);

                    // Теперь вы можете передать эти значения в вашу функцию createTaskElement
                    const taskElement = createTaskElement(taskContent, isDone);
                    taskList.appendChild(taskElement);
                });

                console.log("Tasks loaded from the server");
            } else {
                console.error('Failed to load tasks');
            }
        } catch (error) {
            console.error('Error during tasks loading:', error);
        }
    }

    // Show loading indicator
    loadingIndicator.style.display = 'block';
    // Загружаем задачи с сервера после загрузки страницы
    await loadTasks();

    // Functions for opening and closing the modal window
    function openModal() {
        console.log("Opening modal");
        modal.classList.add("open");
    }

    function closeModal() {
        console.log("Closing modal");
        modal.classList.remove("open");
    }

    // Assigning event handlers for buttons and the modal window
    openModalBtn.addEventListener("click", openModal);
    closeModalBtn.addEventListener("click", closeModal);

    // Preventing the modal window from closing when clicking inside its content
    modal.querySelector(".modal__box").addEventListener("click", (event) => {
        console.log("Inside modal box");
        event.stopPropagation();
    });

    // Closing the modal window when clicking outside its content
    modal.addEventListener("click", () => {
        console.log("Inside modal");
        closeModal();
    });

    // Function to create a task element
    function createTaskElement(taskText, isChecked) {
        const taskElement = document.createElement("li");
        taskElement.className = "task";
        taskElement.innerHTML = `
            <input type="checkbox" ${isChecked ? 'checked' : ''}>
            <span>${taskText}</span>
            <button onclick="editTask(this)">Edit</button>
            <button onclick="deleteTask(this)">Delete</button>
        `;
        return taskElement;
    }

    // Function to add a new task
    window.addTask = async function () {
        const newTaskText = newTaskTextElement.value.trim();
        if (newTaskText !== "") {
            const isChecked = false; // New tasks are always unchecked
            const taskElement = createTaskElement(newTaskText, isChecked);
            taskList.appendChild(taskElement);
            newTaskTextElement.value = "";

            // Sending task content to the server
            try {
                const currentUrl = window.location.href;
                const newUrl = `${currentUrl}/new-task`;

                const response = await fetch(newUrl, {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/x-www-form-urlencoded',
                    },
                    body: `taskContent=${encodeURIComponent(newTaskText)}`,
                });

                if (response.ok) {
                    console.log("OK post");
                } else {
                    console.error('Failed to add task');
                }
            } catch (error) {
                console.error('Error during POST request:', error);
            }
        }
    };

    // Function to edit a task
    window.editTask = async function (button) {
        const taskElement = button.parentElement;
        const taskText = taskElement.querySelector("span").innerText;
        const newText = prompt("Edit task:", taskText);
        if (newText !== null) {
            taskElement.querySelector("span").innerText = newText;

            // Server request to edit the task
            try {
                const currentUrl = window.location.href;
                const updateUrl = `${currentUrl}/edit-task`;
                console.log(updateUrl);

                const formData = new FormData();
                formData.append('taskNewContent', newText);
                formData.append('taskOldContent', taskText);

                const response = await fetch(updateUrl, {
                    method: 'PATCH',
                    body: formData,
                });

                if (response.ok) {
                    console.log("OK patch");
                } else {
                    console.error('Failed to update task');
                }
            } catch (error) {
                console.error('Error during PATCH request:', error);
            }
        }
    };

    // Function to delete a task
    window.deleteTask = async function (button) {
        const taskElement = button.parentElement;
        const taskText = taskElement.querySelector("span").innerText;
        taskList.removeChild(taskElement);

        // Server request to delete the task
        try {
            const currentUrl = window.location.href;
            const updateUrl = `${currentUrl}/delete-task/${taskText}`;
            console.log(updateUrl);

            const response = await fetch(updateUrl, {
                method: 'DELETE',
            });

            if (response.ok) {
                console.log("OK delete");
            } else {
                console.error('Failed to delete task');
            }
        } catch (error) {
            console.error('Error during DELETE request:', error);
        }
    };

    // Function to filter tasks
    window.filterTasks = async function (filterType) {
        // Sending a server request with the filterType parameter
        try {
            const currentUrl = window.location.href;
            const filterUrl = `${currentUrl}/filter-tasks?filterType=${filterType}`;

            const response = await fetch(filterUrl, {
                method: 'GET',
            });

            if (response.ok) {
                // Getting the task list from the server
                const tasks = await response.json();
                console.log(tasks);
                // Clearing the current task list
                taskList.innerHTML = "";

                // Displaying the new task list
                tasks.forEach(task => {
                    const taskContent = task.taskContent;
                    const isDone = task.done;

                    // Using variable values in your code
                    console.log("Task Content:", taskContent);
                    console.log("Is Done:", isDone);

                    // Now you can pass these values to your createTaskElement function
                    const taskElement = createTaskElement(taskContent, isDone);
                    taskList.appendChild(taskElement);
                });

                console.log(`Tasks filtered by: ${filterType}`);
            } else {
                console.error('Failed to filter tasks');
            }
        } catch (error) {
            console.error('Error during filter request:', error);
        }
    };
});

// Function to redirect to the login page
function redirectToSignIn() {
    // Getting the current path
    var currentPath = window.location.pathname;
    console.log(currentPath);
    // Adding "/login" to the current path and going up one level (..)
    var loginPath = currentPath.replace(/\/user\/\d+/, "/login");
    console.log(loginPath);
    // Redirecting the user
    window.location.href = loginPath;
}

// Function to update the task status
async function updateTaskStatus(checkbox) {
    const isChecked = checkbox.checked;
    // Getting the parent element (li)
    const listItem = checkbox.parentNode;

    // Finding the <span> element inside the parent element
    const taskContentElement = listItem.querySelector('span');

    // Getting the text from the <span> element
    const taskContent = taskContentElement.textContent;
    console.log("task content = " + taskContent);

    try {
        const currentUrl = window.location.href;
        const updateUrl = `${currentUrl}/updateTaskStatus`;
        console.log(updateUrl);

        const formData = new FormData();
        formData.append('taskContent', taskContent);
        formData.append('isDone', isChecked);

        const response = await fetch(updateUrl, {
            method: 'PATCH',
            body: formData,
        });

        if (response.ok) {
            // Your code for a successful update
            console.log("OK patch");
        } else {
            // Your code for handling errors
            console.error('Failed to update task');
        }
    } catch (error) {
        console.error('Error during PATCH request:', error);
    }
}


