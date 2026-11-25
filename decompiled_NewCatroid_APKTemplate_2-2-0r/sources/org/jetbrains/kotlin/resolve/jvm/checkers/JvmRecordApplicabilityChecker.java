package org.jetbrains.kotlin.resolve.jvm.checkers;

import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.TuplesKt;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.config.JvmAnalysisFlags;
import org.jetbrains.kotlin.config.JvmTarget;
import org.jetbrains.kotlin.config.LanguageFeature;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.ClassKind;
import org.jetbrains.kotlin.descriptors.ClassifierDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.ModalityUtilsKt;
import org.jetbrains.kotlin.descriptors.PropertyDescriptor;
import org.jetbrains.kotlin.diagnostics.Errors;
import org.jetbrains.kotlin.incremental.components.NoLookupLocation;
import org.jetbrains.kotlin.lexer.KtModifierKeywordToken;
import org.jetbrains.kotlin.lexer.KtTokens;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.JvmNames;
import org.jetbrains.kotlin.psi.KtAnnotationEntry;
import org.jetbrains.kotlin.psi.KtClassOrObject;
import org.jetbrains.kotlin.psi.KtDeclaration;
import org.jetbrains.kotlin.psi.KtDelegatedSuperTypeEntry;
import org.jetbrains.kotlin.psi.KtElementImplStub;
import org.jetbrains.kotlin.psi.KtModifierList;
import org.jetbrains.kotlin.psi.KtParameter;
import org.jetbrains.kotlin.psi.KtParameterList;
import org.jetbrains.kotlin.psi.KtPrimaryConstructor;
import org.jetbrains.kotlin.psi.KtProperty;
import org.jetbrains.kotlin.psi.KtSuperTypeListEntry;
import org.jetbrains.kotlin.resolve.BindingContext;
import org.jetbrains.kotlin.resolve.DescriptorUtils;
import org.jetbrains.kotlin.resolve.checkers.DeclarationChecker;
import org.jetbrains.kotlin.resolve.checkers.DeclarationCheckerContext;
import org.jetbrains.kotlin.resolve.descriptorUtil.DescriptorUtilsKt;
import org.jetbrains.kotlin.resolve.jvm.JdkClassesKt;
import org.jetbrains.kotlin.resolve.jvm.annotations.JvmAnnotationUtilKt;
import org.jetbrains.kotlin.resolve.jvm.diagnostics.ErrorsJvm;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.TypeConstructor;

/* JADX INFO: compiled from: JvmRecordApplicabilityChecker.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J \u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fH\u0016R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\r"}, d2 = {"Lorg/jetbrains/kotlin/resolve/jvm/checkers/JvmRecordApplicabilityChecker;", "Lorg/jetbrains/kotlin/resolve/checkers/DeclarationChecker;", "jvmTarget", "Lorg/jetbrains/kotlin/config/JvmTarget;", "(Lorg/jetbrains/kotlin/config/JvmTarget;)V", "check", "", "declaration", "Lorg/jetbrains/kotlin/psi/KtDeclaration;", "descriptor", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "context", "Lorg/jetbrains/kotlin/resolve/checkers/DeclarationCheckerContext;", "frontend.java"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class JvmRecordApplicabilityChecker implements DeclarationChecker {
    private final JvmTarget jvmTarget;

    public JvmRecordApplicabilityChecker(JvmTarget jvmTarget) {
        Intrinsics.checkNotNullParameter(jvmTarget, "jvmTarget");
        this.jvmTarget = jvmTarget;
    }

    /* JADX WARN: Code duplicated, block: B:64:0x018f  */
    /* JADX WARN: Code duplicated, block: B:66:0x0195  */
    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.jetbrains.kotlin.resolve.checkers.DeclarationChecker
    public void check(KtDeclaration declaration, DeclarationDescriptor descriptor, DeclarationCheckerContext context) {
        FqName fqNameFqNameOrNull;
        Object next;
        List<KtParameter> listEmptyList;
        KtParameterList valueParameterList;
        KtDeclaration nameIdentifier;
        KtDeclaration nameIdentifier2;
        KtDeclaration nameIdentifier3;
        KtSuperTypeListEntry next2;
        TypeConstructor constructor;
        ClassifierDescriptor this$0;
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        Intrinsics.checkNotNullParameter(context, "context");
        if ((descriptor instanceof ClassDescriptor) && (declaration instanceof KtClassOrObject)) {
            KtClassOrObject ktClassOrObject = (KtClassOrObject) declaration;
            Iterator<KtSuperTypeListEntry> it = ktClassOrObject.getSuperTypeListEntries().iterator();
            do {
                fqNameFqNameOrNull = null;
                if (it.hasNext()) {
                    next2 = it.next();
                    KotlinType kotlinType = (KotlinType) context.getTrace().get(BindingContext.TYPE, next2.getTypeReference());
                    if (kotlinType != null && (constructor = kotlinType.getConstructor()) != null && (this$0 = constructor.getThis$0()) != null) {
                        fqNameFqNameOrNull = DescriptorUtilsKt.fqNameOrNull(this$0);
                    }
                } else {
                    ClassDescriptor classDescriptor = (ClassDescriptor) descriptor;
                    if (JvmAnnotationUtilKt.isJvmRecord(classDescriptor)) {
                        List<KtAnnotationEntry> annotationEntries = ktClassOrObject.getAnnotationEntries();
                        Intrinsics.checkNotNullExpressionValue(annotationEntries, "getAnnotationEntries(...)");
                        Iterator<T> it2 = annotationEntries.iterator();
                        do {
                            if (!it2.hasNext()) {
                                next = null;
                                break;
                            }
                            next = it2.next();
                        } while (!Intrinsics.areEqual(((KtAnnotationEntry) next).getShortName(), JvmNames.JVM_RECORD_ANNOTATION_FQ_NAME.shortName()));
                        KtAnnotationEntry ktAnnotationEntry = (KtAnnotationEntry) next;
                        KtAnnotationEntry ktAnnotationEntry2 = ktAnnotationEntry != null ? ktAnnotationEntry : (KtElementImplStub) declaration;
                        if (DescriptorUtilsKt.resolveTopLevelClass(context.getModuleDescriptor(), JdkClassesKt.getJAVA_LANG_RECORD_FQ_NAME(), NoLookupLocation.FOR_DEFAULT_IMPORTS) == null) {
                            context.getTrace().report(ErrorsJvm.JVM_RECORD_REQUIRES_JDK15.on(ktAnnotationEntry2));
                            return;
                        }
                        if (context.getLanguageVersionSettings().supportsFeature(LanguageFeature.JvmRecordSupport)) {
                            if (!JvmRecordApplicabilityCheckerKt.areRecordsAllowed(this.jvmTarget, ((Boolean) context.getLanguageVersionSettings().getFlag(JvmAnalysisFlags.getEnableJvmPreview())).booleanValue())) {
                                context.getTrace().report(ErrorsJvm.JVM_RECORDS_ILLEGAL_BYTECODE_TARGET.on(ktAnnotationEntry2));
                                return;
                            }
                            if (classDescriptor.getClassKind() == ClassKind.ENUM_CLASS) {
                                KtModifierList modifierList = ktClassOrObject.getModifierList();
                                if ((modifierList == null || (nameIdentifier3 = modifierList.getModifier(KtTokens.ENUM_KEYWORD)) == null) && (nameIdentifier3 = ktClassOrObject.mo7392getNameIdentifier()) == null) {
                                    nameIdentifier3 = declaration;
                                }
                                context.getTrace().report(ErrorsJvm.ENUM_JVM_RECORD.on(nameIdentifier3));
                                return;
                            }
                            if (!ModalityUtilsKt.isFinalClass(classDescriptor)) {
                                KtModifierList modifierList2 = ktClassOrObject.getModifierList();
                                if (modifierList2 != null) {
                                    KtModifierKeywordToken ABSTRACT_KEYWORD = KtTokens.ABSTRACT_KEYWORD;
                                    Intrinsics.checkNotNullExpressionValue(ABSTRACT_KEYWORD, "ABSTRACT_KEYWORD");
                                    KtModifierKeywordToken OPEN_KEYWORD = KtTokens.OPEN_KEYWORD;
                                    Intrinsics.checkNotNullExpressionValue(OPEN_KEYWORD, "OPEN_KEYWORD");
                                    KtModifierKeywordToken SEALED_KEYWORD = KtTokens.SEALED_KEYWORD;
                                    Intrinsics.checkNotNullExpressionValue(SEALED_KEYWORD, "SEALED_KEYWORD");
                                    nameIdentifier2 = JvmRecordApplicabilityCheckerKt.findOneOfModifiers(modifierList2, ABSTRACT_KEYWORD, OPEN_KEYWORD, SEALED_KEYWORD);
                                    if (nameIdentifier2 == null) {
                                        nameIdentifier2 = ktClassOrObject.mo7392getNameIdentifier();
                                        if (nameIdentifier2 == null) {
                                            nameIdentifier2 = declaration;
                                        }
                                    }
                                } else {
                                    nameIdentifier2 = ktClassOrObject.mo7392getNameIdentifier();
                                    if (nameIdentifier2 == null) {
                                        nameIdentifier2 = declaration;
                                    }
                                }
                                context.getTrace().report(ErrorsJvm.NON_FINAL_JVM_RECORD.on(nameIdentifier2));
                                return;
                            }
                            if (classDescriptor.getIsInner()) {
                                KtModifierList modifierList3 = ktClassOrObject.getModifierList();
                                if ((modifierList3 == null || (nameIdentifier = modifierList3.getModifier(KtTokens.INNER_KEYWORD)) == null) && (nameIdentifier = ktClassOrObject.mo7392getNameIdentifier()) == null) {
                                    nameIdentifier = declaration;
                                }
                                context.getTrace().report(ErrorsJvm.INNER_JVM_RECORD.on(nameIdentifier));
                                return;
                            }
                            if (DescriptorUtils.isLocal(descriptor)) {
                                context.getTrace().report(ErrorsJvm.LOCAL_JVM_RECORD.on(ktAnnotationEntry2));
                                return;
                            }
                            for (KtDeclaration ktDeclaration : ktClassOrObject.getDeclarations()) {
                                if (ktDeclaration instanceof KtProperty) {
                                    Object obj = context.getTrace().get(BindingContext.DECLARATION_TO_DESCRIPTOR, ktDeclaration);
                                    PropertyDescriptor propertyDescriptor = obj instanceof PropertyDescriptor ? (PropertyDescriptor) obj : null;
                                    if (propertyDescriptor != null && (Intrinsics.areEqual(context.getTrace().getContext().get(BindingContext.BACKING_FIELD_REQUIRED, propertyDescriptor), (Object) true) || ((KtProperty) ktDeclaration).getDelegate() != null)) {
                                        context.getTrace().report(ErrorsJvm.FIELD_IN_JVM_RECORD.on(ktDeclaration));
                                        return;
                                    }
                                }
                            }
                            for (KtSuperTypeListEntry ktSuperTypeListEntry : ktClassOrObject.getSuperTypeListEntries()) {
                                if (ktSuperTypeListEntry instanceof KtDelegatedSuperTypeEntry) {
                                    context.getTrace().report(ErrorsJvm.DELEGATION_BY_IN_JVM_RECORD.on(ktSuperTypeListEntry));
                                    return;
                                }
                            }
                            for (KotlinType kotlinType2 : classDescriptor.getTypeConstructor().mo7438getSupertypes()) {
                                ClassifierDescriptor this$1 = kotlinType2.getConstructor().getThis$0();
                                ClassDescriptor classDescriptor2 = this$1 instanceof ClassDescriptor ? (ClassDescriptor) this$1 : null;
                                if (classDescriptor2 != null && classDescriptor2.getClassKind() != ClassKind.INTERFACE && !Intrinsics.areEqual(DescriptorUtilsKt.getFqNameSafe(classDescriptor2), JdkClassesKt.getJAVA_LANG_RECORD_FQ_NAME())) {
                                    KtDeclaration nameIdentifier4 = ktClassOrObject.mo7392getNameIdentifier();
                                    if (nameIdentifier4 == null) {
                                        nameIdentifier4 = declaration;
                                    }
                                    context.getTrace().report(ErrorsJvm.JVM_RECORD_EXTENDS_CLASS.on(nameIdentifier4, kotlinType2));
                                    return;
                                }
                            }
                            if (!classDescriptor.isData()) {
                                context.getTrace().report(ErrorsJvm.NON_DATA_CLASS_JVM_RECORD.on(ktAnnotationEntry2));
                                return;
                            }
                            KtPrimaryConstructor primaryConstructor = ktClassOrObject.getPrimaryConstructor();
                            if (primaryConstructor == null || (listEmptyList = primaryConstructor.getValueParameters()) == null) {
                                listEmptyList = CollectionsKt.emptyList();
                            }
                            if (listEmptyList.isEmpty()) {
                                KtParameterList nameIdentifier5 = (primaryConstructor == null || (valueParameterList = primaryConstructor.getValueParameterList()) == null) ? ktClassOrObject.mo7392getNameIdentifier() : valueParameterList;
                                if (nameIdentifier5 != null) {
                                    context.getTrace().report(ErrorsJvm.JVM_RECORD_WITHOUT_PRIMARY_CONSTRUCTOR_PARAMETERS.on(nameIdentifier5));
                                    return;
                                }
                            }
                            for (KtParameter ktParameter : listEmptyList) {
                                if (!ktParameter.hasValOrVar() || ktParameter.isMutable()) {
                                    context.getTrace().report(ErrorsJvm.JVM_RECORD_NOT_VAL_PARAMETER.on(ktParameter));
                                    return;
                                }
                            }
                            for (KtParameter ktParameter2 : CollectionsKt.dropLast(listEmptyList, 1)) {
                                if (ktParameter2.isVarArg()) {
                                    context.getTrace().report(ErrorsJvm.JVM_RECORD_NOT_LAST_VARARG_PARAMETER.on(ktParameter2));
                                    return;
                                }
                            }
                            return;
                        }
                        context.getTrace().report(Errors.UNSUPPORTED_FEATURE.on(ktAnnotationEntry2, TuplesKt.to(LanguageFeature.JvmRecordSupport, context.getLanguageVersionSettings())));
                        return;
                    }
                    return;
                }
            } while (!Intrinsics.areEqual(fqNameFqNameOrNull, JdkClassesKt.getJAVA_LANG_RECORD_FQ_NAME()));
            context.getTrace().report(ErrorsJvm.ILLEGAL_JAVA_LANG_RECORD_SUPERTYPE.on(next2));
        }
    }
}
