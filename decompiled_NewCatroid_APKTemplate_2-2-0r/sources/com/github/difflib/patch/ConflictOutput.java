package com.github.difflib.patch;

import java.io.Serializable;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
@FunctionalInterface
public interface ConflictOutput<T> extends Serializable {
    void processConflict(VerifyChunk verifyChunk, AbstractDelta<T> abstractDelta, List<T> list) throws PatchFailedException;
}
