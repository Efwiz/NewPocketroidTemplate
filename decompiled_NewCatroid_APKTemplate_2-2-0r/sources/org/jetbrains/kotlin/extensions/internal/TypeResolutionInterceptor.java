package org.jetbrains.kotlin.extensions.internal;

import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.com.intellij.openapi.project.Project;
import org.jetbrains.kotlin.descriptors.impl.AnonymousFunctionDescriptor;
import org.jetbrains.kotlin.extensions.ProjectExtensionDescriptor;
import org.jetbrains.kotlin.psi.KtElement;
import org.jetbrains.kotlin.psi.KtLambdaExpression;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.expressions.ExpressionTypingContext;

/* JADX INFO: compiled from: TypeResolutionInterceptor.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000F\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\u0018\u0000 \u00162\u00020\u0001:\u0001\u0016B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u001e\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\tJ\"\u0010\u000f\u001a\u0004\u0018\u00010\u00102\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\f\u001a\u00020\r2\b\u0010\u0013\u001a\u0004\u0018\u00010\u0010J\u0006\u0010\u0014\u001a\u00020\u0015R\u0014\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0017"}, d2 = {"Lorg/jetbrains/kotlin/extensions/internal/TypeResolutionInterceptor;", "", "project", "Lorg/jetbrains/kotlin/com/intellij/openapi/project/Project;", "(Lcom/intellij/openapi/project/Project;)V", "extensions", "", "Lorg/jetbrains/kotlin/extensions/internal/TypeResolutionInterceptorExtension;", "interceptFunctionLiteralDescriptor", "Lorg/jetbrains/kotlin/descriptors/impl/AnonymousFunctionDescriptor;", "expression", "Lorg/jetbrains/kotlin/psi/KtLambdaExpression;", "context", "Lorg/jetbrains/kotlin/types/expressions/ExpressionTypingContext;", "descriptor", "interceptType", "Lorg/jetbrains/kotlin/types/KotlinType;", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, "Lorg/jetbrains/kotlin/psi/KtElement;", "resultType", "isEmpty", "", "Companion", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class TypeResolutionInterceptor {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private final List<TypeResolutionInterceptorExtension> extensions;

    public TypeResolutionInterceptor(Project project) {
        Intrinsics.checkNotNullParameter(project, "project");
        this.extensions = INSTANCE.getInstances(project);
    }

    public final AnonymousFunctionDescriptor interceptFunctionLiteralDescriptor(KtLambdaExpression expression, ExpressionTypingContext context, AnonymousFunctionDescriptor descriptor) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        Iterator<T> it = this.extensions.iterator();
        while (it.hasNext()) {
            descriptor = ((TypeResolutionInterceptorExtension) it.next()).interceptFunctionLiteralDescriptor(expression, context, descriptor);
        }
        return descriptor;
    }

    public final KotlinType interceptType(KtElement element, ExpressionTypingContext context, KotlinType resultType) {
        Intrinsics.checkNotNullParameter(element, "element");
        Intrinsics.checkNotNullParameter(context, "context");
        if (resultType == null) {
            return null;
        }
        Iterator<T> it = this.extensions.iterator();
        while (it.hasNext()) {
            resultType = ((TypeResolutionInterceptorExtension) it.next()).interceptType(element, context, resultType);
        }
        return resultType;
    }

    public final boolean isEmpty() {
        return this.extensions.isEmpty();
    }

    /* JADX INFO: compiled from: TypeResolutionInterceptor.kt */
    @Metadata(d1 = {"\u0000\u0010\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0003¨\u0006\u0004"}, d2 = {"Lorg/jetbrains/kotlin/extensions/internal/TypeResolutionInterceptor$Companion;", "Lorg/jetbrains/kotlin/extensions/ProjectExtensionDescriptor;", "Lorg/jetbrains/kotlin/extensions/internal/TypeResolutionInterceptorExtension;", "()V", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion extends ProjectExtensionDescriptor<TypeResolutionInterceptorExtension> {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
            super("org.jetbrains.kotlin.extensions.internal.typeResolutionInterceptorExtension", TypeResolutionInterceptorExtension.class);
        }
    }
}
