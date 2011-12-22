package gss.ircbot.models

import javax.persistence.Entity
import javax.persistence.Id

@Entity
class UserAuthentication implements Serializable {
    @Id
    User user;
    @Id
    IrcServer server;
    @Id
    String nickname;

    UserAuthentication() {
    }

    UserAuthentication(User user, IrcServer server, String nickname) {
        this.user = user;
        this.server = server;
        this.nickname = nickname;
    }

    void setUser(User user) {
        this.user = user;
    }

    User getUser() {
        return user;
    }

    void setServer(IrcServer server) {
        this.server = server;
    }

    IrcServer getServer() {
        return server;
    }

    void setNickName(String nickname) {
        this.nickname = nickname;
    }

    String getNickName() {
        return nickname;
    }
}
