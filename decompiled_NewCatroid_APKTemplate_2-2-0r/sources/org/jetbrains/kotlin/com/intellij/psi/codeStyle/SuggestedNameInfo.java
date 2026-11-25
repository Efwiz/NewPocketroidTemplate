package org.jetbrains.kotlin.com.intellij.psi.codeStyle;

import net.bytebuddy.implementation.MethodDelegation;
import org.jetbrains.kotlin.com.intellij.openapi.util.Key;
import org.jetbrains.kotlin.com.intellij.util.ArrayUtilRt;

/* JADX INFO: loaded from: classes3.dex */
public abstract class SuggestedNameInfo {
    public final String[] names;
    public static final Key<SuggestedNameInfo> SUGGESTED_NAME_INFO_KEY = Key.create("SUGGESTED_NAME_INFO_KEY");
    public static final SuggestedNameInfo NULL_INFO = new SuggestedNameInfo(ArrayUtilRt.EMPTY_STRING_ARRAY) { // from class: org.jetbrains.kotlin.com.intellij.psi.codeStyle.SuggestedNameInfo.1
    };

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", "names", "org/jetbrains/kotlin/com/intellij/psi/codeStyle/SuggestedNameInfo", "<init>"));
    }

    public void nameChosen(String str) {
    }

    public SuggestedNameInfo(String[] strArr) {
        if (strArr == null) {
            $$$reportNull$$$0(0);
        }
        this.names = strArr;
    }

    public static class Delegate extends SuggestedNameInfo {
        SuggestedNameInfo myDelegate;

        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            Object[] objArr = new Object[3];
            if (i != 1) {
                objArr[0] = MethodDelegation.ImplementationDelegate.FIELD_NAME_PREFIX;
            } else {
                objArr[0] = "names";
            }
            objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/codeStyle/SuggestedNameInfo$Delegate";
            objArr[2] = "<init>";
            throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public Delegate(String[] strArr, SuggestedNameInfo suggestedNameInfo) {
            super(strArr);
            if (suggestedNameInfo == null) {
                $$$reportNull$$$0(0);
            }
            if (strArr == null) {
                $$$reportNull$$$0(1);
            }
            this.myDelegate = suggestedNameInfo;
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.codeStyle.SuggestedNameInfo
        public void nameChosen(String str) {
            this.myDelegate.nameChosen(str);
        }
    }
}
