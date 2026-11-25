package antlr.debug;

import antlr.CharStreamException;
import antlr.InputBuffer;
import io.netty.util.internal.StringUtil;
import java.util.Vector;

/* JADX INFO: loaded from: classes.dex */
public class DebuggingInputBuffer extends InputBuffer {
    private InputBuffer buffer;
    private boolean debugMode = true;
    private InputBufferEventSupport inputBufferEventSupport = new InputBufferEventSupport(this);

    public DebuggingInputBuffer(InputBuffer inputBuffer) {
        this.buffer = inputBuffer;
    }

    public void addInputBufferListener(InputBufferListener inputBufferListener) {
        this.inputBufferEventSupport.addInputBufferListener(inputBufferListener);
    }

    @Override // antlr.InputBuffer
    public void consume() {
        char cLA;
        try {
            cLA = this.buffer.LA(1);
        } catch (CharStreamException unused) {
            cLA = StringUtil.SPACE;
        }
        this.buffer.consume();
        if (this.debugMode) {
            this.inputBufferEventSupport.fireConsume(cLA);
        }
    }

    @Override // antlr.InputBuffer
    public void fill(int i) throws CharStreamException {
        this.buffer.fill(i);
    }

    public Vector getInputBufferListeners() {
        return this.inputBufferEventSupport.getInputBufferListeners();
    }

    public boolean isDebugMode() {
        return this.debugMode;
    }

    @Override // antlr.InputBuffer
    public boolean isMarked() {
        return this.buffer.isMarked();
    }

    @Override // antlr.InputBuffer
    public char LA(int i) throws CharStreamException {
        char cLA = this.buffer.LA(i);
        if (this.debugMode) {
            this.inputBufferEventSupport.fireLA(cLA, i);
        }
        return cLA;
    }

    @Override // antlr.InputBuffer
    public int mark() {
        int iMark = this.buffer.mark();
        this.inputBufferEventSupport.fireMark(iMark);
        return iMark;
    }

    public void removeInputBufferListener(InputBufferListener inputBufferListener) {
        InputBufferEventSupport inputBufferEventSupport = this.inputBufferEventSupport;
        if (inputBufferEventSupport != null) {
            inputBufferEventSupport.removeInputBufferListener(inputBufferListener);
        }
    }

    @Override // antlr.InputBuffer
    public void rewind(int i) {
        this.buffer.rewind(i);
        this.inputBufferEventSupport.fireRewind(i);
    }

    public void setDebugMode(boolean z) {
        this.debugMode = z;
    }
}
