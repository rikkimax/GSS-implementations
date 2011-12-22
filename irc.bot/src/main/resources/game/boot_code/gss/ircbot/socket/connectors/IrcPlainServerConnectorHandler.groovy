package gss.ircbot.socket.connectors

import gss.login.socket.connectors.PlainServerConnectionHandler
import gss.login.socket.connectors.PlainServerConnectionMessage
import gss.login.socket.connectors.PlainServerConnection
import gss.run.LoginNode

class IrcPlainServerConnectorHandler extends PlainServerConnectionHandler {

    IrcPlainServerConnectorHandler(PlainServerConnection server, LoginNode loginNode) {
        super(server, loginNode)
    }

    @Override
    Object messageProcess(PlainServerConnectionMessage plainServerConnectionMessage, Object o) {
        println(o);
        return o;
    }
}
