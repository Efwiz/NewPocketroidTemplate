package com.github.difflib;

import com.github.difflib.algorithm.DiffAlgorithmFactory;
import com.github.difflib.algorithm.DiffAlgorithmI;
import com.github.difflib.algorithm.DiffAlgorithmListener;
import com.github.difflib.algorithm.myers.MeyersDiff;
import com.github.difflib.patch.AbstractDelta;
import com.github.difflib.patch.Patch;
import com.github.difflib.patch.PatchFailedException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.function.BiPredicate;

/* JADX INFO: loaded from: classes.dex */
public final class DiffUtils {
    static DiffAlgorithmFactory DEFAULT_DIFF = MeyersDiff.factory();

    public static void withDefaultDiffAlgorithmFactory(DiffAlgorithmFactory diffAlgorithmFactory) {
        DEFAULT_DIFF = diffAlgorithmFactory;
    }

    public static <T> Patch<T> diff(List<T> list, List<T> list2, DiffAlgorithmListener diffAlgorithmListener) {
        return diff(list, list2, DEFAULT_DIFF.create(), diffAlgorithmListener);
    }

    public static <T> Patch<T> diff(List<T> list, List<T> list2) {
        return diff(list, list2, DEFAULT_DIFF.create(), null);
    }

    public static <T> Patch<T> diff(List<T> list, List<T> list2, boolean z) {
        return diff(list, list2, DEFAULT_DIFF.create(), null, z);
    }

    public static Patch<String> diff(String str, String str2, DiffAlgorithmListener diffAlgorithmListener) {
        return diff(Arrays.asList(str.split("\n")), Arrays.asList(str2.split("\n")), diffAlgorithmListener);
    }

    public static <T> Patch<T> diff(List<T> list, List<T> list2, BiPredicate<T, T> biPredicate) {
        if (biPredicate != null) {
            return diff(list, list2, DEFAULT_DIFF.create(biPredicate));
        }
        return diff(list, list2, new MeyersDiff());
    }

    public static <T> Patch<T> diff(List<T> list, List<T> list2, DiffAlgorithmI<T> diffAlgorithmI, DiffAlgorithmListener diffAlgorithmListener) {
        return diff(list, list2, diffAlgorithmI, diffAlgorithmListener, false);
    }

    public static <T> Patch<T> diff(List<T> list, List<T> list2, DiffAlgorithmI<T> diffAlgorithmI, DiffAlgorithmListener diffAlgorithmListener, boolean z) {
        Objects.requireNonNull(list, "original must not be null");
        Objects.requireNonNull(list2, "revised must not be null");
        Objects.requireNonNull(diffAlgorithmI, "algorithm must not be null");
        return Patch.generate(list, list2, diffAlgorithmI.computeDiff(list, list2, diffAlgorithmListener), z);
    }

    public static <T> Patch<T> diff(List<T> list, List<T> list2, DiffAlgorithmI<T> diffAlgorithmI) {
        return diff(list, list2, diffAlgorithmI, null);
    }

    public static Patch<String> diffInline(String str, String str2) {
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        for (char c : str.toCharArray()) {
            arrayList.add(Character.valueOf(c).toString());
        }
        for (char c2 : str2.toCharArray()) {
            arrayList2.add(Character.valueOf(c2).toString());
        }
        Patch<String> patchDiff = diff(arrayList, arrayList2);
        for (AbstractDelta<String> abstractDelta : patchDiff.getDeltas()) {
            abstractDelta.getSource().setLines(compressLines(abstractDelta.getSource().getLines(), ""));
            abstractDelta.getTarget().setLines(compressLines(abstractDelta.getTarget().getLines(), ""));
        }
        return patchDiff;
    }

    private static List<String> compressLines(List<String> list, String str) {
        if (list.isEmpty()) {
            return Collections.emptyList();
        }
        return Collections.singletonList(String.join(str, list));
    }

    public static <T> List<T> patch(List<T> list, Patch<T> patch) throws PatchFailedException {
        return patch.applyTo(list);
    }

    public static <T> List<T> unpatch(List<T> list, Patch<T> patch) {
        return patch.restore(list);
    }

    private DiffUtils() {
    }
}
