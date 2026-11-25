package org.jetbrains.kotlin.fir.declarations;

import androidx.core.app.NotificationCompat;
import androidx.exifinterface.media.ExifInterface;
import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.Constants;
import org.jetbrains.kotlin.KtSourceElement;
import org.jetbrains.kotlin.descriptors.ClassKind;
import org.jetbrains.kotlin.fir.FirElement;
import org.jetbrains.kotlin.fir.FirModuleData;
import org.jetbrains.kotlin.fir.expressions.FirAnnotation;
import org.jetbrains.kotlin.fir.expressions.FirStatement;
import org.jetbrains.kotlin.fir.references.FirControlFlowGraphReference;
import org.jetbrains.kotlin.fir.scopes.FirScopeProvider;
import org.jetbrains.kotlin.fir.symbols.impl.FirClassSymbol;
import org.jetbrains.kotlin.fir.types.FirTypeRef;
import org.jetbrains.kotlin.fir.visitors.FirTransformer;
import org.jetbrains.kotlin.fir.visitors.FirVisitor;
import org.mozilla.javascript.NativeSymbol;

/* JADX INFO: compiled from: FirClass.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000´\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b6\u0018\u00002\u00020\u00012\u00020\u00022\u00020\u00032\u00020\u0004B\u0007\b\u0004¢\u0006\u0002\u0010\u0005J5\u0010<\u001a\u0002H=\"\u0004\b\u0000\u0010=\"\u0004\b\u0001\u0010>2\u0012\u0010?\u001a\u000e\u0012\u0004\u0012\u0002H=\u0012\u0004\u0012\u0002H>0@2\u0006\u0010A\u001a\u0002H>H\u0016¢\u0006\u0002\u0010BJ\u0016\u0010C\u001a\u00020D2\f\u0010E\u001a\b\u0012\u0004\u0012\u00020\b0\u0007H&J\u0012\u0010F\u001a\u00020D2\b\u0010G\u001a\u0004\u0018\u00010\u0014H&J\u0010\u0010H\u001a\u00020D2\u0006\u0010I\u001a\u00020\u001bH&J\u0010\u0010J\u001a\u00020D2\u0006\u0010K\u001a\u00020/H&J\u0016\u0010L\u001a\u00020D2\f\u0010M\u001a\b\u0012\u0004\u0012\u0002030\u0007H&J3\u0010N\u001a\u0002HO\"\b\b\u0000\u0010O*\u00020P\"\u0004\b\u0001\u0010>2\f\u0010Q\u001a\b\u0012\u0004\u0012\u0002H>0R2\u0006\u0010A\u001a\u0002H>H\u0016¢\u0006\u0002\u0010SJ)\u0010T\u001a\u00020\u0000\"\u0004\b\u0000\u0010>2\f\u0010Q\u001a\b\u0012\u0004\u0012\u0002H>0R2\u0006\u0010A\u001a\u0002H>H&¢\u0006\u0002\u0010UJ)\u0010V\u001a\u00020\u0000\"\u0004\b\u0000\u0010>2\f\u0010Q\u001a\b\u0012\u0004\u0012\u0002H>0R2\u0006\u0010A\u001a\u0002H>H&¢\u0006\u0002\u0010UJ)\u0010W\u001a\u00020\u0000\"\u0004\b\u0000\u0010>2\f\u0010Q\u001a\b\u0012\u0004\u0012\u0002H>0R2\u0006\u0010A\u001a\u0002H>H&¢\u0006\u0002\u0010UJ)\u0010X\u001a\u00020\u0000\"\u0004\b\u0000\u0010>2\f\u0010Q\u001a\b\u0012\u0004\u0012\u0002H>0R2\u0006\u0010A\u001a\u0002H>H&¢\u0006\u0002\u0010UJ)\u0010Y\u001a\u00020\u0000\"\u0004\b\u0000\u0010>2\f\u0010Q\u001a\b\u0012\u0004\u0012\u0002H>0R2\u0006\u0010A\u001a\u0002H>H&¢\u0006\u0002\u0010UR\u0018\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u0007X¦\u0004¢\u0006\u0006\u001a\u0004\b\t\u0010\nR\u0012\u0010\u000b\u001a\u00020\fX¦\u0004¢\u0006\u0006\u001a\u0004\b\r\u0010\u000eR\u0012\u0010\u000f\u001a\u00020\u0010X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0011\u0010\u0012R\u0014\u0010\u0013\u001a\u0004\u0018\u00010\u0014X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0015\u0010\u0016R\u0018\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u00180\u0007X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0019\u0010\nR\u0012\u0010\u001a\u001a\u00020\u001bX¦\u0004¢\u0006\u0006\u001a\u0004\b\u001c\u0010\u001dR\u0012\u0010\u001e\u001a\u00020\u001fX¦\u0004¢\u0006\u0006\u001a\u0004\b \u0010!R\u0012\u0010\"\u001a\u00020#X¦\u0004¢\u0006\u0006\u001a\u0004\b$\u0010%R\u0012\u0010&\u001a\u00020'X¦\u0004¢\u0006\u0006\u001a\u0004\b(\u0010)R\u0014\u0010*\u001a\u0004\u0018\u00010+X¦\u0004¢\u0006\u0006\u001a\u0004\b,\u0010-R\u0012\u0010.\u001a\u00020/X¦\u0004¢\u0006\u0006\u001a\u0004\b0\u00101R\u0018\u00102\u001a\b\u0012\u0004\u0012\u0002030\u0007X¦\u0004¢\u0006\u0006\u001a\u0004\b4\u0010\nR\u001a\u00105\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u000006X¦\u0004¢\u0006\u0006\u001a\u0004\b7\u00108R\u0018\u00109\u001a\b\u0012\u0004\u0012\u00020:0\u0007X¦\u0004¢\u0006\u0006\u001a\u0004\b;\u0010\n\u0082\u0001\u0002Z[¨\u0006\\"}, d2 = {"Lorg/jetbrains/kotlin/fir/declarations/FirClass;", "Lorg/jetbrains/kotlin/fir/declarations/FirClassLikeDeclaration;", "Lorg/jetbrains/kotlin/fir/expressions/FirStatement;", "Lorg/jetbrains/kotlin/fir/declarations/FirTypeParameterRefsOwner;", "Lorg/jetbrains/kotlin/fir/declarations/FirControlFlowGraphOwner;", "()V", "annotations", "", "Lorg/jetbrains/kotlin/fir/expressions/FirAnnotation;", "getAnnotations", "()Ljava/util/List;", "attributes", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationAttributes;", "getAttributes", "()Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationAttributes;", "classKind", "Lorg/jetbrains/kotlin/descriptors/ClassKind;", "getClassKind", "()Lorg/jetbrains/kotlin/descriptors/ClassKind;", "controlFlowGraphReference", "Lorg/jetbrains/kotlin/fir/references/FirControlFlowGraphReference;", "getControlFlowGraphReference", "()Lorg/jetbrains/kotlin/fir/references/FirControlFlowGraphReference;", "declarations", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclaration;", "getDeclarations", "deprecationsProvider", "Lorg/jetbrains/kotlin/fir/declarations/DeprecationsProvider;", "getDeprecationsProvider", "()Lorg/jetbrains/kotlin/fir/declarations/DeprecationsProvider;", "moduleData", "Lorg/jetbrains/kotlin/fir/FirModuleData;", "getModuleData", "()Lorg/jetbrains/kotlin/fir/FirModuleData;", "origin", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationOrigin;", "getOrigin", "()Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationOrigin;", "scopeProvider", "Lorg/jetbrains/kotlin/fir/scopes/FirScopeProvider;", "getScopeProvider", "()Lorg/jetbrains/kotlin/fir/scopes/FirScopeProvider;", FirebaseAnalytics.Param.SOURCE, "Lorg/jetbrains/kotlin/KtSourceElement;", "getSource", "()Lorg/jetbrains/kotlin/KtSourceElement;", NotificationCompat.CATEGORY_STATUS, "Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationStatus;", "getStatus", "()Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationStatus;", "superTypeRefs", "Lorg/jetbrains/kotlin/fir/types/FirTypeRef;", "getSuperTypeRefs", NativeSymbol.TYPE_NAME, "Lorg/jetbrains/kotlin/fir/symbols/impl/FirClassSymbol;", "getSymbol", "()Lorg/jetbrains/kotlin/fir/symbols/impl/FirClassSymbol;", "typeParameters", "Lorg/jetbrains/kotlin/fir/declarations/FirTypeParameterRef;", "getTypeParameters", "accept", "R", "D", "visitor", "Lorg/jetbrains/kotlin/fir/visitors/FirVisitor;", "data", "(Lorg/jetbrains/kotlin/fir/visitors/FirVisitor;Ljava/lang/Object;)Ljava/lang/Object;", "replaceAnnotations", "", "newAnnotations", "replaceControlFlowGraphReference", "newControlFlowGraphReference", "replaceDeprecationsProvider", "newDeprecationsProvider", "replaceStatus", "newStatus", "replaceSuperTypeRefs", "newSuperTypeRefs", "transform", ExifInterface.LONGITUDE_EAST, "Lorg/jetbrains/kotlin/fir/FirElement;", "transformer", "Lorg/jetbrains/kotlin/fir/visitors/FirTransformer;", "(Lorg/jetbrains/kotlin/fir/visitors/FirTransformer;Ljava/lang/Object;)Lorg/jetbrains/kotlin/fir/FirElement;", "transformAnnotations", "(Lorg/jetbrains/kotlin/fir/visitors/FirTransformer;Ljava/lang/Object;)Lorg/jetbrains/kotlin/fir/declarations/FirClass;", "transformDeclarations", "transformStatus", "transformSuperTypeRefs", "transformTypeParameters", "Lorg/jetbrains/kotlin/fir/declarations/FirAnonymousObject;", "Lorg/jetbrains/kotlin/fir/declarations/FirRegularClass;", Constants.TYPE_TREE}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class FirClass extends FirClassLikeDeclaration implements FirControlFlowGraphOwner, FirTypeParameterRefsOwner, FirStatement {
    public /* synthetic */ FirClass(DefaultConstructorMarker defaultConstructorMarker) {
        this();
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirClassLikeDeclaration, org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration, org.jetbrains.kotlin.fir.declarations.FirDeclaration, org.jetbrains.kotlin.fir.FirAnnotationContainer
    public abstract List<FirAnnotation> getAnnotations();

    @Override // org.jetbrains.kotlin.fir.declarations.FirClassLikeDeclaration, org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration, org.jetbrains.kotlin.fir.declarations.FirDeclaration
    public abstract FirDeclarationAttributes getAttributes();

    public abstract ClassKind getClassKind();

    public abstract FirControlFlowGraphReference getControlFlowGraphReference();

    public abstract List<FirDeclaration> getDeclarations();

    @Override // org.jetbrains.kotlin.fir.declarations.FirClassLikeDeclaration
    public abstract DeprecationsProvider getDeprecationsProvider();

    @Override // org.jetbrains.kotlin.fir.declarations.FirClassLikeDeclaration, org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration, org.jetbrains.kotlin.fir.declarations.FirDeclaration, org.jetbrains.kotlin.fir.FirElementWithResolveState
    public abstract FirModuleData getModuleData();

    @Override // org.jetbrains.kotlin.fir.declarations.FirClassLikeDeclaration, org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration, org.jetbrains.kotlin.fir.declarations.FirDeclaration
    public abstract FirDeclarationOrigin getOrigin();

    public abstract FirScopeProvider getScopeProvider();

    @Override // org.jetbrains.kotlin.fir.declarations.FirClassLikeDeclaration, org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration, org.jetbrains.kotlin.fir.declarations.FirDeclaration, org.jetbrains.kotlin.fir.FirElementWithResolveState, org.jetbrains.kotlin.fir.FirElement
    public abstract KtSourceElement getSource();

    @Override // org.jetbrains.kotlin.fir.declarations.FirClassLikeDeclaration, org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration
    public abstract FirDeclarationStatus getStatus();

    public abstract List<FirTypeRef> getSuperTypeRefs();

    @Override // org.jetbrains.kotlin.fir.declarations.FirClassLikeDeclaration, org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration, org.jetbrains.kotlin.fir.declarations.FirDeclaration
    public abstract FirClassSymbol<? extends FirClass> getSymbol();

    @Override // org.jetbrains.kotlin.fir.declarations.FirClassLikeDeclaration, org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration, org.jetbrains.kotlin.fir.declarations.FirTypeParameterRefsOwner, org.jetbrains.kotlin.fir.declarations.FirTypeParametersOwner
    public abstract List<FirTypeParameterRef> getTypeParameters();

    @Override // org.jetbrains.kotlin.fir.declarations.FirClassLikeDeclaration, org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration, org.jetbrains.kotlin.fir.declarations.FirDeclaration, org.jetbrains.kotlin.fir.FirAnnotationContainer
    public abstract void replaceAnnotations(List<? extends FirAnnotation> newAnnotations);

    public abstract void replaceControlFlowGraphReference(FirControlFlowGraphReference newControlFlowGraphReference);

    @Override // org.jetbrains.kotlin.fir.declarations.FirClassLikeDeclaration
    public abstract void replaceDeprecationsProvider(DeprecationsProvider newDeprecationsProvider);

    @Override // org.jetbrains.kotlin.fir.declarations.FirClassLikeDeclaration, org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration
    public abstract void replaceStatus(FirDeclarationStatus newStatus);

    public abstract void replaceSuperTypeRefs(List<? extends FirTypeRef> newSuperTypeRefs);

    @Override // org.jetbrains.kotlin.fir.declarations.FirClassLikeDeclaration, org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration, org.jetbrains.kotlin.fir.declarations.FirDeclaration, org.jetbrains.kotlin.fir.FirAnnotationContainer
    public abstract <D> FirClass transformAnnotations(FirTransformer<? super D> transformer, D data);

    public abstract <D> FirClass transformDeclarations(FirTransformer<? super D> transformer, D data);

    @Override // org.jetbrains.kotlin.fir.declarations.FirClassLikeDeclaration, org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration
    public abstract <D> FirClass transformStatus(FirTransformer<? super D> transformer, D data);

    public abstract <D> FirClass transformSuperTypeRefs(FirTransformer<? super D> transformer, D data);

    @Override // org.jetbrains.kotlin.fir.declarations.FirClassLikeDeclaration, org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration, org.jetbrains.kotlin.fir.declarations.FirTypeParameterRefsOwner, org.jetbrains.kotlin.fir.declarations.FirTypeParametersOwner
    public abstract <D> FirClass transformTypeParameters(FirTransformer<? super D> transformer, D data);

    private FirClass() {
        super(null);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.jetbrains.kotlin.fir.declarations.FirClassLikeDeclaration, org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration, org.jetbrains.kotlin.fir.declarations.FirDeclaration, org.jetbrains.kotlin.fir.FirElementWithResolveState, org.jetbrains.kotlin.fir.FirElement
    public <R, D> R accept(FirVisitor<? extends R, ? super D> visitor, D data) {
        Intrinsics.checkNotNullParameter(visitor, "visitor");
        return visitor.visitClass(this, data);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.jetbrains.kotlin.fir.declarations.FirClassLikeDeclaration, org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration, org.jetbrains.kotlin.fir.declarations.FirDeclaration, org.jetbrains.kotlin.fir.FirElementWithResolveState, org.jetbrains.kotlin.fir.FirElement
    public <E extends FirElement, D> E transform(FirTransformer<? super D> transformer, D data) {
        Intrinsics.checkNotNullParameter(transformer, "transformer");
        FirStatement firStatementTransformClass = transformer.transformClass(this, data);
        Intrinsics.checkNotNull(firStatementTransformClass, "null cannot be cast to non-null type E of org.jetbrains.kotlin.fir.declarations.FirClass.transform");
        return firStatementTransformClass;
    }
}
