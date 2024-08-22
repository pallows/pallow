document.addEventListener('DOMContentLoaded', function () {
    const accessToken = localStorage.getItem('Authorization');

    // 로그아웃 함수
    function handleLogout() {
        fetch('/users/logout', {
            method: 'POST',
            headers: {
                'Authorization': accessToken,
                'Content-Type': 'application/json'
            },
        })
            .then(response => response.json())
            .then(responseData => {
                if (responseData.statusCode === 200) {
                    localStorage.removeItem('Authorization');
                    alert('성공적으로 로그아웃 되었습니다.');
                    window.location.href = '/';
                } else {
                    alert('로그아웃에 실패했습니다.');
                }
            })
            .catch(error => {
                console.error('Error logging out:', error);
            });
    }

    // 로그아웃 버튼에 이벤트 리스너 추가
    const logoutButtons = document.querySelectorAll('#logoutBtn, #logoutButton');
    logoutButtons.forEach(button => {
        button.addEventListener('click', handleLogout);
    });
});

