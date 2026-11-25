package org.jetbrains.kotlin.fir.expressions;

import androidx.exifinterface.media.ExifInterface;
import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.Constants;
import org.jetbrains.kotlin.KtSourceElement;
import org.jetbrains.kotlin.fir.FirElement;
import org.jetbrains.kotlin.fir.FirTarget;
import org.jetbrains.kotlin.fir.declarations.FirFunction;
import org.jetbrains.kotlin.fir.types.FirTypeRef;
import org.jetbrains.kotlin.fir.visitors.FirTransformer;
import org.jetbrains.kotlin.fir.visitors.FirVisitor;

/* JADX INFO: compiled from: FirReturnExpression.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000Z\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\b&\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0003J5\u0010\u0019\u001a\u0002H\u001a\"\u0004\b\u0000\u0010\u001a\"\u0004\b\u0001\u0010\u001b2\u0012\u0010\u001c\u001a\u000e\u0012\u0004\u0012\u0002H\u001a\u0012\u0004\u0012\u0002H\u001b0\u001d2\u0006\u0010\u001e\u001a\u0002H\u001bH\u0016¢\u0006\u0002\u0010\u001fJ\u0016\u0010 \u001a\u00020!2\f\u0010\"\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005H&J\u0010\u0010#\u001a\u00020!2\u0006\u0010$\u001a\u00020\u0016H&J3\u0010%\u001a\u0002H&\"\b\b\u0000\u0010&*\u00020'\"\u0004\b\u0001\u0010\u001b2\f\u0010(\u001a\b\u0012\u0004\u0012\u0002H\u001b0)2\u0006\u0010\u001e\u001a\u0002H\u001bH\u0016¢\u0006\u0002\u0010*J)\u0010+\u001a\u00020\u0000\"\u0004\b\u0000\u0010\u001b2\f\u0010(\u001a\b\u0012\u0004\u0012\u0002H\u001b0)2\u0006\u0010\u001e\u001a\u0002H\u001bH&¢\u0006\u0002\u0010,J)\u0010-\u001a\u00020\u0000\"\u0004\b\u0000\u0010\u001b2\f\u0010(\u001a\b\u0012\u0004\u0012\u0002H\u001b0)2\u0006\u0010\u001e\u001a\u0002H\u001bH&¢\u0006\u0002\u0010,J)\u0010.\u001a\u00020\u0000\"\u0004\b\u0000\u0010\u001b2\f\u0010(\u001a\b\u0012\u0004\u0012\u0002H\u001b0)2\u0006\u0010\u001e\u001a\u0002H\u001bH&¢\u0006\u0002\u0010,R\u0018\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0007\u0010\bR\u0012\u0010\t\u001a\u00020\nX¦\u0004¢\u0006\u0006\u001a\u0004\b\u000b\u0010\fR\u0014\u0010\r\u001a\u0004\u0018\u00010\u000eX¦\u0004¢\u0006\u0006\u001a\u0004\b\u000f\u0010\u0010R\u0018\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00020\u0012X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0013\u0010\u0014R\u0012\u0010\u0015\u001a\u00020\u0016X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0017\u0010\u0018¨\u0006/"}, d2 = {"Lorg/jetbrains/kotlin/fir/expressions/FirReturnExpression;", "Lorg/jetbrains/kotlin/fir/expressions/FirJump;", "Lorg/jetbrains/kotlin/fir/declarations/FirFunction;", "()V", "annotations", "", "Lorg/jetbrains/kotlin/fir/expressions/FirAnnotation;", "getAnnotations", "()Ljava/util/List;", "result", "Lorg/jetbrains/kotlin/fir/expressions/FirExpression;", "getResult", "()Lorg/jetbrains/kotlin/fir/expressions/FirExpression;", FirebaseAnalytics.Param.SOURCE, "Lorg/jetbrains/kotlin/KtSourceElement;", "getSource", "()Lorg/jetbrains/kotlin/KtSourceElement;", "target", "Lorg/jetbrains/kotlin/fir/FirTarget;", "getTarget", "()Lorg/jetbrains/kotlin/fir/FirTarget;", "typeRef", "Lorg/jetbrains/kotlin/fir/types/FirTypeRef;", "getTypeRef", "()Lorg/jetbrains/kotlin/fir/types/FirTypeRef;", "accept", "R", "D", "visitor", "Lorg/jetbrains/kotlin/fir/visitors/FirVisitor;", "data", "(Lorg/jetbrains/kotlin/fir/visitors/FirVisitor;Ljava/lang/Object;)Ljava/lang/Object;", "replaceAnnotations", "", "newAnnotations", "replaceTypeRef", "newTypeRef", "transform", ExifInterface.LONGITUDE_EAST, "Lorg/jetbrains/kotlin/fir/FirElement;", "transformer", "Lorg/jetbrains/kotlin/fir/visitors/FirTransformer;", "(Lorg/jetbrains/kotlin/fir/visitors/FirTransformer;Ljava/lang/Object;)Lorg/jetbrains/kotlin/fir/FirElement;", "transformAnnotations", "(Lorg/jetbrains/kotlin/fir/visitors/FirTransformer;Ljava/lang/Object;)Lorg/jetbrains/kotlin/fir/expressions/FirReturnExpression;", "transformOtherChildren", "transformResult", Constants.TYPE_TREE}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class FirReturnExpression extends FirJump<FirFunction> {
    @Override // org.jetbrains.kotlin.fir.expressions.FirJump, org.jetbrains.kotlin.fir.expressions.FirExpression, org.jetbrains.kotlin.fir.expressions.FirStatement, org.jetbrains.kotlin.fir.FirAnnotationContainer
    public abstract List<FirAnnotation> getAnnotations();

    public abstract FirExpression getResult();

    @Override // org.jetbrains.kotlin.fir.expressions.FirJump, org.jetbrains.kotlin.fir.expressions.FirExpression, org.jetbrains.kotlin.fir.FirElement
    public abstract KtSourceElement getSource();

    @Override // org.jetbrains.kotlin.fir.expressions.FirJump
    public abstract FirTarget<FirFunction> getTarget();

    @Override // org.jetbrains.kotlin.fir.expressions.FirJump, org.jetbrains.kotlin.fir.expressions.FirExpression
    public abstract FirTypeRef getTypeRef();

    @Override // org.jetbrains.kotlin.fir.expressions.FirJump, org.jetbrains.kotlin.fir.expressions.FirExpression, org.jetbrains.kotlin.fir.expressions.FirStatement, org.jetbrains.kotlin.fir.FirAnnotationContainer
    public abstract void replaceAnnotations(List<? extends FirAnnotation> newAnnotations);

    @Override // org.jetbrains.kotlin.fir.expressions.FirJump, org.jetbrains.kotlin.fir.expressions.FirExpression
    public abstract void replaceTypeRef(FirTypeRef newTypeRef);

    @Override // org.jetbrains.kotlin.fir.expressions.FirJump, org.jetbrains.kotlin.fir.expressions.FirExpression, org.jetbrains.kotlin.fir.expressions.FirStatement, org.jetbrains.kotlin.fir.FirAnnotationContainer
    public abstract <D> FirReturnExpression transformAnnotations(FirTransformer<? super D> transformer, D data);

    public abstract <D> FirReturnExpression transformOtherChildren(FirTransformer<? super D> transformer, D data);

    public abstract <D> FirReturnExpression transformResult(FirTransformer<? super D> transformer, D data);

    public FirReturnExpression() {
        super(null);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.jetbrains.kotlin.fir.expressions.FirJump, org.jetbrains.kotlin.fir.expressions.FirExpression, org.jetbrains.kotlin.fir.FirElement
    public <R, D> R accept(FirVisitor<? extends R, ? super D> visitor, D data) {
        Intrinsics.checkNotNullParameter(visitor, "visitor");
        return visitor.visitReturnExpression(this, data);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.jetbrains.kotlin.fir.expressions.FirJump, org.jetbrains.kotlin.fir.expressions.FirExpression, org.jetbrains.kotlin.fir.FirElement
    public <E extends FirElement, D> E transform(FirTransformer<? super D> transformer, D data) {
        Intrinsics.checkNotNullParameter(transformer, "transformer");
        FirStatement firStatementTransformReturnExpression = transformer.transformReturnExpression(this, data);
        Intrinsics.checkNotNull(firStatementTransformReturnExpression, "null cannot be cast to non-null type E of org.jetbrains.kotlin.fir.expressions.FirReturnExpression.transform");
        return firStatementTransformReturnExpression;
    }
}
