package org.jetbrains.kotlin.diagnostics;

import kotlin.Metadata;
import org.jetbrains.kotlin.load.java.JvmAnnotationNames;

/* JADX INFO: compiled from: DiagnosticMarker.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u0010\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\bf\u0018\u0000*\u0004\b\u0000\u0010\u0001*\u0004\b\u0001\u0010\u00022\u00020\u0003R\u0012\u0010\u0004\u001a\u00028\u0000X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0005\u0010\u0006R\u0012\u0010\u0007\u001a\u00028\u0001X¦\u0004¢\u0006\u0006\u001a\u0004\b\b\u0010\u0006ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\tÀ\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/diagnostics/DiagnosticWithParameters2Marker;", "A", "B", "Lorg/jetbrains/kotlin/diagnostics/DiagnosticMarker;", "a", "getA", "()Ljava/lang/Object;", JvmAnnotationNames.SERIALIZED_IR_BYTES_FIELD_NAME, "getB", "frontend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface DiagnosticWithParameters2Marker<A, B> extends DiagnosticMarker {
    A getA();

    B getB();
}
