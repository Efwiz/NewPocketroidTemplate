package com.opencsv.bean;

import com.beust.jcommander.Parameters;
import com.opencsv.ICSVParser;
import com.opencsv.exceptions.CsvBadConverterException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Locale;
import java.util.NoSuchElementException;
import java.util.ResourceBundle;
import java.util.function.Predicate;
import org.apache.commons.lang3.Range;
import org.apache.commons.lang3.StringUtils;

/* JADX INFO: loaded from: classes10.dex */
public class PositionToBeanField<T> extends AbstractFieldMapEntry<String, Integer, T> implements Iterable<FieldMapByPositionEntry<T>> {
    private final String initializer;
    private final List<Range<Integer>> ranges;

    public PositionToBeanField(String str, int i, BeanField<T, Integer> beanField, Locale locale) {
        Range<Integer> rangeIs;
        Range<Integer> rangeBetween;
        super(beanField, locale);
        this.initializer = str;
        this.ranges = new LinkedList();
        if (StringUtils.isBlank(str)) {
            throw new CsvBadConverterException(BeanFieldJoin.class, String.format(ResourceBundle.getBundle(ICSVParser.DEFAULT_BUNDLE_NAME, this.errorLocale).getString("invalid.range.definition"), str));
        }
        String[] strArrSplit = str.split(",");
        try {
            int length = strArrSplit.length;
            int i2 = 0;
            int i3 = 0;
            while (i3 < length) {
                String str2 = strArrSplit[i3];
                if (StringUtils.isNotEmpty(str2)) {
                    if (str2.contains(Parameters.DEFAULT_OPTION_PREFIXES)) {
                        String[] strArrSplit2 = str2.split(Parameters.DEFAULT_OPTION_PREFIXES, 2);
                        Integer numValueOf = StringUtils.isEmpty(strArrSplit2[i2]) ? Integer.valueOf(i2) : Integer.valueOf(strArrSplit2[i2].trim());
                        Integer numValueOf2 = Integer.valueOf(i);
                        if (strArrSplit2.length == 2 && StringUtils.isNotEmpty(strArrSplit2[1])) {
                            numValueOf2 = Integer.valueOf(strArrSplit2[1].trim());
                        }
                        rangeIs = Range.between(numValueOf, numValueOf2.intValue() >= i ? numValueOf.intValue() >= i ? numValueOf : Integer.valueOf(i) : numValueOf2);
                    } else {
                        rangeIs = Range.is(Integer.valueOf(str2));
                    }
                    ListIterator<Range<Integer>> listIterator = this.ranges.listIterator();
                    int i4 = i2;
                    while (listIterator.hasNext() && i4 == 0) {
                        Range<Integer> next = listIterator.next();
                        if (next.containsRange(rangeIs)) {
                            i4 = 1;
                        } else {
                            if (next.isOverlappedBy(rangeIs)) {
                                rangeBetween = Range.between(Integer.valueOf(Math.min(next.getMinimum().intValue(), rangeIs.getMinimum().intValue())), Integer.valueOf(Math.max(next.getMaximum().intValue(), rangeIs.getMaximum().intValue())));
                                listIterator.remove();
                            } else if (next.getMaximum().intValue() + 1 == rangeIs.getMinimum().intValue()) {
                                rangeBetween = Range.between(next.getMinimum(), rangeIs.getMaximum());
                            } else if (rangeIs.getMaximum().intValue() + 1 == next.getMinimum().intValue()) {
                                rangeBetween = Range.between(rangeIs.getMinimum(), next.getMaximum());
                            }
                            rangeIs = rangeBetween;
                        }
                    }
                    if (i4 == 0) {
                        this.ranges.add(rangeIs);
                    }
                }
                i3++;
                i2 = 0;
            }
        } catch (NumberFormatException e) {
            CsvBadConverterException csvBadConverterException = new CsvBadConverterException(BeanFieldJoin.class, String.format(ResourceBundle.getBundle(ICSVParser.DEFAULT_BUNDLE_NAME, this.errorLocale).getString("invalid.range.definition"), str));
            csvBadConverterException.initCause(e);
            throw csvBadConverterException;
        }
    }

    public void attenuateRanges(int i) {
        ListIterator<Range<Integer>> listIterator = this.ranges.listIterator();
        while (listIterator.hasNext()) {
            Range<Integer> next = listIterator.next();
            if (next.getMaximum().intValue() > i) {
                if (next.getMinimum().intValue() > i) {
                    listIterator.set(Range.is(next.getMinimum()));
                } else {
                    listIterator.set(Range.between(next.getMinimum(), Integer.valueOf(i)));
                }
            }
        }
    }

    @Override // com.opencsv.bean.ComplexFieldMapEntry
    public boolean contains(final Integer num) {
        return this.ranges.stream().anyMatch(new Predicate() { // from class: com.opencsv.bean.PositionToBeanField$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((Range) obj).contains(num);
            }
        });
    }

    @Override // com.opencsv.bean.ComplexFieldMapEntry
    public String getInitializer() {
        return this.initializer;
    }

    @Override // java.lang.Iterable
    public Iterator<FieldMapByPositionEntry<T>> iterator() {
        return new PositionIterator();
    }

    private class PositionIterator implements Iterator<FieldMapByPositionEntry<T>> {
        private Range<Integer> currentRange;
        private int position;
        private ListIterator<Range<Integer>> rangeIterator;

        PositionIterator() {
            if (!PositionToBeanField.this.ranges.isEmpty()) {
                ListIterator<Range<Integer>> listIterator = PositionToBeanField.this.ranges.listIterator();
                this.rangeIterator = listIterator;
                Range<Integer> next = listIterator.next();
                this.currentRange = next;
                this.position = next.getMinimum().intValue();
                return;
            }
            this.position = -1;
        }

        @Override // java.util.Iterator
        public boolean hasNext() {
            return this.position != -1;
        }

        @Override // java.util.Iterator
        public FieldMapByPositionEntry<T> next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            FieldMapByPositionEntry<T> fieldMapByPositionEntry = new FieldMapByPositionEntry<>(this.position, PositionToBeanField.this.field);
            if (this.position == this.currentRange.getMaximum().intValue() || Integer.MAX_VALUE == this.currentRange.getMaximum().intValue()) {
                if (!this.rangeIterator.hasNext()) {
                    this.position = -1;
                } else {
                    Range<Integer> next = this.rangeIterator.next();
                    this.currentRange = next;
                    this.position = next.getMinimum().intValue();
                }
            } else {
                this.position++;
            }
            return fieldMapByPositionEntry;
        }

        @Override // java.util.Iterator
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }
}
