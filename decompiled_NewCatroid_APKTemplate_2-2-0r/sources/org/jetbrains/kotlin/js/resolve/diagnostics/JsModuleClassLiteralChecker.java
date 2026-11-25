package org.jetbrains.kotlin.js.resolve.diagnostics;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.ClassifierDescriptor;
import org.jetbrains.kotlin.psi.KtClassLiteralExpression;
import org.jetbrains.kotlin.resolve.BindingContext;
import org.jetbrains.kotlin.resolve.BindingTrace;
import org.jetbrains.kotlin.resolve.calls.context.ResolutionContext;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.expressions.ClassLiteralChecker;

/* JADX INFO: compiled from: JsModuleClassLiteralChecker.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J$\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\n\u0010\t\u001a\u0006\u0012\u0002\b\u00030\nH\u0016¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/js/resolve/diagnostics/JsModuleClassLiteralChecker;", "Lorg/jetbrains/kotlin/types/expressions/ClassLiteralChecker;", "()V", "check", "", "expression", "Lorg/jetbrains/kotlin/psi/KtClassLiteralExpression;", ModuleXmlParser.TYPE, "Lorg/jetbrains/kotlin/types/KotlinType;", "context", "Lorg/jetbrains/kotlin/resolve/calls/context/ResolutionContext;", "js.frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class JsModuleClassLiteralChecker implements ClassLiteralChecker {
    public static final JsModuleClassLiteralChecker INSTANCE = new JsModuleClassLiteralChecker();

    private JsModuleClassLiteralChecker() {
    }

    @Override // org.jetbrains.kotlin.types.expressions.ClassLiteralChecker
    public void check(KtClassLiteralExpression expression, KotlinType type, ResolutionContext<?> context) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        Intrinsics.checkNotNullParameter(type, "type");
        Intrinsics.checkNotNullParameter(context, "context");
        ClassifierDescriptor this$0 = type.getConstructor().getThis$0();
        ClassDescriptor classDescriptor = this$0 instanceof ClassDescriptor ? (ClassDescriptor) this$0 : null;
        if (classDescriptor == null) {
            return;
        }
        BindingContext context2 = context.trace.getContext();
        Intrinsics.checkNotNullExpressionValue(context2, "getBindingContext(...)");
        BindingTrace trace = context.trace;
        Intrinsics.checkNotNullExpressionValue(trace, "trace");
        JsModuleCheckUtilKt.checkJsModuleUsage(context2, trace, context.scope.getOwnerDescriptor(), classDescriptor, expression);
    }
}
