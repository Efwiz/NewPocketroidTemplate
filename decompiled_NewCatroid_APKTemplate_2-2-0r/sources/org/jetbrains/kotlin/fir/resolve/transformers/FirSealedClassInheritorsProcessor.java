package org.jetbrains.kotlin.fir.resolve.transformers;

import androidx.exifinterface.media.ExifInterface;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.descriptors.Modality;
import org.jetbrains.kotlin.fir.FirElement;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.UtilsKt;
import org.jetbrains.kotlin.fir.declarations.FirDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirFile;
import org.jetbrains.kotlin.fir.declarations.FirRegularClass;
import org.jetbrains.kotlin.fir.declarations.FirResolvePhase;
import org.jetbrains.kotlin.fir.declarations.FirTypeAlias;
import org.jetbrains.kotlin.fir.declarations.SealedClassInheritorsKt;
import org.jetbrains.kotlin.fir.declarations.utils.FirDeclarationUtilKt;
import org.jetbrains.kotlin.fir.expressions.FirStatement;
import org.jetbrains.kotlin.fir.resolve.LookupTagUtilsKt;
import org.jetbrains.kotlin.fir.resolve.ScopeSession;
import org.jetbrains.kotlin.fir.resolve.providers.FirSymbolProvider;
import org.jetbrains.kotlin.fir.resolve.providers.FirSymbolProviderKt;
import org.jetbrains.kotlin.fir.symbols.ConeClassifierLookupTag;
import org.jetbrains.kotlin.fir.symbols.FirLazyDeclarationResolverKt;
import org.jetbrains.kotlin.fir.symbols.impl.FirClassifierSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirRegularClassSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirTypeAliasSymbol;
import org.jetbrains.kotlin.fir.types.ConeKotlinType;
import org.jetbrains.kotlin.fir.types.ConeLookupTagBasedType;
import org.jetbrains.kotlin.fir.types.FirTypeRef;
import org.jetbrains.kotlin.fir.types.FirTypeUtilsKt;
import org.jetbrains.kotlin.fir.visitors.FirDefaultVisitor;
import org.jetbrains.kotlin.fir.visitors.FirTransformer;
import org.jetbrains.kotlin.fir.visitors.FirTransformerUtilKt;
import org.jetbrains.kotlin.name.ClassId;

/* JADX INFO: compiled from: FirSealedClassInheritorsProcessor.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u0001:\u0002\f\rB\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\u0016\u0010\u0007\u001a\u00020\b2\f\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\nH\u0016¨\u0006\u000e"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/transformers/FirSealedClassInheritorsProcessor;", "Lorg/jetbrains/kotlin/fir/resolve/transformers/FirGlobalResolveProcessor;", "session", "Lorg/jetbrains/kotlin/fir/FirSession;", "scopeSession", "Lorg/jetbrains/kotlin/fir/resolve/ScopeSession;", "(Lorg/jetbrains/kotlin/fir/FirSession;Lorg/jetbrains/kotlin/fir/resolve/ScopeSession;)V", "process", "", "files", "", "Lorg/jetbrains/kotlin/fir/declarations/FirFile;", "InheritorsCollector", "InheritorsTransformer", "resolve"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirSealedClassInheritorsProcessor extends FirGlobalResolveProcessor {
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public FirSealedClassInheritorsProcessor(FirSession session, ScopeSession scopeSession) {
        super(session, scopeSession, FirResolvePhase.SEALED_CLASS_INHERITORS);
        Intrinsics.checkNotNullParameter(session, "session");
        Intrinsics.checkNotNullParameter(scopeSession, "scopeSession");
    }

    @Override // org.jetbrains.kotlin.fir.resolve.transformers.FirGlobalResolveProcessor
    public void process(Collection<? extends FirFile> files) {
        Intrinsics.checkNotNullParameter(files, "files");
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        InheritorsCollector inheritorsCollector = new InheritorsCollector(getSession());
        Collection<? extends FirFile> collection = files;
        for (FirFile firFile : collection) {
            try {
                firFile.accept(inheritorsCollector, linkedHashMap);
                Unit unit = Unit.INSTANCE;
            } catch (Throwable th) {
                UtilsKt.getExceptionHandler(firFile.getModuleData().getSession()).handleExceptionOnFileAnalysis(firFile, th);
                throw null;
            }
        }
        for (FirFile firFile2 : collection) {
            try {
                FirTransformerUtilKt.transformSingle(firFile2, new InheritorsTransformer(linkedHashMap), null);
                Unit unit2 = Unit.INSTANCE;
            } catch (Throwable th2) {
                UtilsKt.getExceptionHandler(firFile2.getModuleData().getSession()).handleExceptionOnFileAnalysis(firFile2, th2);
                throw null;
            }
        }
    }

    /* JADX INFO: compiled from: FirSealedClassInheritorsProcessor.kt */
    @Metadata(d1 = {"\u0000D\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\u0010%\n\u0002\u0018\u0002\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002 \u0012\u0004\u0012\u00020\u0002\u0012\u0016\u0012\u0014\u0012\u0004\u0012\u00020\u0004\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00060\u00050\u00030\u0001B\r\u0012\u0006\u0010\u0007\u001a\u00020\b¢\u0006\u0002\u0010\tJ\u001a\u0010\f\u001a\u0004\u0018\u00010\u00042\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u0010H\u0002J*\u0010\u0011\u001a\u00020\u00022\u0006\u0010\u0012\u001a\u00020\u00132\u0018\u0010\u0014\u001a\u0014\u0012\u0004\u0012\u00020\u0004\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00060\u00050\u0003H\u0016J*\u0010\u0015\u001a\u00020\u00022\u0006\u0010\u0016\u001a\u00020\u00172\u0018\u0010\u0014\u001a\u0014\u0012\u0004\u0012\u00020\u0004\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00060\u00050\u0003H\u0016J*\u0010\u0018\u001a\u00020\u00022\u0006\u0010\u0019\u001a\u00020\u00042\u0018\u0010\u0014\u001a\u0014\u0012\u0004\u0012\u00020\u0004\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00060\u00050\u0003H\u0016R\u0011\u0010\u0007\u001a\u00020\b¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000b¨\u0006\u001a"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/transformers/FirSealedClassInheritorsProcessor$InheritorsCollector;", "Lorg/jetbrains/kotlin/fir/visitors/FirDefaultVisitor;", "", "", "Lorg/jetbrains/kotlin/fir/declarations/FirRegularClass;", "", "Lorg/jetbrains/kotlin/name/ClassId;", "session", "Lorg/jetbrains/kotlin/fir/FirSession;", "(Lorg/jetbrains/kotlin/fir/FirSession;)V", "getSession", "()Lorg/jetbrains/kotlin/fir/FirSession;", "extractClassFromTypeRef", "symbolProvider", "Lorg/jetbrains/kotlin/fir/resolve/providers/FirSymbolProvider;", "typeRef", "Lorg/jetbrains/kotlin/fir/types/FirTypeRef;", "visitElement", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, "Lorg/jetbrains/kotlin/fir/FirElement;", "data", "visitFile", "file", "Lorg/jetbrains/kotlin/fir/declarations/FirFile;", "visitRegularClass", "regularClass", "resolve"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class InheritorsCollector extends FirDefaultVisitor<Unit, Map<FirRegularClass, List<ClassId>>> {
        private final FirSession session;

        public void visitElement(FirElement element, Map<FirRegularClass, List<ClassId>> data) {
            Intrinsics.checkNotNullParameter(element, "element");
            Intrinsics.checkNotNullParameter(data, "data");
        }

        public InheritorsCollector(FirSession session) {
            Intrinsics.checkNotNullParameter(session, "session");
            this.session = session;
        }

        public final FirSession getSession() {
            return this.session;
        }

        @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
        public /* bridge */ /* synthetic */ Object visitElement(FirElement firElement, Object obj) {
            visitElement(firElement, (Map<FirRegularClass, List<ClassId>>) obj);
            return Unit.INSTANCE;
        }

        @Override // org.jetbrains.kotlin.fir.visitors.FirDefaultVisitor, org.jetbrains.kotlin.fir.visitors.FirVisitor
        public /* bridge */ /* synthetic */ Object visitFile(FirFile firFile, Object obj) {
            visitFile(firFile, (Map<FirRegularClass, List<ClassId>>) obj);
            return Unit.INSTANCE;
        }

        @Override // org.jetbrains.kotlin.fir.visitors.FirDefaultVisitor, org.jetbrains.kotlin.fir.visitors.FirVisitor
        public /* bridge */ /* synthetic */ Object visitRegularClass(FirRegularClass firRegularClass, Object obj) {
            visitRegularClass(firRegularClass, (Map<FirRegularClass, List<ClassId>>) obj);
            return Unit.INSTANCE;
        }

        public void visitFile(FirFile file, Map<FirRegularClass, List<ClassId>> data) {
            Intrinsics.checkNotNullParameter(file, "file");
            Intrinsics.checkNotNullParameter(data, "data");
            Iterator<T> it = file.getDeclarations().iterator();
            while (it.hasNext()) {
                ((FirDeclaration) it.next()).accept(this, data);
            }
        }

        public void visitRegularClass(FirRegularClass regularClass, Map<FirRegularClass, List<ClassId>> data) {
            Intrinsics.checkNotNullParameter(regularClass, "regularClass");
            Intrinsics.checkNotNullParameter(data, "data");
            Iterator<T> it = regularClass.getDeclarations().iterator();
            while (it.hasNext()) {
                ((FirDeclaration) it.next()).accept(this, data);
            }
            if (regularClass.getStatus().getModality() == Modality.SEALED) {
                final FirSealedClassInheritorsProcessor$InheritorsCollector$visitRegularClass$2 firSealedClassInheritorsProcessor$InheritorsCollector$visitRegularClass$2 = new Function1<FirRegularClass, List<ClassId>>() { // from class: org.jetbrains.kotlin.fir.resolve.transformers.FirSealedClassInheritorsProcessor$InheritorsCollector$visitRegularClass$2
                    @Override // kotlin.jvm.functions.Function1
                    public final List<ClassId> invoke(FirRegularClass it2) {
                        Intrinsics.checkNotNullParameter(it2, "it");
                        return new ArrayList();
                    }
                };
                data.computeIfAbsent(regularClass, new Function() { // from class: org.jetbrains.kotlin.fir.resolve.transformers.FirSealedClassInheritorsProcessor$InheritorsCollector$$ExternalSyntheticLambda0
                    @Override // java.util.function.Function
                    public final Object apply(Object obj) {
                        return FirSealedClassInheritorsProcessor.InheritorsCollector.visitRegularClass$lambda$2(firSealedClassInheritorsProcessor$InheritorsCollector$visitRegularClass$2, obj);
                    }
                });
            }
            FirSymbolProvider symbolProvider = FirSymbolProviderKt.getSymbolProvider(this.session);
            Iterator<FirTypeRef> it2 = regularClass.getSuperTypeRefs().iterator();
            while (it2.hasNext()) {
                FirRegularClass firRegularClassExtractClassFromTypeRef = extractClassFromTypeRef(symbolProvider, it2.next());
                if ((firRegularClassExtractClassFromTypeRef != null ? firRegularClassExtractClassFromTypeRef.getStatus().getModality() : null) != Modality.SEALED) {
                    firRegularClassExtractClassFromTypeRef = null;
                }
                if (firRegularClassExtractClassFromTypeRef != null && Intrinsics.areEqual(FirDeclarationUtilKt.getClassId(firRegularClassExtractClassFromTypeRef).getPackageFqName(), FirDeclarationUtilKt.getClassId(regularClass).getPackageFqName())) {
                    final FirSealedClassInheritorsProcessor$InheritorsCollector$visitRegularClass$inheritors$1 firSealedClassInheritorsProcessor$InheritorsCollector$visitRegularClass$inheritors$1 = new Function1<FirRegularClass, List<ClassId>>() { // from class: org.jetbrains.kotlin.fir.resolve.transformers.FirSealedClassInheritorsProcessor$InheritorsCollector$visitRegularClass$inheritors$1
                        @Override // kotlin.jvm.functions.Function1
                        public final List<ClassId> invoke(FirRegularClass it3) {
                            Intrinsics.checkNotNullParameter(it3, "it");
                            return new ArrayList();
                        }
                    };
                    List<ClassId> listComputeIfAbsent = data.computeIfAbsent(firRegularClassExtractClassFromTypeRef, new Function() { // from class: org.jetbrains.kotlin.fir.resolve.transformers.FirSealedClassInheritorsProcessor$InheritorsCollector$$ExternalSyntheticLambda1
                        @Override // java.util.function.Function
                        public final Object apply(Object obj) {
                            return FirSealedClassInheritorsProcessor.InheritorsCollector.visitRegularClass$lambda$4(firSealedClassInheritorsProcessor$InheritorsCollector$visitRegularClass$inheritors$1, obj);
                        }
                    });
                    Intrinsics.checkNotNullExpressionValue(listComputeIfAbsent, "computeIfAbsent(...)");
                    listComputeIfAbsent.add(regularClass.getSymbol().getClassId());
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final List visitRegularClass$lambda$2(Function1 tmp0, Object obj) {
            Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
            return (List) tmp0.invoke(obj);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final List visitRegularClass$lambda$4(Function1 tmp0, Object obj) {
            Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
            return (List) tmp0.invoke(obj);
        }

        /* JADX WARN: Multi-variable type inference failed */
        private final FirRegularClass extractClassFromTypeRef(FirSymbolProvider symbolProvider, FirTypeRef typeRef) {
            ConeClassifierLookupTag lookupTag;
            FirClassifierSymbol<?> symbolByLookupTag;
            ConeKotlinType coneType = FirTypeUtilsKt.getConeType(typeRef);
            ConeLookupTagBasedType coneLookupTagBasedType = coneType instanceof ConeLookupTagBasedType ? (ConeLookupTagBasedType) coneType : null;
            if (coneLookupTagBasedType == null || (lookupTag = coneLookupTagBasedType.getLookupTag()) == null || (symbolByLookupTag = LookupTagUtilsKt.getSymbolByLookupTag(symbolProvider, lookupTag)) == null) {
                return null;
            }
            if (symbolByLookupTag instanceof FirRegularClassSymbol) {
                return (FirRegularClass) ((FirRegularClassSymbol) symbolByLookupTag).getFir();
            }
            if (!(symbolByLookupTag instanceof FirTypeAliasSymbol)) {
                return null;
            }
            FirLazyDeclarationResolverKt.lazyResolveToPhase(symbolByLookupTag, FirResolvePhase.SUPER_TYPES);
            return extractClassFromTypeRef(symbolProvider, ((FirTypeAlias) ((FirTypeAliasSymbol) symbolByLookupTag).getFir()).getExpandedTypeRef());
        }
    }

    /* JADX INFO: compiled from: FirSealedClassInheritorsProcessor.kt */
    @Metadata(d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010%\n\u0002\u0018\u0002\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\n\u0012\u0006\u0012\u0004\u0018\u00010\u00020\u0001B\u001f\u0012\u0018\u0010\u0003\u001a\u0014\u0012\u0004\u0012\u00020\u0005\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00070\u00060\u0004¢\u0006\u0002\u0010\bJ)\u0010\t\u001a\u0002H\n\"\b\b\u0000\u0010\n*\u00020\u000b2\u0006\u0010\f\u001a\u0002H\n2\b\u0010\r\u001a\u0004\u0018\u00010\u0002H\u0016¢\u0006\u0002\u0010\u000eJ\u001a\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u00102\b\u0010\r\u001a\u0004\u0018\u00010\u0002H\u0016J\u001a\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u00052\b\u0010\r\u001a\u0004\u0018\u00010\u0002H\u0016R \u0010\u0003\u001a\u0014\u0012\u0004\u0012\u00020\u0005\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00070\u00060\u0004X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0015"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/transformers/FirSealedClassInheritorsProcessor$InheritorsTransformer;", "Lorg/jetbrains/kotlin/fir/visitors/FirTransformer;", "", "inheritorsMap", "", "Lorg/jetbrains/kotlin/fir/declarations/FirRegularClass;", "", "Lorg/jetbrains/kotlin/name/ClassId;", "(Ljava/util/Map;)V", "transformElement", ExifInterface.LONGITUDE_EAST, "Lorg/jetbrains/kotlin/fir/FirElement;", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, "data", "(Lorg/jetbrains/kotlin/fir/FirElement;Ljava/lang/Object;)Lorg/jetbrains/kotlin/fir/FirElement;", "transformFile", "Lorg/jetbrains/kotlin/fir/declarations/FirFile;", "file", "transformRegularClass", "Lorg/jetbrains/kotlin/fir/expressions/FirStatement;", "regularClass", "resolve"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class InheritorsTransformer extends FirTransformer<Object> {
        private final Map<FirRegularClass, List<ClassId>> inheritorsMap;

        @Override // org.jetbrains.kotlin.fir.visitors.FirTransformer
        public <E extends FirElement> E transformElement(E element, Object data) {
            Intrinsics.checkNotNullParameter(element, "element");
            return element;
        }

        public InheritorsTransformer(Map<FirRegularClass, List<ClassId>> inheritorsMap) {
            Intrinsics.checkNotNullParameter(inheritorsMap, "inheritorsMap");
            this.inheritorsMap = inheritorsMap;
        }

        @Override // org.jetbrains.kotlin.fir.visitors.FirTransformer
        public FirFile transformFile(FirFile file, Object data) {
            Intrinsics.checkNotNullParameter(file, "file");
            try {
                FirElement firElementTransformChildren = file.transformChildren(this, data);
                Intrinsics.checkNotNull(firElementTransformChildren, "null cannot be cast to non-null type org.jetbrains.kotlin.fir.declarations.FirFile");
                return (FirFile) firElementTransformChildren;
            } catch (Throwable th) {
                UtilsKt.getExceptionHandler(file.getModuleData().getSession()).handleExceptionOnFileAnalysis(file, th);
                throw null;
            }
        }

        @Override // org.jetbrains.kotlin.fir.visitors.FirTransformer
        public FirStatement transformRegularClass(FirRegularClass regularClass, Object data) {
            List<ClassId> listRemove;
            Intrinsics.checkNotNullParameter(regularClass, "regularClass");
            if (regularClass.getStatus().getModality() == Modality.SEALED && (listRemove = this.inheritorsMap.remove(regularClass)) != null) {
                SealedClassInheritorsKt.setSealedClassInheritors(regularClass, listRemove);
            }
            if (this.inheritorsMap.isEmpty()) {
                return regularClass;
            }
            FirElement firElementTransformChildren = regularClass.transformChildren(this, data);
            Intrinsics.checkNotNull(firElementTransformChildren, "null cannot be cast to non-null type org.jetbrains.kotlin.fir.declarations.FirRegularClass");
            return (FirRegularClass) firElementTransformChildren;
        }
    }
}
