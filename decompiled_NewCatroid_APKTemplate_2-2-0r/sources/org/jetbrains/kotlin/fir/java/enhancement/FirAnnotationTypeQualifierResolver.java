package org.jetbrains.kotlin.fir.java.enhancement;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.antlr.stringtemplate.language.ASTExpr;
import org.eclipse.jdt.internal.compiler.util.SuffixConstants;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.FirSessionComponent;
import org.jetbrains.kotlin.fir.declarations.FirClassLikeDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirRegularClass;
import org.jetbrains.kotlin.fir.expressions.FirAnnotation;
import org.jetbrains.kotlin.fir.expressions.FirArrayLiteral;
import org.jetbrains.kotlin.fir.expressions.FirCall;
import org.jetbrains.kotlin.fir.expressions.FirExpression;
import org.jetbrains.kotlin.fir.expressions.FirExpressionUtilKt;
import org.jetbrains.kotlin.fir.expressions.FirNamedArgumentExpression;
import org.jetbrains.kotlin.fir.expressions.FirVarargArgumentsExpression;
import org.jetbrains.kotlin.fir.java.JavaAnnotationsMappingKt;
import org.jetbrains.kotlin.fir.java.declarations.FirJavaClass;
import org.jetbrains.kotlin.fir.references.FirFromMissingDependenciesNamedReference;
import org.jetbrains.kotlin.fir.references.FirReference;
import org.jetbrains.kotlin.fir.references.FirResolvedNamedReference;
import org.jetbrains.kotlin.fir.resolve.LookupTagUtilsKt;
import org.jetbrains.kotlin.fir.resolve.providers.FirSymbolProviderKt;
import org.jetbrains.kotlin.fir.symbols.ConeClassLikeLookupTag;
import org.jetbrains.kotlin.fir.symbols.FirBasedSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirCallableSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirClassLikeSymbol;
import org.jetbrains.kotlin.fir.types.ConeClassLikeType;
import org.jetbrains.kotlin.fir.types.ConeKotlinType;
import org.jetbrains.kotlin.fir.types.FirResolvedTypeRef;
import org.jetbrains.kotlin.fir.types.FirTypeRef;
import org.jetbrains.kotlin.load.java.AbstractAnnotationTypeQualifierResolver;
import org.jetbrains.kotlin.load.java.JavaModuleAnnotationsProvider;
import org.jetbrains.kotlin.load.java.JavaTypeEnhancementState;
import org.jetbrains.kotlin.load.java.JavaTypeQualifiersByElementType;
import org.jetbrains.kotlin.load.java.JvmAnnotationNames;
import org.jetbrains.kotlin.load.java.structure.JavaAnnotation;
import org.jetbrains.kotlin.load.java.structure.JavaPackage;
import org.jetbrains.kotlin.name.CallableId;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.Name;

/* JADX INFO: compiled from: FirAnnotationTypeQualifierResolver.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000`\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\u001c\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u00012\u00020\u0003B\u001d\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t¢\u0006\u0002\u0010\nJ\u0010\u0010\u0017\u001a\u0004\u0018\u00010\u00182\u0006\u0010\u0019\u001a\u00020\u001aJ\u001a\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\u001c0\u0014*\u00020\u00022\u0006\u0010\u001d\u001a\u00020\u001eH\u0014J\u0012\u0010\u001f\u001a\b\u0012\u0004\u0012\u00020\u001c0 *\u00020!H\u0002R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\u000b\u001a\u0004\u0018\u00010\f*\u00020\u00028TX\u0094\u0004¢\u0006\u0006\u001a\u0004\b\r\u0010\u000eR\u0018\u0010\u000f\u001a\u00020\u0010*\u00020\u00028TX\u0094\u0004¢\u0006\u0006\u001a\u0004\b\u0011\u0010\u0012R\u001e\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00020\u0014*\u00020\u00028TX\u0094\u0004¢\u0006\u0006\u001a\u0004\b\u0015\u0010\u0016¨\u0006\""}, d2 = {"Lorg/jetbrains/kotlin/fir/java/enhancement/FirAnnotationTypeQualifierResolver;", "Lorg/jetbrains/kotlin/load/java/AbstractAnnotationTypeQualifierResolver;", "Lorg/jetbrains/kotlin/fir/expressions/FirAnnotation;", "Lorg/jetbrains/kotlin/fir/FirSessionComponent;", "session", "Lorg/jetbrains/kotlin/fir/FirSession;", "javaTypeEnhancementState", "Lorg/jetbrains/kotlin/load/java/JavaTypeEnhancementState;", "javaModuleAnnotationsProvider", "Lorg/jetbrains/kotlin/load/java/JavaModuleAnnotationsProvider;", "(Lorg/jetbrains/kotlin/fir/FirSession;Lorg/jetbrains/kotlin/load/java/JavaTypeEnhancementState;Lorg/jetbrains/kotlin/load/java/JavaModuleAnnotationsProvider;)V", "fqName", "Lorg/jetbrains/kotlin/name/FqName;", "getFqName", "(Lorg/jetbrains/kotlin/fir/expressions/FirAnnotation;)Lorg/jetbrains/kotlin/name/FqName;", ASTExpr.DEFAULT_MAP_KEY_NAME, "", "getKey", "(Lorg/jetbrains/kotlin/fir/expressions/FirAnnotation;)Ljava/lang/Object;", "metaAnnotations", "", "getMetaAnnotations", "(Lorg/jetbrains/kotlin/fir/expressions/FirAnnotation;)Ljava/lang/Iterable;", "extractDefaultQualifiers", "Lorg/jetbrains/kotlin/load/java/JavaTypeQualifiersByElementType;", "firClass", "Lorg/jetbrains/kotlin/fir/declarations/FirRegularClass;", "enumArguments", "", "onlyValue", "", "toEnumNames", "", "Lorg/jetbrains/kotlin/fir/expressions/FirExpression;", SuffixConstants.EXTENSION_java}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirAnnotationTypeQualifierResolver extends AbstractAnnotationTypeQualifierResolver<FirAnnotation> implements FirSessionComponent {
    private final JavaModuleAnnotationsProvider javaModuleAnnotationsProvider;
    private final FirSession session;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public FirAnnotationTypeQualifierResolver(FirSession session, JavaTypeEnhancementState javaTypeEnhancementState, JavaModuleAnnotationsProvider javaModuleAnnotationsProvider) {
        super(javaTypeEnhancementState);
        Intrinsics.checkNotNullParameter(session, "session");
        Intrinsics.checkNotNullParameter(javaTypeEnhancementState, "javaTypeEnhancementState");
        Intrinsics.checkNotNullParameter(javaModuleAnnotationsProvider, "javaModuleAnnotationsProvider");
        this.session = session;
        this.javaModuleAnnotationsProvider = javaModuleAnnotationsProvider;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.jetbrains.kotlin.load.java.AbstractAnnotationTypeQualifierResolver
    public Iterable<String> enumArguments(FirAnnotation firAnnotation, boolean z) {
        List<String> enumNames;
        Intrinsics.checkNotNullParameter(firAnnotation, "<this>");
        Collection<FirExpression> collectionValues = firAnnotation.getArgumentMapping().getMapping().values();
        ArrayList arrayList = new ArrayList();
        for (FirExpression firExpression : collectionValues) {
            if (!z || !(firExpression instanceof FirNamedArgumentExpression) || Intrinsics.areEqual(((FirNamedArgumentExpression) firExpression).getName(), JvmAnnotationNames.DEFAULT_ANNOTATION_MEMBER_NAME)) {
                enumNames = toEnumNames(firExpression);
            } else {
                enumNames = CollectionsKt.emptyList();
            }
            CollectionsKt.addAll(arrayList, enumNames);
        }
        return arrayList;
    }

    /* JADX WARN: Multi-variable type inference failed */
    private final List<String> toEnumNames(FirExpression firExpression) {
        CallableId callableId;
        Name callableName;
        if (!(firExpression instanceof FirArrayLiteral)) {
            if (!(firExpression instanceof FirVarargArgumentsExpression)) {
                FirReference reference = FirExpressionUtilKt.toReference(firExpression);
                String strAsString = null;
                if (reference instanceof FirResolvedNamedReference) {
                    FirBasedSymbol<?> resolvedSymbol = ((FirResolvedNamedReference) reference).getResolvedSymbol();
                    FirCallableSymbol firCallableSymbol = resolvedSymbol instanceof FirCallableSymbol ? (FirCallableSymbol) resolvedSymbol : null;
                    if (firCallableSymbol != null && (callableId = firCallableSymbol.getCallableId()) != null && (callableName = callableId.getCallableName()) != null) {
                        strAsString = callableName.asString();
                    }
                } else if (reference instanceof FirFromMissingDependenciesNamedReference) {
                    strAsString = ((FirFromMissingDependenciesNamedReference) reference).getName().asString();
                }
                return CollectionsKt.listOfNotNull(strAsString);
            }
            List<FirExpression> arguments = ((FirVarargArgumentsExpression) firExpression).getArguments();
            ArrayList arrayList = new ArrayList();
            Iterator<T> it = arguments.iterator();
            while (it.hasNext()) {
                CollectionsKt.addAll(arrayList, toEnumNames((FirExpression) it.next()));
            }
            return arrayList;
        }
        List<FirExpression> arguments2 = ((FirCall) firExpression).getArgumentList().getArguments();
        ArrayList arrayList2 = new ArrayList();
        Iterator<T> it2 = arguments2.iterator();
        while (it2.hasNext()) {
            CollectionsKt.addAll(arrayList2, toEnumNames((FirExpression) it2.next()));
        }
        return arrayList2;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final JavaTypeQualifiersByElementType extractDefaultQualifiers(FirRegularClass firClass) {
        JavaPackage javaPackage;
        Intrinsics.checkNotNullParameter(firClass, "firClass");
        ClassId classId = firClass.getSymbol().getClassId();
        ClassId outerClassId = classId.getOuterClassId();
        JavaTypeQualifiersByElementType javaTypeQualifiersByElementTypeExtractAndMergeDefaultQualifiers = null;
        if (outerClassId != null) {
            FirClassLikeSymbol<?> classLikeSymbolByClassId = FirSymbolProviderKt.getSymbolProvider(this.session).getClassLikeSymbolByClassId(outerClassId);
            FirClassLikeDeclaration firClassLikeDeclaration = classLikeSymbolByClassId != null ? (FirClassLikeDeclaration) classLikeSymbolByClassId.getFir() : null;
            FirRegularClass firRegularClass = firClassLikeDeclaration instanceof FirRegularClass ? (FirRegularClass) firClassLikeDeclaration : null;
            if (firRegularClass != null) {
                javaTypeQualifiersByElementTypeExtractAndMergeDefaultQualifiers = extractDefaultQualifiers(firRegularClass);
            }
        } else {
            List<JavaAnnotation> annotationsForModuleOwnerOfClass = this.javaModuleAnnotationsProvider.getAnnotationsForModuleOwnerOfClass(classId);
            JavaTypeQualifiersByElementType javaTypeQualifiersByElementTypeExtractAndMergeDefaultQualifiers2 = annotationsForModuleOwnerOfClass != null ? extractAndMergeDefaultQualifiers(null, JavaAnnotationsMappingKt.convertAnnotationsToFir(annotationsForModuleOwnerOfClass, this.session)) : null;
            FirJavaClass firJavaClass = firClass instanceof FirJavaClass ? (FirJavaClass) firClass : null;
            if (firJavaClass != null && (javaPackage = firJavaClass.getJavaPackage()) != null) {
                javaTypeQualifiersByElementTypeExtractAndMergeDefaultQualifiers = extractAndMergeDefaultQualifiers(javaTypeQualifiersByElementTypeExtractAndMergeDefaultQualifiers2, JavaAnnotationsMappingKt.convertAnnotationsToFir(javaPackage, this.session));
            }
            if (javaTypeQualifiersByElementTypeExtractAndMergeDefaultQualifiers == null) {
                javaTypeQualifiersByElementTypeExtractAndMergeDefaultQualifiers = javaTypeQualifiersByElementTypeExtractAndMergeDefaultQualifiers2;
            }
        }
        return extractAndMergeDefaultQualifiers(javaTypeQualifiersByElementTypeExtractAndMergeDefaultQualifiers, firClass.getAnnotations());
    }

    /* JADX INFO: Access modifiers changed from: protected */
    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.jetbrains.kotlin.load.java.AbstractAnnotationTypeQualifierResolver
    public Iterable<FirAnnotation> getMetaAnnotations(FirAnnotation firAnnotation) {
        ConeClassLikeLookupTag lookupTag;
        FirClassLikeSymbol<?> symbol;
        FirClassLikeDeclaration firClassLikeDeclaration;
        Intrinsics.checkNotNullParameter(firAnnotation, "<this>");
        FirTypeRef annotationTypeRef = firAnnotation.getAnnotationTypeRef();
        List<FirAnnotation> listEmptyList = null;
        FirResolvedTypeRef firResolvedTypeRef = annotationTypeRef instanceof FirResolvedTypeRef ? (FirResolvedTypeRef) annotationTypeRef : null;
        ConeKotlinType type = firResolvedTypeRef != null ? firResolvedTypeRef.getType() : null;
        ConeClassLikeType coneClassLikeType = type instanceof ConeClassLikeType ? (ConeClassLikeType) type : null;
        if (coneClassLikeType != null && (lookupTag = coneClassLikeType.getLookupTag()) != null && (symbol = LookupTagUtilsKt.toSymbol(lookupTag, this.session)) != null && (firClassLikeDeclaration = (FirClassLikeDeclaration) symbol.getFir()) != null) {
            listEmptyList = firClassLikeDeclaration.getAnnotations();
        }
        if (listEmptyList == null) {
            listEmptyList = CollectionsKt.emptyList();
        }
        return listEmptyList;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.jetbrains.kotlin.load.java.AbstractAnnotationTypeQualifierResolver
    public Object getKey(FirAnnotation firAnnotation) {
        Intrinsics.checkNotNullParameter(firAnnotation, "<this>");
        FirTypeRef annotationTypeRef = firAnnotation.getAnnotationTypeRef();
        FirResolvedTypeRef firResolvedTypeRef = annotationTypeRef instanceof FirResolvedTypeRef ? (FirResolvedTypeRef) annotationTypeRef : null;
        ConeKotlinType type = firResolvedTypeRef != null ? firResolvedTypeRef.getType() : null;
        ConeClassLikeType coneClassLikeType = type instanceof ConeClassLikeType ? (ConeClassLikeType) type : null;
        Intrinsics.checkNotNull(coneClassLikeType);
        return coneClassLikeType.getLookupTag();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.jetbrains.kotlin.load.java.AbstractAnnotationTypeQualifierResolver
    public FqName getFqName(FirAnnotation firAnnotation) {
        ConeClassLikeLookupTag lookupTag;
        ClassId classId;
        Intrinsics.checkNotNullParameter(firAnnotation, "<this>");
        FirTypeRef annotationTypeRef = firAnnotation.getAnnotationTypeRef();
        FirResolvedTypeRef firResolvedTypeRef = annotationTypeRef instanceof FirResolvedTypeRef ? (FirResolvedTypeRef) annotationTypeRef : null;
        ConeKotlinType type = firResolvedTypeRef != null ? firResolvedTypeRef.getType() : null;
        ConeClassLikeType coneClassLikeType = type instanceof ConeClassLikeType ? (ConeClassLikeType) type : null;
        if (coneClassLikeType == null || (lookupTag = coneClassLikeType.getLookupTag()) == null || (classId = lookupTag.getClassId()) == null) {
            return null;
        }
        return classId.asSingleFqName();
    }
}
