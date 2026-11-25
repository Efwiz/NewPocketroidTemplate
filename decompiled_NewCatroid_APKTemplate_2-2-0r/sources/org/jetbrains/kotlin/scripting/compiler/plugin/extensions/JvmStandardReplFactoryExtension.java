package org.jetbrains.kotlin.scripting.compiler.plugin.extensions;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.JvmClassMappingKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.cli.common.CLIConfigurationKeys;
import org.jetbrains.kotlin.cli.common.extensions.ReplFactoryExtension;
import org.jetbrains.kotlin.cli.common.messages.MessageCollector;
import org.jetbrains.kotlin.cli.common.repl.ReplCompiler;
import org.jetbrains.kotlin.com.intellij.core.JavaCoreProjectEnvironment;
import org.jetbrains.kotlin.com.intellij.openapi.Disposable;
import org.jetbrains.kotlin.config.CompilerConfiguration;
import org.jetbrains.kotlin.scripting.compiler.plugin.repl.GenericReplCompiler;
import org.jetbrains.kotlin.scripting.definitions.KotlinScriptDefinition;
import org.jetbrains.kotlin.scripting.resolve.KotlinScriptDefinitionFromAnnotatedTemplate;
import org.jetbrains.kotlin.utils.PathUtil;

/* JADX INFO: compiled from: JvmStandardReplFactoryExtension.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J8\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\b2\b\u0010\n\u001a\u0004\u0018\u00010\u000b2\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000fH\u0016J(\u0010\u0010\u001a\u00020\u00112\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\b2\u0006\u0010\u0005\u001a\u00020\u00062\b\u0010\n\u001a\u0004\u0018\u00010\u000bH\u0002¨\u0006\u0012"}, d2 = {"Lorg/jetbrains/kotlin/scripting/compiler/plugin/extensions/JvmStandardReplFactoryExtension;", "Lorg/jetbrains/kotlin/cli/common/extensions/ReplFactoryExtension;", "()V", "makeReplCompiler", "Lorg/jetbrains/kotlin/cli/common/repl/ReplCompiler;", "templateClassName", "", "templateClasspath", "", "Ljava/io/File;", "baseClassLoader", "Ljava/lang/ClassLoader;", "configuration", "Lorg/jetbrains/kotlin/config/CompilerConfiguration;", "projectEnvironment", "Lorg/jetbrains/kotlin/com/intellij/core/JavaCoreProjectEnvironment;", "makeScriptDefinition", "Lorg/jetbrains/kotlin/scripting/definitions/KotlinScriptDefinition;", PathUtil.KOTLIN_SCRIPTING_COMPILER_PLUGIN_NAME}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class JvmStandardReplFactoryExtension implements ReplFactoryExtension {
    @Override // org.jetbrains.kotlin.cli.common.extensions.ReplFactoryExtension
    public ReplCompiler makeReplCompiler(String templateClassName, List<? extends File> templateClasspath, ClassLoader baseClassLoader, CompilerConfiguration configuration, JavaCoreProjectEnvironment projectEnvironment) {
        Intrinsics.checkNotNullParameter(templateClassName, "templateClassName");
        Intrinsics.checkNotNullParameter(templateClasspath, "templateClasspath");
        Intrinsics.checkNotNullParameter(configuration, "configuration");
        Intrinsics.checkNotNullParameter(projectEnvironment, "projectEnvironment");
        Disposable parentDisposable = projectEnvironment.getParentDisposable();
        Intrinsics.checkNotNullExpressionValue(parentDisposable, "getParentDisposable(...)");
        KotlinScriptDefinition kotlinScriptDefinitionMakeScriptDefinition = makeScriptDefinition(templateClasspath, templateClassName, baseClassLoader);
        Object notNull = configuration.getNotNull(CLIConfigurationKeys.MESSAGE_COLLECTOR_KEY);
        Intrinsics.checkNotNullExpressionValue(notNull, "getNotNull(...)");
        return new GenericReplCompiler(parentDisposable, kotlinScriptDefinitionMakeScriptDefinition, configuration, (MessageCollector) notNull);
    }

    private final KotlinScriptDefinition makeScriptDefinition(List<? extends File> templateClasspath, String templateClassName, ClassLoader baseClassLoader) {
        try {
            List<? extends File> list = templateClasspath;
            ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
            Iterator<T> it = list.iterator();
            while (it.hasNext()) {
                arrayList.add(((File) it.next()).toURI().toURL());
            }
            Class clsLoadClass = new URLClassLoader((URL[]) arrayList.toArray(new URL[0]), baseClassLoader).loadClass(templateClassName);
            Intrinsics.checkNotNull(clsLoadClass);
            return new KotlinScriptDefinitionFromAnnotatedTemplate(JvmClassMappingKt.getKotlinClass(clsLoadClass), MapsKt.emptyMap(), null, 4, null);
        } catch (ClassNotFoundException e) {
            throw new IllegalStateException("Cannot find script definition template class " + templateClassName, e);
        } catch (Exception e2) {
            throw new IllegalStateException("Error loading script definition template " + templateClassName, e2);
        }
    }
}
