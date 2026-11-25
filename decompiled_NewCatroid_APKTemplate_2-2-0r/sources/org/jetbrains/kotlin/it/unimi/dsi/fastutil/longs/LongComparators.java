package org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs;

import java.io.Serializable;
import java.util.Comparator;

/* JADX INFO: loaded from: classes5.dex */
public final class LongComparators {
    public static final LongComparator NATURAL_COMPARATOR = new NaturalImplicitComparator();
    public static final LongComparator OPPOSITE_COMPARATOR = new OppositeImplicitComparator();

    protected static class NaturalImplicitComparator implements LongComparator, Serializable {
        private static final long serialVersionUID = 1;

        protected NaturalImplicitComparator() {
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.LongComparator
        public final int compare(long j, long j2) {
            return Long.compare(j, j2);
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.LongComparator, java.util.Comparator
        public Comparator<Long> reversed() {
            return LongComparators.OPPOSITE_COMPARATOR;
        }

        private Object readResolve() {
            return LongComparators.NATURAL_COMPARATOR;
        }
    }

    protected static class OppositeImplicitComparator implements LongComparator, Serializable {
        private static final long serialVersionUID = 1;

        protected OppositeImplicitComparator() {
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.LongComparator
        public final int compare(long j, long j2) {
            return -Long.compare(j, j2);
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.LongComparator, java.util.Comparator
        public Comparator<Long> reversed() {
            return LongComparators.NATURAL_COMPARATOR;
        }

        private Object readResolve() {
            return LongComparators.OPPOSITE_COMPARATOR;
        }
    }

    protected static class OppositeComparator implements LongComparator, Serializable {
        private static final long serialVersionUID = 1;
        final LongComparator comparator;

        protected OppositeComparator(LongComparator longComparator) {
            this.comparator = longComparator;
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.LongComparator
        public final int compare(long j, long j2) {
            return this.comparator.compare(j2, j);
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.LongComparator, java.util.Comparator
        public final Comparator<Long> reversed() {
            return this.comparator;
        }
    }

    public static LongComparator oppositeComparator(LongComparator longComparator) {
        return longComparator instanceof OppositeComparator ? ((OppositeComparator) longComparator).comparator : new OppositeComparator(longComparator);
    }

    public static LongComparator asLongComparator(final Comparator<? super Long> comparator) {
        if (comparator == null || (comparator instanceof LongComparator)) {
            return (LongComparator) comparator;
        }
        return new LongComparator() { // from class: org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.LongComparators.1
            @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.LongComparator
            public int compare(long j, long j2) {
                return comparator.compare(Long.valueOf(j), Long.valueOf(j2));
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.LongComparator, java.util.Comparator
            public int compare(Long l, Long l2) {
                return comparator.compare(l, l2);
            }
        };
    }
}
