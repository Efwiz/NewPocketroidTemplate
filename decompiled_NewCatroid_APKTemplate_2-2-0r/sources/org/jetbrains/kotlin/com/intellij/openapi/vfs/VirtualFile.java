package org.jetbrains.kotlin.com.intellij.openapi.vfs;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.huawei.hms.ml.language.common.utils.LanguageCodeUtil;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.util.function.Supplier;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.com.intellij.core.CoreBundle;
import org.jetbrains.kotlin.com.intellij.openapi.Disposable;
import org.jetbrains.kotlin.com.intellij.openapi.application.ApplicationManager;
import org.jetbrains.kotlin.com.intellij.openapi.diagnostic.Logger;
import org.jetbrains.kotlin.com.intellij.openapi.fileTypes.FileType;
import org.jetbrains.kotlin.com.intellij.openapi.fileTypes.FileTypeRegistry;
import org.jetbrains.kotlin.com.intellij.openapi.util.Comparing;
import org.jetbrains.kotlin.com.intellij.openapi.util.Key;
import org.jetbrains.kotlin.com.intellij.openapi.util.ModificationTracker;
import org.jetbrains.kotlin.com.intellij.openapi.util.ThrowableComputable;
import org.jetbrains.kotlin.com.intellij.openapi.util.UserDataHolderBase;
import org.jetbrains.kotlin.com.intellij.openapi.util.io.FileUtilRt;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.encoding.EncodingRegistry;
import org.jetbrains.kotlin.com.intellij.util.text.CharArrayUtil;

/* JADX INFO: loaded from: classes3.dex */
public abstract class VirtualFile extends UserDataHolderBase implements ModificationTracker {
    public static final String PROP_CHILDREN_CASE_SENSITIVITY = "CHILDREN_CASE_SENSITIVITY";
    public static final String PROP_ENCODING = "encoding";
    public static final String PROP_HIDDEN = "HIDDEN";
    public static final String PROP_NAME = "name";
    public static final String PROP_SYMLINK_TARGET = "symlink";
    public static final String PROP_WRITABLE = "writable";
    public static final VirtualFile[] EMPTY_ARRAY = new VirtualFile[0];
    private static final Logger LOG = Logger.getInstance((Class<?>) VirtualFile.class);
    private static final Key<byte[]> BOM_KEY = Key.create("BOM");
    private static final Key<Charset> CHARSET_KEY = Key.create("CHARSET");
    private static final Key<String> DETECTED_LINE_SEPARATOR_KEY = Key.create("DETECTED_LINE_SEPARATOR_KEY");

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str;
        int i2;
        switch (i) {
            case 5:
            case 6:
            case 7:
            case 8:
            case 12:
            case 13:
            case 15:
            case 17:
            case 18:
            case 19:
            case 22:
            case 23:
            case 24:
            case 28:
            case 29:
            case 30:
                str = "Argument for @NotNull parameter '%s' of %s.%s must not be null";
                break;
            case 9:
            case 10:
            case 11:
            case 14:
            case 16:
            case 20:
            case 21:
            case 25:
            case 26:
            case 27:
            default:
                str = "@NotNull method %s.%s must not return null";
                break;
        }
        switch (i) {
            case 5:
            case 6:
            case 7:
            case 8:
            case 12:
            case 13:
            case 15:
            case 17:
            case 18:
            case 19:
            case 22:
            case 23:
            case 24:
            case 28:
            case 29:
            case 30:
                i2 = 3;
                break;
            case 9:
            case 10:
            case 11:
            case 14:
            case 16:
            case 20:
            case 21:
            case 25:
            case 26:
            case 27:
            default:
                i2 = 2;
                break;
        }
        Object[] objArr = new Object[i2];
        switch (i) {
            case 5:
                objArr[0] = "newName";
                break;
            case 6:
                objArr[0] = "property";
                break;
            case 7:
            case 8:
            case 13:
            case 15:
            case 28:
                objArr[0] = "name";
                break;
            case 9:
            case 10:
            case 11:
            case 14:
            case 16:
            case 20:
            case 21:
            case 25:
            case 26:
            case 27:
            default:
                objArr[0] = "org/jetbrains/kotlin/com/intellij/openapi/vfs/VirtualFile";
                break;
            case 12:
                objArr[0] = "relPath";
                break;
            case 17:
            case 18:
                objArr[0] = "newParent";
                break;
            case 19:
                objArr[0] = "copyName";
                break;
            case 22:
            case 23:
            case 24:
                objArr[0] = FirebaseAnalytics.Param.CONTENT;
                break;
            case 29:
                objArr[0] = "computable";
                break;
            case 30:
                objArr[0] = "preloadedContentHint";
                break;
        }
        switch (i) {
            case 1:
                objArr[1] = "toNioPath";
                break;
            case 2:
                objArr[1] = "getUrl";
                break;
            case 3:
                objArr[1] = "getPresentableUrl";
                break;
            case 4:
                objArr[1] = "getNameWithoutExtension";
                break;
            case 5:
            case 6:
            case 7:
            case 8:
            case 12:
            case 13:
            case 15:
            case 17:
            case 18:
            case 19:
            case 22:
            case 23:
            case 24:
            case 28:
            case 29:
            case 30:
                objArr[1] = "org/jetbrains/kotlin/com/intellij/openapi/vfs/VirtualFile";
                break;
            case 9:
            case 10:
                objArr[1] = "findOrCreateChildData";
                break;
            case 11:
                objArr[1] = "getFileType";
                break;
            case 14:
                objArr[1] = "createChildDirectory";
                break;
            case 16:
                objArr[1] = "createChildData";
                break;
            case 20:
                objArr[1] = ConfigConstants.CONFIG_RENAMELIMIT_COPY;
                break;
            case 21:
                objArr[1] = "getCharset";
                break;
            case 25:
                objArr[1] = "getOutputStream";
                break;
            case 26:
                objArr[1] = "contentsToByteArray";
                break;
            case 27:
                objArr[1] = "getPresentableName";
                break;
            default:
                objArr[1] = "getNameSequence";
                break;
        }
        switch (i) {
            case 5:
                objArr[2] = "rename";
                break;
            case 6:
                objArr[2] = LanguageCodeUtil.IS;
                break;
            case 7:
                objArr[2] = "findChild";
                break;
            case 8:
                objArr[2] = "findOrCreateChildData";
                break;
            case 12:
                objArr[2] = "findFileByRelativePath";
                break;
            case 13:
                objArr[2] = "createChildDirectory";
                break;
            case 15:
                objArr[2] = "createChildData";
                break;
            case 17:
                objArr[2] = "move";
                break;
            case 18:
            case 19:
                objArr[2] = ConfigConstants.CONFIG_RENAMELIMIT_COPY;
                break;
            case 22:
            case 23:
            case 24:
                objArr[2] = "setBinaryContent";
                break;
            case 28:
                objArr[2] = "nameEquals";
                break;
            case 29:
            case 30:
                objArr[2] = "computeWithPreloadedContentHint";
                break;
        }
        String str2 = String.format(str, objArr);
        switch (i) {
            case 5:
            case 6:
            case 7:
            case 8:
            case 12:
            case 13:
            case 15:
            case 17:
            case 18:
            case 19:
            case 22:
            case 23:
            case 24:
            case 28:
            case 29:
            case 30:
                throw new IllegalArgumentException(str2);
            case 9:
            case 10:
            case 11:
            case 14:
            case 16:
            case 20:
            case 21:
            case 25:
            case 26:
            case 27:
            default:
                throw new IllegalStateException(str2);
        }
    }

    public abstract byte[] contentsToByteArray() throws IOException;

    public VirtualFile getCanonicalFile() {
        return this;
    }

    public abstract VirtualFile[] getChildren();

    public abstract VirtualFileSystem getFileSystem();

    public abstract InputStream getInputStream() throws IOException;

    public abstract long getLength();

    public abstract String getName();

    public abstract OutputStream getOutputStream(Object obj, long j, long j2) throws IOException;

    public abstract VirtualFile getParent();

    public abstract String getPath();

    public abstract long getTimeStamp();

    public boolean is(VFileProperty vFileProperty) {
        if (vFileProperty != null) {
            return false;
        }
        $$$reportNull$$$0(6);
        return false;
    }

    public abstract boolean isDirectory();

    public boolean isInLocalFileSystem() {
        return false;
    }

    public abstract boolean isValid();

    public abstract boolean isWritable();

    public abstract void refresh(boolean z, boolean z2, Runnable runnable);

    protected VirtualFile() {
        if (this instanceof Disposable) {
            throw new IllegalStateException("VirtualFile must not implement Disposable because of life-cycle requirements. E.g. VirtualFile should exist throughout the application and may not be disposed half-way.");
        }
    }

    public CharSequence getNameSequence() {
        String name2 = getName();
        if (name2 == null) {
            $$$reportNull$$$0(0);
        }
        return name2;
    }

    public Path toNioPath() {
        Path nioPath = getFileSystem().getNioPath(this);
        if (nioPath == null) {
            throw new UnsupportedOperationException("Failed to map " + this + " (filesystem " + getFileSystem() + ") into nio Path");
        }
        if (nioPath == null) {
            $$$reportNull$$$0(1);
        }
        return nioPath;
    }

    public String getUrl() {
        String strConstructUrl = VirtualFileManager.constructUrl(getFileSystem().getProtocol(), getPath());
        if (strConstructUrl == null) {
            $$$reportNull$$$0(2);
        }
        return strConstructUrl;
    }

    public final String getPresentableUrl() {
        String strExtractPresentableUrl = getFileSystem().extractPresentableUrl(getPath());
        if (strExtractPresentableUrl == null) {
            $$$reportNull$$$0(3);
        }
        return strExtractPresentableUrl;
    }

    public String getExtension() {
        CharSequence extension = FileUtilRt.getExtension(getNameSequence(), null);
        if (extension == null) {
            return null;
        }
        return extension.toString();
    }

    public String getNameWithoutExtension() {
        String string = FileUtilRt.getNameWithoutExtension(getNameSequence()).toString();
        if (string == null) {
            $$$reportNull$$$0(4);
        }
        return string;
    }

    public void rename(Object obj, String str) throws IOException {
        if (str == null) {
            $$$reportNull$$$0(5);
        }
        ApplicationManager.getApplication().assertWriteAccessAllowed();
        if (getName().equals(str)) {
            return;
        }
        if (!getFileSystem().isValidName(str)) {
            throw new IOException(CoreBundle.message("file.invalid.name.error", str));
        }
        getFileSystem().renameFile(obj, this, str);
    }

    public void setWritable(boolean z) throws IOException {
        throw new IOException("Not supported");
    }

    public String getCanonicalPath() {
        return getPath();
    }

    public VirtualFile findChild(String str) {
        if (str == null) {
            $$$reportNull$$$0(7);
        }
        VirtualFile[] children = getChildren();
        if (children == null) {
            return null;
        }
        for (VirtualFile virtualFile : children) {
            if (virtualFile.nameEquals(str)) {
                return virtualFile;
            }
        }
        return null;
    }

    public VirtualFile findOrCreateChildData(Object obj, String str) throws IOException {
        if (str == null) {
            $$$reportNull$$$0(8);
        }
        VirtualFile virtualFileFindChild = findChild(str);
        if (virtualFileFindChild != null) {
            if (virtualFileFindChild == null) {
                $$$reportNull$$$0(9);
            }
            return virtualFileFindChild;
        }
        VirtualFile virtualFileCreateChildData = createChildData(obj, str);
        if (virtualFileCreateChildData == null) {
            $$$reportNull$$$0(10);
        }
        return virtualFileCreateChildData;
    }

    public FileType getFileType() {
        FileType fileTypeByFile = FileTypeRegistry.getInstance().getFileTypeByFile(this);
        if (fileTypeByFile == null) {
            $$$reportNull$$$0(11);
        }
        return fileTypeByFile;
    }

    public VirtualFile findFileByRelativePath(String str) {
        if (str == null) {
            $$$reportNull$$$0(12);
        }
        int iShiftForward = CharArrayUtil.shiftForward(str, 0, "/");
        VirtualFile virtualFileFindChild = this;
        while (virtualFileFindChild != null && iShiftForward < str.length()) {
            int iIndexOf = str.indexOf(47, iShiftForward);
            if (iIndexOf < 0) {
                iIndexOf = str.length();
            }
            String strSubstring = str.substring(iShiftForward, iIndexOf);
            if (strSubstring.equals("..")) {
                if (virtualFileFindChild.is(VFileProperty.SYMLINK)) {
                    VirtualFile canonicalFile = virtualFileFindChild.getCanonicalFile();
                    virtualFileFindChild = canonicalFile != null ? canonicalFile.getParent() : null;
                } else {
                    virtualFileFindChild = virtualFileFindChild.getParent();
                }
            } else if (!strSubstring.equals(".")) {
                virtualFileFindChild = virtualFileFindChild.findChild(strSubstring);
            }
            iShiftForward = CharArrayUtil.shiftForward(str, iIndexOf, "/");
        }
        return virtualFileFindChild;
    }

    public VirtualFile createChildDirectory(Object obj, String str) throws IOException {
        if (str == null) {
            $$$reportNull$$$0(13);
        }
        if (!isDirectory()) {
            throw new IOException(CoreBundle.message("directory.create.wrong.parent.error", new Object[0]));
        }
        if (!isValid()) {
            throw new IOException(CoreBundle.message("invalid.directory.create.files", new Object[0]));
        }
        if (!getFileSystem().isValidName(str)) {
            throw new IOException(CoreBundle.message("directory.invalid.name.error", str));
        }
        if (findChild(str) != null) {
            throw new IOException(CoreBundle.message("file.create.already.exists.error", getUrl(), str));
        }
        VirtualFile virtualFileCreateChildDirectory = getFileSystem().createChildDirectory(obj, this, str);
        if (virtualFileCreateChildDirectory == null) {
            $$$reportNull$$$0(14);
        }
        return virtualFileCreateChildDirectory;
    }

    public VirtualFile createChildData(Object obj, String str) throws IOException {
        if (str == null) {
            $$$reportNull$$$0(15);
        }
        if (!isDirectory()) {
            throw new IOException(CoreBundle.message("file.create.wrong.parent.error", new Object[0]));
        }
        if (!isValid()) {
            throw new IOException(CoreBundle.message("invalid.directory.create.files", new Object[0]));
        }
        if (!getFileSystem().isValidName(str)) {
            throw new IOException(CoreBundle.message("file.invalid.name.error", str));
        }
        if (findChild(str) != null) {
            throw new IOException(CoreBundle.message("file.create.already.exists.error", getUrl(), str));
        }
        VirtualFile virtualFileCreateChildFile = getFileSystem().createChildFile(obj, this, str);
        if (virtualFileCreateChildFile == null) {
            $$$reportNull$$$0(16);
        }
        return virtualFileCreateChildFile;
    }

    public void delete(Object obj) throws IOException {
        ApplicationManager.getApplication().assertWriteAccessAllowed();
        LOG.assertTrue(isValid(), "Deleting invalid file");
        getFileSystem().deleteFile(obj, this);
    }

    public void move(final Object obj, final VirtualFile virtualFile) throws Throwable {
        if (virtualFile == null) {
            $$$reportNull$$$0(17);
        }
        ApplicationManager.getApplication().assertWriteAccessAllowed();
        if (getFileSystem() != virtualFile.getFileSystem()) {
            throw new IOException(CoreBundle.message("file.move.error", virtualFile.getPresentableUrl()));
        }
        EncodingRegistry.doActionAndRestoreEncoding(this, new ThrowableComputable() { // from class: org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile$$ExternalSyntheticLambda0
            @Override // org.jetbrains.kotlin.com.intellij.openapi.util.ThrowableComputable
            public final Object compute() {
                return this.f$0.m6509x58354818(obj, virtualFile);
            }
        });
    }

    /* JADX INFO: renamed from: lambda$move$0$org-jetbrains-kotlin-com-intellij-openapi-vfs-VirtualFile, reason: not valid java name */
    /* synthetic */ VirtualFile m6509x58354818(Object obj, VirtualFile virtualFile) throws IOException {
        getFileSystem().moveFile(obj, this, virtualFile);
        return this;
    }

    public VirtualFile copy(final Object obj, final VirtualFile virtualFile, final String str) throws Throwable {
        if (virtualFile == null) {
            $$$reportNull$$$0(18);
        }
        if (str == null) {
            $$$reportNull$$$0(19);
        }
        if (getFileSystem() != virtualFile.getFileSystem()) {
            throw new IOException(CoreBundle.message("file.copy.error", virtualFile.getPresentableUrl()));
        }
        if (!virtualFile.isDirectory()) {
            throw new IOException(CoreBundle.message("file.copy.target.must.be.directory", new Object[0]));
        }
        VirtualFile virtualFileDoActionAndRestoreEncoding = EncodingRegistry.doActionAndRestoreEncoding(this, new ThrowableComputable() { // from class: org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile$$ExternalSyntheticLambda1
            @Override // org.jetbrains.kotlin.com.intellij.openapi.util.ThrowableComputable
            public final Object compute() {
                return this.f$0.m6508x90dada7d(obj, virtualFile, str);
            }
        });
        if (virtualFileDoActionAndRestoreEncoding == null) {
            $$$reportNull$$$0(20);
        }
        return virtualFileDoActionAndRestoreEncoding;
    }

    /* JADX INFO: renamed from: lambda$copy$1$org-jetbrains-kotlin-com-intellij-openapi-vfs-VirtualFile, reason: not valid java name */
    /* synthetic */ VirtualFile m6508x90dada7d(Object obj, VirtualFile virtualFile, String str) throws IOException {
        return getFileSystem().copyFile(obj, this, virtualFile, str);
    }

    public Charset getCharset() {
        Charset storedCharset = getStoredCharset();
        if (storedCharset == null) {
            storedCharset = EncodingRegistry.getInstance().getDefaultCharset();
            setCharset(storedCharset);
        }
        if (storedCharset == null) {
            $$$reportNull$$$0(21);
        }
        return storedCharset;
    }

    private Charset getStoredCharset() {
        return (Charset) getUserData(CHARSET_KEY);
    }

    protected void storeCharset(Charset charset) {
        putUserData(CHARSET_KEY, charset);
    }

    public void setCharset(Charset charset) {
        setCharset(charset, null);
    }

    public void setCharset(Charset charset, Runnable runnable) {
        setCharset(charset, runnable, true);
    }

    public void setCharset(Charset charset, Runnable runnable, boolean z) {
        Charset storedCharset = getStoredCharset();
        storeCharset(charset);
        if (Comparing.equal(charset, storedCharset)) {
            return;
        }
        byte[] mandatoryBom = charset == null ? null : CharsetToolkit.getMandatoryBom(charset);
        byte[] bom = getBOM();
        if (mandatoryBom == null && charset != null && bom != null) {
            mandatoryBom = CharsetToolkit.canHaveBom(charset, bom) ? bom : null;
        }
        setBOM(mandatoryBom);
        if (storedCharset != null) {
            if (runnable != null) {
                runnable.run();
            }
            if (z) {
                VirtualFileManager.getInstance().notifyPropertyChanged(this, "encoding", storedCharset, charset);
            }
        }
    }

    public boolean isCharsetSet() {
        return getStoredCharset() != null;
    }

    public final void setBinaryContent(byte[] bArr) throws IOException {
        if (bArr == null) {
            $$$reportNull$$$0(22);
        }
        setBinaryContent(bArr, -1L, -1L);
    }

    public void setBinaryContent(byte[] bArr, long j, long j2) throws IOException {
        if (bArr == null) {
            $$$reportNull$$$0(23);
        }
        setBinaryContent(bArr, j, j2, this);
    }

    public void setBinaryContent(byte[] bArr, long j, long j2, Object obj) throws IOException {
        if (bArr == null) {
            $$$reportNull$$$0(24);
        }
        OutputStream outputStream = getOutputStream(obj, j, j2);
        try {
            outputStream.write(bArr);
            if (outputStream != null) {
                outputStream.close();
            }
        } catch (Throwable th) {
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
            }
            throw th;
        }
    }

    public final OutputStream getOutputStream(Object obj) throws IOException {
        OutputStream outputStream = getOutputStream(obj, -1L, -1L);
        if (outputStream == null) {
            $$$reportNull$$$0(25);
        }
        return outputStream;
    }

    public byte[] contentsToByteArray(boolean z) throws IOException {
        byte[] bArrContentsToByteArray = contentsToByteArray();
        if (bArrContentsToByteArray == null) {
            $$$reportNull$$$0(26);
        }
        return bArrContentsToByteArray;
    }

    public long getModificationStamp() {
        throw new UnsupportedOperationException(getClass().getName());
    }

    public void refresh(boolean z, boolean z2) {
        refresh(z, z2, null);
    }

    public String getPresentableName() {
        String name2 = getName();
        if (name2 == null) {
            $$$reportNull$$$0(27);
        }
        return name2;
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.util.ModificationTracker
    public long getModificationCount() {
        if (isValid()) {
            return getTimeStamp();
        }
        return -1L;
    }

    protected boolean nameEquals(String str) {
        if (str == null) {
            $$$reportNull$$$0(28);
        }
        return Comparing.equal(getNameSequence(), (CharSequence) str);
    }

    public byte[] getBOM() {
        return (byte[]) getUserData(BOM_KEY);
    }

    public void setBOM(byte[] bArr) {
        putUserData(BOM_KEY, bArr);
    }

    @Override // java.util.concurrent.atomic.AtomicReference
    public String toString() {
        return "VirtualFile: " + getPresentableUrl();
    }

    public boolean exists() {
        return isValid();
    }

    public String getDetectedLineSeparator() {
        return (String) getUserData(DETECTED_LINE_SEPARATOR_KEY);
    }

    public void setDetectedLineSeparator(String str) {
        putUserData(DETECTED_LINE_SEPARATOR_KEY, str);
    }

    public <T> T computeWithPreloadedContentHint(byte[] bArr, Supplier<? extends T> supplier) {
        if (supplier == null) {
            $$$reportNull$$$0(29);
        }
        if (bArr == null) {
            $$$reportNull$$$0(30);
        }
        return supplier.get();
    }

    public boolean isRecursiveOrCircularSymlink() {
        VirtualFile canonicalFile;
        if (!is(VFileProperty.SYMLINK) || (canonicalFile = getCanonicalFile()) == null) {
            return false;
        }
        if (VfsUtilCore.isAncestor(canonicalFile, this, false)) {
            return true;
        }
        for (VirtualFile parent = getParent(); parent != null; parent = parent.getParent()) {
            if (parent.is(VFileProperty.SYMLINK) && canonicalFile.equals(parent.getCanonicalFile())) {
                return true;
            }
        }
        return false;
    }

    public boolean isCaseSensitive() {
        return getFileSystem().isCaseSensitive();
    }
}
