package org.jetbrains.kotlin.com.intellij.psi.impl.source;

import org.jetbrains.kotlin.com.intellij.util.CharTable;

/* JADX INFO: loaded from: classes3.dex */
public final class IdentityCharTable implements CharTable {
    public static final IdentityCharTable INSTANCE = new IdentityCharTable();

    /* JADX WARN: Code duplicated, block: B:21:0x002f  */
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 1 || i == 3 || i == 4) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 1 || i == 3 || i == 4) ? 2 : 3];
        if (i == 1) {
            objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/IdentityCharTable";
        } else if (i == 2) {
            objArr[0] = "baseText";
        } else if (i == 3 || i == 4) {
            objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/IdentityCharTable";
        } else {
            objArr[0] = "text";
        }
        if (i == 1 || i == 3 || i == 4) {
            objArr[1] = "intern";
        } else {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/IdentityCharTable";
        }
        if (i != 1 && i != 3 && i != 4) {
            objArr[2] = "intern";
        }
        String str2 = String.format(str, objArr);
        if (i != 1 && i != 3 && i != 4) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    private IdentityCharTable() {
    }

    @Override // org.jetbrains.kotlin.com.intellij.util.CharTable
    public CharSequence intern(CharSequence charSequence) {
        if (charSequence == null) {
            $$$reportNull$$$0(0);
        }
        if (charSequence == null) {
            $$$reportNull$$$0(1);
        }
        return charSequence;
    }

    @Override // org.jetbrains.kotlin.com.intellij.util.CharTable
    public CharSequence intern(CharSequence charSequence, int i, int i2) {
        if (charSequence == null) {
            $$$reportNull$$$0(2);
        }
        if (i == 0 && i2 == charSequence.length()) {
            if (charSequence == null) {
                $$$reportNull$$$0(3);
            }
            return charSequence;
        }
        CharSequence charSequenceSubSequence = charSequence.subSequence(i, i2);
        if (charSequenceSubSequence == null) {
            $$$reportNull$$$0(4);
        }
        return charSequenceSubSequence;
    }
}
