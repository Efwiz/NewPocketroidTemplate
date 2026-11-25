package org.jetbrains.kotlin.com.google.gwt.dev.js.rhino;

import androidx.core.app.NotificationCompat;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* JADX INFO: loaded from: classes3.dex */
public class Parser {
    private int functionNumber;
    private final boolean insideFunction;
    private int lastExprEndLine;
    private List<ParserListener> listeners;
    private final IRFactory nf;
    private boolean ok;
    private int sourceTop;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", "node", "org/jetbrains/kotlin/com/google/gwt/dev/js/rhino/Parser", "isValidDeleteArgument"));
    }

    public Parser(IRFactory iRFactory, boolean z) {
        this.nf = iRFactory;
        this.insideFunction = z;
    }

    public void addListener(ParserListener parserListener) {
        if (this.listeners == null) {
            this.listeners = new ArrayList();
        }
        this.listeners.add(parserListener);
    }

    private void mustMatchToken(TokenStream tokenStream, int i, String str) throws JavaScriptException, IOException {
        int token = tokenStream.getToken();
        if (token != i) {
            reportError(tokenStream, str);
            tokenStream.ungetToken(token);
        }
    }

    private void reportError(TokenStream tokenStream, String str) throws JavaScriptException {
        this.ok = false;
        tokenStream.reportSyntaxError(str, null);
        throw new JavaScriptException(str);
    }

    public Node parse(TokenStream tokenStream) throws IOException {
        this.ok = true;
        this.sourceTop = 0;
        this.functionNumber = 0;
        Node nodeCreateLeaf = this.nf.createLeaf(133, tokenStream.tokenPosition);
        while (true) {
            tokenStream.flags |= 16;
            int token = tokenStream.getToken();
            tokenStream.flags &= -17;
            if (token <= 0) {
                break;
            }
            if (token == 110) {
                try {
                    nodeCreateLeaf.addChildToBack(function(tokenStream, false));
                } catch (JavaScriptException unused) {
                    this.ok = false;
                }
            } else {
                tokenStream.ungetToken(token);
                nodeCreateLeaf.addChildToBack(statement(tokenStream));
            }
        }
        if (this.ok) {
            return this.nf.createScript(nodeCreateLeaf);
        }
        return null;
    }

    private Node parseFunctionBody(TokenStream tokenStream) throws IOException {
        int i = tokenStream.flags;
        tokenStream.flags &= -13;
        tokenStream.flags |= 2;
        Node nodeCreateBlock = this.nf.createBlock(tokenStream.tokenPosition);
        while (true) {
            try {
                try {
                    int iPeekToken = tokenStream.peekToken();
                    if (iPeekToken <= 0 || iPeekToken == 93) {
                        break;
                    }
                    if (iPeekToken == 110) {
                        tokenStream.getToken();
                        nodeCreateBlock.addChildToBack(function(tokenStream, false));
                    } else {
                        nodeCreateBlock.addChildToBack(statement(tokenStream));
                    }
                } catch (JavaScriptException unused) {
                    this.ok = false;
                }
            } catch (Throwable th) {
                tokenStream.flags = i;
                throw th;
            }
        }
        tokenStream.flags = i;
        return nodeCreateBlock;
    }

    private Node function(TokenStream tokenStream, boolean z) throws JavaScriptException, IOException {
        Node nodeMemberExpr;
        List<ParserListener> list = this.listeners;
        if (list != null) {
            Iterator<ParserListener> it = list.iterator();
            while (it.hasNext()) {
                it.next().functionStarted();
            }
        }
        CodePosition codePosition = tokenStream.tokenPosition;
        Node node = null;
        if (tokenStream.matchToken(44)) {
            Node nodeCreateName = this.nf.createName(tokenStream.getString(), codePosition);
            if (tokenStream.matchToken(94)) {
                node = nodeCreateName;
                nodeMemberExpr = null;
            } else {
                if (Context.getContext().hasFeature(2)) {
                    nodeMemberExpr = memberExprTail(tokenStream, false, nodeCreateName);
                } else {
                    node = nodeCreateName;
                    nodeMemberExpr = null;
                }
                mustMatchToken(tokenStream, 94, "msg.no.paren.parms");
            }
        } else if (tokenStream.matchToken(94)) {
            nodeMemberExpr = null;
        } else {
            nodeMemberExpr = Context.getContext().hasFeature(2) ? memberExpr(tokenStream, false) : null;
            mustMatchToken(tokenStream, 94, "msg.no.paren.parms");
        }
        int i = this.functionNumber + 1;
        int i2 = this.sourceTop;
        try {
            this.functionNumber = 0;
            Node nodeCreateLeaf = this.nf.createLeaf(94, tokenStream.tokenPosition);
            if (!tokenStream.matchToken(95)) {
                do {
                    mustMatchToken(tokenStream, 44, "msg.no.parm");
                    nodeCreateLeaf.addChildToBack(this.nf.createName(tokenStream.getString(), tokenStream.tokenPosition));
                } while (tokenStream.matchToken(96));
                mustMatchToken(tokenStream, 95, "msg.no.paren.after.parms");
            }
            mustMatchToken(tokenStream, 92, "msg.no.brace.body");
            Node functionBody = parseFunctionBody(tokenStream);
            functionBody.setPosition(tokenStream.tokenPosition);
            mustMatchToken(tokenStream, 93, "msg.no.brace.after.body");
            this.sourceTop = i2;
            this.functionNumber = i;
            Node nodeCreateFunction = this.nf.createFunction(node, nodeCreateLeaf, functionBody, codePosition);
            if (nodeMemberExpr != null) {
                nodeCreateFunction = this.nf.createBinary(97, 128, nodeMemberExpr, nodeCreateFunction, codePosition);
            }
            if (!z) {
                wellTerminated(tokenStream, 110);
            }
            List<ParserListener> list2 = this.listeners;
            if (list2 != null) {
                Iterator<ParserListener> it2 = list2.iterator();
                while (it2.hasNext()) {
                    it2.next().functionEnded(tokenStream);
                }
            }
            return nodeCreateFunction;
        } catch (Throwable th) {
            this.sourceTop = i2;
            this.functionNumber = i;
            throw th;
        }
    }

    private Node statements(TokenStream tokenStream) throws IOException {
        Node nodeCreateBlock = this.nf.createBlock(tokenStream.tokenPosition);
        while (true) {
            int iPeekToken = tokenStream.peekToken();
            if (iPeekToken <= 0 || iPeekToken == 93) {
                break;
            }
            nodeCreateBlock.addChildToBack(statement(tokenStream));
        }
        return nodeCreateBlock;
    }

    private Node condition(TokenStream tokenStream) throws JavaScriptException, IOException {
        mustMatchToken(tokenStream, 94, "msg.no.paren.cond");
        Node nodeExpr = expr(tokenStream, false);
        mustMatchToken(tokenStream, 95, "msg.no.paren.after.cond");
        return nodeExpr;
    }

    private void wellTerminated(TokenStream tokenStream, int i) throws JavaScriptException, IOException {
        int iPeekTokenSameLine = tokenStream.peekTokenSameLine();
        if (iPeekTokenSameLine == -1 || iPeekTokenSameLine == 0 || iPeekTokenSameLine == 1 || iPeekTokenSameLine == 89 || iPeekTokenSameLine == 93) {
            return;
        }
        int languageVersion = Context.getContext().getLanguageVersion();
        if ((iPeekTokenSameLine == 110 || i == 110) && languageVersion < 120) {
            return;
        }
        reportError(tokenStream, "msg.no.semi.stmt");
    }

    private Node matchLabel(TokenStream tokenStream) throws JavaScriptException, IOException {
        String string;
        CodePosition codePosition = tokenStream.tokenPosition;
        int lineno = tokenStream.getLineno();
        if (tokenStream.peekTokenSameLine() == 44) {
            tokenStream.getToken();
            string = tokenStream.getString();
        } else {
            string = null;
        }
        if (lineno == tokenStream.getLineno()) {
            wellTerminated(tokenStream, -1);
        }
        if (string != null) {
            return this.nf.createString(string, codePosition);
        }
        return null;
    }

    private Node statement(TokenStream tokenStream) throws IOException {
        int token;
        CodePosition codePosition = tokenStream.lastPosition;
        try {
            Comment comments = getComments(tokenStream);
            tokenStream.collectCommentsAfter();
            Node nodeStatementHelper = statementHelper(tokenStream);
            nodeStatementHelper.setCommentsBeforeNode(comments);
            tokenStream.collectCommentsAfter();
            nodeStatementHelper.setCommentsAfterNode(getComments(tokenStream));
            return nodeStatementHelper;
        } catch (JavaScriptException unused) {
            do {
                token = tokenStream.getToken();
                if (token == 89 || token == 1 || token == 0) {
                    break;
                }
            } while (token != -1);
            IRFactory iRFactory = this.nf;
            return iRFactory.createExprStatement(iRFactory.createName("error", codePosition), codePosition);
        }
    }

    /* JADX WARN: Code duplicated, block: B:141:0x033a  */
    private Node statementHelper(TokenStream tokenStream) throws JavaScriptException, IOException {
        Node nodeCreateLeaf;
        int i;
        Node nodeExpr;
        Node nodeExpr2;
        Node nodeExpr3;
        Node nodeExpr4;
        Node nodeExpr5;
        Node nodeExpr6;
        Node node;
        int token = tokenStream.getToken();
        CodePosition codePosition = tokenStream.tokenPosition;
        if (token == -1 || token == 1) {
            nodeCreateLeaf = this.nf.createLeaf(132, tokenStream.tokenPosition);
            i = 89;
        } else {
            if (token == 5) {
                if (!this.insideFunction && (tokenStream.flags & 2) == 0) {
                    reportError(tokenStream, "msg.bad.return");
                }
                tokenStream.flags |= 16;
                int iPeekTokenSameLine = tokenStream.peekTokenSameLine();
                tokenStream.flags &= -17;
                if (iPeekTokenSameLine != 0 && iPeekTokenSameLine != 1 && iPeekTokenSameLine != 89 && iPeekTokenSameLine != 93) {
                    int lineno = tokenStream.getLineno();
                    nodeExpr = expr(tokenStream, false);
                    if (tokenStream.getLineno() == lineno) {
                        wellTerminated(tokenStream, -1);
                    }
                    tokenStream.flags |= 4;
                } else {
                    tokenStream.flags |= 8;
                    nodeExpr = null;
                }
                nodeCreateLeaf = this.nf.createReturn(nodeExpr, codePosition);
            } else if (token != 62) {
                int i2 = 44;
                int i3 = 94;
                if (token == 75) {
                    Node nodeStatement = statement(tokenStream);
                    Node nodeCreateLeaf2 = this.nf.createLeaf(133, null);
                    int iPeekToken = tokenStream.peekToken();
                    int i4 = 125;
                    if (iPeekToken == 125) {
                        boolean z = false;
                        while (tokenStream.matchToken(i4)) {
                            if (z) {
                                reportError(tokenStream, "msg.catch.unreachable");
                            }
                            CodePosition codePosition2 = tokenStream.tokenPosition;
                            mustMatchToken(tokenStream, i3, "msg.no.paren.catch");
                            mustMatchToken(tokenStream, i2, "msg.bad.catchcond");
                            Node nodeCreateName = this.nf.createName(tokenStream.getString(), tokenStream.tokenPosition);
                            if (tokenStream.matchToken(113)) {
                                nodeExpr2 = expr(tokenStream, false);
                            } else {
                                z = true;
                                nodeExpr2 = null;
                            }
                            mustMatchToken(tokenStream, 95, "msg.bad.catchcond");
                            mustMatchToken(tokenStream, 92, "msg.no.brace.catchblock");
                            nodeCreateLeaf2.addChildToBack(this.nf.createCatch(nodeCreateName, nodeExpr2, statements(tokenStream), codePosition2));
                            mustMatchToken(tokenStream, 93, "msg.no.brace.after.body");
                            i4 = 125;
                            i2 = 44;
                            i3 = 94;
                        }
                    } else if (iPeekToken != 126) {
                        mustMatchToken(tokenStream, 126, "msg.try.no.catchfinally");
                    }
                    nodeCreateLeaf = this.nf.createTryCatchFinally(nodeStatement, nodeCreateLeaf2, tokenStream.matchToken(126) ? statement(tokenStream) : null, codePosition);
                } else if (token != 89) {
                    if (token == 92) {
                        nodeCreateLeaf = statements(tokenStream);
                        mustMatchToken(tokenStream, 93, "msg.no.brace.block");
                    } else if (token == 113) {
                        nodeCreateLeaf = this.nf.createIf(condition(tokenStream), statement(tokenStream), tokenStream.matchToken(114) ? statement(tokenStream) : null, codePosition);
                    } else if (token == 115) {
                        nodeCreateLeaf = this.nf.createSwitch(codePosition);
                        mustMatchToken(tokenStream, 94, "msg.no.paren.switch");
                        nodeCreateLeaf.addChildToBack(expr(tokenStream, false));
                        mustMatchToken(tokenStream, 95, "msg.no.paren.after.switch");
                        mustMatchToken(tokenStream, 92, "msg.no.brace.switch");
                        Node nodeCreateUnary = null;
                        while (true) {
                            int token2 = tokenStream.getToken();
                            if (token2 == 93 || token2 == 0) {
                                break;
                            }
                            if (token2 == 116) {
                                nodeCreateUnary = this.nf.createUnary(116, expr(tokenStream, false), tokenStream.tokenPosition);
                            } else if (token2 == 117) {
                                nodeCreateUnary = this.nf.createLeaf(117, tokenStream.tokenPosition);
                            } else {
                                reportError(tokenStream, "msg.bad.switch");
                            }
                            mustMatchToken(tokenStream, 99, "msg.no.colon.case");
                            Node nodeCreateLeaf3 = this.nf.createLeaf(133, null);
                            while (true) {
                                int iPeekToken2 = tokenStream.peekToken();
                                if (iPeekToken2 == 93 || iPeekToken2 == 116 || iPeekToken2 == 117 || iPeekToken2 == 0) {
                                    break;
                                }
                                nodeCreateLeaf3.addChildToBack(statement(tokenStream));
                            }
                            if (nodeCreateUnary != null) {
                                nodeCreateUnary.addChildToBack(nodeCreateLeaf3);
                            }
                            nodeCreateLeaf.addChildToBack(nodeCreateUnary);
                        }
                    } else if (token != 146) {
                        switch (token) {
                            case 118:
                                nodeCreateLeaf = this.nf.createWhile(condition(tokenStream), statement(tokenStream), codePosition);
                                break;
                            case 119:
                                Node nodeStatement2 = statement(tokenStream);
                                mustMatchToken(tokenStream, 118, "msg.no.while.do");
                                nodeCreateLeaf = this.nf.createDoWhile(nodeStatement2, condition(tokenStream), codePosition);
                                break;
                            case 120:
                                mustMatchToken(tokenStream, 94, "msg.no.paren.for");
                                int iPeekToken3 = tokenStream.peekToken();
                                if (iPeekToken3 == 89) {
                                    nodeExpr3 = this.nf.createLeaf(132, null);
                                } else if (iPeekToken3 == 123) {
                                    tokenStream.getToken();
                                    nodeExpr3 = variables(tokenStream, true, tokenStream.tokenPosition);
                                } else {
                                    nodeExpr3 = expr(tokenStream, true);
                                }
                                Node node2 = nodeExpr3;
                                if (tokenStream.peekToken() == 103 && tokenStream.getOp() == 63) {
                                    tokenStream.matchToken(103);
                                    nodeExpr6 = expr(tokenStream, false);
                                    node = null;
                                } else {
                                    mustMatchToken(tokenStream, 89, "msg.no.semi.for");
                                    if (tokenStream.peekToken() == 89) {
                                        nodeExpr4 = this.nf.createLeaf(132, null);
                                    } else {
                                        nodeExpr4 = expr(tokenStream, false);
                                    }
                                    mustMatchToken(tokenStream, 89, "msg.no.semi.for.cond");
                                    if (tokenStream.peekToken() == 95) {
                                        nodeExpr5 = this.nf.createLeaf(132, null);
                                    } else {
                                        nodeExpr5 = expr(tokenStream, false);
                                    }
                                    nodeExpr6 = nodeExpr4;
                                    node = nodeExpr5;
                                }
                                mustMatchToken(tokenStream, 95, "msg.no.paren.for.ctrl");
                                Node nodeStatement3 = statement(tokenStream);
                                nodeCreateLeaf = node == null ? this.nf.createForIn(node2, nodeExpr6, nodeStatement3, codePosition) : this.nf.createFor(node2, nodeExpr6, node, nodeStatement3, codePosition);
                                break;
                            case 121:
                                nodeCreateLeaf = this.nf.createBreak(matchLabel(tokenStream), codePosition);
                                break;
                            case 122:
                                nodeCreateLeaf = this.nf.createContinue(matchLabel(tokenStream), codePosition);
                                break;
                            case 123:
                                int lineno2 = tokenStream.getLineno();
                                nodeCreateLeaf = variables(tokenStream, false, codePosition);
                                if (tokenStream.getLineno() == lineno2) {
                                    wellTerminated(tokenStream, -1);
                                }
                                break;
                            case 124:
                                reportError(tokenStream, "msg.jsni.unsupported.with");
                                mustMatchToken(tokenStream, 94, "msg.no.paren.with");
                                Node nodeExpr7 = expr(tokenStream, false);
                                mustMatchToken(tokenStream, 95, "msg.no.paren.after.with");
                                nodeCreateLeaf = this.nf.createWith(nodeExpr7, statement(tokenStream), codePosition);
                                break;
                            default:
                                int tokenno = tokenStream.getTokenno();
                                tokenStream.ungetToken(token);
                                int lineno3 = tokenStream.getLineno();
                                Node nodeExpr8 = expr(tokenStream, false);
                                if (tokenStream.peekToken() == 99) {
                                    if (token != 44 || tokenStream.getTokenno() != tokenno) {
                                        reportError(tokenStream, "msg.bad.label");
                                    }
                                    tokenStream.getToken();
                                    String string = tokenStream.getString();
                                    IRFactory iRFactory = this.nf;
                                    Node nodeCreateLabel = iRFactory.createLabel(iRFactory.createString(string, codePosition), codePosition);
                                    nodeCreateLabel.addChildToBack(statement(tokenStream));
                                    return nodeCreateLabel;
                                }
                                if (token == 110 && this.nf.getLeafType(nodeExpr8) != 110) {
                                    reportError(tokenStream, "msg.syntax");
                                }
                                nodeCreateLeaf = this.nf.createExprStatement(nodeExpr8, codePosition);
                                if (tokenStream.getLineno() == lineno3 || (token == 110 && tokenStream.getLineno() == this.lastExprEndLine)) {
                                    wellTerminated(tokenStream, token);
                                }
                                break;
                                break;
                        }
                    } else {
                        nodeCreateLeaf = this.nf.createDebugger(codePosition);
                    }
                    i = 89;
                } else {
                    nodeCreateLeaf = this.nf.createLeaf(132, tokenStream.tokenPosition);
                }
            } else {
                int lineno4 = tokenStream.getLineno();
                nodeCreateLeaf = this.nf.createThrow(expr(tokenStream, false), codePosition);
                if (lineno4 == tokenStream.getLineno()) {
                    wellTerminated(tokenStream, -1);
                }
            }
            i = 89;
        }
        tokenStream.matchToken(i);
        return nodeCreateLeaf;
    }

    private Node variables(TokenStream tokenStream, boolean z, CodePosition codePosition) throws JavaScriptException, IOException {
        Node nodeCreateVariables = this.nf.createVariables(codePosition);
        do {
            mustMatchToken(tokenStream, 44, "msg.bad.var");
            Node nodeCreateName = this.nf.createName(tokenStream.getString(), tokenStream.tokenPosition);
            if (tokenStream.matchToken(97)) {
                if (tokenStream.getOp() != 128) {
                    reportError(tokenStream, "msg.bad.var.init");
                }
                nodeCreateName.addChildToBack(assignExpr(tokenStream, z));
            }
            nodeCreateVariables.addChildToBack(nodeCreateName);
        } while (tokenStream.matchToken(96));
        return nodeCreateVariables;
    }

    public Node expr(TokenStream tokenStream, boolean z) throws JavaScriptException, IOException {
        Node nodeAssignExpr = assignExpr(tokenStream, z);
        while (tokenStream.matchToken(96)) {
            nodeAssignExpr = this.nf.createBinary(96, nodeAssignExpr, assignExpr(tokenStream, z), tokenStream.tokenPosition);
        }
        return nodeAssignExpr;
    }

    private Node assignExpr(TokenStream tokenStream, boolean z) throws JavaScriptException, IOException {
        Comment comments = getComments(tokenStream);
        Node nodeCondExpr = condExpr(tokenStream, z);
        nodeCondExpr.setCommentsBeforeNode(comments);
        if (tokenStream.matchToken(97)) {
            nodeCondExpr = this.nf.createBinary(97, tokenStream.getOp(), nodeCondExpr, assignExpr(tokenStream, z), tokenStream.tokenPosition);
        }
        tokenStream.collectCommentsAfter();
        nodeCondExpr.setCommentsAfterNode(getComments(tokenStream));
        return nodeCondExpr;
    }

    private Node condExpr(TokenStream tokenStream, boolean z) throws JavaScriptException, IOException {
        Node nodeOrExpr = orExpr(tokenStream, z);
        if (!tokenStream.matchToken(98)) {
            return nodeOrExpr;
        }
        CodePosition codePosition = tokenStream.tokenPosition;
        Node nodeAssignExpr = assignExpr(tokenStream, false);
        mustMatchToken(tokenStream, 99, "msg.no.colon.cond");
        return this.nf.createTernary(nodeOrExpr, nodeAssignExpr, assignExpr(tokenStream, z), codePosition);
    }

    private Node orExpr(TokenStream tokenStream, boolean z) throws JavaScriptException, IOException {
        Node nodeAndExpr = andExpr(tokenStream, z);
        while (tokenStream.matchToken(100)) {
            nodeAndExpr = this.nf.createBinary(100, nodeAndExpr, andExpr(tokenStream, z), tokenStream.tokenPosition);
        }
        return nodeAndExpr;
    }

    private Node andExpr(TokenStream tokenStream, boolean z) throws JavaScriptException, IOException {
        Node nodeBitOrExpr = bitOrExpr(tokenStream, z);
        while (tokenStream.matchToken(101)) {
            nodeBitOrExpr = this.nf.createBinary(101, nodeBitOrExpr, bitOrExpr(tokenStream, z), tokenStream.tokenPosition);
        }
        return nodeBitOrExpr;
    }

    private Node bitOrExpr(TokenStream tokenStream, boolean z) throws JavaScriptException, IOException {
        Node nodeBitXorExpr = bitXorExpr(tokenStream, z);
        while (tokenStream.matchToken(11)) {
            nodeBitXorExpr = this.nf.createBinary(11, nodeBitXorExpr, bitXorExpr(tokenStream, z), tokenStream.tokenPosition);
        }
        return nodeBitXorExpr;
    }

    private Node bitXorExpr(TokenStream tokenStream, boolean z) throws JavaScriptException, IOException {
        Node nodeBitAndExpr = bitAndExpr(tokenStream, z);
        while (tokenStream.matchToken(12)) {
            nodeBitAndExpr = this.nf.createBinary(12, nodeBitAndExpr, bitAndExpr(tokenStream, z), tokenStream.tokenPosition);
        }
        return nodeBitAndExpr;
    }

    private Node bitAndExpr(TokenStream tokenStream, boolean z) throws JavaScriptException, IOException {
        Node nodeEqExpr = eqExpr(tokenStream, z);
        while (tokenStream.matchToken(13)) {
            nodeEqExpr = this.nf.createBinary(13, nodeEqExpr, eqExpr(tokenStream, z), tokenStream.tokenPosition);
        }
        return nodeEqExpr;
    }

    private Node eqExpr(TokenStream tokenStream, boolean z) throws JavaScriptException, IOException {
        Node nodeRelExpr = relExpr(tokenStream, z);
        while (tokenStream.matchToken(102)) {
            nodeRelExpr = this.nf.createBinary(102, tokenStream.getOp(), nodeRelExpr, relExpr(tokenStream, z), tokenStream.tokenPosition);
        }
        return nodeRelExpr;
    }

    private Node relExpr(TokenStream tokenStream, boolean z) throws JavaScriptException, IOException {
        Node nodeShiftExpr = shiftExpr(tokenStream);
        CodePosition codePosition = tokenStream.tokenPosition;
        while (tokenStream.matchToken(103)) {
            int op = tokenStream.getOp();
            if (z && op == 63) {
                tokenStream.ungetToken(103);
                break;
            }
            nodeShiftExpr = this.nf.createBinary(103, op, nodeShiftExpr, shiftExpr(tokenStream), codePosition);
            codePosition = tokenStream.tokenPosition;
        }
        return nodeShiftExpr;
    }

    private Node shiftExpr(TokenStream tokenStream) throws JavaScriptException, IOException {
        Node nodeAddExpr = addExpr(tokenStream);
        while (tokenStream.matchToken(104)) {
            nodeAddExpr = this.nf.createBinary(104, tokenStream.getOp(), nodeAddExpr, addExpr(tokenStream), tokenStream.tokenPosition);
        }
        return nodeAddExpr;
    }

    private Node addExpr(TokenStream tokenStream) throws JavaScriptException, IOException {
        Node nodeMulExpr = mulExpr(tokenStream);
        while (true) {
            int token = tokenStream.getToken();
            if (token == 23 || token == 24) {
                nodeMulExpr = this.nf.createBinary(token, nodeMulExpr, mulExpr(tokenStream), tokenStream.tokenPosition);
            } else {
                tokenStream.ungetToken(token);
                return nodeMulExpr;
            }
        }
    }

    private Node mulExpr(TokenStream tokenStream) throws JavaScriptException, IOException {
        Node nodeUnaryExpr = unaryExpr(tokenStream);
        while (true) {
            int iPeekToken = tokenStream.peekToken();
            if (iPeekToken != 25 && iPeekToken != 26 && iPeekToken != 27) {
                return nodeUnaryExpr;
            }
            nodeUnaryExpr = this.nf.createBinary(tokenStream.getToken(), nodeUnaryExpr, unaryExpr(tokenStream), tokenStream.tokenPosition);
        }
    }

    private Node unaryExpr(TokenStream tokenStream) throws JavaScriptException, IOException {
        tokenStream.flags |= 16;
        int token = tokenStream.getToken();
        tokenStream.flags &= -17;
        CodePosition codePosition = tokenStream.tokenPosition;
        if (token == -1) {
            return this.nf.createName(NotificationCompat.CATEGORY_ERROR, codePosition);
        }
        if (token == 31) {
            Node nodeUnaryExpr = unaryExpr(tokenStream);
            if (!isValidDeleteArgument(nodeUnaryExpr)) {
                Context.reportError("msg.wrong.delete argument", nodeUnaryExpr.getPosition(), tokenStream.lastPosition);
            }
            return this.nf.createUnary(31, nodeUnaryExpr, codePosition);
        }
        if (token != 23 && token != 24) {
            switch (token) {
                case 105:
                    return this.nf.createUnary(105, tokenStream.getOp(), unaryExpr(tokenStream), codePosition);
                case 106:
                case 107:
                    return this.nf.createUnary(token, 130, memberExpr(tokenStream, true), codePosition);
                default:
                    tokenStream.ungetToken(token);
                    int lineno = tokenStream.getLineno();
                    Node nodeMemberExpr = memberExpr(tokenStream, true);
                    int iPeekToken = tokenStream.peekToken();
                    if ((iPeekToken != 106 && iPeekToken != 107) || tokenStream.getLineno() != lineno) {
                        return nodeMemberExpr;
                    }
                    return this.nf.createUnary(tokenStream.getToken(), 131, nodeMemberExpr, tokenStream.tokenPosition);
            }
        }
        return this.nf.createUnary(105, token, unaryExpr(tokenStream), codePosition);
    }

    private static boolean isValidDeleteArgument(Node node) {
        if (node == null) {
            $$$reportNull$$$0(0);
        }
        return node.type == 39 || node.type == 41;
    }

    private Node argumentList(TokenStream tokenStream, Node node) throws JavaScriptException, IOException {
        tokenStream.flags |= 16;
        boolean zMatchToken = tokenStream.matchToken(95);
        tokenStream.flags &= -17;
        if (!zMatchToken) {
            do {
                node.addChildToBack(assignExpr(tokenStream, false));
            } while (tokenStream.matchToken(96));
            mustMatchToken(tokenStream, 95, "msg.no.paren.arg");
        }
        return node;
    }

    private Node memberExpr(TokenStream tokenStream, boolean z) throws JavaScriptException, IOException {
        Node nodePrimaryExpr;
        CodePosition codePosition = tokenStream.tokenPosition;
        tokenStream.flags |= 16;
        int iPeekToken = tokenStream.peekToken();
        tokenStream.flags &= -17;
        if (iPeekToken == 30) {
            tokenStream.getToken();
            nodePrimaryExpr = this.nf.createLeaf(30, codePosition);
            nodePrimaryExpr.addChildToBack(memberExpr(tokenStream, false));
            if (tokenStream.matchToken(94)) {
                nodePrimaryExpr = argumentList(tokenStream, nodePrimaryExpr);
            }
            if (tokenStream.peekToken() == 92) {
                nodePrimaryExpr.addChildToBack(primaryExpr(tokenStream));
            }
        } else {
            nodePrimaryExpr = primaryExpr(tokenStream);
        }
        return memberExprTail(tokenStream, z, nodePrimaryExpr);
    }

    private Node memberExprTail(TokenStream tokenStream, boolean z, Node node) throws JavaScriptException, IOException {
        this.lastExprEndLine = tokenStream.getLineno();
        while (true) {
            int token = tokenStream.getToken();
            if (token <= 0) {
                break;
            }
            CodePosition codePosition = tokenStream.tokenPosition;
            if (token == 108) {
                tokenStream.treatKeywordAsIdentifier = true;
                mustMatchToken(tokenStream, 44, "msg.no.name.after.dot");
                tokenStream.treatKeywordAsIdentifier = false;
                IRFactory iRFactory = this.nf;
                node = iRFactory.createBinary(108, node, iRFactory.createName(tokenStream.getString(), tokenStream.tokenPosition), codePosition);
                this.lastExprEndLine = tokenStream.getLineno();
            } else if (token == 90) {
                node = this.nf.createBinary(90, node, expr(tokenStream, false), codePosition);
                mustMatchToken(tokenStream, 91, "msg.no.bracket.index");
                this.lastExprEndLine = tokenStream.getLineno();
            } else if (z && token == 94) {
                node = argumentList(tokenStream, this.nf.createUnary(43, node, codePosition));
                this.lastExprEndLine = tokenStream.getLineno();
            } else {
                tokenStream.ungetToken(token);
                break;
            }
        }
        return node;
    }

    public Node primaryExpr(TokenStream tokenStream) throws JavaScriptException, IOException {
        Comment comments = getComments(tokenStream);
        Node nodePrimaryExprHelper = primaryExprHelper(tokenStream);
        nodePrimaryExprHelper.setCommentsBeforeNode(comments);
        tokenStream.collectCommentsAfter();
        nodePrimaryExprHelper.setCommentsAfterNode(getComments(tokenStream));
        return nodePrimaryExprHelper;
    }

    private Node primaryExprHelper(TokenStream tokenStream) throws JavaScriptException, IOException {
        Node nodeCreateIntNumber;
        tokenStream.flags |= 16;
        int token = tokenStream.getToken();
        CodePosition codePosition = tokenStream.tokenPosition;
        tokenStream.flags &= -17;
        if (token != -1) {
            if (token == 56) {
                String str = tokenStream.regExpFlags;
                tokenStream.regExpFlags = null;
                return this.nf.createRegExp(tokenStream.getString(), str, codePosition);
            }
            if (token == 90) {
                Node nodeCreateLeaf = this.nf.createLeaf(134, codePosition);
                tokenStream.flags |= 16;
                boolean zMatchToken = tokenStream.matchToken(91);
                tokenStream.flags &= -17;
                if (!zMatchToken) {
                    do {
                        tokenStream.flags |= 16;
                        int iPeekToken = tokenStream.peekToken();
                        tokenStream.flags &= -17;
                        if (iPeekToken == 91) {
                            break;
                        }
                        if (iPeekToken == 96) {
                            nodeCreateLeaf.addChildToBack(this.nf.createLeaf(109, 74, codePosition));
                        } else {
                            nodeCreateLeaf.addChildToBack(assignExpr(tokenStream, false));
                        }
                    } while (tokenStream.matchToken(96));
                    mustMatchToken(tokenStream, 91, "msg.no.bracket.arg");
                }
                return this.nf.createArrayLiteral(nodeCreateLeaf);
            }
            if (token == 92) {
                Node nodeCreateLeaf2 = this.nf.createLeaf(135, codePosition);
                if (!tokenStream.matchToken(93)) {
                    do {
                        int token2 = tokenStream.getToken();
                        if (token2 == 93) {
                            tokenStream.ungetToken(token2);
                        } else {
                            if (token2 != 148) {
                                switch (token2) {
                                    case 44:
                                    case 46:
                                        nodeCreateIntNumber = this.nf.createString(tokenStream.getString(), tokenStream.tokenPosition);
                                        break;
                                    case 45:
                                        nodeCreateIntNumber = this.nf.createNumber(tokenStream.getNumber(), tokenStream.tokenPosition);
                                        break;
                                    default:
                                        reportError(tokenStream, "msg.bad.prop");
                                        break;
                                }
                            } else {
                                nodeCreateIntNumber = this.nf.createIntNumber(tokenStream.getNumber(), tokenStream.tokenPosition);
                            }
                            mustMatchToken(tokenStream, 99, "msg.no.colon.prop");
                            nodeCreateLeaf2.addChildToBack(nodeCreateIntNumber);
                            nodeCreateLeaf2.addChildToBack(assignExpr(tokenStream, false));
                        }
                        mustMatchToken(tokenStream, 93, "msg.no.brace.prop");
                    } while (tokenStream.matchToken(96));
                    mustMatchToken(tokenStream, 93, "msg.no.brace.prop");
                }
                return this.nf.createObjectLiteral(nodeCreateLeaf2);
            }
            if (token == 94) {
                Node nodeExpr = expr(tokenStream, false);
                mustMatchToken(tokenStream, 95, "msg.no.paren");
                return nodeExpr;
            }
            if (token == 112) {
                if (tokenStream.peekToken() != 94 && tokenStream.peekToken() != 108) {
                    reportError(tokenStream, "msg.syntax");
                }
                return this.nf.createName(TokenStream.tokenToName(112), codePosition);
            }
            if (token == 148) {
                return this.nf.createIntNumber(tokenStream.getNumber(), codePosition);
            }
            if (token == 109) {
                return this.nf.createLeaf(109, tokenStream.getOp(), codePosition);
            }
            if (token == 110) {
                return function(tokenStream, true);
            }
            switch (token) {
                case 44:
                    return this.nf.createName(tokenStream.getString(), codePosition);
                case 45:
                    return this.nf.createNumber(tokenStream.getNumber(), codePosition);
                case 46:
                    return this.nf.createString(tokenStream.getString(), codePosition);
                default:
                    reportError(tokenStream, "msg.syntax");
                    break;
            }
        }
        return null;
    }

    private Comment getComments(TokenStream tokenStream) {
        Comment headComment = tokenStream.getHeadComment();
        if (headComment != null) {
            tokenStream.releaseComments();
        }
        return headComment;
    }
}
