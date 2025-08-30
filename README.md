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

| Area                | Tools                   |
|---------------------|-------------------------|
| Java                | OpenJDK 21 (Corretto)   |
| GUI (Client)        | JavaFX                  |
| Auth                | OAuth + simple password |
| Password encryption | Bcrypt                  |
| Email sending       | ??                      |
| Database            | SQLite                  |
| Caching / Realtime  | ??                      |
| AI                  | Gemini Java SDK         |
| Build & Test        | Maven, ...              |
| Version Control     | GitHub                  |

---

## Project Structure
```
src/
 └── main/
     ├── java/
     │   ├── module-info.java             # module declarations (requires javafx.controls, javafx.fxml)
     │   └── com.app.studysnap/
     │       ├── Main.java                # entry point, launches UI
     │       └── controllers              # different controllers bound to FXML
     │       └── auth                     # authentication manager + sessions
     │       └── model                    # object classes
     └── resources/
         └── com.app.studysnap/
             ├── content.fxml             # UI layout files
             └── styles
                 └── style.css            # different css files
```