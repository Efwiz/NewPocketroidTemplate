package org.jf.dexlib2.analysis;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.function.Predicate;
import java.util.function.Supplier;
import net.bytebuddy.implementation.auxiliary.TypeProxy;
import org.jf.dexlib2.AccessFlags;
import org.jf.dexlib2.HiddenApiRestriction;
import org.jf.dexlib2.analysis.util.TypeProtoUtils;
import org.jf.dexlib2.base.reference.BaseMethodReference;
import org.jf.dexlib2.iface.Annotation;
import org.jf.dexlib2.iface.ClassDef;
import org.jf.dexlib2.iface.Field;
import org.jf.dexlib2.iface.Method;
import org.jf.dexlib2.iface.MethodImplementation;
import org.jf.dexlib2.iface.MethodParameter;
import org.jf.dexlib2.iface.reference.FieldReference;
import org.jf.dexlib2.iface.reference.MethodReference;
import org.jf.dexlib2.util.AlignmentUtils;
import org.jf.dexlib2.util.MethodUtil;
import org.jf.util.ExceptionWithContext;
import org.jf.util.SparseArray;
import org.jf.util.StringUtils;
import org.jf.util.base.Suppliers;
import org.jf.util.collection.EmptySet;
import org.jf.util.collection.Iterables;
import org.jf.util.collection.ListUtil;

/* JADX INFO: loaded from: classes6.dex */
public class ClassProto implements TypeProto {
    private static final byte OTHER = 2;
    private static final byte REFERENCE = 0;
    private static final byte WIDE = 1;
    protected final ClassPath classPath;
    protected final String type;
    protected boolean vtableFullyResolved = true;
    protected boolean interfacesFullyResolved = true;
    protected Set<String> unresolvedInterfaces = null;
    private final Supplier<ClassDef> classDefSupplier = Suppliers.memoize(new Supplier<ClassDef>() { // from class: org.jf.dexlib2.analysis.ClassProto.1
        @Override // java.util.function.Supplier
        public ClassDef get() {
            return ClassProto.this.classPath.getClassDef(ClassProto.this.type);
        }
    });
    private final Supplier<LinkedHashMap<String, ClassDef>> preDefaultMethodInterfaceSupplier = Suppliers.memoize(new Supplier<LinkedHashMap<String, ClassDef>>() { // from class: org.jf.dexlib2.analysis.ClassProto.2
        @Override // java.util.function.Supplier
        public LinkedHashMap<String, ClassDef> get() {
            HashSet hashSet = new HashSet(0);
            LinkedHashMap<String, ClassDef> linkedHashMap = new LinkedHashMap<>();
            try {
                for (String str : ClassProto.this.getClassDef().getInterfaces()) {
                    if (!linkedHashMap.containsKey(str)) {
                        try {
                            linkedHashMap.put(str, ClassProto.this.classPath.getClassDef(str));
                        } catch (UnresolvedClassException unused) {
                            linkedHashMap.put(str, null);
                            hashSet.add(str);
                            ClassProto.this.interfacesFullyResolved = false;
                        }
                        ClassProto classProto = (ClassProto) ClassProto.this.classPath.getClass(str);
                        for (String str2 : classProto.getInterfaces().keySet()) {
                            if (!linkedHashMap.containsKey(str2)) {
                                linkedHashMap.put(str2, classProto.getInterfaces().get(str2));
                            }
                        }
                        if (!classProto.interfacesFullyResolved) {
                            hashSet.addAll(classProto.getUnresolvedInterfaces());
                            ClassProto.this.interfacesFullyResolved = false;
                        }
                    }
                }
            } catch (UnresolvedClassException unused2) {
                linkedHashMap.put(ClassProto.this.type, null);
                hashSet.add(ClassProto.this.type);
                ClassProto.this.interfacesFullyResolved = false;
            }
            if (ClassProto.this.isInterface() && !linkedHashMap.containsKey(ClassProto.this.getType())) {
                linkedHashMap.put(ClassProto.this.getType(), null);
            }
            String superclass = ClassProto.this.getSuperclass();
            if (superclass != null) {
                try {
                    ClassProto classProto2 = (ClassProto) ClassProto.this.classPath.getClass(superclass);
                    for (String str3 : classProto2.getInterfaces().keySet()) {
                        if (!linkedHashMap.containsKey(str3)) {
                            linkedHashMap.put(str3, null);
                        }
                    }
                    if (!classProto2.interfacesFullyResolved) {
                        hashSet.addAll(classProto2.getUnresolvedInterfaces());
                        ClassProto.this.interfacesFullyResolved = false;
                    }
                } catch (UnresolvedClassException unused3) {
                    hashSet.add(superclass);
                    ClassProto.this.interfacesFullyResolved = false;
                }
            }
            if (hashSet.size() > 0) {
                ClassProto.this.unresolvedInterfaces = hashSet;
            }
            return linkedHashMap;
        }
    });
    private final Supplier<LinkedHashMap<String, ClassDef>> postDefaultMethodInterfaceSupplier = Suppliers.memoize(new Supplier<LinkedHashMap<String, ClassDef>>() { // from class: org.jf.dexlib2.analysis.ClassProto.3
        @Override // java.util.function.Supplier
        public LinkedHashMap<String, ClassDef> get() {
            HashSet hashSet = new HashSet(0);
            LinkedHashMap<String, ClassDef> linkedHashMap = new LinkedHashMap<>();
            String superclass = ClassProto.this.getSuperclass();
            if (superclass != null) {
                ClassProto classProto = (ClassProto) ClassProto.this.classPath.getClass(superclass);
                Iterator<String> it = classProto.getInterfaces().keySet().iterator();
                while (it.hasNext()) {
                    linkedHashMap.put(it.next(), null);
                }
                if (!classProto.interfacesFullyResolved) {
                    hashSet.addAll(classProto.getUnresolvedInterfaces());
                    ClassProto.this.interfacesFullyResolved = false;
                }
            }
            try {
                for (String str : ClassProto.this.getClassDef().getInterfaces()) {
                    if (!linkedHashMap.containsKey(str)) {
                        ClassProto classProto2 = (ClassProto) ClassProto.this.classPath.getClass(str);
                        try {
                            for (Map.Entry<String, ClassDef> entry : classProto2.getInterfaces().entrySet()) {
                                if (!linkedHashMap.containsKey(entry.getKey())) {
                                    linkedHashMap.put(entry.getKey(), entry.getValue());
                                }
                            }
                        } catch (UnresolvedClassException unused) {
                            linkedHashMap.put(str, null);
                            hashSet.add(str);
                            ClassProto.this.interfacesFullyResolved = false;
                        }
                        if (!classProto2.interfacesFullyResolved) {
                            hashSet.addAll(classProto2.getUnresolvedInterfaces());
                            ClassProto.this.interfacesFullyResolved = false;
                        }
                        try {
                            linkedHashMap.put(str, ClassProto.this.classPath.getClassDef(str));
                        } catch (UnresolvedClassException unused2) {
                            linkedHashMap.put(str, null);
                            hashSet.add(str);
                            ClassProto.this.interfacesFullyResolved = false;
                        }
                    }
                }
            } catch (UnresolvedClassException unused3) {
                linkedHashMap.put(ClassProto.this.type, null);
                hashSet.add(ClassProto.this.type);
                ClassProto.this.interfacesFullyResolved = false;
            }
            if (hashSet.size() > 0) {
                ClassProto.this.unresolvedInterfaces = hashSet;
            }
            return linkedHashMap;
        }
    });
    private final Supplier<SparseArray<FieldReference>> dalvikInstanceFieldsSupplier = Suppliers.memoize(new Supplier<SparseArray<FieldReference>>() { // from class: org.jf.dexlib2.analysis.ClassProto.4
        static final /* synthetic */ boolean $assertionsDisabled = false;

        @Override // java.util.function.Supplier
        public SparseArray<FieldReference> get() {
            ClassProto classProto;
            int nextFieldOffset;
            SparseArray<FieldReference> sparseArray;
            ArrayList<Field> sortedInstanceFields = getSortedInstanceFields(ClassProto.this.getClassDef());
            int size = sortedInstanceFields.size();
            byte[] bArr = new byte[sortedInstanceFields.size()];
            for (int i = 0; i < size; i++) {
                bArr[i] = ClassProto.getFieldType(sortedInstanceFields.get(i));
            }
            int size2 = sortedInstanceFields.size() - 1;
            int i2 = 0;
            while (i2 < size) {
                if (bArr[i2] != 0) {
                    while (size2 > i2) {
                        if (bArr[size2] == 0) {
                            swap(bArr, sortedInstanceFields, i2, size2);
                            size2--;
                            break;
                        }
                        size2--;
                    }
                }
                if (bArr[i2] != 0) {
                    break;
                }
                i2++;
            }
            String superclass = ClassProto.this.getSuperclass();
            int i3 = 8;
            if (superclass != null) {
                classProto = (ClassProto) ClassProto.this.classPath.getClass(superclass);
                nextFieldOffset = classProto.getNextFieldOffset();
            } else {
                classProto = null;
                nextFieldOffset = 8;
            }
            int i4 = nextFieldOffset % 8 == 0 ? 0 : 1;
            if (i2 < size && i2 % 2 != i4) {
                if (bArr[i2] == 1) {
                    for (int i5 = size - 1; i5 > i2; i5--) {
                        if (bArr[i5] == 2) {
                            swap(bArr, sortedInstanceFields, i2, i5);
                            i2++;
                            break;
                        }
                    }
                } else {
                    i2++;
                }
            }
            int i6 = size - 1;
            while (i2 < size) {
                if (bArr[i2] != 1) {
                    while (i6 > i2) {
                        if (bArr[i6] == 1) {
                            swap(bArr, sortedInstanceFields, i2, i6);
                            i6--;
                            break;
                        }
                        i6--;
                    }
                }
                if (bArr[i2] != 1) {
                    break;
                }
                i2++;
            }
            if (classProto != null) {
                sparseArray = classProto.getInstanceFields();
            } else {
                sparseArray = new SparseArray<>();
            }
            int size3 = sparseArray.size();
            SparseArray<FieldReference> sparseArray2 = new SparseArray<>(size3 + size);
            if (classProto != null && size3 > 0) {
                for (int i7 = 0; i7 < size3; i7++) {
                    sparseArray2.append(sparseArray.keyAt(i7), sparseArray.valueAt(i7));
                }
                int i8 = size3 - 1;
                int iKeyAt = sparseArray2.keyAt(i8);
                char cCharAt = sparseArray.valueAt(i8).getType().charAt(0);
                i3 = (cCharAt == 'J' || cCharAt == 'D') ? iKeyAt + 8 : iKeyAt + 4;
            }
            boolean z = false;
            for (int i9 = 0; i9 < size; i9++) {
                Field field = sortedInstanceFields.get(i9);
                if (bArr[i9] == 1 && !z) {
                    if (i3 % 8 != 0) {
                        i3 += 4;
                    }
                    z = true;
                }
                sparseArray2.append(i3, field);
                i3 = bArr[i9] == 1 ? i3 + 8 : i3 + 4;
            }
            return sparseArray2;
        }

        private ArrayList<Field> getSortedInstanceFields(ClassDef classDef) {
            ArrayList<Field> arrayListNewArrayList = ListUtil.newArrayList(classDef.getInstanceFields());
            Collections.sort(arrayListNewArrayList);
            return arrayListNewArrayList;
        }

        private void swap(byte[] bArr, List<Field> list, int i, int i2) {
            byte b = bArr[i];
            bArr[i] = bArr[i2];
            bArr[i2] = b;
            list.set(i2, list.set(i, list.get(i2)));
        }
    });
    private final Supplier<SparseArray<FieldReference>> artInstanceFieldsSupplier = Suppliers.memoize(new Supplier<SparseArray<FieldReference>>() { // from class: org.jf.dexlib2.analysis.ClassProto.5
        @Override // java.util.function.Supplier
        public SparseArray<FieldReference> get() {
            PriorityQueue<FieldGap> priorityQueue = new PriorityQueue<>();
            SparseArray<FieldReference> sparseArray = new SparseArray<>();
            ArrayList<Field> sortedInstanceFields = getSortedInstanceFields(ClassProto.this.getClassDef());
            String superclass = ClassProto.this.getSuperclass();
            int fieldSize = 0;
            if (superclass != null) {
                SparseArray<FieldReference> instanceFields = ((ClassProto) ClassProto.this.classPath.getClass(superclass)).getInstanceFields();
                FieldReference fieldReferenceValueAt = null;
                int iKeyAt = 0;
                for (int i = 0; i < instanceFields.size(); i++) {
                    iKeyAt = instanceFields.keyAt(i);
                    fieldReferenceValueAt = instanceFields.valueAt(i);
                    sparseArray.put(iKeyAt, fieldReferenceValueAt);
                }
                if (fieldReferenceValueAt != null) {
                    fieldSize = iKeyAt + getFieldSize(fieldReferenceValueAt);
                }
            }
            for (Field field : sortedInstanceFields) {
                int fieldSize2 = getFieldSize(field);
                if (!AlignmentUtils.isAligned(fieldSize, fieldSize2)) {
                    int iAlignOffset = AlignmentUtils.alignOffset(fieldSize, fieldSize2);
                    addFieldGap(fieldSize, iAlignOffset, priorityQueue);
                    fieldSize = iAlignOffset;
                }
                FieldGap fieldGapPeek = priorityQueue.peek();
                if (fieldGapPeek != null && fieldGapPeek.size >= fieldSize2) {
                    priorityQueue.poll();
                    sparseArray.put(fieldGapPeek.offset, field);
                    if (fieldGapPeek.size > fieldSize2) {
                        addFieldGap(fieldGapPeek.offset + fieldSize2, fieldGapPeek.offset + fieldGapPeek.size, priorityQueue);
                    }
                } else {
                    sparseArray.append(fieldSize, field);
                    fieldSize += fieldSize2;
                }
            }
            return sparseArray;
        }

        private void addFieldGap(int i, int i2, PriorityQueue<FieldGap> priorityQueue) {
            while (i < i2) {
                int i3 = i2 - i;
                if (i3 >= 4 && i % 4 == 0) {
                    priorityQueue.add(FieldGap.newFieldGap(i, 4, ClassProto.this.classPath.oatVersion));
                    i += 4;
                } else if (i3 >= 2 && i % 2 == 0) {
                    priorityQueue.add(FieldGap.newFieldGap(i, 2, ClassProto.this.classPath.oatVersion));
                    i += 2;
                } else {
                    priorityQueue.add(FieldGap.newFieldGap(i, 1, ClassProto.this.classPath.oatVersion));
                    i++;
                }
            }
        }

        private ArrayList<Field> getSortedInstanceFields(ClassDef classDef) {
            ArrayList<Field> arrayListNewArrayList = ListUtil.newArrayList(classDef.getInstanceFields());
            Collections.sort(arrayListNewArrayList, new Comparator<Field>() { // from class: org.jf.dexlib2.analysis.ClassProto.5.1
                @Override // java.util.Comparator
                public int compare(Field field, Field field2) {
                    int iCompare = Integer.compare(getFieldSortOrder(field), getFieldSortOrder(field2));
                    if (iCompare != 0) {
                        return iCompare;
                    }
                    int iCompareTo = field.getName().compareTo(field2.getName());
                    return iCompareTo != 0 ? iCompareTo : field.getType().compareTo(field2.getType());
                }
            });
            return arrayListNewArrayList;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public int getFieldSortOrder(FieldReference fieldReference) {
            char cCharAt = fieldReference.getType().charAt(0);
            if (cCharAt == 'F') {
                return 4;
            }
            if (cCharAt != 'L') {
                if (cCharAt == 'S') {
                    return 6;
                }
                if (cCharAt == 'I') {
                    return 3;
                }
                if (cCharAt == 'J') {
                    return 1;
                }
                if (cCharAt == 'Z') {
                    return 7;
                }
                if (cCharAt != '[') {
                    switch (cCharAt) {
                        case 'B':
                            return 8;
                        case 'C':
                            return 5;
                        case 'D':
                            return 2;
                        default:
                            throw new ExceptionWithContext("Invalid field type: %s", fieldReference.getType());
                    }
                }
            }
            return 0;
        }

        private int getFieldSize(FieldReference fieldReference) {
            return ClassProto.getTypeSize(fieldReference.getType().charAt(0));
        }
    });
    private final Supplier<List<Method>> preDefaultMethodVtableSupplier = Suppliers.memoize(new Supplier<List<Method>>() { // from class: org.jf.dexlib2.analysis.ClassProto.6
        @Override // java.util.function.Supplier
        public List<Method> get() {
            ArrayList arrayListNewArrayList = ListUtil.newArrayList();
            try {
                String superclass = ClassProto.this.getSuperclass();
                if (superclass != null) {
                    ClassProto classProto = (ClassProto) ClassProto.this.classPath.getClass(superclass);
                    arrayListNewArrayList.addAll(classProto.getVtable());
                    if (!classProto.vtableFullyResolved) {
                        ClassProto.this.vtableFullyResolved = false;
                        return arrayListNewArrayList;
                    }
                }
                if (!ClassProto.this.isInterface()) {
                    ClassProto classProto2 = ClassProto.this;
                    classProto2.addToVtable(classProto2.getClassDef().getVirtualMethods(), arrayListNewArrayList, true, true);
                    for (ClassDef classDef : ClassProto.this.getDirectInterfaces()) {
                        ArrayList arrayListNewArrayList2 = ListUtil.newArrayList();
                        Iterator<? extends Method> it = classDef.getVirtualMethods().iterator();
                        while (it.hasNext()) {
                            arrayListNewArrayList2.add(new ReparentedMethod(it.next(), ClassProto.this.type));
                        }
                        ClassProto.this.addToVtable(arrayListNewArrayList2, arrayListNewArrayList, false, true);
                    }
                }
                return arrayListNewArrayList;
            } catch (UnresolvedClassException unused) {
                arrayListNewArrayList.addAll(((ClassProto) ClassProto.this.classPath.getClass(TypeProxy.SilentConstruction.Appender.JAVA_LANG_OBJECT_DESCRIPTOR)).getVtable());
                ClassProto.this.vtableFullyResolved = false;
                return arrayListNewArrayList;
            }
        }
    });
    private final Supplier<List<Method>> buggyPostDefaultMethodVtableSupplier = Suppliers.memoize(new Supplier<List<Method>>() { // from class: org.jf.dexlib2.analysis.ClassProto.7
        @Override // java.util.function.Supplier
        public List<Method> get() {
            ArrayList arrayList;
            ArrayList arrayListNewArrayList = ListUtil.newArrayList();
            int i = 0;
            try {
                String superclass = ClassProto.this.getSuperclass();
                if (superclass != null) {
                    ClassProto classProto = (ClassProto) ClassProto.this.classPath.getClass(superclass);
                    arrayListNewArrayList.addAll(classProto.getVtable());
                    if (!classProto.vtableFullyResolved) {
                        ClassProto.this.vtableFullyResolved = false;
                        return arrayListNewArrayList;
                    }
                }
                if (!ClassProto.this.isInterface()) {
                    ClassProto classProto2 = ClassProto.this;
                    classProto2.addToVtable(classProto2.getClassDef().getVirtualMethods(), arrayListNewArrayList, true, true);
                    ArrayList arrayListNewArrayList2 = ListUtil.newArrayList((Collection) ClassProto.this.getInterfaces().keySet());
                    ArrayList arrayListNewArrayList3 = ListUtil.newArrayList();
                    ArrayList arrayListNewArrayList4 = ListUtil.newArrayList();
                    ArrayList arrayListNewArrayList5 = ListUtil.newArrayList();
                    final HashMap map = new HashMap();
                    int size = arrayListNewArrayList2.size() - 1;
                    while (size >= 0) {
                        for (Method method : ClassProto.this.classPath.getClassDef((String) arrayListNewArrayList2.get(size)).getVirtualMethods()) {
                            int iFindMethodIndexInVtableReverse = ClassProto.this.findMethodIndexInVtableReverse(arrayListNewArrayList, method);
                            Method method2 = iFindMethodIndexInVtableReverse >= 0 ? (Method) arrayListNewArrayList.get(iFindMethodIndexInVtableReverse) : null;
                            int i2 = i;
                            while (i2 < arrayListNewArrayList.size()) {
                                Method method3 = (Method) arrayListNewArrayList.get(i2);
                                if (MethodUtil.methodSignaturesMatch(method3, method)) {
                                    if (ClassProto.this.classPath.shouldCheckPackagePrivateAccess()) {
                                        arrayList = arrayListNewArrayList2;
                                        if (AnalyzedMethodUtil.canAccess(ClassProto.this, method3, true, false, false)) {
                                        }
                                    } else {
                                        arrayList = arrayListNewArrayList2;
                                    }
                                    if (ClassProto.this.interfaceMethodOverrides(method, method3)) {
                                        arrayListNewArrayList.set(i2, method);
                                    }
                                } else {
                                    arrayList = arrayListNewArrayList2;
                                }
                                i2++;
                                arrayListNewArrayList2 = arrayList;
                            }
                            ArrayList arrayList2 = arrayListNewArrayList2;
                            if (iFindMethodIndexInVtableReverse < 0 || ClassProto.this.isOverridableByDefaultMethod((Method) arrayListNewArrayList.get(iFindMethodIndexInVtableReverse))) {
                                int iFindMethodIndexInVtable = ClassProto.this.findMethodIndexInVtable(arrayListNewArrayList3, method);
                                if (iFindMethodIndexInVtable < 0) {
                                    if (ClassProto.this.findMethodIndexInVtable(arrayListNewArrayList4, method) < 0) {
                                        int iFindMethodIndexInVtable2 = ClassProto.this.findMethodIndexInVtable(arrayListNewArrayList5, method);
                                        if (iFindMethodIndexInVtable2 >= 0) {
                                            if (!AccessFlags.ABSTRACT.isSet(method.getAccessFlags()) && !((ClassProto) ClassProto.this.classPath.getClass(((Method) arrayListNewArrayList5.get(iFindMethodIndexInVtable2)).getDefiningClass())).implementsInterface(method.getDefiningClass())) {
                                                map.put(method, Integer.valueOf(((Integer) map.get((Method) arrayListNewArrayList5.remove(iFindMethodIndexInVtable2))).intValue()));
                                                arrayListNewArrayList3.add(method);
                                            }
                                        } else if (AccessFlags.ABSTRACT.isSet(method.getAccessFlags())) {
                                            if (method2 == null) {
                                                arrayListNewArrayList5.add(method);
                                                map.put(method, Integer.valueOf(map.size()));
                                            }
                                        } else if (method2 == null || ClassProto.this.interfaceMethodOverrides(method, method2)) {
                                            arrayListNewArrayList3.add(method);
                                            map.put(method, Integer.valueOf(map.size()));
                                        }
                                    }
                                } else if (!AccessFlags.ABSTRACT.isSet(method.getAccessFlags()) && !((ClassProto) ClassProto.this.classPath.getClass(((Method) arrayListNewArrayList3.get(iFindMethodIndexInVtable)).getDefiningClass())).implementsInterface(method.getDefiningClass())) {
                                    arrayListNewArrayList4.add((Method) arrayListNewArrayList3.remove(iFindMethodIndexInVtable));
                                }
                            }
                            arrayListNewArrayList2 = arrayList2;
                            i = 0;
                        }
                        size--;
                        i = 0;
                    }
                    Comparator<MethodReference> comparator = new Comparator<MethodReference>() { // from class: org.jf.dexlib2.analysis.ClassProto.7.1
                        @Override // java.util.Comparator
                        public int compare(MethodReference methodReference, MethodReference methodReference2) {
                            return Integer.compare(((Integer) map.get(methodReference)).intValue(), ((Integer) map.get(methodReference2)).intValue());
                        }
                    };
                    Collections.sort(arrayListNewArrayList5, comparator);
                    Collections.sort(arrayListNewArrayList3, comparator);
                    Collections.sort(arrayListNewArrayList4, comparator);
                    arrayListNewArrayList.addAll(arrayListNewArrayList5);
                    arrayListNewArrayList.addAll(arrayListNewArrayList3);
                    arrayListNewArrayList.addAll(arrayListNewArrayList4);
                }
                return arrayListNewArrayList;
            } catch (UnresolvedClassException unused) {
                arrayListNewArrayList.addAll(((ClassProto) ClassProto.this.classPath.getClass(TypeProxy.SilentConstruction.Appender.JAVA_LANG_OBJECT_DESCRIPTOR)).getVtable());
                ClassProto.this.vtableFullyResolved = false;
                return arrayListNewArrayList;
            }
        }
    });
    private final Supplier<List<Method>> postDefaultMethodVtableSupplier = Suppliers.memoize(new Supplier<List<Method>>() { // from class: org.jf.dexlib2.analysis.ClassProto.8
        @Override // java.util.function.Supplier
        public List<Method> get() {
            ArrayList arrayListNewArrayList = ListUtil.newArrayList();
            try {
                String superclass = ClassProto.this.getSuperclass();
                if (superclass != null) {
                    ClassProto classProto = (ClassProto) ClassProto.this.classPath.getClass(superclass);
                    arrayListNewArrayList.addAll(classProto.getVtable());
                    if (!classProto.vtableFullyResolved) {
                        ClassProto.this.vtableFullyResolved = false;
                        return arrayListNewArrayList;
                    }
                }
                if (!ClassProto.this.isInterface()) {
                    ClassProto classProto2 = ClassProto.this;
                    classProto2.addToVtable(classProto2.getClassDef().getVirtualMethods(), arrayListNewArrayList, true, true);
                    List listReverse = ListUtil.reverse(ListUtil.newArrayList(ClassProto.this.getDirectInterfaces()));
                    ArrayList arrayListNewArrayList2 = ListUtil.newArrayList();
                    ArrayList arrayListNewArrayList3 = ListUtil.newArrayList();
                    ArrayList arrayListNewArrayList4 = ListUtil.newArrayList();
                    final HashMap map = new HashMap();
                    Iterator it = listReverse.iterator();
                    while (it.hasNext()) {
                        for (Method method : ((ClassDef) it.next()).getVirtualMethods()) {
                            int iFindMethodIndexInVtable = ClassProto.this.findMethodIndexInVtable(arrayListNewArrayList, method);
                            if (iFindMethodIndexInVtable >= 0) {
                                if (ClassProto.this.interfaceMethodOverrides(method, (Method) arrayListNewArrayList.get(iFindMethodIndexInVtable))) {
                                    arrayListNewArrayList.set(iFindMethodIndexInVtable, method);
                                }
                            } else {
                                int iFindMethodIndexInVtable2 = ClassProto.this.findMethodIndexInVtable(arrayListNewArrayList2, method);
                                if (iFindMethodIndexInVtable2 < 0) {
                                    if (ClassProto.this.findMethodIndexInVtable(arrayListNewArrayList3, method) < 0) {
                                        int iFindMethodIndexInVtable3 = ClassProto.this.findMethodIndexInVtable(arrayListNewArrayList4, method);
                                        if (iFindMethodIndexInVtable3 >= 0) {
                                            if (!AccessFlags.ABSTRACT.isSet(method.getAccessFlags()) && !((ClassProto) ClassProto.this.classPath.getClass(((Method) arrayListNewArrayList4.get(iFindMethodIndexInVtable3)).getDefiningClass())).implementsInterface(method.getDefiningClass())) {
                                                map.put(method, Integer.valueOf(((Integer) map.get((Method) arrayListNewArrayList4.remove(iFindMethodIndexInVtable3))).intValue()));
                                                arrayListNewArrayList2.add(method);
                                            }
                                        } else if (!AccessFlags.ABSTRACT.isSet(method.getAccessFlags())) {
                                            arrayListNewArrayList2.add(method);
                                            map.put(method, Integer.valueOf(map.size()));
                                        } else {
                                            arrayListNewArrayList4.add(method);
                                            map.put(method, Integer.valueOf(map.size()));
                                        }
                                    }
                                } else if (!AccessFlags.ABSTRACT.isSet(method.getAccessFlags()) && !((ClassProto) ClassProto.this.classPath.getClass(((Method) arrayListNewArrayList2.get(iFindMethodIndexInVtable2)).getDefiningClass())).implementsInterface(method.getDefiningClass())) {
                                    arrayListNewArrayList3.add((Method) arrayListNewArrayList2.remove(iFindMethodIndexInVtable2));
                                }
                            }
                        }
                    }
                    Comparator<MethodReference> comparator = new Comparator<MethodReference>() { // from class: org.jf.dexlib2.analysis.ClassProto.8.1
                        @Override // java.util.Comparator
                        public int compare(MethodReference methodReference, MethodReference methodReference2) {
                            return Integer.compare(((Integer) map.get(methodReference)).intValue(), ((Integer) map.get(methodReference2)).intValue());
                        }
                    };
                    Collections.sort(arrayListNewArrayList2, comparator);
                    Collections.sort(arrayListNewArrayList3, comparator);
                    Collections.sort(arrayListNewArrayList4, comparator);
                    ClassProto.this.addToVtable(arrayListNewArrayList2, arrayListNewArrayList, false, false);
                    ClassProto.this.addToVtable(arrayListNewArrayList3, arrayListNewArrayList, false, false);
                    ClassProto.this.addToVtable(arrayListNewArrayList4, arrayListNewArrayList, false, false);
                }
                return arrayListNewArrayList;
            } catch (UnresolvedClassException unused) {
                arrayListNewArrayList.addAll(((ClassProto) ClassProto.this.classPath.getClass(TypeProxy.SilentConstruction.Appender.JAVA_LANG_OBJECT_DESCRIPTOR)).getVtable());
                ClassProto.this.vtableFullyResolved = false;
                return arrayListNewArrayList;
            }
        }
    });

    public ClassProto(ClassPath classPath, String str) {
        if (str.charAt(0) != 'L') {
            throw new ExceptionWithContext("Cannot construct ClassProto for non reference type: %s", str);
        }
        this.classPath = classPath;
        this.type = str;
    }

    public String toString() {
        return this.type;
    }

    @Override // org.jf.dexlib2.analysis.TypeProto
    public ClassPath getClassPath() {
        return this.classPath;
    }

    @Override // org.jf.dexlib2.analysis.TypeProto
    public String getType() {
        return this.type;
    }

    public ClassDef getClassDef() {
        return this.classDefSupplier.get();
    }

    @Override // org.jf.dexlib2.analysis.TypeProto
    public boolean isInterface() {
        return (getClassDef().getAccessFlags() & AccessFlags.INTERFACE.getValue()) != 0;
    }

    protected LinkedHashMap<String, ClassDef> getInterfaces() {
        if (!this.classPath.isArt() || this.classPath.oatVersion < 72) {
            return this.preDefaultMethodInterfaceSupplier.get();
        }
        return this.postDefaultMethodInterfaceSupplier.get();
    }

    protected Set<String> getUnresolvedInterfaces() {
        Set<String> set = this.unresolvedInterfaces;
        return set == null ? EmptySet.of() : set;
    }

    protected Iterable<ClassDef> getDirectInterfaces() {
        Iterable<ClassDef> iterableFilter = Iterables.filter(getInterfaces().values(), new Predicate() { // from class: org.jf.dexlib2.analysis.ClassProto$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return Objects.nonNull((ClassDef) obj);
            }
        });
        if (this.interfacesFullyResolved) {
            return iterableFilter;
        }
        throw new UnresolvedClassException("Interfaces for class %s not fully resolved: %s", getType(), StringUtils.join(",", getUnresolvedInterfaces()));
    }

    @Override // org.jf.dexlib2.analysis.TypeProto
    public boolean implementsInterface(String str) {
        if (getInterfaces().containsKey(str)) {
            return true;
        }
        if (this.interfacesFullyResolved) {
            return false;
        }
        throw new UnresolvedClassException("Interfaces for class %s not fully resolved", getType());
    }

    @Override // org.jf.dexlib2.analysis.TypeProto
    public String getSuperclass() {
        return getClassDef().getSuperclass();
    }

    private boolean checkInterface(ClassProto classProto) {
        boolean z;
        boolean zIsInterface;
        try {
            zIsInterface = isInterface();
            z = true;
        } catch (UnresolvedClassException unused) {
            z = false;
            zIsInterface = true;
        }
        if (zIsInterface) {
            try {
                if (classProto.implementsInterface(getType())) {
                    return true;
                }
            } catch (UnresolvedClassException e) {
                if (z) {
                    throw e;
                }
            }
        }
        return false;
    }

    @Override // org.jf.dexlib2.analysis.TypeProto
    public TypeProto getCommonSuperclass(TypeProto typeProto) {
        boolean z;
        if (!(typeProto instanceof ClassProto)) {
            return typeProto.getCommonSuperclass(this);
        }
        if (this == typeProto || getType().equals(typeProto.getType()) || getType().equals(TypeProxy.SilentConstruction.Appender.JAVA_LANG_OBJECT_DESCRIPTOR)) {
            return this;
        }
        if (typeProto.getType().equals(TypeProxy.SilentConstruction.Appender.JAVA_LANG_OBJECT_DESCRIPTOR)) {
            return typeProto;
        }
        try {
            if (checkInterface((ClassProto) typeProto)) {
                return this;
            }
            z = false;
            try {
                if (((ClassProto) typeProto).checkInterface(this)) {
                    return typeProto;
                }
            } catch (UnresolvedClassException unused) {
                z = true;
            }
            if (z) {
                return this.classPath.getUnknownClass();
            }
            ArrayList arrayListNewArrayList = ListUtil.newArrayList(this);
            ListUtil.addAll(arrayListNewArrayList, TypeProtoUtils.getSuperclassChain(this));
            ArrayList arrayListNewArrayList2 = ListUtil.newArrayList(typeProto);
            ListUtil.addAll(arrayListNewArrayList2, TypeProtoUtils.getSuperclassChain(typeProto));
            List listReverse = ListUtil.reverse(arrayListNewArrayList);
            List listReverse2 = ListUtil.reverse(arrayListNewArrayList2);
            for (int iMin = Math.min(listReverse.size(), listReverse2.size()) - 1; iMin >= 0; iMin--) {
                TypeProto typeProto2 = (TypeProto) listReverse.get(iMin);
                if (typeProto2.getType().equals(((TypeProto) listReverse2.get(iMin)).getType())) {
                    return typeProto2;
                }
            }
            return this.classPath.getUnknownClass();
        } catch (UnresolvedClassException unused2) {
            z = true;
        }
    }

    @Override // org.jf.dexlib2.analysis.TypeProto
    public FieldReference getFieldByOffset(int i) {
        if (getInstanceFields().size() == 0) {
            return null;
        }
        return getInstanceFields().get(i);
    }

    @Override // org.jf.dexlib2.analysis.TypeProto
    public Method getMethodByVtableIndex(int i) {
        List<Method> vtable = getVtable();
        if (i < 0 || i >= vtable.size()) {
            return null;
        }
        return vtable.get(i);
    }

    @Override // org.jf.dexlib2.analysis.TypeProto
    public int findMethodIndexInVtable(MethodReference methodReference) {
        return findMethodIndexInVtable(getVtable(), methodReference);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int findMethodIndexInVtable(List<Method> list, MethodReference methodReference) {
        for (int i = 0; i < list.size(); i++) {
            Method method = list.get(i);
            if (MethodUtil.methodSignaturesMatch(method, methodReference) && (!this.classPath.shouldCheckPackagePrivateAccess() || AnalyzedMethodUtil.canAccess(this, method, true, false, false))) {
                return i;
            }
        }
        return -1;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int findMethodIndexInVtableReverse(List<Method> list, MethodReference methodReference) {
        for (int size = list.size() - 1; size >= 0; size--) {
            Method method = list.get(size);
            if (MethodUtil.methodSignaturesMatch(method, methodReference) && (!this.classPath.shouldCheckPackagePrivateAccess() || AnalyzedMethodUtil.canAccess(this, method, true, false, false))) {
                return size;
            }
        }
        return -1;
    }

    public SparseArray<FieldReference> getInstanceFields() {
        if (this.classPath.isArt()) {
            return this.artInstanceFieldsSupplier.get();
        }
        return this.dalvikInstanceFieldsSupplier.get();
    }

    private static abstract class FieldGap implements Comparable<FieldGap> {
        public final int offset;
        public final int size;

        public static FieldGap newFieldGap(int i, int i2, int i3) {
            if (i3 >= 67) {
                return new FieldGap(i, i2) { // from class: org.jf.dexlib2.analysis.ClassProto.FieldGap.1
                    @Override // java.lang.Comparable
                    public int compareTo(FieldGap fieldGap) {
                        int iCompare = Integer.compare(fieldGap.size, this.size);
                        return iCompare != 0 ? iCompare : Integer.compare(this.offset, fieldGap.offset);
                    }
                };
            }
            return new FieldGap(i, i2) { // from class: org.jf.dexlib2.analysis.ClassProto.FieldGap.2
                @Override // java.lang.Comparable
                public int compareTo(FieldGap fieldGap) {
                    int iCompare = Integer.compare(this.size, fieldGap.size);
                    return iCompare != 0 ? iCompare : Integer.compare(fieldGap.offset, this.offset);
                }
            };
        }

        private FieldGap(int i, int i2) {
            this.offset = i;
            this.size = i2;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int getNextFieldOffset() {
        SparseArray<FieldReference> instanceFields = getInstanceFields();
        if (instanceFields.size() == 0) {
            return this.classPath.isArt() ? 0 : 8;
        }
        int size = instanceFields.size() - 1;
        int iKeyAt = instanceFields.keyAt(size);
        FieldReference fieldReferenceValueAt = instanceFields.valueAt(size);
        if (this.classPath.isArt()) {
            return iKeyAt + getTypeSize(fieldReferenceValueAt.getType().charAt(0));
        }
        char cCharAt = fieldReferenceValueAt.getType().charAt(0);
        return (cCharAt == 'D' || cCharAt == 'J') ? iKeyAt + 8 : iKeyAt + 4;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static int getTypeSize(char c) {
        if (c == 'F' || c == 'L') {
            return 4;
        }
        if (c == 'S') {
            return 2;
        }
        if (c == 'I') {
            return 4;
        }
        if (c == 'J') {
            return 8;
        }
        if (c == 'Z') {
            return 1;
        }
        if (c == '[') {
            return 4;
        }
        switch (c) {
            case 'B':
                return 1;
            case 'C':
                return 2;
            case 'D':
                return 8;
            default:
                throw new ExceptionWithContext("Invalid type: %s", Character.valueOf(c));
        }
    }

    public List<Method> getVtable() {
        if (!this.classPath.isArt() || this.classPath.oatVersion < 72) {
            return this.preDefaultMethodVtableSupplier.get();
        }
        if (this.classPath.oatVersion < 87) {
            return this.buggyPostDefaultMethodVtableSupplier.get();
        }
        return this.postDefaultMethodVtableSupplier.get();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void addToVtable(Iterable<? extends Method> iterable, List<Method> list, boolean z, boolean z2) {
        Iterable<Method> iterable2 = iterable;
        if (z2) {
            ArrayList arrayListNewArrayList = ListUtil.newArrayList(iterable);
            Collections.sort(arrayListNewArrayList);
            iterable2 = arrayListNewArrayList;
        }
        for (Method method : iterable2) {
            int iFindMethodIndexInVtable = findMethodIndexInVtable(list, method);
            if (iFindMethodIndexInVtable < 0) {
                list.add(method);
            } else if (z) {
                list.set(iFindMethodIndexInVtable, method);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static byte getFieldType(FieldReference fieldReference) {
        char cCharAt = fieldReference.getType().charAt(0);
        if (cCharAt == 'D' || cCharAt == 'J') {
            return (byte) 1;
        }
        return (cCharAt == 'L' || cCharAt == '[') ? (byte) 0 : (byte) 2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isOverridableByDefaultMethod(Method method) {
        return ((ClassProto) this.classPath.getClass(method.getDefiningClass())).isInterface();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean interfaceMethodOverrides(Method method, Method method2) {
        if (((ClassProto) this.classPath.getClass(method2.getDefiningClass())).isInterface()) {
            return ((ClassProto) this.classPath.getClass(method.getDefiningClass())).implementsInterface(method2.getDefiningClass());
        }
        return false;
    }

    static class ReparentedMethod extends BaseMethodReference implements Method {
        private final String definingClass;
        private final Method method;

        public ReparentedMethod(Method method, String str) {
            this.method = method;
            this.definingClass = str;
        }

        @Override // org.jf.dexlib2.iface.reference.MethodReference, org.jf.dexlib2.iface.Method, org.jf.dexlib2.iface.Member
        public String getDefiningClass() {
            return this.definingClass;
        }

        @Override // org.jf.dexlib2.iface.reference.MethodReference, org.jf.dexlib2.iface.Method, org.jf.dexlib2.iface.Member
        public String getName() {
            return this.method.getName();
        }

        @Override // org.jf.dexlib2.iface.reference.MethodReference
        public List<? extends CharSequence> getParameterTypes() {
            return this.method.getParameterTypes();
        }

        @Override // org.jf.dexlib2.iface.reference.MethodReference, org.jf.dexlib2.iface.Method
        public String getReturnType() {
            return this.method.getReturnType();
        }

        @Override // org.jf.dexlib2.iface.Method
        public List<? extends MethodParameter> getParameters() {
            return this.method.getParameters();
        }

        @Override // org.jf.dexlib2.iface.Method, org.jf.dexlib2.iface.Member
        public int getAccessFlags() {
            return this.method.getAccessFlags();
        }

        @Override // org.jf.dexlib2.iface.Method, org.jf.dexlib2.iface.Annotatable
        public Set<? extends Annotation> getAnnotations() {
            return this.method.getAnnotations();
        }

        @Override // org.jf.dexlib2.iface.Method, org.jf.dexlib2.iface.Member
        public Set<HiddenApiRestriction> getHiddenApiRestrictions() {
            return this.method.getHiddenApiRestrictions();
        }

        @Override // org.jf.dexlib2.iface.Method
        public MethodImplementation getImplementation() {
            return this.method.getImplementation();
        }
    }
}
