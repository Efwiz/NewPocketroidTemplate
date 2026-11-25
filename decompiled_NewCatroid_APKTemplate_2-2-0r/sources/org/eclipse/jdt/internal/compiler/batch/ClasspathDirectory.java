package org.eclipse.jdt.internal.compiler.batch;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import org.eclipse.jdt.core.compiler.CharOperation;
import org.eclipse.jdt.internal.compiler.CompilationResult;
import org.eclipse.jdt.internal.compiler.DefaultErrorHandlingPolicies;
import org.eclipse.jdt.internal.compiler.ast.CompilationUnitDeclaration;
import org.eclipse.jdt.internal.compiler.ast.TypeDeclaration;
import org.eclipse.jdt.internal.compiler.classfmt.ClassFileReader;
import org.eclipse.jdt.internal.compiler.classfmt.ClassFormatException;
import org.eclipse.jdt.internal.compiler.classfmt.ExternalAnnotationProvider;
import org.eclipse.jdt.internal.compiler.env.AccessRuleSet;
import org.eclipse.jdt.internal.compiler.env.NameEnvironmentAnswer;
import org.eclipse.jdt.internal.compiler.impl.CompilerOptions;
import org.eclipse.jdt.internal.compiler.lookup.TypeConstants;
import org.eclipse.jdt.internal.compiler.parser.Parser;
import org.eclipse.jdt.internal.compiler.parser.ScannerHelper;
import org.eclipse.jdt.internal.compiler.problem.DefaultProblemFactory;
import org.eclipse.jdt.internal.compiler.problem.ProblemReporter;
import org.eclipse.jdt.internal.compiler.util.SuffixConstants;
import org.eclipse.jdt.internal.compiler.util.Util;

/* JADX INFO: loaded from: classes14.dex */
public class ClasspathDirectory extends ClasspathLocation {
    private Hashtable directoryCache;
    private String encoding;
    private String[] missingPackageHolder;
    private int mode;
    Map options;
    private Hashtable<String, Hashtable<String, String>> packageSecondaryTypes;

    @Override // org.eclipse.jdt.internal.compiler.batch.FileSystem.Classpath
    public List fetchLinkedJars(FileSystem.ClasspathSectionProblemReporter classpathSectionProblemReporter) {
        return null;
    }

    @Override // org.eclipse.jdt.internal.compiler.batch.FileSystem.Classpath
    public void initialize() throws IOException {
    }

    ClasspathDirectory(File file, String str, int i, AccessRuleSet accessRuleSet, String str2, Map map) {
        super(accessRuleSet, str2);
        this.missingPackageHolder = new String[1];
        this.packageSecondaryTypes = null;
        this.mode = i;
        this.options = map;
        try {
            this.path = file.getCanonicalPath();
        } catch (IOException unused) {
            this.path = file.getAbsolutePath();
        }
        if (!this.path.endsWith(File.separator)) {
            this.path = String.valueOf(this.path) + File.separator;
        }
        this.directoryCache = new Hashtable(11);
        this.encoding = str;
    }

    String[] directoryList(String str) {
        String[] strArr = (String[]) this.directoryCache.get(str);
        if (strArr == this.missingPackageHolder) {
            return null;
        }
        if (strArr != null) {
            return strArr;
        }
        File file = new File(String.valueOf(this.path) + str);
        if (file.isDirectory()) {
            int length = str.length();
            int iLastIndexOf = str.lastIndexOf(File.separatorChar);
            do {
                length--;
                if (length <= iLastIndexOf) {
                    break;
                }
            } while (!ScannerHelper.isUpperCase(str.charAt(length)));
            if (length <= iLastIndexOf || (iLastIndexOf != -1 ? doesFileExist(str.substring(iLastIndexOf + 1), str.substring(0, iLastIndexOf)) : doesFileExist(str, Util.EMPTY_STRING))) {
                String[] list = file.list();
                if (list == null) {
                    list = CharOperation.NO_STRINGS;
                }
                this.directoryCache.put(str, list);
                return list;
            }
        }
        this.directoryCache.put(str, this.missingPackageHolder);
        return null;
    }

    boolean doesFileExist(String str, String str2) {
        String[] strArrDirectoryList = directoryList(str2);
        if (strArrDirectoryList == null) {
            return false;
        }
        int length = strArrDirectoryList.length;
        do {
            length--;
            if (length < 0) {
                return false;
            }
        } while (!str.equals(strArrDirectoryList[length]));
        return true;
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
        String str3 = new String(cArr);
        boolean z2 = (this.mode & 2) != 0 && doesFileExist(new StringBuilder(String.valueOf(str3)).append(".class").toString(), str);
        if ((1 & this.mode) != 0 && doesFileExist(String.valueOf(str3) + SuffixConstants.SUFFIX_STRING_java, str) && !z) {
            String str4 = String.valueOf(this.path) + str2.substring(0, str2.length() - 6) + SuffixConstants.SUFFIX_STRING_java;
            if (!z2) {
                return new NameEnvironmentAnswer(new CompilationUnit(null, str4, this.encoding, this.destinationPath), fetchAccessRestriction(str2));
            }
            if (new File(str4).lastModified() > new File(String.valueOf(this.path) + str2).lastModified()) {
                return new NameEnvironmentAnswer(new CompilationUnit(null, str4, this.encoding, this.destinationPath), fetchAccessRestriction(str2));
            }
        }
        if (z2) {
            try {
                ClassFileReader classFileReader = ClassFileReader.read(String.valueOf(this.path) + str2);
                if (str.length() > 0) {
                    str3 = String.valueOf(str.replace(File.separatorChar, '/')) + "/" + str3;
                }
                if (!CharOperation.equals(classFileReader.getName(), str3.toCharArray())) {
                    classFileReader = null;
                }
                if (classFileReader != null) {
                    return new NameEnvironmentAnswer(classFileReader, fetchAccessRestriction(str2));
                }
            } catch (IOException | ClassFormatException unused) {
            }
        }
        return null;
    }

    public NameEnvironmentAnswer findSecondaryInClass(char[] cArr, String str, String str2) {
        if (TypeConstants.PACKAGE_INFO_NAME.equals(cArr)) {
            return null;
        }
        String str3 = new String(cArr);
        if (this.options == null || !isPackage(str) || (this.mode & 1) == 0 || !doesFileExist(String.valueOf(str3) + SuffixConstants.SUFFIX_STRING_java, str)) {
            return findSourceSecondaryType(str3, str, str2);
        }
        return null;
    }

    @Override // org.eclipse.jdt.internal.compiler.batch.FileSystem.Classpath
    public boolean hasAnnotationFileFor(String str) {
        int i;
        int iLastIndexOf = str.lastIndexOf(47);
        if (iLastIndexOf == -1 || (i = iLastIndexOf + 1) >= str.length()) {
            return false;
        }
        return doesFileExist(String.valueOf(str.substring(i)) + ExternalAnnotationProvider.ANNOTATION_FILE_SUFFIX, str.substring(0, iLastIndexOf));
    }

    private Hashtable<String, String> getPackageTypes(String str) {
        String absolutePath;
        Hashtable<String, String> hashtable = new Hashtable<>();
        String[] strArr = (String[]) this.directoryCache.get(str);
        if (strArr == this.missingPackageHolder || strArr == null) {
            return hashtable;
        }
        File file = new File(String.valueOf(this.path) + str);
        File[] fileArrListFiles = file.isDirectory() ? file.listFiles() : null;
        if (fileArrListFiles == null) {
            return hashtable;
        }
        for (File file2 : fileArrListFiles) {
            if (!file2.isDirectory() && (absolutePath = file2.getAbsolutePath()) != null && (absolutePath.endsWith(SuffixConstants.SUFFIX_STRING_java) || absolutePath.endsWith(SuffixConstants.SUFFIX_STRING_JAVA))) {
                CompilationUnit compilationUnit = new CompilationUnit(null, absolutePath, this.encoding, this.destinationPath);
                CompilationResult compilationResult = new CompilationResult(compilationUnit.getContents(), 1, 1, 10);
                Parser parser = new Parser(new ProblemReporter(DefaultErrorHandlingPolicies.proceedWithAllProblems(), new CompilerOptions(this.options), new DefaultProblemFactory()), false);
                parser.reportSyntaxErrorIsRequired = false;
                CompilationUnitDeclaration compilationUnitDeclaration = parser.parse(compilationUnit, compilationResult);
                TypeDeclaration[] typeDeclarationArr = compilationUnitDeclaration != null ? compilationUnitDeclaration.types : null;
                if (typeDeclarationArr != null) {
                    for (TypeDeclaration typeDeclaration : typeDeclarationArr) {
                        char[] cArr = typeDeclaration.isSecondary() ? typeDeclaration.f619name : null;
                        if (cArr != null) {
                            hashtable.put(new String(cArr), absolutePath);
                        }
                    }
                }
            }
        }
        return hashtable;
    }

    private NameEnvironmentAnswer findSourceSecondaryType(String str, String str2, String str3) {
        if (this.packageSecondaryTypes == null) {
            this.packageSecondaryTypes = new Hashtable<>();
        }
        Hashtable<String, String> packageTypes = this.packageSecondaryTypes.get(str2);
        if (packageTypes == null) {
            packageTypes = getPackageTypes(str2);
            this.packageSecondaryTypes.put(str2, packageTypes);
        }
        String str4 = packageTypes.get(str);
        if (str4 != null) {
            return new NameEnvironmentAnswer(new CompilationUnit(null, str4, this.encoding, this.destinationPath), fetchAccessRestriction(str3));
        }
        return null;
    }

    @Override // org.eclipse.jdt.internal.compiler.batch.FileSystem.Classpath
    public char[][][] findTypeNames(String str) {
        String[] list;
        int length;
        if (!isPackage(str)) {
            return null;
        }
        File file = new File(String.valueOf(this.path) + str);
        if (!file.exists() || !file.isDirectory() || (list = file.list(new FilenameFilter() { // from class: org.eclipse.jdt.internal.compiler.batch.ClasspathDirectory.1
            @Override // java.io.FilenameFilter
            public boolean accept(File file2, String str2) {
                String lowerCase = str2.toLowerCase();
                return lowerCase.endsWith(".class") || lowerCase.endsWith(SuffixConstants.SUFFIX_STRING_java);
            }
        })) == null || (length = list.length) == 0) {
            return null;
        }
        char[][][] cArr = new char[length][][];
        char[][] cArrSplitOn = CharOperation.splitOn(File.separatorChar, str.toCharArray());
        for (int i = 0; i < length; i++) {
            String str2 = list[i];
            cArr[i] = CharOperation.arrayConcat(cArrSplitOn, str2.substring(0, str2.indexOf(46)).toCharArray());
        }
        return cArr;
    }

    @Override // org.eclipse.jdt.internal.compiler.batch.FileSystem.Classpath
    public boolean isPackage(String str) {
        return directoryList(str) != null;
    }

    @Override // org.eclipse.jdt.internal.compiler.batch.FileSystem.Classpath
    public void reset() {
        this.directoryCache = new Hashtable(11);
    }

    public String toString() {
        return "ClasspathDirectory " + this.path;
    }

    @Override // org.eclipse.jdt.internal.compiler.batch.FileSystem.Classpath
    public char[] normalizedPath() {
        if (this.normalizedPath == null) {
            this.normalizedPath = this.path.toCharArray();
            if (File.separatorChar == '\\') {
                CharOperation.replace(this.normalizedPath, '\\', '/');
            }
        }
        return this.normalizedPath;
    }

    @Override // org.eclipse.jdt.internal.compiler.batch.ClasspathLocation, org.eclipse.jdt.internal.compiler.batch.FileSystem.Classpath
    public String getPath() {
        return this.path;
    }

    @Override // org.eclipse.jdt.internal.compiler.batch.ClasspathLocation
    public int getMode() {
        return this.mode;
    }
}
