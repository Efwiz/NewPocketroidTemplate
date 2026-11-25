package org.jetbrains.kotlin.load.java.structure.impl.classFiles;

import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import org.jetbrains.kotlin.load.java.structure.JavaAnnotationArgument;
import org.jetbrains.kotlin.name.Name;

/* JADX INFO: compiled from: Annotations.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b6\u0018\u00002\u00020\u0001B\u0011\b\u0004\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003¢\u0006\u0002\u0010\u0004R\u0016\u0010\u0002\u001a\u0004\u0018\u00010\u0005X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007\u0082\u0001\u0005\b\t\n\u000b\f¨\u0006\r"}, d2 = {"Lorg/jetbrains/kotlin/load/java/structure/impl/classFiles/PlainJavaAnnotationArgument;", "Lorg/jetbrains/kotlin/load/java/structure/JavaAnnotationArgument;", "name", "", "(Ljava/lang/String;)V", "Lorg/jetbrains/kotlin/name/Name;", "getName", "()Lorg/jetbrains/kotlin/name/Name;", "Lorg/jetbrains/kotlin/load/java/structure/impl/classFiles/PlainJavaAnnotationAsAnnotationArgument;", "Lorg/jetbrains/kotlin/load/java/structure/impl/classFiles/PlainJavaArrayAnnotationArgument;", "Lorg/jetbrains/kotlin/load/java/structure/impl/classFiles/PlainJavaClassObjectAnnotationArgument;", "Lorg/jetbrains/kotlin/load/java/structure/impl/classFiles/PlainJavaEnumValueAnnotationArgument;", "Lorg/jetbrains/kotlin/load/java/structure/impl/classFiles/PlainJavaLiteralAnnotationArgument;", "resolution.common.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class PlainJavaAnnotationArgument implements JavaAnnotationArgument {
    private final Name name;

    public /* synthetic */ PlainJavaAnnotationArgument(String str, DefaultConstructorMarker defaultConstructorMarker) {
        this(str);
    }

    private PlainJavaAnnotationArgument(String str) {
        Name nameIdentifier = null;
        if (str != null) {
            str = Name.isValidIdentifier(str) ? str : null;
            if (str != null) {
                nameIdentifier = Name.identifier(str);
            }
        }
        this.name = nameIdentifier;
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaAnnotationArgument
    public Name getName() {
        return this.name;
    }
}
