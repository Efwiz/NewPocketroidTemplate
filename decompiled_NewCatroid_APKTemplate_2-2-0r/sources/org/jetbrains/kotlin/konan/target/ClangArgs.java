package org.jetbrains.kotlin.konan.target;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.konan.file.File;

/* JADX INFO: compiled from: ClangArgs.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000J\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0010\u0011\n\u0002\b\u0006\n\u0002\u0010 \n\u0002\b\u000e\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b6\u0018\u00002\u00020\u0001:\u0002/0B\u0017\b\u0004\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J%\u0010*\u001a\b\u0012\u0004\u0012\u00020\b0\u00152\u0012\u0010+\u001a\n\u0012\u0006\b\u0001\u0012\u00020\b0\u000e\"\u00020\b¢\u0006\u0002\u0010,J%\u0010-\u001a\b\u0012\u0004\u0012\u00020\b0\u00152\u0012\u0010+\u001a\n\u0012\u0006\b\u0001\u0012\u00020\b0\u000e\"\u00020\b¢\u0006\u0002\u0010,J%\u0010.\u001a\b\u0012\u0004\u0012\u00020\b0\u00152\u0012\u0010+\u001a\n\u0012\u0006\b\u0001\u0012\u00020\b0\u000e\"\u00020\b¢\u0006\u0002\u0010,R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u0019\u0010\r\u001a\b\u0012\u0004\u0012\u00020\b0\u000e¢\u0006\n\n\u0002\u0010\u0011\u001a\u0004\b\u000f\u0010\u0010R\u0019\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\b0\u000e¢\u0006\n\n\u0002\u0010\u0011\u001a\u0004\b\u0013\u0010\u0010R\u001a\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\b0\u00158BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u0016\u0010\u0017R\u0017\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\b0\u0015¢\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u0017R\u0019\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\b0\u000e¢\u0006\n\n\u0002\u0010\u0011\u001a\u0004\b\u001b\u0010\u0010R\u0014\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\b0\u0015X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u0017\u0010\u001d\u001a\b\u0012\u0004\u0012\u00020\b0\u0015¢\u0006\b\n\u0000\u001a\u0004\b\u001e\u0010\u0017R\u0014\u0010\u001f\u001a\b\u0012\u0004\u0012\u00020\b0\u0015X\u0082\u0004¢\u0006\u0002\n\u0000R\u0017\u0010 \u001a\b\u0012\u0004\u0012\u00020\b0\u0015¢\u0006\b\n\u0000\u001a\u0004\b!\u0010\u0017R\u0014\u0010\"\u001a\b\u0012\u0004\u0012\u00020\b0\u0015X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010#\u001a\u00020$X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010%\u001a\b\u0012\u0004\u0012\u00020\b0\u0015X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010&\u001a\b\u0012\u0004\u0012\u00020\b0\u0015X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010'\u001a\b\u0012\u0004\u0012\u00020\b0\u0015X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010(\u001a\u00020)X\u0082\u0004¢\u0006\u0002\n\u0000\u0082\u0001\u000212¨\u00063"}, d2 = {"Lorg/jetbrains/kotlin/konan/target/ClangArgs;", "", "configurables", "Lorg/jetbrains/kotlin/konan/target/Configurables;", "forJni", "", "(Lorg/jetbrains/kotlin/konan/target/Configurables;Z)V", "absoluteLlvmHome", "", "absoluteTargetSysRoot", "absoluteTargetToolchain", "argsForWindowsJni", "binDir", "clangArgs", "", "getClangArgs", "()[Ljava/lang/String;", "[Ljava/lang/String;", "clangArgsForKonanSources", "getClangArgsForKonanSources", "clangArgsSpecificForKonanSources", "", "getClangArgsSpecificForKonanSources", "()Ljava/util/List;", "clangPaths", "getClangPaths", "clangXXArgs", "getClangXXArgs", "commonClangArgs", "libclangArgs", "getLibclangArgs", "libclangSpecificArgs", "libclangXXArgs", "getLibclangXXArgs", "specificClangArgs", "target", "Lorg/jetbrains/kotlin/konan/target/KonanTarget;", "targetArCmd", "targetClangCmd", "targetClangXXCmd", "targetTriple", "Lorg/jetbrains/kotlin/konan/target/TargetTriple;", "clangC", "userArgs", "([Ljava/lang/String;)Ljava/util/List;", "clangCXX", "llvmAr", "Jni", "Native", "Lorg/jetbrains/kotlin/konan/target/ClangArgs$Jni;", "Lorg/jetbrains/kotlin/konan/target/ClangArgs$Native;", "kotlin-native-utils"}, k = 1, mv = {1, 5, 1}, xi = 48)
public abstract class ClangArgs {
    private final String absoluteLlvmHome;
    private final String absoluteTargetSysRoot;
    private final String absoluteTargetToolchain;
    private final boolean argsForWindowsJni;
    private final String binDir;
    private final String[] clangArgs;
    private final String[] clangArgsForKonanSources;
    private final List<String> clangPaths;
    private final String[] clangXXArgs;
    private final List<String> commonClangArgs;
    private final Configurables configurables;
    private final boolean forJni;
    private final List<String> libclangArgs;
    private final List<String> libclangSpecificArgs;
    private final List<String> libclangXXArgs;
    private final List<String> specificClangArgs;
    private final KonanTarget target;
    private final List<String> targetArCmd;
    private final List<String> targetClangCmd;
    private final List<String> targetClangXXCmd;
    private final TargetTriple targetTriple;

    /* JADX INFO: compiled from: ClangArgs.kt */
    @Metadata(k = 3, mv = {1, 5, 1}, xi = 48)
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[Family.values().length];
            try {
                iArr[Family.OSX.ordinal()] = 1;
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
                iArr[Family.WATCHOS.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    public /* synthetic */ ClangArgs(Configurables configurables, boolean z, DefaultConstructorMarker defaultConstructorMarker) {
        this(configurables, z);
    }

    private ClangArgs(Configurables configurables, boolean z) throws TargetSupportException {
        String str;
        int i;
        String string;
        String str2;
        List<String> listEmptyList;
        String[] strArr;
        this.configurables = configurables;
        this.forJni = z;
        String absoluteTargetToolchain = configurables.getAbsoluteTargetToolchain();
        this.absoluteTargetToolchain = absoluteTargetToolchain;
        String absoluteTargetSysRoot = configurables.getAbsoluteTargetSysRoot();
        this.absoluteTargetSysRoot = absoluteTargetSysRoot;
        String absoluteLlvmHome = configurables.getAbsoluteLlvmHome();
        this.absoluteLlvmHome = absoluteLlvmHome;
        KonanTarget target = configurables.getTarget();
        this.target = target;
        TargetTriple targetTriple = configurables.getTargetTriple();
        this.targetTriple = targetTriple;
        boolean z2 = z && Intrinsics.areEqual(target, KonanTarget.MINGW_X64.INSTANCE);
        this.argsForWindowsJni = z2;
        KonanTarget host = HostManager.INSTANCE.getHost();
        if (Intrinsics.areEqual(host, KonanTarget.LINUX_X64.INSTANCE) || Intrinsics.areEqual(host, KonanTarget.MINGW_X64.INSTANCE)) {
            str = absoluteTargetToolchain + "/bin";
        } else {
            if (!(Intrinsics.areEqual(host, KonanTarget.MACOS_X64.INSTANCE) ? true : Intrinsics.areEqual(host, KonanTarget.MACOS_ARM64.INSTANCE))) {
                throw new TargetSupportException("Unexpected host platform", null, 2, null);
            }
            str = absoluteTargetToolchain + "/usr/bin";
        }
        String str3 = str;
        this.binDir = str3;
        ArrayList arrayList = new ArrayList();
        if (!z2) {
            arrayList.add(CollectionsKt.listOf("-B" + str3));
        } else {
            if (!(configurables instanceof MingwConfigurables)) {
                throw new IllegalArgumentException("Failed requirement.".toString());
            }
            arrayList.add(((MingwConfigurables) configurables).getMsvc().compilerFlags());
            arrayList.add(((MingwConfigurables) configurables).getWindowsKit().compilerFlags());
            arrayList.add(CollectionsKt.listOf("-fuse-ld=lld"));
        }
        arrayList.add(CollectionsKt.listOf("-fno-stack-protector"));
        if (configurables instanceof GccConfigurables) {
            arrayList.add(CollectionsKt.listOf("--gcc-toolchain=" + ((GccConfigurables) configurables).getAbsoluteGccToolchain()));
        }
        if (z2) {
            string = "x86_64-pc-windows-msvc";
            i = 2;
        } else if (configurables instanceof AppleConfigurables) {
            i = 2;
            string = TargetTriple.copy$default(targetTriple, null, null, targetTriple.getOs() + (Intrinsics.areEqual(target, KonanTarget.MACOS_ARM64.INSTANCE) ? "10.16" : ((AppleConfigurables) configurables).getOsVersionMin()), null, 11, null).toString();
        } else {
            i = 2;
            string = configurables.getTargetTriple().toString();
        }
        String[] strArr2 = new String[i];
        strArr2[0] = "-target";
        strArr2[1] = string;
        arrayList.add(CollectionsKt.listOf((Object[]) strArr2));
        if (!((configurables instanceof ZephyrConfigurables) || (configurables instanceof WasmConfigurables) || (configurables instanceof AndroidConfigurables) || z2)) {
            if (configurables instanceof AppleConfigurables) {
                String[] strArr3 = new String[i];
                strArr3[0] = "-isysroot";
                strArr3[1] = absoluteTargetSysRoot;
                arrayList.add(CollectionsKt.listOf((Object[]) strArr3));
            } else {
                arrayList.add(CollectionsKt.listOf("--sysroot=" + absoluteTargetSysRoot));
            }
        }
        if (!(configurables instanceof MingwConfigurables)) {
            arrayList.add(CollectionsKt.listOf("-fPIC"));
        }
        int i2 = WhenMappings.$EnumSwitchMapping$0[target.getFamily().ordinal()];
        if (i2 == 1) {
            str2 = "__ENVIRONMENT_MAC_OS_X_VERSION_MIN_REQUIRED__";
        } else if (i2 == i) {
            str2 = "__ENVIRONMENT_IPHONE_OS_VERSION_MIN_REQUIRED__";
        } else if (i2 == 3) {
            str2 = "__ENVIRONMENT_TV_OS_VERSION_MIN_REQUIRED__";
        } else {
            str2 = i2 != 4 ? null : "__ENVIRONMENT_WATCH_OS_VERSION_MIN_REQUIRED__";
        }
        if (str2 != null) {
            arrayList.add(CollectionsKt.listOf("-D__ENVIRONMENT_OS_VERSION_MIN_REQUIRED__=" + str2));
        }
        List<String> listFlatten = CollectionsKt.flatten(arrayList);
        this.commonClangArgs = listFlatten;
        if (Intrinsics.areEqual(target, KonanTarget.LINUX_ARM32_HFP.INSTANCE)) {
            String[] strArr4 = new String[i];
            strArr4[0] = "-mfpu=vfp";
            strArr4[1] = "-mfloat-abi=hard";
            listEmptyList = CollectionsKt.listOf((Object[]) strArr4);
        } else {
            if (Intrinsics.areEqual(target, KonanTarget.IOS_ARM32.INSTANCE) ? true : Intrinsics.areEqual(target, KonanTarget.WATCHOS_ARM32.INSTANCE)) {
                listEmptyList = CollectionsKt.listOf("-marm");
            } else {
                if (Intrinsics.areEqual(target, KonanTarget.ANDROID_ARM32.INSTANCE) ? true : Intrinsics.areEqual(target, KonanTarget.ANDROID_ARM64.INSTANCE) ? true : Intrinsics.areEqual(target, KonanTarget.ANDROID_X86.INSTANCE) ? true : Intrinsics.areEqual(target, KonanTarget.ANDROID_X64.INSTANCE)) {
                    String strWithoutVendor = TargetTripleKt.withoutVendor(targetTriple);
                    String strArchitectureDirForTarget = Android.INSTANCE.architectureDirForTarget(target);
                    String str4 = absoluteTargetToolchain + "/sysroot";
                    String[] strArr5 = new String[5];
                    strArr5[0] = "-D__ANDROID_API__=21";
                    strArr5[1] = "--sysroot=" + absoluteTargetSysRoot + '/' + strArchitectureDirForTarget;
                    strArr5[i] = "-I" + str4 + "/usr/include/c++/v1";
                    strArr5[3] = "-I" + str4 + "/usr/include";
                    strArr5[4] = "-I" + str4 + "/usr/include/" + strWithoutVendor;
                    listEmptyList = CollectionsKt.listOf((Object[]) strArr5);
                } else if (Intrinsics.areEqual(target, KonanTarget.WASM32.INSTANCE)) {
                    String[] strArr6 = new String[18];
                    strArr6[0] = "-fno-rtti";
                    strArr6[1] = "-fno-exceptions";
                    strArr6[i] = "-fvisibility=default";
                    strArr6[3] = "-D_LIBCPP_ABI_VERSION=2";
                    strArr6[4] = "-D_LIBCPP_NO_EXCEPTIONS=1";
                    strArr6[5] = "-nostdinc";
                    strArr6[6] = "-Xclang";
                    strArr6[7] = "-nobuiltininc";
                    strArr6[8] = "-Xclang";
                    strArr6[9] = "-nostdsysteminc";
                    strArr6[10] = "-Xclang";
                    strArr6[11] = "-isystem" + absoluteTargetSysRoot + "/include/libcxx";
                    strArr6[12] = "-Xclang";
                    strArr6[13] = "-isystem" + absoluteTargetSysRoot + "/lib/libcxxabi/include";
                    strArr6[14] = "-Xclang";
                    strArr6[15] = "-isystem" + absoluteTargetSysRoot + "/include/compat";
                    strArr6[16] = "-Xclang";
                    strArr6[17] = "-isystem" + absoluteTargetSysRoot + "/include/libc";
                    listEmptyList = CollectionsKt.listOf((Object[]) strArr6);
                } else if (target instanceof KonanTarget.ZEPHYR) {
                    String[] strArr7 = new String[11];
                    strArr7[0] = "-fno-rtti";
                    strArr7[1] = "-fno-exceptions";
                    strArr7[i] = "-fno-asynchronous-unwind-tables";
                    strArr7[3] = "-fno-pie";
                    strArr7[4] = "-fno-pic";
                    strArr7[5] = "-fshort-enums";
                    strArr7[6] = "-nostdinc";
                    strArr7[7] = "-isystem " + configurables.getTargetToolchain() + "/../lib/gcc/arm-none-eabi/7.2.1/include";
                    strArr7[8] = "-isystem " + configurables.getTargetToolchain() + "/../lib/gcc/arm-none-eabi/7.2.1/include-fixed";
                    strArr7[9] = "-isystem" + absoluteTargetSysRoot + "/include/libcxx";
                    strArr7[10] = "-isystem" + absoluteTargetSysRoot + "/include/libc";
                    List listListOf = CollectionsKt.listOf((Object[]) strArr7);
                    Intrinsics.checkNotNull(configurables, "null cannot be cast to non-null type org.jetbrains.kotlin.konan.target.ZephyrConfigurables");
                    listEmptyList = CollectionsKt.plus((Collection) listListOf, (Iterable) ConfigurablesExtensionsKt.constructClangArgs((ZephyrConfigurables) configurables));
                } else {
                    listEmptyList = CollectionsKt.emptyList();
                }
            }
        }
        this.specificClangArgs = listEmptyList;
        String[] strArr8 = new String[i];
        strArr8[0] = absoluteLlvmHome + "/bin";
        strArr8[1] = str3;
        this.clangPaths = CollectionsKt.listOf((Object[]) strArr8);
        String[] strArr9 = (String[]) CollectionsKt.plus((Collection) listFlatten, (Iterable) listEmptyList).toArray(new String[0]);
        this.clangArgs = strArr9;
        if (configurables instanceof AppleConfigurables) {
            strArr = new String[3];
            strArr[0] = "-stdlib=libc++";
            strArr[1] = "-Dat_quick_exit=atexit";
            strArr[i] = "-Dquick_exit=exit";
        } else {
            strArr = new String[0];
        }
        String[] strArr10 = (String[]) ArraysKt.plus((Object[]) strArr9, (Object[]) strArr);
        this.clangXXArgs = strArr10;
        this.clangArgsForKonanSources = (String[]) ArraysKt.plus((Object[]) strArr10, (Collection) getClangArgsSpecificForKonanSources());
        String[] strArr11 = new String[i];
        strArr11[0] = "-isystem";
        strArr11[1] = absoluteLlvmHome + "/lib/clang/" + configurables.getLlvmVersion() + "/include";
        List<String> listListOf2 = CollectionsKt.listOf((Object[]) strArr11);
        this.libclangSpecificArgs = listListOf2;
        this.libclangArgs = CollectionsKt.plus((Collection) listListOf2, (Object[]) strArr9);
        this.libclangXXArgs = CollectionsKt.plus((Collection) listListOf2, (Object[]) strArr10);
        this.targetClangCmd = CollectionsKt.plus((Collection) CollectionsKt.listOf(absoluteLlvmHome + "/bin/clang"), (Object[]) strArr9);
        this.targetClangXXCmd = CollectionsKt.plus((Collection) CollectionsKt.listOf(absoluteLlvmHome + "/bin/clang++"), (Object[]) strArr10);
        this.targetArCmd = CollectionsKt.listOf(absoluteLlvmHome + "/bin/llvm-ar");
    }

    private final List<String> getClangArgsSpecificForKonanSources() {
        String[] strArr = new String[18];
        String strName = this.target.getArchitecture().name();
        if (Intrinsics.areEqual(this.target, KonanTarget.WATCHOS_ARM64.INSTANCE)) {
            strName = null;
        }
        strArr[0] = strName;
        strArr[1] = Intrinsics.areEqual(this.target, KonanTarget.WATCHOS_ARM64.INSTANCE) ? "ARM32" : null;
        String strName2 = this.target.getFamily().name();
        if (this.target.getFamily() == Family.MINGW) {
            strName2 = null;
        }
        strArr[2] = strName2;
        strArr[3] = this.target.getFamily() == Family.MINGW ? "WINDOWS" : null;
        strArr[4] = this.target.getFamily() == Family.OSX ? "MACOSX" : null;
        strArr[5] = !KonanTargetExtenstionsKt.supportsThreads(this.target) ? "NO_THREADS" : null;
        strArr[6] = !KonanTargetExtenstionsKt.supportsExceptions(this.target) ? "NO_EXCEPTIONS" : null;
        strArr[7] = !KonanTargetExtenstionsKt.suportsMemMem(this.target) ? "NO_MEMMEM" : null;
        strArr[8] = !KonanTargetExtenstionsKt.supports64BitAtomics(this.target) ? "NO_64BIT_ATOMIC" : null;
        strArr[9] = !KonanTargetExtenstionsKt.supportsUnalignedAccess(this.target) ? "NO_UNALIGNED_ACCESS" : null;
        strArr[10] = !KonanTargetExtenstionsKt.supports64BitMulOverflow(this.target) ? "FORBID_BUILTIN_MUL_OVERFLOW" : null;
        strArr[11] = KonanTargetExtenstionsKt.supportsObjcInterop(this.target) ? "OBJC_INTEROP" : null;
        strArr[12] = KonanTargetExtenstionsKt.hasFoundationFramework(this.target) ? "HAS_FOUNDATION_FRAMEWORK" : null;
        strArr[13] = KonanTargetExtenstionsKt.hasUIKitFramework(this.target) ? "HAS_UIKIT_FRAMEWORK" : null;
        strArr[14] = KonanTargetExtenstionsKt.supportsIosCrashLog(this.target) ? "REPORT_BACKTRACE_TO_IOS_CRASH_LOG" : null;
        strArr[15] = KonanTargetExtenstionsKt.needSmallBinary(this.target) ? "NEED_SMALL_BINARY" : null;
        strArr[16] = KonanTargetExtenstionsKt.hasAddressDependencyInMemoryModel(this.target) ? "TARGET_HAS_ADDRESS_DEPENDENCY" : null;
        strArr[17] = KonanTargetExtenstionsKt.getSupportsGrandCentralDispatch(this.target) ? "SUPPORTS_GRAND_CENTRAL_DISPATCH" : null;
        List listListOfNotNull = CollectionsKt.listOfNotNull((Object[]) strArr);
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(listListOfNotNull, 10));
        Iterator it = listListOfNotNull.iterator();
        while (it.hasNext()) {
            arrayList.add("KONAN_" + ((String) it.next()) + "=1");
        }
        ArrayList arrayList2 = arrayList;
        String[] strArr2 = new String[9];
        strArr2[0] = KonanTargetExtenstionsKt.binaryFormat(this.target) == BinaryFormat.ELF ? "USE_ELF_SYMBOLS=1" : null;
        String str = "ELFSIZE=" + KonanTargetExtenstionsKt.pointerBits(this.target);
        if (KonanTargetExtenstionsKt.binaryFormat(this.target) != BinaryFormat.ELF) {
            str = null;
        }
        strArr2[1] = str;
        String str2 = "MACHSIZE=" + KonanTargetExtenstionsKt.pointerBits(this.target);
        if (KonanTargetExtenstionsKt.binaryFormat(this.target) != BinaryFormat.MACH_O) {
            str2 = null;
        }
        strArr2[2] = str2;
        strArr2[3] = this.target.getFamily() == Family.ANDROID ? "__ANDROID__" : null;
        strArr2[4] = KonanTargetExtenstionsKt.binaryFormat(this.target) == BinaryFormat.PE_COFF ? "USE_PE_COFF_SYMBOLS=1" : null;
        strArr2[5] = this.target.getFamily() == Family.MINGW ? "UNICODE" : null;
        strArr2[6] = KonanTargetExtenstionsKt.supportsWinAPIUnwind(this.target) ? "USE_WINAPI_UNWIND=1" : null;
        strArr2[7] = KonanTargetExtenstionsKt.supportsGccUnwind(this.target) ? "USE_GCC_UNWIND=1" : null;
        strArr2[8] = this.target.getFamily().isAppleFamily() ? "NS_FORMAT_ARGUMENT(A)=" : null;
        List listPlus = CollectionsKt.plus((Collection) CollectionsKt.plus((Collection) arrayList2, (Iterable) CollectionsKt.listOfNotNull((Object[]) strArr2)), (Iterable) KonanTargetExtenstionsKt.customArgsForKonanSources(this.target));
        ArrayList arrayList3 = new ArrayList(CollectionsKt.collectionSizeOrDefault(listPlus, 10));
        Iterator it2 = listPlus.iterator();
        while (it2.hasNext()) {
            arrayList3.add("-D" + ((String) it2.next()));
        }
        return arrayList3;
    }

    public final List<String> getClangPaths() {
        return this.clangPaths;
    }

    public final String[] getClangArgs() {
        return this.clangArgs;
    }

    public final String[] getClangXXArgs() {
        return this.clangXXArgs;
    }

    public final String[] getClangArgsForKonanSources() {
        return this.clangArgsForKonanSources;
    }

    public final List<String> getLibclangArgs() {
        return this.libclangArgs;
    }

    public final List<String> getLibclangXXArgs() {
        return this.libclangXXArgs;
    }

    public final List<String> clangC(String... userArgs) {
        Intrinsics.checkNotNullParameter(userArgs, "userArgs");
        return CollectionsKt.plus((Collection) this.targetClangCmd, (Iterable) ArraysKt.asList(userArgs));
    }

    public final List<String> clangCXX(String... userArgs) {
        Intrinsics.checkNotNullParameter(userArgs, "userArgs");
        return CollectionsKt.plus((Collection) this.targetClangXXCmd, (Iterable) ArraysKt.asList(userArgs));
    }

    public final List<String> llvmAr(String... userArgs) {
        Intrinsics.checkNotNullParameter(userArgs, "userArgs");
        return CollectionsKt.plus((Collection) this.targetArCmd, (Iterable) ArraysKt.asList(userArgs));
    }

    /* JADX INFO: compiled from: ClangArgs.kt */
    @Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0011\n\u0002\u0010\u000e\n\u0002\b\t\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004R!\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u00068FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b\n\u0010\u000b\u001a\u0004\b\b\u0010\tR\u001b\u0010\f\u001a\u00020\u00078BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u000f\u0010\u000b\u001a\u0004\b\r\u0010\u000e¨\u0006\u0010"}, d2 = {"Lorg/jetbrains/kotlin/konan/target/ClangArgs$Jni;", "Lorg/jetbrains/kotlin/konan/target/ClangArgs;", "configurables", "Lorg/jetbrains/kotlin/konan/target/Configurables;", "(Lorg/jetbrains/kotlin/konan/target/Configurables;)V", "hostCompilerArgsForJni", "", "", "getHostCompilerArgsForJni", "()[Ljava/lang/String;", "hostCompilerArgsForJni$delegate", "Lkotlin/Lazy;", "jdkDir", "getJdkDir", "()Ljava/lang/String;", "jdkDir$delegate", "kotlin-native-utils"}, k = 1, mv = {1, 5, 1}, xi = 48)
    public static final class Jni extends ClangArgs {

        /* JADX INFO: renamed from: hostCompilerArgsForJni$delegate, reason: from kotlin metadata */
        private final Lazy hostCompilerArgsForJni;

        /* JADX INFO: renamed from: jdkDir$delegate, reason: from kotlin metadata */
        private final Lazy jdkDir;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public Jni(Configurables configurables) {
            super(configurables, true, null);
            Intrinsics.checkNotNullParameter(configurables, "configurables");
            this.jdkDir = LazyKt.lazy(new Function0<String>() { // from class: org.jetbrains.kotlin.konan.target.ClangArgs$Jni$jdkDir$2
                @Override // kotlin.jvm.functions.Function0
                public final String invoke() {
                    File absoluteFile = File.INSTANCE.getJavaHome().getAbsoluteFile();
                    if (absoluteFile.child("include").getExists()) {
                        return absoluteFile.getAbsolutePath();
                    }
                    return absoluteFile.getParentFile().getAbsolutePath();
                }
            });
            this.hostCompilerArgsForJni = LazyKt.lazy(new Function0<String[]>() { // from class: org.jetbrains.kotlin.konan.target.ClangArgs$Jni$hostCompilerArgsForJni$2
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final String[] invoke() {
                    List listListOf = CollectionsKt.listOf((Object[]) new String[]{"", HostManager.INSTANCE.getJniHostPlatformIncludeDir()});
                    ClangArgs.Jni jni = this.this$0;
                    ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(listListOf, 10));
                    Iterator it = listListOf.iterator();
                    while (it.hasNext()) {
                        arrayList.add("-I" + jni.getJdkDir() + "/include/" + ((String) it.next()));
                    }
                    return (String[]) arrayList.toArray(new String[0]);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final String getJdkDir() {
            return (String) this.jdkDir.getValue();
        }

        public final String[] getHostCompilerArgsForJni() {
            return (String[]) this.hostCompilerArgsForJni.getValue();
        }
    }

    /* JADX INFO: compiled from: ClangArgs.kt */
    @Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004¨\u0006\u0005"}, d2 = {"Lorg/jetbrains/kotlin/konan/target/ClangArgs$Native;", "Lorg/jetbrains/kotlin/konan/target/ClangArgs;", "configurables", "Lorg/jetbrains/kotlin/konan/target/Configurables;", "(Lorg/jetbrains/kotlin/konan/target/Configurables;)V", "kotlin-native-utils"}, k = 1, mv = {1, 5, 1}, xi = 48)
    public static final class Native extends ClangArgs {
        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public Native(Configurables configurables) {
            super(configurables, false, null);
            Intrinsics.checkNotNullParameter(configurables, "configurables");
        }
    }
}
