package org.jetbrains.kotlin.fir.expressions;

import androidx.exifinterface.media.ExifInterface;
import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.Constants;
import org.jetbrains.kotlin.KtSourceElement;
import org.jetbrains.kotlin.codegen.coroutines.CoroutineCodegenUtilKt;
import org.jetbrains.kotlin.fir.FirElement;
import org.jetbrains.kotlin.fir.FirLabel;
import org.jetbrains.kotlin.fir.visitors.FirTransformer;
import org.jetbrains.kotlin.fir.visitors.FirVisitor;

/* JADX INFO: compiled from: FirWhileLoop.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000V\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\b&\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J5\u0010\u0018\u001a\u0002H\u0019\"\u0004\b\u0000\u0010\u0019\"\u0004\b\u0001\u0010\u001a2\u0012\u0010\u001b\u001a\u000e\u0012\u0004\u0012\u0002H\u0019\u0012\u0004\u0012\u0002H\u001a0\u001c2\u0006\u0010\u001d\u001a\u0002H\u001aH\u0016¢\u0006\u0002\u0010\u001eJ\u0016\u0010\u001f\u001a\u00020 2\f\u0010!\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004H&J3\u0010\"\u001a\u0002H#\"\b\b\u0000\u0010#*\u00020$\"\u0004\b\u0001\u0010\u001a2\f\u0010%\u001a\b\u0012\u0004\u0012\u0002H\u001a0&2\u0006\u0010\u001d\u001a\u0002H\u001aH\u0016¢\u0006\u0002\u0010'J)\u0010(\u001a\u00020\u0000\"\u0004\b\u0000\u0010\u001a2\f\u0010%\u001a\b\u0012\u0004\u0012\u0002H\u001a0&2\u0006\u0010\u001d\u001a\u0002H\u001aH&¢\u0006\u0002\u0010)J)\u0010*\u001a\u00020\u0000\"\u0004\b\u0000\u0010\u001a2\f\u0010%\u001a\b\u0012\u0004\u0012\u0002H\u001a0&2\u0006\u0010\u001d\u001a\u0002H\u001aH&¢\u0006\u0002\u0010)J)\u0010+\u001a\u00020\u0000\"\u0004\b\u0000\u0010\u001a2\f\u0010%\u001a\b\u0012\u0004\u0012\u0002H\u001a0&2\u0006\u0010\u001d\u001a\u0002H\u001aH&¢\u0006\u0002\u0010)J)\u0010,\u001a\u00020\u0000\"\u0004\b\u0000\u0010\u001a2\f\u0010%\u001a\b\u0012\u0004\u0012\u0002H\u001a0&2\u0006\u0010\u001d\u001a\u0002H\u001aH&¢\u0006\u0002\u0010)R\u0018\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0006\u0010\u0007R\u0012\u0010\b\u001a\u00020\tX¦\u0004¢\u0006\u0006\u001a\u0004\b\n\u0010\u000bR\u0012\u0010\f\u001a\u00020\rX¦\u0004¢\u0006\u0006\u001a\u0004\b\u000e\u0010\u000fR\u0014\u0010\u0010\u001a\u0004\u0018\u00010\u0011X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0012\u0010\u0013R\u0014\u0010\u0014\u001a\u0004\u0018\u00010\u0015X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0016\u0010\u0017¨\u0006-"}, d2 = {"Lorg/jetbrains/kotlin/fir/expressions/FirWhileLoop;", "Lorg/jetbrains/kotlin/fir/expressions/FirLoop;", "()V", "annotations", "", "Lorg/jetbrains/kotlin/fir/expressions/FirAnnotation;", "getAnnotations", "()Ljava/util/List;", "block", "Lorg/jetbrains/kotlin/fir/expressions/FirBlock;", "getBlock", "()Lorg/jetbrains/kotlin/fir/expressions/FirBlock;", "condition", "Lorg/jetbrains/kotlin/fir/expressions/FirExpression;", "getCondition", "()Lorg/jetbrains/kotlin/fir/expressions/FirExpression;", CoroutineCodegenUtilKt.COROUTINE_LABEL_FIELD_NAME, "Lorg/jetbrains/kotlin/fir/FirLabel;", "getLabel", "()Lorg/jetbrains/kotlin/fir/FirLabel;", FirebaseAnalytics.Param.SOURCE, "Lorg/jetbrains/kotlin/KtSourceElement;", "getSource", "()Lorg/jetbrains/kotlin/KtSourceElement;", "accept", "R", "D", "visitor", "Lorg/jetbrains/kotlin/fir/visitors/FirVisitor;", "data", "(Lorg/jetbrains/kotlin/fir/visitors/FirVisitor;Ljava/lang/Object;)Ljava/lang/Object;", "replaceAnnotations", "", "newAnnotations", "transform", ExifInterface.LONGITUDE_EAST, "Lorg/jetbrains/kotlin/fir/FirElement;", "transformer", "Lorg/jetbrains/kotlin/fir/visitors/FirTransformer;", "(Lorg/jetbrains/kotlin/fir/visitors/FirTransformer;Ljava/lang/Object;)Lorg/jetbrains/kotlin/fir/FirElement;", "transformAnnotations", "(Lorg/jetbrains/kotlin/fir/visitors/FirTransformer;Ljava/lang/Object;)Lorg/jetbrains/kotlin/fir/expressions/FirWhileLoop;", "transformBlock", "transformCondition", "transformOtherChildren", Constants.TYPE_TREE}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class FirWhileLoop extends FirLoop {
    @Override // org.jetbrains.kotlin.fir.expressions.FirLoop, org.jetbrains.kotlin.fir.expressions.FirStatement, org.jetbrains.kotlin.fir.FirAnnotationContainer
    public abstract List<FirAnnotation> getAnnotations();

    @Override // org.jetbrains.kotlin.fir.expressions.FirLoop
    public abstract FirBlock getBlock();

    @Override // org.jetbrains.kotlin.fir.expressions.FirLoop
    public abstract FirExpression getCondition();

    @Override // org.jetbrains.kotlin.fir.expressions.FirLoop
    public abstract FirLabel getLabel();

    @Override // org.jetbrains.kotlin.fir.expressions.FirLoop, org.jetbrains.kotlin.fir.FirElement
    public abstract KtSourceElement getSource();

    @Override // org.jetbrains.kotlin.fir.expressions.FirLoop, org.jetbrains.kotlin.fir.expressions.FirStatement, org.jetbrains.kotlin.fir.FirAnnotationContainer
    public abstract void replaceAnnotations(List<? extends FirAnnotation> newAnnotations);

    @Override // org.jetbrains.kotlin.fir.expressions.FirLoop, org.jetbrains.kotlin.fir.expressions.FirStatement, org.jetbrains.kotlin.fir.FirAnnotationContainer
    public abstract <D> FirWhileLoop transformAnnotations(FirTransformer<? super D> transformer, D data);

    @Override // org.jetbrains.kotlin.fir.expressions.FirLoop
    public abstract <D> FirWhileLoop transformBlock(FirTransformer<? super D> transformer, D data);

    @Override // org.jetbrains.kotlin.fir.expressions.FirLoop
    public abstract <D> FirWhileLoop transformCondition(FirTransformer<? super D> transformer, D data);

    @Override // org.jetbrains.kotlin.fir.expressions.FirLoop
    public abstract <D> FirWhileLoop transformOtherChildren(FirTransformer<? super D> transformer, D data);

    public FirWhileLoop() {
        super(null);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.jetbrains.kotlin.fir.expressions.FirLoop, org.jetbrains.kotlin.fir.FirElement
    public <R, D> R accept(FirVisitor<? extends R, ? super D> visitor, D data) {
        Intrinsics.checkNotNullParameter(visitor, "visitor");
        return visitor.visitWhileLoop(this, data);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.jetbrains.kotlin.fir.expressions.FirLoop, org.jetbrains.kotlin.fir.FirElement
    public <E extends FirElement, D> E transform(FirTransformer<? super D> transformer, D data) {
        Intrinsics.checkNotNullParameter(transformer, "transformer");
        FirStatement firStatementTransformWhileLoop = transformer.transformWhileLoop(this, data);
        Intrinsics.checkNotNull(firStatementTransformWhileLoop, "null cannot be cast to non-null type E of org.jetbrains.kotlin.fir.expressions.FirWhileLoop.transform");
        return firStatementTransformWhileLoop;
    }
}
