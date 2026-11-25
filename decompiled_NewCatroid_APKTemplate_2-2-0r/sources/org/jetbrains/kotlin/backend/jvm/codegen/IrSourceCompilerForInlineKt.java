package org.jetbrains.kotlin.backend.jvm.codegen;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.intellij.psi.PsiKeyword;
import org.jetbrains.kotlin.ir.expressions.IrLoop;

/* JADX INFO: compiled from: IrSourceCompilerForInline.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\u0012\n\u0000\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\u001a\u0014\u0010\u0000\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u0004H\u0000¨\u0006\u0005"}, d2 = {"nonLocalReturnLabel", "", "Lorg/jetbrains/kotlin/ir/expressions/IrLoop;", "forBreak", "", "backend.jvm.codegen"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class IrSourceCompilerForInlineKt {
    public static final String nonLocalReturnLabel(IrLoop irLoop, boolean z) {
        Intrinsics.checkNotNullParameter(irLoop, "<this>");
        StringBuilder sb = new StringBuilder();
        String label = irLoop.getLabel();
        Intrinsics.checkNotNull(label);
        return sb.append(label).append('$').append(z ? PsiKeyword.BREAK : PsiKeyword.CONTINUE).toString();
    }
}
