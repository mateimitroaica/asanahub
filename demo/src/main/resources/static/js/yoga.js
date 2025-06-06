function deleteYogaClass(id) {
    if (confirm("Are you sure you want to delete this Yoga Class?")) {
        const token = document.querySelector('meta[name="_csrf"]').getAttribute('content');
        const header = document.querySelector('meta[name="_csrf_header"]').getAttribute('content');

        fetch('/yoga-classes/delete/' + id, {
            method: 'DELETE',
            headers: {
                [header]: token
            }
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