package org.jetbrains.kotlin.com.intellij.util.containers;

import com.reandroid.archive.PathTree;
import java.io.Serializable;
import java.util.AbstractList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.RandomAccess;
import org.jetbrains.kotlin.com.intellij.util.ArrayUtilRt;

/* JADX INFO: loaded from: classes3.dex */
@Deprecated
public final class ContainerUtilRt {
    /* JADX WARN: Code duplicated, block: B:26:0x0041  */
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 1 || i == 3 || i == 6 || i == 8 || i == 11) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 1 || i == 3 || i == 6 || i == 8 || i == 11) ? 2 : 3];
        if (i != 1 && i != 3 && i != 6) {
            switch (i) {
                case 8:
                case 11:
                    objArr[0] = "org/jetbrains/kotlin/com/intellij/util/containers/ContainerUtilRt";
                    break;
                case 9:
                    objArr[0] = "collection";
                    break;
                case 10:
                    objArr[0] = "mapper";
                    break;
                default:
                    objArr[0] = PathTree.NAME_elements;
                    break;
            }
        } else {
            objArr[0] = "org/jetbrains/kotlin/com/intellij/util/containers/ContainerUtilRt";
        }
        if (i == 1) {
            objArr[1] = "newLinkedList";
        } else if (i == 3) {
            objArr[1] = "newArrayList";
        } else if (i == 6) {
            objArr[1] = "newHashSet";
        } else if (i == 8) {
            objArr[1] = "emptyList";
        } else if (i != 11) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/util/containers/ContainerUtilRt";
        } else {
            objArr[1] = "map2List";
        }
        switch (i) {
            case 1:
            case 3:
            case 6:
            case 8:
            case 11:
                break;
            case 2:
                objArr[2] = "newArrayList";
                break;
            case 4:
            case 5:
                objArr[2] = "newHashSet";
                break;
            case 7:
                objArr[2] = "newLinkedHashSet";
                break;
            case 9:
            case 10:
                objArr[2] = "map2List";
                break;
            default:
                objArr[2] = "newLinkedList";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i != 1 && i != 3 && i != 6 && i != 8 && i != 11) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    private static final class EmptyList<T> extends AbstractList<T> implements Serializable, RandomAccess {
        private static final EmptyList<?> INSTANCE = new EmptyList<>();
        private static final long serialVersionUID = 1;

        /* JADX WARN: Code duplicated, block: B:27:0x0041  */
        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            String str = (i == 1 || i == 5) ? "Argument for @NotNull parameter '%s' of %s.%s must not be null" : "@NotNull method %s.%s must not return null";
            Object[] objArr = new Object[(i == 1 || i == 5) ? 3 : 2];
            if (i == 1) {
                objArr[0] = "a";
            } else if (i != 5) {
                objArr[0] = "org/jetbrains/kotlin/com/intellij/util/containers/ContainerUtilRt$EmptyList";
            } else {
                objArr[0] = "c";
            }
            if (i == 1) {
                objArr[1] = "org/jetbrains/kotlin/com/intellij/util/containers/ContainerUtilRt$EmptyList";
            } else if (i == 3) {
                objArr[1] = "iterator";
            } else if (i == 4) {
                objArr[1] = "listIterator";
            } else if (i != 5) {
                objArr[1] = "toArray";
            } else {
                objArr[1] = "org/jetbrains/kotlin/com/intellij/util/containers/ContainerUtilRt$EmptyList";
            }
            if (i == 1) {
                objArr[2] = "toArray";
            } else if (i == 5) {
                objArr[2] = "containsAll";
            }
            String str2 = String.format(str, objArr);
            if (i != 1 && i != 5) {
                throw new IllegalStateException(str2);
            }
            throw new IllegalArgumentException(str2);
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
        public boolean contains(Object obj) {
            return false;
        }

        @Override // java.util.AbstractList, java.util.Collection, java.util.List
        public int hashCode() {
            return 1;
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
        public boolean isEmpty() {
            return true;
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
        public int size() {
            return 0;
        }

        private EmptyList() {
        }

        @Override // java.util.AbstractList, java.util.List
        public T get(int i) {
            throw new IndexOutOfBoundsException("Index: " + i);
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
        public Object[] toArray() {
            Object[] objArr = ArrayUtilRt.EMPTY_OBJECT_ARRAY;
            if (objArr == null) {
                $$$reportNull$$$0(0);
            }
            return objArr;
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
        public <E> E[] toArray(E[] eArr) {
            if (eArr == null) {
                $$$reportNull$$$0(1);
            }
            if (eArr.length != 0) {
                eArr[0] = null;
            }
            if (eArr == null) {
                $$$reportNull$$$0(2);
            }
            return eArr;
        }

        @Override // java.util.AbstractList, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, java.util.List
        public Iterator<T> iterator() {
            EmptyIterator emptyIterator = EmptyIterator.getInstance();
            if (emptyIterator == null) {
                $$$reportNull$$$0(3);
            }
            return emptyIterator;
        }

        @Override // java.util.AbstractList, java.util.List
        public ListIterator<T> listIterator() {
            EmptyListIterator emptyListIterator = EmptyListIterator.getInstance();
            if (emptyListIterator == null) {
                $$$reportNull$$$0(4);
            }
            return emptyListIterator;
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
        public boolean containsAll(Collection<?> collection) {
            if (collection == null) {
                $$$reportNull$$$0(5);
            }
            return collection.isEmpty();
        }

        @Override // java.util.AbstractList, java.util.Collection, java.util.List
        public boolean equals(Object obj) {
            return (obj instanceof List) && ((List) obj).isEmpty();
        }
    }

    @Deprecated
    public static <T> List<T> emptyList() {
        EmptyList emptyList = EmptyList.INSTANCE;
        if (emptyList == null) {
            $$$reportNull$$$0(8);
        }
        return emptyList;
    }
}
