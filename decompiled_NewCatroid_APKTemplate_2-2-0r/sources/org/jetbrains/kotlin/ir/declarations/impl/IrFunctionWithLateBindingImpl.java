package org.jetbrains.kotlin.ir.declarations.impl;

import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.DescriptorVisibility;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.descriptors.Modality;
import org.jetbrains.kotlin.ir.ObsoleteDescriptorBasedAPI;
import org.jetbrains.kotlin.ir.declarations.IrAttributeContainer;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationOrigin;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationParent;
import org.jetbrains.kotlin.ir.declarations.IrFactory;
import org.jetbrains.kotlin.ir.declarations.IrFunctionWithLateBinding;
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction;
import org.jetbrains.kotlin.ir.declarations.IrTypeParameter;
import org.jetbrains.kotlin.ir.declarations.IrValueParameter;
import org.jetbrains.kotlin.ir.declarations.MetadataSource;
import org.jetbrains.kotlin.ir.descriptors.IrBasedDescriptorsKt;
import org.jetbrains.kotlin.ir.expressions.IrBody;
import org.jetbrains.kotlin.ir.expressions.IrConstructorCall;
import org.jetbrains.kotlin.ir.symbols.IrPropertySymbol;
import org.jetbrains.kotlin.ir.symbols.IrSimpleFunctionSymbol;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.ir.types.impl.IrUninitializedType;
import org.jetbrains.kotlin.ir.types.impl.ReturnTypeIsNotInitializedException;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.serialization.deserialization.descriptors.DeserializedContainerSource;
import org.mozilla.javascript.NativeSymbol;

/* JADX INFO: compiled from: IrFunctionImpl.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\u009a\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0016\n\u0002\u0018\u0002\n\u0002\b\u0017\n\u0002\u0018\u0002\n\u0002\b\r\n\u0002\u0018\u0002\n\u0002\b\u000b\u0018\u00002\u00020\u0001B\u0089\u0001\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\b\u0012\u0006\u0010\t\u001a\u00020\n\u0012\u0006\u0010\u000b\u001a\u00020\f\u0012\u0006\u0010\r\u001a\u00020\u000e\u0012\u0006\u0010\u000f\u001a\u00020\u0010\u0012\u0006\u0010\u0011\u001a\u00020\u0010\u0012\u0006\u0010\u0012\u001a\u00020\u0010\u0012\u0006\u0010\u0013\u001a\u00020\u0010\u0012\u0006\u0010\u0014\u001a\u00020\u0010\u0012\u0006\u0010\u0015\u001a\u00020\u0010\u0012\u0006\u0010\u0016\u001a\u00020\u0010\u0012\b\b\u0002\u0010\u0017\u001a\u00020\u0010\u0012\b\b\u0002\u0010\u0018\u001a\u00020\u0019¢\u0006\u0002\u0010\u001aJ\u0011\u0010\u008d\u0001\u001a\u00020\u00012\u0006\u0010\u007f\u001a\u00020\u001cH\u0016R\u0010\u0010\u001b\u001a\u0004\u0018\u00010\u001cX\u0082\u000e¢\u0006\u0002\n\u0000R \u0010\u001d\u001a\b\u0012\u0004\u0012\u00020\u001f0\u001eX\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b \u0010!\"\u0004\b\"\u0010#R\u001a\u0010$\u001a\u00020%X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b&\u0010'\"\u0004\b(\u0010)R\u001c\u0010*\u001a\u0004\u0018\u00010+X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b,\u0010-\"\u0004\b.\u0010/R\u0016\u00100\u001a\u0004\u0018\u0001018VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b2\u00103R\u001a\u00104\u001a\u00020\u0003X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b5\u00106\"\u0004\b7\u00108R\u001c\u00109\u001a\u0004\u0018\u00010:X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b;\u0010<\"\u0004\b=\u0010>R\u001a\u0010?\u001a\u00020@8VX\u0097\u0004¢\u0006\f\u0012\u0004\bA\u0010B\u001a\u0004\bC\u0010DR\u001c\u0010E\u001a\u0004\u0018\u00010FX\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bG\u0010H\"\u0004\bI\u0010JR\u0014\u0010\u0004\u001a\u00020\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\bK\u00106R\u001c\u0010L\u001a\u0004\u0018\u00010FX\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bM\u0010H\"\u0004\bN\u0010JR\u0014\u0010\u0018\u001a\u00020\u0019X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\bO\u0010PR\u0014\u0010Q\u001a\u00020\u00108VX\u0096\u0004¢\u0006\u0006\u001a\u0004\bQ\u0010RR\u001a\u0010\u0016\u001a\u00020\u0010X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0016\u0010R\"\u0004\bS\u0010TR\u001a\u0010\u0011\u001a\u00020\u0010X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0011\u0010R\"\u0004\bU\u0010TR\u001a\u0010\u0017\u001a\u00020\u0010X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0017\u0010R\"\u0004\bV\u0010TR\u001a\u0010\u0015\u001a\u00020\u0010X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0015\u0010R\"\u0004\bW\u0010TR\u001a\u0010\u000f\u001a\u00020\u0010X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000f\u0010R\"\u0004\bX\u0010TR\u001a\u0010\u0014\u001a\u00020\u0010X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0014\u0010R\"\u0004\bY\u0010TR\u001a\u0010\u0013\u001a\u00020\u0010X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0013\u0010R\"\u0004\bZ\u0010TR\u001a\u0010\u0012\u001a\u00020\u0010X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0012\u0010R\"\u0004\b[\u0010TR\u001c\u0010\\\u001a\u0004\u0018\u00010]X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b^\u0010_\"\u0004\b`\u0010aR\u001a\u0010\u000b\u001a\u00020\fX\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bb\u0010c\"\u0004\bd\u0010eR\u001a\u0010\u0007\u001a\u00020\bX\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bf\u0010g\"\u0004\bh\u0010iR\u001a\u0010\u0005\u001a\u00020\u0006X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bj\u0010k\"\u0004\bl\u0010mR\u001c\u0010n\u001a\u0004\u0018\u00010%X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bo\u0010'\"\u0004\bp\u0010)R \u0010q\u001a\b\u0012\u0004\u0012\u00020\u001c0\u001eX\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\br\u0010!\"\u0004\bs\u0010#R\u001a\u0010t\u001a\u00020uX\u0096.¢\u0006\u000e\n\u0000\u001a\u0004\bv\u0010w\"\u0004\bx\u0010yR\u001c\u0010\r\u001a\u00020\u000e8VX\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bz\u0010{\"\u0004\b|\u0010}R\u0014\u0010\u0002\u001a\u00020\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b~\u00106R\u0016\u0010\u007f\u001a\u00020\u001c8VX\u0096\u0004¢\u0006\b\u001a\u0006\b\u0080\u0001\u0010\u0081\u0001R$\u0010\u0082\u0001\u001a\t\u0012\u0005\u0012\u00030\u0083\u00010\u001eX\u0096\u000e¢\u0006\u0010\n\u0000\u001a\u0005\b\u0084\u0001\u0010!\"\u0005\b\u0085\u0001\u0010#R#\u0010\u0086\u0001\u001a\b\u0012\u0004\u0012\u00020F0\u001eX\u0096\u000e¢\u0006\u0010\n\u0000\u001a\u0005\b\u0087\u0001\u0010!\"\u0005\b\u0088\u0001\u0010#R\u001e\u0010\t\u001a\u00020\nX\u0096\u000e¢\u0006\u0012\n\u0000\u001a\u0006\b\u0089\u0001\u0010\u008a\u0001\"\u0006\b\u008b\u0001\u0010\u008c\u0001¨\u0006\u008e\u0001"}, d2 = {"Lorg/jetbrains/kotlin/ir/declarations/impl/IrFunctionWithLateBindingImpl;", "Lorg/jetbrains/kotlin/ir/declarations/IrFunctionWithLateBinding;", "startOffset", "", "endOffset", "origin", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationOrigin;", "name", "Lorg/jetbrains/kotlin/name/Name;", "visibility", "Lorg/jetbrains/kotlin/descriptors/DescriptorVisibility;", "modality", "Lorg/jetbrains/kotlin/descriptors/Modality;", "returnType", "Lorg/jetbrains/kotlin/ir/types/IrType;", "isInline", "", "isExternal", "isTailrec", "isSuspend", "isOperator", "isInfix", "isExpect", "isFakeOverride", "factory", "Lorg/jetbrains/kotlin/ir/declarations/IrFactory;", "(IILorg/jetbrains/kotlin/ir/declarations/IrDeclarationOrigin;Lorg/jetbrains/kotlin/name/Name;Lorg/jetbrains/kotlin/descriptors/DescriptorVisibility;Lorg/jetbrains/kotlin/descriptors/Modality;Lorg/jetbrains/kotlin/ir/types/IrType;ZZZZZZZZLorg/jetbrains/kotlin/ir/declarations/IrFactory;)V", "_symbol", "Lorg/jetbrains/kotlin/ir/symbols/IrSimpleFunctionSymbol;", "annotations", "", "Lorg/jetbrains/kotlin/ir/expressions/IrConstructorCall;", "getAnnotations", "()Ljava/util/List;", "setAnnotations", "(Ljava/util/List;)V", "attributeOwnerId", "Lorg/jetbrains/kotlin/ir/declarations/IrAttributeContainer;", "getAttributeOwnerId", "()Lorg/jetbrains/kotlin/ir/declarations/IrAttributeContainer;", "setAttributeOwnerId", "(Lorg/jetbrains/kotlin/ir/declarations/IrAttributeContainer;)V", "body", "Lorg/jetbrains/kotlin/ir/expressions/IrBody;", "getBody", "()Lorg/jetbrains/kotlin/ir/expressions/IrBody;", "setBody", "(Lorg/jetbrains/kotlin/ir/expressions/IrBody;)V", "containerSource", "Lorg/jetbrains/kotlin/serialization/deserialization/descriptors/DeserializedContainerSource;", "getContainerSource", "()Lorg/jetbrains/kotlin/serialization/deserialization/descriptors/DeserializedContainerSource;", "contextReceiverParametersCount", "getContextReceiverParametersCount", "()I", "setContextReceiverParametersCount", "(I)V", "correspondingPropertySymbol", "Lorg/jetbrains/kotlin/ir/symbols/IrPropertySymbol;", "getCorrespondingPropertySymbol", "()Lorg/jetbrains/kotlin/ir/symbols/IrPropertySymbol;", "setCorrespondingPropertySymbol", "(Lorg/jetbrains/kotlin/ir/symbols/IrPropertySymbol;)V", "descriptor", "Lorg/jetbrains/kotlin/descriptors/FunctionDescriptor;", "getDescriptor$annotations", "()V", "getDescriptor", "()Lorg/jetbrains/kotlin/descriptors/FunctionDescriptor;", "dispatchReceiverParameter", "Lorg/jetbrains/kotlin/ir/declarations/IrValueParameter;", "getDispatchReceiverParameter", "()Lorg/jetbrains/kotlin/ir/declarations/IrValueParameter;", "setDispatchReceiverParameter", "(Lorg/jetbrains/kotlin/ir/declarations/IrValueParameter;)V", "getEndOffset", "extensionReceiverParameter", "getExtensionReceiverParameter", "setExtensionReceiverParameter", "getFactory", "()Lorg/jetbrains/kotlin/ir/declarations/IrFactory;", "isBound", "()Z", "setExpect", "(Z)V", "setExternal", "setFakeOverride", "setInfix", "setInline", "setOperator", "setSuspend", "setTailrec", "metadata", "Lorg/jetbrains/kotlin/ir/declarations/MetadataSource;", "getMetadata", "()Lorg/jetbrains/kotlin/ir/declarations/MetadataSource;", "setMetadata", "(Lorg/jetbrains/kotlin/ir/declarations/MetadataSource;)V", "getModality", "()Lorg/jetbrains/kotlin/descriptors/Modality;", "setModality", "(Lorg/jetbrains/kotlin/descriptors/Modality;)V", "getName", "()Lorg/jetbrains/kotlin/name/Name;", "setName", "(Lorg/jetbrains/kotlin/name/Name;)V", "getOrigin", "()Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationOrigin;", "setOrigin", "(Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationOrigin;)V", "originalBeforeInline", "getOriginalBeforeInline", "setOriginalBeforeInline", "overriddenSymbols", "getOverriddenSymbols", "setOverriddenSymbols", "parent", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationParent;", "getParent", "()Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationParent;", "setParent", "(Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationParent;)V", "getReturnType", "()Lorg/jetbrains/kotlin/ir/types/IrType;", "setReturnType", "(Lorg/jetbrains/kotlin/ir/types/IrType;)V", "getStartOffset", NativeSymbol.TYPE_NAME, "getSymbol", "()Lorg/jetbrains/kotlin/ir/symbols/IrSimpleFunctionSymbol;", "typeParameters", "Lorg/jetbrains/kotlin/ir/declarations/IrTypeParameter;", "getTypeParameters", "setTypeParameters", "valueParameters", "getValueParameters", "setValueParameters", "getVisibility", "()Lorg/jetbrains/kotlin/descriptors/DescriptorVisibility;", "setVisibility", "(Lorg/jetbrains/kotlin/descriptors/DescriptorVisibility;)V", "acquireSymbol", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class IrFunctionWithLateBindingImpl extends IrFunctionWithLateBinding {
    private IrSimpleFunctionSymbol _symbol;
    private List<? extends IrConstructorCall> annotations;
    private IrAttributeContainer attributeOwnerId;
    private IrBody body;
    private int contextReceiverParametersCount;
    private IrPropertySymbol correspondingPropertySymbol;
    private IrValueParameter dispatchReceiverParameter;
    private final int endOffset;
    private IrValueParameter extensionReceiverParameter;
    private final IrFactory factory;
    private boolean isExpect;
    private boolean isExternal;
    private boolean isFakeOverride;
    private boolean isInfix;
    private boolean isInline;
    private boolean isOperator;
    private boolean isSuspend;
    private boolean isTailrec;
    private MetadataSource metadata;
    private Modality modality;
    private Name name;
    private IrDeclarationOrigin origin;
    private IrAttributeContainer originalBeforeInline;
    private List<? extends IrSimpleFunctionSymbol> overriddenSymbols;
    public IrDeclarationParent parent;
    private IrType returnType;
    private final int startOffset;
    private List<? extends IrTypeParameter> typeParameters;
    private List<? extends IrValueParameter> valueParameters;
    private DescriptorVisibility visibility;

    @ObsoleteDescriptorBasedAPI
    public static /* synthetic */ void getDescriptor$annotations() {
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrMemberWithContainerSource
    public DeserializedContainerSource getContainerSource() {
        return null;
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrSymbolOwner, org.jetbrains.kotlin.ir.declarations.IrReturnTarget, org.jetbrains.kotlin.ir.declarations.IrOverridableDeclaration
    public IrSimpleFunctionSymbol getSymbol() {
        IrSimpleFunctionSymbol irSimpleFunctionSymbol = this._symbol;
        if (irSimpleFunctionSymbol != null) {
            return irSimpleFunctionSymbol;
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

    @Override // org.jetbrains.kotlin.ir.declarations.IrSimpleFunction
    /* JADX INFO: renamed from: isTailrec, reason: from getter */
    public boolean getIsTailrec() {
        return this.isTailrec;
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrSimpleFunction
    public void setTailrec(boolean z) {
        this.isTailrec = z;
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrSimpleFunction
    /* JADX INFO: renamed from: isSuspend, reason: from getter */
    public boolean getIsSuspend() {
        return this.isSuspend;
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrSimpleFunction
    public void setSuspend(boolean z) {
        this.isSuspend = z;
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrSimpleFunction
    /* JADX INFO: renamed from: isOperator, reason: from getter */
    public boolean getIsOperator() {
        return this.isOperator;
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrSimpleFunction
    public void setOperator(boolean z) {
        this.isOperator = z;
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrSimpleFunction
    /* JADX INFO: renamed from: isInfix, reason: from getter */
    public boolean getIsInfix() {
        return this.isInfix;
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrSimpleFunction
    public void setInfix(boolean z) {
        this.isInfix = z;
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

    public /* synthetic */ IrFunctionWithLateBindingImpl(int i, int i2, IrDeclarationOrigin irDeclarationOrigin, Name name2, DescriptorVisibility descriptorVisibility, Modality modality, IrType irType, boolean z, boolean z2, boolean z3, boolean z4, boolean z5, boolean z6, boolean z7, boolean z8, IrFactory irFactory, int i3, DefaultConstructorMarker defaultConstructorMarker) {
        this(i, i2, irDeclarationOrigin, name2, descriptorVisibility, modality, irType, z, z2, z3, z4, z5, z6, z7, (i3 & 16384) != 0 ? Intrinsics.areEqual(irDeclarationOrigin, IrDeclarationOrigin.FAKE_OVERRIDE.INSTANCE) : z8, (i3 & 32768) != 0 ? IrFactoryImpl.INSTANCE : irFactory);
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrSimpleFunction, org.jetbrains.kotlin.ir.declarations.IrOverridableDeclaration
    /* JADX INFO: renamed from: isFakeOverride, reason: from getter */
    public boolean getIsFakeOverride() {
        return this.isFakeOverride;
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrSimpleFunction, org.jetbrains.kotlin.ir.declarations.IrOverridableDeclaration
    public void setFakeOverride(boolean z) {
        this.isFakeOverride = z;
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrDeclaration, org.jetbrains.kotlin.ir.declarations.lazy.IrLazyDeclarationBase
    public IrFactory getFactory() {
        return this.factory;
    }

    public IrFunctionWithLateBindingImpl(int i, int i2, IrDeclarationOrigin origin, Name name2, DescriptorVisibility visibility, Modality modality, IrType returnType, boolean z, boolean z2, boolean z3, boolean z4, boolean z5, boolean z6, boolean z7, boolean z8, IrFactory factory) {
        Intrinsics.checkNotNullParameter(origin, "origin");
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(visibility, "visibility");
        Intrinsics.checkNotNullParameter(modality, "modality");
        Intrinsics.checkNotNullParameter(returnType, "returnType");
        Intrinsics.checkNotNullParameter(factory, "factory");
        this.startOffset = i;
        this.endOffset = i2;
        this.origin = origin;
        this.name = name2;
        this.visibility = visibility;
        this.modality = modality;
        this.isInline = z;
        this.isExternal = z2;
        this.isTailrec = z3;
        this.isSuspend = z4;
        this.isOperator = z5;
        this.isInfix = z6;
        this.isExpect = z7;
        this.isFakeOverride = z8;
        this.factory = factory;
        this.annotations = CollectionsKt.emptyList();
        this.returnType = returnType;
        this.typeParameters = CollectionsKt.emptyList();
        this.valueParameters = CollectionsKt.emptyList();
        this.overriddenSymbols = CollectionsKt.emptyList();
        this.attributeOwnerId = this;
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrDeclaration
    public FunctionDescriptor getDescriptor() {
        FunctionDescriptor descriptor;
        IrSimpleFunctionSymbol irSimpleFunctionSymbol = this._symbol;
        return (irSimpleFunctionSymbol == null || (descriptor = irSimpleFunctionSymbol.getDescriptor()) == null) ? IrBasedDescriptorsKt.toIrBasedDescriptor((IrSimpleFunction) this) : descriptor;
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrFunctionWithLateBinding
    public IrFunctionWithLateBinding acquireSymbol(IrSimpleFunctionSymbol symbol) {
        Intrinsics.checkNotNullParameter(symbol, "symbol");
        this._symbol = symbol;
        symbol.bind(this);
        return this;
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrFunctionWithLateBinding
    public boolean isBound() {
        return this._symbol != null;
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
    public List<IrValueParameter> getValueParameters() {
        return this.valueParameters;
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrFunction
    public void setValueParameters(List<? extends IrValueParameter> list) {
        Intrinsics.checkNotNullParameter(list, "<set-?>");
        this.valueParameters = list;
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

    @Override // org.jetbrains.kotlin.ir.declarations.IrOverridableDeclaration
    public List<IrSimpleFunctionSymbol> getOverriddenSymbols() {
        return this.overriddenSymbols;
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrOverridableDeclaration
    public void setOverriddenSymbols(List<? extends IrSimpleFunctionSymbol> list) {
        Intrinsics.checkNotNullParameter(list, "<set-?>");
        this.overriddenSymbols = list;
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

    @Override // org.jetbrains.kotlin.ir.declarations.IrSimpleFunction
    public IrPropertySymbol getCorrespondingPropertySymbol() {
        return this.correspondingPropertySymbol;
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrSimpleFunction
    public void setCorrespondingPropertySymbol(IrPropertySymbol irPropertySymbol) {
        this.correspondingPropertySymbol = irPropertySymbol;
    }
}
