# 로컬 설정 관련

## 환경 변수 설정

    로컬 환경으로 구동 (Active profiles : local)

## 설정파일 적용 
    
    resources 폴더 아래
    application.yml : 공통설정
    application-local.yml : 로컬환경설정 (여기에 oil, weather API 주소가 있습니다.), 로그 설정

## 기타

    스프링부트, 코틀린, webflux, 코루틴으로 개발

## Todo

    1. 서킷브레이커 적용은 해놨는데 제대로 작동안함
    2. Oil쪽 API는 하드코딩 되어있음
    3. JDBC 설정 필요
    
## TEST

    POST http://localhost:6000/api/geo/location
    Content-Type: application/json
    {
    "address": "서울 강남구"
    }