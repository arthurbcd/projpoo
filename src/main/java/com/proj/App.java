package com.proj;

import java.io.IOException;
import java.time.LocalDate;

import com.proj.model.Category;
import com.proj.model.Course;
import com.proj.model.Session;
import com.proj.model.Student;
import com.proj.model.Teacher;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
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

    private VBox mainContainer;
    private ListView<String> sessionListView;
    private ListView<String> enrolledStudentListView;
    private ListView<String> waitlistListView;
    private TextArea logArea;

    private Course javaCourse;
    private Session javaSession;
    private Teacher teacher1;

    @Override
    public void start(Stage stage) throws IOException {
        initializeSampleData();
        createUI(stage);

        stage.setTitle("Course Management System");
        stage.show();
    }

    private void initializeSampleData() {
        javaCourse = new Course("Java Programming", 40, Category.COMPUTER_SCIENCE);

        LocalDate now = LocalDate.now();
        javaSession = new Session(javaCourse, now.plusDays(7), now.plusDays(21), 3, teacher1);
        javaSession.addObserver(message -> logMessage("Event: " + message));

        teacher1 = new Teacher("T001", "Dr. Sarah Wilson", "sarah.wilson@example.com");
        teacher1.addSpecialty(Category.COMPUTER_SCIENCE);

        javaCourse.addSession(javaSession);
    }

    private void createUI(Stage stage) {
        mainContainer = new VBox(10);
        mainContainer.setPadding(new Insets(15));

        Label titleLabel = new Label("Course Management System");
        titleLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        VBox courseInfo = createCourseInfoPane();
        HBox controlsPane = createControlsPane();
        HBox listsContainer = new HBox(15);

        VBox sessionPane = new VBox(5);
        sessionPane.getChildren().addAll(
                new Label("Sessions:"),
                createSessionListView());

        VBox enrolledStudentPane = new VBox(5);
        enrolledStudentPane.getChildren().addAll(
                new Label("Enrolled Students:"),
                createEnrolledStudentListView());

        VBox waitlistPane = new VBox(5);
        waitlistPane.getChildren().addAll(
                new Label("Waitlist:"),
                createWaitlistListView());

        listsContainer.getChildren().addAll(sessionPane, enrolledStudentPane, waitlistPane);

        VBox logPane = new VBox(5);
        logPane.getChildren().addAll(
                new Label("Activity Log:"),
                createLogArea());

        mainContainer.getChildren().addAll(
                titleLabel,
                courseInfo,
                controlsPane,
                listsContainer,
                logPane);

        ScrollPane scrollPane = new ScrollPane(mainContainer);
        scrollPane.setFitToWidth(true);
        scene = new Scene(scrollPane, 800, 600);
        stage.setScene(scene);

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

        Button addStudentBtn = new Button("Add Student...");
        Button startSessionBtn = new Button("Start Session");
        Button endSessionBtn = new Button("End Session");
        Button cancelSessionBtn = new Button("Cancel Session");

        addStudentBtn.setOnAction(e -> {
            logMessage("UI: Add Student clicked");
            promptAndEnrollStudent();
        });
        startSessionBtn.setOnAction(e -> {
            logMessage("UI: Start Session clicked");
            startSession();
        });
        endSessionBtn.setOnAction(e -> {
            logMessage("UI: End Session clicked");
            endSession();
        });
        cancelSessionBtn.setOnAction(e -> {
            logMessage("UI: Cancel Session clicked");
            cancelSession();
        });

        controlsPane.getChildren().addAll(
                addStudentBtn,
                new Separator(), startSessionBtn, endSessionBtn, cancelSessionBtn);

        return controlsPane;
    }

    private ListView<String> createSessionListView() {
        sessionListView = new ListView<>();
        sessionListView.setPrefHeight(100);
        return sessionListView;
    }

    private ListView<String> createEnrolledStudentListView() {
        enrolledStudentListView = new ListView<>();
        enrolledStudentListView.setPrefHeight(100);
        return enrolledStudentListView;
    }

    private ListView<String> createWaitlistListView() {
        waitlistListView = new ListView<>();
        waitlistListView.setPrefHeight(100);
        return waitlistListView;
    }

    private TextArea createLogArea() {
        logArea = new TextArea();
        logArea.setPrefHeight(150);
        logArea.setEditable(false);
        logArea.setWrapText(true);
        return logArea;
    }

    private void enrollStudent(Student student) {
        try {
            System.out.println("[Action] Enrolling: " + student.getName());
            javaSession.subscribe(student);
            logMessage("Successfully enrolled " + student.getName());
            System.out.println("[Action] Enrolled: " + student.getName());
        } catch (IllegalStateException e) {
            logMessage("Error: " + e.getMessage());
            System.out.println("[Action] Enroll error: " + e.getMessage());
        }
        updateDisplay();
    }

    private void promptAndEnrollStudent() {
        javafx.scene.control.TextInputDialog nameDialog = new javafx.scene.control.TextInputDialog();
        nameDialog.setTitle("Add Student");
        nameDialog.setHeaderText("Enter student name");
        nameDialog.setContentText("Name:");
        var nameOpt = nameDialog.showAndWait();
        if (nameOpt.isEmpty() || nameOpt.get().trim().isEmpty()) {
            logMessage("Canceled: name required");
            System.out.println("[Dialog] Canceled: name required");
            logMessage("Dialog: Canceled (name required)");
            return;
        }

        javafx.scene.control.TextInputDialog emailDialog = new javafx.scene.control.TextInputDialog();
        emailDialog.setTitle("Add Student");
        emailDialog.setHeaderText("Enter student email");
        emailDialog.setContentText("Email:");
        var emailOpt = emailDialog.showAndWait();
        if (emailOpt.isEmpty() || emailOpt.get().trim().isEmpty()) {
            logMessage("Canceled: email required");
            System.out.println("[Dialog] Canceled: email required");
            logMessage("Dialog: Canceled (email required)");
            return;
        }

        Student s = new Student(nameOpt.get().trim(), emailOpt.get().trim());
        System.out.println("[Dialog] Created student: " + s.getName() + " (" + s.getEmail() + ")");
        logMessage("Dialog: Created student " + s.getName());
        enrollStudent(s);
    }

    private void startSession() {
        try {
            System.out.println("[Action] Starting session: " + javaSession.getId());
            javaSession.start(teacher1);
            logMessage("Session started by " + teacher1.getName());
            System.out.println("[Action] Session started");
            logMessage("Event: State -> " + javaSession.getState().getClass().getSimpleName());
        } catch (IllegalStateException e) {
            logMessage("Error: " + e.getMessage());
            System.out.println("[Action] Start error: " + e.getMessage());
        }
        updateDisplay();
    }

    private void endSession() {
        try {
            System.out.println("[Action] Ending session: " + javaSession.getId());
            javaSession.end(teacher1);
            logMessage("Session ended by " + teacher1.getName());
            System.out.println("[Action] Session ended");
            logMessage("Event: State -> " + javaSession.getState().getClass().getSimpleName());
        } catch (IllegalStateException e) {
            logMessage("Error: " + e.getMessage());
            System.out.println("[Action] End error: " + e.getMessage());
        }
        updateDisplay();
    }

    private void cancelSession() {
        try {
            System.out.println("[Action] Canceling session: " + javaSession.getId());
            javaSession.cancel(teacher1);
            logMessage("Session canceled by " + teacher1.getName());
            System.out.println("[Action] Session canceled");
            logMessage("Event: State -> " + javaSession.getState().getClass().getSimpleName());
        } catch (IllegalStateException e) {
            logMessage("Error: " + e.getMessage());
            System.out.println("[Action] Cancel error: " + e.getMessage());
        }
        updateDisplay();
    }

    private void logMessage(String message) {
        logArea.appendText(LocalDate.now() + " - " + message + "\n");
    }

    private void updateDisplay() {
        sessionListView.getItems().clear();
        sessionListView.getItems().add("Session: " + javaSession.getId());
        sessionListView.getItems().add("State: " + javaSession.getState().getClass().getSimpleName());
        sessionListView.getItems()
                .add("Enrolled: " + javaSession.getStudents().size() + "/" + javaSession.getMaxPlaces());

        enrolledStudentListView.getItems().clear();
        for (Student student : javaSession.getStudents()) {
            enrolledStudentListView.getItems().add(student.getName() + " (" + student.getId() + ")");
        }

        if (javaSession.getStudents().isEmpty()) {
            enrolledStudentListView.getItems().add("No students enrolled");
        }

        waitlistListView.getItems().clear();
        for (Student student : javaSession.getWaitlist()) {
            waitlistListView.getItems().add(student.getName() + " (" + student.getId() + ")");
        }
        if (javaSession.getWaitlist().isEmpty()) {
            waitlistListView.getItems().add("No students in waitlist");
        }
    }

    public static void main(String[] args) {
        launch();
    }
}
