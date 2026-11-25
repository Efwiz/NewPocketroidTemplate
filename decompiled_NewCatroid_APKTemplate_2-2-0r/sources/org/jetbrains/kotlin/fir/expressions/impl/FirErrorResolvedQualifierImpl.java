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
import org.jetbrains.kotlin.fir.diagnostics.ConeDiagnostic;
import org.jetbrains.kotlin.fir.expressions.FirAnnotation;
import org.jetbrains.kotlin.fir.expressions.FirErrorResolvedQualifier;
import org.jetbrains.kotlin.fir.expressions.FirExpression;
import org.jetbrains.kotlin.fir.expressions.FirResolvedQualifier;
import org.jetbrains.kotlin.fir.expressions.FirStatement;
import org.jetbrains.kotlin.fir.symbols.impl.FirClassLikeSymbol;
import org.jetbrains.kotlin.fir.types.FirTypeProjection;
import org.jetbrains.kotlin.fir.types.FirTypeRef;
import org.jetbrains.kotlin.fir.types.impl.FirImplicitTypeRefImplWithoutSource;
import org.jetbrains.kotlin.fir.visitors.FirTransformer;
import org.jetbrains.kotlin.fir.visitors.FirTransformerUtilKt;
import org.jetbrains.kotlin.fir.visitors.FirVisitor;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.name.FqName;
import org.mozilla.javascript.NativeSymbol;

/* JADX INFO: compiled from: FirErrorResolvedQualifierImpl.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000n\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0015\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010 \n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\b\u0004\b\u0000\u0018\u00002\u00020\u0001Bq\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005\u0012\u0006\u0010\u0007\u001a\u00020\b\u0012\b\u0010\t\u001a\u0004\u0018\u00010\b\u0012\f\u0010\n\u001a\b\u0012\u0002\b\u0003\u0018\u00010\u000b\u0012\u0006\u0010\f\u001a\u00020\r\u0012\u0006\u0010\u000e\u001a\u00020\r\u0012\f\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00100\u0005\u0012\f\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00120\u0005\u0012\u0006\u0010\u0013\u001a\u00020\u0010¢\u0006\u0002\u0010\u0014J5\u00106\u001a\u000207\"\u0004\b\u0000\u00108\"\u0004\b\u0001\u001092\u0012\u0010:\u001a\u000e\u0012\u0004\u0012\u0002H8\u0012\u0004\u0012\u0002H90;2\u0006\u0010<\u001a\u0002H9H\u0016¢\u0006\u0002\u0010=J\u0016\u0010>\u001a\u0002072\f\u0010?\u001a\b\u0012\u0004\u0012\u00020\u00060@H\u0016J\u0010\u0010A\u001a\u0002072\u0006\u0010B\u001a\u00020\rH\u0016J\u0010\u0010C\u001a\u0002072\u0006\u0010D\u001a\u00020\rH\u0016J\u0016\u0010E\u001a\u0002072\f\u0010F\u001a\b\u0012\u0004\u0012\u00020\u00120@H\u0016J\u0010\u0010G\u001a\u0002072\u0006\u0010H\u001a\u000201H\u0016J)\u0010I\u001a\u00020\u0000\"\u0004\b\u0000\u001092\f\u0010J\u001a\b\u0012\u0004\u0012\u0002H90K2\u0006\u0010<\u001a\u0002H9H\u0016¢\u0006\u0002\u0010LJ)\u0010M\u001a\u00020\u0000\"\u0004\b\u0000\u001092\f\u0010J\u001a\b\u0012\u0004\u0012\u0002H90K2\u0006\u0010<\u001a\u0002H9H\u0016¢\u0006\u0002\u0010LJ)\u0010N\u001a\u00020\u0000\"\u0004\b\u0000\u001092\f\u0010J\u001a\b\u0012\u0004\u0012\u0002H90K2\u0006\u0010<\u001a\u0002H9H\u0016¢\u0006\u0002\u0010LR(\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005X\u0096\u000eø\u0001\u0000ø\u0001\u0001¢\u0006\u0010\n\u0002\u0010\u0019\u001a\u0004\b\u0015\u0010\u0016\"\u0004\b\u0017\u0010\u0018R\u0016\u0010\u001a\u001a\u0004\u0018\u00010\u001b8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u001c\u0010\u001dR\u0014\u0010\u0013\u001a\u00020\u0010X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u001e\u0010\u001fR\u0014\u0010\u000e\u001a\u00020\rX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010 R\u001a\u0010\f\u001a\u00020\rX\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\f\u0010 \"\u0004\b!\u0010\"R(\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00100\u0005X\u0096\u000eø\u0001\u0000ø\u0001\u0001¢\u0006\u0010\n\u0002\u0010\u0019\u001a\u0004\b#\u0010\u0016\"\u0004\b$\u0010\u0018R\u0014\u0010\u0007\u001a\u00020\bX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b%\u0010&R\u0016\u0010\t\u001a\u0004\u0018\u00010\bX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b'\u0010&R\u0014\u0010(\u001a\u00020\r8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b)\u0010 R\u0016\u0010\u0002\u001a\u0004\u0018\u00010\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b*\u0010+R\u001a\u0010\n\u001a\b\u0012\u0002\b\u0003\u0018\u00010\u000bX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b,\u0010-R(\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00120\u0005X\u0096\u000eø\u0001\u0000ø\u0001\u0001¢\u0006\u0010\n\u0002\u0010\u0019\u001a\u0004\b.\u0010\u0016\"\u0004\b/\u0010\u0018R\u001a\u00100\u001a\u000201X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b2\u00103\"\u0004\b4\u00105\u0082\u0002\u000b\n\u0005\b¡\u001e0\u0001\n\u0002\b!¨\u0006O"}, d2 = {"Lorg/jetbrains/kotlin/fir/expressions/impl/FirErrorResolvedQualifierImpl;", "Lorg/jetbrains/kotlin/fir/expressions/FirErrorResolvedQualifier;", FirebaseAnalytics.Param.SOURCE, "Lorg/jetbrains/kotlin/KtSourceElement;", "annotations", "Lorg/jetbrains/kotlin/fir/MutableOrEmptyList;", "Lorg/jetbrains/kotlin/fir/expressions/FirAnnotation;", "packageFqName", "Lorg/jetbrains/kotlin/name/FqName;", "relativeClassFqName", NativeSymbol.TYPE_NAME, "Lorg/jetbrains/kotlin/fir/symbols/impl/FirClassLikeSymbol;", "isNullableLHSForCallableReference", "", "isFullyQualified", "nonFatalDiagnostics", "Lorg/jetbrains/kotlin/fir/diagnostics/ConeDiagnostic;", "typeArguments", "Lorg/jetbrains/kotlin/fir/types/FirTypeProjection;", "diagnostic", "(Lorg/jetbrains/kotlin/KtSourceElement;Ljava/util/List;Lorg/jetbrains/kotlin/name/FqName;Lorg/jetbrains/kotlin/name/FqName;Lorg/jetbrains/kotlin/fir/symbols/impl/FirClassLikeSymbol;ZZLjava/util/List;Ljava/util/List;Lorg/jetbrains/kotlin/fir/diagnostics/ConeDiagnostic;Lkotlin/jvm/internal/DefaultConstructorMarker;)V", "getAnnotations-5e3fPpI", "()Ljava/util/List;", "setAnnotations-GqUYU-s", "(Ljava/util/List;)V", "Ljava/util/List;", "classId", "Lorg/jetbrains/kotlin/name/ClassId;", "getClassId", "()Lorg/jetbrains/kotlin/name/ClassId;", "getDiagnostic", "()Lorg/jetbrains/kotlin/fir/diagnostics/ConeDiagnostic;", "()Z", "setNullableLHSForCallableReference", "(Z)V", "getNonFatalDiagnostics-5e3fPpI", "setNonFatalDiagnostics-GqUYU-s", "getPackageFqName", "()Lorg/jetbrains/kotlin/name/FqName;", "getRelativeClassFqName", "resolvedToCompanionObject", "getResolvedToCompanionObject", "getSource", "()Lorg/jetbrains/kotlin/KtSourceElement;", "getSymbol", "()Lorg/jetbrains/kotlin/fir/symbols/impl/FirClassLikeSymbol;", "getTypeArguments-5e3fPpI", "setTypeArguments-GqUYU-s", "typeRef", "Lorg/jetbrains/kotlin/fir/types/FirTypeRef;", "getTypeRef", "()Lorg/jetbrains/kotlin/fir/types/FirTypeRef;", "setTypeRef", "(Lorg/jetbrains/kotlin/fir/types/FirTypeRef;)V", "acceptChildren", "", "R", "D", "visitor", "Lorg/jetbrains/kotlin/fir/visitors/FirVisitor;", "data", "(Lorg/jetbrains/kotlin/fir/visitors/FirVisitor;Ljava/lang/Object;)V", "replaceAnnotations", "newAnnotations", "", "replaceIsNullableLHSForCallableReference", "newIsNullableLHSForCallableReference", "replaceResolvedToCompanionObject", "newResolvedToCompanionObject", "replaceTypeArguments", "newTypeArguments", "replaceTypeRef", "newTypeRef", "transformAnnotations", "transformer", "Lorg/jetbrains/kotlin/fir/visitors/FirTransformer;", "(Lorg/jetbrains/kotlin/fir/visitors/FirTransformer;Ljava/lang/Object;)Lorg/jetbrains/kotlin/fir/expressions/impl/FirErrorResolvedQualifierImpl;", "transformChildren", "transformTypeArguments", Constants.TYPE_TREE}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirErrorResolvedQualifierImpl extends FirErrorResolvedQualifier {
    private List<T> annotations;
    private final ConeDiagnostic diagnostic;
    private final boolean isFullyQualified;
    private boolean isNullableLHSForCallableReference;
    private List<T> nonFatalDiagnostics;
    private final FqName packageFqName;
    private final FqName relativeClassFqName;
    private final KtSourceElement source;
    private final FirClassLikeSymbol<?> symbol;
    private List<T> typeArguments;
    private FirTypeRef typeRef;

    public /* synthetic */ FirErrorResolvedQualifierImpl(KtSourceElement ktSourceElement, List list, FqName fqName, FqName fqName2, FirClassLikeSymbol firClassLikeSymbol, boolean z, boolean z2, List list2, List list3, ConeDiagnostic coneDiagnostic, DefaultConstructorMarker defaultConstructorMarker) {
        this(ktSourceElement, list, fqName, fqName2, firClassLikeSymbol, z, z2, list2, list3, coneDiagnostic);
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirErrorResolvedQualifier, org.jetbrains.kotlin.fir.expressions.FirResolvedQualifier
    public boolean getResolvedToCompanionObject() {
        return false;
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirErrorResolvedQualifier, org.jetbrains.kotlin.fir.expressions.FirResolvedQualifier
    public void replaceResolvedToCompanionObject(boolean newResolvedToCompanionObject) {
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirErrorResolvedQualifier, org.jetbrains.kotlin.fir.expressions.FirResolvedQualifier, org.jetbrains.kotlin.fir.expressions.FirExpression, org.jetbrains.kotlin.fir.expressions.FirStatement, org.jetbrains.kotlin.fir.FirAnnotationContainer
    public /* bridge */ /* synthetic */ List getAnnotations() {
        return MutableOrEmptyList.m6756boximpl(m6954getAnnotations5e3fPpI());
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirErrorResolvedQualifier, org.jetbrains.kotlin.fir.expressions.FirResolvedQualifier
    public /* bridge */ /* synthetic */ List getNonFatalDiagnostics() {
        return MutableOrEmptyList.m6756boximpl(m6955getNonFatalDiagnostics5e3fPpI());
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirErrorResolvedQualifier, org.jetbrains.kotlin.fir.expressions.FirResolvedQualifier
    public /* bridge */ /* synthetic */ List getTypeArguments() {
        return MutableOrEmptyList.m6756boximpl(m6956getTypeArguments5e3fPpI());
    }

    @Override // org.jetbrains.kotlin.fir.FirAnnotationContainer
    public /* bridge */ /* synthetic */ FirAnnotationContainer transformAnnotations(FirTransformer firTransformer, Object obj) {
        return transformAnnotations((FirTransformer<? super Object>) firTransformer, obj);
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirErrorResolvedQualifier, org.jetbrains.kotlin.fir.expressions.FirResolvedQualifier, org.jetbrains.kotlin.fir.expressions.FirExpression, org.jetbrains.kotlin.fir.expressions.FirStatement, org.jetbrains.kotlin.fir.FirAnnotationContainer
    public /* bridge */ /* synthetic */ FirErrorResolvedQualifier transformAnnotations(FirTransformer firTransformer, Object obj) {
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

    @Override // org.jetbrains.kotlin.fir.expressions.FirErrorResolvedQualifier, org.jetbrains.kotlin.fir.expressions.FirResolvedQualifier
    public /* bridge */ /* synthetic */ FirErrorResolvedQualifier transformTypeArguments(FirTransformer firTransformer, Object obj) {
        return transformTypeArguments((FirTransformer<? super Object>) firTransformer, obj);
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirResolvedQualifier
    public /* bridge */ /* synthetic */ FirResolvedQualifier transformTypeArguments(FirTransformer firTransformer, Object obj) {
        return transformTypeArguments((FirTransformer<? super Object>) firTransformer, obj);
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirErrorResolvedQualifier, org.jetbrains.kotlin.fir.expressions.FirResolvedQualifier, org.jetbrains.kotlin.fir.expressions.FirExpression, org.jetbrains.kotlin.fir.FirElement
    public KtSourceElement getSource() {
        return this.source;
    }

    /* JADX INFO: renamed from: getAnnotations-5e3fPpI, reason: not valid java name */
    public List<T> m6954getAnnotations5e3fPpI() {
        return this.annotations;
    }

    /* JADX INFO: renamed from: setAnnotations-GqUYU-s, reason: not valid java name */
    public void m6957setAnnotationsGqUYUs(List<T> list) {
        this.annotations = list;
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirErrorResolvedQualifier, org.jetbrains.kotlin.fir.expressions.FirResolvedQualifier
    public FqName getPackageFqName() {
        return this.packageFqName;
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirErrorResolvedQualifier, org.jetbrains.kotlin.fir.expressions.FirResolvedQualifier
    public FqName getRelativeClassFqName() {
        return this.relativeClassFqName;
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirErrorResolvedQualifier, org.jetbrains.kotlin.fir.expressions.FirResolvedQualifier
    public FirClassLikeSymbol<?> getSymbol() {
        return this.symbol;
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirErrorResolvedQualifier, org.jetbrains.kotlin.fir.expressions.FirResolvedQualifier
    /* JADX INFO: renamed from: isNullableLHSForCallableReference, reason: from getter */
    public boolean getIsNullableLHSForCallableReference() {
        return this.isNullableLHSForCallableReference;
    }

    public void setNullableLHSForCallableReference(boolean z) {
        this.isNullableLHSForCallableReference = z;
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirErrorResolvedQualifier, org.jetbrains.kotlin.fir.expressions.FirResolvedQualifier
    /* JADX INFO: renamed from: isFullyQualified, reason: from getter */
    public boolean getIsFullyQualified() {
        return this.isFullyQualified;
    }

    /* JADX INFO: renamed from: getNonFatalDiagnostics-5e3fPpI, reason: not valid java name */
    public List<T> m6955getNonFatalDiagnostics5e3fPpI() {
        return this.nonFatalDiagnostics;
    }

    /* JADX INFO: renamed from: setNonFatalDiagnostics-GqUYU-s, reason: not valid java name */
    public void m6958setNonFatalDiagnosticsGqUYUs(List<T> list) {
        this.nonFatalDiagnostics = list;
    }

    /* JADX INFO: renamed from: getTypeArguments-5e3fPpI, reason: not valid java name */
    public List<T> m6956getTypeArguments5e3fPpI() {
        return this.typeArguments;
    }

    /* JADX INFO: renamed from: setTypeArguments-GqUYU-s, reason: not valid java name */
    public void m6959setTypeArgumentsGqUYUs(List<T> list) {
        this.typeArguments = list;
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirErrorResolvedQualifier, org.jetbrains.kotlin.fir.diagnostics.FirDiagnosticHolder
    public ConeDiagnostic getDiagnostic() {
        return this.diagnostic;
    }

    private FirErrorResolvedQualifierImpl(KtSourceElement ktSourceElement, List<T> list, FqName packageFqName, FqName fqName, FirClassLikeSymbol<?> firClassLikeSymbol, boolean z, boolean z2, List<T> list2, List<T> list3, ConeDiagnostic diagnostic) {
        Intrinsics.checkNotNullParameter(packageFqName, "packageFqName");
        Intrinsics.checkNotNullParameter(diagnostic, "diagnostic");
        this.source = ktSourceElement;
        this.annotations = list;
        this.packageFqName = packageFqName;
        this.relativeClassFqName = fqName;
        this.symbol = firClassLikeSymbol;
        this.isNullableLHSForCallableReference = z;
        this.isFullyQualified = z2;
        this.nonFatalDiagnostics = list2;
        this.typeArguments = list3;
        this.diagnostic = diagnostic;
        this.typeRef = FirImplicitTypeRefImplWithoutSource.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirErrorResolvedQualifier, org.jetbrains.kotlin.fir.expressions.FirResolvedQualifier, org.jetbrains.kotlin.fir.expressions.FirExpression
    public FirTypeRef getTypeRef() {
        return this.typeRef;
    }

    public void setTypeRef(FirTypeRef firTypeRef) {
        Intrinsics.checkNotNullParameter(firTypeRef, "<set-?>");
        this.typeRef = firTypeRef;
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirErrorResolvedQualifier, org.jetbrains.kotlin.fir.expressions.FirResolvedQualifier
    public ClassId getClassId() {
        FqName relativeClassFqName = getRelativeClassFqName();
        if (relativeClassFqName != null) {
            return new ClassId(getPackageFqName(), relativeClassFqName, false);
        }
        return null;
    }

    @Override // org.jetbrains.kotlin.fir.FirPureAbstractElement, org.jetbrains.kotlin.fir.FirElement
    public <R, D> void acceptChildren(FirVisitor<? extends R, ? super D> visitor, D data) {
        Intrinsics.checkNotNullParameter(visitor, "visitor");
        getTypeRef().accept(visitor, data);
        Iterator<T> it = MutableOrEmptyList.m6756boximpl(m6954getAnnotations5e3fPpI()).iterator();
        while (it.hasNext()) {
            ((FirAnnotation) it.next()).accept(visitor, data);
        }
        Iterator<T> it2 = MutableOrEmptyList.m6756boximpl(m6956getTypeArguments5e3fPpI()).iterator();
        while (it2.hasNext()) {
            ((FirTypeProjection) it2.next()).accept(visitor, data);
        }
    }

    @Override // org.jetbrains.kotlin.fir.FirPureAbstractElement, org.jetbrains.kotlin.fir.FirElement
    public <D> FirErrorResolvedQualifierImpl transformChildren(FirTransformer<? super D> transformer, D data) {
        Intrinsics.checkNotNullParameter(transformer, "transformer");
        setTypeRef((FirTypeRef) getTypeRef().transform(transformer, data));
        transformAnnotations((FirTransformer) transformer, (Object) data);
        transformTypeArguments((FirTransformer) transformer, (Object) data);
        return this;
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirErrorResolvedQualifier, org.jetbrains.kotlin.fir.expressions.FirResolvedQualifier, org.jetbrains.kotlin.fir.expressions.FirExpression, org.jetbrains.kotlin.fir.expressions.FirStatement, org.jetbrains.kotlin.fir.FirAnnotationContainer
    public <D> FirErrorResolvedQualifierImpl transformAnnotations(FirTransformer<? super D> transformer, D data) {
        Intrinsics.checkNotNullParameter(transformer, "transformer");
        FirTransformerUtilKt.m7082transformInplaceaLnlfrU(m6954getAnnotations5e3fPpI(), transformer, data);
        return this;
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirErrorResolvedQualifier, org.jetbrains.kotlin.fir.expressions.FirResolvedQualifier
    public <D> FirErrorResolvedQualifierImpl transformTypeArguments(FirTransformer<? super D> transformer, D data) {
        Intrinsics.checkNotNullParameter(transformer, "transformer");
        FirTransformerUtilKt.m7082transformInplaceaLnlfrU(m6956getTypeArguments5e3fPpI(), transformer, data);
        return this;
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirErrorResolvedQualifier, org.jetbrains.kotlin.fir.expressions.FirResolvedQualifier, org.jetbrains.kotlin.fir.expressions.FirExpression
    public void replaceTypeRef(FirTypeRef newTypeRef) {
        Intrinsics.checkNotNullParameter(newTypeRef, "newTypeRef");
        setTypeRef(newTypeRef);
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirErrorResolvedQualifier, org.jetbrains.kotlin.fir.expressions.FirResolvedQualifier, org.jetbrains.kotlin.fir.expressions.FirExpression, org.jetbrains.kotlin.fir.expressions.FirStatement, org.jetbrains.kotlin.fir.FirAnnotationContainer
    public void replaceAnnotations(List<? extends FirAnnotation> newAnnotations) {
        Intrinsics.checkNotNullParameter(newAnnotations, "newAnnotations");
        m6957setAnnotationsGqUYUs(FirBuilderDslKt.toMutableOrEmptyForImmutable(newAnnotations));
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirErrorResolvedQualifier, org.jetbrains.kotlin.fir.expressions.FirResolvedQualifier
    public void replaceIsNullableLHSForCallableReference(boolean newIsNullableLHSForCallableReference) {
        setNullableLHSForCallableReference(newIsNullableLHSForCallableReference);
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirErrorResolvedQualifier, org.jetbrains.kotlin.fir.expressions.FirResolvedQualifier
    public void replaceTypeArguments(List<? extends FirTypeProjection> newTypeArguments) {
        Intrinsics.checkNotNullParameter(newTypeArguments, "newTypeArguments");
        m6959setTypeArgumentsGqUYUs(FirBuilderDslKt.toMutableOrEmptyForImmutable(newTypeArguments));
    }
}
