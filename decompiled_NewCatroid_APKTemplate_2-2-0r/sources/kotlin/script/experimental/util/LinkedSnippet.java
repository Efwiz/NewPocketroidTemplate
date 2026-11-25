package kotlin.script.experimental.util;

import androidx.exifinterface.media.ExifInterface;
import kotlin.Metadata;
import org.jetbrains.kotlin.utils.PathUtil;

/* JADX INFO: compiled from: linkedSnippet.kt */
/* JADX INFO: loaded from: classes12.dex */
@Metadata(d1 = {"\u0000\u000e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0006\bf\u0018\u0000*\u0004\b\u0000\u0010\u00012\u00020\u0002J\r\u0010\u0006\u001a\u00028\u0000H&¢\u0006\u0002\u0010\u0007R\u001a\u0010\u0003\u001a\n\u0012\u0004\u0012\u00028\u0000\u0018\u00010\u0000X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0004\u0010\u0005ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\bÀ\u0006\u0001"}, d2 = {"Lkotlin/script/experimental/util/LinkedSnippet;", ExifInterface.GPS_DIRECTION_TRUE, "", "previous", "getPrevious", "()Lkotlin/script/experimental/util/LinkedSnippet;", "get", "()Ljava/lang/Object;", PathUtil.KOTLIN_SCRIPTING_COMMON_NAME}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface LinkedSnippet<T> {
    T get();

    LinkedSnippet<T> getPrevious();
}
