package org.jetbrains.kotlin.resolve.konan.diagnostics;

import java.util.Collection;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.config.LanguageFeature;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.diagnostics.DiagnosticFactory0;
import org.jetbrains.kotlin.diagnostics.DiagnosticFactory1;
import org.jetbrains.kotlin.diagnostics.DiagnosticFactory2;
import org.jetbrains.kotlin.diagnostics.DiagnosticFactoryForDeprecation1;
import org.jetbrains.kotlin.diagnostics.Errors;
import org.jetbrains.kotlin.diagnostics.Severity;
import org.jetbrains.kotlin.metadata.jvm.deserialization.JvmProtoBufUtil;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.psi.KtDeclaration;
import org.jetbrains.kotlin.psi.KtElement;
import org.jetbrains.kotlin.types.KotlinType;

/* JADX INFO: compiled from: ErrorsNative.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000T\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u001e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0006\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R,\u0010\u0003\u001a\u001e\u0012\f\u0012\n \u0006*\u0004\u0018\u00010\u00050\u0005\u0012\f\u0012\n \u0006*\u0004\u0018\u00010\u00070\u00070\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u001e\u0010\b\u001a\u0010\u0012\f\u0012\n \u0006*\u0004\u0018\u00010\n0\n0\t8\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R,\u0010\u000b\u001a\u001e\u0012\f\u0012\n \u0006*\u0004\u0018\u00010\u00050\u0005\u0012\f\u0012\n \u0006*\u0004\u0018\u00010\u00070\u00070\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R,\u0010\f\u001a\u001e\u0012\f\u0012\n \u0006*\u0004\u0018\u00010\u00050\u0005\u0012\f\u0012\n \u0006*\u0004\u0018\u00010\u00070\u00070\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u001e\u0010\r\u001a\u0010\u0012\f\u0012\n \u0006*\u0004\u0018\u00010\n0\n0\t8\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u001e\u0010\u000e\u001a\u0010\u0012\f\u0012\n \u0006*\u0004\u0018\u00010\n0\n0\t8\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u001e\u0010\u000f\u001a\u0010\u0012\f\u0012\n \u0006*\u0004\u0018\u00010\n0\n0\t8\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u001e\u0010\u0010\u001a\u0010\u0012\f\u0012\n \u0006*\u0004\u0018\u00010\n0\n0\t8\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u001e\u0010\u0011\u001a\u0010\u0012\f\u0012\n \u0006*\u0004\u0018\u00010\n0\n0\t8\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u001e\u0010\u0012\u001a\u0010\u0012\f\u0012\n \u0006*\u0004\u0018\u00010\n0\n0\t8\u0006X\u0087\u0004¢\u0006\u0002\n\u0000RF\u0010\u0013\u001a8\u0012\f\u0012\n \u0006*\u0004\u0018\u00010\n0\n\u0012\f\u0012\n \u0006*\u0004\u0018\u00010\u00150\u0015\u0012\u0018\u0012\u0016\u0012\u0004\u0012\u00020\u0015 \u0006*\n\u0012\u0004\u0012\u00020\u0015\u0018\u00010\u00160\u00160\u00148\u0006X\u0087\u0004¢\u0006\u0002\n\u0000RF\u0010\u0017\u001a8\u0012\f\u0012\n \u0006*\u0004\u0018\u00010\n0\n\u0012\f\u0012\n \u0006*\u0004\u0018\u00010\u00150\u0015\u0012\u0018\u0012\u0016\u0012\u0004\u0012\u00020\u0015 \u0006*\n\u0012\u0004\u0012\u00020\u0015\u0018\u00010\u00160\u00160\u00148\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R8\u0010\u0018\u001a*\u0012\f\u0012\n \u0006*\u0004\u0018\u00010\u00190\u0019\u0012\u0018\u0012\u0016\u0012\u0004\u0012\u00020\u0015 \u0006*\n\u0012\u0004\u0012\u00020\u0015\u0018\u00010\u00160\u00160\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R,\u0010\u001a\u001a\u001e\u0012\f\u0012\n \u0006*\u0004\u0018\u00010\n0\n\u0012\f\u0012\n \u0006*\u0004\u0018\u00010\u00150\u00150\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u001c\u0010\u001b\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u001d0\u001c8\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u001e\u0010\u001e\u001a\u0010\u0012\f\u0012\n \u0006*\u0004\u0018\u00010\n0\n0\t8\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u001e\u0010\u001f\u001a\u0010\u0012\f\u0012\n \u0006*\u0004\u0018\u00010\n0\n0\t8\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R,\u0010 \u001a\u001e\u0012\f\u0012\n \u0006*\u0004\u0018\u00010\n0\n\u0012\f\u0012\n \u0006*\u0004\u0018\u00010\u001d0\u001d0\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R,\u0010!\u001a\u001e\u0012\f\u0012\n \u0006*\u0004\u0018\u00010\n0\n\u0012\f\u0012\n \u0006*\u0004\u0018\u00010\u001d0\u001d0\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u001e\u0010\"\u001a\u0010\u0012\f\u0012\n \u0006*\u0004\u0018\u00010\n0\n0\t8\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u001e\u0010#\u001a\u0010\u0012\f\u0012\n \u0006*\u0004\u0018\u00010\n0\n0\t8\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R,\u0010$\u001a\u001e\u0012\f\u0012\n \u0006*\u0004\u0018\u00010\n0\n\u0012\f\u0012\n \u0006*\u0004\u0018\u00010%0%0\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u001e\u0010&\u001a\u0010\u0012\f\u0012\n \u0006*\u0004\u0018\u00010\n0\n0\t8\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u001e\u0010'\u001a\u0010\u0012\f\u0012\n \u0006*\u0004\u0018\u00010\n0\n0\t8\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u001e\u0010(\u001a\u0010\u0012\f\u0012\n \u0006*\u0004\u0018\u00010\n0\n0\t8\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u001e\u0010)\u001a\u0010\u0012\f\u0012\n \u0006*\u0004\u0018\u00010\n0\n0\t8\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R:\u0010*\u001a,\u0012\f\u0012\n \u0006*\u0004\u0018\u00010\u00050\u0005\u0012\f\u0012\n \u0006*\u0004\u0018\u00010\u00070\u0007\u0012\f\u0012\n \u0006*\u0004\u0018\u00010\u00070\u00070\u00148\u0006X\u0087\u0004¢\u0006\u0002\n\u0000¨\u0006+"}, d2 = {"Lorg/jetbrains/kotlin/resolve/konan/diagnostics/ErrorsNative;", "", "()V", "CANNOT_CHECK_FOR_FORWARD_DECLARATION", "Lorg/jetbrains/kotlin/diagnostics/DiagnosticFactory1;", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", JvmProtoBufUtil.PLATFORM_TYPE_ID, "Lorg/jetbrains/kotlin/types/KotlinType;", "EMPTY_OBJC_NAME", "Lorg/jetbrains/kotlin/diagnostics/DiagnosticFactory0;", "Lorg/jetbrains/kotlin/psi/KtElement;", "FORWARD_DECLARATION_AS_CLASS_LITERAL", "FORWARD_DECLARATION_AS_REIFIED_TYPE_ARGUMENT", "INAPPLICABLE_EXACT_OBJC_NAME", "INAPPLICABLE_OBJC_NAME", "INAPPLICABLE_SHARED_IMMUTABLE_PROPERTY", "INAPPLICABLE_SHARED_IMMUTABLE_TOP_LEVEL", "INAPPLICABLE_THREAD_LOCAL", "INAPPLICABLE_THREAD_LOCAL_TOP_LEVEL", "INCOMPATIBLE_OBJC_NAME_OVERRIDE", "Lorg/jetbrains/kotlin/diagnostics/DiagnosticFactory2;", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "", "INCOMPATIBLE_OBJC_REFINEMENT_OVERRIDE", "INCOMPATIBLE_THROWS_INHERITED", "Lorg/jetbrains/kotlin/psi/KtDeclaration;", "INCOMPATIBLE_THROWS_OVERRIDE", "INVALID_CHARACTERS_NATIVE", "Lorg/jetbrains/kotlin/diagnostics/DiagnosticFactoryForDeprecation1;", "", "INVALID_OBJC_HIDES_TARGETS", "INVALID_OBJC_NAME", "INVALID_OBJC_NAME_CHARS", "INVALID_OBJC_NAME_FIRST_CHAR", "INVALID_REFINES_IN_SWIFT_TARGETS", "MISSING_EXACT_OBJC_NAME", "MISSING_EXCEPTION_IN_THROWS_ON_SUSPEND", "Lorg/jetbrains/kotlin/name/FqName;", "NON_LITERAL_OBJC_NAME_ARG", "REDUNDANT_SWIFT_REFINEMENT", "SUBTYPE_OF_HIDDEN_FROM_OBJC", "THROWS_LIST_EMPTY", "UNCHECKED_CAST_TO_FORWARD_DECLARATION", "frontend.native"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ErrorsNative {
    public static final DiagnosticFactory1<PsiElement, KotlinType> CANNOT_CHECK_FOR_FORWARD_DECLARATION;
    public static final DiagnosticFactory0<KtElement> EMPTY_OBJC_NAME;
    public static final DiagnosticFactory1<PsiElement, KotlinType> FORWARD_DECLARATION_AS_CLASS_LITERAL;
    public static final DiagnosticFactory1<PsiElement, KotlinType> FORWARD_DECLARATION_AS_REIFIED_TYPE_ARGUMENT;
    public static final DiagnosticFactory0<KtElement> INAPPLICABLE_EXACT_OBJC_NAME;
    public static final DiagnosticFactory0<KtElement> INAPPLICABLE_OBJC_NAME;
    public static final DiagnosticFactory0<KtElement> INAPPLICABLE_SHARED_IMMUTABLE_PROPERTY;
    public static final DiagnosticFactory0<KtElement> INAPPLICABLE_SHARED_IMMUTABLE_TOP_LEVEL;
    public static final DiagnosticFactory0<KtElement> INAPPLICABLE_THREAD_LOCAL;
    public static final DiagnosticFactory0<KtElement> INAPPLICABLE_THREAD_LOCAL_TOP_LEVEL;
    public static final DiagnosticFactory2<KtElement, DeclarationDescriptor, Collection<DeclarationDescriptor>> INCOMPATIBLE_OBJC_NAME_OVERRIDE;
    public static final DiagnosticFactory2<KtElement, DeclarationDescriptor, Collection<DeclarationDescriptor>> INCOMPATIBLE_OBJC_REFINEMENT_OVERRIDE;
    public static final DiagnosticFactory1<KtDeclaration, Collection<DeclarationDescriptor>> INCOMPATIBLE_THROWS_INHERITED;
    public static final DiagnosticFactory1<KtElement, DeclarationDescriptor> INCOMPATIBLE_THROWS_OVERRIDE;
    public static final ErrorsNative INSTANCE = new ErrorsNative();
    public static final DiagnosticFactoryForDeprecation1<PsiElement, String> INVALID_CHARACTERS_NATIVE;
    public static final DiagnosticFactory0<KtElement> INVALID_OBJC_HIDES_TARGETS;
    public static final DiagnosticFactory0<KtElement> INVALID_OBJC_NAME;
    public static final DiagnosticFactory1<KtElement, String> INVALID_OBJC_NAME_CHARS;
    public static final DiagnosticFactory1<KtElement, String> INVALID_OBJC_NAME_FIRST_CHAR;
    public static final DiagnosticFactory0<KtElement> INVALID_REFINES_IN_SWIFT_TARGETS;
    public static final DiagnosticFactory0<KtElement> MISSING_EXACT_OBJC_NAME;
    public static final DiagnosticFactory1<KtElement, FqName> MISSING_EXCEPTION_IN_THROWS_ON_SUSPEND;
    public static final DiagnosticFactory0<KtElement> NON_LITERAL_OBJC_NAME_ARG;
    public static final DiagnosticFactory0<KtElement> REDUNDANT_SWIFT_REFINEMENT;
    public static final DiagnosticFactory0<KtElement> SUBTYPE_OF_HIDDEN_FROM_OBJC;
    public static final DiagnosticFactory0<KtElement> THROWS_LIST_EMPTY;
    public static final DiagnosticFactory2<PsiElement, KotlinType, KotlinType> UNCHECKED_CAST_TO_FORWARD_DECLARATION;

    private ErrorsNative() {
    }

    static {
        DiagnosticFactory0<KtElement> diagnosticFactory0Create = DiagnosticFactory0.create(Severity.ERROR);
        Intrinsics.checkNotNullExpressionValue(diagnosticFactory0Create, "create(...)");
        THROWS_LIST_EMPTY = diagnosticFactory0Create;
        DiagnosticFactory1<KtElement, DeclarationDescriptor> diagnosticFactory1Create = DiagnosticFactory1.create(Severity.ERROR);
        Intrinsics.checkNotNullExpressionValue(diagnosticFactory1Create, "create(...)");
        INCOMPATIBLE_THROWS_OVERRIDE = diagnosticFactory1Create;
        DiagnosticFactory1<KtDeclaration, Collection<DeclarationDescriptor>> diagnosticFactory1Create2 = DiagnosticFactory1.create(Severity.ERROR);
        Intrinsics.checkNotNullExpressionValue(diagnosticFactory1Create2, "create(...)");
        INCOMPATIBLE_THROWS_INHERITED = diagnosticFactory1Create2;
        DiagnosticFactory1<KtElement, FqName> diagnosticFactory1Create3 = DiagnosticFactory1.create(Severity.ERROR);
        Intrinsics.checkNotNullExpressionValue(diagnosticFactory1Create3, "create(...)");
        MISSING_EXCEPTION_IN_THROWS_ON_SUSPEND = diagnosticFactory1Create3;
        DiagnosticFactory0<KtElement> diagnosticFactory0Create2 = DiagnosticFactory0.create(Severity.ERROR);
        Intrinsics.checkNotNullExpressionValue(diagnosticFactory0Create2, "create(...)");
        INAPPLICABLE_SHARED_IMMUTABLE_PROPERTY = diagnosticFactory0Create2;
        DiagnosticFactory0<KtElement> diagnosticFactory0Create3 = DiagnosticFactory0.create(Severity.ERROR);
        Intrinsics.checkNotNullExpressionValue(diagnosticFactory0Create3, "create(...)");
        INAPPLICABLE_SHARED_IMMUTABLE_TOP_LEVEL = diagnosticFactory0Create3;
        DiagnosticFactory0<KtElement> diagnosticFactory0Create4 = DiagnosticFactory0.create(Severity.ERROR);
        Intrinsics.checkNotNullExpressionValue(diagnosticFactory0Create4, "create(...)");
        INAPPLICABLE_THREAD_LOCAL = diagnosticFactory0Create4;
        DiagnosticFactory0<KtElement> diagnosticFactory0Create5 = DiagnosticFactory0.create(Severity.ERROR);
        Intrinsics.checkNotNullExpressionValue(diagnosticFactory0Create5, "create(...)");
        INAPPLICABLE_THREAD_LOCAL_TOP_LEVEL = diagnosticFactory0Create5;
        INVALID_CHARACTERS_NATIVE = DiagnosticFactoryForDeprecation1.Companion.create$default(DiagnosticFactoryForDeprecation1.INSTANCE, LanguageFeature.ProhibitInvalidCharsInNativeIdentifiers, null, 2, null);
        DiagnosticFactory0<KtElement> diagnosticFactory0Create6 = DiagnosticFactory0.create(Severity.ERROR);
        Intrinsics.checkNotNullExpressionValue(diagnosticFactory0Create6, "create(...)");
        INAPPLICABLE_OBJC_NAME = diagnosticFactory0Create6;
        DiagnosticFactory0<KtElement> diagnosticFactory0Create7 = DiagnosticFactory0.create(Severity.ERROR);
        Intrinsics.checkNotNullExpressionValue(diagnosticFactory0Create7, "create(...)");
        INVALID_OBJC_NAME = diagnosticFactory0Create7;
        DiagnosticFactory1<KtElement, String> diagnosticFactory1Create4 = DiagnosticFactory1.create(Severity.ERROR);
        Intrinsics.checkNotNullExpressionValue(diagnosticFactory1Create4, "create(...)");
        INVALID_OBJC_NAME_CHARS = diagnosticFactory1Create4;
        DiagnosticFactory1<KtElement, String> diagnosticFactory1Create5 = DiagnosticFactory1.create(Severity.ERROR);
        Intrinsics.checkNotNullExpressionValue(diagnosticFactory1Create5, "create(...)");
        INVALID_OBJC_NAME_FIRST_CHAR = diagnosticFactory1Create5;
        DiagnosticFactory0<KtElement> diagnosticFactory0Create8 = DiagnosticFactory0.create(Severity.ERROR);
        Intrinsics.checkNotNullExpressionValue(diagnosticFactory0Create8, "create(...)");
        EMPTY_OBJC_NAME = diagnosticFactory0Create8;
        DiagnosticFactory2<KtElement, DeclarationDescriptor, Collection<DeclarationDescriptor>> diagnosticFactory2Create = DiagnosticFactory2.create(Severity.ERROR);
        Intrinsics.checkNotNullExpressionValue(diagnosticFactory2Create, "create(...)");
        INCOMPATIBLE_OBJC_NAME_OVERRIDE = diagnosticFactory2Create;
        DiagnosticFactory0<KtElement> diagnosticFactory0Create9 = DiagnosticFactory0.create(Severity.ERROR);
        Intrinsics.checkNotNullExpressionValue(diagnosticFactory0Create9, "create(...)");
        INAPPLICABLE_EXACT_OBJC_NAME = diagnosticFactory0Create9;
        DiagnosticFactory0<KtElement> diagnosticFactory0Create10 = DiagnosticFactory0.create(Severity.ERROR);
        Intrinsics.checkNotNullExpressionValue(diagnosticFactory0Create10, "create(...)");
        MISSING_EXACT_OBJC_NAME = diagnosticFactory0Create10;
        DiagnosticFactory0<KtElement> diagnosticFactory0Create11 = DiagnosticFactory0.create(Severity.ERROR);
        Intrinsics.checkNotNullExpressionValue(diagnosticFactory0Create11, "create(...)");
        NON_LITERAL_OBJC_NAME_ARG = diagnosticFactory0Create11;
        DiagnosticFactory0<KtElement> diagnosticFactory0Create12 = DiagnosticFactory0.create(Severity.ERROR);
        Intrinsics.checkNotNullExpressionValue(diagnosticFactory0Create12, "create(...)");
        REDUNDANT_SWIFT_REFINEMENT = diagnosticFactory0Create12;
        DiagnosticFactory2<KtElement, DeclarationDescriptor, Collection<DeclarationDescriptor>> diagnosticFactory2Create2 = DiagnosticFactory2.create(Severity.ERROR);
        Intrinsics.checkNotNullExpressionValue(diagnosticFactory2Create2, "create(...)");
        INCOMPATIBLE_OBJC_REFINEMENT_OVERRIDE = diagnosticFactory2Create2;
        DiagnosticFactory0<KtElement> diagnosticFactory0Create13 = DiagnosticFactory0.create(Severity.ERROR);
        Intrinsics.checkNotNullExpressionValue(diagnosticFactory0Create13, "create(...)");
        INVALID_OBJC_HIDES_TARGETS = diagnosticFactory0Create13;
        DiagnosticFactory0<KtElement> diagnosticFactory0Create14 = DiagnosticFactory0.create(Severity.ERROR);
        Intrinsics.checkNotNullExpressionValue(diagnosticFactory0Create14, "create(...)");
        INVALID_REFINES_IN_SWIFT_TARGETS = diagnosticFactory0Create14;
        DiagnosticFactory0<KtElement> diagnosticFactory0Create15 = DiagnosticFactory0.create(Severity.ERROR);
        Intrinsics.checkNotNullExpressionValue(diagnosticFactory0Create15, "create(...)");
        SUBTYPE_OF_HIDDEN_FROM_OBJC = diagnosticFactory0Create15;
        DiagnosticFactory1<PsiElement, KotlinType> diagnosticFactory1Create6 = DiagnosticFactory1.create(Severity.ERROR);
        Intrinsics.checkNotNullExpressionValue(diagnosticFactory1Create6, "create(...)");
        CANNOT_CHECK_FOR_FORWARD_DECLARATION = diagnosticFactory1Create6;
        DiagnosticFactory2<PsiElement, KotlinType, KotlinType> diagnosticFactory2Create3 = DiagnosticFactory2.create(Severity.WARNING);
        Intrinsics.checkNotNullExpressionValue(diagnosticFactory2Create3, "create(...)");
        UNCHECKED_CAST_TO_FORWARD_DECLARATION = diagnosticFactory2Create3;
        DiagnosticFactory1<PsiElement, KotlinType> diagnosticFactory1Create7 = DiagnosticFactory1.create(Severity.ERROR);
        Intrinsics.checkNotNullExpressionValue(diagnosticFactory1Create7, "create(...)");
        FORWARD_DECLARATION_AS_REIFIED_TYPE_ARGUMENT = diagnosticFactory1Create7;
        DiagnosticFactory1<PsiElement, KotlinType> diagnosticFactory1Create8 = DiagnosticFactory1.create(Severity.ERROR);
        Intrinsics.checkNotNullExpressionValue(diagnosticFactory1Create8, "create(...)");
        FORWARD_DECLARATION_AS_CLASS_LITERAL = diagnosticFactory1Create8;
        Errors.Initializer.initializeFactoryNames(ErrorsNative.class);
    }
}
