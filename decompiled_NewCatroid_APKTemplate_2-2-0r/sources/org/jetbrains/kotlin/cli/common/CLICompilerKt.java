package org.jetbrains.kotlin.cli.common;

import java.io.File;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.cli.common.messages.CompilerMessageSeverity;
import org.jetbrains.kotlin.cli.common.messages.MessageCollector;

/* JADX INFO: compiled from: CLICompiler.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000\u001c\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0010\u000e\n\u0002\b\u0003\u001a@\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00012\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u00062\f\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00070\u00062\f\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006¨\u0006\n"}, d2 = {"checkPluginsArguments", "", "messageCollector", "Lorg/jetbrains/kotlin/cli/common/messages/MessageCollector;", "useK2", "pluginClasspaths", "", "", "pluginOptions", "pluginConfigurations", "cli"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class CLICompilerKt {
    public static final boolean checkPluginsArguments(MessageCollector messageCollector, boolean z, List<String> pluginClasspaths, List<String> pluginOptions, List<String> pluginConfigurations) {
        Intrinsics.checkNotNullParameter(messageCollector, "messageCollector");
        Intrinsics.checkNotNullParameter(pluginClasspaths, "pluginClasspaths");
        Intrinsics.checkNotNullParameter(pluginOptions, "pluginOptions");
        Intrinsics.checkNotNullParameter(pluginConfigurations, "pluginConfigurations");
        for (String str : pluginClasspaths) {
            if (!new File(str).exists()) {
                MessageCollector.report$default(messageCollector, CompilerMessageSeverity.ERROR, "Plugin classpath entry points to a non-existent location: " + str, null, 4, null);
            }
        }
        boolean z2 = false;
        if (!pluginConfigurations.isEmpty()) {
            MessageCollector.report$default(messageCollector, CompilerMessageSeverity.WARNING, "Argument -Xcompiler-plugin is experimental", null, 4, null);
            if (!z) {
                MessageCollector.report$default(messageCollector, CompilerMessageSeverity.ERROR, "-Xcompiler-plugin argument is allowed only for language version 2.0. Please use -Xplugin argument for language version 1.9 and below", null, 4, null);
                z2 = true;
            }
            List<String> list = pluginClasspaths;
            if (!list.isEmpty() || !pluginOptions.isEmpty()) {
                StringBuilder sb = new StringBuilder();
                StringBuilder sbAppend = sb.append("Mixing legacy and modern plugin arguments is prohibited. Please use only one syntax");
                Intrinsics.checkNotNullExpressionValue(sbAppend, "append(...)");
                Intrinsics.checkNotNullExpressionValue(sbAppend.append('\n'), "append(...)");
                StringBuilder sbAppend2 = sb.append("Legacy arguments:");
                Intrinsics.checkNotNullExpressionValue(sbAppend2, "append(...)");
                Intrinsics.checkNotNullExpressionValue(sbAppend2.append('\n'), "append(...)");
                if (!list.isEmpty()) {
                    StringBuilder sbAppend3 = sb.append("  -Xplugin=" + CollectionsKt.joinToString$default(pluginClasspaths, ",", null, null, 0, null, null, 62, null));
                    Intrinsics.checkNotNullExpressionValue(sbAppend3, "append(...)");
                    Intrinsics.checkNotNullExpressionValue(sbAppend3.append('\n'), "append(...)");
                }
                Iterator<T> it = pluginOptions.iterator();
                while (it.hasNext()) {
                    StringBuilder sbAppend4 = sb.append("  -P " + ((String) it.next()));
                    Intrinsics.checkNotNullExpressionValue(sbAppend4, "append(...)");
                    Intrinsics.checkNotNullExpressionValue(sbAppend4.append('\n'), "append(...)");
                }
                StringBuilder sbAppend5 = sb.append("Modern arguments:");
                Intrinsics.checkNotNullExpressionValue(sbAppend5, "append(...)");
                Intrinsics.checkNotNullExpressionValue(sbAppend5.append('\n'), "append(...)");
                Iterator<T> it2 = pluginConfigurations.iterator();
                while (it2.hasNext()) {
                    StringBuilder sbAppend6 = sb.append("  -Xcompiler-plugin=" + ((String) it2.next()));
                    Intrinsics.checkNotNullExpressionValue(sbAppend6, "append(...)");
                    Intrinsics.checkNotNullExpressionValue(sbAppend6.append('\n'), "append(...)");
                }
                String string = sb.toString();
                Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
                MessageCollector.report$default(messageCollector, CompilerMessageSeverity.ERROR, string, null, 4, null);
                z2 = true;
            }
        }
        return !z2;
    }
}
