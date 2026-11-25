package org.jetbrains.kotlin.resolve.jvm.checkers;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.descriptors.ClassKind;
import org.jetbrains.kotlin.descriptors.ClassifierDescriptor;
import org.jetbrains.kotlin.descriptors.ValueParameterDescriptor;
import org.jetbrains.kotlin.diagnostics.DiagnosticFactory0;
import org.jetbrains.kotlin.load.java.JvmAnnotationNames;
import org.jetbrains.kotlin.load.java.JvmAnnotationNamesKt;
import org.jetbrains.kotlin.load.java.descriptors.JavaClassConstructorDescriptor;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.psi.KtAnnotationEntry;
import org.jetbrains.kotlin.psi.KtElement;
import org.jetbrains.kotlin.psi.KtExpression;
import org.jetbrains.kotlin.psi.ValueArgument;
import org.jetbrains.kotlin.resolve.BindingContext;
import org.jetbrains.kotlin.resolve.DescriptorUtils;
import org.jetbrains.kotlin.resolve.calls.checkers.CallChecker;
import org.jetbrains.kotlin.resolve.calls.checkers.CallCheckerContext;
import org.jetbrains.kotlin.resolve.calls.model.ExpressionValueArgument;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedCall;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedValueArgument;
import org.jetbrains.kotlin.resolve.jvm.diagnostics.ErrorsJvm;
import org.jetbrains.kotlin.types.KotlinType;

/* JADX INFO: compiled from: JavaAnnotationCallChecker.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000@\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u0000 \u00152\u00020\u0001:\u0001\u0015B\u0005¢\u0006\u0002\u0010\u0002J$\u0010\u0003\u001a\u00020\u00042\n\u0010\u0005\u001a\u0006\u0012\u0002\b\u00030\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0016J\u001c\u0010\u000b\u001a\u00020\u00042\n\u0010\u0005\u001a\u0006\u0012\u0002\b\u00030\u00062\u0006\u0010\t\u001a\u00020\nH\u0002J\u001c\u0010\f\u001a\u00020\u00042\n\u0010\u0005\u001a\u0006\u0012\u0002\b\u00030\u00062\u0006\u0010\t\u001a\u00020\nH\u0002J2\u0010\r\u001a\u00020\u00042\u0006\u0010\t\u001a\u00020\n2\u0012\u0010\u000e\u001a\u000e\u0012\u0004\u0012\u00020\u0010\u0012\u0004\u0012\u00020\u00110\u000f2\f\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00140\u0013H\u0002¨\u0006\u0016"}, d2 = {"Lorg/jetbrains/kotlin/resolve/jvm/checkers/JavaAnnotationCallChecker;", "Lorg/jetbrains/kotlin/resolve/calls/checkers/CallChecker;", "()V", "check", "", "resolvedCall", "Lorg/jetbrains/kotlin/resolve/calls/model/ResolvedCall;", "reportOn", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", "context", "Lorg/jetbrains/kotlin/resolve/calls/checkers/CallCheckerContext;", "reportDeprecatedJavaAnnotation", "reportErrorsOnPositionedArguments", "reportOnValueArgument", "arguments", "", "Lorg/jetbrains/kotlin/descriptors/ValueParameterDescriptor;", "Lorg/jetbrains/kotlin/resolve/calls/model/ResolvedValueArgument;", "diagnostic", "Lorg/jetbrains/kotlin/diagnostics/DiagnosticFactory0;", "Lorg/jetbrains/kotlin/psi/KtExpression;", "Companion", "frontend.java"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class JavaAnnotationCallChecker implements CallChecker {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);

    @Override // org.jetbrains.kotlin.resolve.calls.checkers.CallChecker
    public void check(ResolvedCall<?> resolvedCall, PsiElement reportOn, CallCheckerContext context) {
        Intrinsics.checkNotNullParameter(resolvedCall, "resolvedCall");
        Intrinsics.checkNotNullParameter(reportOn, "reportOn");
        Intrinsics.checkNotNullParameter(context, "context");
        CallableDescriptor original = resolvedCall.getResultingDescriptor().getOriginal();
        Intrinsics.checkNotNullExpressionValue(original, "getOriginal(...)");
        if ((original instanceof JavaClassConstructorDescriptor) && ((JavaClassConstructorDescriptor) original).getContainingModule().getKind() == ClassKind.ANNOTATION_CLASS) {
            reportErrorsOnPositionedArguments(resolvedCall, context);
            reportDeprecatedJavaAnnotation(resolvedCall, context);
        }
    }

    private final void reportDeprecatedJavaAnnotation(ResolvedCall<?> resolvedCall, CallCheckerContext context) {
        KotlinType kotlinType;
        KtElement callElement = resolvedCall.getCall().getCallElement();
        KtAnnotationEntry ktAnnotationEntry = callElement instanceof KtAnnotationEntry ? (KtAnnotationEntry) callElement : null;
        if (ktAnnotationEntry == null || (kotlinType = (KotlinType) context.getTrace().get(BindingContext.TYPE, ktAnnotationEntry.getTypeReference())) == null) {
            return;
        }
        Map<FqName, FqName> javaToKotlinNameMap = JvmAnnotationNamesKt.getJavaToKotlinNameMap();
        ClassifierDescriptor this$0 = kotlinType.getConstructor().getThis$0();
        FqName fqName = javaToKotlinNameMap.get(this$0 != null ? DescriptorUtils.getFqNameSafe(this$0) : null);
        if (fqName != null) {
            context.getTrace().report(ErrorsJvm.DEPRECATED_JAVA_ANNOTATION.on(ktAnnotationEntry, fqName));
        }
    }

    private final void reportErrorsOnPositionedArguments(ResolvedCall<?> resolvedCall, CallCheckerContext context) {
        for (Map.Entry<ValueParameterDescriptor, ResolvedValueArgument> entry : INSTANCE.getJavaAnnotationCallValueArgumentsThatShouldBeNamed(resolvedCall).entrySet()) {
            DiagnosticFactory0<KtExpression> POSITIONED_VALUE_ARGUMENT_FOR_JAVA_ANNOTATION = ErrorsJvm.POSITIONED_VALUE_ARGUMENT_FOR_JAVA_ANNOTATION;
            Intrinsics.checkNotNullExpressionValue(POSITIONED_VALUE_ARGUMENT_FOR_JAVA_ANNOTATION, "POSITIONED_VALUE_ARGUMENT_FOR_JAVA_ANNOTATION");
            reportOnValueArgument(context, entry, POSITIONED_VALUE_ARGUMENT_FOR_JAVA_ANNOTATION);
        }
    }

    private final void reportOnValueArgument(CallCheckerContext context, Map.Entry<? extends ValueParameterDescriptor, ? extends ResolvedValueArgument> arguments, DiagnosticFactory0<KtExpression> diagnostic) {
        Iterator<ValueArgument> it = arguments.getValue().getArguments().iterator();
        while (it.hasNext()) {
            KtExpression argumentExpression = it.next().getArgumentExpression();
            if (argumentExpression != null) {
                context.getTrace().report(diagnostic.on(argumentExpression));
            }
        }
    }

    /* JADX INFO: compiled from: JavaAnnotationCallChecker.kt */
    @Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u001e\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00060\u00042\n\u0010\u0007\u001a\u0006\u0012\u0002\b\u00030\b¨\u0006\t"}, d2 = {"Lorg/jetbrains/kotlin/resolve/jvm/checkers/JavaAnnotationCallChecker$Companion;", "", "()V", "getJavaAnnotationCallValueArgumentsThatShouldBeNamed", "", "Lorg/jetbrains/kotlin/descriptors/ValueParameterDescriptor;", "Lorg/jetbrains/kotlin/resolve/calls/model/ResolvedValueArgument;", "resolvedCall", "Lorg/jetbrains/kotlin/resolve/calls/model/ResolvedCall;", "frontend.java"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final Map<ValueParameterDescriptor, ResolvedValueArgument> getJavaAnnotationCallValueArgumentsThatShouldBeNamed(ResolvedCall<?> resolvedCall) {
            Intrinsics.checkNotNullParameter(resolvedCall, "resolvedCall");
            Map<ValueParameterDescriptor, ResolvedValueArgument> valueArguments = resolvedCall.getValueArguments();
            Intrinsics.checkNotNullExpressionValue(valueArguments, "getValueArguments(...)");
            LinkedHashMap linkedHashMap = new LinkedHashMap();
            for (Map.Entry<ValueParameterDescriptor, ResolvedValueArgument> entry : valueArguments.entrySet()) {
                if (!Intrinsics.areEqual(entry.getKey().getShortName(), JvmAnnotationNames.DEFAULT_ANNOTATION_MEMBER_NAME) && (entry.getValue() instanceof ExpressionValueArgument)) {
                    ResolvedValueArgument value = entry.getValue();
                    Intrinsics.checkNotNull(value, "null cannot be cast to non-null type org.jetbrains.kotlin.resolve.calls.model.ExpressionValueArgument");
                    ValueArgument valueArgument = ((ExpressionValueArgument) value).getValueArgument();
                    if (!(valueArgument != null ? valueArgument.isNamed() : true)) {
                        linkedHashMap.put(entry.getKey(), entry.getValue());
                    }
                }
            }
            return linkedHashMap;
        }
    }
}
