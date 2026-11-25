package org.jetbrains.kotlin.resolve.constants;

import androidx.exifinterface.media.ExifInterface;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.descriptors.ModuleDescriptor;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.types.KotlinType;

/* JADX INFO: compiled from: CompileTimeConstant.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000b\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\bf\u0018\u0000*\u0006\b\u0000\u0010\u0001 \u00012\u00020\u0002:\u0001\"J\u0013\u0010\u0018\u001a\u00020\u00042\b\u0010\u0019\u001a\u0004\u0018\u00010\u0002H¦\u0002J\u0015\u0010\u001a\u001a\u00028\u00002\u0006\u0010\u001b\u001a\u00020\u001cH\u0016¢\u0006\u0002\u0010\u001dJ\b\u0010\u001e\u001a\u00020\u001fH&J\u0016\u0010 \u001a\b\u0012\u0004\u0012\u00028\u00000!2\u0006\u0010\u001b\u001a\u00020\u001cH&R\u0014\u0010\u0003\u001a\u00020\u00048VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0005\u0010\u0006R\u0012\u0010\u0007\u001a\u00020\u0004X¦\u0004¢\u0006\u0006\u001a\u0004\b\b\u0010\u0006R\u0014\u0010\t\u001a\u00020\u00048VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\t\u0010\u0006R\u0014\u0010\n\u001a\u00020\u00048VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\n\u0010\u0006R\u0014\u0010\u000b\u001a\u00020\u00048VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u000b\u0010\u0006R\u0012\u0010\f\u001a\u00020\rX¦\u0004¢\u0006\u0006\u001a\u0004\b\u000e\u0010\u000fR\u0012\u0010\u0010\u001a\u00020\u0011X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0012\u0010\u0013R\u0014\u0010\u0014\u001a\u00020\u00048VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0015\u0010\u0006R\u0014\u0010\u0016\u001a\u00020\u00048VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0017\u0010\u0006¨\u0006#"}, d2 = {"Lorg/jetbrains/kotlin/resolve/constants/CompileTimeConstant;", ExifInterface.GPS_DIRECTION_TRUE, "", "canBeUsedInAnnotations", "", "getCanBeUsedInAnnotations", "()Z", "hasIntegerLiteralType", "getHasIntegerLiteralType", "isError", "isPure", "isUnsignedNumberLiteral", "moduleDescriptor", "Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;", "getModuleDescriptor", "()Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;", "parameters", "Lorg/jetbrains/kotlin/resolve/constants/CompileTimeConstant$Parameters;", "getParameters", "()Lorg/jetbrains/kotlin/resolve/constants/CompileTimeConstant$Parameters;", "usesNonConstValAsConstant", "getUsesNonConstValAsConstant", "usesVariableAsConstant", "getUsesVariableAsConstant", Namer.EQUALS_METHOD_NAME, "other", "getValue", "expectedType", "Lorg/jetbrains/kotlin/types/KotlinType;", "(Lorg/jetbrains/kotlin/types/KotlinType;)Ljava/lang/Object;", "hashCode", "", "toConstantValue", "Lorg/jetbrains/kotlin/resolve/constants/ConstantValue;", "Parameters", "descriptors"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface CompileTimeConstant<T> {
    boolean equals(Object other);

    boolean getCanBeUsedInAnnotations();

    boolean getHasIntegerLiteralType();

    ModuleDescriptor getModuleDescriptor();

    Parameters getParameters();

    boolean getUsesNonConstValAsConstant();

    boolean getUsesVariableAsConstant();

    T getValue(KotlinType expectedType);

    int hashCode();

    boolean isError();

    boolean isPure();

    boolean isUnsignedNumberLiteral();

    ConstantValue<T> toConstantValue(KotlinType expectedType);

    /* JADX INFO: compiled from: CompileTimeConstant.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    public static final class DefaultImpls {
        public static <T> boolean isError(CompileTimeConstant<? extends T> compileTimeConstant) {
            return false;
        }

        public static <T> T getValue(CompileTimeConstant<? extends T> compileTimeConstant, KotlinType expectedType) {
            Intrinsics.checkNotNullParameter(expectedType, "expectedType");
            return compileTimeConstant.toConstantValue(expectedType).getValue();
        }

        public static <T> boolean getCanBeUsedInAnnotations(CompileTimeConstant<? extends T> compileTimeConstant) {
            return compileTimeConstant.getParameters().getCanBeUsedInAnnotation();
        }

        public static <T> boolean getUsesVariableAsConstant(CompileTimeConstant<? extends T> compileTimeConstant) {
            return compileTimeConstant.getParameters().getUsesVariableAsConstant();
        }

        public static <T> boolean getUsesNonConstValAsConstant(CompileTimeConstant<? extends T> compileTimeConstant) {
            return compileTimeConstant.getParameters().getUsesNonConstValAsConstant();
        }

        public static <T> boolean isPure(CompileTimeConstant<? extends T> compileTimeConstant) {
            return compileTimeConstant.getParameters().isPure();
        }

        public static <T> boolean isUnsignedNumberLiteral(CompileTimeConstant<? extends T> compileTimeConstant) {
            return compileTimeConstant.getParameters().isUnsignedNumberLiteral();
        }
    }

    /* JADX INFO: compiled from: CompileTimeConstant.kt */
    @Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0016\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B=\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0003\u0012\u0006\u0010\u0006\u001a\u00020\u0003\u0012\u0006\u0010\u0007\u001a\u00020\u0003\u0012\u0006\u0010\b\u001a\u00020\u0003\u0012\u0006\u0010\t\u001a\u00020\u0003¢\u0006\u0002\u0010\nJ\t\u0010\u000f\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0010\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0011\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0012\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0013\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0014\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0015\u001a\u00020\u0003HÆ\u0003JO\u0010\u0016\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00032\b\b\u0002\u0010\u0006\u001a\u00020\u00032\b\b\u0002\u0010\u0007\u001a\u00020\u00032\b\b\u0002\u0010\b\u001a\u00020\u00032\b\b\u0002\u0010\t\u001a\u00020\u0003HÆ\u0001J\u0013\u0010\u0017\u001a\u00020\u00032\b\u0010\u0018\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0019\u001a\u00020\u001aHÖ\u0001J\t\u0010\u001b\u001a\u00020\u001cHÖ\u0001R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0011\u0010\t\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\fR\u0011\u0010\u0004\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0004\u0010\fR\u0011\u0010\u0006\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\fR\u0011\u0010\u0005\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\fR\u0011\u0010\b\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\fR\u0011\u0010\u0007\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\f¨\u0006\u001d"}, d2 = {"Lorg/jetbrains/kotlin/resolve/constants/CompileTimeConstant$Parameters;", "", "canBeUsedInAnnotation", "", "isPure", "isUnsignedNumberLiteral", "isUnsignedLongNumberLiteral", "usesVariableAsConstant", "usesNonConstValAsConstant", "isConvertableConstVal", "(ZZZZZZZ)V", "getCanBeUsedInAnnotation", "()Z", "getUsesNonConstValAsConstant", "getUsesVariableAsConstant", "component1", "component2", "component3", "component4", "component5", "component6", "component7", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "other", "hashCode", "", "toString", "", "descriptors"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final /* data */ class Parameters {
        private final boolean canBeUsedInAnnotation;
        private final boolean isConvertableConstVal;
        private final boolean isPure;
        private final boolean isUnsignedLongNumberLiteral;
        private final boolean isUnsignedNumberLiteral;
        private final boolean usesNonConstValAsConstant;
        private final boolean usesVariableAsConstant;

        public static /* synthetic */ Parameters copy$default(Parameters parameters, boolean z, boolean z2, boolean z3, boolean z4, boolean z5, boolean z6, boolean z7, int i, Object obj) {
            if ((i & 1) != 0) {
                z = parameters.canBeUsedInAnnotation;
            }
            if ((i & 2) != 0) {
                z2 = parameters.isPure;
            }
            boolean z8 = z2;
            if ((i & 4) != 0) {
                z3 = parameters.isUnsignedNumberLiteral;
            }
            boolean z9 = z3;
            if ((i & 8) != 0) {
                z4 = parameters.isUnsignedLongNumberLiteral;
            }
            boolean z10 = z4;
            if ((i & 16) != 0) {
                z5 = parameters.usesVariableAsConstant;
            }
            boolean z11 = z5;
            if ((i & 32) != 0) {
                z6 = parameters.usesNonConstValAsConstant;
            }
            boolean z12 = z6;
            if ((i & 64) != 0) {
                z7 = parameters.isConvertableConstVal;
            }
            return parameters.copy(z, z8, z9, z10, z11, z12, z7);
        }

        /* JADX INFO: renamed from: component1, reason: from getter */
        public final boolean getCanBeUsedInAnnotation() {
            return this.canBeUsedInAnnotation;
        }

        /* JADX INFO: renamed from: component2, reason: from getter */
        public final boolean getIsPure() {
            return this.isPure;
        }

        /* JADX INFO: renamed from: component3, reason: from getter */
        public final boolean getIsUnsignedNumberLiteral() {
            return this.isUnsignedNumberLiteral;
        }

        /* JADX INFO: renamed from: component4, reason: from getter */
        public final boolean getIsUnsignedLongNumberLiteral() {
            return this.isUnsignedLongNumberLiteral;
        }

        /* JADX INFO: renamed from: component5, reason: from getter */
        public final boolean getUsesVariableAsConstant() {
            return this.usesVariableAsConstant;
        }

        /* JADX INFO: renamed from: component6, reason: from getter */
        public final boolean getUsesNonConstValAsConstant() {
            return this.usesNonConstValAsConstant;
        }

        /* JADX INFO: renamed from: component7, reason: from getter */
        public final boolean getIsConvertableConstVal() {
            return this.isConvertableConstVal;
        }

        public final Parameters copy(boolean canBeUsedInAnnotation, boolean isPure, boolean isUnsignedNumberLiteral, boolean isUnsignedLongNumberLiteral, boolean usesVariableAsConstant, boolean usesNonConstValAsConstant, boolean isConvertableConstVal) {
            return new Parameters(canBeUsedInAnnotation, isPure, isUnsignedNumberLiteral, isUnsignedLongNumberLiteral, usesVariableAsConstant, usesNonConstValAsConstant, isConvertableConstVal);
        }

        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof Parameters)) {
                return false;
            }
            Parameters parameters = (Parameters) other;
            return this.canBeUsedInAnnotation == parameters.canBeUsedInAnnotation && this.isPure == parameters.isPure && this.isUnsignedNumberLiteral == parameters.isUnsignedNumberLiteral && this.isUnsignedLongNumberLiteral == parameters.isUnsignedLongNumberLiteral && this.usesVariableAsConstant == parameters.usesVariableAsConstant && this.usesNonConstValAsConstant == parameters.usesNonConstValAsConstant && this.isConvertableConstVal == parameters.isConvertableConstVal;
        }

        public int hashCode() {
            return (((((((((((Boolean.hashCode(this.canBeUsedInAnnotation) * 31) + Boolean.hashCode(this.isPure)) * 31) + Boolean.hashCode(this.isUnsignedNumberLiteral)) * 31) + Boolean.hashCode(this.isUnsignedLongNumberLiteral)) * 31) + Boolean.hashCode(this.usesVariableAsConstant)) * 31) + Boolean.hashCode(this.usesNonConstValAsConstant)) * 31) + Boolean.hashCode(this.isConvertableConstVal);
        }

        public String toString() {
            return "Parameters(canBeUsedInAnnotation=" + this.canBeUsedInAnnotation + ", isPure=" + this.isPure + ", isUnsignedNumberLiteral=" + this.isUnsignedNumberLiteral + ", isUnsignedLongNumberLiteral=" + this.isUnsignedLongNumberLiteral + ", usesVariableAsConstant=" + this.usesVariableAsConstant + ", usesNonConstValAsConstant=" + this.usesNonConstValAsConstant + ", isConvertableConstVal=" + this.isConvertableConstVal + ')';
        }

        public Parameters(boolean z, boolean z2, boolean z3, boolean z4, boolean z5, boolean z6, boolean z7) {
            this.canBeUsedInAnnotation = z;
            this.isPure = z2;
            this.isUnsignedNumberLiteral = z3;
            this.isUnsignedLongNumberLiteral = z4;
            this.usesVariableAsConstant = z5;
            this.usesNonConstValAsConstant = z6;
            this.isConvertableConstVal = z7;
        }

        public final boolean getCanBeUsedInAnnotation() {
            return this.canBeUsedInAnnotation;
        }

        public final boolean isPure() {
            return this.isPure;
        }

        public final boolean isUnsignedNumberLiteral() {
            return this.isUnsignedNumberLiteral;
        }

        public final boolean isUnsignedLongNumberLiteral() {
            return this.isUnsignedLongNumberLiteral;
        }

        public final boolean getUsesVariableAsConstant() {
            return this.usesVariableAsConstant;
        }

        public final boolean getUsesNonConstValAsConstant() {
            return this.usesNonConstValAsConstant;
        }

        public final boolean isConvertableConstVal() {
            return this.isConvertableConstVal;
        }
    }
}
