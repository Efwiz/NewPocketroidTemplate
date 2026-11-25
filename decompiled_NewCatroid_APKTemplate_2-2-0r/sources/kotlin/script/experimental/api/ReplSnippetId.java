package kotlin.script.experimental.api;

import java.io.Serializable;
import kotlin.Metadata;
import org.jetbrains.kotlin.utils.PathUtil;

/* JADX INFO: compiled from: replData.kt */
/* JADX INFO: loaded from: classes12.dex */
@Metadata(d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000f\n\u0000\n\u0002\u0010\b\n\u0002\b\u0005\bf\u0018\u00002\u00020\u00012\b\u0012\u0004\u0012\u00020\u00000\u0002R\u0012\u0010\u0003\u001a\u00020\u0004X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0005\u0010\u0006R\u0012\u0010\u0007\u001a\u00020\u0004X¦\u0004¢\u0006\u0006\u001a\u0004\b\b\u0010\u0006ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\tÀ\u0006\u0001"}, d2 = {"Lkotlin/script/experimental/api/ReplSnippetId;", "Ljava/io/Serializable;", "", "generation", "", "getGeneration", "()I", "no", "getNo", PathUtil.KOTLIN_SCRIPTING_COMMON_NAME}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface ReplSnippetId extends Serializable, Comparable<ReplSnippetId> {
    int getGeneration();

    int getNo();
}
