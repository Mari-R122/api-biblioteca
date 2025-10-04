/**
 * MAIN JAVASCRIPT - API BIBLIOTECA
 * Funcionalidades principales para la interfaz web
 */

// ========================================
// CONFIGURACIÓN GLOBAL
// ========================================

// Configuración de la aplicación
const APP_CONFIG = {
    apiBaseUrl: '/api',
    defaultLoanDays: 15,
    maxLoanRenewals: 1,
    alertAutoHideDelay: 5000,
    animationDuration: 300
};

// ========================================
// UTILIDADES GENERALES
// ========================================

/**
 * Formatea una fecha para mostrar
 * @param {Date} date - Fecha a formatear
 * @returns {string} Fecha formateada
 */
function formatDate(date) {
    if (!date) return 'N/A';
    const d = new Date(date);
    return d.toLocaleDateString('es-ES', {
        year: 'numeric',
        month: '2-digit',
        day: '2-digit'
    });
}

/**
 * Formatea una fecha y hora para mostrar
 * @param {Date} date - Fecha a formatear
 * @returns {string} Fecha y hora formateadas
 */
function formatDateTime(date) {
    if (!date) return 'N/A';
    const d = new Date(date);
    return d.toLocaleString('es-ES', {
        year: 'numeric',
        month: '2-digit',
        day: '2-digit',
        hour: '2-digit',
        minute: '2-digit'
    });
}

/**
 * Muestra un toast de notificación
 * @param {string} message - Mensaje a mostrar
 * @param {string} type - Tipo de toast (success, error, warning, info)
 */
function showToast(message, type = 'info') {
    // Crear el contenedor de toasts si no existe
    let toastContainer = document.getElementById('toast-container');
    if (!toastContainer) {
        toastContainer = document.createElement('div');
        toastContainer.id = 'toast-container';
        toastContainer.className = 'toast-container position-fixed top-0 end-0 p-3';
        toastContainer.style.zIndex = '9999';
        document.body.appendChild(toastContainer);
    }

    // Crear el toast
    const toastId = 'toast-' + Date.now();
    const toastHtml = `
        <div id="${toastId}" class="toast align-items-center text-white bg-${type} border-0" role="alert">
            <div class="d-flex">
                <div class="toast-body">
                    <i class="bi bi-${getToastIcon(type)}"></i> ${message}
                </div>
                <button type="button" class="btn-close btn-close-white me-2 m-auto" data-bs-dismiss="toast"></button>
            </div>
        </div>
    `;

    toastContainer.insertAdjacentHTML('beforeend', toastHtml);

    // Mostrar el toast
    const toastElement = document.getElementById(toastId);
    const toast = new bootstrap.Toast(toastElement);
    toast.show();

    // Remover el toast después de que se oculte
    toastElement.addEventListener('hidden.bs.toast', function() {
        toastElement.remove();
    });
}

/**
 * Obtiene el icono apropiado para el tipo de toast
 * @param {string} type - Tipo de toast
 * @returns {string} Nombre del icono
 */
function getToastIcon(type) {
    const icons = {
        success: 'check-circle',
        error: 'exclamation-triangle',
        warning: 'exclamation-triangle',
        info: 'info-circle'
    };
    return icons[type] || 'info-circle';
}

/**
 * Confirma una acción con el usuario
 * @param {string} message - Mensaje de confirmación
 * @param {string} title - Título del diálogo
 * @returns {Promise<boolean>} Resultado de la confirmación
 */
function confirmAction(message, title = 'Confirmar Acción') {
    return new Promise((resolve) => {
        // Crear modal de confirmación
        const modalId = 'confirm-modal-' + Date.now();
        const modalHtml = `
            <div class="modal fade" id="${modalId}" tabindex="-1">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h5 class="modal-title">${title}</h5>
                            <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
                        </div>
                        <div class="modal-body">
                            <p>${message}</p>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancelar</button>
                            <button type="button" class="btn btn-primary" id="confirm-btn">Confirmar</button>
                        </div>
                    </div>
                </div>
            </div>
        `;

        document.body.insertAdjacentHTML('beforeend', modalHtml);

        const modalElement = document.getElementById(modalId);
        const modal = new bootstrap.Modal(modalElement);

        // Configurar botones
        const confirmBtn = modalElement.querySelector('#confirm-btn');
        const cancelBtn = modalElement.querySelector('.btn-secondary');

        confirmBtn.addEventListener('click', () => {
            modal.hide();
            resolve(true);
        });

        cancelBtn.addEventListener('click', () => {
            modal.hide();
            resolve(false);
        });

        // Limpiar modal cuando se oculte
        modalElement.addEventListener('hidden.bs.modal', () => {
            modalElement.remove();
        });

        modal.show();
    });
}

// ========================================
// FUNCIONALIDADES DE FORMULARIOS
// ========================================

/**
 * Valida un formulario antes de enviarlo
 * @param {HTMLFormElement} form - Formulario a validar
 * @returns {boolean} True si es válido
 */
function validateForm(form) {
    const requiredFields = form.querySelectorAll('[required]');
    let isValid = true;

    requiredFields.forEach(field => {
        if (!field.value.trim()) {
            field.classList.add('is-invalid');
            isValid = false;
        } else {
            field.classList.remove('is-invalid');
        }
    });

    // Validación específica por tipo de campo
    const emailFields = form.querySelectorAll('input[type="email"]');
    emailFields.forEach(field => {
        if (field.value && !isValidEmail(field.value)) {
            field.classList.add('is-invalid');
            isValid = false;
        }
    });

    const phoneFields = form.querySelectorAll('input[type="tel"]');
    phoneFields.forEach(field => {
        if (field.value && !isValidPhone(field.value)) {
            field.classList.add('is-invalid');
            isValid = false;
        }
    });

    return isValid;
}

/**
 * Valida un email
 * @param {string} email - Email a validar
 * @returns {boolean} True si es válido
 */
function isValidEmail(email) {
    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    return emailRegex.test(email);
}

/**
 * Valida un teléfono
 * @param {string} phone - Teléfono a validar
 * @returns {boolean} True si es válido
 */
function isValidPhone(phone) {
    const phoneRegex = /^[\+]?[0-9\s\-\(\)]{7,15}$/;
    return phoneRegex.test(phone);
}

/**
 * Configura la validación en tiempo real para un formulario
 * @param {HTMLFormElement} form - Formulario a configurar
 */
function setupFormValidation(form) {
    const fields = form.querySelectorAll('input, select, textarea');

    fields.forEach(field => {
        field.addEventListener('blur', function() {
            validateField(this);
        });

        field.addEventListener('input', function() {
            if (this.classList.contains('is-invalid')) {
                validateField(this);
            }
        });
    });
}

/**
 * Valida un campo individual
 * @param {HTMLElement} field - Campo a validar
 */
function validateField(field) {
    const value = field.value.trim();
    let isValid = true;
    let message = '';

    // Validación requerida
    if (field.hasAttribute('required') && !value) {
        isValid = false;
        message = 'Este campo es obligatorio';
    }

    // Validación de email
    if (field.type === 'email' && value && !isValidEmail(value)) {
        isValid = false;
        message = 'Ingresa un email válido';
    }

    // Validación de teléfono
    if (field.type === 'tel' && value && !isValidPhone(value)) {
        isValid = false;
        message = 'Ingresa un teléfono válido';
    }

    // Validación de fecha
    if (field.type === 'date' && value) {
        const date = new Date(value);
        const today = new Date();
        if (date > today) {
            isValid = false;
            message = 'La fecha no puede ser futura';
        }
    }

    // Aplicar validación visual
    if (isValid) {
        field.classList.remove('is-invalid');
        field.classList.add('is-valid');
    } else {
        field.classList.remove('is-valid');
        field.classList.add('is-invalid');
    }

    // Mostrar mensaje de error
    let feedback = field.parentNode.querySelector('.invalid-feedback');
    if (!isValid && message) {
        if (!feedback) {
            feedback = document.createElement('div');
            feedback.className = 'invalid-feedback';
            field.parentNode.appendChild(feedback);
        }
        feedback.textContent = message;
    } else if (feedback) {
        feedback.remove();
    }
}

// ========================================
// FUNCIONALIDADES DE BÚSQUEDA Y FILTROS
// ========================================

/**
 * Configura la funcionalidad de búsqueda en tiempo real
 * @param {string} searchInputId - ID del input de búsqueda
 * @param {string} targetTableId - ID de la tabla a filtrar
 */
function setupSearch(searchInputId, targetTableId) {
    const searchInput = document.getElementById(searchInputId);
    const targetTable = document.getElementById(targetTableId);

    if (!searchInput || !targetTable) return;

    searchInput.addEventListener('input', function() {
        const searchTerm = this.value.toLowerCase();
        const rows = targetTable.querySelectorAll('tbody tr');

        rows.forEach(row => {
            const text = row.textContent.toLowerCase();
            if (text.includes(searchTerm)) {
                row.style.display = '';
            } else {
                row.style.display = 'none';
            }
        });
    });
}

/**
 * Limpia los filtros de búsqueda
 * @param {string} formId - ID del formulario de búsqueda
 */
function clearFilters(formId) {
    const form = document.getElementById(formId);
    if (form) {
        form.reset();
        form.dispatchEvent(new Event('submit'));
    }
}

// ========================================
// FUNCIONALIDADES DE TABLAS
// ========================================

/**
 * Ordena una tabla por una columna
 * @param {HTMLTableElement} table - Tabla a ordenar
 * @param {number} columnIndex - Índice de la columna
 */
function sortTable(table, columnIndex) {
    const tbody = table.querySelector('tbody');
    const rows = Array.from(tbody.querySelectorAll('tr'));
    
    const isAscending = table.dataset.sortOrder !== 'asc';
    table.dataset.sortOrder = isAscending ? 'asc' : 'desc';

    rows.sort((a, b) => {
        const aValue = a.cells[columnIndex].textContent.trim();
        const bValue = b.cells[columnIndex].textContent.trim();

        // Intentar comparar como números
        const aNum = parseFloat(aValue);
        const bNum = parseFloat(bValue);
        
        if (!isNaN(aNum) && !isNaN(bNum)) {
            return isAscending ? aNum - bNum : bNum - aNum;
        }

        // Comparar como texto
        return isAscending ? 
            aValue.localeCompare(bValue) : 
            bValue.localeCompare(aValue);
    });

    // Reordenar filas
    rows.forEach(row => tbody.appendChild(row));

    // Actualizar indicadores de ordenamiento
    updateSortIndicators(table, columnIndex);
}

/**
 * Actualiza los indicadores de ordenamiento en la tabla
 * @param {HTMLTableElement} table - Tabla
 * @param {number} columnIndex - Índice de la columna activa
 */
function updateSortIndicators(table, columnIndex) {
    const headers = table.querySelectorAll('th');
    const isAscending = table.dataset.sortOrder === 'asc';

    headers.forEach((header, index) => {
        const icon = header.querySelector('.sort-icon');
        if (icon) {
            icon.remove();
        }

        if (index === columnIndex) {
            const sortIcon = document.createElement('i');
            sortIcon.className = `bi bi-chevron-${isAscending ? 'up' : 'down'} sort-icon ms-1`;
            header.appendChild(sortIcon);
        }
    });
}

/**
 * Configura la funcionalidad de ordenamiento para una tabla
 * @param {string} tableId - ID de la tabla
 */
function setupTableSorting(tableId) {
    const table = document.getElementById(tableId);
    if (!table) return;

    const headers = table.querySelectorAll('th[data-sortable="true"]');
    
    headers.forEach((header, index) => {
        header.style.cursor = 'pointer';
        header.addEventListener('click', () => sortTable(table, index));
    });
}

// ========================================
// FUNCIONALIDADES DE ALERTAS
// ========================================

/**
 * Configura el auto-ocultado de alertas
 */
function setupAlertAutoHide() {
    const alerts = document.querySelectorAll('.alert:not(.alert-permanent)');
    
    alerts.forEach(alert => {
        setTimeout(() => {
            const bsAlert = new bootstrap.Alert(alert);
            bsAlert.close();
        }, APP_CONFIG.alertAutoHideDelay);
    });
}

// ========================================
// FUNCIONALIDADES DE MODALES
// ========================================

/**
 * Abre un modal con contenido dinámico
 * @param {string} title - Título del modal
 * @param {string} content - Contenido del modal
 * @param {Array} buttons - Botones del modal
 */
function openModal(title, content, buttons = []) {
    const modalId = 'dynamic-modal-' + Date.now();
    const modalHtml = `
        <div class="modal fade" id="${modalId}" tabindex="-1">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title">${title}</h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
                    </div>
                    <div class="modal-body">
                        ${content}
                    </div>
                    <div class="modal-footer">
                        ${buttons.map(btn => `
                            <button type="button" class="btn btn-${btn.class || 'secondary'}" 
                                    data-bs-dismiss="modal" ${btn.onclick ? `onclick="${btn.onclick}"` : ''}>
                                ${btn.text}
                            </button>
                        `).join('')}
                    </div>
                </div>
            </div>
        </div>
    `;

    document.body.insertAdjacentHTML('beforeend', modalHtml);

    const modalElement = document.getElementById(modalId);
    const modal = new bootstrap.Modal(modalElement);

    modalElement.addEventListener('hidden.bs.modal', () => {
        modalElement.remove();
    });

    modal.show();
}

// ========================================
// FUNCIONALIDADES DE CARGA
// ========================================

/**
 * Muestra un indicador de carga
 * @param {HTMLElement} element - Elemento donde mostrar la carga
 */
function showLoading(element) {
    element.classList.add('loading');
    element.disabled = true;
}

/**
 * Oculta un indicador de carga
 * @param {HTMLElement} element - Elemento donde ocultar la carga
 */
function hideLoading(element) {
    element.classList.remove('loading');
    element.disabled = false;
}

/**
 * Envía un formulario con indicador de carga
 * @param {HTMLFormElement} form - Formulario a enviar
 */
function submitFormWithLoading(form) {
    const submitBtn = form.querySelector('button[type="submit"]');
    
    if (submitBtn) {
        showLoading(submitBtn);
    }

    // Simular envío (en una implementación real, esto sería manejado por el servidor)
    setTimeout(() => {
        if (submitBtn) {
            hideLoading(submitBtn);
        }
    }, 1000);
}

// ========================================
// INICIALIZACIÓN
// ========================================

/**
 * Inicializa todas las funcionalidades cuando el DOM está listo
 */
document.addEventListener('DOMContentLoaded', function() {
    // Configurar alertas auto-ocultables
    setupAlertAutoHide();

    // Configurar validación de formularios
    const forms = document.querySelectorAll('form[data-validate="true"]');
    forms.forEach(setupFormValidation);

    // Configurar ordenamiento de tablas
    const sortableTables = document.querySelectorAll('table[data-sortable="true"]');
    sortableTables.forEach(table => setupTableSorting(table.id));

    // Configurar búsquedas en tiempo real
    const searchInputs = document.querySelectorAll('input[data-search-target]');
    searchInputs.forEach(input => {
        const targetId = input.dataset.searchTarget;
        setupSearch(input.id, targetId);
    });

    // Configurar tooltips
    const tooltipTriggerList = [].slice.call(document.querySelectorAll('[data-bs-toggle="tooltip"]'));
    tooltipTriggerList.map(function (tooltipTriggerEl) {
        return new bootstrap.Tooltip(tooltipTriggerEl);
    });

    // Configurar popovers
    const popoverTriggerList = [].slice.call(document.querySelectorAll('[data-bs-toggle="popover"]'));
    popoverTriggerList.map(function (popoverTriggerEl) {
        return new bootstrap.Popover(popoverTriggerEl);
    });

    console.log('✅ API Biblioteca - Interfaz web inicializada correctamente');
});

// ========================================
// EXPORTAR FUNCIONES GLOBALES
// ========================================

// Hacer funciones disponibles globalmente
window.APP_CONFIG = APP_CONFIG;
window.showToast = showToast;
window.confirmAction = confirmAction;
window.validateForm = validateForm;
window.formatDate = formatDate;
window.formatDateTime = formatDateTime;
window.clearFilters = clearFilters;
window.openModal = openModal;
window.showLoading = showLoading;
window.hideLoading = hideLoading;
