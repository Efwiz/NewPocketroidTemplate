package org.jetbrains.kotlin.ir.backend.js.export;

import com.gaurav.avnc.vnc.XKeySym;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.SequencesKt;
import kotlin.text.StringsKt;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.config.CommonConfigurationKeys;
import org.jetbrains.kotlin.descriptors.ClassKind;
import org.jetbrains.kotlin.descriptors.DescriptorVisibilities;
import org.jetbrains.kotlin.descriptors.Modality;
import org.jetbrains.kotlin.ir.backend.js.JsIrBackendContext;
import org.jetbrains.kotlin.ir.backend.js.JsLoweredDeclarationOrigin;
import org.jetbrains.kotlin.ir.backend.js.lower.ES6AddBoxParameterLoweringKt;
import org.jetbrains.kotlin.ir.backend.js.utils.AnnotationUtilsKt;
import org.jetbrains.kotlin.ir.backend.js.utils.IrJsUtilsKt;
import org.jetbrains.kotlin.ir.backend.js.utils.NameTablesKt;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrConstructor;
import org.jetbrains.kotlin.ir.declarations.IrDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationOrigin;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationParent;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationWithName;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationsKt;
import org.jetbrains.kotlin.ir.declarations.IrEnumEntry;
import org.jetbrains.kotlin.ir.declarations.IrField;
import org.jetbrains.kotlin.ir.declarations.IrFile;
import org.jetbrains.kotlin.ir.declarations.IrModuleFragment;
import org.jetbrains.kotlin.ir.declarations.IrOverridableDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrPackageFragment;
import org.jetbrains.kotlin.ir.declarations.IrPossiblyExternalDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrProperty;
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction;
import org.jetbrains.kotlin.ir.declarations.IrSymbolOwner;
import org.jetbrains.kotlin.ir.declarations.IrTypeParameter;
import org.jetbrains.kotlin.ir.declarations.IrValueParameter;
import org.jetbrains.kotlin.ir.symbols.IrClassSymbol;
import org.jetbrains.kotlin.ir.symbols.IrClassifierSymbol;
import org.jetbrains.kotlin.ir.symbols.IrTypeParameterSymbol;
import org.jetbrains.kotlin.ir.types.IrDynamicType;
import org.jetbrains.kotlin.ir.types.IrSimpleType;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.ir.types.IrTypeArgument;
import org.jetbrains.kotlin.ir.types.IrTypePredicatesKt;
import org.jetbrains.kotlin.ir.types.IrTypeProjection;
import org.jetbrains.kotlin.ir.types.IrTypeUtilsKt;
import org.jetbrains.kotlin.ir.types.IrTypesKt;
import org.jetbrains.kotlin.ir.util.DumpIrTreeOptions;
import org.jetbrains.kotlin.ir.util.IrUtilsKt;
import org.jetbrains.kotlin.ir.util.RenderIrElementKt;
import org.jetbrains.kotlin.load.java.JvmAbi;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.serialization.js.ModuleKind;
import org.jetbrains.kotlin.utils.MemoryOptimizedCollectionUtilKt;

/* JADX INFO: compiled from: ExportModelGenerator.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\u0086\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u001c\n\u0000\n\u0002\u0010 \n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\u0010!\n\u0002\b\u0003\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\b\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\u0010\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u0014H\u0002J&\u0010\u0015\u001a\u0004\u0018\u00010\u00162\u0006\u0010\u0017\u001a\u00020\u00182\u0012\u0010\u0019\u001a\u000e\u0012\u0004\u0012\u00020\u001b\u0012\u0004\u0012\u00020\u001c0\u001aH\u0002J\u0012\u0010\u001d\u001a\u0004\u0018\u00010\u00162\u0006\u0010\u0017\u001a\u00020\u0014H\u0002J:\u0010\u001d\u001a\u00020\u00162\u0006\u0010\u0013\u001a\u00020\u00142\f\u0010\u001e\u001a\b\u0012\u0004\u0012\u00020\u000b0\u001f2\f\u0010 \u001a\b\u0012\u0004\u0012\u00020\u00160!2\f\u0010\"\u001a\b\u0012\u0004\u0012\u00020#0!H\u0002J6\u0010$\u001a\u00020%2\u0006\u0010\u0013\u001a\u00020\u00142\f\u0010\u001e\u001a\b\u0012\u0004\u0012\u00020\u000b0\u001f2\u0016\b\u0002\u0010&\u001a\u0010\u0012\u0004\u0012\u00020\u0018\u0012\u0006\u0012\u0004\u0018\u00010\u00160'H\u0002J\u0012\u0010(\u001a\u0004\u0018\u00010\u00162\u0006\u0010)\u001a\u00020*H\u0002J\u0012\u0010+\u001a\u0004\u0018\u00010\u00162\u0006\u0010,\u001a\u00020-H\u0002J\u001e\u0010.\u001a\u00020\u00162\u0006\u0010\u0013\u001a\u00020\u00142\f\u0010\u001e\u001a\b\u0012\u0004\u0012\u00020\u000b0\u001fH\u0002J \u0010/\u001a\u0004\u0018\u00010\u00162\u0006\u0010\u0013\u001a\u00020\u00142\f\u0010\u001e\u001a\b\u0012\u0004\u0012\u00020\u000b0\u001fH\u0002J$\u00100\u001a\u0002012\u0006\u00102\u001a\u0002032\u0012\u00104\u001a\u000e\u0012\u0004\u0012\u00020\u001b\u0012\u0004\u0012\u00020\u001c0\u001aH\u0002J\u0012\u00105\u001a\u0004\u0018\u00010\u00162\u0006\u00106\u001a\u000207H\u0002J \u00108\u001a\u0004\u0018\u00010\u00162\u0006\u0010\u0013\u001a\u00020\u00142\f\u0010\u001e\u001a\b\u0012\u0004\u0012\u00020\u000b0\u001fH\u0002J\u0010\u00109\u001a\u00020:2\u0006\u0010;\u001a\u00020<H\u0002J\u0012\u0010=\u001a\u0004\u0018\u00010\u00162\u0006\u0010>\u001a\u00020?H\u0002J\u001c\u0010@\u001a\u00020\u00162\u0006\u0010>\u001a\u00020?2\n\b\u0002\u0010A\u001a\u0004\u0018\u00010BH\u0002J\u001a\u0010C\u001a\u00020B2\u0006\u0010D\u001a\u00020\u000b2\b\b\u0002\u0010E\u001a\u00020\u0005H\u0002J\u0010\u0010F\u001a\u00020B2\u0006\u0010D\u001a\u00020GH\u0002J\u0010\u0010H\u001a\u00020I2\u0006\u0010J\u001a\u00020KH\u0002J\u0010\u0010L\u001a\u00020\u00122\u0006\u00106\u001a\u000207H\u0002J\u001e\u0010M\u001a\u00020N2\f\u0010O\u001a\b\u0012\u0004\u0012\u00020P0\u001f2\b\b\u0002\u0010Q\u001a\u00020RJ\u0014\u0010M\u001a\b\u0012\u0004\u0012\u00020\u00160!2\u0006\u0010S\u001a\u00020TJ\u001a\u0010U\u001a\u00020V*\b\u0012\u0004\u0012\u00020\u00160W2\u0006\u0010\u0013\u001a\u00020\u0014H\u0002J(\u0010X\u001a\u00020V*\b\u0012\u0004\u0012\u00020\u00160W2\u0006\u0010\u0013\u001a\u00020\u00142\f\u0010\u001e\u001a\b\u0012\u0004\u0012\u00020\u000b0\u001fH\u0002J\f\u0010Y\u001a\u00020\u0005*\u00020\u000bH\u0002J\u0012\u0010Z\u001a\b\u0012\u0004\u0012\u00020\u000b0[*\u00020\\H\u0002J\f\u0010]\u001a\u00020B*\u00020\u0014H\u0002J\f\u0010^\u001a\u00020_*\u00020\u0018H\u0002J\f\u0010`\u001a\u00020\u0005*\u00020\u0014H\u0002J\f\u0010a\u001a\u00020\u0005*\u00020\u000bH\u0002J\f\u0010b\u001a\u00020\u0005*\u00020<H\u0002J\u001a\u0010c\u001a\u00020\u0005*\u00020\u00142\f\u0010\u001e\u001a\b\u0012\u0004\u0012\u00020\u000b0\u001fH\u0002J\f\u0010d\u001a\u00020\u0005*\u00020\u0014H\u0002J\f\u0010e\u001a\u00020\u0005*\u00020\u000bH\u0002J\u0014\u0010f\u001a\u00020\u0016*\u00020\u00162\u0006\u0010,\u001a\u00020-H\u0002R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u001e\u0010\t\u001a\u0012\u0012\u0004\u0012\u00020\u000b0\nj\b\u0012\u0004\u0012\u00020\u000b`\fX\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u000e\u0010\u000f\u001a\u00020\u0010X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006g"}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/js/export/ExportModelGenerator;", "", "context", "Lorg/jetbrains/kotlin/ir/backend/js/JsIrBackendContext;", "generateNamespacesForPackages", "", "(Lorg/jetbrains/kotlin/ir/backend/js/JsIrBackendContext;Z)V", "getContext", "()Lorg/jetbrains/kotlin/ir/backend/js/JsIrBackendContext;", "currentlyProcessedTypes", "Ljava/util/HashSet;", "Lorg/jetbrains/kotlin/ir/types/IrType;", "Lkotlin/collections/HashSet;", "getGenerateNamespacesForPackages", "()Z", "transitiveExportCollector", "Lorg/jetbrains/kotlin/ir/backend/js/export/TransitiveExportCollector;", "classExportability", "Lorg/jetbrains/kotlin/ir/backend/js/export/Exportability;", "klass", "Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "exportAsEnumMember", "Lorg/jetbrains/kotlin/ir/backend/js/export/ExportedDeclaration;", "candidate", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationWithName;", "enumEntriesToOrdinal", "", "Lorg/jetbrains/kotlin/ir/declarations/IrEnumEntry;", "", "exportClass", "superTypes", "", "members", "", "nestedClasses", "Lorg/jetbrains/kotlin/ir/backend/js/export/ExportedClass;", "exportClassDeclarations", "Lorg/jetbrains/kotlin/ir/backend/js/export/ExportedClassDeclarationsInfo;", "specialProcessing", "Lkotlin/Function1;", "exportConstructor", JvmAbi.ERASED_INLINE_CONSTRUCTOR_NAME, "Lorg/jetbrains/kotlin/ir/declarations/IrConstructor;", "exportDeclaration", "declaration", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;", "exportDeclarationImplicitly", "exportEnumClass", "exportEnumEntry", "Lorg/jetbrains/kotlin/ir/backend/js/export/ExportedProperty;", "field", "Lorg/jetbrains/kotlin/ir/declarations/IrField;", "enumEntries", "exportFunction", "function", "Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;", "exportOrdinaryClass", "exportParameter", "Lorg/jetbrains/kotlin/ir/backend/js/export/ExportedParameter;", "parameter", "Lorg/jetbrains/kotlin/ir/declarations/IrValueParameter;", "exportProperty", "property", "Lorg/jetbrains/kotlin/ir/declarations/IrProperty;", "exportPropertyUnsafely", "specializeType", "Lorg/jetbrains/kotlin/ir/backend/js/export/ExportedType;", "exportType", ModuleXmlParser.TYPE, "shouldCalculateExportedSupertypeForImplicit", "exportTypeArgument", "Lorg/jetbrains/kotlin/ir/types/IrTypeArgument;", "exportTypeParameter", "Lorg/jetbrains/kotlin/ir/backend/js/export/ExportedType$TypeParameter;", "typeParameter", "Lorg/jetbrains/kotlin/ir/declarations/IrTypeParameter;", "functionExportability", "generateExport", "Lorg/jetbrains/kotlin/ir/backend/js/export/ExportedModule;", "modules", "Lorg/jetbrains/kotlin/ir/declarations/IrModuleFragment;", "moduleKind", "Lorg/jetbrains/kotlin/serialization/js/ModuleKind;", "file", "Lorg/jetbrains/kotlin/ir/declarations/IrPackageFragment;", "addMagicInterfaceProperty", "", "", "addMagicPropertyForInterfaceImplementation", "canBeUsedAsSuperTypeOfExportedClasses", "collectSuperTransitiveHierarchy", "", "Lorg/jetbrains/kotlin/ir/types/IrSimpleType;", "generateTagType", "getExportedIdentifier", "", "isOwnMagicPropertyAdded", "shouldAddMagicPropertyOfSuper", "shouldBeExported", "shouldContainImplementationOfMagicProperty", "shouldNotBeImplemented", "shouldPresentInsideImplementsClause", "withAttributesFor", "backend.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ExportModelGenerator {
    private final JsIrBackendContext context;
    private final HashSet<IrType> currentlyProcessedTypes;
    private final boolean generateNamespacesForPackages;
    private final TransitiveExportCollector transitiveExportCollector;

    /* JADX INFO: compiled from: ExportModelGenerator.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[ClassKind.values().length];
            try {
                iArr[ClassKind.ANNOTATION_CLASS.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[ClassKind.OBJECT.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[ClassKind.CLASS.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                iArr[ClassKind.INTERFACE.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                iArr[ClassKind.ENUM_CLASS.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                iArr[ClassKind.ENUM_ENTRY.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    public ExportModelGenerator(JsIrBackendContext context, boolean z) {
        Intrinsics.checkNotNullParameter(context, "context");
        this.context = context;
        this.generateNamespacesForPackages = z;
        this.transitiveExportCollector = new TransitiveExportCollector(context);
        this.currentlyProcessedTypes = new HashSet<>();
    }

    public final JsIrBackendContext getContext() {
        return this.context;
    }

    public final boolean getGenerateNamespacesForPackages() {
        return this.generateNamespacesForPackages;
    }

    public final List<ExportedDeclaration> generateExport(IrPackageFragment file) {
        Intrinsics.checkNotNullParameter(file, "file");
        FqName packageFqName = file.getPackageFqName();
        List<IrDeclaration> declarations = file.getDeclarations();
        ArrayList arrayList = new ArrayList();
        Iterator<T> it = declarations.iterator();
        while (it.hasNext()) {
            CollectionsKt.addAll(arrayList, CollectionsKt.listOfNotNull(exportDeclaration((IrDeclaration) it.next())));
        }
        List<ExportedDeclaration> listCompactIfPossible = org.jetbrains.kotlin.utils.CollectionsKt.compactIfPossible(arrayList);
        if (listCompactIfPossible.isEmpty()) {
            return CollectionsKt.emptyList();
        }
        if (!this.generateNamespacesForPackages || packageFqName.isRoot()) {
            return listCompactIfPossible;
        }
        String string = packageFqName.toString();
        Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
        return CollectionsKt.listOf(new ExportedNamespace(string, listCompactIfPossible));
    }

    public static /* synthetic */ ExportedModule generateExport$default(ExportModelGenerator exportModelGenerator, Iterable iterable, ModuleKind moduleKind, int i, Object obj) {
        if ((i & 2) != 0) {
            moduleKind = ModuleKind.PLAIN;
        }
        return exportModelGenerator.generateExport(iterable, moduleKind);
    }

    public final ExportedModule generateExport(Iterable<? extends IrModuleFragment> modules, ModuleKind moduleKind) {
        Intrinsics.checkNotNullParameter(modules, "modules");
        Intrinsics.checkNotNullParameter(moduleKind, "moduleKind");
        Object obj = this.context.getConfiguration().get(CommonConfigurationKeys.MODULE_NAME);
        Intrinsics.checkNotNull(obj);
        String str = (String) obj;
        Collection<IrFile> collectionValues = this.context.getExternalPackageFragment().values();
        ArrayList arrayList = new ArrayList();
        Iterator<? extends IrModuleFragment> it = modules.iterator();
        while (it.hasNext()) {
            CollectionsKt.addAll(arrayList, it.next().getFiles());
        }
        List listPlus = CollectionsKt.plus((Collection) collectionValues, (Iterable) arrayList);
        ArrayList arrayList2 = new ArrayList();
        Iterator it2 = listPlus.iterator();
        while (it2.hasNext()) {
            CollectionsKt.addAll(arrayList2, generateExport((IrFile) it2.next()));
        }
        return new ExportedModule(str, moduleKind, org.jetbrains.kotlin.utils.CollectionsKt.compactIfPossible(arrayList2));
    }

    private final ExportedDeclaration exportDeclaration(IrDeclaration declaration) {
        ExportedDeclaration exportedDeclarationExportClass;
        IrDeclarationWithName exportCandidate = ExportModelGeneratorKt.getExportCandidate(declaration);
        if (exportCandidate == null || !ExportModelGeneratorKt.shouldDeclarationBeExportedImplicitlyOrExplicitly(exportCandidate, this.context)) {
            return null;
        }
        if (exportCandidate instanceof IrSimpleFunction) {
            exportedDeclarationExportClass = exportFunction((IrSimpleFunction) exportCandidate);
        } else if (exportCandidate instanceof IrProperty) {
            exportedDeclarationExportClass = exportProperty((IrProperty) exportCandidate);
        } else if (exportCandidate instanceof IrClass) {
            exportedDeclarationExportClass = exportClass((IrClass) exportCandidate);
        } else {
            if (!(exportCandidate instanceof IrField)) {
                throw new IllegalStateException(("Can't export declaration " + exportCandidate).toString());
            }
            exportedDeclarationExportClass = null;
        }
        if (exportedDeclarationExportClass != null) {
            return withAttributesFor(exportedDeclarationExportClass, exportCandidate);
        }
        return null;
    }

    private final ExportedDeclaration exportClass(IrClass candidate) {
        Set setPlus = SetsKt.plus((Set) collectSuperTransitiveHierarchy(IrUtilsKt.getDefaultType(candidate)), (Iterable) candidate.getSuperTypes());
        if (IrUtilsKt.isEnumClass(candidate)) {
            return exportEnumClass(candidate, setPlus);
        }
        return exportOrdinaryClass(candidate, setPlus);
    }

    private final ExportedDeclaration exportFunction(IrSimpleFunction function) {
        Exportability exportabilityFunctionExportability = functionExportability(function);
        if (exportabilityFunctionExportability instanceof Exportability.NotNeeded ? true : exportabilityFunctionExportability instanceof Exportability.Implicit) {
            return null;
        }
        if (exportabilityFunctionExportability instanceof Exportability.Prohibited) {
            return new ErrorDeclaration(((Exportability.Prohibited) exportabilityFunctionExportability).getReason());
        }
        if (!(exportabilityFunctionExportability instanceof Exportability.Allowed)) {
            throw new NoWhenBranchMatchedException();
        }
        IrDeclarationParent parent = function.getParent();
        String exportedIdentifier = getExportedIdentifier(function);
        ExportedType exportedTypeExportType$default = exportType$default(this, function.getReturnType(), false, 2, null);
        List<IrTypeParameter> typeParameters = function.getTypeParameters();
        ArrayList arrayList = new ArrayList(typeParameters.size());
        Iterator<T> it = typeParameters.iterator();
        while (it.hasNext()) {
            arrayList.add(exportTypeParameter((IrTypeParameter) it.next()));
        }
        List listCompactIfPossible = org.jetbrains.kotlin.utils.CollectionsKt.compactIfPossible(arrayList);
        boolean z = parent instanceof IrClass;
        boolean zIsStaticMethod = ExportModelGeneratorKt.isStaticMethod(function);
        boolean z2 = z && !IrUtilsKt.isInterface((IrClass) parent) && function.getModality() == Modality.ABSTRACT;
        boolean zAreEqual = Intrinsics.areEqual(function.getVisibility(), DescriptorVisibilities.PROTECTED);
        List listPlus = CollectionsKt.plus((Collection) CollectionsKt.listOfNotNull(function.getExtensionReceiverParameter()), (Iterable) function.getValueParameters());
        ArrayList arrayList2 = new ArrayList();
        for (Object obj : listPlus) {
            if (shouldBeExported((IrValueParameter) obj)) {
                arrayList2.add(obj);
            }
        }
        ArrayList arrayList3 = arrayList2;
        ArrayList arrayList4 = new ArrayList(arrayList3.size());
        Iterator it2 = arrayList3.iterator();
        while (it2.hasNext()) {
            arrayList4.add(exportParameter((IrValueParameter) it2.next()));
        }
        return new ExportedFunction(exportedIdentifier, exportedTypeExportType$default, org.jetbrains.kotlin.utils.CollectionsKt.compactIfPossible(arrayList4), listCompactIfPossible, z, zIsStaticMethod, z2, zAreEqual, function);
    }

    private final ExportedDeclaration exportConstructor(IrConstructor constructor) {
        if (!constructor.getIsPrimary()) {
            return null;
        }
        List listPlus = CollectionsKt.plus((Collection) CollectionsKt.listOfNotNull(constructor.getExtensionReceiverParameter()), (Iterable) constructor.getValueParameters());
        ArrayList arrayList = new ArrayList();
        for (Object obj : listPlus) {
            if (!ES6AddBoxParameterLoweringKt.isBoxParameter((IrValueParameter) obj)) {
                arrayList.add(obj);
            }
        }
        ArrayList arrayList2 = arrayList;
        ArrayList arrayList3 = new ArrayList(arrayList2.size());
        Iterator it = arrayList2.iterator();
        while (it.hasNext()) {
            arrayList3.add(exportParameter((IrValueParameter) it.next()));
        }
        return new ExportedConstructor(org.jetbrains.kotlin.utils.CollectionsKt.compactIfPossible(arrayList3), ExportModelGeneratorKt.toExportedVisibility(constructor.getVisibility()));
    }

    private final ExportedParameter exportParameter(IrValueParameter parameter) {
        String strAsString = parameter.getName().asString();
        Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
        String strSanitizeName = NameTablesKt.sanitizeName(strAsString, false);
        if (ExportModelGeneratorKt.allReservedWords.contains(strSanitizeName)) {
            strSanitizeName = "_" + strSanitizeName;
        }
        return new ExportedParameter(strSanitizeName, exportType$default(this, parameter.getType(), false, 2, null), Intrinsics.areEqual(parameter.getOrigin(), JsLoweredDeclarationOrigin.JS_SHADOWED_DEFAULT_PARAMETER.INSTANCE));
    }

    private final ExportedDeclaration exportProperty(IrProperty property) {
        for (IrSimpleFunction irSimpleFunction : CollectionsKt.listOfNotNull((Object[]) new IrSimpleFunction[]{property.getGetter(), property.getSetter()})) {
            if (irSimpleFunction.getExtensionReceiverParameter() != null) {
                return null;
            }
            if (irSimpleFunction.getIsFakeOverride() && !ExportModelGeneratorKt.isAllowedFakeOverriddenDeclaration(irSimpleFunction, this.context)) {
                return null;
            }
        }
        return exportPropertyUnsafely$default(this, property, null, 2, null);
    }

    static /* synthetic */ ExportedDeclaration exportPropertyUnsafely$default(ExportModelGenerator exportModelGenerator, IrProperty irProperty, ExportedType exportedType, int i, Object obj) {
        if ((i & 2) != 0) {
            exportedType = null;
        }
        return exportModelGenerator.exportPropertyUnsafely(irProperty, exportedType);
    }

    private final ExportedDeclaration exportPropertyUnsafely(IrProperty property, ExportedType specializeType) {
        ExportedType exportedTypeExportType$default;
        IrSimpleFunction getter;
        IrType returnType;
        IrDeclarationParent parent = property.getParent();
        IrClass irClass = parent instanceof IrClass ? (IrClass) parent : null;
        boolean z = IrUtilsKt.isEffectivelyExternal((IrPossiblyExternalDeclaration) property) && (property.getParent() instanceof IrClass) && (getter = property.getGetter()) != null && (returnType = getter.getReturnType()) != null && IrTypeUtilsKt.isNullable(returnType);
        String exportedIdentifier = getExportedIdentifier(property);
        if (specializeType == null) {
            IrSimpleFunction getter2 = property.getGetter();
            Intrinsics.checkNotNull(getter2);
            exportedTypeExportType$default = exportType$default(this, getter2.getReturnType(), false, 2, null);
        } else {
            exportedTypeExportType$default = specializeType;
        }
        boolean isVar = property.getIsVar();
        boolean z2 = irClass != null;
        boolean z3 = (irClass == null || IrUtilsKt.isInterface(irClass) || property.getModality() != Modality.ABSTRACT) ? false : true;
        boolean zAreEqual = Intrinsics.areEqual(property.getVisibility(), DescriptorVisibilities.PROTECTED);
        boolean z4 = irClass != null && IrUtilsKt.isInterface(irClass);
        IrSimpleFunction getter3 = property.getGetter();
        IrSimpleFunction setter = property.getSetter();
        IrSimpleFunction getter4 = property.getGetter();
        if (getter4 == null) {
            getter4 = property.getSetter();
        }
        return new ExportedProperty(exportedIdentifier, exportedTypeExportType$default, isVar, z2, getter4 != null && IrDeclarationsKt.isStaticMethodOfClass(getter4), z3, zAreEqual, z4, getter3, setter, z);
    }

    private final ExportedProperty exportEnumEntry(IrField field, Map<IrEnumEntry, Integer> enumEntries) {
        IrEnumEntry irEnumEntry = this.context.getMapping().getFieldToEnumEntry().get(field);
        if (irEnumEntry == null) {
            throw new IllegalStateException(("Unable to find enum entry for " + IrUtilsKt.getFqNameWhenAvailable(field)).toString());
        }
        IrDeclarationParent parent = field.getParent();
        Intrinsics.checkNotNull(parent, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.declarations.IrClass");
        IrClass irClass = (IrClass) parent;
        String exportedIdentifier = getExportedIdentifier(irEnumEntry);
        ExportedType.IntersectionType intersectionType = new ExportedType.IntersectionType(exportType$default(this, IrUtilsKt.getDefaultType(irClass), false, 2, null), new ExportedType.InlineInterfaceType(CollectionsKt.listOf((Object[]) new ExportedProperty[]{exportEnumEntry$fakeProperty("name", new ExportedType.LiteralType.StringLiteralType(exportedIdentifier)), exportEnumEntry$fakeProperty("ordinal", new ExportedType.LiteralType.NumberLiteralType(Integer.valueOf(((Number) MapsKt.getValue(enumEntries, irEnumEntry)).intValue())))})));
        boolean zAreEqual = Intrinsics.areEqual(irClass.getVisibility(), DescriptorVisibilities.PROTECTED);
        IrSimpleFunction irSimpleFunction = this.context.getMapping().getEnumEntryToGetInstanceFun().get(irEnumEntry);
        if (irSimpleFunction != null) {
            return new ExportedProperty(exportedIdentifier, intersectionType, false, true, true, false, zAreEqual, false, irSimpleFunction, null, false, 1696, null);
        }
        throw new IllegalStateException(("Unable to find get instance fun for " + IrUtilsKt.getFqNameWhenAvailable(field)).toString());
    }

    private static final ExportedProperty exportEnumEntry$fakeProperty(String str, ExportedType exportedType) {
        return new ExportedProperty(str, exportedType, false, true, false, false, false, false, null, null, false, XKeySym.XK_Greek_pi, null);
    }

    private final Exportability classExportability(IrClass klass) {
        if (WhenMappings.$EnumSwitchMapping$0[klass.getKind().ordinal()] == 1) {
            return new Exportability.Prohibited("Class " + IrUtilsKt.getFqNameWhenAvailable(klass) + " with kind: " + klass.getKind());
        }
        if (AnnotationUtilsKt.isJsImplicitExport(klass)) {
            return Exportability.Implicit.INSTANCE;
        }
        if (IrDeclarationsKt.isSingleFieldValueClass(klass)) {
            return new Exportability.Prohibited("Inline class " + IrUtilsKt.getFqNameWhenAvailable(klass));
        }
        return Exportability.Allowed.INSTANCE;
    }

    private final ExportedDeclaration exportDeclarationImplicitly(IrClass klass, Iterable<? extends IrType> superTypes) {
        List<IrTypeParameter> typeParameters = klass.getTypeParameters();
        ArrayList arrayList = new ArrayList(typeParameters.size());
        Iterator<T> it = typeParameters.iterator();
        while (it.hasNext()) {
            arrayList.add(exportTypeParameter((IrTypeParameter) it.next()));
        }
        List listCompactIfPossible = org.jetbrains.kotlin.utils.CollectionsKt.compactIfPossible(arrayList);
        ArrayList arrayList2 = new ArrayList();
        Iterator<? extends IrType> it2 = superTypes.iterator();
        while (true) {
            if (!it2.hasNext()) {
                break;
            }
            IrType next = it2.next();
            IrSymbolOwner owner = IrTypesKt.getClassifierOrFail(next).getOwner();
            IrDeclaration irDeclaration = owner instanceof IrDeclaration ? (IrDeclaration) owner : null;
            if (irDeclaration != null ? ExportModelGeneratorKt.isExportedImplicitlyOrExplicitly(irDeclaration, this.context) : false) {
                arrayList2.add(next);
            }
        }
        ArrayList arrayList3 = arrayList2;
        ArrayList arrayList4 = new ArrayList(CollectionsKt.collectionSizeOrDefault(arrayList3, 10));
        Iterator it3 = arrayList3.iterator();
        while (it3.hasNext()) {
            arrayList4.add(exportType$default(this, (IrType) it3.next(), false, 2, null));
        }
        ArrayList arrayList5 = new ArrayList();
        for (Object obj : arrayList4) {
            if (!(((ExportedType) obj) instanceof ExportedType.ErrorType)) {
                arrayList5.add(obj);
            }
        }
        List listCompactIfPossible2 = org.jetbrains.kotlin.utils.CollectionsKt.compactIfPossible(arrayList5);
        String exportedIdentifier = getExportedIdentifier(klass);
        ExportedClassDeclarationsInfo exportedClassDeclarationsInfoExportClassDeclarations$default = exportClassDeclarations$default(this, klass, superTypes, null, 4, null);
        return new ExportedRegularClass(exportedIdentifier, true, false, CollectionsKt.emptyList(), listCompactIfPossible2, listCompactIfPossible, exportedClassDeclarationsInfoExportClassDeclarations$default.component1(), exportedClassDeclarationsInfoExportClassDeclarations$default.component2(), klass);
    }

    private final ExportedDeclaration exportOrdinaryClass(IrClass klass, Iterable<? extends IrType> superTypes) {
        Exportability exportabilityClassExportability = classExportability(klass);
        if (exportabilityClassExportability instanceof Exportability.Prohibited) {
            throw new IllegalStateException(((Exportability.Prohibited) exportabilityClassExportability).getReason().toString());
        }
        if (Intrinsics.areEqual(exportabilityClassExportability, Exportability.NotNeeded.INSTANCE)) {
            return null;
        }
        if (Intrinsics.areEqual(exportabilityClassExportability, Exportability.Implicit.INSTANCE)) {
            return exportDeclarationImplicitly(klass, superTypes);
        }
        Intrinsics.areEqual(exportabilityClassExportability, Exportability.Allowed.INSTANCE);
        ExportedClassDeclarationsInfo exportedClassDeclarationsInfoExportClassDeclarations$default = exportClassDeclarations$default(this, klass, superTypes, null, 4, null);
        return exportClass(klass, superTypes, exportedClassDeclarationsInfoExportClassDeclarations$default.component1(), exportedClassDeclarationsInfoExportClassDeclarations$default.component2());
    }

    private final ExportedDeclaration exportEnumClass(IrClass klass, Iterable<? extends IrType> superTypes) {
        Exportability exportabilityClassExportability = classExportability(klass);
        if (exportabilityClassExportability instanceof Exportability.Prohibited) {
            throw new IllegalStateException(((Exportability.Prohibited) exportabilityClassExportability).getReason().toString());
        }
        if (Intrinsics.areEqual(exportabilityClassExportability, Exportability.NotNeeded.INSTANCE)) {
            return null;
        }
        if (Intrinsics.areEqual(exportabilityClassExportability, Exportability.Implicit.INSTANCE)) {
            return exportDeclarationImplicitly(klass, superTypes);
        }
        Intrinsics.areEqual(exportabilityClassExportability, Exportability.Allowed.INSTANCE);
        List<IrDeclaration> declarations = klass.getDeclarations();
        ArrayList arrayList = new ArrayList();
        for (Object obj : declarations) {
            if (obj instanceof IrField) {
                arrayList.add(obj);
            }
        }
        ArrayList arrayList2 = new ArrayList();
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            IrEnumEntry irEnumEntry = this.context.getMapping().getFieldToEnumEntry().get((IrField) it.next());
            if (irEnumEntry != null) {
                arrayList2.add(irEnumEntry);
            }
        }
        ArrayList arrayList3 = arrayList2;
        final Map mapKeysToMap = org.jetbrains.kotlin.utils.CollectionsKt.keysToMap(arrayList3, new ExportModelGenerator$exportEnumClass$enumEntriesToOrdinal$1(arrayList3));
        ExportedClassDeclarationsInfo exportedClassDeclarationsInfoExportClassDeclarations = exportClassDeclarations(klass, superTypes, new Function1<IrDeclarationWithName, ExportedDeclaration>() { // from class: org.jetbrains.kotlin.ir.backend.js.export.ExportModelGenerator.exportEnumClass.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final ExportedDeclaration invoke(IrDeclarationWithName candidate) {
                Intrinsics.checkNotNullParameter(candidate, "candidate");
                return ExportModelGenerator.this.exportAsEnumMember(candidate, mapKeysToMap);
            }
        });
        List<ExportedDeclaration> listComponent1 = exportedClassDeclarationsInfoExportClassDeclarations.component1();
        return exportClass(klass, superTypes, MemoryOptimizedCollectionUtilKt.memoryOptimizedPlus(CollectionsKt.listOf(new ExportedConstructor(CollectionsKt.emptyList(), ExportedVisibility.PRIVATE)), (List) listComponent1), exportedClassDeclarationsInfoExportClassDeclarations.component2());
    }

    /* JADX WARN: Multi-variable type inference failed */
    static /* synthetic */ ExportedClassDeclarationsInfo exportClassDeclarations$default(ExportModelGenerator exportModelGenerator, IrClass irClass, Iterable iterable, Function1 function1, int i, Object obj) {
        if ((i & 4) != 0) {
            function1 = new Function1() { // from class: org.jetbrains.kotlin.ir.backend.js.export.ExportModelGenerator.exportClassDeclarations.1
                @Override // kotlin.jvm.functions.Function1
                public final Void invoke(IrDeclarationWithName it) {
                    Intrinsics.checkNotNullParameter(it, "it");
                    return null;
                }
            };
        }
        return exportModelGenerator.exportClassDeclarations(irClass, iterable, function1);
    }

    private final ExportedClassDeclarationsInfo exportClassDeclarations(IrClass klass, Iterable<? extends IrType> superTypes, Function1<? super IrDeclarationWithName, ? extends ExportedDeclaration> specialProcessing) {
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        boolean zIsJsImplicitExport = AnnotationUtilsKt.isJsImplicitExport(klass);
        for (IrDeclaration irDeclaration : klass.getDeclarations()) {
            IrDeclarationWithName exportCandidate = ExportModelGeneratorKt.getExportCandidate(irDeclaration);
            if (exportCandidate != null && (!zIsJsImplicitExport || (exportCandidate instanceof IrClass))) {
                if (ExportModelGeneratorKt.shouldDeclarationBeExportedImplicitlyOrExplicitly(exportCandidate, this.context)) {
                    ExportedDeclaration exportedDeclarationInvoke = specialProcessing.invoke(exportCandidate);
                    if (exportedDeclarationInvoke != null) {
                        arrayList.add(exportedDeclarationInvoke);
                    } else if (exportCandidate instanceof IrSimpleFunction) {
                        ArrayList arrayList3 = arrayList;
                        ExportedDeclaration exportedDeclarationExportFunction = exportFunction((IrSimpleFunction) exportCandidate);
                        org.jetbrains.kotlin.utils.CollectionsKt.addIfNotNull(arrayList3, exportedDeclarationExportFunction != null ? withAttributesFor(exportedDeclarationExportFunction, exportCandidate) : null);
                    } else if (exportCandidate instanceof IrConstructor) {
                        ArrayList arrayList4 = arrayList;
                        ExportedDeclaration exportedDeclarationExportConstructor = exportConstructor((IrConstructor) exportCandidate);
                        org.jetbrains.kotlin.utils.CollectionsKt.addIfNotNull(arrayList4, exportedDeclarationExportConstructor != null ? withAttributesFor(exportedDeclarationExportConstructor, exportCandidate) : null);
                    } else if (exportCandidate instanceof IrProperty) {
                        ArrayList arrayList5 = arrayList;
                        ExportedDeclaration exportedDeclarationExportProperty = exportProperty((IrProperty) exportCandidate);
                        org.jetbrains.kotlin.utils.CollectionsKt.addIfNotNull(arrayList5, exportedDeclarationExportProperty != null ? withAttributesFor(exportedDeclarationExportProperty, exportCandidate) : null);
                    } else if (exportCandidate instanceof IrClass) {
                        if (!IrUtilsKt.isInterface(klass)) {
                            ExportedDeclaration exportedDeclarationExportClass = exportClass((IrClass) exportCandidate);
                            ExportedDeclaration exportedDeclarationWithAttributesFor = exportedDeclarationExportClass != null ? withAttributesFor(exportedDeclarationExportClass, exportCandidate) : null;
                            if (exportedDeclarationWithAttributesFor instanceof ExportedClass) {
                                arrayList2.add(exportedDeclarationWithAttributesFor);
                            } else {
                                org.jetbrains.kotlin.utils.CollectionsKt.addIfNotNull(arrayList, exportedDeclarationWithAttributesFor);
                            }
                        }
                    } else {
                        if (!(exportCandidate instanceof IrField)) {
                            throw new IllegalStateException(("Can't export member declaration " + irDeclaration).toString());
                        }
                        if (!Intrinsics.areEqual(exportCandidate.getOrigin(), IrDeclarationOrigin.FIELD_FOR_OBJECT_INSTANCE.INSTANCE) && !Intrinsics.areEqual(exportCandidate.getOrigin(), IrDeclarationOrigin.FIELD_FOR_OUTER_THIS.INSTANCE)) {
                            ((IrField) exportCandidate).getCorrespondingPropertySymbol();
                        }
                    }
                } else {
                    continue;
                }
            }
        }
        if (shouldContainImplementationOfMagicProperty(klass, superTypes)) {
            addMagicPropertyForInterfaceImplementation(arrayList, klass, superTypes);
        } else if (shouldNotBeImplemented(klass)) {
            addMagicInterfaceProperty(arrayList, klass);
        }
        return new ExportedClassDeclarationsInfo(arrayList, arrayList2);
    }

    private final boolean shouldNotBeImplemented(IrClass irClass) {
        return (IrUtilsKt.isInterface(irClass) && !irClass.getIsExternal()) || AnnotationUtilsKt.isJsImplicitExport(irClass);
    }

    private final boolean shouldBeExported(IrValueParameter irValueParameter) {
        return !Intrinsics.areEqual(irValueParameter.getOrigin(), JsLoweredDeclarationOrigin.JS_SUPER_CONTEXT_PARAMETER.INSTANCE);
    }

    private final boolean shouldContainImplementationOfMagicProperty(IrClass irClass, Iterable<? extends IrType> iterable) {
        IrClass owner;
        if (!irClass.getIsExternal() && (!(iterable instanceof Collection) || !((Collection) iterable).isEmpty())) {
            Iterator<? extends IrType> it = iterable.iterator();
            while (it.hasNext()) {
                IrClassSymbol classOrNull = IrTypesKt.getClassOrNull(it.next());
                if (classOrNull != null && (owner = classOrNull.getOwner()) != null && ((IrUtilsKt.isInterface(owner) && ExportModelGeneratorKt.isExported(owner, this.context)) || AnnotationUtilsKt.isJsImplicitExport(owner))) {
                    return true;
                }
            }
        }
        return false;
    }

    private final void addMagicInterfaceProperty(List<ExportedDeclaration> list, IrClass irClass) {
        list.add(new ExportedProperty("__doNotUseOrImplementIt", generateTagType(irClass), false, true, false, false, false, true, null, null, false, 1904, null));
    }

    private final boolean shouldAddMagicPropertyOfSuper(IrType irType) {
        IrClass owner;
        IrClassSymbol classOrNull = IrTypesKt.getClassOrNull(irType);
        if (classOrNull == null || (owner = classOrNull.getOwner()) == null) {
            return false;
        }
        return isOwnMagicPropertyAdded(owner);
    }

    private final boolean isOwnMagicPropertyAdded(IrClass irClass) {
        IrClass owner;
        if (AnnotationUtilsKt.isJsImplicitExport(irClass)) {
            return true;
        }
        if (!ExportModelGeneratorKt.isExported(irClass, this.context)) {
            return false;
        }
        if (IrUtilsKt.isInterface(irClass) && !irClass.getIsExternal()) {
            return true;
        }
        List<IrType> superTypes = irClass.getSuperTypes();
        if (!(superTypes instanceof Collection) || !superTypes.isEmpty()) {
            Iterator<T> it = superTypes.iterator();
            while (it.hasNext()) {
                IrClassSymbol classOrNull = IrTypesKt.getClassOrNull((IrType) it.next());
                if (classOrNull != null && (owner = classOrNull.getOwner()) != null && isOwnMagicPropertyAdded(owner)) {
                    return true;
                }
            }
        }
        return false;
    }

    private final ExportedType generateTagType(IrClass irClass) {
        String strAsString = IrJsUtilsKt.getFqNameWithJsNameWhenAvailable(irClass, true).asString();
        Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
        return new ExportedType.InlineInterfaceType(CollectionsKt.listOf(new ExportedProperty(strAsString, ExportedType.Primitive.UniqueSymbol.INSTANCE, false, true, false, false, false, true, null, null, false, 1904, null)));
    }

    private final ExportedDeclaration exportClass(IrClass klass, Iterable<? extends IrType> superTypes, List<? extends ExportedDeclaration> members, List<? extends ExportedClass> nestedClasses) {
        List<IrTypeParameter> typeParameters = klass.getTypeParameters();
        ArrayList arrayList = new ArrayList(typeParameters.size());
        Iterator<T> it = typeParameters.iterator();
        while (it.hasNext()) {
            arrayList.add(exportTypeParameter((IrTypeParameter) it.next()));
        }
        List listCompactIfPossible = org.jetbrains.kotlin.utils.CollectionsKt.compactIfPossible(arrayList);
        ArrayList arrayList2 = new ArrayList();
        for (IrType irType : superTypes) {
            IrType irType2 = irType;
            if (!ExportModelGeneratorKt.isInterface(IrTypesKt.getClassifierOrFail(irType2)) && canBeUsedAsSuperTypeOfExportedClasses(irType2)) {
                arrayList2.add(irType);
            }
        }
        ArrayList arrayList3 = arrayList2;
        ArrayList arrayList4 = new ArrayList(CollectionsKt.collectionSizeOrDefault(arrayList3, 10));
        Iterator it2 = arrayList3.iterator();
        while (it2.hasNext()) {
            arrayList4.add(exportType((IrType) it2.next(), false));
        }
        ArrayList arrayList5 = new ArrayList();
        for (Object obj : arrayList4) {
            if (!(((ExportedType) obj) instanceof ExportedType.ErrorType)) {
                arrayList5.add(obj);
            }
        }
        List listCompactIfPossible2 = org.jetbrains.kotlin.utils.CollectionsKt.compactIfPossible(arrayList5);
        ArrayList arrayList6 = new ArrayList();
        for (IrType irType3 : superTypes) {
            if (shouldPresentInsideImplementsClause(irType3)) {
                arrayList6.add(irType3);
            }
        }
        ArrayList arrayList7 = arrayList6;
        ArrayList arrayList8 = new ArrayList(CollectionsKt.collectionSizeOrDefault(arrayList7, 10));
        Iterator it3 = arrayList7.iterator();
        while (it3.hasNext()) {
            arrayList8.add(exportType((IrType) it3.next(), false));
        }
        ArrayList arrayList9 = new ArrayList();
        for (Object obj2 : arrayList8) {
            if (!(((ExportedType) obj2) instanceof ExportedType.ErrorType)) {
                arrayList9.add(obj2);
            }
        }
        List listCompactIfPossible3 = org.jetbrains.kotlin.utils.CollectionsKt.compactIfPossible(arrayList9);
        String exportedIdentifier = getExportedIdentifier(klass);
        if (klass.getKind() == ClassKind.OBJECT) {
            IrSimpleFunction irSimpleFunction = this.context.getMapping().getObjectToGetInstanceFunction().get(klass);
            Intrinsics.checkNotNull(irSimpleFunction);
            return new ExportedObject(exportedIdentifier, listCompactIfPossible2, listCompactIfPossible3, members, nestedClasses, klass, irSimpleFunction);
        }
        return new ExportedRegularClass(exportedIdentifier, IrUtilsKt.isInterface(klass), klass.getModality() == Modality.ABSTRACT || klass.getModality() == Modality.SEALED || IrUtilsKt.isEnumClass(klass), listCompactIfPossible2, listCompactIfPossible3, listCompactIfPossible, members, nestedClasses, klass);
    }

    private final Set<IrType> collectSuperTransitiveHierarchy(IrSimpleType irSimpleType) {
        return this.transitiveExportCollector.collectSuperTypesTransitiveHierarchyFor(irSimpleType);
    }

    private final boolean shouldPresentInsideImplementsClause(IrType irType) {
        IrClassifierSymbol classifierOrFail = IrTypesKt.getClassifierOrFail(irType);
        if (ExportModelGeneratorKt.isInterface(classifierOrFail)) {
            return true;
        }
        IrSymbolOwner owner = classifierOrFail.getOwner();
        IrDeclaration irDeclaration = owner instanceof IrDeclaration ? (IrDeclaration) owner : null;
        return irDeclaration != null && AnnotationUtilsKt.isJsImplicitExport(irDeclaration);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final ExportedDeclaration exportAsEnumMember(IrDeclarationWithName candidate, Map<IrEnumEntry, Integer> enumEntriesToOrdinal) {
        Set<IrEnumEntry> setKeySet = enumEntriesToOrdinal.keySet();
        ExportedType exportedType = null;
        if (candidate instanceof IrProperty) {
            if (!ExportModelGeneratorKt.isAllowedFakeOverriddenDeclaration((IrOverridableDeclaration) candidate, this.context)) {
                return null;
            }
            String exportedIdentifier = getExportedIdentifier(candidate);
            if (!Intrinsics.areEqual(exportedIdentifier, "name")) {
                if (Intrinsics.areEqual(exportedIdentifier, "ordinal")) {
                    ArrayList arrayList = new ArrayList(enumEntriesToOrdinal.size());
                    Iterator<Map.Entry<IrEnumEntry, Integer>> it = enumEntriesToOrdinal.entrySet().iterator();
                    while (it.hasNext()) {
                        arrayList.add(new ExportedType.LiteralType.NumberLiteralType(Integer.valueOf(it.next().getValue().intValue())));
                    }
                    Iterator it2 = arrayList.iterator();
                    if (!it2.hasNext()) {
                        throw new UnsupportedOperationException("Empty collection can't be reduced.");
                    }
                    Object next = it2.next();
                    while (it2.hasNext()) {
                        next = new ExportedType.UnionType((ExportedType) next, (ExportedType) it2.next());
                    }
                    exportedType = (ExportedType) next;
                }
            } else {
                Set<IrEnumEntry> set = setKeySet;
                ArrayList arrayList2 = new ArrayList(CollectionsKt.collectionSizeOrDefault(set, 10));
                Iterator<T> it3 = set.iterator();
                while (it3.hasNext()) {
                    arrayList2.add(getExportedIdentifier((IrEnumEntry) it3.next()));
                }
                ArrayList arrayList3 = arrayList2;
                ArrayList arrayList4 = new ArrayList(CollectionsKt.collectionSizeOrDefault(arrayList3, 10));
                Iterator it4 = arrayList3.iterator();
                while (it4.hasNext()) {
                    arrayList4.add(new ExportedType.LiteralType.StringLiteralType((String) it4.next()));
                }
                Iterator it5 = arrayList4.iterator();
                if (!it5.hasNext()) {
                    throw new UnsupportedOperationException("Empty collection can't be reduced.");
                }
                Object next2 = it5.next();
                while (it5.hasNext()) {
                    next2 = new ExportedType.UnionType((ExportedType) next2, (ExportedType) it5.next());
                }
                exportedType = (ExportedType) next2;
            }
            return exportPropertyUnsafely((IrProperty) candidate, exportedType);
        }
        if (candidate instanceof IrField) {
            return Intrinsics.areEqual(candidate.getOrigin(), IrDeclarationOrigin.FIELD_FOR_ENUM_ENTRY.INSTANCE) ? exportEnumEntry((IrField) candidate, enumEntriesToOrdinal) : null;
        }
        return null;
    }

    private final boolean canBeUsedAsSuperTypeOfExportedClasses(IrType irType) {
        if (!IrTypePredicatesKt.isAny(irType) && !Intrinsics.areEqual(IrTypesKt.getClassifierOrNull(irType), this.context.getIrBuiltIns().getEnumClass())) {
            IrClassifierSymbol classifierOrNull = IrTypesKt.getClassifierOrNull(irType);
            IrSymbolOwner owner = classifierOrNull != null ? classifierOrNull.getOwner() : null;
            IrDeclaration irDeclaration = owner instanceof IrDeclaration ? (IrDeclaration) owner : null;
            if (irDeclaration == null || !AnnotationUtilsKt.isJsImplicitExport(irDeclaration)) {
                return true;
            }
        }
        return false;
    }

    /* JADX WARN: Multi-variable type inference failed */
    private final ExportedType exportTypeArgument(IrTypeArgument type) {
        if (type instanceof IrTypeProjection) {
            return exportType$default(this, ((IrTypeProjection) type).getType(), false, 2, null);
        }
        if (type instanceof IrType) {
            return exportType$default(this, (IrType) type, false, 2, null);
        }
        return new ExportedType.ErrorType("UnknownType " + RenderIrElementKt.render$default(type, (DumpIrTreeOptions) null, 1, (Object) null));
    }

    private final ExportedType.TypeParameter exportTypeParameter(IrTypeParameter typeParameter) {
        ExportedType exportedType;
        List list = SequencesKt.toList(SequencesKt.filter(SequencesKt.map(SequencesKt.filter(CollectionsKt.asSequence(typeParameter.getSuperTypes()), new Function1<IrType, Boolean>() { // from class: org.jetbrains.kotlin.ir.backend.js.export.ExportModelGenerator$exportTypeParameter$constraint$1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final Boolean invoke(IrType it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return Boolean.valueOf(!Intrinsics.areEqual(it, this.this$0.getContext().getIrBuiltIns().getAnyNType()));
            }
        }), new Function1<IrType, ExportedType>() { // from class: org.jetbrains.kotlin.ir.backend.js.export.ExportModelGenerator$exportTypeParameter$constraint$2
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final ExportedType invoke(IrType it) {
                Intrinsics.checkNotNullParameter(it, "it");
                ExportedType exportedTypeExportType$default = ExportModelGenerator.exportType$default(this.this$0, it, false, 2, null);
                if (!(exportedTypeExportType$default instanceof ExportedType.ImplicitlyExportedType)) {
                    return exportedTypeExportType$default;
                }
                ExportedType.ImplicitlyExportedType implicitlyExportedType = (ExportedType.ImplicitlyExportedType) exportedTypeExportType$default;
                return Intrinsics.areEqual(implicitlyExportedType.getExportedSupertype(), ExportedType.Primitive.Any.INSTANCE) ? ExportedType.ImplicitlyExportedType.copy$default(implicitlyExportedType, null, ExportedType.Primitive.Unknown.INSTANCE, 1, null) : exportedTypeExportType$default;
            }
        }), new Function1<ExportedType, Boolean>() { // from class: org.jetbrains.kotlin.ir.backend.js.export.ExportModelGenerator$exportTypeParameter$constraint$3
            @Override // kotlin.jvm.functions.Function1
            public final Boolean invoke(ExportedType it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return Boolean.valueOf(!(it instanceof ExportedType.ErrorType));
            }
        }));
        String identifier = typeParameter.getName().getIdentifier();
        Intrinsics.checkNotNullExpressionValue(identifier, "getIdentifier(...)");
        int size = list.size();
        if (size == 0) {
            exportedType = null;
        } else if (size == 1) {
            exportedType = (ExportedType) CollectionsKt.single(list);
        } else {
            Iterator it = list.iterator();
            if (!it.hasNext()) {
                throw new UnsupportedOperationException("Empty collection can't be reduced.");
            }
            Object next = it.next();
            while (it.hasNext()) {
                next = new ExportedType.IntersectionType((ExportedType) next, (ExportedType) it.next());
            }
            exportedType = (ExportedType) next;
        }
        return new ExportedType.TypeParameter(identifier, exportedType);
    }

    private final ExportedDeclaration withAttributesFor(ExportedDeclaration exportedDeclaration, IrDeclaration irDeclaration) {
        String deprecated = AnnotationUtilsKt.getDeprecated(irDeclaration);
        if (deprecated != null) {
            exportedDeclaration.getAttributes().add(new ExportedAttribute.DeprecatedAttribute(deprecated));
        }
        return exportedDeclaration;
    }

    static /* synthetic */ ExportedType exportType$default(ExportModelGenerator exportModelGenerator, IrType irType, boolean z, int i, Object obj) {
        if ((i & 2) != 0) {
            z = true;
        }
        return exportModelGenerator.exportType(irType, z);
    }

    private final ExportedType exportType(IrType type, boolean shouldCalculateExportedSupertypeForImplicit) {
        ExportedType.ErrorType errorType;
        ExportedType.TypeParameter typeParameterWithImplicitlyExported;
        if ((type instanceof IrDynamicType) || this.currentlyProcessedTypes.contains(type)) {
            return ExportedType.Primitive.Any.INSTANCE;
        }
        ExportedType.Primitive.Any any = null;
        if (!(type instanceof IrSimpleType)) {
            return new ExportedType.ErrorType("NonSimpleType " + RenderIrElementKt.render$default(type, (DumpIrTreeOptions) null, 1, (Object) null));
        }
        this.currentlyProcessedTypes.add(type);
        IrSimpleType irSimpleType = (IrSimpleType) type;
        IrClassifierSymbol classifier = irSimpleType.getClassifier();
        boolean zIsMarkedNullable = IrTypePredicatesKt.isMarkedNullable(irSimpleType);
        IrType irTypeMakeNotNull = IrTypesKt.makeNotNull(type);
        Intrinsics.checkNotNull(irTypeMakeNotNull, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.types.IrSimpleType");
        IrSimpleType irSimpleType2 = (IrSimpleType) irTypeMakeNotNull;
        IrSimpleType irSimpleType3 = irSimpleType2;
        if (IrTypePredicatesKt.isBoolean(irSimpleType3)) {
            typeParameterWithImplicitlyExported = ExportedType.Primitive.Boolean.INSTANCE;
        } else if (IrTypePredicatesKt.isPrimitiveType$default(irSimpleType3, false, 1, null) && !IrTypePredicatesKt.isLong(irSimpleType3) && !IrTypePredicatesKt.isChar(irSimpleType3)) {
            typeParameterWithImplicitlyExported = ExportedType.Primitive.Number.INSTANCE;
        } else if (IrTypePredicatesKt.isByteArray(irSimpleType3)) {
            typeParameterWithImplicitlyExported = ExportedType.Primitive.ByteArray.INSTANCE;
        } else if (IrTypePredicatesKt.isShortArray(irSimpleType3)) {
            typeParameterWithImplicitlyExported = ExportedType.Primitive.ShortArray.INSTANCE;
        } else if (IrTypePredicatesKt.isIntArray(irSimpleType3)) {
            typeParameterWithImplicitlyExported = ExportedType.Primitive.IntArray.INSTANCE;
        } else if (IrTypePredicatesKt.isFloatArray(irSimpleType3)) {
            typeParameterWithImplicitlyExported = ExportedType.Primitive.FloatArray.INSTANCE;
        } else if (IrTypePredicatesKt.isDoubleArray(irSimpleType3)) {
            typeParameterWithImplicitlyExported = ExportedType.Primitive.DoubleArray.INSTANCE;
        } else if (IrTypePredicatesKt.isBooleanArray(irSimpleType3)) {
            typeParameterWithImplicitlyExported = new ExportedType.ErrorType("BooleanArray");
        } else if (IrTypePredicatesKt.isLongArray(irSimpleType3)) {
            typeParameterWithImplicitlyExported = new ExportedType.ErrorType("LongArray");
        } else if (IrTypePredicatesKt.isCharArray(irSimpleType3)) {
            typeParameterWithImplicitlyExported = new ExportedType.ErrorType("CharArray");
        } else if (IrTypePredicatesKt.isString(irSimpleType3)) {
            typeParameterWithImplicitlyExported = ExportedType.Primitive.String.INSTANCE;
        } else if (org.jetbrains.kotlin.ir.util.IrTypeUtilsKt.isThrowable(irSimpleType3)) {
            typeParameterWithImplicitlyExported = ExportedType.Primitive.Throwable.INSTANCE;
        } else if (IrTypePredicatesKt.isAny(irSimpleType3)) {
            typeParameterWithImplicitlyExported = ExportedType.Primitive.Any.INSTANCE;
        } else if (IrTypePredicatesKt.isUnit(irSimpleType3)) {
            typeParameterWithImplicitlyExported = ExportedType.Primitive.Unit.INSTANCE;
        } else if (IrTypePredicatesKt.isNothing(irSimpleType3)) {
            typeParameterWithImplicitlyExported = ExportedType.Primitive.Nothing.INSTANCE;
        } else if (IrTypePredicatesKt.isArray(irSimpleType3)) {
            typeParameterWithImplicitlyExported = new ExportedType.Array(exportTypeArgument(irSimpleType2.getArguments().get(0)));
        } else if (org.jetbrains.kotlin.ir.util.IrTypeUtilsKt.isSuspendFunction(irSimpleType3)) {
            typeParameterWithImplicitlyExported = new ExportedType.ErrorType("Suspend functions are not supported");
        } else if (org.jetbrains.kotlin.ir.util.IrTypeUtilsKt.isFunction(irSimpleType3)) {
            List listDropLast = CollectionsKt.dropLast(irSimpleType2.getArguments(), 1);
            ArrayList arrayList = new ArrayList(listDropLast.size());
            Iterator it = listDropLast.iterator();
            while (it.hasNext()) {
                arrayList.add(exportTypeArgument((IrTypeArgument) it.next()));
            }
            typeParameterWithImplicitlyExported = new ExportedType.Function(org.jetbrains.kotlin.utils.CollectionsKt.compactIfPossible(arrayList), exportTypeArgument((IrTypeArgument) CollectionsKt.last((List) irSimpleType2.getArguments())));
        } else if (classifier instanceof IrTypeParameterSymbol) {
            String identifier = ((IrTypeParameterSymbol) classifier).getOwner().getName().getIdentifier();
            Intrinsics.checkNotNullExpressionValue(identifier, "getIdentifier(...)");
            typeParameterWithImplicitlyExported = new ExportedType.TypeParameter(identifier, null, 2, null);
        } else if (classifier instanceof IrClassSymbol) {
            IrClass owner = ((IrClassSymbol) classifier).getOwner();
            boolean zIsExportedImplicitlyOrExplicitly = ExportModelGeneratorKt.isExportedImplicitlyOrExplicitly(owner, this.context);
            boolean z = (zIsExportedImplicitlyOrExplicitly || owner.getIsExternal()) ? false : true;
            String strAsString = IrJsUtilsKt.getFqNameWithJsNameWhenAvailable(owner, !(owner.getIsExternal() && !zIsExportedImplicitlyOrExplicitly) && this.generateNamespacesForPackages).asString();
            Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
            if (shouldCalculateExportedSupertypeForImplicit && z) {
                Set<IrType> setCollectSuperTransitiveHierarchy = collectSuperTransitiveHierarchy(irSimpleType2);
                if (!setCollectSuperTransitiveHierarchy.isEmpty()) {
                    Set<IrType> set = setCollectSuperTransitiveHierarchy;
                    ArrayList arrayList2 = new ArrayList(set.size());
                    Iterator<T> it2 = set.iterator();
                    while (it2.hasNext()) {
                        arrayList2.add(exportType$default(this, (IrType) it2.next(), false, 2, null));
                    }
                    Iterator it3 = org.jetbrains.kotlin.utils.CollectionsKt.compactIfPossible(arrayList2).iterator();
                    if (!it3.hasNext()) {
                        throw new UnsupportedOperationException("Empty collection can't be reduced.");
                    }
                    Object next = it3.next();
                    while (it3.hasNext()) {
                        next = new ExportedType.IntersectionType((ExportedType) next, (ExportedType) it3.next());
                    }
                    any = (ExportedType) next;
                }
            }
            if (any == null) {
                any = ExportedType.Primitive.Any.INSTANCE;
            }
            switch (WhenMappings.$EnumSwitchMapping$0[owner.getKind().ordinal()]) {
                case 1:
                case 6:
                    errorType = new ExportedType.ErrorType("Class " + strAsString + " with kind: " + owner.getKind());
                    break;
                case 2:
                    errorType = new ExportedType.TypeOf(strAsString);
                    break;
                case 3:
                case 4:
                case 5:
                    List<IrTypeArgument> arguments = irSimpleType.getArguments();
                    ArrayList arrayList3 = new ArrayList(arguments.size());
                    Iterator<T> it4 = arguments.iterator();
                    while (it4.hasNext()) {
                        arrayList3.add(exportTypeArgument((IrTypeArgument) it4.next()));
                    }
                    errorType = new ExportedType.ClassType(strAsString, org.jetbrains.kotlin.utils.CollectionsKt.compactIfPossible(arrayList3), owner);
                    break;
                default:
                    throw new NoWhenBranchMatchedException();
            }
            typeParameterWithImplicitlyExported = errorType.withImplicitlyExported(z, any);
        } else {
            throw new IllegalStateException(("Unexpected classifier " + classifier).toString());
        }
        ExportedType exportedTypeWithNullability = typeParameterWithImplicitlyExported.withNullability(zIsMarkedNullable);
        this.currentlyProcessedTypes.remove(type);
        return exportedTypeWithNullability;
    }

    private final String getExportedIdentifier(IrDeclarationWithName irDeclarationWithName) {
        Name jsNameOrKotlinName = AnnotationUtilsKt.getJsNameOrKotlinName(irDeclarationWithName);
        if (jsNameOrKotlinName.isSpecial()) {
            throw new IllegalStateException(("Cannot export special name: " + irDeclarationWithName.getName().asString() + " for declaration " + IrUtilsKt.getFqNameWhenAvailable(irDeclarationWithName)).toString());
        }
        String identifier = jsNameOrKotlinName.getIdentifier();
        Intrinsics.checkNotNullExpressionValue(identifier, "with(...)");
        return identifier;
    }

    private final Exportability functionExportability(IrSimpleFunction function) {
        if (function.getIsInline()) {
            List<IrTypeParameter> typeParameters = function.getTypeParameters();
            if (!(typeParameters instanceof Collection) || !typeParameters.isEmpty()) {
                Iterator<T> it = typeParameters.iterator();
                while (it.hasNext()) {
                    if (((IrTypeParameter) it.next()).getIsReified()) {
                        return new Exportability.Prohibited("Inline reified function");
                    }
                }
            }
        }
        if (function.getIsSuspend()) {
            return new Exportability.Prohibited("Suspend function");
        }
        if (function.getIsFakeOverride() && !ExportModelGeneratorKt.isAllowedFakeOverriddenDeclaration(function, this.context)) {
            return Exportability.NotNeeded.INSTANCE;
        }
        if (Intrinsics.areEqual(function.getOrigin(), JsLoweredDeclarationOrigin.BRIDGE_WITHOUT_STABLE_NAME.INSTANCE) || Intrinsics.areEqual(function.getOrigin(), JsLoweredDeclarationOrigin.BRIDGE_PROPERTY_ACCESSOR.INSTANCE) || Intrinsics.areEqual(function.getOrigin(), JsLoweredDeclarationOrigin.BRIDGE_WITH_STABLE_NAME.INSTANCE) || Intrinsics.areEqual(function.getOrigin(), JsLoweredDeclarationOrigin.OBJECT_GET_INSTANCE_FUNCTION.INSTANCE) || Intrinsics.areEqual(function.getOrigin(), JsLoweredDeclarationOrigin.JS_SHADOWED_EXPORT.INSTANCE) || Intrinsics.areEqual(function.getOrigin(), JsLoweredDeclarationOrigin.ENUM_GET_INSTANCE_FUNCTION.INSTANCE)) {
            return Exportability.NotNeeded.INSTANCE;
        }
        IrDeclarationParent parent = function.getParent();
        IrClass irClass = parent instanceof IrClass ? (IrClass) parent : null;
        if (irClass != null && Intrinsics.areEqual(this.context.getMapping().getEnumClassToInitEntryInstancesFun().get(irClass), function)) {
            return Exportability.NotNeeded.INSTANCE;
        }
        String strAsString = function.getName().asString();
        Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
        if (StringsKt.endsWith$default(strAsString, JvmAbi.IMPL_SUFFIX_FOR_INLINE_CLASS_MEMBERS, false, 2, (Object) null)) {
            return Exportability.NotNeeded.INSTANCE;
        }
        if (StringsKt.endsWith$default(strAsString, "$", false, 2, (Object) null)) {
            List<IrValueParameter> valueParameters = function.getValueParameters();
            if (!(valueParameters instanceof Collection) || !valueParameters.isEmpty()) {
                Iterator<T> it2 = valueParameters.iterator();
                while (it2.hasNext()) {
                    String strAsString2 = ((IrValueParameter) it2.next()).getName().asString();
                    Intrinsics.checkNotNullExpressionValue(strAsString2, "asString(...)");
                    if (StringsKt.contains$default((CharSequence) strAsString2, (CharSequence) "$mask", false, 2, (Object) null)) {
                        return Exportability.NotNeeded.INSTANCE;
                    }
                }
            }
        }
        String exportedIdentifier = getExportedIdentifier(function);
        if (irClass == null && ExportModelGeneratorKt.allReservedWords.contains(exportedIdentifier)) {
            return new Exportability.Prohibited("Name is a reserved word");
        }
        return Exportability.Allowed.INSTANCE;
    }

    private final void addMagicPropertyForInterfaceImplementation(List<ExportedDeclaration> list, IrClass irClass, Iterable<? extends IrType> iterable) {
        ArrayList arrayList = new ArrayList();
        for (IrType irType : iterable) {
            if (shouldAddMagicPropertyOfSuper(irType)) {
                arrayList.add(irType);
            }
        }
        ArrayList arrayList2 = arrayList;
        if (arrayList2.isEmpty()) {
            return;
        }
        ArrayList arrayList3 = arrayList2;
        ArrayList arrayList4 = new ArrayList(CollectionsKt.collectionSizeOrDefault(arrayList3, 10));
        Iterator it = arrayList3.iterator();
        while (it.hasNext()) {
            arrayList4.add(new ExportedType.PropertyType(exportType$default(this, (IrType) it.next(), false, 2, null), new ExportedType.LiteralType.StringLiteralType("__doNotUseOrImplementIt")));
        }
        Iterator it2 = arrayList4.iterator();
        if (!it2.hasNext()) {
            throw new UnsupportedOperationException("Empty collection can't be reduced.");
        }
        Object next = it2.next();
        while (it2.hasNext()) {
            next = new ExportedType.IntersectionType((ExportedType) next, (ExportedType) it2.next());
        }
        ExportedType.IntersectionType intersectionType = (ExportedType) next;
        if (shouldNotBeImplemented(irClass)) {
            intersectionType = new ExportedType.IntersectionType(generateTagType(irClass), intersectionType);
        }
        list.add(new ExportedProperty("__doNotUseOrImplementIt", intersectionType, false, true, false, false, false, true, null, null, false, 1904, null));
    }
}
