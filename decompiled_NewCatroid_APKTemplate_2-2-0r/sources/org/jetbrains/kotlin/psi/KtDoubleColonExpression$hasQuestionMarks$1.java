package org.jetbrains.kotlin.psi;

import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.FunctionReference;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KDeclarationContainer;
import org.jetbrains.kotlin.com.intellij.lang.ASTNode;

/* JADX INFO: compiled from: KtDoubleColonExpression.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
/* synthetic */ class KtDoubleColonExpression$hasQuestionMarks$1 extends FunctionReference implements Function1<ASTNode, ASTNode> {
    public static final KtDoubleColonExpression$hasQuestionMarks$1 INSTANCE = new KtDoubleColonExpression$hasQuestionMarks$1();

    KtDoubleColonExpression$hasQuestionMarks$1() {
        super(1);
    }

    @Override // kotlin.jvm.internal.CallableReference
    public final String getSignature() {
        return "getTreeNext()Lcom/intellij/lang/ASTNode;";
    }

    @Override // kotlin.jvm.internal.CallableReference, kotlin.reflect.KCallable
    public final String getName() {
        return "getTreeNext";
    }

    @Override // kotlin.jvm.internal.CallableReference
    public final KDeclarationContainer getOwner() {
        return Reflection.getOrCreateKotlinClass(ASTNode.class);
    }

    @Override // kotlin.jvm.functions.Function1
    public final ASTNode invoke(ASTNode p0) {
        Intrinsics.checkNotNullParameter(p0, "p0");
        return p0.getTreeNext();
    }
}
