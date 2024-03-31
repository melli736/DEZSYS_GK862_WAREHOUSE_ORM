# MidEng GK862 Spring Data & ORM
**Autor:** Melissa Wallpach 4BHIT
**Version:** 20.03.2024

## Ziele der Aufgabe

1. **Verständnis der Datenzugriffsschicht**: mit Spring auf Datenbanken zugreifen und Daten manipulieren. 

2. **Object Relational Mapping (ORM)**: Objekte in einer objektorientierten Programmiersprache wie Java mit relationalen Datenbanktabellen verknüpfen, ohne SQL-Abfragen schreiben zu müssen.

3. **Anpassung eines Datenmodells**: vorhandene Datenmodelle  erweitern und anzupassen.

4. **Umgang mit verschiedenen Datenbankmanagementsystemen**: zwischen verschiedenen Datenbankmanagementsystemen wechseln und Konfigurationen entsprechend anpassen. 


## Theorie

**Object Relational Mapping (ORM)**:
ist eine Technik, die es erlaubt, Daten zwischen einer objektorientierten Programmiersprache wie Java und einer relationalen Datenbank wie MySQL oder PostgreSQL zu verbinden, ohne dass manuelle SQL-Abfragen geschrieben werden müssen. ORM-Frameworks wie Hibernate und Spring Data JPA erleichtern diese Verbindung, indem sie Objekte in der Programmiersprache direkt mit Tabellen in der Datenbank abbilden.

**Data Access**:
bezieht sich darauf, wie Daten in einer Anwendung zugänglich gemacht werden. In diesem Fall nutzen wir Spring, um auf Daten in einer MySQL-Datenbank zuzugreifen. Dabei werden Techniken wie JDBC (Java Database Connectivity) verwendet, um die Verbindung zwischen der Anwendung und der Datenbank herzustellen und SQL-Abfragen auszuführen.

**MySQL**:
ist ein relationales Datenbankverwaltungssystem. In dieser Aufgabenstellung verwenden wir es, um Daten persistent zu speichern und darauf zuzugreifen. Es wird in Verbindung mit Spring verwendet, um die Datenbankoperationen zu implementieren.

**Hibernate und Spring Data JPA**
sind zwei ORM-Frameworks für Java. Sie bieten Werkzeuge und Bibliotheken, um die Interaktion zwischen Java-Objekten und relationalen Datenbanken zu vereinfachen. Hibernate ist ein weit verbreitetes ORM-Framework, während Spring Data JPA eine Abstraktionsschicht über JPA (Java Persistence API) bietet und die Datenzugriffsoperationen weiter vereinfacht.

## Praxis

### Spring-Tutorial "Zugriff auf Daten mit MySQL"

1. **Einrichtung des Projekts:**
- Entwicklungsumgebung (IntelliJ, openjdk 19.0.2 2023-01-17, Gradle 8.4).
- GitHub-Repositorie für das Projekt. https://github.com/melli736/MidEngGK862_SpringData-ORM.git
- `brew install mysql`
   
2. **Erstellung des Spring-Projekts:**
- Auf https://start.spring.io. als Build-Tool Gradle und die Sprache Java auswählen.
- Auf Dependencies klicken und Spring Web, Spring Data JPA und MySQL Driver auswählen -> Generate (=Projektarchiv herunterladen)

3. **Einrichtung der MySQL-Datenbank:**
- MySQL-Client starten im Terminal `mysql -u root -p`   
- Neue Datenbank und einen neuen Benutzer für das Projekt erstellen

```
mysql> create database db_warehouse; -- Creates the new database
mysql> create user 'user'@'%' identified by 'pswd'; -- Creates the user
mysql> grant all on db_warehouse.* to 'user'@'%'; -- Gives all privileges to the new user on the newly created database
```

4. **Konfiguration der Datenbankverbindung:**
- Datei application.properties im Verzeichnis src/main/resources bearbeiten und Verbindungsinformationen zur MySQL-Datenbank einfügen.
```
spring.jpa.hibernate.ddl-auto=update
spring.datasource.url=jdbc:mysql://${MYSQL_HOST:localhost}:3306/db_warehouse
spring.datasource.username=user
spring.datasource.password=pswd
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
#spring.jpa.show-sql: true
hibernate.dialect=org.hibernate.dialect.MySQLDialect
```

5. **Erstellung des Datenbankmodells:**
- Klasse namens User im Verzeichnis src/main/java/com/example/accessingdatamysql erstellen.
- Attribute der (id, name, email) definieren und mit Annotationen für Hibernate markieren.
   
6. **Erstellung des Repositories:**
- Erstelle einer Klasse namens UserRepository im gleichen Verzeichnis wie User.java
- Erweitere die Schnittstelle CrudRepository und definiere den Entitätstyp (User) und den Typ des Primärschlüssels (Integer).

7. **Erstellung des Controllers:**
- Klasse MainController im gleichen Verzeichnis wie die Benutzerklasse erstellen.
- Definiere Methoden für das Hinzufügen eines neuen Benutzers und das Abrufen aller Benutzer. Markiere sie mit entsprechenden Annotationen.

8. **Starten der Anwendung:**
- Führe das Projekt aus, entweder über die IDE oder indem du die Gradle- oder Maven-Befehle verwendest, um die Anwendung zu starten.

9. **Testen der Anwendung:**
- Verwende curl oder eine ähnliche Tool, um die beiden Endpunkte der Anwendung zu testen.
- Führe HTTP-Anfragen aus, um Daten hinzuzufügen und abzurufen, und überprüfe die Ergebnisse.

10. **Sicherheitsanpassungen (optional):**
- Sobald deine Anwendung produktionsbereit ist, solltest du Sicherheitsänderungen vornehmen, um dich vor potenziellen SQL-Injection-Angriffen zu schützen.


### Erweiterung des Datenmodells
- Anpassung des Datenmodells für die Data Warehouse-Anwendung.
- Hinzufügen von Entity Klassen einer Beziehung zwischen Datawarehouse und Products.
- Implementierung der Entitätsbeziehung mittels ORM.

```java
// Beispielcode für die Implementierung der Entitätsbeziehung
@Entity
public class Datawarehouse {
   @OneToMany(mappedBy = "datawarehouse")
      private List<Product> products;
      // Weitere Entitätsattribute und Methoden
}
```

4. **Datenaufnahme:**
   - Einfügen von Datensätzen in die Data Warehouse- und Produkttabellen.
   - Protokollierung der eingefügten Datensätze.

   ```java
   // Beispielcode für das Einfügen von Datensätzen
   datawarehouseRepository.save(datawarehouse);
   productRepository.saveAll(products);
   ```

5. **Erweiterte Anforderungen:**
   - Untersuchung der verfügbaren Methoden für das CrudRepository.
   - Erweiterung des Data Warehouse-Repository um zusätzliche Funktionalitäten.

   ```java
   // Beispielcode für die Implementierung zusätzlicher Repository-Funktionen
   public interface DatawarehouseRepository extends CrudRepository<Datawarehouse, Long> {
       List<Datawarehouse> findByDatawarehouseId(String datawarehouseId);
       Datawarehouse findByDatawarehouseIdAndProductId(String datawarehouseId, String productId);
       Datawarehouse updateByDatawarehouseId(String datawarehouseId);
   }
   ```

6. **Datenbankmigration zu PostgreSQL:**
   - Anpassung der Konfigurationsdateien für PostgreSQL.
   - Migration der Datenbank von MySQL zu PostgreSQL.

   ```java
   // Beispielcode für die PostgreSQL-Konfiguration in Spring
   @Configuration
   @EnableJpaRepositories(basePackages = "com.example.demo.repository")
   @EntityScan(basePackages = "com.example.demo.model")
   public class PostgreSqlConfig {
       // PostgreSQL-Konfiguration hier einfügen
   }
   ```

7. **Abschluss und Zusammenfassung:**
   - Überprüfung aller Implementierungsschritte und Dokumentation.
   - Zusammenfassung der erreichten Ergebnisse und der erfüllten Anforderungen.

Diese strukturierte Arbeitsdokumentation führt Sie durch die Umsetzung der Aufgabe "DEZSYS_GK862_WAREHOUSE_ORM" und bietet klare Anleitungen für die theoretischen Konzepte sowie praktische Implementierungsschritte.
