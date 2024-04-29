document.addEventListener("DOMContentLoaded", async function () {
  // Get interface elements
  const openModalBtn = document.getElementById("open-modal-btn");
  const closeModalBtn = document.getElementById("close-my-modal-btn");
  const modal = document.getElementById("my-modal");
  // Get task list elements and new task input
  const taskList = document.getElementById("taskList");
  const newTaskTextElement = document.getElementById("newTaskText");
  const currentUserId;

  async function loadTasks() {
    console.log("loading tasks");
    try {
      const pathParts = window.location.pathname.split('/');
      currentUserId = pathParts[pathParts.indexOf('users') + 1];

      console.log("CURRENT USER ID " + currentUserId);
      const response = await fetch("/tasks/" + currentUserId + "/get-tasks", {
        method: 'GET',
      });

      if (response.ok) {
        const tasks = await response.json();
        loadingIndicator.style.display = 'none';
        taskList.innerHTML = "";
        tasks.forEach(task => {
          const taskContent = task.taskContent;
          const isDone = task.done;

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

  await loadTasks();


  function openModal() {
    console.log("Opening modal");
    modal.classList.add("open");
  }

  function closeModal() {
    console.log("Closing modal");
    modal.classList.remove("open");
  }

  // Event listeners for buttons and modal window
  openModalBtn.addEventListener("click", openModal);
  closeModalBtn.addEventListener("click", closeModal);

  modal.querySelector(".modal__box").addEventListener("click", (event) => {
    console.log("Inside modal box");
    event.stopPropagation();
  });

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
  window.addTask = async function (userId) {
    const newTaskText = newTaskTextElement.value.trim();
    if (newTaskText !== "") {
      const isChecked = false; // New tasks are always unchecked
      const taskElement = createTaskElement(newTaskText, isChecked);
      taskList.appendChild(taskElement);
      newTaskTextElement.value = "";

      // Send task content to the server
      try {
        const currentUrl = window.location.href;
        const newUrl = "http://localhost:7777/tasks";
        const bodyData = JSON.stringify({
          taskContent: newTaskText,
          userId: userId
        });

        const response = await fetch(newUrl, {
          method: 'POST',
          headers: {
            'Content-Type': 'application/json',
          },
          body: bodyData
        });

        if (response.ok) {
          // Your code for handling a successful response (if necessary)
          console.log("OK post");
        } else {
          // Your code for handling errors
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
        // Your code for a successful deletion
        console.log("OK delete");
      } else {
        // Your code for handling errors
        console.error('Failed to delete task');
      }
    } catch (error) {
      console.error('Error during DELETE request:', error);
    }
  };

  // Function to filter tasks
  window.filterTasks = async function (filterType) {
    console.log("CURRENT USER " + currentUserId);
    console.log("Filter type " + filterType);
//    try {
//      const currentUrl = window.location.href;
//      const filterUrl = "/tasks/" + userId + `/filter?filterType=${filterType}`;
//
//      const response = await fetch(filterUrl, {
//        method: 'GET',
//      });
//
//      if (response.ok) {
//        // Get the task list from the server
//        const tasks = await response.json();
//        console.log(tasks);
//        // Clear the current task list
//        taskList.innerHTML = "";
//
//        // Display the new task list
//        tasks.forEach(task => {
//          const taskContent = task.taskContent;
//          const isDone = task.done;
//
//          // Use the variable values in your code
//          console.log("Task Content:", taskContent);
//          console.log("Is Done:", isDone);
//
//          // Now you can pass these values to your createTaskElement function
//          const taskElement = createTaskElement(taskContent, isDone);
//          taskList.appendChild(taskElement);
//        });
//
//        console.log(`Tasks filtered by: ${filterType}`);
//      } else {
//        console.error('Failed to filter tasks');
//      }
//    } catch (error) {
//      console.error('Error during filter request:', error);
//    }
  };
});

// Function to redirect to the login page
function redirectToSignIn() {
  // Get the current path
  var currentPath = window.location.pathname;
  console.log(currentPath);
  // Add "/login" to the current path and go up one level (..)
  var loginPath = currentPath.replace(/\/user\/\d+/, "/login");
  console.log(loginPath);
  // Redirect the user
  window.location.href = loginPath;
}

// Function to update task status
async function updateTaskStatus(checkbox) {
  const isChecked = checkbox.checked;
  // Get the parent element (li)
  const listItem = checkbox.parentNode;

  // Find the <span> element inside the parent element
  const taskContentElement = listItem.querySelector('span');

  // Get the text from the <span> element
  const taskContent = taskContentElement.textContent;
  console.log("task content = " + taskContent);
  // Server request to update the task
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
