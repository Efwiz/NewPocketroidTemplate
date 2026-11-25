package org.jetbrains.kotlin.ir.declarations.impl;

import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.DescriptorVisibility;
import org.jetbrains.kotlin.descriptors.Modality;
import org.jetbrains.kotlin.descriptors.PropertyDescriptor;
import org.jetbrains.kotlin.ir.ObsoleteDescriptorBasedAPI;
import org.jetbrains.kotlin.ir.declarations.IrAttributeContainer;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationOrigin;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationParent;
import org.jetbrains.kotlin.ir.declarations.IrFactory;
import org.jetbrains.kotlin.ir.declarations.IrField;
import org.jetbrains.kotlin.ir.declarations.IrProperty;
import org.jetbrains.kotlin.ir.declarations.IrPropertyWithLateBinding;
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction;
import org.jetbrains.kotlin.ir.declarations.MetadataSource;
import org.jetbrains.kotlin.ir.descriptors.IrBasedDescriptorsKt;
import org.jetbrains.kotlin.ir.expressions.IrConstructorCall;
import org.jetbrains.kotlin.ir.symbols.IrPropertySymbol;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.serialization.deserialization.descriptors.DeserializedContainerSource;
import org.mozilla.javascript.NativeSymbol;

/* JADX INFO: compiled from: IrPropertyWithLateBindingImpl.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\u0084\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u000f\n\u0002\u0018\u0002\n\u0002\b\u0017\n\u0002\u0018\u0002\n\u0002\b\u0011\u0018\u00002\u00020\u0001By\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\b\u0012\u0006\u0010\t\u001a\u00020\n\u0012\u0006\u0010\u000b\u001a\u00020\f\u0012\u0006\u0010\r\u001a\u00020\u000e\u0012\u0006\u0010\u000f\u001a\u00020\u000e\u0012\u0006\u0010\u0010\u001a\u00020\u000e\u0012\u0006\u0010\u0011\u001a\u00020\u000e\u0012\u0006\u0010\u0012\u001a\u00020\u000e\u0012\u0006\u0010\u0013\u001a\u00020\u000e\u0012\b\b\u0002\u0010\u0014\u001a\u00020\u000e\u0012\b\b\u0002\u0010\u0015\u001a\u00020\u0016¢\u0006\u0002\u0010\u0017J\u0010\u0010t\u001a\u00020\u00012\u0006\u0010m\u001a\u00020\u0019H\u0016R\u0010\u0010\u0018\u001a\u0004\u0018\u00010\u0019X\u0082\u000e¢\u0006\u0002\n\u0000R \u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\u001c0\u001bX\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001d\u0010\u001e\"\u0004\b\u001f\u0010 R\u001a\u0010!\u001a\u00020\"X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b#\u0010$\"\u0004\b%\u0010&R\u001c\u0010'\u001a\u0004\u0018\u00010(X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b)\u0010*\"\u0004\b+\u0010,R\u0016\u0010-\u001a\u0004\u0018\u00010.8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b/\u00100R\u001a\u00101\u001a\u0002028VX\u0097\u0004¢\u0006\f\u0012\u0004\b3\u00104\u001a\u0004\b5\u00106R\u0014\u0010\u0004\u001a\u00020\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b7\u00108R\u0014\u0010\u0015\u001a\u00020\u0016X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b9\u0010:R\u001c\u0010;\u001a\u0004\u0018\u00010<X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b=\u0010>\"\u0004\b?\u0010@R\u0014\u0010A\u001a\u00020\u000e8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\bA\u0010BR\u001a\u0010\u000f\u001a\u00020\u000eX\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000f\u0010B\"\u0004\bC\u0010DR\u001a\u0010\u0011\u001a\u00020\u000eX\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0011\u0010B\"\u0004\bE\u0010DR\u001a\u0010\u0013\u001a\u00020\u000eX\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0013\u0010B\"\u0004\bF\u0010DR\u001a\u0010\u0012\u001a\u00020\u000eX\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0012\u0010B\"\u0004\bG\u0010DR\u001a\u0010\u0014\u001a\u00020\u000eX\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0014\u0010B\"\u0004\bH\u0010DR\u001a\u0010\u0010\u001a\u00020\u000eX\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0010\u0010B\"\u0004\bI\u0010DR\u001a\u0010\r\u001a\u00020\u000eX\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\r\u0010B\"\u0004\bJ\u0010DR\u001c\u0010K\u001a\u0004\u0018\u00010LX\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bM\u0010N\"\u0004\bO\u0010PR\u001a\u0010\u000b\u001a\u00020\fX\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bQ\u0010R\"\u0004\bS\u0010TR\u001a\u0010\u0007\u001a\u00020\bX\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bU\u0010V\"\u0004\bW\u0010XR\u001a\u0010\u0005\u001a\u00020\u0006X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bY\u0010Z\"\u0004\b[\u0010\\R\u001c\u0010]\u001a\u0004\u0018\u00010\"X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b^\u0010$\"\u0004\b_\u0010&R \u0010`\u001a\b\u0012\u0004\u0012\u00020\u00190\u001bX\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\ba\u0010\u001e\"\u0004\bb\u0010 R\u001a\u0010c\u001a\u00020dX\u0096.¢\u0006\u000e\n\u0000\u001a\u0004\be\u0010f\"\u0004\bg\u0010hR\u001c\u0010i\u001a\u0004\u0018\u00010<X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bj\u0010>\"\u0004\bk\u0010@R\u0014\u0010\u0002\u001a\u00020\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\bl\u00108R\u0014\u0010m\u001a\u00020\u00198VX\u0096\u0004¢\u0006\u0006\u001a\u0004\bn\u0010oR\u001a\u0010\t\u001a\u00020\nX\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bp\u0010q\"\u0004\br\u0010s¨\u0006u"}, d2 = {"Lorg/jetbrains/kotlin/ir/declarations/impl/IrPropertyWithLateBindingImpl;", "Lorg/jetbrains/kotlin/ir/declarations/IrPropertyWithLateBinding;", "startOffset", "", "endOffset", "origin", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationOrigin;", "name", "Lorg/jetbrains/kotlin/name/Name;", "visibility", "Lorg/jetbrains/kotlin/descriptors/DescriptorVisibility;", "modality", "Lorg/jetbrains/kotlin/descriptors/Modality;", "isVar", "", "isConst", "isLateinit", "isDelegated", "isExternal", "isExpect", "isFakeOverride", "factory", "Lorg/jetbrains/kotlin/ir/declarations/IrFactory;", "(IILorg/jetbrains/kotlin/ir/declarations/IrDeclarationOrigin;Lorg/jetbrains/kotlin/name/Name;Lorg/jetbrains/kotlin/descriptors/DescriptorVisibility;Lorg/jetbrains/kotlin/descriptors/Modality;ZZZZZZZLorg/jetbrains/kotlin/ir/declarations/IrFactory;)V", "_symbol", "Lorg/jetbrains/kotlin/ir/symbols/IrPropertySymbol;", "annotations", "", "Lorg/jetbrains/kotlin/ir/expressions/IrConstructorCall;", "getAnnotations", "()Ljava/util/List;", "setAnnotations", "(Ljava/util/List;)V", "attributeOwnerId", "Lorg/jetbrains/kotlin/ir/declarations/IrAttributeContainer;", "getAttributeOwnerId", "()Lorg/jetbrains/kotlin/ir/declarations/IrAttributeContainer;", "setAttributeOwnerId", "(Lorg/jetbrains/kotlin/ir/declarations/IrAttributeContainer;)V", "backingField", "Lorg/jetbrains/kotlin/ir/declarations/IrField;", "getBackingField", "()Lorg/jetbrains/kotlin/ir/declarations/IrField;", "setBackingField", "(Lorg/jetbrains/kotlin/ir/declarations/IrField;)V", "containerSource", "Lorg/jetbrains/kotlin/serialization/deserialization/descriptors/DeserializedContainerSource;", "getContainerSource", "()Lorg/jetbrains/kotlin/serialization/deserialization/descriptors/DeserializedContainerSource;", "descriptor", "Lorg/jetbrains/kotlin/descriptors/PropertyDescriptor;", "getDescriptor$annotations", "()V", "getDescriptor", "()Lorg/jetbrains/kotlin/descriptors/PropertyDescriptor;", "getEndOffset", "()I", "getFactory", "()Lorg/jetbrains/kotlin/ir/declarations/IrFactory;", "getter", "Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;", "getGetter", "()Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;", "setGetter", "(Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;)V", "isBound", "()Z", "setConst", "(Z)V", "setDelegated", "setExpect", "setExternal", "setFakeOverride", "setLateinit", "setVar", "metadata", "Lorg/jetbrains/kotlin/ir/declarations/MetadataSource;", "getMetadata", "()Lorg/jetbrains/kotlin/ir/declarations/MetadataSource;", "setMetadata", "(Lorg/jetbrains/kotlin/ir/declarations/MetadataSource;)V", "getModality", "()Lorg/jetbrains/kotlin/descriptors/Modality;", "setModality", "(Lorg/jetbrains/kotlin/descriptors/Modality;)V", "getName", "()Lorg/jetbrains/kotlin/name/Name;", "setName", "(Lorg/jetbrains/kotlin/name/Name;)V", "getOrigin", "()Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationOrigin;", "setOrigin", "(Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationOrigin;)V", "originalBeforeInline", "getOriginalBeforeInline", "setOriginalBeforeInline", "overriddenSymbols", "getOverriddenSymbols", "setOverriddenSymbols", "parent", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationParent;", "getParent", "()Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationParent;", "setParent", "(Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationParent;)V", "setter", "getSetter", "setSetter", "getStartOffset", NativeSymbol.TYPE_NAME, "getSymbol", "()Lorg/jetbrains/kotlin/ir/symbols/IrPropertySymbol;", "getVisibility", "()Lorg/jetbrains/kotlin/descriptors/DescriptorVisibility;", "setVisibility", "(Lorg/jetbrains/kotlin/descriptors/DescriptorVisibility;)V", "acquireSymbol", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class IrPropertyWithLateBindingImpl extends IrPropertyWithLateBinding {
    private IrPropertySymbol _symbol;
    private List<? extends IrConstructorCall> annotations;
    private IrAttributeContainer attributeOwnerId;
    private IrField backingField;
    private final int endOffset;
    private final IrFactory factory;
    private IrSimpleFunction getter;
    private boolean isConst;
    private boolean isDelegated;
    private boolean isExpect;
    private boolean isExternal;
    private boolean isFakeOverride;
    private boolean isLateinit;
    private boolean isVar;
    private MetadataSource metadata;
    private Modality modality;
    private Name name;
    private IrDeclarationOrigin origin;
    private IrAttributeContainer originalBeforeInline;
    private List<? extends IrPropertySymbol> overriddenSymbols;
    public IrDeclarationParent parent;
    private IrSimpleFunction setter;
    private final int startOffset;
    private DescriptorVisibility visibility;

    @ObsoleteDescriptorBasedAPI
    public static /* synthetic */ void getDescriptor$annotations() {
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrMemberWithContainerSource
    public DeserializedContainerSource getContainerSource() {
        return null;
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrSymbolOwner, org.jetbrains.kotlin.ir.declarations.IrReturnTarget, org.jetbrains.kotlin.ir.declarations.IrOverridableDeclaration
    public IrPropertySymbol getSymbol() {
        IrPropertySymbol irPropertySymbol = this._symbol;
        if (irPropertySymbol != null) {
            return irPropertySymbol;
        }
        throw new IllegalStateException((this + " has not acquired a symbol yet").toString());
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

    @Override // org.jetbrains.kotlin.ir.declarations.IrDeclarationWithName
    public Name getName() {
        return this.name;
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrDeclarationWithName
    public void setName(Name name2) {
        Intrinsics.checkNotNullParameter(name2, "<set-?>");
        this.name = name2;
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrDeclarationWithVisibility
    public DescriptorVisibility getVisibility() {
        return this.visibility;
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrDeclarationWithVisibility
    public void setVisibility(DescriptorVisibility descriptorVisibility) {
        Intrinsics.checkNotNullParameter(descriptorVisibility, "<set-?>");
        this.visibility = descriptorVisibility;
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrOverridableMember
    public Modality getModality() {
        return this.modality;
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrOverridableMember
    public void setModality(Modality modality) {
        Intrinsics.checkNotNullParameter(modality, "<set-?>");
        this.modality = modality;
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrProperty
    /* JADX INFO: renamed from: isVar, reason: from getter */
    public boolean getIsVar() {
        return this.isVar;
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrProperty
    public void setVar(boolean z) {
        this.isVar = z;
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrProperty
    /* JADX INFO: renamed from: isConst, reason: from getter */
    public boolean getIsConst() {
        return this.isConst;
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrProperty
    public void setConst(boolean z) {
        this.isConst = z;
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrProperty
    /* JADX INFO: renamed from: isLateinit, reason: from getter */
    public boolean getIsLateinit() {
        return this.isLateinit;
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrProperty
    public void setLateinit(boolean z) {
        this.isLateinit = z;
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrProperty
    /* JADX INFO: renamed from: isDelegated, reason: from getter */
    public boolean getIsDelegated() {
        return this.isDelegated;
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrProperty
    public void setDelegated(boolean z) {
        this.isDelegated = z;
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrPossiblyExternalDeclaration
    /* JADX INFO: renamed from: isExternal, reason: from getter */
    public boolean getIsExternal() {
        return this.isExternal;
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrPossiblyExternalDeclaration
    public void setExternal(boolean z) {
        this.isExternal = z;
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrProperty
    /* JADX INFO: renamed from: isExpect, reason: from getter */
    public boolean getIsExpect() {
        return this.isExpect;
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrProperty
    public void setExpect(boolean z) {
        this.isExpect = z;
    }

    public /* synthetic */ IrPropertyWithLateBindingImpl(int i, int i2, IrDeclarationOrigin irDeclarationOrigin, Name name2, DescriptorVisibility descriptorVisibility, Modality modality, boolean z, boolean z2, boolean z3, boolean z4, boolean z5, boolean z6, boolean z7, IrFactory irFactory, int i3, DefaultConstructorMarker defaultConstructorMarker) {
        this(i, i2, irDeclarationOrigin, name2, descriptorVisibility, modality, z, z2, z3, z4, z5, z6, (i3 & 4096) != 0 ? Intrinsics.areEqual(irDeclarationOrigin, IrDeclarationOrigin.FAKE_OVERRIDE.INSTANCE) : z7, (i3 & 8192) != 0 ? IrFactoryImpl.INSTANCE : irFactory);
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrProperty, org.jetbrains.kotlin.ir.declarations.IrOverridableDeclaration
    /* JADX INFO: renamed from: isFakeOverride, reason: from getter */
    public boolean getIsFakeOverride() {
        return this.isFakeOverride;
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrProperty, org.jetbrains.kotlin.ir.declarations.IrOverridableDeclaration
    public void setFakeOverride(boolean z) {
        this.isFakeOverride = z;
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrDeclaration, org.jetbrains.kotlin.ir.declarations.lazy.IrLazyDeclarationBase
    public IrFactory getFactory() {
        return this.factory;
    }

    public IrPropertyWithLateBindingImpl(int i, int i2, IrDeclarationOrigin origin, Name name2, DescriptorVisibility visibility, Modality modality, boolean z, boolean z2, boolean z3, boolean z4, boolean z5, boolean z6, boolean z7, IrFactory factory) {
        Intrinsics.checkNotNullParameter(origin, "origin");
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(visibility, "visibility");
        Intrinsics.checkNotNullParameter(modality, "modality");
        Intrinsics.checkNotNullParameter(factory, "factory");
        this.startOffset = i;
        this.endOffset = i2;
        this.origin = origin;
        this.name = name2;
        this.visibility = visibility;
        this.modality = modality;
        this.isVar = z;
        this.isConst = z2;
        this.isLateinit = z3;
        this.isDelegated = z4;
        this.isExternal = z5;
        this.isExpect = z6;
        this.isFakeOverride = z7;
        this.factory = factory;
        this.annotations = CollectionsKt.emptyList();
        this.overriddenSymbols = CollectionsKt.emptyList();
        this.attributeOwnerId = this;
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrDeclaration
    public PropertyDescriptor getDescriptor() {
        PropertyDescriptor descriptor;
        IrPropertySymbol irPropertySymbol = this._symbol;
        return (irPropertySymbol == null || (descriptor = irPropertySymbol.getDescriptor()) == null) ? IrBasedDescriptorsKt.toIrBasedDescriptor((IrProperty) this) : descriptor;
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrPropertyWithLateBinding
    public IrPropertyWithLateBinding acquireSymbol(IrPropertySymbol symbol) {
        Intrinsics.checkNotNullParameter(symbol, "symbol");
        this._symbol = symbol;
        symbol.bind(this);
        return this;
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

    @Override // org.jetbrains.kotlin.ir.declarations.IrProperty
    public IrField getBackingField() {
        return this.backingField;
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrProperty
    public void setBackingField(IrField irField) {
        this.backingField = irField;
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrProperty
    public IrSimpleFunction getGetter() {
        return this.getter;
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrProperty
    public void setGetter(IrSimpleFunction irSimpleFunction) {
        this.getter = irSimpleFunction;
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrProperty
    public IrSimpleFunction getSetter() {
        return this.setter;
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrProperty
    public void setSetter(IrSimpleFunction irSimpleFunction) {
        this.setter = irSimpleFunction;
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrOverridableDeclaration
    public List<IrPropertySymbol> getOverriddenSymbols() {
        return this.overriddenSymbols;
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrOverridableDeclaration
    public void setOverriddenSymbols(List<? extends IrPropertySymbol> list) {
        Intrinsics.checkNotNullParameter(list, "<set-?>");
        this.overriddenSymbols = list;
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrMetadataSourceOwner
    public MetadataSource getMetadata() {
        return this.metadata;
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrMetadataSourceOwner
    public void setMetadata(MetadataSource metadataSource) {
        this.metadata = metadataSource;
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrAttributeContainer
    public IrAttributeContainer getAttributeOwnerId() {
        return this.attributeOwnerId;
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrAttributeContainer
    public void setAttributeOwnerId(IrAttributeContainer irAttributeContainer) {
        Intrinsics.checkNotNullParameter(irAttributeContainer, "<set-?>");
        this.attributeOwnerId = irAttributeContainer;
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrAttributeContainer
    public IrAttributeContainer getOriginalBeforeInline() {
        return this.originalBeforeInline;
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrAttributeContainer
    public void setOriginalBeforeInline(IrAttributeContainer irAttributeContainer) {
        this.originalBeforeInline = irAttributeContainer;
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrPropertyWithLateBinding
    public boolean isBound() {
        return this._symbol != null;
    }
}
