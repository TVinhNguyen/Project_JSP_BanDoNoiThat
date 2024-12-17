document.addEventListener("DOMContentLoaded", function () {
    const btnAddProduct = document.getElementById("btnAddProduct");
    const overlay = document.getElementById("overlayAddProduct");
    const btnCloseOverlay = document.getElementById("btnCloseOverlay");
    const btnCloseOverlaye = document.getElementById("btnCloseOverlaye");


    btnAddProduct.addEventListener("click", () => {
        overlay.style.display = "flex";
    });

    btnCloseOverlay.addEventListener("click", () => {
        overlay.style.display = "none";
    });
    btnCloseOverlaye.addEventListener("click", () => {
        document.getElementById('overlayEditProduct').style.display = "none";
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



function openDeleteForm(productId) {
    window.location.href = `/admin/ProductManage/delete?id=${productId}`;
}

function openEditForm(productId) {
    fetch(`/admin/ProductManage/edit?id=${productId}`)
        .then(response => response.json())
        .then(data => {
            document.getElementById('productIde').value = data.id;
            document.getElementById('namee').value = data.name;
            document.getElementById('descriptione').value = data.description;
            document.getElementById('pricee').value = data.price;
            document.getElementById('stocke').value = data.stock;
            document.getElementById('idcategorye').value = data.categoryID;
            document.getElementById('currentImagePreview').src = data.imageUrl;
            document.getElementById('currentImage').value = data.imageUrl;


            document.getElementById('overlayEditProduct').style.display = 'flex';
        })
        .catch(error => {
            console.error('Error fetching product:', error);
            alert('Không thể tải dữ liệu sản phẩm.');
        });
}

function submitSearch() {
    const searchName = document.getElementById("searchName").value.trim();
    const priceMin = document.getElementById("priceMin").value.trim();
    const priceMax = document.getElementById("priceMax").value.trim();
    const idcategory = document.getElementById("idcategory").value;

    const params = new URLSearchParams();
    if (searchName) params.append("name", searchName);
    if (priceMin) params.append("minPriceParam", priceMin);
    if (priceMax) params.append("maxPriceParam", priceMax);
    if (idcategory) params.append("category", idcategory);

    window.location.href = "/admin/ProductManage/?" + params.toString();
}

