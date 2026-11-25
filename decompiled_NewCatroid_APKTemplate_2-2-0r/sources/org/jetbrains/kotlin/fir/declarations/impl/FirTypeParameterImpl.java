package org.jetbrains.kotlin.fir.declarations.impl;

import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.Constants;
import org.jetbrains.kotlin.KtSourceElement;
import org.jetbrains.kotlin.fir.FirAnnotationContainer;
import org.jetbrains.kotlin.fir.FirElement;
import org.jetbrains.kotlin.fir.FirModuleData;
import org.jetbrains.kotlin.fir.MutableOrEmptyList;
import org.jetbrains.kotlin.fir.builder.FirBuilderDslKt;
import org.jetbrains.kotlin.fir.declarations.FirDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirDeclarationAttributes;
import org.jetbrains.kotlin.fir.declarations.FirDeclarationOrigin;
import org.jetbrains.kotlin.fir.declarations.FirResolvePhase;
import org.jetbrains.kotlin.fir.declarations.FirResolveStateKt;
import org.jetbrains.kotlin.fir.declarations.FirTypeParameter;
import org.jetbrains.kotlin.fir.expressions.FirAnnotation;
import org.jetbrains.kotlin.fir.symbols.FirBasedSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirTypeParameterSymbol;
import org.jetbrains.kotlin.fir.types.FirTypeRef;
import org.jetbrains.kotlin.fir.visitors.FirTransformer;
import org.jetbrains.kotlin.fir.visitors.FirTransformerUtilKt;
import org.jetbrains.kotlin.fir.visitors.FirVisitor;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.types.Variance;
import org.mozilla.javascript.NativeSymbol;

/* JADX INFO: compiled from: FirTypeParameterImpl.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000|\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0019\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010 \n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0000\u0018\u00002\u00020\u0001Bw\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u0012\u0006\u0010\n\u001a\u00020\u000b\u0012\u0006\u0010\f\u001a\u00020\r\u0012\u0006\u0010\u000e\u001a\u00020\u000f\u0012\n\u0010\u0010\u001a\u0006\u0012\u0002\b\u00030\u0011\u0012\u0006\u0010\u0012\u001a\u00020\u0013\u0012\u0006\u0010\u0014\u001a\u00020\u0015\u0012\f\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00180\u0017\u0012\f\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u001b0\u001a¢\u0006\u0002\u0010\u001cJ5\u00104\u001a\u000205\"\u0004\b\u0000\u00106\"\u0004\b\u0001\u001072\u0012\u00108\u001a\u000e\u0012\u0004\u0012\u0002H6\u0012\u0004\u0012\u0002H7092\u0006\u0010:\u001a\u0002H7H\u0016¢\u0006\u0002\u0010;J\u0016\u0010<\u001a\u0002052\f\u0010=\u001a\b\u0012\u0004\u0012\u00020\u001b0>H\u0016J\u0016\u0010?\u001a\u0002052\f\u0010@\u001a\b\u0012\u0004\u0012\u00020\u00180>H\u0016J)\u0010A\u001a\u00020\u0000\"\u0004\b\u0000\u001072\f\u0010B\u001a\b\u0012\u0004\u0012\u0002H70C2\u0006\u0010:\u001a\u0002H7H\u0016¢\u0006\u0002\u0010DJ)\u0010E\u001a\u00020\u0000\"\u0004\b\u0000\u001072\f\u0010B\u001a\b\u0012\u0004\u0012\u0002H70C2\u0006\u0010:\u001a\u0002H7H\u0016¢\u0006\u0002\u0010DR(\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u001b0\u001aX\u0096\u000eø\u0001\u0000ø\u0001\u0001¢\u0006\u0010\n\u0002\u0010!\u001a\u0004\b\u001d\u0010\u001e\"\u0004\b\u001f\u0010 R\u0014\u0010\n\u001a\u00020\u000bX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\"\u0010#R\u001a\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00180\u0017X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b$\u0010\u001eR\u0018\u0010\u0010\u001a\u0006\u0012\u0002\b\u00030\u0011X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b%\u0010&R\u0014\u0010\u0014\u001a\u00020\u0015X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010'R\u0014\u0010\u0006\u001a\u00020\u0007X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b(\u0010)R\u0014\u0010\f\u001a\u00020\rX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b*\u0010+R\u0014\u0010\b\u001a\u00020\tX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b,\u0010-R\u0016\u0010\u0002\u001a\u0004\u0018\u00010\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b.\u0010/R\u0014\u0010\u000e\u001a\u00020\u000fX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b0\u00101R\u0014\u0010\u0012\u001a\u00020\u0013X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b2\u00103\u0082\u0002\u000b\n\u0005\b¡\u001e0\u0001\n\u0002\b!¨\u0006F"}, d2 = {"Lorg/jetbrains/kotlin/fir/declarations/impl/FirTypeParameterImpl;", "Lorg/jetbrains/kotlin/fir/declarations/FirTypeParameter;", FirebaseAnalytics.Param.SOURCE, "Lorg/jetbrains/kotlin/KtSourceElement;", "resolvePhase", "Lorg/jetbrains/kotlin/fir/declarations/FirResolvePhase;", "moduleData", "Lorg/jetbrains/kotlin/fir/FirModuleData;", "origin", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationOrigin;", "attributes", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationAttributes;", "name", "Lorg/jetbrains/kotlin/name/Name;", NativeSymbol.TYPE_NAME, "Lorg/jetbrains/kotlin/fir/symbols/impl/FirTypeParameterSymbol;", "containingDeclarationSymbol", "Lorg/jetbrains/kotlin/fir/symbols/FirBasedSymbol;", "variance", "Lorg/jetbrains/kotlin/types/Variance;", "isReified", "", "bounds", "", "Lorg/jetbrains/kotlin/fir/types/FirTypeRef;", "annotations", "Lorg/jetbrains/kotlin/fir/MutableOrEmptyList;", "Lorg/jetbrains/kotlin/fir/expressions/FirAnnotation;", "(Lorg/jetbrains/kotlin/KtSourceElement;Lorg/jetbrains/kotlin/fir/declarations/FirResolvePhase;Lorg/jetbrains/kotlin/fir/FirModuleData;Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationOrigin;Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationAttributes;Lorg/jetbrains/kotlin/name/Name;Lorg/jetbrains/kotlin/fir/symbols/impl/FirTypeParameterSymbol;Lorg/jetbrains/kotlin/fir/symbols/FirBasedSymbol;Lorg/jetbrains/kotlin/types/Variance;ZLjava/util/List;Ljava/util/List;Lkotlin/jvm/internal/DefaultConstructorMarker;)V", "getAnnotations-5e3fPpI", "()Ljava/util/List;", "setAnnotations-GqUYU-s", "(Ljava/util/List;)V", "Ljava/util/List;", "getAttributes", "()Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationAttributes;", "getBounds", "getContainingDeclarationSymbol", "()Lorg/jetbrains/kotlin/fir/symbols/FirBasedSymbol;", "()Z", "getModuleData", "()Lorg/jetbrains/kotlin/fir/FirModuleData;", "getName", "()Lorg/jetbrains/kotlin/name/Name;", "getOrigin", "()Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationOrigin;", "getSource", "()Lorg/jetbrains/kotlin/KtSourceElement;", "getSymbol", "()Lorg/jetbrains/kotlin/fir/symbols/impl/FirTypeParameterSymbol;", "getVariance", "()Lorg/jetbrains/kotlin/types/Variance;", "acceptChildren", "", "R", "D", "visitor", "Lorg/jetbrains/kotlin/fir/visitors/FirVisitor;", "data", "(Lorg/jetbrains/kotlin/fir/visitors/FirVisitor;Ljava/lang/Object;)V", "replaceAnnotations", "newAnnotations", "", "replaceBounds", "newBounds", "transformAnnotations", "transformer", "Lorg/jetbrains/kotlin/fir/visitors/FirTransformer;", "(Lorg/jetbrains/kotlin/fir/visitors/FirTransformer;Ljava/lang/Object;)Lorg/jetbrains/kotlin/fir/declarations/impl/FirTypeParameterImpl;", "transformChildren", Constants.TYPE_TREE}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirTypeParameterImpl extends FirTypeParameter {
    private List<T> annotations;
    private final FirDeclarationAttributes attributes;
    private final List<FirTypeRef> bounds;
    private final FirBasedSymbol<?> containingDeclarationSymbol;
    private final boolean isReified;
    private final FirModuleData moduleData;
    private final Name name;
    private final FirDeclarationOrigin origin;
    private final KtSourceElement source;
    private final FirTypeParameterSymbol symbol;
    private final Variance variance;

    public /* synthetic */ FirTypeParameterImpl(KtSourceElement ktSourceElement, FirResolvePhase firResolvePhase, FirModuleData firModuleData, FirDeclarationOrigin firDeclarationOrigin, FirDeclarationAttributes firDeclarationAttributes, Name name2, FirTypeParameterSymbol firTypeParameterSymbol, FirBasedSymbol firBasedSymbol, Variance variance, boolean z, List list, List list2, DefaultConstructorMarker defaultConstructorMarker) {
        this(ktSourceElement, firResolvePhase, firModuleData, firDeclarationOrigin, firDeclarationAttributes, name2, firTypeParameterSymbol, firBasedSymbol, variance, z, list, list2);
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirTypeParameter, org.jetbrains.kotlin.fir.declarations.FirDeclaration, org.jetbrains.kotlin.fir.FirAnnotationContainer
    public /* bridge */ /* synthetic */ List getAnnotations() {
        return MutableOrEmptyList.m6756boximpl(m6883getAnnotations5e3fPpI());
    }

    @Override // org.jetbrains.kotlin.fir.FirAnnotationContainer
    public /* bridge */ /* synthetic */ FirAnnotationContainer transformAnnotations(FirTransformer firTransformer, Object obj) {
        return transformAnnotations((FirTransformer<? super Object>) firTransformer, obj);
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirDeclaration, org.jetbrains.kotlin.fir.FirAnnotationContainer
    public /* bridge */ /* synthetic */ FirDeclaration transformAnnotations(FirTransformer firTransformer, Object obj) {
        return transformAnnotations((FirTransformer<? super Object>) firTransformer, obj);
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirTypeParameter, org.jetbrains.kotlin.fir.declarations.FirDeclaration, org.jetbrains.kotlin.fir.FirAnnotationContainer
    public /* bridge */ /* synthetic */ FirTypeParameter transformAnnotations(FirTransformer firTransformer, Object obj) {
        return transformAnnotations((FirTransformer<? super Object>) firTransformer, obj);
    }

    @Override // org.jetbrains.kotlin.fir.FirPureAbstractElement, org.jetbrains.kotlin.fir.FirElement
    public /* bridge */ /* synthetic */ FirElement transformChildren(FirTransformer firTransformer, Object obj) {
        return transformChildren((FirTransformer<? super Object>) firTransformer, obj);
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirTypeParameter, org.jetbrains.kotlin.fir.declarations.FirDeclaration, org.jetbrains.kotlin.fir.FirElementWithResolveState, org.jetbrains.kotlin.fir.FirElement
    public KtSourceElement getSource() {
        return this.source;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirTypeParameter, org.jetbrains.kotlin.fir.declarations.FirDeclaration, org.jetbrains.kotlin.fir.FirElementWithResolveState
    public FirModuleData getModuleData() {
        return this.moduleData;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirTypeParameter, org.jetbrains.kotlin.fir.declarations.FirDeclaration
    public FirDeclarationOrigin getOrigin() {
        return this.origin;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirTypeParameter, org.jetbrains.kotlin.fir.declarations.FirDeclaration
    public FirDeclarationAttributes getAttributes() {
        return this.attributes;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirTypeParameter
    public Name getName() {
        return this.name;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirDeclaration
    public FirTypeParameterSymbol getSymbol() {
        return this.symbol;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirTypeParameter
    public FirBasedSymbol<?> getContainingDeclarationSymbol() {
        return this.containingDeclarationSymbol;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirTypeParameter
    public Variance getVariance() {
        return this.variance;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirTypeParameter
    /* JADX INFO: renamed from: isReified, reason: from getter */
    public boolean getIsReified() {
        return this.isReified;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirTypeParameter
    public List<FirTypeRef> getBounds() {
        return this.bounds;
    }

    /* JADX INFO: renamed from: getAnnotations-5e3fPpI, reason: not valid java name */
    public List<T> m6883getAnnotations5e3fPpI() {
        return this.annotations;
    }

    /* JADX INFO: renamed from: setAnnotations-GqUYU-s, reason: not valid java name */
    public void m6884setAnnotationsGqUYUs(List<T> list) {
        this.annotations = list;
    }

    private FirTypeParameterImpl(KtSourceElement ktSourceElement, FirResolvePhase resolvePhase, FirModuleData moduleData, FirDeclarationOrigin origin, FirDeclarationAttributes attributes, Name name2, FirTypeParameterSymbol symbol, FirBasedSymbol<?> containingDeclarationSymbol, Variance variance, boolean z, List<FirTypeRef> bounds, List<T> list) {
        Intrinsics.checkNotNullParameter(resolvePhase, "resolvePhase");
        Intrinsics.checkNotNullParameter(moduleData, "moduleData");
        Intrinsics.checkNotNullParameter(origin, "origin");
        Intrinsics.checkNotNullParameter(attributes, "attributes");
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(symbol, "symbol");
        Intrinsics.checkNotNullParameter(containingDeclarationSymbol, "containingDeclarationSymbol");
        Intrinsics.checkNotNullParameter(variance, "variance");
        Intrinsics.checkNotNullParameter(bounds, "bounds");
        this.source = ktSourceElement;
        this.moduleData = moduleData;
        this.origin = origin;
        this.attributes = attributes;
        this.name = name2;
        this.symbol = symbol;
        this.containingDeclarationSymbol = containingDeclarationSymbol;
        this.variance = variance;
        this.isReified = z;
        this.bounds = bounds;
        this.annotations = list;
        getSymbol().bind(this);
        setResolveState(FirResolveStateKt.asResolveState(resolvePhase));
    }

    @Override // org.jetbrains.kotlin.fir.FirPureAbstractElement, org.jetbrains.kotlin.fir.FirElement
    public <R, D> void acceptChildren(FirVisitor<? extends R, ? super D> visitor, D data) {
        Intrinsics.checkNotNullParameter(visitor, "visitor");
        Iterator<T> it = getBounds().iterator();
        while (it.hasNext()) {
            ((FirTypeRef) it.next()).accept(visitor, data);
        }
        Iterator<T> it2 = MutableOrEmptyList.m6756boximpl(m6883getAnnotations5e3fPpI()).iterator();
        while (it2.hasNext()) {
            ((FirAnnotation) it2.next()).accept(visitor, data);
        }
    }

    @Override // org.jetbrains.kotlin.fir.FirPureAbstractElement, org.jetbrains.kotlin.fir.FirElement
    public <D> FirTypeParameterImpl transformChildren(FirTransformer<? super D> transformer, D data) {
        Intrinsics.checkNotNullParameter(transformer, "transformer");
        FirTransformerUtilKt.transformInplace(getBounds(), transformer, data);
        transformAnnotations((FirTransformer) transformer, (Object) data);
        return this;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirTypeParameter, org.jetbrains.kotlin.fir.declarations.FirDeclaration, org.jetbrains.kotlin.fir.FirAnnotationContainer
    public <D> FirTypeParameterImpl transformAnnotations(FirTransformer<? super D> transformer, D data) {
        Intrinsics.checkNotNullParameter(transformer, "transformer");
        FirTransformerUtilKt.m7082transformInplaceaLnlfrU(m6883getAnnotations5e3fPpI(), transformer, data);
        return this;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirTypeParameter
    public void replaceBounds(List<? extends FirTypeRef> newBounds) {
        Intrinsics.checkNotNullParameter(newBounds, "newBounds");
        getBounds().clear();
        getBounds().addAll(newBounds);
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirTypeParameter, org.jetbrains.kotlin.fir.declarations.FirDeclaration, org.jetbrains.kotlin.fir.FirAnnotationContainer
    public void replaceAnnotations(List<? extends FirAnnotation> newAnnotations) {
        Intrinsics.checkNotNullParameter(newAnnotations, "newAnnotations");
        m6884setAnnotationsGqUYUs(FirBuilderDslKt.toMutableOrEmptyForImmutable(newAnnotations));
    }
}
