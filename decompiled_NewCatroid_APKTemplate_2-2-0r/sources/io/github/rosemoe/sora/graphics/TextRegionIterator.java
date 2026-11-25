package io.github.rosemoe.sora.graphics;

import io.github.rosemoe.sora.lang.styling.Span;
import io.github.rosemoe.sora.lang.styling.SpanFactory;
import io.github.rosemoe.sora.util.RegionIterator;
import java.util.List;

/* JADX INFO: loaded from: classes11.dex */
class TextRegionIterator extends RegionIterator {
    private final List<Span> spans;

    public TextRegionIterator(int i, List<Span> list, List<Integer> list2) {
        super(i, new SpansPoints(list), new SoftBreaksPoints(list2));
        this.spans = list;
    }

    public void requireStartOffset(int i) {
        if (i > getMax()) {
            throw new IllegalArgumentException();
        }
        if (getStartIndex() != 0) {
            throw new IllegalStateException();
        }
        do {
            nextRegion();
            if (getEndIndex() > i) {
                break;
            }
        } while (hasNextRegion());
        this.startIndex = i;
    }

    public Span getSpan() {
        int regionSourcePointer = getRegionSourcePointer(0) - 1;
        if (regionSourcePointer < 0) {
            return SpanFactory.obtain(0, 5L);
        }
        return this.spans.get(regionSourcePointer);
    }

    public int getSpanStart() {
        return getPointerValue(0, getRegionSourcePointer(0) - 1);
    }

    public int getSpanEnd() {
        return getPointerValue(0, getRegionSourcePointer(0));
    }

    private static class SpansPoints implements RegionIterator.RegionProvider {
        private final List<Span> spans;

        public SpansPoints(List<Span> list) {
            this.spans = list;
        }

        @Override // io.github.rosemoe.sora.util.RegionIterator.RegionProvider
        public int getPointCount() {
            List<Span> list = this.spans;
            if (list == null) {
                return 0;
            }
            return list.size();
        }

        @Override // io.github.rosemoe.sora.util.RegionIterator.RegionProvider
        public int getPointAt(int i) {
            return this.spans.get(i).getColumn();
        }
    }

    private static class SoftBreaksPoints implements RegionIterator.RegionProvider {
        private final List<Integer> points;

        public SoftBreaksPoints(List<Integer> list) {
            this.points = list;
        }

        @Override // io.github.rosemoe.sora.util.RegionIterator.RegionProvider
        public int getPointCount() {
            List<Integer> list = this.points;
            if (list == null) {
                return 0;
            }
            return list.size();
        }

        @Override // io.github.rosemoe.sora.util.RegionIterator.RegionProvider
        public int getPointAt(int i) {
            return this.points.get(i).intValue();
        }
    }
}
