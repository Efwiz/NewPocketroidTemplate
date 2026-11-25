package ar.com.hjg.pngj;

import java.util.zip.DataFormatException;
import java.util.zip.Inflater;

/* JADX INFO: loaded from: classes.dex */
public class DeflatedChunksSet {
    private boolean callbackMode;
    public final String chunkid;
    private DeflatedChunkReader curChunk;
    private Inflater inf;
    private final boolean infOwn;
    private long nBytesIn;
    private long nBytesOut;
    protected byte[] row;
    private int rowfilled;
    private int rowlen;
    private int rown;
    State state;

    public boolean allowOtherChunksInBetween(String str) {
        return false;
    }

    protected void preProcessRow() {
    }

    protected void processDoneCallback() {
    }

    private enum State {
        WAITING_FOR_INPUT,
        ROW_READY,
        WORK_DONE,
        TERMINATED;

        public boolean isDone() {
            return this == WORK_DONE || this == TERMINATED;
        }

        public boolean isTerminated() {
            return this == TERMINATED;
        }
    }

    public DeflatedChunksSet(String str, int i, int i2, Inflater inflater, byte[] bArr) {
        this.state = State.WAITING_FOR_INPUT;
        this.callbackMode = true;
        this.nBytesIn = 0L;
        this.nBytesOut = 0L;
        this.chunkid = str;
        this.rowlen = i;
        if (i < 1 || i2 < i) {
            throw new PngjException("bad inital row len " + i);
        }
        if (inflater != null) {
            this.inf = inflater;
            this.infOwn = false;
        } else {
            this.inf = new Inflater();
            this.infOwn = true;
        }
        this.row = (bArr == null || bArr.length < i) ? new byte[i2] : bArr;
        this.rown = -1;
        this.state = State.WAITING_FOR_INPUT;
        try {
            prepareForNextRow(i);
        } catch (RuntimeException e) {
            close();
            throw e;
        }
    }

    public DeflatedChunksSet(String str, int i, int i2) {
        this(str, i, i2, null, null);
    }

    protected void appendNewChunk(DeflatedChunkReader deflatedChunkReader) {
        if (!this.chunkid.equals(deflatedChunkReader.getChunkRaw().f61id)) {
            throw new PngjInputException("Bad chunk inside IdatSet, id:" + deflatedChunkReader.getChunkRaw().f61id + ", expected:" + this.chunkid);
        }
        this.curChunk = deflatedChunkReader;
    }

    protected void processBytes(byte[] bArr, int i, int i2) {
        this.nBytesIn += (long) i2;
        if (i2 < 1 || this.state.isDone()) {
            return;
        }
        if (this.state == State.ROW_READY) {
            throw new PngjInputException("this should only be called if waitingForMoreInput");
        }
        if (this.inf.needsDictionary() || !this.inf.needsInput()) {
            throw new RuntimeException("should not happen");
        }
        this.inf.setInput(bArr, i, i2);
        if (isCallbackMode()) {
            while (inflateData()) {
                prepareForNextRow(processRowCallback());
                if (isDone()) {
                    processDoneCallback();
                }
            }
            return;
        }
        inflateData();
    }

    private boolean inflateData() {
        State state;
        try {
            if (this.state == State.ROW_READY) {
                throw new PngjException("invalid state");
            }
            if (this.state.isDone()) {
                return false;
            }
            byte[] bArr = this.row;
            if (bArr == null || bArr.length < this.rowlen) {
                this.row = new byte[this.rowlen];
            }
            if (this.rowfilled < this.rowlen && !this.inf.finished()) {
                try {
                    Inflater inflater = this.inf;
                    byte[] bArr2 = this.row;
                    int i = this.rowfilled;
                    int iInflate = inflater.inflate(bArr2, i, this.rowlen - i);
                    this.rowfilled += iInflate;
                    this.nBytesOut += (long) iInflate;
                } catch (DataFormatException e) {
                    throw new PngjInputException("error decompressing zlib stream ", e);
                }
            }
            if (this.rowfilled == this.rowlen) {
                state = State.ROW_READY;
            } else if (!this.inf.finished()) {
                state = State.WAITING_FOR_INPUT;
            } else if (this.rowfilled > 0) {
                state = State.ROW_READY;
            } else {
                state = State.WORK_DONE;
            }
            this.state = state;
            if (state != State.ROW_READY) {
                return false;
            }
            preProcessRow();
            return true;
        } catch (RuntimeException e2) {
            close();
            throw e2;
        }
    }

    protected int processRowCallback() {
        throw new PngjInputException("not implemented");
    }

    public byte[] getInflatedRow() {
        return this.row;
    }

    public void prepareForNextRow(int i) {
        this.rowfilled = 0;
        this.rown++;
        if (i < 1) {
            this.rowlen = 0;
            done();
        } else {
            if (this.inf.finished()) {
                this.rowlen = 0;
                done();
                return;
            }
            this.state = State.WAITING_FOR_INPUT;
            this.rowlen = i;
            if (this.callbackMode) {
                return;
            }
            inflateData();
        }
    }

    public boolean isWaitingForMoreInput() {
        return this.state == State.WAITING_FOR_INPUT;
    }

    public boolean isRowReady() {
        return this.state == State.ROW_READY;
    }

    public boolean isDone() {
        return this.state.isDone();
    }

    public boolean isTerminated() {
        return this.state.isTerminated();
    }

    public boolean ackNextChunkId(String str) {
        if (this.state.isTerminated()) {
            return false;
        }
        if (str.equals(this.chunkid) || allowOtherChunksInBetween(str)) {
            return true;
        }
        if (this.state.isDone()) {
            if (!isTerminated()) {
                terminate();
            }
            return false;
        }
        throw new PngjInputException("Unexpected chunk " + str + " while " + this.chunkid + " set is not done");
    }

    protected void terminate() {
        close();
    }

    public void close() {
        Inflater inflater;
        try {
            if (!this.state.isTerminated()) {
                this.state = State.TERMINATED;
            }
            if (!this.infOwn || (inflater = this.inf) == null) {
                return;
            }
            inflater.end();
            this.inf = null;
        } catch (Exception unused) {
        }
    }

    public void done() {
        if (isDone()) {
            return;
        }
        this.state = State.WORK_DONE;
    }

    public int getRowLen() {
        return this.rowlen;
    }

    public int getRowFilled() {
        return this.rowfilled;
    }

    public int getRown() {
        return this.rown;
    }

    public boolean isCallbackMode() {
        return this.callbackMode;
    }

    public void setCallbackMode(boolean z) {
        this.callbackMode = z;
    }

    public long getBytesIn() {
        return this.nBytesIn;
    }

    public long getBytesOut() {
        return this.nBytesOut;
    }

    public String toString() {
        return new StringBuilder("idatSet : " + this.curChunk.getChunkRaw().f61id + " state=" + this.state + " rows=" + this.rown + " bytes=" + this.nBytesIn + "/" + this.nBytesOut).toString();
    }
}
