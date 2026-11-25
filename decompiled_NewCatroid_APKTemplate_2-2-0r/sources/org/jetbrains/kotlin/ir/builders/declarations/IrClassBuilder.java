package org.jetbrains.kotlin.ir.builders.declarations;

import androidx.constraintlayout.core.motion.utils.TypedValues;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.ClassKind;
import org.jetbrains.kotlin.descriptors.Modality;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrDeclaration;
import org.jetbrains.kotlin.js.translate.context.Namer;

/* JADX INFO: compiled from: IrClassBuilder.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0010\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u000e\u0010 \u001a\u00020!2\u0006\u0010\"\u001a\u00020#R\u001a\u0010\u0003\u001a\u00020\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0003\u0010\u0005\"\u0004\b\u0006\u0010\u0007R\u001a\u0010\b\u001a\u00020\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\b\u0010\u0005\"\u0004\b\t\u0010\u0007R\u001a\u0010\n\u001a\u00020\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\n\u0010\u0005\"\u0004\b\u000b\u0010\u0007R\u001a\u0010\f\u001a\u00020\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\f\u0010\u0005\"\u0004\b\r\u0010\u0007R\u001a\u0010\u000e\u001a\u00020\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000e\u0010\u0005\"\u0004\b\u000f\u0010\u0007R\u001a\u0010\u0010\u001a\u00020\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0010\u0010\u0005\"\u0004\b\u0011\u0010\u0007R\u001a\u0010\u0012\u001a\u00020\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0012\u0010\u0005\"\u0004\b\u0013\u0010\u0007R\u001a\u0010\u0014\u001a\u00020\u0015X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0016\u0010\u0017\"\u0004\b\u0018\u0010\u0019R\u001a\u0010\u001a\u001a\u00020\u001bX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001c\u0010\u001d\"\u0004\b\u001e\u0010\u001f¨\u0006$"}, d2 = {"Lorg/jetbrains/kotlin/ir/builders/declarations/IrClassBuilder;", "Lorg/jetbrains/kotlin/ir/builders/declarations/IrDeclarationBuilder;", "()V", "isCompanion", "", "()Z", "setCompanion", "(Z)V", "isData", "setData", "isExpect", "setExpect", "isExternal", "setExternal", "isFun", "setFun", "isInner", "setInner", "isValue", "setValue", Namer.METADATA_CLASS_KIND, "Lorg/jetbrains/kotlin/descriptors/ClassKind;", "getKind", "()Lorg/jetbrains/kotlin/descriptors/ClassKind;", "setKind", "(Lorg/jetbrains/kotlin/descriptors/ClassKind;)V", "modality", "Lorg/jetbrains/kotlin/descriptors/Modality;", "getModality", "()Lorg/jetbrains/kotlin/descriptors/Modality;", "setModality", "(Lorg/jetbrains/kotlin/descriptors/Modality;)V", "updateFrom", "", TypedValues.TransitionType.S_FROM, "Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class IrClassBuilder extends IrDeclarationBuilder {
    private boolean isCompanion;
    private boolean isData;
    private boolean isExpect;
    private boolean isExternal;
    private boolean isFun;
    private boolean isInner;
    private boolean isValue;
    private ClassKind kind = ClassKind.CLASS;
    private Modality modality = Modality.FINAL;

    public final ClassKind getKind() {
        return this.kind;
    }

    public final void setKind(ClassKind classKind) {
        Intrinsics.checkNotNullParameter(classKind, "<set-?>");
        this.kind = classKind;
    }

    public final Modality getModality() {
        return this.modality;
    }

    public final void setModality(Modality modality) {
        Intrinsics.checkNotNullParameter(modality, "<set-?>");
        this.modality = modality;
    }

    /* JADX INFO: renamed from: isCompanion, reason: from getter */
    public final boolean getIsCompanion() {
        return this.isCompanion;
    }

    public final void setCompanion(boolean z) {
        this.isCompanion = z;
    }

    /* JADX INFO: renamed from: isInner, reason: from getter */
    public final boolean getIsInner() {
        return this.isInner;
    }

    public final void setInner(boolean z) {
        this.isInner = z;
    }

    /* JADX INFO: renamed from: isData, reason: from getter */
    public final boolean getIsData() {
        return this.isData;
    }

    public final void setData(boolean z) {
        this.isData = z;
    }

    /* JADX INFO: renamed from: isExternal, reason: from getter */
    public final boolean getIsExternal() {
        return this.isExternal;
    }

    public final void setExternal(boolean z) {
        this.isExternal = z;
    }

    /* JADX INFO: renamed from: isValue, reason: from getter */
    public final boolean getIsValue() {
        return this.isValue;
    }

    public final void setValue(boolean z) {
        this.isValue = z;
    }

    /* JADX INFO: renamed from: isExpect, reason: from getter */
    public final boolean getIsExpect() {
        return this.isExpect;
    }

    public final void setExpect(boolean z) {
        this.isExpect = z;
    }

    /* JADX INFO: renamed from: isFun, reason: from getter */
    public final boolean getIsFun() {
        return this.isFun;
    }

    public final void setFun(boolean z) {
        this.isFun = z;
    }

    public final void updateFrom(IrClass from) {
        Intrinsics.checkNotNullParameter(from, "from");
        super.updateFrom((IrDeclaration) from);
        this.kind = from.getKind();
        this.modality = from.getModality();
        this.isCompanion = from.isCompanion();
        this.isInner = from.isInner();
        this.isData = from.isData();
        this.isExternal = from.isExternal();
        this.isValue = from.isValue();
        this.isExpect = from.isExpect();
        this.isFun = from.isFun();
    }
}
