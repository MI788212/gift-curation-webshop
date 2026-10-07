document.documentElement.classList.add('js');

document.addEventListener('DOMContentLoaded', () => {
    const header = document.querySelector('.siteHeader');
    const toggle = document.querySelector('.siteNavToggle');

    if (header && toggle) {
        toggle.addEventListener('click', () => {
            const isOpen = header.classList.toggle('isOpen');
            toggle.setAttribute('aria-expanded', String(isOpen));
        });
    }

    const currentPath = window.location.pathname;
    document.querySelectorAll('.siteNavLinks a').forEach((link) => {
        const linkPath = new URL(link.href).pathname;
        const isSection = !linkPath.endsWith('/') && currentPath.startsWith(linkPath + '/');
        if (currentPath === linkPath || isSection) {
            link.setAttribute('aria-current', 'page');
        }
    });
});
