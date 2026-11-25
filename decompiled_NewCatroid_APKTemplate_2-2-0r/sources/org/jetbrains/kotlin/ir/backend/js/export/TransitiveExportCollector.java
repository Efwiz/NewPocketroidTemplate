package org.jetbrains.kotlin.ir.backend.js.export;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.ir.backend.js.JsIrBackendContext;
import org.jetbrains.kotlin.ir.backend.js.lower.MoveBodilessDeclarationsToSeparatePlaceKt;
import org.jetbrains.kotlin.ir.backend.js.utils.AnnotationUtilsKt;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrSymbolOwner;
import org.jetbrains.kotlin.ir.declarations.IrTypeParameter;
import org.jetbrains.kotlin.ir.symbols.IrClassSymbol;
import org.jetbrains.kotlin.ir.symbols.IrClassifierSymbol;
import org.jetbrains.kotlin.ir.symbols.IrTypeParameterSymbol;
import org.jetbrains.kotlin.ir.types.IrSimpleType;
import org.jetbrains.kotlin.ir.types.IrStarProjection;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.ir.types.IrTypeArgument;
import org.jetbrains.kotlin.ir.types.IrTypeProjection;
import org.jetbrains.kotlin.ir.types.IrTypeUtilsKt;
import org.jetbrains.kotlin.ir.types.IrTypesKt;
import org.jetbrains.kotlin.ir.util.IrUtilsKt;
import org.jetbrains.kotlin.js.translate.context.Namer;

/* JADX INFO: compiled from: TransitiveExportCollector.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000H\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001:\u0001\u001aB\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0014\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u000b0\n2\u0006\u0010\u000e\u001a\u00020\u000fJ4\u0010\u0010\u001a\u0012\u0012\u0004\u0012\u00020\u0012\u0012\u0004\u0012\u00020\u000b0\u0011j\u0002`\u0013*\u00020\u000f2\u0016\u0010\u0014\u001a\u0012\u0012\u0004\u0012\u00020\u0012\u0012\u0004\u0012\u00020\u000b0\u0011j\u0002`\u0013H\u0002J*\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u000b0\n*\u00020\u000f2\u0016\u0010\u0014\u001a\u0012\u0012\u0004\u0012\u00020\u0012\u0012\u0004\u0012\u00020\u000b0\u0011j\u0002`\u0013H\u0002J*\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u000b0\n*\u00020\u000f2\u0016\u0010\u0014\u001a\u0012\u0012\u0004\u0012\u00020\u0012\u0012\u0004\u0012\u00020\u000b0\u0011j\u0002`\u0013H\u0002J&\u0010\u0017\u001a\u0004\u0018\u00010\u000f*\u00020\u000f2\u0016\u0010\u0014\u001a\u0012\u0012\u0004\u0012\u00020\u0012\u0012\u0004\u0012\u00020\u000b0\u0011j\u0002`\u0013H\u0002J$\u0010\u0018\u001a\u00020\u000b*\u00020\u00192\u0016\u0010\u0014\u001a\u0012\u0012\u0004\u0012\u00020\u0012\u0012\u0004\u0012\u00020\u000b0\u0011j\u0002`\u0013H\u0002R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006R6\u0010\u0007\u001a*\u0012\u0004\u0012\u00020\t\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000b0\n0\bj\u0014\u0012\u0004\u0012\u00020\t\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000b0\n`\fX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u001b"}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/js/export/TransitiveExportCollector;", "", "context", "Lorg/jetbrains/kotlin/ir/backend/js/JsIrBackendContext;", "(Lorg/jetbrains/kotlin/ir/backend/js/JsIrBackendContext;)V", "getContext", "()Lorg/jetbrains/kotlin/ir/backend/js/JsIrBackendContext;", "typesCaches", "Ljava/util/HashMap;", "Lorg/jetbrains/kotlin/ir/backend/js/export/TransitiveExportCollector$ClassWithAppliedArguments;", "", "Lorg/jetbrains/kotlin/ir/types/IrType;", "Lkotlin/collections/HashMap;", "collectSuperTypesTransitiveHierarchyFor", ModuleXmlParser.TYPE, "Lorg/jetbrains/kotlin/ir/types/IrSimpleType;", "calculateTypeSubstitutionMap", "", "Lorg/jetbrains/kotlin/ir/symbols/IrTypeParameterSymbol;", "Lorg/jetbrains/kotlin/ir/backend/js/export/SubstitutionMap;", "typeSubstitutionMap", "collectSuperTypesTransitiveHierarchy", "collectTransitiveHierarchy", "findNearestExportedClass", "getSubstitution", "Lorg/jetbrains/kotlin/ir/types/IrTypeArgument;", "ClassWithAppliedArguments", "backend.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class TransitiveExportCollector {
    private final JsIrBackendContext context;
    private final HashMap<ClassWithAppliedArguments, Set<IrType>> typesCaches;

    public TransitiveExportCollector(JsIrBackendContext context) {
        Intrinsics.checkNotNullParameter(context, "context");
        this.context = context;
        this.typesCaches = new HashMap<>();
    }

    public final JsIrBackendContext getContext() {
        return this.context;
    }

    public final Set<IrType> collectSuperTypesTransitiveHierarchyFor(IrSimpleType type) {
        Intrinsics.checkNotNullParameter(type, "type");
        IrClassSymbol classOrNull = IrTypesKt.getClassOrNull(type);
        if (classOrNull == null) {
            return SetsKt.emptySet();
        }
        HashMap<ClassWithAppliedArguments, Set<IrType>> map = this.typesCaches;
        ClassWithAppliedArguments classWithAppliedArguments = new ClassWithAppliedArguments(classOrNull, type.getArguments());
        Set<IrType> setCollectSuperTypesTransitiveHierarchy = map.get(classWithAppliedArguments);
        if (setCollectSuperTypesTransitiveHierarchy == null) {
            setCollectSuperTypesTransitiveHierarchy = collectSuperTypesTransitiveHierarchy(type, calculateTypeSubstitutionMap(type, MapsKt.emptyMap()));
            map.put(classWithAppliedArguments, setCollectSuperTypesTransitiveHierarchy);
        }
        return setCollectSuperTypesTransitiveHierarchy;
    }

    private final Set<IrType> collectSuperTypesTransitiveHierarchy(IrSimpleType irSimpleType, Map<IrTypeParameterSymbol, ? extends IrType> map) {
        Set<IrType> setEmptySet;
        IrClassSymbol classOrNull = IrTypesKt.getClassOrNull(irSimpleType);
        if (classOrNull == null) {
            return SetsKt.emptySet();
        }
        List<IrType> listSuperTypes = IrTypeUtilsKt.superTypes(classOrNull);
        ArrayList arrayList = new ArrayList();
        for (IrType irType : listSuperTypes) {
            IrSimpleType irSimpleType2 = irType instanceof IrSimpleType ? (IrSimpleType) irType : null;
            if (irSimpleType2 == null || (setEmptySet = collectTransitiveHierarchy(irSimpleType2, map)) == null) {
                setEmptySet = SetsKt.emptySet();
            }
            CollectionsKt.addAll(arrayList, setEmptySet);
        }
        return CollectionsKt.toSet(arrayList);
    }

    private final IrSimpleType findNearestExportedClass(IrSimpleType irSimpleType, Map<IrTypeParameterSymbol, ? extends IrType> map) {
        IrClassifierSymbol classifier = irSimpleType.getClassifier();
        IrClassSymbol irClassSymbol = classifier instanceof IrClassSymbol ? (IrClassSymbol) classifier : null;
        if (irClassSymbol == null) {
            return null;
        }
        if (ExportModelGeneratorKt.isExported(irClassSymbol.getOwner(), this.context)) {
            IrType irTypeSubstitute = org.jetbrains.kotlin.ir.util.IrTypeUtilsKt.substitute(irSimpleType, map);
            Intrinsics.checkNotNull(irTypeSubstitute, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.types.IrSimpleType");
            return (IrSimpleType) irTypeSubstitute;
        }
        for (IrType irType : IrTypeUtilsKt.superTypes(irClassSymbol)) {
            IrSimpleType irSimpleType2 = irType instanceof IrSimpleType ? (IrSimpleType) irType : null;
            IrSimpleType irSimpleTypeFindNearestExportedClass = irSimpleType2 != null ? findNearestExportedClass(irSimpleType2, map) : null;
            if (irSimpleTypeFindNearestExportedClass != null) {
                return irSimpleTypeFindNearestExportedClass;
            }
        }
        return null;
    }

    private final Set<IrType> collectTransitiveHierarchy(IrSimpleType irSimpleType, Map<IrTypeParameterSymbol, ? extends IrType> map) {
        IrSymbolOwner owner = irSimpleType.getClassifier().getOwner();
        IrClass irClass = owner instanceof IrClass ? (IrClass) owner : null;
        if (irClass == null) {
            return SetsKt.emptySet();
        }
        Map<IrTypeParameterSymbol, IrType> mapCalculateTypeSubstitutionMap = calculateTypeSubstitutionMap(irSimpleType, map);
        IrClass irClass2 = irClass;
        if (MoveBodilessDeclarationsToSeparatePlaceKt.isBuiltInClass(irClass2) || MoveBodilessDeclarationsToSeparatePlaceKt.isStdLibClass(irClass2)) {
            return SetsKt.emptySet();
        }
        if (ExportModelGeneratorKt.isExported(irClass2, this.context)) {
            return SetsKt.setOf(org.jetbrains.kotlin.ir.util.IrTypeUtilsKt.substitute(irSimpleType, mapCalculateTypeSubstitutionMap));
        }
        if (AnnotationUtilsKt.isJsImplicitExport(irClass)) {
            IrType[] irTypeArr = new IrType[2];
            irTypeArr[0] = org.jetbrains.kotlin.ir.util.IrTypeUtilsKt.substitute(irSimpleType, map);
            if (IrUtilsKt.isInterface(irClass)) {
                irSimpleType = null;
            }
            irTypeArr[1] = irSimpleType != null ? findNearestExportedClass(irSimpleType, mapCalculateTypeSubstitutionMap) : null;
            return SetsKt.setOfNotNull((Object[]) irTypeArr);
        }
        return collectSuperTypesTransitiveHierarchy(irSimpleType, mapCalculateTypeSubstitutionMap);
    }

    /* JADX WARN: Multi-variable type inference failed */
    private final IrType getSubstitution(IrTypeArgument irTypeArgument, Map<IrTypeParameterSymbol, ? extends IrType> map) {
        if (irTypeArgument instanceof IrType) {
            return org.jetbrains.kotlin.ir.util.IrTypeUtilsKt.substitute((IrType) irTypeArgument, map);
        }
        if (irTypeArgument instanceof IrTypeProjection) {
            return org.jetbrains.kotlin.ir.util.IrTypeUtilsKt.substitute(((IrTypeProjection) irTypeArgument).getType(), map);
        }
        if (irTypeArgument instanceof IrStarProjection) {
            return this.context.getIrBuiltIns().getAnyNType();
        }
        throw new NoWhenBranchMatchedException();
    }

    private final Map<IrTypeParameterSymbol, IrType> calculateTypeSubstitutionMap(IrSimpleType irSimpleType, Map<IrTypeParameterSymbol, ? extends IrType> map) {
        IrClassSymbol classOrNull = IrTypesKt.getClassOrNull(irSimpleType);
        if (classOrNull == null) {
            throw new IllegalStateException(("Unexpected classifier " + irSimpleType.getClassifier() + " for collecting transitive hierarchy").toString());
        }
        List<Pair> listZip = CollectionsKt.zip(classOrNull.getOwner().getTypeParameters(), irSimpleType.getArguments());
        LinkedHashMap linkedHashMap = new LinkedHashMap(RangesKt.coerceAtLeast(MapsKt.mapCapacity(CollectionsKt.collectionSizeOrDefault(listZip, 10)), 16));
        for (Pair pair : listZip) {
            Pair pair2 = TuplesKt.to(((IrTypeParameter) pair.getFirst()).getSymbol(), getSubstitution((IrTypeArgument) pair.getSecond(), map));
            linkedHashMap.put(pair2.getFirst(), pair2.getSecond());
        }
        return MapsKt.plus(map, linkedHashMap);
    }

    /* JADX INFO: compiled from: TransitiveExportCollector.kt */
    @Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0082\b\u0018\u00002\u00020\u0001B\u001b\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005¢\u0006\u0002\u0010\u0007J\t\u0010\f\u001a\u00020\u0003HÆ\u0003J\u000f\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005HÆ\u0003J#\u0010\u000e\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\u000e\b\u0002\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005HÆ\u0001J\u0013\u0010\u000f\u001a\u00020\u00102\b\u0010\u0011\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0012\u001a\u00020\u0013HÖ\u0001J\t\u0010\u0014\u001a\u00020\u0015HÖ\u0001R\u0017\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000b¨\u0006\u0016"}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/js/export/TransitiveExportCollector$ClassWithAppliedArguments;", "", "classSymbol", "Lorg/jetbrains/kotlin/ir/symbols/IrClassSymbol;", "appliedArguments", "", "Lorg/jetbrains/kotlin/ir/types/IrTypeArgument;", "(Lorg/jetbrains/kotlin/ir/symbols/IrClassSymbol;Ljava/util/List;)V", "getAppliedArguments", "()Ljava/util/List;", "getClassSymbol", "()Lorg/jetbrains/kotlin/ir/symbols/IrClassSymbol;", "component1", "component2", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "hashCode", "", "toString", "", "backend.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
    private static final /* data */ class ClassWithAppliedArguments {
        private final List<IrTypeArgument> appliedArguments;
        private final IrClassSymbol classSymbol;

        /* JADX WARN: Multi-variable type inference failed */
        public static /* synthetic */ ClassWithAppliedArguments copy$default(ClassWithAppliedArguments classWithAppliedArguments, IrClassSymbol irClassSymbol, List list, int i, Object obj) {
            if ((i & 1) != 0) {
                irClassSymbol = classWithAppliedArguments.classSymbol;
            }
            if ((i & 2) != 0) {
                list = classWithAppliedArguments.appliedArguments;
            }
            return classWithAppliedArguments.copy(irClassSymbol, list);
        }

        /* JADX INFO: renamed from: component1, reason: from getter */
        public final IrClassSymbol getClassSymbol() {
            return this.classSymbol;
        }

        public final List<IrTypeArgument> component2() {
            return this.appliedArguments;
        }

        public final ClassWithAppliedArguments copy(IrClassSymbol classSymbol, List<? extends IrTypeArgument> appliedArguments) {
            Intrinsics.checkNotNullParameter(classSymbol, "classSymbol");
            Intrinsics.checkNotNullParameter(appliedArguments, "appliedArguments");
            return new ClassWithAppliedArguments(classSymbol, appliedArguments);
        }

        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof ClassWithAppliedArguments)) {
                return false;
            }
            ClassWithAppliedArguments classWithAppliedArguments = (ClassWithAppliedArguments) other;
            return Intrinsics.areEqual(this.classSymbol, classWithAppliedArguments.classSymbol) && Intrinsics.areEqual(this.appliedArguments, classWithAppliedArguments.appliedArguments);
        }

        public int hashCode() {
            return (this.classSymbol.hashCode() * 31) + this.appliedArguments.hashCode();
        }

        public String toString() {
            return "ClassWithAppliedArguments(classSymbol=" + this.classSymbol + ", appliedArguments=" + this.appliedArguments + ')';
        }

        /* JADX WARN: Multi-variable type inference failed */
        public ClassWithAppliedArguments(IrClassSymbol classSymbol, List<? extends IrTypeArgument> appliedArguments) {
            Intrinsics.checkNotNullParameter(classSymbol, "classSymbol");
            Intrinsics.checkNotNullParameter(appliedArguments, "appliedArguments");
            this.classSymbol = classSymbol;
            this.appliedArguments = appliedArguments;
        }

        public final List<IrTypeArgument> getAppliedArguments() {
            return this.appliedArguments;
        }

        public final IrClassSymbol getClassSymbol() {
            return this.classSymbol;
        }
    }
}
