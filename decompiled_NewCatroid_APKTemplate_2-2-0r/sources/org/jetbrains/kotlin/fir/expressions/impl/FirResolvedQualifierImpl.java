package org.jetbrains.kotlin.fir.expressions.impl;

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
import org.jetbrains.kotlin.fir.MutableOrEmptyList;
import org.jetbrains.kotlin.fir.builder.FirBuilderDslKt;
import org.jetbrains.kotlin.fir.declarations.FirClassLikeDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirRegularClass;
import org.jetbrains.kotlin.fir.expressions.FirAnnotation;
import org.jetbrains.kotlin.fir.expressions.FirExpression;
import org.jetbrains.kotlin.fir.expressions.FirResolvedQualifier;
import org.jetbrains.kotlin.fir.expressions.FirStatement;
import org.jetbrains.kotlin.fir.symbols.impl.FirClassLikeSymbol;
import org.jetbrains.kotlin.fir.types.FirTypeProjection;
import org.jetbrains.kotlin.fir.types.FirTypeRef;
import org.jetbrains.kotlin.fir.visitors.FirTransformer;
import org.jetbrains.kotlin.fir.visitors.FirTransformerUtilKt;
import org.jetbrains.kotlin.fir.visitors.FirVisitor;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.name.FqName;
import org.mozilla.javascript.NativeSymbol;

/* JADX INFO: compiled from: FirResolvedQualifierImpl.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000l\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u001b\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010 \n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\b\u0004\b\u0000\u0018\u00002\u00020\u0001Bq\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u0007\u0012\u0006\u0010\t\u001a\u00020\n\u0012\b\u0010\u000b\u001a\u0004\u0018\u00010\n\u0012\f\u0010\f\u001a\b\u0012\u0002\b\u0003\u0018\u00010\r\u0012\u0006\u0010\u000e\u001a\u00020\u000f\u0012\u0006\u0010\u0010\u001a\u00020\u000f\u0012\f\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00120\u0007\u0012\f\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00140\u0007¢\u0006\u0002\u0010\u0015J5\u00107\u001a\u000208\"\u0004\b\u0000\u00109\"\u0004\b\u0001\u0010:2\u0012\u0010;\u001a\u000e\u0012\u0004\u0012\u0002H9\u0012\u0004\u0012\u0002H:0<2\u0006\u0010=\u001a\u0002H:H\u0016¢\u0006\u0002\u0010>J\u0016\u0010?\u001a\u0002082\f\u0010@\u001a\b\u0012\u0004\u0012\u00020\b0AH\u0016J\u0010\u0010B\u001a\u0002082\u0006\u0010C\u001a\u00020\u000fH\u0016J\u0010\u0010D\u001a\u0002082\u0006\u0010E\u001a\u00020\u000fH\u0016J\u0016\u0010F\u001a\u0002082\f\u0010G\u001a\b\u0012\u0004\u0012\u00020\u00140AH\u0016J\u0010\u0010H\u001a\u0002082\u0006\u0010I\u001a\u00020\u0005H\u0016J)\u0010J\u001a\u00020\u0000\"\u0004\b\u0000\u0010:2\f\u0010K\u001a\b\u0012\u0004\u0012\u0002H:0L2\u0006\u0010=\u001a\u0002H:H\u0016¢\u0006\u0002\u0010MJ)\u0010N\u001a\u00020\u0000\"\u0004\b\u0000\u0010:2\f\u0010K\u001a\b\u0012\u0004\u0012\u0002H:0L2\u0006\u0010=\u001a\u0002H:H\u0016¢\u0006\u0002\u0010MJ)\u0010O\u001a\u00020\u0000\"\u0004\b\u0000\u0010:2\f\u0010K\u001a\b\u0012\u0004\u0012\u0002H:0L2\u0006\u0010=\u001a\u0002H:H\u0016¢\u0006\u0002\u0010MR(\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u0007X\u0096\u000eø\u0001\u0000ø\u0001\u0001¢\u0006\u0010\n\u0002\u0010\u001a\u001a\u0004\b\u0016\u0010\u0017\"\u0004\b\u0018\u0010\u0019R\u0016\u0010\u001b\u001a\u0004\u0018\u00010\u001c8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u001d\u0010\u001eR\u0014\u0010\u0010\u001a\u00020\u000fX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u001fR\u001a\u0010\u000e\u001a\u00020\u000fX\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000e\u0010\u001f\"\u0004\b \u0010!R(\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00120\u0007X\u0096\u000eø\u0001\u0000ø\u0001\u0001¢\u0006\u0010\n\u0002\u0010\u001a\u001a\u0004\b\"\u0010\u0017\"\u0004\b#\u0010\u0019R\u001a\u0010\t\u001a\u00020\nX\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b$\u0010%\"\u0004\b&\u0010'R\u001c\u0010\u000b\u001a\u0004\u0018\u00010\nX\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b(\u0010%\"\u0004\b)\u0010'R\u001a\u0010*\u001a\u00020\u000fX\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b+\u0010\u001f\"\u0004\b,\u0010!R\u0016\u0010\u0002\u001a\u0004\u0018\u00010\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b-\u0010.R\u001a\u0010\f\u001a\b\u0012\u0002\b\u0003\u0018\u00010\rX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b/\u00100R(\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00140\u0007X\u0096\u000eø\u0001\u0000ø\u0001\u0001¢\u0006\u0010\n\u0002\u0010\u001a\u001a\u0004\b1\u0010\u0017\"\u0004\b2\u0010\u0019R\u001a\u0010\u0004\u001a\u00020\u0005X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b3\u00104\"\u0004\b5\u00106\u0082\u0002\u000b\n\u0005\b¡\u001e0\u0001\n\u0002\b!¨\u0006P"}, d2 = {"Lorg/jetbrains/kotlin/fir/expressions/impl/FirResolvedQualifierImpl;", "Lorg/jetbrains/kotlin/fir/expressions/FirResolvedQualifier;", FirebaseAnalytics.Param.SOURCE, "Lorg/jetbrains/kotlin/KtSourceElement;", "typeRef", "Lorg/jetbrains/kotlin/fir/types/FirTypeRef;", "annotations", "Lorg/jetbrains/kotlin/fir/MutableOrEmptyList;", "Lorg/jetbrains/kotlin/fir/expressions/FirAnnotation;", "packageFqName", "Lorg/jetbrains/kotlin/name/FqName;", "relativeClassFqName", NativeSymbol.TYPE_NAME, "Lorg/jetbrains/kotlin/fir/symbols/impl/FirClassLikeSymbol;", "isNullableLHSForCallableReference", "", "isFullyQualified", "nonFatalDiagnostics", "Lorg/jetbrains/kotlin/fir/diagnostics/ConeDiagnostic;", "typeArguments", "Lorg/jetbrains/kotlin/fir/types/FirTypeProjection;", "(Lorg/jetbrains/kotlin/KtSourceElement;Lorg/jetbrains/kotlin/fir/types/FirTypeRef;Ljava/util/List;Lorg/jetbrains/kotlin/name/FqName;Lorg/jetbrains/kotlin/name/FqName;Lorg/jetbrains/kotlin/fir/symbols/impl/FirClassLikeSymbol;ZZLjava/util/List;Ljava/util/List;Lkotlin/jvm/internal/DefaultConstructorMarker;)V", "getAnnotations-5e3fPpI", "()Ljava/util/List;", "setAnnotations-GqUYU-s", "(Ljava/util/List;)V", "Ljava/util/List;", "classId", "Lorg/jetbrains/kotlin/name/ClassId;", "getClassId", "()Lorg/jetbrains/kotlin/name/ClassId;", "()Z", "setNullableLHSForCallableReference", "(Z)V", "getNonFatalDiagnostics-5e3fPpI", "setNonFatalDiagnostics-GqUYU-s", "getPackageFqName", "()Lorg/jetbrains/kotlin/name/FqName;", "setPackageFqName", "(Lorg/jetbrains/kotlin/name/FqName;)V", "getRelativeClassFqName", "setRelativeClassFqName", "resolvedToCompanionObject", "getResolvedToCompanionObject", "setResolvedToCompanionObject", "getSource", "()Lorg/jetbrains/kotlin/KtSourceElement;", "getSymbol", "()Lorg/jetbrains/kotlin/fir/symbols/impl/FirClassLikeSymbol;", "getTypeArguments-5e3fPpI", "setTypeArguments-GqUYU-s", "getTypeRef", "()Lorg/jetbrains/kotlin/fir/types/FirTypeRef;", "setTypeRef", "(Lorg/jetbrains/kotlin/fir/types/FirTypeRef;)V", "acceptChildren", "", "R", "D", "visitor", "Lorg/jetbrains/kotlin/fir/visitors/FirVisitor;", "data", "(Lorg/jetbrains/kotlin/fir/visitors/FirVisitor;Ljava/lang/Object;)V", "replaceAnnotations", "newAnnotations", "", "replaceIsNullableLHSForCallableReference", "newIsNullableLHSForCallableReference", "replaceResolvedToCompanionObject", "newResolvedToCompanionObject", "replaceTypeArguments", "newTypeArguments", "replaceTypeRef", "newTypeRef", "transformAnnotations", "transformer", "Lorg/jetbrains/kotlin/fir/visitors/FirTransformer;", "(Lorg/jetbrains/kotlin/fir/visitors/FirTransformer;Ljava/lang/Object;)Lorg/jetbrains/kotlin/fir/expressions/impl/FirResolvedQualifierImpl;", "transformChildren", "transformTypeArguments", Constants.TYPE_TREE}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirResolvedQualifierImpl extends FirResolvedQualifier {
    private List<T> annotations;
    private final boolean isFullyQualified;
    private boolean isNullableLHSForCallableReference;
    private List<T> nonFatalDiagnostics;
    private FqName packageFqName;
    private FqName relativeClassFqName;
    private boolean resolvedToCompanionObject;
    private final KtSourceElement source;
    private final FirClassLikeSymbol<?> symbol;
    private List<T> typeArguments;
    private FirTypeRef typeRef;

    public /* synthetic */ FirResolvedQualifierImpl(KtSourceElement ktSourceElement, FirTypeRef firTypeRef, List list, FqName fqName, FqName fqName2, FirClassLikeSymbol firClassLikeSymbol, boolean z, boolean z2, List list2, List list3, DefaultConstructorMarker defaultConstructorMarker) {
        this(ktSourceElement, firTypeRef, list, fqName, fqName2, firClassLikeSymbol, z, z2, list2, list3);
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirResolvedQualifier, org.jetbrains.kotlin.fir.expressions.FirExpression, org.jetbrains.kotlin.fir.expressions.FirStatement, org.jetbrains.kotlin.fir.FirAnnotationContainer
    public /* bridge */ /* synthetic */ List getAnnotations() {
        return MutableOrEmptyList.m6756boximpl(m7000getAnnotations5e3fPpI());
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirResolvedQualifier
    public /* bridge */ /* synthetic */ List getNonFatalDiagnostics() {
        return MutableOrEmptyList.m6756boximpl(m7001getNonFatalDiagnostics5e3fPpI());
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirResolvedQualifier
    public /* bridge */ /* synthetic */ List getTypeArguments() {
        return MutableOrEmptyList.m6756boximpl(m7002getTypeArguments5e3fPpI());
    }

    @Override // org.jetbrains.kotlin.fir.FirAnnotationContainer
    public /* bridge */ /* synthetic */ FirAnnotationContainer transformAnnotations(FirTransformer firTransformer, Object obj) {
        return transformAnnotations((FirTransformer<? super Object>) firTransformer, obj);
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirExpression, org.jetbrains.kotlin.fir.expressions.FirStatement, org.jetbrains.kotlin.fir.FirAnnotationContainer
    public /* bridge */ /* synthetic */ FirExpression transformAnnotations(FirTransformer firTransformer, Object obj) {
        return transformAnnotations((FirTransformer<? super Object>) firTransformer, obj);
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirResolvedQualifier, org.jetbrains.kotlin.fir.expressions.FirExpression, org.jetbrains.kotlin.fir.expressions.FirStatement, org.jetbrains.kotlin.fir.FirAnnotationContainer
    public /* bridge */ /* synthetic */ FirResolvedQualifier transformAnnotations(FirTransformer firTransformer, Object obj) {
        return transformAnnotations((FirTransformer<? super Object>) firTransformer, obj);
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirStatement, org.jetbrains.kotlin.fir.FirAnnotationContainer
    public /* bridge */ /* synthetic */ FirStatement transformAnnotations(FirTransformer firTransformer, Object obj) {
        return transformAnnotations((FirTransformer<? super Object>) firTransformer, obj);
    }

    @Override // org.jetbrains.kotlin.fir.FirPureAbstractElement, org.jetbrains.kotlin.fir.FirElement
    public /* bridge */ /* synthetic */ FirElement transformChildren(FirTransformer firTransformer, Object obj) {
        return transformChildren((FirTransformer<? super Object>) firTransformer, obj);
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirResolvedQualifier
    public /* bridge */ /* synthetic */ FirResolvedQualifier transformTypeArguments(FirTransformer firTransformer, Object obj) {
        return transformTypeArguments((FirTransformer<? super Object>) firTransformer, obj);
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirResolvedQualifier, org.jetbrains.kotlin.fir.expressions.FirExpression, org.jetbrains.kotlin.fir.FirElement
    public KtSourceElement getSource() {
        return this.source;
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirResolvedQualifier, org.jetbrains.kotlin.fir.expressions.FirExpression
    public FirTypeRef getTypeRef() {
        return this.typeRef;
    }

    public void setTypeRef(FirTypeRef firTypeRef) {
        Intrinsics.checkNotNullParameter(firTypeRef, "<set-?>");
        this.typeRef = firTypeRef;
    }

    /* JADX INFO: renamed from: getAnnotations-5e3fPpI, reason: not valid java name */
    public List<T> m7000getAnnotations5e3fPpI() {
        return this.annotations;
    }

    /* JADX INFO: renamed from: setAnnotations-GqUYU-s, reason: not valid java name */
    public void m7003setAnnotationsGqUYUs(List<T> list) {
        this.annotations = list;
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirResolvedQualifier
    public FqName getPackageFqName() {
        return this.packageFqName;
    }

    public void setPackageFqName(FqName fqName) {
        Intrinsics.checkNotNullParameter(fqName, "<set-?>");
        this.packageFqName = fqName;
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirResolvedQualifier
    public FqName getRelativeClassFqName() {
        return this.relativeClassFqName;
    }

    public void setRelativeClassFqName(FqName fqName) {
        this.relativeClassFqName = fqName;
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirResolvedQualifier
    public FirClassLikeSymbol<?> getSymbol() {
        return this.symbol;
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirResolvedQualifier
    /* JADX INFO: renamed from: isNullableLHSForCallableReference, reason: from getter */
    public boolean getIsNullableLHSForCallableReference() {
        return this.isNullableLHSForCallableReference;
    }

    public void setNullableLHSForCallableReference(boolean z) {
        this.isNullableLHSForCallableReference = z;
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirResolvedQualifier
    /* JADX INFO: renamed from: isFullyQualified, reason: from getter */
    public boolean getIsFullyQualified() {
        return this.isFullyQualified;
    }

    /* JADX INFO: renamed from: getNonFatalDiagnostics-5e3fPpI, reason: not valid java name */
    public List<T> m7001getNonFatalDiagnostics5e3fPpI() {
        return this.nonFatalDiagnostics;
    }

    /* JADX INFO: renamed from: setNonFatalDiagnostics-GqUYU-s, reason: not valid java name */
    public void m7004setNonFatalDiagnosticsGqUYUs(List<T> list) {
        this.nonFatalDiagnostics = list;
    }

    /* JADX INFO: renamed from: getTypeArguments-5e3fPpI, reason: not valid java name */
    public List<T> m7002getTypeArguments5e3fPpI() {
        return this.typeArguments;
    }

    /* JADX INFO: renamed from: setTypeArguments-GqUYU-s, reason: not valid java name */
    public void m7005setTypeArgumentsGqUYUs(List<T> list) {
        this.typeArguments = list;
    }

    /* JADX WARN: Multi-variable type inference failed */
    private FirResolvedQualifierImpl(KtSourceElement ktSourceElement, FirTypeRef typeRef, List<T> list, FqName packageFqName, FqName fqName, FirClassLikeSymbol<?> firClassLikeSymbol, boolean z, boolean z2, List<T> list2, List<T> list3) {
        Intrinsics.checkNotNullParameter(typeRef, "typeRef");
        Intrinsics.checkNotNullParameter(packageFqName, "packageFqName");
        this.source = ktSourceElement;
        this.typeRef = typeRef;
        this.annotations = list;
        this.packageFqName = packageFqName;
        this.relativeClassFqName = fqName;
        this.symbol = firClassLikeSymbol;
        this.isNullableLHSForCallableReference = z;
        this.isFullyQualified = z2;
        this.nonFatalDiagnostics = list2;
        this.typeArguments = list3;
        FirClassLikeSymbol<?> symbol = getSymbol();
        FirClassLikeDeclaration firClassLikeDeclaration = symbol != null ? (FirClassLikeDeclaration) symbol.getFir() : null;
        FirRegularClass firRegularClass = firClassLikeDeclaration instanceof FirRegularClass ? (FirRegularClass) firClassLikeDeclaration : null;
        this.resolvedToCompanionObject = (firRegularClass != null ? firRegularClass.getCompanionObjectSymbol() : null) != null;
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirResolvedQualifier
    public ClassId getClassId() {
        FqName relativeClassFqName = getRelativeClassFqName();
        if (relativeClassFqName != null) {
            return new ClassId(getPackageFqName(), relativeClassFqName, false);
        }
        return null;
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirResolvedQualifier
    public boolean getResolvedToCompanionObject() {
        return this.resolvedToCompanionObject;
    }

    public void setResolvedToCompanionObject(boolean z) {
        this.resolvedToCompanionObject = z;
    }

    @Override // org.jetbrains.kotlin.fir.FirPureAbstractElement, org.jetbrains.kotlin.fir.FirElement
    public <R, D> void acceptChildren(FirVisitor<? extends R, ? super D> visitor, D data) {
        Intrinsics.checkNotNullParameter(visitor, "visitor");
        getTypeRef().accept(visitor, data);
        Iterator<T> it = MutableOrEmptyList.m6756boximpl(m7000getAnnotations5e3fPpI()).iterator();
        while (it.hasNext()) {
            ((FirAnnotation) it.next()).accept(visitor, data);
        }
        Iterator<T> it2 = MutableOrEmptyList.m6756boximpl(m7002getTypeArguments5e3fPpI()).iterator();
        while (it2.hasNext()) {
            ((FirTypeProjection) it2.next()).accept(visitor, data);
        }
    }

    @Override // org.jetbrains.kotlin.fir.FirPureAbstractElement, org.jetbrains.kotlin.fir.FirElement
    public <D> FirResolvedQualifierImpl transformChildren(FirTransformer<? super D> transformer, D data) {
        Intrinsics.checkNotNullParameter(transformer, "transformer");
        setTypeRef((FirTypeRef) getTypeRef().transform(transformer, data));
        transformAnnotations((FirTransformer) transformer, (Object) data);
        transformTypeArguments((FirTransformer) transformer, (Object) data);
        return this;
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirResolvedQualifier, org.jetbrains.kotlin.fir.expressions.FirExpression, org.jetbrains.kotlin.fir.expressions.FirStatement, org.jetbrains.kotlin.fir.FirAnnotationContainer
    public <D> FirResolvedQualifierImpl transformAnnotations(FirTransformer<? super D> transformer, D data) {
        Intrinsics.checkNotNullParameter(transformer, "transformer");
        FirTransformerUtilKt.m7082transformInplaceaLnlfrU(m7000getAnnotations5e3fPpI(), transformer, data);
        return this;
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirResolvedQualifier
    public <D> FirResolvedQualifierImpl transformTypeArguments(FirTransformer<? super D> transformer, D data) {
        Intrinsics.checkNotNullParameter(transformer, "transformer");
        FirTransformerUtilKt.m7082transformInplaceaLnlfrU(m7002getTypeArguments5e3fPpI(), transformer, data);
        return this;
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirResolvedQualifier, org.jetbrains.kotlin.fir.expressions.FirExpression
    public void replaceTypeRef(FirTypeRef newTypeRef) {
        Intrinsics.checkNotNullParameter(newTypeRef, "newTypeRef");
        setTypeRef(newTypeRef);
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirResolvedQualifier, org.jetbrains.kotlin.fir.expressions.FirExpression, org.jetbrains.kotlin.fir.expressions.FirStatement, org.jetbrains.kotlin.fir.FirAnnotationContainer
    public void replaceAnnotations(List<? extends FirAnnotation> newAnnotations) {
        Intrinsics.checkNotNullParameter(newAnnotations, "newAnnotations");
        m7003setAnnotationsGqUYUs(FirBuilderDslKt.toMutableOrEmptyForImmutable(newAnnotations));
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirResolvedQualifier
    public void replaceIsNullableLHSForCallableReference(boolean newIsNullableLHSForCallableReference) {
        setNullableLHSForCallableReference(newIsNullableLHSForCallableReference);
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirResolvedQualifier
    public void replaceResolvedToCompanionObject(boolean newResolvedToCompanionObject) {
        setResolvedToCompanionObject(newResolvedToCompanionObject);
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirResolvedQualifier
    public void replaceTypeArguments(List<? extends FirTypeProjection> newTypeArguments) {
        Intrinsics.checkNotNullParameter(newTypeArguments, "newTypeArguments");
        m7005setTypeArgumentsGqUYUs(FirBuilderDslKt.toMutableOrEmptyForImmutable(newTypeArguments));
    }
}
