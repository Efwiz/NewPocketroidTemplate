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
import org.jetbrains.kotlin.fir.builder.FirBuilderDslKt;
import org.jetbrains.kotlin.fir.expressions.FirAnnotation;
import org.jetbrains.kotlin.fir.expressions.FirExpression;
import org.jetbrains.kotlin.fir.expressions.FirIncrementDecrementExpression;
import org.jetbrains.kotlin.fir.expressions.impl.FirIncrementDecrementExpressionImpl;
import org.jetbrains.kotlin.fir.types.FirTypeRef;
import org.jetbrains.kotlin.name.Name;

/* JADX INFO: compiled from: FirIncrementDecrementExpressionBuilder.kt */
/* JADX INFO: loaded from: classes4.dex */
@FirBuilderDsl
@Metadata(d1 = {"\u0000J\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0000\b\u0007\u0018\u00002\u00020\u00012\u00020\u0002B\u0005¢\u0006\u0002\u0010\u0003J\b\u0010.\u001a\u00020/H\u0016R\u001a\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u001a\u0010\t\u001a\u00020\nX\u0086.¢\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000eR+\u0010\u0011\u001a\u00020\u00102\u0006\u0010\u000f\u001a\u00020\u00108F@FX\u0086\u008e\u0002¢\u0006\u0012\n\u0004\b\u0015\u0010\u0016\u001a\u0004\b\u0011\u0010\u0012\"\u0004\b\u0013\u0010\u0014R\u001a\u0010\u0017\u001a\u00020\u0018X\u0086.¢\u0006\u000e\n\u0000\u001a\u0004\b\u0019\u0010\u001a\"\u0004\b\u001b\u0010\u001cR\u001c\u0010\u001d\u001a\u0004\u0018\u00010\u001eX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001f\u0010 \"\u0004\b!\u0010\"R\u001c\u0010#\u001a\u0004\u0018\u00010\u001eX\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b$\u0010 \"\u0004\b%\u0010\"R*\u0010(\u001a\u00020'2\u0006\u0010&\u001a\u00020'8V@VX\u0097\u000e¢\u0006\u0012\u0012\u0004\b)\u0010\u0003\u001a\u0004\b*\u0010+\"\u0004\b,\u0010-¨\u00060"}, d2 = {"Lorg/jetbrains/kotlin/fir/expressions/builder/FirIncrementDecrementExpressionBuilder;", "Lorg/jetbrains/kotlin/fir/builder/FirAnnotationContainerBuilder;", "Lorg/jetbrains/kotlin/fir/expressions/builder/FirExpressionBuilder;", "()V", "annotations", "", "Lorg/jetbrains/kotlin/fir/expressions/FirAnnotation;", "getAnnotations", "()Ljava/util/List;", "expression", "Lorg/jetbrains/kotlin/fir/expressions/FirExpression;", "getExpression", "()Lorg/jetbrains/kotlin/fir/expressions/FirExpression;", "setExpression", "(Lorg/jetbrains/kotlin/fir/expressions/FirExpression;)V", "<set-?>", "", "isPrefix", "()Z", "setPrefix", "(Z)V", "isPrefix$delegate", "Lkotlin/properties/ReadWriteProperty;", "operationName", "Lorg/jetbrains/kotlin/name/Name;", "getOperationName", "()Lorg/jetbrains/kotlin/name/Name;", "setOperationName", "(Lorg/jetbrains/kotlin/name/Name;)V", "operationSource", "Lorg/jetbrains/kotlin/KtSourceElement;", "getOperationSource", "()Lorg/jetbrains/kotlin/KtSourceElement;", "setOperationSource", "(Lorg/jetbrains/kotlin/KtSourceElement;)V", FirebaseAnalytics.Param.SOURCE, "getSource", "setSource", "<anonymous parameter 0>", "Lorg/jetbrains/kotlin/fir/types/FirTypeRef;", "typeRef", "getTypeRef$annotations", "getTypeRef", "()Lorg/jetbrains/kotlin/fir/types/FirTypeRef;", "setTypeRef", "(Lorg/jetbrains/kotlin/fir/types/FirTypeRef;)V", JsonPOJOBuilder.DEFAULT_BUILD_METHOD, "Lorg/jetbrains/kotlin/fir/expressions/FirIncrementDecrementExpression;", Constants.TYPE_TREE}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirIncrementDecrementExpressionBuilder implements FirAnnotationContainerBuilder, FirExpressionBuilder {
    static final /* synthetic */ KProperty<Object>[] $$delegatedProperties = {Reflection.mutableProperty1(new MutablePropertyReference1Impl(Reflection.getOrCreateKotlinClass(FirIncrementDecrementExpressionBuilder.class), "isPrefix", "isPrefix()Z"))};
    public FirExpression expression;
    public Name operationName;
    private KtSourceElement operationSource;
    private KtSourceElement source;
    private final List<FirAnnotation> annotations = new ArrayList();

    /* JADX INFO: renamed from: isPrefix$delegate, reason: from kotlin metadata */
    private final ReadWriteProperty isPrefix = Delegates.INSTANCE.notNull();

    @Deprecated(level = DeprecationLevel.HIDDEN, message = "Modification of 'typeRef' has no impact for FirIncrementDecrementExpressionBuilder")
    public static /* synthetic */ void getTypeRef$annotations() {
    }

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

    public final boolean isPrefix() {
        return ((Boolean) this.isPrefix.getValue(this, $$delegatedProperties[0])).booleanValue();
    }

    public final void setPrefix(boolean z) {
        this.isPrefix.setValue(this, $$delegatedProperties[0], Boolean.valueOf(z));
    }

    public final Name getOperationName() {
        Name name2 = this.operationName;
        if (name2 != null) {
            return name2;
        }
        Intrinsics.throwUninitializedPropertyAccessException("operationName");
        return null;
    }

    public final void setOperationName(Name name2) {
        Intrinsics.checkNotNullParameter(name2, "<set-?>");
        this.operationName = name2;
    }

    public final FirExpression getExpression() {
        FirExpression firExpression = this.expression;
        if (firExpression != null) {
            return firExpression;
        }
        Intrinsics.throwUninitializedPropertyAccessException("expression");
        return null;
    }

    public final void setExpression(FirExpression firExpression) {
        Intrinsics.checkNotNullParameter(firExpression, "<set-?>");
        this.expression = firExpression;
    }

    public final KtSourceElement getOperationSource() {
        return this.operationSource;
    }

    public final void setOperationSource(KtSourceElement ktSourceElement) {
        this.operationSource = ktSourceElement;
    }

    @Override // org.jetbrains.kotlin.fir.builder.FirAnnotationContainerBuilder
    public FirIncrementDecrementExpression build() {
        return new FirIncrementDecrementExpressionImpl(getSource(), FirBuilderDslKt.toMutableOrEmpty(getAnnotations()), isPrefix(), getOperationName(), getExpression(), this.operationSource, null);
    }

    @Override // org.jetbrains.kotlin.fir.expressions.builder.FirExpressionBuilder
    public /* synthetic */ FirTypeRef getTypeRef() {
        throw new IllegalStateException();
    }

    @Override // org.jetbrains.kotlin.fir.expressions.builder.FirExpressionBuilder
    public /* synthetic */ void setTypeRef(FirTypeRef firTypeRef) {
        Intrinsics.checkNotNullParameter(firTypeRef, "<anonymous parameter 0>");
        throw new IllegalStateException();
    }
}
