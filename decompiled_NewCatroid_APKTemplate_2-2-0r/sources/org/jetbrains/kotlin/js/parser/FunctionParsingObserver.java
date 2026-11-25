package org.jetbrains.kotlin.js.parser;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.google.gwt.dev.js.rhino.ParserListener;
import org.jetbrains.kotlin.com.google.gwt.dev.js.rhino.TokenStream;

/* JADX INFO: compiled from: parserUtils.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fH\u0016J\b\u0010\r\u001a\u00020\nH\u0016R\u001a\u0010\u0003\u001a\u00020\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\b¨\u0006\u000e"}, d2 = {"Lorg/jetbrains/kotlin/js/parser/FunctionParsingObserver;", "Lorg/jetbrains/kotlin/com/google/gwt/dev/js/rhino/ParserListener;", "()V", "functionsStarted", "", "getFunctionsStarted", "()I", "setFunctionsStarted", "(I)V", "functionEnded", "", "tokenStream", "Lorg/jetbrains/kotlin/com/google/gwt/dev/js/rhino/TokenStream;", "functionStarted", "js.parser"}, k = 1, mv = {1, 9, 0}, xi = 48)
final class FunctionParsingObserver implements ParserListener {
    private int functionsStarted;

    public final int getFunctionsStarted() {
        return this.functionsStarted;
    }

    public final void setFunctionsStarted(int i) {
        this.functionsStarted = i;
    }

    @Override // org.jetbrains.kotlin.com.google.gwt.dev.js.rhino.ParserListener
    public void functionStarted() {
        this.functionsStarted++;
    }

    @Override // org.jetbrains.kotlin.com.google.gwt.dev.js.rhino.ParserListener
    public void functionEnded(TokenStream tokenStream) {
        Intrinsics.checkNotNullParameter(tokenStream, "tokenStream");
        int i = this.functionsStarted - 1;
        this.functionsStarted = i;
        if (i == 0) {
            tokenStream.ungetToken(0);
        }
    }
}
