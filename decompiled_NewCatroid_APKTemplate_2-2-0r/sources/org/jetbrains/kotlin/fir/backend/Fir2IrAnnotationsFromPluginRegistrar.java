package org.jetbrains.kotlin.fir.backend;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.KtSourceElement;
import org.jetbrains.kotlin.backend.common.extensions.IrAnnotationsFromPluginRegistrar;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.UtilsKt;
import org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirClassLikeDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirFile;
import org.jetbrains.kotlin.fir.declarations.FirTypeParameter;
import org.jetbrains.kotlin.fir.declarations.FirValueParameter;
import org.jetbrains.kotlin.fir.declarations.utils.FirDeclarationUtilKt;
import org.jetbrains.kotlin.fir.expressions.FirAnnotation;
import org.jetbrains.kotlin.fir.expressions.FirConstExpression;
import org.jetbrains.kotlin.fir.expressions.FirExpression;
import org.jetbrains.kotlin.fir.expressions.builder.FirAnnotationArgumentMappingBuilder;
import org.jetbrains.kotlin.fir.expressions.builder.FirAnnotationBuilder;
import org.jetbrains.kotlin.fir.expressions.builder.FirConstExpressionBuilderKt;
import org.jetbrains.kotlin.fir.resolve.providers.FirProviderKt;
import org.jetbrains.kotlin.fir.resolve.providers.FirProviderUtilsKt;
import org.jetbrains.kotlin.fir.resolve.providers.FirSymbolProviderKt;
import org.jetbrains.kotlin.fir.serialization.FirAdditionalMetadataAnnotationsProvider;
import org.jetbrains.kotlin.fir.symbols.impl.ConeClassLikeLookupTagImpl;
import org.jetbrains.kotlin.fir.symbols.impl.FirClassifierSymbol;
import org.jetbrains.kotlin.fir.types.ConeTypeProjection;
import org.jetbrains.kotlin.fir.types.TypeConstructionUtilsKt;
import org.jetbrains.kotlin.fir.types.TypeUtilsKt;
import org.jetbrains.kotlin.ir.declarations.IrDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationsKt;
import org.jetbrains.kotlin.ir.expressions.IrConst;
import org.jetbrains.kotlin.ir.expressions.IrConstKind;
import org.jetbrains.kotlin.ir.expressions.IrConstructorCall;
import org.jetbrains.kotlin.ir.expressions.IrExpression;
import org.jetbrains.kotlin.ir.util.AdditionalIrUtilsKt;
import org.jetbrains.kotlin.ir.util.DumpIrTreeOptions;
import org.jetbrains.kotlin.ir.util.IrUtilsKt;
import org.jetbrains.kotlin.ir.util.RenderIrElementKt;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.types.ConstantValueKind;

/* JADX INFO: compiled from: Fir2IrAnnotationsFromPluginRegistrar.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000L\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010%\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\u0018\u00002\u00020\u0001:\u0001\u0016B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u001e\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000f2\f\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u000b0\u0011H\u0016J\u0006\u0010\u0012\u001a\u00020\u0013J\f\u0010\u0014\u001a\u00020\u0015*\u00020\u000bH\u0002R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R8\u0010\u0005\u001a,\u0012\u0004\u0012\u00020\u0007\u0012\"\u0012 \u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\t0\b\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000b0\n0\u00060\u0006X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0017"}, d2 = {"Lorg/jetbrains/kotlin/fir/backend/Fir2IrAnnotationsFromPluginRegistrar;", "Lorg/jetbrains/kotlin/backend/common/extensions/IrAnnotationsFromPluginRegistrar;", "components", "Lorg/jetbrains/kotlin/fir/backend/Fir2IrComponents;", "(Lorg/jetbrains/kotlin/fir/backend/Fir2IrComponents;)V", "generatedIrDeclarationsByFileByOffset", "", "", "Lkotlin/Pair;", "", "", "Lorg/jetbrains/kotlin/ir/expressions/IrConstructorCall;", "addMetadataVisibleAnnotationsToElement", "", "declaration", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;", "annotations", "", "createMetadataAnnotationsProvider", "Lorg/jetbrains/kotlin/fir/serialization/FirAdditionalMetadataAnnotationsProvider;", "hasOnlySupportedAnnotationArgumentTypes", "", "Provider", "fir2ir"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class Fir2IrAnnotationsFromPluginRegistrar extends IrAnnotationsFromPluginRegistrar {
    private final Fir2IrComponents components;
    private final Map<String, Map<Pair<Integer, Integer>, List<IrConstructorCall>>> generatedIrDeclarationsByFileByOffset;

    public Fir2IrAnnotationsFromPluginRegistrar(Fir2IrComponents components) {
        Intrinsics.checkNotNullParameter(components, "components");
        this.components = components;
        this.generatedIrDeclarationsByFileByOffset = new LinkedHashMap();
    }

    private final boolean hasOnlySupportedAnnotationArgumentTypes(IrConstructorCall irConstructorCall) {
        int valueArgumentsCount = irConstructorCall.getValueArgumentsCount();
        for (int i = 0; i < valueArgumentsCount; i++) {
            if (!(irConstructorCall.getValueArgument(i) instanceof IrConst)) {
                return false;
            }
        }
        return true;
    }

    @Override // org.jetbrains.kotlin.backend.common.extensions.IrAnnotationsFromPluginRegistrar
    public void addMetadataVisibleAnnotationsToElement(IrDeclaration declaration, List<? extends IrConstructorCall> annotations) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        Intrinsics.checkNotNullParameter(annotations, "annotations");
        List<? extends IrConstructorCall> list = annotations;
        if (!(list instanceof Collection) || !list.isEmpty()) {
            for (IrConstructorCall irConstructorCall : list) {
                if (irConstructorCall.getTypeArgumentsCount() != 0 || !hasOnlySupportedAnnotationArgumentTypes(irConstructorCall)) {
                    throw new IllegalArgumentException("Saving annotations with arguments from IR to metadata is only supported for basic constants. See KT-58968".toString());
                }
            }
        }
        for (IrConstructorCall irConstructorCall2 : list) {
            if (!IrUtilsKt.isAnnotationClass(AdditionalIrUtilsKt.getConstructedClass(irConstructorCall2.getSymbol().getOwner()))) {
                throw new IllegalArgumentException((RenderIrElementKt.render$default(irConstructorCall2, (DumpIrTreeOptions) null, 1, (Object) null) + " is not an annotation constructor call").toString());
            }
        }
        String nameWithPackage = IrDeclarationsKt.getNameWithPackage(IrUtilsKt.getFile(declaration));
        Map<String, Map<Pair<Integer, Integer>, List<IrConstructorCall>>> map = this.generatedIrDeclarationsByFileByOffset;
        LinkedHashMap linkedHashMap = map.get(nameWithPackage);
        if (linkedHashMap == null) {
            linkedHashMap = new LinkedHashMap();
            map.put(nameWithPackage, linkedHashMap);
        }
        Map<Pair<Integer, Integer>, List<IrConstructorCall>> map2 = linkedHashMap;
        Pair<Integer, Integer> pair = TuplesKt.to(Integer.valueOf(declaration.getStartOffset()), Integer.valueOf(declaration.getEndOffset()));
        ArrayList arrayList = map2.get(pair);
        if (arrayList == null) {
            arrayList = new ArrayList();
            map2.put(pair, arrayList);
        }
        CollectionsKt.addAll(arrayList, list);
        declaration.setAnnotations(CollectionsKt.plus((Collection) declaration.getAnnotations(), (Iterable) list));
    }

    public final FirAdditionalMetadataAnnotationsProvider createMetadataAnnotationsProvider() {
        return new Provider();
    }

    /* JADX INFO: compiled from: Fir2IrAnnotationsFromPluginRegistrar.kt */
    @Metadata(d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\b\u0082\u0004\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0016\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\b2\u0006\u0010\n\u001a\u00020\u000bH\u0002J\u0016\u0010\f\u001a\b\u0012\u0004\u0012\u00020\r0\b2\u0006\u0010\n\u001a\u00020\u000bH\u0016J\u0010\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\n\u001a\u00020\u000bH\u0016J\u000e\u0010\u0010\u001a\u0004\u0018\u00010\u0011*\u00020\u000bH\u0002J\f\u0010\u0012\u001a\u00020\r*\u00020\tH\u0002J\u0014\u0010\u0013\u001a\u00020\u000b*\u00020\u000b2\u0006\u0010\u0014\u001a\u00020\u0015H\u0002R\u0018\u0010\u0003\u001a\u00020\u0004*\u00020\u00048BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0016"}, d2 = {"Lorg/jetbrains/kotlin/fir/backend/Fir2IrAnnotationsFromPluginRegistrar$Provider;", "Lorg/jetbrains/kotlin/fir/serialization/FirAdditionalMetadataAnnotationsProvider;", "(Lorg/jetbrains/kotlin/fir/backend/Fir2IrAnnotationsFromPluginRegistrar;)V", "topmostParentClassId", "Lorg/jetbrains/kotlin/name/ClassId;", "getTopmostParentClassId", "(Lorg/jetbrains/kotlin/name/ClassId;)Lorg/jetbrains/kotlin/name/ClassId;", "extractGeneratedIrDeclarations", "", "Lorg/jetbrains/kotlin/ir/expressions/IrConstructorCall;", "declaration", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclaration;", "findGeneratedAnnotationsFor", "Lorg/jetbrains/kotlin/fir/expressions/FirAnnotation;", "hasGeneratedAnnotationsFor", "", "containingFile", "Lorg/jetbrains/kotlin/fir/declarations/FirFile;", "toFirAnnotation", "topmostParent", "session", "Lorg/jetbrains/kotlin/fir/FirSession;", "fir2ir"}, k = 1, mv = {1, 9, 0}, xi = 48)
    private final class Provider extends FirAdditionalMetadataAnnotationsProvider {
        public Provider() {
        }

        @Override // org.jetbrains.kotlin.fir.serialization.FirAdditionalMetadataAnnotationsProvider
        public List<FirAnnotation> findGeneratedAnnotationsFor(FirDeclaration declaration) {
            Intrinsics.checkNotNullParameter(declaration, "declaration");
            List<IrConstructorCall> listExtractGeneratedIrDeclarations = extractGeneratedIrDeclarations(declaration);
            if (listExtractGeneratedIrDeclarations.isEmpty()) {
                listExtractGeneratedIrDeclarations = null;
            }
            if (listExtractGeneratedIrDeclarations == null) {
                return CollectionsKt.emptyList();
            }
            List<IrConstructorCall> list = listExtractGeneratedIrDeclarations;
            ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
            Iterator<T> it = list.iterator();
            while (it.hasNext()) {
                arrayList.add(toFirAnnotation((IrConstructorCall) it.next()));
            }
            return arrayList;
        }

        @Override // org.jetbrains.kotlin.fir.serialization.FirAdditionalMetadataAnnotationsProvider
        public boolean hasGeneratedAnnotationsFor(FirDeclaration declaration) {
            Intrinsics.checkNotNullParameter(declaration, "declaration");
            return !extractGeneratedIrDeclarations(declaration).isEmpty();
        }

        private final List<IrConstructorCall> extractGeneratedIrDeclarations(FirDeclaration declaration) {
            Map map;
            FirFile firFileContainingFile = containingFile(declaration);
            if (firFileContainingFile == null) {
                return CollectionsKt.emptyList();
            }
            String strAsString = UtilsKt.getPackageFqName(firFileContainingFile).child(Name.identifier(firFileContainingFile.getName())).asString();
            Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
            KtSourceElement source = declaration.getSource();
            if (source != null && (map = (Map) Fir2IrAnnotationsFromPluginRegistrar.this.generatedIrDeclarationsByFileByOffset.get(strAsString)) != null) {
                Integer numStartOffsetSkippingComments = ConversionUtilsKt.startOffsetSkippingComments(source);
                List<IrConstructorCall> list = (List) map.get(TuplesKt.to(Integer.valueOf(numStartOffsetSkippingComments != null ? numStartOffsetSkippingComments.intValue() : source.getStartOffset()), Integer.valueOf(source.getEndOffset())));
                return list == null ? CollectionsKt.emptyList() : list;
            }
            return CollectionsKt.emptyList();
        }

        private final FirFile containingFile(FirDeclaration firDeclaration) {
            if (firDeclaration instanceof FirFile) {
                return (FirFile) firDeclaration;
            }
            FirSession session = firDeclaration.getModuleData().getSession();
            return FirProviderUtilsKt.getContainingFile(FirProviderKt.getFirProvider(session), topmostParent(firDeclaration, session).getSymbol());
        }

        private final FirDeclaration topmostParent(FirDeclaration firDeclaration, FirSession firSession) {
            ClassId topmostParentClassId;
            FirClassifierSymbol<?> symbol;
            FirClassifierSymbol<?> symbol2;
            FirDeclaration fir = null;
            if (firDeclaration instanceof FirClassLikeDeclaration) {
                FirClassLikeDeclaration firClassLikeDeclaration = (FirClassLikeDeclaration) firDeclaration;
                if (!FirDeclarationUtilKt.getClassId(firClassLikeDeclaration).isLocal() && (symbol2 = FirSymbolProviderKt.toSymbol(getTopmostParentClassId(FirDeclarationUtilKt.getClassId(firClassLikeDeclaration)), firSession)) != null) {
                    fir = symbol2.getFir();
                }
            } else if (firDeclaration instanceof FirTypeParameter) {
                fir = topmostParent(((FirTypeParameter) firDeclaration).getContainingDeclarationSymbol().getFir(), firSession);
            } else if (firDeclaration instanceof FirValueParameter) {
                fir = topmostParent(((FirValueParameter) firDeclaration).getContainingFunctionSymbol().getFir(), firSession);
            } else {
                if (!(firDeclaration instanceof FirCallableDeclaration)) {
                    throw new IllegalStateException(("Unsupported declaration type: " + firDeclaration).toString());
                }
                ClassId classId = ((FirCallableDeclaration) firDeclaration).getSymbol().getCallableId().getClassId();
                if (classId != null) {
                    if (classId.isLocal()) {
                        classId = null;
                    }
                    if (classId != null && (topmostParentClassId = getTopmostParentClassId(classId)) != null && (symbol = FirSymbolProviderKt.toSymbol(topmostParentClassId, firSession)) != null) {
                        fir = symbol.getFir();
                    }
                }
            }
            return fir == null ? firDeclaration : fir;
        }

        private final ClassId getTopmostParentClassId(ClassId classId) {
            ClassId topmostParentClassId;
            ClassId parentClassId = classId.getParentClassId();
            return (parentClassId == null || (topmostParentClassId = getTopmostParentClassId(parentClassId)) == null) ? classId : topmostParentClassId;
        }

        private final FirAnnotation toFirAnnotation(IrConstructorCall irConstructorCall) {
            FirConstExpression firConstExpressionBuildConstExpression$default;
            ClassId classId = AdditionalIrUtilsKt.getClassId(AdditionalIrUtilsKt.getConstructedClass(irConstructorCall.getSymbol().getOwner()));
            Intrinsics.checkNotNull(classId);
            FirAnnotationBuilder firAnnotationBuilder = new FirAnnotationBuilder();
            ConeClassLikeLookupTagImpl lookupTag = TypeConstructionUtilsKt.toLookupTag(classId);
            firAnnotationBuilder.setAnnotationTypeRef(TypeUtilsKt.toFirResolvedTypeRef$default(TypeConstructionUtilsKt.constructClassType$default(lookupTag, new ConeTypeProjection[0], false, null, 4, null), null, null, 3, null));
            FirAnnotationArgumentMappingBuilder firAnnotationArgumentMappingBuilder = new FirAnnotationArgumentMappingBuilder();
            int valueArgumentsCount = irConstructorCall.getValueArgumentsCount();
            for (int i = 0; i < valueArgumentsCount; i++) {
                Name name2 = irConstructorCall.getSymbol().getOwner().getValueParameters().get(i).getName();
                IrExpression valueArgument = irConstructorCall.getValueArgument(i);
                Intrinsics.checkNotNull(valueArgument, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.expressions.IrConst<*>");
                IrConst irConst = (IrConst) valueArgument;
                Map<Name, FirExpression> mapping = firAnnotationArgumentMappingBuilder.getMapping();
                IrConstKind kind = irConst.getKind();
                if (Intrinsics.areEqual(kind, IrConstKind.Boolean.INSTANCE)) {
                    ConstantValueKind.Boolean r10 = ConstantValueKind.Boolean.INSTANCE;
                    Object value = irConst.getValue();
                    Intrinsics.checkNotNull(value, "null cannot be cast to non-null type kotlin.Boolean");
                    firConstExpressionBuildConstExpression$default = FirConstExpressionBuilderKt.buildConstExpression$default(null, r10, (Boolean) value, null, true, 8, null);
                } else if (Intrinsics.areEqual(kind, IrConstKind.Byte.INSTANCE)) {
                    ConstantValueKind.Byte r11 = ConstantValueKind.Byte.INSTANCE;
                    Object value2 = irConst.getValue();
                    Intrinsics.checkNotNull(value2, "null cannot be cast to non-null type kotlin.Byte");
                    firConstExpressionBuildConstExpression$default = FirConstExpressionBuilderKt.buildConstExpression$default(null, r11, (Byte) value2, null, true, 8, null);
                } else if (Intrinsics.areEqual(kind, IrConstKind.Char.INSTANCE)) {
                    ConstantValueKind.Char r12 = ConstantValueKind.Char.INSTANCE;
                    Object value3 = irConst.getValue();
                    Intrinsics.checkNotNull(value3, "null cannot be cast to non-null type kotlin.Char");
                    firConstExpressionBuildConstExpression$default = FirConstExpressionBuilderKt.buildConstExpression$default(null, r12, (Character) value3, null, true, 8, null);
                } else if (Intrinsics.areEqual(kind, IrConstKind.Double.INSTANCE)) {
                    ConstantValueKind.Double r13 = ConstantValueKind.Double.INSTANCE;
                    Object value4 = irConst.getValue();
                    Intrinsics.checkNotNull(value4, "null cannot be cast to non-null type kotlin.Double");
                    firConstExpressionBuildConstExpression$default = FirConstExpressionBuilderKt.buildConstExpression$default(null, r13, (Double) value4, null, true, 8, null);
                } else if (Intrinsics.areEqual(kind, IrConstKind.Float.INSTANCE)) {
                    ConstantValueKind.Float r14 = ConstantValueKind.Float.INSTANCE;
                    Object value5 = irConst.getValue();
                    Intrinsics.checkNotNull(value5, "null cannot be cast to non-null type kotlin.Float");
                    firConstExpressionBuildConstExpression$default = FirConstExpressionBuilderKt.buildConstExpression$default(null, r14, (Float) value5, null, true, 8, null);
                } else if (Intrinsics.areEqual(kind, IrConstKind.Int.INSTANCE)) {
                    ConstantValueKind.Int r15 = ConstantValueKind.Int.INSTANCE;
                    Object value6 = irConst.getValue();
                    Intrinsics.checkNotNull(value6, "null cannot be cast to non-null type kotlin.Int");
                    firConstExpressionBuildConstExpression$default = FirConstExpressionBuilderKt.buildConstExpression$default(null, r15, (Integer) value6, null, true, 8, null);
                } else if (Intrinsics.areEqual(kind, IrConstKind.Long.INSTANCE)) {
                    ConstantValueKind.Long r16 = ConstantValueKind.Long.INSTANCE;
                    Object value7 = irConst.getValue();
                    Intrinsics.checkNotNull(value7, "null cannot be cast to non-null type kotlin.Long");
                    firConstExpressionBuildConstExpression$default = FirConstExpressionBuilderKt.buildConstExpression$default(null, r16, (Long) value7, null, true, 8, null);
                } else if (Intrinsics.areEqual(kind, IrConstKind.Null.INSTANCE)) {
                    firConstExpressionBuildConstExpression$default = FirConstExpressionBuilderKt.buildConstExpression$default(null, ConstantValueKind.Null.INSTANCE, null, null, true, 8, null);
                } else if (Intrinsics.areEqual(kind, IrConstKind.Short.INSTANCE)) {
                    ConstantValueKind.Short r17 = ConstantValueKind.Short.INSTANCE;
                    Object value8 = irConst.getValue();
                    Intrinsics.checkNotNull(value8, "null cannot be cast to non-null type kotlin.Short");
                    firConstExpressionBuildConstExpression$default = FirConstExpressionBuilderKt.buildConstExpression$default(null, r17, (Short) value8, null, false, 8, null);
                } else {
                    if (!Intrinsics.areEqual(kind, IrConstKind.String.INSTANCE)) {
                        throw new NoWhenBranchMatchedException();
                    }
                    ConstantValueKind.String string = ConstantValueKind.String.INSTANCE;
                    Object value9 = irConst.getValue();
                    Intrinsics.checkNotNull(value9, "null cannot be cast to non-null type kotlin.String");
                    firConstExpressionBuildConstExpression$default = FirConstExpressionBuilderKt.buildConstExpression$default(null, string, (String) value9, null, false, 8, null);
                }
                mapping.put(name2, firConstExpressionBuildConstExpression$default);
            }
            firAnnotationBuilder.setArgumentMapping(firAnnotationArgumentMappingBuilder.build());
            return firAnnotationBuilder.mo6809build();
        }
    }
}
