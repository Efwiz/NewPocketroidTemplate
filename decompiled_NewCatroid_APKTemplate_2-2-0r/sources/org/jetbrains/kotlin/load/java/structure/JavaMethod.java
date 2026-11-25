package org.jetbrains.kotlin.load.java.structure;

import java.util.List;
import kotlin.Metadata;

/* JADX INFO: compiled from: javaElements.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\bf\u0018\u00002\u00020\u00012\u00020\u0002R\u0014\u0010\u0003\u001a\u0004\u0018\u00010\u0004X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0005\u0010\u0006R\u0014\u0010\u0007\u001a\u00020\b8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\t\u0010\nR\u0012\u0010\u000b\u001a\u00020\fX¦\u0004¢\u0006\u0006\u001a\u0004\b\r\u0010\u000eR\u0018\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00110\u0010X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0012\u0010\u0013ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\u0014À\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/load/java/structure/JavaMethod;", "Lorg/jetbrains/kotlin/load/java/structure/JavaMember;", "Lorg/jetbrains/kotlin/load/java/structure/JavaTypeParameterListOwner;", "annotationParameterDefaultValue", "Lorg/jetbrains/kotlin/load/java/structure/JavaAnnotationArgument;", "getAnnotationParameterDefaultValue", "()Lorg/jetbrains/kotlin/load/java/structure/JavaAnnotationArgument;", "hasAnnotationParameterDefaultValue", "", "getHasAnnotationParameterDefaultValue", "()Z", "returnType", "Lorg/jetbrains/kotlin/load/java/structure/JavaType;", "getReturnType", "()Lorg/jetbrains/kotlin/load/java/structure/JavaType;", "valueParameters", "", "Lorg/jetbrains/kotlin/load/java/structure/JavaValueParameter;", "getValueParameters", "()Ljava/util/List;", "compiler.common.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface JavaMethod extends JavaMember, JavaTypeParameterListOwner {
    JavaAnnotationArgument getAnnotationParameterDefaultValue();

    JavaType getReturnType();

    List<JavaValueParameter> getValueParameters();

    default boolean getHasAnnotationParameterDefaultValue() {
        return getAnnotationParameterDefaultValue() != null;
    }
}
