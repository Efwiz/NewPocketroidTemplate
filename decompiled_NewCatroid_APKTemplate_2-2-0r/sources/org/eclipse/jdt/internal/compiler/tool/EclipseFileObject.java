package org.eclipse.jdt.internal.compiler.tool;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;
import java.net.URI;
import java.nio.charset.Charset;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.NestingKind;
import javax.tools.JavaFileObject;
import javax.tools.SimpleJavaFileObject;
import org.eclipse.jdt.internal.compiler.classfmt.ClassFileReader;
import org.eclipse.jdt.internal.compiler.classfmt.ClassFormatException;

/* JADX INFO: loaded from: classes14.dex */
public class EclipseFileObject extends SimpleJavaFileObject {
    private static /* synthetic */ int[] $SWITCH_TABLE$javax$tools$JavaFileObject$Kind;
    private Charset charset;
    private File f;
    private boolean parentsExist;

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

    public EclipseFileObject(String str, URI uri, JavaFileObject.Kind kind, Charset charset) {
        super(uri, kind);
        this.f = new File(this.uri);
        this.charset = charset;
        this.parentsExist = false;
    }

    public Modifier getAccessLevel() {
        ClassFileReader classFileReader;
        if (getKind() != JavaFileObject.Kind.CLASS) {
            return null;
        }
        try {
            classFileReader = ClassFileReader.read(this.f);
        } catch (IOException | ClassFormatException unused) {
            classFileReader = null;
        }
        if (classFileReader == null) {
            return null;
        }
        int iAccessFlags = classFileReader.accessFlags();
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
    }

    public NestingKind getNestingKind() {
        ClassFileReader classFileReader;
        int i = $SWITCH_TABLE$javax$tools$JavaFileObject$Kind()[this.kind.ordinal()];
        if (i == 1) {
            return NestingKind.TOP_LEVEL;
        }
        if (i != 2) {
            return null;
        }
        try {
            classFileReader = ClassFileReader.read(this.f);
        } catch (IOException | ClassFormatException unused) {
            classFileReader = null;
        }
        if (classFileReader == null) {
            return null;
        }
        if (classFileReader.isAnonymous()) {
            return NestingKind.ANONYMOUS;
        }
        if (classFileReader.isLocal()) {
            return NestingKind.LOCAL;
        }
        if (classFileReader.isMember()) {
            return NestingKind.MEMBER;
        }
        return NestingKind.TOP_LEVEL;
    }

    public boolean delete() {
        return this.f.delete();
    }

    public boolean equals(Object obj) {
        if (obj instanceof EclipseFileObject) {
            return ((EclipseFileObject) obj).toUri().equals(this.uri);
        }
        return false;
    }

    public CharSequence getCharContent(boolean z) throws IOException {
        return Util.getCharContents(this, z, org.eclipse.jdt.internal.compiler.util.Util.getFileByteContent(this.f), this.charset.name());
    }

    public long getLastModified() {
        return this.f.lastModified();
    }

    public String getName() {
        return this.f.getPath();
    }

    public int hashCode() {
        return this.f.hashCode();
    }

    public InputStream openInputStream() throws IOException {
        return new BufferedInputStream(new FileInputStream(this.f));
    }

    public OutputStream openOutputStream() throws IOException {
        ensureParentDirectoriesExist();
        return new BufferedOutputStream(new FileOutputStream(this.f));
    }

    public Reader openReader(boolean z) throws IOException {
        return new BufferedReader(new FileReader(this.f));
    }

    public Writer openWriter() throws IOException {
        ensureParentDirectoriesExist();
        return new BufferedWriter(new FileWriter(this.f));
    }

    public String toString() {
        return this.f.getAbsolutePath();
    }

    private void ensureParentDirectoriesExist() throws IOException {
        if (this.parentsExist) {
            return;
        }
        File parentFile = this.f.getParentFile();
        if (parentFile != null && !parentFile.exists() && !parentFile.mkdirs() && (!parentFile.exists() || !parentFile.isDirectory())) {
            throw new IOException("Unable to create parent directories for " + this.f);
        }
        this.parentsExist = true;
    }
}
