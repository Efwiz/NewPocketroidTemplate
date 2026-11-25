package org.jetbrains.kotlin.fir.analysis.diagnostics.jvm;

import kotlin.Metadata;
import kotlin.jvm.internal.PropertyReference1Impl;
import kotlin.jvm.internal.Reflection;
import kotlin.properties.ReadOnlyProperty;
import kotlin.reflect.KProperty;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.config.LanguageFeature;
import org.jetbrains.kotlin.diagnostics.AbstractSourceElementPositioningStrategy;
import org.jetbrains.kotlin.diagnostics.DeprecationDiagnosticFactory0DelegateProvider;
import org.jetbrains.kotlin.diagnostics.DeprecationDiagnosticFactory2DelegateProvider;
import org.jetbrains.kotlin.diagnostics.DeprecationDiagnosticFactory4DelegateProvider;
import org.jetbrains.kotlin.diagnostics.DiagnosticFactory0DelegateProvider;
import org.jetbrains.kotlin.diagnostics.DiagnosticFactory1DelegateProvider;
import org.jetbrains.kotlin.diagnostics.DiagnosticFactory2DelegateProvider;
import org.jetbrains.kotlin.diagnostics.KtDiagnosticFactory0;
import org.jetbrains.kotlin.diagnostics.KtDiagnosticFactory1;
import org.jetbrains.kotlin.diagnostics.KtDiagnosticFactory2;
import org.jetbrains.kotlin.diagnostics.KtDiagnosticFactoryForDeprecation0;
import org.jetbrains.kotlin.diagnostics.KtDiagnosticFactoryForDeprecation2;
import org.jetbrains.kotlin.diagnostics.KtDiagnosticFactoryForDeprecation4;
import org.jetbrains.kotlin.diagnostics.Severity;
import org.jetbrains.kotlin.diagnostics.SourceElementPositioningStrategies;
import org.jetbrains.kotlin.diagnostics.rendering.RootDiagnosticRendererFactory;
import org.jetbrains.kotlin.fir.symbols.impl.FirCallableSymbol;
import org.jetbrains.kotlin.fir.types.ConeKotlinType;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.psi.KtAnnotationEntry;
import org.jetbrains.kotlin.psi.KtDeclaration;
import org.jetbrains.kotlin.psi.KtElement;
import org.jetbrains.kotlin.psi.KtExpression;

/* JADX INFO: compiled from: FirJvmErrors.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\\\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u001f\n\u0002\u0010\u000e\n\u0002\b\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\bd\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0019\n\u0002\u0018\u0002\n\u0002\b\u0015\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u001b\u0010\u0003\u001a\u00020\u00048FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b\u0007\u0010\b\u001a\u0004\b\u0005\u0010\u0006R\u001b\u0010\t\u001a\u00020\n8FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b\r\u0010\b\u001a\u0004\b\u000b\u0010\fR\u001b\u0010\u000e\u001a\u00020\n8FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b\u0010\u0010\b\u001a\u0004\b\u000f\u0010\fR!\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00130\u00128FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b\u0016\u0010\b\u001a\u0004\b\u0014\u0010\u0015R\u001b\u0010\u0017\u001a\u00020\n8FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b\u0019\u0010\b\u001a\u0004\b\u0018\u0010\fR\u001b\u0010\u001a\u001a\u00020\n8FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b\u001c\u0010\b\u001a\u0004\b\u001b\u0010\fR\u001b\u0010\u001d\u001a\u00020\n8FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b\u001f\u0010\b\u001a\u0004\b\u001e\u0010\fR\u001b\u0010 \u001a\u00020\n8FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b\"\u0010\b\u001a\u0004\b!\u0010\fR\u001b\u0010#\u001a\u00020\n8FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b%\u0010\b\u001a\u0004\b$\u0010\fR\u001b\u0010&\u001a\u00020\n8FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b(\u0010\b\u001a\u0004\b'\u0010\fR\u001b\u0010)\u001a\u00020\n8FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b+\u0010\b\u001a\u0004\b*\u0010\fR\u001b\u0010,\u001a\u00020\n8FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b.\u0010\b\u001a\u0004\b-\u0010\fR\u001b\u0010/\u001a\u00020\n8FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b1\u0010\b\u001a\u0004\b0\u0010\fR!\u00102\u001a\b\u0012\u0004\u0012\u0002030\u00128FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b5\u0010\b\u001a\u0004\b4\u0010\u0015R!\u00106\u001a\b\u0012\u0004\u0012\u0002030\u00128FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b8\u0010\b\u001a\u0004\b7\u0010\u0015R\u001b\u00109\u001a\u00020\n8FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b;\u0010\b\u001a\u0004\b:\u0010\fR\u001b\u0010<\u001a\u00020\n8FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b>\u0010\b\u001a\u0004\b=\u0010\fR\u001b\u0010?\u001a\u00020\n8FX\u0086\u0084\u0002¢\u0006\f\n\u0004\bA\u0010\b\u001a\u0004\b@\u0010\fR\u001b\u0010B\u001a\u00020\n8FX\u0086\u0084\u0002¢\u0006\f\n\u0004\bD\u0010\b\u001a\u0004\bC\u0010\fR'\u0010E\u001a\u000e\u0012\u0004\u0012\u00020G\u0012\u0004\u0012\u00020G0F8FX\u0086\u0084\u0002¢\u0006\f\n\u0004\bJ\u0010\b\u001a\u0004\bH\u0010IR!\u0010K\u001a\b\u0012\u0004\u0012\u0002030\u00128FX\u0086\u0084\u0002¢\u0006\f\n\u0004\bM\u0010\b\u001a\u0004\bL\u0010\u0015R\u001b\u0010N\u001a\u00020\n8FX\u0086\u0084\u0002¢\u0006\f\n\u0004\bP\u0010\b\u001a\u0004\bO\u0010\fR\u001b\u0010Q\u001a\u00020\n8FX\u0086\u0084\u0002¢\u0006\f\n\u0004\bS\u0010\b\u001a\u0004\bR\u0010\fR\u001b\u0010T\u001a\u00020\n8FX\u0086\u0084\u0002¢\u0006\f\n\u0004\bV\u0010\b\u001a\u0004\bU\u0010\fR\u001b\u0010W\u001a\u00020\n8FX\u0086\u0084\u0002¢\u0006\f\n\u0004\bY\u0010\b\u001a\u0004\bX\u0010\fR\u001b\u0010Z\u001a\u00020\n8FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b\\\u0010\b\u001a\u0004\b[\u0010\fR\u001b\u0010]\u001a\u00020\n8FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b_\u0010\b\u001a\u0004\b^\u0010\fR!\u0010`\u001a\b\u0012\u0004\u0012\u00020G0\u00128FX\u0086\u0084\u0002¢\u0006\f\n\u0004\bb\u0010\b\u001a\u0004\ba\u0010\u0015R\u001b\u0010c\u001a\u00020\n8FX\u0086\u0084\u0002¢\u0006\f\n\u0004\be\u0010\b\u001a\u0004\bd\u0010\fR\u001b\u0010f\u001a\u00020\n8FX\u0086\u0084\u0002¢\u0006\f\n\u0004\bh\u0010\b\u001a\u0004\bg\u0010\fR\u001b\u0010i\u001a\u00020\n8FX\u0086\u0084\u0002¢\u0006\f\n\u0004\bk\u0010\b\u001a\u0004\bj\u0010\fR\u001b\u0010l\u001a\u00020\n8FX\u0086\u0084\u0002¢\u0006\f\n\u0004\bn\u0010\b\u001a\u0004\bm\u0010\fR\u001b\u0010o\u001a\u00020\n8FX\u0086\u0084\u0002¢\u0006\f\n\u0004\bq\u0010\b\u001a\u0004\bp\u0010\fR\u001b\u0010r\u001a\u00020\n8FX\u0086\u0084\u0002¢\u0006\f\n\u0004\bt\u0010\b\u001a\u0004\bs\u0010\fR\u001b\u0010u\u001a\u00020\n8FX\u0086\u0084\u0002¢\u0006\f\n\u0004\bw\u0010\b\u001a\u0004\bv\u0010\fR\u001b\u0010x\u001a\u00020\n8FX\u0086\u0084\u0002¢\u0006\f\n\u0004\bz\u0010\b\u001a\u0004\by\u0010\fR\u001b\u0010{\u001a\u00020\n8FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b}\u0010\b\u001a\u0004\b|\u0010\fR\u001c\u0010~\u001a\u00020\n8FX\u0086\u0084\u0002¢\u0006\r\n\u0005\b\u0080\u0001\u0010\b\u001a\u0004\b\u007f\u0010\fR\u001e\u0010\u0081\u0001\u001a\u00020\n8FX\u0086\u0084\u0002¢\u0006\u000e\n\u0005\b\u0083\u0001\u0010\b\u001a\u0005\b\u0082\u0001\u0010\fR\u001e\u0010\u0084\u0001\u001a\u00020\n8FX\u0086\u0084\u0002¢\u0006\u000e\n\u0005\b\u0086\u0001\u0010\b\u001a\u0005\b\u0085\u0001\u0010\fR\u001e\u0010\u0087\u0001\u001a\u00020\n8FX\u0086\u0084\u0002¢\u0006\u000e\n\u0005\b\u0089\u0001\u0010\b\u001a\u0005\b\u0088\u0001\u0010\fR\u001e\u0010\u008a\u0001\u001a\u00020\n8FX\u0086\u0084\u0002¢\u0006\u000e\n\u0005\b\u008c\u0001\u0010\b\u001a\u0005\b\u008b\u0001\u0010\fR\u001e\u0010\u008d\u0001\u001a\u00020\n8FX\u0086\u0084\u0002¢\u0006\u000e\n\u0005\b\u008f\u0001\u0010\b\u001a\u0005\b\u008e\u0001\u0010\fR\u001e\u0010\u0090\u0001\u001a\u00020\u00048FX\u0086\u0084\u0002¢\u0006\u000e\n\u0005\b\u0092\u0001\u0010\b\u001a\u0005\b\u0091\u0001\u0010\u0006R\u001e\u0010\u0093\u0001\u001a\u00020\n8FX\u0086\u0084\u0002¢\u0006\u000e\n\u0005\b\u0095\u0001\u0010\b\u001a\u0005\b\u0094\u0001\u0010\fR\u001e\u0010\u0096\u0001\u001a\u00020\n8FX\u0086\u0084\u0002¢\u0006\u000e\n\u0005\b\u0098\u0001\u0010\b\u001a\u0005\b\u0097\u0001\u0010\fR\u001e\u0010\u0099\u0001\u001a\u00020\n8FX\u0086\u0084\u0002¢\u0006\u000e\n\u0005\b\u009b\u0001\u0010\b\u001a\u0005\b\u009a\u0001\u0010\fR\u001e\u0010\u009c\u0001\u001a\u00020\n8FX\u0086\u0084\u0002¢\u0006\u000e\n\u0005\b\u009e\u0001\u0010\b\u001a\u0005\b\u009d\u0001\u0010\fR\u001e\u0010\u009f\u0001\u001a\u00020\n8FX\u0086\u0084\u0002¢\u0006\u000e\n\u0005\b¡\u0001\u0010\b\u001a\u0005\b \u0001\u0010\fR\u001e\u0010¢\u0001\u001a\u00020\n8FX\u0086\u0084\u0002¢\u0006\u000e\n\u0005\b¤\u0001\u0010\b\u001a\u0005\b£\u0001\u0010\fR*\u0010¥\u0001\u001a\u000e\u0012\u0004\u0012\u00020\u0013\u0012\u0004\u0012\u00020\u00130F8FX\u0086\u0084\u0002¢\u0006\u000e\n\u0005\b§\u0001\u0010\b\u001a\u0005\b¦\u0001\u0010IR\u001e\u0010¨\u0001\u001a\u00020\u00048FX\u0086\u0084\u0002¢\u0006\u000e\n\u0005\bª\u0001\u0010\b\u001a\u0005\b©\u0001\u0010\u0006R.\u0010«\u0001\u001a\u0011\u0012\u0005\u0012\u00030\u00ad\u0001\u0012\u0005\u0012\u00030®\u00010¬\u00018FX\u0086\u0084\u0002¢\u0006\u000f\n\u0005\b±\u0001\u0010\b\u001a\u0006\b¯\u0001\u0010°\u0001R:\u0010²\u0001\u001a\u001d\u0012\u0005\u0012\u00030\u00ad\u0001\u0012\u0004\u0012\u000203\u0012\u0005\u0012\u00030\u00ad\u0001\u0012\u0004\u0012\u0002030³\u00018FX\u0086\u0084\u0002¢\u0006\u000f\n\u0005\b¶\u0001\u0010\b\u001a\u0006\b´\u0001\u0010µ\u0001R.\u0010·\u0001\u001a\u0011\u0012\u0005\u0012\u00030\u00ad\u0001\u0012\u0005\u0012\u00030\u00ad\u00010¬\u00018FX\u0086\u0084\u0002¢\u0006\u000f\n\u0005\b¹\u0001\u0010\b\u001a\u0006\b¸\u0001\u0010°\u0001R.\u0010º\u0001\u001a\u0011\u0012\u0005\u0012\u00030\u00ad\u0001\u0012\u0005\u0012\u00030\u00ad\u00010¬\u00018FX\u0086\u0084\u0002¢\u0006\u000f\n\u0005\b¼\u0001\u0010\b\u001a\u0006\b»\u0001\u0010°\u0001R,\u0010½\u0001\u001a\u0010\u0012\u0005\u0012\u00030\u00ad\u0001\u0012\u0005\u0012\u00030\u00ad\u00010F8FX\u0086\u0084\u0002¢\u0006\u000e\n\u0005\b¿\u0001\u0010\b\u001a\u0005\b¾\u0001\u0010IR\u001e\u0010À\u0001\u001a\u00020\u00048FX\u0086\u0084\u0002¢\u0006\u000e\n\u0005\bÂ\u0001\u0010\b\u001a\u0005\bÁ\u0001\u0010\u0006R\u001e\u0010Ã\u0001\u001a\u00020\n8FX\u0086\u0084\u0002¢\u0006\u000e\n\u0005\bÅ\u0001\u0010\b\u001a\u0005\bÄ\u0001\u0010\fR\u001e\u0010Æ\u0001\u001a\u00020\n8FX\u0086\u0084\u0002¢\u0006\u000e\n\u0005\bÈ\u0001\u0010\b\u001a\u0005\bÇ\u0001\u0010\fR$\u0010É\u0001\u001a\b\u0012\u0004\u0012\u0002030\u00128FX\u0086\u0084\u0002¢\u0006\u000e\n\u0005\bË\u0001\u0010\b\u001a\u0005\bÊ\u0001\u0010\u0015R)\u0010Ì\u0001\u001a\r\u0012\t\u0012\u0007\u0012\u0002\b\u00030Í\u00010\u00128FX\u0086\u0084\u0002¢\u0006\u000e\n\u0005\bÏ\u0001\u0010\b\u001a\u0005\bÎ\u0001\u0010\u0015R\u001e\u0010Ð\u0001\u001a\u00020\n8FX\u0086\u0084\u0002¢\u0006\u000e\n\u0005\bÒ\u0001\u0010\b\u001a\u0005\bÑ\u0001\u0010\fR\u001e\u0010Ó\u0001\u001a\u00020\n8FX\u0086\u0084\u0002¢\u0006\u000e\n\u0005\bÕ\u0001\u0010\b\u001a\u0005\bÔ\u0001\u0010\fR\u001e\u0010Ö\u0001\u001a\u00020\n8FX\u0086\u0084\u0002¢\u0006\u000e\n\u0005\bØ\u0001\u0010\b\u001a\u0005\b×\u0001\u0010\fR\u001e\u0010Ù\u0001\u001a\u00020\u00048FX\u0086\u0084\u0002¢\u0006\u000e\n\u0005\bÛ\u0001\u0010\b\u001a\u0005\bÚ\u0001\u0010\u0006R\u001e\u0010Ü\u0001\u001a\u00020\n8FX\u0086\u0084\u0002¢\u0006\u000e\n\u0005\bÞ\u0001\u0010\b\u001a\u0005\bÝ\u0001\u0010\fR\u001e\u0010ß\u0001\u001a\u00020\n8FX\u0086\u0084\u0002¢\u0006\u000e\n\u0005\bá\u0001\u0010\b\u001a\u0005\bà\u0001\u0010\f¨\u0006â\u0001"}, d2 = {"Lorg/jetbrains/kotlin/fir/analysis/diagnostics/jvm/FirJvmErrors;", "", "()V", "CONCURRENT_HASH_MAP_CONTAINS_OPERATOR", "Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticFactoryForDeprecation0;", "getCONCURRENT_HASH_MAP_CONTAINS_OPERATOR", "()Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticFactoryForDeprecation0;", "CONCURRENT_HASH_MAP_CONTAINS_OPERATOR$delegate", "Lkotlin/properties/ReadOnlyProperty;", "CONFLICTING_JVM_DECLARATIONS", "Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticFactory0;", "getCONFLICTING_JVM_DECLARATIONS", "()Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticFactory0;", "CONFLICTING_JVM_DECLARATIONS$delegate", "DELEGATION_BY_IN_JVM_RECORD", "getDELEGATION_BY_IN_JVM_RECORD", "DELEGATION_BY_IN_JVM_RECORD$delegate", "DEPRECATED_JAVA_ANNOTATION", "Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticFactory1;", "Lorg/jetbrains/kotlin/name/FqName;", "getDEPRECATED_JAVA_ANNOTATION", "()Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticFactory1;", "DEPRECATED_JAVA_ANNOTATION$delegate", "ENUM_JVM_RECORD", "getENUM_JVM_RECORD", "ENUM_JVM_RECORD$delegate", "EXTERNAL_DECLARATION_CANNOT_BE_ABSTRACT", "getEXTERNAL_DECLARATION_CANNOT_BE_ABSTRACT", "EXTERNAL_DECLARATION_CANNOT_BE_ABSTRACT$delegate", "EXTERNAL_DECLARATION_CANNOT_BE_INLINED", "getEXTERNAL_DECLARATION_CANNOT_BE_INLINED", "EXTERNAL_DECLARATION_CANNOT_BE_INLINED$delegate", "EXTERNAL_DECLARATION_CANNOT_HAVE_BODY", "getEXTERNAL_DECLARATION_CANNOT_HAVE_BODY", "EXTERNAL_DECLARATION_CANNOT_HAVE_BODY$delegate", "EXTERNAL_DECLARATION_IN_INTERFACE", "getEXTERNAL_DECLARATION_IN_INTERFACE", "EXTERNAL_DECLARATION_IN_INTERFACE$delegate", "FIELD_IN_JVM_RECORD", "getFIELD_IN_JVM_RECORD", "FIELD_IN_JVM_RECORD$delegate", "FUNCTION_DELEGATE_MEMBER_NAME_CLASH", "getFUNCTION_DELEGATE_MEMBER_NAME_CLASH", "FUNCTION_DELEGATE_MEMBER_NAME_CLASH$delegate", "ILLEGAL_JAVA_LANG_RECORD_SUPERTYPE", "getILLEGAL_JAVA_LANG_RECORD_SUPERTYPE", "ILLEGAL_JAVA_LANG_RECORD_SUPERTYPE$delegate", "ILLEGAL_JVM_NAME", "getILLEGAL_JVM_NAME", "ILLEGAL_JVM_NAME$delegate", "INAPPLICABLE_JVM_FIELD", "", "getINAPPLICABLE_JVM_FIELD", "INAPPLICABLE_JVM_FIELD$delegate", "INAPPLICABLE_JVM_FIELD_WARNING", "getINAPPLICABLE_JVM_FIELD_WARNING", "INAPPLICABLE_JVM_FIELD_WARNING$delegate", "INAPPLICABLE_JVM_NAME", "getINAPPLICABLE_JVM_NAME", "INAPPLICABLE_JVM_NAME$delegate", "INNER_JVM_RECORD", "getINNER_JVM_RECORD", "INNER_JVM_RECORD$delegate", "INTERFACE_CANT_CALL_DEFAULT_METHOD_VIA_SUPER", "getINTERFACE_CANT_CALL_DEFAULT_METHOD_VIA_SUPER", "INTERFACE_CANT_CALL_DEFAULT_METHOD_VIA_SUPER$delegate", "JAVA_SAM_INTERFACE_CONSTRUCTOR_REFERENCE", "getJAVA_SAM_INTERFACE_CONSTRUCTOR_REFERENCE", "JAVA_SAM_INTERFACE_CONSTRUCTOR_REFERENCE$delegate", "JAVA_TYPE_MISMATCH", "Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticFactory2;", "Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;", "getJAVA_TYPE_MISMATCH", "()Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticFactory2;", "JAVA_TYPE_MISMATCH$delegate", "JVM_DEFAULT_IN_DECLARATION", "getJVM_DEFAULT_IN_DECLARATION", "JVM_DEFAULT_IN_DECLARATION$delegate", "JVM_DEFAULT_WITH_COMPATIBILITY_IN_DECLARATION", "getJVM_DEFAULT_WITH_COMPATIBILITY_IN_DECLARATION", "JVM_DEFAULT_WITH_COMPATIBILITY_IN_DECLARATION$delegate", "JVM_DEFAULT_WITH_COMPATIBILITY_NOT_ON_INTERFACE", "getJVM_DEFAULT_WITH_COMPATIBILITY_NOT_ON_INTERFACE", "JVM_DEFAULT_WITH_COMPATIBILITY_NOT_ON_INTERFACE$delegate", "JVM_INLINE_WITHOUT_VALUE_CLASS", "getJVM_INLINE_WITHOUT_VALUE_CLASS", "JVM_INLINE_WITHOUT_VALUE_CLASS$delegate", "JVM_PACKAGE_NAME_CANNOT_BE_EMPTY", "getJVM_PACKAGE_NAME_CANNOT_BE_EMPTY", "JVM_PACKAGE_NAME_CANNOT_BE_EMPTY$delegate", "JVM_PACKAGE_NAME_MUST_BE_VALID_NAME", "getJVM_PACKAGE_NAME_MUST_BE_VALID_NAME", "JVM_PACKAGE_NAME_MUST_BE_VALID_NAME$delegate", "JVM_PACKAGE_NAME_NOT_SUPPORTED_IN_FILES_WITH_CLASSES", "getJVM_PACKAGE_NAME_NOT_SUPPORTED_IN_FILES_WITH_CLASSES", "JVM_PACKAGE_NAME_NOT_SUPPORTED_IN_FILES_WITH_CLASSES$delegate", "JVM_RECORD_EXTENDS_CLASS", "getJVM_RECORD_EXTENDS_CLASS", "JVM_RECORD_EXTENDS_CLASS$delegate", "JVM_RECORD_NOT_LAST_VARARG_PARAMETER", "getJVM_RECORD_NOT_LAST_VARARG_PARAMETER", "JVM_RECORD_NOT_LAST_VARARG_PARAMETER$delegate", "JVM_RECORD_NOT_VAL_PARAMETER", "getJVM_RECORD_NOT_VAL_PARAMETER", "JVM_RECORD_NOT_VAL_PARAMETER$delegate", "JVM_RECORD_WITHOUT_PRIMARY_CONSTRUCTOR_PARAMETERS", "getJVM_RECORD_WITHOUT_PRIMARY_CONSTRUCTOR_PARAMETERS", "JVM_RECORD_WITHOUT_PRIMARY_CONSTRUCTOR_PARAMETERS$delegate", "JVM_STATIC_NOT_IN_OBJECT_OR_CLASS_COMPANION", "getJVM_STATIC_NOT_IN_OBJECT_OR_CLASS_COMPANION", "JVM_STATIC_NOT_IN_OBJECT_OR_CLASS_COMPANION$delegate", "JVM_STATIC_NOT_IN_OBJECT_OR_COMPANION", "getJVM_STATIC_NOT_IN_OBJECT_OR_COMPANION", "JVM_STATIC_NOT_IN_OBJECT_OR_COMPANION$delegate", "JVM_STATIC_ON_CONST_OR_JVM_FIELD", "getJVM_STATIC_ON_CONST_OR_JVM_FIELD", "JVM_STATIC_ON_CONST_OR_JVM_FIELD$delegate", "JVM_STATIC_ON_EXTERNAL_IN_INTERFACE", "getJVM_STATIC_ON_EXTERNAL_IN_INTERFACE", "JVM_STATIC_ON_EXTERNAL_IN_INTERFACE$delegate", "JVM_STATIC_ON_NON_PUBLIC_MEMBER", "getJVM_STATIC_ON_NON_PUBLIC_MEMBER", "JVM_STATIC_ON_NON_PUBLIC_MEMBER$delegate", "JVM_SYNTHETIC_ON_DELEGATE", "getJVM_SYNTHETIC_ON_DELEGATE", "JVM_SYNTHETIC_ON_DELEGATE$delegate", "LOCAL_JVM_RECORD", "getLOCAL_JVM_RECORD", "LOCAL_JVM_RECORD$delegate", "NON_DATA_CLASS_JVM_RECORD", "getNON_DATA_CLASS_JVM_RECORD", "NON_DATA_CLASS_JVM_RECORD$delegate", "NON_FINAL_JVM_RECORD", "getNON_FINAL_JVM_RECORD", "NON_FINAL_JVM_RECORD$delegate", "NON_SOURCE_REPEATED_ANNOTATION", "getNON_SOURCE_REPEATED_ANNOTATION", "NON_SOURCE_REPEATED_ANNOTATION$delegate", "NO_REFLECTION_IN_CLASS_PATH", "getNO_REFLECTION_IN_CLASS_PATH", "NO_REFLECTION_IN_CLASS_PATH$delegate", "OVERLOADS_ABSTRACT", "getOVERLOADS_ABSTRACT", "OVERLOADS_ABSTRACT$delegate", "OVERLOADS_ANNOTATION_CLASS_CONSTRUCTOR", "getOVERLOADS_ANNOTATION_CLASS_CONSTRUCTOR", "OVERLOADS_ANNOTATION_CLASS_CONSTRUCTOR$delegate", "OVERLOADS_INTERFACE", "getOVERLOADS_INTERFACE", "OVERLOADS_INTERFACE$delegate", "OVERLOADS_LOCAL", "getOVERLOADS_LOCAL", "OVERLOADS_LOCAL$delegate", "OVERLOADS_PRIVATE", "getOVERLOADS_PRIVATE", "OVERLOADS_PRIVATE$delegate", "OVERLOADS_WITHOUT_DEFAULT_ARGUMENTS", "getOVERLOADS_WITHOUT_DEFAULT_ARGUMENTS", "OVERLOADS_WITHOUT_DEFAULT_ARGUMENTS$delegate", "OVERRIDE_CANNOT_BE_STATIC", "getOVERRIDE_CANNOT_BE_STATIC", "OVERRIDE_CANNOT_BE_STATIC$delegate", "POSITIONED_VALUE_ARGUMENT_FOR_JAVA_ANNOTATION", "getPOSITIONED_VALUE_ARGUMENT_FOR_JAVA_ANNOTATION", "POSITIONED_VALUE_ARGUMENT_FOR_JAVA_ANNOTATION$delegate", "REDUNDANT_REPEATABLE_ANNOTATION", "getREDUNDANT_REPEATABLE_ANNOTATION", "REDUNDANT_REPEATABLE_ANNOTATION$delegate", "REPEATABLE_ANNOTATION_HAS_NESTED_CLASS_NAMED_CONTAINER", "getREPEATABLE_ANNOTATION_HAS_NESTED_CLASS_NAMED_CONTAINER", "REPEATABLE_ANNOTATION_HAS_NESTED_CLASS_NAMED_CONTAINER$delegate", "REPEATABLE_CONTAINER_HAS_NON_DEFAULT_PARAMETER", "Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticFactoryForDeprecation2;", "Lorg/jetbrains/kotlin/name/ClassId;", "Lorg/jetbrains/kotlin/name/Name;", "getREPEATABLE_CONTAINER_HAS_NON_DEFAULT_PARAMETER", "()Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticFactoryForDeprecation2;", "REPEATABLE_CONTAINER_HAS_NON_DEFAULT_PARAMETER$delegate", "REPEATABLE_CONTAINER_HAS_SHORTER_RETENTION", "Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticFactoryForDeprecation4;", "getREPEATABLE_CONTAINER_HAS_SHORTER_RETENTION", "()Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticFactoryForDeprecation4;", "REPEATABLE_CONTAINER_HAS_SHORTER_RETENTION$delegate", "REPEATABLE_CONTAINER_MUST_HAVE_VALUE_ARRAY", "getREPEATABLE_CONTAINER_MUST_HAVE_VALUE_ARRAY", "REPEATABLE_CONTAINER_MUST_HAVE_VALUE_ARRAY$delegate", "REPEATABLE_CONTAINER_TARGET_SET_NOT_A_SUBSET", "getREPEATABLE_CONTAINER_TARGET_SET_NOT_A_SUBSET", "REPEATABLE_CONTAINER_TARGET_SET_NOT_A_SUBSET$delegate", "REPEATED_ANNOTATION_WITH_CONTAINER", "getREPEATED_ANNOTATION_WITH_CONTAINER", "REPEATED_ANNOTATION_WITH_CONTAINER$delegate", "SPREAD_ON_SIGNATURE_POLYMORPHIC_CALL", "getSPREAD_ON_SIGNATURE_POLYMORPHIC_CALL", "SPREAD_ON_SIGNATURE_POLYMORPHIC_CALL$delegate", "STRICTFP_ON_CLASS", "getSTRICTFP_ON_CLASS", "STRICTFP_ON_CLASS$delegate", "SUBCLASS_CANT_CALL_COMPANION_PROTECTED_NON_STATIC", "getSUBCLASS_CANT_CALL_COMPANION_PROTECTED_NON_STATIC", "SUBCLASS_CANT_CALL_COMPANION_PROTECTED_NON_STATIC$delegate", "SUPER_CALL_WITH_DEFAULT_PARAMETERS", "getSUPER_CALL_WITH_DEFAULT_PARAMETERS", "SUPER_CALL_WITH_DEFAULT_PARAMETERS$delegate", "SUSPENSION_POINT_INSIDE_CRITICAL_SECTION", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirCallableSymbol;", "getSUSPENSION_POINT_INSIDE_CRITICAL_SECTION", "SUSPENSION_POINT_INSIDE_CRITICAL_SECTION$delegate", "SYNCHRONIZED_IN_INTERFACE", "getSYNCHRONIZED_IN_INTERFACE", "SYNCHRONIZED_IN_INTERFACE$delegate", "SYNCHRONIZED_ON_ABSTRACT", "getSYNCHRONIZED_ON_ABSTRACT", "SYNCHRONIZED_ON_ABSTRACT$delegate", "SYNCHRONIZED_ON_INLINE", "getSYNCHRONIZED_ON_INLINE", "SYNCHRONIZED_ON_INLINE$delegate", "SYNCHRONIZED_ON_SUSPEND", "getSYNCHRONIZED_ON_SUSPEND", "SYNCHRONIZED_ON_SUSPEND$delegate", "UPPER_BOUND_CANNOT_BE_ARRAY", "getUPPER_BOUND_CANNOT_BE_ARRAY", "UPPER_BOUND_CANNOT_BE_ARRAY$delegate", "VALUE_CLASS_WITHOUT_JVM_INLINE_ANNOTATION", "getVALUE_CLASS_WITHOUT_JVM_INLINE_ANNOTATION", "VALUE_CLASS_WITHOUT_JVM_INLINE_ANNOTATION$delegate", "checkers.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirJvmErrors {
    static final /* synthetic */ KProperty<Object>[] $$delegatedProperties;

    /* JADX INFO: renamed from: CONCURRENT_HASH_MAP_CONTAINS_OPERATOR$delegate, reason: from kotlin metadata */
    private static final ReadOnlyProperty CONCURRENT_HASH_MAP_CONTAINS_OPERATOR;

    /* JADX INFO: renamed from: CONFLICTING_JVM_DECLARATIONS$delegate, reason: from kotlin metadata */
    private static final ReadOnlyProperty CONFLICTING_JVM_DECLARATIONS;

    /* JADX INFO: renamed from: DELEGATION_BY_IN_JVM_RECORD$delegate, reason: from kotlin metadata */
    private static final ReadOnlyProperty DELEGATION_BY_IN_JVM_RECORD;

    /* JADX INFO: renamed from: DEPRECATED_JAVA_ANNOTATION$delegate, reason: from kotlin metadata */
    private static final ReadOnlyProperty DEPRECATED_JAVA_ANNOTATION;

    /* JADX INFO: renamed from: ENUM_JVM_RECORD$delegate, reason: from kotlin metadata */
    private static final ReadOnlyProperty ENUM_JVM_RECORD;

    /* JADX INFO: renamed from: EXTERNAL_DECLARATION_CANNOT_BE_ABSTRACT$delegate, reason: from kotlin metadata */
    private static final ReadOnlyProperty EXTERNAL_DECLARATION_CANNOT_BE_ABSTRACT;

    /* JADX INFO: renamed from: EXTERNAL_DECLARATION_CANNOT_BE_INLINED$delegate, reason: from kotlin metadata */
    private static final ReadOnlyProperty EXTERNAL_DECLARATION_CANNOT_BE_INLINED;

    /* JADX INFO: renamed from: EXTERNAL_DECLARATION_CANNOT_HAVE_BODY$delegate, reason: from kotlin metadata */
    private static final ReadOnlyProperty EXTERNAL_DECLARATION_CANNOT_HAVE_BODY;

    /* JADX INFO: renamed from: EXTERNAL_DECLARATION_IN_INTERFACE$delegate, reason: from kotlin metadata */
    private static final ReadOnlyProperty EXTERNAL_DECLARATION_IN_INTERFACE;

    /* JADX INFO: renamed from: FIELD_IN_JVM_RECORD$delegate, reason: from kotlin metadata */
    private static final ReadOnlyProperty FIELD_IN_JVM_RECORD;

    /* JADX INFO: renamed from: FUNCTION_DELEGATE_MEMBER_NAME_CLASH$delegate, reason: from kotlin metadata */
    private static final ReadOnlyProperty FUNCTION_DELEGATE_MEMBER_NAME_CLASH;

    /* JADX INFO: renamed from: ILLEGAL_JAVA_LANG_RECORD_SUPERTYPE$delegate, reason: from kotlin metadata */
    private static final ReadOnlyProperty ILLEGAL_JAVA_LANG_RECORD_SUPERTYPE;

    /* JADX INFO: renamed from: ILLEGAL_JVM_NAME$delegate, reason: from kotlin metadata */
    private static final ReadOnlyProperty ILLEGAL_JVM_NAME;

    /* JADX INFO: renamed from: INAPPLICABLE_JVM_FIELD$delegate, reason: from kotlin metadata */
    private static final ReadOnlyProperty INAPPLICABLE_JVM_FIELD;

    /* JADX INFO: renamed from: INAPPLICABLE_JVM_FIELD_WARNING$delegate, reason: from kotlin metadata */
    private static final ReadOnlyProperty INAPPLICABLE_JVM_FIELD_WARNING;

    /* JADX INFO: renamed from: INAPPLICABLE_JVM_NAME$delegate, reason: from kotlin metadata */
    private static final ReadOnlyProperty INAPPLICABLE_JVM_NAME;

    /* JADX INFO: renamed from: INNER_JVM_RECORD$delegate, reason: from kotlin metadata */
    private static final ReadOnlyProperty INNER_JVM_RECORD;
    public static final FirJvmErrors INSTANCE;

    /* JADX INFO: renamed from: INTERFACE_CANT_CALL_DEFAULT_METHOD_VIA_SUPER$delegate, reason: from kotlin metadata */
    private static final ReadOnlyProperty INTERFACE_CANT_CALL_DEFAULT_METHOD_VIA_SUPER;

    /* JADX INFO: renamed from: JAVA_SAM_INTERFACE_CONSTRUCTOR_REFERENCE$delegate, reason: from kotlin metadata */
    private static final ReadOnlyProperty JAVA_SAM_INTERFACE_CONSTRUCTOR_REFERENCE;

    /* JADX INFO: renamed from: JAVA_TYPE_MISMATCH$delegate, reason: from kotlin metadata */
    private static final ReadOnlyProperty JAVA_TYPE_MISMATCH;

    /* JADX INFO: renamed from: JVM_DEFAULT_IN_DECLARATION$delegate, reason: from kotlin metadata */
    private static final ReadOnlyProperty JVM_DEFAULT_IN_DECLARATION;

    /* JADX INFO: renamed from: JVM_DEFAULT_WITH_COMPATIBILITY_IN_DECLARATION$delegate, reason: from kotlin metadata */
    private static final ReadOnlyProperty JVM_DEFAULT_WITH_COMPATIBILITY_IN_DECLARATION;

    /* JADX INFO: renamed from: JVM_DEFAULT_WITH_COMPATIBILITY_NOT_ON_INTERFACE$delegate, reason: from kotlin metadata */
    private static final ReadOnlyProperty JVM_DEFAULT_WITH_COMPATIBILITY_NOT_ON_INTERFACE;

    /* JADX INFO: renamed from: JVM_INLINE_WITHOUT_VALUE_CLASS$delegate, reason: from kotlin metadata */
    private static final ReadOnlyProperty JVM_INLINE_WITHOUT_VALUE_CLASS;

    /* JADX INFO: renamed from: JVM_PACKAGE_NAME_CANNOT_BE_EMPTY$delegate, reason: from kotlin metadata */
    private static final ReadOnlyProperty JVM_PACKAGE_NAME_CANNOT_BE_EMPTY;

    /* JADX INFO: renamed from: JVM_PACKAGE_NAME_MUST_BE_VALID_NAME$delegate, reason: from kotlin metadata */
    private static final ReadOnlyProperty JVM_PACKAGE_NAME_MUST_BE_VALID_NAME;

    /* JADX INFO: renamed from: JVM_PACKAGE_NAME_NOT_SUPPORTED_IN_FILES_WITH_CLASSES$delegate, reason: from kotlin metadata */
    private static final ReadOnlyProperty JVM_PACKAGE_NAME_NOT_SUPPORTED_IN_FILES_WITH_CLASSES;

    /* JADX INFO: renamed from: JVM_RECORD_EXTENDS_CLASS$delegate, reason: from kotlin metadata */
    private static final ReadOnlyProperty JVM_RECORD_EXTENDS_CLASS;

    /* JADX INFO: renamed from: JVM_RECORD_NOT_LAST_VARARG_PARAMETER$delegate, reason: from kotlin metadata */
    private static final ReadOnlyProperty JVM_RECORD_NOT_LAST_VARARG_PARAMETER;

    /* JADX INFO: renamed from: JVM_RECORD_NOT_VAL_PARAMETER$delegate, reason: from kotlin metadata */
    private static final ReadOnlyProperty JVM_RECORD_NOT_VAL_PARAMETER;

    /* JADX INFO: renamed from: JVM_RECORD_WITHOUT_PRIMARY_CONSTRUCTOR_PARAMETERS$delegate, reason: from kotlin metadata */
    private static final ReadOnlyProperty JVM_RECORD_WITHOUT_PRIMARY_CONSTRUCTOR_PARAMETERS;

    /* JADX INFO: renamed from: JVM_STATIC_NOT_IN_OBJECT_OR_CLASS_COMPANION$delegate, reason: from kotlin metadata */
    private static final ReadOnlyProperty JVM_STATIC_NOT_IN_OBJECT_OR_CLASS_COMPANION;

    /* JADX INFO: renamed from: JVM_STATIC_NOT_IN_OBJECT_OR_COMPANION$delegate, reason: from kotlin metadata */
    private static final ReadOnlyProperty JVM_STATIC_NOT_IN_OBJECT_OR_COMPANION;

    /* JADX INFO: renamed from: JVM_STATIC_ON_CONST_OR_JVM_FIELD$delegate, reason: from kotlin metadata */
    private static final ReadOnlyProperty JVM_STATIC_ON_CONST_OR_JVM_FIELD;

    /* JADX INFO: renamed from: JVM_STATIC_ON_EXTERNAL_IN_INTERFACE$delegate, reason: from kotlin metadata */
    private static final ReadOnlyProperty JVM_STATIC_ON_EXTERNAL_IN_INTERFACE;

    /* JADX INFO: renamed from: JVM_STATIC_ON_NON_PUBLIC_MEMBER$delegate, reason: from kotlin metadata */
    private static final ReadOnlyProperty JVM_STATIC_ON_NON_PUBLIC_MEMBER;

    /* JADX INFO: renamed from: JVM_SYNTHETIC_ON_DELEGATE$delegate, reason: from kotlin metadata */
    private static final ReadOnlyProperty JVM_SYNTHETIC_ON_DELEGATE;

    /* JADX INFO: renamed from: LOCAL_JVM_RECORD$delegate, reason: from kotlin metadata */
    private static final ReadOnlyProperty LOCAL_JVM_RECORD;

    /* JADX INFO: renamed from: NON_DATA_CLASS_JVM_RECORD$delegate, reason: from kotlin metadata */
    private static final ReadOnlyProperty NON_DATA_CLASS_JVM_RECORD;

    /* JADX INFO: renamed from: NON_FINAL_JVM_RECORD$delegate, reason: from kotlin metadata */
    private static final ReadOnlyProperty NON_FINAL_JVM_RECORD;

    /* JADX INFO: renamed from: NON_SOURCE_REPEATED_ANNOTATION$delegate, reason: from kotlin metadata */
    private static final ReadOnlyProperty NON_SOURCE_REPEATED_ANNOTATION;

    /* JADX INFO: renamed from: NO_REFLECTION_IN_CLASS_PATH$delegate, reason: from kotlin metadata */
    private static final ReadOnlyProperty NO_REFLECTION_IN_CLASS_PATH;

    /* JADX INFO: renamed from: OVERLOADS_ABSTRACT$delegate, reason: from kotlin metadata */
    private static final ReadOnlyProperty OVERLOADS_ABSTRACT;

    /* JADX INFO: renamed from: OVERLOADS_ANNOTATION_CLASS_CONSTRUCTOR$delegate, reason: from kotlin metadata */
    private static final ReadOnlyProperty OVERLOADS_ANNOTATION_CLASS_CONSTRUCTOR;

    /* JADX INFO: renamed from: OVERLOADS_INTERFACE$delegate, reason: from kotlin metadata */
    private static final ReadOnlyProperty OVERLOADS_INTERFACE;

    /* JADX INFO: renamed from: OVERLOADS_LOCAL$delegate, reason: from kotlin metadata */
    private static final ReadOnlyProperty OVERLOADS_LOCAL;

    /* JADX INFO: renamed from: OVERLOADS_PRIVATE$delegate, reason: from kotlin metadata */
    private static final ReadOnlyProperty OVERLOADS_PRIVATE;

    /* JADX INFO: renamed from: OVERLOADS_WITHOUT_DEFAULT_ARGUMENTS$delegate, reason: from kotlin metadata */
    private static final ReadOnlyProperty OVERLOADS_WITHOUT_DEFAULT_ARGUMENTS;

    /* JADX INFO: renamed from: OVERRIDE_CANNOT_BE_STATIC$delegate, reason: from kotlin metadata */
    private static final ReadOnlyProperty OVERRIDE_CANNOT_BE_STATIC;

    /* JADX INFO: renamed from: POSITIONED_VALUE_ARGUMENT_FOR_JAVA_ANNOTATION$delegate, reason: from kotlin metadata */
    private static final ReadOnlyProperty POSITIONED_VALUE_ARGUMENT_FOR_JAVA_ANNOTATION;

    /* JADX INFO: renamed from: REDUNDANT_REPEATABLE_ANNOTATION$delegate, reason: from kotlin metadata */
    private static final ReadOnlyProperty REDUNDANT_REPEATABLE_ANNOTATION;

    /* JADX INFO: renamed from: REPEATABLE_ANNOTATION_HAS_NESTED_CLASS_NAMED_CONTAINER$delegate, reason: from kotlin metadata */
    private static final ReadOnlyProperty REPEATABLE_ANNOTATION_HAS_NESTED_CLASS_NAMED_CONTAINER;

    /* JADX INFO: renamed from: REPEATABLE_CONTAINER_HAS_NON_DEFAULT_PARAMETER$delegate, reason: from kotlin metadata */
    private static final ReadOnlyProperty REPEATABLE_CONTAINER_HAS_NON_DEFAULT_PARAMETER;

    /* JADX INFO: renamed from: REPEATABLE_CONTAINER_HAS_SHORTER_RETENTION$delegate, reason: from kotlin metadata */
    private static final ReadOnlyProperty REPEATABLE_CONTAINER_HAS_SHORTER_RETENTION;

    /* JADX INFO: renamed from: REPEATABLE_CONTAINER_MUST_HAVE_VALUE_ARRAY$delegate, reason: from kotlin metadata */
    private static final ReadOnlyProperty REPEATABLE_CONTAINER_MUST_HAVE_VALUE_ARRAY;

    /* JADX INFO: renamed from: REPEATABLE_CONTAINER_TARGET_SET_NOT_A_SUBSET$delegate, reason: from kotlin metadata */
    private static final ReadOnlyProperty REPEATABLE_CONTAINER_TARGET_SET_NOT_A_SUBSET;

    /* JADX INFO: renamed from: REPEATED_ANNOTATION_WITH_CONTAINER$delegate, reason: from kotlin metadata */
    private static final ReadOnlyProperty REPEATED_ANNOTATION_WITH_CONTAINER;

    /* JADX INFO: renamed from: SPREAD_ON_SIGNATURE_POLYMORPHIC_CALL$delegate, reason: from kotlin metadata */
    private static final ReadOnlyProperty SPREAD_ON_SIGNATURE_POLYMORPHIC_CALL;

    /* JADX INFO: renamed from: STRICTFP_ON_CLASS$delegate, reason: from kotlin metadata */
    private static final ReadOnlyProperty STRICTFP_ON_CLASS;

    /* JADX INFO: renamed from: SUBCLASS_CANT_CALL_COMPANION_PROTECTED_NON_STATIC$delegate, reason: from kotlin metadata */
    private static final ReadOnlyProperty SUBCLASS_CANT_CALL_COMPANION_PROTECTED_NON_STATIC;

    /* JADX INFO: renamed from: SUPER_CALL_WITH_DEFAULT_PARAMETERS$delegate, reason: from kotlin metadata */
    private static final ReadOnlyProperty SUPER_CALL_WITH_DEFAULT_PARAMETERS;

    /* JADX INFO: renamed from: SUSPENSION_POINT_INSIDE_CRITICAL_SECTION$delegate, reason: from kotlin metadata */
    private static final ReadOnlyProperty SUSPENSION_POINT_INSIDE_CRITICAL_SECTION;

    /* JADX INFO: renamed from: SYNCHRONIZED_IN_INTERFACE$delegate, reason: from kotlin metadata */
    private static final ReadOnlyProperty SYNCHRONIZED_IN_INTERFACE;

    /* JADX INFO: renamed from: SYNCHRONIZED_ON_ABSTRACT$delegate, reason: from kotlin metadata */
    private static final ReadOnlyProperty SYNCHRONIZED_ON_ABSTRACT;

    /* JADX INFO: renamed from: SYNCHRONIZED_ON_INLINE$delegate, reason: from kotlin metadata */
    private static final ReadOnlyProperty SYNCHRONIZED_ON_INLINE;

    /* JADX INFO: renamed from: SYNCHRONIZED_ON_SUSPEND$delegate, reason: from kotlin metadata */
    private static final ReadOnlyProperty SYNCHRONIZED_ON_SUSPEND;

    /* JADX INFO: renamed from: UPPER_BOUND_CANNOT_BE_ARRAY$delegate, reason: from kotlin metadata */
    private static final ReadOnlyProperty UPPER_BOUND_CANNOT_BE_ARRAY;

    /* JADX INFO: renamed from: VALUE_CLASS_WITHOUT_JVM_INLINE_ANNOTATION$delegate, reason: from kotlin metadata */
    private static final ReadOnlyProperty VALUE_CLASS_WITHOUT_JVM_INLINE_ANNOTATION;

    private FirJvmErrors() {
    }

    static {
        KProperty<?>[] kPropertyArr = {Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(FirJvmErrors.class), "CONFLICTING_JVM_DECLARATIONS", "getCONFLICTING_JVM_DECLARATIONS()Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticFactory0;")), Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(FirJvmErrors.class), "OVERRIDE_CANNOT_BE_STATIC", "getOVERRIDE_CANNOT_BE_STATIC()Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticFactory0;")), Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(FirJvmErrors.class), "JVM_STATIC_NOT_IN_OBJECT_OR_CLASS_COMPANION", "getJVM_STATIC_NOT_IN_OBJECT_OR_CLASS_COMPANION()Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticFactory0;")), Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(FirJvmErrors.class), "JVM_STATIC_NOT_IN_OBJECT_OR_COMPANION", "getJVM_STATIC_NOT_IN_OBJECT_OR_COMPANION()Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticFactory0;")), Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(FirJvmErrors.class), "JVM_STATIC_ON_NON_PUBLIC_MEMBER", "getJVM_STATIC_ON_NON_PUBLIC_MEMBER()Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticFactory0;")), Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(FirJvmErrors.class), "JVM_STATIC_ON_CONST_OR_JVM_FIELD", "getJVM_STATIC_ON_CONST_OR_JVM_FIELD()Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticFactory0;")), Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(FirJvmErrors.class), "JVM_STATIC_ON_EXTERNAL_IN_INTERFACE", "getJVM_STATIC_ON_EXTERNAL_IN_INTERFACE()Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticFactory0;")), Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(FirJvmErrors.class), "INAPPLICABLE_JVM_NAME", "getINAPPLICABLE_JVM_NAME()Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticFactory0;")), Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(FirJvmErrors.class), "ILLEGAL_JVM_NAME", "getILLEGAL_JVM_NAME()Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticFactory0;")), Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(FirJvmErrors.class), "FUNCTION_DELEGATE_MEMBER_NAME_CLASH", "getFUNCTION_DELEGATE_MEMBER_NAME_CLASH()Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticFactory0;")), Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(FirJvmErrors.class), "VALUE_CLASS_WITHOUT_JVM_INLINE_ANNOTATION", "getVALUE_CLASS_WITHOUT_JVM_INLINE_ANNOTATION()Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticFactory0;")), Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(FirJvmErrors.class), "JVM_INLINE_WITHOUT_VALUE_CLASS", "getJVM_INLINE_WITHOUT_VALUE_CLASS()Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticFactory0;")), Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(FirJvmErrors.class), "JAVA_TYPE_MISMATCH", "getJAVA_TYPE_MISMATCH()Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticFactory2;")), Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(FirJvmErrors.class), "UPPER_BOUND_CANNOT_BE_ARRAY", "getUPPER_BOUND_CANNOT_BE_ARRAY()Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticFactory0;")), Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(FirJvmErrors.class), "STRICTFP_ON_CLASS", "getSTRICTFP_ON_CLASS()Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticFactory0;")), Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(FirJvmErrors.class), "SYNCHRONIZED_ON_ABSTRACT", "getSYNCHRONIZED_ON_ABSTRACT()Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticFactory0;")), Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(FirJvmErrors.class), "SYNCHRONIZED_IN_INTERFACE", "getSYNCHRONIZED_IN_INTERFACE()Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticFactory0;")), Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(FirJvmErrors.class), "SYNCHRONIZED_ON_INLINE", "getSYNCHRONIZED_ON_INLINE()Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticFactory0;")), Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(FirJvmErrors.class), "SYNCHRONIZED_ON_SUSPEND", "getSYNCHRONIZED_ON_SUSPEND()Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticFactoryForDeprecation0;")), Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(FirJvmErrors.class), "OVERLOADS_WITHOUT_DEFAULT_ARGUMENTS", "getOVERLOADS_WITHOUT_DEFAULT_ARGUMENTS()Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticFactory0;")), Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(FirJvmErrors.class), "OVERLOADS_ABSTRACT", "getOVERLOADS_ABSTRACT()Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticFactory0;")), Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(FirJvmErrors.class), "OVERLOADS_INTERFACE", "getOVERLOADS_INTERFACE()Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticFactory0;")), Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(FirJvmErrors.class), "OVERLOADS_LOCAL", "getOVERLOADS_LOCAL()Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticFactory0;")), Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(FirJvmErrors.class), "OVERLOADS_ANNOTATION_CLASS_CONSTRUCTOR", "getOVERLOADS_ANNOTATION_CLASS_CONSTRUCTOR()Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticFactoryForDeprecation0;")), Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(FirJvmErrors.class), "OVERLOADS_PRIVATE", "getOVERLOADS_PRIVATE()Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticFactory0;")), Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(FirJvmErrors.class), "DEPRECATED_JAVA_ANNOTATION", "getDEPRECATED_JAVA_ANNOTATION()Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticFactory1;")), Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(FirJvmErrors.class), "JVM_PACKAGE_NAME_CANNOT_BE_EMPTY", "getJVM_PACKAGE_NAME_CANNOT_BE_EMPTY()Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticFactory0;")), Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(FirJvmErrors.class), "JVM_PACKAGE_NAME_MUST_BE_VALID_NAME", "getJVM_PACKAGE_NAME_MUST_BE_VALID_NAME()Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticFactory0;")), Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(FirJvmErrors.class), "JVM_PACKAGE_NAME_NOT_SUPPORTED_IN_FILES_WITH_CLASSES", "getJVM_PACKAGE_NAME_NOT_SUPPORTED_IN_FILES_WITH_CLASSES()Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticFactory0;")), Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(FirJvmErrors.class), "POSITIONED_VALUE_ARGUMENT_FOR_JAVA_ANNOTATION", "getPOSITIONED_VALUE_ARGUMENT_FOR_JAVA_ANNOTATION()Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticFactory0;")), Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(FirJvmErrors.class), "REDUNDANT_REPEATABLE_ANNOTATION", "getREDUNDANT_REPEATABLE_ANNOTATION()Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticFactory2;")), Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(FirJvmErrors.class), "SUPER_CALL_WITH_DEFAULT_PARAMETERS", "getSUPER_CALL_WITH_DEFAULT_PARAMETERS()Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticFactory1;")), Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(FirJvmErrors.class), "INTERFACE_CANT_CALL_DEFAULT_METHOD_VIA_SUPER", "getINTERFACE_CANT_CALL_DEFAULT_METHOD_VIA_SUPER()Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticFactory0;")), Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(FirJvmErrors.class), "LOCAL_JVM_RECORD", "getLOCAL_JVM_RECORD()Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticFactory0;")), Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(FirJvmErrors.class), "NON_FINAL_JVM_RECORD", "getNON_FINAL_JVM_RECORD()Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticFactory0;")), Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(FirJvmErrors.class), "ENUM_JVM_RECORD", "getENUM_JVM_RECORD()Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticFactory0;")), Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(FirJvmErrors.class), "JVM_RECORD_WITHOUT_PRIMARY_CONSTRUCTOR_PARAMETERS", "getJVM_RECORD_WITHOUT_PRIMARY_CONSTRUCTOR_PARAMETERS()Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticFactory0;")), Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(FirJvmErrors.class), "NON_DATA_CLASS_JVM_RECORD", "getNON_DATA_CLASS_JVM_RECORD()Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticFactory0;")), Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(FirJvmErrors.class), "JVM_RECORD_NOT_VAL_PARAMETER", "getJVM_RECORD_NOT_VAL_PARAMETER()Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticFactory0;")), Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(FirJvmErrors.class), "JVM_RECORD_NOT_LAST_VARARG_PARAMETER", "getJVM_RECORD_NOT_LAST_VARARG_PARAMETER()Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticFactory0;")), Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(FirJvmErrors.class), "INNER_JVM_RECORD", "getINNER_JVM_RECORD()Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticFactory0;")), Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(FirJvmErrors.class), "FIELD_IN_JVM_RECORD", "getFIELD_IN_JVM_RECORD()Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticFactory0;")), Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(FirJvmErrors.class), "DELEGATION_BY_IN_JVM_RECORD", "getDELEGATION_BY_IN_JVM_RECORD()Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticFactory0;")), Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(FirJvmErrors.class), "JVM_RECORD_EXTENDS_CLASS", "getJVM_RECORD_EXTENDS_CLASS()Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticFactory1;")), Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(FirJvmErrors.class), "ILLEGAL_JAVA_LANG_RECORD_SUPERTYPE", "getILLEGAL_JAVA_LANG_RECORD_SUPERTYPE()Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticFactory0;")), Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(FirJvmErrors.class), "JVM_DEFAULT_IN_DECLARATION", "getJVM_DEFAULT_IN_DECLARATION()Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticFactory1;")), Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(FirJvmErrors.class), "JVM_DEFAULT_WITH_COMPATIBILITY_IN_DECLARATION", "getJVM_DEFAULT_WITH_COMPATIBILITY_IN_DECLARATION()Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticFactory0;")), Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(FirJvmErrors.class), "JVM_DEFAULT_WITH_COMPATIBILITY_NOT_ON_INTERFACE", "getJVM_DEFAULT_WITH_COMPATIBILITY_NOT_ON_INTERFACE()Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticFactory0;")), Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(FirJvmErrors.class), "EXTERNAL_DECLARATION_CANNOT_BE_ABSTRACT", "getEXTERNAL_DECLARATION_CANNOT_BE_ABSTRACT()Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticFactory0;")), Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(FirJvmErrors.class), "EXTERNAL_DECLARATION_CANNOT_HAVE_BODY", "getEXTERNAL_DECLARATION_CANNOT_HAVE_BODY()Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticFactory0;")), Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(FirJvmErrors.class), "EXTERNAL_DECLARATION_IN_INTERFACE", "getEXTERNAL_DECLARATION_IN_INTERFACE()Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticFactory0;")), Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(FirJvmErrors.class), "EXTERNAL_DECLARATION_CANNOT_BE_INLINED", "getEXTERNAL_DECLARATION_CANNOT_BE_INLINED()Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticFactory0;")), Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(FirJvmErrors.class), "NON_SOURCE_REPEATED_ANNOTATION", "getNON_SOURCE_REPEATED_ANNOTATION()Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticFactory0;")), Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(FirJvmErrors.class), "REPEATED_ANNOTATION_WITH_CONTAINER", "getREPEATED_ANNOTATION_WITH_CONTAINER()Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticFactory2;")), Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(FirJvmErrors.class), "REPEATABLE_CONTAINER_MUST_HAVE_VALUE_ARRAY", "getREPEATABLE_CONTAINER_MUST_HAVE_VALUE_ARRAY()Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticFactoryForDeprecation2;")), Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(FirJvmErrors.class), "REPEATABLE_CONTAINER_HAS_NON_DEFAULT_PARAMETER", "getREPEATABLE_CONTAINER_HAS_NON_DEFAULT_PARAMETER()Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticFactoryForDeprecation2;")), Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(FirJvmErrors.class), "REPEATABLE_CONTAINER_HAS_SHORTER_RETENTION", "getREPEATABLE_CONTAINER_HAS_SHORTER_RETENTION()Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticFactoryForDeprecation4;")), Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(FirJvmErrors.class), "REPEATABLE_CONTAINER_TARGET_SET_NOT_A_SUBSET", "getREPEATABLE_CONTAINER_TARGET_SET_NOT_A_SUBSET()Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticFactoryForDeprecation2;")), Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(FirJvmErrors.class), "REPEATABLE_ANNOTATION_HAS_NESTED_CLASS_NAMED_CONTAINER", "getREPEATABLE_ANNOTATION_HAS_NESTED_CLASS_NAMED_CONTAINER()Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticFactoryForDeprecation0;")), Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(FirJvmErrors.class), "SUSPENSION_POINT_INSIDE_CRITICAL_SECTION", "getSUSPENSION_POINT_INSIDE_CRITICAL_SECTION()Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticFactory1;")), Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(FirJvmErrors.class), "INAPPLICABLE_JVM_FIELD", "getINAPPLICABLE_JVM_FIELD()Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticFactory1;")), Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(FirJvmErrors.class), "INAPPLICABLE_JVM_FIELD_WARNING", "getINAPPLICABLE_JVM_FIELD_WARNING()Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticFactory1;")), Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(FirJvmErrors.class), "JVM_SYNTHETIC_ON_DELEGATE", "getJVM_SYNTHETIC_ON_DELEGATE()Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticFactory0;")), Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(FirJvmErrors.class), "SUBCLASS_CANT_CALL_COMPANION_PROTECTED_NON_STATIC", "getSUBCLASS_CANT_CALL_COMPANION_PROTECTED_NON_STATIC()Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticFactory0;")), Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(FirJvmErrors.class), "CONCURRENT_HASH_MAP_CONTAINS_OPERATOR", "getCONCURRENT_HASH_MAP_CONTAINS_OPERATOR()Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticFactoryForDeprecation0;")), Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(FirJvmErrors.class), "SPREAD_ON_SIGNATURE_POLYMORPHIC_CALL", "getSPREAD_ON_SIGNATURE_POLYMORPHIC_CALL()Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticFactoryForDeprecation0;")), Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(FirJvmErrors.class), "JAVA_SAM_INTERFACE_CONSTRUCTOR_REFERENCE", "getJAVA_SAM_INTERFACE_CONSTRUCTOR_REFERENCE()Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticFactory0;")), Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(FirJvmErrors.class), "NO_REFLECTION_IN_CLASS_PATH", "getNO_REFLECTION_IN_CLASS_PATH()Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticFactory0;"))};
        $$delegatedProperties = kPropertyArr;
        FirJvmErrors firJvmErrors = new FirJvmErrors();
        INSTANCE = firJvmErrors;
        CONFLICTING_JVM_DECLARATIONS = new DiagnosticFactory0DelegateProvider(Severity.ERROR, AbstractSourceElementPositioningStrategy.INSTANCE.getDEFAULT(), Reflection.getOrCreateKotlinClass(PsiElement.class)).provideDelegate(firJvmErrors, kPropertyArr[0]);
        OVERRIDE_CANNOT_BE_STATIC = new DiagnosticFactory0DelegateProvider(Severity.ERROR, AbstractSourceElementPositioningStrategy.INSTANCE.getDEFAULT(), Reflection.getOrCreateKotlinClass(PsiElement.class)).provideDelegate(firJvmErrors, kPropertyArr[1]);
        JVM_STATIC_NOT_IN_OBJECT_OR_CLASS_COMPANION = new DiagnosticFactory0DelegateProvider(Severity.ERROR, SourceElementPositioningStrategies.INSTANCE.getDECLARATION_SIGNATURE(), Reflection.getOrCreateKotlinClass(PsiElement.class)).provideDelegate(firJvmErrors, kPropertyArr[2]);
        JVM_STATIC_NOT_IN_OBJECT_OR_COMPANION = new DiagnosticFactory0DelegateProvider(Severity.ERROR, SourceElementPositioningStrategies.INSTANCE.getDECLARATION_SIGNATURE(), Reflection.getOrCreateKotlinClass(PsiElement.class)).provideDelegate(firJvmErrors, kPropertyArr[3]);
        JVM_STATIC_ON_NON_PUBLIC_MEMBER = new DiagnosticFactory0DelegateProvider(Severity.ERROR, SourceElementPositioningStrategies.INSTANCE.getDECLARATION_SIGNATURE(), Reflection.getOrCreateKotlinClass(PsiElement.class)).provideDelegate(firJvmErrors, kPropertyArr[4]);
        JVM_STATIC_ON_CONST_OR_JVM_FIELD = new DiagnosticFactory0DelegateProvider(Severity.ERROR, SourceElementPositioningStrategies.INSTANCE.getDECLARATION_SIGNATURE(), Reflection.getOrCreateKotlinClass(PsiElement.class)).provideDelegate(firJvmErrors, kPropertyArr[5]);
        JVM_STATIC_ON_EXTERNAL_IN_INTERFACE = new DiagnosticFactory0DelegateProvider(Severity.ERROR, SourceElementPositioningStrategies.INSTANCE.getDECLARATION_SIGNATURE(), Reflection.getOrCreateKotlinClass(PsiElement.class)).provideDelegate(firJvmErrors, kPropertyArr[6]);
        INAPPLICABLE_JVM_NAME = new DiagnosticFactory0DelegateProvider(Severity.ERROR, AbstractSourceElementPositioningStrategy.INSTANCE.getDEFAULT(), Reflection.getOrCreateKotlinClass(PsiElement.class)).provideDelegate(firJvmErrors, kPropertyArr[7]);
        ILLEGAL_JVM_NAME = new DiagnosticFactory0DelegateProvider(Severity.ERROR, AbstractSourceElementPositioningStrategy.INSTANCE.getDEFAULT(), Reflection.getOrCreateKotlinClass(PsiElement.class)).provideDelegate(firJvmErrors, kPropertyArr[8]);
        FUNCTION_DELEGATE_MEMBER_NAME_CLASH = new DiagnosticFactory0DelegateProvider(Severity.ERROR, SourceElementPositioningStrategies.INSTANCE.getDECLARATION_NAME(), Reflection.getOrCreateKotlinClass(PsiElement.class)).provideDelegate(firJvmErrors, kPropertyArr[9]);
        VALUE_CLASS_WITHOUT_JVM_INLINE_ANNOTATION = new DiagnosticFactory0DelegateProvider(Severity.ERROR, AbstractSourceElementPositioningStrategy.INSTANCE.getDEFAULT(), Reflection.getOrCreateKotlinClass(PsiElement.class)).provideDelegate(firJvmErrors, kPropertyArr[10]);
        JVM_INLINE_WITHOUT_VALUE_CLASS = new DiagnosticFactory0DelegateProvider(Severity.ERROR, AbstractSourceElementPositioningStrategy.INSTANCE.getDEFAULT(), Reflection.getOrCreateKotlinClass(PsiElement.class)).provideDelegate(firJvmErrors, kPropertyArr[11]);
        JAVA_TYPE_MISMATCH = new DiagnosticFactory2DelegateProvider(Severity.ERROR, AbstractSourceElementPositioningStrategy.INSTANCE.getDEFAULT(), Reflection.getOrCreateKotlinClass(KtExpression.class)).provideDelegate(firJvmErrors, kPropertyArr[12]);
        UPPER_BOUND_CANNOT_BE_ARRAY = new DiagnosticFactory0DelegateProvider(Severity.ERROR, AbstractSourceElementPositioningStrategy.INSTANCE.getDEFAULT(), Reflection.getOrCreateKotlinClass(PsiElement.class)).provideDelegate(firJvmErrors, kPropertyArr[13]);
        STRICTFP_ON_CLASS = new DiagnosticFactory0DelegateProvider(Severity.ERROR, AbstractSourceElementPositioningStrategy.INSTANCE.getDEFAULT(), Reflection.getOrCreateKotlinClass(KtAnnotationEntry.class)).provideDelegate(firJvmErrors, kPropertyArr[14]);
        SYNCHRONIZED_ON_ABSTRACT = new DiagnosticFactory0DelegateProvider(Severity.ERROR, AbstractSourceElementPositioningStrategy.INSTANCE.getDEFAULT(), Reflection.getOrCreateKotlinClass(KtAnnotationEntry.class)).provideDelegate(firJvmErrors, kPropertyArr[15]);
        SYNCHRONIZED_IN_INTERFACE = new DiagnosticFactory0DelegateProvider(Severity.ERROR, AbstractSourceElementPositioningStrategy.INSTANCE.getDEFAULT(), Reflection.getOrCreateKotlinClass(KtAnnotationEntry.class)).provideDelegate(firJvmErrors, kPropertyArr[16]);
        SYNCHRONIZED_ON_INLINE = new DiagnosticFactory0DelegateProvider(Severity.WARNING, AbstractSourceElementPositioningStrategy.INSTANCE.getDEFAULT(), Reflection.getOrCreateKotlinClass(KtAnnotationEntry.class)).provideDelegate(firJvmErrors, kPropertyArr[17]);
        SYNCHRONIZED_ON_SUSPEND = new DeprecationDiagnosticFactory0DelegateProvider(LanguageFeature.SynchronizedSuspendError, AbstractSourceElementPositioningStrategy.INSTANCE.getDEFAULT(), Reflection.getOrCreateKotlinClass(KtAnnotationEntry.class)).provideDelegate(firJvmErrors, kPropertyArr[18]);
        OVERLOADS_WITHOUT_DEFAULT_ARGUMENTS = new DiagnosticFactory0DelegateProvider(Severity.WARNING, AbstractSourceElementPositioningStrategy.INSTANCE.getDEFAULT(), Reflection.getOrCreateKotlinClass(KtAnnotationEntry.class)).provideDelegate(firJvmErrors, kPropertyArr[19]);
        OVERLOADS_ABSTRACT = new DiagnosticFactory0DelegateProvider(Severity.ERROR, AbstractSourceElementPositioningStrategy.INSTANCE.getDEFAULT(), Reflection.getOrCreateKotlinClass(KtAnnotationEntry.class)).provideDelegate(firJvmErrors, kPropertyArr[20]);
        OVERLOADS_INTERFACE = new DiagnosticFactory0DelegateProvider(Severity.ERROR, AbstractSourceElementPositioningStrategy.INSTANCE.getDEFAULT(), Reflection.getOrCreateKotlinClass(KtAnnotationEntry.class)).provideDelegate(firJvmErrors, kPropertyArr[21]);
        OVERLOADS_LOCAL = new DiagnosticFactory0DelegateProvider(Severity.ERROR, AbstractSourceElementPositioningStrategy.INSTANCE.getDEFAULT(), Reflection.getOrCreateKotlinClass(KtAnnotationEntry.class)).provideDelegate(firJvmErrors, kPropertyArr[22]);
        OVERLOADS_ANNOTATION_CLASS_CONSTRUCTOR = new DeprecationDiagnosticFactory0DelegateProvider(LanguageFeature.ProhibitJvmOverloadsOnConstructorsOfAnnotationClasses, AbstractSourceElementPositioningStrategy.INSTANCE.getDEFAULT(), Reflection.getOrCreateKotlinClass(KtAnnotationEntry.class)).provideDelegate(firJvmErrors, kPropertyArr[23]);
        OVERLOADS_PRIVATE = new DiagnosticFactory0DelegateProvider(Severity.WARNING, AbstractSourceElementPositioningStrategy.INSTANCE.getDEFAULT(), Reflection.getOrCreateKotlinClass(KtAnnotationEntry.class)).provideDelegate(firJvmErrors, kPropertyArr[24]);
        DEPRECATED_JAVA_ANNOTATION = new DiagnosticFactory1DelegateProvider(Severity.WARNING, AbstractSourceElementPositioningStrategy.INSTANCE.getDEFAULT(), Reflection.getOrCreateKotlinClass(KtAnnotationEntry.class)).provideDelegate(firJvmErrors, kPropertyArr[25]);
        JVM_PACKAGE_NAME_CANNOT_BE_EMPTY = new DiagnosticFactory0DelegateProvider(Severity.ERROR, AbstractSourceElementPositioningStrategy.INSTANCE.getDEFAULT(), Reflection.getOrCreateKotlinClass(KtAnnotationEntry.class)).provideDelegate(firJvmErrors, kPropertyArr[26]);
        JVM_PACKAGE_NAME_MUST_BE_VALID_NAME = new DiagnosticFactory0DelegateProvider(Severity.ERROR, AbstractSourceElementPositioningStrategy.INSTANCE.getDEFAULT(), Reflection.getOrCreateKotlinClass(KtAnnotationEntry.class)).provideDelegate(firJvmErrors, kPropertyArr[27]);
        JVM_PACKAGE_NAME_NOT_SUPPORTED_IN_FILES_WITH_CLASSES = new DiagnosticFactory0DelegateProvider(Severity.ERROR, AbstractSourceElementPositioningStrategy.INSTANCE.getDEFAULT(), Reflection.getOrCreateKotlinClass(KtAnnotationEntry.class)).provideDelegate(firJvmErrors, kPropertyArr[28]);
        POSITIONED_VALUE_ARGUMENT_FOR_JAVA_ANNOTATION = new DiagnosticFactory0DelegateProvider(Severity.ERROR, AbstractSourceElementPositioningStrategy.INSTANCE.getDEFAULT(), Reflection.getOrCreateKotlinClass(KtExpression.class)).provideDelegate(firJvmErrors, kPropertyArr[29]);
        REDUNDANT_REPEATABLE_ANNOTATION = new DiagnosticFactory2DelegateProvider(Severity.WARNING, AbstractSourceElementPositioningStrategy.INSTANCE.getDEFAULT(), Reflection.getOrCreateKotlinClass(KtAnnotationEntry.class)).provideDelegate(firJvmErrors, kPropertyArr[30]);
        SUPER_CALL_WITH_DEFAULT_PARAMETERS = new DiagnosticFactory1DelegateProvider(Severity.ERROR, AbstractSourceElementPositioningStrategy.INSTANCE.getDEFAULT(), Reflection.getOrCreateKotlinClass(PsiElement.class)).provideDelegate(firJvmErrors, kPropertyArr[31]);
        INTERFACE_CANT_CALL_DEFAULT_METHOD_VIA_SUPER = new DiagnosticFactory0DelegateProvider(Severity.ERROR, SourceElementPositioningStrategies.INSTANCE.getREFERENCE_BY_QUALIFIED(), Reflection.getOrCreateKotlinClass(PsiElement.class)).provideDelegate(firJvmErrors, kPropertyArr[32]);
        LOCAL_JVM_RECORD = new DiagnosticFactory0DelegateProvider(Severity.ERROR, AbstractSourceElementPositioningStrategy.INSTANCE.getDEFAULT(), Reflection.getOrCreateKotlinClass(PsiElement.class)).provideDelegate(firJvmErrors, kPropertyArr[33]);
        NON_FINAL_JVM_RECORD = new DiagnosticFactory0DelegateProvider(Severity.ERROR, SourceElementPositioningStrategies.INSTANCE.getNON_FINAL_MODIFIER_OR_NAME(), Reflection.getOrCreateKotlinClass(PsiElement.class)).provideDelegate(firJvmErrors, kPropertyArr[34]);
        ENUM_JVM_RECORD = new DiagnosticFactory0DelegateProvider(Severity.ERROR, SourceElementPositioningStrategies.INSTANCE.getENUM_MODIFIER(), Reflection.getOrCreateKotlinClass(PsiElement.class)).provideDelegate(firJvmErrors, kPropertyArr[35]);
        JVM_RECORD_WITHOUT_PRIMARY_CONSTRUCTOR_PARAMETERS = new DiagnosticFactory0DelegateProvider(Severity.ERROR, AbstractSourceElementPositioningStrategy.INSTANCE.getDEFAULT(), Reflection.getOrCreateKotlinClass(PsiElement.class)).provideDelegate(firJvmErrors, kPropertyArr[36]);
        NON_DATA_CLASS_JVM_RECORD = new DiagnosticFactory0DelegateProvider(Severity.ERROR, AbstractSourceElementPositioningStrategy.INSTANCE.getDEFAULT(), Reflection.getOrCreateKotlinClass(PsiElement.class)).provideDelegate(firJvmErrors, kPropertyArr[37]);
        JVM_RECORD_NOT_VAL_PARAMETER = new DiagnosticFactory0DelegateProvider(Severity.ERROR, AbstractSourceElementPositioningStrategy.INSTANCE.getDEFAULT(), Reflection.getOrCreateKotlinClass(PsiElement.class)).provideDelegate(firJvmErrors, kPropertyArr[38]);
        JVM_RECORD_NOT_LAST_VARARG_PARAMETER = new DiagnosticFactory0DelegateProvider(Severity.ERROR, AbstractSourceElementPositioningStrategy.INSTANCE.getDEFAULT(), Reflection.getOrCreateKotlinClass(PsiElement.class)).provideDelegate(firJvmErrors, kPropertyArr[39]);
        INNER_JVM_RECORD = new DiagnosticFactory0DelegateProvider(Severity.ERROR, SourceElementPositioningStrategies.INSTANCE.getINNER_MODIFIER(), Reflection.getOrCreateKotlinClass(PsiElement.class)).provideDelegate(firJvmErrors, kPropertyArr[40]);
        FIELD_IN_JVM_RECORD = new DiagnosticFactory0DelegateProvider(Severity.ERROR, AbstractSourceElementPositioningStrategy.INSTANCE.getDEFAULT(), Reflection.getOrCreateKotlinClass(PsiElement.class)).provideDelegate(firJvmErrors, kPropertyArr[41]);
        DELEGATION_BY_IN_JVM_RECORD = new DiagnosticFactory0DelegateProvider(Severity.ERROR, AbstractSourceElementPositioningStrategy.INSTANCE.getDEFAULT(), Reflection.getOrCreateKotlinClass(PsiElement.class)).provideDelegate(firJvmErrors, kPropertyArr[42]);
        JVM_RECORD_EXTENDS_CLASS = new DiagnosticFactory1DelegateProvider(Severity.ERROR, SourceElementPositioningStrategies.INSTANCE.getACTUAL_DECLARATION_NAME(), Reflection.getOrCreateKotlinClass(PsiElement.class)).provideDelegate(firJvmErrors, kPropertyArr[43]);
        ILLEGAL_JAVA_LANG_RECORD_SUPERTYPE = new DiagnosticFactory0DelegateProvider(Severity.ERROR, AbstractSourceElementPositioningStrategy.INSTANCE.getDEFAULT(), Reflection.getOrCreateKotlinClass(PsiElement.class)).provideDelegate(firJvmErrors, kPropertyArr[44]);
        JVM_DEFAULT_IN_DECLARATION = new DiagnosticFactory1DelegateProvider(Severity.ERROR, SourceElementPositioningStrategies.INSTANCE.getDECLARATION_SIGNATURE_OR_DEFAULT(), Reflection.getOrCreateKotlinClass(KtElement.class)).provideDelegate(firJvmErrors, kPropertyArr[45]);
        JVM_DEFAULT_WITH_COMPATIBILITY_IN_DECLARATION = new DiagnosticFactory0DelegateProvider(Severity.ERROR, AbstractSourceElementPositioningStrategy.INSTANCE.getDEFAULT(), Reflection.getOrCreateKotlinClass(KtElement.class)).provideDelegate(firJvmErrors, kPropertyArr[46]);
        JVM_DEFAULT_WITH_COMPATIBILITY_NOT_ON_INTERFACE = new DiagnosticFactory0DelegateProvider(Severity.ERROR, AbstractSourceElementPositioningStrategy.INSTANCE.getDEFAULT(), Reflection.getOrCreateKotlinClass(KtElement.class)).provideDelegate(firJvmErrors, kPropertyArr[47]);
        EXTERNAL_DECLARATION_CANNOT_BE_ABSTRACT = new DiagnosticFactory0DelegateProvider(Severity.ERROR, SourceElementPositioningStrategies.INSTANCE.getABSTRACT_MODIFIER(), Reflection.getOrCreateKotlinClass(KtDeclaration.class)).provideDelegate(firJvmErrors, kPropertyArr[48]);
        EXTERNAL_DECLARATION_CANNOT_HAVE_BODY = new DiagnosticFactory0DelegateProvider(Severity.ERROR, SourceElementPositioningStrategies.INSTANCE.getDECLARATION_SIGNATURE(), Reflection.getOrCreateKotlinClass(KtDeclaration.class)).provideDelegate(firJvmErrors, kPropertyArr[49]);
        EXTERNAL_DECLARATION_IN_INTERFACE = new DiagnosticFactory0DelegateProvider(Severity.ERROR, SourceElementPositioningStrategies.INSTANCE.getDECLARATION_SIGNATURE(), Reflection.getOrCreateKotlinClass(KtDeclaration.class)).provideDelegate(firJvmErrors, kPropertyArr[50]);
        EXTERNAL_DECLARATION_CANNOT_BE_INLINED = new DiagnosticFactory0DelegateProvider(Severity.ERROR, SourceElementPositioningStrategies.INSTANCE.getDECLARATION_SIGNATURE(), Reflection.getOrCreateKotlinClass(KtDeclaration.class)).provideDelegate(firJvmErrors, kPropertyArr[51]);
        NON_SOURCE_REPEATED_ANNOTATION = new DiagnosticFactory0DelegateProvider(Severity.ERROR, AbstractSourceElementPositioningStrategy.INSTANCE.getDEFAULT(), Reflection.getOrCreateKotlinClass(KtAnnotationEntry.class)).provideDelegate(firJvmErrors, kPropertyArr[52]);
        REPEATED_ANNOTATION_WITH_CONTAINER = new DiagnosticFactory2DelegateProvider(Severity.ERROR, AbstractSourceElementPositioningStrategy.INSTANCE.getDEFAULT(), Reflection.getOrCreateKotlinClass(KtAnnotationEntry.class)).provideDelegate(firJvmErrors, kPropertyArr[53]);
        REPEATABLE_CONTAINER_MUST_HAVE_VALUE_ARRAY = new DeprecationDiagnosticFactory2DelegateProvider(LanguageFeature.RepeatableAnnotationContainerConstraints, AbstractSourceElementPositioningStrategy.INSTANCE.getDEFAULT(), Reflection.getOrCreateKotlinClass(KtAnnotationEntry.class)).provideDelegate(firJvmErrors, kPropertyArr[54]);
        REPEATABLE_CONTAINER_HAS_NON_DEFAULT_PARAMETER = new DeprecationDiagnosticFactory2DelegateProvider(LanguageFeature.RepeatableAnnotationContainerConstraints, AbstractSourceElementPositioningStrategy.INSTANCE.getDEFAULT(), Reflection.getOrCreateKotlinClass(KtAnnotationEntry.class)).provideDelegate(firJvmErrors, kPropertyArr[55]);
        REPEATABLE_CONTAINER_HAS_SHORTER_RETENTION = new DeprecationDiagnosticFactory4DelegateProvider(LanguageFeature.RepeatableAnnotationContainerConstraints, AbstractSourceElementPositioningStrategy.INSTANCE.getDEFAULT(), Reflection.getOrCreateKotlinClass(KtAnnotationEntry.class)).provideDelegate(firJvmErrors, kPropertyArr[56]);
        REPEATABLE_CONTAINER_TARGET_SET_NOT_A_SUBSET = new DeprecationDiagnosticFactory2DelegateProvider(LanguageFeature.RepeatableAnnotationContainerConstraints, AbstractSourceElementPositioningStrategy.INSTANCE.getDEFAULT(), Reflection.getOrCreateKotlinClass(KtAnnotationEntry.class)).provideDelegate(firJvmErrors, kPropertyArr[57]);
        REPEATABLE_ANNOTATION_HAS_NESTED_CLASS_NAMED_CONTAINER = new DeprecationDiagnosticFactory0DelegateProvider(LanguageFeature.RepeatableAnnotationContainerConstraints, AbstractSourceElementPositioningStrategy.INSTANCE.getDEFAULT(), Reflection.getOrCreateKotlinClass(KtAnnotationEntry.class)).provideDelegate(firJvmErrors, kPropertyArr[58]);
        SUSPENSION_POINT_INSIDE_CRITICAL_SECTION = new DiagnosticFactory1DelegateProvider(Severity.ERROR, SourceElementPositioningStrategies.INSTANCE.getREFERENCE_BY_QUALIFIED(), Reflection.getOrCreateKotlinClass(PsiElement.class)).provideDelegate(firJvmErrors, kPropertyArr[59]);
        INAPPLICABLE_JVM_FIELD = new DiagnosticFactory1DelegateProvider(Severity.ERROR, AbstractSourceElementPositioningStrategy.INSTANCE.getDEFAULT(), Reflection.getOrCreateKotlinClass(KtAnnotationEntry.class)).provideDelegate(firJvmErrors, kPropertyArr[60]);
        INAPPLICABLE_JVM_FIELD_WARNING = new DiagnosticFactory1DelegateProvider(Severity.WARNING, AbstractSourceElementPositioningStrategy.INSTANCE.getDEFAULT(), Reflection.getOrCreateKotlinClass(KtAnnotationEntry.class)).provideDelegate(firJvmErrors, kPropertyArr[61]);
        JVM_SYNTHETIC_ON_DELEGATE = new DiagnosticFactory0DelegateProvider(Severity.ERROR, AbstractSourceElementPositioningStrategy.INSTANCE.getDEFAULT(), Reflection.getOrCreateKotlinClass(KtAnnotationEntry.class)).provideDelegate(firJvmErrors, kPropertyArr[62]);
        SUBCLASS_CANT_CALL_COMPANION_PROTECTED_NON_STATIC = new DiagnosticFactory0DelegateProvider(Severity.ERROR, SourceElementPositioningStrategies.INSTANCE.getREFERENCED_NAME_BY_QUALIFIED(), Reflection.getOrCreateKotlinClass(PsiElement.class)).provideDelegate(firJvmErrors, kPropertyArr[63]);
        CONCURRENT_HASH_MAP_CONTAINS_OPERATOR = new DeprecationDiagnosticFactory0DelegateProvider(LanguageFeature.ProhibitConcurrentHashMapContains, AbstractSourceElementPositioningStrategy.INSTANCE.getDEFAULT(), Reflection.getOrCreateKotlinClass(PsiElement.class)).provideDelegate(firJvmErrors, kPropertyArr[64]);
        SPREAD_ON_SIGNATURE_POLYMORPHIC_CALL = new DeprecationDiagnosticFactory0DelegateProvider(LanguageFeature.ProhibitSpreadOnSignaturePolymorphicCall, SourceElementPositioningStrategies.INSTANCE.getSPREAD_OPERATOR(), Reflection.getOrCreateKotlinClass(PsiElement.class)).provideDelegate(firJvmErrors, kPropertyArr[65]);
        JAVA_SAM_INTERFACE_CONSTRUCTOR_REFERENCE = new DiagnosticFactory0DelegateProvider(Severity.ERROR, AbstractSourceElementPositioningStrategy.INSTANCE.getDEFAULT(), Reflection.getOrCreateKotlinClass(PsiElement.class)).provideDelegate(firJvmErrors, kPropertyArr[66]);
        NO_REFLECTION_IN_CLASS_PATH = new DiagnosticFactory0DelegateProvider(Severity.WARNING, AbstractSourceElementPositioningStrategy.INSTANCE.getDEFAULT(), Reflection.getOrCreateKotlinClass(PsiElement.class)).provideDelegate(firJvmErrors, kPropertyArr[67]);
        RootDiagnosticRendererFactory.INSTANCE.registerFactory(FirJvmErrorsDefaultMessages.INSTANCE);
    }

    public final KtDiagnosticFactory0 getCONFLICTING_JVM_DECLARATIONS() {
        return (KtDiagnosticFactory0) CONFLICTING_JVM_DECLARATIONS.getValue(this, $$delegatedProperties[0]);
    }

    public final KtDiagnosticFactory0 getOVERRIDE_CANNOT_BE_STATIC() {
        return (KtDiagnosticFactory0) OVERRIDE_CANNOT_BE_STATIC.getValue(this, $$delegatedProperties[1]);
    }

    public final KtDiagnosticFactory0 getJVM_STATIC_NOT_IN_OBJECT_OR_CLASS_COMPANION() {
        return (KtDiagnosticFactory0) JVM_STATIC_NOT_IN_OBJECT_OR_CLASS_COMPANION.getValue(this, $$delegatedProperties[2]);
    }

    public final KtDiagnosticFactory0 getJVM_STATIC_NOT_IN_OBJECT_OR_COMPANION() {
        return (KtDiagnosticFactory0) JVM_STATIC_NOT_IN_OBJECT_OR_COMPANION.getValue(this, $$delegatedProperties[3]);
    }

    public final KtDiagnosticFactory0 getJVM_STATIC_ON_NON_PUBLIC_MEMBER() {
        return (KtDiagnosticFactory0) JVM_STATIC_ON_NON_PUBLIC_MEMBER.getValue(this, $$delegatedProperties[4]);
    }

    public final KtDiagnosticFactory0 getJVM_STATIC_ON_CONST_OR_JVM_FIELD() {
        return (KtDiagnosticFactory0) JVM_STATIC_ON_CONST_OR_JVM_FIELD.getValue(this, $$delegatedProperties[5]);
    }

    public final KtDiagnosticFactory0 getJVM_STATIC_ON_EXTERNAL_IN_INTERFACE() {
        return (KtDiagnosticFactory0) JVM_STATIC_ON_EXTERNAL_IN_INTERFACE.getValue(this, $$delegatedProperties[6]);
    }

    public final KtDiagnosticFactory0 getINAPPLICABLE_JVM_NAME() {
        return (KtDiagnosticFactory0) INAPPLICABLE_JVM_NAME.getValue(this, $$delegatedProperties[7]);
    }

    public final KtDiagnosticFactory0 getILLEGAL_JVM_NAME() {
        return (KtDiagnosticFactory0) ILLEGAL_JVM_NAME.getValue(this, $$delegatedProperties[8]);
    }

    public final KtDiagnosticFactory0 getFUNCTION_DELEGATE_MEMBER_NAME_CLASH() {
        return (KtDiagnosticFactory0) FUNCTION_DELEGATE_MEMBER_NAME_CLASH.getValue(this, $$delegatedProperties[9]);
    }

    public final KtDiagnosticFactory0 getVALUE_CLASS_WITHOUT_JVM_INLINE_ANNOTATION() {
        return (KtDiagnosticFactory0) VALUE_CLASS_WITHOUT_JVM_INLINE_ANNOTATION.getValue(this, $$delegatedProperties[10]);
    }

    public final KtDiagnosticFactory0 getJVM_INLINE_WITHOUT_VALUE_CLASS() {
        return (KtDiagnosticFactory0) JVM_INLINE_WITHOUT_VALUE_CLASS.getValue(this, $$delegatedProperties[11]);
    }

    public final KtDiagnosticFactory2<ConeKotlinType, ConeKotlinType> getJAVA_TYPE_MISMATCH() {
        return (KtDiagnosticFactory2) JAVA_TYPE_MISMATCH.getValue(this, $$delegatedProperties[12]);
    }

    public final KtDiagnosticFactory0 getUPPER_BOUND_CANNOT_BE_ARRAY() {
        return (KtDiagnosticFactory0) UPPER_BOUND_CANNOT_BE_ARRAY.getValue(this, $$delegatedProperties[13]);
    }

    public final KtDiagnosticFactory0 getSTRICTFP_ON_CLASS() {
        return (KtDiagnosticFactory0) STRICTFP_ON_CLASS.getValue(this, $$delegatedProperties[14]);
    }

    public final KtDiagnosticFactory0 getSYNCHRONIZED_ON_ABSTRACT() {
        return (KtDiagnosticFactory0) SYNCHRONIZED_ON_ABSTRACT.getValue(this, $$delegatedProperties[15]);
    }

    public final KtDiagnosticFactory0 getSYNCHRONIZED_IN_INTERFACE() {
        return (KtDiagnosticFactory0) SYNCHRONIZED_IN_INTERFACE.getValue(this, $$delegatedProperties[16]);
    }

    public final KtDiagnosticFactory0 getSYNCHRONIZED_ON_INLINE() {
        return (KtDiagnosticFactory0) SYNCHRONIZED_ON_INLINE.getValue(this, $$delegatedProperties[17]);
    }

    public final KtDiagnosticFactoryForDeprecation0 getSYNCHRONIZED_ON_SUSPEND() {
        return (KtDiagnosticFactoryForDeprecation0) SYNCHRONIZED_ON_SUSPEND.getValue(this, $$delegatedProperties[18]);
    }

    public final KtDiagnosticFactory0 getOVERLOADS_WITHOUT_DEFAULT_ARGUMENTS() {
        return (KtDiagnosticFactory0) OVERLOADS_WITHOUT_DEFAULT_ARGUMENTS.getValue(this, $$delegatedProperties[19]);
    }

    public final KtDiagnosticFactory0 getOVERLOADS_ABSTRACT() {
        return (KtDiagnosticFactory0) OVERLOADS_ABSTRACT.getValue(this, $$delegatedProperties[20]);
    }

    public final KtDiagnosticFactory0 getOVERLOADS_INTERFACE() {
        return (KtDiagnosticFactory0) OVERLOADS_INTERFACE.getValue(this, $$delegatedProperties[21]);
    }

    public final KtDiagnosticFactory0 getOVERLOADS_LOCAL() {
        return (KtDiagnosticFactory0) OVERLOADS_LOCAL.getValue(this, $$delegatedProperties[22]);
    }

    public final KtDiagnosticFactoryForDeprecation0 getOVERLOADS_ANNOTATION_CLASS_CONSTRUCTOR() {
        return (KtDiagnosticFactoryForDeprecation0) OVERLOADS_ANNOTATION_CLASS_CONSTRUCTOR.getValue(this, $$delegatedProperties[23]);
    }

    public final KtDiagnosticFactory0 getOVERLOADS_PRIVATE() {
        return (KtDiagnosticFactory0) OVERLOADS_PRIVATE.getValue(this, $$delegatedProperties[24]);
    }

    public final KtDiagnosticFactory1<FqName> getDEPRECATED_JAVA_ANNOTATION() {
        return (KtDiagnosticFactory1) DEPRECATED_JAVA_ANNOTATION.getValue(this, $$delegatedProperties[25]);
    }

    public final KtDiagnosticFactory0 getJVM_PACKAGE_NAME_CANNOT_BE_EMPTY() {
        return (KtDiagnosticFactory0) JVM_PACKAGE_NAME_CANNOT_BE_EMPTY.getValue(this, $$delegatedProperties[26]);
    }

    public final KtDiagnosticFactory0 getJVM_PACKAGE_NAME_MUST_BE_VALID_NAME() {
        return (KtDiagnosticFactory0) JVM_PACKAGE_NAME_MUST_BE_VALID_NAME.getValue(this, $$delegatedProperties[27]);
    }

    public final KtDiagnosticFactory0 getJVM_PACKAGE_NAME_NOT_SUPPORTED_IN_FILES_WITH_CLASSES() {
        return (KtDiagnosticFactory0) JVM_PACKAGE_NAME_NOT_SUPPORTED_IN_FILES_WITH_CLASSES.getValue(this, $$delegatedProperties[28]);
    }

    public final KtDiagnosticFactory0 getPOSITIONED_VALUE_ARGUMENT_FOR_JAVA_ANNOTATION() {
        return (KtDiagnosticFactory0) POSITIONED_VALUE_ARGUMENT_FOR_JAVA_ANNOTATION.getValue(this, $$delegatedProperties[29]);
    }

    public final KtDiagnosticFactory2<FqName, FqName> getREDUNDANT_REPEATABLE_ANNOTATION() {
        return (KtDiagnosticFactory2) REDUNDANT_REPEATABLE_ANNOTATION.getValue(this, $$delegatedProperties[30]);
    }

    public final KtDiagnosticFactory1<String> getSUPER_CALL_WITH_DEFAULT_PARAMETERS() {
        return (KtDiagnosticFactory1) SUPER_CALL_WITH_DEFAULT_PARAMETERS.getValue(this, $$delegatedProperties[31]);
    }

    public final KtDiagnosticFactory0 getINTERFACE_CANT_CALL_DEFAULT_METHOD_VIA_SUPER() {
        return (KtDiagnosticFactory0) INTERFACE_CANT_CALL_DEFAULT_METHOD_VIA_SUPER.getValue(this, $$delegatedProperties[32]);
    }

    public final KtDiagnosticFactory0 getLOCAL_JVM_RECORD() {
        return (KtDiagnosticFactory0) LOCAL_JVM_RECORD.getValue(this, $$delegatedProperties[33]);
    }

    public final KtDiagnosticFactory0 getNON_FINAL_JVM_RECORD() {
        return (KtDiagnosticFactory0) NON_FINAL_JVM_RECORD.getValue(this, $$delegatedProperties[34]);
    }

    public final KtDiagnosticFactory0 getENUM_JVM_RECORD() {
        return (KtDiagnosticFactory0) ENUM_JVM_RECORD.getValue(this, $$delegatedProperties[35]);
    }

    public final KtDiagnosticFactory0 getJVM_RECORD_WITHOUT_PRIMARY_CONSTRUCTOR_PARAMETERS() {
        return (KtDiagnosticFactory0) JVM_RECORD_WITHOUT_PRIMARY_CONSTRUCTOR_PARAMETERS.getValue(this, $$delegatedProperties[36]);
    }

    public final KtDiagnosticFactory0 getNON_DATA_CLASS_JVM_RECORD() {
        return (KtDiagnosticFactory0) NON_DATA_CLASS_JVM_RECORD.getValue(this, $$delegatedProperties[37]);
    }

    public final KtDiagnosticFactory0 getJVM_RECORD_NOT_VAL_PARAMETER() {
        return (KtDiagnosticFactory0) JVM_RECORD_NOT_VAL_PARAMETER.getValue(this, $$delegatedProperties[38]);
    }

    public final KtDiagnosticFactory0 getJVM_RECORD_NOT_LAST_VARARG_PARAMETER() {
        return (KtDiagnosticFactory0) JVM_RECORD_NOT_LAST_VARARG_PARAMETER.getValue(this, $$delegatedProperties[39]);
    }

    public final KtDiagnosticFactory0 getINNER_JVM_RECORD() {
        return (KtDiagnosticFactory0) INNER_JVM_RECORD.getValue(this, $$delegatedProperties[40]);
    }

    public final KtDiagnosticFactory0 getFIELD_IN_JVM_RECORD() {
        return (KtDiagnosticFactory0) FIELD_IN_JVM_RECORD.getValue(this, $$delegatedProperties[41]);
    }

    public final KtDiagnosticFactory0 getDELEGATION_BY_IN_JVM_RECORD() {
        return (KtDiagnosticFactory0) DELEGATION_BY_IN_JVM_RECORD.getValue(this, $$delegatedProperties[42]);
    }

    public final KtDiagnosticFactory1<ConeKotlinType> getJVM_RECORD_EXTENDS_CLASS() {
        return (KtDiagnosticFactory1) JVM_RECORD_EXTENDS_CLASS.getValue(this, $$delegatedProperties[43]);
    }

    public final KtDiagnosticFactory0 getILLEGAL_JAVA_LANG_RECORD_SUPERTYPE() {
        return (KtDiagnosticFactory0) ILLEGAL_JAVA_LANG_RECORD_SUPERTYPE.getValue(this, $$delegatedProperties[44]);
    }

    public final KtDiagnosticFactory1<String> getJVM_DEFAULT_IN_DECLARATION() {
        return (KtDiagnosticFactory1) JVM_DEFAULT_IN_DECLARATION.getValue(this, $$delegatedProperties[45]);
    }

    public final KtDiagnosticFactory0 getJVM_DEFAULT_WITH_COMPATIBILITY_IN_DECLARATION() {
        return (KtDiagnosticFactory0) JVM_DEFAULT_WITH_COMPATIBILITY_IN_DECLARATION.getValue(this, $$delegatedProperties[46]);
    }

    public final KtDiagnosticFactory0 getJVM_DEFAULT_WITH_COMPATIBILITY_NOT_ON_INTERFACE() {
        return (KtDiagnosticFactory0) JVM_DEFAULT_WITH_COMPATIBILITY_NOT_ON_INTERFACE.getValue(this, $$delegatedProperties[47]);
    }

    public final KtDiagnosticFactory0 getEXTERNAL_DECLARATION_CANNOT_BE_ABSTRACT() {
        return (KtDiagnosticFactory0) EXTERNAL_DECLARATION_CANNOT_BE_ABSTRACT.getValue(this, $$delegatedProperties[48]);
    }

    public final KtDiagnosticFactory0 getEXTERNAL_DECLARATION_CANNOT_HAVE_BODY() {
        return (KtDiagnosticFactory0) EXTERNAL_DECLARATION_CANNOT_HAVE_BODY.getValue(this, $$delegatedProperties[49]);
    }

    public final KtDiagnosticFactory0 getEXTERNAL_DECLARATION_IN_INTERFACE() {
        return (KtDiagnosticFactory0) EXTERNAL_DECLARATION_IN_INTERFACE.getValue(this, $$delegatedProperties[50]);
    }

    public final KtDiagnosticFactory0 getEXTERNAL_DECLARATION_CANNOT_BE_INLINED() {
        return (KtDiagnosticFactory0) EXTERNAL_DECLARATION_CANNOT_BE_INLINED.getValue(this, $$delegatedProperties[51]);
    }

    public final KtDiagnosticFactory0 getNON_SOURCE_REPEATED_ANNOTATION() {
        return (KtDiagnosticFactory0) NON_SOURCE_REPEATED_ANNOTATION.getValue(this, $$delegatedProperties[52]);
    }

    public final KtDiagnosticFactory2<ClassId, ClassId> getREPEATED_ANNOTATION_WITH_CONTAINER() {
        return (KtDiagnosticFactory2) REPEATED_ANNOTATION_WITH_CONTAINER.getValue(this, $$delegatedProperties[53]);
    }

    public final KtDiagnosticFactoryForDeprecation2<ClassId, ClassId> getREPEATABLE_CONTAINER_MUST_HAVE_VALUE_ARRAY() {
        return (KtDiagnosticFactoryForDeprecation2) REPEATABLE_CONTAINER_MUST_HAVE_VALUE_ARRAY.getValue(this, $$delegatedProperties[54]);
    }

    public final KtDiagnosticFactoryForDeprecation2<ClassId, Name> getREPEATABLE_CONTAINER_HAS_NON_DEFAULT_PARAMETER() {
        return (KtDiagnosticFactoryForDeprecation2) REPEATABLE_CONTAINER_HAS_NON_DEFAULT_PARAMETER.getValue(this, $$delegatedProperties[55]);
    }

    public final KtDiagnosticFactoryForDeprecation4<ClassId, String, ClassId, String> getREPEATABLE_CONTAINER_HAS_SHORTER_RETENTION() {
        return (KtDiagnosticFactoryForDeprecation4) REPEATABLE_CONTAINER_HAS_SHORTER_RETENTION.getValue(this, $$delegatedProperties[56]);
    }

    public final KtDiagnosticFactoryForDeprecation2<ClassId, ClassId> getREPEATABLE_CONTAINER_TARGET_SET_NOT_A_SUBSET() {
        return (KtDiagnosticFactoryForDeprecation2) REPEATABLE_CONTAINER_TARGET_SET_NOT_A_SUBSET.getValue(this, $$delegatedProperties[57]);
    }

    public final KtDiagnosticFactoryForDeprecation0 getREPEATABLE_ANNOTATION_HAS_NESTED_CLASS_NAMED_CONTAINER() {
        return (KtDiagnosticFactoryForDeprecation0) REPEATABLE_ANNOTATION_HAS_NESTED_CLASS_NAMED_CONTAINER.getValue(this, $$delegatedProperties[58]);
    }

    public final KtDiagnosticFactory1<FirCallableSymbol<?>> getSUSPENSION_POINT_INSIDE_CRITICAL_SECTION() {
        return (KtDiagnosticFactory1) SUSPENSION_POINT_INSIDE_CRITICAL_SECTION.getValue(this, $$delegatedProperties[59]);
    }

    public final KtDiagnosticFactory1<String> getINAPPLICABLE_JVM_FIELD() {
        return (KtDiagnosticFactory1) INAPPLICABLE_JVM_FIELD.getValue(this, $$delegatedProperties[60]);
    }

    public final KtDiagnosticFactory1<String> getINAPPLICABLE_JVM_FIELD_WARNING() {
        return (KtDiagnosticFactory1) INAPPLICABLE_JVM_FIELD_WARNING.getValue(this, $$delegatedProperties[61]);
    }

    public final KtDiagnosticFactory0 getJVM_SYNTHETIC_ON_DELEGATE() {
        return (KtDiagnosticFactory0) JVM_SYNTHETIC_ON_DELEGATE.getValue(this, $$delegatedProperties[62]);
    }

    public final KtDiagnosticFactory0 getSUBCLASS_CANT_CALL_COMPANION_PROTECTED_NON_STATIC() {
        return (KtDiagnosticFactory0) SUBCLASS_CANT_CALL_COMPANION_PROTECTED_NON_STATIC.getValue(this, $$delegatedProperties[63]);
    }

    public final KtDiagnosticFactoryForDeprecation0 getCONCURRENT_HASH_MAP_CONTAINS_OPERATOR() {
        return (KtDiagnosticFactoryForDeprecation0) CONCURRENT_HASH_MAP_CONTAINS_OPERATOR.getValue(this, $$delegatedProperties[64]);
    }

    public final KtDiagnosticFactoryForDeprecation0 getSPREAD_ON_SIGNATURE_POLYMORPHIC_CALL() {
        return (KtDiagnosticFactoryForDeprecation0) SPREAD_ON_SIGNATURE_POLYMORPHIC_CALL.getValue(this, $$delegatedProperties[65]);
    }

    public final KtDiagnosticFactory0 getJAVA_SAM_INTERFACE_CONSTRUCTOR_REFERENCE() {
        return (KtDiagnosticFactory0) JAVA_SAM_INTERFACE_CONSTRUCTOR_REFERENCE.getValue(this, $$delegatedProperties[66]);
    }

    public final KtDiagnosticFactory0 getNO_REFLECTION_IN_CLASS_PATH() {
        return (KtDiagnosticFactory0) NO_REFLECTION_IN_CLASS_PATH.getValue(this, $$delegatedProperties[67]);
    }
}
