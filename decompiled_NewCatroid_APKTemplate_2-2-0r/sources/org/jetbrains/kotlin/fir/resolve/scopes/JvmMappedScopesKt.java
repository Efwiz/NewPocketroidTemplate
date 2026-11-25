package org.jetbrains.kotlin.fir.resolve.scopes;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jdt.internal.compiler.util.SuffixConstants;
import org.jetbrains.kotlin.builtins.jvm.JavaToKotlinClassMap;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.declarations.FirClass;
import org.jetbrains.kotlin.fir.declarations.FirClassLikeDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirRegularClass;
import org.jetbrains.kotlin.fir.declarations.FirResolvePhase;
import org.jetbrains.kotlin.fir.declarations.utils.FirDeclarationUtilKt;
import org.jetbrains.kotlin.fir.resolve.ScopeSession;
import org.jetbrains.kotlin.fir.resolve.providers.FirSymbolProviderKt;
import org.jetbrains.kotlin.fir.scopes.FirContainingNamesAwareScope;
import org.jetbrains.kotlin.fir.scopes.FirKotlinScopeProviderKt;
import org.jetbrains.kotlin.fir.scopes.jvm.JvmMappedScope;
import org.jetbrains.kotlin.fir.symbols.impl.FirClassLikeSymbol;
import org.jetbrains.kotlin.fir.types.FunctionalTypeUtilsKt;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.name.FqNameUnsafe;
import org.jetbrains.kotlin.name.StandardClassIds;

/* JADX INFO: compiled from: JvmMappedScopes.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\"\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a0\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00012\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\b\u0010\t\u001a\u0004\u0018\u00010\n¨\u0006\u000b"}, d2 = {"wrapScopeWithJvmMapped", "Lorg/jetbrains/kotlin/fir/scopes/FirContainingNamesAwareScope;", "klass", "Lorg/jetbrains/kotlin/fir/declarations/FirClass;", "declaredMemberScope", "useSiteSession", "Lorg/jetbrains/kotlin/fir/FirSession;", "scopeSession", "Lorg/jetbrains/kotlin/fir/resolve/ScopeSession;", "memberRequiredPhase", "Lorg/jetbrains/kotlin/fir/declarations/FirResolvePhase;", SuffixConstants.EXTENSION_java}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class JvmMappedScopesKt {
    /* JADX WARN: Multi-variable type inference failed */
    public static final FirContainingNamesAwareScope wrapScopeWithJvmMapped(FirClass klass, FirContainingNamesAwareScope declaredMemberScope, FirSession useSiteSession, ScopeSession scopeSession, FirResolvePhase firResolvePhase) {
        Intrinsics.checkNotNullParameter(klass, "klass");
        Intrinsics.checkNotNullParameter(declaredMemberScope, "declaredMemberScope");
        Intrinsics.checkNotNullParameter(useSiteSession, "useSiteSession");
        Intrinsics.checkNotNullParameter(scopeSession, "scopeSession");
        if (!(klass instanceof FirRegularClass)) {
            return declaredMemberScope;
        }
        ClassId classId = FirDeclarationUtilKt.getClassId(klass);
        FqNameUnsafe unsafe = classId.asSingleFqName().toUnsafe();
        Intrinsics.checkNotNullExpressionValue(unsafe, "toUnsafe(...)");
        ClassId classIdMapKotlinToJava = JavaToKotlinClassMap.INSTANCE.mapKotlinToJava(unsafe);
        if (classIdMapKotlinToJava == null) {
            return declaredMemberScope;
        }
        FirClassLikeSymbol<?> classLikeSymbolByClassId = FirSymbolProviderKt.getSymbolProvider(useSiteSession).getClassLikeSymbolByClassId(classIdMapKotlinToJava);
        FirClassLikeDeclaration firClassLikeDeclaration = classLikeSymbolByClassId != null ? (FirClassLikeDeclaration) classLikeSymbolByClassId.getFir() : null;
        FirRegularClass firRegularClass = firClassLikeDeclaration instanceof FirRegularClass ? (FirRegularClass) firClassLikeDeclaration : null;
        if (firRegularClass == null) {
            return declaredMemberScope;
        }
        FirRegularClass firRegularClass2 = (FirRegularClass) klass;
        return (FunctionalTypeUtilsKt.isSomeFunctionType(firRegularClass2.getSymbol().getLookupTag(), useSiteSession) || Intrinsics.areEqual(classId, StandardClassIds.INSTANCE.getAny())) ? declaredMemberScope : new JvmMappedScope(useSiteSession, firRegularClass2, firRegularClass, declaredMemberScope, FirKotlinScopeProviderKt.unsubstitutedScope((FirClass) firRegularClass, useSiteSession, scopeSession, false, firResolvePhase));
    }
}
