package org.jetbrains.kotlin.scripting.compiler.plugin;

import androidx.exifinterface.media.ExifInterface;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import org.apache.xerces.impl.xs.SchemaSymbols;
import org.jetbrains.kotlin.cli.common.messages.CompilerMessageSeverity;
import org.jetbrains.kotlin.cli.common.messages.MessageCollector;
import org.jetbrains.kotlin.com.intellij.mock.MockProject;
import org.jetbrains.kotlin.extensions.ProjectExtensionDescriptor;
import org.jetbrains.kotlin.utils.PathUtil;

/* JADX INFO: compiled from: pluginRegisrar.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000(\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\u001a!\u0010\u0000\u001a\u00020\u00012\b\u0010\u0002\u001a\u0004\u0018\u00010\u00032\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00010\u0005H\u0082\b\u001a1\u0010\u0006\u001a\u00020\u0001\"\b\b\u0000\u0010\u0007*\u00020\b*\b\u0012\u0004\u0012\u0002H\u00070\t2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u0002H\u0007H\u0002¢\u0006\u0002\u0010\r¨\u0006\u000e"}, d2 = {"withClassloadingProblemsReporting", "", "messageCollector", "Lorg/jetbrains/kotlin/cli/common/messages/MessageCollector;", "body", "Lkotlin/Function0;", "registerExtensionIfRequired", ExifInterface.GPS_DIRECTION_TRUE, "", "Lorg/jetbrains/kotlin/extensions/ProjectExtensionDescriptor;", "project", "Lorg/jetbrains/kotlin/com/intellij/mock/MockProject;", SchemaSymbols.ATTVAL_EXTENSION, "(Lorg/jetbrains/kotlin/extensions/ProjectExtensionDescriptor;Lcom/intellij/mock/MockProject;Ljava/lang/Object;)V", PathUtil.KOTLIN_SCRIPTING_COMPILER_PLUGIN_NAME}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class PluginRegisrarKt {
    /* JADX INFO: Access modifiers changed from: private */
    public static final <T> void registerExtensionIfRequired(ProjectExtensionDescriptor<T> projectExtensionDescriptor, MockProject mockProject, T t) {
        try {
            projectExtensionDescriptor.registerExtension(mockProject, t);
        } catch (IllegalArgumentException unused) {
        }
    }

    private static final void withClassloadingProblemsReporting(MessageCollector messageCollector, Function0<Unit> function0) {
        NoClassDefFoundError noClassDefFoundError;
        ArrayList arrayList;
        URL[] uRLs;
        try {
            function0.invoke();
            noClassDefFoundError = null;
        } catch (ClassNotFoundException e) {
            noClassDefFoundError = e;
        } catch (NoClassDefFoundError e2) {
            noClassDefFoundError = e2;
        }
        if (noClassDefFoundError != null) {
            ClassLoader classLoader = ScriptingCompilerConfigurationComponentRegistrar.class.getClassLoader();
            URLClassLoader uRLClassLoader = classLoader instanceof URLClassLoader ? (URLClassLoader) classLoader : null;
            if (uRLClassLoader == null || (uRLs = uRLClassLoader.getURLs()) == null) {
                arrayList = null;
            } else {
                Intrinsics.checkNotNull(uRLs);
                ArrayList arrayList2 = new ArrayList(uRLs.length);
                for (URL url : uRLs) {
                    arrayList2.add(url.getPath());
                }
                arrayList = arrayList2;
            }
            String str = "Error registering scripting services: " + noClassDefFoundError + "\n  current classpath:\n    " + (arrayList != null ? CollectionsKt.joinToString$default(arrayList, "\n    ", null, null, 0, null, null, 62, null) : null);
            if (messageCollector != null) {
                MessageCollector.report$default(messageCollector, CompilerMessageSeverity.STRONG_WARNING, str, null, 4, null);
            } else {
                System.err.println(str);
            }
        }
    }
}
