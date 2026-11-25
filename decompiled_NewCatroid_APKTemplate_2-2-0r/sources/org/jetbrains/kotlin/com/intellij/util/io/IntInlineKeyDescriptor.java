package org.jetbrains.kotlin.com.intellij.util.io;

/* JADX INFO: loaded from: classes3.dex */
public class IntInlineKeyDescriptor extends InlineKeyDescriptor<Integer> {
    /* JADX WARN: Can't rename method to resolve collision */
    @Override // org.jetbrains.kotlin.com.intellij.util.io.InlineKeyDescriptor
    public Integer fromInt(int i) {
        return Integer.valueOf(i);
    }

    @Override // org.jetbrains.kotlin.com.intellij.util.io.InlineKeyDescriptor
    public int toInt(Integer num) {
        return num.intValue();
    }
}
