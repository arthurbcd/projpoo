package com.proj.state;

import java.util.Date;

import com.proj.model.Session;
import com.proj.model.Teacher;

public class StartedSessionState implements SessionState {
    final Teacher teacher;
    final Date startTime = new Date();

    public StartedSessionState(Teacher teacher) {
        this.teacher = teacher;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public Date getStartTime() {
        return startTime;
    }

    @Override
    public String getNotifyMessage() {
        return "Session started by " + teacher.getName() + " at " + startTime + ".";
    }

    @Override
    public void cancel(Session session, Teacher teacher) {
        session.setState(new CanceledSessionState(teacher));
    }

    @Override
    public void end(Session session, Teacher teacher) {
        session.setState(new EndedSessionState(teacher));
    }

}
