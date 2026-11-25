package org.jetbrains.kotlin.com.intellij.ide.plugins;

import java.io.File;
import java.nio.file.Path;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.jetbrains.kotlin.com.intellij.openapi.diagnostic.Logger;

/* JADX INFO: compiled from: IdeaPluginDescriptorImpl.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000D\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010 \n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001c\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0000\u001a&\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000b2\f\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u000b0\rH\u0002\u001a\u0010\u0010\u000e\u001a\u00020\u000b2\u0006\u0010\u000f\u001a\u00020\u0010H\u0000\u001a\u0016\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\t0\r*\b\u0012\u0004\u0012\u00020\u00130\u0012\u001a\u0016\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00130\u0015*\b\u0012\u0004\u0012\u00020\u00160\u0012\"\u0014\u0010\u0000\u001a\u00020\u00018BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u0002\u0010\u0003\"\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0017"}, d2 = {"LOG", "Lorg/jetbrains/kotlin/com/intellij/openapi/diagnostic/Logger;", "getLOG", "()Lcom/intellij/openapi/diagnostic/Logger;", "checkCompatibilityFlag", "", "checkCycle", "", "descriptor", "Lorg/jetbrains/kotlin/com/intellij/ide/plugins/IdeaPluginDescriptorImpl;", "configFile", "", "visitedFiles", "", "pluginPathToUserString", "file", "Ljava/nio/file/Path;", "toPluginDescriptors", "", "Lorg/jetbrains/kotlin/com/intellij/openapi/extensions/PluginId;", "toPluginSet", "", "Lorg/jetbrains/kotlin/com/intellij/ide/plugins/IdeaPluginDescriptor;", "intellij.platform.core.impl"}, k = 2, mv = {1, 5, 1})
public final class IdeaPluginDescriptorImplKt {
    private static final boolean checkCompatibilityFlag = !Intrinsics.areEqual(System.getProperty("idea.plugin.check.compatibility", "true"), "false");

    /* JADX INFO: Access modifiers changed from: private */
    public static final Logger getLOG() {
        Logger logger = PluginManagerCore.getLogger();
        Intrinsics.checkNotNullExpressionValue(logger, "PluginManagerCore.getLogger()");
        return logger;
    }

    public static final String pluginPathToUserString(Path file) {
        Intrinsics.checkNotNullParameter(file, "file");
        return StringsKt.replace$default(file.toString(), System.getProperty("user.home") + File.separatorChar, "~" + File.separatorChar, false, 4, (Object) null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void checkCycle(IdeaPluginDescriptorImpl ideaPluginDescriptorImpl, String str, List<String> list) {
        int size = list.size();
        for (int i = 0; i < size; i++) {
            if (Intrinsics.areEqual(str, list.get(i))) {
                throw new RuntimeException("Plugin " + ideaPluginDescriptorImpl + " optional descriptors form a cycle: " + String.join(", ", list.subList(i, list.size())));
            }
        }
    }
}
