package org.eclipse.jgit.merge;

import java.util.ArrayList;
import java.util.Iterator;
import org.eclipse.jgit.diff.DiffAlgorithm;
import org.eclipse.jgit.diff.Edit;
import org.eclipse.jgit.diff.HistogramDiff;
import org.eclipse.jgit.diff.Sequence;
import org.eclipse.jgit.diff.SequenceComparator;

/* JADX INFO: loaded from: classes13.dex */
public final class MergeAlgorithm {
    private static volatile /* synthetic */ int[] $SWITCH_TABLE$org$eclipse$jgit$merge$ContentMergeStrategy;
    private static final Edit END_EDIT = new Edit(Integer.MAX_VALUE, Integer.MAX_VALUE);
    private final DiffAlgorithm diffAlg;
    private ContentMergeStrategy strategy;

    static /* synthetic */ int[] $SWITCH_TABLE$org$eclipse$jgit$merge$ContentMergeStrategy() {
        int[] iArr = $SWITCH_TABLE$org$eclipse$jgit$merge$ContentMergeStrategy;
        if (iArr != null) {
            return iArr;
        }
        int[] iArr2 = new int[ContentMergeStrategy.valuesCustom().length];
        try {
            iArr2[ContentMergeStrategy.CONFLICT.ordinal()] = 1;
        } catch (NoSuchFieldError unused) {
        }
        try {
            iArr2[ContentMergeStrategy.OURS.ordinal()] = 2;
        } catch (NoSuchFieldError unused2) {
        }
        try {
            iArr2[ContentMergeStrategy.THEIRS.ordinal()] = 3;
        } catch (NoSuchFieldError unused3) {
        }
        $SWITCH_TABLE$org$eclipse$jgit$merge$ContentMergeStrategy = iArr2;
        return iArr2;
    }

    public MergeAlgorithm() {
        this(new HistogramDiff());
    }

    public MergeAlgorithm(DiffAlgorithm diffAlgorithm) {
        this.strategy = ContentMergeStrategy.CONFLICT;
        this.diffAlg = diffAlgorithm;
    }

    public ContentMergeStrategy getContentMergeStrategy() {
        return this.strategy;
    }

    public void setContentMergeStrategy(ContentMergeStrategy contentMergeStrategy) {
        if (contentMergeStrategy == null) {
            contentMergeStrategy = ContentMergeStrategy.CONFLICT;
        }
        this.strategy = contentMergeStrategy;
    }

    private static boolean isEndEdit(Edit edit) {
        return edit == END_EDIT;
    }

    /* JADX WARN: Code duplicated, block: B:105:0x0280  */
    public <S extends Sequence> MergeResult<S> merge(SequenceComparator<S> sequenceComparator, S s, S s2, S s3) {
        Iterator<Edit> it;
        Edit edit;
        Edit edit2;
        int i;
        char c;
        int i2;
        int i3;
        SequenceComparator<S> sequenceComparator2 = sequenceComparator;
        ArrayList arrayList = new ArrayList(3);
        arrayList.add(s);
        arrayList.add(s2);
        arrayList.add(s3);
        MergeResult<S> mergeResult = new MergeResult<>(arrayList);
        int i4 = 2;
        int i5 = 1;
        int i6 = 0;
        if (s2.size() == 0) {
            if (s3.size() == 0 || this.diffAlg.diff(sequenceComparator2, s, s3).isEmpty() || (i3 = $SWITCH_TABLE$org$eclipse$jgit$merge$ContentMergeStrategy()[this.strategy.ordinal()]) == 2) {
                mergeResult.add(1, 0, 0, MergeChunk.ConflictState.NO_CONFLICT);
            } else if (i3 == 3) {
                mergeResult.add(2, 0, s3.size(), MergeChunk.ConflictState.NO_CONFLICT);
            } else {
                mergeResult.add(1, 0, 0, MergeChunk.ConflictState.FIRST_CONFLICTING_RANGE);
                mergeResult.add(2, 0, s3.size(), MergeChunk.ConflictState.NEXT_CONFLICTING_RANGE);
            }
            return mergeResult;
        }
        if (s3.size() == 0) {
            if (!this.diffAlg.diff(sequenceComparator2, s, s2).isEmpty()) {
                int i7 = $SWITCH_TABLE$org$eclipse$jgit$merge$ContentMergeStrategy()[this.strategy.ordinal()];
                if (i7 == 2) {
                    mergeResult.add(1, 0, s2.size(), MergeChunk.ConflictState.NO_CONFLICT);
                } else if (i7 == 3) {
                    mergeResult.add(2, 0, 0, MergeChunk.ConflictState.NO_CONFLICT);
                } else {
                    mergeResult.add(1, 0, s2.size(), MergeChunk.ConflictState.FIRST_CONFLICTING_RANGE);
                    mergeResult.add(2, 0, 0, MergeChunk.ConflictState.NEXT_CONFLICTING_RANGE);
                }
            } else {
                mergeResult.add(2, 0, 0, MergeChunk.ConflictState.NO_CONFLICT);
            }
            return mergeResult;
        }
        Iterator<Edit> it2 = this.diffAlg.diff(sequenceComparator2, s, s2).iterator();
        Iterator<Edit> it3 = this.diffAlg.diff(sequenceComparator2, s, s3).iterator();
        Edit editNextEdit = nextEdit(it2);
        Edit editNextEdit2 = nextEdit(it3);
        int endA = 0;
        while (true) {
            if (isEndEdit(editNextEdit2) && isEndEdit(editNextEdit)) {
                break;
            }
            if (editNextEdit.getEndA() < editNextEdit2.getBeginA()) {
                if (endA != editNextEdit.getBeginA()) {
                    mergeResult.add(i6, endA, editNextEdit.getBeginA(), MergeChunk.ConflictState.NO_CONFLICT);
                }
                mergeResult.add(i5, editNextEdit.getBeginB(), editNextEdit.getEndB(), MergeChunk.ConflictState.NO_CONFLICT);
                endA = editNextEdit.getEndA();
                editNextEdit = nextEdit(it2);
            } else if (editNextEdit2.getEndA() < editNextEdit.getBeginA()) {
                if (endA != editNextEdit2.getBeginA()) {
                    mergeResult.add(i6, endA, editNextEdit2.getBeginA(), MergeChunk.ConflictState.NO_CONFLICT);
                }
                mergeResult.add(i4, editNextEdit2.getBeginB(), editNextEdit2.getEndB(), MergeChunk.ConflictState.NO_CONFLICT);
                endA = editNextEdit2.getEndA();
                editNextEdit2 = nextEdit(it3);
            } else {
                if (editNextEdit.getBeginA() != endA && editNextEdit2.getBeginA() != endA) {
                    mergeResult.add(i6, endA, Math.min(editNextEdit.getBeginA(), editNextEdit2.getBeginA()), MergeChunk.ConflictState.NO_CONFLICT);
                }
                int beginB = editNextEdit.getBeginB();
                int beginB2 = editNextEdit2.getBeginB();
                if (editNextEdit.getBeginA() < editNextEdit2.getBeginA()) {
                    beginB2 -= editNextEdit2.getBeginA() - editNextEdit.getBeginA();
                } else {
                    beginB -= editNextEdit.getBeginA() - editNextEdit2.getBeginA();
                }
                Edit editNextEdit3 = nextEdit(it2);
                Edit edit3 = editNextEdit2;
                Edit editNextEdit4 = nextEdit(it3);
                while (true) {
                    if (editNextEdit.getEndA() >= editNextEdit4.getBeginA()) {
                        edit3 = editNextEdit4;
                        editNextEdit4 = nextEdit(it3);
                    } else {
                        if (edit3.getEndA() < editNextEdit3.getBeginA()) {
                            break;
                        }
                        editNextEdit = editNextEdit3;
                        editNextEdit3 = nextEdit(it2);
                    }
                }
                int endB = editNextEdit.getEndB();
                int endB2 = edit3.getEndB();
                Iterator<Edit> it4 = it2;
                if (editNextEdit.getEndA() < edit3.getEndA()) {
                    endB += edit3.getEndA() - editNextEdit.getEndA();
                } else {
                    endB2 += editNextEdit.getEndA() - edit3.getEndA();
                }
                int i8 = endB - beginB;
                int i9 = i8 - (endB2 - beginB2);
                if (i9 > 0) {
                    i8 -= i9;
                }
                Edit edit4 = editNextEdit3;
                int i10 = 0;
                while (true) {
                    it = it3;
                    if (i10 >= i8) {
                        edit = editNextEdit4;
                        break;
                    }
                    edit = editNextEdit4;
                    if (!sequenceComparator2.equals(s2, beginB + i10, s3, beginB2 + i10)) {
                        break;
                    }
                    i10++;
                    it3 = it;
                    editNextEdit4 = edit;
                }
                int i11 = i8 - i10;
                int i12 = 0;
                while (true) {
                    if (i12 >= i11) {
                        edit2 = edit3;
                        break;
                    }
                    edit2 = edit3;
                    if (!sequenceComparator2.equals(s2, (endB - i12) - 1, s3, (endB2 - i12) - 1)) {
                        break;
                    }
                    i12++;
                    edit3 = edit2;
                }
                int i13 = i11 - i12;
                if (i10 > 0) {
                    mergeResult.add(1, beginB, beginB + i10, MergeChunk.ConflictState.NO_CONFLICT);
                }
                if (i13 > 0 || i9 != 0) {
                    int i14 = $SWITCH_TABLE$org$eclipse$jgit$merge$ContentMergeStrategy()[this.strategy.ordinal()];
                    i = 2;
                    if (i14 != 2) {
                        c = 3;
                        if (i14 == 3) {
                            mergeResult.add(2, beginB2 + i10, endB2 - i12, MergeChunk.ConflictState.NO_CONFLICT);
                        } else {
                            mergeResult.add(1, beginB + i10, endB - i12, MergeChunk.ConflictState.FIRST_CONFLICTING_RANGE);
                            mergeResult.add(2, beginB2 + i10, endB2 - i12, MergeChunk.ConflictState.NEXT_CONFLICTING_RANGE);
                        }
                    } else {
                        c = 3;
                        i2 = 1;
                        mergeResult.add(1, beginB + i10, endB - i12, MergeChunk.ConflictState.NO_CONFLICT);
                    }
                    if (i12 > 0) {
                        mergeResult.add(i2, endB - i12, endB, MergeChunk.ConflictState.NO_CONFLICT);
                    }
                    endA = Math.max(editNextEdit.getEndA(), edit2.getEndA());
                    sequenceComparator2 = sequenceComparator;
                    i4 = i;
                    i5 = i2;
                    it2 = it4;
                    editNextEdit = edit4;
                    it3 = it;
                    editNextEdit2 = edit;
                    i6 = 0;
                } else {
                    i = 2;
                    c = 3;
                }
                i2 = 1;
                if (i12 > 0) {
                    mergeResult.add(i2, endB - i12, endB, MergeChunk.ConflictState.NO_CONFLICT);
                }
                endA = Math.max(editNextEdit.getEndA(), edit2.getEndA());
                sequenceComparator2 = sequenceComparator;
                i4 = i;
                i5 = i2;
                it2 = it4;
                editNextEdit = edit4;
                it3 = it;
                editNextEdit2 = edit;
                i6 = 0;
            }
        }
        if (endA < s.size()) {
            mergeResult.add(i6, endA, s.size(), MergeChunk.ConflictState.NO_CONFLICT);
        }
        return mergeResult;
    }

    private static Edit nextEdit(Iterator<Edit> it) {
        return it.hasNext() ? it.next() : END_EDIT;
    }
}
