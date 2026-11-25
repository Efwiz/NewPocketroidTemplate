package org.eclipse.jdt.internal.compiler.batch;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import org.eclipse.jdt.core.compiler.CharOperation;
import org.eclipse.jdt.internal.compiler.classfmt.ClassFileReader;
import org.eclipse.jdt.internal.compiler.classfmt.ClassFormatException;
import org.eclipse.jdt.internal.compiler.classfmt.ExternalAnnotationProvider;
import org.eclipse.jdt.internal.compiler.env.AccessRuleSet;
import org.eclipse.jdt.internal.compiler.env.NameEnvironmentAnswer;
import org.eclipse.jdt.internal.compiler.util.ManifestAnalyzer;
import org.eclipse.jdt.internal.compiler.util.Util;

/* JADX INFO: loaded from: classes14.dex */
public class ClasspathJar extends ClasspathLocation {
    protected List<String> annotationPaths;
    protected ZipFile annotationZipFile;
    protected boolean closeZipFileAtEnd;
    protected File file;
    protected Hashtable packageCache;
    protected ZipFile zipFile;

    @Override // org.eclipse.jdt.internal.compiler.batch.ClasspathLocation
    public int getMode() {
        return 2;
    }

    public ClasspathJar(File file, boolean z, AccessRuleSet accessRuleSet, String str) {
        super(accessRuleSet, str);
        this.file = file;
        this.closeZipFileAtEnd = z;
    }

    @Override // org.eclipse.jdt.internal.compiler.batch.FileSystem.Classpath
    public List fetchLinkedJars(FileSystem.ClasspathSectionProblemReporter classpathSectionProblemReporter) throws Throwable {
        InputStream inputStream;
        InputStream inputStream2 = null;
        try {
            initialize();
            ArrayList arrayList = new ArrayList();
            ZipEntry entry = this.zipFile.getEntry("META-INF/MANIFEST.MF");
            if (entry != null) {
                inputStream = this.zipFile.getInputStream(entry);
                try {
                    ManifestAnalyzer manifestAnalyzer = new ManifestAnalyzer();
                    boolean zAnalyzeManifestContents = manifestAnalyzer.analyzeManifestContents(inputStream);
                    List calledFileNames = manifestAnalyzer.getCalledFileNames();
                    if (classpathSectionProblemReporter != null) {
                        if (!zAnalyzeManifestContents || (manifestAnalyzer.getClasspathSectionsCount() == 1 && calledFileNames == null)) {
                            classpathSectionProblemReporter.invalidClasspathSection(getPath());
                        } else if (manifestAnalyzer.getClasspathSectionsCount() > 1) {
                            classpathSectionProblemReporter.multipleClasspathSections(getPath());
                        }
                    }
                    if (calledFileNames != null) {
                        Iterator it = calledFileNames.iterator();
                        String path = getPath();
                        String strSubstring = path.substring(0, path.lastIndexOf(File.separatorChar) + 1);
                        while (it.hasNext()) {
                            arrayList.add(new ClasspathJar(new File(String.valueOf(strSubstring) + ((String) it.next())), this.closeZipFileAtEnd, this.accessRuleSet, this.destinationPath));
                        }
                    }
                    inputStream2 = inputStream;
                } catch (IOException unused) {
                    if (inputStream != null) {
                        try {
                            inputStream.close();
                        } catch (IOException unused2) {
                        }
                    }
                    return null;
                } catch (Throwable th) {
                    th = th;
                    inputStream2 = inputStream;
                    if (inputStream2 != null) {
                        try {
                            inputStream2.close();
                        } catch (IOException unused3) {
                        }
                    }
                    throw th;
                }
            }
            if (inputStream2 != null) {
                try {
                    inputStream2.close();
                } catch (IOException unused4) {
                }
            }
            return arrayList;
        } catch (IOException unused5) {
            inputStream = null;
        } catch (Throwable th2) {
            th = th2;
        }
    }

    @Override // org.eclipse.jdt.internal.compiler.batch.FileSystem.Classpath
    public NameEnvironmentAnswer findClass(char[] cArr, String str, String str2) {
        return findClass(cArr, str, str2, false);
    }

    @Override // org.eclipse.jdt.internal.compiler.batch.FileSystem.Classpath
    public NameEnvironmentAnswer findClass(char[] cArr, String str, String str2, boolean z) {
        if (!isPackage(str)) {
            return null;
        }
        try {
            ClassFileReader classFileReader = ClassFileReader.read(this.zipFile, str2);
            if (classFileReader != null) {
                if (this.annotationPaths != null) {
                    String strSubstring = str2.substring(0, (str2.length() - "CLASS".length()) - 1);
                    Iterator<String> it = this.annotationPaths.iterator();
                    while (it.hasNext()) {
                        try {
                            this.annotationZipFile = classFileReader.setExternalAnnotationProvider(it.next(), strSubstring, this.annotationZipFile, null);
                            if (classFileReader.hasAnnotationProvider()) {
                                break;
                            }
                        } catch (IOException unused) {
                        }
                    }
                }
                return new NameEnvironmentAnswer(classFileReader, fetchAccessRestriction(str2));
            }
        } catch (IOException | ClassFormatException unused2) {
        }
        return null;
    }

    @Override // org.eclipse.jdt.internal.compiler.batch.FileSystem.Classpath
    public boolean hasAnnotationFileFor(String str) {
        return this.zipFile.getEntry(new StringBuilder(String.valueOf(str)).append(ExternalAnnotationProvider.ANNOTATION_FILE_SUFFIX).toString()) != null;
    }

    @Override // org.eclipse.jdt.internal.compiler.batch.FileSystem.Classpath
    public char[][][] findTypeNames(String str) {
        if (!isPackage(str)) {
            return null;
        }
        ArrayList arrayList = new ArrayList();
        Enumeration<? extends ZipEntry> enumerationEntries = this.zipFile.entries();
        while (enumerationEntries.hasMoreElements()) {
            String name2 = enumerationEntries.nextElement().getName();
            int iLastIndexOf = name2.lastIndexOf(47);
            while (iLastIndexOf > 0) {
                String strSubstring = name2.substring(0, iLastIndexOf);
                if (!str.equals(strSubstring)) {
                    break;
                }
                int iLastIndexOf2 = name2.lastIndexOf(46);
                if (iLastIndexOf2 != -1) {
                    arrayList.add(CharOperation.arrayConcat(CharOperation.splitOn('/', strSubstring.toCharArray()), name2.substring(iLastIndexOf + 1, iLastIndexOf2).toCharArray()));
                }
            }
        }
        int size = arrayList.size();
        if (size != 0) {
            arrayList.toArray(new char[size][][]);
        }
        return null;
    }

    @Override // org.eclipse.jdt.internal.compiler.batch.FileSystem.Classpath
    public void initialize() throws IOException {
        if (this.zipFile == null) {
            this.zipFile = new ZipFile(this.file);
        }
    }

    @Override // org.eclipse.jdt.internal.compiler.batch.FileSystem.Classpath
    public boolean isPackage(String str) {
        Hashtable hashtable = this.packageCache;
        if (hashtable != null) {
            return hashtable.containsKey(str);
        }
        Hashtable hashtable2 = new Hashtable(41);
        this.packageCache = hashtable2;
        hashtable2.put(Util.EMPTY_STRING, Util.EMPTY_STRING);
        Enumeration<? extends ZipEntry> enumerationEntries = this.zipFile.entries();
        while (enumerationEntries.hasMoreElements()) {
            String name2 = enumerationEntries.nextElement().getName();
            int iLastIndexOf = name2.lastIndexOf(47);
            while (iLastIndexOf > 0) {
                String strSubstring = name2.substring(0, iLastIndexOf);
                if (this.packageCache.containsKey(strSubstring)) {
                    break;
                }
                this.packageCache.put(strSubstring, strSubstring);
                iLastIndexOf = strSubstring.lastIndexOf(47);
            }
        }
        return this.packageCache.containsKey(str);
    }

    @Override // org.eclipse.jdt.internal.compiler.batch.FileSystem.Classpath
    public void reset() {
        if (this.closeZipFileAtEnd) {
            ZipFile zipFile = this.zipFile;
            if (zipFile != null) {
                try {
                    zipFile.close();
                } catch (IOException unused) {
                }
                this.zipFile = null;
            }
            ZipFile zipFile2 = this.annotationZipFile;
            if (zipFile2 != null) {
                try {
                    zipFile2.close();
                } catch (IOException unused2) {
                }
                this.annotationZipFile = null;
            }
        }
        this.packageCache = null;
    }

    public String toString() {
        return "Classpath for jar file " + this.file.getPath();
    }

    @Override // org.eclipse.jdt.internal.compiler.batch.FileSystem.Classpath
    public char[] normalizedPath() {
        if (this.normalizedPath == null) {
            char[] charArray = getPath().toCharArray();
            if (File.separatorChar == '\\') {
                CharOperation.replace(charArray, '\\', '/');
            }
            this.normalizedPath = CharOperation.subarray(charArray, 0, CharOperation.lastIndexOf('.', charArray));
        }
        return this.normalizedPath;
    }

    @Override // org.eclipse.jdt.internal.compiler.batch.ClasspathLocation, org.eclipse.jdt.internal.compiler.batch.FileSystem.Classpath
    public String getPath() {
        if (this.path == null) {
            try {
                this.path = this.file.getCanonicalPath();
            } catch (IOException unused) {
                this.path = this.file.getAbsolutePath();
            }
        }
        return this.path;
    }
}
