package org.jetbrains.kotlin.com.intellij.openapi.util.io;

import com.gaurav.avnc.vnc.XKeySym;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.huawei.hms.ml.language.common.utils.Constant;
import com.sun.jna.Memory;
import com.sun.jna.Native;
import com.sun.jna.NativeLibrary;
import com.sun.jna.Platform;
import com.sun.jna.Pointer;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.LinkOption;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.DosFileAttributes;
import java.nio.file.attribute.PosixFileAttributes;
import java.nio.file.attribute.PosixFilePermission;
import java.util.Collection;
import java.util.Collections;
import java.util.EnumSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import org.apache.commons.compress.harmony.pack200.PackingOptions;
import org.jetbrains.kotlin.com.intellij.jna.JnaLoader;
import org.jetbrains.kotlin.com.intellij.openapi.diagnostic.Logger;
import org.jetbrains.kotlin.com.intellij.openapi.util.SystemInfo;
import org.jetbrains.kotlin.com.intellij.openapi.util.io.win32.FileInfo;
import org.jetbrains.kotlin.com.intellij.openapi.util.io.win32.IdeaWin32;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.CharsetToolkit;
import org.jetbrains.kotlin.com.intellij.util.SystemProperties;
import org.jetbrains.kotlin.com.intellij.util.containers.LimitedPool;
import org.jetbrains.kotlin.com.intellij.util.system.CpuArch;

/* JADX INFO: loaded from: classes3.dex */
public final class FileSystemUtil {
    public static final boolean DO_NOT_RESOLVE_SYMLINKS = Boolean.getBoolean("idea.symlinks.no.resolve");
    private static final Logger LOG = Logger.getInstance((Class<?>) FileSystemUtil.class);
    private static final Mediator ourMediator = computeMediator();
    private static volatile boolean ourLibExt2FsPresent = true;

    interface Mediator {
        boolean clonePermissions(String str, String str2, boolean z) throws IOException;

        FileAttributes getAttributes(String str) throws IOException;
    }

    /* JADX WARN: Code duplicated, block: B:17:0x0026  */
    /* JADX WARN: Code duplicated, block: B:31:0x0058  */
    /* JADX WARN: Code duplicated, block: B:9:0x0016  */
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str;
        int i2;
        if (i != 15 && i != 23 && i != 25 && i != 26) {
            switch (i) {
                case 2:
                case 3:
                case 4:
                case 5:
                case 6:
                case 7:
                case 8:
                case 9:
                case 10:
                case 11:
                case 12:
                case 13:
                    str = "Argument for @NotNull parameter '%s' of %s.%s must not be null";
                    break;
                default:
                    str = "@NotNull method %s.%s must not return null";
                    break;
            }
        } else {
            str = "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        }
        if (i != 15 && i != 23 && i != 25 && i != 26) {
            switch (i) {
                case 2:
                case 3:
                case 4:
                case 5:
                case 6:
                case 7:
                case 8:
                case 9:
                case 10:
                case 11:
                case 12:
                case 13:
                    i2 = 3;
                    break;
                default:
                    i2 = 2;
                    break;
            }
        } else {
            i2 = 3;
        }
        Object[] objArr = new Object[i2];
        if (i == 15 || i == 23) {
            objArr[0] = "anyChild";
        } else if (i == 25) {
            objArr[0] = "name";
        } else if (i != 26) {
            switch (i) {
                case 2:
                case 5:
                case 7:
                    objArr[0] = "path";
                    break;
                case 3:
                case 4:
                case 6:
                case 8:
                    objArr[0] = "file";
                    break;
                case 9:
                case 11:
                    objArr[0] = FirebaseAnalytics.Param.SOURCE;
                    break;
                case 10:
                case 12:
                    objArr[0] = "target";
                    break;
                case 13:
                    objArr[0] = "anyChild";
                    break;
                default:
                    objArr[0] = "org/jetbrains/kotlin/com/intellij/openapi/util/io/FileSystemUtil";
                    break;
            }
        } else {
            objArr[0] = "dir";
        }
        switch (i) {
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
            case 11:
            case 12:
            case 13:
            case 15:
            case 23:
            case 25:
            case 26:
                objArr[1] = "org/jetbrains/kotlin/com/intellij/openapi/util/io/FileSystemUtil";
                break;
            case 14:
                objArr[1] = "readParentCaseSensitivity";
                break;
            case 16:
            case 17:
            case 18:
            case 19:
            case 20:
            case 21:
            case 22:
                objArr[1] = "readParentCaseSensitivityByJavaIO";
                break;
            case 24:
                objArr[1] = "readCaseSensitivityByNativeAPI";
                break;
            default:
                objArr[1] = "computeMediator";
                break;
        }
        if (i == 15) {
            objArr[2] = "readParentCaseSensitivityByJavaIO";
        } else if (i == 23) {
            objArr[2] = "readCaseSensitivityByNativeAPI";
        } else if (i == 25) {
            objArr[2] = "isCaseToggleable";
        } else if (i != 26) {
            switch (i) {
                case 2:
                case 3:
                    objArr[2] = "getAttributes";
                    break;
                case 4:
                    objArr[2] = "lastModified";
                    break;
                case 5:
                case 6:
                    objArr[2] = "isSymLink";
                    break;
                case 7:
                case 8:
                    objArr[2] = "resolveSymLink";
                    break;
                case 9:
                case 10:
                    objArr[2] = "clonePermissions";
                    break;
                case 11:
                case 12:
                    objArr[2] = "clonePermissionsToExecute";
                    break;
                case 13:
                    objArr[2] = "readParentCaseSensitivity";
                    break;
            }
        } else {
            objArr[2] = "findCaseToggleableChild";
        }
        String str2 = String.format(str, objArr);
        if (i != 15 && i != 23 && i != 25 && i != 26) {
            switch (i) {
                case 2:
                case 3:
                case 4:
                case 5:
                case 6:
                case 7:
                case 8:
                case 9:
                case 10:
                case 11:
                case 12:
                case 13:
                    break;
                default:
                    throw new IllegalStateException(str2);
            }
        }
        throw new IllegalArgumentException(str2);
    }

    static Mediator computeMediator() {
        if (!Boolean.getBoolean("idea.io.use.nio2")) {
            try {
                if (SystemInfo.isWindows && IdeaWin32.isAvailable()) {
                    Mediator mediatorCheck = check(new IdeaWin32MediatorImpl());
                    if (mediatorCheck == null) {
                        $$$reportNull$$$0(0);
                    }
                    return mediatorCheck;
                }
                if ((SystemInfo.isLinux || SystemInfo.isMac) && CpuArch.isIntel64() && JnaLoader.isLoaded() && JnaLoader.isSupportsDirectMapping()) {
                    Mediator mediatorCheck2 = check(new JnaUnixMediatorImpl());
                    if (mediatorCheck2 == null) {
                        $$$reportNull$$$0(1);
                    }
                    return mediatorCheck2;
                }
            } catch (Throwable th) {
                LOG.warn("Failed to load filesystem access layer: " + SystemInfo.OS_NAME + ", " + SystemInfo.JAVA_VERSION, th);
            }
        }
        return new Nio2MediatorImpl();
    }

    private static Mediator check(Mediator mediator) throws Exception {
        mediator.getAttributes(SystemInfo.isWindows ? "C:\\" : "/");
        return mediator;
    }

    private FileSystemUtil() {
    }

    public static FileAttributes getAttributes(String str) {
        if (str == null) {
            $$$reportNull$$$0(2);
        }
        try {
            Logger logger = LOG;
            if (logger.isTraceEnabled()) {
                long jNanoTime = System.nanoTime();
                FileAttributes attributes = ourMediator.getAttributes(str);
                logger.trace("getAttributes(" + str + ") = " + attributes + " in " + TimeUnit.NANOSECONDS.toMicros(System.nanoTime() - jNanoTime) + " mks");
                return attributes;
            }
            return ourMediator.getAttributes(str);
        } catch (Exception e) {
            LOG.warn("getAttributes(" + str + Constant.AFTER_QUTO, e);
            return null;
        }
    }

    public static boolean isSymLink(String str) {
        if (str == null) {
            $$$reportNull$$$0(5);
        }
        FileAttributes attributes = getAttributes(str);
        return attributes != null && attributes.isSymLink();
    }

    public static boolean isSymLink(File file) {
        if (file == null) {
            $$$reportNull$$$0(6);
        }
        return isSymLink(file.getAbsolutePath());
    }

    public static boolean clonePermissionsToExecute(String str, String str2) {
        if (str == null) {
            $$$reportNull$$$0(11);
        }
        if (str2 == null) {
            $$$reportNull$$$0(12);
        }
        try {
            return ourMediator.clonePermissions(str, str2, true);
        } catch (Exception e) {
            LOG.warn(e);
            return false;
        }
    }

    private static class IdeaWin32MediatorImpl implements Mediator {
        private final IdeaWin32 myInstance;

        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            Object[] objArr = new Object[3];
            if (i == 2) {
                objArr[0] = FirebaseAnalytics.Param.SOURCE;
            } else if (i != 3) {
                objArr[0] = "path";
            } else {
                objArr[0] = "target";
            }
            objArr[1] = "org/jetbrains/kotlin/com/intellij/openapi/util/io/FileSystemUtil$IdeaWin32MediatorImpl";
            if (i == 1) {
                objArr[2] = "resolveSymLink";
            } else if (i == 2 || i == 3) {
                objArr[2] = "clonePermissions";
            } else {
                objArr[2] = "getAttributes";
            }
            throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
        }

        @Override // org.jetbrains.kotlin.com.intellij.openapi.util.io.FileSystemUtil.Mediator
        public boolean clonePermissions(String str, String str2, boolean z) {
            if (str == null) {
                $$$reportNull$$$0(2);
            }
            if (str2 != null) {
                return false;
            }
            $$$reportNull$$$0(3);
            return false;
        }

        private IdeaWin32MediatorImpl() {
            this.myInstance = IdeaWin32.getInstance();
        }

        @Override // org.jetbrains.kotlin.com.intellij.openapi.util.io.FileSystemUtil.Mediator
        public FileAttributes getAttributes(String str) {
            if (str == null) {
                $$$reportNull$$$0(0);
            }
            FileInfo info = this.myInstance.getInfo(str);
            if (info != null) {
                return info.toFileAttributes();
            }
            return null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    static class JnaUnixMediatorImpl implements Mediator {
        static final /* synthetic */ boolean $assertionsDisabled = false;
        private final int myGid;
        private final int[] myOffsets;
        private final int myUid;
        private static final int[] LINUX_64 = {24, 48, 88, 28, 32};
        private static final int[] BSD_64 = {8, 72, 40, 12, 16};
        private final boolean myCoarseTs = SystemProperties.getBooleanProperty("idea.io.coarse.ts", false);
        private final LimitedPool<Memory> myMemoryPool = new LimitedPool.Sync(10, new LimitedPool.ObjectFactory() { // from class: org.jetbrains.kotlin.com.intellij.openapi.util.io.FileSystemUtil$JnaUnixMediatorImpl$$ExternalSyntheticLambda0
            @Override // org.jetbrains.kotlin.com.intellij.util.containers.LimitedPool.ObjectFactory
            public final Object create() {
                return FileSystemUtil.JnaUnixMediatorImpl.lambda$new$0();
            }
        });

        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            Object[] objArr = new Object[3];
            if (i == 2) {
                objArr[0] = FirebaseAnalytics.Param.SOURCE;
            } else if (i == 3) {
                objArr[0] = "target";
            } else if (i == 5 || i == 6 || i == 7) {
                objArr[0] = "buffer";
            } else {
                objArr[0] = "path";
            }
            objArr[1] = "org/jetbrains/kotlin/com/intellij/openapi/util/io/FileSystemUtil$JnaUnixMediatorImpl";
            switch (i) {
                case 1:
                    objArr[2] = "resolveSymLink";
                    break;
                case 2:
                case 3:
                    objArr[2] = "clonePermissions";
                    break;
                case 4:
                case 5:
                    objArr[2] = "loadFileStatus";
                    break;
                case 6:
                    objArr[2] = "getModeFlags";
                    break;
                case 7:
                    objArr[2] = "ownFile";
                    break;
                default:
                    objArr[2] = "getAttributes";
                    break;
            }
            throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
        }

        private static final class LibC {
            static native int access(String str, int i);

            static native int chmod(String str, int i);

            static native int getgid();

            static native int getuid();

            private LibC() {
            }
        }

        private static final class UnixLibC {
            static native int lstat(String str, Pointer pointer);

            static native int stat(String str, Pointer pointer);

            private UnixLibC() {
            }
        }

        private static final class LinuxLibC {
            static native int __lxstat64(int i, String str, Pointer pointer);

            static native int __xstat64(int i, String str, Pointer pointer);

            private LinuxLibC() {
            }
        }

        static /* synthetic */ Memory lambda$new$0() {
            return new Memory(256L);
        }

        JnaUnixMediatorImpl() {
            if ("linux-x86-64".equals(Platform.RESOURCE_PREFIX)) {
                this.myOffsets = LINUX_64;
            } else {
                if (!"darwin-x86-64".equals(Platform.RESOURCE_PREFIX)) {
                    throw new IllegalStateException("Unsupported OS/arch: " + Platform.RESOURCE_PREFIX);
                }
                this.myOffsets = BSD_64;
            }
            NativeLibrary nativeLibrary = NativeLibrary.getInstance("c", Collections.singletonMap("string-encoding", CharsetToolkit.getPlatformCharset().name()));
            Native.register(LibC.class, nativeLibrary);
            Native.register(SystemInfo.isLinux ? LinuxLibC.class : UnixLibC.class, nativeLibrary);
            this.myUid = LibC.getuid();
            this.myGid = LibC.getgid();
        }

        /* JADX WARN: Code duplicated, block: B:53:0x00cf  */
        @Override // org.jetbrains.kotlin.com.intellij.openapi.util.io.FileSystemUtil.Mediator
        public FileAttributes getAttributes(String str) {
            long j;
            boolean z;
            if (str == null) {
                $$$reportNull$$$0(0);
            }
            Memory memoryAlloc = this.myMemoryPool.alloc();
            try {
                if ((SystemInfo.isLinux ? LinuxLibC.__lxstat64(1, str, memoryAlloc) : UnixLibC.lstat(str, memoryAlloc)) == 0) {
                    int modeFlags = getModeFlags(memoryAlloc);
                    int modeFlags2 = modeFlags & 65535;
                    boolean z2 = (modeFlags & 61440) == 40960;
                    if (z2) {
                        if (!loadFileStatus(str, memoryAlloc)) {
                            return FileAttributes.BROKEN_SYMLINK;
                        }
                        modeFlags2 = getModeFlags(memoryAlloc) & 65535;
                    }
                    boolean z3 = FileSystemUtil.DO_NOT_RESOLVE_SYMLINKS ? false : z2;
                    int i = modeFlags2 & 61440;
                    boolean z4 = i == 16384;
                    boolean z5 = (z4 || i == 32768) ? false : true;
                    long j2 = memoryAlloc.getLong(this.myOffsets[1]);
                    long j3 = Native.LONG_SIZE == 4 ? memoryAlloc.getInt(this.myOffsets[2]) : memoryAlloc.getLong(this.myOffsets[2]);
                    if (this.myCoarseTs) {
                        j = 0;
                    } else {
                        j = Native.LONG_SIZE == 4 ? memoryAlloc.getInt(this.myOffsets[2] + 4) : memoryAlloc.getLong(this.myOffsets[2] + 8);
                    }
                    long j4 = (j3 * 1000) + (j / PackingOptions.SEGMENT_LIMIT);
                    if (ownFile(memoryAlloc)) {
                        if ((modeFlags2 & 146) != 0) {
                            z = true;
                        } else {
                            z = false;
                        }
                    } else if (LibC.access(str, 2) == 0) {
                        z = true;
                    } else {
                        z = false;
                    }
                    return new FileAttributes(z4, z5, z3, false, j2, j4, z);
                }
                return null;
            } finally {
                this.myMemoryPool.recycle(memoryAlloc);
            }
        }

        @Override // org.jetbrains.kotlin.com.intellij.openapi.util.io.FileSystemUtil.Mediator
        public boolean clonePermissions(String str, String str2, boolean z) {
            if (str == null) {
                $$$reportNull$$$0(2);
            }
            if (str2 == null) {
                $$$reportNull$$$0(3);
            }
            Memory memory = new Memory(256L);
            if (!loadFileStatus(str, memory)) {
                return false;
            }
            int modeFlags = getModeFlags(memory);
            int modeFlags2 = modeFlags & 511;
            if (z) {
                if (!loadFileStatus(str2, memory)) {
                    return false;
                }
                modeFlags2 = (getModeFlags(memory) & XKeySym.XK_sacute) | (modeFlags & 73);
            }
            return LibC.chmod(str2, modeFlags2) == 0;
        }

        private static boolean loadFileStatus(String str, Memory memory) {
            if (str == null) {
                $$$reportNull$$$0(4);
            }
            if (memory == null) {
                $$$reportNull$$$0(5);
            }
            return (SystemInfo.isLinux ? LinuxLibC.__xstat64(1, str, memory) : UnixLibC.stat(str, memory)) == 0;
        }

        private int getModeFlags(Memory memory) {
            if (memory == null) {
                $$$reportNull$$$0(6);
            }
            return SystemInfo.isLinux ? memory.getInt(this.myOffsets[0]) : memory.getShort(this.myOffsets[0]);
        }

        private boolean ownFile(Memory memory) {
            if (memory == null) {
                $$$reportNull$$$0(7);
            }
            return memory.getInt((long) this.myOffsets[3]) == this.myUid && memory.getInt((long) this.myOffsets[4]) == this.myGid;
        }
    }

    private static class Nio2MediatorImpl implements Mediator {
        private final PosixFilePermission[] myExecPermissions;
        private final LinkOption[] myNoFollowLinkOptions;

        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            Object[] objArr = new Object[3];
            if (i == 1) {
                objArr[0] = "path";
            } else if (i == 2) {
                objArr[0] = FirebaseAnalytics.Param.SOURCE;
            } else if (i != 3) {
                objArr[0] = "pathStr";
            } else {
                objArr[0] = "target";
            }
            objArr[1] = "org/jetbrains/kotlin/com/intellij/openapi/util/io/FileSystemUtil$Nio2MediatorImpl";
            if (i == 1) {
                objArr[2] = "resolveSymLink";
            } else if (i == 2 || i == 3) {
                objArr[2] = "clonePermissions";
            } else {
                objArr[2] = "getAttributes";
            }
            throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
        }

        private Nio2MediatorImpl() {
            this.myNoFollowLinkOptions = new LinkOption[]{LinkOption.NOFOLLOW_LINKS};
            this.myExecPermissions = new PosixFilePermission[]{PosixFilePermission.OWNER_EXECUTE, PosixFilePermission.GROUP_EXECUTE, PosixFilePermission.OTHERS_EXECUTE};
        }

        @Override // org.jetbrains.kotlin.com.intellij.openapi.util.io.FileSystemUtil.Mediator
        public FileAttributes getAttributes(String str) {
            boolean z;
            boolean zIsWritable = false;
            if (str == null) {
                $$$reportNull$$$0(0);
            }
            try {
                Path path = Paths.get(str, new String[0]);
                Class cls = SystemInfo.isWindows ? DosFileAttributes.class : PosixFileAttributes.class;
                BasicFileAttributes attributes = Files.readAttributes(path, (Class<BasicFileAttributes>) cls, this.myNoFollowLinkOptions);
                boolean z2 = attributes.isSymbolicLink() || (SystemInfo.isWindows && attributes.isOther() && attributes.isDirectory() && path.getParent() != null);
                if (z2) {
                    try {
                        attributes = Files.readAttributes(path, (Class<BasicFileAttributes>) cls, new LinkOption[0]);
                    } catch (NoSuchFileException unused) {
                        return FileAttributes.BROKEN_SYMLINK;
                    }
                }
                boolean zIsDirectory = attributes.isDirectory();
                boolean zIsOther = attributes.isOther();
                long size = attributes.size();
                long millis = attributes.lastModifiedTime().toMillis();
                if (SystemInfo.isWindows) {
                    boolean z3 = path.getParent() != null && ((DosFileAttributes) attributes).isHidden();
                    zIsWritable = zIsDirectory || !((DosFileAttributes) attributes).isReadOnly();
                    z = z3;
                } else {
                    try {
                        z = false;
                        zIsWritable = Files.isWritable(path);
                    } catch (SecurityException unused2) {
                        z = false;
                    }
                }
                return new FileAttributes(zIsDirectory, zIsOther, z2, z, size, millis, zIsWritable);
            } catch (IOException | InvalidPathException e) {
                FileSystemUtil.LOG.debug(str, e);
                return null;
            }
        }

        @Override // org.jetbrains.kotlin.com.intellij.openapi.util.io.FileSystemUtil.Mediator
        public boolean clonePermissions(String str, String str2, boolean z) throws IOException {
            if (str == null) {
                $$$reportNull$$$0(2);
            }
            if (str2 == null) {
                $$$reportNull$$$0(3);
            }
            if (!SystemInfo.isUnix) {
                return false;
            }
            Path path = Paths.get(str, new String[0]);
            Path path2 = Paths.get(str2, new String[0]);
            Set<PosixFilePermission> setPermissions = ((PosixFileAttributes) Files.readAttributes(path, PosixFileAttributes.class, new LinkOption[0])).permissions();
            Set<PosixFilePermission> setPermissions2 = ((PosixFileAttributes) Files.readAttributes(path2, PosixFileAttributes.class, new LinkOption[0])).permissions();
            if (z) {
                EnumSet enumSetCopyOf = EnumSet.copyOf((Collection) setPermissions2);
                for (PosixFilePermission posixFilePermission : this.myExecPermissions) {
                    if (setPermissions.contains(posixFilePermission)) {
                        enumSetCopyOf.add(posixFilePermission);
                    } else {
                        enumSetCopyOf.remove(posixFilePermission);
                    }
                }
                setPermissions = enumSetCopyOf;
            }
            Files.setAttribute(path2, "posix:permissions", setPermissions, new LinkOption[0]);
            return true;
        }
    }
}
