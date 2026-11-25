package org.jetbrains.kotlin.load.java.structure.impl.classFiles;

import com.reandroid.arsc.chunk.OverlayablePolicy;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.load.java.structure.JavaClass;
import org.jetbrains.kotlin.load.java.structure.JavaConstructor;
import org.jetbrains.kotlin.load.java.structure.JavaTypeParameter;
import org.jetbrains.kotlin.name.SpecialNames;

/* JADX INFO: compiled from: Methods.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u00012\u00020\u0002B1\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\b\u0012\f\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u000b0\b¢\u0006\u0002\u0010\f¨\u0006\r"}, d2 = {"Lorg/jetbrains/kotlin/load/java/structure/impl/classFiles/BinaryJavaConstructor;", "Lorg/jetbrains/kotlin/load/java/structure/impl/classFiles/BinaryJavaMethodBase;", "Lorg/jetbrains/kotlin/load/java/structure/JavaConstructor;", OverlayablePolicy.NAME_flags, "", "containingClass", "Lorg/jetbrains/kotlin/load/java/structure/JavaClass;", "valueParameters", "", "Lorg/jetbrains/kotlin/load/java/structure/impl/classFiles/BinaryJavaValueParameter;", "typeParameters", "Lorg/jetbrains/kotlin/load/java/structure/JavaTypeParameter;", "(ILorg/jetbrains/kotlin/load/java/structure/JavaClass;Ljava/util/List;Ljava/util/List;)V", "resolution.common.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class BinaryJavaConstructor extends BinaryJavaMethodBase implements JavaConstructor {
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public BinaryJavaConstructor(int i, JavaClass containingClass, List<BinaryJavaValueParameter> valueParameters, List<? extends JavaTypeParameter> typeParameters) {
        super(i, containingClass, valueParameters, typeParameters, SpecialNames.NO_NAME_PROVIDED);
        Intrinsics.checkNotNullParameter(containingClass, "containingClass");
        Intrinsics.checkNotNullParameter(valueParameters, "valueParameters");
        Intrinsics.checkNotNullParameter(typeParameters, "typeParameters");
    }
}
