> [스프링 부트와 AWS로 혼자 구현하는 웹 서비스](https://book.naver.com/bookdb/book_detail.nhn?bid=15871738)  

_이것을 끝으로 프로젝트 만들자_&#128640;

### 프로젝트 환경  
* gradle
    ```bash
    ------------------------------------------------------------
    Gradle 5.6.4
    ------------------------------------------------------------

    Build time:   2019-11-01 20:42:00 UTC
    Revision:     dd870424f9bd8e195d614dc14bb140f43c22da98

    Kotlin:       1.3.41
    Groovy:       2.5.4
    Ant:          Apache Ant(TM) version 1.9.14 compiled on March 12 2019
    JVM:          11.0.4 (Ubuntu 11.0.4+11-post-Ubuntu-1ubuntu218.04.3)
    OS:           Linux 4.15.0-72-generic amd64
    ```  

---      

### 생각해볼 것들  
* mavenCentral()과 jcenter()를 같이 사용하면 gradle에서 원격 저장소를 어떻게 사용하는지...
    ```
    repositories {
        mavenCentral()
        jcenter()
    }
    ```  
* p73, 메서드 체이닝이란...  
* p142, 브라우저의 스코프  
* p.145, table 태그에서 thead, tbody, tfoot이 구조적 의미를 가지는 것처럼 table의 구조적 의미(colgroup 등)  


---  

### 이슈  
* gradle 5 에서 lombok을 사용하려면 build.gradle의 dependencies에 annotationProcessor도 추가해야 한다.  
__build.gradle:__  
    ```
    dependencies {
        compile('org.projectlombok:lombok')
        annotationProcessor('org.projectlombok:lombok')
    }
    ```  

---  

#### 어색한 부분  
*  오타 & 오류 → [https://github.com/jojoldu/freelec-springboot2-webservice/issues](https://github.com/jojoldu/freelec-springboot2-webservice/issues)  
