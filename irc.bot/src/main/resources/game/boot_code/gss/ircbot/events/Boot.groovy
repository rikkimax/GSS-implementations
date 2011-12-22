package gss.ircbot.events

import gss.eventing.Event
import gss.ircbot.models.KeyStore
import gss.ircbot.models.UserAuthentication
import gss.run.Booter
import org.hibernate.Session
import org.hibernate.Transaction

class Boot extends Event {
    @Override
    void create(String key, Booter booter) {
        super.create(key, booter);
        assimilate("started");
    }

    @Override
    void run(String s, Object o, Object... objects) {
        println("${s} ${o} ${objects}");
        if (s == "started") {
            booter = (Booter) o;
            assimilate("timeStoredSet");
            Thread.start {
                while (booter.getKeepGoing()) {
                    Session session = booter.getSession();
                    Transaction transaction = session.beginTransaction();
                    KeyStore token = (KeyStore) session.get(KeyStore.class, "time");
                    if (token == null) {
                        token = new KeyStore("time", System.currentTimeMillis() + "");
                    } else {
                        if (System.currentTimeMillis() > Long.parseLong(token.getValue()) + 60000)
                            booter.eventManager.trigger("timeStoredSet", token);
                        token.setValue(System.currentTimeMillis() + "");
                    }
                    session = booter.getSession();
                    transaction = session.beginTransaction();
                    session.saveOrUpdate(token);
                    transaction.commit();
                    sleep(58500);
                }
            }
        } else if (s == "timeStoredSet") {
            // delete all authentication tokens in db..
            Session session = booter.getSession();
            Transaction transaction = session.beginTransaction();
            List<UserAuthentication> toDelete = session.createCriteria(UserAuthentication.class).list();
            toDelete.each {
                session.delete(it);
            }
            transaction.commit();
        }
    }

    @Override
    void destroy(String s) {
    }
}
