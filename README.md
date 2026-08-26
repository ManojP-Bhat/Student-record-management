# Student Record Management System

A simple full-stack CRUD web app built with **core Java, JDBC, MySQL, and vanilla HTML/CSS/JS** —
no Spring, no Maven, no external frameworks. It uses Java's built-in `HttpServer` class,
so the whole backend is just plain Java files you can read top to bottom.

Add, view, and delete student records from a browser. Good first "Java backend" project
for a resume, and a stepping stone toward Spring Boot later.

---

## What this project demonstrates

- Writing a backend **without a framework**, so you understand what Spring Boot does for you later
- The **DAO pattern** (`StudentDAO`) — keeping all SQL in one place
- A basic **REST-style API** (`GET`, `POST`, `DELETE` on the same URL)
- Connecting Java to MySQL with **JDBC**
- Serving a plain HTML/CSS/JS frontend that talks to the backend using `fetch()`

---

## Project structure

```
student-record-management-system/
├── README.md
├── .gitignore
│
├── database/
│   └── schema.sql              # creates the database + students table
│
├── lib/
│   └── README.txt              # where to put the MySQL driver jar
│
├── src/
│   └── com/
│       └── srms/
│           ├── Main.java               # starts the server, wires up routes
│           ├── DBConfig.java           # your MySQL URL / username / password
│           ├── Student.java            # the "model" — one student record
│           ├── StudentDAO.java         # all SQL lives here (Data Access Object)
│           ├── StudentApiHandler.java  # handles GET/POST/DELETE for /api/students
│           ├── StaticFileHandler.java  # serves index.html, style.css, app.js
│           └── util/
│               └── FormParser.java     # tiny helper to read form data
│
└── web/
    ├── index.html               # the page (form + table)
    ├── style.css                # styling
    └── app.js                   # fetch() calls to the Java API
```

**How a request flows through the project:**

```
Browser
  │
  ▼
Main.java  ──────────────►  routes the request
  │                              │
  ├── "/"              ──► StaticFileHandler   ──► reads files from /web
  │
  └── "/api/students"  ──► StudentApiHandler
                                │
                                ▼
                          StudentDAO ──► JDBC ──► MySQL (students table)
```

---

## Prerequisites

- **JDK 11 or newer** (`java -version` to check)
- **MySQL** installed and running locally
- **MySQL Connector/J** driver (a single `.jar` file — see `lib/README.txt`)

---

## Setup

**1. Create the database**

```bash
mysql -u root -p < database/schema.sql
```

**2. Add your MySQL credentials**

Open `src/com/srms/DBConfig.java` and update the username/password to match your MySQL setup.

**3. Download the JDBC driver**

Download `mysql-connector-j-<version>.jar` from the
[official MySQL site](https://dev.mysql.com/downloads/connector/j/) and place it inside the `lib/` folder.

**4. Compile**

```bash
# from the project's root folder
javac -d out -cp "lib/*" $(find src -name "*.java")
```

**5. Run**

```bash
java -cp "out:lib/*" com.srms.Main       # macOS / Linux
java -cp "out;lib/*" com.srms.Main       # Windows
```

**6. Open the app**

Go to **http://localhost:8080** in your browser.

---

## API reference

| Method | Endpoint             | Description                     |
|--------|-----------------------|----------------------------------|
| GET    | `/api/students`       | Returns all students as JSON    |
| POST   | `/api/students`       | Adds a student (form data: `name`, `email`, `course`, `year`) |
| DELETE | `/api/students?id=1`  | Deletes the student with that id |

---

## Ideas to extend it (good for learning, or a v2)

- Add an "Edit student" endpoint (`PUT`)
- Add simple validation (e.g. reject a blank email)
- Move `DBConfig` values into a `.properties` file instead of hardcoding them
- Rebuild the same features using **Spring Boot** once you're comfortable with it —
  this project is intentionally structured close to what a Spring Boot app looks like
  (`Controller` → `Service`/`DAO` → `Database`), so the jump won't feel big.

---

## Uploading this to GitHub

```bash
cd student-record-management-system
git init
git add .
git commit -m "Initial commit: Student Record Management System"
git branch -M main
git remote add origin https://github.com/<your-username>/student-record-management-system.git
git push -u origin main
```

---

## Adding this to your resume

**Project title:** Student Record Management System

**Tech stack line:** Java, JDBC, MySQL, HTML5, CSS3, JavaScript

**Bullet points you can use:**
- Built a full-stack CRUD web application using core Java's built-in HTTP server, JDBC, and MySQL, without relying on an external framework.
- Designed a REST-style API (GET, POST, DELETE) and a DAO layer to separate database logic from request handling.
- Built a responsive HTML/CSS/JavaScript frontend that communicates with the backend using the Fetch API.
