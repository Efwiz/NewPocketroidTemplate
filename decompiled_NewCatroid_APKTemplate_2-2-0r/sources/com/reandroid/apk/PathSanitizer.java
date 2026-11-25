package com.reandroid.apk;

import com.reandroid.archive.InputSource;
import com.reandroid.identifiers.Identifier;
import com.reandroid.utils.HexUtil;
import com.reandroid.utils.StringsUtil;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import name.antonsmirnov.firmata.FormatHelper;

/* JADX INFO: loaded from: classes10.dex */
public class PathSanitizer {
    private static final int MAX_NAME_LENGTH = 140;
    private static final int MAX_PATH_LENGTH = 4096;
    private APKLogger apkLogger;
    private boolean mCaseInsensitive;
    private final Set<String> mSanitizedPaths;
    private int mUniqueName;
    private Collection<ResFile> resFileList;
    private final boolean sanitizeResourceFiles;
    private final Collection<? extends InputSource> sourceList;

    private static boolean isGoodFileNameChar(char c) {
        return c == '_' || (c >= '0' && c <= '9') || ((c >= 'A' && c <= 'Z') || (c >= 'a' && c <= 'z'));
    }

    private static boolean isGoodFileNameSymbol(char c) {
        return c == '.' || c == '+' || c == '-' || c == '#';
    }

    public PathSanitizer(Collection<? extends InputSource> collection, boolean z) {
        this.sourceList = collection;
        this.mSanitizedPaths = new HashSet();
        this.sanitizeResourceFiles = z;
        this.mCaseInsensitive = Identifier.CASE_INSENSITIVE_FS;
    }

    public PathSanitizer(Collection<? extends InputSource> collection) {
        this(collection, false);
    }

    public void sanitize() {
        this.mSanitizedPaths.clear();
        logMessage("Sanitizing paths ...");
        sanitizeCaseInsensitiveOs();
        sanitizeResFiles();
        Iterator<? extends InputSource> it = this.sourceList.iterator();
        while (it.hasNext()) {
            sanitize(it.next(), 1, false);
        }
    }

    public void setResourceFileList(Collection<ResFile> collection) {
        this.resFileList = collection;
    }

    public boolean isCaseInsensitive() {
        return this.mCaseInsensitive;
    }

    public void setCaseInsensitive(boolean z) {
        this.mCaseInsensitive = z;
    }

    private void sanitizeCaseInsensitiveOs() {
        if (Identifier.CASE_INSENSITIVE_FS) {
            logMessage("[WIN/MAC] Checking duplicate case insensitive paths ...");
            this.mUniqueName = 0;
            HashMap map = new HashMap();
            for (InputSource inputSource : this.sourceList) {
                String lowerCase = inputSource.getAlias().toLowerCase();
                InputSource inputSource2 = (InputSource) map.get(lowerCase);
                if (inputSource2 == null) {
                    map.put(lowerCase, inputSource);
                } else {
                    sanitizeCaseInsensitiveOs(inputSource);
                    sanitizeCaseInsensitiveOs(inputSource2);
                    map.remove(lowerCase);
                }
            }
        }
    }

    private void sanitizeCaseInsensitiveOs(InputSource inputSource) {
        String alias = inputSource.getAlias();
        this.mUniqueName++;
        String strCreateUniqueName = createUniqueName(this.mUniqueName + alias);
        int iLastIndexOf = alias.lastIndexOf(47);
        if (iLastIndexOf > 0) {
            strCreateUniqueName = alias.substring(0, iLastIndexOf) + "/" + strCreateUniqueName;
        }
        inputSource.setAlias(strCreateUniqueName);
        String str = FormatHelper.QUOTE + alias + "' -> '" + strCreateUniqueName + FormatHelper.QUOTE;
        if (this.mUniqueName < 10) {
            logMessage("Case sensitive path renamed: " + str);
        } else {
            logVerbose(str);
        }
    }

    private void sanitizeResFiles() {
        Collection<ResFile> collection = this.resFileList;
        if (collection == null) {
            return;
        }
        boolean z = this.sanitizeResourceFiles;
        Set<String> set = this.mSanitizedPaths;
        if (z) {
            logMessage("Sanitizing resource files ...");
        }
        for (ResFile resFile : collection) {
            if (z) {
                sanitize(resFile);
            } else {
                set.add(resFile.getFilePath());
            }
        }
    }

    private void sanitize(ResFile resFile) {
        String strSanitize = sanitize(resFile.getInputSource(), 3, true);
        if (strSanitize == null) {
            return;
        }
        resFile.setFilePath(strSanitize);
    }

    private String sanitize(InputSource inputSource, int i, boolean z) {
        String name2 = inputSource.getName();
        if (this.mSanitizedPaths.contains(name2)) {
            return null;
        }
        this.mSanitizedPaths.add(name2);
        String alias = inputSource.getAlias();
        if (shouldIgnore(alias)) {
            return null;
        }
        String strSanitize = sanitize(alias, i, z);
        if (alias.equals(strSanitize)) {
            return null;
        }
        inputSource.setAlias(strSanitize);
        if (alias.length() > 20) {
            alias = ".. " + alias.substring(alias.length() - 20);
        }
        logVerbose(FormatHelper.QUOTE + alias + "' -> '" + strSanitize + FormatHelper.QUOTE);
        return strSanitize;
    }

    private String sanitize(String str, int i, boolean z) {
        StringBuilder sb = new StringBuilder();
        String[] strArrSplit = StringsUtil.split(str, '/');
        boolean z2 = str.length() >= 4096;
        int length = strArrSplit.length;
        boolean zEquals = false;
        for (int i2 = 0; i2 < length; i2++) {
            String strCreateUniqueName = strArrSplit[i2];
            if (i2 == 0) {
                zEquals = "assets".equals(strCreateUniqueName);
            }
            if (!isGoodSimpleName(strCreateUniqueName, zEquals) || (z2 && i2 >= i)) {
                appendPathName(sb, createUniqueName(str));
                break;
            }
            if (z && i2 >= i - 1) {
                if (i2 < length - 1) {
                    strCreateUniqueName = createUniqueName(str);
                }
                appendPathName(sb, strCreateUniqueName);
                break;
            }
            appendPathName(sb, strCreateUniqueName);
        }
        return sb.toString();
    }

    private boolean shouldIgnore(String str) {
        return str.startsWith("lib/") && str.endsWith(".so");
    }

    public void setApkLogger(APKLogger aPKLogger) {
        this.apkLogger = aPKLogger;
    }

    private String getLogTag() {
        return "[SANITIZE]: ";
    }

    void logMessage(String str) {
        APKLogger aPKLogger = this.apkLogger;
        if (aPKLogger != null) {
            aPKLogger.logMessage(getLogTag() + str);
        }
    }

    void logVerbose(String str) {
        APKLogger aPKLogger = this.apkLogger;
        if (aPKLogger != null) {
            aPKLogger.logVerbose(getLogTag() + str);
        }
    }

    private static void appendPathName(StringBuilder sb, String str) {
        if (sb.length() > 0) {
            sb.append('/');
        }
        sb.append(str);
    }

    private static String createUniqueName(String str) {
        return "alias_" + HexUtil.toHexNoPrefix8(str.hashCode());
    }

    private static boolean isGoodSimpleName(String str, boolean z) {
        int length;
        if (str == null || (length = str.length()) == 0 || length >= 140) {
            return false;
        }
        boolean z2 = false;
        boolean z3 = false;
        for (int i = 0; i < length; i++) {
            char cCharAt = str.charAt(i);
            if (z && cCharAt == ' ') {
                if (z2 || i == 0 || i == length - 1) {
                    return false;
                }
                z2 = true;
            } else if (isGoodFileNameChar(cCharAt)) {
                z2 = false;
                z3 = false;
            } else {
                if (!isGoodFileNameSymbol(cCharAt) || z3) {
                    return false;
                }
                z2 = false;
                z3 = true;
            }
        }
        return true;
    }

    public static String sanitizeSimpleName(String str) {
        if (str == null) {
            return null;
        }
        int length = str.length();
        StringBuilder sb = new StringBuilder(length);
        int i = 0;
        boolean z = true;
        for (int i2 = 0; i2 < length && i < 140; i2++) {
            char cCharAt = str.charAt(i2);
            if (!isGoodFileNameSymbol(cCharAt)) {
                if (isGoodFileNameChar(cCharAt)) {
                    sb.append(cCharAt);
                    i++;
                    z = false;
                }
            } else if (!z) {
                sb.append(cCharAt);
                i++;
            }
            z = true;
        }
        if (i == 0) {
            return null;
        }
        return sb.toString();
    }

    public static PathSanitizer create(ApkModule apkModule) {
        PathSanitizer pathSanitizer = new PathSanitizer(apkModule.getZipEntryMap().listInputSources());
        pathSanitizer.setApkLogger(apkModule.getApkLogger());
        pathSanitizer.setResourceFileList(apkModule.listResFiles());
        return pathSanitizer;
    }
}
