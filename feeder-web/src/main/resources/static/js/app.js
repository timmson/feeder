const indicators = {
    usd: {icon: '💵', name: 'Курс USD', unit: 'руб.', link: 'https://www.cbr.ru/currency_base/daily/'},
    eur: {icon: '💶', name: 'Курс EUR', unit: 'руб.', link: 'https://www.cbr.ru/currency_base/daily/'},
    imoex: {icon: '🇷🇺', name: 'Индекс Мосбиржи', unit: '', link: 'https://www.moex.com/ru/index/IMOEX'},
    keyRate: {icon: '🗝', name: 'Ключевая ставка', unit: '%', link: 'https://www.cbr.ru/hd_base/keyrate/'},
    inflation: {icon: '🎈', name: 'Офиц. инфляция', unit: '%', link: 'https://www.cbr.ru/hd_base/infl/'},
    mredc: {icon: '🏡', name: 'Стоимость м2 в Москве', unit: 'руб.', link: 'https://www.moex.com/ru/index/MREDC'}
};

function formatNumber(value) {
    return new Intl.NumberFormat('ru-RU', {
        minimumFractionDigits: 2,
        maximumFractionDigits: 2
    }).format(value);
}

function toggleTheme() {
    const currentTheme = document.documentElement.getAttribute('data-theme');
    const newTheme = currentTheme === 'dark' ? 'light' : 'dark';
    const icon = document.getElementById('theme-icon');

    document.documentElement.setAttribute('data-theme', newTheme);
    icon.textContent = newTheme === 'dark' ? '☀️' : '🌙';
    localStorage.setItem('theme', newTheme);
}

function initTheme() {
    const savedTheme = localStorage.getItem('theme');
    const prefersDark = window.matchMedia('(prefers-color-scheme: dark)').matches;
    const theme = savedTheme || (prefersDark ? 'dark' : 'light');
    const icon = document.getElementById('theme-icon');

    document.documentElement.setAttribute('data-theme', theme);
    icon.textContent = theme === 'dark' ? '☀️' : '🌙';
}

async function loadStocks() {
    const content = document.getElementById('content');
    const btn = document.querySelector('.refresh-btn');

    btn.disabled = true;
    content.innerHTML = '<div class="loading">Загрузка данных...</div>';

    try {
        const response = await fetch('/api/stocks');
        if (!response.ok) throw new Error('Ошибка загрузки данных');

        const stocks = await response.json();

        if (stocks.length === 0) {
            content.innerHTML = '<div class="error">Нет данных</div>';
            return;
        }

        const html = stocks.map(stock => {
            const info = indicators[stock.name] || {icon: '📈', name: stock.name, unit: '', link: '#'};
            const value = formatNumber(stock.price);

            return `
                <div class="indicator">
                    <div class="indicator-header">
                        <span class="indicator-name">${info.icon} ${info.name}</span>
                        <a href="${info.link}" target="_blank" class="indicator-link">Источник →</a>
                    </div>
                    <div class="indicator-value">${value} ${info.unit}</div>
                </div>
            `;
        }).join('');

        content.innerHTML = html;
    } catch (error) {
        content.innerHTML = `<div class="error">Ошибка: ${error.message}</div>`;
    } finally {
        btn.disabled = false;
    }
}

// Инициализация темы при загрузке
initTheme();

// Загрузка при открытии страницы
loadStocks();

// Автообновление каждые 5 минут
setInterval(loadStocks, 5 * 60 * 1000);
