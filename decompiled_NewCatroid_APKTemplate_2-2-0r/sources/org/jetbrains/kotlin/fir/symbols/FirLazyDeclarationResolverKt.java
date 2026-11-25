package org.jetbrains.kotlin.fir.symbols;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.PropertyReference1Impl;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KProperty;
import org.eclipse.jgit.lib.Constants;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.declarations.FirClass;
import org.jetbrains.kotlin.fir.declarations.FirDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirResolvePhase;
import org.jetbrains.kotlin.fir.symbols.impl.FirClassSymbol;
import org.jetbrains.kotlin.util.AbstractArrayMapOwner;
import org.jetbrains.kotlin.util.ArrayMapAccessor;
import org.jetbrains.kotlin.util.TypeRegistry;

/* JADX INFO: compiled from: FirLazyDeclarationResolver.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u00000\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\u001a\u0012\u0010\t\u001a\u00020\n*\u00020\u00072\u0006\u0010\u000b\u001a\u00020\f\u001a\u0016\u0010\t\u001a\u00020\n*\u0006\u0012\u0002\b\u00030\r2\u0006\u0010\u000b\u001a\u00020\f\u001a\u0012\u0010\u000e\u001a\u00020\n*\u00020\u000f2\u0006\u0010\u000b\u001a\u00020\f\u001a\u0016\u0010\u000e\u001a\u00020\n*\u0006\u0012\u0002\b\u00030\u00102\u0006\u0010\u000b\u001a\u00020\f\"\u001f\u0010\u0000\u001a\u00020\u0001*\u00020\u00028FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b\u0005\u0010\u0006\u001a\u0004\b\u0003\u0010\u0004\"\u0018\u0010\u0000\u001a\u00020\u0001*\u00020\u00078BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u0003\u0010\b¨\u0006\u0011"}, d2 = {"lazyDeclarationResolver", "Lorg/jetbrains/kotlin/fir/symbols/FirLazyDeclarationResolver;", "Lorg/jetbrains/kotlin/fir/FirSession;", "getLazyDeclarationResolver", "(Lorg/jetbrains/kotlin/fir/FirSession;)Lorg/jetbrains/kotlin/fir/symbols/FirLazyDeclarationResolver;", "lazyDeclarationResolver$delegate", "Lorg/jetbrains/kotlin/util/ArrayMapAccessor;", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclaration;", "(Lorg/jetbrains/kotlin/fir/declarations/FirDeclaration;)Lorg/jetbrains/kotlin/fir/symbols/FirLazyDeclarationResolver;", "lazyResolveToPhase", "", "toPhase", "Lorg/jetbrains/kotlin/fir/declarations/FirResolvePhase;", "Lorg/jetbrains/kotlin/fir/symbols/FirBasedSymbol;", "lazyResolveToPhaseWithCallableMembers", "Lorg/jetbrains/kotlin/fir/declarations/FirClass;", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirClassSymbol;", Constants.TYPE_TREE}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class FirLazyDeclarationResolverKt {
    static final /* synthetic */ KProperty<Object>[] $$delegatedProperties = {Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinPackage(FirLazyDeclarationResolverKt.class, Constants.TYPE_TREE), "lazyDeclarationResolver", "getLazyDeclarationResolver(Lorg/jetbrains/kotlin/fir/FirSession;)Lorg/jetbrains/kotlin/fir/symbols/FirLazyDeclarationResolver;"))};
    private static final ArrayMapAccessor lazyDeclarationResolver$delegate = TypeRegistry.generateAccessor$default(FirSession.INSTANCE, Reflection.getOrCreateKotlinClass(FirLazyDeclarationResolver.class), (Object) null, 2, (Object) null);

    public static final FirLazyDeclarationResolver getLazyDeclarationResolver(FirSession firSession) {
        Intrinsics.checkNotNullParameter(firSession, "<this>");
        return (FirLazyDeclarationResolver) lazyDeclarationResolver$delegate.getValue((AbstractArrayMapOwner) firSession, $$delegatedProperties[0]);
    }

    private static final FirLazyDeclarationResolver getLazyDeclarationResolver(FirDeclaration firDeclaration) {
        return getLazyDeclarationResolver(firDeclaration.getModuleData().getSession());
    }

    public static final void lazyResolveToPhase(FirBasedSymbol<?> firBasedSymbol, FirResolvePhase toPhase) {
        Intrinsics.checkNotNullParameter(firBasedSymbol, "<this>");
        Intrinsics.checkNotNullParameter(toPhase, "toPhase");
        getLazyDeclarationResolver(firBasedSymbol.getFir()).lazyResolveToPhase(firBasedSymbol, toPhase);
    }

    public static final void lazyResolveToPhase(FirDeclaration firDeclaration, FirResolvePhase toPhase) {
        Intrinsics.checkNotNullParameter(firDeclaration, "<this>");
        Intrinsics.checkNotNullParameter(toPhase, "toPhase");
        lazyResolveToPhase(firDeclaration.getSymbol(), toPhase);
    }

    /* JADX WARN: Type inference failed for: r0v2, types: [org.jetbrains.kotlin.fir.declarations.FirDeclaration] */
    public static final void lazyResolveToPhaseWithCallableMembers(FirClassSymbol<?> firClassSymbol, FirResolvePhase toPhase) {
        Intrinsics.checkNotNullParameter(firClassSymbol, "<this>");
        Intrinsics.checkNotNullParameter(toPhase, "toPhase");
        getLazyDeclarationResolver((FirDeclaration) firClassSymbol.getFir()).lazyResolveToPhaseWithCallableMembers(firClassSymbol, toPhase);
    }

    public static final void lazyResolveToPhaseWithCallableMembers(FirClass firClass, FirResolvePhase toPhase) {
        Intrinsics.checkNotNullParameter(firClass, "<this>");
        Intrinsics.checkNotNullParameter(toPhase, "toPhase");
        lazyResolveToPhaseWithCallableMembers(firClass.getSymbol(), toPhase);
    }
}
