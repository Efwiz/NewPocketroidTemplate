package org.jetbrains.kotlin.com.intellij.util.io;

import java.nio.channels.FileChannel;

/* JADX INFO: compiled from: D8$$SyntheticClass */
/* JADX INFO: loaded from: classes3.dex */
public final /* synthetic */ class FileChannelWithSizeTracking$$ExternalSyntheticLambda2 implements UnInterruptibleFileChannelHandle.FileChannelIdempotentOperation {
    @Override // org.jetbrains.kotlin.com.intellij.util.io.UnInterruptibleFileChannelHandle.FileChannelIdempotentOperation
    public final Object execute(FileChannel fileChannel) {
        return Long.valueOf(fileChannel.size());
    }
}
