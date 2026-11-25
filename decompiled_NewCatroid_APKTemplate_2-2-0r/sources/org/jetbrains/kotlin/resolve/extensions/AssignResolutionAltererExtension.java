package org.jetbrains.kotlin.resolve.extensions;

import kotlin.Metadata;
import org.jetbrains.kotlin.extensions.AnnotationBasedExtension;
import org.jetbrains.kotlin.extensions.ProjectExtensionDescriptor;
import org.jetbrains.kotlin.psi.KtBinaryExpression;
import org.jetbrains.kotlin.psi.KtExpression;
import org.jetbrains.kotlin.resolve.BindingContext;
import org.jetbrains.kotlin.resolve.scopes.LexicalWritableScope;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.expressions.ExpressionTypingComponents;
import org.jetbrains.kotlin.types.expressions.ExpressionTypingContext;
import org.jetbrains.kotlin.types.expressions.KotlinTypeInfo;

/* JADX INFO: compiled from: AssignResolutionAltererExtension.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000D\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\bg\u0018\u0000 \u00162\u00020\u0001:\u0001\u0016J\"\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\b\u0010\u0006\u001a\u0004\u0018\u00010\u00072\u0006\u0010\b\u001a\u00020\tH&JJ\u0010\n\u001a\u0004\u0018\u00010\u000b2\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\r2\u0006\u0010\u000f\u001a\u00020\u000b2\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u0015H&ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\u0017À\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/resolve/extensions/AssignResolutionAltererExtension;", "Lorg/jetbrains/kotlin/extensions/AnnotationBasedExtension;", "needOverloadAssign", "", "expression", "Lorg/jetbrains/kotlin/psi/KtBinaryExpression;", "leftType", "Lorg/jetbrains/kotlin/types/KotlinType;", "bindingContext", "Lorg/jetbrains/kotlin/resolve/BindingContext;", "resolveAssign", "Lorg/jetbrains/kotlin/types/expressions/KotlinTypeInfo;", "leftOperand", "Lorg/jetbrains/kotlin/psi/KtExpression;", "left", "leftInfo", "context", "Lorg/jetbrains/kotlin/types/expressions/ExpressionTypingContext;", "components", "Lorg/jetbrains/kotlin/types/expressions/ExpressionTypingComponents;", "scope", "Lorg/jetbrains/kotlin/resolve/scopes/LexicalWritableScope;", "Companion", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface AssignResolutionAltererExtension extends AnnotationBasedExtension {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = Companion.$$INSTANCE;

    boolean needOverloadAssign(KtBinaryExpression expression, KotlinType leftType, BindingContext bindingContext);

    KotlinTypeInfo resolveAssign(BindingContext bindingContext, KtBinaryExpression expression, KtExpression leftOperand, KtExpression left, KotlinTypeInfo leftInfo, ExpressionTypingContext context, ExpressionTypingComponents components, LexicalWritableScope scope);

    /* JADX INFO: compiled from: AssignResolutionAltererExtension.kt */
    @Metadata(d1 = {"\u0000\u0010\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0003¨\u0006\u0004"}, d2 = {"Lorg/jetbrains/kotlin/resolve/extensions/AssignResolutionAltererExtension$Companion;", "Lorg/jetbrains/kotlin/extensions/ProjectExtensionDescriptor;", "Lorg/jetbrains/kotlin/resolve/extensions/AssignResolutionAltererExtension;", "()V", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion extends ProjectExtensionDescriptor<AssignResolutionAltererExtension> {
        static final /* synthetic */ Companion $$INSTANCE = new Companion();

        private Companion() {
            super("org.jetbrains.kotlin.assignResolutionAltererExtension", AssignResolutionAltererExtension.class);
        }
    }
}
