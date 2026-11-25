package org.jetbrains.kotlin.incremental;

import java.io.File;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.eclipse.jdt.internal.compiler.util.SuffixConstants;
import org.jetbrains.kotlin.cli.common.CLIConfigurationKeys;
import org.jetbrains.kotlin.cli.common.arguments.K2JVMCompilerArguments;
import org.jetbrains.kotlin.cli.common.config.ContentRootsKt;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.cli.jvm.config.JvmClasspathRoot;
import org.jetbrains.kotlin.cli.jvm.config.JvmContentRootsKt;
import org.jetbrains.kotlin.cli.jvm.config.JvmModulePathRoot;
import org.jetbrains.kotlin.com.intellij.psi.PsiJavaModule;
import org.jetbrains.kotlin.config.CommonConfigurationKeys;
import org.jetbrains.kotlin.config.CompilerConfiguration;
import org.jetbrains.kotlin.config.HmppCliModuleKt;
import org.jetbrains.kotlin.config.HmppCliModuleStructure;

/* JADX INFO: compiled from: IncrementalFirJvmCompilerRunner.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000*\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\"\n\u0000\n\u0002\u0010\u000e\n\u0000\u001a\u0012\u0010\u0000\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u0004\u001a0\u0010\u0005\u001a\u00020\u0001*\u00020\u00022\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u00072\f\u0010\t\u001a\b\u0012\u0004\u0012\u00020\b0\n2\b\u0010\u000b\u001a\u0004\u0018\u00010\f¨\u0006\r"}, d2 = {"configureBaseRoots", "", "Lorg/jetbrains/kotlin/config/CompilerConfiguration;", "args", "Lorg/jetbrains/kotlin/cli/common/arguments/K2JVMCompilerArguments;", "configureSourceRootsFromSources", "allSources", "", "Ljava/io/File;", ModuleXmlParser.COMMON_SOURCES, "", "javaPackagePrefix", "", "incremental-compilation-impl"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class IncrementalFirJvmCompilerRunnerKt {
    public static final void configureBaseRoots(CompilerConfiguration compilerConfiguration, K2JVMCompilerArguments args) {
        boolean z;
        File[] fileArrListFiles;
        Intrinsics.checkNotNullParameter(compilerConfiguration, "<this>");
        Intrinsics.checkNotNullParameter(args, "args");
        String[] javaSourceRoots = args.getJavaSourceRoots();
        if (javaSourceRoots != null) {
            z = false;
            for (String str : javaSourceRoots) {
                File file = new File(str);
                String javaPackagePrefix = args.getJavaPackagePrefix();
                JvmContentRootsKt.addJavaSourceRoot(compilerConfiguration, file, javaPackagePrefix);
                if (!z && javaPackagePrefix == null) {
                    if (Intrinsics.areEqual(file.getName(), PsiJavaModule.MODULE_INFO_FILE)) {
                        z = true;
                        break;
                    }
                    if (file.isDirectory() && (fileArrListFiles = file.listFiles()) != null) {
                        Intrinsics.checkNotNull(fileArrListFiles);
                        for (File file2 : fileArrListFiles) {
                            if (Intrinsics.areEqual(file2.getName(), PsiJavaModule.MODULE_INFO_FILE)) {
                                z = true;
                                break;
                                break;
                            }
                        }
                    }
                }
            }
        } else {
            z = false;
        }
        String classpath = args.getClasspath();
        if (classpath != null) {
            String str2 = classpath;
            String pathSeparator = File.pathSeparator;
            Intrinsics.checkNotNullExpressionValue(pathSeparator, "pathSeparator");
            List<String> listSplit$default = StringsKt.split$default((CharSequence) str2, new String[]{pathSeparator}, false, 0, 6, (Object) null);
            if (listSplit$default != null) {
                for (String str3 : listSplit$default) {
                    compilerConfiguration.add(CLIConfigurationKeys.CONTENT_ROOTS, z ? new JvmModulePathRoot(new File(str3)) : new JvmClasspathRoot(new File(str3)));
                }
            }
        }
    }

    public static final void configureSourceRootsFromSources(CompilerConfiguration compilerConfiguration, Collection<? extends File> allSources, Set<? extends File> commonSources, String str) {
        Intrinsics.checkNotNullParameter(compilerConfiguration, "<this>");
        Intrinsics.checkNotNullParameter(allSources, "allSources");
        Intrinsics.checkNotNullParameter(commonSources, "commonSources");
        HmppCliModuleStructure hmppCliModuleStructure = (HmppCliModuleStructure) compilerConfiguration.get(CommonConfigurationKeys.HMPP_MODULE_STRUCTURE);
        for (File file : allSources) {
            String name2 = file.getName();
            Intrinsics.checkNotNullExpressionValue(name2, "getName(...)");
            if (StringsKt.endsWith$default(name2, SuffixConstants.SUFFIX_STRING_java, false, 2, (Object) null)) {
                JvmContentRootsKt.addJavaSourceRoot(compilerConfiguration, file, str);
            } else {
                String path = file.getPath();
                Intrinsics.checkNotNull(path);
                ContentRootsKt.addKotlinSourceRoot(compilerConfiguration, path, commonSources.contains(file), hmppCliModuleStructure != null ? HmppCliModuleKt.getModuleNameForSource(hmppCliModuleStructure, path) : null);
                if (file.isDirectory()) {
                    JvmContentRootsKt.addJavaSourceRoot(compilerConfiguration, file, str);
                }
            }
        }
    }
}
