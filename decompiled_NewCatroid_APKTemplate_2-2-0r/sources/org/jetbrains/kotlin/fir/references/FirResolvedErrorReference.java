package org.jetbrains.kotlin.fir.references;

import androidx.exifinterface.media.ExifInterface;
import com.google.firebase.analytics.FirebaseAnalytics;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.Constants;
import org.jetbrains.kotlin.KtSourceElement;
import org.jetbrains.kotlin.fir.FirElement;
import org.jetbrains.kotlin.fir.diagnostics.ConeDiagnostic;
import org.jetbrains.kotlin.fir.diagnostics.FirDiagnosticHolder;
import org.jetbrains.kotlin.fir.symbols.FirBasedSymbol;
import org.jetbrains.kotlin.fir.visitors.FirTransformer;
import org.jetbrains.kotlin.fir.visitors.FirVisitor;
import org.jetbrains.kotlin.name.Name;

/* JADX INFO: compiled from: FirResolvedErrorReference.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000F\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b&\u0018\u00002\u00020\u00012\u00020\u0002B\u0005¢\u0006\u0002\u0010\u0003J5\u0010\u0014\u001a\u0002H\u0015\"\u0004\b\u0000\u0010\u0015\"\u0004\b\u0001\u0010\u00162\u0012\u0010\u0017\u001a\u000e\u0012\u0004\u0012\u0002H\u0015\u0012\u0004\u0012\u0002H\u00160\u00182\u0006\u0010\u0019\u001a\u0002H\u0016H\u0016¢\u0006\u0002\u0010\u001aJ3\u0010\u001b\u001a\u0002H\u001c\"\b\b\u0000\u0010\u001c*\u00020\u001d\"\u0004\b\u0001\u0010\u00162\f\u0010\u001e\u001a\b\u0012\u0004\u0012\u0002H\u00160\u001f2\u0006\u0010\u0019\u001a\u0002H\u0016H\u0016¢\u0006\u0002\u0010 R\u0012\u0010\u0004\u001a\u00020\u0005X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0006\u0010\u0007R\u0012\u0010\b\u001a\u00020\tX¦\u0004¢\u0006\u0006\u001a\u0004\b\n\u0010\u000bR\u0016\u0010\f\u001a\u0006\u0012\u0002\b\u00030\rX¦\u0004¢\u0006\u0006\u001a\u0004\b\u000e\u0010\u000fR\u0014\u0010\u0010\u001a\u0004\u0018\u00010\u0011X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0012\u0010\u0013¨\u0006!"}, d2 = {"Lorg/jetbrains/kotlin/fir/references/FirResolvedErrorReference;", "Lorg/jetbrains/kotlin/fir/references/FirResolvedNamedReference;", "Lorg/jetbrains/kotlin/fir/diagnostics/FirDiagnosticHolder;", "()V", "diagnostic", "Lorg/jetbrains/kotlin/fir/diagnostics/ConeDiagnostic;", "getDiagnostic", "()Lorg/jetbrains/kotlin/fir/diagnostics/ConeDiagnostic;", "name", "Lorg/jetbrains/kotlin/name/Name;", "getName", "()Lorg/jetbrains/kotlin/name/Name;", "resolvedSymbol", "Lorg/jetbrains/kotlin/fir/symbols/FirBasedSymbol;", "getResolvedSymbol", "()Lorg/jetbrains/kotlin/fir/symbols/FirBasedSymbol;", FirebaseAnalytics.Param.SOURCE, "Lorg/jetbrains/kotlin/KtSourceElement;", "getSource", "()Lorg/jetbrains/kotlin/KtSourceElement;", "accept", "R", "D", "visitor", "Lorg/jetbrains/kotlin/fir/visitors/FirVisitor;", "data", "(Lorg/jetbrains/kotlin/fir/visitors/FirVisitor;Ljava/lang/Object;)Ljava/lang/Object;", "transform", ExifInterface.LONGITUDE_EAST, "Lorg/jetbrains/kotlin/fir/FirElement;", "transformer", "Lorg/jetbrains/kotlin/fir/visitors/FirTransformer;", "(Lorg/jetbrains/kotlin/fir/visitors/FirTransformer;Ljava/lang/Object;)Lorg/jetbrains/kotlin/fir/FirElement;", Constants.TYPE_TREE}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class FirResolvedErrorReference extends FirResolvedNamedReference implements FirDiagnosticHolder {
    @Override // org.jetbrains.kotlin.fir.diagnostics.FirDiagnosticHolder
    public abstract ConeDiagnostic getDiagnostic();

    @Override // org.jetbrains.kotlin.fir.references.FirResolvedNamedReference, org.jetbrains.kotlin.fir.references.FirNamedReference
    public abstract Name getName();

    @Override // org.jetbrains.kotlin.fir.references.FirResolvedNamedReference
    public abstract FirBasedSymbol<?> getResolvedSymbol();

    @Override // org.jetbrains.kotlin.fir.references.FirResolvedNamedReference, org.jetbrains.kotlin.fir.references.FirNamedReference, org.jetbrains.kotlin.fir.references.FirReference, org.jetbrains.kotlin.fir.FirElement
    public abstract KtSourceElement getSource();

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.jetbrains.kotlin.fir.references.FirResolvedNamedReference, org.jetbrains.kotlin.fir.references.FirNamedReference, org.jetbrains.kotlin.fir.references.FirReference, org.jetbrains.kotlin.fir.FirElement
    public <R, D> R accept(FirVisitor<? extends R, ? super D> visitor, D data) {
        Intrinsics.checkNotNullParameter(visitor, "visitor");
        return visitor.visitResolvedErrorReference(this, data);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.jetbrains.kotlin.fir.references.FirResolvedNamedReference, org.jetbrains.kotlin.fir.references.FirNamedReference, org.jetbrains.kotlin.fir.references.FirReference, org.jetbrains.kotlin.fir.FirElement
    public <E extends FirElement, D> E transform(FirTransformer<? super D> transformer, D data) {
        Intrinsics.checkNotNullParameter(transformer, "transformer");
        FirReference firReferenceTransformResolvedErrorReference = transformer.transformResolvedErrorReference(this, data);
        Intrinsics.checkNotNull(firReferenceTransformResolvedErrorReference, "null cannot be cast to non-null type E of org.jetbrains.kotlin.fir.references.FirResolvedErrorReference.transform");
        return firReferenceTransformResolvedErrorReference;
    }
}
