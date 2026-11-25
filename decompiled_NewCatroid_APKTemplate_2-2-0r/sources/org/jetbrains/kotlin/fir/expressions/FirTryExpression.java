package org.jetbrains.kotlin.fir.expressions;

import androidx.exifinterface.media.ExifInterface;
import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.Constants;
import org.jetbrains.kotlin.KtSourceElement;
import org.jetbrains.kotlin.fir.FirElement;
import org.jetbrains.kotlin.fir.references.FirReference;
import org.jetbrains.kotlin.fir.types.FirTypeRef;
import org.jetbrains.kotlin.fir.visitors.FirTransformer;
import org.jetbrains.kotlin.fir.visitors.FirVisitor;

/* JADX INFO: compiled from: FirTryExpression.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000b\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\t\b&\u0018\u00002\u00020\u00012\u00020\u0002B\u0005¢\u0006\u0002\u0010\u0003J5\u0010\u001e\u001a\u0002H\u001f\"\u0004\b\u0000\u0010\u001f\"\u0004\b\u0001\u0010 2\u0012\u0010!\u001a\u000e\u0012\u0004\u0012\u0002H\u001f\u0012\u0004\u0012\u0002H 0\"2\u0006\u0010#\u001a\u0002H H\u0016¢\u0006\u0002\u0010$J\u0016\u0010%\u001a\u00020&2\f\u0010'\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005H&J\u0010\u0010(\u001a\u00020&2\u0006\u0010)\u001a\u00020\nH&J\u0010\u0010*\u001a\u00020&2\u0006\u0010+\u001a\u00020\u001bH&J3\u0010,\u001a\u0002H-\"\b\b\u0000\u0010-*\u00020.\"\u0004\b\u0001\u0010 2\f\u0010/\u001a\b\u0012\u0004\u0012\u0002H 002\u0006\u0010#\u001a\u0002H H\u0016¢\u0006\u0002\u00101J)\u00102\u001a\u00020\u0000\"\u0004\b\u0000\u0010 2\f\u0010/\u001a\b\u0012\u0004\u0012\u0002H 002\u0006\u0010#\u001a\u0002H H&¢\u0006\u0002\u00103J)\u00104\u001a\u00020\u0000\"\u0004\b\u0000\u0010 2\f\u0010/\u001a\b\u0012\u0004\u0012\u0002H 002\u0006\u0010#\u001a\u0002H H&¢\u0006\u0002\u00103J)\u00105\u001a\u00020\u0000\"\u0004\b\u0000\u0010 2\f\u0010/\u001a\b\u0012\u0004\u0012\u0002H 002\u0006\u0010#\u001a\u0002H H&¢\u0006\u0002\u00103J)\u00106\u001a\u00020\u0000\"\u0004\b\u0000\u0010 2\f\u0010/\u001a\b\u0012\u0004\u0012\u0002H 002\u0006\u0010#\u001a\u0002H H&¢\u0006\u0002\u00103J)\u00107\u001a\u00020\u0000\"\u0004\b\u0000\u0010 2\f\u0010/\u001a\b\u0012\u0004\u0012\u0002H 002\u0006\u0010#\u001a\u0002H H&¢\u0006\u0002\u00103J)\u00108\u001a\u00020\u0000\"\u0004\b\u0000\u0010 2\f\u0010/\u001a\b\u0012\u0004\u0012\u0002H 002\u0006\u0010#\u001a\u0002H H&¢\u0006\u0002\u00103R\u0018\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0007\u0010\bR\u0012\u0010\t\u001a\u00020\nX¦\u0004¢\u0006\u0006\u001a\u0004\b\u000b\u0010\fR\u0018\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u000e0\u0005X¦\u0004¢\u0006\u0006\u001a\u0004\b\u000f\u0010\bR\u0014\u0010\u0010\u001a\u0004\u0018\u00010\u0011X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0012\u0010\u0013R\u0014\u0010\u0014\u001a\u0004\u0018\u00010\u0015X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0016\u0010\u0017R\u0012\u0010\u0018\u001a\u00020\u0011X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0019\u0010\u0013R\u0012\u0010\u001a\u001a\u00020\u001bX¦\u0004¢\u0006\u0006\u001a\u0004\b\u001c\u0010\u001d¨\u00069"}, d2 = {"Lorg/jetbrains/kotlin/fir/expressions/FirTryExpression;", "Lorg/jetbrains/kotlin/fir/expressions/FirExpression;", "Lorg/jetbrains/kotlin/fir/expressions/FirResolvable;", "()V", "annotations", "", "Lorg/jetbrains/kotlin/fir/expressions/FirAnnotation;", "getAnnotations", "()Ljava/util/List;", "calleeReference", "Lorg/jetbrains/kotlin/fir/references/FirReference;", "getCalleeReference", "()Lorg/jetbrains/kotlin/fir/references/FirReference;", "catches", "Lorg/jetbrains/kotlin/fir/expressions/FirCatch;", "getCatches", "finallyBlock", "Lorg/jetbrains/kotlin/fir/expressions/FirBlock;", "getFinallyBlock", "()Lorg/jetbrains/kotlin/fir/expressions/FirBlock;", FirebaseAnalytics.Param.SOURCE, "Lorg/jetbrains/kotlin/KtSourceElement;", "getSource", "()Lorg/jetbrains/kotlin/KtSourceElement;", "tryBlock", "getTryBlock", "typeRef", "Lorg/jetbrains/kotlin/fir/types/FirTypeRef;", "getTypeRef", "()Lorg/jetbrains/kotlin/fir/types/FirTypeRef;", "accept", "R", "D", "visitor", "Lorg/jetbrains/kotlin/fir/visitors/FirVisitor;", "data", "(Lorg/jetbrains/kotlin/fir/visitors/FirVisitor;Ljava/lang/Object;)Ljava/lang/Object;", "replaceAnnotations", "", "newAnnotations", "replaceCalleeReference", "newCalleeReference", "replaceTypeRef", "newTypeRef", "transform", ExifInterface.LONGITUDE_EAST, "Lorg/jetbrains/kotlin/fir/FirElement;", "transformer", "Lorg/jetbrains/kotlin/fir/visitors/FirTransformer;", "(Lorg/jetbrains/kotlin/fir/visitors/FirTransformer;Ljava/lang/Object;)Lorg/jetbrains/kotlin/fir/FirElement;", "transformAnnotations", "(Lorg/jetbrains/kotlin/fir/visitors/FirTransformer;Ljava/lang/Object;)Lorg/jetbrains/kotlin/fir/expressions/FirTryExpression;", "transformCalleeReference", "transformCatches", "transformFinallyBlock", "transformOtherChildren", "transformTryBlock", Constants.TYPE_TREE}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class FirTryExpression extends FirExpression implements FirResolvable {
    @Override // org.jetbrains.kotlin.fir.expressions.FirExpression, org.jetbrains.kotlin.fir.expressions.FirStatement, org.jetbrains.kotlin.fir.FirAnnotationContainer
    public abstract List<FirAnnotation> getAnnotations();

    @Override // org.jetbrains.kotlin.fir.expressions.FirResolvable
    public abstract FirReference getCalleeReference();

    public abstract List<FirCatch> getCatches();

    public abstract FirBlock getFinallyBlock();

    @Override // org.jetbrains.kotlin.fir.expressions.FirExpression, org.jetbrains.kotlin.fir.FirElement
    public abstract KtSourceElement getSource();

    public abstract FirBlock getTryBlock();

    @Override // org.jetbrains.kotlin.fir.expressions.FirExpression
    public abstract FirTypeRef getTypeRef();

    @Override // org.jetbrains.kotlin.fir.expressions.FirExpression, org.jetbrains.kotlin.fir.expressions.FirStatement, org.jetbrains.kotlin.fir.FirAnnotationContainer
    public abstract void replaceAnnotations(List<? extends FirAnnotation> newAnnotations);

    @Override // org.jetbrains.kotlin.fir.expressions.FirResolvable
    public abstract void replaceCalleeReference(FirReference newCalleeReference);

    @Override // org.jetbrains.kotlin.fir.expressions.FirExpression
    public abstract void replaceTypeRef(FirTypeRef newTypeRef);

    @Override // org.jetbrains.kotlin.fir.expressions.FirExpression, org.jetbrains.kotlin.fir.expressions.FirStatement, org.jetbrains.kotlin.fir.FirAnnotationContainer
    public abstract <D> FirTryExpression transformAnnotations(FirTransformer<? super D> transformer, D data);

    @Override // org.jetbrains.kotlin.fir.expressions.FirResolvable
    public abstract <D> FirTryExpression transformCalleeReference(FirTransformer<? super D> transformer, D data);

    public abstract <D> FirTryExpression transformCatches(FirTransformer<? super D> transformer, D data);

    public abstract <D> FirTryExpression transformFinallyBlock(FirTransformer<? super D> transformer, D data);

    public abstract <D> FirTryExpression transformOtherChildren(FirTransformer<? super D> transformer, D data);

    public abstract <D> FirTryExpression transformTryBlock(FirTransformer<? super D> transformer, D data);

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.jetbrains.kotlin.fir.expressions.FirExpression, org.jetbrains.kotlin.fir.FirElement
    public <R, D> R accept(FirVisitor<? extends R, ? super D> visitor, D data) {
        Intrinsics.checkNotNullParameter(visitor, "visitor");
        return visitor.visitTryExpression(this, data);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.jetbrains.kotlin.fir.expressions.FirExpression, org.jetbrains.kotlin.fir.FirElement
    public <E extends FirElement, D> E transform(FirTransformer<? super D> transformer, D data) {
        Intrinsics.checkNotNullParameter(transformer, "transformer");
        FirStatement firStatementTransformTryExpression = transformer.transformTryExpression(this, data);
        Intrinsics.checkNotNull(firStatementTransformTryExpression, "null cannot be cast to non-null type E of org.jetbrains.kotlin.fir.expressions.FirTryExpression.transform");
        return firStatementTransformTryExpression;
    }
}
