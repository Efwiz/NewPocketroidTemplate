package kotlin.script.experimental.util;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.script.experimental.api.ScriptSourceAnnotation;
import org.jetbrains.kotlin.utils.PathUtil;

/* JADX INFO: compiled from: filterByAnnotationType.kt */
/* JADX INFO: loaded from: classes12.dex */
@Metadata(d1 = {"\u0000\u0016\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001b\n\u0002\u0010\u001c\n\u0000\u001a/\u0010\u0000\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00030\u00020\u0001\"\n\b\u0000\u0010\u0003\u0018\u0001*\u00020\u0004*\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00020\u0005H\u0086\b¨\u0006\u0006"}, d2 = {"filterByAnnotationType", "", "Lkotlin/script/experimental/api/ScriptSourceAnnotation;", "A", "", "", PathUtil.KOTLIN_SCRIPTING_COMMON_NAME}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class FilterByAnnotationTypeKt {
    public static final /* synthetic */ <A extends Annotation> List<ScriptSourceAnnotation<A>> filterByAnnotationType(Iterable<? extends ScriptSourceAnnotation<?>> iterable) {
        Intrinsics.checkNotNullParameter(iterable, "<this>");
        ArrayList arrayList = new ArrayList();
        for (ScriptSourceAnnotation<?> scriptSourceAnnotation : iterable) {
            Annotation annotation = scriptSourceAnnotation.getAnnotation();
            Intrinsics.reifiedOperationMarker(3, "A");
            if (annotation instanceof Annotation) {
                arrayList.add(scriptSourceAnnotation);
            }
        }
        ArrayList<ScriptSourceAnnotation> arrayList2 = arrayList;
        ArrayList arrayList3 = new ArrayList(CollectionsKt.collectionSizeOrDefault(arrayList2, 10));
        for (ScriptSourceAnnotation scriptSourceAnnotation2 : arrayList2) {
            Intrinsics.checkNotNull(scriptSourceAnnotation2, "null cannot be cast to non-null type kotlin.script.experimental.api.ScriptSourceAnnotation<A of kotlin.script.experimental.util.FilterByAnnotationTypeKt.filterByAnnotationType$lambda$1>");
            arrayList3.add(scriptSourceAnnotation2);
        }
        return arrayList3;
    }
}
