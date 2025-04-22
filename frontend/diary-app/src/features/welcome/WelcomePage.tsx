// src/features/welcome/WelcomePage.tsx

import React from "react";
import { useNavigate } from "react-router-dom";
import styles from "./WelcomePage.module.css";

function WelcomePage() {
  const navigate = useNavigate();

  const handleGoToLogin = () => {
    navigate("/login");
  };

  return (
    <div className={styles.container}>
      {/* 로고 이미지 */}
      <img
        src="/images/logo.png"
        alt="로고"
        style={{
          position: "absolute",
          width: "227px",
          height: "227px",
          left: "91px",
          top: "268px",
        }}
      />

      {/* Pixel Art 이미지 */}
      <img
        src="/images/pixel_art.png"
        alt="픽셀아트"
        style={{
          position: "absolute",
          width: "161px",
          height: "128px",
          left: "93px",
          top: "268px",
        }}
      />

      {/* 오늘 어떤 일이 있었는지 기록해보자! */}
      <p className={styles.title}>오늘 어떤 일이 있었는지 기록해보자!</p>

      {/* 나랑 글 쓰러갈거지? ★ */}
      <p className={styles.subTitle}>나랑 글 쓰러갈거지?..★</p>

      {/* 여기를 클릭해봐!! (회전된 텍스트) */}
      <p className={styles.rotateText}>여기를 클릭해봐!!</p>

      {/* 쭈쩌리랑 기록하러 가기 → (버튼) */}
      <button className={styles.startButton} onClick={handleGoToLogin}>
        쭈쩌리랑 기록하러 가기 →
      </button>
    </div>
  );
}

export default WelcomePage;
