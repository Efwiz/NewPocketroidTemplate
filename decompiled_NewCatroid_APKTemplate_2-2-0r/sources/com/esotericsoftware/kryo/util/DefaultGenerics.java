package com.esotericsoftware.kryo.util;

import com.esotericsoftware.kryo.Kryo;
import com.huawei.hms.framework.common.ContainerUtils;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;

/* JADX INFO: loaded from: classes8.dex */
public final class DefaultGenerics implements Generics {
    private int argumentsSize;
    private int genericTypesSize;
    private final Kryo kryo;
    private Generics.GenericType[] genericTypes = new Generics.GenericType[16];
    private int[] depths = new int[16];
    private Type[] arguments = new Type[16];

    public DefaultGenerics(Kryo kryo) {
        this.kryo = kryo;
    }

    @Override // com.esotericsoftware.kryo.util.Generics
    public void pushGenericType(Generics.GenericType genericType) {
        int i = this.genericTypesSize;
        int i2 = i + 1;
        Generics.GenericType[] genericTypeArr = this.genericTypes;
        if (i2 == genericTypeArr.length) {
            Generics.GenericType[] genericTypeArr2 = new Generics.GenericType[genericTypeArr.length << 1];
            System.arraycopy(genericTypeArr, 0, genericTypeArr2, 0, i);
            this.genericTypes = genericTypeArr2;
            int[] iArr = this.depths;
            int[] iArr2 = new int[iArr.length << 1];
            System.arraycopy(iArr, 0, iArr2, 0, i);
            this.depths = iArr2;
        }
        this.genericTypesSize = i2;
        this.genericTypes[i] = genericType;
        this.depths[i] = this.kryo.getDepth();
    }

    @Override // com.esotericsoftware.kryo.util.Generics
    public void popGenericType() {
        int i = this.genericTypesSize;
        if (i == 0) {
            return;
        }
        int i2 = i - 1;
        if (this.depths[i2] < this.kryo.getDepth()) {
            return;
        }
        this.genericTypes[i2] = null;
        this.genericTypesSize = i2;
    }

    @Override // com.esotericsoftware.kryo.util.Generics
    public Generics.GenericType[] nextGenericTypes() {
        int i = this.genericTypesSize;
        if (i > 0) {
            int i2 = i - 1;
            Generics.GenericType genericType = this.genericTypes[i2];
            if (genericType.arguments != null && this.depths[i2] == this.kryo.getDepth() - 1) {
                pushGenericType(genericType.arguments[genericType.arguments.length - 1]);
                return genericType.arguments;
            }
        }
        return null;
    }

    @Override // com.esotericsoftware.kryo.util.Generics
    public Class nextGenericClass() {
        Generics.GenericType[] genericTypeArrNextGenericTypes = nextGenericTypes();
        if (genericTypeArrNextGenericTypes == null) {
            return null;
        }
        return genericTypeArrNextGenericTypes[0].resolve(this);
    }

    @Override // com.esotericsoftware.kryo.util.Generics
    public int pushTypeVariables(Generics.GenericsHierarchy genericsHierarchy, Generics.GenericType[] genericTypeArr) {
        if (genericsHierarchy.total == 0 || genericsHierarchy.rootTotal > genericTypeArr.length) {
            return 0;
        }
        int i = this.argumentsSize;
        int i2 = genericsHierarchy.total + i;
        Type[] typeArr = this.arguments;
        if (i2 > typeArr.length) {
            Type[] typeArr2 = new Type[Math.max(i2, typeArr.length << 1)];
            System.arraycopy(this.arguments, 0, typeArr2, 0, i);
            this.arguments = typeArr2;
        }
        int[] iArr = genericsHierarchy.counts;
        TypeVariable[] typeVariableArr = genericsHierarchy.parameters;
        int length = genericTypeArr.length;
        int i3 = 0;
        for (int i4 = 0; i4 < length; i4++) {
            Generics.GenericType genericType = genericTypeArr[i4];
            Class clsResolve = genericType.resolve(this);
            if (clsResolve != null) {
                int i5 = iArr[i4];
                if (genericType == null) {
                    i3 += i5;
                } else {
                    int i6 = i5 + i3;
                    while (i3 < i6) {
                        Type[] typeArr3 = this.arguments;
                        int i7 = this.argumentsSize;
                        typeArr3[i7] = typeVariableArr[i3];
                        typeArr3[i7 + 1] = clsResolve;
                        this.argumentsSize = i7 + 2;
                        i3++;
                    }
                }
            }
        }
        return this.argumentsSize - i;
    }

    @Override // com.esotericsoftware.kryo.util.Generics
    public void popTypeVariables(int i) {
        int i2 = this.argumentsSize;
        int i3 = i2 - i;
        this.argumentsSize = i3;
        while (i3 < i2) {
            this.arguments[i3] = null;
            i3++;
        }
    }

    @Override // com.esotericsoftware.kryo.util.Generics
    public Class resolveTypeVariable(TypeVariable typeVariable) {
        for (int i = this.argumentsSize - 2; i >= 0; i -= 2) {
            Type type = this.arguments[i];
            if (type == typeVariable || type.equals(typeVariable)) {
                return (Class) this.arguments[i + 1];
            }
        }
        return null;
    }

    @Override // com.esotericsoftware.kryo.util.Generics
    public int getGenericTypesSize() {
        return this.genericTypesSize;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < this.argumentsSize; i += 2) {
            if (i != 0) {
                sb.append(", ");
            }
            sb.append(((TypeVariable) this.arguments[i]).getName());
            sb.append(ContainerUtils.KEY_VALUE_DELIMITER);
            sb.append(((Class) this.arguments[i + 1]).getSimpleName());
        }
        return sb.toString();
    }
}
