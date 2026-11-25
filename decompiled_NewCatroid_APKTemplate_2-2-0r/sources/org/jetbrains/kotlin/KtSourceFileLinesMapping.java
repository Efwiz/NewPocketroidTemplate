package org.jetbrains.kotlin;

import androidx.constraintlayout.core.motion.utils.TypedValues;
import com.huawei.hms.ml.language.common.utils.Constant;
import kotlin.Metadata;
import kotlin.Pair;

/* JADX INFO: compiled from: KtSourceFileLinesMapping.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\bf\u0018\u00002\u00020\u0001J\u001c\u0010\b\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00030\t2\u0006\u0010\n\u001a\u00020\u0003H&J\u0010\u0010\u000b\u001a\u00020\u00032\u0006\u0010\n\u001a\u00020\u0003H&J\u0010\u0010\f\u001a\u00020\u00032\u0006\u0010\r\u001a\u00020\u0003H&R\u0012\u0010\u0002\u001a\u00020\u0003X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0004\u0010\u0005R\u0012\u0010\u0006\u001a\u00020\u0003X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0007\u0010\u0005ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\u000eÀ\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/KtSourceFileLinesMapping;", "", "lastOffset", "", "getLastOffset", "()I", "linesCount", "getLinesCount", "getLineAndColumnByOffset", "Lkotlin/Pair;", TypedValues.CycleType.S_WAVE_OFFSET, "getLineByOffset", "getLineStartOffset", Constant.LINE, "frontend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface KtSourceFileLinesMapping {
    int getLastOffset();

    Pair<Integer, Integer> getLineAndColumnByOffset(int offset);

    int getLineByOffset(int offset);

    int getLineStartOffset(int line);

    int getLinesCount();
}
