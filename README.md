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
* p.301  
    * `$kill -15 <PID>` : -15 옵션은 정상종료, -9 옵션은 강제종료.
    * `$ls -tr` : 현재 위치의 폴더와 파일들을 시간순으로 정렬(최신이 가장 위)한 후 거꾸로 정렬(최신이 가장 아래)  
    * `$tail -n 1` : 원래 tail은 입력의 마지막 10줄을 출력한다. -n 옵션을 사용하면 기본값 10 대신 원히는 값을 설정할 수 있다.  
* __JVM__이 애플리케이션을 운영하면서 메모리를 관리하는 방법.  
* **CI/CD 란?**  
    코드 버전 관리를 하는 VCS 시스템에 PUSH가 되면 자동으로 테스트와 빌드가 수행되어 안정적인 배포 파일을 만드는 과정을 지속적 통합(Continuous Integration)이라고 한다.  
    빌드 결과를 자동으로 운영 서버에 무중단 배포까지 진행되는 과정을 지속적 배포(Continuous Deployment)라고 한다.  
* **해당 프로젝트에서 CI/CD를 어떻게 구축했나요?**  
    사용된 기술 : Travis CI, AWS S3<sup>Simple Storage Service</sup>, AWS Code0Deploy, AWS IAM  

    1. Travis CI와 github 계정을 연동한 후 CI/CD를 적용할 저장소를 활성화시킨다.  
    2. 저장소의 루트 디렉토리에 `.travis.yml` 파일을 만든다.  
        1. 프로젝트 언어 지정, 컴파일러와 컴파일러의 버전 지정.  
        2. Travis CI를 어떤 브랜치에 push될 때 수행할지, 브랜치 지정.  
        3. Travis CI의 Home 디렉터리에서 캐시로 사용할 디렉터리 지정  
            그레이들을 통해 의존성을 받게 되면 이를 해당 디렉토리에 캐시하여, 같은 의존성은 다음 배포 때부터 다시 받지 않도록 설정.  
        4. 지정한 브랜치에 push되면 수행할 명령어 설정.
        5. Travis CI 실행 완료 시 자동으로 알람이 가도록 설정.
    3. Travis CI와 AWS S3 연동을 위한 AWS key 발급  
        일반적으로 AWS 서비스에 외부 서비스가 접근할 수 없다. 그러므로 접근 가능한 권한을 가진 key를 생성해서 사용해야 한다. AWS에서는 이러한 인증과 관련된 기능을 제공하는 서비스로 AWS IAM<sup>Identity and Access Management</sup>가 있다.  
        1. AWS IAM에서 `사용자` 생성 시작.
        2. 기존 정책에서 `AmazonS3FullAccess`과 `AWSCodeDeployFull`을 선택.
        3. `사용자` 생성 완료.
        4. 생성한 사용자의 `엑세스 키`와 `비밀 액세스 키`를 Travis CI의 해당 저장소에 환경 변수로 설정.
    4. AWS S3 버킷 생성.  
        S3 버킷의 이름은 공개적으로 엑세스할 수 있는 URL로 사용되는 것을 허용하기 때문에 전세계적으로 고유해야 함.  
        모든 퍼블릭 엑세스 차단에 체크.  
    5. `.travis.yml`에 빌드 결과물을 S3에 올리도록 코드 추가  
        1. deploy 전에 모든 build 결과물을 하나의 zip파일로 만들고 새로운 디렉토리를 생성해 새로운 디렉토리로 zip파일을 이동시킨다.  
        2. deploy 단계에서는 provider를 s3로 지정하고 해당 저장소의 환경변수로 설정했던 AWS key 엑세스 키와 비밀 엑세스 키를 가져온다. s3 버킷 이름과 사용하고 있는 AWS 지역을 지정한다. skin_cleanup 사용이유.....ing  

    **AWS S3가 필요한 이유 :** AWS S3는 일종의 파일 서버이며 정적 파일이나 배포 파일을 관리하는 기능을 지원.
    실제 배포는 AWS CodeDeploy를 이용하지만 CodeDeploy에는 저장하는 기능이 없다. Travis CI가 빌드한 결과물을 받아서 CodeDeploy가 가져갈 수 있도록 보관할 수 있는 공간이 필요하다.

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

* __There is insufficient memory for the Java Runtime Environment to continue.__  
    jar 파일 실행시 _There is insufficient memory for the Java Runtime Environment to continue._ 를 만나게 되었다. 해결 방법은 jar 파일 실행시 `-XX:MaxMetaspaceSize=512m -XX:MetaspaceSize=256m` 옵션을 추가하면 되는데, JVM 메모리 관련 이슈이기 때문에 왜 이런 문제가 일어났는지 정확히 이해하지는 못했다....  
    참고 링크 : [https://elfinlas.github.io/2018/06/08/jvm-memory-out/](https://elfinlas.github.io/2018/06/08/jvm-memory-out/)
---  

#### 어색한 부분  
*  오타 & 오류 → [https://github.com/jojoldu/freelec-springboot2-webservice/issues](https://github.com/jojoldu/freelec-springboot2-webservice/issues)  
