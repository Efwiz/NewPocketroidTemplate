package org.jetbrains.kotlin.it.unimi.dsi.fastutil.chars;

import java.util.Comparator;

/* JADX INFO: loaded from: classes5.dex */
@FunctionalInterface
public interface CharComparator extends Comparator<Character> {
    int compare(char c, char c2);

    @Override // java.util.Comparator
    default Comparator<Character> reversed() {
        return CharComparators.oppositeComparator(this);
    }

    @Override // java.util.Comparator
    @Deprecated
    default int compare(Character ch, Character ch2) {
        return compare(ch.charValue(), ch2.charValue());
    }

    default CharComparator thenComparing(CharComparator charComparator) {
        return new CharComparator$$ExternalSyntheticLambda0(this, charComparator);
    }

    static /* synthetic */ int lambda$thenComparing$2b1ecd07$1(CharComparator _this, CharComparator charComparator, char c, char c2) {
        int iCompare = _this.compare(c, c2);
        return iCompare == 0 ? charComparator.compare(c, c2) : iCompare;
    }

    @Override // java.util.Comparator
    default Comparator<Character> thenComparing(Comparator<? super Character> comparator) {
        return comparator instanceof CharComparator ? thenComparing((CharComparator) comparator) : super.thenComparing(comparator);
    }
}
