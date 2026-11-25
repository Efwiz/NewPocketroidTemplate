package org.jetbrains.kotlin.serialization.builtins;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import org.apache.commons.lang3.SystemProperties;

/* JADX INFO: compiled from: run.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u0014\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0011\n\u0002\u0010\u000e\n\u0002\b\u0002\u001a\u0019\u0010\u0000\u001a\u00020\u00012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003¢\u0006\u0002\u0010\u0005¨\u0006\u0006"}, d2 = {"main", "", "args", "", "", "([Ljava/lang/String;)V", "cli"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class RunKt {
    public static final void main(String[] args) {
        Intrinsics.checkNotNullParameter(args, "args");
        System.setProperty(SystemProperties.JAVA_AWT_HEADLESS, "true");
        if (args.length < 2) {
            System.out.println((Object) "Kotlin built-ins serializer\n\nUsage: ... <destination dir> (<source dir>)+\n\nAnalyzes Kotlin sources found in the given source directories and serializes\nfound top-level declarations to <destination dir> (*.kotlin_builtins files)");
            return;
        }
        File file = new File(args[0]);
        List listDrop = ArraysKt.drop(args, 1);
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(listDrop, 10));
        Iterator it = listDrop.iterator();
        while (it.hasNext()) {
            arrayList.add(new File((String) it.next()));
        }
        ArrayList arrayList2 = arrayList;
        arrayList2.isEmpty();
        ArrayList arrayList3 = new ArrayList();
        for (Object obj : arrayList2) {
            if (!((File) obj).exists()) {
                arrayList3.add(obj);
            }
        }
        arrayList3.isEmpty();
        BuiltInsSerializer.INSTANCE.analyzeAndSerialize(file, arrayList2, CollectionsKt.emptyList(), false, new Function2<Integer, Integer, Unit>() { // from class: org.jetbrains.kotlin.serialization.builtins.RunKt.main.3
            @Override // kotlin.jvm.functions.Function2
            public /* bridge */ /* synthetic */ Unit invoke(Integer num, Integer num2) {
                invoke(num.intValue(), num2.intValue());
                return Unit.INSTANCE;
            }

            public final void invoke(int i, int i2) {
                if (Intrinsics.areEqual(System.getProperty("kotlin.builtins.serializer.log"), "true")) {
                    System.out.println((Object) ("Total bytes written: " + i + " to " + i2 + " files"));
                }
            }
        });
    }
}
