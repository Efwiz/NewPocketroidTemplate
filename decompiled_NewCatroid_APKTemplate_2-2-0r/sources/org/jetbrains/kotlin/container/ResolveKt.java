package org.jetbrains.kotlin.container;

import java.lang.reflect.Constructor;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: Resolve.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000>\n\u0000\n\u0002\u0010 \n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\u001a\u001a\u0010\u0000\u001a\b\u0012\u0004\u0012\u00020\u00020\u00012\f\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00040\u0001\u001a0\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00040\u0001*\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\f\u0010\t\u001a\b\u0012\u0004\u0012\u00020\n0\u00012\u0006\u0010\u000b\u001a\u00020\fH\u0002\u001a\u001e\u0010\r\u001a\u00020\u000e*\u0006\u0012\u0002\b\u00030\u000f2\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\u000b\u001a\u00020\f\u001a\u001a\u0010\u0010\u001a\u00020\u0011*\u00020\u00122\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\u000b\u001a\u00020\f¨\u0006\u0013"}, d2 = {"computeArguments", "", "", "argumentDescriptors", "Lorg/jetbrains/kotlin/container/ValueDescriptor;", "bindArguments", "Ljava/lang/reflect/Member;", "containerId", "", "parameters", "Ljava/lang/reflect/Type;", "context", "Lorg/jetbrains/kotlin/container/ValueResolveContext;", "bindToConstructor", "Lorg/jetbrains/kotlin/container/ConstructorBinding;", "Ljava/lang/Class;", "bindToMethod", "Lorg/jetbrains/kotlin/container/MethodBinding;", "Ljava/lang/reflect/Method;", "container"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class ResolveKt {
    public static final List<Object> computeArguments(List<? extends ValueDescriptor> argumentDescriptors) {
        Intrinsics.checkNotNullParameter(argumentDescriptors, "argumentDescriptors");
        List<? extends ValueDescriptor> list = argumentDescriptors;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
        Iterator<T> it = list.iterator();
        while (it.hasNext()) {
            arrayList.add(((ValueDescriptor) it.next()).getValue());
        }
        return arrayList;
    }

    public static final ConstructorBinding bindToConstructor(Class<?> cls, String containerId, ValueResolveContext context) {
        Intrinsics.checkNotNullParameter(cls, "<this>");
        Intrinsics.checkNotNullParameter(containerId, "containerId");
        Intrinsics.checkNotNullParameter(context, "context");
        ConstructorInfo constructorInfo = CacheKt.getInfo(cls).getConstructorInfo();
        if (constructorInfo == null) {
            throw new IllegalStateException(("No constructor for " + cls + ": " + CacheKt.getInfo(cls) + " in " + containerId).toString());
        }
        Constructor<?> constructor = constructorInfo.getConstructor();
        return new ConstructorBinding(constructor, bindArguments(constructor, containerId, constructorInfo.getParameters(), context));
    }

    public static final MethodBinding bindToMethod(Method method, String containerId, ValueResolveContext context) {
        Intrinsics.checkNotNullParameter(method, "<this>");
        Intrinsics.checkNotNullParameter(containerId, "containerId");
        Intrinsics.checkNotNullParameter(context, "context");
        Type[] genericParameterTypes = method.getGenericParameterTypes();
        Intrinsics.checkNotNullExpressionValue(genericParameterTypes, "getGenericParameterTypes(...)");
        return new MethodBinding(method, bindArguments(method, containerId, ArraysKt.toList(genericParameterTypes), context));
    }

    private static final List<ValueDescriptor> bindArguments(Member member, String str, List<? extends Type> list, ValueResolveContext valueResolveContext) throws UnresolvedDependenciesException {
        ArrayList arrayList = new ArrayList(list.size());
        ArrayList arrayList2 = null;
        for (Type type : list) {
            ValueDescriptor valueDescriptorResolve = valueResolveContext.resolve(type);
            if (valueDescriptorResolve == null) {
                if (arrayList2 == null) {
                    arrayList2 = new ArrayList();
                }
                arrayList2.add(type);
            } else {
                arrayList.add(valueDescriptorResolve);
            }
        }
        if (arrayList2 != null) {
            throw new UnresolvedDependenciesException(str + ": Dependencies for `" + member + "` cannot be satisfied:\n  " + arrayList2);
        }
        return arrayList;
    }
}
