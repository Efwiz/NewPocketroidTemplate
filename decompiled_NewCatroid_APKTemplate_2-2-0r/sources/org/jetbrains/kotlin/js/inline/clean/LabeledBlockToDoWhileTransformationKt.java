package org.jetbrains.kotlin.js.inline.clean;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.js.backend.ast.JsProgramFragment;

/* JADX INFO: compiled from: LabeledBlockToDoWhileTransformation.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\u0012\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u001c\n\u0002\u0018\u0002\n\u0000\u001a\u0014\u0010\u0000\u001a\u00020\u00012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003¨\u0006\u0005"}, d2 = {"transformLabeledBlockToDoWhile", "", "fragments", "", "Lorg/jetbrains/kotlin/js/backend/ast/JsProgramFragment;", "js.translator"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class LabeledBlockToDoWhileTransformationKt {
    public static final void transformLabeledBlockToDoWhile(Iterable<JsProgramFragment> fragments) {
        Intrinsics.checkNotNullParameter(fragments, "fragments");
        for (JsProgramFragment jsProgramFragment : fragments) {
            LabeledBlockToDoWhileTransformation.INSTANCE.apply(jsProgramFragment.getDeclarationBlock());
            LabeledBlockToDoWhileTransformation.INSTANCE.apply(jsProgramFragment.getInitializerBlock());
        }
    }
}
