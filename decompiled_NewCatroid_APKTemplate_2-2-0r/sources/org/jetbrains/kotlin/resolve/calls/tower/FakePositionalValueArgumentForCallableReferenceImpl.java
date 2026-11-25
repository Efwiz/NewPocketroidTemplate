package org.jetbrains.kotlin.resolve.calls.tower;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.LeafPsiElement;
import org.jetbrains.kotlin.psi.FakePositionalValueArgumentForCallableReference;
import org.jetbrains.kotlin.psi.KtElement;
import org.jetbrains.kotlin.psi.KtExpression;
import org.jetbrains.kotlin.psi.ValueArgumentName;

/* JADX INFO: compiled from: NewCallArguments.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\b\u0010\t\u001a\u00020\u0003H\u0016J\n\u0010\n\u001a\u0004\u0018\u00010\u000bH\u0016J\n\u0010\f\u001a\u0004\u0018\u00010\rH\u0016J\n\u0010\u000e\u001a\u0004\u0018\u00010\u000fH\u0016J\b\u0010\u0010\u001a\u00020\u0011H\u0016J\b\u0010\u0012\u001a\u00020\u0011H\u0016R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0004\u001a\u00020\u0005X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\b¨\u0006\u0013"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/tower/FakePositionalValueArgumentForCallableReferenceImpl;", "Lorg/jetbrains/kotlin/psi/FakePositionalValueArgumentForCallableReference;", "callElement", "Lorg/jetbrains/kotlin/psi/KtElement;", "index", "", "(Lorg/jetbrains/kotlin/psi/KtElement;I)V", "getIndex", "()I", "asElement", "getArgumentExpression", "Lorg/jetbrains/kotlin/psi/KtExpression;", "getArgumentName", "Lorg/jetbrains/kotlin/psi/ValueArgumentName;", "getSpreadElement", "Lorg/jetbrains/kotlin/com/intellij/psi/impl/source/tree/LeafPsiElement;", "isExternal", "", "isNamed", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FakePositionalValueArgumentForCallableReferenceImpl implements FakePositionalValueArgumentForCallableReference {
    private final KtElement callElement;
    private final int index;

    @Override // org.jetbrains.kotlin.psi.ValueArgument
    public KtExpression getArgumentExpression() {
        return null;
    }

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

    public FakePositionalValueArgumentForCallableReferenceImpl(KtElement callElement, int i) {
        Intrinsics.checkNotNullParameter(callElement, "callElement");
        this.callElement = callElement;
        this.index = i;
    }

    @Override // org.jetbrains.kotlin.psi.FakePositionalValueArgumentForCallableReference
    public int getIndex() {
        return this.index;
    }

    @Override // org.jetbrains.kotlin.psi.ValueArgument
    /* JADX INFO: renamed from: asElement, reason: from getter */
    public KtElement getCallElement() {
        return this.callElement;
    }
}
