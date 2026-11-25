package org.jetbrains.kotlin.fir.contracts;

import androidx.exifinterface.media.ExifInterface;
import com.google.firebase.analytics.FirebaseAnalytics;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.Constants;
import org.jetbrains.kotlin.KtSourceElement;
import org.jetbrains.kotlin.contracts.description.KtEffectDeclaration;
import org.jetbrains.kotlin.fir.FirElement;
import org.jetbrains.kotlin.fir.diagnostics.ConeDiagnostic;
import org.jetbrains.kotlin.fir.types.ConeKotlinType;
import org.jetbrains.kotlin.fir.visitors.FirTransformer;
import org.jetbrains.kotlin.fir.visitors.FirVisitor;

/* JADX INFO: compiled from: FirEffectDeclaration.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b&\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J5\u0010\u000e\u001a\u0002H\u000f\"\u0004\b\u0000\u0010\u000f\"\u0004\b\u0001\u0010\u00102\u0012\u0010\u0011\u001a\u000e\u0012\u0004\u0012\u0002H\u000f\u0012\u0004\u0012\u0002H\u00100\u00122\u0006\u0010\u0013\u001a\u0002H\u0010H\u0016¢\u0006\u0002\u0010\u0014J3\u0010\u0015\u001a\u0002H\u0016\"\b\b\u0000\u0010\u0016*\u00020\u0017\"\u0004\b\u0001\u0010\u00102\f\u0010\u0018\u001a\b\u0012\u0004\u0012\u0002H\u00100\u00192\u0006\u0010\u0013\u001a\u0002H\u0010H\u0016¢\u0006\u0002\u0010\u001aR\"\u0010\u0003\u001a\u0012\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00060\u0004j\u0002`\u0007X¦\u0004¢\u0006\u0006\u001a\u0004\b\b\u0010\tR\u0014\u0010\n\u001a\u0004\u0018\u00010\u000bX¦\u0004¢\u0006\u0006\u001a\u0004\b\f\u0010\r¨\u0006\u001b"}, d2 = {"Lorg/jetbrains/kotlin/fir/contracts/FirEffectDeclaration;", "Lorg/jetbrains/kotlin/fir/contracts/FirContractElementDeclaration;", "()V", "effect", "Lorg/jetbrains/kotlin/contracts/description/KtEffectDeclaration;", "Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;", "Lorg/jetbrains/kotlin/fir/diagnostics/ConeDiagnostic;", "Lorg/jetbrains/kotlin/fir/contracts/description/ConeEffectDeclaration;", "getEffect", "()Lorg/jetbrains/kotlin/contracts/description/KtEffectDeclaration;", FirebaseAnalytics.Param.SOURCE, "Lorg/jetbrains/kotlin/KtSourceElement;", "getSource", "()Lorg/jetbrains/kotlin/KtSourceElement;", "accept", "R", "D", "visitor", "Lorg/jetbrains/kotlin/fir/visitors/FirVisitor;", "data", "(Lorg/jetbrains/kotlin/fir/visitors/FirVisitor;Ljava/lang/Object;)Ljava/lang/Object;", "transform", ExifInterface.LONGITUDE_EAST, "Lorg/jetbrains/kotlin/fir/FirElement;", "transformer", "Lorg/jetbrains/kotlin/fir/visitors/FirTransformer;", "(Lorg/jetbrains/kotlin/fir/visitors/FirTransformer;Ljava/lang/Object;)Lorg/jetbrains/kotlin/fir/FirElement;", Constants.TYPE_TREE}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class FirEffectDeclaration extends FirContractElementDeclaration {
    @Override // org.jetbrains.kotlin.fir.contracts.FirContractElementDeclaration
    public abstract KtEffectDeclaration<ConeKotlinType, ConeDiagnostic> getEffect();

    @Override // org.jetbrains.kotlin.fir.contracts.FirContractElementDeclaration, org.jetbrains.kotlin.fir.FirElement
    public abstract KtSourceElement getSource();

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.jetbrains.kotlin.fir.contracts.FirContractElementDeclaration, org.jetbrains.kotlin.fir.FirElement
    public <R, D> R accept(FirVisitor<? extends R, ? super D> visitor, D data) {
        Intrinsics.checkNotNullParameter(visitor, "visitor");
        return visitor.visitEffectDeclaration(this, data);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.jetbrains.kotlin.fir.contracts.FirContractElementDeclaration, org.jetbrains.kotlin.fir.FirElement
    public <E extends FirElement, D> E transform(FirTransformer<? super D> transformer, D data) {
        Intrinsics.checkNotNullParameter(transformer, "transformer");
        FirContractElementDeclaration firContractElementDeclarationTransformEffectDeclaration = transformer.transformEffectDeclaration(this, data);
        Intrinsics.checkNotNull(firContractElementDeclarationTransformEffectDeclaration, "null cannot be cast to non-null type E of org.jetbrains.kotlin.fir.contracts.FirEffectDeclaration.transform");
        return firContractElementDeclarationTransformEffectDeclaration;
    }
}
