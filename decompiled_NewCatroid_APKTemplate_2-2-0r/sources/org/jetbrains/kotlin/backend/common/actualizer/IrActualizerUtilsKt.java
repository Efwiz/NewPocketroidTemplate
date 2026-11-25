package org.jetbrains.kotlin.backend.common.actualizer;

import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.jetbrains.kotlin.KtDiagnosticReporterWithImplicitIrBasedContext;
import org.jetbrains.kotlin.backend.common.CommonBackendErrors;
import org.jetbrains.kotlin.descriptors.ClassKind;
import org.jetbrains.kotlin.descriptors.ModuleDescriptor;
import org.jetbrains.kotlin.diagnostics.KtDiagnosticFactory2;
import org.jetbrains.kotlin.diagnostics.KtDiagnosticFactory3;
import org.jetbrains.kotlin.diagnostics.KtDiagnosticReporterWithContext;
import org.jetbrains.kotlin.ir.IrElement;
import org.jetbrains.kotlin.ir.builders.declarations.DeclarationBuildersKt;
import org.jetbrains.kotlin.ir.builders.declarations.IrFunctionBuilder;
import org.jetbrains.kotlin.ir.builders.declarations.IrPropertyBuilder;
import org.jetbrains.kotlin.ir.declarations.IrAnnotationContainer;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationBase;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationOrigin;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationParent;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationWithName;
import org.jetbrains.kotlin.ir.declarations.IrFactory;
import org.jetbrains.kotlin.ir.declarations.IrFunction;
import org.jetbrains.kotlin.ir.declarations.IrOverridableDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrProperty;
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction;
import org.jetbrains.kotlin.ir.declarations.IrSymbolOwner;
import org.jetbrains.kotlin.ir.declarations.IrTypeParameter;
import org.jetbrains.kotlin.ir.declarations.IrTypeParametersContainer;
import org.jetbrains.kotlin.ir.declarations.IrValueParameter;
import org.jetbrains.kotlin.ir.expressions.IrConstructorCall;
import org.jetbrains.kotlin.ir.symbols.IrClassSymbol;
import org.jetbrains.kotlin.ir.symbols.IrPropertySymbol;
import org.jetbrains.kotlin.ir.symbols.IrSimpleFunctionSymbol;
import org.jetbrains.kotlin.ir.symbols.IrSymbol;
import org.jetbrains.kotlin.ir.types.IrTypeSystemContext;
import org.jetbrains.kotlin.ir.util.AdditionalIrUtilsKt;
import org.jetbrains.kotlin.ir.util.DeepCopyIrTreeWithSymbols;
import org.jetbrains.kotlin.ir.util.DeepCopySymbolRemapper;
import org.jetbrains.kotlin.ir.util.DeepCopyTypeRemapper;
import org.jetbrains.kotlin.ir.util.DumpIrTreeOptions;
import org.jetbrains.kotlin.ir.util.IrTypeParameterRemapper;
import org.jetbrains.kotlin.ir.util.IrUtilsKt;
import org.jetbrains.kotlin.ir.util.PatchDeclarationParentsKt;
import org.jetbrains.kotlin.ir.util.RenderIrElementKt;
import org.jetbrains.kotlin.ir.visitors.IrElementTransformer;
import org.jetbrains.kotlin.ir.visitors.IrVisitorsKt;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.resolve.calls.mpp.AbstractExpectActualCompatibilityChecker;
import org.jetbrains.kotlin.resolve.multiplatform.ExpectActualAnnotationsIncompatibilityType;
import org.jetbrains.kotlin.resolve.multiplatform.ExpectActualCompatibility;
import org.jetbrains.kotlin.resolve.multiplatform.OptionalAnnotationUtil;
import org.jetbrains.kotlin.utils.addToStdlib.AddToStdlibKt;

/* JADX INFO: compiled from: IrActualizerUtils.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\u008c\u0001\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010%\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\u001a@\u0010\u0000\u001a\b\u0012\u0004\u0012\u00020\u00020\u00012\u0006\u0010\u0003\u001a\u00020\u00022\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00020\u00012\u0012\u0010\u0005\u001a\u000e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\b0\u00062\u0006\u0010\t\u001a\u00020\nH\u0000\u001a*\u0010\u000b\u001a\u00020\f2\f\u0010\r\u001a\b\u0012\u0004\u0012\u00020\f0\u00012\u0006\u0010\u000e\u001a\u00020\u000f2\n\b\u0002\u0010\u0010\u001a\u0004\u0018\u00010\u0011H\u0002\u001a\"\u0010\u0012\u001a\u0006\u0012\u0002\b\u00030\u00132\f\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00150\u00012\u0006\u0010\u0016\u001a\u00020\u0017H\u0000\u001a\u001e\u0010\u0018\u001a\u00020\u00192\f\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\u00190\u00012\u0006\u0010\u000e\u001a\u00020\u0017H\u0002\u001a,\u0010\u001b\u001a\u00020\u001c2\u0006\u0010\u0003\u001a\u00020\u00022\u0006\u0010\u001d\u001a\u00020\u00022\u0012\u0010\u001e\u001a\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00020\u001fH\u0000\u001a,\u0010 \u001a\u00020\u001c2\u0012\u0010\u001e\u001a\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00020\u001f2\u0006\u0010!\u001a\u00020\"2\u0006\u0010#\u001a\u00020\"H\u0002\u001a\f\u0010$\u001a\u00020%*\u00020&H\u0000\u001a2\u0010'\u001a\u00020\u001c*\u00020(2\u0006\u0010\u0003\u001a\u00020\u00022\u0006\u0010\u001d\u001a\u00020\u00022\f\u0010)\u001a\b\u0012\u0004\u0012\u00020+0*2\u0006\u0010,\u001a\u00020\u0002H\u0000\u001a(\u0010-\u001a\u00020\u001c*\u00020(2\u0006\u0010\u0003\u001a\u00020\u00022\u0006\u0010\u001d\u001a\u00020\u00022\n\u0010.\u001a\u0006\u0012\u0002\b\u00030/H\u0000\u001a\u0014\u00100\u001a\u00020\u001c*\u00020(2\u0006\u00101\u001a\u00020\u0015H\u0000\u001a\u0014\u00100\u001a\u00020\u001c*\u00020(2\u0006\u0010\u0003\u001a\u00020\u0002H\u0000¨\u00062"}, d2 = {"collectActualCallablesMatchingToSpecificExpect", "", "Lorg/jetbrains/kotlin/ir/symbols/IrSymbol;", "expectSymbol", "actualSymbols", "expectToActualClassMap", "", "Lorg/jetbrains/kotlin/name/ClassId;", "Lorg/jetbrains/kotlin/ir/symbols/IrClassSymbol;", "typeSystemContext", "Lorg/jetbrains/kotlin/ir/types/IrTypeSystemContext;", "createFakeOverrideFunction", "Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;", "actualFunctions", "parent", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationParent;", "correspondingPropertySymbol", "Lorg/jetbrains/kotlin/ir/symbols/IrPropertySymbol;", "createFakeOverrideMember", "Lorg/jetbrains/kotlin/ir/declarations/IrOverridableDeclaration;", "actualMembers", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;", "declaration", "Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "createFakeOverrideProperty", "Lorg/jetbrains/kotlin/ir/declarations/IrProperty;", "actualProperties", "recordActualForExpectDeclaration", "", "actualSymbol", FirebaseAnalytics.Param.DESTINATION, "", "recordTypeParametersMapping", "expectTypeParametersContainer", "Lorg/jetbrains/kotlin/ir/declarations/IrTypeParametersContainer;", "actualTypeParametersContainer", "containsOptionalExpectation", "", "Lorg/jetbrains/kotlin/ir/IrElement;", "reportActualAnnotationsNotMatchExpect", "Lorg/jetbrains/kotlin/KtDiagnosticReporterWithImplicitIrBasedContext;", "incompatibilityType", "Lorg/jetbrains/kotlin/resolve/multiplatform/ExpectActualAnnotationsIncompatibilityType;", "Lorg/jetbrains/kotlin/ir/expressions/IrConstructorCall;", "reportOn", "reportIncompatibleExpectActual", "incompatibility", "Lorg/jetbrains/kotlin/resolve/multiplatform/ExpectActualCompatibility$Incompatible;", "reportMissingActual", "irDeclaration", "ir.backend.common"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class IrActualizerUtilsKt {
    public static final List<IrSymbol> collectActualCallablesMatchingToSpecificExpect(final IrSymbol expectSymbol, List<? extends IrSymbol> actualSymbols, final Map<ClassId, ? extends IrClassSymbol> expectToActualClassMap, final IrTypeSystemContext typeSystemContext) {
        Intrinsics.checkNotNullParameter(expectSymbol, "expectSymbol");
        Intrinsics.checkNotNullParameter(actualSymbols, "actualSymbols");
        Intrinsics.checkNotNullParameter(expectToActualClassMap, "expectToActualClassMap");
        Intrinsics.checkNotNullParameter(typeSystemContext, "typeSystemContext");
        final ArrayList arrayList = new ArrayList();
        AbstractExpectActualCompatibilityChecker.INSTANCE.matchSingleExpectTopLevelDeclarationAgainstPotentialActuals(expectSymbol, actualSymbols, new IrExpectActualMatchingContext(typeSystemContext, expectToActualClassMap) { // from class: org.jetbrains.kotlin.backend.common.actualizer.IrActualizerUtilsKt$collectActualCallablesMatchingToSpecificExpect$context$1
            @Override // org.jetbrains.kotlin.backend.common.actualizer.IrExpectActualMatchingContext
            public void onMatchedClasses(IrClassSymbol expectClassSymbol, IrClassSymbol actualClassSymbol) {
                Intrinsics.checkNotNullParameter(expectClassSymbol, "expectClassSymbol");
                Intrinsics.checkNotNullParameter(actualClassSymbol, "actualClassSymbol");
                AddToStdlibKt.shouldNotBeCalled$default(null, 1, null);
                throw null;
            }

            @Override // org.jetbrains.kotlin.backend.common.actualizer.IrExpectActualMatchingContext
            public void onMatchedCallables(IrSymbol expectSymbol2, IrSymbol actualSymbol) {
                Intrinsics.checkNotNullParameter(expectSymbol2, "expectSymbol");
                Intrinsics.checkNotNullParameter(actualSymbol, "actualSymbol");
                if (!Intrinsics.areEqual(expectSymbol2, expectSymbol)) {
                    throw new IllegalArgumentException("Failed requirement.".toString());
                }
                arrayList.add(actualSymbol);
            }
        });
        return arrayList;
    }

    public static final void recordActualForExpectDeclaration(IrSymbol expectSymbol, IrSymbol actualSymbol, Map<IrSymbol, IrSymbol> destination) {
        IrSimpleFunctionSymbol symbol;
        Intrinsics.checkNotNullParameter(expectSymbol, "expectSymbol");
        Intrinsics.checkNotNullParameter(actualSymbol, "actualSymbol");
        Intrinsics.checkNotNullParameter(destination, "destination");
        IrSymbolOwner owner = expectSymbol.getOwner();
        Intrinsics.checkNotNull(owner, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.declarations.IrDeclarationBase");
        IrElement irElement = (IrDeclarationBase) owner;
        IrSymbolOwner owner2 = actualSymbol.getOwner();
        Intrinsics.checkNotNull(owner2, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.declarations.IrDeclaration");
        IrDeclaration irDeclaration = (IrDeclaration) owner2;
        IrSymbol irSymbolPut = destination.put(expectSymbol, actualSymbol);
        if (irSymbolPut != null && !Intrinsics.areEqual(irSymbolPut, actualSymbol)) {
            StringBuilder sbAppend = new StringBuilder("\n            Expect symbol already has registered mapping\n            \n            Expect declaration: ").append(RenderIrElementKt.render$default(irElement, (DumpIrTreeOptions) null, 1, (Object) null)).append("\n            Actual declaration: ").append(RenderIrElementKt.render$default(irDeclaration, (DumpIrTreeOptions) null, 1, (Object) null)).append("\n            Already registered: ");
            Intrinsics.checkNotNull(irSymbolPut);
            throw new IllegalArgumentException(StringsKt.trimIndent(sbAppend.append(RenderIrElementKt.render$default(irSymbolPut.getOwner(), (DumpIrTreeOptions) null, 1, (Object) null)).append("\n        ").toString()).toString());
        }
        if (irElement instanceof IrTypeParametersContainer) {
            recordTypeParametersMapping(destination, (IrTypeParametersContainer) irElement, (IrTypeParametersContainer) irDeclaration);
        }
        if (irElement instanceof IrProperty) {
            IrProperty irProperty = (IrProperty) irDeclaration;
            IrProperty irProperty2 = (IrProperty) irElement;
            IrSimpleFunction getter = irProperty2.getGetter();
            Intrinsics.checkNotNull(getter);
            IrSimpleFunction getter2 = irProperty.getGetter();
            Intrinsics.checkNotNull(getter2);
            destination.put(getter.getSymbol(), getter2.getSymbol());
            recordTypeParametersMapping(destination, getter, getter2);
            IrSimpleFunction setter = irProperty2.getSetter();
            if (setter == null || (symbol = setter.getSymbol()) == null) {
                return;
            }
            IrSimpleFunction setter2 = irProperty.getSetter();
            Intrinsics.checkNotNull(setter2);
            destination.put(symbol, setter2.getSymbol());
        }
    }

    private static final void recordTypeParametersMapping(Map<IrSymbol, IrSymbol> map, IrTypeParametersContainer irTypeParametersContainer, IrTypeParametersContainer irTypeParametersContainer2) {
        for (Pair pair : CollectionsKt.zip(irTypeParametersContainer.getTypeParameters(), irTypeParametersContainer2.getTypeParameters())) {
            map.put(((IrTypeParameter) pair.component1()).getSymbol(), ((IrTypeParameter) pair.component2()).getSymbol());
        }
    }

    public static final void reportMissingActual(KtDiagnosticReporterWithImplicitIrBasedContext ktDiagnosticReporterWithImplicitIrBasedContext, IrSymbol expectSymbol) {
        Intrinsics.checkNotNullParameter(ktDiagnosticReporterWithImplicitIrBasedContext, "<this>");
        Intrinsics.checkNotNullParameter(expectSymbol, "expectSymbol");
        IrSymbolOwner owner = expectSymbol.getOwner();
        Intrinsics.checkNotNull(owner, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.declarations.IrDeclaration");
        reportMissingActual(ktDiagnosticReporterWithImplicitIrBasedContext, (IrDeclaration) owner);
    }

    public static final void reportMissingActual(KtDiagnosticReporterWithImplicitIrBasedContext ktDiagnosticReporterWithImplicitIrBasedContext, IrDeclaration irDeclaration) {
        Name name2;
        Intrinsics.checkNotNullParameter(ktDiagnosticReporterWithImplicitIrBasedContext, "<this>");
        Intrinsics.checkNotNullParameter(irDeclaration, "irDeclaration");
        KtDiagnosticReporterWithContext.DiagnosticContextImpl diagnosticContextImplAt = ktDiagnosticReporterWithImplicitIrBasedContext.at(irDeclaration);
        KtDiagnosticFactory2<String, ModuleDescriptor> no_actual_for_expect = CommonBackendErrors.INSTANCE.getNO_ACTUAL_FOR_EXPECT();
        String strAsString = null;
        IrDeclarationWithName irDeclarationWithName = irDeclaration instanceof IrDeclarationWithName ? (IrDeclarationWithName) irDeclaration : null;
        if (irDeclarationWithName != null && (name2 = irDeclarationWithName.getName()) != null) {
            strAsString = name2.asString();
        }
        if (strAsString == null) {
            strAsString = "";
        }
        diagnosticContextImplAt.report(no_actual_for_expect, strAsString, AdditionalIrUtilsKt.getModule(irDeclaration));
    }

    public static final void reportIncompatibleExpectActual(KtDiagnosticReporterWithImplicitIrBasedContext ktDiagnosticReporterWithImplicitIrBasedContext, IrSymbol expectSymbol, IrSymbol actualSymbol, ExpectActualCompatibility.Incompatible<?> incompatibility) {
        Intrinsics.checkNotNullParameter(ktDiagnosticReporterWithImplicitIrBasedContext, "<this>");
        Intrinsics.checkNotNullParameter(expectSymbol, "expectSymbol");
        Intrinsics.checkNotNullParameter(actualSymbol, "actualSymbol");
        Intrinsics.checkNotNullParameter(incompatibility, "incompatibility");
        IrSymbolOwner owner = expectSymbol.getOwner();
        Intrinsics.checkNotNull(owner, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.declarations.IrDeclaration");
        IrDeclaration irDeclaration = (IrDeclaration) owner;
        IrSymbolOwner owner2 = actualSymbol.getOwner();
        Intrinsics.checkNotNull(owner2, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.declarations.IrDeclaration");
        KtDiagnosticReporterWithContext.DiagnosticContextImpl diagnosticContextImplAt = ktDiagnosticReporterWithImplicitIrBasedContext.at(irDeclaration);
        KtDiagnosticFactory3<String, String, ExpectActualCompatibility.Incompatible<?>> incompatible_matching = CommonBackendErrors.INSTANCE.getINCOMPATIBLE_MATCHING();
        String strAsString = AdditionalIrUtilsKt.getNameWithAssert(irDeclaration).asString();
        Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
        String strAsString2 = AdditionalIrUtilsKt.getNameWithAssert((IrDeclaration) owner2).asString();
        Intrinsics.checkNotNullExpressionValue(strAsString2, "asString(...)");
        diagnosticContextImplAt.report(incompatible_matching, strAsString, strAsString2, incompatibility);
    }

    public static final void reportActualAnnotationsNotMatchExpect(KtDiagnosticReporterWithImplicitIrBasedContext ktDiagnosticReporterWithImplicitIrBasedContext, IrSymbol expectSymbol, IrSymbol actualSymbol, ExpectActualAnnotationsIncompatibilityType<? extends IrConstructorCall> incompatibilityType, IrSymbol reportOn) {
        Intrinsics.checkNotNullParameter(ktDiagnosticReporterWithImplicitIrBasedContext, "<this>");
        Intrinsics.checkNotNullParameter(expectSymbol, "expectSymbol");
        Intrinsics.checkNotNullParameter(actualSymbol, "actualSymbol");
        Intrinsics.checkNotNullParameter(incompatibilityType, "incompatibilityType");
        Intrinsics.checkNotNullParameter(reportOn, "reportOn");
        IrSymbolOwner owner = reportOn.getOwner();
        Intrinsics.checkNotNull(owner, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.declarations.IrDeclaration");
        ktDiagnosticReporterWithImplicitIrBasedContext.at((IrDeclaration) owner).report(CommonBackendErrors.INSTANCE.getACTUAL_ANNOTATIONS_NOT_MATCH_EXPECT(), expectSymbol, actualSymbol, incompatibilityType);
    }

    public static final boolean containsOptionalExpectation(IrElement irElement) {
        Intrinsics.checkNotNullParameter(irElement, "<this>");
        return (irElement instanceof IrClass) && ((IrClass) irElement).getKind() == ClassKind.ANNOTATION_CLASS && IrUtilsKt.hasAnnotation((IrAnnotationContainer) irElement, OptionalAnnotationUtil.INSTANCE.getOPTIONAL_EXPECTATION_FQ_NAME());
    }

    public static final IrOverridableDeclaration<?> createFakeOverrideMember(List<? extends IrDeclaration> actualMembers, IrClass declaration) {
        Intrinsics.checkNotNullParameter(actualMembers, "actualMembers");
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        IrDeclaration irDeclaration = (IrDeclaration) CollectionsKt.first((List) actualMembers);
        if (irDeclaration instanceof IrSimpleFunction) {
            return createFakeOverrideFunction$default(actualMembers, declaration, null, 4, null);
        }
        if (irDeclaration instanceof IrProperty) {
            return createFakeOverrideProperty(actualMembers, declaration);
        }
        throw new IllegalStateException("Only function or property can be overridden".toString());
    }

    private static final IrProperty createFakeOverrideProperty(List<? extends IrProperty> list, IrClass irClass) {
        IrProperty irProperty = (IrProperty) CollectionsKt.first((List) list);
        IrFactory factory = irClass.getFactory();
        IrPropertyBuilder irPropertyBuilder = new IrPropertyBuilder();
        irPropertyBuilder.updateFrom(irProperty);
        irPropertyBuilder.setName(irProperty.getName());
        irPropertyBuilder.setOrigin(IrDeclarationOrigin.FAKE_OVERRIDE.INSTANCE);
        irPropertyBuilder.setFakeOverride(true);
        irPropertyBuilder.setExpect(false);
        IrProperty irPropertyBuildProperty = DeclarationBuildersKt.buildProperty(factory, irPropertyBuilder);
        IrClass irClass2 = irClass;
        irPropertyBuildProperty.setParent(irClass2);
        irPropertyBuildProperty.setAnnotations(irProperty.getAnnotations());
        IrSimpleFunction irSimpleFunctionCreateFakeOverrideFunction = null;
        irPropertyBuildProperty.setBackingField(null);
        List<? extends IrProperty> list2 = list;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list2, 10));
        Iterator<T> it = list2.iterator();
        while (it.hasNext()) {
            IrSimpleFunction getter = ((IrProperty) it.next()).getGetter();
            Intrinsics.checkNotNull(getter, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.declarations.IrSimpleFunction");
            arrayList.add(getter);
        }
        irPropertyBuildProperty.setGetter(createFakeOverrideFunction(arrayList, irClass2, irPropertyBuildProperty.getSymbol()));
        if (irProperty.getSetter() != null) {
            ArrayList arrayList2 = new ArrayList(CollectionsKt.collectionSizeOrDefault(list2, 10));
            Iterator<T> it2 = list2.iterator();
            while (it2.hasNext()) {
                IrSimpleFunction setter = ((IrProperty) it2.next()).getSetter();
                Intrinsics.checkNotNull(setter, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.declarations.IrSimpleFunction");
                arrayList2.add(setter);
            }
            irSimpleFunctionCreateFakeOverrideFunction = createFakeOverrideFunction(arrayList2, irClass2, irPropertyBuildProperty.getSymbol());
        }
        irPropertyBuildProperty.setSetter(irSimpleFunctionCreateFakeOverrideFunction);
        ArrayList arrayList3 = new ArrayList(CollectionsKt.collectionSizeOrDefault(list2, 10));
        Iterator<T> it3 = list2.iterator();
        while (it3.hasNext()) {
            arrayList3.add(((IrProperty) it3.next()).getSymbol());
        }
        irPropertyBuildProperty.setOverriddenSymbols(arrayList3);
        return irPropertyBuildProperty;
    }

    static /* synthetic */ IrSimpleFunction createFakeOverrideFunction$default(List list, IrDeclarationParent irDeclarationParent, IrPropertySymbol irPropertySymbol, int i, Object obj) {
        if ((i & 4) != 0) {
            irPropertySymbol = null;
        }
        return createFakeOverrideFunction(list, irDeclarationParent, irPropertySymbol);
    }

    private static final IrSimpleFunction createFakeOverrideFunction(List<? extends IrSimpleFunction> list, IrDeclarationParent irDeclarationParent, IrPropertySymbol irPropertySymbol) {
        IrSimpleFunction irSimpleFunction = (IrSimpleFunction) CollectionsKt.first((List) list);
        IrFactory factory = irSimpleFunction.getFactory();
        IrFunctionBuilder irFunctionBuilder = new IrFunctionBuilder();
        irFunctionBuilder.updateFrom((IrFunction) irSimpleFunction);
        irFunctionBuilder.setName(irSimpleFunction.getName());
        irFunctionBuilder.setReturnType(irSimpleFunction.getReturnType());
        irFunctionBuilder.setOrigin(IrDeclarationOrigin.FAKE_OVERRIDE.INSTANCE);
        irFunctionBuilder.setFakeOverride(true);
        irFunctionBuilder.setExpect(false);
        IrSimpleFunction irSimpleFunctionBuildFunction = DeclarationBuildersKt.buildFunction(factory, irFunctionBuilder);
        irSimpleFunctionBuildFunction.setParent(irDeclarationParent);
        List<IrConstructorCall> annotations = irSimpleFunction.getAnnotations();
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(annotations, 10));
        Iterator<T> it = annotations.iterator();
        while (true) {
            if (!it.hasNext()) {
                irSimpleFunctionBuildFunction.setAnnotations(arrayList);
                List<IrTypeParameter> typeParameters = irSimpleFunction.getTypeParameters();
                ArrayList arrayList2 = new ArrayList(CollectionsKt.collectionSizeOrDefault(typeParameters, 10));
                for (IrTypeParameter irTypeParameter : typeParameters) {
                    DeepCopySymbolRemapper deepCopySymbolRemapper = new DeepCopySymbolRemapper(null, 1, null);
                    IrVisitorsKt.acceptVoid(irTypeParameter, deepCopySymbolRemapper);
                    DeepCopySymbolRemapper deepCopySymbolRemapper2 = deepCopySymbolRemapper;
                    IrElement irElementPatchDeclarationParents = PatchDeclarationParentsKt.patchDeclarationParents(irTypeParameter.transform((IrElementTransformer<? super Object>) new DeepCopyIrTreeWithSymbols(deepCopySymbolRemapper2, new DeepCopyTypeRemapper(deepCopySymbolRemapper2)), (Object) null), irSimpleFunctionBuildFunction);
                    if (irElementPatchDeclarationParents != null) {
                        arrayList2.add((IrTypeParameter) irElementPatchDeclarationParents);
                    } else {
                        throw new NullPointerException("null cannot be cast to non-null type org.jetbrains.kotlin.ir.declarations.IrTypeParameter");
                    }
                }
                irSimpleFunctionBuildFunction.setTypeParameters(arrayList2);
                IrTypeParameterRemapper irTypeParameterRemapper = new IrTypeParameterRemapper(MapsKt.toMap(CollectionsKt.zip(irSimpleFunction.getTypeParameters(), irSimpleFunctionBuildFunction.getTypeParameters())));
                IrValueParameter dispatchReceiverParameter = irSimpleFunction.getDispatchReceiverParameter();
                irSimpleFunctionBuildFunction.setDispatchReceiverParameter(dispatchReceiverParameter != null ? createFakeOverrideFunction$lambda$16$deepCopyWithTypeParameters(dispatchReceiverParameter, irSimpleFunctionBuildFunction, irTypeParameterRemapper) : null);
                IrValueParameter extensionReceiverParameter = irSimpleFunction.getExtensionReceiverParameter();
                irSimpleFunctionBuildFunction.setExtensionReceiverParameter(extensionReceiverParameter != null ? createFakeOverrideFunction$lambda$16$deepCopyWithTypeParameters(extensionReceiverParameter, irSimpleFunctionBuildFunction, irTypeParameterRemapper) : null);
                List<IrValueParameter> valueParameters = irSimpleFunction.getValueParameters();
                ArrayList arrayList3 = new ArrayList(CollectionsKt.collectionSizeOrDefault(valueParameters, 10));
                Iterator<T> it2 = valueParameters.iterator();
                while (it2.hasNext()) {
                    arrayList3.add(createFakeOverrideFunction$lambda$16$deepCopyWithTypeParameters((IrValueParameter) it2.next(), irSimpleFunctionBuildFunction, irTypeParameterRemapper));
                }
                irSimpleFunctionBuildFunction.setValueParameters(arrayList3);
                irSimpleFunctionBuildFunction.setContextReceiverParametersCount(irSimpleFunction.getContextReceiverParametersCount());
                irSimpleFunctionBuildFunction.setMetadata(irSimpleFunction.getMetadata());
                List<? extends IrSimpleFunction> list2 = list;
                ArrayList arrayList4 = new ArrayList(CollectionsKt.collectionSizeOrDefault(list2, 10));
                Iterator<T> it3 = list2.iterator();
                while (it3.hasNext()) {
                    arrayList4.add(((IrSimpleFunction) it3.next()).getSymbol());
                }
                irSimpleFunctionBuildFunction.setOverriddenSymbols(arrayList4);
                irSimpleFunctionBuildFunction.setAttributeOwnerId(irSimpleFunctionBuildFunction);
                irSimpleFunctionBuildFunction.setCorrespondingPropertySymbol(irPropertySymbol);
                return irSimpleFunctionBuildFunction;
            }
            IrConstructorCall irConstructorCall = (IrConstructorCall) it.next();
            DeepCopySymbolRemapper deepCopySymbolRemapper3 = new DeepCopySymbolRemapper(null, 1, null);
            IrVisitorsKt.acceptVoid(irConstructorCall, deepCopySymbolRemapper3);
            DeepCopySymbolRemapper deepCopySymbolRemapper4 = deepCopySymbolRemapper3;
            IrElement irElementPatchDeclarationParents2 = PatchDeclarationParentsKt.patchDeclarationParents(irConstructorCall.transform((IrElementTransformer<? super Object>) new DeepCopyIrTreeWithSymbols(deepCopySymbolRemapper4, new DeepCopyTypeRemapper(deepCopySymbolRemapper4)), (Object) null), irSimpleFunctionBuildFunction);
            if (irElementPatchDeclarationParents2 != null) {
                arrayList.add((IrConstructorCall) irElementPatchDeclarationParents2);
            } else {
                throw new NullPointerException("null cannot be cast to non-null type org.jetbrains.kotlin.ir.expressions.IrConstructorCall");
            }
        }
    }

    private static final IrValueParameter createFakeOverrideFunction$lambda$16$deepCopyWithTypeParameters(IrValueParameter irValueParameter, IrSimpleFunction irSimpleFunction, IrTypeParameterRemapper irTypeParameterRemapper) {
        IrValueParameter irValueParameter2 = irValueParameter;
        DeepCopySymbolRemapper deepCopySymbolRemapper = new DeepCopySymbolRemapper(null, 1, null);
        IrVisitorsKt.acceptVoid(irValueParameter2, deepCopySymbolRemapper);
        DeepCopySymbolRemapper deepCopySymbolRemapper2 = deepCopySymbolRemapper;
        new DeepCopyTypeRemapper(deepCopySymbolRemapper2);
        IrElement irElementPatchDeclarationParents = PatchDeclarationParentsKt.patchDeclarationParents(irValueParameter2.transform((IrElementTransformer<? super Object>) new DeepCopyIrTreeWithSymbols(deepCopySymbolRemapper2, irTypeParameterRemapper), (Object) null), irSimpleFunction);
        if (irElementPatchDeclarationParents != null) {
            return (IrValueParameter) irElementPatchDeclarationParents;
        }
        throw new NullPointerException("null cannot be cast to non-null type org.jetbrains.kotlin.ir.declarations.IrValueParameter");
    }
}
