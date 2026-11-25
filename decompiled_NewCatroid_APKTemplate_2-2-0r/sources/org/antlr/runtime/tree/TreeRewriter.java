package org.antlr.runtime.tree;

import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.RecognizerSharedState;
import org.antlr.runtime.TokenStream;

/* JADX INFO: loaded from: classes12.dex */
public class TreeRewriter extends TreeParser {
    fptr bottomup_ftpr;
    protected TreeAdaptor originalAdaptor;
    protected TokenStream originalTokenStream;
    protected boolean showTransformations;
    fptr topdown_fptr;

    public interface fptr {
        Object rule() throws RecognitionException;
    }

    public Object bottomup() throws RecognitionException {
        return null;
    }

    public Object topdown() throws RecognitionException {
        return null;
    }

    public TreeRewriter(TreeNodeStream treeNodeStream) {
        this(treeNodeStream, new RecognizerSharedState());
    }

    public TreeRewriter(TreeNodeStream treeNodeStream, RecognizerSharedState recognizerSharedState) {
        super(treeNodeStream, recognizerSharedState);
        this.showTransformations = false;
        this.topdown_fptr = new fptr() { // from class: org.antlr.runtime.tree.TreeRewriter.2
            @Override // org.antlr.runtime.tree.TreeRewriter.fptr
            public Object rule() throws RecognitionException {
                return TreeRewriter.this.topdown();
            }
        };
        this.bottomup_ftpr = new fptr() { // from class: org.antlr.runtime.tree.TreeRewriter.3
            @Override // org.antlr.runtime.tree.TreeRewriter.fptr
            public Object rule() throws RecognitionException {
                return TreeRewriter.this.bottomup();
            }
        };
        this.originalAdaptor = treeNodeStream.getTreeAdaptor();
        this.originalTokenStream = treeNodeStream.getTokenStream();
    }

    public Object applyOnce(Object obj, fptr fptrVar) {
        if (obj == null) {
            return null;
        }
        try {
            this.state = new RecognizerSharedState();
            this.input = new CommonTreeNodeStream(this.originalAdaptor, obj);
            ((CommonTreeNodeStream) this.input).setTokenStream(this.originalTokenStream);
            setBacktrackingLevel(1);
            TreeRuleReturnScope treeRuleReturnScope = (TreeRuleReturnScope) fptrVar.rule();
            setBacktrackingLevel(0);
            if (failed()) {
                return obj;
            }
            if (this.showTransformations && treeRuleReturnScope != null && !obj.equals(treeRuleReturnScope.getTree()) && treeRuleReturnScope.getTree() != null) {
                reportTransformation(obj, treeRuleReturnScope.getTree());
            }
            return (treeRuleReturnScope == null || treeRuleReturnScope.getTree() == null) ? obj : treeRuleReturnScope.getTree();
        } catch (RecognitionException unused) {
            return obj;
        }
    }

    public Object applyRepeatedly(Object obj, fptr fptrVar) {
        boolean z = true;
        while (z) {
            Object objApplyOnce = applyOnce(obj, fptrVar);
            z = !obj.equals(objApplyOnce);
            obj = objApplyOnce;
        }
        return obj;
    }

    public Object downup(Object obj) {
        return downup(obj, false);
    }

    public Object downup(Object obj, boolean z) {
        this.showTransformations = z;
        return new TreeVisitor(new CommonTreeAdaptor()).visit(obj, new TreeVisitorAction() { // from class: org.antlr.runtime.tree.TreeRewriter.1
            @Override // org.antlr.runtime.tree.TreeVisitorAction
            public Object pre(Object obj2) {
                TreeRewriter treeRewriter = TreeRewriter.this;
                return treeRewriter.applyOnce(obj2, treeRewriter.topdown_fptr);
            }

            @Override // org.antlr.runtime.tree.TreeVisitorAction
            public Object post(Object obj2) {
                TreeRewriter treeRewriter = TreeRewriter.this;
                return treeRewriter.applyRepeatedly(obj2, treeRewriter.bottomup_ftpr);
            }
        });
    }

    public void reportTransformation(Object obj, Object obj2) {
        System.out.println(((Tree) obj).toStringTree() + " -> " + ((Tree) obj2).toStringTree());
    }
}
