<!-- Константы для создания пользователя -->
const form_new = document.getElementById('formNewUser');
const role_new = document.querySelector('#roles').selectedOptions;

<!-- Константы для редактирования пользователя -->
const form_ed = document.getElementById('formEditUser');
const id_ed = document.getElementById('edit-id');
const name_ed = document.getElementById('edit-name');
const lastname_ed = document.getElementById('edit-lastname');
const year_ed = document.getElementById('edit-year');
const login_ed = document.getElementById('edit-login');
const email_ed = document.getElementById('edit-email');
const password_ed = document.getElementById('edit-password');

<!-- Константы для удаления пользователя -->
const form_del = document.getElementById('formDeleteUser');
const id_del = document.getElementById('delete-id');
const name_del = document.getElementById('delete-name');
const lastname_del = document.getElementById('delete-lastname');
const year_del = document.getElementById('delete-year');
const login_del = document.getElementById('delete-login');
const email_del = document.getElementById('delete-email');
const password_del = document.getElementById('delete-password');

<!-- Панель админа и таблица с юзерами -->

async function getAdminPage() {
    let page = await fetch('/api/users');

    if(page.ok) {
        let listAllUser = await  page.json();
        loadTableData(listAllUser);
    } else {
        alert(`Error, ${page.status}`)
    }
}
function loadTableData(listAllUser) {
    const tableBody = document.getElementById('adminTable');
    let dataHtml = '';
    for (let user of listAllUser) {
        let roles = [];
        for (let role of user.roles) {
            roles.push(" " + role.role.toString()
                .replaceAll("ROLE_", ""))
        }
        dataHtml +=
            `<tr>
    <td>${user.id}</td>
    <td>${user.name}</td>
    <td>${user.lastName}</td>
    <td>${user.year}</td>
    <td>${user.email}</td>
    <td>${roles}</td>
    <td>
        <button class="btn btn-primary" 
        data-bs-toogle="modal"
        data-bs-target="#editModal" 
        onclick="loadEditData(${user.id})">Edit</button>
    </td>
    <td>
        <button class="btn btn-danger" 
        data-bs-toogle="modal"
        data-bs-target="#deleteModal" 
        onclick="loadDeleteData(${user.id})"
        >Delete</button>
    </td>
</tr>`
    }
    tableBody.innerHTML = dataHtml;
}


<!-- Панель админа и добавление нового пользователя -->

form_new.addEventListener('submit', addNewUser);

async function addNewUser(event) {

    event.preventDefault();
    const urlNew = 'api/admin/new';
    let listOfRole = [];
    for (let i = 0; i < role_new.length; i++) {
        listOfRole.push({
            id:role_new[i].value
        });
    }
    let method = {
        method: 'POST',
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify({
            name: form_new.name.value,
            lastName: form_new.lastname.value,
            year: form_new.year.value,
            email: form_new.email.value,
            login: form_new.login.value,
            password: form_new.password.value,
            roles: listOfRole
        })
    }
    await fetch(urlNew,method).then(() => {
        form_new.reset();
        getAdminPage();

        document.getElementById("nav-newUser-tab").classList.remove("active")
        document.getElementById("nav-newUser-tab").ariaSelected = "false";
        document.getElementById("nav-usersShow-tab").classList.add("active");
        document.getElementById("nav-usersShow-tab").ariaSelected = "true";
        document.getElementById("nav-newUser").classList.remove("show", "active");
        document.getElementById("nav-usersShow").classList.add("show", "active");


    });
}


<!-- Модальное окно для редактирования пользователя  -->
async function loadEditData(id) {
    const  urlDataEd = 'api/admin/' + id;
    let usersPageEd = await fetch(urlDataEd);
    if (usersPageEd.ok) {
        await usersPageEd.json().then(user => {
            id_ed.value = `${user.id}`;
            name_ed.value = `${user.name}`;
            lastname_ed.value = `${user.lastName}`;
            year_ed.value = `${user.year}`;
            login_ed.value = `${user.login}`;
            email_ed.value = `${user.email}`;
            password_ed.value = `${user.password}`;
        })
        document.getElementById("editModal").style.display = "block";
    } else {
        alert(`Error, ${usersPageEd.status}`)
    }
}
async function editUser() {
    console.log(form_ed);
    let urlEdit = 'api/admin/' + id_ed.value;
    let listOfRole = [];
    for (let i=0; i<form_ed.roles.options.length; i++) {
        if (form_ed.roles.options[i].selected) {
            listOfRole.push(form_ed.roles.options[i].value);
        }
    }
    let method = {
        method: 'PATCH',
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify({
            id: id_ed.value,
            name: form_ed.name.value,
            lastName: form_ed.lastname.value,
            year: form_ed.year.value,
            login: form_ed.login.value,
            email: form_ed.email.value,
            password: form_ed.password.value,
            roles: listOfRole
        })
    }
    console.log(urlEdit, method);
    await fetch(urlEdit,method).then(() => {
        document.getElementById("editModal").style.display = "none";
        getAdminPage();
    })
}

<!-- Модальное окно для удаления пользователя  -->
async function loadDeleteData(id) {
    const  urlDataDel = 'api/admin/' + id;
    let usersPageDel = await fetch(urlDataDel);
    if (usersPageDel.ok) {
        await usersPageDel.json().then(user => {
            id_del.value = `${user.id}`;
            name_del.value = `${user.name}`;
            lastname_del.value = `${user.lastName}`;
            year_del.value = `${user.year}`;
            login_del.value = `${user.login}`;
            email_del.value = `${user.email}`;
            password_del.value = `${user.password}`;
        })
        document.getElementById("deleteModal").style.display = "block";
    } else {
        alert(`Error, ${usersPageDel.status}`)
    }
}
async function deleteUser() {
    console.log(form_del);
    let urlDelete = 'api/admin/' + id_del.value;
    let listOfRole = [];
    for (let i=0; i<form_del.roles.options.length; i++) {
        if (form_del.roles.options[i].selected) {
            listOfRole.push(form_del.roles.options[i].value);
        }
    }
    let method = {
        method: 'DELETE',
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify({
            id: id_del.value,
            name: form_del.name.value,
            lastName: form_del.lastname.value,
            year: form_del.year.value,
            login: form_del.login.value,
            email: form_del.email.value,
            password: form_del.password.value,
            roles: listOfRole
        })
    }
    console.log(urlDelete, method);
    await fetch(urlDelete,method).then(() => {
        document.getElementById("deleteModal").style.display = "none";
        getAdminPage();
    })
}
getAdminPage();