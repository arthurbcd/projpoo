package com.proj.state;

import com.proj.model.Session;
import com.proj.model.Student;
import com.proj.model.Teacher;

public class FullSessionState implements SessionState {
    @Override
    public String getNotifyMessage() {
        return "Session is full. No more subscriptions allowed.";
    }

    @Override
    public void subscribe(Session session, Student student) {
        // La session gère la liste d'attente; aucun rejet ici
        // No-op: Session.subscribe() détectera l'état plein et mettra l'apprenant en
        // file d'attente
    }

    @Override
    public void unsubscribe(Session session, Student student) {
        session.setState(new OpenSessionState());
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
