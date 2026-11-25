package org.jetbrains.kotlin.codegen.context;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import org.jetbrains.kotlin.codegen.AccessorForPropertyDescriptor;
import org.jetbrains.kotlin.codegen.OwnerKind;
import org.jetbrains.kotlin.codegen.binding.MutableClosure;
import org.jetbrains.kotlin.codegen.state.KotlinTypeMapper;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.PropertyDescriptor;
import org.jetbrains.kotlin.resolve.jvm.annotations.JvmAnnotationUtilKt;

/* JADX INFO: loaded from: classes3.dex */
public abstract class FieldOwnerContext<T extends DeclarationDescriptor> extends CodegenContext<T> {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private final Map<String, Map<PropertyDescriptor, String>> fieldNames;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 3 || i == 4) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 3 || i == 4) ? 2 : 3];
        if (i == 1) {
            objArr[0] = "contextKind";
        } else if (i == 2) {
            objArr[0] = "possiblySubstitutedDescriptor";
        } else if (i == 3 || i == 4) {
            objArr[0] = "org/jetbrains/kotlin/codegen/context/FieldOwnerContext";
        } else {
            objArr[0] = "contextDescriptor";
        }
        if (i == 3 || i == 4) {
            objArr[1] = "getFieldName";
        } else {
            objArr[1] = "org/jetbrains/kotlin/codegen/context/FieldOwnerContext";
        }
        if (i == 2) {
            objArr[2] = "getFieldName";
        } else if (i != 3 && i != 4) {
            objArr[2] = "<init>";
        }
        String str2 = String.format(str, objArr);
        if (i != 3 && i != 4) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public FieldOwnerContext(T t, OwnerKind ownerKind, CodegenContext codegenContext, MutableClosure mutableClosure, ClassDescriptor classDescriptor, LocalLookup localLookup) {
        super(t, ownerKind, codegenContext, mutableClosure, classDescriptor, localLookup);
        if (t == null) {
            $$$reportNull$$$0(0);
        }
        if (ownerKind == null) {
            $$$reportNull$$$0(1);
        }
        this.fieldNames = new HashMap();
    }

    public String getFieldName(PropertyDescriptor propertyDescriptor, boolean z) {
        if (propertyDescriptor == null) {
            $$$reportNull$$$0(2);
        }
        if (propertyDescriptor instanceof AccessorForPropertyDescriptor) {
            propertyDescriptor = ((AccessorForPropertyDescriptor) propertyDescriptor).getCalleeDescriptor();
        }
        PropertyDescriptor original = propertyDescriptor.getOriginal();
        String strMapDefaultFieldName = KotlinTypeMapper.mapDefaultFieldName(original, z);
        Map<PropertyDescriptor, String> mapComputeIfAbsent = this.fieldNames.computeIfAbsent(strMapDefaultFieldName, new Function() { // from class: org.jetbrains.kotlin.codegen.context.FieldOwnerContext$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return FieldOwnerContext.lambda$getFieldName$0((String) obj);
            }
        });
        String str = mapComputeIfAbsent.get(original);
        if (str != null) {
            if (str == null) {
                $$$reportNull$$$0(3);
            }
            return str;
        }
        if (!mapComputeIfAbsent.isEmpty() && !JvmAnnotationUtilKt.hasJvmFieldAnnotation(original)) {
            strMapDefaultFieldName = strMapDefaultFieldName + "$" + mapComputeIfAbsent.size();
        }
        mapComputeIfAbsent.put(original, strMapDefaultFieldName);
        if (strMapDefaultFieldName == null) {
            $$$reportNull$$$0(4);
        }
        return strMapDefaultFieldName;
    }

    static /* synthetic */ Map lambda$getFieldName$0(String str) {
        return new HashMap();
    }
}
