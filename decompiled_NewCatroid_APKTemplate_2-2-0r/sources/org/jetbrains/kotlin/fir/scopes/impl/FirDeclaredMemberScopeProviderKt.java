package org.jetbrains.kotlin.fir.scopes.impl;

import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.PropertyReference1Impl;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KProperty;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.declarations.FirClass;
import org.jetbrains.kotlin.fir.declarations.FirResolvePhase;
import org.jetbrains.kotlin.fir.resolve.providers.FirSymbolProvider;
import org.jetbrains.kotlin.fir.scopes.FirContainingNamesAwareScope;
import org.jetbrains.kotlin.fir.symbols.impl.FirClassSymbol;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.util.AbstractArrayMapOwner;
import org.jetbrains.kotlin.util.ArrayMapAccessor;
import org.jetbrains.kotlin.util.TypeRegistry;

/* JADX INFO: compiled from: FirDeclaredMemberScopeProvider.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000H\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\u001a&\u0010\u0007\u001a\u0004\u0018\u00010\b2\u0006\u0010\t\u001a\u00020\n2\f\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\r0\f2\u0006\u0010\u000e\u001a\u00020\u000f\u001a\u001c\u0010\u0010\u001a\u00020\u0011*\u00020\u00022\u0006\u0010\u0012\u001a\u00020\u00132\b\u0010\u0014\u001a\u0004\u0018\u00010\u0015\u001a \u0010\u0010\u001a\u00020\u0011*\u00020\u00022\n\u0010\u0012\u001a\u0006\u0012\u0002\b\u00030\u00162\b\u0010\u0014\u001a\u0004\u0018\u00010\u0015\u001a \u0010\u0010\u001a\u00020\u0011*\u0006\u0012\u0002\b\u00030\u00162\u0006\u0010\u0017\u001a\u00020\u00022\b\u0010\u0014\u001a\u0004\u0018\u00010\u0015\u001a(\u0010\u0018\u001a\u00020\u0011*\u00020\u00022\u0006\u0010\u0012\u001a\u00020\u00132\f\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\r0\f2\u0006\u0010\u000e\u001a\u00020\u000f\u001a\u0014\u0010\u0019\u001a\u0004\u0018\u00010\u001a*\u00020\u00022\u0006\u0010\u0012\u001a\u00020\u0013\"\u001f\u0010\u0000\u001a\u00020\u0001*\u00020\u00028FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b\u0005\u0010\u0006\u001a\u0004\b\u0003\u0010\u0004¨\u0006\u001b"}, d2 = {"declaredMemberScopeProvider", "Lorg/jetbrains/kotlin/fir/scopes/impl/FirDeclaredMemberScopeProvider;", "Lorg/jetbrains/kotlin/fir/FirSession;", "getDeclaredMemberScopeProvider", "(Lorg/jetbrains/kotlin/fir/FirSession;)Lorg/jetbrains/kotlin/fir/scopes/impl/FirDeclaredMemberScopeProvider;", "declaredMemberScopeProvider$delegate", "Lorg/jetbrains/kotlin/util/ArrayMapAccessor;", "lazyNestedClassifierScope", "Lorg/jetbrains/kotlin/fir/scopes/impl/FirLazyNestedClassifierScope;", "classId", "Lorg/jetbrains/kotlin/name/ClassId;", "existingNames", "", "Lorg/jetbrains/kotlin/name/Name;", "symbolProvider", "Lorg/jetbrains/kotlin/fir/resolve/providers/FirSymbolProvider;", "declaredMemberScope", "Lorg/jetbrains/kotlin/fir/scopes/FirContainingNamesAwareScope;", "klass", "Lorg/jetbrains/kotlin/fir/declarations/FirClass;", "memberRequiredPhase", "Lorg/jetbrains/kotlin/fir/declarations/FirResolvePhase;", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirClassSymbol;", "session", "declaredMemberScopeWithLazyNestedScope", "nestedClassifierScope", "Lorg/jetbrains/kotlin/fir/scopes/impl/FirNestedClassifierScope;", "providers"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class FirDeclaredMemberScopeProviderKt {
    static final /* synthetic */ KProperty<Object>[] $$delegatedProperties = {Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinPackage(FirDeclaredMemberScopeProviderKt.class, "providers"), "declaredMemberScopeProvider", "getDeclaredMemberScopeProvider(Lorg/jetbrains/kotlin/fir/FirSession;)Lorg/jetbrains/kotlin/fir/scopes/impl/FirDeclaredMemberScopeProvider;"))};
    private static final ArrayMapAccessor declaredMemberScopeProvider$delegate = TypeRegistry.generateAccessor$default(FirSession.INSTANCE, Reflection.getOrCreateKotlinClass(FirDeclaredMemberScopeProvider.class), (Object) null, 2, (Object) null);

    public static final FirContainingNamesAwareScope declaredMemberScope(FirSession firSession, FirClass klass, FirResolvePhase firResolvePhase) {
        Intrinsics.checkNotNullParameter(firSession, "<this>");
        Intrinsics.checkNotNullParameter(klass, "klass");
        return getDeclaredMemberScopeProvider(firSession).declaredMemberScope(klass, false, null, null, firResolvePhase);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static final FirContainingNamesAwareScope declaredMemberScope(FirSession firSession, FirClassSymbol<?> klass, FirResolvePhase firResolvePhase) {
        Intrinsics.checkNotNullParameter(firSession, "<this>");
        Intrinsics.checkNotNullParameter(klass, "klass");
        return declaredMemberScope(firSession, (FirClass) klass.getFir(), firResolvePhase);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static final FirContainingNamesAwareScope declaredMemberScope(FirClassSymbol<?> firClassSymbol, FirSession session, FirResolvePhase firResolvePhase) {
        Intrinsics.checkNotNullParameter(firClassSymbol, "<this>");
        Intrinsics.checkNotNullParameter(session, "session");
        return declaredMemberScope(session, (FirClass) firClassSymbol.getFir(), firResolvePhase);
    }

    public static final FirContainingNamesAwareScope declaredMemberScopeWithLazyNestedScope(FirSession firSession, FirClass klass, List<Name> existingNames, FirSymbolProvider symbolProvider) {
        Intrinsics.checkNotNullParameter(firSession, "<this>");
        Intrinsics.checkNotNullParameter(klass, "klass");
        Intrinsics.checkNotNullParameter(existingNames, "existingNames");
        Intrinsics.checkNotNullParameter(symbolProvider, "symbolProvider");
        return getDeclaredMemberScopeProvider(firSession).declaredMemberScope(klass, true, existingNames, symbolProvider, null);
    }

    public static final FirNestedClassifierScope nestedClassifierScope(FirSession firSession, FirClass klass) {
        Intrinsics.checkNotNullParameter(firSession, "<this>");
        Intrinsics.checkNotNullParameter(klass, "klass");
        return getDeclaredMemberScopeProvider(firSession).nestedClassifierScope(klass);
    }

    public static final FirLazyNestedClassifierScope lazyNestedClassifierScope(ClassId classId, List<Name> existingNames, FirSymbolProvider symbolProvider) {
        Intrinsics.checkNotNullParameter(classId, "classId");
        Intrinsics.checkNotNullParameter(existingNames, "existingNames");
        Intrinsics.checkNotNullParameter(symbolProvider, "symbolProvider");
        if (existingNames.isEmpty()) {
            return null;
        }
        return new FirLazyNestedClassifierScope(classId, existingNames, symbolProvider);
    }

    public static final FirDeclaredMemberScopeProvider getDeclaredMemberScopeProvider(FirSession firSession) {
        Intrinsics.checkNotNullParameter(firSession, "<this>");
        return (FirDeclaredMemberScopeProvider) declaredMemberScopeProvider$delegate.getValue((AbstractArrayMapOwner) firSession, $$delegatedProperties[0]);
    }
}
