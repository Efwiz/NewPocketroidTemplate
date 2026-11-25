package org.jetbrains.kotlin.backend.jvm.lower.indy;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Pair;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.Sequence;
import kotlin.sequences.SequencesKt;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.backend.common.lower.LocalDeclarationsLoweringKt;
import org.jetbrains.kotlin.backend.common.lower.VariableRemapper;
import org.jetbrains.kotlin.backend.jvm.JvmBackendContext;
import org.jetbrains.kotlin.backend.jvm.JvmLoweredDeclarationOrigin;
import org.jetbrains.kotlin.backend.jvm.MfvcNodeKt;
import org.jetbrains.kotlin.backend.jvm.ir.JvmIrTypeUtilsKt;
import org.jetbrains.kotlin.backend.jvm.ir.JvmIrUtilsKt;
import org.jetbrains.kotlin.backend.jvm.lower.InheritedDefaultMethodsOnClassesLoweringKt;
import org.jetbrains.kotlin.config.LanguageFeature;
import org.jetbrains.kotlin.descriptors.DescriptorVisibilities;
import org.jetbrains.kotlin.descriptors.DescriptorVisibility;
import org.jetbrains.kotlin.descriptors.Modality;
import org.jetbrains.kotlin.ir.builders.declarations.DeclarationBuildersKt;
import org.jetbrains.kotlin.ir.builders.declarations.IrClassBuilder;
import org.jetbrains.kotlin.ir.builders.declarations.IrValueParameterBuilder;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrConstructor;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationOrigin;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationParent;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationsKt;
import org.jetbrains.kotlin.ir.declarations.IrFactory;
import org.jetbrains.kotlin.ir.declarations.IrFunction;
import org.jetbrains.kotlin.ir.declarations.IrFunctionWithLateBinding;
import org.jetbrains.kotlin.ir.declarations.IrOverridableMember;
import org.jetbrains.kotlin.ir.declarations.IrProperty;
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction;
import org.jetbrains.kotlin.ir.declarations.IrSymbolOwner;
import org.jetbrains.kotlin.ir.declarations.IrTypeParameter;
import org.jetbrains.kotlin.ir.declarations.IrValueParameter;
import org.jetbrains.kotlin.ir.expressions.IrBody;
import org.jetbrains.kotlin.ir.expressions.IrFunctionReference;
import org.jetbrains.kotlin.ir.expressions.impl.IrFunctionReferenceImpl;
import org.jetbrains.kotlin.ir.overrides.IrOverridingUtilKt;
import org.jetbrains.kotlin.ir.symbols.impl.IrSimpleFunctionSymbolImpl;
import org.jetbrains.kotlin.ir.types.IrSimpleType;
import org.jetbrains.kotlin.ir.types.IrStarProjection;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.ir.types.IrTypeArgument;
import org.jetbrains.kotlin.ir.types.IrTypePredicatesKt;
import org.jetbrains.kotlin.ir.types.IrTypeProjection;
import org.jetbrains.kotlin.ir.types.IrTypesKt;
import org.jetbrains.kotlin.ir.util.AdditionalIrUtilsKt;
import org.jetbrains.kotlin.ir.util.DumpIrTreeKt;
import org.jetbrains.kotlin.ir.util.DumpIrTreeOptions;
import org.jetbrains.kotlin.ir.util.InlineClassesKt;
import org.jetbrains.kotlin.ir.util.IrTypeUtilsKt;
import org.jetbrains.kotlin.ir.util.IrUtilsKt;
import org.jetbrains.kotlin.ir.util.RenderIrElementKt;
import org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoidKt;
import org.jetbrains.kotlin.load.java.JavaDescriptorVisibilities;
import org.jetbrains.kotlin.load.java.JvmAnnotationNames;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.types.Variance;

/* JADX INFO: compiled from: LambdaMetafactoryArguments.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\u008e\u0001\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u000e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\r\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\b\b\u0000\u0018\u00002\u00020\u0001:\u0002NOB\u001b\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005¢\u0006\u0002\u0010\u0007J\u0018\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u00062\u0006\u0010\u000f\u001a\u00020\u0010H\u0002J \u0010\u0011\u001a\u00020\r2\u0006\u0010\u0012\u001a\u00020\u00062\u0006\u0010\u000e\u001a\u00020\u00062\u0006\u0010\u000f\u001a\u00020\u0010H\u0002J(\u0010\u0013\u001a\u00020\t2\u0006\u0010\u0012\u001a\u00020\u00142\u0006\u0010\u000e\u001a\u00020\u00062\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0015\u001a\u00020\u0016H\u0002J\u001a\u0010\u0017\u001a\u00020\t2\u0006\u0010\u0018\u001a\u00020\u00192\b\u0010\u001a\u001a\u0004\u0018\u00010\u001bH\u0002J(\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\u001e0\u001d2\u0006\u0010\u001f\u001a\u00020\u00142\b\b\u0002\u0010 \u001a\u00020\t2\b\b\u0002\u0010!\u001a\u00020\tJ\u001e\u0010\"\u001a\u0004\u0018\u00010\u001b2\b\u0010#\u001a\u0004\u0018\u00010\u001b2\b\u0010$\u001a\u0004\u0018\u00010\u001bH\u0002J\u001a\u0010%\u001a\u0004\u0018\u00010\u001b2\u0006\u0010&\u001a\u00020\u00192\u0006\u0010'\u001a\u00020\u0019H\u0002J\u001a\u0010(\u001a\u0004\u0018\u00010\u001b2\u0006\u0010)\u001a\u00020\u00062\u0006\u0010*\u001a\u00020\u0006H\u0002J\u001a\u0010+\u001a\u0004\u0018\u00010\u00102\u0006\u0010)\u001a\u00020\u00062\u0006\u0010*\u001a\u00020\u0006H\u0002J\u0010\u0010,\u001a\u00020-2\u0006\u0010\u0018\u001a\u00020.H\u0002J2\u0010/\u001a\u0004\u0018\u0001002\u0006\u0010\u0015\u001a\u00020\u00162\u0006\u00101\u001a\u00020\u00062\u0006\u00102\u001a\u00020\u00192\u0006\u0010\u0012\u001a\u00020\u00142\u0006\u00103\u001a\u00020\tH\u0002J\u001e\u00104\u001a\u0002052\u0006\u0010\u0015\u001a\u00020\u00162\u0006\u00102\u001a\u00020\u00192\u0006\u00106\u001a\u00020\tJ\u0010\u00107\u001a\u00020\t2\u0006\u0010\u0012\u001a\u00020\u0014H\u0002J\u001a\u00108\u001a\u0004\u0018\u00010\u00102\u0006\u00109\u001a\u00020\u00102\u0006\u0010:\u001a\u00020\u0010H\u0002J\u0018\u0010;\u001a\u00020\u00162\u0006\u0010<\u001a\u00020\u00062\u0006\u0010\u0015\u001a\u00020\u0016H\u0002J4\u0010=\u001a\u00020\r2\f\u0010>\u001a\b\u0012\u0004\u0012\u00020\u001e0\u001d2\f\u0010?\u001a\b\u0012\u0004\u0012\u00020\u001e0\u001d2\u0006\u0010\u0012\u001a\u00020\u00142\u0006\u0010\u000e\u001a\u00020\u0006H\u0002J&\u0010@\u001a\u00020\u001e*\u00020\u001e2\u0006\u0010A\u001a\u00020\u00062\u0006\u0010B\u001a\u00020C2\b\b\u0002\u0010D\u001a\u00020EH\u0002J\f\u0010F\u001a\u00020\t*\u00020\u0014H\u0002J\f\u0010G\u001a\u00020\t*\u00020HH\u0002J\f\u0010I\u001a\u00020\t*\u00020-H\u0002J\f\u0010J\u001a\u00020\t*\u00020HH\u0002J\f\u0010K\u001a\u00020\t*\u00020-H\u0002J\u000e\u0010L\u001a\u00020\t*\u0004\u0018\u00010\u001bH\u0002J\u000e\u0010M\u001a\u00020\t*\u0004\u0018\u00010\u001bH\u0002R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006P"}, d2 = {"Lorg/jetbrains/kotlin/backend/jvm/lower/indy/LambdaMetafactoryArgumentsBuilder;", "", "context", "Lorg/jetbrains/kotlin/backend/jvm/JvmBackendContext;", "crossinlineLambdas", "", "Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;", "(Lorg/jetbrains/kotlin/backend/jvm/JvmBackendContext;Ljava/util/Set;)V", "isJavaSamConversionWithEqualsHashCode", "", "javaIoSerializableFqn", "Lorg/jetbrains/kotlin/name/FqName;", "adaptFakeInstanceMethodSignature", "", "fakeInstanceMethod", "constraints", "Lorg/jetbrains/kotlin/backend/jvm/lower/indy/LambdaMetafactoryArgumentsBuilder$SignatureAdaptationConstraints;", "adaptLambdaSignature", "implFun", "checkMethodSignatureCompliance", "Lorg/jetbrains/kotlin/ir/declarations/IrFunction;", "reference", "Lorg/jetbrains/kotlin/ir/expressions/IrFunctionReference;", "checkTypeCompliesWithConstraint", "irType", "Lorg/jetbrains/kotlin/ir/types/IrType;", "constraint", "Lorg/jetbrains/kotlin/backend/jvm/lower/indy/LambdaMetafactoryArgumentsBuilder$TypeAdaptationConstraint;", "collectValueParameters", "", "Lorg/jetbrains/kotlin/ir/declarations/IrValueParameter;", "irFun", "withDispatchReceiver", "withExtensionReceiver", "composeTypeAdaptationConstraints", "t1", "t2", "computeParameterTypeAdaptationConstraint", "adapteeType", "expectedType", "computeReturnTypeAdaptationConstraint", "adapteeFun", "expectedFun", "computeSignatureAdaptationConstraints", "getErasedClassForSignatureAdaptation", "Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "Lorg/jetbrains/kotlin/ir/types/IrSimpleType;", "getLambdaMetafactoryArgsOrNullInner", "Lorg/jetbrains/kotlin/backend/jvm/lower/indy/LambdaMetafactoryArguments;", "samMethod", "samType", "shouldBeSerializable", "getLambdaMetafactoryArguments", "Lorg/jetbrains/kotlin/backend/jvm/lower/indy/MetafactoryArgumentsResult;", "plainLambda", "isConstructorRequiringAccessor", "joinSignatureAdaptationConstraints", "sig1", "sig2", "remapExtensionLambda", "lambda", "validateMethodParameters", "implParameters", "methodParameters", ConfigConstants.CONFIG_RENAMELIMIT_COPY, "parent", "newIndex", "", "newName", "Lorg/jetbrains/kotlin/name/Name;", "isAdaptable", "isCrossinlineLambda", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationParent;", "isInheritedFromSerializable", "isInlineFunction", "requiresDelegationToDefaultImpls", "requiresImplLambdaBoxing", "requiresInstanceMethodBoxing", "SignatureAdaptationConstraints", "TypeAdaptationConstraint", "backend.jvm.lower"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class LambdaMetafactoryArgumentsBuilder {
    private final JvmBackendContext context;
    private final Set<IrSimpleFunction> crossinlineLambdas;
    private final boolean isJavaSamConversionWithEqualsHashCode;
    private final FqName javaIoSerializableFqn;

    /* JADX INFO: compiled from: LambdaMetafactoryArguments.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0006\b\u0082\u0081\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004j\u0002\b\u0005j\u0002\b\u0006¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/backend/jvm/lower/indy/LambdaMetafactoryArgumentsBuilder$TypeAdaptationConstraint;", "", "(Ljava/lang/String;I)V", "FORCE_BOXING", "BOX_PRIMITIVE", "KEEP_UNBOXED", "CONFLICT", "backend.jvm.lower"}, k = 1, mv = {1, 9, 0}, xi = 48)
    private enum TypeAdaptationConstraint {
        FORCE_BOXING,
        BOX_PRIMITIVE,
        KEEP_UNBOXED,
        CONFLICT;

        private static final /* synthetic */ EnumEntries $ENTRIES = EnumEntriesKt.enumEntries(values());

        public static EnumEntries<TypeAdaptationConstraint> getEntries() {
            return $ENTRIES;
        }
    }

    /* JADX INFO: compiled from: LambdaMetafactoryArguments.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[TypeAdaptationConstraint.values().length];
            try {
                iArr[TypeAdaptationConstraint.FORCE_BOXING.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[TypeAdaptationConstraint.KEEP_UNBOXED.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[TypeAdaptationConstraint.BOX_PRIMITIVE.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                iArr[TypeAdaptationConstraint.CONFLICT.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public LambdaMetafactoryArgumentsBuilder(JvmBackendContext context, Set<? extends IrSimpleFunction> crossinlineLambdas) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(crossinlineLambdas, "crossinlineLambdas");
        this.context = context;
        this.crossinlineLambdas = crossinlineLambdas;
        this.isJavaSamConversionWithEqualsHashCode = context.getState().getLanguageVersionSettings().supportsFeature(LanguageFeature.JavaSamConversionEqualsHashCode);
        FqName fqNameChild = new FqName("java.io").child(Name.identifier("Serializable"));
        Intrinsics.checkNotNullExpressionValue(fqNameChild, "child(...)");
        this.javaIoSerializableFqn = fqNameChild;
    }

    /* JADX WARN: Code duplicated, block: B:99:0x017e  */
    public final MetafactoryArgumentsResult getLambdaMetafactoryArguments(IrFunctionReference reference, IrType samType, boolean plainLambda) {
        boolean z;
        boolean z2;
        boolean z3;
        Intrinsics.checkNotNullParameter(reference, "reference");
        Intrinsics.checkNotNullParameter(samType, "samType");
        IrClass irClass = IrTypesKt.getClass(samType);
        if (irClass == null) {
            throw new AssertionError("SAM type is not a class: " + RenderIrElementKt.render$default(samType, (DumpIrTreeOptions) null, 1, (Object) null));
        }
        boolean z4 = false;
        boolean z5 = !IrUtilsKt.isLambda(reference.getOrigin()) && (!IrUtilsKt.isFromJava(irClass) || this.isJavaSamConversionWithEqualsHashCode);
        boolean zIsInheritedFromSerializable = isInheritedFromSerializable(irClass);
        IrSimpleFunction singleAbstractMethod = JvmIrUtilsKt.getSingleAbstractMethod(irClass);
        if (singleAbstractMethod == null) {
            throw new AssertionError("SAM class has no single abstract method: " + RenderIrElementKt.render$default(irClass, (DumpIrTreeOptions) null, 1, (Object) null));
        }
        boolean isSuspend = singleAbstractMethod.getIsSuspend();
        if (requiresDelegationToDefaultImpls(irClass)) {
            isSuspend = true;
        }
        IrFunction owner = reference.getSymbol().getOwner();
        List<IrTypeParameter> typeParameters = owner.getTypeParameters();
        if (!(typeParameters instanceof Collection) || !typeParameters.isEmpty()) {
            Iterator<T> it = typeParameters.iterator();
            while (true) {
                if (!it.hasNext()) {
                    z = false;
                    break;
                }
                if (((IrTypeParameter) it.next()).getIsReified()) {
                    z = true;
                    break;
                }
            }
        } else {
            z = false;
            break;
        }
        if (this.context.getGetIntrinsic().invoke(owner.getSymbol()) != null) {
            z = true;
        }
        if (owner.getIsInline()) {
            z = true;
        }
        if (isConstructorRequiringAccessor(owner)) {
            z = true;
        }
        if (owner instanceof IrSimpleFunction) {
            IrDeclarationParent parent = JvmIrUtilsKt.findSuperDeclaration((IrSimpleFunction) owner, false, this.context.getConfig().getJvmDefaultMode()).getParent();
            IrClass irClass2 = parent instanceof IrClass ? (IrClass) parent : null;
            if (irClass2 != null && Intrinsics.areEqual(irClass2.getVisibility(), JavaDescriptorVisibilities.PACKAGE_VISIBILITY)) {
                z = true;
            }
        }
        IrDeclarationParent parent2 = owner.getParent();
        if ((parent2 instanceof IrClass) && Intrinsics.areEqual(((IrClass) parent2).getOrigin(), IrDeclarationOrigin.JVM_MULTIFILE_CLASS.INSTANCE)) {
            z = true;
        }
        if (IrUtilsKt.isLambda(reference.getOrigin()) && !owner.getAnnotations().isEmpty()) {
            isSuspend = true;
        }
        if (plainLambda) {
            int size = owner.getValueParameters().size();
            if (owner.getExtensionReceiverParameter() != null) {
                size++;
            }
            if (size >= 23) {
                isSuspend = true;
            }
        }
        Iterator<IrDeclarationParent> it2 = LocalDeclarationsLoweringKt.getParents(owner).iterator();
        while (true) {
            if (!it2.hasNext()) {
                z2 = false;
                break;
            }
            IrDeclarationParent next = it2.next();
            if (isInlineFunction(next) || isCrossinlineLambda(next)) {
                z2 = true;
                break;
            }
        }
        if (samType instanceof IrSimpleType) {
            List<IrTypeArgument> arguments = ((IrSimpleType) samType).getArguments();
            if (!(arguments instanceof Collection) || !arguments.isEmpty()) {
                for (IrTypeArgument irTypeArgument : arguments) {
                    if ((irTypeArgument instanceof IrStarProjection) || ((irTypeArgument instanceof IrTypeProjection) && ((IrTypeProjection) irTypeArgument).getVariance() != Variance.INVARIANT)) {
                        z4 = true;
                        break;
                    }
                }
            }
            z3 = z4 ? true : isSuspend;
        }
        if (z5) {
            return MetafactoryArgumentsResult.Failure.LambdaMetafactorySemanticsHazard.INSTANCE;
        }
        if (z3) {
            return MetafactoryArgumentsResult.Failure.LambdaMetafactoryAbiHazard.INSTANCE;
        }
        if (z2) {
            return MetafactoryArgumentsResult.Failure.InliningHazard.INSTANCE;
        }
        if (z) {
            return MetafactoryArgumentsResult.Failure.FunctionHazard.INSTANCE;
        }
        LambdaMetafactoryArguments lambdaMetafactoryArgsOrNullInner = getLambdaMetafactoryArgsOrNullInner(reference, singleAbstractMethod, samType, owner, zIsInheritedFromSerializable);
        return lambdaMetafactoryArgsOrNullInner != null ? lambdaMetafactoryArgsOrNullInner : MetafactoryArgumentsResult.Failure.FunctionHazard.INSTANCE;
    }

    private final boolean isConstructorRequiringAccessor(IrFunction implFun) {
        if (!(implFun instanceof IrConstructor)) {
            return false;
        }
        DescriptorVisibility visibility = implFun.getVisibility();
        return Intrinsics.areEqual(visibility, DescriptorVisibilities.PROTECTED) || DescriptorVisibilities.isPrivate(visibility);
    }

    private final boolean isInheritedFromSerializable(IrClass irClass) {
        Set<IrClass> allSuperclasses = IrTypeUtilsKt.getAllSuperclasses(irClass);
        if ((allSuperclasses instanceof Collection) && allSuperclasses.isEmpty()) {
            return false;
        }
        Iterator<T> it = allSuperclasses.iterator();
        while (it.hasNext()) {
            if (Intrinsics.areEqual(IrUtilsKt.getFqNameWhenAvailable((IrClass) it.next()), this.javaIoSerializableFqn)) {
                return true;
            }
        }
        return false;
    }

    private final boolean requiresDelegationToDefaultImpls(IrClass irClass) {
        Sequence<IrSimpleFunction> functions = IrUtilsKt.getFunctions(irClass);
        IrClass irClass2 = irClass;
        for (IrSimpleFunction irSimpleFunctionFindInterfaceImplementation : SequencesKt.plus(SequencesKt.plus((Sequence) functions, SequencesKt.mapNotNull(IrUtilsKt.getProperties(irClass2), new Function1<IrProperty, IrSimpleFunction>() { // from class: org.jetbrains.kotlin.backend.jvm.lower.indy.LambdaMetafactoryArgumentsBuilder$requiresDelegationToDefaultImpls$functionsAndAccessors$1
            @Override // kotlin.jvm.functions.Function1
            public final IrSimpleFunction invoke(IrProperty it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return it.getGetter();
            }
        })), SequencesKt.mapNotNull(IrUtilsKt.getProperties(irClass2), new Function1<IrProperty, IrSimpleFunction>() { // from class: org.jetbrains.kotlin.backend.jvm.lower.indy.LambdaMetafactoryArgumentsBuilder$requiresDelegationToDefaultImpls$functionsAndAccessors$2
            @Override // kotlin.jvm.functions.Function1
            public final IrSimpleFunction invoke(IrProperty it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return it.getSetter();
            }
        }))) {
            if (irSimpleFunctionFindInterfaceImplementation.getModality() != Modality.ABSTRACT && (!irSimpleFunctionFindInterfaceImplementation.getIsFakeOverride() || (irSimpleFunctionFindInterfaceImplementation = InheritedDefaultMethodsOnClassesLoweringKt.findInterfaceImplementation(irSimpleFunctionFindInterfaceImplementation, this.context.getConfig().getJvmDefaultMode())) != null)) {
                if (!Intrinsics.areEqual(irSimpleFunctionFindInterfaceImplementation.getOrigin(), IrDeclarationOrigin.IR_EXTERNAL_JAVA_DECLARATION_STUB.INSTANCE) && !JvmIrUtilsKt.isCompiledToJvmDefault(irSimpleFunctionFindInterfaceImplementation, this.context.getConfig().getJvmDefaultMode())) {
                    return true;
                }
            }
        }
        return false;
    }

    private final LambdaMetafactoryArguments getLambdaMetafactoryArgsOrNullInner(IrFunctionReference reference, IrSimpleFunction samMethod, IrType samType, IrFunction implFun, boolean shouldBeSerializable) {
        IrFunctionReference irFunctionReferenceRemapExtensionLambda = reference;
        List listAllOverridden$default = IrUtilsKt.allOverridden$default(samMethod, false, 1, null);
        ArrayList arrayList = new ArrayList();
        for (Object obj : listAllOverridden$default) {
            if (!((IrSimpleFunction) obj).getIsFakeOverride()) {
                arrayList.add(obj);
            }
        }
        ArrayList arrayList2 = arrayList;
        List listPlus = samMethod.getIsFakeOverride() ? arrayList2 : CollectionsKt.plus((Collection<? extends IrSimpleFunction>) arrayList2, samMethod);
        IrFactory irFactory = this.context.getIrFactory();
        IrClassBuilder irClassBuilder = new IrClassBuilder();
        Name nameSpecial = Name.special("<fake>");
        Intrinsics.checkNotNullExpressionValue(nameSpecial, "special(...)");
        irClassBuilder.setName(nameSpecial);
        IrClass irClassBuildClass = DeclarationBuildersKt.buildClass(irFactory, irClassBuilder);
        irClassBuildClass.setParent(this.context.getIr().getSymbols().getKotlinJvmInternalInvokeDynamicPackage());
        IrOverridableMember irOverridableMemberBuildFakeOverrideMember$default = IrOverridingUtilKt.buildFakeOverrideMember$default(samType, samMethod, irClassBuildClass, null, null, 24, null);
        Intrinsics.checkNotNull(irOverridableMemberBuildFakeOverrideMember$default, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.declarations.IrSimpleFunction");
        IrSimpleFunction irSimpleFunction = (IrSimpleFunction) irOverridableMemberBuildFakeOverrideMember$default;
        ((IrFunctionWithLateBinding) irSimpleFunction).acquireSymbol(new IrSimpleFunctionSymbolImpl(null, 1, null));
        irSimpleFunction.setOverriddenSymbols(CollectionsKt.listOf(samMethod.getSymbol()));
        SignatureAdaptationConstraints signatureAdaptationConstraints = new SignatureAdaptationConstraints(MapsKt.emptyMap(), null);
        Iterator it = listPlus.iterator();
        while (it.hasNext()) {
            SignatureAdaptationConstraints signatureAdaptationConstraintsComputeSignatureAdaptationConstraints = computeSignatureAdaptationConstraints(irSimpleFunction, (IrSimpleFunction) it.next());
            if (signatureAdaptationConstraintsComputeSignatureAdaptationConstraints == null || (signatureAdaptationConstraints = joinSignatureAdaptationConstraints(signatureAdaptationConstraints, signatureAdaptationConstraintsComputeSignatureAdaptationConstraints)) == null) {
                return null;
            }
        }
        if (signatureAdaptationConstraints.hasConflicts()) {
            return null;
        }
        adaptFakeInstanceMethodSignature(irSimpleFunction, signatureAdaptationConstraints);
        if (isAdaptable(implFun)) {
            Intrinsics.checkNotNull(implFun, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.declarations.IrSimpleFunction");
            adaptLambdaSignature((IrSimpleFunction) implFun, irSimpleFunction, signatureAdaptationConstraints);
        } else if (!checkMethodSignatureCompliance(implFun, irSimpleFunction, signatureAdaptationConstraints, irFunctionReferenceRemapExtensionLambda)) {
            return null;
        }
        if (Intrinsics.areEqual(implFun.getOrigin(), IrDeclarationOrigin.LOCAL_FUNCTION_FOR_LAMBDA.INSTANCE) || AdditionalIrUtilsKt.isAnonymousFunction(implFun)) {
            Intrinsics.checkNotNull(implFun, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.declarations.IrSimpleFunction");
            irFunctionReferenceRemapExtensionLambda = remapExtensionLambda((IrSimpleFunction) implFun, irFunctionReferenceRemapExtensionLambda);
        }
        IrFunctionReference irFunctionReference = irFunctionReferenceRemapExtensionLambda;
        if (samMethod.getIsFakeOverride() && arrayList2.size() == 1) {
            return new LambdaMetafactoryArguments((IrSimpleFunction) CollectionsKt.single((List) arrayList2), irSimpleFunction, irFunctionReference, CollectionsKt.emptyList(), shouldBeSerializable);
        }
        return new LambdaMetafactoryArguments(samMethod, irSimpleFunction, irFunctionReference, arrayList2, shouldBeSerializable);
    }

    private final boolean checkMethodSignatureCompliance(IrFunction implFun, IrSimpleFunction fakeInstanceMethod, SignatureAdaptationConstraints constraints, IrFunctionReference reference) {
        List<IrValueParameter> listCollectValueParameters = collectValueParameters(implFun, reference.getDispatchReceiver() == null, reference.getExtensionReceiver() == null);
        List<? extends IrValueParameter> listCollectValueParameters$default = collectValueParameters$default(this, fakeInstanceMethod, false, false, 6, null);
        validateMethodParameters(listCollectValueParameters, listCollectValueParameters$default, implFun, fakeInstanceMethod);
        for (Pair pair : CollectionsKt.zip(listCollectValueParameters, listCollectValueParameters$default)) {
            if (!checkTypeCompliesWithConstraint(((IrValueParameter) pair.component1()).getType(), constraints.getValueParameters().get((IrValueParameter) pair.component2()))) {
                return false;
            }
        }
        if (checkTypeCompliesWithConstraint(implFun.getReturnType(), constraints.getReturnType())) {
            return !IrTypePredicatesKt.isUnit(implFun.getReturnType()) || IrTypePredicatesKt.isUnit(fakeInstanceMethod.getReturnType());
        }
        return false;
    }

    private final boolean checkTypeCompliesWithConstraint(IrType irType, TypeAdaptationConstraint constraint) {
        int i = constraint == null ? -1 : WhenMappings.$EnumSwitchMapping$0[constraint.ordinal()];
        if (i == -1) {
            return true;
        }
        if (i == 1) {
            return org.jetbrains.kotlin.ir.types.IrTypeUtilsKt.isNullable(irType);
        }
        if (i != 2) {
            if (i != 3) {
                if (i != 4) {
                    throw new NoWhenBranchMatchedException();
                }
            } else if (IrTypePredicatesKt.getPrimitiveType(irType) != null) {
                return true;
            }
        } else if (!org.jetbrains.kotlin.ir.types.IrTypeUtilsKt.isNullable(irType)) {
            return true;
        }
        return false;
    }

    private final boolean isAdaptable(IrFunction irFunction) {
        IrDeclarationOrigin origin = irFunction.getOrigin();
        if (Intrinsics.areEqual(origin, IrDeclarationOrigin.LOCAL_FUNCTION_FOR_LAMBDA.INSTANCE) ? true : Intrinsics.areEqual(origin, JvmLoweredDeclarationOrigin.PROXY_FUN_FOR_METAFACTORY.INSTANCE) ? true : Intrinsics.areEqual(origin, JvmLoweredDeclarationOrigin.SYNTHETIC_PROXY_FUN_FOR_METAFACTORY.INSTANCE)) {
            return true;
        }
        if (Intrinsics.areEqual(origin, IrDeclarationOrigin.LOCAL_FUNCTION.INSTANCE)) {
            return AdditionalIrUtilsKt.isAnonymousFunction(irFunction);
        }
        return false;
    }

    private final void adaptLambdaSignature(IrSimpleFunction implFun, IrSimpleFunction fakeInstanceMethod, SignatureAdaptationConstraints constraints) {
        IrSimpleFunction irSimpleFunction = implFun;
        if (!isAdaptable(irSimpleFunction)) {
            throw new AssertionError("Function origin should be adaptable: " + DumpIrTreeKt.dump$default(implFun, null, 1, null));
        }
        List<? extends IrValueParameter> listCollectValueParameters$default = collectValueParameters$default(this, irSimpleFunction, false, false, 6, null);
        List<? extends IrValueParameter> listCollectValueParameters$default2 = collectValueParameters$default(this, fakeInstanceMethod, false, false, 6, null);
        validateMethodParameters(listCollectValueParameters$default, listCollectValueParameters$default2, irSimpleFunction, fakeInstanceMethod);
        for (Pair pair : CollectionsKt.zip(listCollectValueParameters$default, listCollectValueParameters$default2)) {
            IrValueParameter irValueParameter = (IrValueParameter) pair.component1();
            if (requiresImplLambdaBoxing(constraints.getValueParameters().get((IrValueParameter) pair.component2()))) {
                irValueParameter.setType(IrTypesKt.makeNullable(irValueParameter.getType()));
            }
        }
        if (requiresImplLambdaBoxing(constraints.getReturnType()) || (IrTypePredicatesKt.isUnit(implFun.getReturnType()) && !IrTypePredicatesKt.isUnit(fakeInstanceMethod.getReturnType()))) {
            implFun.setReturnType(IrTypesKt.makeNullable(implFun.getReturnType()));
        }
    }

    private final void validateMethodParameters(List<? extends IrValueParameter> implParameters, List<? extends IrValueParameter> methodParameters, IrFunction implFun, IrSimpleFunction fakeInstanceMethod) {
        if (implParameters.size() != methodParameters.size()) {
            throw new AssertionError("Mismatching lambda and instance method parameters:\nimplFun: " + RenderIrElementKt.render$default(implFun, (DumpIrTreeOptions) null, 1, (Object) null) + "\n  (" + implParameters.size() + " parameters)\ninstance method: " + RenderIrElementKt.render$default(fakeInstanceMethod, (DumpIrTreeOptions) null, 1, (Object) null) + "\n  (" + methodParameters.size() + " parameters)");
        }
    }

    private final IrFunctionReference remapExtensionLambda(IrSimpleFunction lambda, IrFunctionReference reference) {
        IrValueParameter extensionReceiverParameter = lambda.getExtensionReceiverParameter();
        if (extensionReceiverParameter == null) {
            return reference;
        }
        ArrayList arrayList = new ArrayList();
        HashMap map = new HashMap();
        int i = 0;
        for (IrValueParameter irValueParameter : CollectionsKt.take(lambda.getValueParameters(), lambda.getContextReceiverParametersCount())) {
            IrValueParameter irValueParameterCopy$default = copy$default(this, irValueParameter, lambda, i, null, 4, null);
            map.put(irValueParameter, irValueParameterCopy$default);
            arrayList.add(irValueParameterCopy$default);
            i++;
        }
        ArrayList arrayList2 = arrayList;
        IrValueParameter irValueParameterCopy = copy(extensionReceiverParameter, lambda, i, extensionReceiverParameter.getName());
        HashMap map2 = map;
        map2.put(extensionReceiverParameter, irValueParameterCopy);
        arrayList.add(irValueParameterCopy);
        int i2 = i + 1;
        for (IrValueParameter irValueParameter2 : CollectionsKt.drop(lambda.getValueParameters(), lambda.getContextReceiverParametersCount())) {
            IrValueParameter irValueParameterCopy$default2 = copy$default(this, irValueParameter2, lambda, i2, null, 4, null);
            map2.put(irValueParameter2, irValueParameterCopy$default2);
            arrayList2.add(irValueParameterCopy$default2);
            i2++;
        }
        IrBody body = lambda.getBody();
        if (body != null) {
            IrElementTransformerVoidKt.transformChildrenVoid(body, new VariableRemapper(map2));
        }
        lambda.setExtensionReceiverParameter(null);
        lambda.setValueParameters(arrayList);
        return new IrFunctionReferenceImpl(reference.getStartOffset(), reference.getEndOffset(), reference.getType(), lambda.getSymbol(), 0, arrayList.size(), null, reference.getOrigin());
    }

    static /* synthetic */ IrValueParameter copy$default(LambdaMetafactoryArgumentsBuilder lambdaMetafactoryArgumentsBuilder, IrValueParameter irValueParameter, IrSimpleFunction irSimpleFunction, int i, Name name2, int i2, Object obj) {
        if ((i2 & 4) != 0) {
            name2 = irValueParameter.getName();
        }
        return lambdaMetafactoryArgumentsBuilder.copy(irValueParameter, irSimpleFunction, i, name2);
    }

    private final void adaptFakeInstanceMethodSignature(IrSimpleFunction fakeInstanceMethod, SignatureAdaptationConstraints constraints) {
        for (Map.Entry<IrValueParameter, TypeAdaptationConstraint> entry : constraints.getValueParameters().entrySet()) {
            IrValueParameter key = entry.getKey();
            TypeAdaptationConstraint value = entry.getValue();
            if (!Intrinsics.areEqual(key.getParent(), fakeInstanceMethod)) {
                throw new AssertionError("Unexpected value parameter: " + RenderIrElementKt.render$default(key, (DumpIrTreeOptions) null, 1, (Object) null) + "; fakeInstanceMethod:\n" + DumpIrTreeKt.dump$default(fakeInstanceMethod, null, 1, null));
            }
            if (requiresInstanceMethodBoxing(value)) {
                key.setType(IrTypesKt.makeNullable(key.getType()));
            }
        }
        if (requiresInstanceMethodBoxing(constraints.getReturnType())) {
            fakeInstanceMethod.setReturnType(IrTypesKt.makeNullable(fakeInstanceMethod.getReturnType()));
        }
    }

    private final boolean requiresInstanceMethodBoxing(TypeAdaptationConstraint typeAdaptationConstraint) {
        return typeAdaptationConstraint == TypeAdaptationConstraint.FORCE_BOXING || typeAdaptationConstraint == TypeAdaptationConstraint.BOX_PRIMITIVE;
    }

    private final boolean requiresImplLambdaBoxing(TypeAdaptationConstraint typeAdaptationConstraint) {
        return typeAdaptationConstraint == TypeAdaptationConstraint.FORCE_BOXING;
    }

    /* JADX INFO: compiled from: LambdaMetafactoryArguments.kt */
    @Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u000b\n\u0000\b\u0002\u0018\u00002\u00020\u0001B#\u0012\u0012\u0010\u0002\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00050\u0003\u0012\b\u0010\u0006\u001a\u0004\u0018\u00010\u0005¢\u0006\u0002\u0010\u0007J\u0006\u0010\f\u001a\u00020\rR\u0013\u0010\u0006\u001a\u0004\u0018\u00010\u0005¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u001d\u0010\u0002\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00050\u0003¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000b¨\u0006\u000e"}, d2 = {"Lorg/jetbrains/kotlin/backend/jvm/lower/indy/LambdaMetafactoryArgumentsBuilder$SignatureAdaptationConstraints;", "", "valueParameters", "", "Lorg/jetbrains/kotlin/ir/declarations/IrValueParameter;", "Lorg/jetbrains/kotlin/backend/jvm/lower/indy/LambdaMetafactoryArgumentsBuilder$TypeAdaptationConstraint;", "returnType", "(Ljava/util/Map;Lorg/jetbrains/kotlin/backend/jvm/lower/indy/LambdaMetafactoryArgumentsBuilder$TypeAdaptationConstraint;)V", "getReturnType", "()Lorg/jetbrains/kotlin/backend/jvm/lower/indy/LambdaMetafactoryArgumentsBuilder$TypeAdaptationConstraint;", "getValueParameters", "()Ljava/util/Map;", "hasConflicts", "", "backend.jvm.lower"}, k = 1, mv = {1, 9, 0}, xi = 48)
    private static final class SignatureAdaptationConstraints {
        private final TypeAdaptationConstraint returnType;
        private final Map<IrValueParameter, TypeAdaptationConstraint> valueParameters;

        /* JADX WARN: Multi-variable type inference failed */
        public SignatureAdaptationConstraints(Map<IrValueParameter, ? extends TypeAdaptationConstraint> valueParameters, TypeAdaptationConstraint typeAdaptationConstraint) {
            Intrinsics.checkNotNullParameter(valueParameters, "valueParameters");
            this.valueParameters = valueParameters;
            this.returnType = typeAdaptationConstraint;
        }

        public final Map<IrValueParameter, TypeAdaptationConstraint> getValueParameters() {
            return this.valueParameters;
        }

        public final TypeAdaptationConstraint getReturnType() {
            return this.returnType;
        }

        public final boolean hasConflicts() {
            return this.returnType == TypeAdaptationConstraint.CONFLICT || this.valueParameters.values().contains(TypeAdaptationConstraint.CONFLICT);
        }
    }

    private final SignatureAdaptationConstraints computeSignatureAdaptationConstraints(IrSimpleFunction adapteeFun, IrSimpleFunction expectedFun) {
        TypeAdaptationConstraint typeAdaptationConstraintComputeReturnTypeAdaptationConstraint = computeReturnTypeAdaptationConstraint(adapteeFun, expectedFun);
        if (typeAdaptationConstraintComputeReturnTypeAdaptationConstraint == TypeAdaptationConstraint.CONFLICT) {
            return null;
        }
        HashMap map = new HashMap();
        List listCollectValueParameters$default = collectValueParameters$default(this, adapteeFun, false, false, 6, null);
        List listCollectValueParameters$default2 = collectValueParameters$default(this, expectedFun, false, false, 6, null);
        if (listCollectValueParameters$default.size() != listCollectValueParameters$default2.size()) {
            throw new AssertionError("Mismatching value parameters:\nadaptee: " + RenderIrElementKt.render$default(adapteeFun, (DumpIrTreeOptions) null, 1, (Object) null) + "\n  " + listCollectValueParameters$default.size() + " value parameters;\nexpected: " + RenderIrElementKt.render$default(expectedFun, (DumpIrTreeOptions) null, 1, (Object) null) + "\n  " + listCollectValueParameters$default2.size() + " value parameters.");
        }
        for (Pair pair : CollectionsKt.zip(listCollectValueParameters$default, listCollectValueParameters$default2)) {
            IrValueParameter irValueParameter = (IrValueParameter) pair.component1();
            TypeAdaptationConstraint typeAdaptationConstraintComputeParameterTypeAdaptationConstraint = computeParameterTypeAdaptationConstraint(irValueParameter.getType(), ((IrValueParameter) pair.component2()).getType());
            if (typeAdaptationConstraintComputeParameterTypeAdaptationConstraint != null) {
                if (typeAdaptationConstraintComputeParameterTypeAdaptationConstraint == TypeAdaptationConstraint.CONFLICT) {
                    return null;
                }
                map.put(irValueParameter, typeAdaptationConstraintComputeParameterTypeAdaptationConstraint);
            }
        }
        return new SignatureAdaptationConstraints(map.isEmpty() ? MapsKt.emptyMap() : map, typeAdaptationConstraintComputeReturnTypeAdaptationConstraint);
    }

    private final TypeAdaptationConstraint computeParameterTypeAdaptationConstraint(IrType adapteeType, IrType expectedType) {
        if (!(adapteeType instanceof IrSimpleType)) {
            throw new AssertionError("Simple type expected: " + RenderIrElementKt.render$default(adapteeType, (DumpIrTreeOptions) null, 1, (Object) null));
        }
        if (!(expectedType instanceof IrSimpleType)) {
            throw new AssertionError("Simple type expected: " + RenderIrElementKt.render$default(expectedType, (DumpIrTreeOptions) null, 1, (Object) null));
        }
        if (IrTypePredicatesKt.isNothing(adapteeType) || IrTypePredicatesKt.isNullableNothing(adapteeType)) {
            return TypeAdaptationConstraint.CONFLICT;
        }
        if (IrTypePredicatesKt.isPrimitiveType$default(adapteeType, false, 1, null)) {
            if (IrTypePredicatesKt.isPrimitiveType$default(expectedType, false, 1, null)) {
                FqName ENHANCED_NULLABILITY_ANNOTATION = JvmAnnotationNames.ENHANCED_NULLABILITY_ANNOTATION;
                Intrinsics.checkNotNullExpressionValue(ENHANCED_NULLABILITY_ANNOTATION, "ENHANCED_NULLABILITY_ANNOTATION");
                if (!IrUtilsKt.hasAnnotation(expectedType, ENHANCED_NULLABILITY_ANNOTATION)) {
                    return TypeAdaptationConstraint.KEEP_UNBOXED;
                }
            }
            return TypeAdaptationConstraint.BOX_PRIMITIVE;
        }
        IrClass erasedClassForSignatureAdaptation = getErasedClassForSignatureAdaptation((IrSimpleType) adapteeType);
        if (IrDeclarationsKt.isSingleFieldValueClass(erasedClassForSignatureAdaptation)) {
            IrSimpleType inlineClassUnderlyingType = InlineClassesKt.getInlineClassUnderlyingType(erasedClassForSignatureAdaptation);
            if (!org.jetbrains.kotlin.ir.types.IrTypeUtilsKt.isNullable(inlineClassUnderlyingType) && !IrTypePredicatesKt.isPrimitiveType$default(inlineClassUnderlyingType, false, 1, null)) {
                return TypeAdaptationConstraint.CONFLICT;
            }
            if (IrDeclarationsKt.isSingleFieldValueClass(getErasedClassForSignatureAdaptation((IrSimpleType) expectedType))) {
                return TypeAdaptationConstraint.CONFLICT;
            }
            return TypeAdaptationConstraint.FORCE_BOXING;
        }
        if (MfvcNodeKt.needsMfvcFlattening(adapteeType)) {
            return TypeAdaptationConstraint.CONFLICT;
        }
        return null;
    }

    private final IrClass getErasedClassForSignatureAdaptation(IrSimpleType irType) {
        IrSymbolOwner owner = irType.getClassifier().getOwner();
        if (owner instanceof IrTypeParameter) {
            return JvmIrTypeUtilsKt.getErasedUpperBound((IrTypeParameter) owner);
        }
        if (owner instanceof IrClass) {
            return (IrClass) owner;
        }
        throw new AssertionError("Unexpected classifier: " + RenderIrElementKt.render$default(owner, (DumpIrTreeOptions) null, 1, (Object) null));
    }

    private final TypeAdaptationConstraint computeReturnTypeAdaptationConstraint(IrSimpleFunction adapteeFun, IrSimpleFunction expectedFun) {
        IrType returnType = adapteeFun.getReturnType();
        if (IrTypePredicatesKt.isUnit(returnType)) {
            if (IrTypePredicatesKt.isUnit(expectedFun.getReturnType())) {
                return TypeAdaptationConstraint.KEEP_UNBOXED;
            }
            return TypeAdaptationConstraint.FORCE_BOXING;
        }
        return computeParameterTypeAdaptationConstraint(returnType, expectedFun.getReturnType());
    }

    private final SignatureAdaptationConstraints joinSignatureAdaptationConstraints(SignatureAdaptationConstraints sig1, SignatureAdaptationConstraints sig2) {
        HashMap valueParameters;
        TypeAdaptationConstraint typeAdaptationConstraintComposeTypeAdaptationConstraints = composeTypeAdaptationConstraints(sig1.getReturnType(), sig2.getReturnType());
        if (typeAdaptationConstraintComposeTypeAdaptationConstraints == TypeAdaptationConstraint.CONFLICT) {
            return null;
        }
        if (sig1.getValueParameters().isEmpty()) {
            valueParameters = sig2.getValueParameters();
        } else if (sig2.getValueParameters().isEmpty()) {
            valueParameters = sig1.getValueParameters();
        } else {
            HashMap map = new HashMap();
            map.putAll(sig1.getValueParameters());
            for (Map.Entry<IrValueParameter, TypeAdaptationConstraint> entry : sig2.getValueParameters().entrySet()) {
                IrValueParameter key = entry.getKey();
                TypeAdaptationConstraint typeAdaptationConstraintComposeTypeAdaptationConstraints2 = composeTypeAdaptationConstraints((TypeAdaptationConstraint) map.get(key), entry.getValue());
                if (typeAdaptationConstraintComposeTypeAdaptationConstraints2 != null) {
                    if (typeAdaptationConstraintComposeTypeAdaptationConstraints2 == TypeAdaptationConstraint.CONFLICT) {
                        return null;
                    }
                    map.put(key, typeAdaptationConstraintComposeTypeAdaptationConstraints2);
                }
            }
            valueParameters = map;
        }
        return new SignatureAdaptationConstraints(valueParameters, typeAdaptationConstraintComposeTypeAdaptationConstraints);
    }

    private final TypeAdaptationConstraint composeTypeAdaptationConstraints(TypeAdaptationConstraint t1, TypeAdaptationConstraint t2) {
        if (t1 == null) {
            return t2;
        }
        return (t2 == null || t1 == t2) ? t1 : TypeAdaptationConstraint.CONFLICT;
    }

    private final boolean isInlineFunction(IrDeclarationParent irDeclarationParent) {
        if (irDeclarationParent instanceof IrSimpleFunction) {
            IrSimpleFunction irSimpleFunction = (IrSimpleFunction) irDeclarationParent;
            if (irSimpleFunction.getIsInline() && !Intrinsics.areEqual(irSimpleFunction.getOrigin(), IrDeclarationOrigin.LOCAL_FUNCTION_FOR_LAMBDA.INSTANCE)) {
                return true;
            }
        }
        return false;
    }

    private final boolean isCrossinlineLambda(IrDeclarationParent irDeclarationParent) {
        return (irDeclarationParent instanceof IrSimpleFunction) && this.crossinlineLambdas.contains(irDeclarationParent);
    }

    public static /* synthetic */ List collectValueParameters$default(LambdaMetafactoryArgumentsBuilder lambdaMetafactoryArgumentsBuilder, IrFunction irFunction, boolean z, boolean z2, int i, Object obj) {
        if ((i & 2) != 0) {
            z = false;
        }
        if ((i & 4) != 0) {
            z2 = true;
        }
        return lambdaMetafactoryArgumentsBuilder.collectValueParameters(irFunction, z, z2);
    }

    public final List<IrValueParameter> collectValueParameters(IrFunction irFun, boolean withDispatchReceiver, boolean withExtensionReceiver) {
        Intrinsics.checkNotNullParameter(irFun, "irFun");
        if ((!withDispatchReceiver || irFun.getDispatchReceiverParameter() == null) && (!withExtensionReceiver || irFun.getExtensionReceiverParameter() == null)) {
            return irFun.getValueParameters();
        }
        ArrayList arrayList = new ArrayList();
        if (withDispatchReceiver) {
            org.jetbrains.kotlin.utils.CollectionsKt.addIfNotNull(arrayList, irFun.getDispatchReceiverParameter());
        }
        if (withExtensionReceiver) {
            org.jetbrains.kotlin.utils.CollectionsKt.addIfNotNull(arrayList, irFun.getExtensionReceiverParameter());
        }
        arrayList.addAll(irFun.getValueParameters());
        return arrayList;
    }

    private final IrValueParameter copy(IrValueParameter irValueParameter, IrSimpleFunction irSimpleFunction, int i, Name name2) {
        IrValueParameterBuilder irValueParameterBuilder = new IrValueParameterBuilder();
        irValueParameterBuilder.updateFrom(irValueParameter);
        irValueParameterBuilder.setIndex(i);
        irValueParameterBuilder.setName(name2);
        IrSimpleFunction irSimpleFunction2 = irSimpleFunction;
        return DeclarationBuildersKt.buildValueParameter(irSimpleFunction2.getFactory(), irValueParameterBuilder, irSimpleFunction2);
    }
}
