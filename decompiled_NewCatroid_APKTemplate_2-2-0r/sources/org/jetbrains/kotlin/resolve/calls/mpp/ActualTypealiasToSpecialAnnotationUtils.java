package org.jetbrains.kotlin.resolve.calls.mpp;

import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.SetsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.builtins.StandardNames;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.name.FqName;

/* JADX INFO: compiled from: ActualTypealiasToSpecialAnnotationUtils.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000e\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\tR\u0014\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\n"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/mpp/ActualTypealiasToSpecialAnnotationUtils;", "", "()V", "FORBIDDEN_PACKAGES", "", "Lorg/jetbrains/kotlin/name/FqName;", "isAnnotationProhibitedInActualTypeAlias", "", "classId", "Lorg/jetbrains/kotlin/name/ClassId;", "resolution.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ActualTypealiasToSpecialAnnotationUtils {
    public static final ActualTypealiasToSpecialAnnotationUtils INSTANCE = new ActualTypealiasToSpecialAnnotationUtils();
    private static final Set<FqName> FORBIDDEN_PACKAGES = SetsKt.setOf((Object[]) new FqName[]{StandardNames.ANNOTATION_PACKAGE_FQ_NAME, StandardNames.BUILT_INS_PACKAGE_FQ_NAME, StandardNames.KOTLIN_INTERNAL_FQ_NAME});

    private ActualTypealiasToSpecialAnnotationUtils() {
    }

    public final boolean isAnnotationProhibitedInActualTypeAlias(ClassId classId) {
        Intrinsics.checkNotNullParameter(classId, "classId");
        return FORBIDDEN_PACKAGES.contains(classId.getPackageFqName());
    }
}
