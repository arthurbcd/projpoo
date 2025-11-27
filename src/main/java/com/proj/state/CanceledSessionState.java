package com.proj.state;

import java.util.Date;

import com.proj.model.Teacher;

public class CanceledSessionState implements SessionState {
    private final Teacher teacher;
    private final Date cancelTime = new Date();

    public CanceledSessionState(Teacher teacher) {
        this.teacher = teacher;
    }

    @Override
    public String getNotifyMessage() {
        return "Session has been canceled by " + teacher.getName() + " at " + cancelTime + ".";
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public Date getCancelTime() {
        return cancelTime;
    }
}
