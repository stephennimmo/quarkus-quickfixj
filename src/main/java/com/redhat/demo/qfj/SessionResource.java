package com.redhat.demo.qfj;

import quickfix.Session;
import quickfix.SessionSettings;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

@Path("/sessions")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class SessionResource {

    private final SessionSettings sessionSettings;

    public SessionResource(SessionSettings sessionSettings) {
        this.sessionSettings = sessionSettings;
    }

    @GET
    public Response get() {
        List<SessionView> sessionList = new ArrayList<>();
        sessionSettings.sectionIterator().forEachRemaining(sessionID -> {
            Session session = Session.lookupSession(sessionID);
            sessionList.add(new SessionView(session.getSessionID(), session.isLoggedOn()));
        });
        return Response.ok(sessionList).build();
    }
}