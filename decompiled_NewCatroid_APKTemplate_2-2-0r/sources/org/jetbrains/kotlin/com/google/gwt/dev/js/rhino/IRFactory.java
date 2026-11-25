package org.jetbrains.kotlin.com.google.gwt.dev.js.rhino;

/* JADX INFO: loaded from: classes3.dex */
public class IRFactory {
    private TokenStream ts;

    public Node createArrayLiteral(Node node) {
        return node;
    }

    public Node createObjectLiteral(Node node) {
        return node;
    }

    public IRFactory(TokenStream tokenStream) {
        this.ts = tokenStream;
    }

    public Node createScript(Node node) {
        Node node2 = new Node(147);
        Node firstChild = node.getFirstChild();
        if (firstChild != null) {
            node2.addChildrenToBack(firstChild);
        }
        return node2;
    }

    public Node createLeaf(int i, CodePosition codePosition) {
        return new Node(i, codePosition);
    }

    public Node createLeaf(int i, int i2, CodePosition codePosition) {
        return new Node(i, i2, codePosition);
    }

    public int getLeafType(Node node) {
        return node.getType();
    }

    public Node createSwitch(CodePosition codePosition) {
        return new Node(115, codePosition);
    }

    public Node createVariables(CodePosition codePosition) {
        return new Node(123, codePosition);
    }

    public Node createExprStatement(Object obj, CodePosition codePosition) {
        return new Node(140, (Node) obj, codePosition);
    }

    public Node createName(String str, CodePosition codePosition) {
        return Node.newString(44, str, codePosition);
    }

    public Node createString(String str, CodePosition codePosition) {
        return Node.newString(str, codePosition);
    }

    public Node createIntNumber(double d, CodePosition codePosition) {
        return Node.newIntNumber(d, codePosition);
    }

    public Node createNumber(double d, CodePosition codePosition) {
        return Node.newNumber(d, codePosition);
    }

    public Node createCatch(Node node, Node node2, Node node3, CodePosition codePosition) {
        if (node2 == null) {
            node2 = new Node(109, 52, codePosition);
        }
        return new Node(125, node, node2, node3, codePosition);
    }

    public Node createThrow(Node node, CodePosition codePosition) {
        return new Node(62, node, codePosition);
    }

    public Node createReturn(Node node, CodePosition codePosition) {
        return node == null ? new Node(5, codePosition) : new Node(5, node, codePosition);
    }

    public Node createLabel(Node node, CodePosition codePosition) {
        Node node2 = new Node(136, codePosition);
        node2.addChildToBack(node);
        return node2;
    }

    public Node createBreak(Node node, CodePosition codePosition) {
        Node node2 = new Node(121, codePosition);
        if (node == null) {
            return node2;
        }
        node2.addChildToBack(node);
        return node2;
    }

    public Node createContinue(Node node, CodePosition codePosition) {
        Node node2 = new Node(122, codePosition);
        if (node == null) {
            return node2;
        }
        node2.addChildToBack(node);
        return node2;
    }

    public Node createDebugger(CodePosition codePosition) {
        return new Node(146, codePosition);
    }

    public Node createBlock(CodePosition codePosition) {
        return new Node(133, codePosition);
    }

    public Node createFunction(Node node, Node node2, Node node3, CodePosition codePosition) {
        if (node == null) {
            node = createName("", codePosition);
        }
        return new Node(110, node, node2, node3, codePosition);
    }

    public Node createWhile(Node node, Node node2, CodePosition codePosition) {
        return new Node(118, node, node2, codePosition);
    }

    public Node createDoWhile(Node node, Node node2, CodePosition codePosition) {
        return new Node(119, node, node2, codePosition);
    }

    public Node createFor(Node node, Node node2, Node node3, Node node4, CodePosition codePosition) {
        return new Node(120, node, node2, node3, node4, codePosition);
    }

    public Node createForIn(Node node, Node node2, Node node3, CodePosition codePosition) {
        return new Node(120, node, node2, node3, codePosition);
    }

    public Node createTryCatchFinally(Node node, Node node2, Node node3, CodePosition codePosition) {
        if (node3 == null) {
            return new Node(75, node, node2, codePosition);
        }
        return new Node(75, node, node2, node3, codePosition);
    }

    public Node createWith(Node node, Node node2, CodePosition codePosition) {
        return new Node(124, node, node2, codePosition);
    }

    public Node createRegExp(String str, String str2, CodePosition codePosition) {
        if (str2.length() == 0) {
            return new Node(56, Node.newString(str, codePosition), codePosition);
        }
        return new Node(56, Node.newString(str, codePosition), Node.newString(str2, codePosition), codePosition);
    }

    public Node createIf(Node node, Node node2, Node node3, CodePosition codePosition) {
        if (node3 == null) {
            return new Node(113, node, node2, codePosition);
        }
        return new Node(113, node, node2, node3, codePosition);
    }

    public Node createTernary(Node node, Node node2, Node node3, CodePosition codePosition) {
        return new Node(98, node, node2, node3, codePosition);
    }

    public Node createUnary(int i, Node node, CodePosition codePosition) {
        return new Node(i, node, codePosition);
    }

    public Node createUnary(int i, int i2, Node node, CodePosition codePosition) {
        return new Node(i, node, i2, codePosition);
    }

    public Node createBinary(int i, Node node, Node node2, CodePosition codePosition) {
        if (i == 90) {
            i = 41;
        } else if (i == 108) {
            node2.setType(46);
            i = 39;
        }
        return new Node(i, node, node2, codePosition);
    }

    public Node createBinary(int i, int i2, Node node, Node node2, CodePosition codePosition) {
        if (i == 97) {
            return createAssignment(i2, node, node2, codePosition);
        }
        return new Node(i, node, node2, i2, codePosition);
    }

    public Node createAssignment(int i, Node node, Node node2, CodePosition codePosition) {
        int type = node.getType();
        if (type != 39 && type != 41 && type != 44) {
            this.ts.reportSyntaxError("msg.bad.lhs.assign", null);
        }
        return new Node(97, node, node2, i, codePosition);
    }
}
