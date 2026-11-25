package org.jetbrains.kotlin.com.intellij.lang;

import com.huawei.hms.ml.language.common.utils.Constant;
import net.bytebuddy.implementation.MethodDelegation;
import org.jetbrains.kotlin.com.intellij.psi.tree.IElementType;

/* JADX INFO: loaded from: classes3.dex */
public class TokenWrapper extends IElementType {
    private final IElementType myDelegate;
    private final String myValue;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 2 || i == 3) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 2 || i == 3) ? 2 : 3];
        if (i == 1) {
            objArr[0] = "value";
        } else if (i == 2 || i == 3) {
            objArr[0] = "org/jetbrains/kotlin/com/intellij/lang/TokenWrapper";
        } else {
            objArr[0] = MethodDelegation.ImplementationDelegate.FIELD_NAME_PREFIX;
        }
        if (i == 2) {
            objArr[1] = "getDelegate";
        } else if (i != 3) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/lang/TokenWrapper";
        } else {
            objArr[1] = "getValue";
        }
        if (i != 2 && i != 3) {
            objArr[2] = "<init>";
        }
        String str2 = String.format(str, objArr);
        if (i != 2 && i != 3) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    public IElementType getDelegate() {
        IElementType iElementType = this.myDelegate;
        if (iElementType == null) {
            $$$reportNull$$$0(2);
        }
        return iElementType;
    }

    public String getValue() {
        String str = this.myValue;
        if (str == null) {
            $$$reportNull$$$0(3);
        }
        return str;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.tree.IElementType
    public String toString() {
        return "Wrapper (" + this.myDelegate + Constant.AFTER_QUTO;
    }
}
