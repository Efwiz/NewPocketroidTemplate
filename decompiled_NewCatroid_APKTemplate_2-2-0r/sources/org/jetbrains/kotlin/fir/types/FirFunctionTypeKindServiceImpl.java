package org.jetbrains.kotlin.fir.types;

import androidx.exifinterface.media.ExifInterface;
import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.builtins.functions.FunctionTypeKind;
import org.jetbrains.kotlin.builtins.functions.FunctionTypeKindExtractor;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.declarations.FirAnnotationUtilsKt;
import org.jetbrains.kotlin.fir.expressions.FirAnnotation;
import org.jetbrains.kotlin.fir.extensions.FirExtensionServiceKt;
import org.jetbrains.kotlin.fir.extensions.FirFunctionTypeKindExtension;
import org.jetbrains.kotlin.fir.extensions.FirFunctionTypeKindExtensionKt;
import org.jetbrains.kotlin.fir.symbols.impl.FirAnonymousFunctionSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirFunctionSymbol;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.name.FqName;

/* JADX INFO: compiled from: FirFunctionTypeKindServiceImpl.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000^\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u001a\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u000b0\r2\n\u0010\u000e\u001a\u0006\u0012\u0002\b\u00030\u000fH\u0016J\u0016\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u000b0\r2\u0006\u0010\u0011\u001a\u00020\u0012H\u0016J \u0010\u0013\u001a\u0004\u0018\u00010\u000b2\u0006\u0010\u0014\u001a\u00020\u00152\f\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00170\rH\u0016J\u0016\u0010\u0018\u001a\u0004\u0018\u00010\u000b2\n\u0010\u000e\u001a\u0006\u0012\u0002\b\u00030\u000fH\u0016JZ\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u000b0\r\"\u0004\b\u0000\u0010\u001a2\u0006\u0010\u001b\u001a\u0002H\u001a2\u0017\u0010\u001c\u001a\u0013\u0012\u0004\u0012\u0002H\u001a\u0012\u0004\u0012\u00020\u001e0\u001d¢\u0006\u0002\b\u001f2\u001d\u0010\u0016\u001a\u0019\u0012\u0004\u0012\u0002H\u001a\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00150\r0\u001d¢\u0006\u0002\b\u001fH\u0082\b¢\u0006\u0002\u0010 J \u0010!\u001a\u00020\"*\b\u0012\u0004\u0012\u00020\u000b0\n2\f\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00150\rH\u0002R\u0014\u0010\u0005\u001a\u00020\u0006X\u0094\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0014\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006#"}, d2 = {"Lorg/jetbrains/kotlin/fir/types/FirFunctionTypeKindServiceImpl;", "Lorg/jetbrains/kotlin/fir/types/FirFunctionTypeKindService;", "session", "Lorg/jetbrains/kotlin/fir/FirSession;", "(Lorg/jetbrains/kotlin/fir/FirSession;)V", "extractor", "Lorg/jetbrains/kotlin/builtins/functions/FunctionTypeKindExtractor;", "getExtractor", "()Lorg/jetbrains/kotlin/builtins/functions/FunctionTypeKindExtractor;", "nonReflectKindsFromExtensions", "", "Lorg/jetbrains/kotlin/builtins/functions/FunctionTypeKind;", "extractAllSpecialKindsForFunction", "", "functionSymbol", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirFunctionSymbol;", "extractAllSpecialKindsForFunctionTypeRef", "typeRef", "Lorg/jetbrains/kotlin/fir/types/FirFunctionTypeRef;", "extractSingleExtensionKindForDeserializedConeType", "classId", "Lorg/jetbrains/kotlin/name/ClassId;", "annotations", "Lorg/jetbrains/kotlin/fir/expressions/FirAnnotation;", "extractSingleSpecialKindForFunction", "extractSpecialKindsImpl", ExifInterface.GPS_DIRECTION_TRUE, FirebaseAnalytics.Param.SOURCE, "isSuspend", "Lkotlin/Function1;", "", "Lkotlin/ExtensionFunctionType;", "(Ljava/lang/Object;Lkotlin/jvm/functions/Function1;Lkotlin/jvm/functions/Function1;)Ljava/util/List;", "extractKindsFromAnnotations", "", "providers"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirFunctionTypeKindServiceImpl extends FirFunctionTypeKindService {
    private final FunctionTypeKindExtractor extractor;
    private final List<FunctionTypeKind> nonReflectKindsFromExtensions;
    private final FirSession session;

    public FirFunctionTypeKindServiceImpl(FirSession session) {
        Intrinsics.checkNotNullParameter(session, "session");
        this.session = session;
        this.nonReflectKindsFromExtensions = new ArrayList();
        final List listCreateListBuilder = CollectionsKt.createListBuilder();
        listCreateListBuilder.add(FunctionTypeKind.Function.INSTANCE);
        listCreateListBuilder.add(FunctionTypeKind.SuspendFunction.INSTANCE);
        listCreateListBuilder.add(FunctionTypeKind.KFunction.INSTANCE);
        listCreateListBuilder.add(FunctionTypeKind.KSuspendFunction.INSTANCE);
        FirFunctionTypeKindExtension.FunctionTypeKindRegistrar functionTypeKindRegistrar = new FirFunctionTypeKindExtension.FunctionTypeKindRegistrar() { // from class: org.jetbrains.kotlin.fir.types.FirFunctionTypeKindServiceImpl$extractor$1$kinds$1$registrar$1
            @Override // org.jetbrains.kotlin.fir.extensions.FirFunctionTypeKindExtension.FunctionTypeKindRegistrar
            public void registerKind(FunctionTypeKind nonReflectKind, FunctionTypeKind reflectKind) {
                Intrinsics.checkNotNullParameter(nonReflectKind, "nonReflectKind");
                Intrinsics.checkNotNullParameter(reflectKind, "reflectKind");
                if (!Intrinsics.areEqual(nonReflectKind.reflectKind(), reflectKind)) {
                    throw new IllegalArgumentException("Failed requirement.".toString());
                }
                if (!Intrinsics.areEqual(reflectKind.nonReflectKind(), nonReflectKind)) {
                    throw new IllegalArgumentException("Failed requirement.".toString());
                }
                listCreateListBuilder.add(nonReflectKind);
                listCreateListBuilder.add(reflectKind);
                this.nonReflectKindsFromExtensions.add(nonReflectKind);
            }
        };
        Iterator<FirFunctionTypeKindExtension> it = FirFunctionTypeKindExtensionKt.getFunctionTypeKindExtensions(FirExtensionServiceKt.getExtensionService(this.session)).iterator();
        while (it.hasNext()) {
            it.next().registerKinds(functionTypeKindRegistrar);
        }
        List listBuild = CollectionsKt.build(listCreateListBuilder);
        List<FunctionTypeKind> list = listBuild;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
        for (FunctionTypeKind functionTypeKind : list) {
            arrayList.add(functionTypeKind.getPackageFqName() + '.' + functionTypeKind.getClassNamePrefix());
        }
        ArrayList arrayList2 = arrayList;
        if (Intrinsics.areEqual(CollectionsKt.distinct(arrayList2), arrayList2)) {
            this.extractor = new FunctionTypeKindExtractor(listBuild);
            return;
        }
        throw new IllegalArgumentException(("There are clashing functional type kinds: " + arrayList2).toString());
    }

    @Override // org.jetbrains.kotlin.fir.types.FirFunctionTypeKindService
    protected FunctionTypeKindExtractor getExtractor() {
        return this.extractor;
    }

    @Override // org.jetbrains.kotlin.fir.types.FirFunctionTypeKindService
    public FunctionTypeKind extractSingleSpecialKindForFunction(FirFunctionSymbol<?> functionSymbol) {
        Intrinsics.checkNotNullParameter(functionSymbol, "functionSymbol");
        if (this.nonReflectKindsFromExtensions.isEmpty()) {
            FunctionTypeKind.SuspendFunction suspendFunction = FunctionTypeKind.SuspendFunction.INSTANCE;
            if (!functionSymbol.getRawStatus().isSuspend()) {
                suspendFunction = null;
            }
            return suspendFunction;
        }
        return (FunctionTypeKind) CollectionsKt.singleOrNull((List) extractAllSpecialKindsForFunction(functionSymbol));
    }

    @Override // org.jetbrains.kotlin.fir.types.FirFunctionTypeKindService
    public FunctionTypeKind extractSingleExtensionKindForDeserializedConeType(ClassId classId, List<? extends FirAnnotation> annotations) {
        Intrinsics.checkNotNullParameter(classId, "classId");
        Intrinsics.checkNotNullParameter(annotations, "annotations");
        if (this.nonReflectKindsFromExtensions.isEmpty() || annotations.isEmpty()) {
            return null;
        }
        FunctionTypeKindExtractor extractor = getExtractor();
        FqName packageFqName = classId.getPackageFqName();
        Intrinsics.checkNotNullExpressionValue(packageFqName, "getPackageFqName(...)");
        String strAsString = classId.getShortClassName().asString();
        Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
        FunctionTypeKind functionalClassKind = extractor.getFunctionalClassKind(packageFqName, strAsString);
        if (functionalClassKind == null || !Intrinsics.areEqual(functionalClassKind.nonReflectKind(), FunctionTypeKind.Function.INSTANCE)) {
            return null;
        }
        List<FunctionTypeKind> listCreateListBuilder = CollectionsKt.createListBuilder();
        ArrayList arrayList = new ArrayList();
        Iterator<T> it = annotations.iterator();
        while (it.hasNext()) {
            ClassId annotationClassId = FirAnnotationUtilsKt.toAnnotationClassId((FirAnnotation) it.next(), this.session);
            if (annotationClassId != null) {
                arrayList.add(annotationClassId);
            }
        }
        extractKindsFromAnnotations(listCreateListBuilder, arrayList);
        FunctionTypeKind functionTypeKind = (FunctionTypeKind) CollectionsKt.singleOrNull(CollectionsKt.build(listCreateListBuilder));
        if (functionTypeKind == null) {
            return null;
        }
        boolean isReflectType = functionalClassKind.getIsReflectType();
        if (!isReflectType) {
            return functionTypeKind;
        }
        if (isReflectType) {
            return functionTypeKind.reflectKind();
        }
        throw new NoWhenBranchMatchedException();
    }

    private final void extractKindsFromAnnotations(List<FunctionTypeKind> list, List<ClassId> list2) {
        for (ClassId classId : list2) {
            for (FunctionTypeKind functionTypeKind : this.nonReflectKindsFromExtensions) {
                if (Intrinsics.areEqual(functionTypeKind.getAnnotationOnInvokeClassId(), classId)) {
                    list.add(functionTypeKind);
                }
            }
        }
    }

    @Override // org.jetbrains.kotlin.fir.types.FirFunctionTypeKindService
    public List<FunctionTypeKind> extractAllSpecialKindsForFunction(FirFunctionSymbol<?> functionSymbol) {
        ArrayList resolvedAnnotationClassIds;
        Intrinsics.checkNotNullParameter(functionSymbol, "functionSymbol");
        List<FunctionTypeKind> listCreateListBuilder = CollectionsKt.createListBuilder();
        if (functionSymbol.getRawStatus().isSuspend()) {
            listCreateListBuilder.add(FunctionTypeKind.SuspendFunction.INSTANCE);
        }
        if (!this.nonReflectKindsFromExtensions.isEmpty()) {
            if (!(functionSymbol instanceof FirAnonymousFunctionSymbol)) {
                resolvedAnnotationClassIds = functionSymbol.getResolvedAnnotationClassIds();
            } else {
                List<FirAnnotation> annotations = functionSymbol.getAnnotations();
                ArrayList arrayList = new ArrayList();
                Iterator<T> it = annotations.iterator();
                while (it.hasNext()) {
                    ClassId annotationClassId = FirAnnotationUtilsKt.toAnnotationClassId((FirAnnotation) it.next(), this.session);
                    if (annotationClassId != null) {
                        arrayList.add(annotationClassId);
                    }
                }
                resolvedAnnotationClassIds = arrayList;
            }
            extractKindsFromAnnotations(listCreateListBuilder, resolvedAnnotationClassIds);
        }
        return CollectionsKt.build(listCreateListBuilder);
    }

    @Override // org.jetbrains.kotlin.fir.types.FirFunctionTypeKindService
    public List<FunctionTypeKind> extractAllSpecialKindsForFunctionTypeRef(FirFunctionTypeRef typeRef) {
        Intrinsics.checkNotNullParameter(typeRef, "typeRef");
        List<FunctionTypeKind> listCreateListBuilder = CollectionsKt.createListBuilder();
        if (typeRef.getIsSuspend()) {
            listCreateListBuilder.add(FunctionTypeKind.SuspendFunction.INSTANCE);
        }
        if (!this.nonReflectKindsFromExtensions.isEmpty()) {
            List<FirAnnotation> annotations = typeRef.getAnnotations();
            ArrayList arrayList = new ArrayList();
            Iterator<T> it = annotations.iterator();
            while (it.hasNext()) {
                ClassId annotationClassId = FirAnnotationUtilsKt.toAnnotationClassId((FirAnnotation) it.next(), this.session);
                if (annotationClassId != null) {
                    arrayList.add(annotationClassId);
                }
            }
            extractKindsFromAnnotations(listCreateListBuilder, arrayList);
        }
        return CollectionsKt.build(listCreateListBuilder);
    }
}
