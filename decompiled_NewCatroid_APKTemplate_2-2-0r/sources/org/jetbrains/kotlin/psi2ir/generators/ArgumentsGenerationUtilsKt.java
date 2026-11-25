package org.jetbrains.kotlin.psi2ir.generators;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import kotlin.Metadata;
import kotlin.NotImplementedError;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import org.jetbrains.kotlin.builtins.FunctionTypesKt;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.config.LanguageFeature;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.descriptors.CallableMemberDescriptor;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.ClassifierDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.DescriptorVisibilities;
import org.jetbrains.kotlin.descriptors.DescriptorVisibility;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.descriptors.Modality;
import org.jetbrains.kotlin.descriptors.ParameterDescriptor;
import org.jetbrains.kotlin.descriptors.ReceiverParameterDescriptor;
import org.jetbrains.kotlin.descriptors.SimpleFunctionDescriptor;
import org.jetbrains.kotlin.descriptors.TypeParameterDescriptor;
import org.jetbrains.kotlin.descriptors.ValueParameterDescriptor;
import org.jetbrains.kotlin.descriptors.impl.SyntheticFieldDescriptor;
import org.jetbrains.kotlin.descriptors.impl.TypeAliasConstructorDescriptor;
import org.jetbrains.kotlin.incremental.components.NoLookupLocation;
import org.jetbrains.kotlin.ir.IrElement;
import org.jetbrains.kotlin.ir.IrStatement;
import org.jetbrains.kotlin.ir.builders.ExpressionHelpersKt;
import org.jetbrains.kotlin.ir.builders.IrBlockBodyBuilder;
import org.jetbrains.kotlin.ir.builders.Scope;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationOrigin;
import org.jetbrains.kotlin.ir.declarations.IrFactory;
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction;
import org.jetbrains.kotlin.ir.declarations.IrValueParameter;
import org.jetbrains.kotlin.ir.expressions.IrBlock;
import org.jetbrains.kotlin.ir.expressions.IrDeclarationReference;
import org.jetbrains.kotlin.ir.expressions.IrExpression;
import org.jetbrains.kotlin.ir.expressions.IrExpressionsKt;
import org.jetbrains.kotlin.ir.expressions.IrFunctionReference;
import org.jetbrains.kotlin.ir.expressions.IrMemberAccessExpression;
import org.jetbrains.kotlin.ir.expressions.IrStatementOrigin;
import org.jetbrains.kotlin.ir.expressions.IrTypeOperator;
import org.jetbrains.kotlin.ir.expressions.IrVararg;
import org.jetbrains.kotlin.ir.expressions.IrVarargElement;
import org.jetbrains.kotlin.ir.expressions.impl.IrBlockImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrCallImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrFunctionReferenceImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrGetEnumValueImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrGetFieldImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrGetObjectValueImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrGetValueImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrReturnImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrSpreadElementImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrTypeOperatorCallImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrVarargImpl;
import org.jetbrains.kotlin.ir.symbols.IrFieldSymbol;
import org.jetbrains.kotlin.ir.symbols.IrSimpleFunctionSymbol;
import org.jetbrains.kotlin.ir.symbols.IrValueParameterSymbol;
import org.jetbrains.kotlin.ir.symbols.impl.IrSimpleFunctionSymbolImpl;
import org.jetbrains.kotlin.ir.symbols.impl.IrValueParameterSymbolImpl;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.ir.util.DescriptorSymbolTableExtension;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.psi.Call;
import org.jetbrains.kotlin.psi.KtConstructorDelegationCall;
import org.jetbrains.kotlin.psi.KtElement;
import org.jetbrains.kotlin.psi.KtExpression;
import org.jetbrains.kotlin.psi.KtReferenceExpression;
import org.jetbrains.kotlin.psi.KtSuperExpression;
import org.jetbrains.kotlin.psi.KtSuperTypeCallEntry;
import org.jetbrains.kotlin.psi.ValueArgument;
import org.jetbrains.kotlin.psi.psiUtil.PsiUtilsKt;
import org.jetbrains.kotlin.psi2ir.intermediate.CallBuilder;
import org.jetbrains.kotlin.psi2ir.intermediate.CallBuilderKt;
import org.jetbrains.kotlin.psi2ir.intermediate.CallReceiver;
import org.jetbrains.kotlin.psi2ir.intermediate.ExpressionValue;
import org.jetbrains.kotlin.psi2ir.intermediate.ExtensionInvokeCallReceiver;
import org.jetbrains.kotlin.psi2ir.intermediate.IntermediateValue;
import org.jetbrains.kotlin.psi2ir.intermediate.SafeCallReceiver;
import org.jetbrains.kotlin.psi2ir.intermediate.SafeExtensionInvokeCallReceiver;
import org.jetbrains.kotlin.psi2ir.intermediate.SimpleCallReceiver;
import org.jetbrains.kotlin.psi2ir.intermediate.TransientReceiverValue;
import org.jetbrains.kotlin.resolve.BindingContext;
import org.jetbrains.kotlin.resolve.DescriptorUtils;
import org.jetbrains.kotlin.resolve.ImportedFromObjectCallableDescriptor;
import org.jetbrains.kotlin.resolve.calls.components.ArgumentsUtilsKt;
import org.jetbrains.kotlin.resolve.calls.model.DefaultValueArgument;
import org.jetbrains.kotlin.resolve.calls.model.ExpressionValueArgument;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedCall;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedCallArgument;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedValueArgument;
import org.jetbrains.kotlin.resolve.calls.model.VarargValueArgument;
import org.jetbrains.kotlin.resolve.calls.tower.NewResolvedCallImpl;
import org.jetbrains.kotlin.resolve.calls.util.CallResolverUtilKt;
import org.jetbrains.kotlin.resolve.calls.util.CallUtilKt;
import org.jetbrains.kotlin.resolve.scopes.MemberScope;
import org.jetbrains.kotlin.resolve.scopes.receivers.ContextClassReceiver;
import org.jetbrains.kotlin.resolve.scopes.receivers.ContextReceiver;
import org.jetbrains.kotlin.resolve.scopes.receivers.ExpressionReceiver;
import org.jetbrains.kotlin.resolve.scopes.receivers.ExtensionReceiver;
import org.jetbrains.kotlin.resolve.scopes.receivers.ImplicitClassReceiver;
import org.jetbrains.kotlin.resolve.scopes.receivers.ReceiverValue;
import org.jetbrains.kotlin.resolve.scopes.receivers.SuperCallReceiverValue;
import org.jetbrains.kotlin.resolve.scopes.receivers.ThisClassReceiver;
import org.jetbrains.kotlin.resolve.scopes.receivers.TransientReceiver;
import org.jetbrains.kotlin.types.FlexibleTypesKt;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.SimpleType;
import org.jetbrains.kotlin.types.StarProjectionImpl;
import org.jetbrains.kotlin.types.TypeProjection;
import org.jetbrains.kotlin.types.TypeProjectionImpl;
import org.jetbrains.kotlin.types.TypeSubstitutionKt;
import org.jetbrains.kotlin.types.TypeSubstitutor;
import org.jetbrains.kotlin.types.UnwrappedType;
import org.jetbrains.kotlin.types.Variance;
import org.jetbrains.kotlin.types.typeUtil.TypeUtilsKt;
import org.jetbrains.kotlin.util.OperatorNameConventions;
import org.jetbrains.kotlin.util.slicedMap.WritableSlice;

/* JADX INFO: compiled from: ArgumentsGenerationUtils.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000à\u0001\n\u0000\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0005\u001a$\u0010\u0000\u001a\u0010\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u0003\u0018\u00010\u00012\f\u0010\u0004\u001a\b\u0012\u0002\b\u0003\u0018\u00010\u0005H\u0000\u001a\u0014\u0010\u0006\u001a\u00020\u00072\n\u0010\u0004\u001a\u0006\u0012\u0002\b\u00030\u0005H\u0000\u001a\u0010\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\tH\u0002\u001a0\u0010\u000b\u001a\u00020\f*\u00020\r2\u0006\u0010\u000e\u001a\u00020\f2\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u00122\n\u0010\u0004\u001a\u0006\u0012\u0002\b\u00030\u0005H\u0002\u001a\u001c\u0010\u0013\u001a\u00020\f*\u00020\r2\u0006\u0010\u0014\u001a\u00020\f2\u0006\u0010\u0015\u001a\u00020\u0003H\u0000\u001a\u0014\u0010\u0016\u001a\u00020\u0017*\u00020\r2\u0006\u0010\u0018\u001a\u00020\u0003H\u0002\u001a,\u0010\u0019\u001a\u00020\u001a*\u00020\r2\u0006\u0010\u001b\u001a\u00020\u001c2\u0006\u0010\u001d\u001a\u00020\u001c2\u0006\u0010\u001e\u001a\u00020\u00032\u0006\u0010\u001f\u001a\u00020\u0003H\u0002\u001a4\u0010 \u001a\u0004\u0018\u00010!*\u00020\r2\u0006\u0010\u001b\u001a\u00020\u001c2\u0006\u0010\u001d\u001a\u00020\u001c2\f\u0010\u0004\u001a\b\u0012\u0002\b\u0003\u0018\u00010\u00052\u0006\u0010\"\u001a\u00020#H\u0000\u001aP\u0010$\u001a\u00020%*\u00020\r2\u0006\u0010&\u001a\u00020'2\u0006\u0010(\u001a\u00020\t2\b\u0010)\u001a\u0004\u0018\u00010*2\b\u0010+\u001a\u0004\u0018\u00010*2\f\u0010,\u001a\b\u0012\u0004\u0012\u00020*0-2\u0006\u0010.\u001a\u00020/2\b\b\u0002\u00100\u001a\u00020/H\u0000\u001a$\u00101\u001a\u00020!*\u00020\r2\u0006\u00102\u001a\u00020\u001c2\u0006\u00103\u001a\u00020\u001c2\u0006\u00104\u001a\u000205H\u0002\u001a \u00101\u001a\u0004\u0018\u00010!*\u00020\r2\u0006\u0010&\u001a\u00020'2\b\u00104\u001a\u0004\u0018\u000105H\u0002\u001a$\u00106\u001a\u00020!*\u00020\r2\u0006\u00102\u001a\u00020\u001c2\u0006\u00103\u001a\u00020\u001c2\u0006\u00104\u001a\u00020*H\u0002\u001a\u001c\u00106\u001a\u00020!*\u00020\r2\u0006\u0010&\u001a\u00020'2\u0006\u00104\u001a\u00020*H\u0002\u001a(\u00107\u001a\u000208*\u00020\r2\u0006\u0010\u001b\u001a\u00020\u001c2\u0006\u0010\u001d\u001a\u00020\u001c2\n\u0010(\u001a\u0006\u0012\u0002\b\u000309H\u0002\u001a \u0010:\u001a\u0004\u0018\u00010!*\u00020\r2\u0006\u0010&\u001a\u00020'2\b\u00104\u001a\u0004\u0018\u00010*H\u0000\u001a \u0010;\u001a\u00020<*\u00020\r2\u0006\u0010=\u001a\u00020\u00072\n\u0010\u0004\u001a\u0006\u0012\u0002\b\u00030\u0005H\u0000\u001a,\u0010>\u001a\u00020?*\u00020\r2\u0006\u0010\n\u001a\u00020@2\u0006\u0010\u001b\u001a\u00020\u001c2\u0006\u0010\u001d\u001a\u00020\u001c2\u0006\u0010\u0018\u001a\u00020\u0003H\u0000\u001a\u001c\u0010A\u001a\u00020\f*\u00020\r2\u0006\u00104\u001a\u00020*2\u0006\u0010B\u001a\u00020@H\u0002\u001a*\u0010C\u001a\u0004\u0018\u00010\f*\u00020\r2\u0006\u0010\u000f\u001a\u00020D2\u0006\u0010\u0011\u001a\u00020\u00122\n\u0010\u0004\u001a\u0006\u0012\u0002\b\u00030\u0005H\u0002\u001a@\u0010E\u001a\u0004\u0018\u00010\f*\u00020\r2\u0006\u0010\u000f\u001a\u00020D2\u0006\u0010\u0011\u001a\u00020\u00122\n\u0010\u0004\u001a\u0006\u0012\u0002\b\u00030\u00052\u0014\u0010F\u001a\u0010\u0012\u0004\u0012\u00020H\u0012\u0006\u0012\u0004\u0018\u00010\f0GH\u0002\u001a@\u0010I\u001a\u0004\u0018\u00010\f*\u00020\r2\u0006\u0010J\u001a\u00020K2\u0006\u0010\u0011\u001a\u00020\u00122\n\u0010\u0004\u001a\u0006\u0012\u0002\b\u00030\u00052\u0014\u0010F\u001a\u0010\u0012\u0004\u0012\u00020H\u0012\u0006\u0012\u0004\u0018\u00010\f0GH\u0002\u001a\u0016\u0010L\u001a\u0004\u0018\u00010\u0003*\u00020\r2\u0006\u0010\u0011\u001a\u00020\u0012H\u0002\u001a\u001a\u0010M\u001a\u0004\u0018\u00010@*\u00020N2\n\u0010\u0004\u001a\u0006\u0012\u0002\b\u00030\u0005H\u0000\u001a\u0010\u0010O\u001a\u00020/*\u0006\u0012\u0002\b\u00030\u0005H\u0002\u001a$\u0010P\u001a\u00020Q*\u00020\r2\u0006\u00104\u001a\u0002052\u0006\u00102\u001a\u00020\u001c2\u0006\u00103\u001a\u00020\u001cH\u0000\u001a\u0018\u0010R\u001a\u00020\u0007*\u00020\r2\n\u0010\u0004\u001a\u0006\u0012\u0002\b\u00030\u0005H\u0000\u001a\u0018\u0010S\u001a\u00020\u0007*\u00020\r2\n\u0010\u0004\u001a\u0006\u0012\u0002\b\u00030\u0005H\u0000\u001a.\u0010T\u001a\u00020\u0007*\u00020\r2\n\u0010\u0004\u001a\u0006\u0012\u0002\b\u00030\u00052\u0014\u0010F\u001a\u0010\u0012\u0004\u0012\u00020H\u0012\u0006\u0012\u0004\u0018\u00010\f0GH\u0000\u001a\u0018\u0010U\u001a\u00020\u0007*\u00020\r2\n\u0010\u0004\u001a\u0006\u0012\u0002\b\u00030\u0005H\u0002\u001a6\u0010V\u001a\u00020<*\u00020\r2\u0006\u0010=\u001a\u00020\u00072\n\u0010\u0004\u001a\u0006\u0012\u0002\b\u00030\u00052\u0014\u0010F\u001a\u0010\u0012\u0004\u0012\u00020H\u0012\u0006\u0012\u0004\u0018\u00010\f0GH\u0000\u001a>\u0010W\u001a\u00020<*\u0006\u0012\u0002\b\u00030X2\u0014\u0010Y\u001a\u0010\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u0003\u0018\u00010\u00012\u0012\u0010Z\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00170GH\u0080\bø\u0001\u0000\u001a\u0014\u0010[\u001a\u00020/*\u00020\r2\u0006\u0010\\\u001a\u00020@H\u0002\u0082\u0002\u0007\n\u0005\b\u009920\u0001¨\u0006]"}, d2 = {"getTypeArguments", "", "Lorg/jetbrains/kotlin/descriptors/TypeParameterDescriptor;", "Lorg/jetbrains/kotlin/types/KotlinType;", "resolvedCall", "Lorg/jetbrains/kotlin/resolve/calls/model/ResolvedCall;", "unwrapCallableDescriptorAndTypeArguments", "Lorg/jetbrains/kotlin/psi2ir/intermediate/CallBuilder;", "unwrapSpecialDescriptor", "Lorg/jetbrains/kotlin/descriptors/CallableDescriptor;", "descriptor", "applySuspendConversionForValueArgumentIfRequired", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "Lorg/jetbrains/kotlin/psi2ir/generators/StatementGenerator;", "expression", "valueArgument", "Lorg/jetbrains/kotlin/psi/ValueArgument;", "valueParameter", "Lorg/jetbrains/kotlin/descriptors/ValueParameterDescriptor;", "castArgumentToFunctionalInterfaceForSamType", "irExpression", "samType", "computeVarargType", "Lorg/jetbrains/kotlin/ir/types/IrType;", ModuleXmlParser.TYPE, "createFunctionForSuspendConversion", "Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;", "startOffset", "", "endOffset", "funType", "suspendFunType", "generateBackingFieldReceiver", "Lorg/jetbrains/kotlin/psi2ir/intermediate/IntermediateValue;", "fieldDescriptor", "Lorg/jetbrains/kotlin/descriptors/impl/SyntheticFieldDescriptor;", "generateCallReceiver", "Lorg/jetbrains/kotlin/psi2ir/intermediate/CallReceiver;", "ktDefaultElement", "Lorg/jetbrains/kotlin/psi/KtElement;", "calleeDescriptor", "dispatchReceiver", "Lorg/jetbrains/kotlin/resolve/scopes/receivers/ReceiverValue;", "extensionReceiver", "contextReceivers", "", "isSafe", "", "isAssignmentReceiver", "generateContextReceiverForDelegatingConstructorCall", "defaultStartOffset", "defaultEndOffset", "receiver", "Lorg/jetbrains/kotlin/resolve/scopes/receivers/ContextClassReceiver;", "generateReceiver", "generateReceiverForCalleeImportedFromObject", "Lorg/jetbrains/kotlin/psi2ir/intermediate/ExpressionValue;", "Lorg/jetbrains/kotlin/resolve/ImportedFromObjectCallableDescriptor;", "generateReceiverOrNull", "generateSamConversionForValueArgumentsIfRequired", "", "call", "generateSingletonReference", "Lorg/jetbrains/kotlin/ir/expressions/IrDeclarationReference;", "Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;", "generateThisOrSuperReceiver", "classDescriptor", "generateValueArgument", "Lorg/jetbrains/kotlin/resolve/calls/model/ResolvedValueArgument;", "generateValueArgumentUsing", "generateArgumentExpression", "Lkotlin/Function1;", "Lorg/jetbrains/kotlin/psi/KtExpression;", "generateVarargExpressionUsing", "varargArgument", "Lorg/jetbrains/kotlin/resolve/calls/model/VarargValueArgument;", "getSamTypeForValueParameter", "getSuperQualifier", "Lorg/jetbrains/kotlin/psi2ir/generators/Generator;", "isExtensionInvokeCall", "loadContextReceiver", "Lorg/jetbrains/kotlin/ir/expressions/impl/IrGetFieldImpl;", "pregenerateCall", "pregenerateCallReceivers", "pregenerateCallUsing", "pregenerateExtensionInvokeCall", "pregenerateValueArgumentsUsing", "putTypeArguments", "Lorg/jetbrains/kotlin/ir/expressions/IrMemberAccessExpression;", "typeArguments", "toIrType", "shouldGenerateReceiverAsSingletonReference", "receiverClassDescriptor", "ir.psi2ir"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class ArgumentsGenerationUtilsKt {
    public static final IntermediateValue generateReceiverOrNull(StatementGenerator statementGenerator, KtElement ktDefaultElement, ReceiverValue receiverValue) {
        Intrinsics.checkNotNullParameter(statementGenerator, "<this>");
        Intrinsics.checkNotNullParameter(ktDefaultElement, "ktDefaultElement");
        if (receiverValue != null) {
            return generateReceiver(statementGenerator, ktDefaultElement, receiverValue);
        }
        return null;
    }

    private static final IntermediateValue generateContextReceiverForDelegatingConstructorCall(StatementGenerator statementGenerator, KtElement ktElement, ContextClassReceiver contextClassReceiver) {
        if (contextClassReceiver == null) {
            return null;
        }
        KtElement ktElement2 = ktElement;
        return generateContextReceiverForDelegatingConstructorCall(statementGenerator, PsiUtilsKt.getStartOffsetSkippingComments(ktElement2), PsiUtilsKt.getEndOffset(ktElement2), contextClassReceiver);
    }

    private static final IntermediateValue generateContextReceiverForDelegatingConstructorCall(StatementGenerator statementGenerator, int i, int i2, ContextClassReceiver contextClassReceiver) {
        KotlinType type = contextClassReceiver.getType();
        Intrinsics.checkNotNullExpressionValue(type, "getType(...)");
        IrType irType = statementGenerator.toIrType(type);
        List<ReceiverParameterDescriptor> contextReceivers = contextClassReceiver.getClassDescriptor().getContextReceivers();
        Intrinsics.checkNotNullExpressionValue(contextReceivers, "getContextReceivers(...)");
        Object obj = null;
        boolean z = false;
        for (Object obj2 : contextReceivers) {
            if (Intrinsics.areEqual(((ReceiverParameterDescriptor) obj2).getValue(), contextClassReceiver)) {
                if (z) {
                    throw new IllegalArgumentException("Collection contains more than one matching element.");
                }
                z = true;
                obj = obj2;
            }
        }
        if (z) {
            return new ExpressionValue(i, i2, statementGenerator, (ReceiverParameterDescriptor) obj) { // from class: org.jetbrains.kotlin.psi2ir.generators.ArgumentsGenerationUtilsKt.generateContextReceiverForDelegatingConstructorCall.2
                final /* synthetic */ int $defaultEndOffset;
                final /* synthetic */ int $defaultStartOffset;
                final /* synthetic */ ReceiverParameterDescriptor $receiverParameter;
                final /* synthetic */ StatementGenerator $this_generateContextReceiverForDelegatingConstructorCall;

                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(this.$irReceiverType);
                    this.$defaultStartOffset = i;
                    this.$defaultEndOffset = i2;
                    this.$this_generateContextReceiverForDelegatingConstructorCall = statementGenerator;
                    this.$receiverParameter = receiverParameterDescriptor;
                }

                @Override // org.jetbrains.kotlin.psi2ir.intermediate.IntermediateValue
                public IrExpression load() {
                    int i3 = this.$defaultStartOffset;
                    int i4 = this.$defaultEndOffset;
                    IrType irType2 = this.$irReceiverType;
                    DescriptorSymbolTableExtension descriptorExtension = this.$this_generateContextReceiverForDelegatingConstructorCall.getContext().getSymbolTable().getDescriptorExtension();
                    ReceiverParameterDescriptor receiverParameter = this.$receiverParameter;
                    Intrinsics.checkNotNullExpressionValue(receiverParameter, "$receiverParameter");
                    return new IrGetValueImpl(i3, i4, irType2, descriptorExtension.referenceValueParameter((ParameterDescriptor) receiverParameter), null, 16, null);
                }
            };
        }
        throw new NoSuchElementException("Collection contains no element matching the predicate.");
    }

    private static final IntermediateValue generateReceiver(StatementGenerator statementGenerator, KtElement ktElement, ReceiverValue receiverValue) {
        KtElement ktElement2 = ktElement;
        return generateReceiver(statementGenerator, PsiUtilsKt.getStartOffsetSkippingComments(ktElement2), PsiUtilsKt.getEndOffset(ktElement2), receiverValue);
    }

    private static final IntermediateValue generateReceiver(StatementGenerator statementGenerator, int i, int i2, ReceiverValue receiverValue) {
        IrType irType;
        Object next;
        if (receiverValue instanceof ExtensionReceiver) {
            ReceiverParameterDescriptor extensionReceiverParameter = ((ExtensionReceiver) receiverValue).getDeclarationDescriptor().getExtensionReceiverParameter();
            Intrinsics.checkNotNull(extensionReceiverParameter);
            KotlinType type = extensionReceiverParameter.getType();
            Intrinsics.checkNotNullExpressionValue(type, "getType(...)");
            irType = statementGenerator.toIrType(type);
        } else if (receiverValue instanceof ContextReceiver) {
            ContextReceiver contextReceiver = (ContextReceiver) receiverValue;
            List<ReceiverParameterDescriptor> contextReceiverParameters = contextReceiver.getDeclarationDescriptor().getContextReceiverParameters();
            Intrinsics.checkNotNullExpressionValue(contextReceiverParameters, "getContextReceiverParameters(...)");
            Iterator<T> it = contextReceiverParameters.iterator();
            do {
                if (!it.hasNext()) {
                    next = null;
                    break;
                }
                next = it.next();
            } while (!Intrinsics.areEqual(((ReceiverParameterDescriptor) next).getValue(), contextReceiver.getOriginal()));
            ReceiverParameterDescriptor receiverParameterDescriptor = (ReceiverParameterDescriptor) next;
            if (receiverParameterDescriptor == null) {
                throw new IllegalStateException(("Unknown receiver: " + receiverValue).toString());
            }
            KotlinType type2 = receiverParameterDescriptor.getType();
            Intrinsics.checkNotNullExpressionValue(type2, "getType(...)");
            irType = statementGenerator.toIrType(type2);
        } else {
            KotlinType type3 = receiverValue.getType();
            Intrinsics.checkNotNullExpressionValue(type3, "getType(...)");
            irType = statementGenerator.toIrType(type3);
        }
        IrType irType2 = irType;
        return receiverValue instanceof TransientReceiver ? new TransientReceiverValue(irType2) : new ExpressionValue(receiverValue, statementGenerator, i, i2) { // from class: org.jetbrains.kotlin.psi2ir.generators.ArgumentsGenerationUtilsKt.generateReceiver.1
            final /* synthetic */ int $defaultEndOffset;
            final /* synthetic */ int $defaultStartOffset;
            final /* synthetic */ ReceiverValue $receiver;
            final /* synthetic */ StatementGenerator $this_generateReceiver;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(this.$irReceiverType);
                this.$receiver = receiverValue;
                this.$this_generateReceiver = statementGenerator;
                this.$defaultStartOffset = i;
                this.$defaultEndOffset = i2;
            }

            @Override // org.jetbrains.kotlin.psi2ir.intermediate.IntermediateValue
            public IrExpression load() {
                IrGetValueImpl irGetValueImpl;
                ReceiverValue receiverValue2 = this.$receiver;
                if (receiverValue2 instanceof ImplicitClassReceiver) {
                    ClassDescriptor classDescriptor = ((ImplicitClassReceiver) receiverValue2).getClassDescriptor();
                    if (ArgumentsGenerationUtilsKt.shouldGenerateReceiverAsSingletonReference(this.$this_generateReceiver, classDescriptor)) {
                        irGetValueImpl = ArgumentsGenerationUtilsKt.generateSingletonReference(this.$this_generateReceiver, classDescriptor, this.$defaultStartOffset, this.$defaultEndOffset, ((ImplicitClassReceiver) this.$receiver).getType());
                    } else {
                        int i3 = this.$defaultStartOffset;
                        int i4 = this.$defaultEndOffset;
                        IrType irType3 = this.$irReceiverType;
                        DescriptorSymbolTableExtension descriptorExtension = this.$this_generateReceiver.getContext().getSymbolTable().getDescriptorExtension();
                        ReceiverParameterDescriptor thisAsReceiverParameter = classDescriptor.getThisAsReceiverParameter();
                        Intrinsics.checkNotNullExpressionValue(thisAsReceiverParameter, "getThisAsReceiverParameter(...)");
                        irGetValueImpl = new IrGetValueImpl(i3, i4, irType3, descriptorExtension.referenceValueParameter((ParameterDescriptor) thisAsReceiverParameter), null, 16, null);
                    }
                    return irGetValueImpl;
                }
                if (receiverValue2 instanceof ContextClassReceiver) {
                    return ArgumentsGenerationUtilsKt.loadContextReceiver(this.$this_generateReceiver, (ContextClassReceiver) receiverValue2, this.$defaultStartOffset, this.$defaultEndOffset);
                }
                if (receiverValue2 instanceof ThisClassReceiver) {
                    return ArgumentsGenerationUtilsKt.generateThisOrSuperReceiver(this.$this_generateReceiver, receiverValue2, ((ThisClassReceiver) receiverValue2).getClassDescriptor());
                }
                if (receiverValue2 instanceof SuperCallReceiverValue) {
                    StatementGenerator statementGenerator2 = this.$this_generateReceiver;
                    ClassifierDescriptor this$0 = ((SuperCallReceiverValue) receiverValue2).getThisType().getConstructor().getThis$0();
                    Intrinsics.checkNotNull(this$0, "null cannot be cast to non-null type org.jetbrains.kotlin.descriptors.ClassDescriptor");
                    return ArgumentsGenerationUtilsKt.generateThisOrSuperReceiver(statementGenerator2, receiverValue2, (ClassDescriptor) this$0);
                }
                if (receiverValue2 instanceof ExpressionReceiver) {
                    IrStatement irStatementGenerateStatement = this.$this_generateReceiver.generateStatement(((ExpressionReceiver) receiverValue2).getExpression());
                    Intrinsics.checkNotNull(irStatementGenerateStatement, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.expressions.IrExpression");
                    return (IrExpression) irStatementGenerateStatement;
                }
                if (receiverValue2 instanceof ExtensionReceiver) {
                    int i5 = this.$defaultStartOffset;
                    IrType irType4 = this.$irReceiverType;
                    DescriptorSymbolTableExtension descriptorExtension2 = this.$this_generateReceiver.getContext().getSymbolTable().getDescriptorExtension();
                    ReceiverParameterDescriptor extensionReceiverParameter2 = ((ExtensionReceiver) this.$receiver).getDeclarationDescriptor().getExtensionReceiverParameter();
                    Intrinsics.checkNotNull(extensionReceiverParameter2);
                    return new IrGetValueImpl(i5, i5, irType4, descriptorExtension2.referenceValueParameter((ParameterDescriptor) extensionReceiverParameter2), null, 16, null);
                }
                if (receiverValue2 instanceof ContextReceiver) {
                    List<ReceiverParameterDescriptor> contextReceiverParameters2 = ((ContextReceiver) receiverValue2).getDeclarationDescriptor().getContextReceiverParameters();
                    Intrinsics.checkNotNullExpressionValue(contextReceiverParameters2, "getContextReceiverParameters(...)");
                    ReceiverValue receiverValue3 = this.$receiver;
                    Object obj = null;
                    boolean z = false;
                    for (Object obj2 : contextReceiverParameters2) {
                        if (Intrinsics.areEqual(((ReceiverParameterDescriptor) obj2).getValue(), ((ContextReceiver) receiverValue3).getOriginal())) {
                            if (z) {
                                throw new IllegalArgumentException("Collection contains more than one matching element.");
                            }
                            z = true;
                            obj = obj2;
                        }
                    }
                    if (z) {
                        ReceiverParameterDescriptor receiverParameterDescriptor2 = (ReceiverParameterDescriptor) obj;
                        int i6 = this.$defaultStartOffset;
                        IrType irType5 = this.$irReceiverType;
                        DescriptorSymbolTableExtension descriptorExtension3 = this.$this_generateReceiver.getContext().getSymbolTable().getDescriptorExtension();
                        Intrinsics.checkNotNull(receiverParameterDescriptor2);
                        return new IrGetValueImpl(i6, i6, irType5, descriptorExtension3.referenceValueParameter((ParameterDescriptor) receiverParameterDescriptor2), null, 16, null);
                    }
                    throw new NoSuchElementException("Collection contains no element matching the predicate.");
                }
                throw new AssertionError("Unexpected receiver: " + this.$receiver.getClass().getSimpleName());
            }
        };
    }

    public static final IrGetFieldImpl loadContextReceiver(StatementGenerator statementGenerator, ContextClassReceiver receiver, int i, int i2) {
        Intrinsics.checkNotNullParameter(statementGenerator, "<this>");
        Intrinsics.checkNotNullParameter(receiver, "receiver");
        ReceiverParameterDescriptor thisAsReceiverParameter = receiver.getClassDescriptor().getThisAsReceiverParameter();
        Intrinsics.checkNotNullExpressionValue(thisAsReceiverParameter, "getThisAsReceiverParameter(...)");
        KotlinType type = thisAsReceiverParameter.getType();
        Intrinsics.checkNotNullExpressionValue(type, "getType(...)");
        IrGetValueImpl irGetValueImpl = new IrGetValueImpl(i, i2, statementGenerator.toIrType(type), statementGenerator.getContext().getSymbolTable().getDescriptorExtension().referenceValue(thisAsReceiverParameter), null, 16, null);
        IrFieldSymbol symbol = statementGenerator.getContext().getAdditionalDescriptorStorage().getSyntheticField(receiver).getSymbol();
        KotlinType type2 = receiver.getType();
        Intrinsics.checkNotNullExpressionValue(type2, "getType(...)");
        return new IrGetFieldImpl(i, i2, symbol, statementGenerator.toIrType(type2), irGetValueImpl, null, null, 96, null);
    }

    public static final IrDeclarationReference generateSingletonReference(StatementGenerator statementGenerator, ClassDescriptor descriptor, int i, int i2, KotlinType type) {
        Intrinsics.checkNotNullParameter(statementGenerator, "<this>");
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        Intrinsics.checkNotNullParameter(type, "type");
        IrType irType = statementGenerator.toIrType(type);
        ClassDescriptor classDescriptor = descriptor;
        if (DescriptorUtils.isObject(classDescriptor)) {
            return new IrGetObjectValueImpl(i, i2, irType, statementGenerator.getContext().getSymbolTable().getDescriptorExtension().referenceClass(descriptor));
        }
        if (DescriptorUtils.isEnumEntry(classDescriptor)) {
            return new IrGetEnumValueImpl(i, i2, irType, statementGenerator.getContext().getSymbolTable().getDescriptorExtension().referenceEnumEntry(descriptor));
        }
        ClassDescriptor classDescriptorMo6346getCompanionObjectDescriptor = descriptor.mo6346getCompanionObjectDescriptor();
        if (classDescriptorMo6346getCompanionObjectDescriptor == null) {
            throw new AssertionError("Class value without companion object: " + descriptor);
        }
        return new IrGetObjectValueImpl(i, i2, irType, statementGenerator.getContext().getSymbolTable().getDescriptorExtension().referenceClass(classDescriptorMo6346getCompanionObjectDescriptor));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean shouldGenerateReceiverAsSingletonReference(StatementGenerator statementGenerator, ClassDescriptor classDescriptor) {
        DeclarationDescriptor scopeOwner = statementGenerator.getScopeOwner();
        return (!classDescriptor.getClassKind().isSingleton() || Intrinsics.areEqual(scopeOwner, classDescriptor) || ((scopeOwner instanceof CallableMemberDescriptor) && Intrinsics.areEqual(((CallableMemberDescriptor) scopeOwner).getContainingModule(), classDescriptor))) ? false : true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final IrExpression generateThisOrSuperReceiver(StatementGenerator statementGenerator, ReceiverValue receiverValue, ClassDescriptor classDescriptor) {
        KotlinType type;
        ExpressionReceiver expressionReceiver = receiverValue instanceof ExpressionReceiver ? (ExpressionReceiver) receiverValue : null;
        if (expressionReceiver == null) {
            throw new AssertionError("'this' or 'super' receiver should be an expression receiver");
        }
        KtExpression expression = expressionReceiver.getExpression();
        if (receiverValue instanceof SuperCallReceiverValue) {
            type = ((SuperCallReceiverValue) receiverValue).getThisType();
        } else {
            type = expressionReceiver.getType();
            Intrinsics.checkNotNullExpressionValue(type, "getType(...)");
        }
        KtExpression ktExpression = expression;
        return statementGenerator.generateThisReceiver(PsiUtilsKt.getStartOffsetSkippingComments(ktExpression), PsiUtilsKt.getEndOffset(ktExpression), type, classDescriptor);
    }

    public static final IntermediateValue generateBackingFieldReceiver(StatementGenerator statementGenerator, int i, int i2, ResolvedCall<?> resolvedCall, SyntheticFieldDescriptor fieldDescriptor) {
        ReceiverValue dispatchReceiverForBackend;
        Intrinsics.checkNotNullParameter(statementGenerator, "<this>");
        Intrinsics.checkNotNullParameter(fieldDescriptor, "fieldDescriptor");
        if ((resolvedCall == null || (dispatchReceiverForBackend = resolvedCall.getDispatchReceiver()) == null) && (dispatchReceiverForBackend = fieldDescriptor.getDispatchReceiverForBackend()) == null) {
            return null;
        }
        return generateReceiver(statementGenerator, i, i2, dispatchReceiverForBackend);
    }

    public static /* synthetic */ CallReceiver generateCallReceiver$default(StatementGenerator statementGenerator, KtElement ktElement, CallableDescriptor callableDescriptor, ReceiverValue receiverValue, ReceiverValue receiverValue2, List list, boolean z, boolean z2, int i, Object obj) {
        return generateCallReceiver(statementGenerator, ktElement, callableDescriptor, receiverValue, receiverValue2, list, z, (i & 64) != 0 ? false : z2);
    }

    /* JADX WARN: Code duplicated, block: B:47:0x010c  */
    /* JADX WARN: Code duplicated, block: B:48:0x0114  */
    public static final CallReceiver generateCallReceiver(StatementGenerator statementGenerator, KtElement ktDefaultElement, CallableDescriptor calleeDescriptor, ReceiverValue receiverValue, ReceiverValue receiverValue2, List<? extends ReceiverValue> contextReceivers, boolean z, boolean z2) {
        ExpressionValue expressionValueGenerateReceiverOrNull;
        IntermediateValue intermediateValueGenerateReceiverOrNull;
        ArrayList arrayList;
        IntermediateValue intermediateValue;
        IntermediateValue intermediateValue2;
        ArrayList arrayList2;
        ReceiverValue receiverValue3 = receiverValue;
        Intrinsics.checkNotNullParameter(statementGenerator, "<this>");
        Intrinsics.checkNotNullParameter(ktDefaultElement, "ktDefaultElement");
        Intrinsics.checkNotNullParameter(calleeDescriptor, "calleeDescriptor");
        Intrinsics.checkNotNullParameter(contextReceivers, "contextReceivers");
        KtElement ktElement = ktDefaultElement;
        int startOffsetSkippingComments = PsiUtilsKt.getStartOffsetSkippingComments(ktElement);
        int endOffset = PsiUtilsKt.getEndOffset(ktElement);
        if (calleeDescriptor instanceof ImportedFromObjectCallableDescriptor) {
            expressionValueGenerateReceiverOrNull = generateReceiverForCalleeImportedFromObject(statementGenerator, startOffsetSkippingComments, endOffset, (ImportedFromObjectCallableDescriptor) calleeDescriptor);
            intermediateValueGenerateReceiverOrNull = generateReceiverOrNull(statementGenerator, ktDefaultElement, receiverValue2);
            ArrayList arrayList3 = new ArrayList();
            Iterator<T> it = contextReceivers.iterator();
            while (it.hasNext()) {
                IntermediateValue intermediateValueGenerateReceiverOrNull2 = generateReceiverOrNull(statementGenerator, ktDefaultElement, (ReceiverValue) it.next());
                if (intermediateValueGenerateReceiverOrNull2 != null) {
                    arrayList3.add(intermediateValueGenerateReceiverOrNull2);
                }
            }
            arrayList = arrayList3;
        } else {
            if (calleeDescriptor instanceof TypeAliasConstructorDescriptor) {
                if (receiverValue2 != null) {
                    receiverValue3 = receiverValue2;
                }
                IntermediateValue intermediateValueGenerateReceiverOrNull3 = generateReceiverOrNull(statementGenerator, ktDefaultElement, receiverValue3);
                ArrayList arrayList4 = new ArrayList();
                Iterator<T> it2 = contextReceivers.iterator();
                while (it2.hasNext()) {
                    IntermediateValue intermediateValueGenerateReceiverOrNull4 = generateReceiverOrNull(statementGenerator, ktDefaultElement, (ReceiverValue) it2.next());
                    if (intermediateValueGenerateReceiverOrNull4 != null) {
                        arrayList4.add(intermediateValueGenerateReceiverOrNull4);
                    }
                }
                intermediateValue = intermediateValueGenerateReceiverOrNull3;
                intermediateValue2 = null;
                arrayList2 = arrayList4;
            } else {
                expressionValueGenerateReceiverOrNull = generateReceiverOrNull(statementGenerator, ktDefaultElement, receiverValue3);
                intermediateValueGenerateReceiverOrNull = generateReceiverOrNull(statementGenerator, ktDefaultElement, receiverValue2);
                if (ktDefaultElement instanceof KtConstructorDelegationCall ? true : ktDefaultElement instanceof KtSuperTypeCallEntry) {
                    ArrayList arrayList5 = new ArrayList();
                    for (ReceiverValue receiverValue4 : contextReceivers) {
                        IntermediateValue intermediateValueGenerateContextReceiverForDelegatingConstructorCall = receiverValue4 instanceof ContextClassReceiver ? generateContextReceiverForDelegatingConstructorCall(statementGenerator, ktDefaultElement, (ContextClassReceiver) receiverValue4) : generateReceiverOrNull(statementGenerator, ktDefaultElement, receiverValue4);
                        if (intermediateValueGenerateContextReceiverForDelegatingConstructorCall != null) {
                            arrayList5.add(intermediateValueGenerateContextReceiverForDelegatingConstructorCall);
                        }
                    }
                    arrayList = arrayList5;
                } else {
                    ArrayList arrayList6 = new ArrayList();
                    Iterator<T> it3 = contextReceivers.iterator();
                    while (it3.hasNext()) {
                        IntermediateValue intermediateValueGenerateReceiverOrNull5 = generateReceiverOrNull(statementGenerator, ktDefaultElement, (ReceiverValue) it3.next());
                        if (intermediateValueGenerateReceiverOrNull5 != null) {
                            arrayList6.add(intermediateValueGenerateReceiverOrNull5);
                        }
                    }
                    arrayList = arrayList6;
                }
            }
            if (!z) {
                return new SimpleCallReceiver(intermediateValue, intermediateValue2, arrayList2);
            }
            if (intermediateValue2 == null || intermediateValue != null) {
                return new SafeCallReceiver(statementGenerator, startOffsetSkippingComments, endOffset, intermediateValue2, arrayList2, intermediateValue, z2);
            }
            throw new AssertionError("Safe call should have an explicit receiver: " + ktDefaultElement.getText());
        }
        intermediateValue = expressionValueGenerateReceiverOrNull;
        intermediateValue2 = intermediateValueGenerateReceiverOrNull;
        arrayList2 = arrayList;
        if (!z) {
            return new SimpleCallReceiver(intermediateValue, intermediateValue2, arrayList2);
        }
        if (intermediateValue2 == null) {
        }
        return new SafeCallReceiver(statementGenerator, startOffsetSkippingComments, endOffset, intermediateValue2, arrayList2, intermediateValue, z2);
    }

    private static final ExpressionValue generateReceiverForCalleeImportedFromObject(final StatementGenerator statementGenerator, final int i, final int i2, ImportedFromObjectCallableDescriptor<?> importedFromObjectCallableDescriptor) {
        final ClassDescriptor containingObject = importedFromObjectCallableDescriptor.getContainingObject();
        SimpleType defaultType = containingObject.getDefaultType();
        Intrinsics.checkNotNullExpressionValue(defaultType, "getDefaultType(...)");
        final IrType irType = statementGenerator.toIrType(defaultType);
        return new ExpressionValue(irType) { // from class: org.jetbrains.kotlin.psi2ir.generators.ArgumentsGenerationUtilsKt$generateReceiverForCalleeImportedFromObject$$inlined$generateExpressionValue$1
            @Override // org.jetbrains.kotlin.psi2ir.intermediate.IntermediateValue
            public IrExpression load() {
                return new IrGetObjectValueImpl(i, i2, irType, statementGenerator.getContext().getSymbolTable().getDescriptorExtension().referenceClass(containingObject));
            }
        };
    }

    private static final IrType computeVarargType(StatementGenerator statementGenerator, KotlinType kotlinType) {
        return statementGenerator.toIrType(FlexibleTypesKt.upperIfFlexible(kotlinType));
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r2v4, types: [org.jetbrains.kotlin.ir.expressions.IrExpression] */
    /* JADX WARN: Type inference failed for: r2v5 */
    /* JADX WARN: Type inference failed for: r2v6 */
    private static final IrExpression generateVarargExpressionUsing(StatementGenerator statementGenerator, VarargValueArgument varargValueArgument, ValueParameterDescriptor valueParameterDescriptor, ResolvedCall<?> resolvedCall, Function1<? super KtExpression, ? extends IrExpression> function1) {
        if (varargValueArgument.getArguments().isEmpty()) {
            return null;
        }
        List<ValueArgument> arguments = varargValueArgument.getArguments();
        Intrinsics.checkNotNullExpressionValue(arguments, "getArguments(...)");
        Iterator it = arguments.iterator();
        int iMin = Integer.MAX_VALUE;
        while (it.hasNext()) {
            iMin = Math.min(iMin, PsiUtilsKt.getStartOffsetSkippingComments(((ValueArgument) it.next()).getCallElement()));
        }
        List<ValueArgument> arguments2 = varargValueArgument.getArguments();
        Intrinsics.checkNotNullExpressionValue(arguments2, "getArguments(...)");
        Iterator it2 = arguments2.iterator();
        int iMax = Integer.MIN_VALUE;
        while (it2.hasNext()) {
            iMax = Math.max(iMax, PsiUtilsKt.getEndOffset(((ValueArgument) it2.next()).getCallElement()));
        }
        KotlinType varargElementType = valueParameterDescriptor.getVarargElementType();
        if (varargElementType == null) {
            throw new AssertionError("Vararg argument for non-vararg parameter " + valueParameterDescriptor);
        }
        KotlinType type = valueParameterDescriptor.getType();
        Intrinsics.checkNotNullExpressionValue(type, "getType(...)");
        IrVarargImpl irVarargImpl = new IrVarargImpl(iMin, iMax, computeVarargType(statementGenerator, type), statementGenerator.toIrType(varargElementType));
        for (ValueArgument valueArgument : varargValueArgument.getArguments()) {
            KtExpression argumentExpression = valueArgument.getArgumentExpression();
            if (argumentExpression == null) {
                throw new AssertionError("No argument expression for vararg element " + valueArgument.getCallElement().getText());
            }
            IrExpression irExpressionInvoke = function1.invoke(argumentExpression);
            if (irExpressionInvoke != null) {
                Intrinsics.checkNotNull(valueArgument);
                ?? ApplySuspendConversionForValueArgumentIfRequired = applySuspendConversionForValueArgumentIfRequired(statementGenerator, irExpressionInvoke, valueArgument, valueParameterDescriptor, resolvedCall);
                if (ApplySuspendConversionForValueArgumentIfRequired != 0) {
                    if (valueArgument.getSpreadElement() != null || (statementGenerator.getContext().getLanguageVersionSettings().supportsFeature(LanguageFeature.AllowAssigningArrayElementsToVarargsInNamedFormForFunctions) && valueArgument.isNamed())) {
                        KtExpression ktExpression = argumentExpression;
                        ApplySuspendConversionForValueArgumentIfRequired = new IrSpreadElementImpl(PsiUtilsKt.getStartOffsetSkippingComments(ktExpression), PsiUtilsKt.getEndOffset(ktExpression), ApplySuspendConversionForValueArgumentIfRequired);
                    }
                    IrExpressionsKt.addElement(irVarargImpl, (IrVarargElement) ((IrElement) ApplySuspendConversionForValueArgumentIfRequired));
                }
            }
            throw new AssertionError("no expression for vararg element " + argumentExpression.getText());
        }
        return irVarargImpl;
    }

    private static final IrExpression generateValueArgument(final StatementGenerator statementGenerator, ResolvedValueArgument resolvedValueArgument, ValueParameterDescriptor valueParameterDescriptor, ResolvedCall<?> resolvedCall) {
        return generateValueArgumentUsing(statementGenerator, resolvedValueArgument, valueParameterDescriptor, resolvedCall, new Function1<KtExpression, IrExpression>() { // from class: org.jetbrains.kotlin.psi2ir.generators.ArgumentsGenerationUtilsKt.generateValueArgument.1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final IrExpression invoke(KtExpression it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return statementGenerator.generateExpression(it);
            }
        });
    }

    private static final IrExpression generateValueArgumentUsing(StatementGenerator statementGenerator, ResolvedValueArgument resolvedValueArgument, ValueParameterDescriptor valueParameterDescriptor, ResolvedCall<?> resolvedCall, Function1<? super KtExpression, ? extends IrExpression> function1) {
        if (resolvedValueArgument instanceof DefaultValueArgument) {
            return null;
        }
        if (resolvedValueArgument instanceof ExpressionValueArgument) {
            ValueArgument valueArgument = ((ExpressionValueArgument) resolvedValueArgument).getValueArgument();
            if (valueArgument == null) {
                throw new AssertionError("No value argument: " + resolvedValueArgument);
            }
            KtExpression argumentExpression = valueArgument.getArgumentExpression();
            if (argumentExpression == null) {
                throw new AssertionError("No argument expression: " + valueArgument);
            }
            IrExpression irExpressionInvoke = function1.invoke(argumentExpression);
            if (irExpressionInvoke != null) {
                return applySuspendConversionForValueArgumentIfRequired(statementGenerator, irExpressionInvoke, valueArgument, valueParameterDescriptor, resolvedCall);
            }
            return null;
        }
        if (resolvedValueArgument instanceof VarargValueArgument) {
            return generateVarargExpressionUsing(statementGenerator, (VarargValueArgument) resolvedValueArgument, valueParameterDescriptor, resolvedCall, function1);
        }
        throw new NotImplementedError("An operation is not implemented: " + ("Unexpected valueArgument: " + resolvedValueArgument.getClass().getSimpleName()));
    }

    private static final IrExpression applySuspendConversionForValueArgumentIfRequired(StatementGenerator statementGenerator, IrExpression irExpression, ValueArgument valueArgument, ValueParameterDescriptor valueParameterDescriptor, ResolvedCall<?> resolvedCall) {
        UnwrappedType expectedTypeForSuspendConvertedArgument;
        KotlinType type;
        if (!statementGenerator.getContext().getLanguageVersionSettings().supportsFeature(LanguageFeature.SuspendConversion)) {
            return irExpression;
        }
        if ((irExpression instanceof IrBlock) && Intrinsics.areEqual(((IrBlock) irExpression).getOrigin(), IrStatementOrigin.ADAPTED_FUNCTION_REFERENCE.INSTANCE)) {
            return irExpression;
        }
        NewResolvedCallImpl newResolvedCallImpl = resolvedCall instanceof NewResolvedCallImpl ? (NewResolvedCallImpl) resolvedCall : null;
        if (newResolvedCallImpl == null || (expectedTypeForSuspendConvertedArgument = newResolvedCallImpl.getExpectedTypeForSuspendConvertedArgument(valueArgument)) == null) {
            return irExpression;
        }
        if (ArgumentsUtilsKt.isVararg(valueParameterDescriptor)) {
            type = valueParameterDescriptor.getVarargElementType();
            Intrinsics.checkNotNull(type);
        } else {
            type = valueParameterDescriptor.getType();
            Intrinsics.checkNotNullExpressionValue(type, "getType(...)");
        }
        if (SamConversionsKt.isSamType(statementGenerator.getContext().getExtensions().getSamConversion(), type)) {
            type = SamConversionsKt.getSubstitutedFunctionTypeForSamType(type);
        }
        IrType irType = statementGenerator.toIrType(type);
        IrBlockImpl irBlockImpl = new IrBlockImpl(irExpression.getStartOffset(), irExpression.getEndOffset(), irType, IrStatementOrigin.SUSPEND_CONVERSION.INSTANCE);
        IrSimpleFunction irSimpleFunctionCreateFunctionForSuspendConversion = createFunctionForSuspendConversion(statementGenerator, irBlockImpl.getStartOffset(), irBlockImpl.getEndOffset(), expectedTypeForSuspendConvertedArgument, type);
        IrFunctionReferenceImpl irFunctionReferenceImpl = new IrFunctionReferenceImpl(irBlockImpl.getStartOffset(), irBlockImpl.getEndOffset(), irType, irSimpleFunctionCreateFunctionForSuspendConversion.getSymbol(), irSimpleFunctionCreateFunctionForSuspendConversion.getTypeParameters().size(), irSimpleFunctionCreateFunctionForSuspendConversion.getValueParameters().size(), null, IrStatementOrigin.SUSPEND_CONVERSION.INSTANCE);
        irBlockImpl.getStatements().add(irSimpleFunctionCreateFunctionForSuspendConversion);
        List<IrStatement> statements = irBlockImpl.getStatements();
        irFunctionReferenceImpl.setExtensionReceiver(irExpression);
        statements.add(irFunctionReferenceImpl);
        return irBlockImpl;
    }

    private static final IrSimpleFunction createFunctionForSuspendConversion(StatementGenerator statementGenerator, int i, int i2, KotlinType kotlinType, KotlinType kotlinType2) {
        Object next;
        KotlinType nullableAnyType = ((TypeProjection) CollectionsKt.last((List) kotlinType.getArguments())).getNullableAnyType();
        Intrinsics.checkNotNullExpressionValue(nullableAnyType, "getType(...)");
        IrType irType = statementGenerator.toIrType(nullableAnyType);
        KotlinType nullableAnyType2 = ((TypeProjection) CollectionsKt.last((List) kotlinType2.getArguments())).getNullableAnyType();
        Intrinsics.checkNotNullExpressionValue(nullableAnyType2, "getType(...)");
        IrType irType2 = statementGenerator.toIrType(nullableAnyType2);
        IrFactory irFactory = statementGenerator.getContext().getIrFactory();
        IrDeclarationOrigin.ADAPTER_FOR_SUSPEND_CONVERSION adapter_for_suspend_conversion = IrDeclarationOrigin.ADAPTER_FOR_SUSPEND_CONVERSION.INSTANCE;
        Name nameIdentifier = Name.identifier(Scope.inventNameForTemporary$default(statementGenerator.getScope(), "suspendConversion", null, 2, null));
        Intrinsics.checkNotNullExpressionValue(nameIdentifier, "identifier(...)");
        DescriptorVisibility LOCAL = DescriptorVisibilities.LOCAL;
        Intrinsics.checkNotNullExpressionValue(LOCAL, "LOCAL");
        IrSimpleFunction irSimpleFunctionCreateSimpleFunction$default = IrFactory.DefaultImpls.createSimpleFunction$default(irFactory, i, i2, adapter_for_suspend_conversion, nameIdentifier, LOCAL, false, false, irType2, Modality.FINAL, new IrSimpleFunctionSymbolImpl(null, 1, null), false, true, false, false, false, null, false, 98304, null);
        IrSimpleFunction irSimpleFunction = irSimpleFunctionCreateSimpleFunction$default;
        statementGenerator.getContext().getSymbolTable().enterScope(irSimpleFunction);
        irSimpleFunctionCreateSimpleFunction$default.setExtensionReceiverParameter(createFunctionForSuspendConversion$createValueParameter(statementGenerator, i, i2, "callee", -1, statementGenerator.toIrType(kotlinType)));
        List listTake = CollectionsKt.take(kotlinType2.getArguments(), kotlinType2.getArguments().size() - 1);
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(listTake, 10));
        int i3 = 0;
        for (Object obj : listTake) {
            int i4 = i3 + 1;
            if (i3 < 0) {
                CollectionsKt.throwIndexOverflow();
            }
            KotlinType nullableAnyType3 = ((TypeProjection) obj).getNullableAnyType();
            Intrinsics.checkNotNullExpressionValue(nullableAnyType3, "getType(...)");
            arrayList.add(createFunctionForSuspendConversion$createValueParameter(statementGenerator, i, i2, "p" + i3, i3, statementGenerator.toIrType(nullableAnyType3)));
            i3 = i4;
        }
        irSimpleFunctionCreateSimpleFunction$default.setValueParameters(arrayList);
        int size = irSimpleFunctionCreateSimpleFunction$default.getValueParameters().size();
        Iterator<T> it = kotlinType.getMemberScope().getContributedFunctions(OperatorNameConventions.INVOKE, NoLookupLocation.FROM_BACKEND).iterator();
        do {
            if (!it.hasNext()) {
                next = null;
                break;
            }
            next = it.next();
        } while (((SimpleFunctionDescriptor) next).getValueParameters().size() != size);
        SimpleFunctionDescriptor simpleFunctionDescriptor = (SimpleFunctionDescriptor) next;
        if (simpleFunctionDescriptor == null) {
            throw new IllegalStateException(("No matching operator fun 'invoke' for suspend conversion: funType=" + kotlinType + ", suspendFunType=" + kotlinType2).toString());
        }
        DescriptorSymbolTableExtension descriptorExtension = statementGenerator.getContext().getSymbolTable().getDescriptorExtension();
        SimpleFunctionDescriptor original = simpleFunctionDescriptor.getOriginal();
        Intrinsics.checkNotNullExpressionValue(original, "getOriginal(...)");
        IrSimpleFunctionSymbol irSimpleFunctionSymbolReferenceSimpleFunction = descriptorExtension.referenceSimpleFunction(original);
        StatementGenerator statementGenerator2 = statementGenerator;
        IrBlockBodyBuilder irBlockBodyBuilder = new IrBlockBodyBuilder(statementGenerator2.getContext(), statementGenerator2.getScope(), i, i2);
        IrCallImpl irCallImpl = new IrCallImpl(i, i2, irType, irSimpleFunctionSymbolReferenceSimpleFunction, 0, size, null, null, 192, null);
        IrBlockBodyBuilder irBlockBodyBuilder2 = irBlockBodyBuilder;
        IrValueParameter extensionReceiverParameter = irSimpleFunctionCreateSimpleFunction$default.getExtensionReceiverParameter();
        Intrinsics.checkNotNull(extensionReceiverParameter);
        irCallImpl.setDispatchReceiver(ExpressionHelpersKt.irGet(irBlockBodyBuilder2, extensionReceiverParameter));
        statementGenerator.getContext().getCallToSubstitutedDescriptorMap$ir_psi2ir().put(irCallImpl, simpleFunctionDescriptor);
        for (IrValueParameter irValueParameter : irSimpleFunctionCreateSimpleFunction$default.getValueParameters()) {
            irCallImpl.putValueArgument(irValueParameter.getIndex(), ExpressionHelpersKt.irGet(irBlockBodyBuilder2, irValueParameter));
        }
        KotlinType nullableAnyType4 = ((TypeProjection) CollectionsKt.last((List) kotlinType2.getArguments())).getNullableAnyType();
        Intrinsics.checkNotNullExpressionValue(nullableAnyType4, "getType(...)");
        if (TypeUtilsKt.isUnit(nullableAnyType4)) {
            irBlockBodyBuilder.unaryPlus(irCallImpl);
        } else {
            irBlockBodyBuilder.unaryPlus(new IrReturnImpl(i, i2, irBlockBodyBuilder.getContext().getIrBuiltIns().getNothingType(), irSimpleFunctionCreateSimpleFunction$default.getSymbol(), irCallImpl));
        }
        irSimpleFunctionCreateSimpleFunction$default.setBody(irBlockBodyBuilder.getIrBlockBody());
        statementGenerator.getContext().getSymbolTable().leaveScope(irSimpleFunction);
        return irSimpleFunctionCreateSimpleFunction$default;
    }

    private static final IrValueParameter createFunctionForSuspendConversion$createValueParameter(StatementGenerator statementGenerator, int i, int i2, String str, int i3, IrType irType) {
        IrFactory irFactory = statementGenerator.getContext().getIrFactory();
        IrDeclarationOrigin.ADAPTER_PARAMETER_FOR_SUSPEND_CONVERSION adapter_parameter_for_suspend_conversion = IrDeclarationOrigin.ADAPTER_PARAMETER_FOR_SUSPEND_CONVERSION.INSTANCE;
        Name nameIdentifier = Name.identifier(str);
        Intrinsics.checkNotNullExpressionValue(nameIdentifier, "identifier(...)");
        return irFactory.createValueParameter(i, i2, (IrDeclarationOrigin) adapter_parameter_for_suspend_conversion, nameIdentifier, irType, false, (IrValueParameterSymbol) new IrValueParameterSymbolImpl(null, 1, null), i3, (IrType) null, false, false, false);
    }

    public static final IrExpression castArgumentToFunctionalInterfaceForSamType(StatementGenerator statementGenerator, IrExpression irExpression, KotlinType samType) {
        Intrinsics.checkNotNullParameter(statementGenerator, "<this>");
        Intrinsics.checkNotNullParameter(irExpression, "irExpression");
        Intrinsics.checkNotNullParameter(samType, "samType");
        return IrExpressionsKt.implicitCastTo(irExpression, statementGenerator.getContext().getTypeTranslator().translateType(SamConversionsKt.getSubstitutedFunctionTypeForSamType(samType)));
    }

    public static final ClassDescriptor getSuperQualifier(Generator generator, ResolvedCall<?> resolvedCall) {
        Intrinsics.checkNotNullParameter(generator, "<this>");
        Intrinsics.checkNotNullParameter(resolvedCall, "resolvedCall");
        Call call = resolvedCall.getCall();
        Intrinsics.checkNotNullExpressionValue(call, "getCall(...)");
        KtSuperExpression superCallExpression = CallResolverUtilKt.getSuperCallExpression(call);
        if (superCallExpression == null) {
            return null;
        }
        WritableSlice<KtReferenceExpression, DeclarationDescriptor> REFERENCE_TARGET = BindingContext.REFERENCE_TARGET;
        Intrinsics.checkNotNullExpressionValue(REFERENCE_TARGET, "REFERENCE_TARGET");
        Object orFail = GeneratorKt.getOrFail(generator, REFERENCE_TARGET, superCallExpression.getInstanceReference());
        Intrinsics.checkNotNull(orFail, "null cannot be cast to non-null type org.jetbrains.kotlin.descriptors.ClassDescriptor");
        return (ClassDescriptor) orFail;
    }

    public static final CallBuilder pregenerateCall(final StatementGenerator statementGenerator, ResolvedCall<?> resolvedCall) {
        Intrinsics.checkNotNullParameter(statementGenerator, "<this>");
        Intrinsics.checkNotNullParameter(resolvedCall, "resolvedCall");
        return pregenerateCallUsing(statementGenerator, resolvedCall, new Function1<KtExpression, IrExpression>() { // from class: org.jetbrains.kotlin.psi2ir.generators.ArgumentsGenerationUtilsKt.pregenerateCall.1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final IrExpression invoke(KtExpression it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return statementGenerator.generateExpression(it);
            }
        });
    }

    public static final CallBuilder pregenerateCallUsing(StatementGenerator statementGenerator, ResolvedCall<?> resolvedCall, Function1<? super KtExpression, ? extends IrExpression> generateArgumentExpression) {
        Intrinsics.checkNotNullParameter(statementGenerator, "<this>");
        Intrinsics.checkNotNullParameter(resolvedCall, "resolvedCall");
        Intrinsics.checkNotNullParameter(generateArgumentExpression, "generateArgumentExpression");
        if (isExtensionInvokeCall(resolvedCall)) {
            return pregenerateExtensionInvokeCall(statementGenerator, resolvedCall);
        }
        CallBuilder callBuilderPregenerateCallReceivers = pregenerateCallReceivers(statementGenerator, resolvedCall);
        pregenerateValueArgumentsUsing(statementGenerator, callBuilderPregenerateCallReceivers, resolvedCall, generateArgumentExpression);
        generateSamConversionForValueArgumentsIfRequired(statementGenerator, callBuilderPregenerateCallReceivers, resolvedCall);
        return callBuilderPregenerateCallReceivers;
    }

    public static final Map<TypeParameterDescriptor, KotlinType> getTypeArguments(ResolvedCall<?> resolvedCall) {
        if (resolvedCall == null || resolvedCall.getResultingDescriptor().getTypeParameters().isEmpty()) {
            return null;
        }
        return resolvedCall.getTypeArguments();
    }

    private static final CallBuilder pregenerateExtensionInvokeCall(StatementGenerator statementGenerator, ResolvedCall<?> resolvedCall) {
        ExtensionInvokeCallReceiver extensionInvokeCallReceiver;
        CallableDescriptor resultingDescriptor = resolvedCall.getResultingDescriptor();
        DeclarationDescriptor containingDeclaration = resultingDescriptor.getContainingModule();
        ClassDescriptor classDescriptor = containingDeclaration instanceof ClassDescriptor ? (ClassDescriptor) containingDeclaration : null;
        if (classDescriptor == null) {
            throw new AssertionError("'invoke' should be a class member: " + resultingDescriptor);
        }
        MemberScope unsubstitutedMemberScope = classDescriptor.getUnsubstitutedMemberScope();
        Name name2 = resultingDescriptor.getShortName();
        Intrinsics.checkNotNullExpressionValue(name2, "getName(...)");
        Collection<? extends SimpleFunctionDescriptor> contributedFunctions = unsubstitutedMemberScope.getContributedFunctions(name2, NoLookupLocation.FROM_BACKEND);
        SimpleFunctionDescriptor simpleFunctionDescriptor = (SimpleFunctionDescriptor) CollectionsKt.singleOrNull(contributedFunctions);
        if (simpleFunctionDescriptor == null) {
            throw new AssertionError("There should be a single 'invoke' in FunctionN class: " + contributedFunctions);
        }
        simpleFunctionDescriptor.getTypeParameters().isEmpty();
        resultingDescriptor.getValueParameters().size();
        simpleFunctionDescriptor.getValueParameters().size();
        ReceiverParameterDescriptor receiverParameterDescriptorMo6753getDispatchReceiverParameter = resultingDescriptor.mo6753getDispatchReceiverParameter();
        Intrinsics.checkNotNull(receiverParameterDescriptorMo6753getDispatchReceiverParameter);
        KotlinType type = receiverParameterDescriptorMo6753getDispatchReceiverParameter.getType();
        Intrinsics.checkNotNullExpressionValue(type, "getType(...)");
        FunctionDescriptor functionDescriptorSubstitute = simpleFunctionDescriptor.substitute(TypeSubstitutor.create(type));
        if (functionDescriptorSubstitute == null) {
            throw new AssertionError("Substitution failed for " + simpleFunctionDescriptor + ", type=" + type);
        }
        KtElement callElement = resolvedCall.getCall().getCallElement();
        Intrinsics.checkNotNullExpressionValue(callElement, "getCallElement(...)");
        CallBuilder callBuilder = new CallBuilder(resolvedCall, functionDescriptorSubstitute, null, true);
        ReceiverValue receiverValueMo7304getDispatchReceiver = resolvedCall.getDispatchReceiver();
        if (receiverValueMo7304getDispatchReceiver == null) {
            throw new AssertionError("Extension 'invoke' call should have a dispatch receiver");
        }
        Intrinsics.checkNotNull(receiverValueMo7304getDispatchReceiver);
        IntermediateValue intermediateValueGenerateReceiver = generateReceiver(statementGenerator, callElement, receiverValueMo7304getDispatchReceiver);
        ReceiverValue receiverValueMo7305getExtensionReceiver = resolvedCall.getExtensionReceiver();
        if (receiverValueMo7305getExtensionReceiver == null) {
            throw new AssertionError("Extension 'invoke' call should have an extension receiver");
        }
        Intrinsics.checkNotNull(receiverValueMo7305getExtensionReceiver);
        IntermediateValue intermediateValueGenerateReceiver2 = generateReceiver(statementGenerator, callElement, receiverValueMo7305getExtensionReceiver);
        Call call = resolvedCall.getCall();
        Intrinsics.checkNotNullExpressionValue(call, "getCall(...)");
        if (CallUtilKt.isSafeCall(call)) {
            KtElement ktElement = callElement;
            extensionInvokeCallReceiver = new SafeExtensionInvokeCallReceiver(statementGenerator, PsiUtilsKt.getStartOffsetSkippingComments(ktElement), PsiUtilsKt.getEndOffset(ktElement), callBuilder, intermediateValueGenerateReceiver, intermediateValueGenerateReceiver2);
        } else {
            extensionInvokeCallReceiver = new ExtensionInvokeCallReceiver(callBuilder, intermediateValueGenerateReceiver, intermediateValueGenerateReceiver2);
        }
        callBuilder.setCallReceiver(extensionInvokeCallReceiver);
        callBuilder.getIrValueArgumentsByIndex()[0] = null;
        Map<ValueParameterDescriptor, ResolvedValueArgument> valueArguments = resolvedCall.getValueArguments();
        Intrinsics.checkNotNullExpressionValue(valueArguments, "getValueArguments(...)");
        for (Map.Entry<ValueParameterDescriptor, ResolvedValueArgument> entry : valueArguments.entrySet()) {
            ValueParameterDescriptor key = entry.getKey();
            ResolvedValueArgument value = entry.getValue();
            IrExpression[] irValueArgumentsByIndex = callBuilder.getIrValueArgumentsByIndex();
            int index = key.getIndex() + 1;
            Intrinsics.checkNotNull(value);
            Intrinsics.checkNotNull(key);
            irValueArgumentsByIndex[index] = generateValueArgument(statementGenerator, value, key, resolvedCall);
        }
        return callBuilder;
    }

    private static final boolean isExtensionInvokeCall(ResolvedCall<?> resolvedCall) {
        ReceiverParameterDescriptor dispatchReceiverParameter;
        KotlinType type;
        CallableDescriptor resultingDescriptor = resolvedCall.getResultingDescriptor();
        SimpleFunctionDescriptor simpleFunctionDescriptor = resultingDescriptor instanceof SimpleFunctionDescriptor ? (SimpleFunctionDescriptor) resultingDescriptor : null;
        return (simpleFunctionDescriptor == null || !Intrinsics.areEqual(simpleFunctionDescriptor.getShortName().asString(), "invoke") || (dispatchReceiverParameter = simpleFunctionDescriptor.mo6753getDispatchReceiverParameter()) == null || (type = dispatchReceiverParameter.getType()) == null || !FunctionTypesKt.isBuiltinFunctionalType(type) || resolvedCall.getExtensionReceiver() == null) ? false : true;
    }

    /* JADX WARN: Code duplicated, block: B:103:0x0292 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:104:0x027d A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:105:0x025c A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:106:0x024c A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:35:0x0148  */
    /* JADX WARN: Code duplicated, block: B:49:0x0171  */
    /* JADX WARN: Code duplicated, block: B:51:0x017e  */
    /* JADX WARN: Code duplicated, block: B:54:0x0191  */
    /* JADX WARN: Code duplicated, block: B:56:0x01a4  */
    /* JADX WARN: Code duplicated, block: B:58:0x01aa  */
    /* JADX WARN: Code duplicated, block: B:64:0x01c1  */
    /* JADX WARN: Code duplicated, block: B:66:0x01cb  */
    /* JADX WARN: Code duplicated, block: B:68:0x01d9  */
    /* JADX WARN: Code duplicated, block: B:69:0x01df  */
    /* JADX WARN: Code duplicated, block: B:71:0x01e5  */
    /* JADX WARN: Code duplicated, block: B:73:0x01f1  */
    /* JADX WARN: Code duplicated, block: B:76:0x021b  */
    /* JADX WARN: Code duplicated, block: B:78:0x0223  */
    /* JADX WARN: Code duplicated, block: B:81:0x022e A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:82:0x0230  */
    /* JADX WARN: Code duplicated, block: B:83:0x0237  */
    /* JADX WARN: Code duplicated, block: B:85:0x023a  */
    /* JADX WARN: Code duplicated, block: B:86:0x0241  */
    public static final void generateSamConversionForValueArgumentsIfRequired(StatementGenerator statementGenerator, CallBuilder call, ResolvedCall<?> resolvedCall) {
        ArrayList arrayList;
        KotlinType type;
        KotlinType type2;
        KotlinType type3;
        KotlinType samTypeForValueParameter;
        IrExpression irExpression;
        KotlinType kotlinTypeSubstitute;
        IrType irType;
        KotlinType kotlinTypeSubstitute2;
        List<IrVarargElement> elements;
        Iterator it;
        int i;
        IrVarargImpl irVarargImplGenerateSamConversionForValueArgumentsIfRequired$samConvertScalarExpression;
        int i2;
        IrVarargElement irVarargElement;
        Iterator it2;
        UnwrappedType unwrappedType;
        IrExpression irExpressionGenerateSamConversionForValueArgumentsIfRequired$samConvertScalarExpression;
        Map<ValueParameterDescriptor, ResolvedCallArgument> argumentMappingByOriginal;
        ResolvedCall<?> resolvedCall2 = resolvedCall;
        Intrinsics.checkNotNullParameter(statementGenerator, "<this>");
        Intrinsics.checkNotNullParameter(call, "call");
        Intrinsics.checkNotNullParameter(resolvedCall2, "resolvedCall");
        GeneratorExtensions.SamConversion samConversion = statementGenerator.getContext().getExtensions().getSamConversion();
        CallableDescriptor resultingDescriptor = resolvedCall.getResultingDescriptor();
        Intrinsics.checkNotNull(resultingDescriptor);
        FunctionDescriptor originalForFunctionInterfaceAdapter = SamConversionsKt.getOriginalForFunctionInterfaceAdapter(resultingDescriptor);
        FunctionDescriptor functionDescriptor = originalForFunctionInterfaceAdapter != null ? originalForFunctionInterfaceAdapter : resultingDescriptor;
        List<ValueParameterDescriptor> valueParameters = resultingDescriptor.getValueParameters();
        Intrinsics.checkNotNullExpressionValue(valueParameters, "getValueParameters(...)");
        List<ValueParameterDescriptor> valueParameters2 = functionDescriptor.getValueParameters();
        Intrinsics.checkNotNullExpressionValue(valueParameters2, "getValueParameters(...)");
        valueParameters.size();
        valueParameters2.size();
        valueParameters.size();
        CallBuilderKt.getArgumentsCount(call);
        functionDescriptor.getTypeParameters().size();
        resultingDescriptor.getTypeParameters().size();
        boolean z = resolvedCall2 instanceof NewResolvedCallImpl;
        NewResolvedCallImpl newResolvedCallImpl = z ? (NewResolvedCallImpl) resolvedCall2 : null;
        Collection<ResolvedCallArgument> collectionValues = (newResolvedCallImpl == null || (argumentMappingByOriginal = newResolvedCallImpl.getArgumentMappingByOriginal()) == null) ? null : argumentMappingByOriginal.values();
        if (collectionValues != null) {
            collectionValues.size();
            valueParameters2.size();
        }
        Set<Map.Entry<TypeParameterDescriptor, KotlinType>> setEntrySet = call.getOriginal().getTypeArguments().entrySet();
        int i3 = 10;
        LinkedHashMap linkedHashMap = new LinkedHashMap(RangesKt.coerceAtLeast(MapsKt.mapCapacity(CollectionsKt.collectionSizeOrDefault(setEntrySet, 10)), 16));
        Iterator<T> it3 = setEntrySet.iterator();
        while (it3.hasNext()) {
            Map.Entry entry = (Map.Entry) it3.next();
            Pair pair = TuplesKt.to(functionDescriptor.getTypeParameters().get(((TypeParameterDescriptor) entry.getKey()).getIndex()).getTypeConstructor(), new TypeProjectionImpl((KotlinType) entry.getValue()));
            linkedHashMap.put(pair.getFirst(), pair.getSecond());
        }
        TypeSubstitutor typeSubstitutorCreate = TypeSubstitutor.create(linkedHashMap);
        Intrinsics.checkNotNullExpressionValue(typeSubstitutorCreate, "create(...)");
        int size = valueParameters2.size();
        int i4 = 0;
        while (i4 < size) {
            ValueParameterDescriptor valueParameterDescriptor = valueParameters2.get(i4);
            Intrinsics.checkNotNullExpressionValue(valueParameterDescriptor, "get(...)");
            ValueParameterDescriptor valueParameterDescriptor2 = valueParameterDescriptor;
            if (z) {
                NewResolvedCallImpl newResolvedCallImpl2 = (NewResolvedCallImpl) resolvedCall2;
                ResolvedValueArgument resolvedValueArgument = newResolvedCallImpl2.getValueArguments().get(valueParameters.get(i4));
                List<ValueArgument> arguments = resolvedValueArgument != null ? resolvedValueArgument.getArguments() : null;
                if (arguments != null) {
                    List<ValueArgument> list = arguments;
                    ArrayList arrayList2 = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, i3));
                    for (ValueArgument valueArgument : list) {
                        Intrinsics.checkNotNull(valueArgument);
                        arrayList2.add(newResolvedCallImpl2.getExpectedTypeForSamConvertedArgument(valueArgument));
                    }
                    arrayList = arrayList2;
                } else {
                    arrayList = null;
                }
            } else {
                arrayList = null;
            }
            if (arrayList == null) {
                type = valueParameterDescriptor2.getType();
                Intrinsics.checkNotNullExpressionValue(type, "getType(...)");
                if (!SamConversionsKt.isSamType(samConversion, type)) {
                    type3 = valueParameterDescriptor2.getOriginal().getType();
                    Intrinsics.checkNotNullExpressionValue(type3, "getType(...)");
                    if (SamConversionsKt.isSamType(samConversion, type3)) {
                        type2 = valueParameters.get(i4).getType();
                        Intrinsics.checkNotNullExpressionValue(type2, "getType(...)");
                        if (FunctionTypesKt.isFunctionType(type2)) {
                            samTypeForValueParameter = getSamTypeForValueParameter(statementGenerator, valueParameterDescriptor2);
                            if (samTypeForValueParameter == null) {
                                samTypeForValueParameter = valueParameterDescriptor2.getType();
                                Intrinsics.checkNotNullExpressionValue(samTypeForValueParameter, "getType(...)");
                            }
                            irExpression = call.getIrValueArgumentsByIndex()[i4];
                            if (irExpression != null) {
                                kotlinTypeSubstitute = typeSubstitutorCreate.substitute(samTypeForValueParameter, Variance.INVARIANT);
                                if (kotlinTypeSubstitute == null) {
                                    throw new AssertionError("Failed to substitute value argument type in SAM conversion: underlyingParameterType=" + valueParameterDescriptor2.getType() + ", substitutionContext=" + linkedHashMap);
                                }
                                irType = statementGenerator.toIrType(kotlinTypeSubstitute);
                                IrExpression[] irValueArgumentsByIndex = call.getIrValueArgumentsByIndex();
                                if (!(irExpression instanceof IrVararg)) {
                                    irVarargImplGenerateSamConversionForValueArgumentsIfRequired$samConvertScalarExpression = generateSamConversionForValueArgumentsIfRequired$samConvertScalarExpression(irType, statementGenerator, kotlinTypeSubstitute, irExpression);
                                } else {
                                    if (valueParameterDescriptor2.getVarargElementType() == null) {
                                        throw new AssertionError("Vararg parameter expected for vararg argument: " + valueParameterDescriptor2);
                                    }
                                    kotlinTypeSubstitute2 = typeSubstitutorCreate.substitute(valueParameterDescriptor2.getType(), Variance.INVARIANT);
                                    if (kotlinTypeSubstitute2 == null) {
                                        throw new AssertionError("Failed to substitute vararg type in SAM conversion: type=" + valueParameterDescriptor2.getType() + ", substitutionContext=" + linkedHashMap);
                                    }
                                    IrVarargImpl irVarargImpl = new IrVarargImpl(irExpression.getStartOffset(), irExpression.getEndOffset(), computeVarargType(statementGenerator, kotlinTypeSubstitute2), irType);
                                    List<IrVarargElement> elements2 = ((IrVararg) irExpression).getElements();
                                    elements = irVarargImpl.getElements();
                                    it = elements2.iterator();
                                    i = 0;
                                    while (it.hasNext()) {
                                        Object next = it.next();
                                        i2 = i + 1;
                                        if (i < 0) {
                                            CollectionsKt.throwIndexOverflow();
                                        }
                                        irVarargElement = (IrVarargElement) next;
                                        it2 = it;
                                        if (!(irVarargElement instanceof IrExpression)) {
                                            throw new AssertionError("Unsupported: spread vararg element with SAM conversion");
                                        }
                                        if (arrayList != null) {
                                            unwrappedType = (UnwrappedType) arrayList.get(i);
                                        } else {
                                            unwrappedType = null;
                                        }
                                        if (unwrappedType != null) {
                                            irExpressionGenerateSamConversionForValueArgumentsIfRequired$samConvertScalarExpression = generateSamConversionForValueArgumentsIfRequired$samConvertScalarExpression(irType, statementGenerator, kotlinTypeSubstitute, (IrExpression) irVarargElement);
                                        } else {
                                            irExpressionGenerateSamConversionForValueArgumentsIfRequired$samConvertScalarExpression = (IrExpression) irVarargElement;
                                        }
                                        elements.add(irExpressionGenerateSamConversionForValueArgumentsIfRequired$samConvertScalarExpression);
                                        i = i2;
                                        it = it2;
                                    }
                                    Unit unit = Unit.INSTANCE;
                                    irVarargImplGenerateSamConversionForValueArgumentsIfRequired$samConvertScalarExpression = irVarargImpl;
                                }
                                irValueArgumentsByIndex[i4] = irVarargImplGenerateSamConversionForValueArgumentsIfRequired$samConvertScalarExpression;
                            }
                        }
                        i4++;
                        resolvedCall2 = resolvedCall;
                        samConversion = samConversion;
                        i3 = 10;
                    }
                } else {
                    type2 = valueParameters.get(i4).getType();
                    Intrinsics.checkNotNullExpressionValue(type2, "getType(...)");
                    if (FunctionTypesKt.isFunctionType(type2)) {
                        samTypeForValueParameter = getSamTypeForValueParameter(statementGenerator, valueParameterDescriptor2);
                        if (samTypeForValueParameter == null) {
                            samTypeForValueParameter = valueParameterDescriptor2.getType();
                            Intrinsics.checkNotNullExpressionValue(samTypeForValueParameter, "getType(...)");
                        }
                        irExpression = call.getIrValueArgumentsByIndex()[i4];
                        if (irExpression != null) {
                            kotlinTypeSubstitute = typeSubstitutorCreate.substitute(samTypeForValueParameter, Variance.INVARIANT);
                            if (kotlinTypeSubstitute == null) {
                                throw new AssertionError("Failed to substitute value argument type in SAM conversion: underlyingParameterType=" + valueParameterDescriptor2.getType() + ", substitutionContext=" + linkedHashMap);
                            }
                            irType = statementGenerator.toIrType(kotlinTypeSubstitute);
                            IrExpression[] irValueArgumentsByIndex2 = call.getIrValueArgumentsByIndex();
                            if (!(irExpression instanceof IrVararg)) {
                                irVarargImplGenerateSamConversionForValueArgumentsIfRequired$samConvertScalarExpression = generateSamConversionForValueArgumentsIfRequired$samConvertScalarExpression(irType, statementGenerator, kotlinTypeSubstitute, irExpression);
                            } else {
                                if (valueParameterDescriptor2.getVarargElementType() == null) {
                                    throw new AssertionError("Vararg parameter expected for vararg argument: " + valueParameterDescriptor2);
                                }
                                kotlinTypeSubstitute2 = typeSubstitutorCreate.substitute(valueParameterDescriptor2.getType(), Variance.INVARIANT);
                                if (kotlinTypeSubstitute2 == null) {
                                    throw new AssertionError("Failed to substitute vararg type in SAM conversion: type=" + valueParameterDescriptor2.getType() + ", substitutionContext=" + linkedHashMap);
                                }
                                IrVarargImpl irVarargImpl2 = new IrVarargImpl(irExpression.getStartOffset(), irExpression.getEndOffset(), computeVarargType(statementGenerator, kotlinTypeSubstitute2), irType);
                                List<IrVarargElement> elements3 = ((IrVararg) irExpression).getElements();
                                elements = irVarargImpl2.getElements();
                                it = elements3.iterator();
                                i = 0;
                                while (it.hasNext()) {
                                    Object next2 = it.next();
                                    i2 = i + 1;
                                    if (i < 0) {
                                        CollectionsKt.throwIndexOverflow();
                                    }
                                    irVarargElement = (IrVarargElement) next2;
                                    it2 = it;
                                    if (!(irVarargElement instanceof IrExpression)) {
                                        throw new AssertionError("Unsupported: spread vararg element with SAM conversion");
                                    }
                                    if (arrayList != null) {
                                        unwrappedType = (UnwrappedType) arrayList.get(i);
                                    } else {
                                        unwrappedType = null;
                                    }
                                    if (unwrappedType != null) {
                                        irExpressionGenerateSamConversionForValueArgumentsIfRequired$samConvertScalarExpression = generateSamConversionForValueArgumentsIfRequired$samConvertScalarExpression(irType, statementGenerator, kotlinTypeSubstitute, (IrExpression) irVarargElement);
                                    } else {
                                        irExpressionGenerateSamConversionForValueArgumentsIfRequired$samConvertScalarExpression = (IrExpression) irVarargElement;
                                    }
                                    elements.add(irExpressionGenerateSamConversionForValueArgumentsIfRequired$samConvertScalarExpression);
                                    i = i2;
                                    it = it2;
                                }
                                Unit unit2 = Unit.INSTANCE;
                                irVarargImplGenerateSamConversionForValueArgumentsIfRequired$samConvertScalarExpression = irVarargImpl2;
                            }
                            irValueArgumentsByIndex2[i4] = irVarargImplGenerateSamConversionForValueArgumentsIfRequired$samConvertScalarExpression;
                        }
                    }
                    i4++;
                    resolvedCall2 = resolvedCall;
                    samConversion = samConversion;
                    i3 = 10;
                }
            } else {
                ArrayList arrayList3 = arrayList;
                if ((arrayList3 instanceof Collection) && arrayList3.isEmpty()) {
                    type = valueParameterDescriptor2.getType();
                    Intrinsics.checkNotNullExpressionValue(type, "getType(...)");
                    if (!SamConversionsKt.isSamType(samConversion, type)) {
                        type3 = valueParameterDescriptor2.getOriginal().getType();
                        Intrinsics.checkNotNullExpressionValue(type3, "getType(...)");
                        if (SamConversionsKt.isSamType(samConversion, type3)) {
                            type2 = valueParameters.get(i4).getType();
                            Intrinsics.checkNotNullExpressionValue(type2, "getType(...)");
                            if (FunctionTypesKt.isFunctionType(type2)) {
                                samTypeForValueParameter = getSamTypeForValueParameter(statementGenerator, valueParameterDescriptor2);
                                if (samTypeForValueParameter == null) {
                                    samTypeForValueParameter = valueParameterDescriptor2.getType();
                                    Intrinsics.checkNotNullExpressionValue(samTypeForValueParameter, "getType(...)");
                                }
                                irExpression = call.getIrValueArgumentsByIndex()[i4];
                                if (irExpression != null) {
                                    kotlinTypeSubstitute = typeSubstitutorCreate.substitute(samTypeForValueParameter, Variance.INVARIANT);
                                    if (kotlinTypeSubstitute == null) {
                                        throw new AssertionError("Failed to substitute value argument type in SAM conversion: underlyingParameterType=" + valueParameterDescriptor2.getType() + ", substitutionContext=" + linkedHashMap);
                                    }
                                    irType = statementGenerator.toIrType(kotlinTypeSubstitute);
                                    IrExpression[] irValueArgumentsByIndex3 = call.getIrValueArgumentsByIndex();
                                    if (!(irExpression instanceof IrVararg)) {
                                        irVarargImplGenerateSamConversionForValueArgumentsIfRequired$samConvertScalarExpression = generateSamConversionForValueArgumentsIfRequired$samConvertScalarExpression(irType, statementGenerator, kotlinTypeSubstitute, irExpression);
                                    } else {
                                        if (valueParameterDescriptor2.getVarargElementType() == null) {
                                            throw new AssertionError("Vararg parameter expected for vararg argument: " + valueParameterDescriptor2);
                                        }
                                        kotlinTypeSubstitute2 = typeSubstitutorCreate.substitute(valueParameterDescriptor2.getType(), Variance.INVARIANT);
                                        if (kotlinTypeSubstitute2 == null) {
                                            throw new AssertionError("Failed to substitute vararg type in SAM conversion: type=" + valueParameterDescriptor2.getType() + ", substitutionContext=" + linkedHashMap);
                                        }
                                        IrVarargImpl irVarargImpl3 = new IrVarargImpl(irExpression.getStartOffset(), irExpression.getEndOffset(), computeVarargType(statementGenerator, kotlinTypeSubstitute2), irType);
                                        List<IrVarargElement> elements4 = ((IrVararg) irExpression).getElements();
                                        elements = irVarargImpl3.getElements();
                                        it = elements4.iterator();
                                        i = 0;
                                        while (it.hasNext()) {
                                            Object next3 = it.next();
                                            i2 = i + 1;
                                            if (i < 0) {
                                                CollectionsKt.throwIndexOverflow();
                                            }
                                            irVarargElement = (IrVarargElement) next3;
                                            it2 = it;
                                            if (!(irVarargElement instanceof IrExpression)) {
                                                throw new AssertionError("Unsupported: spread vararg element with SAM conversion");
                                            }
                                            if (arrayList != null) {
                                                unwrappedType = (UnwrappedType) arrayList.get(i);
                                            } else {
                                                unwrappedType = null;
                                            }
                                            if (unwrappedType != null) {
                                                irExpressionGenerateSamConversionForValueArgumentsIfRequired$samConvertScalarExpression = generateSamConversionForValueArgumentsIfRequired$samConvertScalarExpression(irType, statementGenerator, kotlinTypeSubstitute, (IrExpression) irVarargElement);
                                            } else {
                                                irExpressionGenerateSamConversionForValueArgumentsIfRequired$samConvertScalarExpression = (IrExpression) irVarargElement;
                                            }
                                            elements.add(irExpressionGenerateSamConversionForValueArgumentsIfRequired$samConvertScalarExpression);
                                            i = i2;
                                            it = it2;
                                        }
                                        Unit unit3 = Unit.INSTANCE;
                                        irVarargImplGenerateSamConversionForValueArgumentsIfRequired$samConvertScalarExpression = irVarargImpl3;
                                    }
                                    irValueArgumentsByIndex3[i4] = irVarargImplGenerateSamConversionForValueArgumentsIfRequired$samConvertScalarExpression;
                                }
                            }
                            i4++;
                            resolvedCall2 = resolvedCall;
                            samConversion = samConversion;
                            i3 = 10;
                        }
                    } else {
                        type2 = valueParameters.get(i4).getType();
                        Intrinsics.checkNotNullExpressionValue(type2, "getType(...)");
                        if (FunctionTypesKt.isFunctionType(type2)) {
                            samTypeForValueParameter = getSamTypeForValueParameter(statementGenerator, valueParameterDescriptor2);
                            if (samTypeForValueParameter == null) {
                                samTypeForValueParameter = valueParameterDescriptor2.getType();
                                Intrinsics.checkNotNullExpressionValue(samTypeForValueParameter, "getType(...)");
                            }
                            irExpression = call.getIrValueArgumentsByIndex()[i4];
                            if (irExpression != null) {
                                kotlinTypeSubstitute = typeSubstitutorCreate.substitute(samTypeForValueParameter, Variance.INVARIANT);
                                if (kotlinTypeSubstitute == null) {
                                    throw new AssertionError("Failed to substitute value argument type in SAM conversion: underlyingParameterType=" + valueParameterDescriptor2.getType() + ", substitutionContext=" + linkedHashMap);
                                }
                                irType = statementGenerator.toIrType(kotlinTypeSubstitute);
                                IrExpression[] irValueArgumentsByIndex4 = call.getIrValueArgumentsByIndex();
                                if (!(irExpression instanceof IrVararg)) {
                                    irVarargImplGenerateSamConversionForValueArgumentsIfRequired$samConvertScalarExpression = generateSamConversionForValueArgumentsIfRequired$samConvertScalarExpression(irType, statementGenerator, kotlinTypeSubstitute, irExpression);
                                } else {
                                    if (valueParameterDescriptor2.getVarargElementType() == null) {
                                        throw new AssertionError("Vararg parameter expected for vararg argument: " + valueParameterDescriptor2);
                                    }
                                    kotlinTypeSubstitute2 = typeSubstitutorCreate.substitute(valueParameterDescriptor2.getType(), Variance.INVARIANT);
                                    if (kotlinTypeSubstitute2 == null) {
                                        throw new AssertionError("Failed to substitute vararg type in SAM conversion: type=" + valueParameterDescriptor2.getType() + ", substitutionContext=" + linkedHashMap);
                                    }
                                    IrVarargImpl irVarargImpl4 = new IrVarargImpl(irExpression.getStartOffset(), irExpression.getEndOffset(), computeVarargType(statementGenerator, kotlinTypeSubstitute2), irType);
                                    List<IrVarargElement> elements5 = ((IrVararg) irExpression).getElements();
                                    elements = irVarargImpl4.getElements();
                                    it = elements5.iterator();
                                    i = 0;
                                    while (it.hasNext()) {
                                        Object next4 = it.next();
                                        i2 = i + 1;
                                        if (i < 0) {
                                            CollectionsKt.throwIndexOverflow();
                                        }
                                        irVarargElement = (IrVarargElement) next4;
                                        it2 = it;
                                        if (!(irVarargElement instanceof IrExpression)) {
                                            throw new AssertionError("Unsupported: spread vararg element with SAM conversion");
                                        }
                                        if (arrayList != null) {
                                            unwrappedType = (UnwrappedType) arrayList.get(i);
                                        } else {
                                            unwrappedType = null;
                                        }
                                        if (unwrappedType != null) {
                                            irExpressionGenerateSamConversionForValueArgumentsIfRequired$samConvertScalarExpression = generateSamConversionForValueArgumentsIfRequired$samConvertScalarExpression(irType, statementGenerator, kotlinTypeSubstitute, (IrExpression) irVarargElement);
                                        } else {
                                            irExpressionGenerateSamConversionForValueArgumentsIfRequired$samConvertScalarExpression = (IrExpression) irVarargElement;
                                        }
                                        elements.add(irExpressionGenerateSamConversionForValueArgumentsIfRequired$samConvertScalarExpression);
                                        i = i2;
                                        it = it2;
                                    }
                                    Unit unit4 = Unit.INSTANCE;
                                    irVarargImplGenerateSamConversionForValueArgumentsIfRequired$samConvertScalarExpression = irVarargImpl4;
                                }
                                irValueArgumentsByIndex4[i4] = irVarargImplGenerateSamConversionForValueArgumentsIfRequired$samConvertScalarExpression;
                            }
                        }
                        i4++;
                        resolvedCall2 = resolvedCall;
                        samConversion = samConversion;
                        i3 = 10;
                    }
                } else {
                    Iterator it4 = arrayList3.iterator();
                    while (true) {
                        if (!it4.hasNext()) {
                            type = valueParameterDescriptor2.getType();
                            Intrinsics.checkNotNullExpressionValue(type, "getType(...)");
                            if (!SamConversionsKt.isSamType(samConversion, type)) {
                                type3 = valueParameterDescriptor2.getOriginal().getType();
                                Intrinsics.checkNotNullExpressionValue(type3, "getType(...)");
                                if (SamConversionsKt.isSamType(samConversion, type3)) {
                                    type2 = valueParameters.get(i4).getType();
                                    Intrinsics.checkNotNullExpressionValue(type2, "getType(...)");
                                    if (FunctionTypesKt.isFunctionType(type2)) {
                                    }
                                    i4++;
                                    resolvedCall2 = resolvedCall;
                                    samConversion = samConversion;
                                    i3 = 10;
                                }
                            } else {
                                type2 = valueParameters.get(i4).getType();
                                Intrinsics.checkNotNullExpressionValue(type2, "getType(...)");
                                if (FunctionTypesKt.isFunctionType(type2)) {
                                }
                                i4++;
                                resolvedCall2 = resolvedCall;
                                samConversion = samConversion;
                                i3 = 10;
                            }
                        } else if (((UnwrappedType) it4.next()) == null) {
                        }
                        samTypeForValueParameter = getSamTypeForValueParameter(statementGenerator, valueParameterDescriptor2);
                        if (samTypeForValueParameter == null && (samTypeForValueParameter = valueParameterDescriptor2.getVarargElementType()) == null) {
                            samTypeForValueParameter = valueParameterDescriptor2.getType();
                            Intrinsics.checkNotNullExpressionValue(samTypeForValueParameter, "getType(...)");
                        }
                        irExpression = call.getIrValueArgumentsByIndex()[i4];
                        if (irExpression != null) {
                            kotlinTypeSubstitute = typeSubstitutorCreate.substitute(samTypeForValueParameter, Variance.INVARIANT);
                            if (kotlinTypeSubstitute == null) {
                                throw new AssertionError("Failed to substitute value argument type in SAM conversion: underlyingParameterType=" + valueParameterDescriptor2.getType() + ", substitutionContext=" + linkedHashMap);
                            }
                            irType = statementGenerator.toIrType(kotlinTypeSubstitute);
                            IrExpression[] irValueArgumentsByIndex5 = call.getIrValueArgumentsByIndex();
                            if (!(irExpression instanceof IrVararg)) {
                                irVarargImplGenerateSamConversionForValueArgumentsIfRequired$samConvertScalarExpression = generateSamConversionForValueArgumentsIfRequired$samConvertScalarExpression(irType, statementGenerator, kotlinTypeSubstitute, irExpression);
                            } else {
                                if (valueParameterDescriptor2.getVarargElementType() == null) {
                                    throw new AssertionError("Vararg parameter expected for vararg argument: " + valueParameterDescriptor2);
                                }
                                kotlinTypeSubstitute2 = typeSubstitutorCreate.substitute(valueParameterDescriptor2.getType(), Variance.INVARIANT);
                                if (kotlinTypeSubstitute2 == null) {
                                    throw new AssertionError("Failed to substitute vararg type in SAM conversion: type=" + valueParameterDescriptor2.getType() + ", substitutionContext=" + linkedHashMap);
                                }
                                IrVarargImpl irVarargImpl5 = new IrVarargImpl(irExpression.getStartOffset(), irExpression.getEndOffset(), computeVarargType(statementGenerator, kotlinTypeSubstitute2), irType);
                                List<IrVarargElement> elements6 = ((IrVararg) irExpression).getElements();
                                elements = irVarargImpl5.getElements();
                                it = elements6.iterator();
                                i = 0;
                                while (it.hasNext()) {
                                    Object next5 = it.next();
                                    i2 = i + 1;
                                    if (i < 0) {
                                        CollectionsKt.throwIndexOverflow();
                                    }
                                    irVarargElement = (IrVarargElement) next5;
                                    it2 = it;
                                    if (!(irVarargElement instanceof IrExpression)) {
                                        throw new AssertionError("Unsupported: spread vararg element with SAM conversion");
                                    }
                                    if (arrayList != null) {
                                        unwrappedType = (UnwrappedType) arrayList.get(i);
                                    } else {
                                        unwrappedType = null;
                                    }
                                    if (unwrappedType != null) {
                                        irExpressionGenerateSamConversionForValueArgumentsIfRequired$samConvertScalarExpression = generateSamConversionForValueArgumentsIfRequired$samConvertScalarExpression(irType, statementGenerator, kotlinTypeSubstitute, (IrExpression) irVarargElement);
                                    } else {
                                        irExpressionGenerateSamConversionForValueArgumentsIfRequired$samConvertScalarExpression = (IrExpression) irVarargElement;
                                    }
                                    elements.add(irExpressionGenerateSamConversionForValueArgumentsIfRequired$samConvertScalarExpression);
                                    i = i2;
                                    it = it2;
                                }
                                Unit unit5 = Unit.INSTANCE;
                                irVarargImplGenerateSamConversionForValueArgumentsIfRequired$samConvertScalarExpression = irVarargImpl5;
                            }
                            irValueArgumentsByIndex5[i4] = irVarargImplGenerateSamConversionForValueArgumentsIfRequired$samConvertScalarExpression;
                        }
                        i4++;
                        resolvedCall2 = resolvedCall;
                        samConversion = samConversion;
                        i3 = 10;
                    }
                }
            }
            i4++;
            resolvedCall2 = resolvedCall;
            samConversion = samConversion;
            i3 = 10;
        }
    }

    private static final boolean generateSamConversionForValueArgumentsIfRequired$isFunctionReferenceAdapter(IrExpression irExpression) {
        if (irExpression instanceof IrBlock) {
            IrBlock irBlock = (IrBlock) irExpression;
            if (Intrinsics.areEqual(irBlock.getOrigin(), IrStatementOrigin.SUSPEND_CONVERSION.INSTANCE) || Intrinsics.areEqual(irBlock.getOrigin(), IrStatementOrigin.ADAPTED_FUNCTION_REFERENCE.INSTANCE)) {
                return true;
            }
        }
        return false;
    }

    private static final IrTypeOperatorCallImpl generateSamConversionForValueArgumentsIfRequired$applySamConversion(IrExpression irExpression, IrType irType, StatementGenerator statementGenerator, KotlinType kotlinType) {
        return new IrTypeOperatorCallImpl(irExpression.getStartOffset(), irExpression.getEndOffset(), irType, IrTypeOperator.SAM_CONVERSION, irType, castArgumentToFunctionalInterfaceForSamType(statementGenerator, irExpression, kotlinType));
    }

    private static final IrExpression generateSamConversionForValueArgumentsIfRequired$samConvertScalarExpression(IrType irType, StatementGenerator statementGenerator, KotlinType kotlinType, IrExpression irExpression) {
        if (generateSamConversionForValueArgumentsIfRequired$isFunctionReferenceAdapter(irExpression)) {
            Intrinsics.checkNotNull(irExpression, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.expressions.IrBlock");
            IrBlock irBlock = (IrBlock) irExpression;
            irBlock.setType(irType);
            Object objLast = CollectionsKt.last((List<? extends Object>) irBlock.getStatements());
            Intrinsics.checkNotNull(objLast, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.expressions.IrFunctionReference");
            irBlock.getStatements().set(CollectionsKt.getLastIndex(irBlock.getStatements()), generateSamConversionForValueArgumentsIfRequired$applySamConversion((IrFunctionReference) objLast, irType, statementGenerator, kotlinType));
            return irBlock;
        }
        return generateSamConversionForValueArgumentsIfRequired$applySamConversion(irExpression, irType, statementGenerator, kotlinType);
    }

    private static final KotlinType getSamTypeForValueParameter(StatementGenerator statementGenerator, ValueParameterDescriptor valueParameterDescriptor) {
        KotlinType samTypeForValueParameter = statementGenerator.getContext().getSamTypeApproximator().getSamTypeForValueParameter(valueParameterDescriptor, statementGenerator.getContext().getExtensions().getSamConversion().isCarefulApproximationOfContravariantProjection());
        if (samTypeForValueParameter == null || !SamConversionsKt.isSamType(statementGenerator.getContext().getExtensions().getSamConversion(), samTypeForValueParameter)) {
            return null;
        }
        ClassifierDescriptor this$0 = samTypeForValueParameter.getConstructor().getThis$0();
        if (this$0 == null) {
            throw new AssertionError("SAM type is expected to be a class type: " + samTypeForValueParameter);
        }
        List<TypeProjection> arguments = samTypeForValueParameter.getArguments();
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(arguments, 10));
        int i = 0;
        for (Object obj : arguments) {
            int i2 = i + 1;
            if (i < 0) {
                CollectionsKt.throwIndexOverflow();
            }
            StarProjectionImpl starProjectionImpl = (TypeProjection) obj;
            if (!starProjectionImpl.getNullableAnyType().getConstructor().isDenotable()) {
                TypeParameterDescriptor typeParameterDescriptor = this$0.getTypeConstructor().getParameters().get(i);
                Intrinsics.checkNotNullExpressionValue(typeParameterDescriptor, "get(...)");
                starProjectionImpl = new StarProjectionImpl(typeParameterDescriptor);
            }
            arrayList.add(starProjectionImpl);
            i = i2;
        }
        return TypeSubstitutionKt.replace$default(samTypeForValueParameter, arrayList, null, null, 6, null);
    }

    public static final void pregenerateValueArgumentsUsing(StatementGenerator statementGenerator, CallBuilder call, ResolvedCall<?> resolvedCall, Function1<? super KtExpression, ? extends IrExpression> generateArgumentExpression) {
        Intrinsics.checkNotNullParameter(statementGenerator, "<this>");
        Intrinsics.checkNotNullParameter(call, "call");
        Intrinsics.checkNotNullParameter(resolvedCall, "resolvedCall");
        Intrinsics.checkNotNullParameter(generateArgumentExpression, "generateArgumentExpression");
        List<ResolvedValueArgument> valueArgumentsByIndex = resolvedCall.getValueArgumentsByIndex();
        Intrinsics.checkNotNull(valueArgumentsByIndex);
        int i = 0;
        for (Object obj : valueArgumentsByIndex) {
            int i2 = i + 1;
            if (i < 0) {
                CollectionsKt.throwIndexOverflow();
            }
            ResolvedValueArgument resolvedValueArgument = (ResolvedValueArgument) obj;
            ValueParameterDescriptor valueParameterDescriptor = call.getDescriptor().getValueParameters().get(i);
            IrExpression[] irValueArgumentsByIndex = call.getIrValueArgumentsByIndex();
            Intrinsics.checkNotNull(resolvedValueArgument);
            Intrinsics.checkNotNull(valueParameterDescriptor);
            irValueArgumentsByIndex[i] = generateValueArgumentUsing(statementGenerator, resolvedValueArgument, valueParameterDescriptor, resolvedCall, generateArgumentExpression);
            i = i2;
        }
    }

    public static final CallBuilder pregenerateCallReceivers(StatementGenerator statementGenerator, ResolvedCall<?> resolvedCall) {
        Intrinsics.checkNotNullParameter(statementGenerator, "<this>");
        Intrinsics.checkNotNullParameter(resolvedCall, "resolvedCall");
        CallBuilder callBuilderUnwrapCallableDescriptorAndTypeArguments = unwrapCallableDescriptorAndTypeArguments(resolvedCall);
        KtElement callElement = resolvedCall.getCall().getCallElement();
        Intrinsics.checkNotNullExpressionValue(callElement, "getCallElement(...)");
        CallableDescriptor resultingDescriptor = resolvedCall.getResultingDescriptor();
        Intrinsics.checkNotNullExpressionValue(resultingDescriptor, "getResultingDescriptor(...)");
        ReceiverValue receiverValueMo7304getDispatchReceiver = resolvedCall.getDispatchReceiver();
        ReceiverValue receiverValueMo7305getExtensionReceiver = resolvedCall.getExtensionReceiver();
        List<ReceiverValue> contextReceivers = resolvedCall.getContextReceivers();
        Intrinsics.checkNotNullExpressionValue(contextReceivers, "getContextReceivers(...)");
        Call call = resolvedCall.getCall();
        Intrinsics.checkNotNullExpressionValue(call, "getCall(...)");
        callBuilderUnwrapCallableDescriptorAndTypeArguments.setCallReceiver(generateCallReceiver$default(statementGenerator, callElement, resultingDescriptor, receiverValueMo7304getDispatchReceiver, receiverValueMo7305getExtensionReceiver, contextReceivers, CallUtilKt.isSafeCall(call), false, 64, null));
        callBuilderUnwrapCallableDescriptorAndTypeArguments.setSuperQualifier(getSuperQualifier(statementGenerator, resolvedCall));
        return callBuilderUnwrapCallableDescriptorAndTypeArguments;
    }

    private static final CallableDescriptor unwrapSpecialDescriptor(CallableDescriptor callableDescriptor) {
        CallableDescriptor callableDescriptorUnwrapSpecialDescriptor;
        if (callableDescriptor instanceof ImportedFromObjectCallableDescriptor) {
            return unwrapSpecialDescriptor(((ImportedFromObjectCallableDescriptor) callableDescriptor).getCallableFromObject());
        }
        if (callableDescriptor instanceof TypeAliasConstructorDescriptor) {
            return ((TypeAliasConstructorDescriptor) callableDescriptor).getUnderlyingConstructorDescriptor();
        }
        FunctionDescriptor originalForFunctionInterfaceAdapter = SamConversionsKt.getOriginalForFunctionInterfaceAdapter(callableDescriptor);
        return (originalForFunctionInterfaceAdapter == null || (callableDescriptorUnwrapSpecialDescriptor = unwrapSpecialDescriptor(originalForFunctionInterfaceAdapter)) == null) ? callableDescriptor : callableDescriptorUnwrapSpecialDescriptor;
    }

    /* JADX WARN: Code duplicated, block: B:20:0x00ba  */
    public static final CallBuilder unwrapCallableDescriptorAndTypeArguments(ResolvedCall<?> resolvedCall) {
        Intrinsics.checkNotNullParameter(resolvedCall, "resolvedCall");
        CallableDescriptor resultingDescriptor = resolvedCall.getResultingDescriptor();
        CallableDescriptor callableDescriptor = resolvedCall.get$suspendPropertyDescriptor();
        Intrinsics.checkNotNull(resultingDescriptor);
        CallableDescriptor callableDescriptorUnwrapSpecialDescriptor = unwrapSpecialDescriptor(resultingDescriptor);
        Map<TypeParameterDescriptor, KotlinType> typeArguments = resolvedCall.getTypeArguments();
        Intrinsics.checkNotNullExpressionValue(typeArguments, "getTypeArguments(...)");
        CallableDescriptor original = callableDescriptorUnwrapSpecialDescriptor.getOriginal();
        Intrinsics.checkNotNullExpressionValue(original, "getOriginal(...)");
        List<TypeParameterDescriptor> typeParameters = original.getTypeParameters();
        Intrinsics.checkNotNullExpressionValue(typeParameters, "getTypeParameters(...)");
        if (resultingDescriptor instanceof ImportedFromObjectCallableDescriptor) {
            ((ImportedFromObjectCallableDescriptor) resultingDescriptor).getTypeParameters().size();
            typeParameters.size();
            if (typeParameters.isEmpty()) {
                typeArguments = null;
            } else {
                List<TypeParameterDescriptor> list = typeParameters;
                LinkedHashMap linkedHashMap = new LinkedHashMap(RangesKt.coerceAtLeast(MapsKt.mapCapacity(CollectionsKt.collectionSizeOrDefault(list, 10)), 16));
                for (Object obj : list) {
                    LinkedHashMap linkedHashMap2 = linkedHashMap;
                    TypeParameterDescriptor typeParameterDescriptor = callableDescriptor.getTypeParameters().get(((TypeParameterDescriptor) obj).getIndex());
                    KotlinType kotlinType = typeArguments.get(typeParameterDescriptor);
                    if (kotlinType == null) {
                        throw new AssertionError("No type argument for " + typeParameterDescriptor);
                    }
                    linkedHashMap2.put(obj, kotlinType);
                }
                typeArguments = linkedHashMap;
            }
        } else if (resultingDescriptor instanceof TypeAliasConstructorDescriptor) {
            KotlinType returnType = ((TypeAliasConstructorDescriptor) resultingDescriptor).getReturnType();
            if (returnType.getArguments().isEmpty()) {
                typeArguments = null;
            } else {
                List<TypeParameterDescriptor> list2 = typeParameters;
                LinkedHashMap linkedHashMap3 = new LinkedHashMap(RangesKt.coerceAtLeast(MapsKt.mapCapacity(CollectionsKt.collectionSizeOrDefault(list2, 10)), 16));
                for (Object obj2 : list2) {
                    linkedHashMap3.put(obj2, returnType.getArguments().get(((TypeParameterDescriptor) obj2).getIndex()).getNullableAnyType());
                }
                typeArguments = linkedHashMap3;
            }
        } else {
            Set<TypeParameterDescriptor> setKeySet = typeArguments.keySet();
            if ((setKeySet instanceof Collection) && setKeySet.isEmpty()) {
                if (typeArguments.isEmpty()) {
                    typeArguments = null;
                }
            } else {
                Iterator it = setKeySet.iterator();
                while (true) {
                    if (it.hasNext()) {
                        if (!Intrinsics.areEqual(((TypeParameterDescriptor) it.next()).getContainingModule(), original)) {
                            typeParameters.size();
                            typeArguments.size();
                            if (typeParameters.isEmpty()) {
                                typeArguments = null;
                            } else {
                                Set<TypeParameterDescriptor> setKeySet2 = typeArguments.keySet();
                                LinkedHashMap linkedHashMap4 = new LinkedHashMap(RangesKt.coerceAtLeast(MapsKt.mapCapacity(CollectionsKt.collectionSizeOrDefault(setKeySet2, 10)), 16));
                                for (TypeParameterDescriptor typeParameterDescriptor2 : setKeySet2) {
                                    TypeParameterDescriptor typeParameterDescriptor3 = typeParameters.get(typeParameterDescriptor2.getIndex());
                                    KotlinType kotlinType2 = typeArguments.get(typeParameterDescriptor2);
                                    if (kotlinType2 == null) {
                                        throw new AssertionError("No type argument for " + typeParameterDescriptor3 + " <= " + typeParameterDescriptor2);
                                    }
                                    Pair pair = TuplesKt.to(typeParameterDescriptor3, kotlinType2);
                                    linkedHashMap4.put(pair.getFirst(), pair.getSecond());
                                }
                                typeArguments = linkedHashMap4;
                            }
                        }
                    } else if (typeArguments.isEmpty()) {
                        typeArguments = null;
                    }
                }
            }
        }
        if (typeArguments != null) {
            List<TypeParameterDescriptor> typeParameters2 = original.getTypeParameters();
            Intrinsics.checkNotNullExpressionValue(typeParameters2, "getTypeParameters(...)");
            List<TypeParameterDescriptor> list3 = typeParameters2;
            LinkedHashMap linkedHashMap5 = new LinkedHashMap(RangesKt.coerceAtLeast(MapsKt.mapCapacity(CollectionsKt.collectionSizeOrDefault(list3, 10)), 16));
            for (TypeParameterDescriptor typeParameterDescriptor4 : list3) {
                KotlinType kotlinType3 = typeArguments.get(typeParameterDescriptor4);
                if (kotlinType3 == null) {
                    throw new AssertionError("No type argument for " + typeParameterDescriptor4 + " in " + typeArguments);
                }
                Pair pair2 = TuplesKt.to(typeParameterDescriptor4.getTypeConstructor(), new TypeProjectionImpl(kotlinType3));
                linkedHashMap5.put(pair2.getFirst(), pair2.getSecond());
            }
            TypeSubstitutor typeSubstitutorCreate = TypeSubstitutor.create(linkedHashMap5);
            Intrinsics.checkNotNullExpressionValue(typeSubstitutorCreate, "create(...)");
            callableDescriptorUnwrapSpecialDescriptor = callableDescriptorUnwrapSpecialDescriptor.substitute(typeSubstitutorCreate);
        }
        Intrinsics.checkNotNull(callableDescriptorUnwrapSpecialDescriptor);
        return new CallBuilder(resolvedCall, callableDescriptorUnwrapSpecialDescriptor, typeArguments, false, 8, null);
    }

    public static final void putTypeArguments(IrMemberAccessExpression<?> irMemberAccessExpression, Map<TypeParameterDescriptor, ? extends KotlinType> map, Function1<? super KotlinType, ? extends IrType> toIrType) {
        Intrinsics.checkNotNullParameter(irMemberAccessExpression, "<this>");
        Intrinsics.checkNotNullParameter(toIrType, "toIrType");
        if (map == null) {
            return;
        }
        for (Map.Entry<TypeParameterDescriptor, ? extends KotlinType> entry : map.entrySet()) {
            irMemberAccessExpression.putTypeArgument(entry.getKey().getIndex(), toIrType.invoke(entry.getValue()));
        }
    }
}
