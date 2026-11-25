package org.jetbrains.kotlin.js.translate.expression;

import org.jetbrains.kotlin.builtins.KotlinBuiltIns;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.com.intellij.util.SmartList;
import org.jetbrains.kotlin.js.backend.ast.JsConditional;
import org.jetbrains.kotlin.js.backend.ast.JsExpression;
import org.jetbrains.kotlin.js.backend.ast.JsInvocation;
import org.jetbrains.kotlin.js.backend.ast.JsNameRef;
import org.jetbrains.kotlin.js.backend.ast.JsNullLiteral;
import org.jetbrains.kotlin.js.backend.ast.JsNumberLiteral;
import org.jetbrains.kotlin.js.backend.ast.JsStringLiteral;
import org.jetbrains.kotlin.js.descriptorUtils.DescriptorUtilsKt;
import org.jetbrains.kotlin.js.patterns.NamePredicate;
import org.jetbrains.kotlin.js.translate.context.TemporaryVariable;
import org.jetbrains.kotlin.js.translate.context.TranslationContext;
import org.jetbrains.kotlin.js.translate.general.AbstractTranslator;
import org.jetbrains.kotlin.js.translate.general.Translation;
import org.jetbrains.kotlin.js.translate.intrinsic.functions.factories.TopLevelFIF;
import org.jetbrains.kotlin.js.translate.utils.JsAstUtils;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.psi.KtEscapeStringTemplateEntry;
import org.jetbrains.kotlin.psi.KtExpression;
import org.jetbrains.kotlin.psi.KtLiteralStringTemplateEntry;
import org.jetbrains.kotlin.psi.KtStringTemplateEntry;
import org.jetbrains.kotlin.psi.KtStringTemplateEntryWithExpression;
import org.jetbrains.kotlin.psi.KtStringTemplateExpression;
import org.jetbrains.kotlin.psi.KtVisitorVoid;
import org.jetbrains.kotlin.types.KotlinType;

/* JADX INFO: loaded from: classes5.dex */
public final class StringTemplateTranslator extends AbstractTranslator {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private final KtStringTemplateEntry[] expressionEntries;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = i != 4 ? "Argument for @NotNull parameter '%s' of %s.%s must not be null" : "@NotNull method %s.%s must not return null";
        Object[] objArr = new Object[i != 4 ? 3 : 2];
        if (i == 1 || i == 3) {
            objArr[0] = "context";
        } else if (i != 4) {
            objArr[0] = "expression";
        } else {
            objArr[0] = "org/jetbrains/kotlin/js/translate/expression/StringTemplateTranslator";
        }
        if (i != 4) {
            objArr[1] = "org/jetbrains/kotlin/js/translate/expression/StringTemplateTranslator";
        } else {
            objArr[1] = "translate";
        }
        if (i == 2 || i == 3) {
            objArr[2] = "<init>";
        } else if (i != 4) {
            objArr[2] = "translate";
        }
        String str2 = String.format(str, objArr);
        if (i == 4) {
            throw new IllegalStateException(str2);
        }
    }

    public static JsExpression translate(KtStringTemplateExpression ktStringTemplateExpression, TranslationContext translationContext) {
        if (ktStringTemplateExpression == null) {
            $$$reportNull$$$0(0);
        }
        if (translationContext == null) {
            $$$reportNull$$$0(1);
        }
        return new StringTemplateTranslator(ktStringTemplateExpression, translationContext).translate();
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    private StringTemplateTranslator(KtStringTemplateExpression ktStringTemplateExpression, TranslationContext translationContext) {
        super(translationContext);
        if (ktStringTemplateExpression == null) {
            $$$reportNull$$$0(2);
        }
        if (translationContext == null) {
            $$$reportNull$$$0(3);
        }
        this.expressionEntries = ktStringTemplateExpression.getEntries();
    }

    private JsExpression translate() {
        EntryVisitor entryVisitor = new EntryVisitor();
        for (KtStringTemplateEntry ktStringTemplateEntry : this.expressionEntries) {
            ktStringTemplateEntry.accept(entryVisitor);
        }
        JsExpression resultingExpression = entryVisitor.getResultingExpression();
        if (resultingExpression == null) {
            $$$reportNull$$$0(4);
        }
        return resultingExpression;
    }

    private final class EntryVisitor extends KtVisitorVoid {
        static final /* synthetic */ boolean $assertionsDisabled = false;
        private JsExpression resultingExpression;

        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            String str = i != 6 ? "Argument for @NotNull parameter '%s' of %s.%s must not be null" : "@NotNull method %s.%s must not return null";
            Object[] objArr = new Object[i != 6 ? 3 : 2];
            switch (i) {
                case 1:
                case 3:
                case 4:
                    objArr[0] = "entry";
                    break;
                case 2:
                    objArr[0] = ModuleXmlParser.TYPE;
                    break;
                case 5:
                    objArr[0] = "text";
                    break;
                case 6:
                    objArr[0] = "org/jetbrains/kotlin/js/translate/expression/StringTemplateTranslator$EntryVisitor";
                    break;
                default:
                    objArr[0] = "expression";
                    break;
            }
            if (i != 6) {
                objArr[1] = "org/jetbrains/kotlin/js/translate/expression/StringTemplateTranslator$EntryVisitor";
            } else {
                objArr[1] = "getResultingExpression";
            }
            switch (i) {
                case 1:
                    objArr[2] = "visitStringTemplateEntryWithExpression";
                    break;
                case 2:
                    objArr[2] = "mustCallToString";
                    break;
                case 3:
                    objArr[2] = "visitLiteralStringTemplateEntry";
                    break;
                case 4:
                    objArr[2] = "visitEscapeStringTemplateEntry";
                    break;
                case 5:
                    objArr[2] = "appendText";
                    break;
                case 6:
                    break;
                default:
                    objArr[2] = "append";
                    break;
            }
            String str2 = String.format(str, objArr);
            if (i == 6) {
                throw new IllegalStateException(str2);
            }
        }

        private EntryVisitor() {
            this.resultingExpression = null;
        }

        void append(JsExpression jsExpression) {
            if (jsExpression == null) {
                $$$reportNull$$$0(0);
            }
            JsExpression jsExpression2 = this.resultingExpression;
            if (jsExpression2 == null) {
                this.resultingExpression = jsExpression;
            } else {
                this.resultingExpression = JsAstUtils.sum(jsExpression2, jsExpression);
            }
        }

        @Override // org.jetbrains.kotlin.psi.KtVisitorVoid
        public void visitStringTemplateEntryWithExpression(KtStringTemplateEntryWithExpression ktStringTemplateEntryWithExpression) {
            if (ktStringTemplateEntryWithExpression == null) {
                $$$reportNull$$$0(1);
            }
            KtExpression expression = ktStringTemplateEntryWithExpression.getExpression();
            JsExpression jsExpressionTranslateAsExpression = Translation.translateAsExpression(expression, StringTemplateTranslator.this.context());
            KotlinType type = StringTemplateTranslator.this.context().bindingContext().getType(expression);
            if (type != null && KotlinBuiltIns.isCharOrNullableChar(type)) {
                if (type.getIsMarkedNullable()) {
                    TemporaryVariable temporaryVariableDeclareTemporary = StringTemplateTranslator.this.context().declareTemporary(jsExpressionTranslateAsExpression, ktStringTemplateEntryWithExpression);
                    append(new JsConditional(JsAstUtils.equality(temporaryVariableDeclareTemporary.assignmentExpression(), new JsNullLiteral()), new JsNullLiteral(), JsAstUtils.charToString(temporaryVariableDeclareTemporary.reference())));
                    return;
                } else {
                    append(JsAstUtils.charToString(jsExpressionTranslateAsExpression));
                    return;
                }
            }
            if (jsExpressionTranslateAsExpression instanceof JsNumberLiteral) {
                append(new JsStringLiteral(jsExpressionTranslateAsExpression.toString()));
                return;
            }
            if (type == null || type.getIsMarkedNullable()) {
                append(TopLevelFIF.TO_STRING.apply((JsExpression) null, new SmartList(jsExpressionTranslateAsExpression), StringTemplateTranslator.this.context()));
            } else if (mustCallToString(type)) {
                append(new JsInvocation(new JsNameRef("toString", jsExpressionTranslateAsExpression), new JsExpression[0]));
            } else {
                append(jsExpressionTranslateAsExpression);
            }
        }

        private boolean mustCallToString(KotlinType kotlinType) {
            if (kotlinType == null) {
                $$$reportNull$$$0(2);
            }
            Name nameIfStandardType = DescriptorUtilsKt.getNameIfStandardType(kotlinType);
            if (KotlinBuiltIns.isAny(kotlinType) || KotlinBuiltIns.isComparable(kotlinType) || KotlinBuiltIns.isNumber(kotlinType) || KotlinBuiltIns.isLong(kotlinType)) {
                return true;
            }
            return (nameIfStandardType == null || !NamePredicate.STRING.test(nameIfStandardType)) && this.resultingExpression == null;
        }

        @Override // org.jetbrains.kotlin.psi.KtVisitorVoid
        public void visitLiteralStringTemplateEntry(KtLiteralStringTemplateEntry ktLiteralStringTemplateEntry) {
            if (ktLiteralStringTemplateEntry == null) {
                $$$reportNull$$$0(3);
            }
            appendText(ktLiteralStringTemplateEntry.getText());
        }

        @Override // org.jetbrains.kotlin.psi.KtVisitorVoid
        public void visitEscapeStringTemplateEntry(KtEscapeStringTemplateEntry ktEscapeStringTemplateEntry) {
            if (ktEscapeStringTemplateEntry == null) {
                $$$reportNull$$$0(4);
            }
            appendText(ktEscapeStringTemplateEntry.getUnescapedValue());
        }

        private void appendText(String str) {
            if (str == null) {
                $$$reportNull$$$0(5);
            }
            append(new JsStringLiteral(str));
        }

        public JsExpression getResultingExpression() {
            JsExpression jsExpression = this.resultingExpression;
            if (jsExpression == null) {
                $$$reportNull$$$0(6);
            }
            return jsExpression;
        }
    }
}
