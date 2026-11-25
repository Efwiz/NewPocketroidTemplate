package antlr;

import antlr.collections.AST;
import antlr.collections.ASTEnumeration;
import antlr.collections.impl.ASTEnumerator;
import antlr.collections.impl.Vector;
import com.huawei.hms.framework.common.ContainerUtils;
import java.io.IOException;
import java.io.Serializable;
import java.io.Writer;
import name.antonsmirnov.firmata.FormatHelper;

/* JADX INFO: loaded from: classes.dex */
public abstract class BaseAST implements AST, Serializable {
    private static String[] tokenNames = null;
    private static boolean verboseStringConversion = false;
    protected BaseAST down;
    protected BaseAST right;

    @Override // antlr.collections.AST
    public int getColumn() {
        return 0;
    }

    @Override // antlr.collections.AST
    public int getLine() {
        return 0;
    }

    @Override // antlr.collections.AST
    public int getType() {
        return 0;
    }

    @Override // antlr.collections.AST
    public abstract void initialize(int i, String str);

    @Override // antlr.collections.AST
    public abstract void initialize(Token token);

    @Override // antlr.collections.AST
    public abstract void initialize(AST ast);

    @Override // antlr.collections.AST
    public void setText(String str) {
    }

    @Override // antlr.collections.AST
    public void setType(int i) {
    }

    @Override // antlr.collections.AST
    public void addChild(AST ast) {
        if (ast == null) {
            return;
        }
        BaseAST baseAST = this.down;
        if (baseAST == null) {
            this.down = (BaseAST) ast;
            return;
        }
        while (true) {
            BaseAST baseAST2 = baseAST.right;
            if (baseAST2 == null) {
                baseAST.right = (BaseAST) ast;
                return;
            }
            baseAST = baseAST2;
        }
    }

    @Override // antlr.collections.AST
    public int getNumberOfChildren() {
        BaseAST baseAST = this.down;
        if (baseAST == null) {
            return 0;
        }
        int i = 1;
        while (true) {
            baseAST = baseAST.right;
            if (baseAST == null) {
                return i;
            }
            i++;
        }
    }

    private static void doWorkForFindAll(AST ast, Vector vector, AST ast2, boolean z) {
        while (ast != null) {
            if ((z && ast.equalsTreePartial(ast2)) || (!z && ast.equalsTree(ast2))) {
                vector.appendElement(ast);
            }
            if (ast.getFirstChild() != null) {
                doWorkForFindAll(ast.getFirstChild(), vector, ast2, z);
            }
            ast = ast.getNextSibling();
        }
    }

    @Override // antlr.collections.AST
    public boolean equals(AST ast) {
        if (ast == null) {
            return false;
        }
        if ((getText() == null && ast.getText() != null) || (getText() != null && ast.getText() == null)) {
            return false;
        }
        if (getText() == null && ast.getText() == null) {
            return getType() == ast.getType();
        }
        return getText().equals(ast.getText()) && getType() == ast.getType();
    }

    @Override // antlr.collections.AST
    public boolean equalsList(AST ast) {
        if (ast == null) {
            return false;
        }
        AST nextSibling = this;
        while (nextSibling != null && ast != null) {
            if (!nextSibling.equals(ast)) {
                return false;
            }
            if (nextSibling.getFirstChild() != null) {
                if (!nextSibling.getFirstChild().equalsList(ast.getFirstChild())) {
                    return false;
                }
            } else if (ast.getFirstChild() != null) {
                return false;
            }
            nextSibling = nextSibling.getNextSibling();
            ast = ast.getNextSibling();
        }
        return nextSibling == null && ast == null;
    }

    @Override // antlr.collections.AST
    public boolean equalsListPartial(AST ast) {
        if (ast == null) {
            return true;
        }
        AST nextSibling = this;
        while (nextSibling != null && ast != null) {
            if (!nextSibling.equals(ast)) {
                return false;
            }
            if (nextSibling.getFirstChild() != null && !nextSibling.getFirstChild().equalsListPartial(ast.getFirstChild())) {
                return false;
            }
            nextSibling = nextSibling.getNextSibling();
            ast = ast.getNextSibling();
        }
        return nextSibling != null || ast == null;
    }

    @Override // antlr.collections.AST
    public boolean equalsTree(AST ast) {
        if (!equals(ast)) {
            return false;
        }
        if (getFirstChild() != null) {
            return getFirstChild().equalsList(ast.getFirstChild());
        }
        return ast.getFirstChild() == null;
    }

    @Override // antlr.collections.AST
    public boolean equalsTreePartial(AST ast) {
        if (ast == null) {
            return true;
        }
        if (equals(ast)) {
            return getFirstChild() == null || getFirstChild().equalsListPartial(ast.getFirstChild());
        }
        return false;
    }

    @Override // antlr.collections.AST
    public ASTEnumeration findAll(AST ast) {
        Vector vector = new Vector(10);
        if (ast == null) {
            return null;
        }
        doWorkForFindAll(this, vector, ast, false);
        return new ASTEnumerator(vector);
    }

    @Override // antlr.collections.AST
    public ASTEnumeration findAllPartial(AST ast) {
        Vector vector = new Vector(10);
        if (ast == null) {
            return null;
        }
        doWorkForFindAll(this, vector, ast, true);
        return new ASTEnumerator(vector);
    }

    @Override // antlr.collections.AST
    public AST getFirstChild() {
        return this.down;
    }

    @Override // antlr.collections.AST
    public AST getNextSibling() {
        return this.right;
    }

    @Override // antlr.collections.AST
    public String getText() {
        return "";
    }

    public void removeChildren() {
        this.down = null;
    }

    @Override // antlr.collections.AST
    public void setFirstChild(AST ast) {
        this.down = (BaseAST) ast;
    }

    @Override // antlr.collections.AST
    public void setNextSibling(AST ast) {
        this.right = (BaseAST) ast;
    }

    public static void setVerboseStringConversion(boolean z, String[] strArr) {
        verboseStringConversion = z;
        tokenNames = strArr;
    }

    public static String[] getTokenNames() {
        return tokenNames;
    }

    @Override // antlr.collections.AST
    public String toString() {
        StringBuffer stringBuffer = new StringBuffer("[");
        if (verboseStringConversion && getText() != null && !getText().equalsIgnoreCase(tokenNames[getType()]) && !getText().equalsIgnoreCase(StringUtils.stripFrontBack(tokenNames[getType()], "\"", "\""))) {
            stringBuffer.append(getText());
            stringBuffer.append(",<");
            stringBuffer.append(tokenNames[getType()]);
            stringBuffer.append(">]");
            return stringBuffer.toString();
        }
        return getText();
    }

    @Override // antlr.collections.AST
    public String toStringList() {
        String string = new StringBuffer().append(getFirstChild() != null ? " (" : "").append(" ").append(toString()).toString();
        if (getFirstChild() != null) {
            string = new StringBuffer().append(string).append(((BaseAST) getFirstChild()).toStringList()).toString();
        }
        if (getFirstChild() != null) {
            string = new StringBuffer().append(string).append(" )").toString();
        }
        return getNextSibling() != null ? new StringBuffer().append(string).append(((BaseAST) getNextSibling()).toStringList()).toString() : string;
    }

    @Override // antlr.collections.AST
    public String toStringTree() {
        String string = new StringBuffer().append(getFirstChild() != null ? " (" : "").append(" ").append(toString()).toString();
        if (getFirstChild() != null) {
            string = new StringBuffer().append(string).append(((BaseAST) getFirstChild()).toStringList()).toString();
        }
        return getFirstChild() != null ? new StringBuffer().append(string).append(" )").toString() : string;
    }

    public static String decode(String str) {
        StringBuffer stringBuffer = new StringBuffer();
        int i = 0;
        while (i < str.length()) {
            char cCharAt = str.charAt(i);
            if (cCharAt == '&') {
                char cCharAt2 = str.charAt(i + 1);
                char cCharAt3 = str.charAt(i + 2);
                char cCharAt4 = str.charAt(i + 3);
                int i2 = i + 4;
                char cCharAt5 = str.charAt(i2);
                int i3 = i + 5;
                char cCharAt6 = str.charAt(i3);
                if (cCharAt2 == 'a' && cCharAt3 == 'm' && cCharAt4 == 'p' && cCharAt5 == ';') {
                    stringBuffer.append(ContainerUtils.FIELD_DELIMITER);
                    i = i3;
                } else {
                    if (cCharAt2 == 'l' && cCharAt3 == 't' && cCharAt4 == ';') {
                        stringBuffer.append("<");
                    } else if (cCharAt2 == 'g' && cCharAt3 == 't' && cCharAt4 == ';') {
                        stringBuffer.append(">");
                    } else {
                        if (cCharAt2 == 'q' && cCharAt3 == 'u' && cCharAt4 == 'o' && cCharAt5 == 't' && cCharAt6 == ';') {
                            stringBuffer.append("\"");
                        } else if (cCharAt2 == 'a' && cCharAt3 == 'p' && cCharAt4 == 'o' && cCharAt5 == 's' && cCharAt6 == ';') {
                            stringBuffer.append(FormatHelper.QUOTE);
                        } else {
                            stringBuffer.append(ContainerUtils.FIELD_DELIMITER);
                        }
                        i += 6;
                    }
                    i = i2;
                }
            } else {
                stringBuffer.append(cCharAt);
            }
            i++;
        }
        return new String(stringBuffer);
    }

    public static String encode(String str) {
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < str.length(); i++) {
            char cCharAt = str.charAt(i);
            if (cCharAt == '\"') {
                stringBuffer.append("&quot;");
            } else if (cCharAt == '<') {
                stringBuffer.append("&lt;");
            } else if (cCharAt == '>') {
                stringBuffer.append("&gt;");
            } else if (cCharAt == '&') {
                stringBuffer.append("&amp;");
            } else if (cCharAt == '\'') {
                stringBuffer.append("&apos;");
            } else {
                stringBuffer.append(cCharAt);
            }
        }
        return new String(stringBuffer);
    }

    public void xmlSerializeNode(Writer writer) throws IOException {
        StringBuffer stringBuffer = new StringBuffer(100);
        stringBuffer.append("<");
        stringBuffer.append(new StringBuffer().append(getClass().getName()).append(" ").toString());
        stringBuffer.append(new StringBuffer("text=\"").append(encode(getText())).append("\" type=\"").append(getType()).append("\"/>").toString());
        writer.write(stringBuffer.toString());
    }

    public void xmlSerializeRootOpen(Writer writer) throws IOException {
        StringBuffer stringBuffer = new StringBuffer(100);
        stringBuffer.append("<");
        stringBuffer.append(new StringBuffer().append(getClass().getName()).append(" ").toString());
        stringBuffer.append(new StringBuffer("text=\"").append(encode(getText())).append("\" type=\"").append(getType()).append("\">\n").toString());
        writer.write(stringBuffer.toString());
    }

    public void xmlSerializeRootClose(Writer writer) throws IOException {
        writer.write(new StringBuffer("</").append(getClass().getName()).append(">\n").toString());
    }

    public void xmlSerialize(Writer writer) throws IOException {
        for (AST nextSibling = this; nextSibling != null; nextSibling = nextSibling.getNextSibling()) {
            if (nextSibling.getFirstChild() == null) {
                ((BaseAST) nextSibling).xmlSerializeNode(writer);
            } else {
                BaseAST baseAST = (BaseAST) nextSibling;
                baseAST.xmlSerializeRootOpen(writer);
                ((BaseAST) nextSibling.getFirstChild()).xmlSerialize(writer);
                baseAST.xmlSerializeRootClose(writer);
            }
        }
    }
}
