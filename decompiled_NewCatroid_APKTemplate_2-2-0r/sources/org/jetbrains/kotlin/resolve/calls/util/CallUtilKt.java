package org.jetbrains.kotlin.resolve.calls.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.SequencesKt;
import org.apache.xerces.impl.xs.SchemaSymbols;
import org.jetbrains.kotlin.com.intellij.navigation.NavigationItem;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiFile;
import org.jetbrains.kotlin.com.intellij.psi.PsiWhiteSpace;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiTreeUtil;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.descriptors.PropertyDescriptor;
import org.jetbrains.kotlin.descriptors.TypeParameterDescriptor;
import org.jetbrains.kotlin.descriptors.ValueParameterDescriptor;
import org.jetbrains.kotlin.descriptors.VariableDescriptor;
import org.jetbrains.kotlin.diagnostics.Diagnostic;
import org.jetbrains.kotlin.diagnostics.Errors;
import org.jetbrains.kotlin.incremental.KotlinLookupLocation;
import org.jetbrains.kotlin.psi.Call;
import org.jetbrains.kotlin.psi.KtArrayAccessExpression;
import org.jetbrains.kotlin.psi.KtBinaryExpression;
import org.jetbrains.kotlin.psi.KtCallElement;
import org.jetbrains.kotlin.psi.KtCallableReferenceExpression;
import org.jetbrains.kotlin.psi.KtConstructorCalleeExpression;
import org.jetbrains.kotlin.psi.KtElement;
import org.jetbrains.kotlin.psi.KtExpression;
import org.jetbrains.kotlin.psi.KtFile;
import org.jetbrains.kotlin.psi.KtFunctionLiteral;
import org.jetbrains.kotlin.psi.KtInstanceExpressionWithLabel;
import org.jetbrains.kotlin.psi.KtLambdaArgument;
import org.jetbrains.kotlin.psi.KtLambdaExpression;
import org.jetbrains.kotlin.psi.KtNameReferenceExpression;
import org.jetbrains.kotlin.psi.KtProperty;
import org.jetbrains.kotlin.psi.KtPsiFactoryKt;
import org.jetbrains.kotlin.psi.KtPsiUtil;
import org.jetbrains.kotlin.psi.KtSimpleNameExpression;
import org.jetbrains.kotlin.psi.KtUnaryExpression;
import org.jetbrains.kotlin.psi.KtUserType;
import org.jetbrains.kotlin.psi.KtWhenExpression;
import org.jetbrains.kotlin.psi.ValueArgument;
import org.jetbrains.kotlin.resolve.BindingContext;
import org.jetbrains.kotlin.resolve.BindingTrace;
import org.jetbrains.kotlin.resolve.StatementFilter;
import org.jetbrains.kotlin.resolve.calls.ArgumentTypeResolver;
import org.jetbrains.kotlin.resolve.calls.CallTransformer;
import org.jetbrains.kotlin.resolve.calls.context.ResolutionContext;
import org.jetbrains.kotlin.resolve.calls.inference.components.NewTypeSubstitutor;
import org.jetbrains.kotlin.resolve.calls.model.ArgumentMapping;
import org.jetbrains.kotlin.resolve.calls.model.ArgumentMatch;
import org.jetbrains.kotlin.resolve.calls.model.ArgumentMatchStatus;
import org.jetbrains.kotlin.resolve.calls.model.ArgumentUnmapped;
import org.jetbrains.kotlin.resolve.calls.model.DefaultValueArgument;
import org.jetbrains.kotlin.resolve.calls.model.KotlinCall;
import org.jetbrains.kotlin.resolve.calls.model.MutableResolvedCall;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedCall;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedCallImpl;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedValueArgument;
import org.jetbrains.kotlin.resolve.calls.model.VariableAsFunctionResolvedCall;
import org.jetbrains.kotlin.resolve.calls.tower.NewResolvedCallImpl;
import org.jetbrains.kotlin.resolve.calls.tower.PSIKotlinCallsKt;
import org.jetbrains.kotlin.resolve.scopes.receivers.ExpressionReceiver;
import org.jetbrains.kotlin.resolve.scopes.receivers.ReceiverValue;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.KotlinTypeKt;
import org.jetbrains.kotlin.types.StubTypeForBuilderInference;
import org.jetbrains.kotlin.types.TypeProjection;
import org.jetbrains.kotlin.types.TypeSubstitution;
import org.jetbrains.kotlin.types.UnwrappedType;
import org.jetbrains.kotlin.types.typeUtil.TypeUtilsKt;

/* JADX INFO: compiled from: callUtil.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000¶\u0001\n\u0000\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a\u001a\u0010\n\u001a\u00020\u0001\"\b\b\u0000\u0010\u000b*\u00020\f*\b\u0012\u0004\u0012\u0002H\u000b0\r\u001a\f\u0010\u000e\u001a\u0004\u0018\u00010\u000f*\u00020\u0005\u001a\n\u0010\u0010\u001a\u00020\u0011*\u00020\u0012\u001a\f\u0010\u0010\u001a\u0004\u0018\u00010\u0011*\u00020\u0013\u001a\f\u0010\u0014\u001a\u0004\u0018\u00010\u000f*\u00020\u0012\u001a\f\u0010\u0014\u001a\u0004\u0018\u00010\u000f*\u00020\u0015\u001a\u001a\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00180\u0017*\n\u0012\u0006\u0012\u0004\u0018\u00010\u00180\u0017H\u0002\u001a\u0014\u0010\u0019\u001a\u0004\u0018\u00010\u0012*\u00020\u00022\u0006\u0010\u001a\u001a\u00020\u001b\u001a\u0012\u0010\u001c\u001a\u00020\u0012*\u00020\u00022\u0006\u0010\u001a\u001a\u00020\u001b\u001a\u0010\u0010\u001d\u001a\u0004\u0018\u00010\u0013*\u0006\u0012\u0002\b\u00030\r\u001a\u001a\u0010\u001e\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u001f0\r*\u00020\u00132\u0006\u0010\u001a\u001a\u00020\u001b\u001a&\u0010 \u001a\u0004\u0018\u00010!\"\b\b\u0000\u0010\u000b*\u00020\f*\b\u0012\u0004\u0012\u0002H\u000b0\r2\b\u0010\"\u001a\u0004\u0018\u00010\u0018\u001a\u001e\u0010#\u001a\u0004\u0018\u00010\u0012*\u00020\u00022\u0006\u0010\u001a\u001a\u00020\u001b2\b\b\u0002\u0010$\u001a\u00020\u0001\u001a(\u0010%\u001a\f\u0012\u0006\b\u0001\u0012\u00020\f\u0018\u00010\r*\u0004\u0018\u00010\u00022\u0006\u0010\u001a\u001a\u00020\u001b2\b\b\u0002\u0010$\u001a\u00020\u0001\u001a\u001a\u0010&\u001a\n\u0012\u0006\b\u0001\u0012\u00020'0\r*\u00020\u00132\u0006\u0010\u001a\u001a\u00020\u001b\u001a\u0010\u0010(\u001a\u0004\u0018\u00010\u0013*\u0006\u0012\u0002\b\u00030\r\u001a\u001e\u0010)\u001a\f\u0012\u0006\b\u0001\u0012\u00020\f\u0018\u00010\r*\u0004\u0018\u00010\u00122\u0006\u0010\u001a\u001a\u00020\u001b\u001a\u001e\u0010)\u001a\f\u0012\u0006\b\u0001\u0012\u00020\f\u0018\u00010\r*\u0004\u0018\u00010\u00022\u0006\u0010\u001a\u001a\u00020\u001b\u001a\u001a\u0010*\u001a\n\u0012\u0006\b\u0001\u0012\u00020\f0\r*\u00020\u00122\u0006\u0010\u001a\u001a\u00020\u001b\u001a\u001a\u0010*\u001a\n\u0012\u0006\b\u0001\u0012\u00020\f0\r*\u00020\u00022\u0006\u0010\u001a\u001a\u00020\u001b\u001a\u0014\u0010+\u001a\u0004\u0018\u00010,*\u00020\u00132\u0006\u0010\u001a\u001a\u00020\u001b\u001a\u0014\u0010-\u001a\u0004\u0018\u00010\u0018*\u00020\u00122\u0006\u0010.\u001a\u00020\u0013\u001a\n\u0010/\u001a\u00020\u0002*\u00020\u0012\u001a\u0010\u00100\u001a\b\u0012\u0004\u0012\u00020\u00180\u0017*\u00020\u0012\u001a\u0010\u00100\u001a\b\u0012\u0004\u0012\u00020\u00180\u0017*\u000201\u001a\u001a\u00102\u001a\n\u0012\u0006\b\u0001\u0012\u0002030\r*\u00020\u00132\u0006\u0010\u001a\u001a\u00020\u001b\u001a\"\u00104\u001a\u00020\u0001\"\b\b\u0000\u0010\u000b*\u00020\f*\b\u0012\u0004\u0012\u0002H\u000b0\r2\u0006\u00105\u001a\u00020!\u001a\u001a\u00106\u001a\u00020\u0001\"\b\b\u0000\u0010\u000b*\u00020\f*\b\u0012\u0004\u0012\u0002H\u000b0\r\u001a\u001a\u00107\u001a\u00020\u0001\"\b\b\u0000\u0010\u000b*\u00020\f*\b\u0012\u0004\u0012\u0002H\u000b0\r\u001a\u001a\u00108\u001a\u00020\u0001*\u00020\u00122\u0006\u00109\u001a\u00020\u001b2\u0006\u0010:\u001a\u00020;\u001a(\u00108\u001a\u00020\u0001\"\u000e\b\u0000\u0010<*\b\u0012\u0004\u0012\u0002H<0=*\u00020\u00122\f\u0010\u001a\u001a\b\u0012\u0004\u0012\u0002H<0=\u001a\n\u0010>\u001a\u00020\u0001*\u00020\u0005\u001a\n\u0010>\u001a\u00020\u0001*\u00020\u0012\u001a\n\u0010?\u001a\u00020\u0001*\u00020\u0012\u001a\u001a\u0010@\u001a\u00020\u0001\"\b\b\u0000\u0010\u000b*\u00020\f*\b\u0012\u0004\u0012\u0002H\u000b0\r\u001a.\u0010A\u001a\u00020B*\u00020C2\b\u0010.\u001a\u0004\u0018\u00010\u00132\u0012\u0010D\u001a\u000e\u0012\u0004\u0012\u00020\u0013\u0012\u0004\u0012\u00020F0EH\u0086\bø\u0001\u0000\u001a\u001a\u0010G\u001a\u00020\u0001\"\b\b\u0000\u0010\u000b*\u00020\f*\b\u0012\u0004\u0012\u0002H\u000b0H\u001a\n\u0010I\u001a\u00020J*\u00020K\u001a\u001a\u0010L\u001a\u00020\u0001\"\b\b\u0000\u0010\u000b*\u00020\f*\b\u0012\u0004\u0012\u0002H\u000b0\r\"\u0015\u0010\u0000\u001a\u00020\u0001*\u00020\u00028F¢\u0006\u0006\u001a\u0004\b\u0000\u0010\u0003\"\u0015\u0010\u0004\u001a\u00020\u0001*\u00020\u00058F¢\u0006\u0006\u001a\u0004\b\u0004\u0010\u0006\"\u0015\u0010\u0007\u001a\u00020\u0001*\u00020\b8F¢\u0006\u0006\u001a\u0004\b\u0007\u0010\t\u0082\u0002\u0007\n\u0005\b\u009920\u0001¨\u0006M"}, d2 = {"isFakeElement", "", "Lorg/jetbrains/kotlin/psi/KtElement;", "(Lorg/jetbrains/kotlin/psi/KtElement;)Z", "isFakePsiElement", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", "(Lcom/intellij/psi/PsiElement;)Z", "isTrailingLambdaOnNewLIne", "Lorg/jetbrains/kotlin/psi/KtLambdaExpression;", "(Lorg/jetbrains/kotlin/psi/KtLambdaExpression;)Z", "allArgumentsMapped", "D", "Lorg/jetbrains/kotlin/descriptors/CallableDescriptor;", "Lorg/jetbrains/kotlin/resolve/calls/model/ResolvedCall;", "asCallableReferenceExpression", "Lorg/jetbrains/kotlin/psi/KtCallableReferenceExpression;", "createLookupLocation", "Lorg/jetbrains/kotlin/incremental/KotlinLookupLocation;", "Lorg/jetbrains/kotlin/psi/Call;", "Lorg/jetbrains/kotlin/psi/KtExpression;", "extractCallableReferenceExpression", "Lorg/jetbrains/kotlin/resolve/calls/model/KotlinCall;", "filterArgsInParentheses", "", "Lorg/jetbrains/kotlin/psi/ValueArgument;", "getCall", "context", "Lorg/jetbrains/kotlin/resolve/BindingContext;", "getCallWithAssert", "getFirstArgumentExpression", "getFunctionResolvedCallWithAssert", "Lorg/jetbrains/kotlin/descriptors/FunctionDescriptor;", "getParameterForArgument", "Lorg/jetbrains/kotlin/descriptors/ValueParameterDescriptor;", "valueArgument", "getParentCall", SchemaSymbols.ATTVAL_STRICT, "getParentResolvedCall", "getPropertyResolvedCallWithAssert", "Lorg/jetbrains/kotlin/descriptors/PropertyDescriptor;", "getReceiverExpression", "getResolvedCall", "getResolvedCallWithAssert", "getType", "Lorg/jetbrains/kotlin/types/KotlinType;", "getValueArgumentForExpression", "expression", "getValueArgumentListOrElement", "getValueArgumentsInParentheses", "Lorg/jetbrains/kotlin/psi/KtCallElement;", "getVariableResolvedCallWithAssert", "Lorg/jetbrains/kotlin/descriptors/VariableDescriptor;", "hasTypeMismatchErrorOnParameter", "parameter", "hasUnmappedArguments", "hasUnmappedParameters", "hasUnresolvedArguments", "bindingContext", "statementFilter", "Lorg/jetbrains/kotlin/resolve/StatementFilter;", "C", "Lorg/jetbrains/kotlin/resolve/calls/context/ResolutionContext;", "isCallableReference", "isSafeCall", "noErrorsInValueArguments", "reportTrailingLambdaErrorOr", "", "Lorg/jetbrains/kotlin/resolve/BindingTrace;", "originalDiagnostic", "Lkotlin/Function1;", "Lorg/jetbrains/kotlin/diagnostics/Diagnostic;", "shouldBeSubstituteWithStubTypes", "Lorg/jetbrains/kotlin/resolve/calls/model/ResolvedCallImpl;", "toOldSubstitution", "Lorg/jetbrains/kotlin/types/TypeSubstitution;", "Lorg/jetbrains/kotlin/resolve/calls/inference/components/NewTypeSubstitutor;", "usesDefaultArguments", "frontend"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class CallUtilKt {
    public static final <D extends CallableDescriptor> boolean noErrorsInValueArguments(ResolvedCall<D> resolvedCall) {
        Intrinsics.checkNotNullParameter(resolvedCall, "<this>");
        List<? extends ValueArgument> valueArguments = resolvedCall.getCall().getValueArguments();
        Intrinsics.checkNotNullExpressionValue(valueArguments, "getValueArguments(...)");
        List<? extends ValueArgument> list = valueArguments;
        if ((list instanceof Collection) && list.isEmpty()) {
            return true;
        }
        for (ValueArgument valueArgument : list) {
            Intrinsics.checkNotNull(valueArgument);
            if (resolvedCall.getArgumentMapping(valueArgument).isError()) {
                return false;
            }
        }
        return true;
    }

    public static final <D extends CallableDescriptor> boolean hasUnmappedArguments(ResolvedCall<D> resolvedCall) {
        Intrinsics.checkNotNullParameter(resolvedCall, "<this>");
        List<? extends ValueArgument> valueArguments = resolvedCall.getCall().getValueArguments();
        Intrinsics.checkNotNullExpressionValue(valueArguments, "getValueArguments(...)");
        List<? extends ValueArgument> list = valueArguments;
        if ((list instanceof Collection) && list.isEmpty()) {
            return false;
        }
        for (ValueArgument valueArgument : list) {
            Intrinsics.checkNotNull(valueArgument);
            if (Intrinsics.areEqual(resolvedCall.getArgumentMapping(valueArgument), ArgumentUnmapped.INSTANCE)) {
                return true;
            }
        }
        return false;
    }

    public static final <D extends CallableDescriptor> boolean hasUnmappedParameters(ResolvedCall<D> resolvedCall) {
        Intrinsics.checkNotNullParameter(resolvedCall, "<this>");
        Map<ValueParameterDescriptor, ResolvedValueArgument> valueArguments = resolvedCall.getValueArguments();
        Intrinsics.checkNotNullExpressionValue(valueArguments, "getValueArguments(...)");
        Set<ValueParameterDescriptor> setKeySet = valueArguments.keySet();
        List<ValueParameterDescriptor> valueParameters = resolvedCall.getResultingDescriptor().getValueParameters();
        Intrinsics.checkNotNullExpressionValue(valueParameters, "getValueParameters(...)");
        return !setKeySet.containsAll(valueParameters);
    }

    public static final <D extends CallableDescriptor> boolean allArgumentsMapped(ResolvedCall<D> resolvedCall) {
        Intrinsics.checkNotNullParameter(resolvedCall, "<this>");
        List<? extends ValueArgument> valueArguments = resolvedCall.getCall().getValueArguments();
        Intrinsics.checkNotNullExpressionValue(valueArguments, "getValueArguments(...)");
        List<? extends ValueArgument> list = valueArguments;
        if ((list instanceof Collection) && list.isEmpty()) {
            return true;
        }
        Iterator<T> it = list.iterator();
        while (it.hasNext()) {
            if (!(resolvedCall.getArgumentMapping((ValueArgument) it.next()) instanceof ArgumentMatch)) {
                return false;
            }
        }
        return true;
    }

    public static final <D extends CallableDescriptor> boolean hasTypeMismatchErrorOnParameter(ResolvedCall<D> resolvedCall, ValueParameterDescriptor parameter) {
        Intrinsics.checkNotNullParameter(resolvedCall, "<this>");
        Intrinsics.checkNotNullParameter(parameter, "parameter");
        ResolvedValueArgument resolvedValueArgument = resolvedCall.getValueArguments().get(parameter);
        if (resolvedValueArgument == null) {
            return true;
        }
        List<ValueArgument> arguments = resolvedValueArgument.getArguments();
        Intrinsics.checkNotNullExpressionValue(arguments, "getArguments(...)");
        List<ValueArgument> list = arguments;
        if (!(list instanceof Collection) || !list.isEmpty()) {
            Iterator<T> it = list.iterator();
            while (it.hasNext()) {
                ArgumentMapping argumentMapping = resolvedCall.getArgumentMapping((ValueArgument) it.next());
                Intrinsics.checkNotNullExpressionValue(argumentMapping, "getArgumentMapping(...)");
                if ((argumentMapping instanceof ArgumentMatch) && ((ArgumentMatch) argumentMapping).getStatus() == ArgumentMatchStatus.TYPE_MISMATCH) {
                    return true;
                }
            }
        }
        return false;
    }

    public static final <D extends CallableDescriptor> ValueParameterDescriptor getParameterForArgument(ResolvedCall<D> resolvedCall, ValueArgument valueArgument) {
        Intrinsics.checkNotNullParameter(resolvedCall, "<this>");
        ArgumentMapping argumentMapping = valueArgument != null ? resolvedCall.getArgumentMapping(valueArgument) : null;
        ArgumentMatch argumentMatch = argumentMapping instanceof ArgumentMatch ? (ArgumentMatch) argumentMapping : null;
        if (argumentMatch != null) {
            return argumentMatch.getValueParameter();
        }
        return null;
    }

    public static final <D extends CallableDescriptor> boolean usesDefaultArguments(ResolvedCall<D> resolvedCall) {
        Intrinsics.checkNotNullParameter(resolvedCall, "<this>");
        List<ResolvedValueArgument> valueArgumentsByIndex = resolvedCall.getValueArgumentsByIndex();
        if (valueArgumentsByIndex == null) {
            return false;
        }
        List<ResolvedValueArgument> list = valueArgumentsByIndex;
        if ((list instanceof Collection) && list.isEmpty()) {
            return false;
        }
        Iterator<T> it = list.iterator();
        while (it.hasNext()) {
            if (((ResolvedValueArgument) it.next()) instanceof DefaultValueArgument) {
                return true;
            }
        }
        return false;
    }

    public static final <C extends ResolutionContext<C>> boolean hasUnresolvedArguments(Call call, ResolutionContext<C> context) {
        Intrinsics.checkNotNullParameter(call, "<this>");
        Intrinsics.checkNotNullParameter(context, "context");
        BindingContext bindingContext = context.trace.getContext();
        Intrinsics.checkNotNullExpressionValue(bindingContext, "getBindingContext(...)");
        StatementFilter statementFilter = context.statementFilter;
        Intrinsics.checkNotNullExpressionValue(statementFilter, "statementFilter");
        return hasUnresolvedArguments(call, bindingContext, statementFilter);
    }

    public static final boolean hasUnresolvedArguments(Call call, BindingContext bindingContext, StatementFilter statementFilter) {
        KotlinType type;
        KotlinType returnType;
        Intrinsics.checkNotNullParameter(call, "<this>");
        Intrinsics.checkNotNullParameter(bindingContext, "bindingContext");
        Intrinsics.checkNotNullParameter(statementFilter, "statementFilter");
        List<? extends ValueArgument> valueArguments = call.getValueArguments();
        Intrinsics.checkNotNullExpressionValue(valueArguments, "getValueArguments(...)");
        List<? extends ValueArgument> list = valueArguments;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
        Iterator<T> it = list.iterator();
        while (it.hasNext()) {
            arrayList.add(((ValueArgument) it.next()).getArgumentExpression());
        }
        ArrayList<KtExpression> arrayList2 = arrayList;
        if ((arrayList2 instanceof Collection) && arrayList2.isEmpty()) {
            return false;
        }
        for (KtExpression ktExpression : arrayList2) {
            if (ktExpression != null && !ArgumentTypeResolver.isFunctionLiteralOrCallableReference(ktExpression, statementFilter)) {
                ResolvedCall<? extends CallableDescriptor> resolvedCall = getResolvedCall(ktExpression, bindingContext);
                if (!(resolvedCall instanceof MutableResolvedCall)) {
                    if ((resolvedCall instanceof NewResolvedCallImpl) && (returnType = ((NewResolvedCallImpl) resolvedCall).getResultingDescriptor().getReturnType()) != null) {
                        Intrinsics.checkNotNull(returnType);
                        if (KotlinTypeKt.isError(returnType)) {
                            continue;
                        }
                    }
                    type = bindingContext.getType(ktExpression);
                    if (type != null) {
                    }
                    return true;
                }
                if (((MutableResolvedCall) resolvedCall).hasInferredReturnType()) {
                    type = bindingContext.getType(ktExpression);
                    if (type != null || KotlinTypeKt.isError(type)) {
                        return true;
                    }
                } else {
                    continue;
                }
            }
        }
        return false;
    }

    public static final List<ValueArgument> getValueArgumentsInParentheses(Call call) {
        Intrinsics.checkNotNullParameter(call, "<this>");
        List<? extends ValueArgument> valueArguments = call.getValueArguments();
        Intrinsics.checkNotNullExpressionValue(valueArguments, "getValueArguments(...)");
        return filterArgsInParentheses(valueArguments);
    }

    public static final List<ValueArgument> getValueArgumentsInParentheses(KtCallElement ktCallElement) {
        Intrinsics.checkNotNullParameter(ktCallElement, "<this>");
        List<? extends ValueArgument> valueArguments = ktCallElement.getValueArguments();
        Intrinsics.checkNotNullExpressionValue(valueArguments, "getValueArguments(...)");
        return filterArgsInParentheses(valueArguments);
    }

    public static final KtElement getValueArgumentListOrElement(Call call) {
        Intrinsics.checkNotNullParameter(call, "<this>");
        if (call instanceof CallTransformer.CallForImplicitInvoke) {
            Call outerCall = ((CallTransformer.CallForImplicitInvoke) call).getOuterCall();
            Intrinsics.checkNotNullExpressionValue(outerCall, "getOuterCall(...)");
            return getValueArgumentListOrElement(outerCall);
        }
        KtElement valueArgumentList = call.getValueArgumentList();
        if (valueArgumentList != null || (valueArgumentList = call.getCalleeExpression()) != null) {
            return valueArgumentList;
        }
        KtElement callElement = call.getCallElement();
        Intrinsics.checkNotNullExpressionValue(callElement, "getCallElement(...)");
        return callElement;
    }

    private static final List<ValueArgument> filterArgsInParentheses(List<? extends ValueArgument> list) {
        ArrayList arrayList = new ArrayList();
        for (Object obj : list) {
            if (!(((ValueArgument) obj) instanceof KtLambdaArgument)) {
                arrayList.add(obj);
            }
        }
        return arrayList;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final KtElement getValueArgumentForExpression$deparenthesizeStructurally(KtElement ktElement) {
        KtExpression ktExpressionDeparenthesizeOnce = ktElement instanceof KtExpression ? KtPsiUtil.deparenthesizeOnce((KtExpression) ktElement) : ktElement;
        if (!Intrinsics.areEqual(ktExpressionDeparenthesizeOnce, ktElement)) {
            return ktExpressionDeparenthesizeOnce;
        }
        if (ktElement instanceof KtLambdaExpression) {
            return ((KtLambdaExpression) ktElement).getFunctionLiteral();
        }
        if (ktElement instanceof KtFunctionLiteral) {
            return ((KtFunctionLiteral) ktElement).getBodyExpression();
        }
        return null;
    }

    private static final boolean getValueArgumentForExpression$isParenthesizedExpression(KtElement ktElement, KtExpression ktExpression) {
        Iterator it = SequencesKt.generateSequence(ktElement, new Function1<KtElement, KtElement>() { // from class: org.jetbrains.kotlin.resolve.calls.util.CallUtilKt$getValueArgumentForExpression$isParenthesizedExpression$1
            @Override // kotlin.jvm.functions.Function1
            public final KtElement invoke(KtElement it2) {
                Intrinsics.checkNotNullParameter(it2, "it");
                return CallUtilKt.getValueArgumentForExpression$deparenthesizeStructurally(it2);
            }
        }).iterator();
        while (it.hasNext()) {
            if (Intrinsics.areEqual((KtElement) it.next(), ktExpression)) {
                return true;
            }
        }
        return false;
    }

    public static final ValueArgument getValueArgumentForExpression(Call call, KtExpression expression) {
        Object next;
        KtExpression argumentExpression;
        Intrinsics.checkNotNullParameter(call, "<this>");
        Intrinsics.checkNotNullParameter(expression, "expression");
        List<? extends ValueArgument> valueArguments = call.getValueArguments();
        Intrinsics.checkNotNullExpressionValue(valueArguments, "getValueArguments(...)");
        Iterator<T> it = valueArguments.iterator();
        while (it.hasNext()) {
            next = it.next();
            ValueArgument valueArgument = (ValueArgument) next;
            if ((valueArgument == null || (argumentExpression = valueArgument.getArgumentExpression()) == null) ? false : getValueArgumentForExpression$isParenthesizedExpression(argumentExpression, expression)) {
                return (ValueArgument) next;
            }
        }
        next = null;
        return (ValueArgument) next;
    }

    public static final Call getCall(KtElement ktElement, BindingContext context) {
        NavigationItem calleeExpressionIfAny;
        KtProperty subjectVariable;
        Intrinsics.checkNotNullParameter(ktElement, "<this>");
        Intrinsics.checkNotNullParameter(context, "context");
        if (ktElement instanceof KtExpression) {
            ktElement = KtPsiUtil.deparenthesize((KtExpression) ktElement);
        }
        if (ktElement == null) {
            return null;
        }
        if ((ktElement instanceof KtCallElement) && ((KtCallElement) ktElement).getCalleeExpression() == null) {
            return null;
        }
        if ((ktElement instanceof KtWhenExpression) && (subjectVariable = ((KtWhenExpression) ktElement).getSubjectVariable()) != null) {
            Call call = getCall(subjectVariable, context);
            return call == null ? (Call) context.get(BindingContext.CALL, ktElement) : call;
        }
        PsiElement parent = ktElement.getLightParent();
        if (parent instanceof KtInstanceExpressionWithLabel) {
            calleeExpressionIfAny = (KtExpression) parent;
        } else if (parent instanceof KtUserType) {
            PsiElement lightParent = ((KtUserType) parent).getLightParent().getLightParent();
            calleeExpressionIfAny = (KtExpression) (lightParent instanceof KtConstructorCalleeExpression ? (KtConstructorCalleeExpression) lightParent : null);
        } else {
            calleeExpressionIfAny = PsiUtilsKt.getCalleeExpressionIfAny(ktElement);
        }
        if (calleeExpressionIfAny != null) {
            return (Call) context.get(BindingContext.CALL, calleeExpressionIfAny);
        }
        return (Call) context.get(BindingContext.CALL, ktElement);
    }

    public static /* synthetic */ Call getParentCall$default(KtElement ktElement, BindingContext bindingContext, boolean z, int i, Object obj) {
        if ((i & 2) != 0) {
            z = true;
        }
        return getParentCall(ktElement, bindingContext, z);
    }

    public static final Call getParentCall(KtElement ktElement, BindingContext context, boolean z) {
        KtElement ktElement2;
        Intrinsics.checkNotNullParameter(ktElement, "<this>");
        Intrinsics.checkNotNullParameter(context, "context");
        Class[] clsArr = {KtSimpleNameExpression.class, KtCallElement.class, KtBinaryExpression.class, KtUnaryExpression.class, KtArrayAccessExpression.class};
        if (z) {
            ktElement2 = (KtElement) PsiTreeUtil.getParentOfType(ktElement, (Class[]) Arrays.copyOf(clsArr, 5));
        } else {
            ktElement2 = (KtElement) PsiTreeUtil.getNonStrictParentOfType(ktElement, (Class[]) Arrays.copyOf(clsArr, 5));
        }
        if (ktElement2 != null) {
            return getCall(ktElement2, context);
        }
        return null;
    }

    public static final ResolvedCall<? extends CallableDescriptor> getResolvedCall(Call call, BindingContext context) {
        Intrinsics.checkNotNullParameter(context, "context");
        return (ResolvedCall) context.get(BindingContext.RESOLVED_CALL, call);
    }

    public static final ResolvedCall<? extends CallableDescriptor> getResolvedCall(KtElement ktElement, BindingContext context) {
        Call call;
        Intrinsics.checkNotNullParameter(context, "context");
        if (ktElement == null || (call = getCall(ktElement, context)) == null) {
            return null;
        }
        return getResolvedCall(call, context);
    }

    public static /* synthetic */ ResolvedCall getParentResolvedCall$default(KtElement ktElement, BindingContext bindingContext, boolean z, int i, Object obj) {
        if ((i & 2) != 0) {
            z = true;
        }
        return getParentResolvedCall(ktElement, bindingContext, z);
    }

    public static final ResolvedCall<? extends CallableDescriptor> getParentResolvedCall(KtElement ktElement, BindingContext context, boolean z) {
        Call parentCall;
        Intrinsics.checkNotNullParameter(context, "context");
        if (ktElement == null || (parentCall = getParentCall(ktElement, context, z)) == null) {
            return null;
        }
        return getResolvedCall(parentCall, context);
    }

    public static final Call getCallWithAssert(KtElement ktElement, BindingContext context) {
        Intrinsics.checkNotNullParameter(ktElement, "<this>");
        Intrinsics.checkNotNullParameter(context, "context");
        Call call = getCall(ktElement, context);
        if (call != null) {
            return call;
        }
        throw new AssertionError("No call for " + org.jetbrains.kotlin.psi.psiUtil.PsiUtilsKt.getTextWithLocation(ktElement));
    }

    public static final ResolvedCall<? extends CallableDescriptor> getResolvedCallWithAssert(KtElement ktElement, BindingContext context) {
        Intrinsics.checkNotNullParameter(ktElement, "<this>");
        Intrinsics.checkNotNullParameter(context, "context");
        ResolvedCall<? extends CallableDescriptor> resolvedCall = getResolvedCall(ktElement, context);
        if (resolvedCall != null) {
            return resolvedCall;
        }
        throw new AssertionError("No resolved call for " + org.jetbrains.kotlin.psi.psiUtil.PsiUtilsKt.getTextWithLocation(ktElement));
    }

    public static final ResolvedCall<? extends CallableDescriptor> getResolvedCallWithAssert(Call call, BindingContext context) {
        Intrinsics.checkNotNullParameter(call, "<this>");
        Intrinsics.checkNotNullParameter(context, "context");
        ResolvedCall<? extends CallableDescriptor> resolvedCall = getResolvedCall(call, context);
        if (resolvedCall != null) {
            return resolvedCall;
        }
        StringBuilder sb = new StringBuilder("No resolved call for ");
        KtElement callElement = call.getCallElement();
        Intrinsics.checkNotNullExpressionValue(callElement, "getCallElement(...)");
        throw new AssertionError(sb.append(org.jetbrains.kotlin.psi.psiUtil.PsiUtilsKt.getTextWithLocation(callElement)).toString());
    }

    public static final ResolvedCall<? extends FunctionDescriptor> getFunctionResolvedCallWithAssert(KtExpression ktExpression, BindingContext context) {
        Intrinsics.checkNotNullParameter(ktExpression, "<this>");
        Intrinsics.checkNotNullParameter(context, "context");
        ResolvedCall resolvedCallWithAssert = getResolvedCallWithAssert(ktExpression, context);
        boolean z = resolvedCallWithAssert.getResultingDescriptor() instanceof FunctionDescriptor;
        Intrinsics.checkNotNull(resolvedCallWithAssert, "null cannot be cast to non-null type org.jetbrains.kotlin.resolve.calls.model.ResolvedCall<out org.jetbrains.kotlin.descriptors.FunctionDescriptor>");
        return resolvedCallWithAssert;
    }

    public static final ResolvedCall<? extends PropertyDescriptor> getPropertyResolvedCallWithAssert(KtExpression ktExpression, BindingContext context) {
        Intrinsics.checkNotNullParameter(ktExpression, "<this>");
        Intrinsics.checkNotNullParameter(context, "context");
        ResolvedCall resolvedCallWithAssert = getResolvedCallWithAssert(ktExpression, context);
        boolean z = resolvedCallWithAssert.getResultingDescriptor() instanceof PropertyDescriptor;
        Intrinsics.checkNotNull(resolvedCallWithAssert, "null cannot be cast to non-null type org.jetbrains.kotlin.resolve.calls.model.ResolvedCall<out org.jetbrains.kotlin.descriptors.PropertyDescriptor>");
        return resolvedCallWithAssert;
    }

    public static final ResolvedCall<? extends VariableDescriptor> getVariableResolvedCallWithAssert(KtExpression ktExpression, BindingContext context) {
        Intrinsics.checkNotNullParameter(ktExpression, "<this>");
        Intrinsics.checkNotNullParameter(context, "context");
        ResolvedCall resolvedCallWithAssert = getResolvedCallWithAssert(ktExpression, context);
        boolean z = resolvedCallWithAssert.getResultingDescriptor() instanceof VariableDescriptor;
        Intrinsics.checkNotNull(resolvedCallWithAssert, "null cannot be cast to non-null type org.jetbrains.kotlin.resolve.calls.model.ResolvedCall<out org.jetbrains.kotlin.descriptors.VariableDescriptor>");
        return resolvedCallWithAssert;
    }

    public static final KotlinType getType(KtExpression ktExpression, BindingContext context) {
        Intrinsics.checkNotNullParameter(ktExpression, "<this>");
        Intrinsics.checkNotNullParameter(context, "context");
        KotlinType type = context.getType(ktExpression);
        if (type != null) {
            return type;
        }
        ResolvedCall<? extends CallableDescriptor> resolvedCall = getResolvedCall(ktExpression, context);
        if (resolvedCall instanceof VariableAsFunctionResolvedCall) {
            return ((VariableDescriptor) ((VariableAsFunctionResolvedCall) resolvedCall).getVariableCall().getResultingDescriptor()).getType();
        }
        return null;
    }

    public static final boolean isFakeElement(KtElement ktElement) {
        Intrinsics.checkNotNullParameter(ktElement, "<this>");
        PsiFile containingFile = ktElement.getContainingFile();
        return (containingFile instanceof KtFile) && KtPsiFactoryKt.getDoNotAnalyze((KtFile) containingFile) != null;
    }

    public static final boolean isFakePsiElement(PsiElement psiElement) {
        Intrinsics.checkNotNullParameter(psiElement, "<this>");
        return (psiElement instanceof KtElement) && isFakeElement((KtElement) psiElement);
    }

    public static final boolean isSafeCall(Call call) {
        Intrinsics.checkNotNullParameter(call, "<this>");
        if ((call instanceof CallTransformer.CallForImplicitInvoke) && ((CallTransformer.CallForImplicitInvoke) call).getOuterCall().isSemanticallyEquivalentToSafeCall()) {
            return true;
        }
        return call.isSemanticallyEquivalentToSafeCall();
    }

    public static final boolean isCallableReference(Call call) {
        Intrinsics.checkNotNullParameter(call, "<this>");
        KtElement callElement = call.getCallElement();
        Intrinsics.checkNotNullExpressionValue(callElement, "getCallElement(...)");
        return isCallableReference(callElement);
    }

    public static final boolean isCallableReference(PsiElement psiElement) {
        Intrinsics.checkNotNullParameter(psiElement, "<this>");
        if (psiElement instanceof KtNameReferenceExpression) {
            PsiElement parent = ((KtNameReferenceExpression) psiElement).getLightParent();
            KtCallableReferenceExpression ktCallableReferenceExpression = parent instanceof KtCallableReferenceExpression ? (KtCallableReferenceExpression) parent : null;
            if (Intrinsics.areEqual(ktCallableReferenceExpression != null ? ktCallableReferenceExpression.getCallableReference() : null, psiElement)) {
                return true;
            }
        }
        return false;
    }

    public static final KtCallableReferenceExpression asCallableReferenceExpression(PsiElement psiElement) {
        Intrinsics.checkNotNullParameter(psiElement, "<this>");
        if (isCallableReference(psiElement)) {
            PsiElement lightParent = psiElement.getLightParent();
            Intrinsics.checkNotNull(lightParent, "null cannot be cast to non-null type org.jetbrains.kotlin.psi.KtCallableReferenceExpression");
            return (KtCallableReferenceExpression) lightParent;
        }
        if (psiElement instanceof KtCallableReferenceExpression) {
            return (KtCallableReferenceExpression) psiElement;
        }
        return null;
    }

    /* JADX WARN: Code duplicated, block: B:7:0x0014  */
    public static final KotlinLookupLocation createLookupLocation(Call call) {
        KtExpression callElement;
        Intrinsics.checkNotNullParameter(call, "<this>");
        KtExpression calleeExpression = call.getCalleeExpression();
        if (calleeExpression != null) {
            callElement = calleeExpression;
            if (isFakeElement(callElement)) {
                callElement = call.getCallElement();
            }
        } else {
            callElement = call.getCallElement();
        }
        Intrinsics.checkNotNull(callElement);
        return new KotlinLookupLocation(callElement);
    }

    public static final KotlinLookupLocation createLookupLocation(KtExpression ktExpression) {
        Intrinsics.checkNotNullParameter(ktExpression, "<this>");
        KtExpression ktExpression2 = ktExpression;
        if (isFakeElement(ktExpression2)) {
            return null;
        }
        return new KotlinLookupLocation(ktExpression2);
    }

    public static final KtExpression getFirstArgumentExpression(ResolvedCall<?> resolvedCall) {
        Intrinsics.checkNotNullParameter(resolvedCall, "<this>");
        List<ResolvedValueArgument> valueArgumentsByIndex = resolvedCall.getValueArgumentsByIndex();
        if (valueArgumentsByIndex != null) {
            return valueArgumentsByIndex.get(0).getArguments().get(0).getArgumentExpression();
        }
        return null;
    }

    public static final KtExpression getReceiverExpression(ResolvedCall<?> resolvedCall) {
        KtExpression expression;
        Intrinsics.checkNotNullParameter(resolvedCall, "<this>");
        ReceiverValue receiverValueMo7305getExtensionReceiver = resolvedCall.getExtensionReceiver();
        ExpressionReceiver expressionReceiver = receiverValueMo7305getExtensionReceiver instanceof ExpressionReceiver ? (ExpressionReceiver) receiverValueMo7305getExtensionReceiver : null;
        if (expressionReceiver != null && (expression = expressionReceiver.getExpression()) != null) {
            return expression;
        }
        ReceiverValue receiverValueMo7304getDispatchReceiver = resolvedCall.getDispatchReceiver();
        ExpressionReceiver expressionReceiver2 = receiverValueMo7304getDispatchReceiver instanceof ExpressionReceiver ? (ExpressionReceiver) receiverValueMo7304getDispatchReceiver : null;
        if (expressionReceiver2 != null) {
            return expressionReceiver2.getExpression();
        }
        return null;
    }

    public static final boolean isTrailingLambdaOnNewLIne(KtLambdaExpression ktLambdaExpression) {
        Intrinsics.checkNotNullParameter(ktLambdaExpression, "<this>");
        PsiElement parent = ktLambdaExpression.getLightParent();
        KtLambdaArgument ktLambdaArgument = parent instanceof KtLambdaArgument ? (KtLambdaArgument) parent : null;
        if (ktLambdaArgument == null) {
            return false;
        }
        for (PsiElement prevSibling = ktLambdaArgument.getPrevSibling(); prevSibling != null && !(prevSibling instanceof KtElement); prevSibling = prevSibling.getPrevSibling()) {
            if ((prevSibling instanceof PsiWhiteSpace) && prevSibling.textContains('\n')) {
                return true;
            }
        }
        return false;
    }

    public static final void reportTrailingLambdaErrorOr(BindingTrace bindingTrace, KtExpression ktExpression, Function1<? super KtExpression, ? extends Diagnostic> originalDiagnostic) {
        Intrinsics.checkNotNullParameter(bindingTrace, "<this>");
        Intrinsics.checkNotNullParameter(originalDiagnostic, "originalDiagnostic");
        if (ktExpression != null) {
            if ((ktExpression instanceof KtLambdaExpression) && isTrailingLambdaOnNewLIne((KtLambdaExpression) ktExpression)) {
                bindingTrace.report(Errors.UNEXPECTED_TRAILING_LAMBDA_ON_A_NEW_LINE.on(ktExpression));
            } else {
                bindingTrace.report(originalDiagnostic.invoke(ktExpression));
            }
        }
    }

    public static final TypeSubstitution toOldSubstitution(final NewTypeSubstitutor newTypeSubstitutor) {
        Intrinsics.checkNotNullParameter(newTypeSubstitutor, "<this>");
        return new TypeSubstitution() { // from class: org.jetbrains.kotlin.resolve.calls.util.CallUtilKt.toOldSubstitution.1
            @Override // org.jetbrains.kotlin.types.TypeSubstitution
            /* JADX INFO: renamed from: get */
            public TypeProjection mo7475get(KotlinType key) {
                Intrinsics.checkNotNullParameter(key, "key");
                UnwrappedType unwrappedTypeSafeSubstitute = newTypeSubstitutor.safeSubstitute(key.unwrap());
                if (unwrappedTypeSafeSubstitute == key) {
                    unwrappedTypeSafeSubstitute = null;
                }
                if (unwrappedTypeSafeSubstitute != null) {
                    return TypeUtilsKt.asTypeProjection(unwrappedTypeSafeSubstitute);
                }
                return null;
            }

            @Override // org.jetbrains.kotlin.types.TypeSubstitution
            public boolean isEmpty() {
                return newTypeSubstitutor.isEmpty();
            }
        };
    }

    public static final <D extends CallableDescriptor> boolean shouldBeSubstituteWithStubTypes(ResolvedCallImpl<D> resolvedCallImpl) {
        KotlinType type;
        KotlinType type2;
        Intrinsics.checkNotNullParameter(resolvedCallImpl, "<this>");
        Map<TypeParameterDescriptor, KotlinType> typeArguments = resolvedCallImpl.getTypeArguments();
        Intrinsics.checkNotNullExpressionValue(typeArguments, "getTypeArguments(...)");
        if (!typeArguments.isEmpty()) {
            Iterator<Map.Entry<TypeParameterDescriptor, KotlinType>> it = typeArguments.entrySet().iterator();
            while (it.hasNext()) {
                KotlinType value = it.next().getValue();
                Intrinsics.checkNotNullExpressionValue(value, "<get-value>(...)");
                if (TypeUtilsKt.contains(value, new Function1<UnwrappedType, Boolean>() { // from class: org.jetbrains.kotlin.resolve.calls.util.CallUtilKt$shouldBeSubstituteWithStubTypes$1$1
                    @Override // kotlin.jvm.functions.Function1
                    public final Boolean invoke(UnwrappedType it2) {
                        Intrinsics.checkNotNullParameter(it2, "it");
                        return Boolean.valueOf(it2 instanceof StubTypeForBuilderInference);
                    }
                })) {
                    return true;
                }
            }
        }
        ReceiverValue receiverValueMo7304getDispatchReceiver = resolvedCallImpl.getDispatchReceiver();
        if (receiverValueMo7304getDispatchReceiver != null && (type2 = receiverValueMo7304getDispatchReceiver.getType()) != null && TypeUtilsKt.contains(type2, new Function1<UnwrappedType, Boolean>() { // from class: org.jetbrains.kotlin.resolve.calls.util.CallUtilKt.shouldBeSubstituteWithStubTypes.2
            @Override // kotlin.jvm.functions.Function1
            public final Boolean invoke(UnwrappedType it2) {
                Intrinsics.checkNotNullParameter(it2, "it");
                return Boolean.valueOf(it2 instanceof StubTypeForBuilderInference);
            }
        })) {
            return true;
        }
        ReceiverValue receiverValueMo7305getExtensionReceiver = resolvedCallImpl.getExtensionReceiver();
        if (receiverValueMo7305getExtensionReceiver != null && (type = receiverValueMo7305getExtensionReceiver.getType()) != null && TypeUtilsKt.contains(type, new Function1<UnwrappedType, Boolean>() { // from class: org.jetbrains.kotlin.resolve.calls.util.CallUtilKt.shouldBeSubstituteWithStubTypes.3
            @Override // kotlin.jvm.functions.Function1
            public final Boolean invoke(UnwrappedType it2) {
                Intrinsics.checkNotNullParameter(it2, "it");
                return Boolean.valueOf(it2 instanceof StubTypeForBuilderInference);
            }
        })) {
            return true;
        }
        Map<ValueParameterDescriptor, ResolvedValueArgument> valueArguments = resolvedCallImpl.getValueArguments();
        Intrinsics.checkNotNullExpressionValue(valueArguments, "getValueArguments(...)");
        if (!valueArguments.isEmpty()) {
            Iterator<Map.Entry<ValueParameterDescriptor, ResolvedValueArgument>> it2 = valueArguments.entrySet().iterator();
            while (it2.hasNext()) {
                KotlinType type3 = it2.next().getKey().getType();
                Intrinsics.checkNotNullExpressionValue(type3, "getType(...)");
                if (TypeUtilsKt.contains(type3, new Function1<UnwrappedType, Boolean>() { // from class: org.jetbrains.kotlin.resolve.calls.util.CallUtilKt$shouldBeSubstituteWithStubTypes$4$1
                    @Override // kotlin.jvm.functions.Function1
                    public final Boolean invoke(UnwrappedType it3) {
                        Intrinsics.checkNotNullParameter(it3, "it");
                        return Boolean.valueOf(it3 instanceof StubTypeForBuilderInference);
                    }
                })) {
                    return true;
                }
            }
        }
        return false;
    }

    public static final KtCallableReferenceExpression extractCallableReferenceExpression(KotlinCall kotlinCall) {
        Intrinsics.checkNotNullParameter(kotlinCall, "<this>");
        return extractCallableReferenceExpression(PSIKotlinCallsKt.getPsiKotlinCall(kotlinCall).getPsiCall());
    }

    public static final KtCallableReferenceExpression extractCallableReferenceExpression(Call call) {
        Intrinsics.checkNotNullParameter(call, "<this>");
        KtElement callElement = call.getCallElement();
        Intrinsics.checkNotNullExpressionValue(callElement, "getCallElement(...)");
        return asCallableReferenceExpression(callElement);
    }
}
