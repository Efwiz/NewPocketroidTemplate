package org.jetbrains.kotlin.com.intellij.util.containers;

import com.google.android.gms.analytics.ecommerce.ProductAction;
import com.google.firebase.database.core.ServerValues;
import com.reandroid.arsc.chunk.TypeBlock;
import com.reandroid.arsc.value.CompoundEntry;
import org.antlr.stringtemplate.language.ASTExpr;
import org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.Object2IntMap;
import org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;

/* JADX INFO: loaded from: classes3.dex */
public class ObjectIntHashMap<K> implements ObjectIntMap<K> {
    private final Object2IntMap<K> myMap;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 4 || i == 5 || i == 6) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 4 || i == 5 || i == 6) ? 2 : 3];
        if (i == 4 || i == 5 || i == 6) {
            objArr[0] = "org/jetbrains/kotlin/com/intellij/util/containers/ObjectIntHashMap";
        } else {
            objArr[0] = ASTExpr.DEFAULT_MAP_KEY_NAME;
        }
        if (i == 4) {
            objArr[1] = "keySet";
        } else if (i == 5) {
            objArr[1] = CompoundEntry.NAME_values;
        } else if (i != 6) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/util/containers/ObjectIntHashMap";
        } else {
            objArr[1] = TypeBlock.NAME_entries;
        }
        switch (i) {
            case 1:
                objArr[2] = "put";
                break;
            case 2:
                objArr[2] = ProductAction.ACTION_REMOVE;
                break;
            case 3:
                objArr[2] = "containsKey";
                break;
            case 4:
            case 5:
            case 6:
                break;
            case 7:
                objArr[2] = ServerValues.NAME_OP_INCREMENT;
                break;
            default:
                objArr[2] = "get";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i != 4 && i != 5 && i != 6) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    public ObjectIntHashMap() {
        this(10);
    }

    public ObjectIntHashMap(int i) {
        Object2IntOpenHashMap object2IntOpenHashMap = new Object2IntOpenHashMap(i);
        this.myMap = object2IntOpenHashMap;
        object2IntOpenHashMap.defaultReturnValue(-1);
    }

    @Override // org.jetbrains.kotlin.com.intellij.util.containers.ObjectIntMap
    public int get(K k) {
        if (k == null) {
            $$$reportNull$$$0(0);
        }
        return this.myMap.getInt(k);
    }

    @Override // org.jetbrains.kotlin.com.intellij.util.containers.ObjectIntMap
    public int put(K k, int i) {
        if (k == null) {
            $$$reportNull$$$0(1);
        }
        return this.myMap.put(k, i);
    }
}
