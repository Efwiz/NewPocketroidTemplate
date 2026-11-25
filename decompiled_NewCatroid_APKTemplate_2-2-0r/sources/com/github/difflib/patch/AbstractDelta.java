package com.github.difflib.patch;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

/* JADX INFO: loaded from: classes.dex */
public abstract class AbstractDelta<T> implements Serializable {
    private final Chunk<T> source;
    private final Chunk<T> target;
    private final DeltaType type;

    protected abstract void applyTo(List<T> list) throws PatchFailedException;

    protected abstract void restore(List<T> list);

    public abstract AbstractDelta<T> withChunks(Chunk<T> chunk, Chunk<T> chunk2);

    public AbstractDelta(DeltaType deltaType, Chunk<T> chunk, Chunk<T> chunk2) {
        Objects.requireNonNull(chunk);
        Objects.requireNonNull(chunk2);
        Objects.requireNonNull(deltaType);
        this.type = deltaType;
        this.source = chunk;
        this.target = chunk2;
    }

    public Chunk<T> getSource() {
        return this.source;
    }

    public Chunk<T> getTarget() {
        return this.target;
    }

    public DeltaType getType() {
        return this.type;
    }

    protected VerifyChunk verifyChunkToFitTarget(List<T> list) throws PatchFailedException {
        return getSource().verifyChunk(list);
    }

    protected VerifyChunk verifyAntApplyTo(List<T> list) throws PatchFailedException {
        VerifyChunk verifyChunkVerifyChunkToFitTarget = verifyChunkToFitTarget(list);
        if (verifyChunkVerifyChunkToFitTarget == VerifyChunk.OK) {
            applyTo(list);
        }
        return verifyChunkVerifyChunkToFitTarget;
    }

    protected void applyFuzzyToAt(List<T> list, int i, int i2) throws PatchFailedException {
        throw new UnsupportedOperationException(getClass().getSimpleName() + " does not supports applying patch fuzzy");
    }

    public int hashCode() {
        return Objects.hash(this.source, this.target, this.type);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        AbstractDelta abstractDelta = (AbstractDelta) obj;
        return Objects.equals(this.source, abstractDelta.source) && Objects.equals(this.target, abstractDelta.target) && this.type == abstractDelta.type;
    }
}
