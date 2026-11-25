package org.jf.dexlib2.immutable;

import java.util.AbstractCollection;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import org.jf.dexlib2.base.reference.BaseTypeReference;
import org.jf.dexlib2.iface.Annotation;
import org.jf.dexlib2.iface.ClassDef;
import org.jf.dexlib2.iface.Field;
import org.jf.dexlib2.iface.Method;
import org.jf.dexlib2.util.FieldUtil;
import org.jf.dexlib2.util.MethodUtil;
import org.jf.util.ImmutableConverter;
import org.jf.util.ImmutableUtils;
import org.jf.util.collection.Iterables;
import org.jf.util.collection.ListUtil;

/* JADX INFO: loaded from: classes6.dex */
public class ImmutableClassDef extends BaseTypeReference implements ClassDef {
    private static final ImmutableConverter<ImmutableClassDef, ClassDef> CONVERTER = new ImmutableConverter<ImmutableClassDef, ClassDef>() { // from class: org.jf.dexlib2.immutable.ImmutableClassDef.3
        /* JADX INFO: Access modifiers changed from: protected */
        @Override // org.jf.util.ImmutableConverter
        public boolean isImmutable(ClassDef classDef) {
            return classDef instanceof ImmutableClassDef;
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // org.jf.util.ImmutableConverter
        public ImmutableClassDef makeImmutable(ClassDef classDef) {
            return ImmutableClassDef.of(classDef);
        }
    };
    protected final int accessFlags;
    protected final Set<? extends ImmutableAnnotation> annotations;
    protected final Set<? extends ImmutableMethod> directMethods;
    protected final Set<? extends ImmutableField> instanceFields;
    protected final List<String> interfaces;
    protected final String sourceFile;
    protected final Set<? extends ImmutableField> staticFields;
    protected final String superclass;
    protected final String type;
    protected final Set<? extends ImmutableMethod> virtualMethods;

    public ImmutableClassDef(String str, int i, String str2, Collection<String> collection, String str3, Collection<? extends Annotation> collection2, Iterable<? extends Field> iterable, Iterable<? extends Method> iterable2) {
        iterable = iterable == null ? ListUtil.of() : iterable;
        iterable2 = iterable2 == null ? ListUtil.of() : iterable2;
        this.type = str;
        this.accessFlags = i;
        this.superclass = str2;
        this.interfaces = collection == null ? ListUtil.of() : ListUtil.copyOf((Collection) collection);
        this.sourceFile = str3;
        this.annotations = ImmutableAnnotation.immutableSetOf(collection2);
        this.staticFields = ImmutableField.immutableSetOf(Iterables.filter(iterable, FieldUtil.FIELD_IS_STATIC));
        this.instanceFields = ImmutableField.immutableSetOf(Iterables.filter(iterable, FieldUtil.FIELD_IS_INSTANCE));
        this.directMethods = ImmutableMethod.immutableSetOf(Iterables.filter(iterable2, MethodUtil.METHOD_IS_DIRECT));
        this.virtualMethods = ImmutableMethod.immutableSetOf(Iterables.filter(iterable2, MethodUtil.METHOD_IS_VIRTUAL));
    }

    public ImmutableClassDef(String str, int i, String str2, Collection<String> collection, String str3, Collection<? extends Annotation> collection2, Iterable<? extends Field> iterable, Iterable<? extends Field> iterable2, Iterable<? extends Method> iterable3, Iterable<? extends Method> iterable4) {
        this.type = str;
        this.accessFlags = i;
        this.superclass = str2;
        this.interfaces = collection == null ? ListUtil.of() : ListUtil.copyOf((Collection) collection);
        this.sourceFile = str3;
        this.annotations = ImmutableAnnotation.immutableSetOf(collection2);
        this.staticFields = ImmutableField.immutableSetOf(iterable);
        this.instanceFields = ImmutableField.immutableSetOf(iterable2);
        this.directMethods = ImmutableMethod.immutableSetOf(iterable3);
        this.virtualMethods = ImmutableMethod.immutableSetOf(iterable4);
    }

    public ImmutableClassDef(String str, int i, String str2, List<String> list, String str3, Set<? extends ImmutableAnnotation> set, Set<? extends ImmutableField> set2, Set<? extends ImmutableField> set3, Set<? extends ImmutableMethod> set4, Set<? extends ImmutableMethod> set5) {
        this.type = str;
        this.accessFlags = i;
        this.superclass = str2;
        this.interfaces = ListUtil.nullToEmptyList(list);
        this.sourceFile = str3;
        this.annotations = ImmutableUtils.nullToEmptySet(set);
        this.staticFields = ImmutableUtils.nullToEmptySortedSet(set2);
        this.instanceFields = ImmutableUtils.nullToEmptySortedSet(set3);
        this.directMethods = ImmutableUtils.nullToEmptySortedSet(set4);
        this.virtualMethods = ImmutableUtils.nullToEmptySortedSet(set5);
    }

    public static ImmutableClassDef of(ClassDef classDef) {
        if (classDef instanceof ImmutableClassDef) {
            return (ImmutableClassDef) classDef;
        }
        return new ImmutableClassDef(classDef.getType(), classDef.getAccessFlags(), classDef.getSuperclass(), classDef.getInterfaces(), classDef.getSourceFile(), classDef.getAnnotations(), classDef.getStaticFields(), classDef.getInstanceFields(), classDef.getDirectMethods(), classDef.getVirtualMethods());
    }

    @Override // org.jf.dexlib2.iface.reference.TypeReference, org.jf.dexlib2.iface.ClassDef
    public String getType() {
        return this.type;
    }

    @Override // org.jf.dexlib2.iface.ClassDef
    public int getAccessFlags() {
        return this.accessFlags;
    }

    @Override // org.jf.dexlib2.iface.ClassDef
    public String getSuperclass() {
        return this.superclass;
    }

    @Override // org.jf.dexlib2.iface.ClassDef
    public List<String> getInterfaces() {
        return this.interfaces;
    }

    @Override // org.jf.dexlib2.iface.ClassDef
    public String getSourceFile() {
        return this.sourceFile;
    }

    @Override // org.jf.dexlib2.iface.ClassDef, org.jf.dexlib2.iface.Annotatable
    public Set<? extends ImmutableAnnotation> getAnnotations() {
        return this.annotations;
    }

    @Override // org.jf.dexlib2.iface.ClassDef
    public Set<? extends ImmutableField> getStaticFields() {
        return this.staticFields;
    }

    @Override // org.jf.dexlib2.iface.ClassDef
    public Set<? extends ImmutableField> getInstanceFields() {
        return this.instanceFields;
    }

    @Override // org.jf.dexlib2.iface.ClassDef
    public Set<? extends ImmutableMethod> getDirectMethods() {
        return this.directMethods;
    }

    @Override // org.jf.dexlib2.iface.ClassDef
    public Set<? extends ImmutableMethod> getVirtualMethods() {
        return this.virtualMethods;
    }

    @Override // org.jf.dexlib2.iface.ClassDef
    public Collection<? extends ImmutableField> getFields() {
        return new AbstractCollection<ImmutableField>() { // from class: org.jf.dexlib2.immutable.ImmutableClassDef.1
            @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable
            public Iterator<ImmutableField> iterator() {
                return Iterables.concat(ImmutableClassDef.this.staticFields.iterator(), ImmutableClassDef.this.instanceFields.iterator());
            }

            @Override // java.util.AbstractCollection, java.util.Collection
            public int size() {
                return ImmutableClassDef.this.staticFields.size() + ImmutableClassDef.this.instanceFields.size();
            }
        };
    }

    @Override // org.jf.dexlib2.iface.ClassDef
    public Collection<? extends ImmutableMethod> getMethods() {
        return new AbstractCollection<ImmutableMethod>() { // from class: org.jf.dexlib2.immutable.ImmutableClassDef.2
            @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable
            public Iterator<ImmutableMethod> iterator() {
                return Iterables.concat(ImmutableClassDef.this.directMethods.iterator(), ImmutableClassDef.this.virtualMethods.iterator());
            }

            @Override // java.util.AbstractCollection, java.util.Collection
            public int size() {
                return ImmutableClassDef.this.directMethods.size() + ImmutableClassDef.this.virtualMethods.size();
            }
        };
    }

    public static Set<ImmutableClassDef> immutableSetOf(Iterable<? extends ClassDef> iterable) {
        return CONVERTER.toSet(iterable);
    }
}
