package org.eclipse.jgit.storage.file;

import com.gaurav.avnc.vnc.XKeySym;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.text.MessageFormat;
import org.eclipse.jgit.errors.ConfigInvalidException;
import org.eclipse.jgit.errors.LockFailedException;
import org.eclipse.jgit.internal.JGitText;
import org.eclipse.jgit.internal.storage.file.FileSnapshot;
import org.eclipse.jgit.internal.storage.file.LockFile;
import org.eclipse.jgit.lib.AnyObjectId;
import org.eclipse.jgit.lib.Config;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.StoredConfig;
import org.eclipse.jgit.util.FS;
import org.eclipse.jgit.util.FileUtils;
import org.eclipse.jgit.util.IO;
import org.eclipse.jgit.util.RawParseUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/* JADX INFO: loaded from: classes13.dex */
public class FileBasedConfig extends StoredConfig {
    private static final Logger LOG = LoggerFactory.getLogger((Class<?>) FileBasedConfig.class);
    private final File configFile;
    private final FS fs;
    private volatile ObjectId hash;
    private volatile FileSnapshot snapshot;
    private boolean utf8Bom;

    @Override // org.eclipse.jgit.lib.Config
    protected boolean notifyUponTransientChanges() {
        return false;
    }

    public FileBasedConfig(File file, FS fs) {
        this(null, file, fs);
    }

    public FileBasedConfig(Config config, File file, FS fs) {
        super(config);
        this.configFile = file;
        this.fs = fs;
        this.snapshot = FileSnapshot.DIRTY;
        this.hash = ObjectId.zeroId();
    }

    public final File getFile() {
        return this.configFile;
    }

    @Override // org.eclipse.jgit.lib.StoredConfig
    public void load() throws ConfigInvalidException, IOException {
        String strDecode;
        int i = 20;
        int i2 = 0;
        while (true) {
            FileSnapshot fileSnapshot = this.snapshot;
            FileSnapshot fileSnapshotSaveNoConfig = FileSnapshot.saveNoConfig(getFile());
            try {
                byte[] fully = IO.readFully(getFile());
                ObjectId objectIdHash = hash(fully);
                if (this.hash.equals((AnyObjectId) objectIdHash)) {
                    if (fileSnapshot.equals(fileSnapshotSaveNoConfig)) {
                        fileSnapshot.setClean(fileSnapshotSaveNoConfig);
                        return;
                    } else {
                        this.snapshot = fileSnapshotSaveNoConfig;
                        return;
                    }
                }
                if (isUtf8(fully)) {
                    strDecode = RawParseUtils.decode(StandardCharsets.UTF_8, fully, 3, fully.length);
                    this.utf8Bom = true;
                } else {
                    strDecode = RawParseUtils.decode(fully);
                }
                fromText(strDecode);
                this.snapshot = fileSnapshotSaveNoConfig;
                this.hash = objectIdHash;
                return;
            } catch (FileNotFoundException e) {
                if (i2 < 5 && this.configFile.exists()) {
                    Logger logger = LOG;
                    if (logger.isDebugEnabled()) {
                        logger.debug(MessageFormat.format(JGitText.get().configHandleMayBeLocked, Integer.valueOf(i2)), (Throwable) e);
                    }
                    try {
                        Thread.sleep(i);
                    } catch (InterruptedException unused) {
                        Thread.currentThread().interrupt();
                    }
                    i2++;
                    i *= 2;
                } else {
                    if (this.configFile.exists()) {
                        throw e;
                    }
                    clear();
                    this.snapshot = fileSnapshotSaveNoConfig;
                    return;
                }
            } catch (IOException e2) {
                if (FileUtils.isStaleFileHandle(e2) && i2 < 5) {
                    Logger logger2 = LOG;
                    if (logger2.isDebugEnabled()) {
                        logger2.debug(MessageFormat.format(JGitText.get().configHandleIsStale, Integer.valueOf(i2)), (Throwable) e2);
                    }
                    i2++;
                } else {
                    throw new IOException(MessageFormat.format(JGitText.get().cannotReadFile, getFile()), e2);
                }
            } catch (ConfigInvalidException e3) {
                throw new ConfigInvalidException(MessageFormat.format(JGitText.get().cannotReadFile, getFile()), e3);
            }
        }
        throw new IOException(MessageFormat.format(JGitText.get().cannotReadFile, getFile()), e2);
    }

    @Override // org.eclipse.jgit.lib.StoredConfig
    public void save() throws IOException {
        byte[] bArrEncode;
        String text = toText();
        if (this.utf8Bom) {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            byteArrayOutputStream.write(XKeySym.XK_idiaeresis);
            byteArrayOutputStream.write(187);
            byteArrayOutputStream.write(191);
            byteArrayOutputStream.write(text.getBytes(StandardCharsets.UTF_8));
            bArrEncode = byteArrayOutputStream.toByteArray();
        } else {
            bArrEncode = Constants.encode(text);
        }
        LockFile lockFile = new LockFile(getFile());
        try {
            if (!lockFile.lock()) {
                throw new LockFailedException(getFile());
            }
            lockFile.setNeedSnapshotNoConfig(true);
            lockFile.write(bArrEncode);
            if (!lockFile.commit()) {
                throw new IOException(MessageFormat.format(JGitText.get().cannotCommitWriteTo, getFile()));
            }
            lockFile.unlock();
            this.snapshot = lockFile.getCommitSnapshot();
            this.hash = hash(bArrEncode);
            fireConfigChangedEvent();
        } catch (Throwable th) {
            lockFile.unlock();
            throw th;
        }
    }

    @Override // org.eclipse.jgit.lib.StoredConfig, org.eclipse.jgit.lib.Config
    public void clear() {
        this.hash = hash(new byte[0]);
        super.clear();
    }

    private static ObjectId hash(byte[] bArr) {
        return ObjectId.fromRaw(Constants.newMessageDigest().digest(bArr));
    }

    public String toString() {
        return String.valueOf(getClass().getSimpleName()) + "[" + getFile().getPath() + "]";
    }

    public boolean isOutdated() {
        return this.snapshot.isModified(getFile());
    }

    @Override // org.eclipse.jgit.lib.Config
    protected byte[] readIncludedConfig(String str) throws ConfigInvalidException {
        File fileResolve;
        if (str.startsWith("~/")) {
            FS fs = this.fs;
            fileResolve = fs.resolve(fs.userHome(), str.substring(2));
        } else {
            fileResolve = this.fs.resolve(this.configFile.getParentFile(), str);
        }
        if (!fileResolve.exists()) {
            return null;
        }
        try {
            return IO.readFully(fileResolve);
        } catch (IOException e) {
            throw new ConfigInvalidException(MessageFormat.format(JGitText.get().cannotReadFile, str), e);
        }
    }
}
