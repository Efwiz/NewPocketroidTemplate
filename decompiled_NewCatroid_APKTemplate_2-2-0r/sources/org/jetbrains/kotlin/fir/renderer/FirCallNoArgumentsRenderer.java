package org.jetbrains.kotlin.fir.renderer;

import com.huawei.hms.ml.language.common.utils.Constant;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.Constants;
import org.jetbrains.kotlin.fir.expressions.FirAnnotationArgumentMapping;
import org.jetbrains.kotlin.fir.expressions.FirExpression;

/* JADX INFO: compiled from: FirCallNoArgumentsRenderer.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0016J\u0016\u0010\u0007\u001a\u00020\u00042\f\u0010\b\u001a\b\u0012\u0004\u0012\u00020\n0\tH\u0016¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/fir/renderer/FirCallNoArgumentsRenderer;", "Lorg/jetbrains/kotlin/fir/renderer/FirCallArgumentsRenderer;", "()V", "renderArgumentMapping", "", "argumentMapping", "Lorg/jetbrains/kotlin/fir/expressions/FirAnnotationArgumentMapping;", "renderArguments", "arguments", "", "Lorg/jetbrains/kotlin/fir/expressions/FirExpression;", Constants.TYPE_TREE}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirCallNoArgumentsRenderer extends FirCallArgumentsRenderer {
    @Override // org.jetbrains.kotlin.fir.renderer.FirCallArgumentsRenderer
    public void renderArgumentMapping(FirAnnotationArgumentMapping argumentMapping) {
        Intrinsics.checkNotNullParameter(argumentMapping, "argumentMapping");
        getPrinter().print("(");
        if (!argumentMapping.getMapping().isEmpty()) {
            getPrinter().print("...");
        }
        getPrinter().print(Constant.AFTER_QUTO);
    }

    @Override // org.jetbrains.kotlin.fir.renderer.FirCallArgumentsRenderer
    public void renderArguments(List<? extends FirExpression> arguments) {
        Intrinsics.checkNotNullParameter(arguments, "arguments");
        getPrinter().print("(");
        if (!arguments.isEmpty()) {
            getPrinter().print("...");
        }
        getPrinter().print(Constant.AFTER_QUTO);
    }
}
