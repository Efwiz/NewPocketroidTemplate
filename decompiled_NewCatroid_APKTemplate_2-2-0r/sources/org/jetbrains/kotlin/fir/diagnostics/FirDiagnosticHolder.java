package org.jetbrains.kotlin.fir.diagnostics;

import androidx.exifinterface.media.ExifInterface;
import com.google.firebase.analytics.FirebaseAnalytics;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.Constants;
import org.jetbrains.kotlin.KtSourceElement;
import org.jetbrains.kotlin.fir.FirElement;
import org.jetbrains.kotlin.fir.visitors.FirTransformer;
import org.jetbrains.kotlin.fir.visitors.FirVisitor;

/* JADX INFO: compiled from: FirDiagnosticHolder.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\bf\u0018\u00002\u00020\u0001J5\u0010\n\u001a\u0002H\u000b\"\u0004\b\u0000\u0010\u000b\"\u0004\b\u0001\u0010\f2\u0012\u0010\r\u001a\u000e\u0012\u0004\u0012\u0002H\u000b\u0012\u0004\u0012\u0002H\f0\u000e2\u0006\u0010\u000f\u001a\u0002H\fH\u0016¢\u0006\u0002\u0010\u0010J3\u0010\u0011\u001a\u0002H\u0012\"\b\b\u0000\u0010\u0012*\u00020\u0001\"\u0004\b\u0001\u0010\f2\f\u0010\u0013\u001a\b\u0012\u0004\u0012\u0002H\f0\u00142\u0006\u0010\u000f\u001a\u0002H\fH\u0016¢\u0006\u0002\u0010\u0015R\u0012\u0010\u0002\u001a\u00020\u0003X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0004\u0010\u0005R\u0014\u0010\u0006\u001a\u0004\u0018\u00010\u0007X¦\u0004¢\u0006\u0006\u001a\u0004\b\b\u0010\tø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\u0016À\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/fir/diagnostics/FirDiagnosticHolder;", "Lorg/jetbrains/kotlin/fir/FirElement;", "diagnostic", "Lorg/jetbrains/kotlin/fir/diagnostics/ConeDiagnostic;", "getDiagnostic", "()Lorg/jetbrains/kotlin/fir/diagnostics/ConeDiagnostic;", FirebaseAnalytics.Param.SOURCE, "Lorg/jetbrains/kotlin/KtSourceElement;", "getSource", "()Lorg/jetbrains/kotlin/KtSourceElement;", "accept", "R", "D", "visitor", "Lorg/jetbrains/kotlin/fir/visitors/FirVisitor;", "data", "(Lorg/jetbrains/kotlin/fir/visitors/FirVisitor;Ljava/lang/Object;)Ljava/lang/Object;", "transform", ExifInterface.LONGITUDE_EAST, "transformer", "Lorg/jetbrains/kotlin/fir/visitors/FirTransformer;", "(Lorg/jetbrains/kotlin/fir/visitors/FirTransformer;Ljava/lang/Object;)Lorg/jetbrains/kotlin/fir/FirElement;", Constants.TYPE_TREE}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface FirDiagnosticHolder extends FirElement {
    ConeDiagnostic getDiagnostic();

    @Override // org.jetbrains.kotlin.fir.FirElement
    KtSourceElement getSource();

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.jetbrains.kotlin.fir.FirElement
    default <R, D> R accept(FirVisitor<? extends R, ? super D> visitor, D data) {
        Intrinsics.checkNotNullParameter(visitor, "visitor");
        return visitor.visitDiagnosticHolder(this, data);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.jetbrains.kotlin.fir.FirElement
    default <E extends FirElement, D> E transform(FirTransformer<? super D> transformer, D data) {
        Intrinsics.checkNotNullParameter(transformer, "transformer");
        FirDiagnosticHolder firDiagnosticHolderTransformDiagnosticHolder = transformer.transformDiagnosticHolder(this, data);
        Intrinsics.checkNotNull(firDiagnosticHolderTransformDiagnosticHolder, "null cannot be cast to non-null type E of org.jetbrains.kotlin.fir.diagnostics.FirDiagnosticHolder.transform");
        return firDiagnosticHolderTransformDiagnosticHolder;
    }
}
