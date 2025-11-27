package com.proj.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.proj.observer.Observable;
import com.proj.state.OpenSessionState;
import com.proj.state.SessionState;

// Map<Key, Value>
//

public class Session extends Observable {
    private final Course course;
    private final LocalDate startAt;
    private final LocalDate endAt;
    private final int maxPlaces;

    public Session(Course course, LocalDate startAt, LocalDate endAt, int maxPlaces) {
        this.course = course;
        this.startAt = startAt;
        this.endAt = endAt;
        this.maxPlaces = maxPlaces;

    }

    private final String id = java.util.UUID.randomUUID().toString();
    private final HashMap<String, Student> students = new HashMap<>();
    private final HashMap<String, Inscription> inscriptions = new HashMap<>();
    private SessionState state = new OpenSessionState();

    // Métodos públicos que delegam para o estado
    public void subscribe(Student student) {
        if (isSubscribed(student.getId())) {
            throw new IllegalStateException("Student is already subscribed to this session.");
        }
        state.subscribe(this, student); // arret si exception

        // Success
        students.put(student.getId(), student);
        addObserver(student);

        // Adiciona inscrição e observer
        Inscription inscription = new Inscription(student, this);
        inscriptions.put(student.getId(), inscription);
        student.addInscription(inscription);
    }

    public boolean isSubscribed(String studentId) {
        return students.containsKey(studentId);
    }

    public void unsubscribe(String studentId) {
        Student student = students.get(studentId);
        if (student == null) {
            throw new IllegalStateException("Student is not subscribed to this session.");
        }

        state.unsubscribe(this, student); // arret si exception

        // Success
        students.remove(studentId);
        removeObserver(student);

        // Remove subscription
        inscriptions.remove(studentId);
        student.removeInscription(getId()); // sessionId
    }

    public void start(Teacher teacher) {
        state.start(this, teacher);
    }

    public void cancel(Teacher teacher) {
        state.cancel(this, teacher);
    }

    public void end(Teacher teacher) {
        state.end(this, teacher);
    }

    // Métodos utilitários
    public boolean isFull() {
        return students.size() >= maxPlaces;
    }

    public int availablePlaces() {
        return maxPlaces - students.size();
    }

    public void setState(SessionState state) {
        this.state = state;
        String message = state.getNotifyMessage();

        System.out.println(message);
        notifyObservers(message);
    }

    // Getters
    public String getId() {
        return id;
    }

    public Course getCourse() {
        return course;
    }

    public LocalDate getStartAt() {
        return startAt;
    }

    public LocalDate getEndAt() {
        return endAt;
    }

    public int getMaxPlaces() {
        return maxPlaces;
    }

    public List<Student> getStudents() {
        return new ArrayList<>(students.values());
    }

    public List<Inscription> getInscriptions() {
        return new ArrayList<>(inscriptions.values());
    }

    public SessionState getState() {
        return state;
    }
}
