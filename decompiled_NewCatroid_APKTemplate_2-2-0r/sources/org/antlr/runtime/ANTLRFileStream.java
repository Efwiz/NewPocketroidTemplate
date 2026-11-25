package org.antlr.runtime;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

/* JADX INFO: loaded from: classes12.dex */
public class ANTLRFileStream extends ANTLRStringStream {
    protected String fileName;

    public ANTLRFileStream(String str) throws IOException {
        this(str, null);
    }

    public ANTLRFileStream(String str, String str2) throws IOException {
        this.fileName = str;
        load(str, str2);
    }

    public void load(String str, String str2) throws IOException {
        InputStreamReader inputStreamReader;
        if (str == null) {
            return;
        }
        int length = (int) new File(str).length();
        FileInputStream fileInputStream = new FileInputStream(str);
        if (str2 != null) {
            inputStreamReader = new InputStreamReader(fileInputStream, str2);
        } else {
            inputStreamReader = new InputStreamReader(fileInputStream);
        }
        try {
            this.data = new char[length];
            this.n = inputStreamReader.read(this.data);
        } finally {
            inputStreamReader.close();
        }
    }

    @Override // org.antlr.runtime.ANTLRStringStream, org.antlr.runtime.IntStream
    public String getSourceName() {
        return this.fileName;
    }
}
