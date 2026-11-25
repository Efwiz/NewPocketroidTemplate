package org.jetbrains.kotlin.ir.interpreter.checker;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.collections.CollectionsKt;
import kotlin.collections.SetsKt;
import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.ir.BuiltInOperatorNames;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrConstructor;
import org.jetbrains.kotlin.ir.declarations.IrDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationParent;
import org.jetbrains.kotlin.ir.declarations.IrFunction;
import org.jetbrains.kotlin.ir.declarations.IrProperty;
import org.jetbrains.kotlin.ir.declarations.IrValueParameter;
import org.jetbrains.kotlin.ir.expressions.IrBlock;
import org.jetbrains.kotlin.ir.expressions.IrCall;
import org.jetbrains.kotlin.ir.expressions.IrCallableReference;
import org.jetbrains.kotlin.ir.expressions.IrComposite;
import org.jetbrains.kotlin.ir.expressions.IrDeclarationReference;
import org.jetbrains.kotlin.ir.expressions.IrExpression;
import org.jetbrains.kotlin.ir.expressions.IrFunctionExpression;
import org.jetbrains.kotlin.ir.expressions.IrGetEnumValue;
import org.jetbrains.kotlin.ir.expressions.IrMemberAccessExpression;
import org.jetbrains.kotlin.ir.expressions.IrStatementOrigin;
import org.jetbrains.kotlin.ir.expressions.IrWhen;
import org.jetbrains.kotlin.ir.interpreter.UtilsKt;
import org.jetbrains.kotlin.ir.types.IrSimpleType;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.ir.types.IrTypePredicatesKt;
import org.jetbrains.kotlin.ir.util.IrFakeOverrideUtilsKt;
import org.jetbrains.kotlin.ir.util.IrTypeUtilsKt;
import org.jetbrains.kotlin.ir.util.IrUtilsKt;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.name.FqName;

/* JADX INFO: compiled from: EvaluationMode.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000^\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\b\u0086\u0081\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0016J\u0014\u0010\u0007\u001a\u00020\u00042\n\u0010\b\u001a\u0006\u0012\u0002\b\u00030\tH\u0016J\u0010\u0010\n\u001a\u00020\u00042\u0006\u0010\b\u001a\u00020\u000bH\u0016J\u0010\u0010\f\u001a\u00020\u00042\u0006\u0010\r\u001a\u00020\u000eH\u0016J\u0010\u0010\u000f\u001a\u00020\u00042\u0006\u0010\u0010\u001a\u00020\u0011H\u0016J\u0010\u0010\u0012\u001a\u00020\u00042\u0006\u0010\u0013\u001a\u00020\u0014H\u0016J\u0010\u0010\u0015\u001a\u00020\u00042\u0006\u0010\u0016\u001a\u00020\u0017H\u0016J\u0010\u0010\u0018\u001a\u00020\u00042\u0006\u0010\u0013\u001a\u00020\u0019H\u0016J\u0010\u0010\u001a\u001a\u00020\u00042\u0006\u0010\u0016\u001a\u00020\u0017H\u0016J\f\u0010\u001b\u001a\u00020\u0004*\u00020\u001cH\u0004J\u0014\u0010\u001d\u001a\u00020\u0004*\u00020\u001c2\u0006\u0010\u001e\u001a\u00020\u001fH\u0004j\u0002\b j\u0002\b!j\u0002\b\"¨\u0006#"}, d2 = {"Lorg/jetbrains/kotlin/ir/interpreter/checker/EvaluationMode;", "", "(Ljava/lang/String;I)V", "canEvaluateBlock", "", "block", "Lorg/jetbrains/kotlin/ir/expressions/IrBlock;", "canEvaluateCallableReference", "reference", "Lorg/jetbrains/kotlin/ir/expressions/IrCallableReference;", "canEvaluateClassReference", "Lorg/jetbrains/kotlin/ir/expressions/IrDeclarationReference;", "canEvaluateComposite", "composite", "Lorg/jetbrains/kotlin/ir/expressions/IrComposite;", "canEvaluateEnumValue", "enumEntry", "Lorg/jetbrains/kotlin/ir/expressions/IrGetEnumValue;", "canEvaluateExpression", "expression", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "canEvaluateFunction", "function", "Lorg/jetbrains/kotlin/ir/declarations/IrFunction;", "canEvaluateFunctionExpression", "Lorg/jetbrains/kotlin/ir/expressions/IrFunctionExpression;", "mustCheckBodyOf", "isMarkedAsIntrinsicConstEvaluation", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;", "isMarkedWith", "annotation", "Lorg/jetbrains/kotlin/name/FqName;", "FULL", "ONLY_BUILTINS", "ONLY_INTRINSIC_CONST", "ir.interpreter"}, k = 1, mv = {1, 9, 0}, xi = 48)
public enum EvaluationMode {
    FULL { // from class: org.jetbrains.kotlin.ir.interpreter.checker.EvaluationMode.FULL
        @Override // org.jetbrains.kotlin.ir.interpreter.checker.EvaluationMode
        public boolean canEvaluateBlock(IrBlock block) {
            Intrinsics.checkNotNullParameter(block, "block");
            return true;
        }

        @Override // org.jetbrains.kotlin.ir.interpreter.checker.EvaluationMode
        public boolean canEvaluateCallableReference(IrCallableReference<?> reference) {
            Intrinsics.checkNotNullParameter(reference, "reference");
            return true;
        }

        @Override // org.jetbrains.kotlin.ir.interpreter.checker.EvaluationMode
        public boolean canEvaluateClassReference(IrDeclarationReference reference) {
            Intrinsics.checkNotNullParameter(reference, "reference");
            return true;
        }

        @Override // org.jetbrains.kotlin.ir.interpreter.checker.EvaluationMode
        public boolean canEvaluateComposite(IrComposite composite) {
            Intrinsics.checkNotNullParameter(composite, "composite");
            return true;
        }

        @Override // org.jetbrains.kotlin.ir.interpreter.checker.EvaluationMode
        public boolean canEvaluateEnumValue(IrGetEnumValue enumEntry) {
            Intrinsics.checkNotNullParameter(enumEntry, "enumEntry");
            return true;
        }

        @Override // org.jetbrains.kotlin.ir.interpreter.checker.EvaluationMode
        public boolean canEvaluateExpression(IrExpression expression) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            return true;
        }

        @Override // org.jetbrains.kotlin.ir.interpreter.checker.EvaluationMode
        public boolean canEvaluateFunction(IrFunction function) {
            Intrinsics.checkNotNullParameter(function, "function");
            return true;
        }

        @Override // org.jetbrains.kotlin.ir.interpreter.checker.EvaluationMode
        public boolean canEvaluateFunctionExpression(IrFunctionExpression expression) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            return true;
        }

        @Override // org.jetbrains.kotlin.ir.interpreter.checker.EvaluationMode
        public boolean mustCheckBodyOf(IrFunction function) {
            Intrinsics.checkNotNullParameter(function, "function");
            return true;
        }
    },
    ONLY_BUILTINS { // from class: org.jetbrains.kotlin.ir.interpreter.checker.EvaluationMode.ONLY_BUILTINS
        private final Set<String> allowedBuiltinExtensionFunctions;
        private final Set<String> allowedMethodsOnPrimitives = SetsKt.setOf((Object[]) new String[]{"not", "unaryMinus", "unaryPlus", "inv", "toString", "toChar", "toByte", "toShort", "toInt", "toLong", "toFloat", "toDouble", Namer.EQUALS_METHOD_NAME, "compareTo", "plus", "minus", "times", "div", "rem", "and", "or", "xor", "shl", "shr", "ushr", BuiltInOperatorNames.LESS, BuiltInOperatorNames.LESS_OR_EQUAL, BuiltInOperatorNames.GREATER, BuiltInOperatorNames.GREATER_OR_EQUAL});
        private final Set<String> allowedMethodsOnStrings = SetsKt.setOf((Object[]) new String[]{"<get-length>", "plus", "get", "compareTo", Namer.EQUALS_METHOD_NAME, "toString"});
        private final Set<String> allowedExtensionFunctions = SetsKt.setOf((Object[]) new String[]{"kotlin.floorDiv", "kotlin.mod", "kotlin.NumbersKt.floorDiv", "kotlin.NumbersKt.mod", "kotlin.<get-code>"});

        @Override // org.jetbrains.kotlin.ir.interpreter.checker.EvaluationMode
        public boolean canEvaluateFunction(IrFunction function) {
            IrSimpleType defaultType;
            Intrinsics.checkNotNullParameter(function, "function");
            IrProperty property = UtilsKt.getProperty(function);
            if (property != null && property.getIsConst()) {
                return true;
            }
            IrType returnType = function.getReturnType();
            if (!IrTypePredicatesKt.isPrimitiveType$default(returnType, false, 1, null) && !IrTypePredicatesKt.isString(returnType) && !IrTypePredicatesKt.isUnsignedType$default(returnType, false, 1, null)) {
                return false;
            }
            FqName fqNameWhenAvailable = IrUtilsKt.getFqNameWhenAvailable(function);
            String strAsString = fqNameWhenAvailable != null ? fqNameWhenAvailable.asString() : null;
            IrClass parentClassOrNull = IrUtilsKt.getParentClassOrNull(function);
            IrSimpleType defaultType2 = parentClassOrNull != null ? IrUtilsKt.getDefaultType(parentClassOrNull) : null;
            if (defaultType2 == null) {
                if (CollectionsKt.contains(this.allowedExtensionFunctions, strAsString) || CollectionsKt.contains(this.allowedBuiltinExtensionFunctions, strAsString)) {
                    return true;
                }
            } else {
                IrSimpleType irSimpleType = defaultType2;
                if (IrTypePredicatesKt.isPrimitiveType$default(irSimpleType, false, 1, null)) {
                    return this.allowedMethodsOnPrimitives.contains(function.getName().asString());
                }
                if (IrTypePredicatesKt.isString(irSimpleType)) {
                    return this.allowedMethodsOnStrings.contains(function.getName().asString());
                }
                if (IrUtilsKt.isObject(parentClassOrNull)) {
                    IrClass parentClassOrNull2 = IrUtilsKt.getParentClassOrNull(parentClassOrNull);
                    if (parentClassOrNull2 != null && (defaultType = IrUtilsKt.getDefaultType(parentClassOrNull2)) != null) {
                        IrSimpleType irSimpleType2 = defaultType;
                        if (IrTypePredicatesKt.isPrimitiveType$default(irSimpleType2, false, 1, null) || IrTypeUtilsKt.isUnsigned(irSimpleType2)) {
                            return true;
                        }
                    }
                } else if ((IrTypePredicatesKt.isUnsignedType$default(irSimpleType, false, 1, null) && (function instanceof IrConstructor)) || CollectionsKt.contains(this.allowedExtensionFunctions, strAsString) || CollectionsKt.contains(this.allowedBuiltinExtensionFunctions, strAsString)) {
                    return true;
                }
            }
            return false;
        }

        @Override // org.jetbrains.kotlin.ir.interpreter.checker.EvaluationMode
        public boolean canEvaluateBlock(IrBlock block) {
            Intrinsics.checkNotNullParameter(block, "block");
            return block.getStatements().size() == 1;
        }

        @Override // org.jetbrains.kotlin.ir.interpreter.checker.EvaluationMode
        public boolean canEvaluateExpression(IrExpression expression) {
            IrType type;
            Intrinsics.checkNotNullParameter(expression, "expression");
            if (!(expression instanceof IrCall)) {
                return false;
            }
            List<Pair<IrValueParameter, IrExpression>> allArgumentsWithIr = IrUtilsKt.getAllArgumentsWithIr((IrMemberAccessExpression) expression);
            if (!(allArgumentsWithIr instanceof Collection) || !allArgumentsWithIr.isEmpty()) {
                Iterator<T> it = allArgumentsWithIr.iterator();
                while (it.hasNext()) {
                    IrExpression irExpression = (IrExpression) ((Pair) it.next()).getSecond();
                    if (irExpression != null && (type = irExpression.getType()) != null && IrTypeUtilsKt.isUnsigned(type)) {
                        FqName fqNameWhenAvailable = IrUtilsKt.getFqNameWhenAvailable(((IrCall) expression).getSymbol().getOwner());
                        return Intrinsics.areEqual(fqNameWhenAvailable != null ? fqNameWhenAvailable.asString() : null, "kotlin.String.plus");
                    }
                }
            }
            return true;
        }
    },
    ONLY_INTRINSIC_CONST { // from class: org.jetbrains.kotlin.ir.interpreter.checker.EvaluationMode.ONLY_INTRINSIC_CONST
        @Override // org.jetbrains.kotlin.ir.interpreter.checker.EvaluationMode
        public boolean canEvaluateFunction(IrFunction function) {
            Intrinsics.checkNotNullParameter(function, "function");
            return isCompileTimePropertyAccessor(function) || isMarkedAsIntrinsicConstEvaluation(function);
        }

        private final boolean isCompileTimePropertyAccessor(IrFunction irFunction) {
            IrProperty property;
            if (irFunction == null || (property = UtilsKt.getProperty(irFunction)) == null) {
                return false;
            }
            if (!property.getIsConst()) {
                IrProperty irProperty = (IrProperty) IrFakeOverrideUtilsKt.resolveFakeOverride$default(property, false, null, 3, null);
                if (irProperty != null) {
                    property = irProperty;
                }
                if (!isMarkedAsIntrinsicConstEvaluation(property)) {
                    return false;
                }
            }
            return true;
        }

        @Override // org.jetbrains.kotlin.ir.interpreter.checker.EvaluationMode
        public boolean canEvaluateBlock(IrBlock block) {
            Intrinsics.checkNotNullParameter(block, "block");
            return Intrinsics.areEqual(block.getOrigin(), IrStatementOrigin.WHEN.INSTANCE) || block.getStatements().size() == 1;
        }

        @Override // org.jetbrains.kotlin.ir.interpreter.checker.EvaluationMode
        public boolean canEvaluateExpression(IrExpression expression) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            return EvaluationMode.ONLY_BUILTINS.canEvaluateExpression(expression) || (expression instanceof IrWhen);
        }
    };

    private static final /* synthetic */ EnumEntries $ENTRIES = EnumEntriesKt.enumEntries(values());

    /* synthetic */ EvaluationMode(DefaultConstructorMarker defaultConstructorMarker) {
        this();
    }

    public static EnumEntries<EvaluationMode> getEntries() {
        return $ENTRIES;
    }

    public boolean canEvaluateBlock(IrBlock block) {
        Intrinsics.checkNotNullParameter(block, "block");
        return false;
    }

    public boolean canEvaluateCallableReference(IrCallableReference<?> reference) {
        Intrinsics.checkNotNullParameter(reference, "reference");
        return false;
    }

    public boolean canEvaluateClassReference(IrDeclarationReference reference) {
        Intrinsics.checkNotNullParameter(reference, "reference");
        return false;
    }

    public boolean canEvaluateEnumValue(IrGetEnumValue enumEntry) {
        Intrinsics.checkNotNullParameter(enumEntry, "enumEntry");
        return false;
    }

    public boolean canEvaluateExpression(IrExpression expression) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        return false;
    }

    public boolean canEvaluateFunction(IrFunction function) {
        Intrinsics.checkNotNullParameter(function, "function");
        return false;
    }

    public boolean canEvaluateFunctionExpression(IrFunctionExpression expression) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        return false;
    }

    public boolean canEvaluateComposite(IrComposite composite) {
        Intrinsics.checkNotNullParameter(composite, "composite");
        return Intrinsics.areEqual(composite.getOrigin(), IrStatementOrigin.DESTRUCTURING_DECLARATION.INSTANCE) || composite.getOrigin() == null;
    }

    public boolean mustCheckBodyOf(IrFunction function) {
        Intrinsics.checkNotNullParameter(function, "function");
        return UtilsKt.getProperty(function) != null;
    }

    protected final boolean isMarkedAsIntrinsicConstEvaluation(IrDeclaration irDeclaration) {
        Intrinsics.checkNotNullParameter(irDeclaration, "<this>");
        return isMarkedWith(irDeclaration, UtilsKt.getIntrinsicConstEvaluationAnnotation());
    }

    protected final boolean isMarkedWith(IrDeclaration irDeclaration, FqName annotation) {
        Intrinsics.checkNotNullParameter(irDeclaration, "<this>");
        Intrinsics.checkNotNullParameter(annotation, "annotation");
        if ((irDeclaration instanceof IrClass) && ((IrClass) irDeclaration).getIsCompanion()) {
            return false;
        }
        if (UtilsKt.hasAnnotation(irDeclaration, annotation)) {
            return true;
        }
        IrDeclarationParent parent = irDeclaration.getParent();
        IrClass irClass = parent instanceof IrClass ? (IrClass) parent : null;
        if (irClass != null) {
            return isMarkedWith(irClass, annotation);
        }
        return false;
    }
}
