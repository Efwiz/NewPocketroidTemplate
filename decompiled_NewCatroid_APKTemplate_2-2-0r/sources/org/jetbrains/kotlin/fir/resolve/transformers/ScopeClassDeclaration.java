package org.jetbrains.kotlin.fir.resolve.transformers;

import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.fir.declarations.FirDeclaration;
import org.jetbrains.kotlin.fir.scopes.FirScope;
import org.jetbrains.kotlin.js.translate.context.Namer;

/* JADX INFO: compiled from: ScopeClassDeclaration.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u001c\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0010\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B9\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u0012\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006\u0012\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\u0007\u0012\n\b\u0002\u0010\t\u001a\u0004\u0018\u00010\u0007¢\u0006\u0002\u0010\nJ\u000f\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003HÆ\u0003J\u000f\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006HÆ\u0003J\u000b\u0010\u0014\u001a\u0004\u0018\u00010\u0007HÆ\u0003J\u000b\u0010\u0015\u001a\u0004\u0018\u00010\u0007HÆ\u0003JA\u0010\u0016\u001a\u00020\u00002\u000e\b\u0002\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\u000e\b\u0002\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u00062\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\u00072\n\b\u0002\u0010\t\u001a\u0004\u0018\u00010\u0007HÆ\u0001J\u0013\u0010\u0017\u001a\u00020\u00182\b\u0010\u0019\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u001a\u001a\u00020\u001bHÖ\u0001J\t\u0010\u001c\u001a\u00020\u001dHÖ\u0001R\u0013\u0010\t\u001a\u0004\u0018\u00010\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0017\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u0017\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u0013\u0010\b\u001a\u0004\u0018\u00010\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\f¨\u0006\u001e"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/transformers/ScopeClassDeclaration;", "", "scopes", "", "Lorg/jetbrains/kotlin/fir/scopes/FirScope;", "containingDeclarations", "", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclaration;", "topContainer", "containerDeclaration", "(Ljava/lang/Iterable;Ljava/util/List;Lorg/jetbrains/kotlin/fir/declarations/FirDeclaration;Lorg/jetbrains/kotlin/fir/declarations/FirDeclaration;)V", "getContainerDeclaration", "()Lorg/jetbrains/kotlin/fir/declarations/FirDeclaration;", "getContainingDeclarations", "()Ljava/util/List;", "getScopes", "()Ljava/lang/Iterable;", "getTopContainer", "component1", "component2", "component3", "component4", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "hashCode", "", "toString", "", "resolve"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final /* data */ class ScopeClassDeclaration {
    private final FirDeclaration containerDeclaration;
    private final List<FirDeclaration> containingDeclarations;
    private final Iterable<FirScope> scopes;
    private final FirDeclaration topContainer;

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ ScopeClassDeclaration copy$default(ScopeClassDeclaration scopeClassDeclaration, Iterable iterable, List list, FirDeclaration firDeclaration, FirDeclaration firDeclaration2, int i, Object obj) {
        if ((i & 1) != 0) {
            iterable = scopeClassDeclaration.scopes;
        }
        if ((i & 2) != 0) {
            list = scopeClassDeclaration.containingDeclarations;
        }
        if ((i & 4) != 0) {
            firDeclaration = scopeClassDeclaration.topContainer;
        }
        if ((i & 8) != 0) {
            firDeclaration2 = scopeClassDeclaration.containerDeclaration;
        }
        return scopeClassDeclaration.copy(iterable, list, firDeclaration, firDeclaration2);
    }

    public final Iterable<FirScope> component1() {
        return this.scopes;
    }

    public final List<FirDeclaration> component2() {
        return this.containingDeclarations;
    }

    /* JADX INFO: renamed from: component3, reason: from getter */
    public final FirDeclaration getTopContainer() {
        return this.topContainer;
    }

    /* JADX INFO: renamed from: component4, reason: from getter */
    public final FirDeclaration getContainerDeclaration() {
        return this.containerDeclaration;
    }

    public final ScopeClassDeclaration copy(Iterable<? extends FirScope> scopes, List<? extends FirDeclaration> containingDeclarations, FirDeclaration topContainer, FirDeclaration containerDeclaration) {
        Intrinsics.checkNotNullParameter(scopes, "scopes");
        Intrinsics.checkNotNullParameter(containingDeclarations, "containingDeclarations");
        return new ScopeClassDeclaration(scopes, containingDeclarations, topContainer, containerDeclaration);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof ScopeClassDeclaration)) {
            return false;
        }
        ScopeClassDeclaration scopeClassDeclaration = (ScopeClassDeclaration) other;
        return Intrinsics.areEqual(this.scopes, scopeClassDeclaration.scopes) && Intrinsics.areEqual(this.containingDeclarations, scopeClassDeclaration.containingDeclarations) && Intrinsics.areEqual(this.topContainer, scopeClassDeclaration.topContainer) && Intrinsics.areEqual(this.containerDeclaration, scopeClassDeclaration.containerDeclaration);
    }

    public int hashCode() {
        int iHashCode = ((this.scopes.hashCode() * 31) + this.containingDeclarations.hashCode()) * 31;
        FirDeclaration firDeclaration = this.topContainer;
        int iHashCode2 = (iHashCode + (firDeclaration == null ? 0 : firDeclaration.hashCode())) * 31;
        FirDeclaration firDeclaration2 = this.containerDeclaration;
        return iHashCode2 + (firDeclaration2 != null ? firDeclaration2.hashCode() : 0);
    }

    public String toString() {
        return "ScopeClassDeclaration(scopes=" + this.scopes + ", containingDeclarations=" + this.containingDeclarations + ", topContainer=" + this.topContainer + ", containerDeclaration=" + this.containerDeclaration + ')';
    }

    /* JADX WARN: Multi-variable type inference failed */
    public ScopeClassDeclaration(Iterable<? extends FirScope> scopes, List<? extends FirDeclaration> containingDeclarations, FirDeclaration firDeclaration, FirDeclaration firDeclaration2) {
        Intrinsics.checkNotNullParameter(scopes, "scopes");
        Intrinsics.checkNotNullParameter(containingDeclarations, "containingDeclarations");
        this.scopes = scopes;
        this.containingDeclarations = containingDeclarations;
        this.topContainer = firDeclaration;
        this.containerDeclaration = firDeclaration2;
    }

    public /* synthetic */ ScopeClassDeclaration(Iterable iterable, List list, FirDeclaration firDeclaration, FirDeclaration firDeclaration2, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(iterable, list, (i & 4) != 0 ? null : firDeclaration, (i & 8) != 0 ? null : firDeclaration2);
    }

    public final Iterable<FirScope> getScopes() {
        return this.scopes;
    }

    public final List<FirDeclaration> getContainingDeclarations() {
        return this.containingDeclarations;
    }

    public final FirDeclaration getTopContainer() {
        return this.topContainer;
    }

    public final FirDeclaration getContainerDeclaration() {
        return this.containerDeclaration;
    }
}
