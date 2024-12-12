document.addEventListener("DOMContentLoaded", function () {
    const btnAddUser = document.getElementById("btnAddUser");
    const overlayAddUser = document.getElementById("overlayAddUser");
    const btnCloseAddOverlay = document.getElementById("btnCloseAddOverlay");
    const btnCloseEditOverlay = document.getElementById("btnCloseEditOverlay");

    btnAddUser.addEventListener("click", () => {
        overlayAddUser.style.display = "flex";
    });

    btnCloseAddOverlay.addEventListener("click", () => {
        overlayAddUser.style.display = "none";
    });

    btnCloseEditOverlay.addEventListener("click", () => {
        document.getElementById('overlayEditUser').style.display = "none";
    });

    // Close overlay when clicking outside of it
    overlayAddUser.addEventListener("click", (event) => {
        if (event.target === overlayAddUser) {
            overlayAddUser.style.display = "none";
        }
    });
});

document.querySelector('form').addEventListener('submit', function (e) {
    const username = document.getElementById('username').value;
    const email = document.getElementById('email').value;
    const phone = document.getElementById('phone').value;
    const role = document.getElementById('role').value;

    if (!username) {
        alert('Username is required.');
        e.preventDefault();
    } else if (!email || !email.includes('@')) {
        alert('Please provide a valid email address.');
        e.preventDefault();
    } else if (!phone || isNaN(phone)) {
        alert('Phone must be a valid number.');
        e.preventDefault();
    } else if (!role) {
        alert('Please select a role.');
        e.preventDefault();
    }
});

function openDeleteForm(userId) {
    const confirmDelete = confirm("Are you sure you want to delete this user?");
    if (confirmDelete) {
        window.location.href = `/admin/UserManage/delete?id=${userId}`;
    }
}

function openEditForm(userId) {
    fetch(`/admin/UserManage/edit?id=${userId}`)
        .then(response => response.json())
        .then(data => {
            document.getElementById('userIde').value = data.id;
            document.getElementById('usernamee').value = data.username;
            document.getElementById('passworde').value = data.password;
            document.getElementById('fullNamee').value = data.fullName;
            document.getElementById('emaile').value = data.email;
            document.getElementById('phonee').value = data.phone;
            document.getElementById('addresse').value = data.address;

            document.getElementById('overlayEditUser').style.display = 'flex';
        })
        .catch(error => {
            console.error('Error fetching user:', error);
            alert('Could not load user data.');
        });
}
