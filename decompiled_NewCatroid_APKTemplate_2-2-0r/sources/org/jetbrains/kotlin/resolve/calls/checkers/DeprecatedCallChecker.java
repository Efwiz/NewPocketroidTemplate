package org.jetbrains.kotlin.resolve.calls.checkers;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.ArrayIteratorKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SpreadBuilder;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.tree.IElementType;
import org.jetbrains.kotlin.com.intellij.psi.tree.TokenSet;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiTreeUtil;
import org.jetbrains.kotlin.config.LanguageVersionSettings;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.descriptors.PropertyDescriptor;
import org.jetbrains.kotlin.descriptors.PropertyGetterDescriptor;
import org.jetbrains.kotlin.descriptors.PropertySetterDescriptor;
import org.jetbrains.kotlin.lexer.KtTokens;
import org.jetbrains.kotlin.psi.KtBinaryExpression;
import org.jetbrains.kotlin.psi.KtCallableReferenceExpression;
import org.jetbrains.kotlin.psi.KtExpression;
import org.jetbrains.kotlin.psi.KtReferenceExpression;
import org.jetbrains.kotlin.psi.KtSimpleNameExpression;
import org.jetbrains.kotlin.psi.KtUnaryExpression;
import org.jetbrains.kotlin.resolve.BindingTrace;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedCall;
import org.jetbrains.kotlin.resolve.calls.util.FakeCallableDescriptorForObject;
import org.jetbrains.kotlin.resolve.deprecation.DeprecationResolver;
import org.jetbrains.kotlin.resolve.deprecation.DeprecationUtilKt;
import org.jetbrains.kotlin.resolve.deprecation.DescriptorBasedDeprecationInfo;

/* JADX INFO: compiled from: DeprecatedCallChecker.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000P\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J0\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u0011H\u0002J$\u0010\u0006\u001a\u00020\u00072\n\u0010\u0012\u001a\u0006\u0012\u0002\b\u00030\u00132\u0006\u0010\u0014\u001a\u00020\r2\u0006\u0010\u0015\u001a\u00020\u0016H\u0016J\u0015\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u0019\u001a\u00020\rH\u0000¢\u0006\u0002\b\u001aR\u0013\u0010\u0003\u001a\u00070\u0004¢\u0006\u0002\b\u0005X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u001b"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/checkers/DeprecatedCallChecker;", "Lorg/jetbrains/kotlin/resolve/calls/checkers/CallChecker;", "()V", "PROPERTY_SET_OPERATIONS", "Lorg/jetbrains/kotlin/com/intellij/psi/tree/TokenSet;", "Lorg/jetbrains/annotations/NotNull;", "check", "", "targetDescriptor", "Lorg/jetbrains/kotlin/descriptors/CallableDescriptor;", "trace", "Lorg/jetbrains/kotlin/resolve/BindingTrace;", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", "languageVersionSettings", "Lorg/jetbrains/kotlin/config/LanguageVersionSettings;", "deprecationResolver", "Lorg/jetbrains/kotlin/resolve/deprecation/DeprecationResolver;", "resolvedCall", "Lorg/jetbrains/kotlin/resolve/calls/model/ResolvedCall;", "reportOn", "context", "Lorg/jetbrains/kotlin/resolve/calls/checkers/CallCheckerContext;", "shouldCheckPropertyGetter", "", "expression", "shouldCheckPropertyGetter$frontend", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class DeprecatedCallChecker implements CallChecker {
    public static final DeprecatedCallChecker INSTANCE = new DeprecatedCallChecker();
    private static final TokenSet PROPERTY_SET_OPERATIONS;

    private DeprecatedCallChecker() {
    }

    @Override // org.jetbrains.kotlin.resolve.calls.checkers.CallChecker
    public void check(ResolvedCall<?> resolvedCall, PsiElement reportOn, CallCheckerContext context) {
        Intrinsics.checkNotNullParameter(resolvedCall, "resolvedCall");
        Intrinsics.checkNotNullParameter(reportOn, "reportOn");
        Intrinsics.checkNotNullParameter(context, "context");
        CallableDescriptor resultingDescriptor = resolvedCall.getResultingDescriptor();
        Intrinsics.checkNotNullExpressionValue(resultingDescriptor, "getResultingDescriptor(...)");
        check(resultingDescriptor, context.getTrace(), reportOn, context.getLanguageVersionSettings(), context.getDeprecationResolver());
    }

    private final void check(CallableDescriptor targetDescriptor, BindingTrace trace, PsiElement element, LanguageVersionSettings languageVersionSettings, DeprecationResolver deprecationResolver) {
        PropertyGetterDescriptor getter;
        if (targetDescriptor instanceof FakeCallableDescriptorForObject) {
            return;
        }
        List mutableList = CollectionsKt.toMutableList((Collection) deprecationResolver.getDeprecations(targetDescriptor));
        if (targetDescriptor instanceof PropertySetterDescriptor) {
            PropertyDescriptor correspondingProperty = ((PropertySetterDescriptor) targetDescriptor).getCorrespondingProperty();
            Intrinsics.checkNotNullExpressionValue(correspondingProperty, "getCorrespondingProperty(...)");
            CollectionsKt.removeAll((Collection) mutableList, (Iterable) deprecationResolver.getDeprecations(correspondingProperty));
        }
        if (!mutableList.isEmpty()) {
            Iterator it = mutableList.iterator();
            while (it.hasNext()) {
                trace.report(DeprecationUtilKt.createDeprecationDiagnostic(element, (DescriptorBasedDeprecationInfo) it.next(), languageVersionSettings));
            }
        } else if ((targetDescriptor instanceof PropertyDescriptor) && shouldCheckPropertyGetter$frontend(element) && (getter = ((PropertyDescriptor) targetDescriptor).getGetter()) != null) {
            INSTANCE.check(getter, trace, element, languageVersionSettings, deprecationResolver);
        }
    }

    static {
        SpreadBuilder spreadBuilder = new SpreadBuilder(3);
        spreadBuilder.addSpread(KtTokens.ALL_ASSIGNMENTS.getTypes());
        spreadBuilder.add(KtTokens.PLUSPLUS);
        spreadBuilder.add(KtTokens.MINUSMINUS);
        TokenSet tokenSetCreate = TokenSet.create((IElementType[]) spreadBuilder.toArray(new IElementType[spreadBuilder.size()]));
        Intrinsics.checkNotNullExpressionValue(tokenSetCreate, "create(...)");
        PROPERTY_SET_OPERATIONS = tokenSetCreate;
    }

    public final boolean shouldCheckPropertyGetter$frontend(PsiElement expression) {
        KtSimpleNameExpression operationReference;
        Intrinsics.checkNotNullParameter(expression, "expression");
        KtBinaryExpression ktBinaryExpression = (KtBinaryExpression) PsiTreeUtil.getParentOfType(expression, KtBinaryExpression.class);
        if (ktBinaryExpression != null) {
            KtExpression left = ktBinaryExpression.getLeft();
            if (Intrinsics.areEqual(left, expression) && PROPERTY_SET_OPERATIONS.contains(ktBinaryExpression.getOperationToken())) {
                return false;
            }
            KtReferenceExpression[] ktReferenceExpressionArr = (KtReferenceExpression[]) PsiTreeUtil.getChildrenOfType(left, KtReferenceExpression.class);
            if (ktReferenceExpressionArr != null) {
                Iterator it = ArrayIteratorKt.iterator(ktReferenceExpressionArr);
                while (it.hasNext()) {
                    if (Intrinsics.areEqual((KtReferenceExpression) it.next(), expression) && PROPERTY_SET_OPERATIONS.contains(ktBinaryExpression.getOperationToken())) {
                        return false;
                    }
                }
            }
        }
        KtUnaryExpression ktUnaryExpression = (KtUnaryExpression) PsiTreeUtil.getParentOfType(expression, KtUnaryExpression.class);
        if (PROPERTY_SET_OPERATIONS.contains((ktUnaryExpression == null || (operationReference = ktUnaryExpression.getOperationReference()) == null) ? null : operationReference.getReferencedNameElementType())) {
            return false;
        }
        KtCallableReferenceExpression ktCallableReferenceExpression = (KtCallableReferenceExpression) PsiTreeUtil.getParentOfType(expression, KtCallableReferenceExpression.class);
        return ktCallableReferenceExpression == null || !Intrinsics.areEqual(ktCallableReferenceExpression.getCallableReference(), expression);
    }
}
