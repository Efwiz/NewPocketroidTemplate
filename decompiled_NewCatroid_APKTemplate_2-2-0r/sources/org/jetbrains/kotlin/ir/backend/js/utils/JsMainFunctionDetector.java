package org.jetbrains.kotlin.ir.backend.js.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.ir.backend.js.JsCommonBackendContext;
import org.jetbrains.kotlin.ir.declarations.IrDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationParent;
import org.jetbrains.kotlin.ir.declarations.IrFile;
import org.jetbrains.kotlin.ir.declarations.IrModuleFragment;
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction;
import org.jetbrains.kotlin.ir.declarations.IrValueParameter;
import org.jetbrains.kotlin.ir.types.IrTypePredicatesKt;

/* JADX INFO: compiled from: JsMainFunctionDetector.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0010\u0010\u0007\u001a\u0004\u0018\u00010\b2\u0006\u0010\t\u001a\u00020\nJ\u0010\u0010\u0007\u001a\u0004\u0018\u00010\b2\u0006\u0010\u000b\u001a\u00020\fJ\u0014\u0010\r\u001a\u00020\u000e*\u00020\b2\u0006\u0010\u000f\u001a\u00020\u000eH\u0002J\u0014\u0010\u0010\u001a\u00020\u000e*\u00020\b2\u0006\u0010\u000f\u001a\u00020\u000eH\u0002R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0011"}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/js/utils/JsMainFunctionDetector;", "", "context", "Lorg/jetbrains/kotlin/ir/backend/js/JsCommonBackendContext;", "(Lorg/jetbrains/kotlin/ir/backend/js/JsCommonBackendContext;)V", "getContext", "()Lorg/jetbrains/kotlin/ir/backend/js/JsCommonBackendContext;", "getMainFunctionOrNull", "Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;", "file", "Lorg/jetbrains/kotlin/ir/declarations/IrFile;", "module", "Lorg/jetbrains/kotlin/ir/declarations/IrModuleFragment;", "isMain", "", "allowEmptyParameters", "isSuitableForMainParametersSize", "backend.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class JsMainFunctionDetector {
    private final JsCommonBackendContext context;

    public JsMainFunctionDetector(JsCommonBackendContext context) {
        Intrinsics.checkNotNullParameter(context, "context");
        this.context = context;
    }

    public final JsCommonBackendContext getContext() {
        return this.context;
    }

    private final boolean isSuitableForMainParametersSize(IrSimpleFunction irSimpleFunction, boolean z) {
        int size = irSimpleFunction.getValueParameters().size();
        if (size != 0) {
            return size == 1 || size == 2;
        }
        return z;
    }

    private final boolean isMain(IrSimpleFunction irSimpleFunction, boolean z) {
        boolean z2 = false;
        if (!irSimpleFunction.getTypeParameters().isEmpty() || !isSuitableForMainParametersSize(irSimpleFunction, z)) {
            return false;
        }
        boolean zIsLoweredSuspendFunction = JsMainFunctionDetectorKt.isLoweredSuspendFunction(irSimpleFunction, this.context);
        if ((!IrTypePredicatesKt.isUnit(irSimpleFunction.getReturnType()) && (!zIsLoweredSuspendFunction || (!Intrinsics.areEqual(irSimpleFunction.getReturnType(), this.context.getIrBuiltIns().getAnyNType()) && !Intrinsics.areEqual(irSimpleFunction.getReturnType(), this.context.getIrBuiltIns().getAnyType())))) || !Intrinsics.areEqual(irSimpleFunction.getName().asString(), "main") || irSimpleFunction.getExtensionReceiverParameter() != null) {
            return false;
        }
        if (irSimpleFunction.getValueParameters().size() == 1) {
            return zIsLoweredSuspendFunction || JsMainFunctionDetectorKt.isStringArrayParameter((IrValueParameter) CollectionsKt.single((List) irSimpleFunction.getValueParameters()));
        }
        if (irSimpleFunction.getValueParameters().size() == 2) {
            return JsMainFunctionDetectorKt.isStringArrayParameter(irSimpleFunction.getValueParameters().get(0)) && zIsLoweredSuspendFunction;
        }
        if (!z) {
            throw new IllegalArgumentException("Failed requirement.".toString());
        }
        if (!irSimpleFunction.getValueParameters().isEmpty()) {
            throw new IllegalArgumentException("Failed requirement.".toString());
        }
        IrDeclarationParent parent = irSimpleFunction.getParent();
        Intrinsics.checkNotNull(parent, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.declarations.IrFile");
        List<IrDeclaration> declarations = ((IrFile) parent).getDeclarations();
        ArrayList arrayList = new ArrayList();
        for (Object obj : declarations) {
            if (obj instanceof IrSimpleFunction) {
                arrayList.add(obj);
            }
        }
        ArrayList arrayList2 = arrayList;
        if (!(arrayList2 instanceof Collection) || !arrayList2.isEmpty()) {
            Iterator it = arrayList2.iterator();
            while (it.hasNext()) {
                if (isMain((IrSimpleFunction) it.next(), false)) {
                    z2 = true;
                    break;
                }
            }
        }
        return !z2;
    }

    public final IrSimpleFunction getMainFunctionOrNull(IrFile file) {
        Intrinsics.checkNotNullParameter(file, "file");
        List<IrDeclaration> declarations = file.getDeclarations();
        ArrayList arrayList = new ArrayList();
        for (Object obj : declarations) {
            if (obj instanceof IrSimpleFunction) {
                arrayList.add(obj);
            }
        }
        Object obj2 = null;
        boolean z = false;
        Object obj3 = null;
        for (Object obj4 : arrayList) {
            if (isMain((IrSimpleFunction) obj4, true)) {
                if (z) {
                    return (IrSimpleFunction) obj2;
                }
                obj3 = obj4;
                z = true;
            }
        }
        if (z) {
            obj2 = obj3;
        }
        return (IrSimpleFunction) obj2;
    }

    public final IrSimpleFunction getMainFunctionOrNull(IrModuleFragment module) {
        Intrinsics.checkNotNullParameter(module, "module");
        Pair pair = null;
        for (IrFile irFile : module.getFiles()) {
            String strAsString = irFile.getPackageFqName().asString();
            Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
            IrSimpleFunction mainFunctionOrNull = getMainFunctionOrNull(irFile);
            if (mainFunctionOrNull != null) {
                if (pair == null) {
                    pair = new Pair(strAsString, mainFunctionOrNull);
                } else if (strAsString.compareTo((String) pair.getFirst()) < 0) {
                    pair = new Pair(strAsString, mainFunctionOrNull);
                }
            }
        }
        if (pair != null) {
            return (IrSimpleFunction) pair.getSecond();
        }
        return null;
    }
}
