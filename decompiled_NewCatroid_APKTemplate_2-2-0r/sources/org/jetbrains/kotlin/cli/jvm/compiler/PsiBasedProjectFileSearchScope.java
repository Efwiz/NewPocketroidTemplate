package org.jetbrains.kotlin.cli.jvm.compiler;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.intellij.psi.search.GlobalSearchScope;
import org.jetbrains.kotlin.fir.session.environment.AbstractProjectFileSearchScope;

/* JADX INFO: compiled from: VfsBasedProjectEnvironment.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\b\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0011\u0010\n\u001a\u00020\u00012\u0006\u0010\u000b\u001a\u00020\u0001H\u0096\u0002J\t\u0010\f\u001a\u00020\u0001H\u0096\u0002J\u0011\u0010\r\u001a\u00020\u00012\u0006\u0010\u000b\u001a\u00020\u0001H\u0096\u0002R\u0014\u0010\u0005\u001a\u00020\u00068VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0005\u0010\u0007R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\t¨\u0006\u000e"}, d2 = {"Lorg/jetbrains/kotlin/cli/jvm/compiler/PsiBasedProjectFileSearchScope;", "Lorg/jetbrains/kotlin/fir/session/environment/AbstractProjectFileSearchScope;", "psiSearchScope", "Lorg/jetbrains/kotlin/com/intellij/psi/search/GlobalSearchScope;", "(Lcom/intellij/psi/search/GlobalSearchScope;)V", "isEmpty", "", "()Z", "getPsiSearchScope", "()Lcom/intellij/psi/search/GlobalSearchScope;", "minus", "other", "not", "plus", "cli"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class PsiBasedProjectFileSearchScope implements AbstractProjectFileSearchScope {
    private final GlobalSearchScope psiSearchScope;

    public PsiBasedProjectFileSearchScope(GlobalSearchScope psiSearchScope) {
        Intrinsics.checkNotNullParameter(psiSearchScope, "psiSearchScope");
        this.psiSearchScope = psiSearchScope;
    }

    public final GlobalSearchScope getPsiSearchScope() {
        return this.psiSearchScope;
    }

    @Override // org.jetbrains.kotlin.fir.session.environment.AbstractProjectFileSearchScope
    public boolean isEmpty() {
        return Intrinsics.areEqual(this.psiSearchScope, GlobalSearchScope.EMPTY_SCOPE);
    }

    @Override // org.jetbrains.kotlin.fir.session.environment.AbstractProjectFileSearchScope
    public AbstractProjectFileSearchScope minus(AbstractProjectFileSearchScope other) {
        Intrinsics.checkNotNullParameter(other, "other");
        GlobalSearchScope globalSearchScopeIntersectWith = this.psiSearchScope.intersectWith(GlobalSearchScope.notScope(VfsBasedProjectEnvironmentKt.asPsiSearchScope(other)));
        Intrinsics.checkNotNullExpressionValue(globalSearchScopeIntersectWith, "intersectWith(...)");
        return new PsiBasedProjectFileSearchScope(globalSearchScopeIntersectWith);
    }

    @Override // org.jetbrains.kotlin.fir.session.environment.AbstractProjectFileSearchScope
    public AbstractProjectFileSearchScope plus(AbstractProjectFileSearchScope other) {
        Intrinsics.checkNotNullParameter(other, "other");
        GlobalSearchScope globalSearchScopeUniteWith = this.psiSearchScope.uniteWith(VfsBasedProjectEnvironmentKt.asPsiSearchScope(other));
        Intrinsics.checkNotNullExpressionValue(globalSearchScopeUniteWith, "uniteWith(...)");
        return new PsiBasedProjectFileSearchScope(globalSearchScopeUniteWith);
    }

    @Override // org.jetbrains.kotlin.fir.session.environment.AbstractProjectFileSearchScope
    public AbstractProjectFileSearchScope not() {
        GlobalSearchScope globalSearchScopeNotScope = GlobalSearchScope.notScope(this.psiSearchScope);
        Intrinsics.checkNotNullExpressionValue(globalSearchScopeNotScope, "notScope(...)");
        return new PsiBasedProjectFileSearchScope(globalSearchScopeNotScope);
    }
}
