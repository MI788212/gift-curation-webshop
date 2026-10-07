document.addEventListener('DOMContentLoaded', () => {
    const form = document.querySelector('.contactForm');
    if (!form) {
        return;
    }

    const button = form.querySelector('button[type="submit"]');
    const status = form.querySelector('.contactFormStatus');

    const showStatus = (message, state) => {
        status.textContent = message;
        status.dataset.state = state;
    };

    form.addEventListener('submit', async (event) => {
        event.preventDefault();
        button.disabled = true;
        showStatus(form.dataset.sending, 'sending');

        let response;
        try {
            response = await fetch(form.action, {
                method: 'POST',
                body: new FormData(form),
                headers: { Accept: 'application/json' },
            });
        } catch (error) {
            showStatus(form.dataset.error, 'error');
            button.disabled = false;
            return;
        }

        if (response.ok) {
            form.reset();
            showStatus(form.dataset.success, 'success');
            button.disabled = false;
            return;
        }

        // Formspree turns down background sends when it wants a captcha first;
        // a normal submit lets it show one, so the message still gets through.
        HTMLFormElement.prototype.submit.call(form);
    });
});
