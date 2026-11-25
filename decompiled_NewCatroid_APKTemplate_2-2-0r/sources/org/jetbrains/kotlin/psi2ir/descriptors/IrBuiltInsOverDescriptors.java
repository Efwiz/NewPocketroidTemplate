package org.jetbrains.kotlin.psi2ir.descriptors;

import androidx.exifinterface.media.ExifInterface;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.Triple;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.collections.IndexedValue;
import kotlin.collections.MapsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref;
import kotlin.ranges.RangesKt;
import kotlin.sequences.Sequence;
import net.bytebuddy.description.method.ParameterDescription;
import org.apache.xerces.impl.xs.SchemaSymbols;
import org.jetbrains.kotlin.backend.jvm.lower.VarargLoweringKt;
import org.jetbrains.kotlin.builtins.BuiltInsPackageFragment;
import org.jetbrains.kotlin.builtins.KotlinBuiltIns;
import org.jetbrains.kotlin.builtins.PrimitiveType;
import org.jetbrains.kotlin.builtins.UnsignedType;
import org.jetbrains.kotlin.com.intellij.psi.PsiKeyword;
import org.jetbrains.kotlin.config.LanguageVersionSettings;
import org.jetbrains.kotlin.descriptors.CallableMemberDescriptor;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.ClassKind;
import org.jetbrains.kotlin.descriptors.ClassifierDescriptor;
import org.jetbrains.kotlin.descriptors.DescriptorVisibilities;
import org.jetbrains.kotlin.descriptors.DescriptorVisibility;
import org.jetbrains.kotlin.descriptors.FindClassInModuleKt;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.descriptors.Modality;
import org.jetbrains.kotlin.descriptors.PropertyDescriptor;
import org.jetbrains.kotlin.descriptors.ReceiverParameterDescriptor;
import org.jetbrains.kotlin.descriptors.SimpleFunctionDescriptor;
import org.jetbrains.kotlin.descriptors.SourceElement;
import org.jetbrains.kotlin.descriptors.ValueParameterDescriptor;
import org.jetbrains.kotlin.descriptors.annotations.Annotations;
import org.jetbrains.kotlin.descriptors.impl.ModuleDescriptorImpl;
import org.jetbrains.kotlin.descriptors.impl.SimpleFunctionDescriptorImpl;
import org.jetbrains.kotlin.descriptors.impl.TypeParameterDescriptorImpl;
import org.jetbrains.kotlin.descriptors.impl.ValueParameterDescriptorImpl;
import org.jetbrains.kotlin.incremental.IncrementalJvmCacheKt;
import org.jetbrains.kotlin.incremental.components.NoLookupLocation;
import org.jetbrains.kotlin.ir.BuiltInOperatorNames;
import org.jetbrains.kotlin.ir.IrBuiltIns;
import org.jetbrains.kotlin.ir.ObsoleteDescriptorBasedAPI;
import org.jetbrains.kotlin.ir.builders.declarations.DeclarationBuildersKt;
import org.jetbrains.kotlin.ir.builders.declarations.IrClassBuilder;
import org.jetbrains.kotlin.ir.builders.declarations.IrFunctionBuilder;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrConstructor;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationOrigin;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationOriginImpl;
import org.jetbrains.kotlin.ir.declarations.IrExternalPackageFragment;
import org.jetbrains.kotlin.ir.declarations.IrFactory;
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction;
import org.jetbrains.kotlin.ir.declarations.IrTypeParameter;
import org.jetbrains.kotlin.ir.declarations.IrValueParameter;
import org.jetbrains.kotlin.ir.declarations.impl.IrExternalPackageFragmentImpl;
import org.jetbrains.kotlin.ir.descriptors.IrAbstractDescriptorBasedFunctionFactory;
import org.jetbrains.kotlin.ir.descriptors.IrBuiltinValueParameterDescriptorImpl;
import org.jetbrains.kotlin.ir.descriptors.IrBuiltinsPackageFragmentDescriptorImpl;
import org.jetbrains.kotlin.ir.descriptors.IrDescriptorBasedFunctionFactory;
import org.jetbrains.kotlin.ir.descriptors.IrSimpleBuiltinOperatorDescriptorImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrConstructorCallImpl;
import org.jetbrains.kotlin.ir.symbols.IrClassSymbol;
import org.jetbrains.kotlin.ir.symbols.IrClassifierSymbol;
import org.jetbrains.kotlin.ir.symbols.IrPropertySymbol;
import org.jetbrains.kotlin.ir.symbols.IrSimpleFunctionSymbol;
import org.jetbrains.kotlin.ir.symbols.IrTypeParameterSymbol;
import org.jetbrains.kotlin.ir.symbols.IrValueParameterSymbol;
import org.jetbrains.kotlin.ir.symbols.impl.IrValueParameterSymbolImpl;
import org.jetbrains.kotlin.ir.types.IrSimpleType;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.ir.types.IrTypeSystemContextImpl;
import org.jetbrains.kotlin.ir.types.IrTypesKt;
import org.jetbrains.kotlin.ir.types.SimpleTypeNullability;
import org.jetbrains.kotlin.ir.types.impl.IrSimpleTypeBuilder;
import org.jetbrains.kotlin.ir.types.impl.IrSimpleTypeImpl;
import org.jetbrains.kotlin.ir.types.impl.IrSimpleTypeImplKt;
import org.jetbrains.kotlin.ir.types.impl.IrTypeBaseKt;
import org.jetbrains.kotlin.ir.util.IrUtilsKt;
import org.jetbrains.kotlin.ir.util.SymbolTable;
import org.jetbrains.kotlin.ir.util.TypeTranslator;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.name.StandardClassIds;
import org.jetbrains.kotlin.resolve.scopes.MemberScope;
import org.jetbrains.kotlin.storage.LockBasedStorageManager;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.KotlinTypeFactory;
import org.jetbrains.kotlin.types.SimpleType;
import org.jetbrains.kotlin.types.TypeAttributes;
import org.jetbrains.kotlin.types.TypeConstructor;
import org.jetbrains.kotlin.types.Variance;
import org.jetbrains.kotlin.types.checker.KotlinTypeChecker;
import org.jetbrains.kotlin.types.checker.KotlinTypeRefiner;
import org.jetbrains.kotlin.util.OperatorNameConventions;

/* JADX INFO: compiled from: IrBuiltInsOverDescriptors.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000 \u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u000f\n\u0002\u0010%\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b?\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\b\u0017\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u001d\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\bB\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\b'\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0011\n\u0002\u0010\u000e\n\u0002\b\t\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u001c\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\r\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ(\u0010Á\u0002\u001a\u00030Â\u00022\u0016\u0010Ã\u0002\u001a\f\u0012\u0007\b\u0001\u0012\u00030Å\u00020Ä\u0002\"\u00030Å\u0002H\u0002¢\u0006\u0003\u0010Æ\u0002J\t\u0010Ç\u0002\u001a\u00020\fH\u0002J\u001c\u0010È\u0002\u001a\u00020\f2\b\u0010É\u0002\u001a\u00030Å\u00022\u0007\u0010Ê\u0002\u001a\u00020\u0014H\u0002J8\u0010Ë\u0002\u001a\u00020\f2\b\u0010É\u0002\u001a\u00030Å\u00022\u0007\u0010Ì\u0002\u001a\u00020\u00142\u000e\u0010Í\u0002\u001a\t\u0012\u0004\u0012\u00020\u00140\u008b\u00022\n\b\u0002\u0010Î\u0002\u001a\u00030Ï\u0002H\u0002J\"\u0010Ð\u0002\u001a\t\u0012\u0004\u0012\u00020\f0Ñ\u00022\u0007\u0010Ò\u0002\u001a\u00020\u00102\u0007\u0010É\u0002\u001a\u00020*H\u0016J2\u0010Ó\u0002\u001a\u0004\u0018\u00010\u00102\u0007\u0010É\u0002\u001a\u00020*2\u0016\u0010Ã\u0002\u001a\f\u0012\u0007\b\u0001\u0012\u00030Å\u00020Ä\u0002\"\u00030Å\u0002H\u0016¢\u0006\u0003\u0010Ô\u0002J\u001e\u0010Ó\u0002\u001a\u0004\u0018\u00010\u00102\u0007\u0010É\u0002\u001a\u00020*2\b\u0010Õ\u0002\u001a\u00030\u0096\u0001H\u0016J\u001d\u0010Ö\u0002\u001a\u0005\u0018\u00010×\u00022\u0007\u0010É\u0002\u001a\u00020*2\b\u0010Õ\u0002\u001a\u00030\u0096\u0001J7\u0010Ø\u0002\u001a\t\u0012\u0004\u0012\u00020\f0Ñ\u00022\u0007\u0010É\u0002\u001a\u00020*2\u0016\u0010Ã\u0002\u001a\f\u0012\u0007\b\u0001\u0012\u00030Å\u00020Ä\u0002\"\u00030Å\u0002H\u0016¢\u0006\u0003\u0010Ù\u0002J#\u0010Ø\u0002\u001a\t\u0012\u0004\u0012\u00020\f0Ñ\u00022\u0007\u0010É\u0002\u001a\u00020*2\b\u0010Õ\u0002\u001a\u00030\u0096\u0001H\u0016J$\u0010Ú\u0002\u001a\n\u0012\u0005\u0012\u00030Û\u00020Ñ\u00022\u0007\u0010É\u0002\u001a\u00020*2\b\u0010Õ\u0002\u001a\u00030\u0096\u0001H\u0016J\u0014\u0010Ü\u0002\u001a\u00030\u0098\u00012\b\u0010Ý\u0002\u001a\u00030Þ\u0002H\u0016J$\u0010ß\u0002\u001a\u00020\f2\u0007\u0010É\u0002\u001a\u00020*2\u0007\u0010à\u0002\u001a\u00020\u00142\u0007\u0010á\u0002\u001a\u00020\u0014H\u0016Jb\u0010â\u0002\u001a\u000f\u0012\u0005\u0012\u0003Hã\u0002\u0012\u0004\u0012\u00020\f0}\"\n\b\u0000\u0010ã\u0002*\u00030ä\u00022\u0007\u0010É\u0002\u001a\u00020*2\u0016\u0010Ã\u0002\u001a\f\u0012\u0007\b\u0001\u0012\u00030Å\u00020Ä\u0002\"\u00030Å\u00022\u0017\u0010å\u0002\u001a\u0012\u0012\u0004\u0012\u000202\u0012\u0007\u0012\u0005\u0018\u0001Hã\u00020æ\u0002H\u0002¢\u0006\u0003\u0010ç\u0002J\u001d\u0010¸\u0001\u001a\u00020\u00102\b\u0010è\u0002\u001a\u00030Ï\u00022\b\u0010é\u0002\u001a\u00030Þ\u0002H\u0016J<\u0010ê\u0002\u001a\u000e\u0012\u0004\u0012\u00020~\u0012\u0004\u0012\u00020\f0}2\u0007\u0010É\u0002\u001a\u00020*2\u0016\u0010Ã\u0002\u001a\f\u0012\u0007\b\u0001\u0012\u00030Å\u00020Ä\u0002\"\u00030Å\u0002H\u0016¢\u0006\u0003\u0010ë\u0002J<\u0010ì\u0002\u001a\u000e\u0012\u0004\u0012\u00020~\u0012\u0004\u0012\u00020\f0}2\u0007\u0010É\u0002\u001a\u00020*2\u0016\u0010Ã\u0002\u001a\f\u0012\u0007\b\u0001\u0012\u00030Å\u00020Ä\u0002\"\u00030Å\u0002H\u0016¢\u0006\u0003\u0010ë\u0002J\u001b\u0010í\u0002\u001a\u00020\f2\u0007\u0010É\u0002\u001a\u00020*2\u0007\u0010î\u0002\u001a\u00020\u0014H\u0016J\u0014\u0010ï\u0002\u001a\u00030\u0098\u00012\b\u0010Ý\u0002\u001a\u00030Þ\u0002H\u0016J\u0014\u0010ð\u0002\u001a\u00030\u0098\u00012\b\u0010Ý\u0002\u001a\u00030Þ\u0002H\u0016J\u0014\u0010ñ\u0002\u001a\u00030\u0098\u00012\b\u0010Ý\u0002\u001a\u00030Þ\u0002H\u0016J*\u0010ò\u0002\u001a\u000e\u0012\u0004\u0012\u00020~\u0012\u0004\u0012\u00020\f0}*\t\u0012\u0004\u0012\u00020\u00140\u008b\u00022\b\u0010É\u0002\u001a\u00030Å\u0002H\u0002J\u000e\u0010ó\u0002\u001a\u00020\u0018*\u00030ô\u0002H\u0002J\u000e\u0010õ\u0002\u001a\u00020\u0018*\u00030ô\u0002H\u0002J\u000e\u0010ö\u0002\u001a\u00020\u0010*\u00030×\u0002H\u0002J\u000e\u0010÷\u0002\u001a\u00020\u0014*\u00030ø\u0002H\u0002R\u0010\u0010\t\u001a\u0004\u0018\u00010\nX\u0082\u000e¢\u0006\u0002\n\u0000R\u0014\u0010\u000b\u001a\u00020\fX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u0014\u0010\u000f\u001a\u00020\u0010X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012R\u0014\u0010\u0013\u001a\u00020\u0014X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0016R\u0011\u0010\u0017\u001a\u00020\u0018¢\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u001aR\u0014\u0010\u001b\u001a\u00020\u0010X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\u0012R\u0014\u0010\u001d\u001a\u00020\u0014X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u001e\u0010\u0016R\u0014\u0010\u001f\u001a\u00020\u0014X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b \u0010\u0016R\u0014\u0010!\u001a\u00020\u0010X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\"\u0010\u0012R\u0014\u0010#\u001a\u00020\fX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b$\u0010\u000eR\u0014\u0010%\u001a\u00020\fX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b&\u0010\u000eR,\u0010'\u001a \u0012\u0016\u0012\u0014\u0012\u0004\u0012\u00020*\u0012\u0004\u0012\u00020\u0014\u0012\u0004\u0012\u00020\u00140)\u0012\u0004\u0012\u00020\f0(X\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010+\u001a\u00020\u0018¢\u0006\b\n\u0000\u001a\u0004\b,\u0010\u001aR\u0014\u0010-\u001a\u00020\u0010X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b.\u0010\u0012R\u0014\u0010/\u001a\u00020\u0010X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b0\u0010\u0012R\u0016\u00101\u001a\u000702¢\u0006\u0002\b3¢\u0006\b\n\u0000\u001a\u0004\b4\u00105R\u0014\u00106\u001a\u00020\fX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b7\u0010\u000eR\u0014\u00108\u001a\u00020\u0014X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b9\u0010\u0016R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b:\u0010;R\u000e\u0010<\u001a\u00020=X\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010>\u001a\u00020\u0018¢\u0006\b\n\u0000\u001a\u0004\b?\u0010\u001aR\u0014\u0010@\u001a\u00020\u0010X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\bA\u0010\u0012R\u0014\u0010B\u001a\u00020\u0010X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\bC\u0010\u0012R\u0014\u0010D\u001a\u00020\u0014X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\bE\u0010\u0016R\u0011\u0010F\u001a\u00020\u0018¢\u0006\b\n\u0000\u001a\u0004\bG\u0010\u001aR\u0014\u0010H\u001a\u00020\u0010X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\bI\u0010\u0012R\u0014\u0010J\u001a\u00020\u0010X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\bK\u0010\u0012R\u0014\u0010L\u001a\u00020\u0010X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\bM\u0010\u0012R\u0014\u0010N\u001a\u00020\u0014X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\bO\u0010\u0016R\u0014\u0010P\u001a\u00020\fX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\bQ\u0010\u000eR\u0014\u0010R\u001a\u00020\u0010X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\bS\u0010\u0012R\u0014\u0010T\u001a\u00020\u0010X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\bU\u0010\u0012R\u0014\u0010V\u001a\u00020\fX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\bW\u0010\u000eR\u0014\u0010X\u001a\u00020\fX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\bY\u0010\u000eR\u0011\u0010Z\u001a\u00020\u0018¢\u0006\b\n\u0000\u001a\u0004\b[\u0010\u001aR\u0014\u0010\\\u001a\u00020\u0010X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b]\u0010\u0012R\u0014\u0010^\u001a\u00020\u0010X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b_\u0010\u0012R\u0014\u0010`\u001a\u00020\u0014X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\ba\u0010\u0016R\u0014\u0010b\u001a\u00020\u0010X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\bc\u0010\u0012R\u0014\u0010d\u001a\u00020\fX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\be\u0010\u000eR\u0014\u0010f\u001a\u00020\fX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\bg\u0010\u000eR\u0014\u0010h\u001a\u00020\fX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\bi\u0010\u000eR\u0014\u0010j\u001a\u00020\fX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\bk\u0010\u000eR\u0011\u0010l\u001a\u00020\u0018¢\u0006\b\n\u0000\u001a\u0004\bm\u0010\u001aR\u0014\u0010n\u001a\u00020\u0010X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\bo\u0010\u0012R\u0014\u0010p\u001a\u00020\u0010X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\bq\u0010\u0012R\u0014\u0010r\u001a\u00020\u0014X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\bs\u0010\u0016R\u0014\u0010t\u001a\u00020\u0010X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\bu\u0010\u0012R$\u0010w\u001a\u00020\n2\u0006\u0010v\u001a\u00020\n8F@FX\u0086\u000e¢\u0006\f\u001a\u0004\bx\u0010y\"\u0004\bz\u0010{R!\u0010|\u001a\u000e\u0012\u0004\u0012\u00020~\u0012\u0004\u0012\u00020\f0}X\u0096\u0004¢\u0006\t\n\u0000\u001a\u0005\b\u007f\u0010\u0080\u0001R#\u0010\u0081\u0001\u001a\u000e\u0012\u0004\u0012\u00020~\u0012\u0004\u0012\u00020\f0}X\u0096\u0004¢\u0006\n\n\u0000\u001a\u0006\b\u0082\u0001\u0010\u0080\u0001R#\u0010\u0083\u0001\u001a\u000e\u0012\u0004\u0012\u00020~\u0012\u0004\u0012\u00020\f0}X\u0096\u0004¢\u0006\n\n\u0000\u001a\u0006\b\u0084\u0001\u0010\u0080\u0001R\u0016\u0010\u0085\u0001\u001a\u00020\fX\u0096\u0004¢\u0006\t\n\u0000\u001a\u0005\b\u0086\u0001\u0010\u000eR\u0013\u0010\u0087\u0001\u001a\u00020\u0018¢\u0006\t\n\u0000\u001a\u0005\b\u0088\u0001\u0010\u001aR\u0016\u0010\u0089\u0001\u001a\u00020\u0010X\u0096\u0004¢\u0006\t\n\u0000\u001a\u0005\b\u008a\u0001\u0010\u0012R\u0016\u0010\u008b\u0001\u001a\u00020\u0010X\u0096\u0004¢\u0006\t\n\u0000\u001a\u0005\b\u008c\u0001\u0010\u0012R\u0016\u0010\u008d\u0001\u001a\u00020\fX\u0096\u0004¢\u0006\t\n\u0000\u001a\u0005\b\u008e\u0001\u0010\u000eR\u0016\u0010\u008f\u0001\u001a\u00020\fX\u0096\u0004¢\u0006\t\n\u0000\u001a\u0005\b\u0090\u0001\u0010\u000eR\u0016\u0010\u0091\u0001\u001a\u00020\u0014X\u0096\u0004¢\u0006\t\n\u0000\u001a\u0005\b\u0092\u0001\u0010\u0016R\u0016\u0010\u0093\u0001\u001a\u00020\fX\u0096\u0004¢\u0006\t\n\u0000\u001a\u0005\b\u0094\u0001\u0010\u000eR\u0010\u0010\u0095\u0001\u001a\u00030\u0096\u0001X\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u0097\u0001\u001a\u00030\u0098\u0001X\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u0099\u0001\u001a\u00030\u009a\u0001X\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u009b\u0001\u001a\u00030\u009c\u0001X\u0082\u0004¢\u0006\u0002\n\u0000R\u0018\u0010\u009d\u0001\u001a\u00030\u009e\u0001X\u0096\u0004¢\u0006\n\n\u0000\u001a\u0006\b\u009f\u0001\u0010 \u0001R\u0016\u0010¡\u0001\u001a\u00020\u0010X\u0096\u0004¢\u0006\t\n\u0000\u001a\u0005\b¢\u0001\u0010\u0012R\u0016\u0010£\u0001\u001a\u00020\u0010X\u0096\u0004¢\u0006\t\n\u0000\u001a\u0005\b¤\u0001\u0010\u0012R\u0016\u0010¥\u0001\u001a\u00020\u0010X\u0096\u0004¢\u0006\t\n\u0000\u001a\u0005\b¦\u0001\u0010\u0012R\u0016\u0010§\u0001\u001a\u00020\u0010X\u0096\u0004¢\u0006\t\n\u0000\u001a\u0005\b¨\u0001\u0010\u0012R\u0016\u0010©\u0001\u001a\u00020\u0010X\u0096\u0004¢\u0006\t\n\u0000\u001a\u0005\bª\u0001\u0010\u0012R\u0016\u0010«\u0001\u001a\u00020\u0010X\u0096\u0004¢\u0006\t\n\u0000\u001a\u0005\b¬\u0001\u0010\u0012R\u0016\u0010\u00ad\u0001\u001a\u00020\u0010X\u0096\u0004¢\u0006\t\n\u0000\u001a\u0005\b®\u0001\u0010\u0012R\u0016\u0010¯\u0001\u001a\u00020\u0010X\u0096\u0004¢\u0006\t\n\u0000\u001a\u0005\b°\u0001\u0010\u0012R\u0016\u0010±\u0001\u001a\u00020\u0010X\u0096\u0004¢\u0006\t\n\u0000\u001a\u0005\b²\u0001\u0010\u0012R\u0016\u0010³\u0001\u001a\u00020\u0010X\u0096\u0004¢\u0006\t\n\u0000\u001a\u0005\b´\u0001\u0010\u0012R\u0016\u0010µ\u0001\u001a\u00020\u0010X\u0096\u0004¢\u0006\t\n\u0000\u001a\u0005\b¶\u0001\u0010\u0012R\u0016\u0010·\u0001\u001a\u00020\u0010X\u0096\u0004¢\u0006\t\n\u0000\u001a\u0005\b¸\u0001\u0010\u0012R\u0016\u0010¹\u0001\u001a\u00020\u0010X\u0096\u0004¢\u0006\t\n\u0000\u001a\u0005\bº\u0001\u0010\u0012R\u0010\u0010»\u0001\u001a\u00030¼\u0001X\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010½\u0001\u001a\u00030\u0096\u0001X\u0082\u0004¢\u0006\u0002\n\u0000R\u0018\u0010¾\u0001\u001a\u00030¿\u0001X\u0096\u0004¢\u0006\n\n\u0000\u001a\u0006\bÀ\u0001\u0010Á\u0001R#\u0010Â\u0001\u001a\u000e\u0012\u0004\u0012\u00020~\u0012\u0004\u0012\u00020\f0}X\u0096\u0004¢\u0006\n\n\u0000\u001a\u0006\bÃ\u0001\u0010\u0080\u0001R#\u0010Ä\u0001\u001a\u000e\u0012\u0004\u0012\u00020~\u0012\u0004\u0012\u00020\f0}X\u0096\u0004¢\u0006\n\n\u0000\u001a\u0006\bÅ\u0001\u0010\u0080\u0001R\u0016\u0010Æ\u0001\u001a\u00020\fX\u0096\u0004¢\u0006\t\n\u0000\u001a\u0005\bÇ\u0001\u0010\u000eR\u0016\u0010È\u0001\u001a\u00020\u0010X\u0096\u0004¢\u0006\t\n\u0000\u001a\u0005\bÉ\u0001\u0010\u0012R\u0016\u0010Ê\u0001\u001a\u00020\u0010X\u0096\u0004¢\u0006\t\n\u0000\u001a\u0005\bË\u0001\u0010\u0012R\u0013\u0010Ì\u0001\u001a\u00020\u0018¢\u0006\t\n\u0000\u001a\u0005\bÍ\u0001\u0010\u001aR\u0016\u0010Î\u0001\u001a\u00020\u0010X\u0096\u0004¢\u0006\t\n\u0000\u001a\u0005\bÏ\u0001\u0010\u0012R\u0016\u0010Ð\u0001\u001a\u00020\u0010X\u0096\u0004¢\u0006\t\n\u0000\u001a\u0005\bÑ\u0001\u0010\u0012R\u0016\u0010Ò\u0001\u001a\u00020\u0014X\u0096\u0004¢\u0006\t\n\u0000\u001a\u0005\bÓ\u0001\u0010\u0016R\u0016\u0010Ô\u0001\u001a\u00020\u0010X\u0096\u0004¢\u0006\t\n\u0000\u001a\u0005\bÕ\u0001\u0010\u0012R\u0016\u0010Ö\u0001\u001a\u00020\u0010X\u0096\u0004¢\u0006\t\n\u0000\u001a\u0005\b×\u0001\u0010\u0012R\u0016\u0010Ø\u0001\u001a\u00020\fX\u0096\u0004¢\u0006\t\n\u0000\u001a\u0005\bÙ\u0001\u0010\u000eR\u0016\u0010Ú\u0001\u001a\u00020\fX\u0096\u0004¢\u0006\t\n\u0000\u001a\u0005\bÛ\u0001\u0010\u000eR\u0016\u0010Ü\u0001\u001a\u00020\u0010X\u0096\u0004¢\u0006\t\n\u0000\u001a\u0005\bÝ\u0001\u0010\u0012R\u0016\u0010Þ\u0001\u001a\u00020\u0010X\u0096\u0004¢\u0006\t\n\u0000\u001a\u0005\bß\u0001\u0010\u0012R\u0016\u0010à\u0001\u001a\u00020\u0010X\u0096\u0004¢\u0006\t\n\u0000\u001a\u0005\bá\u0001\u0010\u0012R\u0016\u0010â\u0001\u001a\u00020\u0010X\u0096\u0004¢\u0006\t\n\u0000\u001a\u0005\bã\u0001\u0010\u0012R\u0016\u0010ä\u0001\u001a\u00020\u0010X\u0096\u0004¢\u0006\t\n\u0000\u001a\u0005\bå\u0001\u0010\u0012R\u0016\u0010æ\u0001\u001a\u00020\u0010X\u0096\u0004¢\u0006\t\n\u0000\u001a\u0005\bç\u0001\u0010\u0012R\u0016\u0010è\u0001\u001a\u00020\u0010X\u0096\u0004¢\u0006\t\n\u0000\u001a\u0005\bé\u0001\u0010\u0012R\u0016\u0010ê\u0001\u001a\u00020\u0010X\u0096\u0004¢\u0006\t\n\u0000\u001a\u0005\bë\u0001\u0010\u0012R\u0016\u0010ì\u0001\u001a\u00020\fX\u0096\u0004¢\u0006\t\n\u0000\u001a\u0005\bí\u0001\u0010\u000eR\u0013\u0010î\u0001\u001a\u00020\u0018¢\u0006\t\n\u0000\u001a\u0005\bï\u0001\u0010\u001aR\u0016\u0010ð\u0001\u001a\u00020\u0010X\u0096\u0004¢\u0006\t\n\u0000\u001a\u0005\bñ\u0001\u0010\u0012R\u0016\u0010ò\u0001\u001a\u00020\u0014X\u0096\u0004¢\u0006\t\n\u0000\u001a\u0005\bó\u0001\u0010\u0016R\u0016\u0010ô\u0001\u001a\u00020\u0014X\u0096\u0004¢\u0006\t\n\u0000\u001a\u0005\bõ\u0001\u0010\u0016R\u0013\u0010ö\u0001\u001a\u00020\u0018¢\u0006\t\n\u0000\u001a\u0005\b÷\u0001\u0010\u001aR\u0016\u0010ø\u0001\u001a\u00020\u0010X\u0096\u0004¢\u0006\t\n\u0000\u001a\u0005\bù\u0001\u0010\u0012R\u0016\u0010ú\u0001\u001a\u00020\u0014X\u0096\u0004¢\u0006\t\n\u0000\u001a\u0005\bû\u0001\u0010\u0016R\u0018\u0010ü\u0001\u001a\u00030¼\u0001X\u0096\u0004¢\u0006\n\n\u0000\u001a\u0006\bý\u0001\u0010þ\u0001R\u0016\u0010ÿ\u0001\u001a\u00020\fX\u0096\u0004¢\u0006\t\n\u0000\u001a\u0005\b\u0080\u0002\u0010\u000eR\u0010\u0010\u0081\u0002\u001a\u00030\u0082\u0002X\u0082\u0004¢\u0006\u0002\n\u0000R%\u0010\u0083\u0002\u001a\u0010\u0012\u0004\u0012\u00020\u0010\u0012\u0006\u0012\u0004\u0018\u00010\u00140}X\u0096\u0004¢\u0006\n\n\u0000\u001a\u0006\b\u0084\u0002\u0010\u0080\u0001R%\u0010\u0085\u0002\u001a\u0010\u0012\u0006\u0012\u0004\u0018\u00010\u0014\u0012\u0004\u0012\u00020\u00100}X\u0096\u0004¢\u0006\n\n\u0000\u001a\u0006\b\u0086\u0002\u0010\u0080\u0001R$\u0010\u0087\u0002\u001a\u000f\u0012\u0004\u0012\u00020\u0010\u0012\u0005\u0012\u00030\u0088\u00020}X\u0096\u0004¢\u0006\n\n\u0000\u001a\u0006\b\u0089\u0002\u0010\u0080\u0001R\u001e\u0010\u008a\u0002\u001a\t\u0012\u0004\u0012\u00020\u00140\u008b\u0002X\u0096\u0004¢\u0006\n\n\u0000\u001a\u0006\b\u008c\u0002\u0010\u008d\u0002R\u001e\u0010\u008e\u0002\u001a\t\u0012\u0004\u0012\u00020\u00140\u008b\u0002X\u0096\u0004¢\u0006\n\n\u0000\u001a\u0006\b\u008f\u0002\u0010\u008d\u0002R\u001e\u0010\u0090\u0002\u001a\t\u0012\u0004\u0012\u00020\u00140\u008b\u0002X\u0096\u0004¢\u0006\n\n\u0000\u001a\u0006\b\u0091\u0002\u0010\u008d\u0002R$\u0010\u0092\u0002\u001a\u000f\u0012\u0005\u0012\u00030\u0088\u0002\u0012\u0004\u0012\u00020\u00140}X\u0096\u0004¢\u0006\n\n\u0000\u001a\u0006\b\u0093\u0002\u0010\u0080\u0001R\u001b\u0010\u0094\u0002\u001a\t\u0012\u0004\u0012\u00020\u00180\u008b\u0002¢\u0006\n\n\u0000\u001a\u0006\b\u0095\u0002\u0010\u008d\u0002R$\u0010\u0096\u0002\u001a\u000f\u0012\u0005\u0012\u00030\u0088\u0002\u0012\u0004\u0012\u00020\u00100}X\u0096\u0004¢\u0006\n\n\u0000\u001a\u0006\b\u0097\u0002\u0010\u0080\u0001R\u0016\u0010\u0098\u0002\u001a\u00020\u0010X\u0096\u0004¢\u0006\t\n\u0000\u001a\u0005\b\u0099\u0002\u0010\u0012R\u0013\u0010\u009a\u0002\u001a\u00020\u0018¢\u0006\t\n\u0000\u001a\u0005\b\u009b\u0002\u0010\u001aR\u0016\u0010\u009c\u0002\u001a\u00020\u0010X\u0096\u0004¢\u0006\t\n\u0000\u001a\u0005\b\u009d\u0002\u0010\u0012R\u0016\u0010\u009e\u0002\u001a\u00020\u0010X\u0096\u0004¢\u0006\t\n\u0000\u001a\u0005\b\u009f\u0002\u0010\u0012R\u0016\u0010 \u0002\u001a\u00020\u0014X\u0096\u0004¢\u0006\t\n\u0000\u001a\u0005\b¡\u0002\u0010\u0016R\u0013\u0010¢\u0002\u001a\u00020\u0018¢\u0006\t\n\u0000\u001a\u0005\b£\u0002\u0010\u001aR\u0016\u0010¤\u0002\u001a\u00020\u0010X\u0096\u0004¢\u0006\t\n\u0000\u001a\u0005\b¥\u0002\u0010\u0012R\u0016\u0010¦\u0002\u001a\u00020\u0014X\u0096\u0004¢\u0006\t\n\u0000\u001a\u0005\b§\u0002\u0010\u0016R\u0013\u0010\u0006\u001a\u00020\u0007¢\u0006\n\n\u0000\u001a\u0006\b¨\u0002\u0010©\u0002R\u0016\u0010ª\u0002\u001a\u00020\fX\u0096\u0004¢\u0006\t\n\u0000\u001a\u0005\b«\u0002\u0010\u000eR\u0016\u0010¬\u0002\u001a\u00020\fX\u0096\u0004¢\u0006\t\n\u0000\u001a\u0005\b\u00ad\u0002\u0010\u000eR\u0016\u0010®\u0002\u001a\u00020\u0010X\u0096\u0004¢\u0006\t\n\u0000\u001a\u0005\b¯\u0002\u0010\u0012R\u0016\u0010°\u0002\u001a\u00020\u0014X\u0096\u0004¢\u0006\t\n\u0000\u001a\u0005\b±\u0002\u0010\u0016R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R(\u0010²\u0002\u001a\u001b\u0012\u0011\u0012\u000f\u0012\u0004\u0012\u00020*\u0012\u0004\u0012\u00020\u00140³\u0002\u0012\u0004\u0012\u00020\f0(X\u0082\u0004¢\u0006\u0002\n\u0000R\u0013\u0010´\u0002\u001a\u00020\u0018¢\u0006\t\n\u0000\u001a\u0005\bµ\u0002\u0010\u001aR\u0016\u0010¶\u0002\u001a\u00020\u0010X\u0096\u0004¢\u0006\t\n\u0000\u001a\u0005\b·\u0002\u0010\u0012R\u0016\u0010¸\u0002\u001a\u00020\u0014X\u0096\u0004¢\u0006\t\n\u0000\u001a\u0005\b¹\u0002\u0010\u0016R.\u0010º\u0002\u001a\u0010\u0012\u0004\u0012\u00020\u0010\u0012\u0006\u0012\u0004\u0018\u00010\u00140}8VX\u0096\u0084\u0002¢\u0006\u0010\n\u0006\b¼\u0002\u0010½\u0002\u001a\u0006\b»\u0002\u0010\u0080\u0001R$\u0010¾\u0002\u001a\u000f\u0012\u0005\u0012\u00030¿\u0002\u0012\u0004\u0012\u00020\u00100}X\u0096\u0004¢\u0006\n\n\u0000\u001a\u0006\bÀ\u0002\u0010\u0080\u0001¨\u0006ù\u0002"}, d2 = {"Lorg/jetbrains/kotlin/psi2ir/descriptors/IrBuiltInsOverDescriptors;", "Lorg/jetbrains/kotlin/ir/IrBuiltIns;", "builtIns", "Lorg/jetbrains/kotlin/builtins/KotlinBuiltIns;", "typeTranslator", "Lorg/jetbrains/kotlin/ir/util/TypeTranslator;", "symbolTable", "Lorg/jetbrains/kotlin/ir/util/SymbolTable;", "(Lorg/jetbrains/kotlin/builtins/KotlinBuiltIns;Lorg/jetbrains/kotlin/ir/util/TypeTranslator;Lorg/jetbrains/kotlin/ir/util/SymbolTable;)V", "_functionFactory", "Lorg/jetbrains/kotlin/ir/descriptors/IrAbstractDescriptorBasedFunctionFactory;", "andandSymbol", "Lorg/jetbrains/kotlin/ir/symbols/IrSimpleFunctionSymbol;", "getAndandSymbol", "()Lorg/jetbrains/kotlin/ir/symbols/IrSimpleFunctionSymbol;", "annotationClass", "Lorg/jetbrains/kotlin/ir/symbols/IrClassSymbol;", "getAnnotationClass", "()Lorg/jetbrains/kotlin/ir/symbols/IrClassSymbol;", "annotationType", "Lorg/jetbrains/kotlin/ir/types/IrType;", "getAnnotationType", "()Lorg/jetbrains/kotlin/ir/types/IrType;", "any", "Lorg/jetbrains/kotlin/types/SimpleType;", "getAny", "()Lorg/jetbrains/kotlin/types/SimpleType;", "anyClass", "getAnyClass", "anyNType", "getAnyNType", SchemaSymbols.ATTVAL_ANYTYPE, "getAnyType", "arrayClass", "getArrayClass", VarargLoweringKt.ARRAY_OF_NAME, "getArrayOf", "arrayOfNulls", "getArrayOfNulls", "binaryOperatorCache", "", "Lkotlin/Triple;", "Lorg/jetbrains/kotlin/name/Name;", "bool", "getBool", "booleanArray", "getBooleanArray", "booleanClass", "getBooleanClass", "booleanNot", "Lorg/jetbrains/kotlin/descriptors/SimpleFunctionDescriptor;", "Lkotlin/jvm/JvmWildcard;", "getBooleanNot", "()Lorg/jetbrains/kotlin/descriptors/SimpleFunctionDescriptor;", "booleanNotSymbol", "getBooleanNotSymbol", "booleanType", "getBooleanType", "getBuiltIns", "()Lorg/jetbrains/kotlin/builtins/KotlinBuiltIns;", "builtInsModule", "Lorg/jetbrains/kotlin/descriptors/impl/ModuleDescriptorImpl;", "byte", "getByte", "byteArray", "getByteArray", "byteClass", "getByteClass", "byteType", "getByteType", PsiKeyword.CHAR, "getChar", "charArray", "getCharArray", "charClass", "getCharClass", "charSequenceClass", "getCharSequenceClass", "charType", "getCharType", "checkNotNullSymbol", "getCheckNotNullSymbol", "collectionClass", "getCollectionClass", "comparableClass", "getComparableClass", "dataClassArrayMemberHashCodeSymbol", "getDataClassArrayMemberHashCodeSymbol", "dataClassArrayMemberToStringSymbol", "getDataClassArrayMemberToStringSymbol", "double", "getDouble", "doubleArray", "getDoubleArray", "doubleClass", "getDoubleClass", "doubleType", "getDoubleType", "enumClass", "getEnumClass", "eqeqSymbol", "getEqeqSymbol", "eqeqeqSymbol", "getEqeqeqSymbol", "extensionStringPlus", "getExtensionStringPlus", "extensionToString", "getExtensionToString", "float", "getFloat", "floatArray", "getFloatArray", "floatClass", "getFloatClass", "floatType", "getFloatType", "functionClass", "getFunctionClass", "value", "functionFactory", "getFunctionFactory", "()Lorg/jetbrains/kotlin/ir/descriptors/IrAbstractDescriptorBasedFunctionFactory;", "setFunctionFactory", "(Lorg/jetbrains/kotlin/ir/descriptors/IrAbstractDescriptorBasedFunctionFactory;)V", "greaterFunByOperandType", "", "Lorg/jetbrains/kotlin/ir/symbols/IrClassifierSymbol;", "getGreaterFunByOperandType", "()Ljava/util/Map;", "greaterOrEqualFunByOperandType", "getGreaterOrEqualFunByOperandType", "ieee754equalsFunByOperandType", "getIeee754equalsFunByOperandType", "illegalArgumentExceptionSymbol", "getIllegalArgumentExceptionSymbol", "int", "getInt", "intArray", "getIntArray", "intClass", "getIntClass", "intPlusSymbol", "getIntPlusSymbol", "intTimesSymbol", "getIntTimesSymbol", "intType", "getIntType", "intXorSymbol", "getIntXorSymbol", "intrinsicConstAnnotationFqName", "Lorg/jetbrains/kotlin/name/FqName;", "intrinsicConstClass", "Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "intrinsicConstConstructor", "Lorg/jetbrains/kotlin/ir/declarations/IrConstructor;", "intrinsicConstType", "Lorg/jetbrains/kotlin/ir/types/IrSimpleType;", "irFactory", "Lorg/jetbrains/kotlin/ir/declarations/IrFactory;", "getIrFactory", "()Lorg/jetbrains/kotlin/ir/declarations/IrFactory;", "iterableClass", "getIterableClass", "iteratorClass", "getIteratorClass", "kCallableClass", "getKCallableClass", "kClassClass", "getKClassClass", "kFunctionClass", "getKFunctionClass", "kMutableProperty0Class", "getKMutableProperty0Class", "kMutableProperty1Class", "getKMutableProperty1Class", "kMutableProperty2Class", "getKMutableProperty2Class", "kProperty0Class", "getKProperty0Class", "kProperty1Class", "getKProperty1Class", "kProperty2Class", "getKProperty2Class", "kPropertyClass", "getKPropertyClass", "kTypeClass", "getKTypeClass", "kotlinInternalIrPackage", "Lorg/jetbrains/kotlin/ir/declarations/IrExternalPackageFragment;", "kotlinInternalPackage", "languageVersionSettings", "Lorg/jetbrains/kotlin/config/LanguageVersionSettings;", "getLanguageVersionSettings", "()Lorg/jetbrains/kotlin/config/LanguageVersionSettings;", "lessFunByOperandType", "getLessFunByOperandType", "lessOrEqualFunByOperandType", "getLessOrEqualFunByOperandType", "linkageErrorSymbol", "getLinkageErrorSymbol", "listClass", "getListClass", "listIteratorClass", "getListIteratorClass", "long", "getLong", "longArray", "getLongArray", "longClass", "getLongClass", "longType", "getLongType", "mapClass", "getMapClass", "mapEntryClass", "getMapEntryClass", "memberStringPlus", "getMemberStringPlus", "memberToString", "getMemberToString", "mutableCollectionClass", "getMutableCollectionClass", "mutableIterableClass", "getMutableIterableClass", "mutableIteratorClass", "getMutableIteratorClass", "mutableListClass", "getMutableListClass", "mutableListIteratorClass", "getMutableListIteratorClass", "mutableMapClass", "getMutableMapClass", "mutableMapEntryClass", "getMutableMapEntryClass", "mutableSetClass", "getMutableSetClass", "noWhenBranchMatchedExceptionSymbol", "getNoWhenBranchMatchedExceptionSymbol", "nothing", "getNothing", "nothingClass", "getNothingClass", "nothingNType", "getNothingNType", "nothingType", "getNothingType", "number", "getNumber", "numberClass", "getNumberClass", "numberType", "getNumberType", "operatorsPackageFragment", "getOperatorsPackageFragment", "()Lorg/jetbrains/kotlin/ir/declarations/IrExternalPackageFragment;", "ororSymbol", "getOrorSymbol", "packageFragmentDescriptor", "Lorg/jetbrains/kotlin/ir/descriptors/IrBuiltinsPackageFragmentDescriptorImpl;", "primitiveArrayElementTypes", "getPrimitiveArrayElementTypes", "primitiveArrayForType", "getPrimitiveArrayForType", "primitiveArraysToPrimitiveTypes", "Lorg/jetbrains/kotlin/builtins/PrimitiveType;", "getPrimitiveArraysToPrimitiveTypes", "primitiveFloatingPointIrTypes", "", "getPrimitiveFloatingPointIrTypes", "()Ljava/util/List;", "primitiveIrTypes", "getPrimitiveIrTypes", "primitiveIrTypesWithComparisons", "getPrimitiveIrTypesWithComparisons", "primitiveTypeToIrType", "getPrimitiveTypeToIrType", "primitiveTypes", "getPrimitiveTypes", "primitiveTypesToPrimitiveArrays", "getPrimitiveTypesToPrimitiveArrays", "setClass", "getSetClass", "short", "getShort", "shortArray", "getShortArray", "shortClass", "getShortClass", "shortType", "getShortType", "string", "getString", "stringClass", "getStringClass", "stringType", "getStringType", "getSymbolTable", "()Lorg/jetbrains/kotlin/ir/util/SymbolTable;", "throwCceSymbol", "getThrowCceSymbol", "throwIseSymbol", "getThrowIseSymbol", "throwableClass", "getThrowableClass", "throwableType", "getThrowableType", "unaryOperatorCache", "Lkotlin/Pair;", "unit", "getUnit", "unitClass", "getUnitClass", "unitType", "getUnitType", "unsignedArraysElementTypes", "getUnsignedArraysElementTypes", "unsignedArraysElementTypes$delegate", "Lkotlin/Lazy;", "unsignedTypesToUnsignedArrays", "Lorg/jetbrains/kotlin/builtins/UnsignedType;", "getUnsignedTypesToUnsignedArrays", "builtInsPackage", "Lorg/jetbrains/kotlin/resolve/scopes/MemberScope;", "packageNameSegments", "", "", "([Ljava/lang/String;)Lorg/jetbrains/kotlin/resolve/scopes/MemberScope;", "defineCheckNotNullOperator", "defineComparisonOperator", "name", "operandType", "defineOperator", "returnType", "valueParameterTypes", "isIntrinsicConst", "", "findBuiltInClassMemberFunctions", "", "builtInClass", "findClass", "(Lorg/jetbrains/kotlin/name/Name;[Ljava/lang/String;)Lorg/jetbrains/kotlin/ir/symbols/IrClassSymbol;", "packageFqName", "findClassDescriptor", "Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;", "findFunctions", "(Lorg/jetbrains/kotlin/name/Name;[Ljava/lang/String;)Ljava/lang/Iterable;", "findProperties", "Lorg/jetbrains/kotlin/ir/symbols/IrPropertySymbol;", "functionN", "arity", "", "getBinaryOperator", "lhsType", "rhsType", "getFunctionsByKey", ExifInterface.GPS_DIRECTION_TRUE, "", "makeKey", "Lkotlin/Function1;", "(Lorg/jetbrains/kotlin/name/Name;[Ljava/lang/String;Lkotlin/jvm/functions/Function1;)Ljava/util/Map;", "mutable", "n", "getNonBuiltInFunctionsByExtensionReceiver", "(Lorg/jetbrains/kotlin/name/Name;[Ljava/lang/String;)Ljava/util/Map;", "getNonBuiltinFunctionsByReturnType", "getUnaryOperator", "receiverType", "kFunctionN", "kSuspendFunctionN", "suspendFunctionN", "defineComparisonOperatorForEachIrType", "makeNonNullType", "Lorg/jetbrains/kotlin/types/TypeConstructor;", "makeNullableType", "toIrSymbol", "toIrType", "Lorg/jetbrains/kotlin/types/KotlinType;", "ir.psi2ir"}, k = 1, mv = {1, 9, 0}, xi = 48)
@ObsoleteDescriptorBasedAPI
public final class IrBuiltInsOverDescriptors extends IrBuiltIns {
    private IrAbstractDescriptorBasedFunctionFactory _functionFactory;
    private final IrSimpleFunctionSymbol andandSymbol;
    private final IrClassSymbol annotationClass;
    private final IrType annotationType;
    private final SimpleType any;
    private final IrClassSymbol anyClass;
    private final IrType anyNType;
    private final IrType anyType;
    private final IrClassSymbol arrayClass;
    private final IrSimpleFunctionSymbol arrayOf;
    private final IrSimpleFunctionSymbol arrayOfNulls;
    private final Map<Triple<Name, IrType, IrType>, IrSimpleFunctionSymbol> binaryOperatorCache;
    private final SimpleType bool;
    private final IrClassSymbol booleanArray;
    private final IrClassSymbol booleanClass;
    private final SimpleFunctionDescriptor booleanNot;
    private final IrSimpleFunctionSymbol booleanNotSymbol;
    private final IrType booleanType;
    private final KotlinBuiltIns builtIns;
    private final ModuleDescriptorImpl builtInsModule;
    private final SimpleType byte;
    private final IrClassSymbol byteArray;
    private final IrClassSymbol byteClass;
    private final IrType byteType;
    private final SimpleType char;
    private final IrClassSymbol charArray;
    private final IrClassSymbol charClass;
    private final IrClassSymbol charSequenceClass;
    private final IrType charType;
    private final IrSimpleFunctionSymbol checkNotNullSymbol;
    private final IrClassSymbol collectionClass;
    private final IrClassSymbol comparableClass;
    private final IrSimpleFunctionSymbol dataClassArrayMemberHashCodeSymbol;
    private final IrSimpleFunctionSymbol dataClassArrayMemberToStringSymbol;
    private final SimpleType double;
    private final IrClassSymbol doubleArray;
    private final IrClassSymbol doubleClass;
    private final IrType doubleType;
    private final IrClassSymbol enumClass;
    private final IrSimpleFunctionSymbol eqeqSymbol;
    private final IrSimpleFunctionSymbol eqeqeqSymbol;
    private final IrSimpleFunctionSymbol extensionStringPlus;
    private final IrSimpleFunctionSymbol extensionToString;
    private final SimpleType float;
    private final IrClassSymbol floatArray;
    private final IrClassSymbol floatClass;
    private final IrType floatType;
    private final IrClassSymbol functionClass;
    private final Map<IrClassifierSymbol, IrSimpleFunctionSymbol> greaterFunByOperandType;
    private final Map<IrClassifierSymbol, IrSimpleFunctionSymbol> greaterOrEqualFunByOperandType;
    private final Map<IrClassifierSymbol, IrSimpleFunctionSymbol> ieee754equalsFunByOperandType;
    private final IrSimpleFunctionSymbol illegalArgumentExceptionSymbol;
    private final SimpleType int;
    private final IrClassSymbol intArray;
    private final IrClassSymbol intClass;
    private final IrSimpleFunctionSymbol intPlusSymbol;
    private final IrSimpleFunctionSymbol intTimesSymbol;
    private final IrType intType;
    private final IrSimpleFunctionSymbol intXorSymbol;
    private final FqName intrinsicConstAnnotationFqName;
    private final IrClass intrinsicConstClass;
    private final IrConstructor intrinsicConstConstructor;
    private final IrSimpleType intrinsicConstType;
    private final IrFactory irFactory;
    private final IrClassSymbol iterableClass;
    private final IrClassSymbol iteratorClass;
    private final IrClassSymbol kCallableClass;
    private final IrClassSymbol kClassClass;
    private final IrClassSymbol kFunctionClass;
    private final IrClassSymbol kMutableProperty0Class;
    private final IrClassSymbol kMutableProperty1Class;
    private final IrClassSymbol kMutableProperty2Class;
    private final IrClassSymbol kProperty0Class;
    private final IrClassSymbol kProperty1Class;
    private final IrClassSymbol kProperty2Class;
    private final IrClassSymbol kPropertyClass;
    private final IrClassSymbol kTypeClass;
    private final IrExternalPackageFragment kotlinInternalIrPackage;
    private final FqName kotlinInternalPackage;
    private final LanguageVersionSettings languageVersionSettings;
    private final Map<IrClassifierSymbol, IrSimpleFunctionSymbol> lessFunByOperandType;
    private final Map<IrClassifierSymbol, IrSimpleFunctionSymbol> lessOrEqualFunByOperandType;
    private final IrSimpleFunctionSymbol linkageErrorSymbol;
    private final IrClassSymbol listClass;
    private final IrClassSymbol listIteratorClass;
    private final SimpleType long;
    private final IrClassSymbol longArray;
    private final IrClassSymbol longClass;
    private final IrType longType;
    private final IrClassSymbol mapClass;
    private final IrClassSymbol mapEntryClass;
    private final IrSimpleFunctionSymbol memberStringPlus;
    private final IrSimpleFunctionSymbol memberToString;
    private final IrClassSymbol mutableCollectionClass;
    private final IrClassSymbol mutableIterableClass;
    private final IrClassSymbol mutableIteratorClass;
    private final IrClassSymbol mutableListClass;
    private final IrClassSymbol mutableListIteratorClass;
    private final IrClassSymbol mutableMapClass;
    private final IrClassSymbol mutableMapEntryClass;
    private final IrClassSymbol mutableSetClass;
    private final IrSimpleFunctionSymbol noWhenBranchMatchedExceptionSymbol;
    private final SimpleType nothing;
    private final IrClassSymbol nothingClass;
    private final IrType nothingNType;
    private final IrType nothingType;
    private final SimpleType number;
    private final IrClassSymbol numberClass;
    private final IrType numberType;
    private final IrExternalPackageFragment operatorsPackageFragment;
    private final IrSimpleFunctionSymbol ororSymbol;
    private final IrBuiltinsPackageFragmentDescriptorImpl packageFragmentDescriptor;
    private final Map<IrClassSymbol, IrType> primitiveArrayElementTypes;
    private final Map<IrType, IrClassSymbol> primitiveArrayForType;
    private final Map<IrClassSymbol, PrimitiveType> primitiveArraysToPrimitiveTypes;
    private final List<IrType> primitiveFloatingPointIrTypes;
    private final List<IrType> primitiveIrTypes;
    private final List<IrType> primitiveIrTypesWithComparisons;
    private final Map<PrimitiveType, IrType> primitiveTypeToIrType;
    private final List<SimpleType> primitiveTypes;
    private final Map<PrimitiveType, IrClassSymbol> primitiveTypesToPrimitiveArrays;
    private final IrClassSymbol setClass;
    private final SimpleType short;
    private final IrClassSymbol shortArray;
    private final IrClassSymbol shortClass;
    private final IrType shortType;
    private final SimpleType string;
    private final IrClassSymbol stringClass;
    private final IrType stringType;
    private final SymbolTable symbolTable;
    private final IrSimpleFunctionSymbol throwCceSymbol;
    private final IrSimpleFunctionSymbol throwIseSymbol;
    private final IrClassSymbol throwableClass;
    private final IrType throwableType;
    private final TypeTranslator typeTranslator;
    private final Map<Pair<Name, IrType>, IrSimpleFunctionSymbol> unaryOperatorCache;
    private final SimpleType unit;
    private final IrClassSymbol unitClass;
    private final IrType unitType;

    /* JADX INFO: renamed from: unsignedArraysElementTypes$delegate, reason: from kotlin metadata */
    private final Lazy unsignedArraysElementTypes;
    private final Map<UnsignedType, IrClassSymbol> unsignedTypesToUnsignedArrays;

    public final KotlinBuiltIns getBuiltIns() {
        return this.builtIns;
    }

    public final SymbolTable getSymbolTable() {
        return this.symbolTable;
    }

    public IrBuiltInsOverDescriptors(KotlinBuiltIns builtIns, TypeTranslator typeTranslator, SymbolTable symbolTable) {
        Object obj;
        Intrinsics.checkNotNullParameter(builtIns, "builtIns");
        Intrinsics.checkNotNullParameter(typeTranslator, "typeTranslator");
        Intrinsics.checkNotNullParameter(symbolTable, "symbolTable");
        this.builtIns = builtIns;
        this.typeTranslator = typeTranslator;
        this.symbolTable = symbolTable;
        this.languageVersionSettings = typeTranslator.getLanguageVersionSettings();
        this.irFactory = symbolTable.getIrFactory();
        ModuleDescriptorImpl builtInsModule = builtIns.getBuiltInsModule();
        Intrinsics.checkNotNullExpressionValue(builtInsModule, "getBuiltInsModule(...)");
        this.builtInsModule = builtInsModule;
        FqName base_internal_package = StandardClassIds.INSTANCE.getBASE_INTERNAL_PACKAGE();
        this.kotlinInternalPackage = base_internal_package;
        IrExternalPackageFragment irExternalPackageFragmentCreateEmptyExternalPackageFragment = IrExternalPackageFragmentImpl.INSTANCE.createEmptyExternalPackageFragment(builtInsModule, base_internal_package);
        this.kotlinInternalIrPackage = irExternalPackageFragmentCreateEmptyExternalPackageFragment;
        IrBuiltinsPackageFragmentDescriptorImpl irBuiltinsPackageFragmentDescriptorImpl = new IrBuiltinsPackageFragmentDescriptorImpl(builtInsModule, IrBuiltIns.INSTANCE.getKOTLIN_INTERNAL_IR_FQN());
        this.packageFragmentDescriptor = irBuiltinsPackageFragmentDescriptorImpl;
        this.operatorsPackageFragment = symbolTable.getDescriptorExtension().declareExternalPackageFragmentIfNotExists(irBuiltinsPackageFragmentDescriptorImpl);
        SimpleType anyType = builtIns.getAnyType();
        Intrinsics.checkNotNullExpressionValue(anyType, "getAnyType(...)");
        this.any = anyType;
        this.anyType = toIrType(anyType);
        ClassDescriptor any = builtIns.getAny();
        Intrinsics.checkNotNullExpressionValue(any, "getAny(...)");
        this.anyClass = toIrSymbol(any);
        this.anyNType = IrTypesKt.makeNullable(getAnyType());
        FqName fqNameChild = base_internal_package.child(Name.identifier("IntrinsicConstEvaluation"));
        Intrinsics.checkNotNullExpressionValue(fqNameChild, "child(...)");
        this.intrinsicConstAnnotationFqName = fqNameChild;
        IrFactory irFactory = getIrFactory();
        IrClassBuilder irClassBuilder = new IrClassBuilder();
        Name nameShortName = fqNameChild.shortName();
        Intrinsics.checkNotNullExpressionValue(nameShortName, "shortName(...)");
        irClassBuilder.setName(nameShortName);
        irClassBuilder.setKind(ClassKind.ANNOTATION_CLASS);
        irClassBuilder.setModality(Modality.FINAL);
        Unit unit = Unit.INSTANCE;
        IrClass irClassBuildClass = DeclarationBuildersKt.buildClass(irFactory, irClassBuilder);
        irClassBuildClass.setParent(irExternalPackageFragmentCreateEmptyExternalPackageFragment);
        IrUtilsKt.createImplicitParameterDeclarationWithWrappedDescriptor(irClassBuildClass);
        IrFactory factory = irClassBuildClass.getFactory();
        IrFunctionBuilder irFunctionBuilder = new IrFunctionBuilder();
        irFunctionBuilder.setPrimary(true);
        irFunctionBuilder.setReturnType(IrUtilsKt.getDefaultType(irClassBuildClass));
        IrConstructor irConstructorBuildConstructor = DeclarationBuildersKt.buildConstructor(factory, irFunctionBuilder);
        irClassBuildClass.getDeclarations().add(irConstructorBuildConstructor);
        irConstructorBuildConstructor.setParent(irClassBuildClass);
        IrBuiltInsOverDescriptors irBuiltInsOverDescriptors = this;
        IrUtilsKt.addFakeOverrides$default(irClassBuildClass, new IrTypeSystemContextImpl(irBuiltInsOverDescriptors), null, null, 6, null);
        Unit unit2 = Unit.INSTANCE;
        this.intrinsicConstClass = irClassBuildClass;
        this.intrinsicConstType = IrUtilsKt.getDefaultType(irClassBuildClass);
        IrConstructor primaryConstructor = IrUtilsKt.getPrimaryConstructor(irClassBuildClass);
        Intrinsics.checkNotNull(primaryConstructor, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.declarations.IrConstructor");
        this.intrinsicConstConstructor = primaryConstructor;
        SimpleType booleanType = builtIns.getBooleanType();
        Intrinsics.checkNotNullExpressionValue(booleanType, "getBooleanType(...)");
        this.bool = booleanType;
        this.booleanType = toIrType(booleanType);
        ClassDescriptor classDescriptor = builtIns.getBoolean();
        Intrinsics.checkNotNullExpressionValue(classDescriptor, "getBoolean(...)");
        this.booleanClass = toIrSymbol(classDescriptor);
        SimpleType charType = builtIns.getCharType();
        Intrinsics.checkNotNullExpressionValue(charType, "getCharType(...)");
        this.char = charType;
        this.charType = toIrType(charType);
        ClassDescriptor classDescriptor2 = builtIns.getChar();
        Intrinsics.checkNotNullExpressionValue(classDescriptor2, "getChar(...)");
        this.charClass = toIrSymbol(classDescriptor2);
        SimpleType defaultType = builtIns.getNumber().getDefaultType();
        Intrinsics.checkNotNullExpressionValue(defaultType, "getDefaultType(...)");
        this.number = defaultType;
        this.numberType = toIrType(defaultType);
        ClassDescriptor number = builtIns.getNumber();
        Intrinsics.checkNotNullExpressionValue(number, "getNumber(...)");
        this.numberClass = toIrSymbol(number);
        SimpleType byteType = builtIns.getByteType();
        Intrinsics.checkNotNullExpressionValue(byteType, "getByteType(...)");
        this.byte = byteType;
        this.byteType = toIrType(byteType);
        ClassDescriptor classDescriptor3 = builtIns.getByte();
        Intrinsics.checkNotNullExpressionValue(classDescriptor3, "getByte(...)");
        this.byteClass = toIrSymbol(classDescriptor3);
        SimpleType shortType = builtIns.getShortType();
        Intrinsics.checkNotNullExpressionValue(shortType, "getShortType(...)");
        this.short = shortType;
        this.shortType = toIrType(shortType);
        ClassDescriptor classDescriptor4 = builtIns.getShort();
        Intrinsics.checkNotNullExpressionValue(classDescriptor4, "getShort(...)");
        this.shortClass = toIrSymbol(classDescriptor4);
        SimpleType intType = builtIns.getIntType();
        Intrinsics.checkNotNullExpressionValue(intType, "getIntType(...)");
        this.int = intType;
        this.intType = toIrType(intType);
        ClassDescriptor classDescriptor5 = builtIns.getInt();
        Intrinsics.checkNotNullExpressionValue(classDescriptor5, "getInt(...)");
        this.intClass = toIrSymbol(classDescriptor5);
        SimpleType longType = builtIns.getLongType();
        Intrinsics.checkNotNullExpressionValue(longType, "getLongType(...)");
        this.long = longType;
        this.longType = toIrType(longType);
        ClassDescriptor classDescriptor6 = builtIns.getLong();
        Intrinsics.checkNotNullExpressionValue(classDescriptor6, "getLong(...)");
        this.longClass = toIrSymbol(classDescriptor6);
        SimpleType floatType = builtIns.getFloatType();
        Intrinsics.checkNotNullExpressionValue(floatType, "getFloatType(...)");
        this.float = floatType;
        this.floatType = toIrType(floatType);
        ClassDescriptor classDescriptor7 = builtIns.getFloat();
        Intrinsics.checkNotNullExpressionValue(classDescriptor7, "getFloat(...)");
        this.floatClass = toIrSymbol(classDescriptor7);
        SimpleType doubleType = builtIns.getDoubleType();
        Intrinsics.checkNotNullExpressionValue(doubleType, "getDoubleType(...)");
        this.double = doubleType;
        this.doubleType = toIrType(doubleType);
        ClassDescriptor classDescriptor8 = builtIns.getDouble();
        Intrinsics.checkNotNullExpressionValue(classDescriptor8, "getDouble(...)");
        this.doubleClass = toIrSymbol(classDescriptor8);
        SimpleType nothingType = builtIns.getNothingType();
        Intrinsics.checkNotNullExpressionValue(nothingType, "getNothingType(...)");
        this.nothing = nothingType;
        this.nothingType = toIrType(nothingType);
        ClassDescriptor nothing = builtIns.getNothing();
        Intrinsics.checkNotNullExpressionValue(nothing, "getNothing(...)");
        this.nothingClass = toIrSymbol(nothing);
        this.nothingNType = IrTypesKt.makeNullable(getNothingType());
        SimpleType unitType = builtIns.getUnitType();
        Intrinsics.checkNotNullExpressionValue(unitType, "getUnitType(...)");
        this.unit = unitType;
        this.unitType = toIrType(unitType);
        ClassDescriptor unit3 = builtIns.getUnit();
        Intrinsics.checkNotNullExpressionValue(unit3, "getUnit(...)");
        this.unitClass = toIrSymbol(unit3);
        SimpleType stringType = builtIns.getStringType();
        Intrinsics.checkNotNullExpressionValue(stringType, "getStringType(...)");
        this.string = stringType;
        this.stringType = toIrType(stringType);
        ClassDescriptor string = builtIns.getString();
        Intrinsics.checkNotNullExpressionValue(string, "getString(...)");
        this.stringClass = toIrSymbol(string);
        Name nameIdentifier = Name.identifier("CharSequence");
        Intrinsics.checkNotNullExpressionValue(nameIdentifier, "identifier(...)");
        IrClassSymbol irClassSymbolFindClass = findClass(nameIdentifier, IncrementalJvmCacheKt.KOTLIN_CACHE_DIRECTORY_NAME);
        Intrinsics.checkNotNull(irClassSymbolFindClass);
        this.charSequenceClass = irClassSymbolFindClass;
        ClassDescriptor collection = builtIns.getCollection();
        Intrinsics.checkNotNullExpressionValue(collection, "getCollection(...)");
        this.collectionClass = toIrSymbol(collection);
        ClassDescriptor set = builtIns.getSet();
        Intrinsics.checkNotNullExpressionValue(set, "getSet(...)");
        this.setClass = toIrSymbol(set);
        ClassDescriptor list = builtIns.getList();
        Intrinsics.checkNotNullExpressionValue(list, "getList(...)");
        this.listClass = toIrSymbol(list);
        ClassDescriptor map = builtIns.getMap();
        Intrinsics.checkNotNullExpressionValue(map, "getMap(...)");
        this.mapClass = toIrSymbol(map);
        ClassDescriptor mapEntry = builtIns.getMapEntry();
        Intrinsics.checkNotNullExpressionValue(mapEntry, "getMapEntry(...)");
        this.mapEntryClass = toIrSymbol(mapEntry);
        ClassDescriptor iterable = builtIns.getIterable();
        Intrinsics.checkNotNullExpressionValue(iterable, "getIterable(...)");
        this.iterableClass = toIrSymbol(iterable);
        ClassDescriptor iterator = builtIns.getIterator();
        Intrinsics.checkNotNullExpressionValue(iterator, "getIterator(...)");
        this.iteratorClass = toIrSymbol(iterator);
        ClassDescriptor listIterator = builtIns.getListIterator();
        Intrinsics.checkNotNullExpressionValue(listIterator, "getListIterator(...)");
        this.listIteratorClass = toIrSymbol(listIterator);
        ClassDescriptor mutableCollection = builtIns.getMutableCollection();
        Intrinsics.checkNotNullExpressionValue(mutableCollection, "getMutableCollection(...)");
        this.mutableCollectionClass = toIrSymbol(mutableCollection);
        ClassDescriptor mutableSet = builtIns.getMutableSet();
        Intrinsics.checkNotNullExpressionValue(mutableSet, "getMutableSet(...)");
        this.mutableSetClass = toIrSymbol(mutableSet);
        ClassDescriptor mutableList = builtIns.getMutableList();
        Intrinsics.checkNotNullExpressionValue(mutableList, "getMutableList(...)");
        this.mutableListClass = toIrSymbol(mutableList);
        ClassDescriptor mutableMap = builtIns.getMutableMap();
        Intrinsics.checkNotNullExpressionValue(mutableMap, "getMutableMap(...)");
        this.mutableMapClass = toIrSymbol(mutableMap);
        ClassDescriptor mutableMapEntry = builtIns.getMutableMapEntry();
        Intrinsics.checkNotNullExpressionValue(mutableMapEntry, "getMutableMapEntry(...)");
        this.mutableMapEntryClass = toIrSymbol(mutableMapEntry);
        ClassDescriptor mutableIterable = builtIns.getMutableIterable();
        Intrinsics.checkNotNullExpressionValue(mutableIterable, "getMutableIterable(...)");
        this.mutableIterableClass = toIrSymbol(mutableIterable);
        ClassDescriptor mutableIterator = builtIns.getMutableIterator();
        Intrinsics.checkNotNullExpressionValue(mutableIterator, "getMutableIterator(...)");
        this.mutableIteratorClass = toIrSymbol(mutableIterator);
        ClassDescriptor mutableListIterator = builtIns.getMutableListIterator();
        Intrinsics.checkNotNullExpressionValue(mutableListIterator, "getMutableListIterator(...)");
        this.mutableListIteratorClass = toIrSymbol(mutableListIterator);
        ClassDescriptor comparable = builtIns.getComparable();
        Intrinsics.checkNotNullExpressionValue(comparable, "getComparable(...)");
        this.comparableClass = toIrSymbol(comparable);
        ClassDescriptor array = builtIns.getArray();
        Intrinsics.checkNotNullExpressionValue(array, "getArray(...)");
        this.arrayClass = toIrSymbol(array);
        SimpleType defaultType2 = this.builtIns.getThrowable().getDefaultType();
        Intrinsics.checkNotNullExpressionValue(defaultType2, "getDefaultType(...)");
        this.throwableType = toIrType(defaultType2);
        ClassDescriptor throwable = this.builtIns.getThrowable();
        Intrinsics.checkNotNullExpressionValue(throwable, "getThrowable(...)");
        this.throwableClass = toIrSymbol(throwable);
        ClassDescriptor kCallable = this.builtIns.getKCallable();
        Intrinsics.checkNotNullExpressionValue(kCallable, "getKCallable(...)");
        this.kCallableClass = toIrSymbol(kCallable);
        ClassDescriptor kProperty = this.builtIns.getKProperty();
        Intrinsics.checkNotNullExpressionValue(kProperty, "getKProperty(...)");
        this.kPropertyClass = toIrSymbol(kProperty);
        ClassDescriptor kClass = this.builtIns.getKClass();
        Intrinsics.checkNotNullExpressionValue(kClass, "getKClass(...)");
        this.kClassClass = toIrSymbol(kClass);
        ClassDescriptor kType = this.builtIns.getKType();
        Intrinsics.checkNotNullExpressionValue(kType, "getKType(...)");
        this.kTypeClass = toIrSymbol(kType);
        ClassDescriptor kProperty0 = this.builtIns.getKProperty0();
        Intrinsics.checkNotNullExpressionValue(kProperty0, "getKProperty0(...)");
        this.kProperty0Class = toIrSymbol(kProperty0);
        ClassDescriptor kProperty1 = this.builtIns.getKProperty1();
        Intrinsics.checkNotNullExpressionValue(kProperty1, "getKProperty1(...)");
        this.kProperty1Class = toIrSymbol(kProperty1);
        ClassDescriptor kProperty2 = this.builtIns.getKProperty2();
        Intrinsics.checkNotNullExpressionValue(kProperty2, "getKProperty2(...)");
        this.kProperty2Class = toIrSymbol(kProperty2);
        ClassDescriptor kMutableProperty0 = this.builtIns.getKMutableProperty0();
        Intrinsics.checkNotNullExpressionValue(kMutableProperty0, "getKMutableProperty0(...)");
        this.kMutableProperty0Class = toIrSymbol(kMutableProperty0);
        ClassDescriptor kMutableProperty1 = this.builtIns.getKMutableProperty1();
        Intrinsics.checkNotNullExpressionValue(kMutableProperty1, "getKMutableProperty1(...)");
        this.kMutableProperty1Class = toIrSymbol(kMutableProperty1);
        ClassDescriptor kMutableProperty2 = this.builtIns.getKMutableProperty2();
        Intrinsics.checkNotNullExpressionValue(kMutableProperty2, "getKMutableProperty2(...)");
        this.kMutableProperty2Class = toIrSymbol(kMutableProperty2);
        ClassDescriptor builtInClassByFqName = this.builtIns.getBuiltInClassByFqName(new FqName("kotlin.Function"));
        Intrinsics.checkNotNullExpressionValue(builtInClassByFqName, "getBuiltInClassByFqName(...)");
        this.functionClass = toIrSymbol(builtInClassByFqName);
        ClassDescriptor builtInClassByFqName2 = this.builtIns.getBuiltInClassByFqName(new FqName("kotlin.reflect.KFunction"));
        Intrinsics.checkNotNullExpressionValue(builtInClassByFqName2, "getBuiltInClassByFqName(...)");
        this.kFunctionClass = toIrSymbol(builtInClassByFqName2);
        ClassDescriptor annotation = this.builtIns.getAnnotation();
        Intrinsics.checkNotNullExpressionValue(annotation, "getAnnotation(...)");
        this.annotationClass = toIrSymbol(annotation);
        SimpleType annotationType = this.builtIns.getAnnotationType();
        Intrinsics.checkNotNullExpressionValue(annotationType, "getAnnotationType(...)");
        this.annotationType = toIrType(annotationType);
        this.primitiveTypeToIrType = MapsKt.mapOf(TuplesKt.to(PrimitiveType.BOOLEAN, getBooleanType()), TuplesKt.to(PrimitiveType.CHAR, getCharType()), TuplesKt.to(PrimitiveType.BYTE, getByteType()), TuplesKt.to(PrimitiveType.SHORT, getShortType()), TuplesKt.to(PrimitiveType.INT, getIntType()), TuplesKt.to(PrimitiveType.FLOAT, getFloatType()), TuplesKt.to(PrimitiveType.LONG, getLongType()), TuplesKt.to(PrimitiveType.DOUBLE, getDoubleType()));
        this.primitiveTypes = CollectionsKt.listOf((Object[]) new SimpleType[]{booleanType, charType, byteType, shortType, intType, floatType, longType, doubleType});
        this.primitiveIrTypes = CollectionsKt.listOf((Object[]) new IrType[]{getBooleanType(), getCharType(), getByteType(), getShortType(), getIntType(), getFloatType(), getLongType(), getDoubleType()});
        this.primitiveIrTypesWithComparisons = CollectionsKt.listOf((Object[]) new IrType[]{getCharType(), getByteType(), getShortType(), getIntType(), getFloatType(), getLongType(), getDoubleType()});
        this.primitiveFloatingPointIrTypes = CollectionsKt.listOf((Object[]) new IrType[]{getFloatType(), getDoubleType()});
        ClassDescriptor primitiveArrayClassDescriptor = this.builtIns.getPrimitiveArrayClassDescriptor(PrimitiveType.BYTE);
        Intrinsics.checkNotNullExpressionValue(primitiveArrayClassDescriptor, "getPrimitiveArrayClassDescriptor(...)");
        this.byteArray = toIrSymbol(primitiveArrayClassDescriptor);
        ClassDescriptor primitiveArrayClassDescriptor2 = this.builtIns.getPrimitiveArrayClassDescriptor(PrimitiveType.CHAR);
        Intrinsics.checkNotNullExpressionValue(primitiveArrayClassDescriptor2, "getPrimitiveArrayClassDescriptor(...)");
        this.charArray = toIrSymbol(primitiveArrayClassDescriptor2);
        ClassDescriptor primitiveArrayClassDescriptor3 = this.builtIns.getPrimitiveArrayClassDescriptor(PrimitiveType.SHORT);
        Intrinsics.checkNotNullExpressionValue(primitiveArrayClassDescriptor3, "getPrimitiveArrayClassDescriptor(...)");
        this.shortArray = toIrSymbol(primitiveArrayClassDescriptor3);
        ClassDescriptor primitiveArrayClassDescriptor4 = this.builtIns.getPrimitiveArrayClassDescriptor(PrimitiveType.INT);
        Intrinsics.checkNotNullExpressionValue(primitiveArrayClassDescriptor4, "getPrimitiveArrayClassDescriptor(...)");
        this.intArray = toIrSymbol(primitiveArrayClassDescriptor4);
        ClassDescriptor primitiveArrayClassDescriptor5 = this.builtIns.getPrimitiveArrayClassDescriptor(PrimitiveType.LONG);
        Intrinsics.checkNotNullExpressionValue(primitiveArrayClassDescriptor5, "getPrimitiveArrayClassDescriptor(...)");
        this.longArray = toIrSymbol(primitiveArrayClassDescriptor5);
        ClassDescriptor primitiveArrayClassDescriptor6 = this.builtIns.getPrimitiveArrayClassDescriptor(PrimitiveType.FLOAT);
        Intrinsics.checkNotNullExpressionValue(primitiveArrayClassDescriptor6, "getPrimitiveArrayClassDescriptor(...)");
        this.floatArray = toIrSymbol(primitiveArrayClassDescriptor6);
        ClassDescriptor primitiveArrayClassDescriptor7 = this.builtIns.getPrimitiveArrayClassDescriptor(PrimitiveType.DOUBLE);
        Intrinsics.checkNotNullExpressionValue(primitiveArrayClassDescriptor7, "getPrimitiveArrayClassDescriptor(...)");
        this.doubleArray = toIrSymbol(primitiveArrayClassDescriptor7);
        ClassDescriptor primitiveArrayClassDescriptor8 = this.builtIns.getPrimitiveArrayClassDescriptor(PrimitiveType.BOOLEAN);
        Intrinsics.checkNotNullExpressionValue(primitiveArrayClassDescriptor8, "getPrimitiveArrayClassDescriptor(...)");
        this.booleanArray = toIrSymbol(primitiveArrayClassDescriptor8);
        PrimitiveType[] primitiveTypeArrValues = PrimitiveType.values();
        LinkedHashMap linkedHashMap = new LinkedHashMap(RangesKt.coerceAtLeast(MapsKt.mapCapacity(primitiveTypeArrValues.length), 16));
        for (PrimitiveType primitiveType : primitiveTypeArrValues) {
            ClassDescriptor primitiveArrayClassDescriptor9 = this.builtIns.getPrimitiveArrayClassDescriptor(primitiveType);
            Intrinsics.checkNotNullExpressionValue(primitiveArrayClassDescriptor9, "getPrimitiveArrayClassDescriptor(...)");
            Pair pair = TuplesKt.to(toIrSymbol(primitiveArrayClassDescriptor9), primitiveType);
            linkedHashMap.put(pair.getFirst(), pair.getSecond());
        }
        this.primitiveArraysToPrimitiveTypes = linkedHashMap;
        Map<IrClassSymbol, PrimitiveType> primitiveArraysToPrimitiveTypes = getPrimitiveArraysToPrimitiveTypes();
        ArrayList arrayList = new ArrayList(primitiveArraysToPrimitiveTypes.size());
        for (Map.Entry<IrClassSymbol, PrimitiveType> entry : primitiveArraysToPrimitiveTypes.entrySet()) {
            arrayList.add(TuplesKt.to(entry.getValue(), entry.getKey()));
        }
        this.primitiveTypesToPrimitiveArrays = MapsKt.toMap(arrayList);
        Map<IrClassSymbol, PrimitiveType> primitiveArraysToPrimitiveTypes2 = getPrimitiveArraysToPrimitiveTypes();
        LinkedHashMap linkedHashMap2 = new LinkedHashMap(MapsKt.mapCapacity(primitiveArraysToPrimitiveTypes2.size()));
        Iterator<T> it = primitiveArraysToPrimitiveTypes2.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry entry2 = (Map.Entry) it.next();
            linkedHashMap2.put(entry2.getKey(), getPrimitiveTypeToIrType().get(entry2.getValue()));
        }
        this.primitiveArrayElementTypes = linkedHashMap2;
        Sequence<Map.Entry> sequenceAsSequence = MapsKt.asSequence(getPrimitiveArrayElementTypes());
        LinkedHashMap linkedHashMap3 = new LinkedHashMap();
        for (Map.Entry entry3 : sequenceAsSequence) {
            Pair pair2 = TuplesKt.to(entry3.getValue(), entry3.getKey());
            linkedHashMap3.put(pair2.getFirst(), pair2.getSecond());
        }
        this.primitiveArrayForType = linkedHashMap3;
        UnsignedType[] unsignedTypeArrValues = UnsignedType.values();
        ArrayList arrayList2 = new ArrayList();
        int length = unsignedTypeArrValues.length;
        int i = 0;
        while (true) {
            obj = null;
            if (i >= length) {
                break;
            }
            UnsignedType unsignedType = unsignedTypeArrValues[i];
            ModuleDescriptorImpl builtInsModule2 = this.builtIns.getBuiltInsModule();
            Intrinsics.checkNotNullExpressionValue(builtInsModule2, "getBuiltInsModule(...)");
            ClassDescriptor classDescriptorFindClassAcrossModuleDependencies = FindClassInModuleKt.findClassAcrossModuleDependencies(builtInsModule2, unsignedType.getArrayClassId());
            IrClassSymbol irSymbol = classDescriptorFindClassAcrossModuleDependencies != null ? toIrSymbol(classDescriptorFindClassAcrossModuleDependencies) : null;
            obj = irSymbol != null ? TuplesKt.to(unsignedType, irSymbol) : null;
            if (obj != null) {
                arrayList2.add(obj);
            }
            i++;
        }
        this.unsignedTypesToUnsignedArrays = MapsKt.toMap(arrayList2);
        this.unsignedArraysElementTypes = LazyKt.lazy(new Function0<Map<IrClassSymbol, ? extends IrType>>() { // from class: org.jetbrains.kotlin.psi2ir.descriptors.IrBuiltInsOverDescriptors$unsignedArraysElementTypes$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Map<IrClassSymbol, ? extends IrType> invoke() {
                IrType irType;
                SimpleType defaultType3;
                Map<UnsignedType, IrClassSymbol> unsignedTypesToUnsignedArrays = this.this$0.getUnsignedTypesToUnsignedArrays();
                IrBuiltInsOverDescriptors irBuiltInsOverDescriptors2 = this.this$0;
                ArrayList arrayList3 = new ArrayList(unsignedTypesToUnsignedArrays.size());
                for (Map.Entry<UnsignedType, IrClassSymbol> entry4 : unsignedTypesToUnsignedArrays.entrySet()) {
                    UnsignedType key = entry4.getKey();
                    IrClassSymbol value = entry4.getValue();
                    ModuleDescriptorImpl builtInsModule3 = irBuiltInsOverDescriptors2.getBuiltIns().getBuiltInsModule();
                    Intrinsics.checkNotNullExpressionValue(builtInsModule3, "getBuiltInsModule(...)");
                    ClassDescriptor classDescriptorFindClassAcrossModuleDependencies2 = FindClassInModuleKt.findClassAcrossModuleDependencies(builtInsModule3, key.getClassId());
                    if (classDescriptorFindClassAcrossModuleDependencies2 == null || (defaultType3 = classDescriptorFindClassAcrossModuleDependencies2.getDefaultType()) == null) {
                        irType = null;
                    } else {
                        Intrinsics.checkNotNull(defaultType3);
                        irType = irBuiltInsOverDescriptors2.toIrType(defaultType3);
                    }
                    arrayList3.add(TuplesKt.to(value, irType));
                }
                return MapsKt.toMap(arrayList3);
            }
        });
        this.lessFunByOperandType = defineComparisonOperatorForEachIrType(getPrimitiveIrTypesWithComparisons(), BuiltInOperatorNames.LESS);
        this.lessOrEqualFunByOperandType = defineComparisonOperatorForEachIrType(getPrimitiveIrTypesWithComparisons(), BuiltInOperatorNames.LESS_OR_EQUAL);
        this.greaterOrEqualFunByOperandType = defineComparisonOperatorForEachIrType(getPrimitiveIrTypesWithComparisons(), BuiltInOperatorNames.GREATER_OR_EQUAL);
        this.greaterFunByOperandType = defineComparisonOperatorForEachIrType(getPrimitiveIrTypesWithComparisons(), BuiltInOperatorNames.GREATER);
        List<IrType> primitiveFloatingPointIrTypes = getPrimitiveFloatingPointIrTypes();
        ArrayList arrayList3 = new ArrayList(CollectionsKt.collectionSizeOrDefault(primitiveFloatingPointIrTypes, 10));
        for (IrType irType : primitiveFloatingPointIrTypes) {
            arrayList3.add(TuplesKt.to(IrTypesKt.getClassifierOrFail(irType), defineOperator(BuiltInOperatorNames.IEEE754_EQUALS, getBooleanType(), CollectionsKt.listOf((Object[]) new IrType[]{IrTypesKt.makeNullable(irType), IrTypesKt.makeNullable(irType)}), true)));
        }
        this.ieee754equalsFunByOperandType = MapsKt.toMap(arrayList3);
        MemberScope unsubstitutedMemberScope = this.builtIns.getBoolean().getUnsubstitutedMemberScope();
        Name nameIdentifier2 = Name.identifier("not");
        Intrinsics.checkNotNullExpressionValue(nameIdentifier2, "identifier(...)");
        SimpleFunctionDescriptor simpleFunctionDescriptor = (SimpleFunctionDescriptor) CollectionsKt.single(unsubstitutedMemberScope.getContributedFunctions(nameIdentifier2, NoLookupLocation.FROM_BACKEND));
        this.booleanNot = simpleFunctionDescriptor;
        this.booleanNotSymbol = this.symbolTable.getDescriptorExtension().referenceSimpleFunction(simpleFunctionDescriptor);
        this.eqeqeqSymbol = defineOperator$default(this, BuiltInOperatorNames.EQEQEQ, getBooleanType(), CollectionsKt.listOf((Object[]) new IrType[]{getAnyNType(), getAnyNType()}), false, 8, null);
        this.eqeqSymbol = defineOperator(BuiltInOperatorNames.EQEQ, getBooleanType(), CollectionsKt.listOf((Object[]) new IrType[]{getAnyNType(), getAnyNType()}), true);
        this.throwCceSymbol = defineOperator$default(this, BuiltInOperatorNames.THROW_CCE, getNothingType(), CollectionsKt.emptyList(), false, 8, null);
        this.throwIseSymbol = defineOperator$default(this, BuiltInOperatorNames.THROW_ISE, getNothingType(), CollectionsKt.emptyList(), false, 8, null);
        this.andandSymbol = defineOperator(BuiltInOperatorNames.ANDAND, getBooleanType(), CollectionsKt.listOf((Object[]) new IrType[]{getBooleanType(), getBooleanType()}), true);
        this.ororSymbol = defineOperator(BuiltInOperatorNames.OROR, getBooleanType(), CollectionsKt.listOf((Object[]) new IrType[]{getBooleanType(), getBooleanType()}), true);
        this.noWhenBranchMatchedExceptionSymbol = defineOperator$default(this, BuiltInOperatorNames.NO_WHEN_BRANCH_MATCHED_EXCEPTION, getNothingType(), CollectionsKt.emptyList(), false, 8, null);
        this.illegalArgumentExceptionSymbol = defineOperator$default(this, BuiltInOperatorNames.ILLEGAL_ARGUMENT_EXCEPTION, getNothingType(), CollectionsKt.listOf(getStringType()), false, 8, null);
        this.checkNotNullSymbol = defineCheckNotNullOperator();
        this.dataClassArrayMemberHashCodeSymbol = defineOperator$default(this, "dataClassArrayMemberHashCode", getIntType(), CollectionsKt.listOf(getAnyType()), false, 8, null);
        this.dataClassArrayMemberToStringSymbol = defineOperator$default(this, "dataClassArrayMemberToString", getStringType(), CollectionsKt.listOf(getAnyNType()), false, 8, null);
        MemberScope unsubstitutedMemberScope2 = this.builtIns.getInt().getUnsubstitutedMemberScope();
        Intrinsics.checkNotNullExpressionValue(unsubstitutedMemberScope2, "getUnsubstitutedMemberScope(...)");
        Name nameIdentifier3 = Name.identifier("times");
        Intrinsics.checkNotNullExpressionValue(nameIdentifier3, "identifier(...)");
        for (Object obj2 : unsubstitutedMemberScope2.getContributedFunctions(nameIdentifier3, NoLookupLocation.FROM_BACKEND)) {
            if (KotlinTypeChecker.DEFAULT.equalTypes(((CallableMemberDescriptor) obj2).getValueParameters().get(0).getType(), this.int)) {
                this.intTimesSymbol = this.symbolTable.getDescriptorExtension().referenceSimpleFunction((SimpleFunctionDescriptor) obj2);
                MemberScope unsubstitutedMemberScope3 = this.builtIns.getInt().getUnsubstitutedMemberScope();
                Intrinsics.checkNotNullExpressionValue(unsubstitutedMemberScope3, "getUnsubstitutedMemberScope(...)");
                Name nameIdentifier4 = Name.identifier("xor");
                Intrinsics.checkNotNullExpressionValue(nameIdentifier4, "identifier(...)");
                for (Object obj3 : unsubstitutedMemberScope3.getContributedFunctions(nameIdentifier4, NoLookupLocation.FROM_BACKEND)) {
                    if (KotlinTypeChecker.DEFAULT.equalTypes(((CallableMemberDescriptor) obj3).getValueParameters().get(0).getType(), this.int)) {
                        this.intXorSymbol = this.symbolTable.getDescriptorExtension().referenceSimpleFunction((SimpleFunctionDescriptor) obj3);
                        MemberScope unsubstitutedMemberScope4 = this.builtIns.getInt().getUnsubstitutedMemberScope();
                        Intrinsics.checkNotNullExpressionValue(unsubstitutedMemberScope4, "getUnsubstitutedMemberScope(...)");
                        Name nameIdentifier5 = Name.identifier("plus");
                        Intrinsics.checkNotNullExpressionValue(nameIdentifier5, "identifier(...)");
                        for (Object obj4 : unsubstitutedMemberScope4.getContributedFunctions(nameIdentifier5, NoLookupLocation.FROM_BACKEND)) {
                            if (KotlinTypeChecker.DEFAULT.equalTypes(((CallableMemberDescriptor) obj4).getValueParameters().get(0).getType(), this.int)) {
                                this.intPlusSymbol = this.symbolTable.getDescriptorExtension().referenceSimpleFunction((SimpleFunctionDescriptor) obj4);
                                Name nameIdentifier6 = Name.identifier(VarargLoweringKt.ARRAY_OF_NAME);
                                Intrinsics.checkNotNullExpressionValue(nameIdentifier6, "identifier(...)");
                                for (IrSimpleFunctionSymbol irSimpleFunctionSymbol : IrBuiltIns.findFunctions$default(irBuiltInsOverDescriptors, nameIdentifier6, null, 2, null)) {
                                    if (irSimpleFunctionSymbol.getDescriptor().getExtensionReceiverParameter() == null && irSimpleFunctionSymbol.getDescriptor().mo6753getDispatchReceiverParameter() == null) {
                                        if (irSimpleFunctionSymbol.getDescriptor().getValueParameters().size() == 1) {
                                            if (irSimpleFunctionSymbol.getDescriptor().getValueParameters().get(0).getVarargElementType() != null) {
                                                this.arrayOf = irSimpleFunctionSymbol;
                                                Name nameIdentifier7 = Name.identifier("arrayOfNulls");
                                                Intrinsics.checkNotNullExpressionValue(nameIdentifier7, "identifier(...)");
                                                for (IrSimpleFunctionSymbol irSimpleFunctionSymbol2 : IrBuiltIns.findFunctions$default(irBuiltInsOverDescriptors, nameIdentifier7, null, 2, null)) {
                                                    if (irSimpleFunctionSymbol2.getDescriptor().getExtensionReceiverParameter() == null && irSimpleFunctionSymbol2.getDescriptor().mo6753getDispatchReceiverParameter() == null) {
                                                        if (irSimpleFunctionSymbol2.getDescriptor().getValueParameters().size() == 1) {
                                                            if (KotlinBuiltIns.isInt(irSimpleFunctionSymbol2.getDescriptor().getValueParameters().get(0).getType())) {
                                                                this.arrayOfNulls = irSimpleFunctionSymbol2;
                                                                this.linkageErrorSymbol = defineOperator$default(this, "linkageError", getNothingType(), CollectionsKt.listOf(getStringType()), false, 8, null);
                                                                ClassDescriptor classDescriptor9 = this.builtIns.getEnum();
                                                                Intrinsics.checkNotNullExpressionValue(classDescriptor9, "getEnum(...)");
                                                                this.enumClass = toIrSymbol(classDescriptor9);
                                                                this.binaryOperatorCache = new LinkedHashMap();
                                                                this.unaryOperatorCache = new LinkedHashMap();
                                                                for (IrSimpleFunctionSymbol irSimpleFunctionSymbol3 : findFunctions(OperatorNameConventions.TO_STRING, IncrementalJvmCacheKt.KOTLIN_CACHE_DIRECTORY_NAME)) {
                                                                    FunctionDescriptor descriptor = irSimpleFunctionSymbol3.getDescriptor();
                                                                    if (descriptor instanceof SimpleFunctionDescriptor) {
                                                                        SimpleFunctionDescriptor simpleFunctionDescriptor2 = (SimpleFunctionDescriptor) descriptor;
                                                                        if (simpleFunctionDescriptor2.mo6753getDispatchReceiverParameter() == null && simpleFunctionDescriptor2.getExtensionReceiverParameter() != null) {
                                                                            ReceiverParameterDescriptor extensionReceiverParameter = simpleFunctionDescriptor2.getExtensionReceiverParameter();
                                                                            Intrinsics.checkNotNull(extensionReceiverParameter);
                                                                            if (KotlinBuiltIns.isNullableAny(extensionReceiverParameter.getType()) && simpleFunctionDescriptor2.getValueParameters().isEmpty()) {
                                                                                this.extensionToString = irSimpleFunctionSymbol3;
                                                                                IrSimpleFunctionSymbol irSimpleFunctionSymbol4 = null;
                                                                                boolean z = false;
                                                                                for (IrSimpleFunctionSymbol irSimpleFunctionSymbol5 : findBuiltInClassMemberFunctions(getAnyClass(), OperatorNameConventions.TO_STRING)) {
                                                                                    FunctionDescriptor descriptor2 = irSimpleFunctionSymbol5.getDescriptor();
                                                                                    if ((descriptor2 instanceof SimpleFunctionDescriptor) && ((SimpleFunctionDescriptor) descriptor2).getValueParameters().isEmpty()) {
                                                                                        if (z) {
                                                                                            throw new IllegalArgumentException("Collection contains more than one matching element.");
                                                                                        }
                                                                                        irSimpleFunctionSymbol4 = irSimpleFunctionSymbol5;
                                                                                        z = true;
                                                                                    }
                                                                                }
                                                                                if (z) {
                                                                                    this.memberToString = irSimpleFunctionSymbol4;
                                                                                    for (IrSimpleFunctionSymbol irSimpleFunctionSymbol6 : findFunctions(OperatorNameConventions.PLUS, IncrementalJvmCacheKt.KOTLIN_CACHE_DIRECTORY_NAME)) {
                                                                                        FunctionDescriptor descriptor3 = irSimpleFunctionSymbol6.getDescriptor();
                                                                                        if (descriptor3 instanceof SimpleFunctionDescriptor) {
                                                                                            SimpleFunctionDescriptor simpleFunctionDescriptor3 = (SimpleFunctionDescriptor) descriptor3;
                                                                                            if (simpleFunctionDescriptor3.mo6753getDispatchReceiverParameter() == null && simpleFunctionDescriptor3.getExtensionReceiverParameter() != null) {
                                                                                                ReceiverParameterDescriptor extensionReceiverParameter2 = simpleFunctionDescriptor3.getExtensionReceiverParameter();
                                                                                                Intrinsics.checkNotNull(extensionReceiverParameter2);
                                                                                                if (KotlinBuiltIns.isStringOrNullableString(extensionReceiverParameter2.getType())) {
                                                                                                    if (simpleFunctionDescriptor3.getValueParameters().size() == 1) {
                                                                                                        List<ValueParameterDescriptor> valueParameters = simpleFunctionDescriptor3.getValueParameters();
                                                                                                        Intrinsics.checkNotNullExpressionValue(valueParameters, "getValueParameters(...)");
                                                                                                        if (KotlinBuiltIns.isNullableAny(((ValueParameterDescriptor) CollectionsKt.first((List) valueParameters)).getType())) {
                                                                                                            this.extensionStringPlus = irSimpleFunctionSymbol6;
                                                                                                            boolean z2 = false;
                                                                                                            for (Object obj5 : findBuiltInClassMemberFunctions(getStringClass(), OperatorNameConventions.PLUS)) {
                                                                                                                FunctionDescriptor descriptor4 = ((IrSimpleFunctionSymbol) obj5).getDescriptor();
                                                                                                                if (descriptor4 instanceof SimpleFunctionDescriptor) {
                                                                                                                    SimpleFunctionDescriptor simpleFunctionDescriptor4 = (SimpleFunctionDescriptor) descriptor4;
                                                                                                                    if (simpleFunctionDescriptor4.getValueParameters().size() == 1) {
                                                                                                                        List<ValueParameterDescriptor> valueParameters2 = simpleFunctionDescriptor4.getValueParameters();
                                                                                                                        Intrinsics.checkNotNullExpressionValue(valueParameters2, "getValueParameters(...)");
                                                                                                                        if (!KotlinBuiltIns.isNullableAny(((ValueParameterDescriptor) CollectionsKt.first((List) valueParameters2)).getType())) {
                                                                                                                            continue;
                                                                                                                        } else {
                                                                                                                            if (z2) {
                                                                                                                                throw new IllegalArgumentException("Collection contains more than one matching element.");
                                                                                                                            }
                                                                                                                            obj = obj5;
                                                                                                                            z2 = true;
                                                                                                                        }
                                                                                                                    } else {
                                                                                                                        continue;
                                                                                                                    }
                                                                                                                }
                                                                                                            }
                                                                                                            if (z2) {
                                                                                                                this.memberStringPlus = (IrSimpleFunctionSymbol) obj;
                                                                                                                return;
                                                                                                            }
                                                                                                            throw new NoSuchElementException("Collection contains no element matching the predicate.");
                                                                                                        }
                                                                                                    } else {
                                                                                                        continue;
                                                                                                    }
                                                                                                }
                                                                                            }
                                                                                        }
                                                                                    }
                                                                                    throw new NoSuchElementException("Collection contains no element matching the predicate.");
                                                                                }
                                                                                throw new NoSuchElementException("Collection contains no element matching the predicate.");
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                                throw new NoSuchElementException("Collection contains no element matching the predicate.");
                                                            }
                                                        }
                                                    }
                                                }
                                                throw new NoSuchElementException("Collection contains no element matching the predicate.");
                                            }
                                        }
                                    }
                                }
                                throw new NoSuchElementException("Collection contains no element matching the predicate.");
                            }
                        }
                        throw new NoSuchElementException("Collection contains no element matching the predicate.");
                    }
                }
                throw new NoSuchElementException("Collection contains no element matching the predicate.");
            }
        }
        throw new NoSuchElementException("Collection contains no element matching the predicate.");
    }

    @Override // org.jetbrains.kotlin.ir.IrBuiltIns
    public LanguageVersionSettings getLanguageVersionSettings() {
        return this.languageVersionSettings;
    }

    public final IrAbstractDescriptorBasedFunctionFactory getFunctionFactory() {
        IrAbstractDescriptorBasedFunctionFactory irAbstractDescriptorBasedFunctionFactory;
        synchronized (this) {
            if (this._functionFactory == null) {
                this._functionFactory = new IrDescriptorBasedFunctionFactory(this, this.symbolTable, this.typeTranslator, null, false, 24, null);
            }
            irAbstractDescriptorBasedFunctionFactory = this._functionFactory;
            Intrinsics.checkNotNull(irAbstractDescriptorBasedFunctionFactory);
        }
        return irAbstractDescriptorBasedFunctionFactory;
    }

    public final void setFunctionFactory(IrAbstractDescriptorBasedFunctionFactory value) {
        Intrinsics.checkNotNullParameter(value, "value");
        synchronized (this) {
            if (this._functionFactory != null) {
                throw new IllegalStateException("functionFactory already set".toString());
            }
            this._functionFactory = value;
            Unit unit = Unit.INSTANCE;
        }
    }

    @Override // org.jetbrains.kotlin.ir.IrBuiltIns
    public IrFactory getIrFactory() {
        return this.irFactory;
    }

    @Override // org.jetbrains.kotlin.ir.IrBuiltIns
    public IrExternalPackageFragment getOperatorsPackageFragment() {
        return this.operatorsPackageFragment;
    }

    private final IrClassSymbol toIrSymbol(ClassDescriptor classDescriptor) {
        return this.symbolTable.getDescriptorExtension().referenceClass(classDescriptor);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final IrType toIrType(KotlinType kotlinType) {
        return this.typeTranslator.translateType(kotlinType);
    }

    static /* synthetic */ IrSimpleFunctionSymbol defineOperator$default(IrBuiltInsOverDescriptors irBuiltInsOverDescriptors, String str, IrType irType, List list, boolean z, int i, Object obj) {
        if ((i & 8) != 0) {
            z = false;
        }
        return irBuiltInsOverDescriptors.defineOperator(str, irType, list, z);
    }

    private final IrSimpleFunctionSymbol defineOperator(final String name2, final IrType returnType, final List<? extends IrType> valueParameterTypes, final boolean isIntrinsicConst) {
        IrBuiltinsPackageFragmentDescriptorImpl irBuiltinsPackageFragmentDescriptorImpl = this.packageFragmentDescriptor;
        Name nameIdentifier = Name.identifier(name2);
        Intrinsics.checkNotNullExpressionValue(nameIdentifier, "identifier(...)");
        KotlinType originalKotlinType = IrTypeBaseKt.getOriginalKotlinType(returnType);
        Intrinsics.checkNotNull(originalKotlinType);
        final IrSimpleBuiltinOperatorDescriptorImpl irSimpleBuiltinOperatorDescriptorImpl = new IrSimpleBuiltinOperatorDescriptorImpl(irBuiltinsPackageFragmentDescriptorImpl, nameIdentifier, originalKotlinType);
        int i = 0;
        for (IrType irType : valueParameterTypes) {
            Name nameIdentifier2 = Name.identifier(ParameterDescription.NAME_PREFIX + i);
            Intrinsics.checkNotNullExpressionValue(nameIdentifier2, "identifier(...)");
            KotlinType originalKotlinType2 = IrTypeBaseKt.getOriginalKotlinType(irType);
            Intrinsics.checkNotNull(originalKotlinType2);
            irSimpleBuiltinOperatorDescriptorImpl.addValueParameter(new IrBuiltinValueParameterDescriptorImpl(irSimpleBuiltinOperatorDescriptorImpl, nameIdentifier2, i, originalKotlinType2));
            i++;
        }
        return this.symbolTable.getDescriptorExtension().declareSimpleFunctionIfNotExists(irSimpleBuiltinOperatorDescriptorImpl, new Function1<IrSimpleFunctionSymbol, IrSimpleFunction>() { // from class: org.jetbrains.kotlin.psi2ir.descriptors.IrBuiltInsOverDescriptors$defineOperator$symbol$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            /* JADX WARN: Multi-variable type inference failed */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final IrSimpleFunction invoke(IrSimpleFunctionSymbol it) {
                Intrinsics.checkNotNullParameter(it, "it");
                IrFactory irFactory = this.this$0.getIrFactory();
                IrDeclarationOriginImpl builtin_operator = IrBuiltIns.INSTANCE.getBUILTIN_OPERATOR();
                Name nameIdentifier3 = Name.identifier(name2);
                Intrinsics.checkNotNullExpressionValue(nameIdentifier3, "identifier(...)");
                DescriptorVisibility PUBLIC = DescriptorVisibilities.PUBLIC;
                Intrinsics.checkNotNullExpressionValue(PUBLIC, "PUBLIC");
                String str = "identifier(...)";
                IrSimpleFunction irSimpleFunctionCreateSimpleFunction$default = IrFactory.DefaultImpls.createSimpleFunction$default(irFactory, -1, -1, builtin_operator, nameIdentifier3, PUBLIC, false, false, returnType, Modality.FINAL, it, false, false, false, false, false, null, false, 98304, null);
                irSimpleFunctionCreateSimpleFunction$default.setParent(this.this$0.getOperatorsPackageFragment());
                this.this$0.getOperatorsPackageFragment().getDeclarations().add(irSimpleFunctionCreateSimpleFunction$default);
                Iterable<IndexedValue> iterableWithIndex = CollectionsKt.withIndex(valueParameterTypes);
                IrSimpleBuiltinOperatorDescriptorImpl irSimpleBuiltinOperatorDescriptorImpl2 = irSimpleBuiltinOperatorDescriptorImpl;
                IrBuiltInsOverDescriptors irBuiltInsOverDescriptors = this.this$0;
                ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(iterableWithIndex, 10));
                for (IndexedValue indexedValue : iterableWithIndex) {
                    int index = indexedValue.getIndex();
                    IrType irType2 = (IrType) indexedValue.component2();
                    IrValueParameterSymbolImpl irValueParameterSymbolImpl = new IrValueParameterSymbolImpl(irSimpleBuiltinOperatorDescriptorImpl2.getValueParameters().get(index));
                    IrFactory irFactory2 = irBuiltInsOverDescriptors.getIrFactory();
                    IrDeclarationOriginImpl builtin_operator2 = IrBuiltIns.INSTANCE.getBUILTIN_OPERATOR();
                    Name nameIdentifier4 = Name.identifier(ParameterDescription.NAME_PREFIX + index);
                    String str2 = str;
                    Intrinsics.checkNotNullExpressionValue(nameIdentifier4, str2);
                    IrValueParameter irValueParameterCreateValueParameter = irFactory2.createValueParameter(-1, -1, (IrDeclarationOrigin) builtin_operator2, nameIdentifier4, irType2, false, (IrValueParameterSymbol) irValueParameterSymbolImpl, index, (IrType) null, false, false, false);
                    irValueParameterCreateValueParameter.setParent(irSimpleFunctionCreateSimpleFunction$default);
                    arrayList.add(irValueParameterCreateValueParameter);
                    str = str2;
                }
                irSimpleFunctionCreateSimpleFunction$default.setValueParameters(arrayList);
                if (isIntrinsicConst) {
                    irSimpleFunctionCreateSimpleFunction$default.setAnnotations(CollectionsKt.plus((Collection<? extends IrConstructorCallImpl>) irSimpleFunctionCreateSimpleFunction$default.getAnnotations(), IrConstructorCallImpl.Companion.fromSymbolDescriptor$default(IrConstructorCallImpl.INSTANCE, -1, -1, this.this$0.intrinsicConstType, this.this$0.intrinsicConstConstructor.getSymbol(), null, 16, null)));
                }
                return irSimpleFunctionCreateSimpleFunction$default;
            }
        }).getSymbol();
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v7, types: [T, org.jetbrains.kotlin.types.SimpleType] */
    /* JADX WARN: Type inference failed for: r1v4, types: [T, java.lang.Object, org.jetbrains.kotlin.descriptors.impl.TypeParameterDescriptorImpl] */
    /* JADX WARN: Type inference failed for: r1v8, types: [T, org.jetbrains.kotlin.types.SimpleType] */
    /* JADX WARN: Type inference failed for: r1v9, types: [T, org.jetbrains.kotlin.descriptors.impl.ValueParameterDescriptorImpl] */
    private final IrSimpleFunctionSymbol defineCheckNotNullOperator() {
        final Name nameIdentifier = Name.identifier(BuiltInOperatorNames.CHECK_NOT_NULL);
        Intrinsics.checkNotNullExpressionValue(nameIdentifier, "identifier(...)");
        final Ref.ObjectRef objectRef = new Ref.ObjectRef();
        final Ref.ObjectRef objectRef2 = new Ref.ObjectRef();
        final Ref.ObjectRef objectRef3 = new Ref.ObjectRef();
        final Ref.ObjectRef objectRef4 = new Ref.ObjectRef();
        SimpleFunctionDescriptorImpl simpleFunctionDescriptorImplCreate = SimpleFunctionDescriptorImpl.create(this.packageFragmentDescriptor, Annotations.INSTANCE.getEMPTY(), nameIdentifier, CallableMemberDescriptor.Kind.SYNTHESIZED, SourceElement.NO_SOURCE);
        ?? CreateForFurtherModification = TypeParameterDescriptorImpl.createForFurtherModification(simpleFunctionDescriptorImplCreate, Annotations.INSTANCE.getEMPTY(), false, Variance.INVARIANT, Name.identifier("T0"), 0, SourceElement.NO_SOURCE, LockBasedStorageManager.NO_LOCKS);
        CreateForFurtherModification.addUpperBound(this.any);
        CreateForFurtherModification.setInitialized();
        Intrinsics.checkNotNullExpressionValue(CreateForFurtherModification, "apply(...)");
        objectRef.element = CreateForFurtherModification;
        TypeConstructor typeConstructor = ((TypeParameterDescriptorImpl) objectRef.element).getTypeConstructor();
        Intrinsics.checkNotNullExpressionValue(typeConstructor, "getTypeConstructor(...)");
        objectRef4.element = makeNullableType(typeConstructor);
        Intrinsics.checkNotNull(simpleFunctionDescriptorImplCreate);
        Annotations empty = Annotations.INSTANCE.getEMPTY();
        Name nameIdentifier2 = Name.identifier("arg0");
        Intrinsics.checkNotNullExpressionValue(nameIdentifier2, "identifier(...)");
        KotlinType kotlinType = (KotlinType) objectRef4.element;
        SourceElement NO_SOURCE = SourceElement.NO_SOURCE;
        Intrinsics.checkNotNullExpressionValue(NO_SOURCE, "NO_SOURCE");
        objectRef2.element = new ValueParameterDescriptorImpl(simpleFunctionDescriptorImplCreate, null, 0, empty, nameIdentifier2, kotlinType, false, false, false, null, NO_SOURCE);
        TypeConstructor typeConstructor2 = ((TypeParameterDescriptorImpl) objectRef.element).getTypeConstructor();
        Intrinsics.checkNotNullExpressionValue(typeConstructor2, "getTypeConstructor(...)");
        objectRef3.element = makeNonNullType(typeConstructor2);
        simpleFunctionDescriptorImplCreate.initialize((ReceiverParameterDescriptor) null, (ReceiverParameterDescriptor) null, CollectionsKt.emptyList(), CollectionsKt.listOf(objectRef.element), CollectionsKt.listOf(objectRef2.element), (KotlinType) objectRef3.element, Modality.FINAL, DescriptorVisibilities.PUBLIC);
        Intrinsics.checkNotNullExpressionValue(simpleFunctionDescriptorImplCreate, "apply(...)");
        return this.symbolTable.getDescriptorExtension().declareSimpleFunctionIfNotExists(simpleFunctionDescriptorImplCreate, new Function1<IrSimpleFunctionSymbol, IrSimpleFunction>() { // from class: org.jetbrains.kotlin.psi2ir.descriptors.IrBuiltInsOverDescriptors.defineCheckNotNullOperator.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final IrSimpleFunction invoke(IrSimpleFunctionSymbol operatorSymbol) {
                Intrinsics.checkNotNullParameter(operatorSymbol, "operatorSymbol");
                IrTypeParameter irTypeParameterDeclareGlobalTypeParameter = IrBuiltInsOverDescriptors.this.getSymbolTable().getDescriptorExtension().declareGlobalTypeParameter(-1, -1, IrBuiltIns.INSTANCE.getBUILTIN_OPERATOR(), objectRef.element);
                irTypeParameterDeclareGlobalTypeParameter.setSuperTypes(CollectionsKt.plus((Collection<? extends IrType>) irTypeParameterDeclareGlobalTypeParameter.getSuperTypes(), IrBuiltInsOverDescriptors.this.getAnyType()));
                IrTypeParameterSymbol symbol = irTypeParameterDeclareGlobalTypeParameter.getSymbol();
                IrSimpleTypeBuilder irSimpleTypeBuilder = new IrSimpleTypeBuilder();
                Ref.ObjectRef<SimpleType> objectRef5 = objectRef3;
                IrTypeParameterSymbol irTypeParameterSymbol = symbol;
                irSimpleTypeBuilder.setClassifier(irTypeParameterSymbol);
                irSimpleTypeBuilder.setKotlinType(objectRef5.element);
                irSimpleTypeBuilder.setNullability(SimpleTypeNullability.DEFINITELY_NOT_NULL);
                IrSimpleTypeImpl irSimpleTypeImplBuildSimpleType = IrSimpleTypeImplKt.buildSimpleType(irSimpleTypeBuilder);
                IrSimpleTypeBuilder irSimpleTypeBuilder2 = new IrSimpleTypeBuilder();
                Ref.ObjectRef<SimpleType> objectRef6 = objectRef4;
                irSimpleTypeBuilder2.setClassifier(irTypeParameterSymbol);
                irSimpleTypeBuilder2.setKotlinType(objectRef6.element);
                irSimpleTypeBuilder2.setNullability(SimpleTypeNullability.MARKED_NULLABLE);
                IrSimpleTypeImpl irSimpleTypeImplBuildSimpleType2 = IrSimpleTypeImplKt.buildSimpleType(irSimpleTypeBuilder2);
                IrFactory irFactory = IrBuiltInsOverDescriptors.this.getIrFactory();
                IrDeclarationOriginImpl builtin_operator = IrBuiltIns.INSTANCE.getBUILTIN_OPERATOR();
                Name name2 = nameIdentifier;
                DescriptorVisibility PUBLIC = DescriptorVisibilities.PUBLIC;
                Intrinsics.checkNotNullExpressionValue(PUBLIC, "PUBLIC");
                IrSimpleFunction irSimpleFunctionCreateSimpleFunction$default = IrFactory.DefaultImpls.createSimpleFunction$default(irFactory, -1, -1, builtin_operator, name2, PUBLIC, false, false, irSimpleTypeImplBuildSimpleType, Modality.FINAL, operatorSymbol, false, false, false, false, false, null, false, 98304, null);
                IrBuiltInsOverDescriptors irBuiltInsOverDescriptors = IrBuiltInsOverDescriptors.this;
                Ref.ObjectRef<ValueParameterDescriptor> objectRef7 = objectRef2;
                irSimpleFunctionCreateSimpleFunction$default.setParent(irBuiltInsOverDescriptors.getOperatorsPackageFragment());
                irBuiltInsOverDescriptors.getOperatorsPackageFragment().getDeclarations().add(irSimpleFunctionCreateSimpleFunction$default);
                IrValueParameterSymbolImpl irValueParameterSymbolImpl = new IrValueParameterSymbolImpl(objectRef7.element);
                IrFactory irFactory2 = irBuiltInsOverDescriptors.getIrFactory();
                IrDeclarationOriginImpl builtin_operator2 = IrBuiltIns.INSTANCE.getBUILTIN_OPERATOR();
                Name nameIdentifier3 = Name.identifier("arg0");
                Intrinsics.checkNotNullExpressionValue(nameIdentifier3, "identifier(...)");
                IrValueParameter irValueParameterCreateValueParameter = irFactory2.createValueParameter(-1, -1, (IrDeclarationOrigin) builtin_operator2, nameIdentifier3, (IrType) irSimpleTypeImplBuildSimpleType2, false, (IrValueParameterSymbol) irValueParameterSymbolImpl, 0, (IrType) null, false, false, false);
                IrSimpleFunction irSimpleFunction = irSimpleFunctionCreateSimpleFunction$default;
                irValueParameterCreateValueParameter.setParent(irSimpleFunction);
                irTypeParameterDeclareGlobalTypeParameter.setParent(irSimpleFunction);
                irSimpleFunctionCreateSimpleFunction$default.setValueParameters(CollectionsKt.plus((Collection<? extends IrValueParameter>) irSimpleFunctionCreateSimpleFunction$default.getValueParameters(), irValueParameterCreateValueParameter));
                irSimpleFunctionCreateSimpleFunction$default.setTypeParameters(CollectionsKt.plus((Collection<? extends IrTypeParameter>) irSimpleFunctionCreateSimpleFunction$default.getTypeParameters(), irTypeParameterDeclareGlobalTypeParameter));
                return irSimpleFunctionCreateSimpleFunction$default;
            }
        }).getSymbol();
    }

    private final IrSimpleFunctionSymbol defineComparisonOperator(String name2, IrType operandType) {
        return defineOperator(name2, getBooleanType(), CollectionsKt.listOf((Object[]) new IrType[]{operandType, operandType}), true);
    }

    private final Map<IrClassifierSymbol, IrSimpleFunctionSymbol> defineComparisonOperatorForEachIrType(List<? extends IrType> list, String str) {
        List<? extends IrType> list2 = list;
        LinkedHashMap linkedHashMap = new LinkedHashMap(RangesKt.coerceAtLeast(MapsKt.mapCapacity(CollectionsKt.collectionSizeOrDefault(list2, 10)), 16));
        for (IrType irType : list2) {
            Pair pair = TuplesKt.to(IrTypesKt.getClassifierOrFail(irType), defineComparisonOperator(str, irType));
            linkedHashMap.put(pair.getFirst(), pair.getSecond());
        }
        return linkedHashMap;
    }

    public final SimpleType getAny() {
        return this.any;
    }

    @Override // org.jetbrains.kotlin.ir.IrBuiltIns
    public IrType getAnyType() {
        return this.anyType;
    }

    @Override // org.jetbrains.kotlin.ir.IrBuiltIns
    public IrClassSymbol getAnyClass() {
        return this.anyClass;
    }

    @Override // org.jetbrains.kotlin.ir.IrBuiltIns
    public IrType getAnyNType() {
        return this.anyNType;
    }

    public final SimpleType getBool() {
        return this.bool;
    }

    @Override // org.jetbrains.kotlin.ir.IrBuiltIns
    public IrType getBooleanType() {
        return this.booleanType;
    }

    @Override // org.jetbrains.kotlin.ir.IrBuiltIns
    public IrClassSymbol getBooleanClass() {
        return this.booleanClass;
    }

    public final SimpleType getChar() {
        return this.char;
    }

    @Override // org.jetbrains.kotlin.ir.IrBuiltIns
    public IrType getCharType() {
        return this.charType;
    }

    @Override // org.jetbrains.kotlin.ir.IrBuiltIns
    public IrClassSymbol getCharClass() {
        return this.charClass;
    }

    public final SimpleType getNumber() {
        return this.number;
    }

    @Override // org.jetbrains.kotlin.ir.IrBuiltIns
    public IrType getNumberType() {
        return this.numberType;
    }

    @Override // org.jetbrains.kotlin.ir.IrBuiltIns
    public IrClassSymbol getNumberClass() {
        return this.numberClass;
    }

    public final SimpleType getByte() {
        return this.byte;
    }

    @Override // org.jetbrains.kotlin.ir.IrBuiltIns
    public IrType getByteType() {
        return this.byteType;
    }

    @Override // org.jetbrains.kotlin.ir.IrBuiltIns
    public IrClassSymbol getByteClass() {
        return this.byteClass;
    }

    public final SimpleType getShort() {
        return this.short;
    }

    @Override // org.jetbrains.kotlin.ir.IrBuiltIns
    public IrType getShortType() {
        return this.shortType;
    }

    @Override // org.jetbrains.kotlin.ir.IrBuiltIns
    public IrClassSymbol getShortClass() {
        return this.shortClass;
    }

    public final SimpleType getInt() {
        return this.int;
    }

    @Override // org.jetbrains.kotlin.ir.IrBuiltIns
    public IrType getIntType() {
        return this.intType;
    }

    @Override // org.jetbrains.kotlin.ir.IrBuiltIns
    public IrClassSymbol getIntClass() {
        return this.intClass;
    }

    public final SimpleType getLong() {
        return this.long;
    }

    @Override // org.jetbrains.kotlin.ir.IrBuiltIns
    public IrType getLongType() {
        return this.longType;
    }

    @Override // org.jetbrains.kotlin.ir.IrBuiltIns
    public IrClassSymbol getLongClass() {
        return this.longClass;
    }

    public final SimpleType getFloat() {
        return this.float;
    }

    @Override // org.jetbrains.kotlin.ir.IrBuiltIns
    public IrType getFloatType() {
        return this.floatType;
    }

    @Override // org.jetbrains.kotlin.ir.IrBuiltIns
    public IrClassSymbol getFloatClass() {
        return this.floatClass;
    }

    public final SimpleType getDouble() {
        return this.double;
    }

    @Override // org.jetbrains.kotlin.ir.IrBuiltIns
    public IrType getDoubleType() {
        return this.doubleType;
    }

    @Override // org.jetbrains.kotlin.ir.IrBuiltIns
    public IrClassSymbol getDoubleClass() {
        return this.doubleClass;
    }

    public final SimpleType getNothing() {
        return this.nothing;
    }

    @Override // org.jetbrains.kotlin.ir.IrBuiltIns
    public IrType getNothingType() {
        return this.nothingType;
    }

    @Override // org.jetbrains.kotlin.ir.IrBuiltIns
    public IrClassSymbol getNothingClass() {
        return this.nothingClass;
    }

    @Override // org.jetbrains.kotlin.ir.IrBuiltIns
    public IrType getNothingNType() {
        return this.nothingNType;
    }

    public final SimpleType getUnit() {
        return this.unit;
    }

    @Override // org.jetbrains.kotlin.ir.IrBuiltIns
    public IrType getUnitType() {
        return this.unitType;
    }

    @Override // org.jetbrains.kotlin.ir.IrBuiltIns
    public IrClassSymbol getUnitClass() {
        return this.unitClass;
    }

    public final SimpleType getString() {
        return this.string;
    }

    @Override // org.jetbrains.kotlin.ir.IrBuiltIns
    public IrType getStringType() {
        return this.stringType;
    }

    @Override // org.jetbrains.kotlin.ir.IrBuiltIns
    public IrClassSymbol getStringClass() {
        return this.stringClass;
    }

    @Override // org.jetbrains.kotlin.ir.IrBuiltIns
    public IrClassSymbol getCharSequenceClass() {
        return this.charSequenceClass;
    }

    @Override // org.jetbrains.kotlin.ir.IrBuiltIns
    public IrClassSymbol getCollectionClass() {
        return this.collectionClass;
    }

    @Override // org.jetbrains.kotlin.ir.IrBuiltIns
    public IrClassSymbol getSetClass() {
        return this.setClass;
    }

    @Override // org.jetbrains.kotlin.ir.IrBuiltIns
    public IrClassSymbol getListClass() {
        return this.listClass;
    }

    @Override // org.jetbrains.kotlin.ir.IrBuiltIns
    public IrClassSymbol getMapClass() {
        return this.mapClass;
    }

    @Override // org.jetbrains.kotlin.ir.IrBuiltIns
    public IrClassSymbol getMapEntryClass() {
        return this.mapEntryClass;
    }

    @Override // org.jetbrains.kotlin.ir.IrBuiltIns
    public IrClassSymbol getIterableClass() {
        return this.iterableClass;
    }

    @Override // org.jetbrains.kotlin.ir.IrBuiltIns
    public IrClassSymbol getIteratorClass() {
        return this.iteratorClass;
    }

    @Override // org.jetbrains.kotlin.ir.IrBuiltIns
    public IrClassSymbol getListIteratorClass() {
        return this.listIteratorClass;
    }

    @Override // org.jetbrains.kotlin.ir.IrBuiltIns
    public IrClassSymbol getMutableCollectionClass() {
        return this.mutableCollectionClass;
    }

    @Override // org.jetbrains.kotlin.ir.IrBuiltIns
    public IrClassSymbol getMutableSetClass() {
        return this.mutableSetClass;
    }

    @Override // org.jetbrains.kotlin.ir.IrBuiltIns
    public IrClassSymbol getMutableListClass() {
        return this.mutableListClass;
    }

    @Override // org.jetbrains.kotlin.ir.IrBuiltIns
    public IrClassSymbol getMutableMapClass() {
        return this.mutableMapClass;
    }

    @Override // org.jetbrains.kotlin.ir.IrBuiltIns
    public IrClassSymbol getMutableMapEntryClass() {
        return this.mutableMapEntryClass;
    }

    @Override // org.jetbrains.kotlin.ir.IrBuiltIns
    public IrClassSymbol getMutableIterableClass() {
        return this.mutableIterableClass;
    }

    @Override // org.jetbrains.kotlin.ir.IrBuiltIns
    public IrClassSymbol getMutableIteratorClass() {
        return this.mutableIteratorClass;
    }

    @Override // org.jetbrains.kotlin.ir.IrBuiltIns
    public IrClassSymbol getMutableListIteratorClass() {
        return this.mutableListIteratorClass;
    }

    @Override // org.jetbrains.kotlin.ir.IrBuiltIns
    public IrClassSymbol getComparableClass() {
        return this.comparableClass;
    }

    @Override // org.jetbrains.kotlin.ir.IrBuiltIns
    public IrClassSymbol getArrayClass() {
        return this.arrayClass;
    }

    @Override // org.jetbrains.kotlin.ir.IrBuiltIns
    public IrType getThrowableType() {
        return this.throwableType;
    }

    @Override // org.jetbrains.kotlin.ir.IrBuiltIns
    public IrClassSymbol getThrowableClass() {
        return this.throwableClass;
    }

    @Override // org.jetbrains.kotlin.ir.IrBuiltIns
    public IrClassSymbol getKCallableClass() {
        return this.kCallableClass;
    }

    @Override // org.jetbrains.kotlin.ir.IrBuiltIns
    public IrClassSymbol getKPropertyClass() {
        return this.kPropertyClass;
    }

    @Override // org.jetbrains.kotlin.ir.IrBuiltIns
    public IrClassSymbol getKClassClass() {
        return this.kClassClass;
    }

    @Override // org.jetbrains.kotlin.ir.IrBuiltIns
    public IrClassSymbol getKTypeClass() {
        return this.kTypeClass;
    }

    @Override // org.jetbrains.kotlin.ir.IrBuiltIns
    public IrClassSymbol getKProperty0Class() {
        return this.kProperty0Class;
    }

    @Override // org.jetbrains.kotlin.ir.IrBuiltIns
    public IrClassSymbol getKProperty1Class() {
        return this.kProperty1Class;
    }

    @Override // org.jetbrains.kotlin.ir.IrBuiltIns
    public IrClassSymbol getKProperty2Class() {
        return this.kProperty2Class;
    }

    @Override // org.jetbrains.kotlin.ir.IrBuiltIns
    public IrClassSymbol getKMutableProperty0Class() {
        return this.kMutableProperty0Class;
    }

    @Override // org.jetbrains.kotlin.ir.IrBuiltIns
    public IrClassSymbol getKMutableProperty1Class() {
        return this.kMutableProperty1Class;
    }

    @Override // org.jetbrains.kotlin.ir.IrBuiltIns
    public IrClassSymbol getKMutableProperty2Class() {
        return this.kMutableProperty2Class;
    }

    @Override // org.jetbrains.kotlin.ir.IrBuiltIns
    public IrClassSymbol getFunctionClass() {
        return this.functionClass;
    }

    @Override // org.jetbrains.kotlin.ir.IrBuiltIns
    public IrClassSymbol getKFunctionClass() {
        return this.kFunctionClass;
    }

    @Override // org.jetbrains.kotlin.ir.IrBuiltIns
    public IrClassSymbol getAnnotationClass() {
        return this.annotationClass;
    }

    @Override // org.jetbrains.kotlin.ir.IrBuiltIns
    public IrType getAnnotationType() {
        return this.annotationType;
    }

    @Override // org.jetbrains.kotlin.ir.IrBuiltIns
    public IrClassSymbol getKPropertyClass(boolean mutable, int n) {
        if (n == 0) {
            return mutable ? getKMutableProperty0Class() : getKProperty0Class();
        }
        if (n == 1) {
            return mutable ? getKMutableProperty1Class() : getKProperty1Class();
        }
        if (n == 2) {
            return mutable ? getKMutableProperty2Class() : getKProperty2Class();
        }
        throw new IllegalStateException(("No KProperty for n=" + n + " mutable=" + mutable).toString());
    }

    @Override // org.jetbrains.kotlin.ir.IrBuiltIns
    public Map<PrimitiveType, IrType> getPrimitiveTypeToIrType() {
        return this.primitiveTypeToIrType;
    }

    public final List<SimpleType> getPrimitiveTypes() {
        return this.primitiveTypes;
    }

    @Override // org.jetbrains.kotlin.ir.IrBuiltIns
    public List<IrType> getPrimitiveIrTypes() {
        return this.primitiveIrTypes;
    }

    @Override // org.jetbrains.kotlin.ir.IrBuiltIns
    public List<IrType> getPrimitiveIrTypesWithComparisons() {
        return this.primitiveIrTypesWithComparisons;
    }

    @Override // org.jetbrains.kotlin.ir.IrBuiltIns
    public List<IrType> getPrimitiveFloatingPointIrTypes() {
        return this.primitiveFloatingPointIrTypes;
    }

    @Override // org.jetbrains.kotlin.ir.IrBuiltIns
    public IrClassSymbol getByteArray() {
        return this.byteArray;
    }

    @Override // org.jetbrains.kotlin.ir.IrBuiltIns
    public IrClassSymbol getCharArray() {
        return this.charArray;
    }

    @Override // org.jetbrains.kotlin.ir.IrBuiltIns
    public IrClassSymbol getShortArray() {
        return this.shortArray;
    }

    @Override // org.jetbrains.kotlin.ir.IrBuiltIns
    public IrClassSymbol getIntArray() {
        return this.intArray;
    }

    @Override // org.jetbrains.kotlin.ir.IrBuiltIns
    public IrClassSymbol getLongArray() {
        return this.longArray;
    }

    @Override // org.jetbrains.kotlin.ir.IrBuiltIns
    public IrClassSymbol getFloatArray() {
        return this.floatArray;
    }

    @Override // org.jetbrains.kotlin.ir.IrBuiltIns
    public IrClassSymbol getDoubleArray() {
        return this.doubleArray;
    }

    @Override // org.jetbrains.kotlin.ir.IrBuiltIns
    public IrClassSymbol getBooleanArray() {
        return this.booleanArray;
    }

    @Override // org.jetbrains.kotlin.ir.IrBuiltIns
    public Map<IrClassSymbol, PrimitiveType> getPrimitiveArraysToPrimitiveTypes() {
        return this.primitiveArraysToPrimitiveTypes;
    }

    @Override // org.jetbrains.kotlin.ir.IrBuiltIns
    public Map<PrimitiveType, IrClassSymbol> getPrimitiveTypesToPrimitiveArrays() {
        return this.primitiveTypesToPrimitiveArrays;
    }

    @Override // org.jetbrains.kotlin.ir.IrBuiltIns
    public Map<IrClassSymbol, IrType> getPrimitiveArrayElementTypes() {
        return this.primitiveArrayElementTypes;
    }

    @Override // org.jetbrains.kotlin.ir.IrBuiltIns
    public Map<IrType, IrClassSymbol> getPrimitiveArrayForType() {
        return this.primitiveArrayForType;
    }

    @Override // org.jetbrains.kotlin.ir.IrBuiltIns
    public Map<UnsignedType, IrClassSymbol> getUnsignedTypesToUnsignedArrays() {
        return this.unsignedTypesToUnsignedArrays;
    }

    @Override // org.jetbrains.kotlin.ir.IrBuiltIns
    public Map<IrClassSymbol, IrType> getUnsignedArraysElementTypes() {
        return (Map) this.unsignedArraysElementTypes.getValue();
    }

    @Override // org.jetbrains.kotlin.ir.IrBuiltIns
    public Map<IrClassifierSymbol, IrSimpleFunctionSymbol> getLessFunByOperandType() {
        return this.lessFunByOperandType;
    }

    @Override // org.jetbrains.kotlin.ir.IrBuiltIns
    public Map<IrClassifierSymbol, IrSimpleFunctionSymbol> getLessOrEqualFunByOperandType() {
        return this.lessOrEqualFunByOperandType;
    }

    @Override // org.jetbrains.kotlin.ir.IrBuiltIns
    public Map<IrClassifierSymbol, IrSimpleFunctionSymbol> getGreaterOrEqualFunByOperandType() {
        return this.greaterOrEqualFunByOperandType;
    }

    @Override // org.jetbrains.kotlin.ir.IrBuiltIns
    public Map<IrClassifierSymbol, IrSimpleFunctionSymbol> getGreaterFunByOperandType() {
        return this.greaterFunByOperandType;
    }

    @Override // org.jetbrains.kotlin.ir.IrBuiltIns
    public Map<IrClassifierSymbol, IrSimpleFunctionSymbol> getIeee754equalsFunByOperandType() {
        return this.ieee754equalsFunByOperandType;
    }

    public final SimpleFunctionDescriptor getBooleanNot() {
        return this.booleanNot;
    }

    @Override // org.jetbrains.kotlin.ir.IrBuiltIns
    public IrSimpleFunctionSymbol getBooleanNotSymbol() {
        return this.booleanNotSymbol;
    }

    @Override // org.jetbrains.kotlin.ir.IrBuiltIns
    public IrSimpleFunctionSymbol getEqeqeqSymbol() {
        return this.eqeqeqSymbol;
    }

    @Override // org.jetbrains.kotlin.ir.IrBuiltIns
    public IrSimpleFunctionSymbol getEqeqSymbol() {
        return this.eqeqSymbol;
    }

    @Override // org.jetbrains.kotlin.ir.IrBuiltIns
    public IrSimpleFunctionSymbol getThrowCceSymbol() {
        return this.throwCceSymbol;
    }

    @Override // org.jetbrains.kotlin.ir.IrBuiltIns
    public IrSimpleFunctionSymbol getThrowIseSymbol() {
        return this.throwIseSymbol;
    }

    @Override // org.jetbrains.kotlin.ir.IrBuiltIns
    public IrSimpleFunctionSymbol getAndandSymbol() {
        return this.andandSymbol;
    }

    @Override // org.jetbrains.kotlin.ir.IrBuiltIns
    public IrSimpleFunctionSymbol getOrorSymbol() {
        return this.ororSymbol;
    }

    @Override // org.jetbrains.kotlin.ir.IrBuiltIns
    public IrSimpleFunctionSymbol getNoWhenBranchMatchedExceptionSymbol() {
        return this.noWhenBranchMatchedExceptionSymbol;
    }

    @Override // org.jetbrains.kotlin.ir.IrBuiltIns
    public IrSimpleFunctionSymbol getIllegalArgumentExceptionSymbol() {
        return this.illegalArgumentExceptionSymbol;
    }

    @Override // org.jetbrains.kotlin.ir.IrBuiltIns
    public IrSimpleFunctionSymbol getCheckNotNullSymbol() {
        return this.checkNotNullSymbol;
    }

    private final SimpleType makeNonNullType(TypeConstructor typeConstructor) {
        return KotlinTypeFactory.simpleType$default(TypeAttributes.INSTANCE.getEmpty(), typeConstructor, CollectionsKt.emptyList(), false, (KotlinTypeRefiner) null, 16, (Object) null);
    }

    private final SimpleType makeNullableType(TypeConstructor typeConstructor) {
        return KotlinTypeFactory.simpleType$default(TypeAttributes.INSTANCE.getEmpty(), typeConstructor, CollectionsKt.emptyList(), true, (KotlinTypeRefiner) null, 16, (Object) null);
    }

    @Override // org.jetbrains.kotlin.ir.IrBuiltIns
    public IrSimpleFunctionSymbol getDataClassArrayMemberHashCodeSymbol() {
        return this.dataClassArrayMemberHashCodeSymbol;
    }

    @Override // org.jetbrains.kotlin.ir.IrBuiltIns
    public IrSimpleFunctionSymbol getDataClassArrayMemberToStringSymbol() {
        return this.dataClassArrayMemberToStringSymbol;
    }

    @Override // org.jetbrains.kotlin.ir.IrBuiltIns
    public IrSimpleFunctionSymbol getIntTimesSymbol() {
        return this.intTimesSymbol;
    }

    @Override // org.jetbrains.kotlin.ir.IrBuiltIns
    public IrSimpleFunctionSymbol getIntXorSymbol() {
        return this.intXorSymbol;
    }

    @Override // org.jetbrains.kotlin.ir.IrBuiltIns
    public IrSimpleFunctionSymbol getIntPlusSymbol() {
        return this.intPlusSymbol;
    }

    @Override // org.jetbrains.kotlin.ir.IrBuiltIns
    public IrSimpleFunctionSymbol getArrayOf() {
        return this.arrayOf;
    }

    @Override // org.jetbrains.kotlin.ir.IrBuiltIns
    public IrSimpleFunctionSymbol getArrayOfNulls() {
        return this.arrayOfNulls;
    }

    @Override // org.jetbrains.kotlin.ir.IrBuiltIns
    public IrSimpleFunctionSymbol getLinkageErrorSymbol() {
        return this.linkageErrorSymbol;
    }

    @Override // org.jetbrains.kotlin.ir.IrBuiltIns
    public IrClassSymbol getEnumClass() {
        return this.enumClass;
    }

    private final MemberScope builtInsPackage(String... packageNameSegments) {
        ModuleDescriptorImpl builtInsModule = this.builtIns.getBuiltInsModule();
        FqName fqNameFromSegments = FqName.fromSegments(CollectionsKt.listOf(Arrays.copyOf(packageNameSegments, packageNameSegments.length)));
        Intrinsics.checkNotNullExpressionValue(fqNameFromSegments, "fromSegments(...)");
        return builtInsModule.getPackage(fqNameFromSegments).getMemberScope();
    }

    @Override // org.jetbrains.kotlin.ir.IrBuiltIns
    public Iterable<IrSimpleFunctionSymbol> findFunctions(Name name2, String... packageNameSegments) {
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(packageNameSegments, "packageNameSegments");
        Collection<? extends SimpleFunctionDescriptor> contributedFunctions = builtInsPackage((String[]) Arrays.copyOf(packageNameSegments, packageNameSegments.length)).getContributedFunctions(name2, NoLookupLocation.FROM_BACKEND);
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(contributedFunctions, 10));
        Iterator<T> it = contributedFunctions.iterator();
        while (it.hasNext()) {
            arrayList.add(this.symbolTable.getDescriptorExtension().referenceSimpleFunction((SimpleFunctionDescriptor) it.next()));
        }
        return arrayList;
    }

    @Override // org.jetbrains.kotlin.ir.IrBuiltIns
    public Iterable<IrSimpleFunctionSymbol> findFunctions(Name name2, FqName packageFqName) {
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(packageFqName, "packageFqName");
        Collection<? extends SimpleFunctionDescriptor> contributedFunctions = this.builtIns.getBuiltInsModule().getPackage(packageFqName).getMemberScope().getContributedFunctions(name2, NoLookupLocation.FROM_BACKEND);
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(contributedFunctions, 10));
        Iterator<T> it = contributedFunctions.iterator();
        while (it.hasNext()) {
            arrayList.add(this.symbolTable.getDescriptorExtension().referenceSimpleFunction((SimpleFunctionDescriptor) it.next()));
        }
        return arrayList;
    }

    @Override // org.jetbrains.kotlin.ir.IrBuiltIns
    public Iterable<IrPropertySymbol> findProperties(Name name2, FqName packageFqName) {
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(packageFqName, "packageFqName");
        Collection<? extends PropertyDescriptor> contributedVariables = this.builtIns.getBuiltInsModule().getPackage(packageFqName).getMemberScope().getContributedVariables(name2, NoLookupLocation.FROM_BACKEND);
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(contributedVariables, 10));
        Iterator<T> it = contributedVariables.iterator();
        while (it.hasNext()) {
            arrayList.add(this.symbolTable.getDescriptorExtension().referenceProperty((PropertyDescriptor) it.next()));
        }
        return arrayList;
    }

    @Override // org.jetbrains.kotlin.ir.IrBuiltIns
    public IrClassSymbol findClass(Name name2, String... packageNameSegments) {
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(packageNameSegments, "packageNameSegments");
        ClassifierDescriptor contributedClassifier = builtInsPackage((String[]) Arrays.copyOf(packageNameSegments, packageNameSegments.length)).mo7465getContributedClassifier(name2, NoLookupLocation.FROM_BACKEND);
        ClassDescriptor classDescriptor = contributedClassifier instanceof ClassDescriptor ? (ClassDescriptor) contributedClassifier : null;
        if (classDescriptor != null) {
            return this.symbolTable.getDescriptorExtension().referenceClass(classDescriptor);
        }
        return null;
    }

    @Override // org.jetbrains.kotlin.ir.IrBuiltIns
    public IrClassSymbol findClass(Name name2, FqName packageFqName) {
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(packageFqName, "packageFqName");
        ClassDescriptor classDescriptorFindClassDescriptor = findClassDescriptor(name2, packageFqName);
        if (classDescriptorFindClassDescriptor != null) {
            return this.symbolTable.getDescriptorExtension().referenceClass(classDescriptorFindClassDescriptor);
        }
        return null;
    }

    public final ClassDescriptor findClassDescriptor(Name name2, FqName packageFqName) {
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(packageFqName, "packageFqName");
        ClassifierDescriptor contributedClassifier = this.builtIns.getBuiltInsModule().getPackage(packageFqName).getMemberScope().mo7465getContributedClassifier(name2, NoLookupLocation.FROM_BACKEND);
        if (contributedClassifier instanceof ClassDescriptor) {
            return (ClassDescriptor) contributedClassifier;
        }
        return null;
    }

    @Override // org.jetbrains.kotlin.ir.IrBuiltIns
    public Iterable<IrSimpleFunctionSymbol> findBuiltInClassMemberFunctions(IrClassSymbol builtInClass, Name name2) {
        Intrinsics.checkNotNullParameter(builtInClass, "builtInClass");
        Intrinsics.checkNotNullParameter(name2, "name");
        Collection<? extends SimpleFunctionDescriptor> contributedFunctions = builtInClass.getDescriptor().getUnsubstitutedMemberScope().getContributedFunctions(name2, NoLookupLocation.FROM_BACKEND);
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(contributedFunctions, 10));
        Iterator<T> it = contributedFunctions.iterator();
        while (it.hasNext()) {
            arrayList.add(this.symbolTable.getDescriptorExtension().referenceSimpleFunction((SimpleFunctionDescriptor) it.next()));
        }
        return arrayList;
    }

    @Override // org.jetbrains.kotlin.ir.IrBuiltIns
    public IrSimpleFunctionSymbol getBinaryOperator(Name name2, IrType lhsType, IrType rhsType) {
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(lhsType, "lhsType");
        Intrinsics.checkNotNullParameter(rhsType, "rhsType");
        if (!(lhsType instanceof IrSimpleType)) {
            throw new IllegalArgumentException(("Expected IrSimpleType in getBinaryOperator, got " + lhsType).toString());
        }
        IrClassifierSymbol classifier = ((IrSimpleType) lhsType).getClassifier();
        if (!(classifier instanceof IrClassSymbol) || !classifier.isBound()) {
            throw new IllegalArgumentException(("Expected a bound IrClassSymbol for lhsType in getBinaryOperator, got " + classifier).toString());
        }
        Triple<Name, IrType, IrType> triple = new Triple<>(name2, lhsType, rhsType);
        Map<Triple<Name, IrType, IrType>, IrSimpleFunctionSymbol> map = this.binaryOperatorCache;
        IrSimpleFunctionSymbol irSimpleFunctionSymbol = map.get(triple);
        if (irSimpleFunctionSymbol == null) {
            IrSimpleFunctionSymbol irSimpleFunctionSymbol2 = null;
            boolean z = false;
            for (IrSimpleFunctionSymbol irSimpleFunctionSymbol3 : IrUtilsKt.getFunctions((IrClassSymbol) classifier)) {
                IrSimpleFunction owner = irSimpleFunctionSymbol3.getOwner();
                if (Intrinsics.areEqual(owner.getName(), name2) && owner.getValueParameters().size() == 1 && Intrinsics.areEqual(owner.getValueParameters().get(0).getType(), rhsType)) {
                    if (z) {
                        throw new IllegalArgumentException("Sequence contains more than one matching element.");
                    }
                    irSimpleFunctionSymbol2 = irSimpleFunctionSymbol3;
                    z = true;
                }
            }
            if (!z) {
                throw new NoSuchElementException("Sequence contains no element matching the predicate.");
            }
            irSimpleFunctionSymbol = irSimpleFunctionSymbol2;
            map.put(triple, irSimpleFunctionSymbol);
        }
        return irSimpleFunctionSymbol;
    }

    @Override // org.jetbrains.kotlin.ir.IrBuiltIns
    public IrSimpleFunctionSymbol getUnaryOperator(Name name2, IrType receiverType) {
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(receiverType, "receiverType");
        if (!(receiverType instanceof IrSimpleType)) {
            throw new IllegalArgumentException(("Expected IrSimpleType in getBinaryOperator, got " + receiverType).toString());
        }
        IrClassifierSymbol classifier = ((IrSimpleType) receiverType).getClassifier();
        if (!(classifier instanceof IrClassSymbol) || !classifier.isBound()) {
            throw new IllegalArgumentException(("Expected a bound IrClassSymbol for receiverType in getBinaryOperator, got " + classifier).toString());
        }
        Pair<Name, IrType> pair = new Pair<>(name2, receiverType);
        Map<Pair<Name, IrType>, IrSimpleFunctionSymbol> map = this.unaryOperatorCache;
        IrSimpleFunctionSymbol irSimpleFunctionSymbol = map.get(pair);
        if (irSimpleFunctionSymbol == null) {
            IrSimpleFunctionSymbol irSimpleFunctionSymbol2 = null;
            boolean z = false;
            for (IrSimpleFunctionSymbol irSimpleFunctionSymbol3 : IrUtilsKt.getFunctions((IrClassSymbol) classifier)) {
                IrSimpleFunction owner = irSimpleFunctionSymbol3.getOwner();
                if (Intrinsics.areEqual(owner.getName(), name2) && owner.getValueParameters().isEmpty()) {
                    if (z) {
                        throw new IllegalArgumentException("Sequence contains more than one matching element.");
                    }
                    z = true;
                    irSimpleFunctionSymbol2 = irSimpleFunctionSymbol3;
                }
            }
            if (!z) {
                throw new NoSuchElementException("Sequence contains no element matching the predicate.");
            }
            irSimpleFunctionSymbol = irSimpleFunctionSymbol2;
            map.put(pair, irSimpleFunctionSymbol);
        }
        return irSimpleFunctionSymbol;
    }

    private final <T> Map<T, IrSimpleFunctionSymbol> getFunctionsByKey(Name name2, String[] packageNameSegments, Function1<? super SimpleFunctionDescriptor, ? extends T> makeKey) {
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        for (SimpleFunctionDescriptor simpleFunctionDescriptor : builtInsPackage((String[]) Arrays.copyOf(packageNameSegments, packageNameSegments.length)).getContributedFunctions(name2, NoLookupLocation.FROM_BACKEND)) {
            T tInvoke = makeKey.invoke(simpleFunctionDescriptor);
            if (tInvoke != null) {
                linkedHashMap.put(tInvoke, this.symbolTable.getDescriptorExtension().referenceSimpleFunction(simpleFunctionDescriptor));
            }
        }
        return linkedHashMap;
    }

    @Override // org.jetbrains.kotlin.ir.IrBuiltIns
    public Map<IrClassifierSymbol, IrSimpleFunctionSymbol> getNonBuiltInFunctionsByExtensionReceiver(Name name2, String... packageNameSegments) {
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(packageNameSegments, "packageNameSegments");
        return getFunctionsByKey(name2, (String[]) Arrays.copyOf(packageNameSegments, packageNameSegments.length), new Function1<SimpleFunctionDescriptor, IrClassifierSymbol>() { // from class: org.jetbrains.kotlin.psi2ir.descriptors.IrBuiltInsOverDescriptors.getNonBuiltInFunctionsByExtensionReceiver.1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final IrClassifierSymbol invoke(SimpleFunctionDescriptor it) {
                Intrinsics.checkNotNullParameter(it, "it");
                if ((it.getContainingModule() instanceof BuiltInsPackageFragment) || it.getExtensionReceiverParameter() == null) {
                    return null;
                }
                SymbolTable symbolTable = IrBuiltInsOverDescriptors.this.getSymbolTable();
                ReceiverParameterDescriptor extensionReceiverParameter = it.getExtensionReceiverParameter();
                Intrinsics.checkNotNull(extensionReceiverParameter);
                ClassifierDescriptor this$0 = extensionReceiverParameter.getType().getConstructor().getThis$0();
                Intrinsics.checkNotNull(this$0);
                return IrUtilsKt.referenceClassifier(symbolTable, this$0);
            }
        });
    }

    @Override // org.jetbrains.kotlin.ir.IrBuiltIns
    public Map<IrClassifierSymbol, IrSimpleFunctionSymbol> getNonBuiltinFunctionsByReturnType(Name name2, String... packageNameSegments) {
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(packageNameSegments, "packageNameSegments");
        Name nameIdentifier = Name.identifier("getProgressionLastElement");
        Intrinsics.checkNotNullExpressionValue(nameIdentifier, "identifier(...)");
        return getFunctionsByKey(nameIdentifier, (String[]) Arrays.copyOf(packageNameSegments, packageNameSegments.length), new Function1<SimpleFunctionDescriptor, IrClassifierSymbol>() { // from class: org.jetbrains.kotlin.psi2ir.descriptors.IrBuiltInsOverDescriptors.getNonBuiltinFunctionsByReturnType.1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final IrClassifierSymbol invoke(SimpleFunctionDescriptor d) {
                KotlinType returnType;
                TypeConstructor constructor;
                ClassifierDescriptor this$0;
                Intrinsics.checkNotNullParameter(d, "d");
                if ((d.getContainingModule() instanceof BuiltInsPackageFragment) || (returnType = d.getReturnType()) == null || (constructor = returnType.getConstructor()) == null || (this$0 = constructor.getThis$0()) == null) {
                    return null;
                }
                return IrUtilsKt.referenceClassifier(IrBuiltInsOverDescriptors.this.getSymbolTable(), this$0);
            }
        });
    }

    @Override // org.jetbrains.kotlin.ir.IrBuiltIns
    public IrSimpleFunctionSymbol getExtensionToString() {
        return this.extensionToString;
    }

    @Override // org.jetbrains.kotlin.ir.IrBuiltIns
    public IrSimpleFunctionSymbol getMemberToString() {
        return this.memberToString;
    }

    @Override // org.jetbrains.kotlin.ir.IrBuiltIns
    public IrSimpleFunctionSymbol getExtensionStringPlus() {
        return this.extensionStringPlus;
    }

    @Override // org.jetbrains.kotlin.ir.IrBuiltIns
    public IrSimpleFunctionSymbol getMemberStringPlus() {
        return this.memberStringPlus;
    }

    @Override // org.jetbrains.kotlin.ir.IrBuiltIns
    public IrClass functionN(int arity) {
        return getFunctionFactory().functionN(arity);
    }

    @Override // org.jetbrains.kotlin.ir.IrBuiltIns
    public IrClass kFunctionN(int arity) {
        return getFunctionFactory().kFunctionN(arity);
    }

    @Override // org.jetbrains.kotlin.ir.IrBuiltIns
    public IrClass suspendFunctionN(int arity) {
        return getFunctionFactory().suspendFunctionN(arity);
    }

    @Override // org.jetbrains.kotlin.ir.IrBuiltIns
    public IrClass kSuspendFunctionN(int arity) {
        return getFunctionFactory().kSuspendFunctionN(arity);
    }
}
