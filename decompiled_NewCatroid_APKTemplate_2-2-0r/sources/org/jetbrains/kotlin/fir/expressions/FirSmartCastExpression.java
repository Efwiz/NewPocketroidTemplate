package org.jetbrains.kotlin.fir.expressions;

import androidx.exifinterface.media.ExifInterface;
import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.Collection;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.Constants;
import org.jetbrains.kotlin.KtSourceElement;
import org.jetbrains.kotlin.fir.FirElement;
import org.jetbrains.kotlin.fir.types.ConeKotlinType;
import org.jetbrains.kotlin.fir.types.FirTypeRef;
import org.jetbrains.kotlin.fir.visitors.FirTransformer;
import org.jetbrains.kotlin.fir.visitors.FirVisitor;
import org.jetbrains.kotlin.types.SmartcastStability;

/* JADX INFO: compiled from: FirSmartCastExpression.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000b\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\b&\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J5\u0010#\u001a\u0002H$\"\u0004\b\u0000\u0010$\"\u0004\b\u0001\u0010%2\u0012\u0010&\u001a\u000e\u0012\u0004\u0012\u0002H$\u0012\u0004\u0012\u0002H%0'2\u0006\u0010(\u001a\u0002H%H\u0016¢\u0006\u0002\u0010)J\u0016\u0010*\u001a\u00020+2\f\u0010,\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004H&J\u0010\u0010-\u001a\u00020+2\u0006\u0010.\u001a\u00020\u0001H&J\u0010\u0010/\u001a\u00020+2\u0006\u00100\u001a\u00020\u0013H&J3\u00101\u001a\u0002H2\"\b\b\u0000\u00102*\u000203\"\u0004\b\u0001\u0010%2\f\u00104\u001a\b\u0012\u0004\u0012\u0002H%052\u0006\u0010(\u001a\u0002H%H\u0016¢\u0006\u0002\u00106J)\u00107\u001a\u00020\u0000\"\u0004\b\u0000\u0010%2\f\u00104\u001a\b\u0012\u0004\u0012\u0002H%052\u0006\u0010(\u001a\u0002H%H&¢\u0006\u0002\u00108J)\u00109\u001a\u00020\u0000\"\u0004\b\u0000\u0010%2\f\u00104\u001a\b\u0012\u0004\u0012\u0002H%052\u0006\u0010(\u001a\u0002H%H&¢\u0006\u0002\u00108R\u0018\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0006\u0010\u0007R\u0012\u0010\b\u001a\u00020\tX¦\u0004¢\u0006\u0006\u001a\u0004\b\b\u0010\nR\u0012\u0010\u000b\u001a\u00020\u0001X¦\u0004¢\u0006\u0006\u001a\u0004\b\f\u0010\rR\u0012\u0010\u000e\u001a\u00020\u000fX¦\u0004¢\u0006\u0006\u001a\u0004\b\u0010\u0010\u0011R\u0012\u0010\u0012\u001a\u00020\u0013X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0014\u0010\u0015R\u0014\u0010\u0016\u001a\u0004\u0018\u00010\u0013X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0017\u0010\u0015R\u0014\u0010\u0018\u001a\u0004\u0018\u00010\u0019X¦\u0004¢\u0006\u0006\u001a\u0004\b\u001a\u0010\u001bR\u0012\u0010\u001c\u001a\u00020\u0013X¦\u0004¢\u0006\u0006\u001a\u0004\b\u001d\u0010\u0015R\u0018\u0010\u001e\u001a\b\u0012\u0004\u0012\u00020 0\u001fX¦\u0004¢\u0006\u0006\u001a\u0004\b!\u0010\"¨\u0006:"}, d2 = {"Lorg/jetbrains/kotlin/fir/expressions/FirSmartCastExpression;", "Lorg/jetbrains/kotlin/fir/expressions/FirExpression;", "()V", "annotations", "", "Lorg/jetbrains/kotlin/fir/expressions/FirAnnotation;", "getAnnotations", "()Ljava/util/List;", "isStable", "", "()Z", "originalExpression", "getOriginalExpression", "()Lorg/jetbrains/kotlin/fir/expressions/FirExpression;", "smartcastStability", "Lorg/jetbrains/kotlin/types/SmartcastStability;", "getSmartcastStability", "()Lorg/jetbrains/kotlin/types/SmartcastStability;", "smartcastType", "Lorg/jetbrains/kotlin/fir/types/FirTypeRef;", "getSmartcastType", "()Lorg/jetbrains/kotlin/fir/types/FirTypeRef;", "smartcastTypeWithoutNullableNothing", "getSmartcastTypeWithoutNullableNothing", FirebaseAnalytics.Param.SOURCE, "Lorg/jetbrains/kotlin/KtSourceElement;", "getSource", "()Lorg/jetbrains/kotlin/KtSourceElement;", "typeRef", "getTypeRef", "typesFromSmartCast", "", "Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;", "getTypesFromSmartCast", "()Ljava/util/Collection;", "accept", "R", "D", "visitor", "Lorg/jetbrains/kotlin/fir/visitors/FirVisitor;", "data", "(Lorg/jetbrains/kotlin/fir/visitors/FirVisitor;Ljava/lang/Object;)Ljava/lang/Object;", "replaceAnnotations", "", "newAnnotations", "replaceOriginalExpression", "newOriginalExpression", "replaceTypeRef", "newTypeRef", "transform", ExifInterface.LONGITUDE_EAST, "Lorg/jetbrains/kotlin/fir/FirElement;", "transformer", "Lorg/jetbrains/kotlin/fir/visitors/FirTransformer;", "(Lorg/jetbrains/kotlin/fir/visitors/FirTransformer;Ljava/lang/Object;)Lorg/jetbrains/kotlin/fir/FirElement;", "transformAnnotations", "(Lorg/jetbrains/kotlin/fir/visitors/FirTransformer;Ljava/lang/Object;)Lorg/jetbrains/kotlin/fir/expressions/FirSmartCastExpression;", "transformOriginalExpression", Constants.TYPE_TREE}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class FirSmartCastExpression extends FirExpression {
    @Override // org.jetbrains.kotlin.fir.expressions.FirExpression, org.jetbrains.kotlin.fir.expressions.FirStatement, org.jetbrains.kotlin.fir.FirAnnotationContainer
    public abstract List<FirAnnotation> getAnnotations();

    public abstract FirExpression getOriginalExpression();

    public abstract SmartcastStability getSmartcastStability();

    public abstract FirTypeRef getSmartcastType();

    public abstract FirTypeRef getSmartcastTypeWithoutNullableNothing();

    @Override // org.jetbrains.kotlin.fir.expressions.FirExpression, org.jetbrains.kotlin.fir.FirElement
    public abstract KtSourceElement getSource();

    @Override // org.jetbrains.kotlin.fir.expressions.FirExpression
    public abstract FirTypeRef getTypeRef();

    public abstract Collection<ConeKotlinType> getTypesFromSmartCast();

    public abstract boolean isStable();

    @Override // org.jetbrains.kotlin.fir.expressions.FirExpression, org.jetbrains.kotlin.fir.expressions.FirStatement, org.jetbrains.kotlin.fir.FirAnnotationContainer
    public abstract void replaceAnnotations(List<? extends FirAnnotation> newAnnotations);

    public abstract void replaceOriginalExpression(FirExpression newOriginalExpression);

    @Override // org.jetbrains.kotlin.fir.expressions.FirExpression
    public abstract void replaceTypeRef(FirTypeRef newTypeRef);

    @Override // org.jetbrains.kotlin.fir.expressions.FirExpression, org.jetbrains.kotlin.fir.expressions.FirStatement, org.jetbrains.kotlin.fir.FirAnnotationContainer
    public abstract <D> FirSmartCastExpression transformAnnotations(FirTransformer<? super D> transformer, D data);

    public abstract <D> FirSmartCastExpression transformOriginalExpression(FirTransformer<? super D> transformer, D data);

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.jetbrains.kotlin.fir.expressions.FirExpression, org.jetbrains.kotlin.fir.FirElement
    public <R, D> R accept(FirVisitor<? extends R, ? super D> visitor, D data) {
        Intrinsics.checkNotNullParameter(visitor, "visitor");
        return visitor.visitSmartCastExpression(this, data);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.jetbrains.kotlin.fir.expressions.FirExpression, org.jetbrains.kotlin.fir.FirElement
    public <E extends FirElement, D> E transform(FirTransformer<? super D> transformer, D data) {
        Intrinsics.checkNotNullParameter(transformer, "transformer");
        FirStatement firStatementTransformSmartCastExpression = transformer.transformSmartCastExpression(this, data);
        Intrinsics.checkNotNull(firStatementTransformSmartCastExpression, "null cannot be cast to non-null type E of org.jetbrains.kotlin.fir.expressions.FirSmartCastExpression.transform");
        return firStatementTransformSmartCastExpression;
    }
}
