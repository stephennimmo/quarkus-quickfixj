package com.redhat.demo.qfj;

import quickfix.SessionID;

public class SessionView {

    private SessionID sessionID;
    private boolean loggedIn;

    public SessionView(SessionID sessionID, boolean loggedIn) {
        this.sessionID = sessionID;
        this.loggedIn = loggedIn;
    }

    public SessionID getSessionID() {
        return sessionID;
    }

    public boolean isLoggedIn() {
        return loggedIn;
    }

}
