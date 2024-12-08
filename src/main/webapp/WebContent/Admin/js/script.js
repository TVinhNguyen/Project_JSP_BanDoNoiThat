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
