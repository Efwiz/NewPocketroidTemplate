package org.jetbrains.kotlin.codegen.optimization.common;

import com.reandroid.dex.sections.Marker;
import kotlin.Metadata;
import org.jetbrains.kotlin.codegen.optimization.common.VarFrame;
import org.jetbrains.kotlin.js.translate.context.Namer;

/* JADX INFO: compiled from: backwardAnalysis.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\bf\u0018\u0000*\u000e\b\u0000\u0010\u0001*\b\u0012\u0004\u0012\u0002H\u00010\u00002\u00020\u0002J\u0013\u0010\u0003\u001a\u00020\u00042\b\u0010\u0005\u001a\u0004\u0018\u00010\u0002H¦\u0002J\b\u0010\u0006\u001a\u00020\u0007H&J\b\u0010\b\u001a\u00020\tH&J\u0015\u0010\n\u001a\u00020\t2\u0006\u0010\u0005\u001a\u00028\u0000H&¢\u0006\u0002\u0010\u000bø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\fÀ\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/codegen/optimization/common/VarFrame;", "F", "", Namer.EQUALS_METHOD_NAME, "", "other", "hashCode", "", "markControlFlowMerge", "", "mergeFrom", "(Lorg/jetbrains/kotlin/codegen/optimization/common/VarFrame;)V", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface VarFrame<F extends VarFrame<F>> {
    boolean equals(Object other);

    int hashCode();

    void markControlFlowMerge();

    void mergeFrom(F other);
}
