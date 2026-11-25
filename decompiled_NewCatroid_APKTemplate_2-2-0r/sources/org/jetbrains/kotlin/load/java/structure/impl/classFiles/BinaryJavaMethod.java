package org.jetbrains.kotlin.load.java.structure.impl.classFiles;

import com.reandroid.arsc.chunk.OverlayablePolicy;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.load.java.structure.JavaAnnotationArgument;
import org.jetbrains.kotlin.load.java.structure.JavaClass;
import org.jetbrains.kotlin.load.java.structure.JavaMethod;
import org.jetbrains.kotlin.load.java.structure.JavaType;
import org.jetbrains.kotlin.load.java.structure.JavaTypeParameter;
import org.jetbrains.kotlin.name.Name;

/* JADX INFO: compiled from: Methods.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000@\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\b\u0018\u00002\u00020\u00012\u00020\u0002BA\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\b\u0012\f\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u000b0\b\u0012\u0006\u0010\f\u001a\u00020\r\u0012\u0006\u0010\u000e\u001a\u00020\u000f¢\u0006\u0002\u0010\u0010R(\u0010\u0013\u001a\u0004\u0018\u00010\u00122\b\u0010\u0011\u001a\u0004\u0018\u00010\u0012@PX\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0014\u0010\u0015\"\u0004\b\u0016\u0010\u0017R\u0014\u0010\u000e\u001a\u00020\u000fX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0019¨\u0006\u001a"}, d2 = {"Lorg/jetbrains/kotlin/load/java/structure/impl/classFiles/BinaryJavaMethod;", "Lorg/jetbrains/kotlin/load/java/structure/impl/classFiles/BinaryJavaMethodBase;", "Lorg/jetbrains/kotlin/load/java/structure/JavaMethod;", OverlayablePolicy.NAME_flags, "", "containingClass", "Lorg/jetbrains/kotlin/load/java/structure/JavaClass;", "valueParameters", "", "Lorg/jetbrains/kotlin/load/java/structure/impl/classFiles/BinaryJavaValueParameter;", "typeParameters", "Lorg/jetbrains/kotlin/load/java/structure/JavaTypeParameter;", "name", "Lorg/jetbrains/kotlin/name/Name;", "returnType", "Lorg/jetbrains/kotlin/load/java/structure/JavaType;", "(ILorg/jetbrains/kotlin/load/java/structure/JavaClass;Ljava/util/List;Ljava/util/List;Lorg/jetbrains/kotlin/name/Name;Lorg/jetbrains/kotlin/load/java/structure/JavaType;)V", "value", "Lorg/jetbrains/kotlin/load/java/structure/JavaAnnotationArgument;", "annotationParameterDefaultValue", "getAnnotationParameterDefaultValue", "()Lorg/jetbrains/kotlin/load/java/structure/JavaAnnotationArgument;", "setAnnotationParameterDefaultValue$resolution_common_jvm", "(Lorg/jetbrains/kotlin/load/java/structure/JavaAnnotationArgument;)V", "getReturnType", "()Lorg/jetbrains/kotlin/load/java/structure/JavaType;", "resolution.common.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class BinaryJavaMethod extends BinaryJavaMethodBase implements JavaMethod {
    private JavaAnnotationArgument annotationParameterDefaultValue;
    private final JavaType returnType;

    @Override // org.jetbrains.kotlin.load.java.structure.JavaMethod
    public JavaType getReturnType() {
        return this.returnType;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public BinaryJavaMethod(int i, JavaClass containingClass, List<BinaryJavaValueParameter> valueParameters, List<? extends JavaTypeParameter> typeParameters, Name name2, JavaType returnType) {
        super(i, containingClass, valueParameters, typeParameters, name2);
        Intrinsics.checkNotNullParameter(containingClass, "containingClass");
        Intrinsics.checkNotNullParameter(valueParameters, "valueParameters");
        Intrinsics.checkNotNullParameter(typeParameters, "typeParameters");
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(returnType, "returnType");
        this.returnType = returnType;
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaMethod
    public JavaAnnotationArgument getAnnotationParameterDefaultValue() {
        return this.annotationParameterDefaultValue;
    }

    public void setAnnotationParameterDefaultValue$resolution_common_jvm(JavaAnnotationArgument javaAnnotationArgument) {
        if (this.annotationParameterDefaultValue != null) {
            throw new AssertionError("Annotation method cannot have two default values: " + this + " (old=" + this.annotationParameterDefaultValue + ", new=" + javaAnnotationArgument + ')');
        }
        this.annotationParameterDefaultValue = javaAnnotationArgument;
    }
}
