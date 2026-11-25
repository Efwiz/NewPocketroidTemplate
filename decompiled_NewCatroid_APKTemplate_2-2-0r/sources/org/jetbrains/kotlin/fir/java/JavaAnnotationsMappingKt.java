package org.jetbrains.kotlin.fir.java;

import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.ArrayList;
import java.util.Collection;
import java.util.EnumSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.annotation.AnnotationRetention;
import kotlin.annotation.AnnotationTarget;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jdt.internal.compiler.util.SuffixConstants;
import org.jetbrains.kotlin.KtFakeSourceElement;
import org.jetbrains.kotlin.KtFakeSourceElementKind;
import org.jetbrains.kotlin.KtRealPsiSourceElement;
import org.jetbrains.kotlin.KtRealSourceElementKind;
import org.jetbrains.kotlin.KtSourceElement;
import org.jetbrains.kotlin.KtSourceElementKind;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.fir.FirAnnotationContainer;
import org.jetbrains.kotlin.fir.FirElement;
import org.jetbrains.kotlin.fir.FirModuleData;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.declarations.FirClassLikeDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirConstructor;
import org.jetbrains.kotlin.fir.declarations.FirDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirRegularClass;
import org.jetbrains.kotlin.fir.declarations.FirValueParameter;
import org.jetbrains.kotlin.fir.diagnostics.ConeSimpleDiagnostic;
import org.jetbrains.kotlin.fir.diagnostics.DiagnosticKind;
import org.jetbrains.kotlin.fir.expressions.FirAnnotation;
import org.jetbrains.kotlin.fir.expressions.FirAnnotationArgumentMapping;
import org.jetbrains.kotlin.fir.expressions.FirArgumentUtilKt;
import org.jetbrains.kotlin.fir.expressions.FirExpression;
import org.jetbrains.kotlin.fir.expressions.FirPropertyAccessExpression;
import org.jetbrains.kotlin.fir.expressions.builder.FirAnnotationBuilder;
import org.jetbrains.kotlin.fir.expressions.builder.FirArgumentListBuilder;
import org.jetbrains.kotlin.fir.expressions.builder.FirArrayLiteralBuilder;
import org.jetbrains.kotlin.fir.expressions.builder.FirClassReferenceExpressionBuilder;
import org.jetbrains.kotlin.fir.expressions.builder.FirErrorExpressionBuilder;
import org.jetbrains.kotlin.fir.expressions.builder.FirGetClassCallBuilder;
import org.jetbrains.kotlin.fir.expressions.builder.FirPropertyAccessExpressionBuilder;
import org.jetbrains.kotlin.fir.expressions.builder.FirVarargArgumentsExpressionBuilder;
import org.jetbrains.kotlin.fir.java.declarations.FirJavaValueParameterBuilder;
import org.jetbrains.kotlin.fir.references.FirErrorNamedReference;
import org.jetbrains.kotlin.fir.references.FirFromMissingDependenciesNamedReference;
import org.jetbrains.kotlin.fir.references.FirResolvedNamedReference;
import org.jetbrains.kotlin.fir.references.builder.FirErrorNamedReferenceBuilder;
import org.jetbrains.kotlin.fir.references.builder.FirFromMissingDependenciesNamedReferenceBuilder;
import org.jetbrains.kotlin.fir.references.builder.FirResolvedNamedReferenceBuilder;
import org.jetbrains.kotlin.fir.resolve.LookupTagUtilsKt;
import org.jetbrains.kotlin.fir.resolve.diagnostics.ConeUnresolvedReferenceError;
import org.jetbrains.kotlin.fir.resolve.providers.FirSymbolProviderKt;
import org.jetbrains.kotlin.fir.symbols.ConeClassLikeLookupTag;
import org.jetbrains.kotlin.fir.symbols.impl.ConeClassLikeLookupTagImpl;
import org.jetbrains.kotlin.fir.symbols.impl.FirClassLikeSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirFunctionSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirVariableSymbol;
import org.jetbrains.kotlin.fir.types.ArrayUtilsKt;
import org.jetbrains.kotlin.fir.types.ConeAttributes;
import org.jetbrains.kotlin.fir.types.ConeErrorType;
import org.jetbrains.kotlin.fir.types.ConeKotlinType;
import org.jetbrains.kotlin.fir.types.ConeSimpleKotlinType;
import org.jetbrains.kotlin.fir.types.ConeTypeProjection;
import org.jetbrains.kotlin.fir.types.ConeTypeUtilsKt;
import org.jetbrains.kotlin.fir.types.FirErrorTypeRef;
import org.jetbrains.kotlin.fir.types.FirResolvedTypeRef;
import org.jetbrains.kotlin.fir.types.FirTypeRef;
import org.jetbrains.kotlin.fir.types.TypeConstructionUtilsKt;
import org.jetbrains.kotlin.fir.types.builder.FirErrorTypeRefBuilder;
import org.jetbrains.kotlin.fir.types.builder.FirResolvedTypeRefBuilder;
import org.jetbrains.kotlin.fir.types.impl.ConeClassLikeTypeImpl;
import org.jetbrains.kotlin.fir.types.jvm.FirJavaTypeRef;
import org.jetbrains.kotlin.fir.visitors.FirTransformer;
import org.jetbrains.kotlin.fir.visitors.FirVisitor;
import org.jetbrains.kotlin.load.java.structure.JavaAnnotation;
import org.jetbrains.kotlin.load.java.structure.JavaAnnotationArgument;
import org.jetbrains.kotlin.load.java.structure.JavaAnnotationAsAnnotationArgument;
import org.jetbrains.kotlin.load.java.structure.JavaAnnotationOwner;
import org.jetbrains.kotlin.load.java.structure.JavaArrayAnnotationArgument;
import org.jetbrains.kotlin.load.java.structure.JavaClassObjectAnnotationArgument;
import org.jetbrains.kotlin.load.java.structure.JavaEnumValueAnnotationArgument;
import org.jetbrains.kotlin.load.java.structure.JavaLiteralAnnotationArgument;
import org.jetbrains.kotlin.load.java.structure.JavaValueParameter;
import org.jetbrains.kotlin.load.java.structure.impl.JavaElementImpl;
import org.jetbrains.kotlin.metadata.jvm.deserialization.JvmProtoBufUtil;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.name.SpecialNames;
import org.jetbrains.kotlin.name.StandardClassIds;

/* JADX INFO: compiled from: javaAnnotationsMapping.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u009c\u0001\n\u0000\n\u0002\u0010$\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010%\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\u0010\u001c\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\u001a$\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000b2\b\u0010\f\u001a\u0004\u0018\u00010\r2\b\u0010\u000e\u001a\u0004\u0018\u00010\u000fH\u0002\u001a:\u0010\u0010\u001a\u00020\u00112\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\u0012\u001a\u00020\u00132\f\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00160\u00152\u0012\u0010\u0017\u001a\u000e\u0012\u0004\u0012\u00020\u000f\u0012\u0004\u0012\u00020\u00190\u0018H\u0002\u001a \u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\u001c0\u001b*\b\u0012\u0004\u0012\u00020\u001e0\u001d2\u0006\u0010\n\u001a\u00020\u000bH\u0000\u001a\u001a\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\u001c0\u001b*\u00020\u001f2\u0006\u0010\n\u001a\u00020\u000bH\u0000\u001a\u0016\u0010 \u001a\u0004\u0018\u00010\u0019*\u00020\u00162\u0006\u0010\n\u001a\u00020\u000bH\u0002\u001a\u001c\u0010!\u001a\u0004\u0018\u00010\u0019*\b\u0012\u0004\u0012\u00020\u00160\u001b2\u0006\u0010\n\u001a\u00020\u000bH\u0002\u001a\u001c\u0010\"\u001a\u00020\u0011*\u00020#2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010$\u001a\u00020\u001fH\u0000\u001a\u0014\u0010%\u001a\u00020\u001c*\u00020\u001e2\u0006\u0010\n\u001a\u00020\u000bH\u0002\u001a&\u0010&\u001a\u00020\u0019*\u00020\u00162\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010'\u001a\u00020(2\b\u0010)\u001a\u0004\u0018\u00010*H\u0000\u001a0\u0010+\u001a\u00020,*\u00020-2\u0006\u0010\n\u001a\u00020\u000b2\n\u0010.\u001a\u0006\u0012\u0002\b\u00030/2\u0006\u00100\u001a\u0002012\u0006\u00102\u001a\u000203H\u0000\"\u001a\u0010\u0000\u001a\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00030\u0001X\u0082\u0004¢\u0006\u0002\n\u0000\">\u0010\u0004\u001a2\u0012\u0004\u0012\u00020\u0002\u0012(\u0012&\u0012\f\u0012\n \u0007*\u0004\u0018\u00010\u00060\u0006 \u0007*\u0012\u0012\f\u0012\n \u0007*\u0004\u0018\u00010\u00060\u0006\u0018\u00010\u00050\u00050\u0001X\u0082\u0004¢\u0006\u0002\n\u0000¨\u00064"}, d2 = {"JAVA_RETENTION_TO_KOTLIN", "", "", "Lkotlin/annotation/AnnotationRetention;", "JAVA_TARGETS_TO_KOTLIN", "Ljava/util/EnumSet;", "Lkotlin/annotation/AnnotationTarget;", JvmProtoBufUtil.PLATFORM_TYPE_ID, "buildEnumCall", "Lorg/jetbrains/kotlin/fir/expressions/FirPropertyAccessExpression;", "session", "Lorg/jetbrains/kotlin/fir/FirSession;", "classId", "Lorg/jetbrains/kotlin/name/ClassId;", "entryName", "Lorg/jetbrains/kotlin/name/Name;", "fillAnnotationArgumentMapping", "", "lookupTag", "Lorg/jetbrains/kotlin/fir/symbols/impl/ConeClassLikeLookupTagImpl;", "annotationArguments", "", "Lorg/jetbrains/kotlin/load/java/structure/JavaAnnotationArgument;", FirebaseAnalytics.Param.DESTINATION, "", "Lorg/jetbrains/kotlin/fir/expressions/FirExpression;", "convertAnnotationsToFir", "", "Lorg/jetbrains/kotlin/fir/expressions/FirAnnotation;", "", "Lorg/jetbrains/kotlin/load/java/structure/JavaAnnotation;", "Lorg/jetbrains/kotlin/load/java/structure/JavaAnnotationOwner;", "mapJavaRetentionArgument", "mapJavaTargetArguments", "setAnnotationsFromJava", "Lorg/jetbrains/kotlin/fir/FirAnnotationContainer;", "javaAnnotationOwner", "toFirAnnotationCall", "toFirExpression", "javaTypeParameterStack", "Lorg/jetbrains/kotlin/fir/java/JavaTypeParameterStack;", "expectedTypeRef", "Lorg/jetbrains/kotlin/fir/types/FirTypeRef;", "toFirValueParameter", "Lorg/jetbrains/kotlin/fir/declarations/FirValueParameter;", "Lorg/jetbrains/kotlin/load/java/structure/JavaValueParameter;", "functionSymbol", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirFunctionSymbol;", "moduleData", "Lorg/jetbrains/kotlin/fir/FirModuleData;", "index", "", SuffixConstants.EXTENSION_java}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class JavaAnnotationsMappingKt {
    private static final Map<String, AnnotationRetention> JAVA_RETENTION_TO_KOTLIN = MapsKt.mapOf(TuplesKt.to("RUNTIME", AnnotationRetention.RUNTIME), TuplesKt.to("CLASS", AnnotationRetention.BINARY), TuplesKt.to("SOURCE", AnnotationRetention.SOURCE));
    private static final Map<String, EnumSet<AnnotationTarget>> JAVA_TARGETS_TO_KOTLIN = MapsKt.mapOf(TuplesKt.to("TYPE", EnumSet.of(AnnotationTarget.CLASS, AnnotationTarget.FILE)), TuplesKt.to("ANNOTATION_TYPE", EnumSet.of(AnnotationTarget.ANNOTATION_CLASS)), TuplesKt.to("TYPE_PARAMETER", EnumSet.of(AnnotationTarget.TYPE_PARAMETER)), TuplesKt.to("FIELD", EnumSet.of(AnnotationTarget.FIELD)), TuplesKt.to("LOCAL_VARIABLE", EnumSet.of(AnnotationTarget.LOCAL_VARIABLE)), TuplesKt.to("PARAMETER", EnumSet.of(AnnotationTarget.VALUE_PARAMETER)), TuplesKt.to("CONSTRUCTOR", EnumSet.of(AnnotationTarget.CONSTRUCTOR)), TuplesKt.to("METHOD", EnumSet.of(AnnotationTarget.FUNCTION, AnnotationTarget.PROPERTY_GETTER, AnnotationTarget.PROPERTY_SETTER)), TuplesKt.to("TYPE_USE", EnumSet.of(AnnotationTarget.TYPE)));

    public static final List<FirAnnotation> convertAnnotationsToFir(JavaAnnotationOwner javaAnnotationOwner, FirSession session) {
        Intrinsics.checkNotNullParameter(javaAnnotationOwner, "<this>");
        Intrinsics.checkNotNullParameter(session, "session");
        return convertAnnotationsToFir(javaAnnotationOwner.mo7252getAnnotations(), session);
    }

    public static final void setAnnotationsFromJava(FirAnnotationContainer firAnnotationContainer, FirSession session, JavaAnnotationOwner javaAnnotationOwner) {
        Intrinsics.checkNotNullParameter(firAnnotationContainer, "<this>");
        Intrinsics.checkNotNullParameter(session, "session");
        Intrinsics.checkNotNullParameter(javaAnnotationOwner, "javaAnnotationOwner");
        ArrayList arrayList = new ArrayList();
        Iterator<T> it = javaAnnotationOwner.mo7252getAnnotations().iterator();
        while (it.hasNext()) {
            arrayList.add(toFirAnnotationCall((JavaAnnotation) it.next(), session));
        }
        firAnnotationContainer.replaceAnnotations(arrayList);
    }

    public static final FirExpression toFirExpression(JavaAnnotationArgument javaAnnotationArgument, FirSession session, JavaTypeParameterStack javaTypeParameterStack, FirTypeRef firTypeRef) {
        FirResolvedTypeRef firResolvedTypeRefMo6810build;
        ConeErrorType coneErrorType;
        ConeSimpleKotlinType coneSimpleKotlinTypeLowerBoundIfFlexible;
        Intrinsics.checkNotNullParameter(javaAnnotationArgument, "<this>");
        Intrinsics.checkNotNullParameter(session, "session");
        Intrinsics.checkNotNullParameter(javaTypeParameterStack, "javaTypeParameterStack");
        FirTypeRef firTypeRefMo6810build = null;
        if (javaAnnotationArgument instanceof JavaLiteralAnnotationArgument) {
            return JavaUtilsKt.createConstantOrError(((JavaLiteralAnnotationArgument) javaAnnotationArgument).getValue(), session, firTypeRef != null ? JavaTypeConversionKt.resolveIfJavaType$default(firTypeRef, session, javaTypeParameterStack, null, 4, null) : null);
        }
        if (!(javaAnnotationArgument instanceof JavaArrayAnnotationArgument)) {
            if (javaAnnotationArgument instanceof JavaEnumValueAnnotationArgument) {
                JavaEnumValueAnnotationArgument javaEnumValueAnnotationArgument = (JavaEnumValueAnnotationArgument) javaAnnotationArgument;
                return buildEnumCall(session, javaEnumValueAnnotationArgument.getEnumClassId(), javaEnumValueAnnotationArgument.getEntryName());
            }
            if (!(javaAnnotationArgument instanceof JavaClassObjectAnnotationArgument)) {
                if (javaAnnotationArgument instanceof JavaAnnotationAsAnnotationArgument) {
                    return toFirAnnotationCall(((JavaAnnotationAsAnnotationArgument) javaAnnotationArgument).getAnnotation(), session);
                }
                FirErrorExpressionBuilder firErrorExpressionBuilder = new FirErrorExpressionBuilder();
                firErrorExpressionBuilder.setDiagnostic(new ConeSimpleDiagnostic("Unknown JavaAnnotationArgument: " + firErrorExpressionBuilder.getClass(), DiagnosticKind.Java));
                return firErrorExpressionBuilder.mo6809build();
            }
            FirGetClassCallBuilder firGetClassCallBuilder = new FirGetClassCallBuilder();
            FirResolvedTypeRef firResolvedTypeRef$default = JavaTypeConversionKt.toFirResolvedTypeRef$default(((JavaClassObjectAnnotationArgument) javaAnnotationArgument).getReferencedType(), session, javaTypeParameterStack, null, 4, null);
            FirResolvedTypeRefBuilder firResolvedTypeRefBuilder = new FirResolvedTypeRefBuilder();
            firResolvedTypeRefBuilder.setType(TypeConstructionUtilsKt.constructClassLikeType$default(StandardClassIds.INSTANCE.getKClass(), new ConeKotlinType[]{firResolvedTypeRef$default.getType()}, false, null, 4, null));
            FirResolvedTypeRef firResolvedTypeRefMo6810build2 = firResolvedTypeRefBuilder.mo6809build();
            FirClassReferenceExpressionBuilder firClassReferenceExpressionBuilder = new FirClassReferenceExpressionBuilder();
            firClassReferenceExpressionBuilder.setClassTypeRef(firResolvedTypeRef$default);
            FirResolvedTypeRef firResolvedTypeRef = firResolvedTypeRefMo6810build2;
            firClassReferenceExpressionBuilder.setTypeRef(firResolvedTypeRef);
            firGetClassCallBuilder.setArgumentList(FirArgumentUtilKt.buildUnaryArgumentList(firClassReferenceExpressionBuilder.mo6809build()));
            firGetClassCallBuilder.setTypeRef(firResolvedTypeRef);
            return firGetClassCallBuilder.mo6809build();
        }
        FirArrayLiteralBuilder firArrayLiteralBuilder = new FirArrayLiteralBuilder();
        if (firTypeRef != null) {
            if (firTypeRef instanceof FirJavaTypeRef) {
                FirResolvedTypeRefBuilder firResolvedTypeRefBuilder2 = new FirResolvedTypeRefBuilder();
                firResolvedTypeRefBuilder2.setType(JavaTypeConversionKt.toConeKotlinTypeProbablyFlexible$default(firTypeRef, session, javaTypeParameterStack, null, 4, null));
                firResolvedTypeRefMo6810build = firResolvedTypeRefBuilder2.mo6809build();
            } else {
                firResolvedTypeRefMo6810build = firTypeRef;
            }
            firArrayLiteralBuilder.setTypeRef(firResolvedTypeRefMo6810build);
            FirResolvedTypeRefBuilder firResolvedTypeRefBuilder3 = new FirResolvedTypeRefBuilder();
            FirResolvedTypeRef firResolvedTypeRef2 = firTypeRef instanceof FirResolvedTypeRef ? (FirResolvedTypeRef) firTypeRef : null;
            ConeKotlinType type = firResolvedTypeRef2 != null ? firResolvedTypeRef2.getType() : null;
            if (!(type instanceof ConeKotlinType)) {
                type = null;
            }
            if (type == null || (coneSimpleKotlinTypeLowerBoundIfFlexible = ConeTypeUtilsKt.lowerBoundIfFlexible(type)) == null || (coneErrorType = ArrayUtilsKt.arrayElementType(coneSimpleKotlinTypeLowerBoundIfFlexible)) == null) {
                coneErrorType = new ConeErrorType(new ConeSimpleDiagnostic("expected type is not array type", null, 2, null), false, null, null, 14, null);
            }
            firResolvedTypeRefBuilder3.setType(coneErrorType);
            firTypeRefMo6810build = firResolvedTypeRefBuilder3.mo6809build();
        }
        FirArgumentListBuilder firArgumentListBuilder = new FirArgumentListBuilder();
        List<JavaAnnotationArgument> elements = ((JavaArrayAnnotationArgument) javaAnnotationArgument).getElements();
        List<FirExpression> arguments = firArgumentListBuilder.getArguments();
        Iterator<T> it = elements.iterator();
        while (it.hasNext()) {
            arguments.add(toFirExpression((JavaAnnotationArgument) it.next(), session, javaTypeParameterStack, firTypeRefMo6810build));
        }
        firArrayLiteralBuilder.setArgumentList(firArgumentListBuilder.build());
        return firArrayLiteralBuilder.mo6809build();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final FirExpression mapJavaRetentionArgument(JavaAnnotationArgument javaAnnotationArgument, FirSession firSession) {
        Name entryName;
        Map<String, AnnotationRetention> map = JAVA_RETENTION_TO_KOTLIN;
        JavaEnumValueAnnotationArgument javaEnumValueAnnotationArgument = javaAnnotationArgument instanceof JavaEnumValueAnnotationArgument ? (JavaEnumValueAnnotationArgument) javaAnnotationArgument : null;
        AnnotationRetention annotationRetention = map.get((javaEnumValueAnnotationArgument == null || (entryName = javaEnumValueAnnotationArgument.getEntryName()) == null) ? null : entryName.asString());
        return annotationRetention != null ? buildEnumCall(firSession, StandardClassIds.INSTANCE.getAnnotationRetention(), Name.identifier(annotationRetention.name())) : null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    public static final void fillAnnotationArgumentMapping(FirSession firSession, ConeClassLikeLookupTagImpl coneClassLikeLookupTagImpl, Collection<? extends JavaAnnotationArgument> collection, Map<Name, FirExpression> map) {
        FirConstructor firConstructor;
        FirValueParameter firValueParameter;
        List<FirValueParameter> valueParameters;
        Object next;
        List<FirDeclaration> declarations;
        Object next2;
        if (collection.isEmpty()) {
            return;
        }
        FirClassLikeSymbol<?> symbol = LookupTagUtilsKt.toSymbol((ConeClassLikeLookupTag) coneClassLikeLookupTagImpl, firSession);
        LookupTagUtilsKt.bindSymbolToLookupTag(coneClassLikeLookupTagImpl, firSession, symbol);
        FirRegularClass firRegularClass = (FirRegularClass) (symbol != null ? (FirClassLikeDeclaration) symbol.getFir() : null);
        if (firRegularClass == null || (declarations = firRegularClass.getDeclarations()) == null) {
            firConstructor = null;
        } else {
            Iterator<T> it = declarations.iterator();
            do {
                if (!it.hasNext()) {
                    next2 = null;
                    break;
                }
                next2 = it.next();
            } while (!(next2 instanceof FirConstructor));
            firConstructor = (FirConstructor) next2;
        }
        for (JavaAnnotationArgument javaAnnotationArgument : collection) {
            Name name2 = javaAnnotationArgument.getName();
            if (name2 == null) {
                name2 = StandardClassIds.Annotations.ParameterNames.INSTANCE.getValue();
            }
            if (firConstructor == null || (valueParameters = firConstructor.getValueParameters()) == null) {
                firValueParameter = null;
            } else {
                Iterator<T> it2 = valueParameters.iterator();
                do {
                    if (!it2.hasNext()) {
                        next = null;
                        break;
                    }
                    next = it2.next();
                } while (!Intrinsics.areEqual(((FirValueParameter) next).getName(), name2));
                firValueParameter = (FirValueParameter) next;
            }
            Pair pair = TuplesKt.to(name2, toFirExpression(javaAnnotationArgument, firSession, JavaTypeParameterStack.INSTANCE.getEMPTY(), firValueParameter != null ? firValueParameter.getReturnTypeRef() : null));
            map.put(pair.getFirst(), pair.getSecond());
        }
    }

    public static final List<FirAnnotation> convertAnnotationsToFir(Iterable<? extends JavaAnnotation> iterable, FirSession session) {
        Intrinsics.checkNotNullParameter(iterable, "<this>");
        Intrinsics.checkNotNullParameter(session, "session");
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(iterable, 10));
        Iterator<? extends JavaAnnotation> it = iterable.iterator();
        while (it.hasNext()) {
            arrayList.add(toFirAnnotationCall(it.next(), session));
        }
        return arrayList;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static final FirValueParameter toFirValueParameter(final JavaValueParameter javaValueParameter, final FirSession session, FirFunctionSymbol<?> functionSymbol, FirModuleData moduleData, int i) {
        PsiElement psi;
        Intrinsics.checkNotNullParameter(javaValueParameter, "<this>");
        Intrinsics.checkNotNullParameter(session, "session");
        Intrinsics.checkNotNullParameter(functionSymbol, "functionSymbol");
        Intrinsics.checkNotNullParameter(moduleData, "moduleData");
        FirJavaValueParameterBuilder firJavaValueParameterBuilder = new FirJavaValueParameterBuilder();
        KtRealPsiSourceElement ktFakeSourceElement = null;
        JavaElementImpl javaElementImpl = javaValueParameter instanceof JavaElementImpl ? (JavaElementImpl) javaValueParameter : null;
        if (javaElementImpl != null && (psi = javaElementImpl.getPsi()) != null) {
            Intrinsics.checkNotNull(psi);
            Object obj = (KtSourceElementKind) KtRealSourceElementKind.INSTANCE;
            if (obj instanceof KtRealSourceElementKind) {
                ktFakeSourceElement = new KtRealPsiSourceElement(psi);
            } else {
                if (!(obj instanceof KtFakeSourceElementKind)) {
                    throw new NoWhenBranchMatchedException();
                }
                ktFakeSourceElement = new KtFakeSourceElement(psi, (KtFakeSourceElementKind) obj);
            }
        }
        firJavaValueParameterBuilder.setSource(ktFakeSourceElement);
        firJavaValueParameterBuilder.setFromSource(javaValueParameter.isFromSource());
        firJavaValueParameterBuilder.setModuleData(moduleData);
        firJavaValueParameterBuilder.setContainingFunctionSymbol(functionSymbol);
        Name name2 = javaValueParameter.getName();
        if (name2 == null) {
            name2 = Name.identifier("p" + i);
            Intrinsics.checkNotNullExpressionValue(name2, "identifier(...)");
        }
        firJavaValueParameterBuilder.setName(name2);
        firJavaValueParameterBuilder.setReturnTypeRef(JavaTypeConversionKt.toFirJavaTypeRef(javaValueParameter.getType(), session));
        firJavaValueParameterBuilder.setVararg(javaValueParameter.getIsVararg());
        firJavaValueParameterBuilder.setAnnotationBuilder(new Function0<List<? extends FirAnnotation>>() { // from class: org.jetbrains.kotlin.fir.java.JavaAnnotationsMappingKt$toFirValueParameter$1$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final List<? extends FirAnnotation> invoke() {
                return JavaAnnotationsMappingKt.convertAnnotationsToFir(javaValueParameter, session);
            }
        });
        return firJavaValueParameterBuilder.build();
    }

    private static final FirPropertyAccessExpression buildEnumCall(FirSession firSession, ClassId classId, Name name2) {
        FirResolvedNamedReference firResolvedNamedReferenceBuild;
        FirErrorNamedReference firErrorNamedReferenceBuild;
        FirVariableSymbol firVariableSymbol;
        FirPropertyAccessExpressionBuilder firPropertyAccessExpressionBuilder = new FirPropertyAccessExpressionBuilder();
        FirFromMissingDependenciesNamedReference firFromMissingDependenciesNamedReferenceBuild = null;
        if (classId == null || name2 == null || (firVariableSymbol = (FirVariableSymbol) CollectionsKt.firstOrNull((List) FirSymbolProviderKt.getClassDeclaredPropertySymbols(FirSymbolProviderKt.getSymbolProvider(firSession), classId, name2))) == null) {
            firResolvedNamedReferenceBuild = null;
        } else {
            FirResolvedNamedReferenceBuilder firResolvedNamedReferenceBuilder = new FirResolvedNamedReferenceBuilder();
            firResolvedNamedReferenceBuilder.setName(name2);
            firResolvedNamedReferenceBuilder.setResolvedSymbol(firVariableSymbol);
            firResolvedNamedReferenceBuild = firResolvedNamedReferenceBuilder.build();
        }
        if (firResolvedNamedReferenceBuild != null) {
            firErrorNamedReferenceBuild = firResolvedNamedReferenceBuild;
        } else {
            if (name2 != null) {
                FirFromMissingDependenciesNamedReferenceBuilder firFromMissingDependenciesNamedReferenceBuilder = new FirFromMissingDependenciesNamedReferenceBuilder();
                firFromMissingDependenciesNamedReferenceBuilder.setName(name2);
                firFromMissingDependenciesNamedReferenceBuild = firFromMissingDependenciesNamedReferenceBuilder.build();
            }
            if (firFromMissingDependenciesNamedReferenceBuild != null) {
                firErrorNamedReferenceBuild = firFromMissingDependenciesNamedReferenceBuild;
            } else {
                FirErrorNamedReferenceBuilder firErrorNamedReferenceBuilder = new FirErrorNamedReferenceBuilder();
                firErrorNamedReferenceBuilder.setDiagnostic(new ConeSimpleDiagnostic("Enum entry name is null in Java for " + classId, DiagnosticKind.Java));
                firErrorNamedReferenceBuild = firErrorNamedReferenceBuilder.build();
            }
        }
        firPropertyAccessExpressionBuilder.setCalleeReference(firErrorNamedReferenceBuild);
        if (classId != null) {
            FirResolvedTypeRefBuilder firResolvedTypeRefBuilder = new FirResolvedTypeRefBuilder();
            firResolvedTypeRefBuilder.setType(new ConeClassLikeTypeImpl(TypeConstructionUtilsKt.toLookupTag(classId), new ConeTypeProjection[0], false, null, 8, null));
            firPropertyAccessExpressionBuilder.setTypeRef(firResolvedTypeRefBuilder.mo6809build());
        }
        return firPropertyAccessExpressionBuilder.mo6809build();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final FirExpression mapJavaTargetArguments(List<? extends JavaAnnotationArgument> list, FirSession firSession) {
        FirVarargArgumentsExpressionBuilder firVarargArgumentsExpressionBuilder = new FirVarargArgumentsExpressionBuilder();
        EnumSet enumSetNoneOf = EnumSet.noneOf(AnnotationTarget.class);
        Iterator<? extends JavaAnnotationArgument> it = list.iterator();
        while (true) {
            if (it.hasNext()) {
                JavaAnnotationArgument next = it.next();
                if (!(next instanceof JavaEnumValueAnnotationArgument)) {
                    return null;
                }
                Map<String, EnumSet<AnnotationTarget>> map = JAVA_TARGETS_TO_KOTLIN;
                Name entryName = ((JavaEnumValueAnnotationArgument) next).getEntryName();
                EnumSet<AnnotationTarget> enumSet = map.get(entryName != null ? entryName.asString() : null);
                if (enumSet != null) {
                    enumSetNoneOf.addAll(enumSet);
                }
            } else {
                ClassId annotationTarget = StandardClassIds.INSTANCE.getAnnotationTarget();
                Intrinsics.checkNotNull(enumSetNoneOf);
                List<FirExpression> arguments = firVarargArgumentsExpressionBuilder.getArguments();
                Iterator it2 = enumSetNoneOf.iterator();
                while (it2.hasNext()) {
                    arguments.add(buildEnumCall(firSession, annotationTarget, Name.identifier(((AnnotationTarget) it2.next()).name())));
                }
                ConeClassLikeTypeImpl coneClassLikeTypeImpl = new ConeClassLikeTypeImpl(TypeConstructionUtilsKt.toLookupTag(annotationTarget), new ConeTypeProjection[0], false, ConeAttributes.INSTANCE.getEmpty());
                FirResolvedTypeRefBuilder firResolvedTypeRefBuilder = new FirResolvedTypeRefBuilder();
                ConeClassLikeTypeImpl coneClassLikeTypeImpl2 = coneClassLikeTypeImpl;
                firResolvedTypeRefBuilder.setType(coneClassLikeTypeImpl2);
                firVarargArgumentsExpressionBuilder.setTypeRef(firResolvedTypeRefBuilder.mo6809build());
                FirResolvedTypeRefBuilder firResolvedTypeRefBuilder2 = new FirResolvedTypeRefBuilder();
                firResolvedTypeRefBuilder2.setType(ArrayUtilsKt.createOutArrayType$default(coneClassLikeTypeImpl2, false, false, 3, null));
                firVarargArgumentsExpressionBuilder.setVarargElementType(firResolvedTypeRefBuilder2.mo6809build());
                return firVarargArgumentsExpressionBuilder.mo6809build();
            }
        }
    }

    private static final FirAnnotation toFirAnnotationCall(final JavaAnnotation javaAnnotation, final FirSession firSession) {
        ClassId deprecated;
        Name shortClassName;
        FirErrorTypeRef firErrorTypeRefMo6810build;
        FirAnnotationBuilder firAnnotationBuilder = new FirAnnotationBuilder();
        ClassId classId = javaAnnotation.getClassId();
        if (Intrinsics.areEqual(classId, StandardClassIds.Annotations.Java.INSTANCE.getTarget())) {
            deprecated = StandardClassIds.Annotations.INSTANCE.getTarget();
        } else if (Intrinsics.areEqual(classId, StandardClassIds.Annotations.Java.INSTANCE.getRetention())) {
            deprecated = StandardClassIds.Annotations.INSTANCE.getRetention();
        } else if (Intrinsics.areEqual(classId, StandardClassIds.Annotations.Java.INSTANCE.getDocumented())) {
            deprecated = StandardClassIds.Annotations.INSTANCE.getMustBeDocumented();
        } else {
            deprecated = Intrinsics.areEqual(classId, StandardClassIds.Annotations.Java.INSTANCE.getDeprecated()) ? StandardClassIds.Annotations.INSTANCE.getDeprecated() : javaAnnotation.getClassId();
        }
        final ConeClassLikeLookupTagImpl lookupTag = deprecated != null ? TypeConstructionUtilsKt.toLookupTag(deprecated) : null;
        if (lookupTag == null) {
            ClassId classId2 = javaAnnotation.getClassId();
            if (classId2 == null || (shortClassName = classId2.getShortClassName()) == null) {
                shortClassName = SpecialNames.NO_NAME_PROVIDED;
            }
            Intrinsics.checkNotNull(shortClassName);
            FirErrorTypeRefBuilder firErrorTypeRefBuilder = new FirErrorTypeRefBuilder();
            firErrorTypeRefBuilder.setDiagnostic(new ConeUnresolvedReferenceError(shortClassName));
            firErrorTypeRefMo6810build = firErrorTypeRefBuilder.mo6809build();
        } else {
            FirResolvedTypeRefBuilder firResolvedTypeRefBuilder = new FirResolvedTypeRefBuilder();
            firResolvedTypeRefBuilder.setType(new ConeClassLikeTypeImpl(lookupTag, new ConeTypeProjection[0], false, null, 8, null));
            firErrorTypeRefMo6810build = firResolvedTypeRefBuilder.mo6809build();
        }
        firAnnotationBuilder.setAnnotationTypeRef(firErrorTypeRefMo6810build);
        firAnnotationBuilder.setArgumentMapping(new FirAnnotationArgumentMapping(javaAnnotation, firSession, lookupTag) { // from class: org.jetbrains.kotlin.fir.java.JavaAnnotationsMappingKt$toFirAnnotationCall$1$3

            /* JADX INFO: renamed from: mapping$delegate, reason: from kotlin metadata */
            private final Lazy mapping;

            @Override // org.jetbrains.kotlin.fir.FirPureAbstractElement, org.jetbrains.kotlin.fir.FirElement
            public <R, D> void acceptChildren(FirVisitor<? extends R, ? super D> visitor, D data) {
                Intrinsics.checkNotNullParameter(visitor, "visitor");
            }

            @Override // org.jetbrains.kotlin.fir.expressions.FirAnnotationArgumentMapping, org.jetbrains.kotlin.fir.FirElement
            public KtSourceElement getSource() {
                return null;
            }

            {
                this.mapping = LazyKt.lazy(new Function0<Map<Name, ? extends FirExpression>>() { // from class: org.jetbrains.kotlin.fir.java.JavaAnnotationsMappingKt$toFirAnnotationCall$1$3$mapping$2
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(0);
                    }

                    @Override // kotlin.jvm.functions.Function0
                    public final Map<Name, ? extends FirExpression> invoke() {
                        FirExpression firExpressionMapJavaRetentionArgument;
                        FirExpression firExpressionMapJavaTargetArguments;
                        Map<Name, ? extends FirExpression> linkedHashMap = null;
                        if (Intrinsics.areEqual(javaAnnotation.getClassId(), StandardClassIds.Annotations.Java.INSTANCE.getTarget())) {
                            JavaAnnotationArgument javaAnnotationArgument = (JavaAnnotationArgument) CollectionsKt.firstOrNull(javaAnnotation.getArguments());
                            if (javaAnnotationArgument instanceof JavaArrayAnnotationArgument) {
                                firExpressionMapJavaTargetArguments = JavaAnnotationsMappingKt.mapJavaTargetArguments(((JavaArrayAnnotationArgument) javaAnnotationArgument).getElements(), firSession);
                            } else {
                                firExpressionMapJavaTargetArguments = javaAnnotationArgument instanceof JavaEnumValueAnnotationArgument ? JavaAnnotationsMappingKt.mapJavaTargetArguments(CollectionsKt.listOf(javaAnnotationArgument), firSession) : null;
                            }
                            if (firExpressionMapJavaTargetArguments != null) {
                                linkedHashMap = MapsKt.mapOf(TuplesKt.to(StandardClassIds.Annotations.ParameterNames.INSTANCE.getTargetAllowedTargets(), firExpressionMapJavaTargetArguments));
                            }
                        } else if (Intrinsics.areEqual(javaAnnotation.getClassId(), StandardClassIds.Annotations.Java.INSTANCE.getRetention())) {
                            JavaAnnotationArgument javaAnnotationArgument2 = (JavaAnnotationArgument) CollectionsKt.firstOrNull(javaAnnotation.getArguments());
                            if (javaAnnotationArgument2 != null && (firExpressionMapJavaRetentionArgument = JavaAnnotationsMappingKt.mapJavaRetentionArgument(javaAnnotationArgument2, firSession)) != null) {
                                linkedHashMap = MapsKt.mapOf(TuplesKt.to(StandardClassIds.Annotations.ParameterNames.INSTANCE.getRetentionValue(), firExpressionMapJavaRetentionArgument));
                            }
                        } else if (Intrinsics.areEqual(javaAnnotation.getClassId(), StandardClassIds.Annotations.Java.INSTANCE.getDeprecated())) {
                            linkedHashMap = MapsKt.mapOf(TuplesKt.to(StandardClassIds.Annotations.ParameterNames.INSTANCE.getDeprecatedMessage(), JavaUtilsKt.createConstantOrError$default("Deprecated in Java", firSession, null, 2, null)));
                        } else if (lookupTag != null) {
                            Collection<JavaAnnotationArgument> arguments = javaAnnotation.getArguments();
                            FirSession firSession2 = firSession;
                            ConeClassLikeLookupTagImpl coneClassLikeLookupTagImpl = lookupTag;
                            if (!arguments.isEmpty()) {
                                linkedHashMap = new LinkedHashMap<>(arguments.size());
                                JavaAnnotationsMappingKt.fillAnnotationArgumentMapping(firSession2, coneClassLikeLookupTagImpl, arguments, linkedHashMap);
                            }
                            linkedHashMap = linkedHashMap;
                        }
                        return linkedHashMap == null ? MapsKt.emptyMap() : linkedHashMap;
                    }
                });
            }

            @Override // org.jetbrains.kotlin.fir.FirPureAbstractElement, org.jetbrains.kotlin.fir.FirElement
            public <D> FirElement transformChildren(FirTransformer<? super D> transformer, D data) {
                Intrinsics.checkNotNullParameter(transformer, "transformer");
                return this;
            }

            @Override // org.jetbrains.kotlin.fir.expressions.FirAnnotationArgumentMapping
            public Map<Name, FirExpression> getMapping() {
                return (Map) this.mapping.getValue();
            }
        });
        return firAnnotationBuilder.mo6809build();
    }
}
