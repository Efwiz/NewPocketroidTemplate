package org.jetbrains.kotlin.ir.builders.declarations;

import androidx.constraintlayout.core.motion.utils.TypedValues;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.ir.declarations.IrDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrValueParameter;
import org.jetbrains.kotlin.ir.types.IrType;

/* JADX INFO: compiled from: IrValueParameterBuilder.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u000e\u0010\u001d\u001a\u00020\u001e2\u0006\u0010\u001f\u001a\u00020 R\u001a\u0010\u0003\u001a\u00020\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u001a\u0010\t\u001a\u00020\nX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\t\u0010\u000b\"\u0004\b\f\u0010\rR\u001a\u0010\u000e\u001a\u00020\nX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000e\u0010\u000b\"\u0004\b\u000f\u0010\rR\u001a\u0010\u0010\u001a\u00020\nX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0010\u0010\u000b\"\u0004\b\u0011\u0010\rR\u001a\u0010\u0012\u001a\u00020\nX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0012\u0010\u000b\"\u0004\b\u0013\u0010\rR\u001a\u0010\u0014\u001a\u00020\u0015X\u0086.¢\u0006\u000e\n\u0000\u001a\u0004\b\u0016\u0010\u0017\"\u0004\b\u0018\u0010\u0019R\u001c\u0010\u001a\u001a\u0004\u0018\u00010\u0015X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001b\u0010\u0017\"\u0004\b\u001c\u0010\u0019¨\u0006!"}, d2 = {"Lorg/jetbrains/kotlin/ir/builders/declarations/IrValueParameterBuilder;", "Lorg/jetbrains/kotlin/ir/builders/declarations/IrDeclarationBuilder;", "()V", "index", "", "getIndex", "()I", "setIndex", "(I)V", "isAssignable", "", "()Z", "setAssignable", "(Z)V", "isCrossInline", "setCrossInline", "isHidden", "setHidden", "isNoinline", "setNoinline", ModuleXmlParser.TYPE, "Lorg/jetbrains/kotlin/ir/types/IrType;", "getType", "()Lorg/jetbrains/kotlin/ir/types/IrType;", "setType", "(Lorg/jetbrains/kotlin/ir/types/IrType;)V", "varargElementType", "getVarargElementType", "setVarargElementType", "updateFrom", "", TypedValues.TransitionType.S_FROM, "Lorg/jetbrains/kotlin/ir/declarations/IrValueParameter;", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class IrValueParameterBuilder extends IrDeclarationBuilder {
    private int index = -1;
    private boolean isAssignable;
    private boolean isCrossInline;
    private boolean isHidden;
    private boolean isNoinline;
    public IrType type;
    private IrType varargElementType;

    public final IrType getType() {
        IrType irType = this.type;
        if (irType != null) {
            return irType;
        }
        Intrinsics.throwUninitializedPropertyAccessException(ModuleXmlParser.TYPE);
        return null;
    }

    public final void setType(IrType irType) {
        Intrinsics.checkNotNullParameter(irType, "<set-?>");
        this.type = irType;
    }

    public final int getIndex() {
        return this.index;
    }

    public final void setIndex(int i) {
        this.index = i;
    }

    public final IrType getVarargElementType() {
        return this.varargElementType;
    }

    public final void setVarargElementType(IrType irType) {
        this.varargElementType = irType;
    }

    /* JADX INFO: renamed from: isCrossInline, reason: from getter */
    public final boolean getIsCrossInline() {
        return this.isCrossInline;
    }

    public final void setCrossInline(boolean z) {
        this.isCrossInline = z;
    }

    /* JADX INFO: renamed from: isNoinline, reason: from getter */
    public final boolean getIsNoinline() {
        return this.isNoinline;
    }

    public final void setNoinline(boolean z) {
        this.isNoinline = z;
    }

    /* JADX INFO: renamed from: isHidden, reason: from getter */
    public final boolean getIsHidden() {
        return this.isHidden;
    }

    public final void setHidden(boolean z) {
        this.isHidden = z;
    }

    /* JADX INFO: renamed from: isAssignable, reason: from getter */
    public final boolean getIsAssignable() {
        return this.isAssignable;
    }

    public final void setAssignable(boolean z) {
        this.isAssignable = z;
    }

    public final void updateFrom(IrValueParameter from) {
        Intrinsics.checkNotNullParameter(from, "from");
        super.updateFrom((IrDeclaration) from);
        setType(from.getType());
        this.index = from.getIndex();
        this.varargElementType = from.getVarargElementType();
        this.isCrossInline = from.isCrossinline();
        this.isNoinline = from.isNoinline();
        this.isHidden = from.isHidden();
        this.isAssignable = from.isAssignable();
    }
}
