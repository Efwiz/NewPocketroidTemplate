package org.jetbrains.kotlin.fir.declarations;

import kotlin.Metadata;
import org.jetbrains.kotlin.fir.contracts.FirContractDescription;
import org.jetbrains.kotlin.fir.contracts.impl.FirEmptyContractDescription;

/* JADX INFO: compiled from: contractSubstitutions.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000.\n\u0000\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\u001a4\u0010\u0004\u001a\u0012\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00070\u0005j\u0002`\b*\u0012\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00070\u0005j\u0002`\b2\u0006\u0010\t\u001a\u00020\nH\u0002\u001a4\u0010\u0004\u001a\u0012\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00070\u000bj\u0002`\f*\u0012\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00070\u000bj\u0002`\f2\u0006\u0010\t\u001a\u00020\nH\u0002\"\u0017\u0010\u0000\u001a\u00020\u0001*\u0004\u0018\u00010\u00028F¢\u0006\u0006\u001a\u0004\b\u0000\u0010\u0003¨\u0006\r"}, d2 = {"isNullOrEmpty", "", "Lorg/jetbrains/kotlin/fir/contracts/FirContractDescription;", "(Lorg/jetbrains/kotlin/fir/contracts/FirContractDescription;)Z", "substitute", "Lorg/jetbrains/kotlin/contracts/description/KtBooleanExpression;", "Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;", "Lorg/jetbrains/kotlin/fir/diagnostics/ConeDiagnostic;", "Lorg/jetbrains/kotlin/fir/contracts/description/ConeBooleanExpression;", "substitutor", "Lorg/jetbrains/kotlin/fir/resolve/substitution/ConeSubstitutor;", "Lorg/jetbrains/kotlin/contracts/description/KtEffectDeclaration;", "Lorg/jetbrains/kotlin/fir/contracts/description/ConeEffectDeclaration;", "providers"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class ContractSubstitutionsKt {
    public static final boolean isNullOrEmpty(FirContractDescription firContractDescription) {
        return firContractDescription == null || (firContractDescription instanceof FirEmptyContractDescription);
    }
}
