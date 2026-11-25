package org.jetbrains.kotlin.it.unimi.dsi.fastutil.bytes;

import java.io.Serializable;
import java.util.Comparator;

/* JADX INFO: loaded from: classes5.dex */
public final class ByteComparators {
    public static final ByteComparator NATURAL_COMPARATOR = new NaturalImplicitComparator();
    public static final ByteComparator OPPOSITE_COMPARATOR = new OppositeImplicitComparator();

    protected static class NaturalImplicitComparator implements ByteComparator, Serializable {
        private static final long serialVersionUID = 1;

        protected NaturalImplicitComparator() {
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.bytes.ByteComparator
        public final int compare(byte b, byte b2) {
            return Byte.compare(b, b2);
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.bytes.ByteComparator, java.util.Comparator
        public Comparator<Byte> reversed() {
            return ByteComparators.OPPOSITE_COMPARATOR;
        }

        private Object readResolve() {
            return ByteComparators.NATURAL_COMPARATOR;
        }
    }

    protected static class OppositeImplicitComparator implements ByteComparator, Serializable {
        private static final long serialVersionUID = 1;

        protected OppositeImplicitComparator() {
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.bytes.ByteComparator
        public final int compare(byte b, byte b2) {
            return -Byte.compare(b, b2);
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.bytes.ByteComparator, java.util.Comparator
        public Comparator<Byte> reversed() {
            return ByteComparators.NATURAL_COMPARATOR;
        }

        private Object readResolve() {
            return ByteComparators.OPPOSITE_COMPARATOR;
        }
    }

    protected static class OppositeComparator implements ByteComparator, Serializable {
        private static final long serialVersionUID = 1;
        final ByteComparator comparator;

        protected OppositeComparator(ByteComparator byteComparator) {
            this.comparator = byteComparator;
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.bytes.ByteComparator
        public final int compare(byte b, byte b2) {
            return this.comparator.compare(b2, b);
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.bytes.ByteComparator, java.util.Comparator
        public final Comparator<Byte> reversed() {
            return this.comparator;
        }
    }

    public static ByteComparator oppositeComparator(ByteComparator byteComparator) {
        return byteComparator instanceof OppositeComparator ? ((OppositeComparator) byteComparator).comparator : new OppositeComparator(byteComparator);
    }

    public static ByteComparator asByteComparator(final Comparator<? super Byte> comparator) {
        if (comparator == null || (comparator instanceof ByteComparator)) {
            return (ByteComparator) comparator;
        }
        return new ByteComparator() { // from class: org.jetbrains.kotlin.it.unimi.dsi.fastutil.bytes.ByteComparators.1
            @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.bytes.ByteComparator
            public int compare(byte b, byte b2) {
                return comparator.compare(Byte.valueOf(b), Byte.valueOf(b2));
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.bytes.ByteComparator, java.util.Comparator
            public int compare(Byte b, Byte b2) {
                return comparator.compare(b, b2);
            }
        };
    }
}
