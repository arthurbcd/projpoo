package com.proj.state;

import com.proj.model.Session;
import com.proj.model.Student;
import com.proj.model.Teacher;

public class OpenSessionState implements SessionState {

    @Override
    public String getNotifyMessage() {
        return "Session is now open for subscriptions.";
    }

    @Override
    public void subscribe(Session session, Student student) {
        if (session.availablePlaces() == 1) {
            session.setState(new FullSessionState());
        }
    }

    @Override
    public void unsubscribe(Session session, Student student) {
        // Student can simply unsubscribe without state change.
    }

    @Override
    public void start(Session session, Teacher teacher) {
        session.setState(new StartedSessionState(teacher));
    }

    @Override
    public void cancel(Session session, Teacher teacher) {
        session.setState(new CanceledSessionState(teacher));
    }
}
