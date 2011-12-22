package gss.ircbot.models.queues

import gss.ircbot.models.IrcServer
import gss.ircbot.models.User
import javax.persistence.*

@Entity
class UserActionQueue implements Serializable {
    private Long id;
    private User user;
    private IrcServer ircServer;
    private Action action;
    private Boolean read;
    private Long created;
    private String raw;

    enum Action {
        JOIN, PART, QUIT, NICK
    }

    UserActionQueue() {
    }

    UserActionQueue(User user, IrcServer ircServer, Action action, String raw) {
        this.user = user;
        this.ircServer = ircServer;
        this.action = action;
        this.raw = raw;
        read = false;
        created = System.currentTimeMillis();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", updatable = false, nullable = false)
    Long getId() {
        return id;
    }

    @Id
    User getUser() {
        return user;
    }

    @Id
    IrcServer getIrcServer() {
        return ircServer;
    }

    @Enumerated(EnumType.STRING)
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

    void setIrcServer(IrcServer ircServer) {
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
