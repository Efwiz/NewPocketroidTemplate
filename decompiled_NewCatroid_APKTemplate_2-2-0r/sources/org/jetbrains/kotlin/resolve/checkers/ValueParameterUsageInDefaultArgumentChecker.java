package org.jetbrains.kotlin.resolve.checkers;

import java.util.ArrayList;
import java.util.List;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Pair;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.config.LanguageFeature;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.descriptors.ValueParameterDescriptor;
import org.jetbrains.kotlin.descriptors.VariableDescriptor;
import org.jetbrains.kotlin.diagnostics.DiagnosticFactory1;
import org.jetbrains.kotlin.diagnostics.Errors;
import org.jetbrains.kotlin.psi.KtCallExpression;
import org.jetbrains.kotlin.psi.KtDeclaration;
import org.jetbrains.kotlin.psi.KtExpression;
import org.jetbrains.kotlin.psi.KtFunction;
import org.jetbrains.kotlin.psi.KtParameter;
import org.jetbrains.kotlin.psi.KtSimpleNameExpression;
import org.jetbrains.kotlin.psi.KtVisitorVoid;
import org.jetbrains.kotlin.resolve.BindingContext;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedCall;
import org.jetbrains.kotlin.resolve.calls.tower.NewVariableAsFunctionResolvedCallImpl;
import org.jetbrains.kotlin.resolve.calls.util.CallUtilKt;

/* JADX INFO: compiled from: ValueParameterUsageInDefaultArgumentChecker.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J \u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0016J4\u0010\u000b\u001a\u00020\u00042\u0006\u0010\f\u001a\u00020\r2\f\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00100\u000f2\f\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00100\u000f2\u0006\u0010\t\u001a\u00020\nH\u0002¨\u0006\u0012"}, d2 = {"Lorg/jetbrains/kotlin/resolve/checkers/ValueParameterUsageInDefaultArgumentChecker;", "Lorg/jetbrains/kotlin/resolve/checkers/DeclarationChecker;", "()V", "check", "", "declaration", "Lorg/jetbrains/kotlin/psi/KtDeclaration;", "descriptor", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "context", "Lorg/jetbrains/kotlin/resolve/checkers/DeclarationCheckerContext;", "checkParameter", "parameter", "Lorg/jetbrains/kotlin/psi/KtParameter;", "allParameters", "", "Lorg/jetbrains/kotlin/descriptors/ValueParameterDescriptor;", "declaredParameters", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ValueParameterUsageInDefaultArgumentChecker implements DeclarationChecker {
    public static final ValueParameterUsageInDefaultArgumentChecker INSTANCE = new ValueParameterUsageInDefaultArgumentChecker();

    private ValueParameterUsageInDefaultArgumentChecker() {
    }

    @Override // org.jetbrains.kotlin.resolve.checkers.DeclarationChecker
    public void check(KtDeclaration declaration, DeclarationDescriptor descriptor, DeclarationCheckerContext context) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        Intrinsics.checkNotNullParameter(context, "context");
        if ((declaration instanceof KtFunction) && (descriptor instanceof FunctionDescriptor)) {
            List<ValueParameterDescriptor> valueParameters = ((FunctionDescriptor) descriptor).getValueParameters();
            Intrinsics.checkNotNullExpressionValue(valueParameters, "getValueParameters(...)");
            ArrayList arrayList = new ArrayList();
            List<KtParameter> valueParameters2 = ((KtFunction) declaration).getValueParameters();
            Intrinsics.checkNotNullExpressionValue(valueParameters2, "getValueParameters(...)");
            for (Pair pair : CollectionsKt.dropLast(CollectionsKt.zip(valueParameters2, valueParameters), 1)) {
                KtParameter ktParameter = (KtParameter) pair.component1();
                ValueParameterDescriptor valueParameterDescriptor = (ValueParameterDescriptor) pair.component2();
                Intrinsics.checkNotNull(ktParameter);
                checkParameter(ktParameter, valueParameters, arrayList, context);
                arrayList.add(valueParameterDescriptor);
            }
        }
    }

    private final void checkParameter(KtParameter parameter, final List<? extends ValueParameterDescriptor> allParameters, final List<? extends ValueParameterDescriptor> declaredParameters, final DeclarationCheckerContext context) {
        KtExpression defaultValue = parameter.getDefaultValue();
        if (defaultValue == null) {
            return;
        }
        final BindingContext context2 = context.getTrace().getContext();
        Intrinsics.checkNotNullExpressionValue(context2, "getBindingContext(...)");
        defaultValue.acceptChildren(new KtVisitorVoid() { // from class: org.jetbrains.kotlin.resolve.checkers.ValueParameterUsageInDefaultArgumentChecker$checkParameter$visitor$1
            @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElementVisitor
            public void visitElement(PsiElement element) {
                Intrinsics.checkNotNullParameter(element, "element");
                element.acceptChildren(this);
            }

            @Override // org.jetbrains.kotlin.psi.KtVisitorVoid
            public void visitSimpleNameExpression(KtSimpleNameExpression expression) {
                Intrinsics.checkNotNullParameter(expression, "expression");
                ResolvedCall<? extends CallableDescriptor> resolvedCall = CallUtilKt.getResolvedCall(expression, context2);
                CallableDescriptor resultingDescriptor = resolvedCall != null ? resolvedCall.getResultingDescriptor() : null;
                ValueParameterDescriptor valueParameterDescriptor = resultingDescriptor instanceof ValueParameterDescriptor ? (ValueParameterDescriptor) resultingDescriptor : null;
                if (valueParameterDescriptor == null) {
                    return;
                }
                checkParameter(expression, valueParameterDescriptor);
            }

            @Override // org.jetbrains.kotlin.psi.KtVisitorVoid
            public void visitCallExpression(KtCallExpression expression) {
                Intrinsics.checkNotNullParameter(expression, "expression");
                ResolvedCall<? extends CallableDescriptor> resolvedCall = CallUtilKt.getResolvedCall(expression, context2);
                if (resolvedCall instanceof NewVariableAsFunctionResolvedCallImpl) {
                    KtExpression calleeExpression = expression.getCalleeExpression();
                    KtSimpleNameExpression ktSimpleNameExpression = calleeExpression instanceof KtSimpleNameExpression ? (KtSimpleNameExpression) calleeExpression : null;
                    VariableDescriptor resultingDescriptor = ((NewVariableAsFunctionResolvedCallImpl) resolvedCall).getVariableCall().getResultingDescriptor();
                    ValueParameterDescriptor valueParameterDescriptor = resultingDescriptor instanceof ValueParameterDescriptor ? (ValueParameterDescriptor) resultingDescriptor : null;
                    if (ktSimpleNameExpression != null && valueParameterDescriptor != null) {
                        checkParameter(ktSimpleNameExpression, valueParameterDescriptor);
                    }
                }
                expression.acceptChildren(this);
            }

            private final void checkParameter(KtSimpleNameExpression expression, ValueParameterDescriptor descriptor) {
                DiagnosticFactory1<KtSimpleNameExpression, ValueParameterDescriptor> diagnosticFactory1;
                if (!allParameters.contains(descriptor) || declaredParameters.contains(descriptor)) {
                    return;
                }
                boolean zSupportsFeature = context.getLanguageVersionSettings().supportsFeature(LanguageFeature.ProhibitIllegalValueParameterUsageInDefaultArguments);
                if (zSupportsFeature) {
                    diagnosticFactory1 = Errors.UNINITIALIZED_PARAMETER;
                } else if (!zSupportsFeature) {
                    diagnosticFactory1 = Errors.UNINITIALIZED_PARAMETER_WARNING;
                } else {
                    throw new NoWhenBranchMatchedException();
                }
                context.getTrace().report(diagnosticFactory1.on(expression, descriptor));
            }
        });
    }
}
