package org.resthub.identity.core.session;

import org.eclipse.jetty.server.session.HashSessionManager;
import org.eclipse.jetty.server.session.HashedSession;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Bastien on 27/06/14.
 */
public class TestHashedSession extends HashedSession {
    public TestHashedSession(HashSessionManager hashSessionManager, HttpServletRequest request) {
        super(hashSessionManager, request);
    }
}
