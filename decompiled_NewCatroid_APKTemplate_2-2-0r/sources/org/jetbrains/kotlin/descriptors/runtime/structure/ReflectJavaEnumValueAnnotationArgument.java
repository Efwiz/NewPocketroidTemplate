package org.jetbrains.kotlin.descriptors.runtime.structure;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.load.java.structure.JavaEnumValueAnnotationArgument;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.name.Name;

/* JADX INFO: compiled from: ReflectJavaAnnotationArguments.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0010\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u00012\u00020\u0002B\u001b\u0012\b\u0010\u0003\u001a\u0004\u0018\u00010\u0004\u0012\n\u0010\u0005\u001a\u0006\u0012\u0002\b\u00030\u0006¢\u0006\u0002\u0010\u0007R\u0016\u0010\b\u001a\u0004\u0018\u00010\u00048VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\t\u0010\nR\u0016\u0010\u000b\u001a\u0004\u0018\u00010\f8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\r\u0010\u000eR\u0012\u0010\u0005\u001a\u0006\u0012\u0002\b\u00030\u0006X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u000f"}, d2 = {"Lorg/jetbrains/kotlin/descriptors/runtime/structure/ReflectJavaEnumValueAnnotationArgument;", "Lorg/jetbrains/kotlin/descriptors/runtime/structure/ReflectJavaAnnotationArgument;", "Lorg/jetbrains/kotlin/load/java/structure/JavaEnumValueAnnotationArgument;", "name", "Lorg/jetbrains/kotlin/name/Name;", "value", "", "(Lorg/jetbrains/kotlin/name/Name;Ljava/lang/Enum;)V", "entryName", "getEntryName", "()Lorg/jetbrains/kotlin/name/Name;", "enumClassId", "Lorg/jetbrains/kotlin/name/ClassId;", "getEnumClassId", "()Lorg/jetbrains/kotlin/name/ClassId;", "descriptors.runtime"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ReflectJavaEnumValueAnnotationArgument extends ReflectJavaAnnotationArgument implements JavaEnumValueAnnotationArgument {
    private final Enum<?> value;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ReflectJavaEnumValueAnnotationArgument(Name name2, Enum<?> value) {
        super(name2, null);
        Intrinsics.checkNotNullParameter(value, "value");
        this.value = value;
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaEnumValueAnnotationArgument
    public ClassId getEnumClassId() {
        Class<?> enclosingClass = this.value.getClass();
        if (!enclosingClass.isEnum()) {
            enclosingClass = enclosingClass.getEnclosingClass();
        }
        Intrinsics.checkNotNull(enclosingClass);
        return ReflectClassUtilKt.getClassId(enclosingClass);
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaEnumValueAnnotationArgument
    public Name getEntryName() {
        return Name.identifier(this.value.name());
    }
}
