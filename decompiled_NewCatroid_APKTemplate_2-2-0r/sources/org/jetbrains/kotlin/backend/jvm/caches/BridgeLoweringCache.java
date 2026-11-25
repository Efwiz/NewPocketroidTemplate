package org.jetbrains.kotlin.backend.jvm.caches;

import com.gaurav.avnc.vnc.XKeySym;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.jetbrains.kotlin.backend.common.lower.BuiltInWithDifferentJvmName;
import org.jetbrains.kotlin.backend.common.lower.SpecialBridgeMethods;
import org.jetbrains.kotlin.backend.common.lower.SpecialMethodWithDefaultInfo;
import org.jetbrains.kotlin.backend.jvm.JvmBackendContext;
import org.jetbrains.kotlin.backend.jvm.SpecialBridge;
import org.jetbrains.kotlin.ir.builders.declarations.DeclarationBuildersKt;
import org.jetbrains.kotlin.ir.builders.declarations.IrFunctionBuilder;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationOrigin;
import org.jetbrains.kotlin.ir.declarations.IrFactory;
import org.jetbrains.kotlin.ir.declarations.IrFunction;
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction;
import org.jetbrains.kotlin.ir.declarations.IrValueParameter;
import org.jetbrains.kotlin.ir.symbols.IrFunctionSymbol;
import org.jetbrains.kotlin.ir.symbols.IrPropertySymbol;
import org.jetbrains.kotlin.ir.symbols.IrSimpleFunctionSymbol;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.ir.util.IrUtilsKt;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.org.objectweb.asm.commons.Method;

/* JADX INFO: compiled from: BridgeLoweringCache.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0010\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000eH\u0002J\u000e\u0010\u000f\u001a\u00020\b2\u0006\u0010\r\u001a\u00020\u0010J\u0010\u0010\u0011\u001a\u0004\u0018\u00010\u00122\u0006\u0010\r\u001a\u00020\u000eR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\u0005\u001a\u000e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\b0\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0013"}, d2 = {"Lorg/jetbrains/kotlin/backend/jvm/caches/BridgeLoweringCache;", "", "context", "Lorg/jetbrains/kotlin/backend/jvm/JvmBackendContext;", "(Lorg/jetbrains/kotlin/backend/jvm/JvmBackendContext;)V", "signatureCache", "Ljava/util/concurrent/ConcurrentHashMap;", "Lorg/jetbrains/kotlin/ir/symbols/IrFunctionSymbol;", "Lorg/jetbrains/org/objectweb/asm/commons/Method;", "specialBridgeMethods", "Lorg/jetbrains/kotlin/backend/common/lower/SpecialBridgeMethods;", "canHaveSpecialBridge", "", "function", "Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;", "computeJvmMethod", "Lorg/jetbrains/kotlin/ir/declarations/IrFunction;", "computeSpecialBridge", "Lorg/jetbrains/kotlin/backend/jvm/SpecialBridge;", "backend.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class BridgeLoweringCache {
    private final JvmBackendContext context;
    private final ConcurrentHashMap<IrFunctionSymbol, Method> signatureCache;
    private final SpecialBridgeMethods specialBridgeMethods;

    public BridgeLoweringCache(JvmBackendContext context) {
        Intrinsics.checkNotNullParameter(context, "context");
        this.context = context;
        this.specialBridgeMethods = new SpecialBridgeMethods(context);
        this.signatureCache = new ConcurrentHashMap<>();
    }

    public final Method computeJvmMethod(IrFunction function) {
        Intrinsics.checkNotNullParameter(function, "function");
        ConcurrentHashMap<IrFunctionSymbol, Method> concurrentHashMap = this.signatureCache;
        IrFunctionSymbol symbol = function.getSymbol();
        Method method = concurrentHashMap.get(symbol);
        if (method == null) {
            Method methodMapAsmMethod = this.context.getDefaultMethodSignatureMapper().mapAsmMethod(function);
            Method methodPutIfAbsent = concurrentHashMap.putIfAbsent(symbol, methodMapAsmMethod);
            method = methodPutIfAbsent == null ? methodMapAsmMethod : methodPutIfAbsent;
        }
        Intrinsics.checkNotNullExpressionValue(method, "getOrPut(...)");
        return method;
    }

    private final boolean canHaveSpecialBridge(IrSimpleFunction function) {
        if (this.specialBridgeMethods.getSpecialMethodNames().contains(function.getName())) {
            return true;
        }
        String strAsString = function.getName().asString();
        Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
        HashSet<Name> specialMethodNames = this.specialBridgeMethods.getSpecialMethodNames();
        if (!(specialMethodNames instanceof Collection) || !specialMethodNames.isEmpty()) {
            Iterator<T> it = specialMethodNames.iterator();
            while (it.hasNext()) {
                if (StringsKt.startsWith$default(strAsString, ((Name) it.next()).asString() + '-', false, 2, (Object) null)) {
                    return true;
                }
            }
        }
        return false;
    }

    public final SpecialBridge computeSpecialBridge(IrSimpleFunction function) {
        SpecialBridge unsubstitutedSpecialBridge;
        Intrinsics.checkNotNullParameter(function, "function");
        IrPropertySymbol correspondingPropertySymbol = function.getCorrespondingPropertySymbol();
        SpecialBridge specialBridgeCopy = null;
        if (correspondingPropertySymbol != null) {
            if (!this.specialBridgeMethods.getSpecialPropertyNames().contains(correspondingPropertySymbol.getOwner().getName())) {
                return null;
            }
        } else if (!canHaveSpecialBridge(function)) {
            return null;
        }
        SpecialMethodWithDefaultInfo specialMethodInfo = this.specialBridgeMethods.getSpecialMethodInfo(function);
        if (specialMethodInfo != null) {
            return new SpecialBridge(function, computeJvmMethod(function), specialMethodInfo.getNeedsGenericSignature(), null, null, specialMethodInfo, null, false, false, false, specialMethodInfo.getNeedsUnsubstitutedBridge(), null, XKeySym.XK_rightshoe, null);
        }
        BuiltInWithDifferentJvmName builtInWithDifferentJvmName = this.specialBridgeMethods.getBuiltInWithDifferentJvmName(function);
        if (builtInWithDifferentJvmName != null) {
            return new SpecialBridge(function, computeJvmMethod(function), builtInWithDifferentJvmName.getNeedsGenericSignature(), null, null, null, null, false, false, builtInWithDifferentJvmName.getIsOverriding(), false, null, XKeySym.XK_Thai_lekpaet, null);
        }
        Iterator<IrSimpleFunctionSymbol> it = function.getOverriddenSymbols().iterator();
        while (it.hasNext()) {
            SpecialBridge specialBridgeComputeSpecialBridge = computeSpecialBridge(it.next().getOwner());
            if (specialBridgeComputeSpecialBridge != null) {
                if (!specialBridgeComputeSpecialBridge.getNeedsGenericSignature()) {
                    return specialBridgeComputeSpecialBridge;
                }
                SpecialMethodWithDefaultInfo methodInfo = specialBridgeComputeSpecialBridge.getMethodInfo();
                int i = 0;
                int argumentsToCheck = methodInfo != null ? methodInfo.getArgumentsToCheck() : 0;
                List<IrValueParameter> valueParameters = function.getValueParameters();
                ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(valueParameters, 10));
                for (Object obj : valueParameters) {
                    int i2 = i + 1;
                    if (i < 0) {
                        CollectionsKt.throwIndexOverflow();
                    }
                    arrayList.add(i < argumentsToCheck ? this.context.getIrBuiltIns().getAnyNType() : ((IrValueParameter) obj).getType());
                    i = i2;
                }
                ArrayList arrayList2 = arrayList;
                IrFactory irFactory = this.context.getIrFactory();
                IrFunctionBuilder irFunctionBuilder = new IrFunctionBuilder();
                irFunctionBuilder.updateFrom((IrFunction) specialBridgeComputeSpecialBridge.getOverridden());
                Name nameIdentifier = Name.identifier(specialBridgeComputeSpecialBridge.getSignature().getName());
                Intrinsics.checkNotNullExpressionValue(nameIdentifier, "identifier(...)");
                irFunctionBuilder.setName(nameIdentifier);
                irFunctionBuilder.setReturnType(function.getReturnType());
                IrSimpleFunction irSimpleFunctionBuildFunction = DeclarationBuildersKt.buildFunction(irFactory, irFunctionBuilder);
                List<Pair> listZip = CollectionsKt.zip(function.getValueParameters(), arrayList2);
                ArrayList arrayList3 = new ArrayList(CollectionsKt.collectionSizeOrDefault(listZip, 10));
                for (Pair pair : listZip) {
                    arrayList3.add(IrUtilsKt.copyTo$default((IrValueParameter) pair.component1(), irSimpleFunctionBuildFunction, IrDeclarationOrigin.BRIDGE.INSTANCE, 0, 0, 0, null, null, (IrType) pair.component2(), null, null, false, false, false, 8060, null));
                }
                irSimpleFunctionBuildFunction.setValueParameters(arrayList3);
                irSimpleFunctionBuildFunction.setOverriddenSymbols(CollectionsKt.listOf(specialBridgeComputeSpecialBridge.getOverridden().getSymbol()));
                irSimpleFunctionBuildFunction.setParent(function.getParent());
                Method methodComputeJvmMethod = computeJvmMethod(irSimpleFunctionBuildFunction);
                if (specialBridgeComputeSpecialBridge.getUnsubstitutedSpecialBridge() != null) {
                    unsubstitutedSpecialBridge = specialBridgeComputeSpecialBridge.getUnsubstitutedSpecialBridge();
                    methodComputeJvmMethod = methodComputeJvmMethod;
                } else {
                    if (specialBridgeComputeSpecialBridge.getNeedsUnsubstitutedBridge() && !Intrinsics.areEqual(specialBridgeComputeSpecialBridge.getSignature(), methodComputeJvmMethod)) {
                        specialBridgeCopy = specialBridgeComputeSpecialBridge.copy((XKeySym.XK_Greek_epsilon & 1) != 0 ? specialBridgeComputeSpecialBridge.overridden : null, (XKeySym.XK_Greek_epsilon & 2) != 0 ? specialBridgeComputeSpecialBridge.signature : null, (XKeySym.XK_Greek_epsilon & 4) != 0 ? specialBridgeComputeSpecialBridge.needsGenericSignature : false, (XKeySym.XK_Greek_epsilon & 8) != 0 ? specialBridgeComputeSpecialBridge.substitutedParameterTypes : null, (XKeySym.XK_Greek_epsilon & 16) != 0 ? specialBridgeComputeSpecialBridge.substitutedReturnType : null, (XKeySym.XK_Greek_epsilon & 32) != 0 ? specialBridgeComputeSpecialBridge.methodInfo : null, (XKeySym.XK_Greek_epsilon & 64) != 0 ? specialBridgeComputeSpecialBridge.superQualifierSymbol : null, (XKeySym.XK_Greek_epsilon & 128) != 0 ? specialBridgeComputeSpecialBridge.isFinal : false, (XKeySym.XK_Greek_epsilon & 256) != 0 ? specialBridgeComputeSpecialBridge.isSynthetic : true, (XKeySym.XK_Greek_epsilon & 512) != 0 ? specialBridgeComputeSpecialBridge.isOverriding : false, (XKeySym.XK_Greek_epsilon & 1024) != 0 ? specialBridgeComputeSpecialBridge.needsUnsubstitutedBridge : false, (XKeySym.XK_Greek_epsilon & 2048) != 0 ? specialBridgeComputeSpecialBridge.unsubstitutedSpecialBridge : null);
                    }
                    unsubstitutedSpecialBridge = specialBridgeCopy;
                }
                return specialBridgeComputeSpecialBridge.copy((XKeySym.XK_Greek_epsilon & 1) != 0 ? specialBridgeComputeSpecialBridge.overridden : null, (XKeySym.XK_Greek_epsilon & 2) != 0 ? specialBridgeComputeSpecialBridge.signature : methodComputeJvmMethod, (XKeySym.XK_Greek_epsilon & 4) != 0 ? specialBridgeComputeSpecialBridge.needsGenericSignature : false, (XKeySym.XK_Greek_epsilon & 8) != 0 ? specialBridgeComputeSpecialBridge.substitutedParameterTypes : arrayList2, (XKeySym.XK_Greek_epsilon & 16) != 0 ? specialBridgeComputeSpecialBridge.substitutedReturnType : function.getReturnType(), (XKeySym.XK_Greek_epsilon & 32) != 0 ? specialBridgeComputeSpecialBridge.methodInfo : null, (XKeySym.XK_Greek_epsilon & 64) != 0 ? specialBridgeComputeSpecialBridge.superQualifierSymbol : null, (XKeySym.XK_Greek_epsilon & 128) != 0 ? specialBridgeComputeSpecialBridge.isFinal : false, (XKeySym.XK_Greek_epsilon & 256) != 0 ? specialBridgeComputeSpecialBridge.isSynthetic : false, (XKeySym.XK_Greek_epsilon & 512) != 0 ? specialBridgeComputeSpecialBridge.isOverriding : false, (XKeySym.XK_Greek_epsilon & 1024) != 0 ? specialBridgeComputeSpecialBridge.needsUnsubstitutedBridge : false, (XKeySym.XK_Greek_epsilon & 2048) != 0 ? specialBridgeComputeSpecialBridge.unsubstitutedSpecialBridge : unsubstitutedSpecialBridge);
            }
        }
        return null;
    }
}
