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

/* JADX INFO: loaded from: classes.dex */
public final class MeyersDiff<T> implements DiffAlgorithmI<T> {
    private final BiPredicate<T, T> equalizer;

    public MeyersDiff() {
        this.equalizer = new BiPredicate() { // from class: com.github.difflib.algorithm.myers.MeyersDiff$$ExternalSyntheticLambda0
            @Override // java.util.function.BiPredicate
            public final boolean test(Object obj, Object obj2) {
                return obj.equals(obj2);
            }
        };
    }

    public MeyersDiff(BiPredicate<T, T> biPredicate) {
        Objects.requireNonNull(biPredicate, "equalizer must not be null");
        this.equalizer = biPredicate;
    }

    @Override // com.github.difflib.algorithm.DiffAlgorithmI
    public List<Change> computeDiff(List<T> list, List<T> list2, DiffAlgorithmListener diffAlgorithmListener) {
        Objects.requireNonNull(list, "source list must not be null");
        Objects.requireNonNull(list2, "target list must not be null");
        if (diffAlgorithmListener != null) {
            diffAlgorithmListener.diffStart();
        }
        List<Change> listBuildRevision = buildRevision(buildPath(list, list2, diffAlgorithmListener), list, list2);
        if (diffAlgorithmListener != null) {
            diffAlgorithmListener.diffEnd();
        }
        return listBuildRevision;
    }

    private PathNode buildPath(List<T> list, List<T> list2, DiffAlgorithmListener diffAlgorithmListener) {
        int i;
        PathNode pathNode;
        int i2;
        List<T> list3 = list;
        Objects.requireNonNull(list3, "original sequence is null");
        Objects.requireNonNull(list2, "revised sequence is null");
        int size = list.size();
        int size2 = list2.size();
        int i3 = size + size2 + 1;
        int i4 = (i3 * 2) + 1;
        int i5 = i4 / 2;
        PathNode[] pathNodeArr = new PathNode[i4];
        pathNodeArr[i5 + 1] = new PathNode(0, -1, true, true, null);
        int i6 = 0;
        while (i6 < i3) {
            if (diffAlgorithmListener != 0) {
                diffAlgorithmListener.diffStep(i6, i3);
            }
            int i7 = -i6;
            int i8 = i7;
            while (i8 <= i6) {
                int i9 = i5 + i8;
                int i10 = i9 + 1;
                int i11 = i9 - 1;
                if (i8 == i7 || (i8 != i6 && pathNodeArr[i11].i < pathNodeArr[i10].i)) {
                    i = pathNodeArr[i10].i;
                    pathNode = pathNodeArr[i10];
                } else {
                    i = pathNodeArr[i11].i + 1;
                    pathNode = pathNodeArr[i11];
                }
                PathNode pathNode2 = pathNode;
                pathNodeArr[i11] = null;
                int i12 = i - i8;
                PathNode pathNode3 = new PathNode(i, i12, false, false, pathNode2);
                while (true) {
                    if (i < size && i12 < size2) {
                        i2 = i3;
                        if (!this.equalizer.test(list3.get(i), list2.get(i12))) {
                            break;
                        }
                        i++;
                        i12++;
                        list3 = list;
                        i3 = i2;
                    } else {
                        i2 = i3;
                        break;
                    }
                }
                if (i != pathNode3.i) {
                    pathNode3 = new PathNode(i, i12, true, false, pathNode3);
                }
                pathNodeArr[i9] = pathNode3;
                if (i >= size && i12 >= size2) {
                    return pathNode3;
                }
                i8 += 2;
                list3 = list;
                i3 = i2;
            }
            pathNodeArr[(i5 + i6) - 1] = null;
            i6++;
            list3 = list;
        }
        throw new IllegalStateException("could not find a diff path");
    }

    private List<Change> buildRevision(PathNode pathNode, List<T> list, List<T> list2) {
        Objects.requireNonNull(pathNode, "path is null");
        Objects.requireNonNull(list, "original sequence is null");
        Objects.requireNonNull(list2, "revised sequence is null");
        ArrayList arrayList = new ArrayList();
        if (pathNode.isSnake()) {
            pathNode = pathNode.prev;
        }
        while (pathNode != null && pathNode.prev != null && pathNode.prev.j >= 0) {
            if (pathNode.isSnake()) {
                throw new IllegalStateException("bad diffpath: found snake when looking for diff");
            }
            int i = pathNode.i;
            int i2 = pathNode.j;
            pathNode = pathNode.prev;
            int i3 = pathNode.i;
            int i4 = pathNode.j;
            if (i3 == i && i4 != i2) {
                arrayList.add(new Change(DeltaType.INSERT, i3, i, i4, i2));
            } else if (i3 != i && i4 == i2) {
                arrayList.add(new Change(DeltaType.DELETE, i3, i, i4, i2));
            } else {
                arrayList.add(new Change(DeltaType.CHANGE, i3, i, i4, i2));
            }
            if (pathNode.isSnake()) {
                pathNode = pathNode.prev;
            }
        }
        return arrayList;
    }

    public static DiffAlgorithmFactory factory() {
        return new DiffAlgorithmFactory() { // from class: com.github.difflib.algorithm.myers.MeyersDiff.1
            @Override // com.github.difflib.algorithm.DiffAlgorithmFactory
            public <T> DiffAlgorithmI<T> create() {
                return new MeyersDiff();
            }

            @Override // com.github.difflib.algorithm.DiffAlgorithmFactory
            public <T> DiffAlgorithmI<T> create(BiPredicate<T, T> biPredicate) {
                return new MeyersDiff(biPredicate);
            }
        };
    }
}
