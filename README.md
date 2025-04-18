# 쭈쩌리
> REST API 기반으로 구현한 일기장 중심의 일상 공유 SNS 입니다.

## Team
|Name|GitHUb|Role|
|----|----|----|
|연은결|[eungyeol0506](https://github.com/eungyeol0506/)|BE|
|배은지||FE|

## Project Overview
### demo: 
### Project: 쭈쩌리(Jjoojjeollee) 
이 프로젝트의 목표는 ~ 제공하는 웹 서비스 **쭈쩌리**를 구축하는 것입니다. 
~~사용자에게 직접적으로 무엇을 제공할 수 있는지 서술~~

~~중점으로 둔 기술은 무엇인지, 왜? 그렇게 했는지~~

#### 관련 문서
> wiki 작성 중

## Technology Stack

## System Architechture

## Deployment Setup

## Project Structure
```
├── backend/
│   └── src/main/java/app/project/jjoojjeollee/
│       ├── api/                          # api 계층
│       ├── domain/                       # 도메인 계층 (핵심 비즈니스 로직)
│       ├── exception/                    # 예외처리 핸들러
│       ├── repository/                   # DAO 계층
│       ├── service/                      # 서비스 계층 (조립)
│       ├── param/                        # DTO 
│       └── jjoojjeolleeApplication.java  # 스프링 부트 앱
│
├── frontend/

├── .github/workflows/                   # GitHub Actions CI/CD 설정
└── README.md                            # 프로젝트 문서
```

## 기타
> 도움받은 자료
> - [문서작성 및 프로젝트 전체 구조](https://github.com/prgrms-be-devcourse/NBE4-5-3-Team09)
> - [도메인 모델 패턴 참고](https://github.com/gothinkster/spring-boot-realworld-example-app/)