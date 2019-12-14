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

* Amazon Linux AMI<sup>Amazon Machine Image</sup> 1에 Java 11 설치  
    1. [Amazon Corretto 11용 다운로드 페이지](https://docs.aws.amazon.com/ko_kr/corretto/latest/corretto-11-ug/downloads-list.html)에서 .rpm 파일 다운로드  
    2. 로컬파일 설치
    ```bash
    $ sudo yum localinstall <.rpm 파일 경로>
    ```  
    3. Java 버전 확인  
    ```bash
    $ java -version
    ```  
    __결과 :__  
    ```
    [ec2-user@ip-***-**-**-*** ~]$ java -version
    openjdk version "11.0.3" 2019-04-16 LTS
    OpenJDK Runtime Environment Corretto-11.0.3.7.1 (build 11.0.3+7-LTS)
    OpenJDK 64-Bit Server VM Corretto-11.0.3.7.1 (build 11.0.3+7-LTS, mixed mode)
    ```  
    4. 설치 후 Java 버전이 11이 아닌경우 아래 명령어를 사용하여 버전 변경  
    ```bash
    $ sudo alternatives --config java
    ```  
    
---  

#### 어색한 부분  
*  오타 & 오류 → [https://github.com/jojoldu/freelec-springboot2-webservice/issues](https://github.com/jojoldu/freelec-springboot2-webservice/issues)  
