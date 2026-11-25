package org.eclipse.jdt.internal.compiler.batch;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import javax.tools.JavaFileManager;
import javax.tools.JavaFileObject;
import org.eclipse.jdt.core.compiler.CharOperation;
import org.eclipse.jdt.internal.compiler.classfmt.ClassFileReader;
import org.eclipse.jdt.internal.compiler.classfmt.ClassFormatException;
import org.eclipse.jdt.internal.compiler.env.NameEnvironmentAnswer;

/* JADX INFO: loaded from: classes14.dex */
public class ClasspathJsr199 extends ClasspathLocation {
    private static final Set<JavaFileObject.Kind> fileTypes;
    private JavaFileManager fileManager;
    private JavaFileManager.Location location;

    @Override // org.eclipse.jdt.internal.compiler.batch.FileSystem.Classpath
    public List fetchLinkedJars(FileSystem.ClasspathSectionProblemReporter classpathSectionProblemReporter) {
        return null;
    }

    @Override // org.eclipse.jdt.internal.compiler.batch.ClasspathLocation
    public int getMode() {
        return 2;
    }

    @Override // org.eclipse.jdt.internal.compiler.batch.FileSystem.Classpath
    public boolean hasAnnotationFileFor(String str) {
        return false;
    }

    @Override // org.eclipse.jdt.internal.compiler.batch.FileSystem.Classpath
    public void initialize() throws IOException {
    }

    static {
        HashSet hashSet = new HashSet();
        fileTypes = hashSet;
        hashSet.add(JavaFileObject.Kind.CLASS);
    }

    public ClasspathJsr199(JavaFileManager javaFileManager, JavaFileManager.Location location) {
        super(null, null);
        this.fileManager = javaFileManager;
        this.location = location;
    }

    @Override // org.eclipse.jdt.internal.compiler.batch.FileSystem.Classpath
    public NameEnvironmentAnswer findClass(char[] cArr, String str, String str2) {
        return findClass(cArr, str, str2, false);
    }

    @Override // org.eclipse.jdt.internal.compiler.batch.FileSystem.Classpath
    public NameEnvironmentAnswer findClass(char[] cArr, String str, String str2, boolean z) throws Throwable {
        JavaFileObject javaFileForInput;
        Throwable th;
        if (File.separatorChar != '/') {
            str2 = str2.replace(File.separatorChar, '/');
        }
        try {
            int iLastIndexOf = str2.lastIndexOf(46);
            try {
                javaFileForInput = this.fileManager.getJavaFileForInput(this.location, iLastIndexOf < 0 ? str2 : str2.substring(0, iLastIndexOf), JavaFileObject.Kind.CLASS);
            } catch (IOException unused) {
                javaFileForInput = null;
            }
            if (javaFileForInput == null) {
                return null;
            }
            try {
                InputStream inputStreamOpenInputStream = javaFileForInput.openInputStream();
                try {
                    ClassFileReader classFileReader = ClassFileReader.read(inputStreamOpenInputStream, str2);
                    if (classFileReader != null) {
                        NameEnvironmentAnswer nameEnvironmentAnswer = new NameEnvironmentAnswer(classFileReader, fetchAccessRestriction(str2));
                        if (inputStreamOpenInputStream != null) {
                            inputStreamOpenInputStream.close();
                        }
                        return nameEnvironmentAnswer;
                    }
                    if (inputStreamOpenInputStream != null) {
                        inputStreamOpenInputStream.close();
                    }
                } catch (Throwable th2) {
                    th = th2;
                    if (inputStreamOpenInputStream != null) {
                        try {
                            inputStreamOpenInputStream.close();
                        } catch (Throwable th3) {
                            th = th3;
                            if (th == null) {
                                throw th;
                            }
                            if (th != th) {
                                th.addSuppressed(th);
                            }
                            throw th;
                            return null;
                        }
                    }
                    throw th;
                }
            } catch (Throwable th4) {
                th = th4;
                th = null;
            }
        } catch (IOException | ClassFormatException unused2) {
        }
        return null;
    }

    @Override // org.eclipse.jdt.internal.compiler.batch.FileSystem.Classpath
    public char[][][] findTypeNames(String str) {
        Iterable list;
        int iLastIndexOf;
        if (File.separatorChar != '/') {
            str = str.replace(File.separatorChar, '/');
        }
        try {
            list = this.fileManager.list(this.location, str, fileTypes, false);
        } catch (IOException unused) {
            list = null;
        }
        if (list == null) {
            return null;
        }
        ArrayList arrayList = new ArrayList();
        char[][] cArrSplitOn = CharOperation.splitOn(File.separatorChar, str.toCharArray());
        Iterator it = list.iterator();
        while (it.hasNext()) {
            String path = ((JavaFileObject) it.next()).toUri().getPath();
            int iLastIndexOf2 = path.lastIndexOf(47);
            if (iLastIndexOf2 > 0 && (iLastIndexOf = path.lastIndexOf(46)) != -1) {
                arrayList.add(CharOperation.arrayConcat(cArrSplitOn, path.substring(iLastIndexOf2 + 1, iLastIndexOf).toCharArray()));
            }
        }
        int size = arrayList.size();
        if (size == 0) {
            return null;
        }
        char[][][] cArr = new char[size][][];
        arrayList.toArray(cArr);
        return cArr;
    }

    @Override // org.eclipse.jdt.internal.compiler.batch.FileSystem.Classpath
    public boolean isPackage(String str) {
        if (File.separatorChar != '/') {
            str = str.replace(File.separatorChar, '/');
        }
        try {
            JavaFileManager javaFileManager = this.fileManager;
            JavaFileManager.Location location = this.location;
            Set<JavaFileObject.Kind> set = fileTypes;
            return javaFileManager.list(location, str, set, false).iterator().hasNext() || this.fileManager.list(this.location, str, set, true).iterator().hasNext();
        } catch (IOException unused) {
            return false;
        }
    }

    @Override // org.eclipse.jdt.internal.compiler.batch.FileSystem.Classpath
    public void reset() {
        try {
            this.fileManager.flush();
        } catch (IOException unused) {
        }
    }

    public String toString() {
        return "Classpath for Jsr 199 JavaFileManager: " + this.location;
    }

    @Override // org.eclipse.jdt.internal.compiler.batch.FileSystem.Classpath
    public char[] normalizedPath() {
        if (this.normalizedPath == null) {
            this.normalizedPath = this.path.toCharArray();
        }
        return this.normalizedPath;
    }

    @Override // org.eclipse.jdt.internal.compiler.batch.ClasspathLocation, org.eclipse.jdt.internal.compiler.batch.FileSystem.Classpath
    public String getPath() {
        if (this.path == null) {
            this.path = this.location.getName();
        }
        return this.path;
    }
}
