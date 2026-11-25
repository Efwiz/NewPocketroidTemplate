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
import org.jetbrains.kotlin.fir.expressions.FirAnnotation;
import org.jetbrains.kotlin.fir.expressions.FirArgumentList;
import org.jetbrains.kotlin.fir.expressions.FirDelegatedConstructorCall;
import org.jetbrains.kotlin.fir.expressions.FirEmptyArgumentList;
import org.jetbrains.kotlin.fir.expressions.FirExpression;
import org.jetbrains.kotlin.fir.expressions.impl.FirDelegatedConstructorCallImpl;
import org.jetbrains.kotlin.fir.expressions.impl.FirNoReceiverExpression;
import org.jetbrains.kotlin.fir.references.FirReference;
import org.jetbrains.kotlin.fir.types.FirTypeRef;

/* JADX INFO: compiled from: FirDelegatedConstructorCallBuilder.kt */
/* JADX INFO: loaded from: classes4.dex */
@FirBuilderDsl
@Metadata(d1 = {"\u0000R\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u000b\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\b\u0007\u0018\u00002\u00020\u00012\u00020\u0002B\u0005¢\u0006\u0002\u0010\u0003J\b\u00101\u001a\u000202H\u0016R\u001a\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u001a\u0010\t\u001a\u00020\nX\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000eR\u001a\u0010\u000f\u001a\u00020\u0010X\u0086.¢\u0006\u000e\n\u0000\u001a\u0004\b\u0011\u0010\u0012\"\u0004\b\u0013\u0010\u0014R\u001a\u0010\u0015\u001a\u00020\u0016X\u0086.¢\u0006\u000e\n\u0000\u001a\u0004\b\u0017\u0010\u0018\"\u0004\b\u0019\u0010\u001aR\u0017\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\u001c0\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u001d\u0010\bR\u001a\u0010\u001e\u001a\u00020\u001cX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001f\u0010 \"\u0004\b!\u0010\"R+\u0010%\u001a\u00020$2\u0006\u0010#\u001a\u00020$8F@FX\u0086\u008e\u0002¢\u0006\u0012\n\u0004\b)\u0010*\u001a\u0004\b%\u0010&\"\u0004\b'\u0010(R\u001c\u0010+\u001a\u0004\u0018\u00010,X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b-\u0010.\"\u0004\b/\u00100¨\u00063"}, d2 = {"Lorg/jetbrains/kotlin/fir/expressions/builder/FirDelegatedConstructorCallBuilder;", "Lorg/jetbrains/kotlin/fir/expressions/builder/FirCallBuilder;", "Lorg/jetbrains/kotlin/fir/builder/FirAnnotationContainerBuilder;", "()V", "annotations", "", "Lorg/jetbrains/kotlin/fir/expressions/FirAnnotation;", "getAnnotations", "()Ljava/util/List;", "argumentList", "Lorg/jetbrains/kotlin/fir/expressions/FirArgumentList;", "getArgumentList", "()Lorg/jetbrains/kotlin/fir/expressions/FirArgumentList;", "setArgumentList", "(Lorg/jetbrains/kotlin/fir/expressions/FirArgumentList;)V", "calleeReference", "Lorg/jetbrains/kotlin/fir/references/FirReference;", "getCalleeReference", "()Lorg/jetbrains/kotlin/fir/references/FirReference;", "setCalleeReference", "(Lorg/jetbrains/kotlin/fir/references/FirReference;)V", "constructedTypeRef", "Lorg/jetbrains/kotlin/fir/types/FirTypeRef;", "getConstructedTypeRef", "()Lorg/jetbrains/kotlin/fir/types/FirTypeRef;", "setConstructedTypeRef", "(Lorg/jetbrains/kotlin/fir/types/FirTypeRef;)V", "contextReceiverArguments", "Lorg/jetbrains/kotlin/fir/expressions/FirExpression;", "getContextReceiverArguments", "dispatchReceiver", "getDispatchReceiver", "()Lorg/jetbrains/kotlin/fir/expressions/FirExpression;", "setDispatchReceiver", "(Lorg/jetbrains/kotlin/fir/expressions/FirExpression;)V", "<set-?>", "", "isThis", "()Z", "setThis", "(Z)V", "isThis$delegate", "Lkotlin/properties/ReadWriteProperty;", FirebaseAnalytics.Param.SOURCE, "Lorg/jetbrains/kotlin/KtSourceElement;", "getSource", "()Lorg/jetbrains/kotlin/KtSourceElement;", "setSource", "(Lorg/jetbrains/kotlin/KtSourceElement;)V", JsonPOJOBuilder.DEFAULT_BUILD_METHOD, "Lorg/jetbrains/kotlin/fir/expressions/FirDelegatedConstructorCall;", Constants.TYPE_TREE}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirDelegatedConstructorCallBuilder implements FirAnnotationContainerBuilder, FirCallBuilder {
    static final /* synthetic */ KProperty<Object>[] $$delegatedProperties = {Reflection.mutableProperty1(new MutablePropertyReference1Impl(Reflection.getOrCreateKotlinClass(FirDelegatedConstructorCallBuilder.class), "isThis", "isThis()Z"))};
    public FirReference calleeReference;
    public FirTypeRef constructedTypeRef;
    private KtSourceElement source;
    private final List<FirAnnotation> annotations = new ArrayList();
    private FirArgumentList argumentList = FirEmptyArgumentList.INSTANCE;
    private final List<FirExpression> contextReceiverArguments = new ArrayList();
    private FirExpression dispatchReceiver = FirNoReceiverExpression.INSTANCE;

    /* JADX INFO: renamed from: isThis$delegate, reason: from kotlin metadata */
    private final ReadWriteProperty isThis = Delegates.INSTANCE.notNull();

    @Override // org.jetbrains.kotlin.fir.builder.FirAnnotationContainerBuilder
    public KtSourceElement getSource() {
        return this.source;
    }

    @Override // org.jetbrains.kotlin.fir.builder.FirAnnotationContainerBuilder
    public void setSource(KtSourceElement ktSourceElement) {
        this.source = ktSourceElement;
    }

    @Override // org.jetbrains.kotlin.fir.builder.FirAnnotationContainerBuilder
    public List<FirAnnotation> getAnnotations() {
        return this.annotations;
    }

    @Override // org.jetbrains.kotlin.fir.expressions.builder.FirCallBuilder
    public FirArgumentList getArgumentList() {
        return this.argumentList;
    }

    @Override // org.jetbrains.kotlin.fir.expressions.builder.FirCallBuilder
    public void setArgumentList(FirArgumentList firArgumentList) {
        Intrinsics.checkNotNullParameter(firArgumentList, "<set-?>");
        this.argumentList = firArgumentList;
    }

    public final List<FirExpression> getContextReceiverArguments() {
        return this.contextReceiverArguments;
    }

    public final FirTypeRef getConstructedTypeRef() {
        FirTypeRef firTypeRef = this.constructedTypeRef;
        if (firTypeRef != null) {
            return firTypeRef;
        }
        Intrinsics.throwUninitializedPropertyAccessException("constructedTypeRef");
        return null;
    }

    public final void setConstructedTypeRef(FirTypeRef firTypeRef) {
        Intrinsics.checkNotNullParameter(firTypeRef, "<set-?>");
        this.constructedTypeRef = firTypeRef;
    }

    public final FirExpression getDispatchReceiver() {
        return this.dispatchReceiver;
    }

    public final void setDispatchReceiver(FirExpression firExpression) {
        Intrinsics.checkNotNullParameter(firExpression, "<set-?>");
        this.dispatchReceiver = firExpression;
    }

    public final FirReference getCalleeReference() {
        FirReference firReference = this.calleeReference;
        if (firReference != null) {
            return firReference;
        }
        Intrinsics.throwUninitializedPropertyAccessException("calleeReference");
        return null;
    }

    public final void setCalleeReference(FirReference firReference) {
        Intrinsics.checkNotNullParameter(firReference, "<set-?>");
        this.calleeReference = firReference;
    }

    public final boolean isThis() {
        return ((Boolean) this.isThis.getValue(this, $$delegatedProperties[0])).booleanValue();
    }

    public final void setThis(boolean z) {
        this.isThis.setValue(this, $$delegatedProperties[0], Boolean.valueOf(z));
    }

    @Override // org.jetbrains.kotlin.fir.builder.FirAnnotationContainerBuilder
    public FirDelegatedConstructorCall build() {
        return new FirDelegatedConstructorCallImpl(getSource(), FirBuilderDslKt.toMutableOrEmpty(getAnnotations()), getArgumentList(), FirBuilderDslKt.toMutableOrEmpty(this.contextReceiverArguments), getConstructedTypeRef(), this.dispatchReceiver, getCalleeReference(), isThis(), null);
    }
}
