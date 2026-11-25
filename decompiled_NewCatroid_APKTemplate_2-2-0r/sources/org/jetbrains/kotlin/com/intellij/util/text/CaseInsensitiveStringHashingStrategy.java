package org.jetbrains.kotlin.com.intellij.util.text;

import gnu.trove.TObjectHashingStrategy;
import org.jetbrains.kotlin.com.intellij.openapi.util.text.Strings;

/* JADX INFO: loaded from: classes3.dex */
@Deprecated
public final class CaseInsensitiveStringHashingStrategy implements TObjectHashingStrategy<String> {
    public static final CaseInsensitiveStringHashingStrategy INSTANCE = new CaseInsensitiveStringHashingStrategy();

    @Override // gnu.trove.TObjectHashingStrategy
    public int computeHashCode(String str) {
        return Strings.stringHashCodeInsensitive(str);
    }

    @Override // gnu.trove.TObjectHashingStrategy, gnu.trove.Equality
    public boolean equals(String str, String str2) {
        return str.equalsIgnoreCase(str2);
    }
}
