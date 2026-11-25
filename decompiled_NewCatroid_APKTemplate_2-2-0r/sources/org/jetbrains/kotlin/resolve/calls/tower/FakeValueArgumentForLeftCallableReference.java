package org.jetbrains.kotlin.resolve.calls.tower;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.LeafPsiElement;
import org.jetbrains.kotlin.psi.KtCallableReferenceExpression;
import org.jetbrains.kotlin.psi.KtElement;
import org.jetbrains.kotlin.psi.KtExpression;
import org.jetbrains.kotlin.psi.ValueArgument;
import org.jetbrains.kotlin.psi.ValueArgumentName;

/* JADX INFO: compiled from: NewCallArguments.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\b\u0010\u0007\u001a\u00020\bH\u0016J\n\u0010\t\u001a\u0004\u0018\u00010\nH\u0016J\n\u0010\u000b\u001a\u0004\u0018\u00010\fH\u0016J\n\u0010\r\u001a\u0004\u0018\u00010\u000eH\u0016J\b\u0010\u000f\u001a\u00020\u0010H\u0016J\b\u0010\u0011\u001a\u00020\u0010H\u0016R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0012"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/tower/FakeValueArgumentForLeftCallableReference;", "Lorg/jetbrains/kotlin/psi/ValueArgument;", "ktExpression", "Lorg/jetbrains/kotlin/psi/KtCallableReferenceExpression;", "(Lorg/jetbrains/kotlin/psi/KtCallableReferenceExpression;)V", "getKtExpression", "()Lorg/jetbrains/kotlin/psi/KtCallableReferenceExpression;", "asElement", "Lorg/jetbrains/kotlin/psi/KtElement;", "getArgumentExpression", "Lorg/jetbrains/kotlin/psi/KtExpression;", "getArgumentName", "Lorg/jetbrains/kotlin/psi/ValueArgumentName;", "getSpreadElement", "Lorg/jetbrains/kotlin/com/intellij/psi/impl/source/tree/LeafPsiElement;", "isExternal", "", "isNamed", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FakeValueArgumentForLeftCallableReference implements ValueArgument {
    private final KtCallableReferenceExpression ktExpression;

    @Override // org.jetbrains.kotlin.psi.ValueArgument
    public ValueArgumentName getArgumentName() {
        return null;
    }

    @Override // org.jetbrains.kotlin.psi.ValueArgument
    public LeafPsiElement getSpreadElement() {
        return null;
    }

    @Override // org.jetbrains.kotlin.psi.ValueArgument
    public boolean isExternal() {
        return false;
    }

    @Override // org.jetbrains.kotlin.psi.ValueArgument
    public boolean isNamed() {
        return false;
    }

    public FakeValueArgumentForLeftCallableReference(KtCallableReferenceExpression ktExpression) {
        Intrinsics.checkNotNullParameter(ktExpression, "ktExpression");
        this.ktExpression = ktExpression;
    }

    public final KtCallableReferenceExpression getKtExpression() {
        return this.ktExpression;
    }

    @Override // org.jetbrains.kotlin.psi.ValueArgument
    public KtExpression getArgumentExpression() {
        return this.ktExpression.getReceiverExpression();
    }

    @Override // org.jetbrains.kotlin.psi.ValueArgument
    /* JADX INFO: renamed from: asElement */
    public KtElement getCallElement() {
        KtExpression argumentExpression = getArgumentExpression();
        if (argumentExpression == null) {
            argumentExpression = this.ktExpression;
        }
        return argumentExpression;
    }
}
