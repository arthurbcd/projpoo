package com.proj.state;

import java.util.Date;

import com.proj.model.Teacher;

public class EndedSessionState implements SessionState {
    final Teacher teacher;
    final Date endTime = new Date();

    public EndedSessionState(Teacher teacher) {
        this.teacher = teacher;
    }

    @Override
    public String getNotifyMessage() {
        return "Session has ended by " + teacher.getName() + " at " + endTime + ".";
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public Date getEndTime() {
        return endTime;
    }

}
