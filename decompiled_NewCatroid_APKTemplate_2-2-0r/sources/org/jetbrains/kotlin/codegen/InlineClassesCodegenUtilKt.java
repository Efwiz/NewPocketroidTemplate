package org.jetbrains.kotlin.codegen;

import com.reandroid.dex.sections.Marker;
import java.util.Collection;
import java.util.Iterator;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref;
import org.jetbrains.kotlin.codegen.state.GenerationState;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.descriptors.CallableMemberDescriptor;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.ClassifierDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.descriptors.InlineClassRepresentation;
import org.jetbrains.kotlin.descriptors.ValueParameterDescriptor;
import org.jetbrains.kotlin.descriptors.impl.AnonymousFunctionDescriptor;
import org.jetbrains.kotlin.load.kotlin.JvmPackagePartSource;
import org.jetbrains.kotlin.load.kotlin.VirtualFileFinder;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.resolve.descriptorUtil.DescriptorUtilsKt;
import org.jetbrains.kotlin.serialization.deserialization.descriptors.DeserializedClassDescriptor;
import org.jetbrains.kotlin.serialization.deserialization.descriptors.DeserializedContainerSource;
import org.jetbrains.kotlin.serialization.deserialization.descriptors.DeserializedSimpleFunctionDescriptor;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.SimpleType;
import org.jetbrains.kotlin.types.typeUtil.TypeUtilsKt;
import org.jetbrains.org.objectweb.asm.ClassReader;
import org.jetbrains.org.objectweb.asm.ClassVisitor;
import org.jetbrains.org.objectweb.asm.MethodVisitor;
import org.jetbrains.org.objectweb.asm.commons.Method;

/* JADX INFO: compiled from: inlineClassesCodegenUtil.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u00000\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a%\u0010\u0000\u001a\u0004\u0018\u00010\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\b\u001a%\u0010\u0000\u001a\u0004\u0018\u00010\u00012\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\u000b\u001a\n\u0010\f\u001a\u00020\u0001*\u00020\r\u001a\n\u0010\u000e\u001a\u00020\u0001*\u00020\u000f¨\u0006\u0010"}, d2 = {"classFileContainsMethod", "", "descriptor", "Lorg/jetbrains/kotlin/descriptors/FunctionDescriptor;", "state", "Lorg/jetbrains/kotlin/codegen/state/GenerationState;", "method", "Lorg/jetbrains/org/objectweb/asm/commons/Method;", "(Lorg/jetbrains/kotlin/descriptors/FunctionDescriptor;Lorg/jetbrains/kotlin/codegen/state/GenerationState;Lorg/jetbrains/org/objectweb/asm/commons/Method;)Ljava/lang/Boolean;", "classId", "Lorg/jetbrains/kotlin/name/ClassId;", "(Lorg/jetbrains/kotlin/name/ClassId;Lorg/jetbrains/kotlin/codegen/state/GenerationState;Lorg/jetbrains/org/objectweb/asm/commons/Method;)Ljava/lang/Boolean;", "isGenericParameter", "Lorg/jetbrains/kotlin/descriptors/CallableDescriptor;", "isInlineClassWithUnderlyingTypeAnyOrAnyN", "Lorg/jetbrains/kotlin/types/KotlinType;", Marker.BACKEND}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class InlineClassesCodegenUtilKt {
    public static final boolean isInlineClassWithUnderlyingTypeAnyOrAnyN(KotlinType kotlinType) {
        InlineClassRepresentation<SimpleType> inlineClassRepresentation;
        SimpleType simpleType;
        Intrinsics.checkNotNullParameter(kotlinType, "<this>");
        ClassifierDescriptor this$0 = kotlinType.getConstructor().getThis$0();
        return (this$0 instanceof ClassDescriptor) && (inlineClassRepresentation = DescriptorUtilsKt.getInlineClassRepresentation((ClassDescriptor) this$0)) != null && (simpleType = (SimpleType) inlineClassRepresentation.getUnderlyingType()) != null && TypeUtilsKt.isAnyOrNullableAny(simpleType);
    }

    public static final boolean isGenericParameter(CallableDescriptor callableDescriptor) {
        Intrinsics.checkNotNullParameter(callableDescriptor, "<this>");
        if (!(callableDescriptor instanceof ValueParameterDescriptor)) {
            return false;
        }
        ValueParameterDescriptor valueParameterDescriptor = (ValueParameterDescriptor) callableDescriptor;
        if (valueParameterDescriptor.getContainingModule() instanceof AnonymousFunctionDescriptor) {
            return true;
        }
        int iIndexOf = valueParameterDescriptor.getContainingModule().getValueParameters().indexOf(callableDescriptor);
        Collection<? extends CallableDescriptor> overriddenDescriptors = valueParameterDescriptor.getContainingModule().getOverriddenDescriptors();
        Intrinsics.checkNotNullExpressionValue(overriddenDescriptors, "getOverriddenDescriptors(...)");
        Collection<? extends CallableDescriptor> collection = overriddenDescriptors;
        if (collection.isEmpty()) {
            return false;
        }
        Iterator<T> it = collection.iterator();
        while (it.hasNext()) {
            KotlinType type = ((CallableDescriptor) it.next()).getOriginal().getValueParameters().get(iIndexOf).getType();
            Intrinsics.checkNotNullExpressionValue(type, "getType(...)");
            if (TypeUtilsKt.isTypeParameter(type)) {
                return true;
            }
        }
        return false;
    }

    public static final Boolean classFileContainsMethod(FunctionDescriptor descriptor, GenerationState state, Method method) {
        ClassId classId;
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        Intrinsics.checkNotNullParameter(state, "state");
        Intrinsics.checkNotNullParameter(method, "method");
        if (!(descriptor instanceof DeserializedSimpleFunctionDescriptor)) {
            return null;
        }
        DeserializedSimpleFunctionDescriptor deserializedSimpleFunctionDescriptor = (DeserializedSimpleFunctionDescriptor) descriptor;
        if (deserializedSimpleFunctionDescriptor.getKind() == CallableMemberDescriptor.Kind.FAKE_OVERRIDE) {
            Collection<? extends FunctionDescriptor> overriddenDescriptors = deserializedSimpleFunctionDescriptor.getOverriddenDescriptors();
            Intrinsics.checkNotNullExpressionValue(overriddenDescriptors, "getOverriddenDescriptors(...)");
            Collection<? extends FunctionDescriptor> collection = overriddenDescriptors;
            boolean z = false;
            if (!collection.isEmpty()) {
                for (FunctionDescriptor functionDescriptor : collection) {
                    Intrinsics.checkNotNull(functionDescriptor);
                    if (Intrinsics.areEqual((Object) classFileContainsMethod(functionDescriptor, state, method), (Object) true)) {
                        z = true;
                        break;
                    }
                }
            }
            return Boolean.valueOf(z);
        }
        if (deserializedSimpleFunctionDescriptor.getContainingModule() instanceof DeserializedClassDescriptor) {
            DeclarationDescriptor containingDeclaration = deserializedSimpleFunctionDescriptor.getContainingModule();
            Intrinsics.checkNotNull(containingDeclaration, "null cannot be cast to non-null type org.jetbrains.kotlin.serialization.deserialization.descriptors.DeserializedClassDescriptor");
            classId = DescriptorUtilsKt.getClassId((ClassifierDescriptor) containingDeclaration);
            if (classId == null) {
                return null;
            }
        } else {
            if (!(deserializedSimpleFunctionDescriptor.getContainerSource() instanceof JvmPackagePartSource)) {
                return null;
            }
            DeserializedContainerSource containerSource = deserializedSimpleFunctionDescriptor.getContainerSource();
            Intrinsics.checkNotNull(containerSource, "null cannot be cast to non-null type org.jetbrains.kotlin.load.kotlin.JvmPackagePartSource");
            classId = ((JvmPackagePartSource) containerSource).getClassId();
        }
        return classFileContainsMethod(classId, state, method);
    }

    public static final Boolean classFileContainsMethod(ClassId classId, GenerationState state, final Method method) {
        byte[] bArrContentsToByteArray;
        Intrinsics.checkNotNullParameter(classId, "classId");
        Intrinsics.checkNotNullParameter(state, "state");
        Intrinsics.checkNotNullParameter(method, "method");
        VirtualFile virtualFileFindVirtualFileWithHeader = VirtualFileFinder.INSTANCE.getInstance(state.getProject(), state.getModule()).findVirtualFileWithHeader(classId);
        if (virtualFileFindVirtualFileWithHeader == null || (bArrContentsToByteArray = virtualFileFindVirtualFileWithHeader.contentsToByteArray()) == null) {
            return null;
        }
        final Ref.BooleanRef booleanRef = new Ref.BooleanRef();
        new ClassReader(bArrContentsToByteArray).accept(new ClassVisitor() { // from class: org.jetbrains.kotlin.codegen.InlineClassesCodegenUtilKt.classFileContainsMethod.2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(589824);
            }

            @Override // org.jetbrains.org.objectweb.asm.ClassVisitor
            public MethodVisitor visitMethod(int access, String name2, String descriptor, String signature, String[] exceptions) {
                if (Intrinsics.areEqual(name2, method.getName()) && Intrinsics.areEqual(descriptor, method.getDescriptor())) {
                    booleanRef.element = true;
                }
                return super.visitMethod(access, name2, descriptor, signature, exceptions);
            }
        }, 4);
        return Boolean.valueOf(booleanRef.element);
    }
}
