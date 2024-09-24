import React, { useEffect } from 'react';
import { useLocation } from 'react-router-dom';
import axios from "axios";

export default function KakaoLoginHandler() {
    const location = useLocation();

    useEffect(() => {
        const searchParams = new URLSearchParams(location.search);
        const code = searchParams.get('code');

        sendCodeToBackend(code);
    }, [location]);

    const sendCodeToBackend = async (code) => {
        try {
            const response = await axios.get('/api/v1/login/kakao', {
                params: { code: code }
            });

            console.log(response.data);
            localStorage.setItem('user', JSON.stringify(response.data));

        } catch (error) {
            console.error('Error sending code to backend:', error);
            alert("로그인에 실패하였습니다. 다시 시도해주세요.");
        } finally {
            setTimeout(() => {
                window.close();
            }, 1000);
        }
    };
};