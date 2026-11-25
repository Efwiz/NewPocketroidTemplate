package org.antlr.runtime;

import java.util.Map;

/* JADX INFO: loaded from: classes12.dex */
public class RecognizerSharedState {
    public int _fsp;
    public int backtracking;
    public int channel;
    public boolean errorRecovery;
    public boolean failed;
    public BitSet[] following;
    public int lastErrorIndex;
    public Map<Integer, Integer>[] ruleMemo;
    public int syntaxErrors;
    public String text;
    public Token token;
    public int tokenStartCharIndex;
    public int tokenStartCharPositionInLine;
    public int tokenStartLine;
    public int type;

    public RecognizerSharedState() {
        this.following = new BitSet[100];
        this._fsp = -1;
        this.errorRecovery = false;
        this.lastErrorIndex = -1;
        this.failed = false;
        this.syntaxErrors = 0;
        this.backtracking = 0;
        this.tokenStartCharIndex = -1;
    }

    public RecognizerSharedState(RecognizerSharedState recognizerSharedState) {
        BitSet[] bitSetArr = new BitSet[100];
        this.following = bitSetArr;
        this._fsp = -1;
        this.errorRecovery = false;
        this.lastErrorIndex = -1;
        this.failed = false;
        this.syntaxErrors = 0;
        this.backtracking = 0;
        this.tokenStartCharIndex = -1;
        int length = bitSetArr.length;
        BitSet[] bitSetArr2 = recognizerSharedState.following;
        if (length < bitSetArr2.length) {
            this.following = new BitSet[bitSetArr2.length];
        }
        BitSet[] bitSetArr3 = recognizerSharedState.following;
        System.arraycopy(bitSetArr3, 0, this.following, 0, bitSetArr3.length);
        this._fsp = recognizerSharedState._fsp;
        this.errorRecovery = recognizerSharedState.errorRecovery;
        this.lastErrorIndex = recognizerSharedState.lastErrorIndex;
        this.failed = recognizerSharedState.failed;
        this.syntaxErrors = recognizerSharedState.syntaxErrors;
        this.backtracking = recognizerSharedState.backtracking;
        Map<Integer, Integer>[] mapArr = recognizerSharedState.ruleMemo;
        if (mapArr != null) {
            Map<Integer, Integer>[] mapArr2 = new Map[mapArr.length];
            this.ruleMemo = mapArr2;
            Map<Integer, Integer>[] mapArr3 = recognizerSharedState.ruleMemo;
            System.arraycopy(mapArr3, 0, mapArr2, 0, mapArr3.length);
        }
        this.token = recognizerSharedState.token;
        this.tokenStartCharIndex = recognizerSharedState.tokenStartCharIndex;
        this.tokenStartCharPositionInLine = recognizerSharedState.tokenStartCharPositionInLine;
        this.channel = recognizerSharedState.channel;
        this.type = recognizerSharedState.type;
        this.text = recognizerSharedState.text;
    }
}
