package org.jetbrains.kotlin.backend.jvm.lower;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.backend.common.phaser.DumperVerifierKt;
import org.jetbrains.kotlin.backend.common.phaser.NamedCompilerPhase;
import org.jetbrains.kotlin.backend.common.phaser.PhaseBuildersKt;
import org.jetbrains.kotlin.backend.jvm.JvmBackendContext;
import org.jetbrains.kotlin.builtins.PrimitiveType;
import org.jetbrains.kotlin.builtins.StandardNames;
import org.jetbrains.kotlin.builtins.UnsignedType;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationParent;
import org.jetbrains.kotlin.ir.declarations.IrFile;
import org.jetbrains.kotlin.ir.declarations.IrFunction;
import org.jetbrains.kotlin.ir.declarations.IrPackageFragment;
import org.jetbrains.kotlin.ir.util.AdditionalIrUtilsKt;
import org.jetbrains.kotlin.ir.util.IrUtilsKt;
import org.jetbrains.kotlin.util.capitalizeDecapitalize.CapitalizeDecapitalizeKt;

/* JADX INFO: compiled from: VarargLowering.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u00000\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\"\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a\f\u0010\r\u001a\u00020\u000e*\u00020\u000fH\u0000\u001a\f\u0010\u0010\u001a\u00020\u000e*\u00020\u000fH\u0000\"\u000e\u0010\u0000\u001a\u00020\u0001X\u0080T¢\u0006\u0002\n\u0000\"\u001a\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00010\u0003X\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0004\u0010\u0005\"-\u0010\u0006\u001a\u001e\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\t0\u0007j\u000e\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\t`\n¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\f¨\u0006\u0011"}, d2 = {"ARRAY_OF_NAME", "", "PRIMITIVE_ARRAY_OF_NAMES", "", "getPRIMITIVE_ARRAY_OF_NAMES", "()Ljava/util/Set;", "varargPhase", "Lorg/jetbrains/kotlin/backend/common/phaser/NamedCompilerPhase;", "Lorg/jetbrains/kotlin/backend/jvm/JvmBackendContext;", "Lorg/jetbrains/kotlin/ir/declarations/IrFile;", "Lorg/jetbrains/kotlin/backend/common/phaser/SameTypeNamedCompilerPhase;", "getVarargPhase", "()Lorg/jetbrains/kotlin/backend/common/phaser/NamedCompilerPhase;", "isArrayOf", "", "Lorg/jetbrains/kotlin/ir/declarations/IrFunction;", "isEmptyArray", "backend.jvm.lower"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class VarargLoweringKt {
    public static final String ARRAY_OF_NAME = "arrayOf";
    private static final Set<String> PRIMITIVE_ARRAY_OF_NAMES;
    private static final NamedCompilerPhase<JvmBackendContext, IrFile> varargPhase = PhaseBuildersKt.makeIrFilePhase(VarargLoweringKt$varargPhase$1.INSTANCE, "VarargLowering", "Replace varargs with array arguments and lower arrayOf and emptyArray calls", (248 & 8) != 0 ? SetsKt.emptySet() : SetsKt.setOf(PolymorphicSignatureLoweringKt.getPolymorphicSignaturePhase()), (248 & 16) != 0 ? SetsKt.emptySet() : null, (248 & 32) != 0 ? SetsKt.emptySet() : null, (248 & 64) != 0 ? SetsKt.emptySet() : null, (248 & 128) != 0 ? SetsKt.setOf((Object[]) new Function3[]{DumperVerifierKt.getDefaultDumper(), DumperVerifierKt.getValidationAction()}) : null);

    public static final NamedCompilerPhase<JvmBackendContext, IrFile> getVarargPhase() {
        return varargPhase;
    }

    static {
        PrimitiveType[] primitiveTypeArrValues = PrimitiveType.values();
        ArrayList arrayList = new ArrayList(primitiveTypeArrValues.length);
        for (PrimitiveType primitiveType : primitiveTypeArrValues) {
            arrayList.add(primitiveType.name());
        }
        ArrayList arrayList2 = arrayList;
        UnsignedType[] unsignedTypeArrValues = UnsignedType.values();
        ArrayList arrayList3 = new ArrayList(unsignedTypeArrValues.length);
        for (UnsignedType unsignedType : unsignedTypeArrValues) {
            String strAsString = unsignedType.getTypeName().asString();
            Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
            arrayList3.add(strAsString);
        }
        List listPlus = CollectionsKt.plus((Collection) arrayList2, (Iterable) arrayList3);
        ArrayList arrayList4 = new ArrayList(CollectionsKt.collectionSizeOrDefault(listPlus, 10));
        Iterator it = listPlus.iterator();
        while (it.hasNext()) {
            arrayList4.add(CapitalizeDecapitalizeKt.toLowerCaseAsciiOnly((String) it.next()) + "ArrayOf");
        }
        PRIMITIVE_ARRAY_OF_NAMES = CollectionsKt.toSet(arrayList4);
    }

    public static final Set<String> getPRIMITIVE_ARRAY_OF_NAMES() {
        return PRIMITIVE_ARRAY_OF_NAMES;
    }

    public static final boolean isArrayOf(IrFunction irFunction) {
        IrPackageFragment packageFragment;
        Intrinsics.checkNotNullParameter(irFunction, "<this>");
        IrDeclarationParent parent = irFunction.getParent();
        if (parent instanceof IrClass) {
            packageFragment = IrUtilsKt.getPackageFragment((IrDeclaration) parent);
        } else {
            if (!(parent instanceof IrPackageFragment)) {
                return false;
            }
            packageFragment = (IrPackageFragment) parent;
        }
        if (!Intrinsics.areEqual(packageFragment.getPackageFqName(), StandardNames.BUILT_INS_PACKAGE_FQ_NAME)) {
            return false;
        }
        String strAsString = irFunction.getName().asString();
        return (PRIMITIVE_ARRAY_OF_NAMES.contains(strAsString) || Intrinsics.areEqual(strAsString, ARRAY_OF_NAME)) && irFunction.getExtensionReceiverParameter() == null && irFunction.getDispatchReceiverParameter() == null && irFunction.getValueParameters().size() == 1 && AdditionalIrUtilsKt.isVararg(irFunction.getValueParameters().get(0));
    }

    public static final boolean isEmptyArray(IrFunction irFunction) {
        Intrinsics.checkNotNullParameter(irFunction, "<this>");
        if (Intrinsics.areEqual(irFunction.getName().asString(), "emptyArray")) {
            IrDeclarationParent parent = irFunction.getParent();
            IrPackageFragment irPackageFragment = parent instanceof IrPackageFragment ? (IrPackageFragment) parent : null;
            if (Intrinsics.areEqual(irPackageFragment != null ? irPackageFragment.getPackageFqName() : null, StandardNames.BUILT_INS_PACKAGE_FQ_NAME)) {
                return true;
            }
        }
        return false;
    }
}
