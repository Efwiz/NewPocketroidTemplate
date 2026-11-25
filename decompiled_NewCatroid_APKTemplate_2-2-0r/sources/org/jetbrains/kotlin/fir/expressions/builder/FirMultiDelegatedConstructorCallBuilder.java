package org.jetbrains.kotlin.fir.expressions.builder;

import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.ArrayList;
import java.util.List;
import kotlin.Deprecated;
import kotlin.DeprecationLevel;
import kotlin.Metadata;
import org.eclipse.jgit.lib.Constants;
import org.jetbrains.kotlin.KtSourceElement;
import org.jetbrains.kotlin.fir.builder.FirAnnotationContainerBuilder;
import org.jetbrains.kotlin.fir.builder.FirBuilderDsl;
import org.jetbrains.kotlin.fir.expressions.FirAnnotation;
import org.jetbrains.kotlin.fir.expressions.FirDelegatedConstructorCall;
import org.jetbrains.kotlin.fir.expressions.FirMultiDelegatedConstructorCall;
import org.jetbrains.kotlin.fir.expressions.impl.FirMultiDelegatedConstructorCallImpl;

/* JADX INFO: compiled from: FirMultiDelegatedConstructorCallBuilder.kt */
/* JADX INFO: loaded from: classes4.dex */
@FirBuilderDsl
@Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\b\u0010\u0014\u001a\u00020\u0015H\u0016R\"\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u00048\u0016X\u0097\u0004¢\u0006\u000e\n\u0000\u0012\u0004\b\u0006\u0010\u0002\u001a\u0004\b\u0007\u0010\bR\u0017\u0010\t\u001a\b\u0012\u0004\u0012\u00020\n0\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\bR.\u0010\u000e\u001a\u0004\u0018\u00010\r2\b\u0010\f\u001a\u0004\u0018\u00010\r8V@VX\u0097\u000e¢\u0006\u0012\u0012\u0004\b\u000f\u0010\u0002\u001a\u0004\b\u0010\u0010\u0011\"\u0004\b\u0012\u0010\u0013¨\u0006\u0016"}, d2 = {"Lorg/jetbrains/kotlin/fir/expressions/builder/FirMultiDelegatedConstructorCallBuilder;", "Lorg/jetbrains/kotlin/fir/builder/FirAnnotationContainerBuilder;", "()V", "annotations", "", "Lorg/jetbrains/kotlin/fir/expressions/FirAnnotation;", "getAnnotations$annotations", "getAnnotations", "()Ljava/util/List;", "delegatedConstructorCalls", "Lorg/jetbrains/kotlin/fir/expressions/FirDelegatedConstructorCall;", "getDelegatedConstructorCalls", "<anonymous parameter 0>", "Lorg/jetbrains/kotlin/KtSourceElement;", FirebaseAnalytics.Param.SOURCE, "getSource$annotations", "getSource", "()Lorg/jetbrains/kotlin/KtSourceElement;", "setSource", "(Lorg/jetbrains/kotlin/KtSourceElement;)V", JsonPOJOBuilder.DEFAULT_BUILD_METHOD, "Lorg/jetbrains/kotlin/fir/expressions/FirMultiDelegatedConstructorCall;", Constants.TYPE_TREE}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirMultiDelegatedConstructorCallBuilder implements FirAnnotationContainerBuilder {
    private final List<FirDelegatedConstructorCall> delegatedConstructorCalls = new ArrayList();
    private final List<FirAnnotation> annotations = new ArrayList();

    @Deprecated(level = DeprecationLevel.HIDDEN, message = "Modification of 'annotations' has no impact for FirMultiDelegatedConstructorCallBuilder")
    public static /* synthetic */ void getAnnotations$annotations() {
    }

    @Deprecated(level = DeprecationLevel.HIDDEN, message = "Modification of 'source' has no impact for FirMultiDelegatedConstructorCallBuilder")
    public static /* synthetic */ void getSource$annotations() {
    }

    public final List<FirDelegatedConstructorCall> getDelegatedConstructorCalls() {
        return this.delegatedConstructorCalls;
    }

    @Override // org.jetbrains.kotlin.fir.builder.FirAnnotationContainerBuilder
    public FirMultiDelegatedConstructorCall build() {
        return new FirMultiDelegatedConstructorCallImpl(this.delegatedConstructorCalls);
    }

    @Override // org.jetbrains.kotlin.fir.builder.FirAnnotationContainerBuilder
    public /* synthetic */ KtSourceElement getSource() {
        throw new IllegalStateException();
    }

    @Override // org.jetbrains.kotlin.fir.builder.FirAnnotationContainerBuilder
    public /* synthetic */ void setSource(KtSourceElement ktSourceElement) {
        throw new IllegalStateException();
    }

    @Override // org.jetbrains.kotlin.fir.builder.FirAnnotationContainerBuilder
    public /* synthetic */ List getAnnotations() {
        return this.annotations;
    }
}
