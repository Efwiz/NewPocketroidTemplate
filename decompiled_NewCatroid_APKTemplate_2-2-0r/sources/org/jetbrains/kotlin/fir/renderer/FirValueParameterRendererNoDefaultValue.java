package org.jetbrains.kotlin.fir.renderer;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.Constants;
import org.jetbrains.kotlin.fir.declarations.FirValueParameter;

/* JADX INFO: compiled from: FirValueParameterRendererNoDefaultValue.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0014¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/fir/renderer/FirValueParameterRendererNoDefaultValue;", "Lorg/jetbrains/kotlin/fir/renderer/FirValueParameterRenderer;", "()V", "renderDefaultValue", "", "valueParameter", "Lorg/jetbrains/kotlin/fir/declarations/FirValueParameter;", Constants.TYPE_TREE}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirValueParameterRendererNoDefaultValue extends FirValueParameterRenderer {
    @Override // org.jetbrains.kotlin.fir.renderer.FirValueParameterRenderer
    protected void renderDefaultValue(FirValueParameter valueParameter) {
        Intrinsics.checkNotNullParameter(valueParameter, "valueParameter");
        if (valueParameter.getDefaultValue() != null) {
            getPrinter().print(" = ...");
        }
    }
}
