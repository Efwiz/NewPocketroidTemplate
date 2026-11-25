package org.antlr.runtime.tree;

import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.RecognizerSharedState;
import org.antlr.runtime.TokenStream;

/* JADX INFO: loaded from: classes12.dex */
public class TreeFilter extends TreeParser {
    fptr bottomup_fptr;
    protected TreeAdaptor originalAdaptor;
    protected TokenStream originalTokenStream;
    fptr topdown_fptr;

    public interface fptr {
        void rule() throws RecognitionException;
    }

    public void bottomup() throws RecognitionException {
    }

    public void topdown() throws RecognitionException {
    }

    public TreeFilter(TreeNodeStream treeNodeStream) {
        this(treeNodeStream, new RecognizerSharedState());
    }

    public TreeFilter(TreeNodeStream treeNodeStream, RecognizerSharedState recognizerSharedState) {
        super(treeNodeStream, recognizerSharedState);
        this.topdown_fptr = new fptr() { // from class: org.antlr.runtime.tree.TreeFilter.2
            @Override // org.antlr.runtime.tree.TreeFilter.fptr
            public void rule() throws RecognitionException {
                TreeFilter.this.topdown();
            }
        };
        this.bottomup_fptr = new fptr() { // from class: org.antlr.runtime.tree.TreeFilter.3
            @Override // org.antlr.runtime.tree.TreeFilter.fptr
            public void rule() throws RecognitionException {
                TreeFilter.this.bottomup();
            }
        };
        this.originalAdaptor = treeNodeStream.getTreeAdaptor();
        this.originalTokenStream = treeNodeStream.getTokenStream();
    }

    public void applyOnce(Object obj, fptr fptrVar) {
        if (obj == null) {
            return;
        }
        try {
            this.state = new RecognizerSharedState();
            this.input = new CommonTreeNodeStream(this.originalAdaptor, obj);
            ((CommonTreeNodeStream) this.input).setTokenStream(this.originalTokenStream);
            setBacktrackingLevel(1);
            fptrVar.rule();
            setBacktrackingLevel(0);
        } catch (RecognitionException unused) {
        }
    }

    public void downup(Object obj) {
        new TreeVisitor(new CommonTreeAdaptor()).visit(obj, new TreeVisitorAction() { // from class: org.antlr.runtime.tree.TreeFilter.1
            @Override // org.antlr.runtime.tree.TreeVisitorAction
            public Object pre(Object obj2) {
                TreeFilter treeFilter = TreeFilter.this;
                treeFilter.applyOnce(obj2, treeFilter.topdown_fptr);
                return obj2;
            }

            @Override // org.antlr.runtime.tree.TreeVisitorAction
            public Object post(Object obj2) {
                TreeFilter treeFilter = TreeFilter.this;
                treeFilter.applyOnce(obj2, treeFilter.bottomup_fptr);
                return obj2;
            }
        });
    }
}
