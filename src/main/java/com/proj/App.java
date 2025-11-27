package com.proj;

import java.io.IOException;
import java.time.LocalDate;

import com.proj.model.Category;
import com.proj.model.Course;
import com.proj.model.Session;
import com.proj.model.Student;
import com.proj.model.Teacher;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * JavaFX App - Course Management System
 */
public class App extends Application {

    private static Scene scene;

    // Data collections
    private VBox mainContainer;
    private ListView<String> sessionListView;
    private ListView<String> enrolledStudentListView;
    private ListView<String> studentListView;
    private TextArea logArea;

    // Sample data
    private Course javaCourse;
    private Session javaSession;
    private Student student1, student2, student3;
    private Teacher teacher1;

    @Override
    public void start(Stage stage) throws IOException {
        // Initialize sample data
        initializeSampleData();

        // Create the UI
        createUI(stage);

        stage.setTitle("Course Management System");
        stage.show();
    }

    private void initializeSampleData() {
        // Create categories and courses
        javaCourse = new Course("Java Programming", 40, Category.COMPUTER_SCIENCE);

        // Create session
        LocalDate now = LocalDate.now();
        javaSession = new Session(javaCourse, now.plusDays(7), now.plusDays(21), 3);

        // Create students
        student1 = new Student("Alice Johnson", "alice@example.com");
        student2 = new Student("Bob Smith", "bob@example.com");
        student3 = new Student("Charlie Brown", "charlie@example.com");

        // Create teacher
        teacher1 = new Teacher("T001", "Dr. Sarah Wilson", "sarah.wilson@example.com");
        teacher1.addSpecialty(Category.COMPUTER_SCIENCE);

        // Add course to session
        javaCourse.addSession(javaSession);
    }

    private void createUI(Stage stage) {
        // Main container
        mainContainer = new VBox(10);
        mainContainer.setPadding(new Insets(15));

        // Title
        Label titleLabel = new Label("Course Management System");
        titleLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        // Course info
        VBox courseInfo = createCourseInfoPane();

        // Controls
        HBox controlsPane = createControlsPane();

        // Lists container
        HBox listsContainer = new HBox(15);

        // Student list
        VBox studentPane = new VBox(5);
        studentPane.getChildren().addAll(
                new Label("All Students:"),
                createStudentListView());

        // Session list
        VBox sessionPane = new VBox(5);
        sessionPane.getChildren().addAll(
                new Label("Sessions:"),
                createSessionListView());

        // Enrolled Student list
        VBox enrolledStudentPane = new VBox(5);
        enrolledStudentPane.getChildren().addAll(
                new Label("Enrolled Students:"),
                createEnrolledStudentListView());

        listsContainer.getChildren().addAll(studentPane, sessionPane, enrolledStudentPane);

        // Log area
        VBox logPane = new VBox(5);
        logPane.getChildren().addAll(
                new Label("Activity Log:"),
                createLogArea());

        // Add all to main container
        mainContainer.getChildren().addAll(
                titleLabel,
                courseInfo,
                controlsPane,
                listsContainer,
                logPane);

        // Create scene
        ScrollPane scrollPane = new ScrollPane(mainContainer);
        scrollPane.setFitToWidth(true);
        scene = new Scene(scrollPane, 800, 600);
        stage.setScene(scene);

        // Update initial display
        updateDisplay();
    }

    private VBox createCourseInfoPane() {
        VBox coursePane = new VBox(5);
        coursePane.setStyle("-fx-border-color: gray; -fx-border-radius: 5; -fx-padding: 10;");

        Label courseTitle = new Label("Course: " + javaCourse.getTitle());
        Label courseDuration = new Label("Duration: " + javaCourse.getDurationHours() + " hours");
        Label courseCategory = new Label("Category: " + javaCourse.getCategory());
        Label sessionInfo = new Label("Session: " + javaSession.getId() +
                " (Max: " + javaSession.getMaxPlaces() + " students)");

        coursePane.getChildren().addAll(courseTitle, courseDuration, courseCategory, sessionInfo);
        return coursePane;
    }

    private HBox createControlsPane() {
        HBox controlsPane = new HBox(10);
        controlsPane.setAlignment(Pos.CENTER);

        Button enrollStudent1Btn = new Button("Enroll Alice");
        Button enrollStudent2Btn = new Button("Enroll Bob");
        Button enrollStudent3Btn = new Button("Enroll Charlie");
        Button startSessionBtn = new Button("Start Session");
        Button endSessionBtn = new Button("End Session");
        Button cancelSessionBtn = new Button("Cancel Session");

        // Event handlers
        enrollStudent1Btn.setOnAction(e -> enrollStudent(student1));
        enrollStudent2Btn.setOnAction(e -> enrollStudent(student2));
        enrollStudent3Btn.setOnAction(e -> enrollStudent(student3));
        startSessionBtn.setOnAction(e -> startSession());
        endSessionBtn.setOnAction(e -> endSession());
        cancelSessionBtn.setOnAction(e -> cancelSession());

        controlsPane.getChildren().addAll(
                enrollStudent1Btn, enrollStudent2Btn, enrollStudent3Btn,
                new Separator(), startSessionBtn, endSessionBtn, cancelSessionBtn);

        return controlsPane;
    }

    private ListView<String> createSessionListView() {
        sessionListView = new ListView<>();
        sessionListView.setPrefHeight(100);
        return sessionListView;
    }

    private ListView<String> createStudentListView() {
        studentListView = new ListView<>();
        studentListView.setPrefHeight(100);
        return studentListView;
    }

    private ListView<String> createEnrolledStudentListView() {
        enrolledStudentListView = new ListView<>();
        enrolledStudentListView.setPrefHeight(100);
        return enrolledStudentListView;
    }

    private TextArea createLogArea() {
        logArea = new TextArea();
        logArea.setPrefHeight(150);
        logArea.setEditable(false);
        logArea.setWrapText(true);
        return logArea;
    }

    // Action methods
    private void enrollStudent(Student student) {
        try {
            javaSession.subscribe(student);
            logMessage("Successfully enrolled " + student.getName());
        } catch (IllegalStateException e) {
            logMessage("Error: " + e.getMessage());
        }
        updateDisplay();
    }

    private void startSession() {
        try {
            javaSession.start(teacher1);
            logMessage("Session started by " + teacher1.getName());
        } catch (IllegalStateException e) {
            logMessage("Error: " + e.getMessage());
        }
        updateDisplay();
    }

    private void endSession() {
        try {
            javaSession.end(teacher1);
            logMessage("Session ended by " + teacher1.getName());
        } catch (IllegalStateException e) {
            logMessage("Error: " + e.getMessage());
        }
        updateDisplay();
    }

    private void cancelSession() {
        try {
            javaSession.cancel(teacher1);
            logMessage("Session canceled by " + teacher1.getName());
        } catch (IllegalStateException e) {
            logMessage("Error: " + e.getMessage());
        }
        updateDisplay();
    }

    private void logMessage(String message) {
        logArea.appendText(LocalDate.now() + " - " + message + "\n");
    }

    private void updateDisplay() {
        // Update session info
        sessionListView.getItems().clear();
        sessionListView.getItems().add("Session: " + javaSession.getId());
        sessionListView.getItems().add("State: " + javaSession.getState().getClass().getSimpleName());
        sessionListView.getItems()
                .add("Enrolled: " + javaSession.getStudents().size() + "/" + javaSession.getMaxPlaces());

        // Update student list
        enrolledStudentListView.getItems().clear();
        for (Student student : javaSession.getStudents()) {
            enrolledStudentListView.getItems().add(student.getName() + " (" + student.getId() + ")");
        }

        if (javaSession.getStudents().isEmpty()) {
            enrolledStudentListView.getItems().add("No students enrolled");
        }
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }
}
