package org.jetbrains.kotlin.codegen.optimization;

import com.reandroid.dex.sections.Marker;
import kotlin.Metadata;
import org.jetbrains.kotlin.codegen.optimization.fixStack.FixStackMethodTransformer;
import org.jetbrains.kotlin.codegen.optimization.transformer.CompositeMethodTransformer;

/* JADX INFO: compiled from: FixStackWithLabelNormalizationMethodTransformer.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/codegen/optimization/FixStackWithLabelNormalizationMethodTransformer;", "Lorg/jetbrains/kotlin/codegen/optimization/transformer/CompositeMethodTransformer;", "()V", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FixStackWithLabelNormalizationMethodTransformer extends CompositeMethodTransformer {
    public FixStackWithLabelNormalizationMethodTransformer() {
        super(new LabelNormalizationMethodTransformer(), new FixStackMethodTransformer());
    }
}
