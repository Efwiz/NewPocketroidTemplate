package org.jetbrains.kotlin.psi;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.intellij.lang.ASTNode;
import org.jetbrains.kotlin.psi.stubs.KotlinValueArgumentStub;
import org.jetbrains.kotlin.psi.stubs.elements.KtStubElementTypes;

/* JADX INFO: compiled from: KtLambdaArgument.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u00012\u00020\u0002B\u000f\b\u0016\u0012\u0006\u0010\u0003\u001a\u00020\u0004¢\u0006\u0002\u0010\u0005B\u0015\b\u0016\u0012\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00000\u0007¢\u0006\u0002\u0010\bJ\n\u0010\t\u001a\u0004\u0018\u00010\nH\u0016¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/psi/KtLambdaArgument;", "Lorg/jetbrains/kotlin/psi/KtValueArgument;", "Lorg/jetbrains/kotlin/psi/LambdaArgument;", "node", "Lorg/jetbrains/kotlin/com/intellij/lang/ASTNode;", "(Lcom/intellij/lang/ASTNode;)V", "stub", "Lorg/jetbrains/kotlin/psi/stubs/KotlinValueArgumentStub;", "(Lorg/jetbrains/kotlin/psi/stubs/KotlinValueArgumentStub;)V", "getLambdaExpression", "Lorg/jetbrains/kotlin/psi/KtLambdaExpression;", "psi"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class KtLambdaArgument extends KtValueArgument implements LambdaArgument {
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KtLambdaArgument(ASTNode node) {
        super(node);
        Intrinsics.checkNotNullParameter(node, "node");
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KtLambdaArgument(KotlinValueArgumentStub<KtLambdaArgument> stub) {
        super(stub, KtStubElementTypes.LAMBDA_ARGUMENT);
        Intrinsics.checkNotNullParameter(stub, "stub");
    }

    @Override // org.jetbrains.kotlin.psi.LambdaArgument
    public KtLambdaExpression getLambdaExpression() {
        KtExpression argumentExpression = getArgumentExpression();
        if (argumentExpression != null) {
            return KtElementUtilsKt.unpackFunctionLiteral$default(argumentExpression, false, 1, null);
        }
        return null;
    }
}
