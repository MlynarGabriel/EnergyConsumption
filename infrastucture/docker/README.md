# Docker setup for DISYS project
This Docker Compose configuration includes two essential containers for the project's infrastructure. The first container hosts the database, providing persistent storage and efficient management for the project's data. It ensures reliability and scalability, making it suitable for handling the application's data transactions. The second container is configured as a queue, which facilitates asynchronous task processing and communication between various components of the project. This setup improves system performance by decoupling workloads and enhancing the application's ability to handle concurrent operations. Together, these containers provide a robust and scalable foundation for the project's backend systems.

## Database
The database container in this setup runs a PostgreSQL database configured with a user named **"disysuser"** and a password **"disyspw"**. For the project, you will need to create the necessary databases and tables to store and manage the required data effectively. The connection details are as follows: the hostname is the localhost, the default port is 5432, and you can authenticate using the provided username and password. Ensure that the PostgreSQL container is running before attempting to connect.

database name is also disysuser

## Queue 
The queue container in this setup runs a RabbitMQ Management instance, which provides both message queuing functionality and a user-friendly web-based management interface. RabbitMQ facilitates the communication between different components of the project by enabling reliable message passing. To connect to the RabbitMQ instance, you can access the management interface via a web browser by navigating to http://loclahost:15672. By default, you can log in using the username "guest" and password "guest", unless overridden in the Docker Compose configuration. For programmatic access, RabbitMQ client libraries can connect using the host at loclahost:5672. Ensure the container is running and accessible before attempting to connect.

# DIS Energy Monitoring System

## Projektbeschreibung

In diesem Projekt wurde ein verteiltes System zur **Überwachung und Analyse von Energieverbrauch und -produktion** innerhalb von Energiegemeinschaften entwickelt.

Ziel ist es, die Verteilung von Energie zwischen **Community**-Teilnehmern und dem **öffentlichen Netz (Grid)** in Echtzeit zu überwachen und visuell darzustellen.

---

## Systemarchitektur

Das System besteht aus **6 unabhängig lauffähigen Komponenten**, die über eine **Message Queue (RabbitMQ)** kommunizieren und Daten in einer **PostgreSQL-Datenbank** speichern:

### Komponentenübersicht:

| Komponente                | Beschreibung |
|--------------------------|--------------|
| **Community Producer**   | Sendet Produktionsdaten basierend auf Wetter (Sonnenstunden) |
| **Community User**       | Simuliert Energieverbrauch abhängig von Tageszeit |
| **Usage Service**        | Rechnet Minutenwerte in Stundenwerte um und schreibt in DB |
| **Current Percentage**   | Berechnet Community-Auslastung und Grid-Anteil je Stunde |
| **REST API**             | Stellt aktuelle und historische Daten zur Verfügung |
| **GUI (JavaFX)**         | Visualisiert Live-Daten und bietet Filter für Historie |


## Lessons Learned

- Zu Beginn dachten wir, dass eine Benutzeroberfläche zur Verwaltung der Datenbank notwendig sei. Daher haben wir mit **pgAdmin** im Docker-Container gearbeitet. Im Verlauf des Projekts stellte sich jedoch heraus, dass die Integration über **IntelliJ** als DataSource deutlich einfacher und effektiver war.

- Die Arbeit mit mehreren **Spring Boot**-Komponenten war anfangs herausfordernd – insbesondere was **Abhängigkeiten (Dependencies)** betrifft. Durch das Projekt haben wir ein besseres Verständnis dafür entwickelt, **warum bestimmte Starter (z. B. JPA)** benötigt werden und wie sie konfiguriert werden.

- Besonders wichtig war die Erkenntnis, dass **die klare Aufteilung der Komponenten** enorm zur Wartbarkeit und Übersichtlichkeit beiträgt – gerade in einem verteilten System mit mehreren Verantwortlichkeiten.

- Auch wenn komplexe Lösungen oft verlockend sind, haben wir gelernt, dass **einfache und pragmatische Ansätze** oft besser funktionieren und effizienter umsetzbar sind.



## Docker nutzen
in den ordner wo der docker-compose.yml ist und folgende code ausführen vorausgesetzt docker läuft in Hintergrund:
docker compose up -d

docker container schließen:
docker compose down


## SQL Struktur holen
Wir haben einen voreingestellte Datenbank Strutur damit ihr das Schema holt foglende Code ausführen: 
docker exec -i postgres psql -U disysuser -d disysuser < dump.sql

