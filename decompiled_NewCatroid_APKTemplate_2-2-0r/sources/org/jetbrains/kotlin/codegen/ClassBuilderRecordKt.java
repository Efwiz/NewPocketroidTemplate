package org.jetbrains.kotlin.codegen;

import com.reandroid.dex.sections.Marker;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: ClassBuilderRecord.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000\u0014\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\u001a$\u0010\u0000\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00042\b\u0010\u0006\u001a\u0004\u0018\u00010\u0004¨\u0006\u0007"}, d2 = {"addRecordComponent", "", "Lorg/jetbrains/kotlin/codegen/ClassBuilder;", "name", "", "desc", "signature", Marker.BACKEND}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class ClassBuilderRecordKt {
    public static final void addRecordComponent(ClassBuilder classBuilder, String name2, String desc, String str) {
        Intrinsics.checkNotNullParameter(classBuilder, "<this>");
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(desc, "desc");
        classBuilder.newRecordComponent(name2, desc, str);
    }
}
