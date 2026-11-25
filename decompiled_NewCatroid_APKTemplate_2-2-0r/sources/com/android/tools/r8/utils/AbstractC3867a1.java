package com.android.tools.r8.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.function.Consumer;
import org.jetbrains.kotlin.cli.common.arguments.PreprocessCommandLineArgumentsKt;

/* JADX INFO: renamed from: com.android.tools.r8.utils.a1, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes8.dex */
public abstract class AbstractC3867a1 {
    public static String[] a(String[] strArr, Consumer consumer) {
        ArrayList arrayList = new ArrayList(strArr.length);
        for (String str : strArr) {
            if (str.startsWith(PreprocessCommandLineArgumentsKt.ARGFILE_ARGUMENT)) {
                Path path = Paths.get(str.substring(1), new String[0]);
                try {
                    arrayList.addAll(Files.readAllLines(path));
                } catch (IOException e) {
                    consumer.accept(new ExceptionDiagnostic(e, new Z0(path)));
                }
            } else {
                arrayList.add(str);
            }
        }
        return (String[]) arrayList.toArray(Y2.b);
    }
}
