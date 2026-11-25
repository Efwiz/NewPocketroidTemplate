package org.antlr.runtime.debug;

import java.io.IOException;
import org.antlr.runtime.BitSet;
import org.antlr.runtime.IntStream;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.RecognizerSharedState;
import org.antlr.runtime.tree.TreeNodeStream;
import org.antlr.runtime.tree.TreeParser;

/* JADX INFO: loaded from: classes12.dex */
public class DebugTreeParser extends TreeParser {
    protected DebugEventListener dbg;
    public boolean isCyclicDecision;

    public DebugTreeParser(TreeNodeStream treeNodeStream, DebugEventListener debugEventListener, RecognizerSharedState recognizerSharedState) {
        super(treeNodeStream instanceof DebugTreeNodeStream ? treeNodeStream : new DebugTreeNodeStream(treeNodeStream, debugEventListener), recognizerSharedState);
        this.dbg = null;
        this.isCyclicDecision = false;
        setDebugListener(debugEventListener);
    }

    public DebugTreeParser(TreeNodeStream treeNodeStream, RecognizerSharedState recognizerSharedState) {
        super(treeNodeStream instanceof DebugTreeNodeStream ? treeNodeStream : new DebugTreeNodeStream(treeNodeStream, null), recognizerSharedState);
        this.dbg = null;
        this.isCyclicDecision = false;
    }

    public DebugTreeParser(TreeNodeStream treeNodeStream, DebugEventListener debugEventListener) {
        this(treeNodeStream instanceof DebugTreeNodeStream ? treeNodeStream : new DebugTreeNodeStream(treeNodeStream, debugEventListener), debugEventListener, null);
    }

    public void setDebugListener(DebugEventListener debugEventListener) {
        if (this.input instanceof DebugTreeNodeStream) {
            ((DebugTreeNodeStream) this.input).setDebugListener(debugEventListener);
        }
        this.dbg = debugEventListener;
    }

    public DebugEventListener getDebugListener() {
        return this.dbg;
    }

    public void reportError(IOException iOException) {
        System.err.println(iOException);
        iOException.printStackTrace(System.err);
    }

    @Override // org.antlr.runtime.BaseRecognizer
    public void reportError(RecognitionException recognitionException) {
        this.dbg.recognitionException(recognitionException);
    }

    @Override // org.antlr.runtime.tree.TreeParser, org.antlr.runtime.BaseRecognizer
    protected Object getMissingSymbol(IntStream intStream, RecognitionException recognitionException, int i, BitSet bitSet) {
        Object missingSymbol = super.getMissingSymbol(intStream, recognitionException, i, bitSet);
        this.dbg.consumeNode(missingSymbol);
        return missingSymbol;
    }

    @Override // org.antlr.runtime.BaseRecognizer
    public void beginResync() {
        this.dbg.beginResync();
    }

    @Override // org.antlr.runtime.BaseRecognizer
    public void endResync() {
        this.dbg.endResync();
    }

    public void beginBacktrack(int i) {
        this.dbg.beginBacktrack(i);
    }

    public void endBacktrack(int i, boolean z) {
        this.dbg.endBacktrack(i, z);
    }
}
