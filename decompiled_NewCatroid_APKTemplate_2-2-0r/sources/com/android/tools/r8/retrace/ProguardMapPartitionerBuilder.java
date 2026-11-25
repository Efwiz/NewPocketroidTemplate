package com.android.tools.r8.retrace;

import com.android.tools.r8.retrace.ProguardMapPartitioner;
import com.android.tools.r8.retrace.ProguardMapPartitionerBuilder;
import java.util.function.Consumer;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public interface ProguardMapPartitionerBuilder<B extends ProguardMapPartitionerBuilder<B, P>, P extends ProguardMapPartitioner> {
    P build();

    B setAllowEmptyMappedRanges(boolean z);

    B setAllowExperimentalMapping(boolean z);

    B setPartitionConsumer(Consumer<MappingPartition> consumer);

    B setProguardMapProducer(ProguardMapProducer proguardMapProducer);
}
