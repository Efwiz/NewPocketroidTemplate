package org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.com.google.common.base.MoreObjects;
import org.jetbrains.kotlin.com.intellij.openapi.util.text.StringUtil;
import org.jetbrains.kotlin.com.intellij.psi.PsiType;
import org.jetbrains.kotlin.com.intellij.psi.util.TypeConversionUtil;
import org.jetbrains.kotlin.com.intellij.util.io.IOUtil;

/* JADX INFO: loaded from: classes3.dex */
public class FunctionalExpressionKey {
    public static final int UNKNOWN_PARAM_COUNT = -1;
    private final String knownType;
    private final int lambdaParameterCount;
    private final CoarseType lambdaReturnType;

    public enum CoarseType {
        VOID,
        UNKNOWN,
        BOOLEAN,
        NON_VOID
    }

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[3];
        if (i == 1 || i == 2) {
            objArr[0] = "dataStream";
        } else {
            objArr[0] = "lambdaReturnType";
        }
        objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/java/stubs/FunctionalExpressionKey";
        if (i == 1) {
            objArr[2] = "deserializeKey";
        } else if (i != 2) {
            objArr[2] = "<init>";
        } else {
            objArr[2] = "serializeKey";
        }
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
    }

    public FunctionalExpressionKey(int i, CoarseType coarseType, String str) {
        if (coarseType == null) {
            $$$reportNull$$$0(0);
        }
        this.lambdaParameterCount = i;
        this.lambdaReturnType = coarseType;
        this.knownType = StringUtil.notNullize(str);
    }

    public static FunctionalExpressionKey deserializeKey(DataInput dataInput) throws IOException {
        if (dataInput == null) {
            $$$reportNull$$$0(1);
        }
        return new FunctionalExpressionKey(dataInput.readByte(), CoarseType.values()[dataInput.readByte()], IOUtil.readUTF(dataInput));
    }

    public void serializeKey(DataOutput dataOutput) throws IOException {
        if (dataOutput == null) {
            $$$reportNull$$$0(2);
        }
        dataOutput.writeByte(this.lambdaParameterCount);
        dataOutput.writeByte(this.lambdaReturnType.ordinal());
        IOUtil.writeUTF(dataOutput, this.knownType);
    }

    public boolean canRepresent(int i, boolean z, boolean z2) {
        int i2 = this.lambdaParameterCount;
        if (i2 >= 0 && i != i2) {
            return false;
        }
        int i3 = AnonymousClass1.$SwitchMap$com$intellij$psi$impl$java$stubs$FunctionalExpressionKey$CoarseType[this.lambdaReturnType.ordinal()];
        if (i3 == 1) {
            return z2;
        }
        if (i3 == 2) {
            return !z2;
        }
        if (i3 != 3) {
            return true;
        }
        return z;
    }

    /* JADX INFO: renamed from: org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.FunctionalExpressionKey$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$intellij$psi$impl$java$stubs$FunctionalExpressionKey$CoarseType;

        static {
            int[] iArr = new int[CoarseType.values().length];
            $SwitchMap$com$intellij$psi$impl$java$stubs$FunctionalExpressionKey$CoarseType = iArr;
            try {
                iArr[CoarseType.VOID.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$intellij$psi$impl$java$stubs$FunctionalExpressionKey$CoarseType[CoarseType.NON_VOID.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$intellij$psi$impl$java$stubs$FunctionalExpressionKey$CoarseType[CoarseType.BOOLEAN.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
        }
    }

    public static boolean isBooleanCompatible(PsiType psiType) {
        return PsiType.BOOLEAN.equals(psiType) || TypeConversionUtil.isAssignableFromPrimitiveWrapper(TypeConversionUtil.erasure(psiType));
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof FunctionalExpressionKey)) {
            return false;
        }
        FunctionalExpressionKey functionalExpressionKey = (FunctionalExpressionKey) obj;
        return this.lambdaParameterCount == functionalExpressionKey.lambdaParameterCount && this.lambdaReturnType == functionalExpressionKey.lambdaReturnType && this.knownType.equals(functionalExpressionKey.knownType);
    }

    public int hashCode() {
        return (((this.lambdaParameterCount * 31) + this.lambdaReturnType.ordinal()) * 31) + this.knownType.hashCode();
    }

    public String toString() {
        return MoreObjects.toStringHelper(this).add("lambdaParameterCount", this.lambdaParameterCount).add(ModuleXmlParser.TYPE, this.lambdaReturnType).add("knownType", this.knownType).toString();
    }
}
