package org.jetbrains.kotlin.container;

import java.io.Closeable;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: Singletons.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001c\n\u0000\n\u0002\u0010\u000e\b\u0016\u0018\u00002\u00020\u0001B\u0019\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\n\u0010\u0004\u001a\u0006\u0012\u0002\b\u00030\u0005¢\u0006\u0002\u0010\u0006J\u0010\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fH\u0014J\u001c\u0010\r\u001a\u00020\n2\n\u0010\u0004\u001a\u0006\u0012\u0002\b\u00030\u00052\u0006\u0010\u000b\u001a\u00020\fH\u0002J\u0016\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00100\u000f2\u0006\u0010\u000b\u001a\u00020\fH\u0016J\u000e\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00100\u0012H\u0016J\b\u0010\u0013\u001a\u00020\u0014H\u0016R\u0015\u0010\u0004\u001a\u0006\u0012\u0002\b\u00030\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\b¨\u0006\u0002"}, d2 = {"Lorg/jetbrains/kotlin/container/SingletonTypeComponentDescriptor;", "Lorg/jetbrains/kotlin/container/SingletonDescriptor;", "container", "Lorg/jetbrains/kotlin/container/ComponentContainer;", "klass", "Ljava/lang/Class;", "(Lorg/jetbrains/kotlin/container/ComponentContainer;Ljava/lang/Class;)V", "getKlass", "()Ljava/lang/Class;", "createInstance", "", "context", "Lorg/jetbrains/kotlin/container/ValueResolveContext;", "createInstanceOf", "getDependencies", "", "Ljava/lang/reflect/Type;", "getRegistrations", "", "toString", ""}, k = 1, mv = {1, 9, 0}, xi = 48)
public class SingletonTypeComponentDescriptor extends SingletonDescriptor {
    private final Class<?> klass;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public SingletonTypeComponentDescriptor(ComponentContainer container, Class<?> klass) {
        super(container);
        Intrinsics.checkNotNullParameter(container, "container");
        Intrinsics.checkNotNullParameter(klass, "klass");
        this.klass = klass;
    }

    public final Class<?> getKlass() {
        return this.klass;
    }

    @Override // org.jetbrains.kotlin.container.SingletonDescriptor
    protected Object createInstance(ValueResolveContext context) {
        Intrinsics.checkNotNullParameter(context, "context");
        return createInstanceOf(this.klass, context);
    }

    @Override // org.jetbrains.kotlin.container.ComponentDescriptor
    public Iterable<Type> getRegistrations() {
        return CacheKt.getInfo(this.klass).getRegistrations();
    }

    private final Object createInstanceOf(Class<?> klass, ValueResolveContext context) throws Throwable {
        ConstructorBinding constructorBindingBindToConstructor = ResolveKt.bindToConstructor(klass, getContainer().getContainerId(), context);
        setState(ComponentState.Initializing);
        for (ValueDescriptor valueDescriptor : constructorBindingBindToConstructor.getArgumentDescriptors()) {
            if ((valueDescriptor instanceof Closeable) && !(valueDescriptor instanceof SingletonDescriptor)) {
                registerDisposableObject((Closeable) valueDescriptor);
            }
        }
        Constructor<?> constructor = constructorBindingBindToConstructor.getConstructor();
        try {
            Object[] array = ResolveKt.computeArguments(constructorBindingBindToConstructor.getArgumentDescriptors()).toArray(new Object[0]);
            Object objNewInstance = constructor.newInstance(Arrays.copyOf(array, array.length));
            Intrinsics.checkNotNull(objNewInstance);
            setState(ComponentState.Initialized);
            return objNewInstance;
        } catch (InvocationTargetException e) {
            Throwable targetException = e.getTargetException();
            if (targetException == null) {
                throw e;
            }
            Intrinsics.checkNotNull(targetException);
            throw targetException;
        }
    }

    @Override // org.jetbrains.kotlin.container.ComponentDescriptor
    public Collection<Type> getDependencies(ValueResolveContext context) {
        Intrinsics.checkNotNullParameter(context, "context");
        ClassInfo info = CacheKt.getInfo(this.klass);
        ConstructorInfo constructorInfo = info.getConstructorInfo();
        List<Type> parameters = constructorInfo != null ? constructorInfo.getParameters() : null;
        if (parameters == null) {
            parameters = CollectionsKt.emptyList();
        }
        List<SetterInfo> setterInfos = info.getSetterInfos();
        if (setterInfos.isEmpty()) {
            return parameters;
        }
        List<Type> list = parameters;
        ArrayList arrayList = new ArrayList();
        Iterator<T> it = setterInfos.iterator();
        while (it.hasNext()) {
            CollectionsKt.addAll(arrayList, ((SetterInfo) it.next()).getParameters());
        }
        return CollectionsKt.plus((Collection) list, (Iterable) arrayList);
    }

    public String toString() {
        return "Singleton: " + this.klass.getSimpleName();
    }
}
