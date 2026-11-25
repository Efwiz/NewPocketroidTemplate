package org.jetbrains.kotlin.codegen.inline;

import org.jetbrains.org.objectweb.asm.MethodVisitor;
import org.jetbrains.org.objectweb.asm.commons.InstructionAdapter;

/* JADX INFO: loaded from: classes3.dex */
public class InlineMethodInstructionAdapter extends InstructionAdapter {
    @Override // org.jetbrains.org.objectweb.asm.MethodVisitor
    public void visitAnnotableParameterCount(int i, boolean z) {
    }

    InlineMethodInstructionAdapter(MethodVisitor methodVisitor) {
        super(589824, methodVisitor);
    }
}
