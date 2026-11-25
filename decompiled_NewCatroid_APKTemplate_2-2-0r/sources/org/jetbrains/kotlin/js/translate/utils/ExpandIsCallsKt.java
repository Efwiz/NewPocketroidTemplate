package org.jetbrains.kotlin.js.translate.utils;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.js.backend.ast.JsProgramFragment;

/* JADX INFO: compiled from: expandIsCalls.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\u0012\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u001c\n\u0002\u0018\u0002\n\u0000\u001a\u0014\u0010\u0000\u001a\u00020\u00012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003¨\u0006\u0005"}, d2 = {"expandIsCalls", "", "fragments", "", "Lorg/jetbrains/kotlin/js/backend/ast/JsProgramFragment;", "js.translator"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class ExpandIsCallsKt {
    public static final void expandIsCalls(Iterable<JsProgramFragment> fragments) {
        Intrinsics.checkNotNullParameter(fragments, "fragments");
        TypeCheckRewritingVisitor typeCheckRewritingVisitor = new TypeCheckRewritingVisitor();
        for (JsProgramFragment jsProgramFragment : fragments) {
            typeCheckRewritingVisitor.accept(jsProgramFragment.getDeclarationBlock());
            typeCheckRewritingVisitor.accept(jsProgramFragment.getInitializerBlock());
        }
    }
}
