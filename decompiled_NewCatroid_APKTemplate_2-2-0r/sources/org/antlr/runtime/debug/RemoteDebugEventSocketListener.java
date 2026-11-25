package org.antlr.runtime.debug;

import com.google.firebase.analytics.FirebaseAnalytics;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ConnectException;
import java.net.Socket;
import java.util.StringTokenizer;
import org.antlr.runtime.CharStream;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.Token;
import org.antlr.runtime.tree.BaseTree;
import org.antlr.runtime.tree.Tree;
import org.jetbrains.kotlin.cli.common.arguments.K2JsArgumentConstants;

/* JADX INFO: loaded from: classes12.dex */
public class RemoteDebugEventSocketListener implements Runnable {
    static final int MAX_EVENT_ELEMENTS = 8;
    String event;
    public String grammarFileName;
    BufferedReader in;
    DebugEventListener listener;
    String machine;
    PrintWriter out;
    int port;
    public String version;
    Socket channel = null;
    int previousTokenIndex = -1;
    boolean tokenIndexesInvalid = false;

    public boolean tokenIndexesAreInvalid() {
        return false;
    }

    public static class ProxyToken implements Token {
        int channel;
        int charPos;
        int index;
        int line;
        String text;
        int type;

        @Override // org.antlr.runtime.Token
        public CharStream getInputStream() {
            return null;
        }

        @Override // org.antlr.runtime.Token
        public void setInputStream(CharStream charStream) {
        }

        public ProxyToken(int i) {
            this.index = i;
        }

        public ProxyToken(int i, int i2, int i3, int i4, int i5, String str) {
            this.index = i;
            this.type = i2;
            this.channel = i3;
            this.line = i4;
            this.charPos = i5;
            this.text = str;
        }

        @Override // org.antlr.runtime.Token
        public String getText() {
            return this.text;
        }

        @Override // org.antlr.runtime.Token
        public void setText(String str) {
            this.text = str;
        }

        @Override // org.antlr.runtime.Token
        public int getType() {
            return this.type;
        }

        @Override // org.antlr.runtime.Token
        public void setType(int i) {
            this.type = i;
        }

        @Override // org.antlr.runtime.Token
        public int getLine() {
            return this.line;
        }

        @Override // org.antlr.runtime.Token
        public void setLine(int i) {
            this.line = i;
        }

        @Override // org.antlr.runtime.Token
        public int getCharPositionInLine() {
            return this.charPos;
        }

        @Override // org.antlr.runtime.Token
        public void setCharPositionInLine(int i) {
            this.charPos = i;
        }

        @Override // org.antlr.runtime.Token
        public int getChannel() {
            return this.channel;
        }

        @Override // org.antlr.runtime.Token
        public void setChannel(int i) {
            this.channel = i;
        }

        @Override // org.antlr.runtime.Token
        public int getTokenIndex() {
            return this.index;
        }

        @Override // org.antlr.runtime.Token
        public void setTokenIndex(int i) {
            this.index = i;
        }

        public String toString() {
            String str;
            if (this.channel == 0) {
                str = "";
            } else {
                str = ",channel=" + this.channel;
            }
            return "[" + getText() + "/<" + this.type + ">" + str + "," + this.line + ":" + getCharPositionInLine() + ",@" + this.index + "]";
        }
    }

    public static class ProxyTree extends BaseTree {
        public int ID;
        public int charPos;
        public int line;
        public String text;
        public int tokenIndex;
        public int type;

        @Override // org.antlr.runtime.tree.Tree
        public Tree dupNode() {
            return null;
        }

        @Override // org.antlr.runtime.tree.Tree
        public int getTokenStopIndex() {
            return 0;
        }

        @Override // org.antlr.runtime.tree.Tree
        public void setTokenStartIndex(int i) {
        }

        @Override // org.antlr.runtime.tree.Tree
        public void setTokenStopIndex(int i) {
        }

        public ProxyTree(int i, int i2, int i3, int i4, int i5, String str) {
            this.ID = i;
            this.type = i2;
            this.line = i3;
            this.charPos = i4;
            this.tokenIndex = i5;
            this.text = str;
        }

        public ProxyTree(int i) {
            this.line = 0;
            this.charPos = -1;
            this.tokenIndex = -1;
            this.ID = i;
        }

        @Override // org.antlr.runtime.tree.Tree
        public int getTokenStartIndex() {
            return this.tokenIndex;
        }

        @Override // org.antlr.runtime.tree.Tree
        public int getType() {
            return this.type;
        }

        @Override // org.antlr.runtime.tree.Tree
        public String getText() {
            return this.text;
        }

        @Override // org.antlr.runtime.tree.BaseTree, org.antlr.runtime.tree.Tree
        public String toString() {
            return "fix this";
        }
    }

    public RemoteDebugEventSocketListener(DebugEventListener debugEventListener, String str, int i) throws IOException {
        this.listener = debugEventListener;
        this.machine = str;
        this.port = i;
        if (!openConnection()) {
            throw new ConnectException();
        }
    }

    protected void eventHandler() {
        try {
            handshake();
            this.event = this.in.readLine();
            while (true) {
                String str = this.event;
                if (str == null) {
                    break;
                }
                dispatch(str);
                ack();
                this.event = this.in.readLine();
            }
        } catch (Exception e) {
            System.err.println(e);
            e.printStackTrace(System.err);
        } finally {
            closeConnection();
        }
    }

    protected boolean openConnection() {
        try {
            Socket socket = new Socket(this.machine, this.port);
            this.channel = socket;
            socket.setTcpNoDelay(true);
            this.out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(this.channel.getOutputStream(), "UTF8")));
            this.in = new BufferedReader(new InputStreamReader(this.channel.getInputStream(), "UTF8"));
            return true;
        } catch (Exception e) {
            System.err.println(e);
            return false;
        }
    }

    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:34:0x005e -> B:54:0x0063). Please report as a decompilation issue!!! */
    protected void closeConnection() {
        try {
            try {
                try {
                    this.in.close();
                    this.in = null;
                    this.out.close();
                    this.out = null;
                    this.channel.close();
                    this.channel = null;
                    BufferedReader bufferedReader = this.in;
                    if (bufferedReader != null) {
                        try {
                            bufferedReader.close();
                        } catch (IOException e) {
                            System.err.println(e);
                        }
                    }
                    PrintWriter printWriter = this.out;
                    if (printWriter != null) {
                        printWriter.close();
                    }
                    Socket socket = this.channel;
                    if (socket != null) {
                        socket.close();
                    }
                } catch (Throwable th) {
                    BufferedReader bufferedReader2 = this.in;
                    if (bufferedReader2 != null) {
                        try {
                            bufferedReader2.close();
                        } catch (IOException e2) {
                            System.err.println(e2);
                        }
                    }
                    PrintWriter printWriter2 = this.out;
                    if (printWriter2 != null) {
                        printWriter2.close();
                    }
                    Socket socket2 = this.channel;
                    if (socket2 != null) {
                        try {
                            socket2.close();
                            throw th;
                        } catch (IOException e3) {
                            System.err.println(e3);
                            throw th;
                        }
                    }
                    throw th;
                }
            } catch (Exception e4) {
                System.err.println(e4);
                e4.printStackTrace(System.err);
                BufferedReader bufferedReader3 = this.in;
                if (bufferedReader3 != null) {
                    try {
                        bufferedReader3.close();
                    } catch (IOException e5) {
                        System.err.println(e5);
                    }
                }
                PrintWriter printWriter3 = this.out;
                if (printWriter3 != null) {
                    printWriter3.close();
                }
                Socket socket3 = this.channel;
                if (socket3 != null) {
                    socket3.close();
                }
            }
        } catch (IOException e6) {
            System.err.println(e6);
        }
    }

    protected void handshake() throws IOException {
        this.version = getEventElements(this.in.readLine())[1];
        this.grammarFileName = getEventElements(this.in.readLine())[1];
        ack();
        this.listener.commence();
    }

    protected void ack() {
        this.out.println("ack");
        this.out.flush();
    }

    protected void dispatch(String str) {
        String[] eventElements = getEventElements(str);
        if (eventElements != null) {
            String str2 = eventElements[0];
            if (str2 != null) {
                if (str2.equals("enterRule")) {
                    this.listener.enterRule(eventElements[1], eventElements[2]);
                    return;
                }
                if (eventElements[0].equals("exitRule")) {
                    this.listener.exitRule(eventElements[1], eventElements[2]);
                    return;
                }
                if (eventElements[0].equals("enterAlt")) {
                    this.listener.enterAlt(Integer.parseInt(eventElements[1]));
                    return;
                }
                if (eventElements[0].equals("enterSubRule")) {
                    this.listener.enterSubRule(Integer.parseInt(eventElements[1]));
                    return;
                }
                if (eventElements[0].equals("exitSubRule")) {
                    this.listener.exitSubRule(Integer.parseInt(eventElements[1]));
                    return;
                }
                if (eventElements[0].equals("enterDecision")) {
                    this.listener.enterDecision(Integer.parseInt(eventElements[1]), eventElements[2].equals("true"));
                    return;
                }
                if (eventElements[0].equals("exitDecision")) {
                    this.listener.exitDecision(Integer.parseInt(eventElements[1]));
                    return;
                }
                if (eventElements[0].equals(FirebaseAnalytics.Param.LOCATION)) {
                    this.listener.location(Integer.parseInt(eventElements[1]), Integer.parseInt(eventElements[2]));
                    return;
                }
                if (eventElements[0].equals("consumeToken")) {
                    ProxyToken proxyTokenDeserializeToken = deserializeToken(eventElements, 1);
                    if (proxyTokenDeserializeToken.getTokenIndex() == this.previousTokenIndex) {
                        this.tokenIndexesInvalid = true;
                    }
                    this.previousTokenIndex = proxyTokenDeserializeToken.getTokenIndex();
                    this.listener.consumeToken(proxyTokenDeserializeToken);
                    return;
                }
                if (eventElements[0].equals("consumeHiddenToken")) {
                    ProxyToken proxyTokenDeserializeToken2 = deserializeToken(eventElements, 1);
                    if (proxyTokenDeserializeToken2.getTokenIndex() == this.previousTokenIndex) {
                        this.tokenIndexesInvalid = true;
                    }
                    this.previousTokenIndex = proxyTokenDeserializeToken2.getTokenIndex();
                    this.listener.consumeHiddenToken(proxyTokenDeserializeToken2);
                    return;
                }
                if (eventElements[0].equals("LT")) {
                    this.listener.LT(Integer.parseInt(eventElements[1]), (Token) deserializeToken(eventElements, 2));
                    return;
                }
                if (eventElements[0].equals("mark")) {
                    this.listener.mark(Integer.parseInt(eventElements[1]));
                    return;
                }
                if (eventElements[0].equals("rewind")) {
                    String str3 = eventElements[1];
                    if (str3 != null) {
                        this.listener.rewind(Integer.parseInt(str3));
                        return;
                    } else {
                        this.listener.rewind();
                        return;
                    }
                }
                if (eventElements[0].equals("beginBacktrack")) {
                    this.listener.beginBacktrack(Integer.parseInt(eventElements[1]));
                    return;
                }
                if (eventElements[0].equals("endBacktrack")) {
                    this.listener.endBacktrack(Integer.parseInt(eventElements[1]), Integer.parseInt(eventElements[2]) == 1);
                    return;
                }
                if (eventElements[0].equals(K2JsArgumentConstants.RUNTIME_DIAGNOSTIC_EXCEPTION)) {
                    String str4 = eventElements[1];
                    String str5 = eventElements[2];
                    String str6 = eventElements[3];
                    String str7 = eventElements[4];
                    try {
                        RecognitionException recognitionException = (RecognitionException) Class.forName(str4).asSubclass(RecognitionException.class).newInstance();
                        recognitionException.index = Integer.parseInt(str5);
                        recognitionException.line = Integer.parseInt(str6);
                        recognitionException.charPositionInLine = Integer.parseInt(str7);
                        this.listener.recognitionException(recognitionException);
                        return;
                    } catch (ClassNotFoundException e) {
                        System.err.println("can't find class " + e);
                        e.printStackTrace(System.err);
                        return;
                    } catch (IllegalAccessException e2) {
                        System.err.println("can't access class " + e2);
                        e2.printStackTrace(System.err);
                        return;
                    } catch (InstantiationException e3) {
                        System.err.println("can't instantiate class " + e3);
                        e3.printStackTrace(System.err);
                        return;
                    }
                }
                if (eventElements[0].equals("beginResync")) {
                    this.listener.beginResync();
                    return;
                }
                if (eventElements[0].equals("endResync")) {
                    this.listener.endResync();
                    return;
                }
                if (eventElements[0].equals("terminate")) {
                    this.listener.terminate();
                    return;
                }
                if (eventElements[0].equals("semanticPredicate")) {
                    this.listener.semanticPredicate(Boolean.valueOf(eventElements[1]).booleanValue(), unEscapeNewlines(eventElements[2]));
                    return;
                }
                if (eventElements[0].equals("consumeNode")) {
                    this.listener.consumeNode(deserializeNode(eventElements, 1));
                    return;
                }
                if (eventElements[0].equals("LN")) {
                    this.listener.LT(Integer.parseInt(eventElements[1]), deserializeNode(eventElements, 2));
                    return;
                }
                if (eventElements[0].equals("createNodeFromTokenElements")) {
                    this.listener.createNode(new ProxyTree(Integer.parseInt(eventElements[1]), Integer.parseInt(eventElements[2]), -1, -1, -1, unEscapeNewlines(eventElements[3])));
                    return;
                }
                if (eventElements[0].equals("createNode")) {
                    this.listener.createNode(new ProxyTree(Integer.parseInt(eventElements[1])), new ProxyToken(Integer.parseInt(eventElements[2])));
                    return;
                }
                if (eventElements[0].equals("nilNode")) {
                    this.listener.nilNode(new ProxyTree(Integer.parseInt(eventElements[1])));
                    return;
                }
                if (eventElements[0].equals("errorNode")) {
                    this.listener.errorNode(new ProxyTree(Integer.parseInt(eventElements[1]), Integer.parseInt(eventElements[2]), -1, -1, -1, unEscapeNewlines(eventElements[3])));
                    return;
                }
                if (eventElements[0].equals("becomeRoot")) {
                    this.listener.becomeRoot(new ProxyTree(Integer.parseInt(eventElements[1])), new ProxyTree(Integer.parseInt(eventElements[2])));
                    return;
                } else if (eventElements[0].equals("addChild")) {
                    this.listener.addChild(new ProxyTree(Integer.parseInt(eventElements[1])), new ProxyTree(Integer.parseInt(eventElements[2])));
                    return;
                } else if (!eventElements[0].equals("setTokenBoundaries")) {
                    System.err.println("unknown debug event: " + str);
                    return;
                } else {
                    this.listener.setTokenBoundaries(new ProxyTree(Integer.parseInt(eventElements[1])), Integer.parseInt(eventElements[2]), Integer.parseInt(eventElements[3]));
                    return;
                }
            }
        }
        System.err.println("unknown debug event: " + str);
    }

    protected ProxyTree deserializeNode(String[] strArr, int i) {
        return new ProxyTree(Integer.parseInt(strArr[i]), Integer.parseInt(strArr[i + 1]), Integer.parseInt(strArr[i + 2]), Integer.parseInt(strArr[i + 3]), Integer.parseInt(strArr[i + 4]), unEscapeNewlines(strArr[i + 5]));
    }

    protected ProxyToken deserializeToken(String[] strArr, int i) {
        String str = strArr[i];
        String str2 = strArr[i + 1];
        String str3 = strArr[i + 2];
        String str4 = strArr[i + 3];
        String str5 = strArr[i + 4];
        return new ProxyToken(Integer.parseInt(str), Integer.parseInt(str2), Integer.parseInt(str3), Integer.parseInt(str4), Integer.parseInt(str5), unEscapeNewlines(strArr[i + 5]));
    }

    public void start() {
        new Thread(this).start();
    }

    @Override // java.lang.Runnable
    public void run() {
        eventHandler();
    }

    public String[] getEventElements(String str) {
        String strSubstring = null;
        if (str == null) {
            return null;
        }
        String[] strArr = new String[8];
        try {
            int iIndexOf = str.indexOf(34);
            int i = 0;
            if (iIndexOf >= 0) {
                String strSubstring2 = str.substring(0, iIndexOf);
                strSubstring = str.substring(iIndexOf + 1, str.length());
                str = strSubstring2;
            }
            StringTokenizer stringTokenizer = new StringTokenizer(str, "\t", false);
            while (stringTokenizer.hasMoreTokens()) {
                if (i >= 8) {
                    return strArr;
                }
                strArr[i] = stringTokenizer.nextToken();
                i++;
            }
            if (strSubstring != null) {
                strArr[i] = strSubstring;
            }
        } catch (Exception e) {
            e.printStackTrace(System.err);
        }
        return strArr;
    }

    protected String unEscapeNewlines(String str) {
        return str.replaceAll("%0A", "\n").replaceAll("%0D", "\r").replaceAll("%25", "%");
    }
}
