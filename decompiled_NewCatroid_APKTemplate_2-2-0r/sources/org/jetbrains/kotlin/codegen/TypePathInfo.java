package org.jetbrains.kotlin.codegen;

import androidx.exifinterface.media.ExifInterface;
import com.reandroid.dex.sections.Marker;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.org.objectweb.asm.TypePath;

/* JADX INFO: compiled from: typeAnnotationCollector.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\b\u0006\u0018\u0000*\u0004\b\u0000\u0010\u00012\u00020\u0002B\u001d\u0012\b\u0010\u0003\u001a\u0004\u0018\u00010\u0004\u0012\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00028\u00000\u0006¢\u0006\u0002\u0010\u0007R\u0017\u0010\u0005\u001a\b\u0012\u0004\u0012\u00028\u00000\u0006¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0013\u0010\u0003\u001a\u0004\u0018\u00010\u0004¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000b¨\u0006\f"}, d2 = {"Lorg/jetbrains/kotlin/codegen/TypePathInfo;", ExifInterface.GPS_DIRECTION_TRUE, "", "path", "Lorg/jetbrains/org/objectweb/asm/TypePath;", "annotations", "", "(Lorg/jetbrains/org/objectweb/asm/TypePath;Ljava/util/List;)V", "getAnnotations", "()Ljava/util/List;", "getPath", "()Lorg/jetbrains/org/objectweb/asm/TypePath;", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class TypePathInfo<T> {
    private final List<T> annotations;
    private final TypePath path;

    /* JADX WARN: Multi-variable type inference failed */
    public TypePathInfo(TypePath typePath, List<? extends T> annotations) {
        Intrinsics.checkNotNullParameter(annotations, "annotations");
        this.path = typePath;
        this.annotations = annotations;
    }

    public final TypePath getPath() {
        return this.path;
    }

    public final List<T> getAnnotations() {
        return this.annotations;
    }
}
