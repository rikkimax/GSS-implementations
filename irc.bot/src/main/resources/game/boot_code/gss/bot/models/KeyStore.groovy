package gss.bot.models

import javax.persistence.Entity
import javax.persistence.Id

@Entity
class KeyStore implements Serializable {
    @Id
    private String id;
    private String value;

    KeyStore() {
    }

    KeyStore(String key, String value) {
        this.id = key;
        this.value = value;
    }

    String getId() {
        return id;
    }

    void setId(String id) {
        this.id = id;
    }

    String getValue() {
        return value;
    }

    void setValue(String time) {
        this.value = time;
    }
}
