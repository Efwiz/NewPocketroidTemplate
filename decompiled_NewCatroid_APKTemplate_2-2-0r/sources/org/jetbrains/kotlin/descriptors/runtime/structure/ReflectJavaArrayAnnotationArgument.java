package org.jetbrains.kotlin.descriptors.runtime.structure;

import com.reandroid.arsc.value.CompoundEntry;
import java.util.ArrayList;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.load.java.structure.JavaArrayAnnotationArgument;
import org.jetbrains.kotlin.name.Name;

/* JADX INFO: compiled from: ReflectJavaAnnotationArguments.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0011\n\u0002\b\u0003\n\u0002\u0010 \n\u0000\u0018\u00002\u00020\u00012\u00020\u0002B\u001b\u0012\b\u0010\u0003\u001a\u0004\u0018\u00010\u0004\u0012\n\u0010\u0005\u001a\u0006\u0012\u0002\b\u00030\u0006¢\u0006\u0002\u0010\u0007J\u000e\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00010\nH\u0016R\u0014\u0010\u0005\u001a\u0006\u0012\u0002\b\u00030\u0006X\u0082\u0004¢\u0006\u0004\n\u0002\u0010\b¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/descriptors/runtime/structure/ReflectJavaArrayAnnotationArgument;", "Lorg/jetbrains/kotlin/descriptors/runtime/structure/ReflectJavaAnnotationArgument;", "Lorg/jetbrains/kotlin/load/java/structure/JavaArrayAnnotationArgument;", "name", "Lorg/jetbrains/kotlin/name/Name;", CompoundEntry.NAME_values, "", "(Lorg/jetbrains/kotlin/name/Name;[Ljava/lang/Object;)V", "[Ljava/lang/Object;", "getElements", "", "descriptors.runtime"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ReflectJavaArrayAnnotationArgument extends ReflectJavaAnnotationArgument implements JavaArrayAnnotationArgument {
    private final Object[] values;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ReflectJavaArrayAnnotationArgument(Name name2, Object[] values) {
        super(name2, null);
        Intrinsics.checkNotNullParameter(values, "values");
        this.values = values;
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaArrayAnnotationArgument
    public List<ReflectJavaAnnotationArgument> getElements() {
        Object[] objArr = this.values;
        ArrayList arrayList = new ArrayList(objArr.length);
        for (Object obj : objArr) {
            ReflectJavaAnnotationArgument.Companion companion = ReflectJavaAnnotationArgument.INSTANCE;
            Intrinsics.checkNotNull(obj);
            arrayList.add(companion.create(obj, null));
        }
        return arrayList;
    }
}
