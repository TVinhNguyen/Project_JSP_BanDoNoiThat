document.addEventListener("DOMContentLoaded", function () {
    const btnAddProduct = document.getElementById("btnAddProduct");
    const overlay = document.getElementById("overlayAddProduct");
    const btnCloseOverlay = document.getElementById("btnCloseOverlay");

    btnAddProduct.addEventListener("click", () => {
        overlay.style.display = "flex";
    });

    btnCloseOverlay.addEventListener("click", () => {
        overlay.style.display = "none";
    });

    // Close overlay when clicking outside of it
    overlay.addEventListener("click", (event) => {
        if (event.target === overlay) {
            overlay.style.display = "none";
        }
    });
});

document.querySelector('form').addEventListener('submit', function (e) {
    const price = document.getElementById('price').value;
    const stock = document.getElementById('stock').value;
    const category = document.getElementById('idcategory').value;
    const image = document.getElementById('image').value;

    if (!price || price <= 0) {
        alert('Price must be a positive number.');
        e.preventDefault();
    } else if (!stock || stock <= 0) {
        alert('Stock must be a positive number.');
        e.preventDefault();
    } else if (!category) {
        alert('Please select a category.');
        e.preventDefault();
    } else if (!image) {
        alert('Please upload an image.');
        e.preventDefault();
    }
});
document.getElementById('image').addEventListener('change', function (e) {
    const file = e.target.files[0];
    if (file) {
        const imgPreview = document.createElement('img');
        imgPreview.src = URL.createObjectURL(file);
        imgPreview.style.maxWidth = '10px';
        imgPreview.style.marginTop = '5px';
        e.target.parentNode.appendChild(imgPreview);
    }
});

const btnClose = document.getElementById('btnCloseOverlay');
const overlay = document.getElementById('overlayAddProduct');

btnClose.addEventListener('click', function() {
    const confirmClose = confirm("Do you want to cancel the product creation?");
    if (confirmClose) {
        overlay.style.display = 'none';
    }
});

function openOverlay() {
    overlay.style.display = 'flex';
}

// delete
document.addEventListener('DOMContentLoaded', function() {
    const deleteButtons = document.querySelectorAll('.btn-delete');

    deleteButtons.forEach(button => {
        button.addEventListener('click', function() {
            const productId = button.getAttribute('data-id');

            const confirmDelete = confirm("Are you sure you want to delete this product?");

            if (confirmDelete) {
                fetch(`/admin/ProductManage/${productId}`, {
                    method: 'DELETE',
                    headers: {
                        'Content-Type': 'application/json'
                    }
                })
                    .then(response => {
                        if (response.ok) {
                            alert("Product deleted successfully.");
                            window.location.reload(); // Refresh the page to reflect the changes
                        } else {
                            alert("Failed to delete the product.");
                        }
                    })
                    .catch(error => {
                        console.error('Error:', error);
                        alert("Error deleting product.");
                    });
            }
        });
    });
});


