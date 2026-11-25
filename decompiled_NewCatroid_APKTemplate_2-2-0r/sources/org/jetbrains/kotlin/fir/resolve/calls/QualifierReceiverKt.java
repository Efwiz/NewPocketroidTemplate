package org.jetbrains.kotlin.fir.resolve.calls;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.declarations.FirClassLikeDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirRegularClass;
import org.jetbrains.kotlin.fir.declarations.FirTypeAlias;
import org.jetbrains.kotlin.fir.declarations.utils.FirDeclarationUtilKt;
import org.jetbrains.kotlin.fir.expressions.FirResolvedQualifier;
import org.jetbrains.kotlin.fir.resolve.LookupTagUtilsKt;
import org.jetbrains.kotlin.fir.resolve.ScopeSession;
import org.jetbrains.kotlin.fir.symbols.ConeClassLikeLookupTag;
import org.jetbrains.kotlin.fir.symbols.impl.FirClassLikeSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirRegularClassSymbol;
import org.jetbrains.kotlin.fir.types.ConeClassLikeType;

/* JADX INFO: compiled from: QualifierReceiver.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000$\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\u001a \u0010\u0000\u001a\u0004\u0018\u00010\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0007\u001a\u0014\u0010\b\u001a\u0004\u0018\u00010\t*\u00020\n2\u0006\u0010\u0004\u001a\u00020\u0005¨\u0006\u000b"}, d2 = {"createQualifierReceiver", "Lorg/jetbrains/kotlin/fir/resolve/calls/QualifierReceiver;", "explicitReceiver", "Lorg/jetbrains/kotlin/fir/expressions/FirResolvedQualifier;", "useSiteSession", "Lorg/jetbrains/kotlin/fir/FirSession;", "scopeSession", "Lorg/jetbrains/kotlin/fir/resolve/ScopeSession;", "fullyExpandedClass", "Lorg/jetbrains/kotlin/fir/declarations/FirRegularClass;", "Lorg/jetbrains/kotlin/fir/declarations/FirClassLikeDeclaration;", "resolve"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class QualifierReceiverKt {
    /* JADX WARN: Multi-variable type inference failed */
    public static final FirRegularClass fullyExpandedClass(FirClassLikeDeclaration firClassLikeDeclaration, FirSession useSiteSession) {
        ConeClassLikeLookupTag lookupTag;
        FirClassLikeSymbol<?> symbol;
        FirClassLikeDeclaration firClassLikeDeclaration2;
        Intrinsics.checkNotNullParameter(firClassLikeDeclaration, "<this>");
        Intrinsics.checkNotNullParameter(useSiteSession, "useSiteSession");
        if (!(firClassLikeDeclaration instanceof FirTypeAlias)) {
            if (firClassLikeDeclaration instanceof FirRegularClass) {
                return (FirRegularClass) firClassLikeDeclaration;
            }
            throw new IllegalStateException(("Not supported: " + firClassLikeDeclaration).toString());
        }
        ConeClassLikeType expandedConeType = FirDeclarationUtilKt.getExpandedConeType((FirTypeAlias) firClassLikeDeclaration);
        if (expandedConeType == null || (lookupTag = expandedConeType.getLookupTag()) == null || (symbol = LookupTagUtilsKt.toSymbol(lookupTag, useSiteSession)) == null || (firClassLikeDeclaration2 = (FirClassLikeDeclaration) symbol.getFir()) == null) {
            return null;
        }
        return fullyExpandedClass(firClassLikeDeclaration2, useSiteSession);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static final QualifierReceiver createQualifierReceiver(FirResolvedQualifier explicitReceiver, FirSession useSiteSession, ScopeSession scopeSession) {
        FirRegularClassSymbol symbol;
        Intrinsics.checkNotNullParameter(explicitReceiver, "explicitReceiver");
        Intrinsics.checkNotNullParameter(useSiteSession, "useSiteSession");
        Intrinsics.checkNotNullParameter(scopeSession, "scopeSession");
        FirClassLikeSymbol<?> symbol2 = explicitReceiver.getSymbol();
        if (symbol2 != null) {
            FirRegularClass firRegularClassFullyExpandedClass = fullyExpandedClass((FirClassLikeDeclaration) symbol2.getFir(), useSiteSession);
            if (firRegularClassFullyExpandedClass == null || (symbol = firRegularClassFullyExpandedClass.getSymbol()) == null) {
                return null;
            }
            return new ClassQualifierReceiver(explicitReceiver, symbol, symbol2, useSiteSession, scopeSession);
        }
        return new PackageQualifierReceiver(explicitReceiver, useSiteSession);
    }
}
