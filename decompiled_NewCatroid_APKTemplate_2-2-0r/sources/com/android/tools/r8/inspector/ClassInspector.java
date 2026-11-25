package com.android.tools.r8.inspector;

import com.android.tools.r8.references.ClassReference;
import java.util.function.Consumer;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public interface ClassInspector {
    void forEachField(Consumer<FieldInspector> consumer);

    void forEachMethod(Consumer<MethodInspector> consumer);

    ClassReference getClassReference();

    String getSourceFile();
}
