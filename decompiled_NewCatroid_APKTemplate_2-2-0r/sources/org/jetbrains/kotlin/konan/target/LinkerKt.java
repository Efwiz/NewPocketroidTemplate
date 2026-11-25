package org.jetbrains.kotlin.konan.target;

import java.util.List;
import kotlin.Metadata;
import kotlin.NotImplementedError;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.jetbrains.kotlin.konan.exec.Command;
import org.jetbrains.kotlin.name.JvmNames;

/* JADX INFO: compiled from: Linker.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000,\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\u001a\u000e\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0003\u001aB\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u00052\u0006\u0010\u0007\u001a\u00020\b2\n\u0010\t\u001a\u00060\bj\u0002`\n2\u0010\u0010\u000b\u001a\f\u0012\b\u0012\u00060\bj\u0002`\f0\u00052\f\u0010\r\u001a\b\u0012\u0004\u0012\u00020\b0\u0005H\u0002*\n\u0010\u000e\"\u00020\b2\u00020\b*\n\u0010\u000f\"\u00020\b2\u00020\b¨\u0006\u0010"}, d2 = {"linker", "Lorg/jetbrains/kotlin/konan/target/LinkerFlags;", "configurables", "Lorg/jetbrains/kotlin/konan/target/Configurables;", "staticGnuArCommands", "", "Lorg/jetbrains/kotlin/konan/exec/Command;", "ar", "", "executable", "Lorg/jetbrains/kotlin/konan/target/ExecutableFile;", "objectFiles", "Lorg/jetbrains/kotlin/konan/target/ObjectFile;", "libraries", "ExecutableFile", "ObjectFile", "kotlin-native-utils"}, k = 2, mv = {1, 5, 1}, xi = 48)
public final class LinkerKt {
    /* JADX INFO: Access modifiers changed from: private */
    public static final List<Command> staticGnuArCommands(String str, String str2, List<String> list, List<String> list2) {
        if (HostManager.INSTANCE.getHostIsMingw()) {
            String str3 = StringsKt.replace$default(str2, '/', '\\', false, 4, (Object) null) + JvmNames.MULTIFILE_PART_NAME_DELIMITER;
            String strReplace$default = StringsKt.replace$default(str, '/', '\\', false, 4, (Object) null);
            Command command = new Command(strReplace$default, "-rucT", str3);
            command.unaryPlus(list);
            command.unaryPlus(list2);
            Unit unit = Unit.INSTANCE;
            Command command2 = new Command("cmd", "/c");
            command2.unaryPlus("(echo create " + str2 + " & echo addlib " + str3 + " & echo save & echo end) | " + strReplace$default + " -M");
            Unit unit2 = Unit.INSTANCE;
            return CollectionsKt.listOf((Object[]) new Command[]{command, command2, new Command("cmd", "/c", "del", "/q", str3)});
        }
        if (!HostManager.INSTANCE.getHostIsLinux() && !HostManager.INSTANCE.getHostIsMac()) {
            throw new NotImplementedError("An operation is not implemented: " + ("Unsupported host " + HostManager.INSTANCE.getHost()));
        }
        Command command3 = new Command(str, "cqT", str2);
        command3.unaryPlus(list);
        command3.unaryPlus(list2);
        Unit unit3 = Unit.INSTANCE;
        Command command4 = new Command("/bin/sh", "-c");
        command4.unaryPlus("printf 'create " + str2 + "\\naddlib " + str2 + "\\nsave\\nend' | " + str + " -M");
        Unit unit4 = Unit.INSTANCE;
        return CollectionsKt.listOf((Object[]) new Command[]{command3, command4});
    }

    public static final LinkerFlags linker(Configurables configurables) {
        Intrinsics.checkNotNullParameter(configurables, "configurables");
        if (configurables instanceof GccConfigurables) {
            return new GccBasedLinker((GccConfigurables) configurables);
        }
        if (configurables instanceof AppleConfigurables) {
            return new MacOSBasedLinker((AppleConfigurables) configurables);
        }
        if (configurables instanceof AndroidConfigurables) {
            return new AndroidLinker((AndroidConfigurables) configurables);
        }
        if (configurables instanceof MingwConfigurables) {
            return new MingwLinker((MingwConfigurables) configurables);
        }
        if (configurables instanceof WasmConfigurables) {
            return new WasmLinker((WasmConfigurables) configurables);
        }
        if (configurables instanceof ZephyrConfigurables) {
            return new ZephyrLinker((ZephyrConfigurables) configurables);
        }
        throw new IllegalStateException(("Unexpected target: " + configurables.getTarget()).toString());
    }
}
