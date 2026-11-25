package org.jetbrains.kotlin.com.intellij.util.keyFMap;

import com.google.firebase.crashlytics.internal.metadata.UserMetadata;
import com.reandroid.arsc.value.CompoundEntry;
import gnu.trove.TIntObjectHashMap;
import gnu.trove.TIntObjectProcedure;
import java.util.Arrays;
import org.antlr.stringtemplate.language.ASTExpr;
import org.jetbrains.kotlin.com.intellij.openapi.util.Key;
import org.jetbrains.kotlin.com.intellij.util.ArrayUtil;

/* JADX INFO: loaded from: classes3.dex */
final class MapBackedFMap extends TIntObjectHashMap<Object> implements KeyFMap {
    static final /* synthetic */ boolean $assertionsDisabled = false;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 8 || i == 10 || i == 12) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 8 || i == 10 || i == 12) ? 2 : 3];
        switch (i) {
            case 2:
            case 4:
                objArr[0] = "newValue";
                break;
            case 3:
                objArr[0] = CompoundEntry.NAME_values;
                break;
            case 5:
                objArr[0] = UserMetadata.KEYDATA_FILENAME;
                break;
            case 6:
            case 9:
            case 11:
                objArr[0] = ASTExpr.DEFAULT_MAP_KEY_NAME;
                break;
            case 7:
                objArr[0] = "value";
                break;
            case 8:
            case 10:
            case 12:
                objArr[0] = "org/jetbrains/kotlin/com/intellij/util/keyFMap/MapBackedFMap";
                break;
            case 13:
                objArr[0] = "other";
                break;
            default:
                objArr[0] = "oldMap";
                break;
        }
        if (i == 8) {
            objArr[1] = "plus";
        } else if (i == 10) {
            objArr[1] = "minus";
        } else if (i != 12) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/util/keyFMap/MapBackedFMap";
        } else {
            objArr[1] = "getKeys";
        }
        switch (i) {
            case 6:
            case 7:
                objArr[2] = "plus";
                break;
            case 8:
            case 10:
            case 12:
                break;
            case 9:
                objArr[2] = "minus";
                break;
            case 11:
                objArr[2] = "get";
                break;
            case 13:
                objArr[2] = "equalsByReference";
                break;
            default:
                objArr[2] = "<init>";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i != 8 && i != 10 && i != 12) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    private MapBackedFMap(MapBackedFMap mapBackedFMap, final int i) {
        super(mapBackedFMap.size());
        if (mapBackedFMap == null) {
            $$$reportNull$$$0(0);
        }
        mapBackedFMap.forEachEntry(new TIntObjectProcedure() { // from class: org.jetbrains.kotlin.com.intellij.util.keyFMap.MapBackedFMap$$ExternalSyntheticLambda0
            @Override // gnu.trove.TIntObjectProcedure
            public final boolean execute(int i2, Object obj) {
                return this.f$0.m6740xafa12668(i, i2, obj);
            }
        });
    }

    /* JADX INFO: renamed from: lambda$new$0$org-jetbrains-kotlin-com-intellij-util-keyFMap-MapBackedFMap, reason: not valid java name */
    /* synthetic */ boolean m6740xafa12668(int i, int i2, Object obj) {
        if (i2 == i) {
            return true;
        }
        put(i2, obj);
        return true;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    private MapBackedFMap(MapBackedFMap mapBackedFMap, int i, Object obj) {
        super(mapBackedFMap.size() + 1);
        if (mapBackedFMap == null) {
            $$$reportNull$$$0(1);
        }
        if (obj == null) {
            $$$reportNull$$$0(2);
        }
        mapBackedFMap.forEachEntry(new TIntObjectProcedure() { // from class: org.jetbrains.kotlin.com.intellij.util.keyFMap.MapBackedFMap$$ExternalSyntheticLambda1
            @Override // gnu.trove.TIntObjectProcedure
            public final boolean execute(int i2, Object obj2) {
                return this.f$0.m6741x43df9607(i2, obj2);
            }
        });
        put(i, obj);
    }

    /* JADX INFO: renamed from: lambda$new$1$org-jetbrains-kotlin-com-intellij-util-keyFMap-MapBackedFMap, reason: not valid java name */
    /* synthetic */ boolean m6741x43df9607(int i, Object obj) {
        put(i, obj);
        return true;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    MapBackedFMap(int[] iArr, int i, Object[] objArr, Object obj) {
        super(iArr.length + 1);
        if (objArr == null) {
            $$$reportNull$$$0(3);
        }
        if (obj == null) {
            $$$reportNull$$$0(4);
        }
        if (iArr == null) {
            $$$reportNull$$$0(5);
        }
        for (int i2 = 0; i2 < iArr.length; i2++) {
            put(iArr[i2], objArr[i2]);
        }
        put(i, obj);
    }

    @Override // org.jetbrains.kotlin.com.intellij.util.keyFMap.KeyFMap
    public <V> KeyFMap plus(Key<V> key, V v) {
        if (key == null) {
            $$$reportNull$$$0(6);
        }
        if (v == null) {
            $$$reportNull$$$0(7);
        }
        int iHashCode = key.hashCode();
        return v == get(iHashCode) ? this : new MapBackedFMap(this, iHashCode, v);
    }

    @Override // org.jetbrains.kotlin.com.intellij.util.keyFMap.KeyFMap
    public KeyFMap minus(Key<?> key) {
        if (key == null) {
            $$$reportNull$$$0(9);
        }
        int size = size();
        int iHashCode = key.hashCode();
        if (!containsKey(iHashCode)) {
            return this;
        }
        if (size == 9) {
            int[] iArrKeys = keys();
            int[] iArrRemove = ArrayUtil.remove(iArrKeys, ArrayUtil.indexOf(iArrKeys, iHashCode));
            Arrays.sort(iArrRemove);
            Object[] objArr = new Object[iArrRemove.length];
            for (int i = 0; i < iArrRemove.length; i++) {
                objArr[i] = get(iArrRemove[i]);
            }
            return new ArrayBackedFMap(iArrRemove, objArr);
        }
        return new MapBackedFMap(this, iHashCode);
    }

    @Override // org.jetbrains.kotlin.com.intellij.util.keyFMap.KeyFMap
    public <V> V get(Key<V> key) {
        if (key == null) {
            $$$reportNull$$$0(11);
        }
        return (V) get(key.hashCode());
    }

    @Override // org.jetbrains.kotlin.com.intellij.util.keyFMap.KeyFMap
    public Key[] getKeys() {
        Key[] keysByIndices = ArrayBackedFMap.getKeysByIndices(keys());
        if (keysByIndices == null) {
            $$$reportNull$$$0(12);
        }
        return keysByIndices;
    }

    @Override // gnu.trove.TIntObjectHashMap
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        forEachEntry(new TIntObjectProcedure<Object>() { // from class: org.jetbrains.kotlin.com.intellij.util.keyFMap.MapBackedFMap.1
            @Override // gnu.trove.TIntObjectProcedure
            public boolean execute(int i, Object obj) {
                StringBuilder sb2 = sb;
                sb2.append(sb2.length() == 0 ? "" : ", ").append(Key.getKeyByIndex(i)).append(" -> ").append(obj);
                return true;
            }
        });
        return "[" + ((Object) sb) + "]";
    }
}
