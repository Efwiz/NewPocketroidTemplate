package org.jetbrains.kotlin.ir.backend.js.utils;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.builtins.StandardNames;
import org.jetbrains.kotlin.ir.declarations.IrAnnotationContainer;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationWithName;
import org.jetbrains.kotlin.ir.declarations.IrFile;
import org.jetbrains.kotlin.ir.declarations.IrOverridableDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrSymbolOwner;
import org.jetbrains.kotlin.ir.expressions.IrClassReference;
import org.jetbrains.kotlin.ir.expressions.IrConst;
import org.jetbrains.kotlin.ir.expressions.IrConstructorCall;
import org.jetbrains.kotlin.ir.expressions.IrExpression;
import org.jetbrains.kotlin.ir.symbols.IrClassSymbol;
import org.jetbrains.kotlin.ir.symbols.IrClassifierSymbol;
import org.jetbrains.kotlin.ir.symbols.IrSymbol;
import org.jetbrains.kotlin.ir.util.AdditionalIrUtilsKt;
import org.jetbrains.kotlin.ir.util.IrUtilsKt;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.Name;

/* JADX INFO: compiled from: AnnotationUtils.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000@\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u000b\u001a\f\u0010\u0006\u001a\u0004\u0018\u00010\u0004*\u00020\u0007\u001a\u0012\u0010\b\u001a\u0004\u0018\u00010\t*\u0006\u0012\u0002\b\u00030\nH\u0002\u001a\f\u0010\u000b\u001a\u0004\u0018\u00010\t*\u00020\f\u001a\f\u0010\r\u001a\u0004\u0018\u00010\t*\u00020\u000e\u001a\f\u0010\u000f\u001a\u0004\u0018\u00010\t*\u00020\f\u001a\f\u0010\u0010\u001a\u0004\u0018\u00010\t*\u00020\f\u001a\f\u0010\u0011\u001a\u0004\u0018\u00010\t*\u00020\u0012\u001a\n\u0010\u0013\u001a\u00020\u0014*\u00020\u0012\u001a\f\u0010\u0015\u001a\u0004\u0018\u00010\t*\u00020\f\u001a\n\u0010\u0016\u001a\u00020\t*\u00020\u0007\u001a\n\u0010\u0017\u001a\u00020\u0003*\u00020\f\u001a\n\u0010\u0018\u001a\u00020\u0003*\u00020\f\u001a\n\u0010\u0019\u001a\u00020\u0003*\u00020\f\u001a\n\u0010\u001a\u001a\u00020\u0003*\u00020\f\u001a\n\u0010\u001b\u001a\u00020\u0003*\u00020\f\u001a\n\u0010\u001c\u001a\u00020\u0003*\u00020\f\u001a\n\u0010\u001d\u001a\u00020\u0003*\u00020\f\u001a\n\u0010\u001e\u001a\u00020\u0003*\u00020\f\"\u000e\u0010\u0000\u001a\u00020\u0001X\u0082\u0004¢\u0006\u0002\n\u0000\"\u0015\u0010\u0002\u001a\u00020\u0003*\u00020\u00048F¢\u0006\u0006\u001a\u0004\b\u0002\u0010\u0005¨\u0006\u001f"}, d2 = {"associatedObjectKeyAnnotationFqName", "Lorg/jetbrains/kotlin/name/FqName;", "isAssociatedObjectAnnotatedAnnotation", "", "Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "(Lorg/jetbrains/kotlin/ir/declarations/IrClass;)Z", "associatedObject", "Lorg/jetbrains/kotlin/ir/expressions/IrConstructorCall;", "dfsOverridableJsNameOrNull", "", "Lorg/jetbrains/kotlin/ir/declarations/IrOverridableDeclaration;", "getDeprecated", "Lorg/jetbrains/kotlin/ir/declarations/IrAnnotationContainer;", "getJsFileName", "Lorg/jetbrains/kotlin/ir/declarations/IrFile;", "getJsModule", "getJsName", "getJsNameForOverriddenDeclaration", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationWithName;", "getJsNameOrKotlinName", "Lorg/jetbrains/kotlin/name/Name;", "getJsQualifier", "getSingleConstStringArgument", "hasJsPolyfill", "isJsExport", "isJsExportIgnore", "isJsImplicitExport", "isJsNativeGetter", "isJsNativeInvoke", "isJsNativeSetter", "isJsNonModule", "backend.js"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class AnnotationUtilsKt {
    private static final FqName associatedObjectKeyAnnotationFqName = new FqName("kotlin.reflect.AssociatedObjectKey");

    public static final String getSingleConstStringArgument(IrConstructorCall irConstructorCall) {
        Intrinsics.checkNotNullParameter(irConstructorCall, "<this>");
        IrExpression valueArgument = irConstructorCall.getValueArgument(0);
        Intrinsics.checkNotNull(valueArgument, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.expressions.IrConst<kotlin.String>");
        return (String) ((IrConst) valueArgument).getValue();
    }

    public static final String getJsModule(IrAnnotationContainer irAnnotationContainer) {
        Intrinsics.checkNotNullParameter(irAnnotationContainer, "<this>");
        IrConstructorCall annotation = IrUtilsKt.getAnnotation(irAnnotationContainer, JsAnnotations.INSTANCE.getJsModuleFqn());
        if (annotation != null) {
            return getSingleConstStringArgument(annotation);
        }
        return null;
    }

    public static final boolean isJsNonModule(IrAnnotationContainer irAnnotationContainer) {
        Intrinsics.checkNotNullParameter(irAnnotationContainer, "<this>");
        return IrUtilsKt.hasAnnotation(irAnnotationContainer, JsAnnotations.INSTANCE.getJsNonModuleFqn());
    }

    public static final String getJsQualifier(IrAnnotationContainer irAnnotationContainer) {
        Intrinsics.checkNotNullParameter(irAnnotationContainer, "<this>");
        IrConstructorCall annotation = IrUtilsKt.getAnnotation(irAnnotationContainer, JsAnnotations.INSTANCE.getJsQualifierFqn());
        if (annotation != null) {
            return getSingleConstStringArgument(annotation);
        }
        return null;
    }

    public static final String getJsFileName(IrFile irFile) {
        Intrinsics.checkNotNullParameter(irFile, "<this>");
        IrConstructorCall annotation = IrUtilsKt.getAnnotation(irFile, JsAnnotations.INSTANCE.getJsFileNameFqn());
        if (annotation != null) {
            return getSingleConstStringArgument(annotation);
        }
        return null;
    }

    public static final String getJsName(IrAnnotationContainer irAnnotationContainer) {
        Intrinsics.checkNotNullParameter(irAnnotationContainer, "<this>");
        IrConstructorCall annotation = IrUtilsKt.getAnnotation(irAnnotationContainer, JsAnnotations.INSTANCE.getJsNameFqn());
        if (annotation != null) {
            return getSingleConstStringArgument(annotation);
        }
        return null;
    }

    public static final String getDeprecated(IrAnnotationContainer irAnnotationContainer) {
        Intrinsics.checkNotNullParameter(irAnnotationContainer, "<this>");
        IrConstructorCall annotation = IrUtilsKt.getAnnotation(irAnnotationContainer, StandardNames.FqNames.deprecated);
        if (annotation != null) {
            return getSingleConstStringArgument(annotation);
        }
        return null;
    }

    public static final boolean hasJsPolyfill(IrAnnotationContainer irAnnotationContainer) {
        Intrinsics.checkNotNullParameter(irAnnotationContainer, "<this>");
        return IrUtilsKt.hasAnnotation(irAnnotationContainer, JsAnnotations.INSTANCE.getJsPolyfillFqn());
    }

    public static final boolean isJsExport(IrAnnotationContainer irAnnotationContainer) {
        Intrinsics.checkNotNullParameter(irAnnotationContainer, "<this>");
        return IrUtilsKt.hasAnnotation(irAnnotationContainer, JsAnnotations.INSTANCE.getJsExportFqn());
    }

    public static final boolean isJsImplicitExport(IrAnnotationContainer irAnnotationContainer) {
        Intrinsics.checkNotNullParameter(irAnnotationContainer, "<this>");
        return IrUtilsKt.hasAnnotation(irAnnotationContainer, JsAnnotations.INSTANCE.getJsImplicitExportFqn());
    }

    public static final boolean isJsExportIgnore(IrAnnotationContainer irAnnotationContainer) {
        Intrinsics.checkNotNullParameter(irAnnotationContainer, "<this>");
        return IrUtilsKt.hasAnnotation(irAnnotationContainer, JsAnnotations.INSTANCE.getJsExportIgnoreFqn());
    }

    public static final boolean isJsNativeGetter(IrAnnotationContainer irAnnotationContainer) {
        Intrinsics.checkNotNullParameter(irAnnotationContainer, "<this>");
        return IrUtilsKt.hasAnnotation(irAnnotationContainer, JsAnnotations.INSTANCE.getJsNativeGetter());
    }

    public static final boolean isJsNativeSetter(IrAnnotationContainer irAnnotationContainer) {
        Intrinsics.checkNotNullParameter(irAnnotationContainer, "<this>");
        return IrUtilsKt.hasAnnotation(irAnnotationContainer, JsAnnotations.INSTANCE.getJsNativeSetter());
    }

    public static final boolean isJsNativeInvoke(IrAnnotationContainer irAnnotationContainer) {
        Intrinsics.checkNotNullParameter(irAnnotationContainer, "<this>");
        return IrUtilsKt.hasAnnotation(irAnnotationContainer, JsAnnotations.INSTANCE.getJsNativeInvoke());
    }

    private static final String dfsOverridableJsNameOrNull(IrOverridableDeclaration<?> irOverridableDeclaration) {
        String strDfsOverridableJsNameOrNull;
        String jsName;
        Iterator it = irOverridableDeclaration.getOverriddenSymbols().iterator();
        while (it.hasNext()) {
            IrSymbolOwner owner = ((IrSymbol) it.next()).getOwner();
            if ((owner instanceof IrAnnotationContainer) && (jsName = getJsName((IrAnnotationContainer) owner)) != null) {
                return jsName;
            }
            if ((owner instanceof IrOverridableDeclaration) && (strDfsOverridableJsNameOrNull = dfsOverridableJsNameOrNull((IrOverridableDeclaration) owner)) != null) {
                return strDfsOverridableJsNameOrNull;
            }
        }
        return null;
    }

    public static final String getJsNameForOverriddenDeclaration(IrDeclarationWithName irDeclarationWithName) {
        Intrinsics.checkNotNullParameter(irDeclarationWithName, "<this>");
        String jsName = getJsName(irDeclarationWithName);
        if (jsName != null) {
            return jsName;
        }
        if (irDeclarationWithName instanceof IrOverridableDeclaration) {
            return dfsOverridableJsNameOrNull((IrOverridableDeclaration) irDeclarationWithName);
        }
        return null;
    }

    public static final Name getJsNameOrKotlinName(IrDeclarationWithName irDeclarationWithName) {
        Intrinsics.checkNotNullParameter(irDeclarationWithName, "<this>");
        String jsNameForOverriddenDeclaration = getJsNameForOverriddenDeclaration(irDeclarationWithName);
        if (jsNameForOverriddenDeclaration == null) {
            return irDeclarationWithName.getName();
        }
        Name nameIdentifier = Name.identifier(jsNameForOverriddenDeclaration);
        Intrinsics.checkNotNullExpressionValue(nameIdentifier, "identifier(...)");
        return nameIdentifier;
    }

    public static final boolean isAssociatedObjectAnnotatedAnnotation(IrClass irClass) {
        Intrinsics.checkNotNullParameter(irClass, "<this>");
        if (IrUtilsKt.isAnnotationClass(irClass)) {
            List<IrConstructorCall> annotations = irClass.getAnnotations();
            if (!(annotations instanceof Collection) || !annotations.isEmpty()) {
                Iterator<T> it = annotations.iterator();
                while (it.hasNext()) {
                    if (Intrinsics.areEqual(IrUtilsKt.getFqNameWhenAvailable(AdditionalIrUtilsKt.getConstructedClass(((IrConstructorCall) it.next()).getSymbol().getOwner())), associatedObjectKeyAnnotationFqName)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public static final IrClass associatedObject(IrConstructorCall irConstructorCall) {
        IrClass owner;
        Intrinsics.checkNotNullParameter(irConstructorCall, "<this>");
        if (!isAssociatedObjectAnnotatedAnnotation(AdditionalIrUtilsKt.getConstructedClass(irConstructorCall.getSymbol().getOwner()))) {
            return null;
        }
        IrExpression valueArgument = irConstructorCall.getValueArgument(0);
        IrClassReference irClassReference = valueArgument instanceof IrClassReference ? (IrClassReference) valueArgument : null;
        IrClassifierSymbol symbol = irClassReference != null ? irClassReference.getSymbol() : null;
        IrClassSymbol irClassSymbol = symbol instanceof IrClassSymbol ? (IrClassSymbol) symbol : null;
        if (irClassSymbol == null || (owner = irClassSymbol.getOwner()) == null || !IrUtilsKt.isObject(owner)) {
            return null;
        }
        return owner;
    }
}
