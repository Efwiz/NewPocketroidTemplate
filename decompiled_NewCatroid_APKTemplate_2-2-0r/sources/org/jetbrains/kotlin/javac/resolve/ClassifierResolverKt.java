package org.jetbrains.kotlin.javac.resolve;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.Name;

/* JADX INFO: compiled from: ClassifierResolver.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\u0010\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\u001a\u0018\u0010\u0000\u001a\u00020\u00012\b\b\u0002\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0003¨\u0006\u0005"}, d2 = {"classId", "Lorg/jetbrains/kotlin/name/ClassId;", "packageName", "", "className", "javac-wrapper"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class ClassifierResolverKt {
    public static final ClassId classId(String packageName, String className) {
        Intrinsics.checkNotNullParameter(packageName, "packageName");
        Intrinsics.checkNotNullParameter(className, "className");
        return new ClassId(new FqName(packageName), Name.identifier(className));
    }

    public static /* synthetic */ ClassId classId$default(String str, String str2, int i, Object obj) {
        if ((i & 1) != 0) {
            str = "";
        }
        return classId(str, str2);
    }
}
