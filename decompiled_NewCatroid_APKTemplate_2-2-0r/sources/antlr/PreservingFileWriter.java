package antlr;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import name.antonsmirnov.firmata.FormatHelper;

/* JADX INFO: loaded from: classes.dex */
public class PreservingFileWriter extends FileWriter {
    protected File target_file;
    protected File tmp_file;

    public PreservingFileWriter(String str) throws IOException {
        super(new StringBuffer().append(str).append(".antlr.tmp").toString());
        File file = new File(str);
        this.target_file = file;
        String parent = file.getParent();
        if (parent != null) {
            File file2 = new File(parent);
            if (!file2.exists()) {
                throw new IOException(new StringBuffer("destination directory of '").append(str).append("' doesn't exist").toString());
            }
            if (!file2.canWrite()) {
                throw new IOException(new StringBuffer("destination directory of '").append(str).append("' isn't writeable").toString());
            }
        }
        if (this.target_file.exists() && !this.target_file.canWrite()) {
            throw new IOException(new StringBuffer("cannot write to '").append(str).append(FormatHelper.QUOTE).toString());
        }
        this.tmp_file = new File(new StringBuffer().append(str).append(".antlr.tmp").toString());
    }

    /* JADX WARN: Code duplicated, block: B:61:0x00b7 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:63:0x00b2 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    @Override // java.io.OutputStreamWriter, java.io.Writer, java.io.Closeable, java.lang.AutoCloseable
    public void close() throws Throwable {
        BufferedWriter bufferedWriter;
        BufferedReader bufferedReader;
        File file;
        try {
            super.close();
            char[] cArr = new char[1024];
            if (this.target_file.length() == this.tmp_file.length()) {
                char[] cArr2 = new char[1024];
                bufferedReader = new BufferedReader(new FileReader(this.tmp_file));
                try {
                    BufferedReader bufferedReader2 = new BufferedReader(new FileReader(this.target_file));
                    boolean z = true;
                    while (z) {
                        int i = bufferedReader.read(cArr, 0, 1024);
                        if (i != bufferedReader2.read(cArr2, 0, 1024)) {
                            z = false;
                            break;
                        }
                        if (i == -1) {
                            break;
                        }
                        for (int i2 = 0; i2 < i; i2++) {
                            if (cArr[i2] != cArr2[i2]) {
                                z = false;
                                break;
                            }
                        }
                    }
                    bufferedReader.close();
                    bufferedReader2.close();
                    if (z) {
                        File file2 = this.tmp_file;
                        if (file2 == null || !file2.exists()) {
                            return;
                        }
                        this.tmp_file.delete();
                        this.tmp_file = null;
                        return;
                    }
                } catch (Throwable th) {
                    th = th;
                    bufferedWriter = null;
                    if (bufferedReader != null) {
                        try {
                            bufferedReader.close();
                        } catch (IOException unused) {
                        }
                    }
                    if (bufferedWriter != null) {
                        try {
                            bufferedWriter.close();
                        } catch (IOException unused2) {
                        }
                    }
                    file = this.tmp_file;
                    if (file != null) {
                        throw th;
                    }
                    throw th;
                }
            }
            bufferedReader = new BufferedReader(new FileReader(this.tmp_file));
            bufferedWriter = new BufferedWriter(new FileWriter(this.target_file));
            while (true) {
                try {
                    int i3 = bufferedReader.read(cArr, 0, 1024);
                    if (i3 == -1) {
                        try {
                            break;
                        } catch (IOException unused3) {
                        }
                    } else {
                        bufferedWriter.write(cArr, 0, i3);
                    }
                } catch (Throwable th2) {
                    th = th2;
                    if (bufferedReader != null) {
                        bufferedReader.close();
                    }
                    if (bufferedWriter != null) {
                        bufferedWriter.close();
                    }
                    file = this.tmp_file;
                    if (file != null || !file.exists()) {
                        throw th;
                    }
                    this.tmp_file.delete();
                    this.tmp_file = null;
                    throw th;
                }
            }
            bufferedReader.close();
            try {
                bufferedWriter.close();
            } catch (IOException unused4) {
            }
            File file3 = this.tmp_file;
            if (file3 == null || !file3.exists()) {
                return;
            }
            this.tmp_file.delete();
            this.tmp_file = null;
        } catch (Throwable th3) {
            th = th3;
            bufferedWriter = null;
            bufferedReader = null;
        }
    }
}
