package org.jetbrains.kotlin.daemon.common;

import java.io.File;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.collections.ArraysKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.jetbrains.kotlin.cli.common.CompilerSystemProperties;

/* JADX INFO: compiled from: FileSystemUtils.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\n\n\u0002\u0010\u0011\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u001f\u0010\r\u001a\u00020\u00042\u0012\u0010\u000e\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00040\u000f\"\u00020\u0004¢\u0006\u0002\u0010\u0010R\u0011\u0010\u0003\u001a\u00020\u00048F¢\u0006\u0006\u001a\u0004\b\u0005\u0010\u0006R\u0011\u0010\u0007\u001a\u00020\u00048F¢\u0006\u0006\u001a\u0004\b\b\u0010\u0006R\u0011\u0010\t\u001a\u00020\u00048F¢\u0006\u0006\u001a\u0004\b\n\u0010\u0006R\u0011\u0010\u000b\u001a\u00020\u00048F¢\u0006\u0006\u001a\u0004\b\f\u0010\u0006¨\u0006\u0011"}, d2 = {"Lorg/jetbrains/kotlin/daemon/common/FileSystem;", "", "()V", "logFilesPath", "", "getLogFilesPath", "()Ljava/lang/String;", "runtimeStateFilesBasePath", "getRuntimeStateFilesBasePath", "tempPath", "getTempPath", "userHomePath", "getUserHomePath", "getRuntimeStateFilesPath", "names", "", "([Ljava/lang/String;)Ljava/lang/String;", "daemon-common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FileSystem {
    public static final FileSystem INSTANCE = new FileSystem();

    /* JADX INFO: compiled from: FileSystemUtils.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[OSKind.values().length];
            try {
                iArr[OSKind.Windows.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[OSKind.OSX.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[OSKind.Unix.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                iArr[OSKind.Unknown.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    private FileSystem() {
    }

    public final String getUserHomePath() {
        return CompilerSystemProperties.USER_HOME.getSafeValue();
    }

    public final String getTempPath() {
        return CompilerSystemProperties.TMP_DIR.getSafeValue();
    }

    public final String getLogFilesPath() {
        return getTempPath();
    }

    public final String getRuntimeStateFilesBasePath() {
        int i = WhenMappings.$EnumSwitchMapping$0[OSKind.INSTANCE.getCurrent().ordinal()];
        if (i == 1) {
            return FileSystemUtilsKt.orDefault(System.getenv("LOCALAPPDATA"), getTempPath());
        }
        if (i == 2) {
            return getUserHomePath() + "/Library/Application Support";
        }
        if (i == 3) {
            return FileSystemUtilsKt.orDefault(System.getenv("XDG_DATA_HOME"), getUserHomePath() + "/.local/share");
        }
        if (i == 4) {
            return getTempPath();
        }
        throw new NoWhenBranchMatchedException();
    }

    public final String getRuntimeStateFilesPath(String... names) {
        Intrinsics.checkNotNullParameter(names, "names");
        ArraysKt.any(names);
        File file = new File(getRuntimeStateFilesBasePath());
        int i = 0;
        if (!file.exists() || !file.isDirectory()) {
            List listDrop = ArraysKt.drop(names, 1);
            String userHomePath = getUserHomePath();
            String str = (String) ArraysKt.first(names);
            if (!StringsKt.startsWith$default(str, ".", false, 2, (Object) null)) {
                str = "." + str;
            }
            File file2 = new File(userHomePath, str);
            Iterator it = listDrop.iterator();
            while (true) {
                file = file2;
                if (!it.hasNext()) {
                    break;
                }
                file2 = new File(file, (String) it.next());
            }
        } else {
            int length = names.length;
            while (i < length) {
                File file3 = new File(file, names[i]);
                i++;
                file = file3;
            }
        }
        if ((!file.exists() || !file.isDirectory()) && !file.mkdirs()) {
            return getTempPath();
        }
        String absolutePath = file.getAbsolutePath();
        Intrinsics.checkNotNullExpressionValue(absolutePath, "getAbsolutePath(...)");
        return absolutePath;
    }
}
