package org.jetbrains.kotlin.backend.common.lower;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.Sequence;
import org.jetbrains.kotlin.backend.common.BackendContext;
import org.jetbrains.kotlin.ir.builders.declarations.DeclarationBuildersKt;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationOrigin;
import org.jetbrains.kotlin.ir.declarations.IrFunction;
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction;
import org.jetbrains.kotlin.ir.symbols.IrSimpleFunctionSymbol;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.ir.types.IrTypesKt;
import org.jetbrains.kotlin.ir.util.IrUtilsKt;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.utils.CollectionsKt;

/* JADX INFO: compiled from: MethodsFromAnyGeneratorForLowerings.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u0000 \u00182\u00020\u0001:\u0001\u0018B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ\u0006\u0010\u000f\u001a\u00020\u0010J\u0006\u0010\u0011\u001a\u00020\u0010J\u0006\u0010\u0012\u001a\u00020\u0010J\u001c\u0010\u0013\u001a\u00020\u0010*\u00020\u00052\u0006\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u0017H\u0002R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0011\u0010\u0006\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000e¨\u0006\u0019"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/lower/MethodsFromAnyGeneratorForLowerings;", "", "context", "Lorg/jetbrains/kotlin/backend/common/BackendContext;", "irClass", "Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "origin", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationOrigin;", "(Lorg/jetbrains/kotlin/backend/common/BackendContext;Lorg/jetbrains/kotlin/ir/declarations/IrClass;Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationOrigin;)V", "getContext", "()Lorg/jetbrains/kotlin/backend/common/BackendContext;", "getIrClass", "()Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "getOrigin", "()Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationOrigin;", "createEqualsMethodDeclaration", "Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;", "createHashCodeMethodDeclaration", "createToStringMethodDeclaration", "addSyntheticFunction", "name", "", "returnType", "Lorg/jetbrains/kotlin/ir/types/IrType;", "Companion", "ir.backend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class MethodsFromAnyGeneratorForLowerings {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private final BackendContext context;
    private final IrClass irClass;
    private final IrDeclarationOrigin origin;

    public MethodsFromAnyGeneratorForLowerings(BackendContext context, IrClass irClass, IrDeclarationOrigin origin) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(irClass, "irClass");
        Intrinsics.checkNotNullParameter(origin, "origin");
        this.context = context;
        this.irClass = irClass;
        this.origin = origin;
    }

    public final BackendContext getContext() {
        return this.context;
    }

    public final IrClass getIrClass() {
        return this.irClass;
    }

    public final IrDeclarationOrigin getOrigin() {
        return this.origin;
    }

    private final IrSimpleFunction addSyntheticFunction(IrClass irClass, String str, IrType irType) {
        return DeclarationBuildersKt.addFunction$default(irClass, str, irType, null, null, false, false, false, false, null, -2, -2, 508, null);
    }

    public final IrSimpleFunction createToStringMethodDeclaration() {
        IrSimpleFunction irSimpleFunctionAddSyntheticFunction = addSyntheticFunction(this.irClass, "toString", this.context.getIrBuiltIns().getStringType());
        irSimpleFunctionAddSyntheticFunction.setOverriddenSymbols(INSTANCE.collectOverridenSymbols(this.irClass, new Function1<IrFunction, Boolean>() { // from class: org.jetbrains.kotlin.backend.common.lower.MethodsFromAnyGeneratorForLowerings$createToStringMethodDeclaration$1$1
            @Override // kotlin.jvm.functions.Function1
            public final Boolean invoke(IrFunction it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return Boolean.valueOf(IrUtilsKt.isToString(it));
            }
        }));
        return irSimpleFunctionAddSyntheticFunction;
    }

    public final IrSimpleFunction createHashCodeMethodDeclaration() {
        IrSimpleFunction irSimpleFunctionAddSyntheticFunction = addSyntheticFunction(this.irClass, "hashCode", this.context.getIrBuiltIns().getIntType());
        irSimpleFunctionAddSyntheticFunction.setOverriddenSymbols(INSTANCE.collectOverridenSymbols(this.irClass, new Function1<IrFunction, Boolean>() { // from class: org.jetbrains.kotlin.backend.common.lower.MethodsFromAnyGeneratorForLowerings$createHashCodeMethodDeclaration$1$1
            @Override // kotlin.jvm.functions.Function1
            public final Boolean invoke(IrFunction it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return Boolean.valueOf(IrUtilsKt.isHashCode(it));
            }
        }));
        return irSimpleFunctionAddSyntheticFunction;
    }

    public final IrSimpleFunction createEqualsMethodDeclaration() {
        IrSimpleFunction irSimpleFunctionAddSyntheticFunction = addSyntheticFunction(this.irClass, Namer.EQUALS_METHOD_NAME, this.context.getIrBuiltIns().getBooleanType());
        irSimpleFunctionAddSyntheticFunction.setOverriddenSymbols(INSTANCE.collectOverridenSymbols(this.irClass, new Function1<IrFunction, Boolean>() { // from class: org.jetbrains.kotlin.backend.common.lower.MethodsFromAnyGeneratorForLowerings$createEqualsMethodDeclaration$1$1
            @Override // kotlin.jvm.functions.Function1
            public final Boolean invoke(IrFunction it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return Boolean.valueOf(IrUtilsKt.isEquals(it));
            }
        }));
        DeclarationBuildersKt.addValueParameter$default(irSimpleFunctionAddSyntheticFunction, "other", this.context.getIrBuiltIns().getAnyNType(), (IrDeclarationOrigin) null, 4, (Object) null);
        return irSimpleFunctionAddSyntheticFunction;
    }

    /* JADX INFO: compiled from: MethodsFromAnyGeneratorForLowerings.kt */
    @Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J$\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004*\u00020\u00062\u0012\u0010\u0007\u001a\u000e\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\n0\b¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/lower/MethodsFromAnyGeneratorForLowerings$Companion;", "", "()V", "collectOverridenSymbols", "", "Lorg/jetbrains/kotlin/ir/symbols/IrSimpleFunctionSymbol;", "Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "predicate", "Lkotlin/Function1;", "Lorg/jetbrains/kotlin/ir/declarations/IrFunction;", "", "ir.backend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final List<IrSimpleFunctionSymbol> collectOverridenSymbols(IrClass irClass, Function1<? super IrFunction, Boolean> predicate) {
            Sequence<IrSimpleFunction> functions;
            Intrinsics.checkNotNullParameter(irClass, "<this>");
            Intrinsics.checkNotNullParameter(predicate, "predicate");
            List<IrType> superTypes = irClass.getSuperTypes();
            ArrayList arrayList = new ArrayList();
            Iterator<T> it = superTypes.iterator();
            while (it.hasNext()) {
                IrClass irClass2 = IrTypesKt.getClass((IrType) it.next());
                IrSimpleFunctionSymbol symbol = null;
                if (irClass2 != null && (functions = IrUtilsKt.getFunctions(irClass2)) != null) {
                    Iterator<IrSimpleFunction> it2 = functions.iterator();
                    boolean z = false;
                    IrSimpleFunction irSimpleFunction = null;
                    while (true) {
                        if (!it2.hasNext()) {
                            if (!z) {
                                break;
                            }
                            break;
                        }
                        IrSimpleFunction next = it2.next();
                        if (predicate.invoke(next).booleanValue()) {
                            if (!z) {
                                z = true;
                                irSimpleFunction = next;
                            }
                        }
                        irSimpleFunction = null;
                        break;
                    }
                    IrSimpleFunction irSimpleFunction2 = irSimpleFunction;
                    if (irSimpleFunction2 != null) {
                        symbol = irSimpleFunction2.getSymbol();
                    }
                }
                if (symbol != null) {
                    arrayList.add(symbol);
                }
            }
            return CollectionsKt.compactIfPossible(arrayList);
        }
    }
}
