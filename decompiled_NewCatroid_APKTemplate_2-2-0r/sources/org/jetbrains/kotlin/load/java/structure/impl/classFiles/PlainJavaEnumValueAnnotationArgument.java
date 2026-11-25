package org.jetbrains.kotlin.load.java.structure.impl.classFiles;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.load.java.structure.JavaEnumValueAnnotationArgument;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.name.Name;

/* JADX INFO: compiled from: Annotations.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\u0018\u00002\u00020\u00012\u00020\u0002B\u001f\u0012\b\u0010\u0003\u001a\u0004\u0018\u00010\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\u0004¢\u0006\u0002\u0010\bR\u0014\u0010\u0007\u001a\u00020\tX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0014\u0010\u0005\u001a\u00020\u0006X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\r¨\u0006\u000e"}, d2 = {"Lorg/jetbrains/kotlin/load/java/structure/impl/classFiles/PlainJavaEnumValueAnnotationArgument;", "Lorg/jetbrains/kotlin/load/java/structure/impl/classFiles/PlainJavaAnnotationArgument;", "Lorg/jetbrains/kotlin/load/java/structure/JavaEnumValueAnnotationArgument;", "name", "", "enumClassId", "Lorg/jetbrains/kotlin/name/ClassId;", "entryName", "(Ljava/lang/String;Lorg/jetbrains/kotlin/name/ClassId;Ljava/lang/String;)V", "Lorg/jetbrains/kotlin/name/Name;", "getEntryName", "()Lorg/jetbrains/kotlin/name/Name;", "getEnumClassId", "()Lorg/jetbrains/kotlin/name/ClassId;", "resolution.common.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class PlainJavaEnumValueAnnotationArgument extends PlainJavaAnnotationArgument implements JavaEnumValueAnnotationArgument {
    private final Name entryName;
    private final ClassId enumClassId;

    @Override // org.jetbrains.kotlin.load.java.structure.JavaEnumValueAnnotationArgument
    public ClassId getEnumClassId() {
        return this.enumClassId;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public PlainJavaEnumValueAnnotationArgument(String str, ClassId enumClassId, String entryName) {
        super(str, null);
        Intrinsics.checkNotNullParameter(enumClassId, "enumClassId");
        Intrinsics.checkNotNullParameter(entryName, "entryName");
        this.enumClassId = enumClassId;
        Name nameIdentifier = Name.identifier(entryName);
        Intrinsics.checkNotNullExpressionValue(nameIdentifier, "identifier(...)");
        this.entryName = nameIdentifier;
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaEnumValueAnnotationArgument
    public Name getEntryName() {
        return this.entryName;
    }
}
