package org.antlr.runtime.tree;

import org.antlr.runtime.CommonToken;

/* JADX INFO: loaded from: classes12.dex */
public class TreePatternParser {
    protected TreeAdaptor adaptor;
    protected TreePatternLexer tokenizer;
    protected int ttype;
    protected TreeWizard wizard;

    public TreePatternParser(TreePatternLexer treePatternLexer, TreeWizard treeWizard, TreeAdaptor treeAdaptor) {
        this.tokenizer = treePatternLexer;
        this.wizard = treeWizard;
        this.adaptor = treeAdaptor;
        this.ttype = treePatternLexer.nextToken();
    }

    public Object pattern() {
        int i = this.ttype;
        if (i == 1) {
            return parseTree();
        }
        if (i == 3) {
            Object node = parseNode();
            if (this.ttype == -1) {
                return node;
            }
        }
        return null;
    }

    public Object parseTree() {
        if (this.ttype != 1) {
            throw new RuntimeException("no BEGIN");
        }
        this.ttype = this.tokenizer.nextToken();
        Object node = parseNode();
        if (node == null) {
            return null;
        }
        while (true) {
            int i = this.ttype;
            if (i != 1 && i != 3 && i != 5 && i != 7) {
                if (i != 2) {
                    throw new RuntimeException("no END");
                }
                this.ttype = this.tokenizer.nextToken();
                return node;
            }
            if (i == 1) {
                this.adaptor.addChild(node, parseTree());
            } else {
                Object node2 = parseNode();
                if (node2 == null) {
                    return null;
                }
                this.adaptor.addChild(node, node2);
            }
        }
    }

    public Object parseNode() {
        String string;
        String string2;
        String str;
        if (this.ttype == 5) {
            int iNextToken = this.tokenizer.nextToken();
            this.ttype = iNextToken;
            if (iNextToken != 3) {
                return null;
            }
            string = this.tokenizer.sval.toString();
            int iNextToken2 = this.tokenizer.nextToken();
            this.ttype = iNextToken2;
            if (iNextToken2 != 6) {
                return null;
            }
            this.ttype = this.tokenizer.nextToken();
        } else {
            string = null;
        }
        int i = this.ttype;
        if (i == 7) {
            this.ttype = this.tokenizer.nextToken();
            TreeWizard.WildcardTreePattern wildcardTreePattern = new TreeWizard.WildcardTreePattern(new CommonToken(0, "."));
            if (string != null) {
                wildcardTreePattern.label = string;
            }
            return wildcardTreePattern;
        }
        if (i != 3) {
            return null;
        }
        String string3 = this.tokenizer.sval.toString();
        this.ttype = this.tokenizer.nextToken();
        if (string3.equals("nil")) {
            return this.adaptor.nil();
        }
        if (this.ttype == 4) {
            string2 = this.tokenizer.sval.toString();
            this.ttype = this.tokenizer.nextToken();
            str = string2;
        } else {
            string2 = string3;
            str = null;
        }
        int tokenType = this.wizard.getTokenType(string3);
        if (tokenType == 0) {
            return null;
        }
        Object objCreate = this.adaptor.create(tokenType, string2);
        if (string != null && objCreate.getClass() == TreeWizard.TreePattern.class) {
            ((TreeWizard.TreePattern) objCreate).label = string;
        }
        if (str != null && objCreate.getClass() == TreeWizard.TreePattern.class) {
            ((TreeWizard.TreePattern) objCreate).hasTextArg = true;
        }
        return objCreate;
    }
}
