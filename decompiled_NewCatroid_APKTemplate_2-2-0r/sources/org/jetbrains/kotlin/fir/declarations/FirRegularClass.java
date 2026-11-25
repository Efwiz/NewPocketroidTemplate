package org.jetbrains.kotlin.fir.declarations;

import androidx.core.app.NotificationCompat;
import androidx.exifinterface.media.ExifInterface;
import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.List;
import kotlin.Metadata;
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
import org.jetbrains.kotlin.fir.symbols.impl.FirRegularClassSymbol;
import org.jetbrains.kotlin.fir.types.FirTypeRef;
import org.jetbrains.kotlin.fir.visitors.FirTransformer;
import org.jetbrains.kotlin.fir.visitors.FirVisitor;
import org.jetbrains.kotlin.name.Name;
import org.mozilla.javascript.NativeSymbol;

/* JADX INFO: compiled from: FirRegularClass.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000¶\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\r\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\b&\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J5\u0010F\u001a\u0002HG\"\u0004\b\u0000\u0010G\"\u0004\b\u0001\u0010H2\u0012\u0010I\u001a\u000e\u0012\u0004\u0012\u0002HG\u0012\u0004\u0012\u0002HH0J2\u0006\u0010K\u001a\u0002HHH\u0016¢\u0006\u0002\u0010LJ\u0016\u0010M\u001a\u00020N2\f\u0010O\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004H&J\u0012\u0010P\u001a\u00020N2\b\u0010Q\u001a\u0004\u0018\u00010\u0011H&J\u0012\u0010R\u001a\u00020N2\b\u0010S\u001a\u0004\u0018\u00010\u0018H&J\u0010\u0010T\u001a\u00020N2\u0006\u0010U\u001a\u00020\u001fH&J\u0010\u0010V\u001a\u00020N2\u0006\u0010W\u001a\u00020;H&J\u0016\u0010X\u001a\u00020N2\f\u0010Y\u001a\b\u0012\u0004\u0012\u00020?0\u0004H&J3\u0010Z\u001a\u0002H[\"\b\b\u0000\u0010[*\u00020\\\"\u0004\b\u0001\u0010H2\f\u0010]\u001a\b\u0012\u0004\u0012\u0002HH0^2\u0006\u0010K\u001a\u0002HHH\u0016¢\u0006\u0002\u0010_J)\u0010`\u001a\u00020\u0000\"\u0004\b\u0000\u0010H2\f\u0010]\u001a\b\u0012\u0004\u0012\u0002HH0^2\u0006\u0010K\u001a\u0002HHH&¢\u0006\u0002\u0010aJ)\u0010b\u001a\u00020\u0000\"\u0004\b\u0000\u0010H2\f\u0010]\u001a\b\u0012\u0004\u0012\u0002HH0^2\u0006\u0010K\u001a\u0002HHH&¢\u0006\u0002\u0010aJ)\u0010c\u001a\u00020\u0000\"\u0004\b\u0000\u0010H2\f\u0010]\u001a\b\u0012\u0004\u0012\u0002HH0^2\u0006\u0010K\u001a\u0002HHH&¢\u0006\u0002\u0010aJ)\u0010d\u001a\u00020\u0000\"\u0004\b\u0000\u0010H2\f\u0010]\u001a\b\u0012\u0004\u0012\u0002HH0^2\u0006\u0010K\u001a\u0002HHH&¢\u0006\u0002\u0010aJ)\u0010e\u001a\u00020\u0000\"\u0004\b\u0000\u0010H2\f\u0010]\u001a\b\u0012\u0004\u0012\u0002HH0^2\u0006\u0010K\u001a\u0002HHH&¢\u0006\u0002\u0010aR\u0018\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0006\u0010\u0007R\u0012\u0010\b\u001a\u00020\tX¦\u0004¢\u0006\u0006\u001a\u0004\b\n\u0010\u000bR\u0012\u0010\f\u001a\u00020\rX¦\u0004¢\u0006\u0006\u001a\u0004\b\u000e\u0010\u000fR\u0014\u0010\u0010\u001a\u0004\u0018\u00010\u0011X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0012\u0010\u0013R\u0018\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00150\u0004X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0016\u0010\u0007R\u0014\u0010\u0017\u001a\u0004\u0018\u00010\u0018X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0019\u0010\u001aR\u0018\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\u001c0\u0004X¦\u0004¢\u0006\u0006\u001a\u0004\b\u001d\u0010\u0007R\u0012\u0010\u001e\u001a\u00020\u001fX¦\u0004¢\u0006\u0006\u001a\u0004\b \u0010!R\u0012\u0010\"\u001a\u00020#X¦\u0004¢\u0006\u0006\u001a\u0004\b$\u0010%R\u0012\u0010&\u001a\u00020'X¦\u0004¢\u0006\u0006\u001a\u0004\b(\u0010)R\u0012\u0010*\u001a\u00020+X¦\u0004¢\u0006\u0006\u001a\u0004\b,\u0010-R\u0012\u0010.\u001a\u00020/X¦\u0004¢\u0006\u0006\u001a\u0004\b0\u00101R\u0012\u00102\u001a\u000203X¦\u0004¢\u0006\u0006\u001a\u0004\b4\u00105R\u0014\u00106\u001a\u0004\u0018\u000107X¦\u0004¢\u0006\u0006\u001a\u0004\b8\u00109R\u0012\u0010:\u001a\u00020;X¦\u0004¢\u0006\u0006\u001a\u0004\b<\u0010=R\u0018\u0010>\u001a\b\u0012\u0004\u0012\u00020?0\u0004X¦\u0004¢\u0006\u0006\u001a\u0004\b@\u0010\u0007R\u0012\u0010A\u001a\u00020\u0011X¦\u0004¢\u0006\u0006\u001a\u0004\bB\u0010\u0013R\u0018\u0010C\u001a\b\u0012\u0004\u0012\u00020D0\u0004X¦\u0004¢\u0006\u0006\u001a\u0004\bE\u0010\u0007¨\u0006f"}, d2 = {"Lorg/jetbrains/kotlin/fir/declarations/FirRegularClass;", "Lorg/jetbrains/kotlin/fir/declarations/FirClass;", "()V", "annotations", "", "Lorg/jetbrains/kotlin/fir/expressions/FirAnnotation;", "getAnnotations", "()Ljava/util/List;", "attributes", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationAttributes;", "getAttributes", "()Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationAttributes;", "classKind", "Lorg/jetbrains/kotlin/descriptors/ClassKind;", "getClassKind", "()Lorg/jetbrains/kotlin/descriptors/ClassKind;", "companionObjectSymbol", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirRegularClassSymbol;", "getCompanionObjectSymbol", "()Lorg/jetbrains/kotlin/fir/symbols/impl/FirRegularClassSymbol;", "contextReceivers", "Lorg/jetbrains/kotlin/fir/declarations/FirContextReceiver;", "getContextReceivers", "controlFlowGraphReference", "Lorg/jetbrains/kotlin/fir/references/FirControlFlowGraphReference;", "getControlFlowGraphReference", "()Lorg/jetbrains/kotlin/fir/references/FirControlFlowGraphReference;", "declarations", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclaration;", "getDeclarations", "deprecationsProvider", "Lorg/jetbrains/kotlin/fir/declarations/DeprecationsProvider;", "getDeprecationsProvider", "()Lorg/jetbrains/kotlin/fir/declarations/DeprecationsProvider;", "hasLazyNestedClassifiers", "", "getHasLazyNestedClassifiers", "()Z", "moduleData", "Lorg/jetbrains/kotlin/fir/FirModuleData;", "getModuleData", "()Lorg/jetbrains/kotlin/fir/FirModuleData;", "name", "Lorg/jetbrains/kotlin/name/Name;", "getName", "()Lorg/jetbrains/kotlin/name/Name;", "origin", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationOrigin;", "getOrigin", "()Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationOrigin;", "scopeProvider", "Lorg/jetbrains/kotlin/fir/scopes/FirScopeProvider;", "getScopeProvider", "()Lorg/jetbrains/kotlin/fir/scopes/FirScopeProvider;", FirebaseAnalytics.Param.SOURCE, "Lorg/jetbrains/kotlin/KtSourceElement;", "getSource", "()Lorg/jetbrains/kotlin/KtSourceElement;", NotificationCompat.CATEGORY_STATUS, "Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationStatus;", "getStatus", "()Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationStatus;", "superTypeRefs", "Lorg/jetbrains/kotlin/fir/types/FirTypeRef;", "getSuperTypeRefs", NativeSymbol.TYPE_NAME, "getSymbol", "typeParameters", "Lorg/jetbrains/kotlin/fir/declarations/FirTypeParameterRef;", "getTypeParameters", "accept", "R", "D", "visitor", "Lorg/jetbrains/kotlin/fir/visitors/FirVisitor;", "data", "(Lorg/jetbrains/kotlin/fir/visitors/FirVisitor;Ljava/lang/Object;)Ljava/lang/Object;", "replaceAnnotations", "", "newAnnotations", "replaceCompanionObjectSymbol", "newCompanionObjectSymbol", "replaceControlFlowGraphReference", "newControlFlowGraphReference", "replaceDeprecationsProvider", "newDeprecationsProvider", "replaceStatus", "newStatus", "replaceSuperTypeRefs", "newSuperTypeRefs", "transform", ExifInterface.LONGITUDE_EAST, "Lorg/jetbrains/kotlin/fir/FirElement;", "transformer", "Lorg/jetbrains/kotlin/fir/visitors/FirTransformer;", "(Lorg/jetbrains/kotlin/fir/visitors/FirTransformer;Ljava/lang/Object;)Lorg/jetbrains/kotlin/fir/FirElement;", "transformAnnotations", "(Lorg/jetbrains/kotlin/fir/visitors/FirTransformer;Ljava/lang/Object;)Lorg/jetbrains/kotlin/fir/declarations/FirRegularClass;", "transformDeclarations", "transformStatus", "transformSuperTypeRefs", "transformTypeParameters", Constants.TYPE_TREE}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class FirRegularClass extends FirClass {
    @Override // org.jetbrains.kotlin.fir.declarations.FirClass, org.jetbrains.kotlin.fir.declarations.FirClassLikeDeclaration, org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration, org.jetbrains.kotlin.fir.declarations.FirDeclaration, org.jetbrains.kotlin.fir.FirAnnotationContainer
    public abstract List<FirAnnotation> getAnnotations();

    @Override // org.jetbrains.kotlin.fir.declarations.FirClass, org.jetbrains.kotlin.fir.declarations.FirClassLikeDeclaration, org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration, org.jetbrains.kotlin.fir.declarations.FirDeclaration
    public abstract FirDeclarationAttributes getAttributes();

    @Override // org.jetbrains.kotlin.fir.declarations.FirClass
    public abstract ClassKind getClassKind();

    public abstract FirRegularClassSymbol getCompanionObjectSymbol();

    public abstract List<FirContextReceiver> getContextReceivers();

    @Override // org.jetbrains.kotlin.fir.declarations.FirClass, org.jetbrains.kotlin.fir.declarations.FirControlFlowGraphOwner
    public abstract FirControlFlowGraphReference getControlFlowGraphReference();

    @Override // org.jetbrains.kotlin.fir.declarations.FirClass
    public abstract List<FirDeclaration> getDeclarations();

    @Override // org.jetbrains.kotlin.fir.declarations.FirClass, org.jetbrains.kotlin.fir.declarations.FirClassLikeDeclaration
    public abstract DeprecationsProvider getDeprecationsProvider();

    public abstract boolean getHasLazyNestedClassifiers();

    @Override // org.jetbrains.kotlin.fir.declarations.FirClass, org.jetbrains.kotlin.fir.declarations.FirClassLikeDeclaration, org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration, org.jetbrains.kotlin.fir.declarations.FirDeclaration, org.jetbrains.kotlin.fir.FirElementWithResolveState
    public abstract FirModuleData getModuleData();

    public abstract Name getName();

    @Override // org.jetbrains.kotlin.fir.declarations.FirClass, org.jetbrains.kotlin.fir.declarations.FirClassLikeDeclaration, org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration, org.jetbrains.kotlin.fir.declarations.FirDeclaration
    public abstract FirDeclarationOrigin getOrigin();

    @Override // org.jetbrains.kotlin.fir.declarations.FirClass
    public abstract FirScopeProvider getScopeProvider();

    @Override // org.jetbrains.kotlin.fir.declarations.FirClass, org.jetbrains.kotlin.fir.declarations.FirClassLikeDeclaration, org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration, org.jetbrains.kotlin.fir.declarations.FirDeclaration, org.jetbrains.kotlin.fir.FirElementWithResolveState, org.jetbrains.kotlin.fir.FirElement
    public abstract KtSourceElement getSource();

    @Override // org.jetbrains.kotlin.fir.declarations.FirClass, org.jetbrains.kotlin.fir.declarations.FirClassLikeDeclaration, org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration
    public abstract FirDeclarationStatus getStatus();

    @Override // org.jetbrains.kotlin.fir.declarations.FirClass
    public abstract List<FirTypeRef> getSuperTypeRefs();

    @Override // org.jetbrains.kotlin.fir.declarations.FirClass, org.jetbrains.kotlin.fir.declarations.FirClassLikeDeclaration, org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration, org.jetbrains.kotlin.fir.declarations.FirDeclaration
    public abstract FirRegularClassSymbol getSymbol();

    @Override // org.jetbrains.kotlin.fir.declarations.FirClass, org.jetbrains.kotlin.fir.declarations.FirClassLikeDeclaration, org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration, org.jetbrains.kotlin.fir.declarations.FirTypeParameterRefsOwner, org.jetbrains.kotlin.fir.declarations.FirTypeParametersOwner
    public abstract List<FirTypeParameterRef> getTypeParameters();

    @Override // org.jetbrains.kotlin.fir.declarations.FirClass, org.jetbrains.kotlin.fir.declarations.FirClassLikeDeclaration, org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration, org.jetbrains.kotlin.fir.declarations.FirDeclaration, org.jetbrains.kotlin.fir.FirAnnotationContainer
    public abstract void replaceAnnotations(List<? extends FirAnnotation> newAnnotations);

    public abstract void replaceCompanionObjectSymbol(FirRegularClassSymbol newCompanionObjectSymbol);

    @Override // org.jetbrains.kotlin.fir.declarations.FirClass, org.jetbrains.kotlin.fir.declarations.FirControlFlowGraphOwner
    public abstract void replaceControlFlowGraphReference(FirControlFlowGraphReference newControlFlowGraphReference);

    @Override // org.jetbrains.kotlin.fir.declarations.FirClass, org.jetbrains.kotlin.fir.declarations.FirClassLikeDeclaration
    public abstract void replaceDeprecationsProvider(DeprecationsProvider newDeprecationsProvider);

    @Override // org.jetbrains.kotlin.fir.declarations.FirClass, org.jetbrains.kotlin.fir.declarations.FirClassLikeDeclaration, org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration
    public abstract void replaceStatus(FirDeclarationStatus newStatus);

    @Override // org.jetbrains.kotlin.fir.declarations.FirClass
    public abstract void replaceSuperTypeRefs(List<? extends FirTypeRef> newSuperTypeRefs);

    @Override // org.jetbrains.kotlin.fir.declarations.FirClass, org.jetbrains.kotlin.fir.declarations.FirClassLikeDeclaration, org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration, org.jetbrains.kotlin.fir.declarations.FirDeclaration, org.jetbrains.kotlin.fir.FirAnnotationContainer
    public abstract <D> FirRegularClass transformAnnotations(FirTransformer<? super D> transformer, D data);

    @Override // org.jetbrains.kotlin.fir.declarations.FirClass
    public abstract <D> FirRegularClass transformDeclarations(FirTransformer<? super D> transformer, D data);

    @Override // org.jetbrains.kotlin.fir.declarations.FirClass, org.jetbrains.kotlin.fir.declarations.FirClassLikeDeclaration, org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration
    public abstract <D> FirRegularClass transformStatus(FirTransformer<? super D> transformer, D data);

    @Override // org.jetbrains.kotlin.fir.declarations.FirClass
    public abstract <D> FirRegularClass transformSuperTypeRefs(FirTransformer<? super D> transformer, D data);

    @Override // org.jetbrains.kotlin.fir.declarations.FirClass, org.jetbrains.kotlin.fir.declarations.FirClassLikeDeclaration, org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration, org.jetbrains.kotlin.fir.declarations.FirTypeParameterRefsOwner, org.jetbrains.kotlin.fir.declarations.FirTypeParametersOwner
    public abstract <D> FirRegularClass transformTypeParameters(FirTransformer<? super D> transformer, D data);

    public FirRegularClass() {
        super(null);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.jetbrains.kotlin.fir.declarations.FirClass, org.jetbrains.kotlin.fir.declarations.FirClassLikeDeclaration, org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration, org.jetbrains.kotlin.fir.declarations.FirDeclaration, org.jetbrains.kotlin.fir.FirElementWithResolveState, org.jetbrains.kotlin.fir.FirElement
    public <R, D> R accept(FirVisitor<? extends R, ? super D> visitor, D data) {
        Intrinsics.checkNotNullParameter(visitor, "visitor");
        return visitor.visitRegularClass(this, data);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.jetbrains.kotlin.fir.declarations.FirClass, org.jetbrains.kotlin.fir.declarations.FirClassLikeDeclaration, org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration, org.jetbrains.kotlin.fir.declarations.FirDeclaration, org.jetbrains.kotlin.fir.FirElementWithResolveState, org.jetbrains.kotlin.fir.FirElement
    public <E extends FirElement, D> E transform(FirTransformer<? super D> transformer, D data) {
        Intrinsics.checkNotNullParameter(transformer, "transformer");
        FirStatement firStatementTransformRegularClass = transformer.transformRegularClass(this, data);
        Intrinsics.checkNotNull(firStatementTransformRegularClass, "null cannot be cast to non-null type E of org.jetbrains.kotlin.fir.declarations.FirRegularClass.transform");
        return firStatementTransformRegularClass;
    }
}
