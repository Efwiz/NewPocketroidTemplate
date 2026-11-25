package org.jetbrains.kotlin.com.intellij.psi.stubs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import org.eclipse.jdt.internal.compiler.impl.CompilerOptions;
import org.jetbrains.kotlin.com.intellij.openapi.util.Key;
import org.jetbrains.kotlin.com.intellij.psi.stubs.Stub;
import org.jetbrains.kotlin.com.intellij.util.ArrayUtil;
import org.jetbrains.kotlin.com.intellij.util.containers.CollectionFactory;
import org.jetbrains.kotlin.com.intellij.util.containers.HashingStrategy;

/* JADX INFO: loaded from: classes3.dex */
public class ObjectStubTree<T extends Stub> {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final Key<ObjectStubTree<?>> STUB_TO_TREE_REFERENCE = Key.create("stub to tree reference");
    private String myDebugInfo;
    private boolean myHasBackReference;
    private final List<T> myPlainList;
    protected final ObjectStubBase<?> myRoot;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 1 || i == 2 || i == 3 || i == 4 || i == 6) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 1 || i == 2 || i == 3 || i == 4 || i == 6) ? 2 : 3];
        if (i == 1 || i == 2 || i == 3 || i == 4 || i == 6) {
            objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/stubs/ObjectStubTree";
        } else if (i == 8) {
            objArr[0] = "result";
        } else if (i != 9) {
            objArr[0] = "root";
        } else {
            objArr[0] = CompilerOptions.INFO;
        }
        if (i == 1) {
            objArr[1] = "getRoot";
        } else if (i == 2) {
            objArr[1] = "getPlainList";
        } else if (i == 3) {
            objArr[1] = "getPlainListFromAllRoots";
        } else if (i == 4) {
            objArr[1] = "indexStubTree";
        } else if (i != 6) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/stubs/ObjectStubTree";
        } else {
            objArr[1] = "enumerateStubs";
        }
        switch (i) {
            case 1:
            case 2:
            case 3:
            case 4:
            case 6:
                break;
            case 5:
                objArr[2] = "enumerateStubs";
                break;
            case 7:
            case 8:
                objArr[2] = "enumerateStubsInto";
                break;
            case 9:
                objArr[2] = "setDebugInfo";
                break;
            case 10:
                objArr[2] = "getStubTree";
                break;
            default:
                objArr[2] = "<init>";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i != 1 && i != 2 && i != 3 && i != 4 && i != 6) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    public ObjectStubTree(ObjectStubBase<?> objectStubBase, boolean z) {
        if (objectStubBase == null) {
            $$$reportNull$$$0(0);
        }
        this.myRoot = objectStubBase;
        this.myPlainList = enumerateStubs(objectStubBase);
        if (z) {
            objectStubBase.putUserData(STUB_TO_TREE_REFERENCE, this);
        }
    }

    public Stub getRoot() {
        ObjectStubBase<?> objectStubBase = this.myRoot;
        if (objectStubBase == null) {
            $$$reportNull$$$0(1);
        }
        return objectStubBase;
    }

    public List<T> getPlainList() {
        List<T> list = this.myPlainList;
        if (list == null) {
            $$$reportNull$$$0(2);
        }
        return list;
    }

    List<T> getPlainListFromAllRoots() {
        List<T> plainList = getPlainList();
        if (plainList == null) {
            $$$reportNull$$$0(3);
        }
        return plainList;
    }

    public Map<StubIndexKey<?, ?>, Map<Object, int[]>> indexStubTree(Function<? super StubIndexKey<?, ?>, ? extends HashingStrategy<Object>> function) {
        StubIndexSink stubIndexSink = new StubIndexSink(function);
        List<T> plainListFromAllRoots = getPlainListFromAllRoots();
        int size = plainListFromAllRoots.size();
        for (int i = 0; i < size; i++) {
            T t = plainListFromAllRoots.get(i);
            stubIndexSink.myStubIdx = i;
            StubSerializationUtil.getSerializer(t).indexStub(t, stubIndexSink);
        }
        Map<StubIndexKey<?, ?>, Map<Object, int[]>> result = stubIndexSink.getResult();
        if (result == null) {
            $$$reportNull$$$0(4);
        }
        return result;
    }

    protected List<T> enumerateStubs(Stub stub) {
        if (stub == null) {
            $$$reportNull$$$0(5);
        }
        ArrayList arrayList = new ArrayList();
        enumerateStubsInto(stub, arrayList);
        return arrayList;
    }

    private static void enumerateStubsInto(Stub stub, List<? super Stub> list) {
        if (stub == null) {
            $$$reportNull$$$0(7);
        }
        if (list == null) {
            $$$reportNull$$$0(8);
        }
        ((ObjectStubBase) stub).f717id = list.size();
        list.add(stub);
        List<? extends Stub> childrenStubs = stub.getChildrenStubs();
        for (int i = 0; i < childrenStubs.size(); i++) {
            enumerateStubsInto(childrenStubs.get(i), list);
        }
    }

    public void setDebugInfo(String str) {
        if (str == null) {
            $$$reportNull$$$0(9);
        }
        this.myHasBackReference = getStubTree(this.myRoot) != null;
        this.myDebugInfo = str;
    }

    public static ObjectStubTree getStubTree(ObjectStubBase objectStubBase) {
        if (objectStubBase == null) {
            $$$reportNull$$$0(10);
        }
        return (ObjectStubTree) objectStubBase.getUserData(STUB_TO_TREE_REFERENCE);
    }

    public String getDebugInfo() {
        return this.myHasBackReference ? this.myDebugInfo + "; with backReference" : this.myDebugInfo;
    }

    public String toString() {
        return getClass().getSimpleName() + "{myDebugInfo='" + getDebugInfo() + "', myRoot=" + this.myRoot + '}' + hashCode();
    }

    private static final class StubIndexSink implements IndexSink {
        private final Function<? super StubIndexKey<?, ?>, ? extends HashingStrategy<Object>> myHashingStrategyFunction;
        private final Map<StubIndexKey<?, ?>, Map<Object, int[]>> myResult;
        private int myStubIdx;

        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            String str = i != 2 ? "Argument for @NotNull parameter '%s' of %s.%s must not be null" : "@NotNull method %s.%s must not return null";
            Object[] objArr = new Object[i != 2 ? 3 : 2];
            if (i == 1) {
                objArr[0] = "value";
            } else if (i != 2) {
                objArr[0] = "indexKey";
            } else {
                objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/stubs/ObjectStubTree$StubIndexSink";
            }
            if (i != 2) {
                objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/stubs/ObjectStubTree$StubIndexSink";
            } else {
                objArr[1] = "getResult";
            }
            if (i != 2) {
                objArr[2] = "occurrence";
            }
            String str2 = String.format(str, objArr);
            if (i == 2) {
                throw new IllegalStateException(str2);
            }
        }

        private StubIndexSink(Function<? super StubIndexKey<?, ?>, ? extends HashingStrategy<Object>> function) {
            this.myResult = new HashMap();
            this.myHashingStrategyFunction = function;
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.stubs.IndexSink
        public void occurrence(StubIndexKey stubIndexKey, Object obj) {
            if (stubIndexKey == null) {
                $$$reportNull$$$0(0);
            }
            if (obj == null) {
                $$$reportNull$$$0(1);
            }
            Map<Object, int[]> map = this.myResult.get(stubIndexKey);
            if (map == null) {
                Function<? super StubIndexKey<?, ?>, ? extends HashingStrategy<Object>> function = this.myHashingStrategyFunction;
                map = function == null ? new HashMap<>() : CollectionFactory.createCustomHashingStrategyMap(function.apply(stubIndexKey));
                this.myResult.put(stubIndexKey, map);
            }
            int[] iArrRealloc = map.get(obj);
            if (iArrRealloc == null) {
                map.put(obj, new int[]{this.myStubIdx});
                return;
            }
            int iLastIndexOfNot = ArrayUtil.lastIndexOfNot(iArrRealloc, 0);
            if (iLastIndexOfNot < 0 || iArrRealloc[iLastIndexOfNot] != this.myStubIdx) {
                int i = iLastIndexOfNot + 1;
                if (i == iArrRealloc.length) {
                    iArrRealloc = ArrayUtil.realloc(iArrRealloc, Math.max(4, iArrRealloc.length << 1));
                    map.put(obj, iArrRealloc);
                }
                iArrRealloc[i] = this.myStubIdx;
            }
        }

        public Map<StubIndexKey<?, ?>, Map<Object, int[]>> getResult() {
            int iIndexOf;
            for (Map<Object, int[]> map : this.myResult.values()) {
                for (Map.Entry<Object, int[]> entry : map.entrySet()) {
                    int[] value = entry.getValue();
                    if (value.length != 1 && (iIndexOf = ArrayUtil.indexOf(value, 0)) != -1) {
                        map.put(entry.getKey(), ArrayUtil.realloc(value, iIndexOf));
                    }
                }
            }
            Map<StubIndexKey<?, ?>, Map<Object, int[]>> map2 = this.myResult;
            if (map2 == null) {
                $$$reportNull$$$0(2);
            }
            return map2;
        }
    }
}
