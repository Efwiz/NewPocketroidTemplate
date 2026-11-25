package org.jetbrains.kotlin.it.unimi.dsi.fastutil.floats;

import java.io.Serializable;
import java.util.Comparator;

/* JADX INFO: loaded from: classes5.dex */
public final class FloatComparators {
    public static final FloatComparator NATURAL_COMPARATOR = new NaturalImplicitComparator();
    public static final FloatComparator OPPOSITE_COMPARATOR = new OppositeImplicitComparator();

    protected static class NaturalImplicitComparator implements FloatComparator, Serializable {
        private static final long serialVersionUID = 1;

        protected NaturalImplicitComparator() {
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.floats.FloatComparator
        public final int compare(float f, float f2) {
            return Float.compare(f, f2);
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.floats.FloatComparator, java.util.Comparator
        public Comparator<Float> reversed() {
            return FloatComparators.OPPOSITE_COMPARATOR;
        }

        private Object readResolve() {
            return FloatComparators.NATURAL_COMPARATOR;
        }
    }

    protected static class OppositeImplicitComparator implements FloatComparator, Serializable {
        private static final long serialVersionUID = 1;

        protected OppositeImplicitComparator() {
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.floats.FloatComparator
        public final int compare(float f, float f2) {
            return -Float.compare(f, f2);
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.floats.FloatComparator, java.util.Comparator
        public Comparator<Float> reversed() {
            return FloatComparators.NATURAL_COMPARATOR;
        }

        private Object readResolve() {
            return FloatComparators.OPPOSITE_COMPARATOR;
        }
    }

    protected static class OppositeComparator implements FloatComparator, Serializable {
        private static final long serialVersionUID = 1;
        final FloatComparator comparator;

        protected OppositeComparator(FloatComparator floatComparator) {
            this.comparator = floatComparator;
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.floats.FloatComparator
        public final int compare(float f, float f2) {
            return this.comparator.compare(f2, f);
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.floats.FloatComparator, java.util.Comparator
        public final Comparator<Float> reversed() {
            return this.comparator;
        }
    }

    public static FloatComparator oppositeComparator(FloatComparator floatComparator) {
        return floatComparator instanceof OppositeComparator ? ((OppositeComparator) floatComparator).comparator : new OppositeComparator(floatComparator);
    }

    public static FloatComparator asFloatComparator(final Comparator<? super Float> comparator) {
        if (comparator == null || (comparator instanceof FloatComparator)) {
            return (FloatComparator) comparator;
        }
        return new FloatComparator() { // from class: org.jetbrains.kotlin.it.unimi.dsi.fastutil.floats.FloatComparators.1
            @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.floats.FloatComparator
            public int compare(float f, float f2) {
                return comparator.compare(Float.valueOf(f), Float.valueOf(f2));
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.floats.FloatComparator, java.util.Comparator
            public int compare(Float f, Float f2) {
                return comparator.compare(f, f2);
            }
        };
    }
}
