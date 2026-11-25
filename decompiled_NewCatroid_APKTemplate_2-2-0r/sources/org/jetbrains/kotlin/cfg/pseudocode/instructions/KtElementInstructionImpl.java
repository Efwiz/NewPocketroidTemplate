package org.jetbrains.kotlin.cfg.pseudocode.instructions;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.Regex;
import org.eclipse.jgit.transport.WalkEncryption;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.psi.KtElement;

/* JADX INFO: compiled from: KtElementInstructionImpl.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0000\b&\u0018\u00002\u00020\u00012\u00020\u0002B\u0015\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0006¢\u0006\u0002\u0010\u0007J\u0010\u0010\n\u001a\u00020\u000b2\u0006\u0010\u0003\u001a\u00020\fH\u0004R\u0014\u0010\u0003\u001a\u00020\u0004X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\t¨\u0006\r"}, d2 = {"Lorg/jetbrains/kotlin/cfg/pseudocode/instructions/KtElementInstructionImpl;", "Lorg/jetbrains/kotlin/cfg/pseudocode/instructions/InstructionImpl;", "Lorg/jetbrains/kotlin/cfg/pseudocode/instructions/KtElementInstruction;", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, "Lorg/jetbrains/kotlin/psi/KtElement;", "blockScope", "Lorg/jetbrains/kotlin/cfg/pseudocode/instructions/BlockScope;", "(Lorg/jetbrains/kotlin/psi/KtElement;Lorg/jetbrains/kotlin/cfg/pseudocode/instructions/BlockScope;)V", "getElement", "()Lorg/jetbrains/kotlin/psi/KtElement;", "render", "", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", "cfg"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class KtElementInstructionImpl extends InstructionImpl implements KtElementInstruction {
    private final KtElement element;

    @Override // org.jetbrains.kotlin.cfg.pseudocode.instructions.KtElementInstruction
    public KtElement getElement() {
        return this.element;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KtElementInstructionImpl(KtElement element, BlockScope blockScope) {
        super(blockScope);
        Intrinsics.checkNotNullParameter(element, "element");
        Intrinsics.checkNotNullParameter(blockScope, "blockScope");
        this.element = element;
    }

    protected final String render(PsiElement element) {
        Intrinsics.checkNotNullParameter(element, "element");
        String text = element.getText();
        if (text != null) {
            String strReplace = new Regex(WalkEncryption.Vals.REGEX_WS).replace(text, " ");
            if (strReplace != null) {
                return strReplace;
            }
        }
        return "";
    }
}
