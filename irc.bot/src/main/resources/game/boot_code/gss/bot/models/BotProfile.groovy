package gss.bot.models

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
    private String loginPassword;
    private String adminUser;
    private String adminPassword;

    BotProfile() {
    }

    BotProfile(String nick, int id, String name, int mode, String loginPassword, String adminUser, String adminPassword) {
        this.nick = nick
        this.id = id;
        this.name = name
        this.mode = mode
        this.loginPassword = loginPassword
        this.adminUser = adminUser
        this.adminPassword = adminPassword
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
        return this.loginPassword;
    }

    String getOperPass() {
        return this.adminPassword;
    }

    String getOperUser() {
        return this.adminUser;
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
        this.loginPassword = nickServPass;
    }

    void setOperPass(String operPass) {
        this.adminPassword = operPass;
    }

    void setOperUser(String operUser) {
        this.adminUser = operUser;
    }
}
