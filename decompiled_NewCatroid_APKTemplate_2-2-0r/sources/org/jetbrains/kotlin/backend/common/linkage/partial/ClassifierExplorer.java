package org.jetbrains.kotlin.backend.common.linkage.partial;

import androidx.exifinterface.media.ExifInterface;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.Sequence;
import org.jetbrains.kotlin.builtins.PrimitiveType;
import org.jetbrains.kotlin.builtins.StandardNames;
import org.jetbrains.kotlin.builtins.UnsignedType;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.descriptors.ClassKind;
import org.jetbrains.kotlin.descriptors.NotFoundClasses;
import org.jetbrains.kotlin.ir.IrBuiltIns;
import org.jetbrains.kotlin.ir.IrElement;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrConstructor;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationParent;
import org.jetbrains.kotlin.ir.declarations.IrSymbolOwner;
import org.jetbrains.kotlin.ir.declarations.IrTypeParameter;
import org.jetbrains.kotlin.ir.declarations.IrValueParameter;
import org.jetbrains.kotlin.ir.declarations.lazy.IrLazyClass;
import org.jetbrains.kotlin.ir.linkage.partial.ExploredClassifier;
import org.jetbrains.kotlin.ir.symbols.IrClassSymbol;
import org.jetbrains.kotlin.ir.symbols.IrClassifierSymbol;
import org.jetbrains.kotlin.ir.types.IrDynamicType;
import org.jetbrains.kotlin.ir.types.IrErrorType;
import org.jetbrains.kotlin.ir.types.IrSimpleType;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.ir.types.IrTypeArgument;
import org.jetbrains.kotlin.ir.types.IrTypeProjection;
import org.jetbrains.kotlin.ir.util.IrUtilsKt;
import org.jetbrains.kotlin.ir.visitors.IrVisitorsKt;
import org.jetbrains.kotlin.name.Name;
import org.mozilla.javascript.NativeSymbol;

/* JADX INFO: compiled from: ClassifierExplorer.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000z\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010#\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0000\u0018\u0000 02\u00020\u0001:\u00010B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ\u000e\u0010\u001a\u001a\u00020\u001b2\u0006\u0010\u001c\u001a\u00020\u001dJ\u0010\u0010\u001e\u001a\u0004\u0018\u00010\u001f2\u0006\u0010 \u001a\u00020!J\u0010\u0010\"\u001a\u0004\u0018\u00010\u001f2\u0006\u0010#\u001a\u00020$J\u001c\u0010%\u001a\u0004\u0018\u00010\u001f*\u00020&2\f\u0010'\u001a\b\u0012\u0004\u0012\u00020!0(H\u0002J$\u0010)\u001a\u0004\u0018\u00010\u001f*\u00020*2\f\u0010'\u001a\b\u0012\u0004\u0012\u00020!0(2\u0006\u0010+\u001a\u00020,H\u0002J\u001c\u0010-\u001a\u0004\u0018\u00010\u001f*\u00020,2\f\u0010.\u001a\b\u0012\u0004\u0012\u00020\r0\fH\u0002J\u001a\u0010\u001e\u001a\u00020/*\u00020!2\f\u0010'\u001a\b\u0012\u0004\u0012\u00020!0(H\u0002J\u001a\u0010\"\u001a\u00020/*\u00020$2\f\u0010'\u001a\b\u0012\u0004\u0012\u00020!0(H\u0002R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082\u0004¢\u0006\u0002\n\u0000R!\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\r0\f8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u0010\u0010\u0011\u001a\u0004\b\u000e\u0010\u000fR!\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\r0\f8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u0014\u0010\u0011\u001a\u0004\b\u0013\u0010\u000fR\u001b\u0010\u0015\u001a\u00020\u00168BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u0019\u0010\u0011\u001a\u0004\b\u0017\u0010\u0018R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000¨\u00061"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/linkage/partial/ClassifierExplorer;", "", "builtIns", "Lorg/jetbrains/kotlin/ir/IrBuiltIns;", "stubGenerator", "Lorg/jetbrains/kotlin/backend/common/linkage/partial/MissingDeclarationStubGenerator;", "allowErrorTypes", "", "(Lorg/jetbrains/kotlin/ir/IrBuiltIns;Lorg/jetbrains/kotlin/backend/common/linkage/partial/MissingDeclarationStubGenerator;Z)V", "exploredSymbols", "Lorg/jetbrains/kotlin/backend/common/linkage/partial/ExploredClassifiers;", "permittedAnnotationArrayParameterSymbols", "", "Lorg/jetbrains/kotlin/ir/symbols/IrClassSymbol;", "getPermittedAnnotationArrayParameterSymbols", "()Ljava/util/Set;", "permittedAnnotationArrayParameterSymbols$delegate", "Lkotlin/Lazy;", "permittedAnnotationParameterSymbols", "getPermittedAnnotationParameterSymbols", "permittedAnnotationParameterSymbols$delegate", "stdlibModule", "Lorg/jetbrains/kotlin/ir/linkage/partial/PartialLinkageUtils$Module;", "getStdlibModule", "()Lorg/jetbrains/kotlin/ir/linkage/partial/PartialLinkageUtils$Module;", "stdlibModule$delegate", "exploreIrElement", "", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, "Lorg/jetbrains/kotlin/ir/IrElement;", "exploreSymbol", "Lorg/jetbrains/kotlin/ir/linkage/partial/ExploredClassifier$Unusable;", NativeSymbol.TYPE_NAME, "Lorg/jetbrains/kotlin/ir/symbols/IrClassifierSymbol;", "exploreType", ModuleXmlParser.TYPE, "Lorg/jetbrains/kotlin/ir/types/IrType;", "exploreAnnotationConstructor", "Lorg/jetbrains/kotlin/ir/declarations/IrConstructor;", "visitedSymbols", "", "exploreAnnotationConstructorParameter", "Lorg/jetbrains/kotlin/ir/declarations/IrValueParameter;", "annotationClass", "Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "exploreSuperClasses", "superTypeSymbols", "Lorg/jetbrains/kotlin/ir/linkage/partial/ExploredClassifier;", "Companion", "ir.serialization.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ClassifierExplorer {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private final boolean allowErrorTypes;
    private final IrBuiltIns builtIns;
    private final ExploredClassifiers exploredSymbols;

    /* JADX INFO: renamed from: permittedAnnotationArrayParameterSymbols$delegate, reason: from kotlin metadata */
    private final Lazy permittedAnnotationArrayParameterSymbols;

    /* JADX INFO: renamed from: permittedAnnotationParameterSymbols$delegate, reason: from kotlin metadata */
    private final Lazy permittedAnnotationParameterSymbols;

    /* JADX INFO: renamed from: stdlibModule$delegate, reason: from kotlin metadata */
    private final Lazy stdlibModule;
    private final MissingDeclarationStubGenerator stubGenerator;

    /* JADX INFO: compiled from: ClassifierExplorer.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[ClassKind.values().length];
            try {
                iArr[ClassKind.ENUM_CLASS.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[ClassKind.CLASS.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[ClassKind.INTERFACE.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                iArr[ClassKind.ANNOTATION_CLASS.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    public ClassifierExplorer(IrBuiltIns builtIns, MissingDeclarationStubGenerator stubGenerator, boolean z) {
        Intrinsics.checkNotNullParameter(builtIns, "builtIns");
        Intrinsics.checkNotNullParameter(stubGenerator, "stubGenerator");
        this.builtIns = builtIns;
        this.stubGenerator = stubGenerator;
        this.allowErrorTypes = z;
        this.exploredSymbols = new ExploredClassifiers();
        this.permittedAnnotationArrayParameterSymbols = LazyKt.lazy(new Function0<Set<? extends IrClassSymbol>>() { // from class: org.jetbrains.kotlin.backend.common.linkage.partial.ClassifierExplorer$permittedAnnotationArrayParameterSymbols$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Set<? extends IrClassSymbol> invoke() {
                return SetsKt.setOf((Object[]) new IrClassSymbol[]{this.this$0.builtIns.getStringClass(), this.this$0.builtIns.getKClassClass()});
            }
        });
        this.permittedAnnotationParameterSymbols = LazyKt.lazy(new Function0<Set<? extends IrClassSymbol>>() { // from class: org.jetbrains.kotlin.backend.common.linkage.partial.ClassifierExplorer$permittedAnnotationParameterSymbols$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Set<? extends IrClassSymbol> invoke() {
                ClassifierExplorer classifierExplorer = this.this$0;
                Set setCreateSetBuilder = SetsKt.createSetBuilder();
                Set set = setCreateSetBuilder;
                CollectionsKt.addAll(set, classifierExplorer.getPermittedAnnotationArrayParameterSymbols());
                for (PrimitiveType primitiveType : PrimitiveType.values()) {
                    org.jetbrains.kotlin.utils.CollectionsKt.addIfNotNull(set, classifierExplorer.builtIns.findClass(primitiveType.getTypeName(), StandardNames.BUILT_INS_PACKAGE_FQ_NAME));
                    org.jetbrains.kotlin.utils.CollectionsKt.addIfNotNull(set, classifierExplorer.builtIns.findClass(primitiveType.getArrayTypeName(), StandardNames.BUILT_INS_PACKAGE_FQ_NAME));
                }
                for (UnsignedType unsignedType : UnsignedType.values()) {
                    org.jetbrains.kotlin.utils.CollectionsKt.addIfNotNull(set, classifierExplorer.builtIns.findClass(unsignedType.getTypeName(), StandardNames.BUILT_INS_PACKAGE_FQ_NAME));
                    IrBuiltIns irBuiltIns = classifierExplorer.builtIns;
                    Name shortClassName = unsignedType.getArrayClassId().getShortClassName();
                    Intrinsics.checkNotNullExpressionValue(shortClassName, "getShortClassName(...)");
                    org.jetbrains.kotlin.utils.CollectionsKt.addIfNotNull(set, irBuiltIns.findClass(shortClassName, StandardNames.BUILT_INS_PACKAGE_FQ_NAME));
                }
                return SetsKt.build(setCreateSetBuilder);
            }
        });
        this.stdlibModule = LazyKt.lazy(new Function0<org.jetbrains.kotlin.ir.linkage.partial.PartialLinkageUtils.Module>() { // from class: org.jetbrains.kotlin.backend.common.linkage.partial.ClassifierExplorer$stdlibModule$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final org.jetbrains.kotlin.ir.linkage.partial.PartialLinkageUtils.Module invoke() {
                return org.jetbrains.kotlin.ir.linkage.partial.PartialLinkageUtils.Module.INSTANCE.determineModuleFor(this.this$0.builtIns.getAnyClass().getOwner());
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final Set<IrClassSymbol> getPermittedAnnotationArrayParameterSymbols() {
        return (Set) this.permittedAnnotationArrayParameterSymbols.getValue();
    }

    private final Set<IrClassSymbol> getPermittedAnnotationParameterSymbols() {
        return (Set) this.permittedAnnotationParameterSymbols.getValue();
    }

    private final org.jetbrains.kotlin.ir.linkage.partial.PartialLinkageUtils.Module getStdlibModule() {
        return (org.jetbrains.kotlin.ir.linkage.partial.PartialLinkageUtils.Module) this.stdlibModule.getValue();
    }

    public final ExploredClassifier.Unusable exploreType(IrType type) {
        Intrinsics.checkNotNullParameter(type, "type");
        return INSTANCE.asUnusable(exploreType(type, new HashSet()));
    }

    public final ExploredClassifier.Unusable exploreSymbol(IrClassifierSymbol symbol) {
        Intrinsics.checkNotNullParameter(symbol, "symbol");
        return INSTANCE.asUnusable(exploreSymbol(symbol, new HashSet()));
    }

    public final void exploreIrElement(IrElement element) {
        Intrinsics.checkNotNullParameter(element, "element");
        IrVisitorsKt.acceptChildrenVoid(element, new IrElementExplorer(new Function1<IrType, Unit>() { // from class: org.jetbrains.kotlin.backend.common.linkage.partial.ClassifierExplorer.exploreIrElement.1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(IrType irType) {
                invoke2(irType);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(IrType it) {
                Intrinsics.checkNotNullParameter(it, "it");
                ClassifierExplorer.this.exploreType(it, new HashSet());
            }
        }));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final ExploredClassifier exploreType(IrType irType, Set<IrClassifierSymbol> set) {
        ExploredClassifier exploredClassifierAsUnusable;
        if (!(irType instanceof IrSimpleType)) {
            if (irType instanceof IrDynamicType) {
                return ExploredClassifier.Usable.INSTANCE;
            }
            if ((irType instanceof IrErrorType) && this.allowErrorTypes) {
                return ExploredClassifier.Usable.INSTANCE;
            }
            throw new IllegalArgumentException("Unsupported IR type: " + irType.getClass() + ", " + irType);
        }
        IrSimpleType irSimpleType = (IrSimpleType) irType;
        ExploredClassifier.Unusable unusableAsUnusable = INSTANCE.asUnusable(exploreSymbol(irSimpleType.getClassifier(), set));
        if (unusableAsUnusable != null) {
            return unusableAsUnusable;
        }
        Iterator<T> it = irSimpleType.getArguments().iterator();
        do {
            exploredClassifierAsUnusable = null;
            if (!it.hasNext()) {
                break;
            }
            Object next = it.next();
            Companion companion = INSTANCE;
            IrType typeOrNull = companion.getTypeOrNull((IrTypeArgument) next);
            exploredClassifierAsUnusable = companion.asUnusable(typeOrNull != null ? exploreType(typeOrNull, set) : null);
        } while (exploredClassifierAsUnusable == null);
        if (exploredClassifierAsUnusable != null) {
            return exploredClassifierAsUnusable;
        }
        return ExploredClassifier.Usable.INSTANCE;
    }

    /* JADX WARN: Code duplicated, block: B:106:0x011b A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:109:0x015a A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:111:? A[LOOP:2: B:62:0x0128->B:111:?, LOOP_END, SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:46:0x00d9 A[PHI: r4
      0x00d9: PHI (r4v20 org.jetbrains.kotlin.ir.linkage.partial.ExploredClassifier$Unusable) = 
      (r4v6 org.jetbrains.kotlin.ir.linkage.partial.ExploredClassifier$Unusable)
      (r4v23 org.jetbrains.kotlin.ir.linkage.partial.ExploredClassifier$Unusable)
     binds: [B:60:0x011c, B:45:0x00d7] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Code duplicated, block: B:47:0x00dc  */
    /* JADX WARN: Code duplicated, block: B:49:0x00e4  */
    /* JADX WARN: Code duplicated, block: B:50:0x00eb  */
    /* JADX WARN: Code duplicated, block: B:53:0x00f2  */
    /* JADX WARN: Code duplicated, block: B:56:0x0102  */
    /* JADX WARN: Code duplicated, block: B:61:0x011e  */
    /* JADX WARN: Code duplicated, block: B:64:0x012e  */
    /* JADX WARN: Code duplicated, block: B:66:0x013f  */
    /* JADX WARN: Code duplicated, block: B:67:0x0144  */
    /* JADX WARN: Code duplicated, block: B:70:0x0149  */
    /* JADX WARN: Code duplicated, block: B:71:0x014c  */
    /* JADX WARN: Code duplicated, block: B:76:0x015d  */
    /* JADX WARN: Code duplicated, block: B:77:0x0164  */
    private final ExploredClassifier exploreSymbol(IrClassifierSymbol irClassifierSymbol, Set<IrClassifierSymbol> set) {
        ExploredClassifier.Unusable.CanBeRootCause rootCause;
        IrClassSymbol outerClassSymbolIfApplicable;
        ExploredClassifier exploredClassifierExploreSymbol;
        ExploredClassifier.Unusable unusableAsUnusable;
        Iterator<T> it;
        ExploredClassifier.Unusable unusableAsUnusable2;
        IrSimpleType irSimpleTypeAsSimpleType;
        IrClassifierSymbol classifier;
        IrClassSymbol irClassSymbol;
        ExploredClassifier.Unusable unusableAsUnusable3;
        ExploredClassifier exploredClassifier = this.exploredSymbols.get(irClassifierSymbol);
        if (exploredClassifier != null) {
            return exploredClassifier;
        }
        if (!irClassifierSymbol.isBound()) {
            this.stubGenerator.getDeclaration(irClassifierSymbol);
            return this.exploredSymbols.registerUnusable(irClassifierSymbol, new ExploredClassifier.Unusable.MissingClassifier(irClassifierSymbol));
        }
        IrSymbolOwner owner = irClassifierSymbol.getOwner();
        ExploredClassifier.Unusable unusableExploreSuperClasses = null;
        IrLazyClass irLazyClass = owner instanceof IrLazyClass ? (IrLazyClass) owner : null;
        if (irLazyClass != null && ((irLazyClass.getDescriptor() instanceof NotFoundClasses.MockClassDescriptor) || PartialLinkageUtils.INSTANCE.isEffectivelyMissingLazyIrDeclaration(irLazyClass))) {
            return this.exploredSymbols.registerUnusable(irClassifierSymbol, new ExploredClassifier.Unusable.MissingClassifier(irClassifierSymbol));
        }
        if (!set.add(irClassifierSymbol)) {
            return ExploredClassifier.Usable.INSTANCE;
        }
        IrSymbolOwner owner2 = irClassifierSymbol.getOwner();
        if (owner2 instanceof IrClass) {
            org.jetbrains.kotlin.ir.linkage.partial.PartialLinkageUtils.Module.Companion companion = org.jetbrains.kotlin.ir.linkage.partial.PartialLinkageUtils.Module.INSTANCE;
            IrSymbolOwner owner3 = irClassifierSymbol.getOwner();
            Intrinsics.checkNotNull(owner3, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.declarations.IrClass");
            org.jetbrains.kotlin.ir.linkage.partial.PartialLinkageUtils.Module moduleDetermineModuleFor = companion.determineModuleFor((IrClass) owner3);
            if (moduleDetermineModuleFor instanceof org.jetbrains.kotlin.ir.linkage.partial.PartialLinkageUtils.Module.MissingDeclarations) {
                return this.exploredSymbols.registerUnusable(irClassifierSymbol, new ExploredClassifier.Unusable.MissingClassifier(irClassifierSymbol));
            }
            if (!(Intrinsics.areEqual(moduleDetermineModuleFor, getStdlibModule()) ? true : Intrinsics.areEqual(moduleDetermineModuleFor, org.jetbrains.kotlin.ir.linkage.partial.PartialLinkageUtils.Module.SyntheticBuiltInFunctions.INSTANCE))) {
                HashSet hashSet = new HashSet();
                IrClass irClass = (IrClass) owner2;
                Sequence annotationConstructorsIfApplicable = INSTANCE.getAnnotationConstructorsIfApplicable(irClass);
                if (annotationConstructorsIfApplicable == null) {
                    Companion companion2 = INSTANCE;
                    outerClassSymbolIfApplicable = companion2.getOuterClassSymbolIfApplicable(irClass);
                    if (outerClassSymbolIfApplicable != null) {
                        exploredClassifierExploreSymbol = exploreSymbol(outerClassSymbolIfApplicable, set);
                    } else {
                        exploredClassifierExploreSymbol = null;
                    }
                    unusableAsUnusable = companion2.asUnusable(exploredClassifierExploreSymbol);
                    if (unusableAsUnusable == null) {
                        it = irClass.getTypeParameters().iterator();
                        do {
                            if (it.hasNext()) {
                                unusableAsUnusable2 = null;
                                break;
                            }
                            unusableAsUnusable2 = INSTANCE.asUnusable(exploreSymbol(((IrTypeParameter) it.next()).getSymbol(), set));
                        } while (unusableAsUnusable2 == null);
                        if (unusableAsUnusable2 == null) {
                            for (Object obj : irClass.getSuperTypes()) {
                                Companion companion3 = INSTANCE;
                                IrType irType = (IrType) obj;
                                HashSet hashSet2 = hashSet;
                                irSimpleTypeAsSimpleType = companion3.asSimpleType(irType);
                                if (irSimpleTypeAsSimpleType != null) {
                                    classifier = irSimpleTypeAsSimpleType.getClassifier();
                                } else {
                                    classifier = null;
                                }
                                if (classifier instanceof IrClassSymbol) {
                                    irClassSymbol = (IrClassSymbol) classifier;
                                } else {
                                    irClassSymbol = null;
                                }
                                org.jetbrains.kotlin.utils.CollectionsKt.addIfNotNull(hashSet2, irClassSymbol);
                                unusableAsUnusable3 = companion3.asUnusable(exploreType(irType, set));
                                if (unusableAsUnusable3 != null) {
                                    unusableExploreSuperClasses = unusableAsUnusable3;
                                    break;
                                }
                            }
                            if (unusableExploreSuperClasses == null) {
                                unusableExploreSuperClasses = exploreSuperClasses(irClass, hashSet);
                            }
                        } else {
                            unusableExploreSuperClasses = unusableAsUnusable2;
                        }
                    } else {
                        unusableExploreSuperClasses = unusableAsUnusable;
                    }
                } else {
                    Iterator it2 = annotationConstructorsIfApplicable.iterator();
                    do {
                        if (!it2.hasNext()) {
                            unusableAsUnusable2 = null;
                            break;
                        }
                        unusableAsUnusable2 = INSTANCE.asUnusable(exploreAnnotationConstructor((IrConstructor) it2.next(), set));
                    } while (unusableAsUnusable2 == null);
                    if (unusableAsUnusable2 != null) {
                        unusableExploreSuperClasses = unusableAsUnusable2;
                    } else {
                        Companion companion4 = INSTANCE;
                        outerClassSymbolIfApplicable = companion4.getOuterClassSymbolIfApplicable(irClass);
                        if (outerClassSymbolIfApplicable != null) {
                            exploredClassifierExploreSymbol = exploreSymbol(outerClassSymbolIfApplicable, set);
                        } else {
                            exploredClassifierExploreSymbol = null;
                        }
                        unusableAsUnusable = companion4.asUnusable(exploredClassifierExploreSymbol);
                        if (unusableAsUnusable == null) {
                            it = irClass.getTypeParameters().iterator();
                            do {
                                if (it.hasNext()) {
                                    unusableAsUnusable2 = null;
                                    break;
                                }
                                unusableAsUnusable2 = INSTANCE.asUnusable(exploreSymbol(((IrTypeParameter) it.next()).getSymbol(), set));
                            } while (unusableAsUnusable2 == null);
                            if (unusableAsUnusable2 == null) {
                                while (r3.hasNext()) {
                                    Companion companion5 = INSTANCE;
                                    IrType irType2 = (IrType) obj;
                                    HashSet hashSet3 = hashSet;
                                    irSimpleTypeAsSimpleType = companion5.asSimpleType(irType2);
                                    if (irSimpleTypeAsSimpleType != null) {
                                        classifier = irSimpleTypeAsSimpleType.getClassifier();
                                    } else {
                                        classifier = null;
                                    }
                                    if (classifier instanceof IrClassSymbol) {
                                        irClassSymbol = (IrClassSymbol) classifier;
                                    } else {
                                        irClassSymbol = null;
                                    }
                                    org.jetbrains.kotlin.utils.CollectionsKt.addIfNotNull(hashSet3, irClassSymbol);
                                    unusableAsUnusable3 = companion5.asUnusable(exploreType(irType2, set));
                                    if (unusableAsUnusable3 != null) {
                                        unusableExploreSuperClasses = unusableAsUnusable3;
                                        break;
                                    }
                                }
                                if (unusableExploreSuperClasses == null) {
                                    unusableExploreSuperClasses = exploreSuperClasses(irClass, hashSet);
                                }
                            } else {
                                unusableExploreSuperClasses = unusableAsUnusable2;
                            }
                        } else {
                            unusableExploreSuperClasses = unusableAsUnusable;
                        }
                    }
                }
            }
        } else if (owner2 instanceof IrTypeParameter) {
            Iterator<T> it3 = ((IrTypeParameter) owner2).getSuperTypes().iterator();
            while (it3.hasNext()) {
                ExploredClassifier.Unusable unusableAsUnusable4 = INSTANCE.asUnusable(exploreType((IrType) it3.next(), set));
                if (unusableAsUnusable4 != null) {
                    unusableExploreSuperClasses = unusableAsUnusable4;
                    break;
                }
            }
        }
        if (unusableExploreSuperClasses == null) {
            return this.exploredSymbols.registerUsable(irClassifierSymbol);
        }
        if (Intrinsics.areEqual(unusableExploreSuperClasses.getSymbol(), irClassifierSymbol)) {
            return this.exploredSymbols.registerUnusable(irClassifierSymbol, unusableExploreSuperClasses);
        }
        if (unusableExploreSuperClasses instanceof ExploredClassifier.Unusable.DueToOtherClassifier) {
            rootCause = ((ExploredClassifier.Unusable.DueToOtherClassifier) unusableExploreSuperClasses).getRootCause();
        } else {
            if (!(unusableExploreSuperClasses instanceof ExploredClassifier.Unusable.CanBeRootCause)) {
                throw new NoWhenBranchMatchedException();
            }
            rootCause = (ExploredClassifier.Unusable.CanBeRootCause) unusableExploreSuperClasses;
        }
        return this.exploredSymbols.registerUnusable(irClassifierSymbol, new ExploredClassifier.Unusable.DueToOtherClassifier(irClassifierSymbol, rootCause));
    }

    private final ExploredClassifier.Unusable exploreAnnotationConstructor(IrConstructor irConstructor, Set<IrClassifierSymbol> set) {
        for (Object obj : irConstructor.getValueParameters()) {
            Companion companion = INSTANCE;
            IrValueParameter irValueParameter = (IrValueParameter) obj;
            ExploredClassifier.Unusable unusableAsUnusable = companion.asUnusable(exploreType(irValueParameter.getType(), set));
            ExploredClassifier.Unusable unusableAsUnusable2 = companion.asUnusable(unusableAsUnusable != null ? unusableAsUnusable : exploreAnnotationConstructorParameter(irValueParameter, set, IrUtilsKt.getParentAsClass(irConstructor)));
            if (unusableAsUnusable2 != null) {
                return unusableAsUnusable2;
            }
        }
        return null;
    }

    private final ExploredClassifier.Unusable exploreAnnotationConstructorParameter(IrValueParameter irValueParameter, Set<IrClassifierSymbol> set, IrClass irClass) {
        IrSimpleType irSimpleTypeAsSimpleType;
        Companion companion = INSTANCE;
        IrSimpleType irSimpleTypeAsSimpleType2 = companion.asSimpleType(irValueParameter.getType());
        if (irSimpleTypeAsSimpleType2 == null) {
            return null;
        }
        IrClassifierSymbol classifier = irSimpleTypeAsSimpleType2.getClassifier();
        Intrinsics.checkNotNull(classifier, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.symbols.IrClassSymbol");
        IrClassSymbol irClassSymbol = (IrClassSymbol) classifier;
        IrClass owner = irClassSymbol.getOwner();
        if (!IrUtilsKt.isAnnotationClass(owner)) {
            if (IrUtilsKt.isEnumClass(owner) || getPermittedAnnotationParameterSymbols().contains(irClassSymbol)) {
                return null;
            }
            if (Intrinsics.areEqual(irClassSymbol, this.builtIns.getArrayClass())) {
                for (IrTypeArgument irTypeArgument : irSimpleTypeAsSimpleType2.getArguments()) {
                    Companion companion2 = INSTANCE;
                    IrType typeOrNull = companion2.getTypeOrNull(irTypeArgument);
                    if (typeOrNull != null && (irSimpleTypeAsSimpleType = companion2.asSimpleType(typeOrNull)) != null) {
                        IrClassifierSymbol classifier2 = irSimpleTypeAsSimpleType.getClassifier();
                        Intrinsics.checkNotNull(classifier2, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.symbols.IrClassSymbol");
                        IrClassSymbol irClassSymbol2 = (IrClassSymbol) classifier2;
                        IrClass owner2 = irClassSymbol2.getOwner();
                        if (!IrUtilsKt.isAnnotationClass(owner2)) {
                            if (!IrUtilsKt.isEnumClass(owner2) && !getPermittedAnnotationArrayParameterSymbols().contains(irClassSymbol2)) {
                                return new ExploredClassifier.Unusable.AnnotationWithUnacceptableParameter(irClass.getSymbol(), irClassSymbol2);
                            }
                        } else {
                            ExploredClassifier.Unusable unusableAsUnusable = companion2.asUnusable(exploreSymbol(irClassSymbol2, set));
                            if (unusableAsUnusable != null) {
                                return unusableAsUnusable;
                            }
                        }
                    }
                }
            } else {
                return new ExploredClassifier.Unusable.AnnotationWithUnacceptableParameter(irClass.getSymbol(), irClassSymbol);
            }
        } else {
            ExploredClassifier.Unusable unusableAsUnusable2 = companion.asUnusable(exploreSymbol(irClassSymbol, set));
            if (unusableAsUnusable2 != null) {
                return unusableAsUnusable2;
            }
        }
        return null;
    }

    private final ExploredClassifier.Unusable exploreSuperClasses(IrClass irClass, Set<? extends IrClassSymbol> set) {
        ArrayList arrayList;
        if (IrUtilsKt.isInterface(irClass)) {
            if (irClass.getIsExternal()) {
                ArrayList arrayList2 = new ArrayList();
                for (Object obj : set) {
                    IrClassSymbol irClassSymbol = (IrClassSymbol) obj;
                    if (!Intrinsics.areEqual(irClassSymbol, this.builtIns.getAnyClass())) {
                        IrClass owner = irClassSymbol.getOwner();
                        if (!owner.getIsExternal() || (!IrUtilsKt.isInterface(owner) && !IrUtilsKt.isClass(owner))) {
                            arrayList2.add(obj);
                        }
                    }
                }
                arrayList = arrayList2;
            } else {
                ArrayList arrayList3 = new ArrayList();
                for (Object obj2 : set) {
                    IrClassSymbol irClassSymbol2 = (IrClassSymbol) obj2;
                    if (!Intrinsics.areEqual(irClassSymbol2, this.builtIns.getAnyClass()) && !IrUtilsKt.isInterface(irClassSymbol2.getOwner())) {
                        arrayList3.add(obj2);
                    }
                }
                arrayList = arrayList3;
            }
            ArrayList arrayList4 = arrayList;
            if (!arrayList4.isEmpty()) {
                return new ExploredClassifier.Unusable.InvalidInheritance(irClass.getSymbol(), arrayList4);
            }
        } else {
            ArrayList arrayList5 = new ArrayList();
            for (Object obj3 : set) {
                if (!IrUtilsKt.isInterface(((IrClassSymbol) obj3).getOwner())) {
                    arrayList5.add(obj3);
                }
            }
            ArrayList arrayList6 = arrayList5;
            int size = arrayList6.size();
            if (size != 0) {
                if (size == 1) {
                    IrClassSymbol irClassSymbol3 = (IrClassSymbol) CollectionsKt.first((List) arrayList6);
                    if (!Intrinsics.areEqual(irClassSymbol3, this.builtIns.getAnyClass()) && !Intrinsics.areEqual(irClassSymbol3, this.builtIns.getEnumClass())) {
                        IrClass owner2 = irClassSymbol3.getOwner();
                        int i = WhenMappings.$EnumSwitchMapping$0[irClass.getKind().ordinal()];
                        if (i != 1 && i != 3 && i != 4 && !irClass.getIsValue()) {
                            int i2 = WhenMappings.$EnumSwitchMapping$0[owner2.getKind().ordinal()];
                            if (i2 == 1) {
                            }
                        }
                        return new ExploredClassifier.Unusable.InvalidInheritance(irClass.getSymbol(), arrayList6);
                    }
                } else {
                    return new ExploredClassifier.Unusable.InvalidInheritance(irClass.getSymbol(), arrayList6);
                }
            }
        }
        return null;
    }

    /* JADX INFO: compiled from: ClassifierExplorer.kt */
    @Metadata(d1 = {"\u0000N\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u001c\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000e\u0010\u0012\u001a\u0004\u0018\u00010\u0013*\u00020\u000eH\u0002J\u0010\u0010\u0014\u001a\u0004\u0018\u00010\u0015*\u0004\u0018\u00010\u0016H\u0002J1\u0010\u0017\u001a\u0004\u0018\u00010\u0015\"\u0004\b\u0000\u0010\u0018*\b\u0012\u0004\u0012\u0002H\u00180\u00192\u0014\u0010\u001a\u001a\u0010\u0012\u0004\u0012\u0002H\u0018\u0012\u0006\u0012\u0004\u0018\u00010\u00160\u001bH\u0082\bJ1\u0010\u0017\u001a\u0004\u0018\u00010\u0015\"\u0004\b\u0000\u0010\u0018*\b\u0012\u0004\u0012\u0002H\u00180\u00042\u0014\u0010\u001a\u001a\u0010\u0012\u0004\u0012\u0002H\u0018\u0012\u0006\u0012\u0004\u0018\u00010\u00160\u001bH\u0082\bR \u0010\u0003\u001a\n\u0012\u0004\u0012\u00020\u0005\u0018\u00010\u0004*\u00020\u00068BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u0007\u0010\bR\u001a\u0010\t\u001a\u0004\u0018\u00010\n*\u00020\u00068BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u000b\u0010\fR\u001a\u0010\r\u001a\u0004\u0018\u00010\u000e*\u00020\u000f8BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u0010\u0010\u0011¨\u0006\u001c"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/linkage/partial/ClassifierExplorer$Companion;", "", "()V", "annotationConstructorsIfApplicable", "Lkotlin/sequences/Sequence;", "Lorg/jetbrains/kotlin/ir/declarations/IrConstructor;", "Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "getAnnotationConstructorsIfApplicable", "(Lorg/jetbrains/kotlin/ir/declarations/IrClass;)Lkotlin/sequences/Sequence;", "outerClassSymbolIfApplicable", "Lorg/jetbrains/kotlin/ir/symbols/IrClassSymbol;", "getOuterClassSymbolIfApplicable", "(Lorg/jetbrains/kotlin/ir/declarations/IrClass;)Lorg/jetbrains/kotlin/ir/symbols/IrClassSymbol;", "typeOrNull", "Lorg/jetbrains/kotlin/ir/types/IrType;", "Lorg/jetbrains/kotlin/ir/types/IrTypeArgument;", "getTypeOrNull", "(Lorg/jetbrains/kotlin/ir/types/IrTypeArgument;)Lorg/jetbrains/kotlin/ir/types/IrType;", "asSimpleType", "Lorg/jetbrains/kotlin/ir/types/IrSimpleType;", "asUnusable", "Lorg/jetbrains/kotlin/ir/linkage/partial/ExploredClassifier$Unusable;", "Lorg/jetbrains/kotlin/ir/linkage/partial/ExploredClassifier;", "firstUnusable", ExifInterface.GPS_DIRECTION_TRUE, "", "transform", "Lkotlin/Function1;", "ir.serialization.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final Sequence<IrConstructor> getAnnotationConstructorsIfApplicable(IrClass irClass) {
            if (IrUtilsKt.isAnnotationClass(irClass)) {
                return IrUtilsKt.getConstructors(irClass);
            }
            return null;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final IrClassSymbol getOuterClassSymbolIfApplicable(IrClass irClass) {
            if (!irClass.getIsInner() && !IrUtilsKt.isEnumEntry(irClass)) {
                return null;
            }
            IrDeclarationParent parent = irClass.getParent();
            IrClass irClass2 = parent instanceof IrClass ? (IrClass) parent : null;
            if (irClass2 != null) {
                return irClass2.getSymbol();
            }
            return null;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final IrType getTypeOrNull(IrTypeArgument irTypeArgument) {
            IrTypeProjection irTypeProjection = irTypeArgument instanceof IrTypeProjection ? (IrTypeProjection) irTypeArgument : null;
            if (irTypeProjection != null) {
                return irTypeProjection.getType();
            }
            return null;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final IrSimpleType asSimpleType(IrType irType) {
            if (irType instanceof IrSimpleType) {
                return (IrSimpleType) irType;
            }
            return null;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final ExploredClassifier.Unusable asUnusable(ExploredClassifier exploredClassifier) {
            if (exploredClassifier instanceof ExploredClassifier.Unusable) {
                return (ExploredClassifier.Unusable) exploredClassifier;
            }
            return null;
        }
    }
}
