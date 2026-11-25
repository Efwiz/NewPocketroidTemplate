package kotlin.script.experimental.util;

import androidx.exifinterface.media.ExifInterface;
import kotlin.Metadata;
import net.bytebuddy.implementation.auxiliary.TypeProxy;
import org.jetbrains.kotlin.utils.PathUtil;

/* JADX INFO: compiled from: linkedSnippet.kt */
/* JADX INFO: loaded from: classes12.dex */
@Metadata(d1 = {"\u0000\u000e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\t\u0018\u0000*\u0004\b\u0000\u0010\u00012\b\u0012\u0004\u0012\u0002H\u00010\u0002B\u001d\u0012\u0006\u0010\u0003\u001a\u00028\u0000\u0012\u000e\u0010\u0004\u001a\n\u0012\u0004\u0012\u00028\u0000\u0018\u00010\u0000¢\u0006\u0002\u0010\u0005J\r\u0010\t\u001a\u00028\u0000H\u0016¢\u0006\u0002\u0010\nR\u0010\u0010\u0003\u001a\u00028\u0000X\u0082\u0004¢\u0006\u0004\n\u0002\u0010\u0006R\u001c\u0010\u0004\u001a\n\u0012\u0004\u0012\u00028\u0000\u0018\u00010\u0000X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\b¨\u0006\u000b"}, d2 = {"Lkotlin/script/experimental/util/LinkedSnippetImpl;", ExifInterface.GPS_DIRECTION_TRUE, "Lkotlin/script/experimental/util/LinkedSnippet;", "_val", "previous", "(Ljava/lang/Object;Lkotlin/script/experimental/util/LinkedSnippetImpl;)V", TypeProxy.SilentConstruction.Appender.JAVA_LANG_OBJECT_DESCRIPTOR, "getPrevious", "()Lkotlin/script/experimental/util/LinkedSnippetImpl;", "get", "()Ljava/lang/Object;", PathUtil.KOTLIN_SCRIPTING_COMMON_NAME}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class LinkedSnippetImpl<T> implements LinkedSnippet<T> {
    private final T _val;
    private final LinkedSnippetImpl<T> previous;

    public LinkedSnippetImpl(T t, LinkedSnippetImpl<T> linkedSnippetImpl) {
        this._val = t;
        this.previous = linkedSnippetImpl;
    }

    @Override // kotlin.script.experimental.util.LinkedSnippet
    public LinkedSnippetImpl<T> getPrevious() {
        return this.previous;
    }

    @Override // kotlin.script.experimental.util.LinkedSnippet
    public T get() {
        return this._val;
    }
}
