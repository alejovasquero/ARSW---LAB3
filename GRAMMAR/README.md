# Arquitecturas de Software
# Laboratorio 3 Introduction to Spring and Configuration using annotations

## Integrantes
- David Alejandro Vasquez Carreño
- Michael Jefferson Ballesteros Coca

____________
#### Instalación

Vamos a compilar el código con maven.
   ```console
mvn compile
   ```


Para ejecutar la aplicación.

  ```console
mvn exec:java -Dexec.mainClass="edu.eci.arsw.springdemo.ui.Main"
   ```

Para ejecutar las pruebas

  ```console
mvn test
   ```
____________

### PART 1

1. Opening in NetBeans

![](img/project.JPG)

2. Spring Configuration
  ```

<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context"

       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans-4.2.xsd
          http://www.springframework.org/schema/context http://www.springframework.org/schema/context/spring-context-4.2.xsd
">

    <context:component-scan base-package="edu.eci.arsw" />
</beans>
   ```

3. Annotations

    1. GrammarChecker
    ```
        ...
        public class GrammarChecker {
            @Autowired
            SpellChecker sc;
        ...

    ```
        
    2. EnglishSpeakChecker
    ```
        ...
        @Service
        public class EnglishSpellChecker implements SpellChecker {
        ...

    ```
4. Testing

Testing EnglishSpeakTeacher on instance of GrammarChecker

![](img/english.JPG)


### PART 2
SpanishSpeakChecker

```
    ...
    @Service
    public class SpanishSpellChecker implements SpellChecker {
    ...

```

Testing SpanishSpeakTeacher on instance of GrammarChecker

![](img/espaniol.JPG)

## Contribuciones

* **Alejandro Vasquez** - *Extender* - [alejovasquero](https://github.com/alejovasquero)
* **Michael Ballesteros** - *Extender* - [Wasawsky](https://github.com/Wasawsky)
