package org.antlr.runtime.debug;

import java.io.IOException;
import org.antlr.runtime.Parser;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.RecognizerSharedState;
import org.antlr.runtime.TokenStream;

/* JADX INFO: loaded from: classes12.dex */
public class DebugParser extends Parser {
    protected DebugEventListener dbg;
    public boolean isCyclicDecision;

    public DebugParser(TokenStream tokenStream, DebugEventListener debugEventListener, RecognizerSharedState recognizerSharedState) {
        super(tokenStream instanceof DebugTokenStream ? tokenStream : new DebugTokenStream(tokenStream, debugEventListener), recognizerSharedState);
        this.dbg = null;
        this.isCyclicDecision = false;
        setDebugListener(debugEventListener);
    }

    public DebugParser(TokenStream tokenStream, RecognizerSharedState recognizerSharedState) {
        super(tokenStream instanceof DebugTokenStream ? tokenStream : new DebugTokenStream(tokenStream, null), recognizerSharedState);
        this.dbg = null;
        this.isCyclicDecision = false;
    }

    public DebugParser(TokenStream tokenStream, DebugEventListener debugEventListener) {
        this(tokenStream instanceof DebugTokenStream ? tokenStream : new DebugTokenStream(tokenStream, debugEventListener), debugEventListener, null);
    }

    public void setDebugListener(DebugEventListener debugEventListener) {
        if (this.input instanceof DebugTokenStream) {
            ((DebugTokenStream) this.input).setDebugListener(debugEventListener);
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

    @Override // org.antlr.runtime.BaseRecognizer
    public void reportError(RecognitionException recognitionException) {
        super.reportError(recognitionException);
        this.dbg.recognitionException(recognitionException);
    }
}
