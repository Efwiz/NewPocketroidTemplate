package org.jetbrains.kotlin.codegen.intrinsics;

import androidx.exifinterface.media.ExifInterface;
import com.reandroid.dex.sections.Marker;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.backend.jvm.lower.VarargLoweringKt;
import org.jetbrains.kotlin.builtins.StandardNames;
import org.jetbrains.kotlin.codegen.AsmUtil;
import org.jetbrains.kotlin.codegen.StackValue;
import org.jetbrains.kotlin.codegen.inline.ReificationArgument;
import org.jetbrains.kotlin.codegen.inline.ReifiedTypeInliner;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.descriptors.PackageFragmentDescriptor;
import org.jetbrains.kotlin.resolve.jvm.AsmTypes;
import org.jetbrains.kotlin.util.OperatorNameConventions;
import org.jetbrains.org.objectweb.asm.Label;
import org.jetbrains.org.objectweb.asm.Type;
import org.jetbrains.org.objectweb.asm.commons.InstructionAdapter;
import org.jetbrains.org.objectweb.asm.commons.Method;
import org.jetbrains.org.objectweb.asm.tree.MethodNode;

/* JADX INFO: compiled from: IntrinsicArrayConstructors.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\bÀ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000e\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nJ\u000e\u0010\u000b\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nJ\u000e\u0010\f\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nJ\u000e\u0010\r\u001a\u00020\u00042\u0006\u0010\u000e\u001a\u00020\u000fJ\u000e\u0010\u0010\u001a\u00020\u00042\u0006\u0010\u000e\u001a\u00020\u000fR\u0018\u0010\u0003\u001a\u00020\u0004*\u00020\u00058BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u0003\u0010\u0006¨\u0006\u0011"}, d2 = {"Lorg/jetbrains/kotlin/codegen/intrinsics/IntrinsicArrayConstructors;", "", "()V", "isBuiltInsPackage", "", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "(Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;)Z", "generateArrayConstructorBody", "Lorg/jetbrains/org/objectweb/asm/tree/MethodNode;", "method", "Lorg/jetbrains/org/objectweb/asm/commons/Method;", "generateArrayOfBody", "generateEmptyArrayBody", "isArrayOf", "descriptor", "Lorg/jetbrains/kotlin/descriptors/FunctionDescriptor;", "isEmptyArray", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class IntrinsicArrayConstructors {
    public static final IntrinsicArrayConstructors INSTANCE = new IntrinsicArrayConstructors();

    private IntrinsicArrayConstructors() {
    }

    public final boolean isArrayOf(FunctionDescriptor descriptor) {
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        if (Intrinsics.areEqual(descriptor.getShortName().asString(), VarargLoweringKt.ARRAY_OF_NAME)) {
            DeclarationDescriptor containingDeclaration = descriptor.getContainingDeclaration();
            Intrinsics.checkNotNullExpressionValue(containingDeclaration, "getContainingDeclaration(...)");
            if (isBuiltInsPackage(containingDeclaration)) {
                return true;
            }
        }
        return false;
    }

    public final boolean isEmptyArray(FunctionDescriptor descriptor) {
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        if (Intrinsics.areEqual(descriptor.getShortName().asString(), "emptyArray")) {
            DeclarationDescriptor containingDeclaration = descriptor.getContainingDeclaration();
            Intrinsics.checkNotNullExpressionValue(containingDeclaration, "getContainingDeclaration(...)");
            if (isBuiltInsPackage(containingDeclaration)) {
                return true;
            }
        }
        return false;
    }

    private final boolean isBuiltInsPackage(DeclarationDescriptor declarationDescriptor) {
        return (declarationDescriptor instanceof PackageFragmentDescriptor) && Intrinsics.areEqual(((PackageFragmentDescriptor) declarationDescriptor).getFqName(), StandardNames.BUILT_INS_PACKAGE_FQ_NAME);
    }

    public final MethodNode generateArrayConstructorBody(Method method) {
        Intrinsics.checkNotNullParameter(method, "method");
        MethodNode methodNode = new MethodNode(393216, 25, method.getName(), method.getDescriptor(), null, null);
        Type returnType = method.getReturnType();
        Type typeCorrectElementType = AsmUtil.correctElementType(returnType);
        Intrinsics.checkNotNullExpressionValue(typeCorrectElementType, "correctElementType(...)");
        StackValue.Local local = StackValue.local(0, Type.INT_TYPE);
        Intrinsics.checkNotNullExpressionValue(local, "local(...)");
        StackValue.Local local2 = StackValue.local(1, AsmTypes.FUNCTION1);
        Intrinsics.checkNotNullExpressionValue(local2, "local(...)");
        StackValue.Local local3 = StackValue.local(2, Type.INT_TYPE);
        Intrinsics.checkNotNullExpressionValue(local3, "local(...)");
        StackValue.Local local4 = StackValue.local(3, returnType);
        Intrinsics.checkNotNullExpressionValue(local4, "local(...)");
        InstructionAdapter instructionAdapter = new InstructionAdapter(methodNode);
        local.put(instructionAdapter);
        if (typeCorrectElementType.getSort() == 10) {
            ReifiedTypeInliner.INSTANCE.putReifiedOperationMarker(ReifiedTypeInliner.OperationKind.NEW_ARRAY, new ReificationArgument(ExifInterface.GPS_DIRECTION_TRUE, true, 0), instructionAdapter);
        }
        instructionAdapter.newarray(typeCorrectElementType);
        local4.store(StackValue.onStack(returnType), instructionAdapter);
        local3.store(StackValue.constant(0), instructionAdapter);
        Label label = new Label();
        instructionAdapter.visitLabel(label);
        local3.put(instructionAdapter);
        local.put(instructionAdapter);
        Label label2 = new Label();
        instructionAdapter.ificmpge(label2);
        local4.put(instructionAdapter);
        local3.put(instructionAdapter);
        local2.put(instructionAdapter);
        local3.put(AsmUtil.boxType(Type.INT_TYPE), instructionAdapter);
        instructionAdapter.invokeinterface(AsmTypes.FUNCTION1.getInternalName(), OperatorNameConventions.INVOKE.asString(), Type.getMethodDescriptor(AsmTypes.OBJECT_TYPE, AsmTypes.OBJECT_TYPE));
        StackValue.coerce(AsmTypes.OBJECT_TYPE, typeCorrectElementType, instructionAdapter);
        instructionAdapter.astore(typeCorrectElementType);
        instructionAdapter.iinc(local3.index, 1);
        instructionAdapter.goTo(label);
        instructionAdapter.visitLabel(label2);
        local4.put(instructionAdapter);
        instructionAdapter.areturn(returnType);
        instructionAdapter.visitMaxs(5, 4);
        return methodNode;
    }

    public final MethodNode generateEmptyArrayBody(Method method) {
        Intrinsics.checkNotNullParameter(method, "method");
        MethodNode methodNode = new MethodNode(393216, 25, method.getName(), method.getDescriptor(), null, null);
        InstructionAdapter instructionAdapter = new InstructionAdapter(methodNode);
        instructionAdapter.iconst(0);
        ReifiedTypeInliner.INSTANCE.putReifiedOperationMarker(ReifiedTypeInliner.OperationKind.NEW_ARRAY, new ReificationArgument(ExifInterface.GPS_DIRECTION_TRUE, true, 0), instructionAdapter);
        instructionAdapter.newarray(AsmTypes.OBJECT_TYPE);
        instructionAdapter.areturn(AsmTypes.OBJECT_TYPE);
        instructionAdapter.visitMaxs(3, 1);
        return methodNode;
    }

    public final MethodNode generateArrayOfBody(Method method) {
        Intrinsics.checkNotNullParameter(method, "method");
        MethodNode methodNode = new MethodNode(393216, 25, method.getName(), method.getDescriptor(), null, null);
        InstructionAdapter instructionAdapter = new InstructionAdapter(methodNode);
        instructionAdapter.load(0, AsmTypes.OBJECT_TYPE);
        instructionAdapter.areturn(AsmTypes.OBJECT_TYPE);
        instructionAdapter.visitMaxs(1, 1);
        return methodNode;
    }
}
