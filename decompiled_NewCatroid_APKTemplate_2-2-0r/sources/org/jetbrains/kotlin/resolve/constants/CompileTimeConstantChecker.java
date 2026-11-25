package org.jetbrains.kotlin.resolve.constants;

import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.Set;
import org.jetbrains.kotlin.KtNodeTypes;
import org.jetbrains.kotlin.builtins.KotlinBuiltIns;
import org.jetbrains.kotlin.com.google.common.collect.Sets;
import org.jetbrains.kotlin.com.intellij.psi.tree.IElementType;
import org.jetbrains.kotlin.descriptors.ModuleDescriptor;
import org.jetbrains.kotlin.diagnostics.Diagnostic;
import org.jetbrains.kotlin.diagnostics.DiagnosticFactory;
import org.jetbrains.kotlin.diagnostics.DiagnosticUtilsKt;
import org.jetbrains.kotlin.diagnostics.Errors;
import org.jetbrains.kotlin.psi.KtConstantExpression;
import org.jetbrains.kotlin.psi.KtElement;
import org.jetbrains.kotlin.resolve.BindingTrace;
import org.jetbrains.kotlin.resolve.calls.context.ResolutionContext;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.KotlinTypeKt;
import org.jetbrains.kotlin.types.TypeUtils;
import org.jetbrains.kotlin.types.checker.KotlinTypeChecker;

/* JADX INFO: loaded from: classes6.dex */
public class CompileTimeConstantChecker {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final Set<DiagnosticFactory<?>> errorsThatDependOnExpectedType = Sets.newHashSet(Errors.CONSTANT_EXPECTED_TYPE_MISMATCH, Errors.NULL_FOR_NONNULL_TYPE);
    private final KotlinBuiltIns builtIns;
    private final boolean checkOnlyErrorsThatDependOnExpectedType;
    private final ResolutionContext<?> context;
    private final ModuleDescriptor module;
    private final BindingTrace trace;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[3];
        switch (i) {
            case 1:
                objArr[0] = "module";
                break;
            case 2:
            case 5:
            case 7:
            case 9:
            case 11:
            case 12:
            case 14:
            case 15:
            case 17:
            case 18:
                objArr[0] = "expression";
                break;
            case 3:
            case 4:
            case 6:
            case 8:
            case 10:
            case 20:
                objArr[0] = "expectedType";
                break;
            case 13:
                objArr[0] = "text";
                break;
            case 16:
            case 21:
                objArr[0] = "diagnostic";
                break;
            case 19:
                objArr[0] = "typeName";
                break;
            default:
                objArr[0] = "context";
                break;
        }
        objArr[1] = "org/jetbrains/kotlin/resolve/constants/CompileTimeConstantChecker";
        switch (i) {
            case 2:
            case 3:
                objArr[2] = "checkConstantExpressionType";
                break;
            case 4:
            case 5:
                objArr[2] = "checkIntegerValue";
                break;
            case 6:
            case 7:
                objArr[2] = "checkFloatValue";
                break;
            case 8:
            case 9:
                objArr[2] = "checkBooleanValue";
                break;
            case 10:
            case 11:
                objArr[2] = "checkNullValue";
                break;
            case 12:
                objArr[2] = "parseCharacter";
                break;
            case 13:
            case 14:
                objArr[2] = "escapedStringToCharacter";
                break;
            case 15:
                objArr[2] = "illegalEscape";
                break;
            case 16:
                objArr[2] = "createErrorCharacter";
                break;
            case 17:
                objArr[2] = "parseChar";
                break;
            case 18:
            case 19:
            case 20:
                objArr[2] = "reportConstantExpectedTypeMismatch";
                break;
            case 21:
                objArr[2] = "reportError";
                break;
            default:
                objArr[2] = "<init>";
                break;
        }
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
    }

    public CompileTimeConstantChecker(ResolutionContext<?> resolutionContext, ModuleDescriptor moduleDescriptor, boolean z) {
        if (resolutionContext == null) {
            $$$reportNull$$$0(0);
        }
        if (moduleDescriptor == null) {
            $$$reportNull$$$0(1);
        }
        this.context = resolutionContext;
        this.module = moduleDescriptor;
        this.builtIns = moduleDescriptor.getBuiltIns();
        this.checkOnlyErrorsThatDependOnExpectedType = z;
        this.trace = resolutionContext.trace;
    }

    public boolean checkConstantExpressionType(ConstantValue<?> constantValue, KtConstantExpression ktConstantExpression, KotlinType kotlinType) {
        if (ktConstantExpression == null) {
            $$$reportNull$$$0(2);
        }
        if (kotlinType == null) {
            $$$reportNull$$$0(3);
        }
        IElementType elementType = ktConstantExpression.getNode().getElementType();
        if (elementType == KtNodeTypes.INTEGER_CONSTANT) {
            return checkIntegerValue(constantValue, kotlinType, ktConstantExpression);
        }
        if (elementType == KtNodeTypes.FLOAT_CONSTANT) {
            return checkFloatValue(constantValue, kotlinType, ktConstantExpression);
        }
        if (elementType == KtNodeTypes.BOOLEAN_CONSTANT) {
            return checkBooleanValue(kotlinType, ktConstantExpression);
        }
        if (elementType == KtNodeTypes.CHARACTER_CONSTANT) {
            return checkCharValue(constantValue, kotlinType, ktConstantExpression);
        }
        if (elementType == KtNodeTypes.NULL) {
            return checkNullValue(kotlinType, ktConstantExpression);
        }
        return false;
    }

    private boolean checkIntegerValue(ConstantValue<?> constantValue, KotlinType kotlinType, KtConstantExpression ktConstantExpression) {
        if (kotlinType == null) {
            $$$reportNull$$$0(4);
        }
        if (ktConstantExpression == null) {
            $$$reportNull$$$0(5);
        }
        if (constantValue == null) {
            return reportError(Errors.INT_LITERAL_OUT_OF_RANGE.on(ktConstantExpression));
        }
        if (ktConstantExpression.getText().endsWith("l")) {
            return reportError(Errors.WRONG_LONG_SUFFIX.on(ktConstantExpression));
        }
        if (noExpectedTypeOrError(kotlinType)) {
            return false;
        }
        if (KotlinTypeChecker.DEFAULT.isSubtypeOf(constantValue.getType(this.module), kotlinType)) {
            return false;
        }
        return reportConstantExpectedTypeMismatch(ktConstantExpression, "integer", kotlinType, null);
    }

    private boolean checkFloatValue(ConstantValue<?> constantValue, KotlinType kotlinType, KtConstantExpression ktConstantExpression) {
        if (kotlinType == null) {
            $$$reportNull$$$0(6);
        }
        if (ktConstantExpression == null) {
            $$$reportNull$$$0(7);
        }
        if (constantValue == null) {
            return reportError(Errors.FLOAT_LITERAL_OUT_OF_RANGE.on(ktConstantExpression));
        }
        if (noExpectedTypeOrError(kotlinType)) {
            return false;
        }
        if (KotlinTypeChecker.DEFAULT.isSubtypeOf(constantValue.getType(this.module), kotlinType)) {
            return false;
        }
        return reportConstantExpectedTypeMismatch(ktConstantExpression, "floating-point", kotlinType, null);
    }

    private boolean checkBooleanValue(KotlinType kotlinType, KtConstantExpression ktConstantExpression) {
        if (kotlinType == null) {
            $$$reportNull$$$0(8);
        }
        if (ktConstantExpression == null) {
            $$$reportNull$$$0(9);
        }
        if (noExpectedTypeOrError(kotlinType) || KotlinTypeChecker.DEFAULT.isSubtypeOf(this.builtIns.getBooleanType(), kotlinType)) {
            return false;
        }
        return reportConstantExpectedTypeMismatch(ktConstantExpression, "boolean", kotlinType, this.builtIns.getBooleanType());
    }

    private boolean checkCharValue(ConstantValue<?> constantValue, KotlinType kotlinType, KtConstantExpression ktConstantExpression) {
        Diagnostic diagnostic;
        if (!noExpectedTypeOrError(kotlinType) && !KotlinTypeChecker.DEFAULT.isSubtypeOf(this.builtIns.getCharType(), kotlinType)) {
            return reportConstantExpectedTypeMismatch(ktConstantExpression, FirebaseAnalytics.Param.CHARACTER, kotlinType, this.builtIns.getCharType());
        }
        if (constantValue == null && (diagnostic = parseCharacter(ktConstantExpression).getDiagnostic()) != null) {
            return reportError(diagnostic);
        }
        return false;
    }

    private boolean checkNullValue(KotlinType kotlinType, KtConstantExpression ktConstantExpression) {
        if (kotlinType == null) {
            $$$reportNull$$$0(10);
        }
        if (ktConstantExpression == null) {
            $$$reportNull$$$0(11);
        }
        if (noExpectedTypeOrError(kotlinType) || TypeUtils.acceptsNullable(kotlinType)) {
            return false;
        }
        if (DiagnosticUtilsKt.reportTypeMismatchDueToTypeProjection(this.context, ktConstantExpression, kotlinType, this.builtIns.getNullableNothingType())) {
            return true;
        }
        return reportError(Errors.NULL_FOR_NONNULL_TYPE.on(ktConstantExpression, kotlinType));
    }

    private static CharacterWithDiagnostic parseCharacter(KtConstantExpression ktConstantExpression) {
        if (ktConstantExpression == null) {
            $$$reportNull$$$0(12);
        }
        String text = ktConstantExpression.getText();
        if (text.length() < 2 || text.charAt(0) != '\'' || text.charAt(text.length() - 1) != '\'') {
            return createErrorCharacter(Errors.INCORRECT_CHARACTER_LITERAL.on(ktConstantExpression));
        }
        String strSubstring = text.substring(1, text.length() - 1);
        if (strSubstring.length() == 0) {
            return createErrorCharacter(Errors.EMPTY_CHARACTER_LITERAL.on(ktConstantExpression));
        }
        if (strSubstring.charAt(0) != '\\') {
            if (strSubstring.length() == 1) {
                return new CharacterWithDiagnostic(strSubstring.charAt(0));
            }
            return createErrorCharacter(Errors.TOO_MANY_CHARACTERS_IN_CHARACTER_LITERAL.on(ktConstantExpression, ktConstantExpression));
        }
        return escapedStringToCharacter(strSubstring, ktConstantExpression);
    }

    public static CharacterWithDiagnostic escapedStringToCharacter(String str, KtElement ktElement) {
        if (str == null) {
            $$$reportNull$$$0(13);
        }
        if (ktElement == null) {
            $$$reportNull$$$0(14);
        }
        String strSubstring = str.substring(1);
        int length = strSubstring.length();
        if (length == 0) {
            return illegalEscape(ktElement);
        }
        if (length == 1) {
            Character chTranslateEscape = translateEscape(strSubstring.charAt(0));
            if (chTranslateEscape == null) {
                return illegalEscape(ktElement);
            }
            return new CharacterWithDiagnostic(chTranslateEscape.charValue());
        }
        if (length == 5 && strSubstring.charAt(0) == 'u') {
            try {
                return new CharacterWithDiagnostic((char) Integer.valueOf(strSubstring.substring(1), 16).intValue());
            } catch (NumberFormatException unused) {
            }
        }
        return illegalEscape(ktElement);
    }

    private static CharacterWithDiagnostic illegalEscape(KtElement ktElement) {
        if (ktElement == null) {
            $$$reportNull$$$0(15);
        }
        return createErrorCharacter(Errors.ILLEGAL_ESCAPE.on(ktElement, ktElement));
    }

    private static CharacterWithDiagnostic createErrorCharacter(Diagnostic diagnostic) {
        if (diagnostic == null) {
            $$$reportNull$$$0(16);
        }
        return new CharacterWithDiagnostic(diagnostic);
    }

    public static class CharacterWithDiagnostic {
        private Diagnostic diagnostic;
        private Character value;

        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", "diagnostic", "org/jetbrains/kotlin/resolve/constants/CompileTimeConstantChecker$CharacterWithDiagnostic", "<init>"));
        }

        public CharacterWithDiagnostic(Diagnostic diagnostic) {
            if (diagnostic == null) {
                $$$reportNull$$$0(0);
            }
            this.diagnostic = diagnostic;
        }

        public CharacterWithDiagnostic(char c) {
            this.value = Character.valueOf(c);
        }

        public Diagnostic getDiagnostic() {
            return this.diagnostic;
        }

        public Character getValue() {
            return this.value;
        }
    }

    public static Character parseChar(KtConstantExpression ktConstantExpression) {
        if (ktConstantExpression == null) {
            $$$reportNull$$$0(17);
        }
        return parseCharacter(ktConstantExpression).getValue();
    }

    private static Character translateEscape(char c) {
        if (c == '\"') {
            return '\"';
        }
        if (c == '$') {
            return '$';
        }
        if (c == '\'') {
            return '\'';
        }
        if (c == '\\') {
            return '\\';
        }
        if (c == 'b') {
            return '\b';
        }
        if (c == 'n') {
            return '\n';
        }
        if (c != 'r') {
            return c != 't' ? null : '\t';
        }
        return '\r';
    }

    private static boolean noExpectedTypeOrError(KotlinType kotlinType) {
        return TypeUtils.noExpectedType(kotlinType) || KotlinTypeKt.isError(kotlinType);
    }

    private boolean reportConstantExpectedTypeMismatch(KtConstantExpression ktConstantExpression, String str, KotlinType kotlinType, KotlinType kotlinType2) {
        if (ktConstantExpression == null) {
            $$$reportNull$$$0(18);
        }
        if (str == null) {
            $$$reportNull$$$0(19);
        }
        if (kotlinType == null) {
            $$$reportNull$$$0(20);
        }
        if (DiagnosticUtilsKt.reportTypeMismatchDueToTypeProjection(this.context, ktConstantExpression, kotlinType, kotlinType2)) {
            return true;
        }
        this.trace.report(Errors.CONSTANT_EXPECTED_TYPE_MISMATCH.on(ktConstantExpression, str, kotlinType));
        return true;
    }

    private boolean reportError(Diagnostic diagnostic) {
        if (diagnostic == null) {
            $$$reportNull$$$0(21);
        }
        if (this.checkOnlyErrorsThatDependOnExpectedType && !errorsThatDependOnExpectedType.contains(diagnostic.getFactory())) {
            return false;
        }
        this.trace.report(diagnostic);
        return true;
    }
}
