package org.jetbrains.kotlin.codegen.inline;

/* JADX INFO: loaded from: classes3.dex */
class InvokeCall {
    public final int finallyDepthShift;
    public final FunctionalArgument functionalArgument;

    InvokeCall(FunctionalArgument functionalArgument, int i) {
        this.functionalArgument = functionalArgument;
        this.finallyDepthShift = i;
    }
}
