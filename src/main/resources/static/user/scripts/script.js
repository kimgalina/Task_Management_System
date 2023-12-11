const inputFields = document.querySelectorAll(".input-field textarea"),
  todoListsArray = document.querySelectorAll(".todoLists"),
  clearButtons = document.querySelectorAll(".clear-button"), 
  pendingNum = document.querySelector(".pending-num"),
  filterOptionsArray = document.querySelectorAll(".filter-options");

let currentFilter = "all";
  
function filterTasks(filter) {
  currentFilter = filter;
  updateTaskList();
}

function updateTaskList() {
  const allTasks = document.querySelectorAll(".list");

  allTasks.forEach((task) => {
    const isCompleted = task.classList.contains("completed");
    const isUncompleted = task.classList.contains("pending");

    if (
      (currentFilter === "all") ||
      (currentFilter === "completed" && isCompleted) ||
      (currentFilter === "uncompleted" && isUncompleted)
    ) {
      task.style.display = "flex";
    } else {
      task.style.display = "none";
    }
  });
}

function allTasks() {
  let tasks = document.querySelectorAll(".pending");

  pendingNum.textContent = tasks.length === 0 ? "no" : tasks.length;

  let allLists = document.querySelectorAll(".list");
  if (allLists.length > 0) {
    todoLists.style.marginTop = "20px";
    clearButton.style.pointerEvents = "auto";
    return;
  }
  todoLists.style.marginTop = "0px";
  clearButton.style.pointerEvents = "none";
}

inputFields.forEach((inputField, index) => {
  inputField.addEventListener("keyup", async (e) => {
    let inputVal = inputField.value.trim();

    if (e.key === "Enter" && inputVal.length > 0) {
      // отправляем содержимое task на сервер
      try {
        const currentUrl = window.location.href;
        const newUrl = `${currentUrl}/new-task`;

        const response = await fetch(newUrl, {
          method: 'POST',
          headers: {
            'Content-Type': 'application/x-www-form-urlencoded', // изменено на форму
          },
          body: `taskContent=${encodeURIComponent(inputVal)}`, // изменено на форму
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
      let liTag = `<li class="list pending" onclick="handleStatus(this)">
        <input type="checkbox" />
        <span class="task">${inputVal}</span>
        <i class="uil uil-trash" onclick="deleteTask(this)"></i>
      </li>`;

      todoListsArray[index].insertAdjacentHTML("beforeend", liTag);
      inputField.value = "";
      allTasks();
      updateTaskList();
    }
  });
});

function handleStatus(e) {
  const checkbox = e.querySelector("input"); 
  checkbox.checked = !checkbox.checked;
  e.classList.toggle("completed");
  e.classList.toggle("pending");
  allTasks();
  updateTaskList();
}

function deleteTask(e) {
  e.parentElement.remove(); 
  allTasks();
  updateTaskList();
}
updateTaskList();

document.getElementById("open-modal-btn").addEventListener("click", function() {
  document.getElementById("my-modal").classList.add("open")
})

document.getElementById("close-my-modal-btn").addEventListener("click", function() {
  document.getElementById("my-modal").classList.remove("open")
})

document.querySelector("#my-modal .modal__box").addEventListener('click', event => {
  event._isClickWithInModal = true;
});
document.getElementById("my-modal").addEventListener('click', event => {
  if (event._isClickWithInModal) return;
  event.currentTarget.classList.remove('open');
});

const openTodoButtons = document.querySelectorAll(".open-todo-btn");
const closeTodoButtons = document.querySelectorAll(".todo__close-btn");

closeTodoButtons.forEach(button => {
  button.addEventListener("click", function() {
    document.getElementById("my-todo").classList.remove("open");
  });
});

openTodoButtons.forEach(button => {
  button.addEventListener("click", function() {
    document.getElementById("my-todo").classList.add("open");
  });
});

document.getElementById("open-todo-btn").addEventListener("click", function() {
  document.getElementById("my-todo").classList.add("open")
})

document.getElementById("todo__close-btn").addEventListener("click", function() {
  document.getElementById("my-todo").classList.remove("open")
})

document.querySelector("#my-todo .todo__box").addEventListener('click', event => {
  event._isClickWithInTodo = true;
});

document.getElementById("my-todo").addEventListener('click', event => {
  if (event._isClickWithInTodo) return;
  event.currentTarget.classList.remove('open');
});

function redirectToSignIn() {
  // Получаем текущий путь
  var currentPath = window.location.pathname;

  // Добавляем "/signup" к текущему пути и переносимся на уровень вверх (..)
  var loginPath = currentPath.replace(/\/user\/\d+/, "/login");

  // Перенаправляем пользователя
  window.location.href =loginPath;
}

