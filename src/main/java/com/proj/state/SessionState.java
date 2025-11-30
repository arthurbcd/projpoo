package com.proj.state;

import com.proj.model.Session;
import com.proj.model.Student;
import com.proj.model.Teacher;

public interface SessionState {
    String getNotifyMessage();

    default String getLabel() {
        return getClass().getSimpleName().replaceFirst("SessionState$", "").toLowerCase();
    }

    default void subscribe(Session session, Student student) {
        throw new IllegalStateException("Cannot subscribe to a " + getLabel() + " session.");
    }

    default void unsubscribe(Session session, Student student) {
        throw new IllegalStateException("Cannot unsubscribe from a " + getLabel() + " session.");
    }

    default void start(Session session, Teacher teacher) {
        throw new IllegalStateException("Cannot start a " + getLabel() + " session.");
    }

    default void cancel(Session session, Teacher teacher) {
        throw new IllegalStateException("Cannot cancel a " + getLabel() + " session.");
    }

    default void end(Session session, Teacher teacher) {
        throw new IllegalStateException("Cannot end a " + getLabel() + " session.");
    }
}
