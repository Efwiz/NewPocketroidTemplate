package org.jetbrains.kotlin.fir.references;

import androidx.exifinterface.media.ExifInterface;
import com.google.firebase.analytics.FirebaseAnalytics;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.Constants;
import org.jetbrains.kotlin.KtSourceElement;
import org.jetbrains.kotlin.fir.FirElement;
import org.jetbrains.kotlin.fir.symbols.FirBasedSymbol;
import org.jetbrains.kotlin.fir.visitors.FirTransformer;
import org.jetbrains.kotlin.fir.visitors.FirVisitor;

/* JADX INFO: compiled from: FirThisReference.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000R\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b&\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J5\u0010\u0016\u001a\u0002H\u0017\"\u0004\b\u0000\u0010\u0017\"\u0004\b\u0001\u0010\u00182\u0012\u0010\u0019\u001a\u000e\u0012\u0004\u0012\u0002H\u0017\u0012\u0004\u0012\u0002H\u00180\u001a2\u0006\u0010\u001b\u001a\u0002H\u0018H\u0016¢\u0006\u0002\u0010\u001cJ\u0016\u0010\u001d\u001a\u00020\u001e2\f\u0010\u001f\u001a\b\u0012\u0002\b\u0003\u0018\u00010\u0004H&J\u0010\u0010 \u001a\u00020\u001e2\u0006\u0010!\u001a\u00020\bH&J3\u0010\"\u001a\u0002H#\"\b\b\u0000\u0010#*\u00020$\"\u0004\b\u0001\u0010\u00182\f\u0010%\u001a\b\u0012\u0004\u0012\u0002H\u00180&2\u0006\u0010\u001b\u001a\u0002H\u0018H\u0016¢\u0006\u0002\u0010'R\u0018\u0010\u0003\u001a\b\u0012\u0002\b\u0003\u0018\u00010\u0004X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0005\u0010\u0006R\u0012\u0010\u0007\u001a\u00020\bX¦\u0004¢\u0006\u0006\u001a\u0004\b\t\u0010\nR\u0012\u0010\u000b\u001a\u00020\fX¦\u0004¢\u0006\u0006\u001a\u0004\b\u000b\u0010\rR\u0014\u0010\u000e\u001a\u0004\u0018\u00010\u000fX¦\u0004¢\u0006\u0006\u001a\u0004\b\u0010\u0010\u0011R\u0014\u0010\u0012\u001a\u0004\u0018\u00010\u0013X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0014\u0010\u0015¨\u0006("}, d2 = {"Lorg/jetbrains/kotlin/fir/references/FirThisReference;", "Lorg/jetbrains/kotlin/fir/references/FirReference;", "()V", "boundSymbol", "Lorg/jetbrains/kotlin/fir/symbols/FirBasedSymbol;", "getBoundSymbol", "()Lorg/jetbrains/kotlin/fir/symbols/FirBasedSymbol;", "contextReceiverNumber", "", "getContextReceiverNumber", "()I", "isImplicit", "", "()Z", "labelName", "", "getLabelName", "()Ljava/lang/String;", FirebaseAnalytics.Param.SOURCE, "Lorg/jetbrains/kotlin/KtSourceElement;", "getSource", "()Lorg/jetbrains/kotlin/KtSourceElement;", "accept", "R", "D", "visitor", "Lorg/jetbrains/kotlin/fir/visitors/FirVisitor;", "data", "(Lorg/jetbrains/kotlin/fir/visitors/FirVisitor;Ljava/lang/Object;)Ljava/lang/Object;", "replaceBoundSymbol", "", "newBoundSymbol", "replaceContextReceiverNumber", "newContextReceiverNumber", "transform", ExifInterface.LONGITUDE_EAST, "Lorg/jetbrains/kotlin/fir/FirElement;", "transformer", "Lorg/jetbrains/kotlin/fir/visitors/FirTransformer;", "(Lorg/jetbrains/kotlin/fir/visitors/FirTransformer;Ljava/lang/Object;)Lorg/jetbrains/kotlin/fir/FirElement;", Constants.TYPE_TREE}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class FirThisReference extends FirReference {
    public abstract FirBasedSymbol<?> getBoundSymbol();

    public abstract int getContextReceiverNumber();

    public abstract String getLabelName();

    @Override // org.jetbrains.kotlin.fir.references.FirReference, org.jetbrains.kotlin.fir.FirElement
    public abstract KtSourceElement getSource();

    /* JADX INFO: renamed from: isImplicit */
    public abstract boolean getIsImplicit();

    public abstract void replaceBoundSymbol(FirBasedSymbol<?> newBoundSymbol);

    public abstract void replaceContextReceiverNumber(int newContextReceiverNumber);

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.jetbrains.kotlin.fir.references.FirReference, org.jetbrains.kotlin.fir.FirElement
    public <R, D> R accept(FirVisitor<? extends R, ? super D> visitor, D data) {
        Intrinsics.checkNotNullParameter(visitor, "visitor");
        return visitor.visitThisReference(this, data);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.jetbrains.kotlin.fir.references.FirReference, org.jetbrains.kotlin.fir.FirElement
    public <E extends FirElement, D> E transform(FirTransformer<? super D> transformer, D data) {
        Intrinsics.checkNotNullParameter(transformer, "transformer");
        FirReference firReferenceTransformThisReference = transformer.transformThisReference(this, data);
        Intrinsics.checkNotNull(firReferenceTransformThisReference, "null cannot be cast to non-null type E of org.jetbrains.kotlin.fir.references.FirThisReference.transform");
        return firReferenceTransformThisReference;
    }
}
