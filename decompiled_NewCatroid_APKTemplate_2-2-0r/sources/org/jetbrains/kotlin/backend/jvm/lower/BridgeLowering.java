package org.jetbrains.kotlin.backend.jvm.lower;

import androidx.constraintlayout.core.motion.utils.TypedValues;
import com.gaurav.avnc.vnc.XKeySym;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref;
import kotlin.sequences.Sequence;
import org.jetbrains.kotlin.backend.common.FileLoweringPass;
import org.jetbrains.kotlin.backend.common.lower.DeclarationIrBuilder;
import org.jetbrains.kotlin.backend.common.lower.LowerUtilsKt;
import org.jetbrains.kotlin.backend.common.lower.SpecialMethodWithDefaultInfo;
import org.jetbrains.kotlin.backend.jvm.JvmBackendContext;
import org.jetbrains.kotlin.backend.jvm.JvmLoweredDeclarationOrigin;
import org.jetbrains.kotlin.backend.jvm.MemoizedMultiFieldValueClassReplacements;
import org.jetbrains.kotlin.backend.jvm.SpecialBridge;
import org.jetbrains.kotlin.backend.jvm.ir.JvmIrTypeUtilsKt;
import org.jetbrains.kotlin.backend.jvm.ir.JvmIrUtilsKt;
import org.jetbrains.kotlin.backend.jvm.mapping.IrTypeMapper;
import org.jetbrains.kotlin.backend.jvm.mapping.MethodSignatureMapper;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.codegen.AsmUtil;
import org.jetbrains.kotlin.descriptors.DescriptorVisibilities;
import org.jetbrains.kotlin.descriptors.Modality;
import org.jetbrains.kotlin.ir.IrStatement;
import org.jetbrains.kotlin.ir.builders.ExpressionHelpersKt;
import org.jetbrains.kotlin.ir.builders.IrBlockBodyBuilder;
import org.jetbrains.kotlin.ir.builders.IrBlockBuilder;
import org.jetbrains.kotlin.ir.builders.IrBuilderWithScope;
import org.jetbrains.kotlin.ir.builders.declarations.DeclarationBuildersKt;
import org.jetbrains.kotlin.ir.builders.declarations.IrFunctionBuilder;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationOrigin;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationsKt;
import org.jetbrains.kotlin.ir.declarations.IrFactory;
import org.jetbrains.kotlin.ir.declarations.IrFile;
import org.jetbrains.kotlin.ir.declarations.IrFunction;
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction;
import org.jetbrains.kotlin.ir.declarations.IrTypeParameter;
import org.jetbrains.kotlin.ir.declarations.IrValueParameter;
import org.jetbrains.kotlin.ir.expressions.IrBlockBody;
import org.jetbrains.kotlin.ir.expressions.IrBody;
import org.jetbrains.kotlin.ir.expressions.IrCall;
import org.jetbrains.kotlin.ir.expressions.IrExpression;
import org.jetbrains.kotlin.ir.expressions.IrExpressionBody;
import org.jetbrains.kotlin.ir.expressions.IrExpressionsKt;
import org.jetbrains.kotlin.ir.expressions.IrStatementOrigin;
import org.jetbrains.kotlin.ir.expressions.impl.IrGetValueImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrIfThenElseImpl;
import org.jetbrains.kotlin.ir.symbols.IrClassSymbol;
import org.jetbrains.kotlin.ir.symbols.IrSimpleFunctionSymbol;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.ir.types.IrTypePredicatesKt;
import org.jetbrains.kotlin.ir.types.IrTypesKt;
import org.jetbrains.kotlin.ir.util.DumpIrTreeOptions;
import org.jetbrains.kotlin.ir.util.IrFakeOverrideUtilsKt;
import org.jetbrains.kotlin.ir.util.IrTypeUtilsKt;
import org.jetbrains.kotlin.ir.util.IrUtilsKt;
import org.jetbrains.kotlin.ir.util.RenderIrElementKt;
import org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.utils.SmartList;
import org.jetbrains.org.objectweb.asm.commons.Method;

/* JADX INFO: compiled from: BridgeLowering.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000´\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0000\u0018\u00002\u00020\u00012\u00020\u0002:\u0001OB\r\u0012\u0006\u0010\u0003\u001a\u00020\u0004¢\u0006\u0002\u0010\u0005J\u0018\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u000fH\u0002J\u0018\u0010\u0017\u001a\n\u0012\u0004\u0012\u00020\u0019\u0018\u00010\u00182\u0006\u0010\u001a\u001a\u00020\u000fH\u0002J\u0010\u0010\u001b\u001a\u00020\u00132\u0006\u0010\u001c\u001a\u00020\u001dH\u0016J\u0010\u0010\u001e\u001a\u00020\u001f2\u0006\u0010 \u001a\u00020\u0015H\u0016J\u0014\u0010!\u001a\u00020\u000f*\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u000fH\u0002J$\u0010\"\u001a\u00020\u0013*\u00020#2\u0006\u0010$\u001a\u00020\u000f2\u0006\u0010%\u001a\u00020\u000f2\u0006\u0010&\u001a\u00020'H\u0002J\u001c\u0010(\u001a\u00020\u000f*\u00020\u00152\u0006\u0010%\u001a\u00020)2\u0006\u0010$\u001a\u00020\u000fH\u0002J\u001c\u0010*\u001a\u00020\u000f*\u00020\u00152\u0006\u0010+\u001a\u00020\u000e2\u0006\u0010$\u001a\u00020\u000fH\u0002J.\u0010,\u001a\u00020\u0013*\u00020\u000f2\u0006\u0010\u0014\u001a\u00020\u00152\u0006\u0010-\u001a\u00020\u000f2\u0010\b\u0002\u0010.\u001a\n\u0012\u0004\u0012\u00020/\u0018\u00010\u0018H\u0002J.\u00100\u001a\u000201*\u0002012\u0006\u0010$\u001a\u00020\u000f2\f\u00102\u001a\b\u0012\u0004\u0012\u000204032\n\b\u0002\u00105\u001a\u0004\u0018\u00010/H\u0002J(\u00106\u001a\u000207*\u0002082\u0006\u0010%\u001a\u00020\u000f2\u0006\u0010$\u001a\u00020\u000f2\n\b\u0002\u00109\u001a\u0004\u0018\u00010:H\u0002J\u0012\u0010;\u001a\b\u0012\u0004\u0012\u00020\t0\u0018*\u00020\u000fH\u0002J\u001c\u0010<\u001a\u00020=*\u00020\u000f2\u0006\u0010>\u001a\u00020?2\u0006\u0010@\u001a\u00020\tH\u0002J\f\u0010A\u001a\u00020=*\u00020\u000fH\u0002J\f\u0010B\u001a\u00020=*\u00020\u000fH\u0002J\f\u0010C\u001a\u00020=*\u00020/H\u0002J\u0012\u0010D\u001a\b\u0012\u0004\u0012\u00020\u000e0\u0018*\u00020\u000fH\u0002J$\u0010E\u001a\u00020F*\u0002082\u0006\u0010G\u001a\u0002012\u0006\u0010H\u001a\u00020/2\u0006\u0010I\u001a\u000207H\u0002J$\u0010J\u001a\u00020\u0013*\u00020\u000f2\u0006\u0010K\u001a\u00020\t2\u0006\u0010L\u001a\u00020\t2\u0006\u0010M\u001a\u00020NH\u0002R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u0018\u0010\b\u001a\u00020\t*\u00020\n8BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u000b\u0010\fR\u001a\u0010\r\u001a\u0004\u0018\u00010\u000e*\u00020\u000f8BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u0010\u0010\u0011¨\u0006P"}, d2 = {"Lorg/jetbrains/kotlin/backend/jvm/lower/BridgeLowering;", "Lorg/jetbrains/kotlin/backend/common/FileLoweringPass;", "Lorg/jetbrains/kotlin/ir/visitors/IrElementTransformerVoid;", "context", "Lorg/jetbrains/kotlin/backend/jvm/JvmBackendContext;", "(Lorg/jetbrains/kotlin/backend/jvm/JvmBackendContext;)V", "getContext", "()Lorg/jetbrains/kotlin/backend/jvm/JvmBackendContext;", "jvmMethod", "Lorg/jetbrains/org/objectweb/asm/commons/Method;", "Lorg/jetbrains/kotlin/ir/declarations/IrFunction;", "getJvmMethod", "(Lorg/jetbrains/kotlin/ir/declarations/IrFunction;)Lorg/jetbrains/org/objectweb/asm/commons/Method;", "specialBridgeOrNull", "Lorg/jetbrains/kotlin/backend/jvm/SpecialBridge;", "Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;", "getSpecialBridgeOrNull", "(Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;)Lorg/jetbrains/kotlin/backend/jvm/SpecialBridge;", "createBridges", "", "irClass", "Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "irFunction", "getStructure", "", "Lorg/jetbrains/kotlin/backend/jvm/MemoizedMultiFieldValueClassReplacements$RemappedParameter;", "function", "lower", "irFile", "Lorg/jetbrains/kotlin/ir/declarations/IrFile;", "visitClass", "Lorg/jetbrains/kotlin/ir/IrStatement;", "declaration", "addAbstractMethodStub", "addBoxedAndUnboxedMfvcArguments", "Lorg/jetbrains/kotlin/ir/builders/IrBlockBuilder;", "target", "bridge", "irCall", "Lorg/jetbrains/kotlin/ir/expressions/IrCall;", "addBridge", "Lorg/jetbrains/kotlin/backend/jvm/lower/BridgeLowering$Bridge;", "addSpecialBridge", "specialBridge", "copyParametersWithErasure", TypedValues.TransitionType.S_FROM, "substitutedParameterTypes", "Lorg/jetbrains/kotlin/ir/types/IrType;", "copyWithTypeErasure", "Lorg/jetbrains/kotlin/ir/declarations/IrValueParameter;", "visibleTypeParameters", "", "Lorg/jetbrains/kotlin/ir/declarations/IrTypeParameter;", "substitutedType", "delegatingCall", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "Lorg/jetbrains/kotlin/ir/builders/IrBuilderWithScope;", "superQualifierSymbol", "Lorg/jetbrains/kotlin/ir/symbols/IrClassSymbol;", "getSpecialBridgeSignatures", "isClashingWithPotentialBridge", "", "name", "Lorg/jetbrains/kotlin/name/Name;", "signature", "isPotentialBridgeTarget", "isThrowingStub", "isTypeParameterWithPrimitiveUpperBound", "overriddenSpecialBridges", "parameterTypeCheck", "Lorg/jetbrains/kotlin/ir/expressions/impl/IrIfThenElseImpl;", "parameter", ModuleXmlParser.TYPE, "defaultValue", "rewriteSpecialMethodBody", "ourSignature", "specialOverrideSignature", "specialOverrideInfo", "Lorg/jetbrains/kotlin/backend/common/lower/SpecialMethodWithDefaultInfo;", "Bridge", "backend.jvm.lower"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class BridgeLowering extends IrElementTransformerVoid implements FileLoweringPass {
    private final JvmBackendContext context;

    public BridgeLowering(JvmBackendContext context) {
        Intrinsics.checkNotNullParameter(context, "context");
        this.context = context;
    }

    public final JvmBackendContext getContext() {
        return this.context;
    }

    /* JADX INFO: compiled from: BridgeLowering.kt */
    @Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\b\b\u0002\u0018\u00002\u00020\u0001B%\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u000e\b\u0002\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u0007¢\u0006\u0002\u0010\tR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0017\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u0007¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000f¨\u0006\u0010"}, d2 = {"Lorg/jetbrains/kotlin/backend/jvm/lower/BridgeLowering$Bridge;", "", "overridden", "Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;", "signature", "Lorg/jetbrains/org/objectweb/asm/commons/Method;", "overriddenSymbols", "", "Lorg/jetbrains/kotlin/ir/symbols/IrSimpleFunctionSymbol;", "(Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;Lorg/jetbrains/org/objectweb/asm/commons/Method;Ljava/util/List;)V", "getOverridden", "()Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;", "getOverriddenSymbols", "()Ljava/util/List;", "getSignature", "()Lorg/jetbrains/org/objectweb/asm/commons/Method;", "backend.jvm.lower"}, k = 1, mv = {1, 9, 0}, xi = 48)
    private static final class Bridge {
        private final IrSimpleFunction overridden;
        private final List<IrSimpleFunctionSymbol> overriddenSymbols;
        private final Method signature;

        public Bridge(IrSimpleFunction overridden, Method signature, List<IrSimpleFunctionSymbol> overriddenSymbols) {
            Intrinsics.checkNotNullParameter(overridden, "overridden");
            Intrinsics.checkNotNullParameter(signature, "signature");
            Intrinsics.checkNotNullParameter(overriddenSymbols, "overriddenSymbols");
            this.overridden = overridden;
            this.signature = signature;
            this.overriddenSymbols = overriddenSymbols;
        }

        public final IrSimpleFunction getOverridden() {
            return this.overridden;
        }

        public final Method getSignature() {
            return this.signature;
        }

        public /* synthetic */ Bridge(IrSimpleFunction irSimpleFunction, Method method, ArrayList arrayList, int i, DefaultConstructorMarker defaultConstructorMarker) {
            this(irSimpleFunction, method, (i & 4) != 0 ? new ArrayList() : arrayList);
        }

        public final List<IrSimpleFunctionSymbol> getOverriddenSymbols() {
            return this.overriddenSymbols;
        }
    }

    @Override // org.jetbrains.kotlin.backend.common.FileLoweringPass
    public void lower(IrFile irFile) {
        Intrinsics.checkNotNullParameter(irFile, "irFile");
        transformChildrenVoid(irFile);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
    public IrStatement visitClass(IrClass declaration) {
        IrSimpleFunction next;
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        if (Intrinsics.areEqual(declaration.getOrigin(), JvmLoweredDeclarationOrigin.DEFAULT_IMPLS.INSTANCE) || IrUtilsKt.isAnnotationClass(declaration)) {
            return super.visitClass(declaration);
        }
        Sequence<IrSimpleFunction> functions = IrUtilsKt.getFunctions(declaration);
        SmartList smartList = new SmartList();
        for (IrSimpleFunction irSimpleFunction : functions) {
            if (isPotentialBridgeTarget(irSimpleFunction)) {
                smartList.add(irSimpleFunction);
            }
        }
        SmartList<IrSimpleFunction> smartList2 = smartList;
        if (smartList2.isEmpty()) {
            return super.visitClass(declaration);
        }
        for (IrSimpleFunction irSimpleFunction2 : smartList2) {
            Intrinsics.checkNotNull(irSimpleFunction2);
            createBridges(declaration, irSimpleFunction2);
        }
        if (IrDeclarationsKt.isSingleFieldValueClass(declaration)) {
            Iterator<IrSimpleFunction> it = IrUtilsKt.getFunctions(declaration).iterator();
            while (true) {
                if (!it.hasNext()) {
                    next = null;
                    break;
                }
                next = it.next();
                IrFunction irFunction = this.context.getInlineClassReplacements().getOriginalFunctionForStaticReplacement().get(next);
                if (irFunction != null && this.context.getDefaultMethodSignatureMapper().shouldBoxSingleValueParameterForSpecialCaseOfRemove(irFunction)) {
                    break;
                }
            }
            IrSimpleFunction irSimpleFunction3 = next;
            if (irSimpleFunction3 != null) {
                IrValueParameter irValueParameter = (IrValueParameter) CollectionsKt.last((List) irSimpleFunction3.getValueParameters());
                irValueParameter.setType(IrTypesKt.makeNullable(irValueParameter.getType()));
            }
        }
        return super.visitClass(declaration);
    }

    private final boolean isPotentialBridgeTarget(IrSimpleFunction irSimpleFunction) {
        if (DescriptorVisibilities.isPrivate(irSimpleFunction.getVisibility())) {
            return false;
        }
        IrSimpleFunction irSimpleFunction2 = irSimpleFunction;
        if (IrUtilsKt.isStatic(irSimpleFunction2) || irSimpleFunction.getOverriddenSymbols().isEmpty() || IrUtilsKt.isMethodOfAny(irSimpleFunction2)) {
            return false;
        }
        if (!JvmIrUtilsKt.isJvmAbstract(irSimpleFunction, this.context.getConfig().getJvmDefaultMode())) {
            return !irSimpleFunction.getIsFakeOverride() || BridgeLoweringKt.resolvesToClass(irSimpleFunction);
        }
        if (!JvmIrUtilsKt.isJvmInterface(IrUtilsKt.getParentAsClass(irSimpleFunction))) {
            return true;
        }
        if (getSpecialBridgeOrNull(irSimpleFunction) != null) {
            this.context.getFunctionsWithSpecialBridges().add(irSimpleFunction);
        }
        return false;
    }

    /* JADX WARN: Multi-variable type inference failed */
    private final void createBridges(IrClass irClass, IrSimpleFunction irFunction) {
        Ref.ObjectRef objectRef;
        int i;
        LinkedHashSet linkedHashSet = new LinkedHashSet();
        IrSimpleFunction irSimpleFunction = irFunction;
        IrDeclaration irDeclaration = (IrSimpleFunction) IrFakeOverrideUtilsKt.resolveFakeOverride$default(irSimpleFunction, false, null, 3, null);
        IrDeclaration irDeclaration2 = irDeclaration;
        if (irDeclaration == null) {
            irDeclaration2 = irFunction;
        }
        if (Intrinsics.areEqual(irDeclaration2.getOrigin(), IrDeclarationOrigin.FUNCTION_FOR_DEFAULT_PARAMETER.INSTANCE)) {
            return;
        }
        Method jvmMethod = getJvmMethod((IrFunction) irDeclaration2);
        if (!irFunction.getIsFakeOverride() || irFunction.getModality() == Modality.FINAL) {
            linkedHashSet.add(jvmMethod);
        }
        SpecialBridge specialBridgeOrNull = getSpecialBridgeOrNull(irFunction);
        Ref.ObjectRef objectRef2 = new Ref.ObjectRef();
        objectRef2.element = irFunction;
        if (specialBridgeOrNull != null) {
            LinkedHashSet linkedHashSet2 = linkedHashSet;
            List listAllOverridden$default = IrUtilsKt.allOverridden$default(irSimpleFunction, false, 1, null);
            ArrayList arrayList = new ArrayList();
            Iterator it = listAllOverridden$default.iterator();
            while (it.hasNext()) {
                CollectionsKt.addAll(arrayList, getSpecialBridgeSignatures((IrSimpleFunction) it.next()));
            }
            CollectionsKt.addAll(linkedHashSet2, arrayList);
            objectRef = objectRef2;
            i = 1;
            IrSimpleFunction irSimpleFunctionCreateBridges$getSpecialBridgeTargetAddingExtraBridges = createBridges$getSpecialBridgeTargetAddingExtraBridges(linkedHashSet, specialBridgeOrNull, irFunction, this, objectRef2, irClass);
            if (!irFunction.getIsFakeOverride() && specialBridgeOrNull.getMethodInfo() != null) {
                Method signature = specialBridgeOrNull.getSignature();
                SpecialMethodWithDefaultInfo methodInfo = specialBridgeOrNull.getMethodInfo();
                Intrinsics.checkNotNull(methodInfo);
                rewriteSpecialMethodBody(irFunction, jvmMethod, signature, methodInfo);
            }
            if (specialBridgeOrNull.getSubstitutedReturnType() != null) {
                for (SpecialBridge specialBridge : overriddenSpecialBridges(irFunction)) {
                    if (!linkedHashSet.contains(specialBridge.getSignature())) {
                        addSpecialBridge(irClass, specialBridge, irSimpleFunctionCreateBridges$getSpecialBridgeTargetAddingExtraBridges);
                        linkedHashSet2.add(specialBridge.getSignature());
                    }
                }
            }
        } else {
            objectRef = objectRef2;
            i = 1;
            if (JvmIrUtilsKt.isJvmAbstract(irFunction, this.context.getConfig().getJvmDefaultMode())) {
                return;
            }
        }
        if (irFunction.getIsFakeOverride()) {
            Iterator it2 = irFunction.getOverriddenSymbols().iterator();
            while (it2.hasNext()) {
                IrSimpleFunction owner = ((IrSimpleFunctionSymbol) it2.next()).getOwner();
                if (!JvmIrUtilsKt.isJvmAbstract(owner, this.context.getConfig().getJvmDefaultMode())) {
                    List listAllOverridden$default2 = IrUtilsKt.allOverridden$default(owner, false, i, null);
                    ArrayList arrayList2 = new ArrayList();
                    for (Object obj : listAllOverridden$default2) {
                        if (!((IrSimpleFunction) obj).getIsFakeOverride()) {
                            arrayList2.add(obj);
                        }
                    }
                    Iterator it3 = arrayList2.iterator();
                    while (it3.hasNext()) {
                        linkedHashSet.add(getJvmMethod((IrSimpleFunction) it3.next()));
                    }
                }
            }
        }
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        for (IrSimpleFunction irSimpleFunction2 : IrUtilsKt.allOverridden$default(irSimpleFunction, false, i, null)) {
            if (!irSimpleFunction2.getIsFakeOverride()) {
                Method jvmMethod2 = getJvmMethod(irSimpleFunction2);
                if (!Intrinsics.areEqual(jvmMethod, jvmMethod2) && !linkedHashSet.contains(jvmMethod2)) {
                    Object bridge = linkedHashMap.get(jvmMethod2);
                    if (bridge == null) {
                        bridge = new Bridge(irSimpleFunction2, jvmMethod2, null, 4, null);
                        linkedHashMap.put(jvmMethod2, bridge);
                    }
                    ((Bridge) bridge).getOverriddenSymbols().add(irSimpleFunction2.getSymbol());
                }
            }
        }
        if (linkedHashMap.isEmpty()) {
            return;
        }
        Collection collectionValues = linkedHashMap.values();
        ArrayList arrayList3 = new ArrayList();
        for (Object obj2 : collectionValues) {
            if (!linkedHashSet.contains(((Bridge) obj2).getSignature())) {
                arrayList3.add(obj2);
            }
        }
        Iterator it4 = arrayList3.iterator();
        while (it4.hasNext()) {
            addBridge(irClass, (Bridge) it4.next(), (IrSimpleFunction) objectRef.element);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    private static final IrSimpleFunction createBridges$getSpecialBridgeTargetAddingExtraBridges(Set<Method> set, SpecialBridge specialBridge, IrSimpleFunction irSimpleFunction, BridgeLowering bridgeLowering, Ref.ObjectRef<IrSimpleFunction> objectRef, IrClass irClass) {
        IrClass irClass2;
        Ref.ObjectRef<IrSimpleFunction> objectRef2;
        BridgeLowering bridgeLowering2;
        IrSimpleFunction irSimpleFunction2;
        T tAddAbstractMethodStub;
        Set<Method> set2 = set;
        if (set2.contains(specialBridge.getSignature()) || (irSimpleFunction.getIsFakeOverride() && Intrinsics.areEqual(bridgeLowering.getJvmMethod(irSimpleFunction), specialBridge.getSignature()))) {
            return irSimpleFunction;
        }
        if (irSimpleFunction.getIsFakeOverride()) {
            if (JvmIrUtilsKt.isJvmAbstract(irSimpleFunction, bridgeLowering.context.getConfig().getJvmDefaultMode())) {
                irClass.getDeclarations().remove(irSimpleFunction);
                irClass2 = irClass;
                bridgeLowering2 = bridgeLowering;
                tAddAbstractMethodStub = bridgeLowering.addAbstractMethodStub(irClass, irSimpleFunction);
                objectRef2 = objectRef;
            } else {
                if (irSimpleFunction.getModality() != Modality.FINAL) {
                    IrSimpleFunction irSimpleFunctionOverriddenFromClass = BridgeLoweringKt.overriddenFromClass(irSimpleFunction);
                    Intrinsics.checkNotNull(irSimpleFunctionOverriddenFromClass);
                    Method jvmMethod = bridgeLowering.getJvmMethod(irSimpleFunction);
                    IrClassSymbol symbol = IrUtilsKt.getParentAsClass(irSimpleFunctionOverriddenFromClass).getSymbol();
                    SpecialMethodWithDefaultInfo methodInfo = specialBridge.getMethodInfo();
                    irSimpleFunction2 = irSimpleFunction;
                    SpecialBridge specialBridge2 = new SpecialBridge(irSimpleFunction, jvmMethod, false, null, null, methodInfo != null ? SpecialMethodWithDefaultInfo.copy$default(methodInfo, null, 0, false, false, 13, null) : null, symbol, false, false, false, false, null, 3868, null);
                    if (irSimpleFunctionOverriddenFromClass.getIsFakeOverride() && specialBridge.isOverriding()) {
                        irSimpleFunctionOverriddenFromClass = null;
                    }
                    if (irSimpleFunctionOverriddenFromClass == null) {
                        irSimpleFunctionOverriddenFromClass = specialBridge.getOverridden();
                    }
                    IrSimpleFunction irSimpleFunction3 = irSimpleFunctionOverriddenFromClass;
                    bridgeLowering2 = bridgeLowering;
                    if (Intrinsics.areEqual(specialBridge2.getSignature(), bridgeLowering2.getJvmMethod(irSimpleFunction3))) {
                        irClass2 = irClass;
                    } else {
                        irClass.getDeclarations().remove(irSimpleFunction2);
                        irClass2 = irClass;
                        IrSimpleFunction irSimpleFunctionAddSpecialBridge = bridgeLowering2.addSpecialBridge(irClass2, specialBridge2, irSimpleFunction3);
                        objectRef2 = objectRef;
                        tAddAbstractMethodStub = irSimpleFunctionAddSpecialBridge;
                    }
                } else {
                    irClass2 = irClass;
                    bridgeLowering2 = bridgeLowering;
                    irSimpleFunction2 = irSimpleFunction;
                }
                objectRef2 = objectRef;
                tAddAbstractMethodStub = irSimpleFunction2;
            }
            objectRef2.element = tAddAbstractMethodStub;
            set2 = set;
            set2.add(bridgeLowering2.getJvmMethod(objectRef2.element));
        } else {
            irClass2 = irClass;
            objectRef2 = objectRef;
            bridgeLowering2 = bridgeLowering;
        }
        SpecialBridge unsubstitutedSpecialBridge = specialBridge.getUnsubstitutedSpecialBridge();
        if (unsubstitutedSpecialBridge != null && !set2.contains(unsubstitutedSpecialBridge.getSignature())) {
            Iterator<IrSimpleFunction> it = IrUtilsKt.getFunctions(irClass).iterator();
            do {
                if (!it.hasNext()) {
                    set2.add(unsubstitutedSpecialBridge.getSignature());
                    bridgeLowering2.addSpecialBridge(irClass2, unsubstitutedSpecialBridge, objectRef2.element);
                    break;
                }
            } while (!bridgeLowering2.isClashingWithPotentialBridge(it.next(), irSimpleFunction.getName(), unsubstitutedSpecialBridge.getSignature()));
        }
        Iterator<IrSimpleFunction> it2 = IrUtilsKt.getFunctions(irClass).iterator();
        while (it2.hasNext()) {
            if (bridgeLowering2.isClashingWithPotentialBridge(it2.next(), irSimpleFunction.getName(), specialBridge.getSignature())) {
                return irSimpleFunction;
            }
        }
        set2.add(specialBridge.getSignature());
        return bridgeLowering2.addSpecialBridge(irClass2, specialBridge, objectRef2.element);
    }

    private final boolean isClashingWithPotentialBridge(IrSimpleFunction irSimpleFunction, Name name2, Method method) {
        return (!irSimpleFunction.getIsFakeOverride() || irSimpleFunction.getModality() == Modality.FINAL) && Intrinsics.areEqual(irSimpleFunction.getName(), name2) && Intrinsics.areEqual(getJvmMethod(irSimpleFunction), method);
    }

    private final SpecialBridge getSpecialBridgeOrNull(IrSimpleFunction irSimpleFunction) {
        return this.context.getBridgeLoweringCache().computeSpecialBridge(irSimpleFunction);
    }

    private final List<Method> getSpecialBridgeSignatures(IrSimpleFunction irSimpleFunction) {
        IrSimpleFunction irSimpleFunction2 = irSimpleFunction;
        if (IrUtilsKt.isInterface(IrUtilsKt.getParentAsClass(irSimpleFunction2)) || IrUtilsKt.isFromJava(irSimpleFunction2)) {
            return CollectionsKt.emptyList();
        }
        SpecialBridge specialBridgeOrNull = getSpecialBridgeOrNull(irSimpleFunction);
        if (specialBridgeOrNull == null) {
            return CollectionsKt.emptyList();
        }
        SmartList smartList = new SmartList();
        Method jvmMethod = getJvmMethod(irSimpleFunction);
        if (!Intrinsics.areEqual(specialBridgeOrNull.getSignature(), jvmMethod)) {
            smartList.add(specialBridgeOrNull.getSignature());
        }
        SpecialBridge unsubstitutedSpecialBridge = specialBridgeOrNull.getUnsubstitutedSpecialBridge();
        if (unsubstitutedSpecialBridge != null && !Intrinsics.areEqual(unsubstitutedSpecialBridge.getSignature(), jvmMethod)) {
            smartList.add(unsubstitutedSpecialBridge.getSignature());
        }
        return smartList;
    }

    private final List<SpecialBridge> overriddenSpecialBridges(IrSimpleFunction irSimpleFunction) {
        Method methodMapCalleeToAsmMethod$default = MethodSignatureMapper.mapCalleeToAsmMethod$default(this.context.getDefaultMethodSignatureMapper(), irSimpleFunction, false, 2, null);
        List listAllOverridden$default = IrUtilsKt.allOverridden$default(irSimpleFunction, false, 1, null);
        ArrayList arrayList = new ArrayList();
        for (Object obj : listAllOverridden$default) {
            IrSimpleFunction irSimpleFunction2 = (IrSimpleFunction) obj;
            if (IrUtilsKt.isInterface(IrUtilsKt.getParentAsClass(irSimpleFunction2)) || IrUtilsKt.isFromJava(irSimpleFunction2)) {
                arrayList.add(obj);
            }
        }
        ArrayList arrayList2 = new ArrayList();
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            SpecialBridge specialBridgeOrNull = getSpecialBridgeOrNull((IrSimpleFunction) it.next());
            if (specialBridgeOrNull != null) {
                arrayList2.add(specialBridgeOrNull);
            }
        }
        ArrayList arrayList3 = new ArrayList();
        for (Object obj2 : arrayList2) {
            if (!Intrinsics.areEqual(((SpecialBridge) obj2).getSignature(), methodMapCalleeToAsmMethod$default)) {
                arrayList3.add(obj2);
            }
        }
        ArrayList<SpecialBridge> arrayList4 = arrayList3;
        ArrayList arrayList5 = new ArrayList(CollectionsKt.collectionSizeOrDefault(arrayList4, 10));
        for (SpecialBridge specialBridge : arrayList4) {
            arrayList5.add(specialBridge.copy((XKeySym.XK_Greek_epsilon & 1) != 0 ? specialBridge.overridden : null, (XKeySym.XK_Greek_epsilon & 2) != 0 ? specialBridge.signature : null, (XKeySym.XK_Greek_epsilon & 4) != 0 ? specialBridge.needsGenericSignature : false, (XKeySym.XK_Greek_epsilon & 8) != 0 ? specialBridge.substitutedParameterTypes : null, (XKeySym.XK_Greek_epsilon & 16) != 0 ? specialBridge.substitutedReturnType : null, (XKeySym.XK_Greek_epsilon & 32) != 0 ? specialBridge.methodInfo : null, (XKeySym.XK_Greek_epsilon & 64) != 0 ? specialBridge.superQualifierSymbol : null, (XKeySym.XK_Greek_epsilon & 128) != 0 ? specialBridge.isFinal : false, (XKeySym.XK_Greek_epsilon & 256) != 0 ? specialBridge.isSynthetic : true, (XKeySym.XK_Greek_epsilon & 512) != 0 ? specialBridge.isOverriding : false, (XKeySym.XK_Greek_epsilon & 1024) != 0 ? specialBridge.needsUnsubstitutedBridge : false, (XKeySym.XK_Greek_epsilon & 2048) != 0 ? specialBridge.unsubstitutedSpecialBridge : null));
        }
        return arrayList5;
    }

    private final boolean isThrowingStub(IrSimpleFunction irSimpleFunction) {
        if (!Intrinsics.areEqual(irSimpleFunction.getOrigin(), IrDeclarationOrigin.IR_BUILTINS_STUB.INSTANCE) && !Intrinsics.areEqual(irSimpleFunction.getOrigin(), IrDeclarationOrigin.BRIDGE.INSTANCE) && !Intrinsics.areEqual(irSimpleFunction.getOrigin(), IrDeclarationOrigin.BRIDGE_SPECIAL.INSTANCE)) {
            return false;
        }
        IrBody body = irSimpleFunction.getBody();
        IrBlockBody irBlockBody = body instanceof IrBlockBody ? (IrBlockBody) body : null;
        if (irBlockBody == null || irBlockBody.getStatements().size() != 1) {
            return false;
        }
        IrStatement irStatement = irBlockBody.getStatements().get(0);
        IrCall irCall = irStatement instanceof IrCall ? (IrCall) irStatement : null;
        if (irCall == null) {
            return false;
        }
        return Intrinsics.areEqual(irCall.getSymbol(), this.context.getIr().getSymbols().getThrowUnsupportedOperationException());
    }

    private final boolean isTypeParameterWithPrimitiveUpperBound(IrType irType) {
        return IrTypeUtilsKt.isTypeParameter(irType) && IrTypePredicatesKt.isPrimitiveType$default(JvmIrTypeUtilsKt.eraseTypeParameters(irType), false, 1, null);
    }

    private final void rewriteSpecialMethodBody(IrSimpleFunction irSimpleFunction, Method method, Method method2, SpecialMethodWithDefaultInfo specialMethodWithDefaultInfo) {
        if (Intrinsics.areEqual(method2, method)) {
            List<IrValueParameter> listTake = CollectionsKt.take(irSimpleFunction.getValueParameters(), specialMethodWithDefaultInfo.getArgumentsToCheck());
            if ((listTake instanceof Collection) && listTake.isEmpty()) {
                return;
            }
            Iterator it = listTake.iterator();
            while (it.hasNext()) {
                if (!org.jetbrains.kotlin.ir.types.IrTypeUtilsKt.isNullable(((IrValueParameter) it.next()).getType())) {
                    DeclarationIrBuilder declarationIrBuilderCreateIrBuilder$default = LowerUtilsKt.createIrBuilder$default(this.context, irSimpleFunction.getSymbol(), 0, 0, 6, (Object) null);
                    IrBlockBodyBuilder irBlockBodyBuilder = new IrBlockBodyBuilder(declarationIrBuilderCreateIrBuilder$default.getContext(), declarationIrBuilderCreateIrBuilder$default.getScope(), declarationIrBuilderCreateIrBuilder$default.getStartOffset(), declarationIrBuilderCreateIrBuilder$default.getEndOffset());
                    for (IrValueParameter irValueParameter : listTake) {
                        IrType type = irValueParameter.getType();
                        if (!org.jetbrains.kotlin.ir.types.IrTypeUtilsKt.isNullable(type)) {
                            irValueParameter.setType(IrTypesKt.makeNullable(type));
                            irBlockBodyBuilder.unaryPlus(parameterTypeCheck(irBlockBodyBuilder, irValueParameter, type, specialMethodWithDefaultInfo.getDefaultValueGenerator().invoke(irSimpleFunction)));
                        }
                    }
                    IrBody body = irSimpleFunction.getBody();
                    if (body instanceof IrExpressionBody) {
                        irBlockBodyBuilder.unaryPlus(ExpressionHelpersKt.irReturn(irBlockBodyBuilder, ((IrExpressionBody) body).getExpression()));
                    } else if (body instanceof IrBlockBody) {
                        Iterator<T> it2 = ((IrBlockBody) body).getStatements().iterator();
                        while (it2.hasNext()) {
                            irBlockBodyBuilder.unaryPlus((IrStatement) it2.next());
                        }
                    } else if (body != null) {
                        throw new IllegalStateException(("Unsupported method body kind: " + RenderIrElementKt.render$default(body, (DumpIrTreeOptions) null, 1, (Object) null)).toString());
                    }
                    irSimpleFunction.setBody(irBlockBodyBuilder.getIrBlockBody());
                    return;
                }
            }
            return;
        }
        int i = 0;
        for (Object obj : irSimpleFunction.getValueParameters()) {
            int i2 = i + 1;
            if (i < 0) {
                CollectionsKt.throwIndexOverflow();
            }
            IrValueParameter irValueParameter2 = (IrValueParameter) obj;
            if (AsmUtil.isPrimitive(IrTypeMapper.mapType$default(this.context.getDefaultTypeMapper(), irValueParameter2.getType(), null, null, false, 14, null)) && method.getArgumentTypes()[i].getSort() == 10) {
                irValueParameter2.setType(IrTypesKt.makeNullable(irValueParameter2.getType()));
            }
            i = i2;
        }
    }

    private final IrIfThenElseImpl parameterTypeCheck(IrBuilderWithScope irBuilderWithScope, IrValueParameter irValueParameter, IrType irType, IrExpression irExpression) {
        return ExpressionHelpersKt.irIfThen$default(irBuilderWithScope, irBuilderWithScope.getContext().getIrBuiltIns().getUnitType(), LowerUtilsKt.irNot(irBuilderWithScope, ExpressionHelpersKt.irIs(irBuilderWithScope, ExpressionHelpersKt.irGet(irBuilderWithScope, irValueParameter), irType)), ExpressionHelpersKt.irReturn(irBuilderWithScope, irExpression), null, 8, null);
    }

    /* JADX WARN: Multi-variable type inference failed */
    static /* synthetic */ void copyParametersWithErasure$default(BridgeLowering bridgeLowering, IrSimpleFunction irSimpleFunction, IrClass irClass, IrSimpleFunction irSimpleFunction2, List list, int i, Object obj) {
        if ((i & 4) != 0) {
            list = null;
        }
        bridgeLowering.copyParametersWithErasure(irSimpleFunction, irClass, irSimpleFunction2, list);
    }

    private final void copyParametersWithErasure(IrSimpleFunction irSimpleFunction, IrClass irClass, IrSimpleFunction irSimpleFunction2, List<? extends IrType> list) {
        ArrayList arrayList;
        Set<IrTypeParameter> setCollectVisibleTypeParameters = JvmIrTypeUtilsKt.collectVisibleTypeParameters(irSimpleFunction);
        IrValueParameter thisReceiver = irClass.getThisReceiver();
        irSimpleFunction.setDispatchReceiverParameter(thisReceiver != null ? IrUtilsKt.copyTo$default(thisReceiver, irSimpleFunction, null, 0, 0, 0, null, null, IrUtilsKt.getDefaultType(irClass), null, null, false, false, false, 8062, null) : null);
        IrValueParameter extensionReceiverParameter = irSimpleFunction2.getExtensionReceiverParameter();
        irSimpleFunction.setExtensionReceiverParameter(extensionReceiverParameter != null ? copyWithTypeErasure$default(this, extensionReceiverParameter, irSimpleFunction, setCollectVisibleTypeParameters, null, 4, null) : null);
        if (list != null) {
            List<Pair> listZip = CollectionsKt.zip(irSimpleFunction2.getValueParameters(), list);
            ArrayList arrayList2 = new ArrayList(CollectionsKt.collectionSizeOrDefault(listZip, 10));
            for (Pair pair : listZip) {
                arrayList2.add(copyWithTypeErasure((IrValueParameter) pair.component1(), irSimpleFunction, setCollectVisibleTypeParameters, (IrType) pair.component2()));
            }
            arrayList = arrayList2;
        } else {
            List<IrValueParameter> valueParameters = irSimpleFunction2.getValueParameters();
            ArrayList arrayList3 = new ArrayList(CollectionsKt.collectionSizeOrDefault(valueParameters, 10));
            Iterator<T> it = valueParameters.iterator();
            while (it.hasNext()) {
                arrayList3.add(copyWithTypeErasure$default(this, (IrValueParameter) it.next(), irSimpleFunction, setCollectVisibleTypeParameters, null, 4, null));
            }
            arrayList = arrayList3;
        }
        irSimpleFunction.setValueParameters(arrayList);
        irSimpleFunction.setContextReceiverParametersCount(irSimpleFunction2.getContextReceiverParametersCount());
    }

    static /* synthetic */ IrValueParameter copyWithTypeErasure$default(BridgeLowering bridgeLowering, IrValueParameter irValueParameter, IrSimpleFunction irSimpleFunction, Set set, IrType irType, int i, Object obj) {
        if ((i & 4) != 0) {
            irType = null;
        }
        return bridgeLowering.copyWithTypeErasure(irValueParameter, irSimpleFunction, set, irType);
    }

    private final IrValueParameter copyWithTypeErasure(IrValueParameter irValueParameter, IrSimpleFunction irSimpleFunction, Set<? extends IrTypeParameter> set, IrType irType) {
        IrType irTypeEraseTypeParameters;
        IrSimpleFunction irSimpleFunction2 = irSimpleFunction;
        IrDeclarationOrigin.BRIDGE bridge = IrDeclarationOrigin.BRIDGE.INSTANCE;
        int startOffset = irSimpleFunction.getStartOffset();
        int endOffset = irSimpleFunction.getEndOffset();
        if (irType == null || (irTypeEraseTypeParameters = JvmIrTypeUtilsKt.eraseToScope(irType, set)) == null) {
            irTypeEraseTypeParameters = JvmIrTypeUtilsKt.eraseTypeParameters(irValueParameter.getType());
        }
        IrType irType2 = irTypeEraseTypeParameters;
        IrType varargElementType = irValueParameter.getVarargElementType();
        return IrUtilsKt.copyTo$default(irValueParameter, irSimpleFunction2, bridge, 0, startOffset, endOffset, null, null, irType2, varargElementType != null ? JvmIrTypeUtilsKt.eraseToScope(varargElementType, set) : null, irValueParameter.getDefaultValue() != null ? IrUtilsKt.createStubDefaultValue(irValueParameter) : null, false, false, false, 7268, null);
    }

    static /* synthetic */ IrExpression delegatingCall$default(BridgeLowering bridgeLowering, IrBuilderWithScope irBuilderWithScope, IrSimpleFunction irSimpleFunction, IrSimpleFunction irSimpleFunction2, IrClassSymbol irClassSymbol, int i, Object obj) {
        if ((i & 4) != 0) {
            irClassSymbol = null;
        }
        return bridgeLowering.delegatingCall(irBuilderWithScope, irSimpleFunction, irSimpleFunction2, irClassSymbol);
    }

    private final List<MemoizedMultiFieldValueClassReplacements.RemappedParameter> getStructure(IrSimpleFunction function) {
        List<MemoizedMultiFieldValueClassReplacements.RemappedParameter> list = this.context.getMultiFieldValueClassReplacements().getBindingNewFunctionToParameterTemplateStructure().get(function);
        if (list == null) {
            return null;
        }
        Iterator<T> it = list.iterator();
        int size = 0;
        while (it.hasNext()) {
            size += ((MemoizedMultiFieldValueClassReplacements.RemappedParameter) it.next()).getValueParameters().size();
        }
        if (size == IrUtilsKt.getExplicitParametersCount(function)) {
            return list;
        }
        throw new IllegalArgumentException(("Bad parameters structure: " + list).toString());
    }

    private final void addBoxedAndUnboxedMfvcArguments(final IrBlockBuilder irBlockBuilder, IrSimpleFunction irSimpleFunction, final IrSimpleFunction irSimpleFunction2, IrCall irCall) {
        for (Map.Entry<IrValueParameter, IrExpression> entry : this.context.getMultiFieldValueClassReplacements().mapFunctionMfvcStructures(irBlockBuilder, irSimpleFunction, irSimpleFunction2, new Function2<IrValueParameter, IrType, IrExpression>() { // from class: org.jetbrains.kotlin.backend.jvm.lower.BridgeLowering$addBoxedAndUnboxedMfvcArguments$parameters2arguments$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(2);
            }

            @Override // kotlin.jvm.functions.Function2
            public final IrExpression invoke(IrValueParameter sourceParameter, IrType targetParameterType) {
                Intrinsics.checkNotNullParameter(sourceParameter, "sourceParameter");
                Intrinsics.checkNotNullParameter(targetParameterType, "targetParameterType");
                if (Intrinsics.areEqual(sourceParameter, irSimpleFunction2.getDispatchReceiverParameter())) {
                    return ExpressionHelpersKt.irGet(irBlockBuilder, sourceParameter);
                }
                IrBlockBuilder irBlockBuilder2 = irBlockBuilder;
                return IrUtilsKt.irCastIfNeeded(irBlockBuilder2, ExpressionHelpersKt.irGet(irBlockBuilder2, sourceParameter), targetParameterType);
            }
        }).entrySet()) {
            IrValueParameter key = entry.getKey();
            IrExpression value = entry.getValue();
            if (value != null) {
                IrExpressionsKt.putArgument(irCall, key, value);
            }
        }
    }

    private final Method getJvmMethod(IrFunction irFunction) {
        return this.context.getBridgeLoweringCache().computeJvmMethod(irFunction);
    }

    private final IrSimpleFunction addAbstractMethodStub(IrClass irClass, IrSimpleFunction irSimpleFunction) {
        IrFactory factory = irClass.getFactory();
        IrFunctionBuilder irFunctionBuilder = new IrFunctionBuilder();
        irFunctionBuilder.updateFrom((IrFunction) irSimpleFunction);
        irFunctionBuilder.setModality(Modality.ABSTRACT);
        irFunctionBuilder.setOrigin(JvmLoweredDeclarationOrigin.ABSTRACT_BRIDGE_STUB.INSTANCE);
        irFunctionBuilder.setName(irSimpleFunction.getName());
        irFunctionBuilder.setReturnType(irSimpleFunction.getReturnType());
        irFunctionBuilder.setFakeOverride(false);
        IrSimpleFunction irSimpleFunctionBuildFunction = DeclarationBuildersKt.buildFunction(factory, irFunctionBuilder);
        IrClass irClass2 = irClass;
        irClass2.getDeclarations().add(irSimpleFunctionBuildFunction);
        irSimpleFunctionBuildFunction.setParent(irClass2);
        JvmIrUtilsKt.copyCorrespondingPropertyFrom(irSimpleFunctionBuildFunction, irSimpleFunction);
        IrValueParameter thisReceiver = irClass.getThisReceiver();
        irSimpleFunctionBuildFunction.setDispatchReceiverParameter(thisReceiver != null ? IrUtilsKt.copyTo$default(thisReceiver, irSimpleFunctionBuildFunction, null, 0, 0, 0, null, null, IrUtilsKt.getDefaultType(irClass), null, null, false, false, false, 8062, null) : null);
        List<IrValueParameter> valueParameters = irSimpleFunction.getValueParameters();
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(valueParameters, 10));
        for (IrValueParameter irValueParameter : valueParameters) {
            arrayList.add(IrUtilsKt.copyTo$default(irValueParameter, irSimpleFunctionBuildFunction, null, 0, 0, 0, null, null, irValueParameter.getType(), null, null, false, false, false, 8062, null));
        }
        irSimpleFunctionBuildFunction.setValueParameters(arrayList);
        irSimpleFunctionBuildFunction.setOverriddenSymbols(CollectionsKt.toList(irSimpleFunction.getOverriddenSymbols()));
        return irSimpleFunctionBuildFunction;
    }

    private final IrSimpleFunction addBridge(IrClass irClass, Bridge bridge, IrSimpleFunction irSimpleFunction) {
        IrExpressionBody irExpressionBodyIrExprBody;
        IrFactory factory = irClass.getFactory();
        IrFunctionBuilder irFunctionBuilder = new IrFunctionBuilder();
        irFunctionBuilder.setStartOffset(irClass.getStartOffset());
        irFunctionBuilder.setEndOffset(irClass.getStartOffset());
        irFunctionBuilder.setModality(Modality.OPEN);
        irFunctionBuilder.setOrigin(IrDeclarationOrigin.BRIDGE.INSTANCE);
        Name nameIdentifier = Name.identifier(bridge.getSignature().getName());
        Intrinsics.checkNotNullExpressionValue(nameIdentifier, "identifier(...)");
        irFunctionBuilder.setName(nameIdentifier);
        irFunctionBuilder.setReturnType(JvmIrTypeUtilsKt.eraseTypeParameters(bridge.getOverridden().getReturnType()));
        irFunctionBuilder.setSuspend(bridge.getOverridden().getIsSuspend());
        IrSimpleFunction irSimpleFunctionBuildFunction = DeclarationBuildersKt.buildFunction(factory, irFunctionBuilder);
        IrClass irClass2 = irClass;
        irClass2.getDeclarations().add(irSimpleFunctionBuildFunction);
        irSimpleFunctionBuildFunction.setParent(irClass2);
        IrDeclarationsKt.copyAttributes(irSimpleFunctionBuildFunction, irSimpleFunction);
        copyParametersWithErasure$default(this, irSimpleFunctionBuildFunction, irClass, bridge.getOverridden(), null, 4, null);
        this.context.remapMultiFieldValueClassStructure(bridge.getOverridden(), irSimpleFunctionBuildFunction, null);
        if (isThrowingStub(irSimpleFunction)) {
            irExpressionBodyIrExprBody = CollectionStubMethodLoweringKt.createThrowingStubBody(this.context, irSimpleFunctionBuildFunction);
        } else {
            DeclarationIrBuilder declarationIrBuilderCreateIrBuilder = LowerUtilsKt.createIrBuilder(this.context, irSimpleFunctionBuildFunction.getSymbol(), irSimpleFunctionBuildFunction.getStartOffset(), irSimpleFunctionBuildFunction.getEndOffset());
            irExpressionBodyIrExprBody = ExpressionHelpersKt.irExprBody(declarationIrBuilderCreateIrBuilder, delegatingCall$default(this, declarationIrBuilderCreateIrBuilder, irSimpleFunctionBuildFunction, irSimpleFunction, null, 4, null));
        }
        irSimpleFunctionBuildFunction.setBody(irExpressionBodyIrExprBody);
        if (!isTypeParameterWithPrimitiveUpperBound(bridge.getOverridden().getReturnType())) {
            List<IrSimpleFunctionSymbol> overriddenSymbols = bridge.getOverriddenSymbols();
            LinkedHashSet linkedHashSet = new LinkedHashSet();
            Iterator<T> it = overriddenSymbols.iterator();
            while (it.hasNext()) {
                CollectionsKt.addAll(linkedHashSet, ((IrSimpleFunctionSymbol) it.next()).getOwner().getOverriddenSymbols());
            }
            LinkedHashSet linkedHashSet2 = linkedHashSet;
            LinkedHashSet linkedHashSet3 = new LinkedHashSet();
            Iterator it2 = linkedHashSet2.iterator();
            while (it2.hasNext()) {
                List listAllOverridden$default = IrUtilsKt.allOverridden$default(((IrSimpleFunctionSymbol) it2.next()).getOwner(), false, 1, null);
                ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(listAllOverridden$default, 10));
                Iterator it3 = listAllOverridden$default.iterator();
                while (it3.hasNext()) {
                    arrayList.add(((IrSimpleFunction) it3.next()).getSymbol());
                }
                CollectionsKt.addAll(linkedHashSet3, arrayList);
            }
            LinkedHashSet linkedHashSet4 = linkedHashSet3;
            ArrayList arrayList2 = new ArrayList();
            for (Object obj : linkedHashSet2) {
                if (!linkedHashSet4.contains((IrSimpleFunctionSymbol) obj)) {
                    arrayList2.add(obj);
                }
            }
            irSimpleFunctionBuildFunction.setOverriddenSymbols(arrayList2);
        }
        return irSimpleFunctionBuildFunction;
    }

    private final IrSimpleFunction addSpecialBridge(IrClass irClass, SpecialBridge specialBridge, IrSimpleFunction irSimpleFunction) {
        IrType irTypeEraseTypeParameters;
        IrFactory factory = irClass.getFactory();
        IrFunctionBuilder irFunctionBuilder = new IrFunctionBuilder();
        irFunctionBuilder.setStartOffset(irClass.getStartOffset());
        irFunctionBuilder.setEndOffset(irClass.getStartOffset());
        irFunctionBuilder.setModality(specialBridge.isFinal() ? Modality.FINAL : Modality.OPEN);
        irFunctionBuilder.setOrigin(specialBridge.isSynthetic() ? IrDeclarationOrigin.BRIDGE.INSTANCE : IrDeclarationOrigin.BRIDGE_SPECIAL.INSTANCE);
        Name nameIdentifier = Name.identifier(specialBridge.getSignature().getName());
        Intrinsics.checkNotNullExpressionValue(nameIdentifier, "identifier(...)");
        irFunctionBuilder.setName(nameIdentifier);
        IrType substitutedReturnType = specialBridge.getSubstitutedReturnType();
        if (substitutedReturnType == null || (irTypeEraseTypeParameters = JvmIrTypeUtilsKt.eraseToScope(substitutedReturnType, IrUtilsKt.getParentAsClass(irSimpleFunction))) == null) {
            irTypeEraseTypeParameters = JvmIrTypeUtilsKt.eraseTypeParameters(specialBridge.getOverridden().getReturnType());
        }
        irFunctionBuilder.setReturnType(irTypeEraseTypeParameters);
        IrSimpleFunction irSimpleFunctionBuildFunction = DeclarationBuildersKt.buildFunction(factory, irFunctionBuilder);
        IrClass irClass2 = irClass;
        irClass2.getDeclarations().add(irSimpleFunctionBuildFunction);
        irSimpleFunctionBuildFunction.setParent(irClass2);
        this.context.getFunctionsWithSpecialBridges().add(irSimpleFunction);
        copyParametersWithErasure(irSimpleFunctionBuildFunction, irClass, specialBridge.getOverridden(), specialBridge.getSubstitutedParameterTypes());
        IrSimpleFunction irSimpleFunction2 = irSimpleFunctionBuildFunction;
        this.context.remapMultiFieldValueClassStructure(specialBridge.getOverridden(), irSimpleFunction2, null);
        DeclarationIrBuilder declarationIrBuilderCreateIrBuilder = LowerUtilsKt.createIrBuilder(this.context, irSimpleFunctionBuildFunction.getSymbol(), irSimpleFunctionBuildFunction.getStartOffset(), irSimpleFunctionBuildFunction.getEndOffset());
        IrBlockBodyBuilder irBlockBodyBuilder = new IrBlockBodyBuilder(declarationIrBuilderCreateIrBuilder.getContext(), declarationIrBuilderCreateIrBuilder.getScope(), declarationIrBuilderCreateIrBuilder.getStartOffset(), declarationIrBuilderCreateIrBuilder.getEndOffset());
        SpecialMethodWithDefaultInfo methodInfo = specialBridge.getMethodInfo();
        if (methodInfo != null) {
            for (IrValueParameter irValueParameter : CollectionsKt.take(irSimpleFunctionBuildFunction.getValueParameters(), methodInfo.getArgumentsToCheck())) {
                irBlockBodyBuilder.unaryPlus(parameterTypeCheck(irBlockBodyBuilder, irValueParameter, irSimpleFunction.getValueParameters().get(irValueParameter.getIndex()).getType(), methodInfo.getDefaultValueGenerator().invoke(irSimpleFunctionBuildFunction)));
            }
        }
        IrBlockBodyBuilder irBlockBodyBuilder2 = irBlockBodyBuilder;
        irBlockBodyBuilder.unaryPlus(ExpressionHelpersKt.irReturn(irBlockBodyBuilder2, delegatingCall(irBlockBodyBuilder2, irSimpleFunctionBuildFunction, irSimpleFunction, specialBridge.getSuperQualifierSymbol())));
        irSimpleFunctionBuildFunction.setBody(irBlockBodyBuilder.getIrBlockBody());
        if (specialBridge.isOverriding()) {
            irSimpleFunctionBuildFunction.setOverriddenSymbols(CollectionsKt.listOf(specialBridge.getOverridden().getSymbol()));
        }
        if (this.context.getDefaultMethodSignatureMapper().shouldBoxSingleValueParameterForSpecialCaseOfRemove(irSimpleFunction2)) {
            IrValueParameter irValueParameter2 = (IrValueParameter) CollectionsKt.last((List) irSimpleFunctionBuildFunction.getValueParameters());
            irValueParameter2.setType(IrTypesKt.makeNullable(irValueParameter2.getType()));
        }
        return irSimpleFunctionBuildFunction;
    }

    private final IrExpression delegatingCall(IrBuilderWithScope irBuilderWithScope, IrSimpleFunction irSimpleFunction, IrSimpleFunction irSimpleFunction2, IrClassSymbol irClassSymbol) {
        IrBlockBuilder irBlockBuilder = new IrBlockBuilder(irBuilderWithScope.getContext(), irBuilderWithScope.getScope(), irBuilderWithScope.getStartOffset(), irBuilderWithScope.getEndOffset(), null, null, false, 64, null);
        IrBlockBuilder irBlockBuilder2 = irBlockBuilder;
        IrSimpleFunction irSimpleFunction3 = irSimpleFunction2;
        IrCall irCall = ExpressionHelpersKt.irCall(irBlockBuilder2, irSimpleFunction3, IrStatementOrigin.BRIDGE_DELEGATION.INSTANCE, irClassSymbol);
        if (getStructure(irSimpleFunction2) == null && getStructure(irSimpleFunction) == null) {
            for (Pair pair : CollectionsKt.zip(IrUtilsKt.getExplicitParameters(irSimpleFunction), IrUtilsKt.getExplicitParameters(irSimpleFunction3))) {
                IrValueParameter irValueParameter = (IrValueParameter) pair.component1();
                IrValueParameter irValueParameter2 = (IrValueParameter) pair.component2();
                IrGetValueImpl irGetValueImplIrGet = ExpressionHelpersKt.irGet(irBlockBuilder2, irValueParameter);
                if (!Intrinsics.areEqual(irValueParameter, irSimpleFunction.getDispatchReceiverParameter())) {
                    irGetValueImplIrGet = IrUtilsKt.irCastIfNeeded(irBlockBuilder2, irGetValueImplIrGet, JvmIrTypeUtilsKt.getUpperBound(irValueParameter2.getType()));
                }
                IrExpressionsKt.putArgument(irCall, irValueParameter2, irGetValueImplIrGet);
            }
        } else {
            addBoxedAndUnboxedMfvcArguments(irBlockBuilder, irSimpleFunction2, irSimpleFunction, irCall);
        }
        Unit unit = Unit.INSTANCE;
        irBlockBuilder.unaryPlus(ExpressionHelpersKt.irReturn(irBlockBuilder2, irCall));
        return IrUtilsKt.irCastIfNeeded(irBuilderWithScope, IrUtilsKt.unwrapBlock(irBlockBuilder.getIrBlockBody()), JvmIrTypeUtilsKt.getUpperBound(irSimpleFunction.getReturnType()));
    }
}
