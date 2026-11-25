package org.jetbrains.kotlin.ir.backend.js.utils;

import java.util.List;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import net.bytebuddy.description.type.TypeDescription;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.ir.backend.js.JsIrBackendContext;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrConstructor;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationWithName;
import org.jetbrains.kotlin.ir.declarations.IrPossiblyExternalDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrSymbolOwner;
import org.jetbrains.kotlin.ir.symbols.IrClassSymbol;
import org.jetbrains.kotlin.ir.symbols.IrClassifierSymbol;
import org.jetbrains.kotlin.ir.symbols.IrScriptSymbol;
import org.jetbrains.kotlin.ir.symbols.IrTypeParameterSymbol;
import org.jetbrains.kotlin.ir.types.IrDynamicType;
import org.jetbrains.kotlin.ir.types.IrErrorType;
import org.jetbrains.kotlin.ir.types.IrSimpleType;
import org.jetbrains.kotlin.ir.types.IrStarProjection;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.ir.types.IrTypeArgument;
import org.jetbrains.kotlin.ir.types.IrTypeProjection;
import org.jetbrains.kotlin.ir.types.IrTypesKt;
import org.jetbrains.kotlin.ir.types.SimpleTypeNullability;
import org.jetbrains.kotlin.ir.util.IrUtilsKt;
import org.jetbrains.kotlin.js.backend.ast.JsExpression;
import org.jetbrains.kotlin.js.backend.ast.JsInvocation;
import org.jetbrains.kotlin.js.backend.ast.JsNameRef;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.types.Variance;

/* JADX INFO: compiled from: IrTypeUtils.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u00002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a\u0013\u0010\u0000\u001a\u0004\u0018\u00010\u00012\u0006\u0010\u0002\u001a\u00020\u0003H\u0086\u0010\u001a\u0014\u0010\u0004\u001a\u00020\u0005*\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0002\u001a\u0012\u0010\u0004\u001a\u00020\u0005*\u00020\u00032\u0006\u0010\u0007\u001a\u00020\b\u001a\u0014\u0010\u0004\u001a\u00020\u0005*\u00020\t2\u0006\u0010\u0007\u001a\u00020\bH\u0002\u001a\u0012\u0010\n\u001a\u00020\u000b*\u00020\u00012\u0006\u0010\u0007\u001a\u00020\f\u001a\u0012\u0010\n\u001a\u00020\u000b*\u00020\u00032\u0006\u0010\u0007\u001a\u00020\f\u001a\u0012\u0010\r\u001a\u00020\u000b*\u00020\u000e2\u0006\u0010\u0007\u001a\u00020\f¨\u0006\u000f"}, d2 = {"erase", "Lorg/jetbrains/kotlin/ir/declarations/IrClass;", ModuleXmlParser.TYPE, "Lorg/jetbrains/kotlin/ir/types/IrType;", "asString", "", "Lorg/jetbrains/kotlin/ir/symbols/IrClassifierSymbol;", "context", "Lorg/jetbrains/kotlin/ir/backend/js/JsIrBackendContext;", "Lorg/jetbrains/kotlin/ir/types/IrTypeArgument;", "getClassRef", "Lorg/jetbrains/kotlin/js/backend/ast/JsExpression;", "Lorg/jetbrains/kotlin/ir/backend/js/utils/JsStaticContext;", "getConstructorRef", "Lorg/jetbrains/kotlin/ir/declarations/IrConstructor;", "backend.js"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class IrTypeUtilsKt {

    /* JADX INFO: compiled from: IrTypeUtils.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[SimpleTypeNullability.values().length];
            try {
                iArr[SimpleTypeNullability.MARKED_NULLABLE.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[SimpleTypeNullability.NOT_SPECIFIED.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[SimpleTypeNullability.DEFINITELY_NOT_NULL.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    /* JADX WARN: Code duplicated, block: B:21:0x0059  */
    public static final String asString(IrType irType, final JsIrBackendContext context) {
        String str;
        Intrinsics.checkNotNullParameter(irType, "<this>");
        Intrinsics.checkNotNullParameter(context, "context");
        if (irType instanceof IrErrorType) {
            return "$ErrorType$";
        }
        if (irType instanceof IrDynamicType) {
            return "dynamic";
        }
        if (irType instanceof IrSimpleType) {
            IrSimpleType irSimpleType = (IrSimpleType) irType;
            StringBuilder sbAppend = new StringBuilder().append(asString(irSimpleType.getClassifier(), context));
            int i = WhenMappings.$EnumSwitchMapping$0[irSimpleType.getNullability().ordinal()];
            if (i == 1) {
                str = TypeDescription.Generic.OfWildcardType.SYMBOL;
            } else if (i == 2) {
                str = "";
            } else {
                if (i != 3) {
                    throw new NoWhenBranchMatchedException();
                }
                if (irSimpleType.getClassifier() instanceof IrTypeParameterSymbol) {
                    str = " & Any";
                } else {
                    str = "";
                }
            }
            StringBuilder sbAppend2 = sbAppend.append(str);
            List<IrTypeArgument> arguments = irSimpleType.getArguments();
            String strJoinToString$default = !arguments.isEmpty() ? CollectionsKt.joinToString$default(arguments, ",", "<", ">", 0, null, new Function1<IrTypeArgument, CharSequence>() { // from class: org.jetbrains.kotlin.ir.backend.js.utils.IrTypeUtilsKt$asString$1$1
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public final CharSequence invoke(IrTypeArgument it) {
                    Intrinsics.checkNotNullParameter(it, "it");
                    return IrTypeUtilsKt.asString(it, context);
                }
            }, 24, null) : null;
            return sbAppend2.append(strJoinToString$default != null ? strJoinToString$default : "").toString();
        }
        throw new IllegalStateException(("Unexpected kind of IrType: " + irType.getClass().getTypeName()).toString());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String asString(IrTypeArgument irTypeArgument, JsIrBackendContext jsIrBackendContext) {
        if (irTypeArgument instanceof IrStarProjection) {
            return "*";
        }
        if (!(irTypeArgument instanceof IrTypeProjection)) {
            throw new NoWhenBranchMatchedException();
        }
        IrTypeProjection irTypeProjection = (IrTypeProjection) irTypeArgument;
        return irTypeProjection.getVariance().getLabel() + (irTypeProjection.getVariance() != Variance.INVARIANT ? " " : "") + asString(irTypeProjection.getType(), jsIrBackendContext);
    }

    private static final String asString(IrClassifierSymbol irClassifierSymbol, JsIrBackendContext jsIrBackendContext) {
        String strAsString;
        if (irClassifierSymbol instanceof IrTypeParameterSymbol) {
            String strAsString2 = ((IrTypeParameterSymbol) irClassifierSymbol).getOwner().getName().asString();
            Intrinsics.checkNotNullExpressionValue(strAsString2, "asString(...)");
            return strAsString2;
        }
        if (!(irClassifierSymbol instanceof IrScriptSymbol)) {
            if (!(irClassifierSymbol instanceof IrClassSymbol)) {
                throw new NoWhenBranchMatchedException();
            }
            IrClassSymbol irClassSymbol = (IrClassSymbol) irClassifierSymbol;
            String str = jsIrBackendContext.getClassToItsId().get(irClassSymbol.getOwner());
            if (str == null) {
                String str2 = jsIrBackendContext.getLocalClassNames().get(irClassSymbol.getOwner());
                if (str2 == null) {
                    FqName fqNameWhenAvailable = IrUtilsKt.getFqNameWhenAvailable(irClassSymbol.getOwner());
                    Intrinsics.checkNotNull(fqNameWhenAvailable);
                    strAsString = fqNameWhenAvailable.asString();
                } else {
                    strAsString = str2;
                }
            } else {
                strAsString = str;
            }
            Intrinsics.checkNotNull(strAsString);
            return strAsString;
        }
        throw new IllegalArgumentException("Unexpected kind of " + IrClassifierSymbol.class.getTypeName() + ": " + irClassifierSymbol);
    }

    public static final IrClass erase(IrType type) {
        Intrinsics.checkNotNullParameter(type, "type");
        while (true) {
            IrClassifierSymbol classifierOrFail = IrTypesKt.getClassifierOrFail(type);
            if (classifierOrFail instanceof IrClassSymbol) {
                return ((IrClassSymbol) classifierOrFail).getOwner();
            }
            if (!(classifierOrFail instanceof IrTypeParameterSymbol)) {
                if (classifierOrFail instanceof IrScriptSymbol) {
                    return null;
                }
                throw new NoWhenBranchMatchedException();
            }
            type = (IrType) CollectionsKt.first((List) ((IrTypeParameterSymbol) classifierOrFail).getOwner().getSuperTypes());
        }
    }

    public static final JsExpression getClassRef(IrType irType, JsStaticContext context) {
        Intrinsics.checkNotNullParameter(irType, "<this>");
        Intrinsics.checkNotNullParameter(context, "context");
        IrSymbolOwner owner = IrTypesKt.getClassifierOrFail(irType).getOwner();
        if (owner instanceof IrClass) {
            return getClassRef((IrClass) owner, context);
        }
        Intrinsics.checkNotNull(owner, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.declarations.IrDeclarationWithName");
        JsNameRef jsNameRefMakeRef = context.getNameForStaticDeclaration((IrDeclarationWithName) owner).makeRef();
        Intrinsics.checkNotNullExpressionValue(jsNameRefMakeRef, "makeRef(...)");
        return jsNameRefMakeRef;
    }

    public static final JsExpression getClassRef(IrClass irClass, JsStaticContext context) {
        Intrinsics.checkNotNullParameter(irClass, "<this>");
        Intrinsics.checkNotNullParameter(context, "context");
        if (IrUtilsKt.isEffectivelyExternal((IrPossiblyExternalDeclaration) irClass)) {
            return context.getRefForExternalClass(irClass);
        }
        JsExpression jsExpressionMakeRef = context.getNameForClass(irClass).makeRef();
        if (context.isPerFile()) {
            jsExpressionMakeRef = new JsInvocation(jsExpressionMakeRef, new JsExpression[0]);
        }
        return jsExpressionMakeRef;
    }

    public static final JsExpression getConstructorRef(IrConstructor irConstructor, JsStaticContext context) {
        Intrinsics.checkNotNullParameter(irConstructor, "<this>");
        Intrinsics.checkNotNullParameter(context, "context");
        JsInvocation jsInvocationMakeRef = context.getNameForConstructor(irConstructor).makeRef();
        if (context.isPerFile() && !IrUtilsKt.isEffectivelyExternal((IrPossiblyExternalDeclaration) irConstructor)) {
            jsInvocationMakeRef = new JsInvocation(jsInvocationMakeRef, new JsExpression[0]);
        }
        return jsInvocationMakeRef;
    }
}
