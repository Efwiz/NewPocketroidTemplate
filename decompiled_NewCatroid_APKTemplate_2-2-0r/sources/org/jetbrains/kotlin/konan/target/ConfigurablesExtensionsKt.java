package org.jetbrains.kotlin.konan.target;

import java.util.ArrayList;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;

/* JADX INFO: compiled from: ConfigurablesExtensions.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\u0012\n\u0000\n\u0002\u0010 \n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a\u0010\u0010\u0000\u001a\b\u0012\u0004\u0012\u00020\u00020\u0001*\u00020\u0003\u001a\u0010\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00020\u0001*\u00020\u0003¨\u0006\u0005"}, d2 = {"constructClangArgs", "", "", "Lorg/jetbrains/kotlin/konan/target/ZephyrConfigurables;", "constructClangCC1Args", "kotlin-native-utils"}, k = 2, mv = {1, 5, 1}, xi = 48)
public final class ConfigurablesExtensionsKt {
    public static final List<String> constructClangArgs(ZephyrConfigurables zephyrConfigurables) {
        Intrinsics.checkNotNullParameter(zephyrConfigurables, "<this>");
        ArrayList arrayList = new ArrayList();
        String targetCpu = zephyrConfigurables.getTargetCpu();
        if (targetCpu != null) {
            arrayList.add("-mcpu=" + targetCpu);
        }
        String targetAbi = zephyrConfigurables.getTargetAbi();
        if (targetAbi != null) {
            arrayList.add("-mabi=" + targetAbi);
        }
        arrayList.addAll(zephyrConfigurables.getBoardSpecificClangFlags());
        return arrayList;
    }

    public static final List<String> constructClangCC1Args(ZephyrConfigurables zephyrConfigurables) {
        Intrinsics.checkNotNullParameter(zephyrConfigurables, "<this>");
        ArrayList arrayList = new ArrayList();
        arrayList.addAll(StringsKt.split$default((CharSequence) "-cc1 -emit-obj -disable-llvm-optzns -x ir -fdata-sections -ffunction-sections", new String[]{" "}, false, 0, 6, (Object) null));
        String targetCpu = zephyrConfigurables.getTargetCpu();
        if (targetCpu != null) {
            arrayList.addAll(CollectionsKt.listOf((Object[]) new String[]{"-target-abi", targetCpu}));
        }
        String targetAbi = zephyrConfigurables.getTargetAbi();
        if (targetAbi != null) {
            arrayList.addAll(CollectionsKt.listOf((Object[]) new String[]{"-target-cpu", targetAbi}));
        }
        arrayList.addAll(zephyrConfigurables.getBoardSpecificClangFlags());
        return arrayList;
    }
}
