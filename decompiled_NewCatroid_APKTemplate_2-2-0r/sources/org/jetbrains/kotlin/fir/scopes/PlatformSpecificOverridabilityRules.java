package org.jetbrains.kotlin.fir.scopes;

import kotlin.Metadata;
import org.jetbrains.kotlin.fir.FirSessionComponent;
import org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirProperty;
import org.jetbrains.kotlin.fir.declarations.FirSimpleFunction;

/* JADX INFO: compiled from: PlatformSpecificOverridabilityRules.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bf\u0018\u00002\u00020\u0001J\u001f\u0010\u0002\u001a\u0004\u0018\u00010\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0005H&¢\u0006\u0002\u0010\u0007J\u001f\u0010\b\u001a\u0004\u0018\u00010\u00032\u0006\u0010\u0004\u001a\u00020\t2\u0006\u0010\u0006\u001a\u00020\nH&¢\u0006\u0002\u0010\u000bø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\fÀ\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/fir/scopes/PlatformSpecificOverridabilityRules;", "Lorg/jetbrains/kotlin/fir/FirSessionComponent;", "isOverriddenFunction", "", "overrideCandidate", "Lorg/jetbrains/kotlin/fir/declarations/FirSimpleFunction;", "baseDeclaration", "(Lorg/jetbrains/kotlin/fir/declarations/FirSimpleFunction;Lorg/jetbrains/kotlin/fir/declarations/FirSimpleFunction;)Ljava/lang/Boolean;", "isOverriddenProperty", "Lorg/jetbrains/kotlin/fir/declarations/FirCallableDeclaration;", "Lorg/jetbrains/kotlin/fir/declarations/FirProperty;", "(Lorg/jetbrains/kotlin/fir/declarations/FirCallableDeclaration;Lorg/jetbrains/kotlin/fir/declarations/FirProperty;)Ljava/lang/Boolean;", "providers"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface PlatformSpecificOverridabilityRules extends FirSessionComponent {
    Boolean isOverriddenFunction(FirSimpleFunction overrideCandidate, FirSimpleFunction baseDeclaration);

    Boolean isOverriddenProperty(FirCallableDeclaration overrideCandidate, FirProperty baseDeclaration);
}
