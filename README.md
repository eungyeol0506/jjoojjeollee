# 쭈쩌리
> REST API 기반으로 구현한 일기장 중심의 일상 공유 SNS 입니다.

## Team
|Name|GitHUb|Role|
|----|----|----|
|연은결|[eungyeol0506](https://github.com/eungyeol0506/)|BE|
|배은지|[BAEEUNJi97](https://github.com/BAEEUNJi97/)|FE|

## Project Overview
### demo: (개발중)
### Project: 쭈쩌리(Jjoojjeollee) 
이 프로젝트의 목표는 개인 소셜 서비스를 제공하는 **쭈쩌리**를 구축하는 것입니다. 

**쭈쩌리** 서비스는 일기장을 중심으로 일상을 공유하며, 아날로그를 통해 친구들과 나누던 추억을 디지털로 재해석하여 향수를 제공합니다. 이미지 기반의 핀터레스트, 일기 기능의 duck-z, 투다 서비스를 벤치마킹하여, 사용자와 다른 사용자 사이의 관계를 최소화하여 개인/그룹 기록과 성취에 중점을 두었습니다. 

프로젝트를 통해 `CI/CD 자동화`, `RESTful web API 환경`, `도메인-모델 패턴`, `git기반 협업` 역량 강화를 중점으로 아카이빙을 기반으로 프로젝트 전반의 `기획`부터 `배포`까지 모두 경험하는 것을 목표로 합니다. 

#### 관련 문서
> wiki 작성 중

## Technology Stack
> 자세한 내용은 [기술 스택 문서]에서 확인할 수 있습니다.

#### Frontend
<div align=""> 
  <img src="https://img.shields.io/badge/HTML5-E34F26?style=for-the-badge&logo=html5&logoColor=white"/>
  <img src="https://img.shields.io/badge/CSS3-1572B6?style=for-the-badge&logo=css3&logoColor=white"/>
  <img src="https://img.shields.io/badge/JavaScript-F7DF1E?style=for-the-badge&logo=javascript&logoColor=black"/>
  <img src="https://img.shields.io/badge/TypeScript-3178C6?style=for-the-badge&logo=typescript&logoColor=white"/>
  <img src="https://img.shields.io/badge/React-61DAFB?style=for-the-badge&logo=react&logoColor=white"/>
  <img src="https://img.shields.io/badge/Node.js-5FA04E?style=for-the-badge&logo=nodedotjs&logoColor=white"/>
</div>

#### Backend
<div align=""> 
  <img src="https://img.shields.io/badge/Java17-007396?style=for-the-badge&logo=java&logoColor=white"/>
  <img src="https://img.shields.io/badge/SpringBoot-6DB33F?style=for-the-badge&logo=springboot&logoColor=white"/>
  <img src="https://img.shields.io/badge/Apache%20Tomcat-F8DC75?style=for-the-badge&logo=apachetomcat&logoColor=white"/>
  <img src="https://img.shields.io/badge/Hibernate-59666C?style=for-the-badge&logo=hibernate&logoColor=white"/>
  <img src="https://img.shields.io/badge/JUnit5-25A162?style=for-the-badge&logo=junit5&logoColor=white"/>
</div>

#### Database
<div align="">
  <img src="https://img.shields.io/badge/Oracle-DA291C?style=for-the-badge&logoColor=white"/> 
</div>

#### Deployment & Infra
<div align="">
  <img src="https://img.shields.io/badge/linux-FCC624?style=for-the-badge&logo=linux&logoColor=white"/> 
  <img src="https://img.shields.io/badge/Github%20Action-181717?style=for-the-badge&logo=github&logoColor=white"/>   
  <img src="https://img.shields.io/badge/AWS-232F3E?style=for-the-badge&logo=amazonwebservices&logoColor=white"/>   
</div>


## System Architechture
#### Overview

> 이미지 추가 예정

**배포 자동화**

## Deployment Setup
#### [1] Clone the Repository
``` 
git clone https://github.com/eungyeol0506/jjoojjeollee.git 
```
#### [2] Run Database (H2 Database)

#### [3] Run Backend ( Spring Boot )

#### [4] Run Frontend ( Node.js )

## Project Structure
```
├── backend/
│   └── src/main/java/app/project/jjoojjeollee/
│       ├── api/                          # api 계층
│       ├── domain/                       # 도메인 계층 (핵심 비즈니스 로직)
│       ├── exception/                    # 예외처리 핸들러
│       ├── global/                       # 공통 사용 모듈
│       ├── repository/                   # DAO 계층
│       ├── service/                      # 서비스 계층 (조립)
│       ├── param/                        # DTO(외부 -> 내부) 
│       └── jjoojjeolleeApplication.java  # 스프링 부트 앱
│
├── frontend/
 -- 추가 예정
├── .github/workflows/                   # GitHub Actions CI/CD 설정
└── README.md                            # 프로젝트 문서
```

## 기타
> 도움받은 자료
> - [문서작성 및 프로젝트 전체 구조](https://github.com/prgrms-be-devcourse/NBE4-5-3-Team09)
> - [도메인 모델 패턴 참고](https://github.com/gothinkster/spring-boot-realworld-example-app/)