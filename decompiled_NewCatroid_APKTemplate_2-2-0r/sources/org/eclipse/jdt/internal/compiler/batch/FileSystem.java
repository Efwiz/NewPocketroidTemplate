package org.eclipse.jdt.internal.compiler.batch;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.eclipse.jdt.core.compiler.CharOperation;
import org.eclipse.jdt.internal.compiler.classfmt.ClassFileReader;
import org.eclipse.jdt.internal.compiler.env.AccessRuleSet;
import org.eclipse.jdt.internal.compiler.env.INameEnvironment;
import org.eclipse.jdt.internal.compiler.env.NameEnvironmentAnswer;
import org.eclipse.jdt.internal.compiler.util.SuffixConstants;
import org.eclipse.jdt.internal.compiler.util.Util;

/* JADX INFO: loaded from: classes14.dex */
public class FileSystem implements INameEnvironment, SuffixConstants {
    protected boolean annotationsFromClasspath;
    protected Classpath[] classpaths;
    Set knownFileNames;

    public interface Classpath {
        List fetchLinkedJars(ClasspathSectionProblemReporter classpathSectionProblemReporter);

        NameEnvironmentAnswer findClass(char[] cArr, String str, String str2);

        NameEnvironmentAnswer findClass(char[] cArr, String str, String str2, boolean z);

        char[][][] findTypeNames(String str);

        String getPath();

        boolean hasAnnotationFileFor(String str);

        void initialize() throws IOException;

        boolean isPackage(String str);

        char[] normalizedPath();

        void reset();
    }

    public interface ClasspathSectionProblemReporter {
        void invalidClasspathSection(String str);

        void multipleClasspathSections(String str);
    }

    public static class ClasspathNormalizer {
        public static ArrayList normalize(ArrayList arrayList) {
            ArrayList arrayList2 = new ArrayList();
            HashSet hashSet = new HashSet();
            Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                Classpath classpath = (Classpath) it.next();
                if (!hashSet.contains(classpath)) {
                    arrayList2.add(classpath);
                    hashSet.add(classpath);
                }
            }
            return arrayList2;
        }
    }

    public FileSystem(String[] strArr, String[] strArr2, String str) {
        int length = strArr.length;
        this.classpaths = new Classpath[length];
        int i = 0;
        for (String str2 : strArr) {
            Classpath classpath = getClasspath(str2, str, null, null);
            try {
                classpath.initialize();
                int i2 = i + 1;
                try {
                    this.classpaths[i] = classpath;
                } catch (IOException unused) {
                }
                i = i2;
            } catch (IOException unused2) {
            }
        }
        if (i != length) {
            Classpath[] classpathArr = this.classpaths;
            Classpath[] classpathArr2 = new Classpath[i];
            this.classpaths = classpathArr2;
            System.arraycopy(classpathArr, 0, classpathArr2, 0, i);
        }
        initializeKnownFileNames(strArr2);
    }

    protected FileSystem(Classpath[] classpathArr, String[] strArr, boolean z) {
        int length = classpathArr.length;
        this.classpaths = new Classpath[length];
        int i = 0;
        for (Classpath classpath : classpathArr) {
            try {
                classpath.initialize();
                int i2 = i + 1;
                try {
                    this.classpaths[i] = classpath;
                } catch (IOException unused) {
                }
                i = i2;
            } catch (IOException unused2) {
            }
        }
        if (i != length) {
            Classpath[] classpathArr2 = this.classpaths;
            Classpath[] classpathArr3 = new Classpath[i];
            this.classpaths = classpathArr3;
            System.arraycopy(classpathArr2, 0, classpathArr3, 0, i);
        }
        initializeKnownFileNames(strArr);
        this.annotationsFromClasspath = z;
    }

    public static Classpath getClasspath(String str, String str2, AccessRuleSet accessRuleSet) {
        return getClasspath(str, str2, false, accessRuleSet, null, null);
    }

    public static Classpath getClasspath(String str, String str2, AccessRuleSet accessRuleSet, Map map) {
        return getClasspath(str, str2, false, accessRuleSet, null, map);
    }

    public static Classpath getClasspath(String str, String str2, boolean z, AccessRuleSet accessRuleSet, String str3, Map map) {
        Classpath classpathJar;
        File file = new File(convertPathSeparators(str));
        if (file.isDirectory()) {
            if (!file.exists()) {
                return null;
            }
            int i = z ? 1 : 3;
            if (str3 != null && str3 != "none") {
                str3 = convertPathSeparators(str3);
            }
            classpathJar = new ClasspathDirectory(file, str2, i, accessRuleSet, str3, map);
        } else {
            if (!Util.isPotentialZipArchive(str)) {
                return null;
            }
            if (z) {
                if (str3 != null && str3 != "none") {
                    str3 = convertPathSeparators(str3);
                }
                classpathJar = new ClasspathSourceJar(file, true, accessRuleSet, str2, str3);
            } else {
                if (str3 != null) {
                    return null;
                }
                classpathJar = new ClasspathJar(file, true, accessRuleSet, null);
            }
        }
        return classpathJar;
    }

    private void initializeKnownFileNames(String[] strArr) {
        if (strArr == null) {
            this.knownFileNames = new HashSet(0);
            return;
        }
        this.knownFileNames = new HashSet(strArr.length * 2);
        int length = strArr.length;
        while (true) {
            length--;
            if (length < 0) {
                return;
            }
            try {
                char[] charArray = new File(strArr[length]).getCanonicalPath().toCharArray();
                int iLastIndexOf = CharOperation.lastIndexOf('.', charArray);
                if (iLastIndexOf != -1) {
                    charArray = CharOperation.subarray(charArray, 0, iLastIndexOf);
                }
                CharOperation.replace(charArray, '\\', '/');
                int length2 = this.classpaths.length;
                char[] cArr = null;
                for (int i = 0; i < length2; i++) {
                    char[] cArrNormalizedPath = this.classpaths[i].normalizedPath();
                    if ((this.classpaths[i] instanceof ClasspathDirectory) && CharOperation.prefixEquals(cArrNormalizedPath, charArray) && (cArr == null || cArrNormalizedPath.length > cArr.length)) {
                        cArr = cArrNormalizedPath;
                    }
                }
                if (cArr == null) {
                    this.knownFileNames.add(new String(charArray));
                } else {
                    this.knownFileNames.add(new String(CharOperation.subarray(charArray, cArr.length, charArray.length)));
                }
            } catch (IOException unused) {
            }
        }
    }

    @Override // org.eclipse.jdt.internal.compiler.env.INameEnvironment
    public void cleanup() {
        int length = this.classpaths.length;
        for (int i = 0; i < length; i++) {
            this.classpaths[i].reset();
        }
    }

    private static String convertPathSeparators(String str) {
        if (File.separatorChar == '/') {
            return str.replace('\\', '/');
        }
        return str.replace('/', '\\');
    }

    private NameEnvironmentAnswer findClass(String str, char[] cArr, boolean z) {
        NameEnvironmentAnswer nameEnvironmentAnswerInternalFindClass = internalFindClass(str, cArr, z);
        if (this.annotationsFromClasspath && nameEnvironmentAnswerInternalFindClass != null && (nameEnvironmentAnswerInternalFindClass.getBinaryType() instanceof ClassFileReader)) {
            int length = this.classpaths.length;
            for (int i = 0; i < length; i++) {
                Classpath classpath = this.classpaths[i];
                if (classpath.hasAnnotationFileFor(str)) {
                    try {
                        ((ClassFileReader) nameEnvironmentAnswerInternalFindClass.getBinaryType()).setExternalAnnotationProvider(classpath.getPath(), str, classpath instanceof ClasspathJar ? ((ClasspathJar) classpath).zipFile : null, null);
                        break;
                    } catch (IOException unused) {
                        continue;
                    }
                }
            }
        }
        return nameEnvironmentAnswerInternalFindClass;
    }

    private NameEnvironmentAnswer internalFindClass(String str, char[] cArr, boolean z) {
        String strSubstring;
        NameEnvironmentAnswer nameEnvironmentAnswer;
        NameEnvironmentAnswer nameEnvironmentAnswerFindClass;
        if (this.knownFileNames.contains(str)) {
            return null;
        }
        String str2 = String.valueOf(str) + ".class";
        if (str.length() == cArr.length) {
            strSubstring = Util.EMPTY_STRING;
        } else {
            strSubstring = str2.substring(0, (str.length() - cArr.length) - 1);
        }
        String strReplace = File.separatorChar == '/' ? strSubstring : strSubstring.replace('/', File.separatorChar);
        if (strSubstring == strReplace) {
            int length = this.classpaths.length;
            nameEnvironmentAnswer = null;
            for (int i = 0; i < length; i++) {
                NameEnvironmentAnswer nameEnvironmentAnswerFindClass2 = this.classpaths[i].findClass(cArr, strSubstring, str2, z);
                if (nameEnvironmentAnswerFindClass2 != null) {
                    if (!nameEnvironmentAnswerFindClass2.ignoreIfBetter()) {
                        if (nameEnvironmentAnswerFindClass2.isBetter(nameEnvironmentAnswer)) {
                            return nameEnvironmentAnswerFindClass2;
                        }
                    } else if (nameEnvironmentAnswerFindClass2.isBetter(nameEnvironmentAnswer)) {
                        nameEnvironmentAnswer = nameEnvironmentAnswerFindClass2;
                    }
                }
            }
        } else {
            String strReplace2 = str2.replace('/', File.separatorChar);
            int length2 = this.classpaths.length;
            NameEnvironmentAnswer nameEnvironmentAnswer2 = null;
            for (int i2 = 0; i2 < length2; i2++) {
                Classpath classpath = this.classpaths[i2];
                if (classpath instanceof ClasspathJar) {
                    nameEnvironmentAnswerFindClass = classpath.findClass(cArr, strSubstring, str2, z);
                } else {
                    nameEnvironmentAnswerFindClass = classpath.findClass(cArr, strReplace, strReplace2, z);
                }
                if (nameEnvironmentAnswerFindClass != null) {
                    if (!nameEnvironmentAnswerFindClass.ignoreIfBetter()) {
                        if (nameEnvironmentAnswerFindClass.isBetter(nameEnvironmentAnswer2)) {
                            return nameEnvironmentAnswerFindClass;
                        }
                    } else if (nameEnvironmentAnswerFindClass.isBetter(nameEnvironmentAnswer2)) {
                        nameEnvironmentAnswer2 = nameEnvironmentAnswerFindClass;
                    }
                }
            }
            nameEnvironmentAnswer = nameEnvironmentAnswer2;
        }
        if (nameEnvironmentAnswer != null) {
            return nameEnvironmentAnswer;
        }
        return null;
    }

    @Override // org.eclipse.jdt.internal.compiler.env.INameEnvironment
    public NameEnvironmentAnswer findType(char[][] cArr) {
        if (cArr != null) {
            return findClass(new String(CharOperation.concatWith(cArr, '/')), cArr[cArr.length - 1], false);
        }
        return null;
    }

    public char[][][] findTypeNames(char[][] cArr) {
        char[][][] cArrFindTypeNames;
        char[][][] cArr2 = null;
        if (cArr != null) {
            String str = new String(CharOperation.concatWith(cArr, '/'));
            String strReplace = File.separatorChar == '/' ? str : str.replace('/', File.separatorChar);
            if (str == strReplace) {
                int length = this.classpaths.length;
                for (int i = 0; i < length; i++) {
                    char[][][] cArrFindTypeNames2 = this.classpaths[i].findTypeNames(str);
                    if (cArrFindTypeNames2 != null) {
                        if (cArr2 == null) {
                            cArr2 = cArrFindTypeNames2;
                        } else {
                            int length2 = cArr2.length;
                            int length3 = cArrFindTypeNames2.length;
                            char[][][] cArr3 = new char[length3 + length2][][];
                            System.arraycopy(cArr2, 0, cArr3, 0, length2);
                            System.arraycopy(cArrFindTypeNames2, 0, cArr3, length2, length3);
                            cArr2 = cArr3;
                        }
                    }
                }
            } else {
                int length4 = this.classpaths.length;
                for (int i2 = 0; i2 < length4; i2++) {
                    Classpath classpath = this.classpaths[i2];
                    if (classpath instanceof ClasspathJar) {
                        cArrFindTypeNames = classpath.findTypeNames(str);
                    } else {
                        cArrFindTypeNames = classpath.findTypeNames(strReplace);
                    }
                    if (cArrFindTypeNames != null) {
                        if (cArr2 == null) {
                            cArr2 = cArrFindTypeNames;
                        } else {
                            int length5 = cArr2.length;
                            int length6 = cArrFindTypeNames.length;
                            char[][][] cArr4 = new char[length6 + length5][][];
                            System.arraycopy(cArr2, 0, cArr4, 0, length5);
                            System.arraycopy(cArrFindTypeNames, 0, cArr4, length5, length6);
                            cArr2 = cArr4;
                        }
                    }
                }
            }
        }
        return cArr2;
    }

    public NameEnvironmentAnswer findType(char[][] cArr, boolean z) {
        if (cArr != null) {
            return findClass(new String(CharOperation.concatWith(cArr, '/')), cArr[cArr.length - 1], z);
        }
        return null;
    }

    @Override // org.eclipse.jdt.internal.compiler.env.INameEnvironment
    public NameEnvironmentAnswer findType(char[] cArr, char[][] cArr2) {
        if (cArr != null) {
            return findClass(new String(CharOperation.concatWith(cArr2, cArr, '/')), cArr, false);
        }
        return null;
    }

    @Override // org.eclipse.jdt.internal.compiler.env.INameEnvironment
    public boolean isPackage(char[][] cArr, char[] cArr2) {
        String str = new String(CharOperation.concatWith(cArr, cArr2, '/'));
        String strReplace = File.separatorChar == '/' ? str : str.replace('/', File.separatorChar);
        if (str == strReplace) {
            int length = this.classpaths.length;
            for (int i = 0; i < length; i++) {
                if (this.classpaths[i].isPackage(str)) {
                    return true;
                }
            }
        } else {
            int length2 = this.classpaths.length;
            for (int i2 = 0; i2 < length2; i2++) {
                Classpath classpath = this.classpaths[i2];
                if (classpath instanceof ClasspathJar) {
                    if (classpath.isPackage(str)) {
                        return true;
                    }
                } else {
                    if (classpath.isPackage(strReplace)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
