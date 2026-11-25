package org.eclipse.jdt.internal.compiler.codegen;

/* JADX INFO: loaded from: classes14.dex */
public abstract class Label {
    public static final int POS_NOT_SET = -1;
    public CodeStream codeStream;
    public int position = -1;

    public abstract void place();

    public Label() {
    }

    public Label(CodeStream codeStream) {
        this.codeStream = codeStream;
    }
}
