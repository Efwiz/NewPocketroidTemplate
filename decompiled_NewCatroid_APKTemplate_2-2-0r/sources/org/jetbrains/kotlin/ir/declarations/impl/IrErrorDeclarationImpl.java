package org.jetbrains.kotlin.ir.declarations.impl;

import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationOrigin;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationParent;
import org.jetbrains.kotlin.ir.declarations.IrErrorDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrFactory;
import org.jetbrains.kotlin.ir.descriptors.IrBasedDescriptorsKt;
import org.jetbrains.kotlin.ir.expressions.IrConstructorCall;

/* JADX INFO: compiled from: IrErrorDeclarationImpl.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\f\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0006\u0018\u00002\u00020\u0001B)\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006\u0012\b\b\u0002\u0010\u0007\u001a\u00020\b¢\u0006\u0002\u0010\tR\u0010\u0010\u0005\u001a\u0004\u0018\u00010\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R \u0010\n\u001a\b\u0012\u0004\u0012\u00020\f0\u000bX\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\r\u0010\u000e\"\u0004\b\u000f\u0010\u0010R\u0014\u0010\u0011\u001a\u00020\u00068VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0012\u0010\u0013R\u0014\u0010\u0004\u001a\u00020\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0015R\u0014\u0010\u0007\u001a\u00020\bX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0017R\u001a\u0010\u0018\u001a\u00020\u0019X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001a\u0010\u001b\"\u0004\b\u001c\u0010\u001dR\u001a\u0010\u001e\u001a\u00020\u001fX\u0096.¢\u0006\u000e\n\u0000\u001a\u0004\b \u0010!\"\u0004\b\"\u0010#R\u0014\u0010\u0002\u001a\u00020\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b$\u0010\u0015¨\u0006%"}, d2 = {"Lorg/jetbrains/kotlin/ir/declarations/impl/IrErrorDeclarationImpl;", "Lorg/jetbrains/kotlin/ir/declarations/IrErrorDeclaration;", "startOffset", "", "endOffset", "_descriptor", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "factory", "Lorg/jetbrains/kotlin/ir/declarations/IrFactory;", "(IILorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;Lorg/jetbrains/kotlin/ir/declarations/IrFactory;)V", "annotations", "", "Lorg/jetbrains/kotlin/ir/expressions/IrConstructorCall;", "getAnnotations", "()Ljava/util/List;", "setAnnotations", "(Ljava/util/List;)V", "descriptor", "getDescriptor", "()Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "getEndOffset", "()I", "getFactory", "()Lorg/jetbrains/kotlin/ir/declarations/IrFactory;", "origin", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationOrigin;", "getOrigin", "()Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationOrigin;", "setOrigin", "(Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationOrigin;)V", "parent", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationParent;", "getParent", "()Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationParent;", "setParent", "(Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationParent;)V", "getStartOffset", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class IrErrorDeclarationImpl extends IrErrorDeclaration {
    private final DeclarationDescriptor _descriptor;
    private List<? extends IrConstructorCall> annotations;
    private final int endOffset;
    private final IrFactory factory;
    private IrDeclarationOrigin origin;
    public IrDeclarationParent parent;
    private final int startOffset;

    @Override // org.jetbrains.kotlin.ir.IrElement
    public int getStartOffset() {
        return this.startOffset;
    }

    @Override // org.jetbrains.kotlin.ir.IrElement
    public int getEndOffset() {
        return this.endOffset;
    }

    public /* synthetic */ IrErrorDeclarationImpl(int i, int i2, DeclarationDescriptor declarationDescriptor, IrFactoryImpl irFactoryImpl, int i3, DefaultConstructorMarker defaultConstructorMarker) {
        this(i, i2, declarationDescriptor, (i3 & 8) != 0 ? IrFactoryImpl.INSTANCE : irFactoryImpl);
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrDeclaration, org.jetbrains.kotlin.ir.declarations.lazy.IrLazyDeclarationBase
    public IrFactory getFactory() {
        return this.factory;
    }

    public IrErrorDeclarationImpl(int i, int i2, DeclarationDescriptor declarationDescriptor, IrFactory factory) {
        Intrinsics.checkNotNullParameter(factory, "factory");
        this.startOffset = i;
        this.endOffset = i2;
        this._descriptor = declarationDescriptor;
        this.factory = factory;
        this.origin = IrDeclarationOrigin.DEFINED.INSTANCE;
        this.annotations = CollectionsKt.emptyList();
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrDeclaration
    public DeclarationDescriptor getDescriptor() {
        DeclarationDescriptor declarationDescriptor = this._descriptor;
        return declarationDescriptor == null ? IrBasedDescriptorsKt.toIrBasedDescriptor((IrErrorDeclaration) this) : declarationDescriptor;
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrDeclaration
    public IrDeclarationOrigin getOrigin() {
        return this.origin;
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrDeclaration
    public void setOrigin(IrDeclarationOrigin irDeclarationOrigin) {
        Intrinsics.checkNotNullParameter(irDeclarationOrigin, "<set-?>");
        this.origin = irDeclarationOrigin;
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrDeclaration
    public IrDeclarationParent getParent() {
        IrDeclarationParent irDeclarationParent = this.parent;
        if (irDeclarationParent != null) {
            return irDeclarationParent;
        }
        Intrinsics.throwUninitializedPropertyAccessException("parent");
        return null;
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrDeclaration
    public void setParent(IrDeclarationParent irDeclarationParent) {
        Intrinsics.checkNotNullParameter(irDeclarationParent, "<set-?>");
        this.parent = irDeclarationParent;
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrMutableAnnotationContainer, org.jetbrains.kotlin.ir.declarations.IrAnnotationContainer
    public List<IrConstructorCall> getAnnotations() {
        return this.annotations;
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrMutableAnnotationContainer
    public void setAnnotations(List<? extends IrConstructorCall> list) {
        Intrinsics.checkNotNullParameter(list, "<set-?>");
        this.annotations = list;
    }
}
