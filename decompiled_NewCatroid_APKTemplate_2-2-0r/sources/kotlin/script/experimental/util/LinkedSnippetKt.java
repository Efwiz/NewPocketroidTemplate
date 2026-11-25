package kotlin.script.experimental.util;

import androidx.exifinterface.media.ExifInterface;
import com.google.android.gms.analytics.ecommerce.ProductAction;
import java.util.ArrayList;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.utils.PathUtil;

/* JADX INFO: compiled from: linkedSnippet.kt */
/* JADX INFO: loaded from: classes12.dex */
@Metadata(d1 = {"\u0000 \n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\u001a+\u0010\u0000\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\n\u0012\u0004\u0012\u0002H\u0002\u0018\u00010\u00012\u0006\u0010\u0003\u001a\u0002H\u0002¢\u0006\u0002\u0010\u0004\u001a\u001f\u0010\u0005\u001a\u0004\u0018\u0001H\u0002\"\u0004\b\u0000\u0010\u0002*\n\u0012\u0004\u0012\u0002H\u0002\u0018\u00010\u0006¢\u0006\u0002\u0010\u0007\u001a\u001e\u0010\b\u001a\b\u0012\u0004\u0012\u0002H\u00020\t\"\u0004\b\u0000\u0010\u0002*\n\u0012\u0004\u0012\u0002H\u0002\u0018\u00010\u0006\u001a8\u0010\b\u001a\b\u0012\u0004\u0012\u0002H\n0\t\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\n*\n\u0012\u0004\u0012\u0002H\u0002\u0018\u00010\u00062\u0012\u0010\u000b\u001a\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\n0\f¨\u0006\r"}, d2 = {ProductAction.ACTION_ADD, "Lkotlin/script/experimental/util/LinkedSnippetImpl;", ExifInterface.GPS_DIRECTION_TRUE, "value", "(Lkotlin/script/experimental/util/LinkedSnippetImpl;Ljava/lang/Object;)Lkotlin/script/experimental/util/LinkedSnippetImpl;", "get", "Lkotlin/script/experimental/util/LinkedSnippet;", "(Lkotlin/script/experimental/util/LinkedSnippet;)Ljava/lang/Object;", "toList", "", "R", "mapper", "Lkotlin/Function1;", PathUtil.KOTLIN_SCRIPTING_COMMON_NAME}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class LinkedSnippetKt {
    public static final <T> List<T> toList(LinkedSnippet<T> linkedSnippet) {
        return toList(linkedSnippet, new Function1<T, T>() { // from class: kotlin.script.experimental.util.LinkedSnippetKt.toList.1
            @Override // kotlin.jvm.functions.Function1
            public final T invoke(T t) {
                return t;
            }
        });
    }

    public static final <T, R> List<R> toList(LinkedSnippet<T> linkedSnippet, Function1<? super T, ? extends R> mapper) {
        Intrinsics.checkNotNullParameter(mapper, "mapper");
        ArrayList arrayList = new ArrayList();
        while (linkedSnippet != null) {
            arrayList.add(mapper.invoke(linkedSnippet.get()));
            linkedSnippet = linkedSnippet.getPrevious();
        }
        ArrayList arrayList2 = arrayList;
        CollectionsKt.reverse(arrayList2);
        return arrayList2;
    }

    public static final <T> T get(LinkedSnippet<T> linkedSnippet) {
        if (linkedSnippet != null) {
            return linkedSnippet.get();
        }
        return null;
    }

    public static final <T> LinkedSnippetImpl<T> add(LinkedSnippetImpl<T> linkedSnippetImpl, T t) {
        return new LinkedSnippetImpl<>(t, linkedSnippetImpl);
    }
}
