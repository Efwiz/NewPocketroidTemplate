package com.github.difflib.unifieddiff;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;

/* JADX INFO: compiled from: UnifiedDiffReader.java */
/* JADX INFO: loaded from: classes.dex */
class InternalUnifiedDiffReader extends BufferedReader {
    private String lastLine;

    public InternalUnifiedDiffReader(Reader reader) {
        super(reader);
    }

    @Override // java.io.BufferedReader
    public String readLine() throws IOException {
        this.lastLine = super.readLine();
        return lastLine();
    }

    String lastLine() {
        return this.lastLine;
    }
}
