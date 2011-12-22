package gss.ircbot.models.queues

import javax.persistence.*
import gss.ircbot.models.IrcServer

@Entity
class IrcMessageQueue implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;
    private Long created;
    private Boolean read;
    @Column(name = "MessageFrom")
    private String from;
    private String fromIdent;
    private String fromHost
    @Column(name = "MessageTo")
    private String to;
    private String message;
    private Boolean isNotice;
    @ManyToOne
    private IrcServer server;

    IrcMessageQueue() {
    }

    IrcMessageQueue(String to, String from, String message) {
        created = System.currentTimeMillis();
        setTo(to);
        setFrom(from);
        setMessage(message);
        read = false;
        isNotice = false;
    }

    Boolean getRead() {
        return read;
    }

    void setRead(Boolean read) {
        this.read = read;
    }

    Long getId() {
        return id;
    }

    void setId(Long id) {
        this.id = id;
    }

    IrcServer getServer() {
        return server;
    }

    void setServer(IrcServer server) {
        this.server = server;
    }

    String getFrom() {
        return from;
    }

    void setFrom(String from) {
        this.from = from;
    }

    String getFromIdent() {
        return fromIdent;
    }

    void setFromIdent(String fromIdent) {
        this.fromIdent = fromIdent;
    }

    String getFromHost() {
        return fromHost;
    }

    void setFromHost(String fromHost) {
        this.fromHost = fromHost;
    }

    String getTo() {
        return to;
    }

    void setTo(String to) {
        this.to = to;
    }

    String getMessage() {
        return message;
    }

    void setMessage(String message) {
        this.message = message;
    }

    Long getCreated() {
        return this.created
    }

    void setCreated(Long time) {
        created = time;
    }

    Boolean isNotice() {
        return isNotice;
    }

    void setIsNotice(Boolean isNotice) {
        this.isNotice = isNotice;
    }
}
