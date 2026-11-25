package org.jetbrains.kotlin.ir.builders.declarations;

import androidx.constraintlayout.core.motion.utils.TypedValues;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.Modality;
import org.jetbrains.kotlin.ir.declarations.IrConstructor;
import org.jetbrains.kotlin.ir.declarations.IrDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrFunction;
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.ir.types.impl.IrUninitializedType;
import org.jetbrains.kotlin.serialization.deserialization.descriptors.DeserializedContainerSource;

/* JADX INFO: compiled from: IrFunctionBuilder.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\u0014\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u000e\u00100\u001a\u0002012\u0006\u00102\u001a\u00020%R\u001c\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u001a\u0010\t\u001a\u00020\nX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\t\u0010\u000b\"\u0004\b\f\u0010\rR\u001a\u0010\u000e\u001a\u00020\nX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000e\u0010\u000b\"\u0004\b\u000f\u0010\rR\u001a\u0010\u0010\u001a\u00020\nX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0010\u0010\u000b\"\u0004\b\u0011\u0010\rR\u001a\u0010\u0012\u001a\u00020\nX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0012\u0010\u000b\"\u0004\b\u0013\u0010\rR\u001a\u0010\u0014\u001a\u00020\nX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0014\u0010\u000b\"\u0004\b\u0015\u0010\rR\u001a\u0010\u0016\u001a\u00020\nX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0016\u0010\u000b\"\u0004\b\u0017\u0010\rR\u001a\u0010\u0018\u001a\u00020\nX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0018\u0010\u000b\"\u0004\b\u0019\u0010\rR\u001a\u0010\u001a\u001a\u00020\nX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001a\u0010\u000b\"\u0004\b\u001b\u0010\rR\u001a\u0010\u001c\u001a\u00020\nX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001c\u0010\u000b\"\u0004\b\u001d\u0010\rR\u001a\u0010\u001e\u001a\u00020\u001fX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b \u0010!\"\u0004\b\"\u0010#R\u001c\u0010$\u001a\u0004\u0018\u00010%X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b&\u0010'\"\u0004\b(\u0010)R\u001a\u0010*\u001a\u00020+X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b,\u0010-\"\u0004\b.\u0010/¨\u00063"}, d2 = {"Lorg/jetbrains/kotlin/ir/builders/declarations/IrFunctionBuilder;", "Lorg/jetbrains/kotlin/ir/builders/declarations/IrDeclarationBuilder;", "()V", "containerSource", "Lorg/jetbrains/kotlin/serialization/deserialization/descriptors/DeserializedContainerSource;", "getContainerSource", "()Lorg/jetbrains/kotlin/serialization/deserialization/descriptors/DeserializedContainerSource;", "setContainerSource", "(Lorg/jetbrains/kotlin/serialization/deserialization/descriptors/DeserializedContainerSource;)V", "isExpect", "", "()Z", "setExpect", "(Z)V", "isExternal", "setExternal", "isFakeOverride", "setFakeOverride", "isInfix", "setInfix", "isInline", "setInline", "isOperator", "setOperator", "isPrimary", "setPrimary", "isSuspend", "setSuspend", "isTailrec", "setTailrec", "modality", "Lorg/jetbrains/kotlin/descriptors/Modality;", "getModality", "()Lorg/jetbrains/kotlin/descriptors/Modality;", "setModality", "(Lorg/jetbrains/kotlin/descriptors/Modality;)V", "originalDeclaration", "Lorg/jetbrains/kotlin/ir/declarations/IrFunction;", "getOriginalDeclaration", "()Lorg/jetbrains/kotlin/ir/declarations/IrFunction;", "setOriginalDeclaration", "(Lorg/jetbrains/kotlin/ir/declarations/IrFunction;)V", "returnType", "Lorg/jetbrains/kotlin/ir/types/IrType;", "getReturnType", "()Lorg/jetbrains/kotlin/ir/types/IrType;", "setReturnType", "(Lorg/jetbrains/kotlin/ir/types/IrType;)V", "updateFrom", "", TypedValues.TransitionType.S_FROM, "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class IrFunctionBuilder extends IrDeclarationBuilder {
    private DeserializedContainerSource containerSource;
    private boolean isExpect;
    private boolean isExternal;
    private boolean isFakeOverride;
    private boolean isInfix;
    private boolean isInline;
    private boolean isOperator;
    private boolean isPrimary;
    private boolean isSuspend;
    private boolean isTailrec;
    private IrFunction originalDeclaration;
    private IrType returnType = IrUninitializedType.INSTANCE;
    private Modality modality = Modality.FINAL;

    /* JADX INFO: renamed from: isInline, reason: from getter */
    public final boolean getIsInline() {
        return this.isInline;
    }

    public final void setInline(boolean z) {
        this.isInline = z;
    }

    /* JADX INFO: renamed from: isExternal, reason: from getter */
    public final boolean getIsExternal() {
        return this.isExternal;
    }

    public final void setExternal(boolean z) {
        this.isExternal = z;
    }

    public final IrType getReturnType() {
        return this.returnType;
    }

    public final void setReturnType(IrType irType) {
        Intrinsics.checkNotNullParameter(irType, "<set-?>");
        this.returnType = irType;
    }

    public final Modality getModality() {
        return this.modality;
    }

    public final void setModality(Modality modality) {
        Intrinsics.checkNotNullParameter(modality, "<set-?>");
        this.modality = modality;
    }

    /* JADX INFO: renamed from: isTailrec, reason: from getter */
    public final boolean getIsTailrec() {
        return this.isTailrec;
    }

    public final void setTailrec(boolean z) {
        this.isTailrec = z;
    }

    /* JADX INFO: renamed from: isSuspend, reason: from getter */
    public final boolean getIsSuspend() {
        return this.isSuspend;
    }

    public final void setSuspend(boolean z) {
        this.isSuspend = z;
    }

    /* JADX INFO: renamed from: isExpect, reason: from getter */
    public final boolean getIsExpect() {
        return this.isExpect;
    }

    public final void setExpect(boolean z) {
        this.isExpect = z;
    }

    /* JADX INFO: renamed from: isOperator, reason: from getter */
    public final boolean getIsOperator() {
        return this.isOperator;
    }

    public final void setOperator(boolean z) {
        this.isOperator = z;
    }

    /* JADX INFO: renamed from: isInfix, reason: from getter */
    public final boolean getIsInfix() {
        return this.isInfix;
    }

    public final void setInfix(boolean z) {
        this.isInfix = z;
    }

    /* JADX INFO: renamed from: isPrimary, reason: from getter */
    public final boolean getIsPrimary() {
        return this.isPrimary;
    }

    public final void setPrimary(boolean z) {
        this.isPrimary = z;
    }

    /* JADX INFO: renamed from: isFakeOverride, reason: from getter */
    public final boolean getIsFakeOverride() {
        return this.isFakeOverride;
    }

    public final void setFakeOverride(boolean z) {
        this.isFakeOverride = z;
    }

    public final IrFunction getOriginalDeclaration() {
        return this.originalDeclaration;
    }

    public final void setOriginalDeclaration(IrFunction irFunction) {
        this.originalDeclaration = irFunction;
    }

    public final DeserializedContainerSource getContainerSource() {
        return this.containerSource;
    }

    public final void setContainerSource(DeserializedContainerSource deserializedContainerSource) {
        this.containerSource = deserializedContainerSource;
    }

    public final void updateFrom(IrFunction from) {
        Intrinsics.checkNotNullParameter(from, "from");
        super.updateFrom((IrDeclaration) from);
        this.containerSource = from.getContainerSource();
        this.isInline = from.isInline();
        this.isExternal = from.isExternal();
        this.isExpect = from.isExpect();
        if (from instanceof IrSimpleFunction) {
            IrSimpleFunction irSimpleFunction = (IrSimpleFunction) from;
            this.modality = irSimpleFunction.getModality();
            this.isTailrec = irSimpleFunction.isTailrec();
            this.isSuspend = irSimpleFunction.isSuspend();
            this.isOperator = irSimpleFunction.isOperator();
            this.isInfix = irSimpleFunction.isInfix();
            this.isFakeOverride = irSimpleFunction.isFakeOverride();
        } else {
            this.modality = Modality.FINAL;
            this.isTailrec = false;
            this.isSuspend = false;
            this.isOperator = false;
            this.isInfix = false;
        }
        if (from instanceof IrConstructor) {
            this.isPrimary = ((IrConstructor) from).isPrimary();
        }
    }
}
