package org.jetbrains.kotlin.com.intellij.util.xmlb;

import java.lang.reflect.Type;
import net.bytebuddy.implementation.Implementation;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.com.intellij.openapi.util.text.StringUtil;
import org.jetbrains.kotlin.com.intellij.serialization.MutableAccessor;
import org.jetbrains.kotlin.com.intellij.util.ReflectionUtil;
import org.jetbrains.kotlin.com.intellij.util.XmlElement;

/* JADX INFO: loaded from: classes3.dex */
abstract class BasePrimitiveBinding implements NestedBinding {
    protected final MutableAccessor myAccessor;
    protected Binding myBinding;
    protected final Converter<Object> myConverter;

    /* JADX INFO: renamed from: name, reason: collision with root package name */
    protected final String f726name;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = i != 1 ? "Argument for @NotNull parameter '%s' of %s.%s must not be null" : "@NotNull method %s.%s must not return null";
        Object[] objArr = new Object[i != 1 ? 3 : 2];
        switch (i) {
            case 1:
                objArr[0] = "org/jetbrains/kotlin/com/intellij/util/xmlb/BasePrimitiveBinding";
                break;
            case 2:
                objArr[0] = "originalType";
                break;
            case 3:
                objArr[0] = "serializer";
                break;
            case 4:
                objArr[0] = "o";
                break;
            case 5:
            case 6:
                objArr[0] = CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD;
                break;
            default:
                objArr[0] = Implementation.Context.Default.ACCESSOR_METHOD_SUFFIX;
                break;
        }
        if (i != 1) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/util/xmlb/BasePrimitiveBinding";
        } else {
            objArr[1] = "getAccessor";
        }
        switch (i) {
            case 1:
                break;
            case 2:
            case 3:
                objArr[2] = ConfigConstants.CONFIG_INIT_SECTION;
                break;
            case 4:
                objArr[2] = "serialize";
                break;
            case 5:
            case 6:
                objArr[2] = "deserializeUnsafe";
                break;
            default:
                objArr[2] = "<init>";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i == 1) {
            throw new IllegalStateException(str2);
        }
    }

    public abstract Object deserialize(Object obj, XmlElement xmlElement);

    protected BasePrimitiveBinding(MutableAccessor mutableAccessor, String str, Class<? extends Converter> cls) {
        if (mutableAccessor == null) {
            $$$reportNull$$$0(0);
        }
        this.myAccessor = mutableAccessor;
        this.f726name = StringUtil.isEmpty(str) ? mutableAccessor.getName() : str;
        this.myConverter = (cls == null || cls == Converter.class) ? null : (Converter) ReflectionUtil.newInstance(cls);
    }

    @Override // org.jetbrains.kotlin.com.intellij.util.xmlb.Binding
    public final void init(Type type, Serializer serializer) {
        if (type == null) {
            $$$reportNull$$$0(2);
        }
        if (serializer == null) {
            $$$reportNull$$$0(3);
        }
        if (this.myConverter != null || (this instanceof AttributeBinding)) {
            return;
        }
        this.myBinding = serializer.getBinding(this.myAccessor);
    }

    @Override // org.jetbrains.kotlin.com.intellij.util.xmlb.Binding
    public final Object deserializeUnsafe(Object obj, XmlElement xmlElement) {
        if (xmlElement == null) {
            $$$reportNull$$$0(6);
        }
        return deserialize(obj, xmlElement);
    }
}
