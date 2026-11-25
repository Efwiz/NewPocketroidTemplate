package org.luaj.vm2.compiler;

/* JADX INFO: loaded from: classes6.dex */
class InstructionPtr {
    final int[] code;
    final int idx;

    InstructionPtr(int[] iArr, int i) {
        this.code = iArr;
        this.idx = i;
    }

    int get() {
        return this.code[this.idx];
    }

    void set(int i) {
        this.code[this.idx] = i;
    }
}
