package org.jetbrains.kotlin.com.intellij.util.io;

/* JADX INFO: loaded from: classes3.dex */
public final class BooleanDataDescriptor extends InlineKeyDescriptor<Boolean> {
    public static final BooleanDataDescriptor INSTANCE = new BooleanDataDescriptor();

    @Override // org.jetbrains.kotlin.com.intellij.util.io.InlineKeyDescriptor
    protected boolean isCompactFormat() {
        return true;
    }

    private BooleanDataDescriptor() {
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // org.jetbrains.kotlin.com.intellij.util.io.InlineKeyDescriptor
    public Boolean fromInt(int i) {
        return Boolean.valueOf(i != 0);
    }

    @Override // org.jetbrains.kotlin.com.intellij.util.io.InlineKeyDescriptor
    public int toInt(Boolean bool) {
        return bool == Boolean.TRUE ? 1 : 0;
    }
}
