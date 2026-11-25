package org.eclipse.jgit.internal.storage.file;

import java.io.File;
import java.io.IOException;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.util.FS;
import org.eclipse.jgit.util.FileUtils;

/* JADX INFO: loaded from: classes13.dex */
public class PackLock {
    private final File keepFile;

    public PackLock(File file, FS fs) {
        File parentFile = file.getParentFile();
        String name2 = file.getName();
        this.keepFile = new File(parentFile, String.valueOf(name2.substring(0, name2.length() - 5)) + ".keep");
    }

    public boolean lock(String str) throws IOException {
        if (str == null) {
            return false;
        }
        if (!str.endsWith("\n")) {
            str = String.valueOf(str) + "\n";
        }
        LockFile lockFile = new LockFile(this.keepFile);
        if (!lockFile.lock()) {
            return false;
        }
        lockFile.write(Constants.encode(str));
        return lockFile.commit();
    }

    public void unlock() throws IOException {
        FileUtils.delete(this.keepFile);
    }
}
