package com.koushikdutta.async.http.filter;

import com.koushikdutta.async.ByteBufferList;
import com.koushikdutta.async.DataEmitter;
import com.koushikdutta.async.FilteredDataEmitter;
import com.koushikdutta.async.Util;

/* JADX INFO: loaded from: classes10.dex */
public class ChunkedInputFilter extends FilteredDataEmitter {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private int mChunkLength = 0;
    private int mChunkLengthRemaining = 0;
    private State mState = State.CHUNK_LEN;
    ByteBufferList pending = new ByteBufferList();

    private enum State {
        CHUNK_LEN,
        CHUNK_LEN_CR,
        CHUNK_LEN_CRLF,
        CHUNK,
        CHUNK_CR,
        CHUNK_CRLF,
        COMPLETE
    }

    private boolean checkByte(char c, char c2) {
        if (c == c2) {
            return true;
        }
        report(new ChunkedDataException(c2 + " was expected, got " + c));
        return false;
    }

    private boolean checkLF(char c) {
        return checkByte(c, '\n');
    }

    private boolean checkCR(char c) {
        return checkByte(c, '\r');
    }

    @Override // com.koushikdutta.async.DataEmitterBase
    protected void report(Exception exc) {
        if (exc == null && this.mState != State.COMPLETE) {
            exc = new ChunkedDataException("chunked input ended before final chunk");
        }
        super.report(exc);
    }

    /* JADX INFO: renamed from: com.koushikdutta.async.http.filter.ChunkedInputFilter$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$koushikdutta$async$http$filter$ChunkedInputFilter$State;

        static {
            int[] iArr = new int[State.values().length];
            $SwitchMap$com$koushikdutta$async$http$filter$ChunkedInputFilter$State = iArr;
            try {
                iArr[State.CHUNK_LEN.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$koushikdutta$async$http$filter$ChunkedInputFilter$State[State.CHUNK_LEN_CR.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$koushikdutta$async$http$filter$ChunkedInputFilter$State[State.CHUNK.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$koushikdutta$async$http$filter$ChunkedInputFilter$State[State.CHUNK_CR.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$com$koushikdutta$async$http$filter$ChunkedInputFilter$State[State.CHUNK_CRLF.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$com$koushikdutta$async$http$filter$ChunkedInputFilter$State[State.COMPLETE.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
        }
    }

    @Override // com.koushikdutta.async.FilteredDataEmitter, com.koushikdutta.async.callback.DataCallback
    public void onDataAvailable(DataEmitter dataEmitter, ByteBufferList byteBufferList) {
        while (byteBufferList.remaining() > 0) {
            try {
                switch (AnonymousClass1.$SwitchMap$com$koushikdutta$async$http$filter$ChunkedInputFilter$State[this.mState.ordinal()]) {
                    case 1:
                        char byteChar = byteBufferList.getByteChar();
                        if (byteChar == '\r') {
                            this.mState = State.CHUNK_LEN_CR;
                        } else {
                            int i = this.mChunkLength * 16;
                            this.mChunkLength = i;
                            if (byteChar >= 'a' && byteChar <= 'f') {
                                this.mChunkLength = i + (byteChar - 'W');
                            } else if (byteChar >= '0' && byteChar <= '9') {
                                this.mChunkLength = i + (byteChar - '0');
                            } else if (byteChar >= 'A' && byteChar <= 'F') {
                                this.mChunkLength = i + (byteChar - '7');
                            } else {
                                report(new ChunkedDataException("invalid chunk length: " + byteChar));
                                return;
                            }
                        }
                        this.mChunkLengthRemaining = this.mChunkLength;
                        continue;
                    case 2:
                        if (checkLF(byteBufferList.getByteChar())) {
                            this.mState = State.CHUNK;
                            continue;
                        } else {
                            return;
                        }
                        break;
                    case 3:
                        int iMin = Math.min(this.mChunkLengthRemaining, byteBufferList.remaining());
                        int i2 = this.mChunkLengthRemaining - iMin;
                        this.mChunkLengthRemaining = i2;
                        if (i2 == 0) {
                            this.mState = State.CHUNK_CR;
                        }
                        if (iMin != 0) {
                            byteBufferList.get(this.pending, iMin);
                            Util.emitAllData(this, this.pending);
                            continue;
                        }
                        break;
                    case 4:
                        if (checkCR(byteBufferList.getByteChar())) {
                            this.mState = State.CHUNK_CRLF;
                            continue;
                        } else {
                            return;
                        }
                        break;
                    case 5:
                        if (checkLF(byteBufferList.getByteChar())) {
                            if (this.mChunkLength > 0) {
                                this.mState = State.CHUNK_LEN;
                            } else {
                                this.mState = State.COMPLETE;
                                report(null);
                            }
                            this.mChunkLength = 0;
                            continue;
                        } else {
                            return;
                        }
                        break;
                    case 6:
                        return;
                    default:
                        continue;
                }
            } catch (Exception e) {
                report(e);
                return;
            }
            report(e);
            return;
        }
    }
}
