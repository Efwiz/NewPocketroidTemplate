package org.jetbrains.kotlin.fir.contracts;

import androidx.exifinterface.media.ExifInterface;
import com.google.firebase.analytics.FirebaseAnalytics;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.Constants;
import org.jetbrains.kotlin.KtSourceElement;
import org.jetbrains.kotlin.fir.FirElement;
import org.jetbrains.kotlin.fir.expressions.FirFunctionCall;
import org.jetbrains.kotlin.fir.visitors.FirTransformer;
import org.jetbrains.kotlin.fir.visitors.FirVisitor;

/* JADX INFO: compiled from: FirLegacyRawContractDescription.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b&\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J5\u0010\u000b\u001a\u0002H\f\"\u0004\b\u0000\u0010\f\"\u0004\b\u0001\u0010\r2\u0012\u0010\u000e\u001a\u000e\u0012\u0004\u0012\u0002H\f\u0012\u0004\u0012\u0002H\r0\u000f2\u0006\u0010\u0010\u001a\u0002H\rH\u0016¢\u0006\u0002\u0010\u0011J3\u0010\u0012\u001a\u0002H\u0013\"\b\b\u0000\u0010\u0013*\u00020\u0014\"\u0004\b\u0001\u0010\r2\f\u0010\u0015\u001a\b\u0012\u0004\u0012\u0002H\r0\u00162\u0006\u0010\u0010\u001a\u0002H\rH\u0016¢\u0006\u0002\u0010\u0017R\u0012\u0010\u0003\u001a\u00020\u0004X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0005\u0010\u0006R\u0014\u0010\u0007\u001a\u0004\u0018\u00010\bX¦\u0004¢\u0006\u0006\u001a\u0004\b\t\u0010\n¨\u0006\u0018"}, d2 = {"Lorg/jetbrains/kotlin/fir/contracts/FirLegacyRawContractDescription;", "Lorg/jetbrains/kotlin/fir/contracts/FirContractDescription;", "()V", "contractCall", "Lorg/jetbrains/kotlin/fir/expressions/FirFunctionCall;", "getContractCall", "()Lorg/jetbrains/kotlin/fir/expressions/FirFunctionCall;", FirebaseAnalytics.Param.SOURCE, "Lorg/jetbrains/kotlin/KtSourceElement;", "getSource", "()Lorg/jetbrains/kotlin/KtSourceElement;", "accept", "R", "D", "visitor", "Lorg/jetbrains/kotlin/fir/visitors/FirVisitor;", "data", "(Lorg/jetbrains/kotlin/fir/visitors/FirVisitor;Ljava/lang/Object;)Ljava/lang/Object;", "transform", ExifInterface.LONGITUDE_EAST, "Lorg/jetbrains/kotlin/fir/FirElement;", "transformer", "Lorg/jetbrains/kotlin/fir/visitors/FirTransformer;", "(Lorg/jetbrains/kotlin/fir/visitors/FirTransformer;Ljava/lang/Object;)Lorg/jetbrains/kotlin/fir/FirElement;", Constants.TYPE_TREE}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class FirLegacyRawContractDescription extends FirContractDescription {
    public abstract FirFunctionCall getContractCall();

    @Override // org.jetbrains.kotlin.fir.contracts.FirContractDescription, org.jetbrains.kotlin.fir.FirElement
    public abstract KtSourceElement getSource();

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.jetbrains.kotlin.fir.contracts.FirContractDescription, org.jetbrains.kotlin.fir.FirElement
    public <R, D> R accept(FirVisitor<? extends R, ? super D> visitor, D data) {
        Intrinsics.checkNotNullParameter(visitor, "visitor");
        return visitor.visitLegacyRawContractDescription(this, data);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.jetbrains.kotlin.fir.contracts.FirContractDescription, org.jetbrains.kotlin.fir.FirElement
    public <E extends FirElement, D> E transform(FirTransformer<? super D> transformer, D data) {
        Intrinsics.checkNotNullParameter(transformer, "transformer");
        FirContractDescription firContractDescriptionTransformLegacyRawContractDescription = transformer.transformLegacyRawContractDescription(this, data);
        Intrinsics.checkNotNull(firContractDescriptionTransformLegacyRawContractDescription, "null cannot be cast to non-null type E of org.jetbrains.kotlin.fir.contracts.FirLegacyRawContractDescription.transform");
        return firContractDescriptionTransformLegacyRawContractDescription;
    }
}
