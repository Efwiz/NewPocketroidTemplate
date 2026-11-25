package org.jetbrains.kotlin.codegen.inline;

import com.reandroid.dex.sections.Marker;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: LambdaInfo.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b&\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u000e\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/codegen/inline/ExpressionLambda;", "Lorg/jetbrains/kotlin/codegen/inline/LambdaInfo;", "()V", "generateLambdaBody", "", "sourceCompiler", "Lorg/jetbrains/kotlin/codegen/inline/SourceCompilerForInline;", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class ExpressionLambda extends LambdaInfo {
    public final void generateLambdaBody(SourceCompilerForInline sourceCompiler) {
        Intrinsics.checkNotNullParameter(sourceCompiler, "sourceCompiler");
        setNode(sourceCompiler.generateLambdaBody(this, getReifiedTypeParametersUsages()));
        InlineCodegenUtilsKt.preprocessSuspendMarkers(getNode().getNode(), true, false);
    }
}
