package org.jetbrains.kotlin.fir.declarations;

import androidx.core.app.NotificationCompat;
import androidx.exifinterface.media.ExifInterface;
import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.Constants;
import org.jetbrains.kotlin.KtSourceElement;
import org.jetbrains.kotlin.fir.FirElement;
import org.jetbrains.kotlin.fir.FirModuleData;
import org.jetbrains.kotlin.fir.expressions.FirAnnotation;
import org.jetbrains.kotlin.fir.expressions.FirStatement;
import org.jetbrains.kotlin.fir.symbols.impl.FirTypeAliasSymbol;
import org.jetbrains.kotlin.fir.types.FirTypeRef;
import org.jetbrains.kotlin.fir.visitors.FirTransformer;
import org.jetbrains.kotlin.fir.visitors.FirVisitor;
import org.jetbrains.kotlin.name.Name;
import org.mozilla.javascript.NativeSymbol;

/* JADX INFO: compiled from: FirTypeAlias.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u008a\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\b&\u0018\u00002\u00020\u00012\u00020\u0002B\u0005¢\u0006\u0002\u0010\u0003J5\u00100\u001a\u0002H1\"\u0004\b\u0000\u00101\"\u0004\b\u0001\u001022\u0012\u00103\u001a\u000e\u0012\u0004\u0012\u0002H1\u0012\u0004\u0012\u0002H2042\u0006\u00105\u001a\u0002H2H\u0016¢\u0006\u0002\u00106J\u0016\u00107\u001a\u0002082\f\u00109\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005H&J\u0010\u0010:\u001a\u0002082\u0006\u0010;\u001a\u00020\u000eH&J\u0010\u0010<\u001a\u0002082\u0006\u0010=\u001a\u00020\u0012H&J\u0010\u0010>\u001a\u0002082\u0006\u0010?\u001a\u00020&H&J3\u0010@\u001a\u0002HA\"\b\b\u0000\u0010A*\u00020B\"\u0004\b\u0001\u001022\f\u0010C\u001a\b\u0012\u0004\u0012\u0002H20D2\u0006\u00105\u001a\u0002H2H\u0016¢\u0006\u0002\u0010EJ)\u0010F\u001a\u00020\u0000\"\u0004\b\u0000\u001022\f\u0010C\u001a\b\u0012\u0004\u0012\u0002H20D2\u0006\u00105\u001a\u0002H2H&¢\u0006\u0002\u0010GJ)\u0010H\u001a\u00020\u0000\"\u0004\b\u0000\u001022\f\u0010C\u001a\b\u0012\u0004\u0012\u0002H20D2\u0006\u00105\u001a\u0002H2H&¢\u0006\u0002\u0010GJ)\u0010I\u001a\u00020\u0000\"\u0004\b\u0000\u001022\f\u0010C\u001a\b\u0012\u0004\u0012\u0002H20D2\u0006\u00105\u001a\u0002H2H&¢\u0006\u0002\u0010GJ)\u0010J\u001a\u00020\u0000\"\u0004\b\u0000\u001022\f\u0010C\u001a\b\u0012\u0004\u0012\u0002H20D2\u0006\u00105\u001a\u0002H2H&¢\u0006\u0002\u0010GR\u0018\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0007\u0010\bR\u0012\u0010\t\u001a\u00020\nX¦\u0004¢\u0006\u0006\u001a\u0004\b\u000b\u0010\fR\u0012\u0010\r\u001a\u00020\u000eX¦\u0004¢\u0006\u0006\u001a\u0004\b\u000f\u0010\u0010R\u0012\u0010\u0011\u001a\u00020\u0012X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0013\u0010\u0014R\u0012\u0010\u0015\u001a\u00020\u0016X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0017\u0010\u0018R\u0012\u0010\u0019\u001a\u00020\u001aX¦\u0004¢\u0006\u0006\u001a\u0004\b\u001b\u0010\u001cR\u0012\u0010\u001d\u001a\u00020\u001eX¦\u0004¢\u0006\u0006\u001a\u0004\b\u001f\u0010 R\u0014\u0010!\u001a\u0004\u0018\u00010\"X¦\u0004¢\u0006\u0006\u001a\u0004\b#\u0010$R\u0012\u0010%\u001a\u00020&X¦\u0004¢\u0006\u0006\u001a\u0004\b'\u0010(R\u0012\u0010)\u001a\u00020*X¦\u0004¢\u0006\u0006\u001a\u0004\b+\u0010,R\u0018\u0010-\u001a\b\u0012\u0004\u0012\u00020.0\u0005X¦\u0004¢\u0006\u0006\u001a\u0004\b/\u0010\b¨\u0006K"}, d2 = {"Lorg/jetbrains/kotlin/fir/declarations/FirTypeAlias;", "Lorg/jetbrains/kotlin/fir/declarations/FirClassLikeDeclaration;", "Lorg/jetbrains/kotlin/fir/declarations/FirTypeParametersOwner;", "()V", "annotations", "", "Lorg/jetbrains/kotlin/fir/expressions/FirAnnotation;", "getAnnotations", "()Ljava/util/List;", "attributes", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationAttributes;", "getAttributes", "()Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationAttributes;", "deprecationsProvider", "Lorg/jetbrains/kotlin/fir/declarations/DeprecationsProvider;", "getDeprecationsProvider", "()Lorg/jetbrains/kotlin/fir/declarations/DeprecationsProvider;", "expandedTypeRef", "Lorg/jetbrains/kotlin/fir/types/FirTypeRef;", "getExpandedTypeRef", "()Lorg/jetbrains/kotlin/fir/types/FirTypeRef;", "moduleData", "Lorg/jetbrains/kotlin/fir/FirModuleData;", "getModuleData", "()Lorg/jetbrains/kotlin/fir/FirModuleData;", "name", "Lorg/jetbrains/kotlin/name/Name;", "getName", "()Lorg/jetbrains/kotlin/name/Name;", "origin", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationOrigin;", "getOrigin", "()Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationOrigin;", FirebaseAnalytics.Param.SOURCE, "Lorg/jetbrains/kotlin/KtSourceElement;", "getSource", "()Lorg/jetbrains/kotlin/KtSourceElement;", NotificationCompat.CATEGORY_STATUS, "Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationStatus;", "getStatus", "()Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationStatus;", NativeSymbol.TYPE_NAME, "Lorg/jetbrains/kotlin/fir/symbols/impl/FirTypeAliasSymbol;", "getSymbol", "()Lorg/jetbrains/kotlin/fir/symbols/impl/FirTypeAliasSymbol;", "typeParameters", "Lorg/jetbrains/kotlin/fir/declarations/FirTypeParameter;", "getTypeParameters", "accept", "R", "D", "visitor", "Lorg/jetbrains/kotlin/fir/visitors/FirVisitor;", "data", "(Lorg/jetbrains/kotlin/fir/visitors/FirVisitor;Ljava/lang/Object;)Ljava/lang/Object;", "replaceAnnotations", "", "newAnnotations", "replaceDeprecationsProvider", "newDeprecationsProvider", "replaceExpandedTypeRef", "newExpandedTypeRef", "replaceStatus", "newStatus", "transform", ExifInterface.LONGITUDE_EAST, "Lorg/jetbrains/kotlin/fir/FirElement;", "transformer", "Lorg/jetbrains/kotlin/fir/visitors/FirTransformer;", "(Lorg/jetbrains/kotlin/fir/visitors/FirTransformer;Ljava/lang/Object;)Lorg/jetbrains/kotlin/fir/FirElement;", "transformAnnotations", "(Lorg/jetbrains/kotlin/fir/visitors/FirTransformer;Ljava/lang/Object;)Lorg/jetbrains/kotlin/fir/declarations/FirTypeAlias;", "transformExpandedTypeRef", "transformStatus", "transformTypeParameters", Constants.TYPE_TREE}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class FirTypeAlias extends FirClassLikeDeclaration implements FirTypeParametersOwner {
    @Override // org.jetbrains.kotlin.fir.declarations.FirClassLikeDeclaration, org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration, org.jetbrains.kotlin.fir.declarations.FirDeclaration, org.jetbrains.kotlin.fir.FirAnnotationContainer
    public abstract List<FirAnnotation> getAnnotations();

    @Override // org.jetbrains.kotlin.fir.declarations.FirClassLikeDeclaration, org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration, org.jetbrains.kotlin.fir.declarations.FirDeclaration
    public abstract FirDeclarationAttributes getAttributes();

    @Override // org.jetbrains.kotlin.fir.declarations.FirClassLikeDeclaration
    public abstract DeprecationsProvider getDeprecationsProvider();

    public abstract FirTypeRef getExpandedTypeRef();

    @Override // org.jetbrains.kotlin.fir.declarations.FirClassLikeDeclaration, org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration, org.jetbrains.kotlin.fir.declarations.FirDeclaration, org.jetbrains.kotlin.fir.FirElementWithResolveState
    public abstract FirModuleData getModuleData();

    public abstract Name getName();

    @Override // org.jetbrains.kotlin.fir.declarations.FirClassLikeDeclaration, org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration, org.jetbrains.kotlin.fir.declarations.FirDeclaration
    public abstract FirDeclarationOrigin getOrigin();

    @Override // org.jetbrains.kotlin.fir.declarations.FirClassLikeDeclaration, org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration, org.jetbrains.kotlin.fir.declarations.FirDeclaration, org.jetbrains.kotlin.fir.FirElementWithResolveState, org.jetbrains.kotlin.fir.FirElement
    public abstract KtSourceElement getSource();

    @Override // org.jetbrains.kotlin.fir.declarations.FirClassLikeDeclaration, org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration
    public abstract FirDeclarationStatus getStatus();

    @Override // org.jetbrains.kotlin.fir.declarations.FirClassLikeDeclaration, org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration, org.jetbrains.kotlin.fir.declarations.FirDeclaration
    public abstract FirTypeAliasSymbol getSymbol();

    @Override // org.jetbrains.kotlin.fir.declarations.FirClassLikeDeclaration, org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration, org.jetbrains.kotlin.fir.declarations.FirTypeParameterRefsOwner, org.jetbrains.kotlin.fir.declarations.FirTypeParametersOwner
    public abstract List<FirTypeParameter> getTypeParameters();

    @Override // org.jetbrains.kotlin.fir.declarations.FirClassLikeDeclaration, org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration, org.jetbrains.kotlin.fir.declarations.FirDeclaration, org.jetbrains.kotlin.fir.FirAnnotationContainer
    public abstract void replaceAnnotations(List<? extends FirAnnotation> newAnnotations);

    @Override // org.jetbrains.kotlin.fir.declarations.FirClassLikeDeclaration
    public abstract void replaceDeprecationsProvider(DeprecationsProvider newDeprecationsProvider);

    public abstract void replaceExpandedTypeRef(FirTypeRef newExpandedTypeRef);

    @Override // org.jetbrains.kotlin.fir.declarations.FirClassLikeDeclaration, org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration
    public abstract void replaceStatus(FirDeclarationStatus newStatus);

    @Override // org.jetbrains.kotlin.fir.declarations.FirClassLikeDeclaration, org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration, org.jetbrains.kotlin.fir.declarations.FirDeclaration, org.jetbrains.kotlin.fir.FirAnnotationContainer
    public abstract <D> FirTypeAlias transformAnnotations(FirTransformer<? super D> transformer, D data);

    public abstract <D> FirTypeAlias transformExpandedTypeRef(FirTransformer<? super D> transformer, D data);

    @Override // org.jetbrains.kotlin.fir.declarations.FirClassLikeDeclaration, org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration
    public abstract <D> FirTypeAlias transformStatus(FirTransformer<? super D> transformer, D data);

    @Override // org.jetbrains.kotlin.fir.declarations.FirClassLikeDeclaration, org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration, org.jetbrains.kotlin.fir.declarations.FirTypeParameterRefsOwner, org.jetbrains.kotlin.fir.declarations.FirTypeParametersOwner
    public abstract <D> FirTypeAlias transformTypeParameters(FirTransformer<? super D> transformer, D data);

    public FirTypeAlias() {
        super(null);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.jetbrains.kotlin.fir.declarations.FirClassLikeDeclaration, org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration, org.jetbrains.kotlin.fir.declarations.FirDeclaration, org.jetbrains.kotlin.fir.FirElementWithResolveState, org.jetbrains.kotlin.fir.FirElement
    public <R, D> R accept(FirVisitor<? extends R, ? super D> visitor, D data) {
        Intrinsics.checkNotNullParameter(visitor, "visitor");
        return visitor.visitTypeAlias(this, data);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.jetbrains.kotlin.fir.declarations.FirClassLikeDeclaration, org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration, org.jetbrains.kotlin.fir.declarations.FirDeclaration, org.jetbrains.kotlin.fir.FirElementWithResolveState, org.jetbrains.kotlin.fir.FirElement
    public <E extends FirElement, D> E transform(FirTransformer<? super D> transformer, D data) {
        Intrinsics.checkNotNullParameter(transformer, "transformer");
        FirStatement firStatementTransformTypeAlias = transformer.transformTypeAlias(this, data);
        Intrinsics.checkNotNull(firStatementTransformTypeAlias, "null cannot be cast to non-null type E of org.jetbrains.kotlin.fir.declarations.FirTypeAlias.transform");
        return firStatementTransformTypeAlias;
    }
}
