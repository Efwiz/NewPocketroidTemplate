package org.antlr.runtime.debug;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import org.antlr.runtime.BaseRecognizer;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.Token;
import org.antlr.runtime.tree.TreeAdaptor;

/* JADX INFO: loaded from: classes12.dex */
public class DebugEventSocketProxy extends BlankDebugEventListener {
    public static final int DEFAULT_DEBUGGER_PORT = 49100;
    protected TreeAdaptor adaptor;
    protected String grammarFileName;
    protected BufferedReader in;
    protected PrintWriter out;
    protected int port;
    protected BaseRecognizer recognizer;
    protected ServerSocket serverSocket;
    protected Socket socket;

    @Override // org.antlr.runtime.debug.BlankDebugEventListener, org.antlr.runtime.debug.DebugEventListener
    public void commence() {
    }

    public DebugEventSocketProxy(BaseRecognizer baseRecognizer, TreeAdaptor treeAdaptor) {
        this(baseRecognizer, DEFAULT_DEBUGGER_PORT, treeAdaptor);
    }

    public DebugEventSocketProxy(BaseRecognizer baseRecognizer, int i, TreeAdaptor treeAdaptor) {
        this.port = DEFAULT_DEBUGGER_PORT;
        this.grammarFileName = baseRecognizer.getGrammarFileName();
        this.adaptor = treeAdaptor;
        this.port = i;
    }

    public void handshake() throws IOException {
        if (this.serverSocket == null) {
            ServerSocket serverSocket = new ServerSocket(this.port);
            this.serverSocket = serverSocket;
            Socket socketAccept = serverSocket.accept();
            this.socket = socketAccept;
            socketAccept.setTcpNoDelay(true);
            this.out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(this.socket.getOutputStream(), "UTF8")));
            this.in = new BufferedReader(new InputStreamReader(this.socket.getInputStream(), "UTF8"));
            this.out.println("ANTLR 2");
            this.out.println("grammar \"" + this.grammarFileName);
            this.out.flush();
            ack();
        }
    }

    @Override // org.antlr.runtime.debug.BlankDebugEventListener, org.antlr.runtime.debug.DebugEventListener
    public void terminate() {
        transmit("terminate");
        this.out.close();
        try {
            this.socket.close();
        } catch (IOException e) {
            e.printStackTrace(System.err);
        }
    }

    protected void ack() {
        try {
            this.in.readLine();
        } catch (IOException e) {
            e.printStackTrace(System.err);
        }
    }

    protected void transmit(String str) {
        this.out.println(str);
        this.out.flush();
        ack();
    }

    @Override // org.antlr.runtime.debug.BlankDebugEventListener, org.antlr.runtime.debug.DebugEventListener
    public void enterRule(String str, String str2) {
        transmit("enterRule\t" + str + "\t" + str2);
    }

    @Override // org.antlr.runtime.debug.BlankDebugEventListener, org.antlr.runtime.debug.DebugEventListener
    public void enterAlt(int i) {
        transmit("enterAlt\t" + i);
    }

    @Override // org.antlr.runtime.debug.BlankDebugEventListener, org.antlr.runtime.debug.DebugEventListener
    public void exitRule(String str, String str2) {
        transmit("exitRule\t" + str + "\t" + str2);
    }

    @Override // org.antlr.runtime.debug.BlankDebugEventListener, org.antlr.runtime.debug.DebugEventListener
    public void enterSubRule(int i) {
        transmit("enterSubRule\t" + i);
    }

    @Override // org.antlr.runtime.debug.BlankDebugEventListener, org.antlr.runtime.debug.DebugEventListener
    public void exitSubRule(int i) {
        transmit("exitSubRule\t" + i);
    }

    @Override // org.antlr.runtime.debug.BlankDebugEventListener, org.antlr.runtime.debug.DebugEventListener
    public void enterDecision(int i, boolean z) {
        transmit("enterDecision\t" + i + "\t" + z);
    }

    @Override // org.antlr.runtime.debug.BlankDebugEventListener, org.antlr.runtime.debug.DebugEventListener
    public void exitDecision(int i) {
        transmit("exitDecision\t" + i);
    }

    @Override // org.antlr.runtime.debug.BlankDebugEventListener, org.antlr.runtime.debug.DebugEventListener
    public void consumeToken(Token token) {
        transmit("consumeToken\t" + serializeToken(token));
    }

    @Override // org.antlr.runtime.debug.BlankDebugEventListener, org.antlr.runtime.debug.DebugEventListener
    public void consumeHiddenToken(Token token) {
        transmit("consumeHiddenToken\t" + serializeToken(token));
    }

    @Override // org.antlr.runtime.debug.BlankDebugEventListener, org.antlr.runtime.debug.DebugEventListener
    public void LT(int i, Token token) {
        if (token != null) {
            transmit("LT\t" + i + "\t" + serializeToken(token));
        }
    }

    @Override // org.antlr.runtime.debug.BlankDebugEventListener, org.antlr.runtime.debug.DebugEventListener
    public void mark(int i) {
        transmit("mark\t" + i);
    }

    @Override // org.antlr.runtime.debug.BlankDebugEventListener, org.antlr.runtime.debug.DebugEventListener
    public void rewind(int i) {
        transmit("rewind\t" + i);
    }

    @Override // org.antlr.runtime.debug.BlankDebugEventListener, org.antlr.runtime.debug.DebugEventListener
    public void rewind() {
        transmit("rewind");
    }

    @Override // org.antlr.runtime.debug.BlankDebugEventListener, org.antlr.runtime.debug.DebugEventListener
    public void beginBacktrack(int i) {
        transmit("beginBacktrack\t" + i);
    }

    @Override // org.antlr.runtime.debug.BlankDebugEventListener, org.antlr.runtime.debug.DebugEventListener
    public void endBacktrack(int i, boolean z) {
        transmit("endBacktrack\t" + i + "\t" + (z ? 1 : 0));
    }

    @Override // org.antlr.runtime.debug.BlankDebugEventListener, org.antlr.runtime.debug.DebugEventListener
    public void location(int i, int i2) {
        transmit("location\t" + i + "\t" + i2);
    }

    @Override // org.antlr.runtime.debug.BlankDebugEventListener, org.antlr.runtime.debug.DebugEventListener
    public void recognitionException(RecognitionException recognitionException) {
        StringBuilder sb = new StringBuilder(50);
        sb.append("exception\t");
        sb.append(recognitionException.getClass().getName());
        sb.append("\t");
        sb.append(recognitionException.index);
        sb.append("\t");
        sb.append(recognitionException.line);
        sb.append("\t");
        sb.append(recognitionException.charPositionInLine);
        transmit(sb.toString());
    }

    @Override // org.antlr.runtime.debug.BlankDebugEventListener, org.antlr.runtime.debug.DebugEventListener
    public void beginResync() {
        transmit("beginResync");
    }

    @Override // org.antlr.runtime.debug.BlankDebugEventListener, org.antlr.runtime.debug.DebugEventListener
    public void endResync() {
        transmit("endResync");
    }

    @Override // org.antlr.runtime.debug.BlankDebugEventListener, org.antlr.runtime.debug.DebugEventListener
    public void semanticPredicate(boolean z, String str) {
        StringBuffer stringBuffer = new StringBuffer(50);
        stringBuffer.append("semanticPredicate\t");
        stringBuffer.append(z);
        serializeText(stringBuffer, str);
        transmit(stringBuffer.toString());
    }

    @Override // org.antlr.runtime.debug.BlankDebugEventListener, org.antlr.runtime.debug.DebugEventListener
    public void consumeNode(Object obj) {
        StringBuffer stringBuffer = new StringBuffer(50);
        stringBuffer.append("consumeNode");
        serializeNode(stringBuffer, obj);
        transmit(stringBuffer.toString());
    }

    @Override // org.antlr.runtime.debug.BlankDebugEventListener, org.antlr.runtime.debug.DebugEventListener
    public void LT(int i, Object obj) {
        this.adaptor.getUniqueID(obj);
        this.adaptor.getText(obj);
        this.adaptor.getType(obj);
        StringBuffer stringBuffer = new StringBuffer(50);
        stringBuffer.append("LN\t");
        stringBuffer.append(i);
        serializeNode(stringBuffer, obj);
        transmit(stringBuffer.toString());
    }

    protected void serializeNode(StringBuffer stringBuffer, Object obj) {
        int line;
        int charPositionInLine;
        int uniqueID = this.adaptor.getUniqueID(obj);
        String text = this.adaptor.getText(obj);
        int type = this.adaptor.getType(obj);
        stringBuffer.append("\t");
        stringBuffer.append(uniqueID);
        stringBuffer.append("\t");
        stringBuffer.append(type);
        Token token = this.adaptor.getToken(obj);
        if (token != null) {
            line = token.getLine();
            charPositionInLine = token.getCharPositionInLine();
        } else {
            line = -1;
            charPositionInLine = -1;
        }
        stringBuffer.append("\t");
        stringBuffer.append(line);
        stringBuffer.append("\t");
        stringBuffer.append(charPositionInLine);
        int tokenStartIndex = this.adaptor.getTokenStartIndex(obj);
        stringBuffer.append("\t");
        stringBuffer.append(tokenStartIndex);
        serializeText(stringBuffer, text);
    }

    @Override // org.antlr.runtime.debug.BlankDebugEventListener, org.antlr.runtime.debug.DebugEventListener
    public void nilNode(Object obj) {
        transmit("nilNode\t" + this.adaptor.getUniqueID(obj));
    }

    @Override // org.antlr.runtime.debug.BlankDebugEventListener, org.antlr.runtime.debug.DebugEventListener
    public void errorNode(Object obj) {
        int uniqueID = this.adaptor.getUniqueID(obj);
        String string = obj.toString();
        StringBuffer stringBuffer = new StringBuffer(50);
        stringBuffer.append("errorNode\t");
        stringBuffer.append(uniqueID);
        stringBuffer.append("\t0");
        serializeText(stringBuffer, string);
        transmit(stringBuffer.toString());
    }

    @Override // org.antlr.runtime.debug.BlankDebugEventListener, org.antlr.runtime.debug.DebugEventListener
    public void createNode(Object obj) {
        int uniqueID = this.adaptor.getUniqueID(obj);
        String text = this.adaptor.getText(obj);
        int type = this.adaptor.getType(obj);
        StringBuffer stringBuffer = new StringBuffer(50);
        stringBuffer.append("createNodeFromTokenElements\t");
        stringBuffer.append(uniqueID);
        stringBuffer.append("\t");
        stringBuffer.append(type);
        serializeText(stringBuffer, text);
        transmit(stringBuffer.toString());
    }

    @Override // org.antlr.runtime.debug.BlankDebugEventListener, org.antlr.runtime.debug.DebugEventListener
    public void createNode(Object obj, Token token) {
        transmit("createNode\t" + this.adaptor.getUniqueID(obj) + "\t" + token.getTokenIndex());
    }

    @Override // org.antlr.runtime.debug.BlankDebugEventListener, org.antlr.runtime.debug.DebugEventListener
    public void becomeRoot(Object obj, Object obj2) {
        transmit("becomeRoot\t" + this.adaptor.getUniqueID(obj) + "\t" + this.adaptor.getUniqueID(obj2));
    }

    @Override // org.antlr.runtime.debug.BlankDebugEventListener, org.antlr.runtime.debug.DebugEventListener
    public void addChild(Object obj, Object obj2) {
        transmit("addChild\t" + this.adaptor.getUniqueID(obj) + "\t" + this.adaptor.getUniqueID(obj2));
    }

    @Override // org.antlr.runtime.debug.BlankDebugEventListener, org.antlr.runtime.debug.DebugEventListener
    public void setTokenBoundaries(Object obj, int i, int i2) {
        transmit("setTokenBoundaries\t" + this.adaptor.getUniqueID(obj) + "\t" + i + "\t" + i2);
    }

    public void setTreeAdaptor(TreeAdaptor treeAdaptor) {
        this.adaptor = treeAdaptor;
    }

    public TreeAdaptor getTreeAdaptor() {
        return this.adaptor;
    }

    protected String serializeToken(Token token) {
        StringBuffer stringBuffer = new StringBuffer(50);
        stringBuffer.append(token.getTokenIndex());
        stringBuffer.append('\t');
        stringBuffer.append(token.getType());
        stringBuffer.append('\t');
        stringBuffer.append(token.getChannel());
        stringBuffer.append('\t');
        stringBuffer.append(token.getLine());
        stringBuffer.append('\t');
        stringBuffer.append(token.getCharPositionInLine());
        serializeText(stringBuffer, token.getText());
        return stringBuffer.toString();
    }

    protected void serializeText(StringBuffer stringBuffer, String str) {
        stringBuffer.append("\t\"");
        if (str == null) {
            str = "";
        }
        stringBuffer.append(escapeNewlines(str));
    }

    protected String escapeNewlines(String str) {
        return str.replaceAll("%", "%25").replaceAll("\n", "%0A").replaceAll("\r", "%0D");
    }
}
