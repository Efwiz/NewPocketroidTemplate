package org.jetbrains.kotlin.cli.jvm.modules;

import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.intellij.psi.PsiJavaModule;
import org.jetbrains.kotlin.resolve.jvm.modules.JavaModule;
import org.jetbrains.kotlin.resolve.jvm.modules.JavaModuleFinder;
import org.jetbrains.kotlin.resolve.jvm.modules.JavaModuleInfo;
import org.jetbrains.kotlin.storage.LockBasedStorageManager;

/* JADX INFO: compiled from: JavaModuleGraph.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J$\u0010\t\u001a\u0012\u0012\u0004\u0012\u00020\u00070\nj\b\u0012\u0004\u0012\u00020\u0007`\u000b2\f\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u00070\rJ\u0016\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u00072\u0006\u0010\u0011\u001a\u00020\u0007R\u001c\u0010\u0005\u001a\u0010\u0012\u0004\u0012\u00020\u0007\u0012\u0006\u0012\u0004\u0018\u00010\b0\u0006X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0012"}, d2 = {"Lorg/jetbrains/kotlin/cli/jvm/modules/JavaModuleGraph;", "", "finder", "Lorg/jetbrains/kotlin/resolve/jvm/modules/JavaModuleFinder;", "(Lorg/jetbrains/kotlin/resolve/jvm/modules/JavaModuleFinder;)V", "module", "Lkotlin/Function1;", "", "Lorg/jetbrains/kotlin/resolve/jvm/modules/JavaModule;", "getAllDependencies", "Ljava/util/LinkedHashSet;", "Lkotlin/collections/LinkedHashSet;", "moduleNames", "", "reads", "", "moduleName", "dependencyName", "cli-base"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class JavaModuleGraph {
    private final Function1<String, JavaModule> module;

    public JavaModuleGraph(JavaModuleFinder finder) {
        Intrinsics.checkNotNullParameter(finder, "finder");
        this.module = LockBasedStorageManager.NO_LOCKS.createMemoizedFunctionWithNullableValues(new JavaModuleGraph$module$1(finder));
    }

    public final LinkedHashSet<String> getAllDependencies(List<String> moduleNames) {
        Intrinsics.checkNotNullParameter(moduleNames, "moduleNames");
        LinkedHashSet<String> linkedHashSet = new LinkedHashSet<>(moduleNames);
        linkedHashSet.add(PsiJavaModule.JAVA_BASE);
        Iterator<String> it = moduleNames.iterator();
        while (it.hasNext()) {
            JavaModule javaModuleInvoke = this.module.invoke(it.next());
            if (javaModuleInvoke != null && !(javaModuleInvoke instanceof JavaModule.Automatic)) {
                if (javaModuleInvoke instanceof JavaModule.Explicit) {
                    JavaModule.Explicit explicit = (JavaModule.Explicit) javaModuleInvoke;
                    for (JavaModuleInfo.Requires requires : explicit.getModuleInfo().getRequires()) {
                        String moduleName = requires.getModuleName();
                        boolean isTransitive = requires.getIsTransitive();
                        if (linkedHashSet.add(moduleName) && !getAllDependencies$dfs(this, linkedHashSet, moduleName) && !isTransitive && explicit.getIsJdkModuleFromCtSym()) {
                            linkedHashSet.remove(moduleName);
                        }
                    }
                } else {
                    throw new IllegalStateException(("Unknown module: " + javaModuleInvoke + " (" + javaModuleInvoke.getClass() + ')').toString());
                }
            }
        }
        return linkedHashSet;
    }

    private static final boolean getAllDependencies$dfs(JavaModuleGraph javaModuleGraph, LinkedHashSet<String> linkedHashSet, String str) {
        JavaModuleInfo moduleInfo;
        JavaModule javaModuleInvoke = javaModuleGraph.module.invoke(str);
        JavaModule.Explicit explicit = javaModuleInvoke instanceof JavaModule.Explicit ? (JavaModule.Explicit) javaModuleInvoke : null;
        if (explicit == null || (moduleInfo = explicit.getModuleInfo()) == null) {
            return false;
        }
        for (JavaModuleInfo.Requires requires : moduleInfo.getRequires()) {
            String moduleName = requires.getModuleName();
            if (requires.getIsTransitive() && linkedHashSet.add(moduleName)) {
                getAllDependencies$dfs(javaModuleGraph, linkedHashSet, moduleName);
            }
        }
        return true;
    }

    public final boolean reads(String moduleName, String dependencyName) {
        Intrinsics.checkNotNullParameter(moduleName, "moduleName");
        Intrinsics.checkNotNullParameter(dependencyName, "dependencyName");
        if (Intrinsics.areEqual(moduleName, dependencyName) || Intrinsics.areEqual(dependencyName, PsiJavaModule.JAVA_BASE)) {
            return true;
        }
        LinkedHashSet linkedHashSet = new LinkedHashSet();
        JavaModule javaModuleInvoke = this.module.invoke(moduleName);
        if (javaModuleInvoke == null) {
            return false;
        }
        if (javaModuleInvoke instanceof JavaModule.Automatic) {
            return true;
        }
        if (javaModuleInvoke instanceof JavaModule.Explicit) {
            Iterator<JavaModuleInfo.Requires> it = ((JavaModule.Explicit) javaModuleInvoke).getModuleInfo().getRequires().iterator();
            while (it.hasNext()) {
                if (reads$dfs$0(linkedHashSet, this, dependencyName, it.next().getModuleName())) {
                    return true;
                }
            }
        }
        return reads$dfs$0(linkedHashSet, this, dependencyName, moduleName);
    }

    private static final boolean reads$dfs$0(LinkedHashSet<String> linkedHashSet, JavaModuleGraph javaModuleGraph, String str, String str2) {
        JavaModule javaModuleInvoke;
        if (!linkedHashSet.add(str2) || (javaModuleInvoke = javaModuleGraph.module.invoke(str2)) == null) {
            return false;
        }
        if (javaModuleInvoke instanceof JavaModule.Automatic) {
            return true;
        }
        if (javaModuleInvoke instanceof JavaModule.Explicit) {
            for (JavaModuleInfo.Requires requires : ((JavaModule.Explicit) javaModuleInvoke).getModuleInfo().getRequires()) {
                String moduleName = requires.getModuleName();
                boolean isTransitive = requires.getIsTransitive();
                if (Intrinsics.areEqual(moduleName, str)) {
                    return true;
                }
                if (isTransitive && reads$dfs$0(linkedHashSet, javaModuleGraph, str, str)) {
                    return true;
                }
            }
            return false;
        }
        throw new IllegalStateException(("Unsupported module type: " + javaModuleInvoke).toString());
    }
}
