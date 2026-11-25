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
import org.jetbrains.kotlin.fir.declarations.FirVariable;
import org.jetbrains.kotlin.fir.expressions.ExhaustivenessStatus;
import org.jetbrains.kotlin.fir.expressions.FirAnnotation;
import org.jetbrains.kotlin.fir.expressions.FirExpression;
import org.jetbrains.kotlin.fir.expressions.FirResolvable;
import org.jetbrains.kotlin.fir.expressions.FirStatement;
import org.jetbrains.kotlin.fir.expressions.FirWhenBranch;
import org.jetbrains.kotlin.fir.expressions.FirWhenExpression;
import org.jetbrains.kotlin.fir.references.FirReference;
import org.jetbrains.kotlin.fir.types.FirTypeRef;
import org.jetbrains.kotlin.fir.visitors.FirTransformer;
import org.jetbrains.kotlin.fir.visitors.FirTransformerUtilKt;
import org.jetbrains.kotlin.fir.visitors.FirVisitor;

/* JADX INFO: compiled from: FirWhenExpressionImpl.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000j\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b \n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010 \n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0007\b\u0000\u0018\u00002\u00020\u0001Ba\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u0007\u0012\u0006\u0010\t\u001a\u00020\n\u0012\b\u0010\u000b\u001a\u0004\u0018\u00010\f\u0012\b\u0010\r\u001a\u0004\u0018\u00010\u000e\u0012\f\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00110\u0010\u0012\b\u0010\u0012\u001a\u0004\u0018\u00010\u0013\u0012\u0006\u0010\u0014\u001a\u00020\u0015¢\u0006\u0002\u0010\u0016J5\u00105\u001a\u000206\"\u0004\b\u0000\u00107\"\u0004\b\u0001\u001082\u0012\u00109\u001a\u000e\u0012\u0004\u0012\u0002H7\u0012\u0004\u0012\u0002H80:2\u0006\u0010;\u001a\u0002H8H\u0016¢\u0006\u0002\u0010<J\u0016\u0010=\u001a\u0002062\f\u0010>\u001a\b\u0012\u0004\u0012\u00020\b0?H\u0016J\u0010\u0010@\u001a\u0002062\u0006\u0010A\u001a\u00020\nH\u0016J\u0012\u0010B\u001a\u0002062\b\u0010C\u001a\u0004\u0018\u00010\u0013H\u0016J\u0010\u0010D\u001a\u0002062\u0006\u0010E\u001a\u00020\u0005H\u0016J)\u0010F\u001a\u00020\u0000\"\u0004\b\u0000\u001082\f\u0010G\u001a\b\u0012\u0004\u0012\u0002H80H2\u0006\u0010;\u001a\u0002H8H\u0016¢\u0006\u0002\u0010IJ)\u0010J\u001a\u00020\u0000\"\u0004\b\u0000\u001082\f\u0010G\u001a\b\u0012\u0004\u0012\u0002H80H2\u0006\u0010;\u001a\u0002H8H\u0016¢\u0006\u0002\u0010IJ)\u0010K\u001a\u00020\u0000\"\u0004\b\u0000\u001082\f\u0010G\u001a\b\u0012\u0004\u0012\u0002H80H2\u0006\u0010;\u001a\u0002H8H\u0016¢\u0006\u0002\u0010IJ)\u0010L\u001a\u00020\u0000\"\u0004\b\u0000\u001082\f\u0010G\u001a\b\u0012\u0004\u0012\u0002H80H2\u0006\u0010;\u001a\u0002H8H\u0016¢\u0006\u0002\u0010IJ)\u0010M\u001a\u00020\u0000\"\u0004\b\u0000\u001082\f\u0010G\u001a\b\u0012\u0004\u0012\u0002H80H2\u0006\u0010;\u001a\u0002H8H\u0016¢\u0006\u0002\u0010IJ)\u0010N\u001a\u00020\u0000\"\u0004\b\u0000\u001082\f\u0010G\u001a\b\u0012\u0004\u0012\u0002H80H2\u0006\u0010;\u001a\u0002H8H\u0016¢\u0006\u0002\u0010IR(\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u0007X\u0096\u000eø\u0001\u0000ø\u0001\u0001¢\u0006\u0010\n\u0002\u0010\u001b\u001a\u0004\b\u0017\u0010\u0018\"\u0004\b\u0019\u0010\u001aR\u001a\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00110\u0010X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\u0018R\u001a\u0010\t\u001a\u00020\nX\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001d\u0010\u001e\"\u0004\b\u001f\u0010 R\u001c\u0010\u0012\u001a\u0004\u0018\u00010\u0013X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b!\u0010\"\"\u0004\b#\u0010$R\u0016\u0010\u0002\u001a\u0004\u0018\u00010\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b%\u0010&R\u001c\u0010\u000b\u001a\u0004\u0018\u00010\fX\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b'\u0010(\"\u0004\b)\u0010*R\u001c\u0010\r\u001a\u0004\u0018\u00010\u000eX\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b+\u0010,\"\u0004\b-\u0010.R\u001a\u0010\u0004\u001a\u00020\u0005X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b/\u00100\"\u0004\b1\u00102R\u0014\u0010\u0014\u001a\u00020\u0015X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b3\u00104\u0082\u0002\u000b\n\u0005\b¡\u001e0\u0001\n\u0002\b!¨\u0006O"}, d2 = {"Lorg/jetbrains/kotlin/fir/expressions/impl/FirWhenExpressionImpl;", "Lorg/jetbrains/kotlin/fir/expressions/FirWhenExpression;", FirebaseAnalytics.Param.SOURCE, "Lorg/jetbrains/kotlin/KtSourceElement;", "typeRef", "Lorg/jetbrains/kotlin/fir/types/FirTypeRef;", "annotations", "Lorg/jetbrains/kotlin/fir/MutableOrEmptyList;", "Lorg/jetbrains/kotlin/fir/expressions/FirAnnotation;", "calleeReference", "Lorg/jetbrains/kotlin/fir/references/FirReference;", "subject", "Lorg/jetbrains/kotlin/fir/expressions/FirExpression;", "subjectVariable", "Lorg/jetbrains/kotlin/fir/declarations/FirVariable;", "branches", "", "Lorg/jetbrains/kotlin/fir/expressions/FirWhenBranch;", "exhaustivenessStatus", "Lorg/jetbrains/kotlin/fir/expressions/ExhaustivenessStatus;", "usedAsExpression", "", "(Lorg/jetbrains/kotlin/KtSourceElement;Lorg/jetbrains/kotlin/fir/types/FirTypeRef;Ljava/util/List;Lorg/jetbrains/kotlin/fir/references/FirReference;Lorg/jetbrains/kotlin/fir/expressions/FirExpression;Lorg/jetbrains/kotlin/fir/declarations/FirVariable;Ljava/util/List;Lorg/jetbrains/kotlin/fir/expressions/ExhaustivenessStatus;ZLkotlin/jvm/internal/DefaultConstructorMarker;)V", "getAnnotations-5e3fPpI", "()Ljava/util/List;", "setAnnotations-GqUYU-s", "(Ljava/util/List;)V", "Ljava/util/List;", "getBranches", "getCalleeReference", "()Lorg/jetbrains/kotlin/fir/references/FirReference;", "setCalleeReference", "(Lorg/jetbrains/kotlin/fir/references/FirReference;)V", "getExhaustivenessStatus", "()Lorg/jetbrains/kotlin/fir/expressions/ExhaustivenessStatus;", "setExhaustivenessStatus", "(Lorg/jetbrains/kotlin/fir/expressions/ExhaustivenessStatus;)V", "getSource", "()Lorg/jetbrains/kotlin/KtSourceElement;", "getSubject", "()Lorg/jetbrains/kotlin/fir/expressions/FirExpression;", "setSubject", "(Lorg/jetbrains/kotlin/fir/expressions/FirExpression;)V", "getSubjectVariable", "()Lorg/jetbrains/kotlin/fir/declarations/FirVariable;", "setSubjectVariable", "(Lorg/jetbrains/kotlin/fir/declarations/FirVariable;)V", "getTypeRef", "()Lorg/jetbrains/kotlin/fir/types/FirTypeRef;", "setTypeRef", "(Lorg/jetbrains/kotlin/fir/types/FirTypeRef;)V", "getUsedAsExpression", "()Z", "acceptChildren", "", "R", "D", "visitor", "Lorg/jetbrains/kotlin/fir/visitors/FirVisitor;", "data", "(Lorg/jetbrains/kotlin/fir/visitors/FirVisitor;Ljava/lang/Object;)V", "replaceAnnotations", "newAnnotations", "", "replaceCalleeReference", "newCalleeReference", "replaceExhaustivenessStatus", "newExhaustivenessStatus", "replaceTypeRef", "newTypeRef", "transformAnnotations", "transformer", "Lorg/jetbrains/kotlin/fir/visitors/FirTransformer;", "(Lorg/jetbrains/kotlin/fir/visitors/FirTransformer;Ljava/lang/Object;)Lorg/jetbrains/kotlin/fir/expressions/impl/FirWhenExpressionImpl;", "transformBranches", "transformCalleeReference", "transformChildren", "transformOtherChildren", "transformSubject", Constants.TYPE_TREE}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirWhenExpressionImpl extends FirWhenExpression {
    private List<T> annotations;
    private final List<FirWhenBranch> branches;
    private FirReference calleeReference;
    private ExhaustivenessStatus exhaustivenessStatus;
    private final KtSourceElement source;
    private FirExpression subject;
    private FirVariable subjectVariable;
    private FirTypeRef typeRef;
    private final boolean usedAsExpression;

    public /* synthetic */ FirWhenExpressionImpl(KtSourceElement ktSourceElement, FirTypeRef firTypeRef, List list, FirReference firReference, FirExpression firExpression, FirVariable firVariable, List list2, ExhaustivenessStatus exhaustivenessStatus, boolean z, DefaultConstructorMarker defaultConstructorMarker) {
        this(ktSourceElement, firTypeRef, list, firReference, firExpression, firVariable, list2, exhaustivenessStatus, z);
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirWhenExpression, org.jetbrains.kotlin.fir.expressions.FirExpression, org.jetbrains.kotlin.fir.expressions.FirStatement, org.jetbrains.kotlin.fir.FirAnnotationContainer
    public /* bridge */ /* synthetic */ List getAnnotations() {
        return MutableOrEmptyList.m6756boximpl(m7038getAnnotations5e3fPpI());
    }

    @Override // org.jetbrains.kotlin.fir.FirAnnotationContainer
    public /* bridge */ /* synthetic */ FirAnnotationContainer transformAnnotations(FirTransformer firTransformer, Object obj) {
        return transformAnnotations((FirTransformer<? super Object>) firTransformer, obj);
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirExpression, org.jetbrains.kotlin.fir.expressions.FirStatement, org.jetbrains.kotlin.fir.FirAnnotationContainer
    public /* bridge */ /* synthetic */ FirExpression transformAnnotations(FirTransformer firTransformer, Object obj) {
        return transformAnnotations((FirTransformer<? super Object>) firTransformer, obj);
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirStatement, org.jetbrains.kotlin.fir.FirAnnotationContainer
    public /* bridge */ /* synthetic */ FirStatement transformAnnotations(FirTransformer firTransformer, Object obj) {
        return transformAnnotations((FirTransformer<? super Object>) firTransformer, obj);
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirWhenExpression, org.jetbrains.kotlin.fir.expressions.FirExpression, org.jetbrains.kotlin.fir.expressions.FirStatement, org.jetbrains.kotlin.fir.FirAnnotationContainer
    public /* bridge */ /* synthetic */ FirWhenExpression transformAnnotations(FirTransformer firTransformer, Object obj) {
        return transformAnnotations((FirTransformer<? super Object>) firTransformer, obj);
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirWhenExpression
    public /* bridge */ /* synthetic */ FirWhenExpression transformBranches(FirTransformer firTransformer, Object obj) {
        return transformBranches((FirTransformer<? super Object>) firTransformer, obj);
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirResolvable
    public /* bridge */ /* synthetic */ FirResolvable transformCalleeReference(FirTransformer firTransformer, Object obj) {
        return transformCalleeReference((FirTransformer<? super Object>) firTransformer, obj);
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirWhenExpression, org.jetbrains.kotlin.fir.expressions.FirResolvable
    public /* bridge */ /* synthetic */ FirWhenExpression transformCalleeReference(FirTransformer firTransformer, Object obj) {
        return transformCalleeReference((FirTransformer<? super Object>) firTransformer, obj);
    }

    @Override // org.jetbrains.kotlin.fir.FirPureAbstractElement, org.jetbrains.kotlin.fir.FirElement
    public /* bridge */ /* synthetic */ FirElement transformChildren(FirTransformer firTransformer, Object obj) {
        return transformChildren((FirTransformer<? super Object>) firTransformer, obj);
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirWhenExpression
    public /* bridge */ /* synthetic */ FirWhenExpression transformOtherChildren(FirTransformer firTransformer, Object obj) {
        return transformOtherChildren((FirTransformer<? super Object>) firTransformer, obj);
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirWhenExpression
    public /* bridge */ /* synthetic */ FirWhenExpression transformSubject(FirTransformer firTransformer, Object obj) {
        return transformSubject((FirTransformer<? super Object>) firTransformer, obj);
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirWhenExpression, org.jetbrains.kotlin.fir.expressions.FirExpression, org.jetbrains.kotlin.fir.FirElement
    public KtSourceElement getSource() {
        return this.source;
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirWhenExpression, org.jetbrains.kotlin.fir.expressions.FirExpression
    public FirTypeRef getTypeRef() {
        return this.typeRef;
    }

    public void setTypeRef(FirTypeRef firTypeRef) {
        Intrinsics.checkNotNullParameter(firTypeRef, "<set-?>");
        this.typeRef = firTypeRef;
    }

    /* JADX INFO: renamed from: getAnnotations-5e3fPpI, reason: not valid java name */
    public List<T> m7038getAnnotations5e3fPpI() {
        return this.annotations;
    }

    /* JADX INFO: renamed from: setAnnotations-GqUYU-s, reason: not valid java name */
    public void m7039setAnnotationsGqUYUs(List<T> list) {
        this.annotations = list;
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirWhenExpression, org.jetbrains.kotlin.fir.expressions.FirResolvable
    public FirReference getCalleeReference() {
        return this.calleeReference;
    }

    public void setCalleeReference(FirReference firReference) {
        Intrinsics.checkNotNullParameter(firReference, "<set-?>");
        this.calleeReference = firReference;
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirWhenExpression
    public FirExpression getSubject() {
        return this.subject;
    }

    public void setSubject(FirExpression firExpression) {
        this.subject = firExpression;
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirWhenExpression
    public FirVariable getSubjectVariable() {
        return this.subjectVariable;
    }

    public void setSubjectVariable(FirVariable firVariable) {
        this.subjectVariable = firVariable;
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirWhenExpression
    public List<FirWhenBranch> getBranches() {
        return this.branches;
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirWhenExpression
    public ExhaustivenessStatus getExhaustivenessStatus() {
        return this.exhaustivenessStatus;
    }

    public void setExhaustivenessStatus(ExhaustivenessStatus exhaustivenessStatus) {
        this.exhaustivenessStatus = exhaustivenessStatus;
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirWhenExpression
    public boolean getUsedAsExpression() {
        return this.usedAsExpression;
    }

    private FirWhenExpressionImpl(KtSourceElement ktSourceElement, FirTypeRef typeRef, List<T> list, FirReference calleeReference, FirExpression firExpression, FirVariable firVariable, List<FirWhenBranch> branches, ExhaustivenessStatus exhaustivenessStatus, boolean z) {
        Intrinsics.checkNotNullParameter(typeRef, "typeRef");
        Intrinsics.checkNotNullParameter(calleeReference, "calleeReference");
        Intrinsics.checkNotNullParameter(branches, "branches");
        this.source = ktSourceElement;
        this.typeRef = typeRef;
        this.annotations = list;
        this.calleeReference = calleeReference;
        this.subject = firExpression;
        this.subjectVariable = firVariable;
        this.branches = branches;
        this.exhaustivenessStatus = exhaustivenessStatus;
        this.usedAsExpression = z;
    }

    @Override // org.jetbrains.kotlin.fir.FirPureAbstractElement, org.jetbrains.kotlin.fir.FirElement
    public <R, D> void acceptChildren(FirVisitor<? extends R, ? super D> visitor, D data) {
        Intrinsics.checkNotNullParameter(visitor, "visitor");
        getTypeRef().accept(visitor, data);
        Iterator<T> it = MutableOrEmptyList.m6756boximpl(m7038getAnnotations5e3fPpI()).iterator();
        while (it.hasNext()) {
            ((FirAnnotation) it.next()).accept(visitor, data);
        }
        getCalleeReference().accept(visitor, data);
        FirVariable subjectVariable = getSubjectVariable();
        if (subjectVariable != null) {
            subjectVariable.accept(visitor, data);
        } else {
            FirExpression subject = getSubject();
            if (subject != null) {
                subject.accept(visitor, data);
            }
        }
        Iterator<T> it2 = getBranches().iterator();
        while (it2.hasNext()) {
            ((FirWhenBranch) it2.next()).accept(visitor, data);
        }
    }

    @Override // org.jetbrains.kotlin.fir.FirPureAbstractElement, org.jetbrains.kotlin.fir.FirElement
    public <D> FirWhenExpressionImpl transformChildren(FirTransformer<? super D> transformer, D data) {
        Intrinsics.checkNotNullParameter(transformer, "transformer");
        transformCalleeReference((FirTransformer) transformer, (Object) data);
        transformSubject((FirTransformer) transformer, (Object) data);
        transformBranches((FirTransformer) transformer, (Object) data);
        transformOtherChildren((FirTransformer) transformer, (Object) data);
        return this;
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirWhenExpression, org.jetbrains.kotlin.fir.expressions.FirExpression, org.jetbrains.kotlin.fir.expressions.FirStatement, org.jetbrains.kotlin.fir.FirAnnotationContainer
    public <D> FirWhenExpressionImpl transformAnnotations(FirTransformer<? super D> transformer, D data) {
        Intrinsics.checkNotNullParameter(transformer, "transformer");
        FirTransformerUtilKt.m7082transformInplaceaLnlfrU(m7038getAnnotations5e3fPpI(), transformer, data);
        return this;
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirWhenExpression, org.jetbrains.kotlin.fir.expressions.FirResolvable
    public <D> FirWhenExpressionImpl transformCalleeReference(FirTransformer<? super D> transformer, D data) {
        Intrinsics.checkNotNullParameter(transformer, "transformer");
        setCalleeReference((FirReference) getCalleeReference().transform(transformer, data));
        return this;
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirWhenExpression
    public <D> FirWhenExpressionImpl transformSubject(FirTransformer<? super D> transformer, D data) {
        Intrinsics.checkNotNullParameter(transformer, "transformer");
        if (getSubjectVariable() != null) {
            FirVariable subjectVariable = getSubjectVariable();
            setSubjectVariable(subjectVariable != null ? (FirVariable) subjectVariable.transform(transformer, data) : null);
            FirVariable subjectVariable2 = getSubjectVariable();
            setSubject(subjectVariable2 != null ? subjectVariable2.getInitializer() : null);
        } else {
            FirExpression subject = getSubject();
            setSubject(subject != null ? (FirExpression) subject.transform(transformer, data) : null);
        }
        return this;
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirWhenExpression
    public <D> FirWhenExpressionImpl transformBranches(FirTransformer<? super D> transformer, D data) {
        Intrinsics.checkNotNullParameter(transformer, "transformer");
        FirTransformerUtilKt.transformInplace(getBranches(), transformer, data);
        return this;
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirWhenExpression
    public <D> FirWhenExpressionImpl transformOtherChildren(FirTransformer<? super D> transformer, D data) {
        Intrinsics.checkNotNullParameter(transformer, "transformer");
        setTypeRef((FirTypeRef) getTypeRef().transform(transformer, data));
        transformAnnotations((FirTransformer) transformer, (Object) data);
        return this;
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirWhenExpression, org.jetbrains.kotlin.fir.expressions.FirExpression
    public void replaceTypeRef(FirTypeRef newTypeRef) {
        Intrinsics.checkNotNullParameter(newTypeRef, "newTypeRef");
        setTypeRef(newTypeRef);
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirWhenExpression, org.jetbrains.kotlin.fir.expressions.FirExpression, org.jetbrains.kotlin.fir.expressions.FirStatement, org.jetbrains.kotlin.fir.FirAnnotationContainer
    public void replaceAnnotations(List<? extends FirAnnotation> newAnnotations) {
        Intrinsics.checkNotNullParameter(newAnnotations, "newAnnotations");
        m7039setAnnotationsGqUYUs(FirBuilderDslKt.toMutableOrEmptyForImmutable(newAnnotations));
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirWhenExpression, org.jetbrains.kotlin.fir.expressions.FirResolvable
    public void replaceCalleeReference(FirReference newCalleeReference) {
        Intrinsics.checkNotNullParameter(newCalleeReference, "newCalleeReference");
        setCalleeReference(newCalleeReference);
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirWhenExpression
    public void replaceExhaustivenessStatus(ExhaustivenessStatus newExhaustivenessStatus) {
        setExhaustivenessStatus(newExhaustivenessStatus);
    }
}
