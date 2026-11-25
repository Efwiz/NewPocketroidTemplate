package org.jetbrains.kotlin.psi2ir.generators;

import com.badlogic.gdx.physics.bullet.collision.ContactCallbackEvent;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.builtins.FunctionTypesKt;
import org.jetbrains.kotlin.builtins.KotlinBuiltIns;
import org.jetbrains.kotlin.builtins.ReflectionTypes;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.ClassifierDescriptor;
import org.jetbrains.kotlin.descriptors.DescriptorVisibilities;
import org.jetbrains.kotlin.descriptors.DescriptorVisibility;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.descriptors.Modality;
import org.jetbrains.kotlin.descriptors.PropertyDescriptor;
import org.jetbrains.kotlin.descriptors.PropertyGetterDescriptor;
import org.jetbrains.kotlin.descriptors.PropertySetterDescriptor;
import org.jetbrains.kotlin.descriptors.TypeParameterDescriptor;
import org.jetbrains.kotlin.descriptors.ValueParameterDescriptor;
import org.jetbrains.kotlin.descriptors.VariableAccessorDescriptor;
import org.jetbrains.kotlin.descriptors.VariableDescriptorWithAccessors;
import org.jetbrains.kotlin.descriptors.annotations.Annotations;
import org.jetbrains.kotlin.descriptors.synthetic.FunctionInterfaceConstructorDescriptor;
import org.jetbrains.kotlin.ir.IrBuiltIns;
import org.jetbrains.kotlin.ir.IrElement;
import org.jetbrains.kotlin.ir.IrStatement;
import org.jetbrains.kotlin.ir.builders.ExpressionHelpersKt;
import org.jetbrains.kotlin.ir.builders.IrBlockBodyBuilder;
import org.jetbrains.kotlin.ir.builders.IrBuilderWithScope;
import org.jetbrains.kotlin.ir.builders.Scope;
import org.jetbrains.kotlin.ir.declarations.DescriptorMetadataSource;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationOrigin;
import org.jetbrains.kotlin.ir.declarations.IrFactory;
import org.jetbrains.kotlin.ir.declarations.IrProperty;
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction;
import org.jetbrains.kotlin.ir.declarations.IrValueParameter;
import org.jetbrains.kotlin.ir.expressions.IrBlockBody;
import org.jetbrains.kotlin.ir.expressions.IrCall;
import org.jetbrains.kotlin.ir.expressions.IrCallableReference;
import org.jetbrains.kotlin.ir.expressions.IrDeclarationReference;
import org.jetbrains.kotlin.ir.expressions.IrExpression;
import org.jetbrains.kotlin.ir.expressions.IrExpressionsKt;
import org.jetbrains.kotlin.ir.expressions.IrFunctionAccessExpression;
import org.jetbrains.kotlin.ir.expressions.IrFunctionReference;
import org.jetbrains.kotlin.ir.expressions.IrLocalDelegatedPropertyReference;
import org.jetbrains.kotlin.ir.expressions.IrPropertyReference;
import org.jetbrains.kotlin.ir.expressions.IrStatementOrigin;
import org.jetbrains.kotlin.ir.expressions.IrVarargElement;
import org.jetbrains.kotlin.ir.expressions.impl.IrBlockImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrCallImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrClassReferenceImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrConstructorCallImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrFunctionReferenceImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrGetClassImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrGetValueImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrLocalDelegatedPropertyReferenceImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrPropertyReferenceImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrReturnImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrSpreadElementImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrVarargImpl;
import org.jetbrains.kotlin.ir.symbols.IrConstructorSymbol;
import org.jetbrains.kotlin.ir.symbols.IrFieldSymbol;
import org.jetbrains.kotlin.ir.symbols.IrFunctionSymbol;
import org.jetbrains.kotlin.ir.symbols.IrPropertySymbol;
import org.jetbrains.kotlin.ir.symbols.IrSimpleFunctionSymbol;
import org.jetbrains.kotlin.ir.symbols.IrValueParameterSymbol;
import org.jetbrains.kotlin.ir.symbols.IrVariableSymbol;
import org.jetbrains.kotlin.ir.symbols.impl.IrSimpleFunctionSymbolImpl;
import org.jetbrains.kotlin.ir.symbols.impl.IrValueParameterSymbolImpl;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.ir.util.IrUtilsKt;
import org.jetbrains.kotlin.ir.util.SymbolTable;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.psi.FakeImplicitSpreadValueArgumentForCallableReference;
import org.jetbrains.kotlin.psi.FakePositionalValueArgumentForCallableReference;
import org.jetbrains.kotlin.psi.KtCallableReferenceExpression;
import org.jetbrains.kotlin.psi.KtClassLiteralExpression;
import org.jetbrains.kotlin.psi.KtElement;
import org.jetbrains.kotlin.psi.KtExpression;
import org.jetbrains.kotlin.psi.KtSimpleNameExpression;
import org.jetbrains.kotlin.psi.ValueArgument;
import org.jetbrains.kotlin.psi.psiUtil.PsiUtilsKt;
import org.jetbrains.kotlin.psi2ir.descriptors.IrBuiltInsOverDescriptors;
import org.jetbrains.kotlin.psi2ir.intermediate.CallBuilder;
import org.jetbrains.kotlin.psi2ir.intermediate.CallExpressionBuilder;
import org.jetbrains.kotlin.psi2ir.intermediate.IntermediateValue;
import org.jetbrains.kotlin.resolve.BindingContext;
import org.jetbrains.kotlin.resolve.ImportedFromObjectCallableDescriptor;
import org.jetbrains.kotlin.resolve.calls.model.DefaultValueArgument;
import org.jetbrains.kotlin.resolve.calls.model.ExpressionValueArgument;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedCall;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedValueArgument;
import org.jetbrains.kotlin.resolve.calls.model.VarargValueArgument;
import org.jetbrains.kotlin.resolve.scopes.receivers.ReceiverValue;
import org.jetbrains.kotlin.resolve.scopes.receivers.TransientReceiver;
import org.jetbrains.kotlin.serialization.deserialization.descriptors.DeserializedContainerSource;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.SimpleType;
import org.jetbrains.kotlin.types.TypeConstructor;
import org.jetbrains.kotlin.types.TypeProjection;
import org.jetbrains.kotlin.types.TypeProjectionImpl;
import org.jetbrains.kotlin.types.TypeSubstitutor;
import org.jetbrains.kotlin.types.expressions.DoubleColonLHS;
import org.jetbrains.kotlin.util.slicedMap.WritableSlice;
import org.mozilla.javascript.NativeSymbol;

/* JADX INFO: compiled from: ReflectionReferencesGenerator.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u0080\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\b\u0000\u0018\u00002\u00020\u0001:\u0001_B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J:\u0010\u0005\u001a\u0004\u0018\u00010\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\b2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\bH\u0002J0\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\b2\u0006\u0010\u0013\u001a\u00020\u00142\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u0010\u001a\u00020\bH\u0002J0\u0010\u0015\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\b2\u0006\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u00192\u0006\u0010\f\u001a\u00020\rH\u0002JF\u0010\u001a\u001a\u00020\r2\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\b2\u0006\u0010\u001b\u001a\u00020\u001c2\f\u0010\u001d\u001a\b\u0012\u0004\u0012\u00020\u001f0\u001e2\u0006\u0010 \u001a\u00020\u001f2\u0006\u0010\u0018\u001a\u00020\u00192\u0006\u0010!\u001a\u00020\u001fH\u0002J0\u0010\"\u001a\u00020#2\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\b2\u0006\u0010$\u001a\u00020%2\u0006\u0010&\u001a\u00020\b2\u0006\u0010'\u001a\u00020\u001fH\u0002J \u0010(\u001a\u00020\r2\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\b2\u0006\u0010)\u001a\u00020*H\u0002J \u0010+\u001a\u00020\u00062\u0006\u0010,\u001a\u00020-2\u0006\u0010\u0018\u001a\u00020\u00192\u0006\u0010!\u001a\u00020\u001fH\u0002J\u000e\u0010.\u001a\u00020\u00062\u0006\u0010,\u001a\u00020-JD\u0010.\u001a\u0006\u0012\u0002\b\u00030/2\u0006\u00100\u001a\u0002012\u0006\u0010'\u001a\u00020\u001f2\u0006\u00102\u001a\u00020*2\u0014\u00103\u001a\u0010\u0012\u0004\u0012\u000205\u0012\u0004\u0012\u00020\u001f\u0018\u0001042\n\b\u0002\u00106\u001a\u0004\u0018\u000107J\u000e\u00108\u001a\u00020\u00062\u0006\u00109\u001a\u00020:J \u0010;\u001a\u00020\u00062\u0006\u0010,\u001a\u00020-2\u0006\u0010!\u001a\u00020\u001f2\u0006\u0010)\u001a\u00020*H\u0002JP\u0010<\u001a\u00020=2\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\b2\u0006\u0010'\u001a\u00020\u001f2\u0006\u0010>\u001a\u00020\u00172\u0006\u0010)\u001a\u00020\u001c2\u0014\u00103\u001a\u0010\u0012\u0004\u0012\u000205\u0012\u0004\u0012\u00020\u001f\u0018\u0001042\b\u00106\u001a\u0004\u0018\u000107H\u0002J8\u0010?\u001a\u00020@2\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\b2\u0006\u0010'\u001a\u00020\u001f2\u0006\u0010A\u001a\u00020B2\u0006\u0010C\u001a\u00020D2\b\u00106\u001a\u0004\u0018\u000107JP\u0010E\u001a\u00020F2\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\b2\u0006\u0010'\u001a\u00020\u001f2\u0006\u0010G\u001a\u00020H2\u0014\u00103\u001a\u0010\u0012\u0004\u0012\u000205\u0012\u0004\u0012\u00020\u001f\u0018\u0001042\b\u00106\u001a\u0004\u0018\u0001072\u0006\u0010I\u001a\u00020JH\u0002J\u0012\u0010K\u001a\u0004\u0018\u00010L2\u0006\u0010M\u001a\u00020HH\u0002J(\u0010N\u001a\u00020J2\u000e\u0010O\u001a\n\u0012\u0006\b\u0001\u0012\u00020*0P2\u0006\u0010Q\u001a\u00020*2\u0006\u0010!\u001a\u00020\u001fH\u0002J4\u0010R\u001a\u00020S2\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\b2\u0006\u0010T\u001a\u00020U2\u0006\u0010\f\u001a\u00020\r2\n\u0010O\u001a\u0006\u0012\u0002\b\u00030PH\u0002J\u0018\u0010V\u001a\u00020J2\u0006\u0010)\u001a\u00020*2\u0006\u0010!\u001a\u00020\u001fH\u0002J\u0018\u0010W\u001a\u00020J2\u0006\u0010)\u001a\u00020*2\u0006\u0010!\u001a\u00020\u001fH\u0002J\u0018\u0010X\u001a\u00020Y2\u0006\u0010)\u001a\u00020H2\u0006\u0010I\u001a\u00020JH\u0002J\u0012\u0010Z\u001a\u0004\u0018\u00010\u001f*\u0006\u0012\u0002\b\u00030PH\u0002J\u0014\u0010[\u001a\u00020\\*\u00020\u001f2\u0006\u0010]\u001a\u00020JH\u0002J\f\u0010^\u001a\u00020\u001f*\u00020\u001fH\u0002¨\u0006`"}, d2 = {"Lorg/jetbrains/kotlin/psi2ir/generators/ReflectionReferencesGenerator;", "Lorg/jetbrains/kotlin/psi2ir/generators/StatementGeneratorExtension;", "statementGenerator", "Lorg/jetbrains/kotlin/psi2ir/generators/StatementGenerator;", "(Lorg/jetbrains/kotlin/psi2ir/generators/StatementGenerator;)V", "adaptResolvedValueArgument", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "startOffset", "", "endOffset", "resolvedValueArgument", "Lorg/jetbrains/kotlin/resolve/calls/model/ResolvedValueArgument;", "irAdapterFun", "Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;", "valueParameter", "Lorg/jetbrains/kotlin/descriptors/ValueParameterDescriptor;", "shift", "adaptValueArgument", "Lorg/jetbrains/kotlin/ir/expressions/IrVarargElement;", "valueArgument", "Lorg/jetbrains/kotlin/psi/ValueArgument;", "createAdapteeCall", "adapteeSymbol", "Lorg/jetbrains/kotlin/ir/symbols/IrFunctionSymbol;", "callBuilder", "Lorg/jetbrains/kotlin/psi2ir/intermediate/CallBuilder;", "createAdapterFun", "adapteeDescriptor", "Lorg/jetbrains/kotlin/descriptors/FunctionDescriptor;", "ktExpectedParameterTypes", "", "Lorg/jetbrains/kotlin/types/KotlinType;", "ktExpectedReturnType", "callableReferenceType", "createAdapterParameter", "Lorg/jetbrains/kotlin/ir/declarations/IrValueParameter;", "name", "Lorg/jetbrains/kotlin/name/Name;", "index", ModuleXmlParser.TYPE, "createFunInterfaceConstructorAdapter", "descriptor", "Lorg/jetbrains/kotlin/descriptors/CallableDescriptor;", "generateAdaptedCallableReference", "ktCallableReference", "Lorg/jetbrains/kotlin/psi/KtCallableReferenceExpression;", "generateCallableReference", "Lorg/jetbrains/kotlin/ir/expressions/IrCallableReference;", "ktElement", "Lorg/jetbrains/kotlin/psi/KtElement;", "callableDescriptor", "typeArguments", "", "Lorg/jetbrains/kotlin/descriptors/TypeParameterDescriptor;", "origin", "Lorg/jetbrains/kotlin/ir/expressions/IrStatementOrigin;", "generateClassLiteral", "ktClassLiteral", "Lorg/jetbrains/kotlin/psi/KtClassLiteralExpression;", "generateFunctionInterfaceConstructorReference", "generateFunctionReference", "Lorg/jetbrains/kotlin/ir/expressions/IrFunctionReference;", NativeSymbol.TYPE_NAME, "generateLocalDelegatedPropertyReference", "Lorg/jetbrains/kotlin/ir/expressions/IrLocalDelegatedPropertyReference;", "variableDescriptor", "Lorg/jetbrains/kotlin/descriptors/VariableDescriptorWithAccessors;", "irDelegateSymbol", "Lorg/jetbrains/kotlin/ir/symbols/IrVariableSymbol;", "generatePropertyReference", "Lorg/jetbrains/kotlin/ir/expressions/IrPropertyReference;", "propertyDescriptor", "Lorg/jetbrains/kotlin/descriptors/PropertyDescriptor;", "mutable", "", "getFieldForPropertyReference", "Lorg/jetbrains/kotlin/ir/symbols/IrFieldSymbol;", "originalProperty", "isAdaptedCallableReference", "resolvedCall", "Lorg/jetbrains/kotlin/resolve/calls/model/ResolvedCall;", "resolvedDescriptor", "putAdaptedValueArguments", "", "irAdapteeCall", "Lorg/jetbrains/kotlin/ir/expressions/IrFunctionAccessExpression;", "requiresCoercionToUnit", "requiresSuspendConversion", "resolvePropertySymbol", "Lorg/jetbrains/kotlin/psi2ir/generators/ReflectionReferencesGenerator$DelegatedPropertySymbols;", "getBoundReceiverType", "kFunctionTypeToFunctionType", "Lorg/jetbrains/kotlin/types/SimpleType;", "suspendFunction", "maybeKFunctionTypeToFunctionType", "DelegatedPropertySymbols", "ir.psi2ir"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ReflectionReferencesGenerator extends StatementGeneratorExtension {
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ReflectionReferencesGenerator(StatementGenerator statementGenerator) {
        super(statementGenerator);
        Intrinsics.checkNotNullParameter(statementGenerator, "statementGenerator");
    }

    public final IrExpression generateClassLiteral(KtClassLiteralExpression ktClassLiteral) {
        Intrinsics.checkNotNullParameter(ktClassLiteral, "ktClassLiteral");
        KtExpression receiverExpression = ktClassLiteral.getReceiverExpression();
        Intrinsics.checkNotNull(receiverExpression);
        ReflectionReferencesGenerator reflectionReferencesGenerator = this;
        WritableSlice<KtExpression, DoubleColonLHS> DOUBLE_COLON_LHS = BindingContext.DOUBLE_COLON_LHS;
        Intrinsics.checkNotNullExpressionValue(DOUBLE_COLON_LHS, "DOUBLE_COLON_LHS");
        DoubleColonLHS doubleColonLHS = (DoubleColonLHS) GeneratorKt.getOrFail(reflectionReferencesGenerator, DOUBLE_COLON_LHS, receiverExpression);
        IrType irType = toIrType(GeneratorKt.getTypeInferredByFrontendOrFail(reflectionReferencesGenerator, ktClassLiteral));
        if ((doubleColonLHS instanceof DoubleColonLHS.Expression) && !((DoubleColonLHS.Expression) doubleColonLHS).getIsObjectQualifier()) {
            KtClassLiteralExpression ktClassLiteralExpression = ktClassLiteral;
            return new IrGetClassImpl(PsiUtilsKt.getStartOffsetSkippingComments(ktClassLiteralExpression), PsiUtilsKt.getEndOffset(ktClassLiteralExpression), irType, genExpr(receiverExpression));
        }
        ClassifierDescriptor this$0 = doubleColonLHS.getType().getConstructor().getThis$0();
        if (this$0 == null) {
            throw new AssertionError("Unexpected type constructor for " + doubleColonLHS.getType() + ": " + this$0);
        }
        KtClassLiteralExpression ktClassLiteralExpression2 = ktClassLiteral;
        return new IrClassReferenceImpl(PsiUtilsKt.getStartOffsetSkippingComments(ktClassLiteralExpression2), PsiUtilsKt.getEndOffset(ktClassLiteralExpression2), irType, IrUtilsKt.referenceClassifier(getContext().getSymbolTable(), this$0), toIrType(doubleColonLHS.getType()));
    }

    public final IrExpression generateCallableReference(final KtCallableReferenceExpression ktCallableReference) {
        Intrinsics.checkNotNullParameter(ktCallableReference, "ktCallableReference");
        ReflectionReferencesGenerator reflectionReferencesGenerator = this;
        KtSimpleNameExpression callableReference = ktCallableReference.getCallableReference();
        Intrinsics.checkNotNullExpressionValue(callableReference, "getCallableReference(...)");
        ResolvedCall<? extends CallableDescriptor> resolvedCall = GeneratorKt.getResolvedCall(reflectionReferencesGenerator, callableReference);
        Intrinsics.checkNotNull(resolvedCall);
        CallableDescriptor resultingDescriptor = resolvedCall.getResultingDescriptor();
        final KotlinType kotlinTypeApproximateFunctionReferenceType = getContext().getTypeTranslator().approximateFunctionReferenceType(GeneratorKt.getTypeInferredByFrontendOrFail(reflectionReferencesGenerator, ktCallableReference));
        final CallBuilder callBuilderUnwrapCallableDescriptorAndTypeArguments = ArgumentsGenerationUtilsKt.unwrapCallableDescriptorAndTypeArguments(resolvedCall);
        if ((resultingDescriptor instanceof FunctionInterfaceConstructorDescriptor) || (resultingDescriptor.getOriginal() instanceof FunctionInterfaceConstructorDescriptor)) {
            return generateFunctionInterfaceConstructorReference(ktCallableReference, kotlinTypeApproximateFunctionReferenceType, callBuilderUnwrapCallableDescriptorAndTypeArguments.getDescriptor());
        }
        if (isAdaptedCallableReference(resolvedCall, resultingDescriptor, kotlinTypeApproximateFunctionReferenceType)) {
            return generateAdaptedCallableReference(ktCallableReference, callBuilderUnwrapCallableDescriptorAndTypeArguments, kotlinTypeApproximateFunctionReferenceType);
        }
        ReceiverValue dispatchReceiver = resolvedCall.getDispatchReceiver();
        ReceiverValue extensionReceiver = resolvedCall.getExtensionReceiver();
        List<ReceiverValue> contextReceivers = resolvedCall.getContextReceivers();
        Intrinsics.checkNotNullExpressionValue(contextReceivers, "getContextReceivers(...)");
        return ArgumentsGenerationUtilsKt.generateCallReceiver$default(getStatementGenerator(), ktCallableReference, resultingDescriptor, dispatchReceiver, extensionReceiver, contextReceivers, false, false, 64, null).call(new CallExpressionBuilder() { // from class: org.jetbrains.kotlin.psi2ir.generators.ReflectionReferencesGenerator$$ExternalSyntheticLambda0
            @Override // org.jetbrains.kotlin.psi2ir.intermediate.CallExpressionBuilder
            public final IrExpression withReceivers(IntermediateValue intermediateValue, IntermediateValue intermediateValue2, List list) {
                return ReflectionReferencesGenerator.generateCallableReference$lambda$1(this.f$0, ktCallableReference, kotlinTypeApproximateFunctionReferenceType, callBuilderUnwrapCallableDescriptorAndTypeArguments, intermediateValue, intermediateValue2, list);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final IrExpression generateCallableReference$lambda$1(ReflectionReferencesGenerator this$0, KtCallableReferenceExpression ktCallableReference, KotlinType callableReferenceType, CallBuilder callBuilder, IntermediateValue intermediateValue, IntermediateValue intermediateValue2, List list) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(ktCallableReference, "$ktCallableReference");
        Intrinsics.checkNotNullParameter(callableReferenceType, "$callableReferenceType");
        Intrinsics.checkNotNullParameter(callBuilder, "$callBuilder");
        Intrinsics.checkNotNullParameter(list, "<anonymous parameter 2>");
        IrCallableReference irCallableReferenceGenerateCallableReference$default = generateCallableReference$default(this$0, ktCallableReference, callableReferenceType, callBuilder.getDescriptor(), callBuilder.getTypeArguments(), null, 16, null);
        irCallableReferenceGenerateCallableReference$default.setDispatchReceiver(intermediateValue != null ? intermediateValue.loadIfExists() : null);
        irCallableReferenceGenerateCallableReference$default.setExtensionReceiver(intermediateValue2 != null ? intermediateValue2.loadIfExists() : null);
        return irCallableReferenceGenerateCallableReference$default;
    }

    private final boolean isAdaptedCallableReference(ResolvedCall<? extends CallableDescriptor> resolvedCall, CallableDescriptor resolvedDescriptor, KotlinType callableReferenceType) {
        Map<ValueParameterDescriptor, ResolvedValueArgument> valueArguments = resolvedCall.getValueArguments();
        Intrinsics.checkNotNullExpressionValue(valueArguments, "getValueArguments(...)");
        return !valueArguments.isEmpty() || requiresCoercionToUnit(resolvedDescriptor, callableReferenceType) || requiresSuspendConversion(resolvedDescriptor, callableReferenceType);
    }

    private final IrExpression generateFunctionInterfaceConstructorReference(KtCallableReferenceExpression ktCallableReference, KotlinType callableReferenceType, CallableDescriptor descriptor) {
        KtCallableReferenceExpression ktCallableReferenceExpression = ktCallableReference;
        int startOffsetSkippingComments = PsiUtilsKt.getStartOffsetSkippingComments(ktCallableReferenceExpression);
        int endOffset = PsiUtilsKt.getEndOffset(ktCallableReferenceExpression);
        IrType irType = toIrType(callableReferenceType);
        IrSimpleFunction irSimpleFunctionCreateFunInterfaceConstructorAdapter = createFunInterfaceConstructorAdapter(startOffsetSkippingComments, endOffset, descriptor);
        return new IrBlockImpl(startOffsetSkippingComments, endOffset, irType, IrStatementOrigin.FUN_INTERFACE_CONSTRUCTOR_REFERENCE.INSTANCE, CollectionsKt.listOf((Object[]) new IrElement[]{irSimpleFunctionCreateFunInterfaceConstructorAdapter, new IrFunctionReferenceImpl(startOffsetSkippingComments, endOffset, irType, irSimpleFunctionCreateFunInterfaceConstructorAdapter.getSymbol(), irSimpleFunctionCreateFunInterfaceConstructorAdapter.getTypeParameters().size(), irSimpleFunctionCreateFunInterfaceConstructorAdapter.getValueParameters().size(), irSimpleFunctionCreateFunInterfaceConstructorAdapter.getSymbol(), IrStatementOrigin.FUN_INTERFACE_CONSTRUCTOR_REFERENCE.INSTANCE)}));
    }

    private final IrSimpleFunction createFunInterfaceConstructorAdapter(int startOffset, int endOffset, CallableDescriptor descriptor) {
        KotlinType returnType = descriptor.getReturnType();
        if (returnType == null) {
            throw new AssertionError("Unresolved return type: " + descriptor);
        }
        ClassifierDescriptor this$0 = returnType.getConstructor().getThis$0();
        ClassDescriptor classDescriptor = this$0 instanceof ClassDescriptor ? (ClassDescriptor) this$0 : null;
        if (classDescriptor == null) {
            throw new AssertionError("Class type expected: " + returnType);
        }
        IrType irType = toIrType(returnType);
        List<ValueParameterDescriptor> valueParameters = descriptor.getValueParameters();
        Intrinsics.checkNotNullExpressionValue(valueParameters, "getValueParameters(...)");
        ValueParameterDescriptor valueParameterDescriptor = (ValueParameterDescriptor) CollectionsKt.singleOrNull((List) valueParameters);
        if (valueParameterDescriptor == null) {
            throw new AssertionError("Single value parameter expected: " + descriptor);
        }
        IrFactory irFactory = getContext().getIrFactory();
        IrDeclarationOrigin.ADAPTER_FOR_FUN_INTERFACE_CONSTRUCTOR adapter_for_fun_interface_constructor = IrDeclarationOrigin.ADAPTER_FOR_FUN_INTERFACE_CONSTRUCTOR.INSTANCE;
        Name name2 = classDescriptor.getShortName();
        Intrinsics.checkNotNullExpressionValue(name2, "getName(...)");
        DescriptorVisibility LOCAL = DescriptorVisibilities.LOCAL;
        Intrinsics.checkNotNullExpressionValue(LOCAL, "LOCAL");
        IrSimpleFunction irSimpleFunctionCreateSimpleFunction$default = IrFactory.DefaultImpls.createSimpleFunction$default(irFactory, startOffset, endOffset, adapter_for_fun_interface_constructor, name2, LOCAL, false, false, irType, Modality.FINAL, new IrSimpleFunctionSymbolImpl(null, 1, null), false, false, false, false, false, null, false, 98304, null);
        SymbolTable symbolTable = getContext().getSymbolTable();
        IrSimpleFunction irSimpleFunction = irSimpleFunctionCreateSimpleFunction$default;
        symbolTable.enterScope(irSimpleFunction);
        irSimpleFunctionCreateSimpleFunction$default.setMetadata(null);
        irSimpleFunctionCreateSimpleFunction$default.setDispatchReceiverParameter(null);
        irSimpleFunctionCreateSimpleFunction$default.setExtensionReceiverParameter(null);
        KotlinType type = valueParameterDescriptor.getType();
        Intrinsics.checkNotNullExpressionValue(type, "getType(...)");
        Name name3 = valueParameterDescriptor.getShortName();
        Intrinsics.checkNotNullExpressionValue(name3, "getName(...)");
        IrValueParameter irValueParameterCreateAdapterParameter = createAdapterParameter(startOffset, endOffset, name3, 0, type);
        IrType type2 = irValueParameterCreateAdapterParameter.getType();
        FunctionDescriptor descriptor2 = getContext().getIrBuiltIns().getCheckNotNullSymbol().getDescriptor();
        FunctionDescriptor functionDescriptorSubstitute = descriptor2.substitute(TypeSubstitutor.create((Map<TypeConstructor, TypeProjection>) MapsKt.mapOf(TuplesKt.to(descriptor2.getTypeParameters().get(0).getTypeConstructor(), new TypeProjectionImpl(type)))));
        if (functionDescriptorSubstitute == null) {
            throw new AssertionError("Substitution failed for " + descriptor2 + ": T=" + type);
        }
        Intrinsics.checkNotNull(functionDescriptorSubstitute);
        irSimpleFunctionCreateSimpleFunction$default.setValueParameters(CollectionsKt.listOf(irValueParameterCreateAdapterParameter));
        IrBlockBodyBuilder irBlockBodyBuilder = new IrBlockBodyBuilder(getContext(), new Scope(irSimpleFunctionCreateSimpleFunction$default.getSymbol()), startOffset, endOffset);
        IrBlockBodyBuilder irBlockBodyBuilder2 = irBlockBodyBuilder;
        IrCall irCall = ExpressionHelpersKt.irCall((IrBuilderWithScope) irBlockBodyBuilder2, irBlockBodyBuilder.getContext().getIrBuiltIns().getCheckNotNullSymbol());
        getContext().getCallToSubstitutedDescriptorMap$ir_psi2ir().put(irCall, functionDescriptorSubstitute);
        irCall.setType(type2);
        irCall.putTypeArgument(0, type2);
        irCall.putValueArgument(0, ExpressionHelpersKt.irGet(irBlockBodyBuilder2, irValueParameterCreateAdapterParameter));
        Unit unit = Unit.INSTANCE;
        irBlockBodyBuilder.unaryPlus(ExpressionHelpersKt.irReturn(irBlockBodyBuilder2, ExpressionHelpersKt.irSamConversion(irBlockBodyBuilder2, irCall, irType)));
        irSimpleFunctionCreateSimpleFunction$default.setBody(irBlockBodyBuilder.getIrBlockBody());
        Unit unit2 = Unit.INSTANCE;
        symbolTable.leaveScope(irSimpleFunction);
        return irSimpleFunctionCreateSimpleFunction$default;
    }

    private final boolean requiresCoercionToUnit(CallableDescriptor descriptor, KotlinType callableReferenceType) {
        KotlinType nullableAnyType = ((TypeProjection) CollectionsKt.last((List) callableReferenceType.getArguments())).getNullableAnyType();
        Intrinsics.checkNotNullExpressionValue(nullableAnyType, "getType(...)");
        if (KotlinBuiltIns.isUnit(nullableAnyType)) {
            KotlinType returnType = descriptor.getReturnType();
            Intrinsics.checkNotNull(returnType);
            if (!KotlinBuiltIns.isUnit(returnType)) {
                return true;
            }
        }
        return false;
    }

    private final boolean requiresSuspendConversion(CallableDescriptor descriptor, KotlinType callableReferenceType) {
        return (descriptor instanceof FunctionDescriptor) && !((FunctionDescriptor) descriptor).isSuspend() && FunctionTypesKt.isKSuspendFunctionType(callableReferenceType);
    }

    private final IrExpression generateAdaptedCallableReference(KtCallableReferenceExpression ktCallableReference, CallBuilder callBuilder, KotlinType callableReferenceType) {
        final CallableDescriptor descriptor = callBuilder.getDescriptor();
        if (!(descriptor instanceof FunctionDescriptor)) {
            throw new AssertionError("Function descriptor expected in adapted callable reference: " + descriptor);
        }
        KtCallableReferenceExpression ktCallableReferenceExpression = ktCallableReference;
        final int startOffsetSkippingComments = PsiUtilsKt.getStartOffsetSkippingComments(ktCallableReferenceExpression);
        final int endOffset = PsiUtilsKt.getEndOffset(ktCallableReferenceExpression);
        SymbolTable symbolTable = getContext().getSymbolTable();
        FunctionDescriptor functionDescriptor = (FunctionDescriptor) descriptor;
        FunctionDescriptor original = functionDescriptor.getOriginal();
        Intrinsics.checkNotNullExpressionValue(original, "getOriginal(...)");
        final IrFunctionSymbol irFunctionSymbolReferenceFunction = IrUtilsKt.referenceFunction(symbolTable, original);
        KotlinType typeInferredByFrontendOrFail = GeneratorKt.getTypeInferredByFrontendOrFail(this, ktCallableReference);
        final IrType irType = toIrType(maybeKFunctionTypeToFunctionType(typeInferredByFrontendOrFail));
        List<TypeProjection> arguments = typeInferredByFrontendOrFail.getArguments();
        KotlinType nullableAnyType = ((TypeProjection) CollectionsKt.last((List) arguments)).getNullableAnyType();
        Intrinsics.checkNotNullExpressionValue(nullableAnyType, "getType(...)");
        List listTake = CollectionsKt.take(arguments, arguments.size() - 1);
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(listTake, 10));
        Iterator it = listTake.iterator();
        while (it.hasNext()) {
            arrayList.add(((TypeProjection) it.next()).getNullableAnyType());
        }
        final IrSimpleFunction irSimpleFunctionCreateAdapterFun = createAdapterFun(startOffsetSkippingComments, endOffset, functionDescriptor, arrayList, nullableAnyType, callBuilder, callableReferenceType);
        IrExpression irExpressionCreateAdapteeCall = createAdapteeCall(startOffsetSkippingComments, endOffset, irFunctionSymbolReferenceFunction, callBuilder, irSimpleFunctionCreateAdapterFun);
        IrBlockBody irBlockBodyCreateBlockBody = getContext().getIrFactory().createBlockBody(startOffsetSkippingComments, endOffset);
        if (KotlinBuiltIns.isUnit(nullableAnyType)) {
            irBlockBodyCreateBlockBody.getStatements().add(irExpressionCreateAdapteeCall);
        } else {
            irBlockBodyCreateBlockBody.getStatements().add(new IrReturnImpl(startOffsetSkippingComments, endOffset, getContext().getIrBuiltIns().getNothingType(), irSimpleFunctionCreateAdapterFun.getSymbol(), irExpressionCreateAdapteeCall));
        }
        irSimpleFunctionCreateAdapterFun.setBody(irBlockBodyCreateBlockBody);
        ResolvedCall<?> original2 = callBuilder.getOriginal();
        CallableDescriptor resultingDescriptor = original2.getResultingDescriptor();
        Intrinsics.checkNotNullExpressionValue(resultingDescriptor, "getResultingDescriptor(...)");
        ReceiverValue dispatchReceiver = original2.getDispatchReceiver();
        ReceiverValue extensionReceiver = original2.getExtensionReceiver();
        List<ReceiverValue> contextReceivers = original2.getContextReceivers();
        Intrinsics.checkNotNullExpressionValue(contextReceivers, "getContextReceivers(...)");
        return ArgumentsGenerationUtilsKt.generateCallReceiver$default(getStatementGenerator(), ktCallableReference, resultingDescriptor, dispatchReceiver, extensionReceiver, contextReceivers, false, false, 64, null).call(new CallExpressionBuilder() { // from class: org.jetbrains.kotlin.psi2ir.generators.ReflectionReferencesGenerator$$ExternalSyntheticLambda1
            @Override // org.jetbrains.kotlin.psi2ir.intermediate.CallExpressionBuilder
            public final IrExpression withReceivers(IntermediateValue intermediateValue, IntermediateValue intermediateValue2, List list) {
                return ReflectionReferencesGenerator.generateAdaptedCallableReference$lambda$11(startOffsetSkippingComments, endOffset, irType, irSimpleFunctionCreateAdapterFun, irFunctionSymbolReferenceFunction, descriptor, intermediateValue, intermediateValue2, list);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final IrExpression generateAdaptedCallableReference$lambda$11(int i, int i2, IrType irFunctionalType, IrSimpleFunction irAdapterFun, IrFunctionSymbol adapteeSymbol, CallableDescriptor adapteeDescriptor, IntermediateValue intermediateValue, IntermediateValue intermediateValue2, List list) {
        Intrinsics.checkNotNullParameter(irFunctionalType, "$irFunctionalType");
        Intrinsics.checkNotNullParameter(irAdapterFun, "$irAdapterFun");
        Intrinsics.checkNotNullParameter(adapteeSymbol, "$adapteeSymbol");
        Intrinsics.checkNotNullParameter(adapteeDescriptor, "$adapteeDescriptor");
        Intrinsics.checkNotNullParameter(list, "<anonymous parameter 2>");
        IrExpression irExpressionLoadIfExists = intermediateValue != null ? intermediateValue.loadIfExists() : null;
        IrExpression irExpressionLoadIfExists2 = intermediateValue2 != null ? intermediateValue2.loadIfExists() : null;
        if (irExpressionLoadIfExists != null && irExpressionLoadIfExists2 != null) {
            throw new IllegalStateException(("Bound callable reference cannot have both receivers: " + adapteeDescriptor).toString());
        }
        IrExpression irExpression = irExpressionLoadIfExists == null ? irExpressionLoadIfExists2 : irExpressionLoadIfExists;
        IrFunctionReferenceImpl irFunctionReferenceImpl = new IrFunctionReferenceImpl(i, i2, irFunctionalType, irAdapterFun.getSymbol(), irAdapterFun.getTypeParameters().size(), irAdapterFun.getValueParameters().size(), adapteeSymbol, IrStatementOrigin.ADAPTED_FUNCTION_REFERENCE.INSTANCE);
        IrBlockImpl irBlockImpl = new IrBlockImpl(i, i2, irFunctionalType, IrStatementOrigin.ADAPTED_FUNCTION_REFERENCE.INSTANCE);
        irBlockImpl.getStatements().add(irAdapterFun);
        List<IrStatement> statements = irBlockImpl.getStatements();
        irFunctionReferenceImpl.setExtensionReceiver(irExpression);
        statements.add(irFunctionReferenceImpl);
        return irBlockImpl;
    }

    private final IrExpression createAdapteeCall(int startOffset, int endOffset, IrFunctionSymbol adapteeSymbol, CallBuilder callBuilder, IrSimpleFunction irAdapterFun) {
        IrCallImpl irCallImplFromSymbolDescriptor;
        ResolvedCall<?> original = callBuilder.getOriginal();
        CallableDescriptor resultingDescriptor = original.getResultingDescriptor();
        KotlinType returnType = resultingDescriptor.getReturnType();
        Intrinsics.checkNotNull(returnType);
        IrType irType = toIrType(returnType);
        if (adapteeSymbol instanceof IrConstructorSymbol) {
            irCallImplFromSymbolDescriptor = IrConstructorCallImpl.Companion.fromSymbolDescriptor$default(IrConstructorCallImpl.INSTANCE, startOffset, endOffset, irType, (IrConstructorSymbol) adapteeSymbol, null, 16, null);
        } else if (adapteeSymbol instanceof IrSimpleFunctionSymbol) {
            IrSimpleFunctionSymbol irSimpleFunctionSymbol = (IrSimpleFunctionSymbol) adapteeSymbol;
            irCallImplFromSymbolDescriptor = IrCallImpl.INSTANCE.fromSymbolDescriptor(startOffset, endOffset, irType, irSimpleFunctionSymbol, (176 & 16) != 0 ? IrExpressionsKt.getTypeParametersCount(irSimpleFunctionSymbol.getDescriptor()) : 0, (176 & 32) != 0 ? irSimpleFunctionSymbol.getDescriptor().getValueParameters().size() + irSimpleFunctionSymbol.getDescriptor().getContextReceiverParameters().size() : 0, (176 & 64) != 0 ? null : null, (176 & 128) != 0 ? null : null);
        } else {
            throw new NoWhenBranchMatchedException();
        }
        IrFunctionAccessExpression irFunctionAccessExpression = irCallImplFromSymbolDescriptor;
        boolean z = (original.getDispatchReceiver() == null || (original.getDispatchReceiver() instanceof TransientReceiver)) ? false : true;
        boolean z2 = (original.getExtensionReceiver() == null || (original.getExtensionReceiver() instanceof TransientReceiver)) ? false : true;
        boolean z3 = callBuilder.getOriginal().getResultingDescriptor() instanceof ImportedFromObjectCallableDescriptor;
        if (z || z2 || z3) {
            IrValueParameter extensionReceiverParameter = irAdapterFun.getExtensionReceiverParameter();
            Intrinsics.checkNotNull(extensionReceiverParameter);
            IrGetValueImpl irGetValueImpl = new IrGetValueImpl(startOffset, endOffset, extensionReceiverParameter.getSymbol(), IrStatementOrigin.ADAPTED_FUNCTION_REFERENCE.INSTANCE);
            if (z || z3) {
                irFunctionAccessExpression.setDispatchReceiver(irGetValueImpl);
            } else if (z2) {
                irFunctionAccessExpression.setExtensionReceiver(irGetValueImpl);
            }
        }
        Map<IrDeclarationReference, CallableDescriptor> callToSubstitutedDescriptorMap$ir_psi2ir = getContext().getCallToSubstitutedDescriptorMap$ir_psi2ir();
        Intrinsics.checkNotNull(resultingDescriptor);
        callToSubstitutedDescriptorMap$ir_psi2ir.put(irFunctionAccessExpression, resultingDescriptor);
        IrFunctionAccessExpression irFunctionAccessExpression2 = irFunctionAccessExpression;
        Map<TypeParameterDescriptor, KotlinType> typeArguments = callBuilder.getTypeArguments();
        if (typeArguments != null) {
            for (Map.Entry<TypeParameterDescriptor, KotlinType> entry : typeArguments.entrySet()) {
                irFunctionAccessExpression2.putTypeArgument(entry.getKey().getIndex(), toIrType(entry.getValue()));
            }
        }
        putAdaptedValueArguments(startOffset, endOffset, irFunctionAccessExpression, irAdapterFun, original);
        return irFunctionAccessExpression;
    }

    private final void putAdaptedValueArguments(int startOffset, int endOffset, IrFunctionAccessExpression irAdapteeCall, IrSimpleFunction irAdapterFun, ResolvedCall<?> resolvedCall) {
        Map<ValueParameterDescriptor, ResolvedValueArgument> valueArguments = resolvedCall.getValueArguments();
        Intrinsics.checkNotNullExpressionValue(valueArguments, "getValueArguments(...)");
        int i = 0;
        if (resolvedCall.getDispatchReceiver() instanceof TransientReceiver) {
            IrValueParameter irValueParameter = irAdapterFun.getValueParameters().get(0);
            irAdapteeCall.setDispatchReceiver(new IrGetValueImpl(startOffset, endOffset, irValueParameter.getType(), irValueParameter.getSymbol(), null, 16, null));
        } else if (resolvedCall.getExtensionReceiver() instanceof TransientReceiver) {
            IrValueParameter irValueParameter2 = irAdapterFun.getValueParameters().get(0);
            irAdapteeCall.setExtensionReceiver(new IrGetValueImpl(startOffset, endOffset, irValueParameter2.getType(), irValueParameter2.getSymbol(), null, 16, null));
            i = 1;
        }
        for (Map.Entry<ValueParameterDescriptor, ResolvedValueArgument> entry : valueArguments.entrySet()) {
            ValueParameterDescriptor key = entry.getKey();
            ResolvedValueArgument value = entry.getValue();
            ValueParameterDescriptor valueParameterDescriptor = resolvedCall.getResultingDescriptor().getValueParameters().get(key.getIndex());
            int index = key.getIndex();
            Intrinsics.checkNotNull(value);
            Intrinsics.checkNotNull(valueParameterDescriptor);
            irAdapteeCall.putValueArgument(index, adaptResolvedValueArgument(startOffset, endOffset, value, irAdapterFun, valueParameterDescriptor, i));
        }
    }

    private final IrExpression adaptResolvedValueArgument(int startOffset, int endOffset, ResolvedValueArgument resolvedValueArgument, IrSimpleFunction irAdapterFun, ValueParameterDescriptor valueParameter, int shift) {
        IrVarargImpl irVarargImpl = null;
        if (resolvedValueArgument instanceof DefaultValueArgument) {
            return null;
        }
        if (resolvedValueArgument instanceof VarargValueArgument) {
            VarargValueArgument varargValueArgument = (VarargValueArgument) resolvedValueArgument;
            if (!varargValueArgument.getArguments().isEmpty()) {
                KotlinType type = valueParameter.getType();
                Intrinsics.checkNotNullExpressionValue(type, "getType(...)");
                IrType irType = toIrType(type);
                KotlinType varargElementType = valueParameter.getVarargElementType();
                Intrinsics.checkNotNull(varargElementType);
                IrType irType2 = toIrType(varargElementType);
                List<ValueArgument> arguments = varargValueArgument.getArguments();
                Intrinsics.checkNotNullExpressionValue(arguments, "getArguments(...)");
                List<ValueArgument> list = arguments;
                ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
                for (ValueArgument valueArgument : list) {
                    Intrinsics.checkNotNull(valueArgument);
                    arrayList.add(adaptValueArgument(startOffset, endOffset, valueArgument, irAdapterFun, shift));
                }
                irVarargImpl = new IrVarargImpl(startOffset, endOffset, irType, irType2, arrayList);
            }
            return irVarargImpl;
        }
        if (resolvedValueArgument instanceof ExpressionValueArgument) {
            ValueArgument valueArgument2 = ((ExpressionValueArgument) resolvedValueArgument).getValueArgument();
            Intrinsics.checkNotNull(valueArgument2);
            IrVarargElement irVarargElementAdaptValueArgument = adaptValueArgument(startOffset, endOffset, valueArgument2, irAdapterFun, shift);
            Intrinsics.checkNotNull(irVarargElementAdaptValueArgument, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.expressions.IrExpression");
            return (IrExpression) irVarargElementAdaptValueArgument;
        }
        throw new AssertionError("Unexpected ResolvedValueArgument: " + resolvedValueArgument);
    }

    private final IrVarargElement adaptValueArgument(int startOffset, int endOffset, ValueArgument valueArgument, IrSimpleFunction irAdapterFun, int shift) {
        if (valueArgument instanceof FakeImplicitSpreadValueArgumentForCallableReference) {
            IrVarargElement irVarargElementAdaptValueArgument = adaptValueArgument(startOffset, endOffset, ((FakeImplicitSpreadValueArgumentForCallableReference) valueArgument).getExpression(), irAdapterFun, shift);
            Intrinsics.checkNotNull(irVarargElementAdaptValueArgument, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.expressions.IrExpression");
            return new IrSpreadElementImpl(startOffset, endOffset, (IrExpression) irVarargElementAdaptValueArgument);
        }
        if (valueArgument instanceof FakePositionalValueArgumentForCallableReference) {
            IrValueParameter irValueParameter = irAdapterFun.getValueParameters().get(((FakePositionalValueArgumentForCallableReference) valueArgument).getIndex() + shift);
            return new IrGetValueImpl(startOffset, endOffset, irValueParameter.getType(), irValueParameter.getSymbol(), null, 16, null);
        }
        throw new AssertionError("Unexpected ValueArgument: " + valueArgument);
    }

    private final IrSimpleFunction createAdapterFun(int startOffset, int endOffset, FunctionDescriptor adapteeDescriptor, List<? extends KotlinType> ktExpectedParameterTypes, KotlinType ktExpectedReturnType, CallBuilder callBuilder, KotlinType callableReferenceType) {
        int i = 0;
        boolean z = !adapteeDescriptor.isSuspend() && FunctionTypesKt.isKSuspendFunctionType(callableReferenceType);
        IrFactory irFactory = getContext().getIrFactory();
        IrDeclarationOrigin.ADAPTER_FOR_CALLABLE_REFERENCE adapter_for_callable_reference = IrDeclarationOrigin.ADAPTER_FOR_CALLABLE_REFERENCE.INSTANCE;
        Name name2 = adapteeDescriptor.getShortName();
        Intrinsics.checkNotNullExpressionValue(name2, "getName(...)");
        DescriptorVisibility LOCAL = DescriptorVisibilities.LOCAL;
        Intrinsics.checkNotNullExpressionValue(LOCAL, "LOCAL");
        IrSimpleFunction irSimpleFunctionCreateSimpleFunction$default = IrFactory.DefaultImpls.createSimpleFunction$default(irFactory, startOffset, endOffset, adapter_for_callable_reference, name2, LOCAL, adapteeDescriptor.isInline(), false, toIrType(ktExpectedReturnType), Modality.FINAL, new IrSimpleFunctionSymbolImpl(null, 1, null), false, adapteeDescriptor.isSuspend() || z, adapteeDescriptor.isOperator(), adapteeDescriptor.isInfix(), false, null, false, 98304, null);
        SymbolTable symbolTable = getContext().getSymbolTable();
        IrSimpleFunction irSimpleFunction = irSimpleFunctionCreateSimpleFunction$default;
        symbolTable.enterScope(irSimpleFunction);
        irSimpleFunctionCreateSimpleFunction$default.setMetadata(new DescriptorMetadataSource.Function(adapteeDescriptor));
        irSimpleFunctionCreateSimpleFunction$default.setDispatchReceiverParameter(null);
        KotlinType boundReceiverType = getBoundReceiverType(callBuilder.getOriginal());
        if (boundReceiverType != null) {
            Name nameIdentifier = Name.identifier("receiver");
            Intrinsics.checkNotNullExpressionValue(nameIdentifier, "identifier(...)");
            irSimpleFunctionCreateSimpleFunction$default.setExtensionReceiverParameter(createAdapterParameter(startOffset, endOffset, nameIdentifier, -1, boundReceiverType));
        } else {
            irSimpleFunctionCreateSimpleFunction$default.setExtensionReceiverParameter(null);
        }
        List<IrValueParameter> valueParameters = irSimpleFunctionCreateSimpleFunction$default.getValueParameters();
        List<? extends KotlinType> list = ktExpectedParameterTypes;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
        Iterator<T> it = list.iterator();
        while (true) {
            int i2 = i;
            if (!it.hasNext()) {
                irSimpleFunctionCreateSimpleFunction$default.setValueParameters(CollectionsKt.plus((Collection) valueParameters, (Iterable) arrayList));
                Unit unit = Unit.INSTANCE;
                symbolTable.leaveScope(irSimpleFunction);
                return irSimpleFunctionCreateSimpleFunction$default;
            }
            Object next = it.next();
            i = i2 + 1;
            if (i2 < 0) {
                CollectionsKt.throwIndexOverflow();
            }
            Name nameIdentifier2 = Name.identifier("p" + i2);
            Intrinsics.checkNotNullExpressionValue(nameIdentifier2, "identifier(...)");
            arrayList.add(createAdapterParameter(startOffset, endOffset, nameIdentifier2, i2, (KotlinType) next));
        }
    }

    private final KotlinType getBoundReceiverType(ResolvedCall<?> resolvedCall) {
        CallableDescriptor resultingDescriptor = resolvedCall.getResultingDescriptor();
        if (resultingDescriptor instanceof ImportedFromObjectCallableDescriptor) {
            return ((ImportedFromObjectCallableDescriptor) resultingDescriptor).getContainingObject().getDefaultType();
        }
        ReceiverValue dispatchReceiver = resolvedCall.getDispatchReceiver();
        if (dispatchReceiver instanceof TransientReceiver) {
            dispatchReceiver = null;
        }
        ReceiverValue extensionReceiver = resolvedCall.getExtensionReceiver();
        if (extensionReceiver instanceof TransientReceiver) {
            extensionReceiver = null;
        }
        if (dispatchReceiver == null) {
            if (extensionReceiver != null) {
                return extensionReceiver.getType();
            }
            return null;
        }
        if (extensionReceiver == null) {
            return dispatchReceiver.getType();
        }
        throw new IllegalStateException(("Bound callable references can't have both receivers: " + resolvedCall.getResultingDescriptor()).toString());
    }

    private final IrValueParameter createAdapterParameter(int startOffset, int endOffset, Name name2, int index, KotlinType type) {
        return getContext().getIrFactory().createValueParameter(startOffset, endOffset, (IrDeclarationOrigin) IrDeclarationOrigin.ADAPTER_PARAMETER_FOR_CALLABLE_REFERENCE.INSTANCE, name2, toIrType(type), false, (IrValueParameterSymbol) new IrValueParameterSymbolImpl(null, 1, null), index, (IrType) null, false, false, false);
    }

    public static /* synthetic */ IrCallableReference generateCallableReference$default(ReflectionReferencesGenerator reflectionReferencesGenerator, KtElement ktElement, KotlinType kotlinType, CallableDescriptor callableDescriptor, Map map, IrStatementOrigin irStatementOrigin, int i, Object obj) {
        if ((i & 16) != 0) {
            irStatementOrigin = null;
        }
        return reflectionReferencesGenerator.generateCallableReference(ktElement, kotlinType, callableDescriptor, map, irStatementOrigin);
    }

    public final IrCallableReference<?> generateCallableReference(KtElement ktElement, KotlinType type, CallableDescriptor callableDescriptor, Map<TypeParameterDescriptor, ? extends KotlinType> typeArguments, IrStatementOrigin origin) {
        Intrinsics.checkNotNullParameter(ktElement, "ktElement");
        Intrinsics.checkNotNullParameter(type, "type");
        Intrinsics.checkNotNullParameter(callableDescriptor, "callableDescriptor");
        KtElement ktElement2 = ktElement;
        int startOffsetSkippingComments = PsiUtilsKt.getStartOffsetSkippingComments(ktElement2);
        int endOffset = PsiUtilsKt.getEndOffset(ktElement2);
        if (callableDescriptor instanceof FunctionDescriptor) {
            SymbolTable symbolTable = getContext().getSymbolTable();
            FunctionDescriptor functionDescriptor = (FunctionDescriptor) callableDescriptor;
            FunctionDescriptor original = functionDescriptor.getOriginal();
            Intrinsics.checkNotNullExpressionValue(original, "getOriginal(...)");
            return generateFunctionReference(startOffsetSkippingComments, endOffset, type, IrUtilsKt.referenceFunction(symbolTable, original), functionDescriptor, typeArguments, origin);
        }
        if (callableDescriptor instanceof PropertyDescriptor) {
            return generatePropertyReference(startOffsetSkippingComments, endOffset, type, (PropertyDescriptor) callableDescriptor, typeArguments, origin, ReflectionTypes.INSTANCE.isNumberedKMutablePropertyType(type));
        }
        throw new AssertionError("Unexpected callable reference: " + callableDescriptor);
    }

    public final IrLocalDelegatedPropertyReference generateLocalDelegatedPropertyReference(int startOffset, int endOffset, KotlinType type, VariableDescriptorWithAccessors variableDescriptor, IrVariableSymbol irDelegateSymbol, IrStatementOrigin origin) {
        Intrinsics.checkNotNullParameter(type, "type");
        Intrinsics.checkNotNullParameter(variableDescriptor, "variableDescriptor");
        Intrinsics.checkNotNullParameter(irDelegateSymbol, "irDelegateSymbol");
        VariableAccessorDescriptor getter = variableDescriptor.getGetter();
        if (getter == null) {
            throw new AssertionError("Local delegated property should have a getter: " + variableDescriptor);
        }
        VariableAccessorDescriptor setter = variableDescriptor.getSetter();
        IrLocalDelegatedPropertyReferenceImpl irLocalDelegatedPropertyReferenceImpl = new IrLocalDelegatedPropertyReferenceImpl(startOffset, endOffset, toIrType(type), getContext().getSymbolTable().getDescriptorExtension().referenceLocalDelegatedProperty(variableDescriptor), irDelegateSymbol, getContext().getSymbolTable().getDescriptorExtension().referenceSimpleFunction(getter), setter != null ? getContext().getSymbolTable().getDescriptorExtension().referenceSimpleFunction(setter) : null, origin);
        getContext().getCallToSubstitutedDescriptorMap$ir_psi2ir().put(irLocalDelegatedPropertyReferenceImpl, variableDescriptor);
        return irLocalDelegatedPropertyReferenceImpl;
    }

    /* JADX INFO: compiled from: ReflectionReferencesGenerator.kt */
    @Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\b\u0002\u0018\u00002\u00020\u0001B!\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u0012\b\u0010\u0006\u001a\u0004\u0018\u00010\u0005¢\u0006\u0002\u0010\u0007R\u0013\u0010\u0004\u001a\u0004\u0018\u00010\u0005¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0013\u0010\u0006\u001a\u0004\u0018\u00010\u0005¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\t¨\u0006\r"}, d2 = {"Lorg/jetbrains/kotlin/psi2ir/generators/ReflectionReferencesGenerator$DelegatedPropertySymbols;", "", "propertySymbol", "Lorg/jetbrains/kotlin/ir/symbols/IrPropertySymbol;", "getterSymbol", "Lorg/jetbrains/kotlin/ir/symbols/IrSimpleFunctionSymbol;", "setterSymbol", "(Lorg/jetbrains/kotlin/ir/symbols/IrPropertySymbol;Lorg/jetbrains/kotlin/ir/symbols/IrSimpleFunctionSymbol;Lorg/jetbrains/kotlin/ir/symbols/IrSimpleFunctionSymbol;)V", "getGetterSymbol", "()Lorg/jetbrains/kotlin/ir/symbols/IrSimpleFunctionSymbol;", "getPropertySymbol", "()Lorg/jetbrains/kotlin/ir/symbols/IrPropertySymbol;", "getSetterSymbol", "ir.psi2ir"}, k = 1, mv = {1, 9, 0}, xi = 48)
    private static final class DelegatedPropertySymbols {
        private final IrSimpleFunctionSymbol getterSymbol;
        private final IrPropertySymbol propertySymbol;
        private final IrSimpleFunctionSymbol setterSymbol;

        public DelegatedPropertySymbols(IrPropertySymbol propertySymbol, IrSimpleFunctionSymbol irSimpleFunctionSymbol, IrSimpleFunctionSymbol irSimpleFunctionSymbol2) {
            Intrinsics.checkNotNullParameter(propertySymbol, "propertySymbol");
            this.propertySymbol = propertySymbol;
            this.getterSymbol = irSimpleFunctionSymbol;
            this.setterSymbol = irSimpleFunctionSymbol2;
        }

        public final IrPropertySymbol getPropertySymbol() {
            return this.propertySymbol;
        }

        public final IrSimpleFunctionSymbol getGetterSymbol() {
            return this.getterSymbol;
        }

        public final IrSimpleFunctionSymbol getSetterSymbol() {
            return this.setterSymbol;
        }
    }

    private final DelegatedPropertySymbols resolvePropertySymbol(final PropertyDescriptor descriptor, boolean mutable) {
        PropertySetterDescriptor setter;
        final IrPropertySymbol irPropertySymbolReferenceProperty = getContext().getSymbolTable().getDescriptorExtension().referenceProperty(descriptor);
        Pair<FunctionDescriptor, FunctionDescriptor> pairUnwrapSyntheticJavaProperty = getContext().getExtensions().unwrapSyntheticJavaProperty(descriptor);
        IrSimpleFunctionSymbol irSimpleFunctionSymbolReferenceSimpleFunction = null;
        if (pairUnwrapSyntheticJavaProperty != null) {
            FunctionDescriptor functionDescriptorComponent1 = pairUnwrapSyntheticJavaProperty.component1();
            FunctionDescriptor functionDescriptorComponent2 = pairUnwrapSyntheticJavaProperty.component2();
            IrSimpleFunctionSymbol irSimpleFunctionSymbolReferenceSimpleFunction2 = getContext().getSymbolTable().getDescriptorExtension().referenceSimpleFunction(functionDescriptorComponent1);
            if (mutable && functionDescriptorComponent2 != null) {
                irSimpleFunctionSymbolReferenceSimpleFunction = getContext().getSymbolTable().getDescriptorExtension().referenceSimpleFunction(functionDescriptorComponent2);
            }
            if (!irPropertySymbolReferenceProperty.isBound()) {
                final int i = -1;
                getContext().getSymbolTable().getDescriptorExtension().declareProperty(descriptor, new Function1<IrPropertySymbol, IrProperty>() { // from class: org.jetbrains.kotlin.psi2ir.generators.ReflectionReferencesGenerator.resolvePropertySymbol.1
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(1);
                    }

                    @Override // kotlin.jvm.functions.Function1
                    public final IrProperty invoke(IrPropertySymbol it) {
                        Intrinsics.checkNotNullParameter(it, "it");
                        IrFactory irFactory = ReflectionReferencesGenerator.this.getContext().getIrFactory();
                        int i2 = i;
                        IrDeclarationOrigin.SYNTHETIC_JAVA_PROPERTY_DELEGATE synthetic_java_property_delegate = IrDeclarationOrigin.SYNTHETIC_JAVA_PROPERTY_DELEGATE.INSTANCE;
                        Name name2 = descriptor.getShortName();
                        Intrinsics.checkNotNullExpressionValue(name2, "getName(...)");
                        DescriptorVisibility visibility = descriptor.getVisibility();
                        Intrinsics.checkNotNullExpressionValue(visibility, "getVisibility(...)");
                        Modality modality = descriptor.getModality();
                        Intrinsics.checkNotNullExpressionValue(modality, "getModality(...)");
                        IrProperty irPropertyCreateProperty$default = IrFactory.DefaultImpls.createProperty$default(irFactory, i2, i2, (IrDeclarationOrigin) synthetic_java_property_delegate, name2, visibility, modality, irPropertySymbolReferenceProperty, descriptor.isVar(), descriptor.isConst(), descriptor.isLateInit(), descriptor.getIsDelegated(), descriptor.isExternal(), (DeserializedContainerSource) null, descriptor.get$isExpect(), false, ContactCallbackEvent.ON_STARTED_FILTERED_MANIFOLD, (Object) null);
                        irPropertyCreateProperty$default.setParent(ReflectionReferencesGenerator.this.getScope().getLocalDeclarationParent());
                        return irPropertyCreateProperty$default;
                    }
                });
            }
            return new DelegatedPropertySymbols(irPropertySymbolReferenceProperty, irSimpleFunctionSymbolReferenceSimpleFunction2, irSimpleFunctionSymbolReferenceSimpleFunction);
        }
        PropertyGetterDescriptor getter = descriptor.getGetter();
        IrSimpleFunctionSymbol irSimpleFunctionSymbolReferenceSimpleFunction3 = getter != null ? getContext().getSymbolTable().getDescriptorExtension().referenceSimpleFunction(getter) : null;
        if (mutable && (setter = descriptor.getSetter()) != null) {
            irSimpleFunctionSymbolReferenceSimpleFunction = getContext().getSymbolTable().getDescriptorExtension().referenceSimpleFunction(setter);
        }
        return new DelegatedPropertySymbols(irPropertySymbolReferenceProperty, irSimpleFunctionSymbolReferenceSimpleFunction3, irSimpleFunctionSymbolReferenceSimpleFunction);
    }

    private final IrPropertyReference generatePropertyReference(int startOffset, int endOffset, KotlinType type, PropertyDescriptor propertyDescriptor, Map<TypeParameterDescriptor, ? extends KotlinType> typeArguments, IrStatementOrigin origin, boolean mutable) {
        PropertyDescriptor original = propertyDescriptor.getOriginal();
        Intrinsics.checkNotNullExpressionValue(original, "getOriginal(...)");
        DelegatedPropertySymbols delegatedPropertySymbolsResolvePropertySymbol = resolvePropertySymbol(original, mutable);
        IrPropertyReferenceImpl irPropertyReferenceImpl = new IrPropertyReferenceImpl(startOffset, endOffset, toIrType(type), delegatedPropertySymbolsResolvePropertySymbol.getPropertySymbol(), typeArguments != null ? IrExpressionsKt.getTypeParametersCount(propertyDescriptor) : 0, getFieldForPropertyReference(original), delegatedPropertySymbolsResolvePropertySymbol.getGetterSymbol(), delegatedPropertySymbolsResolvePropertySymbol.getSetterSymbol(), origin);
        getContext().getCallToSubstitutedDescriptorMap$ir_psi2ir().put(irPropertyReferenceImpl, propertyDescriptor);
        IrPropertyReferenceImpl irPropertyReferenceImpl2 = irPropertyReferenceImpl;
        if (typeArguments != null) {
            for (Map.Entry<TypeParameterDescriptor, ? extends KotlinType> entry : typeArguments.entrySet()) {
                irPropertyReferenceImpl2.putTypeArgument(entry.getKey().getIndex(), toIrType(entry.getValue()));
            }
        }
        return irPropertyReferenceImpl;
    }

    private final IrFieldSymbol getFieldForPropertyReference(PropertyDescriptor originalProperty) {
        if (!originalProperty.getIsDelegated() && originalProperty.getGetter() == null) {
            return getContext().getSymbolTable().getDescriptorExtension().referenceField(originalProperty);
        }
        return null;
    }

    private final IrFunctionReference generateFunctionReference(int startOffset, int endOffset, KotlinType type, IrFunctionSymbol symbol, FunctionDescriptor descriptor, Map<TypeParameterDescriptor, ? extends KotlinType> typeArguments, IrStatementOrigin origin) {
        IrFunctionReferenceImpl irFunctionReferenceImplFromSymbolDescriptor = IrFunctionReferenceImpl.INSTANCE.fromSymbolDescriptor(startOffset, endOffset, toIrType(type), symbol, IrExpressionsKt.getTypeParametersCount(descriptor), symbol, origin);
        getContext().getCallToSubstitutedDescriptorMap$ir_psi2ir().put(irFunctionReferenceImplFromSymbolDescriptor, descriptor);
        IrFunctionReferenceImpl irFunctionReferenceImpl = irFunctionReferenceImplFromSymbolDescriptor;
        if (typeArguments != null) {
            for (Map.Entry<TypeParameterDescriptor, ? extends KotlinType> entry : typeArguments.entrySet()) {
                irFunctionReferenceImpl.putTypeArgument(entry.getKey().getIndex(), toIrType(entry.getValue()));
            }
        }
        return irFunctionReferenceImplFromSymbolDescriptor;
    }

    private final KotlinType maybeKFunctionTypeToFunctionType(KotlinType kotlinType) {
        if (FunctionTypesKt.isKFunctionType(kotlinType)) {
            return kFunctionTypeToFunctionType(kotlinType, false);
        }
        return FunctionTypesKt.isKSuspendFunctionType(kotlinType) ? kFunctionTypeToFunctionType(kotlinType, true) : kotlinType;
    }

    private final SimpleType kFunctionTypeToFunctionType(KotlinType kotlinType, boolean z) {
        IrBuiltIns irBuiltIns = getStatementGenerator().getContext().getIrBuiltIns();
        Intrinsics.checkNotNull(irBuiltIns, "null cannot be cast to non-null type org.jetbrains.kotlin.psi2ir.descriptors.IrBuiltInsOverDescriptors");
        KotlinBuiltIns builtIns = ((IrBuiltInsOverDescriptors) irBuiltIns).getBuiltIns();
        Annotations annotations = kotlinType.getAnnotations();
        List listEmptyList = CollectionsKt.emptyList();
        List listDropLast = CollectionsKt.dropLast(kotlinType.getArguments(), 1);
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(listDropLast, 10));
        Iterator it = listDropLast.iterator();
        while (it.hasNext()) {
            KotlinType nullableAnyType = ((TypeProjection) it.next()).getNullableAnyType();
            Intrinsics.checkNotNullExpressionValue(nullableAnyType, "getType(...)");
            arrayList.add(nullableAnyType);
        }
        KotlinType nullableAnyType2 = ((TypeProjection) CollectionsKt.last((List) kotlinType.getArguments())).getNullableAnyType();
        Intrinsics.checkNotNullExpressionValue(nullableAnyType2, "getType(...)");
        return FunctionTypesKt.createFunctionType(builtIns, annotations, null, listEmptyList, arrayList, null, nullableAnyType2, z);
    }
}
