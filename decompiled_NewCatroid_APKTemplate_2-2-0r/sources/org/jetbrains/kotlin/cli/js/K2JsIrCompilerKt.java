package org.jetbrains.kotlin.cli.js;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.cli.common.ExitCode;
import org.jetbrains.kotlin.cli.common.arguments.K2JSCompilerArguments;
import org.jetbrains.kotlin.cli.common.arguments.K2JsArgumentConstants;
import org.jetbrains.kotlin.cli.common.messages.CompilerMessageSeverity;
import org.jetbrains.kotlin.cli.common.messages.MessageCollector;
import org.jetbrains.kotlin.cli.jvm.plugins.PluginCliParser;
import org.jetbrains.kotlin.config.CompilerConfiguration;
import org.jetbrains.kotlin.ir.backend.js.transformers.irToJs.JsGenerationGranularity;
import org.jetbrains.kotlin.js.config.RuntimeDiagnostic;
import org.jetbrains.kotlin.utils.PathUtil;

/* JADX INFO: compiled from: K2JsIrCompiler.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u00000\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a\u000e\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b\u001a\u001e\u0010\t\u001a\u0004\u0018\u00010\n*\u00020\u000b2\b\u0010\f\u001a\u0004\u0018\u00010\r2\u0006\u0010\u000e\u001a\u00020\u000f\"\u0018\u0010\u0000\u001a\u00020\u0001*\u00020\u00028BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u0003\u0010\u0004¨\u0006\u0010"}, d2 = {"granularity", "Lorg/jetbrains/kotlin/ir/backend/js/transformers/irToJs/JsGenerationGranularity;", "Lorg/jetbrains/kotlin/cli/common/arguments/K2JSCompilerArguments;", "getGranularity", "(Lorg/jetbrains/kotlin/cli/common/arguments/K2JSCompilerArguments;)Lorg/jetbrains/kotlin/ir/backend/js/transformers/irToJs/JsGenerationGranularity;", "loadPluginsForTests", "Lorg/jetbrains/kotlin/cli/common/ExitCode;", "configuration", "Lorg/jetbrains/kotlin/config/CompilerConfiguration;", "resolve", "Lorg/jetbrains/kotlin/js/config/RuntimeDiagnostic;", "Lorg/jetbrains/kotlin/js/config/RuntimeDiagnostic$Companion;", "value", "", "messageCollector", "Lorg/jetbrains/kotlin/cli/common/messages/MessageCollector;", "cli-js"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class K2JsIrCompilerKt {
    /* JADX INFO: Access modifiers changed from: private */
    public static final JsGenerationGranularity getGranularity(K2JSCompilerArguments k2JSCompilerArguments) {
        if (k2JSCompilerArguments.getIrPerFile()) {
            return JsGenerationGranularity.PER_FILE;
        }
        return k2JSCompilerArguments.getIrPerModule() ? JsGenerationGranularity.PER_MODULE : JsGenerationGranularity.WHOLE_PROGRAM;
    }

    public static final RuntimeDiagnostic resolve(RuntimeDiagnostic.Companion companion, String str, MessageCollector messageCollector) {
        String lowerCase;
        Intrinsics.checkNotNullParameter(companion, "<this>");
        Intrinsics.checkNotNullParameter(messageCollector, "messageCollector");
        if (str != null) {
            lowerCase = str.toLowerCase(Locale.ROOT);
            Intrinsics.checkNotNullExpressionValue(lowerCase, "toLowerCase(...)");
        } else {
            lowerCase = null;
        }
        if (Intrinsics.areEqual(lowerCase, K2JsArgumentConstants.RUNTIME_DIAGNOSTIC_LOG)) {
            return RuntimeDiagnostic.LOG;
        }
        if (Intrinsics.areEqual(lowerCase, K2JsArgumentConstants.RUNTIME_DIAGNOSTIC_EXCEPTION)) {
            return RuntimeDiagnostic.EXCEPTION;
        }
        if (lowerCase == null) {
            return null;
        }
        MessageCollector.report$default(messageCollector, CompilerMessageSeverity.STRONG_WARNING, "Unknown runtime diagnostic '" + str + '\'', null, 4, null);
        return null;
    }

    public static final ExitCode loadPluginsForTests(CompilerConfiguration configuration) {
        Intrinsics.checkNotNullParameter(configuration, "configuration");
        List listEmptyList = CollectionsKt.emptyList();
        File libPath = PathUtil.getKotlinPathsForCompiler().getLibPath();
        if (!libPath.exists() || !libPath.isDirectory()) {
            libPath = null;
        }
        if (libPath == null) {
            libPath = new File(".");
        }
        String[] kotlin_scripting_plugin_classpath_jars = PathUtil.INSTANCE.getKOTLIN_SCRIPTING_PLUGIN_CLASSPATH_JARS();
        ArrayList arrayList = new ArrayList(kotlin_scripting_plugin_classpath_jars.length);
        for (String str : kotlin_scripting_plugin_classpath_jars) {
            arrayList.add(new File(libPath, str));
        }
        ArrayList arrayList2 = new ArrayList();
        ArrayList arrayList3 = new ArrayList();
        for (Object obj : arrayList) {
            if (((File) obj).exists()) {
                arrayList2.add(obj);
            } else {
                arrayList3.add(obj);
            }
        }
        List list = (List) new Pair(arrayList2, arrayList3).component1();
        ArrayList arrayList4 = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
        Iterator it = list.iterator();
        while (it.hasNext()) {
            arrayList4.add(((File) it.next()).getCanonicalPath());
        }
        return PluginCliParser.loadPluginsSafe(CollectionsKt.plus((Collection) arrayList4, (Iterable) listEmptyList), CollectionsKt.emptyList(), CollectionsKt.emptyList(), configuration);
    }
}
