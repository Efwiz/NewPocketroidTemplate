package org.catrobat.catroid.content;

import com.badlogic.gdx.utils.FloatArray;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import kotlin.text.StringsKt;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/FloatArrayManager.class
  classes14.dex
 */
/* JADX INFO: compiled from: FloatArrayManager.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0002\b\u0007\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0014\n\u0002\b\b\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0016\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\u00052\u0006\u0010\n\u001a\u00020\u000bJ\u0016\u0010\f\u001a\u00020\b2\u0006\u0010\r\u001a\u00020\u00052\u0006\u0010\u000e\u001a\u00020\u0005J\u000e\u0010\u000f\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\u0005J\u0006\u0010\u0010\u001a\u00020\bJ\u000e\u0010\u0011\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\u0005J\u000e\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u0015J\u0010\u0010\u0016\u001a\u0004\u0018\u00010\u00152\u0006\u0010\t\u001a\u00020\u0005J\u000e\u0010\u0017\u001a\u00020\u00132\u0006\u0010\t\u001a\u00020\u0005J\u0017\u0010\u0018\u001a\u0004\u0018\u00010\u00062\u0006\u0010\t\u001a\u00020\u0005H\u0000¢\u0006\u0002\b\u0019J\u001e\u0010\u001a\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\u00052\u0006\u0010\n\u001a\u00020\u00052\u0006\u0010\u001b\u001a\u00020\u0013J\u0016\u0010\u001c\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\u00052\u0006\u0010\u001b\u001a\u00020\u0013R\u001a\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00060\u0004X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u001d"}, d2 = {"Lorg/catrobat/catroid/content/FloatArrayManager;", "", "()V", "arrays", "Ljava/util/concurrent/ConcurrentHashMap;", "", "Lcom/badlogic/gdx/utils/FloatArray;", "addElement", "", "name", "value", "", "addTableDataToFloatArray", "tableName", "arrayName", "createArray", "deleteAllArrays", "deleteArray", "findMaxIndex", "", "array", "", "getArray", "getArraySize", "getInternalArray", "getInternalArray$catroid_apktemplateTemplateRelease", "insertElement", "index", "removeElement", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FloatArrayManager {
    public static final FloatArrayManager INSTANCE = new FloatArrayManager();
    private static final ConcurrentHashMap<String, FloatArray> arrays = new ConcurrentHashMap<>();

    private FloatArrayManager() {
    }

    public final void createArray(String name2) {
        Intrinsics.checkNotNullParameter(name2, "name");
        arrays.putIfAbsent(name2, new FloatArray());
    }

    public final void deleteArray(String name2) {
        Intrinsics.checkNotNullParameter(name2, "name");
        arrays.remove(name2);
    }

    public final void deleteAllArrays() {
        arrays.clear();
    }

    public final float[] getArray(String name2) {
        Intrinsics.checkNotNullParameter(name2, "name");
        FloatArray floatArray = arrays.get(name2);
        if (floatArray == null) {
            return null;
        }
        return floatArray.toArray();
    }

    public final FloatArray getInternalArray$catroid_apktemplateTemplateRelease(String name2) {
        Intrinsics.checkNotNullParameter(name2, "name");
        return arrays.get(name2);
    }

    public final void addElement(String name2, float value) {
        Intrinsics.checkNotNullParameter(name2, "name");
        FloatArray floatArray = arrays.get(name2);
        if (floatArray != null) {
            synchronized (floatArray) {
                floatArray.add(value);
                Unit unit = Unit.INSTANCE;
            }
        }
    }

    public final void insertElement(String name2, String value, int index) {
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(value, "value");
        List listSplit$default = StringsKt.split$default((CharSequence) value, new String[]{","}, false, 0, 6, (Object) null);
        FloatArray floatArray = arrays.get(name2);
        if (floatArray != null) {
            synchronized (floatArray) {
                int iCoerceIn = RangesKt.coerceIn(index, 0, floatArray.size);
                Iterator it = listSplit$default.iterator();
                while (it.hasNext()) {
                    Float floatOrNull = StringsKt.toFloatOrNull(StringsKt.trim((CharSequence) it.next()).toString());
                    if (floatOrNull != null) {
                        floatArray.insert(iCoerceIn, floatOrNull.floatValue());
                        iCoerceIn++;
                    }
                }
                Unit unit = Unit.INSTANCE;
            }
        }
    }

    public final void removeElement(String name2, int index) {
        Intrinsics.checkNotNullParameter(name2, "name");
        FloatArray floatArray = arrays.get(name2);
        if (floatArray != null) {
            synchronized (floatArray) {
                if (index >= 0) {
                    try {
                        if (index < floatArray.size) {
                            floatArray.removeIndex(index);
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }
                Unit unit = Unit.INSTANCE;
            }
        }
    }

    public final int getArraySize(String name2) {
        Intrinsics.checkNotNullParameter(name2, "name");
        FloatArray floatArray = arrays.get(name2);
        if (floatArray != null) {
            return floatArray.size;
        }
        return 0;
    }

    public final void addTableDataToFloatArray(String tableName, String arrayName) {
        Float floatOrNull;
        Intrinsics.checkNotNullParameter(tableName, "tableName");
        Intrinsics.checkNotNullParameter(arrayName, "arrayName");
        FloatArray internalArray$catroid_apktemplateTemplateRelease = getInternalArray$catroid_apktemplateTemplateRelease(arrayName);
        if (internalArray$catroid_apktemplateTemplateRelease == null) {
            return;
        }
        int tableXSize = TableManager.INSTANCE.getTableXSize(tableName);
        int tableYSize = TableManager.INSTANCE.getTableYSize(tableName);
        if (tableXSize == 0 || tableYSize == 0) {
            return;
        }
        synchronized (internalArray$catroid_apktemplateTemplateRelease) {
            internalArray$catroid_apktemplateTemplateRelease.ensureCapacity(tableXSize * tableYSize);
            if (1 <= tableYSize) {
                int i = 1;
                while (true) {
                    if (1 <= tableXSize) {
                        int i2 = 1;
                        while (true) {
                            String elementValue = TableManager.INSTANCE.getElementValue(tableName, i2, i);
                            internalArray$catroid_apktemplateTemplateRelease.add((elementValue == null || (floatOrNull = StringsKt.toFloatOrNull(elementValue)) == null) ? 0.0f : floatOrNull.floatValue());
                            if (i2 == tableXSize) {
                                break;
                            } else {
                                i2++;
                            }
                        }
                    }
                    if (i == tableYSize) {
                        break;
                    } else {
                        i++;
                    }
                }
            }
            Unit unit = Unit.INSTANCE;
        }
    }

    public final int findMaxIndex(float[] array) {
        Intrinsics.checkNotNullParameter(array, "array");
        if (array.length == 0) {
            return -1;
        }
        int i = 0;
        float f = array[0];
        int length = array.length;
        for (int i2 = 1; i2 < length; i2++) {
            float f2 = array[i2];
            if (f2 > f) {
                i = i2;
                f = f2;
            }
        }
        return i;
    }
}
