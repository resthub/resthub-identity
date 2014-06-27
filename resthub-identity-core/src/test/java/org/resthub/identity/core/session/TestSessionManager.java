package org.resthub.identity.core.session;

import org.eclipse.jetty.server.session.AbstractSession;
import org.eclipse.jetty.server.session.HashSessionIdManager;
import org.eclipse.jetty.server.session.HashSessionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.util.Random;

/**
 * Created by Bastien on 27/06/14.
 */
public class TestSessionManager extends HashSessionManager {

    private AbstractSession tweakedSession = null;

    private HashSessionManager hashSessionManager = new HashSessionManager();

    private Logger LOGGER = LoggerFactory.getLogger(TestSessionManager.class);

    public TestSessionManager(){
        super();
        hashSessionManager.setSessionIdManager(new HashSessionIdManager(new Random()));
    }

    @Override
    protected void addSession(AbstractSession session) {
        LOGGER.debug("into addSession {}", session);
    }

    @Override
    public AbstractSession getSession(String idInCluster) {
        LOGGER.debug("into getSession {}", tweakedSession);
        return tweakedSession;
    }

    @Override
    protected void invalidateSessions() throws Exception {

    }

    @Override
    protected AbstractSession newSession(HttpServletRequest request) {
        if (tweakedSession == null) {
            tweakedSession = new TestHashedSession(hashSessionManager, request);
        }
        LOGGER.debug("into newSession {}", tweakedSession);
        return tweakedSession;
    }

    @Override
    protected boolean removeSession(String idInCluster) {
        return true;
    }
}
