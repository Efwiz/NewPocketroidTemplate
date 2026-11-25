package org.jetbrains.kotlin.fir.expressions.builder;

import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.ArrayList;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.MutablePropertyReference1Impl;
import kotlin.jvm.internal.Reflection;
import kotlin.properties.Delegates;
import kotlin.properties.ReadWriteProperty;
import kotlin.reflect.KProperty;
import org.eclipse.jgit.lib.Constants;
import org.jetbrains.kotlin.KtSourceElement;
import org.jetbrains.kotlin.fir.builder.FirAnnotationContainerBuilder;
import org.jetbrains.kotlin.fir.builder.FirBuilderDsl;
import org.jetbrains.kotlin.fir.builder.FirBuilderDslKt;
import org.jetbrains.kotlin.fir.declarations.FirVariable;
import org.jetbrains.kotlin.fir.expressions.ExhaustivenessStatus;
import org.jetbrains.kotlin.fir.expressions.FirAnnotation;
import org.jetbrains.kotlin.fir.expressions.FirExpression;
import org.jetbrains.kotlin.fir.expressions.FirWhenBranch;
import org.jetbrains.kotlin.fir.expressions.FirWhenExpression;
import org.jetbrains.kotlin.fir.expressions.impl.FirWhenExpressionImpl;
import org.jetbrains.kotlin.fir.references.FirReference;
import org.jetbrains.kotlin.fir.references.impl.FirStubReference;
import org.jetbrains.kotlin.fir.types.FirTypeRef;
import org.jetbrains.kotlin.fir.types.impl.FirImplicitTypeRefImplWithoutSource;

/* JADX INFO: compiled from: FirWhenExpressionBuilder.kt */
/* JADX INFO: loaded from: classes4.dex */
@FirBuilderDsl
@Metadata(d1 = {"\u0000b\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\b\n\u0002\u0018\u0002\n\u0000\b\u0007\u0018\u00002\u00020\u00012\u00020\u0002B\u0005¢\u0006\u0002\u0010\u0003J\b\u00109\u001a\u00020:H\u0016R\u001a\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0017\u0010\t\u001a\b\u0012\u0004\u0012\u00020\n0\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\bR\u001a\u0010\f\u001a\u00020\rX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000e\u0010\u000f\"\u0004\b\u0010\u0010\u0011R\u001c\u0010\u0012\u001a\u0004\u0018\u00010\u0013X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0014\u0010\u0015\"\u0004\b\u0016\u0010\u0017R\u001c\u0010\u0018\u001a\u0004\u0018\u00010\u0019X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001a\u0010\u001b\"\u0004\b\u001c\u0010\u001dR\u001c\u0010\u001e\u001a\u0004\u0018\u00010\u001fX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b \u0010!\"\u0004\b\"\u0010#R\u001c\u0010$\u001a\u0004\u0018\u00010%X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b&\u0010'\"\u0004\b(\u0010)R\u001a\u0010*\u001a\u00020+X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b,\u0010-\"\u0004\b.\u0010/R+\u00102\u001a\u0002012\u0006\u00100\u001a\u0002018F@FX\u0086\u008e\u0002¢\u0006\u0012\n\u0004\b7\u00108\u001a\u0004\b3\u00104\"\u0004\b5\u00106¨\u0006;"}, d2 = {"Lorg/jetbrains/kotlin/fir/expressions/builder/FirWhenExpressionBuilder;", "Lorg/jetbrains/kotlin/fir/builder/FirAnnotationContainerBuilder;", "Lorg/jetbrains/kotlin/fir/expressions/builder/FirExpressionBuilder;", "()V", "annotations", "", "Lorg/jetbrains/kotlin/fir/expressions/FirAnnotation;", "getAnnotations", "()Ljava/util/List;", "branches", "Lorg/jetbrains/kotlin/fir/expressions/FirWhenBranch;", "getBranches", "calleeReference", "Lorg/jetbrains/kotlin/fir/references/FirReference;", "getCalleeReference", "()Lorg/jetbrains/kotlin/fir/references/FirReference;", "setCalleeReference", "(Lorg/jetbrains/kotlin/fir/references/FirReference;)V", "exhaustivenessStatus", "Lorg/jetbrains/kotlin/fir/expressions/ExhaustivenessStatus;", "getExhaustivenessStatus", "()Lorg/jetbrains/kotlin/fir/expressions/ExhaustivenessStatus;", "setExhaustivenessStatus", "(Lorg/jetbrains/kotlin/fir/expressions/ExhaustivenessStatus;)V", FirebaseAnalytics.Param.SOURCE, "Lorg/jetbrains/kotlin/KtSourceElement;", "getSource", "()Lorg/jetbrains/kotlin/KtSourceElement;", "setSource", "(Lorg/jetbrains/kotlin/KtSourceElement;)V", "subject", "Lorg/jetbrains/kotlin/fir/expressions/FirExpression;", "getSubject", "()Lorg/jetbrains/kotlin/fir/expressions/FirExpression;", "setSubject", "(Lorg/jetbrains/kotlin/fir/expressions/FirExpression;)V", "subjectVariable", "Lorg/jetbrains/kotlin/fir/declarations/FirVariable;", "getSubjectVariable", "()Lorg/jetbrains/kotlin/fir/declarations/FirVariable;", "setSubjectVariable", "(Lorg/jetbrains/kotlin/fir/declarations/FirVariable;)V", "typeRef", "Lorg/jetbrains/kotlin/fir/types/FirTypeRef;", "getTypeRef", "()Lorg/jetbrains/kotlin/fir/types/FirTypeRef;", "setTypeRef", "(Lorg/jetbrains/kotlin/fir/types/FirTypeRef;)V", "<set-?>", "", "usedAsExpression", "getUsedAsExpression", "()Z", "setUsedAsExpression", "(Z)V", "usedAsExpression$delegate", "Lkotlin/properties/ReadWriteProperty;", JsonPOJOBuilder.DEFAULT_BUILD_METHOD, "Lorg/jetbrains/kotlin/fir/expressions/FirWhenExpression;", Constants.TYPE_TREE}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirWhenExpressionBuilder implements FirAnnotationContainerBuilder, FirExpressionBuilder {
    static final /* synthetic */ KProperty<Object>[] $$delegatedProperties = {Reflection.mutableProperty1(new MutablePropertyReference1Impl(Reflection.getOrCreateKotlinClass(FirWhenExpressionBuilder.class), "usedAsExpression", "getUsedAsExpression()Z"))};
    private ExhaustivenessStatus exhaustivenessStatus;
    private KtSourceElement source;
    private FirExpression subject;
    private FirVariable subjectVariable;
    private FirTypeRef typeRef = FirImplicitTypeRefImplWithoutSource.INSTANCE;
    private final List<FirAnnotation> annotations = new ArrayList();
    private FirReference calleeReference = FirStubReference.INSTANCE;
    private final List<FirWhenBranch> branches = new ArrayList();

    /* JADX INFO: renamed from: usedAsExpression$delegate, reason: from kotlin metadata */
    private final ReadWriteProperty usedAsExpression = Delegates.INSTANCE.notNull();

    @Override // org.jetbrains.kotlin.fir.builder.FirAnnotationContainerBuilder
    public KtSourceElement getSource() {
        return this.source;
    }

    @Override // org.jetbrains.kotlin.fir.builder.FirAnnotationContainerBuilder
    public void setSource(KtSourceElement ktSourceElement) {
        this.source = ktSourceElement;
    }

    @Override // org.jetbrains.kotlin.fir.expressions.builder.FirExpressionBuilder
    public FirTypeRef getTypeRef() {
        return this.typeRef;
    }

    @Override // org.jetbrains.kotlin.fir.expressions.builder.FirExpressionBuilder
    public void setTypeRef(FirTypeRef firTypeRef) {
        Intrinsics.checkNotNullParameter(firTypeRef, "<set-?>");
        this.typeRef = firTypeRef;
    }

    @Override // org.jetbrains.kotlin.fir.builder.FirAnnotationContainerBuilder
    public List<FirAnnotation> getAnnotations() {
        return this.annotations;
    }

    public final FirReference getCalleeReference() {
        return this.calleeReference;
    }

    public final void setCalleeReference(FirReference firReference) {
        Intrinsics.checkNotNullParameter(firReference, "<set-?>");
        this.calleeReference = firReference;
    }

    public final FirExpression getSubject() {
        return this.subject;
    }

    public final void setSubject(FirExpression firExpression) {
        this.subject = firExpression;
    }

    public final FirVariable getSubjectVariable() {
        return this.subjectVariable;
    }

    public final void setSubjectVariable(FirVariable firVariable) {
        this.subjectVariable = firVariable;
    }

    public final List<FirWhenBranch> getBranches() {
        return this.branches;
    }

    public final ExhaustivenessStatus getExhaustivenessStatus() {
        return this.exhaustivenessStatus;
    }

    public final void setExhaustivenessStatus(ExhaustivenessStatus exhaustivenessStatus) {
        this.exhaustivenessStatus = exhaustivenessStatus;
    }

    public final boolean getUsedAsExpression() {
        return ((Boolean) this.usedAsExpression.getValue(this, $$delegatedProperties[0])).booleanValue();
    }

    public final void setUsedAsExpression(boolean z) {
        this.usedAsExpression.setValue(this, $$delegatedProperties[0], Boolean.valueOf(z));
    }

    @Override // org.jetbrains.kotlin.fir.builder.FirAnnotationContainerBuilder
    public FirWhenExpression build() {
        return new FirWhenExpressionImpl(getSource(), getTypeRef(), FirBuilderDslKt.toMutableOrEmpty(getAnnotations()), this.calleeReference, this.subject, this.subjectVariable, this.branches, this.exhaustivenessStatus, getUsedAsExpression(), null);
    }
}
