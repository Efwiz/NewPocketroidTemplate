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
import org.jetbrains.kotlin.fir.diagnostics.FirDiagnosticHolder;
import org.jetbrains.kotlin.fir.symbols.impl.FirClassLikeSymbol;
import org.jetbrains.kotlin.fir.types.FirTypeProjection;
import org.jetbrains.kotlin.fir.types.FirTypeRef;
import org.jetbrains.kotlin.fir.visitors.FirTransformer;
import org.jetbrains.kotlin.fir.visitors.FirVisitor;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.name.FqName;
import org.mozilla.javascript.NativeSymbol;

/* JADX INFO: compiled from: FirErrorResolvedQualifier.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000z\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\b&\u0018\u00002\u00020\u00012\u00020\u0002B\u0005¢\u0006\u0002\u0010\u0003J5\u0010.\u001a\u0002H/\"\u0004\b\u0000\u0010/\"\u0004\b\u0001\u001002\u0012\u00101\u001a\u000e\u0012\u0004\u0012\u0002H/\u0012\u0004\u0012\u0002H0022\u0006\u00103\u001a\u0002H0H\u0016¢\u0006\u0002\u00104J\u0016\u00105\u001a\u0002062\f\u00107\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005H&J\u0010\u00108\u001a\u0002062\u0006\u00109\u001a\u00020\u0012H&J\u0010\u0010:\u001a\u0002062\u0006\u0010;\u001a\u00020\u0012H&J\u0016\u0010<\u001a\u0002062\f\u0010=\u001a\b\u0012\u0004\u0012\u00020(0\u0005H&J\u0010\u0010>\u001a\u0002062\u0006\u0010?\u001a\u00020+H&J3\u0010@\u001a\u0002HA\"\b\b\u0000\u0010A*\u00020B\"\u0004\b\u0001\u001002\f\u0010C\u001a\b\u0012\u0004\u0012\u0002H00D2\u0006\u00103\u001a\u0002H0H\u0016¢\u0006\u0002\u0010EJ)\u0010F\u001a\u00020\u0000\"\u0004\b\u0000\u001002\f\u0010C\u001a\b\u0012\u0004\u0012\u0002H00D2\u0006\u00103\u001a\u0002H0H&¢\u0006\u0002\u0010GJ)\u0010H\u001a\u00020\u0000\"\u0004\b\u0000\u001002\f\u0010C\u001a\b\u0012\u0004\u0012\u0002H00D2\u0006\u00103\u001a\u0002H0H&¢\u0006\u0002\u0010GR\u0018\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0007\u0010\bR\u0014\u0010\t\u001a\u0004\u0018\u00010\nX¦\u0004¢\u0006\u0006\u001a\u0004\b\u000b\u0010\fR\u0012\u0010\r\u001a\u00020\u000eX¦\u0004¢\u0006\u0006\u001a\u0004\b\u000f\u0010\u0010R\u0012\u0010\u0011\u001a\u00020\u0012X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0011\u0010\u0013R\u0012\u0010\u0014\u001a\u00020\u0012X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0014\u0010\u0013R\u0018\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u000e0\u0005X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0016\u0010\bR\u0012\u0010\u0017\u001a\u00020\u0018X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0019\u0010\u001aR\u0014\u0010\u001b\u001a\u0004\u0018\u00010\u0018X¦\u0004¢\u0006\u0006\u001a\u0004\b\u001c\u0010\u001aR\u0012\u0010\u001d\u001a\u00020\u0012X¦\u0004¢\u0006\u0006\u001a\u0004\b\u001e\u0010\u0013R\u0014\u0010\u001f\u001a\u0004\u0018\u00010 X¦\u0004¢\u0006\u0006\u001a\u0004\b!\u0010\"R\u0018\u0010#\u001a\b\u0012\u0002\b\u0003\u0018\u00010$X¦\u0004¢\u0006\u0006\u001a\u0004\b%\u0010&R\u0018\u0010'\u001a\b\u0012\u0004\u0012\u00020(0\u0005X¦\u0004¢\u0006\u0006\u001a\u0004\b)\u0010\bR\u0012\u0010*\u001a\u00020+X¦\u0004¢\u0006\u0006\u001a\u0004\b,\u0010-¨\u0006I"}, d2 = {"Lorg/jetbrains/kotlin/fir/expressions/FirErrorResolvedQualifier;", "Lorg/jetbrains/kotlin/fir/expressions/FirResolvedQualifier;", "Lorg/jetbrains/kotlin/fir/diagnostics/FirDiagnosticHolder;", "()V", "annotations", "", "Lorg/jetbrains/kotlin/fir/expressions/FirAnnotation;", "getAnnotations", "()Ljava/util/List;", "classId", "Lorg/jetbrains/kotlin/name/ClassId;", "getClassId", "()Lorg/jetbrains/kotlin/name/ClassId;", "diagnostic", "Lorg/jetbrains/kotlin/fir/diagnostics/ConeDiagnostic;", "getDiagnostic", "()Lorg/jetbrains/kotlin/fir/diagnostics/ConeDiagnostic;", "isFullyQualified", "", "()Z", "isNullableLHSForCallableReference", "nonFatalDiagnostics", "getNonFatalDiagnostics", "packageFqName", "Lorg/jetbrains/kotlin/name/FqName;", "getPackageFqName", "()Lorg/jetbrains/kotlin/name/FqName;", "relativeClassFqName", "getRelativeClassFqName", "resolvedToCompanionObject", "getResolvedToCompanionObject", FirebaseAnalytics.Param.SOURCE, "Lorg/jetbrains/kotlin/KtSourceElement;", "getSource", "()Lorg/jetbrains/kotlin/KtSourceElement;", NativeSymbol.TYPE_NAME, "Lorg/jetbrains/kotlin/fir/symbols/impl/FirClassLikeSymbol;", "getSymbol", "()Lorg/jetbrains/kotlin/fir/symbols/impl/FirClassLikeSymbol;", "typeArguments", "Lorg/jetbrains/kotlin/fir/types/FirTypeProjection;", "getTypeArguments", "typeRef", "Lorg/jetbrains/kotlin/fir/types/FirTypeRef;", "getTypeRef", "()Lorg/jetbrains/kotlin/fir/types/FirTypeRef;", "accept", "R", "D", "visitor", "Lorg/jetbrains/kotlin/fir/visitors/FirVisitor;", "data", "(Lorg/jetbrains/kotlin/fir/visitors/FirVisitor;Ljava/lang/Object;)Ljava/lang/Object;", "replaceAnnotations", "", "newAnnotations", "replaceIsNullableLHSForCallableReference", "newIsNullableLHSForCallableReference", "replaceResolvedToCompanionObject", "newResolvedToCompanionObject", "replaceTypeArguments", "newTypeArguments", "replaceTypeRef", "newTypeRef", "transform", ExifInterface.LONGITUDE_EAST, "Lorg/jetbrains/kotlin/fir/FirElement;", "transformer", "Lorg/jetbrains/kotlin/fir/visitors/FirTransformer;", "(Lorg/jetbrains/kotlin/fir/visitors/FirTransformer;Ljava/lang/Object;)Lorg/jetbrains/kotlin/fir/FirElement;", "transformAnnotations", "(Lorg/jetbrains/kotlin/fir/visitors/FirTransformer;Ljava/lang/Object;)Lorg/jetbrains/kotlin/fir/expressions/FirErrorResolvedQualifier;", "transformTypeArguments", Constants.TYPE_TREE}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class FirErrorResolvedQualifier extends FirResolvedQualifier implements FirDiagnosticHolder {
    @Override // org.jetbrains.kotlin.fir.expressions.FirResolvedQualifier, org.jetbrains.kotlin.fir.expressions.FirExpression, org.jetbrains.kotlin.fir.expressions.FirStatement, org.jetbrains.kotlin.fir.FirAnnotationContainer
    public abstract List<FirAnnotation> getAnnotations();

    @Override // org.jetbrains.kotlin.fir.expressions.FirResolvedQualifier
    public abstract ClassId getClassId();

    @Override // org.jetbrains.kotlin.fir.diagnostics.FirDiagnosticHolder
    public abstract ConeDiagnostic getDiagnostic();

    @Override // org.jetbrains.kotlin.fir.expressions.FirResolvedQualifier
    public abstract List<ConeDiagnostic> getNonFatalDiagnostics();

    @Override // org.jetbrains.kotlin.fir.expressions.FirResolvedQualifier
    public abstract FqName getPackageFqName();

    @Override // org.jetbrains.kotlin.fir.expressions.FirResolvedQualifier
    public abstract FqName getRelativeClassFqName();

    @Override // org.jetbrains.kotlin.fir.expressions.FirResolvedQualifier
    public abstract boolean getResolvedToCompanionObject();

    @Override // org.jetbrains.kotlin.fir.expressions.FirResolvedQualifier, org.jetbrains.kotlin.fir.expressions.FirExpression, org.jetbrains.kotlin.fir.FirElement
    public abstract KtSourceElement getSource();

    @Override // org.jetbrains.kotlin.fir.expressions.FirResolvedQualifier
    public abstract FirClassLikeSymbol<?> getSymbol();

    @Override // org.jetbrains.kotlin.fir.expressions.FirResolvedQualifier
    public abstract List<FirTypeProjection> getTypeArguments();

    @Override // org.jetbrains.kotlin.fir.expressions.FirResolvedQualifier, org.jetbrains.kotlin.fir.expressions.FirExpression
    public abstract FirTypeRef getTypeRef();

    @Override // org.jetbrains.kotlin.fir.expressions.FirResolvedQualifier
    /* JADX INFO: renamed from: isFullyQualified */
    public abstract boolean getIsFullyQualified();

    @Override // org.jetbrains.kotlin.fir.expressions.FirResolvedQualifier
    /* JADX INFO: renamed from: isNullableLHSForCallableReference */
    public abstract boolean getIsNullableLHSForCallableReference();

    @Override // org.jetbrains.kotlin.fir.expressions.FirResolvedQualifier, org.jetbrains.kotlin.fir.expressions.FirExpression, org.jetbrains.kotlin.fir.expressions.FirStatement, org.jetbrains.kotlin.fir.FirAnnotationContainer
    public abstract void replaceAnnotations(List<? extends FirAnnotation> newAnnotations);

    @Override // org.jetbrains.kotlin.fir.expressions.FirResolvedQualifier
    public abstract void replaceIsNullableLHSForCallableReference(boolean newIsNullableLHSForCallableReference);

    @Override // org.jetbrains.kotlin.fir.expressions.FirResolvedQualifier
    public abstract void replaceResolvedToCompanionObject(boolean newResolvedToCompanionObject);

    @Override // org.jetbrains.kotlin.fir.expressions.FirResolvedQualifier
    public abstract void replaceTypeArguments(List<? extends FirTypeProjection> newTypeArguments);

    @Override // org.jetbrains.kotlin.fir.expressions.FirResolvedQualifier, org.jetbrains.kotlin.fir.expressions.FirExpression
    public abstract void replaceTypeRef(FirTypeRef newTypeRef);

    @Override // org.jetbrains.kotlin.fir.expressions.FirResolvedQualifier, org.jetbrains.kotlin.fir.expressions.FirExpression, org.jetbrains.kotlin.fir.expressions.FirStatement, org.jetbrains.kotlin.fir.FirAnnotationContainer
    public abstract <D> FirErrorResolvedQualifier transformAnnotations(FirTransformer<? super D> transformer, D data);

    @Override // org.jetbrains.kotlin.fir.expressions.FirResolvedQualifier
    public abstract <D> FirErrorResolvedQualifier transformTypeArguments(FirTransformer<? super D> transformer, D data);

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.jetbrains.kotlin.fir.expressions.FirResolvedQualifier, org.jetbrains.kotlin.fir.expressions.FirExpression, org.jetbrains.kotlin.fir.FirElement
    public <R, D> R accept(FirVisitor<? extends R, ? super D> visitor, D data) {
        Intrinsics.checkNotNullParameter(visitor, "visitor");
        return visitor.visitErrorResolvedQualifier(this, data);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.jetbrains.kotlin.fir.expressions.FirResolvedQualifier, org.jetbrains.kotlin.fir.expressions.FirExpression, org.jetbrains.kotlin.fir.FirElement
    public <E extends FirElement, D> E transform(FirTransformer<? super D> transformer, D data) {
        Intrinsics.checkNotNullParameter(transformer, "transformer");
        FirStatement firStatementTransformErrorResolvedQualifier = transformer.transformErrorResolvedQualifier(this, data);
        Intrinsics.checkNotNull(firStatementTransformErrorResolvedQualifier, "null cannot be cast to non-null type E of org.jetbrains.kotlin.fir.expressions.FirErrorResolvedQualifier.transform");
        return firStatementTransformErrorResolvedQualifier;
    }
}
