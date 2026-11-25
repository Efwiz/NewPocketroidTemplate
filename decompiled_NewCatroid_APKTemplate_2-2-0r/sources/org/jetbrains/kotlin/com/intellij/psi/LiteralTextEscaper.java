package org.jetbrains.kotlin.com.intellij.psi;

import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.com.intellij.openapi.util.TextRange;
import org.jetbrains.kotlin.com.intellij.psi.PsiLanguageInjectionHost;

/* JADX INFO: loaded from: classes3.dex */
public abstract class LiteralTextEscaper<T extends PsiLanguageInjectionHost> {
    protected final T myHost;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = i != 1 ? "Argument for @NotNull parameter '%s' of %s.%s must not be null" : "@NotNull method %s.%s must not return null";
        Object[] objArr = new Object[i != 1 ? 3 : 2];
        if (i == 1) {
            objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/LiteralTextEscaper";
        } else if (i != 2) {
            objArr[0] = "host";
        } else {
            objArr[0] = CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD;
        }
        if (i != 1) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/LiteralTextEscaper";
        } else {
            objArr[1] = "getRelevantTextRange";
        }
        if (i != 1) {
            if (i != 2) {
                objArr[2] = "<init>";
            } else {
                objArr[2] = "createSimple";
            }
        }
        String str2 = String.format(str, objArr);
        if (i == 1) {
            throw new IllegalStateException(str2);
        }
    }

    public abstract boolean decode(TextRange textRange, StringBuilder sb);

    public abstract int getOffsetInHost(int i, TextRange textRange);

    public abstract boolean isOneLine();

    protected LiteralTextEscaper(T t) {
        if (t == null) {
            $$$reportNull$$$0(0);
        }
        this.myHost = t;
    }

    public TextRange getRelevantTextRange() {
        TextRange textRangeFrom = TextRange.from(0, this.myHost.getTextLength());
        if (textRangeFrom == null) {
            $$$reportNull$$$0(1);
        }
        return textRangeFrom;
    }

    public static <T extends PsiLanguageInjectionHost> LiteralTextEscaper<T> createSimple(T t) {
        if (t == null) {
            $$$reportNull$$$0(2);
        }
        return (LiteralTextEscaper<T>) new LiteralTextEscaper<T>(t) { // from class: org.jetbrains.kotlin.com.intellij.psi.LiteralTextEscaper.1
            private static /* synthetic */ void $$$reportNull$$$0(int i) {
                Object[] objArr = new Object[3];
                if (i != 1) {
                    objArr[0] = "rangeInsideHost";
                } else {
                    objArr[0] = "outChars";
                }
                objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/LiteralTextEscaper$1";
                if (i != 2) {
                    objArr[2] = "decode";
                } else {
                    objArr[2] = "getOffsetInHost";
                }
                throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
            }

            @Override // org.jetbrains.kotlin.com.intellij.psi.LiteralTextEscaper
            public boolean isOneLine() {
                return true;
            }

            @Override // org.jetbrains.kotlin.com.intellij.psi.LiteralTextEscaper
            public boolean decode(TextRange textRange, StringBuilder sb) {
                if (textRange == null) {
                    $$$reportNull$$$0(0);
                }
                if (sb == null) {
                    $$$reportNull$$$0(1);
                }
                sb.append(textRange.substring(this.myHost.getText()));
                return true;
            }

            @Override // org.jetbrains.kotlin.com.intellij.psi.LiteralTextEscaper
            public int getOffsetInHost(int i, TextRange textRange) {
                if (textRange == null) {
                    $$$reportNull$$$0(2);
                }
                return textRange.getStartOffset() + i;
            }
        };
    }
}
