package org.jetbrains.kotlin.com.intellij.openapi.util.text;

import com.huawei.hms.ml.language.common.utils.LanguageCodeUtil;
import java.time.Duration;
import org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntArrayList;
import org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.LongArrayList;

/* JADX INFO: loaded from: classes3.dex */
public final class Formats {
    private static final String[] TIME_UNITS = {LanguageCodeUtil.MS, "s", "m", "h", "d"};
    private static final long[] TIME_MULTIPLIERS = {1, 1000, 60, 60, 24};

    /* JADX WARN: Code duplicated, block: B:23:0x0032  */
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 1 || i == 3 || i == 4 || i == 5) ? "Argument for @NotNull parameter '%s' of %s.%s must not be null" : "@NotNull method %s.%s must not return null";
        Object[] objArr = new Object[(i == 1 || i == 3 || i == 4 || i == 5) ? 3 : 2];
        if (i == 1) {
            objArr[0] = "unitSeparator";
        } else if (i == 3) {
            objArr[0] = "duration";
        } else if (i == 4 || i == 5) {
            objArr[0] = "unitSeparator";
        } else {
            objArr[0] = "org/jetbrains/kotlin/com/intellij/openapi/util/text/Formats";
        }
        if (i == 1 || i == 3 || i == 4 || i == 5) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/openapi/util/text/Formats";
        } else if (i != 6) {
            objArr[1] = "formatFileSize";
        } else {
            objArr[1] = "formatDuration";
        }
        if (i == 1) {
            objArr[2] = "formatFileSize";
        } else if (i == 3 || i == 4 || i == 5) {
            objArr[2] = "formatDuration";
        }
        String str2 = String.format(str, objArr);
        if (i != 1 && i != 3 && i != 4 && i != 5) {
            throw new IllegalStateException(str2);
        }
        throw new IllegalArgumentException(str2);
    }

    public static String formatFileSize(long j) {
        String fileSize = StringUtilRt.formatFileSize(j);
        if (fileSize == null) {
            $$$reportNull$$$0(0);
        }
        return fileSize;
    }

    public static String formatFileSize(long j, String str) {
        if (str == null) {
            $$$reportNull$$$0(1);
        }
        String fileSize = StringUtilRt.formatFileSize(j, str);
        if (fileSize == null) {
            $$$reportNull$$$0(2);
        }
        return fileSize;
    }

    public static String formatDuration(long j) {
        return formatDuration(j, " ");
    }

    public static String formatDuration(Duration duration) {
        if (duration == null) {
            $$$reportNull$$$0(3);
        }
        return formatDuration(duration.toMillis());
    }

    public static String formatDuration(long j, String str) {
        if (str == null) {
            $$$reportNull$$$0(4);
        }
        return formatDuration(j, str, Integer.MAX_VALUE);
    }

    private static String formatDuration(long j, String str, int i) {
        int i2;
        if (str == null) {
            $$$reportNull$$$0(5);
        }
        LongArrayList longArrayList = new LongArrayList();
        IntArrayList intArrayList = new IntArrayList();
        long j2 = j;
        int i3 = 1;
        while (true) {
            if (i3 >= TIME_UNITS.length || j2 <= 0) {
                break;
            }
            long j3 = TIME_MULTIPLIERS[i3];
            if (j2 < j3) {
                break;
            }
            long j4 = j2 % j3;
            j2 /= j3;
            if (j4 != 0 || !longArrayList.isEmpty()) {
                longArrayList.add(0, j4);
                intArrayList.add(0, i3 - 1);
            }
            i3++;
        }
        longArrayList.add(0, j2);
        intArrayList.add(0, i3 - 1);
        if (longArrayList.size() > i) {
            int i4 = intArrayList.getInt(i - 1);
            long j5 = TIME_MULTIPLIERS[i4];
            if (longArrayList.getLong(i) > j5 / 2) {
                long j6 = j5 - longArrayList.getLong(i);
                for (int i5 = i4 - 1; i5 > 0; i5--) {
                    j6 *= TIME_MULTIPLIERS[i5];
                }
                return formatDuration(j + j6, str, i);
            }
        }
        StringBuilder sb = new StringBuilder();
        for (i2 = 0; i2 < longArrayList.size() && i2 < i; i2++) {
            if (i2 > 0) {
                sb.append(" ");
            }
            sb.append(longArrayList.getLong(i2)).append(str).append(TIME_UNITS[intArrayList.getInt(i2)]);
        }
        String string = sb.toString();
        if (string == null) {
            $$$reportNull$$$0(6);
        }
        return string;
    }
}
