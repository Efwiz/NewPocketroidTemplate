package org.jetbrains.kotlin.it.unimi.dsi.fastutil.shorts;

import java.io.Serializable;
import java.util.Comparator;

/* JADX INFO: loaded from: classes5.dex */
public final class ShortComparators {
    public static final ShortComparator NATURAL_COMPARATOR = new NaturalImplicitComparator();
    public static final ShortComparator OPPOSITE_COMPARATOR = new OppositeImplicitComparator();

    protected static class NaturalImplicitComparator implements ShortComparator, Serializable {
        private static final long serialVersionUID = 1;

        protected NaturalImplicitComparator() {
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.shorts.ShortComparator
        public final int compare(short s, short s2) {
            return Short.compare(s, s2);
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.shorts.ShortComparator, java.util.Comparator
        public Comparator<Short> reversed() {
            return ShortComparators.OPPOSITE_COMPARATOR;
        }

        private Object readResolve() {
            return ShortComparators.NATURAL_COMPARATOR;
        }
    }

    protected static class OppositeImplicitComparator implements ShortComparator, Serializable {
        private static final long serialVersionUID = 1;

        protected OppositeImplicitComparator() {
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.shorts.ShortComparator
        public final int compare(short s, short s2) {
            return -Short.compare(s, s2);
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.shorts.ShortComparator, java.util.Comparator
        public Comparator<Short> reversed() {
            return ShortComparators.NATURAL_COMPARATOR;
        }

        private Object readResolve() {
            return ShortComparators.OPPOSITE_COMPARATOR;
        }
    }

    protected static class OppositeComparator implements ShortComparator, Serializable {
        private static final long serialVersionUID = 1;
        final ShortComparator comparator;

        protected OppositeComparator(ShortComparator shortComparator) {
            this.comparator = shortComparator;
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.shorts.ShortComparator
        public final int compare(short s, short s2) {
            return this.comparator.compare(s2, s);
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.shorts.ShortComparator, java.util.Comparator
        public final Comparator<Short> reversed() {
            return this.comparator;
        }
    }

    public static ShortComparator oppositeComparator(ShortComparator shortComparator) {
        return shortComparator instanceof OppositeComparator ? ((OppositeComparator) shortComparator).comparator : new OppositeComparator(shortComparator);
    }

    public static ShortComparator asShortComparator(final Comparator<? super Short> comparator) {
        if (comparator == null || (comparator instanceof ShortComparator)) {
            return (ShortComparator) comparator;
        }
        return new ShortComparator() { // from class: org.jetbrains.kotlin.it.unimi.dsi.fastutil.shorts.ShortComparators.1
            @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.shorts.ShortComparator
            public int compare(short s, short s2) {
                return comparator.compare(Short.valueOf(s), Short.valueOf(s2));
            }

            @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.shorts.ShortComparator, java.util.Comparator
            public int compare(Short sh, Short sh2) {
                return comparator.compare(sh, sh2);
            }
        };
    }
}
