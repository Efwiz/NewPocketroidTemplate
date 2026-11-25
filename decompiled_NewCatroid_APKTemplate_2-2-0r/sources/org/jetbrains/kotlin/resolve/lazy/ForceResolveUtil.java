package org.jetbrains.kotlin.resolve.lazy;

import com.reandroid.arsc.container.SpecTypePair;
import java.util.Collection;
import java.util.Iterator;
import java.util.function.Consumer;
import org.jetbrains.kotlin.com.intellij.openapi.diagnostic.Logger;
import org.jetbrains.kotlin.com.intellij.openapi.progress.ProgressManager;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.ReceiverParameterDescriptor;
import org.jetbrains.kotlin.descriptors.TypeAliasDescriptor;
import org.jetbrains.kotlin.descriptors.TypeParameterDescriptor;
import org.jetbrains.kotlin.descriptors.ValueParameterDescriptor;
import org.jetbrains.kotlin.descriptors.annotations.AnnotationDescriptor;
import org.jetbrains.kotlin.descriptors.annotations.Annotations;
import org.jetbrains.kotlin.descriptors.impl.ValueParameterDescriptorImpl;
import org.jetbrains.kotlin.resolve.DescriptorUtils;
import org.jetbrains.kotlin.resolve.scopes.MemberScope;
import org.jetbrains.kotlin.types.FlexibleTypesKt;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.TypeConstructor;
import org.jetbrains.kotlin.types.TypeProjection;

/* JADX INFO: loaded from: classes6.dex */
public class ForceResolveUtil {
    private static final Logger LOG = Logger.getInstance((Class<?>) ForceResolveUtil.class);

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[3];
        if (i == 1) {
            objArr[0] = "scope";
        } else if (i == 2) {
            objArr[0] = "descriptors";
        } else if (i == 3) {
            objArr[0] = SpecTypePair.NAME_types;
        } else if (i == 4) {
            objArr[0] = "typeConstructor";
        } else if (i != 5) {
            objArr[0] = "descriptor";
        } else {
            objArr[0] = "annotations";
        }
        objArr[1] = "org/jetbrains/kotlin/resolve/lazy/ForceResolveUtil";
        objArr[2] = "forceResolveAllContents";
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
    }

    private ForceResolveUtil() {
    }

    public static <T> T forceResolveAllContents(T t) {
        if (t == null) {
            $$$reportNull$$$0(0);
        }
        doForceResolveAllContents(t);
        return t;
    }

    public static void forceResolveAllContents(MemberScope memberScope) {
        if (memberScope == null) {
            $$$reportNull$$$0(1);
        }
        forceResolveAllContents((Iterable<? extends DeclarationDescriptor>) DescriptorUtils.getAllDescriptors(memberScope));
    }

    public static void forceResolveAllContents(Iterable<? extends DeclarationDescriptor> iterable) {
        if (iterable == null) {
            $$$reportNull$$$0(2);
        }
        Iterator<? extends DeclarationDescriptor> it = iterable.iterator();
        while (it.hasNext()) {
            forceResolveAllContents(it.next());
        }
    }

    public static void forceResolveAllContents(Collection<KotlinType> collection) {
        if (collection == null) {
            $$$reportNull$$$0(3);
        }
        Iterator<KotlinType> it = collection.iterator();
        while (it.hasNext()) {
            forceResolveAllContents(it.next());
        }
    }

    public static void forceResolveAllContents(TypeConstructor typeConstructor) {
        if (typeConstructor == null) {
            $$$reportNull$$$0(4);
        }
        doForceResolveAllContents(typeConstructor);
    }

    public static void forceResolveAllContents(Annotations annotations) {
        if (annotations == null) {
            $$$reportNull$$$0(5);
        }
        doForceResolveAllContents(annotations);
        Iterator<AnnotationDescriptor> it = annotations.iterator();
        while (it.hasNext()) {
            doForceResolveAllContents(it.next());
        }
    }

    private static void doForceResolveAllContents(Object obj) {
        ProgressManager.checkCanceled();
        if (obj instanceof LazyEntity) {
            ((LazyEntity) obj).forceResolveAllContents();
            return;
        }
        if (obj instanceof ValueParameterDescriptorImpl.WithDestructuringDeclaration) {
            ((ValueParameterDescriptorImpl.WithDestructuringDeclaration) obj).getDestructuringVariables();
            return;
        }
        if (obj instanceof CallableDescriptor) {
            CallableDescriptor callableDescriptor = (CallableDescriptor) obj;
            callableDescriptor.getContextReceiverParameters().forEach(new Consumer() { // from class: org.jetbrains.kotlin.resolve.lazy.ForceResolveUtil$$ExternalSyntheticLambda0
                @Override // java.util.function.Consumer
                public final void accept(Object obj2) {
                    ForceResolveUtil.forceResolveAllContents(((ReceiverParameterDescriptor) obj2).getType());
                }
            });
            ReceiverParameterDescriptor extensionReceiverParameter = callableDescriptor.getExtensionReceiverParameter();
            if (extensionReceiverParameter != null) {
                forceResolveAllContents(extensionReceiverParameter.getType());
            }
            Iterator<ValueParameterDescriptor> it = callableDescriptor.getValueParameters().iterator();
            while (it.hasNext()) {
                forceResolveAllContents(it.next());
            }
            Iterator<TypeParameterDescriptor> it2 = callableDescriptor.getTypeParameters().iterator();
            while (it2.hasNext()) {
                forceResolveAllContents((Collection<KotlinType>) it2.next().getUpperBounds());
            }
            forceResolveAllContents(callableDescriptor.getReturnType());
            forceResolveAllContents(callableDescriptor.getAnnotations());
            return;
        }
        if (obj instanceof TypeAliasDescriptor) {
            forceResolveAllContents((KotlinType) ((TypeAliasDescriptor) obj).getUnderlyingType());
        }
    }

    public static KotlinType forceResolveAllContents(KotlinType kotlinType) {
        if (kotlinType == null) {
            return null;
        }
        forceResolveAllContents(kotlinType.getAnnotations());
        if (FlexibleTypesKt.isFlexible(kotlinType)) {
            forceResolveAllContents((KotlinType) FlexibleTypesKt.asFlexibleType(kotlinType).getLowerBound());
            forceResolveAllContents((KotlinType) FlexibleTypesKt.asFlexibleType(kotlinType).getUpperBound());
        } else {
            forceResolveAllContents(kotlinType.getConstructor());
            for (TypeProjection typeProjection : kotlinType.getArguments()) {
                if (!typeProjection.isStarProjection()) {
                    forceResolveAllContents(typeProjection.getNullableAnyType());
                }
            }
        }
        return kotlinType;
    }
}
