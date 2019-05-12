# RecruitingApp
## Project structure
```
spring-boot-vuejs
├─┬ RecruitingAppServer     → backend module with Spring Boot code
│ ├── src
│ └── pom.xml
├─┬ RecruitingAppClient    → frontend module with Vue.js code
│ ├── src
│ └── pom.xml
└── pom.xml     → Maven parent pom managing both modules
```
## Import
import project into your IDE

## Settings
in RecruitingAppServer/src/main/java/com/recruiting/application.properties 
setup database settings and serverport(vue using 8080 so spring cant run on his default port)
```
spring.datasource.url=YourDatabaseURL
spring.datasource.username=YourDatabaseUsername
spring.datasource.password=YourDatabasePassword

spring.liquibase.url=YourDatabaseURL
spring.liquibase.user=YourDatabaseUsername
spring.liquibase.password=YourDatabasePassword

server.port=8088 (or put smth different if 8088 is busy, but there's some changes on vue app then)
```
setup smtp server settings, use [mailtrap](https://mailtrap.io) if u dont have one
```
spring.mail.host=smtp.mailtrap.io
spring.mail.username=YourSMTPUsername
spring.mail.password=YourSMTPPassword
spring.mail.port=2525
spring.mail.protocol=smtp
```
If Spring app port was changed you need to change smth on Vue app:
RecruitingAppClient/vue.config.js
```
target: 'http://localhost:8088' - change to new port
```
RecruitingAppClient/src/components/http-commonn.js
```
baseURL: 'http://localhost:8088/api' - change to new port
```

## First App run
Inside the root directory, do a:
```
mvn clean install
```
Run our Spring Boot App:
Just run main ServerApplication
or use maven:
```
mvn --projects RecruitingAppServer spring-boot:run
```
Run Vue app:
```
npm run serve
```
