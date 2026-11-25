package org.jetbrains.kotlin.resolve.calls.checkers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.builtins.FunctionTypesKt;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.descriptors.ClassifierDescriptor;
import org.jetbrains.kotlin.descriptors.ReceiverParameterDescriptor;
import org.jetbrains.kotlin.descriptors.TypeParameterDescriptor;
import org.jetbrains.kotlin.descriptors.ValueParameterDescriptor;
import org.jetbrains.kotlin.diagnostics.Diagnostic;
import org.jetbrains.kotlin.diagnostics.DiagnosticFactory0;
import org.jetbrains.kotlin.diagnostics.DiagnosticUtilsKt;
import org.jetbrains.kotlin.diagnostics.Errors;
import org.jetbrains.kotlin.psi.Call;
import org.jetbrains.kotlin.psi.KtExpression;
import org.jetbrains.kotlin.psi.KtTypeProjection;
import org.jetbrains.kotlin.psi.ValueArgument;
import org.jetbrains.kotlin.resolve.BindingContext;
import org.jetbrains.kotlin.resolve.BindingTrace;
import org.jetbrains.kotlin.resolve.calls.GenericCandidateResolverKt;
import org.jetbrains.kotlin.resolve.calls.components.ArgumentsUtilsKt;
import org.jetbrains.kotlin.resolve.calls.model.ExpressionValueArgument;
import org.jetbrains.kotlin.resolve.calls.model.KotlinCallArgument;
import org.jetbrains.kotlin.resolve.calls.model.ResolutionAtom;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedAtom;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedCall;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedCallAtom;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedValueArgument;
import org.jetbrains.kotlin.resolve.calls.model.SimpleKotlinCallArgument;
import org.jetbrains.kotlin.resolve.calls.model.SingleCallResolutionResult;
import org.jetbrains.kotlin.resolve.calls.tower.NewCallArgumentsKt;
import org.jetbrains.kotlin.resolve.calls.tower.NewResolvedCallImpl;
import org.jetbrains.kotlin.resolve.calls.tower.PSIKotlinCallsKt;
import org.jetbrains.kotlin.resolve.calls.util.CallUtilKt;
import org.jetbrains.kotlin.resolve.scopes.LexicalScopeKind;
import org.jetbrains.kotlin.resolve.scopes.receivers.ReceiverValue;
import org.jetbrains.kotlin.types.DeferredType;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.TypeConstructor;
import org.jetbrains.kotlin.types.TypeProjection;
import org.jetbrains.kotlin.types.TypeUtils;
import org.jetbrains.kotlin.types.typeUtil.TypeUtilsKt;

/* JADX INFO: compiled from: ImplicitNothingAsTypeParameterCallChecker.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000R\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J$\u0010\u0003\u001a\u00020\u00042\n\u0010\u0005\u001a\u0006\u0012\u0002\b\u00030\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0016J\u001c\u0010\u000b\u001a\u00020\f2\n\u0010\u0005\u001a\u0006\u0012\u0002\b\u00030\u00062\u0006\u0010\t\u001a\u00020\nH\u0002J$\u0010\r\u001a\u00020\f2\n\u0010\u0005\u001a\u0006\u0012\u0002\b\u00030\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0002J\u001e\u0010\u000e\u001a\u00020\f2\f\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00110\u00102\u0006\u0010\t\u001a\u00020\nH\u0002J,\u0010\u0012\u001a\n\u0012\u0004\u0012\u00020\u0011\u0018\u00010\u00102\n\u0010\u0005\u001a\u0006\u0012\u0002\b\u00030\u00062\u0006\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u0016H\u0002J\u0010\u0010\u0017\u001a\u00020\f2\u0006\u0010\t\u001a\u00020\nH\u0002J\u0018\u0010\u0018\u001a\u00020\f2\u0006\u0010\u0019\u001a\u00020\u00142\u0006\u0010\u001a\u001a\u00020\u001bH\u0002J\u0016\u0010\u001c\u001a\u0004\u0018\u00010\u001d*\u00020\u00112\u0006\u0010\u0015\u001a\u00020\u0016H\u0002¨\u0006\u001e"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/checkers/ImplicitNothingAsTypeParameterCallChecker;", "Lorg/jetbrains/kotlin/resolve/calls/checkers/CallChecker;", "()V", "check", "", "resolvedCall", "Lorg/jetbrains/kotlin/resolve/calls/model/ResolvedCall;", "reportOn", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", "context", "Lorg/jetbrains/kotlin/resolve/calls/checkers/CallCheckerContext;", "checkAgainstNotNothingExpectedType", "", "checkByReturnPositionWithoutExpected", "findFunctionsWithImplicitNothingAndReport", "resolvedAtoms", "", "Lorg/jetbrains/kotlin/resolve/calls/model/ResolvedAtom;", "getSubResolvedAtomsToAnalyze", "expectedType", "Lorg/jetbrains/kotlin/types/KotlinType;", "bindingContext", "Lorg/jetbrains/kotlin/resolve/BindingContext;", "isDelegationContext", "isOwnTypeParameter", ModuleXmlParser.TYPE, "declaration", "Lorg/jetbrains/kotlin/descriptors/CallableDescriptor;", "getResolvedCallAtom", "Lorg/jetbrains/kotlin/resolve/calls/model/ResolvedCallAtom;", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ImplicitNothingAsTypeParameterCallChecker implements CallChecker {
    public static final ImplicitNothingAsTypeParameterCallChecker INSTANCE = new ImplicitNothingAsTypeParameterCallChecker();

    private ImplicitNothingAsTypeParameterCallChecker() {
    }

    /* JADX WARN: Code duplicated, block: B:58:0x011f  */
    private final boolean checkByReturnPositionWithoutExpected(ResolvedCall<?> resolvedCall, PsiElement reportOn, CallCheckerContext context) {
        boolean z;
        List<TypeProjection> arguments;
        TypeProjection typeProjection;
        ReceiverValue value;
        KotlinType type;
        CallableDescriptor resultingDescriptor = resolvedCall.getResultingDescriptor();
        KotlinType expectedType = context.getResolutionContext().expectedType;
        Intrinsics.checkNotNullExpressionValue(expectedType, "expectedType");
        KotlinType returnType = resultingDescriptor.getReturnType();
        if (returnType == null) {
            return false;
        }
        ReceiverParameterDescriptor receiverParameterDescriptorMo6753getDispatchReceiverParameter = resolvedCall.getResultingDescriptor().mo6753getDispatchReceiverParameter();
        boolean z2 = (receiverParameterDescriptorMo6753getDispatchReceiverParameter == null || (value = receiverParameterDescriptorMo6753getDispatchReceiverParameter.getValue()) == null || (type = value.getType()) == null || !FunctionTypesKt.isBuiltinFunctionalType(type)) ? false : true;
        if (!(returnType instanceof DeferredType) && !z2 && !GenericCandidateResolverKt.getSPECIAL_FUNCTION_NAMES().contains(resultingDescriptor.getShortName())) {
            List<KtTypeProjection> typeArguments = resolvedCall.getCall().getTypeArguments();
            Intrinsics.checkNotNullExpressionValue(typeArguments, "getTypeArguments(...)");
            if (typeArguments.isEmpty()) {
                List<ValueParameterDescriptor> valueParameters = resolvedCall.get$suspendPropertyDescriptor().getValueParameters();
                Intrinsics.checkNotNullExpressionValue(valueParameters, "getValueParameters(...)");
                ArrayList arrayList = new ArrayList();
                for (Object obj : valueParameters) {
                    KotlinType type2 = ((ValueParameterDescriptor) obj).getType();
                    Intrinsics.checkNotNullExpressionValue(type2, "getType(...)");
                    if (FunctionTypesKt.isFunctionOrSuspendFunctionType(type2)) {
                        arrayList.add(obj);
                    }
                }
                ArrayList arrayList2 = arrayList;
                ArrayList arrayList3 = new ArrayList(CollectionsKt.collectionSizeOrDefault(arrayList2, 10));
                Iterator it = arrayList2.iterator();
                while (it.hasNext()) {
                    KotlinType returnType2 = ((ValueParameterDescriptor) it.next()).getReturnType();
                    arrayList3.add((returnType2 == null || (arguments = returnType2.getArguments()) == null || (typeProjection = (TypeProjection) CollectionsKt.last((List) arguments)) == null) ? null : typeProjection.getNullableAnyType());
                }
                Set set = CollectionsKt.toSet(arrayList3);
                KotlinType returnType3 = resultingDescriptor.getOriginal().getReturnType();
                if (returnType3 == null) {
                    return false;
                }
                if (TypeUtilsKt.isNothingOrNullableNothing(returnType) && TypeUtilsKt.isTypeParameter(returnType3)) {
                    CallableDescriptor original = resultingDescriptor.getOriginal();
                    Intrinsics.checkNotNullExpressionValue(original, "getOriginal(...)");
                    if ((isOwnTypeParameter(returnType3, original) || isDelegationContext(context)) && (TypeUtils.noExpectedType(expectedType) || !TypeUtilsKt.isNothing(expectedType))) {
                        z = true;
                    } else {
                        z = false;
                    }
                } else {
                    z = false;
                }
                if ((!TypeUtilsKt.isNullableNothing(returnType) || returnType3.getIsMarkedNullable()) && z && !set.contains(returnType3)) {
                    BindingTrace trace = context.getTrace();
                    Diagnostic diagnosticOn = Errors.IMPLICIT_NOTHING_TYPE_ARGUMENT_IN_RETURN_POSITION.on(reportOn);
                    Intrinsics.checkNotNullExpressionValue(diagnosticOn, "on(...)");
                    DiagnosticFactory0<PsiElement> diagnosticFactory0 = Errors.IMPLICIT_NOTHING_TYPE_ARGUMENT_AGAINST_NOT_NOTHING_EXPECTED_TYPE;
                    Intrinsics.checkNotNullExpressionValue(diagnosticFactory0, "IMPLICIT_NOTHING_TYPE_AR…NOT_NOTHING_EXPECTED_TYPE");
                    DiagnosticUtilsKt.reportDiagnosticOnceWrtDiagnosticFactoryList(trace, diagnosticOn, diagnosticFactory0);
                    return true;
                }
            }
        }
        return false;
    }

    private final boolean isOwnTypeParameter(KotlinType type, CallableDescriptor declaration) {
        ClassifierDescriptor this$0 = type.getConstructor().getThis$0();
        TypeParameterDescriptor typeParameterDescriptor = this$0 instanceof TypeParameterDescriptor ? (TypeParameterDescriptor) this$0 : null;
        if (typeParameterDescriptor == null) {
            return false;
        }
        return Intrinsics.areEqual(typeParameterDescriptor.getContainingModule(), declaration);
    }

    private final boolean isDelegationContext(CallCheckerContext context) {
        return context.getResolutionContext().scope.getKind() == LexicalScopeKind.PROPERTY_DELEGATE_METHOD;
    }

    private final ResolvedCallAtom getResolvedCallAtom(ResolvedAtom resolvedAtom, BindingContext bindingContext) {
        if (resolvedAtom instanceof SingleCallResolutionResult) {
            return ((SingleCallResolutionResult) resolvedAtom).getResultCallAtom();
        }
        ResolutionAtom atom = resolvedAtom.getAtom();
        KotlinCallArgument kotlinCallArgument = atom instanceof KotlinCallArgument ? (KotlinCallArgument) atom : null;
        if (kotlinCallArgument == null) {
            return null;
        }
        ResolvedCall<? extends CallableDescriptor> resolvedCall = CallUtilKt.getResolvedCall(NewCallArgumentsKt.getPsiExpression(kotlinCallArgument), bindingContext);
        if (resolvedCall instanceof NewResolvedCallImpl) {
            return ((NewResolvedCallImpl) resolvedCall).getResolvedCallAtom();
        }
        return null;
    }

    private final boolean findFunctionsWithImplicitNothingAndReport(List<? extends ResolvedAtom> resolvedAtoms, CallCheckerContext context) {
        boolean z;
        boolean z2 = false;
        for (ResolvedAtom resolvedAtom : resolvedAtoms) {
            List<ResolvedAtom> subResolvedAtoms = resolvedAtom.getSubResolvedAtoms();
            List<ResolvedAtom> list = subResolvedAtoms;
            if (list == null || list.isEmpty() || !findFunctionsWithImplicitNothingAndReport(subResolvedAtoms, context)) {
                BindingContext context2 = context.getTrace().getContext();
                Intrinsics.checkNotNullExpressionValue(context2, "getBindingContext(...)");
                ResolvedCallAtom resolvedCallAtom = getResolvedCallAtom(resolvedAtom, context2);
                if (resolvedCallAtom != null) {
                    ResolutionAtom atom = resolvedAtom.getAtom();
                    if (!(atom instanceof SimpleKotlinCallArgument) || TypeUtilsKt.isNothingOrNullableNothing(ArgumentsUtilsKt.getStableType(((SimpleKotlinCallArgument) atom).getReceiver()))) {
                        CallableDescriptor callableDescriptor = resolvedCallAtom.get_candidateDescriptor();
                        List<TypeParameterDescriptor> typeParameters = callableDescriptor.getTypeParameters();
                        Intrinsics.checkNotNullExpressionValue(typeParameters, "getTypeParameters(...)");
                        List<TypeParameterDescriptor> list2 = typeParameters;
                        if (!(list2 instanceof Collection) || !list2.isEmpty()) {
                            Iterator<T> it = list2.iterator();
                            while (true) {
                                if (!it.hasNext()) {
                                    z = false;
                                    break;
                                }
                                TypeConstructor typeConstructor = ((TypeParameterDescriptor) it.next()).getTypeConstructor();
                                KotlinType returnType = callableDescriptor.getReturnType();
                                if (Intrinsics.areEqual(typeConstructor, returnType != null ? returnType.getConstructor() : null)) {
                                    z = true;
                                    break;
                                }
                            }
                        } else {
                            z = false;
                            break;
                        }
                        boolean zContains = GenericCandidateResolverKt.getSPECIAL_FUNCTION_NAMES().contains(callableDescriptor.getShortName());
                        boolean zIsEmpty = PSIKotlinCallsKt.getPsiKotlinCall(resolvedCallAtom.getAtom()).getTypeArguments().isEmpty();
                        if (!zContains && z && zIsEmpty) {
                            BindingTrace trace = context.getTrace();
                            DiagnosticFactory0<PsiElement> diagnosticFactory0 = Errors.IMPLICIT_NOTHING_TYPE_ARGUMENT_AGAINST_NOT_NOTHING_EXPECTED_TYPE;
                            Call psiCall = PSIKotlinCallsKt.getPsiKotlinCall(resolvedCallAtom.getAtom()).getPsiCall();
                            KtExpression calleeExpression = psiCall.getCalleeExpression();
                            Diagnostic diagnosticOn = diagnosticFactory0.on(calleeExpression != null ? calleeExpression : psiCall.getCallElement());
                            Intrinsics.checkNotNullExpressionValue(diagnosticOn, "on(...)");
                            DiagnosticFactory0<PsiElement> IMPLICIT_NOTHING_TYPE_ARGUMENT_IN_RETURN_POSITION = Errors.IMPLICIT_NOTHING_TYPE_ARGUMENT_IN_RETURN_POSITION;
                            Intrinsics.checkNotNullExpressionValue(IMPLICIT_NOTHING_TYPE_ARGUMENT_IN_RETURN_POSITION, "IMPLICIT_NOTHING_TYPE_ARGUMENT_IN_RETURN_POSITION");
                            DiagnosticUtilsKt.reportDiagnosticOnceWrtDiagnosticFactoryList(trace, diagnosticOn, IMPLICIT_NOTHING_TYPE_ARGUMENT_IN_RETURN_POSITION);
                        }
                    }
                }
            }
            z2 = true;
        }
        return z2;
    }

    /* JADX WARN: Code duplicated, block: B:33:0x0075  */
    private final List<ResolvedAtom> getSubResolvedAtomsToAnalyze(ResolvedCall<?> resolvedCall, KotlinType expectedType, BindingContext bindingContext) {
        ReceiverParameterDescriptor extensionReceiverParameter;
        KotlinType type;
        ValueParameterDescriptor parameterForArgument;
        KotlinType type2;
        List<ResolvedAtom> subResolvedAtomsToAnalyze;
        KtExpression argumentExpression;
        List<ResolvedAtom> subResolvedAtomsToAnalyze2 = null;
        if (!(resolvedCall instanceof NewResolvedCallImpl)) {
            return null;
        }
        boolean z = true;
        boolean z2 = (TypeUtils.noExpectedType(expectedType) || TypeUtilsKt.isNothingOrNullableNothing(expectedType)) ? false : true;
        NewResolvedCallImpl newResolvedCallImpl = (NewResolvedCallImpl) resolvedCall;
        KotlinType returnType = newResolvedCallImpl.getResultingDescriptor().getReturnType();
        boolean z3 = returnType != null && TypeUtilsKt.isNothingOrNullableNothing(returnType);
        List<ResolvedAtom> subResolvedAtoms = newResolvedCallImpl.getResolvedCallAtom().getSubResolvedAtoms();
        if (subResolvedAtoms != null && !subResolvedAtoms.isEmpty()) {
            z = false;
        }
        if (z2 && z3 && !z) {
            return newResolvedCallImpl.getResolvedCallAtom().getSubResolvedAtoms();
        }
        Collection<ResolvedValueArgument> collectionValues = newResolvedCallImpl.getValueArguments().values();
        ArrayList arrayList = new ArrayList();
        for (ResolvedValueArgument resolvedValueArgument : collectionValues) {
            if (resolvedValueArgument instanceof ExpressionValueArgument) {
                ExpressionValueArgument expressionValueArgument = (ExpressionValueArgument) resolvedValueArgument;
                ValueArgument valueArgument = expressionValueArgument.getValueArgument();
                ResolvedCall<? extends CallableDescriptor> resolvedCall2 = (valueArgument == null || (argumentExpression = valueArgument.getArgumentExpression()) == null) ? null : CallUtilKt.getResolvedCall(argumentExpression, bindingContext);
                NewResolvedCallImpl newResolvedCallImpl2 = resolvedCall2 instanceof NewResolvedCallImpl ? (NewResolvedCallImpl) resolvedCall2 : null;
                if (newResolvedCallImpl2 == null || (parameterForArgument = CallUtilKt.getParameterForArgument(resolvedCall, expressionValueArgument.getValueArgument())) == null || (type2 = parameterForArgument.getType()) == null) {
                    subResolvedAtomsToAnalyze = null;
                } else {
                    Intrinsics.checkNotNull(type2);
                    subResolvedAtomsToAnalyze = INSTANCE.getSubResolvedAtomsToAnalyze(newResolvedCallImpl2, type2, bindingContext);
                }
            } else {
                subResolvedAtomsToAnalyze = null;
            }
            if (subResolvedAtomsToAnalyze != null) {
                arrayList.add(subResolvedAtomsToAnalyze);
            }
        }
        List<ResolvedAtom> listFlatten = CollectionsKt.flatten(arrayList);
        SimpleKotlinCallArgument extensionReceiverArgument = newResolvedCallImpl.getResolvedCallAtom().getExtensionReceiverArgument();
        KtExpression psiExpression = extensionReceiverArgument != null ? NewCallArgumentsKt.getPsiExpression(extensionReceiverArgument) : null;
        if (psiExpression != null) {
            KtExpression ktExpression = psiExpression;
            if (!Intrinsics.areEqual(CallUtilKt.getResolvedCall(ktExpression, bindingContext), resolvedCall)) {
                ImplicitNothingAsTypeParameterCallChecker implicitNothingAsTypeParameterCallChecker = INSTANCE;
                ResolvedCall<? extends CallableDescriptor> resolvedCall3 = CallUtilKt.getResolvedCall(ktExpression, bindingContext);
                if (resolvedCall3 != null && (extensionReceiverParameter = newResolvedCallImpl.getResultingDescriptor().getExtensionReceiverParameter()) != null && (type = extensionReceiverParameter.getType()) != null) {
                    Intrinsics.checkNotNull(type);
                    subResolvedAtomsToAnalyze2 = implicitNothingAsTypeParameterCallChecker.getSubResolvedAtomsToAnalyze(resolvedCall3, type, bindingContext);
                }
            }
        }
        return subResolvedAtomsToAnalyze2 != null ? CollectionsKt.plus((Collection) listFlatten, (Iterable) subResolvedAtomsToAnalyze2) : listFlatten;
    }

    private final boolean checkAgainstNotNothingExpectedType(ResolvedCall<?> resolvedCall, CallCheckerContext context) {
        KotlinType expectedType = context.getResolutionContext().expectedType;
        Intrinsics.checkNotNullExpressionValue(expectedType, "expectedType");
        BindingContext context2 = context.getTrace().getContext();
        Intrinsics.checkNotNullExpressionValue(context2, "getBindingContext(...)");
        List<ResolvedAtom> subResolvedAtomsToAnalyze = getSubResolvedAtomsToAnalyze(resolvedCall, expectedType, context2);
        if (subResolvedAtomsToAnalyze == null) {
            return false;
        }
        return findFunctionsWithImplicitNothingAndReport(subResolvedAtomsToAnalyze, context);
    }

    @Override // org.jetbrains.kotlin.resolve.calls.checkers.CallChecker
    public void check(ResolvedCall<?> resolvedCall, PsiElement reportOn, CallCheckerContext context) {
        Intrinsics.checkNotNullParameter(resolvedCall, "resolvedCall");
        Intrinsics.checkNotNullParameter(reportOn, "reportOn");
        Intrinsics.checkNotNullParameter(context, "context");
        if (checkByReturnPositionWithoutExpected(resolvedCall, reportOn, context)) {
            return;
        }
        checkAgainstNotNothingExpectedType(resolvedCall, context);
    }
}
