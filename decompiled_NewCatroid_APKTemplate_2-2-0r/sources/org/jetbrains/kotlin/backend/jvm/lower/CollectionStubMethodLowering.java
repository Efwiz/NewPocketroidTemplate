package org.jetbrains.kotlin.backend.jvm.lower;

import com.google.android.gms.analytics.ecommerce.ProductAction;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.Sequence;
import kotlin.sequences.SequencesKt;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.backend.common.ClassLoweringPass;
import org.jetbrains.kotlin.backend.jvm.JvmBackendContext;
import org.jetbrains.kotlin.backend.jvm.caches.CollectionStubComputer;
import org.jetbrains.kotlin.backend.jvm.caches.StubsForCollectionClass;
import org.jetbrains.kotlin.backend.jvm.ir.JvmIrUtilsKt;
import org.jetbrains.kotlin.descriptors.Modality;
import org.jetbrains.kotlin.ir.builders.declarations.DeclarationBuildersKt;
import org.jetbrains.kotlin.ir.builders.declarations.IrFunctionBuilder;
import org.jetbrains.kotlin.ir.builders.declarations.IrValueParameterBuilder;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationOrigin;
import org.jetbrains.kotlin.ir.declarations.IrFactory;
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction;
import org.jetbrains.kotlin.ir.declarations.IrTypeParameter;
import org.jetbrains.kotlin.ir.declarations.IrValueParameter;
import org.jetbrains.kotlin.ir.symbols.IrClassSymbol;
import org.jetbrains.kotlin.ir.symbols.IrSimpleFunctionSymbol;
import org.jetbrains.kotlin.ir.symbols.IrTypeParameterSymbol;
import org.jetbrains.kotlin.ir.types.IrSimpleType;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.ir.types.IrTypeArgument;
import org.jetbrains.kotlin.ir.types.IrTypeCheckerStateKt;
import org.jetbrains.kotlin.ir.types.IrTypeProjection;
import org.jetbrains.kotlin.ir.types.IrTypeSystemContextWithAdditionalAxioms;
import org.jetbrains.kotlin.ir.types.IrTypesKt;
import org.jetbrains.kotlin.ir.util.IrTypeUtilsKt;
import org.jetbrains.kotlin.ir.util.IrUtilsKt;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.types.AbstractTypeChecker;
import org.jetbrains.kotlin.types.TypeCheckerState;
import org.jetbrains.kotlin.types.model.KotlinTypeMarker;

/* JADX INFO: compiled from: CollectionStubMethodLowering.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\u0094\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\f\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u001e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\b\u0000\u0018\u00002\u00020\u0001:\u0002HIB\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J \u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u000b2\u0006\u0010\u0019\u001a\u00020\u000b2\u0006\u0010\u001a\u001a\u00020\u001bH\u0002J \u0010\u001c\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u000b2\u0006\u0010\u0019\u001a\u00020\u000b2\u0006\u0010\u001a\u001a\u00020\u001bH\u0002J\u0016\u0010\u001d\u001a\b\u0012\u0004\u0012\u00020\u001f0\u001e2\u0006\u0010\u000e\u001a\u00020\u000fH\u0002J\u0016\u0010 \u001a\b\u0012\u0004\u0012\u00020\u001f0\u001e2\u0006\u0010!\u001a\u00020\u000fH\u0002J,\u0010\"\u001a\u000e\u0012\u0004\u0012\u00020$\u0012\u0004\u0012\u00020%0#2\u0006\u0010&\u001a\u00020\u000f2\u0006\u0010'\u001a\u00020\u000f2\u0006\u0010(\u001a\u00020\u000fH\u0002J \u0010)\u001a\u00020\u000b2\u0006\u0010*\u001a\u00020\u000b2\u0006\u0010+\u001a\u00020%2\u0006\u0010,\u001a\u00020-H\u0002J,\u0010.\u001a\u00020\u000b2\u0006\u0010/\u001a\u00020\u000b2\u0006\u0010!\u001a\u00020\u000f2\u0012\u00100\u001a\u000e\u0012\u0004\u0012\u00020$\u0012\u0004\u0012\u00020%0#H\u0002J\u0018\u00101\u001a\u00020\u001b2\u0006\u0010\u0018\u001a\u00020\u000b2\u0006\u0010\u0019\u001a\u00020\u000bH\u0002J\u0016\u00102\u001a\b\u0012\u0004\u0012\u00020\u000b0\u001e2\u0006\u0010!\u001a\u00020\u000fH\u0002J\u0018\u00103\u001a\u00020\u00172\u0006\u00104\u001a\u00020\u000b2\u0006\u00105\u001a\u00020\u000bH\u0002J%\u00106\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u000b2\u0006\u0010\u0019\u001a\u00020\u000b2\u0006\u0010\u001a\u001a\u00020\u001bH\u0000¢\u0006\u0002\b7J\u0010\u00108\u001a\u00020%2\u0006\u0010/\u001a\u00020\u000bH\u0002J\u0010\u00109\u001a\u00020:2\u0006\u0010!\u001a\u00020\u000fH\u0016J(\u0010;\u001a\u00020<*\u00020<2\u0006\u0010=\u001a\u00020\u000b2\u0012\u0010>\u001a\u000e\u0012\u0004\u0012\u00020%\u0012\u0004\u0012\u00020%0?H\u0002J(\u0010@\u001a\u00020<*\u00020<2\u0006\u0010=\u001a\u00020\u000b2\u0012\u00100\u001a\u000e\u0012\u0004\u0012\u00020$\u0012\u0004\u0012\u00020%0#H\u0002J\u001a\u0010A\u001a\b\u0012\u0004\u0012\u00020\u000b0\u001e*\u00020\u001f2\u0006\u0010!\u001a\u00020\u000fH\u0002J\u001a\u0010B\u001a\u00020%*\b\u0012\u0004\u0012\u00020%0C2\u0006\u0010D\u001a\u00020EH\u0002J\f\u0010F\u001a\u00020G*\u00020\u000bH\u0002R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0018\u0010\t\u001a\u00020\n*\u00020\u000b8BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\f\u0010\rR\u001a\u0010\u000e\u001a\u0004\u0018\u00010\u000f*\u00020\u000f8BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u0010\u0010\u0011R\u001e\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u000f0\u0013*\u00020\u000f8BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u0014\u0010\u0015¨\u0006J"}, d2 = {"Lorg/jetbrains/kotlin/backend/jvm/lower/CollectionStubMethodLowering;", "Lorg/jetbrains/kotlin/backend/common/ClassLoweringPass;", "context", "Lorg/jetbrains/kotlin/backend/jvm/JvmBackendContext;", "(Lorg/jetbrains/kotlin/backend/jvm/JvmBackendContext;)V", "collectionStubComputer", "Lorg/jetbrains/kotlin/backend/jvm/caches/CollectionStubComputer;", "getContext", "()Lorg/jetbrains/kotlin/backend/jvm/JvmBackendContext;", "nameAndArity", "Lorg/jetbrains/kotlin/backend/jvm/lower/CollectionStubMethodLowering$NameAndArity;", "Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;", "getNameAndArity", "(Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;)Lorg/jetbrains/kotlin/backend/jvm/lower/CollectionStubMethodLowering$NameAndArity;", "superClass", "Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "getSuperClass", "(Lorg/jetbrains/kotlin/ir/declarations/IrClass;)Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "superClassChain", "Lkotlin/sequences/Sequence;", "getSuperClassChain", "(Lorg/jetbrains/kotlin/ir/declarations/IrClass;)Lkotlin/sequences/Sequence;", "areTypeParametersEquivalent", "", "overrideFun", "parentFun", "typeChecker", "Lorg/jetbrains/kotlin/types/TypeCheckerState;", "areValueParametersEquivalent", "computeStubsForSuperClass", "", "Lorg/jetbrains/kotlin/backend/jvm/caches/StubsForCollectionClass;", "computeStubsForSuperClasses", "irClass", "computeSubstitutionMap", "", "Lorg/jetbrains/kotlin/ir/symbols/IrTypeParameterSymbol;", "Lorg/jetbrains/kotlin/ir/types/IrType;", "readOnlyClass", "mutableClass", "targetClass", "createRemoveAtStub", "removeAtStub", "stubReturnType", "stubOrigin", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationOrigin;", "createStubMethod", "function", "substitutionMap", "createTypeCheckerState", "generateRelevantStubMethods", "isEffectivelyOverriddenBy", "superFun", "overridingFun", "isReturnTypeOverrideCompliant", "isReturnTypeOverrideCompliant$backend_jvm_lower", "liftStubMethodReturnType", "lower", "", "copyWithCustomTypeSubstitution", "Lorg/jetbrains/kotlin/ir/declarations/IrValueParameter;", "target", "substituteType", "Lkotlin/Function1;", "copyWithSubstitution", "createStubFuns", "findMostSpecificTypeForClass", "", "classifier", "Lorg/jetbrains/kotlin/ir/symbols/IrClassSymbol;", "toJvmSignature", "", "FilteredStubsForCollectionClass", "NameAndArity", "backend.jvm.lower"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class CollectionStubMethodLowering implements ClassLoweringPass {
    private final CollectionStubComputer collectionStubComputer;
    private final JvmBackendContext context;

    public CollectionStubMethodLowering(JvmBackendContext context) {
        Intrinsics.checkNotNullParameter(context, "context");
        this.context = context;
        this.collectionStubComputer = context.getCollectionStubComputer();
    }

    public final JvmBackendContext getContext() {
        return this.context;
    }

    /* JADX INFO: compiled from: CollectionStubMethodLowering.kt */
    @Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\f\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\b\u0082\b\u0018\u00002\u00020\u0001B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0005¢\u0006\u0002\u0010\u0007J\t\u0010\r\u001a\u00020\u0003HÆ\u0003J\t\u0010\u000e\u001a\u00020\u0005HÆ\u0003J\t\u0010\u000f\u001a\u00020\u0005HÆ\u0003J'\u0010\u0010\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u0005HÆ\u0001J\u0013\u0010\u0011\u001a\u00020\u00122\b\u0010\u0013\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0014\u001a\u00020\u0005HÖ\u0001J\t\u0010\u0015\u001a\u00020\u0016HÖ\u0001R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0011\u0010\u0006\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\u000b¨\u0006\u0017"}, d2 = {"Lorg/jetbrains/kotlin/backend/jvm/lower/CollectionStubMethodLowering$NameAndArity;", "", "name", "Lorg/jetbrains/kotlin/name/Name;", "typeParametersCount", "", "valueParametersCount", "(Lorg/jetbrains/kotlin/name/Name;II)V", "getName", "()Lorg/jetbrains/kotlin/name/Name;", "getTypeParametersCount", "()I", "getValueParametersCount", "component1", "component2", "component3", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "hashCode", "toString", "", "backend.jvm.lower"}, k = 1, mv = {1, 9, 0}, xi = 48)
    private static final /* data */ class NameAndArity {
        private final Name name;
        private final int typeParametersCount;
        private final int valueParametersCount;

        public static /* synthetic */ NameAndArity copy$default(NameAndArity nameAndArity, Name name2, int i, int i2, int i3, Object obj) {
            if ((i3 & 1) != 0) {
                name2 = nameAndArity.name;
            }
            if ((i3 & 2) != 0) {
                i = nameAndArity.typeParametersCount;
            }
            if ((i3 & 4) != 0) {
                i2 = nameAndArity.valueParametersCount;
            }
            return nameAndArity.copy(name2, i, i2);
        }

        /* JADX INFO: renamed from: component1, reason: from getter */
        public final Name getName() {
            return this.name;
        }

        /* JADX INFO: renamed from: component2, reason: from getter */
        public final int getTypeParametersCount() {
            return this.typeParametersCount;
        }

        /* JADX INFO: renamed from: component3, reason: from getter */
        public final int getValueParametersCount() {
            return this.valueParametersCount;
        }

        public final NameAndArity copy(Name name2, int typeParametersCount, int valueParametersCount) {
            Intrinsics.checkNotNullParameter(name2, "name");
            return new NameAndArity(name2, typeParametersCount, valueParametersCount);
        }

        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof NameAndArity)) {
                return false;
            }
            NameAndArity nameAndArity = (NameAndArity) other;
            return Intrinsics.areEqual(this.name, nameAndArity.name) && this.typeParametersCount == nameAndArity.typeParametersCount && this.valueParametersCount == nameAndArity.valueParametersCount;
        }

        public int hashCode() {
            return (((this.name.hashCode() * 31) + Integer.hashCode(this.typeParametersCount)) * 31) + Integer.hashCode(this.valueParametersCount);
        }

        public String toString() {
            return "NameAndArity(name=" + this.name + ", typeParametersCount=" + this.typeParametersCount + ", valueParametersCount=" + this.valueParametersCount + ')';
        }

        public NameAndArity(Name name2, int i, int i2) {
            Intrinsics.checkNotNullParameter(name2, "name");
            this.name = name2;
            this.typeParametersCount = i;
            this.valueParametersCount = i2;
        }

        public final Name getName() {
            return this.name;
        }

        public final int getTypeParametersCount() {
            return this.typeParametersCount;
        }

        public final int getValueParametersCount() {
            return this.valueParametersCount;
        }
    }

    private final NameAndArity getNameAndArity(IrSimpleFunction irSimpleFunction) {
        return new NameAndArity(irSimpleFunction.getName(), irSimpleFunction.getTypeParameters().size(), irSimpleFunction.getValueParameters().size());
    }

    @Override // org.jetbrains.kotlin.backend.common.ClassLoweringPass
    public void lower(IrClass irClass) {
        Intrinsics.checkNotNullParameter(irClass, "irClass");
        if (IrUtilsKt.isInterface(irClass)) {
            return;
        }
        List<IrSimpleFunction> listGenerateRelevantStubMethods = generateRelevantStubMethods(irClass);
        if (listGenerateRelevantStubMethods.isEmpty()) {
            return;
        }
        Sequence<IrSimpleFunction> functions = IrUtilsKt.getFunctions(irClass);
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        for (IrSimpleFunction irSimpleFunction : functions) {
            IrSimpleFunction irSimpleFunction2 = irSimpleFunction;
            if (irSimpleFunction2.getModality() == Modality.ABSTRACT && irSimpleFunction2.getIsFakeOverride()) {
                arrayList.add(irSimpleFunction);
            } else {
                arrayList2.add(irSimpleFunction);
            }
        }
        Pair pair = new Pair(arrayList, arrayList2);
        List list = (List) pair.component1();
        List list2 = (List) pair.component2();
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        for (Object obj : list2) {
            NameAndArity nameAndArity = getNameAndArity((IrSimpleFunction) obj);
            Object obj2 = linkedHashMap.get(nameAndArity);
            if (obj2 == null) {
                obj2 = (List) new ArrayList();
                linkedHashMap.put(nameAndArity, obj2);
            }
            ((List) obj2).add(obj);
        }
        LinkedHashMap linkedHashMap2 = new LinkedHashMap();
        for (Object obj3 : list) {
            NameAndArity nameAndArity2 = getNameAndArity((IrSimpleFunction) obj3);
            Object obj4 = linkedHashMap2.get(nameAndArity2);
            if (obj4 == null) {
                obj4 = (List) new ArrayList();
                linkedHashMap2.put(nameAndArity2, obj4);
            }
            ((List) obj4).add(obj3);
        }
        for (IrSimpleFunction irSimpleFunction3 : listGenerateRelevantStubMethods) {
            NameAndArity nameAndArity3 = getNameAndArity(irSimpleFunction3);
            List listEmptyList = (List) linkedHashMap.get(nameAndArity3);
            if (listEmptyList == null) {
                listEmptyList = CollectionsKt.emptyList();
            }
            ArrayList arrayList3 = new ArrayList();
            for (Object obj5 : listEmptyList) {
                if (isEffectivelyOverriddenBy(irSimpleFunction3, (IrSimpleFunction) obj5)) {
                    arrayList3.add(obj5);
                }
            }
            ArrayList<IrSimpleFunction> arrayList4 = arrayList3;
            if (arrayList4.isEmpty()) {
                List listEmptyList2 = (List) linkedHashMap2.get(nameAndArity3);
                if (listEmptyList2 == null) {
                    listEmptyList2 = CollectionsKt.emptyList();
                }
                ArrayList arrayList5 = new ArrayList();
                for (Object obj6 : listEmptyList2) {
                    if (isEffectivelyOverriddenBy((IrSimpleFunction) obj6, irSimpleFunction3)) {
                        arrayList5.add(obj6);
                    }
                }
                List<IrSimpleFunctionSymbol> overriddenSymbols = irSimpleFunction3.getOverriddenSymbols();
                ArrayList arrayList6 = arrayList5;
                ArrayList arrayList7 = new ArrayList(CollectionsKt.collectionSizeOrDefault(arrayList6, 10));
                Iterator it = arrayList6.iterator();
                while (it.hasNext()) {
                    arrayList7.add(((IrSimpleFunction) it.next()).getSymbol());
                }
                irSimpleFunction3.setOverriddenSymbols(CollectionsKt.plus((Collection) overriddenSymbols, (Iterable) arrayList7));
                String strAsString = irSimpleFunction3.getName().asString();
                int iHashCode = strAsString.hashCode();
                if (iHashCode != -934610812) {
                    if (iHashCode == -512823337 && strAsString.equals("removeAt")) {
                        IrSimpleFunction irSimpleFunctionCreateRemoveAtStub = createRemoveAtStub(irSimpleFunction3, irSimpleFunction3.getReturnType(), IrDeclarationOrigin.IR_BUILTINS_STUB.INSTANCE);
                        irClass.getDeclarations().add(irSimpleFunctionCreateRemoveAtStub);
                        IrSimpleFunction irSimpleFunctionCreateRemoveAtStub2 = createRemoveAtStub(irSimpleFunction3, this.context.getIrBuiltIns().getAnyNType(), IrDeclarationOrigin.BRIDGE.INSTANCE);
                        if (!Intrinsics.areEqual(toJvmSignature(irSimpleFunctionCreateRemoveAtStub2), toJvmSignature(irSimpleFunctionCreateRemoveAtStub))) {
                            irClass.getDeclarations().add(irSimpleFunctionCreateRemoveAtStub2);
                        }
                    } else {
                        irClass.getDeclarations().add(irSimpleFunction3);
                    }
                } else if (strAsString.equals(ProductAction.ACTION_REMOVE)) {
                    List<IrDeclaration> declarations = irClass.getDeclarations();
                    List<IrValueParameter> valueParameters = irSimpleFunction3.getValueParameters();
                    ArrayList arrayList8 = new ArrayList(CollectionsKt.collectionSizeOrDefault(valueParameters, 10));
                    Iterator<T> it2 = valueParameters.iterator();
                    while (it2.hasNext()) {
                        arrayList8.add(copyWithCustomTypeSubstitution((IrValueParameter) it2.next(), irSimpleFunction3, new Function1<IrType, IrType>() { // from class: org.jetbrains.kotlin.backend.jvm.lower.CollectionStubMethodLowering$lower$4$1$1
                            {
                                super(1);
                            }

                            @Override // kotlin.jvm.functions.Function1
                            public final IrType invoke(IrType it3) {
                                Intrinsics.checkNotNullParameter(it3, "it");
                                return this.this$0.getContext().getIrBuiltIns().getAnyNType();
                            }
                        }));
                    }
                    irSimpleFunction3.setValueParameters(arrayList8);
                    declarations.add(irSimpleFunction3);
                } else {
                    irClass.getDeclarations().add(irSimpleFunction3);
                }
            } else {
                for (IrSimpleFunction irSimpleFunction4 : arrayList4) {
                    this.context.recordOverridesWithoutStubs(irSimpleFunction4);
                    irSimpleFunction4.setOverriddenSymbols(CollectionsKt.plus((Collection) irSimpleFunction4.getOverriddenSymbols(), (Iterable) irSimpleFunction3.getOverriddenSymbols()));
                }
            }
        }
    }

    private final IrSimpleFunction createRemoveAtStub(IrSimpleFunction removeAtStub, IrType stubReturnType, IrDeclarationOrigin stubOrigin) {
        IrFactory irFactory = this.context.getIrFactory();
        IrFunctionBuilder irFunctionBuilder = new IrFunctionBuilder();
        Name nameIdentifier = Name.identifier(ProductAction.ACTION_REMOVE);
        Intrinsics.checkNotNullExpressionValue(nameIdentifier, "identifier(...)");
        irFunctionBuilder.setName(nameIdentifier);
        irFunctionBuilder.setReturnType(stubReturnType);
        irFunctionBuilder.setVisibility(removeAtStub.getVisibility());
        irFunctionBuilder.setOrigin(stubOrigin);
        irFunctionBuilder.setModality(Modality.OPEN);
        IrSimpleFunction irSimpleFunctionBuildFunction = DeclarationBuildersKt.buildFunction(irFactory, irFunctionBuilder);
        irSimpleFunctionBuildFunction.setParent(removeAtStub.getParent());
        IrValueParameter dispatchReceiverParameter = removeAtStub.getDispatchReceiverParameter();
        irSimpleFunctionBuildFunction.setDispatchReceiverParameter(dispatchReceiverParameter != null ? copyWithCustomTypeSubstitution(dispatchReceiverParameter, irSimpleFunctionBuildFunction, new Function1<IrType, IrType>() { // from class: org.jetbrains.kotlin.backend.jvm.lower.CollectionStubMethodLowering$createRemoveAtStub$2$1
            @Override // kotlin.jvm.functions.Function1
            public final IrType invoke(IrType it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return it;
            }
        }) : null);
        irSimpleFunctionBuildFunction.setExtensionReceiverParameter(null);
        List<IrValueParameter> valueParameters = removeAtStub.getValueParameters();
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(valueParameters, 10));
        Iterator<T> it = valueParameters.iterator();
        while (it.hasNext()) {
            arrayList.add(copyWithCustomTypeSubstitution((IrValueParameter) it.next(), irSimpleFunctionBuildFunction, new Function1<IrType, IrType>() { // from class: org.jetbrains.kotlin.backend.jvm.lower.CollectionStubMethodLowering$createRemoveAtStub$2$2$1
                @Override // kotlin.jvm.functions.Function1
                public final IrType invoke(IrType it2) {
                    Intrinsics.checkNotNullParameter(it2, "it");
                    return it2;
                }
            }));
        }
        irSimpleFunctionBuildFunction.setValueParameters(arrayList);
        irSimpleFunctionBuildFunction.setBody(CollectionStubMethodLoweringKt.createThrowingStubBody(this.context, irSimpleFunctionBuildFunction));
        return irSimpleFunctionBuildFunction;
    }

    private final String toJvmSignature(IrSimpleFunction irSimpleFunction) {
        String string = this.context.getDefaultMethodSignatureMapper().mapAsmMethod(irSimpleFunction).toString();
        Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
        return string;
    }

    private final IrSimpleFunction createStubMethod(IrSimpleFunction function, IrClass irClass, Map<IrTypeParameterSymbol, ? extends IrType> substitutionMap) {
        IrFactory irFactory = this.context.getIrFactory();
        IrFunctionBuilder irFunctionBuilder = new IrFunctionBuilder();
        irFunctionBuilder.setName(function.getName());
        irFunctionBuilder.setReturnType(IrTypeUtilsKt.substitute(liftStubMethodReturnType(function), substitutionMap));
        irFunctionBuilder.setVisibility(function.getVisibility());
        irFunctionBuilder.setOrigin(IrDeclarationOrigin.IR_BUILTINS_STUB.INSTANCE);
        irFunctionBuilder.setModality(Modality.OPEN);
        IrSimpleFunction irSimpleFunctionBuildFunction = DeclarationBuildersKt.buildFunction(irFactory, irFunctionBuilder);
        irSimpleFunctionBuildFunction.setOverriddenSymbols(CollectionsKt.listOf(function.getSymbol()));
        irSimpleFunctionBuildFunction.setParent(irClass);
        IrValueParameter dispatchReceiverParameter = function.getDispatchReceiverParameter();
        irSimpleFunctionBuildFunction.setDispatchReceiverParameter(dispatchReceiverParameter != null ? copyWithSubstitution(dispatchReceiverParameter, irSimpleFunctionBuildFunction, substitutionMap) : null);
        IrValueParameter extensionReceiverParameter = function.getExtensionReceiverParameter();
        irSimpleFunctionBuildFunction.setExtensionReceiverParameter(extensionReceiverParameter != null ? copyWithSubstitution(extensionReceiverParameter, irSimpleFunctionBuildFunction, substitutionMap) : null);
        List<IrValueParameter> valueParameters = function.getValueParameters();
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(valueParameters, 10));
        Iterator<T> it = valueParameters.iterator();
        while (it.hasNext()) {
            arrayList.add(copyWithSubstitution((IrValueParameter) it.next(), irSimpleFunctionBuildFunction, substitutionMap));
        }
        irSimpleFunctionBuildFunction.setValueParameters(arrayList);
        irSimpleFunctionBuildFunction.setBody(CollectionStubMethodLoweringKt.createThrowingStubBody(this.context, irSimpleFunctionBuildFunction));
        return irSimpleFunctionBuildFunction;
    }

    private final IrType liftStubMethodReturnType(IrSimpleFunction function) {
        IrClassSymbol listIterator;
        String strAsString = function.getName().asString();
        int iHashCode = strAsString.hashCode();
        if (iHashCode == -2028626132) {
            if (strAsString.equals("listIterator")) {
                listIterator = this.context.getIr().getSymbols().getListIterator();
                IrType returnType = function.getReturnType();
                Intrinsics.checkNotNull(returnType, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.types.IrSimpleType");
                return IrTypesKt.typeWithArguments(listIterator, ((IrSimpleType) returnType).getArguments());
            }
            return function.getReturnType();
        }
        if (iHashCode == -1868774658) {
            if (strAsString.equals("subList")) {
                listIterator = this.context.getIr().getSymbols().getList();
                IrType returnType2 = function.getReturnType();
                Intrinsics.checkNotNull(returnType2, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.types.IrSimpleType");
                return IrTypesKt.typeWithArguments(listIterator, ((IrSimpleType) returnType2).getArguments());
            }
            return function.getReturnType();
        }
        if (iHashCode == 1182533742 && strAsString.equals("iterator")) {
            listIterator = this.context.getIr().getSymbols().getIterator();
            IrType returnType3 = function.getReturnType();
            Intrinsics.checkNotNull(returnType3, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.types.IrSimpleType");
            return IrTypesKt.typeWithArguments(listIterator, ((IrSimpleType) returnType3).getArguments());
        }
        return function.getReturnType();
    }

    private final boolean isEffectivelyOverriddenBy(IrSimpleFunction superFun, IrSimpleFunction overridingFun) {
        if (!Intrinsics.areEqual(superFun.getName(), overridingFun.getName()) || superFun.getTypeParameters().size() != overridingFun.getTypeParameters().size() || superFun.getValueParameters().size() != overridingFun.getValueParameters().size()) {
            return false;
        }
        if (!superFun.getIsSuspend() && overridingFun.getIsSuspend()) {
            return false;
        }
        TypeCheckerState typeCheckerStateCreateTypeCheckerState = createTypeCheckerState(superFun, overridingFun);
        return areTypeParametersEquivalent(overridingFun, superFun, typeCheckerStateCreateTypeCheckerState) && areValueParametersEquivalent(overridingFun, superFun, typeCheckerStateCreateTypeCheckerState) && isReturnTypeOverrideCompliant$backend_jvm_lower(overridingFun, superFun, typeCheckerStateCreateTypeCheckerState);
    }

    private final TypeCheckerState createTypeCheckerState(IrSimpleFunction overrideFun, IrSimpleFunction parentFun) {
        return IrTypeCheckerStateKt.createIrTypeCheckerState(new IrTypeSystemContextWithAdditionalAxioms(this.context.getTypeSystem(), overrideFun.getTypeParameters(), parentFun.getTypeParameters()));
    }

    private final boolean areTypeParametersEquivalent(IrSimpleFunction overrideFun, IrSimpleFunction parentFun, TypeCheckerState typeChecker) {
        List<Pair> listZip = CollectionsKt.zip(overrideFun.getTypeParameters(), parentFun.getTypeParameters());
        if ((listZip instanceof Collection) && listZip.isEmpty()) {
            return true;
        }
        for (Pair pair : listZip) {
            List<Pair> listZip2 = CollectionsKt.zip(((IrTypeParameter) pair.component1()).getSuperTypes(), ((IrTypeParameter) pair.component2()).getSuperTypes());
            if (!(listZip2 instanceof Collection) || !listZip2.isEmpty()) {
                for (Pair pair2 : listZip2) {
                    if (!AbstractTypeChecker.INSTANCE.equalTypes(typeChecker, (IrType) pair2.component1(), (IrType) pair2.component2())) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    private final boolean areValueParametersEquivalent(IrSimpleFunction overrideFun, IrSimpleFunction parentFun, TypeCheckerState typeChecker) {
        List<Pair> listZip = CollectionsKt.zip(overrideFun.getValueParameters(), parentFun.getValueParameters());
        if ((listZip instanceof Collection) && listZip.isEmpty()) {
            return true;
        }
        for (Pair pair : listZip) {
            if (!AbstractTypeChecker.INSTANCE.equalTypes(typeChecker, ((IrValueParameter) pair.component1()).getType(), ((IrValueParameter) pair.component2()).getType())) {
                return false;
            }
        }
        return true;
    }

    public final boolean isReturnTypeOverrideCompliant$backend_jvm_lower(IrSimpleFunction overrideFun, IrSimpleFunction parentFun, TypeCheckerState typeChecker) {
        Intrinsics.checkNotNullParameter(overrideFun, "overrideFun");
        Intrinsics.checkNotNullParameter(parentFun, "parentFun");
        Intrinsics.checkNotNullParameter(typeChecker, "typeChecker");
        return AbstractTypeChecker.isSubtypeOf$default(AbstractTypeChecker.INSTANCE, typeChecker, (KotlinTypeMarker) overrideFun.getReturnType(), (KotlinTypeMarker) parentFun.getReturnType(), false, 8, (Object) null);
    }

    private final IrValueParameter copyWithSubstitution(IrValueParameter irValueParameter, IrSimpleFunction irSimpleFunction, final Map<IrTypeParameterSymbol, ? extends IrType> map) {
        return copyWithCustomTypeSubstitution(irValueParameter, irSimpleFunction, new Function1<IrType, IrType>() { // from class: org.jetbrains.kotlin.backend.jvm.lower.CollectionStubMethodLowering.copyWithSubstitution.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            /* JADX WARN: Multi-variable type inference failed */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final IrType invoke(IrType it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return IrTypeUtilsKt.substitute(it, map);
            }
        });
    }

    private final Map<IrTypeParameterSymbol, IrType> computeSubstitutionMap(IrClass readOnlyClass, IrClass mutableClass, IrClass targetClass) {
        IrType irTypeFindMostSpecificTypeForClass = findMostSpecificTypeForClass(IrTypeUtilsKt.getAllSubstitutedSupertypes(targetClass), readOnlyClass.getSymbol());
        Intrinsics.checkNotNull(irTypeFindMostSpecificTypeForClass, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.types.IrSimpleType");
        List<IrTypeArgument> arguments = ((IrSimpleType) irTypeFindMostSpecificTypeForClass).getArguments();
        ArrayList arrayList = new ArrayList();
        for (IrTypeArgument irTypeArgument : arguments) {
            IrTypeProjection irTypeProjection = irTypeArgument instanceof IrTypeProjection ? (IrTypeProjection) irTypeArgument : null;
            IrType type = irTypeProjection != null ? irTypeProjection.getType() : null;
            if (type != null) {
                arrayList.add(type);
            }
        }
        ArrayList arrayList2 = arrayList;
        if (arrayList2.isEmpty() || arrayList2.size() != mutableClass.getTypeParameters().size()) {
            throw new IllegalStateException("Type argument mismatch between immutable class " + IrUtilsKt.getFqNameWhenAvailable(readOnlyClass) + " and mutable class " + IrUtilsKt.getFqNameWhenAvailable(mutableClass) + ", when processingclass " + IrUtilsKt.getFqNameWhenAvailable(targetClass));
        }
        List<IrTypeParameter> typeParameters = mutableClass.getTypeParameters();
        ArrayList arrayList3 = new ArrayList(CollectionsKt.collectionSizeOrDefault(typeParameters, 10));
        Iterator<T> it = typeParameters.iterator();
        while (it.hasNext()) {
            arrayList3.add(((IrTypeParameter) it.next()).getSymbol());
        }
        return MapsKt.toMap(CollectionsKt.zip(arrayList3, arrayList2));
    }

    private final List<IrSimpleFunction> generateRelevantStubMethods(IrClass irClass) {
        List<StubsForCollectionClass> listStubsForCollectionClasses = this.collectionStubComputer.stubsForCollectionClasses(irClass);
        ArrayList arrayList = new ArrayList();
        Iterator<T> it = listStubsForCollectionClasses.iterator();
        while (it.hasNext()) {
            CollectionsKt.addAll(arrayList, createStubFuns((StubsForCollectionClass) it.next(), irClass));
        }
        ArrayList arrayList2 = arrayList;
        if (arrayList2.isEmpty()) {
            return arrayList2;
        }
        List<StubsForCollectionClass> listComputeStubsForSuperClasses = computeStubsForSuperClasses(irClass);
        ArrayList arrayList3 = new ArrayList();
        Iterator<T> it2 = listComputeStubsForSuperClasses.iterator();
        while (it2.hasNext()) {
            CollectionsKt.addAll(arrayList3, createStubFuns((StubsForCollectionClass) it2.next(), irClass));
        }
        HashSet hashSet = new HashSet();
        Iterator it3 = arrayList3.iterator();
        while (it3.hasNext()) {
            hashSet.add(toJvmSignature((IrSimpleFunction) it3.next()));
        }
        HashSet hashSet2 = hashSet;
        ArrayList arrayList4 = new ArrayList();
        for (Object obj : arrayList2) {
            if (hashSet2.add(toJvmSignature((IrSimpleFunction) obj))) {
                arrayList4.add(obj);
            }
        }
        return arrayList4;
    }

    private final List<IrSimpleFunction> createStubFuns(StubsForCollectionClass stubsForCollectionClass, IrClass irClass) {
        Map<IrTypeParameterSymbol, IrType> mapComputeSubstitutionMap = computeSubstitutionMap(stubsForCollectionClass.getReadOnlyClass().getOwner(), stubsForCollectionClass.getMutableClass().getOwner(), irClass);
        Collection<IrSimpleFunction> candidatesForStubs = stubsForCollectionClass.getCandidatesForStubs();
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(candidatesForStubs, 10));
        Iterator<T> it = candidatesForStubs.iterator();
        while (it.hasNext()) {
            arrayList.add(createStubMethod((IrSimpleFunction) it.next(), irClass, mapComputeSubstitutionMap));
        }
        return arrayList;
    }

    private final List<StubsForCollectionClass> computeStubsForSuperClasses(IrClass irClass) {
        IrClass superClass = getSuperClass(irClass);
        return superClass == null ? CollectionsKt.emptyList() : SequencesKt.toList(SequencesKt.flatMapIterable(getSuperClassChain(superClass), new Function1<IrClass, List<? extends StubsForCollectionClass>>() { // from class: org.jetbrains.kotlin.backend.jvm.lower.CollectionStubMethodLowering.computeStubsForSuperClasses.1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final List<StubsForCollectionClass> invoke(IrClass superClass2) {
                Intrinsics.checkNotNullParameter(superClass2, "superClass");
                return CollectionStubMethodLowering.this.computeStubsForSuperClass(superClass2);
            }
        }));
    }

    /* JADX INFO: compiled from: CollectionStubMethodLowering.kt */
    @Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0002\b\u0007\b\u0002\u0018\u00002\u00020\u0001B#\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006¢\u0006\u0002\u0010\bR\u001a\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0014\u0010\u0004\u001a\u00020\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0014\u0010\u0002\u001a\u00020\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\f¨\u0006\u000e"}, d2 = {"Lorg/jetbrains/kotlin/backend/jvm/lower/CollectionStubMethodLowering$FilteredStubsForCollectionClass;", "Lorg/jetbrains/kotlin/backend/jvm/caches/StubsForCollectionClass;", "readOnlyClass", "Lorg/jetbrains/kotlin/ir/symbols/IrClassSymbol;", "mutableClass", "candidatesForStubs", "", "Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;", "(Lorg/jetbrains/kotlin/ir/symbols/IrClassSymbol;Lorg/jetbrains/kotlin/ir/symbols/IrClassSymbol;Ljava/util/Collection;)V", "getCandidatesForStubs", "()Ljava/util/Collection;", "getMutableClass", "()Lorg/jetbrains/kotlin/ir/symbols/IrClassSymbol;", "getReadOnlyClass", "backend.jvm.lower"}, k = 1, mv = {1, 9, 0}, xi = 48)
    private static final class FilteredStubsForCollectionClass implements StubsForCollectionClass {
        private final Collection<IrSimpleFunction> candidatesForStubs;
        private final IrClassSymbol mutableClass;
        private final IrClassSymbol readOnlyClass;

        /* JADX WARN: Multi-variable type inference failed */
        public FilteredStubsForCollectionClass(IrClassSymbol readOnlyClass, IrClassSymbol mutableClass, Collection<? extends IrSimpleFunction> candidatesForStubs) {
            Intrinsics.checkNotNullParameter(readOnlyClass, "readOnlyClass");
            Intrinsics.checkNotNullParameter(mutableClass, "mutableClass");
            Intrinsics.checkNotNullParameter(candidatesForStubs, "candidatesForStubs");
            this.readOnlyClass = readOnlyClass;
            this.mutableClass = mutableClass;
            this.candidatesForStubs = candidatesForStubs;
        }

        @Override // org.jetbrains.kotlin.backend.jvm.caches.StubsForCollectionClass
        public IrClassSymbol getReadOnlyClass() {
            return this.readOnlyClass;
        }

        @Override // org.jetbrains.kotlin.backend.jvm.caches.StubsForCollectionClass
        public IrClassSymbol getMutableClass() {
            return this.mutableClass;
        }

        @Override // org.jetbrains.kotlin.backend.jvm.caches.StubsForCollectionClass
        public Collection<IrSimpleFunction> getCandidatesForStubs() {
            return this.candidatesForStubs;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final List<StubsForCollectionClass> computeStubsForSuperClass(IrClass superClass) {
        List<StubsForCollectionClass> listStubsForCollectionClasses = this.collectionStubComputer.stubsForCollectionClasses(superClass);
        if (listStubsForCollectionClasses.isEmpty() || superClass.getModality() != Modality.ABSTRACT) {
            return listStubsForCollectionClasses;
        }
        Sequence sequenceFilter = SequencesKt.filter(IrUtilsKt.getFunctions(superClass), new Function1<IrSimpleFunction, Boolean>() { // from class: org.jetbrains.kotlin.backend.jvm.lower.CollectionStubMethodLowering$computeStubsForSuperClass$abstractFunsByNameAndArity$1
            @Override // kotlin.jvm.functions.Function1
            public final Boolean invoke(IrSimpleFunction it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return Boolean.valueOf(!it.getIsFakeOverride() && it.getModality() == Modality.ABSTRACT);
            }
        });
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        for (Object obj : sequenceFilter) {
            NameAndArity nameAndArity = getNameAndArity((IrSimpleFunction) obj);
            Object obj2 = linkedHashMap.get(nameAndArity);
            if (obj2 == null) {
                obj2 = (List) new ArrayList();
                linkedHashMap.put(nameAndArity, obj2);
            }
            ((List) obj2).add(obj);
        }
        if (linkedHashMap.isEmpty()) {
            return listStubsForCollectionClasses;
        }
        List<StubsForCollectionClass> list = listStubsForCollectionClasses;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
        for (StubsForCollectionClass stubsForCollectionClass : list) {
            Map<IrTypeParameterSymbol, IrType> mapComputeSubstitutionMap = computeSubstitutionMap(stubsForCollectionClass.getReadOnlyClass().getOwner(), stubsForCollectionClass.getMutableClass().getOwner(), superClass);
            Collection<IrSimpleFunction> candidatesForStubs = stubsForCollectionClass.getCandidatesForStubs();
            ArrayList arrayList2 = new ArrayList();
            for (Object obj3 : candidatesForStubs) {
                IrSimpleFunction irSimpleFunctionCreateStubMethod = createStubMethod((IrSimpleFunction) obj3, superClass, mapComputeSubstitutionMap);
                List listEmptyList = (List) linkedHashMap.get(getNameAndArity(irSimpleFunctionCreateStubMethod));
                if (listEmptyList == null) {
                    listEmptyList = CollectionsKt.emptyList();
                }
                List list2 = listEmptyList;
                if (!(list2 instanceof Collection) || !list2.isEmpty()) {
                    Iterator it = list2.iterator();
                    do {
                        if (it.hasNext()) {
                        }
                    } while (!isEffectivelyOverriddenBy(irSimpleFunctionCreateStubMethod, (IrSimpleFunction) it.next()));
                }
                arrayList2.add(obj3);
            }
            arrayList.add(new FilteredStubsForCollectionClass(stubsForCollectionClass.getReadOnlyClass(), stubsForCollectionClass.getMutableClass(), arrayList2));
        }
        return arrayList;
    }

    private final IrType findMostSpecificTypeForClass(Collection<? extends IrType> collection, IrClassSymbol irClassSymbol) {
        ArrayList arrayList = new ArrayList();
        for (Object obj : collection) {
            if (Intrinsics.areEqual(IrTypesKt.getClassifierOrNull((IrType) obj), irClassSymbol)) {
                arrayList.add(obj);
            }
        }
        ArrayList arrayList2 = arrayList;
        if (arrayList2.isEmpty()) {
            throw new IllegalStateException(("No supertype of " + irClassSymbol + " in " + collection).toString());
        }
        if (arrayList2.size() == 1) {
            return (IrType) CollectionsKt.first((List) arrayList2);
        }
        ArrayList<IrType> arrayList3 = arrayList2;
        for (IrType irType : arrayList3) {
            if (!(arrayList3 instanceof Collection) || !arrayList3.isEmpty()) {
                Iterator it = arrayList3.iterator();
                while (it.hasNext()) {
                    IrClassSymbol classOrNull = IrTypesKt.getClassOrNull((IrType) it.next());
                    Intrinsics.checkNotNull(classOrNull);
                    if (!org.jetbrains.kotlin.ir.types.IrTypeUtilsKt.isSubtypeOfClass(irType, classOrNull)) {
                    }
                }
            }
            return irType;
        }
        throw new NoSuchElementException("Collection contains no element matching the predicate.");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final IrClass getSuperClass(IrClass irClass) {
        List<IrType> superTypes = irClass.getSuperTypes();
        ArrayList arrayList = new ArrayList();
        Iterator<T> it = superTypes.iterator();
        while (it.hasNext()) {
            IrClass irClass2 = IrTypesKt.getClass((IrType) it.next());
            if (irClass2 != null) {
                arrayList.add(irClass2);
            }
        }
        Object obj = null;
        boolean z = false;
        Object obj2 = null;
        for (Object obj3 : arrayList) {
            if (!JvmIrUtilsKt.isJvmInterface((IrClass) obj3)) {
                if (z) {
                    return (IrClass) obj;
                }
                z = true;
                obj2 = obj3;
            }
        }
        if (z) {
            obj = obj2;
        }
        return (IrClass) obj;
    }

    private final Sequence<IrClass> getSuperClassChain(IrClass irClass) {
        return SequencesKt.generateSequence(irClass, new Function1<IrClass, IrClass>() { // from class: org.jetbrains.kotlin.backend.jvm.lower.CollectionStubMethodLowering$superClassChain$1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final IrClass invoke(IrClass it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return this.this$0.getSuperClass(it);
            }
        });
    }

    private final IrValueParameter copyWithCustomTypeSubstitution(IrValueParameter irValueParameter, IrSimpleFunction irSimpleFunction, Function1<? super IrType, ? extends IrType> function1) {
        IrValueParameterBuilder irValueParameterBuilder = new IrValueParameterBuilder();
        irValueParameterBuilder.setOrigin(IrDeclarationOrigin.IR_BUILTINS_STUB.INSTANCE);
        irValueParameterBuilder.setName(irValueParameter.getName());
        irValueParameterBuilder.setIndex(irValueParameter.getIndex());
        irValueParameterBuilder.setType(function1.invoke(irValueParameter.getType()));
        IrType varargElementType = irValueParameter.getVarargElementType();
        irValueParameterBuilder.setVarargElementType(varargElementType != null ? function1.invoke(varargElementType) : null);
        irValueParameterBuilder.setCrossInline(irValueParameter.getIsCrossinline());
        irValueParameterBuilder.setNoinline(irValueParameter.getIsNoinline());
        IrSimpleFunction irSimpleFunction2 = irSimpleFunction;
        return DeclarationBuildersKt.buildValueParameter(irSimpleFunction2.getFactory(), irValueParameterBuilder, irSimpleFunction2);
    }
}
