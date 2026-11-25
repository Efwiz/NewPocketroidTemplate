package org.jetbrains.kotlin.it.unimi.dsi.fastutil.booleans;

import java.io.Serializable;
import java.util.Comparator;

/* JADX INFO: loaded from: classes5.dex */
public final class BooleanComparators {
    public static final BooleanComparator NATURAL_COMPARATOR = new NaturalImplicitComparator();
    public static final BooleanComparator OPPOSITE_COMPARATOR = new OppositeImplicitComparator();

    protected static class NaturalImplicitComparator implements BooleanComparator, Serializable {
        private static final long serialVersionUID = 1;

        protected NaturalImplicitComparator() {
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.booleans.BooleanComparator
        public final int compare(boolean z, boolean z2) {
            return Boolean.compare(z, z2);
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.booleans.BooleanComparator, java.util.Comparator
        public Comparator<Boolean> reversed() {
            return BooleanComparators.OPPOSITE_COMPARATOR;
        }

        private Object readResolve() {
            return BooleanComparators.NATURAL_COMPARATOR;
        }
    }

    protected static class OppositeImplicitComparator implements BooleanComparator, Serializable {
        private static final long serialVersionUID = 1;

        protected OppositeImplicitComparator() {
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.booleans.BooleanComparator
        public final int compare(boolean z, boolean z2) {
            return -Boolean.compare(z, z2);
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.booleans.BooleanComparator, java.util.Comparator
        public Comparator<Boolean> reversed() {
            return BooleanComparators.NATURAL_COMPARATOR;
        }

        private Object readResolve() {
            return BooleanComparators.OPPOSITE_COMPARATOR;
        }
    }

    protected static class OppositeComparator implements BooleanComparator, Serializable {
        private static final long serialVersionUID = 1;
        final BooleanComparator comparator;

        protected OppositeComparator(BooleanComparator booleanComparator) {
            this.comparator = booleanComparator;
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.booleans.BooleanComparator
        public final int compare(boolean z, boolean z2) {
            return this.comparator.compare(z2, z);
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.booleans.BooleanComparator, java.util.Comparator
        public final Comparator<Boolean> reversed() {
            return this.comparator;
        }
    }

    public static BooleanComparator oppositeComparator(BooleanComparator booleanComparator) {
        return booleanComparator instanceof OppositeComparator ? ((OppositeComparator) booleanComparator).comparator : new OppositeComparator(booleanComparator);
    }

    public static BooleanComparator asBooleanComparator(final Comparator<? super Boolean> comparator) {
        if (comparator == null || (comparator instanceof BooleanComparator)) {
            return (BooleanComparator) comparator;
        }
        return new BooleanComparator() { // from class: org.jetbrains.kotlin.it.unimi.dsi.fastutil.booleans.BooleanComparators.1
            @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.booleans.BooleanComparator
            public int compare(boolean z, boolean z2) {
                return comparator.compare(Boolean.valueOf(z), Boolean.valueOf(z2));
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.booleans.BooleanComparator, java.util.Comparator
            public int compare(Boolean bool, Boolean bool2) {
                return comparator.compare(bool, bool2);
            }
        };
    }
}
