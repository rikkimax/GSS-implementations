package gss.bot.models

import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.OneToMany
import javax.persistence.Table

@Entity
@Table(name="UserData")
class User implements Serializable {
    @Id
    private String user;
    private String passwordHash;
    @OneToMany
    private List<Channel> channels;
    @OneToMany
    private List<UserAuthentication> authentications;

    User() {
    }

    User(String user, String password) {
        this.user = user;
        setPassword(password);
        channels = new ArrayList<Channel>();
        authentications = new ArrayList<UserAuthentication>();
    }

    void setUser(String user) {
        this.user = user;
    }

    String getUser() {
        return user;
    }

    void setPassword(String pass) {
        passwordHash = pass.hashCode() + ":" + pass.length().hashCode();
    }

    Boolean isPassword(String pass) {
        return passwordHash == pass.hashCode() + ":" + pass.length().hashCode();
    }

    void setChannels(List<Channel> channels) {
        this.channels = channels;
    }

    List<Channel> getChannels() {
        return channels;
    }

    void clearChannels() {
        channels.clear();
    }

    int countChannels() {
        return channels.size();
    }

    Channel getChannel(int id) {
        return channels.get(id);
    }

    void addChannel(Channel channel) {
        if (!channels.contains(channel))
            channels.add(channel);
    }

    void removeChannel(Channel channel) {
        channels.remove(channel);
    }

    Boolean hasChannel(Channel channel) {
        return channels.contains(channel);
    }

    void setAuthentications(List<UserAuthentication> authentications) {
        this.authentications = authentications;
    }

    List<UserAuthentication> getAuthentications() {
        return authentications;
    }

    void clearAuthentications() {
        authentications.clear();
    }

    int countAuthentications() {
        return authentications.size();
    }

    UserAuthentication getAuthentication(int id) {
        return authentications.get(id);
    }

    void addAuthentication(UserAuthentication channel) {
        if (!authentications.contains(channel))
            authentications.add(channel);
    }

    void removeAuthentication(UserAuthentication channel) {
        authentications.remove(channel);
    }

    Boolean hasAuthentication(UserAuthentication channel) {
        return authentications.contains(channel);
    }

    Boolean isAuthenticated(Server server) {
        authentications.each {
            if (it.getServer() == server)
                return true;
        }
        return false;
    }
}
