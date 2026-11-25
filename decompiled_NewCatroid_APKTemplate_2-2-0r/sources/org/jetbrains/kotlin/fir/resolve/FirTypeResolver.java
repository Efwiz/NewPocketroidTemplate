package org.jetbrains.kotlin.fir.resolve;

import kotlin.Metadata;
import org.jetbrains.kotlin.fir.FirSessionComponent;
import org.jetbrains.kotlin.fir.declarations.FirFile;
import org.jetbrains.kotlin.fir.resolve.transformers.ScopeClassDeclaration;
import org.jetbrains.kotlin.fir.types.FirTypeRef;

/* JADX INFO: compiled from: FirTypeResolver.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b&\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002JB\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\n2\u0006\u0010\f\u001a\u00020\n2\b\u0010\r\u001a\u0004\u0018\u00010\u000e2\u0006\u0010\u000f\u001a\u00020\u0010H&¨\u0006\u0011"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/FirTypeResolver;", "Lorg/jetbrains/kotlin/fir/FirSessionComponent;", "()V", "resolveType", "Lorg/jetbrains/kotlin/fir/resolve/FirTypeResolutionResult;", "typeRef", "Lorg/jetbrains/kotlin/fir/types/FirTypeRef;", "scopeClassDeclaration", "Lorg/jetbrains/kotlin/fir/resolve/transformers/ScopeClassDeclaration;", "areBareTypesAllowed", "", "isOperandOfIsOperator", "resolveDeprecations", "useSiteFile", "Lorg/jetbrains/kotlin/fir/declarations/FirFile;", "supertypeSupplier", "Lorg/jetbrains/kotlin/fir/resolve/SupertypeSupplier;", "resolve"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class FirTypeResolver implements FirSessionComponent {
    public abstract FirTypeResolutionResult resolveType(FirTypeRef typeRef, ScopeClassDeclaration scopeClassDeclaration, boolean areBareTypesAllowed, boolean isOperandOfIsOperator, boolean resolveDeprecations, FirFile useSiteFile, SupertypeSupplier supertypeSupplier);
}
