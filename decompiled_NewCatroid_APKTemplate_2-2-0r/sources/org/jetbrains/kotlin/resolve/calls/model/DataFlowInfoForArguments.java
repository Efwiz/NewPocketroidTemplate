package org.jetbrains.kotlin.resolve.calls.model;

import org.jetbrains.kotlin.psi.ValueArgument;
import org.jetbrains.kotlin.resolve.calls.smartcasts.DataFlowInfo;

/* JADX INFO: loaded from: classes6.dex */
public interface DataFlowInfoForArguments {
    DataFlowInfo getInfo(ValueArgument valueArgument);

    DataFlowInfo getResultInfo();
}
