package org.jetbrains.kotlin.fir.declarations;

import androidx.exifinterface.media.ExifInterface;
import com.google.firebase.analytics.FirebaseAnalytics;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.Constants;
import org.jetbrains.kotlin.KtSourceElement;
import org.jetbrains.kotlin.fir.FirElement;
import org.jetbrains.kotlin.fir.FirPureAbstractElement;
import org.jetbrains.kotlin.fir.symbols.impl.FirTypeParameterSymbol;
import org.jetbrains.kotlin.fir.visitors.FirTransformer;
import org.jetbrains.kotlin.fir.visitors.FirVisitor;
import org.mozilla.javascript.NativeSymbol;

/* JADX INFO: compiled from: FirConstructedClassTypeParameterRef.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b&\u0018\u00002\u00020\u00012\u00020\u0002B\u0005¢\u0006\u0002\u0010\u0003J5\u0010\f\u001a\u0002H\r\"\u0004\b\u0000\u0010\r\"\u0004\b\u0001\u0010\u000e2\u0012\u0010\u000f\u001a\u000e\u0012\u0004\u0012\u0002H\r\u0012\u0004\u0012\u0002H\u000e0\u00102\u0006\u0010\u0011\u001a\u0002H\u000eH\u0016¢\u0006\u0002\u0010\u0012J3\u0010\u0013\u001a\u0002H\u0014\"\b\b\u0000\u0010\u0014*\u00020\u0015\"\u0004\b\u0001\u0010\u000e2\f\u0010\u0016\u001a\b\u0012\u0004\u0012\u0002H\u000e0\u00172\u0006\u0010\u0011\u001a\u0002H\u000eH\u0016¢\u0006\u0002\u0010\u0018R\u0014\u0010\u0004\u001a\u0004\u0018\u00010\u0005X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0006\u0010\u0007R\u0012\u0010\b\u001a\u00020\tX¦\u0004¢\u0006\u0006\u001a\u0004\b\n\u0010\u000b¨\u0006\u0019"}, d2 = {"Lorg/jetbrains/kotlin/fir/declarations/FirConstructedClassTypeParameterRef;", "Lorg/jetbrains/kotlin/fir/FirPureAbstractElement;", "Lorg/jetbrains/kotlin/fir/declarations/FirTypeParameterRef;", "()V", FirebaseAnalytics.Param.SOURCE, "Lorg/jetbrains/kotlin/KtSourceElement;", "getSource", "()Lorg/jetbrains/kotlin/KtSourceElement;", NativeSymbol.TYPE_NAME, "Lorg/jetbrains/kotlin/fir/symbols/impl/FirTypeParameterSymbol;", "getSymbol", "()Lorg/jetbrains/kotlin/fir/symbols/impl/FirTypeParameterSymbol;", "accept", "R", "D", "visitor", "Lorg/jetbrains/kotlin/fir/visitors/FirVisitor;", "data", "(Lorg/jetbrains/kotlin/fir/visitors/FirVisitor;Ljava/lang/Object;)Ljava/lang/Object;", "transform", ExifInterface.LONGITUDE_EAST, "Lorg/jetbrains/kotlin/fir/FirElement;", "transformer", "Lorg/jetbrains/kotlin/fir/visitors/FirTransformer;", "(Lorg/jetbrains/kotlin/fir/visitors/FirTransformer;Ljava/lang/Object;)Lorg/jetbrains/kotlin/fir/FirElement;", Constants.TYPE_TREE}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class FirConstructedClassTypeParameterRef extends FirPureAbstractElement implements FirTypeParameterRef {
    @Override // org.jetbrains.kotlin.fir.FirElement
    public abstract KtSourceElement getSource();

    @Override // org.jetbrains.kotlin.fir.declarations.FirTypeParameterRef
    public abstract FirTypeParameterSymbol getSymbol();

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.jetbrains.kotlin.fir.FirElement
    public <R, D> R accept(FirVisitor<? extends R, ? super D> visitor, D data) {
        Intrinsics.checkNotNullParameter(visitor, "visitor");
        return visitor.visitConstructedClassTypeParameterRef(this, data);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.jetbrains.kotlin.fir.FirElement
    public <E extends FirElement, D> E transform(FirTransformer<? super D> transformer, D data) {
        Intrinsics.checkNotNullParameter(transformer, "transformer");
        FirTypeParameterRef firTypeParameterRefTransformConstructedClassTypeParameterRef = transformer.transformConstructedClassTypeParameterRef(this, data);
        Intrinsics.checkNotNull(firTypeParameterRefTransformConstructedClassTypeParameterRef, "null cannot be cast to non-null type E of org.jetbrains.kotlin.fir.declarations.FirConstructedClassTypeParameterRef.transform");
        return firTypeParameterRefTransformConstructedClassTypeParameterRef;
    }
}
