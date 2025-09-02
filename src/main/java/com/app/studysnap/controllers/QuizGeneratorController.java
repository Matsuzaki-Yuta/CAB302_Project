package com.app.studysnap.controllers;

import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;

import java.io.File;
import java.util.List;

public class QuizGeneratorController {

    // Common UI
    @FXML private TabPane tabPane;
    @FXML private TextArea previewArea;
    @FXML private ProgressIndicator progress;

    // Upload tab
    @FXML private StackPane fileDropZone;
    @FXML private Button chooseFileBtn;
    @FXML private Label chosenFileLabel;
    @FXML private CheckBox includeAnswersUpload;
    private File selectedFile;

    // Paste tab
    @FXML private TextArea pastedTextArea;
    @FXML private CheckBox includeAnswersPaste;

    // Prompt tab (MCQ assumed)
    @FXML private TextArea promptTextArea;
    @FXML private CheckBox includeAnswersPrompt;

    // Public tab
    @FXML private TextField searchField;
    @FXML private TableView<PublicQuizRow> publicTable;
    @FXML private TableColumn<PublicQuizRow, String> colName, colSubject, colDescription, colAuthor;

    @FXML
    public void initialize() {
        // Drag & drop setup for file area
        if (fileDropZone != null) {
            fileDropZone.setOnDragOver(this::onDragOver);
            fileDropZone.setOnDragDropped(this::onDragDropped);
        }

        // Public table setup (empty for now, but columns wired)
        if (publicTable != null) {
            colName.setCellValueFactory(new PropertyValueFactory<>("name"));
            colSubject.setCellValueFactory(new PropertyValueFactory<>("subject"));
            colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
            colAuthor.setCellValueFactory(new PropertyValueFactory<>("author"));

            // make sure columns are scaled properly with screen
            publicTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_FLEX_LAST_COLUMN);
            var tableWidth = publicTable.widthProperty().subtract(18);

            colName.prefWidthProperty().bind(tableWidth.multiply(0.25));
            colSubject.prefWidthProperty().bind(tableWidth.multiply(0.25));
            colDescription.prefWidthProperty().bind(tableWidth.multiply(0.35));
            colAuthor.prefWidthProperty().bind(tableWidth.multiply(0.15));

            // minimum size so they don’t collapse
            colName.setMinWidth(120);
            colSubject.setMinWidth(100);
            colDescription.setMinWidth(160);
            colAuthor.setMinWidth(100);
        }
    }

    // ---------------- Upload ----------------
    @FXML private void onChooseFile() {
        FileChooser fc = new FileChooser();
        fc.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Documents", List.of("*.pdf", "*.txt"))
        );
        File f = fc.showOpenDialog(chooseFileBtn.getScene().getWindow());
        if (f != null) {
            selectedFile = f;
            chosenFileLabel.setText(f.getName());
        }
    }

    private void onDragOver(DragEvent e) {
        Dragboard db = e.getDragboard();
        if (db.hasFiles()) e.acceptTransferModes(TransferMode.COPY);
        e.consume();
    }

    private void onDragDropped(DragEvent e) {
        Dragboard db = e.getDragboard();
        if (db.hasFiles()) {
            selectedFile = db.getFiles().getFirst();
            chosenFileLabel.setText(selectedFile.getName());
            e.setDropCompleted(true);
        } else {
            e.setDropCompleted(false);
        }
        e.consume();
    }

    @FXML private void onGenerateFromUpload() {
        if (selectedFile == null) {
            alert(Alert.AlertType.WARNING, "No file selected", "Choose a PDF/TXT first.");
            return;
        }
        disableAll(true);
        // TODO Backend: Extract text from selectedFile and produce MCQs (off UI thread)
        // runAsync(() -> quizService.generateFromFile(selectedFile, includeAnswersUpload.isSelected()),
        //          result -> previewArea.setText(result));
        disableAll(false);
    }

    // ---------------- Paste ----------------
    @FXML private void onGenerateFromPaste() {
        String text = pastedTextArea.getText();
        if (text == null || text.isBlank()) {
            alert(Alert.AlertType.WARNING, "Nothing to generate", "Paste some content first.");
            return;
        }
        disableAll(true);
        // TODO Backend: generate MCQs from 'text' (off UI thread)
        // runAsync(() -> quizService.generateFromText(text, includeAnswersPaste.isSelected()),
        //          result -> previewArea.setText(result));
        disableAll(false);
    }

    @FXML private void onResetPaste() {
        pastedTextArea.clear();
    }

    // ---------------- Prompt ----------------
    @FXML private void onGenerateFromPrompt() {
        String prompt = promptTextArea.getText();
        if (prompt == null || prompt.isBlank()) {
            alert(Alert.AlertType.WARNING, "Empty prompt", "Write a short prompt.");
            return;
        }
        boolean includeAnswers = includeAnswersPrompt.isSelected();

        disableAll(true);
        // TODO Backend: generate MCQs from prompt (off UI thread)
        // runAsync(() -> quizService.generateFromPrompt(prompt, n, includeAnswers),
        //          result -> previewArea.setText(result));
        disableAll(false);
    }

    @FXML private void onResetPrompt() {
        promptTextArea.clear();
    }

    // ---------------- Public ----------------
    @FXML private void onRefreshPublic() {
        String q = searchField.getText();
        disableAll(true);
        // TODO Backend: fetch summaries list (off UI thread)
        // runAsync(() -> quizDao.findPublic(q),
        //          list -> publicTable.getItems().setAll(list));
        disableAll(false);
    }

    @FXML private void onDownloadSelected() {
        PublicQuizRow sel = publicTable.getSelectionModel().getSelectedItem();
        if (sel == null) {
            alert(Alert.AlertType.INFORMATION, "No selection", "Pick a quiz row to download.");
            return;
        }
        disableAll(true);
        // TODO Backend: load full quiz by id (off UI thread)
        // runAsync(() -> quizDao.getById(sel.id()),
        //          quiz -> previewArea.setText(quiz.renderAsText(true)));
        disableAll(false);
    }

    // ---------------- Save & Export ----------------
    @FXML private void onSave() {
        Dialog<ButtonType> dlg = new Dialog<>();
        dlg.setTitle("Save Quiz");
        dlg.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        TextField nameField = new TextField();
        TextField subjectField = new TextField();
        TextArea descArea = new TextArea(); descArea.setPrefRowCount(3);
        CheckBox publicCheck = new CheckBox("Make it public?");

        GridPane gp = new GridPane();
        gp.setHgap(10); gp.setVgap(8); gp.setPadding(new Insets(10));
        gp.addRow(0, new Label("Quiz name:"), nameField);
        gp.addRow(1, new Label("Subject:"), subjectField);
        gp.addRow(2, new Label("Description:"), descArea);
        gp.add(publicCheck, 1, 3);
        dlg.getDialogPane().setContent(gp);

        if (dlg.showAndWait().orElse(ButtonType.CANCEL) != ButtonType.OK) return;

        String nm = nameField.getText();
        String subj = subjectField.getText();
        String desc = descArea.getText();
        boolean isPublic = publicCheck.isSelected();
        String content = previewArea.getText();

        disableAll(true);
        // TODO Backend: persist using your DAO (off UI thread)
        // runAsync(() -> quizDao.saveText(nm, subj, desc, content, isPublic),
        //          _v -> alert(Alert.AlertType.INFORMATION, "Saved", "Quiz saved successfully."));
        disableAll(false);
    }

    @FXML private void onExportPdf() {
        // TODO Backend: export previewArea.getText() to PDF (off UI thread)
        // runAsync(() -> pdfService.export(previewArea.getText()), _v -> alert(...));
        alert(Alert.AlertType.INFORMATION, "Export", "PDF export placeholder. Add implementation.");
    }

    // ---------------- Helpers ----------------
    private void disableAll(boolean busy) {
        progress.setVisible(busy);
        tabPane.setDisable(busy);
    }

    // Async to run work off the UI thread and switch back on success
    private <T> void runAsync(java.util.concurrent.Callable<T> background, java.util.function.Consumer<T> onSuccess) {
        Task<T> task = new Task<>() {
            @Override protected T call() throws Exception { return background.call(); }
        };
        task.setOnSucceeded(e -> onSuccess.accept(task.getValue()));
        task.setOnFailed(e -> alert(Alert.AlertType.ERROR, "Error", String.valueOf(task.getException())));
        new Thread(task, "quiz-bg").start();
    }

    private void alert(Alert.AlertType type, String header, String content) {
        Alert a = new Alert(type, content, ButtonType.OK);
        a.setHeaderText(header);
        a.showAndWait();
    }

    // Minimal row model for the public list (adjust later if you add IDs)
    public record PublicQuizRow(String name, String author, int questions, String updated) {}
}