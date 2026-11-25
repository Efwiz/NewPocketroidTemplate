package com.opencsv;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;

/* JADX INFO: loaded from: classes10.dex */
public class ResultSetColumnNameHelperService extends ResultSetHelperService implements ResultSetHelper {
    private String[] columnHeaders;
    private String[] columnNames;
    private final Map<String, Integer> columnNamePositionMap = new HashMap();
    private Locale errorLocale = Locale.getDefault();

    public void setErrorLocale(Locale locale) {
        this.errorLocale = (Locale) ObjectUtils.defaultIfNull(locale, Locale.getDefault());
    }

    public void setColumnNames(String[] strArr, String[] strArr2) {
        if (strArr2.length != strArr.length) {
            throw new UnsupportedOperationException(ResourceBundle.getBundle(ICSVParser.DEFAULT_BUNDLE_NAME, this.errorLocale).getString("column.count.mismatch"));
        }
        if (hasInvalidValue(strArr)) {
            throw new UnsupportedOperationException(ResourceBundle.getBundle(ICSVParser.DEFAULT_BUNDLE_NAME, this.errorLocale).getString("column.name.bogus"));
        }
        if (hasInvalidValue(strArr2)) {
            throw new UnsupportedOperationException(ResourceBundle.getBundle(ICSVParser.DEFAULT_BUNDLE_NAME, this.errorLocale).getString("header.name.bogus"));
        }
        this.columnNames = (String[]) Arrays.copyOf(strArr, strArr.length);
        this.columnHeaders = (String[]) Arrays.copyOf(strArr2, strArr2.length);
    }

    private boolean hasInvalidValue(String[] strArr) {
        return Stream.of((Object[]) strArr).anyMatch(new Predicate() { // from class: com.opencsv.ResultSetColumnNameHelperService$$ExternalSyntheticLambda1
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return StringUtils.isBlank((String) obj);
            }
        });
    }

    @Override // com.opencsv.ResultSetHelperService, com.opencsv.ResultSetHelper
    public String[] getColumnNames(ResultSet resultSet) throws SQLException {
        if (this.columnNamePositionMap.isEmpty()) {
            populateColumnData(resultSet);
        }
        String[] strArr = this.columnHeaders;
        return (String[]) Arrays.copyOf(strArr, strArr.length);
    }

    private void populateColumnData(ResultSet resultSet) throws SQLException {
        String[] columnNames = super.getColumnNames(resultSet);
        if (this.columnNames == null) {
            this.columnNames = (String[]) Arrays.copyOf(columnNames, columnNames.length);
            this.columnHeaders = (String[]) Arrays.copyOf(columnNames, columnNames.length);
        }
        for (String str : this.columnNames) {
            int iIndexOf = ArrayUtils.indexOf(columnNames, str);
            if (iIndexOf == -1) {
                throw new UnsupportedOperationException(String.format(ResourceBundle.getBundle(ICSVParser.DEFAULT_BUNDLE_NAME, this.errorLocale).getString("column.nonexistant"), str));
            }
            this.columnNamePositionMap.put(str, Integer.valueOf(iIndexOf));
        }
    }

    @Override // com.opencsv.ResultSetHelperService, com.opencsv.ResultSetHelper
    public String[] getColumnValues(ResultSet resultSet) throws SQLException, IOException {
        if (this.columnNamePositionMap.isEmpty()) {
            populateColumnData(resultSet);
        }
        return getColumnValueSubset(super.getColumnValues(resultSet, false, "dd-MMM-yyyy", "dd-MMM-yyyy HH:mm:ss"));
    }

    @Override // com.opencsv.ResultSetHelperService, com.opencsv.ResultSetHelper
    public String[] getColumnValues(ResultSet resultSet, boolean z) throws SQLException, IOException {
        if (this.columnNamePositionMap.isEmpty()) {
            populateColumnData(resultSet);
        }
        return getColumnValueSubset(super.getColumnValues(resultSet, z, "dd-MMM-yyyy", "dd-MMM-yyyy HH:mm:ss"));
    }

    @Override // com.opencsv.ResultSetHelperService, com.opencsv.ResultSetHelper
    public String[] getColumnValues(ResultSet resultSet, boolean z, String str, String str2) throws SQLException, IOException {
        if (this.columnNamePositionMap.isEmpty()) {
            populateColumnData(resultSet);
        }
        return getColumnValueSubset(super.getColumnValues(resultSet, z, str, str2));
    }

    private String[] getColumnValueSubset(final String[] strArr) {
        return (String[]) ((List) Stream.of((Object[]) this.columnNames).map(new Function() { // from class: com.opencsv.ResultSetColumnNameHelperService$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return this.f$0.m2729x9ad79871(strArr, (String) obj);
            }
        }).collect(Collectors.toList())).toArray(new String[this.columnNames.length]);
    }

    /* JADX INFO: renamed from: lambda$getColumnValueSubset$1$com-opencsv-ResultSetColumnNameHelperService, reason: not valid java name */
    /* synthetic */ String m2729x9ad79871(String[] strArr, String str) {
        return strArr[this.columnNamePositionMap.get(str).intValue()];
    }
}
