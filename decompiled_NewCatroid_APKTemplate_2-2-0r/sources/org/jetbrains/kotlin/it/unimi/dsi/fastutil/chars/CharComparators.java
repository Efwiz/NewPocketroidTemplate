package org.jetbrains.kotlin.it.unimi.dsi.fastutil.chars;

import java.io.Serializable;
import java.util.Comparator;

/* JADX INFO: loaded from: classes5.dex */
public final class CharComparators {
    public static final CharComparator NATURAL_COMPARATOR = new NaturalImplicitComparator();
    public static final CharComparator OPPOSITE_COMPARATOR = new OppositeImplicitComparator();

    protected static class NaturalImplicitComparator implements CharComparator, Serializable {
        private static final long serialVersionUID = 1;

        protected NaturalImplicitComparator() {
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.chars.CharComparator
        public final int compare(char c, char c2) {
            return Character.compare(c, c2);
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.chars.CharComparator, java.util.Comparator
        public Comparator<Character> reversed() {
            return CharComparators.OPPOSITE_COMPARATOR;
        }

        private Object readResolve() {
            return CharComparators.NATURAL_COMPARATOR;
        }
    }

    protected static class OppositeImplicitComparator implements CharComparator, Serializable {
        private static final long serialVersionUID = 1;

        protected OppositeImplicitComparator() {
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.chars.CharComparator
        public final int compare(char c, char c2) {
            return -Character.compare(c, c2);
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.chars.CharComparator, java.util.Comparator
        public Comparator<Character> reversed() {
            return CharComparators.NATURAL_COMPARATOR;
        }

        private Object readResolve() {
            return CharComparators.OPPOSITE_COMPARATOR;
        }
    }

    protected static class OppositeComparator implements CharComparator, Serializable {
        private static final long serialVersionUID = 1;
        final CharComparator comparator;

        protected OppositeComparator(CharComparator charComparator) {
            this.comparator = charComparator;
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.chars.CharComparator
        public final int compare(char c, char c2) {
            return this.comparator.compare(c2, c);
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.chars.CharComparator, java.util.Comparator
        public final Comparator<Character> reversed() {
            return this.comparator;
        }
    }

    public static CharComparator oppositeComparator(CharComparator charComparator) {
        return charComparator instanceof OppositeComparator ? ((OppositeComparator) charComparator).comparator : new OppositeComparator(charComparator);
    }
}
