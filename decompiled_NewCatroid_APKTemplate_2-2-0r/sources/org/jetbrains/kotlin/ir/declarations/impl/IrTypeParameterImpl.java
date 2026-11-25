package org.jetbrains.kotlin.ir.declarations.impl;

import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.TypeParameterDescriptor;
import org.jetbrains.kotlin.ir.ObsoleteDescriptorBasedAPI;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationOrigin;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationParent;
import org.jetbrains.kotlin.ir.declarations.IrFactory;
import org.jetbrains.kotlin.ir.declarations.IrTypeParameter;
import org.jetbrains.kotlin.ir.expressions.IrConstructorCall;
import org.jetbrains.kotlin.ir.symbols.IrTypeParameterSymbol;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.types.Variance;
import org.mozilla.javascript.NativeSymbol;

/* JADX INFO: compiled from: IrTypeParameterImpl.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000^\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0017\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\t\u0018\u00002\u00020\u0001BO\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\b\u0012\u0006\u0010\t\u001a\u00020\n\u0012\u0006\u0010\u000b\u001a\u00020\u0003\u0012\u0006\u0010\f\u001a\u00020\r\u0012\u0006\u0010\u000e\u001a\u00020\u000f\u0012\b\b\u0002\u0010\u0010\u001a\u00020\u0011¢\u0006\u0002\u0010\u0012R \u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00150\u0014X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0016\u0010\u0017\"\u0004\b\u0018\u0010\u0019R\u001a\u0010\u001a\u001a\u00020\u001b8VX\u0097\u0004¢\u0006\f\u0012\u0004\b\u001c\u0010\u001d\u001a\u0004\b\u001e\u0010\u001fR\u0014\u0010\u0004\u001a\u00020\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b \u0010!R\u0014\u0010\u0010\u001a\u00020\u0011X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\"\u0010#R\u001a\u0010\u000b\u001a\u00020\u0003X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b$\u0010!\"\u0004\b%\u0010&R\u001a\u0010\f\u001a\u00020\rX\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\f\u0010'\"\u0004\b(\u0010)R\u001a\u0010\t\u001a\u00020\nX\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b*\u0010+\"\u0004\b,\u0010-R\u001a\u0010\u0005\u001a\u00020\u0006X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b.\u0010/\"\u0004\b0\u00101R\u001a\u00102\u001a\u000203X\u0096.¢\u0006\u000e\n\u0000\u001a\u0004\b4\u00105\"\u0004\b6\u00107R\u0014\u0010\u0002\u001a\u00020\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b8\u0010!R \u00109\u001a\b\u0012\u0004\u0012\u00020:0\u0014X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b;\u0010\u0017\"\u0004\b<\u0010\u0019R\u0014\u0010\u0007\u001a\u00020\bX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b=\u0010>R\u001a\u0010\u000e\u001a\u00020\u000fX\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b?\u0010@\"\u0004\bA\u0010B¨\u0006C"}, d2 = {"Lorg/jetbrains/kotlin/ir/declarations/impl/IrTypeParameterImpl;", "Lorg/jetbrains/kotlin/ir/declarations/IrTypeParameter;", "startOffset", "", "endOffset", "origin", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationOrigin;", NativeSymbol.TYPE_NAME, "Lorg/jetbrains/kotlin/ir/symbols/IrTypeParameterSymbol;", "name", "Lorg/jetbrains/kotlin/name/Name;", "index", "isReified", "", "variance", "Lorg/jetbrains/kotlin/types/Variance;", "factory", "Lorg/jetbrains/kotlin/ir/declarations/IrFactory;", "(IILorg/jetbrains/kotlin/ir/declarations/IrDeclarationOrigin;Lorg/jetbrains/kotlin/ir/symbols/IrTypeParameterSymbol;Lorg/jetbrains/kotlin/name/Name;IZLorg/jetbrains/kotlin/types/Variance;Lorg/jetbrains/kotlin/ir/declarations/IrFactory;)V", "annotations", "", "Lorg/jetbrains/kotlin/ir/expressions/IrConstructorCall;", "getAnnotations", "()Ljava/util/List;", "setAnnotations", "(Ljava/util/List;)V", "descriptor", "Lorg/jetbrains/kotlin/descriptors/TypeParameterDescriptor;", "getDescriptor$annotations", "()V", "getDescriptor", "()Lorg/jetbrains/kotlin/descriptors/TypeParameterDescriptor;", "getEndOffset", "()I", "getFactory", "()Lorg/jetbrains/kotlin/ir/declarations/IrFactory;", "getIndex", "setIndex", "(I)V", "()Z", "setReified", "(Z)V", "getName", "()Lorg/jetbrains/kotlin/name/Name;", "setName", "(Lorg/jetbrains/kotlin/name/Name;)V", "getOrigin", "()Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationOrigin;", "setOrigin", "(Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationOrigin;)V", "parent", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationParent;", "getParent", "()Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationParent;", "setParent", "(Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationParent;)V", "getStartOffset", "superTypes", "Lorg/jetbrains/kotlin/ir/types/IrType;", "getSuperTypes", "setSuperTypes", "getSymbol", "()Lorg/jetbrains/kotlin/ir/symbols/IrTypeParameterSymbol;", "getVariance", "()Lorg/jetbrains/kotlin/types/Variance;", "setVariance", "(Lorg/jetbrains/kotlin/types/Variance;)V", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class IrTypeParameterImpl extends IrTypeParameter {
    private List<? extends IrConstructorCall> annotations;
    private final int endOffset;
    private final IrFactory factory;
    private int index;
    private boolean isReified;
    private Name name;
    private IrDeclarationOrigin origin;
    public IrDeclarationParent parent;
    private final int startOffset;
    private List<? extends IrType> superTypes;
    private final IrTypeParameterSymbol symbol;
    private Variance variance;

    @ObsoleteDescriptorBasedAPI
    public static /* synthetic */ void getDescriptor$annotations() {
    }

    @Override // org.jetbrains.kotlin.ir.IrElement
    public int getStartOffset() {
        return this.startOffset;
    }

    @Override // org.jetbrains.kotlin.ir.IrElement
    public int getEndOffset() {
        return this.endOffset;
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

    @Override // org.jetbrains.kotlin.ir.declarations.IrSymbolOwner, org.jetbrains.kotlin.ir.declarations.IrReturnTarget, org.jetbrains.kotlin.ir.declarations.IrOverridableDeclaration
    public IrTypeParameterSymbol getSymbol() {
        return this.symbol;
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrDeclarationWithName
    public Name getName() {
        return this.name;
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrDeclarationWithName
    public void setName(Name name2) {
        Intrinsics.checkNotNullParameter(name2, "<set-?>");
        this.name = name2;
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrTypeParameter
    public int getIndex() {
        return this.index;
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrTypeParameter
    public void setIndex(int i) {
        this.index = i;
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrTypeParameter
    /* JADX INFO: renamed from: isReified, reason: from getter */
    public boolean getIsReified() {
        return this.isReified;
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrTypeParameter
    public void setReified(boolean z) {
        this.isReified = z;
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrTypeParameter
    public Variance getVariance() {
        return this.variance;
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrTypeParameter
    public void setVariance(Variance variance) {
        Intrinsics.checkNotNullParameter(variance, "<set-?>");
        this.variance = variance;
    }

    public /* synthetic */ IrTypeParameterImpl(int i, int i2, IrDeclarationOrigin irDeclarationOrigin, IrTypeParameterSymbol irTypeParameterSymbol, Name name2, int i3, boolean z, Variance variance, IrFactory irFactory, int i4, DefaultConstructorMarker defaultConstructorMarker) {
        this(i, i2, irDeclarationOrigin, irTypeParameterSymbol, name2, i3, z, variance, (i4 & 256) != 0 ? IrFactoryImpl.INSTANCE : irFactory);
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrDeclaration, org.jetbrains.kotlin.ir.declarations.lazy.IrLazyDeclarationBase
    public IrFactory getFactory() {
        return this.factory;
    }

    public IrTypeParameterImpl(int i, int i2, IrDeclarationOrigin origin, IrTypeParameterSymbol symbol, Name name2, int i3, boolean z, Variance variance, IrFactory factory) {
        Intrinsics.checkNotNullParameter(origin, "origin");
        Intrinsics.checkNotNullParameter(symbol, "symbol");
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(variance, "variance");
        Intrinsics.checkNotNullParameter(factory, "factory");
        this.startOffset = i;
        this.endOffset = i2;
        this.origin = origin;
        this.symbol = symbol;
        this.name = name2;
        this.index = i3;
        this.isReified = z;
        this.variance = variance;
        this.factory = factory;
        getSymbol().bind(this);
        this.annotations = CollectionsKt.emptyList();
        this.superTypes = CollectionsKt.emptyList();
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

    @Override // org.jetbrains.kotlin.ir.declarations.IrDeclaration
    public TypeParameterDescriptor getDescriptor() {
        return getSymbol().getDescriptor();
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrTypeParameter
    public List<IrType> getSuperTypes() {
        return this.superTypes;
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrTypeParameter
    public void setSuperTypes(List<? extends IrType> list) {
        Intrinsics.checkNotNullParameter(list, "<set-?>");
        this.superTypes = list;
    }
}
