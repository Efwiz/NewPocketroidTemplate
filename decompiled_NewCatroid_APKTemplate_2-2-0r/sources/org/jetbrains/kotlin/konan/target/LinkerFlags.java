package org.jetbrains.kotlin.konan.target;

import com.google.android.gms.common.Scopes;
import java.util.List;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.konan.exec.Command;

/* JADX INFO: compiled from: Linker.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000N\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0002\b\t\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0007\b&\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u001c\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\b0\u00162\f\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\b0\u0016H&J\u0084\u0001\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u00190\u00162\u0010\u0010\u001a\u001a\f\u0012\b\u0012\u00060\bj\u0002`\u001b0\u00162\n\u0010\u001c\u001a\u00060\bj\u0002`\u001d2\f\u0010\u001e\u001a\b\u0012\u0004\u0012\u00020\b0\u00162\f\u0010\u001f\u001a\b\u0012\u0004\u0012\u00020\b0\u00162\u0006\u0010 \u001a\u00020\u00122\u0006\u0010!\u001a\u00020\u00122\u0006\u0010\"\u001a\u00020#2\u0006\u0010$\u001a\u00020\b2\u0006\u0010%\u001a\u00020\u00122\u0006\u0010&\u001a\u00020\u00122\n\b\u0002\u0010'\u001a\u0004\u0018\u00010(H&J\u001c\u0010)\u001a\b\u0012\u0004\u0012\u00020\b0\u00162\f\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\b0\u0016H\u0016J,\u0010*\u001a\b\u0012\u0004\u0012\u00020\u00190\u00162\u0010\u0010\u001a\u001a\f\u0012\b\u0012\u00060\bj\u0002`\u001b0\u00162\n\u0010+\u001a\u00060\bj\u0002`\u001bH\u0016J\u001c\u0010,\u001a\u0004\u0018\u00010\b2\u0006\u0010-\u001a\u00020\b2\b\b\u0002\u0010.\u001a\u00020\u0012H\u0016R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006R\u0014\u0010\u0007\u001a\u00020\bX\u0084\u0004¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0014\u0010\u000b\u001a\u00020\bX\u0084\u0004¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\nR\u001d\u0010\r\u001a\u0004\u0018\u00010\b8DX\u0084\u0084\u0002¢\u0006\f\n\u0004\b\u000f\u0010\u0010\u001a\u0004\b\u000e\u0010\nR\u0014\u0010\u0011\u001a\u00020\u00128VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0013\u0010\u0014¨\u0006/"}, d2 = {"Lorg/jetbrains/kotlin/konan/target/LinkerFlags;", "", "configurables", "Lorg/jetbrains/kotlin/konan/target/Configurables;", "(Lorg/jetbrains/kotlin/konan/target/Configurables;)V", "getConfigurables", "()Lorg/jetbrains/kotlin/konan/target/Configurables;", "llvmBin", "", "getLlvmBin", "()Ljava/lang/String;", "llvmLib", "getLlvmLib", "profileLibrary", "getProfileLibrary", "profileLibrary$delegate", "Lkotlin/Lazy;", "useCompilerDriverAsLinker", "", "getUseCompilerDriverAsLinker", "()Z", "filterStaticLibraries", "", "binaries", "finalLinkCommands", "Lorg/jetbrains/kotlin/konan/exec/Command;", "objectFiles", "Lorg/jetbrains/kotlin/konan/target/ObjectFile;", "executable", "Lorg/jetbrains/kotlin/konan/target/ExecutableFile;", "libraries", "linkerArgs", "optimize", "debug", Namer.METADATA_CLASS_KIND, "Lorg/jetbrains/kotlin/konan/target/LinkerOutputKind;", "outputDsymBundle", "needsProfileLibrary", "mimallocEnabled", "sanitizer", "Lorg/jetbrains/kotlin/konan/target/SanitizerKind;", "linkStaticLibraries", "preLinkCommands", "output", "provideCompilerRtLibrary", "libraryName", "isDynamic", "kotlin-native-utils"}, k = 1, mv = {1, 5, 1}, xi = 48)
public abstract class LinkerFlags {
    private final Configurables configurables;
    private final String llvmBin;
    private final String llvmLib;

    /* JADX INFO: renamed from: profileLibrary$delegate, reason: from kotlin metadata */
    private final Lazy profileLibrary;

    public abstract List<String> filterStaticLibraries(List<String> binaries);

    public abstract List<Command> finalLinkCommands(List<String> objectFiles, String executable, List<String> libraries, List<String> linkerArgs, boolean optimize, boolean debug, LinkerOutputKind kind, String outputDsymBundle, boolean needsProfileLibrary, boolean mimallocEnabled, SanitizerKind sanitizer);

    public boolean getUseCompilerDriverAsLinker() {
        return false;
    }

    public LinkerFlags(Configurables configurables) {
        Intrinsics.checkNotNullParameter(configurables, "configurables");
        this.configurables = configurables;
        this.llvmBin = configurables.getAbsoluteLlvmHome() + "/bin";
        this.llvmLib = configurables.getAbsoluteLlvmHome() + "/lib";
        this.profileLibrary = LazyKt.lazy(new Function0<String>() { // from class: org.jetbrains.kotlin.konan.target.LinkerFlags$profileLibrary$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final String invoke() {
                return LinkerFlags.provideCompilerRtLibrary$default(this.this$0, Scopes.PROFILE, false, 2, null);
            }
        });
    }

    public final Configurables getConfigurables() {
        return this.configurables;
    }

    protected final String getLlvmBin() {
        return this.llvmBin;
    }

    protected final String getLlvmLib() {
        return this.llvmLib;
    }

    public static /* synthetic */ List finalLinkCommands$default(LinkerFlags linkerFlags, List list, String str, List list2, List list3, boolean z, boolean z2, LinkerOutputKind linkerOutputKind, String str2, boolean z3, boolean z4, SanitizerKind sanitizerKind, int i, Object obj) {
        if (obj == null) {
            return linkerFlags.finalLinkCommands(list, str, list2, list3, z, z2, linkerOutputKind, str2, z3, z4, (i & 1024) != 0 ? null : sanitizerKind);
        }
        throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: finalLinkCommands");
    }

    public List<Command> preLinkCommands(List<String> objectFiles, String output) {
        Intrinsics.checkNotNullParameter(objectFiles, "objectFiles");
        Intrinsics.checkNotNullParameter(output, "output");
        throw new IllegalStateException(("Pre-link is unsupported for " + this.configurables.getTarget() + '.').toString());
    }

    public List<String> linkStaticLibraries(List<String> binaries) {
        Intrinsics.checkNotNullParameter(binaries, "binaries");
        return filterStaticLibraries(binaries);
    }

    public static /* synthetic */ String provideCompilerRtLibrary$default(LinkerFlags linkerFlags, String str, boolean z, int i, Object obj) {
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: provideCompilerRtLibrary");
        }
        if ((i & 2) != 0) {
            z = false;
        }
        return linkerFlags.provideCompilerRtLibrary(str, z);
    }

    public String provideCompilerRtLibrary(String libraryName, boolean isDynamic) {
        Intrinsics.checkNotNullParameter(libraryName, "libraryName");
        System.err.println("Can't provide " + libraryName + '.');
        return null;
    }

    protected final String getProfileLibrary() {
        return (String) this.profileLibrary.getValue();
    }
}
