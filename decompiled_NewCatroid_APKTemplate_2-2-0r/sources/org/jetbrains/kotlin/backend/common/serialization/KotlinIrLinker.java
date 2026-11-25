package org.jetbrains.kotlin.backend.common.serialization;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.Deprecated;
import kotlin.DeprecationLevel;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.ReplaceWith;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.Sequence;
import kotlin.sequences.SequencesKt;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.backend.common.linkage.issues.IrSymbolTypeMismatchException;
import org.jetbrains.kotlin.backend.common.linkage.issues.NoDeserializerForModule;
import org.jetbrains.kotlin.backend.common.linkage.issues.SignatureIdNotFoundInModuleWithDependencies;
import org.jetbrains.kotlin.backend.common.linkage.issues.SymbolTypeMismatch;
import org.jetbrains.kotlin.backend.common.linkage.issues.UserVisibleIrModulesSupport;
import org.jetbrains.kotlin.backend.common.linkage.partial.PartialLinkageSupportForLinker;
import org.jetbrains.kotlin.backend.common.overrides.FakeOverrideBuilder;
import org.jetbrains.kotlin.backend.common.overrides.FileLocalAwareLinker;
import org.jetbrains.kotlin.backend.common.serialization.encodings.BinarySymbolData;
import org.jetbrains.kotlin.descriptors.CallableMemberDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.ModuleDescriptor;
import org.jetbrains.kotlin.ir.IrBuiltIns;
import org.jetbrains.kotlin.ir.builders.TranslationPluginContext;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrConstructor;
import org.jetbrains.kotlin.ir.declarations.IrDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrEnumEntry;
import org.jetbrains.kotlin.ir.declarations.IrFile;
import org.jetbrains.kotlin.ir.declarations.IrFunction;
import org.jetbrains.kotlin.ir.declarations.IrModuleFragment;
import org.jetbrains.kotlin.ir.declarations.IrProperty;
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction;
import org.jetbrains.kotlin.ir.declarations.IrSymbolOwner;
import org.jetbrains.kotlin.ir.declarations.IrValueParameter;
import org.jetbrains.kotlin.ir.expressions.IrExpressionBody;
import org.jetbrains.kotlin.ir.linkage.IrDeserializer;
import org.jetbrains.kotlin.ir.symbols.IrBindableSymbol;
import org.jetbrains.kotlin.ir.symbols.IrClassSymbol;
import org.jetbrains.kotlin.ir.symbols.IrConstructorSymbol;
import org.jetbrains.kotlin.ir.symbols.IrDelegatingClassSymbolImpl;
import org.jetbrains.kotlin.ir.symbols.IrDelegatingConstructorSymbolImpl;
import org.jetbrains.kotlin.ir.symbols.IrDelegatingEnumEntrySymbolImpl;
import org.jetbrains.kotlin.ir.symbols.IrDelegatingPropertySymbolImpl;
import org.jetbrains.kotlin.ir.symbols.IrDelegatingSimpleFunctionSymbolImpl;
import org.jetbrains.kotlin.ir.symbols.IrDelegatingSymbol;
import org.jetbrains.kotlin.ir.symbols.IrDelegatingSymbolKt;
import org.jetbrains.kotlin.ir.symbols.IrEnumEntrySymbol;
import org.jetbrains.kotlin.ir.symbols.IrPropertySymbol;
import org.jetbrains.kotlin.ir.symbols.IrSimpleFunctionSymbol;
import org.jetbrains.kotlin.ir.symbols.IrSymbol;
import org.jetbrains.kotlin.ir.symbols.IrSymbolKt;
import org.jetbrains.kotlin.ir.util.IdSignature;
import org.jetbrains.kotlin.ir.util.IrMessageLogger;
import org.jetbrains.kotlin.ir.util.IrUtilsKt;
import org.jetbrains.kotlin.ir.util.PatchDeclarationParentsKt;
import org.jetbrains.kotlin.ir.util.SymbolTable;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.library.KotlinLibrary;
import org.jetbrains.kotlin.library.KotlinLibraryKt;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.org.jline.reader.LineReader;
import org.jetbrains.kotlin.resolve.descriptorUtil.DescriptorUtilsKt;
import org.mozilla.javascript.NativeSymbol;

/* JADX INFO: compiled from: KotlinIrLinker.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000´\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\f\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u000e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b&\u0018\u00002\u00020\u00012\u00020\u0002B\\\u0012\b\u0010\u0003\u001a\u0004\u0018\u00010\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\b\u0012\u0006\u0010\t\u001a\u00020\n\u0012\f\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\u00040\f\u0012%\b\u0002\u0010\r\u001a\u001f\u0012\u0004\u0012\u00020\u000f\u0012\u0004\u0012\u00020\u0010\u0012\u0004\u0012\u00020\u0011\u0012\u0004\u0012\u00020\u00100\u000e¢\u0006\u0002\b\u0012¢\u0006\u0002\u0010\u0013J\u0018\u0010M\u001a\u00020N2\u0006\u0010O\u001a\u00020P2\u0006\u0010Q\u001a\u00020PH\u0002J\u0006\u0010R\u001a\u00020NJ\u001e\u0010S\u001a\u00020\u001e2\u0006\u0010T\u001a\u00020U2\f\u0010V\u001a\b\u0012\u0004\u0012\u00020\u001e0/H\u0014J.\u0010W\u001a\u00020\u001e2\u0006\u0010X\u001a\u00020\u00042\b\u0010Y\u001a\u0004\u0018\u00010Z2\u0012\u0010[\u001a\u000e\u0012\u0004\u0012\u00020\u001d\u0012\u0004\u0012\u00020]0\\H$J\u0006\u0010^\u001a\u00020NJ$\u0010_\u001a\u00020U2\u0006\u0010X\u001a\u00020\u00042\u0006\u0010`\u001a\u00020Z2\f\u0010a\u001a\b\u0012\u0004\u0012\u00020\u001d0/J\u0016\u0010b\u001a\u00020U2\u0006\u0010X\u001a\u00020\u00042\u0006\u0010`\u001a\u00020ZJ\u0016\u0010c\u001a\u00020U2\u0006\u0010X\u001a\u00020\u00042\u0006\u0010`\u001a\u00020ZJ:\u0010d\u001a\u00020U2\u0006\u0010X\u001a\u00020\u00042\b\u0010`\u001a\u0004\u0018\u00010Z2\u0014\b\u0002\u0010e\u001a\u000e\u0012\u0004\u0012\u00020\u001d\u0012\u0004\u0012\u00020]0\\2\n\b\u0002\u0010f\u001a\u0004\u0018\u00010\u001dJ \u0010d\u001a\u00020U2\u0006\u0010X\u001a\u00020\u00042\b\u0010`\u001a\u0004\u0018\u00010Z2\u0006\u0010g\u001a\u00020\u001dJ\u0018\u0010h\u001a\u00020U2\u0006\u0010X\u001a\u00020\u00042\b\u0010`\u001a\u0004\u0018\u00010ZJ\u001a\u0010i\u001a\u0004\u0018\u00010j2\u0006\u0010k\u001a\u00020\u00102\u0006\u0010l\u001a\u00020mH\u0004J\u001e\u0010n\u001a\u00020\u00102\u0006\u0010o\u001a\u00020\u00112\u0006\u0010p\u001a\u00020q2\u0006\u0010r\u001a\u00020\u001eJ\u0096\u0001\u0010s\u001a\u00020N\"\n\b\u0000\u0010t\u0018\u0001*\u00020j\"\u001a\b\u0001\u0010u\u0018\u0001*\u0012\u0012\u0004\u0012\u0002Hw\u0012\u0004\u0012\u0002Ht\u0012\u0002\b\u00030v\"\u0014\b\u0002\u0010w\u0018\u0001*\f\u0012\u0002\b\u0003\u0012\u0004\u0012\u0002Ht0x2\u0006\u0010y\u001a\u0002Hu2\u0006\u0010z\u001a\u00020\u001028\b\b\u0010{\u001a2\u0012\u0013\u0012\u0011Ht¢\u0006\f\b}\u0012\b\b~\u0012\u0004\b\b(O\u0012\u0013\u0012\u0011Ht¢\u0006\f\b}\u0012\b\b~\u0012\u0004\b\b(Q\u0012\u0004\u0012\u00020N0|H\u0082\b¢\u0006\u0002\u0010\u007fJ\t\u0010\u0080\u0001\u001a\u00020NH\u0002J\u0014\u0010\u0081\u0001\u001a\u0005\u0018\u00010\u0082\u00012\u0006\u0010k\u001a\u00020\u0010H\u0002J\u0013\u0010\u0083\u0001\u001a\u0004\u0018\u00010j2\u0006\u0010k\u001a\u00020\u0010H\u0016J\u0013\u0010\u0084\u0001\u001a\u00030\u0085\u00012\u0007\u0010\u0086\u0001\u001a\u00020jH\u0016J\u0019\u0010\u0087\u0001\u001a\u00020\u00102\u0007\u0010\u0088\u0001\u001a\u00020\u00112\u0007\u0010\u0089\u0001\u001a\u00020\u0010J\"\u0010\u008a\u0001\u001a\u00020N2\b\u0010T\u001a\u0004\u0018\u00010U2\r\u0010\u008b\u0001\u001a\b\u0012\u0004\u0012\u0002000/H\u0016J\u0011\u0010\u008c\u0001\u001a\u00020m2\u0006\u0010X\u001a\u00020\u0004H$J\u0019\u0010\u008d\u0001\u001a\u00020\u001e2\u0006\u0010X\u001a\u00020\u00042\u0006\u0010r\u001a\u00020\u001eH\u0014J\u0011\u0010\u008e\u0001\u001a\u00020m2\u0006\u0010k\u001a\u00020\u0010H\u0014J\u0012\u0010\u008f\u0001\u001a\u00020N2\u0007\u0010\u0090\u0001\u001a\u00020mH\u0016J%\u0010\u0091\u0001\u001a\u00020\u00102\u0007\u0010\u0092\u0001\u001a\u00020\u00112\b\u0010\u0093\u0001\u001a\u00030\u0094\u00012\u0007\u0010g\u001a\u00030\u0095\u0001H\u0016J\u001a\u0010\u0096\u0001\u001a\u00020\u001e2\u0007\u0010\u0097\u0001\u001a\u00020\u00042\b\u0010o\u001a\u0004\u0018\u00010\u0011J\u0013\u0010\u0098\u0001\u001a\u00020q2\b\u0010\u0093\u0001\u001a\u00030\u0094\u0001H\u0002J\u001d\u0010\u0099\u0001\u001a\u0005\u0018\u00010\u009a\u00012\u0007\u0010\u009b\u0001\u001a\u00020j2\u0006\u0010o\u001a\u00020\u0011H\u0016J\u001d\u0010\u009c\u0001\u001a\u0005\u0018\u00010\u009d\u00012\u0007\u0010\u009b\u0001\u001a\u00020j2\u0006\u0010o\u001a\u00020\u0011H\u0016J\u0013\u0010\u009e\u0001\u001a\u0004\u0018\u00010j2\u0006\u0010k\u001a\u00020\u0010H\u0002R0\u0010\u0014\u001a\u001e\u0012\u0004\u0012\u00020\u0011\u0012\u0004\u0012\u00020\u00100\u0015j\u000e\u0012\u0004\u0012\u00020\u0011\u0012\u0004\u0012\u00020\u0010`\u0016X\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0018R\u0011\u0010\u0007\u001a\u00020\b¢\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u001aR\u0010\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R0\u0010\u001b\u001a\u001e\u0012\u0004\u0012\u00020\u001d\u0012\u0004\u0012\u00020\u001e0\u001cj\u000e\u0012\u0004\u0012\u00020\u001d\u0012\u0004\u0012\u00020\u001e`\u001fX\u0084\u0004¢\u0006\b\n\u0000\u001a\u0004\b \u0010!R-\u0010\"\u001a\u001e\u0012\u0004\u0012\u00020\u0011\u0012\u0004\u0012\u00020\u00110\u001cj\u000e\u0012\u0004\u0012\u00020\u0011\u0012\u0004\u0012\u00020\u0011`\u001f¢\u0006\b\n\u0000\u001a\u0004\b#\u0010!R0\u0010$\u001a\u001e\u0012\u0004\u0012\u00020\u0011\u0012\u0004\u0012\u00020\u00100\u0015j\u000e\u0012\u0004\u0012\u00020\u0011\u0012\u0004\u0012\u00020\u0010`\u0016X\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b%\u0010\u0018R\u0014\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\u00040\fX\u0082\u0004¢\u0006\u0002\n\u0000R\u0012\u0010&\u001a\u00020'X¦\u0004¢\u0006\u0006\u001a\u0004\b(\u0010)R\u0011\u0010*\u001a\u00020+¢\u0006\b\n\u0000\u001a\u0004\b,\u0010-R\u0014\u0010.\u001a\b\u0012\u0004\u0012\u0002000/X\u0082.¢\u0006\u0002\n\u0000R\u0011\u0010\u0005\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b1\u00102R!\u00103\u001a\u0012\u0012\u0004\u0012\u00020\u001e04j\b\u0012\u0004\u0012\u00020\u001e`5¢\u0006\b\n\u0000\u001a\u0004\b6\u00107R\u0014\u00108\u001a\u0002098VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b:\u0010;R.\u0010\r\u001a\u001f\u0012\u0004\u0012\u00020\u000f\u0012\u0004\u0012\u00020\u0010\u0012\u0004\u0012\u00020\u0011\u0012\u0004\u0012\u00020\u00100\u000e¢\u0006\u0002\b\u0012¢\u0006\b\n\u0000\u001a\u0004\b<\u0010=R\u0011\u0010\t\u001a\u00020\n¢\u0006\b\n\u0000\u001a\u0004\b>\u0010?R-\u0010@\u001a\u001e\u0012\u0004\u0012\u00020\u0011\u0012\u0004\u0012\u00020\u001e0\u0015j\u000e\u0012\u0004\u0012\u00020\u0011\u0012\u0004\u0012\u00020\u001e`\u0016¢\u0006\b\n\u0000\u001a\u0004\bA\u0010\u0018R\u0014\u0010B\u001a\u0004\u0018\u00010CX¦\u0004¢\u0006\u0006\u001a\u0004\bD\u0010ER\u001e\u0010F\u001a\u0012\u0012\u0004\u0012\u00020\u00100Gj\b\u0012\u0004\u0012\u00020\u0010`HX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010I\u001a\u00020J8TX\u0094\u0004¢\u0006\u0006\u001a\u0004\bK\u0010L¨\u0006\u009f\u0001"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/serialization/KotlinIrLinker;", "Lorg/jetbrains/kotlin/ir/linkage/IrDeserializer;", "Lorg/jetbrains/kotlin/backend/common/overrides/FileLocalAwareLinker;", "currentModule", "Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;", "messageLogger", "Lorg/jetbrains/kotlin/ir/util/IrMessageLogger;", "builtIns", "Lorg/jetbrains/kotlin/ir/IrBuiltIns;", "symbolTable", "Lorg/jetbrains/kotlin/ir/util/SymbolTable;", "exportedDependencies", "", "symbolProcessor", "Lkotlin/Function3;", "Lorg/jetbrains/kotlin/backend/common/serialization/IrSymbolDeserializer;", "Lorg/jetbrains/kotlin/ir/symbols/IrSymbol;", "Lorg/jetbrains/kotlin/ir/util/IdSignature;", "Lkotlin/ExtensionFunctionType;", "(Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;Lorg/jetbrains/kotlin/ir/util/IrMessageLogger;Lorg/jetbrains/kotlin/ir/IrBuiltIns;Lorg/jetbrains/kotlin/ir/util/SymbolTable;Ljava/util/List;Lkotlin/jvm/functions/Function3;)V", "actualSymbols", "Ljava/util/HashMap;", "Lkotlin/collections/HashMap;", "getActualSymbols$ir_serialization_common", "()Ljava/util/HashMap;", "getBuiltIns", "()Lorg/jetbrains/kotlin/ir/IrBuiltIns;", "deserializersForModules", "Ljava/util/LinkedHashMap;", "", "Lorg/jetbrains/kotlin/backend/common/serialization/IrModuleDeserializer;", "Lkotlin/collections/LinkedHashMap;", "getDeserializersForModules", "()Ljava/util/LinkedHashMap;", "expectIdSignatureToActualIdSignature", "getExpectIdSignatureToActualIdSignature", "expectSymbols", "getExpectSymbols$ir_serialization_common", "fakeOverrideBuilder", "Lorg/jetbrains/kotlin/backend/common/overrides/FakeOverrideBuilder;", "getFakeOverrideBuilder", "()Lorg/jetbrains/kotlin/backend/common/overrides/FakeOverrideBuilder;", "internationService", "Lorg/jetbrains/kotlin/backend/common/serialization/IrInterningService;", "getInternationService", "()Lorg/jetbrains/kotlin/backend/common/serialization/IrInterningService;", "linkerExtensions", "", "Lorg/jetbrains/kotlin/ir/linkage/IrDeserializer$IrLinkerExtension;", "getMessageLogger", "()Lorg/jetbrains/kotlin/ir/util/IrMessageLogger;", "modulesWithReachableTopLevels", "Ljava/util/LinkedHashSet;", "Lkotlin/collections/LinkedHashSet;", "getModulesWithReachableTopLevels", "()Ljava/util/LinkedHashSet;", "partialLinkageSupport", "Lorg/jetbrains/kotlin/backend/common/linkage/partial/PartialLinkageSupportForLinker;", "getPartialLinkageSupport", "()Lorg/jetbrains/kotlin/backend/common/linkage/partial/PartialLinkageSupportForLinker;", "getSymbolProcessor", "()Lkotlin/jvm/functions/Function3;", "getSymbolTable", "()Lorg/jetbrains/kotlin/ir/util/SymbolTable;", "topLevelActualIdSignatureToModuleDeserializer", "getTopLevelActualIdSignatureToModuleDeserializer", "translationPluginContext", "Lorg/jetbrains/kotlin/ir/builders/TranslationPluginContext;", "getTranslationPluginContext", "()Lorg/jetbrains/kotlin/ir/builders/TranslationPluginContext;", "triedToDeserializeDeclarationForSymbol", "Ljava/util/HashSet;", "Lkotlin/collections/HashSet;", "userVisibleIrModulesSupport", "Lorg/jetbrains/kotlin/backend/common/linkage/issues/UserVisibleIrModulesSupport;", "getUserVisibleIrModulesSupport", "()Lorg/jetbrains/kotlin/backend/common/linkage/issues/UserVisibleIrModulesSupport;", "actualizeIrFunction", "", "e", "Lorg/jetbrains/kotlin/ir/declarations/IrFunction;", "a", LineReader.CLEAR, "createCurrentModuleDeserializer", "moduleFragment", "Lorg/jetbrains/kotlin/ir/declarations/IrModuleFragment;", "dependencies", "createModuleDeserializer", "moduleDescriptor", "klib", "Lorg/jetbrains/kotlin/library/KotlinLibrary;", "strategyResolver", "Lkotlin/Function1;", "Lorg/jetbrains/kotlin/backend/common/serialization/DeserializationStrategy;", "deserializeAllReachableTopLevels", "deserializeDirtyFiles", "kotlinLibrary", "dirtyFiles", "deserializeFullModule", "deserializeHeadersWithInlineBodies", "deserializeIrModuleHeader", "deserializationStrategy", "_moduleName", "moduleName", "deserializeOnlyHeaderModule", "deserializeOrResolveDeclaration", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;", NativeSymbol.TYPE_NAME, "allowSymbolsWithoutSignaturesFromOtherModule", "", "deserializeOrReturnUnboundIrSymbolIfPartialLinkageEnabled", "idSignature", "symbolKind", "Lorg/jetbrains/kotlin/backend/common/serialization/encodings/BinarySymbolData$SymbolKind;", "moduleDeserializer", "finalizeExpectActual", "Owner", "DelegatingSymbol", "Lorg/jetbrains/kotlin/ir/symbols/IrDelegatingSymbol;", "DelegateSymbol", "Lorg/jetbrains/kotlin/ir/symbols/IrBindableSymbol;", "expectSymbol", "actualSymbol", "actualizer", "Lkotlin/Function2;", "Lkotlin/ParameterName;", "name", "(Lorg/jetbrains/kotlin/ir/symbols/IrDelegatingSymbol;Lorg/jetbrains/kotlin/ir/symbols/IrSymbol;Lkotlin/jvm/functions/Function2;)V", "finalizeExpectActualLinker", "findDeserializedDeclarationForSymbol", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "getDeclaration", "getFileOf", "Lorg/jetbrains/kotlin/ir/declarations/IrFile;", "declaration", "handleExpectActualMapping", "idSig", "rawSymbol", ConfigConstants.CONFIG_INIT_SECTION, "extensions", "isBuiltInModule", "maybeWrapWithBuiltInAndInit", "platformSpecificSymbol", "postProcess", "inOrAfterLinkageStep", "resolveBySignatureInModule", "signature", Namer.METADATA_CLASS_KIND, "Lorg/jetbrains/kotlin/ir/linkage/IrDeserializer$TopLevelSymbolKind;", "Lorg/jetbrains/kotlin/name/Name;", "resolveModuleDeserializer", "module", "topLevelKindToSymbolKind", "tryReferencingPropertyByLocalSignature", "Lorg/jetbrains/kotlin/ir/symbols/IrPropertySymbol;", "parent", "tryReferencingSimpleFunctionByLocalSignature", "Lorg/jetbrains/kotlin/ir/symbols/IrSimpleFunctionSymbol;", "tryResolveCustomDeclaration", "ir.serialization.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class KotlinIrLinker implements FileLocalAwareLinker, IrDeserializer {
    private final HashMap<IdSignature, IrSymbol> actualSymbols;
    private final IrBuiltIns builtIns;
    private final ModuleDescriptor currentModule;
    private final LinkedHashMap<String, IrModuleDeserializer> deserializersForModules;
    private final LinkedHashMap<IdSignature, IdSignature> expectIdSignatureToActualIdSignature;
    private final HashMap<IdSignature, IrSymbol> expectSymbols;
    private final List<ModuleDescriptor> exportedDependencies;
    private final IrInterningService internationService;
    private Collection<? extends IrDeserializer.IrLinkerExtension> linkerExtensions;
    private final IrMessageLogger messageLogger;
    private final LinkedHashSet<IrModuleDeserializer> modulesWithReachableTopLevels;
    private final Function3<IrSymbolDeserializer, IrSymbol, IdSignature, IrSymbol> symbolProcessor;
    private final SymbolTable symbolTable;
    private final HashMap<IdSignature, IrModuleDeserializer> topLevelActualIdSignatureToModuleDeserializer;
    private final HashSet<IrSymbol> triedToDeserializeDeclarationForSymbol;

    /* JADX INFO: compiled from: KotlinIrLinker.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[IrDeserializer.TopLevelSymbolKind.values().length];
            try {
                iArr[IrDeserializer.TopLevelSymbolKind.CLASS_SYMBOL.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[IrDeserializer.TopLevelSymbolKind.PROPERTY_SYMBOL.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[IrDeserializer.TopLevelSymbolKind.FUNCTION_SYMBOL.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                iArr[IrDeserializer.TopLevelSymbolKind.TYPEALIAS_SYMBOL.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    protected abstract IrModuleDeserializer createModuleDeserializer(ModuleDescriptor moduleDescriptor, KotlinLibrary klib, Function1<? super String, ? extends DeserializationStrategy> strategyResolver);

    public abstract FakeOverrideBuilder getFakeOverrideBuilder();

    public abstract TranslationPluginContext getTranslationPluginContext();

    protected abstract boolean isBuiltInModule(ModuleDescriptor moduleDescriptor);

    protected boolean platformSpecificSymbol(IrSymbol symbol) {
        Intrinsics.checkNotNullParameter(symbol, "symbol");
        return false;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public KotlinIrLinker(ModuleDescriptor moduleDescriptor, IrMessageLogger messageLogger, IrBuiltIns builtIns, SymbolTable symbolTable, List<? extends ModuleDescriptor> exportedDependencies, Function3<? super IrSymbolDeserializer, ? super IrSymbol, ? super IdSignature, ? extends IrSymbol> symbolProcessor) {
        Intrinsics.checkNotNullParameter(messageLogger, "messageLogger");
        Intrinsics.checkNotNullParameter(builtIns, "builtIns");
        Intrinsics.checkNotNullParameter(symbolTable, "symbolTable");
        Intrinsics.checkNotNullParameter(exportedDependencies, "exportedDependencies");
        Intrinsics.checkNotNullParameter(symbolProcessor, "symbolProcessor");
        this.currentModule = moduleDescriptor;
        this.messageLogger = messageLogger;
        this.builtIns = builtIns;
        this.symbolTable = symbolTable;
        this.exportedDependencies = exportedDependencies;
        this.symbolProcessor = symbolProcessor;
        this.internationService = new IrInterningService();
        this.expectIdSignatureToActualIdSignature = new LinkedHashMap<>();
        this.topLevelActualIdSignatureToModuleDeserializer = new HashMap<>();
        this.expectSymbols = new HashMap<>();
        this.actualSymbols = new HashMap<>();
        this.modulesWithReachableTopLevels = new LinkedHashSet<>();
        this.deserializersForModules = new LinkedHashMap<>();
        this.triedToDeserializeDeclarationForSymbol = new HashSet<>();
    }

    @Override // org.jetbrains.kotlin.ir.linkage.IrDeserializer
    @Deprecated(level = DeprecationLevel.ERROR, message = "Use postProcess(inOrAfterLinkageStep) instead", replaceWith = @ReplaceWith(expression = "postProcess(inOrAfterLinkageStep = true)", imports = {}))
    public void postProcess() {
        IrDeserializer.DefaultImpls.postProcess(this);
    }

    public final IrMessageLogger getMessageLogger() {
        return this.messageLogger;
    }

    public final IrBuiltIns getBuiltIns() {
        return this.builtIns;
    }

    public final SymbolTable getSymbolTable() {
        return this.symbolTable;
    }

    public /* synthetic */ KotlinIrLinker(ModuleDescriptor moduleDescriptor, IrMessageLogger irMessageLogger, IrBuiltIns irBuiltIns, SymbolTable symbolTable, List list, AnonymousClass1 anonymousClass1, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(moduleDescriptor, irMessageLogger, irBuiltIns, symbolTable, list, (i & 32) != 0 ? new Function3<IrSymbolDeserializer, IrSymbol, IdSignature, IrSymbol>() { // from class: org.jetbrains.kotlin.backend.common.serialization.KotlinIrLinker.1
            @Override // kotlin.jvm.functions.Function3
            public final IrSymbol invoke(IrSymbolDeserializer irSymbolDeserializer, IrSymbol s, IdSignature idSignature) {
                Intrinsics.checkNotNullParameter(irSymbolDeserializer, "$this$null");
                Intrinsics.checkNotNullParameter(s, "s");
                Intrinsics.checkNotNullParameter(idSignature, "<anonymous parameter 1>");
                return s;
            }
        } : anonymousClass1);
    }

    public final Function3<IrSymbolDeserializer, IrSymbol, IdSignature, IrSymbol> getSymbolProcessor() {
        return this.symbolProcessor;
    }

    public final IrInterningService getInternationService() {
        return this.internationService;
    }

    public final LinkedHashMap<IdSignature, IdSignature> getExpectIdSignatureToActualIdSignature() {
        return this.expectIdSignatureToActualIdSignature;
    }

    public final HashMap<IdSignature, IrModuleDeserializer> getTopLevelActualIdSignatureToModuleDeserializer() {
        return this.topLevelActualIdSignatureToModuleDeserializer;
    }

    public final HashMap<IdSignature, IrSymbol> getExpectSymbols$ir_serialization_common() {
        return this.expectSymbols;
    }

    public final HashMap<IdSignature, IrSymbol> getActualSymbols$ir_serialization_common() {
        return this.actualSymbols;
    }

    public final LinkedHashSet<IrModuleDeserializer> getModulesWithReachableTopLevels() {
        return this.modulesWithReachableTopLevels;
    }

    protected final LinkedHashMap<String, IrModuleDeserializer> getDeserializersForModules() {
        return this.deserializersForModules;
    }

    public PartialLinkageSupportForLinker getPartialLinkageSupport() {
        return PartialLinkageSupportForLinker.INSTANCE.getDISABLED();
    }

    protected UserVisibleIrModulesSupport getUserVisibleIrModulesSupport() {
        return UserVisibleIrModulesSupport.INSTANCE.getDEFAULT();
    }

    public final IrSymbol deserializeOrReturnUnboundIrSymbolIfPartialLinkageEnabled(IdSignature idSignature, BinarySymbolData.SymbolKind symbolKind, IrModuleDeserializer moduleDeserializer) {
        Intrinsics.checkNotNullParameter(idSignature, "idSignature");
        Intrinsics.checkNotNullParameter(symbolKind, "symbolKind");
        Intrinsics.checkNotNullParameter(moduleDeserializer, "moduleDeserializer");
        IrModuleDeserializer irModuleDeserializerFindModuleDeserializerForTopLevelId = BasicIrModuleDeserializerKt.findModuleDeserializerForTopLevelId(moduleDeserializer, idSignature.topLevelSignature());
        IrSymbol irSymbolMo6122tryDeserializeIrSymbol = irModuleDeserializerFindModuleDeserializerForTopLevelId != null ? irModuleDeserializerFindModuleDeserializerForTopLevelId.mo6122tryDeserializeIrSymbol(idSignature, symbolKind) : null;
        if (irSymbolMo6122tryDeserializeIrSymbol != null) {
            return irSymbolMo6122tryDeserializeIrSymbol;
        }
        if (getPartialLinkageSupport().isEnabled()) {
            return IrSymbolDeserializerKt.referenceDeserializedSymbol(this.symbolTable, null, symbolKind, idSignature);
        }
        Collection<IrModuleDeserializer> collectionValues = this.deserializersForModules.values();
        Intrinsics.checkNotNullExpressionValue(collectionValues, "<get-values>(...)");
        new SignatureIdNotFoundInModuleWithDependencies(idSignature, moduleDeserializer, collectionValues, getUserVisibleIrModulesSupport()).raiseIssue(this.messageLogger);
        throw null;
    }

    public final IrModuleDeserializer resolveModuleDeserializer(ModuleDescriptor module, IdSignature idSignature) {
        Intrinsics.checkNotNullParameter(module, "module");
        IrModuleDeserializer irModuleDeserializer = this.deserializersForModules.get(module.getShortName().asString());
        if (irModuleDeserializer != null) {
            return irModuleDeserializer;
        }
        Name name2 = module.getShortName();
        Intrinsics.checkNotNullExpressionValue(name2, "getName(...)");
        new NoDeserializerForModule(name2, idSignature).raiseIssue(this.messageLogger);
        throw null;
    }

    public final void deserializeAllReachableTopLevels() {
        while (!this.modulesWithReachableTopLevels.isEmpty()) {
            IrModuleDeserializer irModuleDeserializer = (IrModuleDeserializer) CollectionsKt.first(this.modulesWithReachableTopLevels);
            this.modulesWithReachableTopLevels.remove(irModuleDeserializer);
            irModuleDeserializer.deserializeReachableDeclarations();
        }
    }

    private final DeclarationDescriptor findDeserializedDeclarationForSymbol(IrSymbol symbol) {
        if (!this.triedToDeserializeDeclarationForSymbol.add(symbol) || !symbol.getHasDescriptor()) {
            return null;
        }
        DeclarationDescriptor descriptor = symbol.getDescriptor();
        resolveModuleDeserializer(DescriptorUtilsKt.getModule(descriptor), symbol.getSignature()).declareIrSymbol(symbol);
        deserializeAllReachableTopLevels();
        if (symbol.isBound()) {
            return descriptor;
        }
        return null;
    }

    private final IrDeclaration tryResolveCustomDeclaration(IrSymbol symbol) {
        TranslationPluginContext translationPluginContext;
        IrDeclaration irDeclarationResolveSymbol;
        if (!symbol.getHasDescriptor()) {
            return null;
        }
        DeclarationDescriptor descriptor = symbol.getDescriptor();
        if (((descriptor instanceof CallableMemberDescriptor) && ((CallableMemberDescriptor) descriptor).getKind() == CallableMemberDescriptor.Kind.FAKE_OVERRIDE) || (translationPluginContext = getTranslationPluginContext()) == null) {
            return null;
        }
        Collection<? extends IrDeserializer.IrLinkerExtension> collection = this.linkerExtensions;
        if (collection == null) {
            Intrinsics.throwUninitializedPropertyAccessException("linkerExtensions");
            collection = null;
        }
        Iterator<T> it = collection.iterator();
        do {
            if (!it.hasNext()) {
                irDeclarationResolveSymbol = null;
                break;
            }
            irDeclarationResolveSymbol = ((IrDeserializer.IrLinkerExtension) it.next()).resolveSymbol(symbol, translationPluginContext);
        } while (irDeclarationResolveSymbol == null);
        if (irDeclarationResolveSymbol == null) {
            return null;
        }
        if (Intrinsics.areEqual(symbol.getOwner(), irDeclarationResolveSymbol)) {
            return irDeclarationResolveSymbol;
        }
        throw new IllegalArgumentException("Failed requirement.".toString());
    }

    @Override // org.jetbrains.kotlin.ir.linkage.IrProvider
    public IrDeclaration getDeclaration(IrSymbol symbol) {
        Intrinsics.checkNotNullParameter(symbol, "symbol");
        return deserializeOrResolveDeclaration(symbol, false);
    }

    protected final IrDeclaration deserializeOrResolveDeclaration(IrSymbol symbol, boolean allowSymbolsWithoutSignaturesFromOtherModule) {
        DeclarationDescriptor moduleDescriptor;
        Intrinsics.checkNotNullParameter(symbol, "symbol");
        if (!allowSymbolsWithoutSignaturesFromOtherModule && !IrSymbolKt.isPublicApi(symbol) && symbol.getHasDescriptor() && !platformSpecificSymbol(symbol) && DescriptorUtilsKt.getModule(symbol.getDescriptor()) != this.currentModule) {
            return null;
        }
        if (!symbol.isBound()) {
            try {
                if (findDeserializedDeclarationForSymbol(symbol) == null && tryResolveCustomDeclaration(symbol) == null) {
                    return null;
                }
            } catch (IrSymbolTypeMismatchException e) {
                Collection<IrModuleDeserializer> collectionValues = this.deserializersForModules.values();
                Intrinsics.checkNotNullExpressionValue(collectionValues, "<get-values>(...)");
                new SymbolTypeMismatch(e, collectionValues, getUserVisibleIrModulesSupport()).raiseIssue(this.messageLogger);
                throw null;
            }
        }
        if ((!symbol.isBound() && (LegacyDescriptorUtilsKt.isExpectMember(symbol.getDescriptor()) || ((moduleDescriptor = symbol.getDescriptor().getContainingDeclaration()) != null && LegacyDescriptorUtilsKt.isExpectMember(moduleDescriptor)))) || !symbol.isBound()) {
            return null;
        }
        IrSymbolOwner owner = symbol.getOwner();
        Intrinsics.checkNotNull(owner, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.declarations.IrDeclaration");
        return (IrDeclaration) owner;
    }

    public IrFile getFileOf(IrDeclaration declaration) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        return IrUtilsKt.getFile(declaration);
    }

    @Override // org.jetbrains.kotlin.backend.common.overrides.FileLocalAwareLinker
    public IrSimpleFunctionSymbol tryReferencingSimpleFunctionByLocalSignature(IrDeclaration parent, IdSignature idSignature) {
        Intrinsics.checkNotNullParameter(parent, "parent");
        Intrinsics.checkNotNullParameter(idSignature, "idSignature");
        if (idSignature.isPubliclyVisible()) {
            return null;
        }
        IrFile fileOf = getFileOf(parent);
        return resolveModuleDeserializer(fileOf.getPackageFragmentDescriptor().getContainingDeclaration(), null).referenceSimpleFunctionByLocalSignature(fileOf, idSignature);
    }

    @Override // org.jetbrains.kotlin.backend.common.overrides.FileLocalAwareLinker
    public IrPropertySymbol tryReferencingPropertyByLocalSignature(IrDeclaration parent, IdSignature idSignature) {
        Intrinsics.checkNotNullParameter(parent, "parent");
        Intrinsics.checkNotNullParameter(idSignature, "idSignature");
        if (idSignature.isPubliclyVisible()) {
            return null;
        }
        IrFile fileOf = getFileOf(parent);
        return resolveModuleDeserializer(fileOf.getPackageFragmentDescriptor().getContainingDeclaration(), null).referencePropertyByLocalSignature(fileOf, idSignature);
    }

    protected IrModuleDeserializer createCurrentModuleDeserializer(IrModuleFragment moduleFragment, Collection<? extends IrModuleDeserializer> dependencies) {
        Intrinsics.checkNotNullParameter(moduleFragment, "moduleFragment");
        Intrinsics.checkNotNullParameter(dependencies, "dependencies");
        return new CurrentModuleDeserializer(moduleFragment, dependencies);
    }

    @Override // org.jetbrains.kotlin.ir.linkage.IrDeserializer
    public void init(IrModuleFragment moduleFragment, Collection<? extends IrDeserializer.IrLinkerExtension> extensions) {
        Intrinsics.checkNotNullParameter(extensions, "extensions");
        this.linkerExtensions = extensions;
        if (moduleFragment != null) {
            List<ModuleDescriptor> allDependencyModules = moduleFragment.getDescriptor().getAllDependencyModules();
            ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(allDependencyModules, 10));
            Iterator<T> it = allDependencyModules.iterator();
            while (it.hasNext()) {
                arrayList.add(resolveModuleDeserializer((ModuleDescriptor) it.next(), null));
            }
            IrModuleDeserializer irModuleDeserializerCreateCurrentModuleDeserializer = createCurrentModuleDeserializer(moduleFragment, arrayList);
            LinkedHashMap<String, IrModuleDeserializer> linkedHashMap = this.deserializersForModules;
            String strAsString = moduleFragment.getName().asString();
            Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
            linkedHashMap.put(strAsString, maybeWrapWithBuiltInAndInit(moduleFragment.getDescriptor(), irModuleDeserializerCreateCurrentModuleDeserializer));
        }
        Collection<IrModuleDeserializer> collectionValues = this.deserializersForModules.values();
        Intrinsics.checkNotNullExpressionValue(collectionValues, "<get-values>(...)");
        Iterator<T> it2 = collectionValues.iterator();
        while (it2.hasNext()) {
            ((IrModuleDeserializer) it2.next()).init();
        }
    }

    public final void clear() {
        this.internationService.clear();
    }

    @Override // org.jetbrains.kotlin.ir.linkage.IrDeserializer
    public void postProcess(boolean inOrAfterLinkageStep) {
        finalizeExpectActualLinker();
        if (inOrAfterLinkageStep) {
            getPartialLinkageSupport().exploreClassifiers(getFakeOverrideBuilder());
        }
        getFakeOverrideBuilder().provideFakeOverrides();
        this.triedToDeserializeDeclarationForSymbol.clear();
        if (inOrAfterLinkageStep) {
            getPartialLinkageSupport().generateStubsAndPatchUsages(this.symbolTable, new Function0<Sequence<? extends IrModuleFragment>>() { // from class: org.jetbrains.kotlin.backend.common.serialization.KotlinIrLinker.postProcess.1
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final Sequence<? extends IrModuleFragment> invoke() {
                    Collection<IrModuleDeserializer> collectionValues = KotlinIrLinker.this.getDeserializersForModules().values();
                    Intrinsics.checkNotNullExpressionValue(collectionValues, "<get-values>(...)");
                    return SequencesKt.map(CollectionsKt.asSequence(collectionValues), new Function1<IrModuleDeserializer, IrModuleFragment>() { // from class: org.jetbrains.kotlin.backend.common.serialization.KotlinIrLinker.postProcess.1.1
                        @Override // kotlin.jvm.functions.Function1
                        public final IrModuleFragment invoke(IrModuleDeserializer it) {
                            Intrinsics.checkNotNullParameter(it, "it");
                            return it.getModuleFragment();
                        }
                    });
                }
            });
        }
    }

    public final IrSymbol handleExpectActualMapping(IdSignature idSig, IrSymbol rawSymbol) {
        IrSymbol irSymbol;
        Intrinsics.checkNotNullParameter(idSig, "idSig");
        Intrinsics.checkNotNullParameter(rawSymbol, "rawSymbol");
        if (this.expectIdSignatureToActualIdSignature.values().contains(idSig)) {
            this.actualSymbols.put(idSig, rawSymbol);
        }
        IdSignature idSignature = this.expectIdSignatureToActualIdSignature.get(idSig);
        if (idSignature == null) {
            return rawSymbol;
        }
        idSig.test(IdSignature.Flags.IS_EXPECT);
        IrDelegatingSymbol<? extends IrBindableSymbol<?, ? extends IrDeclaration>, ? extends IrDeclaration, ?> irDelegatingSymbolWrapInDelegatedSymbol = IrDelegatingSymbolKt.wrapInDelegatedSymbol(rawSymbol);
        this.expectSymbols.put(idSig, irDelegatingSymbolWrapInDelegatedSymbol);
        IrModuleDeserializer irModuleDeserializer = this.topLevelActualIdSignatureToModuleDeserializer.get(idSignature);
        if (irModuleDeserializer != null && ((irSymbol = this.actualSymbols.get(idSignature)) == null || !irSymbol.isBound())) {
            irModuleDeserializer.addModuleReachableTopLevel(idSignature);
        }
        return irDelegatingSymbolWrapInDelegatedSymbol;
    }

    private final BinarySymbolData.SymbolKind topLevelKindToSymbolKind(IrDeserializer.TopLevelSymbolKind kind) {
        int i = WhenMappings.$EnumSwitchMapping$0[kind.ordinal()];
        if (i == 1) {
            return BinarySymbolData.SymbolKind.CLASS_SYMBOL;
        }
        if (i == 2) {
            return BinarySymbolData.SymbolKind.PROPERTY_SYMBOL;
        }
        if (i == 3) {
            return BinarySymbolData.SymbolKind.FUNCTION_SYMBOL;
        }
        if (i == 4) {
            return BinarySymbolData.SymbolKind.TYPEALIAS_SYMBOL;
        }
        throw new NoWhenBranchMatchedException();
    }

    @Override // org.jetbrains.kotlin.ir.linkage.IrDeserializer
    public IrSymbol resolveBySignatureInModule(IdSignature signature, IrDeserializer.TopLevelSymbolKind kind, Name moduleName) {
        Object next;
        IrModuleDeserializer irModuleDeserializer;
        Intrinsics.checkNotNullParameter(signature, "signature");
        Intrinsics.checkNotNullParameter(kind, "kind");
        Intrinsics.checkNotNullParameter(moduleName, "moduleName");
        Set<Map.Entry<String, IrModuleDeserializer>> setEntrySet = this.deserializersForModules.entrySet();
        Intrinsics.checkNotNullExpressionValue(setEntrySet, "<get-entries>(...)");
        Iterator<T> it = setEntrySet.iterator();
        do {
            if (!it.hasNext()) {
                next = null;
                break;
            }
            next = it.next();
        } while (!Intrinsics.areEqual(((Map.Entry) next).getKey(), moduleName.asString()));
        Map.Entry entry = (Map.Entry) next;
        if (entry == null || (irModuleDeserializer = (IrModuleDeserializer) entry.getValue()) == null) {
            throw new IllegalStateException(("No module for name '" + moduleName + "' found").toString());
        }
        Intrinsics.areEqual(signature, signature.topLevelSignature());
        if (!irModuleDeserializer.contains(signature)) {
            throw new IllegalStateException(("No signature " + signature + " in module " + moduleName).toString());
        }
        IrSymbol irSymbolDeserializeIrSymbolOrFail = IrModuleDeserializerKt.deserializeIrSymbolOrFail(irModuleDeserializer, signature, topLevelKindToSymbolKind(kind));
        deserializeAllReachableTopLevels();
        return irSymbolDeserializeIrSymbolOrFail;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void actualizeIrFunction(IrFunction e, IrFunction a) {
        int size = e.getValueParameters().size();
        for (int i = 0; i < size; i++) {
            IrValueParameter irValueParameter = e.getValueParameters().get(i);
            IrValueParameter irValueParameter2 = a.getValueParameters().get(i);
            IrExpressionBody defaultValue = irValueParameter.getDefaultValue();
            if (irValueParameter2.getDefaultValue() == null && defaultValue != null) {
                irValueParameter2.setDefaultValue((IrExpressionBody) PatchDeclarationParentsKt.patchDeclarationParents(defaultValue, a));
                irValueParameter.setDefaultValue(null);
            }
        }
    }

    private final void finalizeExpectActualLinker() {
        for (Map.Entry<IdSignature, IdSignature> entry : this.expectIdSignatureToActualIdSignature.entrySet()) {
            IrSymbol irSymbol = this.expectSymbols.get(entry.getKey());
            IrSymbol irSymbol2 = this.actualSymbols.get(entry.getValue());
            if (irSymbol != null && irSymbol2 != null) {
                if (irSymbol instanceof IrDelegatingClassSymbolImpl) {
                    IrDelegatingSymbol irDelegatingSymbol = (IrDelegatingSymbol) irSymbol;
                    KotlinIrLinker$finalizeExpectActualLinker$1$1 kotlinIrLinker$finalizeExpectActualLinker$1$1 = new Function2<IrClass, IrClass, Unit>() { // from class: org.jetbrains.kotlin.backend.common.serialization.KotlinIrLinker$finalizeExpectActualLinker$1$1
                        /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                        public final void invoke2(IrClass irClass, IrClass irClass2) {
                            Intrinsics.checkNotNullParameter(irClass, "<anonymous parameter 0>");
                            Intrinsics.checkNotNullParameter(irClass2, "<anonymous parameter 1>");
                        }

                        @Override // kotlin.jvm.functions.Function2
                        public /* bridge */ /* synthetic */ Unit invoke(IrClass irClass, IrClass irClass2) {
                            invoke2(irClass, irClass2);
                            return Unit.INSTANCE;
                        }
                    };
                    if (!(irSymbol2 instanceof IrClassSymbol)) {
                        throw new IllegalArgumentException("Failed requirement.".toString());
                    }
                    IrBindableSymbol irBindableSymbol = (IrBindableSymbol) irSymbol2;
                    kotlinIrLinker$finalizeExpectActualLinker$1$1.invoke((IrDeclaration) irDelegatingSymbol.getOwner(), (IrDeclaration) irBindableSymbol.getOwner());
                    irDelegatingSymbol.setDelegate(irBindableSymbol);
                } else if (irSymbol instanceof IrDelegatingEnumEntrySymbolImpl) {
                    IrDelegatingSymbol irDelegatingSymbol2 = (IrDelegatingSymbol) irSymbol;
                    KotlinIrLinker$finalizeExpectActualLinker$1$2 kotlinIrLinker$finalizeExpectActualLinker$1$2 = new Function2<IrEnumEntry, IrEnumEntry, Unit>() { // from class: org.jetbrains.kotlin.backend.common.serialization.KotlinIrLinker$finalizeExpectActualLinker$1$2
                        /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                        public final void invoke2(IrEnumEntry irEnumEntry, IrEnumEntry irEnumEntry2) {
                            Intrinsics.checkNotNullParameter(irEnumEntry, "<anonymous parameter 0>");
                            Intrinsics.checkNotNullParameter(irEnumEntry2, "<anonymous parameter 1>");
                        }

                        @Override // kotlin.jvm.functions.Function2
                        public /* bridge */ /* synthetic */ Unit invoke(IrEnumEntry irEnumEntry, IrEnumEntry irEnumEntry2) {
                            invoke2(irEnumEntry, irEnumEntry2);
                            return Unit.INSTANCE;
                        }
                    };
                    if (!(irSymbol2 instanceof IrEnumEntrySymbol)) {
                        throw new IllegalArgumentException("Failed requirement.".toString());
                    }
                    IrBindableSymbol irBindableSymbol2 = (IrBindableSymbol) irSymbol2;
                    kotlinIrLinker$finalizeExpectActualLinker$1$2.invoke((IrDeclaration) irDelegatingSymbol2.getOwner(), (IrDeclaration) irBindableSymbol2.getOwner());
                    irDelegatingSymbol2.setDelegate(irBindableSymbol2);
                } else if (irSymbol instanceof IrDelegatingSimpleFunctionSymbolImpl) {
                    IrDelegatingSymbol irDelegatingSymbol3 = (IrDelegatingSymbol) irSymbol;
                    Function2 function2 = new Function2<IrSimpleFunction, IrSimpleFunction, Unit>() { // from class: org.jetbrains.kotlin.backend.common.serialization.KotlinIrLinker$finalizeExpectActualLinker$1$3
                        {
                            super(2);
                        }

                        @Override // kotlin.jvm.functions.Function2
                        public /* bridge */ /* synthetic */ Unit invoke(IrSimpleFunction irSimpleFunction, IrSimpleFunction irSimpleFunction2) {
                            invoke2(irSimpleFunction, irSimpleFunction2);
                            return Unit.INSTANCE;
                        }

                        /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                        public final void invoke2(IrSimpleFunction e, IrSimpleFunction a) {
                            Intrinsics.checkNotNullParameter(e, "e");
                            Intrinsics.checkNotNullParameter(a, "a");
                            this.this$0.actualizeIrFunction(e, a);
                        }
                    };
                    if (!(irSymbol2 instanceof IrSimpleFunctionSymbol)) {
                        throw new IllegalArgumentException("Failed requirement.".toString());
                    }
                    IrBindableSymbol irBindableSymbol3 = (IrBindableSymbol) irSymbol2;
                    function2.invoke((IrDeclaration) irDelegatingSymbol3.getOwner(), (IrDeclaration) irBindableSymbol3.getOwner());
                    irDelegatingSymbol3.setDelegate(irBindableSymbol3);
                } else if (irSymbol instanceof IrDelegatingConstructorSymbolImpl) {
                    IrDelegatingSymbol irDelegatingSymbol4 = (IrDelegatingSymbol) irSymbol;
                    Function2 function3 = new Function2<IrConstructor, IrConstructor, Unit>() { // from class: org.jetbrains.kotlin.backend.common.serialization.KotlinIrLinker$finalizeExpectActualLinker$1$4
                        {
                            super(2);
                        }

                        @Override // kotlin.jvm.functions.Function2
                        public /* bridge */ /* synthetic */ Unit invoke(IrConstructor irConstructor, IrConstructor irConstructor2) {
                            invoke2(irConstructor, irConstructor2);
                            return Unit.INSTANCE;
                        }

                        /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                        public final void invoke2(IrConstructor e, IrConstructor a) {
                            Intrinsics.checkNotNullParameter(e, "e");
                            Intrinsics.checkNotNullParameter(a, "a");
                            this.this$0.actualizeIrFunction(e, a);
                        }
                    };
                    if (!(irSymbol2 instanceof IrConstructorSymbol)) {
                        throw new IllegalArgumentException("Failed requirement.".toString());
                    }
                    IrBindableSymbol irBindableSymbol4 = (IrBindableSymbol) irSymbol2;
                    function3.invoke((IrDeclaration) irDelegatingSymbol4.getOwner(), (IrDeclaration) irBindableSymbol4.getOwner());
                    irDelegatingSymbol4.setDelegate(irBindableSymbol4);
                } else if (irSymbol instanceof IrDelegatingPropertySymbolImpl) {
                    IrDelegatingSymbol irDelegatingSymbol5 = (IrDelegatingSymbol) irSymbol;
                    KotlinIrLinker$finalizeExpectActualLinker$1$5 kotlinIrLinker$finalizeExpectActualLinker$1$5 = new Function2<IrProperty, IrProperty, Unit>() { // from class: org.jetbrains.kotlin.backend.common.serialization.KotlinIrLinker$finalizeExpectActualLinker$1$5
                        /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                        public final void invoke2(IrProperty irProperty, IrProperty irProperty2) {
                            Intrinsics.checkNotNullParameter(irProperty, "<anonymous parameter 0>");
                            Intrinsics.checkNotNullParameter(irProperty2, "<anonymous parameter 1>");
                        }

                        @Override // kotlin.jvm.functions.Function2
                        public /* bridge */ /* synthetic */ Unit invoke(IrProperty irProperty, IrProperty irProperty2) {
                            invoke2(irProperty, irProperty2);
                            return Unit.INSTANCE;
                        }
                    };
                    if (!(irSymbol2 instanceof IrPropertySymbol)) {
                        throw new IllegalArgumentException("Failed requirement.".toString());
                    }
                    IrBindableSymbol irBindableSymbol5 = (IrBindableSymbol) irSymbol2;
                    kotlinIrLinker$finalizeExpectActualLinker$1$5.invoke((IrDeclaration) irDelegatingSymbol5.getOwner(), (IrDeclaration) irBindableSymbol5.getOwner());
                    irDelegatingSymbol5.setDelegate(irBindableSymbol5);
                } else {
                    throw new IllegalStateException(("Unexpected expect symbol kind during actualization: " + irSymbol).toString());
                }
            }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ IrModuleFragment deserializeIrModuleHeader$default(KotlinIrLinker kotlinIrLinker, ModuleDescriptor moduleDescriptor, KotlinLibrary kotlinLibrary, Function1 function1, String str, int i, Object obj) {
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: deserializeIrModuleHeader");
        }
        if ((i & 4) != 0) {
            function1 = new Function1<String, DeserializationStrategy>() { // from class: org.jetbrains.kotlin.backend.common.serialization.KotlinIrLinker.deserializeIrModuleHeader.1
                @Override // kotlin.jvm.functions.Function1
                public final DeserializationStrategy invoke(String it) {
                    Intrinsics.checkNotNullParameter(it, "it");
                    return DeserializationStrategy.ONLY_REFERENCED;
                }
            };
        }
        if ((i & 8) != 0) {
            str = null;
        }
        return kotlinIrLinker.deserializeIrModuleHeader(moduleDescriptor, kotlinLibrary, function1, str);
    }

    public final IrModuleFragment deserializeIrModuleHeader(ModuleDescriptor moduleDescriptor, KotlinLibrary kotlinLibrary, Function1<? super String, ? extends DeserializationStrategy> deserializationStrategy, String _moduleName) {
        String uniqueName;
        String str;
        Intrinsics.checkNotNullParameter(moduleDescriptor, "moduleDescriptor");
        Intrinsics.checkNotNullParameter(deserializationStrategy, "deserializationStrategy");
        if (kotlinLibrary == null || (uniqueName = KotlinLibraryKt.getUniqueName(kotlinLibrary)) == null || (str = "<" + uniqueName + '>') == null) {
            Intrinsics.checkNotNull(_moduleName);
        } else {
            _moduleName = str;
        }
        Intrinsics.areEqual(moduleDescriptor.getShortName().asString(), _moduleName);
        LinkedHashMap<String, IrModuleDeserializer> linkedHashMap = this.deserializersForModules;
        IrModuleDeserializer irModuleDeserializerMaybeWrapWithBuiltInAndInit = linkedHashMap.get(_moduleName);
        if (irModuleDeserializerMaybeWrapWithBuiltInAndInit == null) {
            irModuleDeserializerMaybeWrapWithBuiltInAndInit = maybeWrapWithBuiltInAndInit(moduleDescriptor, createModuleDeserializer(moduleDescriptor, kotlinLibrary, deserializationStrategy));
            linkedHashMap.put(_moduleName, irModuleDeserializerMaybeWrapWithBuiltInAndInit);
        }
        return irModuleDeserializerMaybeWrapWithBuiltInAndInit.getModuleFragment();
    }

    protected IrModuleDeserializer maybeWrapWithBuiltInAndInit(ModuleDescriptor moduleDescriptor, IrModuleDeserializer moduleDeserializer) {
        Intrinsics.checkNotNullParameter(moduleDescriptor, "moduleDescriptor");
        Intrinsics.checkNotNullParameter(moduleDeserializer, "moduleDeserializer");
        return isBuiltInModule(moduleDescriptor) ? new IrModuleDeserializerWithBuiltIns(this.builtIns, moduleDeserializer) : moduleDeserializer;
    }

    public final IrModuleFragment deserializeIrModuleHeader(ModuleDescriptor moduleDescriptor, KotlinLibrary kotlinLibrary, String moduleName) {
        Intrinsics.checkNotNullParameter(moduleDescriptor, "moduleDescriptor");
        Intrinsics.checkNotNullParameter(moduleName, "moduleName");
        return deserializeIrModuleHeader(moduleDescriptor, kotlinLibrary, this.exportedDependencies.contains(moduleDescriptor) ? new Function1<String, DeserializationStrategy>() { // from class: org.jetbrains.kotlin.backend.common.serialization.KotlinIrLinker$deserializeIrModuleHeader$deserializationStrategy$1
            @Override // kotlin.jvm.functions.Function1
            public final DeserializationStrategy invoke(String it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return DeserializationStrategy.ALL;
            }
        } : new Function1<String, DeserializationStrategy>() { // from class: org.jetbrains.kotlin.backend.common.serialization.KotlinIrLinker$deserializeIrModuleHeader$deserializationStrategy$2
            @Override // kotlin.jvm.functions.Function1
            public final DeserializationStrategy invoke(String it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return DeserializationStrategy.EXPLICITLY_EXPORTED;
            }
        }, moduleName);
    }

    public final IrModuleFragment deserializeFullModule(ModuleDescriptor moduleDescriptor, KotlinLibrary kotlinLibrary) {
        Intrinsics.checkNotNullParameter(moduleDescriptor, "moduleDescriptor");
        Intrinsics.checkNotNullParameter(kotlinLibrary, "kotlinLibrary");
        return deserializeIrModuleHeader$default(this, moduleDescriptor, kotlinLibrary, new Function1<String, DeserializationStrategy>() { // from class: org.jetbrains.kotlin.backend.common.serialization.KotlinIrLinker.deserializeFullModule.1
            @Override // kotlin.jvm.functions.Function1
            public final DeserializationStrategy invoke(String it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return DeserializationStrategy.ALL;
            }
        }, null, 8, null);
    }

    public final IrModuleFragment deserializeOnlyHeaderModule(ModuleDescriptor moduleDescriptor, KotlinLibrary kotlinLibrary) {
        Intrinsics.checkNotNullParameter(moduleDescriptor, "moduleDescriptor");
        return deserializeIrModuleHeader$default(this, moduleDescriptor, kotlinLibrary, new Function1<String, DeserializationStrategy>() { // from class: org.jetbrains.kotlin.backend.common.serialization.KotlinIrLinker.deserializeOnlyHeaderModule.1
            @Override // kotlin.jvm.functions.Function1
            public final DeserializationStrategy invoke(String it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return DeserializationStrategy.ONLY_DECLARATION_HEADERS;
            }
        }, null, 8, null);
    }

    public final IrModuleFragment deserializeHeadersWithInlineBodies(ModuleDescriptor moduleDescriptor, KotlinLibrary kotlinLibrary) {
        Intrinsics.checkNotNullParameter(moduleDescriptor, "moduleDescriptor");
        Intrinsics.checkNotNullParameter(kotlinLibrary, "kotlinLibrary");
        return deserializeIrModuleHeader$default(this, moduleDescriptor, kotlinLibrary, new Function1<String, DeserializationStrategy>() { // from class: org.jetbrains.kotlin.backend.common.serialization.KotlinIrLinker.deserializeHeadersWithInlineBodies.1
            @Override // kotlin.jvm.functions.Function1
            public final DeserializationStrategy invoke(String it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return DeserializationStrategy.WITH_INLINE_BODIES;
            }
        }, null, 8, null);
    }

    public final IrModuleFragment deserializeDirtyFiles(ModuleDescriptor moduleDescriptor, KotlinLibrary kotlinLibrary, final Collection<String> dirtyFiles) {
        Intrinsics.checkNotNullParameter(moduleDescriptor, "moduleDescriptor");
        Intrinsics.checkNotNullParameter(kotlinLibrary, "kotlinLibrary");
        Intrinsics.checkNotNullParameter(dirtyFiles, "dirtyFiles");
        return deserializeIrModuleHeader$default(this, moduleDescriptor, kotlinLibrary, new Function1<String, DeserializationStrategy>() { // from class: org.jetbrains.kotlin.backend.common.serialization.KotlinIrLinker.deserializeDirtyFiles.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final DeserializationStrategy invoke(String it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return dirtyFiles.contains(it) ? DeserializationStrategy.ALL : DeserializationStrategy.WITH_INLINE_BODIES;
            }
        }, null, 8, null);
    }
}
