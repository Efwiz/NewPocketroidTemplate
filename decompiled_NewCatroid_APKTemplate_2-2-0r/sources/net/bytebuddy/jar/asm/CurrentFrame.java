package net.bytebuddy.jar.asm;

/* JADX INFO: loaded from: classes12.dex */
final class CurrentFrame extends Frame {
    CurrentFrame(Label label) {
        super(label);
    }

    @Override // net.bytebuddy.jar.asm.Frame
    void execute(int i, int i2, Symbol symbol, SymbolTable symbolTable) {
        super.execute(i, i2, symbol, symbolTable);
        Frame frame = new Frame(null);
        merge(symbolTable, frame, 0);
        copyFrom(frame);
    }
}
