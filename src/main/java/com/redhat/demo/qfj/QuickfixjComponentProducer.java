package com.redhat.demo.qfj;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import quickfix.*;

import javax.enterprise.inject.Produces;

public class QuickfixjComponentProducer {

    @Produces
    public SessionSettings create(@ConfigProperty(name = "quickfixj.sessionSettingsPath") String sessionSettingsPath) throws ConfigError {
        return new SessionSettings(getClass().getClassLoader().getResource(sessionSettingsPath).getFile());
    }

    @Produces
    public MessageStoreFactory messageStoreFactory(SessionSettings sessionSettings) {
        return new MemoryStoreFactory();
    }

    @Produces
    public LogFactory logFactory(SessionSettings sessionSettings) {
        return new ScreenLogFactory();
    }

    @Produces
    public MessageFactory messageFactory(SessionSettings sessionSettings) {
        return new DefaultMessageFactory();
    }

    @Produces
    public Application application() {
        return new ApplicationAdapter();
    }

}
