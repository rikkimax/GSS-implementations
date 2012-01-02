package gss.ircbot.models.queues

import gss.bot.models.User
import javax.persistence.*
import gss.bot.models.Server

@Entity
class UserActionQueue implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;
    private User user;
    private Server ircServer;
    @Enumerated(EnumType.STRING)
    private Action action;
    private Boolean read;
    private Long created;
    private String raw;

    enum Action {
        JOIN, PART, QUIT, NICK
    }

    UserActionQueue() {
    }

    UserActionQueue(User user, Server ircServer, Action action, String raw) {
        this.user = user;
        this.ircServer = ircServer;
        this.action = action;
        this.raw = raw;
        read = false;
        created = System.currentTimeMillis();
    }

    Long getId() {
        return id;
    }

    User getUser() {
        return user;
    }

    Server getIrcServer() {
        return ircServer;
    }

    Action getAction() {
        return action;
    }

    String getRaw() {
        return raw;
    }

    void setId(Long id) {
        this.id = id;
    }

    void setUser(User user) {
        this.user = user;
    }

    void setIrcServer(Server ircServer) {
        this.ircServer = ircServer;
    }

    void setAction(Action action) {
        this.action = action;
    }

    Long getCreated() {
        return this.created
    }

    void setCreated(Long time) {
        created = time;
    }

    Boolean getRead() {
        return read;
    }

    void setRead(Boolean read) {
        this.read = read;
    }

    void setRaw(String raw) {
        this.raw = raw;
    }
}
