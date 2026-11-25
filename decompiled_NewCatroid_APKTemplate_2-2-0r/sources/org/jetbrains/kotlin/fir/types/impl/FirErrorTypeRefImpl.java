package org.jetbrains.kotlin.fir.types.impl;

import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.Constants;
import org.jetbrains.kotlin.KtSourceElement;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.fir.FirAnnotationContainer;
import org.jetbrains.kotlin.fir.FirElement;
import org.jetbrains.kotlin.fir.MutableOrEmptyList;
import org.jetbrains.kotlin.fir.diagnostics.ConeDiagnostic;
import org.jetbrains.kotlin.fir.expressions.FirAnnotation;
import org.jetbrains.kotlin.fir.types.ConeErrorType;
import org.jetbrains.kotlin.fir.types.ConeKotlinType;
import org.jetbrains.kotlin.fir.types.FirErrorTypeRef;
import org.jetbrains.kotlin.fir.types.FirResolvedTypeRef;
import org.jetbrains.kotlin.fir.types.FirTypeRef;
import org.jetbrains.kotlin.fir.visitors.FirTransformer;
import org.jetbrains.kotlin.fir.visitors.FirTransformerUtilKt;
import org.jetbrains.kotlin.fir.visitors.FirVisitor;

/* JADX INFO: compiled from: FirErrorTypeRefImpl.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000X\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0011\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010 \n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\b\u0000\u0018\u00002\u00020\u0001B9\b\u0016\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\b\b\u0002\u0010\b\u001a\u00020\t\u0012\n\b\u0002\u0010\n\u001a\u0004\u0018\u00010\u0005¢\u0006\u0002\u0010\u000bB?\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\u0006\u0010\f\u001a\u00020\r\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\b\b\u0002\u0010\b\u001a\u00020\t\u0012\n\b\u0002\u0010\n\u001a\u0004\u0018\u00010\u0005¢\u0006\u0002\u0010\u000eJ5\u0010\"\u001a\u00020#\"\u0004\b\u0000\u0010$\"\u0004\b\u0001\u0010%2\u0012\u0010&\u001a\u000e\u0012\u0004\u0012\u0002H$\u0012\u0004\u0012\u0002H%0'2\u0006\u0010(\u001a\u0002H%H\u0016¢\u0006\u0002\u0010)J\u0016\u0010*\u001a\u00020#2\f\u0010+\u001a\b\u0012\u0004\u0012\u00020\u00110,H\u0016J)\u0010-\u001a\u00020\u0000\"\u0004\b\u0000\u0010%2\f\u0010.\u001a\b\u0012\u0004\u0012\u0002H%0/2\u0006\u0010(\u001a\u0002H%H\u0016¢\u0006\u0002\u00100J)\u00101\u001a\u00020\u0000\"\u0004\b\u0000\u0010%2\f\u0010.\u001a\b\u0012\u0004\u0012\u0002H%0/2\u0006\u0010(\u001a\u0002H%H\u0016¢\u0006\u0002\u00100J)\u00102\u001a\u00020\u0001\"\u0004\b\u0000\u0010%2\f\u0010.\u001a\b\u0012\u0004\u0012\u0002H%0/2\u0006\u0010(\u001a\u0002H%H\u0016¢\u0006\u0002\u00103R\"\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00110\u0010X\u0096\u0004ø\u0001\u0000ø\u0001\u0001¢\u0006\n\n\u0002\u0010\u0014\u001a\u0004\b\u0012\u0010\u0013R\u001c\u0010\u0004\u001a\u0004\u0018\u00010\u0005X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0015\u0010\u0016\"\u0004\b\u0017\u0010\u0018R\u0014\u0010\u0006\u001a\u00020\u0007X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u001aR\u0014\u0010\b\u001a\u00020\tX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\u001bR\u001c\u0010\n\u001a\u0004\u0018\u00010\u0005X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001c\u0010\u0016\"\u0004\b\u001d\u0010\u0018R\u0016\u0010\u0002\u001a\u0004\u0018\u00010\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u001e\u0010\u001fR\u0014\u0010\f\u001a\u00020\rX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b \u0010!\u0082\u0002\u000b\n\u0005\b¡\u001e0\u0001\n\u0002\b!¨\u00064"}, d2 = {"Lorg/jetbrains/kotlin/fir/types/impl/FirErrorTypeRefImpl;", "Lorg/jetbrains/kotlin/fir/types/FirErrorTypeRef;", FirebaseAnalytics.Param.SOURCE, "Lorg/jetbrains/kotlin/KtSourceElement;", "delegatedTypeRef", "Lorg/jetbrains/kotlin/fir/types/FirTypeRef;", "diagnostic", "Lorg/jetbrains/kotlin/fir/diagnostics/ConeDiagnostic;", "isFromStubType", "", "partiallyResolvedTypeRef", "(Lorg/jetbrains/kotlin/KtSourceElement;Lorg/jetbrains/kotlin/fir/types/FirTypeRef;Lorg/jetbrains/kotlin/fir/diagnostics/ConeDiagnostic;ZLorg/jetbrains/kotlin/fir/types/FirTypeRef;)V", ModuleXmlParser.TYPE, "Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;", "(Lorg/jetbrains/kotlin/KtSourceElement;Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;Lorg/jetbrains/kotlin/fir/types/FirTypeRef;Lorg/jetbrains/kotlin/fir/diagnostics/ConeDiagnostic;ZLorg/jetbrains/kotlin/fir/types/FirTypeRef;)V", "annotations", "Lorg/jetbrains/kotlin/fir/MutableOrEmptyList;", "Lorg/jetbrains/kotlin/fir/expressions/FirAnnotation;", "getAnnotations-5e3fPpI", "()Ljava/util/List;", "Ljava/util/List;", "getDelegatedTypeRef", "()Lorg/jetbrains/kotlin/fir/types/FirTypeRef;", "setDelegatedTypeRef", "(Lorg/jetbrains/kotlin/fir/types/FirTypeRef;)V", "getDiagnostic", "()Lorg/jetbrains/kotlin/fir/diagnostics/ConeDiagnostic;", "()Z", "getPartiallyResolvedTypeRef", "setPartiallyResolvedTypeRef", "getSource", "()Lorg/jetbrains/kotlin/KtSourceElement;", "getType", "()Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;", "acceptChildren", "", "R", "D", "visitor", "Lorg/jetbrains/kotlin/fir/visitors/FirVisitor;", "data", "(Lorg/jetbrains/kotlin/fir/visitors/FirVisitor;Ljava/lang/Object;)V", "replaceAnnotations", "newAnnotations", "", "transformAnnotations", "transformer", "Lorg/jetbrains/kotlin/fir/visitors/FirTransformer;", "(Lorg/jetbrains/kotlin/fir/visitors/FirTransformer;Ljava/lang/Object;)Lorg/jetbrains/kotlin/fir/types/impl/FirErrorTypeRefImpl;", "transformChildren", "transformPartiallyResolvedTypeRef", "(Lorg/jetbrains/kotlin/fir/visitors/FirTransformer;Ljava/lang/Object;)Lorg/jetbrains/kotlin/fir/types/FirErrorTypeRef;", Constants.TYPE_TREE}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirErrorTypeRefImpl extends FirErrorTypeRef {
    private final List<T> annotations;
    private FirTypeRef delegatedTypeRef;
    private final ConeDiagnostic diagnostic;
    private final boolean isFromStubType;
    private FirTypeRef partiallyResolvedTypeRef;
    private final KtSourceElement source;
    private final ConeKotlinType type;

    public /* synthetic */ FirErrorTypeRefImpl(KtSourceElement ktSourceElement, ConeKotlinType coneKotlinType, FirTypeRef firTypeRef, ConeDiagnostic coneDiagnostic, boolean z, FirTypeRef firTypeRef2, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(ktSourceElement, coneKotlinType, firTypeRef, coneDiagnostic, (i & 16) != 0 ? false : z, (i & 32) != 0 ? null : firTypeRef2);
    }

    @Override // org.jetbrains.kotlin.fir.types.FirErrorTypeRef, org.jetbrains.kotlin.fir.types.FirResolvedTypeRef, org.jetbrains.kotlin.fir.types.FirTypeRef, org.jetbrains.kotlin.fir.FirAnnotationContainer
    public /* bridge */ /* synthetic */ List getAnnotations() {
        return MutableOrEmptyList.m6756boximpl(m7073getAnnotations5e3fPpI());
    }

    @Override // org.jetbrains.kotlin.fir.FirAnnotationContainer
    public /* bridge */ /* synthetic */ FirAnnotationContainer transformAnnotations(FirTransformer firTransformer, Object obj) {
        return transformAnnotations((FirTransformer<? super Object>) firTransformer, obj);
    }

    @Override // org.jetbrains.kotlin.fir.types.FirErrorTypeRef, org.jetbrains.kotlin.fir.types.FirResolvedTypeRef, org.jetbrains.kotlin.fir.types.FirTypeRef, org.jetbrains.kotlin.fir.FirAnnotationContainer
    public /* bridge */ /* synthetic */ FirErrorTypeRef transformAnnotations(FirTransformer firTransformer, Object obj) {
        return transformAnnotations((FirTransformer<? super Object>) firTransformer, obj);
    }

    @Override // org.jetbrains.kotlin.fir.types.FirResolvedTypeRef, org.jetbrains.kotlin.fir.types.FirTypeRef, org.jetbrains.kotlin.fir.FirAnnotationContainer
    public /* bridge */ /* synthetic */ FirResolvedTypeRef transformAnnotations(FirTransformer firTransformer, Object obj) {
        return transformAnnotations((FirTransformer<? super Object>) firTransformer, obj);
    }

    @Override // org.jetbrains.kotlin.fir.types.FirTypeRef, org.jetbrains.kotlin.fir.FirAnnotationContainer
    public /* bridge */ /* synthetic */ FirTypeRef transformAnnotations(FirTransformer firTransformer, Object obj) {
        return transformAnnotations((FirTransformer<? super Object>) firTransformer, obj);
    }

    @Override // org.jetbrains.kotlin.fir.FirPureAbstractElement, org.jetbrains.kotlin.fir.FirElement
    public /* bridge */ /* synthetic */ FirElement transformChildren(FirTransformer firTransformer, Object obj) {
        return transformChildren((FirTransformer<? super Object>) firTransformer, obj);
    }

    @Override // org.jetbrains.kotlin.fir.types.FirErrorTypeRef, org.jetbrains.kotlin.fir.types.FirResolvedTypeRef, org.jetbrains.kotlin.fir.types.FirTypeRef, org.jetbrains.kotlin.fir.FirElement
    public KtSourceElement getSource() {
        return this.source;
    }

    @Override // org.jetbrains.kotlin.fir.types.FirErrorTypeRef, org.jetbrains.kotlin.fir.types.FirResolvedTypeRef
    public ConeKotlinType getType() {
        return this.type;
    }

    @Override // org.jetbrains.kotlin.fir.types.FirErrorTypeRef, org.jetbrains.kotlin.fir.types.FirResolvedTypeRef
    public FirTypeRef getDelegatedTypeRef() {
        return this.delegatedTypeRef;
    }

    public void setDelegatedTypeRef(FirTypeRef firTypeRef) {
        this.delegatedTypeRef = firTypeRef;
    }

    @Override // org.jetbrains.kotlin.fir.types.FirErrorTypeRef, org.jetbrains.kotlin.fir.diagnostics.FirDiagnosticHolder
    public ConeDiagnostic getDiagnostic() {
        return this.diagnostic;
    }

    @Override // org.jetbrains.kotlin.fir.types.FirErrorTypeRef, org.jetbrains.kotlin.fir.types.FirResolvedTypeRef
    /* JADX INFO: renamed from: isFromStubType, reason: from getter */
    public boolean getIsFromStubType() {
        return this.isFromStubType;
    }

    @Override // org.jetbrains.kotlin.fir.types.FirErrorTypeRef
    public FirTypeRef getPartiallyResolvedTypeRef() {
        return this.partiallyResolvedTypeRef;
    }

    public void setPartiallyResolvedTypeRef(FirTypeRef firTypeRef) {
        this.partiallyResolvedTypeRef = firTypeRef;
    }

    public FirErrorTypeRefImpl(KtSourceElement ktSourceElement, ConeKotlinType type, FirTypeRef firTypeRef, ConeDiagnostic diagnostic, boolean z, FirTypeRef firTypeRef2) {
        Intrinsics.checkNotNullParameter(type, "type");
        Intrinsics.checkNotNullParameter(diagnostic, "diagnostic");
        this.source = ktSourceElement;
        this.type = type;
        this.delegatedTypeRef = firTypeRef;
        this.diagnostic = diagnostic;
        this.isFromStubType = z;
        this.partiallyResolvedTypeRef = firTypeRef2;
        this.annotations = MutableOrEmptyList.INSTANCE.m6775empty5e3fPpI();
    }

    public /* synthetic */ FirErrorTypeRefImpl(KtSourceElement ktSourceElement, FirTypeRef firTypeRef, ConeDiagnostic coneDiagnostic, boolean z, FirTypeRef firTypeRef2, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(ktSourceElement, firTypeRef, coneDiagnostic, (i & 8) != 0 ? false : z, (i & 16) != 0 ? null : firTypeRef2);
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public FirErrorTypeRefImpl(KtSourceElement ktSourceElement, FirTypeRef firTypeRef, ConeDiagnostic diagnostic, boolean z, FirTypeRef firTypeRef2) {
        this(ktSourceElement, new ConeErrorType(diagnostic, false, null, null, 14, null), firTypeRef, diagnostic, z, firTypeRef2);
        Intrinsics.checkNotNullParameter(diagnostic, "diagnostic");
    }

    /* JADX INFO: renamed from: getAnnotations-5e3fPpI, reason: not valid java name */
    public List<T> m7073getAnnotations5e3fPpI() {
        return this.annotations;
    }

    @Override // org.jetbrains.kotlin.fir.FirPureAbstractElement, org.jetbrains.kotlin.fir.FirElement
    public <R, D> void acceptChildren(FirVisitor<? extends R, ? super D> visitor, D data) {
        Intrinsics.checkNotNullParameter(visitor, "visitor");
        Iterator<T> it = MutableOrEmptyList.m6756boximpl(m7073getAnnotations5e3fPpI()).iterator();
        while (it.hasNext()) {
            ((FirAnnotation) it.next()).accept(visitor, data);
        }
        FirTypeRef partiallyResolvedTypeRef = getPartiallyResolvedTypeRef();
        if (partiallyResolvedTypeRef != null) {
            partiallyResolvedTypeRef.accept(visitor, data);
        }
    }

    @Override // org.jetbrains.kotlin.fir.FirPureAbstractElement, org.jetbrains.kotlin.fir.FirElement
    public <D> FirErrorTypeRefImpl transformChildren(FirTransformer<? super D> transformer, D data) {
        Intrinsics.checkNotNullParameter(transformer, "transformer");
        transformAnnotations((FirTransformer) transformer, (Object) data);
        return this;
    }

    @Override // org.jetbrains.kotlin.fir.types.FirErrorTypeRef, org.jetbrains.kotlin.fir.types.FirResolvedTypeRef, org.jetbrains.kotlin.fir.types.FirTypeRef, org.jetbrains.kotlin.fir.FirAnnotationContainer
    public void replaceAnnotations(List<? extends FirAnnotation> newAnnotations) {
        Intrinsics.checkNotNullParameter(newAnnotations, "newAnnotations");
        throw new AssertionError("Replacing annotations in FirErrorTypeRefImpl is not supported");
    }

    @Override // org.jetbrains.kotlin.fir.types.FirErrorTypeRef, org.jetbrains.kotlin.fir.types.FirResolvedTypeRef, org.jetbrains.kotlin.fir.types.FirTypeRef, org.jetbrains.kotlin.fir.FirAnnotationContainer
    public <D> FirErrorTypeRefImpl transformAnnotations(FirTransformer<? super D> transformer, D data) {
        Intrinsics.checkNotNullParameter(transformer, "transformer");
        FirTransformerUtilKt.m7082transformInplaceaLnlfrU(m7073getAnnotations5e3fPpI(), transformer, data);
        return this;
    }

    @Override // org.jetbrains.kotlin.fir.types.FirErrorTypeRef
    public <D> FirErrorTypeRef transformPartiallyResolvedTypeRef(FirTransformer<? super D> transformer, D data) {
        Intrinsics.checkNotNullParameter(transformer, "transformer");
        FirTypeRef partiallyResolvedTypeRef = getPartiallyResolvedTypeRef();
        setPartiallyResolvedTypeRef(partiallyResolvedTypeRef != null ? (FirTypeRef) partiallyResolvedTypeRef.transform(transformer, data) : null);
        return this;
    }
}
