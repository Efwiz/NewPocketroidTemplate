package org.jetbrains.kotlin.backend.jvm.lower;

import androidx.constraintlayout.core.motion.utils.TypedValues;
import com.gaurav.avnc.vnc.XKeySym;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import java.util.Set;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.LazyThreadSafetyMode;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.Sequence;
import kotlin.sequences.SequencesKt;
import kotlin.text.StringsKt;
import org.jetbrains.kotlin.backend.common.lower.ClosureAnnotator;
import org.jetbrains.kotlin.backend.common.lower.DeclarationIrBuilder;
import org.jetbrains.kotlin.backend.common.lower.LowerUtilsKt;
import org.jetbrains.kotlin.backend.jvm.JvmBackendContext;
import org.jetbrains.kotlin.backend.jvm.JvmInnerClassesSupport;
import org.jetbrains.kotlin.codegen.state.GenerationState;
import org.jetbrains.kotlin.descriptors.ClassKind;
import org.jetbrains.kotlin.descriptors.DescriptorVisibilities;
import org.jetbrains.kotlin.descriptors.DescriptorVisibility;
import org.jetbrains.kotlin.descriptors.Modality;
import org.jetbrains.kotlin.diagnostics.KtDiagnosticFactory1;
import org.jetbrains.kotlin.diagnostics.KtDiagnosticFactory2;
import org.jetbrains.kotlin.diagnostics.KtDiagnosticReporterWithContext;
import org.jetbrains.kotlin.ir.IrElement;
import org.jetbrains.kotlin.ir.IrFileEntry;
import org.jetbrains.kotlin.ir.IrStatement;
import org.jetbrains.kotlin.ir.builders.ExpressionHelpersKt;
import org.jetbrains.kotlin.ir.builders.IrBlockBodyBuilder;
import org.jetbrains.kotlin.ir.builders.IrBuilderWithScope;
import org.jetbrains.kotlin.ir.builders.declarations.DeclarationBuildersKt;
import org.jetbrains.kotlin.ir.builders.declarations.IrClassBuilder;
import org.jetbrains.kotlin.ir.builders.declarations.IrFieldBuilder;
import org.jetbrains.kotlin.ir.builders.declarations.IrFunctionBuilder;
import org.jetbrains.kotlin.ir.builders.declarations.IrPropertyBuilder;
import org.jetbrains.kotlin.ir.builders.declarations.IrValueParameterBuilder;
import org.jetbrains.kotlin.ir.declarations.IrAnonymousInitializer;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrConstructor;
import org.jetbrains.kotlin.ir.declarations.IrDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationBase;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationOrigin;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationParent;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationWithName;
import org.jetbrains.kotlin.ir.declarations.IrEnumEntry;
import org.jetbrains.kotlin.ir.declarations.IrErrorDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrExternalPackageFragment;
import org.jetbrains.kotlin.ir.declarations.IrFactory;
import org.jetbrains.kotlin.ir.declarations.IrField;
import org.jetbrains.kotlin.ir.declarations.IrFile;
import org.jetbrains.kotlin.ir.declarations.IrFunction;
import org.jetbrains.kotlin.ir.declarations.IrLocalDelegatedProperty;
import org.jetbrains.kotlin.ir.declarations.IrModuleFragment;
import org.jetbrains.kotlin.ir.declarations.IrPackageFragment;
import org.jetbrains.kotlin.ir.declarations.IrProperty;
import org.jetbrains.kotlin.ir.declarations.IrScript;
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction;
import org.jetbrains.kotlin.ir.declarations.IrTypeAlias;
import org.jetbrains.kotlin.ir.declarations.IrTypeParameter;
import org.jetbrains.kotlin.ir.declarations.IrValueDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrValueParameter;
import org.jetbrains.kotlin.ir.declarations.IrVariable;
import org.jetbrains.kotlin.ir.declarations.impl.IrAnonymousInitializerImpl;
import org.jetbrains.kotlin.ir.declarations.impl.IrClassImpl;
import org.jetbrains.kotlin.ir.declarations.impl.IrExternalPackageFragmentImpl;
import org.jetbrains.kotlin.ir.declarations.impl.IrValueParameterImpl;
import org.jetbrains.kotlin.ir.descriptors.IrBasedDescriptorsKt;
import org.jetbrains.kotlin.ir.expressions.IrBlock;
import org.jetbrains.kotlin.ir.expressions.IrBlockBody;
import org.jetbrains.kotlin.ir.expressions.IrBody;
import org.jetbrains.kotlin.ir.expressions.IrBranch;
import org.jetbrains.kotlin.ir.expressions.IrBreak;
import org.jetbrains.kotlin.ir.expressions.IrBreakContinue;
import org.jetbrains.kotlin.ir.expressions.IrCall;
import org.jetbrains.kotlin.ir.expressions.IrCallableReference;
import org.jetbrains.kotlin.ir.expressions.IrCatch;
import org.jetbrains.kotlin.ir.expressions.IrClassReference;
import org.jetbrains.kotlin.ir.expressions.IrComposite;
import org.jetbrains.kotlin.ir.expressions.IrConst;
import org.jetbrains.kotlin.ir.expressions.IrConstantArray;
import org.jetbrains.kotlin.ir.expressions.IrConstantObject;
import org.jetbrains.kotlin.ir.expressions.IrConstantPrimitive;
import org.jetbrains.kotlin.ir.expressions.IrConstantValue;
import org.jetbrains.kotlin.ir.expressions.IrConstructorCall;
import org.jetbrains.kotlin.ir.expressions.IrContainerExpression;
import org.jetbrains.kotlin.ir.expressions.IrContinue;
import org.jetbrains.kotlin.ir.expressions.IrDeclarationReference;
import org.jetbrains.kotlin.ir.expressions.IrDelegatingConstructorCall;
import org.jetbrains.kotlin.ir.expressions.IrDoWhileLoop;
import org.jetbrains.kotlin.ir.expressions.IrDynamicExpression;
import org.jetbrains.kotlin.ir.expressions.IrDynamicMemberExpression;
import org.jetbrains.kotlin.ir.expressions.IrDynamicOperatorExpression;
import org.jetbrains.kotlin.ir.expressions.IrElseBranch;
import org.jetbrains.kotlin.ir.expressions.IrEnumConstructorCall;
import org.jetbrains.kotlin.ir.expressions.IrErrorCallExpression;
import org.jetbrains.kotlin.ir.expressions.IrErrorExpression;
import org.jetbrains.kotlin.ir.expressions.IrExpression;
import org.jetbrains.kotlin.ir.expressions.IrExpressionBody;
import org.jetbrains.kotlin.ir.expressions.IrFieldAccessExpression;
import org.jetbrains.kotlin.ir.expressions.IrFunctionAccessExpression;
import org.jetbrains.kotlin.ir.expressions.IrFunctionExpression;
import org.jetbrains.kotlin.ir.expressions.IrFunctionReference;
import org.jetbrains.kotlin.ir.expressions.IrGetClass;
import org.jetbrains.kotlin.ir.expressions.IrGetEnumValue;
import org.jetbrains.kotlin.ir.expressions.IrGetField;
import org.jetbrains.kotlin.ir.expressions.IrGetObjectValue;
import org.jetbrains.kotlin.ir.expressions.IrGetSingletonValue;
import org.jetbrains.kotlin.ir.expressions.IrGetValue;
import org.jetbrains.kotlin.ir.expressions.IrInstanceInitializerCall;
import org.jetbrains.kotlin.ir.expressions.IrLocalDelegatedPropertyReference;
import org.jetbrains.kotlin.ir.expressions.IrLoop;
import org.jetbrains.kotlin.ir.expressions.IrMemberAccessExpression;
import org.jetbrains.kotlin.ir.expressions.IrPropertyReference;
import org.jetbrains.kotlin.ir.expressions.IrRawFunctionReference;
import org.jetbrains.kotlin.ir.expressions.IrReturn;
import org.jetbrains.kotlin.ir.expressions.IrSetField;
import org.jetbrains.kotlin.ir.expressions.IrSetValue;
import org.jetbrains.kotlin.ir.expressions.IrSpreadElement;
import org.jetbrains.kotlin.ir.expressions.IrStringConcatenation;
import org.jetbrains.kotlin.ir.expressions.IrSuspendableExpression;
import org.jetbrains.kotlin.ir.expressions.IrSuspensionPoint;
import org.jetbrains.kotlin.ir.expressions.IrSyntheticBody;
import org.jetbrains.kotlin.ir.expressions.IrThrow;
import org.jetbrains.kotlin.ir.expressions.IrTry;
import org.jetbrains.kotlin.ir.expressions.IrTypeOperatorCall;
import org.jetbrains.kotlin.ir.expressions.IrValueAccessExpression;
import org.jetbrains.kotlin.ir.expressions.IrVararg;
import org.jetbrains.kotlin.ir.expressions.IrWhen;
import org.jetbrains.kotlin.ir.expressions.IrWhileLoop;
import org.jetbrains.kotlin.ir.expressions.impl.IrClassReferenceImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrGetValueImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrInstanceInitializerCallImpl;
import org.jetbrains.kotlin.ir.symbols.IrClassSymbol;
import org.jetbrains.kotlin.ir.symbols.IrClassifierSymbol;
import org.jetbrains.kotlin.ir.symbols.IrFieldSymbol;
import org.jetbrains.kotlin.ir.symbols.IrPropertySymbol;
import org.jetbrains.kotlin.ir.symbols.IrScriptSymbol;
import org.jetbrains.kotlin.ir.symbols.IrSimpleFunctionSymbol;
import org.jetbrains.kotlin.ir.symbols.IrValueParameterSymbol;
import org.jetbrains.kotlin.ir.symbols.IrValueSymbol;
import org.jetbrains.kotlin.ir.symbols.impl.IrAnonymousInitializerSymbolImpl;
import org.jetbrains.kotlin.ir.symbols.impl.IrConstructorSymbolImpl;
import org.jetbrains.kotlin.ir.symbols.impl.IrFieldSymbolImpl;
import org.jetbrains.kotlin.ir.symbols.impl.IrValueParameterSymbolImpl;
import org.jetbrains.kotlin.ir.types.IrSimpleType;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.ir.types.IrTypeAbbreviation;
import org.jetbrains.kotlin.ir.types.IrTypesKt;
import org.jetbrains.kotlin.ir.types.impl.IrSimpleTypeImpl;
import org.jetbrains.kotlin.ir.util.IrUtilsKt;
import org.jetbrains.kotlin.ir.util.PatchDeclarationParentsKt;
import org.jetbrains.kotlin.ir.util.SimpleTypeRemapper;
import org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid;
import org.jetbrains.kotlin.ir.visitors.IrVisitorsKt;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.name.NameUtils;
import org.jetbrains.kotlin.name.SpecialNames;
import org.jetbrains.kotlin.resolve.jvm.diagnostics.JvmBackendErrors;
import org.jetbrains.kotlin.utils.SortUtilsKt;

/* JADX INFO: compiled from: ScriptLowering.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\u0082\u0001\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0002\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\u001e\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00130\u00122\u0006\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u0017H\u0002JH\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\u001b2\u0006\u0010\u0014\u001a\u00020\u00152.\u0010\u001c\u001a*\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u001f\u0012\u0004\u0012\u00020 0\u001e0\u001dj\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u001f\u0012\u0004\u0012\u00020 0\u001e`!H\u0002J\u0018\u0010\"\u001a\u00020#2\u0006\u0010\u0014\u001a\u00020\u00152\u0006\u0010$\u001a\u00020%H\u0002J\u000e\u0010&\u001a\u00020#2\u0006\u0010'\u001a\u00020(J\u0018\u0010)\u001a\u00020\u001b2\u0006\u0010*\u001a\u00020+2\u0006\u0010\u0014\u001a\u00020\u0015H\u0002J\f\u0010,\u001a\u00020#*\u00020\u001bH\u0002J \u0010-\u001a\u00020#*\u00020\u001b2\u0006\u0010.\u001a\u00020/2\n\b\u0002\u00100\u001a\u0004\u0018\u000101H\u0002R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u001b\u0010\u000b\u001a\u00020\f8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u000f\u0010\u0010\u001a\u0004\b\r\u0010\u000e¨\u00062"}, d2 = {"Lorg/jetbrains/kotlin/backend/jvm/lower/ScriptsToClassesLowering;", "", "context", "Lorg/jetbrains/kotlin/backend/jvm/JvmBackendContext;", "innerClassesSupport", "Lorg/jetbrains/kotlin/backend/jvm/JvmInnerClassesSupport;", "(Lorg/jetbrains/kotlin/backend/jvm/JvmBackendContext;Lorg/jetbrains/kotlin/backend/jvm/JvmInnerClassesSupport;)V", "getContext", "()Lorg/jetbrains/kotlin/backend/jvm/JvmBackendContext;", "getInnerClassesSupport", "()Lorg/jetbrains/kotlin/backend/jvm/JvmInnerClassesSupport;", "scriptingJvmPackage", "Lorg/jetbrains/kotlin/ir/declarations/IrExternalPackageFragment;", "getScriptingJvmPackage", "()Lorg/jetbrains/kotlin/ir/declarations/IrExternalPackageFragment;", "scriptingJvmPackage$delegate", "Lkotlin/Lazy;", "collectCapturingClasses", "", "Lorg/jetbrains/kotlin/ir/declarations/impl/IrClassImpl;", "irScript", "Lorg/jetbrains/kotlin/ir/declarations/IrScript;", "typeRemapper", "Lorg/jetbrains/kotlin/ir/util/SimpleTypeRemapper;", "createConstructor", "Lorg/jetbrains/kotlin/ir/declarations/IrConstructor;", "irScriptClass", "Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "implicitReceiversFieldsWithParameters", "Ljava/util/ArrayList;", "Lkotlin/Pair;", "Lorg/jetbrains/kotlin/ir/declarations/IrField;", "Lorg/jetbrains/kotlin/ir/declarations/IrValueParameter;", "Lkotlin/collections/ArrayList;", "finalizeScriptClass", "", "symbolRemapper", "Lorg/jetbrains/kotlin/backend/jvm/lower/ScriptsToClassesSymbolRemapper;", "lower", "module", "Lorg/jetbrains/kotlin/ir/declarations/IrModuleFragment;", "prepareScriptClass", "irFile", "Lorg/jetbrains/kotlin/ir/declarations/IrFile;", "addScriptMainFun", "addSimplePropertyFrom", TypedValues.TransitionType.S_FROM, "Lorg/jetbrains/kotlin/ir/declarations/IrValueDeclaration;", "initializer", "Lorg/jetbrains/kotlin/ir/expressions/IrExpressionBody;", "backend.jvm.lower"}, k = 1, mv = {1, 9, 0}, xi = 48)
final class ScriptsToClassesLowering {
    private final JvmBackendContext context;
    private final JvmInnerClassesSupport innerClassesSupport;

    /* JADX INFO: renamed from: scriptingJvmPackage$delegate, reason: from kotlin metadata */
    private final Lazy scriptingJvmPackage;

    public ScriptsToClassesLowering(JvmBackendContext context, JvmInnerClassesSupport innerClassesSupport) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(innerClassesSupport, "innerClassesSupport");
        this.context = context;
        this.innerClassesSupport = innerClassesSupport;
        this.scriptingJvmPackage = LazyKt.lazy(LazyThreadSafetyMode.PUBLICATION, (Function0) new Function0<IrExternalPackageFragment>() { // from class: org.jetbrains.kotlin.backend.jvm.lower.ScriptsToClassesLowering$scriptingJvmPackage$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final IrExternalPackageFragment invoke() {
                return IrExternalPackageFragmentImpl.INSTANCE.createEmptyExternalPackageFragment(this.this$0.getContext().getState().getModule(), new FqName("kotlin.script.experimental.jvm"));
            }
        });
    }

    public final JvmBackendContext getContext() {
        return this.context;
    }

    public final JvmInnerClassesSupport getInnerClassesSupport() {
        return this.innerClassesSupport;
    }

    public final void lower(IrModuleFragment module) {
        Intrinsics.checkNotNullParameter(module, "module");
        ArrayList arrayList = new ArrayList();
        final LinkedHashMap linkedHashMap = new LinkedHashMap();
        for (IrFile irFile : module.getFiles()) {
            ListIterator<IrDeclaration> listIterator = irFile.getDeclarations().listIterator();
            while (listIterator.hasNext()) {
                IrDeclaration next = listIterator.next();
                if (next instanceof IrScript) {
                    IrScript irScript = (IrScript) next;
                    IrClass irClassPrepareScriptClass = prepareScriptClass(irFile, irScript);
                    arrayList.add(next);
                    List<IrScriptSymbol> importedScripts = irScript.getImportedScripts();
                    List<IrScriptSymbol> list = importedScripts;
                    if (list == null || list.isEmpty()) {
                        importedScripts = null;
                    }
                    if (importedScripts != null) {
                        List<IrScriptSymbol> list2 = importedScripts;
                        ArrayList arrayList2 = new ArrayList(CollectionsKt.collectionSizeOrDefault(list2, 10));
                        Iterator<T> it = list2.iterator();
                        while (it.hasNext()) {
                            arrayList2.add(((IrScriptSymbol) it.next()).getOwner());
                        }
                        linkedHashMap.put(next, arrayList2);
                    }
                    listIterator.set(irClassPrepareScriptClass);
                }
            }
        }
        ScriptsToClassesSymbolRemapper scriptsToClassesSymbolRemapper = new ScriptsToClassesSymbolRemapper();
        for (IrScript irScript2 : CollectionsKt.reversed(SortUtilsKt.topologicalSort$default(arrayList, null, new Function1<IrScript, Iterable<? extends IrScript>>() { // from class: org.jetbrains.kotlin.backend.jvm.lower.ScriptsToClassesLowering$lower$orderedScripts$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final Iterable<IrScript> invoke(IrScript topologicalSort) {
                Intrinsics.checkNotNullParameter(topologicalSort, "$this$topologicalSort");
                List<IrScript> listEmptyList = linkedHashMap.get(topologicalSort);
                if (listEmptyList == null) {
                    listEmptyList = CollectionsKt.emptyList();
                }
                return listEmptyList;
            }
        }, 2, null))) {
            finalizeScriptClass(irScript2, scriptsToClassesSymbolRemapper);
            IrClassSymbol targetClass = irScript2.getTargetClass();
            Intrinsics.checkNotNull(targetClass);
            PatchDeclarationParentsKt.patchDeclarationParents(targetClass.getOwner(), irScript2.getParent());
        }
    }

    private final IrClass prepareScriptClass(IrFile irFile, IrScript irScript) {
        IrFileEntry fileEntry = irFile.getFileEntry();
        IrFactory irFactory = this.context.getIrFactory();
        IrClassBuilder irClassBuilder = new IrClassBuilder();
        irClassBuilder.setStartOffset(0);
        irClassBuilder.setEndOffset(fileEntry.getMaxOffset());
        irClassBuilder.setOrigin(IrDeclarationOrigin.SCRIPT_CLASS.INSTANCE);
        Name name2 = irScript.getName();
        if (name2.isSpecial()) {
            String strAsStringStripSpecialMarkers = irScript.getName().asStringStripSpecialMarkers();
            Intrinsics.checkNotNullExpressionValue(strAsStringStripSpecialMarkers, "asStringStripSpecialMarkers(...)");
            name2 = NameUtils.getScriptNameForFile(StringsKt.removePrefix(strAsStringStripSpecialMarkers, (CharSequence) "script-"));
        }
        irClassBuilder.setName(name2);
        irClassBuilder.setKind(ClassKind.CLASS);
        DescriptorVisibility PUBLIC = DescriptorVisibilities.PUBLIC;
        Intrinsics.checkNotNullExpressionValue(PUBLIC, "PUBLIC");
        irClassBuilder.setVisibility(PUBLIC);
        irClassBuilder.setModality(Modality.FINAL);
        IrClass irClassBuildClass = DeclarationBuildersKt.buildClass(irFactory, irClassBuilder);
        List<IrType> superTypes = irClassBuildClass.getSuperTypes();
        IrType baseClass = irScript.getBaseClass();
        if (baseClass == null) {
            baseClass = this.context.getIrBuiltIns().getAnyNType();
        }
        irClassBuildClass.setSuperTypes(CollectionsKt.plus((Collection<? extends IrType>) superTypes, baseClass));
        irClassBuildClass.setParent(irFile);
        irClassBuildClass.setMetadata(irScript.getMetadata());
        irScript.setTargetClass(irClassBuildClass.getSymbol());
        return irClassBuildClass;
    }

    private final Set<IrClassImpl> collectCapturingClasses(final IrScript irScript, SimpleTypeRemapper typeRemapper) {
        final ClosureAnnotator closureAnnotator = new ClosureAnnotator(irScript, irScript);
        final LinkedHashSet linkedHashSet = new LinkedHashSet();
        final LinkedHashSet linkedHashSet2 = new LinkedHashSet();
        LinkedHashSet linkedHashSet3 = linkedHashSet2;
        IrValueParameter thisReceiver = irScript.getThisReceiver();
        org.jetbrains.kotlin.utils.CollectionsKt.addIfNotNull(linkedHashSet3, thisReceiver != null ? thisReceiver.getType() : null);
        List<IrScriptSymbol> earlierScripts = irScript.getEarlierScripts();
        if (earlierScripts != null) {
            Iterator<T> it = earlierScripts.iterator();
            while (it.hasNext()) {
                IrValueParameter thisReceiver2 = ((IrScriptSymbol) it.next()).getOwner().getThisReceiver();
                org.jetbrains.kotlin.utils.CollectionsKt.addIfNotNull(linkedHashSet3, thisReceiver2 != null ? thisReceiver2.getType() : null);
            }
        }
        List<IrScriptSymbol> importedScripts = irScript.getImportedScripts();
        if (importedScripts != null) {
            Iterator<T> it2 = importedScripts.iterator();
            while (it2.hasNext()) {
                IrClassSymbol targetClass = ((IrScriptSymbol) it2.next()).getOwner().getTargetClass();
                Intrinsics.checkNotNull(targetClass);
                IrValueParameter thisReceiver3 = targetClass.getOwner().getThisReceiver();
                Intrinsics.checkNotNull(thisReceiver3);
                linkedHashSet2.add(thisReceiver3.getType());
            }
        }
        for (IrValueParameter irValueParameter : irScript.getImplicitReceiversParameters()) {
            linkedHashSet2.add(irValueParameter.getType());
            linkedHashSet2.add(typeRemapper.remapType(irValueParameter.getType()));
        }
        IrElementVisitorVoid irElementVisitorVoid = new IrElementVisitorVoid() { // from class: org.jetbrains.kotlin.backend.jvm.lower.ScriptsToClassesLowering$collectCapturingClasses$collector$1
            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitAnonymousInitializer */
            public /* bridge */ /* synthetic */ Object visitAnonymousInitializer2(IrAnonymousInitializer irAnonymousInitializer, Object obj) {
                visitAnonymousInitializer(irAnonymousInitializer, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitAnonymousInitializer(IrAnonymousInitializer irAnonymousInitializer) {
                IrElementVisitorVoid.DefaultImpls.visitAnonymousInitializer(this, irAnonymousInitializer);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitAnonymousInitializer(IrAnonymousInitializer irAnonymousInitializer, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitAnonymousInitializer(this, irAnonymousInitializer, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitBlock */
            public /* bridge */ /* synthetic */ Object visitBlock2(IrBlock irBlock, Object obj) {
                visitBlock(irBlock, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitBlock(IrBlock irBlock) {
                IrElementVisitorVoid.DefaultImpls.visitBlock(this, irBlock);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitBlock(IrBlock irBlock, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitBlock(this, irBlock, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitBlockBody */
            public /* bridge */ /* synthetic */ Object visitBlockBody2(IrBlockBody irBlockBody, Object obj) {
                visitBlockBody(irBlockBody, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitBlockBody(IrBlockBody irBlockBody) {
                IrElementVisitorVoid.DefaultImpls.visitBlockBody(this, irBlockBody);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitBlockBody(IrBlockBody irBlockBody, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitBlockBody(this, irBlockBody, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitBody */
            public /* bridge */ /* synthetic */ Object visitBody2(IrBody irBody, Object obj) {
                visitBody(irBody, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitBody(IrBody irBody) {
                IrElementVisitorVoid.DefaultImpls.visitBody(this, irBody);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitBody(IrBody irBody, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitBody(this, irBody, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitBranch */
            public /* bridge */ /* synthetic */ Object visitBranch2(IrBranch irBranch, Object obj) {
                visitBranch(irBranch, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitBranch(IrBranch irBranch) {
                IrElementVisitorVoid.DefaultImpls.visitBranch(this, irBranch);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitBranch(IrBranch irBranch, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitBranch(this, irBranch, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitBreak */
            public /* bridge */ /* synthetic */ Object visitBreak2(IrBreak irBreak, Object obj) {
                visitBreak(irBreak, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitBreak(IrBreak irBreak) {
                IrElementVisitorVoid.DefaultImpls.visitBreak(this, irBreak);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitBreak(IrBreak irBreak, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitBreak(this, irBreak, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitBreakContinue */
            public /* bridge */ /* synthetic */ Object visitBreakContinue2(IrBreakContinue irBreakContinue, Object obj) {
                visitBreakContinue(irBreakContinue, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitBreakContinue(IrBreakContinue irBreakContinue) {
                IrElementVisitorVoid.DefaultImpls.visitBreakContinue(this, irBreakContinue);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitBreakContinue(IrBreakContinue irBreakContinue, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitBreakContinue(this, irBreakContinue, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitCall */
            public /* bridge */ /* synthetic */ Object visitCall2(IrCall irCall, Object obj) {
                visitCall(irCall, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitCall(IrCall irCall) {
                IrElementVisitorVoid.DefaultImpls.visitCall(this, irCall);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitCall(IrCall irCall, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitCall(this, irCall, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            public /* bridge */ /* synthetic */ Object visitCallableReference(IrCallableReference irCallableReference, Object obj) {
                visitCallableReference((IrCallableReference<?>) irCallableReference, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitCallableReference(IrCallableReference<?> irCallableReference) {
                IrElementVisitorVoid.DefaultImpls.visitCallableReference(this, irCallableReference);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitCallableReference(IrCallableReference<?> irCallableReference, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitCallableReference(this, irCallableReference, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitCatch */
            public /* bridge */ /* synthetic */ Object visitCatch2(IrCatch irCatch, Object obj) {
                visitCatch(irCatch, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitCatch(IrCatch irCatch) {
                IrElementVisitorVoid.DefaultImpls.visitCatch(this, irCatch);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitCatch(IrCatch irCatch, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitCatch(this, irCatch, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitClass */
            public /* bridge */ /* synthetic */ Object visitClass2(IrClass irClass, Object obj) {
                visitClass(irClass, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitClass(IrClass irClass, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitClass(this, irClass, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitClassReference */
            public /* bridge */ /* synthetic */ Object visitClassReference2(IrClassReference irClassReference, Object obj) {
                visitClassReference(irClassReference, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitClassReference(IrClassReference irClassReference) {
                IrElementVisitorVoid.DefaultImpls.visitClassReference(this, irClassReference);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitClassReference(IrClassReference irClassReference, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitClassReference(this, irClassReference, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitComposite */
            public /* bridge */ /* synthetic */ Object visitComposite2(IrComposite irComposite, Object obj) {
                visitComposite(irComposite, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitComposite(IrComposite irComposite) {
                IrElementVisitorVoid.DefaultImpls.visitComposite(this, irComposite);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitComposite(IrComposite irComposite, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitComposite(this, irComposite, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            public /* bridge */ /* synthetic */ Object visitConst(IrConst irConst, Object obj) {
                visitConst((IrConst<?>) irConst, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitConst(IrConst<?> irConst) {
                IrElementVisitorVoid.DefaultImpls.visitConst(this, irConst);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitConst(IrConst<?> irConst, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitConst(this, irConst, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitConstantArray */
            public /* bridge */ /* synthetic */ Object visitConstantArray2(IrConstantArray irConstantArray, Object obj) {
                visitConstantArray(irConstantArray, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitConstantArray(IrConstantArray irConstantArray) {
                IrElementVisitorVoid.DefaultImpls.visitConstantArray(this, irConstantArray);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitConstantArray(IrConstantArray irConstantArray, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitConstantArray(this, irConstantArray, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitConstantObject */
            public /* bridge */ /* synthetic */ Object visitConstantObject2(IrConstantObject irConstantObject, Object obj) {
                visitConstantObject(irConstantObject, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitConstantObject(IrConstantObject irConstantObject) {
                IrElementVisitorVoid.DefaultImpls.visitConstantObject(this, irConstantObject);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitConstantObject(IrConstantObject irConstantObject, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitConstantObject(this, irConstantObject, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitConstantPrimitive */
            public /* bridge */ /* synthetic */ Object visitConstantPrimitive2(IrConstantPrimitive irConstantPrimitive, Object obj) {
                visitConstantPrimitive(irConstantPrimitive, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitConstantPrimitive(IrConstantPrimitive irConstantPrimitive) {
                IrElementVisitorVoid.DefaultImpls.visitConstantPrimitive(this, irConstantPrimitive);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitConstantPrimitive(IrConstantPrimitive irConstantPrimitive, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitConstantPrimitive(this, irConstantPrimitive, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitConstantValue */
            public /* bridge */ /* synthetic */ Object visitConstantValue2(IrConstantValue irConstantValue, Object obj) {
                visitConstantValue(irConstantValue, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitConstantValue(IrConstantValue irConstantValue) {
                IrElementVisitorVoid.DefaultImpls.visitConstantValue(this, irConstantValue);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitConstantValue(IrConstantValue irConstantValue, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitConstantValue(this, irConstantValue, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitConstructor */
            public /* bridge */ /* synthetic */ Object visitConstructor2(IrConstructor irConstructor, Object obj) {
                visitConstructor(irConstructor, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitConstructor(IrConstructor irConstructor) {
                IrElementVisitorVoid.DefaultImpls.visitConstructor(this, irConstructor);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitConstructor(IrConstructor irConstructor, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitConstructor(this, irConstructor, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitConstructorCall */
            public /* bridge */ /* synthetic */ Object visitConstructorCall2(IrConstructorCall irConstructorCall, Object obj) {
                visitConstructorCall(irConstructorCall, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitConstructorCall(IrConstructorCall irConstructorCall) {
                IrElementVisitorVoid.DefaultImpls.visitConstructorCall(this, irConstructorCall);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitConstructorCall(IrConstructorCall irConstructorCall, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitConstructorCall(this, irConstructorCall, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitContainerExpression */
            public /* bridge */ /* synthetic */ Object visitContainerExpression2(IrContainerExpression irContainerExpression, Object obj) {
                visitContainerExpression(irContainerExpression, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitContainerExpression(IrContainerExpression irContainerExpression) {
                IrElementVisitorVoid.DefaultImpls.visitContainerExpression(this, irContainerExpression);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitContainerExpression(IrContainerExpression irContainerExpression, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitContainerExpression(this, irContainerExpression, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitContinue */
            public /* bridge */ /* synthetic */ Object visitContinue2(IrContinue irContinue, Object obj) {
                visitContinue(irContinue, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitContinue(IrContinue irContinue) {
                IrElementVisitorVoid.DefaultImpls.visitContinue(this, irContinue);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitContinue(IrContinue irContinue, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitContinue(this, irContinue, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitDeclaration */
            public /* bridge */ /* synthetic */ Object visitDeclaration2(IrDeclarationBase irDeclarationBase, Object obj) {
                visitDeclaration(irDeclarationBase, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitDeclaration(IrDeclarationBase irDeclarationBase) {
                IrElementVisitorVoid.DefaultImpls.visitDeclaration(this, irDeclarationBase);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitDeclaration(IrDeclarationBase irDeclarationBase, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitDeclaration(this, irDeclarationBase, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitDeclarationReference */
            public /* bridge */ /* synthetic */ Object visitDeclarationReference2(IrDeclarationReference irDeclarationReference, Object obj) {
                visitDeclarationReference(irDeclarationReference, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitDeclarationReference(IrDeclarationReference irDeclarationReference) {
                IrElementVisitorVoid.DefaultImpls.visitDeclarationReference(this, irDeclarationReference);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitDeclarationReference(IrDeclarationReference irDeclarationReference, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitDeclarationReference(this, irDeclarationReference, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitDelegatingConstructorCall */
            public /* bridge */ /* synthetic */ Object visitDelegatingConstructorCall2(IrDelegatingConstructorCall irDelegatingConstructorCall, Object obj) {
                visitDelegatingConstructorCall(irDelegatingConstructorCall, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitDelegatingConstructorCall(IrDelegatingConstructorCall irDelegatingConstructorCall) {
                IrElementVisitorVoid.DefaultImpls.visitDelegatingConstructorCall(this, irDelegatingConstructorCall);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitDelegatingConstructorCall(IrDelegatingConstructorCall irDelegatingConstructorCall, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitDelegatingConstructorCall(this, irDelegatingConstructorCall, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitDoWhileLoop */
            public /* bridge */ /* synthetic */ Object visitDoWhileLoop2(IrDoWhileLoop irDoWhileLoop, Object obj) {
                visitDoWhileLoop(irDoWhileLoop, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitDoWhileLoop(IrDoWhileLoop irDoWhileLoop) {
                IrElementVisitorVoid.DefaultImpls.visitDoWhileLoop(this, irDoWhileLoop);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitDoWhileLoop(IrDoWhileLoop irDoWhileLoop, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitDoWhileLoop(this, irDoWhileLoop, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitDynamicExpression */
            public /* bridge */ /* synthetic */ Object visitDynamicExpression2(IrDynamicExpression irDynamicExpression, Object obj) {
                visitDynamicExpression(irDynamicExpression, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitDynamicExpression(IrDynamicExpression irDynamicExpression) {
                IrElementVisitorVoid.DefaultImpls.visitDynamicExpression(this, irDynamicExpression);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitDynamicExpression(IrDynamicExpression irDynamicExpression, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitDynamicExpression(this, irDynamicExpression, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitDynamicMemberExpression */
            public /* bridge */ /* synthetic */ Object visitDynamicMemberExpression2(IrDynamicMemberExpression irDynamicMemberExpression, Object obj) {
                visitDynamicMemberExpression(irDynamicMemberExpression, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitDynamicMemberExpression(IrDynamicMemberExpression irDynamicMemberExpression) {
                IrElementVisitorVoid.DefaultImpls.visitDynamicMemberExpression(this, irDynamicMemberExpression);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitDynamicMemberExpression(IrDynamicMemberExpression irDynamicMemberExpression, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitDynamicMemberExpression(this, irDynamicMemberExpression, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitDynamicOperatorExpression */
            public /* bridge */ /* synthetic */ Object visitDynamicOperatorExpression2(IrDynamicOperatorExpression irDynamicOperatorExpression, Object obj) {
                visitDynamicOperatorExpression(irDynamicOperatorExpression, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitDynamicOperatorExpression(IrDynamicOperatorExpression irDynamicOperatorExpression) {
                IrElementVisitorVoid.DefaultImpls.visitDynamicOperatorExpression(this, irDynamicOperatorExpression);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitDynamicOperatorExpression(IrDynamicOperatorExpression irDynamicOperatorExpression, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitDynamicOperatorExpression(this, irDynamicOperatorExpression, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitElement */
            public /* bridge */ /* synthetic */ Object visitElement2(IrElement irElement, Object obj) {
                visitElement(irElement, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitElement(IrElement irElement, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitElement(this, irElement, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitElseBranch */
            public /* bridge */ /* synthetic */ Object visitElseBranch2(IrElseBranch irElseBranch, Object obj) {
                visitElseBranch(irElseBranch, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitElseBranch(IrElseBranch irElseBranch) {
                IrElementVisitorVoid.DefaultImpls.visitElseBranch(this, irElseBranch);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitElseBranch(IrElseBranch irElseBranch, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitElseBranch(this, irElseBranch, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitEnumConstructorCall */
            public /* bridge */ /* synthetic */ Object visitEnumConstructorCall2(IrEnumConstructorCall irEnumConstructorCall, Object obj) {
                visitEnumConstructorCall(irEnumConstructorCall, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitEnumConstructorCall(IrEnumConstructorCall irEnumConstructorCall) {
                IrElementVisitorVoid.DefaultImpls.visitEnumConstructorCall(this, irEnumConstructorCall);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitEnumConstructorCall(IrEnumConstructorCall irEnumConstructorCall, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitEnumConstructorCall(this, irEnumConstructorCall, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitEnumEntry */
            public /* bridge */ /* synthetic */ Object visitEnumEntry2(IrEnumEntry irEnumEntry, Object obj) {
                visitEnumEntry(irEnumEntry, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitEnumEntry(IrEnumEntry irEnumEntry) {
                IrElementVisitorVoid.DefaultImpls.visitEnumEntry(this, irEnumEntry);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitEnumEntry(IrEnumEntry irEnumEntry, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitEnumEntry(this, irEnumEntry, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitErrorCallExpression */
            public /* bridge */ /* synthetic */ Object visitErrorCallExpression2(IrErrorCallExpression irErrorCallExpression, Object obj) {
                visitErrorCallExpression(irErrorCallExpression, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitErrorCallExpression(IrErrorCallExpression irErrorCallExpression) {
                IrElementVisitorVoid.DefaultImpls.visitErrorCallExpression(this, irErrorCallExpression);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitErrorCallExpression(IrErrorCallExpression irErrorCallExpression, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitErrorCallExpression(this, irErrorCallExpression, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitErrorDeclaration */
            public /* bridge */ /* synthetic */ Object visitErrorDeclaration2(IrErrorDeclaration irErrorDeclaration, Object obj) {
                visitErrorDeclaration(irErrorDeclaration, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitErrorDeclaration(IrErrorDeclaration irErrorDeclaration) {
                IrElementVisitorVoid.DefaultImpls.visitErrorDeclaration(this, irErrorDeclaration);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitErrorDeclaration(IrErrorDeclaration irErrorDeclaration, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitErrorDeclaration(this, irErrorDeclaration, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitErrorExpression */
            public /* bridge */ /* synthetic */ Object visitErrorExpression2(IrErrorExpression irErrorExpression, Object obj) {
                visitErrorExpression(irErrorExpression, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitErrorExpression(IrErrorExpression irErrorExpression) {
                IrElementVisitorVoid.DefaultImpls.visitErrorExpression(this, irErrorExpression);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitErrorExpression(IrErrorExpression irErrorExpression, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitErrorExpression(this, irErrorExpression, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitExpression */
            public /* bridge */ /* synthetic */ Object visitExpression2(IrExpression irExpression, Object obj) {
                visitExpression(irExpression, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitExpression(IrExpression irExpression) {
                IrElementVisitorVoid.DefaultImpls.visitExpression(this, irExpression);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitExpression(IrExpression irExpression, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitExpression(this, irExpression, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitExpressionBody */
            public /* bridge */ /* synthetic */ Object visitExpressionBody2(IrExpressionBody irExpressionBody, Object obj) {
                visitExpressionBody(irExpressionBody, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitExpressionBody(IrExpressionBody irExpressionBody) {
                IrElementVisitorVoid.DefaultImpls.visitExpressionBody(this, irExpressionBody);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitExpressionBody(IrExpressionBody irExpressionBody, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitExpressionBody(this, irExpressionBody, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitExternalPackageFragment */
            public /* bridge */ /* synthetic */ Object visitExternalPackageFragment2(IrExternalPackageFragment irExternalPackageFragment, Object obj) {
                visitExternalPackageFragment(irExternalPackageFragment, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitExternalPackageFragment(IrExternalPackageFragment irExternalPackageFragment) {
                IrElementVisitorVoid.DefaultImpls.visitExternalPackageFragment(this, irExternalPackageFragment);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitExternalPackageFragment(IrExternalPackageFragment irExternalPackageFragment, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitExternalPackageFragment(this, irExternalPackageFragment, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitField */
            public /* bridge */ /* synthetic */ Object visitField2(IrField irField, Object obj) {
                visitField(irField, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitField(IrField irField) {
                IrElementVisitorVoid.DefaultImpls.visitField(this, irField);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitField(IrField irField, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitField(this, irField, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitFieldAccess */
            public /* bridge */ /* synthetic */ Object visitFieldAccess2(IrFieldAccessExpression irFieldAccessExpression, Object obj) {
                visitFieldAccess(irFieldAccessExpression, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitFieldAccess(IrFieldAccessExpression irFieldAccessExpression) {
                IrElementVisitorVoid.DefaultImpls.visitFieldAccess(this, irFieldAccessExpression);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitFieldAccess(IrFieldAccessExpression irFieldAccessExpression, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitFieldAccess(this, irFieldAccessExpression, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitFile */
            public /* bridge */ /* synthetic */ Object visitFile2(IrFile irFile, Object obj) {
                visitFile(irFile, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitFile(IrFile irFile) {
                IrElementVisitorVoid.DefaultImpls.visitFile(this, irFile);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitFile(IrFile irFile, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitFile(this, irFile, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitFunction */
            public /* bridge */ /* synthetic */ Object visitFunction2(IrFunction irFunction, Object obj) {
                visitFunction(irFunction, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitFunction(IrFunction irFunction) {
                IrElementVisitorVoid.DefaultImpls.visitFunction(this, irFunction);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitFunction(IrFunction irFunction, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitFunction(this, irFunction, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitFunctionAccess */
            public /* bridge */ /* synthetic */ Object visitFunctionAccess2(IrFunctionAccessExpression irFunctionAccessExpression, Object obj) {
                visitFunctionAccess(irFunctionAccessExpression, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitFunctionAccess(IrFunctionAccessExpression irFunctionAccessExpression) {
                IrElementVisitorVoid.DefaultImpls.visitFunctionAccess(this, irFunctionAccessExpression);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitFunctionAccess(IrFunctionAccessExpression irFunctionAccessExpression, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitFunctionAccess(this, irFunctionAccessExpression, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitFunctionExpression */
            public /* bridge */ /* synthetic */ Object visitFunctionExpression2(IrFunctionExpression irFunctionExpression, Object obj) {
                visitFunctionExpression(irFunctionExpression, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitFunctionExpression(IrFunctionExpression irFunctionExpression) {
                IrElementVisitorVoid.DefaultImpls.visitFunctionExpression(this, irFunctionExpression);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitFunctionExpression(IrFunctionExpression irFunctionExpression, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitFunctionExpression(this, irFunctionExpression, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitFunctionReference */
            public /* bridge */ /* synthetic */ Object visitFunctionReference2(IrFunctionReference irFunctionReference, Object obj) {
                visitFunctionReference(irFunctionReference, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitFunctionReference(IrFunctionReference irFunctionReference) {
                IrElementVisitorVoid.DefaultImpls.visitFunctionReference(this, irFunctionReference);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitFunctionReference(IrFunctionReference irFunctionReference, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitFunctionReference(this, irFunctionReference, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitGetClass */
            public /* bridge */ /* synthetic */ Object visitGetClass2(IrGetClass irGetClass, Object obj) {
                visitGetClass(irGetClass, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitGetClass(IrGetClass irGetClass) {
                IrElementVisitorVoid.DefaultImpls.visitGetClass(this, irGetClass);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitGetClass(IrGetClass irGetClass, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitGetClass(this, irGetClass, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitGetEnumValue */
            public /* bridge */ /* synthetic */ Object visitGetEnumValue2(IrGetEnumValue irGetEnumValue, Object obj) {
                visitGetEnumValue(irGetEnumValue, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitGetEnumValue(IrGetEnumValue irGetEnumValue) {
                IrElementVisitorVoid.DefaultImpls.visitGetEnumValue(this, irGetEnumValue);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitGetEnumValue(IrGetEnumValue irGetEnumValue, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitGetEnumValue(this, irGetEnumValue, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitGetField */
            public /* bridge */ /* synthetic */ Object visitGetField2(IrGetField irGetField, Object obj) {
                visitGetField(irGetField, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitGetField(IrGetField irGetField) {
                IrElementVisitorVoid.DefaultImpls.visitGetField(this, irGetField);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitGetField(IrGetField irGetField, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitGetField(this, irGetField, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitGetObjectValue */
            public /* bridge */ /* synthetic */ Object visitGetObjectValue2(IrGetObjectValue irGetObjectValue, Object obj) {
                visitGetObjectValue(irGetObjectValue, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitGetObjectValue(IrGetObjectValue irGetObjectValue) {
                IrElementVisitorVoid.DefaultImpls.visitGetObjectValue(this, irGetObjectValue);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitGetObjectValue(IrGetObjectValue irGetObjectValue, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitGetObjectValue(this, irGetObjectValue, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitGetValue */
            public /* bridge */ /* synthetic */ Object visitGetValue2(IrGetValue irGetValue, Object obj) {
                visitGetValue(irGetValue, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitGetValue(IrGetValue irGetValue) {
                IrElementVisitorVoid.DefaultImpls.visitGetValue(this, irGetValue);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitGetValue(IrGetValue irGetValue, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitGetValue(this, irGetValue, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitInstanceInitializerCall */
            public /* bridge */ /* synthetic */ Object visitInstanceInitializerCall2(IrInstanceInitializerCall irInstanceInitializerCall, Object obj) {
                visitInstanceInitializerCall(irInstanceInitializerCall, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitInstanceInitializerCall(IrInstanceInitializerCall irInstanceInitializerCall) {
                IrElementVisitorVoid.DefaultImpls.visitInstanceInitializerCall(this, irInstanceInitializerCall);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitInstanceInitializerCall(IrInstanceInitializerCall irInstanceInitializerCall, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitInstanceInitializerCall(this, irInstanceInitializerCall, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitLocalDelegatedProperty */
            public /* bridge */ /* synthetic */ Object visitLocalDelegatedProperty2(IrLocalDelegatedProperty irLocalDelegatedProperty, Object obj) {
                visitLocalDelegatedProperty(irLocalDelegatedProperty, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitLocalDelegatedProperty(IrLocalDelegatedProperty irLocalDelegatedProperty) {
                IrElementVisitorVoid.DefaultImpls.visitLocalDelegatedProperty(this, irLocalDelegatedProperty);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitLocalDelegatedProperty(IrLocalDelegatedProperty irLocalDelegatedProperty, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitLocalDelegatedProperty(this, irLocalDelegatedProperty, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitLocalDelegatedPropertyReference */
            public /* bridge */ /* synthetic */ Object visitLocalDelegatedPropertyReference2(IrLocalDelegatedPropertyReference irLocalDelegatedPropertyReference, Object obj) {
                visitLocalDelegatedPropertyReference(irLocalDelegatedPropertyReference, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitLocalDelegatedPropertyReference(IrLocalDelegatedPropertyReference irLocalDelegatedPropertyReference) {
                IrElementVisitorVoid.DefaultImpls.visitLocalDelegatedPropertyReference(this, irLocalDelegatedPropertyReference);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitLocalDelegatedPropertyReference(IrLocalDelegatedPropertyReference irLocalDelegatedPropertyReference, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitLocalDelegatedPropertyReference(this, irLocalDelegatedPropertyReference, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitLoop */
            public /* bridge */ /* synthetic */ Object visitLoop2(IrLoop irLoop, Object obj) {
                visitLoop(irLoop, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitLoop(IrLoop irLoop) {
                IrElementVisitorVoid.DefaultImpls.visitLoop(this, irLoop);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitLoop(IrLoop irLoop, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitLoop(this, irLoop, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            public /* bridge */ /* synthetic */ Object visitMemberAccess(IrMemberAccessExpression irMemberAccessExpression, Object obj) {
                visitMemberAccess((IrMemberAccessExpression<?>) irMemberAccessExpression, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitMemberAccess(IrMemberAccessExpression<?> irMemberAccessExpression) {
                IrElementVisitorVoid.DefaultImpls.visitMemberAccess(this, irMemberAccessExpression);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitMemberAccess(IrMemberAccessExpression<?> irMemberAccessExpression, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitMemberAccess(this, irMemberAccessExpression, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitModuleFragment */
            public /* bridge */ /* synthetic */ Object visitModuleFragment2(IrModuleFragment irModuleFragment, Object obj) {
                visitModuleFragment(irModuleFragment, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitModuleFragment(IrModuleFragment irModuleFragment) {
                IrElementVisitorVoid.DefaultImpls.visitModuleFragment(this, irModuleFragment);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitModuleFragment(IrModuleFragment irModuleFragment, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitModuleFragment(this, irModuleFragment, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitPackageFragment */
            public /* bridge */ /* synthetic */ Object visitPackageFragment2(IrPackageFragment irPackageFragment, Object obj) {
                visitPackageFragment(irPackageFragment, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitPackageFragment(IrPackageFragment irPackageFragment) {
                IrElementVisitorVoid.DefaultImpls.visitPackageFragment(this, irPackageFragment);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitPackageFragment(IrPackageFragment irPackageFragment, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitPackageFragment(this, irPackageFragment, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitProperty */
            public /* bridge */ /* synthetic */ Object visitProperty2(IrProperty irProperty, Object obj) {
                visitProperty(irProperty, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitProperty(IrProperty irProperty) {
                IrElementVisitorVoid.DefaultImpls.visitProperty(this, irProperty);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitProperty(IrProperty irProperty, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitProperty(this, irProperty, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitPropertyReference */
            public /* bridge */ /* synthetic */ Object visitPropertyReference2(IrPropertyReference irPropertyReference, Object obj) {
                visitPropertyReference(irPropertyReference, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitPropertyReference(IrPropertyReference irPropertyReference) {
                IrElementVisitorVoid.DefaultImpls.visitPropertyReference(this, irPropertyReference);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitPropertyReference(IrPropertyReference irPropertyReference, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitPropertyReference(this, irPropertyReference, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitRawFunctionReference */
            public /* bridge */ /* synthetic */ Object visitRawFunctionReference2(IrRawFunctionReference irRawFunctionReference, Object obj) {
                visitRawFunctionReference(irRawFunctionReference, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitRawFunctionReference(IrRawFunctionReference irRawFunctionReference) {
                IrElementVisitorVoid.DefaultImpls.visitRawFunctionReference(this, irRawFunctionReference);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitRawFunctionReference(IrRawFunctionReference irRawFunctionReference, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitRawFunctionReference(this, irRawFunctionReference, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitReturn */
            public /* bridge */ /* synthetic */ Object visitReturn2(IrReturn irReturn, Object obj) {
                visitReturn(irReturn, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitReturn(IrReturn irReturn) {
                IrElementVisitorVoid.DefaultImpls.visitReturn(this, irReturn);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitReturn(IrReturn irReturn, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitReturn(this, irReturn, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitScript */
            public /* bridge */ /* synthetic */ Object visitScript2(IrScript irScript2, Object obj) {
                visitScript(irScript2, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitScript(IrScript irScript2) {
                IrElementVisitorVoid.DefaultImpls.visitScript(this, irScript2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitScript(IrScript irScript2, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitScript(this, irScript2, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitSetField */
            public /* bridge */ /* synthetic */ Object visitSetField2(IrSetField irSetField, Object obj) {
                visitSetField(irSetField, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitSetField(IrSetField irSetField) {
                IrElementVisitorVoid.DefaultImpls.visitSetField(this, irSetField);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitSetField(IrSetField irSetField, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitSetField(this, irSetField, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitSetValue */
            public /* bridge */ /* synthetic */ Object visitSetValue2(IrSetValue irSetValue, Object obj) {
                visitSetValue(irSetValue, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitSetValue(IrSetValue irSetValue) {
                IrElementVisitorVoid.DefaultImpls.visitSetValue(this, irSetValue);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitSetValue(IrSetValue irSetValue, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitSetValue(this, irSetValue, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitSimpleFunction */
            public /* bridge */ /* synthetic */ Object visitSimpleFunction2(IrSimpleFunction irSimpleFunction, Object obj) {
                visitSimpleFunction(irSimpleFunction, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitSimpleFunction(IrSimpleFunction irSimpleFunction) {
                IrElementVisitorVoid.DefaultImpls.visitSimpleFunction(this, irSimpleFunction);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitSimpleFunction(IrSimpleFunction irSimpleFunction, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitSimpleFunction(this, irSimpleFunction, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitSingletonReference */
            public /* bridge */ /* synthetic */ Object visitSingletonReference2(IrGetSingletonValue irGetSingletonValue, Object obj) {
                visitSingletonReference(irGetSingletonValue, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitSingletonReference(IrGetSingletonValue irGetSingletonValue) {
                IrElementVisitorVoid.DefaultImpls.visitSingletonReference(this, irGetSingletonValue);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitSingletonReference(IrGetSingletonValue irGetSingletonValue, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitSingletonReference(this, irGetSingletonValue, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitSpreadElement */
            public /* bridge */ /* synthetic */ Object visitSpreadElement2(IrSpreadElement irSpreadElement, Object obj) {
                visitSpreadElement(irSpreadElement, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitSpreadElement(IrSpreadElement irSpreadElement) {
                IrElementVisitorVoid.DefaultImpls.visitSpreadElement(this, irSpreadElement);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitSpreadElement(IrSpreadElement irSpreadElement, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitSpreadElement(this, irSpreadElement, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitStringConcatenation */
            public /* bridge */ /* synthetic */ Object visitStringConcatenation2(IrStringConcatenation irStringConcatenation, Object obj) {
                visitStringConcatenation(irStringConcatenation, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitStringConcatenation(IrStringConcatenation irStringConcatenation) {
                IrElementVisitorVoid.DefaultImpls.visitStringConcatenation(this, irStringConcatenation);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitStringConcatenation(IrStringConcatenation irStringConcatenation, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitStringConcatenation(this, irStringConcatenation, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitSuspendableExpression */
            public /* bridge */ /* synthetic */ Object visitSuspendableExpression2(IrSuspendableExpression irSuspendableExpression, Object obj) {
                visitSuspendableExpression(irSuspendableExpression, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitSuspendableExpression(IrSuspendableExpression irSuspendableExpression) {
                IrElementVisitorVoid.DefaultImpls.visitSuspendableExpression(this, irSuspendableExpression);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitSuspendableExpression(IrSuspendableExpression irSuspendableExpression, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitSuspendableExpression(this, irSuspendableExpression, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitSuspensionPoint */
            public /* bridge */ /* synthetic */ Object visitSuspensionPoint2(IrSuspensionPoint irSuspensionPoint, Object obj) {
                visitSuspensionPoint(irSuspensionPoint, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitSuspensionPoint(IrSuspensionPoint irSuspensionPoint) {
                IrElementVisitorVoid.DefaultImpls.visitSuspensionPoint(this, irSuspensionPoint);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitSuspensionPoint(IrSuspensionPoint irSuspensionPoint, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitSuspensionPoint(this, irSuspensionPoint, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitSyntheticBody */
            public /* bridge */ /* synthetic */ Object visitSyntheticBody2(IrSyntheticBody irSyntheticBody, Object obj) {
                visitSyntheticBody(irSyntheticBody, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitSyntheticBody(IrSyntheticBody irSyntheticBody) {
                IrElementVisitorVoid.DefaultImpls.visitSyntheticBody(this, irSyntheticBody);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitSyntheticBody(IrSyntheticBody irSyntheticBody, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitSyntheticBody(this, irSyntheticBody, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitThrow */
            public /* bridge */ /* synthetic */ Object visitThrow2(IrThrow irThrow, Object obj) {
                visitThrow(irThrow, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitThrow(IrThrow irThrow) {
                IrElementVisitorVoid.DefaultImpls.visitThrow(this, irThrow);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitThrow(IrThrow irThrow, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitThrow(this, irThrow, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitTry */
            public /* bridge */ /* synthetic */ Object visitTry2(IrTry irTry, Object obj) {
                visitTry(irTry, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitTry(IrTry irTry) {
                IrElementVisitorVoid.DefaultImpls.visitTry(this, irTry);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitTry(IrTry irTry, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitTry(this, irTry, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitTypeAlias */
            public /* bridge */ /* synthetic */ Object visitTypeAlias2(IrTypeAlias irTypeAlias, Object obj) {
                visitTypeAlias(irTypeAlias, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitTypeAlias(IrTypeAlias irTypeAlias) {
                IrElementVisitorVoid.DefaultImpls.visitTypeAlias(this, irTypeAlias);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitTypeAlias(IrTypeAlias irTypeAlias, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitTypeAlias(this, irTypeAlias, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitTypeOperator */
            public /* bridge */ /* synthetic */ Object visitTypeOperator2(IrTypeOperatorCall irTypeOperatorCall, Object obj) {
                visitTypeOperator(irTypeOperatorCall, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitTypeOperator(IrTypeOperatorCall irTypeOperatorCall) {
                IrElementVisitorVoid.DefaultImpls.visitTypeOperator(this, irTypeOperatorCall);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitTypeOperator(IrTypeOperatorCall irTypeOperatorCall, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitTypeOperator(this, irTypeOperatorCall, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitTypeParameter */
            public /* bridge */ /* synthetic */ Object visitTypeParameter2(IrTypeParameter irTypeParameter, Object obj) {
                visitTypeParameter(irTypeParameter, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitTypeParameter(IrTypeParameter irTypeParameter) {
                IrElementVisitorVoid.DefaultImpls.visitTypeParameter(this, irTypeParameter);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitTypeParameter(IrTypeParameter irTypeParameter, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitTypeParameter(this, irTypeParameter, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitValueAccess */
            public /* bridge */ /* synthetic */ Object visitValueAccess2(IrValueAccessExpression irValueAccessExpression, Object obj) {
                visitValueAccess(irValueAccessExpression, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitValueAccess(IrValueAccessExpression irValueAccessExpression) {
                IrElementVisitorVoid.DefaultImpls.visitValueAccess(this, irValueAccessExpression);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitValueAccess(IrValueAccessExpression irValueAccessExpression, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitValueAccess(this, irValueAccessExpression, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitValueParameter */
            public /* bridge */ /* synthetic */ Object visitValueParameter2(IrValueParameter irValueParameter2, Object obj) {
                visitValueParameter(irValueParameter2, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitValueParameter(IrValueParameter irValueParameter2) {
                IrElementVisitorVoid.DefaultImpls.visitValueParameter(this, irValueParameter2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitValueParameter(IrValueParameter irValueParameter2, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitValueParameter(this, irValueParameter2, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitVararg */
            public /* bridge */ /* synthetic */ Object visitVararg2(IrVararg irVararg, Object obj) {
                visitVararg(irVararg, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitVararg(IrVararg irVararg) {
                IrElementVisitorVoid.DefaultImpls.visitVararg(this, irVararg);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitVararg(IrVararg irVararg, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitVararg(this, irVararg, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitVariable */
            public /* bridge */ /* synthetic */ Object visitVariable2(IrVariable irVariable, Object obj) {
                visitVariable(irVariable, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitVariable(IrVariable irVariable) {
                IrElementVisitorVoid.DefaultImpls.visitVariable(this, irVariable);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitVariable(IrVariable irVariable, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitVariable(this, irVariable, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitWhen */
            public /* bridge */ /* synthetic */ Object visitWhen2(IrWhen irWhen, Object obj) {
                visitWhen(irWhen, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitWhen(IrWhen irWhen) {
                IrElementVisitorVoid.DefaultImpls.visitWhen(this, irWhen);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitWhen(IrWhen irWhen, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitWhen(this, irWhen, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitWhileLoop */
            public /* bridge */ /* synthetic */ Object visitWhileLoop2(IrWhileLoop irWhileLoop, Object obj) {
                visitWhileLoop(irWhileLoop, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitWhileLoop(IrWhileLoop irWhileLoop) {
                IrElementVisitorVoid.DefaultImpls.visitWhileLoop(this, irWhileLoop);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitWhileLoop(IrWhileLoop irWhileLoop, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitWhileLoop(this, irWhileLoop, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            /* JADX INFO: renamed from: visitElement */
            public void mo6335visitElement(IrElement element) {
                Intrinsics.checkNotNullParameter(element, "element");
                IrVisitorsKt.acceptChildrenVoid(element, this);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            /* JADX INFO: renamed from: visitClass */
            public void mo6112visitClass(IrClass declaration) {
                Name name2;
                Intrinsics.checkNotNullParameter(declaration, "declaration");
                if ((declaration instanceof IrClassImpl) && !declaration.getIsInner()) {
                    List<IrValueSymbol> capturedValues = closureAnnotator.getClassClosure(declaration).getCapturedValues();
                    Set<IrType> set = linkedHashSet2;
                    if (!(capturedValues instanceof Collection) || !capturedValues.isEmpty()) {
                        Iterator<T> it3 = capturedValues.iterator();
                        while (it3.hasNext()) {
                            if (set.contains(((IrValueSymbol) it3.next()).getOwner().getType())) {
                                if (!IrUtilsKt.isInterface(declaration)) {
                                    if (!IrUtilsKt.isEnumClass(declaration)) {
                                        if (!IrUtilsKt.isEnumEntry(declaration)) {
                                            if (declaration.getIsCompanion()) {
                                                visitClass$reportError(this, declaration, JvmBackendErrors.INSTANCE.getSCRIPT_CAPTURING_OBJECT(), SpecialNames.DEFAULT_NAME_FOR_COMPANION_OBJECT);
                                                break;
                                            }
                                            if (!declaration.getKind().isSingleton()) {
                                                if (!IrUtilsKt.isClass(declaration)) {
                                                    break;
                                                }
                                                if (!Intrinsics.areEqual(declaration.getParent(), irScript)) {
                                                    IrDeclarationParent parent = declaration.getParent();
                                                    IrClass irClass = parent instanceof IrClass ? (IrClass) parent : null;
                                                    if (irClass != null && !irClass.getIsInner()) {
                                                        KtDiagnosticReporterWithContext.DiagnosticContextImpl diagnosticContextImplAt = this.getContext().getKtDiagnosticReporter().at(declaration);
                                                        KtDiagnosticFactory2<String, String> script_capturing_nested_class = JvmBackendErrors.INSTANCE.getSCRIPT_CAPTURING_NESTED_CLASS();
                                                        String strAsString = declaration.getName().asString();
                                                        Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
                                                        IrDeclarationParent parent2 = declaration.getParent();
                                                        IrDeclarationWithName irDeclarationWithName = parent2 instanceof IrDeclarationWithName ? (IrDeclarationWithName) parent2 : null;
                                                        if (irDeclarationWithName == null || (name2 = irDeclarationWithName.getName()) == null) {
                                                            name2 = SpecialNames.NO_NAME_PROVIDED;
                                                        }
                                                        String strAsString2 = name2.asString();
                                                        Intrinsics.checkNotNullExpressionValue(strAsString2, "asString(...)");
                                                        diagnosticContextImplAt.report(script_capturing_nested_class, strAsString, strAsString2);
                                                        break;
                                                    }
                                                    break;
                                                    break;
                                                }
                                                linkedHashSet.add(declaration);
                                                break;
                                            }
                                            visitClass$reportError$default(this, declaration, JvmBackendErrors.INSTANCE.getSCRIPT_CAPTURING_OBJECT(), null, 8, null);
                                            break;
                                        }
                                        visitClass$reportError$default(this, declaration, JvmBackendErrors.INSTANCE.getSCRIPT_CAPTURING_ENUM_ENTRY(), null, 8, null);
                                        break;
                                    }
                                    visitClass$reportError$default(this, declaration, JvmBackendErrors.INSTANCE.getSCRIPT_CAPTURING_ENUM(), null, 8, null);
                                    break;
                                }
                                visitClass$reportError$default(this, declaration, JvmBackendErrors.INSTANCE.getSCRIPT_CAPTURING_INTERFACE(), null, 8, null);
                                break;
                            }
                        }
                    }
                }
                IrElementVisitorVoid.DefaultImpls.visitClass(this, declaration);
            }

            static /* synthetic */ void visitClass$reportError$default(ScriptsToClassesLowering scriptsToClassesLowering, IrClass irClass, KtDiagnosticFactory1 ktDiagnosticFactory1, Name name2, int i, Object obj) {
                if ((i & 8) != 0) {
                    name2 = null;
                }
                visitClass$reportError(scriptsToClassesLowering, irClass, ktDiagnosticFactory1, name2);
            }

            private static final void visitClass$reportError(ScriptsToClassesLowering scriptsToClassesLowering, IrClass irClass, KtDiagnosticFactory1<String> ktDiagnosticFactory1, Name name2) {
                KtDiagnosticReporterWithContext.DiagnosticContextImpl diagnosticContextImplAt = scriptsToClassesLowering.getContext().getKtDiagnosticReporter().at(irClass);
                if (name2 == null) {
                    name2 = irClass.getName();
                }
                String strAsString = name2.asString();
                Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
                diagnosticContextImplAt.report(ktDiagnosticFactory1, strAsString);
            }
        };
        for (IrStatement irStatement : irScript.getStatements()) {
            if (irStatement instanceof IrClassImpl) {
                irElementVisitorVoid.mo6112visitClass((IrClass) irStatement);
            }
        }
        return linkedHashSet;
    }

    /* JADX WARN: Code duplicated, block: B:10:0x00a6  */
    private final void finalizeScriptClass(IrScript irScript, ScriptsToClassesSymbolRemapper symbolRemapper) {
        IrField irField;
        IrProperty owner;
        IrType type;
        ScriptToClassTransformerContext scriptToClassTransformerContext;
        ScriptToClassTransformer scriptToClassTransformer;
        IrClass irClass;
        Iterator it;
        List<IrValueParameter> list;
        Object next;
        IrClassSymbol classOrNull;
        IrClass owner2;
        Sequence<IrConstructor> constructors;
        Name nameShortName;
        Name nameShortName2;
        if (irScript.getThisReceiver() == null) {
            irScript.setThisReceiver(ScriptLoweringKt.createThisReceiverParameter(irScript, this.context, IrDeclarationOrigin.INSTANCE_RECEIVER.INSTANCE, new IrSimpleTypeImpl((IrClassifierSymbol) irScript.getSymbol(), false, CollectionsKt.emptyList(), CollectionsKt.emptyList(), (IrTypeAbbreviation) null, 16, (DefaultConstructorMarker) null)));
        }
        IrClassSymbol targetClass = irScript.getTargetClass();
        Intrinsics.checkNotNull(targetClass);
        IrClass owner3 = targetClass.getOwner();
        SimpleTypeRemapper simpleTypeRemapper = new SimpleTypeRemapper(symbolRemapper);
        Set<IrClassImpl> setCollectCapturingClasses = collectCapturingClasses(irScript, simpleTypeRemapper);
        IrValueParameter earlierScriptsParameter = irScript.getEarlierScriptsParameter();
        if (earlierScriptsParameter != null) {
            IrFactory factory = owner3.getFactory();
            IrDeclarationOrigin.SCRIPT_EARLIER_SCRIPTS script_earlier_scripts = IrDeclarationOrigin.SCRIPT_EARLIER_SCRIPTS.INSTANCE;
            Name nameIdentifier = Name.identifier("$$earlierScripts");
            Intrinsics.checkNotNullExpressionValue(nameIdentifier, "identifier(...)");
            DescriptorVisibility PRIVATE = DescriptorVisibilities.PRIVATE;
            Intrinsics.checkNotNullExpressionValue(PRIVATE, "PRIVATE");
            IrField irFieldCreateField = factory.createField(-1, -1, (IrDeclarationOrigin) script_earlier_scripts, nameIdentifier, PRIVATE, (IrFieldSymbol) new IrFieldSymbolImpl(null, 1, null), earlierScriptsParameter.getType(), true, false, false);
            if (irFieldCreateField != null) {
                irFieldCreateField.setParent(owner3);
                owner3.getDeclarations().add(irFieldCreateField);
                irField = irFieldCreateField;
            } else {
                irField = null;
            }
        } else {
            irField = null;
        }
        ArrayList<Pair<IrField, IrValueParameter>> arrayList = new ArrayList<>();
        List<IrScriptSymbol> importedScripts = irScript.getImportedScripts();
        if (importedScripts != null) {
            Iterator<T> it2 = importedScripts.iterator();
            while (it2.hasNext()) {
                IrClassSymbol targetClass2 = ((IrScriptSymbol) it2.next()).getOwner().getTargetClass();
                Intrinsics.checkNotNull(targetClass2);
                IrSimpleType defaultType = IrUtilsKt.getDefaultType(targetClass2.getOwner());
                StringBuilder sb = new StringBuilder("$$importedScript_");
                IrSimpleType irSimpleType = defaultType;
                FqName classFqName = IrTypesKt.getClassFqName(irSimpleType);
                String strAsString = (classFqName == null || (nameShortName2 = classFqName.shortName()) == null) ? null : nameShortName2.asString();
                Intrinsics.checkNotNull(strAsString);
                Name nameIdentifier2 = Name.identifier(sb.append(strAsString).toString());
                Intrinsics.checkNotNullExpressionValue(nameIdentifier2, "identifier(...)");
                IrValueParameter irValueParameterCreateValueParameter = owner3.getFactory().createValueParameter(-1, -1, (IrDeclarationOrigin) IrDeclarationOrigin.SCRIPT_IMPLICIT_RECEIVER.INSTANCE, nameIdentifier2, (IrType) irSimpleType, false, (IrValueParameterSymbol) new IrValueParameterSymbolImpl(null, 1, null), -1, (IrType) null, false, false, false);
                irValueParameterCreateValueParameter.setParent(owner3);
                arrayList.add(TuplesKt.to(finalizeScriptClass$lambda$14$createField(owner3, simpleTypeRemapper, nameIdentifier2, irSimpleType), irValueParameterCreateValueParameter));
            }
            Unit unit = Unit.INSTANCE;
        }
        for (IrValueParameter irValueParameter : irScript.getImplicitReceiversParameters()) {
            StringBuilder sb2 = new StringBuilder("$$implicitReceiver_");
            FqName classFqName2 = IrTypesKt.getClassFqName(irValueParameter.getType());
            String strAsString2 = (classFqName2 == null || (nameShortName = classFqName2.shortName()) == null) ? null : nameShortName.asString();
            Intrinsics.checkNotNull(strAsString2);
            Name nameIdentifier3 = Name.identifier(sb2.append(strAsString2).toString());
            Intrinsics.checkNotNullExpressionValue(nameIdentifier3, "identifier(...)");
            arrayList.add(TuplesKt.to(finalizeScriptClass$lambda$14$createField(owner3, simpleTypeRemapper, nameIdentifier3, irValueParameter.getType()), irValueParameter));
        }
        ScriptToClassTransformer scriptToClassTransformer2 = new ScriptToClassTransformer(irScript, owner3, simpleTypeRemapper, this.context, setCollectCapturingClasses, this.innerClassesSupport, irField, arrayList);
        ScriptFixLambdasTransformer scriptFixLambdasTransformer = new ScriptFixLambdasTransformer(owner3);
        owner3.setThisReceiver(scriptToClassTransformer2.getScriptClassReceiver());
        IrValueParameter thisReceiver = owner3.getThisReceiver();
        ScriptToClassTransformerContext scriptToClassTransformerContext2 = new ScriptToClassTransformerContext(thisReceiver != null ? thisReceiver.getSymbol() : null, null, null, false, null, 16, null);
        IrValueParameter thisReceiver2 = owner3.getThisReceiver();
        ScriptToClassTransformerContext scriptToClassTransformerContext3 = new ScriptToClassTransformerContext(thisReceiver2 != null ? thisReceiver2.getSymbol() : null, null, null, true, null, 16, null);
        List<IrVariable> explicitCallParameters = irScript.getExplicitCallParameters();
        ArrayList arrayList2 = new ArrayList(CollectionsKt.collectionSizeOrDefault(explicitCallParameters, 10));
        for (IrVariable irVariable : explicitCallParameters) {
            IrFactory factory2 = owner3.getFactory();
            IrFieldBuilder irFieldBuilder = new IrFieldBuilder();
            irFieldBuilder.setStartOffset(irVariable.getStartOffset());
            irFieldBuilder.setEndOffset(irVariable.getEndOffset());
            irFieldBuilder.setOrigin(IrDeclarationOrigin.SCRIPT_CALL_PARAMETER.INSTANCE);
            irFieldBuilder.setName(irVariable.getName());
            irFieldBuilder.setType(irVariable.getType());
            DescriptorVisibility LOCAL = DescriptorVisibilities.LOCAL;
            Intrinsics.checkNotNullExpressionValue(LOCAL, "LOCAL");
            irFieldBuilder.setVisibility(LOCAL);
            irFieldBuilder.setFinal(true);
            IrField irFieldBuildField = DeclarationBuildersKt.buildField(factory2, irFieldBuilder);
            irFieldBuildField.setParent(owner3);
            owner3.getDeclarations().add(irFieldBuildField);
            arrayList2.add(TuplesKt.to(irVariable, irFieldBuildField));
        }
        ArrayList arrayList3 = arrayList2;
        IrConstructor constructor = irScript.getConstructor();
        IrElement irElementFinalizeScriptClass$patchDeclarationForClass = constructor != null ? finalizeScriptClass$patchDeclarationForClass(constructor, scriptToClassTransformer2, scriptToClassTransformerContext2, scriptFixLambdasTransformer) : null;
        IrConstructor irConstructorCreateConstructor = irElementFinalizeScriptClass$patchDeclarationForClass instanceof IrConstructor ? (IrConstructor) irElementFinalizeScriptClass$patchDeclarationForClass : null;
        if (irConstructorCreateConstructor == null) {
            irConstructorCreateConstructor = createConstructor(owner3, irScript, arrayList);
        }
        int i = irScript.getEarlierScriptsParameter() == null ? 0 : 1;
        List<IrValueParameter> listSubList = irConstructorCreateConstructor.getValueParameters().subList(i, irScript.getExplicitCallParameters().size() + i);
        DeclarationIrBuilder declarationIrBuilderCreateIrBuilder$default = LowerUtilsKt.createIrBuilder$default(this.context, irConstructorCreateConstructor.getSymbol(), 0, 0, 6, (Object) null);
        IrBlockBodyBuilder irBlockBodyBuilder = new IrBlockBodyBuilder(declarationIrBuilderCreateIrBuilder$default.getContext(), declarationIrBuilderCreateIrBuilder$default.getScope(), declarationIrBuilderCreateIrBuilder$default.getStartOffset(), declarationIrBuilderCreateIrBuilder$default.getEndOffset());
        IrType baseClass = irScript.getBaseClass();
        IrConstructor irConstructor = (baseClass == null || (classOrNull = IrTypesKt.getClassOrNull(baseClass)) == null || (owner2 = classOrNull.getOwner()) == null || (constructors = IrUtilsKt.getConstructors(owner2)) == null) ? null : (IrConstructor) SequencesKt.firstOrNull(constructors);
        if (irConstructor == null) {
            irBlockBodyBuilder.unaryPlus(ExpressionHelpersKt.irDelegatingConstructorCall(irBlockBodyBuilder, (IrConstructor) SequencesKt.single(IrUtilsKt.getConstructors(irBlockBodyBuilder.getContext().getIrBuiltIns().getAnyClass().getOwner()))));
        } else {
            IrDelegatingConstructorCall irDelegatingConstructorCall = ExpressionHelpersKt.irDelegatingConstructorCall(irBlockBodyBuilder, irConstructor);
            int i2 = 0;
            for (Object obj : listSubList) {
                int i3 = i2 + 1;
                if (i2 < 0) {
                    CollectionsKt.throwIndexOverflow();
                }
                IrValueParameter irValueParameter2 = (IrValueParameter) obj;
                if (i2 < irDelegatingConstructorCall.getValueArgumentsCount()) {
                    irDelegatingConstructorCall.putValueArgument(i2, new IrGetValueImpl(irValueParameter2.getStartOffset(), irValueParameter2.getEndOffset(), irValueParameter2.getType(), irValueParameter2.getSymbol(), null, 16, null));
                }
                i2 = i3;
            }
            irBlockBodyBuilder.unaryPlus(irDelegatingConstructorCall);
        }
        if (irField != null) {
            IrBlockBodyBuilder irBlockBodyBuilder2 = irBlockBodyBuilder;
            IrValueParameter thisReceiver3 = owner3.getThisReceiver();
            Intrinsics.checkNotNull(thisReceiver3);
            IrGetValueImpl irGetValueImplIrGet = ExpressionHelpersKt.irGet(irBlockBodyBuilder2, thisReceiver3);
            IrValueParameter earlierScriptsParameter2 = irScript.getEarlierScriptsParameter();
            Intrinsics.checkNotNull(earlierScriptsParameter2);
            irBlockBodyBuilder.unaryPlus(ExpressionHelpersKt.irSetField$default(irBlockBodyBuilder2, irGetValueImplIrGet, irField, ExpressionHelpersKt.irGet(irBlockBodyBuilder2, earlierScriptsParameter2), null, 8, null));
        }
        Iterator it3 = arrayList3.iterator();
        while (it3.hasNext()) {
            Pair pair = (Pair) it3.next();
            IrVariable irVariable2 = (IrVariable) pair.component1();
            IrField irField2 = (IrField) pair.component2();
            IrBlockBodyBuilder irBlockBodyBuilder3 = irBlockBodyBuilder;
            IrValueParameter thisReceiver4 = owner3.getThisReceiver();
            Intrinsics.checkNotNull(thisReceiver4);
            IrGetValueImpl irGetValueImplIrGet2 = ExpressionHelpersKt.irGet(irBlockBodyBuilder3, thisReceiver4);
            IrType type2 = irVariable2.getType();
            Iterator<T> it4 = listSubList.iterator();
            while (true) {
                if (!it4.hasNext()) {
                    it = it3;
                    list = listSubList;
                    next = null;
                    break;
                } else {
                    next = it4.next();
                    it = it3;
                    list = listSubList;
                    if (Intrinsics.areEqual(((IrValueParameter) next).getName(), irVariable2.getName())) {
                        break;
                    }
                    it3 = it;
                    listSubList = list;
                }
            }
            Intrinsics.checkNotNull(next);
            irBlockBodyBuilder.unaryPlus(ExpressionHelpersKt.irSetField$default(irBlockBodyBuilder3, irGetValueImplIrGet2, irField2, ExpressionHelpersKt.irGet(irBlockBodyBuilder3, type2, ((IrValueParameter) next).getSymbol()), null, 8, null));
            it3 = it;
            listSubList = list;
        }
        Iterator<T> it5 = arrayList.iterator();
        while (it5.hasNext()) {
            Pair pair2 = (Pair) it5.next();
            IrField irField3 = (IrField) pair2.component1();
            IrValueParameter irValueParameter3 = (IrValueParameter) pair2.component2();
            IrBlockBodyBuilder irBlockBodyBuilder4 = irBlockBodyBuilder;
            IrValueParameter thisReceiver5 = owner3.getThisReceiver();
            Intrinsics.checkNotNull(thisReceiver5);
            IrGetValueImpl irGetValueImplIrGet3 = ExpressionHelpersKt.irGet(irBlockBodyBuilder4, thisReceiver5);
            IrElement irElementFinalizeScriptClass$patchDeclarationForClass2 = finalizeScriptClass$patchDeclarationForClass(irValueParameter3, scriptToClassTransformer2, scriptToClassTransformerContext2, scriptFixLambdasTransformer);
            Intrinsics.checkNotNull(irElementFinalizeScriptClass$patchDeclarationForClass2, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.declarations.IrValueParameter");
            irBlockBodyBuilder.unaryPlus(ExpressionHelpersKt.irSetField$default(irBlockBodyBuilder4, irGetValueImplIrGet3, irField3, ExpressionHelpersKt.irGet(irBlockBodyBuilder4, (IrValueParameter) irElementFinalizeScriptClass$patchDeclarationForClass2), null, 8, null));
        }
        irBlockBodyBuilder.unaryPlus(new IrInstanceInitializerCallImpl(irScript.getStartOffset(), irScript.getEndOffset(), owner3.getSymbol(), irBlockBodyBuilder.getContext().getIrBuiltIns().getUnitType()));
        irConstructorCreateConstructor.setBody(irBlockBodyBuilder.getIrBlockBody());
        owner3.getDeclarations().add(irConstructorCreateConstructor);
        IrClass irClass2 = owner3;
        irConstructorCreateConstructor.setParent(irClass2);
        boolean z = false;
        for (IrStatement irStatement : irScript.getStatements()) {
            if (irStatement instanceof IrVariable) {
                IrElement irElementFinalizeScriptClass$patchDeclarationForClass3 = finalizeScriptClass$patchDeclarationForClass(irStatement, scriptToClassTransformer2, scriptToClassTransformerContext2, scriptFixLambdasTransformer);
                Intrinsics.checkNotNull(irElementFinalizeScriptClass$patchDeclarationForClass3, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.declarations.IrVariable");
                scriptToClassTransformerContext = scriptToClassTransformerContext2;
                scriptToClassTransformer = scriptToClassTransformer2;
                irClass = owner3;
                addSimplePropertyFrom$default(this, owner3, (IrVariable) irElementFinalizeScriptClass$patchDeclarationForClass3, null, 2, null);
            } else {
                scriptToClassTransformerContext = scriptToClassTransformerContext2;
                scriptToClassTransformer = scriptToClassTransformer2;
                irClass = owner3;
                if (irStatement instanceof IrDeclaration) {
                    IrElement irElementFinalizeScriptClass$patchDeclarationForClass4 = finalizeScriptClass$patchDeclarationForClass(irStatement, scriptToClassTransformer, scriptToClassTransformerContext, scriptFixLambdasTransformer);
                    Intrinsics.checkNotNull(irElementFinalizeScriptClass$patchDeclarationForClass4, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.declarations.IrDeclaration");
                    IrDeclaration irDeclaration = (IrDeclaration) irElementFinalizeScriptClass$patchDeclarationForClass4;
                    irClass.getDeclarations().add(irDeclaration);
                    if ((irDeclaration instanceof IrSimpleFunction) && Intrinsics.areEqual(((IrSimpleFunction) irDeclaration).getName().asString(), "main")) {
                        z = true;
                    }
                } else {
                    IrElement irElementFinalizeScriptClass$patchTopLevelStatementForClass = finalizeScriptClass$patchTopLevelStatementForClass(irStatement, scriptToClassTransformer, scriptToClassTransformerContext3, scriptFixLambdasTransformer);
                    Intrinsics.checkNotNull(irElementFinalizeScriptClass$patchTopLevelStatementForClass, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.IrStatement");
                    IrStatement irStatement2 = (IrStatement) irElementFinalizeScriptClass$patchTopLevelStatementForClass;
                    IrFunctionBuilder irFunctionBuilder = new IrFunctionBuilder();
                    Unit unit2 = Unit.INSTANCE;
                    irFunctionBuilder.setReturnType(IrUtilsKt.getDefaultType(irClass));
                    IrAnonymousInitializerImpl irAnonymousInitializerImpl = new IrAnonymousInitializerImpl(irFunctionBuilder.getStartOffset(), irFunctionBuilder.getEndOffset(), irFunctionBuilder.getOrigin(), new IrAnonymousInitializerSymbolImpl(null, 1, null), false, null, 48, null);
                    irClass.getDeclarations().add(irAnonymousInitializerImpl);
                    irAnonymousInitializerImpl.setParent(irClass2);
                    IrAnonymousInitializerImpl irAnonymousInitializerImpl2 = irAnonymousInitializerImpl;
                    DeclarationIrBuilder declarationIrBuilderCreateIrBuilder$default2 = LowerUtilsKt.createIrBuilder$default(this.context, irAnonymousInitializerImpl2.getSymbol(), 0, 0, 6, (Object) null);
                    IrBlockBodyBuilder irBlockBodyBuilder5 = new IrBlockBodyBuilder(declarationIrBuilderCreateIrBuilder$default2.getContext(), declarationIrBuilderCreateIrBuilder$default2.getScope(), declarationIrBuilderCreateIrBuilder$default2.getStartOffset(), declarationIrBuilderCreateIrBuilder$default2.getEndOffset());
                    if (irStatement2 instanceof IrComposite) {
                        Iterator<IrStatement> it6 = ((IrComposite) irStatement2).getStatements().iterator();
                        while (it6.hasNext()) {
                            irBlockBodyBuilder5.unaryPlus(it6.next());
                        }
                    } else {
                        irBlockBodyBuilder5.unaryPlus(irStatement2);
                    }
                    irAnonymousInitializerImpl2.setBody(irBlockBodyBuilder5.getIrBlockBody());
                }
            }
            owner3 = irClass;
            scriptToClassTransformerContext2 = scriptToClassTransformerContext;
            scriptToClassTransformer2 = scriptToClassTransformer;
        }
        IrClass irClass3 = owner3;
        if (!z) {
            addScriptMainFun(irClass3);
        }
        List<IrConstructorCall> annotations = irClass3.getAnnotations();
        IrDeclarationParent parent = irClass3.getParent();
        Intrinsics.checkNotNull(parent, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.declarations.IrFile");
        irClass3.setAnnotations(CollectionsKt.plus((Collection) annotations, (Iterable) ((IrFile) parent).getAnnotations()));
        IrPropertySymbol resultProperty = irScript.getResultProperty();
        if (resultProperty == null || (owner = resultProperty.getOwner()) == null) {
            return;
        }
        this.context.getState().getScriptSpecific().setResultFieldName(owner.getName().getIdentifier());
        GenerationState.ForScript scriptSpecific = this.context.getState().getScriptSpecific();
        IrField backingField = owner.getBackingField();
        scriptSpecific.setResultType((backingField == null || (type = backingField.getType()) == null) ? null : IrBasedDescriptorsKt.toIrBasedKotlinType$default(type, null, 1, null));
        Unit unit3 = Unit.INSTANCE;
        Unit unit4 = Unit.INSTANCE;
    }

    private static final IrField finalizeScriptClass$lambda$14$createField(IrClass irClass, SimpleTypeRemapper simpleTypeRemapper, Name name2, IrType irType) {
        IrFactory factory = irClass.getFactory();
        IrDeclarationOrigin.SCRIPT_IMPLICIT_RECEIVER script_implicit_receiver = IrDeclarationOrigin.SCRIPT_IMPLICIT_RECEIVER.INSTANCE;
        DescriptorVisibility PRIVATE = DescriptorVisibilities.PRIVATE;
        Intrinsics.checkNotNullExpressionValue(PRIVATE, "PRIVATE");
        IrField irFieldCreateField = factory.createField(-1, -1, (IrDeclarationOrigin) script_implicit_receiver, name2, PRIVATE, (IrFieldSymbol) new IrFieldSymbolImpl(null, 1, null), simpleTypeRemapper.remapType(irType), true, false, false);
        irFieldCreateField.setParent(irClass);
        irClass.getDeclarations().add(irFieldCreateField);
        return irFieldCreateField;
    }

    private static final <E extends IrElement> IrElement finalizeScriptClass$patchDeclarationForClass(E e, ScriptToClassTransformer scriptToClassTransformer, ScriptToClassTransformerContext scriptToClassTransformerContext, ScriptFixLambdasTransformer scriptFixLambdasTransformer) {
        ScriptToClassTransformerContext scriptToClassTransformerContextCopy$default;
        ScriptToClassTransformer scriptToClassTransformer2 = scriptToClassTransformer;
        IrDeclaration irDeclaration = e instanceof IrDeclaration ? (IrDeclaration) e : null;
        if (irDeclaration != null && (scriptToClassTransformerContextCopy$default = ScriptToClassTransformerContext.copy$default(scriptToClassTransformerContext, null, null, null, false, irDeclaration, 15, null)) != null) {
            scriptToClassTransformerContext = scriptToClassTransformerContextCopy$default;
        }
        return e.transform(scriptToClassTransformer2, scriptToClassTransformerContext).transform(scriptFixLambdasTransformer, new ScriptFixLambdasTransformerContext(false, null, 3, null));
    }

    private static final <E extends IrElement> IrElement finalizeScriptClass$patchTopLevelStatementForClass(E e, ScriptToClassTransformer scriptToClassTransformer, ScriptToClassTransformerContext scriptToClassTransformerContext, ScriptFixLambdasTransformer scriptFixLambdasTransformer) {
        return e.transform(scriptToClassTransformer, scriptToClassTransformerContext).transform(scriptFixLambdasTransformer, new ScriptFixLambdasTransformerContext(false, null, 3, null));
    }

    private final IrConstructor createConstructor(IrClass irScriptClass, IrScript irScript, ArrayList<Pair<IrField, IrValueParameter>> implicitReceiversFieldsWithParameters) {
        int i;
        IrFunctionBuilder irFunctionBuilder = new IrFunctionBuilder();
        int i2 = 1;
        irFunctionBuilder.setPrimary(true);
        IrValueParameter thisReceiver = irScriptClass.getThisReceiver();
        Intrinsics.checkNotNull(thisReceiver);
        IrType type = thisReceiver.getType();
        Intrinsics.checkNotNull(type, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.types.IrSimpleType");
        irFunctionBuilder.setReturnType((IrSimpleType) type);
        IrConstructor irConstructorCreateConstructor = irScriptClass.getFactory().createConstructor(irFunctionBuilder.getStartOffset(), irFunctionBuilder.getEndOffset(), irFunctionBuilder.getOrigin(), SpecialNames.INIT, irFunctionBuilder.getVisibility(), irFunctionBuilder.getIsInline(), irFunctionBuilder.getIsExpect(), irFunctionBuilder.getReturnType(), new IrConstructorSymbolImpl(null, 1, null), irFunctionBuilder.getIsPrimary(), irFunctionBuilder.getIsExternal(), irFunctionBuilder.getContainerSource());
        List listCreateListBuilder = CollectionsKt.createListBuilder();
        IrValueParameter earlierScriptsParameter = irScript.getEarlierScriptsParameter();
        if (earlierScriptsParameter != null) {
            listCreateListBuilder.add(earlierScriptsParameter);
            i = 1;
        } else {
            i = 0;
        }
        List<IrVariable> explicitCallParameters = irScript.getExplicitCallParameters();
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(explicitCallParameters, 10));
        int i3 = i;
        for (IrVariable irVariable : explicitCallParameters) {
            IrValueParameterImpl irValueParameterImpl = new IrValueParameterImpl(irVariable.getStartOffset(), irVariable.getEndOffset(), IrDeclarationOrigin.SCRIPT_CALL_PARAMETER.INSTANCE, new IrValueParameterSymbolImpl(null, i2, null), irVariable.getName(), i3, irVariable.getType(), null, false, false, false, false, null, 4096, null);
            irValueParameterImpl.setParent(irScript);
            arrayList.add(irValueParameterImpl);
            i3++;
            i2 = 1;
        }
        listCreateListBuilder.addAll(arrayList);
        Iterator<T> it = implicitReceiversFieldsWithParameters.iterator();
        while (it.hasNext()) {
            listCreateListBuilder.add((IrValueParameter) ((Pair) it.next()).component2());
        }
        listCreateListBuilder.addAll(irScript.getProvidedPropertiesParameters());
        irConstructorCreateConstructor.setValueParameters(CollectionsKt.build(listCreateListBuilder));
        irConstructorCreateConstructor.setParent(irScript);
        return irConstructorCreateConstructor;
    }

    private final IrExternalPackageFragment getScriptingJvmPackage() {
        return (IrExternalPackageFragment) this.scriptingJvmPackage.getValue();
    }

    private final void addScriptMainFun(IrClass irClass) {
        IrClassSymbol javaLangClass = this.context.getIr().getSymbols().getJavaLangClass();
        IrSimpleFunction kClassJavaPropertyGetter = this.context.getIr().getSymbols().getKClassJavaPropertyGetter();
        IrFactory irFactory = this.context.getIrFactory();
        IrClassBuilder irClassBuilder = new IrClassBuilder();
        Name nameIdentifier = Name.identifier("RunnerKt");
        Intrinsics.checkNotNullExpressionValue(nameIdentifier, "identifier(...)");
        irClassBuilder.setName(nameIdentifier);
        irClassBuilder.setKind(ClassKind.CLASS);
        irClassBuilder.setModality(Modality.FINAL);
        IrClass irClassBuildClass = DeclarationBuildersKt.buildClass(irFactory, irClassBuilder);
        irClassBuildClass.setParent(getScriptingJvmPackage());
        IrUtilsKt.createImplicitParameterDeclarationWithWrappedDescriptor(irClassBuildClass);
        IrSimpleFunction irSimpleFunctionAddFunction$default = DeclarationBuildersKt.addFunction$default(irClassBuildClass, "runCompiledScript", this.context.getIrBuiltIns().getUnitType(), null, null, true, false, false, false, null, 0, 0, XKeySym.XK_Greek_mu, null);
        DeclarationBuildersKt.addValueParameter$default(irSimpleFunctionAddFunction$default, "scriptClass", IrTypesKt.getStarProjectedType(javaLangClass), (IrDeclarationOrigin) null, 4, (Object) null);
        IrValueParameterBuilder irValueParameterBuilder = new IrValueParameterBuilder();
        Name nameIdentifier2 = Name.identifier("args");
        Intrinsics.checkNotNullExpressionValue(nameIdentifier2, "identifier(...)");
        irValueParameterBuilder.setName(nameIdentifier2);
        irValueParameterBuilder.setType(IrTypesKt.typeWith(this.context.getIrBuiltIns().getArrayClass(), this.context.getIrBuiltIns().getStringType()));
        irValueParameterBuilder.setOrigin(IrDeclarationOrigin.DEFINED.INSTANCE);
        irValueParameterBuilder.setVarargElementType(this.context.getIrBuiltIns().getAnyNType());
        if (irValueParameterBuilder.getIndex() == -1) {
            irValueParameterBuilder.setIndex(irSimpleFunctionAddFunction$default.getValueParameters().size());
        }
        irSimpleFunctionAddFunction$default.setValueParameters(CollectionsKt.plus((Collection<? extends IrValueParameter>) irSimpleFunctionAddFunction$default.getValueParameters(), DeclarationBuildersKt.buildValueParameter(irSimpleFunctionAddFunction$default.getFactory(), irValueParameterBuilder, irSimpleFunctionAddFunction$default)));
        IrSimpleFunctionSymbol irSimpleFunctionSymbol = null;
        boolean z = false;
        for (IrSimpleFunctionSymbol irSimpleFunctionSymbol2 : IrUtilsKt.getFunctions(irClassBuildClass.getSymbol())) {
            if (Intrinsics.areEqual(irSimpleFunctionSymbol2.getOwner().getName().asString(), "runCompiledScript")) {
                if (z) {
                    throw new IllegalArgumentException("Sequence contains more than one matching element.");
                }
                z = true;
                irSimpleFunctionSymbol = irSimpleFunctionSymbol2;
            }
        }
        if (z) {
            IrSimpleFunctionSymbol irSimpleFunctionSymbol3 = irSimpleFunctionSymbol;
            IrClassReferenceImpl irClassReferenceImpl = new IrClassReferenceImpl(irClass.getStartOffset(), irClass.getEndOffset(), IrTypesKt.getStarProjectedType(this.context.getIrBuiltIns().getKClassClass()), this.context.getIrBuiltIns().getKClassClass(), IrUtilsKt.getDefaultType(irClass));
            IrFactory factory = irClass.getFactory();
            IrFunctionBuilder irFunctionBuilder = new IrFunctionBuilder();
            Name nameIdentifier3 = Name.identifier("main");
            Intrinsics.checkNotNullExpressionValue(nameIdentifier3, "identifier(...)");
            irFunctionBuilder.setName(nameIdentifier3);
            DescriptorVisibility PUBLIC = DescriptorVisibilities.PUBLIC;
            Intrinsics.checkNotNullExpressionValue(PUBLIC, "PUBLIC");
            irFunctionBuilder.setVisibility(PUBLIC);
            irFunctionBuilder.setReturnType(this.context.getIrBuiltIns().getUnitType());
            irFunctionBuilder.setModality(Modality.FINAL);
            IrSimpleFunction irSimpleFunctionBuildFunction = DeclarationBuildersKt.buildFunction(factory, irFunctionBuilder);
            IrClass irClass2 = irClass;
            irClass2.getDeclarations().add(irSimpleFunctionBuildFunction);
            irSimpleFunctionBuildFunction.setParent(irClass2);
            IrSimpleFunction irSimpleFunction = irSimpleFunctionBuildFunction;
            IrValueParameterBuilder irValueParameterBuilder2 = new IrValueParameterBuilder();
            Name nameIdentifier4 = Name.identifier("args");
            Intrinsics.checkNotNullExpressionValue(nameIdentifier4, "identifier(...)");
            irValueParameterBuilder2.setName(nameIdentifier4);
            irValueParameterBuilder2.setType(IrTypesKt.typeWith(this.context.getIrBuiltIns().getArrayClass(), this.context.getIrBuiltIns().getStringType()));
            if (irValueParameterBuilder2.getIndex() == -1) {
                irValueParameterBuilder2.setIndex(irSimpleFunction.getValueParameters().size());
            }
            IrValueParameter irValueParameterBuildValueParameter = DeclarationBuildersKt.buildValueParameter(irSimpleFunction.getFactory(), irValueParameterBuilder2, irSimpleFunction);
            irSimpleFunction.setValueParameters(CollectionsKt.plus((Collection<? extends IrValueParameter>) irSimpleFunction.getValueParameters(), irValueParameterBuildValueParameter));
            DeclarationIrBuilder declarationIrBuilderCreateIrBuilder$default = LowerUtilsKt.createIrBuilder$default(this.context, irSimpleFunctionBuildFunction.getSymbol(), 0, 0, 6, (Object) null);
            IrCall irCall = ExpressionHelpersKt.irCall((IrBuilderWithScope) declarationIrBuilderCreateIrBuilder$default, irSimpleFunctionSymbol3);
            IrCall irCallIrGet = ExpressionHelpersKt.irGet(declarationIrBuilderCreateIrBuilder$default, IrTypesKt.getStarProjectedType(javaLangClass), null, kClassJavaPropertyGetter.getSymbol());
            irCallIrGet.setExtensionReceiver(irClassReferenceImpl);
            Unit unit = Unit.INSTANCE;
            irCall.putValueArgument(0, irCallIrGet);
            irCall.putValueArgument(1, new IrGetValueImpl(-1, -1, irValueParameterBuildValueParameter.getType(), irValueParameterBuildValueParameter.getSymbol(), null, 16, null));
            irSimpleFunctionBuildFunction.setBody(ExpressionHelpersKt.irExprBody(declarationIrBuilderCreateIrBuilder$default, irCall));
            return;
        }
        throw new NoSuchElementException("Sequence contains no element matching the predicate.");
    }

    static /* synthetic */ void addSimplePropertyFrom$default(ScriptsToClassesLowering scriptsToClassesLowering, IrClass irClass, IrValueDeclaration irValueDeclaration, IrExpressionBody irExpressionBody, int i, Object obj) {
        if ((i & 2) != 0) {
            irExpressionBody = null;
        }
        scriptsToClassesLowering.addSimplePropertyFrom(irClass, irValueDeclaration, irExpressionBody);
    }

    private final void addSimplePropertyFrom(IrClass irClass, IrValueDeclaration irValueDeclaration, IrExpressionBody irExpressionBody) {
        IrFactory factory = irClass.getFactory();
        IrPropertyBuilder irPropertyBuilder = new IrPropertyBuilder();
        irPropertyBuilder.updateFrom((IrDeclaration) irValueDeclaration);
        irPropertyBuilder.setName(irValueDeclaration.getName());
        IrProperty irPropertyBuildProperty = DeclarationBuildersKt.buildProperty(factory, irPropertyBuilder);
        irClass.getDeclarations().add(irPropertyBuildProperty);
        IrClass irClass2 = irClass;
        irPropertyBuildProperty.setParent(irClass2);
        IrFactory irFactory = this.context.getIrFactory();
        IrFieldBuilder irFieldBuilder = new IrFieldBuilder();
        irFieldBuilder.setName(irValueDeclaration.getName());
        irFieldBuilder.setType(irValueDeclaration.getType());
        DescriptorVisibility PROTECTED = DescriptorVisibilities.PROTECTED;
        Intrinsics.checkNotNullExpressionValue(PROTECTED, "PROTECTED");
        irFieldBuilder.setVisibility(PROTECTED);
        IrField irFieldBuildField = DeclarationBuildersKt.buildField(irFactory, irFieldBuilder);
        irFieldBuildField.setParent(irClass2);
        if (irExpressionBody != null) {
            irFieldBuildField.setInitializer(irExpressionBody);
        }
        irPropertyBuildProperty.setBackingField(irFieldBuildField);
        DeclarationBuildersKt.addDefaultGetter(irPropertyBuildProperty, irClass, this.context.getIrBuiltIns());
    }
}
