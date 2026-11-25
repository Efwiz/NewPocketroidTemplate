package org.jetbrains.kotlin.ir.backend.js.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.Sequence;
import kotlin.sequences.SequencesKt;
import kotlin.text.StringsKt;
import org.jetbrains.kotlin.backend.common.lower.LocalDeclarationsLoweringKt;
import org.jetbrains.kotlin.descriptors.ClassKind;
import org.jetbrains.kotlin.idea.KotlinFileType;
import org.jetbrains.kotlin.ir.IrBuiltIns;
import org.jetbrains.kotlin.ir.IrStatement;
import org.jetbrains.kotlin.ir.backend.js.JsIrBackendContext;
import org.jetbrains.kotlin.ir.backend.js.JsLoweredDeclarationOrigin;
import org.jetbrains.kotlin.ir.backend.js.JsStatementOrigins;
import org.jetbrains.kotlin.ir.backend.js.export.ExportModelGeneratorKt;
import org.jetbrains.kotlin.ir.backend.js.ir.JsIrBuilder;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrConstructor;
import org.jetbrains.kotlin.ir.declarations.IrDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationOrigin;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationParent;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationWithName;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationsKt;
import org.jetbrains.kotlin.ir.declarations.IrField;
import org.jetbrains.kotlin.ir.declarations.IrFile;
import org.jetbrains.kotlin.ir.declarations.IrFunction;
import org.jetbrains.kotlin.ir.declarations.IrPackageFragment;
import org.jetbrains.kotlin.ir.declarations.IrPossiblyExternalDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction;
import org.jetbrains.kotlin.ir.expressions.IrCall;
import org.jetbrains.kotlin.ir.expressions.IrExpression;
import org.jetbrains.kotlin.ir.expressions.IrFunctionAccessExpression;
import org.jetbrains.kotlin.ir.expressions.IrInlinedFunctionBlock;
import org.jetbrains.kotlin.ir.expressions.IrReturn;
import org.jetbrains.kotlin.ir.expressions.impl.IrConstImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrGetFieldImpl;
import org.jetbrains.kotlin.ir.symbols.IrClassSymbol;
import org.jetbrains.kotlin.ir.symbols.IrFunctionSymbol;
import org.jetbrains.kotlin.ir.symbols.IrReturnableBlockSymbol;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.ir.types.IrTypesKt;
import org.jetbrains.kotlin.ir.util.IrUtilsKt;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.Name;

/* JADX INFO: compiled from: IrJsUtils.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\u0086\u0001\n\u0000\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a\u001a\u0010\u0016\u001a\u0004\u0018\u00010\u00172\u0006\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\u0001H\u0002\u001a\u000e\u0010\u001b\u001a\u00020\u00142\u0006\u0010\u001c\u001a\u00020\u001d\u001a\u0014\u0010\u001e\u001a\u0004\u0018\u00010\u001f*\u00020\u001d2\u0006\u0010 \u001a\u00020\u0002\u001a\u0012\u0010!\u001a\u00020\u0017*\u00020\"2\u0006\u0010\u001a\u001a\u00020\u0001\u001a\n\u0010#\u001a\u00020\u0014*\u00020\u001d\u001a\u0012\u0010$\u001a\u00020\u0001*\u00020%2\u0006\u0010\u001c\u001a\u00020\u001d\u001a\u0014\u0010&\u001a\u00020\u0001*\u0004\u0018\u00010\n2\u0006\u0010\u001c\u001a\u00020\u001d\u001a\u0014\u0010'\u001a\u00020\u0001*\u0004\u0018\u00010\n2\u0006\u0010\u001c\u001a\u00020\u001d\u001a\u0012\u0010(\u001a\u00020\u0001*\u00020\n2\u0006\u0010\u001c\u001a\u00020\u001d\u001a\n\u0010)\u001a\u00020\u0001*\u00020\n\u001a\n\u0010)\u001a\u00020\u0001*\u00020*\u001a\n\u0010+\u001a\u00020\u0001*\u00020\n\u001a\n\u0010+\u001a\u00020\u0001*\u00020,\u001a\u0012\u0010-\u001a\u00020\u0001*\u00020.2\u0006\u0010/\u001a\u000200\u001a\u0012\u00101\u001a\u00020\u0001*\u0002022\u0006\u0010\u001c\u001a\u00020\u001d\u001a\u0012\u00103\u001a\u00020\u0014*\u00020\u00022\u0006\u0010\u001c\u001a\u00020\u001d\"\u0015\u0010\u0000\u001a\u00020\u0001*\u00020\u00028F¢\u0006\u0006\u001a\u0004\b\u0000\u0010\u0003\"\u0015\u0010\u0004\u001a\u00020\u0005*\u00020\u00068F¢\u0006\u0006\u001a\u0004\b\u0007\u0010\b\"\u0017\u0010\t\u001a\u0004\u0018\u00010\u0002*\u00020\n8F¢\u0006\u0006\u001a\u0004\b\u000b\u0010\f\"\u001d\u0010\r\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u000f0\u000e*\u00020\u00108F¢\u0006\u0006\u001a\u0004\b\u0011\u0010\u0012\"\u001d\u0010\u0013\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00140\u000e*\u00020\u00108F¢\u0006\u0006\u001a\u0004\b\u0015\u0010\u0012¨\u00064"}, d2 = {"isInstantiableEnum", "", "Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "(Lorg/jetbrains/kotlin/ir/declarations/IrClass;)Z", "nameWithoutExtension", "", "Lorg/jetbrains/kotlin/ir/declarations/IrFile;", "getNameWithoutExtension", "(Lorg/jetbrains/kotlin/ir/declarations/IrFile;)Ljava/lang/String;", "parentEnumClassOrNull", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;", "getParentEnumClassOrNull", "(Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;)Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "typeArguments", "", "Lorg/jetbrains/kotlin/ir/types/IrType;", "Lorg/jetbrains/kotlin/ir/expressions/IrFunctionAccessExpression;", "getTypeArguments", "(Lorg/jetbrains/kotlin/ir/expressions/IrFunctionAccessExpression;)Ljava/util/List;", "valueArguments", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "getValueArguments", "getKotlinOrJsQualifier", "Lorg/jetbrains/kotlin/name/FqName;", "parent", "Lorg/jetbrains/kotlin/ir/declarations/IrPackageFragment;", "shouldIncludePackage", "irEmpty", "context", "Lorg/jetbrains/kotlin/ir/backend/js/JsIrBackendContext;", "findDefaultConstructorFor", "Lorg/jetbrains/kotlin/ir/declarations/IrFunction;", "irClass", "getFqNameWithJsNameWhenAvailable", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationWithName;", "getVoid", "hasStrictSignature", "Lorg/jetbrains/kotlin/ir/declarations/IrConstructor;", "isExportedClass", "isExportedInterface", "isExportedMember", "isObjectInstanceField", "Lorg/jetbrains/kotlin/ir/declarations/IrField;", "isObjectInstanceGetter", "Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;", "isTheLastReturnStatementIn", "Lorg/jetbrains/kotlin/ir/expressions/IrReturn;", "target", "Lorg/jetbrains/kotlin/ir/symbols/IrReturnableBlockSymbol;", "isUnitInstanceFunction", "Lorg/jetbrains/kotlin/ir/symbols/IrFunctionSymbol;", "jsConstructorReference", "backend.js"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class IrJsUtilsKt {
    public static final String getNameWithoutExtension(IrFile irFile) {
        Intrinsics.checkNotNullParameter(irFile, "<this>");
        return StringsKt.substringBeforeLast$default(IrDeclarationsKt.getName(irFile), KotlinFileType.DOT_DEFAULT_EXTENSION, (String) null, 2, (Object) null);
    }

    public static final IrExpression jsConstructorReference(IrClass irClass, JsIrBackendContext context) {
        Intrinsics.checkNotNullParameter(irClass, "<this>");
        Intrinsics.checkNotNullParameter(context, "context");
        IrCall irCallBuildCall$default = JsIrBuilder.buildCall$default(JsIrBuilder.INSTANCE, context.getIntrinsics().getJsClass(), null, null, JsStatementOrigins.CLASS_REFERENCE.INSTANCE, null, 22, null);
        irCallBuildCall$default.putTypeArgument(0, IrUtilsKt.getDefaultType(irClass));
        return irCallBuildCall$default;
    }

    public static final boolean isExportedMember(IrDeclaration irDeclaration, JsIrBackendContext context) {
        Intrinsics.checkNotNullParameter(irDeclaration, "<this>");
        Intrinsics.checkNotNullParameter(context, "context");
        return IrUtilsKt.getParentClassOrNull(irDeclaration) != null && ExportModelGeneratorKt.isExported(irDeclaration, context);
    }

    public static final boolean isExportedClass(IrDeclaration irDeclaration, JsIrBackendContext context) {
        Intrinsics.checkNotNullParameter(context, "context");
        return (irDeclaration instanceof IrClass) && ((IrClass) irDeclaration).getKind() == ClassKind.CLASS && ExportModelGeneratorKt.isExported(irDeclaration, context);
    }

    public static final boolean isExportedInterface(IrDeclaration irDeclaration, JsIrBackendContext context) {
        Intrinsics.checkNotNullParameter(context, "context");
        return (irDeclaration instanceof IrClass) && ((IrClass) irDeclaration).getKind() == ClassKind.INTERFACE && ExportModelGeneratorKt.isExported(irDeclaration, context);
    }

    public static final boolean isTheLastReturnStatementIn(IrReturn irReturn, IrReturnableBlockSymbol target) {
        Intrinsics.checkNotNullParameter(irReturn, "<this>");
        Intrinsics.checkNotNullParameter(target, "target");
        IrStatement irStatement = (IrStatement) CollectionsKt.singleOrNull((List) target.getOwner().getStatements());
        if (irStatement instanceof IrInlinedFunctionBlock) {
            return CollectionsKt.lastOrNull((List) ((IrInlinedFunctionBlock) irStatement).getStatements()) == irReturn;
        }
        return CollectionsKt.lastOrNull((List) target.getOwner().getStatements()) == irReturn;
    }

    public static final FqName getFqNameWithJsNameWhenAvailable(IrDeclarationWithName irDeclarationWithName, boolean z) {
        FqName fqNameChild;
        Intrinsics.checkNotNullParameter(irDeclarationWithName, "<this>");
        Name jsNameOrKotlinName = AnnotationUtilsKt.getJsNameOrKotlinName(irDeclarationWithName);
        IrDeclarationParent parent = irDeclarationWithName.getParent();
        if (parent instanceof IrDeclarationWithName) {
            FqName fqNameChild2 = getFqNameWithJsNameWhenAvailable((IrDeclarationWithName) parent, z).child(jsNameOrKotlinName);
            Intrinsics.checkNotNullExpressionValue(fqNameChild2, "child(...)");
            return fqNameChild2;
        }
        if (!(parent instanceof IrPackageFragment)) {
            return new FqName(jsNameOrKotlinName.getIdentifier());
        }
        FqName kotlinOrJsQualifier = getKotlinOrJsQualifier((IrPackageFragment) parent, z);
        return (kotlinOrJsQualifier == null || (fqNameChild = kotlinOrJsQualifier.child(jsNameOrKotlinName)) == null) ? new FqName(jsNameOrKotlinName.getIdentifier()) : fqNameChild;
    }

    public static final boolean hasStrictSignature(IrConstructor irConstructor, JsIrBackendContext context) {
        Intrinsics.checkNotNullParameter(irConstructor, "<this>");
        Intrinsics.checkNotNullParameter(context, "context");
        IrBuiltIns irBuiltIns = context.getIrBuiltIns();
        List listPlus = CollectionsKt.plus((Collection<? extends IrClassSymbol>) irBuiltIns.getPrimitiveTypesToPrimitiveArrays().values(), irBuiltIns.getStringClass());
        IrClass parentAsClass = IrUtilsKt.getParentAsClass(irConstructor);
        return parentAsClass.getIsExternal() || parentAsClass.getIsExpect() || IrUtilsKt.isAnnotationClass(parentAsClass) || context.getInlineClassesUtils().isClassInlineLike(parentAsClass) || listPlus.contains(parentAsClass.getSymbol());
    }

    private static final FqName getKotlinOrJsQualifier(IrPackageFragment irPackageFragment, boolean z) {
        String jsQualifier;
        IrFile irFile = irPackageFragment instanceof IrFile ? (IrFile) irPackageFragment : null;
        if (irFile != null && (jsQualifier = AnnotationUtilsKt.getJsQualifier(irFile)) != null) {
            return new FqName(jsQualifier);
        }
        FqName packageFqName = irPackageFragment.getPackageFqName();
        if (z) {
            return packageFqName;
        }
        return null;
    }

    public static final List<IrType> getTypeArguments(IrFunctionAccessExpression irFunctionAccessExpression) {
        Intrinsics.checkNotNullParameter(irFunctionAccessExpression, "<this>");
        int typeArgumentsCount = irFunctionAccessExpression.getTypeArgumentsCount();
        ArrayList arrayList = new ArrayList(typeArgumentsCount);
        for (int i = 0; i < typeArgumentsCount; i++) {
            arrayList.add(irFunctionAccessExpression.getTypeArgument(i));
        }
        return arrayList;
    }

    public static final List<IrExpression> getValueArguments(IrFunctionAccessExpression irFunctionAccessExpression) {
        Intrinsics.checkNotNullParameter(irFunctionAccessExpression, "<this>");
        int valueArgumentsCount = irFunctionAccessExpression.getValueArgumentsCount();
        ArrayList arrayList = new ArrayList(valueArgumentsCount);
        for (int i = 0; i < valueArgumentsCount; i++) {
            arrayList.add(irFunctionAccessExpression.getValueArgument(i));
        }
        return arrayList;
    }

    public static final boolean isInstantiableEnum(IrClass irClass) {
        Intrinsics.checkNotNullParameter(irClass, "<this>");
        return (!IrUtilsKt.isEnumClass(irClass) || irClass.getIsExpect() || IrUtilsKt.isEffectivelyExternal((IrPossiblyExternalDeclaration) irClass)) ? false : true;
    }

    public static final IrClass getParentEnumClassOrNull(IrDeclaration irDeclaration) {
        Object next;
        Intrinsics.checkNotNullParameter(irDeclaration, "<this>");
        Sequence sequenceFilter = SequencesKt.filter(LocalDeclarationsLoweringKt.getParents(irDeclaration), new Function1<Object, Boolean>() { // from class: org.jetbrains.kotlin.ir.backend.js.utils.IrJsUtilsKt$special$$inlined$filterIsInstance$1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // kotlin.jvm.functions.Function1
            public final Boolean invoke(Object obj) {
                return Boolean.valueOf(obj instanceof IrClass);
            }
        });
        Intrinsics.checkNotNull(sequenceFilter, "null cannot be cast to non-null type kotlin.sequences.Sequence<R of kotlin.sequences.SequencesKt___SequencesKt.filterIsInstance>");
        Iterator it = sequenceFilter.iterator();
        while (it.hasNext()) {
            next = it.next();
            if (isInstantiableEnum((IrClass) next)) {
                return (IrClass) next;
            }
        }
        next = null;
        return (IrClass) next;
    }

    public static final boolean isUnitInstanceFunction(IrFunctionSymbol irFunctionSymbol, JsIrBackendContext context) {
        Intrinsics.checkNotNullParameter(irFunctionSymbol, "<this>");
        Intrinsics.checkNotNullParameter(context, "context");
        return irFunctionSymbol.getOwner().getOrigin() == JsLoweredDeclarationOrigin.OBJECT_GET_INSTANCE_FUNCTION.INSTANCE && IrTypesKt.getClassifierOrNull(irFunctionSymbol.getOwner().getReturnType()) == context.getIrBuiltIns().getUnitClass();
    }

    public static final IrExpression getVoid(JsIrBackendContext jsIrBackendContext) {
        Intrinsics.checkNotNullParameter(jsIrBackendContext, "<this>");
        IrField backingField = jsIrBackendContext.getIntrinsics().getVoid().getOwner().getBackingField();
        if (backingField != null) {
            return new IrGetFieldImpl(-1, -1, backingField.getSymbol(), jsIrBackendContext.getIrBuiltIns().getNothingNType(), null, null, 48, null);
        }
        return IrConstImpl.INSTANCE.constNull(-1, -1, jsIrBackendContext.getIrBuiltIns().getNothingNType());
    }

    public static final IrExpression irEmpty(JsIrBackendContext context) {
        Intrinsics.checkNotNullParameter(context, "context");
        return JsIrBuilder.INSTANCE.buildComposite(context.getDynamicType(), CollectionsKt.emptyList());
    }

    public static final boolean isObjectInstanceGetter(IrDeclaration irDeclaration) {
        Intrinsics.checkNotNullParameter(irDeclaration, "<this>");
        return (irDeclaration instanceof IrSimpleFunction) && isObjectInstanceGetter((IrSimpleFunction) irDeclaration);
    }

    public static final boolean isObjectInstanceGetter(IrSimpleFunction irSimpleFunction) {
        Intrinsics.checkNotNullParameter(irSimpleFunction, "<this>");
        return Intrinsics.areEqual(irSimpleFunction.getOrigin(), JsLoweredDeclarationOrigin.OBJECT_GET_INSTANCE_FUNCTION.INSTANCE);
    }

    public static final boolean isObjectInstanceField(IrDeclaration irDeclaration) {
        Intrinsics.checkNotNullParameter(irDeclaration, "<this>");
        return (irDeclaration instanceof IrField) && isObjectInstanceField((IrField) irDeclaration);
    }

    public static final boolean isObjectInstanceField(IrField irField) {
        Intrinsics.checkNotNullParameter(irField, "<this>");
        return Intrinsics.areEqual(irField.getOrigin(), IrDeclarationOrigin.FIELD_FOR_OBJECT_INSTANCE.INSTANCE);
    }

    public static final IrFunction findDefaultConstructorFor(JsIrBackendContext jsIrBackendContext, IrClass irClass) {
        Intrinsics.checkNotNullParameter(jsIrBackendContext, "<this>");
        Intrinsics.checkNotNullParameter(irClass, "irClass");
        IrConstructor irConstructor = jsIrBackendContext.getMapping().getClassToItsDefaultConstructor().get(irClass);
        if (irConstructor == null) {
            return null;
        }
        IrSimpleFunction irSimpleFunction = jsIrBackendContext.getMapping().getSecondaryConstructorToFactory().get(irConstructor);
        return irSimpleFunction != null ? irSimpleFunction : irConstructor;
    }
}
