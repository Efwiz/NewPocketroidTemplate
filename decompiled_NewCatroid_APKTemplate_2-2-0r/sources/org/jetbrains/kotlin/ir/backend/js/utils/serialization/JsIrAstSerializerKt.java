package org.jetbrains.kotlin.ir.backend.js.utils.serialization;

import java.io.OutputStream;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.ir.backend.js.transformers.irToJs.JsIrProgramFragment;

/* JADX INFO: compiled from: JsIrAstSerializer.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\u0016\n\u0000\n\u0002\u0010\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a\u0018\u0010\u0000\u001a\u00020\u0001*\b\u0012\u0004\u0012\u00020\u00030\u00022\u0006\u0010\u0004\u001a\u00020\u0005¨\u0006\u0006"}, d2 = {"serializeTo", "", "", "Lorg/jetbrains/kotlin/ir/backend/js/transformers/irToJs/JsIrProgramFragment;", "output", "Ljava/io/OutputStream;", "backend.js"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class JsIrAstSerializerKt {
    public static final void serializeTo(List<JsIrProgramFragment> list, OutputStream output) {
        Intrinsics.checkNotNullParameter(list, "<this>");
        Intrinsics.checkNotNullParameter(output, "output");
        new JsIrAstSerializer().appendAll(list).saveTo(output);
    }
}
