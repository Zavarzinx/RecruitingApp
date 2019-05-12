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

## First App run
Inside the root directory, do a:
```
mvn clean install
```
Run our Spring Boot App:
```
mvn --projects RecruitingAppServer spring-boot:run
```
Run Vue app:
```
npm run serve
```
