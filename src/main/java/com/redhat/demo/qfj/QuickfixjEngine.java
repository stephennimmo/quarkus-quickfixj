package com.redhat.demo.qfj;

import io.quarkus.runtime.ShutdownEvent;
import io.quarkus.runtime.StartupEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import quickfix.*;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@ApplicationScoped
public class QuickfixjEngine {

    private static final Logger LOGGER = LoggerFactory.getLogger(QuickfixjEngine.class);

    private final SessionSettings sessionSettings;
    private final MessageStoreFactory messageStoreFactory;
    private final LogFactory logFactory;
    private final MessageFactory messageFactory;
    private final Application application;

    private Set<Connector> connectorSet = new HashSet<>();

    public QuickfixjEngine(SessionSettings sessionSettings, MessageStoreFactory messageStoreFactory, LogFactory logFactory,
                           MessageFactory messageFactory, Application application) throws ConfigError {
        this.sessionSettings = sessionSettings;
        this.messageStoreFactory = messageStoreFactory;
        this.logFactory = logFactory;
        this.messageFactory = messageFactory;
        this.application = application;
    }

    void onStartupEvent(@Observes StartupEvent event) {
        LOGGER.info("The QuickfixjService is starting...");
        try {
            Connector acceptorConnector = new SocketAcceptor(application, messageStoreFactory, sessionSettings, logFactory, messageFactory);
            acceptorConnector.start();
            Connector initiatorConnector = new SocketInitiator(application, messageStoreFactory, sessionSettings, logFactory, messageFactory);
            initiatorConnector.start();
            connectorSet.addAll(List.of(acceptorConnector, initiatorConnector));
            LOGGER.debug("All connectors are started...");
        } catch (ConfigError e) {
            //TODO Handle error
            throw new RuntimeException(e);
        }
    }

    void onShutdownEvent(@Observes ShutdownEvent event) {
        LOGGER.info("The QuickfixjService is stopping...");
        connectorSet.forEach(Connector::stop);
    }

}
