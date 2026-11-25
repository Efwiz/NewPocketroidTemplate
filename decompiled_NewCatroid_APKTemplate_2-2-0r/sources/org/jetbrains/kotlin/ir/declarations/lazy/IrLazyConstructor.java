package org.jetbrains.kotlin.ir.declarations.lazy;

import java.util.ArrayList;
import java.util.List;
import kotlin.Lazy;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.MutablePropertyReference1Impl;
import kotlin.jvm.internal.Reflection;
import kotlin.properties.ReadWriteProperty;
import kotlin.reflect.KProperty;
import org.jetbrains.kotlin.descriptors.ClassConstructorDescriptor;
import org.jetbrains.kotlin.descriptors.DescriptorVisibility;
import org.jetbrains.kotlin.descriptors.ReceiverParameterDescriptor;
import org.jetbrains.kotlin.descriptors.TypeParameterDescriptor;
import org.jetbrains.kotlin.ir.declarations.IrConstructor;
import org.jetbrains.kotlin.ir.declarations.IrDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationOrigin;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationParent;
import org.jetbrains.kotlin.ir.declarations.IrFactory;
import org.jetbrains.kotlin.ir.declarations.IrFunction;
import org.jetbrains.kotlin.ir.declarations.IrTypeParameter;
import org.jetbrains.kotlin.ir.declarations.IrValueParameter;
import org.jetbrains.kotlin.ir.declarations.MetadataSource;
import org.jetbrains.kotlin.ir.expressions.IrBody;
import org.jetbrains.kotlin.ir.expressions.IrConstructorCall;
import org.jetbrains.kotlin.ir.symbols.IrConstructorSymbol;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.ir.util.DeclarationStubGenerator;
import org.jetbrains.kotlin.ir.util.SymbolTable;
import org.jetbrains.kotlin.ir.util.TypeTranslator;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.serialization.deserialization.descriptors.DescriptorWithContainerSource;
import org.jetbrains.kotlin.serialization.deserialization.descriptors.DeserializedContainerSource;
import org.jetbrains.kotlin.types.KotlinType;
import org.mozilla.javascript.NativeSymbol;

/* JADX INFO: compiled from: IrLazyConstructor.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\u0096\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\f\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0002\b\r\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0002\b\u000f\u0018\u00002\u00020\u00012\u00020\u0002Bm\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0004\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u0012\u0006\u0010\n\u001a\u00020\u000b\u0012\u0006\u0010\f\u001a\u00020\r\u0012\u0006\u0010\u000e\u001a\u00020\u000f\u0012\u0006\u0010\u0010\u001a\u00020\u0011\u0012\u0006\u0010\u0012\u001a\u00020\u0011\u0012\u0006\u0010\u0013\u001a\u00020\u0011\u0012\u0006\u0010\u0014\u001a\u00020\u0011\u0012\u0006\u0010\u0015\u001a\u00020\u0016\u0012\u0006\u0010\u0017\u001a\u00020\u0018¢\u0006\u0002\u0010\u0019R7\u0010\u001d\u001a\b\u0012\u0004\u0012\u00020\u001c0\u001b2\f\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\u001c0\u001b8V@VX\u0096\u008e\u0002¢\u0006\u0012\n\u0004\b\"\u0010#\u001a\u0004\b\u001e\u0010\u001f\"\u0004\b \u0010!R\u001c\u0010$\u001a\u0004\u0018\u00010%X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b&\u0010'\"\u0004\b(\u0010)R\u0016\u0010*\u001a\u0004\u0018\u00010+8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b,\u0010-R\u001a\u0010.\u001a\u00020\u0004X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b/\u00100\"\u0004\b1\u00102R\u0014\u0010\n\u001a\u00020\u000bX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b3\u00104R/\u00106\u001a\u0004\u0018\u0001052\b\u0010\u001a\u001a\u0004\u0018\u0001058V@VX\u0096\u008e\u0002¢\u0006\u0012\n\u0004\b;\u0010#\u001a\u0004\b7\u00108\"\u0004\b9\u0010:R\u0014\u0010\u0005\u001a\u00020\u0004X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b<\u00100R/\u0010=\u001a\u0004\u0018\u0001052\b\u0010\u001a\u001a\u0004\u0018\u0001058V@VX\u0096\u008e\u0002¢\u0006\u0012\n\u0004\b@\u0010#\u001a\u0004\b>\u00108\"\u0004\b?\u0010:R\u001d\u0010A\u001a\u0004\u0018\u00010B8VX\u0096\u0084\u0002¢\u0006\f\n\u0004\bE\u0010F\u001a\u0004\bC\u0010DR\u001a\u0010\u0014\u001a\u00020\u0011X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0014\u0010G\"\u0004\bH\u0010IR\u001a\u0010\u0012\u001a\u00020\u0011X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0012\u0010G\"\u0004\bJ\u0010IR\u001a\u0010\u0010\u001a\u00020\u0011X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0010\u0010G\"\u0004\bK\u0010IR\u001a\u0010\u0013\u001a\u00020\u0011X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0013\u0010G\"\u0004\bL\u0010IR(\u0010O\u001a\u0004\u0018\u00010N2\b\u0010M\u001a\u0004\u0018\u00010N8V@VX\u0096\u000e¢\u0006\f\u001a\u0004\bP\u0010Q\"\u0004\bR\u0010SR\u001a\u0010\f\u001a\u00020\rX\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bT\u0010U\"\u0004\bV\u0010WR\u001a\u0010\u0006\u001a\u00020\u0007X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bX\u0010Y\"\u0004\bZ\u0010[R+\u0010]\u001a\u00020\\2\u0006\u0010\u001a\u001a\u00020\\8V@VX\u0096\u008e\u0002¢\u0006\u0012\n\u0004\bb\u0010#\u001a\u0004\b^\u0010_\"\u0004\b`\u0010aR+\u0010d\u001a\u00020c2\u0006\u0010\u001a\u001a\u00020c8V@VX\u0096\u008e\u0002¢\u0006\u0012\n\u0004\bi\u0010#\u001a\u0004\be\u0010f\"\u0004\bg\u0010hR\u0014\u0010\u0003\u001a\u00020\u0004X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\bj\u00100R\u0014\u0010\u0015\u001a\u00020\u0016X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\bk\u0010lR\u0014\u0010\b\u001a\u00020\tX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\bm\u0010nR7\u0010p\u001a\b\u0012\u0004\u0012\u00020o0\u001b2\f\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020o0\u001b8V@VX\u0096\u008e\u0002¢\u0006\u0012\n\u0004\bs\u0010#\u001a\u0004\bq\u0010\u001f\"\u0004\br\u0010!R\u0014\u0010\u0017\u001a\u00020\u0018X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\bt\u0010uR7\u0010v\u001a\b\u0012\u0004\u0012\u0002050\u001b2\f\u0010\u001a\u001a\b\u0012\u0004\u0012\u0002050\u001b8V@VX\u0096\u008e\u0002¢\u0006\u0012\n\u0004\by\u0010#\u001a\u0004\bw\u0010\u001f\"\u0004\bx\u0010!R\u001a\u0010\u000e\u001a\u00020\u000fX\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bz\u0010{\"\u0004\b|\u0010}¨\u0006~"}, d2 = {"Lorg/jetbrains/kotlin/ir/declarations/lazy/IrLazyConstructor;", "Lorg/jetbrains/kotlin/ir/declarations/IrConstructor;", "Lorg/jetbrains/kotlin/ir/declarations/lazy/IrLazyFunctionBase;", "startOffset", "", "endOffset", "origin", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationOrigin;", NativeSymbol.TYPE_NAME, "Lorg/jetbrains/kotlin/ir/symbols/IrConstructorSymbol;", "descriptor", "Lorg/jetbrains/kotlin/descriptors/ClassConstructorDescriptor;", "name", "Lorg/jetbrains/kotlin/name/Name;", "visibility", "Lorg/jetbrains/kotlin/descriptors/DescriptorVisibility;", "isInline", "", "isExternal", "isPrimary", "isExpect", "stubGenerator", "Lorg/jetbrains/kotlin/ir/util/DeclarationStubGenerator;", "typeTranslator", "Lorg/jetbrains/kotlin/ir/util/TypeTranslator;", "(IILorg/jetbrains/kotlin/ir/declarations/IrDeclarationOrigin;Lorg/jetbrains/kotlin/ir/symbols/IrConstructorSymbol;Lorg/jetbrains/kotlin/descriptors/ClassConstructorDescriptor;Lorg/jetbrains/kotlin/name/Name;Lorg/jetbrains/kotlin/descriptors/DescriptorVisibility;ZZZZLorg/jetbrains/kotlin/ir/util/DeclarationStubGenerator;Lorg/jetbrains/kotlin/ir/util/TypeTranslator;)V", "<set-?>", "", "Lorg/jetbrains/kotlin/ir/expressions/IrConstructorCall;", "annotations", "getAnnotations", "()Ljava/util/List;", "setAnnotations", "(Ljava/util/List;)V", "annotations$delegate", "Lkotlin/properties/ReadWriteProperty;", "body", "Lorg/jetbrains/kotlin/ir/expressions/IrBody;", "getBody", "()Lorg/jetbrains/kotlin/ir/expressions/IrBody;", "setBody", "(Lorg/jetbrains/kotlin/ir/expressions/IrBody;)V", "containerSource", "Lorg/jetbrains/kotlin/serialization/deserialization/descriptors/DeserializedContainerSource;", "getContainerSource", "()Lorg/jetbrains/kotlin/serialization/deserialization/descriptors/DeserializedContainerSource;", "contextReceiverParametersCount", "getContextReceiverParametersCount", "()I", "setContextReceiverParametersCount", "(I)V", "getDescriptor", "()Lorg/jetbrains/kotlin/descriptors/ClassConstructorDescriptor;", "Lorg/jetbrains/kotlin/ir/declarations/IrValueParameter;", "dispatchReceiverParameter", "getDispatchReceiverParameter", "()Lorg/jetbrains/kotlin/ir/declarations/IrValueParameter;", "setDispatchReceiverParameter", "(Lorg/jetbrains/kotlin/ir/declarations/IrValueParameter;)V", "dispatchReceiverParameter$delegate", "getEndOffset", "extensionReceiverParameter", "getExtensionReceiverParameter", "setExtensionReceiverParameter", "extensionReceiverParameter$delegate", "initialSignatureFunction", "Lorg/jetbrains/kotlin/ir/declarations/IrFunction;", "getInitialSignatureFunction", "()Lorg/jetbrains/kotlin/ir/declarations/IrFunction;", "initialSignatureFunction$delegate", "Lkotlin/Lazy;", "()Z", "setExpect", "(Z)V", "setExternal", "setInline", "setPrimary", "<anonymous parameter 0>", "Lorg/jetbrains/kotlin/ir/declarations/MetadataSource;", "metadata", "getMetadata", "()Lorg/jetbrains/kotlin/ir/declarations/MetadataSource;", "setMetadata", "(Lorg/jetbrains/kotlin/ir/declarations/MetadataSource;)V", "getName", "()Lorg/jetbrains/kotlin/name/Name;", "setName", "(Lorg/jetbrains/kotlin/name/Name;)V", "getOrigin", "()Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationOrigin;", "setOrigin", "(Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationOrigin;)V", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationParent;", "parent", "getParent", "()Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationParent;", "setParent", "(Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationParent;)V", "parent$delegate", "Lorg/jetbrains/kotlin/ir/types/IrType;", "returnType", "getReturnType", "()Lorg/jetbrains/kotlin/ir/types/IrType;", "setReturnType", "(Lorg/jetbrains/kotlin/ir/types/IrType;)V", "returnType$delegate", "getStartOffset", "getStubGenerator", "()Lorg/jetbrains/kotlin/ir/util/DeclarationStubGenerator;", "getSymbol", "()Lorg/jetbrains/kotlin/ir/symbols/IrConstructorSymbol;", "Lorg/jetbrains/kotlin/ir/declarations/IrTypeParameter;", "typeParameters", "getTypeParameters", "setTypeParameters", "typeParameters$delegate", "getTypeTranslator", "()Lorg/jetbrains/kotlin/ir/util/TypeTranslator;", "valueParameters", "getValueParameters", "setValueParameters", "valueParameters$delegate", "getVisibility", "()Lorg/jetbrains/kotlin/descriptors/DescriptorVisibility;", "setVisibility", "(Lorg/jetbrains/kotlin/descriptors/DescriptorVisibility;)V", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class IrLazyConstructor extends IrConstructor implements IrLazyFunctionBase {
    static final /* synthetic */ KProperty<Object>[] $$delegatedProperties = {Reflection.mutableProperty1(new MutablePropertyReference1Impl(Reflection.getOrCreateKotlinClass(IrLazyConstructor.class), "parent", "getParent()Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationParent;")), Reflection.mutableProperty1(new MutablePropertyReference1Impl(Reflection.getOrCreateKotlinClass(IrLazyConstructor.class), "annotations", "getAnnotations()Ljava/util/List;")), Reflection.mutableProperty1(new MutablePropertyReference1Impl(Reflection.getOrCreateKotlinClass(IrLazyConstructor.class), "returnType", "getReturnType()Lorg/jetbrains/kotlin/ir/types/IrType;")), Reflection.mutableProperty1(new MutablePropertyReference1Impl(Reflection.getOrCreateKotlinClass(IrLazyConstructor.class), "dispatchReceiverParameter", "getDispatchReceiverParameter()Lorg/jetbrains/kotlin/ir/declarations/IrValueParameter;")), Reflection.mutableProperty1(new MutablePropertyReference1Impl(Reflection.getOrCreateKotlinClass(IrLazyConstructor.class), "extensionReceiverParameter", "getExtensionReceiverParameter()Lorg/jetbrains/kotlin/ir/declarations/IrValueParameter;")), Reflection.mutableProperty1(new MutablePropertyReference1Impl(Reflection.getOrCreateKotlinClass(IrLazyConstructor.class), "valueParameters", "getValueParameters()Ljava/util/List;")), Reflection.mutableProperty1(new MutablePropertyReference1Impl(Reflection.getOrCreateKotlinClass(IrLazyConstructor.class), "typeParameters", "getTypeParameters()Ljava/util/List;"))};

    /* JADX INFO: renamed from: annotations$delegate, reason: from kotlin metadata */
    private final ReadWriteProperty annotations;
    private IrBody body;
    private int contextReceiverParametersCount;
    private final ClassConstructorDescriptor descriptor;

    /* JADX INFO: renamed from: dispatchReceiverParameter$delegate, reason: from kotlin metadata */
    private final ReadWriteProperty dispatchReceiverParameter;
    private final int endOffset;

    /* JADX INFO: renamed from: extensionReceiverParameter$delegate, reason: from kotlin metadata */
    private final ReadWriteProperty extensionReceiverParameter;

    /* JADX INFO: renamed from: initialSignatureFunction$delegate, reason: from kotlin metadata */
    private final Lazy initialSignatureFunction;
    private boolean isExpect;
    private boolean isExternal;
    private boolean isInline;
    private boolean isPrimary;
    private Name name;
    private IrDeclarationOrigin origin;

    /* JADX INFO: renamed from: parent$delegate, reason: from kotlin metadata */
    private final ReadWriteProperty parent;

    /* JADX INFO: renamed from: returnType$delegate, reason: from kotlin metadata */
    private final ReadWriteProperty returnType;
    private final int startOffset;
    private final DeclarationStubGenerator stubGenerator;
    private final IrConstructorSymbol symbol;

    /* JADX INFO: renamed from: typeParameters$delegate, reason: from kotlin metadata */
    private final ReadWriteProperty typeParameters;
    private final TypeTranslator typeTranslator;

    /* JADX INFO: renamed from: valueParameters$delegate, reason: from kotlin metadata */
    private final ReadWriteProperty valueParameters;
    private DescriptorVisibility visibility;

    @Override // org.jetbrains.kotlin.ir.declarations.IrMetadataSourceOwner
    public MetadataSource getMetadata() {
        return null;
    }

    @Override // org.jetbrains.kotlin.ir.declarations.lazy.IrLazyFunctionBase
    public Lazy<IrFunction> createInitialSignatureFunction() {
        return IrLazyFunctionBase.DefaultImpls.createInitialSignatureFunction(this);
    }

    @Override // org.jetbrains.kotlin.ir.declarations.lazy.IrLazyDeclarationBase
    public ReadWriteProperty<Object, List<IrConstructorCall>> createLazyAnnotations() {
        return IrLazyFunctionBase.DefaultImpls.createLazyAnnotations(this);
    }

    @Override // org.jetbrains.kotlin.ir.declarations.lazy.IrLazyDeclarationBase
    public ReadWriteProperty<Object, IrDeclarationParent> createLazyParent() {
        return IrLazyFunctionBase.DefaultImpls.createLazyParent(this);
    }

    @Override // org.jetbrains.kotlin.ir.declarations.lazy.IrLazyFunctionBase
    public IrValueParameter createReceiverParameter(ReceiverParameterDescriptor receiverParameterDescriptor, boolean z) {
        return IrLazyFunctionBase.DefaultImpls.createReceiverParameter(this, receiverParameterDescriptor, z);
    }

    @Override // org.jetbrains.kotlin.ir.declarations.lazy.IrLazyFunctionBase
    public IrType createReturnType() {
        return IrLazyFunctionBase.DefaultImpls.createReturnType(this);
    }

    @Override // org.jetbrains.kotlin.ir.declarations.lazy.IrLazyFunctionBase
    public List<IrValueParameter> createValueParameters() {
        return IrLazyFunctionBase.DefaultImpls.createValueParameters(this);
    }

    @Override // org.jetbrains.kotlin.ir.declarations.lazy.IrLazyDeclarationBase
    public IrValueParameter generateReceiverParameterStub(ReceiverParameterDescriptor receiverParameterDescriptor) {
        return IrLazyFunctionBase.DefaultImpls.generateReceiverParameterStub(this, receiverParameterDescriptor);
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrDeclaration, org.jetbrains.kotlin.ir.declarations.lazy.IrLazyDeclarationBase
    public IrFactory getFactory() {
        return IrLazyFunctionBase.DefaultImpls.getFactory(this);
    }

    @Override // org.jetbrains.kotlin.ir.declarations.lazy.IrLazyFunctionBase
    public IrDeclaration getTopLevelDeclaration() {
        return IrLazyFunctionBase.DefaultImpls.getTopLevelDeclaration(this);
    }

    @Override // org.jetbrains.kotlin.ir.declarations.lazy.IrLazyDeclarationBase
    public IrDeclarationParent lazyParent() {
        return IrLazyFunctionBase.DefaultImpls.lazyParent(this);
    }

    @Override // org.jetbrains.kotlin.ir.declarations.lazy.IrLazyDeclarationBase
    public IrType toIrType(KotlinType kotlinType) {
        return IrLazyFunctionBase.DefaultImpls.toIrType(this, kotlinType);
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
    public IrConstructorSymbol getSymbol() {
        return this.symbol;
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrFunction, org.jetbrains.kotlin.ir.declarations.IrDeclaration
    public ClassConstructorDescriptor getDescriptor() {
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

    @Override // org.jetbrains.kotlin.ir.declarations.IrFunction
    /* JADX INFO: renamed from: isInline, reason: from getter */
    public boolean getIsInline() {
        return this.isInline;
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrFunction
    public void setInline(boolean z) {
        this.isInline = z;
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

    @Override // org.jetbrains.kotlin.ir.declarations.IrConstructor
    /* JADX INFO: renamed from: isPrimary, reason: from getter */
    public boolean getIsPrimary() {
        return this.isPrimary;
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrConstructor
    public void setPrimary(boolean z) {
        this.isPrimary = z;
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrFunction
    /* JADX INFO: renamed from: isExpect, reason: from getter */
    public boolean getIsExpect() {
        return this.isExpect;
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrFunction
    public void setExpect(boolean z) {
        this.isExpect = z;
    }

    @Override // org.jetbrains.kotlin.ir.declarations.lazy.IrLazyDeclarationBase
    public DeclarationStubGenerator getStubGenerator() {
        return this.stubGenerator;
    }

    @Override // org.jetbrains.kotlin.ir.declarations.lazy.IrLazyDeclarationBase
    public TypeTranslator getTypeTranslator() {
        return this.typeTranslator;
    }

    public IrLazyConstructor(int i, int i2, IrDeclarationOrigin origin, IrConstructorSymbol symbol, ClassConstructorDescriptor descriptor, Name name2, DescriptorVisibility visibility, boolean z, boolean z2, boolean z3, boolean z4, DeclarationStubGenerator stubGenerator, TypeTranslator typeTranslator) {
        Intrinsics.checkNotNullParameter(origin, "origin");
        Intrinsics.checkNotNullParameter(symbol, "symbol");
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(visibility, "visibility");
        Intrinsics.checkNotNullParameter(stubGenerator, "stubGenerator");
        Intrinsics.checkNotNullParameter(typeTranslator, "typeTranslator");
        this.startOffset = i;
        this.endOffset = i2;
        this.origin = origin;
        this.symbol = symbol;
        this.descriptor = descriptor;
        this.name = name2;
        this.visibility = visibility;
        this.isInline = z;
        this.isExternal = z2;
        this.isPrimary = z3;
        this.isExpect = z4;
        this.stubGenerator = stubGenerator;
        this.typeTranslator = typeTranslator;
        this.parent = createLazyParent();
        this.annotations = createLazyAnnotations();
        this.returnType = LazyUtilKt.lazyVar(getStubGenerator().getLock(), new Function0<IrType>() { // from class: org.jetbrains.kotlin.ir.declarations.lazy.IrLazyConstructor$returnType$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final IrType invoke() {
                return this.this$0.createReturnType();
            }
        });
        this.initialSignatureFunction = createInitialSignatureFunction();
        this.dispatchReceiverParameter = LazyUtilKt.lazyVar(getStubGenerator().getLock(), new Function0<IrValueParameter>() { // from class: org.jetbrains.kotlin.ir.declarations.lazy.IrLazyConstructor$dispatchReceiverParameter$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final IrValueParameter invoke() {
                IrLazyConstructor irLazyConstructor = this.this$0;
                return IrLazyFunctionBase.DefaultImpls.createReceiverParameter$default(irLazyConstructor, irLazyConstructor.getDescriptor().mo6753getDispatchReceiverParameter(), false, 2, null);
            }
        });
        this.extensionReceiverParameter = LazyUtilKt.lazyVar(getStubGenerator().getLock(), new Function0<IrValueParameter>() { // from class: org.jetbrains.kotlin.ir.declarations.lazy.IrLazyConstructor$extensionReceiverParameter$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final IrValueParameter invoke() {
                IrLazyConstructor irLazyConstructor = this.this$0;
                return IrLazyFunctionBase.DefaultImpls.createReceiverParameter$default(irLazyConstructor, irLazyConstructor.getDescriptor().getExtensionReceiverParameter(), false, 2, null);
            }
        });
        this.valueParameters = LazyUtilKt.lazyVar(getStubGenerator().getLock(), new Function0<List<? extends IrValueParameter>>() { // from class: org.jetbrains.kotlin.ir.declarations.lazy.IrLazyConstructor$valueParameters$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final List<? extends IrValueParameter> invoke() {
                return this.this$0.createValueParameters();
            }
        });
        this.contextReceiverParametersCount = getDescriptor().getContextReceiverParameters().size();
        this.typeParameters = LazyUtilKt.lazyVar(getStubGenerator().getLock(), new Function0<List<? extends IrTypeParameter>>() { // from class: org.jetbrains.kotlin.ir.declarations.lazy.IrLazyConstructor$typeParameters$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final List<? extends IrTypeParameter> invoke() {
                TypeTranslator typeTranslator2 = this.this$0.getTypeTranslator();
                IrLazyConstructor irLazyConstructor = this.this$0;
                final IrLazyConstructor irLazyConstructor2 = this.this$0;
                return (List) typeTranslator2.buildWithScope(irLazyConstructor, new Function0<ArrayList<IrTypeParameter>>() { // from class: org.jetbrains.kotlin.ir.declarations.lazy.IrLazyConstructor$typeParameters$2.1
                    {
                        super(0);
                    }

                    @Override // kotlin.jvm.functions.Function0
                    public final ArrayList<IrTypeParameter> invoke() {
                        SymbolTable symbolTable = irLazyConstructor2.getStubGenerator().getSymbolTable();
                        IrLazyConstructor irLazyConstructor3 = irLazyConstructor2;
                        IrLazyConstructor irLazyConstructor4 = irLazyConstructor3;
                        symbolTable.enterScope(irLazyConstructor4);
                        int size = irLazyConstructor3.getDescriptor().getConstructedClass().getOriginal().getDeclaredTypeParameters().size();
                        List<TypeParameterDescriptor> typeParameters = irLazyConstructor3.getDescriptor().getTypeParameters();
                        Intrinsics.checkNotNullExpressionValue(typeParameters, "getTypeParameters(...)");
                        List<TypeParameterDescriptor> listSubList = typeParameters.subList(size, typeParameters.size());
                        ArrayList<IrTypeParameter> arrayList = new ArrayList<>();
                        for (TypeParameterDescriptor typeParameterDescriptor : listSubList) {
                            DeclarationStubGenerator stubGenerator2 = irLazyConstructor3.getStubGenerator();
                            Intrinsics.checkNotNull(typeParameterDescriptor);
                            arrayList.add(stubGenerator2.generateOrGetTypeParameterStub$ir_tree(typeParameterDescriptor));
                        }
                        ArrayList<IrTypeParameter> arrayList2 = arrayList;
                        symbolTable.leaveScope(irLazyConstructor4);
                        return arrayList2;
                    }
                });
            }
        });
        getSymbol().bind(this);
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

    @Override // org.jetbrains.kotlin.ir.declarations.IrFunction
    public IrBody getBody() {
        return this.body;
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrFunction
    public void setBody(IrBody irBody) {
        this.body = irBody;
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrFunction
    public IrType getReturnType() {
        return (IrType) this.returnType.getValue(this, $$delegatedProperties[2]);
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrFunction
    public void setReturnType(IrType irType) {
        Intrinsics.checkNotNullParameter(irType, "<set-?>");
        this.returnType.setValue(this, $$delegatedProperties[2], irType);
    }

    @Override // org.jetbrains.kotlin.ir.declarations.lazy.IrLazyFunctionBase
    public IrFunction getInitialSignatureFunction() {
        return (IrFunction) this.initialSignatureFunction.getValue();
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrFunction
    public IrValueParameter getDispatchReceiverParameter() {
        return (IrValueParameter) this.dispatchReceiverParameter.getValue(this, $$delegatedProperties[3]);
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrFunction
    public void setDispatchReceiverParameter(IrValueParameter irValueParameter) {
        this.dispatchReceiverParameter.setValue(this, $$delegatedProperties[3], irValueParameter);
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrFunction
    public IrValueParameter getExtensionReceiverParameter() {
        return (IrValueParameter) this.extensionReceiverParameter.getValue(this, $$delegatedProperties[4]);
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrFunction
    public void setExtensionReceiverParameter(IrValueParameter irValueParameter) {
        this.extensionReceiverParameter.setValue(this, $$delegatedProperties[4], irValueParameter);
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrFunction
    public List<IrValueParameter> getValueParameters() {
        return (List) this.valueParameters.getValue(this, $$delegatedProperties[5]);
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrFunction
    public void setValueParameters(List<? extends IrValueParameter> list) {
        Intrinsics.checkNotNullParameter(list, "<set-?>");
        this.valueParameters.setValue(this, $$delegatedProperties[5], list);
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrFunction
    public int getContextReceiverParametersCount() {
        return this.contextReceiverParametersCount;
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrFunction
    public void setContextReceiverParametersCount(int i) {
        this.contextReceiverParametersCount = i;
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrMetadataSourceOwner
    public void setMetadata(MetadataSource metadataSource) {
        throw new IllegalStateException("We should never need to store metadata of external declarations.".toString());
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrTypeParametersContainer
    public List<IrTypeParameter> getTypeParameters() {
        return (List) this.typeParameters.getValue(this, $$delegatedProperties[6]);
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrTypeParametersContainer
    public void setTypeParameters(List<? extends IrTypeParameter> list) {
        Intrinsics.checkNotNullParameter(list, "<set-?>");
        this.typeParameters.setValue(this, $$delegatedProperties[6], list);
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrMemberWithContainerSource
    public DeserializedContainerSource getContainerSource() {
        ClassConstructorDescriptor descriptor = getDescriptor();
        DescriptorWithContainerSource descriptorWithContainerSource = descriptor instanceof DescriptorWithContainerSource ? (DescriptorWithContainerSource) descriptor : null;
        if (descriptorWithContainerSource != null) {
            return descriptorWithContainerSource.getContainerSource();
        }
        return null;
    }
}
