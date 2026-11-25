package org.jetbrains.kotlin.codegen.coroutines;

import com.reandroid.dex.sections.Marker;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.builtins.StandardNames;
import org.jetbrains.kotlin.codegen.CodegenUtilKt;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.Name;

/* JADX INFO: compiled from: ChangeBoxingMethodTransformer.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000\b\n\u0000\n\u0002\u0010\u000e\n\u0000\"\u000e\u0010\u0000\u001a\u00020\u0001X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0002"}, d2 = {"BOXING_CLASS_INTERNAL_NAME", "", Marker.BACKEND}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class ChangeBoxingMethodTransformerKt {
    private static final String BOXING_CLASS_INTERNAL_NAME;

    static {
        FqName fqNameChild = StandardNames.COROUTINES_JVM_INTERNAL_PACKAGE_FQ_NAME.child(Name.identifier("Boxing"));
        Intrinsics.checkNotNullExpressionValue(fqNameChild, "child(...)");
        BOXING_CLASS_INTERNAL_NAME = CodegenUtilKt.topLevelClassInternalName(fqNameChild);
    }
}
