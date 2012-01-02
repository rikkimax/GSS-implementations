package gss.bot.models

import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.ManyToOne

@Entity
class Server implements Serializable {
    @Id
    private String name;
    private String host;
    private int port;
    private Boolean simpleId;
    private String handler
    private String pass;
    @ManyToOne
    private BotProfile defaultProfile;

    Server() {
    }

    Server(String name, String host, int port, Boolean simpleId, String handler, BotProfile botProfile) {
        this.name = name;
        setHost(host);
        setPort(port);
        setSimpleId(simpleId);
        setHandler(handler);
        setDefaultProfile(botProfile);
    }

    synchronized String getName() {
        return name;
    }

    synchronized String getHost() {
        return host;
    }

    synchronized void setHost(String host) {
        this.host = host;
    }

    synchronized int getPort() {
        return port;
    }

    synchronized void setPort(int port) {
        this.port = port;
    }

    synchronized Boolean getSimpleId() {
        return simpleId;
    }

    synchronized void setSimpleId(Boolean simpleId) {
        this.simpleId = simpleId;
    }

    synchronized String getHandler() {
        return handler;
    }

    synchronized void setHandler(String handler) {
        this.handler = handler;
    }

    synchronized BotProfile getDefaultProfile() {
        return defaultProfile;
    }

    synchronized void setDefaultProfile(BotProfile botProfile) {
        defaultProfile = botProfile;
    }

    @Override
    boolean equals(Object obj) {
        if (obj instanceof Server)
            return obj.getName() == name;
        return false;
    }
}
