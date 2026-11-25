package org.eclipse.jdt.internal.compiler.tool;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.util.zip.ZipFile;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.NestingKind;
import javax.tools.JavaFileObject;
import org.eclipse.jdt.internal.compiler.classfmt.ClassFileReader;
import org.eclipse.jdt.internal.compiler.classfmt.ClassFormatException;

/* JADX INFO: loaded from: classes14.dex */
public class ArchiveFileObject implements JavaFileObject {
    private static /* synthetic */ int[] $SWITCH_TABLE$javax$tools$JavaFileObject$Kind;
    private Charset charset;
    private String entryName;
    private File file;
    private ZipFile zipFile;

    static /* synthetic */ int[] $SWITCH_TABLE$javax$tools$JavaFileObject$Kind() {
        int[] iArr = $SWITCH_TABLE$javax$tools$JavaFileObject$Kind;
        if (iArr != null) {
            return iArr;
        }
        int[] iArr2 = new int[JavaFileObject.Kind.values().length];
        try {
            iArr2[JavaFileObject.Kind.CLASS.ordinal()] = 2;
        } catch (NoSuchFieldError unused) {
        }
        try {
            iArr2[JavaFileObject.Kind.HTML.ordinal()] = 3;
        } catch (NoSuchFieldError unused2) {
        }
        try {
            iArr2[JavaFileObject.Kind.OTHER.ordinal()] = 4;
        } catch (NoSuchFieldError unused3) {
        }
        try {
            iArr2[JavaFileObject.Kind.SOURCE.ordinal()] = 1;
        } catch (NoSuchFieldError unused4) {
        }
        $SWITCH_TABLE$javax$tools$JavaFileObject$Kind = iArr2;
        return iArr2;
    }

    public ArchiveFileObject(File file, String str, Charset charset) {
        this.entryName = str;
        this.file = file;
        this.charset = charset;
    }

    protected void finalize() throws Throwable {
        ZipFile zipFile = this.zipFile;
        if (zipFile != null) {
            try {
                zipFile.close();
            } catch (IOException unused) {
            }
        }
        super.finalize();
    }

    /* JADX WARN: Code duplicated, block: B:19:0x002c A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:41:0x002e A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:49:? A[RETURN, SYNTHETIC] */
    public Modifier getAccessLevel() throws Throwable {
        Throwable th;
        ClassFileReader classFileReader;
        if (getKind() != JavaFileObject.Kind.CLASS) {
            return null;
        }
        try {
            ZipFile zipFile = new ZipFile(this.file);
            try {
                ClassFileReader classFileReader2 = ClassFileReader.read(zipFile, this.entryName);
                try {
                    zipFile.close();
                    if (classFileReader2 == null) {
                        return null;
                    }
                    int iAccessFlags = classFileReader2.accessFlags();
                    if ((iAccessFlags & 1) != 0) {
                        return Modifier.PUBLIC;
                    }
                    if ((iAccessFlags & 1024) != 0) {
                        return Modifier.ABSTRACT;
                    }
                    if ((iAccessFlags & 16) != 0) {
                        return Modifier.FINAL;
                    }
                    return null;
                } catch (Throwable th2) {
                    th = th2;
                    classFileReader = classFileReader2;
                    th = null;
                    if (th != null) {
                        throw th;
                    }
                    if (th != th) {
                        try {
                            th.addSuppressed(th);
                        } catch (IOException | ClassFormatException unused) {
                            classFileReader2 = classFileReader;
                        }
                    }
                    throw th;
                }
            } catch (Throwable th3) {
                th = th3;
                try {
                    zipFile.close();
                    throw th;
                } catch (Throwable th4) {
                    th = th4;
                    classFileReader = null;
                    if (th != null) {
                        throw th;
                    }
                    if (th != th) {
                        th.addSuppressed(th);
                    }
                    throw th;
                }
            }
        } catch (Throwable th5) {
            th = th5;
            th = null;
            classFileReader = null;
        }
    }

    public JavaFileObject.Kind getKind() {
        String lowerCase = this.entryName.toLowerCase();
        if (lowerCase.endsWith(JavaFileObject.Kind.CLASS.extension)) {
            return JavaFileObject.Kind.CLASS;
        }
        if (lowerCase.endsWith(JavaFileObject.Kind.SOURCE.extension)) {
            return JavaFileObject.Kind.SOURCE;
        }
        if (lowerCase.endsWith(JavaFileObject.Kind.HTML.extension)) {
            return JavaFileObject.Kind.HTML;
        }
        return JavaFileObject.Kind.OTHER;
    }

    /* JADX WARN: Code duplicated, block: B:21:0x0038 A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:46:0x003a A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:54:? A[RETURN, SYNTHETIC] */
    public NestingKind getNestingKind() throws Throwable {
        Throwable th;
        ClassFileReader classFileReader;
        int i = $SWITCH_TABLE$javax$tools$JavaFileObject$Kind()[getKind().ordinal()];
        if (i == 1) {
            return NestingKind.TOP_LEVEL;
        }
        if (i != 2) {
            return null;
        }
        try {
            ZipFile zipFile = new ZipFile(this.file);
            try {
                ClassFileReader classFileReader2 = ClassFileReader.read(zipFile, this.entryName);
                try {
                    zipFile.close();
                    if (classFileReader2 == null) {
                        return null;
                    }
                    if (classFileReader2.isAnonymous()) {
                        return NestingKind.ANONYMOUS;
                    }
                    if (classFileReader2.isLocal()) {
                        return NestingKind.LOCAL;
                    }
                    if (classFileReader2.isMember()) {
                        return NestingKind.MEMBER;
                    }
                    return NestingKind.TOP_LEVEL;
                } catch (Throwable th2) {
                    th = th2;
                    classFileReader = classFileReader2;
                    th = null;
                    if (th != null) {
                        throw th;
                    }
                    if (th != th) {
                        try {
                            th.addSuppressed(th);
                        } catch (IOException | ClassFormatException unused) {
                            classFileReader2 = classFileReader;
                        }
                    }
                    throw th;
                }
            } catch (Throwable th3) {
                th = th3;
                try {
                    zipFile.close();
                    throw th;
                } catch (Throwable th4) {
                    th = th4;
                    classFileReader = null;
                    if (th != null) {
                        throw th;
                    }
                    if (th != th) {
                        th.addSuppressed(th);
                    }
                    throw th;
                }
            }
        } catch (Throwable th5) {
            th = th5;
            th = null;
            classFileReader = null;
        }
    }

    public boolean isNameCompatible(String str, JavaFileObject.Kind kind) {
        return this.entryName.endsWith(String.valueOf(str) + kind.extension);
    }

    public boolean delete() {
        throw new UnsupportedOperationException();
    }

    public boolean equals(Object obj) {
        if (obj instanceof ArchiveFileObject) {
            return ((ArchiveFileObject) obj).toUri().equals(toUri());
        }
        return false;
    }

    public int hashCode() {
        return toUri().hashCode();
    }

    public CharSequence getCharContent(boolean z) throws IOException {
        Throwable th = null;
        if (getKind() != JavaFileObject.Kind.SOURCE) {
            return null;
        }
        try {
            ZipFile zipFile = new ZipFile(this.file);
            try {
                return Util.getCharContents(this, z, org.eclipse.jdt.internal.compiler.util.Util.getZipEntryByteContent(zipFile.getEntry(this.entryName), zipFile), this.charset.name());
            } finally {
                zipFile.close();
            }
        } catch (Throwable th2) {
            if (0 == 0) {
                throw th2;
            }
            if (null != th2) {
                th.addSuppressed(th2);
            }
            throw null;
        }
    }

    public long getLastModified() {
        Throwable th = null;
        try {
            ZipFile zipFile = new ZipFile(this.file);
            try {
                return zipFile.getEntry(this.entryName).getTime();
            } finally {
                zipFile.close();
            }
        } catch (Throwable th2) {
            if (0 == 0) {
                throw th2;
            }
            if (null == th2) {
                throw null;
            }
            try {
                th.addSuppressed(th2);
                throw null;
            } catch (IOException unused) {
                return 0L;
            }
            return 0L;
        }
    }

    public String getName() {
        return this.entryName;
    }

    public InputStream openInputStream() throws IOException {
        if (this.zipFile == null) {
            this.zipFile = new ZipFile(this.file);
        }
        return this.zipFile.getInputStream(this.zipFile.getEntry(this.entryName));
    }

    public OutputStream openOutputStream() throws IOException {
        throw new UnsupportedOperationException();
    }

    public Reader openReader(boolean z) throws IOException {
        throw new UnsupportedOperationException();
    }

    public Writer openWriter() throws IOException {
        throw new UnsupportedOperationException();
    }

    public URI toUri() {
        try {
            return new URI("jar:" + this.file.toURI().getPath() + "!" + this.entryName);
        } catch (URISyntaxException unused) {
            return null;
        }
    }

    public String toString() {
        return String.valueOf(this.file.getAbsolutePath()) + "[" + this.entryName + "]";
    }
}
