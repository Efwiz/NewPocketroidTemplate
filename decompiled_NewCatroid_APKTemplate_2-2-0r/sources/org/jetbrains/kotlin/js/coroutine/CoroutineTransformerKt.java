package org.jetbrains.kotlin.js.coroutine;

import java.util.Iterator;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.js.backend.ast.JsProgramFragment;
import org.jetbrains.kotlin.js.inline.ImportIntoFragmentInliningScope;

/* JADX INFO: compiled from: CoroutineTransformer.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\u0012\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u001c\n\u0002\u0018\u0002\n\u0000\u001a\u0014\u0010\u0000\u001a\u00020\u00012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003¨\u0006\u0005"}, d2 = {"transformCoroutines", "", "fragments", "", "Lorg/jetbrains/kotlin/js/backend/ast/JsProgramFragment;", "js.translator"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class CoroutineTransformerKt {
    public static final void transformCoroutines(Iterable<JsProgramFragment> fragments) {
        Intrinsics.checkNotNullParameter(fragments, "fragments");
        final CoroutineTransformer coroutineTransformer = new CoroutineTransformer();
        Iterator<JsProgramFragment> it = fragments.iterator();
        while (it.hasNext()) {
            ImportIntoFragmentInliningScope.INSTANCE.process(it.next(), new Function1<ImportIntoFragmentInliningScope, Unit>() { // from class: org.jetbrains.kotlin.js.coroutine.CoroutineTransformerKt.transformCoroutines.1
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public /* bridge */ /* synthetic */ Unit invoke(ImportIntoFragmentInliningScope importIntoFragmentInliningScope) {
                    invoke2(importIntoFragmentInliningScope);
                    return Unit.INSTANCE;
                }

                /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                public final void invoke2(ImportIntoFragmentInliningScope scope) {
                    Intrinsics.checkNotNullParameter(scope, "scope");
                    coroutineTransformer.accept(scope.getAllCode());
                }
            });
        }
    }
}
