package org.jetbrains.kotlin.com.intellij.openapi.application;

import java.io.File;
import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import name.antonsmirnov.firmata.FormatHelper;
import org.eclipse.jgit.lib.Constants;
import org.jetbrains.kotlin.com.intellij.openapi.project.Project;
import org.jetbrains.kotlin.com.intellij.openapi.util.SystemInfoRt;
import org.jetbrains.kotlin.com.intellij.openapi.util.text.StringUtilRt;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.StandardFileSystems;
import org.jetbrains.kotlin.com.intellij.psi.PsiTreeChangeEvent;

/* JADX INFO: loaded from: classes3.dex */
public final class PathManager {
    private static final String PATHS_SELECTOR = System.getProperty("idea.paths.selector");
    private static volatile List<Path> ourBinDirectories;
    private static String ourConfigPath;
    private static volatile String ourHomePath;
    private static String ourSystemPath;

    /* JADX WARN: Code duplicated, block: B:22:0x0044 A[FALL_THROUGH] */
    /* JADX WARN: Code duplicated, block: B:77:0x00ea  */
    /* JADX WARN: Code duplicated, block: B:78:0x00ef  */
    /* JADX WARN: Code duplicated, block: B:79:0x00f4  */
    /* JADX WARN: Code duplicated, block: B:81:0x00fe  */
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str;
        int i2;
        if (i != 1 && i != 2 && i != 3 && i != 5 && i != 6 && i != 17 && i != 20 && i != 24 && i != 28 && i != 37 && i != 48 && i != 64 && i != 66 && i != 30 && i != 31) {
            switch (i) {
                default:
                    switch (i) {
                        case 54:
                        case 55:
                        case 56:
                        case 57:
                        case 58:
                        case 59:
                        case 60:
                        case 61:
                            break;
                        default:
                            str = "@NotNull method %s.%s must not return null";
                            break;
                    }
                case 40:
                case 41:
                case 42:
                case 43:
                case 44:
                case 45:
                case 46:
                    str = "Argument for @NotNull parameter '%s' of %s.%s must not be null";
                    break;
            }
        } else {
            str = "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        }
        String str2 = str;
        if (i != 1 && i != 2 && i != 3 && i != 5 && i != 6 && i != 17 && i != 20 && i != 24 && i != 28 && i != 37 && i != 48 && i != 64 && i != 66 && i != 30 && i != 31) {
            switch (i) {
                case 40:
                case 41:
                case 42:
                case 43:
                case 44:
                case 45:
                case 46:
                    i2 = 3;
                    break;
                default:
                    switch (i) {
                        case 54:
                        case 55:
                        case 56:
                        case 57:
                        case 58:
                        case 59:
                        case 60:
                        case 61:
                            i2 = 3;
                            break;
                        default:
                            i2 = 2;
                            break;
                    }
                    break;
            }
        } else {
            i2 = 3;
        }
        Object[] objArr = new Object[i2];
        if (i == 1) {
            objArr[0] = "path";
        } else if (i == 2) {
            objArr[0] = "target";
        } else if (i == 3) {
            objArr[0] = "aClass";
        } else if (i == 5 || i == 6) {
            objArr[0] = PsiTreeChangeEvent.PROP_FILE_NAME;
        } else if (i == 17) {
            objArr[0] = "selector";
        } else if (i == 20) {
            objArr[0] = PsiTreeChangeEvent.PROP_FILE_NAME;
        } else if (i == 24 || i == 28 || i == 37) {
            objArr[0] = "selector";
        } else if (i == 48) {
            objArr[0] = "homePath";
        } else if (i == 64) {
            objArr[0] = "path";
        } else if (i == 66) {
            objArr[0] = "property";
        } else if (i == 30) {
            objArr[0] = "userHome";
        } else if (i != 31) {
            switch (i) {
                case 40:
                    objArr[0] = "context";
                    break;
                case 41:
                    objArr[0] = "path";
                    break;
                case 42:
                    objArr[0] = "classLoader";
                    break;
                case 43:
                    objArr[0] = "resourcePath";
                    break;
                case 44:
                    objArr[0] = "url";
                    break;
                case 45:
                    objArr[0] = "ideaHomePath";
                    break;
                case 46:
                    objArr[0] = "relativePath";
                    break;
                default:
                    switch (i) {
                        case 54:
                        case 55:
                            objArr[0] = "aClass";
                            break;
                        case 56:
                        case 58:
                            objArr[0] = "classesRoot";
                            break;
                        case 57:
                            objArr[0] = "artifactName";
                            break;
                        case 59:
                            objArr[0] = "artifactFileName";
                            break;
                        case 60:
                            objArr[0] = "artifactDirNameInPluginLayout";
                            break;
                        case 61:
                            objArr[0] = "artifactDirNameInBuildLayout";
                            break;
                        default:
                            objArr[0] = "org/jetbrains/kotlin/com/intellij/openapi/application/PathManager";
                            break;
                    }
                    break;
            }
        } else {
            objArr[0] = "pathsSelector";
        }
        switch (i) {
            case 1:
            case 2:
            case 3:
            case 5:
            case 6:
            case 17:
            case 20:
            case 24:
            case 28:
            case 30:
            case 31:
            case 37:
            case 40:
            case 41:
            case 42:
            case 43:
            case 44:
            case 45:
            case 46:
            case 48:
            case 54:
            case 55:
            case 56:
            case 57:
            case 58:
            case 59:
            case 60:
            case 61:
            case 64:
            case 66:
                objArr[1] = "org/jetbrains/kotlin/com/intellij/openapi/application/PathManager";
                break;
            case 4:
                objArr[1] = "getBinPath";
                break;
            case 7:
                objArr[1] = "findBinFileWithException";
                break;
            case 8:
                objArr[1] = "getLibPath";
                break;
            case 9:
                objArr[1] = "getPreInstalledPluginsPath";
                break;
            case 10:
                objArr[1] = "getBundledRuntimePath";
                break;
            case 11:
                objArr[1] = "getCommonDataPath";
                break;
            case 12:
                objArr[1] = "getConfigDir";
                break;
            case 13:
            case 14:
                objArr[1] = "getConfigPath";
                break;
            case 15:
            case 16:
                objArr[1] = "getScratchPath";
                break;
            case 18:
                objArr[1] = "getDefaultConfigPathFor";
                break;
            case 19:
                objArr[1] = "getOptionsPath";
                break;
            case 21:
                objArr[1] = "getOptionsFile";
                break;
            case 22:
            case 23:
                objArr[1] = "getPluginsPath";
                break;
            case 25:
                objArr[1] = "getDefaultPluginPathFor";
                break;
            case 26:
            case 27:
                objArr[1] = "getSystemPath";
                break;
            case 29:
                objArr[1] = "getDefaultSystemPathFor";
                break;
            case 32:
                objArr[1] = "getDefaultUnixSystemPath";
                break;
            case 33:
                objArr[1] = "getTempPath";
                break;
            case 34:
                objArr[1] = "getIndexRoot";
                break;
            case 35:
            case 36:
                objArr[1] = "getLogPath";
                break;
            case 38:
                objArr[1] = "getDefaultLogPathFor";
                break;
            case 39:
                objArr[1] = "getPluginTempPath";
                break;
            case 47:
                objArr[1] = "findFileInLibDirectory";
                break;
            case 49:
            case 50:
            case 51:
            case 52:
            case 53:
                objArr[1] = "getCommunityHomePath";
                break;
            case 62:
            case 63:
                objArr[1] = "getArtifactPath";
                break;
            case 65:
                objArr[1] = "getAbsolutePath";
                break;
            default:
                objArr[1] = "getHomePath";
                break;
        }
        if (i == 1 || i == 2) {
            objArr[2] = "isUnderHomeDirectory";
        } else if (i == 3) {
            objArr[2] = "getHomePathFor";
        } else if (i == 5) {
            objArr[2] = "findBinFile";
        } else if (i == 6) {
            objArr[2] = "findBinFileWithException";
        } else if (i == 17) {
            objArr[2] = "getDefaultConfigPathFor";
        } else if (i == 20) {
            objArr[2] = "getOptionsFile";
        } else if (i == 24) {
            objArr[2] = "getDefaultPluginPathFor";
        } else if (i == 28) {
            objArr[2] = "getDefaultSystemPathFor";
        } else if (i == 37) {
            objArr[2] = "getDefaultLogPathFor";
        } else if (i == 48) {
            objArr[2] = "getCommunityHomePath";
        } else if (i == 64) {
            objArr[2] = "getAbsolutePath";
        } else if (i == 66) {
            objArr[2] = "getExplicitPath";
        } else if (i != 30 && i != 31) {
            switch (i) {
                case 40:
                case 41:
                case 42:
                case 43:
                    objArr[2] = "getResourceRoot";
                    break;
                case 44:
                    objArr[2] = "splitJarUrl";
                    break;
                case 45:
                    objArr[2] = "substituteVars";
                    break;
                case 46:
                    objArr[2] = "findFileInLibDirectory";
                    break;
                default:
                    switch (i) {
                        case 54:
                            objArr[2] = "getJarPathForClass";
                            break;
                        case 55:
                            objArr[2] = "getJarForClass";
                            break;
                        case 56:
                        case 57:
                            objArr[2] = "getJarArtifactPath";
                            break;
                        case 58:
                        case 59:
                        case 60:
                        case 61:
                            objArr[2] = "getArtifactPath";
                            break;
                    }
                    break;
            }
        } else {
            objArr[2] = "getDefaultUnixSystemPath";
        }
        String str3 = String.format(str2, objArr);
        if (i != 1 && i != 2 && i != 3 && i != 5 && i != 6 && i != 17 && i != 20 && i != 24 && i != 28 && i != 37 && i != 48 && i != 64 && i != 66 && i != 30 && i != 31) {
            switch (i) {
                case 40:
                case 41:
                case 42:
                case 43:
                case 44:
                case 45:
                case 46:
                    break;
                default:
                    switch (i) {
                        case 54:
                        case 55:
                        case 56:
                        case 57:
                        case 58:
                        case 59:
                        case 60:
                        case 61:
                            break;
                        default:
                            throw new IllegalStateException(str3);
                    }
                    break;
            }
        }
        throw new IllegalArgumentException(str3);
    }

    public static String getHomePath() {
        String homePath = getHomePath(true);
        if (homePath == null) {
            $$$reportNull$$$0(0);
        }
        return homePath;
    }

    public static String getHomePath(boolean z) {
        String str = ourHomePath;
        if (str != null) {
            return str;
        }
        synchronized (PathManager.class) {
            String homePathFor = ourHomePath;
            if (homePathFor != null) {
                return homePathFor;
            }
            String explicitPath = getExplicitPath("idea.home.path");
            if (explicitPath == null) {
                explicitPath = getExplicitPath("idea.home");
            }
            if (explicitPath != null) {
                if (!Files.isDirectory(Paths.get(explicitPath, new String[0]), new LinkOption[0])) {
                    ourHomePath = explicitPath;
                    throw new RuntimeException("Invalid home path '" + explicitPath + FormatHelper.QUOTE);
                }
                homePathFor = explicitPath;
            } else if (z && (homePathFor = getHomePathFor(PathManager.class)) == null) {
                throw new RuntimeException("Could not find installation home path. Please ".concat(SystemInfoRt.isMac ? "reinstall the software." : "make sure bin/idea.properties is present in the installation directory."));
            }
            if (homePathFor != null && SystemInfoRt.isWindows) {
                try {
                    homePathFor = Paths.get(homePathFor, new String[0]).toRealPath(LinkOption.NOFOLLOW_LINKS).toString();
                } catch (IOException unused) {
                }
            }
            ourBinDirectories = homePathFor == null ? Collections.emptyList() : getBinDirectories(Paths.get(homePathFor, new String[0]));
            ourHomePath = homePathFor;
            return homePathFor;
        }
    }

    private static List<Path> getBinDirectories() {
        List<Path> list = ourBinDirectories;
        if (list != null) {
            return list;
        }
        getHomePath(true);
        return ourBinDirectories;
    }

    public static String getHomePathFor(Class<?> cls) {
        if (cls == null) {
            $$$reportNull$$$0(3);
        }
        String resourceRoot = getResourceRoot(cls, "/" + cls.getName().replace('.', '/') + ".class");
        if (resourceRoot == null) {
            return null;
        }
        Path absolutePath = Paths.get(resourceRoot, new String[0]).toAbsolutePath();
        do {
            absolutePath = absolutePath.getParent();
            if (absolutePath == null) {
                break;
            }
        } while (!isIdeaHome(absolutePath));
        if (absolutePath != null) {
            return absolutePath.toString();
        }
        return null;
    }

    private static boolean isIdeaHome(Path path) {
        Iterator<Path> it = getBinDirectories(path).iterator();
        while (it.hasNext()) {
            if (Files.isRegularFile(it.next().resolve("idea.properties"), new LinkOption[0])) {
                return true;
            }
        }
        return false;
    }

    private static List<Path> getBinDirectories(Path path) {
        ArrayList arrayList = new ArrayList();
        Path[] pathArr = {path.resolve("bin"), Paths.get(getCommunityHomePath(path.toString()), "bin")};
        String str = SystemInfoRt.isWindows ? "win" : SystemInfoRt.isMac ? "mac" : "linux";
        for (int i = 0; i < 2; i++) {
            Path path2 = pathArr[i];
            if (!arrayList.contains(path2) && Files.isDirectory(path2, new LinkOption[0])) {
                arrayList.add(path2);
                Path pathResolve = path2.resolve(str);
                if (Files.isDirectory(pathResolve, new LinkOption[0])) {
                    arrayList.add(pathResolve);
                }
            }
        }
        return arrayList;
    }

    public static String getBinPath() {
        String str = getHomePath() + "/bin";
        if (str == null) {
            $$$reportNull$$$0(4);
        }
        return str;
    }

    public static Path findBinFile(String str) {
        if (str == null) {
            $$$reportNull$$$0(5);
        }
        Iterator<Path> it = getBinDirectories().iterator();
        while (it.hasNext()) {
            Path pathResolve = it.next().resolve(str);
            if (Files.isRegularFile(pathResolve, new LinkOption[0])) {
                return pathResolve;
            }
        }
        return null;
    }

    public static String getConfigPath() {
        String str = ourConfigPath;
        if (str != null) {
            if (str == null) {
                $$$reportNull$$$0(13);
            }
            return str;
        }
        String explicitPath = getExplicitPath("idea.config.path");
        if (explicitPath != null) {
            ourConfigPath = explicitPath;
        } else {
            String str2 = PATHS_SELECTOR;
            if (str2 != null) {
                ourConfigPath = getDefaultConfigPathFor(str2);
            } else {
                ourConfigPath = getHomePath() + "/config";
            }
        }
        String str3 = ourConfigPath;
        if (str3 == null) {
            $$$reportNull$$$0(14);
        }
        return str3;
    }

    public static String getDefaultConfigPathFor(String str) {
        if (str == null) {
            $$$reportNull$$$0(17);
        }
        String strPlatformPath = platformPath(str, "Application Support", "", "APPDATA", "", Constants.XDG_CONFIG_HOME, ".config", "");
        if (strPlatformPath == null) {
            $$$reportNull$$$0(18);
        }
        return strPlatformPath;
    }

    public static String getSystemPath() {
        String str = ourSystemPath;
        if (str != null) {
            if (str == null) {
                $$$reportNull$$$0(26);
            }
            return str;
        }
        String explicitPath = getExplicitPath("idea.system.path");
        if (explicitPath != null) {
            ourSystemPath = explicitPath;
        } else {
            String str2 = PATHS_SELECTOR;
            if (str2 != null) {
                ourSystemPath = getDefaultSystemPathFor(str2);
            } else {
                ourSystemPath = getHomePath() + "/system";
            }
        }
        String str3 = ourSystemPath;
        if (str3 == null) {
            $$$reportNull$$$0(27);
        }
        return str3;
    }

    public static String getDefaultSystemPathFor(String str) {
        if (str == null) {
            $$$reportNull$$$0(28);
        }
        String strPlatformPath = platformPath(str, "Caches", "", "LOCALAPPDATA", "", "XDG_CACHE_HOME", ".cache", "");
        if (strPlatformPath == null) {
            $$$reportNull$$$0(29);
        }
        return strPlatformPath;
    }

    public static Path getIndexRoot() {
        String explicitPath = getExplicitPath("index_root_path");
        if (explicitPath == null) {
            explicitPath = getSystemPath() + "/index";
        }
        Path path = Paths.get(explicitPath, new String[0]);
        if (path == null) {
            $$$reportNull$$$0(34);
        }
        return path;
    }

    public static String getResourceRoot(Class<?> cls, String str) {
        if (cls == null) {
            $$$reportNull$$$0(40);
        }
        if (str == null) {
            $$$reportNull$$$0(41);
        }
        URL resource = cls.getResource(str);
        if (resource == null) {
            resource = ClassLoader.getSystemResource(str.substring(1));
        }
        if (resource != null) {
            return extractRoot(resource, str);
        }
        return null;
    }

    public static String getResourceRoot(ClassLoader classLoader, String str) {
        if (classLoader == null) {
            $$$reportNull$$$0(42);
        }
        if (str == null) {
            $$$reportNull$$$0(43);
        }
        URL resource = classLoader.getResource(str);
        if (resource == null) {
            return null;
        }
        return extractRoot(resource, "/" + str);
    }

    private static String extractRoot(URL url, String str) {
        String strSubstring;
        if (str.length() == 0 || (str.charAt(0) != '/' && str.charAt(0) != '\\')) {
            log("precondition failed: " + str);
            return null;
        }
        String protocol = url.getProtocol();
        if ("file".equals(protocol)) {
            try {
                String path = new File(url.toURI().getSchemeSpecificPart()).getPath();
                strSubstring = StringUtilRt.endsWithIgnoreCase(path.replace('\\', '/'), str.replace('\\', '/')) ? path.substring(0, path.length() - str.length()) : null;
            } catch (URISyntaxException e) {
                throw new IllegalArgumentException("URL='" + url + FormatHelper.QUOTE, e);
            }
        } else {
            if ("jar".equals(protocol)) {
                strSubstring = splitJarUrl(url.getFile());
                if (strSubstring == null) {
                }
            } else if (StandardFileSystems.JRT_PROTOCOL.equals(protocol)) {
                return null;
            }
        }
        if (strSubstring == null) {
            log("cannot extract '" + str + "' from '" + url + FormatHelper.QUOTE);
            return null;
        }
        return Paths.get(strSubstring, new String[0]).normalize().toString();
    }

    private static String splitJarUrl(String str) {
        if (str == null) {
            $$$reportNull$$$0(44);
        }
        int iIndexOf = str.indexOf("!/");
        if (iIndexOf < 0) {
            return null;
        }
        String strSubstring = str.substring(0, iIndexOf);
        String[] strArr = {"jar", ":"};
        int i = 0;
        int i2 = 0;
        while (true) {
            if (i < 2) {
                String str2 = strArr[i];
                int length = str2.length();
                if (!strSubstring.regionMatches(i2, str2, 0, length)) {
                    break;
                }
                i2 += length;
                i++;
            } else {
                strSubstring = strSubstring.substring("jar".length() + 1);
                break;
            }
        }
        if (!strSubstring.startsWith("file")) {
            return strSubstring;
        }
        try {
            URL url = new URL(strSubstring);
            try {
                return new File(url.toURI().getSchemeSpecificPart()).getPath().replace('\\', '/');
            } catch (URISyntaxException e) {
                throw new IllegalArgumentException("URL='" + url + FormatHelper.QUOTE, e);
            }
        } catch (Exception unused) {
            String strSubstring2 = strSubstring.substring("file".length());
            if (strSubstring2.startsWith("://")) {
                return strSubstring2.substring("://".length());
            }
            return (strSubstring2.isEmpty() || strSubstring2.charAt(0) != ':') ? strSubstring2 : strSubstring2.substring(1);
        }
    }

    public static String getCommunityHomePath() {
        return getCommunityHomePath(getHomePath());
    }

    private static String getCommunityHomePath(String str) {
        if (str == null) {
            $$$reportNull$$$0(48);
        }
        if (!Files.isDirectory(Paths.get(str, Project.DIRECTORY_STORE_FOLDER), new LinkOption[0])) {
            if (str == null) {
                $$$reportNull$$$0(49);
            }
            return str;
        }
        if (Files.isDirectory(Paths.get(str, "community/.idea"), new LinkOption[0])) {
            String str2 = str + "/community";
            if (str2 == null) {
                $$$reportNull$$$0(50);
            }
            return str2;
        }
        if (Files.isDirectory(Paths.get(str, "ultimate/community/.idea"), new LinkOption[0])) {
            String str3 = str + "/ultimate/community";
            if (str3 == null) {
                $$$reportNull$$$0(51);
            }
            return str3;
        }
        if (!Files.isRegularFile(Paths.get(str, "../../Product.Root"), new LinkOption[0])) {
            if (str == null) {
                $$$reportNull$$$0(53);
            }
            return str;
        }
        String str4 = str + "/../ultimate/community";
        if (str4 == null) {
            $$$reportNull$$$0(52);
        }
        return str4;
    }

    public static String getJarPathForClass(Class<?> cls) {
        if (cls == null) {
            $$$reportNull$$$0(54);
        }
        Path jarForClass = getJarForClass(cls);
        if (jarForClass == null) {
            return null;
        }
        return jarForClass.toString();
    }

    public static Path getJarForClass(Class<?> cls) {
        if (cls == null) {
            $$$reportNull$$$0(55);
        }
        String resourceRoot = getResourceRoot(cls, "/" + cls.getName().replace('.', '/') + ".class");
        if (resourceRoot == null) {
            return null;
        }
        return Paths.get(resourceRoot, new String[0]).toAbsolutePath();
    }

    private static void log(String str) {
        System.err.println(str);
    }

    public static String getAbsolutePath(String str) {
        if (str == null) {
            $$$reportNull$$$0(64);
        }
        if (str.startsWith("~/") || str.startsWith("~\\")) {
            str = System.getProperty("user.home") + str.substring(1);
        }
        String string = Paths.get(str, new String[0]).toAbsolutePath().normalize().toString();
        if (string == null) {
            $$$reportNull$$$0(65);
        }
        return string;
    }

    private static String getExplicitPath(String str) {
        if (str == null) {
            $$$reportNull$$$0(66);
        }
        String property = System.getProperty(str);
        if (property == null) {
            return null;
        }
        if (property.length() > 1 && '\"' == property.charAt(0) && '\"' == property.charAt(property.length() - 1)) {
            property = property.substring(1, property.length() - 1);
        }
        return getAbsolutePath(property);
    }

    private static String platformPath(String str, String str2, String str3, String str4, String str5, String str6, String str7, String str8) {
        String property = System.getProperty("user.home");
        String strVendorName = vendorName();
        if (SystemInfoRt.isMac) {
            String str9 = property + "/Library/" + str2 + '/' + strVendorName;
            if (!str.isEmpty()) {
                str9 = str9 + '/' + str;
            }
            return !str3.isEmpty() ? str9 + '/' + str3 : str9;
        }
        if (SystemInfoRt.isWindows) {
            String str10 = System.getenv(str4);
            if (str10 == null || str10.isEmpty()) {
                str10 = property + "\\AppData\\" + (str4.startsWith("LOCAL") ? "Local" : "Roaming");
            }
            String str11 = str10 + '\\' + strVendorName;
            if (!str.isEmpty()) {
                str11 = str11 + '\\' + str;
            }
            return !str5.isEmpty() ? str11 + '\\' + str5 : str11;
        }
        if (SystemInfoRt.isUnix) {
            return getUnixPlatformPath(property, str, str6, str7, str8);
        }
        throw new UnsupportedOperationException("Unsupported OS: " + SystemInfoRt.OS_NAME);
    }

    private static String getUnixPlatformPath(String str, String str2, String str3, String str4, String str5) {
        String str6 = str3 != null ? System.getenv(str3) : null;
        if (str6 == null || str6.isEmpty()) {
            str6 = str + '/' + str4;
        }
        String str7 = str6 + '/' + vendorName();
        if (!str2.isEmpty()) {
            str7 = str7 + '/' + str2;
        }
        return !str5.isEmpty() ? str7 + '/' + str5 : str7;
    }

    private static String vendorName() {
        String property = System.getProperty("idea.vendor.name");
        if (property == null) {
            try {
                Class<?> cls = Class.forName("org.jetbrains.kotlin.com.intellij.openapi.application.ex.ApplicationInfoEx");
                Class<?> cls2 = Class.forName("org.jetbrains.kotlin.com.intellij.openapi.application.impl.ApplicationInfoImpl");
                MethodHandles.Lookup lookup = MethodHandles.lookup();
                property = (String) lookup.findVirtual(cls2, "getShortCompanyName", MethodType.methodType(String.class)).invoke((Object) lookup.findStatic(cls2, "getShadowInstance", MethodType.methodType(cls)).invoke());
            } catch (Throwable unused) {
            }
            System.setProperty("idea.vendor.name", property != null ? property : "JetBrains");
        }
        return property;
    }
}
