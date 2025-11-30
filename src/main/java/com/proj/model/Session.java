package com.proj.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import com.proj.observer.Observable;
import com.proj.state.OpenSessionState;
import com.proj.state.SessionState;

public class Session extends Observable {
    private final Course course;
    private final LocalDate startAt;
    private final LocalDate endAt;
    private final int maxPlaces;
    private final Teacher teacher;
    private final Queue<Student> waitlist = new LinkedList<>();

    public Session(Course course, LocalDate startAt, LocalDate endAt, int maxPlaces, Teacher teacher) {
        this.course = course;
        this.startAt = startAt;
        this.endAt = endAt;
        this.maxPlaces = maxPlaces;
        this.teacher = teacher;
        addObserver(teacher);
    }

    private final String id = java.util.UUID.randomUUID().toString();
    private final HashMap<String, Student> students = new HashMap<>();
    private final HashMap<String, Inscription> inscriptions = new HashMap<>();
    private SessionState state = new OpenSessionState();

    public void subscribe(Student student) {
        if (isSubscribed(student.getId())) {
            throw new IllegalStateException("Student is already subscribed to this session.");
        }
        state.subscribe(this, student);
        if (isFull()) {
            addToWaitlist(student);
            return;
        }
        students.put(student.getId(), student);
        addObserver(student);
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

        state.unsubscribe(this, student);

        students.remove(studentId);
        removeObserver(student);

        inscriptions.remove(studentId);
        student.removeInscription(getId());
        promoteFromWaitlist();
    }

    public void start(Teacher teacher) {
        state.start(this, teacher);
    }

    public void cancel(Teacher teacher) {
        state.cancel(this, teacher);
    }

    public void end(Teacher teacher) {
        state.end(this, teacher);
        for (Inscription ins : inscriptions.values()) {
            ins.setCompleted(true);
        }
    }

    public boolean isFull() {
        return students.size() >= maxPlaces;
    }

    public int availablePlaces() {
        return maxPlaces - students.size();
    }

    private void addToWaitlist(Student student) {
        if (!waitlist.contains(student)) {
            waitlist.offer(student);
            notifyObservers("Student " + student.getName() + " added to waitlist for session " + id);
        }
    }

    private void promoteFromWaitlist() {
        if (!isFull() && !waitlist.isEmpty()) {
            Student next = waitlist.poll();
            subscribe(next); // will handle full transition / notifications
            notifyObservers("Student " + next.getName() + " promoted from waitlist to session " + id);
        }
    }

    public void setState(SessionState state) {
        this.state = state;
        String message = state.getNotifyMessage();

        System.out.println(message);
        notifyObservers(message);
    }

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

    public Teacher getTeacher() {
        return teacher;
    }

    public List<Student> getStudents() {
        return new ArrayList<>(students.values());
    }

    public List<Student> getWaitlist() {
        return new ArrayList<>(waitlist);
    }

    public List<Inscription> getInscriptions() {
        return new ArrayList<>(inscriptions.values());
    }

    public SessionState getState() {
        return state;
    }
}
