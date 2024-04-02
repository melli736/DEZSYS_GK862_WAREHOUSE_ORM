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
1. **Anpassung des Datenmodells für die Data Warehouse-Anwendung**
- Hinzufügen von Entity Klassen WarehouseEntity & ProduktEntity mit einer Beziehung zwischen Datawarehouse und Products.
- Erstellen der Repository interfaces 
- Implementierung der Entitätsbeziehung mittels ORM-Annotationen @OneToMany und @ManyToOne. 


2. **Datenaufnahme und Test:**
   - Einfügen von Datensätzen in die Data Warehouse- und Produkttabellen.
   - Protokollierung der eingefügten Datensätze.

Hier sind die aktualisierten CURL-Anweisungen mit der von Ihnen angegebenen Schreibweise:

**Warehouse 1 hinzufügen:**
```bash
curl -X POST http://localhost:8080/warehouse/add -d "warehouseName=Warehouse1" -d "street=Wexstraße 19" -d "city=Wien" -d "country=Austria" -d "plz=1200"
```

**Warehouse 2 hinzufügen:**
```bash
curl -X POST http://localhost:8080/warehouse/add -d "warehouseName=Warehouse2" -d "street=Sportgasse" -d "city=Graz" -d "country=Austria" -d "plz=54321"
```

**Alle Warehouses anzeigen:**
```bash
curl http://localhost:8080/warehouse/all
```


**Product 1 zu Warehouse 1 hinzufügen:**
```bash
curl http://localhost:8080/warehouse/1/products/add -d "name=Krustenbrot" -d "category=Brot" -d "amount=10" -d "unit=Laibe" -d "warehouseId=1"
```

**Product 2 zu Warehouse 1 hinzufügen:**
```bash
curl http://localhost:8080/warehouse/1/products/add -d "name=Avocado" -d "category=Früchte" -d "amount=20" -d "unit=Stück" -d "warehouseId=1"
```

**Product 3 zu Warehouse 1 hinzufügen:**
```bash
curl http://localhost:8080/warehouse/1/products/add -d "name=Quinoa" -d "category=Getreide" -d "amount=15" -d "unit=Kg" -d "warehouseId=1"
```

**Product 4 zu Warehouse 1 hinzufügen:**
```bash
curl http://localhost:8080/warehouse/1/products/add -d "name=Mandel Milch" -d "category=Pflanzenmilch" -d "amount=5" -d "unit=Liter" -d "warehouseId=1"
```

**Product 5 zu Warehouse 2 hinzufügen (Wiederverwendung von Product 1):**
```bash
curl http://localhost:8080/warehouse/2/products/add -d "name=Krustenbrot" -d "category=Brot" -d "amount=10" -d "unit=Laibe" -d "warehouseId=2"
```

**Product 6 zu Warehouse 2 hinzufügen (Wiederverwendung von Product 2):**
```bash
curl http://localhost:8080/warehouse/2/products/add -d "name=Avocado" -d "category=Früchte" -d "amount=20" -d "unit=Stück" -d "warehouseId=2"
```

**Product 7 zu Warehouse 2 hinzufügen (Neues Produkt):**
```bash
curl http://localhost:8080/warehouse/2/products/add -d "name=Kartoffelsalat" -d "category=Salate" -d "amount=8" -d "unit=Schüsseln" -d "warehouseId=2"
```

**Anzeige aller Produkte im Warehouse 1**
```bash
curl http://localhost:8080/warehouse/1/products
```

**Anzeige eines bestimmten Produkte im Warehouse 1**
```bash
curl http://localhost:8080/warehouse/1/products/1  
```

**Warehouse 3 Update**
```bash
curl -X PUT http://localhost:8080/warehouse/3 -d "warehouseName=Neuer Lagerhausname" -d "street=Neue Strasse" -d "city=Neue Stadt" -d "country=Neues Land" -d "plz=Neue PLZ"
```
### Erweiterte Anforderungen

Das `CrudRepository` Interface bietet verschiedene Methoden für die Datenverwaltung:

1. `save`: Speichert eine Entität. Falls die Entität bereits vorhanden ist, wird sie aktualisiert.
2. `saveAll`: Speichert eine Liste von Entitäten.
3. `findById`: Sucht eine Entität anhand ihrer ID.
4. `existsById`: Überprüft, ob eine Entität mit der gegebenen ID vorhanden ist.
5. `findAll`: Ruft alle Entitäten des Typs ab.
6. `findAllById`: Ruft alle Entitäten mit den angegebenen IDs ab.
7. `count`: Gibt die Anzahl der Entitäten zurück.
8. `deleteById`: Löscht eine Entität anhand ihrer ID.
9. `delete`: Löscht eine gegebene Entität.
10. `deleteAllById`: Löscht alle Entitäten mit den angegebenen IDs.
11. `deleteAll`: Löscht alle Entitäten, die vom Repository verwaltet werden.

### Probleme

1. Beim Rückgeben von ORM-Daten tritt eine Rekursion auf, die zu unerwünschten Ergebnissen führt. 
Lösung: Verwendung der Annotation `@JsonIgnore` in der Klasse ProductEntity, um die Rekursion zu beenden. Durch Hinzufügen dieser Annotation wird verhindert, dass die JSON-Daten weiterhin rekursiv durchlaufen werden.
```java
@ManyToOne
@JoinColumn(name="warehouse_id", nullable=false)
@JsonIgnore
private WarehouseEntity warehouse;
```

### Implementierung zusätzlicher Repository-Funktionen 
```java

public interface DatawarehouseRepository extends CrudRepository<Datawarehouse, Long> {
       List<Datawarehouse> findByDatawarehouseId(String datawarehouseId);
       Datawarehouse findByDatawarehouseIdAndProductId(String datawarehouseId, String productId);
       Datawarehouse updateByDatawarehouseId(String datawarehouseId);
   }
   ```

### Datenbankmigration zu PostgreSQL
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
