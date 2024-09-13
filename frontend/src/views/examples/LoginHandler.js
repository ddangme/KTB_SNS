import {useEffect, useRef} from 'react';
import {useNavigate} from 'react-router-dom';

export default function LoginHandler() {
    const navigate = useNavigate();
    const isFirstRender = useRef(true);

    async function sendCode(code) {
        try {
            const response = await fetch('http://localhost:8080/api/v1/login/oauth2/code', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify({code: code})
            })
            console.log(response)
        } catch (error) {
            console.error(error);
        }
    }

    useEffect(() => {
        if (isFirstRender.current) {
            const code = new URL(window.location.href).searchParams.get("code");

            sendCode(code).then(
                response => console.log(response)
            );

            // navigate("/");
            isFirstRender.current = false;
        }
    }, [navigate]);
}
