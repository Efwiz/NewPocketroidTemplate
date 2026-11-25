package org.jetbrains.kotlin.backend.jvm.lower;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.collections.SetsKt;
import kotlin.comparisons.ComparisonsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.KtDiagnosticReporterWithImplicitIrBasedContext;
import org.jetbrains.kotlin.backend.common.LowerKt;
import org.jetbrains.kotlin.backend.common.lower.DeclarationIrBuilder;
import org.jetbrains.kotlin.backend.common.lower.LowerUtilsKt;
import org.jetbrains.kotlin.backend.common.phaser.DumperVerifierKt;
import org.jetbrains.kotlin.backend.common.phaser.NamedCompilerPhase;
import org.jetbrains.kotlin.backend.common.phaser.PhaseBuildersKt;
import org.jetbrains.kotlin.backend.jvm.JvmBackendContext;
import org.jetbrains.kotlin.backend.jvm.MultifileFacadeFileEntry;
import org.jetbrains.kotlin.backend.jvm.ir.JvmIrUtilsKt;
import org.jetbrains.kotlin.config.JvmAnalysisFlags;
import org.jetbrains.kotlin.descriptors.DescriptorVisibilities;
import org.jetbrains.kotlin.descriptors.DescriptorVisibility;
import org.jetbrains.kotlin.descriptors.Modality;
import org.jetbrains.kotlin.descriptors.impl.EmptyPackageFragmentDescriptor;
import org.jetbrains.kotlin.ir.IrElement;
import org.jetbrains.kotlin.ir.IrElementBase;
import org.jetbrains.kotlin.ir.builders.ExpressionHelpersKt;
import org.jetbrains.kotlin.ir.builders.IrBlockBodyBuilder;
import org.jetbrains.kotlin.ir.builders.declarations.DeclarationBuildersKt;
import org.jetbrains.kotlin.ir.builders.declarations.IrClassBuilder;
import org.jetbrains.kotlin.ir.builders.declarations.IrFunctionBuilder;
import org.jetbrains.kotlin.ir.declarations.IrAnnotationContainer;
import org.jetbrains.kotlin.ir.declarations.IrAttributeContainer;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrConstructor;
import org.jetbrains.kotlin.ir.declarations.IrDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationOrigin;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationWithName;
import org.jetbrains.kotlin.ir.declarations.IrFactory;
import org.jetbrains.kotlin.ir.declarations.IrField;
import org.jetbrains.kotlin.ir.declarations.IrFile;
import org.jetbrains.kotlin.ir.declarations.IrModuleFragment;
import org.jetbrains.kotlin.ir.declarations.IrProperty;
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction;
import org.jetbrains.kotlin.ir.declarations.impl.IrFileImpl;
import org.jetbrains.kotlin.ir.expressions.IrConstructorCall;
import org.jetbrains.kotlin.ir.symbols.IrPropertySymbol;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.ir.types.IrTypesKt;
import org.jetbrains.kotlin.ir.util.AdditionalIrUtilsKt;
import org.jetbrains.kotlin.ir.util.DeepCopyIrTreeWithSymbols;
import org.jetbrains.kotlin.ir.util.DeepCopySymbolRemapper;
import org.jetbrains.kotlin.ir.util.DeepCopyTypeRemapper;
import org.jetbrains.kotlin.ir.util.IrUtilsKt;
import org.jetbrains.kotlin.ir.util.PatchDeclarationParentsKt;
import org.jetbrains.kotlin.ir.util.TransformKt;
import org.jetbrains.kotlin.ir.visitors.IrElementTransformer;
import org.jetbrains.kotlin.ir.visitors.IrVisitorsKt;
import org.jetbrains.kotlin.load.java.JavaDescriptorVisibilities;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.JvmNames;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.resolve.inline.InlineOnlyKt;
import org.jetbrains.kotlin.resolve.jvm.JvmClassName;
import org.jetbrains.kotlin.resolve.jvm.diagnostics.JvmBackendErrors;

/* JADX INFO: compiled from: GenerateMultifileFacades.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000P\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010%\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\u001a:\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\b2\u0006\u0010\n\u001a\u00020\u00032\u0006\u0010\u000b\u001a\u00020\u00022\u0006\u0010\f\u001a\u00020\r2\u0012\u0010\u000e\u001a\u000e\u0012\u0004\u0012\u00020\u0010\u0012\u0004\u0012\u00020\u00100\u000fH\u0002\u001a\u001e\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u000b\u001a\u00020\u00022\f\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00120\bH\u0002\u001a \u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u00122\u0006\u0010\u0017\u001a\u00020\u00122\u0006\u0010\u0018\u001a\u00020\u0019H\u0002\u001a.\u0010\u001a\u001a\u0004\u0018\u00010\u0010*\u00020\u00102\u0006\u0010\u000b\u001a\u00020\u00022\u0006\u0010\u0017\u001a\u00020\u00122\u0006\u0010\u0018\u001a\u00020\u00192\u0006\u0010\f\u001a\u00020\rH\u0002\u001a\f\u0010\u001b\u001a\u00020\r*\u00020\u001cH\u0002\"0\u0010\u0000\u001a\u001e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00030\u0001j\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u0003`\u0004X\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u001d"}, d2 = {"generateMultifileFacadesPhase", "Lorg/jetbrains/kotlin/backend/common/phaser/NamedCompilerPhase;", "Lorg/jetbrains/kotlin/backend/jvm/JvmBackendContext;", "Lorg/jetbrains/kotlin/ir/declarations/IrModuleFragment;", "Lorg/jetbrains/kotlin/backend/common/phaser/SameTypeNamedCompilerPhase;", "getGenerateMultifileFacadesPhase", "()Lorg/jetbrains/kotlin/backend/common/phaser/NamedCompilerPhase;", "generateMultifileFacades", "", "Lorg/jetbrains/kotlin/ir/declarations/IrFile;", "module", "context", "shouldGeneratePartHierarchy", "", "functionDelegates", "", "Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;", "modifyMultifilePartsForHierarchy", "Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "parts", "moveFieldsOfConstProperties", "", "partClass", "facadeClass", "correspondingProperties", "Lorg/jetbrains/kotlin/backend/jvm/lower/CorrespondingPropertyCache;", "createMultifileDelegateIfNeeded", "shouldMoveToFacade", "Lorg/jetbrains/kotlin/ir/declarations/IrField;", "backend.jvm.lower"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class GenerateMultifileFacadesKt {
    private static final NamedCompilerPhase<JvmBackendContext, IrModuleFragment> generateMultifileFacadesPhase = PhaseBuildersKt.makeCustomPhase(new Function2<JvmBackendContext, IrModuleFragment, Unit>() { // from class: org.jetbrains.kotlin.backend.jvm.lower.GenerateMultifileFacadesKt$generateMultifileFacadesPhase$1
        @Override // kotlin.jvm.functions.Function2
        public /* bridge */ /* synthetic */ Unit invoke(JvmBackendContext jvmBackendContext, IrModuleFragment irModuleFragment) {
            invoke2(jvmBackendContext, irModuleFragment);
            return Unit.INSTANCE;
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
        public final void invoke2(JvmBackendContext context, IrModuleFragment input) {
            Intrinsics.checkNotNullParameter(context, "context");
            Intrinsics.checkNotNullParameter(input, "input");
            LinkedHashMap linkedHashMap = new LinkedHashMap();
            boolean zBooleanValue = ((Boolean) context.getState().getLanguageVersionSettings().getFlag(JvmAnalysisFlags.getInheritMultifileParts())).booleanValue();
            input.getFiles().addAll(GenerateMultifileFacadesKt.generateMultifileFacades(input, context, zBooleanValue, linkedHashMap));
            LowerKt.lower(new UpdateFunctionCallSites(linkedHashMap), input);
            LowerKt.lower(new UpdateConstantFacadePropertyReferences(context, zBooleanValue), input);
            context.getMultifileFacadesToAdd().clear();
            Set<Map.Entry> setEntrySet = linkedHashMap.entrySet();
            Map<IrSimpleFunction, IrSimpleFunction> multifileFacadeMemberToPartMember = context.getMultifileFacadeMemberToPartMember();
            for (Map.Entry entry : setEntrySet) {
                Pair pair = TuplesKt.to((IrSimpleFunction) entry.getValue(), (IrSimpleFunction) entry.getKey());
                multifileFacadeMemberToPartMember.put(pair.getFirst(), pair.getSecond());
            }
        }
    }, "GenerateMultifileFacades", "Generate JvmMultifileClass facades, based on the information provided by FileClassLowering", (504 & 8) != 0 ? SetsKt.emptySet() : SetsKt.setOf(FileClassLoweringKt.getFileClassPhase()), (504 & 16) != 0 ? SetsKt.emptySet() : null, (504 & 32) != 0 ? SetsKt.emptySet() : null, (504 & 64) != 0 ? SetsKt.emptySet() : null, (504 & 128) != 0 ? SetsKt.setOf((Object[]) new Function3[]{DumperVerifierKt.getDefaultDumper(), DumperVerifierKt.getValidationAction()}) : null, (504 & 256) != 0 ? 1 : 0);

    public static final NamedCompilerPhase<JvmBackendContext, IrModuleFragment> getGenerateMultifileFacadesPhase() {
        return generateMultifileFacadesPhase;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    public static final List<IrFile> generateMultifileFacades(IrModuleFragment irModuleFragment, final JvmBackendContext jvmBackendContext, boolean z, Map<IrSimpleFunction, IrSimpleFunction> map) {
        Map<JvmClassName, List<IrClass>> multifileFacadesToAdd = jvmBackendContext.getMultifileFacadesToAdd();
        ArrayList arrayList = new ArrayList(multifileFacadesToAdd.size());
        for (Iterator<Map.Entry<JvmClassName, List<IrClass>>> it = multifileFacadesToAdd.entrySet().iterator(); it.hasNext(); it = it) {
            Map.Entry<JvmClassName, List<IrClass>> next = it.next();
            final JvmClassName key = next.getKey();
            final List<IrClass> listSortedWith = CollectionsKt.sortedWith(next.getValue(), new Comparator() { // from class: org.jetbrains.kotlin.backend.jvm.lower.GenerateMultifileFacadesKt$generateMultifileFacades$lambda$9$$inlined$sortedBy$1
                /* JADX WARN: Multi-variable type inference failed */
                @Override // java.util.Comparator
                public final int compare(T t, T t2) {
                    return ComparisonsKt.compareValues(((IrClass) t).getName(), ((IrClass) t2).getName());
                }
            });
            FqName fqNameWhenAvailable = IrUtilsKt.getFqNameWhenAvailable((IrDeclarationWithName) CollectionsKt.first(listSortedWith));
            Intrinsics.checkNotNull(fqNameWhenAvailable);
            FqName fqNameParent = fqNameWhenAvailable.parent();
            Intrinsics.checkNotNullExpressionValue(fqNameParent, "parent(...)");
            List list = listSortedWith;
            if (!(list instanceof Collection) || !list.isEmpty()) {
                Iterator it2 = list.iterator();
                while (it2.hasNext()) {
                    FqName fqNameWhenAvailable2 = IrUtilsKt.getFqNameWhenAvailable((IrClass) it2.next());
                    Intrinsics.checkNotNull(fqNameWhenAvailable2);
                    if (!Intrinsics.areEqual(fqNameWhenAvailable2.parent(), fqNameParent)) {
                        throw new UnsupportedOperationException("Multi-file parts of a facade with JvmPackageName should all lie in the same Kotlin package:\n  " + CollectionsKt.joinToString$default(list, "\n  ", null, null, 0, null, new Function1<IrClass, CharSequence>() { // from class: org.jetbrains.kotlin.backend.jvm.lower.GenerateMultifileFacadesKt$generateMultifileFacades$1$2
                            {
                                super(1);
                            }

                            @Override // kotlin.jvm.functions.Function1
                            public final CharSequence invoke(IrClass klass) {
                                Intrinsics.checkNotNullParameter(klass, "klass");
                                return "Class " + IrUtilsKt.getFqNameWhenAvailable(klass) + ", JVM name " + jvmBackendContext.getClassNameOverride().get(klass);
                            }
                        }, 30, null));
                    }
                }
            }
            ArrayList arrayList2 = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
            Iterator it3 = list.iterator();
            while (it3.hasNext()) {
                arrayList2.add(JvmIrUtilsKt.getFileParent((IrClass) it3.next()));
            }
            IrFileImpl irFileImpl = new IrFileImpl(new MultifileFacadeFileEntry(key, arrayList2), new EmptyPackageFragmentDescriptor(irModuleFragment.getDescriptor(), fqNameParent), irModuleFragment);
            jvmBackendContext.log(new Function0<String>() { // from class: org.jetbrains.kotlin.backend.jvm.lower.GenerateMultifileFacadesKt$generateMultifileFacades$1$3
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                /* JADX WARN: Multi-variable type inference failed */
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final String invoke() {
                    return "Multifile facade " + key + ":\n  " + CollectionsKt.joinToString$default(listSortedWith, "\n  ", null, null, 0, null, new Function1<IrClass, CharSequence>() { // from class: org.jetbrains.kotlin.backend.jvm.lower.GenerateMultifileFacadesKt$generateMultifileFacades$1$3.1
                        @Override // kotlin.jvm.functions.Function1
                        public final CharSequence invoke(IrClass it4) {
                            Intrinsics.checkNotNullParameter(it4, "it");
                            FqName fqNameWhenAvailable3 = IrUtilsKt.getFqNameWhenAvailable(it4);
                            Intrinsics.checkNotNull(fqNameWhenAvailable3);
                            String strAsString = fqNameWhenAvailable3.asString();
                            Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
                            return strAsString;
                        }
                    }, 30, null) + '\n';
                }
            });
            IrFactory irFactory = jvmBackendContext.getIrFactory();
            IrClassBuilder irClassBuilder = new IrClassBuilder();
            Name nameShortName = key.getFqNameForTopLevelClassMaybeWithDollars().shortName();
            Intrinsics.checkNotNullExpressionValue(nameShortName, "shortName(...)");
            irClassBuilder.setName(nameShortName);
            IrClass irClassBuildClass = DeclarationBuildersKt.buildClass(irFactory, irClassBuilder);
            irClassBuildClass.setParent(irFileImpl);
            IrUtilsKt.createImplicitParameterDeclarationWithWrappedDescriptor(irClassBuildClass);
            irClassBuildClass.setOrigin(IrDeclarationOrigin.JVM_MULTIFILE_CLASS.INSTANCE);
            if (!Intrinsics.areEqual(key.getPackageFqName(), fqNameParent)) {
                jvmBackendContext.getClassNameOverride().put(irClassBuildClass, key);
            }
            if (z) {
                IrClass irClassModifyMultifilePartsForHierarchy = modifyMultifilePartsForHierarchy(jvmBackendContext, listSortedWith);
                irClassBuildClass.setSuperTypes(CollectionsKt.listOf(IrTypesKt.typeWith(irClassModifyMultifilePartsForHierarchy, new IrType[0])));
                IrFactory factory = irClassBuildClass.getFactory();
                IrFunctionBuilder irFunctionBuilder = new IrFunctionBuilder();
                DescriptorVisibility PRIVATE = DescriptorVisibilities.PRIVATE;
                Intrinsics.checkNotNullExpressionValue(PRIVATE, "PRIVATE");
                irFunctionBuilder.setVisibility(PRIVATE);
                irFunctionBuilder.setPrimary(true);
                irFunctionBuilder.setReturnType(IrUtilsKt.getDefaultType(irClassBuildClass));
                IrConstructor irConstructorBuildConstructor = DeclarationBuildersKt.buildConstructor(factory, irFunctionBuilder);
                irClassBuildClass.getDeclarations().add(irConstructorBuildConstructor);
                irConstructorBuildConstructor.setParent(irClassBuildClass);
                DeclarationIrBuilder declarationIrBuilderCreateIrBuilder$default = LowerUtilsKt.createIrBuilder$default(jvmBackendContext, irConstructorBuildConstructor.getSymbol(), 0, 0, 6, (Object) null);
                IrBlockBodyBuilder irBlockBodyBuilder = new IrBlockBodyBuilder(declarationIrBuilderCreateIrBuilder$default.getContext(), declarationIrBuilderCreateIrBuilder$default.getScope(), declarationIrBuilderCreateIrBuilder$default.getStartOffset(), declarationIrBuilderCreateIrBuilder$default.getEndOffset());
                IrConstructor primaryConstructor = IrUtilsKt.getPrimaryConstructor(irClassModifyMultifilePartsForHierarchy);
                Intrinsics.checkNotNull(primaryConstructor);
                irBlockBodyBuilder.unaryPlus(ExpressionHelpersKt.irDelegatingConstructorCall(irBlockBodyBuilder, primaryConstructor));
                irConstructorBuildConstructor.setBody(irBlockBodyBuilder.getIrBlockBody());
            }
            ArrayList arrayList3 = new ArrayList();
            for (Object obj : list) {
                if (!IrUtilsKt.hasAnnotation((IrClass) obj, JvmNames.JVM_SYNTHETIC_ANNOTATION_FQ_NAME)) {
                    arrayList3.add(obj);
                }
            }
            ArrayList arrayList4 = arrayList3;
            Object obj2 = null;
            if (arrayList4.isEmpty()) {
                List<IrConstructorCall> annotations = irClassBuildClass.getAnnotations();
                IrConstructorCall annotation = IrUtilsKt.getAnnotation((IrAnnotationContainer) CollectionsKt.first(listSortedWith), JvmNames.JVM_SYNTHETIC_ANNOTATION_FQ_NAME);
                Intrinsics.checkNotNull(annotation);
                IrConstructorCall irConstructorCall = annotation;
                DeepCopySymbolRemapper deepCopySymbolRemapper = new DeepCopySymbolRemapper(null, 1, null);
                IrVisitorsKt.acceptVoid(irConstructorCall, deepCopySymbolRemapper);
                DeepCopySymbolRemapper deepCopySymbolRemapper2 = deepCopySymbolRemapper;
                IrElement irElementPatchDeclarationParents = PatchDeclarationParentsKt.patchDeclarationParents(irConstructorCall.transform((IrElementTransformer<? super Object>) new DeepCopyIrTreeWithSymbols(deepCopySymbolRemapper2, new DeepCopyTypeRemapper(deepCopySymbolRemapper2)), (Object) null), null);
                if (irElementPatchDeclarationParents != null) {
                    irClassBuildClass.setAnnotations(CollectionsKt.plus((Collection<? extends IrConstructorCall>) annotations, (IrConstructorCall) irElementPatchDeclarationParents));
                } else {
                    throw new NullPointerException("null cannot be cast to non-null type org.jetbrains.kotlin.ir.expressions.IrConstructorCall");
                }
            } else if (arrayList4.size() < listSortedWith.size()) {
                Iterator it4 = arrayList4.iterator();
                while (it4.hasNext()) {
                    IrFile fileParent = JvmIrUtilsKt.getFileParent((IrClass) it4.next());
                    Iterator<T> it5 = fileParent.getAnnotations().iterator();
                    Object obj3 = obj2;
                    boolean z2 = false;
                    while (true) {
                        if (!it5.hasNext()) {
                            if (!z2) {
                                break;
                            }
                            break;
                        }
                        Object next2 = it5.next();
                        if (AdditionalIrUtilsKt.isAnnotationWithEqualFqName((IrConstructorCall) next2, JvmNames.INSTANCE.getJVM_MULTIFILE_CLASS())) {
                            if (!z2) {
                                obj3 = next2;
                                z2 = true;
                            }
                        }
                        obj3 = null;
                        break;
                    }
                    IrElementBase irElementBase = (IrConstructorCall) obj3;
                    KtDiagnosticReporterWithImplicitIrBasedContext ktDiagnosticReporter = jvmBackendContext.getKtDiagnosticReporter();
                    if (irElementBase == null) {
                        irElementBase = fileParent;
                    }
                    ktDiagnosticReporter.at(irElementBase, fileParent).report(JvmBackendErrors.INSTANCE.getNOT_ALL_MULTIFILE_CLASS_PARTS_ARE_JVM_SYNTHETIC());
                    obj2 = null;
                }
            }
            irFileImpl.getDeclarations().add(irClassBuildClass);
            for (IrClass irClass : listSortedWith) {
                Map<IrClass, JvmClassName> multifileFacadeForPart = jvmBackendContext.getMultifileFacadeForPart();
                IrAttributeContainer attributeOwnerId = irClass.getAttributeOwnerId();
                Intrinsics.checkNotNull(attributeOwnerId, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.declarations.IrClass");
                multifileFacadeForPart.put((IrClass) attributeOwnerId, key);
                Map<IrClass, IrClass> multifileFacadeClassForPart = jvmBackendContext.getMultifileFacadeClassForPart();
                IrAttributeContainer attributeOwnerId2 = irClass.getAttributeOwnerId();
                Intrinsics.checkNotNull(attributeOwnerId2, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.declarations.IrClass");
                multifileFacadeClassForPart.put((IrClass) attributeOwnerId2, irClassBuildClass);
                CorrespondingPropertyCache correspondingPropertyCache = new CorrespondingPropertyCache(jvmBackendContext, irClassBuildClass);
                for (IrDeclaration irDeclaration : irClass.getDeclarations()) {
                    if (irDeclaration instanceof IrSimpleFunction) {
                        IrSimpleFunction irSimpleFunction = (IrSimpleFunction) irDeclaration;
                        if (!irSimpleFunction.getIsExternal()) {
                            IrPropertySymbol correspondingPropertySymbol = irSimpleFunction.getCorrespondingPropertySymbol();
                            IrProperty owner = correspondingPropertySymbol != null ? correspondingPropertySymbol.getOwner() : null;
                            if (!IrUtilsKt.hasAnnotation(irDeclaration, InlineOnlyKt.getINLINE_ONLY_ANNOTATION_FQ_NAME())) {
                                if (owner == null || !IrUtilsKt.hasAnnotation(owner, InlineOnlyKt.getINLINE_ONLY_ANNOTATION_FQ_NAME())) {
                                    IrSimpleFunction irSimpleFunctionCreateMultifileDelegateIfNeeded = createMultifileDelegateIfNeeded(irSimpleFunction, jvmBackendContext, irClassBuildClass, correspondingPropertyCache, z);
                                    if (irSimpleFunctionCreateMultifileDelegateIfNeeded != null) {
                                        map.put(irDeclaration, irSimpleFunctionCreateMultifileDelegateIfNeeded);
                                    }
                                }
                            }
                        }
                    }
                }
                moveFieldsOfConstProperties(irClass, irClassBuildClass, correspondingPropertyCache);
            }
            arrayList.add(irFileImpl);
        }
        return arrayList;
    }

    private static final IrClass modifyMultifilePartsForHierarchy(JvmBackendContext jvmBackendContext, List<? extends IrClass> list) {
        for (Pair pair : CollectionsKt.zip(list, CollectionsKt.plus((Collection) CollectionsKt.listOf(jvmBackendContext.getIrBuiltIns().getAnyClass().getOwner()), (Iterable) list.subList(0, list.size() - 1)))) {
            IrClass irClass = (IrClass) pair.component1();
            IrClass irClass2 = (IrClass) pair.component2();
            irClass.setModality(Modality.OPEN);
            DescriptorVisibility PACKAGE_VISIBILITY = JavaDescriptorVisibilities.PACKAGE_VISIBILITY;
            Intrinsics.checkNotNullExpressionValue(PACKAGE_VISIBILITY, "PACKAGE_VISIBILITY");
            irClass.setVisibility(PACKAGE_VISIBILITY);
            irClass.setSuperTypes(CollectionsKt.listOf(IrTypesKt.typeWith(irClass2, new IrType[0])));
            IrFactory factory = irClass.getFactory();
            IrFunctionBuilder irFunctionBuilder = new IrFunctionBuilder();
            irFunctionBuilder.setPrimary(true);
            irFunctionBuilder.setReturnType(IrUtilsKt.getDefaultType(irClass));
            IrConstructor irConstructorBuildConstructor = DeclarationBuildersKt.buildConstructor(factory, irFunctionBuilder);
            irClass.getDeclarations().add(irConstructorBuildConstructor);
            irConstructorBuildConstructor.setParent(irClass);
            DeclarationIrBuilder declarationIrBuilderCreateIrBuilder$default = LowerUtilsKt.createIrBuilder$default(jvmBackendContext, irConstructorBuildConstructor.getSymbol(), 0, 0, 6, (Object) null);
            IrBlockBodyBuilder irBlockBodyBuilder = new IrBlockBodyBuilder(declarationIrBuilderCreateIrBuilder$default.getContext(), declarationIrBuilderCreateIrBuilder$default.getScope(), declarationIrBuilderCreateIrBuilder$default.getStartOffset(), declarationIrBuilderCreateIrBuilder$default.getEndOffset());
            IrConstructor primaryConstructor = IrUtilsKt.getPrimaryConstructor(irClass2);
            Intrinsics.checkNotNull(primaryConstructor);
            irBlockBodyBuilder.unaryPlus(ExpressionHelpersKt.irDelegatingConstructorCall(irBlockBodyBuilder, primaryConstructor));
            irConstructorBuildConstructor.setBody(irBlockBodyBuilder.getIrBlockBody());
        }
        return (IrClass) CollectionsKt.last((List) list);
    }

    /* JADX WARN: Code duplicated, block: B:13:0x004d  */
    private static final void moveFieldsOfConstProperties(IrClass irClass, IrClass irClass2, CorrespondingPropertyCache correspondingPropertyCache) {
        List listEmptyList;
        List<IrDeclaration> declarations = irClass.getDeclarations();
        int iReplaceInPlace = 0;
        while (iReplaceInPlace < declarations.size()) {
            IrDeclaration irDeclaration = declarations.get(iReplaceInPlace);
            if (irDeclaration instanceof IrField) {
                IrField irField = (IrField) irDeclaration;
                if (shouldMoveToFacade(irField)) {
                    PatchDeclarationParentsKt.patchDeclarationParents(irDeclaration, irClass2);
                    irClass2.getDeclarations().add(irDeclaration);
                    IrPropertySymbol correspondingPropertySymbol = irField.getCorrespondingPropertySymbol();
                    if (correspondingPropertySymbol != null) {
                        IrProperty orCopyProperty = correspondingPropertyCache.getOrCopyProperty(correspondingPropertySymbol.getOwner());
                        irField.setCorrespondingPropertySymbol(orCopyProperty.getSymbol());
                        orCopyProperty.setBackingField(irField);
                    }
                    listEmptyList = CollectionsKt.emptyList();
                } else {
                    listEmptyList = null;
                }
            } else {
                listEmptyList = null;
            }
            iReplaceInPlace = TransformKt.replaceInPlace(declarations, listEmptyList, iReplaceInPlace);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean shouldMoveToFacade(IrField irField) {
        IrPropertySymbol correspondingPropertySymbol = irField.getCorrespondingPropertySymbol();
        IrProperty owner = correspondingPropertySymbol != null ? correspondingPropertySymbol.getOwner() : null;
        return (owner == null || !owner.getIsConst() || DescriptorVisibilities.isPrivate(irField.getVisibility())) ? false : true;
    }

    /* JADX WARN: Code restructure failed: missing block: B:29:0x008f, code lost:
    
        if (r0.isConst() == true) goto L30;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private static final org.jetbrains.kotlin.ir.declarations.IrSimpleFunction createMultifileDelegateIfNeeded(org.jetbrains.kotlin.ir.declarations.IrSimpleFunction r11, org.jetbrains.kotlin.backend.jvm.JvmBackendContext r12, org.jetbrains.kotlin.ir.declarations.IrClass r13, org.jetbrains.kotlin.backend.jvm.lower.CorrespondingPropertyCache r14, boolean r15) {
        /*
            Method dump skipped, instruction units count: 489
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: org.jetbrains.kotlin.backend.jvm.lower.GenerateMultifileFacadesKt.createMultifileDelegateIfNeeded(org.jetbrains.kotlin.ir.declarations.IrSimpleFunction, org.jetbrains.kotlin.backend.jvm.JvmBackendContext, org.jetbrains.kotlin.ir.declarations.IrClass, org.jetbrains.kotlin.backend.jvm.lower.CorrespondingPropertyCache, boolean):org.jetbrains.kotlin.ir.declarations.IrSimpleFunction");
    }
}
