package org.jetbrains.kotlin.resolve.jvm.checkers;

import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.intellij.psi.CommonClassNames;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.ValueParameterDescriptor;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.psi.KtBinaryExpression;
import org.jetbrains.kotlin.resolve.calls.checkers.CallChecker;
import org.jetbrains.kotlin.resolve.calls.checkers.CallCheckerContext;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedCall;
import org.jetbrains.kotlin.resolve.descriptorUtil.DescriptorUtilsKt;
import org.jetbrains.kotlin.resolve.jvm.diagnostics.ErrorsJvm;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.typeUtil.TypeUtilsKt;
import org.jetbrains.kotlin.util.OperatorNameConventions;

/* JADX INFO: compiled from: InconsistentOperatorFromJavaCallChecker.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J$\u0010\u0005\u001a\u00020\u00062\n\u0010\u0007\u001a\u0006\u0012\u0002\b\u00030\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fH\u0016R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\r"}, d2 = {"Lorg/jetbrains/kotlin/resolve/jvm/checkers/InconsistentOperatorFromJavaCallChecker;", "Lorg/jetbrains/kotlin/resolve/calls/checkers/CallChecker;", "()V", "CONCURRENT_HASH_MAP_FQ_NAME", "Lorg/jetbrains/kotlin/name/FqName;", "check", "", "resolvedCall", "Lorg/jetbrains/kotlin/resolve/calls/model/ResolvedCall;", "reportOn", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", "context", "Lorg/jetbrains/kotlin/resolve/calls/checkers/CallCheckerContext;", "frontend.java"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class InconsistentOperatorFromJavaCallChecker implements CallChecker {
    public static final InconsistentOperatorFromJavaCallChecker INSTANCE = new InconsistentOperatorFromJavaCallChecker();
    private static final FqName CONCURRENT_HASH_MAP_FQ_NAME = new FqName(CommonClassNames.JAVA_UTIL_CONCURRENT_HASH_MAP);

    private InconsistentOperatorFromJavaCallChecker() {
    }

    @Override // org.jetbrains.kotlin.resolve.calls.checkers.CallChecker
    public void check(ResolvedCall<?> resolvedCall, PsiElement reportOn, CallCheckerContext context) {
        KotlinType type;
        Intrinsics.checkNotNullParameter(resolvedCall, "resolvedCall");
        Intrinsics.checkNotNullParameter(reportOn, "reportOn");
        Intrinsics.checkNotNullParameter(context, "context");
        CallableDescriptor callableDescriptor = resolvedCall.get$suspendPropertyDescriptor();
        if (Intrinsics.areEqual(callableDescriptor.getShortName(), OperatorNameConventions.CONTAINS)) {
            List<ValueParameterDescriptor> valueParameters = callableDescriptor.getValueParameters();
            Intrinsics.checkNotNullExpressionValue(valueParameters, "getValueParameters(...)");
            ValueParameterDescriptor valueParameterDescriptor = (ValueParameterDescriptor) CollectionsKt.singleOrNull((List) valueParameters);
            if (valueParameterDescriptor != null && (type = valueParameterDescriptor.getType()) != null && TypeUtilsKt.isAnyOrNullableAny(type) && (resolvedCall.getCall().getCallElement() instanceof KtBinaryExpression) && resolvedCall.getStatus().possibleTransformToSuccess()) {
                Iterator it = DescriptorUtilsKt.overriddenTreeUniqueAsSequence(callableDescriptor, false).iterator();
                while (it.hasNext()) {
                    DeclarationDescriptor containingDeclaration = ((CallableDescriptor) it.next()).getDeclarationDescriptor();
                    ClassDescriptor classDescriptor = containingDeclaration instanceof ClassDescriptor ? (ClassDescriptor) containingDeclaration : null;
                    if (classDescriptor != null && Intrinsics.areEqual(DescriptorUtilsKt.fqNameOrNull(classDescriptor), CONCURRENT_HASH_MAP_FQ_NAME)) {
                        context.getTrace().report(ErrorsJvm.CONCURRENT_HASH_MAP_CONTAINS_OPERATOR.on(context.getLanguageVersionSettings(), reportOn));
                        return;
                    }
                }
            }
        }
    }
}
