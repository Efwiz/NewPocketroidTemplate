package org.jetbrains.kotlin.fir.scopes.impl;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirConstructor;
import org.jetbrains.kotlin.fir.declarations.FirDeclarationOrigin;
import org.jetbrains.kotlin.fir.declarations.FirField;
import org.jetbrains.kotlin.fir.declarations.FirProperty;
import org.jetbrains.kotlin.fir.declarations.FirSimpleFunction;
import org.jetbrains.kotlin.fir.scopes.FirOverrideChecker;
import org.jetbrains.kotlin.fir.symbols.impl.FirCallableSymbol;
import org.jetbrains.kotlin.fir.utils.exceptions.FirExceptionUtilsKt;
import org.jetbrains.kotlin.utils.exceptions.ExceptionAttachmentBuilder;
import org.jetbrains.kotlin.utils.exceptions.KotlinIllegalArgumentExceptionWithAttachments;

/* JADX INFO: compiled from: AbstractFirOverrideScope.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u0018\n\u0000\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a\u001c\u0010\u0000\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0004H\u0000\u001a\"\u0010\u0000\u001a\u00020\u0001*\u00020\u00022\n\u0010\u0003\u001a\u0006\u0012\u0002\b\u00030\u00062\n\u0010\u0005\u001a\u0006\u0012\u0002\b\u00030\u0006¨\u0006\u0007"}, d2 = {"similarFunctionsOrBothProperties", "", "Lorg/jetbrains/kotlin/fir/scopes/FirOverrideChecker;", "overrideCandidate", "Lorg/jetbrains/kotlin/fir/declarations/FirCallableDeclaration;", "baseDeclaration", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirCallableSymbol;", "providers"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class AbstractFirOverrideScopeKt {
    public static final boolean similarFunctionsOrBothProperties(FirOverrideChecker firOverrideChecker, FirCallableDeclaration overrideCandidate, FirCallableDeclaration baseDeclaration) {
        Intrinsics.checkNotNullParameter(firOverrideChecker, "<this>");
        Intrinsics.checkNotNullParameter(overrideCandidate, "overrideCandidate");
        Intrinsics.checkNotNullParameter(baseDeclaration, "baseDeclaration");
        if (Intrinsics.areEqual(overrideCandidate.getOrigin(), FirDeclarationOrigin.DynamicScope.INSTANCE)) {
            return false;
        }
        if (overrideCandidate instanceof FirSimpleFunction) {
            if (baseDeclaration instanceof FirSimpleFunction) {
                return firOverrideChecker.isOverriddenFunction((FirSimpleFunction) overrideCandidate, (FirSimpleFunction) baseDeclaration);
            }
            if (baseDeclaration instanceof FirProperty) {
                return firOverrideChecker.isOverriddenProperty(overrideCandidate, (FirProperty) baseDeclaration);
            }
            return false;
        }
        if (overrideCandidate instanceof FirConstructor) {
            return false;
        }
        if (overrideCandidate instanceof FirProperty) {
            return (baseDeclaration instanceof FirProperty) && firOverrideChecker.isOverriddenProperty(overrideCandidate, (FirProperty) baseDeclaration);
        }
        if (overrideCandidate instanceof FirField) {
            return baseDeclaration instanceof FirField;
        }
        KotlinIllegalArgumentExceptionWithAttachments kotlinIllegalArgumentExceptionWithAttachments = new KotlinIllegalArgumentExceptionWithAttachments("Unknown fir callable type", null);
        ExceptionAttachmentBuilder exceptionAttachmentBuilder = new ExceptionAttachmentBuilder();
        FirExceptionUtilsKt.withFirEntry(exceptionAttachmentBuilder, "overrideCandidate", overrideCandidate);
        FirExceptionUtilsKt.withFirEntry(exceptionAttachmentBuilder, "baseDeclaration", baseDeclaration);
        kotlinIllegalArgumentExceptionWithAttachments.withAttachment("info.txt", exceptionAttachmentBuilder.buildString());
        throw kotlinIllegalArgumentExceptionWithAttachments;
    }

    public static final boolean similarFunctionsOrBothProperties(FirOverrideChecker firOverrideChecker, FirCallableSymbol<?> overrideCandidate, FirCallableSymbol<?> baseDeclaration) {
        Intrinsics.checkNotNullParameter(firOverrideChecker, "<this>");
        Intrinsics.checkNotNullParameter(overrideCandidate, "overrideCandidate");
        Intrinsics.checkNotNullParameter(baseDeclaration, "baseDeclaration");
        return similarFunctionsOrBothProperties(firOverrideChecker, (FirCallableDeclaration) overrideCandidate.getFir(), (FirCallableDeclaration) baseDeclaration.getFir());
    }
}
