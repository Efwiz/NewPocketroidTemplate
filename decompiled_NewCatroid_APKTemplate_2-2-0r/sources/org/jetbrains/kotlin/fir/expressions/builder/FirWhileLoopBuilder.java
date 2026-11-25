package org.jetbrains.kotlin.fir.expressions.builder;

import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.ArrayList;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.Constants;
import org.jetbrains.kotlin.KtSourceElement;
import org.jetbrains.kotlin.codegen.coroutines.CoroutineCodegenUtilKt;
import org.jetbrains.kotlin.fir.FirLabel;
import org.jetbrains.kotlin.fir.builder.FirAnnotationContainerBuilder;
import org.jetbrains.kotlin.fir.builder.FirBuilderDsl;
import org.jetbrains.kotlin.fir.builder.FirBuilderDslKt;
import org.jetbrains.kotlin.fir.expressions.FirAnnotation;
import org.jetbrains.kotlin.fir.expressions.FirBlock;
import org.jetbrains.kotlin.fir.expressions.FirExpression;
import org.jetbrains.kotlin.fir.expressions.FirWhileLoop;
import org.jetbrains.kotlin.fir.expressions.impl.FirWhileLoopImpl;

/* JADX INFO: compiled from: FirWhileLoopBuilder.kt */
/* JADX INFO: loaded from: classes4.dex */
@FirBuilderDsl
@Metadata(d1 = {"\u0000B\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\b\u0007\u0018\u00002\u00020\u00012\u00020\u0002B\u0005¢\u0006\u0002\u0010\u0003J\b\u0010!\u001a\u00020\"H\u0016R\u001a\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u001a\u0010\t\u001a\u00020\nX\u0096.¢\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000eR\u001a\u0010\u000f\u001a\u00020\u0010X\u0096.¢\u0006\u000e\n\u0000\u001a\u0004\b\u0011\u0010\u0012\"\u0004\b\u0013\u0010\u0014R\u001c\u0010\u0015\u001a\u0004\u0018\u00010\u0016X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0017\u0010\u0018\"\u0004\b\u0019\u0010\u001aR\u001c\u0010\u001b\u001a\u0004\u0018\u00010\u001cX\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001d\u0010\u001e\"\u0004\b\u001f\u0010 ¨\u0006#"}, d2 = {"Lorg/jetbrains/kotlin/fir/expressions/builder/FirWhileLoopBuilder;", "Lorg/jetbrains/kotlin/fir/expressions/builder/FirLoopBuilder;", "Lorg/jetbrains/kotlin/fir/builder/FirAnnotationContainerBuilder;", "()V", "annotations", "", "Lorg/jetbrains/kotlin/fir/expressions/FirAnnotation;", "getAnnotations", "()Ljava/util/List;", "block", "Lorg/jetbrains/kotlin/fir/expressions/FirBlock;", "getBlock", "()Lorg/jetbrains/kotlin/fir/expressions/FirBlock;", "setBlock", "(Lorg/jetbrains/kotlin/fir/expressions/FirBlock;)V", "condition", "Lorg/jetbrains/kotlin/fir/expressions/FirExpression;", "getCondition", "()Lorg/jetbrains/kotlin/fir/expressions/FirExpression;", "setCondition", "(Lorg/jetbrains/kotlin/fir/expressions/FirExpression;)V", CoroutineCodegenUtilKt.COROUTINE_LABEL_FIELD_NAME, "Lorg/jetbrains/kotlin/fir/FirLabel;", "getLabel", "()Lorg/jetbrains/kotlin/fir/FirLabel;", "setLabel", "(Lorg/jetbrains/kotlin/fir/FirLabel;)V", FirebaseAnalytics.Param.SOURCE, "Lorg/jetbrains/kotlin/KtSourceElement;", "getSource", "()Lorg/jetbrains/kotlin/KtSourceElement;", "setSource", "(Lorg/jetbrains/kotlin/KtSourceElement;)V", JsonPOJOBuilder.DEFAULT_BUILD_METHOD, "Lorg/jetbrains/kotlin/fir/expressions/FirWhileLoop;", Constants.TYPE_TREE}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirWhileLoopBuilder implements FirAnnotationContainerBuilder, FirLoopBuilder {
    private final List<FirAnnotation> annotations = new ArrayList();
    public FirBlock block;
    public FirExpression condition;
    private FirLabel label;
    private KtSourceElement source;

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

    @Override // org.jetbrains.kotlin.fir.expressions.builder.FirLoopBuilder
    public FirLabel getLabel() {
        return this.label;
    }

    @Override // org.jetbrains.kotlin.fir.expressions.builder.FirLoopBuilder
    public void setLabel(FirLabel firLabel) {
        this.label = firLabel;
    }

    @Override // org.jetbrains.kotlin.fir.expressions.builder.FirLoopBuilder
    public FirExpression getCondition() {
        FirExpression firExpression = this.condition;
        if (firExpression != null) {
            return firExpression;
        }
        Intrinsics.throwUninitializedPropertyAccessException("condition");
        return null;
    }

    @Override // org.jetbrains.kotlin.fir.expressions.builder.FirLoopBuilder
    public void setCondition(FirExpression firExpression) {
        Intrinsics.checkNotNullParameter(firExpression, "<set-?>");
        this.condition = firExpression;
    }

    @Override // org.jetbrains.kotlin.fir.expressions.builder.FirLoopBuilder
    public FirBlock getBlock() {
        FirBlock firBlock = this.block;
        if (firBlock != null) {
            return firBlock;
        }
        Intrinsics.throwUninitializedPropertyAccessException("block");
        return null;
    }

    @Override // org.jetbrains.kotlin.fir.expressions.builder.FirLoopBuilder
    public void setBlock(FirBlock firBlock) {
        Intrinsics.checkNotNullParameter(firBlock, "<set-?>");
        this.block = firBlock;
    }

    @Override // org.jetbrains.kotlin.fir.builder.FirAnnotationContainerBuilder
    public FirWhileLoop build() {
        return new FirWhileLoopImpl(getSource(), FirBuilderDslKt.toMutableOrEmpty(getAnnotations()), getLabel(), getCondition(), getBlock(), null);
    }
}
