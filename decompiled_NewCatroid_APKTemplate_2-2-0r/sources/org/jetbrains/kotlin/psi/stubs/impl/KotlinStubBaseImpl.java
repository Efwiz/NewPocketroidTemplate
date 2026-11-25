package org.jetbrains.kotlin.psi.stubs.impl;

import androidx.exifinterface.media.ExifInterface;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.jetbrains.kotlin.com.intellij.openapi.diagnostic.Logger;
import org.jetbrains.kotlin.com.intellij.psi.stubs.IStubElementType;
import org.jetbrains.kotlin.com.intellij.psi.stubs.NamedStub;
import org.jetbrains.kotlin.com.intellij.psi.stubs.StubBase;
import org.jetbrains.kotlin.com.intellij.psi.stubs.StubElement;
import org.jetbrains.kotlin.psi.KtElementImplStub;
import org.jetbrains.kotlin.psi.stubs.KotlinCallableStubBase;
import org.jetbrains.kotlin.psi.stubs.KotlinClassOrObjectStub;
import org.jetbrains.kotlin.psi.stubs.KotlinClassifierStub;
import org.jetbrains.kotlin.psi.stubs.KotlinStubWithFqName;

/* JADX INFO: compiled from: KotlinStubBaseImpl.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000B\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0010 \n\u0002\u0010\u0000\n\u0002\b\u0003\b\u0016\u0018\u0000 \u0018*\f\b\u0000\u0010\u0001*\u0006\u0012\u0002\b\u00030\u00022\b\u0012\u0004\u0012\u0002H\u00010\u0003:\u0001\u0018B#\u0012\f\u0010\u0004\u001a\b\u0012\u0002\b\u0003\u0018\u00010\u0005\u0012\u000e\u0010\u0006\u001a\n\u0012\u0002\b\u0003\u0012\u0002\b\u00030\u0007¢\u0006\u0002\u0010\bJ\u001a\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\n2\n\u0010\f\u001a\u0006\u0012\u0002\b\u00030\rH\u0002J\u0010\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u000bH\u0002J\u0018\u0010\u0011\u001a\u0012\u0012\n\b\u0001\u0012\u0006\u0012\u0002\b\u00030\u0005\u0012\u0002\b\u00030\u0007H\u0016J\u0012\u0010\u0012\u001a\u0004\u0018\u00010\u000f2\u0006\u0010\u0013\u001a\u00020\u000bH\u0002J \u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u000f0\u00152\u0010\u0010\f\u001a\f\u0012\b\b\u0001\u0012\u0004\u0018\u00010\u00160\rH\u0002J\b\u0010\u0017\u001a\u00020\u000fH\u0016¨\u0006\u0019"}, d2 = {"Lorg/jetbrains/kotlin/psi/stubs/impl/KotlinStubBaseImpl;", ExifInterface.GPS_DIRECTION_TRUE, "Lorg/jetbrains/kotlin/psi/KtElementImplStub;", "Lorg/jetbrains/kotlin/com/intellij/psi/stubs/StubBase;", "parent", "Lorg/jetbrains/kotlin/com/intellij/psi/stubs/StubElement;", "elementType", "Lorg/jetbrains/kotlin/com/intellij/psi/stubs/IStubElementType;", "(Lcom/intellij/psi/stubs/StubElement;Lcom/intellij/psi/stubs/IStubElementType;)V", "collectProperties", "", "Ljava/lang/reflect/Method;", "stubInterface", "Ljava/lang/Class;", "getPropertyName", "", "method", "getStubType", "renderProperty", "property", "renderPropertyValues", "", "", "toString", "Companion", "psi"}, k = 1, mv = {1, 9, 0}, xi = 48)
public class KotlinStubBaseImpl<T extends KtElementImplStub<?>> extends StubBase<T> {
    private static final List<Class<? extends Object>> BASE_STUB_INTERFACES;
    private static final Logger LOGGER;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KotlinStubBaseImpl(StubElement<?> stubElement, IStubElementType<?, ?> elementType) {
        super(stubElement, elementType);
        Intrinsics.checkNotNullParameter(elementType, "elementType");
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.stubs.StubBase, org.jetbrains.kotlin.com.intellij.psi.stubs.Stub, org.jetbrains.kotlin.com.intellij.psi.stubs.StubElement
    public IStubElementType<? extends StubElement<?>, ?> getStubType() {
        IStubElementType<? extends StubElement<?>, ?> stubType = super.getStubType();
        Intrinsics.checkNotNull(stubType, "null cannot be cast to non-null type com.intellij.psi.stubs.IStubElementType<out com.intellij.psi.stubs.StubElement<*>, *>");
        return stubType;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.stubs.StubBase, java.util.concurrent.atomic.AtomicReference
    public String toString() {
        Class<?>[] interfaces = getClass().getInterfaces();
        Intrinsics.checkNotNullExpressionValue(interfaces, "getInterfaces(...)");
        Object obj = null;
        boolean z = false;
        for (Class<?> cls : interfaces) {
            String name2 = cls.getName();
            Intrinsics.checkNotNullExpressionValue(name2, "getName(...)");
            if (StringsKt.contains$default((CharSequence) name2, (CharSequence) "Stub", false, 2, (Object) null)) {
                if (z) {
                    throw new IllegalArgumentException("Array contains more than one matching element.");
                }
                z = true;
                obj = cls;
            }
        }
        if (z) {
            Class<? extends Object> cls2 = (Class) obj;
            Intrinsics.checkNotNull(cls2);
            List<String> listRenderPropertyValues = renderPropertyValues(cls2);
            if (listRenderPropertyValues.isEmpty()) {
                return KotlinStubBaseImplKt.getSTUB_TO_STRING_PREFIX() + getStubType();
            }
            return KotlinStubBaseImplKt.getSTUB_TO_STRING_PREFIX() + getStubType() + CollectionsKt.joinToString$default(listRenderPropertyValues, ", ", "[", "]", 0, null, null, 56, null);
        }
        throw new NoSuchElementException("Array contains no element matching the predicate.");
    }

    private final List<String> renderPropertyValues(Class<? extends Object> stubInterface) {
        Collection<Method> collectionCollectProperties = collectProperties(stubInterface);
        ArrayList arrayList = new ArrayList();
        Iterator<T> it = collectionCollectProperties.iterator();
        while (it.hasNext()) {
            String strRenderProperty = renderProperty((Method) it.next());
            if (strRenderProperty != null) {
                arrayList.add(strRenderProperty);
            }
        }
        return CollectionsKt.sorted(arrayList);
    }

    private final Collection<Method> collectProperties(Class<?> stubInterface) {
        ArrayList arrayList = new ArrayList();
        Method[] declaredMethods = stubInterface.getDeclaredMethods();
        Intrinsics.checkNotNullExpressionValue(declaredMethods, "getDeclaredMethods(...)");
        ArrayList arrayList2 = new ArrayList();
        for (Method method : declaredMethods) {
            Class<?>[] parameterTypes = method.getParameterTypes();
            Intrinsics.checkNotNull(parameterTypes);
            if (parameterTypes.length == 0) {
                arrayList2.add(method);
            }
        }
        arrayList.addAll(arrayList2);
        Class<?>[] interfaces = stubInterface.getInterfaces();
        Intrinsics.checkNotNullExpressionValue(interfaces, "getInterfaces(...)");
        for (Class<?> cls : interfaces) {
            if (BASE_STUB_INTERFACES.contains(cls)) {
                Intrinsics.checkNotNull(cls);
                arrayList.addAll(collectProperties(cls));
            }
        }
        return arrayList;
    }

    private final String renderProperty(Method property) {
        try {
            return getPropertyName(property) + '=' + property.invoke(this, new Object[0]);
        } catch (Exception e) {
            LOGGER.error((Throwable) e);
            return null;
        }
    }

    private final String getPropertyName(Method method) {
        String name2 = method.getName();
        Intrinsics.checkNotNull(name2);
        if (!StringsKt.startsWith$default(name2, "get", false, 2, (Object) null)) {
            return name2;
        }
        String strSubstring = name2.substring(3);
        Intrinsics.checkNotNullExpressionValue(strSubstring, "substring(...)");
        if (strSubstring.length() <= 0) {
            return strSubstring;
        }
        char lowerCase = Character.toLowerCase(strSubstring.charAt(0));
        String strSubstring2 = strSubstring.substring(1);
        Intrinsics.checkNotNullExpressionValue(strSubstring2, "substring(...)");
        return lowerCase + strSubstring2;
    }

    static {
        Logger logger = Logger.getInstance((Class<?>) KotlinStubBaseImpl.class);
        Intrinsics.checkNotNullExpressionValue(logger, "getInstance(...)");
        LOGGER = logger;
        BASE_STUB_INTERFACES = CollectionsKt.listOf((Object[]) new Class[]{KotlinStubWithFqName.class, KotlinClassifierStub.class, KotlinClassOrObjectStub.class, NamedStub.class, KotlinCallableStubBase.class});
    }
}
