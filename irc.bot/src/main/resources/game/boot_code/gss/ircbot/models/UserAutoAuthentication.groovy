package gss.ircbot.models

import javax.persistence.Entity
import javax.persistence.Id
import gss.bot.models.Server
import gss.bot.models.User

@Entity
class UserAutoAuthentication implements Serializable {
    @Id
    private User user;
    @Id
    private Server server;
    @Id
    private String nick;
    @Id
    private String ident;
    @Id
    private String host;

    UserAutoAuthentication() {
        super();
    }

    UserAutoAuthentication(User user, Server server, String nick, String ident, String host) {
        this.user = user;
        this.server = server;
        this.nick = nick;
        this.ident = ident;
        this.host = host;
    }

    User getUser() {
        return user;
    }

    void setUser(User user) {
        this.user = user;
    }

    Server getServer() {
        return server;
    }

    void setServer(Server server) {
        this.server = server;
    }

    String getNick() {
        return nick;
    }

    void setNick(String nick) {
        this.nick = nick;
    }

    String getIdent() {
        return ident;
    }

    void setIdent(String ident) {
        this.ident = ident;
    }

    String getHost() {
        return host;
    }

    void setHost(String host) {
        this.host = host;
    }

    Boolean toAuthenticate(String nick, String ident, String host) {
        if (this.nick == nick && this.ident == ident && this.host == host)
            return true;
        if (this.nick != null && nick == "")
            return false;
        if (this.ident != null && ident == "")
            return false;
        if (this.host != null && host == "")
            return false;
        if ((this.nick == null && this.ident == ident && this.host == host) ||
                (this.nick == nick && this.ident == null && this.host == host) ||
                (this.nick == nick && this.ident == ident && this.host == null))
            return true;
        return false;
    }

    Boolean equals(UserAutoAuthentication userAutoAuthentication) {
        return userAutoAuthentication.user == user &&
                userAutoAuthentication.server == server &&
                userAutoAuthentication.nick == nick &&
                userAutoAuthentication.ident == ident &&
                userAutoAuthentication.host == host;
    }
}
