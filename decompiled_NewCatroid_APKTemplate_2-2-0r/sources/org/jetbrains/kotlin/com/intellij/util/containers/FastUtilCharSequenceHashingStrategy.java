package org.jetbrains.kotlin.com.intellij.util.containers;

import org.jetbrains.kotlin.com.intellij.openapi.util.text.StringUtilRt;
import org.jetbrains.kotlin.com.intellij.openapi.util.text.Strings;
import org.jetbrains.kotlin.it.unimi.dsi.fastutil.Hash;

/* JADX INFO: compiled from: FastUtilHashingStrategies.java */
/* JADX INFO: loaded from: classes3.dex */
final class FastUtilCharSequenceHashingStrategy implements Hash.Strategy<CharSequence> {
    private final boolean isCaseSensitive;

    FastUtilCharSequenceHashingStrategy(boolean z) {
        this.isCaseSensitive = z;
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.Hash.Strategy
    public int hashCode(CharSequence charSequence) {
        if (charSequence == null) {
            return 0;
        }
        return this.isCaseSensitive ? Strings.stringHashCode(charSequence) : Strings.stringHashCodeInsensitive(charSequence);
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.Hash.Strategy
    public boolean equals(CharSequence charSequence, CharSequence charSequence2) {
        return StringUtilRt.equal(charSequence, charSequence2, this.isCaseSensitive);
    }
}
