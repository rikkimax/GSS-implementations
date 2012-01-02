package gss.bot.models

import javax.persistence.Entity
import javax.persistence.Id

@Entity
class Channel implements Serializable {
    @Id
    private String chan;
    @Id
    private Server server;
    private String password;
    private Boolean manage;

    Channel() {
    }

    Channel(String chan, Server server, password, Boolean manage) {
        this.chan = chan;
        this.server = server;
        this.password = password;
        this.manage = manage;
    }

    void setChan(String chan) {
        this.chan = chan;
    }

    String getChan() {
        return chan;
    }

    void setServer(Server server) {
        this.server = server;
    }

    Server getServer() {
        return server;
    }

    void setPassword(String password) {
        this.password = password;
    }

    String getPassword() {
        return password;
    }

    void setManage(Boolean manage) {
        this.manage = manage;
    }

    Boolean getManage() {
        return manage;
    }

    @Override
    boolean equals(Object obj) {
        if (obj instanceof Channel)
            return obj.getChan() == chan && obj.getServer() == server;
        return false;
    }
}
