package org.jetbrains.kotlin.backend.jvm.codegen;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.builtins.StandardNames;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.descriptors.annotations.KotlinTarget;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrTypeParameter;
import org.jetbrains.kotlin.ir.expressions.IrConstructorCall;
import org.jetbrains.kotlin.ir.expressions.IrExpression;
import org.jetbrains.kotlin.ir.expressions.IrGetEnumValue;
import org.jetbrains.kotlin.ir.expressions.IrVararg;
import org.jetbrains.kotlin.ir.expressions.IrVarargElement;
import org.jetbrains.kotlin.ir.symbols.IrClassifierSymbol;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.ir.types.IrTypePredicatesKt;
import org.jetbrains.kotlin.ir.types.IrTypeUtilsKt;
import org.jetbrains.kotlin.ir.types.IrTypesKt;
import org.jetbrains.kotlin.ir.util.IrUtilsKt;
import org.jetbrains.kotlin.name.Name;

/* JADX INFO: compiled from: AnnotationCodegen.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000*\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a\u0010\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\u0002\u001a\u0018\u0010\u0006\u001a\n\u0012\u0004\u0012\u00020\b\u0018\u00010\u00072\u0006\u0010\t\u001a\u00020\nH\u0002\u001a\u0014\u0010\u000b\u001a\n\u0012\u0004\u0012\u00020\b\u0018\u00010\u0007*\u00020\fH\u0002\"\u000e\u0010\u0000\u001a\u00020\u0001X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\r"}, d2 = {"TARGET_ALLOWED_TARGETS", "Lorg/jetbrains/kotlin/name/Name;", "isBareTypeParameterWithNullableUpperBound", "", ModuleXmlParser.TYPE, "Lorg/jetbrains/kotlin/ir/types/IrType;", "loadAnnotationTargets", "", "Lorg/jetbrains/kotlin/descriptors/annotations/KotlinTarget;", "targetEntry", "Lorg/jetbrains/kotlin/ir/expressions/IrConstructorCall;", "applicableTargetSet", "Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "backend.jvm.codegen"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class AnnotationCodegenKt {
    private static final Name TARGET_ALLOWED_TARGETS;

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean isBareTypeParameterWithNullableUpperBound(IrType irType) {
        IrClassifierSymbol classifierOrNull = IrTypesKt.getClassifierOrNull(irType);
        return ((classifierOrNull != null ? classifierOrNull.getOwner() : null) instanceof IrTypeParameter) && !IrTypePredicatesKt.isMarkedNullable(irType) && IrTypeUtilsKt.isNullable(irType);
    }

    static {
        Name nameIdentifier = Name.identifier("allowedTargets");
        Intrinsics.checkNotNullExpressionValue(nameIdentifier, "identifier(...)");
        TARGET_ALLOWED_TARGETS = nameIdentifier;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Set<KotlinTarget> applicableTargetSet(IrClass irClass) {
        IrConstructorCall annotation = IrUtilsKt.getAnnotation(irClass, StandardNames.FqNames.target);
        if (annotation == null) {
            return null;
        }
        return loadAnnotationTargets(annotation);
    }

    private static final Set<KotlinTarget> loadAnnotationTargets(IrConstructorCall irConstructorCall) {
        IrExpression valueArgument = IrUtilsKt.getValueArgument(irConstructorCall, TARGET_ALLOWED_TARGETS);
        IrVararg irVararg = valueArgument instanceof IrVararg ? (IrVararg) valueArgument : null;
        if (irVararg == null) {
            return null;
        }
        List<IrVarargElement> elements = irVararg.getElements();
        ArrayList<IrGetEnumValue> arrayList = new ArrayList();
        for (Object obj : elements) {
            if (obj instanceof IrGetEnumValue) {
                arrayList.add(obj);
            }
        }
        ArrayList arrayList2 = new ArrayList();
        for (IrGetEnumValue irGetEnumValue : arrayList) {
            KotlinTarget.Companion companion = KotlinTarget.INSTANCE;
            String strAsString = irGetEnumValue.getSymbol().getOwner().getName().asString();
            Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
            KotlinTarget kotlinTargetValueOrNull = companion.valueOrNull(strAsString);
            if (kotlinTargetValueOrNull != null) {
                arrayList2.add(kotlinTargetValueOrNull);
            }
        }
        return CollectionsKt.toSet(arrayList2);
    }
}
