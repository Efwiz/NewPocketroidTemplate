package org.jetbrains.kotlin.fir;

import androidx.exifinterface.media.ExifInterface;
import com.google.firebase.analytics.FirebaseAnalytics;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.Constants;
import org.jetbrains.kotlin.KtSourceElement;
import org.jetbrains.kotlin.fir.declarations.FirResolveState;
import org.jetbrains.kotlin.fir.declarations.ResolveStateAccess;
import org.jetbrains.kotlin.fir.visitors.FirTransformer;
import org.jetbrains.kotlin.fir.visitors.FirVisitor;

/* JADX INFO: compiled from: FirElementWithResolveState.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\b&\u0018\u00002\u00020\u00012\u00020\u0002B\u0005¢\u0006\u0002\u0010\u0003J5\u0010\u0013\u001a\u0002H\u0014\"\u0004\b\u0000\u0010\u0014\"\u0004\b\u0001\u0010\u00152\u0012\u0010\u0016\u001a\u000e\u0012\u0004\u0012\u0002H\u0014\u0012\u0004\u0012\u0002H\u00150\u00172\u0006\u0010\u0018\u001a\u0002H\u0015H\u0016¢\u0006\u0002\u0010\u0019J3\u0010\u001a\u001a\u0002H\u001b\"\b\b\u0000\u0010\u001b*\u00020\u0002\"\u0004\b\u0001\u0010\u00152\f\u0010\u001c\u001a\b\u0012\u0004\u0012\u0002H\u00150\u001d2\u0006\u0010\u0018\u001a\u0002H\u0015H\u0016¢\u0006\u0002\u0010\u001eR\u0012\u0010\u0004\u001a\u00020\u0005X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0006\u0010\u0007R$\u0010\b\u001a\u00020\t8\u0006@\u0006X\u0087.¢\u0006\u0014\n\u0000\u0012\u0004\b\n\u0010\u0003\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000eR\u0014\u0010\u000f\u001a\u0004\u0018\u00010\u0010X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0011\u0010\u0012¨\u0006\u001f"}, d2 = {"Lorg/jetbrains/kotlin/fir/FirElementWithResolveState;", "Lorg/jetbrains/kotlin/fir/FirPureAbstractElement;", "Lorg/jetbrains/kotlin/fir/FirElement;", "()V", "moduleData", "Lorg/jetbrains/kotlin/fir/FirModuleData;", "getModuleData", "()Lorg/jetbrains/kotlin/fir/FirModuleData;", "resolveState", "Lorg/jetbrains/kotlin/fir/declarations/FirResolveState;", "getResolveState$annotations", "getResolveState", "()Lorg/jetbrains/kotlin/fir/declarations/FirResolveState;", "setResolveState", "(Lorg/jetbrains/kotlin/fir/declarations/FirResolveState;)V", FirebaseAnalytics.Param.SOURCE, "Lorg/jetbrains/kotlin/KtSourceElement;", "getSource", "()Lorg/jetbrains/kotlin/KtSourceElement;", "accept", "R", "D", "visitor", "Lorg/jetbrains/kotlin/fir/visitors/FirVisitor;", "data", "(Lorg/jetbrains/kotlin/fir/visitors/FirVisitor;Ljava/lang/Object;)Ljava/lang/Object;", "transform", ExifInterface.LONGITUDE_EAST, "transformer", "Lorg/jetbrains/kotlin/fir/visitors/FirTransformer;", "(Lorg/jetbrains/kotlin/fir/visitors/FirTransformer;Ljava/lang/Object;)Lorg/jetbrains/kotlin/fir/FirElement;", Constants.TYPE_TREE}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class FirElementWithResolveState extends FirPureAbstractElement implements FirElement {
    public volatile FirResolveState resolveState;

    @ResolveStateAccess
    public static /* synthetic */ void getResolveState$annotations() {
    }

    public abstract FirModuleData getModuleData();

    @Override // org.jetbrains.kotlin.fir.FirElement
    public abstract KtSourceElement getSource();

    public final FirResolveState getResolveState() {
        FirResolveState firResolveState = this.resolveState;
        if (firResolveState != null) {
            return firResolveState;
        }
        Intrinsics.throwUninitializedPropertyAccessException("resolveState");
        return null;
    }

    public final void setResolveState(FirResolveState firResolveState) {
        Intrinsics.checkNotNullParameter(firResolveState, "<set-?>");
        this.resolveState = firResolveState;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.jetbrains.kotlin.fir.FirElement
    public <R, D> R accept(FirVisitor<? extends R, ? super D> visitor, D data) {
        Intrinsics.checkNotNullParameter(visitor, "visitor");
        return visitor.visitElementWithResolveState(this, data);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.jetbrains.kotlin.fir.FirElement
    public <E extends FirElement, D> E transform(FirTransformer<? super D> transformer, D data) {
        Intrinsics.checkNotNullParameter(transformer, "transformer");
        FirElementWithResolveState firElementWithResolveStateTransformElementWithResolveState = transformer.transformElementWithResolveState(this, data);
        Intrinsics.checkNotNull(firElementWithResolveStateTransformElementWithResolveState, "null cannot be cast to non-null type E of org.jetbrains.kotlin.fir.FirElementWithResolveState.transform");
        return firElementWithResolveStateTransformElementWithResolveState;
    }
}
