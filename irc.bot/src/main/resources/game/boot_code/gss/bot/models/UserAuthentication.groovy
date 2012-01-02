package gss.bot.models

import javax.persistence.Entity
import javax.persistence.Id
import gss.bot.models.Server
import gss.bot.models.User

@Entity
class UserAuthentication implements Serializable {
    @Id
    User user;
    @Id
    Server server;
    @Id
    String nickname;

    UserAuthentication() {
    }

    UserAuthentication(User user, Server server, String nickname) {
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

    void setServer(Server server) {
        this.server = server;
    }

    Server getServer() {
        return server;
    }

    void setNickName(String nickname) {
        this.nickname = nickname;
    }

    String getNickName() {
        return nickname;
    }
}
