package org.jetbrains.kotlin.fir.expressions.builder;

import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.ArrayList;
import java.util.List;
import kotlin.Deprecated;
import kotlin.DeprecationLevel;
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
import org.jetbrains.kotlin.fir.expressions.FirAnnotation;
import org.jetbrains.kotlin.fir.expressions.FirDelegatedConstructorCall;
import org.jetbrains.kotlin.fir.expressions.impl.FirLazyDelegatedConstructorCall;
import org.jetbrains.kotlin.fir.references.FirReference;
import org.jetbrains.kotlin.fir.types.FirTypeRef;

/* JADX INFO: compiled from: FirLazyDelegatedConstructorCallBuilder.kt */
/* JADX INFO: loaded from: classes4.dex */
@FirBuilderDsl
@Metadata(d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\b\u0010%\u001a\u00020&H\u0016R\"\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u00048\u0016X\u0097\u0004¢\u0006\u000e\n\u0000\u0012\u0004\b\u0006\u0010\u0002\u001a\u0004\b\u0007\u0010\bR\u001a\u0010\t\u001a\u00020\nX\u0086.¢\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000eR\u001a\u0010\u000f\u001a\u00020\u0010X\u0086.¢\u0006\u000e\n\u0000\u001a\u0004\b\u0011\u0010\u0012\"\u0004\b\u0013\u0010\u0014R+\u0010\u0017\u001a\u00020\u00162\u0006\u0010\u0015\u001a\u00020\u00168F@FX\u0086\u008e\u0002¢\u0006\u0012\n\u0004\b\u001b\u0010\u001c\u001a\u0004\b\u0017\u0010\u0018\"\u0004\b\u0019\u0010\u001aR.\u0010\u001f\u001a\u0004\u0018\u00010\u001e2\b\u0010\u001d\u001a\u0004\u0018\u00010\u001e8V@VX\u0097\u000e¢\u0006\u0012\u0012\u0004\b \u0010\u0002\u001a\u0004\b!\u0010\"\"\u0004\b#\u0010$¨\u0006'"}, d2 = {"Lorg/jetbrains/kotlin/fir/expressions/builder/FirLazyDelegatedConstructorCallBuilder;", "Lorg/jetbrains/kotlin/fir/builder/FirAnnotationContainerBuilder;", "()V", "annotations", "", "Lorg/jetbrains/kotlin/fir/expressions/FirAnnotation;", "getAnnotations$annotations", "getAnnotations", "()Ljava/util/List;", "calleeReference", "Lorg/jetbrains/kotlin/fir/references/FirReference;", "getCalleeReference", "()Lorg/jetbrains/kotlin/fir/references/FirReference;", "setCalleeReference", "(Lorg/jetbrains/kotlin/fir/references/FirReference;)V", "constructedTypeRef", "Lorg/jetbrains/kotlin/fir/types/FirTypeRef;", "getConstructedTypeRef", "()Lorg/jetbrains/kotlin/fir/types/FirTypeRef;", "setConstructedTypeRef", "(Lorg/jetbrains/kotlin/fir/types/FirTypeRef;)V", "<set-?>", "", "isThis", "()Z", "setThis", "(Z)V", "isThis$delegate", "Lkotlin/properties/ReadWriteProperty;", "<anonymous parameter 0>", "Lorg/jetbrains/kotlin/KtSourceElement;", FirebaseAnalytics.Param.SOURCE, "getSource$annotations", "getSource", "()Lorg/jetbrains/kotlin/KtSourceElement;", "setSource", "(Lorg/jetbrains/kotlin/KtSourceElement;)V", JsonPOJOBuilder.DEFAULT_BUILD_METHOD, "Lorg/jetbrains/kotlin/fir/expressions/FirDelegatedConstructorCall;", Constants.TYPE_TREE}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirLazyDelegatedConstructorCallBuilder implements FirAnnotationContainerBuilder {
    static final /* synthetic */ KProperty<Object>[] $$delegatedProperties = {Reflection.mutableProperty1(new MutablePropertyReference1Impl(Reflection.getOrCreateKotlinClass(FirLazyDelegatedConstructorCallBuilder.class), "isThis", "isThis()Z"))};
    public FirReference calleeReference;
    public FirTypeRef constructedTypeRef;

    /* JADX INFO: renamed from: isThis$delegate, reason: from kotlin metadata */
    private final ReadWriteProperty isThis = Delegates.INSTANCE.notNull();
    private final List<FirAnnotation> annotations = new ArrayList();

    @Deprecated(level = DeprecationLevel.HIDDEN, message = "Modification of 'annotations' has no impact for FirLazyDelegatedConstructorCallBuilder")
    public static /* synthetic */ void getAnnotations$annotations() {
    }

    @Deprecated(level = DeprecationLevel.HIDDEN, message = "Modification of 'source' has no impact for FirLazyDelegatedConstructorCallBuilder")
    public static /* synthetic */ void getSource$annotations() {
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
        return new FirLazyDelegatedConstructorCall(getConstructedTypeRef(), getCalleeReference(), isThis());
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
