package org.jetbrains.kotlin.codegen;

import com.reandroid.dex.sections.Marker;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.org.objectweb.asm.Type;

/* JADX INFO: compiled from: JvmBackendClassResolver.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\"\u0015\u0010\u0000\u001a\u00020\u0001*\u00020\u00028F¢\u0006\u0006\u001a\u0004\b\u0003\u0010\u0004¨\u0006\u0005"}, d2 = {"classId", "Lorg/jetbrains/kotlin/name/ClassId;", "Lorg/jetbrains/org/objectweb/asm/Type;", "getClassId", "(Lorg/jetbrains/org/objectweb/asm/Type;)Lorg/jetbrains/kotlin/name/ClassId;", Marker.BACKEND}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class JvmBackendClassResolverKt {
    public static final ClassId getClassId(Type type) {
        FqName fqName;
        Intrinsics.checkNotNullParameter(type, "<this>");
        String className = type.getClassName();
        Intrinsics.checkNotNull(className);
        int iLastIndexOf$default = StringsKt.lastIndexOf$default((CharSequence) className, '.', 0, false, 6, (Object) null);
        if (iLastIndexOf$default >= 0) {
            String strSubstring = className.substring(0, iLastIndexOf$default);
            Intrinsics.checkNotNullExpressionValue(strSubstring, "substring(...)");
            fqName = new FqName(strSubstring);
        } else {
            fqName = FqName.ROOT;
        }
        if (iLastIndexOf$default >= 0) {
            className = className.substring(iLastIndexOf$default + 1);
            Intrinsics.checkNotNullExpressionValue(className, "substring(...)");
        }
        String str = className;
        Intrinsics.checkNotNull(str);
        return new ClassId(fqName, new FqName(StringsKt.replace$default(str, '$', '.', false, 4, (Object) null)), false);
    }
}
