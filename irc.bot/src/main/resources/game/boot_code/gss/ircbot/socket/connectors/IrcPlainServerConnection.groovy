package gss.ircbot.socket.connectors

import gss.ircbot.models.IrcServer
import gss.login.socket.connectors.PlainServerConnection
import gss.login.socket.connectors.PlainServerConnectionHandler
import gss.run.LoginNode
import java.util.concurrent.ConcurrentHashMap
import org.apache.commons.lang.StringUtils
import org.hibernate.Session
import org.hibernate.criterion.Restrictions

class IrcPlainServerConnection extends PlainServerConnection {
    IrcPlainServerConnection(LoginNode loginNode) {
        super(loginNode);
    }

    @Override
    synchronized void setValues(HashMap<Object, Object> values) {
        otherSettings = new ConcurrentHashMap((Map) values);
        setValues();
    }

    synchronized void setValues() {
        String tcp;
        int port;
        Boolean simpleID = false;
        if (otherSettings.get("profile") != null) {
            Session session = loginNode.getSession();
            if (session != null) {
                session.beginTransaction();
                List items = session.createCriteria(IrcServer.class).add(Restrictions.eq("name", otherSettings.get("profile"))).list();
                if (items.size() > 0) {
                    IrcServer server = items.get(0);
                    tcp = server.host;
                    port = server.port;
                    simpleID = server.simpleId;
                    println("server: " + server);
                    if (server.handler != null) {
                        def result = Eval.xy(this, loginNode, "return new ${server.handler}(x, y);");
                        if (result != null)
                            if (result instanceof PlainServerConnectionHandler)
                                ioHandler = result;
                    }
                }
            }
        }
        if (otherSettings.get("host") != null)
            tcp = (String) otherSettings.get("host");
        else if (otherSettings.get("ip") != null)
            tcp = (String) otherSettings.get("ip");
        if (otherSettings.get("port") != null)
            port = (Integer) otherSettings.get("port");
        this.server = tcp;
        this.port = port;
        println("tcp:${tcp}, port:${port}, other:${otherSettings}");
        if (otherSettings.get("simpleID") != null)
            simpleID = otherSettings.get("simpleID");
        else if (otherSettings.get("simpleId") != null)
            simpleID = otherSettings.get("simpleId");
        else if (otherSettings.get("simpleid") != null)
            simpleID = otherSettings.get("simpleid");
        String ip;
        if (server != null)
            ip = server.replace(".", "");
        else
            ip = "";
        String ipPort;
        if (port != null)
            ipPort = port + "." + ip;
        else
            ipPort = "." + ip;
        if (!simpleID)
            id = ipPort;
        else {
            ipPort = StringUtils.reverse(ipPort);
            ipPort = "plain." + ipPort + ".message";
            int length = ipPort.length() / 3;
            if ((ipPort.length() / 3) - length > 0)
                length++;
            String[] parts = new String[length];
            int done = 0;
            for (int i = 0; i < ipPort.length(); i++) {
                if (i % 3 == 0)
                    parts[done] = ipPort.charAt(i).toString();
                else
                    parts[done] += ipPort.charAt(i);
                if (i % 3 == 2)
                    done++;
            }
            String out = parts[parts.size() - 1] + parts[0] + parts[(int) (parts.length / 2)];
            parts.each {
                out += it;
            }
            id = out;
        }
        if (otherSettings.get("handler") != null) {
            def result = Eval.xy(this, loginNode, "return new ${otherSettings.get("handler")}(x, y);");
            if (result != null)
                if (result instanceof PlainServerConnectionHandler)
                    ioHandler = result;
        }
        if (otherSettings.get("timeout") != null)
            timeout = new Long(otherSettings.get("timeout").toString());
        if (tcp == null || port == null)
            loginNode.getServersConnections().removeSocket(this);
    }
}
