package org.jf.dexlib2.writer.pool;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import org.jf.dexlib2.base.reference.BaseTypeReference;
import org.jf.dexlib2.iface.Annotation;
import org.jf.dexlib2.iface.ClassDef;
import org.jf.dexlib2.iface.Field;
import org.jf.util.collection.ArraySet;
import org.jf.util.collection.Iterables;
import org.jf.util.collection.ListUtil;

/* JADX INFO: loaded from: classes6.dex */
class PoolClassDef extends BaseTypeReference implements ClassDef {
    final ClassDef classDef;
    final Set<PoolMethod> directMethods;
    final Set<Field> instanceFields;
    final TypeListPool.Key<List<String>> interfaces;
    final Set<Field> staticFields;
    final Set<PoolMethod> virtualMethods;
    int classDefIndex = -1;
    int annotationDirectoryOffset = 0;

    PoolClassDef(ClassDef classDef) {
        this.classDef = classDef;
        this.interfaces = new TypeListPool.Key<>(ListUtil.copyOf((Collection) classDef.getInterfaces()));
        this.staticFields = ArraySet.copyOf(classDef.getStaticFields());
        this.instanceFields = ArraySet.copyOf(classDef.getInstanceFields());
        this.directMethods = ArraySet.copyOf(Iterables.transform(classDef.getDirectMethods(), PoolMethod.TRANSFORM));
        this.virtualMethods = ArraySet.copyOf(Iterables.transform(classDef.getVirtualMethods(), PoolMethod.TRANSFORM));
    }

    @Override // org.jf.dexlib2.iface.reference.TypeReference, org.jf.dexlib2.iface.ClassDef
    public String getType() {
        return this.classDef.getType();
    }

    @Override // org.jf.dexlib2.iface.ClassDef
    public int getAccessFlags() {
        return this.classDef.getAccessFlags();
    }

    @Override // org.jf.dexlib2.iface.ClassDef
    public String getSuperclass() {
        return this.classDef.getSuperclass();
    }

    @Override // org.jf.dexlib2.iface.ClassDef
    public List<String> getInterfaces() {
        return (List) this.interfaces.types;
    }

    @Override // org.jf.dexlib2.iface.ClassDef
    public String getSourceFile() {
        return this.classDef.getSourceFile();
    }

    @Override // org.jf.dexlib2.iface.ClassDef, org.jf.dexlib2.iface.Annotatable
    public Set<? extends Annotation> getAnnotations() {
        return this.classDef.getAnnotations();
    }

    @Override // org.jf.dexlib2.iface.ClassDef
    public Set<Field> getStaticFields() {
        return this.staticFields;
    }

    @Override // org.jf.dexlib2.iface.ClassDef
    public Set<Field> getInstanceFields() {
        return this.instanceFields;
    }

    @Override // org.jf.dexlib2.iface.ClassDef
    public Collection<Field> getFields() {
        return ListUtil.sortedCopy(Iterables.concat(this.staticFields.iterator(), this.instanceFields.iterator()));
    }

    @Override // org.jf.dexlib2.iface.ClassDef
    public Set<PoolMethod> getDirectMethods() {
        return this.directMethods;
    }

    @Override // org.jf.dexlib2.iface.ClassDef
    public Set<PoolMethod> getVirtualMethods() {
        return this.virtualMethods;
    }

    @Override // org.jf.dexlib2.iface.ClassDef
    public Collection<PoolMethod> getMethods() {
        return ListUtil.sortedCopy(Iterables.concat(this.directMethods.iterator(), this.virtualMethods.iterator()));
    }
}
