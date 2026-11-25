package org.jetbrains.kotlin.ir.declarations.lazy;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import kotlin.Lazy;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.MutablePropertyReference1Impl;
import kotlin.jvm.internal.Reflection;
import kotlin.properties.ReadWriteProperty;
import kotlin.reflect.KProperty;
import org.jetbrains.kotlin.descriptors.CallableMemberDescriptor;
import org.jetbrains.kotlin.descriptors.DescriptorVisibility;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.descriptors.Modality;
import org.jetbrains.kotlin.descriptors.TypeParameterDescriptor;
import org.jetbrains.kotlin.ir.declarations.IrAttributeContainer;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationOrigin;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationParent;
import org.jetbrains.kotlin.ir.declarations.IrFunction;
import org.jetbrains.kotlin.ir.declarations.IrTypeParameter;
import org.jetbrains.kotlin.ir.declarations.IrValueParameter;
import org.jetbrains.kotlin.ir.declarations.MetadataSource;
import org.jetbrains.kotlin.ir.expressions.IrBody;
import org.jetbrains.kotlin.ir.expressions.IrConstructorCall;
import org.jetbrains.kotlin.ir.symbols.IrPropertySymbol;
import org.jetbrains.kotlin.ir.symbols.IrSimpleFunctionSymbol;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.ir.util.DeclarationStubGenerator;
import org.jetbrains.kotlin.ir.util.SymbolTable;
import org.jetbrains.kotlin.ir.util.TypeTranslator;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.resolve.descriptorUtil.DescriptorUtilsKt;
import org.jetbrains.kotlin.serialization.deserialization.descriptors.DescriptorWithContainerSource;
import org.jetbrains.kotlin.serialization.deserialization.descriptors.DeserializedContainerSource;
import org.mozilla.javascript.NativeSymbol;

/* JADX INFO: compiled from: IrLazyFunction.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000¬\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\f\n\u0002\u0018\u0002\n\u0002\b\u000f\n\u0002\u0018\u0002\n\u0002\b\u0018\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0002\b\u000f\u0018\u00002\u00020\u00012\u00020\u0002B\u0095\u0001\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0004\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u0012\u0006\u0010\n\u001a\u00020\u000b\u0012\u0006\u0010\f\u001a\u00020\r\u0012\u0006\u0010\u000e\u001a\u00020\u000f\u0012\u0006\u0010\u0010\u001a\u00020\u0011\u0012\u0006\u0010\u0012\u001a\u00020\u0013\u0012\u0006\u0010\u0014\u001a\u00020\u0013\u0012\u0006\u0010\u0015\u001a\u00020\u0013\u0012\u0006\u0010\u0016\u001a\u00020\u0013\u0012\u0006\u0010\u0017\u001a\u00020\u0013\u0012\u0006\u0010\u0018\u001a\u00020\u0013\u0012\u0006\u0010\u0019\u001a\u00020\u0013\u0012\u0006\u0010\u001a\u001a\u00020\u0013\u0012\u0006\u0010\u001b\u001a\u00020\u001c\u0012\u0006\u0010\u001d\u001a\u00020\u001e¢\u0006\u0002\u0010\u001fR7\u0010#\u001a\b\u0012\u0004\u0012\u00020\"0!2\f\u0010 \u001a\b\u0012\u0004\u0012\u00020\"0!8V@VX\u0096\u008e\u0002¢\u0006\u0012\n\u0004\b(\u0010)\u001a\u0004\b$\u0010%\"\u0004\b&\u0010'R$\u0010,\u001a\u00020+2\u0006\u0010*\u001a\u00020+8V@VX\u0096\u000e¢\u0006\f\u001a\u0004\b-\u0010.\"\u0004\b/\u00100R/\u00102\u001a\u0004\u0018\u0001012\b\u0010 \u001a\u0004\u0018\u0001018V@VX\u0096\u008e\u0002¢\u0006\u0012\n\u0004\b7\u0010)\u001a\u0004\b3\u00104\"\u0004\b5\u00106R\u0016\u00108\u001a\u0004\u0018\u0001098VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b:\u0010;R\u001a\u0010<\u001a\u00020\u0004X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b=\u0010>\"\u0004\b?\u0010@R\u001c\u0010A\u001a\u0004\u0018\u00010BX\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bC\u0010D\"\u0004\bE\u0010FR\u0014\u0010\n\u001a\u00020\u000bX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\bG\u0010HR/\u0010J\u001a\u0004\u0018\u00010I2\b\u0010 \u001a\u0004\u0018\u00010I8V@VX\u0096\u008e\u0002¢\u0006\u0012\n\u0004\bO\u0010)\u001a\u0004\bK\u0010L\"\u0004\bM\u0010NR\u0014\u0010\u0005\u001a\u00020\u0004X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\bP\u0010>R/\u0010Q\u001a\u0004\u0018\u00010I2\b\u0010 \u001a\u0004\u0018\u00010I8V@VX\u0096\u008e\u0002¢\u0006\u0012\n\u0004\bT\u0010)\u001a\u0004\bR\u0010L\"\u0004\bS\u0010NR\u001d\u0010U\u001a\u0004\u0018\u00010V8VX\u0096\u0084\u0002¢\u0006\f\n\u0004\bY\u0010Z\u001a\u0004\bW\u0010XR\u0014\u0010[\u001a\u00020\u00138VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b[\u0010\\R\u001a\u0010\u0017\u001a\u00020\u0013X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0017\u0010\\\"\u0004\b]\u0010^R\u001a\u0010\u0014\u001a\u00020\u0013X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0014\u0010\\\"\u0004\b_\u0010^R\u001a\u0010\u0018\u001a\u00020\u0013X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0018\u0010\\\"\u0004\b`\u0010^R\u001a\u0010\u001a\u001a\u00020\u0013X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001a\u0010\\\"\u0004\ba\u0010^R\u001a\u0010\u0012\u001a\u00020\u0013X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0012\u0010\\\"\u0004\bb\u0010^R\u001a\u0010\u0019\u001a\u00020\u0013X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0019\u0010\\\"\u0004\bc\u0010^R\u001a\u0010\u0016\u001a\u00020\u0013X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0016\u0010\\\"\u0004\bd\u0010^R\u001a\u0010\u0015\u001a\u00020\u0013X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0015\u0010\\\"\u0004\be\u0010^R(\u0010g\u001a\u0004\u0018\u00010f2\b\u0010*\u001a\u0004\u0018\u00010f8V@VX\u0096\u000e¢\u0006\f\u001a\u0004\bh\u0010i\"\u0004\bj\u0010kR\u001a\u0010\u0010\u001a\u00020\u0011X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bl\u0010m\"\u0004\bn\u0010oR\u001a\u0010\f\u001a\u00020\rX\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bp\u0010q\"\u0004\br\u0010sR\u001a\u0010\u0006\u001a\u00020\u0007X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bt\u0010u\"\u0004\bv\u0010wR(\u0010x\u001a\u0004\u0018\u00010+2\b\u0010*\u001a\u0004\u0018\u00010+8V@VX\u0096\u000e¢\u0006\f\u001a\u0004\by\u0010.\"\u0004\bz\u00100R7\u0010{\u001a\b\u0012\u0004\u0012\u00020\t0!2\f\u0010 \u001a\b\u0012\u0004\u0012\u00020\t0!8V@VX\u0096\u008e\u0002¢\u0006\u0012\n\u0004\b~\u0010)\u001a\u0004\b|\u0010%\"\u0004\b}\u0010'R1\u0010\u0080\u0001\u001a\u00020\u007f2\u0006\u0010 \u001a\u00020\u007f8V@VX\u0096\u008e\u0002¢\u0006\u0017\n\u0005\b\u0085\u0001\u0010)\u001a\u0006\b\u0081\u0001\u0010\u0082\u0001\"\u0006\b\u0083\u0001\u0010\u0084\u0001R3\u0010\u0087\u0001\u001a\u00030\u0086\u00012\u0007\u0010 \u001a\u00030\u0086\u00018V@VX\u0096\u008e\u0002¢\u0006\u0017\n\u0005\b\u008c\u0001\u0010)\u001a\u0006\b\u0088\u0001\u0010\u0089\u0001\"\u0006\b\u008a\u0001\u0010\u008b\u0001R\u0015\u0010\u0003\u001a\u00020\u0004X\u0096\u0004¢\u0006\t\n\u0000\u001a\u0005\b\u008d\u0001\u0010>R\u0016\u0010\u001b\u001a\u00020\u001cX\u0096\u0004¢\u0006\n\n\u0000\u001a\u0006\b\u008e\u0001\u0010\u008f\u0001R\u0016\u0010\b\u001a\u00020\tX\u0096\u0004¢\u0006\n\n\u0000\u001a\u0006\b\u0090\u0001\u0010\u0091\u0001R=\u0010\u0093\u0001\u001a\t\u0012\u0005\u0012\u00030\u0092\u00010!2\r\u0010 \u001a\t\u0012\u0005\u0012\u00030\u0092\u00010!8V@VX\u0096\u008e\u0002¢\u0006\u0015\n\u0005\b\u0096\u0001\u0010)\u001a\u0005\b\u0094\u0001\u0010%\"\u0005\b\u0095\u0001\u0010'R\u0016\u0010\u001d\u001a\u00020\u001eX\u0096\u0004¢\u0006\n\n\u0000\u001a\u0006\b\u0097\u0001\u0010\u0098\u0001R;\u0010\u0099\u0001\u001a\b\u0012\u0004\u0012\u00020I0!2\f\u0010 \u001a\b\u0012\u0004\u0012\u00020I0!8V@VX\u0096\u008e\u0002¢\u0006\u0015\n\u0005\b\u009c\u0001\u0010)\u001a\u0005\b\u009a\u0001\u0010%\"\u0005\b\u009b\u0001\u0010'R\u001e\u0010\u000e\u001a\u00020\u000fX\u0096\u000e¢\u0006\u0012\n\u0000\u001a\u0006\b\u009d\u0001\u0010\u009e\u0001\"\u0006\b\u009f\u0001\u0010 \u0001¨\u0006¡\u0001"}, d2 = {"Lorg/jetbrains/kotlin/ir/declarations/lazy/IrLazyFunction;", "Lorg/jetbrains/kotlin/ir/declarations/lazy/AbstractIrLazyFunction;", "Lorg/jetbrains/kotlin/ir/declarations/lazy/IrLazyFunctionBase;", "startOffset", "", "endOffset", "origin", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationOrigin;", NativeSymbol.TYPE_NAME, "Lorg/jetbrains/kotlin/ir/symbols/IrSimpleFunctionSymbol;", "descriptor", "Lorg/jetbrains/kotlin/descriptors/FunctionDescriptor;", "name", "Lorg/jetbrains/kotlin/name/Name;", "visibility", "Lorg/jetbrains/kotlin/descriptors/DescriptorVisibility;", "modality", "Lorg/jetbrains/kotlin/descriptors/Modality;", "isInline", "", "isExternal", "isTailrec", "isSuspend", "isExpect", "isFakeOverride", "isOperator", "isInfix", "stubGenerator", "Lorg/jetbrains/kotlin/ir/util/DeclarationStubGenerator;", "typeTranslator", "Lorg/jetbrains/kotlin/ir/util/TypeTranslator;", "(IILorg/jetbrains/kotlin/ir/declarations/IrDeclarationOrigin;Lorg/jetbrains/kotlin/ir/symbols/IrSimpleFunctionSymbol;Lorg/jetbrains/kotlin/descriptors/FunctionDescriptor;Lorg/jetbrains/kotlin/name/Name;Lorg/jetbrains/kotlin/descriptors/DescriptorVisibility;Lorg/jetbrains/kotlin/descriptors/Modality;ZZZZZZZZLorg/jetbrains/kotlin/ir/util/DeclarationStubGenerator;Lorg/jetbrains/kotlin/ir/util/TypeTranslator;)V", "<set-?>", "", "Lorg/jetbrains/kotlin/ir/expressions/IrConstructorCall;", "annotations", "getAnnotations", "()Ljava/util/List;", "setAnnotations", "(Ljava/util/List;)V", "annotations$delegate", "Lkotlin/properties/ReadWriteProperty;", "<anonymous parameter 0>", "Lorg/jetbrains/kotlin/ir/declarations/IrAttributeContainer;", "attributeOwnerId", "getAttributeOwnerId", "()Lorg/jetbrains/kotlin/ir/declarations/IrAttributeContainer;", "setAttributeOwnerId", "(Lorg/jetbrains/kotlin/ir/declarations/IrAttributeContainer;)V", "Lorg/jetbrains/kotlin/ir/expressions/IrBody;", "body", "getBody", "()Lorg/jetbrains/kotlin/ir/expressions/IrBody;", "setBody", "(Lorg/jetbrains/kotlin/ir/expressions/IrBody;)V", "body$delegate", "containerSource", "Lorg/jetbrains/kotlin/serialization/deserialization/descriptors/DeserializedContainerSource;", "getContainerSource", "()Lorg/jetbrains/kotlin/serialization/deserialization/descriptors/DeserializedContainerSource;", "contextReceiverParametersCount", "getContextReceiverParametersCount", "()I", "setContextReceiverParametersCount", "(I)V", "correspondingPropertySymbol", "Lorg/jetbrains/kotlin/ir/symbols/IrPropertySymbol;", "getCorrespondingPropertySymbol", "()Lorg/jetbrains/kotlin/ir/symbols/IrPropertySymbol;", "setCorrespondingPropertySymbol", "(Lorg/jetbrains/kotlin/ir/symbols/IrPropertySymbol;)V", "getDescriptor", "()Lorg/jetbrains/kotlin/descriptors/FunctionDescriptor;", "Lorg/jetbrains/kotlin/ir/declarations/IrValueParameter;", "dispatchReceiverParameter", "getDispatchReceiverParameter", "()Lorg/jetbrains/kotlin/ir/declarations/IrValueParameter;", "setDispatchReceiverParameter", "(Lorg/jetbrains/kotlin/ir/declarations/IrValueParameter;)V", "dispatchReceiverParameter$delegate", "getEndOffset", "extensionReceiverParameter", "getExtensionReceiverParameter", "setExtensionReceiverParameter", "extensionReceiverParameter$delegate", "initialSignatureFunction", "Lorg/jetbrains/kotlin/ir/declarations/IrFunction;", "getInitialSignatureFunction", "()Lorg/jetbrains/kotlin/ir/declarations/IrFunction;", "initialSignatureFunction$delegate", "Lkotlin/Lazy;", "isDeserializationEnabled", "()Z", "setExpect", "(Z)V", "setExternal", "setFakeOverride", "setInfix", "setInline", "setOperator", "setSuspend", "setTailrec", "Lorg/jetbrains/kotlin/ir/declarations/MetadataSource;", "metadata", "getMetadata", "()Lorg/jetbrains/kotlin/ir/declarations/MetadataSource;", "setMetadata", "(Lorg/jetbrains/kotlin/ir/declarations/MetadataSource;)V", "getModality", "()Lorg/jetbrains/kotlin/descriptors/Modality;", "setModality", "(Lorg/jetbrains/kotlin/descriptors/Modality;)V", "getName", "()Lorg/jetbrains/kotlin/name/Name;", "setName", "(Lorg/jetbrains/kotlin/name/Name;)V", "getOrigin", "()Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationOrigin;", "setOrigin", "(Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationOrigin;)V", "originalBeforeInline", "getOriginalBeforeInline", "setOriginalBeforeInline", "overriddenSymbols", "getOverriddenSymbols", "setOverriddenSymbols", "overriddenSymbols$delegate", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationParent;", "parent", "getParent", "()Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationParent;", "setParent", "(Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationParent;)V", "parent$delegate", "Lorg/jetbrains/kotlin/ir/types/IrType;", "returnType", "getReturnType", "()Lorg/jetbrains/kotlin/ir/types/IrType;", "setReturnType", "(Lorg/jetbrains/kotlin/ir/types/IrType;)V", "returnType$delegate", "getStartOffset", "getStubGenerator", "()Lorg/jetbrains/kotlin/ir/util/DeclarationStubGenerator;", "getSymbol", "()Lorg/jetbrains/kotlin/ir/symbols/IrSimpleFunctionSymbol;", "Lorg/jetbrains/kotlin/ir/declarations/IrTypeParameter;", "typeParameters", "getTypeParameters", "setTypeParameters", "typeParameters$delegate", "getTypeTranslator", "()Lorg/jetbrains/kotlin/ir/util/TypeTranslator;", "valueParameters", "getValueParameters", "setValueParameters", "valueParameters$delegate", "getVisibility", "()Lorg/jetbrains/kotlin/descriptors/DescriptorVisibility;", "setVisibility", "(Lorg/jetbrains/kotlin/descriptors/DescriptorVisibility;)V", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class IrLazyFunction extends AbstractIrLazyFunction implements IrLazyFunctionBase {
    static final /* synthetic */ KProperty<Object>[] $$delegatedProperties = {Reflection.mutableProperty1(new MutablePropertyReference1Impl(Reflection.getOrCreateKotlinClass(IrLazyFunction.class), "parent", "getParent()Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationParent;")), Reflection.mutableProperty1(new MutablePropertyReference1Impl(Reflection.getOrCreateKotlinClass(IrLazyFunction.class), "annotations", "getAnnotations()Ljava/util/List;")), Reflection.mutableProperty1(new MutablePropertyReference1Impl(Reflection.getOrCreateKotlinClass(IrLazyFunction.class), "body", "getBody()Lorg/jetbrains/kotlin/ir/expressions/IrBody;")), Reflection.mutableProperty1(new MutablePropertyReference1Impl(Reflection.getOrCreateKotlinClass(IrLazyFunction.class), "returnType", "getReturnType()Lorg/jetbrains/kotlin/ir/types/IrType;")), Reflection.mutableProperty1(new MutablePropertyReference1Impl(Reflection.getOrCreateKotlinClass(IrLazyFunction.class), "dispatchReceiverParameter", "getDispatchReceiverParameter()Lorg/jetbrains/kotlin/ir/declarations/IrValueParameter;")), Reflection.mutableProperty1(new MutablePropertyReference1Impl(Reflection.getOrCreateKotlinClass(IrLazyFunction.class), "extensionReceiverParameter", "getExtensionReceiverParameter()Lorg/jetbrains/kotlin/ir/declarations/IrValueParameter;")), Reflection.mutableProperty1(new MutablePropertyReference1Impl(Reflection.getOrCreateKotlinClass(IrLazyFunction.class), "valueParameters", "getValueParameters()Ljava/util/List;")), Reflection.mutableProperty1(new MutablePropertyReference1Impl(Reflection.getOrCreateKotlinClass(IrLazyFunction.class), "typeParameters", "getTypeParameters()Ljava/util/List;")), Reflection.mutableProperty1(new MutablePropertyReference1Impl(Reflection.getOrCreateKotlinClass(IrLazyFunction.class), "overriddenSymbols", "getOverriddenSymbols()Ljava/util/List;"))};

    /* JADX INFO: renamed from: annotations$delegate, reason: from kotlin metadata */
    private final ReadWriteProperty annotations;

    /* JADX INFO: renamed from: body$delegate, reason: from kotlin metadata */
    private final ReadWriteProperty body;
    private int contextReceiverParametersCount;
    private IrPropertySymbol correspondingPropertySymbol;
    private final FunctionDescriptor descriptor;

    /* JADX INFO: renamed from: dispatchReceiverParameter$delegate, reason: from kotlin metadata */
    private final ReadWriteProperty dispatchReceiverParameter;
    private final int endOffset;

    /* JADX INFO: renamed from: extensionReceiverParameter$delegate, reason: from kotlin metadata */
    private final ReadWriteProperty extensionReceiverParameter;

    /* JADX INFO: renamed from: initialSignatureFunction$delegate, reason: from kotlin metadata */
    private final Lazy initialSignatureFunction;
    private boolean isExpect;
    private boolean isExternal;
    private boolean isFakeOverride;
    private boolean isInfix;
    private boolean isInline;
    private boolean isOperator;
    private boolean isSuspend;
    private boolean isTailrec;
    private Modality modality;
    private Name name;
    private IrDeclarationOrigin origin;

    /* JADX INFO: renamed from: overriddenSymbols$delegate, reason: from kotlin metadata */
    private final ReadWriteProperty overriddenSymbols;

    /* JADX INFO: renamed from: parent$delegate, reason: from kotlin metadata */
    private final ReadWriteProperty parent;

    /* JADX INFO: renamed from: returnType$delegate, reason: from kotlin metadata */
    private final ReadWriteProperty returnType;
    private final int startOffset;
    private final DeclarationStubGenerator stubGenerator;
    private final IrSimpleFunctionSymbol symbol;

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

    @Override // org.jetbrains.kotlin.ir.declarations.IrAttributeContainer
    public IrAttributeContainer getOriginalBeforeInline() {
        return null;
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
    public IrSimpleFunctionSymbol getSymbol() {
        return this.symbol;
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrDeclaration
    public FunctionDescriptor getDescriptor() {
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

    @Override // org.jetbrains.kotlin.ir.declarations.IrFunction
    /* JADX INFO: renamed from: isExpect, reason: from getter */
    public boolean getIsExpect() {
        return this.isExpect;
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrFunction
    public void setExpect(boolean z) {
        this.isExpect = z;
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

    @Override // org.jetbrains.kotlin.ir.declarations.lazy.IrLazyDeclarationBase
    public DeclarationStubGenerator getStubGenerator() {
        return this.stubGenerator;
    }

    @Override // org.jetbrains.kotlin.ir.declarations.lazy.IrLazyDeclarationBase
    public TypeTranslator getTypeTranslator() {
        return this.typeTranslator;
    }

    public IrLazyFunction(int i, int i2, IrDeclarationOrigin origin, IrSimpleFunctionSymbol symbol, FunctionDescriptor descriptor, Name name2, DescriptorVisibility visibility, Modality modality, boolean z, boolean z2, boolean z3, boolean z4, boolean z5, boolean z6, boolean z7, boolean z8, DeclarationStubGenerator stubGenerator, TypeTranslator typeTranslator) {
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
        this.isInline = z;
        this.isExternal = z2;
        this.isTailrec = z3;
        this.isSuspend = z4;
        this.isExpect = z5;
        this.isFakeOverride = z6;
        this.isOperator = z7;
        this.isInfix = z8;
        this.stubGenerator = stubGenerator;
        this.typeTranslator = typeTranslator;
        this.parent = createLazyParent();
        this.annotations = createLazyAnnotations();
        this.body = LazyUtilKt.lazyVar(getStubGenerator().getLock(), new Function0<IrBody>() { // from class: org.jetbrains.kotlin.ir.declarations.lazy.IrLazyFunction$body$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final IrBody invoke() {
                if (this.this$0.tryLoadIr()) {
                    return this.this$0.getBody();
                }
                return null;
            }
        });
        this.returnType = LazyUtilKt.lazyVar(getStubGenerator().getLock(), new Function0<IrType>() { // from class: org.jetbrains.kotlin.ir.declarations.lazy.IrLazyFunction$returnType$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final IrType invoke() {
                return this.this$0.tryLoadIr() ? this.this$0.getReturnType() : this.this$0.createReturnType();
            }
        });
        this.initialSignatureFunction = createInitialSignatureFunction();
        this.dispatchReceiverParameter = LazyUtilKt.lazyVar(getStubGenerator().getLock(), new Function0<IrValueParameter>() { // from class: org.jetbrains.kotlin.ir.declarations.lazy.IrLazyFunction$dispatchReceiverParameter$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final IrValueParameter invoke() {
                if (this.this$0.tryLoadIr()) {
                    return this.this$0.getDispatchReceiverParameter();
                }
                IrLazyFunction irLazyFunction = this.this$0;
                return irLazyFunction.createReceiverParameter(irLazyFunction.getDescriptor().mo6753getDispatchReceiverParameter(), true);
            }
        });
        this.extensionReceiverParameter = LazyUtilKt.lazyVar(getStubGenerator().getLock(), new Function0<IrValueParameter>() { // from class: org.jetbrains.kotlin.ir.declarations.lazy.IrLazyFunction$extensionReceiverParameter$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final IrValueParameter invoke() {
                if (this.this$0.tryLoadIr()) {
                    return this.this$0.getExtensionReceiverParameter();
                }
                IrLazyFunction irLazyFunction = this.this$0;
                return IrLazyFunctionBase.DefaultImpls.createReceiverParameter$default(irLazyFunction, irLazyFunction.getDescriptor().getExtensionReceiverParameter(), false, 2, null);
            }
        });
        this.valueParameters = LazyUtilKt.lazyVar(getStubGenerator().getLock(), new Function0<List<? extends IrValueParameter>>() { // from class: org.jetbrains.kotlin.ir.declarations.lazy.IrLazyFunction$valueParameters$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final List<? extends IrValueParameter> invoke() {
                return this.this$0.tryLoadIr() ? this.this$0.getValueParameters() : this.this$0.createValueParameters();
            }
        });
        this.contextReceiverParametersCount = getDescriptor().getContextReceiverParameters().size();
        this.typeParameters = LazyUtilKt.lazyVar(getStubGenerator().getLock(), new Function0<List<? extends IrTypeParameter>>() { // from class: org.jetbrains.kotlin.ir.declarations.lazy.IrLazyFunction$typeParameters$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final List<? extends IrTypeParameter> invoke() {
                if (this.this$0.tryLoadIr()) {
                    return this.this$0.getTypeParameters();
                }
                TypeTranslator typeTranslator2 = this.this$0.getTypeTranslator();
                IrLazyFunction irLazyFunction = this.this$0;
                final IrLazyFunction irLazyFunction2 = this.this$0;
                return (List) typeTranslator2.buildWithScope(irLazyFunction, new Function0<ArrayList<IrTypeParameter>>() { // from class: org.jetbrains.kotlin.ir.declarations.lazy.IrLazyFunction$typeParameters$2.1
                    {
                        super(0);
                    }

                    @Override // kotlin.jvm.functions.Function0
                    public final ArrayList<IrTypeParameter> invoke() {
                        IrTypeParameter irTypeParameterGenerateOrGetTypeParameterStub$ir_tree;
                        SymbolTable symbolTable = irLazyFunction2.getStubGenerator().getSymbolTable();
                        IrLazyFunction irLazyFunction3 = irLazyFunction2;
                        IrLazyFunction irLazyFunction4 = irLazyFunction3;
                        symbolTable.enterScope(irLazyFunction4);
                        CallableMemberDescriptor propertyIfAccessor = DescriptorUtilsKt.getPropertyIfAccessor(irLazyFunction3.getDescriptor());
                        List<TypeParameterDescriptor> typeParameters = propertyIfAccessor.getTypeParameters();
                        Intrinsics.checkNotNullExpressionValue(typeParameters, "getTypeParameters(...)");
                        ArrayList<IrTypeParameter> arrayList = new ArrayList<>();
                        for (TypeParameterDescriptor typeParameterDescriptor : typeParameters) {
                            if (!Intrinsics.areEqual(irLazyFunction3.getDescriptor(), propertyIfAccessor)) {
                                DeclarationStubGenerator stubGenerator2 = irLazyFunction3.getStubGenerator();
                                Intrinsics.checkNotNull(typeParameterDescriptor);
                                irTypeParameterGenerateOrGetTypeParameterStub$ir_tree = stubGenerator2.generateOrGetScopedTypeParameterStub$ir_tree(typeParameterDescriptor);
                                irTypeParameterGenerateOrGetTypeParameterStub$ir_tree.setParent(irLazyFunction3);
                            } else {
                                DeclarationStubGenerator stubGenerator3 = irLazyFunction3.getStubGenerator();
                                Intrinsics.checkNotNull(typeParameterDescriptor);
                                irTypeParameterGenerateOrGetTypeParameterStub$ir_tree = stubGenerator3.generateOrGetTypeParameterStub$ir_tree(typeParameterDescriptor);
                            }
                            arrayList.add(irTypeParameterGenerateOrGetTypeParameterStub$ir_tree);
                        }
                        ArrayList<IrTypeParameter> arrayList2 = arrayList;
                        symbolTable.leaveScope(irLazyFunction4);
                        return arrayList2;
                    }
                });
            }
        });
        this.overriddenSymbols = LazyUtilKt.lazyVar(getStubGenerator().getLock(), new Function0<List<? extends IrSimpleFunctionSymbol>>() { // from class: org.jetbrains.kotlin.ir.declarations.lazy.IrLazyFunction$overriddenSymbols$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final List<? extends IrSimpleFunctionSymbol> invoke() {
                Collection<? extends FunctionDescriptor> overriddenDescriptors = this.this$0.getDescriptor().getOverriddenDescriptors();
                Intrinsics.checkNotNullExpressionValue(overriddenDescriptors, "getOverriddenDescriptors(...)");
                ArrayList arrayList = new ArrayList();
                IrLazyFunction irLazyFunction = this.this$0;
                for (FunctionDescriptor functionDescriptor : overriddenDescriptors) {
                    DeclarationStubGenerator stubGenerator2 = irLazyFunction.getStubGenerator();
                    FunctionDescriptor original = functionDescriptor.getOriginal();
                    Intrinsics.checkNotNullExpressionValue(original, "getOriginal(...)");
                    arrayList.add(DeclarationStubGenerator.generateFunctionStub$default(stubGenerator2, original, false, 2, null).getSymbol());
                }
                return arrayList;
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
        return (IrBody) this.body.getValue(this, $$delegatedProperties[2]);
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrFunction
    public void setBody(IrBody irBody) {
        this.body.setValue(this, $$delegatedProperties[2], irBody);
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrFunction
    public IrType getReturnType() {
        return (IrType) this.returnType.getValue(this, $$delegatedProperties[3]);
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrFunction
    public void setReturnType(IrType irType) {
        Intrinsics.checkNotNullParameter(irType, "<set-?>");
        this.returnType.setValue(this, $$delegatedProperties[3], irType);
    }

    @Override // org.jetbrains.kotlin.ir.declarations.lazy.IrLazyFunctionBase
    public IrFunction getInitialSignatureFunction() {
        return (IrFunction) this.initialSignatureFunction.getValue();
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrFunction
    public IrValueParameter getDispatchReceiverParameter() {
        return (IrValueParameter) this.dispatchReceiverParameter.getValue(this, $$delegatedProperties[4]);
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrFunction
    public void setDispatchReceiverParameter(IrValueParameter irValueParameter) {
        this.dispatchReceiverParameter.setValue(this, $$delegatedProperties[4], irValueParameter);
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrFunction
    public IrValueParameter getExtensionReceiverParameter() {
        return (IrValueParameter) this.extensionReceiverParameter.getValue(this, $$delegatedProperties[5]);
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrFunction
    public void setExtensionReceiverParameter(IrValueParameter irValueParameter) {
        this.extensionReceiverParameter.setValue(this, $$delegatedProperties[5], irValueParameter);
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrFunction
    public List<IrValueParameter> getValueParameters() {
        return (List) this.valueParameters.getValue(this, $$delegatedProperties[6]);
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrFunction
    public void setValueParameters(List<? extends IrValueParameter> list) {
        Intrinsics.checkNotNullParameter(list, "<set-?>");
        this.valueParameters.setValue(this, $$delegatedProperties[6], list);
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
        return (List) this.typeParameters.getValue(this, $$delegatedProperties[7]);
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrTypeParametersContainer
    public void setTypeParameters(List<? extends IrTypeParameter> list) {
        Intrinsics.checkNotNullParameter(list, "<set-?>");
        this.typeParameters.setValue(this, $$delegatedProperties[7], list);
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrOverridableDeclaration
    public List<IrSimpleFunctionSymbol> getOverriddenSymbols() {
        return (List) this.overriddenSymbols.getValue(this, $$delegatedProperties[8]);
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrOverridableDeclaration
    public void setOverriddenSymbols(List<? extends IrSimpleFunctionSymbol> list) {
        Intrinsics.checkNotNullParameter(list, "<set-?>");
        this.overriddenSymbols.setValue(this, $$delegatedProperties[8], list);
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
    public void setOriginalBeforeInline(IrAttributeContainer irAttributeContainer) {
        throw new IllegalStateException("We should never need to change originalBeforeInline of external declarations.".toString());
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrSimpleFunction
    public IrPropertySymbol getCorrespondingPropertySymbol() {
        return this.correspondingPropertySymbol;
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrSimpleFunction
    public void setCorrespondingPropertySymbol(IrPropertySymbol irPropertySymbol) {
        this.correspondingPropertySymbol = irPropertySymbol;
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrMemberWithContainerSource
    public DeserializedContainerSource getContainerSource() {
        FunctionDescriptor descriptor = getDescriptor();
        DescriptorWithContainerSource descriptorWithContainerSource = descriptor instanceof DescriptorWithContainerSource ? (DescriptorWithContainerSource) descriptor : null;
        if (descriptorWithContainerSource != null) {
            return descriptorWithContainerSource.getContainerSource();
        }
        return null;
    }

    @Override // org.jetbrains.kotlin.ir.declarations.lazy.AbstractIrLazyFunction
    public boolean isDeserializationEnabled() {
        return getStubGenerator().getExtensions().getIrDeserializationEnabled();
    }
}
