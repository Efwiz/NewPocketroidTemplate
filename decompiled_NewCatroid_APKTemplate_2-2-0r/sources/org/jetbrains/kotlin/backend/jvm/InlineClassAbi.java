package org.jetbrains.kotlin.backend.jvm;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.backend.jvm.ir.JvmIrTypeUtilsKt;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.codegen.state.InfoForMangling;
import org.jetbrains.kotlin.codegen.state.InlineClassManglingUtilsKt;
import org.jetbrains.kotlin.descriptors.InlineClassRepresentation;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrConstructor;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationOrigin;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationParent;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationsKt;
import org.jetbrains.kotlin.ir.declarations.IrFunction;
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction;
import org.jetbrains.kotlin.ir.declarations.IrValueParameter;
import org.jetbrains.kotlin.ir.expressions.IrStatementOriginImpl;
import org.jetbrains.kotlin.ir.symbols.IrClassSymbol;
import org.jetbrains.kotlin.ir.symbols.IrPropertySymbol;
import org.jetbrains.kotlin.ir.types.IrSimpleType;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.ir.types.IrTypePredicatesKt;
import org.jetbrains.kotlin.ir.types.IrTypeUtilsKt;
import org.jetbrains.kotlin.ir.types.IrTypesKt;
import org.jetbrains.kotlin.ir.util.AdditionalIrUtilsKt;
import org.jetbrains.kotlin.ir.util.IrUtilsKt;
import org.jetbrains.kotlin.load.java.JvmAbi;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.FqNameUnsafe;
import org.jetbrains.kotlin.name.Name;

/* JADX INFO: compiled from: InlineClassAbi.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000@\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\bÆ\u0002\u0018\u00002\u00020\u0001:\u0001\u001bB\u0007\b\u0002¢\u0006\u0002\u0010\u0002J2\u0010\b\u001a\u0004\u0018\u00010\t2\u0006\u0010\n\u001a\u00020\u000b2\f\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u000e0\r2\b\u0010\u000f\u001a\u0004\u0018\u00010\u000e2\b\b\u0002\u0010\u0010\u001a\u00020\u000bJ \u0010\b\u001a\u0004\u0018\u00010\t2\u0006\u0010\u0011\u001a\u00020\u00052\u0006\u0010\u0012\u001a\u00020\u000b2\u0006\u0010\n\u001a\u00020\u000bJ&\u0010\u0013\u001a\u00020\u00042\u0006\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0011\u001a\u00020\u00052\u0006\u0010\u0012\u001a\u00020\u000b2\u0006\u0010\n\u001a\u00020\u000bJ\u0010\u0010\u0016\u001a\u0004\u0018\u00010\u000e2\u0006\u0010\u0017\u001a\u00020\u000eJ\f\u0010\u0018\u001a\u00020\u0019*\u00020\u000eH\u0002J\u0014\u0010\u001a\u001a\u00020\u000b*\u00020\u00052\u0006\u0010\u0014\u001a\u00020\u0015H\u0002R\u0018\u0010\u0003\u001a\u00020\u0004*\u00020\u00058BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u0006\u0010\u0007¨\u0006\u001c"}, d2 = {"Lorg/jetbrains/kotlin/backend/jvm/InlineClassAbi;", "", "()V", "propertyName", "Lorg/jetbrains/kotlin/name/Name;", "Lorg/jetbrains/kotlin/ir/declarations/IrFunction;", "getPropertyName", "(Lorg/jetbrains/kotlin/ir/declarations/IrFunction;)Lorg/jetbrains/kotlin/name/Name;", "hashSuffix", "", "useOldMangleRules", "", "valueParameters", "", "Lorg/jetbrains/kotlin/ir/types/IrType;", "returnType", "addContinuation", "irFunction", "mangleReturnTypes", "mangledNameFor", "context", "Lorg/jetbrains/kotlin/backend/jvm/JvmBackendContext;", "unboxType", ModuleXmlParser.TYPE, "asInfoForMangling", "Lorg/jetbrains/kotlin/codegen/state/InfoForMangling;", "isAlreadyMangledMfvcFunction", "UNMANGLED_FUNCTION_REFERENCE", "backend.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class InlineClassAbi {
    public static final InlineClassAbi INSTANCE = new InlineClassAbi();

    private InlineClassAbi() {
    }

    /* JADX INFO: compiled from: InlineClassAbi.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/backend/jvm/InlineClassAbi$UNMANGLED_FUNCTION_REFERENCE;", "Lorg/jetbrains/kotlin/ir/expressions/IrStatementOriginImpl;", "()V", "backend.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class UNMANGLED_FUNCTION_REFERENCE extends IrStatementOriginImpl {
        public static final UNMANGLED_FUNCTION_REFERENCE INSTANCE = new UNMANGLED_FUNCTION_REFERENCE();

        private UNMANGLED_FUNCTION_REFERENCE() {
            super("UNMANGLED_FUNCTION_REFERENCE");
        }
    }

    public final IrType unboxType(IrType type) {
        IrClass owner;
        InlineClassRepresentation<IrSimpleType> inlineClassRepresentation;
        Intrinsics.checkNotNullParameter(type, "type");
        IrClassSymbol classOrNull = IrTypesKt.getClassOrNull(type);
        if (classOrNull == null || (owner = classOrNull.getOwner()) == null || (inlineClassRepresentation = IrDeclarationsKt.getInlineClassRepresentation(owner)) == null) {
            return null;
        }
        IrSimpleType irSimpleTypeUnboxInlineClass = InlineClassAbiKt.unboxInlineClass((IrType) inlineClassRepresentation.getUnderlyingType());
        if (!IrTypeUtilsKt.isNullable(irSimpleTypeUnboxInlineClass) && org.jetbrains.kotlin.ir.util.IrTypeUtilsKt.isTypeParameter(irSimpleTypeUnboxInlineClass)) {
            irSimpleTypeUnboxInlineClass = IrUtilsKt.getDefaultType(JvmIrTypeUtilsKt.getErasedUpperBound(irSimpleTypeUnboxInlineClass));
        }
        if (!IrTypeUtilsKt.isNullable(type)) {
            return irSimpleTypeUnboxInlineClass;
        }
        if (!IrTypeUtilsKt.isNullable(irSimpleTypeUnboxInlineClass) && !IrTypePredicatesKt.isPrimitiveType$default(irSimpleTypeUnboxInlineClass, false, 1, null)) {
            return IrTypesKt.makeNullable(irSimpleTypeUnboxInlineClass);
        }
        return null;
    }

    public final Name mangledNameFor(JvmBackendContext context, IrFunction irFunction, boolean mangleReturnTypes, boolean useOldMangleRules) {
        String strAsString;
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(irFunction, "irFunction");
        if (irFunction instanceof IrConstructor) {
            AdditionalIrUtilsKt.getConstructedClass((IrConstructor) irFunction).getIsValue();
            Name nameIdentifier = Name.identifier("constructor-impl");
            Intrinsics.checkNotNullExpressionValue(nameIdentifier, "identifier(...)");
            return nameIdentifier;
        }
        if (isAlreadyMangledMfvcFunction(irFunction, context)) {
            return irFunction.getName();
        }
        String strHashSuffix = hashSuffix(irFunction, mangleReturnTypes, useOldMangleRules);
        if (strHashSuffix == null) {
            IrDeclarationParent parent = irFunction.getParent();
            IrClass irClass = parent instanceof IrClass ? (IrClass) parent : null;
            if (irClass == null || !irClass.getIsValue() || Intrinsics.areEqual(irFunction.getOrigin(), IrDeclarationOrigin.IR_BUILTINS_STUB.INSTANCE)) {
                return irFunction.getName();
            }
        }
        IrFunction irFunction2 = irFunction;
        if (AdditionalIrUtilsKt.isGetter(irFunction2)) {
            String strAsString2 = getPropertyName(irFunction).asString();
            Intrinsics.checkNotNullExpressionValue(strAsString2, "asString(...)");
            strAsString = JvmAbi.getterName(strAsString2);
        } else if (AdditionalIrUtilsKt.isSetter(irFunction2)) {
            String strAsString3 = getPropertyName(irFunction).asString();
            Intrinsics.checkNotNullExpressionValue(strAsString3, "asString(...)");
            strAsString = JvmAbi.setterName(strAsString3);
        } else {
            if (irFunction.getName().isSpecial()) {
                throw new IllegalStateException(("Unhandled special name in mangledNameFor: " + irFunction.getName()).toString());
            }
            strAsString = irFunction.getName().asString();
            Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
        }
        StringBuilder sbAppend = new StringBuilder().append(strAsString).append('-');
        if (strHashSuffix == null) {
            strHashSuffix = "impl";
        }
        Name nameIdentifier2 = Name.identifier(sbAppend.append(strHashSuffix).toString());
        Intrinsics.checkNotNullExpressionValue(nameIdentifier2, "identifier(...)");
        return nameIdentifier2;
    }

    private final boolean isAlreadyMangledMfvcFunction(IrFunction irFunction, JvmBackendContext jvmBackendContext) {
        List<MemoizedMultiFieldValueClassReplacements.RemappedParameter> list = jvmBackendContext.getMultiFieldValueClassReplacements().getBindingNewFunctionToParameterTemplateStructure().get(irFunction);
        if (list == null) {
            return false;
        }
        List<MemoizedMultiFieldValueClassReplacements.RemappedParameter> list2 = list;
        if ((list2 instanceof Collection) && list2.isEmpty()) {
            return false;
        }
        Iterator<T> it = list2.iterator();
        while (it.hasNext()) {
            if (((MemoizedMultiFieldValueClassReplacements.RemappedParameter) it.next()) instanceof MemoizedMultiFieldValueClassReplacements.RemappedParameter.MultiFieldValueClassMapping) {
                return true;
            }
        }
        return false;
    }

    public final String hashSuffix(IrFunction irFunction, boolean mangleReturnTypes, boolean useOldMangleRules) {
        Intrinsics.checkNotNullParameter(irFunction, "irFunction");
        List<IrValueParameter> fullValueParameterList = InlineClassAbiKt.getFullValueParameterList(irFunction);
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(fullValueParameterList, 10));
        Iterator<T> it = fullValueParameterList.iterator();
        while (it.hasNext()) {
            arrayList.add(((IrValueParameter) it.next()).getType());
        }
        ArrayList arrayList2 = arrayList;
        IrType returnType = irFunction.getReturnType();
        if (!mangleReturnTypes || !InlineClassAbiKt.getHasMangledReturnType(irFunction)) {
            returnType = null;
        }
        return hashSuffix(useOldMangleRules, arrayList2, returnType, AdditionalIrUtilsKt.isSuspend(irFunction));
    }

    public static /* synthetic */ String hashSuffix$default(InlineClassAbi inlineClassAbi, boolean z, List list, IrType irType, boolean z2, int i, Object obj) {
        if ((i & 8) != 0) {
            z2 = false;
        }
        return inlineClassAbi.hashSuffix(z, list, irType, z2);
    }

    public final String hashSuffix(boolean useOldMangleRules, List<? extends IrType> valueParameters, IrType returnType, boolean addContinuation) {
        boolean z;
        ArrayList arrayListPlus;
        Intrinsics.checkNotNullParameter(valueParameters, "valueParameters");
        List<? extends IrType> list = valueParameters;
        if (!(list instanceof Collection) || !list.isEmpty()) {
            Iterator<T> it = list.iterator();
            while (true) {
                if (!it.hasNext()) {
                    z = false;
                    break;
                }
                if (InlineClassAbiKt.getRequiresMangling$default((IrType) it.next(), false, false, 3, null)) {
                    z = true;
                    break;
                }
            }
        } else {
            z = false;
            break;
        }
        if (addContinuation) {
            ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
            Iterator<T> it2 = list.iterator();
            while (it2.hasNext()) {
                arrayList.add(INSTANCE.asInfoForMangling((IrType) it2.next()));
            }
            arrayListPlus = CollectionsKt.plus((Collection<? extends InfoForMangling>) arrayList, new InfoForMangling(new FqNameUnsafe("kotlin.coroutines.Continuation"), false, false));
        } else {
            ArrayList arrayList2 = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
            Iterator<T> it3 = list.iterator();
            while (it3.hasNext()) {
                arrayList2.add(INSTANCE.asInfoForMangling((IrType) it3.next()));
            }
            arrayListPlus = arrayList2;
        }
        String strCollectFunctionSignatureForManglingSuffix = InlineClassManglingUtilsKt.collectFunctionSignatureForManglingSuffix(useOldMangleRules, z, arrayListPlus, returnType != null ? asInfoForMangling(returnType) : null);
        if (strCollectFunctionSignatureForManglingSuffix != null) {
            return InlineClassManglingUtilsKt.md5base64(strCollectFunctionSignatureForManglingSuffix);
        }
        return null;
    }

    private final InfoForMangling asInfoForMangling(IrType irType) {
        FqName fqNameWhenAvailable = IrUtilsKt.getFqNameWhenAvailable(JvmIrTypeUtilsKt.getErasedUpperBound(irType));
        Intrinsics.checkNotNull(fqNameWhenAvailable);
        FqNameUnsafe unsafe = fqNameWhenAvailable.toUnsafe();
        Intrinsics.checkNotNullExpressionValue(unsafe, "toUnsafe(...)");
        return new InfoForMangling(unsafe, JvmIrTypeUtilsKt.isValueClassType(irType), IrTypeUtilsKt.isNullable(irType));
    }

    private final Name getPropertyName(IrFunction irFunction) {
        Intrinsics.checkNotNull(irFunction, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.declarations.IrSimpleFunction");
        IrPropertySymbol correspondingPropertySymbol = ((IrSimpleFunction) irFunction).getCorrespondingPropertySymbol();
        Intrinsics.checkNotNull(correspondingPropertySymbol);
        return correspondingPropertySymbol.getOwner().getName();
    }
}
