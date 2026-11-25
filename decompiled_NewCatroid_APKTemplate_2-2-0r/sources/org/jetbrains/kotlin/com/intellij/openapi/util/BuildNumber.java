package org.jetbrains.kotlin.com.intellij.openapi.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Objects;
import org.catrobat.catroid.web.ServerAuthenticationConstants;
import org.jetbrains.kotlin.com.intellij.openapi.application.PathManager;

/* JADX INFO: loaded from: classes3.dex */
public final class BuildNumber implements Comparable<BuildNumber> {
    private final int[] myComponents;
    private final String myProductCode;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 3 || i == 4 || i == 5 || i == 12) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 3 || i == 4 || i == 5 || i == 12) ? 2 : 3];
        switch (i) {
            case 2:
                objArr[0] = "components";
                break;
            case 3:
            case 4:
            case 5:
            case 12:
                objArr[0] = "org/jetbrains/kotlin/com/intellij/openapi/util/BuildNumber";
                break;
            case 6:
            case 7:
            case 9:
                objArr[0] = "version";
                break;
            case 8:
            default:
                objArr[0] = "productCode";
                break;
            case 10:
                objArr[0] = ServerAuthenticationConstants.SIGNIN_GOOGLE_CODE_KEY;
                break;
            case 11:
                objArr[0] = "o";
                break;
        }
        if (i == 3) {
            objArr[1] = "getProductCode";
        } else if (i == 4) {
            objArr[1] = "getComponents";
        } else if (i == 5) {
            objArr[1] = "asString";
        } else if (i != 12) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/openapi/util/BuildNumber";
        } else {
            objArr[1] = "currentVersion";
        }
        switch (i) {
            case 3:
            case 4:
            case 5:
            case 12:
                break;
            case 6:
                objArr[2] = "fromStringOrNull";
                break;
            case 7:
            case 8:
                objArr[2] = "fromStringWithProductCode";
                break;
            case 9:
                objArr[2] = "fromString";
                break;
            case 10:
                objArr[2] = "parseBuildNumber";
                break;
            case 11:
                objArr[2] = "compareTo";
                break;
            default:
                objArr[2] = "<init>";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i != 3 && i != 4 && i != 5 && i != 12) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    private static int getBaseLineForHistoricBuilds(int i) {
        if (i >= 10000) {
            return 88;
        }
        if (i >= 9500) {
            return 85;
        }
        if (i >= 9100) {
            return 81;
        }
        if (i >= 8000) {
            return 80;
        }
        if (i >= 7500) {
            return 75;
        }
        if (i >= 7200) {
            return 72;
        }
        if (i >= 6900) {
            return 69;
        }
        if (i >= 6500) {
            return 65;
        }
        if (i >= 6000) {
            return 60;
        }
        if (i >= 5000) {
            return 55;
        }
        return i >= 4000 ? 50 : 40;
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public BuildNumber(String str, int i, int i2) {
        this(str, i, i2);
        if (str == null) {
            $$$reportNull$$$0(0);
        }
    }

    public BuildNumber(String str, int... iArr) {
        if (str == null) {
            $$$reportNull$$$0(1);
        }
        if (iArr == null) {
            $$$reportNull$$$0(2);
        }
        this.myProductCode = str;
        this.myComponents = iArr;
    }

    private static boolean isPlaceholder(String str) {
        return "__BUILD_NUMBER__".equals(str) || "__BUILD__".equals(str);
    }

    public String getProductCode() {
        String str = this.myProductCode;
        if (str == null) {
            $$$reportNull$$$0(3);
        }
        return str;
    }

    public String asString() {
        return asString(true, true);
    }

    public String asStringWithoutProductCode() {
        return asString(false, true);
    }

    private String asString(boolean z, boolean z2) {
        StringBuilder sb = new StringBuilder();
        if (z && !this.myProductCode.isEmpty()) {
            sb.append(this.myProductCode).append('-');
        }
        for (int i : this.myComponents) {
            if (i != Integer.MAX_VALUE) {
                sb.append(i);
            } else if (z2) {
                sb.append("SNAPSHOT");
            }
            sb.append('.');
        }
        if (sb.charAt(sb.length() - 1) == '.') {
            sb.setLength(sb.length() - 1);
        }
        String string = sb.toString();
        if (string == null) {
            $$$reportNull$$$0(5);
        }
        return string;
    }

    public static BuildNumber fromPluginsCompatibleBuild() {
        return fromString(getPluginsCompatibleBuild());
    }

    public static BuildNumber fromString(String str) {
        if (str == null) {
            return null;
        }
        return fromString(str.trim(), null, null);
    }

    public static BuildNumber fromStringWithProductCode(String str, String str2) {
        if (str == null) {
            $$$reportNull$$$0(7);
        }
        if (str2 == null) {
            $$$reportNull$$$0(8);
        }
        return fromString(str, null, str2);
    }

    public static BuildNumber fromString(String str, String str2, String str3) {
        String strSubstring;
        int i;
        if (str == null) {
            $$$reportNull$$$0(9);
        }
        if (str.isEmpty()) {
            return null;
        }
        int iIndexOf = str.indexOf(45);
        if (iIndexOf > 0) {
            str3 = str.substring(0, iIndexOf);
            strSubstring = str.substring(iIndexOf + 1);
        } else {
            if (str3 == null) {
                str3 = "";
            }
            strSubstring = str;
        }
        if ("SNAPSHOT".equals(strSubstring) || isPlaceholder(strSubstring)) {
            return new BuildNumber(str3, currentVersion().myComponents);
        }
        int iIndexOf2 = strSubstring.indexOf(46);
        if (iIndexOf2 > 0) {
            if (strSubstring.substring(0, iIndexOf2).trim().isEmpty()) {
                return null;
            }
            String[] strArrSplit = strSubstring.split("\\.");
            int[] iArrCopyOf = new int[strArrSplit.length];
            int length = strArrSplit.length;
            for (int i2 = 0; i2 < length; i2++) {
                int buildNumber = parseBuildNumber(str, strArrSplit[i2], str2);
                iArrCopyOf[i2] = buildNumber;
                if (buildNumber == Integer.MAX_VALUE && (i = i2 + 1) != length) {
                    iArrCopyOf = Arrays.copyOf(iArrCopyOf, i);
                    break;
                }
            }
            return new BuildNumber(str3, iArrCopyOf);
        }
        int buildNumber2 = parseBuildNumber(str, strSubstring, str2);
        if (buildNumber2 <= 2000) {
            return new BuildNumber(str3, buildNumber2, 0);
        }
        return new BuildNumber(str3, getBaseLineForHistoricBuilds(buildNumber2), buildNumber2);
    }

    private static int parseBuildNumber(String str, String str2, String str3) {
        if (str2 == null) {
            $$$reportNull$$$0(10);
        }
        if ("SNAPSHOT".equals(str2) || isPlaceholder(str2) || "*".equals(str2)) {
            return Integer.MAX_VALUE;
        }
        try {
            return Integer.parseInt(str2);
        } catch (NumberFormatException unused) {
            throw new RuntimeException("Invalid version number: " + str + "; plugin name: " + str3);
        }
    }

    @Override // java.lang.Comparable
    public int compareTo(BuildNumber buildNumber) {
        if (buildNumber == null) {
            $$$reportNull$$$0(11);
        }
        int[] iArr = this.myComponents;
        int[] iArr2 = buildNumber.myComponents;
        for (int i = 0; i < Math.min(iArr.length, iArr2.length); i++) {
            int i2 = iArr[i];
            int i3 = iArr2[i];
            if (i2 == i3 && i2 == Integer.MAX_VALUE) {
                return 0;
            }
            if (i2 == Integer.MAX_VALUE) {
                return 1;
            }
            if (i3 == Integer.MAX_VALUE) {
                return -1;
            }
            int i4 = i2 - i3;
            if (i4 != 0) {
                return i4;
            }
        }
        return iArr.length - iArr2.length;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        BuildNumber buildNumber = (BuildNumber) obj;
        if (this.myProductCode.equals(buildNumber.myProductCode)) {
            return Arrays.equals(this.myComponents, buildNumber.myComponents);
        }
        return false;
    }

    public int hashCode() {
        return (this.myProductCode.hashCode() * 31) + Arrays.hashCode(this.myComponents);
    }

    public String toString() {
        return asString();
    }

    private static final class Holder {
        private static final BuildNumber CURRENT_VERSION = fromFile();

        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            String str = i != 4 ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
            Object[] objArr = new Object[i != 4 ? 2 : 3];
            if (i != 4) {
                objArr[0] = "org/jetbrains/kotlin/com/intellij/openapi/util/BuildNumber$Holder";
            } else {
                objArr[0] = "path";
            }
            if (i != 4) {
                objArr[1] = "fromFile";
            } else {
                objArr[1] = "org/jetbrains/kotlin/com/intellij/openapi/util/BuildNumber$Holder";
            }
            if (i == 4) {
                objArr[2] = "readFile";
            }
            String str2 = String.format(str, objArr);
            if (i == 4) {
                throw new IllegalArgumentException(str2);
            }
        }

        private static BuildNumber fromFile() {
            BuildNumber file;
            BuildNumber file2;
            String homePath = PathManager.getHomePath();
            Path path = Paths.get(homePath, new String[0]);
            BuildNumber file3 = readFile(path.resolve("build.txt"));
            if (file3 != null) {
                if (file3 == null) {
                    $$$reportNull$$$0(0);
                }
                return file3;
            }
            if (SystemInfoRt.isMac && (file2 = readFile(path.resolve("Resources/build.txt"))) != null) {
                if (file2 == null) {
                    $$$reportNull$$$0(1);
                }
                return file2;
            }
            String communityHomePath = PathManager.getCommunityHomePath();
            if (!communityHomePath.equals(homePath) && (file = readFile(Paths.get(communityHomePath, "build.txt"))) != null) {
                if (file == null) {
                    $$$reportNull$$$0(2);
                }
                return file;
            }
            BuildNumber buildNumber = (BuildNumber) Objects.requireNonNull(BuildNumber.fromString("999.SNAPSHOT"));
            if (buildNumber == null) {
                $$$reportNull$$$0(3);
            }
            return buildNumber;
        }

        private static BuildNumber readFile(Path path) {
            if (path == null) {
                $$$reportNull$$$0(4);
            }
            try {
                BufferedReader bufferedReaderNewBufferedReader = Files.newBufferedReader(path);
                try {
                    String line = bufferedReaderNewBufferedReader.readLine();
                    if (line != null) {
                        BuildNumber buildNumberFromString = BuildNumber.fromString(line);
                        if (bufferedReaderNewBufferedReader != null) {
                            bufferedReaderNewBufferedReader.close();
                        }
                        return buildNumberFromString;
                    }
                    if (bufferedReaderNewBufferedReader == null) {
                        return null;
                    }
                    bufferedReaderNewBufferedReader.close();
                    return null;
                } catch (Throwable th) {
                    if (bufferedReaderNewBufferedReader != null) {
                        try {
                            bufferedReaderNewBufferedReader.close();
                        } catch (Throwable th2) {
                            th.addSuppressed(th2);
                        }
                    }
                    throw th;
                }
            } catch (IOException unused) {
                return null;
            }
            return null;
        }
    }

    public static BuildNumber currentVersion() {
        BuildNumber buildNumber = Holder.CURRENT_VERSION;
        if (buildNumber == null) {
            $$$reportNull$$$0(12);
        }
        return buildNumber;
    }

    private static String getPluginsCompatibleBuild() {
        return System.getProperty("idea.plugins.compatible.build");
    }
}
