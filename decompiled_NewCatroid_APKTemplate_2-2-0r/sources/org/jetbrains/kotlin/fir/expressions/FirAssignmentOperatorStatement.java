package org.jetbrains.kotlin.fir.expressions;

import androidx.exifinterface.media.ExifInterface;
import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.Constants;
import org.jetbrains.kotlin.KtSourceElement;
import org.jetbrains.kotlin.fir.FirElement;
import org.jetbrains.kotlin.fir.FirPureAbstractElement;
import org.jetbrains.kotlin.fir.visitors.FirTransformer;
import org.jetbrains.kotlin.fir.visitors.FirVisitor;

/* JADX INFO: compiled from: FirAssignmentOperatorStatement.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000R\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\b&\u0018\u00002\u00020\u00012\u00020\u0002B\u0005¢\u0006\u0002\u0010\u0003J5\u0010\u0017\u001a\u0002H\u0018\"\u0004\b\u0000\u0010\u0018\"\u0004\b\u0001\u0010\u00192\u0012\u0010\u001a\u001a\u000e\u0012\u0004\u0012\u0002H\u0018\u0012\u0004\u0012\u0002H\u00190\u001b2\u0006\u0010\u001c\u001a\u0002H\u0019H\u0016¢\u0006\u0002\u0010\u001dJ\u0016\u0010\u001e\u001a\u00020\u001f2\f\u0010 \u001a\b\u0012\u0004\u0012\u00020\u00060\u0005H&J3\u0010!\u001a\u0002H\"\"\b\b\u0000\u0010\"*\u00020#\"\u0004\b\u0001\u0010\u00192\f\u0010$\u001a\b\u0012\u0004\u0012\u0002H\u00190%2\u0006\u0010\u001c\u001a\u0002H\u0019H\u0016¢\u0006\u0002\u0010&J)\u0010'\u001a\u00020\u0000\"\u0004\b\u0000\u0010\u00192\f\u0010$\u001a\b\u0012\u0004\u0012\u0002H\u00190%2\u0006\u0010\u001c\u001a\u0002H\u0019H&¢\u0006\u0002\u0010(J)\u0010)\u001a\u00020\u0000\"\u0004\b\u0000\u0010\u00192\f\u0010$\u001a\b\u0012\u0004\u0012\u0002H\u00190%2\u0006\u0010\u001c\u001a\u0002H\u0019H&¢\u0006\u0002\u0010(J)\u0010*\u001a\u00020\u0000\"\u0004\b\u0000\u0010\u00192\f\u0010$\u001a\b\u0012\u0004\u0012\u0002H\u00190%2\u0006\u0010\u001c\u001a\u0002H\u0019H&¢\u0006\u0002\u0010(R\u0018\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0007\u0010\bR\u0012\u0010\t\u001a\u00020\nX¦\u0004¢\u0006\u0006\u001a\u0004\b\u000b\u0010\fR\u0012\u0010\r\u001a\u00020\u000eX¦\u0004¢\u0006\u0006\u001a\u0004\b\u000f\u0010\u0010R\u0012\u0010\u0011\u001a\u00020\nX¦\u0004¢\u0006\u0006\u001a\u0004\b\u0012\u0010\fR\u0014\u0010\u0013\u001a\u0004\u0018\u00010\u0014X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0015\u0010\u0016¨\u0006+"}, d2 = {"Lorg/jetbrains/kotlin/fir/expressions/FirAssignmentOperatorStatement;", "Lorg/jetbrains/kotlin/fir/FirPureAbstractElement;", "Lorg/jetbrains/kotlin/fir/expressions/FirStatement;", "()V", "annotations", "", "Lorg/jetbrains/kotlin/fir/expressions/FirAnnotation;", "getAnnotations", "()Ljava/util/List;", "leftArgument", "Lorg/jetbrains/kotlin/fir/expressions/FirExpression;", "getLeftArgument", "()Lorg/jetbrains/kotlin/fir/expressions/FirExpression;", "operation", "Lorg/jetbrains/kotlin/fir/expressions/FirOperation;", "getOperation", "()Lorg/jetbrains/kotlin/fir/expressions/FirOperation;", "rightArgument", "getRightArgument", FirebaseAnalytics.Param.SOURCE, "Lorg/jetbrains/kotlin/KtSourceElement;", "getSource", "()Lorg/jetbrains/kotlin/KtSourceElement;", "accept", "R", "D", "visitor", "Lorg/jetbrains/kotlin/fir/visitors/FirVisitor;", "data", "(Lorg/jetbrains/kotlin/fir/visitors/FirVisitor;Ljava/lang/Object;)Ljava/lang/Object;", "replaceAnnotations", "", "newAnnotations", "transform", ExifInterface.LONGITUDE_EAST, "Lorg/jetbrains/kotlin/fir/FirElement;", "transformer", "Lorg/jetbrains/kotlin/fir/visitors/FirTransformer;", "(Lorg/jetbrains/kotlin/fir/visitors/FirTransformer;Ljava/lang/Object;)Lorg/jetbrains/kotlin/fir/FirElement;", "transformAnnotations", "(Lorg/jetbrains/kotlin/fir/visitors/FirTransformer;Ljava/lang/Object;)Lorg/jetbrains/kotlin/fir/expressions/FirAssignmentOperatorStatement;", "transformLeftArgument", "transformRightArgument", Constants.TYPE_TREE}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class FirAssignmentOperatorStatement extends FirPureAbstractElement implements FirStatement {
    @Override // org.jetbrains.kotlin.fir.expressions.FirStatement, org.jetbrains.kotlin.fir.FirAnnotationContainer
    public abstract List<FirAnnotation> getAnnotations();

    public abstract FirExpression getLeftArgument();

    public abstract FirOperation getOperation();

    public abstract FirExpression getRightArgument();

    @Override // org.jetbrains.kotlin.fir.FirElement
    public abstract KtSourceElement getSource();

    @Override // org.jetbrains.kotlin.fir.expressions.FirStatement, org.jetbrains.kotlin.fir.FirAnnotationContainer
    public abstract void replaceAnnotations(List<? extends FirAnnotation> newAnnotations);

    @Override // org.jetbrains.kotlin.fir.expressions.FirStatement, org.jetbrains.kotlin.fir.FirAnnotationContainer
    public abstract <D> FirAssignmentOperatorStatement transformAnnotations(FirTransformer<? super D> transformer, D data);

    public abstract <D> FirAssignmentOperatorStatement transformLeftArgument(FirTransformer<? super D> transformer, D data);

    public abstract <D> FirAssignmentOperatorStatement transformRightArgument(FirTransformer<? super D> transformer, D data);

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.jetbrains.kotlin.fir.FirElement
    public <R, D> R accept(FirVisitor<? extends R, ? super D> visitor, D data) {
        Intrinsics.checkNotNullParameter(visitor, "visitor");
        return visitor.visitAssignmentOperatorStatement(this, data);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.jetbrains.kotlin.fir.FirElement
    public <E extends FirElement, D> E transform(FirTransformer<? super D> transformer, D data) {
        Intrinsics.checkNotNullParameter(transformer, "transformer");
        FirStatement firStatementTransformAssignmentOperatorStatement = transformer.transformAssignmentOperatorStatement(this, data);
        Intrinsics.checkNotNull(firStatementTransformAssignmentOperatorStatement, "null cannot be cast to non-null type E of org.jetbrains.kotlin.fir.expressions.FirAssignmentOperatorStatement.transform");
        return firStatementTransformAssignmentOperatorStatement;
    }
}
