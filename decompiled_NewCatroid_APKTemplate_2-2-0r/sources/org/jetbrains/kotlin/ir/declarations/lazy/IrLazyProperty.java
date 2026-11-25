package org.jetbrains.kotlin.ir.declarations.lazy;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.MutablePropertyReference1Impl;
import kotlin.jvm.internal.Reflection;
import kotlin.properties.ReadWriteProperty;
import kotlin.reflect.KProperty;
import org.jetbrains.kotlin.descriptors.DescriptorVisibility;
import org.jetbrains.kotlin.descriptors.Modality;
import org.jetbrains.kotlin.descriptors.PropertyDescriptor;
import org.jetbrains.kotlin.descriptors.PropertyGetterDescriptor;
import org.jetbrains.kotlin.descriptors.PropertySetterDescriptor;
import org.jetbrains.kotlin.descriptors.ReceiverParameterDescriptor;
import org.jetbrains.kotlin.ir.declarations.IrAttributeContainer;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationOrigin;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationParent;
import org.jetbrains.kotlin.ir.declarations.IrFactory;
import org.jetbrains.kotlin.ir.declarations.IrField;
import org.jetbrains.kotlin.ir.declarations.IrProperty;
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction;
import org.jetbrains.kotlin.ir.declarations.IrValueParameter;
import org.jetbrains.kotlin.ir.declarations.MetadataSource;
import org.jetbrains.kotlin.ir.expressions.IrConstructorCall;
import org.jetbrains.kotlin.ir.symbols.IrPropertySymbol;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.ir.util.DeclarationStubGenerator;
import org.jetbrains.kotlin.ir.util.TypeTranslator;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.serialization.deserialization.descriptors.DeserializedContainerSource;
import org.jetbrains.kotlin.serialization.deserialization.descriptors.DeserializedPropertyDescriptor;
import org.jetbrains.kotlin.types.KotlinType;
import org.mozilla.javascript.NativeSymbol;

/* JADX INFO: compiled from: IrLazyProperty.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\u008c\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0010\n\u0002\u0018\u0002\n\u0002\b\u0018\n\u0002\u0018\u0002\n\u0002\b\u0016\u0018\u00002\u00020\u00012\u00020\u0002B\u008d\u0001\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0004\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u0012\u0006\u0010\n\u001a\u00020\u000b\u0012\u0006\u0010\f\u001a\u00020\r\u0012\u0006\u0010\u000e\u001a\u00020\u000f\u0012\u0006\u0010\u0010\u001a\u00020\u0011\u0012\u0006\u0010\u0012\u001a\u00020\u0013\u0012\u0006\u0010\u0014\u001a\u00020\u0013\u0012\u0006\u0010\u0015\u001a\u00020\u0013\u0012\u0006\u0010\u0016\u001a\u00020\u0013\u0012\u0006\u0010\u0017\u001a\u00020\u0013\u0012\u0006\u0010\u0018\u001a\u00020\u0013\u0012\u0006\u0010\u0019\u001a\u00020\u0013\u0012\u0006\u0010\u001a\u001a\u00020\u001b\u0012\u0006\u0010\u001c\u001a\u00020\u001d¢\u0006\u0002\u0010\u001eR7\u0010\"\u001a\b\u0012\u0004\u0012\u00020!0 2\f\u0010\u001f\u001a\b\u0012\u0004\u0012\u00020!0 8V@VX\u0096\u008e\u0002¢\u0006\u0012\n\u0004\b'\u0010(\u001a\u0004\b#\u0010$\"\u0004\b%\u0010&R$\u0010+\u001a\u00020*2\u0006\u0010)\u001a\u00020*8V@VX\u0096\u000e¢\u0006\f\u001a\u0004\b,\u0010-\"\u0004\b.\u0010/R/\u00101\u001a\u0004\u0018\u0001002\b\u0010\u001f\u001a\u0004\u0018\u0001008V@VX\u0096\u008e\u0002¢\u0006\u0012\n\u0004\b6\u0010(\u001a\u0004\b2\u00103\"\u0004\b4\u00105R\u0016\u00107\u001a\u0004\u0018\u0001088VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b9\u0010:R\u0014\u0010\n\u001a\u00020\u000bX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b;\u0010<R\u0014\u0010\u0005\u001a\u00020\u0004X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b=\u0010>R/\u0010@\u001a\u0004\u0018\u00010?2\b\u0010\u001f\u001a\u0004\u0018\u00010?8V@VX\u0096\u008e\u0002¢\u0006\u0012\n\u0004\bE\u0010(\u001a\u0004\bA\u0010B\"\u0004\bC\u0010DR\u000e\u0010F\u001a\u00020\u0013X\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\u0014\u001a\u00020\u0013X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0014\u0010G\"\u0004\bH\u0010IR\u001a\u0010\u0016\u001a\u00020\u0013X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0016\u0010G\"\u0004\bJ\u0010IR\u001a\u0010\u0018\u001a\u00020\u0013X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0018\u0010G\"\u0004\bK\u0010IR\u001a\u0010\u0017\u001a\u00020\u0013X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0017\u0010G\"\u0004\bL\u0010IR\u001a\u0010\u0019\u001a\u00020\u0013X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0019\u0010G\"\u0004\bM\u0010IR\u001a\u0010\u0015\u001a\u00020\u0013X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0015\u0010G\"\u0004\bN\u0010IR\u001a\u0010\u0012\u001a\u00020\u0013X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0012\u0010G\"\u0004\bO\u0010IR(\u0010Q\u001a\u0004\u0018\u00010P2\b\u0010)\u001a\u0004\u0018\u00010P8V@VX\u0096\u000e¢\u0006\f\u001a\u0004\bR\u0010S\"\u0004\bT\u0010UR\u001a\u0010\u0010\u001a\u00020\u0011X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bV\u0010W\"\u0004\bX\u0010YR\u001a\u0010\f\u001a\u00020\rX\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bZ\u0010[\"\u0004\b\\\u0010]R\u001a\u0010\u0006\u001a\u00020\u0007X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b^\u0010_\"\u0004\b`\u0010aR(\u0010b\u001a\u0004\u0018\u00010*2\b\u0010)\u001a\u0004\u0018\u00010*8V@VX\u0096\u000e¢\u0006\f\u001a\u0004\bc\u0010-\"\u0004\bd\u0010/R7\u0010e\u001a\b\u0012\u0004\u0012\u00020\t0 2\f\u0010\u001f\u001a\b\u0012\u0004\u0012\u00020\t0 8V@VX\u0096\u008e\u0002¢\u0006\u0012\n\u0004\bh\u0010(\u001a\u0004\bf\u0010$\"\u0004\bg\u0010&R+\u0010j\u001a\u00020i2\u0006\u0010\u001f\u001a\u00020i8V@VX\u0096\u008e\u0002¢\u0006\u0012\n\u0004\bo\u0010(\u001a\u0004\bk\u0010l\"\u0004\bm\u0010nR/\u0010p\u001a\u0004\u0018\u00010?2\b\u0010\u001f\u001a\u0004\u0018\u00010?8V@VX\u0096\u008e\u0002¢\u0006\u0012\n\u0004\bs\u0010(\u001a\u0004\bq\u0010B\"\u0004\br\u0010DR\u0014\u0010\u0003\u001a\u00020\u0004X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\bt\u0010>R\u0014\u0010\u001a\u001a\u00020\u001bX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\bu\u0010vR\u0014\u0010\b\u001a\u00020\tX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\bw\u0010xR\u0014\u0010\u001c\u001a\u00020\u001dX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\by\u0010zR\u001a\u0010\u000e\u001a\u00020\u000fX\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b{\u0010|\"\u0004\b}\u0010~¨\u0006\u007f"}, d2 = {"Lorg/jetbrains/kotlin/ir/declarations/lazy/IrLazyProperty;", "Lorg/jetbrains/kotlin/ir/declarations/IrProperty;", "Lorg/jetbrains/kotlin/ir/declarations/lazy/IrLazyDeclarationBase;", "startOffset", "", "endOffset", "origin", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationOrigin;", NativeSymbol.TYPE_NAME, "Lorg/jetbrains/kotlin/ir/symbols/IrPropertySymbol;", "descriptor", "Lorg/jetbrains/kotlin/descriptors/PropertyDescriptor;", "name", "Lorg/jetbrains/kotlin/name/Name;", "visibility", "Lorg/jetbrains/kotlin/descriptors/DescriptorVisibility;", "modality", "Lorg/jetbrains/kotlin/descriptors/Modality;", "isVar", "", "isConst", "isLateinit", "isDelegated", "isExternal", "isExpect", "isFakeOverride", "stubGenerator", "Lorg/jetbrains/kotlin/ir/util/DeclarationStubGenerator;", "typeTranslator", "Lorg/jetbrains/kotlin/ir/util/TypeTranslator;", "(IILorg/jetbrains/kotlin/ir/declarations/IrDeclarationOrigin;Lorg/jetbrains/kotlin/ir/symbols/IrPropertySymbol;Lorg/jetbrains/kotlin/descriptors/PropertyDescriptor;Lorg/jetbrains/kotlin/name/Name;Lorg/jetbrains/kotlin/descriptors/DescriptorVisibility;Lorg/jetbrains/kotlin/descriptors/Modality;ZZZZZZZLorg/jetbrains/kotlin/ir/util/DeclarationStubGenerator;Lorg/jetbrains/kotlin/ir/util/TypeTranslator;)V", "<set-?>", "", "Lorg/jetbrains/kotlin/ir/expressions/IrConstructorCall;", "annotations", "getAnnotations", "()Ljava/util/List;", "setAnnotations", "(Ljava/util/List;)V", "annotations$delegate", "Lkotlin/properties/ReadWriteProperty;", "<anonymous parameter 0>", "Lorg/jetbrains/kotlin/ir/declarations/IrAttributeContainer;", "attributeOwnerId", "getAttributeOwnerId", "()Lorg/jetbrains/kotlin/ir/declarations/IrAttributeContainer;", "setAttributeOwnerId", "(Lorg/jetbrains/kotlin/ir/declarations/IrAttributeContainer;)V", "Lorg/jetbrains/kotlin/ir/declarations/IrField;", "backingField", "getBackingField", "()Lorg/jetbrains/kotlin/ir/declarations/IrField;", "setBackingField", "(Lorg/jetbrains/kotlin/ir/declarations/IrField;)V", "backingField$delegate", "containerSource", "Lorg/jetbrains/kotlin/serialization/deserialization/descriptors/DeserializedContainerSource;", "getContainerSource", "()Lorg/jetbrains/kotlin/serialization/deserialization/descriptors/DeserializedContainerSource;", "getDescriptor", "()Lorg/jetbrains/kotlin/descriptors/PropertyDescriptor;", "getEndOffset", "()I", "Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;", "getter", "getGetter", "()Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;", "setGetter", "(Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;)V", "getter$delegate", "hasBackingField", "()Z", "setConst", "(Z)V", "setDelegated", "setExpect", "setExternal", "setFakeOverride", "setLateinit", "setVar", "Lorg/jetbrains/kotlin/ir/declarations/MetadataSource;", "metadata", "getMetadata", "()Lorg/jetbrains/kotlin/ir/declarations/MetadataSource;", "setMetadata", "(Lorg/jetbrains/kotlin/ir/declarations/MetadataSource;)V", "getModality", "()Lorg/jetbrains/kotlin/descriptors/Modality;", "setModality", "(Lorg/jetbrains/kotlin/descriptors/Modality;)V", "getName", "()Lorg/jetbrains/kotlin/name/Name;", "setName", "(Lorg/jetbrains/kotlin/name/Name;)V", "getOrigin", "()Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationOrigin;", "setOrigin", "(Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationOrigin;)V", "originalBeforeInline", "getOriginalBeforeInline", "setOriginalBeforeInline", "overriddenSymbols", "getOverriddenSymbols", "setOverriddenSymbols", "overriddenSymbols$delegate", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationParent;", "parent", "getParent", "()Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationParent;", "setParent", "(Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationParent;)V", "parent$delegate", "setter", "getSetter", "setSetter", "setter$delegate", "getStartOffset", "getStubGenerator", "()Lorg/jetbrains/kotlin/ir/util/DeclarationStubGenerator;", "getSymbol", "()Lorg/jetbrains/kotlin/ir/symbols/IrPropertySymbol;", "getTypeTranslator", "()Lorg/jetbrains/kotlin/ir/util/TypeTranslator;", "getVisibility", "()Lorg/jetbrains/kotlin/descriptors/DescriptorVisibility;", "setVisibility", "(Lorg/jetbrains/kotlin/descriptors/DescriptorVisibility;)V", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class IrLazyProperty extends IrProperty implements IrLazyDeclarationBase {
    static final /* synthetic */ KProperty<Object>[] $$delegatedProperties = {Reflection.mutableProperty1(new MutablePropertyReference1Impl(Reflection.getOrCreateKotlinClass(IrLazyProperty.class), "parent", "getParent()Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationParent;")), Reflection.mutableProperty1(new MutablePropertyReference1Impl(Reflection.getOrCreateKotlinClass(IrLazyProperty.class), "annotations", "getAnnotations()Ljava/util/List;")), Reflection.mutableProperty1(new MutablePropertyReference1Impl(Reflection.getOrCreateKotlinClass(IrLazyProperty.class), "backingField", "getBackingField()Lorg/jetbrains/kotlin/ir/declarations/IrField;")), Reflection.mutableProperty1(new MutablePropertyReference1Impl(Reflection.getOrCreateKotlinClass(IrLazyProperty.class), "getter", "getGetter()Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;")), Reflection.mutableProperty1(new MutablePropertyReference1Impl(Reflection.getOrCreateKotlinClass(IrLazyProperty.class), "setter", "getSetter()Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;")), Reflection.mutableProperty1(new MutablePropertyReference1Impl(Reflection.getOrCreateKotlinClass(IrLazyProperty.class), "overriddenSymbols", "getOverriddenSymbols()Ljava/util/List;"))};

    /* JADX INFO: renamed from: annotations$delegate, reason: from kotlin metadata */
    private final ReadWriteProperty annotations;

    /* JADX INFO: renamed from: backingField$delegate, reason: from kotlin metadata */
    private final ReadWriteProperty backingField;
    private final PropertyDescriptor descriptor;
    private final int endOffset;

    /* JADX INFO: renamed from: getter$delegate, reason: from kotlin metadata */
    private final ReadWriteProperty getter;
    private final boolean hasBackingField;
    private boolean isConst;
    private boolean isDelegated;
    private boolean isExpect;
    private boolean isExternal;
    private boolean isFakeOverride;
    private boolean isLateinit;
    private boolean isVar;
    private Modality modality;
    private Name name;
    private IrDeclarationOrigin origin;

    /* JADX INFO: renamed from: overriddenSymbols$delegate, reason: from kotlin metadata */
    private final ReadWriteProperty overriddenSymbols;

    /* JADX INFO: renamed from: parent$delegate, reason: from kotlin metadata */
    private final ReadWriteProperty parent;

    /* JADX INFO: renamed from: setter$delegate, reason: from kotlin metadata */
    private final ReadWriteProperty setter;
    private final int startOffset;
    private final DeclarationStubGenerator stubGenerator;
    private final IrPropertySymbol symbol;
    private final TypeTranslator typeTranslator;
    private DescriptorVisibility visibility;

    @Override // org.jetbrains.kotlin.ir.declarations.IrMetadataSourceOwner
    public MetadataSource getMetadata() {
        return null;
    }

    @Override // org.jetbrains.kotlin.ir.declarations.lazy.IrLazyDeclarationBase
    public ReadWriteProperty<Object, List<IrConstructorCall>> createLazyAnnotations() {
        return IrLazyDeclarationBase.DefaultImpls.createLazyAnnotations(this);
    }

    @Override // org.jetbrains.kotlin.ir.declarations.lazy.IrLazyDeclarationBase
    public ReadWriteProperty<Object, IrDeclarationParent> createLazyParent() {
        return IrLazyDeclarationBase.DefaultImpls.createLazyParent(this);
    }

    @Override // org.jetbrains.kotlin.ir.declarations.lazy.IrLazyDeclarationBase
    public IrValueParameter generateReceiverParameterStub(ReceiverParameterDescriptor receiverParameterDescriptor) {
        return IrLazyDeclarationBase.DefaultImpls.generateReceiverParameterStub(this, receiverParameterDescriptor);
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrDeclaration, org.jetbrains.kotlin.ir.declarations.lazy.IrLazyDeclarationBase
    public IrFactory getFactory() {
        return IrLazyDeclarationBase.DefaultImpls.getFactory(this);
    }

    @Override // org.jetbrains.kotlin.ir.declarations.lazy.IrLazyDeclarationBase
    public IrDeclarationParent lazyParent() {
        return IrLazyDeclarationBase.DefaultImpls.lazyParent(this);
    }

    @Override // org.jetbrains.kotlin.ir.declarations.lazy.IrLazyDeclarationBase
    public IrType toIrType(KotlinType kotlinType) {
        return IrLazyDeclarationBase.DefaultImpls.toIrType(this, kotlinType);
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
    public IrPropertySymbol getSymbol() {
        return this.symbol;
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrDeclaration
    public PropertyDescriptor getDescriptor() {
        return this.descriptor;
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

    @Override // org.jetbrains.kotlin.ir.declarations.IrProperty, org.jetbrains.kotlin.ir.declarations.IrOverridableDeclaration
    /* JADX INFO: renamed from: isFakeOverride, reason: from getter */
    public boolean getIsFakeOverride() {
        return this.isFakeOverride;
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrProperty, org.jetbrains.kotlin.ir.declarations.IrOverridableDeclaration
    public void setFakeOverride(boolean z) {
        this.isFakeOverride = z;
    }

    @Override // org.jetbrains.kotlin.ir.declarations.lazy.IrLazyDeclarationBase
    public DeclarationStubGenerator getStubGenerator() {
        return this.stubGenerator;
    }

    @Override // org.jetbrains.kotlin.ir.declarations.lazy.IrLazyDeclarationBase
    public TypeTranslator getTypeTranslator() {
        return this.typeTranslator;
    }

    public IrLazyProperty(int i, int i2, IrDeclarationOrigin origin, IrPropertySymbol symbol, PropertyDescriptor descriptor, Name name2, DescriptorVisibility visibility, Modality modality, boolean z, boolean z2, boolean z3, boolean z4, boolean z5, boolean z6, boolean z7, DeclarationStubGenerator stubGenerator, TypeTranslator typeTranslator) {
        Intrinsics.checkNotNullParameter(origin, "origin");
        Intrinsics.checkNotNullParameter(symbol, "symbol");
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(visibility, "visibility");
        Intrinsics.checkNotNullParameter(modality, "modality");
        Intrinsics.checkNotNullParameter(stubGenerator, "stubGenerator");
        Intrinsics.checkNotNullParameter(typeTranslator, "typeTranslator");
        this.startOffset = i;
        this.endOffset = i2;
        this.origin = origin;
        this.symbol = symbol;
        this.descriptor = descriptor;
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
        this.stubGenerator = stubGenerator;
        this.typeTranslator = typeTranslator;
        getSymbol().bind(this);
        this.parent = createLazyParent();
        this.annotations = createLazyAnnotations();
        this.hasBackingField = getDescriptor().mo6754getCompileTimeInitializer() != null || getDescriptor().getGetter() == null || getStubGenerator().getExtensions().isPropertyWithPlatformField(getDescriptor());
        this.backingField = LazyUtilKt.lazyVar(getStubGenerator().getLock(), new Function0<IrField>() { // from class: org.jetbrains.kotlin.ir.declarations.lazy.IrLazyProperty$backingField$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final IrField invoke() {
                if (!this.this$0.hasBackingField) {
                    return null;
                }
                IrField irFieldGenerateFieldStub = this.this$0.getStubGenerator().generateFieldStub(this.this$0.getDescriptor());
                irFieldGenerateFieldStub.setCorrespondingPropertySymbol(this.this$0.getSymbol());
                return irFieldGenerateFieldStub;
            }
        });
        this.getter = LazyUtilKt.lazyVar(getStubGenerator().getLock(), new Function0<IrSimpleFunction>() { // from class: org.jetbrains.kotlin.ir.declarations.lazy.IrLazyProperty$getter$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final IrSimpleFunction invoke() {
                PropertyGetterDescriptor getter = this.this$0.getDescriptor().getGetter();
                if (getter == null) {
                    return null;
                }
                IrLazyProperty irLazyProperty = this.this$0;
                IrSimpleFunction irSimpleFunctionGenerateFunctionStub = irLazyProperty.getStubGenerator().generateFunctionStub(getter, false);
                irSimpleFunctionGenerateFunctionStub.setCorrespondingPropertySymbol(irLazyProperty.getSymbol());
                return irSimpleFunctionGenerateFunctionStub;
            }
        });
        this.setter = LazyUtilKt.lazyVar(getStubGenerator().getLock(), new Function0<IrSimpleFunction>() { // from class: org.jetbrains.kotlin.ir.declarations.lazy.IrLazyProperty$setter$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final IrSimpleFunction invoke() {
                PropertySetterDescriptor setter = this.this$0.getDescriptor().getSetter();
                if (setter == null) {
                    return null;
                }
                IrLazyProperty irLazyProperty = this.this$0;
                IrSimpleFunction irSimpleFunctionGenerateFunctionStub = irLazyProperty.getStubGenerator().generateFunctionStub(setter, false);
                irSimpleFunctionGenerateFunctionStub.setCorrespondingPropertySymbol(irLazyProperty.getSymbol());
                return irSimpleFunctionGenerateFunctionStub;
            }
        });
        this.overriddenSymbols = LazyUtilKt.lazyVar(getStubGenerator().getLock(), new Function0<List<? extends IrPropertySymbol>>() { // from class: org.jetbrains.kotlin.ir.declarations.lazy.IrLazyProperty$overriddenSymbols$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final List<? extends IrPropertySymbol> invoke() {
                Collection<? extends PropertyDescriptor> overriddenDescriptors = this.this$0.getDescriptor().getOverriddenDescriptors();
                Intrinsics.checkNotNullExpressionValue(overriddenDescriptors, "getOverriddenDescriptors(...)");
                ArrayList arrayList = new ArrayList();
                IrLazyProperty irLazyProperty = this.this$0;
                for (PropertyDescriptor propertyDescriptor : overriddenDescriptors) {
                    DeclarationStubGenerator stubGenerator2 = irLazyProperty.getStubGenerator();
                    PropertyDescriptor original = propertyDescriptor.getOriginal();
                    Intrinsics.checkNotNullExpressionValue(original, "getOriginal(...)");
                    arrayList.add(stubGenerator2.generatePropertyStub(original).getSymbol());
                }
                return arrayList;
            }
        });
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrDeclaration
    public IrDeclarationParent getParent() {
        return (IrDeclarationParent) this.parent.getValue(this, $$delegatedProperties[0]);
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrDeclaration
    public void setParent(IrDeclarationParent irDeclarationParent) {
        Intrinsics.checkNotNullParameter(irDeclarationParent, "<set-?>");
        this.parent.setValue(this, $$delegatedProperties[0], irDeclarationParent);
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrMutableAnnotationContainer, org.jetbrains.kotlin.ir.declarations.IrAnnotationContainer
    public List<IrConstructorCall> getAnnotations() {
        return (List) this.annotations.getValue(this, $$delegatedProperties[1]);
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrMutableAnnotationContainer
    public void setAnnotations(List<? extends IrConstructorCall> list) {
        Intrinsics.checkNotNullParameter(list, "<set-?>");
        this.annotations.setValue(this, $$delegatedProperties[1], list);
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrProperty
    public IrField getBackingField() {
        return (IrField) this.backingField.getValue(this, $$delegatedProperties[2]);
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrProperty
    public void setBackingField(IrField irField) {
        this.backingField.setValue(this, $$delegatedProperties[2], irField);
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrProperty
    public IrSimpleFunction getGetter() {
        return (IrSimpleFunction) this.getter.getValue(this, $$delegatedProperties[3]);
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrProperty
    public void setGetter(IrSimpleFunction irSimpleFunction) {
        this.getter.setValue(this, $$delegatedProperties[3], irSimpleFunction);
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrProperty
    public IrSimpleFunction getSetter() {
        return (IrSimpleFunction) this.setter.getValue(this, $$delegatedProperties[4]);
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrProperty
    public void setSetter(IrSimpleFunction irSimpleFunction) {
        this.setter.setValue(this, $$delegatedProperties[4], irSimpleFunction);
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrOverridableDeclaration
    public List<IrPropertySymbol> getOverriddenSymbols() {
        return (List) this.overriddenSymbols.getValue(this, $$delegatedProperties[5]);
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrOverridableDeclaration
    public void setOverriddenSymbols(List<? extends IrPropertySymbol> list) {
        Intrinsics.checkNotNullParameter(list, "<set-?>");
        this.overriddenSymbols.setValue(this, $$delegatedProperties[5], list);
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrMemberWithContainerSource
    public DeserializedContainerSource getContainerSource() {
        PropertyDescriptor descriptor = getDescriptor();
        DeserializedPropertyDescriptor deserializedPropertyDescriptor = descriptor instanceof DeserializedPropertyDescriptor ? (DeserializedPropertyDescriptor) descriptor : null;
        if (deserializedPropertyDescriptor != null) {
            return deserializedPropertyDescriptor.getContainerSource();
        }
        return null;
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrMetadataSourceOwner
    public void setMetadata(MetadataSource metadataSource) {
        throw new IllegalStateException("We should never need to store metadata of external declarations.".toString());
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrAttributeContainer
    public IrAttributeContainer getAttributeOwnerId() {
        return this;
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrAttributeContainer
    public void setAttributeOwnerId(IrAttributeContainer irAttributeContainer) {
        Intrinsics.checkNotNullParameter(irAttributeContainer, "<anonymous parameter 0>");
        throw new IllegalStateException("We should never need to change attributeOwnerId of external declarations.".toString());
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrAttributeContainer
    public IrAttributeContainer getOriginalBeforeInline() {
        return this;
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrAttributeContainer
    public void setOriginalBeforeInline(IrAttributeContainer irAttributeContainer) {
        throw new IllegalStateException("We should never need to change originalBeforeInline of external declarations.".toString());
    }
}
