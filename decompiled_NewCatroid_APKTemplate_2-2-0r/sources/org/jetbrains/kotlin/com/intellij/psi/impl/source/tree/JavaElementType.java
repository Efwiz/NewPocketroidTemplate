package org.jetbrains.kotlin.com.intellij.psi.impl.source.tree;

import java.util.function.Supplier;
import org.jetbrains.kotlin.com.intellij.lang.ASTNode;
import org.jetbrains.kotlin.com.intellij.lang.Language;
import org.jetbrains.kotlin.com.intellij.lang.LighterASTNode;
import org.jetbrains.kotlin.com.intellij.lang.LighterLazyParseableNode;
import org.jetbrains.kotlin.com.intellij.lang.PsiBuilder;
import org.jetbrains.kotlin.com.intellij.lang.PsiBuilderUtil;
import org.jetbrains.kotlin.com.intellij.lang.java.JavaLanguage;
import org.jetbrains.kotlin.com.intellij.lang.java.JavaParserDefinition;
import org.jetbrains.kotlin.com.intellij.lang.java.parser.JavaParser;
import org.jetbrains.kotlin.com.intellij.lang.java.parser.JavaParserUtil;
import org.jetbrains.kotlin.com.intellij.openapi.project.Project;
import org.jetbrains.kotlin.com.intellij.pom.java.LanguageLevel;
import org.jetbrains.kotlin.com.intellij.psi.JavaTokenType;
import org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.JavaStubElementTypes;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.JavaDummyElement;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.PsiDiamondTypeElementImpl;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.PsiImportStaticReferenceElementImpl;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.PsiJavaCodeReferenceElementImpl;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.PsiJavaModuleReferenceElementImpl;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.PsiReceiverParameterImpl;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.PsiTypeElementImpl;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.java.PsiArrayAccessExpressionImpl;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.java.PsiArrayInitializerExpressionImpl;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.java.PsiArrayInitializerMemberValueImpl;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.java.PsiAssertStatementImpl;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.java.PsiAssignmentExpressionImpl;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.java.PsiBinaryExpressionImpl;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.java.PsiBlockStatementImpl;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.java.PsiBreakStatementImpl;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.java.PsiCaseLabelElementListImpl;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.java.PsiCatchSectionImpl;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.java.PsiClassObjectAccessExpressionImpl;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.java.PsiCodeBlockImpl;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.java.PsiConditionalExpressionImpl;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.java.PsiContinueStatementImpl;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.java.PsiDeclarationStatementImpl;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.java.PsiDefaultLabelElementImpl;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.java.PsiDoWhileStatementImpl;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.java.PsiEmptyExpressionImpl;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.java.PsiEmptyStatementImpl;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.java.PsiExpressionListImpl;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.java.PsiExpressionListStatementImpl;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.java.PsiExpressionStatementImpl;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.java.PsiForStatementImpl;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.java.PsiForeachStatementImpl;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.java.PsiGuardedPatternImpl;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.java.PsiIfStatementImpl;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.java.PsiInstanceOfExpressionImpl;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.java.PsiLabeledStatementImpl;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.java.PsiLocalVariableImpl;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.java.PsiMethodCallExpressionImpl;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.java.PsiNewExpressionImpl;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.java.PsiPackageStatementImpl;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.java.PsiParenthesizedExpressionImpl;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.java.PsiParenthesizedPatternImpl;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.java.PsiPatternVariableImpl;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.java.PsiPolyadicExpressionImpl;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.java.PsiPostfixExpressionImpl;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.java.PsiPrefixExpressionImpl;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.java.PsiReferenceExpressionImpl;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.java.PsiReferenceParameterListImpl;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.java.PsiResourceExpressionImpl;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.java.PsiResourceListImpl;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.java.PsiResourceVariableImpl;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.java.PsiReturnStatementImpl;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.java.PsiSuperExpressionImpl;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.java.PsiSwitchExpressionImpl;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.java.PsiSwitchLabelStatementImpl;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.java.PsiSwitchLabeledRuleStatementImpl;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.java.PsiSwitchStatementImpl;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.java.PsiSynchronizedStatementImpl;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.java.PsiThisExpressionImpl;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.java.PsiThrowStatementImpl;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.java.PsiTryStatementImpl;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.java.PsiTypeCastExpressionImpl;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.java.PsiTypeTestPatternImpl;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.java.PsiWhileStatementImpl;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.java.PsiYieldStatementImpl;
import org.jetbrains.kotlin.com.intellij.psi.tree.ICompositeElementType;
import org.jetbrains.kotlin.com.intellij.psi.tree.IElementType;
import org.jetbrains.kotlin.com.intellij.psi.tree.IErrorCounterReparseableElementType;
import org.jetbrains.kotlin.com.intellij.psi.tree.ILazyParseableElementType;
import org.jetbrains.kotlin.com.intellij.psi.tree.ILightLazyParseableElementType;
import org.jetbrains.kotlin.com.intellij.psi.tree.java.IJavaElementType;
import org.jetbrains.kotlin.com.intellij.util.diff.FlyweightCapableTreeStructure;
import org.jetbrains.kotlin.load.java.JvmAbi;

/* JADX INFO: loaded from: classes3.dex */
public interface JavaElementType {
    public static final IElementType ANNOTATION_ARRAY_INITIALIZER;
    public static final IElementType ARRAY_ACCESS_EXPRESSION;
    public static final IElementType ARRAY_INITIALIZER_EXPRESSION;
    public static final IElementType ASSERT_STATEMENT;
    public static final IElementType ASSIGNMENT_EXPRESSION;
    public static final IElementType BINARY_EXPRESSION;
    public static final IElementType BLOCK_STATEMENT;
    public static final IElementType BREAK_STATEMENT;
    public static final IElementType CASE_LABEL_ELEMENT_LIST;
    public static final IElementType CATCH_SECTION;
    public static final IElementType CLASS_OBJECT_ACCESS_EXPRESSION;
    public static final ILazyParseableElementType CODE_BLOCK;
    public static final IElementType CONDITIONAL_EXPRESSION;
    public static final IElementType CONTINUE_STATEMENT;
    public static final IElementType DECLARATION_STATEMENT;
    public static final IElementType DEFAULT_CASE_LABEL_ELEMENT;
    public static final IElementType DIAMOND_TYPE;
    public static final IElementType DO_WHILE_STATEMENT;
    public static final IElementType DUMMY_ELEMENT;
    public static final IElementType EMPTY_EXPRESSION;
    public static final IElementType EMPTY_STATEMENT;
    public static final IElementType EXPRESSION_LIST;
    public static final IElementType EXPRESSION_LIST_STATEMENT;
    public static final IElementType EXPRESSION_STATEMENT;
    public static final IElementType FOREACH_STATEMENT;
    public static final IElementType FOR_STATEMENT;
    public static final IElementType GUARDED_PATTERN;
    public static final IElementType IF_STATEMENT;
    public static final IElementType IMPORT_STATIC_REFERENCE;
    public static final IElementType INSTANCE_OF_EXPRESSION;
    public static final IElementType JAVA_CODE_REFERENCE;
    public static final IElementType LABELED_STATEMENT;
    public static final IElementType LOCAL_VARIABLE;
    public static final IElementType METHOD_CALL_EXPRESSION;
    public static final IElementType MODULE_REFERENCE;
    public static final IElementType NEW_EXPRESSION;
    public static final IElementType PACKAGE_STATEMENT;
    public static final IElementType PARENTHESIZED_PATTERN;
    public static final IElementType PARENTH_EXPRESSION;
    public static final IElementType PATTERN_VARIABLE;
    public static final IElementType POLYADIC_EXPRESSION;
    public static final IElementType POSTFIX_EXPRESSION;
    public static final IElementType PREFIX_EXPRESSION;
    public static final IElementType RECEIVER_PARAMETER;
    public static final IElementType REFERENCE_EXPRESSION;
    public static final IElementType REFERENCE_PARAMETER_LIST;
    public static final IElementType RESOURCE_EXPRESSION;
    public static final IElementType RESOURCE_LIST;
    public static final IElementType RESOURCE_VARIABLE;
    public static final IElementType RETURN_STATEMENT;
    public static final IElementType SUPER_EXPRESSION;
    public static final IElementType SWITCH_EXPRESSION;
    public static final IElementType SWITCH_LABELED_RULE;
    public static final IElementType SWITCH_LABEL_STATEMENT;
    public static final IElementType SWITCH_STATEMENT;
    public static final IElementType SYNCHRONIZED_STATEMENT;
    public static final IElementType THIS_EXPRESSION;
    public static final IElementType THROW_STATEMENT;
    public static final IElementType TRY_STATEMENT;
    public static final IElementType TYPE;
    public static final IElementType TYPE_CAST_EXPRESSION;
    public static final IElementType TYPE_TEST_PATTERN;
    public static final IElementType WHILE_STATEMENT;
    public static final IElementType YIELD_STATEMENT;
    public static final IElementType CLASS = JavaStubElementTypes.CLASS;
    public static final IElementType ANONYMOUS_CLASS = JavaStubElementTypes.ANONYMOUS_CLASS;
    public static final IElementType ENUM_CONSTANT_INITIALIZER = JavaStubElementTypes.ENUM_CONSTANT_INITIALIZER;
    public static final IElementType TYPE_PARAMETER_LIST = JavaStubElementTypes.TYPE_PARAMETER_LIST;
    public static final IElementType TYPE_PARAMETER = JavaStubElementTypes.TYPE_PARAMETER;
    public static final IElementType IMPORT_LIST = JavaStubElementTypes.IMPORT_LIST;
    public static final IElementType IMPORT_STATEMENT = JavaStubElementTypes.IMPORT_STATEMENT;
    public static final IElementType IMPORT_STATIC_STATEMENT = JavaStubElementTypes.IMPORT_STATIC_STATEMENT;
    public static final IElementType MODIFIER_LIST = JavaStubElementTypes.MODIFIER_LIST;
    public static final IElementType ANNOTATION = JavaStubElementTypes.ANNOTATION;
    public static final IElementType NAME_VALUE_PAIR = JavaStubElementTypes.NAME_VALUE_PAIR;
    public static final IElementType LITERAL_EXPRESSION = JavaStubElementTypes.LITERAL_EXPRESSION;
    public static final IElementType ANNOTATION_PARAMETER_LIST = JavaStubElementTypes.ANNOTATION_PARAMETER_LIST;
    public static final IElementType EXTENDS_LIST = JavaStubElementTypes.EXTENDS_LIST;
    public static final IElementType IMPLEMENTS_LIST = JavaStubElementTypes.IMPLEMENTS_LIST;
    public static final IElementType FIELD = JavaStubElementTypes.FIELD;
    public static final IElementType ENUM_CONSTANT = JavaStubElementTypes.ENUM_CONSTANT;
    public static final IElementType METHOD = JavaStubElementTypes.METHOD;
    public static final IElementType ANNOTATION_METHOD = JavaStubElementTypes.ANNOTATION_METHOD;
    public static final IElementType CLASS_INITIALIZER = JavaStubElementTypes.CLASS_INITIALIZER;
    public static final IElementType PARAMETER = JavaStubElementTypes.PARAMETER;
    public static final IElementType PARAMETER_LIST = JavaStubElementTypes.PARAMETER_LIST;
    public static final IElementType EXTENDS_BOUND_LIST = JavaStubElementTypes.EXTENDS_BOUND_LIST;
    public static final IElementType THROWS_LIST = JavaStubElementTypes.THROWS_LIST;
    public static final IElementType LAMBDA_EXPRESSION = JavaStubElementTypes.LAMBDA_EXPRESSION;
    public static final IElementType METHOD_REF_EXPRESSION = JavaStubElementTypes.METHOD_REF_EXPRESSION;
    public static final IElementType MODULE = JavaStubElementTypes.MODULE;
    public static final IElementType REQUIRES_STATEMENT = JavaStubElementTypes.REQUIRES_STATEMENT;
    public static final IElementType EXPORTS_STATEMENT = JavaStubElementTypes.EXPORTS_STATEMENT;
    public static final IElementType OPENS_STATEMENT = JavaStubElementTypes.OPENS_STATEMENT;
    public static final IElementType USES_STATEMENT = JavaStubElementTypes.USES_STATEMENT;
    public static final IElementType PROVIDES_STATEMENT = JavaStubElementTypes.PROVIDES_STATEMENT;
    public static final IElementType PROVIDES_WITH_LIST = JavaStubElementTypes.PROVIDES_WITH_LIST;
    public static final IElementType RECORD_COMPONENT = JavaStubElementTypes.RECORD_COMPONENT;
    public static final IElementType RECORD_HEADER = JavaStubElementTypes.RECORD_HEADER;
    public static final IElementType PERMITS_LIST = JavaStubElementTypes.PERMITS_LIST;
    public static final IElementType MEMBERS = new AnonymousClass1("MEMBERS", JavaLanguage.INSTANCE);
    public static final IElementType STATEMENTS = new AnonymousClass2("STATEMENTS", JavaLanguage.INSTANCE);
    public static final IElementType EXPRESSION_TEXT = new AnonymousClass3("EXPRESSION_TEXT", JavaLanguage.INSTANCE);
    public static final IElementType REFERENCE_TEXT = new AnonymousClass4("REFERENCE_TEXT", JavaLanguage.INSTANCE);
    public static final IElementType TYPE_WITH_DISJUNCTIONS_TEXT = new TypeTextElementType("TYPE_WITH_DISJUNCTIONS_TEXT", 16);
    public static final IElementType TYPE_WITH_CONJUNCTIONS_TEXT = new TypeTextElementType("TYPE_WITH_CONJUNCTIONS_TEXT", 32);

    public static final class JavaCompositeElementType extends IJavaElementType implements ICompositeElementType {
        private final Supplier<? extends ASTNode> myConstructor;

        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            String str = i != 4 ? "Argument for @NotNull parameter '%s' of %s.%s must not be null" : "@NotNull method %s.%s must not return null";
            Object[] objArr = new Object[i != 4 ? 3 : 2];
            if (i == 1 || i == 3) {
                objArr[0] = JvmAbi.ERASED_INLINE_CONSTRUCTOR_NAME;
            } else if (i != 4) {
                objArr[0] = "debugName";
            } else {
                objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/tree/JavaElementType$JavaCompositeElementType";
            }
            if (i != 4) {
                objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/tree/JavaElementType$JavaCompositeElementType";
            } else {
                objArr[1] = "createCompositeNode";
            }
            if (i != 4) {
                objArr[2] = "<init>";
            }
            String str2 = String.format(str, objArr);
            if (i == 4) {
                throw new IllegalStateException(str2);
            }
        }

        /* synthetic */ JavaCompositeElementType(String str, Supplier supplier, AnonymousClass1 anonymousClass1) {
            this(str, supplier);
        }

        /* synthetic */ JavaCompositeElementType(String str, Supplier supplier, boolean z, AnonymousClass1 anonymousClass1) {
            this(str, (Supplier<? extends ASTNode>) supplier, z);
        }

        /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
        private JavaCompositeElementType(String str, Supplier<? extends ASTNode> supplier) {
            this(str, supplier, false);
            if (str == null) {
                $$$reportNull$$$0(0);
            }
            if (supplier == null) {
                $$$reportNull$$$0(1);
            }
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        private JavaCompositeElementType(String str, Supplier<? extends ASTNode> supplier, boolean z) {
            super(str, z);
            if (str == null) {
                $$$reportNull$$$0(2);
            }
            if (supplier == null) {
                $$$reportNull$$$0(3);
            }
            this.myConstructor = supplier;
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.tree.ICompositeElementType
        public ASTNode createCompositeNode() {
            ASTNode aSTNode = this.myConstructor.get();
            if (aSTNode == null) {
                $$$reportNull$$$0(4);
            }
            return aSTNode;
        }
    }

    static {
        AnonymousClass1 anonymousClass1 = null;
        IMPORT_STATIC_REFERENCE = new JavaCompositeElementType("IMPORT_STATIC_REFERENCE", new Supplier() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.JavaElementType$$ExternalSyntheticLambda0
            @Override // java.util.function.Supplier
            public final Object get() {
                return JavaElementType.lambda$static$0();
            }
        }, anonymousClass1);
        TYPE = new JavaCompositeElementType("TYPE", new Supplier() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.JavaElementType$$ExternalSyntheticLambda2
            @Override // java.util.function.Supplier
            public final Object get() {
                return JavaElementType.lambda$static$1();
            }
        }, anonymousClass1);
        DIAMOND_TYPE = new JavaCompositeElementType("DIAMOND_TYPE", new Supplier() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.JavaElementType$$ExternalSyntheticLambda14
            @Override // java.util.function.Supplier
            public final Object get() {
                return JavaElementType.lambda$static$2();
            }
        }, anonymousClass1);
        boolean z = true;
        REFERENCE_PARAMETER_LIST = new JavaCompositeElementType("REFERENCE_PARAMETER_LIST", new Supplier() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.JavaElementType$$ExternalSyntheticLambda26
            @Override // java.util.function.Supplier
            public final Object get() {
                return JavaElementType.lambda$static$3();
            }
        }, z, anonymousClass1);
        JAVA_CODE_REFERENCE = new JavaCompositeElementType("JAVA_CODE_REFERENCE", new Supplier() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.JavaElementType$$ExternalSyntheticLambda38
            @Override // java.util.function.Supplier
            public final Object get() {
                return JavaElementType.lambda$static$4();
            }
        }, anonymousClass1);
        PACKAGE_STATEMENT = new JavaCompositeElementType("PACKAGE_STATEMENT", new Supplier() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.JavaElementType$$ExternalSyntheticLambda50
            @Override // java.util.function.Supplier
            public final Object get() {
                return JavaElementType.lambda$static$5();
            }
        }, anonymousClass1);
        LOCAL_VARIABLE = new JavaCompositeElementType("LOCAL_VARIABLE", new Supplier() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.JavaElementType$$ExternalSyntheticLambda53
            @Override // java.util.function.Supplier
            public final Object get() {
                return JavaElementType.lambda$static$6();
            }
        }, anonymousClass1);
        REFERENCE_EXPRESSION = new JavaCompositeElementType("REFERENCE_EXPRESSION", new Supplier() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.JavaElementType$$ExternalSyntheticLambda54
            @Override // java.util.function.Supplier
            public final Object get() {
                return JavaElementType.lambda$static$7();
            }
        }, anonymousClass1);
        THIS_EXPRESSION = new JavaCompositeElementType("THIS_EXPRESSION", new Supplier() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.JavaElementType$$ExternalSyntheticLambda56
            @Override // java.util.function.Supplier
            public final Object get() {
                return JavaElementType.lambda$static$8();
            }
        }, anonymousClass1);
        SUPER_EXPRESSION = new JavaCompositeElementType("SUPER_EXPRESSION", new Supplier() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.JavaElementType$$ExternalSyntheticLambda57
            @Override // java.util.function.Supplier
            public final Object get() {
                return JavaElementType.lambda$static$9();
            }
        }, anonymousClass1);
        PARENTH_EXPRESSION = new JavaCompositeElementType("PARENTH_EXPRESSION", new Supplier() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.JavaElementType$$ExternalSyntheticLambda11
            @Override // java.util.function.Supplier
            public final Object get() {
                return JavaElementType.lambda$static$10();
            }
        }, anonymousClass1);
        METHOD_CALL_EXPRESSION = new JavaCompositeElementType("METHOD_CALL_EXPRESSION", new Supplier() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.JavaElementType$$ExternalSyntheticLambda22
            @Override // java.util.function.Supplier
            public final Object get() {
                return JavaElementType.lambda$static$11();
            }
        }, anonymousClass1);
        TYPE_CAST_EXPRESSION = new JavaCompositeElementType("TYPE_CAST_EXPRESSION", new Supplier() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.JavaElementType$$ExternalSyntheticLambda33
            @Override // java.util.function.Supplier
            public final Object get() {
                return JavaElementType.lambda$static$12();
            }
        }, anonymousClass1);
        PREFIX_EXPRESSION = new JavaCompositeElementType("PREFIX_EXPRESSION", new Supplier() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.JavaElementType$$ExternalSyntheticLambda44
            @Override // java.util.function.Supplier
            public final Object get() {
                return JavaElementType.lambda$static$13();
            }
        }, anonymousClass1);
        POSTFIX_EXPRESSION = new JavaCompositeElementType("POSTFIX_EXPRESSION", new Supplier() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.JavaElementType$$ExternalSyntheticLambda55
            @Override // java.util.function.Supplier
            public final Object get() {
                return JavaElementType.lambda$static$14();
            }
        }, anonymousClass1);
        BINARY_EXPRESSION = new JavaCompositeElementType("BINARY_EXPRESSION", new Supplier() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.JavaElementType$$ExternalSyntheticLambda58
            @Override // java.util.function.Supplier
            public final Object get() {
                return JavaElementType.lambda$static$15();
            }
        }, anonymousClass1);
        POLYADIC_EXPRESSION = new JavaCompositeElementType("POLYADIC_EXPRESSION", new Supplier() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.JavaElementType$$ExternalSyntheticLambda59
            @Override // java.util.function.Supplier
            public final Object get() {
                return JavaElementType.lambda$static$16();
            }
        }, anonymousClass1);
        CONDITIONAL_EXPRESSION = new JavaCompositeElementType("CONDITIONAL_EXPRESSION", new Supplier() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.JavaElementType$$ExternalSyntheticLambda60
            @Override // java.util.function.Supplier
            public final Object get() {
                return JavaElementType.lambda$static$17();
            }
        }, anonymousClass1);
        ASSIGNMENT_EXPRESSION = new JavaCompositeElementType("ASSIGNMENT_EXPRESSION", new Supplier() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.JavaElementType$$ExternalSyntheticLambda61
            @Override // java.util.function.Supplier
            public final Object get() {
                return JavaElementType.lambda$static$18();
            }
        }, anonymousClass1);
        NEW_EXPRESSION = new JavaCompositeElementType("NEW_EXPRESSION", new Supplier() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.JavaElementType$$ExternalSyntheticLambda1
            @Override // java.util.function.Supplier
            public final Object get() {
                return JavaElementType.lambda$static$19();
            }
        }, anonymousClass1);
        ARRAY_ACCESS_EXPRESSION = new JavaCompositeElementType("ARRAY_ACCESS_EXPRESSION", new Supplier() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.JavaElementType$$ExternalSyntheticLambda3
            @Override // java.util.function.Supplier
            public final Object get() {
                return JavaElementType.lambda$static$20();
            }
        }, anonymousClass1);
        ARRAY_INITIALIZER_EXPRESSION = new JavaCompositeElementType("ARRAY_INITIALIZER_EXPRESSION", new Supplier() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.JavaElementType$$ExternalSyntheticLambda4
            @Override // java.util.function.Supplier
            public final Object get() {
                return JavaElementType.lambda$static$21();
            }
        }, anonymousClass1);
        INSTANCE_OF_EXPRESSION = new JavaCompositeElementType("INSTANCE_OF_EXPRESSION", new Supplier() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.JavaElementType$$ExternalSyntheticLambda5
            @Override // java.util.function.Supplier
            public final Object get() {
                return JavaElementType.lambda$static$22();
            }
        }, anonymousClass1);
        CLASS_OBJECT_ACCESS_EXPRESSION = new JavaCompositeElementType("CLASS_OBJECT_ACCESS_EXPRESSION", new Supplier() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.JavaElementType$$ExternalSyntheticLambda6
            @Override // java.util.function.Supplier
            public final Object get() {
                return JavaElementType.lambda$static$23();
            }
        }, anonymousClass1);
        EMPTY_EXPRESSION = new JavaCompositeElementType("EMPTY_EXPRESSION", new Supplier() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.JavaElementType$$ExternalSyntheticLambda7
            @Override // java.util.function.Supplier
            public final Object get() {
                return JavaElementType.lambda$static$24();
            }
        }, z, anonymousClass1);
        EXPRESSION_LIST = new JavaCompositeElementType("EXPRESSION_LIST", new Supplier() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.JavaElementType$$ExternalSyntheticLambda8
            @Override // java.util.function.Supplier
            public final Object get() {
                return JavaElementType.lambda$static$25();
            }
        }, z, anonymousClass1);
        EMPTY_STATEMENT = new JavaCompositeElementType("EMPTY_STATEMENT", new Supplier() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.JavaElementType$$ExternalSyntheticLambda9
            @Override // java.util.function.Supplier
            public final Object get() {
                return JavaElementType.lambda$static$26();
            }
        }, anonymousClass1);
        BLOCK_STATEMENT = new JavaCompositeElementType("BLOCK_STATEMENT", new Supplier() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.JavaElementType$$ExternalSyntheticLambda10
            @Override // java.util.function.Supplier
            public final Object get() {
                return JavaElementType.lambda$static$27();
            }
        }, anonymousClass1);
        EXPRESSION_STATEMENT = new JavaCompositeElementType("EXPRESSION_STATEMENT", new Supplier() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.JavaElementType$$ExternalSyntheticLambda12
            @Override // java.util.function.Supplier
            public final Object get() {
                return JavaElementType.lambda$static$28();
            }
        }, anonymousClass1);
        EXPRESSION_LIST_STATEMENT = new JavaCompositeElementType("EXPRESSION_LIST_STATEMENT", new Supplier() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.JavaElementType$$ExternalSyntheticLambda13
            @Override // java.util.function.Supplier
            public final Object get() {
                return JavaElementType.lambda$static$29();
            }
        }, anonymousClass1);
        DECLARATION_STATEMENT = new JavaCompositeElementType("DECLARATION_STATEMENT", new Supplier() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.JavaElementType$$ExternalSyntheticLambda15
            @Override // java.util.function.Supplier
            public final Object get() {
                return JavaElementType.lambda$static$30();
            }
        }, anonymousClass1);
        IF_STATEMENT = new JavaCompositeElementType("IF_STATEMENT", new Supplier() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.JavaElementType$$ExternalSyntheticLambda16
            @Override // java.util.function.Supplier
            public final Object get() {
                return JavaElementType.lambda$static$31();
            }
        }, anonymousClass1);
        WHILE_STATEMENT = new JavaCompositeElementType("WHILE_STATEMENT", new Supplier() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.JavaElementType$$ExternalSyntheticLambda17
            @Override // java.util.function.Supplier
            public final Object get() {
                return JavaElementType.lambda$static$32();
            }
        }, anonymousClass1);
        FOR_STATEMENT = new JavaCompositeElementType("FOR_STATEMENT", new Supplier() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.JavaElementType$$ExternalSyntheticLambda18
            @Override // java.util.function.Supplier
            public final Object get() {
                return JavaElementType.lambda$static$33();
            }
        }, anonymousClass1);
        FOREACH_STATEMENT = new JavaCompositeElementType("FOREACH_STATEMENT", new Supplier() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.JavaElementType$$ExternalSyntheticLambda19
            @Override // java.util.function.Supplier
            public final Object get() {
                return JavaElementType.lambda$static$34();
            }
        }, anonymousClass1);
        DO_WHILE_STATEMENT = new JavaCompositeElementType("DO_WHILE_STATEMENT", new Supplier() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.JavaElementType$$ExternalSyntheticLambda20
            @Override // java.util.function.Supplier
            public final Object get() {
                return JavaElementType.lambda$static$35();
            }
        }, anonymousClass1);
        SWITCH_STATEMENT = new JavaCompositeElementType("SWITCH_STATEMENT", new Supplier() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.JavaElementType$$ExternalSyntheticLambda21
            @Override // java.util.function.Supplier
            public final Object get() {
                return JavaElementType.lambda$static$36();
            }
        }, anonymousClass1);
        SWITCH_EXPRESSION = new JavaCompositeElementType("SWITCH_EXPRESSION", new Supplier() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.JavaElementType$$ExternalSyntheticLambda23
            @Override // java.util.function.Supplier
            public final Object get() {
                return JavaElementType.lambda$static$37();
            }
        }, anonymousClass1);
        SWITCH_LABEL_STATEMENT = new JavaCompositeElementType("SWITCH_LABEL_STATEMENT", new Supplier() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.JavaElementType$$ExternalSyntheticLambda24
            @Override // java.util.function.Supplier
            public final Object get() {
                return JavaElementType.lambda$static$38();
            }
        }, anonymousClass1);
        SWITCH_LABELED_RULE = new JavaCompositeElementType("SWITCH_LABELED_RULE", new Supplier() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.JavaElementType$$ExternalSyntheticLambda25
            @Override // java.util.function.Supplier
            public final Object get() {
                return JavaElementType.lambda$static$39();
            }
        }, anonymousClass1);
        BREAK_STATEMENT = new JavaCompositeElementType("BREAK_STATEMENT", new Supplier() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.JavaElementType$$ExternalSyntheticLambda27
            @Override // java.util.function.Supplier
            public final Object get() {
                return JavaElementType.lambda$static$40();
            }
        }, anonymousClass1);
        YIELD_STATEMENT = new JavaCompositeElementType("YIELD_STATEMENT", new Supplier() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.JavaElementType$$ExternalSyntheticLambda28
            @Override // java.util.function.Supplier
            public final Object get() {
                return JavaElementType.lambda$static$41();
            }
        }, anonymousClass1);
        CONTINUE_STATEMENT = new JavaCompositeElementType("CONTINUE_STATEMENT", new Supplier() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.JavaElementType$$ExternalSyntheticLambda29
            @Override // java.util.function.Supplier
            public final Object get() {
                return JavaElementType.lambda$static$42();
            }
        }, anonymousClass1);
        RETURN_STATEMENT = new JavaCompositeElementType("RETURN_STATEMENT", new Supplier() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.JavaElementType$$ExternalSyntheticLambda30
            @Override // java.util.function.Supplier
            public final Object get() {
                return JavaElementType.lambda$static$43();
            }
        }, anonymousClass1);
        THROW_STATEMENT = new JavaCompositeElementType("THROW_STATEMENT", new Supplier() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.JavaElementType$$ExternalSyntheticLambda31
            @Override // java.util.function.Supplier
            public final Object get() {
                return JavaElementType.lambda$static$44();
            }
        }, anonymousClass1);
        SYNCHRONIZED_STATEMENT = new JavaCompositeElementType("SYNCHRONIZED_STATEMENT", new Supplier() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.JavaElementType$$ExternalSyntheticLambda32
            @Override // java.util.function.Supplier
            public final Object get() {
                return JavaElementType.lambda$static$45();
            }
        }, anonymousClass1);
        TRY_STATEMENT = new JavaCompositeElementType("TRY_STATEMENT", new Supplier() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.JavaElementType$$ExternalSyntheticLambda34
            @Override // java.util.function.Supplier
            public final Object get() {
                return JavaElementType.lambda$static$46();
            }
        }, anonymousClass1);
        RESOURCE_LIST = new JavaCompositeElementType("RESOURCE_LIST", new Supplier() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.JavaElementType$$ExternalSyntheticLambda35
            @Override // java.util.function.Supplier
            public final Object get() {
                return JavaElementType.lambda$static$47();
            }
        }, anonymousClass1);
        RESOURCE_VARIABLE = new JavaCompositeElementType("RESOURCE_VARIABLE", new Supplier() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.JavaElementType$$ExternalSyntheticLambda36
            @Override // java.util.function.Supplier
            public final Object get() {
                return JavaElementType.lambda$static$48();
            }
        }, anonymousClass1);
        RESOURCE_EXPRESSION = new JavaCompositeElementType("RESOURCE_EXPRESSION", new Supplier() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.JavaElementType$$ExternalSyntheticLambda37
            @Override // java.util.function.Supplier
            public final Object get() {
                return JavaElementType.lambda$static$49();
            }
        }, anonymousClass1);
        CATCH_SECTION = new JavaCompositeElementType("CATCH_SECTION", new Supplier() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.JavaElementType$$ExternalSyntheticLambda39
            @Override // java.util.function.Supplier
            public final Object get() {
                return JavaElementType.lambda$static$50();
            }
        }, anonymousClass1);
        LABELED_STATEMENT = new JavaCompositeElementType("LABELED_STATEMENT", new Supplier() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.JavaElementType$$ExternalSyntheticLambda40
            @Override // java.util.function.Supplier
            public final Object get() {
                return JavaElementType.lambda$static$51();
            }
        }, anonymousClass1);
        ASSERT_STATEMENT = new JavaCompositeElementType("ASSERT_STATEMENT", new Supplier() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.JavaElementType$$ExternalSyntheticLambda41
            @Override // java.util.function.Supplier
            public final Object get() {
                return JavaElementType.lambda$static$52();
            }
        }, anonymousClass1);
        ANNOTATION_ARRAY_INITIALIZER = new JavaCompositeElementType("ANNOTATION_ARRAY_INITIALIZER", new Supplier() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.JavaElementType$$ExternalSyntheticLambda42
            @Override // java.util.function.Supplier
            public final Object get() {
                return JavaElementType.lambda$static$53();
            }
        }, anonymousClass1);
        RECEIVER_PARAMETER = new JavaCompositeElementType("RECEIVER", new Supplier() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.JavaElementType$$ExternalSyntheticLambda43
            @Override // java.util.function.Supplier
            public final Object get() {
                return JavaElementType.lambda$static$54();
            }
        }, anonymousClass1);
        MODULE_REFERENCE = new JavaCompositeElementType("MODULE_REFERENCE", new Supplier() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.JavaElementType$$ExternalSyntheticLambda45
            @Override // java.util.function.Supplier
            public final Object get() {
                return JavaElementType.lambda$static$55();
            }
        }, anonymousClass1);
        TYPE_TEST_PATTERN = new JavaCompositeElementType("TYPE_TEST_PATTERN", new Supplier() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.JavaElementType$$ExternalSyntheticLambda46
            @Override // java.util.function.Supplier
            public final Object get() {
                return JavaElementType.lambda$static$56();
            }
        }, anonymousClass1);
        PATTERN_VARIABLE = new JavaCompositeElementType("PATTERN_VARIABLE", new Supplier() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.JavaElementType$$ExternalSyntheticLambda47
            @Override // java.util.function.Supplier
            public final Object get() {
                return JavaElementType.lambda$static$57();
            }
        }, anonymousClass1);
        PARENTHESIZED_PATTERN = new JavaCompositeElementType("PARENTHESIZED_PATTERN", new Supplier() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.JavaElementType$$ExternalSyntheticLambda48
            @Override // java.util.function.Supplier
            public final Object get() {
                return JavaElementType.lambda$static$58();
            }
        }, anonymousClass1);
        GUARDED_PATTERN = new JavaCompositeElementType("GUARDED_PATTERN", new Supplier() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.JavaElementType$$ExternalSyntheticLambda49
            @Override // java.util.function.Supplier
            public final Object get() {
                return JavaElementType.lambda$static$59();
            }
        }, anonymousClass1);
        DEFAULT_CASE_LABEL_ELEMENT = new JavaCompositeElementType("DEFAULT_CASE_LABEL_ELEMENT", new Supplier() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.JavaElementType$$ExternalSyntheticLambda51
            @Override // java.util.function.Supplier
            public final Object get() {
                return JavaElementType.lambda$static$60();
            }
        }, anonymousClass1);
        CASE_LABEL_ELEMENT_LIST = new JavaCompositeElementType("CASE_LABEL_ELEMENT_LIST", new Supplier() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.JavaElementType$$ExternalSyntheticLambda52
            @Override // java.util.function.Supplier
            public final Object get() {
                return JavaElementType.lambda$static$61();
            }
        }, anonymousClass1);
        CODE_BLOCK = new ICodeBlockElementType(anonymousClass1);
        DUMMY_ELEMENT = new JavaDummyElementType(anonymousClass1);
    }

    static /* synthetic */ ASTNode lambda$static$0() {
        return new PsiImportStaticReferenceElementImpl();
    }

    static /* synthetic */ ASTNode lambda$static$1() {
        return new PsiTypeElementImpl();
    }

    static /* synthetic */ ASTNode lambda$static$2() {
        return new PsiDiamondTypeElementImpl();
    }

    static /* synthetic */ ASTNode lambda$static$3() {
        return new PsiReferenceParameterListImpl();
    }

    static /* synthetic */ ASTNode lambda$static$4() {
        return new PsiJavaCodeReferenceElementImpl();
    }

    static /* synthetic */ ASTNode lambda$static$5() {
        return new PsiPackageStatementImpl();
    }

    static /* synthetic */ ASTNode lambda$static$6() {
        return new PsiLocalVariableImpl();
    }

    static /* synthetic */ ASTNode lambda$static$7() {
        return new PsiReferenceExpressionImpl();
    }

    static /* synthetic */ ASTNode lambda$static$8() {
        return new PsiThisExpressionImpl();
    }

    static /* synthetic */ ASTNode lambda$static$9() {
        return new PsiSuperExpressionImpl();
    }

    static /* synthetic */ ASTNode lambda$static$10() {
        return new PsiParenthesizedExpressionImpl();
    }

    static /* synthetic */ ASTNode lambda$static$11() {
        return new PsiMethodCallExpressionImpl();
    }

    static /* synthetic */ ASTNode lambda$static$12() {
        return new PsiTypeCastExpressionImpl();
    }

    static /* synthetic */ ASTNode lambda$static$13() {
        return new PsiPrefixExpressionImpl();
    }

    static /* synthetic */ ASTNode lambda$static$14() {
        return new PsiPostfixExpressionImpl();
    }

    static /* synthetic */ ASTNode lambda$static$15() {
        return new PsiBinaryExpressionImpl();
    }

    static /* synthetic */ ASTNode lambda$static$16() {
        return new PsiPolyadicExpressionImpl();
    }

    static /* synthetic */ ASTNode lambda$static$17() {
        return new PsiConditionalExpressionImpl();
    }

    static /* synthetic */ ASTNode lambda$static$18() {
        return new PsiAssignmentExpressionImpl();
    }

    static /* synthetic */ ASTNode lambda$static$19() {
        return new PsiNewExpressionImpl();
    }

    static /* synthetic */ ASTNode lambda$static$20() {
        return new PsiArrayAccessExpressionImpl();
    }

    static /* synthetic */ ASTNode lambda$static$21() {
        return new PsiArrayInitializerExpressionImpl();
    }

    static /* synthetic */ ASTNode lambda$static$22() {
        return new PsiInstanceOfExpressionImpl();
    }

    static /* synthetic */ ASTNode lambda$static$23() {
        return new PsiClassObjectAccessExpressionImpl();
    }

    static /* synthetic */ ASTNode lambda$static$24() {
        return new PsiEmptyExpressionImpl();
    }

    static /* synthetic */ ASTNode lambda$static$25() {
        return new PsiExpressionListImpl();
    }

    static /* synthetic */ ASTNode lambda$static$26() {
        return new PsiEmptyStatementImpl();
    }

    static /* synthetic */ ASTNode lambda$static$27() {
        return new PsiBlockStatementImpl();
    }

    static /* synthetic */ ASTNode lambda$static$28() {
        return new PsiExpressionStatementImpl();
    }

    static /* synthetic */ ASTNode lambda$static$29() {
        return new PsiExpressionListStatementImpl();
    }

    static /* synthetic */ ASTNode lambda$static$30() {
        return new PsiDeclarationStatementImpl();
    }

    static /* synthetic */ ASTNode lambda$static$31() {
        return new PsiIfStatementImpl();
    }

    static /* synthetic */ ASTNode lambda$static$32() {
        return new PsiWhileStatementImpl();
    }

    static /* synthetic */ ASTNode lambda$static$33() {
        return new PsiForStatementImpl();
    }

    static /* synthetic */ ASTNode lambda$static$34() {
        return new PsiForeachStatementImpl();
    }

    static /* synthetic */ ASTNode lambda$static$35() {
        return new PsiDoWhileStatementImpl();
    }

    static /* synthetic */ ASTNode lambda$static$36() {
        return new PsiSwitchStatementImpl();
    }

    static /* synthetic */ ASTNode lambda$static$37() {
        return new PsiSwitchExpressionImpl();
    }

    static /* synthetic */ ASTNode lambda$static$38() {
        return new PsiSwitchLabelStatementImpl();
    }

    static /* synthetic */ ASTNode lambda$static$39() {
        return new PsiSwitchLabeledRuleStatementImpl();
    }

    static /* synthetic */ ASTNode lambda$static$40() {
        return new PsiBreakStatementImpl();
    }

    static /* synthetic */ ASTNode lambda$static$41() {
        return new PsiYieldStatementImpl();
    }

    static /* synthetic */ ASTNode lambda$static$42() {
        return new PsiContinueStatementImpl();
    }

    static /* synthetic */ ASTNode lambda$static$43() {
        return new PsiReturnStatementImpl();
    }

    static /* synthetic */ ASTNode lambda$static$44() {
        return new PsiThrowStatementImpl();
    }

    static /* synthetic */ ASTNode lambda$static$45() {
        return new PsiSynchronizedStatementImpl();
    }

    static /* synthetic */ ASTNode lambda$static$46() {
        return new PsiTryStatementImpl();
    }

    static /* synthetic */ ASTNode lambda$static$47() {
        return new PsiResourceListImpl();
    }

    static /* synthetic */ ASTNode lambda$static$48() {
        return new PsiResourceVariableImpl();
    }

    static /* synthetic */ ASTNode lambda$static$49() {
        return new PsiResourceExpressionImpl();
    }

    static /* synthetic */ ASTNode lambda$static$50() {
        return new PsiCatchSectionImpl();
    }

    static /* synthetic */ ASTNode lambda$static$51() {
        return new PsiLabeledStatementImpl();
    }

    static /* synthetic */ ASTNode lambda$static$52() {
        return new PsiAssertStatementImpl();
    }

    static /* synthetic */ ASTNode lambda$static$53() {
        return new PsiArrayInitializerMemberValueImpl();
    }

    static /* synthetic */ ASTNode lambda$static$54() {
        return new PsiReceiverParameterImpl();
    }

    static /* synthetic */ ASTNode lambda$static$55() {
        return new PsiJavaModuleReferenceElementImpl();
    }

    static /* synthetic */ ASTNode lambda$static$56() {
        return new PsiTypeTestPatternImpl();
    }

    static /* synthetic */ ASTNode lambda$static$57() {
        return new PsiPatternVariableImpl();
    }

    static /* synthetic */ ASTNode lambda$static$58() {
        return new PsiParenthesizedPatternImpl();
    }

    static /* synthetic */ ASTNode lambda$static$59() {
        return new PsiGuardedPatternImpl();
    }

    static /* synthetic */ ASTNode lambda$static$60() {
        return new PsiDefaultLabelElementImpl();
    }

    static /* synthetic */ ASTNode lambda$static$61() {
        return new PsiCaseLabelElementListImpl();
    }

    public static final class ICodeBlockElementType extends IErrorCounterReparseableElementType implements ICompositeElementType, ILightLazyParseableElementType {
        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", "chameleon", "org/jetbrains/kotlin/com/intellij/psi/impl/source/tree/JavaElementType$ICodeBlockElementType", "parseContents"));
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.tree.ILazyParseableElementTypeBase
        public boolean reuseCollapsedTokens() {
            return true;
        }

        /* synthetic */ ICodeBlockElementType(AnonymousClass1 anonymousClass1) {
            this();
        }

        private ICodeBlockElementType() {
            super("CODE_BLOCK", JavaLanguage.INSTANCE);
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.tree.ILazyParseableElementType
        public ASTNode createNode(CharSequence charSequence) {
            return new PsiCodeBlockImpl(charSequence);
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.tree.ICompositeElementType
        public ASTNode createCompositeNode() {
            return new PsiCodeBlockImpl(null);
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.tree.ILazyParseableElementType, org.jetbrains.kotlin.com.intellij.psi.tree.ILazyParseableElementTypeBase
        public ASTNode parseContents(ASTNode aSTNode) {
            if (aSTNode == null) {
                $$$reportNull$$$0(0);
            }
            PsiBuilder psiBuilderCreateBuilder = JavaParserUtil.createBuilder(aSTNode);
            JavaParser.INSTANCE.getStatementParser().parseCodeBlockDeep(psiBuilderCreateBuilder, true);
            return psiBuilderCreateBuilder.getTreeBuilt().getFirstChildNode();
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.tree.ILightLazyParseableElementType
        public FlyweightCapableTreeStructure<LighterASTNode> parseContents(LighterLazyParseableNode lighterLazyParseableNode) {
            PsiBuilder psiBuilderCreateBuilder = JavaParserUtil.createBuilder(lighterLazyParseableNode);
            JavaParser.INSTANCE.getStatementParser().parseCodeBlockDeep(psiBuilderCreateBuilder, true);
            return psiBuilderCreateBuilder.getLightTree();
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.tree.IErrorCounterReparseableElementType
        public int getErrorsCount(CharSequence charSequence, Language language, Project project) {
            return PsiBuilderUtil.hasProperBraceBalance(charSequence, JavaParserDefinition.createLexer(LanguageLevel.HIGHEST), JavaTokenType.LBRACE, JavaTokenType.RBRACE) ? 0 : Integer.MIN_VALUE;
        }
    }

    /* JADX INFO: renamed from: org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.JavaElementType$1, reason: invalid class name */
    class AnonymousClass1 extends ICodeFragmentElementType {
        private final JavaParserUtil.ParserWrapper myParser;

        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", "chameleon", "org/jetbrains/kotlin/com/intellij/psi/impl/source/tree/JavaElementType$1", "parseContents"));
        }

        AnonymousClass1(String str, Language language) {
            super(str, language);
            this.myParser = new JavaParserUtil.ParserWrapper() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.JavaElementType$1$$ExternalSyntheticLambda0
                @Override // org.jetbrains.kotlin.com.intellij.lang.java.parser.JavaParserUtil.ParserWrapper
                public final void parse(PsiBuilder psiBuilder) {
                    JavaParser.INSTANCE.getDeclarationParser().parseClassBodyDeclarations(psiBuilder, false);
                }
            };
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.tree.IFileElementType, org.jetbrains.kotlin.com.intellij.psi.tree.ILazyParseableElementType, org.jetbrains.kotlin.com.intellij.psi.tree.ILazyParseableElementTypeBase
        public ASTNode parseContents(ASTNode aSTNode) {
            if (aSTNode == null) {
                $$$reportNull$$$0(0);
            }
            return JavaParserUtil.parseFragment(aSTNode, this.myParser);
        }
    }

    /* JADX INFO: renamed from: org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.JavaElementType$2, reason: invalid class name */
    class AnonymousClass2 extends ICodeFragmentElementType {
        private final JavaParserUtil.ParserWrapper myParser;

        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", "chameleon", "org/jetbrains/kotlin/com/intellij/psi/impl/source/tree/JavaElementType$2", "parseContents"));
        }

        AnonymousClass2(String str, Language language) {
            super(str, language);
            this.myParser = new JavaParserUtil.ParserWrapper() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.JavaElementType$2$$ExternalSyntheticLambda0
                @Override // org.jetbrains.kotlin.com.intellij.lang.java.parser.JavaParserUtil.ParserWrapper
                public final void parse(PsiBuilder psiBuilder) {
                    JavaParser.INSTANCE.getStatementParser().parseStatements(psiBuilder);
                }
            };
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.tree.IFileElementType, org.jetbrains.kotlin.com.intellij.psi.tree.ILazyParseableElementType, org.jetbrains.kotlin.com.intellij.psi.tree.ILazyParseableElementTypeBase
        public ASTNode parseContents(ASTNode aSTNode) {
            if (aSTNode == null) {
                $$$reportNull$$$0(0);
            }
            return JavaParserUtil.parseFragment(aSTNode, this.myParser);
        }
    }

    /* JADX INFO: renamed from: org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.JavaElementType$3, reason: invalid class name */
    class AnonymousClass3 extends ICodeFragmentElementType {
        private final JavaParserUtil.ParserWrapper myParser;

        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", "chameleon", "org/jetbrains/kotlin/com/intellij/psi/impl/source/tree/JavaElementType$3", "parseContents"));
        }

        AnonymousClass3(String str, Language language) {
            super(str, language);
            this.myParser = new JavaParserUtil.ParserWrapper() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.JavaElementType$3$$ExternalSyntheticLambda0
                @Override // org.jetbrains.kotlin.com.intellij.lang.java.parser.JavaParserUtil.ParserWrapper
                public final void parse(PsiBuilder psiBuilder) {
                    JavaParser.INSTANCE.getExpressionParser().parse(psiBuilder);
                }
            };
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.tree.IFileElementType, org.jetbrains.kotlin.com.intellij.psi.tree.ILazyParseableElementType, org.jetbrains.kotlin.com.intellij.psi.tree.ILazyParseableElementTypeBase
        public ASTNode parseContents(ASTNode aSTNode) {
            if (aSTNode == null) {
                $$$reportNull$$$0(0);
            }
            return JavaParserUtil.parseFragment(aSTNode, this.myParser);
        }
    }

    /* JADX INFO: renamed from: org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.JavaElementType$4, reason: invalid class name */
    class AnonymousClass4 extends ICodeFragmentElementType {
        private final JavaParserUtil.ParserWrapper myParser;

        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", "chameleon", "org/jetbrains/kotlin/com/intellij/psi/impl/source/tree/JavaElementType$4", "parseContents"));
        }

        AnonymousClass4(String str, Language language) {
            super(str, language);
            this.myParser = new JavaParserUtil.ParserWrapper() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.JavaElementType$4$$ExternalSyntheticLambda0
                @Override // org.jetbrains.kotlin.com.intellij.lang.java.parser.JavaParserUtil.ParserWrapper
                public final void parse(PsiBuilder psiBuilder) {
                    JavaParser.INSTANCE.getReferenceParser().parseJavaCodeReference(psiBuilder, false, true, false, false);
                }
            };
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.tree.IFileElementType, org.jetbrains.kotlin.com.intellij.psi.tree.ILazyParseableElementType, org.jetbrains.kotlin.com.intellij.psi.tree.ILazyParseableElementTypeBase
        public ASTNode parseContents(ASTNode aSTNode) {
            if (aSTNode == null) {
                $$$reportNull$$$0(0);
            }
            return JavaParserUtil.parseFragment(aSTNode, this.myParser);
        }
    }

    public static class TypeTextElementType extends ICodeFragmentElementType {
        private final int myFlags;
        private final JavaParserUtil.ParserWrapper myParser;

        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", "chameleon", "org/jetbrains/kotlin/com/intellij/psi/impl/source/tree/JavaElementType$TypeTextElementType", "parseContents"));
        }

        TypeTextElementType(String str, int i) {
            super(str, JavaLanguage.INSTANCE);
            this.myParser = new JavaParserUtil.ParserWrapper() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.JavaElementType.TypeTextElementType.1
                @Override // org.jetbrains.kotlin.com.intellij.lang.java.parser.JavaParserUtil.ParserWrapper
                public void parse(PsiBuilder psiBuilder) {
                    JavaParser.INSTANCE.getReferenceParser().parseType(psiBuilder, TypeTextElementType.this.myFlags | 7);
                }
            };
            this.myFlags = i;
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.tree.IFileElementType, org.jetbrains.kotlin.com.intellij.psi.tree.ILazyParseableElementType, org.jetbrains.kotlin.com.intellij.psi.tree.ILazyParseableElementTypeBase
        public ASTNode parseContents(ASTNode aSTNode) {
            if (aSTNode == null) {
                $$$reportNull$$$0(0);
            }
            return JavaParserUtil.parseFragment(aSTNode, this.myParser);
        }
    }

    public static final class JavaDummyElementType extends ILazyParseableElementType implements ICompositeElementType {
        static final /* synthetic */ boolean $assertionsDisabled = false;

        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", "chameleon", "org/jetbrains/kotlin/com/intellij/psi/impl/source/tree/JavaElementType$JavaDummyElementType", "parseContents"));
        }

        /* synthetic */ JavaDummyElementType(AnonymousClass1 anonymousClass1) {
            this();
        }

        private JavaDummyElementType() {
            super("DUMMY_ELEMENT", JavaLanguage.INSTANCE);
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.tree.ICompositeElementType
        public ASTNode createCompositeNode() {
            return new CompositePsiElement(this) { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.JavaElementType.JavaDummyElementType.1
            };
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.tree.ILazyParseableElementType, org.jetbrains.kotlin.com.intellij.psi.tree.ILazyParseableElementTypeBase
        public ASTNode parseContents(ASTNode aSTNode) {
            if (aSTNode == null) {
                $$$reportNull$$$0(0);
            }
            JavaDummyElement javaDummyElement = (JavaDummyElement) aSTNode;
            return JavaParserUtil.parseFragment(aSTNode, javaDummyElement.getParser(), javaDummyElement.consumeAll(), javaDummyElement.getLanguageLevel());
        }
    }
}
