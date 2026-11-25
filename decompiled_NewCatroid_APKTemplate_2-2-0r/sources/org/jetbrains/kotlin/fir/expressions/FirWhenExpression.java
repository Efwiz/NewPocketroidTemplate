package org.jetbrains.kotlin.fir.expressions;

import androidx.exifinterface.media.ExifInterface;
import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.Constants;
import org.jetbrains.kotlin.KtSourceElement;
import org.jetbrains.kotlin.fir.FirElement;
import org.jetbrains.kotlin.fir.declarations.FirVariable;
import org.jetbrains.kotlin.fir.references.FirReference;
import org.jetbrains.kotlin.fir.types.FirTypeRef;
import org.jetbrains.kotlin.fir.visitors.FirTransformer;
import org.jetbrains.kotlin.fir.visitors.FirVisitor;

/* JADX INFO: compiled from: FirWhenExpression.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000r\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\b&\u0018\u00002\u00020\u00012\u00020\u0002B\u0005¢\u0006\u0002\u0010\u0003J5\u0010'\u001a\u0002H(\"\u0004\b\u0000\u0010(\"\u0004\b\u0001\u0010)2\u0012\u0010*\u001a\u000e\u0012\u0004\u0012\u0002H(\u0012\u0004\u0012\u0002H)0+2\u0006\u0010,\u001a\u0002H)H\u0016¢\u0006\u0002\u0010-J\u0016\u0010.\u001a\u00020/2\f\u00100\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005H&J\u0010\u00101\u001a\u00020/2\u0006\u00102\u001a\u00020\rH&J\u0012\u00103\u001a\u00020/2\b\u00104\u001a\u0004\u0018\u00010\u0011H&J\u0010\u00105\u001a\u00020/2\u0006\u00106\u001a\u00020 H&J3\u00107\u001a\u0002H8\"\b\b\u0000\u00108*\u000209\"\u0004\b\u0001\u0010)2\f\u0010:\u001a\b\u0012\u0004\u0012\u0002H)0;2\u0006\u0010,\u001a\u0002H)H\u0016¢\u0006\u0002\u0010<J)\u0010=\u001a\u00020\u0000\"\u0004\b\u0000\u0010)2\f\u0010:\u001a\b\u0012\u0004\u0012\u0002H)0;2\u0006\u0010,\u001a\u0002H)H&¢\u0006\u0002\u0010>J)\u0010?\u001a\u00020\u0000\"\u0004\b\u0000\u0010)2\f\u0010:\u001a\b\u0012\u0004\u0012\u0002H)0;2\u0006\u0010,\u001a\u0002H)H&¢\u0006\u0002\u0010>J)\u0010@\u001a\u00020\u0000\"\u0004\b\u0000\u0010)2\f\u0010:\u001a\b\u0012\u0004\u0012\u0002H)0;2\u0006\u0010,\u001a\u0002H)H&¢\u0006\u0002\u0010>J)\u0010A\u001a\u00020\u0000\"\u0004\b\u0000\u0010)2\f\u0010:\u001a\b\u0012\u0004\u0012\u0002H)0;2\u0006\u0010,\u001a\u0002H)H&¢\u0006\u0002\u0010>J)\u0010B\u001a\u00020\u0000\"\u0004\b\u0000\u0010)2\f\u0010:\u001a\b\u0012\u0004\u0012\u0002H)0;2\u0006\u0010,\u001a\u0002H)H&¢\u0006\u0002\u0010>R\u0018\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0007\u0010\bR\u0018\u0010\t\u001a\b\u0012\u0004\u0012\u00020\n0\u0005X¦\u0004¢\u0006\u0006\u001a\u0004\b\u000b\u0010\bR\u0012\u0010\f\u001a\u00020\rX¦\u0004¢\u0006\u0006\u001a\u0004\b\u000e\u0010\u000fR\u0014\u0010\u0010\u001a\u0004\u0018\u00010\u0011X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0012\u0010\u0013R\u0014\u0010\u0014\u001a\u0004\u0018\u00010\u0015X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0016\u0010\u0017R\u0014\u0010\u0018\u001a\u0004\u0018\u00010\u0001X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0019\u0010\u001aR\u0014\u0010\u001b\u001a\u0004\u0018\u00010\u001cX¦\u0004¢\u0006\u0006\u001a\u0004\b\u001d\u0010\u001eR\u0012\u0010\u001f\u001a\u00020 X¦\u0004¢\u0006\u0006\u001a\u0004\b!\u0010\"R\u0012\u0010#\u001a\u00020$X¦\u0004¢\u0006\u0006\u001a\u0004\b%\u0010&¨\u0006C"}, d2 = {"Lorg/jetbrains/kotlin/fir/expressions/FirWhenExpression;", "Lorg/jetbrains/kotlin/fir/expressions/FirExpression;", "Lorg/jetbrains/kotlin/fir/expressions/FirResolvable;", "()V", "annotations", "", "Lorg/jetbrains/kotlin/fir/expressions/FirAnnotation;", "getAnnotations", "()Ljava/util/List;", "branches", "Lorg/jetbrains/kotlin/fir/expressions/FirWhenBranch;", "getBranches", "calleeReference", "Lorg/jetbrains/kotlin/fir/references/FirReference;", "getCalleeReference", "()Lorg/jetbrains/kotlin/fir/references/FirReference;", "exhaustivenessStatus", "Lorg/jetbrains/kotlin/fir/expressions/ExhaustivenessStatus;", "getExhaustivenessStatus", "()Lorg/jetbrains/kotlin/fir/expressions/ExhaustivenessStatus;", FirebaseAnalytics.Param.SOURCE, "Lorg/jetbrains/kotlin/KtSourceElement;", "getSource", "()Lorg/jetbrains/kotlin/KtSourceElement;", "subject", "getSubject", "()Lorg/jetbrains/kotlin/fir/expressions/FirExpression;", "subjectVariable", "Lorg/jetbrains/kotlin/fir/declarations/FirVariable;", "getSubjectVariable", "()Lorg/jetbrains/kotlin/fir/declarations/FirVariable;", "typeRef", "Lorg/jetbrains/kotlin/fir/types/FirTypeRef;", "getTypeRef", "()Lorg/jetbrains/kotlin/fir/types/FirTypeRef;", "usedAsExpression", "", "getUsedAsExpression", "()Z", "accept", "R", "D", "visitor", "Lorg/jetbrains/kotlin/fir/visitors/FirVisitor;", "data", "(Lorg/jetbrains/kotlin/fir/visitors/FirVisitor;Ljava/lang/Object;)Ljava/lang/Object;", "replaceAnnotations", "", "newAnnotations", "replaceCalleeReference", "newCalleeReference", "replaceExhaustivenessStatus", "newExhaustivenessStatus", "replaceTypeRef", "newTypeRef", "transform", ExifInterface.LONGITUDE_EAST, "Lorg/jetbrains/kotlin/fir/FirElement;", "transformer", "Lorg/jetbrains/kotlin/fir/visitors/FirTransformer;", "(Lorg/jetbrains/kotlin/fir/visitors/FirTransformer;Ljava/lang/Object;)Lorg/jetbrains/kotlin/fir/FirElement;", "transformAnnotations", "(Lorg/jetbrains/kotlin/fir/visitors/FirTransformer;Ljava/lang/Object;)Lorg/jetbrains/kotlin/fir/expressions/FirWhenExpression;", "transformBranches", "transformCalleeReference", "transformOtherChildren", "transformSubject", Constants.TYPE_TREE}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class FirWhenExpression extends FirExpression implements FirResolvable {
    @Override // org.jetbrains.kotlin.fir.expressions.FirExpression, org.jetbrains.kotlin.fir.expressions.FirStatement, org.jetbrains.kotlin.fir.FirAnnotationContainer
    public abstract List<FirAnnotation> getAnnotations();

    public abstract List<FirWhenBranch> getBranches();

    @Override // org.jetbrains.kotlin.fir.expressions.FirResolvable
    public abstract FirReference getCalleeReference();

    public abstract ExhaustivenessStatus getExhaustivenessStatus();

    @Override // org.jetbrains.kotlin.fir.expressions.FirExpression, org.jetbrains.kotlin.fir.FirElement
    public abstract KtSourceElement getSource();

    public abstract FirExpression getSubject();

    public abstract FirVariable getSubjectVariable();

    @Override // org.jetbrains.kotlin.fir.expressions.FirExpression
    public abstract FirTypeRef getTypeRef();

    public abstract boolean getUsedAsExpression();

    @Override // org.jetbrains.kotlin.fir.expressions.FirExpression, org.jetbrains.kotlin.fir.expressions.FirStatement, org.jetbrains.kotlin.fir.FirAnnotationContainer
    public abstract void replaceAnnotations(List<? extends FirAnnotation> newAnnotations);

    @Override // org.jetbrains.kotlin.fir.expressions.FirResolvable
    public abstract void replaceCalleeReference(FirReference newCalleeReference);

    public abstract void replaceExhaustivenessStatus(ExhaustivenessStatus newExhaustivenessStatus);

    @Override // org.jetbrains.kotlin.fir.expressions.FirExpression
    public abstract void replaceTypeRef(FirTypeRef newTypeRef);

    @Override // org.jetbrains.kotlin.fir.expressions.FirExpression, org.jetbrains.kotlin.fir.expressions.FirStatement, org.jetbrains.kotlin.fir.FirAnnotationContainer
    public abstract <D> FirWhenExpression transformAnnotations(FirTransformer<? super D> transformer, D data);

    public abstract <D> FirWhenExpression transformBranches(FirTransformer<? super D> transformer, D data);

    @Override // org.jetbrains.kotlin.fir.expressions.FirResolvable
    public abstract <D> FirWhenExpression transformCalleeReference(FirTransformer<? super D> transformer, D data);

    public abstract <D> FirWhenExpression transformOtherChildren(FirTransformer<? super D> transformer, D data);

    public abstract <D> FirWhenExpression transformSubject(FirTransformer<? super D> transformer, D data);

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.jetbrains.kotlin.fir.expressions.FirExpression, org.jetbrains.kotlin.fir.FirElement
    public <R, D> R accept(FirVisitor<? extends R, ? super D> visitor, D data) {
        Intrinsics.checkNotNullParameter(visitor, "visitor");
        return visitor.visitWhenExpression(this, data);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.jetbrains.kotlin.fir.expressions.FirExpression, org.jetbrains.kotlin.fir.FirElement
    public <E extends FirElement, D> E transform(FirTransformer<? super D> transformer, D data) {
        Intrinsics.checkNotNullParameter(transformer, "transformer");
        FirStatement firStatementTransformWhenExpression = transformer.transformWhenExpression(this, data);
        Intrinsics.checkNotNull(firStatementTransformWhenExpression, "null cannot be cast to non-null type E of org.jetbrains.kotlin.fir.expressions.FirWhenExpression.transform");
        return firStatementTransformWhenExpression;
    }
}
