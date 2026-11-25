package org.jetbrains.kotlin.cli.jvm.compiler;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.Sequence;
import kotlin.text.StringsKt;
import org.jetbrains.kotlin.cli.jvm.index.JavaRoot;
import org.jetbrains.kotlin.cli.jvm.modules.CliJavaModuleFinder;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.kotlin.resolve.jvm.modules.JavaModule;
import org.jetbrains.kotlin.resolve.jvm.modules.JavaModuleInfo;

/* JADX INFO: compiled from: cliJavaModuleUtils.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000\u001a\n\u0000\n\u0002\u0010 \n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\u001a\u0010\u0010\u0000\u001a\b\u0012\u0004\u0012\u00020\u00020\u0001*\u00020\u0003\u001a\u0010\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00050\u0001*\u00020\u0006¨\u0006\u0007"}, d2 = {"computeDefaultRootModules", "", "", "Lorg/jetbrains/kotlin/cli/jvm/modules/CliJavaModuleFinder;", "getJavaModuleRoots", "Lorg/jetbrains/kotlin/cli/jvm/index/JavaRoot;", "Lorg/jetbrains/kotlin/resolve/jvm/modules/JavaModule;", "cli-base"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class CliJavaModuleUtilsKt {
    public static final List<JavaRoot> getJavaModuleRoots(JavaModule javaModule) {
        JavaRoot.RootType rootType;
        Intrinsics.checkNotNullParameter(javaModule, "<this>");
        List<JavaModule.Root> moduleRoots = javaModule.getModuleRoots();
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(moduleRoots, 10));
        for (JavaModule.Root root : moduleRoots) {
            VirtualFile file = root.getFile();
            boolean isBinary = root.getIsBinary();
            if (root.getIsBinarySignature()) {
                rootType = JavaRoot.RootType.BINARY_SIG;
            } else if (isBinary) {
                rootType = JavaRoot.RootType.BINARY;
            } else {
                rootType = JavaRoot.RootType.SOURCE;
            }
            arrayList.add(new JavaRoot(file, rootType, null, 4, null));
        }
        return arrayList;
    }

    public static final List<String> computeDefaultRootModules(CliJavaModuleFinder cliJavaModuleFinder) {
        Intrinsics.checkNotNullParameter(cliJavaModuleFinder, "<this>");
        ArrayList arrayList = new ArrayList();
        Sequence<JavaModule.Explicit> systemModules = cliJavaModuleFinder.getSystemModules();
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        for (JavaModule.Explicit explicit : systemModules) {
            linkedHashMap.put(explicit.getName(), explicit);
        }
        boolean zContainsKey = linkedHashMap.containsKey("java.se");
        if (zContainsKey) {
            arrayList.add("java.se");
        }
        if (!zContainsKey) {
            for (Map.Entry entry : linkedHashMap.entrySet()) {
                String str = (String) entry.getKey();
                JavaModule.Explicit explicit2 = (JavaModule.Explicit) entry.getValue();
                if (StringsKt.startsWith$default(str, "java.", false, 2, (Object) null) && computeDefaultRootModules$exportsAtLeastOnePackageUnqualified(explicit2)) {
                    arrayList.add(str);
                }
            }
        }
        for (Map.Entry entry2 : linkedHashMap.entrySet()) {
            String str2 = (String) entry2.getKey();
            JavaModule.Explicit explicit3 = (JavaModule.Explicit) entry2.getValue();
            if (!StringsKt.startsWith$default(str2, "java.", false, 2, (Object) null) && computeDefaultRootModules$exportsAtLeastOnePackageUnqualified(explicit3)) {
                arrayList.add(str2);
            }
        }
        return arrayList;
    }

    private static final boolean computeDefaultRootModules$exportsAtLeastOnePackageUnqualified(JavaModule.Explicit explicit) {
        List<JavaModuleInfo.Exports> exports = explicit.getModuleInfo().getExports();
        if ((exports instanceof Collection) && exports.isEmpty()) {
            return false;
        }
        Iterator<T> it = exports.iterator();
        while (it.hasNext()) {
            if (((JavaModuleInfo.Exports) it.next()).getToModules().isEmpty()) {
                return true;
            }
        }
        return false;
    }
}
