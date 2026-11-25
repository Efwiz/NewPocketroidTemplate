package org.jetbrains.kotlin.fir.expressions;

import androidx.exifinterface.media.ExifInterface;
import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.Constants;
import org.jetbrains.kotlin.KtSourceElement;
import org.jetbrains.kotlin.fir.FirElement;
import org.jetbrains.kotlin.fir.diagnostics.ConeDiagnostic;
import org.jetbrains.kotlin.fir.symbols.impl.FirClassLikeSymbol;
import org.jetbrains.kotlin.fir.types.FirTypeProjection;
import org.jetbrains.kotlin.fir.types.FirTypeRef;
import org.jetbrains.kotlin.fir.visitors.FirTransformer;
import org.jetbrains.kotlin.fir.visitors.FirVisitor;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.name.FqName;
import org.mozilla.javascript.NativeSymbol;

/* JADX INFO: compiled from: FirResolvedQualifier.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000v\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\b&\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J5\u0010*\u001a\u0002H+\"\u0004\b\u0000\u0010+\"\u0004\b\u0001\u0010,2\u0012\u0010-\u001a\u000e\u0012\u0004\u0012\u0002H+\u0012\u0004\u0012\u0002H,0.2\u0006\u0010/\u001a\u0002H,H\u0016¢\u0006\u0002\u00100J\u0016\u00101\u001a\u0002022\f\u00103\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004H&J\u0010\u00104\u001a\u0002022\u0006\u00105\u001a\u00020\rH&J\u0010\u00106\u001a\u0002022\u0006\u00107\u001a\u00020\rH&J\u0016\u00108\u001a\u0002022\f\u00109\u001a\b\u0012\u0004\u0012\u00020$0\u0004H&J\u0010\u0010:\u001a\u0002022\u0006\u0010;\u001a\u00020'H&J3\u0010<\u001a\u0002H=\"\b\b\u0000\u0010=*\u00020>\"\u0004\b\u0001\u0010,2\f\u0010?\u001a\b\u0012\u0004\u0012\u0002H,0@2\u0006\u0010/\u001a\u0002H,H\u0016¢\u0006\u0002\u0010AJ)\u0010B\u001a\u00020\u0000\"\u0004\b\u0000\u0010,2\f\u0010?\u001a\b\u0012\u0004\u0012\u0002H,0@2\u0006\u0010/\u001a\u0002H,H&¢\u0006\u0002\u0010CJ)\u0010D\u001a\u00020\u0000\"\u0004\b\u0000\u0010,2\f\u0010?\u001a\b\u0012\u0004\u0012\u0002H,0@2\u0006\u0010/\u001a\u0002H,H&¢\u0006\u0002\u0010CR\u0018\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0006\u0010\u0007R\u0014\u0010\b\u001a\u0004\u0018\u00010\tX¦\u0004¢\u0006\u0006\u001a\u0004\b\n\u0010\u000bR\u0012\u0010\f\u001a\u00020\rX¦\u0004¢\u0006\u0006\u001a\u0004\b\f\u0010\u000eR\u0012\u0010\u000f\u001a\u00020\rX¦\u0004¢\u0006\u0006\u001a\u0004\b\u000f\u0010\u000eR\u0018\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00110\u0004X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0012\u0010\u0007R\u0012\u0010\u0013\u001a\u00020\u0014X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0015\u0010\u0016R\u0014\u0010\u0017\u001a\u0004\u0018\u00010\u0014X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0018\u0010\u0016R\u0012\u0010\u0019\u001a\u00020\rX¦\u0004¢\u0006\u0006\u001a\u0004\b\u001a\u0010\u000eR\u0014\u0010\u001b\u001a\u0004\u0018\u00010\u001cX¦\u0004¢\u0006\u0006\u001a\u0004\b\u001d\u0010\u001eR\u0018\u0010\u001f\u001a\b\u0012\u0002\b\u0003\u0018\u00010 X¦\u0004¢\u0006\u0006\u001a\u0004\b!\u0010\"R\u0018\u0010#\u001a\b\u0012\u0004\u0012\u00020$0\u0004X¦\u0004¢\u0006\u0006\u001a\u0004\b%\u0010\u0007R\u0012\u0010&\u001a\u00020'X¦\u0004¢\u0006\u0006\u001a\u0004\b(\u0010)¨\u0006E"}, d2 = {"Lorg/jetbrains/kotlin/fir/expressions/FirResolvedQualifier;", "Lorg/jetbrains/kotlin/fir/expressions/FirExpression;", "()V", "annotations", "", "Lorg/jetbrains/kotlin/fir/expressions/FirAnnotation;", "getAnnotations", "()Ljava/util/List;", "classId", "Lorg/jetbrains/kotlin/name/ClassId;", "getClassId", "()Lorg/jetbrains/kotlin/name/ClassId;", "isFullyQualified", "", "()Z", "isNullableLHSForCallableReference", "nonFatalDiagnostics", "Lorg/jetbrains/kotlin/fir/diagnostics/ConeDiagnostic;", "getNonFatalDiagnostics", "packageFqName", "Lorg/jetbrains/kotlin/name/FqName;", "getPackageFqName", "()Lorg/jetbrains/kotlin/name/FqName;", "relativeClassFqName", "getRelativeClassFqName", "resolvedToCompanionObject", "getResolvedToCompanionObject", FirebaseAnalytics.Param.SOURCE, "Lorg/jetbrains/kotlin/KtSourceElement;", "getSource", "()Lorg/jetbrains/kotlin/KtSourceElement;", NativeSymbol.TYPE_NAME, "Lorg/jetbrains/kotlin/fir/symbols/impl/FirClassLikeSymbol;", "getSymbol", "()Lorg/jetbrains/kotlin/fir/symbols/impl/FirClassLikeSymbol;", "typeArguments", "Lorg/jetbrains/kotlin/fir/types/FirTypeProjection;", "getTypeArguments", "typeRef", "Lorg/jetbrains/kotlin/fir/types/FirTypeRef;", "getTypeRef", "()Lorg/jetbrains/kotlin/fir/types/FirTypeRef;", "accept", "R", "D", "visitor", "Lorg/jetbrains/kotlin/fir/visitors/FirVisitor;", "data", "(Lorg/jetbrains/kotlin/fir/visitors/FirVisitor;Ljava/lang/Object;)Ljava/lang/Object;", "replaceAnnotations", "", "newAnnotations", "replaceIsNullableLHSForCallableReference", "newIsNullableLHSForCallableReference", "replaceResolvedToCompanionObject", "newResolvedToCompanionObject", "replaceTypeArguments", "newTypeArguments", "replaceTypeRef", "newTypeRef", "transform", ExifInterface.LONGITUDE_EAST, "Lorg/jetbrains/kotlin/fir/FirElement;", "transformer", "Lorg/jetbrains/kotlin/fir/visitors/FirTransformer;", "(Lorg/jetbrains/kotlin/fir/visitors/FirTransformer;Ljava/lang/Object;)Lorg/jetbrains/kotlin/fir/FirElement;", "transformAnnotations", "(Lorg/jetbrains/kotlin/fir/visitors/FirTransformer;Ljava/lang/Object;)Lorg/jetbrains/kotlin/fir/expressions/FirResolvedQualifier;", "transformTypeArguments", Constants.TYPE_TREE}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class FirResolvedQualifier extends FirExpression {
    @Override // org.jetbrains.kotlin.fir.expressions.FirExpression, org.jetbrains.kotlin.fir.expressions.FirStatement, org.jetbrains.kotlin.fir.FirAnnotationContainer
    public abstract List<FirAnnotation> getAnnotations();

    public abstract ClassId getClassId();

    public abstract List<ConeDiagnostic> getNonFatalDiagnostics();

    public abstract FqName getPackageFqName();

    public abstract FqName getRelativeClassFqName();

    public abstract boolean getResolvedToCompanionObject();

    @Override // org.jetbrains.kotlin.fir.expressions.FirExpression, org.jetbrains.kotlin.fir.FirElement
    public abstract KtSourceElement getSource();

    public abstract FirClassLikeSymbol<?> getSymbol();

    public abstract List<FirTypeProjection> getTypeArguments();

    @Override // org.jetbrains.kotlin.fir.expressions.FirExpression
    public abstract FirTypeRef getTypeRef();

    public abstract boolean isFullyQualified();

    public abstract boolean isNullableLHSForCallableReference();

    @Override // org.jetbrains.kotlin.fir.expressions.FirExpression, org.jetbrains.kotlin.fir.expressions.FirStatement, org.jetbrains.kotlin.fir.FirAnnotationContainer
    public abstract void replaceAnnotations(List<? extends FirAnnotation> newAnnotations);

    public abstract void replaceIsNullableLHSForCallableReference(boolean newIsNullableLHSForCallableReference);

    public abstract void replaceResolvedToCompanionObject(boolean newResolvedToCompanionObject);

    public abstract void replaceTypeArguments(List<? extends FirTypeProjection> newTypeArguments);

    @Override // org.jetbrains.kotlin.fir.expressions.FirExpression
    public abstract void replaceTypeRef(FirTypeRef newTypeRef);

    @Override // org.jetbrains.kotlin.fir.expressions.FirExpression, org.jetbrains.kotlin.fir.expressions.FirStatement, org.jetbrains.kotlin.fir.FirAnnotationContainer
    public abstract <D> FirResolvedQualifier transformAnnotations(FirTransformer<? super D> transformer, D data);

    public abstract <D> FirResolvedQualifier transformTypeArguments(FirTransformer<? super D> transformer, D data);

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.jetbrains.kotlin.fir.expressions.FirExpression, org.jetbrains.kotlin.fir.FirElement
    public <R, D> R accept(FirVisitor<? extends R, ? super D> visitor, D data) {
        Intrinsics.checkNotNullParameter(visitor, "visitor");
        return visitor.visitResolvedQualifier(this, data);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.jetbrains.kotlin.fir.expressions.FirExpression, org.jetbrains.kotlin.fir.FirElement
    public <E extends FirElement, D> E transform(FirTransformer<? super D> transformer, D data) {
        Intrinsics.checkNotNullParameter(transformer, "transformer");
        FirStatement firStatementTransformResolvedQualifier = transformer.transformResolvedQualifier(this, data);
        Intrinsics.checkNotNull(firStatementTransformResolvedQualifier, "null cannot be cast to non-null type E of org.jetbrains.kotlin.fir.expressions.FirResolvedQualifier.transform");
        return firStatementTransformResolvedQualifier;
    }
}
