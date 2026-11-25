package com.hivemq.client.internal.util;

/* JADX INFO: loaded from: classes10.dex */
public class Ranges {
    private Range rootRange;

    public Ranges(int i, int i2) {
        this.rootRange = new Range(i, i2 + 1);
    }

    public int getId() {
        if (this.rootRange.start == this.rootRange.end) {
            return -1;
        }
        int i = this.rootRange.start;
        this.rootRange.start++;
        if (this.rootRange.start == this.rootRange.end && this.rootRange.next != null) {
            this.rootRange = this.rootRange.next;
        }
        return i;
    }

    public void returnId(int i) {
        Range range = this.rootRange;
        if (i < range.start - 1) {
            this.rootRange = new Range(i, i + 1, range);
            return;
        }
        Range rangeReturnId = returnId(range, i);
        while (true) {
            Range range2 = rangeReturnId;
            Range range3 = range;
            range = range2;
            if (range == null) {
                return;
            }
            if (i < range.start - 1) {
                range3.next = new Range(i, i + 1, range);
                return;
            }
            rangeReturnId = returnId(range, i);
        }
    }

    private Range returnId(Range range, int i) {
        Range range2 = range.next;
        if (i == range.start - 1) {
            range.start = i;
            return null;
        }
        if (i < range.end) {
            throw new IllegalStateException("The id was already returned. This must not happen and is a bug.");
        }
        if (i != range.end) {
            if (range2 != null) {
                return range2;
            }
            throw new IllegalStateException("The id is greater than maxId. This must not happen and is a bug.");
        }
        if (range2 == null) {
            throw new IllegalStateException("The id is greater than maxId. This must not happen and is a bug.");
        }
        range.end++;
        if (range.end == range2.start) {
            range.end = range2.end;
            range.next = range2.next;
        }
        return null;
    }

    public int resize(int i) {
        Range range = this.rootRange;
        while (true) {
            if (range.end <= i) {
                Range range2 = range.next;
                if (range2 == null) {
                    range.end = i + 1;
                    return 0;
                }
                range = range2;
            } else {
                int i2 = i + 1;
                int i3 = range.start - i2;
                int i4 = i3 >= 0 ? i3 : 0;
                while (range.next != null) {
                    Range range3 = range.next;
                    i4 += range3.start - range.end;
                    range = range3;
                }
                if (i4 == 0) {
                    range.end = i2;
                }
                return i4;
            }
        }
    }

    private static class Range {
        int end;
        Range next;
        int start;

        Range(int i, int i2) {
            this.start = i;
            this.end = i2;
        }

        Range(int i, int i2, Range range) {
            this.start = i;
            this.end = i2;
            this.next = range;
        }
    }
}
