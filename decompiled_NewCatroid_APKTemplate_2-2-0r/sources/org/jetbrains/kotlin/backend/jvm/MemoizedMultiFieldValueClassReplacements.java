package org.jetbrains.kotlin.backend.jvm;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import kotlin.text.StringsKt;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.backend.common.lower.LocalDeclarationsLoweringKt;
import org.jetbrains.kotlin.backend.jvm.ir.JvmIrBuilder;
import org.jetbrains.kotlin.backend.jvm.ir.JvmIrBuilderKt;
import org.jetbrains.kotlin.backend.jvm.ir.JvmIrTypeUtilsKt;
import org.jetbrains.kotlin.backend.jvm.ir.JvmIrUtilsKt;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.descriptors.DescriptorVisibilities;
import org.jetbrains.kotlin.ir.IrStatement;
import org.jetbrains.kotlin.ir.builders.ExpressionHelpersKt;
import org.jetbrains.kotlin.ir.builders.IrBlockBuilder;
import org.jetbrains.kotlin.ir.builders.declarations.DeclarationBuildersKt;
import org.jetbrains.kotlin.ir.builders.declarations.IrFunctionBuilder;
import org.jetbrains.kotlin.ir.builders.declarations.IrValueParameterBuilder;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrConstructor;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationOrigin;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationParent;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationsKt;
import org.jetbrains.kotlin.ir.declarations.IrFactory;
import org.jetbrains.kotlin.ir.declarations.IrField;
import org.jetbrains.kotlin.ir.declarations.IrFunction;
import org.jetbrains.kotlin.ir.declarations.IrProperty;
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction;
import org.jetbrains.kotlin.ir.declarations.IrTypeParameter;
import org.jetbrains.kotlin.ir.declarations.IrValueParameter;
import org.jetbrains.kotlin.ir.declarations.IrVariable;
import org.jetbrains.kotlin.ir.expressions.IrComposite;
import org.jetbrains.kotlin.ir.expressions.IrExpression;
import org.jetbrains.kotlin.ir.expressions.IrExpressionBody;
import org.jetbrains.kotlin.ir.expressions.IrStatementOriginImpl;
import org.jetbrains.kotlin.ir.symbols.IrPropertySymbol;
import org.jetbrains.kotlin.ir.symbols.IrTypeParameterSymbol;
import org.jetbrains.kotlin.ir.types.IrSimpleType;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.ir.types.IrTypePredicatesKt;
import org.jetbrains.kotlin.ir.types.IrTypesKt;
import org.jetbrains.kotlin.ir.util.AdditionalIrUtilsKt;
import org.jetbrains.kotlin.ir.util.DumpIrTreeKt;
import org.jetbrains.kotlin.ir.util.DumpIrTreeOptions;
import org.jetbrains.kotlin.ir.util.IrTypeUtilsKt;
import org.jetbrains.kotlin.ir.util.IrUtilsKt;
import org.jetbrains.kotlin.ir.util.RenderIrElementKt;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.load.java.JvmAbi;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.resolve.InlineClassDescriptorResolver;
import org.jetbrains.kotlin.storage.LockBasedStorageManager;
import org.jetbrains.kotlin.storage.MemoizedFunctionToNotNull;
import org.jetbrains.kotlin.storage.MemoizedFunctionToNullable;

/* JADX INFO: compiled from: MemoizedMultiFieldValueClassReplacements.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000Â\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010%\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010#\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\f\n\u0002\u0010\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\"\n\u0002\b\u000b\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\u000e\n\u0002\b\u0006\u0018\u00002\u00020\u0001:\u0002fgB\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\u0016\u00102\u001a\u0002032\u0006\u00104\u001a\u00020\u00122\u0006\u00105\u001a\u00020\u0014J;\u00106\u001a\u00020\u001e2\u0006\u00107\u001a\u00020\t2\u0006\u00108\u001a\u0002092\b\b\u0002\u0010:\u001a\u00020;2\u0017\u0010<\u001a\u0013\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u0002030\u0016¢\u0006\u0002\b=H\u0002J\u0010\u0010>\u001a\u00020\u001c2\u0006\u0010?\u001a\u00020\u001cH\u0002J\u0010\u0010@\u001a\u00020\u001e2\u0006\u00107\u001a\u00020\tH\u0014J\u0010\u0010A\u001a\u00020\u001e2\u0006\u00107\u001a\u00020\tH\u0014J\u0014\u0010B\u001a\b\u0012\u0004\u0012\u00020\u00140C2\u0006\u00104\u001a\u00020\u0012J\u0010\u0010D\u001a\u0004\u0018\u00010\u00172\u0006\u00105\u001a\u00020\u0014J\u0010\u0010E\u001a\u0004\u0018\u00010\u00172\u0006\u0010F\u001a\u00020\u0019J\u0012\u0010G\u001a\u0004\u0018\u00010\u001a2\u0006\u0010F\u001a\u00020\u0019H\u0002J\u0012\u0010H\u001a\u0004\u0018\u00010\u001c2\u0006\u0010?\u001a\u00020\u001cH\u0016J\u0010\u0010I\u001a\u0004\u0018\u00010\"2\u0006\u0010J\u001a\u00020\u0012J:\u0010K\u001a\b\u0012\u0004\u0012\u00020\u000b0\n2\u0006\u0010L\u001a\u00020\t2\u0006\u0010M\u001a\u00020;2\u0012\u0010N\u001a\u000e\u0012\u0004\u0012\u00020P\u0012\u0004\u0012\u00020Q0O2\u0006\u0010R\u001a\u00020\tH\u0002Jf\u0010S\u001a\u0010\u0012\u0004\u0012\u00020%\u0012\u0006\u0012\u0004\u0018\u00010&0O2\u0006\u0010T\u001a\u00020U2\u0006\u0010R\u001a\u00020\t2\u0006\u0010L\u001a\u00020\t28\u0010V\u001a4\u0012\u0013\u0012\u00110%¢\u0006\f\bX\u0012\b\bY\u0012\u0004\b\b(Z\u0012\u0013\u0012\u00110Q¢\u0006\f\bX\u0012\b\bY\u0012\u0004\b\b([\u0012\u0006\u0012\u0004\u0018\u00010&0WJ\u0018\u0010\\\u001a\u00020;2\u0006\u0010]\u001a\u00020\u00122\u0006\u0010F\u001a\u00020\u0019H\u0002J$\u0010^\u001a\u0002032\f\u0010_\u001a\b\u0012\u0004\u0012\u00020\u000b0\n2\f\u0010`\u001a\b\u0012\u0004\u0012\u00020\u000b0\nH\u0002JF\u0010a\u001a\b\u0012\u0004\u0012\u00020\u000b0\n*\b\u0012\u0004\u0012\u00020%0\n2\b\u0010Y\u001a\u0004\u0018\u00010b2\u0012\u0010N\u001a\u000e\u0012\u0004\u0012\u00020P\u0012\u0004\u0012\u00020Q0O2\u0006\u0010R\u001a\u00020\t2\u0006\u0010c\u001a\u000209H\u0002J:\u0010a\u001a\u00020\u000b*\u00020%2\b\u0010Y\u001a\u0004\u0018\u00010b2\u0012\u0010N\u001a\u000e\u0012\u0004\u0012\u00020P\u0012\u0004\u0012\u00020Q0O2\u0006\u0010R\u001a\u00020\t2\u0006\u0010c\u001a\u000209H\u0002J\u001a\u0010d\u001a\b\u0012\u0004\u0012\u00020\u000b0\n*\u00020\t2\u0006\u00107\u001a\u00020\tH\u0002J\f\u0010e\u001a\u00020\u0014*\u00020\u0014H\u0002J\f\u0010e\u001a\u00020\u0019*\u00020\u0019H\u0002R#\u0010\u0007\u001a\u0014\u0012\u0004\u0012\u00020\t\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000b0\n0\b¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR#\u0010\u000e\u001a\u0014\u0012\u0004\u0012\u00020\t\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000b0\n0\b¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\rR \u0010\u0010\u001a\u0014\u0012\u0004\u0012\u00020\u0012\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00140\u00130\u0011X\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\u0015\u001a\u000e\u0012\u0004\u0012\u00020\u0014\u0012\u0004\u0012\u00020\u00170\u0016X\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\u0018\u001a\u000e\u0012\u0004\u0012\u00020\u0019\u0012\u0004\u0012\u00020\u001a0\u0016X\u0082\u0004¢\u0006\u0002\n\u0000R\u001c\u0010\u001b\u001a\u0010\u0012\u0004\u0012\u00020\u001c\u0012\u0006\u0012\u0004\u0018\u00010\u001c0\u0016X\u0082\u0004¢\u0006\u0002\n\u0000R\"\u0010\u001d\u001a\u0010\u0012\u0004\u0012\u00020\t\u0012\u0006\u0012\u0004\u0018\u00010\u001e0\u0016X\u0094\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u001f\u0010 R\u001d\u0010!\u001a\u000e\u0012\u0004\u0012\u00020\u0012\u0012\u0004\u0012\u00020\"0\u0016¢\u0006\b\n\u0000\u001a\u0004\b#\u0010 R\u001d\u0010$\u001a\u000e\u0012\u0004\u0012\u00020%\u0012\u0004\u0012\u00020&0\u0011¢\u0006\b\n\u0000\u001a\u0004\b'\u0010(R\u001d\u0010)\u001a\u000e\u0012\u0004\u0012\u00020\u001c\u0012\u0004\u0012\u00020\u001c0\b¢\u0006\b\n\u0000\u001a\u0004\b*\u0010\rR\u001d\u0010+\u001a\u000e\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\t0\b¢\u0006\b\n\u0000\u001a\u0004\b,\u0010\rR\u001d\u0010-\u001a\u000e\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\t0\b¢\u0006\b\n\u0000\u001a\u0004\b.\u0010\rR\u001a\u0010/\u001a\u0004\u0018\u00010\u0014*\u00020\u00198BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b0\u00101¨\u0006h"}, d2 = {"Lorg/jetbrains/kotlin/backend/jvm/MemoizedMultiFieldValueClassReplacements;", "Lorg/jetbrains/kotlin/backend/jvm/MemoizedValueClassAbstractReplacements;", "irFactory", "Lorg/jetbrains/kotlin/ir/declarations/IrFactory;", "context", "Lorg/jetbrains/kotlin/backend/jvm/JvmBackendContext;", "(Lorg/jetbrains/kotlin/ir/declarations/IrFactory;Lorg/jetbrains/kotlin/backend/jvm/JvmBackendContext;)V", "bindingNewFunctionToParameterTemplateStructure", "", "Lorg/jetbrains/kotlin/ir/declarations/IrFunction;", "", "Lorg/jetbrains/kotlin/backend/jvm/MemoizedMultiFieldValueClassReplacements$RemappedParameter;", "getBindingNewFunctionToParameterTemplateStructure", "()Ljava/util/Map;", "bindingOldFunctionToParameterTemplateStructure", "getBindingOldFunctionToParameterTemplateStructure", "fieldsToRemove", "Ljava/util/concurrent/ConcurrentHashMap;", "Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "", "Lorg/jetbrains/kotlin/ir/declarations/IrField;", "getMfvcStandaloneFieldNodeImpl", "Lkotlin/Function1;", "Lorg/jetbrains/kotlin/backend/jvm/NameableMfvcNode;", "getRegularClassMfvcPropertyNodeImpl", "Lorg/jetbrains/kotlin/ir/declarations/IrProperty;", "Lorg/jetbrains/kotlin/backend/jvm/IntermediateMfvcNode;", "getReplacementForRegularClassConstructorImpl", "Lorg/jetbrains/kotlin/ir/declarations/IrConstructor;", "getReplacementFunctionImpl", "Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;", "getGetReplacementFunctionImpl", "()Lkotlin/jvm/functions/Function1;", "getRootMfvcNode", "Lorg/jetbrains/kotlin/backend/jvm/RootMfvcNode;", "getGetRootMfvcNode", "oldMfvcDefaultArguments", "Lorg/jetbrains/kotlin/ir/declarations/IrValueParameter;", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "getOldMfvcDefaultArguments", "()Ljava/util/concurrent/ConcurrentHashMap;", "originalConstructorForConstructorReplacement", "getOriginalConstructorForConstructorReplacement", "originalFunctionForMethodReplacement", "getOriginalFunctionForMethodReplacement", "originalFunctionForStaticReplacement", "getOriginalFunctionForStaticReplacement", "backingFieldIfNotToRemove", "getBackingFieldIfNotToRemove", "(Lorg/jetbrains/kotlin/ir/declarations/IrProperty;)Lorg/jetbrains/kotlin/ir/declarations/IrField;", "addFieldToRemove", "", "clazz", "field", "buildReplacement", "function", "replacementOrigin", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationOrigin;", "noFakeOverride", "", "body", "Lkotlin/ExtensionFunctionType;", "createConstructorReplacement", JvmAbi.ERASED_INLINE_CONSTRUCTOR_NAME, "createMethodReplacement", "createStaticReplacement", "getFieldsToRemove", "", "getMfvcFieldNode", "getMfvcPropertyNode", "property", "getRegularClassMfvcPropertyNode", "getReplacementForRegularClassConstructor", "getRootMfvcNodeOrNull", "irClass", "makeAndAddGroupedValueParametersFrom", "sourceFunction", "includeDispatcherReceiver", "substitutionMap", "", "Lorg/jetbrains/kotlin/ir/symbols/IrTypeParameterSymbol;", "Lorg/jetbrains/kotlin/ir/types/IrType;", "targetFunction", "mapFunctionMfvcStructures", "irBuilder", "Lorg/jetbrains/kotlin/ir/builders/IrBlockBuilder;", "getArgument", "Lkotlin/Function2;", "Lkotlin/ParameterName;", "name", "sourceParameter", "targetParameterType", "useRootNode", "parent", "verifyStructureCompatibility", "targetStructure", "sourceStructure", "grouped", "", "originWhenFlattened", "makeMethodLikeRemappedParameters", "withAddedStaticReplacementIfNeeded", "FLATTENED_NOTHING_DEFAULT_VALUE", "RemappedParameter", "backend.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class MemoizedMultiFieldValueClassReplacements extends MemoizedValueClassAbstractReplacements {
    private final Map<IrFunction, List<RemappedParameter>> bindingNewFunctionToParameterTemplateStructure;
    private final Map<IrFunction, List<RemappedParameter>> bindingOldFunctionToParameterTemplateStructure;
    private final ConcurrentHashMap<IrClass, Set<IrField>> fieldsToRemove;
    private final Function1<IrField, NameableMfvcNode> getMfvcStandaloneFieldNodeImpl;
    private final Function1<IrProperty, IntermediateMfvcNode> getRegularClassMfvcPropertyNodeImpl;
    private final Function1<IrConstructor, IrConstructor> getReplacementForRegularClassConstructorImpl;
    private final Function1<IrFunction, IrSimpleFunction> getReplacementFunctionImpl;
    private final Function1<IrClass, RootMfvcNode> getRootMfvcNode;
    private final ConcurrentHashMap<IrValueParameter, IrExpression> oldMfvcDefaultArguments;
    private final Map<IrConstructor, IrConstructor> originalConstructorForConstructorReplacement;
    private final Map<IrFunction, IrFunction> originalFunctionForMethodReplacement;
    private final Map<IrFunction, IrFunction> originalFunctionForStaticReplacement;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public MemoizedMultiFieldValueClassReplacements(IrFactory irFactory, final JvmBackendContext context) {
        super(irFactory, context, new LockBasedStorageManager("multi-field-value-class-replacements"));
        Intrinsics.checkNotNullParameter(irFactory, "irFactory");
        Intrinsics.checkNotNullParameter(context, "context");
        this.originalFunctionForStaticReplacement = new ConcurrentHashMap();
        this.originalFunctionForMethodReplacement = new ConcurrentHashMap();
        this.originalConstructorForConstructorReplacement = new ConcurrentHashMap();
        this.oldMfvcDefaultArguments = new ConcurrentHashMap<>();
        this.bindingOldFunctionToParameterTemplateStructure = new ConcurrentHashMap();
        this.bindingNewFunctionToParameterTemplateStructure = new ConcurrentHashMap<IrFunction, List<? extends RemappedParameter>>() { // from class: org.jetbrains.kotlin.backend.jvm.MemoizedMultiFieldValueClassReplacements$bindingNewFunctionToParameterTemplateStructure$1
            @Override // java.util.concurrent.ConcurrentHashMap, java.util.AbstractMap, java.util.Map
            public final /* bridge */ boolean containsKey(Object obj) {
                if (obj instanceof IrFunction) {
                    return containsKey((IrFunction) obj);
                }
                return false;
            }

            public /* bridge */ boolean containsKey(IrFunction irFunction) {
                return super.containsKey((Object) irFunction);
            }

            @Override // java.util.concurrent.ConcurrentHashMap, java.util.AbstractMap, java.util.Map
            public final /* bridge */ boolean containsValue(Object obj) {
                if (obj instanceof List) {
                    return containsValue((List<? extends MemoizedMultiFieldValueClassReplacements.RemappedParameter>) obj);
                }
                return false;
            }

            public /* bridge */ boolean containsValue(List<? extends MemoizedMultiFieldValueClassReplacements.RemappedParameter> list) {
                return super.containsValue((Object) list);
            }

            @Override // java.util.concurrent.ConcurrentHashMap, java.util.AbstractMap, java.util.Map
            public final /* bridge */ Set<Map.Entry<IrFunction, List<MemoizedMultiFieldValueClassReplacements.RemappedParameter>>> entrySet() {
                return getEntries();
            }

            @Override // java.util.concurrent.ConcurrentHashMap, java.util.AbstractMap, java.util.Map
            public final /* bridge */ /* synthetic */ Object get(Object obj) {
                if (obj instanceof IrFunction) {
                    return get((IrFunction) obj);
                }
                return null;
            }

            @Override // java.util.concurrent.ConcurrentHashMap, java.util.AbstractMap, java.util.Map
            public final /* bridge */ List<MemoizedMultiFieldValueClassReplacements.RemappedParameter> get(Object obj) {
                if (obj instanceof IrFunction) {
                    return get((IrFunction) obj);
                }
                return null;
            }

            public /* bridge */ List<MemoizedMultiFieldValueClassReplacements.RemappedParameter> get(IrFunction irFunction) {
                return (List) super.get((Object) irFunction);
            }

            public /* bridge */ Set<Map.Entry<IrFunction, List<MemoizedMultiFieldValueClassReplacements.RemappedParameter>>> getEntries() {
                return super.entrySet();
            }

            public /* bridge */ ConcurrentHashMap.KeySetView<IrFunction, List<MemoizedMultiFieldValueClassReplacements.RemappedParameter>> getKeys() {
                return super.keySet();
            }

            @Override // java.util.concurrent.ConcurrentHashMap, java.util.Map, java.util.concurrent.ConcurrentMap
            public final /* bridge */ /* synthetic */ Object getOrDefault(Object obj, Object obj2) {
                return !(obj instanceof IrFunction) ? obj2 : getOrDefault((IrFunction) obj, (List<? extends MemoizedMultiFieldValueClassReplacements.RemappedParameter>) obj2);
            }

            public final /* bridge */ List getOrDefault(Object obj, List list) {
                return !(obj instanceof IrFunction) ? list : getOrDefault((IrFunction) obj, (List<? extends MemoizedMultiFieldValueClassReplacements.RemappedParameter>) list);
            }

            public /* bridge */ List<MemoizedMultiFieldValueClassReplacements.RemappedParameter> getOrDefault(IrFunction irFunction, List<? extends MemoizedMultiFieldValueClassReplacements.RemappedParameter> list) {
                return (List) super.getOrDefault((Object) irFunction, list);
            }

            public /* bridge */ int getSize() {
                return super.size();
            }

            public /* bridge */ Collection<List<MemoizedMultiFieldValueClassReplacements.RemappedParameter>> getValues() {
                return super.values();
            }

            @Override // java.util.concurrent.ConcurrentHashMap, java.util.AbstractMap, java.util.Map
            public final /* bridge */ ConcurrentHashMap.KeySetView<IrFunction, List<MemoizedMultiFieldValueClassReplacements.RemappedParameter>> keySet() {
                return getKeys();
            }

            @Override // java.util.concurrent.ConcurrentHashMap, java.util.AbstractMap, java.util.Map
            public final /* bridge */ /* synthetic */ Object remove(Object obj) {
                if (obj instanceof IrFunction) {
                    return remove((IrFunction) obj);
                }
                return null;
            }

            @Override // java.util.concurrent.ConcurrentHashMap, java.util.AbstractMap, java.util.Map
            public final /* bridge */ List<MemoizedMultiFieldValueClassReplacements.RemappedParameter> remove(Object obj) {
                if (obj instanceof IrFunction) {
                    return remove((IrFunction) obj);
                }
                return null;
            }

            public /* bridge */ List<MemoizedMultiFieldValueClassReplacements.RemappedParameter> remove(IrFunction irFunction) {
                return (List) super.remove((Object) irFunction);
            }

            @Override // java.util.concurrent.ConcurrentHashMap, java.util.Map, java.util.concurrent.ConcurrentMap
            public final /* bridge */ boolean remove(Object obj, Object obj2) {
                if ((obj instanceof IrFunction) && (obj2 instanceof List)) {
                    return remove((IrFunction) obj, (List<? extends MemoizedMultiFieldValueClassReplacements.RemappedParameter>) obj2);
                }
                return false;
            }

            public /* bridge */ boolean remove(IrFunction irFunction, List<? extends MemoizedMultiFieldValueClassReplacements.RemappedParameter> list) {
                return super.remove((Object) irFunction, (Object) list);
            }

            @Override // java.util.concurrent.ConcurrentHashMap, java.util.AbstractMap, java.util.Map
            public final /* bridge */ int size() {
                return getSize();
            }

            @Override // java.util.concurrent.ConcurrentHashMap, java.util.AbstractMap, java.util.Map
            public final /* bridge */ Collection<List<MemoizedMultiFieldValueClassReplacements.RemappedParameter>> values() {
                return getValues();
            }

            @Override // java.util.concurrent.ConcurrentHashMap, java.util.AbstractMap, java.util.Map
            public List<MemoizedMultiFieldValueClassReplacements.RemappedParameter> put(IrFunction key, List<? extends MemoizedMultiFieldValueClassReplacements.RemappedParameter> value) {
                Intrinsics.checkNotNullParameter(key, "key");
                Intrinsics.checkNotNullParameter(value, "value");
                int explicitParametersCount = IrUtilsKt.getExplicitParametersCount(key);
                Iterator<T> it = value.iterator();
                int size = 0;
                while (it.hasNext()) {
                    size += ((MemoizedMultiFieldValueClassReplacements.RemappedParameter) it.next()).getValueParameters().size();
                }
                if (explicitParametersCount != size) {
                    throw new IllegalArgumentException(("Illegal structure " + value + " for function " + DumpIrTreeKt.dump$default(key, null, 1, null)).toString());
                }
                return (List) super.put(key, value);
            }
        };
        MemoizedFunctionToNullable memoizedFunctionToNullableCreateMemoizedFunctionWithNullableValues = getStorageManager().createMemoizedFunctionWithNullableValues(new Function1<IrFunction, IrSimpleFunction>() { // from class: org.jetbrains.kotlin.backend.jvm.MemoizedMultiFieldValueClassReplacements$getReplacementFunctionImpl$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final IrSimpleFunction invoke(IrFunction irFunction) {
                if (Intrinsics.areEqual(irFunction.getOrigin(), IrDeclarationOrigin.DELEGATED_PROPERTY_ACCESSOR.INSTANCE) && Intrinsics.areEqual(irFunction.getVisibility(), DescriptorVisibilities.LOCAL)) {
                    return null;
                }
                Intrinsics.checkNotNull(irFunction);
                IrFunction irFunction2 = irFunction;
                if (JvmIrUtilsKt.isStaticValueClassReplacement(irFunction2)) {
                    return null;
                }
                if ((Intrinsics.areEqual(irFunction.getOrigin(), IrDeclarationOrigin.GENERATED_MULTI_FIELD_VALUE_CLASS_MEMBER.INSTANCE) && AdditionalIrUtilsKt.isAccessor(irFunction2)) || Intrinsics.areEqual(irFunction.getOrigin(), JvmLoweredDeclarationOrigin.MULTI_FIELD_VALUE_CLASS_GENERATED_IMPL_METHOD.INSTANCE)) {
                    return null;
                }
                if (irFunction.getOrigin().getIsSynthetic() && !Intrinsics.areEqual(irFunction.getOrigin(), IrDeclarationOrigin.SYNTHETIC_GENERATED_SAM_IMPLEMENTATION.INSTANCE)) {
                    if (!(irFunction instanceof IrConstructor)) {
                        return null;
                    }
                    IrConstructor irConstructor = (IrConstructor) irFunction;
                    if (!IrDeclarationsKt.isMultiFieldValueClass(AdditionalIrUtilsKt.getConstructedClass(irConstructor)) || irConstructor.getIsPrimary()) {
                        return null;
                    }
                }
                if (InlineClassAbiKt.isMultiFieldValueClassFieldGetter(irFunction)) {
                    return null;
                }
                IrDeclarationParent parent = irFunction.getParent();
                IrClass irClass = parent instanceof IrClass ? (IrClass) parent : null;
                if (irClass != null && IrDeclarationsKt.isMultiFieldValueClass(irClass)) {
                    if (IrUtilsKt.isValueClassTypedEquals(irFunction)) {
                        IrSimpleFunction irSimpleFunctionCreateStaticReplacement = this.this$0.createStaticReplacement(irFunction);
                        irSimpleFunctionCreateStaticReplacement.setName(InlineClassDescriptorResolver.SPECIALIZED_EQUALS_NAME);
                        return irSimpleFunctionCreateStaticReplacement;
                    }
                    if (this.this$0.isRemoveAtSpecialBuiltinStub(irFunction)) {
                        return null;
                    }
                    if (this.this$0.isValueClassMemberFakeOverriddenFromJvmDefaultInterfaceMethod(irFunction) || Intrinsics.areEqual(irFunction.getOrigin(), IrDeclarationOrigin.IR_BUILTINS_STUB.INSTANCE)) {
                        return this.this$0.createMethodReplacement(irFunction);
                    }
                    return this.this$0.createStaticReplacement(irFunction);
                }
                if (!(irFunction instanceof IrSimpleFunction)) {
                    return null;
                }
                if (IrUtilsKt.isFromJava(irFunction2) && this.this$0.overridesOnlyMethodsFromJava((IrSimpleFunction) irFunction)) {
                    return null;
                }
                List<IrValueParameter> fullValueParameterList = InlineClassAbiKt.getFullValueParameterList(irFunction);
                if ((fullValueParameterList instanceof Collection) && fullValueParameterList.isEmpty()) {
                    return null;
                }
                Iterator<T> it = fullValueParameterList.iterator();
                while (it.hasNext()) {
                    if (MfvcNodeKt.needsMfvcFlattening(((IrValueParameter) it.next()).getType())) {
                        MemoizedMultiFieldValueClassReplacements memoizedMultiFieldValueClassReplacements = this.this$0;
                        JvmBackendContext jvmBackendContext = context;
                        IrSimpleFunction irSimpleFunction = (IrSimpleFunction) irFunction;
                        if (irSimpleFunction.getIsFakeOverride() && memoizedMultiFieldValueClassReplacements.getReplacementFunction(JvmIrUtilsKt.findSuperDeclaration(irSimpleFunction, false, jvmBackendContext.getConfig().getJvmDefaultMode())) == null) {
                            return null;
                        }
                        return this.this$0.createMethodReplacement(irFunction);
                    }
                }
                return null;
            }
        });
        Intrinsics.checkNotNullExpressionValue(memoizedFunctionToNullableCreateMemoizedFunctionWithNullableValues, "createMemoizedFunctionWithNullableValues(...)");
        this.getReplacementFunctionImpl = memoizedFunctionToNullableCreateMemoizedFunctionWithNullableValues;
        MemoizedFunctionToNullable memoizedFunctionToNullableCreateMemoizedFunctionWithNullableValues2 = getStorageManager().createMemoizedFunctionWithNullableValues(new Function1<IrConstructor, IrConstructor>() { // from class: org.jetbrains.kotlin.backend.jvm.MemoizedMultiFieldValueClassReplacements$getReplacementForRegularClassConstructorImpl$1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final IrConstructor invoke(IrConstructor irConstructor) {
                Intrinsics.checkNotNull(irConstructor);
                if (IrUtilsKt.isFromJava(irConstructor)) {
                    return null;
                }
                return this.this$0.createConstructorReplacement(irConstructor);
            }
        });
        Intrinsics.checkNotNullExpressionValue(memoizedFunctionToNullableCreateMemoizedFunctionWithNullableValues2, "createMemoizedFunctionWithNullableValues(...)");
        this.getReplacementForRegularClassConstructorImpl = memoizedFunctionToNullableCreateMemoizedFunctionWithNullableValues2;
        MemoizedFunctionToNotNull memoizedFunctionToNotNullCreateMemoizedFunction = getStorageManager().createMemoizedFunction(new Function1<IrClass, RootMfvcNode>() { // from class: org.jetbrains.kotlin.backend.jvm.MemoizedMultiFieldValueClassReplacements$getRootMfvcNode$1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final RootMfvcNode invoke(IrClass irClass) {
                Intrinsics.checkNotNull(irClass);
                if (!MfvcNodeKt.needsMfvcFlattening(IrUtilsKt.getDefaultType(irClass))) {
                    throw new IllegalArgumentException(RenderIrElementKt.render$default(IrUtilsKt.getDefaultType(irClass), (DumpIrTreeOptions) null, 1, (Object) null).toString());
                }
                return MfvcNodeFactoryKt.getRootNode(context, irClass);
            }
        });
        Intrinsics.checkNotNullExpressionValue(memoizedFunctionToNotNullCreateMemoizedFunction, "createMemoizedFunction(...)");
        this.getRootMfvcNode = memoizedFunctionToNotNullCreateMemoizedFunction;
        MemoizedFunctionToNotNull memoizedFunctionToNotNullCreateMemoizedFunction2 = getStorageManager().createMemoizedFunction(new Function1<IrProperty, IntermediateMfvcNode>() { // from class: org.jetbrains.kotlin.backend.jvm.MemoizedMultiFieldValueClassReplacements$getRegularClassMfvcPropertyNodeImpl$1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final IntermediateMfvcNode invoke(IrProperty property) {
                Intrinsics.checkNotNullParameter(property, "property");
                return MfvcNodeFactoryKt.createIntermediateNodeForMfvcPropertyOfRegularClass(IrUtilsKt.getParentAsClass(property), context, property);
            }
        });
        Intrinsics.checkNotNullExpressionValue(memoizedFunctionToNotNullCreateMemoizedFunction2, "createMemoizedFunction(...)");
        this.getRegularClassMfvcPropertyNodeImpl = memoizedFunctionToNotNullCreateMemoizedFunction2;
        this.fieldsToRemove = new ConcurrentHashMap<>();
        MemoizedFunctionToNotNull memoizedFunctionToNotNullCreateMemoizedFunction3 = getStorageManager().createMemoizedFunction(new Function1<IrField, IntermediateMfvcNode>() { // from class: org.jetbrains.kotlin.backend.jvm.MemoizedMultiFieldValueClassReplacements$getMfvcStandaloneFieldNodeImpl$1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final IntermediateMfvcNode invoke(IrField irField) {
                Intrinsics.checkNotNull(irField);
                return MfvcNodeFactoryKt.createIntermediateNodeForStandaloneMfvcField(IrUtilsKt.getParentAsClass(irField), context, irField);
            }
        });
        Intrinsics.checkNotNullExpressionValue(memoizedFunctionToNotNullCreateMemoizedFunction3, "createMemoizedFunction(...)");
        this.getMfvcStandaloneFieldNodeImpl = memoizedFunctionToNotNullCreateMemoizedFunction3;
    }

    public final Map<IrFunction, IrFunction> getOriginalFunctionForStaticReplacement() {
        return this.originalFunctionForStaticReplacement;
    }

    public final Map<IrFunction, IrFunction> getOriginalFunctionForMethodReplacement() {
        return this.originalFunctionForMethodReplacement;
    }

    public final Map<IrConstructor, IrConstructor> getOriginalConstructorForConstructorReplacement() {
        return this.originalConstructorForConstructorReplacement;
    }

    private final RemappedParameter grouped(final IrValueParameter irValueParameter, final String str, Map<IrTypeParameterSymbol, ? extends IrType> map, final IrFunction irFunction, final IrDeclarationOrigin irDeclarationOrigin) {
        IrExpression expression;
        if (MfvcNodeKt.needsMfvcFlattening(irValueParameter.getType())) {
            RootMfvcNode rootMfvcNodeInvoke = this.getRootMfvcNode.invoke(JvmIrTypeUtilsKt.getErasedUpperBound(irValueParameter.getType()));
            IrExpressionBody defaultValue = irValueParameter.getDefaultValue();
            if (defaultValue != null && (expression = defaultValue.getExpression()) != null) {
                this.oldMfvcDefaultArguments.putIfAbsent(irValueParameter, expression);
            }
            IrType irTypeSubstitute = IrTypeUtilsKt.substitute(irValueParameter.getType(), map);
            Intrinsics.checkNotNull(irTypeSubstitute, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.types.IrSimpleType");
            IrSimpleType irSimpleType = (IrSimpleType) irTypeSubstitute;
            final Map<IrTypeParameterSymbol, IrType> mapMakeTypeArgumentsFromType = MfvcNodeKt.makeTypeArgumentsFromType(irSimpleType);
            return new RemappedParameter.MultiFieldValueClassMapping(rootMfvcNodeInvoke, irSimpleType, (List<? extends IrValueParameter>) MfvcNodeKt.flatMapLeaves(rootMfvcNodeInvoke, new Function1<LeafMfvcNode, List<? extends IrValueParameter>>() { // from class: org.jetbrains.kotlin.backend.jvm.MemoizedMultiFieldValueClassReplacements$grouped$$inlined$mapLeaves$1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public final List<IrValueParameter> invoke(LeafMfvcNode it) {
                    IrExpressionBody irExpressionBodyIrExprBody;
                    Intrinsics.checkNotNullParameter(it, "it");
                    IrFunction irFunction2 = irFunction;
                    IrValueParameterBuilder irValueParameterBuilder = new IrValueParameterBuilder();
                    irValueParameterBuilder.updateFrom(irValueParameter);
                    StringBuilder sb = new StringBuilder();
                    Comparable name2 = str;
                    if (name2 == null) {
                        name2 = irValueParameter.getName();
                    }
                    Name nameIdentifier = Name.identifier(sb.append(name2).append('-').append(MfvcNodeKt.getFullFieldName(it)).toString());
                    Intrinsics.checkNotNullExpressionValue(nameIdentifier, "identifier(...)");
                    irValueParameterBuilder.setName(nameIdentifier);
                    irValueParameterBuilder.setType(IrTypeUtilsKt.substitute(it.getType(), mapMakeTypeArgumentsFromType));
                    irValueParameterBuilder.setOrigin(irDeclarationOrigin);
                    irValueParameterBuilder.setIndex(irFunction.getValueParameters().size());
                    irValueParameterBuilder.setAssignable(irValueParameterBuilder.getIsAssignable() || irValueParameter.getDefaultValue() != null);
                    if (irValueParameterBuilder.getIndex() == -1) {
                        irValueParameterBuilder.setIndex(irFunction2.getValueParameters().size());
                    }
                    IrValueParameter irValueParameterBuildValueParameter = DeclarationBuildersKt.buildValueParameter(irFunction2.getFactory(), irValueParameterBuilder, irFunction2);
                    irFunction2.setValueParameters(CollectionsKt.plus((Collection<? extends IrValueParameter>) irFunction2.getValueParameters(), irValueParameterBuildValueParameter));
                    if (irValueParameter.getDefaultValue() != null) {
                        JvmIrBuilder jvmIrBuilderCreateJvmIrBuilder$default = JvmIrBuilderKt.createJvmIrBuilder$default(this.getContext(), irFunction.getSymbol(), 0, 0, 6, null);
                        irExpressionBodyIrExprBody = ExpressionHelpersKt.irExprBody(jvmIrBuilderCreateJvmIrBuilder$default, ExpressionHelpersKt.irGet(jvmIrBuilderCreateJvmIrBuilder$default, irValueParameterBuildValueParameter));
                    } else {
                        irExpressionBodyIrExprBody = null;
                    }
                    irValueParameterBuildValueParameter.setDefaultValue(irExpressionBodyIrExprBody);
                    if (irValueParameter.getAnnotations().isEmpty()) {
                        return CollectionsKt.listOf(irValueParameterBuildValueParameter);
                    }
                    throw new IllegalArgumentException("Annotations are not supported for MFVC parameters".toString());
                }
            }));
        }
        IrValueParameterBuilder irValueParameterBuilder = new IrValueParameterBuilder();
        irValueParameterBuilder.updateFrom(irValueParameter);
        irValueParameterBuilder.setName(irValueParameter.getName());
        irValueParameterBuilder.setIndex(irFunction.getValueParameters().size());
        if (irValueParameterBuilder.getIndex() == -1) {
            irValueParameterBuilder.setIndex(irFunction.getValueParameters().size());
        }
        IrValueParameter irValueParameterBuildValueParameter = DeclarationBuildersKt.buildValueParameter(irFunction.getFactory(), irValueParameterBuilder, irFunction);
        irFunction.setValueParameters(CollectionsKt.plus((Collection<? extends IrValueParameter>) irFunction.getValueParameters(), irValueParameterBuildValueParameter));
        irValueParameterBuildValueParameter.setDefaultValue(irValueParameter.getDefaultValue());
        IrUtilsKt.copyAnnotationsFrom(irValueParameterBuildValueParameter, irValueParameter);
        return new RemappedParameter.RegularMapping(irValueParameterBuildValueParameter);
    }

    private final List<RemappedParameter> grouped(List<? extends IrValueParameter> list, String str, Map<IrTypeParameterSymbol, ? extends IrType> map, IrFunction irFunction, IrDeclarationOrigin irDeclarationOrigin) {
        List<? extends IrValueParameter> list2 = list;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list2, 10));
        Iterator<T> it = list2.iterator();
        while (it.hasNext()) {
            arrayList.add(grouped((IrValueParameter) it.next(), str, map, irFunction, irDeclarationOrigin));
        }
        return arrayList;
    }

    public final ConcurrentHashMap<IrValueParameter, IrExpression> getOldMfvcDefaultArguments() {
        return this.oldMfvcDefaultArguments;
    }

    static /* synthetic */ IrSimpleFunction buildReplacement$default(MemoizedMultiFieldValueClassReplacements memoizedMultiFieldValueClassReplacements, IrFunction irFunction, IrDeclarationOrigin irDeclarationOrigin, boolean z, Function1 function1, int i, Object obj) {
        if ((i & 4) != 0) {
            z = false;
        }
        return memoizedMultiFieldValueClassReplacements.buildReplacement(irFunction, irDeclarationOrigin, z, function1);
    }

    private final IrSimpleFunction buildReplacement(final IrFunction function, final IrDeclarationOrigin replacementOrigin, boolean noFakeOverride, Function1<? super IrFunction, Unit> body) {
        return commonBuildReplacementInner(function, noFakeOverride, body, new Function1<IrFunctionBuilder, Unit>() { // from class: org.jetbrains.kotlin.backend.jvm.MemoizedMultiFieldValueClassReplacements.buildReplacement.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(IrFunctionBuilder irFunctionBuilder) {
                invoke2(irFunctionBuilder);
                return Unit.INSTANCE;
            }

            /* JADX WARN: Code duplicated, block: B:18:0x0057  */
            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(IrFunctionBuilder commonBuildReplacementInner) {
                JvmLoweredDeclarationOrigin.STATIC_MULTI_FIELD_VALUE_CLASS_CONSTRUCTOR static_multi_field_value_class_constructor;
                Name nameMangledNameFor;
                Intrinsics.checkNotNullParameter(commonBuildReplacementInner, "$this$commonBuildReplacementInner");
                if (Intrinsics.areEqual(function.getOrigin(), IrDeclarationOrigin.GENERATED_MULTI_FIELD_VALUE_CLASS_MEMBER.INSTANCE)) {
                    static_multi_field_value_class_constructor = JvmLoweredDeclarationOrigin.MULTI_FIELD_VALUE_CLASS_GENERATED_IMPL_METHOD.INSTANCE;
                } else {
                    IrFunction irFunction = function;
                    if ((irFunction instanceof IrConstructor) && IrDeclarationsKt.isMultiFieldValueClass(AdditionalIrUtilsKt.getConstructedClass((IrConstructor) irFunction))) {
                        static_multi_field_value_class_constructor = JvmLoweredDeclarationOrigin.STATIC_MULTI_FIELD_VALUE_CLASS_CONSTRUCTOR.INSTANCE;
                    } else {
                        static_multi_field_value_class_constructor = replacementOrigin;
                    }
                }
                commonBuildReplacementInner.setOrigin(static_multi_field_value_class_constructor);
                if (AdditionalIrUtilsKt.isLocal(function)) {
                    IrFunction irFunction2 = function;
                    if (!(irFunction2 instanceof IrSimpleFunction) || ((IrSimpleFunction) irFunction2).getOverriddenSymbols().isEmpty()) {
                        nameMangledNameFor = function.getName();
                    } else {
                        nameMangledNameFor = InlineClassAbi.INSTANCE.mangledNameFor(this.getContext(), function, false, false);
                    }
                } else {
                    nameMangledNameFor = InlineClassAbi.INSTANCE.mangledNameFor(this.getContext(), function, false, false);
                }
                commonBuildReplacementInner.setName(nameMangledNameFor);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final List<RemappedParameter> makeAndAddGroupedValueParametersFrom(IrFunction sourceFunction, boolean includeDispatcherReceiver, Map<IrTypeParameterSymbol, ? extends IrType> substitutionMap, IrFunction targetFunction) {
        ArrayList arrayList = new ArrayList();
        if (sourceFunction.getDispatchReceiverParameter() != null && includeDispatcherReceiver) {
            IrValueParameter thisReceiver = IrUtilsKt.getParentAsClass(sourceFunction).getThisReceiver();
            Intrinsics.checkNotNull(thisReceiver);
            arrayList.add(grouped(thisReceiver, "$dispatchReceiver", substitutionMap, targetFunction, IrDeclarationOrigin.MOVED_DISPATCH_RECEIVER.INSTANCE));
        }
        List listTake = CollectionsKt.take(sourceFunction.getValueParameters(), sourceFunction.getContextReceiverParametersCount());
        ArrayList arrayList2 = new ArrayList(CollectionsKt.collectionSizeOrDefault(listTake, 10));
        int i = 0;
        for (Object obj : listTake) {
            int i2 = i + 1;
            if (i < 0) {
                CollectionsKt.throwIndexOverflow();
            }
            arrayList2.add(grouped((IrValueParameter) obj, "contextReceiver" + i, substitutionMap, targetFunction, IrDeclarationOrigin.MOVED_CONTEXT_RECEIVER.INSTANCE));
            i = i2;
        }
        arrayList.addAll(arrayList2);
        IrValueParameter extensionReceiverParameter = sourceFunction.getExtensionReceiverParameter();
        if (extensionReceiverParameter != null) {
            arrayList.add(grouped(extensionReceiverParameter, JvmIrUtilsKt.extensionReceiverName(sourceFunction, getContext().getState()), substitutionMap, targetFunction, IrDeclarationOrigin.MOVED_EXTENSION_RECEIVER.INSTANCE));
        }
        CollectionsKt.addAll(arrayList, grouped(CollectionsKt.drop(sourceFunction.getValueParameters(), sourceFunction.getContextReceiverParametersCount()), (String) null, substitutionMap, targetFunction, JvmLoweredDeclarationOrigin.GENERATED_MULTI_FIELD_VALUE_CLASS_PARAMETER.INSTANCE));
        return arrayList;
    }

    /* JADX INFO: compiled from: MemoizedMultiFieldValueClassReplacements.kt */
    @Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b6\u0018\u00002\u00020\u0001:\u0002\b\tB\u0007\b\u0004¢\u0006\u0002\u0010\u0002R\u0018\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0006\u0010\u0007\u0082\u0001\u0002\n\u000b¨\u0006\f"}, d2 = {"Lorg/jetbrains/kotlin/backend/jvm/MemoizedMultiFieldValueClassReplacements$RemappedParameter;", "", "()V", "valueParameters", "", "Lorg/jetbrains/kotlin/ir/declarations/IrValueParameter;", "getValueParameters", "()Ljava/util/List;", "MultiFieldValueClassMapping", "RegularMapping", "Lorg/jetbrains/kotlin/backend/jvm/MemoizedMultiFieldValueClassReplacements$RemappedParameter$MultiFieldValueClassMapping;", "Lorg/jetbrains/kotlin/backend/jvm/MemoizedMultiFieldValueClassReplacements$RemappedParameter$RegularMapping;", "backend.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static abstract class RemappedParameter {
        public /* synthetic */ RemappedParameter(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public abstract List<IrValueParameter> getValueParameters();

        private RemappedParameter() {
        }

        /* JADX INFO: compiled from: MemoizedMultiFieldValueClassReplacements.kt */
        @Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010 \n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\t\u0010\u000b\u001a\u00020\u0003HÆ\u0003J\u0013\u0010\f\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u0003HÆ\u0001J\u0013\u0010\r\u001a\u00020\u000e2\b\u0010\u000f\u001a\u0004\u0018\u00010\u0010HÖ\u0003J\t\u0010\u0011\u001a\u00020\u0012HÖ\u0001J\b\u0010\u0013\u001a\u00020\u0014H\u0016R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006R\u001a\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00030\bX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006\u0015"}, d2 = {"Lorg/jetbrains/kotlin/backend/jvm/MemoizedMultiFieldValueClassReplacements$RemappedParameter$RegularMapping;", "Lorg/jetbrains/kotlin/backend/jvm/MemoizedMultiFieldValueClassReplacements$RemappedParameter;", "valueParameter", "Lorg/jetbrains/kotlin/ir/declarations/IrValueParameter;", "(Lorg/jetbrains/kotlin/ir/declarations/IrValueParameter;)V", "getValueParameter", "()Lorg/jetbrains/kotlin/ir/declarations/IrValueParameter;", "valueParameters", "", "getValueParameters", "()Ljava/util/List;", "component1", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "", "hashCode", "", "toString", "", "backend.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
        public static final /* data */ class RegularMapping extends RemappedParameter {
            private final IrValueParameter valueParameter;
            private final List<IrValueParameter> valueParameters;

            public static /* synthetic */ RegularMapping copy$default(RegularMapping regularMapping, IrValueParameter irValueParameter, int i, Object obj) {
                if ((i & 1) != 0) {
                    irValueParameter = regularMapping.valueParameter;
                }
                return regularMapping.copy(irValueParameter);
            }

            /* JADX INFO: renamed from: component1, reason: from getter */
            public final IrValueParameter getValueParameter() {
                return this.valueParameter;
            }

            public final RegularMapping copy(IrValueParameter valueParameter) {
                Intrinsics.checkNotNullParameter(valueParameter, "valueParameter");
                return new RegularMapping(valueParameter);
            }

            public boolean equals(Object other) {
                if (this == other) {
                    return true;
                }
                return (other instanceof RegularMapping) && Intrinsics.areEqual(this.valueParameter, ((RegularMapping) other).valueParameter);
            }

            public int hashCode() {
                return this.valueParameter.hashCode();
            }

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public RegularMapping(IrValueParameter valueParameter) {
                super(null);
                Intrinsics.checkNotNullParameter(valueParameter, "valueParameter");
                this.valueParameter = valueParameter;
                this.valueParameters = CollectionsKt.listOf(valueParameter);
            }

            public final IrValueParameter getValueParameter() {
                return this.valueParameter;
            }

            @Override // org.jetbrains.kotlin.backend.jvm.MemoizedMultiFieldValueClassReplacements.RemappedParameter
            public List<IrValueParameter> getValueParameters() {
                return this.valueParameters;
            }

            public String toString() {
                return "RegularMapping(valueParameter=" + RenderIrElementKt.render$default(this.valueParameter, (DumpIrTreeOptions) null, 1, (Object) null) + ')';
            }
        }

        /* JADX INFO: compiled from: MemoizedMultiFieldValueClassReplacements.kt */
        @Metadata(d1 = {"\u0000N\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u000f\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B%\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u0007¢\u0006\u0002\u0010\tB3\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0016\u0010\n\u001a\u0012\u0012\u0004\u0012\u00020\f\u0012\u0004\u0012\u00020\r0\u000bj\u0002`\u000e\u0012\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u0007¢\u0006\u0002\u0010\u000fJ\t\u0010\u0019\u001a\u00020\u0003HÆ\u0003J\u0019\u0010\u001a\u001a\u0012\u0012\u0004\u0012\u00020\f\u0012\u0004\u0012\u00020\r0\u000bj\u0002`\u000eHÆ\u0003J\u000f\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\b0\u0007HÆ\u0003J=\u0010\u001c\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\u0018\b\u0002\u0010\n\u001a\u0012\u0012\u0004\u0012\u00020\f\u0012\u0004\u0012\u00020\r0\u000bj\u0002`\u000e2\u000e\b\u0002\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u0007HÆ\u0001J\u0013\u0010\u001d\u001a\u00020\u001e2\b\u0010\u001f\u001a\u0004\u0018\u00010 HÖ\u0003J\t\u0010!\u001a\u00020\"HÖ\u0001J\b\u0010#\u001a\u00020$H\u0016R\u0011\u0010\u0010\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0014R!\u0010\n\u001a\u0012\u0012\u0004\u0012\u00020\f\u0012\u0004\u0012\u00020\r0\u000bj\u0002`\u000e¢\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0016R\u001a\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u0007X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0018¨\u0006%"}, d2 = {"Lorg/jetbrains/kotlin/backend/jvm/MemoizedMultiFieldValueClassReplacements$RemappedParameter$MultiFieldValueClassMapping;", "Lorg/jetbrains/kotlin/backend/jvm/MemoizedMultiFieldValueClassReplacements$RemappedParameter;", "rootMfvcNode", "Lorg/jetbrains/kotlin/backend/jvm/RootMfvcNode;", ModuleXmlParser.TYPE, "Lorg/jetbrains/kotlin/ir/types/IrSimpleType;", "valueParameters", "", "Lorg/jetbrains/kotlin/ir/declarations/IrValueParameter;", "(Lorg/jetbrains/kotlin/backend/jvm/RootMfvcNode;Lorg/jetbrains/kotlin/ir/types/IrSimpleType;Ljava/util/List;)V", "typeArguments", "", "Lorg/jetbrains/kotlin/ir/symbols/IrTypeParameterSymbol;", "Lorg/jetbrains/kotlin/ir/types/IrType;", "Lorg/jetbrains/kotlin/backend/jvm/TypeArguments;", "(Lorg/jetbrains/kotlin/backend/jvm/RootMfvcNode;Ljava/util/Map;Ljava/util/List;)V", "boxedType", "getBoxedType", "()Lorg/jetbrains/kotlin/ir/types/IrSimpleType;", "getRootMfvcNode", "()Lorg/jetbrains/kotlin/backend/jvm/RootMfvcNode;", "getTypeArguments", "()Ljava/util/Map;", "getValueParameters", "()Ljava/util/List;", "component1", "component2", "component3", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "", "hashCode", "", "toString", "", "backend.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
        public static final /* data */ class MultiFieldValueClassMapping extends RemappedParameter {
            private final IrSimpleType boxedType;
            private final RootMfvcNode rootMfvcNode;
            private final Map<IrTypeParameterSymbol, IrType> typeArguments;
            private final List<IrValueParameter> valueParameters;

            /* JADX WARN: Multi-variable type inference failed */
            public static /* synthetic */ MultiFieldValueClassMapping copy$default(MultiFieldValueClassMapping multiFieldValueClassMapping, RootMfvcNode rootMfvcNode, Map map, List list, int i, Object obj) {
                if ((i & 1) != 0) {
                    rootMfvcNode = multiFieldValueClassMapping.rootMfvcNode;
                }
                if ((i & 2) != 0) {
                    map = multiFieldValueClassMapping.typeArguments;
                }
                if ((i & 4) != 0) {
                    list = multiFieldValueClassMapping.valueParameters;
                }
                return multiFieldValueClassMapping.copy(rootMfvcNode, map, list);
            }

            /* JADX INFO: renamed from: component1, reason: from getter */
            public final RootMfvcNode getRootMfvcNode() {
                return this.rootMfvcNode;
            }

            public final Map<IrTypeParameterSymbol, IrType> component2() {
                return this.typeArguments;
            }

            public final List<IrValueParameter> component3() {
                return this.valueParameters;
            }

            public final MultiFieldValueClassMapping copy(RootMfvcNode rootMfvcNode, Map<IrTypeParameterSymbol, ? extends IrType> typeArguments, List<? extends IrValueParameter> valueParameters) {
                Intrinsics.checkNotNullParameter(rootMfvcNode, "rootMfvcNode");
                Intrinsics.checkNotNullParameter(typeArguments, "typeArguments");
                Intrinsics.checkNotNullParameter(valueParameters, "valueParameters");
                return new MultiFieldValueClassMapping(rootMfvcNode, typeArguments, valueParameters);
            }

            public boolean equals(Object other) {
                if (this == other) {
                    return true;
                }
                if (!(other instanceof MultiFieldValueClassMapping)) {
                    return false;
                }
                MultiFieldValueClassMapping multiFieldValueClassMapping = (MultiFieldValueClassMapping) other;
                return Intrinsics.areEqual(this.rootMfvcNode, multiFieldValueClassMapping.rootMfvcNode) && Intrinsics.areEqual(this.typeArguments, multiFieldValueClassMapping.typeArguments) && Intrinsics.areEqual(this.valueParameters, multiFieldValueClassMapping.valueParameters);
            }

            public int hashCode() {
                return (((this.rootMfvcNode.hashCode() * 31) + this.typeArguments.hashCode()) * 31) + this.valueParameters.hashCode();
            }

            public final RootMfvcNode getRootMfvcNode() {
                return this.rootMfvcNode;
            }

            public final Map<IrTypeParameterSymbol, IrType> getTypeArguments() {
                return this.typeArguments;
            }

            @Override // org.jetbrains.kotlin.backend.jvm.MemoizedMultiFieldValueClassReplacements.RemappedParameter
            public List<IrValueParameter> getValueParameters() {
                return this.valueParameters;
            }

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            /* JADX WARN: Multi-variable type inference failed */
            public MultiFieldValueClassMapping(RootMfvcNode rootMfvcNode, Map<IrTypeParameterSymbol, ? extends IrType> typeArguments, List<? extends IrValueParameter> valueParameters) {
                super(null);
                Intrinsics.checkNotNullParameter(rootMfvcNode, "rootMfvcNode");
                Intrinsics.checkNotNullParameter(typeArguments, "typeArguments");
                Intrinsics.checkNotNullParameter(valueParameters, "valueParameters");
                this.rootMfvcNode = rootMfvcNode;
                this.typeArguments = typeArguments;
                this.valueParameters = valueParameters;
                if (getValueParameters().size() <= 1) {
                    throw new IllegalArgumentException("MFVC must have > 1 fields".toString());
                }
                IrType irTypeSubstitute = IrTypeUtilsKt.substitute(rootMfvcNode.getType(), typeArguments);
                Intrinsics.checkNotNull(irTypeSubstitute, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.types.IrSimpleType");
                this.boxedType = (IrSimpleType) irTypeSubstitute;
            }

            /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
            public MultiFieldValueClassMapping(RootMfvcNode rootMfvcNode, IrSimpleType type, List<? extends IrValueParameter> valueParameters) {
                this(rootMfvcNode, MfvcNodeKt.makeTypeArgumentsFromType(type), valueParameters);
                Intrinsics.checkNotNullParameter(rootMfvcNode, "rootMfvcNode");
                Intrinsics.checkNotNullParameter(type, "type");
                Intrinsics.checkNotNullParameter(valueParameters, "valueParameters");
            }

            public final IrSimpleType getBoxedType() {
                return this.boxedType;
            }

            public String toString() {
                return StringsKt.trimMargin$default("MultiFieldValueClassMapping(\n                    |    rootMfvcNode=" + this.rootMfvcNode + ",\n                    |    typeArguments=[" + CollectionsKt.joinToString$default(this.typeArguments.values(), ",", null, null, 0, null, new Function1<IrType, CharSequence>() { // from class: org.jetbrains.kotlin.backend.jvm.MemoizedMultiFieldValueClassReplacements$RemappedParameter$MultiFieldValueClassMapping$toString$1
                    @Override // kotlin.jvm.functions.Function1
                    public final CharSequence invoke(IrType it) {
                        Intrinsics.checkNotNullParameter(it, "it");
                        return "\n        " + RenderIrElementKt.render$default(it, (DumpIrTreeOptions) null, 1, (Object) null);
                    }
                }, 30, null) + "],\n                    |    valueParameters=[" + CollectionsKt.joinToString$default(getValueParameters(), ",", null, null, 0, null, new Function1<IrValueParameter, CharSequence>() { // from class: org.jetbrains.kotlin.backend.jvm.MemoizedMultiFieldValueClassReplacements$RemappedParameter$MultiFieldValueClassMapping$toString$2
                    @Override // kotlin.jvm.functions.Function1
                    public final CharSequence invoke(IrValueParameter it) {
                        Intrinsics.checkNotNullParameter(it, "it");
                        return "\n        " + RenderIrElementKt.render$default(it, (DumpIrTreeOptions) null, 1, (Object) null);
                    }
                }, 30, null) + "],\n                    |    boxedType=" + RenderIrElementKt.render$default(this.boxedType, (DumpIrTreeOptions) null, 1, (Object) null) + "\n                    |)", null, 1, null);
            }
        }
    }

    public final Map<IrFunction, List<RemappedParameter>> getBindingOldFunctionToParameterTemplateStructure() {
        return this.bindingOldFunctionToParameterTemplateStructure;
    }

    public final Map<IrFunction, List<RemappedParameter>> getBindingNewFunctionToParameterTemplateStructure() {
        return this.bindingNewFunctionToParameterTemplateStructure;
    }

    @Override // org.jetbrains.kotlin.backend.jvm.MemoizedValueClassAbstractReplacements
    protected IrSimpleFunction createStaticReplacement(final IrFunction function) {
        Intrinsics.checkNotNullParameter(function, "function");
        return buildReplacement(function, JvmLoweredDeclarationOrigin.STATIC_MULTI_FIELD_VALUE_CLASS_REPLACEMENT.INSTANCE, true, new Function1<IrFunction, Unit>() { // from class: org.jetbrains.kotlin.backend.jvm.MemoizedMultiFieldValueClassReplacements.createStaticReplacement.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(IrFunction irFunction) {
                invoke2(irFunction);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(IrFunction buildReplacement) {
                Intrinsics.checkNotNullParameter(buildReplacement, "$this$buildReplacement");
                MemoizedMultiFieldValueClassReplacements.this.getOriginalFunctionForStaticReplacement().put(buildReplacement, function);
                buildReplacement.setTypeParameters(CollectionsKt.emptyList());
                IrFunction irFunction = buildReplacement;
                IrUtilsKt.copyTypeParametersFrom$default(irFunction, IrUtilsKt.getParentAsClass(function), null, null, 6, null);
                List<IrTypeParameter> typeParameters = IrUtilsKt.getParentAsClass(function).getTypeParameters();
                ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(typeParameters, 10));
                Iterator<T> it = typeParameters.iterator();
                while (it.hasNext()) {
                    arrayList.add(((IrTypeParameter) it.next()).getSymbol());
                }
                ArrayList arrayList2 = arrayList;
                List<IrTypeParameter> typeParameters2 = buildReplacement.getTypeParameters();
                ArrayList arrayList3 = new ArrayList(CollectionsKt.collectionSizeOrDefault(typeParameters2, 10));
                Iterator<T> it2 = typeParameters2.iterator();
                while (it2.hasNext()) {
                    arrayList3.add(IrTypesKt.getDefaultType((IrTypeParameter) it2.next()));
                }
                Map map = MapsKt.toMap(CollectionsKt.zip(arrayList2, arrayList3));
                IrFunction irFunction2 = function;
                IrUtilsKt.copyTypeParametersFrom$default(irFunction, irFunction2, null, MapsKt.toMap(CollectionsKt.zip(IrUtilsKt.getParentAsClass(irFunction2).getTypeParameters(), buildReplacement.getTypeParameters())), 2, null);
                List<RemappedParameter> listMakeAndAddGroupedValueParametersFrom = MemoizedMultiFieldValueClassReplacements.this.makeAndAddGroupedValueParametersFrom(function, true, map, buildReplacement);
                MemoizedMultiFieldValueClassReplacements.this.getBindingOldFunctionToParameterTemplateStructure().put(function, listMakeAndAddGroupedValueParametersFrom);
                MemoizedMultiFieldValueClassReplacements.this.getBindingNewFunctionToParameterTemplateStructure().put(buildReplacement, listMakeAndAddGroupedValueParametersFrom);
            }
        });
    }

    @Override // org.jetbrains.kotlin.backend.jvm.MemoizedValueClassAbstractReplacements
    protected IrSimpleFunction createMethodReplacement(final IrFunction function) {
        Intrinsics.checkNotNullParameter(function, "function");
        return buildReplacement$default(this, function, function.getOrigin(), false, new Function1<IrFunction, Unit>() { // from class: org.jetbrains.kotlin.backend.jvm.MemoizedMultiFieldValueClassReplacements.createMethodReplacement.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(IrFunction irFunction) {
                invoke2(irFunction);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(IrFunction buildReplacement) {
                Intrinsics.checkNotNullParameter(buildReplacement, "$this$buildReplacement");
                MemoizedMultiFieldValueClassReplacements.this.getOriginalFunctionForMethodReplacement().put(buildReplacement, function);
                List<RemappedParameter> listMakeMethodLikeRemappedParameters = MemoizedMultiFieldValueClassReplacements.this.makeMethodLikeRemappedParameters(buildReplacement, function);
                MemoizedMultiFieldValueClassReplacements.this.getBindingOldFunctionToParameterTemplateStructure().put(function, listMakeMethodLikeRemappedParameters);
                MemoizedMultiFieldValueClassReplacements.this.getBindingNewFunctionToParameterTemplateStructure().put(buildReplacement, listMakeMethodLikeRemappedParameters);
            }
        }, 4, null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final IrConstructor createConstructorReplacement(IrConstructor constructor) {
        IrFactory irFactory = getIrFactory();
        IrFunctionBuilder irFunctionBuilder = new IrFunctionBuilder();
        IrConstructor irConstructor = constructor;
        irFunctionBuilder.updateFrom((IrFunction) irConstructor);
        irFunctionBuilder.setReturnType(constructor.getReturnType());
        IrConstructor irConstructorBuildConstructor = DeclarationBuildersKt.buildConstructor(irFactory, irFunctionBuilder);
        irConstructorBuildConstructor.setParent(constructor.getParent());
        List<RemappedParameter> listMakeMethodLikeRemappedParameters = makeMethodLikeRemappedParameters(irConstructorBuildConstructor, irConstructor);
        this.bindingOldFunctionToParameterTemplateStructure.put(constructor, listMakeMethodLikeRemappedParameters);
        IrUtilsKt.copyTypeParametersFrom$default(irConstructorBuildConstructor, constructor, null, null, 6, null);
        irConstructorBuildConstructor.setAnnotations(constructor.getAnnotations());
        this.originalConstructorForConstructorReplacement.put(irConstructorBuildConstructor, constructor);
        this.bindingNewFunctionToParameterTemplateStructure.put(irConstructorBuildConstructor, listMakeMethodLikeRemappedParameters);
        if (constructor.getMetadata() != null) {
            irConstructorBuildConstructor.setMetadata(constructor.getMetadata());
            constructor.setMetadata(null);
        }
        return irConstructorBuildConstructor;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final List<RemappedParameter> makeMethodLikeRemappedParameters(IrFunction irFunction, IrFunction irFunction2) {
        IrValueParameter irValueParameterCopyTo$default;
        IrFunction irFunction3;
        IrValueParameter dispatchReceiverParameter = irFunction2.getDispatchReceiverParameter();
        if (dispatchReceiverParameter != null) {
            irFunction3 = irFunction;
            irValueParameterCopyTo$default = IrUtilsKt.copyTo$default(dispatchReceiverParameter, irFunction3, null, -1, 0, 0, null, null, null, null, null, false, false, false, 8186, null);
        } else {
            irValueParameterCopyTo$default = null;
            irFunction3 = irFunction;
        }
        irFunction3.setDispatchReceiverParameter(irValueParameterCopyTo$default);
        List<RemappedParameter> listMakeAndAddGroupedValueParametersFrom = makeAndAddGroupedValueParametersFrom(irFunction2, false, MapsKt.emptyMap(), irFunction3);
        IrValueParameter dispatchReceiverParameter2 = irFunction.getDispatchReceiverParameter();
        return dispatchReceiverParameter2 != null ? CollectionsKt.plus((Collection) CollectionsKt.listOf(new RemappedParameter.RegularMapping(dispatchReceiverParameter2)), (Iterable) listMakeAndAddGroupedValueParametersFrom) : listMakeAndAddGroupedValueParametersFrom;
    }

    @Override // org.jetbrains.kotlin.backend.jvm.MemoizedValueClassAbstractReplacements
    protected Function1<IrFunction, IrSimpleFunction> getGetReplacementFunctionImpl() {
        return this.getReplacementFunctionImpl;
    }

    @Override // org.jetbrains.kotlin.backend.jvm.MemoizedValueClassAbstractReplacements
    public IrConstructor getReplacementForRegularClassConstructor(IrConstructor constructor) {
        Intrinsics.checkNotNullParameter(constructor, "constructor");
        if (IrDeclarationsKt.isMultiFieldValueClass(AdditionalIrUtilsKt.getConstructedClass(constructor))) {
            return null;
        }
        List<IrValueParameter> valueParameters = constructor.getValueParameters();
        if ((valueParameters instanceof Collection) && valueParameters.isEmpty()) {
            return null;
        }
        Iterator<T> it = valueParameters.iterator();
        while (it.hasNext()) {
            if (MfvcNodeKt.needsMfvcFlattening(((IrValueParameter) it.next()).getType())) {
                return this.getReplacementForRegularClassConstructorImpl.invoke(constructor);
            }
        }
        return null;
    }

    public final Function1<IrClass, RootMfvcNode> getGetRootMfvcNode() {
        return this.getRootMfvcNode;
    }

    public final RootMfvcNode getRootMfvcNodeOrNull(IrClass irClass) {
        Intrinsics.checkNotNullParameter(irClass, "irClass");
        if (MfvcNodeKt.needsMfvcFlattening(IrUtilsKt.getDefaultType(irClass))) {
            return this.getRootMfvcNode.invoke(irClass);
        }
        return null;
    }

    private final IrField withAddedStaticReplacementIfNeeded(IrField irField) {
        IrProperty owner;
        IrField staticBackingField;
        IrPropertySymbol correspondingPropertySymbol = irField.getCorrespondingPropertySymbol();
        if (correspondingPropertySymbol == null || (owner = correspondingPropertySymbol.getOwner()) == null || (staticBackingField = getContext().getCachedDeclarations().getStaticBackingField(owner)) == null) {
            return irField;
        }
        owner.setBackingField(staticBackingField);
        return staticBackingField;
    }

    private final IrProperty withAddedStaticReplacementIfNeeded(IrProperty irProperty) {
        IrField staticBackingField = getContext().getCachedDeclarations().getStaticBackingField(irProperty);
        if (staticBackingField == null) {
            return irProperty;
        }
        irProperty.setBackingField(staticBackingField);
        return irProperty;
    }

    public final Set<IrField> getFieldsToRemove(IrClass clazz) {
        Intrinsics.checkNotNullParameter(clazz, "clazz");
        Set<IrField> set = this.fieldsToRemove.get(clazz);
        return set == null ? SetsKt.emptySet() : set;
    }

    public final void addFieldToRemove(IrClass clazz, IrField field) {
        Set<IrField> setPutIfAbsent;
        Intrinsics.checkNotNullParameter(clazz, "clazz");
        Intrinsics.checkNotNullParameter(field, "field");
        ConcurrentHashMap<IrClass, Set<IrField>> concurrentHashMap = this.fieldsToRemove;
        ConcurrentHashMap.KeySetView keySetViewKeySet = concurrentHashMap.get(clazz);
        if (keySetViewKeySet == null && (setPutIfAbsent = concurrentHashMap.putIfAbsent(clazz, (keySetViewKeySet = new ConcurrentHashMap().keySet(Unit.INSTANCE)))) != null) {
            keySetViewKeySet = setPutIfAbsent;
        }
        keySetViewKeySet.add(withAddedStaticReplacementIfNeeded(field));
    }

    public final NameableMfvcNode getMfvcFieldNode(IrField field) {
        Intrinsics.checkNotNullParameter(field, "field");
        IrField irFieldWithAddedStaticReplacementIfNeeded = withAddedStaticReplacementIfNeeded(field);
        IrDeclarationParent parent = irFieldWithAddedStaticReplacementIfNeeded.getParent();
        IrPropertySymbol correspondingPropertySymbol = irFieldWithAddedStaticReplacementIfNeeded.getCorrespondingPropertySymbol();
        IrProperty owner = correspondingPropertySymbol != null ? correspondingPropertySymbol.getOwner() : null;
        if (owner != null && !owner.getIsDelegated()) {
            return getMfvcPropertyNode(owner);
        }
        if ((parent instanceof IrClass) && MfvcNodeKt.needsMfvcFlattening(irFieldWithAddedStaticReplacementIfNeeded.getType())) {
            return this.getMfvcStandaloneFieldNodeImpl.invoke(irFieldWithAddedStaticReplacementIfNeeded);
        }
        return null;
    }

    /* JADX WARN: Code duplicated, block: B:10:0x001d  */
    private final IntermediateMfvcNode getRegularClassMfvcPropertyNode(IrProperty property) {
        IrType type;
        IrDeclarationParent parent = property.getParent();
        IrType[] irTypeArr = new IrType[2];
        IrField backingFieldIfNotToRemove = getBackingFieldIfNotToRemove(property);
        if (backingFieldIfNotToRemove == null) {
            type = null;
        } else {
            if (property.getIsDelegated()) {
                backingFieldIfNotToRemove = null;
            }
            if (backingFieldIfNotToRemove != null) {
                type = backingFieldIfNotToRemove.getType();
            } else {
                type = null;
            }
        }
        irTypeArr[0] = type;
        IrSimpleFunction getter = property.getGetter();
        irTypeArr[1] = getter != null ? getter.getReturnType() : null;
        List listListOfNotNull = CollectionsKt.listOfNotNull((Object[]) irTypeArr);
        if (listListOfNotNull.isEmpty()) {
            return null;
        }
        List list = listListOfNotNull;
        if (!(list instanceof Collection) || !list.isEmpty()) {
            Iterator it = list.iterator();
            while (it.hasNext()) {
                if (!MfvcNodeKt.needsMfvcFlattening((IrType) it.next())) {
                    return null;
                }
            }
        }
        if (!(parent instanceof IrClass) || property.getIsFakeOverride()) {
            return null;
        }
        IrSimpleFunction getter2 = property.getGetter();
        if ((getter2 == null || (getter2.getContextReceiverParametersCount() <= 0 && getter2.getExtensionReceiverParameter() == null)) && !useRootNode((IrClass) parent, property)) {
            return this.getRegularClassMfvcPropertyNodeImpl.invoke(property);
        }
        return null;
    }

    public final NameableMfvcNode getMfvcPropertyNode(IrProperty property) {
        Intrinsics.checkNotNullParameter(property, "property");
        IrDeclarationParent parent = property.getParent();
        IrProperty irPropertyWithAddedStaticReplacementIfNeeded = withAddedStaticReplacementIfNeeded(property);
        if (parent instanceof IrClass) {
            return useRootNode((IrClass) parent, irPropertyWithAddedStaticReplacementIfNeeded) ? this.getRootMfvcNode.invoke((IrClass) parent).get(irPropertyWithAddedStaticReplacementIfNeeded.getName()) : getRegularClassMfvcPropertyNode(irPropertyWithAddedStaticReplacementIfNeeded);
        }
        return null;
    }

    /* JADX WARN: Code duplicated, block: B:20:0x003d A[ORIG_RETURN, RETURN] */
    /* JADX WARN: Code duplicated, block: B:23:? A[RETURN, SYNTHETIC] */
    private final boolean useRootNode(IrClass parent, IrProperty property) {
        Boolean boolValueOf;
        boolean isStatic;
        IrSimpleFunction getter = property.getGetter();
        if ((getter != null && (getter.getContextReceiverParametersCount() > 0 || getter.getExtensionReceiverParameter() != null)) || !IrDeclarationsKt.isMultiFieldValueClass(parent)) {
            return false;
        }
        if (getter == null) {
            IrField backingFieldIfNotToRemove = getBackingFieldIfNotToRemove(property);
            if (backingFieldIfNotToRemove != null) {
                isStatic = backingFieldIfNotToRemove.getIsStatic();
            } else {
                boolValueOf = null;
            }
            if (Intrinsics.areEqual((Object) boolValueOf, (Object) false)) {
                return true;
            }
            return false;
        }
        isStatic = IrUtilsKt.isStatic(getter);
        boolValueOf = Boolean.valueOf(isStatic);
        if (Intrinsics.areEqual((Object) boolValueOf, (Object) false)) {
            return true;
        }
        return false;
    }

    private final IrField getBackingFieldIfNotToRemove(IrProperty irProperty) {
        IrField backingField = irProperty.getBackingField();
        if (backingField == null || getFieldsToRemove(IrUtilsKt.getParentAsClass(irProperty)).contains(backingField)) {
            return null;
        }
        return backingField;
    }

    /* JADX INFO: compiled from: MemoizedMultiFieldValueClassReplacements.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÂ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/backend/jvm/MemoizedMultiFieldValueClassReplacements$FLATTENED_NOTHING_DEFAULT_VALUE;", "Lorg/jetbrains/kotlin/ir/expressions/IrStatementOriginImpl;", "()V", "backend.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
    private static final class FLATTENED_NOTHING_DEFAULT_VALUE extends IrStatementOriginImpl {
        public static final FLATTENED_NOTHING_DEFAULT_VALUE INSTANCE = new FLATTENED_NOTHING_DEFAULT_VALUE();

        private FLATTENED_NOTHING_DEFAULT_VALUE() {
            super("FLATTENED_NOTHING_DEFAULT_VALUE");
        }
    }

    public final Map<IrValueParameter, IrExpression> mapFunctionMfvcStructures(final IrBlockBuilder irBuilder, IrFunction targetFunction, IrFunction sourceFunction, Function2<? super IrValueParameter, ? super IrType, ? extends IrExpression> getArgument) {
        IrExpression irExpressionMakeBoxedExpression;
        IrDeclarationParent next;
        Intrinsics.checkNotNullParameter(irBuilder, "irBuilder");
        Intrinsics.checkNotNullParameter(targetFunction, "targetFunction");
        Intrinsics.checkNotNullParameter(sourceFunction, "sourceFunction");
        Intrinsics.checkNotNullParameter(getArgument, "getArgument");
        ArrayList arrayList = this.bindingNewFunctionToParameterTemplateStructure.get(targetFunction);
        if (arrayList == null) {
            List<IrValueParameter> explicitParameters = IrUtilsKt.getExplicitParameters(targetFunction);
            ArrayList arrayList2 = new ArrayList(CollectionsKt.collectionSizeOrDefault(explicitParameters, 10));
            Iterator<T> it = explicitParameters.iterator();
            while (it.hasNext()) {
                arrayList2.add(new RemappedParameter.RegularMapping((IrValueParameter) it.next()));
            }
            arrayList = arrayList2;
        }
        ArrayList arrayList3 = this.bindingNewFunctionToParameterTemplateStructure.get(sourceFunction);
        if (arrayList3 == null) {
            List<IrValueParameter> explicitParameters2 = IrUtilsKt.getExplicitParameters(sourceFunction);
            ArrayList arrayList4 = new ArrayList(CollectionsKt.collectionSizeOrDefault(explicitParameters2, 10));
            Iterator<T> it2 = explicitParameters2.iterator();
            while (it2.hasNext()) {
                arrayList4.add(new RemappedParameter.RegularMapping((IrValueParameter) it2.next()));
            }
            arrayList3 = arrayList4;
        }
        verifyStructureCompatibility(arrayList, arrayList3);
        Map mapCreateMapBuilder = MapsKt.createMapBuilder();
        for (Pair pair : CollectionsKt.zip(arrayList, arrayList3)) {
            RemappedParameter remappedParameter = (RemappedParameter) pair.component1();
            RemappedParameter remappedParameter2 = (RemappedParameter) pair.component2();
            int i = 0;
            if (remappedParameter instanceof RemappedParameter.RegularMapping) {
                if (remappedParameter2 instanceof RemappedParameter.RegularMapping) {
                    RemappedParameter.RegularMapping regularMapping = (RemappedParameter.RegularMapping) remappedParameter;
                    mapCreateMapBuilder.put(regularMapping.getValueParameter(), getArgument.invoke(((RemappedParameter.RegularMapping) remappedParameter2).getValueParameter(), regularMapping.getValueParameter().getType()));
                } else if (remappedParameter2 instanceof RemappedParameter.MultiFieldValueClassMapping) {
                    List<IrValueParameter> valueParameters = remappedParameter2.getValueParameters();
                    ArrayList arrayList5 = new ArrayList(CollectionsKt.collectionSizeOrDefault(valueParameters, 10));
                    for (IrValueParameter irValueParameter : valueParameters) {
                        IrExpression irExpressionInvoke = getArgument.invoke(irValueParameter, irValueParameter.getType());
                        if (irExpressionInvoke == null) {
                            throw new IllegalStateException(("Expected an argument for " + remappedParameter2).toString());
                        }
                        arrayList5.add(irExpressionInvoke);
                    }
                    ArrayList arrayList6 = arrayList5;
                    ArrayList arrayList7 = arrayList6;
                    if ((arrayList7 instanceof Collection) && arrayList7.isEmpty()) {
                        Object obj = arrayList6.get(0);
                        Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.expressions.IrComposite");
                        IrStatement irStatement = ((IrComposite) obj).getStatements().get(0);
                        Intrinsics.checkNotNull(irStatement, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.expressions.IrExpression");
                        irExpressionMakeBoxedExpression = (IrExpression) irStatement;
                    } else {
                        Iterator it3 = arrayList7.iterator();
                        while (true) {
                            if (it3.hasNext()) {
                                IrExpression irExpression = (IrExpression) it3.next();
                                if (!(irExpression instanceof IrComposite) || !Intrinsics.areEqual(((IrComposite) irExpression).getOrigin(), FLATTENED_NOTHING_DEFAULT_VALUE.INSTANCE)) {
                                    RemappedParameter.MultiFieldValueClassMapping multiFieldValueClassMapping = (RemappedParameter.MultiFieldValueClassMapping) remappedParameter2;
                                    irExpressionMakeBoxedExpression = MfvcNodeKt.makeBoxedExpression(multiFieldValueClassMapping.getRootMfvcNode(), irBuilder, multiFieldValueClassMapping.getTypeArguments(), arrayList6, new Function0<Unit>() { // from class: org.jetbrains.kotlin.backend.jvm.MemoizedMultiFieldValueClassReplacements$mapFunctionMfvcStructures$1$newArgument$2
                                        /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                                        public final void invoke2() {
                                        }

                                        @Override // kotlin.jvm.functions.Function0
                                        public /* bridge */ /* synthetic */ Unit invoke() {
                                            invoke2();
                                            return Unit.INSTANCE;
                                        }
                                    });
                                }
                            } else {
                                Object obj2 = arrayList6.get(0);
                                Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.expressions.IrComposite");
                                IrStatement irStatement2 = ((IrComposite) obj2).getStatements().get(0);
                                Intrinsics.checkNotNull(irStatement2, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.expressions.IrExpression");
                                irExpressionMakeBoxedExpression = (IrExpression) irStatement2;
                            }
                        }
                    }
                    mapCreateMapBuilder.put(((RemappedParameter.RegularMapping) remappedParameter).getValueParameter(), irExpressionMakeBoxedExpression);
                } else {
                    continue;
                }
            } else if (!(remappedParameter instanceof RemappedParameter.MultiFieldValueClassMapping)) {
                continue;
            } else if (remappedParameter2 instanceof RemappedParameter.RegularMapping) {
                RemappedParameter.RegularMapping regularMapping2 = (RemappedParameter.RegularMapping) remappedParameter2;
                RemappedParameter.MultiFieldValueClassMapping multiFieldValueClassMapping2 = (RemappedParameter.MultiFieldValueClassMapping) remappedParameter;
                IrExpression irExpressionInvoke2 = getArgument.invoke(regularMapping2.getValueParameter(), multiFieldValueClassMapping2.getBoxedType());
                if (irExpressionInvoke2 == null) {
                    throw new IllegalStateException(("Expected an argument for " + remappedParameter2).toString());
                }
                if (IrTypePredicatesKt.isNothing(regularMapping2.getValueParameter().getType())) {
                    for (IrValueParameter irValueParameter2 : remappedParameter.getValueParameters()) {
                        int i2 = i + 1;
                        IrBlockBuilder irBlockBuilder = irBuilder;
                        IrBlockBuilder irBlockBuilder2 = new IrBlockBuilder(irBlockBuilder.getContext(), irBlockBuilder.getScope(), irBlockBuilder.getStartOffset(), irBlockBuilder.getEndOffset(), FLATTENED_NOTHING_DEFAULT_VALUE.INSTANCE, null, true);
                        if (i == 0) {
                            irBlockBuilder2.unaryPlus(irExpressionInvoke2);
                        }
                        irBlockBuilder2.unaryPlus(JvmIrTypeUtilsKt.defaultValue(irValueParameter2.getType(), -1, -1, getContext()));
                        Unit unit = Unit.INSTANCE;
                        mapCreateMapBuilder.put(irValueParameter2, irBlockBuilder2.getIrBlockBody());
                        i = i2;
                    }
                } else {
                    ReceiverBasedMfvcNodeInstance receiverBasedMfvcNodeInstanceCreateInstanceFromBox = MfvcNodeKt.createInstanceFromBox(multiFieldValueClassMapping2.getRootMfvcNode(), irBuilder, irExpressionInvoke2, AccessType.ChooseEffective, new Function1<IrVariable, Unit>() { // from class: org.jetbrains.kotlin.backend.jvm.MemoizedMultiFieldValueClassReplacements$mapFunctionMfvcStructures$1$1$instance$1
                        {
                            super(1);
                        }

                        @Override // kotlin.jvm.functions.Function1
                        public /* bridge */ /* synthetic */ Unit invoke(IrVariable irVariable) {
                            invoke2(irVariable);
                            return Unit.INSTANCE;
                        }

                        /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                        public final void invoke2(IrVariable it4) {
                            Intrinsics.checkNotNullParameter(it4, "it");
                            irBuilder.unaryPlus(it4);
                        }
                    });
                    Iterator<IrDeclarationParent> it4 = LocalDeclarationsLoweringKt.getParents(sourceFunction).iterator();
                    do {
                        if (!it4.hasNext()) {
                            throw new NoSuchElementException("No element of given type found");
                        }
                        next = it4.next();
                    } while (!(next instanceof IrClass));
                    for (Pair pair2 : CollectionsKt.zip(remappedParameter.getValueParameters(), receiverBasedMfvcNodeInstanceCreateInstanceFromBox.makeFlattenedGetterExpressions(irBuilder, (IrClass) next, new Function0<Unit>() { // from class: org.jetbrains.kotlin.backend.jvm.MemoizedMultiFieldValueClassReplacements$mapFunctionMfvcStructures$1$1$arguments$1
                        /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                        public final void invoke2() {
                        }

                        @Override // kotlin.jvm.functions.Function0
                        public /* bridge */ /* synthetic */ Unit invoke() {
                            invoke2();
                            return Unit.INSTANCE;
                        }
                    }))) {
                        mapCreateMapBuilder.put((IrValueParameter) pair2.component1(), (IrExpression) pair2.component2());
                    }
                }
            } else if (remappedParameter2 instanceof RemappedParameter.MultiFieldValueClassMapping) {
                for (Pair pair3 : CollectionsKt.zip(remappedParameter2.getValueParameters(), remappedParameter.getValueParameters())) {
                    IrValueParameter irValueParameter3 = (IrValueParameter) pair3.component1();
                    IrValueParameter irValueParameter4 = (IrValueParameter) pair3.component2();
                    mapCreateMapBuilder.put(irValueParameter4, getArgument.invoke(irValueParameter3, irValueParameter4.getType()));
                }
            }
        }
        return MapsKt.build(mapCreateMapBuilder);
    }

    private final void verifyStructureCompatibility(List<? extends RemappedParameter> targetStructure, List<? extends RemappedParameter> sourceStructure) {
        for (Pair pair : CollectionsKt.zip(targetStructure, sourceStructure)) {
            RemappedParameter remappedParameter = (RemappedParameter) pair.component1();
            RemappedParameter remappedParameter2 = (RemappedParameter) pair.component2();
            if ((remappedParameter instanceof RemappedParameter.MultiFieldValueClassMapping) && (remappedParameter2 instanceof RemappedParameter.MultiFieldValueClassMapping) && !Intrinsics.areEqual(AdditionalIrUtilsKt.getClassId(((RemappedParameter.MultiFieldValueClassMapping) remappedParameter).getRootMfvcNode().getMfvc()), AdditionalIrUtilsKt.getClassId(((RemappedParameter.MultiFieldValueClassMapping) remappedParameter2).getRootMfvcNode().getMfvc()))) {
                throw new IllegalArgumentException(("Incompatible parameter structures:\n" + remappedParameter + " inside " + targetStructure + '\n' + remappedParameter2 + " inside " + sourceStructure).toString());
            }
        }
        for (RemappedParameter remappedParameter3 : CollectionsKt.slice((List) targetStructure, RangesKt.until(sourceStructure.size(), targetStructure.size()))) {
            if (!(remappedParameter3 instanceof RemappedParameter.RegularMapping) || Intrinsics.areEqual(((RemappedParameter.RegularMapping) remappedParameter3).getValueParameter().getOrigin(), IrDeclarationOrigin.DEFINED.INSTANCE)) {
                throw new IllegalArgumentException(("Unexpected target MFVC parameter structure:\n" + remappedParameter3 + " inside " + targetStructure).toString());
            }
        }
        for (RemappedParameter remappedParameter4 : CollectionsKt.slice((List) sourceStructure, RangesKt.until(targetStructure.size(), sourceStructure.size()))) {
            if (!(remappedParameter4 instanceof RemappedParameter.RegularMapping) || Intrinsics.areEqual(((RemappedParameter.RegularMapping) remappedParameter4).getValueParameter().getOrigin(), IrDeclarationOrigin.DEFINED.INSTANCE)) {
                throw new IllegalArgumentException(("Unexpected source MFVC parameter structure:\n" + remappedParameter4 + " inside " + sourceStructure).toString());
            }
        }
    }
}
