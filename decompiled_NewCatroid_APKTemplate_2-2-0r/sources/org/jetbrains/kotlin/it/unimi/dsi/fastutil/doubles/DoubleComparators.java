package org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles;

import java.io.Serializable;
import java.util.Comparator;

/* JADX INFO: loaded from: classes5.dex */
public final class DoubleComparators {
    public static final DoubleComparator NATURAL_COMPARATOR = new NaturalImplicitComparator();
    public static final DoubleComparator OPPOSITE_COMPARATOR = new OppositeImplicitComparator();

    protected static class NaturalImplicitComparator implements DoubleComparator, Serializable {
        private static final long serialVersionUID = 1;

        protected NaturalImplicitComparator() {
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.DoubleComparator
        public final int compare(double d, double d2) {
            return Double.compare(d, d2);
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.DoubleComparator, java.util.Comparator
        public Comparator<Double> reversed() {
            return DoubleComparators.OPPOSITE_COMPARATOR;
        }

        private Object readResolve() {
            return DoubleComparators.NATURAL_COMPARATOR;
        }
    }

    protected static class OppositeImplicitComparator implements DoubleComparator, Serializable {
        private static final long serialVersionUID = 1;

        protected OppositeImplicitComparator() {
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.DoubleComparator
        public final int compare(double d, double d2) {
            return -Double.compare(d, d2);
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.DoubleComparator, java.util.Comparator
        public Comparator<Double> reversed() {
            return DoubleComparators.NATURAL_COMPARATOR;
        }

        private Object readResolve() {
            return DoubleComparators.OPPOSITE_COMPARATOR;
        }
    }

    protected static class OppositeComparator implements DoubleComparator, Serializable {
        private static final long serialVersionUID = 1;
        final DoubleComparator comparator;

        protected OppositeComparator(DoubleComparator doubleComparator) {
            this.comparator = doubleComparator;
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.DoubleComparator
        public final int compare(double d, double d2) {
            return this.comparator.compare(d2, d);
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.DoubleComparator, java.util.Comparator
        public final Comparator<Double> reversed() {
            return this.comparator;
        }
    }

    public static DoubleComparator oppositeComparator(DoubleComparator doubleComparator) {
        return doubleComparator instanceof OppositeComparator ? ((OppositeComparator) doubleComparator).comparator : new OppositeComparator(doubleComparator);
    }

    public static DoubleComparator asDoubleComparator(final Comparator<? super Double> comparator) {
        if (comparator == null || (comparator instanceof DoubleComparator)) {
            return (DoubleComparator) comparator;
        }
        return new DoubleComparator() { // from class: org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.DoubleComparators.1
            @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.DoubleComparator
            public int compare(double d, double d2) {
                return comparator.compare(Double.valueOf(d), Double.valueOf(d2));
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.DoubleComparator, java.util.Comparator
            public int compare(Double d, Double d2) {
                return comparator.compare(d, d2);
            }
        };
    }
}
