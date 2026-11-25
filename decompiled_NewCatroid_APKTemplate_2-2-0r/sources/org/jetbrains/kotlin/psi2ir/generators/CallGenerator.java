package org.jetbrains.kotlin.psi2ir.generators;

import io.netty.util.internal.StringUtil;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.NotImplementedError;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.codegen.AsmUtil;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.descriptors.ClassConstructorDescriptor;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.ClassKind;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.descriptors.PropertyDescriptor;
import org.jetbrains.kotlin.descriptors.PropertyGetterDescriptor;
import org.jetbrains.kotlin.descriptors.ScriptDescriptor;
import org.jetbrains.kotlin.descriptors.SimpleFunctionDescriptor;
import org.jetbrains.kotlin.descriptors.TypeAliasDescriptor;
import org.jetbrains.kotlin.descriptors.TypeParameterDescriptor;
import org.jetbrains.kotlin.descriptors.ValueParameterDescriptor;
import org.jetbrains.kotlin.descriptors.VariableDescriptor;
import org.jetbrains.kotlin.descriptors.impl.LocalVariableAccessorDescriptor;
import org.jetbrains.kotlin.descriptors.impl.LocalVariableDescriptor;
import org.jetbrains.kotlin.descriptors.impl.SyntheticFieldDescriptor;
import org.jetbrains.kotlin.ir.expressions.IrDeclarationReference;
import org.jetbrains.kotlin.ir.expressions.IrDynamicOperator;
import org.jetbrains.kotlin.ir.expressions.IrExpression;
import org.jetbrains.kotlin.ir.expressions.IrExpressionsKt;
import org.jetbrains.kotlin.ir.expressions.IrFunctionAccessExpression;
import org.jetbrains.kotlin.ir.expressions.IrStatementOrigin;
import org.jetbrains.kotlin.ir.expressions.IrTypeOperator;
import org.jetbrains.kotlin.ir.expressions.impl.IrBlockImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrCallImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrConstructorCallImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrDelegatingConstructorCallImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrDynamicMemberExpressionImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrDynamicOperatorExpressionImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrEnumConstructorCallImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrGetFieldImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrGetValueImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrTypeOperatorCallImpl;
import org.jetbrains.kotlin.ir.symbols.IrClassSymbol;
import org.jetbrains.kotlin.ir.symbols.IrConstructorSymbol;
import org.jetbrains.kotlin.ir.symbols.IrFieldSymbol;
import org.jetbrains.kotlin.ir.symbols.IrSimpleFunctionSymbol;
import org.jetbrains.kotlin.ir.symbols.IrValueParameterSymbol;
import org.jetbrains.kotlin.ir.types.IrDynamicType;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.ir.util.DescriptorSymbolTableExtension;
import org.jetbrains.kotlin.ir.util.DumpIrTreeOptions;
import org.jetbrains.kotlin.ir.util.RenderIrElementKt;
import org.jetbrains.kotlin.psi2ir.KotlinUtilsKt;
import org.jetbrains.kotlin.psi2ir.generators.fragments.FragmentContext;
import org.jetbrains.kotlin.psi2ir.intermediate.CallBuilder;
import org.jetbrains.kotlin.psi2ir.intermediate.CallBuilderKt;
import org.jetbrains.kotlin.psi2ir.intermediate.CallExpressionBuilder;
import org.jetbrains.kotlin.psi2ir.intermediate.CallReceiver;
import org.jetbrains.kotlin.psi2ir.intermediate.IntermediateValue;
import org.jetbrains.kotlin.psi2ir.intermediate.IrUtilsKt;
import org.jetbrains.kotlin.psi2ir.intermediate.RematerializableValueKt;
import org.jetbrains.kotlin.psi2ir.intermediate.SimpleCallReceiver;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedCall;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedValueArgument;
import org.jetbrains.kotlin.resolve.calls.util.FakeCallableDescriptorForObject;
import org.jetbrains.kotlin.resolve.descriptorUtil.DescriptorUtilsKt;
import org.jetbrains.kotlin.types.KotlinType;

/* JADX INFO: compiled from: CallGenerator.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u008e\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\b\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J>\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\b2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000f2\f\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00060\u0011H\u0002J*\u0010\u0012\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\b2\u0006\u0010\n\u001a\u00020\u000b2\n\b\u0002\u0010\u0013\u001a\u0004\u0018\u00010\u0014J8\u0010\u0015\u001a\u00020\u00062\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\b2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0016\u001a\u00020\bH\u0002J2\u0010\u0017\u001a\u00020\u00062\u0006\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\b2\b\u0010\u0013\u001a\u0004\u0018\u00010\u00142\u0006\u0010\n\u001a\u00020\u000bH\u0002J\u001e\u0010\u001a\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\b2\u0006\u0010\n\u001a\u00020\u000bJ<\u0010\u001b\u001a\u00020\u001c2\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\b2\u0006\u0010\u001d\u001a\u00020\u001e2\u0006\u0010\n\u001a\u00020\u000b2\b\u0010\u001f\u001a\u0004\u0018\u00010 2\b\u0010!\u001a\u0004\u0018\u00010 H\u0002J\u001e\u0010\"\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\b2\u0006\u0010\n\u001a\u00020\u000bJ7\u0010#\u001a\u00020\u00062\u0006\u0010\u001d\u001a\u00020\u001e2\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\b2\b\u0010\u0013\u001a\u0004\u0018\u00010\u00142\u0006\u0010\n\u001a\u00020\u000bH\u0000¢\u0006\u0002\b$JN\u0010%\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\b2\u0006\u0010&\u001a\u00020'2\u0014\u0010(\u001a\u0010\u0012\u0004\u0012\u00020*\u0012\u0004\u0012\u00020+\u0018\u00010)2\n\b\u0002\u0010\u0013\u001a\u0004\u0018\u00010\u00142\n\b\u0002\u0010,\u001a\u0004\u0018\u00010\u000fH\u0002J(\u0010-\u001a\u00020\u00062\u0006\u0010&\u001a\u00020.2\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\b2\u0006\u0010\n\u001a\u00020\u000bH\u0002J(\u0010/\u001a\u00020\u00062\u0006\u0010&\u001a\u0002002\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\b2\u0006\u0010\n\u001a\u00020\u000bH\u0002JB\u00101\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\b2\u0006\u0010&\u001a\u0002022\f\u00103\u001a\b\u0012\u0002\b\u0003\u0018\u0001042\b\u0010\u0013\u001a\u0004\u0018\u00010\u00142\n\b\u0002\u00105\u001a\u0004\u0018\u00010\u000fJ$\u00106\u001a\u00020\u00062\b\u0010\u001f\u001a\u0004\u0018\u00010 2\b\u0010!\u001a\u0004\u0018\u00010 2\u0006\u00107\u001a\u000202H\u0002¨\u00068"}, d2 = {"Lorg/jetbrains/kotlin/psi2ir/generators/CallGenerator;", "Lorg/jetbrains/kotlin/psi2ir/generators/StatementGeneratorExtension;", "statementGenerator", "Lorg/jetbrains/kotlin/psi2ir/generators/StatementGenerator;", "(Lorg/jetbrains/kotlin/psi2ir/generators/StatementGenerator;)V", "addParametersToCall", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "startOffset", "", "endOffset", "call", "Lorg/jetbrains/kotlin/psi2ir/intermediate/CallBuilder;", "irCall", "Lorg/jetbrains/kotlin/ir/expressions/IrFunctionAccessExpression;", "irResultType", "Lorg/jetbrains/kotlin/ir/types/IrType;", "contextReceivers", "", "generateCall", "origin", "Lorg/jetbrains/kotlin/ir/expressions/IrStatementOrigin;", "generateCallWithArgumentReordering", "contextReceiversCount", "generateConstructorCall", "constructorDescriptor", "Lorg/jetbrains/kotlin/descriptors/ClassConstructorDescriptor;", "generateDelegatingConstructorCall", "generateDynamicFunctionCall", "Lorg/jetbrains/kotlin/ir/expressions/impl/IrDynamicOperatorExpressionImpl;", "functionDescriptor", "Lorg/jetbrains/kotlin/descriptors/FunctionDescriptor;", "dispatchReceiverValue", "Lorg/jetbrains/kotlin/psi2ir/intermediate/IntermediateValue;", "extensionReceiverValue", "generateEnumConstructorSuperCall", "generateFunctionCall", "generateFunctionCall$ir_psi2ir", "generateGetVariable", "descriptor", "Lorg/jetbrains/kotlin/descriptors/VariableDescriptor;", "typeArguments", "", "Lorg/jetbrains/kotlin/descriptors/TypeParameterDescriptor;", "Lorg/jetbrains/kotlin/types/KotlinType;", "irType", "generatePropertyGetterCall", "Lorg/jetbrains/kotlin/descriptors/PropertyDescriptor;", "generateSamConstructorCall", "Lorg/jetbrains/kotlin/descriptors/CallableDescriptor;", "generateValueReference", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "resolvedCall", "Lorg/jetbrains/kotlin/resolve/calls/model/ResolvedCall;", "smartCastIrType", "getDynamicExpressionReceiver", "referencedDescriptor", "ir.psi2ir"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class CallGenerator extends StatementGeneratorExtension {
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public CallGenerator(StatementGenerator statementGenerator) {
        super(statementGenerator);
        Intrinsics.checkNotNullParameter(statementGenerator, "statementGenerator");
    }

    public static /* synthetic */ IrExpression generateCall$default(CallGenerator callGenerator, int i, int i2, CallBuilder callBuilder, IrStatementOrigin irStatementOrigin, int i3, Object obj) {
        if ((i3 & 8) != 0) {
            irStatementOrigin = null;
        }
        return callGenerator.generateCall(i, i2, callBuilder, irStatementOrigin);
    }

    public final IrExpression generateCall(final int startOffset, final int endOffset, final CallBuilder call, final IrStatementOrigin origin) {
        Intrinsics.checkNotNullParameter(call, "call");
        final CallableDescriptor descriptor = call.getDescriptor();
        CallableDescriptor original = descriptor.getOriginal();
        Intrinsics.checkNotNullExpressionValue(original, "getOriginal(...)");
        if (SamConversionsKt.isSamConstructor(original)) {
            return generateSamConstructorCall(descriptor, startOffset, endOffset, call);
        }
        if (descriptor instanceof PropertyDescriptor) {
            return generatePropertyGetterCall((PropertyDescriptor) descriptor, startOffset, endOffset, call);
        }
        if (descriptor instanceof ClassConstructorDescriptor) {
            return generateConstructorCall((ClassConstructorDescriptor) descriptor, startOffset, endOffset, origin, call);
        }
        if (descriptor instanceof FunctionDescriptor) {
            return generateFunctionCall$ir_psi2ir((FunctionDescriptor) descriptor, startOffset, endOffset, origin, call);
        }
        return call.getCallReceiver().call(new CallExpressionBuilder() { // from class: org.jetbrains.kotlin.psi2ir.generators.CallGenerator$$ExternalSyntheticLambda1
            @Override // org.jetbrains.kotlin.psi2ir.intermediate.CallExpressionBuilder
            public final IrExpression withReceivers(IntermediateValue intermediateValue, IntermediateValue intermediateValue2, List list) {
                return CallGenerator.generateCall$lambda$0(this.f$0, startOffset, endOffset, descriptor, call, origin, intermediateValue, intermediateValue2, list);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final IrExpression generateCall$lambda$0(CallGenerator this$0, int i, int i2, CallableDescriptor descriptor, CallBuilder call, IrStatementOrigin irStatementOrigin, IntermediateValue intermediateValue, IntermediateValue intermediateValue2, List list) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(descriptor, "$descriptor");
        Intrinsics.checkNotNullParameter(call, "$call");
        Intrinsics.checkNotNullParameter(list, "<anonymous parameter 2>");
        return generateValueReference$default(this$0, i, i2, descriptor, call.getOriginal(), irStatementOrigin, null, 32, null);
    }

    private final IrExpression generateSamConstructorCall(CallableDescriptor descriptor, int startOffset, int endOffset, CallBuilder call) {
        KotlinType returnType = descriptor.getReturnType();
        Intrinsics.checkNotNull(returnType);
        IrType irType = toIrType(returnType);
        IrTypeOperator irTypeOperator = IrTypeOperator.SAM_CONVERSION;
        StatementGenerator statementGenerator = getStatementGenerator();
        IrExpression irExpression = call.getIrValueArgumentsByIndex()[0];
        Intrinsics.checkNotNull(irExpression);
        return new IrTypeOperatorCallImpl(startOffset, endOffset, irType, irTypeOperator, irType, ArgumentsGenerationUtilsKt.castArgumentToFunctionalInterfaceForSamType(statementGenerator, irExpression, returnType));
    }

    public static /* synthetic */ IrExpression generateValueReference$default(CallGenerator callGenerator, int i, int i2, DeclarationDescriptor declarationDescriptor, ResolvedCall resolvedCall, IrStatementOrigin irStatementOrigin, IrType irType, int i3, Object obj) {
        if ((i3 & 32) != 0) {
            irType = null;
        }
        return callGenerator.generateValueReference(i, i2, declarationDescriptor, resolvedCall, irStatementOrigin, irType);
    }

    public final IrExpression generateValueReference(int startOffset, int endOffset, DeclarationDescriptor descriptor, ResolvedCall<?> resolvedCall, IrStatementOrigin origin, IrType smartCastIrType) {
        Map<DeclarationDescriptor, IrValueParameterSymbol> capturedDescriptorToFragmentParameterMap;
        IrValueParameterSymbol irValueParameterSymbol;
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        FragmentContext fragmentContext = getContext().getFragmentContext();
        if (fragmentContext != null && (capturedDescriptorToFragmentParameterMap = fragmentContext.getCapturedDescriptorToFragmentParameterMap()) != null && (irValueParameterSymbol = capturedDescriptorToFragmentParameterMap.get(descriptor)) != null) {
            KotlinType type = irValueParameterSymbol.getDescriptor().getType();
            Intrinsics.checkNotNullExpressionValue(type, "getType(...)");
            IrGetValueImpl irGetValueImpl = new IrGetValueImpl(startOffset, endOffset, toIrType(type), irValueParameterSymbol, origin);
            if (smartCastIrType != null) {
                return new IrTypeOperatorCallImpl(startOffset, endOffset, smartCastIrType, IrTypeOperator.IMPLICIT_CAST, smartCastIrType, irGetValueImpl);
            }
            return irGetValueImpl;
        }
        if (descriptor instanceof FakeCallableDescriptorForObject) {
            return generateValueReference(startOffset, endOffset, ((FakeCallableDescriptorForObject) descriptor).getReferencedDescriptor(), resolvedCall, origin, smartCastIrType);
        }
        if (descriptor instanceof TypeAliasDescriptor) {
            ClassDescriptor classDescriptor = ((TypeAliasDescriptor) descriptor).getClassDescriptor();
            Intrinsics.checkNotNull(classDescriptor);
            return generateValueReference(startOffset, endOffset, classDescriptor, null, origin, smartCastIrType);
        }
        if (descriptor instanceof ClassDescriptor) {
            ClassDescriptor classDescriptor2 = (ClassDescriptor) descriptor;
            KotlinType classValueType = DescriptorUtilsKt.getClassValueType(classDescriptor2);
            Intrinsics.checkNotNull(classValueType);
            return ArgumentsGenerationUtilsKt.generateSingletonReference(getStatementGenerator(), classDescriptor2, startOffset, endOffset, classValueType);
        }
        if (descriptor instanceof PropertyDescriptor) {
            StatementGenerator statementGenerator = getStatementGenerator();
            Intrinsics.checkNotNull(resolvedCall);
            IrExpression irExpressionGenerateCall$default = generateCall$default(this, startOffset, endOffset, ArgumentsGenerationUtilsKt.pregenerateCall(statementGenerator, resolvedCall), null, 8, null);
            return smartCastIrType != null ? new IrTypeOperatorCallImpl(startOffset, endOffset, smartCastIrType, IrTypeOperator.IMPLICIT_CAST, smartCastIrType, irExpressionGenerateCall$default) : irExpressionGenerateCall$default;
        }
        if (descriptor instanceof SyntheticFieldDescriptor) {
            SyntheticFieldDescriptor syntheticFieldDescriptor = (SyntheticFieldDescriptor) descriptor;
            IntermediateValue intermediateValueGenerateBackingFieldReceiver = ArgumentsGenerationUtilsKt.generateBackingFieldReceiver(getStatementGenerator(), startOffset, endOffset, resolvedCall, syntheticFieldDescriptor);
            IrFieldSymbol irFieldSymbolReferenceField = getStatementGenerator().getContext().getSymbolTable().getDescriptorExtension().referenceField(syntheticFieldDescriptor.getPropertyDescriptor());
            KotlinType type2 = syntheticFieldDescriptor.getPropertyDescriptor().getType();
            Intrinsics.checkNotNullExpressionValue(type2, "getType(...)");
            return new IrGetFieldImpl(startOffset, endOffset, irFieldSymbolReferenceField, toIrType(type2), intermediateValueGenerateBackingFieldReceiver != null ? intermediateValueGenerateBackingFieldReceiver.load() : null, null, null, 96, null);
        }
        if (descriptor instanceof VariableDescriptor) {
            return generateGetVariable(startOffset, endOffset, (VariableDescriptor) descriptor, ArgumentsGenerationUtilsKt.getTypeArguments(resolvedCall), origin, smartCastIrType);
        }
        throw new NotImplementedError("An operation is not implemented: " + ("Unexpected callable descriptor: " + descriptor + StringUtil.SPACE + descriptor.getClass().getSimpleName()));
    }

    private final IrExpression generateGetVariable(int startOffset, int endOffset, VariableDescriptor descriptor, Map<TypeParameterDescriptor, ? extends KotlinType> typeArguments, IrStatementOrigin origin, IrType irType) {
        if (descriptor instanceof LocalVariableDescriptor) {
            LocalVariableDescriptor localVariableDescriptor = (LocalVariableDescriptor) descriptor;
            if (localVariableDescriptor.isDelegated()) {
                LocalVariableAccessorDescriptor.Getter getter = localVariableDescriptor.getGetter();
                Intrinsics.checkNotNull(getter);
                DescriptorSymbolTableExtension descriptorExtension = getContext().getSymbolTable().getDescriptorExtension();
                SimpleFunctionDescriptor original = getter.getOriginal();
                Intrinsics.checkNotNullExpressionValue(original, "getOriginal(...)");
                IrSimpleFunctionSymbol irSimpleFunctionSymbolReferenceSimpleFunction = descriptorExtension.referenceSimpleFunction(original);
                IrCallImpl.Companion companion = IrCallImpl.INSTANCE;
                KotlinType type = localVariableDescriptor.getType();
                Intrinsics.checkNotNullExpressionValue(type, "getType(...)");
                IrCallImpl irCallImplFromSymbolDescriptor = companion.fromSymbolDescriptor(startOffset, endOffset, toIrType(type), irSimpleFunctionSymbolReferenceSimpleFunction, (176 & 16) != 0 ? IrExpressionsKt.getTypeParametersCount(irSimpleFunctionSymbolReferenceSimpleFunction.getDescriptor()) : 0, (176 & 32) != 0 ? irSimpleFunctionSymbolReferenceSimpleFunction.getDescriptor().getValueParameters().size() + irSimpleFunctionSymbolReferenceSimpleFunction.getDescriptor().getContextReceiverParameters().size() : 0, (176 & 64) != 0 ? null : origin == null ? IrStatementOrigin.GET_LOCAL_PROPERTY.INSTANCE : origin, (176 & 128) != 0 ? null : null);
                getContext().getCallToSubstitutedDescriptorMap$ir_psi2ir().put(irCallImplFromSymbolDescriptor, getter);
                IrCallImpl irCallImpl = irCallImplFromSymbolDescriptor;
                if (typeArguments != null) {
                    for (Map.Entry<TypeParameterDescriptor, ? extends KotlinType> entry : typeArguments.entrySet()) {
                        irCallImpl.putTypeArgument(entry.getKey().getIndex(), toIrType(entry.getValue()));
                    }
                }
                return irCallImplFromSymbolDescriptor;
            }
        }
        KotlinType type2 = descriptor.getType();
        Intrinsics.checkNotNullExpressionValue(type2, "getType(...)");
        IrGetValueImpl irGetValueImpl = new IrGetValueImpl(startOffset, endOffset, toIrType(type2), getContext().getSymbolTable().getDescriptorExtension().referenceValue(descriptor), origin);
        if (irType != null) {
            return new IrTypeOperatorCallImpl(startOffset, endOffset, irType, IrTypeOperator.IMPLICIT_CAST, irType, irGetValueImpl);
        }
        return irGetValueImpl;
    }

    public final IrExpression generateDelegatingConstructorCall(final int startOffset, final int endOffset, final CallBuilder call) {
        Intrinsics.checkNotNullParameter(call, "call");
        return call.getCallReceiver().call(new CallExpressionBuilder() { // from class: org.jetbrains.kotlin.psi2ir.generators.CallGenerator$$ExternalSyntheticLambda3
            @Override // org.jetbrains.kotlin.psi2ir.intermediate.CallExpressionBuilder
            public final IrExpression withReceivers(IntermediateValue intermediateValue, IntermediateValue intermediateValue2, List list) {
                return CallGenerator.generateDelegatingConstructorCall$lambda$7(call, this, startOffset, endOffset, intermediateValue, intermediateValue2, list);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final IrExpression generateDelegatingConstructorCall$lambda$7(CallBuilder call, CallGenerator this$0, int i, int i2, IntermediateValue intermediateValue, IntermediateValue intermediateValue2, List contextReceivers) {
        Intrinsics.checkNotNullParameter(call, "$call");
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(contextReceivers, "contextReceivers");
        CallableDescriptor descriptor = call.getDescriptor();
        ClassConstructorDescriptor classConstructorDescriptor = descriptor instanceof ClassConstructorDescriptor ? (ClassConstructorDescriptor) descriptor : null;
        if (classConstructorDescriptor == null) {
            throw new AssertionError("Class constructor expected: " + call.getDescriptor());
        }
        IrDelegatingConstructorCallImpl irDelegatingConstructorCallImplFromSymbolDescriptor$default = IrDelegatingConstructorCallImpl.Companion.fromSymbolDescriptor$default(IrDelegatingConstructorCallImpl.INSTANCE, i, i2, this$0.getContext().getIrBuiltIns().getUnitType(), this$0.getContext().getSymbolTable().getDescriptorExtension().referenceConstructor(classConstructorDescriptor.getOriginal()), 0, 0, 48, null);
        this$0.getContext().getCallToSubstitutedDescriptorMap$ir_psi2ir().put(irDelegatingConstructorCallImplFromSymbolDescriptor$default, classConstructorDescriptor);
        IrDelegatingConstructorCallImpl irDelegatingConstructorCallImpl = irDelegatingConstructorCallImplFromSymbolDescriptor$default;
        Map<TypeParameterDescriptor, KotlinType> typeArguments = call.getTypeArguments();
        if (typeArguments != null) {
            for (Map.Entry<TypeParameterDescriptor, KotlinType> entry : typeArguments.entrySet()) {
                irDelegatingConstructorCallImpl.putTypeArgument(entry.getKey().getIndex(), this$0.toIrType(entry.getValue()));
            }
        }
        irDelegatingConstructorCallImplFromSymbolDescriptor$default.setDispatchReceiver(intermediateValue != null ? intermediateValue.load() : null);
        irDelegatingConstructorCallImplFromSymbolDescriptor$default.setExtensionReceiver(intermediateValue2 != null ? intermediateValue2.load() : null);
        irDelegatingConstructorCallImplFromSymbolDescriptor$default.setContextReceiversCount(contextReceivers.size());
        IrDelegatingConstructorCallImpl irDelegatingConstructorCallImpl2 = irDelegatingConstructorCallImplFromSymbolDescriptor$default;
        IrType unitType = this$0.getContext().getIrBuiltIns().getUnitType();
        List list = contextReceivers;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
        Iterator it = list.iterator();
        while (it.hasNext()) {
            arrayList.add(((IntermediateValue) it.next()).load());
        }
        return this$0.addParametersToCall(i, i2, call, irDelegatingConstructorCallImpl2, unitType, arrayList);
    }

    public final IrExpression generateEnumConstructorSuperCall(final int startOffset, final int endOffset, final CallBuilder call) {
        Intrinsics.checkNotNullParameter(call, "call");
        final CallableDescriptor descriptor = call.getDescriptor();
        if (!(descriptor instanceof ClassConstructorDescriptor)) {
            throw new AssertionError("Constructor expected: " + descriptor);
        }
        ClassDescriptor containingDeclaration = ((ClassConstructorDescriptor) descriptor).getContainingDeclaration();
        if (containingDeclaration.getKind() != ClassKind.ENUM_CLASS) {
            throw new AssertionError("Enum class constructor expected: " + containingDeclaration);
        }
        return call.getCallReceiver().call(new CallExpressionBuilder() { // from class: org.jetbrains.kotlin.psi2ir.generators.CallGenerator$$ExternalSyntheticLambda4
            @Override // org.jetbrains.kotlin.psi2ir.intermediate.CallExpressionBuilder
            public final IrExpression withReceivers(IntermediateValue intermediateValue, IntermediateValue intermediateValue2, List list) {
                return CallGenerator.generateEnumConstructorSuperCall$lambda$8(descriptor, this, startOffset, endOffset, call, intermediateValue, intermediateValue2, list);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final IrExpression generateEnumConstructorSuperCall$lambda$8(CallableDescriptor constructorDescriptor, CallGenerator this$0, int i, int i2, CallBuilder call, IntermediateValue intermediateValue, IntermediateValue intermediateValue2, List contextReceivers) {
        Intrinsics.checkNotNullParameter(constructorDescriptor, "$constructorDescriptor");
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(call, "$call");
        Intrinsics.checkNotNullParameter(contextReceivers, "contextReceivers");
        if (intermediateValue != null) {
            throw new AssertionError("Dispatch receiver should be null: " + intermediateValue);
        }
        if (intermediateValue2 != null) {
            throw new AssertionError("Extension receiver should be null: " + intermediateValue2);
        }
        if (!contextReceivers.isEmpty()) {
            throw new AssertionError("Context receivers should be empty: " + contextReceivers);
        }
        ClassConstructorDescriptor classConstructorDescriptor = (ClassConstructorDescriptor) constructorDescriptor;
        ClassConstructorDescriptor original = classConstructorDescriptor.getOriginal();
        IrConstructorSymbol irConstructorSymbolReferenceConstructor = this$0.getContext().getSymbolTable().getDescriptorExtension().referenceConstructor(original);
        KotlinType returnType = classConstructorDescriptor.getReturnType();
        Intrinsics.checkNotNullExpressionValue(returnType, "getReturnType(...)");
        IrEnumConstructorCallImpl irEnumConstructorCallImpl = new IrEnumConstructorCallImpl(i, i2, this$0.toIrType(returnType), irConstructorSymbolReferenceConstructor, IrExpressionsKt.getTypeParametersCount(original), original.getValueParameters().size());
        this$0.getContext().getCallToSubstitutedDescriptorMap$ir_psi2ir().put(irEnumConstructorCallImpl, constructorDescriptor);
        return this$0.addParametersToCall(i, i2, call, irEnumConstructorCallImpl, irEnumConstructorCallImpl.getType(), CollectionsKt.emptyList());
    }

    private final IrExpression generatePropertyGetterCall(final PropertyDescriptor descriptor, final int startOffset, final int endOffset, final CallBuilder call) {
        final FunctionDescriptor unwrappedGetMethod = KotlinUtilsKt.getUnwrappedGetMethod(descriptor);
        KotlinType type = descriptor.getType();
        Intrinsics.checkNotNullExpressionValue(type, "getType(...)");
        final IrType irType = toIrType(type);
        IrClassSymbol irClassSymbolReferenceClass = null;
        if (unwrappedGetMethod == null) {
            ClassDescriptor superQualifier = call.getSuperQualifier();
            if (superQualifier == null) {
                DeclarationDescriptor containingDeclaration = descriptor.getContainingDeclaration();
                superQualifier = containingDeclaration instanceof ClassDescriptor ? (ClassDescriptor) containingDeclaration : null;
            }
            if (superQualifier != null && !(superQualifier instanceof ScriptDescriptor)) {
                irClassSymbolReferenceClass = getContext().getSymbolTable().getDescriptorExtension().referenceClass(superQualifier);
            }
            final IrClassSymbol irClassSymbol = irClassSymbolReferenceClass;
            DescriptorSymbolTableExtension descriptorExtension = getContext().getSymbolTable().getDescriptorExtension();
            GeneratorExtensions extensions = getContext().getExtensions();
            PropertyDescriptor original = KotlinUtilsKt.resolveFakeOverride(descriptor).getOriginal();
            Intrinsics.checkNotNullExpressionValue(original, "getOriginal(...)");
            final IrFieldSymbol irFieldSymbolReferenceField = descriptorExtension.referenceField(extensions.remapDebuggerFieldPropertyDescriptor(original));
            return call.getCallReceiver().call(new CallExpressionBuilder() { // from class: org.jetbrains.kotlin.psi2ir.generators.CallGenerator$$ExternalSyntheticLambda5
                @Override // org.jetbrains.kotlin.psi2ir.intermediate.CallExpressionBuilder
                public final IrExpression withReceivers(IntermediateValue intermediateValue, IntermediateValue intermediateValue2, List list) {
                    return CallGenerator.generatePropertyGetterCall$lambda$11(startOffset, endOffset, irFieldSymbolReferenceField, irType, irClassSymbol, this, descriptor, intermediateValue, intermediateValue2, list);
                }
            });
        }
        ClassDescriptor superQualifier2 = call.getSuperQualifier();
        final IrClassSymbol irClassSymbolReferenceClass2 = superQualifier2 != null ? getContext().getSymbolTable().getDescriptorExtension().referenceClass(superQualifier2) : null;
        return IrUtilsKt.adjustForCallee(call.getCallReceiver(), unwrappedGetMethod).call(new CallExpressionBuilder() { // from class: org.jetbrains.kotlin.psi2ir.generators.CallGenerator$$ExternalSyntheticLambda6
            @Override // org.jetbrains.kotlin.psi2ir.intermediate.CallExpressionBuilder
            public final IrExpression withReceivers(IntermediateValue intermediateValue, IntermediateValue intermediateValue2, List list) {
                return CallGenerator.generatePropertyGetterCall$lambda$16(descriptor, this, startOffset, endOffset, irType, unwrappedGetMethod, irClassSymbolReferenceClass2, call, intermediateValue, intermediateValue2, list);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final IrExpression generatePropertyGetterCall$lambda$11(int i, int i2, IrFieldSymbol fieldSymbol, IrType irType, IrClassSymbol irClassSymbol, CallGenerator this$0, PropertyDescriptor descriptor, IntermediateValue intermediateValue, IntermediateValue intermediateValue2, List list) {
        IrExpression irExpressionLoad;
        Intrinsics.checkNotNullParameter(fieldSymbol, "$fieldSymbol");
        Intrinsics.checkNotNullParameter(irType, "$irType");
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(descriptor, "$descriptor");
        Intrinsics.checkNotNullParameter(list, "<anonymous parameter 2>");
        if (intermediateValue == null || (irExpressionLoad = intermediateValue.load()) == null) {
            irExpressionLoad = intermediateValue2 != null ? intermediateValue2.load() : null;
        }
        IrGetFieldImpl irGetFieldImpl = new IrGetFieldImpl(i, i2, fieldSymbol, irType, irExpressionLoad, IrStatementOrigin.GET_PROPERTY.INSTANCE, irClassSymbol);
        this$0.getContext().getCallToSubstitutedDescriptorMap$ir_psi2ir().put(irGetFieldImpl, descriptor);
        return irGetFieldImpl;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final IrExpression generatePropertyGetterCall$lambda$16(PropertyDescriptor descriptor, CallGenerator this$0, int i, int i2, IrType irType, FunctionDescriptor functionDescriptor, IrClassSymbol irClassSymbol, CallBuilder call, IntermediateValue intermediateValue, IntermediateValue intermediateValue2, List contextReceiverValues) {
        Intrinsics.checkNotNullParameter(descriptor, "$descriptor");
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(irType, "$irType");
        Intrinsics.checkNotNullParameter(call, "$call");
        Intrinsics.checkNotNullParameter(contextReceiverValues, "contextReceiverValues");
        PropertyDescriptor propertyDescriptor = descriptor;
        if (org.jetbrains.kotlin.resolve.calls.tasks.DynamicCallsKt.isDynamic(propertyDescriptor)) {
            IrExpression dynamicExpressionReceiver = this$0.getDynamicExpressionReceiver(intermediateValue, intermediateValue2, propertyDescriptor);
            String strAsString = descriptor.getShortName().asString();
            Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
            return new IrDynamicMemberExpressionImpl(i, i2, irType, strAsString, dynamicExpressionReceiver);
        }
        DescriptorSymbolTableExtension descriptorExtension = this$0.getContext().getSymbolTable().getDescriptorExtension();
        FunctionDescriptor original = functionDescriptor.getOriginal();
        Intrinsics.checkNotNullExpressionValue(original, "getOriginal(...)");
        IrCallImpl irCallImpl = new IrCallImpl(i, i2, irType, descriptorExtension.referenceSimpleFunction(original), IrExpressionsKt.getTypeParametersCount(descriptor), descriptor.getContextReceiverParameters().size(), IrStatementOrigin.GET_PROPERTY.INSTANCE, irClassSymbol);
        Map<IrDeclarationReference, CallableDescriptor> callToSubstitutedDescriptorMap$ir_psi2ir = this$0.getContext().getCallToSubstitutedDescriptorMap$ir_psi2ir();
        PropertyGetterDescriptor getter = descriptor.getGetter();
        Intrinsics.checkNotNull(getter);
        callToSubstitutedDescriptorMap$ir_psi2ir.put(irCallImpl, IrUtilsKt.computeSubstitutedSyntheticAccessor(descriptor, functionDescriptor, getter));
        IrCallImpl irCallImpl2 = irCallImpl;
        Map<TypeParameterDescriptor, KotlinType> typeArguments = call.getTypeArguments();
        if (typeArguments != null) {
            for (Map.Entry<TypeParameterDescriptor, KotlinType> entry : typeArguments.entrySet()) {
                irCallImpl2.putTypeArgument(entry.getKey().getIndex(), this$0.toIrType(entry.getValue()));
            }
        }
        irCallImpl.setDispatchReceiver(intermediateValue != null ? intermediateValue.load() : null);
        irCallImpl.setExtensionReceiver(intermediateValue2 != null ? intermediateValue2.load() : null);
        List list = contextReceiverValues;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
        Iterator it = list.iterator();
        while (it.hasNext()) {
            arrayList.add(((IntermediateValue) it.next()).load());
        }
        ArrayList arrayList2 = arrayList;
        irCallImpl.setContextReceiversCount(arrayList2.size());
        this$0.addParametersToCall(i, i2, call, irCallImpl, irType, arrayList2);
        return irCallImpl;
    }

    private final IrExpression getDynamicExpressionReceiver(IntermediateValue dispatchReceiverValue, IntermediateValue extensionReceiverValue, DeclarationDescriptor referencedDescriptor) {
        IrExpression irExpressionLoad;
        if (dispatchReceiverValue == null || (irExpressionLoad = dispatchReceiverValue.load()) == null) {
            throw new AssertionError("Dynamic member reference " + referencedDescriptor + " should have a dispatch receiver");
        }
        if (!(irExpressionLoad.getType() instanceof IrDynamicType)) {
            throw new AssertionError("Dynamic member reference " + referencedDescriptor + " should have a receiver of dynamic type: " + RenderIrElementKt.render$default(irExpressionLoad, (DumpIrTreeOptions) null, 1, (Object) null));
        }
        if (extensionReceiverValue == null) {
            return irExpressionLoad;
        }
        throw new AssertionError("Dynamic member reference " + referencedDescriptor + " should have no extension receiver");
    }

    private final IrExpression generateConstructorCall(final ClassConstructorDescriptor constructorDescriptor, final int startOffset, final int endOffset, final IrStatementOrigin origin, final CallBuilder call) {
        return call.getCallReceiver().call(new CallExpressionBuilder() { // from class: org.jetbrains.kotlin.psi2ir.generators.CallGenerator$$ExternalSyntheticLambda2
            @Override // org.jetbrains.kotlin.psi2ir.intermediate.CallExpressionBuilder
            public final IrExpression withReceivers(IntermediateValue intermediateValue, IntermediateValue intermediateValue2, List list) {
                return CallGenerator.generateConstructorCall$lambda$20(this.f$0, constructorDescriptor, startOffset, endOffset, origin, call, intermediateValue, intermediateValue2, list);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final IrExpression generateConstructorCall$lambda$20(CallGenerator this$0, ClassConstructorDescriptor constructorDescriptor, int i, int i2, IrStatementOrigin irStatementOrigin, CallBuilder call, IntermediateValue intermediateValue, IntermediateValue intermediateValue2, List contextReceiverValues) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(constructorDescriptor, "$constructorDescriptor");
        Intrinsics.checkNotNullParameter(call, "$call");
        Intrinsics.checkNotNullParameter(contextReceiverValues, "contextReceiverValues");
        KotlinType returnType = constructorDescriptor.getReturnType();
        Intrinsics.checkNotNullExpressionValue(returnType, "getReturnType(...)");
        IrType irType = this$0.toIrType(returnType);
        IrConstructorCallImpl irConstructorCallImplFromSymbolDescriptor = IrConstructorCallImpl.INSTANCE.fromSymbolDescriptor(i, i2, irType, this$0.getContext().getSymbolTable().getDescriptorExtension().referenceConstructor(constructorDescriptor.getOriginal()), irStatementOrigin);
        this$0.getContext().getCallToSubstitutedDescriptorMap$ir_psi2ir().put(irConstructorCallImplFromSymbolDescriptor, constructorDescriptor);
        IrConstructorCallImpl irConstructorCallImpl = irConstructorCallImplFromSymbolDescriptor;
        Map<TypeParameterDescriptor, KotlinType> typeArguments = call.getTypeArguments();
        if (typeArguments != null) {
            for (Map.Entry<TypeParameterDescriptor, KotlinType> entry : typeArguments.entrySet()) {
                irConstructorCallImpl.putTypeArgument(entry.getKey().getIndex(), this$0.toIrType(entry.getValue()));
            }
        }
        irConstructorCallImplFromSymbolDescriptor.setDispatchReceiver(intermediateValue != null ? intermediateValue.load() : null);
        irConstructorCallImplFromSymbolDescriptor.setExtensionReceiver(intermediateValue2 != null ? intermediateValue2.load() : null);
        List list = contextReceiverValues;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
        Iterator it = list.iterator();
        while (it.hasNext()) {
            arrayList.add(((IntermediateValue) it.next()).load());
        }
        return this$0.addParametersToCall(i, i2, call, irConstructorCallImplFromSymbolDescriptor, irType, arrayList);
    }

    public final IrExpression generateFunctionCall$ir_psi2ir(final FunctionDescriptor functionDescriptor, final int startOffset, final int endOffset, final IrStatementOrigin origin, final CallBuilder call) {
        Intrinsics.checkNotNullParameter(functionDescriptor, "functionDescriptor");
        Intrinsics.checkNotNullParameter(call, "call");
        CallExpressionBuilder callExpressionBuilder = new CallExpressionBuilder() { // from class: org.jetbrains.kotlin.psi2ir.generators.CallGenerator$$ExternalSyntheticLambda0
            @Override // org.jetbrains.kotlin.psi2ir.intermediate.CallExpressionBuilder
            public final IrExpression withReceivers(IntermediateValue intermediateValue, IntermediateValue intermediateValue2, List list) {
                return CallGenerator.generateFunctionCall$lambda$25(functionDescriptor, this, startOffset, endOffset, call, origin, intermediateValue, intermediateValue2, list);
            }
        };
        CallReceiver callReceiver = call.getCallReceiver();
        if (!(callReceiver instanceof SimpleCallReceiver)) {
            return callReceiver.call(callExpressionBuilder);
        }
        SimpleCallReceiver simpleCallReceiver = (SimpleCallReceiver) callReceiver;
        return callExpressionBuilder.withReceivers(simpleCallReceiver.getDispatchReceiverValue(), simpleCallReceiver.getExtensionReceiverValue(), simpleCallReceiver.getContextReceiverValues());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final IrExpression generateFunctionCall$lambda$25(FunctionDescriptor functionDescriptor, CallGenerator this$0, int i, int i2, CallBuilder call, IrStatementOrigin irStatementOrigin, IntermediateValue intermediateValue, IntermediateValue intermediateValue2, List contextReceiverValues) {
        Intrinsics.checkNotNullParameter(functionDescriptor, "$functionDescriptor");
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(call, "$call");
        Intrinsics.checkNotNullParameter(contextReceiverValues, "contextReceiverValues");
        if (org.jetbrains.kotlin.resolve.calls.tasks.DynamicCallsKt.isDynamic(functionDescriptor)) {
            return this$0.generateDynamicFunctionCall(i, i2, functionDescriptor, call, intermediateValue, intermediateValue2);
        }
        DescriptorSymbolTableExtension descriptorExtension = this$0.getContext().getSymbolTable().getDescriptorExtension();
        FunctionDescriptor original = functionDescriptor.getOriginal();
        Intrinsics.checkNotNullExpressionValue(original, "getOriginal(...)");
        IrSimpleFunctionSymbol irSimpleFunctionSymbolReferenceSimpleFunction = descriptorExtension.referenceSimpleFunction(original);
        IrCallImpl.Companion companion = IrCallImpl.INSTANCE;
        KotlinType returnType = functionDescriptor.getReturnType();
        Intrinsics.checkNotNull(returnType);
        IrType irType = this$0.toIrType(returnType);
        ClassDescriptor superQualifier = call.getSuperQualifier();
        IrCallImpl irCallImplFromSymbolDescriptor = companion.fromSymbolDescriptor(i, i2, irType, irSimpleFunctionSymbolReferenceSimpleFunction, (176 & 16) != 0 ? IrExpressionsKt.getTypeParametersCount(irSimpleFunctionSymbolReferenceSimpleFunction.getDescriptor()) : 0, (176 & 32) != 0 ? irSimpleFunctionSymbolReferenceSimpleFunction.getDescriptor().getValueParameters().size() + irSimpleFunctionSymbolReferenceSimpleFunction.getDescriptor().getContextReceiverParameters().size() : 0, (176 & 64) != 0 ? null : irStatementOrigin, (176 & 128) != 0 ? null : superQualifier != null ? this$0.getContext().getSymbolTable().getDescriptorExtension().referenceClass(superQualifier) : null);
        this$0.getContext().getCallToSubstitutedDescriptorMap$ir_psi2ir().put(irCallImplFromSymbolDescriptor, functionDescriptor);
        IrCallImpl irCallImpl = irCallImplFromSymbolDescriptor;
        Map<TypeParameterDescriptor, KotlinType> typeArguments = call.getTypeArguments();
        if (typeArguments != null) {
            for (Map.Entry<TypeParameterDescriptor, KotlinType> entry : typeArguments.entrySet()) {
                irCallImpl.putTypeArgument(entry.getKey().getIndex(), this$0.toIrType(entry.getValue()));
            }
        }
        irCallImplFromSymbolDescriptor.setDispatchReceiver(intermediateValue != null ? intermediateValue.load() : null);
        irCallImplFromSymbolDescriptor.setExtensionReceiver(intermediateValue2 != null ? intermediateValue2.load() : null);
        List list = contextReceiverValues;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
        Iterator it = list.iterator();
        while (it.hasNext()) {
            arrayList.add(((IntermediateValue) it.next()).load());
        }
        ArrayList arrayList2 = arrayList;
        irCallImplFromSymbolDescriptor.setContextReceiversCount(arrayList2.size());
        return this$0.addParametersToCall(i, i2, call, irCallImplFromSymbolDescriptor, irCallImplFromSymbolDescriptor.getType(), arrayList2);
    }

    private static final IrDynamicOperatorExpressionImpl generateDynamicFunctionCall$makeDynamicOperatorExpression(int i, int i2, CallGenerator callGenerator, FunctionDescriptor functionDescriptor, IrDynamicOperator irDynamicOperator) {
        KotlinType returnType = functionDescriptor.getReturnType();
        Intrinsics.checkNotNull(returnType);
        return new IrDynamicOperatorExpressionImpl(i, i2, callGenerator.toIrType(returnType), irDynamicOperator);
    }

    private static final IrDynamicOperatorExpressionImpl generateDynamicFunctionCall$makeDynamicOperatorExpressionWithArguments(int i, int i2, CallGenerator callGenerator, FunctionDescriptor functionDescriptor, CallBuilder callBuilder, IrDynamicOperator irDynamicOperator, IrExpression irExpression) {
        IrDynamicOperatorExpressionImpl irDynamicOperatorExpressionImplGenerateDynamicFunctionCall$makeDynamicOperatorExpression = generateDynamicFunctionCall$makeDynamicOperatorExpression(i, i2, callGenerator, functionDescriptor, irDynamicOperator);
        irDynamicOperatorExpressionImplGenerateDynamicFunctionCall$makeDynamicOperatorExpression.setReceiver(irExpression);
        List<IrExpression> arguments = irDynamicOperatorExpressionImplGenerateDynamicFunctionCall$makeDynamicOperatorExpression.getArguments();
        List<IrExpression> valueArgumentsInParameterOrder = CallBuilderKt.getValueArgumentsInParameterOrder(callBuilder);
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(valueArgumentsInParameterOrder, 10));
        int i3 = 0;
        for (Object obj : valueArgumentsInParameterOrder) {
            int i4 = i3 + 1;
            if (i3 < 0) {
                CollectionsKt.throwIndexOverflow();
            }
            IrExpression irExpression2 = (IrExpression) obj;
            if (irExpression2 == null) {
                throw new AssertionError("No argument in dynamic call " + functionDescriptor + " at position " + i3);
            }
            arrayList.add(irExpression2);
            i3 = i4;
        }
        arguments.addAll(arrayList);
        return irDynamicOperatorExpressionImplGenerateDynamicFunctionCall$makeDynamicOperatorExpression;
    }

    private final IrDynamicOperatorExpressionImpl generateDynamicFunctionCall(int startOffset, int endOffset, FunctionDescriptor functionDescriptor, CallBuilder call, IntermediateValue dispatchReceiverValue, IntermediateValue extensionReceiverValue) {
        IrExpression dynamicExpressionReceiver = getDynamicExpressionReceiver(dispatchReceiverValue, extensionReceiverValue, functionDescriptor);
        if (DynamicCallsKt.isImplicitInvoke(call.getOriginal())) {
            return generateDynamicFunctionCall$makeDynamicOperatorExpressionWithArguments(startOffset, endOffset, this, functionDescriptor, call, IrDynamicOperator.INVOKE, dynamicExpressionReceiver);
        }
        if (DynamicCallsKt.isImplicitGet(call.getOriginal())) {
            return generateDynamicFunctionCall$makeDynamicOperatorExpressionWithArguments(startOffset, endOffset, this, functionDescriptor, call, IrDynamicOperator.ARRAY_ACCESS, dynamicExpressionReceiver);
        }
        if (DynamicCallsKt.isImplicitSet(call.getOriginal())) {
            IrDynamicOperatorExpressionImpl irDynamicOperatorExpressionImplGenerateDynamicFunctionCall$makeDynamicOperatorExpression = generateDynamicFunctionCall$makeDynamicOperatorExpression(startOffset, endOffset, this, functionDescriptor, IrDynamicOperator.EQ);
            List<IrExpression> valueArgumentsInParameterOrder = CallBuilderKt.getValueArgumentsInParameterOrder(call);
            IrExpression irExpression = valueArgumentsInParameterOrder.get(0);
            if (irExpression == null) {
                throw new AssertionError("No index argument in dynamic array set: " + call.getOriginal().getCall().getCallElement().getText());
            }
            IrExpression irExpression2 = valueArgumentsInParameterOrder.get(1);
            if (irExpression2 == null) {
                throw new AssertionError("No value argument in dynamic array set: " + call.getOriginal().getCall().getCallElement().getText());
            }
            IrDynamicOperatorExpressionImpl irDynamicOperatorExpressionImpl = irDynamicOperatorExpressionImplGenerateDynamicFunctionCall$makeDynamicOperatorExpression;
            IrDynamicOperatorExpressionImpl irDynamicOperatorExpressionImplGenerateDynamicFunctionCall$makeDynamicOperatorExpression2 = generateDynamicFunctionCall$makeDynamicOperatorExpression(startOffset, endOffset, this, functionDescriptor, IrDynamicOperator.ARRAY_ACCESS);
            IrDynamicOperatorExpressionImpl irDynamicOperatorExpressionImpl2 = irDynamicOperatorExpressionImplGenerateDynamicFunctionCall$makeDynamicOperatorExpression2;
            IrExpressionsKt.setLeft(irDynamicOperatorExpressionImpl2, dynamicExpressionReceiver);
            IrExpressionsKt.setRight(irDynamicOperatorExpressionImpl2, irExpression);
            IrExpressionsKt.setLeft(irDynamicOperatorExpressionImpl, irDynamicOperatorExpressionImplGenerateDynamicFunctionCall$makeDynamicOperatorExpression2);
            IrExpressionsKt.setRight(irDynamicOperatorExpressionImpl, irExpression2);
            return irDynamicOperatorExpressionImplGenerateDynamicFunctionCall$makeDynamicOperatorExpression;
        }
        IrDynamicOperator irDynamicOperator = IrDynamicOperator.INVOKE;
        IrType type = dynamicExpressionReceiver.getType();
        String strAsString = functionDescriptor.getShortName().asString();
        Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
        return generateDynamicFunctionCall$makeDynamicOperatorExpressionWithArguments(startOffset, endOffset, this, functionDescriptor, call, irDynamicOperator, new IrDynamicMemberExpressionImpl(startOffset, endOffset, type, strAsString, dynamicExpressionReceiver));
    }

    private final IrExpression addParametersToCall(int startOffset, int endOffset, CallBuilder call, IrFunctionAccessExpression irCall, IrType irResultType, List<? extends IrExpression> contextReceivers) {
        int i = 0;
        int i2 = 0;
        for (Object obj : contextReceivers) {
            int i3 = i2 + 1;
            if (i2 < 0) {
                CollectionsKt.throwIndexOverflow();
            }
            irCall.putValueArgument(i2, (IrExpression) obj);
            i2 = i3;
        }
        if (CallBuilderKt.isValueArgumentReorderingRequired(call)) {
            return generateCallWithArgumentReordering(irCall, startOffset, endOffset, call, irResultType, contextReceivers.size());
        }
        Iterator<IrExpression> it = CallBuilderKt.getValueArgumentsInParameterOrder(call).iterator();
        while (it.hasNext()) {
            irCall.putValueArgument(i + contextReceivers.size(), it.next());
            i++;
        }
        return irCall;
    }

    private final IrExpression generateCallWithArgumentReordering(IrFunctionAccessExpression irCall, int startOffset, int endOffset, CallBuilder call, IrType irResultType, int contextReceiversCount) {
        IrBlockImpl irBlockImpl = new IrBlockImpl(startOffset, endOffset, irResultType, IrStatementOrigin.ARGUMENTS_REORDERING_FOR_CALL.INSTANCE);
        IrExpression dispatchReceiver = irCall.getDispatchReceiver();
        irCall.setDispatchReceiver(dispatchReceiver != null ? generateCallWithArgumentReordering$freeze(dispatchReceiver, this, irBlockImpl, "$this") : null);
        IrExpression extensionReceiver = irCall.getExtensionReceiver();
        irCall.setExtensionReceiver(extensionReceiver != null ? generateCallWithArgumentReordering$freeze(extensionReceiver, this, irBlockImpl, AsmUtil.RECEIVER_PARAMETER_NAME) : null);
        ResolvedCall<?> original = call.getOriginal();
        List<ValueParameterDescriptor> valueParameters = original.getResultingDescriptor().getValueParameters();
        Intrinsics.checkNotNullExpressionValue(valueParameters, "getValueParameters(...)");
        HashMap map = new HashMap();
        List<ResolvedValueArgument> valueArgumentsByIndex = original.getValueArgumentsByIndex();
        Intrinsics.checkNotNull(valueArgumentsByIndex);
        int i = 0;
        for (ResolvedValueArgument resolvedValueArgument : valueArgumentsByIndex) {
            int i2 = i + 1;
            Integer numValueOf = Integer.valueOf(i);
            Intrinsics.checkNotNull(resolvedValueArgument);
            map.put(resolvedValueArgument, numValueOf);
            i = i2;
        }
        Iterator<ResolvedValueArgument> it = original.getValueArguments().values().iterator();
        while (it.hasNext()) {
            Object obj = map.get(it.next());
            Intrinsics.checkNotNull(obj);
            int iIntValue = ((Number) obj).intValue();
            ValueParameterDescriptor valueParameterDescriptor = valueParameters.get(iIntValue);
            Intrinsics.checkNotNullExpressionValue(valueParameterDescriptor, "get(...)");
            IrExpression valueArgument = call.getValueArgument(valueParameterDescriptor);
            if (valueArgument != null) {
                int i3 = iIntValue + contextReceiversCount;
                String strAsString = valueParameters.get(iIntValue).getShortName().asString();
                Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
                irCall.putValueArgument(i3, generateCallWithArgumentReordering$freeze(valueArgument, this, irBlockImpl, strAsString));
            }
        }
        irBlockImpl.getStatements().add(irCall);
        return irBlockImpl;
    }

    private static final IrExpression generateCallWithArgumentReordering$freeze(IrExpression irExpression, CallGenerator callGenerator, IrBlockImpl irBlockImpl, String str) {
        return IrExpressionsKt.isUnchanging(irExpression) ? irExpression : RematerializableValueKt.createTemporaryVariableInBlock$default(callGenerator.getScope(), callGenerator.getContext(), irExpression, irBlockImpl, str, 0, 0, 48, null).load();
    }
}
