package org.jetbrains.kotlin.ir.builders.declarations;

import androidx.constraintlayout.core.motion.utils.TypedValues;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.Modality;
import org.jetbrains.kotlin.ir.declarations.IrDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrProperty;
import org.jetbrains.kotlin.serialization.deserialization.descriptors.DeserializedContainerSource;

/* JADX INFO: compiled from: IrPropertyBuilder.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\u0010\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u000e\u0010&\u001a\u00020'2\u0006\u0010(\u001a\u00020!R\u001c\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u001a\u0010\t\u001a\u00020\nX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\t\u0010\u000b\"\u0004\b\f\u0010\rR\u001a\u0010\u000e\u001a\u00020\nX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000e\u0010\u000b\"\u0004\b\u000f\u0010\rR\u001a\u0010\u0010\u001a\u00020\nX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0010\u0010\u000b\"\u0004\b\u0011\u0010\rR\u001a\u0010\u0012\u001a\u00020\nX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0012\u0010\u000b\"\u0004\b\u0013\u0010\rR\u001a\u0010\u0014\u001a\u00020\nX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0014\u0010\u000b\"\u0004\b\u0015\u0010\rR\u001a\u0010\u0016\u001a\u00020\nX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0016\u0010\u000b\"\u0004\b\u0017\u0010\rR\u001a\u0010\u0018\u001a\u00020\nX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0018\u0010\u000b\"\u0004\b\u0019\u0010\rR\u001a\u0010\u001a\u001a\u00020\u001bX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001c\u0010\u001d\"\u0004\b\u001e\u0010\u001fR\u001c\u0010 \u001a\u0004\u0018\u00010!X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\"\u0010#\"\u0004\b$\u0010%¨\u0006)"}, d2 = {"Lorg/jetbrains/kotlin/ir/builders/declarations/IrPropertyBuilder;", "Lorg/jetbrains/kotlin/ir/builders/declarations/IrDeclarationBuilder;", "()V", "containerSource", "Lorg/jetbrains/kotlin/serialization/deserialization/descriptors/DeserializedContainerSource;", "getContainerSource", "()Lorg/jetbrains/kotlin/serialization/deserialization/descriptors/DeserializedContainerSource;", "setContainerSource", "(Lorg/jetbrains/kotlin/serialization/deserialization/descriptors/DeserializedContainerSource;)V", "isConst", "", "()Z", "setConst", "(Z)V", "isDelegated", "setDelegated", "isExpect", "setExpect", "isExternal", "setExternal", "isFakeOverride", "setFakeOverride", "isLateinit", "setLateinit", "isVar", "setVar", "modality", "Lorg/jetbrains/kotlin/descriptors/Modality;", "getModality", "()Lorg/jetbrains/kotlin/descriptors/Modality;", "setModality", "(Lorg/jetbrains/kotlin/descriptors/Modality;)V", "originalDeclaration", "Lorg/jetbrains/kotlin/ir/declarations/IrProperty;", "getOriginalDeclaration", "()Lorg/jetbrains/kotlin/ir/declarations/IrProperty;", "setOriginalDeclaration", "(Lorg/jetbrains/kotlin/ir/declarations/IrProperty;)V", "updateFrom", "", TypedValues.TransitionType.S_FROM, "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class IrPropertyBuilder extends IrDeclarationBuilder {
    private DeserializedContainerSource containerSource;
    private boolean isConst;
    private boolean isDelegated;
    private boolean isExpect;
    private boolean isExternal;
    private boolean isFakeOverride;
    private boolean isLateinit;
    private boolean isVar;
    private Modality modality = Modality.FINAL;
    private IrProperty originalDeclaration;

    public final Modality getModality() {
        return this.modality;
    }

    public final void setModality(Modality modality) {
        Intrinsics.checkNotNullParameter(modality, "<set-?>");
        this.modality = modality;
    }

    /* JADX INFO: renamed from: isVar, reason: from getter */
    public final boolean getIsVar() {
        return this.isVar;
    }

    public final void setVar(boolean z) {
        this.isVar = z;
    }

    /* JADX INFO: renamed from: isConst, reason: from getter */
    public final boolean getIsConst() {
        return this.isConst;
    }

    public final void setConst(boolean z) {
        this.isConst = z;
    }

    /* JADX INFO: renamed from: isLateinit, reason: from getter */
    public final boolean getIsLateinit() {
        return this.isLateinit;
    }

    public final void setLateinit(boolean z) {
        this.isLateinit = z;
    }

    /* JADX INFO: renamed from: isDelegated, reason: from getter */
    public final boolean getIsDelegated() {
        return this.isDelegated;
    }

    public final void setDelegated(boolean z) {
        this.isDelegated = z;
    }

    /* JADX INFO: renamed from: isExternal, reason: from getter */
    public final boolean getIsExternal() {
        return this.isExternal;
    }

    public final void setExternal(boolean z) {
        this.isExternal = z;
    }

    /* JADX INFO: renamed from: isExpect, reason: from getter */
    public final boolean getIsExpect() {
        return this.isExpect;
    }

    public final void setExpect(boolean z) {
        this.isExpect = z;
    }

    /* JADX INFO: renamed from: isFakeOverride, reason: from getter */
    public final boolean getIsFakeOverride() {
        return this.isFakeOverride;
    }

    public final void setFakeOverride(boolean z) {
        this.isFakeOverride = z;
    }

    public final IrProperty getOriginalDeclaration() {
        return this.originalDeclaration;
    }

    public final void setOriginalDeclaration(IrProperty irProperty) {
        this.originalDeclaration = irProperty;
    }

    public final DeserializedContainerSource getContainerSource() {
        return this.containerSource;
    }

    public final void setContainerSource(DeserializedContainerSource deserializedContainerSource) {
        this.containerSource = deserializedContainerSource;
    }

    public final void updateFrom(IrProperty from) {
        Intrinsics.checkNotNullParameter(from, "from");
        super.updateFrom((IrDeclaration) from);
        this.isVar = from.isVar();
        this.isConst = from.isConst();
        this.isLateinit = from.isLateinit();
        this.isDelegated = from.isDelegated();
        this.isExternal = from.isExternal();
        this.isExpect = from.isExpect();
        this.isFakeOverride = from.isFakeOverride();
        this.originalDeclaration = from;
        this.containerSource = from.getContainerSource();
    }
}
