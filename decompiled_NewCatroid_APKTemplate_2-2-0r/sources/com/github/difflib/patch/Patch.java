package com.github.difflib.patch;

import com.github.difflib.algorithm.Change;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.ListIterator;
import java.util.function.Function;

/* JADX INFO: loaded from: classes.dex */
public final class Patch<T> implements Serializable {
    public static final ConflictOutput<String> CONFLICT_PRODUCES_MERGE_CONFLICT = new Patch$$ExternalSyntheticLambda0();
    public final ConflictOutput<T> CONFLICT_PRODUCES_EXCEPTION;
    private ConflictOutput<T> conflictOutput;
    private final List<AbstractDelta<T>> deltas;

    public Patch() {
        this(10);
    }

    public Patch(int i) {
        Patch$$ExternalSyntheticLambda2 patch$$ExternalSyntheticLambda2 = new Patch$$ExternalSyntheticLambda2();
        this.CONFLICT_PRODUCES_EXCEPTION = patch$$ExternalSyntheticLambda2;
        this.conflictOutput = patch$$ExternalSyntheticLambda2;
        this.deltas = new ArrayList(i);
    }

    public List<T> applyTo(List<T> list) throws PatchFailedException {
        ArrayList arrayList = new ArrayList(list);
        ListIterator<AbstractDelta<T>> listIterator = getDeltas().listIterator(this.deltas.size());
        while (listIterator.hasPrevious()) {
            AbstractDelta<T> abstractDeltaPrevious = listIterator.previous();
            VerifyChunk verifyChunkVerifyAntApplyTo = abstractDeltaPrevious.verifyAntApplyTo(arrayList);
            if (verifyChunkVerifyAntApplyTo != VerifyChunk.OK) {
                this.conflictOutput.processConflict(verifyChunkVerifyAntApplyTo, abstractDeltaPrevious, arrayList);
            }
        }
        return arrayList;
    }

    private static class PatchApplyingContext<T> {
        public boolean afterOutRange;
        public boolean beforeOutRange;
        public int currentFuzz;
        public int defaultPosition;
        public int lastPatchEnd;
        public final int maxFuzz;
        public final List<T> result;

        /* synthetic */ PatchApplyingContext(List list, int i, AnonymousClass1 anonymousClass1) {
            this(list, i);
        }

        private PatchApplyingContext(List<T> list, int i) {
            this.lastPatchEnd = -1;
            this.currentFuzz = 0;
            this.beforeOutRange = false;
            this.afterOutRange = false;
            this.result = list;
            this.maxFuzz = i;
        }
    }

    public List<T> applyFuzzy(List<T> list, int i) throws PatchFailedException {
        PatchApplyingContext<T> patchApplyingContext = new PatchApplyingContext<>(new ArrayList(list), i, null);
        int i2 = 0;
        for (AbstractDelta<T> abstractDelta : getDeltas()) {
            patchApplyingContext.defaultPosition = abstractDelta.getSource().getPosition() + i2;
            int iFindPositionFuzzy = findPositionFuzzy(patchApplyingContext, abstractDelta);
            if (iFindPositionFuzzy >= 0) {
                abstractDelta.applyFuzzyToAt(patchApplyingContext.result, patchApplyingContext.currentFuzz, iFindPositionFuzzy);
                int position = iFindPositionFuzzy - abstractDelta.getSource().getPosition();
                patchApplyingContext.lastPatchEnd = abstractDelta.getSource().last() + position;
                i2 = position;
            } else {
                this.conflictOutput.processConflict(VerifyChunk.CONTENT_DOES_NOT_MATCH_TARGET, abstractDelta, patchApplyingContext.result);
            }
        }
        return patchApplyingContext.result;
    }

    private int findPositionFuzzy(PatchApplyingContext<T> patchApplyingContext, AbstractDelta<T> abstractDelta) throws PatchFailedException {
        for (int i = 0; i <= patchApplyingContext.maxFuzz; i++) {
            patchApplyingContext.currentFuzz = i;
            int iFindPositionWithFuzz = findPositionWithFuzz(patchApplyingContext, abstractDelta, i);
            if (iFindPositionWithFuzz >= 0) {
                return iFindPositionWithFuzz;
            }
        }
        return -1;
    }

    private int findPositionWithFuzz(PatchApplyingContext<T> patchApplyingContext, AbstractDelta<T> abstractDelta, int i) throws PatchFailedException {
        if (abstractDelta.getSource().verifyChunk(patchApplyingContext.result, i, patchApplyingContext.defaultPosition) == VerifyChunk.OK) {
            return patchApplyingContext.defaultPosition;
        }
        patchApplyingContext.beforeOutRange = false;
        patchApplyingContext.afterOutRange = false;
        for (int i2 = 0; i2 >= 0; i2++) {
            int iFindPositionWithFuzzAndMoreDelta = findPositionWithFuzzAndMoreDelta(patchApplyingContext, abstractDelta, i, i2);
            if (iFindPositionWithFuzzAndMoreDelta >= 0) {
                return iFindPositionWithFuzzAndMoreDelta;
            }
            if (patchApplyingContext.beforeOutRange && patchApplyingContext.afterOutRange) {
                return -1;
            }
        }
        return -1;
    }

    private int findPositionWithFuzzAndMoreDelta(PatchApplyingContext<T> patchApplyingContext, AbstractDelta<T> abstractDelta, int i, int i2) throws PatchFailedException {
        if (!patchApplyingContext.beforeOutRange && (patchApplyingContext.defaultPosition - i2) + i <= patchApplyingContext.lastPatchEnd) {
            patchApplyingContext.beforeOutRange = true;
        }
        if (!patchApplyingContext.afterOutRange) {
            if (patchApplyingContext.result.size() < ((patchApplyingContext.defaultPosition + i2) + abstractDelta.getSource().size()) - i) {
                patchApplyingContext.afterOutRange = true;
            }
        }
        if (!patchApplyingContext.beforeOutRange && abstractDelta.getSource().verifyChunk(patchApplyingContext.result, i, patchApplyingContext.defaultPosition - i2) == VerifyChunk.OK) {
            return patchApplyingContext.defaultPosition - i2;
        }
        if (patchApplyingContext.afterOutRange || abstractDelta.getSource().verifyChunk(patchApplyingContext.result, i, patchApplyingContext.defaultPosition + i2) != VerifyChunk.OK) {
            return -1;
        }
        return patchApplyingContext.defaultPosition + i2;
    }

    static /* synthetic */ void lambda$new$e23a5734$1(VerifyChunk verifyChunk, AbstractDelta abstractDelta, List list) throws PatchFailedException {
        throw new PatchFailedException("could not apply patch due to " + verifyChunk.toString());
    }

    static /* synthetic */ void lambda$static$5696245$1(VerifyChunk verifyChunk, AbstractDelta abstractDelta, List list) throws PatchFailedException {
        if (list.size() > abstractDelta.getSource().getPosition()) {
            ArrayList arrayList = new ArrayList();
            for (int i = 0; i < abstractDelta.getSource().size(); i++) {
                arrayList.add(list.get(abstractDelta.getSource().getPosition()));
                list.remove(abstractDelta.getSource().getPosition());
            }
            arrayList.add(0, "<<<<<< HEAD");
            arrayList.add("======");
            arrayList.addAll(abstractDelta.getSource().getLines());
            arrayList.add(">>>>>>> PATCH");
            list.addAll(abstractDelta.getSource().getPosition(), arrayList);
            return;
        }
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Patch withConflictOutput(ConflictOutput<T> conflictOutput) {
        this.conflictOutput = conflictOutput;
        return this;
    }

    public List<T> restore(List<T> list) {
        ArrayList arrayList = new ArrayList(list);
        ListIterator<AbstractDelta<T>> listIterator = getDeltas().listIterator(this.deltas.size());
        while (listIterator.hasPrevious()) {
            listIterator.previous().restore(arrayList);
        }
        return arrayList;
    }

    public void addDelta(AbstractDelta<T> abstractDelta) {
        this.deltas.add(abstractDelta);
    }

    public List<AbstractDelta<T>> getDeltas() {
        this.deltas.sort(Comparator.comparing(new Function() { // from class: com.github.difflib.patch.Patch$$ExternalSyntheticLambda3
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return Integer.valueOf(((AbstractDelta) obj).getSource().getPosition());
            }
        }));
        return this.deltas;
    }

    public String toString() {
        return "Patch{deltas=" + this.deltas + '}';
    }

    public static <T> Patch<T> generate(List<T> list, List<T> list2, List<Change> list3) {
        return generate(list, list2, list3, false);
    }

    private static <T> Chunk<T> buildChunk(int i, int i2, List<T> list) {
        return new Chunk<>(i, new ArrayList(list.subList(i, i2)));
    }

    public static <T> Patch<T> generate(List<T> list, List<T> list2, List<Change> list3, boolean z) {
        Patch<T> patch = new Patch<>(list3.size());
        if (z) {
            ArrayList arrayList = new ArrayList(list3);
            Collections.sort(arrayList, Comparator.comparing(new Function() { // from class: com.github.difflib.patch.Patch$$ExternalSyntheticLambda1
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return Integer.valueOf(((Change) obj).startOriginal);
                }
            }));
            list3 = arrayList;
        }
        int i = 0;
        int i2 = 0;
        for (Change change : list3) {
            if (z && i < change.startOriginal) {
                patch.addDelta(new EqualDelta(buildChunk(i, change.startOriginal, list), buildChunk(i2, change.startRevised, list2)));
            }
            Chunk chunkBuildChunk = buildChunk(change.startOriginal, change.endOriginal, list);
            Chunk chunkBuildChunk2 = buildChunk(change.startRevised, change.endRevised, list2);
            int i3 = AnonymousClass1.$SwitchMap$com$github$difflib$patch$DeltaType[change.deltaType.ordinal()];
            if (i3 == 1) {
                patch.addDelta(new DeleteDelta(chunkBuildChunk, chunkBuildChunk2));
            } else if (i3 == 2) {
                patch.addDelta(new InsertDelta(chunkBuildChunk, chunkBuildChunk2));
            } else if (i3 == 3) {
                patch.addDelta(new ChangeDelta(chunkBuildChunk, chunkBuildChunk2));
            }
            i = change.endOriginal;
            i2 = change.endRevised;
        }
        if (z && i < list.size()) {
            patch.addDelta(new EqualDelta(buildChunk(i, list.size(), list), buildChunk(i2, list2.size(), list2)));
        }
        return patch;
    }

    /* JADX INFO: renamed from: com.github.difflib.patch.Patch$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$github$difflib$patch$DeltaType;

        static {
            int[] iArr = new int[DeltaType.values().length];
            $SwitchMap$com$github$difflib$patch$DeltaType = iArr;
            try {
                iArr[DeltaType.DELETE.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$github$difflib$patch$DeltaType[DeltaType.INSERT.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$github$difflib$patch$DeltaType[DeltaType.CHANGE.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
        }
    }
}
