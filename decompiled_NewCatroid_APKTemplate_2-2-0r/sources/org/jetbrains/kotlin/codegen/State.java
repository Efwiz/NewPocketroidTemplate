package org.jetbrains.kotlin.codegen;

import androidx.exifinterface.media.ExifInterface;
import com.reandroid.dex.sections.Marker;
import java.util.ArrayList;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.org.objectweb.asm.TypePath;

/* JADX INFO: compiled from: typeAnnotationCollector.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u00008\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010!\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\b\u0002\b\u0002\u0018\u0000*\u0004\b\u0000\u0010\u00012\u00020\u0002B\u0013\u0012\f\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004¢\u0006\u0002\u0010\u0006J\u000e\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u0005J\u0014\u0010\u0012\u001a\u00020\u00102\f\u0010\u0013\u001a\b\u0012\u0004\u0012\u00028\u00000\u0014J\u0006\u0010\u0015\u001a\u00020\u0010R\u0017\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR-\u0010\t\u001a\u001e\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00000\u000b0\nj\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00000\u000b`\f¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000e¨\u0006\u0016"}, d2 = {"Lorg/jetbrains/kotlin/codegen/State;", ExifInterface.GPS_DIRECTION_TRUE, "", "path", "", "", "(Ljava/util/List;)V", "getPath", "()Ljava/util/List;", "results", "Ljava/util/ArrayList;", "Lorg/jetbrains/kotlin/codegen/TypePathInfo;", "Lkotlin/collections/ArrayList;", "getResults", "()Ljava/util/ArrayList;", "addStep", "", "step", "rememberAnnotations", "annotations", "", "removeStep", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
final class State<T> {
    private final List<String> path;
    private final ArrayList<TypePathInfo<T>> results;

    public State(List<String> path) {
        Intrinsics.checkNotNullParameter(path, "path");
        this.path = path;
        this.results = new ArrayList<>();
    }

    public final List<String> getPath() {
        return this.path;
    }

    public final ArrayList<TypePathInfo<T>> getResults() {
        return this.results;
    }

    public final void addStep(String step) {
        Intrinsics.checkNotNullParameter(step, "step");
        this.path.add(step);
    }

    public final void removeStep() {
        List<String> list = this.path;
        list.remove(CollectionsKt.getLastIndex(list));
    }

    public final void rememberAnnotations(List<? extends T> annotations) {
        Intrinsics.checkNotNullParameter(annotations, "annotations");
        this.results.add(new TypePathInfo<>(TypePath.fromString(CollectionsKt.joinToString$default(this.path, "", null, null, 0, null, null, 62, null)), annotations));
    }
}
