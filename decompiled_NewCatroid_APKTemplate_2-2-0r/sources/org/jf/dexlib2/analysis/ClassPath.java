package org.jf.dexlib2.analysis;

import androidx.exifinterface.media.ExifInterface;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.jf.dexlib2.Opcodes;
import org.jf.dexlib2.analysis.reflection.ReflectionClassDef;
import org.jf.dexlib2.iface.ClassDef;
import org.jf.dexlib2.immutable.ImmutableDexFile;
import org.jf.util.collection.ListUtil;

/* JADX INFO: loaded from: classes6.dex */
public class ClassPath {
    public static final int NOT_ART = -1;
    public static final int NOT_SPECIFIED = -2;
    private final boolean checkPackagePrivateAccess;
    private final Map<String, TypeProto> classLoader;
    private List<ClassProvider> classProviders;
    private OdexedFieldInstructionMapper mapper;
    public final int oatVersion;
    private final TypeProto unknownClass;

    public ClassPath(ClassProvider... classProviderArr) throws IOException {
        this(Arrays.asList(classProviderArr), false, -1);
    }

    public ClassPath(Iterable<ClassProvider> iterable) throws IOException {
        this(iterable, false, -1);
    }

    public ClassPath(Iterable<? extends ClassProvider> iterable, boolean z, int i) {
        HashMap<String, TypeProto> map = new HashMap<String, TypeProto>() { // from class: org.jf.dexlib2.analysis.ClassPath.1
            @Override // java.util.HashMap, java.util.AbstractMap, java.util.Map
            public TypeProto get(Object obj) {
                String string = obj.toString();
                TypeProto typeProto = (TypeProto) super.get((Object) string);
                if (typeProto != null) {
                    return typeProto;
                }
                TypeProto typeProtoLoadProto = loadProto(string);
                super.put(string, typeProtoLoadProto);
                return typeProtoLoadProto;
            }

            private TypeProto loadProto(String str) {
                if (str.charAt(0) == '[') {
                    return new ArrayProto(ClassPath.this, str);
                }
                return new ClassProto(ClassPath.this, str);
            }
        };
        this.classLoader = map;
        UnknownClassProto unknownClassProto = new UnknownClassProto(this);
        this.unknownClass = unknownClassProto;
        map.put(unknownClassProto.getType(), unknownClassProto);
        this.checkPackagePrivateAccess = z;
        this.oatVersion = i;
        loadPrimitiveType("Z");
        loadPrimitiveType("B");
        loadPrimitiveType(ExifInterface.LATITUDE_SOUTH);
        loadPrimitiveType("C");
        loadPrimitiveType("I");
        loadPrimitiveType("J");
        loadPrimitiveType("F");
        loadPrimitiveType("D");
        loadPrimitiveType("L");
        ArrayList arrayListNewArrayList = ListUtil.newArrayList(iterable);
        this.classProviders = arrayListNewArrayList;
        arrayListNewArrayList.add(getBasicClasses());
    }

    private void loadPrimitiveType(String str) {
        this.classLoader.put(str, new PrimitiveProto(this, str));
    }

    private static ClassProvider getBasicClasses() {
        return new DexClassProvider(new ImmutableDexFile(Opcodes.getDefault(), ListUtil.of(new ReflectionClassDef(Class.class), new ReflectionClassDef(Cloneable.class), new ReflectionClassDef(Object.class), new ReflectionClassDef(Serializable.class), new ReflectionClassDef(String.class), new ReflectionClassDef(Throwable.class))));
    }

    public boolean isArt() {
        return this.oatVersion != -1;
    }

    public TypeProto getClass(CharSequence charSequence) {
        return this.classLoader.get(charSequence.toString());
    }

    public ClassDef getClassDef(String str) {
        Iterator<ClassProvider> it = this.classProviders.iterator();
        while (it.hasNext()) {
            ClassDef classDef = it.next().getClassDef(str);
            if (classDef != null) {
                return classDef;
            }
        }
        throw new UnresolvedClassException("Could not resolve class %s", str);
    }

    public TypeProto getUnknownClass() {
        return this.unknownClass;
    }

    public boolean shouldCheckPackagePrivateAccess() {
        return this.checkPackagePrivateAccess;
    }

    public OdexedFieldInstructionMapper getFieldInstructionMapper() {
        if (this.mapper == null) {
            this.mapper = new OdexedFieldInstructionMapper(isArt());
        }
        return this.mapper;
    }
}
