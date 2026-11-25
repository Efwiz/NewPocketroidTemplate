package org.jetbrains.kotlin.com.intellij.util.containers;

import org.jetbrains.kotlin.com.intellij.openapi.util.text.StringUtilRt;
import org.jetbrains.kotlin.it.unimi.dsi.fastutil.Hash;

/* JADX INFO: compiled from: FastUtilHashingStrategies.java */
/* JADX INFO: loaded from: classes3.dex */
final class FastUtilCaseInsensitiveStringHashingStrategy implements Hash.Strategy<String> {
    static final Hash.Strategy<String> INSTANCE = new FastUtilCaseInsensitiveStringHashingStrategy();

    FastUtilCaseInsensitiveStringHashingStrategy() {
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.Hash.Strategy
    public int hashCode(String str) {
        if (str == null) {
            return 0;
        }
        return StringUtilRt.stringHashCodeInsensitive(str);
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.Hash.Strategy
    public boolean equals(String str, String str2) {
        return str == str2 || (str != null && str.equalsIgnoreCase(str2));
    }
}
