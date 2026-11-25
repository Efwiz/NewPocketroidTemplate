package org.jetbrains.kotlin.resolve.calls.smartcasts;

import kotlin.Metadata;

/* JADX INFO: compiled from: DataFlowInfo.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u0010\u0010\u0003\u001a\u00020\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000¨\u0006\u0005"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/smartcasts/DataFlowInfoFactory;", "", "()V", "EMPTY", "Lorg/jetbrains/kotlin/resolve/calls/smartcasts/DataFlowInfo;", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class DataFlowInfoFactory {
    public static final DataFlowInfoFactory INSTANCE = new DataFlowInfoFactory();
    public static final DataFlowInfo EMPTY = new DataFlowInfoImpl();

    private DataFlowInfoFactory() {
    }
}
