package org.jetbrains.kotlin.fir;

import androidx.exifinterface.media.ExifInterface;
import com.google.firebase.analytics.FirebaseAnalytics;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.Constants;
import org.jetbrains.kotlin.KtSourceElement;
import org.jetbrains.kotlin.fir.visitors.FirTransformer;
import org.jetbrains.kotlin.fir.visitors.FirVisitor;
import org.jetbrains.kotlin.fir.visitors.FirVisitorVoid;

/* JADX INFO: compiled from: FirElement.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\bf\u0018\u00002\u00020\u0001J5\u0010\u0006\u001a\u0002H\u0007\"\u0004\b\u0000\u0010\u0007\"\u0004\b\u0001\u0010\b2\u0012\u0010\t\u001a\u000e\u0012\u0004\u0012\u0002H\u0007\u0012\u0004\u0012\u0002H\b0\n2\u0006\u0010\u000b\u001a\u0002H\bH\u0016¢\u0006\u0002\u0010\fJ\u0010\u0010\u0006\u001a\u00020\r2\u0006\u0010\t\u001a\u00020\u000eH\u0016J5\u0010\u000f\u001a\u00020\r\"\u0004\b\u0000\u0010\u0007\"\u0004\b\u0001\u0010\b2\u0012\u0010\t\u001a\u000e\u0012\u0004\u0012\u0002H\u0007\u0012\u0004\u0012\u0002H\b0\n2\u0006\u0010\u000b\u001a\u0002H\bH&¢\u0006\u0002\u0010\u0010J\u0010\u0010\u000f\u001a\u00020\r2\u0006\u0010\t\u001a\u00020\u000eH\u0016J3\u0010\u0011\u001a\u0002H\u0012\"\b\b\u0000\u0010\u0012*\u00020\u0000\"\u0004\b\u0001\u0010\b2\f\u0010\u0013\u001a\b\u0012\u0004\u0012\u0002H\b0\u00142\u0006\u0010\u000b\u001a\u0002H\bH\u0016¢\u0006\u0002\u0010\u0015J)\u0010\u0016\u001a\u00020\u0000\"\u0004\b\u0000\u0010\b2\f\u0010\u0013\u001a\b\u0012\u0004\u0012\u0002H\b0\u00142\u0006\u0010\u000b\u001a\u0002H\bH&¢\u0006\u0002\u0010\u0015R\u0014\u0010\u0002\u001a\u0004\u0018\u00010\u0003X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0004\u0010\u0005ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\u0017À\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/fir/FirElement;", "", FirebaseAnalytics.Param.SOURCE, "Lorg/jetbrains/kotlin/KtSourceElement;", "getSource", "()Lorg/jetbrains/kotlin/KtSourceElement;", "accept", "R", "D", "visitor", "Lorg/jetbrains/kotlin/fir/visitors/FirVisitor;", "data", "(Lorg/jetbrains/kotlin/fir/visitors/FirVisitor;Ljava/lang/Object;)Ljava/lang/Object;", "", "Lorg/jetbrains/kotlin/fir/visitors/FirVisitorVoid;", "acceptChildren", "(Lorg/jetbrains/kotlin/fir/visitors/FirVisitor;Ljava/lang/Object;)V", "transform", ExifInterface.LONGITUDE_EAST, "transformer", "Lorg/jetbrains/kotlin/fir/visitors/FirTransformer;", "(Lorg/jetbrains/kotlin/fir/visitors/FirTransformer;Ljava/lang/Object;)Lorg/jetbrains/kotlin/fir/FirElement;", "transformChildren", Constants.TYPE_TREE}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface FirElement {
    <R, D> void acceptChildren(FirVisitor<? extends R, ? super D> visitor, D data);

    KtSourceElement getSource();

    <D> FirElement transformChildren(FirTransformer<? super D> transformer, D data);

    /* JADX WARN: Multi-variable type inference failed */
    default <R, D> R accept(FirVisitor<? extends R, ? super D> visitor, D data) {
        Intrinsics.checkNotNullParameter(visitor, "visitor");
        return visitor.visitElement(this, data);
    }

    /* JADX WARN: Multi-variable type inference failed */
    default <E extends FirElement, D> E transform(FirTransformer<? super D> transformer, D data) {
        Intrinsics.checkNotNullParameter(transformer, "transformer");
        E e = (E) transformer.transformElement(this, data);
        Intrinsics.checkNotNull(e, "null cannot be cast to non-null type E of org.jetbrains.kotlin.fir.FirElement.transform");
        return e;
    }

    default void accept(FirVisitorVoid visitor) {
        Intrinsics.checkNotNullParameter(visitor, "visitor");
        accept(visitor, null);
    }

    default void acceptChildren(FirVisitorVoid visitor) {
        Intrinsics.checkNotNullParameter(visitor, "visitor");
        acceptChildren(visitor, null);
    }
}
