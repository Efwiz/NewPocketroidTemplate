package org.luaj.vm2.lib.jse;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.OsLib;

/* JADX INFO: loaded from: classes6.dex */
public class JseOsLib extends OsLib {
    public static int EXEC_ERROR = -3;
    public static int EXEC_INTERRUPTED = -2;
    public static int EXEC_IOEXCEPTION = 1;

    @Override // org.luaj.vm2.lib.OsLib
    protected Varargs execute(String str) {
        int iWaitFor;
        try {
            iWaitFor = new JseProcess(str, (InputStream) null, this.globals.STDOUT, this.globals.STDERR).waitFor();
        } catch (IOException unused) {
            iWaitFor = EXEC_IOEXCEPTION;
        } catch (InterruptedException unused2) {
            iWaitFor = EXEC_INTERRUPTED;
        } catch (Throwable unused3) {
            iWaitFor = EXEC_ERROR;
        }
        return iWaitFor == 0 ? varargsOf(TRUE, valueOf("exit"), ZERO) : varargsOf(NIL, valueOf("signal"), valueOf(iWaitFor));
    }

    @Override // org.luaj.vm2.lib.OsLib
    protected String getenv(String str) {
        String str2 = System.getenv(str);
        return str2 != null ? str2 : System.getProperty(str);
    }

    @Override // org.luaj.vm2.lib.OsLib
    protected void remove(String str) throws IOException {
        File file = new File(str);
        if (!file.exists()) {
            throw new IOException("No such file or directory");
        }
        if (!file.delete()) {
            throw new IOException("Failed to delete");
        }
    }

    @Override // org.luaj.vm2.lib.OsLib
    protected void rename(String str, String str2) throws IOException {
        File file = new File(str);
        if (!file.exists()) {
            throw new IOException("No such file or directory");
        }
        if (!file.renameTo(new File(str2))) {
            throw new IOException("Failed to delete");
        }
    }

    @Override // org.luaj.vm2.lib.OsLib
    protected String tmpname() {
        try {
            return File.createTempFile(TMP_PREFIX, TMP_SUFFIX).getName();
        } catch (IOException unused) {
            return super.tmpname();
        }
    }
}
