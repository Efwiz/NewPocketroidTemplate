package org.jetbrains.kotlin.js.inline;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.js.inline.util.FunctionWithWrapper;

/* JADX INFO: compiled from: InlineFunctionDefinition.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0006\u0018\u00002\u00020\u0001B\u0017\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005¢\u0006\u0002\u0010\u0006R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0013\u0010\u0004\u001a\u0004\u0018\u00010\u0005¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/js/inline/InlineFunctionDefinition;", "", "fn", "Lorg/jetbrains/kotlin/js/inline/util/FunctionWithWrapper;", "tag", "", "(Lorg/jetbrains/kotlin/js/inline/util/FunctionWithWrapper;Ljava/lang/String;)V", "getFn", "()Lorg/jetbrains/kotlin/js/inline/util/FunctionWithWrapper;", "getTag", "()Ljava/lang/String;", "js.translator"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class InlineFunctionDefinition {
    private final FunctionWithWrapper fn;
    private final String tag;

    public InlineFunctionDefinition(FunctionWithWrapper fn, String str) {
        Intrinsics.checkNotNullParameter(fn, "fn");
        this.fn = fn;
        this.tag = str;
    }

    public final FunctionWithWrapper getFn() {
        return this.fn;
    }

    public final String getTag() {
        return this.tag;
    }
}
