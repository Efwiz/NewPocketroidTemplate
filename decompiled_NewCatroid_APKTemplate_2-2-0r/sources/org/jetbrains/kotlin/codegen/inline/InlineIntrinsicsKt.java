package org.jetbrains.kotlin.codegen.inline;

import com.reandroid.dex.sections.Marker;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.backend.common.CommonCoroutineCodegenUtilKt;
import org.jetbrains.kotlin.builtins.KotlinBuiltIns;
import org.jetbrains.kotlin.builtins.StandardNames;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.codegen.AsmUtil;
import org.jetbrains.kotlin.codegen.AssertCodegenUtilKt;
import org.jetbrains.kotlin.codegen.coroutines.CoroutineCodegenUtilKt;
import org.jetbrains.kotlin.codegen.intrinsics.IntrinsicArrayConstructors;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.descriptors.PackageFragmentDescriptor;
import org.jetbrains.kotlin.descriptors.TypeParameterDescriptor;
import org.jetbrains.kotlin.descriptors.ValueParameterDescriptor;
import org.jetbrains.kotlin.resolve.calls.checkers.CoroutineCallCheckerKt;
import org.jetbrains.kotlin.resolve.calls.checkers.TypeOfChecker;
import org.jetbrains.kotlin.resolve.jvm.AsmTypes;
import org.jetbrains.kotlin.types.TypeSystemCommonBackendContext;
import org.jetbrains.kotlin.types.model.TypeParameterMarker;
import org.jetbrains.org.objectweb.asm.Type;
import org.jetbrains.org.objectweb.asm.commons.InstructionAdapter;
import org.jetbrains.org.objectweb.asm.commons.Method;
import org.jetbrains.org.objectweb.asm.tree.MethodNode;

/* JADX INFO: compiled from: inlineIntrinsics.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000<\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\u001a \u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0007H\u0002\u001a\"\u0010\b\u001a\u0004\u0018\u00010\t2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u0006\u001a\u00020\u0007H\u0000\u001a\u0010\u0010\u000e\u001a\u0004\u0018\u00010\t2\u0006\u0010\n\u001a\u00020\u000b\u001a\u0018\u0010\u000f\u001a\u00020\u00032\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u0013H\u0000\u001a\u0010\u0010\u0014\u001a\u00020\u00132\u0006\u0010\n\u001a\u00020\u000bH\u0002¨\u0006\u0015"}, d2 = {"createSpecialEnumMethodBody", "Lorg/jetbrains/org/objectweb/asm/tree/MethodNode;", "name", "", "typeParameter", "Lorg/jetbrains/kotlin/types/model/TypeParameterMarker;", "typeSystem", "Lorg/jetbrains/kotlin/types/TypeSystemCommonBackendContext;", "generateInlineIntrinsic", "Lorg/jetbrains/kotlin/codegen/inline/SMAPAndMethodNode;", "descriptor", "Lorg/jetbrains/kotlin/descriptors/FunctionDescriptor;", "asmMethod", "Lorg/jetbrains/org/objectweb/asm/commons/Method;", "generateInlineIntrinsicForIr", "getSpecialEnumFunDescriptor", ModuleXmlParser.TYPE, "Lorg/jetbrains/org/objectweb/asm/Type;", "isValueOf", "", "isSpecialEnumMethod", Marker.BACKEND}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class InlineIntrinsicsKt {
    public static final SMAPAndMethodNode generateInlineIntrinsicForIr(FunctionDescriptor descriptor) {
        MethodNode methodNodeCreateMethodNodeForSuspendCoroutineUninterceptedOrReturn;
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        if (CoroutineCallCheckerKt.isBuiltInCoroutineContext(descriptor)) {
            methodNodeCreateMethodNodeForSuspendCoroutineUninterceptedOrReturn = CoroutineCodegenUtilKt.createMethodNodeForCoroutineContext(descriptor);
        } else {
            methodNodeCreateMethodNodeForSuspendCoroutineUninterceptedOrReturn = CommonCoroutineCodegenUtilKt.isBuiltInSuspendCoroutineUninterceptedOrReturn(descriptor) ? CoroutineCodegenUtilKt.createMethodNodeForSuspendCoroutineUninterceptedOrReturn() : null;
        }
        if (methodNodeCreateMethodNodeForSuspendCoroutineUninterceptedOrReturn != null) {
            return new SMAPAndMethodNode(methodNodeCreateMethodNodeForSuspendCoroutineUninterceptedOrReturn, new SMAP(CollectionsKt.emptyList()));
        }
        return null;
    }

    public static final SMAPAndMethodNode generateInlineIntrinsic(FunctionDescriptor descriptor, Method asmMethod, TypeSystemCommonBackendContext typeSystem) {
        MethodNode methodNodeGenerateEmptyArrayBody;
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        Intrinsics.checkNotNullParameter(asmMethod, "asmMethod");
        Intrinsics.checkNotNullParameter(typeSystem, "typeSystem");
        SMAPAndMethodNode sMAPAndMethodNodeGenerateInlineIntrinsicForIr = generateInlineIntrinsicForIr(descriptor);
        if (sMAPAndMethodNodeGenerateInlineIntrinsicForIr != null) {
            return sMAPAndMethodNodeGenerateInlineIntrinsicForIr;
        }
        if (isSpecialEnumMethod(descriptor)) {
            String strAsString = descriptor.getShortName().asString();
            Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
            List<TypeParameterDescriptor> typeParameters = descriptor.getOriginal().getTypeParameters();
            Intrinsics.checkNotNullExpressionValue(typeParameters, "getTypeParameters(...)");
            Object objSingle = CollectionsKt.single((List<? extends Object>) typeParameters);
            Intrinsics.checkNotNullExpressionValue(objSingle, "single(...)");
            methodNodeGenerateEmptyArrayBody = createSpecialEnumMethodBody(strAsString, (TypeParameterMarker) objSingle, typeSystem);
        } else if (TypeOfChecker.INSTANCE.isTypeOf(descriptor)) {
            List<TypeParameterDescriptor> typeParameters2 = descriptor.getOriginal().getTypeParameters();
            Intrinsics.checkNotNullExpressionValue(typeParameters2, "getTypeParameters(...)");
            Object objSingle2 = CollectionsKt.single((List<? extends Object>) typeParameters2);
            Intrinsics.checkNotNullExpressionValue(objSingle2, "single(...)");
            methodNodeGenerateEmptyArrayBody = TypeOfKt.createTypeOfMethodBody(typeSystem, (TypeParameterMarker) objSingle2);
        } else if (AssertCodegenUtilKt.isBuiltinAlwaysEnabledAssert(descriptor)) {
            methodNodeGenerateEmptyArrayBody = AssertCodegenUtilKt.createMethodNodeForAlwaysEnabledAssert(descriptor);
        } else if (descriptor instanceof FictitiousArrayConstructor) {
            methodNodeGenerateEmptyArrayBody = IntrinsicArrayConstructors.INSTANCE.generateArrayConstructorBody(asmMethod);
        } else if (IntrinsicArrayConstructors.INSTANCE.isArrayOf(descriptor)) {
            methodNodeGenerateEmptyArrayBody = IntrinsicArrayConstructors.INSTANCE.generateArrayOfBody(asmMethod);
        } else {
            methodNodeGenerateEmptyArrayBody = IntrinsicArrayConstructors.INSTANCE.isEmptyArray(descriptor) ? IntrinsicArrayConstructors.INSTANCE.generateEmptyArrayBody(asmMethod) : null;
        }
        if (methodNodeGenerateEmptyArrayBody != null) {
            return new SMAPAndMethodNode(methodNodeGenerateEmptyArrayBody, new SMAP(CollectionsKt.emptyList()));
        }
        return null;
    }

    private static final boolean isSpecialEnumMethod(FunctionDescriptor functionDescriptor) {
        DeclarationDescriptor packageFragment = functionDescriptor.getContainingModule();
        PackageFragmentDescriptor packageFragmentDescriptor = packageFragment instanceof PackageFragmentDescriptor ? (PackageFragmentDescriptor) packageFragment : null;
        if (packageFragmentDescriptor == null || !Intrinsics.areEqual(packageFragmentDescriptor.getFqName(), StandardNames.BUILT_INS_PACKAGE_FQ_NAME) || functionDescriptor.getTypeParameters().size() != 1) {
            return false;
        }
        String strAsString = functionDescriptor.getShortName().asString();
        Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
        List<ValueParameterDescriptor> valueParameters = functionDescriptor.getValueParameters();
        Intrinsics.checkNotNullExpressionValue(valueParameters, "getValueParameters(...)");
        return (Intrinsics.areEqual(strAsString, "enumValues") && valueParameters.size() == 0) || (Intrinsics.areEqual(strAsString, "enumValueOf") && valueParameters.size() == 1 && KotlinBuiltIns.isString(valueParameters.get(0).getType()));
    }

    private static final MethodNode createSpecialEnumMethodBody(String str, TypeParameterMarker typeParameterMarker, TypeSystemCommonBackendContext typeSystemCommonBackendContext) {
        boolean zAreEqual = Intrinsics.areEqual("enumValueOf", str);
        Type ENUM_TYPE = AsmTypes.ENUM_TYPE;
        Intrinsics.checkNotNullExpressionValue(ENUM_TYPE, "ENUM_TYPE");
        MethodNode methodNode = new MethodNode(589824, 8, "fake", getSpecialEnumFunDescriptor(ENUM_TYPE, zAreEqual), null, null);
        ReifiedTypeInliner.INSTANCE.putReifiedOperationMarkerIfNeeded(typeParameterMarker, false, ReifiedTypeInliner.OperationKind.ENUM_REIFIED, new InstructionAdapter(methodNode), typeSystemCommonBackendContext);
        if (zAreEqual) {
            methodNode.visitInsn(1);
            methodNode.visitVarInsn(25, 0);
            methodNode.visitMethodInsn(184, AsmTypes.ENUM_TYPE.getInternalName(), "valueOf", Type.getMethodDescriptor(AsmTypes.ENUM_TYPE, AsmTypes.JAVA_CLASS_TYPE, AsmTypes.JAVA_STRING_TYPE), false);
            methodNode.visitTypeInsn(192, AsmTypes.ENUM_TYPE.getInternalName());
        } else {
            methodNode.visitInsn(3);
            methodNode.visitTypeInsn(189, AsmTypes.ENUM_TYPE.getInternalName());
            methodNode.visitTypeInsn(192, AsmUtil.getArrayType(AsmTypes.ENUM_TYPE).getInternalName());
        }
        methodNode.visitInsn(176);
        methodNode.visitMaxs(zAreEqual ? 3 : 2, zAreEqual ? 1 : 0);
        return methodNode;
    }

    public static final String getSpecialEnumFunDescriptor(Type type, boolean z) {
        Intrinsics.checkNotNullParameter(type, "type");
        if (z) {
            String methodDescriptor = Type.getMethodDescriptor(type, AsmTypes.JAVA_STRING_TYPE);
            Intrinsics.checkNotNullExpressionValue(methodDescriptor, "getMethodDescriptor(...)");
            return methodDescriptor;
        }
        String methodDescriptor2 = Type.getMethodDescriptor(AsmUtil.getArrayType(type), new Type[0]);
        Intrinsics.checkNotNullExpressionValue(methodDescriptor2, "getMethodDescriptor(...)");
        return methodDescriptor2;
    }
}
