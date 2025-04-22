// src/index.tsx

import React from "react";
import ReactDOM from "react-dom/client";
import "./index.css";
import "bootstrap/dist/css/bootstrap.min.css";
import App from "./App";
import reportWebVitals from "./reportWebVitals"; // ⭐ 얘도 import 제일 위로!
import { BrowserRouter } from "react-router-dom";
// import WelcomePage from "./features/welcome/WelcomePage";

const root = ReactDOM.createRoot(
  document.getElementById("root") as HTMLElement,
);

root.render(
  <React.StrictMode>
    <BrowserRouter>
      <App />
    </BrowserRouter>
  </React.StrictMode>,
);

// 성능 측정용
reportWebVitals();
