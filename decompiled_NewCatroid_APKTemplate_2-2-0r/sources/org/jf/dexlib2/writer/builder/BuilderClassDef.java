package org.jf.dexlib2.writer.builder;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import org.jf.dexlib2.base.reference.BaseTypeReference;
import org.jf.dexlib2.iface.ClassDef;
import org.jf.dexlib2.util.MethodUtil;
import org.jf.util.collection.ArraySet;
import org.jf.util.collection.Iterables;
import org.jf.util.collection.ListUtil;

/* JADX INFO: loaded from: classes6.dex */
public class BuilderClassDef extends BaseTypeReference implements ClassDef {
    final int accessFlags;
    final BuilderAnnotationSet annotations;
    final Set<BuilderMethod> directMethods;
    final Set<BuilderField> instanceFields;
    final BuilderTypeList interfaces;
    final BuilderStringReference sourceFile;
    final Set<BuilderField> staticFields;
    final BuilderEncodedValues.BuilderArrayEncodedValue staticInitializers;
    final BuilderTypeReference superclass;
    final BuilderTypeReference type;
    final Set<BuilderMethod> virtualMethods;
    int classDefIndex = -1;
    int annotationDirectoryOffset = 0;

    BuilderClassDef(BuilderTypeReference builderTypeReference, int i, BuilderTypeReference builderTypeReference2, BuilderTypeList builderTypeList, BuilderStringReference builderStringReference, BuilderAnnotationSet builderAnnotationSet, Set<BuilderField> set, Set<BuilderField> set2, Iterable<? extends BuilderMethod> iterable, BuilderEncodedValues.BuilderArrayEncodedValue builderArrayEncodedValue) {
        iterable = iterable == null ? ListUtil.of() : iterable;
        set = set == null ? ArraySet.of() : set;
        set2 = set2 == null ? ArraySet.of() : set2;
        this.type = builderTypeReference;
        this.accessFlags = i;
        this.superclass = builderTypeReference2;
        this.interfaces = builderTypeList;
        this.sourceFile = builderStringReference;
        this.annotations = builderAnnotationSet;
        this.staticFields = set;
        this.instanceFields = set2;
        this.directMethods = ArraySet.copyOf(Iterables.filter(iterable, MethodUtil.METHOD_IS_DIRECT)).sort();
        this.virtualMethods = ArraySet.copyOf(Iterables.filter(iterable, MethodUtil.METHOD_IS_VIRTUAL)).sort();
        this.staticInitializers = builderArrayEncodedValue;
    }

    @Override // org.jf.dexlib2.iface.reference.TypeReference, org.jf.dexlib2.iface.ClassDef
    public String getType() {
        return this.type.getType();
    }

    @Override // org.jf.dexlib2.iface.ClassDef
    public int getAccessFlags() {
        return this.accessFlags;
    }

    @Override // org.jf.dexlib2.iface.ClassDef
    public String getSuperclass() {
        BuilderTypeReference builderTypeReference = this.superclass;
        if (builderTypeReference == null) {
            return null;
        }
        return builderTypeReference.getType();
    }

    @Override // org.jf.dexlib2.iface.ClassDef
    public String getSourceFile() {
        BuilderStringReference builderStringReference = this.sourceFile;
        if (builderStringReference == null) {
            return null;
        }
        return builderStringReference.getString();
    }

    @Override // org.jf.dexlib2.iface.ClassDef, org.jf.dexlib2.iface.Annotatable
    public BuilderAnnotationSet getAnnotations() {
        return this.annotations;
    }

    @Override // org.jf.dexlib2.iface.ClassDef
    public Set<BuilderField> getStaticFields() {
        return this.staticFields;
    }

    @Override // org.jf.dexlib2.iface.ClassDef
    public Set<BuilderField> getInstanceFields() {
        return this.instanceFields;
    }

    @Override // org.jf.dexlib2.iface.ClassDef
    public Set<BuilderMethod> getDirectMethods() {
        return this.directMethods;
    }

    @Override // org.jf.dexlib2.iface.ClassDef
    public Set<BuilderMethod> getVirtualMethods() {
        return this.virtualMethods;
    }

    @Override // org.jf.dexlib2.iface.ClassDef
    public List<String> getInterfaces() {
        return ListUtil.transform(this.interfaces, new Function<BuilderTypeReference, String>() { // from class: org.jf.dexlib2.writer.builder.BuilderClassDef.1
            @Override // java.util.function.Function
            public String apply(BuilderTypeReference builderTypeReference) {
                return builderTypeReference.toString();
            }
        });
    }

    @Override // org.jf.dexlib2.iface.ClassDef
    public Collection<BuilderField> getFields() {
        ArraySet arraySet = new ArraySet(this.staticFields.size() + this.instanceFields.size());
        arraySet.addAll((Collection) this.staticFields);
        arraySet.addAll((Collection) this.instanceFields);
        arraySet.sort();
        return arraySet;
    }

    @Override // org.jf.dexlib2.iface.ClassDef
    public Collection<BuilderMethod> getMethods() {
        ArraySet arraySet = new ArraySet(this.directMethods.size() + this.virtualMethods.size());
        arraySet.addAll((Collection) this.directMethods);
        arraySet.addAll((Collection) this.virtualMethods);
        arraySet.sort();
        return arraySet;
    }
}
