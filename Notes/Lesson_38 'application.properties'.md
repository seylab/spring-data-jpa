
## application.properties

Spring Boot uygulamalar?nda yap?land?rma ayarlar? genellikle `application.properties` dosyas?na yaz?l?r.  
Bu dosya, uygulaman?n �e?itli bile?enlerine de?er atamak i�in kullan?l?r.

�rnek olarak:

```properties
# application.properties
app.name=MyApplication
app.version=1.0.0
app.author=John Doe
```
Yukar?daki dosyada, `app.name`, `app.version`, ve `app.author` gibi anahtar-de?er �iftleri tan?mlanm??t?r. Bu de?erler, Spring i�erisinde farkl? bile?enlerde kullan?labilir.

### 1. `@PropertySource` Anotasyonu

`@PropertySource`, belirtilen bir dosyadan �zellik (property) y�klemek i�in kullan?l?r.  
Genellikle bir `@Configuration` s?n?f?nda yer al?r ve `application.properties` d???nda farkl? bir �zellik dosyas?n? eklemek i�in kullan?l?r.

�rnek kullan?m:
```java
@Configuration
@PropertySource("classpath:custom.properties")
public class AppConfig {
    // �zellik dosyas?ndan de?erleri kullanmak i�in kullan?lacak s?n?f
}
```
Bu anotasyon ile `custom.properties` adl? dosyadaki de?erler uygulamaya y�klenir ve daha sonra `@Value` ile eri?ilebilir hale gelir.

Not: E?er Spring Boot kullan?yorsan?z, varsay?lan olarak `application.properties` dosyas?n? eklemenize gerek yoktur. `@PropertySource` sadece ek yap?land?rma dosyalar? i�in kullan?l?r.

### 2. `@Value` Anotasyonu

`@Value`, `application.properties` veya di?er �zellik dosyalar?ndaki anahtar-de?er �iftlerini do?rudan s?n?f alanlar?na atamak i�in kullan?l?r. Bu, s?n?f i�indeki herhangi bir alana d??ar?dan gelen yap?land?rma de?erini enjekte etmenin basit bir yoludur.

�rnek kullan?m:
```java
@Component
public class AppProperties {

    @Value("${app.name}")
    private String appName;

    @Value("${app.version}")
    private String appVersion;

    @Value("${app.author}")
    private String appAuthor;

    public void printProperties() {
        System.out.println("App Name: " + appName);
        System.out.println("App Version: " + appVersion);
        System.out.println("App Author: " + appAuthor);
    }
}
```

#### A�?klama:

- `@Value("${app.name}")`: `application.properties` dosyas?ndaki `app.name` de?erini al?r ve `appName` de?i?kenine atar.
- `@Value("${app.version}")`: `application.properties` dosyas?ndaki `app.version` de?erini al?r ve `appVersion` de?i?kenine atar.
- `@Value("${app.author}")`: `application.properties` dosyas?ndaki `app.author` de?erini al?r ve `appAuthor` de?i?kenine atar.

Uygulama �al??t?r?ld???nda, `printProperties` metodu bu de?erleri konsola yazd?r?r.

### �rnek `application.properties` ve Kullan?m:

#### `application.properties`
```properties
app.name=ExampleApp
app.version=2.1.0
app.author=Jane Doe
```

#### Java Class
```java
@Component
public class AppConfig {

    @Value("${app.name}")
    private String appName;

    @Value("${app.version}")
    private String appVersion;

    @Value("${app.author}")
    private String appAuthor;

    public void displayConfig() {
        System.out.println("Application Name: " + appName);
        System.out.println("Version: " + appVersion);
        System.out.println("Author: " + appAuthor);
    }
}
```

#### Output
```yaml
Application Name: ExampleApp
Version: 2.1.0
Author: Jane Doe
```

### 3. Varsay?lan De?er Atama
E?er bir �zellik yap?land?rma dosyas?nda bulunmuyorsa, `@Value` anotasyonu ile varsay?lan bir de?er atanabilir.  
Bu durumda, o anahtar yap?land?rma dosyas?nda tan?ml? de?ilse bile varsay?lan de?er kullan?lacakt?r.

�rnek:
```java
@Value("${app.description:Default description}")
private String appDescription;
```
E?er `app.description` de?eri `application.properties` dosyas?nda yoksa, `Default description` de?eri atan?r.

### �zet:
- `application.properties` dosyas?, Spring Boot uygulamalar?nda yap?land?rma ayarlar? i�in kullan?l?r.
- `@PropertySource`, d?? �zellik dosyalar?n? y�klemek i�in kullan?l?r.
- `@Value`, �zellik dosyalar?ndan al?nan de?erleri s?n?f alanlar?na enjekte eder.
