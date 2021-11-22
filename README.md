# Docker Jenkins Springboot Git Mariadb Windows 

### 시나리오

 Springboot(jpa + mariadb) 개발 후 git에 push 합니다. Jenkins에서 build후 host docker images로 등록합니다.

### Docker 

* network
  ```shell
  docker network docker-network
  ```

* Mariadb
  * port(-p) 옵션 뒤에 두었다가 오류발생했었음. 
  ```shell
    docker run -d -p 3306:3306 --network docker-network --name mariadb -v D:\03.작업공간\03.docker\07.mariadb:/etc/mysql/conf.d  -e MYSQL_ROOT_PASSWORD=1234 --character-set-server=utf8mb4 --collation-server=utf8mb4_unicode_ci 
  ```
  
* Jenkins
  * Jenkins에서 host docker의 명령어를 사용해야하므로 docker.sock을 연결합니다. 윈도우에서는 "//var/"처럼 역슬러시 2번 붙여줘야합니다. (mac/linux는 역슬러시 1번)
  ```shell
    docker run -d -p 9090:8080 -v //var/run/docker.sock:/var/run/docker.sock -v D:\03.작업공간\03.docker\10.jenkins:/var/jenkins_home --name jenkins -u root jenkins/jenkins:lts
  ```

### Github

Repository로 Github사용합니다. Gitlab을 사용한 뒤에 Jenkins처럼 설정해줘도 됩니다.

* GitHub > Settings > Developer settings > Personal access tokens 에서 public_repo로 키 발급받습니다.

### Jenkins

 Jenkin를 설치합니다.
  * GitHub > Settings > Developer settings > Personal access tokens 에서 public_repo로 키 발급받습니다.
  * Jenkins 메인설정
    * Dashboard > Jenkins관리 > Global Tool Configuration > Gradle구성합니다.(ex. Gradle 7.3)
  * 프로젝트 구성
    * Jenkins > 신규 생성한 프로젝트 > 구성
    * Git에서 발급받은 개인키를 등록합니다.
    ```shell
      https://개인키명:개인키@github.com/리파지토리.git
    ```      
    * Build
    ```shell
      clean
      build -x test
    ```
    * Execute
    ```shell
      docker build -t jenkins/springdockerapp .
    ```
    ```shell
      
    ```
    ```shell
      
    ```
  * 기타
    * Jenkins 빌드 시 test를 실행하지 않도록 구성했는데 만약 test 구성이 필요하다면 jenkins docker 설정에서 --network 추가해주면 됩니다.
    * test 시에 db 연결하려고 할테니...
    * 지금은 일단 패스

### Springboot
 endpoint는 간단히 구성됩니다. GET /system/health, GET /students/names/{name}, GET /students/ids/{id}, POST /students 

* application.yml
  * mariadb:3306에서 mariadb는 docker mariadb 별칭입니다.
  ```yaml
    spring:
      datasource:
        url: jdbc:mariadb://{docker mariadb 별칭}:3306/docker_test
        driver-class-name: org.mariadb.jdbc.Driver
        username: root
        password: 1234
      jpa:
        open-in-view: false
        generate-ddl: true
        show-sql: true
        hibernate:
          ddl-auto: none
  ```
* Dockerfile
  * adoptopenjdk/openjdk11 베이스로 이미지 생성합니다.
  ```dockerfile
    FROM adoptopenjdk/openjdk11
    EXPOSE 8081
    ARG JAR_FILE=build/libs/*.jar
    COPY ${JAR_FILE} app.jar
    ENTRYPOINT ["java", "-jar", "/app.jar"]
  ```













