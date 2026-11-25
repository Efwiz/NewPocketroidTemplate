package com.github.difflib.algorithm.myers;

import com.github.difflib.algorithm.Change;
import com.github.difflib.algorithm.DiffAlgorithmFactory;
import com.github.difflib.algorithm.DiffAlgorithmI;
import com.github.difflib.algorithm.DiffAlgorithmListener;
import com.github.difflib.patch.DeltaType;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.BiPredicate;
import java.util.function.Consumer;

/* JADX INFO: loaded from: classes.dex */
public class MeyersDiffWithLinearSpace<T> implements DiffAlgorithmI<T> {
    private final BiPredicate<T, T> equalizer;

    public MeyersDiffWithLinearSpace() {
        this.equalizer = new BiPredicate() { // from class: com.github.difflib.algorithm.myers.MeyersDiffWithLinearSpace$$ExternalSyntheticLambda1
            @Override // java.util.function.BiPredicate
            public final boolean test(Object obj, Object obj2) {
                return obj.equals(obj2);
            }
        };
    }

    public MeyersDiffWithLinearSpace(BiPredicate<T, T> biPredicate) {
        Objects.requireNonNull(biPredicate, "equalizer must not be null");
        this.equalizer = biPredicate;
    }

    @Override // com.github.difflib.algorithm.DiffAlgorithmI
    public List<Change> computeDiff(List<T> list, List<T> list2, final DiffAlgorithmListener diffAlgorithmListener) {
        Objects.requireNonNull(list, "source list must not be null");
        Objects.requireNonNull(list2, "target list must not be null");
        if (diffAlgorithmListener != null) {
            diffAlgorithmListener.diffStart();
        }
        MeyersDiffWithLinearSpace<T>.DiffData diffData = new DiffData(list, list2);
        final int size = list.size() + list2.size();
        buildScript(diffData, 0, list.size(), 0, list2.size(), new Consumer() { // from class: com.github.difflib.algorithm.myers.MeyersDiffWithLinearSpace$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                MeyersDiffWithLinearSpace.lambda$computeDiff$0(diffAlgorithmListener, size, (Integer) obj);
            }
        });
        if (diffAlgorithmListener != null) {
            diffAlgorithmListener.diffEnd();
        }
        return diffData.script;
    }

    static /* synthetic */ void lambda$computeDiff$0(DiffAlgorithmListener diffAlgorithmListener, int i, Integer num) {
        if (diffAlgorithmListener != null) {
            diffAlgorithmListener.diffStep(num.intValue(), i);
        }
    }

    private void buildScript(MeyersDiffWithLinearSpace<T>.DiffData diffData, int i, int i2, int i3, int i4, Consumer<Integer> consumer) {
        if (consumer != null) {
            consumer.accept(Integer.valueOf(((i2 - i) / 2) + ((i4 - i3) / 2)));
        }
        MeyersDiffWithLinearSpace<T>.Snake middleSnake = getMiddleSnake(diffData, i, i2, i3, i4);
        if (middleSnake != null && ((middleSnake.start != i2 || middleSnake.diag != i2 - i4) && (middleSnake.end != i || middleSnake.diag != i - i3))) {
            buildScript(diffData, i, middleSnake.start, i3, middleSnake.start - middleSnake.diag, consumer);
            buildScript(diffData, middleSnake.end, i2, middleSnake.end - middleSnake.diag, i4, consumer);
            return;
        }
        int i5 = i3;
        int i6 = i;
        while (true) {
            if (i6 >= i2 && i5 >= i4) {
                return;
            }
            if (i6 < i2 && i5 < i4) {
                if (this.equalizer.test(diffData.source.get(i6), diffData.target.get(i5))) {
                    i6++;
                }
                i5++;
            }
            if (i2 - i > i4 - i3) {
                if (diffData.script.isEmpty() || diffData.script.get(diffData.script.size() - 1).endOriginal != i6 || diffData.script.get(diffData.script.size() - 1).deltaType != DeltaType.DELETE) {
                    diffData.script.add(new Change(DeltaType.DELETE, i6, i6 + 1, i5, i5));
                } else {
                    diffData.script.set(diffData.script.size() - 1, diffData.script.get(diffData.script.size() - 1).withEndOriginal(i6 + 1));
                }
                i6++;
            } else {
                if (diffData.script.isEmpty() || diffData.script.get(diffData.script.size() - 1).endRevised != i5 || diffData.script.get(diffData.script.size() - 1).deltaType != DeltaType.INSERT) {
                    diffData.script.add(new Change(DeltaType.INSERT, i6, i6, i5, i5 + 1));
                } else {
                    diffData.script.set(diffData.script.size() - 1, diffData.script.get(diffData.script.size() - 1).withEndRevised(i5 + 1));
                }
                i5++;
            }
        }
    }

    private MeyersDiffWithLinearSpace<T>.Snake getMiddleSnake(MeyersDiffWithLinearSpace<T>.DiffData diffData, int i, int i2, int i3, int i4) {
        int i5;
        int i6 = i2;
        int i7 = i6 - i;
        int i8 = i4 - i3;
        if (i7 == 0 || i8 == 0) {
            return null;
        }
        int i9 = i7 - i8;
        int i10 = i8 + i7;
        if (i10 % 2 != 0) {
            i10++;
        }
        int i11 = i10 / 2;
        int i12 = i11 + 1;
        diffData.vDown[i12] = i;
        diffData.vUp[i12] = i6 + 1;
        int i13 = 0;
        while (i13 <= i11) {
            int i14 = -i13;
            int i15 = i14;
            while (i15 <= i13) {
                int i16 = i15 + i11;
                if (i15 == i14 || (i15 != i13 && diffData.vDown[i16 - 1] < diffData.vDown[i16 + 1])) {
                    diffData.vDown[i16] = diffData.vDown[i16 + 1];
                } else {
                    diffData.vDown[i16] = diffData.vDown[i16 - 1] + 1;
                }
                int i17 = diffData.vDown[i16];
                int i18 = ((i17 - i) + i3) - i15;
                while (i17 < i6 && i18 < i4 && this.equalizer.test(diffData.source.get(i17), diffData.target.get(i18))) {
                    i17++;
                    diffData.vDown[i16] = i17;
                    i18++;
                    i6 = i2;
                }
                if (i9 % 2 != 0 && i9 - i13 <= i15 && i15 <= i9 + i13) {
                    int i19 = i16 - i9;
                    if (diffData.vUp[i19] <= diffData.vDown[i16]) {
                        return buildSnake(diffData, diffData.vUp[i19], (i15 + i) - i3, i2, i4);
                    }
                }
                i15 += 2;
                i6 = i2;
            }
            int i20 = i9 - i13;
            int i21 = i20;
            while (true) {
                int i22 = i9 + i13;
                if (i21 <= i22) {
                    int i23 = (i21 + i11) - i9;
                    if (i21 == i20 || (i21 != i22 && diffData.vUp[i23 + 1] <= diffData.vUp[i23 - 1])) {
                        diffData.vUp[i23] = diffData.vUp[i23 + 1] - 1;
                    } else {
                        diffData.vUp[i23] = diffData.vUp[i23 - 1];
                    }
                    int i24 = diffData.vUp[i23] - 1;
                    int i25 = ((i24 - i) + i3) - i21;
                    while (true) {
                        if (i24 >= i && i25 >= i3) {
                            i5 = i20;
                            if (!this.equalizer.test(diffData.source.get(i24), diffData.target.get(i25))) {
                                break;
                            }
                            diffData.vUp[i23] = i24;
                            i25--;
                            i24--;
                            i20 = i5;
                        } else {
                            i5 = i20;
                            break;
                        }
                    }
                    if (i9 % 2 == 0 && i14 <= i21 && i21 <= i13 && diffData.vUp[i23] <= diffData.vDown[i23 + i9]) {
                        return buildSnake(diffData, diffData.vUp[i23], (i21 + i) - i3, i2, i4);
                    }
                    i21 += 2;
                    i20 = i5;
                }
            }
            i13++;
            i6 = i2;
        }
        throw new IllegalStateException("could not find a diff path");
    }

    private MeyersDiffWithLinearSpace<T>.Snake buildSnake(MeyersDiffWithLinearSpace<T>.DiffData diffData, int i, int i2, int i3, int i4) {
        int i5 = i;
        while (true) {
            int i6 = i5 - i2;
            if (i6 >= i4 || i5 >= i3 || !this.equalizer.test(diffData.source.get(i5), diffData.target.get(i6))) {
                break;
            }
            i5++;
        }
        return new Snake(i, i5, i2);
    }

    private class DiffData {
        final List<Change> script;
        final int size;
        final List<T> source;
        final List<T> target;
        final int[] vDown;
        final int[] vUp;

        public DiffData(List<T> list, List<T> list2) {
            this.source = list;
            this.target = list2;
            int size = list.size() + list2.size() + 2;
            this.size = size;
            this.vDown = new int[size];
            this.vUp = new int[size];
            this.script = new ArrayList();
        }
    }

    private class Snake {
        final int diag;
        final int end;
        final int start;

        public Snake(int i, int i2, int i3) {
            this.start = i;
            this.end = i2;
            this.diag = i3;
        }
    }

    public static DiffAlgorithmFactory factory() {
        return new DiffAlgorithmFactory() { // from class: com.github.difflib.algorithm.myers.MeyersDiffWithLinearSpace.1
            @Override // com.github.difflib.algorithm.DiffAlgorithmFactory
            public <T> DiffAlgorithmI<T> create() {
                return new MeyersDiffWithLinearSpace();
            }

            @Override // com.github.difflib.algorithm.DiffAlgorithmFactory
            public <T> DiffAlgorithmI<T> create(BiPredicate<T, T> biPredicate) {
                return new MeyersDiffWithLinearSpace(biPredicate);
            }
        };
    }
}
