package org.jetbrains.kotlin.konan.target;

import java.util.ArrayList;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.Sequence;
import kotlin.sequences.SequencesKt;
import kotlin.text.StringsKt;

/* JADX INFO: compiled from: utils.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\u000e\n\u0000\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0002\b\u0005\u001a\u0010\u0010\u0005\u001a\u00020\u00012\u0006\u0010\u0006\u001a\u00020\u0001H\u0002\"\u0015\u0010\u0000\u001a\u00020\u0001*\u00020\u00028F¢\u0006\u0006\u001a\u0004\b\u0003\u0010\u0004¨\u0006\u0007"}, d2 = {"presetName", "", "Lorg/jetbrains/kotlin/konan/target/KonanTarget;", "getPresetName", "(Lorg/jetbrains/kotlin/konan/target/KonanTarget;)Ljava/lang/String;", "evaluatePresetName", "targetName", "kotlin-native-utils"}, k = 2, mv = {1, 5, 1}, xi = 48)
public final class UtilsKt {
    public static final String getPresetName(KonanTarget konanTarget) {
        Intrinsics.checkNotNullParameter(konanTarget, "<this>");
        if (Intrinsics.areEqual(konanTarget, KonanTarget.ANDROID_ARM32.INSTANCE)) {
            return "androidNativeArm32";
        }
        if (Intrinsics.areEqual(konanTarget, KonanTarget.ANDROID_ARM64.INSTANCE)) {
            return "androidNativeArm64";
        }
        if (Intrinsics.areEqual(konanTarget, KonanTarget.ANDROID_X86.INSTANCE)) {
            return "androidNativeX86";
        }
        return Intrinsics.areEqual(konanTarget, KonanTarget.ANDROID_X64.INSTANCE) ? "androidNativeX64" : evaluatePresetName(konanTarget.getName());
    }

    private static final String evaluatePresetName(String str) {
        List<String> listSplit$default = StringsKt.split$default((CharSequence) str, new char[]{'_'}, false, 0, 6, (Object) null);
        ArrayList arrayList = new ArrayList();
        for (String str2 : listSplit$default) {
            if (str2.length() <= 0) {
                str2 = null;
            }
            if (str2 != null) {
                arrayList.add(str2);
            }
        }
        ArrayList arrayList2 = arrayList;
        Sequence sequenceDrop = SequencesKt.drop(CollectionsKt.asSequence(arrayList2), 1);
        String str3 = (String) CollectionsKt.firstOrNull((List) arrayList2);
        return SequencesKt.joinToString$default(sequenceDrop, str, str3 != null ? str3 : "", null, 0, null, new Function1<String, CharSequence>() { // from class: org.jetbrains.kotlin.konan.target.UtilsKt.evaluatePresetName.1
            @Override // kotlin.jvm.functions.Function1
            public final CharSequence invoke(String it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return StringsKt.capitalize(it);
            }
        }, 28, null);
    }
}
