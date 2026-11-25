package org.jetbrains.kotlin.js.resolve.diagnostics;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.ClassifierDescriptor;
import org.jetbrains.kotlin.js.translate.utils.AnnotationsUtils;
import org.jetbrains.kotlin.psi.KtClassLiteralExpression;
import org.jetbrains.kotlin.resolve.BindingTrace;
import org.jetbrains.kotlin.resolve.calls.checkers.RttiExpressionChecker;
import org.jetbrains.kotlin.resolve.calls.checkers.RttiExpressionInformation;
import org.jetbrains.kotlin.resolve.calls.checkers.RttiOperation;
import org.jetbrains.kotlin.resolve.calls.context.ResolutionContext;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.TypeConstructor;
import org.jetbrains.kotlin.types.expressions.ClassLiteralChecker;

/* JADX INFO: compiled from: JsNativeRttiChecker.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u00012\u00020\u0002B\u0005¢\u0006\u0002\u0010\u0003J$\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\t2\n\u0010\n\u001a\u0006\u0012\u0002\b\u00030\u000bH\u0016J \u0010\u0004\u001a\u00020\u00052\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u0011H\u0016¨\u0006\u0012"}, d2 = {"Lorg/jetbrains/kotlin/js/resolve/diagnostics/JsNativeRttiChecker;", "Lorg/jetbrains/kotlin/resolve/calls/checkers/RttiExpressionChecker;", "Lorg/jetbrains/kotlin/types/expressions/ClassLiteralChecker;", "()V", "check", "", "expression", "Lorg/jetbrains/kotlin/psi/KtClassLiteralExpression;", ModuleXmlParser.TYPE, "Lorg/jetbrains/kotlin/types/KotlinType;", "context", "Lorg/jetbrains/kotlin/resolve/calls/context/ResolutionContext;", "rttiInformation", "Lorg/jetbrains/kotlin/resolve/calls/checkers/RttiExpressionInformation;", "reportOn", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", "trace", "Lorg/jetbrains/kotlin/resolve/BindingTrace;", "js.frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class JsNativeRttiChecker implements RttiExpressionChecker, ClassLiteralChecker {

    /* JADX INFO: compiled from: JsNativeRttiChecker.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[RttiOperation.values().length];
            try {
                iArr[RttiOperation.IS.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[RttiOperation.NOT_IS.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[RttiOperation.AS.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                iArr[RttiOperation.SAFE_AS.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    @Override // org.jetbrains.kotlin.resolve.calls.checkers.RttiExpressionChecker
    public void check(RttiExpressionInformation rttiInformation, PsiElement reportOn, BindingTrace trace) {
        TypeConstructor constructor;
        Intrinsics.checkNotNullParameter(rttiInformation, "rttiInformation");
        Intrinsics.checkNotNullParameter(reportOn, "reportOn");
        Intrinsics.checkNotNullParameter(trace, "trace");
        KotlinType sourceType = rttiInformation.getSourceType();
        KotlinType targetType = rttiInformation.getTargetType();
        ClassifierDescriptor this$0 = (targetType == null || (constructor = targetType.getConstructor()) == null) ? null : constructor.getThis$0();
        if (sourceType == null || this$0 == null || !AnnotationsUtils.isNativeInterface(this$0)) {
            return;
        }
        int i = WhenMappings.$EnumSwitchMapping$0[rttiInformation.getOperation().ordinal()];
        if (i == 1 || i == 2) {
            trace.report(ErrorsJs.CANNOT_CHECK_FOR_EXTERNAL_INTERFACE.on(reportOn, targetType));
        } else if (i == 3 || i == 4) {
            trace.report(ErrorsJs.UNCHECKED_CAST_TO_EXTERNAL_INTERFACE.on(reportOn, sourceType, targetType));
        }
    }

    @Override // org.jetbrains.kotlin.types.expressions.ClassLiteralChecker
    public void check(KtClassLiteralExpression expression, KotlinType type, ResolutionContext<?> context) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        Intrinsics.checkNotNullParameter(type, "type");
        Intrinsics.checkNotNullParameter(context, "context");
        ClassifierDescriptor this$0 = type.getConstructor().getThis$0();
        ClassDescriptor classDescriptor = this$0 instanceof ClassDescriptor ? (ClassDescriptor) this$0 : null;
        if (classDescriptor != null && AnnotationsUtils.isNativeInterface(classDescriptor)) {
            context.trace.report(ErrorsJs.EXTERNAL_INTERFACE_AS_CLASS_LITERAL.on(expression));
        }
    }
}
