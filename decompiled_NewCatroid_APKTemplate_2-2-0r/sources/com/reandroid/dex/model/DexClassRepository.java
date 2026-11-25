package com.reandroid.dex.model;

import com.reandroid.arsc.base.BlockRefresh;
import com.reandroid.arsc.item.IntegerReference;
import com.reandroid.dex.common.FullRefresh;
import com.reandroid.dex.common.SectionItem;
import com.reandroid.dex.data.AnnotationElement;
import com.reandroid.dex.data.AnnotationItem;
import com.reandroid.dex.id.ClassId;
import com.reandroid.dex.id.MethodId;
import com.reandroid.dex.id.SourceFile;
import com.reandroid.dex.key.FieldKey;
import com.reandroid.dex.key.Key;
import com.reandroid.dex.key.MethodKey;
import com.reandroid.dex.key.ProtoKey;
import com.reandroid.dex.key.StringKey;
import com.reandroid.dex.key.TypeKey;
import com.reandroid.dex.key.TypeKeyReference;
import com.reandroid.dex.key.TypeListKey;
import com.reandroid.dex.sections.Marker;
import com.reandroid.dex.sections.Section;
import com.reandroid.dex.sections.SectionType;
import com.reandroid.utils.ObjectsUtil;
import com.reandroid.utils.collection.ArrayCollection;
import com.reandroid.utils.collection.CombiningIterator;
import com.reandroid.utils.collection.ComputeIterator;
import com.reandroid.utils.collection.EmptyIterator;
import com.reandroid.utils.collection.FilterIterator;
import com.reandroid.utils.collection.IterableIterator;
import com.reandroid.utils.collection.SingleIterator;
import com.reandroid.utils.collection.UniqueIterator;
import java.util.Iterator;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import name.antonsmirnov.firmata.FormatHelper;

/* JADX INFO: loaded from: classes11.dex */
public interface DexClassRepository extends FullRefresh, BlockRefresh {
    void clearPoolMap();

    DexClass getDexClass(TypeKey typeKey);

    Iterator<DexClass> getDexClasses(Predicate<? super TypeKey> predicate);

    Iterator<DexClass> getDexClassesCloned(Predicate<? super TypeKey> predicate);

    int getDexClassesCount();

    Iterator<Marker> getMarkers();

    <T extends SectionItem> Iterator<Section<T>> getSections(SectionType<T> sectionType);

    boolean removeClasses(Predicate<? super DexClass> predicate);

    <T1 extends SectionItem> boolean removeEntries(SectionType<T1> sectionType, Predicate<T1> predicate);

    <T1 extends SectionItem> boolean removeEntriesWithKey(SectionType<T1> sectionType, Predicate<? super Key> predicate);

    <T1 extends SectionItem> boolean removeEntry(SectionType<T1> sectionType, Key key);

    int shrink();

    default DexClass getDexClass(String str) {
        return getDexClass(TypeKey.create(str));
    }

    default Iterator<DexClass> searchExtending(TypeKey typeKey) {
        return EmptyIterator.of();
    }

    default Iterator<DexClass> searchImplementations(TypeKey typeKey) {
        return EmptyIterator.of();
    }

    default <T extends SectionItem> Iterator<T> getItems(SectionType<T> sectionType) {
        return new IterableIterator<Section<T>, T>(getSections(sectionType)) { // from class: com.reandroid.dex.model.DexClassRepository.1
            @Override // com.reandroid.utils.collection.IterableIterator
            public Iterator<T> iterator(Section<T> section) {
                return section.iterator();
            }
        };
    }

    default <T extends SectionItem> Iterator<T> getClonedItems(SectionType<T> sectionType) {
        return new IterableIterator<Section<T>, T>(getSections(sectionType)) { // from class: com.reandroid.dex.model.DexClassRepository.2
            @Override // com.reandroid.utils.collection.IterableIterator
            public Iterator<T> iterator(Section<T> section) {
                return section.clonedIterator();
            }
        };
    }

    default <T extends SectionItem> Iterator<T> getItems(SectionType<T> sectionType, final Key key) {
        return new IterableIterator<Section<T>, T>(getSections(sectionType)) { // from class: com.reandroid.dex.model.DexClassRepository.3
            @Override // com.reandroid.utils.collection.IterableIterator
            public Iterator<T> iterator(Section<T> section) {
                return section.getAll(key);
            }
        };
    }

    default <T extends SectionItem> T getItem(SectionType<T> sectionType, Key key) {
        Iterator<Section<T>> sections = getSections(sectionType);
        while (sections.hasNext()) {
            T t = (T) sections.next().get(key);
            if (t != null) {
                return t;
            }
        }
        return null;
    }

    default boolean contains(SectionType<?> sectionType, Key key) {
        return getItems(sectionType, key).hasNext();
    }

    default boolean contains(Key key) {
        if (key == null) {
            return false;
        }
        if (key instanceof StringKey) {
            return contains(SectionType.STRING_ID, key);
        }
        if (key instanceof TypeKey) {
            return contains(SectionType.TYPE_ID, key);
        }
        if (key instanceof FieldKey) {
            return contains(SectionType.FIELD_ID, key);
        }
        if (key instanceof ProtoKey) {
            return contains(SectionType.PROTO_ID, key);
        }
        if (key instanceof MethodKey) {
            return contains(SectionType.METHOD_ID, key);
        }
        if (key instanceof TypeListKey) {
            return contains(SectionType.TYPE_LIST, key);
        }
        throw new IllegalArgumentException("Unknown key type: " + key.getClass() + ", '" + key + FormatHelper.QUOTE);
    }

    default boolean containsClass(TypeKey typeKey) {
        return contains(SectionType.CLASS_ID, typeKey);
    }

    default <T extends SectionItem> Iterator<T> getClonedItems(SectionType<T> sectionType, Predicate<? super T> predicate) {
        return FilterIterator.of(getClonedItems(sectionType), predicate);
    }

    default Iterator<DexClass> findUserClasses(final Key key) {
        return new UniqueIterator(getDexClasses(), new Predicate() { // from class: com.reandroid.dex.model.DexClassRepository$$ExternalSyntheticLambda1
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((DexClass) obj).uses(key);
            }
        });
    }

    default Iterator<DexClass> getDexClasses() {
        return getDexClasses(null);
    }

    default Iterator<DexClass> getDexClassesCloned() {
        return getDexClassesCloned(null);
    }

    default Iterator<DexClass> getPackageClasses(String str) {
        return getPackageClasses(str, true);
    }

    default Iterator<DexClass> getPackageClasses(final String str, final boolean z) {
        return getDexClasses(new Predicate() { // from class: com.reandroid.dex.model.DexClassRepository$$ExternalSyntheticLambda2
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((TypeKey) obj).isPackage(str, z);
            }
        });
    }

    default DexMethod getDeclaredMethod(MethodKey methodKey) {
        DexClass dexClass = getDexClass(methodKey.getDeclaring());
        if (dexClass == null) {
            return null;
        }
        DexMethod declaredMethod = dexClass.getDeclaredMethod(methodKey, false);
        return declaredMethod == null ? dexClass.getDeclaredMethod(methodKey, true) : declaredMethod;
    }

    default DexMethod getDeclaredMethod(MethodKey methodKey, boolean z) {
        DexClass dexClass = getDexClass(methodKey.getDeclaring());
        if (dexClass != null) {
            return dexClass.getDeclaredMethod(methodKey, z);
        }
        return null;
    }

    default DexField getDeclaredField(FieldKey fieldKey) {
        DexClass dexClass = getDexClass(fieldKey.getDeclaring());
        if (dexClass != null) {
            return dexClass.getDeclaredField(fieldKey);
        }
        return null;
    }

    default DexDeclaration getDexDeclaration(Key key) {
        if (key instanceof TypeKey) {
            return getDexClass((TypeKey) key);
        }
        if (key instanceof MethodKey) {
            return getDeclaredMethod((MethodKey) key);
        }
        if (key instanceof FieldKey) {
            return getDeclaredField((FieldKey) key);
        }
        return null;
    }

    default Iterator<DexMethod> getDeclaredMethods() {
        return new IterableIterator<DexClass, DexMethod>(getDexClasses()) { // from class: com.reandroid.dex.model.DexClassRepository.4
            @Override // com.reandroid.utils.collection.IterableIterator
            public Iterator<DexMethod> iterator(DexClass dexClass) {
                return dexClass.getDeclaredMethods();
            }
        };
    }

    default Iterator<DexField> getDeclaredFields() {
        return new IterableIterator<DexClass, DexField>(getDexClasses()) { // from class: com.reandroid.dex.model.DexClassRepository.5
            @Override // com.reandroid.utils.collection.IterableIterator
            public Iterator<DexField> iterator(DexClass dexClass) {
                return dexClass.getDeclaredFields();
            }
        };
    }

    default Iterator<IntegerReference> visitIntegers() {
        return new DexIntegerVisitor(this);
    }

    default boolean removeClass(TypeKey typeKey) {
        return removeEntry(SectionType.CLASS_ID, typeKey);
    }

    default boolean removeClassesWithKeys(Predicate<? super TypeKey> predicate) {
        return removeEntriesWithKey(SectionType.CLASS_ID, (Predicate) ObjectsUtil.cast(predicate));
    }

    default boolean removeAnnotations(final TypeKey typeKey) {
        return removeEntries(SectionType.ANNOTATION_ITEM, new Predicate() { // from class: com.reandroid.dex.model.DexClassRepository$$ExternalSyntheticLambda3
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return typeKey.equals(((AnnotationItem) obj).getTypeKey());
            }
        });
    }

    default int removeAnnotationElements(final MethodKey methodKey) {
        TypeKey declaring = methodKey.getDeclaring();
        Predicate<AnnotationElement> predicate = new Predicate() { // from class: com.reandroid.dex.model.DexClassRepository$$ExternalSyntheticLambda4
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((AnnotationElement) obj).is(methodKey);
            }
        };
        Iterator clonedItems = getClonedItems(SectionType.ANNOTATION_ITEM);
        int i = 0;
        while (clonedItems.hasNext()) {
            AnnotationItem annotationItem = (AnnotationItem) clonedItems.next();
            if (declaring.equals(methodKey.getDeclaring()) && annotationItem.removeIf(predicate)) {
                if (annotationItem.isEmpty()) {
                    annotationItem.removeSelf();
                }
                i++;
            }
        }
        return i;
    }

    default void clearDebug() {
        Iterator sections = getSections(SectionType.DEBUG_INFO);
        while (sections.hasNext()) {
            ((Section) sections.next()).removeSelf();
        }
    }

    default List<TypeKeyReference> getExternalTypeKeyReferenceList() {
        return ArrayCollection.empty();
    }

    default Iterator<MethodKey> findEquivalentMethods(MethodKey methodKey) {
        DexClass dexClass = getDexClass(methodKey.getDeclaring());
        if (dexClass == null) {
            return EmptyIterator.of();
        }
        return new IterableIterator<DexMethod, MethodKey>(dexClass.getMethods(methodKey)) { // from class: com.reandroid.dex.model.DexClassRepository.6
            @Override // com.reandroid.utils.collection.IterableIterator
            public Iterator<MethodKey> iterator(DexMethod dexMethod) {
                DexMethod declared = dexMethod.getDeclared();
                return CombiningIterator.two(SingleIterator.of(declared.getKey()), declared.getOverridingKeys());
            }
        };
    }

    default Iterator<DexMethod> getMethods(MethodKey methodKey) {
        return ComputeIterator.of(findEquivalentMethods(methodKey), new Function() { // from class: com.reandroid.dex.model.DexClassRepository$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return this.f$0.getDeclaredMethod((MethodKey) obj);
            }
        });
    }

    default Iterator<MethodId> getMethodIds(MethodKey methodKey) {
        return new IterableIterator<MethodKey, MethodId>(findEquivalentMethods(methodKey)) { // from class: com.reandroid.dex.model.DexClassRepository.7
            @Override // com.reandroid.utils.collection.IterableIterator
            public Iterator<MethodId> iterator(MethodKey methodKey2) {
                return DexClassRepository.this.getItems(SectionType.METHOD_ID, methodKey2);
            }
        };
    }

    default void clearMarkers() {
        Iterator<Marker> markers = getMarkers();
        while (markers.hasNext()) {
            markers.next().removeSelf();
        }
    }

    default void setClassSourceFileAll() {
        setClassSourceFileAll(SourceFile.SourceFile);
    }

    default void setClassSourceFileAll(String str) {
        Iterator items = getItems(SectionType.CLASS_ID);
        while (items.hasNext()) {
            ((ClassId) items.next()).setSourceFile(str);
        }
    }
}
