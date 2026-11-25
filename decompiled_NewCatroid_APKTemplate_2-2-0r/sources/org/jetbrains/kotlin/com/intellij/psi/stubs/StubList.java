package org.jetbrains.kotlin.com.intellij.psi.stubs;

import java.util.AbstractList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.function.IntUnaryOperator;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.tree.IElementType;
import org.jetbrains.kotlin.com.intellij.util.containers.ContainerUtil;
import org.jetbrains.kotlin.com.intellij.util.containers.IntObjectMap;

/* JADX INFO: loaded from: classes3.dex */
abstract class StubList extends AbstractList<StubBase<?>> {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private final MostlyUShortIntList myJoinedChildrenList;
    private final MostlyUShortIntList myStubData;
    private TempState myTempState = new TempState();

    private enum ChildrenStorage {
        inPlainList,
        inJoinedList,
        inTempMap
    }

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 2 || i == 3) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 2 || i == 3) ? 2 : 3];
        if (i == 1) {
            objArr[0] = "elementType";
        } else if (i == 2 || i == 3) {
            objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/stubs/StubList";
        } else {
            objArr[0] = "stub";
        }
        if (i == 2) {
            objArr[1] = "finalizeLoadingStage";
        } else if (i != 3) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/stubs/StubList";
        } else {
            objArr[1] = "toPlainList";
        }
        if (i == 1) {
            objArr[2] = "findChildStubByType";
        } else if (i != 2 && i != 3) {
            objArr[2] = "addStub";
        }
        String str2 = String.format(str, objArr);
        if (i != 2 && i != 3) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    private static int childrenCountIndex(int i) {
        return (i * 3) + 2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static int childrenStartIndex(int i) {
        return (i * 3) + 1;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public List<StubElement<?>> toPlainList() {
        return this;
    }

    StubList(int i) {
        this.myStubData = new MostlyUShortIntList(i * 3);
        MostlyUShortIntList mostlyUShortIntList = new MostlyUShortIntList(i);
        this.myJoinedChildrenList = mostlyUShortIntList;
        mostlyUShortIntList.add(0);
    }

    IStubElementType<?, ?> getStubType(int i) {
        return (IStubElementType) IElementType.find(getStubTypeIndex(i));
    }

    short getStubTypeIndex(int i) {
        return (short) this.myStubData.get(i * 3);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int getChildrenStart(int i) {
        return this.myStubData.get(childrenStartIndex(i));
    }

    int getChildrenCount(int i) {
        return this.myStubData.get(childrenCountIndex(i));
    }

    void addStub(StubBase<?> stubBase, StubBase<?> stubBase2, IStubElementType<?, ?> iStubElementType) {
        if (stubBase == null) {
            $$$reportNull$$$0(0);
        }
        int size = size();
        stubBase.f717id = size;
        int i = stubBase2 == null ? -1 : stubBase2.f717id;
        if (nonDfsOrderDetected(i, size)) {
            ((TempState) Objects.requireNonNull(this.myTempState)).switchChildrenToTempMap(i);
        }
        addStub(size, i, iStubElementType != null ? iStubElementType.getIndex() : (short) 0);
    }

    private boolean nonDfsOrderDetected(int i, int i2) {
        return i >= 0 && i2 != i + 1 && getChildrenCount(i) == 0;
    }

    void addStub(int i, int i2, short s) {
        this.myStubData.add(s);
        this.myStubData.add(0);
        this.myStubData.add(0);
        if (i == 0) {
            return;
        }
        int childrenCount = getChildrenCount(i2);
        int iEnsureCapacityForNextChild = this.myTempState.ensureCapacityForNextChild(i, i2, childrenCount);
        ChildrenStorage childrenStorage = getChildrenStorage(iEnsureCapacityForNextChild);
        if (childrenStorage == ChildrenStorage.inJoinedList) {
            addToJoinedChildren(iEnsureCapacityForNextChild + childrenCount, i);
        } else if (childrenStorage == ChildrenStorage.inTempMap) {
            tempMap().get(i2).add(i);
        }
        this.myStubData.set(childrenCountIndex(i2), childrenCount + 1);
    }

    private int getParentIndex(int i) {
        return ((StubBase) get(i).getParentStub()).f717id;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static ChildrenStorage getChildrenStorage(int i) {
        if (i == 0) {
            return ChildrenStorage.inPlainList;
        }
        if (i == -1) {
            return ChildrenStorage.inTempMap;
        }
        return ChildrenStorage.inJoinedList;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean canAddToJoinedList(int i) {
        return this.myJoinedChildrenList.size() == i || this.myJoinedChildrenList.get(i) == 0;
    }

    private void addToJoinedChildren(int i, int i2) {
        if (this.myJoinedChildrenList.size() == i) {
            this.myJoinedChildrenList.add(i2);
        } else {
            this.myJoinedChildrenList.set(i, i2);
        }
    }

    void prepareForChildren(int i, int i2) {
        this.myTempState.prepareForChildren(i, i2);
    }

    List<StubBase<?>> getChildrenStubs(int i) {
        int childrenCount = getChildrenCount(i);
        if (childrenCount == 0) {
            return Collections.emptyList();
        }
        int childrenStart = getChildrenStart(i);
        int i2 = AnonymousClass2.$SwitchMap$com$intellij$psi$stubs$StubList$ChildrenStorage[getChildrenStorage(childrenStart).ordinal()];
        if (i2 == 1) {
            int i3 = i + 1;
            return subList(i3, childrenCount + i3);
        }
        if (i2 == 2) {
            return idSubList(this.myJoinedChildrenList, childrenStart, childrenCount);
        }
        return idSubList(tempMap().get(i), 0, childrenCount);
    }

    /* JADX INFO: renamed from: org.jetbrains.kotlin.com.intellij.psi.stubs.StubList$2, reason: invalid class name */
    static /* synthetic */ class AnonymousClass2 {
        static final /* synthetic */ int[] $SwitchMap$com$intellij$psi$stubs$StubList$ChildrenStorage;

        static {
            int[] iArr = new int[ChildrenStorage.values().length];
            $SwitchMap$com$intellij$psi$stubs$StubList$ChildrenStorage = iArr;
            try {
                iArr[ChildrenStorage.inPlainList.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$intellij$psi$stubs$StubList$ChildrenStorage[ChildrenStorage.inJoinedList.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
        }
    }

    private List<StubBase<?>> idSubList(final MostlyUShortIntList mostlyUShortIntList, final int i, final int i2) {
        return new AbstractList<StubBase<?>>() { // from class: org.jetbrains.kotlin.com.intellij.psi.stubs.StubList.1
            @Override // java.util.AbstractList, java.util.List
            public StubBase<?> get(int i3) {
                if (i3 < 0 || i3 >= i2) {
                    throw new IndexOutOfBoundsException("index=" + i3 + ", size=" + i2);
                }
                return StubList.this.get(mostlyUShortIntList.get(i + i3));
            }

            @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
            public int size() {
                return i2;
            }
        };
    }

    private IntObjectMap<MostlyUShortIntList> tempMap() {
        return (IntObjectMap) Objects.requireNonNull(this.myTempState.myTempJoinedChildrenMap);
    }

    <P extends PsiElement, S extends StubElement<P>> S findChildStubByType(int i, IStubElementType<S, P> iStubElementType) {
        if (iStubElementType == null) {
            $$$reportNull$$$0(1);
        }
        int childrenCount = getChildrenCount(i);
        int childrenStart = getChildrenStart(i);
        int i2 = AnonymousClass2.$SwitchMap$com$intellij$psi$stubs$StubList$ChildrenStorage[getChildrenStorage(childrenStart).ordinal()];
        if (i2 == 1) {
            int i3 = i + 1;
            return (S) findChildStubByType(iStubElementType, IntUnaryOperator.identity(), i3, childrenCount + i3);
        }
        if (i2 == 2) {
            return (S) findChildStubByType(iStubElementType, this.myJoinedChildrenList, childrenStart, childrenCount + childrenStart);
        }
        return (S) findChildStubByType(iStubElementType, (IntUnaryOperator) ((IntObjectMap) Objects.requireNonNull(tempMap())).get(i), 0, childrenCount);
    }

    private <P extends PsiElement, S extends StubElement<P>> S findChildStubByType(IStubElementType<S, P> iStubElementType, IntUnaryOperator intUnaryOperator, int i, int i2) {
        while (i < i2) {
            int iApplyAsInt = intUnaryOperator.applyAsInt(i);
            if (iStubElementType.getIndex() == getStubTypeIndex(iApplyAsInt)) {
                return get(iApplyAsInt);
            }
            i++;
        }
        return null;
    }

    StubList finalizeLoadingStage() {
        if (this.myTempState != null) {
            this.myTempState = null;
            this.myJoinedChildrenList.trimToSize();
            this.myStubData.trimToSize();
        }
        return this;
    }

    boolean isChildrenLayoutOptimal() {
        TempState tempState = this.myTempState;
        return tempState == null || tempState.myTempJoinedChildrenMap == null;
    }

    boolean areChildrenNonAdjacent(int i, int i2) {
        return getParentIndex(i - 1) != i2;
    }

    private final class TempState {
        static final /* synthetic */ boolean $assertionsDisabled = false;
        int myCurrentParent;
        int myExpectedChildrenCount;
        IntObjectMap<MostlyUShortIntList> myTempJoinedChildrenMap;

        private TempState() {
            this.myCurrentParent = -1;
        }

        int ensureCapacityForNextChild(int i, int i2, int i3) {
            int i4 = this.myCurrentParent;
            if (i4 >= 0) {
                int i5 = this.myExpectedChildrenCount;
                if (i3 == i5 - 1) {
                    this.myCurrentParent = -1;
                } else if (i2 != i4) {
                    this.myCurrentParent = -1;
                    return switchChildrenToJoinedList(i2, i3, i5 - i3);
                }
            }
            int childrenStart = StubList.this.getChildrenStart(i2);
            ChildrenStorage childrenStorage = StubList.getChildrenStorage(childrenStart);
            if (childrenStorage == ChildrenStorage.inPlainList) {
                if (i3 != 0 && StubList.this.areChildrenNonAdjacent(i, i2)) {
                    return switchChildrenToJoinedList(i2, i3, 0);
                }
            } else if (childrenStorage == ChildrenStorage.inJoinedList && !StubList.this.canAddToJoinedList(i3 + childrenStart)) {
                switchChildrenToTempMap(i2);
                return -1;
            }
            return childrenStart;
        }

        private int switchChildrenToJoinedList(int i, int i2, int i3) {
            int size = StubList.this.myJoinedChildrenList.size();
            for (int i4 = 0; i4 < i2; i4++) {
                StubList.this.myJoinedChildrenList.add(i + i4 + 1);
            }
            for (int i5 = 0; i5 < i3; i5++) {
                StubList.this.myJoinedChildrenList.add(0);
            }
            StubList.this.myStubData.set(StubList.childrenStartIndex(i), size);
            return size;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void switchChildrenToTempMap(int i) {
            if (this.myTempJoinedChildrenMap == null) {
                this.myTempJoinedChildrenMap = ContainerUtil.createConcurrentIntObjectMap();
            }
            int childrenStart = StubList.this.getChildrenStart(i);
            int childrenCount = StubList.this.getChildrenCount(i);
            MostlyUShortIntList mostlyUShortIntList = new MostlyUShortIntList(childrenCount + 1);
            int i2 = AnonymousClass2.$SwitchMap$com$intellij$psi$stubs$StubList$ChildrenStorage[StubList.getChildrenStorage(childrenStart).ordinal()];
            if (i2 == 1) {
                for (int i3 = 0; i3 < childrenCount; i3++) {
                    mostlyUShortIntList.add(i + i3 + 1);
                }
            } else {
                if (i2 != 2) {
                    throw new IllegalStateException();
                }
                for (int i4 = childrenStart; i4 < childrenStart + childrenCount; i4++) {
                    mostlyUShortIntList.add(StubList.this.myJoinedChildrenList.get(i4));
                }
            }
            this.myTempJoinedChildrenMap.put(i, mostlyUShortIntList);
            StubList.this.myStubData.set(StubList.childrenStartIndex(i), -1);
        }

        void prepareForChildren(int i, int i2) {
            if (i2 == 0) {
                return;
            }
            int i3 = this.myCurrentParent;
            if (i3 >= 0) {
                int childrenCount = StubList.this.getChildrenCount(i3);
                switchChildrenToJoinedList(this.myCurrentParent, childrenCount, this.myExpectedChildrenCount - childrenCount);
            }
            this.myCurrentParent = i;
            this.myExpectedChildrenCount = i2;
        }
    }
}
