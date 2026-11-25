package org.jetbrains.kotlin.js.dce;

import androidx.core.app.NotificationCompat;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: DeadCodeEliminationResult.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001c\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\u0018\u00002\u00020\u0001B)\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\u0010\u0010\u0004\u001a\f\u0012\b\u0012\u00060\u0006R\u00020\u00030\u0005\u0012\u0006\u0010\u0007\u001a\u00020\b¢\u0006\u0002\u0010\tR\u0013\u0010\u0002\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u001b\u0010\u0004\u001a\f\u0012\b\u0012\u00060\u0006R\u00020\u00030\u0005¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0011\u0010\u0007\u001a\u00020\b¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000f¨\u0006\u0010"}, d2 = {"Lorg/jetbrains/kotlin/js/dce/DeadCodeEliminationResult;", "", "context", "Lorg/jetbrains/kotlin/js/dce/Context;", "reachableNodes", "", "Lorg/jetbrains/kotlin/js/dce/Context$Node;", NotificationCompat.CATEGORY_STATUS, "Lorg/jetbrains/kotlin/js/dce/DeadCodeEliminationStatus;", "(Lorg/jetbrains/kotlin/js/dce/Context;Ljava/lang/Iterable;Lorg/jetbrains/kotlin/js/dce/DeadCodeEliminationStatus;)V", "getContext", "()Lorg/jetbrains/kotlin/js/dce/Context;", "getReachableNodes", "()Ljava/lang/Iterable;", "getStatus", "()Lorg/jetbrains/kotlin/js/dce/DeadCodeEliminationStatus;", "js.dce"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class DeadCodeEliminationResult {
    private final Context context;
    private final Iterable<Context.Node> reachableNodes;
    private final DeadCodeEliminationStatus status;

    public DeadCodeEliminationResult(Context context, Iterable<Context.Node> reachableNodes, DeadCodeEliminationStatus status) {
        Intrinsics.checkNotNullParameter(reachableNodes, "reachableNodes");
        Intrinsics.checkNotNullParameter(status, "status");
        this.context = context;
        this.reachableNodes = reachableNodes;
        this.status = status;
    }

    public final Context getContext() {
        return this.context;
    }

    public final Iterable<Context.Node> getReachableNodes() {
        return this.reachableNodes;
    }

    public final DeadCodeEliminationStatus getStatus() {
        return this.status;
    }
}
