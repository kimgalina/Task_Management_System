document.addEventListener("DOMContentLoaded", function () {
  // Получение элементов интерфейса
  const openModalBtn = document.getElementById("open-modal-btn");
  const closeModalBtn = document.getElementById("close-my-modal-btn");
  const modal = document.getElementById("my-modal");

  // Функции для открытия и закрытия модального окна
  function openModal() {
    console.log("Opening modal");
    modal.classList.add("open");
  }

  function closeModal() {
    console.log("Closing modal");
    modal.classList.remove("open");
  }

  // Обработчики событий для кнопок и модального окна
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

  // Получение элементов списка задач и ввода новой задачи
  const taskList = document.getElementById("taskList");
  const newTaskTextElement = document.getElementById("newTaskText");

  // Функция для создания элемента задачи
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

  // Функция для добавления новой задачи
  window.addTask = async function () {
    const newTaskText = newTaskTextElement.value.trim();
    if (newTaskText !== "") {
      const isChecked = false; // Новая задача всегда не выполнена
      const taskElement = createTaskElement(newTaskText, isChecked);
      taskList.appendChild(taskElement);
      newTaskTextElement.value = "";

      // Отправка содержимого задачи на сервер
      try {
        const currentUrl = window.location.href;
        const newUrl = `${currentUrl}/new-task`;

        const response = await fetch(newUrl, {
          method: 'POST',
          headers: {
            'Content-Type': 'application/x-www-form-urlencoded', // изменено на форму
          },
          body: `taskContent=${encodeURIComponent(newTaskText)}`, // изменено на форму
        });

        if (response.ok) {
          // Ваш код обработки успешного ответа (если необходимо)
          console.log("OK post");
        } else {
          // Ваш код обработки ошибки
          console.error('Failed to add task');
        }
      } catch (error) {
        console.error('Error during POST request:', error);
      }
    }
  };

  // Функция для редактирования задачи
  window.editTask = async function (button) {
    const taskElement = button.parentElement;
    const taskText = taskElement.querySelector("span").innerText;
    const newText = prompt("Edit task:", taskText);
    if (newText !== null) {
      taskElement.querySelector("span").innerText = newText;

      // Запрос на сервер для изменения задачи
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
          // Ваш код для успешного обновления
          console.log("OK patch");
        } else {
          // Ваш код для обработки ошибок
          console.error('Failed to update task');
        }
      } catch (error) {
        console.error('Error during PATCH request:', error);
      }
    }
  };

  // Функция для удаления задачи
  window.deleteTask = async function (button) {
    const taskElement = button.parentElement;
    const taskText = taskElement.querySelector("span").innerText;
    taskList.removeChild(taskElement);

    // Запрос на сервер для удаления задачи
    try {
      const currentUrl = window.location.href;

      const updateUrl = `${currentUrl}/delete-task/${taskText}`;
      console.log(updateUrl);

      const response = await fetch(updateUrl, {
        method: 'DELETE',
      });

      if (response.ok) {
        // Ваш код для успешного удаления
        console.log("OK delete");
      } else {
        // Ваш код для обработки ошибок
        console.error('Failed to delete task');
      }
    } catch (error) {
      console.error('Error during DELETE request:', error);
    }
  };

  // Функция для фильтрации задач
  window.filterTasks = async function (filterType) {
    // Отправка запроса на сервер с параметром filterType
    try {
      const currentUrl = window.location.href;
      const filterUrl = `${currentUrl}/filter-tasks?filterType=${filterType}`;

      const response = await fetch(filterUrl, {
        method: 'GET',
      });

      if (response.ok) {
        // Получение списка задач с сервера
        const tasks = await response.json();
        console.log(tasks);
        // Очистка текущего списка задач
        taskList.innerHTML = "";

        // Отображение нового списка задач
        tasks.forEach(task => {
          const taskContent = task.taskContent;
          const isDone = task.done;

          // Используйте значения переменных в вашем коде
          console.log("Task Content:", taskContent);
          console.log("Is Done:", isDone);

          // Теперь вы можете передать эти значения в вашу функцию createTaskElement
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

// Функция для перенаправления на страницу входа
function redirectToSignIn() {
  // Получение текущего пути
  var currentPath = window.location.pathname;
  console.log(currentPath);
  // Добавление "/signup" к текущему пути и переход на уровень вверх (..)
  var loginPath = currentPath.replace(/\/user\/\d+/, "/login");
  console.log(loginPath);
  // Перенаправление пользователя
  window.location.href = loginPath;
}

// Функция для обновления статуса задачи
async function updateTaskStatus(checkbox) {
  const isChecked = checkbox.checked;
  // Получение родительского элемента (li)
  const listItem = checkbox.parentNode;

  // Нахождение элемента <span> внутри родительского элемента
  const taskContentElement = listItem.querySelector('span');

  // Получение текста из элемента <span>
  const taskContent = taskContentElement.textContent;
  console.log("task content = " + taskContent);
  // Запрос на сервер для изменения задачи
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
      // Ваш код для успешного обновления
      console.log("OK patch");
    } else {
      // Ваш код для обработки ошибок
      console.error('Failed to update task');
    }
  } catch (error) {
    console.error('Error during PATCH request:', error);
  }
}
