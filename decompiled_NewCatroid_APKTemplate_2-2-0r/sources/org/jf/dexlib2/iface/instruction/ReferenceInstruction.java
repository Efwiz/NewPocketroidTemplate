package org.jf.dexlib2.iface.instruction;

import org.jf.dexlib2.iface.reference.Reference;

/* JADX INFO: loaded from: classes6.dex */
public interface ReferenceInstruction extends Instruction {
    Reference getReference();

    int getReferenceType();
}
