// auth.js
function getToken() {
    return localStorage.getItem('jwt');
}

function setToken(token) {
    localStorage.setItem('jwt', token);
}


// 토큰 검증 함수
async function handleTokenValidation() {
    const token = getToken();
    if (!token) {
        alert("로그인 후 이용해주세요.")
        redirectToLogin();
        return;
    }
    try {
        const response = await fetch('/auth/refresh', {
            method: 'POST',
            headers: {
                'Authorization': token,
            },
            credentials: 'include' // 리프레시 토큰 쿠키를 포함
        });
        if (response.ok) {
            const data = await response.json();
            console.log('handleTokenValidation 함수 서버 응답중');
            setToken(data.data);
            console.log('토큰 재발급 성공'); // 현재는 이 함수를 불러올 때 마다 토큰을 재 발급
        } else {
            console.error('토큰 재발급 실패');
            //  redirectToLogin();
        }
    } catch (error) {
        console.error('Error:', error);
        if (error.message.includes("리프레시 토큰이 만료되었습니다.")) {
            alert("리프레시 토큰이 만료되었습니다. 다시 로그인해주세요.");
        }
        redirectToLogin();
    }
}

function redirectToLogin() {
    window.location.href = "/login.html";
}

// TODO :  //  Html 접근시 토큰검증함수
async function checkToken() {
    document.addEventListener("DOMContentLoaded", async function () {
        const token = getToken();
        try {
            await handleTokenValidation();

            if (!token) {
                window.location.href = "/login.html?error=Unauthorized";
            }
        } catch (error) {
            console.error("handleTokenValidation 에러", error);
        }
    });
}

// TODO :  // API 요청 로그인유저 검증 입니다.
export async function fetchWithJwtAuth(url, options = {}) {
    const token = getToken('jwt');
    console.log("fetchWithJwtAuth 의 토큰", token)
    if (!options.headers) {
        options.headers = {};
    }
    if (token) {
        console.log("이거좀 보여줘", options.headers['Authorization'] = token)
        options.headers['Authorization'] = token;
    }
    console.log('보내는 헤더:', options.headers); // 헤더를 확인하는 로그 추가
    const response = await fetch(url, options);
    console.log('응답 상태:', response.status);
    return response;
}

// TODO :  // 로그아웃입니다.
function logout() {
    const token = getToken('jwt');
    fetch('/auth/logout', {
        method: 'POST',
        headers: {
            'Authorization': token,
            'Content-Type': 'application/json'
        },
        credentials: 'include'  // 쿠키 포함
    }).then(response => {
        if (response.ok) {
            localStorage.removeItem('jwt'); // JWT 액세스 토큰 제거
            console.log('로그아웃');
            window.location.href = 'login.html';
        }
    }).catch(error => {
        console.error('Error during logout:', error);
    });
}

export {getToken, setToken, handleTokenValidation, checkToken, logout};
