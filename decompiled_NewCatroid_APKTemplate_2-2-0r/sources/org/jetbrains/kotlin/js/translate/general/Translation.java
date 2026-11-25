package org.jetbrains.kotlin.js.translate.general;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import org.jetbrains.kotlin.builtins.KotlinBuiltIns;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.descriptors.ModuleDescriptor;
import org.jetbrains.kotlin.idea.MainFunctionDetector;
import org.jetbrains.kotlin.js.backend.ast.JsArrayLiteral;
import org.jetbrains.kotlin.js.backend.ast.JsBlock;
import org.jetbrains.kotlin.js.backend.ast.JsBooleanLiteral;
import org.jetbrains.kotlin.js.backend.ast.JsDoubleLiteral;
import org.jetbrains.kotlin.js.backend.ast.JsExpression;
import org.jetbrains.kotlin.js.backend.ast.JsIntLiteral;
import org.jetbrains.kotlin.js.backend.ast.JsNameRef;
import org.jetbrains.kotlin.js.backend.ast.JsNode;
import org.jetbrains.kotlin.js.backend.ast.JsNullLiteral;
import org.jetbrains.kotlin.js.backend.ast.JsProgramFragment;
import org.jetbrains.kotlin.js.backend.ast.JsStatement;
import org.jetbrains.kotlin.js.backend.ast.JsStringLiteral;
import org.jetbrains.kotlin.js.backend.ast.metadata.MetadataProperties;
import org.jetbrains.kotlin.js.config.JsConfig;
import org.jetbrains.kotlin.js.facade.MainCallParameters;
import org.jetbrains.kotlin.js.facade.TranslationUnit;
import org.jetbrains.kotlin.js.facade.exceptions.TranslationException;
import org.jetbrains.kotlin.js.facade.exceptions.TranslationRuntimeException;
import org.jetbrains.kotlin.js.facade.exceptions.UnsupportedFeatureException;
import org.jetbrains.kotlin.js.naming.NameSuggestion;
import org.jetbrains.kotlin.js.sourceMap.SourceFilePathResolver;
import org.jetbrains.kotlin.js.translate.callTranslator.CallTranslator;
import org.jetbrains.kotlin.js.translate.context.StaticContext;
import org.jetbrains.kotlin.js.translate.context.TemporaryVariable;
import org.jetbrains.kotlin.js.translate.context.TranslationContext;
import org.jetbrains.kotlin.js.translate.declaration.FileDeclarationVisitor;
import org.jetbrains.kotlin.js.translate.expression.ExpressionVisitor;
import org.jetbrains.kotlin.js.translate.expression.PatternTranslator;
import org.jetbrains.kotlin.js.translate.reference.ReferenceTranslator;
import org.jetbrains.kotlin.js.translate.test.JSTestGenerator;
import org.jetbrains.kotlin.js.translate.utils.AnnotationsUtils;
import org.jetbrains.kotlin.js.translate.utils.BindingUtils;
import org.jetbrains.kotlin.js.translate.utils.JsAstUtils;
import org.jetbrains.kotlin.js.translate.utils.PsiUtils;
import org.jetbrains.kotlin.js.translate.utils.TranslationUtils;
import org.jetbrains.kotlin.js.translate.utils.mutator.AssignToExpressionMutator;
import org.jetbrains.kotlin.js.translate.utils.mutator.LastExpressionMutator;
import org.jetbrains.kotlin.name.FqNameUnsafe;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.protobuf.CodedInputStream;
import org.jetbrains.kotlin.psi.KtDeclaration;
import org.jetbrains.kotlin.psi.KtExpression;
import org.jetbrains.kotlin.psi.KtFile;
import org.jetbrains.kotlin.psi.KtSimpleNameExpression;
import org.jetbrains.kotlin.resolve.BindingTrace;
import org.jetbrains.kotlin.resolve.DescriptorUtils;
import org.jetbrains.kotlin.resolve.bindingContextUtil.BindingContextUtilsKt;
import org.jetbrains.kotlin.resolve.constants.CompileTimeConstant;
import org.jetbrains.kotlin.resolve.constants.ConstantValue;
import org.jetbrains.kotlin.resolve.constants.NullValue;
import org.jetbrains.kotlin.resolve.constants.UByteValue;
import org.jetbrains.kotlin.resolve.constants.UIntValue;
import org.jetbrains.kotlin.resolve.constants.ULongValue;
import org.jetbrains.kotlin.resolve.constants.UShortValue;
import org.jetbrains.kotlin.resolve.constants.UnsignedValueConstant;
import org.jetbrains.kotlin.resolve.constants.evaluate.ConstantExpressionEvaluator;
import org.jetbrains.kotlin.serialization.js.ast.JsAstProtoBuf;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.TypeUtils;
import org.jetbrains.kotlin.utils.ExceptionUtilsKt;

/* JADX INFO: loaded from: classes5.dex */
public final class Translation {
    static final /* synthetic */ boolean $assertionsDisabled = false;

    /* JADX WARN: Code duplicated, block: B:10:0x0018 A[FALL_THROUGH] */
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str;
        int i2;
        if (i != 1 && i != 19 && i != 33 && i != 36) {
            switch (i) {
                default:
                    switch (i) {
                        case 25:
                        case 26:
                        case 27:
                            break;
                        default:
                            str = "Argument for @NotNull parameter '%s' of %s.%s must not be null";
                            break;
                    }
                case 7:
                case 8:
                case 9:
                case 10:
                case 11:
                    str = "@NotNull method %s.%s must not return null";
                    break;
            }
        } else {
            str = "@NotNull method %s.%s must not return null";
        }
        if (i != 1 && i != 19 && i != 33 && i != 36) {
            switch (i) {
                case 7:
                case 8:
                case 9:
                case 10:
                case 11:
                    i2 = 2;
                    break;
                default:
                    switch (i) {
                        case 25:
                        case 26:
                        case 27:
                            i2 = 2;
                            break;
                        default:
                            i2 = 3;
                            break;
                    }
                    break;
            }
        } else {
            i2 = 2;
        }
        Object[] objArr = new Object[i2];
        switch (i) {
            case 1:
            case 7:
            case 8:
            case 9:
            case 10:
            case 11:
            case 19:
            case 25:
            case 26:
            case 27:
            case 33:
            case 36:
                objArr[0] = "org/jetbrains/kotlin/js/translate/general/Translation";
                break;
            case 2:
            case 4:
            case 13:
            case 20:
            case 22:
            case 28:
            case 30:
            case 34:
                objArr[0] = "expression";
                break;
            case 3:
            case 5:
            case 14:
            case 16:
            case 18:
            case 21:
            case 23:
            case 29:
            case 31:
            case 35:
            case 49:
            case 52:
            case 55:
            default:
                objArr[0] = "context";
                break;
            case 6:
            case 24:
            case 32:
                objArr[0] = "block";
                break;
            case 12:
                objArr[0] = "compileTimeValue";
                break;
            case 15:
                objArr[0] = "constant";
                break;
            case 17:
                objArr[0] = "unsignedConstant";
                break;
            case 37:
            case 43:
                objArr[0] = "bindingTrace";
                break;
            case 38:
            case 44:
                objArr[0] = "units";
                break;
            case 39:
            case 45:
            case 59:
                objArr[0] = "mainCallParameters";
                break;
            case 40:
            case 46:
            case 57:
                objArr[0] = "moduleDescriptor";
                break;
            case 41:
            case 47:
            case 56:
                objArr[0] = "config";
                break;
            case 42:
            case 48:
                objArr[0] = "sourceFilePathResolver";
                break;
            case 50:
            case 53:
                objArr[0] = "file";
                break;
            case 51:
            case 54:
            case 58:
                objArr[0] = "fileMemberScope";
                break;
        }
        if (i == 1) {
            objArr[1] = "patternTranslator";
        } else if (i == 19) {
            objArr[1] = "doTranslateExpression";
        } else if (i == 33) {
            objArr[1] = "translateAsStatement";
        } else if (i != 36) {
            switch (i) {
                case 7:
                case 8:
                case 9:
                case 10:
                case 11:
                    objArr[1] = "translateExpression";
                    break;
                default:
                    switch (i) {
                        case 25:
                        case 26:
                        case 27:
                            objArr[1] = "translateAsExpression";
                            break;
                        default:
                            objArr[1] = "org/jetbrains/kotlin/js/translate/general/Translation";
                            break;
                    }
                    break;
            }
        } else {
            objArr[1] = "translateAsStatementAndMergeInBlockIfNeeded";
        }
        switch (i) {
            case 1:
            case 7:
            case 8:
            case 9:
            case 10:
            case 11:
            case 19:
            case 25:
            case 26:
            case 27:
            case 33:
            case 36:
                break;
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
                objArr[2] = "translateExpression";
                break;
            case 12:
            case 13:
            case 14:
                objArr[2] = "translateConstant";
                break;
            case 15:
            case 16:
                objArr[2] = "translateConstantWithoutType";
                break;
            case 17:
            case 18:
                objArr[2] = "translateUnsignedConstant";
                break;
            case 20:
            case 21:
            case 22:
            case 23:
            case 24:
                objArr[2] = "translateAsExpression";
                break;
            case 28:
            case 29:
            case 30:
            case 31:
            case 32:
                objArr[2] = "translateAsStatement";
                break;
            case 34:
            case 35:
                objArr[2] = "translateAsStatementAndMergeInBlockIfNeeded";
                break;
            case 37:
            case 38:
            case 39:
            case 40:
            case 41:
            case 42:
                objArr[2] = "generateAst";
                break;
            case 43:
            case 44:
            case 45:
            case 46:
            case 47:
            case 48:
                objArr[2] = "doGenerateAst";
                break;
            case 49:
            case 50:
            case 51:
                objArr[2] = "translateFile";
                break;
            case 52:
            case 53:
            case 54:
                objArr[2] = "mayBeGenerateTests";
                break;
            case 55:
            case 56:
            case 57:
            case 58:
            case 59:
                objArr[2] = "maybeGenerateCallToMain";
                break;
            default:
                objArr[2] = "patternTranslator";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i != 1 && i != 19 && i != 33 && i != 36) {
            switch (i) {
                case 7:
                case 8:
                case 9:
                case 10:
                case 11:
                    break;
                default:
                    switch (i) {
                        case 25:
                        case 26:
                        case 27:
                            break;
                        default:
                            throw new IllegalArgumentException(str2);
                    }
                    break;
            }
        }
        throw new IllegalStateException(str2);
    }

    private Translation() {
    }

    public static PatternTranslator patternTranslator(TranslationContext translationContext) {
        if (translationContext == null) {
            $$$reportNull$$$0(0);
        }
        PatternTranslator patternTranslatorNewInstance = PatternTranslator.newInstance(translationContext);
        if (patternTranslatorNewInstance == null) {
            $$$reportNull$$$0(1);
        }
        return patternTranslatorNewInstance;
    }

    public static JsNode translateExpression(KtExpression ktExpression, TranslationContext translationContext) {
        if (ktExpression == null) {
            $$$reportNull$$$0(2);
        }
        if (translationContext == null) {
            $$$reportNull$$$0(3);
        }
        return translateExpression(ktExpression, translationContext, translationContext.dynamicContext().jsBlock());
    }

    public static JsNode translateExpression(KtExpression ktExpression, TranslationContext translationContext, JsBlock jsBlock) {
        KotlinType type;
        JsExpression jsExpressionTranslateConstant;
        DeclarationDescriptor descriptorForReferenceExpression;
        if (ktExpression == null) {
            $$$reportNull$$$0(4);
        }
        if (translationContext == null) {
            $$$reportNull$$$0(5);
        }
        if (jsBlock == null) {
            $$$reportNull$$$0(6);
        }
        JsExpression aliasForExpression = translationContext.aliasingContext().getAliasForExpression(ktExpression);
        if (aliasForExpression != null) {
            if (aliasForExpression == null) {
                $$$reportNull$$$0(7);
            }
            return aliasForExpression;
        }
        CompileTimeConstant<?> constant = ConstantExpressionEvaluator.getConstant(ktExpression, translationContext.bindingContext());
        if (constant != null && !constant.getUsesNonConstValAsConstant() && (type = translationContext.bindingContext().getType(ktExpression)) != null && ((KotlinBuiltIns.isLong(type) || KotlinBuiltIns.isInt(type)) && (jsExpressionTranslateConstant = translateConstant(constant, ktExpression, translationContext)) != null)) {
            jsExpressionTranslateConstant.setSource(ktExpression);
            if (KotlinBuiltIns.isLong(type)) {
                KtSimpleNameExpression simpleName = PsiUtils.getSimpleName(ktExpression);
                if (simpleName != null && (descriptorForReferenceExpression = BindingUtils.getDescriptorForReferenceExpression(translationContext.bindingContext(), simpleName)) != null) {
                    JsExpression jsExpressionDeclareConstantValue = translationContext.declareConstantValue(descriptorForReferenceExpression, simpleName, jsExpressionTranslateConstant);
                    if (jsExpressionDeclareConstantValue == null) {
                        $$$reportNull$$$0(8);
                    }
                    return jsExpressionDeclareConstantValue;
                }
                String strSanitizeName = NameSuggestion.sanitizeName("L" + constant.getValue(type).toString());
                JsExpression jsExpressionDeclareConstantValue2 = translationContext.declareConstantValue(strSanitizeName, "constant:" + strSanitizeName, jsExpressionTranslateConstant, null);
                if (jsExpressionDeclareConstantValue2 == null) {
                    $$$reportNull$$$0(9);
                }
                return jsExpressionDeclareConstantValue2;
            }
            if (KotlinBuiltIns.isInt(type)) {
                if (jsExpressionTranslateConstant == null) {
                    $$$reportNull$$$0(10);
                }
                return jsExpressionTranslateConstant;
            }
        }
        TranslationContext translationContextInnerBlock = translationContext.innerBlock();
        JsNode jsNodeDoTranslateExpression = doTranslateExpression(ktExpression, translationContextInnerBlock);
        translationContext.moveVarsFrom(translationContextInnerBlock);
        jsBlock.getStatements().addAll(translationContextInnerBlock.dynamicContext().jsBlock().getStatements());
        if (jsNodeDoTranslateExpression == null) {
            $$$reportNull$$$0(11);
        }
        return jsNodeDoTranslateExpression;
    }

    public static JsExpression translateConstant(CompileTimeConstant compileTimeConstant, KtExpression ktExpression, TranslationContext translationContext) {
        if (compileTimeConstant == null) {
            $$$reportNull$$$0(12);
        }
        if (ktExpression == null) {
            $$$reportNull$$$0(13);
        }
        if (translationContext == null) {
            $$$reportNull$$$0(14);
        }
        KotlinType type = translationContext.bindingContext().getType(ktExpression);
        JsExpression jsExpressionTranslateConstantWithoutType = translateConstantWithoutType(compileTimeConstant.toConstantValue(type != null ? type : TypeUtils.NO_EXPECTED_TYPE), translationContext);
        if (jsExpressionTranslateConstantWithoutType != null) {
            MetadataProperties.setType(jsExpressionTranslateConstantWithoutType, type);
        }
        return jsExpressionTranslateConstantWithoutType;
    }

    private static JsExpression translateConstantWithoutType(ConstantValue<?> constantValue, TranslationContext translationContext) {
        if (constantValue == null) {
            $$$reportNull$$$0(15);
        }
        if (translationContext == null) {
            $$$reportNull$$$0(16);
        }
        if (constantValue instanceof NullValue) {
            return new JsNullLiteral();
        }
        if (constantValue instanceof UnsignedValueConstant) {
            return translateUnsignedConstant((UnsignedValueConstant) constantValue, translationContext);
        }
        Object value = constantValue.getValue();
        if ((value instanceof Integer) || (value instanceof Short) || (value instanceof Byte)) {
            return new JsIntLiteral(((Number) value).intValue());
        }
        if (value instanceof Long) {
            return JsAstUtils.newLong(((Long) value).longValue());
        }
        if (value instanceof Float) {
            float fFloatValue = ((Float) value).floatValue();
            return new JsDoubleLiteral((Float.isInfinite(fFloatValue) || Float.isNaN(fFloatValue)) ? fFloatValue : Double.parseDouble(Float.toString(fFloatValue)));
        }
        if (value instanceof Number) {
            return new JsDoubleLiteral(((Number) value).doubleValue());
        }
        if (value instanceof Boolean) {
            return new JsBooleanLiteral(((Boolean) value).booleanValue());
        }
        if (value instanceof String) {
            return new JsStringLiteral((String) value);
        }
        if (value instanceof Character) {
            return new JsIntLiteral(((Character) value).charValue());
        }
        return null;
    }

    private static JsExpression translateUnsignedConstant(UnsignedValueConstant<?> unsignedValueConstant, TranslationContext translationContext) {
        if (unsignedValueConstant == null) {
            $$$reportNull$$$0(17);
        }
        if (translationContext == null) {
            $$$reportNull$$$0(18);
        }
        if (unsignedValueConstant instanceof UByteValue) {
            return JsAstUtils.byteToUByte(((UByteValue) unsignedValueConstant).getValue().byteValue(), translationContext);
        }
        if (unsignedValueConstant instanceof UShortValue) {
            return JsAstUtils.shortToUShort(((UShortValue) unsignedValueConstant).getValue().shortValue(), translationContext);
        }
        if (unsignedValueConstant instanceof UIntValue) {
            return JsAstUtils.intToUInt(((UIntValue) unsignedValueConstant).getValue().intValue(), translationContext);
        }
        if (unsignedValueConstant instanceof ULongValue) {
            return JsAstUtils.longToULong(JsAstUtils.newLong(((ULongValue) unsignedValueConstant).getValue().longValue()), translationContext);
        }
        return null;
    }

    private static JsNode doTranslateExpression(KtExpression ktExpression, TranslationContext translationContext) {
        try {
            JsNode jsNode = (JsNode) ktExpression.accept(new ExpressionVisitor(), translationContext);
            if (jsNode == null) {
                $$$reportNull$$$0(19);
            }
            return jsNode;
        } catch (AssertionError e) {
            e = e;
            throw new TranslationRuntimeException(ktExpression, e);
        } catch (TranslationRuntimeException e2) {
            throw e2;
        } catch (RuntimeException e3) {
            e = e3;
            throw new TranslationRuntimeException(ktExpression, e);
        }
    }

    public static JsExpression translateAsExpression(KtExpression ktExpression, TranslationContext translationContext) {
        if (ktExpression == null) {
            $$$reportNull$$$0(20);
        }
        if (translationContext == null) {
            $$$reportNull$$$0(21);
        }
        return translateAsExpression(ktExpression, translationContext, translationContext.dynamicContext().jsBlock());
    }

    public static JsExpression translateAsExpression(KtExpression ktExpression, TranslationContext translationContext, JsBlock jsBlock) {
        if (ktExpression == null) {
            $$$reportNull$$$0(22);
        }
        if (translationContext == null) {
            $$$reportNull$$$0(23);
        }
        if (jsBlock == null) {
            $$$reportNull$$$0(24);
        }
        JsNode jsNodeTranslateExpression = translateExpression(ktExpression, translationContext, jsBlock);
        if (jsNodeTranslateExpression instanceof JsExpression) {
            JsExpression jsExpressionCoerce = (JsExpression) jsNodeTranslateExpression;
            KotlinType type = translationContext.bindingContext().getType(ktExpression);
            if (MetadataProperties.getType(jsExpressionCoerce) == null) {
                MetadataProperties.setType(jsExpressionCoerce, type);
            } else if (type != null) {
                jsExpressionCoerce = TranslationUtils.coerce(translationContext, jsExpressionCoerce, type);
            }
            if (jsExpressionCoerce == null) {
                $$$reportNull$$$0(25);
            }
            return jsExpressionCoerce;
        }
        if (BindingContextUtilsKt.isUsedAsExpression(ktExpression, translationContext.bindingContext())) {
            TemporaryVariable temporaryVariableDeclareTemporary = translationContext.declareTemporary(null, ktExpression);
            jsBlock.getStatements().add(LastExpressionMutator.mutateLastExpression(jsNodeTranslateExpression, new AssignToExpressionMutator(temporaryVariableDeclareTemporary.reference())));
            JsNameRef jsNameRefReference = temporaryVariableDeclareTemporary.reference();
            MetadataProperties.setType(jsNameRefReference, translationContext.bindingContext().getType(ktExpression));
            if (jsNameRefReference == null) {
                $$$reportNull$$$0(26);
            }
            return jsNameRefReference;
        }
        jsBlock.getStatements().add(JsAstUtils.convertToStatement(jsNodeTranslateExpression));
        JsExpression jsExpressionSource = new JsNullLiteral().source((Object) ktExpression);
        if (jsExpressionSource == null) {
            $$$reportNull$$$0(27);
        }
        return jsExpressionSource;
    }

    public static JsStatement translateAsStatement(KtExpression ktExpression, TranslationContext translationContext) {
        if (ktExpression == null) {
            $$$reportNull$$$0(28);
        }
        if (translationContext == null) {
            $$$reportNull$$$0(29);
        }
        return translateAsStatement(ktExpression, translationContext, translationContext.dynamicContext().jsBlock());
    }

    public static JsStatement translateAsStatement(KtExpression ktExpression, TranslationContext translationContext, JsBlock jsBlock) {
        if (ktExpression == null) {
            $$$reportNull$$$0(30);
        }
        if (translationContext == null) {
            $$$reportNull$$$0(31);
        }
        if (jsBlock == null) {
            $$$reportNull$$$0(32);
        }
        JsStatement jsStatementConvertToStatement = JsAstUtils.convertToStatement(translateExpression(ktExpression, translationContext, jsBlock));
        if (jsStatementConvertToStatement == null) {
            $$$reportNull$$$0(33);
        }
        return jsStatementConvertToStatement;
    }

    public static JsStatement translateAsStatementAndMergeInBlockIfNeeded(KtExpression ktExpression, TranslationContext translationContext) {
        if (ktExpression == null) {
            $$$reportNull$$$0(34);
        }
        if (translationContext == null) {
            $$$reportNull$$$0(35);
        }
        JsBlock jsBlock = new JsBlock();
        JsStatement jsStatementMergeStatementInBlockIfNeeded = JsAstUtils.mergeStatementInBlockIfNeeded(JsAstUtils.convertToStatement(translateExpression(ktExpression, translationContext, jsBlock)), jsBlock);
        if (jsStatementMergeStatementInBlockIfNeeded == null) {
            $$$reportNull$$$0(36);
        }
        return jsStatementMergeStatementInBlockIfNeeded;
    }

    public static AstGenerationResult generateAst(BindingTrace bindingTrace, Collection<TranslationUnit> collection, MainCallParameters mainCallParameters, ModuleDescriptor moduleDescriptor, JsConfig jsConfig, SourceFilePathResolver sourceFilePathResolver) throws TranslationException {
        if (bindingTrace == null) {
            $$$reportNull$$$0(37);
        }
        if (collection == null) {
            $$$reportNull$$$0(38);
        }
        if (mainCallParameters == null) {
            $$$reportNull$$$0(39);
        }
        if (moduleDescriptor == null) {
            $$$reportNull$$$0(40);
        }
        if (jsConfig == null) {
            $$$reportNull$$$0(41);
        }
        if (sourceFilePathResolver == null) {
            $$$reportNull$$$0(42);
        }
        try {
            return doGenerateAst(bindingTrace, collection, mainCallParameters, moduleDescriptor, jsConfig, sourceFilePathResolver);
        } catch (UnsupportedOperationException e) {
            throw new UnsupportedFeatureException("Unsupported feature used.", e);
        } catch (Throwable th) {
            throw ExceptionUtilsKt.rethrow(th);
        }
    }

    private static AstGenerationResult doGenerateAst(BindingTrace bindingTrace, Collection<TranslationUnit> collection, MainCallParameters mainCallParameters, ModuleDescriptor moduleDescriptor, JsConfig jsConfig, SourceFilePathResolver sourceFilePathResolver) throws IOException {
        if (bindingTrace == null) {
            $$$reportNull$$$0(43);
        }
        if (collection == null) {
            $$$reportNull$$$0(44);
        }
        if (mainCallParameters == null) {
            $$$reportNull$$$0(45);
        }
        if (moduleDescriptor == null) {
            $$$reportNull$$$0(46);
        }
        if (jsConfig == null) {
            $$$reportNull$$$0(47);
        }
        if (sourceFilePathResolver == null) {
            $$$reportNull$$$0(48);
        }
        HashMap map = new HashMap();
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        for (TranslationUnit translationUnit : collection) {
            if (translationUnit instanceof TranslationUnit.SourceFile) {
                KtFile file = ((TranslationUnit.SourceFile) translationUnit).getFile();
                StaticContext staticContext = new StaticContext(bindingTrace, jsConfig, moduleDescriptor, sourceFilePathResolver, file.getPackageFqName().asString());
                TranslationContext translationContextRootContext = TranslationContext.rootContext(staticContext);
                ArrayList arrayList = new ArrayList();
                translateFile(translationContextRootContext, file, arrayList);
                JsProgramFragment fragment = staticContext.getFragment();
                Iterator<String> it = staticContext.getInlineFunctionTags().iterator();
                while (it.hasNext()) {
                    map.put(it.next(), translationUnit);
                }
                NormalizeImportTagsKt.normalizeImportTags(fragment);
                fragment.setTests(mayBeGenerateTests(translationContextRootContext, file, arrayList));
                fragment.setMainFunction(maybeGenerateCallToMain(translationContextRootContext, jsConfig, moduleDescriptor, arrayList, mainCallParameters));
                linkedHashMap.put(file, new SourceFileTranslationResult(fragment, staticContext.getInlineFunctionTags(), arrayList));
            } else if (translationUnit instanceof TranslationUnit.BinaryAst) {
                Iterator<String> it2 = JsAstProtoBuf.InlineData.parseFrom(CodedInputStream.newInstance(((TranslationUnit.BinaryAst) translationUnit).getInlineData())).getInlineFunctionTagsList().iterator();
                while (it2.hasNext()) {
                    map.put(it2.next(), translationUnit);
                }
            }
        }
        return new AstGenerationResult(collection, linkedHashMap, map, moduleDescriptor, jsConfig);
    }

    private static void translateFile(TranslationContext translationContext, KtFile ktFile, List<DeclarationDescriptor> list) {
        if (translationContext == null) {
            $$$reportNull$$$0(49);
        }
        if (ktFile == null) {
            $$$reportNull$$$0(50);
        }
        if (list == null) {
            $$$reportNull$$$0(51);
        }
        FileDeclarationVisitor fileDeclarationVisitor = new FileDeclarationVisitor(translationContext);
        try {
            for (KtDeclaration ktDeclaration : ktFile.getDeclarations()) {
                DeclarationDescriptor descriptorForElement = BindingUtils.getDescriptorForElement(translationContext.bindingContext(), ktDeclaration);
                list.add(descriptorForElement);
                if (!AnnotationsUtils.isPredefinedObject(descriptorForElement)) {
                    ktDeclaration.accept(fileDeclarationVisitor, translationContext);
                }
            }
        } catch (AssertionError e) {
            e = e;
            throw new TranslationRuntimeException(ktFile, e);
        } catch (TranslationRuntimeException e2) {
            throw e2;
        } catch (RuntimeException e3) {
            e = e3;
            throw new TranslationRuntimeException(ktFile, e);
        }
    }

    private static JsStatement mayBeGenerateTests(TranslationContext translationContext, KtFile ktFile, List<DeclarationDescriptor> list) {
        if (translationContext == null) {
            $$$reportNull$$$0(52);
        }
        if (ktFile == null) {
            $$$reportNull$$$0(53);
        }
        if (list == null) {
            $$$reportNull$$$0(54);
        }
        return new JSTestGenerator(translationContext).generateTestCalls(ktFile, list);
    }

    private static JsStatement maybeGenerateCallToMain(TranslationContext translationContext, JsConfig jsConfig, ModuleDescriptor moduleDescriptor, List<DeclarationDescriptor> list, MainCallParameters mainCallParameters) {
        if (translationContext == null) {
            $$$reportNull$$$0(55);
        }
        if (jsConfig == null) {
            $$$reportNull$$$0(56);
        }
        if (moduleDescriptor == null) {
            $$$reportNull$$$0(57);
        }
        if (list == null) {
            $$$reportNull$$$0(58);
        }
        if (mainCallParameters == null) {
            $$$reportNull$$$0(59);
        }
        if (!mainCallParameters.shouldBeGenerated()) {
            return null;
        }
        MainFunctionDetector mainFunctionDetector = new MainFunctionDetector(translationContext.bindingContext(), jsConfig.getLanguageVersionSettings());
        FunctionDescriptor functionDescriptor = null;
        for (DeclarationDescriptor declarationDescriptor : list) {
            if (mainFunctionDetector.isMain(declarationDescriptor)) {
                functionDescriptor = (FunctionDescriptor) declarationDescriptor;
            }
        }
        if (functionDescriptor == null) {
            return null;
        }
        int size = functionDescriptor.getValueParameters().size();
        ArrayList arrayList = new ArrayList();
        if (size != 0) {
            arrayList.add(new JsArrayLiteral(JsAstUtils.toStringLiteralList(mainCallParameters.arguments())));
        }
        if (functionDescriptor.isSuspend()) {
            arrayList.add(ReferenceTranslator.translateAsValueReference(DescriptorUtils.getPropertyByName(moduleDescriptor.getPackage(new FqNameUnsafe("kotlin.coroutines.js.internal").toSafe()).getMemberScope(), Name.identifier("EmptyContinuation")), translationContext));
            arrayList.add(new JsBooleanLiteral(false));
        }
        return CallTranslator.INSTANCE.buildCall(translationContext, functionDescriptor, arrayList, null).makeStmt();
    }
}
