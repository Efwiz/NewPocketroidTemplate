package org.jetbrains.kotlin.cli.common.repl;

import kotlin.Metadata;

/* JADX INFO: compiled from: ReplState.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u000f\n\u0000\n\u0002\u0010\b\n\u0002\b\u0005\bf\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001R\u0012\u0010\u0002\u001a\u00020\u0003X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0004\u0010\u0005R\u0012\u0010\u0006\u001a\u00020\u0003X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0007\u0010\u0005ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\bÀ\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/cli/common/repl/ILineId;", "", "generation", "", "getGeneration", "()I", "no", "getNo", "cli-common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface ILineId extends Comparable<ILineId> {
    int getGeneration();

    int getNo();
}
