package org.jetbrains.kotlin.cli.jvm.config;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.cli.common.CLIConfigurationKeys;
import org.jetbrains.kotlin.cli.common.config.ContentRoot;
import org.jetbrains.kotlin.cli.common.config.KotlinSourceRoot;
import org.jetbrains.kotlin.cli.common.messages.CompilerMessageSeverity;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.cli.jvm.compiler.CoreEnvironmentUtilsKt;
import org.jetbrains.kotlin.cli.jvm.modules.CoreJrtFileSystem;
import org.jetbrains.kotlin.config.CompilerConfiguration;
import org.jetbrains.kotlin.config.CompilerConfigurationKey;
import org.jetbrains.kotlin.config.JVMConfigurationKeys;
import org.jetbrains.kotlin.utils.PathUtil;

/* JADX INFO: compiled from: JvmContentRoots.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000&\n\u0000\n\u0002\u0010\"\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\t\u001a \u0010\r\u001a\u00020\u000e*\u00020\u00032\u0006\u0010\u000f\u001a\u00020\b2\n\b\u0002\u0010\u0010\u001a\u0004\u0018\u00010\u0002H\u0007\u001a&\u0010\u0011\u001a\u00020\u000e*\u00020\u00032\f\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\b0\u00072\n\b\u0002\u0010\u0010\u001a\u0004\u0018\u00010\u0002H\u0007\u001a\u0012\u0010\u0013\u001a\u00020\u000e*\u00020\u00032\u0006\u0010\u000f\u001a\u00020\b\u001a\u0018\u0010\u0014\u001a\u00020\u000e*\u00020\u00032\f\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\b0\u0007\u001a\u0018\u0010\u0015\u001a\u00020\u000e*\u00020\u00032\f\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\b0\u0007\u001a\n\u0010\u0016\u001a\u00020\u000e*\u00020\u0003\"\u001b\u0010\u0000\u001a\b\u0012\u0004\u0012\u00020\u00020\u0001*\u00020\u00038F¢\u0006\u0006\u001a\u0004\b\u0004\u0010\u0005\"\u001b\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u0007*\u00020\u00038F¢\u0006\u0006\u001a\u0004\b\t\u0010\n\"\u001b\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\b0\u0007*\u00020\u00038F¢\u0006\u0006\u001a\u0004\b\f\u0010\n¨\u0006\u0017"}, d2 = {ModuleXmlParser.JAVA_SOURCE_ROOTS, "", "", "Lorg/jetbrains/kotlin/config/CompilerConfiguration;", "getJavaSourceRoots", "(Lorg/jetbrains/kotlin/config/CompilerConfiguration;)Ljava/util/Set;", "jvmClasspathRoots", "", "Ljava/io/File;", "getJvmClasspathRoots", "(Lorg/jetbrains/kotlin/config/CompilerConfiguration;)Ljava/util/List;", "jvmModularRoots", "getJvmModularRoots", "addJavaSourceRoot", "", "file", ModuleXmlParser.JAVA_SOURCE_PACKAGE_PREFIX, "addJavaSourceRoots", "files", "addJvmClasspathRoot", "addJvmClasspathRoots", "addJvmSdkRoots", "configureJdkClasspathRoots", "cli-base"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class JvmContentRootsKt {
    public static final void addJavaSourceRoot(CompilerConfiguration compilerConfiguration, File file) {
        Intrinsics.checkNotNullParameter(compilerConfiguration, "<this>");
        Intrinsics.checkNotNullParameter(file, "file");
        addJavaSourceRoot$default(compilerConfiguration, file, null, 2, null);
    }

    public static final void addJavaSourceRoots(CompilerConfiguration compilerConfiguration, List<? extends File> files) {
        Intrinsics.checkNotNullParameter(compilerConfiguration, "<this>");
        Intrinsics.checkNotNullParameter(files, "files");
        addJavaSourceRoots$default(compilerConfiguration, files, null, 2, null);
    }

    public static final void addJvmClasspathRoot(CompilerConfiguration compilerConfiguration, File file) {
        Intrinsics.checkNotNullParameter(compilerConfiguration, "<this>");
        Intrinsics.checkNotNullParameter(file, "file");
        compilerConfiguration.add(CLIConfigurationKeys.CONTENT_ROOTS, new JvmClasspathRoot(file));
    }

    public static final void addJvmClasspathRoots(CompilerConfiguration compilerConfiguration, List<? extends File> files) {
        Intrinsics.checkNotNullParameter(compilerConfiguration, "<this>");
        Intrinsics.checkNotNullParameter(files, "files");
        Iterator<T> it = files.iterator();
        while (it.hasNext()) {
            addJvmClasspathRoot(compilerConfiguration, (File) it.next());
        }
    }

    public static final void addJvmSdkRoots(CompilerConfiguration compilerConfiguration, List<? extends File> files) {
        Intrinsics.checkNotNullParameter(compilerConfiguration, "<this>");
        Intrinsics.checkNotNullParameter(files, "files");
        CompilerConfigurationKey<List<ContentRoot>> compilerConfigurationKey = CLIConfigurationKeys.CONTENT_ROOTS;
        List<? extends File> list = files;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
        Iterator<T> it = list.iterator();
        while (it.hasNext()) {
            arrayList.add(new JvmClasspathRoot((File) it.next(), true));
        }
        compilerConfiguration.addAll(compilerConfigurationKey, 0, arrayList);
    }

    public static final List<File> getJvmClasspathRoots(CompilerConfiguration compilerConfiguration) {
        Intrinsics.checkNotNullParameter(compilerConfiguration, "<this>");
        List list = compilerConfiguration.getList(CLIConfigurationKeys.CONTENT_ROOTS);
        Intrinsics.checkNotNullExpressionValue(list, "getList(...)");
        ArrayList arrayList = new ArrayList();
        for (Object obj : list) {
            if (obj instanceof JvmClasspathRoot) {
                arrayList.add(obj);
            }
        }
        ArrayList arrayList2 = arrayList;
        ArrayList arrayList3 = new ArrayList(CollectionsKt.collectionSizeOrDefault(arrayList2, 10));
        Iterator it = arrayList2.iterator();
        while (it.hasNext()) {
            arrayList3.add(((JvmContentRoot) it.next()).getFile());
        }
        return arrayList3;
    }

    public static final List<File> getJvmModularRoots(CompilerConfiguration compilerConfiguration) {
        Intrinsics.checkNotNullParameter(compilerConfiguration, "<this>");
        List list = compilerConfiguration.getList(CLIConfigurationKeys.CONTENT_ROOTS);
        Intrinsics.checkNotNullExpressionValue(list, "getList(...)");
        ArrayList arrayList = new ArrayList();
        for (Object obj : list) {
            if (obj instanceof JvmModulePathRoot) {
                arrayList.add(obj);
            }
        }
        ArrayList arrayList2 = arrayList;
        ArrayList arrayList3 = new ArrayList(CollectionsKt.collectionSizeOrDefault(arrayList2, 10));
        Iterator it = arrayList2.iterator();
        while (it.hasNext()) {
            arrayList3.add(((JvmContentRoot) it.next()).getFile());
        }
        return arrayList3;
    }

    public static /* synthetic */ void addJavaSourceRoot$default(CompilerConfiguration compilerConfiguration, File file, String str, int i, Object obj) {
        if ((i & 2) != 0) {
            str = null;
        }
        addJavaSourceRoot(compilerConfiguration, file, str);
    }

    public static final void addJavaSourceRoot(CompilerConfiguration compilerConfiguration, File file, String str) {
        Intrinsics.checkNotNullParameter(compilerConfiguration, "<this>");
        Intrinsics.checkNotNullParameter(file, "file");
        compilerConfiguration.add(CLIConfigurationKeys.CONTENT_ROOTS, new JavaSourceRoot(file, str));
    }

    public static /* synthetic */ void addJavaSourceRoots$default(CompilerConfiguration compilerConfiguration, List list, String str, int i, Object obj) {
        if ((i & 2) != 0) {
            str = null;
        }
        addJavaSourceRoots(compilerConfiguration, list, str);
    }

    public static final void addJavaSourceRoots(CompilerConfiguration compilerConfiguration, List<? extends File> files, String str) {
        Intrinsics.checkNotNullParameter(compilerConfiguration, "<this>");
        Intrinsics.checkNotNullParameter(files, "files");
        Iterator<T> it = files.iterator();
        while (it.hasNext()) {
            addJavaSourceRoot(compilerConfiguration, (File) it.next(), str);
        }
    }

    public static final Set<String> getJavaSourceRoots(CompilerConfiguration compilerConfiguration) {
        String path;
        Intrinsics.checkNotNullParameter(compilerConfiguration, "<this>");
        List<ContentRoot> list = compilerConfiguration.getList(CLIConfigurationKeys.CONTENT_ROOTS);
        Intrinsics.checkNotNullExpressionValue(list, "getList(...)");
        LinkedHashSet linkedHashSet = new LinkedHashSet();
        for (ContentRoot contentRoot : list) {
            if (contentRoot instanceof KotlinSourceRoot) {
                path = ((KotlinSourceRoot) contentRoot).getPath();
            } else {
                path = contentRoot instanceof JavaSourceRoot ? ((JavaSourceRoot) contentRoot).getFile().getPath() : null;
            }
            if (path != null) {
                linkedHashSet.add(path);
            }
        }
        return linkedHashSet;
    }

    public static final void configureJdkClasspathRoots(CompilerConfiguration compilerConfiguration) {
        Intrinsics.checkNotNullParameter(compilerConfiguration, "<this>");
        if (compilerConfiguration.getBoolean(JVMConfigurationKeys.NO_JDK)) {
            return;
        }
        File file = (File) compilerConfiguration.get(JVMConfigurationKeys.JDK_HOME);
        if (file == null) {
            file = new File(System.getProperty("java.home"));
        }
        List<File> jdkClassesRootsFromJdkOrJre = PathUtil.getJdkClassesRootsFromJdkOrJre(file);
        if (CoreJrtFileSystem.INSTANCE.isModularJdk(file)) {
            return;
        }
        if (jdkClassesRootsFromJdkOrJre.isEmpty()) {
            CoreEnvironmentUtilsKt.report$default(compilerConfiguration, CompilerMessageSeverity.ERROR, "No class roots are found in the JDK path: " + file, null, 4, null);
        } else {
            addJvmSdkRoots(compilerConfiguration, jdkClassesRootsFromJdkOrJre);
        }
    }
}
