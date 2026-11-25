package org.jetbrains.kotlin.com.intellij.util.text;

import com.huawei.hms.ml.language.common.utils.LanguageCodeUtil;
import org.apache.xerces.impl.xs.SchemaSymbols;

/* JADX INFO: loaded from: classes3.dex */
public abstract class ImmutableCharSequence implements CharSequence {
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[3];
        if (i != 2) {
            objArr[0] = LanguageCodeUtil.CS;
        } else {
            objArr[0] = "seq";
        }
        objArr[1] = "org/jetbrains/kotlin/com/intellij/util/text/ImmutableCharSequence";
        if (i == 1) {
            objArr[2] = "isImmutable";
        } else if (i != 2) {
            objArr[2] = "asImmutable";
        } else {
            objArr[2] = SchemaSymbols.ATTVAL_REPLACE;
        }
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
    }

    public abstract ImmutableCharSequence delete(int i, int i2);

    public abstract ImmutableCharSequence insert(int i, CharSequence charSequence);

    public abstract ImmutableCharSequence subtext(int i, int i2);

    @Override // java.lang.CharSequence
    public abstract String toString();

    public static CharSequence asImmutable(CharSequence charSequence) {
        if (charSequence == null) {
            $$$reportNull$$$0(0);
        }
        return isImmutable(charSequence) ? charSequence : charSequence.toString();
    }

    private static boolean isImmutable(CharSequence charSequence) {
        if (charSequence == null) {
            $$$reportNull$$$0(1);
        }
        if (charSequence instanceof ImmutableCharSequence) {
            return true;
        }
        return (charSequence instanceof CharSequenceSubSequence) && isImmutable(((CharSequenceSubSequence) charSequence).getBaseSequence());
    }

    public ImmutableCharSequence replace(int i, int i2, CharSequence charSequence) {
        if (charSequence == null) {
            $$$reportNull$$$0(2);
        }
        return delete(i, i2).insert(i, charSequence);
    }
}
