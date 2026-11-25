package org.jetbrains.kotlin.com.intellij.openapi.util.io;

import java.io.IOException;

/* JADX INFO: loaded from: classes3.dex */
class NullAppendable implements Appendable {
    static Appendable INSTANCE = new NullAppendable();

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        throw new IllegalStateException(String.format("@NotNull method %s.%s must not return null", "org/jetbrains/kotlin/com/intellij/openapi/util/io/NullAppendable", "append"));
    }

    @Override // java.lang.Appendable
    public Appendable append(char c) throws IOException {
        return this;
    }

    @Override // java.lang.Appendable
    public Appendable append(CharSequence charSequence) throws IOException {
        return this;
    }

    @Override // java.lang.Appendable
    public Appendable append(CharSequence charSequence, int i, int i2) throws IOException {
        return this;
    }

    NullAppendable() {
    }
}
