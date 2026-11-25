package org.eclipse.jdt.internal.compiler.batch;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import org.eclipse.jdt.internal.compiler.env.AccessRuleSet;
import org.eclipse.jdt.internal.compiler.env.NameEnvironmentAnswer;
import org.eclipse.jdt.internal.compiler.util.SuffixConstants;
import org.eclipse.jdt.internal.compiler.util.Util;

/* JADX INFO: loaded from: classes14.dex */
public class ClasspathSourceJar extends ClasspathJar {
    private String encoding;

    @Override // org.eclipse.jdt.internal.compiler.batch.ClasspathJar, org.eclipse.jdt.internal.compiler.batch.ClasspathLocation
    public int getMode() {
        return 1;
    }

    public ClasspathSourceJar(File file, boolean z, AccessRuleSet accessRuleSet, String str, String str2) {
        super(file, z, accessRuleSet, str2);
        this.encoding = str;
    }

    @Override // org.eclipse.jdt.internal.compiler.batch.ClasspathJar, org.eclipse.jdt.internal.compiler.batch.FileSystem.Classpath
    public NameEnvironmentAnswer findClass(char[] cArr, String str, String str2, boolean z) throws Throwable {
        InputStream inputStream;
        if (!isPackage(str)) {
            return null;
        }
        ZipEntry entry = this.zipFile.getEntry(String.valueOf(str2.substring(0, str2.length() - 6)) + SuffixConstants.SUFFIX_STRING_java);
        try {
            if (entry != null) {
                try {
                    inputStream = this.zipFile.getInputStream(entry);
                    try {
                        char[] inputStreamAsCharArray = Util.getInputStreamAsCharArray(inputStream, -1, this.encoding);
                        if (inputStream != null) {
                            inputStream.close();
                        }
                        return new NameEnvironmentAnswer(new CompilationUnit(inputStreamAsCharArray, String.valueOf(str2.substring(0, str2.length() - 6)) + SuffixConstants.SUFFIX_STRING_java, this.encoding, this.destinationPath), fetchAccessRestriction(str2));
                    } catch (Throwable th) {
                        th = th;
                        if (inputStream != null) {
                            inputStream.close();
                        }
                        throw th;
                    }
                } catch (Throwable th2) {
                    th = th2;
                    inputStream = null;
                }
            }
        } catch (IOException unused) {
        }
        return null;
    }

    @Override // org.eclipse.jdt.internal.compiler.batch.ClasspathJar, org.eclipse.jdt.internal.compiler.batch.FileSystem.Classpath
    public NameEnvironmentAnswer findClass(char[] cArr, String str, String str2) {
        return findClass(cArr, str, str2, false);
    }
}
