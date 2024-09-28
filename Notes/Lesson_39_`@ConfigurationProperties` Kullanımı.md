
## `@ConfigurationProperties` Kullanımı

### 1. Genel Bakış
`@ConfigurationProperties`, Spring Boot'ta uygulama yapılandırmalarını bir sınıfa bağlamak için kullanılır. 
Bu anotasyon sayesinde `application.properties` veya `application.yml` dosyasındaki ayarları, bir Java sınıfına otomatik olarak enjekte edebiliriz. Bu yöntem, özellikle karmaşık yapılandırmaların yönetilmesinde çok kullanışlıdır.

---

### 2. `@ConfigurationProperties` Kullanımı

`@ConfigurationProperties` anotasyonu ile yapılandırma değerlerini bir sınıfa bağlayabiliriz. Bu sayede, yapılandırma dosyasında yer alan belirli bir önekle (prefix) başlayan tüm özellikler, bir sınıfa otomatik olarak atanır.

#### Örnek `application.properties`:

```properties
app.name=MyApplication
app.version=1.0.0
app.author.name=John Doe
app.author.email=johndoe@example.com
```

#### Örnek Java Sınıfı:

```java
@Component
@ConfigurationProperties(prefix = "app")
public class AppProperties {

    private String name;
    private String version;
    private final Author author = new Author();

    // Getters and Setters

    public static class Author {
        private String name;
        private String email;

        // Getters and Setters
    }
}
```

#### Açıklama:
- `@ConfigurationProperties(prefix = "app")`: `application.properties` dosyasındaki `app` ile başlayan tüm özellikler `AppProperties` sınıfına bağlanır.
- İç içe yapılandırmaları yönetmek için `Author` adlı bir alt sınıf kullanılmıştır.
- `@Component`: Sınıf, Spring konteynerine bileşen olarak eklenir. Ancak, genellikle `@EnableConfigurationProperties` ile de kullanılabilir.

---

### 3. `@EnableConfigurationProperties` Anotasyonu

`@ConfigurationProperties` anotasyonunun çalışması için, bu sınıfın Spring tarafından taranması gereklidir. Bunun için, ana yapılandırma sınıfına `@EnableConfigurationProperties` ekleyerek ilgili sınıfın taranmasını sağlayabiliriz.

#### Örnek:

```java
@Configuration
@EnableConfigurationProperties(AppProperties.class)
public class AppConfig {
}
```

Bu örnekte, `AppProperties` sınıfı, Spring tarafından yapılandırma sınıfı olarak taranır ve `application.properties` dosyasındaki `app` önekli tüm değerler bu sınıfa bağlanır.

---

### 4. `application.properties` Örneği:

```properties
app.name=MyApp
app.version=2.0.0
app.author.name=Jane Doe
app.author.email=janedoe@example.com
```

---

### 5. `@ConfigurationProperties` ve Validasyon

`@ConfigurationProperties` anotasyonu ile birlikte, Spring'in validasyon özelliklerini kullanarak yapılandırma verilerinin doğruluğunu kontrol edebiliriz.

#### Örnek:

```java
@Component
@ConfigurationProperties(prefix = "app")
@Validated
public class AppProperties {

    @NotNull
    private String name;

    @Pattern(regexp = "\d+\.\d+\.\d+")
    private String version;

    // Getters and Setters
}
```

#### Açıklama:
- `@Validated`: Sınıfa validasyon eklemek için kullanılır.
- `@NotNull`: `name` alanının boş olmasını engeller.
- `@Pattern`: `version` alanı için belirli bir düzen sağlar (örneğin: "1.0.0" formatında olmalı).

---

### 6. Neden `@ConfigurationProperties` Kullanmalıyız?

- **Karmaşık Yapılandırmaları Yönetme**: Özellikle çok fazla ayar olduğunda, bu ayarları doğrudan sınıflara bağlayarak, kodun okunabilirliğini artırabiliriz.
- **Tip Güvenliği**: Değerlerin doğrudan sınıf değişkenlerine atanması, derleme zamanında tip kontrollerinin yapılmasını sağlar.
- **Kapsülleme**: Yapılandırmaların tek bir sınıfta toplanması, yapılandırma verilerinin merkezi bir yerde yönetilmesini sağlar.
  
---

### 7. Özet:

- `@ConfigurationProperties`, yapılandırma dosyalarındaki değerleri bir sınıfa bağlar.
- Karmaşık yapılandırmaları yönetmek için idealdir.
- Tip güvenliği sağlar ve validasyon ile entegre çalışır.
