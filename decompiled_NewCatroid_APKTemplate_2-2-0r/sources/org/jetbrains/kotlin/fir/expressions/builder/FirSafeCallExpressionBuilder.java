package org.jetbrains.kotlin.fir.expressions.builder;

import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.ArrayList;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.Constants;
import org.jetbrains.kotlin.KtSourceElement;
import org.jetbrains.kotlin.fir.FirExpressionRef;
import org.jetbrains.kotlin.fir.builder.FirAnnotationContainerBuilder;
import org.jetbrains.kotlin.fir.builder.FirBuilderDsl;
import org.jetbrains.kotlin.fir.builder.FirBuilderDslKt;
import org.jetbrains.kotlin.fir.expressions.FirAnnotation;
import org.jetbrains.kotlin.fir.expressions.FirCheckedSafeCallSubject;
import org.jetbrains.kotlin.fir.expressions.FirExpression;
import org.jetbrains.kotlin.fir.expressions.FirSafeCallExpression;
import org.jetbrains.kotlin.fir.expressions.FirStatement;
import org.jetbrains.kotlin.fir.expressions.impl.FirSafeCallExpressionImpl;
import org.jetbrains.kotlin.fir.types.FirTypeRef;
import org.jetbrains.kotlin.fir.types.impl.FirImplicitTypeRefImplWithoutSource;

/* JADX INFO: compiled from: FirSafeCallExpressionBuilder.kt */
/* JADX INFO: loaded from: classes4.dex */
@FirBuilderDsl
@Metadata(d1 = {"\u0000N\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\b\u0007\u0018\u00002\u00020\u00012\u00020\u0002B\u0005¢\u0006\u0002\u0010\u0003J\b\u0010(\u001a\u00020)H\u0016R\u001a\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR \u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\nX\u0086.¢\u0006\u000e\n\u0000\u001a\u0004\b\f\u0010\r\"\u0004\b\u000e\u0010\u000fR\u001a\u0010\u0010\u001a\u00020\u0011X\u0086.¢\u0006\u000e\n\u0000\u001a\u0004\b\u0012\u0010\u0013\"\u0004\b\u0014\u0010\u0015R\u001a\u0010\u0016\u001a\u00020\u0017X\u0086.¢\u0006\u000e\n\u0000\u001a\u0004\b\u0018\u0010\u0019\"\u0004\b\u001a\u0010\u001bR\u001c\u0010\u001c\u001a\u0004\u0018\u00010\u001dX\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001e\u0010\u001f\"\u0004\b \u0010!R\u001a\u0010\"\u001a\u00020#X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b$\u0010%\"\u0004\b&\u0010'¨\u0006*"}, d2 = {"Lorg/jetbrains/kotlin/fir/expressions/builder/FirSafeCallExpressionBuilder;", "Lorg/jetbrains/kotlin/fir/builder/FirAnnotationContainerBuilder;", "Lorg/jetbrains/kotlin/fir/expressions/builder/FirExpressionBuilder;", "()V", "annotations", "", "Lorg/jetbrains/kotlin/fir/expressions/FirAnnotation;", "getAnnotations", "()Ljava/util/List;", "checkedSubjectRef", "Lorg/jetbrains/kotlin/fir/FirExpressionRef;", "Lorg/jetbrains/kotlin/fir/expressions/FirCheckedSafeCallSubject;", "getCheckedSubjectRef", "()Lorg/jetbrains/kotlin/fir/FirExpressionRef;", "setCheckedSubjectRef", "(Lorg/jetbrains/kotlin/fir/FirExpressionRef;)V", "receiver", "Lorg/jetbrains/kotlin/fir/expressions/FirExpression;", "getReceiver", "()Lorg/jetbrains/kotlin/fir/expressions/FirExpression;", "setReceiver", "(Lorg/jetbrains/kotlin/fir/expressions/FirExpression;)V", "selector", "Lorg/jetbrains/kotlin/fir/expressions/FirStatement;", "getSelector", "()Lorg/jetbrains/kotlin/fir/expressions/FirStatement;", "setSelector", "(Lorg/jetbrains/kotlin/fir/expressions/FirStatement;)V", FirebaseAnalytics.Param.SOURCE, "Lorg/jetbrains/kotlin/KtSourceElement;", "getSource", "()Lorg/jetbrains/kotlin/KtSourceElement;", "setSource", "(Lorg/jetbrains/kotlin/KtSourceElement;)V", "typeRef", "Lorg/jetbrains/kotlin/fir/types/FirTypeRef;", "getTypeRef", "()Lorg/jetbrains/kotlin/fir/types/FirTypeRef;", "setTypeRef", "(Lorg/jetbrains/kotlin/fir/types/FirTypeRef;)V", JsonPOJOBuilder.DEFAULT_BUILD_METHOD, "Lorg/jetbrains/kotlin/fir/expressions/FirSafeCallExpression;", Constants.TYPE_TREE}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirSafeCallExpressionBuilder implements FirAnnotationContainerBuilder, FirExpressionBuilder {
    public FirExpressionRef<FirCheckedSafeCallSubject> checkedSubjectRef;
    public FirExpression receiver;
    public FirStatement selector;
    private KtSourceElement source;
    private FirTypeRef typeRef = FirImplicitTypeRefImplWithoutSource.INSTANCE;
    private final List<FirAnnotation> annotations = new ArrayList();

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

    public final FirExpression getReceiver() {
        FirExpression firExpression = this.receiver;
        if (firExpression != null) {
            return firExpression;
        }
        Intrinsics.throwUninitializedPropertyAccessException("receiver");
        return null;
    }

    public final void setReceiver(FirExpression firExpression) {
        Intrinsics.checkNotNullParameter(firExpression, "<set-?>");
        this.receiver = firExpression;
    }

    public final FirExpressionRef<FirCheckedSafeCallSubject> getCheckedSubjectRef() {
        FirExpressionRef<FirCheckedSafeCallSubject> firExpressionRef = this.checkedSubjectRef;
        if (firExpressionRef != null) {
            return firExpressionRef;
        }
        Intrinsics.throwUninitializedPropertyAccessException("checkedSubjectRef");
        return null;
    }

    public final void setCheckedSubjectRef(FirExpressionRef<FirCheckedSafeCallSubject> firExpressionRef) {
        Intrinsics.checkNotNullParameter(firExpressionRef, "<set-?>");
        this.checkedSubjectRef = firExpressionRef;
    }

    public final FirStatement getSelector() {
        FirStatement firStatement = this.selector;
        if (firStatement != null) {
            return firStatement;
        }
        Intrinsics.throwUninitializedPropertyAccessException("selector");
        return null;
    }

    public final void setSelector(FirStatement firStatement) {
        Intrinsics.checkNotNullParameter(firStatement, "<set-?>");
        this.selector = firStatement;
    }

    @Override // org.jetbrains.kotlin.fir.builder.FirAnnotationContainerBuilder
    public FirSafeCallExpression build() {
        return new FirSafeCallExpressionImpl(getSource(), getTypeRef(), FirBuilderDslKt.toMutableOrEmpty(getAnnotations()), getReceiver(), getCheckedSubjectRef(), getSelector(), null);
    }
}
