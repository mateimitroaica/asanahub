function deleteYogaClass(id) {
    if (confirm("Are you sure you want to delete this Yoga Class?")) {
        fetch('/yoga-classes/delete/' + id, {
            method: 'DELETE'
        })
            .then(response => {
                if (response.ok) {
                    window.location.reload();
                } else {
                    alert('Failed to delete.');
                }
            });
    }
}

function editYogaClass(id) {
    window.location.href='/yoga-classes/edit/' +id;
}

function submitFilterForm() {
    document.getElementById('filterForm').submit();
}