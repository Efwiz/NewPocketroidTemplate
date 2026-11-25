package com.esotericsoftware.kryo.util;

import com.esotericsoftware.kryo.KryoException;
import java.lang.reflect.Array;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.lang.reflect.WildcardType;

/* JADX INFO: loaded from: classes8.dex */
public class GenericsUtil {
    public static Type resolveType(Class cls, Class cls2, Type type) {
        if (type instanceof Class) {
            return type;
        }
        if (type instanceof TypeVariable) {
            return resolveTypeVariable(cls, cls2, type, true);
        }
        if (type instanceof ParameterizedType) {
            return ((ParameterizedType) type).getRawType();
        }
        if (type instanceof GenericArrayType) {
            int i = 1;
            while (true) {
                type = ((GenericArrayType) type).getGenericComponentType();
                if (!(type instanceof GenericArrayType)) {
                    break;
                }
                i++;
            }
            Type typeResolveType = resolveType(cls, cls2, type);
            if (!(typeResolveType instanceof Class)) {
                return type;
            }
            if (i == 1) {
                return Array.newInstance((Class<?>) typeResolveType, 0).getClass();
            }
            return Array.newInstance((Class<?>) typeResolveType, new int[i]).getClass();
        }
        if (type instanceof WildcardType) {
            WildcardType wildcardType = (WildcardType) type;
            Type type2 = wildcardType.getUpperBounds()[0];
            if (type2 != Object.class) {
                return resolveType(cls, cls2, type2);
            }
            Type[] lowerBounds = wildcardType.getLowerBounds();
            return lowerBounds.length != 0 ? resolveType(cls, cls2, lowerBounds[0]) : Object.class;
        }
        throw new KryoException("Unable to resolve type: " + type);
    }

    private static Type resolveTypeVariable(Class cls, Class cls2, Type type, boolean z) {
        Type genericSuperclass = cls2.getGenericSuperclass();
        if (!(genericSuperclass instanceof ParameterizedType)) {
            return type;
        }
        Class superclass = cls2.getSuperclass();
        if (superclass != cls) {
            type = resolveTypeVariable(cls, superclass, type, false);
            if (type instanceof Class) {
                return type;
            }
        }
        String string = type.toString();
        TypeVariable[] typeParameters = superclass.getTypeParameters();
        int length = typeParameters.length;
        for (int i = 0; i < length; i++) {
            if (typeParameters[i].getName().equals(string)) {
                Type type2 = ((ParameterizedType) genericSuperclass).getActualTypeArguments()[i];
                if (type2 instanceof Class) {
                    return type2;
                }
                if (!(type2 instanceof ParameterizedType) && !(type2 instanceof GenericArrayType)) {
                    if (type2 instanceof TypeVariable) {
                        return z ? type : type2;
                    }
                }
                return resolveType(cls, cls2, type2);
            }
        }
        return type;
    }

    public static Type[] resolveTypeParameters(Class cls, Class cls2, Type type) {
        if (type instanceof ParameterizedType) {
            Type[] actualTypeArguments = ((ParameterizedType) type).getActualTypeArguments();
            int length = actualTypeArguments.length;
            Type[] typeArr = new Type[length];
            for (int i = 0; i < length; i++) {
                typeArr[i] = resolveType(cls, cls2, actualTypeArguments[i]);
            }
            return typeArr;
        }
        if (!(type instanceof GenericArrayType)) {
            return null;
        }
        do {
            type = ((GenericArrayType) type).getGenericComponentType();
        } while (type instanceof GenericArrayType);
        return resolveTypeParameters(cls, cls2, type);
    }
}
