package org.jetbrains.kotlin.config;

import com.google.android.gms.analytics.ecommerce.ProductAction;
import com.reandroid.arsc.value.CompoundEntry;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import org.antlr.stringtemplate.language.ASTExpr;
import org.jetbrains.kotlin.com.intellij.openapi.util.Key;

/* JADX INFO: loaded from: classes3.dex */
public class CompilerConfiguration {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    public static CompilerConfiguration EMPTY;
    private final Map<Key, Object> map = new LinkedHashMap();
    private boolean readOnly = false;

    /* JADX WARN: Code duplicated, block: B:40:0x0064  */
    /* JADX WARN: Code duplicated, block: B:9:0x0014  */
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str;
        int i2;
        if (i != 3 && i != 5 && i != 8 && i != 10) {
            switch (i) {
                case 25:
                case 26:
                case 27:
                case 28:
                case 29:
                    str = "@NotNull method %s.%s must not return null";
                    break;
                default:
                    str = "Argument for @NotNull parameter '%s' of %s.%s must not be null";
                    break;
            }
        } else {
            str = "@NotNull method %s.%s must not return null";
        }
        if (i != 3 && i != 5 && i != 8 && i != 10) {
            switch (i) {
                case 25:
                case 26:
                case 27:
                case 28:
                case 29:
                    i2 = 2;
                    break;
                default:
                    i2 = 3;
                    break;
            }
        } else {
            i2 = 2;
        }
        Object[] objArr = new Object[i2];
        if (i == 2) {
            objArr[0] = "defaultValue";
        } else if (i == 3 || i == 5 || i == 8 || i == 10) {
            objArr[0] = "org/jetbrains/kotlin/config/CompilerConfiguration";
        } else if (i == 12 || i == 14 || i == 20 || i == 17) {
            objArr[0] = "value";
        } else if (i != 18) {
            switch (i) {
                case 23:
                    objArr[0] = CompoundEntry.NAME_values;
                    break;
                case 24:
                    objArr[0] = "object";
                    break;
                case 25:
                case 26:
                case 27:
                case 28:
                case 29:
                    objArr[0] = "org/jetbrains/kotlin/config/CompilerConfiguration";
                    break;
                default:
                    objArr[0] = ASTExpr.DEFAULT_MAP_KEY_NAME;
                    break;
            }
        } else {
            objArr[0] = "configurationKey";
        }
        if (i == 3) {
            objArr[1] = "get";
        } else if (i == 5) {
            objArr[1] = "getNotNull";
        } else if (i == 8) {
            objArr[1] = "getList";
        } else if (i != 10) {
            switch (i) {
                case 25:
                case 26:
                case 27:
                case 28:
                case 29:
                    objArr[1] = "unmodifiable";
                    break;
                default:
                    objArr[1] = "org/jetbrains/kotlin/config/CompilerConfiguration";
                    break;
            }
        } else {
            objArr[1] = "getMap";
        }
        switch (i) {
            case 3:
            case 5:
            case 8:
            case 10:
            case 25:
            case 26:
            case 27:
            case 28:
            case 29:
                break;
            case 4:
                objArr[2] = "getNotNull";
                break;
            case 6:
                objArr[2] = "getBoolean";
                break;
            case 7:
                objArr[2] = "getList";
                break;
            case 9:
                objArr[2] = "getMap";
                break;
            case 11:
            case 12:
            case 18:
            case 19:
            case 20:
                objArr[2] = "put";
                break;
            case 13:
            case 14:
                objArr[2] = "putIfAbsent";
                break;
            case 15:
                objArr[2] = "putIfNotNull";
                break;
            case 16:
            case 17:
                objArr[2] = ProductAction.ACTION_ADD;
                break;
            case 21:
            case 22:
            case 23:
                objArr[2] = "addAll";
                break;
            case 24:
                objArr[2] = "unmodifiable";
                break;
            default:
                objArr[2] = "get";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i != 3 && i != 5 && i != 8 && i != 10) {
            switch (i) {
                case 25:
                case 26:
                case 27:
                case 28:
                case 29:
                    break;
                default:
                    throw new IllegalArgumentException(str2);
            }
        }
        throw new IllegalStateException(str2);
    }

    static {
        CompilerConfiguration compilerConfiguration = new CompilerConfiguration();
        EMPTY = compilerConfiguration;
        compilerConfiguration.setReadOnly(true);
    }

    public <T> T get(CompilerConfigurationKey<T> compilerConfigurationKey) {
        if (compilerConfigurationKey == null) {
            $$$reportNull$$$0(0);
        }
        Object obj = this.map.get(compilerConfigurationKey.ideaKey);
        if (obj == null) {
            return null;
        }
        return (T) unmodifiable(obj);
    }

    public <T> T get(CompilerConfigurationKey<T> compilerConfigurationKey, T t) {
        if (compilerConfigurationKey == null) {
            $$$reportNull$$$0(1);
        }
        if (t == null) {
            $$$reportNull$$$0(2);
        }
        Object obj = get(compilerConfigurationKey);
        if (obj != null) {
            t = (T) obj;
        }
        if (t == null) {
            $$$reportNull$$$0(3);
        }
        return t;
    }

    public <T> T getNotNull(CompilerConfigurationKey<T> compilerConfigurationKey) {
        if (compilerConfigurationKey == null) {
            $$$reportNull$$$0(4);
        }
        T t = (T) get(compilerConfigurationKey);
        if (t == null) {
            $$$reportNull$$$0(5);
        }
        return t;
    }

    public boolean getBoolean(CompilerConfigurationKey<Boolean> compilerConfigurationKey) {
        if (compilerConfigurationKey == null) {
            $$$reportNull$$$0(6);
        }
        return ((Boolean) get(compilerConfigurationKey, false)).booleanValue();
    }

    public <T> List<T> getList(CompilerConfigurationKey<List<T>> compilerConfigurationKey) {
        if (compilerConfigurationKey == null) {
            $$$reportNull$$$0(7);
        }
        List<T> listEmptyList = (List) get(compilerConfigurationKey);
        if (listEmptyList == null) {
            listEmptyList = Collections.emptyList();
        }
        if (listEmptyList == null) {
            $$$reportNull$$$0(8);
        }
        return listEmptyList;
    }

    public <K, V> Map<K, V> getMap(CompilerConfigurationKey<Map<K, V>> compilerConfigurationKey) {
        if (compilerConfigurationKey == null) {
            $$$reportNull$$$0(9);
        }
        Map<K, V> mapEmptyMap = (Map) get(compilerConfigurationKey);
        if (mapEmptyMap == null) {
            mapEmptyMap = Collections.emptyMap();
        }
        if (mapEmptyMap == null) {
            $$$reportNull$$$0(10);
        }
        return mapEmptyMap;
    }

    public <T> void put(CompilerConfigurationKey<T> compilerConfigurationKey, T t) {
        if (compilerConfigurationKey == null) {
            $$$reportNull$$$0(11);
        }
        if (t == null) {
            $$$reportNull$$$0(12);
        }
        checkReadOnly();
        this.map.put(compilerConfigurationKey.ideaKey, t);
    }

    public <T> T putIfAbsent(CompilerConfigurationKey<T> compilerConfigurationKey, T t) {
        if (compilerConfigurationKey == null) {
            $$$reportNull$$$0(13);
        }
        if (t == null) {
            $$$reportNull$$$0(14);
        }
        T t2 = (T) get(compilerConfigurationKey);
        if (t2 != null) {
            return t2;
        }
        checkReadOnly();
        put(compilerConfigurationKey, t);
        return t;
    }

    public <T> void putIfNotNull(CompilerConfigurationKey<T> compilerConfigurationKey, T t) {
        if (compilerConfigurationKey == null) {
            $$$reportNull$$$0(15);
        }
        if (t != null) {
            put(compilerConfigurationKey, t);
        }
    }

    public <T> void add(CompilerConfigurationKey<List<T>> compilerConfigurationKey, T t) {
        if (compilerConfigurationKey == null) {
            $$$reportNull$$$0(16);
        }
        if (t == null) {
            $$$reportNull$$$0(17);
        }
        checkReadOnly();
        Key<List<T>> key = compilerConfigurationKey.ideaKey;
        this.map.computeIfAbsent(key, new Function() { // from class: org.jetbrains.kotlin.config.CompilerConfiguration$$ExternalSyntheticLambda2
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return CompilerConfiguration.lambda$add$0((Key) obj);
            }
        });
        ((List) this.map.get(key)).add(t);
    }

    static /* synthetic */ Object lambda$add$0(Key key) {
        return new ArrayList();
    }

    public <K, V> void put(CompilerConfigurationKey<Map<K, V>> compilerConfigurationKey, K k, V v) {
        if (compilerConfigurationKey == null) {
            $$$reportNull$$$0(18);
        }
        if (k == null) {
            $$$reportNull$$$0(19);
        }
        if (v == null) {
            $$$reportNull$$$0(20);
        }
        checkReadOnly();
        Key<Map<K, V>> key = compilerConfigurationKey.ideaKey;
        this.map.computeIfAbsent(key, new Function() { // from class: org.jetbrains.kotlin.config.CompilerConfiguration$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return CompilerConfiguration.lambda$put$1((Key) obj);
            }
        });
        ((Map) this.map.get(key)).put(k, v);
    }

    static /* synthetic */ Object lambda$put$1(Key key) {
        return new HashMap();
    }

    public <T> void addAll(CompilerConfigurationKey<List<T>> compilerConfigurationKey, Collection<T> collection) {
        if (compilerConfigurationKey == null) {
            $$$reportNull$$$0(21);
        }
        if (collection != null) {
            addAll(compilerConfigurationKey, getList(compilerConfigurationKey).size(), collection);
        }
    }

    public <T> void addAll(CompilerConfigurationKey<List<T>> compilerConfigurationKey, int i, Collection<T> collection) {
        if (compilerConfigurationKey == null) {
            $$$reportNull$$$0(22);
        }
        if (collection == null) {
            $$$reportNull$$$0(23);
        }
        checkReadOnly();
        checkForNullElements(collection);
        Key<List<T>> key = compilerConfigurationKey.ideaKey;
        this.map.computeIfAbsent(key, new Function() { // from class: org.jetbrains.kotlin.config.CompilerConfiguration$$ExternalSyntheticLambda1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return CompilerConfiguration.lambda$addAll$2((Key) obj);
            }
        });
        ((List) this.map.get(key)).addAll(i, collection);
    }

    static /* synthetic */ Object lambda$addAll$2(Key key) {
        return new ArrayList();
    }

    public CompilerConfiguration copy() {
        CompilerConfiguration compilerConfiguration = new CompilerConfiguration();
        compilerConfiguration.map.putAll(this.map);
        return compilerConfiguration;
    }

    private void checkReadOnly() {
        if (this.readOnly) {
            throw new IllegalStateException("CompilerConfiguration is read-only");
        }
    }

    public void setReadOnly(boolean z) {
        if (z != this.readOnly) {
            this.readOnly = z;
        }
    }

    public boolean isReadOnly() {
        return this.readOnly;
    }

    private static <T> T unmodifiable(T t) {
        if (t == null) {
            $$$reportNull$$$0(24);
        }
        if (t instanceof List) {
            T t2 = (T) Collections.unmodifiableList((List) t);
            if (t2 == null) {
                $$$reportNull$$$0(25);
            }
            return t2;
        }
        if (t instanceof Map) {
            T t3 = (T) Collections.unmodifiableMap((Map) t);
            if (t3 == null) {
                $$$reportNull$$$0(26);
            }
            return t3;
        }
        if (t instanceof Set) {
            T t4 = (T) Collections.unmodifiableSet((Set) t);
            if (t4 == null) {
                $$$reportNull$$$0(27);
            }
            return t4;
        }
        if (!(t instanceof Collection)) {
            if (t == null) {
                $$$reportNull$$$0(29);
            }
            return t;
        }
        T t5 = (T) Collections.unmodifiableCollection((Collection) t);
        if (t5 == null) {
            $$$reportNull$$$0(28);
        }
        return t5;
    }

    public String toString() {
        return this.map.toString();
    }

    private static <T> void checkForNullElements(Collection<T> collection) {
        Iterator<T> it = collection.iterator();
        int i = 0;
        while (it.hasNext()) {
            if (it.next() == null) {
                throw new IllegalArgumentException("Element " + i + " is null, while null values in compiler configuration are not allowed");
            }
            i++;
        }
    }
}
