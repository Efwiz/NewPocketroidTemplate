package org.jetbrains.kotlin.resolve.konan.diagnostics;

import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.ClassifierDescriptor;
import org.jetbrains.kotlin.descriptors.TypeParameterDescriptor;
import org.jetbrains.kotlin.descriptors.konan.UtilsKt;
import org.jetbrains.kotlin.diagnostics.DiagnosticFactory1;
import org.jetbrains.kotlin.psi.KtElement;
import org.jetbrains.kotlin.psi.KtTypeArgumentList;
import org.jetbrains.kotlin.psi.KtTypeProjection;
import org.jetbrains.kotlin.resolve.BindingTrace;
import org.jetbrains.kotlin.resolve.calls.checkers.CallChecker;
import org.jetbrains.kotlin.resolve.calls.checkers.CallCheckerContext;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedCall;
import org.jetbrains.kotlin.types.KotlinType;

/* JADX INFO: compiled from: NativeReifiedForwardDeclarationChecker.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J$\u0010\u0003\u001a\u00020\u00042\n\u0010\u0005\u001a\u0006\u0012\u0002\b\u00030\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0016¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/resolve/konan/diagnostics/NativeReifiedForwardDeclarationChecker;", "Lorg/jetbrains/kotlin/resolve/calls/checkers/CallChecker;", "()V", "check", "", "resolvedCall", "Lorg/jetbrains/kotlin/resolve/calls/model/ResolvedCall;", "reportOn", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", "context", "Lorg/jetbrains/kotlin/resolve/calls/checkers/CallCheckerContext;", "frontend.native"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class NativeReifiedForwardDeclarationChecker implements CallChecker {
    @Override // org.jetbrains.kotlin.resolve.calls.checkers.CallChecker
    public void check(ResolvedCall<?> resolvedCall, PsiElement reportOn, CallCheckerContext context) {
        KtElement calleeExpression;
        Intrinsics.checkNotNullParameter(resolvedCall, "resolvedCall");
        Intrinsics.checkNotNullParameter(reportOn, "reportOn");
        Intrinsics.checkNotNullParameter(context, "context");
        KtTypeArgumentList typeArgumentList = resolvedCall.getCall().getTypeArgumentList();
        List<KtTypeProjection> arguments = typeArgumentList != null ? typeArgumentList.getArguments() : null;
        Map<TypeParameterDescriptor, KotlinType> typeArguments = resolvedCall.getTypeArguments();
        Intrinsics.checkNotNullExpressionValue(typeArguments, "getTypeArguments(...)");
        for (Map.Entry<TypeParameterDescriptor, KotlinType> entry : typeArguments.entrySet()) {
            TypeParameterDescriptor key = entry.getKey();
            KotlinType value = entry.getValue();
            if (key.isReified()) {
                ClassifierDescriptor this$0 = value.getConstructor().getThis$0();
                if ((this$0 instanceof ClassDescriptor) && UtilsKt.getForwardDeclarationKindOrNull((ClassDescriptor) this$0) != null) {
                    if (arguments != null) {
                        calleeExpression = arguments.get(key.getIndex()).getTypeReference();
                    } else {
                        calleeExpression = resolvedCall.getCall().getCalleeExpression();
                    }
                    KtElement ktElement = calleeExpression;
                    BindingTrace trace = context.getTrace();
                    DiagnosticFactory1<PsiElement, KotlinType> diagnosticFactory1 = ErrorsNative.FORWARD_DECLARATION_AS_REIFIED_TYPE_ARGUMENT;
                    Intrinsics.checkNotNull(ktElement);
                    trace.report(diagnosticFactory1.on(ktElement, value));
                }
            }
        }
    }
}
