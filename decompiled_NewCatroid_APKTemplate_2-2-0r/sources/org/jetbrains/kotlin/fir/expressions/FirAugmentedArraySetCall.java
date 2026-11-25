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
import org.jetbrains.kotlin.fir.references.FirReference;
import org.jetbrains.kotlin.fir.visitors.FirTransformer;
import org.jetbrains.kotlin.fir.visitors.FirVisitor;

/* JADX INFO: compiled from: FirAugmentedArraySetCall.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000b\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\b&\u0018\u00002\u00020\u00012\u00020\u0002B\u0005¢\u0006\u0002\u0010\u0003J5\u0010\u001f\u001a\u0002H \"\u0004\b\u0000\u0010 \"\u0004\b\u0001\u0010!2\u0012\u0010\"\u001a\u000e\u0012\u0004\u0012\u0002H \u0012\u0004\u0012\u0002H!0#2\u0006\u0010$\u001a\u0002H!H\u0016¢\u0006\u0002\u0010%J\u0016\u0010&\u001a\u00020'2\f\u0010(\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005H&J\u0010\u0010)\u001a\u00020'2\u0006\u0010*\u001a\u00020\u000eH&J3\u0010+\u001a\u0002H,\"\b\b\u0000\u0010,*\u00020-\"\u0004\b\u0001\u0010!2\f\u0010.\u001a\b\u0012\u0004\u0012\u0002H!0/2\u0006\u0010$\u001a\u0002H!H\u0016¢\u0006\u0002\u00100J)\u00101\u001a\u00020\u0000\"\u0004\b\u0000\u0010!2\f\u0010.\u001a\b\u0012\u0004\u0012\u0002H!0/2\u0006\u0010$\u001a\u0002H!H&¢\u0006\u0002\u00102R\u0018\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0007\u0010\bR\u0014\u0010\t\u001a\u0004\u0018\u00010\nX¦\u0004¢\u0006\u0006\u001a\u0004\b\u000b\u0010\fR\u0012\u0010\r\u001a\u00020\u000eX¦\u0004¢\u0006\u0006\u001a\u0004\b\u000f\u0010\u0010R\u0012\u0010\u0011\u001a\u00020\u0012X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0013\u0010\u0014R\u0012\u0010\u0015\u001a\u00020\u0016X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0017\u0010\u0018R\u0012\u0010\u0019\u001a\u00020\u001aX¦\u0004¢\u0006\u0006\u001a\u0004\b\u001b\u0010\u001cR\u0014\u0010\u001d\u001a\u0004\u0018\u00010\nX¦\u0004¢\u0006\u0006\u001a\u0004\b\u001e\u0010\f¨\u00063"}, d2 = {"Lorg/jetbrains/kotlin/fir/expressions/FirAugmentedArraySetCall;", "Lorg/jetbrains/kotlin/fir/FirPureAbstractElement;", "Lorg/jetbrains/kotlin/fir/expressions/FirStatement;", "()V", "annotations", "", "Lorg/jetbrains/kotlin/fir/expressions/FirAnnotation;", "getAnnotations", "()Ljava/util/List;", "arrayAccessSource", "Lorg/jetbrains/kotlin/KtSourceElement;", "getArrayAccessSource", "()Lorg/jetbrains/kotlin/KtSourceElement;", "calleeReference", "Lorg/jetbrains/kotlin/fir/references/FirReference;", "getCalleeReference", "()Lorg/jetbrains/kotlin/fir/references/FirReference;", "lhsGetCall", "Lorg/jetbrains/kotlin/fir/expressions/FirFunctionCall;", "getLhsGetCall", "()Lorg/jetbrains/kotlin/fir/expressions/FirFunctionCall;", "operation", "Lorg/jetbrains/kotlin/fir/expressions/FirOperation;", "getOperation", "()Lorg/jetbrains/kotlin/fir/expressions/FirOperation;", "rhs", "Lorg/jetbrains/kotlin/fir/expressions/FirExpression;", "getRhs", "()Lorg/jetbrains/kotlin/fir/expressions/FirExpression;", FirebaseAnalytics.Param.SOURCE, "getSource", "accept", "R", "D", "visitor", "Lorg/jetbrains/kotlin/fir/visitors/FirVisitor;", "data", "(Lorg/jetbrains/kotlin/fir/visitors/FirVisitor;Ljava/lang/Object;)Ljava/lang/Object;", "replaceAnnotations", "", "newAnnotations", "replaceCalleeReference", "newCalleeReference", "transform", ExifInterface.LONGITUDE_EAST, "Lorg/jetbrains/kotlin/fir/FirElement;", "transformer", "Lorg/jetbrains/kotlin/fir/visitors/FirTransformer;", "(Lorg/jetbrains/kotlin/fir/visitors/FirTransformer;Ljava/lang/Object;)Lorg/jetbrains/kotlin/fir/FirElement;", "transformAnnotations", "(Lorg/jetbrains/kotlin/fir/visitors/FirTransformer;Ljava/lang/Object;)Lorg/jetbrains/kotlin/fir/expressions/FirAugmentedArraySetCall;", Constants.TYPE_TREE}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class FirAugmentedArraySetCall extends FirPureAbstractElement implements FirStatement {
    @Override // org.jetbrains.kotlin.fir.expressions.FirStatement, org.jetbrains.kotlin.fir.FirAnnotationContainer
    public abstract List<FirAnnotation> getAnnotations();

    public abstract KtSourceElement getArrayAccessSource();

    public abstract FirReference getCalleeReference();

    public abstract FirFunctionCall getLhsGetCall();

    public abstract FirOperation getOperation();

    public abstract FirExpression getRhs();

    @Override // org.jetbrains.kotlin.fir.FirElement
    public abstract KtSourceElement getSource();

    @Override // org.jetbrains.kotlin.fir.expressions.FirStatement, org.jetbrains.kotlin.fir.FirAnnotationContainer
    public abstract void replaceAnnotations(List<? extends FirAnnotation> newAnnotations);

    public abstract void replaceCalleeReference(FirReference newCalleeReference);

    @Override // org.jetbrains.kotlin.fir.expressions.FirStatement, org.jetbrains.kotlin.fir.FirAnnotationContainer
    public abstract <D> FirAugmentedArraySetCall transformAnnotations(FirTransformer<? super D> transformer, D data);

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.jetbrains.kotlin.fir.FirElement
    public <R, D> R accept(FirVisitor<? extends R, ? super D> visitor, D data) {
        Intrinsics.checkNotNullParameter(visitor, "visitor");
        return visitor.visitAugmentedArraySetCall(this, data);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.jetbrains.kotlin.fir.FirElement
    public <E extends FirElement, D> E transform(FirTransformer<? super D> transformer, D data) {
        Intrinsics.checkNotNullParameter(transformer, "transformer");
        FirStatement firStatementTransformAugmentedArraySetCall = transformer.transformAugmentedArraySetCall(this, data);
        Intrinsics.checkNotNull(firStatementTransformAugmentedArraySetCall, "null cannot be cast to non-null type E of org.jetbrains.kotlin.fir.expressions.FirAugmentedArraySetCall.transform");
        return firStatementTransformAugmentedArraySetCall;
    }
}
