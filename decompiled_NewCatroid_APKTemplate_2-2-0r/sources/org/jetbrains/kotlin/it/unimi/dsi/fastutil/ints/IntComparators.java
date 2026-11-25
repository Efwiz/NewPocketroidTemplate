package org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints;

import java.io.Serializable;
import java.util.Comparator;

/* JADX INFO: loaded from: classes5.dex */
public final class IntComparators {
    public static final IntComparator NATURAL_COMPARATOR = new NaturalImplicitComparator();
    public static final IntComparator OPPOSITE_COMPARATOR = new OppositeImplicitComparator();

    protected static class NaturalImplicitComparator implements IntComparator, Serializable {
        private static final long serialVersionUID = 1;

        protected NaturalImplicitComparator() {
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntComparator
        public final int compare(int i, int i2) {
            return Integer.compare(i, i2);
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntComparator, java.util.Comparator
        public Comparator<Integer> reversed() {
            return IntComparators.OPPOSITE_COMPARATOR;
        }

        private Object readResolve() {
            return IntComparators.NATURAL_COMPARATOR;
        }
    }

    protected static class OppositeImplicitComparator implements IntComparator, Serializable {
        private static final long serialVersionUID = 1;

        protected OppositeImplicitComparator() {
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntComparator
        public final int compare(int i, int i2) {
            return -Integer.compare(i, i2);
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntComparator, java.util.Comparator
        public Comparator<Integer> reversed() {
            return IntComparators.NATURAL_COMPARATOR;
        }

        private Object readResolve() {
            return IntComparators.OPPOSITE_COMPARATOR;
        }
    }

    protected static class OppositeComparator implements IntComparator, Serializable {
        private static final long serialVersionUID = 1;
        final IntComparator comparator;

        protected OppositeComparator(IntComparator intComparator) {
            this.comparator = intComparator;
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntComparator
        public final int compare(int i, int i2) {
            return this.comparator.compare(i2, i);
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntComparator, java.util.Comparator
        public final Comparator<Integer> reversed() {
            return this.comparator;
        }
    }

    public static IntComparator oppositeComparator(IntComparator intComparator) {
        return intComparator instanceof OppositeComparator ? ((OppositeComparator) intComparator).comparator : new OppositeComparator(intComparator);
    }

    public static IntComparator asIntComparator(final Comparator<? super Integer> comparator) {
        if (comparator == null || (comparator instanceof IntComparator)) {
            return (IntComparator) comparator;
        }
        return new IntComparator() { // from class: org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntComparators.1
            @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntComparator
            public int compare(int i, int i2) {
                return comparator.compare(Integer.valueOf(i), Integer.valueOf(i2));
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntComparator, java.util.Comparator
            public int compare(Integer num, Integer num2) {
                return comparator.compare(num, num2);
            }
        };
    }
}
