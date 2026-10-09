document.addEventListener('DOMContentLoaded', () => {
    // Ги наоѓаме сите форми за отстранување од омилени
    const removeForms = document.querySelectorAll('.remove-fav-form');

    removeForms.forEach(form => {
        form.addEventListener('submit', function (e) {
            // 1. Го спречуваме моменталниот submit
            e.preventDefault();

            // 2. Ја наоѓаме картичката (.favorite-card)
            const card = this.closest('.favorite-card');

            if (card) {
                // 3. Ја додаваме класата за fade-out анимација
                card.classList.add('removing');

                // 4. Го испраќаме submit-от по 400ms
                setTimeout(() => {
                    this.submit();
                }, 550);
            } else {
                this.submit();
            }
        });
    });
});

function increaseQty(btn) {
    const input = btn.parentNode.querySelector('.qty-input');
    let currentValue = parseInt(input.value) || 1;
    if (currentValue < 10) {
        input.value = currentValue + 1;
    }
}

function decreaseQty(btn) {
    const input = btn.parentNode.querySelector('.qty-input');
    let currentValue = parseInt(input.value) || 1;
    if (currentValue > 1) {
        input.value = currentValue - 1;
    }
}