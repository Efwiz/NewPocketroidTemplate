package org.jetbrains.kotlin.fir.analysis.diagnostics.js;

import kotlin.Metadata;
import kotlin.jvm.internal.PropertyReference1Impl;
import kotlin.jvm.internal.Reflection;
import kotlin.properties.ReadOnlyProperty;
import kotlin.reflect.KProperty;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.diagnostics.AbstractSourceElementPositioningStrategy;
import org.jetbrains.kotlin.diagnostics.DiagnosticFactory0DelegateProvider;
import org.jetbrains.kotlin.diagnostics.DiagnosticFactory1DelegateProvider;
import org.jetbrains.kotlin.diagnostics.DiagnosticFactory2DelegateProvider;
import org.jetbrains.kotlin.diagnostics.KtDiagnosticFactory0;
import org.jetbrains.kotlin.diagnostics.KtDiagnosticFactory1;
import org.jetbrains.kotlin.diagnostics.KtDiagnosticFactory2;
import org.jetbrains.kotlin.diagnostics.Severity;
import org.jetbrains.kotlin.diagnostics.SourceElementPositioningStrategies;
import org.jetbrains.kotlin.diagnostics.rendering.RootDiagnosticRendererFactory;
import org.jetbrains.kotlin.fir.symbols.FirBasedSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirCallableSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirClassLikeSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirNamedFunctionSymbol;
import org.jetbrains.kotlin.fir.types.ConeKotlinType;
import org.jetbrains.kotlin.psi.KtAnonymousInitializer;
import org.jetbrains.kotlin.psi.KtClassOrObject;
import org.jetbrains.kotlin.psi.KtDeclaration;
import org.jetbrains.kotlin.psi.KtElement;
import org.jetbrains.kotlin.psi.KtExpression;
import org.jetbrains.kotlin.psi.KtParameter;

/* JADX INFO: compiled from: FirJsErrors.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000T\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b0\n\u0002\u0010\u000e\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b+\n\u0002\u0010\b\n\u0002\b!\n\u0002\u0018\u0002\n\u0002\b$\n\u0002\u0018\u0002\n\u0002\b\u0006\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u001b\u0010\u0003\u001a\u00020\u00048FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b\u0007\u0010\b\u001a\u0004\b\u0005\u0010\u0006R\u001b\u0010\t\u001a\u00020\u00048FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b\u000b\u0010\b\u001a\u0004\b\n\u0010\u0006R%\u0010\f\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u000e0\r8FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b\u0011\u0010\b\u001a\u0004\b\u000f\u0010\u0010R%\u0010\u0012\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u000e0\r8FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b\u0014\u0010\b\u001a\u0004\b\u0013\u0010\u0010R!\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00160\r8FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b\u0018\u0010\b\u001a\u0004\b\u0017\u0010\u0010R\u001b\u0010\u0019\u001a\u00020\u00048FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b\u001b\u0010\b\u001a\u0004\b\u001a\u0010\u0006R\u001b\u0010\u001c\u001a\u00020\u00048FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b\u001e\u0010\b\u001a\u0004\b\u001d\u0010\u0006R\u001b\u0010\u001f\u001a\u00020\u00048FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b!\u0010\b\u001a\u0004\b \u0010\u0006R\u001b\u0010\"\u001a\u00020\u00048FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b$\u0010\b\u001a\u0004\b#\u0010\u0006R\u001b\u0010%\u001a\u00020\u00048FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b'\u0010\b\u001a\u0004\b&\u0010\u0006R\u001b\u0010(\u001a\u00020\u00048FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b*\u0010\b\u001a\u0004\b)\u0010\u0006R\u001b\u0010+\u001a\u00020\u00048FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b-\u0010\b\u001a\u0004\b,\u0010\u0006R\u001b\u0010.\u001a\u00020\u00048FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b0\u0010\b\u001a\u0004\b/\u0010\u0006R\u001b\u00101\u001a\u00020\u00048FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b3\u0010\b\u001a\u0004\b2\u0010\u0006R!\u00104\u001a\b\u0012\u0004\u0012\u00020\u00160\r8FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b6\u0010\b\u001a\u0004\b5\u0010\u0010R\u001b\u00107\u001a\u00020\u00048FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b9\u0010\b\u001a\u0004\b8\u0010\u0006R\u001b\u0010:\u001a\u00020\u00048FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b<\u0010\b\u001a\u0004\b;\u0010\u0006R\u001b\u0010=\u001a\u00020\u00048FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b?\u0010\b\u001a\u0004\b>\u0010\u0006R\u001b\u0010@\u001a\u00020\u00048FX\u0086\u0084\u0002¢\u0006\f\n\u0004\bB\u0010\b\u001a\u0004\bA\u0010\u0006R\u001b\u0010C\u001a\u00020\u00048FX\u0086\u0084\u0002¢\u0006\f\n\u0004\bE\u0010\b\u001a\u0004\bD\u0010\u0006R!\u0010F\u001a\b\u0012\u0004\u0012\u00020G0\r8FX\u0086\u0084\u0002¢\u0006\f\n\u0004\bI\u0010\b\u001a\u0004\bH\u0010\u0010R!\u0010J\u001a\b\u0012\u0004\u0012\u00020\u00160\r8FX\u0086\u0084\u0002¢\u0006\f\n\u0004\bL\u0010\b\u001a\u0004\bK\u0010\u0010R/\u0010M\u001a\u0016\u0012\b\u0012\u0006\u0012\u0002\b\u00030O\u0012\b\u0012\u0006\u0012\u0002\b\u00030O0N8FX\u0086\u0084\u0002¢\u0006\f\n\u0004\bR\u0010\b\u001a\u0004\bP\u0010QR\u001b\u0010S\u001a\u00020\u00048FX\u0086\u0084\u0002¢\u0006\f\n\u0004\bU\u0010\b\u001a\u0004\bT\u0010\u0006R\u001b\u0010V\u001a\u00020\u00048FX\u0086\u0084\u0002¢\u0006\f\n\u0004\bX\u0010\b\u001a\u0004\bW\u0010\u0006R\u001b\u0010Y\u001a\u00020\u00048FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b[\u0010\b\u001a\u0004\bZ\u0010\u0006R\u001b\u0010\\\u001a\u00020\u00048FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b^\u0010\b\u001a\u0004\b]\u0010\u0006R\u001b\u0010_\u001a\u00020\u00048FX\u0086\u0084\u0002¢\u0006\f\n\u0004\ba\u0010\b\u001a\u0004\b`\u0010\u0006R\u001b\u0010b\u001a\u00020\u00048FX\u0086\u0084\u0002¢\u0006\f\n\u0004\bd\u0010\b\u001a\u0004\bc\u0010\u0006R\u001b\u0010e\u001a\u00020\u00048FX\u0086\u0084\u0002¢\u0006\f\n\u0004\bg\u0010\b\u001a\u0004\bf\u0010\u0006R\u001b\u0010h\u001a\u00020\u00048FX\u0086\u0084\u0002¢\u0006\f\n\u0004\bj\u0010\b\u001a\u0004\bi\u0010\u0006R\u001b\u0010k\u001a\u00020\u00048FX\u0086\u0084\u0002¢\u0006\f\n\u0004\bm\u0010\b\u001a\u0004\bl\u0010\u0006R!\u0010n\u001a\b\u0012\u0004\u0012\u00020\u00160\r8FX\u0086\u0084\u0002¢\u0006\f\n\u0004\bp\u0010\b\u001a\u0004\bo\u0010\u0010R\u001b\u0010q\u001a\u00020\u00048FX\u0086\u0084\u0002¢\u0006\f\n\u0004\bs\u0010\b\u001a\u0004\br\u0010\u0006R!\u0010t\u001a\b\u0012\u0004\u0012\u00020G0\r8FX\u0086\u0084\u0002¢\u0006\f\n\u0004\bv\u0010\b\u001a\u0004\bu\u0010\u0010R!\u0010w\u001a\b\u0012\u0004\u0012\u00020G0\r8FX\u0086\u0084\u0002¢\u0006\f\n\u0004\by\u0010\b\u001a\u0004\bx\u0010\u0010R'\u0010z\u001a\u000e\u0012\u0004\u0012\u00020{\u0012\u0004\u0012\u00020G0N8FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b}\u0010\b\u001a\u0004\b|\u0010QR\u001c\u0010~\u001a\u00020\u00048FX\u0086\u0084\u0002¢\u0006\r\n\u0005\b\u0080\u0001\u0010\b\u001a\u0004\b\u007f\u0010\u0006R\u001e\u0010\u0081\u0001\u001a\u00020\u00048FX\u0086\u0084\u0002¢\u0006\u000e\n\u0005\b\u0083\u0001\u0010\b\u001a\u0005\b\u0082\u0001\u0010\u0006R\u001e\u0010\u0084\u0001\u001a\u00020\u00048FX\u0086\u0084\u0002¢\u0006\u000e\n\u0005\b\u0086\u0001\u0010\b\u001a\u0005\b\u0085\u0001\u0010\u0006R\u001e\u0010\u0087\u0001\u001a\u00020\u00048FX\u0086\u0084\u0002¢\u0006\u000e\n\u0005\b\u0089\u0001\u0010\b\u001a\u0005\b\u0088\u0001\u0010\u0006R\u001e\u0010\u008a\u0001\u001a\u00020\u00048FX\u0086\u0084\u0002¢\u0006\u000e\n\u0005\b\u008c\u0001\u0010\b\u001a\u0005\b\u008b\u0001\u0010\u0006R\u001e\u0010\u008d\u0001\u001a\u00020\u00048FX\u0086\u0084\u0002¢\u0006\u000e\n\u0005\b\u008f\u0001\u0010\b\u001a\u0005\b\u008e\u0001\u0010\u0006R$\u0010\u0090\u0001\u001a\b\u0012\u0004\u0012\u00020G0\r8FX\u0086\u0084\u0002¢\u0006\u000e\n\u0005\b\u0092\u0001\u0010\b\u001a\u0005\b\u0091\u0001\u0010\u0010R*\u0010\u0093\u0001\u001a\u000e\u0012\u0004\u0012\u00020G\u0012\u0004\u0012\u00020\u00160N8FX\u0086\u0084\u0002¢\u0006\u000e\n\u0005\b\u0095\u0001\u0010\b\u001a\u0005\b\u0094\u0001\u0010QR$\u0010\u0096\u0001\u001a\b\u0012\u0004\u0012\u00020\u00160\r8FX\u0086\u0084\u0002¢\u0006\u000e\n\u0005\b\u0098\u0001\u0010\b\u001a\u0005\b\u0097\u0001\u0010\u0010R\u001e\u0010\u0099\u0001\u001a\u00020\u00048FX\u0086\u0084\u0002¢\u0006\u000e\n\u0005\b\u009b\u0001\u0010\b\u001a\u0005\b\u009a\u0001\u0010\u0006R%\u0010\u009c\u0001\u001a\t\u0012\u0005\u0012\u00030\u009d\u00010\r8FX\u0086\u0084\u0002¢\u0006\u000e\n\u0005\b\u009f\u0001\u0010\b\u001a\u0005\b\u009e\u0001\u0010\u0010R\u001e\u0010 \u0001\u001a\u00020\u00048FX\u0086\u0084\u0002¢\u0006\u000e\n\u0005\b¢\u0001\u0010\b\u001a\u0005\b¡\u0001\u0010\u0006R\u001e\u0010£\u0001\u001a\u00020\u00048FX\u0086\u0084\u0002¢\u0006\u000e\n\u0005\b¥\u0001\u0010\b\u001a\u0005\b¤\u0001\u0010\u0006R\u001e\u0010¦\u0001\u001a\u00020\u00048FX\u0086\u0084\u0002¢\u0006\u000e\n\u0005\b¨\u0001\u0010\b\u001a\u0005\b§\u0001\u0010\u0006R\u001e\u0010©\u0001\u001a\u00020\u00048FX\u0086\u0084\u0002¢\u0006\u000e\n\u0005\b«\u0001\u0010\b\u001a\u0005\bª\u0001\u0010\u0006R*\u0010¬\u0001\u001a\u000e\u0012\u0004\u0012\u00020\u0016\u0012\u0004\u0012\u00020\u00160N8FX\u0086\u0084\u0002¢\u0006\u000e\n\u0005\b®\u0001\u0010\b\u001a\u0005\b\u00ad\u0001\u0010QR\u001e\u0010¯\u0001\u001a\u00020\u00048FX\u0086\u0084\u0002¢\u0006\u000e\n\u0005\b±\u0001\u0010\b\u001a\u0005\b°\u0001\u0010\u0006R\u001e\u0010²\u0001\u001a\u00020\u00048FX\u0086\u0084\u0002¢\u0006\u000e\n\u0005\b´\u0001\u0010\b\u001a\u0005\b³\u0001\u0010\u0006R$\u0010µ\u0001\u001a\b\u0012\u0004\u0012\u00020G0\r8FX\u0086\u0084\u0002¢\u0006\u000e\n\u0005\b·\u0001\u0010\b\u001a\u0005\b¶\u0001\u0010\u0010R$\u0010¸\u0001\u001a\b\u0012\u0004\u0012\u00020G0\r8FX\u0086\u0084\u0002¢\u0006\u000e\n\u0005\bº\u0001\u0010\b\u001a\u0005\b¹\u0001\u0010\u0010R\u001e\u0010»\u0001\u001a\u00020\u00048FX\u0086\u0084\u0002¢\u0006\u000e\n\u0005\b½\u0001\u0010\b\u001a\u0005\b¼\u0001\u0010\u0006R\u001e\u0010¾\u0001\u001a\u00020\u00048FX\u0086\u0084\u0002¢\u0006\u000e\n\u0005\bÀ\u0001\u0010\b\u001a\u0005\b¿\u0001\u0010\u0006R)\u0010Á\u0001\u001a\r\u0012\t\u0012\u0007\u0012\u0002\b\u00030Â\u00010\r8FX\u0086\u0084\u0002¢\u0006\u000e\n\u0005\bÄ\u0001\u0010\b\u001a\u0005\bÃ\u0001\u0010\u0010R$\u0010Å\u0001\u001a\b\u0012\u0004\u0012\u00020G0\r8FX\u0086\u0084\u0002¢\u0006\u000e\n\u0005\bÇ\u0001\u0010\b\u001a\u0005\bÆ\u0001\u0010\u0010¨\u0006È\u0001"}, d2 = {"Lorg/jetbrains/kotlin/fir/analysis/diagnostics/js/FirJsErrors;", "", "()V", "CALL_FROM_UMD_MUST_BE_JS_MODULE_AND_JS_NON_MODULE", "Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticFactory0;", "getCALL_FROM_UMD_MUST_BE_JS_MODULE_AND_JS_NON_MODULE", "()Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticFactory0;", "CALL_FROM_UMD_MUST_BE_JS_MODULE_AND_JS_NON_MODULE$delegate", "Lkotlin/properties/ReadOnlyProperty;", "CALL_TO_DEFINED_EXTERNALLY_FROM_NON_EXTERNAL_DECLARATION", "getCALL_TO_DEFINED_EXTERNALLY_FROM_NON_EXTERNAL_DECLARATION", "CALL_TO_DEFINED_EXTERNALLY_FROM_NON_EXTERNAL_DECLARATION$delegate", "CALL_TO_JS_MODULE_WITHOUT_MODULE_SYSTEM", "Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticFactory1;", "Lorg/jetbrains/kotlin/fir/symbols/FirBasedSymbol;", "getCALL_TO_JS_MODULE_WITHOUT_MODULE_SYSTEM", "()Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticFactory1;", "CALL_TO_JS_MODULE_WITHOUT_MODULE_SYSTEM$delegate", "CALL_TO_JS_NON_MODULE_WITH_MODULE_SYSTEM", "getCALL_TO_JS_NON_MODULE_WITH_MODULE_SYSTEM", "CALL_TO_JS_NON_MODULE_WITH_MODULE_SYSTEM$delegate", "CANNOT_CHECK_FOR_EXTERNAL_INTERFACE", "Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;", "getCANNOT_CHECK_FOR_EXTERNAL_INTERFACE", "CANNOT_CHECK_FOR_EXTERNAL_INTERFACE$delegate", "DELEGATION_BY_DYNAMIC", "getDELEGATION_BY_DYNAMIC", "DELEGATION_BY_DYNAMIC$delegate", "ENUM_CLASS_IN_EXTERNAL_DECLARATION_WARNING", "getENUM_CLASS_IN_EXTERNAL_DECLARATION_WARNING", "ENUM_CLASS_IN_EXTERNAL_DECLARATION_WARNING$delegate", "EXTENSION_FUNCTION_IN_EXTERNAL_DECLARATION", "getEXTENSION_FUNCTION_IN_EXTERNAL_DECLARATION", "EXTENSION_FUNCTION_IN_EXTERNAL_DECLARATION$delegate", "EXTERNAL_ANONYMOUS_INITIALIZER", "getEXTERNAL_ANONYMOUS_INITIALIZER", "EXTERNAL_ANONYMOUS_INITIALIZER$delegate", "EXTERNAL_CLASS_CONSTRUCTOR_PROPERTY_PARAMETER", "getEXTERNAL_CLASS_CONSTRUCTOR_PROPERTY_PARAMETER", "EXTERNAL_CLASS_CONSTRUCTOR_PROPERTY_PARAMETER$delegate", "EXTERNAL_DELEGATED_CONSTRUCTOR_CALL", "getEXTERNAL_DELEGATED_CONSTRUCTOR_CALL", "EXTERNAL_DELEGATED_CONSTRUCTOR_CALL$delegate", "EXTERNAL_DELEGATION", "getEXTERNAL_DELEGATION", "EXTERNAL_DELEGATION$delegate", "EXTERNAL_ENUM_ENTRY_WITH_BODY", "getEXTERNAL_ENUM_ENTRY_WITH_BODY", "EXTERNAL_ENUM_ENTRY_WITH_BODY$delegate", "EXTERNAL_INTERFACE_AS_CLASS_LITERAL", "getEXTERNAL_INTERFACE_AS_CLASS_LITERAL", "EXTERNAL_INTERFACE_AS_CLASS_LITERAL$delegate", "EXTERNAL_INTERFACE_AS_REIFIED_TYPE_ARGUMENT", "getEXTERNAL_INTERFACE_AS_REIFIED_TYPE_ARGUMENT", "EXTERNAL_INTERFACE_AS_REIFIED_TYPE_ARGUMENT$delegate", "EXTERNAL_TYPE_EXTENDS_NON_EXTERNAL_TYPE", "getEXTERNAL_TYPE_EXTENDS_NON_EXTERNAL_TYPE", "EXTERNAL_TYPE_EXTENDS_NON_EXTERNAL_TYPE$delegate", "IMPLEMENTING_FUNCTION_INTERFACE", "getIMPLEMENTING_FUNCTION_INTERFACE", "IMPLEMENTING_FUNCTION_INTERFACE$delegate", "INLINE_CLASS_IN_EXTERNAL_DECLARATION", "getINLINE_CLASS_IN_EXTERNAL_DECLARATION", "INLINE_CLASS_IN_EXTERNAL_DECLARATION$delegate", "INLINE_CLASS_IN_EXTERNAL_DECLARATION_WARNING", "getINLINE_CLASS_IN_EXTERNAL_DECLARATION_WARNING", "INLINE_CLASS_IN_EXTERNAL_DECLARATION_WARNING$delegate", "INLINE_EXTERNAL_DECLARATION", "getINLINE_EXTERNAL_DECLARATION", "INLINE_EXTERNAL_DECLARATION$delegate", "JS_BUILTIN_NAME_CLASH", "", "getJS_BUILTIN_NAME_CLASH", "JS_BUILTIN_NAME_CLASH$delegate", "JS_EXTERNAL_ARGUMENT", "getJS_EXTERNAL_ARGUMENT", "JS_EXTERNAL_ARGUMENT$delegate", "JS_EXTERNAL_INHERITORS_ONLY", "Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticFactory2;", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirClassLikeSymbol;", "getJS_EXTERNAL_INHERITORS_ONLY", "()Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticFactory2;", "JS_EXTERNAL_INHERITORS_ONLY$delegate", "JS_MODULE_PROHIBITED_ON_NON_NATIVE", "getJS_MODULE_PROHIBITED_ON_NON_NATIVE", "JS_MODULE_PROHIBITED_ON_NON_NATIVE$delegate", "JS_MODULE_PROHIBITED_ON_VAR", "getJS_MODULE_PROHIBITED_ON_VAR", "JS_MODULE_PROHIBITED_ON_VAR$delegate", "JS_NAME_IS_NOT_ON_ALL_ACCESSORS", "getJS_NAME_IS_NOT_ON_ALL_ACCESSORS", "JS_NAME_IS_NOT_ON_ALL_ACCESSORS$delegate", "JS_NAME_ON_ACCESSOR_AND_PROPERTY", "getJS_NAME_ON_ACCESSOR_AND_PROPERTY", "JS_NAME_ON_ACCESSOR_AND_PROPERTY$delegate", "JS_NAME_ON_PRIMARY_CONSTRUCTOR_PROHIBITED", "getJS_NAME_ON_PRIMARY_CONSTRUCTOR_PROHIBITED", "JS_NAME_ON_PRIMARY_CONSTRUCTOR_PROHIBITED$delegate", "JS_NAME_PROHIBITED_FOR_EXTENSION_PROPERTY", "getJS_NAME_PROHIBITED_FOR_EXTENSION_PROPERTY", "JS_NAME_PROHIBITED_FOR_EXTENSION_PROPERTY$delegate", "JS_NAME_PROHIBITED_FOR_NAMED_NATIVE", "getJS_NAME_PROHIBITED_FOR_NAMED_NATIVE", "JS_NAME_PROHIBITED_FOR_NAMED_NATIVE$delegate", "JS_NAME_PROHIBITED_FOR_OVERRIDE", "getJS_NAME_PROHIBITED_FOR_OVERRIDE", "JS_NAME_PROHIBITED_FOR_OVERRIDE$delegate", "NAME_CONTAINS_ILLEGAL_CHARS", "getNAME_CONTAINS_ILLEGAL_CHARS", "NAME_CONTAINS_ILLEGAL_CHARS$delegate", "NATIVE_ANNOTATIONS_ALLOWED_ONLY_ON_MEMBER_OR_EXTENSION_FUN", "getNATIVE_ANNOTATIONS_ALLOWED_ONLY_ON_MEMBER_OR_EXTENSION_FUN", "NATIVE_ANNOTATIONS_ALLOWED_ONLY_ON_MEMBER_OR_EXTENSION_FUN$delegate", "NATIVE_GETTER_RETURN_TYPE_SHOULD_BE_NULLABLE", "getNATIVE_GETTER_RETURN_TYPE_SHOULD_BE_NULLABLE", "NATIVE_GETTER_RETURN_TYPE_SHOULD_BE_NULLABLE$delegate", "NATIVE_INDEXER_CAN_NOT_HAVE_DEFAULT_ARGUMENTS", "getNATIVE_INDEXER_CAN_NOT_HAVE_DEFAULT_ARGUMENTS", "NATIVE_INDEXER_CAN_NOT_HAVE_DEFAULT_ARGUMENTS$delegate", "NATIVE_INDEXER_KEY_SHOULD_BE_STRING_OR_NUMBER", "getNATIVE_INDEXER_KEY_SHOULD_BE_STRING_OR_NUMBER", "NATIVE_INDEXER_KEY_SHOULD_BE_STRING_OR_NUMBER$delegate", "NATIVE_INDEXER_WRONG_PARAMETER_COUNT", "", "getNATIVE_INDEXER_WRONG_PARAMETER_COUNT", "NATIVE_INDEXER_WRONG_PARAMETER_COUNT$delegate", "NATIVE_SETTER_WRONG_RETURN_TYPE", "getNATIVE_SETTER_WRONG_RETURN_TYPE", "NATIVE_SETTER_WRONG_RETURN_TYPE$delegate", "NESTED_CLASS_IN_EXTERNAL_INTERFACE", "getNESTED_CLASS_IN_EXTERNAL_INTERFACE", "NESTED_CLASS_IN_EXTERNAL_INTERFACE$delegate", "NESTED_EXTERNAL_DECLARATION", "getNESTED_EXTERNAL_DECLARATION", "NESTED_EXTERNAL_DECLARATION$delegate", "NESTED_JS_EXPORT", "getNESTED_JS_EXPORT", "NESTED_JS_EXPORT$delegate", "NESTED_JS_MODULE_PROHIBITED", "getNESTED_JS_MODULE_PROHIBITED", "NESTED_JS_MODULE_PROHIBITED$delegate", "NON_ABSTRACT_MEMBER_OF_EXTERNAL_INTERFACE", "getNON_ABSTRACT_MEMBER_OF_EXTERNAL_INTERFACE", "NON_ABSTRACT_MEMBER_OF_EXTERNAL_INTERFACE$delegate", "NON_CONSUMABLE_EXPORTED_IDENTIFIER", "getNON_CONSUMABLE_EXPORTED_IDENTIFIER", "NON_CONSUMABLE_EXPORTED_IDENTIFIER$delegate", "NON_EXPORTABLE_TYPE", "getNON_EXPORTABLE_TYPE", "NON_EXPORTABLE_TYPE$delegate", "NON_EXTERNAL_DECLARATION_IN_INAPPROPRIATE_FILE", "getNON_EXTERNAL_DECLARATION_IN_INAPPROPRIATE_FILE", "NON_EXTERNAL_DECLARATION_IN_INAPPROPRIATE_FILE$delegate", "OVERRIDING_EXTERNAL_FUN_WITH_OPTIONAL_PARAMS", "getOVERRIDING_EXTERNAL_FUN_WITH_OPTIONAL_PARAMS", "OVERRIDING_EXTERNAL_FUN_WITH_OPTIONAL_PARAMS$delegate", "OVERRIDING_EXTERNAL_FUN_WITH_OPTIONAL_PARAMS_WITH_FAKE", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirNamedFunctionSymbol;", "getOVERRIDING_EXTERNAL_FUN_WITH_OPTIONAL_PARAMS_WITH_FAKE", "OVERRIDING_EXTERNAL_FUN_WITH_OPTIONAL_PARAMS_WITH_FAKE$delegate", "PROPERTY_DELEGATION_BY_DYNAMIC", "getPROPERTY_DELEGATION_BY_DYNAMIC", "PROPERTY_DELEGATION_BY_DYNAMIC$delegate", "RUNTIME_ANNOTATION_NOT_SUPPORTED", "getRUNTIME_ANNOTATION_NOT_SUPPORTED", "RUNTIME_ANNOTATION_NOT_SUPPORTED$delegate", "RUNTIME_ANNOTATION_ON_EXTERNAL_DECLARATION", "getRUNTIME_ANNOTATION_ON_EXTERNAL_DECLARATION", "RUNTIME_ANNOTATION_ON_EXTERNAL_DECLARATION$delegate", "SPREAD_OPERATOR_IN_DYNAMIC_CALL", "getSPREAD_OPERATOR_IN_DYNAMIC_CALL", "SPREAD_OPERATOR_IN_DYNAMIC_CALL$delegate", "UNCHECKED_CAST_TO_EXTERNAL_INTERFACE", "getUNCHECKED_CAST_TO_EXTERNAL_INTERFACE", "UNCHECKED_CAST_TO_EXTERNAL_INTERFACE$delegate", "WRONG_BODY_OF_EXTERNAL_DECLARATION", "getWRONG_BODY_OF_EXTERNAL_DECLARATION", "WRONG_BODY_OF_EXTERNAL_DECLARATION$delegate", "WRONG_DEFAULT_VALUE_FOR_EXTERNAL_FUN_PARAMETER", "getWRONG_DEFAULT_VALUE_FOR_EXTERNAL_FUN_PARAMETER", "WRONG_DEFAULT_VALUE_FOR_EXTERNAL_FUN_PARAMETER$delegate", "WRONG_EXPORTED_DECLARATION", "getWRONG_EXPORTED_DECLARATION", "WRONG_EXPORTED_DECLARATION$delegate", "WRONG_EXTERNAL_DECLARATION", "getWRONG_EXTERNAL_DECLARATION", "WRONG_EXTERNAL_DECLARATION$delegate", "WRONG_INITIALIZER_OF_EXTERNAL_DECLARATION", "getWRONG_INITIALIZER_OF_EXTERNAL_DECLARATION", "WRONG_INITIALIZER_OF_EXTERNAL_DECLARATION$delegate", "WRONG_JS_QUALIFIER", "getWRONG_JS_QUALIFIER", "WRONG_JS_QUALIFIER$delegate", "WRONG_MULTIPLE_INHERITANCE", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirCallableSymbol;", "getWRONG_MULTIPLE_INHERITANCE", "WRONG_MULTIPLE_INHERITANCE$delegate", "WRONG_OPERATION_WITH_DYNAMIC", "getWRONG_OPERATION_WITH_DYNAMIC", "WRONG_OPERATION_WITH_DYNAMIC$delegate", "checkers.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirJsErrors {
    static final /* synthetic */ KProperty<Object>[] $$delegatedProperties;

    /* JADX INFO: renamed from: CALL_FROM_UMD_MUST_BE_JS_MODULE_AND_JS_NON_MODULE$delegate, reason: from kotlin metadata */
    private static final ReadOnlyProperty CALL_FROM_UMD_MUST_BE_JS_MODULE_AND_JS_NON_MODULE;

    /* JADX INFO: renamed from: CALL_TO_DEFINED_EXTERNALLY_FROM_NON_EXTERNAL_DECLARATION$delegate, reason: from kotlin metadata */
    private static final ReadOnlyProperty CALL_TO_DEFINED_EXTERNALLY_FROM_NON_EXTERNAL_DECLARATION;

    /* JADX INFO: renamed from: CALL_TO_JS_MODULE_WITHOUT_MODULE_SYSTEM$delegate, reason: from kotlin metadata */
    private static final ReadOnlyProperty CALL_TO_JS_MODULE_WITHOUT_MODULE_SYSTEM;

    /* JADX INFO: renamed from: CALL_TO_JS_NON_MODULE_WITH_MODULE_SYSTEM$delegate, reason: from kotlin metadata */
    private static final ReadOnlyProperty CALL_TO_JS_NON_MODULE_WITH_MODULE_SYSTEM;

    /* JADX INFO: renamed from: CANNOT_CHECK_FOR_EXTERNAL_INTERFACE$delegate, reason: from kotlin metadata */
    private static final ReadOnlyProperty CANNOT_CHECK_FOR_EXTERNAL_INTERFACE;

    /* JADX INFO: renamed from: DELEGATION_BY_DYNAMIC$delegate, reason: from kotlin metadata */
    private static final ReadOnlyProperty DELEGATION_BY_DYNAMIC;

    /* JADX INFO: renamed from: ENUM_CLASS_IN_EXTERNAL_DECLARATION_WARNING$delegate, reason: from kotlin metadata */
    private static final ReadOnlyProperty ENUM_CLASS_IN_EXTERNAL_DECLARATION_WARNING;

    /* JADX INFO: renamed from: EXTENSION_FUNCTION_IN_EXTERNAL_DECLARATION$delegate, reason: from kotlin metadata */
    private static final ReadOnlyProperty EXTENSION_FUNCTION_IN_EXTERNAL_DECLARATION;

    /* JADX INFO: renamed from: EXTERNAL_ANONYMOUS_INITIALIZER$delegate, reason: from kotlin metadata */
    private static final ReadOnlyProperty EXTERNAL_ANONYMOUS_INITIALIZER;

    /* JADX INFO: renamed from: EXTERNAL_CLASS_CONSTRUCTOR_PROPERTY_PARAMETER$delegate, reason: from kotlin metadata */
    private static final ReadOnlyProperty EXTERNAL_CLASS_CONSTRUCTOR_PROPERTY_PARAMETER;

    /* JADX INFO: renamed from: EXTERNAL_DELEGATED_CONSTRUCTOR_CALL$delegate, reason: from kotlin metadata */
    private static final ReadOnlyProperty EXTERNAL_DELEGATED_CONSTRUCTOR_CALL;

    /* JADX INFO: renamed from: EXTERNAL_DELEGATION$delegate, reason: from kotlin metadata */
    private static final ReadOnlyProperty EXTERNAL_DELEGATION;

    /* JADX INFO: renamed from: EXTERNAL_ENUM_ENTRY_WITH_BODY$delegate, reason: from kotlin metadata */
    private static final ReadOnlyProperty EXTERNAL_ENUM_ENTRY_WITH_BODY;

    /* JADX INFO: renamed from: EXTERNAL_INTERFACE_AS_CLASS_LITERAL$delegate, reason: from kotlin metadata */
    private static final ReadOnlyProperty EXTERNAL_INTERFACE_AS_CLASS_LITERAL;

    /* JADX INFO: renamed from: EXTERNAL_INTERFACE_AS_REIFIED_TYPE_ARGUMENT$delegate, reason: from kotlin metadata */
    private static final ReadOnlyProperty EXTERNAL_INTERFACE_AS_REIFIED_TYPE_ARGUMENT;

    /* JADX INFO: renamed from: EXTERNAL_TYPE_EXTENDS_NON_EXTERNAL_TYPE$delegate, reason: from kotlin metadata */
    private static final ReadOnlyProperty EXTERNAL_TYPE_EXTENDS_NON_EXTERNAL_TYPE;

    /* JADX INFO: renamed from: IMPLEMENTING_FUNCTION_INTERFACE$delegate, reason: from kotlin metadata */
    private static final ReadOnlyProperty IMPLEMENTING_FUNCTION_INTERFACE;

    /* JADX INFO: renamed from: INLINE_CLASS_IN_EXTERNAL_DECLARATION$delegate, reason: from kotlin metadata */
    private static final ReadOnlyProperty INLINE_CLASS_IN_EXTERNAL_DECLARATION;

    /* JADX INFO: renamed from: INLINE_CLASS_IN_EXTERNAL_DECLARATION_WARNING$delegate, reason: from kotlin metadata */
    private static final ReadOnlyProperty INLINE_CLASS_IN_EXTERNAL_DECLARATION_WARNING;

    /* JADX INFO: renamed from: INLINE_EXTERNAL_DECLARATION$delegate, reason: from kotlin metadata */
    private static final ReadOnlyProperty INLINE_EXTERNAL_DECLARATION;
    public static final FirJsErrors INSTANCE;

    /* JADX INFO: renamed from: JS_BUILTIN_NAME_CLASH$delegate, reason: from kotlin metadata */
    private static final ReadOnlyProperty JS_BUILTIN_NAME_CLASH;

    /* JADX INFO: renamed from: JS_EXTERNAL_ARGUMENT$delegate, reason: from kotlin metadata */
    private static final ReadOnlyProperty JS_EXTERNAL_ARGUMENT;

    /* JADX INFO: renamed from: JS_EXTERNAL_INHERITORS_ONLY$delegate, reason: from kotlin metadata */
    private static final ReadOnlyProperty JS_EXTERNAL_INHERITORS_ONLY;

    /* JADX INFO: renamed from: JS_MODULE_PROHIBITED_ON_NON_NATIVE$delegate, reason: from kotlin metadata */
    private static final ReadOnlyProperty JS_MODULE_PROHIBITED_ON_NON_NATIVE;

    /* JADX INFO: renamed from: JS_MODULE_PROHIBITED_ON_VAR$delegate, reason: from kotlin metadata */
    private static final ReadOnlyProperty JS_MODULE_PROHIBITED_ON_VAR;

    /* JADX INFO: renamed from: JS_NAME_IS_NOT_ON_ALL_ACCESSORS$delegate, reason: from kotlin metadata */
    private static final ReadOnlyProperty JS_NAME_IS_NOT_ON_ALL_ACCESSORS;

    /* JADX INFO: renamed from: JS_NAME_ON_ACCESSOR_AND_PROPERTY$delegate, reason: from kotlin metadata */
    private static final ReadOnlyProperty JS_NAME_ON_ACCESSOR_AND_PROPERTY;

    /* JADX INFO: renamed from: JS_NAME_ON_PRIMARY_CONSTRUCTOR_PROHIBITED$delegate, reason: from kotlin metadata */
    private static final ReadOnlyProperty JS_NAME_ON_PRIMARY_CONSTRUCTOR_PROHIBITED;

    /* JADX INFO: renamed from: JS_NAME_PROHIBITED_FOR_EXTENSION_PROPERTY$delegate, reason: from kotlin metadata */
    private static final ReadOnlyProperty JS_NAME_PROHIBITED_FOR_EXTENSION_PROPERTY;

    /* JADX INFO: renamed from: JS_NAME_PROHIBITED_FOR_NAMED_NATIVE$delegate, reason: from kotlin metadata */
    private static final ReadOnlyProperty JS_NAME_PROHIBITED_FOR_NAMED_NATIVE;

    /* JADX INFO: renamed from: JS_NAME_PROHIBITED_FOR_OVERRIDE$delegate, reason: from kotlin metadata */
    private static final ReadOnlyProperty JS_NAME_PROHIBITED_FOR_OVERRIDE;

    /* JADX INFO: renamed from: NAME_CONTAINS_ILLEGAL_CHARS$delegate, reason: from kotlin metadata */
    private static final ReadOnlyProperty NAME_CONTAINS_ILLEGAL_CHARS;

    /* JADX INFO: renamed from: NATIVE_ANNOTATIONS_ALLOWED_ONLY_ON_MEMBER_OR_EXTENSION_FUN$delegate, reason: from kotlin metadata */
    private static final ReadOnlyProperty NATIVE_ANNOTATIONS_ALLOWED_ONLY_ON_MEMBER_OR_EXTENSION_FUN;

    /* JADX INFO: renamed from: NATIVE_GETTER_RETURN_TYPE_SHOULD_BE_NULLABLE$delegate, reason: from kotlin metadata */
    private static final ReadOnlyProperty NATIVE_GETTER_RETURN_TYPE_SHOULD_BE_NULLABLE;

    /* JADX INFO: renamed from: NATIVE_INDEXER_CAN_NOT_HAVE_DEFAULT_ARGUMENTS$delegate, reason: from kotlin metadata */
    private static final ReadOnlyProperty NATIVE_INDEXER_CAN_NOT_HAVE_DEFAULT_ARGUMENTS;

    /* JADX INFO: renamed from: NATIVE_INDEXER_KEY_SHOULD_BE_STRING_OR_NUMBER$delegate, reason: from kotlin metadata */
    private static final ReadOnlyProperty NATIVE_INDEXER_KEY_SHOULD_BE_STRING_OR_NUMBER;

    /* JADX INFO: renamed from: NATIVE_INDEXER_WRONG_PARAMETER_COUNT$delegate, reason: from kotlin metadata */
    private static final ReadOnlyProperty NATIVE_INDEXER_WRONG_PARAMETER_COUNT;

    /* JADX INFO: renamed from: NATIVE_SETTER_WRONG_RETURN_TYPE$delegate, reason: from kotlin metadata */
    private static final ReadOnlyProperty NATIVE_SETTER_WRONG_RETURN_TYPE;

    /* JADX INFO: renamed from: NESTED_CLASS_IN_EXTERNAL_INTERFACE$delegate, reason: from kotlin metadata */
    private static final ReadOnlyProperty NESTED_CLASS_IN_EXTERNAL_INTERFACE;

    /* JADX INFO: renamed from: NESTED_EXTERNAL_DECLARATION$delegate, reason: from kotlin metadata */
    private static final ReadOnlyProperty NESTED_EXTERNAL_DECLARATION;

    /* JADX INFO: renamed from: NESTED_JS_EXPORT$delegate, reason: from kotlin metadata */
    private static final ReadOnlyProperty NESTED_JS_EXPORT;

    /* JADX INFO: renamed from: NESTED_JS_MODULE_PROHIBITED$delegate, reason: from kotlin metadata */
    private static final ReadOnlyProperty NESTED_JS_MODULE_PROHIBITED;

    /* JADX INFO: renamed from: NON_ABSTRACT_MEMBER_OF_EXTERNAL_INTERFACE$delegate, reason: from kotlin metadata */
    private static final ReadOnlyProperty NON_ABSTRACT_MEMBER_OF_EXTERNAL_INTERFACE;

    /* JADX INFO: renamed from: NON_CONSUMABLE_EXPORTED_IDENTIFIER$delegate, reason: from kotlin metadata */
    private static final ReadOnlyProperty NON_CONSUMABLE_EXPORTED_IDENTIFIER;

    /* JADX INFO: renamed from: NON_EXPORTABLE_TYPE$delegate, reason: from kotlin metadata */
    private static final ReadOnlyProperty NON_EXPORTABLE_TYPE;

    /* JADX INFO: renamed from: NON_EXTERNAL_DECLARATION_IN_INAPPROPRIATE_FILE$delegate, reason: from kotlin metadata */
    private static final ReadOnlyProperty NON_EXTERNAL_DECLARATION_IN_INAPPROPRIATE_FILE;

    /* JADX INFO: renamed from: OVERRIDING_EXTERNAL_FUN_WITH_OPTIONAL_PARAMS$delegate, reason: from kotlin metadata */
    private static final ReadOnlyProperty OVERRIDING_EXTERNAL_FUN_WITH_OPTIONAL_PARAMS;

    /* JADX INFO: renamed from: OVERRIDING_EXTERNAL_FUN_WITH_OPTIONAL_PARAMS_WITH_FAKE$delegate, reason: from kotlin metadata */
    private static final ReadOnlyProperty OVERRIDING_EXTERNAL_FUN_WITH_OPTIONAL_PARAMS_WITH_FAKE;

    /* JADX INFO: renamed from: PROPERTY_DELEGATION_BY_DYNAMIC$delegate, reason: from kotlin metadata */
    private static final ReadOnlyProperty PROPERTY_DELEGATION_BY_DYNAMIC;

    /* JADX INFO: renamed from: RUNTIME_ANNOTATION_NOT_SUPPORTED$delegate, reason: from kotlin metadata */
    private static final ReadOnlyProperty RUNTIME_ANNOTATION_NOT_SUPPORTED;

    /* JADX INFO: renamed from: RUNTIME_ANNOTATION_ON_EXTERNAL_DECLARATION$delegate, reason: from kotlin metadata */
    private static final ReadOnlyProperty RUNTIME_ANNOTATION_ON_EXTERNAL_DECLARATION;

    /* JADX INFO: renamed from: SPREAD_OPERATOR_IN_DYNAMIC_CALL$delegate, reason: from kotlin metadata */
    private static final ReadOnlyProperty SPREAD_OPERATOR_IN_DYNAMIC_CALL;

    /* JADX INFO: renamed from: UNCHECKED_CAST_TO_EXTERNAL_INTERFACE$delegate, reason: from kotlin metadata */
    private static final ReadOnlyProperty UNCHECKED_CAST_TO_EXTERNAL_INTERFACE;

    /* JADX INFO: renamed from: WRONG_BODY_OF_EXTERNAL_DECLARATION$delegate, reason: from kotlin metadata */
    private static final ReadOnlyProperty WRONG_BODY_OF_EXTERNAL_DECLARATION;

    /* JADX INFO: renamed from: WRONG_DEFAULT_VALUE_FOR_EXTERNAL_FUN_PARAMETER$delegate, reason: from kotlin metadata */
    private static final ReadOnlyProperty WRONG_DEFAULT_VALUE_FOR_EXTERNAL_FUN_PARAMETER;

    /* JADX INFO: renamed from: WRONG_EXPORTED_DECLARATION$delegate, reason: from kotlin metadata */
    private static final ReadOnlyProperty WRONG_EXPORTED_DECLARATION;

    /* JADX INFO: renamed from: WRONG_EXTERNAL_DECLARATION$delegate, reason: from kotlin metadata */
    private static final ReadOnlyProperty WRONG_EXTERNAL_DECLARATION;

    /* JADX INFO: renamed from: WRONG_INITIALIZER_OF_EXTERNAL_DECLARATION$delegate, reason: from kotlin metadata */
    private static final ReadOnlyProperty WRONG_INITIALIZER_OF_EXTERNAL_DECLARATION;

    /* JADX INFO: renamed from: WRONG_JS_QUALIFIER$delegate, reason: from kotlin metadata */
    private static final ReadOnlyProperty WRONG_JS_QUALIFIER;

    /* JADX INFO: renamed from: WRONG_MULTIPLE_INHERITANCE$delegate, reason: from kotlin metadata */
    private static final ReadOnlyProperty WRONG_MULTIPLE_INHERITANCE;

    /* JADX INFO: renamed from: WRONG_OPERATION_WITH_DYNAMIC$delegate, reason: from kotlin metadata */
    private static final ReadOnlyProperty WRONG_OPERATION_WITH_DYNAMIC;

    private FirJsErrors() {
    }

    static {
        KProperty<?>[] kPropertyArr = {Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(FirJsErrors.class), "WRONG_JS_QUALIFIER", "getWRONG_JS_QUALIFIER()Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticFactory0;")), Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(FirJsErrors.class), "JS_MODULE_PROHIBITED_ON_VAR", "getJS_MODULE_PROHIBITED_ON_VAR()Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticFactory0;")), Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(FirJsErrors.class), "JS_MODULE_PROHIBITED_ON_NON_NATIVE", "getJS_MODULE_PROHIBITED_ON_NON_NATIVE()Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticFactory0;")), Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(FirJsErrors.class), "NESTED_JS_MODULE_PROHIBITED", "getNESTED_JS_MODULE_PROHIBITED()Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticFactory0;")), Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(FirJsErrors.class), "CALL_FROM_UMD_MUST_BE_JS_MODULE_AND_JS_NON_MODULE", "getCALL_FROM_UMD_MUST_BE_JS_MODULE_AND_JS_NON_MODULE()Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticFactory0;")), Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(FirJsErrors.class), "CALL_TO_JS_MODULE_WITHOUT_MODULE_SYSTEM", "getCALL_TO_JS_MODULE_WITHOUT_MODULE_SYSTEM()Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticFactory1;")), Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(FirJsErrors.class), "CALL_TO_JS_NON_MODULE_WITH_MODULE_SYSTEM", "getCALL_TO_JS_NON_MODULE_WITH_MODULE_SYSTEM()Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticFactory1;")), Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(FirJsErrors.class), "RUNTIME_ANNOTATION_NOT_SUPPORTED", "getRUNTIME_ANNOTATION_NOT_SUPPORTED()Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticFactory0;")), Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(FirJsErrors.class), "RUNTIME_ANNOTATION_ON_EXTERNAL_DECLARATION", "getRUNTIME_ANNOTATION_ON_EXTERNAL_DECLARATION()Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticFactory0;")), Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(FirJsErrors.class), "NATIVE_ANNOTATIONS_ALLOWED_ONLY_ON_MEMBER_OR_EXTENSION_FUN", "getNATIVE_ANNOTATIONS_ALLOWED_ONLY_ON_MEMBER_OR_EXTENSION_FUN()Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticFactory1;")), Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(FirJsErrors.class), "NATIVE_INDEXER_KEY_SHOULD_BE_STRING_OR_NUMBER", "getNATIVE_INDEXER_KEY_SHOULD_BE_STRING_OR_NUMBER()Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticFactory1;")), Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(FirJsErrors.class), "NATIVE_INDEXER_WRONG_PARAMETER_COUNT", "getNATIVE_INDEXER_WRONG_PARAMETER_COUNT()Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticFactory2;")), Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(FirJsErrors.class), "NATIVE_INDEXER_CAN_NOT_HAVE_DEFAULT_ARGUMENTS", "getNATIVE_INDEXER_CAN_NOT_HAVE_DEFAULT_ARGUMENTS()Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticFactory1;")), Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(FirJsErrors.class), "NATIVE_GETTER_RETURN_TYPE_SHOULD_BE_NULLABLE", "getNATIVE_GETTER_RETURN_TYPE_SHOULD_BE_NULLABLE()Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticFactory0;")), Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(FirJsErrors.class), "NATIVE_SETTER_WRONG_RETURN_TYPE", "getNATIVE_SETTER_WRONG_RETURN_TYPE()Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticFactory0;")), Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(FirJsErrors.class), "JS_NAME_IS_NOT_ON_ALL_ACCESSORS", "getJS_NAME_IS_NOT_ON_ALL_ACCESSORS()Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticFactory0;")), Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(FirJsErrors.class), "JS_NAME_PROHIBITED_FOR_NAMED_NATIVE", "getJS_NAME_PROHIBITED_FOR_NAMED_NATIVE()Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticFactory0;")), Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(FirJsErrors.class), "JS_NAME_PROHIBITED_FOR_OVERRIDE", "getJS_NAME_PROHIBITED_FOR_OVERRIDE()Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticFactory0;")), Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(FirJsErrors.class), "JS_NAME_ON_PRIMARY_CONSTRUCTOR_PROHIBITED", "getJS_NAME_ON_PRIMARY_CONSTRUCTOR_PROHIBITED()Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticFactory0;")), Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(FirJsErrors.class), "JS_NAME_ON_ACCESSOR_AND_PROPERTY", "getJS_NAME_ON_ACCESSOR_AND_PROPERTY()Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticFactory0;")), Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(FirJsErrors.class), "JS_NAME_PROHIBITED_FOR_EXTENSION_PROPERTY", "getJS_NAME_PROHIBITED_FOR_EXTENSION_PROPERTY()Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticFactory0;")), Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(FirJsErrors.class), "JS_BUILTIN_NAME_CLASH", "getJS_BUILTIN_NAME_CLASH()Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticFactory1;")), Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(FirJsErrors.class), "NAME_CONTAINS_ILLEGAL_CHARS", "getNAME_CONTAINS_ILLEGAL_CHARS()Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticFactory0;")), Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(FirJsErrors.class), "WRONG_MULTIPLE_INHERITANCE", "getWRONG_MULTIPLE_INHERITANCE()Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticFactory1;")), Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(FirJsErrors.class), "IMPLEMENTING_FUNCTION_INTERFACE", "getIMPLEMENTING_FUNCTION_INTERFACE()Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticFactory0;")), Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(FirJsErrors.class), "OVERRIDING_EXTERNAL_FUN_WITH_OPTIONAL_PARAMS", "getOVERRIDING_EXTERNAL_FUN_WITH_OPTIONAL_PARAMS()Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticFactory0;")), Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(FirJsErrors.class), "OVERRIDING_EXTERNAL_FUN_WITH_OPTIONAL_PARAMS_WITH_FAKE", "getOVERRIDING_EXTERNAL_FUN_WITH_OPTIONAL_PARAMS_WITH_FAKE()Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticFactory1;")), Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(FirJsErrors.class), "CALL_TO_DEFINED_EXTERNALLY_FROM_NON_EXTERNAL_DECLARATION", "getCALL_TO_DEFINED_EXTERNALLY_FROM_NON_EXTERNAL_DECLARATION()Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticFactory0;")), Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(FirJsErrors.class), "EXTERNAL_CLASS_CONSTRUCTOR_PROPERTY_PARAMETER", "getEXTERNAL_CLASS_CONSTRUCTOR_PROPERTY_PARAMETER()Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticFactory0;")), Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(FirJsErrors.class), "EXTERNAL_ENUM_ENTRY_WITH_BODY", "getEXTERNAL_ENUM_ENTRY_WITH_BODY()Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticFactory0;")), Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(FirJsErrors.class), "EXTERNAL_ANONYMOUS_INITIALIZER", "getEXTERNAL_ANONYMOUS_INITIALIZER()Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticFactory0;")), Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(FirJsErrors.class), "EXTERNAL_DELEGATION", "getEXTERNAL_DELEGATION()Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticFactory0;")), Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(FirJsErrors.class), "EXTERNAL_DELEGATED_CONSTRUCTOR_CALL", "getEXTERNAL_DELEGATED_CONSTRUCTOR_CALL()Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticFactory0;")), Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(FirJsErrors.class), "WRONG_BODY_OF_EXTERNAL_DECLARATION", "getWRONG_BODY_OF_EXTERNAL_DECLARATION()Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticFactory0;")), Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(FirJsErrors.class), "WRONG_INITIALIZER_OF_EXTERNAL_DECLARATION", "getWRONG_INITIALIZER_OF_EXTERNAL_DECLARATION()Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticFactory0;")), Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(FirJsErrors.class), "WRONG_DEFAULT_VALUE_FOR_EXTERNAL_FUN_PARAMETER", "getWRONG_DEFAULT_VALUE_FOR_EXTERNAL_FUN_PARAMETER()Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticFactory0;")), Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(FirJsErrors.class), "NESTED_EXTERNAL_DECLARATION", "getNESTED_EXTERNAL_DECLARATION()Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticFactory0;")), Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(FirJsErrors.class), "WRONG_EXTERNAL_DECLARATION", "getWRONG_EXTERNAL_DECLARATION()Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticFactory1;")), Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(FirJsErrors.class), "NESTED_CLASS_IN_EXTERNAL_INTERFACE", "getNESTED_CLASS_IN_EXTERNAL_INTERFACE()Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticFactory0;")), Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(FirJsErrors.class), "EXTERNAL_TYPE_EXTENDS_NON_EXTERNAL_TYPE", "getEXTERNAL_TYPE_EXTENDS_NON_EXTERNAL_TYPE()Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticFactory0;")), Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(FirJsErrors.class), "INLINE_EXTERNAL_DECLARATION", "getINLINE_EXTERNAL_DECLARATION()Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticFactory0;")), Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(FirJsErrors.class), "ENUM_CLASS_IN_EXTERNAL_DECLARATION_WARNING", "getENUM_CLASS_IN_EXTERNAL_DECLARATION_WARNING()Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticFactory0;")), Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(FirJsErrors.class), "INLINE_CLASS_IN_EXTERNAL_DECLARATION_WARNING", "getINLINE_CLASS_IN_EXTERNAL_DECLARATION_WARNING()Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticFactory0;")), Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(FirJsErrors.class), "INLINE_CLASS_IN_EXTERNAL_DECLARATION", "getINLINE_CLASS_IN_EXTERNAL_DECLARATION()Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticFactory0;")), Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(FirJsErrors.class), "EXTENSION_FUNCTION_IN_EXTERNAL_DECLARATION", "getEXTENSION_FUNCTION_IN_EXTERNAL_DECLARATION()Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticFactory0;")), Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(FirJsErrors.class), "NON_ABSTRACT_MEMBER_OF_EXTERNAL_INTERFACE", "getNON_ABSTRACT_MEMBER_OF_EXTERNAL_INTERFACE()Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticFactory0;")), Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(FirJsErrors.class), "NON_EXTERNAL_DECLARATION_IN_INAPPROPRIATE_FILE", "getNON_EXTERNAL_DECLARATION_IN_INAPPROPRIATE_FILE()Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticFactory1;")), Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(FirJsErrors.class), "CANNOT_CHECK_FOR_EXTERNAL_INTERFACE", "getCANNOT_CHECK_FOR_EXTERNAL_INTERFACE()Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticFactory1;")), Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(FirJsErrors.class), "UNCHECKED_CAST_TO_EXTERNAL_INTERFACE", "getUNCHECKED_CAST_TO_EXTERNAL_INTERFACE()Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticFactory2;")), Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(FirJsErrors.class), "EXTERNAL_INTERFACE_AS_CLASS_LITERAL", "getEXTERNAL_INTERFACE_AS_CLASS_LITERAL()Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticFactory0;")), Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(FirJsErrors.class), "JS_EXTERNAL_INHERITORS_ONLY", "getJS_EXTERNAL_INHERITORS_ONLY()Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticFactory2;")), Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(FirJsErrors.class), "JS_EXTERNAL_ARGUMENT", "getJS_EXTERNAL_ARGUMENT()Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticFactory1;")), Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(FirJsErrors.class), "EXTERNAL_INTERFACE_AS_REIFIED_TYPE_ARGUMENT", "getEXTERNAL_INTERFACE_AS_REIFIED_TYPE_ARGUMENT()Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticFactory1;")), Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(FirJsErrors.class), "NESTED_JS_EXPORT", "getNESTED_JS_EXPORT()Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticFactory0;")), Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(FirJsErrors.class), "WRONG_EXPORTED_DECLARATION", "getWRONG_EXPORTED_DECLARATION()Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticFactory1;")), Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(FirJsErrors.class), "NON_EXPORTABLE_TYPE", "getNON_EXPORTABLE_TYPE()Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticFactory2;")), Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(FirJsErrors.class), "NON_CONSUMABLE_EXPORTED_IDENTIFIER", "getNON_CONSUMABLE_EXPORTED_IDENTIFIER()Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticFactory1;")), Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(FirJsErrors.class), "DELEGATION_BY_DYNAMIC", "getDELEGATION_BY_DYNAMIC()Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticFactory0;")), Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(FirJsErrors.class), "PROPERTY_DELEGATION_BY_DYNAMIC", "getPROPERTY_DELEGATION_BY_DYNAMIC()Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticFactory0;")), Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(FirJsErrors.class), "SPREAD_OPERATOR_IN_DYNAMIC_CALL", "getSPREAD_OPERATOR_IN_DYNAMIC_CALL()Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticFactory0;")), Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(FirJsErrors.class), "WRONG_OPERATION_WITH_DYNAMIC", "getWRONG_OPERATION_WITH_DYNAMIC()Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticFactory1;"))};
        $$delegatedProperties = kPropertyArr;
        FirJsErrors firJsErrors = new FirJsErrors();
        INSTANCE = firJsErrors;
        WRONG_JS_QUALIFIER = new DiagnosticFactory0DelegateProvider(Severity.ERROR, AbstractSourceElementPositioningStrategy.INSTANCE.getDEFAULT(), Reflection.getOrCreateKotlinClass(KtElement.class)).provideDelegate(firJsErrors, kPropertyArr[0]);
        JS_MODULE_PROHIBITED_ON_VAR = new DiagnosticFactory0DelegateProvider(Severity.ERROR, SourceElementPositioningStrategies.INSTANCE.getDECLARATION_SIGNATURE_OR_DEFAULT(), Reflection.getOrCreateKotlinClass(KtElement.class)).provideDelegate(firJsErrors, kPropertyArr[1]);
        JS_MODULE_PROHIBITED_ON_NON_NATIVE = new DiagnosticFactory0DelegateProvider(Severity.ERROR, SourceElementPositioningStrategies.INSTANCE.getDECLARATION_SIGNATURE_OR_DEFAULT(), Reflection.getOrCreateKotlinClass(KtElement.class)).provideDelegate(firJsErrors, kPropertyArr[2]);
        NESTED_JS_MODULE_PROHIBITED = new DiagnosticFactory0DelegateProvider(Severity.ERROR, SourceElementPositioningStrategies.INSTANCE.getDECLARATION_SIGNATURE_OR_DEFAULT(), Reflection.getOrCreateKotlinClass(KtElement.class)).provideDelegate(firJsErrors, kPropertyArr[3]);
        CALL_FROM_UMD_MUST_BE_JS_MODULE_AND_JS_NON_MODULE = new DiagnosticFactory0DelegateProvider(Severity.ERROR, SourceElementPositioningStrategies.INSTANCE.getDECLARATION_SIGNATURE_OR_DEFAULT(), Reflection.getOrCreateKotlinClass(KtElement.class)).provideDelegate(firJsErrors, kPropertyArr[4]);
        CALL_TO_JS_MODULE_WITHOUT_MODULE_SYSTEM = new DiagnosticFactory1DelegateProvider(Severity.ERROR, SourceElementPositioningStrategies.INSTANCE.getDECLARATION_SIGNATURE_OR_DEFAULT(), Reflection.getOrCreateKotlinClass(KtElement.class)).provideDelegate(firJsErrors, kPropertyArr[5]);
        CALL_TO_JS_NON_MODULE_WITH_MODULE_SYSTEM = new DiagnosticFactory1DelegateProvider(Severity.ERROR, SourceElementPositioningStrategies.INSTANCE.getDECLARATION_SIGNATURE_OR_DEFAULT(), Reflection.getOrCreateKotlinClass(KtElement.class)).provideDelegate(firJsErrors, kPropertyArr[6]);
        RUNTIME_ANNOTATION_NOT_SUPPORTED = new DiagnosticFactory0DelegateProvider(Severity.WARNING, SourceElementPositioningStrategies.INSTANCE.getDECLARATION_SIGNATURE_OR_DEFAULT(), Reflection.getOrCreateKotlinClass(PsiElement.class)).provideDelegate(firJsErrors, kPropertyArr[7]);
        RUNTIME_ANNOTATION_ON_EXTERNAL_DECLARATION = new DiagnosticFactory0DelegateProvider(Severity.ERROR, SourceElementPositioningStrategies.INSTANCE.getDECLARATION_SIGNATURE_OR_DEFAULT(), Reflection.getOrCreateKotlinClass(PsiElement.class)).provideDelegate(firJsErrors, kPropertyArr[8]);
        NATIVE_ANNOTATIONS_ALLOWED_ONLY_ON_MEMBER_OR_EXTENSION_FUN = new DiagnosticFactory1DelegateProvider(Severity.ERROR, SourceElementPositioningStrategies.INSTANCE.getDECLARATION_SIGNATURE_OR_DEFAULT(), Reflection.getOrCreateKotlinClass(KtElement.class)).provideDelegate(firJsErrors, kPropertyArr[9]);
        NATIVE_INDEXER_KEY_SHOULD_BE_STRING_OR_NUMBER = new DiagnosticFactory1DelegateProvider(Severity.ERROR, SourceElementPositioningStrategies.INSTANCE.getDECLARATION_SIGNATURE_OR_DEFAULT(), Reflection.getOrCreateKotlinClass(KtElement.class)).provideDelegate(firJsErrors, kPropertyArr[10]);
        NATIVE_INDEXER_WRONG_PARAMETER_COUNT = new DiagnosticFactory2DelegateProvider(Severity.ERROR, SourceElementPositioningStrategies.INSTANCE.getDECLARATION_SIGNATURE_OR_DEFAULT(), Reflection.getOrCreateKotlinClass(KtElement.class)).provideDelegate(firJsErrors, kPropertyArr[11]);
        NATIVE_INDEXER_CAN_NOT_HAVE_DEFAULT_ARGUMENTS = new DiagnosticFactory1DelegateProvider(Severity.ERROR, SourceElementPositioningStrategies.INSTANCE.getDECLARATION_SIGNATURE_OR_DEFAULT(), Reflection.getOrCreateKotlinClass(KtElement.class)).provideDelegate(firJsErrors, kPropertyArr[12]);
        NATIVE_GETTER_RETURN_TYPE_SHOULD_BE_NULLABLE = new DiagnosticFactory0DelegateProvider(Severity.ERROR, SourceElementPositioningStrategies.INSTANCE.getDECLARATION_RETURN_TYPE(), Reflection.getOrCreateKotlinClass(KtDeclaration.class)).provideDelegate(firJsErrors, kPropertyArr[13]);
        NATIVE_SETTER_WRONG_RETURN_TYPE = new DiagnosticFactory0DelegateProvider(Severity.ERROR, SourceElementPositioningStrategies.INSTANCE.getDECLARATION_RETURN_TYPE(), Reflection.getOrCreateKotlinClass(KtDeclaration.class)).provideDelegate(firJsErrors, kPropertyArr[14]);
        JS_NAME_IS_NOT_ON_ALL_ACCESSORS = new DiagnosticFactory0DelegateProvider(Severity.ERROR, SourceElementPositioningStrategies.INSTANCE.getDECLARATION_SIGNATURE_OR_DEFAULT(), Reflection.getOrCreateKotlinClass(KtElement.class)).provideDelegate(firJsErrors, kPropertyArr[15]);
        JS_NAME_PROHIBITED_FOR_NAMED_NATIVE = new DiagnosticFactory0DelegateProvider(Severity.ERROR, AbstractSourceElementPositioningStrategy.INSTANCE.getDEFAULT(), Reflection.getOrCreateKotlinClass(KtElement.class)).provideDelegate(firJsErrors, kPropertyArr[16]);
        JS_NAME_PROHIBITED_FOR_OVERRIDE = new DiagnosticFactory0DelegateProvider(Severity.ERROR, AbstractSourceElementPositioningStrategy.INSTANCE.getDEFAULT(), Reflection.getOrCreateKotlinClass(KtElement.class)).provideDelegate(firJsErrors, kPropertyArr[17]);
        JS_NAME_ON_PRIMARY_CONSTRUCTOR_PROHIBITED = new DiagnosticFactory0DelegateProvider(Severity.ERROR, AbstractSourceElementPositioningStrategy.INSTANCE.getDEFAULT(), Reflection.getOrCreateKotlinClass(KtElement.class)).provideDelegate(firJsErrors, kPropertyArr[18]);
        JS_NAME_ON_ACCESSOR_AND_PROPERTY = new DiagnosticFactory0DelegateProvider(Severity.ERROR, AbstractSourceElementPositioningStrategy.INSTANCE.getDEFAULT(), Reflection.getOrCreateKotlinClass(KtElement.class)).provideDelegate(firJsErrors, kPropertyArr[19]);
        JS_NAME_PROHIBITED_FOR_EXTENSION_PROPERTY = new DiagnosticFactory0DelegateProvider(Severity.ERROR, AbstractSourceElementPositioningStrategy.INSTANCE.getDEFAULT(), Reflection.getOrCreateKotlinClass(KtElement.class)).provideDelegate(firJsErrors, kPropertyArr[20]);
        JS_BUILTIN_NAME_CLASH = new DiagnosticFactory1DelegateProvider(Severity.ERROR, SourceElementPositioningStrategies.INSTANCE.getDECLARATION_SIGNATURE_OR_DEFAULT(), Reflection.getOrCreateKotlinClass(KtElement.class)).provideDelegate(firJsErrors, kPropertyArr[21]);
        NAME_CONTAINS_ILLEGAL_CHARS = new DiagnosticFactory0DelegateProvider(Severity.ERROR, SourceElementPositioningStrategies.INSTANCE.getDECLARATION_SIGNATURE_OR_DEFAULT(), Reflection.getOrCreateKotlinClass(KtElement.class)).provideDelegate(firJsErrors, kPropertyArr[22]);
        WRONG_MULTIPLE_INHERITANCE = new DiagnosticFactory1DelegateProvider(Severity.ERROR, SourceElementPositioningStrategies.INSTANCE.getDECLARATION_SIGNATURE_OR_DEFAULT(), Reflection.getOrCreateKotlinClass(KtElement.class)).provideDelegate(firJsErrors, kPropertyArr[23]);
        IMPLEMENTING_FUNCTION_INTERFACE = new DiagnosticFactory0DelegateProvider(Severity.ERROR, SourceElementPositioningStrategies.INSTANCE.getDECLARATION_SIGNATURE_OR_DEFAULT(), Reflection.getOrCreateKotlinClass(KtClassOrObject.class)).provideDelegate(firJsErrors, kPropertyArr[24]);
        OVERRIDING_EXTERNAL_FUN_WITH_OPTIONAL_PARAMS = new DiagnosticFactory0DelegateProvider(Severity.ERROR, SourceElementPositioningStrategies.INSTANCE.getDECLARATION_SIGNATURE_OR_DEFAULT(), Reflection.getOrCreateKotlinClass(KtElement.class)).provideDelegate(firJsErrors, kPropertyArr[25]);
        OVERRIDING_EXTERNAL_FUN_WITH_OPTIONAL_PARAMS_WITH_FAKE = new DiagnosticFactory1DelegateProvider(Severity.ERROR, SourceElementPositioningStrategies.INSTANCE.getDECLARATION_SIGNATURE_OR_DEFAULT(), Reflection.getOrCreateKotlinClass(KtElement.class)).provideDelegate(firJsErrors, kPropertyArr[26]);
        CALL_TO_DEFINED_EXTERNALLY_FROM_NON_EXTERNAL_DECLARATION = new DiagnosticFactory0DelegateProvider(Severity.ERROR, AbstractSourceElementPositioningStrategy.INSTANCE.getDEFAULT(), Reflection.getOrCreateKotlinClass(PsiElement.class)).provideDelegate(firJsErrors, kPropertyArr[27]);
        EXTERNAL_CLASS_CONSTRUCTOR_PROPERTY_PARAMETER = new DiagnosticFactory0DelegateProvider(Severity.ERROR, AbstractSourceElementPositioningStrategy.INSTANCE.getDEFAULT(), Reflection.getOrCreateKotlinClass(KtParameter.class)).provideDelegate(firJsErrors, kPropertyArr[28]);
        EXTERNAL_ENUM_ENTRY_WITH_BODY = new DiagnosticFactory0DelegateProvider(Severity.ERROR, AbstractSourceElementPositioningStrategy.INSTANCE.getDEFAULT(), Reflection.getOrCreateKotlinClass(KtElement.class)).provideDelegate(firJsErrors, kPropertyArr[29]);
        EXTERNAL_ANONYMOUS_INITIALIZER = new DiagnosticFactory0DelegateProvider(Severity.ERROR, AbstractSourceElementPositioningStrategy.INSTANCE.getDEFAULT(), Reflection.getOrCreateKotlinClass(KtAnonymousInitializer.class)).provideDelegate(firJsErrors, kPropertyArr[30]);
        EXTERNAL_DELEGATION = new DiagnosticFactory0DelegateProvider(Severity.ERROR, AbstractSourceElementPositioningStrategy.INSTANCE.getDEFAULT(), Reflection.getOrCreateKotlinClass(KtElement.class)).provideDelegate(firJsErrors, kPropertyArr[31]);
        EXTERNAL_DELEGATED_CONSTRUCTOR_CALL = new DiagnosticFactory0DelegateProvider(Severity.ERROR, AbstractSourceElementPositioningStrategy.INSTANCE.getDEFAULT(), Reflection.getOrCreateKotlinClass(KtElement.class)).provideDelegate(firJsErrors, kPropertyArr[32]);
        WRONG_BODY_OF_EXTERNAL_DECLARATION = new DiagnosticFactory0DelegateProvider(Severity.ERROR, AbstractSourceElementPositioningStrategy.INSTANCE.getDEFAULT(), Reflection.getOrCreateKotlinClass(KtElement.class)).provideDelegate(firJsErrors, kPropertyArr[33]);
        WRONG_INITIALIZER_OF_EXTERNAL_DECLARATION = new DiagnosticFactory0DelegateProvider(Severity.ERROR, AbstractSourceElementPositioningStrategy.INSTANCE.getDEFAULT(), Reflection.getOrCreateKotlinClass(KtElement.class)).provideDelegate(firJsErrors, kPropertyArr[34]);
        WRONG_DEFAULT_VALUE_FOR_EXTERNAL_FUN_PARAMETER = new DiagnosticFactory0DelegateProvider(Severity.ERROR, AbstractSourceElementPositioningStrategy.INSTANCE.getDEFAULT(), Reflection.getOrCreateKotlinClass(KtElement.class)).provideDelegate(firJsErrors, kPropertyArr[35]);
        NESTED_EXTERNAL_DECLARATION = new DiagnosticFactory0DelegateProvider(Severity.ERROR, SourceElementPositioningStrategies.INSTANCE.getDECLARATION_SIGNATURE_OR_DEFAULT(), Reflection.getOrCreateKotlinClass(KtExpression.class)).provideDelegate(firJsErrors, kPropertyArr[36]);
        WRONG_EXTERNAL_DECLARATION = new DiagnosticFactory1DelegateProvider(Severity.ERROR, SourceElementPositioningStrategies.INSTANCE.getDECLARATION_SIGNATURE_OR_DEFAULT(), Reflection.getOrCreateKotlinClass(KtExpression.class)).provideDelegate(firJsErrors, kPropertyArr[37]);
        NESTED_CLASS_IN_EXTERNAL_INTERFACE = new DiagnosticFactory0DelegateProvider(Severity.ERROR, SourceElementPositioningStrategies.INSTANCE.getDECLARATION_SIGNATURE_OR_DEFAULT(), Reflection.getOrCreateKotlinClass(KtExpression.class)).provideDelegate(firJsErrors, kPropertyArr[38]);
        EXTERNAL_TYPE_EXTENDS_NON_EXTERNAL_TYPE = new DiagnosticFactory0DelegateProvider(Severity.ERROR, SourceElementPositioningStrategies.INSTANCE.getDECLARATION_SIGNATURE_OR_DEFAULT(), Reflection.getOrCreateKotlinClass(KtElement.class)).provideDelegate(firJsErrors, kPropertyArr[39]);
        INLINE_EXTERNAL_DECLARATION = new DiagnosticFactory0DelegateProvider(Severity.ERROR, SourceElementPositioningStrategies.INSTANCE.getDECLARATION_SIGNATURE_OR_DEFAULT(), Reflection.getOrCreateKotlinClass(KtDeclaration.class)).provideDelegate(firJsErrors, kPropertyArr[40]);
        ENUM_CLASS_IN_EXTERNAL_DECLARATION_WARNING = new DiagnosticFactory0DelegateProvider(Severity.WARNING, SourceElementPositioningStrategies.INSTANCE.getDECLARATION_SIGNATURE_OR_DEFAULT(), Reflection.getOrCreateKotlinClass(KtDeclaration.class)).provideDelegate(firJsErrors, kPropertyArr[41]);
        INLINE_CLASS_IN_EXTERNAL_DECLARATION_WARNING = new DiagnosticFactory0DelegateProvider(Severity.WARNING, SourceElementPositioningStrategies.INSTANCE.getDECLARATION_SIGNATURE_OR_DEFAULT(), Reflection.getOrCreateKotlinClass(KtElement.class)).provideDelegate(firJsErrors, kPropertyArr[42]);
        INLINE_CLASS_IN_EXTERNAL_DECLARATION = new DiagnosticFactory0DelegateProvider(Severity.ERROR, SourceElementPositioningStrategies.INSTANCE.getDECLARATION_SIGNATURE_OR_DEFAULT(), Reflection.getOrCreateKotlinClass(KtElement.class)).provideDelegate(firJsErrors, kPropertyArr[43]);
        EXTENSION_FUNCTION_IN_EXTERNAL_DECLARATION = new DiagnosticFactory0DelegateProvider(Severity.ERROR, SourceElementPositioningStrategies.INSTANCE.getDECLARATION_SIGNATURE_OR_DEFAULT(), Reflection.getOrCreateKotlinClass(KtElement.class)).provideDelegate(firJsErrors, kPropertyArr[44]);
        NON_ABSTRACT_MEMBER_OF_EXTERNAL_INTERFACE = new DiagnosticFactory0DelegateProvider(Severity.ERROR, SourceElementPositioningStrategies.INSTANCE.getDECLARATION_SIGNATURE_OR_DEFAULT(), Reflection.getOrCreateKotlinClass(KtExpression.class)).provideDelegate(firJsErrors, kPropertyArr[45]);
        NON_EXTERNAL_DECLARATION_IN_INAPPROPRIATE_FILE = new DiagnosticFactory1DelegateProvider(Severity.ERROR, SourceElementPositioningStrategies.INSTANCE.getDECLARATION_SIGNATURE_OR_DEFAULT(), Reflection.getOrCreateKotlinClass(KtElement.class)).provideDelegate(firJsErrors, kPropertyArr[46]);
        CANNOT_CHECK_FOR_EXTERNAL_INTERFACE = new DiagnosticFactory1DelegateProvider(Severity.ERROR, AbstractSourceElementPositioningStrategy.INSTANCE.getDEFAULT(), Reflection.getOrCreateKotlinClass(KtElement.class)).provideDelegate(firJsErrors, kPropertyArr[47]);
        UNCHECKED_CAST_TO_EXTERNAL_INTERFACE = new DiagnosticFactory2DelegateProvider(Severity.WARNING, AbstractSourceElementPositioningStrategy.INSTANCE.getDEFAULT(), Reflection.getOrCreateKotlinClass(KtElement.class)).provideDelegate(firJsErrors, kPropertyArr[48]);
        EXTERNAL_INTERFACE_AS_CLASS_LITERAL = new DiagnosticFactory0DelegateProvider(Severity.ERROR, AbstractSourceElementPositioningStrategy.INSTANCE.getDEFAULT(), Reflection.getOrCreateKotlinClass(KtElement.class)).provideDelegate(firJsErrors, kPropertyArr[49]);
        JS_EXTERNAL_INHERITORS_ONLY = new DiagnosticFactory2DelegateProvider(Severity.ERROR, SourceElementPositioningStrategies.INSTANCE.getDECLARATION_SIGNATURE_OR_DEFAULT(), Reflection.getOrCreateKotlinClass(KtDeclaration.class)).provideDelegate(firJsErrors, kPropertyArr[50]);
        JS_EXTERNAL_ARGUMENT = new DiagnosticFactory1DelegateProvider(Severity.ERROR, SourceElementPositioningStrategies.INSTANCE.getDECLARATION_SIGNATURE_OR_DEFAULT(), Reflection.getOrCreateKotlinClass(KtExpression.class)).provideDelegate(firJsErrors, kPropertyArr[51]);
        EXTERNAL_INTERFACE_AS_REIFIED_TYPE_ARGUMENT = new DiagnosticFactory1DelegateProvider(Severity.ERROR, SourceElementPositioningStrategies.INSTANCE.getDECLARATION_SIGNATURE_OR_DEFAULT(), Reflection.getOrCreateKotlinClass(KtElement.class)).provideDelegate(firJsErrors, kPropertyArr[52]);
        NESTED_JS_EXPORT = new DiagnosticFactory0DelegateProvider(Severity.ERROR, AbstractSourceElementPositioningStrategy.INSTANCE.getDEFAULT(), Reflection.getOrCreateKotlinClass(KtElement.class)).provideDelegate(firJsErrors, kPropertyArr[53]);
        WRONG_EXPORTED_DECLARATION = new DiagnosticFactory1DelegateProvider(Severity.ERROR, SourceElementPositioningStrategies.INSTANCE.getDECLARATION_SIGNATURE_OR_DEFAULT(), Reflection.getOrCreateKotlinClass(KtElement.class)).provideDelegate(firJsErrors, kPropertyArr[54]);
        NON_EXPORTABLE_TYPE = new DiagnosticFactory2DelegateProvider(Severity.WARNING, SourceElementPositioningStrategies.INSTANCE.getDECLARATION_SIGNATURE_OR_DEFAULT(), Reflection.getOrCreateKotlinClass(KtElement.class)).provideDelegate(firJsErrors, kPropertyArr[55]);
        NON_CONSUMABLE_EXPORTED_IDENTIFIER = new DiagnosticFactory1DelegateProvider(Severity.WARNING, AbstractSourceElementPositioningStrategy.INSTANCE.getDEFAULT(), Reflection.getOrCreateKotlinClass(KtElement.class)).provideDelegate(firJsErrors, kPropertyArr[56]);
        DELEGATION_BY_DYNAMIC = new DiagnosticFactory0DelegateProvider(Severity.ERROR, AbstractSourceElementPositioningStrategy.INSTANCE.getDEFAULT(), Reflection.getOrCreateKotlinClass(KtElement.class)).provideDelegate(firJsErrors, kPropertyArr[57]);
        PROPERTY_DELEGATION_BY_DYNAMIC = new DiagnosticFactory0DelegateProvider(Severity.ERROR, AbstractSourceElementPositioningStrategy.INSTANCE.getDEFAULT(), Reflection.getOrCreateKotlinClass(KtElement.class)).provideDelegate(firJsErrors, kPropertyArr[58]);
        SPREAD_OPERATOR_IN_DYNAMIC_CALL = new DiagnosticFactory0DelegateProvider(Severity.ERROR, SourceElementPositioningStrategies.INSTANCE.getSPREAD_OPERATOR(), Reflection.getOrCreateKotlinClass(KtElement.class)).provideDelegate(firJsErrors, kPropertyArr[59]);
        WRONG_OPERATION_WITH_DYNAMIC = new DiagnosticFactory1DelegateProvider(Severity.ERROR, AbstractSourceElementPositioningStrategy.INSTANCE.getDEFAULT(), Reflection.getOrCreateKotlinClass(KtElement.class)).provideDelegate(firJsErrors, kPropertyArr[60]);
        RootDiagnosticRendererFactory.INSTANCE.registerFactory(FirJsErrorsDefaultMessages.INSTANCE);
    }

    public final KtDiagnosticFactory0 getWRONG_JS_QUALIFIER() {
        return (KtDiagnosticFactory0) WRONG_JS_QUALIFIER.getValue(this, $$delegatedProperties[0]);
    }

    public final KtDiagnosticFactory0 getJS_MODULE_PROHIBITED_ON_VAR() {
        return (KtDiagnosticFactory0) JS_MODULE_PROHIBITED_ON_VAR.getValue(this, $$delegatedProperties[1]);
    }

    public final KtDiagnosticFactory0 getJS_MODULE_PROHIBITED_ON_NON_NATIVE() {
        return (KtDiagnosticFactory0) JS_MODULE_PROHIBITED_ON_NON_NATIVE.getValue(this, $$delegatedProperties[2]);
    }

    public final KtDiagnosticFactory0 getNESTED_JS_MODULE_PROHIBITED() {
        return (KtDiagnosticFactory0) NESTED_JS_MODULE_PROHIBITED.getValue(this, $$delegatedProperties[3]);
    }

    public final KtDiagnosticFactory0 getCALL_FROM_UMD_MUST_BE_JS_MODULE_AND_JS_NON_MODULE() {
        return (KtDiagnosticFactory0) CALL_FROM_UMD_MUST_BE_JS_MODULE_AND_JS_NON_MODULE.getValue(this, $$delegatedProperties[4]);
    }

    public final KtDiagnosticFactory1<FirBasedSymbol<?>> getCALL_TO_JS_MODULE_WITHOUT_MODULE_SYSTEM() {
        return (KtDiagnosticFactory1) CALL_TO_JS_MODULE_WITHOUT_MODULE_SYSTEM.getValue(this, $$delegatedProperties[5]);
    }

    public final KtDiagnosticFactory1<FirBasedSymbol<?>> getCALL_TO_JS_NON_MODULE_WITH_MODULE_SYSTEM() {
        return (KtDiagnosticFactory1) CALL_TO_JS_NON_MODULE_WITH_MODULE_SYSTEM.getValue(this, $$delegatedProperties[6]);
    }

    public final KtDiagnosticFactory0 getRUNTIME_ANNOTATION_NOT_SUPPORTED() {
        return (KtDiagnosticFactory0) RUNTIME_ANNOTATION_NOT_SUPPORTED.getValue(this, $$delegatedProperties[7]);
    }

    public final KtDiagnosticFactory0 getRUNTIME_ANNOTATION_ON_EXTERNAL_DECLARATION() {
        return (KtDiagnosticFactory0) RUNTIME_ANNOTATION_ON_EXTERNAL_DECLARATION.getValue(this, $$delegatedProperties[8]);
    }

    public final KtDiagnosticFactory1<ConeKotlinType> getNATIVE_ANNOTATIONS_ALLOWED_ONLY_ON_MEMBER_OR_EXTENSION_FUN() {
        return (KtDiagnosticFactory1) NATIVE_ANNOTATIONS_ALLOWED_ONLY_ON_MEMBER_OR_EXTENSION_FUN.getValue(this, $$delegatedProperties[9]);
    }

    public final KtDiagnosticFactory1<String> getNATIVE_INDEXER_KEY_SHOULD_BE_STRING_OR_NUMBER() {
        return (KtDiagnosticFactory1) NATIVE_INDEXER_KEY_SHOULD_BE_STRING_OR_NUMBER.getValue(this, $$delegatedProperties[10]);
    }

    public final KtDiagnosticFactory2<Integer, String> getNATIVE_INDEXER_WRONG_PARAMETER_COUNT() {
        return (KtDiagnosticFactory2) NATIVE_INDEXER_WRONG_PARAMETER_COUNT.getValue(this, $$delegatedProperties[11]);
    }

    public final KtDiagnosticFactory1<String> getNATIVE_INDEXER_CAN_NOT_HAVE_DEFAULT_ARGUMENTS() {
        return (KtDiagnosticFactory1) NATIVE_INDEXER_CAN_NOT_HAVE_DEFAULT_ARGUMENTS.getValue(this, $$delegatedProperties[12]);
    }

    public final KtDiagnosticFactory0 getNATIVE_GETTER_RETURN_TYPE_SHOULD_BE_NULLABLE() {
        return (KtDiagnosticFactory0) NATIVE_GETTER_RETURN_TYPE_SHOULD_BE_NULLABLE.getValue(this, $$delegatedProperties[13]);
    }

    public final KtDiagnosticFactory0 getNATIVE_SETTER_WRONG_RETURN_TYPE() {
        return (KtDiagnosticFactory0) NATIVE_SETTER_WRONG_RETURN_TYPE.getValue(this, $$delegatedProperties[14]);
    }

    public final KtDiagnosticFactory0 getJS_NAME_IS_NOT_ON_ALL_ACCESSORS() {
        return (KtDiagnosticFactory0) JS_NAME_IS_NOT_ON_ALL_ACCESSORS.getValue(this, $$delegatedProperties[15]);
    }

    public final KtDiagnosticFactory0 getJS_NAME_PROHIBITED_FOR_NAMED_NATIVE() {
        return (KtDiagnosticFactory0) JS_NAME_PROHIBITED_FOR_NAMED_NATIVE.getValue(this, $$delegatedProperties[16]);
    }

    public final KtDiagnosticFactory0 getJS_NAME_PROHIBITED_FOR_OVERRIDE() {
        return (KtDiagnosticFactory0) JS_NAME_PROHIBITED_FOR_OVERRIDE.getValue(this, $$delegatedProperties[17]);
    }

    public final KtDiagnosticFactory0 getJS_NAME_ON_PRIMARY_CONSTRUCTOR_PROHIBITED() {
        return (KtDiagnosticFactory0) JS_NAME_ON_PRIMARY_CONSTRUCTOR_PROHIBITED.getValue(this, $$delegatedProperties[18]);
    }

    public final KtDiagnosticFactory0 getJS_NAME_ON_ACCESSOR_AND_PROPERTY() {
        return (KtDiagnosticFactory0) JS_NAME_ON_ACCESSOR_AND_PROPERTY.getValue(this, $$delegatedProperties[19]);
    }

    public final KtDiagnosticFactory0 getJS_NAME_PROHIBITED_FOR_EXTENSION_PROPERTY() {
        return (KtDiagnosticFactory0) JS_NAME_PROHIBITED_FOR_EXTENSION_PROPERTY.getValue(this, $$delegatedProperties[20]);
    }

    public final KtDiagnosticFactory1<String> getJS_BUILTIN_NAME_CLASH() {
        return (KtDiagnosticFactory1) JS_BUILTIN_NAME_CLASH.getValue(this, $$delegatedProperties[21]);
    }

    public final KtDiagnosticFactory0 getNAME_CONTAINS_ILLEGAL_CHARS() {
        return (KtDiagnosticFactory0) NAME_CONTAINS_ILLEGAL_CHARS.getValue(this, $$delegatedProperties[22]);
    }

    public final KtDiagnosticFactory1<FirCallableSymbol<?>> getWRONG_MULTIPLE_INHERITANCE() {
        return (KtDiagnosticFactory1) WRONG_MULTIPLE_INHERITANCE.getValue(this, $$delegatedProperties[23]);
    }

    public final KtDiagnosticFactory0 getIMPLEMENTING_FUNCTION_INTERFACE() {
        return (KtDiagnosticFactory0) IMPLEMENTING_FUNCTION_INTERFACE.getValue(this, $$delegatedProperties[24]);
    }

    public final KtDiagnosticFactory0 getOVERRIDING_EXTERNAL_FUN_WITH_OPTIONAL_PARAMS() {
        return (KtDiagnosticFactory0) OVERRIDING_EXTERNAL_FUN_WITH_OPTIONAL_PARAMS.getValue(this, $$delegatedProperties[25]);
    }

    public final KtDiagnosticFactory1<FirNamedFunctionSymbol> getOVERRIDING_EXTERNAL_FUN_WITH_OPTIONAL_PARAMS_WITH_FAKE() {
        return (KtDiagnosticFactory1) OVERRIDING_EXTERNAL_FUN_WITH_OPTIONAL_PARAMS_WITH_FAKE.getValue(this, $$delegatedProperties[26]);
    }

    public final KtDiagnosticFactory0 getCALL_TO_DEFINED_EXTERNALLY_FROM_NON_EXTERNAL_DECLARATION() {
        return (KtDiagnosticFactory0) CALL_TO_DEFINED_EXTERNALLY_FROM_NON_EXTERNAL_DECLARATION.getValue(this, $$delegatedProperties[27]);
    }

    public final KtDiagnosticFactory0 getEXTERNAL_CLASS_CONSTRUCTOR_PROPERTY_PARAMETER() {
        return (KtDiagnosticFactory0) EXTERNAL_CLASS_CONSTRUCTOR_PROPERTY_PARAMETER.getValue(this, $$delegatedProperties[28]);
    }

    public final KtDiagnosticFactory0 getEXTERNAL_ENUM_ENTRY_WITH_BODY() {
        return (KtDiagnosticFactory0) EXTERNAL_ENUM_ENTRY_WITH_BODY.getValue(this, $$delegatedProperties[29]);
    }

    public final KtDiagnosticFactory0 getEXTERNAL_ANONYMOUS_INITIALIZER() {
        return (KtDiagnosticFactory0) EXTERNAL_ANONYMOUS_INITIALIZER.getValue(this, $$delegatedProperties[30]);
    }

    public final KtDiagnosticFactory0 getEXTERNAL_DELEGATION() {
        return (KtDiagnosticFactory0) EXTERNAL_DELEGATION.getValue(this, $$delegatedProperties[31]);
    }

    public final KtDiagnosticFactory0 getEXTERNAL_DELEGATED_CONSTRUCTOR_CALL() {
        return (KtDiagnosticFactory0) EXTERNAL_DELEGATED_CONSTRUCTOR_CALL.getValue(this, $$delegatedProperties[32]);
    }

    public final KtDiagnosticFactory0 getWRONG_BODY_OF_EXTERNAL_DECLARATION() {
        return (KtDiagnosticFactory0) WRONG_BODY_OF_EXTERNAL_DECLARATION.getValue(this, $$delegatedProperties[33]);
    }

    public final KtDiagnosticFactory0 getWRONG_INITIALIZER_OF_EXTERNAL_DECLARATION() {
        return (KtDiagnosticFactory0) WRONG_INITIALIZER_OF_EXTERNAL_DECLARATION.getValue(this, $$delegatedProperties[34]);
    }

    public final KtDiagnosticFactory0 getWRONG_DEFAULT_VALUE_FOR_EXTERNAL_FUN_PARAMETER() {
        return (KtDiagnosticFactory0) WRONG_DEFAULT_VALUE_FOR_EXTERNAL_FUN_PARAMETER.getValue(this, $$delegatedProperties[35]);
    }

    public final KtDiagnosticFactory0 getNESTED_EXTERNAL_DECLARATION() {
        return (KtDiagnosticFactory0) NESTED_EXTERNAL_DECLARATION.getValue(this, $$delegatedProperties[36]);
    }

    public final KtDiagnosticFactory1<String> getWRONG_EXTERNAL_DECLARATION() {
        return (KtDiagnosticFactory1) WRONG_EXTERNAL_DECLARATION.getValue(this, $$delegatedProperties[37]);
    }

    public final KtDiagnosticFactory0 getNESTED_CLASS_IN_EXTERNAL_INTERFACE() {
        return (KtDiagnosticFactory0) NESTED_CLASS_IN_EXTERNAL_INTERFACE.getValue(this, $$delegatedProperties[38]);
    }

    public final KtDiagnosticFactory0 getEXTERNAL_TYPE_EXTENDS_NON_EXTERNAL_TYPE() {
        return (KtDiagnosticFactory0) EXTERNAL_TYPE_EXTENDS_NON_EXTERNAL_TYPE.getValue(this, $$delegatedProperties[39]);
    }

    public final KtDiagnosticFactory0 getINLINE_EXTERNAL_DECLARATION() {
        return (KtDiagnosticFactory0) INLINE_EXTERNAL_DECLARATION.getValue(this, $$delegatedProperties[40]);
    }

    public final KtDiagnosticFactory0 getENUM_CLASS_IN_EXTERNAL_DECLARATION_WARNING() {
        return (KtDiagnosticFactory0) ENUM_CLASS_IN_EXTERNAL_DECLARATION_WARNING.getValue(this, $$delegatedProperties[41]);
    }

    public final KtDiagnosticFactory0 getINLINE_CLASS_IN_EXTERNAL_DECLARATION_WARNING() {
        return (KtDiagnosticFactory0) INLINE_CLASS_IN_EXTERNAL_DECLARATION_WARNING.getValue(this, $$delegatedProperties[42]);
    }

    public final KtDiagnosticFactory0 getINLINE_CLASS_IN_EXTERNAL_DECLARATION() {
        return (KtDiagnosticFactory0) INLINE_CLASS_IN_EXTERNAL_DECLARATION.getValue(this, $$delegatedProperties[43]);
    }

    public final KtDiagnosticFactory0 getEXTENSION_FUNCTION_IN_EXTERNAL_DECLARATION() {
        return (KtDiagnosticFactory0) EXTENSION_FUNCTION_IN_EXTERNAL_DECLARATION.getValue(this, $$delegatedProperties[44]);
    }

    public final KtDiagnosticFactory0 getNON_ABSTRACT_MEMBER_OF_EXTERNAL_INTERFACE() {
        return (KtDiagnosticFactory0) NON_ABSTRACT_MEMBER_OF_EXTERNAL_INTERFACE.getValue(this, $$delegatedProperties[45]);
    }

    public final KtDiagnosticFactory1<ConeKotlinType> getNON_EXTERNAL_DECLARATION_IN_INAPPROPRIATE_FILE() {
        return (KtDiagnosticFactory1) NON_EXTERNAL_DECLARATION_IN_INAPPROPRIATE_FILE.getValue(this, $$delegatedProperties[46]);
    }

    public final KtDiagnosticFactory1<ConeKotlinType> getCANNOT_CHECK_FOR_EXTERNAL_INTERFACE() {
        return (KtDiagnosticFactory1) CANNOT_CHECK_FOR_EXTERNAL_INTERFACE.getValue(this, $$delegatedProperties[47]);
    }

    public final KtDiagnosticFactory2<ConeKotlinType, ConeKotlinType> getUNCHECKED_CAST_TO_EXTERNAL_INTERFACE() {
        return (KtDiagnosticFactory2) UNCHECKED_CAST_TO_EXTERNAL_INTERFACE.getValue(this, $$delegatedProperties[48]);
    }

    public final KtDiagnosticFactory0 getEXTERNAL_INTERFACE_AS_CLASS_LITERAL() {
        return (KtDiagnosticFactory0) EXTERNAL_INTERFACE_AS_CLASS_LITERAL.getValue(this, $$delegatedProperties[49]);
    }

    public final KtDiagnosticFactory2<FirClassLikeSymbol<?>, FirClassLikeSymbol<?>> getJS_EXTERNAL_INHERITORS_ONLY() {
        return (KtDiagnosticFactory2) JS_EXTERNAL_INHERITORS_ONLY.getValue(this, $$delegatedProperties[50]);
    }

    public final KtDiagnosticFactory1<ConeKotlinType> getJS_EXTERNAL_ARGUMENT() {
        return (KtDiagnosticFactory1) JS_EXTERNAL_ARGUMENT.getValue(this, $$delegatedProperties[51]);
    }

    public final KtDiagnosticFactory1<ConeKotlinType> getEXTERNAL_INTERFACE_AS_REIFIED_TYPE_ARGUMENT() {
        return (KtDiagnosticFactory1) EXTERNAL_INTERFACE_AS_REIFIED_TYPE_ARGUMENT.getValue(this, $$delegatedProperties[52]);
    }

    public final KtDiagnosticFactory0 getNESTED_JS_EXPORT() {
        return (KtDiagnosticFactory0) NESTED_JS_EXPORT.getValue(this, $$delegatedProperties[53]);
    }

    public final KtDiagnosticFactory1<String> getWRONG_EXPORTED_DECLARATION() {
        return (KtDiagnosticFactory1) WRONG_EXPORTED_DECLARATION.getValue(this, $$delegatedProperties[54]);
    }

    public final KtDiagnosticFactory2<String, ConeKotlinType> getNON_EXPORTABLE_TYPE() {
        return (KtDiagnosticFactory2) NON_EXPORTABLE_TYPE.getValue(this, $$delegatedProperties[55]);
    }

    public final KtDiagnosticFactory1<String> getNON_CONSUMABLE_EXPORTED_IDENTIFIER() {
        return (KtDiagnosticFactory1) NON_CONSUMABLE_EXPORTED_IDENTIFIER.getValue(this, $$delegatedProperties[56]);
    }

    public final KtDiagnosticFactory0 getDELEGATION_BY_DYNAMIC() {
        return (KtDiagnosticFactory0) DELEGATION_BY_DYNAMIC.getValue(this, $$delegatedProperties[57]);
    }

    public final KtDiagnosticFactory0 getPROPERTY_DELEGATION_BY_DYNAMIC() {
        return (KtDiagnosticFactory0) PROPERTY_DELEGATION_BY_DYNAMIC.getValue(this, $$delegatedProperties[58]);
    }

    public final KtDiagnosticFactory0 getSPREAD_OPERATOR_IN_DYNAMIC_CALL() {
        return (KtDiagnosticFactory0) SPREAD_OPERATOR_IN_DYNAMIC_CALL.getValue(this, $$delegatedProperties[59]);
    }

    public final KtDiagnosticFactory1<String> getWRONG_OPERATION_WITH_DYNAMIC() {
        return (KtDiagnosticFactory1) WRONG_OPERATION_WITH_DYNAMIC.getValue(this, $$delegatedProperties[60]);
    }
}
