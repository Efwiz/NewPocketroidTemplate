package org.jetbrains.kotlin.resolve.jvm.checkers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.collections.CollectionsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.descriptors.ClassifierDescriptor;
import org.jetbrains.kotlin.descriptors.ReceiverParameterDescriptor;
import org.jetbrains.kotlin.descriptors.TypeParameterDescriptor;
import org.jetbrains.kotlin.diagnostics.AbstractDiagnostic;
import org.jetbrains.kotlin.diagnostics.Diagnostic;
import org.jetbrains.kotlin.diagnostics.DiagnosticFactoryWithPsiElement;
import org.jetbrains.kotlin.diagnostics.Errors;
import org.jetbrains.kotlin.metadata.jvm.deserialization.JvmProtoBufUtil;
import org.jetbrains.kotlin.psi.KtBinaryExpression;
import org.jetbrains.kotlin.psi.KtExpression;
import org.jetbrains.kotlin.resolve.BindingContext;
import org.jetbrains.kotlin.resolve.calls.checkers.AssignmentCheckUtilsKt;
import org.jetbrains.kotlin.resolve.calls.checkers.AssignmentChecker;
import org.jetbrains.kotlin.resolve.calls.checkers.CallCheckerContext;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedCall;
import org.jetbrains.kotlin.resolve.calls.tower.TowerUtilsKt;
import org.jetbrains.kotlin.resolve.calls.util.CallUtilKt;
import org.jetbrains.kotlin.resolve.jvm.diagnostics.ErrorsJvm;
import org.jetbrains.kotlin.resolve.scopes.receivers.ReceiverValue;
import org.jetbrains.kotlin.synthetic.SyntheticJavaPropertyDescriptor;
import org.jetbrains.kotlin.types.IndexedParametersSubstitution;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.TypeProjection;
import org.jetbrains.kotlin.types.TypeSubstitutor;
import org.jetbrains.kotlin.types.Variance;
import org.jetbrains.kotlin.types.typeUtil.TypeUtilsKt;

/* JADX INFO: compiled from: JvmSyntheticAssignmentChecker.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0018\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000eH\u0016R\u009f\u0001\u0010\u0003\u001a\u0092\u0001\u0012\u008d\u0001\u0012\u008a\u0001\u0012\u000e\b\u0001\u0012\n \u0007*\u0004\u0018\u00010\u00060\u0006\u0012.\b\u0001\u0012*\u0012\u000e\b\u0001\u0012\n \u0007*\u0004\u0018\u00010\u00060\u0006 \u0007*\u0014\u0012\u000e\b\u0001\u0012\n \u0007*\u0004\u0018\u00010\u00060\u0006\u0018\u00010\b0\b \u0007*D\u0012\u000e\b\u0001\u0012\n \u0007*\u0004\u0018\u00010\u00060\u0006\u0012.\b\u0001\u0012*\u0012\u000e\b\u0001\u0012\n \u0007*\u0004\u0018\u00010\u00060\u0006 \u0007*\u0014\u0012\u000e\b\u0001\u0012\n \u0007*\u0004\u0018\u00010\u00060\u0006\u0018\u00010\b0\b\u0018\u00010\u00050\u00050\u0004X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u000f"}, d2 = {"Lorg/jetbrains/kotlin/resolve/jvm/checkers/JvmSyntheticAssignmentChecker;", "Lorg/jetbrains/kotlin/resolve/calls/checkers/AssignmentChecker;", "()V", "TYPE_MISMATCH_ERRORS", "", "Lorg/jetbrains/kotlin/diagnostics/DiagnosticFactoryWithPsiElement;", "Lorg/jetbrains/kotlin/psi/KtExpression;", JvmProtoBufUtil.PLATFORM_TYPE_ID, "Lorg/jetbrains/kotlin/diagnostics/AbstractDiagnostic;", "check", "", "assignmentExpression", "Lorg/jetbrains/kotlin/psi/KtBinaryExpression;", "context", "Lorg/jetbrains/kotlin/resolve/calls/checkers/CallCheckerContext;", "frontend.java"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class JvmSyntheticAssignmentChecker implements AssignmentChecker {
    public static final JvmSyntheticAssignmentChecker INSTANCE = new JvmSyntheticAssignmentChecker();
    private static final Set<DiagnosticFactoryWithPsiElement<? extends KtExpression, ? extends AbstractDiagnostic<? extends KtExpression>>> TYPE_MISMATCH_ERRORS = SetsKt.setOf((Object[]) new DiagnosticFactoryWithPsiElement[]{Errors.TYPE_MISMATCH, Errors.CONSTANT_EXPECTED_TYPE_MISMATCH, Errors.NULL_FOR_NONNULL_TYPE});

    private JvmSyntheticAssignmentChecker() {
    }

    @Override // org.jetbrains.kotlin.resolve.calls.checkers.AssignmentChecker
    public void check(KtBinaryExpression assignmentExpression, CallCheckerContext context) {
        ReceiverValue extensionReceiver;
        KotlinType type;
        ReceiverParameterDescriptor extensionReceiverParameter;
        KotlinType type2;
        KotlinType returnType;
        Intrinsics.checkNotNullParameter(assignmentExpression, "assignmentExpression");
        Intrinsics.checkNotNullParameter(context, "context");
        KtExpression left = assignmentExpression.getLeft();
        if (left == null) {
            return;
        }
        BindingContext context2 = context.getTrace().getContext();
        Intrinsics.checkNotNullExpressionValue(context2, "getBindingContext(...)");
        ResolvedCall<? extends CallableDescriptor> resolvedCall = CallUtilKt.getResolvedCall(left, context2);
        if (resolvedCall == null) {
            return;
        }
        CallableDescriptor resultingDescriptor = resolvedCall.getResultingDescriptor();
        if (!TowerUtilsKt.isSynthesized(resultingDescriptor) || !(resultingDescriptor instanceof SyntheticJavaPropertyDescriptor) || (extensionReceiver = resolvedCall.getExtensionReceiver()) == null || (type = extensionReceiver.getType()) == null || (extensionReceiverParameter = resolvedCall.get$suspendPropertyDescriptor().getExtensionReceiverParameter()) == null || (type2 = extensionReceiverParameter.getType()) == null || type.getConstructor() != type2.getConstructor() || (returnType = resolvedCall.get$suspendPropertyDescriptor().getReturnType()) == null) {
            return;
        }
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        for (Pair pair : CollectionsKt.zip(type2.getArguments(), type.getArguments())) {
            TypeProjection typeProjection = (TypeProjection) pair.component1();
            TypeProjection typeProjection2 = (TypeProjection) pair.component2();
            ClassifierDescriptor this$0 = typeProjection.getNullableAnyType().getConstructor().getThis$0();
            TypeParameterDescriptor typeParameterDescriptor = this$0 instanceof TypeParameterDescriptor ? (TypeParameterDescriptor) this$0 : null;
            if (typeParameterDescriptor != null) {
                arrayList.add(typeParameterDescriptor);
                arrayList2.add(typeProjection2);
            }
        }
        TypeSubstitutor typeSubstitutorCreate = TypeSubstitutor.create(new IndexedParametersSubstitution((TypeParameterDescriptor[]) arrayList.toArray(new TypeParameterDescriptor[0]), (TypeProjection[]) arrayList2.toArray(new TypeProjection[0]), true));
        Intrinsics.checkNotNullExpressionValue(typeSubstitutorCreate, "create(...)");
        KotlinType kotlinTypeSubstitute = typeSubstitutorCreate.substitute(returnType.unwrap(), Variance.IN_VARIANCE);
        if (kotlinTypeSubstitute == null) {
            return;
        }
        if (TypeUtilsKt.isNothing(kotlinTypeSubstitute)) {
            context.getTrace().report(ErrorsJvm.SYNTHETIC_SETTER_PROJECTED_OUT.on(left, resultingDescriptor));
            return;
        }
        KtExpression right = assignmentExpression.getRight();
        if (right == null) {
            return;
        }
        BindingContext context3 = context.getTrace().getContext();
        Intrinsics.checkNotNullExpressionValue(context3, "getBindingContext(...)");
        KotlinType type3 = CallUtilKt.getType(right, context3);
        if (type3 == null || AssignmentCheckUtilsKt.isAssignmentCorrectWithDataFlowInfo(kotlinTypeSubstitute, right, type3, context)) {
            return;
        }
        KtExpression ktExpression = right;
        Collection<Diagnostic> collectionForElement = context.getTrace().getContext().getDiagnostics().forElement(ktExpression);
        if (!(collectionForElement instanceof Collection) || !collectionForElement.isEmpty()) {
            Iterator<T> it = collectionForElement.iterator();
            while (it.hasNext()) {
                if (CollectionsKt.contains(TYPE_MISMATCH_ERRORS, ((Diagnostic) it.next()).getFactory())) {
                    return;
                }
            }
        }
        context.getTrace().report(Errors.TYPE_MISMATCH_WARNING.on(ktExpression, kotlinTypeSubstitute, type3));
    }
}
