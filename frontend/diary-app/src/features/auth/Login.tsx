// src/features/auth/Login.tsx

import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import styles from "./Login.module.css";

export default function Login() {
  const navigate = useNavigate();
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [loginError, setLoginError] = useState({ error: false, message: "" });

  // 로그인 요청 함수
  const signIn = async (email: string, password: string) => {
    try {
      const response = await fetch("https://example.com/api/login", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify({ email, password }),
      });

      if (!response.ok) {
        throw new Error("로그인에 실패했습니다.");
      }

      const data = await response.json();
      console.log("서버 응답 데이터:", data);

      navigate("/");
    } catch (err: any) {
      console.error("로그인 에러", err);
      setLoginError({
        error: true,
        message: err.message || "로그인 중 문제가 발생했습니다.",
      });
    }
  };

  // 입력 핸들러
  const handleInput = (e: React.FormEvent<HTMLInputElement>) => {
    const value = e.currentTarget.value;
    const type = e.currentTarget.type;

    if (type === "email") {
      setEmail(value);
    } else {
      setPassword(value);
    }
  };

  // 회원가입 페이지로 이동
  const handleSignup = () => {
    navigate("/signup");
  };

  return (
    <div className={styles.loginWrapper}>
      {/* 로고 */}
      <img src="/images/logo.png" alt="로고" className={styles.logo} />

      {/* 로그인 폼 */}
      <form
        onSubmit={(e) => {
          e.preventDefault();
          signIn(email, password);
        }}
        className={styles.loginForm}
      >
        {/* 이메일 입력 */}
        <input
          type="email"
          placeholder="이메일"
          value={email}
          onChange={handleInput}
          required
          className={styles.inputEmail}
        />

        {/* 비밀번호 입력 */}
        <input
          type="password"
          placeholder="비밀번호"
          value={password}
          onChange={handleInput}
          required
          className={styles.inputPassword}
        />

        {/* 로그인 버튼 */}
        <button
          type="submit"
          className={styles.pixelButton}
          style={{
            backgroundImage: "url('/images/pixel_art_botton.png')",
            backgroundSize: "100% 100%",
            backgroundPosition: "center",
            backgroundRepeat: "no-repeat",
          }}
        >
          로그인하기
        </button>
      </form>

      {/* 회원가입 버튼 */}
      <button
        type="button"
        className={styles.pixelButton}
        onClick={handleSignup}
        style={{
          backgroundImage: "url('/images/pixel_art_botton.png')",
          backgroundSize: "100% 100%",
          backgroundPosition: "center",
          backgroundRepeat: "no-repeat",
        }}
      >
        회원가입
      </button>

      {/* 자동 로그인 + 아이디/비밀번호 찾기 */}
      <div className={styles.bottomOptions}>
        <label style={{ display: "flex", alignItems: "center" }}>
          <input type="checkbox" className={styles.checkbox} />
          <span style={{ marginLeft: "8px" }}>자동 로그인</span>
        </label>
        <div className={styles.findAccount}>아이디,비밀번호 찾기 &gt;</div>
      </div>

      {/* 에러 메시지 표시 */}
      {loginError.error && (
        <div className={styles.errorMessage}>{loginError.message}</div>
      )}
    </div>
  );
}
