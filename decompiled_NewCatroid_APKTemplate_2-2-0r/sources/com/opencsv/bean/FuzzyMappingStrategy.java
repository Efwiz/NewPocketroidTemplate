package com.opencsv.bean;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.apache.commons.collections4.ListValuedMap;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.text.similarity.LevenshteinDistance;

/* JADX INFO: loaded from: classes10.dex */
public class FuzzyMappingStrategy<T> extends HeaderColumnNameMappingStrategy<T> {
    @Override // com.opencsv.bean.HeaderNameBaseMappingStrategy, com.opencsv.bean.AbstractMappingStrategy
    protected void loadUnadornedFieldMap(ListValuedMap<Class<?>, Field> listValuedMap) {
    }

    @Override // com.opencsv.bean.HeaderNameBaseMappingStrategy, com.opencsv.bean.MappingStrategy
    public void captureHeader(CSVReader cSVReader) throws CsvRequiredFieldEmptyException, IOException {
        super.captureHeader(cSVReader);
        Set set = (Set) Stream.of((Object[]) this.headerIndex.getHeaderIndex()).filter(new Predicate() { // from class: com.opencsv.bean.FuzzyMappingStrategy$$ExternalSyntheticLambda1
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return Objects.nonNull((String) obj);
            }
        }).filter(new Predicate() { // from class: com.opencsv.bean.FuzzyMappingStrategy$$ExternalSyntheticLambda2
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return this.f$0.m2753lambda$captureHeader$0$comopencsvbeanFuzzyMappingStrategy((String) obj);
            }
        }).collect(Collectors.toSet());
        final ListValuedMap<Class<?>, Field> listValuedMap = partitionFields().get(Boolean.FALSE);
        final LevenshteinDistance defaultInstance = LevenshteinDistance.getDefaultInstance();
        final LinkedList linkedList = new LinkedList();
        set.forEach(new Consumer() { // from class: com.opencsv.bean.FuzzyMappingStrategy$$ExternalSyntheticLambda3
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                listValuedMap.entries().forEach(new Consumer() { // from class: com.opencsv.bean.FuzzyMappingStrategy$$ExternalSyntheticLambda0
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj2) {
                        List list = list;
                        LevenshteinDistance levenshteinDistance = levenshteinDistance;
                        String str = str;
                        Map.Entry entry = (Map.Entry) obj2;
                        list.add(new FuzzyMappingStrategy.FuzzyComparison(levenshteinDistance.apply(str.toUpperCase(), ((Field) entry.getValue()).getName().toUpperCase()), str, (Class) entry.getKey(), (Field) entry.getValue()));
                    }
                });
            }
        });
        linkedList.sort(null);
        while (!linkedList.isEmpty()) {
            final FuzzyComparison fuzzyComparison = (FuzzyComparison) linkedList.get(0);
            this.fieldMap.put(fuzzyComparison.header.toUpperCase(), new BeanFieldSingleValue(fuzzyComparison.type, fuzzyComparison.field, false, this.errorLocale, determineConverter(fuzzyComparison.field, fuzzyComparison.field.getType(), null, null, null), null, null));
            linkedList.removeIf(new Predicate() { // from class: com.opencsv.bean.FuzzyMappingStrategy$$ExternalSyntheticLambda4
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    return FuzzyMappingStrategy.lambda$captureHeader$3(fuzzyComparison, (FuzzyMappingStrategy.FuzzyComparison) obj);
                }
            });
        }
    }

    /* JADX INFO: renamed from: lambda$captureHeader$0$com-opencsv-bean-FuzzyMappingStrategy, reason: not valid java name */
    /* synthetic */ boolean m2753lambda$captureHeader$0$comopencsvbeanFuzzyMappingStrategy(String str) {
        return this.fieldMap.get(str.toUpperCase()) == null;
    }

    static /* synthetic */ boolean lambda$captureHeader$3(FuzzyComparison fuzzyComparison, FuzzyComparison fuzzyComparison2) {
        return StringUtils.equals(fuzzyComparison2.header, fuzzyComparison.header) || Objects.equals(fuzzyComparison2.field, fuzzyComparison.field);
    }

    /* JADX INFO: Access modifiers changed from: private */
    static class FuzzyComparison implements Comparable<FuzzyComparison> {
        final Integer distance;
        final Field field;
        final String header;
        final Class<?> type;

        FuzzyComparison(Integer num, String str, Class<?> cls, Field field) {
            this.distance = num;
            this.header = str;
            this.type = cls;
            this.field = field;
        }

        @Override // java.lang.Comparable
        public int compareTo(FuzzyComparison fuzzyComparison) {
            return Integer.compare(this.distance.intValue(), fuzzyComparison.distance.intValue());
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj instanceof FuzzyComparison) {
                return Objects.equals(this.distance, ((FuzzyComparison) obj).distance);
            }
            return false;
        }

        public int hashCode() {
            return Objects.hash(this.distance);
        }
    }
}
