# CAB302 Project

Java/JavaFX application focused on improving learning.

Group Members:
- Theo Negrao
- Yuta Matsuzaki
- Nur Syazeera Binti Shatri
- Nicolas Ruiz Guarin
- Darshakkumar Dilipkumar Patel

---

## Features
- AI flashcard/quiz generation
- gamified learning experience
- multiplayer interaction / study sessions
- decks sharing
- study group performance competition

---

## Tech stack

| Area                | Tools                 |
|---------------------|-----------------------|
| Java                | OpenJDK 21 (Corretto) |
| GUI (Client)        | JavaFX                |
| Auth                | ??                    |
| Backend             | ??                    |
| Database            | SQL (SQLite, local?)  |
| Caching / Realtime  | ??                    |
| AI                  | OpenAI Java SDK ??    |
| Build & Test        | Maven, ...            |
| Version Control     | GitHub                |

---

## Installation

Clone the repository:
```bash
git clone https://github.com/Matsuzaki-Yuta/CAB302_Project.git
```

Open the project in IntelliJ IDEA and run.

---

## Project Structure
```
src/
 └── main/
     ├── java/
     │   ├── module-info.java             # module declarations (requires javafx.controls, javafx.fxml)
     │   └── com.app.studysnap/
     │       ├── Main.java                # entry point, launches UI
     │       └── controllers
     │           └── controller.java      # different controllers bound to FXML
     └── resources/
         └── com.app.studysnap/
             ├── content.fxml             # UI layout files
     │       └── styles
     │           └── style.css            # different css files
```