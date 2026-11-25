package org.jetbrains.kotlin.descriptors.runtime.components;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.jetbrains.kotlin.name.ClassId;

/* JADX INFO: compiled from: ReflectKotlinClassFinder.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\f\n\u0000\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0000\u001a\f\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u0002¨\u0006\u0003"}, d2 = {"toRuntimeFqName", "", "Lorg/jetbrains/kotlin/name/ClassId;", "descriptors.runtime"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class ReflectKotlinClassFinderKt {
    /* JADX INFO: Access modifiers changed from: private */
    public static final String toRuntimeFqName(ClassId classId) {
        String strAsString = classId.getRelativeClassName().asString();
        Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
        String strReplace$default = StringsKt.replace$default(strAsString, '.', '$', false, 4, (Object) null);
        return classId.getPackageFqName().isRoot() ? strReplace$default : classId.getPackageFqName() + '.' + strReplace$default;
    }
}
