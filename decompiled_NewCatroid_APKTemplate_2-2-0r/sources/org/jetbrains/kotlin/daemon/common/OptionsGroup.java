package org.jetbrains.kotlin.daemon.common;

import java.io.Serializable;
import java.util.List;
import kotlin.Metadata;

/* JADX INFO: compiled from: DaemonParams.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\bf\u0018\u00002\u00020\u0001R$\u0010\u0002\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0002\b\u0003\u0012\u0002\b\u0003\u0012\u0002\b\u00030\u00040\u0003X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0005\u0010\u0006ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\u0007À\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/daemon/common/OptionsGroup;", "Ljava/io/Serializable;", "mappers", "", "Lorg/jetbrains/kotlin/daemon/common/PropMapper;", "getMappers", "()Ljava/util/List;", "daemon-common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface OptionsGroup extends Serializable {
    List<PropMapper<?, ?, ?>> getMappers();
}
