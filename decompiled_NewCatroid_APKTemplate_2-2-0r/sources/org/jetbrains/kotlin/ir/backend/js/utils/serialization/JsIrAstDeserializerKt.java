package org.jetbrains.kotlin.ir.backend.js.utils.serialization;

import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.ir.backend.js.transformers.irToJs.JsIrProgramFragment;

/* JADX INFO: compiled from: JsIrAstDeserializer.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\u0012\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0012\n\u0000\u001a\u0014\u0010\u0000\u001a\b\u0012\u0004\u0012\u00020\u00020\u00012\u0006\u0010\u0003\u001a\u00020\u0004¨\u0006\u0005"}, d2 = {"deserializeJsIrProgramFragment", "", "Lorg/jetbrains/kotlin/ir/backend/js/transformers/irToJs/JsIrProgramFragment;", "input", "", "backend.js"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class JsIrAstDeserializerKt {
    public static final List<JsIrProgramFragment> deserializeJsIrProgramFragment(byte[] input) {
        Intrinsics.checkNotNullParameter(input, "input");
        return new JsIrAstDeserializer(input).readFragments();
    }
}
