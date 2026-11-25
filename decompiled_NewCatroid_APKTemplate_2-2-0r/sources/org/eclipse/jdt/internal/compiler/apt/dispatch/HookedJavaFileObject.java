package org.eclipse.jdt.internal.compiler.apt.dispatch;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;
import javax.tools.ForwardingJavaFileObject;
import javax.tools.JavaFileObject;
import org.eclipse.jdt.core.compiler.CharOperation;
import org.eclipse.jdt.internal.compiler.batch.CompilationUnit;
import org.eclipse.jdt.internal.compiler.classfmt.ClassFileReader;
import org.eclipse.jdt.internal.compiler.classfmt.ClassFormatException;
import org.eclipse.jdt.internal.compiler.lookup.BinaryTypeBinding;
import org.eclipse.jdt.internal.compiler.lookup.ReferenceBinding;

/* JADX INFO: loaded from: classes14.dex */
public class HookedJavaFileObject extends ForwardingJavaFileObject<JavaFileObject> {
    private static /* synthetic */ int[] $SWITCH_TABLE$javax$tools$JavaFileObject$Kind;
    private boolean _closed;
    protected final String _fileName;
    protected final BatchFilerImpl _filer;
    private String _typeName;

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

    private class ForwardingWriter extends Writer {
        private final Writer _w;

        ForwardingWriter(Writer writer) {
            this._w = writer;
        }

        @Override // java.io.Writer, java.lang.Appendable
        public Writer append(char c) throws IOException {
            return this._w.append(c);
        }

        @Override // java.io.Writer, java.lang.Appendable
        public Writer append(CharSequence charSequence, int i, int i2) throws IOException {
            return this._w.append(charSequence, i, i2);
        }

        @Override // java.io.Writer, java.lang.Appendable
        public Writer append(CharSequence charSequence) throws IOException {
            return this._w.append(charSequence);
        }

        @Override // java.io.Writer, java.io.Closeable, java.lang.AutoCloseable
        public void close() throws IOException {
            this._w.close();
            HookedJavaFileObject.this.closed();
        }

        @Override // java.io.Writer, java.io.Flushable
        public void flush() throws IOException {
            this._w.flush();
        }

        @Override // java.io.Writer
        public void write(char[] cArr) throws IOException {
            this._w.write(cArr);
        }

        @Override // java.io.Writer
        public void write(int i) throws IOException {
            this._w.write(i);
        }

        @Override // java.io.Writer
        public void write(String str, int i, int i2) throws IOException {
            this._w.write(str, i, i2);
        }

        @Override // java.io.Writer
        public void write(String str) throws IOException {
            this._w.write(str);
        }

        @Override // java.io.Writer
        public void write(char[] cArr, int i, int i2) throws IOException {
            this._w.write(cArr, i, i2);
        }

        protected Object clone() throws CloneNotSupportedException {
            return HookedJavaFileObject.this.new ForwardingWriter(this._w);
        }

        public int hashCode() {
            return this._w.hashCode();
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
            ForwardingWriter forwardingWriter = (ForwardingWriter) obj;
            Writer writer = this._w;
            if (writer == null) {
                if (forwardingWriter._w != null) {
                    return false;
                }
            } else if (!writer.equals(forwardingWriter._w)) {
                return false;
            }
            return true;
        }

        public String toString() {
            return "ForwardingWriter wrapping " + this._w.toString();
        }
    }

    private class ForwardingOutputStream extends OutputStream {
        private final OutputStream _os;

        ForwardingOutputStream(OutputStream outputStream) {
            this._os = outputStream;
        }

        @Override // java.io.OutputStream, java.io.Closeable, java.lang.AutoCloseable
        public void close() throws IOException {
            this._os.close();
            HookedJavaFileObject.this.closed();
        }

        @Override // java.io.OutputStream, java.io.Flushable
        public void flush() throws IOException {
            this._os.flush();
        }

        @Override // java.io.OutputStream
        public void write(byte[] bArr, int i, int i2) throws IOException {
            this._os.write(bArr, i, i2);
        }

        @Override // java.io.OutputStream
        public void write(byte[] bArr) throws IOException {
            this._os.write(bArr);
        }

        @Override // java.io.OutputStream
        public void write(int i) throws IOException {
            this._os.write(i);
        }

        protected Object clone() throws CloneNotSupportedException {
            return HookedJavaFileObject.this.new ForwardingOutputStream(this._os);
        }

        public int hashCode() {
            return this._os.hashCode();
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
            ForwardingOutputStream forwardingOutputStream = (ForwardingOutputStream) obj;
            OutputStream outputStream = this._os;
            if (outputStream == null) {
                if (forwardingOutputStream._os != null) {
                    return false;
                }
            } else if (!outputStream.equals(forwardingOutputStream._os)) {
                return false;
            }
            return true;
        }

        public String toString() {
            return "ForwardingOutputStream wrapping " + this._os.toString();
        }
    }

    public HookedJavaFileObject(JavaFileObject javaFileObject, String str, String str2, BatchFilerImpl batchFilerImpl) {
        super(javaFileObject);
        this._closed = false;
        this._filer = batchFilerImpl;
        this._fileName = str;
        this._typeName = str2;
    }

    public OutputStream openOutputStream() throws IOException {
        return new ForwardingOutputStream(super.openOutputStream());
    }

    public Writer openWriter() throws IOException {
        return new ForwardingWriter(super.openWriter());
    }

    protected void closed() {
        if (this._closed) {
            return;
        }
        this._closed = true;
        int i = $SWITCH_TABLE$javax$tools$JavaFileObject$Kind()[getKind().ordinal()];
        ClassFileReader classFileReader = null;
        if (i == 1) {
            this._filer.addNewUnit(new CompilationUnit(null, this._fileName, null));
            return;
        }
        if (i != 2) {
            return;
        }
        try {
            classFileReader = ClassFileReader.read(this._fileName);
        } catch (IOException unused) {
        } catch (ClassFormatException unused2) {
            ReferenceBinding type = this._filer._env._compiler.lookupEnvironment.getType(CharOperation.splitOn('.', this._typeName.toCharArray()));
            if (type != null) {
                this._filer.addNewClassFile(type);
            }
        }
        if (classFileReader != null) {
            ReferenceBinding type2 = this._filer._env._compiler.lookupEnvironment.getType(CharOperation.splitOn('/', classFileReader.getName()));
            if (type2 == null || !type2.isValidBinding()) {
                return;
            }
            if (type2.isBinaryBinding()) {
                this._filer.addNewClassFile(type2);
            } else {
                this._filer.addNewClassFile(new BinaryTypeBinding(type2.getPackage(), classFileReader, this._filer._env._compiler.lookupEnvironment, true));
            }
        }
    }
}
