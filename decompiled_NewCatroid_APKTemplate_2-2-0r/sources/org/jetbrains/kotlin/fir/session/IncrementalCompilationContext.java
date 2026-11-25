package org.jetbrains.kotlin.fir.session;

import java.util.Collection;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.fir.resolve.providers.FirSymbolProvider;
import org.jetbrains.kotlin.fir.session.environment.AbstractProjectFileSearchScope;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.load.kotlin.PackagePartProvider;

/* JADX INFO: compiled from: IncrementalCompilationContext.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\f\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B'\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u0012\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006\u0012\b\u0010\u0007\u001a\u0004\u0018\u00010\b¢\u0006\u0002\u0010\tJ\u000f\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003HÆ\u0003J\u000b\u0010\u0011\u001a\u0004\u0018\u00010\u0006HÆ\u0003J\u000b\u0010\u0012\u001a\u0004\u0018\u00010\bHÆ\u0003J1\u0010\u0013\u001a\u00020\u00002\u000e\b\u0002\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u00062\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\bHÆ\u0001J\u0013\u0010\u0014\u001a\u00020\u00152\b\u0010\u0016\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0017\u001a\u00020\u0018HÖ\u0001J\t\u0010\u0019\u001a\u00020\u001aHÖ\u0001R\u0013\u0010\u0007\u001a\u0004\u0018\u00010\b¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0013\u0010\u0005\u001a\u0004\u0018\u00010\u0006¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0017\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000f¨\u0006\u001b"}, d2 = {"Lorg/jetbrains/kotlin/fir/session/IncrementalCompilationContext;", "", "previousFirSessionsSymbolProviders", "", "Lorg/jetbrains/kotlin/fir/resolve/providers/FirSymbolProvider;", "precompiledBinariesPackagePartProvider", "Lorg/jetbrains/kotlin/load/kotlin/PackagePartProvider;", "precompiledBinariesFileScope", "Lorg/jetbrains/kotlin/fir/session/environment/AbstractProjectFileSearchScope;", "(Ljava/util/Collection;Lorg/jetbrains/kotlin/load/kotlin/PackagePartProvider;Lorg/jetbrains/kotlin/fir/session/environment/AbstractProjectFileSearchScope;)V", "getPrecompiledBinariesFileScope", "()Lorg/jetbrains/kotlin/fir/session/environment/AbstractProjectFileSearchScope;", "getPrecompiledBinariesPackagePartProvider", "()Lorg/jetbrains/kotlin/load/kotlin/PackagePartProvider;", "getPreviousFirSessionsSymbolProviders", "()Ljava/util/Collection;", "component1", "component2", "component3", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "hashCode", "", "toString", "", "entrypoint"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final /* data */ class IncrementalCompilationContext {
    private final AbstractProjectFileSearchScope precompiledBinariesFileScope;
    private final PackagePartProvider precompiledBinariesPackagePartProvider;
    private final Collection<FirSymbolProvider> previousFirSessionsSymbolProviders;

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ IncrementalCompilationContext copy$default(IncrementalCompilationContext incrementalCompilationContext, Collection collection, PackagePartProvider packagePartProvider, AbstractProjectFileSearchScope abstractProjectFileSearchScope, int i, Object obj) {
        if ((i & 1) != 0) {
            collection = incrementalCompilationContext.previousFirSessionsSymbolProviders;
        }
        if ((i & 2) != 0) {
            packagePartProvider = incrementalCompilationContext.precompiledBinariesPackagePartProvider;
        }
        if ((i & 4) != 0) {
            abstractProjectFileSearchScope = incrementalCompilationContext.precompiledBinariesFileScope;
        }
        return incrementalCompilationContext.copy(collection, packagePartProvider, abstractProjectFileSearchScope);
    }

    public final Collection<FirSymbolProvider> component1() {
        return this.previousFirSessionsSymbolProviders;
    }

    /* JADX INFO: renamed from: component2, reason: from getter */
    public final PackagePartProvider getPrecompiledBinariesPackagePartProvider() {
        return this.precompiledBinariesPackagePartProvider;
    }

    /* JADX INFO: renamed from: component3, reason: from getter */
    public final AbstractProjectFileSearchScope getPrecompiledBinariesFileScope() {
        return this.precompiledBinariesFileScope;
    }

    public final IncrementalCompilationContext copy(Collection<? extends FirSymbolProvider> previousFirSessionsSymbolProviders, PackagePartProvider precompiledBinariesPackagePartProvider, AbstractProjectFileSearchScope precompiledBinariesFileScope) {
        Intrinsics.checkNotNullParameter(previousFirSessionsSymbolProviders, "previousFirSessionsSymbolProviders");
        return new IncrementalCompilationContext(previousFirSessionsSymbolProviders, precompiledBinariesPackagePartProvider, precompiledBinariesFileScope);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof IncrementalCompilationContext)) {
            return false;
        }
        IncrementalCompilationContext incrementalCompilationContext = (IncrementalCompilationContext) other;
        return Intrinsics.areEqual(this.previousFirSessionsSymbolProviders, incrementalCompilationContext.previousFirSessionsSymbolProviders) && Intrinsics.areEqual(this.precompiledBinariesPackagePartProvider, incrementalCompilationContext.precompiledBinariesPackagePartProvider) && Intrinsics.areEqual(this.precompiledBinariesFileScope, incrementalCompilationContext.precompiledBinariesFileScope);
    }

    public int hashCode() {
        int iHashCode = this.previousFirSessionsSymbolProviders.hashCode() * 31;
        PackagePartProvider packagePartProvider = this.precompiledBinariesPackagePartProvider;
        int iHashCode2 = (iHashCode + (packagePartProvider == null ? 0 : packagePartProvider.hashCode())) * 31;
        AbstractProjectFileSearchScope abstractProjectFileSearchScope = this.precompiledBinariesFileScope;
        return iHashCode2 + (abstractProjectFileSearchScope != null ? abstractProjectFileSearchScope.hashCode() : 0);
    }

    public String toString() {
        return "IncrementalCompilationContext(previousFirSessionsSymbolProviders=" + this.previousFirSessionsSymbolProviders + ", precompiledBinariesPackagePartProvider=" + this.precompiledBinariesPackagePartProvider + ", precompiledBinariesFileScope=" + this.precompiledBinariesFileScope + ')';
    }

    /* JADX WARN: Multi-variable type inference failed */
    public IncrementalCompilationContext(Collection<? extends FirSymbolProvider> previousFirSessionsSymbolProviders, PackagePartProvider packagePartProvider, AbstractProjectFileSearchScope abstractProjectFileSearchScope) {
        Intrinsics.checkNotNullParameter(previousFirSessionsSymbolProviders, "previousFirSessionsSymbolProviders");
        this.previousFirSessionsSymbolProviders = previousFirSessionsSymbolProviders;
        this.precompiledBinariesPackagePartProvider = packagePartProvider;
        this.precompiledBinariesFileScope = abstractProjectFileSearchScope;
    }

    public final Collection<FirSymbolProvider> getPreviousFirSessionsSymbolProviders() {
        return this.previousFirSessionsSymbolProviders;
    }

    public final PackagePartProvider getPrecompiledBinariesPackagePartProvider() {
        return this.precompiledBinariesPackagePartProvider;
    }

    public final AbstractProjectFileSearchScope getPrecompiledBinariesFileScope() {
        return this.precompiledBinariesFileScope;
    }
}
