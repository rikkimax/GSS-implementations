package gss.ircbot.models

import javax.persistence.Entity
import javax.persistence.Id

@Entity
class BotProfile implements Serializable {
    @Id
    private String nick;
    @Id
    private int id;
    private String name;
    private int mode;
    private String nickServPass;
    private String operUser;
    private String operPass;

    BotProfile() {
    }

    BotProfile(String nick, int id, String name, int mode, String nickServPass, String operUser, String operPass) {
        this.nick = nick
        this.id = id;
        this.name = name
        this.mode = mode
        this.nickServPass = nickServPass
        this.operUser = operUser
        this.operPass = operPass
    }

    int getId() {
        return id;
    }

    String getName() {
        return this.name;
    }

    int getMode() {
        return this.mode;
    }

    String getNick() {
        return this.nick;
    }

    String getNickServPass() {
        return this.nickServPass;
    }

    String getOperPass() {
        return this.operPass;
    }

    String getOperUser() {
        return this.operUser;
    }

    void setId(int id) {
        this.id = id;
    }

    void setName(String name) {
        this.name = name;
    }

    void setMode(int mode) {
        this.mode = mode;
    }

    void setNick(String nick) {
        this.nick = nick;
    }

    void setNickServPass(String nickServPass) {
        this.nickServPass = nickServPass;
    }

    void setOperPass(String operPass) {
        this.operPass = operPass;
    }

    void setOperUser(String operUser) {
        this.operUser = operUser;
    }
}
