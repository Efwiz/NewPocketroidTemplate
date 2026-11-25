package org.jetbrains.kotlin.load.java.structure.impl.classFiles;

import com.reandroid.archive.PathTree;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.load.java.structure.JavaAnnotationArgument;
import org.jetbrains.kotlin.load.java.structure.JavaArrayAnnotationArgument;

/* JADX INFO: compiled from: Annotations.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u00012\u00020\u0002B\u001d\u0012\b\u0010\u0003\u001a\u0004\u0018\u00010\u0004\u0012\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006¢\u0006\u0002\u0010\bJ\u000e\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006H\u0016R\u0014\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\n"}, d2 = {"Lorg/jetbrains/kotlin/load/java/structure/impl/classFiles/PlainJavaArrayAnnotationArgument;", "Lorg/jetbrains/kotlin/load/java/structure/impl/classFiles/PlainJavaAnnotationArgument;", "Lorg/jetbrains/kotlin/load/java/structure/JavaArrayAnnotationArgument;", "name", "", PathTree.NAME_elements, "", "Lorg/jetbrains/kotlin/load/java/structure/JavaAnnotationArgument;", "(Ljava/lang/String;Ljava/util/List;)V", "getElements", "resolution.common.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class PlainJavaArrayAnnotationArgument extends PlainJavaAnnotationArgument implements JavaArrayAnnotationArgument {
    private final List<JavaAnnotationArgument> elements;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    /* JADX WARN: Multi-variable type inference failed */
    public PlainJavaArrayAnnotationArgument(String str, List<? extends JavaAnnotationArgument> elements) {
        super(str, null);
        Intrinsics.checkNotNullParameter(elements, "elements");
        this.elements = elements;
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaArrayAnnotationArgument
    public List<JavaAnnotationArgument> getElements() {
        return this.elements;
    }
}
