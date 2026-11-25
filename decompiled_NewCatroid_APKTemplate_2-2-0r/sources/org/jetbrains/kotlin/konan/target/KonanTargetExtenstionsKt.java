package org.jetbrains.kotlin.konan.target;

import java.util.List;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: KonanTargetExtenstions.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u00000\n\u0000\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u000e\u001a\f\u0010\u0005\u001a\u0004\u0018\u00010\u0006*\u00020\u0002\u001a\u0010\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\b*\u00020\u0002\u001a\n\u0010\n\u001a\u00020\u0001*\u00020\u0002\u001a\n\u0010\u000b\u001a\u00020\u0001*\u00020\u0002\u001a\n\u0010\f\u001a\u00020\u0001*\u00020\u0002\u001a\n\u0010\r\u001a\u00020\u0001*\u00020\u0002\u001a\n\u0010\u000e\u001a\u00020\u000f*\u00020\u0002\u001a\n\u0010\u0010\u001a\u00020\u0001*\u00020\u0002\u001a\u0010\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00120\b*\u00020\u0002\u001a\n\u0010\u0013\u001a\u00020\u0001*\u00020\u0002\u001a\n\u0010\u0014\u001a\u00020\u0001*\u00020\u0002\u001a\n\u0010\u0015\u001a\u00020\u0001*\u00020\u0002\u001a\n\u0010\u0016\u001a\u00020\u0001*\u00020\u0002\u001a\n\u0010\u0017\u001a\u00020\u0001*\u00020\u0002\u001a\n\u0010\u0018\u001a\u00020\u0001*\u00020\u0002\u001a\n\u0010\u0019\u001a\u00020\u0001*\u00020\u0002\u001a\n\u0010\u001a\u001a\u00020\u0001*\u00020\u0002\u001a\n\u0010\u001b\u001a\u00020\u0001*\u00020\u0002\u001a\n\u0010\u001c\u001a\u00020\u0001*\u00020\u0002\u001a\n\u0010\u001d\u001a\u00020\u0001*\u00020\u0002\u001a\n\u0010\u001e\u001a\u00020\u0001*\u00020\u0002\u001a\n\u0010\u001f\u001a\u00020\u0001*\u00020\u0002\"\u0015\u0010\u0000\u001a\u00020\u0001*\u00020\u00028F¢\u0006\u0006\u001a\u0004\b\u0003\u0010\u0004¨\u0006 "}, d2 = {"supportsGrandCentralDispatch", "", "Lorg/jetbrains/kotlin/konan/target/KonanTarget;", "getSupportsGrandCentralDispatch", "(Lorg/jetbrains/kotlin/konan/target/KonanTarget;)Z", "binaryFormat", "Lorg/jetbrains/kotlin/konan/target/BinaryFormat;", "customArgsForKonanSources", "", "", "hasAddressDependencyInMemoryModel", "hasFoundationFramework", "hasUIKitFramework", "needSmallBinary", "pointerBits", "", "suportsMemMem", "supportedSanitizers", "Lorg/jetbrains/kotlin/konan/target/SanitizerKind;", "supports64BitAtomics", "supports64BitMulOverflow", "supportsCodeCoverage", "supportsCoreSymbolication", "supportsExceptions", "supportsGccUnwind", "supportsIosCrashLog", "supportsLibBacktrace", "supportsMimallocAllocator", "supportsObjcInterop", "supportsThreads", "supportsUnalignedAccess", "supportsWinAPIUnwind", "kotlin-native-utils"}, k = 2, mv = {1, 5, 1}, xi = 48)
public final class KonanTargetExtenstionsKt {

    /* JADX INFO: compiled from: KonanTargetExtenstions.kt */
    @Metadata(k = 3, mv = {1, 5, 1}, xi = 48)
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;
        public static final /* synthetic */ int[] $EnumSwitchMapping$1;

        static {
            int[] iArr = new int[Family.values().length];
            try {
                iArr[Family.WATCHOS.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[Family.IOS.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[Family.TVOS.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                iArr[Family.OSX.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                iArr[Family.ANDROID.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                iArr[Family.LINUX.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                iArr[Family.MINGW.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                iArr[Family.WASM.ordinal()] = 8;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                iArr[Family.ZEPHYR.ordinal()] = 9;
            } catch (NoSuchFieldError unused9) {
            }
            $EnumSwitchMapping$0 = iArr;
            int[] iArr2 = new int[Architecture.values().length];
            try {
                iArr2[Architecture.X64.ordinal()] = 1;
            } catch (NoSuchFieldError unused10) {
            }
            try {
                iArr2[Architecture.X86.ordinal()] = 2;
            } catch (NoSuchFieldError unused11) {
            }
            try {
                iArr2[Architecture.ARM64.ordinal()] = 3;
            } catch (NoSuchFieldError unused12) {
            }
            try {
                iArr2[Architecture.ARM32.ordinal()] = 4;
            } catch (NoSuchFieldError unused13) {
            }
            try {
                iArr2[Architecture.MIPS32.ordinal()] = 5;
            } catch (NoSuchFieldError unused14) {
            }
            try {
                iArr2[Architecture.MIPSEL32.ordinal()] = 6;
            } catch (NoSuchFieldError unused15) {
            }
            try {
                iArr2[Architecture.WASM32.ordinal()] = 7;
            } catch (NoSuchFieldError unused16) {
            }
            $EnumSwitchMapping$1 = iArr2;
        }
    }

    public static final boolean supportsCodeCoverage(KonanTarget konanTarget) {
        Intrinsics.checkNotNullParameter(konanTarget, "<this>");
        return false;
    }

    public static final BinaryFormat binaryFormat(KonanTarget konanTarget) {
        Intrinsics.checkNotNullParameter(konanTarget, "<this>");
        switch (WhenMappings.$EnumSwitchMapping$0[konanTarget.getFamily().ordinal()]) {
            case 1:
                return BinaryFormat.MACH_O;
            case 2:
                return BinaryFormat.MACH_O;
            case 3:
                return BinaryFormat.MACH_O;
            case 4:
                return BinaryFormat.MACH_O;
            case 5:
                return BinaryFormat.ELF;
            case 6:
                return BinaryFormat.ELF;
            case 7:
                return BinaryFormat.PE_COFF;
            case 8:
            case 9:
                return null;
            default:
                throw new NoWhenBranchMatchedException();
        }
    }

    public static final int pointerBits(KonanTarget konanTarget) {
        Intrinsics.checkNotNullParameter(konanTarget, "<this>");
        switch (WhenMappings.$EnumSwitchMapping$1[konanTarget.getArchitecture().ordinal()]) {
            case 1:
                return 64;
            case 2:
            case 4:
            case 5:
            case 6:
            case 7:
                break;
            case 3:
                if (!Intrinsics.areEqual(konanTarget, KonanTarget.WATCHOS_ARM64.INSTANCE)) {
                    return 64;
                }
                break;
            default:
                throw new NoWhenBranchMatchedException();
        }
        return 32;
    }

    public static final boolean supportsMimallocAllocator(KonanTarget konanTarget) {
        Intrinsics.checkNotNullParameter(konanTarget, "<this>");
        if ((konanTarget instanceof KonanTarget.LINUX_X64) || (konanTarget instanceof KonanTarget.MINGW_X86) || (konanTarget instanceof KonanTarget.MINGW_X64) || (konanTarget instanceof KonanTarget.MACOS_X64) || (konanTarget instanceof KonanTarget.MACOS_ARM64) || (konanTarget instanceof KonanTarget.LINUX_ARM64) || (konanTarget instanceof KonanTarget.LINUX_ARM32_HFP) || (konanTarget instanceof KonanTarget.ANDROID_X64) || (konanTarget instanceof KonanTarget.ANDROID_ARM64) || (konanTarget instanceof KonanTarget.IOS_ARM32) || (konanTarget instanceof KonanTarget.IOS_ARM64) || (konanTarget instanceof KonanTarget.IOS_X64) || (konanTarget instanceof KonanTarget.IOS_SIMULATOR_ARM64)) {
            return true;
        }
        if (!(konanTarget instanceof KonanTarget.WATCHOS_ARM32 ? true : konanTarget instanceof KonanTarget.WATCHOS_ARM64 ? true : konanTarget instanceof KonanTarget.WATCHOS_SIMULATOR_ARM64 ? true : konanTarget instanceof KonanTarget.WATCHOS_X64 ? true : konanTarget instanceof KonanTarget.WATCHOS_X86 ? true : konanTarget instanceof KonanTarget.TVOS_ARM64 ? true : konanTarget instanceof KonanTarget.TVOS_SIMULATOR_ARM64 ? true : konanTarget instanceof KonanTarget.TVOS_X64 ? true : konanTarget instanceof KonanTarget.ANDROID_X86 ? true : konanTarget instanceof KonanTarget.ANDROID_ARM32)) {
            if (!(konanTarget instanceof KonanTarget.LINUX_MIPS32 ? true : konanTarget instanceof KonanTarget.LINUX_MIPSEL32) && !(konanTarget instanceof KonanTarget.WASM32)) {
                boolean z = konanTarget instanceof KonanTarget.ZEPHYR;
            }
        }
        return false;
    }

    public static final boolean supportsLibBacktrace(KonanTarget konanTarget) {
        Intrinsics.checkNotNullParameter(konanTarget, "<this>");
        if (konanTarget.getFamily().isAppleFamily()) {
            return true;
        }
        return (konanTarget.getFamily() == Family.LINUX && !CollectionsKt.listOf((Object[]) new Architecture[]{Architecture.MIPS32, Architecture.MIPSEL32}).contains(konanTarget.getArchitecture())) || konanTarget.getFamily() == Family.ANDROID;
    }

    public static final boolean supportsCoreSymbolication(KonanTarget konanTarget) {
        Intrinsics.checkNotNullParameter(konanTarget, "<this>");
        return CollectionsKt.listOf((Object[]) new KonanTarget[]{KonanTarget.MACOS_X64.INSTANCE, KonanTarget.MACOS_ARM64.INSTANCE, KonanTarget.IOS_X64.INSTANCE, KonanTarget.IOS_SIMULATOR_ARM64.INSTANCE, KonanTarget.TVOS_X64.INSTANCE, KonanTarget.TVOS_SIMULATOR_ARM64.INSTANCE, KonanTarget.WATCHOS_X86.INSTANCE, KonanTarget.WATCHOS_X64.INSTANCE, KonanTarget.WATCHOS_SIMULATOR_ARM64.INSTANCE}).contains(konanTarget);
    }

    public static final boolean supportsGccUnwind(KonanTarget konanTarget) {
        Intrinsics.checkNotNullParameter(konanTarget, "<this>");
        return konanTarget.getFamily() == Family.ANDROID || konanTarget.getFamily() == Family.LINUX || (konanTarget instanceof KonanTarget.MINGW_X86);
    }

    public static final boolean supportsWinAPIUnwind(KonanTarget konanTarget) {
        Intrinsics.checkNotNullParameter(konanTarget, "<this>");
        return konanTarget instanceof KonanTarget.MINGW_X64;
    }

    public static final boolean supportsThreads(KonanTarget konanTarget) {
        Intrinsics.checkNotNullParameter(konanTarget, "<this>");
        return ((konanTarget instanceof KonanTarget.WASM32) || (konanTarget instanceof KonanTarget.ZEPHYR)) ? false : true;
    }

    public static final boolean supportsExceptions(KonanTarget konanTarget) {
        Intrinsics.checkNotNullParameter(konanTarget, "<this>");
        return ((konanTarget instanceof KonanTarget.WASM32) || (konanTarget instanceof KonanTarget.ZEPHYR)) ? false : true;
    }

    public static final boolean suportsMemMem(KonanTarget konanTarget) {
        Intrinsics.checkNotNullParameter(konanTarget, "<this>");
        return ((konanTarget instanceof KonanTarget.WASM32) || (konanTarget instanceof KonanTarget.MINGW_X86) || (konanTarget instanceof KonanTarget.MINGW_X64) || (konanTarget instanceof KonanTarget.ZEPHYR)) ? false : true;
    }

    public static final boolean supportsObjcInterop(KonanTarget konanTarget) {
        Intrinsics.checkNotNullParameter(konanTarget, "<this>");
        return konanTarget.getFamily().isAppleFamily();
    }

    public static final boolean hasFoundationFramework(KonanTarget konanTarget) {
        Intrinsics.checkNotNullParameter(konanTarget, "<this>");
        return konanTarget.getFamily().isAppleFamily();
    }

    public static final boolean hasUIKitFramework(KonanTarget konanTarget) {
        Intrinsics.checkNotNullParameter(konanTarget, "<this>");
        return konanTarget.getFamily() == Family.IOS || konanTarget.getFamily() == Family.TVOS;
    }

    public static final boolean supports64BitMulOverflow(KonanTarget konanTarget) {
        Intrinsics.checkNotNullParameter(konanTarget, "<this>");
        return ((konanTarget instanceof KonanTarget.MINGW_X86) || (konanTarget instanceof KonanTarget.LINUX_ARM32_HFP) || (konanTarget instanceof KonanTarget.LINUX_MIPS32) || (konanTarget instanceof KonanTarget.LINUX_MIPSEL32) || (konanTarget instanceof KonanTarget.WASM32) || (konanTarget instanceof KonanTarget.ZEPHYR) || (konanTarget instanceof KonanTarget.ANDROID_ARM32) || (konanTarget instanceof KonanTarget.ANDROID_X86)) ? false : true;
    }

    public static final boolean supportsIosCrashLog(KonanTarget konanTarget) {
        Intrinsics.checkNotNullParameter(konanTarget, "<this>");
        return Intrinsics.areEqual(konanTarget, KonanTarget.IOS_ARM32.INSTANCE) || Intrinsics.areEqual(konanTarget, KonanTarget.IOS_ARM64.INSTANCE) || Intrinsics.areEqual(konanTarget, KonanTarget.WATCHOS_ARM32.INSTANCE) || Intrinsics.areEqual(konanTarget, KonanTarget.WATCHOS_ARM64.INSTANCE) || Intrinsics.areEqual(konanTarget, KonanTarget.TVOS_ARM64.INSTANCE);
    }

    public static final boolean supports64BitAtomics(KonanTarget konanTarget) {
        Intrinsics.checkNotNullParameter(konanTarget, "<this>");
        switch (WhenMappings.$EnumSwitchMapping$1[konanTarget.getArchitecture().ordinal()]) {
            case 1:
            case 2:
            case 3:
                if (!Intrinsics.areEqual(konanTarget, KonanTarget.WATCHOS_ARM64.INSTANCE) && !Intrinsics.areEqual(konanTarget, KonanTarget.WATCHOS_X86.INSTANCE)) {
                    return true;
                }
            case 4:
            case 5:
            case 6:
            case 7:
                return false;
            default:
                throw new NoWhenBranchMatchedException();
        }
    }

    public static final boolean supportsUnalignedAccess(KonanTarget konanTarget) {
        Intrinsics.checkNotNullParameter(konanTarget, "<this>");
        switch (WhenMappings.$EnumSwitchMapping$1[konanTarget.getArchitecture().ordinal()]) {
            case 1:
            case 2:
            case 3:
                if (!Intrinsics.areEqual(konanTarget, KonanTarget.WATCHOS_ARM64.INSTANCE)) {
                    return true;
                }
            case 4:
            case 5:
            case 6:
            case 7:
                return false;
            default:
                throw new NoWhenBranchMatchedException();
        }
    }

    public static final boolean needSmallBinary(KonanTarget konanTarget) {
        Intrinsics.checkNotNullParameter(konanTarget, "<this>");
        if (konanTarget.getFamily() == Family.WATCHOS) {
            return true;
        }
        return konanTarget.getFamily().isAppleFamily() && konanTarget.getArchitecture() == Architecture.ARM32;
    }

    public static final List<SanitizerKind> supportedSanitizers(KonanTarget konanTarget) {
        Intrinsics.checkNotNullParameter(konanTarget, "<this>");
        if (konanTarget instanceof KonanTarget.LINUX_X64) {
            return CollectionsKt.listOf(SanitizerKind.ADDRESS);
        }
        return konanTarget instanceof KonanTarget.MACOS_X64 ? CollectionsKt.listOf(SanitizerKind.THREAD) : CollectionsKt.emptyList();
    }

    public static final boolean hasAddressDependencyInMemoryModel(KonanTarget konanTarget) {
        Intrinsics.checkNotNullParameter(konanTarget, "<this>");
        switch (WhenMappings.$EnumSwitchMapping$1[konanTarget.getArchitecture().ordinal()]) {
            case 1:
            case 2:
            case 3:
            case 4:
                return true;
            case 5:
            case 6:
            case 7:
                return false;
            default:
                throw new NoWhenBranchMatchedException();
        }
    }

    public static final boolean getSupportsGrandCentralDispatch(KonanTarget konanTarget) {
        Intrinsics.checkNotNullParameter(konanTarget, "<this>");
        int i = WhenMappings.$EnumSwitchMapping$0[konanTarget.getFamily().ordinal()];
        return i == 1 || i == 2 || i == 3 || i == 4;
    }

    public static final List<String> customArgsForKonanSources(KonanTarget konanTarget) {
        Intrinsics.checkNotNullParameter(konanTarget, "<this>");
        if (Intrinsics.areEqual(konanTarget, KonanTarget.WASM32.INSTANCE)) {
            return CollectionsKt.listOf((Object[]) new String[]{"KONAN_NO_FFI=1", "KONAN_INTERNAL_DLMALLOC=1", "KONAN_INTERNAL_SNPRINTF=1", "KONAN_INTERNAL_NOW=1", "KONAN_NO_CTORS_SECTION=1", "KONAN_NO_BACKTRACE=1", "KONAN_NO_EXTERNAL_CALLS_CHECKER=1"});
        }
        if (konanTarget instanceof KonanTarget.ZEPHYR) {
            return CollectionsKt.listOf((Object[]) new String[]{"KONAN_NO_FFI=1", "KONAN_NO_MATH=1", "KONAN_INTERNAL_SNPRINTF=1", "KONAN_INTERNAL_NOW=1", "KONAN_NO_CTORS_SECTION=1", "KONAN_NO_BACKTRACE=1"});
        }
        return CollectionsKt.emptyList();
    }
}
