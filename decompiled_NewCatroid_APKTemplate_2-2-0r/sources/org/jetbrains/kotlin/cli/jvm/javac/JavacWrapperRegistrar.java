package org.jetbrains.kotlin.cli.jvm.javac;

import com.sun.tools.javac.util.Context;
import java.io.File;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.asJava.LightClassGenerationSupport;
import org.jetbrains.kotlin.cli.common.CLIConfigurationKeys;
import org.jetbrains.kotlin.cli.common.messages.CompilerMessageSeverity;
import org.jetbrains.kotlin.cli.common.messages.MessageCollector;
import org.jetbrains.kotlin.cli.jvm.compiler.JvmPackagePartProvider;
import org.jetbrains.kotlin.cli.jvm.config.JvmContentRootsKt;
import org.jetbrains.kotlin.com.intellij.mock.MockProject;
import org.jetbrains.kotlin.config.CompilerConfiguration;
import org.jetbrains.kotlin.config.JVMConfigurationKeys;
import org.jetbrains.kotlin.javac.JavacWrapper;
import org.jetbrains.kotlin.psi.KtFile;

/* JADX INFO: compiled from: JavacWrapperRegistrar.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000J\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0011\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J}\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\f\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\r0\f2\f\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u000f0\f2\u000e\u0010\u0010\u001a\n\u0012\u0004\u0012\u00020\u0004\u0018\u00010\u00112\u000e\u0010\u0012\u001a\n\u0012\u0004\u0012\u00020\r\u0018\u00010\f2\u000e\u0010\u0013\u001a\n\u0012\u0004\u0012\u00020\r\u0018\u00010\f2\u0006\u0010\u0014\u001a\u00020\u00152\f\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00170\f¢\u0006\u0002\u0010\u0018R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000¨\u0006\u0019"}, d2 = {"Lorg/jetbrains/kotlin/cli/jvm/javac/JavacWrapperRegistrar;", "", "()V", "JAVAC_CONTEXT_CLASS", "", "registerJavac", "", "project", "Lorg/jetbrains/kotlin/com/intellij/mock/MockProject;", "configuration", "Lorg/jetbrains/kotlin/config/CompilerConfiguration;", "javaFiles", "", "Ljava/io/File;", "kotlinFiles", "Lorg/jetbrains/kotlin/psi/KtFile;", "arguments", "", "bootClasspath", "sourcePath", "lightClassGenerationSupport", "Lorg/jetbrains/kotlin/asJava/LightClassGenerationSupport;", "packagePartsProviders", "Lorg/jetbrains/kotlin/cli/jvm/compiler/JvmPackagePartProvider;", "(Lcom/intellij/mock/MockProject;Lorg/jetbrains/kotlin/config/CompilerConfiguration;Ljava/util/List;Ljava/util/List;[Ljava/lang/String;Ljava/util/List;Ljava/util/List;Lorg/jetbrains/kotlin/asJava/LightClassGenerationSupport;Ljava/util/List;)Z", "cli-base"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class JavacWrapperRegistrar {
    public static final JavacWrapperRegistrar INSTANCE = new JavacWrapperRegistrar();

    private JavacWrapperRegistrar() {
    }

    public final boolean registerJavac(MockProject project, CompilerConfiguration configuration, List<? extends File> javaFiles, List<? extends KtFile> kotlinFiles, String[] strArr, List<? extends File> list, List<? extends File> list2, LightClassGenerationSupport lightClassGenerationSupport, List<JvmPackagePartProvider> packagePartsProviders) {
        Intrinsics.checkNotNullParameter(project, "project");
        Intrinsics.checkNotNullParameter(configuration, "configuration");
        Intrinsics.checkNotNullParameter(javaFiles, "javaFiles");
        Intrinsics.checkNotNullParameter(kotlinFiles, "kotlinFiles");
        Intrinsics.checkNotNullParameter(lightClassGenerationSupport, "lightClassGenerationSupport");
        Intrinsics.checkNotNullParameter(packagePartsProviders, "packagePartsProviders");
        Object notNull = configuration.getNotNull(CLIConfigurationKeys.MESSAGE_COLLECTOR_KEY);
        Intrinsics.checkNotNullExpressionValue(notNull, "getNotNull(...)");
        MessageCollector messageCollector = (MessageCollector) notNull;
        try {
            Class.forName("com.sun.tools.javac.util.Context");
            Context context = new Context();
            JavacLogger.INSTANCE.preRegister(context, messageCollector);
            List<File> jvmClasspathRoots = JvmContentRootsKt.getJvmClasspathRoots(configuration);
            File file = (File) configuration.get(JVMConfigurationKeys.OUTPUT_DIRECTORY);
            project.registerService((Class<JavacWrapper>) JavacWrapper.class, new JavacWrapper(javaFiles, kotlinFiles, strArr, jvmClasspathRoots, list, list2, new JavacWrapperKotlinResolverImpl(lightClassGenerationSupport), packagePartsProviders, configuration.getBoolean(JVMConfigurationKeys.COMPILE_JAVA), file, context));
            return true;
        } catch (ClassNotFoundException unused) {
            MessageCollector.report$default(messageCollector, CompilerMessageSeverity.ERROR, "'com.sun.tools.javac.util.Context' class can't be found ('tools.jar' is not found)", null, 4, null);
            return false;
        }
    }
}
