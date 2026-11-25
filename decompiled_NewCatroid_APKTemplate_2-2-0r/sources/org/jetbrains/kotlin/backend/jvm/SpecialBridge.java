package org.jetbrains.kotlin.backend.jvm;

import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.backend.common.lower.SpecialMethodWithDefaultInfo;
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction;
import org.jetbrains.kotlin.ir.symbols.IrClassSymbol;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.org.objectweb.asm.commons.Method;

/* JADX INFO: compiled from: SpecialBridge.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000B\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b'\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\u0089\u0001\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0007\u0012\u0010\b\u0002\u0010\b\u001a\n\u0012\u0004\u0012\u00020\n\u0018\u00010\t\u0012\n\b\u0002\u0010\u000b\u001a\u0004\u0018\u00010\n\u0012\n\b\u0002\u0010\f\u001a\u0004\u0018\u00010\r\u0012\n\b\u0002\u0010\u000e\u001a\u0004\u0018\u00010\u000f\u0012\b\b\u0002\u0010\u0010\u001a\u00020\u0007\u0012\b\b\u0002\u0010\u0011\u001a\u00020\u0007\u0012\b\b\u0002\u0010\u0012\u001a\u00020\u0007\u0012\b\b\u0002\u0010\u0013\u001a\u00020\u0007\u0012\n\b\u0002\u0010\u0014\u001a\u0004\u0018\u00010\u0000¢\u0006\u0002\u0010\u0015J\t\u0010'\u001a\u00020\u0003HÆ\u0003J\t\u0010(\u001a\u00020\u0007HÆ\u0003J\t\u0010)\u001a\u00020\u0007HÆ\u0003J\u000b\u0010*\u001a\u0004\u0018\u00010\u0000HÆ\u0003J\t\u0010+\u001a\u00020\u0005HÆ\u0003J\t\u0010,\u001a\u00020\u0007HÆ\u0003J\u0011\u0010-\u001a\n\u0012\u0004\u0012\u00020\n\u0018\u00010\tHÆ\u0003J\u000b\u0010.\u001a\u0004\u0018\u00010\nHÆ\u0003J\u000b\u0010/\u001a\u0004\u0018\u00010\rHÆ\u0003J\u000b\u00100\u001a\u0004\u0018\u00010\u000fHÆ\u0003J\t\u00101\u001a\u00020\u0007HÆ\u0003J\t\u00102\u001a\u00020\u0007HÆ\u0003J\u0091\u0001\u00103\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u00072\u0010\b\u0002\u0010\b\u001a\n\u0012\u0004\u0012\u00020\n\u0018\u00010\t2\n\b\u0002\u0010\u000b\u001a\u0004\u0018\u00010\n2\n\b\u0002\u0010\f\u001a\u0004\u0018\u00010\r2\n\b\u0002\u0010\u000e\u001a\u0004\u0018\u00010\u000f2\b\b\u0002\u0010\u0010\u001a\u00020\u00072\b\b\u0002\u0010\u0011\u001a\u00020\u00072\b\b\u0002\u0010\u0012\u001a\u00020\u00072\b\b\u0002\u0010\u0013\u001a\u00020\u00072\n\b\u0002\u0010\u0014\u001a\u0004\u0018\u00010\u0000HÆ\u0001J\u0013\u00104\u001a\u00020\u00072\b\u00105\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u00106\u001a\u000207HÖ\u0001J\t\u00108\u001a\u000209HÖ\u0001R\u0011\u0010\u0010\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0016R\u0011\u0010\u0012\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0016R\u0011\u0010\u0011\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0016R\u0013\u0010\f\u001a\u0004\u0018\u00010\r¢\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0018R\u0011\u0010\u0006\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u0016R\u0011\u0010\u0013\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u0016R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u001b\u0010\u001cR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u001d\u0010\u001eR\u0019\u0010\b\u001a\n\u0012\u0004\u0012\u00020\n\u0018\u00010\t¢\u0006\b\n\u0000\u001a\u0004\b\u001f\u0010 R\u0013\u0010\u000b\u001a\u0004\u0018\u00010\n¢\u0006\b\n\u0000\u001a\u0004\b!\u0010\"R\u0013\u0010\u000e\u001a\u0004\u0018\u00010\u000f¢\u0006\b\n\u0000\u001a\u0004\b#\u0010$R\u0013\u0010\u0014\u001a\u0004\u0018\u00010\u0000¢\u0006\b\n\u0000\u001a\u0004\b%\u0010&¨\u0006:"}, d2 = {"Lorg/jetbrains/kotlin/backend/jvm/SpecialBridge;", "", "overridden", "Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;", "signature", "Lorg/jetbrains/org/objectweb/asm/commons/Method;", "needsGenericSignature", "", "substitutedParameterTypes", "", "Lorg/jetbrains/kotlin/ir/types/IrType;", "substitutedReturnType", "methodInfo", "Lorg/jetbrains/kotlin/backend/common/lower/SpecialMethodWithDefaultInfo;", "superQualifierSymbol", "Lorg/jetbrains/kotlin/ir/symbols/IrClassSymbol;", "isFinal", "isSynthetic", "isOverriding", "needsUnsubstitutedBridge", "unsubstitutedSpecialBridge", "(Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;Lorg/jetbrains/org/objectweb/asm/commons/Method;ZLjava/util/List;Lorg/jetbrains/kotlin/ir/types/IrType;Lorg/jetbrains/kotlin/backend/common/lower/SpecialMethodWithDefaultInfo;Lorg/jetbrains/kotlin/ir/symbols/IrClassSymbol;ZZZZLorg/jetbrains/kotlin/backend/jvm/SpecialBridge;)V", "()Z", "getMethodInfo", "()Lorg/jetbrains/kotlin/backend/common/lower/SpecialMethodWithDefaultInfo;", "getNeedsGenericSignature", "getNeedsUnsubstitutedBridge", "getOverridden", "()Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;", "getSignature", "()Lorg/jetbrains/org/objectweb/asm/commons/Method;", "getSubstitutedParameterTypes", "()Ljava/util/List;", "getSubstitutedReturnType", "()Lorg/jetbrains/kotlin/ir/types/IrType;", "getSuperQualifierSymbol", "()Lorg/jetbrains/kotlin/ir/symbols/IrClassSymbol;", "getUnsubstitutedSpecialBridge", "()Lorg/jetbrains/kotlin/backend/jvm/SpecialBridge;", "component1", "component10", "component11", "component12", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "component9", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "other", "hashCode", "", "toString", "", "backend.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final /* data */ class SpecialBridge {
    private final boolean isFinal;
    private final boolean isOverriding;
    private final boolean isSynthetic;
    private final SpecialMethodWithDefaultInfo methodInfo;
    private final boolean needsGenericSignature;
    private final boolean needsUnsubstitutedBridge;
    private final IrSimpleFunction overridden;
    private final Method signature;
    private final List<IrType> substitutedParameterTypes;
    private final IrType substitutedReturnType;
    private final IrClassSymbol superQualifierSymbol;
    private final SpecialBridge unsubstitutedSpecialBridge;

    /* JADX INFO: renamed from: component1, reason: from getter */
    public final IrSimpleFunction getOverridden() {
        return this.overridden;
    }

    /* JADX INFO: renamed from: component10, reason: from getter */
    public final boolean getIsOverriding() {
        return this.isOverriding;
    }

    /* JADX INFO: renamed from: component11, reason: from getter */
    public final boolean getNeedsUnsubstitutedBridge() {
        return this.needsUnsubstitutedBridge;
    }

    /* JADX INFO: renamed from: component12, reason: from getter */
    public final SpecialBridge getUnsubstitutedSpecialBridge() {
        return this.unsubstitutedSpecialBridge;
    }

    /* JADX INFO: renamed from: component2, reason: from getter */
    public final Method getSignature() {
        return this.signature;
    }

    /* JADX INFO: renamed from: component3, reason: from getter */
    public final boolean getNeedsGenericSignature() {
        return this.needsGenericSignature;
    }

    public final List<IrType> component4() {
        return this.substitutedParameterTypes;
    }

    /* JADX INFO: renamed from: component5, reason: from getter */
    public final IrType getSubstitutedReturnType() {
        return this.substitutedReturnType;
    }

    /* JADX INFO: renamed from: component6, reason: from getter */
    public final SpecialMethodWithDefaultInfo getMethodInfo() {
        return this.methodInfo;
    }

    /* JADX INFO: renamed from: component7, reason: from getter */
    public final IrClassSymbol getSuperQualifierSymbol() {
        return this.superQualifierSymbol;
    }

    /* JADX INFO: renamed from: component8, reason: from getter */
    public final boolean getIsFinal() {
        return this.isFinal;
    }

    /* JADX INFO: renamed from: component9, reason: from getter */
    public final boolean getIsSynthetic() {
        return this.isSynthetic;
    }

    public final SpecialBridge copy(IrSimpleFunction overridden, Method signature, boolean needsGenericSignature, List<? extends IrType> substitutedParameterTypes, IrType substitutedReturnType, SpecialMethodWithDefaultInfo methodInfo, IrClassSymbol superQualifierSymbol, boolean isFinal, boolean isSynthetic, boolean isOverriding, boolean needsUnsubstitutedBridge, SpecialBridge unsubstitutedSpecialBridge) {
        Intrinsics.checkNotNullParameter(overridden, "overridden");
        Intrinsics.checkNotNullParameter(signature, "signature");
        return new SpecialBridge(overridden, signature, needsGenericSignature, substitutedParameterTypes, substitutedReturnType, methodInfo, superQualifierSymbol, isFinal, isSynthetic, isOverriding, needsUnsubstitutedBridge, unsubstitutedSpecialBridge);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof SpecialBridge)) {
            return false;
        }
        SpecialBridge specialBridge = (SpecialBridge) other;
        return Intrinsics.areEqual(this.overridden, specialBridge.overridden) && Intrinsics.areEqual(this.signature, specialBridge.signature) && this.needsGenericSignature == specialBridge.needsGenericSignature && Intrinsics.areEqual(this.substitutedParameterTypes, specialBridge.substitutedParameterTypes) && Intrinsics.areEqual(this.substitutedReturnType, specialBridge.substitutedReturnType) && Intrinsics.areEqual(this.methodInfo, specialBridge.methodInfo) && Intrinsics.areEqual(this.superQualifierSymbol, specialBridge.superQualifierSymbol) && this.isFinal == specialBridge.isFinal && this.isSynthetic == specialBridge.isSynthetic && this.isOverriding == specialBridge.isOverriding && this.needsUnsubstitutedBridge == specialBridge.needsUnsubstitutedBridge && Intrinsics.areEqual(this.unsubstitutedSpecialBridge, specialBridge.unsubstitutedSpecialBridge);
    }

    public int hashCode() {
        int iHashCode = ((((this.overridden.hashCode() * 31) + this.signature.hashCode()) * 31) + Boolean.hashCode(this.needsGenericSignature)) * 31;
        List<IrType> list = this.substitutedParameterTypes;
        int iHashCode2 = (iHashCode + (list == null ? 0 : list.hashCode())) * 31;
        IrType irType = this.substitutedReturnType;
        int iHashCode3 = (iHashCode2 + (irType == null ? 0 : irType.hashCode())) * 31;
        SpecialMethodWithDefaultInfo specialMethodWithDefaultInfo = this.methodInfo;
        int iHashCode4 = (iHashCode3 + (specialMethodWithDefaultInfo == null ? 0 : specialMethodWithDefaultInfo.hashCode())) * 31;
        IrClassSymbol irClassSymbol = this.superQualifierSymbol;
        int iHashCode5 = (((((((((iHashCode4 + (irClassSymbol == null ? 0 : irClassSymbol.hashCode())) * 31) + Boolean.hashCode(this.isFinal)) * 31) + Boolean.hashCode(this.isSynthetic)) * 31) + Boolean.hashCode(this.isOverriding)) * 31) + Boolean.hashCode(this.needsUnsubstitutedBridge)) * 31;
        SpecialBridge specialBridge = this.unsubstitutedSpecialBridge;
        return iHashCode5 + (specialBridge != null ? specialBridge.hashCode() : 0);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("SpecialBridge(overridden=");
        sb.append(this.overridden).append(", signature=").append(this.signature).append(", needsGenericSignature=").append(this.needsGenericSignature).append(", substitutedParameterTypes=").append(this.substitutedParameterTypes).append(", substitutedReturnType=").append(this.substitutedReturnType).append(", methodInfo=").append(this.methodInfo).append(", superQualifierSymbol=").append(this.superQualifierSymbol).append(", isFinal=").append(this.isFinal).append(", isSynthetic=").append(this.isSynthetic).append(", isOverriding=").append(this.isOverriding).append(", needsUnsubstitutedBridge=").append(this.needsUnsubstitutedBridge).append(", unsubstitutedSpecialBridge=");
        sb.append(this.unsubstitutedSpecialBridge).append(')');
        return sb.toString();
    }

    /* JADX WARN: Multi-variable type inference failed */
    public SpecialBridge(IrSimpleFunction overridden, Method signature, boolean z, List<? extends IrType> list, IrType irType, SpecialMethodWithDefaultInfo specialMethodWithDefaultInfo, IrClassSymbol irClassSymbol, boolean z2, boolean z3, boolean z4, boolean z5, SpecialBridge specialBridge) {
        Intrinsics.checkNotNullParameter(overridden, "overridden");
        Intrinsics.checkNotNullParameter(signature, "signature");
        this.overridden = overridden;
        this.signature = signature;
        this.needsGenericSignature = z;
        this.substitutedParameterTypes = list;
        this.substitutedReturnType = irType;
        this.methodInfo = specialMethodWithDefaultInfo;
        this.superQualifierSymbol = irClassSymbol;
        this.isFinal = z2;
        this.isSynthetic = z3;
        this.isOverriding = z4;
        this.needsUnsubstitutedBridge = z5;
        this.unsubstitutedSpecialBridge = specialBridge;
    }

    public /* synthetic */ SpecialBridge(IrSimpleFunction irSimpleFunction, Method method, boolean z, List list, IrType irType, SpecialMethodWithDefaultInfo specialMethodWithDefaultInfo, IrClassSymbol irClassSymbol, boolean z2, boolean z3, boolean z4, boolean z5, SpecialBridge specialBridge, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(irSimpleFunction, method, (i & 4) != 0 ? false : z, (i & 8) != 0 ? null : list, (i & 16) != 0 ? null : irType, (i & 32) != 0 ? null : specialMethodWithDefaultInfo, (i & 64) != 0 ? null : irClassSymbol, (i & 128) != 0 ? true : z2, (i & 256) != 0 ? false : z3, (i & 512) != 0 ? true : z4, (i & 1024) != 0 ? false : z5, (i & 2048) != 0 ? null : specialBridge);
    }

    public final IrSimpleFunction getOverridden() {
        return this.overridden;
    }

    public final Method getSignature() {
        return this.signature;
    }

    public final boolean getNeedsGenericSignature() {
        return this.needsGenericSignature;
    }

    public final List<IrType> getSubstitutedParameterTypes() {
        return this.substitutedParameterTypes;
    }

    public final IrType getSubstitutedReturnType() {
        return this.substitutedReturnType;
    }

    public final SpecialMethodWithDefaultInfo getMethodInfo() {
        return this.methodInfo;
    }

    public final IrClassSymbol getSuperQualifierSymbol() {
        return this.superQualifierSymbol;
    }

    public final boolean isFinal() {
        return this.isFinal;
    }

    public final boolean isSynthetic() {
        return this.isSynthetic;
    }

    public final boolean isOverriding() {
        return this.isOverriding;
    }

    public final boolean getNeedsUnsubstitutedBridge() {
        return this.needsUnsubstitutedBridge;
    }

    public final SpecialBridge getUnsubstitutedSpecialBridge() {
        return this.unsubstitutedSpecialBridge;
    }
}
