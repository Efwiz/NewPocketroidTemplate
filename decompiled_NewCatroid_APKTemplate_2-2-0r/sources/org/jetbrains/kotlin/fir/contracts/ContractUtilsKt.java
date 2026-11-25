package org.jetbrains.kotlin.fir.contracts;

import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.Constants;
import org.jetbrains.kotlin.KtSourceElement;
import org.jetbrains.kotlin.contracts.description.KtContractDescriptionElement;
import org.jetbrains.kotlin.contracts.description.KtEffectDeclaration;
import org.jetbrains.kotlin.fir.contracts.builder.FirContractElementDeclarationBuilder;
import org.jetbrains.kotlin.fir.contracts.builder.FirEffectDeclarationBuilder;
import org.jetbrains.kotlin.fir.diagnostics.ConeDiagnostic;
import org.jetbrains.kotlin.fir.types.ConeKotlinType;

/* JADX INFO: compiled from: ContractUtils.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u00006\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\u001a&\u0010\u0006\u001a\u00020\u0007*\u0012\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\n0\bj\u0002`\u000b2\n\b\u0002\u0010\f\u001a\u0004\u0018\u00010\r\u001a&\u0010\u0006\u001a\u00020\u0002*\u0012\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\n0\u000ej\u0002`\u000f2\n\b\u0002\u0010\f\u001a\u0004\u0018\u00010\r\"\u001d\u0010\u0000\u001a\n\u0012\u0004\u0012\u00020\u0002\u0018\u00010\u0001*\u00020\u00038F¢\u0006\u0006\u001a\u0004\b\u0004\u0010\u0005¨\u0006\u0010"}, d2 = {"effects", "", "Lorg/jetbrains/kotlin/fir/contracts/FirEffectDeclaration;", "Lorg/jetbrains/kotlin/fir/contracts/FirContractDescription;", "getEffects", "(Lorg/jetbrains/kotlin/fir/contracts/FirContractDescription;)Ljava/util/List;", "toFirElement", "Lorg/jetbrains/kotlin/fir/contracts/FirContractElementDeclaration;", "Lorg/jetbrains/kotlin/contracts/description/KtContractDescriptionElement;", "Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;", "Lorg/jetbrains/kotlin/fir/diagnostics/ConeDiagnostic;", "Lorg/jetbrains/kotlin/fir/contracts/description/ConeContractDescriptionElement;", FirebaseAnalytics.Param.SOURCE, "Lorg/jetbrains/kotlin/KtSourceElement;", "Lorg/jetbrains/kotlin/contracts/description/KtEffectDeclaration;", "Lorg/jetbrains/kotlin/fir/contracts/description/ConeEffectDeclaration;", Constants.TYPE_TREE}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class ContractUtilsKt {
    public static final List<FirEffectDeclaration> getEffects(FirContractDescription firContractDescription) {
        Intrinsics.checkNotNullParameter(firContractDescription, "<this>");
        FirResolvedContractDescription firResolvedContractDescription = firContractDescription instanceof FirResolvedContractDescription ? (FirResolvedContractDescription) firContractDescription : null;
        if (firResolvedContractDescription != null) {
            return firResolvedContractDescription.getEffects();
        }
        return null;
    }

    public static /* synthetic */ FirEffectDeclaration toFirElement$default(KtEffectDeclaration ktEffectDeclaration, KtSourceElement ktSourceElement, int i, Object obj) {
        if ((i & 1) != 0) {
            ktSourceElement = null;
        }
        return toFirElement((KtEffectDeclaration<ConeKotlinType, ConeDiagnostic>) ktEffectDeclaration, ktSourceElement);
    }

    public static /* synthetic */ FirContractElementDeclaration toFirElement$default(KtContractDescriptionElement ktContractDescriptionElement, KtSourceElement ktSourceElement, int i, Object obj) {
        if ((i & 1) != 0) {
            ktSourceElement = null;
        }
        return toFirElement((KtContractDescriptionElement<ConeKotlinType, ConeDiagnostic>) ktContractDescriptionElement, ktSourceElement);
    }

    public static final FirEffectDeclaration toFirElement(KtEffectDeclaration<ConeKotlinType, ConeDiagnostic> ktEffectDeclaration, KtSourceElement ktSourceElement) {
        Intrinsics.checkNotNullParameter(ktEffectDeclaration, "<this>");
        FirEffectDeclarationBuilder firEffectDeclarationBuilder = new FirEffectDeclarationBuilder();
        if (ktSourceElement != null) {
            firEffectDeclarationBuilder.setSource(ktSourceElement);
        }
        firEffectDeclarationBuilder.setEffect(ktEffectDeclaration);
        return firEffectDeclarationBuilder.build();
    }

    public static final FirContractElementDeclaration toFirElement(KtContractDescriptionElement<ConeKotlinType, ConeDiagnostic> ktContractDescriptionElement, KtSourceElement ktSourceElement) {
        Intrinsics.checkNotNullParameter(ktContractDescriptionElement, "<this>");
        FirContractElementDeclarationBuilder firContractElementDeclarationBuilder = new FirContractElementDeclarationBuilder();
        if (ktSourceElement != null) {
            firContractElementDeclarationBuilder.setSource(ktSourceElement);
        }
        firContractElementDeclarationBuilder.setEffect(ktContractDescriptionElement);
        return firContractElementDeclarationBuilder.build();
    }
}
