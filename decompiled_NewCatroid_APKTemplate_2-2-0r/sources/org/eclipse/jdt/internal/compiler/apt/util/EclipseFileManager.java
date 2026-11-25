package org.eclipse.jdt.internal.compiler.apt.util;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.charset.Charset;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.zip.ZipException;
import javax.tools.FileObject;
import javax.tools.JavaFileManager;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.StandardLocation;
import okhttp3.HttpUrl;
import org.apache.commons.lang3.SystemProperties;
import org.catrobat.catroid.common.Constants;
import org.eclipse.jdt.core.compiler.IProblem;
import org.eclipse.jdt.internal.compiler.batch.FileSystem;
import org.eclipse.jdt.internal.compiler.batch.Main;
import org.eclipse.jdt.internal.compiler.classfmt.ClassFileConstants;
import org.eclipse.jdt.internal.compiler.env.AccessRule;
import org.eclipse.jdt.internal.compiler.env.AccessRuleSet;
import org.eclipse.jdt.internal.compiler.impl.CompilerOptions;

/* JADX INFO: loaded from: classes14.dex */
public class EclipseFileManager implements StandardJavaFileManager {
    static final int HAS_BOOTCLASSPATH = 2;
    static final int HAS_ENDORSED_DIRS = 4;
    static final int HAS_EXT_DIRS = 1;
    static final int HAS_PROCESSORPATH = 8;
    private static final String NO_EXTENSION = "";
    Map<File, Archive> archivesCache;
    public ResourceBundle bundle;
    Charset charset;
    int flags;
    Locale locale;
    Map<String, Iterable<? extends File>> locations;

    public EclipseFileManager(Locale locale, Charset charset) {
        this.locale = locale == null ? Locale.getDefault() : locale;
        this.charset = charset == null ? Charset.defaultCharset() : charset;
        this.locations = new HashMap();
        this.archivesCache = new HashMap();
        try {
            setLocation(StandardLocation.PLATFORM_CLASS_PATH, getDefaultBootclasspath());
            Iterable<? extends File> defaultClasspath = getDefaultClasspath();
            setLocation(StandardLocation.CLASS_PATH, defaultClasspath);
            setLocation(StandardLocation.ANNOTATION_PROCESSOR_PATH, defaultClasspath);
        } catch (IOException unused) {
        }
        try {
            this.bundle = Main.ResourceBundleFactory.getBundle(this.locale);
        } catch (MissingResourceException unused2) {
            System.out.println("Missing resource : " + Main.bundleName.replace('.', '/') + ".properties for locale " + locale);
        }
    }

    public void close() throws IOException {
        Map<String, Iterable<? extends File>> map = this.locations;
        if (map != null) {
            map.clear();
        }
        Iterator<Archive> it = this.archivesCache.values().iterator();
        while (it.hasNext()) {
            it.next().close();
        }
        this.archivesCache.clear();
    }

    private void collectAllMatchingFiles(File file, String str, Set<JavaFileObject.Kind> set, boolean z, ArrayList<JavaFileObject> arrayList) {
        List<String> types;
        if (!isArchive(file)) {
            File file2 = new File(file, str);
            if (file2.exists()) {
                try {
                    String canonicalPath = file2.getCanonicalPath();
                    if (File.separatorChar == '/') {
                        if (!canonicalPath.endsWith(str)) {
                            return;
                        }
                    } else if (!canonicalPath.endsWith(str.replace('/', File.separatorChar))) {
                        return;
                    }
                    File[] fileArrListFiles = file2.listFiles();
                    if (fileArrListFiles != null) {
                        for (File file3 : fileArrListFiles) {
                            if (file3.isDirectory() && z) {
                                collectAllMatchingFiles(file, String.valueOf(str) + '/' + file3.getName(), set, z, arrayList);
                            } else {
                                JavaFileObject.Kind kind = getKind(file3);
                                if (set.contains(kind)) {
                                    arrayList.add(new EclipseFileObject(String.valueOf(str) + file3.getName(), file3.toURI(), kind, this.charset));
                                }
                            }
                        }
                        return;
                    }
                    return;
                } catch (IOException unused) {
                    return;
                }
            }
            return;
        }
        Archive archive = getArchive(file);
        if (archive == Archive.UNKNOWN_ARCHIVE) {
            return;
        }
        String str2 = !str.endsWith("/") ? String.valueOf(str) + '/' : str;
        if (z) {
            for (String str3 : archive.allPackages()) {
                if (str3.startsWith(str2) && (types = archive.getTypes(str3)) != null) {
                    for (String str4 : types) {
                        if (set.contains(getKind(getExtension(str4)))) {
                            arrayList.add(archive.getArchiveFileObject(String.valueOf(str3) + str4, this.charset));
                        }
                    }
                }
            }
            return;
        }
        List<String> types2 = archive.getTypes(str2);
        if (types2 != null) {
            for (String str5 : types2) {
                if (set.contains(getKind(getExtension(str5)))) {
                    arrayList.add(archive.getArchiveFileObject(String.valueOf(str2) + str5, this.charset));
                }
            }
        }
    }

    private Iterable<? extends File> concatFiles(Iterable<? extends File> iterable, Iterable<? extends File> iterable2) {
        ArrayList arrayList = new ArrayList();
        if (iterable2 == null) {
            return iterable;
        }
        Iterator<? extends File> it = iterable.iterator();
        while (it.hasNext()) {
            arrayList.add(it.next());
        }
        Iterator<? extends File> it2 = iterable2.iterator();
        while (it2.hasNext()) {
            arrayList.add(it2.next());
        }
        return arrayList;
    }

    public void flush() throws IOException {
        Iterator<Archive> it = this.archivesCache.values().iterator();
        while (it.hasNext()) {
            it.next().flush();
        }
    }

    private Archive getArchive(File file) {
        Archive archive = this.archivesCache.get(file);
        if (archive == null) {
            archive = Archive.UNKNOWN_ARCHIVE;
            if (file.exists()) {
                try {
                    archive = new Archive(file);
                } catch (ZipException | IOException unused) {
                }
                if (archive != null) {
                    this.archivesCache.put(file, archive);
                }
            }
            this.archivesCache.put(file, archive);
        }
        return archive;
    }

    public ClassLoader getClassLoader(JavaFileManager.Location location) {
        Iterable<? extends File> location2 = getLocation(location);
        if (location2 == null) {
            return null;
        }
        ArrayList arrayList = new ArrayList();
        Iterator<? extends File> it = location2.iterator();
        while (it.hasNext()) {
            try {
                arrayList.add(it.next().toURI().toURL());
            } catch (MalformedURLException e) {
                throw new RuntimeException(e);
            }
        }
        return new URLClassLoader((URL[]) arrayList.toArray(new URL[arrayList.size()]), getClass().getClassLoader());
    }

    private Iterable<? extends File> getPathsFrom(String str) {
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        try {
            processPathEntries(4, arrayList, str, this.charset.name(), false, false);
            Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                arrayList2.add(new File(((FileSystem.Classpath) it.next()).getPath()));
            }
            return arrayList2;
        } catch (IllegalArgumentException unused) {
            return null;
        }
    }

    Iterable<? extends File> getDefaultBootclasspath() {
        ArrayList arrayList = new ArrayList();
        String property = System.getProperty("java.version");
        if (property.length() > 3) {
            property = property.substring(0, 3);
        }
        if (CompilerOptions.versionToJdkLevel(property) < ClassFileConstants.JDK1_6) {
            return null;
        }
        Iterator<String> it = org.eclipse.jdt.internal.compiler.util.Util.collectFilesNames().iterator();
        while (it.hasNext()) {
            arrayList.add(new File(it.next()));
        }
        return arrayList;
    }

    Iterable<? extends File> getDefaultClasspath() {
        ArrayList arrayList = new ArrayList();
        String property = System.getProperty(SystemProperties.JAVA_CLASS_PATH);
        if (property == null || property.length() == 0) {
            return null;
        }
        StringTokenizer stringTokenizer = new StringTokenizer(property, File.pathSeparator);
        while (stringTokenizer.hasMoreTokens()) {
            File file = new File(stringTokenizer.nextToken());
            if (file.exists()) {
                arrayList.add(file);
            }
        }
        return arrayList;
    }

    private Iterable<? extends File> getEndorsedDirsFrom(String str) {
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        try {
            processPathEntries(4, arrayList, str, this.charset.name(), false, false);
            Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                arrayList2.add(new File(((FileSystem.Classpath) it.next()).getPath()));
            }
            return arrayList2;
        } catch (IllegalArgumentException unused) {
            return null;
        }
    }

    private Iterable<? extends File> getExtdirsFrom(String str) {
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        try {
            processPathEntries(4, arrayList, str, this.charset.name(), false, false);
            Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                arrayList2.add(new File(((FileSystem.Classpath) it.next()).getPath()));
            }
            return arrayList2;
        } catch (IllegalArgumentException unused) {
            return null;
        }
    }

    private String getExtension(File file) {
        return getExtension(file.getName());
    }

    private String getExtension(String str) {
        int iLastIndexOf = str.lastIndexOf(46);
        if (iLastIndexOf == -1) {
            return "";
        }
        return str.substring(iLastIndexOf);
    }

    public FileObject getFileForInput(JavaFileManager.Location location, String str, String str2) throws IOException {
        Archive archive;
        Iterable<? extends File> location2 = getLocation(location);
        if (location2 == null) {
            throw new IllegalArgumentException("Unknown location : " + location);
        }
        String strNormalizedFileName = normalizedFileName(str, str2);
        for (File file : location2) {
            if (file.isDirectory()) {
                File file2 = new File(file, strNormalizedFileName);
                if (file2.exists()) {
                    return new EclipseFileObject(String.valueOf(str) + File.separator + str2, file2.toURI(), getKind(file2), this.charset);
                }
            } else if (isArchive(file) && (archive = getArchive(file)) != Archive.UNKNOWN_ARCHIVE && archive.contains(strNormalizedFileName)) {
                return archive.getArchiveFileObject(strNormalizedFileName, this.charset);
            }
        }
        return null;
    }

    private String normalizedFileName(String str, String str2) {
        StringBuilder sb = new StringBuilder();
        sb.append(normalized(str));
        if (sb.length() > 0) {
            sb.append('/');
        }
        sb.append(str2.replace('\\', '/'));
        return sb.toString();
    }

    public FileObject getFileForOutput(JavaFileManager.Location location, String str, String str2, FileObject fileObject) throws IOException {
        Iterable<? extends File> location2 = getLocation(location);
        if (location2 == null) {
            throw new IllegalArgumentException("Unknown location : " + location);
        }
        Iterator<? extends File> it = location2.iterator();
        if (it.hasNext()) {
            File file = new File(it.next(), String.valueOf(normalized(str)) + '/' + str2.replace('\\', '/'));
            return new EclipseFileObject(String.valueOf(str) + File.separator + str2, file.toURI(), getKind(file), this.charset);
        }
        throw new IllegalArgumentException("location is empty : " + location);
    }

    public JavaFileObject getJavaFileForInput(JavaFileManager.Location location, String str, JavaFileObject.Kind kind) throws IOException {
        Archive archive;
        if (kind != JavaFileObject.Kind.CLASS && kind != JavaFileObject.Kind.SOURCE) {
            throw new IllegalArgumentException("Invalid kind : " + kind);
        }
        Iterable<? extends File> location2 = getLocation(location);
        if (location2 == null) {
            throw new IllegalArgumentException("Unknown location : " + location);
        }
        String str2 = String.valueOf(normalized(str)) + kind.extension;
        for (File file : location2) {
            if (file.isDirectory()) {
                File file2 = new File(file, str2);
                if (file2.exists()) {
                    return new EclipseFileObject(str, file2.toURI(), kind, this.charset);
                }
            } else if (isArchive(file) && (archive = getArchive(file)) != Archive.UNKNOWN_ARCHIVE && archive.contains(str2)) {
                return archive.getArchiveFileObject(str2, this.charset);
            }
        }
        return null;
    }

    public JavaFileObject getJavaFileForOutput(JavaFileManager.Location location, String str, JavaFileObject.Kind kind, FileObject fileObject) throws IOException {
        if (kind != JavaFileObject.Kind.CLASS && kind != JavaFileObject.Kind.SOURCE) {
            throw new IllegalArgumentException("Invalid kind : " + kind);
        }
        Iterable<? extends File> location2 = getLocation(location);
        if (location2 == null) {
            if (!location.equals(StandardLocation.CLASS_OUTPUT) && !location.equals(StandardLocation.SOURCE_OUTPUT)) {
                throw new IllegalArgumentException("Unknown location : " + location);
            }
            if (fileObject != null) {
                String strNormalized = normalized(str);
                int iLastIndexOf = strNormalized.lastIndexOf(47);
                if (iLastIndexOf != -1) {
                    strNormalized = strNormalized.substring(iLastIndexOf + 1);
                }
                String str2 = String.valueOf(strNormalized) + kind.extension;
                URI uri = fileObject.toUri();
                try {
                    String path = uri.getPath();
                    int iLastIndexOf2 = path.lastIndexOf(47);
                    if (iLastIndexOf2 != -1) {
                        path = String.valueOf(path.substring(0, iLastIndexOf2 + 1)) + str2;
                    }
                    return new EclipseFileObject(str, new URI(uri.getScheme(), uri.getHost(), path, uri.getFragment()), kind, this.charset);
                } catch (URISyntaxException unused) {
                    throw new IllegalArgumentException("invalid sibling");
                }
            }
            return new EclipseFileObject(str, new File(System.getProperty("user.dir"), String.valueOf(normalized(str)) + kind.extension).toURI(), kind, this.charset);
        }
        Iterator<? extends File> it = location2.iterator();
        if (it.hasNext()) {
            return new EclipseFileObject(str, new File(it.next(), String.valueOf(normalized(str)) + kind.extension).toURI(), kind, this.charset);
        }
        throw new IllegalArgumentException("location is empty : " + location);
    }

    public Iterable<? extends JavaFileObject> getJavaFileObjects(File... fileArr) {
        return getJavaFileObjectsFromFiles(Arrays.asList(fileArr));
    }

    public Iterable<? extends JavaFileObject> getJavaFileObjects(String... strArr) {
        return getJavaFileObjectsFromStrings(Arrays.asList(strArr));
    }

    public Iterable<? extends JavaFileObject> getJavaFileObjectsFromFiles(Iterable<? extends File> iterable) {
        ArrayList arrayList = new ArrayList();
        for (File file : iterable) {
            if (file.isDirectory()) {
                throw new IllegalArgumentException("file : " + file.getAbsolutePath() + " is a directory");
            }
            arrayList.add(new EclipseFileObject(file.getAbsolutePath(), file.toURI(), getKind(file), this.charset));
        }
        return arrayList;
    }

    public Iterable<? extends JavaFileObject> getJavaFileObjectsFromStrings(Iterable<String> iterable) {
        ArrayList arrayList = new ArrayList();
        Iterator<String> it = iterable.iterator();
        while (it.hasNext()) {
            arrayList.add(new File(it.next()));
        }
        return getJavaFileObjectsFromFiles(arrayList);
    }

    public JavaFileObject.Kind getKind(File file) {
        return getKind(getExtension(file));
    }

    private JavaFileObject.Kind getKind(String str) {
        if (JavaFileObject.Kind.CLASS.extension.equals(str)) {
            return JavaFileObject.Kind.CLASS;
        }
        if (JavaFileObject.Kind.SOURCE.extension.equals(str)) {
            return JavaFileObject.Kind.SOURCE;
        }
        if (JavaFileObject.Kind.HTML.extension.equals(str)) {
            return JavaFileObject.Kind.HTML;
        }
        return JavaFileObject.Kind.OTHER;
    }

    public Iterable<? extends File> getLocation(JavaFileManager.Location location) {
        Map<String, Iterable<? extends File>> map = this.locations;
        if (map == null) {
            return null;
        }
        return map.get(location.getName());
    }

    private Iterable<? extends File> getOutputDir(String str) {
        if ("none".equals(str)) {
            return null;
        }
        File file = new File(str);
        if (file.exists() && !file.isDirectory()) {
            throw new IllegalArgumentException("file : " + file.getAbsolutePath() + " is not a directory");
        }
        ArrayList arrayList = new ArrayList(1);
        arrayList.add(file);
        return arrayList;
    }

    public boolean handleOption(String str, Iterator<String> it) {
        try {
            if ("-bootclasspath".equals(str)) {
                if (it.hasNext()) {
                    Iterable<? extends File> pathsFrom = getPathsFrom(it.next());
                    if (pathsFrom != null) {
                        Iterable<? extends File> location = getLocation(StandardLocation.PLATFORM_CLASS_PATH);
                        int i = this.flags;
                        if ((i & 4) == 0 && (i & 1) == 0) {
                            setLocation(StandardLocation.PLATFORM_CLASS_PATH, pathsFrom);
                        } else if ((i & 4) != 0) {
                            setLocation(StandardLocation.PLATFORM_CLASS_PATH, concatFiles(location, pathsFrom));
                        } else {
                            setLocation(StandardLocation.PLATFORM_CLASS_PATH, prependFiles(location, pathsFrom));
                        }
                    }
                    this.flags |= 2;
                    return true;
                }
                throw new IllegalArgumentException();
            }
            if (!"-classpath".equals(str) && !"-cp".equals(str)) {
                if ("-encoding".equals(str)) {
                    if (it.hasNext()) {
                        this.charset = Charset.forName(it.next());
                        return true;
                    }
                    throw new IllegalArgumentException();
                }
                if ("-sourcepath".equals(str)) {
                    if (it.hasNext()) {
                        Iterable<? extends File> pathsFrom2 = getPathsFrom(it.next());
                        if (pathsFrom2 != null) {
                            setLocation(StandardLocation.SOURCE_PATH, pathsFrom2);
                        }
                        return true;
                    }
                    throw new IllegalArgumentException();
                }
                if ("-extdirs".equals(str)) {
                    if (it.hasNext()) {
                        setLocation(StandardLocation.PLATFORM_CLASS_PATH, concatFiles(getLocation(StandardLocation.PLATFORM_CLASS_PATH), getExtdirsFrom(it.next())));
                        this.flags |= 1;
                        return true;
                    }
                    throw new IllegalArgumentException();
                }
                if ("-endorseddirs".equals(str)) {
                    if (it.hasNext()) {
                        setLocation(StandardLocation.PLATFORM_CLASS_PATH, prependFiles(getLocation(StandardLocation.PLATFORM_CLASS_PATH), getEndorsedDirsFrom(it.next())));
                        this.flags |= 4;
                        return true;
                    }
                    throw new IllegalArgumentException();
                }
                if ("-d".equals(str)) {
                    if (it.hasNext()) {
                        Iterable<? extends File> outputDir = getOutputDir(it.next());
                        if (outputDir != null) {
                            setLocation(StandardLocation.CLASS_OUTPUT, outputDir);
                        }
                        return true;
                    }
                    throw new IllegalArgumentException();
                }
                if ("-s".equals(str)) {
                    if (it.hasNext()) {
                        Iterable<? extends File> outputDir2 = getOutputDir(it.next());
                        if (outputDir2 != null) {
                            setLocation(StandardLocation.SOURCE_OUTPUT, outputDir2);
                        }
                        return true;
                    }
                    throw new IllegalArgumentException();
                }
                if (!"-processorpath".equals(str)) {
                    return false;
                }
                if (it.hasNext()) {
                    Iterable<? extends File> pathsFrom3 = getPathsFrom(it.next());
                    if (pathsFrom3 != null) {
                        setLocation(StandardLocation.ANNOTATION_PROCESSOR_PATH, pathsFrom3);
                    }
                    this.flags |= 8;
                    return true;
                }
                throw new IllegalArgumentException();
            }
            if (it.hasNext()) {
                Iterable<? extends File> pathsFrom4 = getPathsFrom(it.next());
                if (pathsFrom4 != null) {
                    Iterable<? extends File> location2 = getLocation(StandardLocation.CLASS_PATH);
                    if (location2 != null) {
                        setLocation(StandardLocation.CLASS_PATH, concatFiles(location2, pathsFrom4));
                    } else {
                        setLocation(StandardLocation.CLASS_PATH, pathsFrom4);
                    }
                    if ((this.flags & 8) == 0) {
                        setLocation(StandardLocation.ANNOTATION_PROCESSOR_PATH, pathsFrom4);
                    }
                }
                return true;
            }
            throw new IllegalArgumentException();
        } catch (IOException unused) {
            return false;
        }
    }

    public boolean hasLocation(JavaFileManager.Location location) {
        Map<String, Iterable<? extends File>> map = this.locations;
        return map != null && map.containsKey(location.getName());
    }

    public String inferBinaryName(JavaFileManager.Location location, JavaFileObject javaFileObject) {
        JavaFileObject javaFileForInput;
        String name2 = javaFileObject.getName();
        int iLastIndexOf = name2.lastIndexOf(46);
        if (iLastIndexOf != -1) {
            name2 = name2.substring(0, iLastIndexOf);
        }
        try {
            javaFileForInput = getJavaFileForInput(location, name2, javaFileObject.getKind());
        } catch (IOException unused) {
            javaFileForInput = null;
        } catch (IllegalArgumentException unused2) {
            return null;
        }
        if (javaFileForInput == null) {
            return null;
        }
        return name2.replace('/', '.');
    }

    private boolean isArchive(File file) {
        String extension = getExtension(file);
        return extension.equalsIgnoreCase(".jar") || extension.equalsIgnoreCase(Constants.ZIP_EXTENSION);
    }

    public boolean isSameFile(FileObject fileObject, FileObject fileObject2) {
        if (!(fileObject instanceof EclipseFileObject)) {
            throw new IllegalArgumentException("Unsupported file object class : " + fileObject.getClass());
        }
        if (!(fileObject2 instanceof EclipseFileObject)) {
            throw new IllegalArgumentException("Unsupported file object class : " + fileObject2.getClass());
        }
        return fileObject.equals(fileObject2);
    }

    public int isSupportedOption(String str) {
        return Options.processOptionsFileManager(str);
    }

    public Iterable<JavaFileObject> list(JavaFileManager.Location location, String str, Set<JavaFileObject.Kind> set, boolean z) throws IOException {
        Iterable<? extends File> location2 = getLocation(location);
        if (location2 == null) {
            throw new IllegalArgumentException("Unknown location : " + location);
        }
        ArrayList<JavaFileObject> arrayList = new ArrayList<>();
        String strNormalized = normalized(str);
        Iterator<? extends File> it = location2.iterator();
        while (it.hasNext()) {
            collectAllMatchingFiles(it.next(), strNormalized, set, z, arrayList);
        }
        return arrayList;
    }

    private String normalized(String str) {
        char[] charArray = str.toCharArray();
        int length = charArray.length;
        for (int i = 0; i < length; i++) {
            char c = charArray[i];
            if (c == '.') {
                charArray[i] = '/';
            } else if (c == '\\') {
                charArray[i] = '/';
            }
        }
        return new String(charArray);
    }

    private Iterable<? extends File> prependFiles(Iterable<? extends File> iterable, Iterable<? extends File> iterable2) {
        if (iterable2 == null) {
            return iterable;
        }
        ArrayList arrayList = new ArrayList();
        Iterator<? extends File> it = iterable2.iterator();
        while (it.hasNext()) {
            arrayList.add(it.next());
        }
        Iterator<? extends File> it2 = iterable.iterator();
        while (it2.hasNext()) {
            arrayList.add(it2.next());
        }
        return arrayList;
    }

    public void setLocation(JavaFileManager.Location location, Iterable<? extends File> iterable) throws IOException {
        if (iterable != null) {
            if (location.isOutputLocation()) {
                Iterator<? extends File> it = iterable.iterator();
                int i = 0;
                while (it.hasNext()) {
                    it.next();
                    i++;
                }
                if (i != 1) {
                    throw new IllegalArgumentException("output location can only have one path");
                }
            }
            this.locations.put(location.getName(), iterable);
        }
    }

    public void setLocale(Locale locale) {
        Locale locale2 = locale == null ? Locale.getDefault() : locale;
        this.locale = locale2;
        try {
            this.bundle = Main.ResourceBundleFactory.getBundle(locale2);
        } catch (MissingResourceException e) {
            System.out.println("Missing resource : " + Main.bundleName.replace('.', '/') + ".properties for locale " + locale);
            throw e;
        }
    }

    /* JADX WARN: Code duplicated, block: B:30:0x007b  */
    /* JADX WARN: Code duplicated, block: B:31:0x007e  */
    public void processPathEntries(int i, ArrayList arrayList, String str, String str2, boolean z, boolean z2) {
        char c;
        char c2;
        int i2;
        ArrayList arrayList2 = new ArrayList(i);
        StringTokenizer stringTokenizer = new StringTokenizer(str, String.valueOf(File.pathSeparator) + HttpUrl.PATH_SEGMENT_ENCODE_SET_URI, true);
        ArrayList arrayList3 = new ArrayList();
        while (stringTokenizer.hasMoreTokens()) {
            arrayList3.add(stringTokenizer.nextToken());
        }
        int size = arrayList3.size();
        int i3 = 0;
        String str3 = null;
        String strTrim = null;
        int i4 = -1;
        char c3 = 0;
        while (i3 < size && c3 != 'c') {
            int i5 = i3 + 1;
            String str4 = (String) arrayList3.get(i3);
            if (str4.equals(File.pathSeparator)) {
                if (c3 == 0) {
                    i3 = i5;
                } else if (c3 == 1 || c3 == 2) {
                    addNewEntry(arrayList, str3, arrayList2, str2, strTrim, z, z2);
                    arrayList2.clear();
                    i3 = i5;
                    c3 = 3;
                } else if (c3 == 3) {
                    i3 = i5;
                } else if (c3 == 6) {
                    i3 = i5;
                    c3 = 4;
                } else {
                    if (c3 == 7) {
                        throw new IllegalArgumentException(bind("configure.incorrectDestinationPathEntry", str));
                    }
                    if (c3 != '\b') {
                        if (c3 != '\n') {
                            if (c3 != 11) {
                                c = 11;
                                i3 = i5;
                                c3 = 'c';
                                c2 = 5;
                            } else {
                                i3 = i4 + 1;
                                c = 11;
                                c3 = 5;
                                c2 = 5;
                            }
                        }
                        i3 = i5;
                    } else {
                        addNewEntry(arrayList, str3, arrayList2, str2, strTrim, z, z2);
                        arrayList2.clear();
                        i3 = i5;
                        c3 = 3;
                    }
                }
                c = 11;
                c2 = 5;
            } else if (str4.equals("[")) {
                if (c3 == 0) {
                    c = 11;
                    str3 = "";
                } else if (c3 == 1) {
                    c = 11;
                } else if (c3 == 2) {
                    c = 11;
                    c3 = '\t';
                    i3 = i5;
                    c2 = 5;
                } else if (c3 != '\b') {
                    c = 11;
                    if (c3 == 11) {
                        i3 = i4;
                    }
                    i3 = i5;
                    c3 = 'c';
                    c2 = 5;
                } else {
                    c = 11;
                    i3 = i5;
                    c3 = 5;
                    c2 = 5;
                }
                i4 = i3;
                i3 = i5;
                c3 = '\n';
                c2 = 5;
            } else {
                c = 11;
                if (str4.equals("]")) {
                    if (c3 != 6) {
                        if (c3 != 7) {
                            if (c3 == '\n') {
                                c3 = 11;
                            }
                            i3 = i5;
                            c3 = 'c';
                        } else {
                            i3 = i5;
                            c3 = '\b';
                        }
                        c2 = 5;
                    } else {
                        c3 = 2;
                    }
                    i3 = i5;
                    c2 = 5;
                } else {
                    if (c3 == 0 || c3 == 3) {
                        c2 = 5;
                        i3 = i5;
                        str3 = str4;
                    } else {
                        if (c3 != 4) {
                            c2 = 5;
                            if (c3 == 5) {
                                i2 = i5;
                                if (str4.startsWith("-d ")) {
                                    if (strTrim != null) {
                                        throw new IllegalArgumentException(bind("configure.duplicateDestinationPathEntry", str));
                                    }
                                    strTrim = str4.substring(3).trim();
                                    i3 = i2;
                                }
                            } else {
                                switch (c3) {
                                    case '\t':
                                        i3 = i5;
                                        if (str4.startsWith("-d ")) {
                                            strTrim = str4.substring(3).trim();
                                        } else {
                                            c3 = 'c';
                                        }
                                        break;
                                    case '\n':
                                        i3 = i5;
                                        break;
                                    case 11:
                                        i3 = i5;
                                        for (int i6 = i4; i6 < i3; i6++) {
                                            str3 = String.valueOf(str3) + ((String) arrayList3.get(i6));
                                        }
                                        break;
                                    default:
                                        i3 = i5;
                                        c3 = 'c';
                                        break;
                                }
                            }
                            c3 = 7;
                        } else {
                            i2 = i5;
                            c2 = 5;
                        }
                        if (strTrim != null) {
                            throw new IllegalArgumentException(bind("configure.accessRuleAfterDestinationPath", str));
                        }
                        arrayList2.add(str4);
                        int i7 = i2;
                        c3 = 6;
                        i3 = i7;
                    }
                    c3 = 1;
                }
            }
            if (c3 == c && i3 == size) {
                i3 = i4 + 1;
                c3 = c2;
            }
        }
        if (c3 == 1 || c3 == 2 || c3 == '\b') {
            addNewEntry(arrayList, str3, arrayList2, str2, strTrim, z, z2);
        }
    }

    protected void addNewEntry(ArrayList arrayList, String str, ArrayList arrayList2, String str2, String str3, boolean z, boolean z2) {
        AccessRuleSet accessRuleSet;
        int i;
        if (arrayList2.size() != 0) {
            AccessRule[] accessRuleArr = new AccessRule[arrayList2.size()];
            Iterator it = arrayList2.iterator();
            int i2 = 0;
            boolean z3 = true;
            while (it.hasNext()) {
                String str4 = (String) it.next();
                char cCharAt = str4.charAt(0);
                String strSubstring = str4.substring(1);
                if (strSubstring.length() > 0) {
                    if (cCharAt == '+') {
                        i = i2 + 1;
                        accessRuleArr[i2] = new AccessRule(strSubstring.toCharArray(), 0);
                    } else if (cCharAt == '-') {
                        i = i2 + 1;
                        accessRuleArr[i2] = new AccessRule(strSubstring.toCharArray(), IProblem.ForbiddenReference);
                    } else if (cCharAt == '?') {
                        i = i2 + 1;
                        accessRuleArr[i2] = new AccessRule(strSubstring.toCharArray(), IProblem.ForbiddenReference, true);
                    } else if (cCharAt == '~') {
                        i = i2 + 1;
                        accessRuleArr[i2] = new AccessRule(strSubstring.toCharArray(), IProblem.DiscouragedReference);
                    }
                    i2 = i;
                }
                z3 = false;
            }
            if (!z3) {
                return;
            } else {
                accessRuleSet = new AccessRuleSet(accessRuleArr, (byte) 0, str);
            }
        } else {
            accessRuleSet = null;
        }
        String str5 = "none".equals(str3) ? "none" : str3;
        if (z2 && str5 != null && (str.endsWith(".jar") || str.endsWith(Constants.ZIP_EXTENSION))) {
            throw new IllegalArgumentException(bind("configure.unexpectedDestinationPathEntryFile", str));
        }
        FileSystem.Classpath classpath = FileSystem.getClasspath(str, str2, z, accessRuleSet, str5, null);
        if (classpath != null) {
            arrayList.add(classpath);
        }
    }

    private String bind(String str, String str2) {
        return bind(str, new String[]{str2});
    }

    private String bind(String str, String[] strArr) {
        if (str == null) {
            return "No message available";
        }
        try {
            return MessageFormat.format(this.bundle.getString(str), strArr);
        } catch (MissingResourceException unused) {
            return "Missing message: " + str + " in: org.eclipse.jdt.internal.compiler.batch.messages";
        }
    }
}
