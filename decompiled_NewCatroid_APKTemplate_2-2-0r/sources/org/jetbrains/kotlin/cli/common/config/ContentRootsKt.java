package org.jetbrains.kotlin.cli.common.config;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.cli.common.CLIConfigurationKeys;
import org.jetbrains.kotlin.config.CompilerConfiguration;

/* JADX INFO: compiled from: ContentRoots.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000&\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0004\u001a*\u0010\u0006\u001a\u00020\u0007*\u00020\u00032\u0006\u0010\b\u001a\u00020\t2\b\b\u0002\u0010\n\u001a\u00020\u000b2\n\b\u0002\u0010\f\u001a\u0004\u0018\u00010\tH\u0007\u001a\u0018\u0010\r\u001a\u00020\u0007*\u00020\u00032\f\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\t0\u0001\"\u001b\u0010\u0000\u001a\b\u0012\u0004\u0012\u00020\u00020\u0001*\u00020\u00038F¢\u0006\u0006\u001a\u0004\b\u0004\u0010\u0005¨\u0006\u000f"}, d2 = {"kotlinSourceRoots", "", "Lorg/jetbrains/kotlin/cli/common/config/KotlinSourceRoot;", "Lorg/jetbrains/kotlin/config/CompilerConfiguration;", "getKotlinSourceRoots", "(Lorg/jetbrains/kotlin/config/CompilerConfiguration;)Ljava/util/List;", "addKotlinSourceRoot", "", "path", "", "isCommon", "", "hmppModuleName", "addKotlinSourceRoots", "sources", "cli-base"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class ContentRootsKt {
    public static final void addKotlinSourceRoot(CompilerConfiguration compilerConfiguration, String path) {
        Intrinsics.checkNotNullParameter(compilerConfiguration, "<this>");
        Intrinsics.checkNotNullParameter(path, "path");
        addKotlinSourceRoot$default(compilerConfiguration, path, false, null, 6, null);
    }

    public static final void addKotlinSourceRoot(CompilerConfiguration compilerConfiguration, String path, boolean z) {
        Intrinsics.checkNotNullParameter(compilerConfiguration, "<this>");
        Intrinsics.checkNotNullParameter(path, "path");
        addKotlinSourceRoot$default(compilerConfiguration, path, z, null, 4, null);
    }

    public static /* synthetic */ void addKotlinSourceRoot$default(CompilerConfiguration compilerConfiguration, String str, boolean z, String str2, int i, Object obj) {
        if ((i & 2) != 0) {
            z = false;
        }
        if ((i & 4) != 0) {
            str2 = null;
        }
        addKotlinSourceRoot(compilerConfiguration, str, z, str2);
    }

    public static final void addKotlinSourceRoot(CompilerConfiguration compilerConfiguration, String path, boolean z, String str) {
        Intrinsics.checkNotNullParameter(compilerConfiguration, "<this>");
        Intrinsics.checkNotNullParameter(path, "path");
        compilerConfiguration.add(CLIConfigurationKeys.CONTENT_ROOTS, new KotlinSourceRoot(path, z, str));
    }

    public static final void addKotlinSourceRoots(CompilerConfiguration compilerConfiguration, List<String> sources) {
        Intrinsics.checkNotNullParameter(compilerConfiguration, "<this>");
        Intrinsics.checkNotNullParameter(sources, "sources");
        Iterator<T> it = sources.iterator();
        while (it.hasNext()) {
            addKotlinSourceRoot$default(compilerConfiguration, (String) it.next(), false, null, 6, null);
        }
    }

    public static final List<KotlinSourceRoot> getKotlinSourceRoots(CompilerConfiguration compilerConfiguration) {
        ArrayList arrayList;
        Intrinsics.checkNotNullParameter(compilerConfiguration, "<this>");
        List list = (List) compilerConfiguration.get(CLIConfigurationKeys.CONTENT_ROOTS);
        if (list != null) {
            ArrayList arrayList2 = new ArrayList();
            for (Object obj : list) {
                if (obj instanceof KotlinSourceRoot) {
                    arrayList2.add(obj);
                }
            }
            arrayList = arrayList2;
        } else {
            arrayList = null;
        }
        return arrayList == null ? CollectionsKt.emptyList() : arrayList;
    }
}
