package org.jetbrains.kotlin.fir.expressions;

import androidx.exifinterface.media.ExifInterface;
import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.Constants;
import org.jetbrains.kotlin.KtSourceElement;
import org.jetbrains.kotlin.fir.FirElement;
import org.jetbrains.kotlin.fir.types.FirTypeRef;
import org.jetbrains.kotlin.fir.visitors.FirTransformer;
import org.jetbrains.kotlin.fir.visitors.FirVisitor;

/* JADX INFO: compiled from: FirTypeOperatorCall.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000Z\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\b&\u0018\u00002\u00020\u00012\u00020\u0002B\u0005¢\u0006\u0002\u0010\u0003J5\u0010\u001b\u001a\u0002H\u001c\"\u0004\b\u0000\u0010\u001c\"\u0004\b\u0001\u0010\u001d2\u0012\u0010\u001e\u001a\u000e\u0012\u0004\u0012\u0002H\u001c\u0012\u0004\u0012\u0002H\u001d0\u001f2\u0006\u0010 \u001a\u0002H\u001dH\u0016¢\u0006\u0002\u0010!J\u0016\u0010\"\u001a\u00020#2\f\u0010$\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005H&J\u0010\u0010%\u001a\u00020#2\u0006\u0010&\u001a\u00020\nH&J\u0010\u0010'\u001a\u00020#2\u0006\u0010(\u001a\u00020\u000eH&J3\u0010)\u001a\u0002H*\"\b\b\u0000\u0010**\u00020+\"\u0004\b\u0001\u0010\u001d2\f\u0010,\u001a\b\u0012\u0004\u0012\u0002H\u001d0-2\u0006\u0010 \u001a\u0002H\u001dH\u0016¢\u0006\u0002\u0010.J)\u0010/\u001a\u00020\u0000\"\u0004\b\u0000\u0010\u001d2\f\u0010,\u001a\b\u0012\u0004\u0012\u0002H\u001d0-2\u0006\u0010 \u001a\u0002H\u001dH&¢\u0006\u0002\u00100J)\u00101\u001a\u00020\u0000\"\u0004\b\u0000\u0010\u001d2\f\u0010,\u001a\b\u0012\u0004\u0012\u0002H\u001d0-2\u0006\u0010 \u001a\u0002H\u001dH&¢\u0006\u0002\u00100J)\u00102\u001a\u00020\u0000\"\u0004\b\u0000\u0010\u001d2\f\u0010,\u001a\b\u0012\u0004\u0012\u0002H\u001d0-2\u0006\u0010 \u001a\u0002H\u001dH&¢\u0006\u0002\u00100R\u0018\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0007\u0010\bR\u0012\u0010\t\u001a\u00020\nX¦\u0004¢\u0006\u0006\u001a\u0004\b\u000b\u0010\fR\u0012\u0010\r\u001a\u00020\u000eX¦\u0004¢\u0006\u0006\u001a\u0004\b\u000f\u0010\u0010R\u0012\u0010\u0011\u001a\u00020\u0012X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0013\u0010\u0014R\u0014\u0010\u0015\u001a\u0004\u0018\u00010\u0016X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0017\u0010\u0018R\u0012\u0010\u0019\u001a\u00020\u000eX¦\u0004¢\u0006\u0006\u001a\u0004\b\u001a\u0010\u0010¨\u00063"}, d2 = {"Lorg/jetbrains/kotlin/fir/expressions/FirTypeOperatorCall;", "Lorg/jetbrains/kotlin/fir/expressions/FirExpression;", "Lorg/jetbrains/kotlin/fir/expressions/FirCall;", "()V", "annotations", "", "Lorg/jetbrains/kotlin/fir/expressions/FirAnnotation;", "getAnnotations", "()Ljava/util/List;", "argumentList", "Lorg/jetbrains/kotlin/fir/expressions/FirArgumentList;", "getArgumentList", "()Lorg/jetbrains/kotlin/fir/expressions/FirArgumentList;", "conversionTypeRef", "Lorg/jetbrains/kotlin/fir/types/FirTypeRef;", "getConversionTypeRef", "()Lorg/jetbrains/kotlin/fir/types/FirTypeRef;", "operation", "Lorg/jetbrains/kotlin/fir/expressions/FirOperation;", "getOperation", "()Lorg/jetbrains/kotlin/fir/expressions/FirOperation;", FirebaseAnalytics.Param.SOURCE, "Lorg/jetbrains/kotlin/KtSourceElement;", "getSource", "()Lorg/jetbrains/kotlin/KtSourceElement;", "typeRef", "getTypeRef", "accept", "R", "D", "visitor", "Lorg/jetbrains/kotlin/fir/visitors/FirVisitor;", "data", "(Lorg/jetbrains/kotlin/fir/visitors/FirVisitor;Ljava/lang/Object;)Ljava/lang/Object;", "replaceAnnotations", "", "newAnnotations", "replaceArgumentList", "newArgumentList", "replaceTypeRef", "newTypeRef", "transform", ExifInterface.LONGITUDE_EAST, "Lorg/jetbrains/kotlin/fir/FirElement;", "transformer", "Lorg/jetbrains/kotlin/fir/visitors/FirTransformer;", "(Lorg/jetbrains/kotlin/fir/visitors/FirTransformer;Ljava/lang/Object;)Lorg/jetbrains/kotlin/fir/FirElement;", "transformAnnotations", "(Lorg/jetbrains/kotlin/fir/visitors/FirTransformer;Ljava/lang/Object;)Lorg/jetbrains/kotlin/fir/expressions/FirTypeOperatorCall;", "transformConversionTypeRef", "transformOtherChildren", Constants.TYPE_TREE}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class FirTypeOperatorCall extends FirExpression implements FirCall {
    @Override // org.jetbrains.kotlin.fir.expressions.FirExpression, org.jetbrains.kotlin.fir.expressions.FirStatement, org.jetbrains.kotlin.fir.FirAnnotationContainer
    public abstract List<FirAnnotation> getAnnotations();

    @Override // org.jetbrains.kotlin.fir.expressions.FirCall
    public abstract FirArgumentList getArgumentList();

    public abstract FirTypeRef getConversionTypeRef();

    public abstract FirOperation getOperation();

    @Override // org.jetbrains.kotlin.fir.expressions.FirExpression, org.jetbrains.kotlin.fir.FirElement
    public abstract KtSourceElement getSource();

    @Override // org.jetbrains.kotlin.fir.expressions.FirExpression
    public abstract FirTypeRef getTypeRef();

    @Override // org.jetbrains.kotlin.fir.expressions.FirExpression, org.jetbrains.kotlin.fir.expressions.FirStatement, org.jetbrains.kotlin.fir.FirAnnotationContainer
    public abstract void replaceAnnotations(List<? extends FirAnnotation> newAnnotations);

    @Override // org.jetbrains.kotlin.fir.expressions.FirCall
    public abstract void replaceArgumentList(FirArgumentList newArgumentList);

    @Override // org.jetbrains.kotlin.fir.expressions.FirExpression
    public abstract void replaceTypeRef(FirTypeRef newTypeRef);

    @Override // org.jetbrains.kotlin.fir.expressions.FirExpression, org.jetbrains.kotlin.fir.expressions.FirStatement, org.jetbrains.kotlin.fir.FirAnnotationContainer
    public abstract <D> FirTypeOperatorCall transformAnnotations(FirTransformer<? super D> transformer, D data);

    public abstract <D> FirTypeOperatorCall transformConversionTypeRef(FirTransformer<? super D> transformer, D data);

    public abstract <D> FirTypeOperatorCall transformOtherChildren(FirTransformer<? super D> transformer, D data);

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.jetbrains.kotlin.fir.expressions.FirExpression, org.jetbrains.kotlin.fir.FirElement
    public <R, D> R accept(FirVisitor<? extends R, ? super D> visitor, D data) {
        Intrinsics.checkNotNullParameter(visitor, "visitor");
        return visitor.visitTypeOperatorCall(this, data);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.jetbrains.kotlin.fir.expressions.FirExpression, org.jetbrains.kotlin.fir.FirElement
    public <E extends FirElement, D> E transform(FirTransformer<? super D> transformer, D data) {
        Intrinsics.checkNotNullParameter(transformer, "transformer");
        FirStatement firStatementTransformTypeOperatorCall = transformer.transformTypeOperatorCall(this, data);
        Intrinsics.checkNotNull(firStatementTransformTypeOperatorCall, "null cannot be cast to non-null type E of org.jetbrains.kotlin.fir.expressions.FirTypeOperatorCall.transform");
        return firStatementTransformTypeOperatorCall;
    }
}
