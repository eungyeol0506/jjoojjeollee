// src/router.tsx
import React from "react";
import { BrowserRouter, Routes, Route } from "react-router-dom";
import WelcomePage from "./features/welcome/WelcomePage";
import Login from "./features/auth/Login";

export default function Router() {
  return (
    <Routes>
      <Route path="/" element={<WelcomePage />} />
      <Route path="/login" element={<Login />} /> {/* ⭐ /login 경로 추가 */}
    </Routes>
  );
}

// 아래 한 줄 추가!
export {};
