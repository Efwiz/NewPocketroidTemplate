package org.jetbrains.kotlin.com.intellij.util.xmlb;

import java.lang.reflect.Type;
import java.util.List;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.com.intellij.openapi.diagnostic.Logger;
import org.jetbrains.kotlin.com.intellij.util.XmlElement;

/* JADX INFO: loaded from: classes3.dex */
public interface Binding {
    public static final Logger LOG = Logger.getInstance((Class<?>) Binding.class);

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[3];
        switch (i) {
            case 1:
                objArr[0] = "serializer";
                break;
            case 2:
            case 4:
                objArr[0] = "binding";
                break;
            case 3:
            case 5:
                objArr[0] = "nodes";
                break;
            case 6:
                objArr[0] = "targetElement";
                break;
            default:
                objArr[0] = "originalType";
                break;
        }
        objArr[1] = "org/jetbrains/kotlin/com/intellij/util/xmlb/Binding";
        if (i == 2 || i == 3) {
            objArr[2] = "deserializeList";
        } else if (i == 4 || i == 5) {
            objArr[2] = "deserializeList2";
        } else if (i != 6) {
            objArr[2] = ConfigConstants.CONFIG_INIT_SECTION;
        } else {
            objArr[2] = "addContent";
        }
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
    }

    Object deserializeUnsafe(Object obj, XmlElement xmlElement);

    default void init(Type type, Serializer serializer) {
        if (type == null) {
            $$$reportNull$$$0(0);
        }
        if (serializer == null) {
            $$$reportNull$$$0(1);
        }
    }

    boolean isBoundTo(XmlElement xmlElement);

    static Object deserializeList2(Binding binding, Object obj, List<XmlElement> list) {
        if (binding == null) {
            $$$reportNull$$$0(4);
        }
        if (list == null) {
            $$$reportNull$$$0(5);
        }
        if (binding instanceof MultiNodeBinding) {
            return ((MultiNodeBinding) binding).deserializeList2(obj, list);
        }
        if (list.size() == 1) {
            return binding.deserializeUnsafe(obj, list.get(0));
        }
        if (list.isEmpty()) {
            return null;
        }
        throw new AssertionError("Duplicate data for " + binding + " will be ignored");
    }
}
