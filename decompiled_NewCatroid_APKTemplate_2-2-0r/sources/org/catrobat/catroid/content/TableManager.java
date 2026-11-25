package org.catrobat.catroid.content;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/TableManager.class
  classes14.dex
 */
/* JADX INFO: compiled from: TableManager.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\u0018\u0000 \u00032\u00020\u0001:\u0001\u0003B\u0005¢\u0006\u0002\u0010\u0002¨\u0006\u0004"}, d2 = {"Lorg/catrobat/catroid/content/TableManager;", "", "()V", "Companion", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class TableManager {
    public static final String DEFAULT_VALUE = "0";

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private static final ConcurrentHashMap<String, Companion.TableData> tables = new ConcurrentHashMap<>();

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/TableManager$Companion.class
      classes14.dex
     */
    /* JADX INFO: compiled from: TableManager.kt */
    @Metadata(d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\b\u000b\b\u0086\u0003\u0018\u00002\u00020\u0001:\u0001\u001dB\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u001e\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u00042\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\fJ\u0006\u0010\u000e\u001a\u00020\tJ\u000e\u0010\u000f\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u0004J \u0010\u0010\u001a\u0004\u0018\u00010\u00042\u0006\u0010\n\u001a\u00020\u00042\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\fJ*\u0010\u0011\u001a\u001e\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\f\u0012\u0004\u0012\u00020\f0\u0013\u0012\u0006\u0012\u0004\u0018\u00010\u0001\u0018\u00010\u00122\u0006\u0010\n\u001a\u00020\u0004J\u000e\u0010\u0014\u001a\u00020\f2\u0006\u0010\n\u001a\u00020\u0004J\u000e\u0010\u0015\u001a\u00020\f2\u0006\u0010\n\u001a\u00020\u0004J(\u0010\u0016\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u00042\b\u0010\u0017\u001a\u0004\u0018\u00010\u00012\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\fJ&\u0010\u0016\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u00042\u0006\u0010\u0017\u001a\u00020\f2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\fJ&\u0010\u0018\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u00042\u0006\u0010\u0019\u001a\u00020\u00042\u0006\u0010\u001a\u001a\u00020\u00042\u0006\u0010\u001b\u001a\u00020\u0004J\u001e\u0010\u001c\u001a\u00020\u00042\u0006\u0010\n\u001a\u00020\u00042\u0006\u0010\u001a\u001a\u00020\u00042\u0006\u0010\u001b\u001a\u00020\u0004R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u001a\u0010\u0005\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00070\u0006X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u001e"}, d2 = {"Lorg/catrobat/catroid/content/TableManager$Companion;", "", "()V", "DEFAULT_VALUE", "", "tables", "Ljava/util/concurrent/ConcurrentHashMap;", "Lorg/catrobat/catroid/content/TableManager$Companion$TableData;", "createTable", "", "name", "x", "", "y", "deleteAllTables", "deleteTable", "getElementValue", "getTable", "", "Lkotlin/Pair;", "getTableXSize", "getTableYSize", "insertElement", "value", "stringToTable", "data", "xDelimiter", "yDelimiter", "tableToString", "TableData", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        /* JADX WARN: Classes with same name are omitted, all sources:
          NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/TableManager$Companion$TableData.class
          classes14.dex
         */
        /* JADX INFO: compiled from: TableManager.kt */
        @Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\t\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\f\n\u0002\u0010\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0016\u0010\u0011\u001a\u00020\u00012\u0006\u0010\u0012\u001a\u00020\t2\u0006\u0010\u0013\u001a\u00020\tJ\u0016\u0010\u0014\u001a\u00020\u00052\u0006\u0010\u0012\u001a\u00020\t2\u0006\u0010\u0013\u001a\u00020\tJ\u001e\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0012\u001a\u00020\t2\u0006\u0010\u0013\u001a\u00020\t2\u0006\u0010\u0017\u001a\u00020\u0001R\u001d\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00010\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u001a\u0010\b\u001a\u00020\tX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\n\u0010\u000b\"\u0004\b\f\u0010\rR\u001a\u0010\u000e\u001a\u00020\tX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000f\u0010\u000b\"\u0004\b\u0010\u0010\r¨\u0006\u0018"}, d2 = {"Lorg/catrobat/catroid/content/TableManager$Companion$TableData;", "", "()V", "cells", "Ljava/util/concurrent/ConcurrentHashMap;", "", "getCells", "()Ljava/util/concurrent/ConcurrentHashMap;", "maxX", "", "getMaxX", "()I", "setMaxX", "(I)V", "maxY", "getMaxY", "setMaxY", "get", "x", "y", "getKey", "insert", "", "value", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
        public static final class TableData {
            private final ConcurrentHashMap<Long, Object> cells = new ConcurrentHashMap<>();
            private volatile int maxX;
            private volatile int maxY;

            public final long getKey(int x, int y) {
                return (((long) y) & 4294967295L) | (((long) x) << 32);
            }

            public final ConcurrentHashMap<Long, Object> getCells() {
                return this.cells;
            }

            public final int getMaxX() {
                return this.maxX;
            }

            public final void setMaxX(int i) {
                this.maxX = i;
            }

            public final int getMaxY() {
                return this.maxY;
            }

            public final void setMaxY(int i) {
                this.maxY = i;
            }

            public final synchronized void insert(int x, int y, Object value) {
                Intrinsics.checkNotNullParameter(value, "value");
                if (x > 0 && y > 0) {
                    this.cells.put(Long.valueOf(getKey(x, y)), value);
                    this.maxX = Math.max(this.maxX, x);
                    this.maxY = Math.max(this.maxY, y);
                }
            }

            public final Object get(int x, int y) {
                Object obj = this.cells.get(Long.valueOf(getKey(x, y)));
                return obj == null ? "0" : obj;
            }
        }

        public final void createTable(String name2, int x, int y) {
            Intrinsics.checkNotNullParameter(name2, "name");
            if (TableManager.tables.containsKey(name2)) {
                return;
            }
            TableData tableData = new TableData();
            if (1 <= x) {
                int i = 1;
                while (true) {
                    if (1 <= y) {
                        int i2 = 1;
                        while (true) {
                            tableData.insert(i, i2, "0");
                            if (i2 == y) {
                                break;
                            } else {
                                i2++;
                            }
                        }
                    }
                    if (i == x) {
                        break;
                    } else {
                        i++;
                    }
                }
            }
            TableManager.tables.put(name2, tableData);
        }

        public final void insertElement(String name2, Object value, int x, int y) {
            TableData tableData;
            Intrinsics.checkNotNullParameter(name2, "name");
            if (value == null || (tableData = (TableData) TableManager.tables.get(name2)) == null) {
                return;
            }
            tableData.insert(x, y, value);
        }

        public final void insertElement(String name2, int value, int x, int y) {
            Intrinsics.checkNotNullParameter(name2, "name");
            TableData tableData = (TableData) TableManager.tables.get(name2);
            if (tableData != null) {
                tableData.insert(x, y, Integer.valueOf(value));
            }
        }

        public final void deleteTable(String name2) {
            Intrinsics.checkNotNullParameter(name2, "name");
            TableManager.tables.remove(name2);
        }

        public final void deleteAllTables() {
            TableManager.tables.clear();
        }

        public final int getTableXSize(String name2) {
            Intrinsics.checkNotNullParameter(name2, "name");
            TableData tableData = (TableData) TableManager.tables.get(name2);
            if (tableData != null) {
                return tableData.getMaxX();
            }
            return 0;
        }

        public final int getTableYSize(String name2) {
            Intrinsics.checkNotNullParameter(name2, "name");
            TableData tableData = (TableData) TableManager.tables.get(name2);
            if (tableData != null) {
                return tableData.getMaxY();
            }
            return 0;
        }

        public final String getElementValue(String name2, int x, int y) {
            Object obj;
            Intrinsics.checkNotNullParameter(name2, "name");
            TableData tableData = (TableData) TableManager.tables.get(name2);
            if (tableData == null || (obj = tableData.get(x, y)) == null) {
                return null;
            }
            return obj.toString();
        }

        /* JADX WARN: Multi-variable type inference failed */
        public final void stringToTable(String name2, String data, String xDelimiter, String yDelimiter) {
            Intrinsics.checkNotNullParameter(name2, "name");
            Intrinsics.checkNotNullParameter(data, "data");
            Intrinsics.checkNotNullParameter(xDelimiter, "xDelimiter");
            Intrinsics.checkNotNullParameter(yDelimiter, "yDelimiter");
            TableData tableData = (TableData) TableManager.tables.get(name2);
            if (tableData == null) {
                return;
            }
            tableData.getCells().clear();
            tableData.setMaxX(0);
            tableData.setMaxY(0);
            int i = 0;
            for (Object obj : StringsKt.split$default((CharSequence) data, new String[]{yDelimiter}, false, 0, 6, (Object) null)) {
                int i2 = i + 1;
                if (i < 0) {
                    CollectionsKt.throwIndexOverflow();
                }
                int i3 = 0;
                for (Object obj2 : StringsKt.split$default((CharSequence) obj, new String[]{xDelimiter}, false, 0, 6, (Object) null)) {
                    int i4 = i3 + 1;
                    if (i3 < 0) {
                        CollectionsKt.throwIndexOverflow();
                    }
                    String string = StringsKt.trim((CharSequence) obj2).toString();
                    Integer intOrNull = StringsKt.toIntOrNull(string);
                    if (intOrNull != null) {
                        string = intOrNull;
                    }
                    tableData.insert(i4, i2, string);
                    i3 = i4;
                }
                i = i2;
            }
        }

        public final String tableToString(String name2, String xDelimiter, String yDelimiter) {
            Intrinsics.checkNotNullParameter(name2, "name");
            Intrinsics.checkNotNullParameter(xDelimiter, "xDelimiter");
            Intrinsics.checkNotNullParameter(yDelimiter, "yDelimiter");
            TableData tableData = (TableData) TableManager.tables.get(name2);
            if (tableData == null) {
                return "";
            }
            int maxX = tableData.getMaxX();
            int maxY = tableData.getMaxY();
            if (maxX == 0 || maxY == 0) {
                return "";
            }
            StringBuilder sb = new StringBuilder();
            if (1 <= maxY) {
                int i = 1;
                while (true) {
                    if (1 <= maxX) {
                        int i2 = 1;
                        while (true) {
                            sb.append(tableData.get(i2, i));
                            if (i2 < maxX) {
                                sb.append(xDelimiter);
                            }
                            if (i2 == maxX) {
                                break;
                            }
                            i2++;
                        }
                    }
                    if (i < maxY) {
                        sb.append(yDelimiter);
                    }
                    if (i == maxY) {
                        break;
                    }
                    i++;
                }
            }
            String string = sb.toString();
            Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
            return string;
        }

        public final Map<Pair<Integer, Integer>, Object> getTable(String name2) {
            Intrinsics.checkNotNullParameter(name2, "name");
            TableData tableData = (TableData) TableManager.tables.get(name2);
            if (tableData == null) {
                return null;
            }
            LinkedHashMap linkedHashMap = new LinkedHashMap();
            for (Map.Entry<Long, Object> entry : tableData.getCells().entrySet()) {
                long jLongValue = entry.getKey().longValue();
                linkedHashMap.put(new Pair(Integer.valueOf((int) (jLongValue >> 32)), Integer.valueOf((int) jLongValue)), entry.getValue());
            }
            return linkedHashMap;
        }
    }
}
