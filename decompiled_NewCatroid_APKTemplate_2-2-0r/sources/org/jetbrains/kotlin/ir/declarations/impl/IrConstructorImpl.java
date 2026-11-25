package org.jetbrains.kotlin.ir.declarations.impl;

import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.ClassConstructorDescriptor;
import org.jetbrains.kotlin.descriptors.DescriptorVisibility;
import org.jetbrains.kotlin.ir.ObsoleteDescriptorBasedAPI;
import org.jetbrains.kotlin.ir.declarations.IrConstructor;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationOrigin;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationParent;
import org.jetbrains.kotlin.ir.declarations.IrFactory;
import org.jetbrains.kotlin.ir.declarations.IrTypeParameter;
import org.jetbrains.kotlin.ir.declarations.IrValueParameter;
import org.jetbrains.kotlin.ir.declarations.MetadataSource;
import org.jetbrains.kotlin.ir.expressions.IrBody;
import org.jetbrains.kotlin.ir.expressions.IrConstructorCall;
import org.jetbrains.kotlin.ir.symbols.IrConstructorSymbol;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.ir.types.impl.IrUninitializedType;
import org.jetbrains.kotlin.ir.types.impl.ReturnTypeIsNotInitializedException;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.serialization.deserialization.descriptors.DeserializedContainerSource;
import org.mozilla.javascript.NativeSymbol;

/* JADX INFO: compiled from: IrConstructorImpl.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\u0082\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u000e\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0011\n\u0002\u0018\u0002\n\u0002\b\r\n\u0002\u0018\u0002\n\u0002\b\f\n\u0002\u0018\u0002\n\u0002\b\n\u0018\u00002\u00020\u0001Bs\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\b\u0012\u0006\u0010\t\u001a\u00020\n\u0012\u0006\u0010\u000b\u001a\u00020\f\u0012\u0006\u0010\r\u001a\u00020\u000e\u0012\u0006\u0010\u000f\u001a\u00020\u0010\u0012\u0006\u0010\u0011\u001a\u00020\u0010\u0012\u0006\u0010\u0012\u001a\u00020\u0010\u0012\u0006\u0010\u0013\u001a\u00020\u0010\u0012\n\b\u0002\u0010\u0014\u001a\u0004\u0018\u00010\u0015\u0012\b\b\u0002\u0010\u0016\u001a\u00020\u0017¢\u0006\u0002\u0010\u0018R \u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u001b0\u001aX\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001c\u0010\u001d\"\u0004\b\u001e\u0010\u001fR\u001c\u0010 \u001a\u0004\u0018\u00010!X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\"\u0010#\"\u0004\b$\u0010%R\u001c\u0010\u0014\u001a\u0004\u0018\u00010\u0015X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b&\u0010'\"\u0004\b(\u0010)R\u001a\u0010*\u001a\u00020\u0003X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b+\u0010,\"\u0004\b-\u0010.R\u001a\u0010/\u001a\u0002008VX\u0097\u0004¢\u0006\f\u0012\u0004\b1\u00102\u001a\u0004\b3\u00104R\u001c\u00105\u001a\u0004\u0018\u000106X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b7\u00108\"\u0004\b9\u0010:R\u0014\u0010\u0004\u001a\u00020\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b;\u0010,R\u001c\u0010<\u001a\u0004\u0018\u000106X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b=\u00108\"\u0004\b>\u0010:R\u0014\u0010\u0016\u001a\u00020\u0017X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b?\u0010@R\u001a\u0010\u0013\u001a\u00020\u0010X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0013\u0010A\"\u0004\bB\u0010CR\u001a\u0010\u0011\u001a\u00020\u0010X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0011\u0010A\"\u0004\bD\u0010CR\u001a\u0010\u000f\u001a\u00020\u0010X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000f\u0010A\"\u0004\bE\u0010CR\u001a\u0010\u0012\u001a\u00020\u0010X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0012\u0010A\"\u0004\bF\u0010CR\u001c\u0010G\u001a\u0004\u0018\u00010HX\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bI\u0010J\"\u0004\bK\u0010LR\u001a\u0010\t\u001a\u00020\nX\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bM\u0010N\"\u0004\bO\u0010PR\u001a\u0010\u0005\u001a\u00020\u0006X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bQ\u0010R\"\u0004\bS\u0010TR\u001a\u0010U\u001a\u00020VX\u0096.¢\u0006\u000e\n\u0000\u001a\u0004\bW\u0010X\"\u0004\bY\u0010ZR\u001c\u0010\r\u001a\u00020\u000e8VX\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b[\u0010\\\"\u0004\b]\u0010^R\u0014\u0010\u0002\u001a\u00020\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b_\u0010,R\u0014\u0010\u0007\u001a\u00020\bX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b`\u0010aR \u0010b\u001a\b\u0012\u0004\u0012\u00020c0\u001aX\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bd\u0010\u001d\"\u0004\be\u0010\u001fR \u0010f\u001a\b\u0012\u0004\u0012\u0002060\u001aX\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bg\u0010\u001d\"\u0004\bh\u0010\u001fR\u001a\u0010\u000b\u001a\u00020\fX\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bi\u0010j\"\u0004\bk\u0010l¨\u0006m"}, d2 = {"Lorg/jetbrains/kotlin/ir/declarations/impl/IrConstructorImpl;", "Lorg/jetbrains/kotlin/ir/declarations/IrConstructor;", "startOffset", "", "endOffset", "origin", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationOrigin;", NativeSymbol.TYPE_NAME, "Lorg/jetbrains/kotlin/ir/symbols/IrConstructorSymbol;", "name", "Lorg/jetbrains/kotlin/name/Name;", "visibility", "Lorg/jetbrains/kotlin/descriptors/DescriptorVisibility;", "returnType", "Lorg/jetbrains/kotlin/ir/types/IrType;", "isInline", "", "isExternal", "isPrimary", "isExpect", "containerSource", "Lorg/jetbrains/kotlin/serialization/deserialization/descriptors/DeserializedContainerSource;", "factory", "Lorg/jetbrains/kotlin/ir/declarations/IrFactory;", "(IILorg/jetbrains/kotlin/ir/declarations/IrDeclarationOrigin;Lorg/jetbrains/kotlin/ir/symbols/IrConstructorSymbol;Lorg/jetbrains/kotlin/name/Name;Lorg/jetbrains/kotlin/descriptors/DescriptorVisibility;Lorg/jetbrains/kotlin/ir/types/IrType;ZZZZLorg/jetbrains/kotlin/serialization/deserialization/descriptors/DeserializedContainerSource;Lorg/jetbrains/kotlin/ir/declarations/IrFactory;)V", "annotations", "", "Lorg/jetbrains/kotlin/ir/expressions/IrConstructorCall;", "getAnnotations", "()Ljava/util/List;", "setAnnotations", "(Ljava/util/List;)V", "body", "Lorg/jetbrains/kotlin/ir/expressions/IrBody;", "getBody", "()Lorg/jetbrains/kotlin/ir/expressions/IrBody;", "setBody", "(Lorg/jetbrains/kotlin/ir/expressions/IrBody;)V", "getContainerSource", "()Lorg/jetbrains/kotlin/serialization/deserialization/descriptors/DeserializedContainerSource;", "setContainerSource", "(Lorg/jetbrains/kotlin/serialization/deserialization/descriptors/DeserializedContainerSource;)V", "contextReceiverParametersCount", "getContextReceiverParametersCount", "()I", "setContextReceiverParametersCount", "(I)V", "descriptor", "Lorg/jetbrains/kotlin/descriptors/ClassConstructorDescriptor;", "getDescriptor$annotations", "()V", "getDescriptor", "()Lorg/jetbrains/kotlin/descriptors/ClassConstructorDescriptor;", "dispatchReceiverParameter", "Lorg/jetbrains/kotlin/ir/declarations/IrValueParameter;", "getDispatchReceiverParameter", "()Lorg/jetbrains/kotlin/ir/declarations/IrValueParameter;", "setDispatchReceiverParameter", "(Lorg/jetbrains/kotlin/ir/declarations/IrValueParameter;)V", "getEndOffset", "extensionReceiverParameter", "getExtensionReceiverParameter", "setExtensionReceiverParameter", "getFactory", "()Lorg/jetbrains/kotlin/ir/declarations/IrFactory;", "()Z", "setExpect", "(Z)V", "setExternal", "setInline", "setPrimary", "metadata", "Lorg/jetbrains/kotlin/ir/declarations/MetadataSource;", "getMetadata", "()Lorg/jetbrains/kotlin/ir/declarations/MetadataSource;", "setMetadata", "(Lorg/jetbrains/kotlin/ir/declarations/MetadataSource;)V", "getName", "()Lorg/jetbrains/kotlin/name/Name;", "setName", "(Lorg/jetbrains/kotlin/name/Name;)V", "getOrigin", "()Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationOrigin;", "setOrigin", "(Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationOrigin;)V", "parent", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationParent;", "getParent", "()Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationParent;", "setParent", "(Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationParent;)V", "getReturnType", "()Lorg/jetbrains/kotlin/ir/types/IrType;", "setReturnType", "(Lorg/jetbrains/kotlin/ir/types/IrType;)V", "getStartOffset", "getSymbol", "()Lorg/jetbrains/kotlin/ir/symbols/IrConstructorSymbol;", "typeParameters", "Lorg/jetbrains/kotlin/ir/declarations/IrTypeParameter;", "getTypeParameters", "setTypeParameters", "valueParameters", "getValueParameters", "setValueParameters", "getVisibility", "()Lorg/jetbrains/kotlin/descriptors/DescriptorVisibility;", "setVisibility", "(Lorg/jetbrains/kotlin/descriptors/DescriptorVisibility;)V", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class IrConstructorImpl extends IrConstructor {
    private List<? extends IrConstructorCall> annotations;
    private IrBody body;
    private DeserializedContainerSource containerSource;
    private int contextReceiverParametersCount;
    private IrValueParameter dispatchReceiverParameter;
    private final int endOffset;
    private IrValueParameter extensionReceiverParameter;
    private final IrFactory factory;
    private boolean isExpect;
    private boolean isExternal;
    private boolean isInline;
    private boolean isPrimary;
    private MetadataSource metadata;
    private Name name;
    private IrDeclarationOrigin origin;
    public IrDeclarationParent parent;
    private IrType returnType;
    private final int startOffset;
    private final IrConstructorSymbol symbol;
    private List<? extends IrTypeParameter> typeParameters;
    private List<? extends IrValueParameter> valueParameters;
    private DescriptorVisibility visibility;

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
    public IrConstructorSymbol getSymbol() {
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

    @Override // org.jetbrains.kotlin.ir.declarations.IrMemberWithContainerSource
    public DeserializedContainerSource getContainerSource() {
        return this.containerSource;
    }

    public void setContainerSource(DeserializedContainerSource deserializedContainerSource) {
        this.containerSource = deserializedContainerSource;
    }

    public /* synthetic */ IrConstructorImpl(int i, int i2, IrDeclarationOrigin irDeclarationOrigin, IrConstructorSymbol irConstructorSymbol, Name name2, DescriptorVisibility descriptorVisibility, IrType irType, boolean z, boolean z2, boolean z3, boolean z4, DeserializedContainerSource deserializedContainerSource, IrFactory irFactory, int i3, DefaultConstructorMarker defaultConstructorMarker) {
        this(i, i2, irDeclarationOrigin, irConstructorSymbol, name2, descriptorVisibility, irType, z, z2, z3, z4, (i3 & 2048) != 0 ? null : deserializedContainerSource, (i3 & 4096) != 0 ? IrFactoryImpl.INSTANCE : irFactory);
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrDeclaration, org.jetbrains.kotlin.ir.declarations.lazy.IrLazyDeclarationBase
    public IrFactory getFactory() {
        return this.factory;
    }

    public IrConstructorImpl(int i, int i2, IrDeclarationOrigin origin, IrConstructorSymbol symbol, Name name2, DescriptorVisibility visibility, IrType returnType, boolean z, boolean z2, boolean z3, boolean z4, DeserializedContainerSource deserializedContainerSource, IrFactory factory) {
        Intrinsics.checkNotNullParameter(origin, "origin");
        Intrinsics.checkNotNullParameter(symbol, "symbol");
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(visibility, "visibility");
        Intrinsics.checkNotNullParameter(returnType, "returnType");
        Intrinsics.checkNotNullParameter(factory, "factory");
        this.startOffset = i;
        this.endOffset = i2;
        this.origin = origin;
        this.symbol = symbol;
        this.name = name2;
        this.visibility = visibility;
        this.isInline = z;
        this.isExternal = z2;
        this.isPrimary = z3;
        this.isExpect = z4;
        this.containerSource = deserializedContainerSource;
        this.factory = factory;
        getSymbol().bind(this);
        this.annotations = CollectionsKt.emptyList();
        this.returnType = returnType;
        this.typeParameters = CollectionsKt.emptyList();
        this.valueParameters = CollectionsKt.emptyList();
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

    @Override // org.jetbrains.kotlin.ir.declarations.IrFunction
    public void setReturnType(IrType irType) {
        Intrinsics.checkNotNullParameter(irType, "<set-?>");
        this.returnType = irType;
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrFunction
    public IrType getReturnType() {
        if (this.returnType == IrUninitializedType.INSTANCE) {
            throw new ReturnTypeIsNotInitializedException(this);
        }
        return this.returnType;
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrTypeParametersContainer
    public List<IrTypeParameter> getTypeParameters() {
        return this.typeParameters;
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrTypeParametersContainer
    public void setTypeParameters(List<? extends IrTypeParameter> list) {
        Intrinsics.checkNotNullParameter(list, "<set-?>");
        this.typeParameters = list;
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrFunction
    public IrValueParameter getDispatchReceiverParameter() {
        return this.dispatchReceiverParameter;
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrFunction
    public void setDispatchReceiverParameter(IrValueParameter irValueParameter) {
        this.dispatchReceiverParameter = irValueParameter;
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrFunction
    public IrValueParameter getExtensionReceiverParameter() {
        return this.extensionReceiverParameter;
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrFunction
    public void setExtensionReceiverParameter(IrValueParameter irValueParameter) {
        this.extensionReceiverParameter = irValueParameter;
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrFunction
    public int getContextReceiverParametersCount() {
        return this.contextReceiverParametersCount;
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrFunction
    public void setContextReceiverParametersCount(int i) {
        this.contextReceiverParametersCount = i;
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrFunction
    public List<IrValueParameter> getValueParameters() {
        return this.valueParameters;
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrFunction
    public void setValueParameters(List<? extends IrValueParameter> list) {
        Intrinsics.checkNotNullParameter(list, "<set-?>");
        this.valueParameters = list;
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrFunction
    public IrBody getBody() {
        return this.body;
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrFunction
    public void setBody(IrBody irBody) {
        this.body = irBody;
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrMetadataSourceOwner
    public MetadataSource getMetadata() {
        return this.metadata;
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrMetadataSourceOwner
    public void setMetadata(MetadataSource metadataSource) {
        this.metadata = metadataSource;
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrFunction, org.jetbrains.kotlin.ir.declarations.IrDeclaration
    public ClassConstructorDescriptor getDescriptor() {
        return getSymbol().getDescriptor();
    }
}
