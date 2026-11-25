package org.jetbrains.kotlin.backend.jvm.lower;

import java.util.Collection;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.SetsKt;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.backend.common.phaser.DumperVerifierKt;
import org.jetbrains.kotlin.backend.common.phaser.NamedCompilerPhase;
import org.jetbrains.kotlin.backend.common.phaser.PhaseBuildersKt;
import org.jetbrains.kotlin.backend.jvm.JvmBackendContext;
import org.jetbrains.kotlin.backend.jvm.JvmLoweredDeclarationOrigin;
import org.jetbrains.kotlin.backend.jvm.ir.JvmIrTypeUtilsKt;
import org.jetbrains.kotlin.backend.jvm.ir.JvmIrUtilsKt;
import org.jetbrains.kotlin.config.JvmDefaultMode;
import org.jetbrains.kotlin.descriptors.DescriptorVisibilities;
import org.jetbrains.kotlin.descriptors.Modality;
import org.jetbrains.kotlin.descriptors.deserialization.PlatformDependentDeclarationFilterKt;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationOrigin;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationParent;
import org.jetbrains.kotlin.ir.declarations.IrFile;
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction;
import org.jetbrains.kotlin.ir.expressions.IrExpression;
import org.jetbrains.kotlin.ir.expressions.IrTypeOperator;
import org.jetbrains.kotlin.ir.expressions.impl.IrTypeOperatorCallImpl;
import org.jetbrains.kotlin.ir.symbols.IrSimpleFunctionSymbol;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.ir.util.IrFakeOverrideUtilsKt;
import org.jetbrains.kotlin.ir.util.IrUtilsKt;
import org.jetbrains.kotlin.name.FqName;

/* JADX INFO: compiled from: InheritedDefaultMethodsOnClassesLowering.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u00008\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a\u0010\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u0012H\u0002\u001a\u0016\u0010\u0013\u001a\u0004\u0018\u00010\u0012*\u00020\u00122\u0006\u0010\u0014\u001a\u00020\u0015H\u0000\u001a\f\u0010\u0016\u001a\u00020\u0010*\u00020\u0012H\u0002\u001a \u0010\u0017\u001a\u00020\u0010*\u00020\u00122\u0006\u0010\u0014\u001a\u00020\u00152\n\b\u0002\u0010\u0018\u001a\u0004\u0018\u00010\u0012H\u0000\u001a\u0014\u0010\u0019\u001a\u00020\u001a*\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\u001cH\u0000\"0\u0010\u0000\u001a\u001e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00030\u0001j\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u0003`\u0004X\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"0\u0010\u0007\u001a\u001e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00030\u0001j\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u0003`\u0004X\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\u0006\"0\u0010\t\u001a\u001e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00030\u0001j\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u0003`\u0004X\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u0006\"0\u0010\u000b\u001a\u001e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00030\u0001j\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u0003`\u0004X\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\u0006\"0\u0010\r\u001a\u001e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00030\u0001j\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u0003`\u0004X\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u0006¨\u0006\u001d"}, d2 = {"inheritedDefaultMethodsOnClassesPhase", "Lorg/jetbrains/kotlin/backend/common/phaser/NamedCompilerPhase;", "Lorg/jetbrains/kotlin/backend/jvm/JvmBackendContext;", "Lorg/jetbrains/kotlin/ir/declarations/IrFile;", "Lorg/jetbrains/kotlin/backend/common/phaser/SameTypeNamedCompilerPhase;", "getInheritedDefaultMethodsOnClassesPhase", "()Lorg/jetbrains/kotlin/backend/common/phaser/NamedCompilerPhase;", "interfaceDefaultCallsPhase", "getInterfaceDefaultCallsPhase", "interfaceObjectCallsPhase", "getInterfaceObjectCallsPhase", "interfaceSuperCallsPhase", "getInterfaceSuperCallsPhase", "replaceDefaultImplsOverriddenSymbolsPhase", "getReplaceDefaultImplsOverriddenSymbolsPhase", "isDefaultImplsBridge", "", "f", "Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;", "findInterfaceImplementation", "jvmDefaultMode", "Lorg/jetbrains/kotlin/config/JvmDefaultMode;", "isCloneableClone", "isDefinitelyNotDefaultImplsMethod", "implementation", "reinterpretAsDispatchReceiverOfType", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "irType", "Lorg/jetbrains/kotlin/ir/types/IrType;", "backend.jvm.lower"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class InheritedDefaultMethodsOnClassesLoweringKt {
    private static final NamedCompilerPhase<JvmBackendContext, IrFile> inheritedDefaultMethodsOnClassesPhase = PhaseBuildersKt.makeIrFilePhase(InheritedDefaultMethodsOnClassesLoweringKt$inheritedDefaultMethodsOnClassesPhase$1.INSTANCE, "InheritedDefaultMethodsOnClasses", "Add bridge-implementations in classes that inherit default implementations from interfaces", (248 & 8) != 0 ? SetsKt.emptySet() : null, (248 & 16) != 0 ? SetsKt.emptySet() : null, (248 & 32) != 0 ? SetsKt.emptySet() : null, (248 & 64) != 0 ? SetsKt.emptySet() : null, (248 & 128) != 0 ? SetsKt.setOf((Object[]) new Function3[]{DumperVerifierKt.getDefaultDumper(), DumperVerifierKt.getValidationAction()}) : null);
    private static final NamedCompilerPhase<JvmBackendContext, IrFile> replaceDefaultImplsOverriddenSymbolsPhase = PhaseBuildersKt.makeIrFilePhase(InheritedDefaultMethodsOnClassesLoweringKt$replaceDefaultImplsOverriddenSymbolsPhase$1.INSTANCE, "ReplaceDefaultImplsOverriddenSymbols", "Replace overridden symbols for methods inherited from interfaces to classes", (248 & 8) != 0 ? SetsKt.emptySet() : null, (248 & 16) != 0 ? SetsKt.emptySet() : null, (248 & 32) != 0 ? SetsKt.emptySet() : null, (248 & 64) != 0 ? SetsKt.emptySet() : null, (248 & 128) != 0 ? SetsKt.setOf((Object[]) new Function3[]{DumperVerifierKt.getDefaultDumper(), DumperVerifierKt.getValidationAction()}) : null);
    private static final NamedCompilerPhase<JvmBackendContext, IrFile> interfaceSuperCallsPhase = PhaseBuildersKt.makeIrFilePhase(InheritedDefaultMethodsOnClassesLoweringKt$interfaceSuperCallsPhase$1.INSTANCE, "InterfaceSuperCalls", "Redirect super interface calls to DefaultImpls", (248 & 8) != 0 ? SetsKt.emptySet() : null, (248 & 16) != 0 ? SetsKt.emptySet() : null, (248 & 32) != 0 ? SetsKt.emptySet() : null, (248 & 64) != 0 ? SetsKt.emptySet() : null, (248 & 128) != 0 ? SetsKt.setOf((Object[]) new Function3[]{DumperVerifierKt.getDefaultDumper(), DumperVerifierKt.getValidationAction()}) : null);
    private static final NamedCompilerPhase<JvmBackendContext, IrFile> interfaceDefaultCallsPhase = PhaseBuildersKt.makeIrFilePhase(InheritedDefaultMethodsOnClassesLoweringKt$interfaceDefaultCallsPhase$1.INSTANCE, "InterfaceDefaultCalls", "Redirect interface calls with default arguments to DefaultImpls (except method compiled to JVM defaults)", (248 & 8) != 0 ? SetsKt.emptySet() : null, (248 & 16) != 0 ? SetsKt.emptySet() : null, (248 & 32) != 0 ? SetsKt.emptySet() : null, (248 & 64) != 0 ? SetsKt.emptySet() : null, (248 & 128) != 0 ? SetsKt.setOf((Object[]) new Function3[]{DumperVerifierKt.getDefaultDumper(), DumperVerifierKt.getValidationAction()}) : null);
    private static final NamedCompilerPhase<JvmBackendContext, IrFile> interfaceObjectCallsPhase = PhaseBuildersKt.makeIrFilePhase(InheritedDefaultMethodsOnClassesLoweringKt$interfaceObjectCallsPhase$1.INSTANCE, "InterfaceObjectCalls", "Resolve calls to Object methods on interface types to virtual methods", (248 & 8) != 0 ? SetsKt.emptySet() : null, (248 & 16) != 0 ? SetsKt.emptySet() : null, (248 & 32) != 0 ? SetsKt.emptySet() : null, (248 & 64) != 0 ? SetsKt.emptySet() : null, (248 & 128) != 0 ? SetsKt.setOf((Object[]) new Function3[]{DumperVerifierKt.getDefaultDumper(), DumperVerifierKt.getValidationAction()}) : null);

    public static final NamedCompilerPhase<JvmBackendContext, IrFile> getInheritedDefaultMethodsOnClassesPhase() {
        return inheritedDefaultMethodsOnClassesPhase;
    }

    public static final NamedCompilerPhase<JvmBackendContext, IrFile> getReplaceDefaultImplsOverriddenSymbolsPhase() {
        return replaceDefaultImplsOverriddenSymbolsPhase;
    }

    public static final NamedCompilerPhase<JvmBackendContext, IrFile> getInterfaceSuperCallsPhase() {
        return interfaceSuperCallsPhase;
    }

    public static final IrExpression reinterpretAsDispatchReceiverOfType(IrExpression irExpression, IrType irType) {
        Intrinsics.checkNotNullParameter(irExpression, "<this>");
        Intrinsics.checkNotNullParameter(irType, "irType");
        return JvmIrTypeUtilsKt.isInlineClassType(irExpression.getType()) ? irExpression : new IrTypeOperatorCallImpl(irExpression.getStartOffset(), irExpression.getEndOffset(), irType, IrTypeOperator.REINTERPRET_CAST, irType, irExpression);
    }

    public static final NamedCompilerPhase<JvmBackendContext, IrFile> getInterfaceDefaultCallsPhase() {
        return interfaceDefaultCallsPhase;
    }

    public static /* synthetic */ boolean isDefinitelyNotDefaultImplsMethod$default(IrSimpleFunction irSimpleFunction, JvmDefaultMode jvmDefaultMode, IrSimpleFunction irSimpleFunction2, int i, Object obj) {
        if ((i & 2) != 0) {
            irSimpleFunction2 = (IrSimpleFunction) IrFakeOverrideUtilsKt.resolveFakeOverride$default(irSimpleFunction, false, null, 3, null);
        }
        return isDefinitelyNotDefaultImplsMethod(irSimpleFunction, jvmDefaultMode, irSimpleFunction2);
    }

    public static final boolean isDefinitelyNotDefaultImplsMethod(IrSimpleFunction irSimpleFunction, JvmDefaultMode jvmDefaultMode, IrSimpleFunction irSimpleFunction2) {
        Intrinsics.checkNotNullParameter(irSimpleFunction, "<this>");
        Intrinsics.checkNotNullParameter(jvmDefaultMode, "jvmDefaultMode");
        return irSimpleFunction2 == null || Intrinsics.areEqual(irSimpleFunction2.getOrigin(), IrDeclarationOrigin.IR_EXTERNAL_JAVA_DECLARATION_STUB.INSTANCE) || JvmIrUtilsKt.isCompiledToJvmDefault(irSimpleFunction2, jvmDefaultMode) || Intrinsics.areEqual(irSimpleFunction.getOrigin(), IrDeclarationOrigin.FUNCTION_FOR_DEFAULT_PARAMETER.INSTANCE) || IrUtilsKt.hasAnnotation(irSimpleFunction, PlatformDependentDeclarationFilterKt.getPLATFORM_DEPENDENT_ANNOTATION_FQ_NAME()) || isCloneableClone(irSimpleFunction);
    }

    private static final boolean isCloneableClone(IrSimpleFunction irSimpleFunction) {
        FqName fqNameWhenAvailable;
        if (Intrinsics.areEqual(irSimpleFunction.getName().asString(), "clone")) {
            IrDeclarationParent parent = irSimpleFunction.getParent();
            String strAsString = null;
            IrClass irClass = parent instanceof IrClass ? (IrClass) parent : null;
            if (irClass != null && (fqNameWhenAvailable = IrUtilsKt.getFqNameWhenAvailable(irClass)) != null) {
                strAsString = fqNameWhenAvailable.asString();
            }
            if (Intrinsics.areEqual(strAsString, "kotlin.Cloneable") && irSimpleFunction.getValueParameters().isEmpty()) {
                return true;
            }
        }
        return false;
    }

    public static final NamedCompilerPhase<JvmBackendContext, IrFile> getInterfaceObjectCallsPhase() {
        return interfaceObjectCallsPhase;
    }

    public static final IrSimpleFunction findInterfaceImplementation(IrSimpleFunction irSimpleFunction, JvmDefaultMode jvmDefaultMode) {
        IrSimpleFunction irSimpleFunction2;
        Intrinsics.checkNotNullParameter(irSimpleFunction, "<this>");
        Intrinsics.checkNotNullParameter(jvmDefaultMode, "jvmDefaultMode");
        if (!irSimpleFunction.getIsFakeOverride()) {
            return null;
        }
        IrDeclarationParent parent = irSimpleFunction.getParent();
        if (((parent instanceof IrClass) && (JvmIrUtilsKt.isJvmInterface((IrClass) parent) || IrUtilsKt.isFromJava((IrDeclaration) parent))) || (irSimpleFunction2 = (IrSimpleFunction) IrFakeOverrideUtilsKt.resolveFakeOverride$default(irSimpleFunction, false, InheritedDefaultMethodsOnClassesLoweringKt$findInterfaceImplementation$implementation$1.INSTANCE, 1, null)) == null) {
            return null;
        }
        List<IrSimpleFunctionSymbol> overriddenSymbols = irSimpleFunction.getOverriddenSymbols();
        if (!(overriddenSymbols instanceof Collection) || !overriddenSymbols.isEmpty()) {
            for (IrSimpleFunctionSymbol irSimpleFunctionSymbol : overriddenSymbols) {
                if (!IrUtilsKt.isInterface(IrUtilsKt.getParentAsClass(irSimpleFunctionSymbol.getOwner())) && irSimpleFunctionSymbol.getOwner().getModality() != Modality.ABSTRACT && Intrinsics.areEqual(IrFakeOverrideUtilsKt.resolveFakeOverride$default(irSimpleFunctionSymbol.getOwner(), false, InheritedDefaultMethodsOnClassesLoweringKt$findInterfaceImplementation$1$1.INSTANCE, 1, null), irSimpleFunction2)) {
                    return null;
                }
            }
        }
        if (!IrUtilsKt.hasInterfaceParent(irSimpleFunction2) || DescriptorVisibilities.isPrivate(irSimpleFunction2.getVisibility()) || isDefinitelyNotDefaultImplsMethod$default(irSimpleFunction2, jvmDefaultMode, null, 2, null) || IrUtilsKt.isMethodOfAny(irSimpleFunction2)) {
            return null;
        }
        return irSimpleFunction2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean isDefaultImplsBridge(IrSimpleFunction irSimpleFunction) {
        return Intrinsics.areEqual(irSimpleFunction.getOrigin(), JvmLoweredDeclarationOrigin.SUPER_INTERFACE_METHOD_BRIDGE.INSTANCE);
    }
}
