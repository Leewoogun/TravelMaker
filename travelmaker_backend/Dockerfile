
# open jdk 17 버전의 환경을 구성
FROM openjdk:17-alpine

# build가 되는 시점에 JAR_FILE이라는 변수 명에 build/libs/*.jar 선언
# build/libs - gradle로 빌드했을 때 jar 파일이 생성되는 경로
ARG JAR_FILE=build/libs/gumibom-0.0.1-SNAPSHOT.jar

# JAR_FILE을 agaproject.jar로 복사
COPY ${JAR_FILE} gumibom.jar

# 운영 및 개발에서 사용되는 환경 설정을 분리
ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=prod", "-Duser.timezone=Asia/Seoul", "/gumibom.jar"]