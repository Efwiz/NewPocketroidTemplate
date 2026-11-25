package org.jetbrains.kotlin.backend.jvm.ir;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.huawei.hms.ml.language.common.utils.Constant;
import com.reandroid.arsc.value.CompoundEntry;
import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref;
import kotlin.jvm.internal.SpreadBuilder;
import kotlin.text.StringsKt;
import net.bytebuddy.implementation.Implementation;
import org.antlr.stringtemplate.language.ASTExpr;
import org.jetbrains.kotlin.backend.common.lower.LowerUtilsKt;
import org.jetbrains.kotlin.backend.jvm.CachedFieldsForObjectInstances;
import org.jetbrains.kotlin.backend.jvm.JvmBackendContext;
import org.jetbrains.kotlin.backend.jvm.JvmLoweredDeclarationOrigin;
import org.jetbrains.kotlin.backend.jvm.JvmSymbols;
import org.jetbrains.kotlin.backend.jvm.MfvcNodeKt;
import org.jetbrains.kotlin.codegen.AsmUtil;
import org.jetbrains.kotlin.codegen.AssertCodegenUtilKt;
import org.jetbrains.kotlin.codegen.CommonVariableAsmNameManglingUtils;
import org.jetbrains.kotlin.codegen.InlineClassesCodegenUtilKt;
import org.jetbrains.kotlin.codegen.inline.coroutines.CoroutineTransformerKt;
import org.jetbrains.kotlin.codegen.state.GenerationState;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.config.JvmDefaultMode;
import org.jetbrains.kotlin.config.LanguageFeature;
import org.jetbrains.kotlin.descriptors.ClassKind;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptorWithSource;
import org.jetbrains.kotlin.descriptors.DescriptorVisibilities;
import org.jetbrains.kotlin.descriptors.DescriptorVisibility;
import org.jetbrains.kotlin.descriptors.Modality;
import org.jetbrains.kotlin.descriptors.SourceElement;
import org.jetbrains.kotlin.descriptors.deserialization.PlatformDependentDeclarationFilterKt;
import org.jetbrains.kotlin.ir.IrBuiltIns;
import org.jetbrains.kotlin.ir.IrElement;
import org.jetbrains.kotlin.ir.IrFileEntry;
import org.jetbrains.kotlin.ir.PsiIrFileEntry;
import org.jetbrains.kotlin.ir.builders.ExpressionHelpersKt;
import org.jetbrains.kotlin.ir.builders.IrBuilderWithScope;
import org.jetbrains.kotlin.ir.builders.declarations.DeclarationBuildersKt;
import org.jetbrains.kotlin.ir.builders.declarations.IrFieldBuilder;
import org.jetbrains.kotlin.ir.builders.declarations.IrPropertyBuilder;
import org.jetbrains.kotlin.ir.declarations.IrAnonymousInitializer;
import org.jetbrains.kotlin.ir.declarations.IrAttributeContainer;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrConstructor;
import org.jetbrains.kotlin.ir.declarations.IrDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationBase;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationOrigin;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationParent;
import org.jetbrains.kotlin.ir.declarations.IrEnumEntry;
import org.jetbrains.kotlin.ir.declarations.IrErrorDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrExternalPackageFragment;
import org.jetbrains.kotlin.ir.declarations.IrFactory;
import org.jetbrains.kotlin.ir.declarations.IrField;
import org.jetbrains.kotlin.ir.declarations.IrFile;
import org.jetbrains.kotlin.ir.declarations.IrFunction;
import org.jetbrains.kotlin.ir.declarations.IrLocalDelegatedProperty;
import org.jetbrains.kotlin.ir.declarations.IrMemberWithContainerSource;
import org.jetbrains.kotlin.ir.declarations.IrModuleFragment;
import org.jetbrains.kotlin.ir.declarations.IrPackageFragment;
import org.jetbrains.kotlin.ir.declarations.IrProperty;
import org.jetbrains.kotlin.ir.declarations.IrScript;
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction;
import org.jetbrains.kotlin.ir.declarations.IrTypeAlias;
import org.jetbrains.kotlin.ir.declarations.IrTypeParameter;
import org.jetbrains.kotlin.ir.declarations.IrValueParameter;
import org.jetbrains.kotlin.ir.declarations.IrVariable;
import org.jetbrains.kotlin.ir.declarations.lazy.IrLazyClass;
import org.jetbrains.kotlin.ir.declarations.lazy.IrMaybeDeserializedClass;
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
import org.jetbrains.kotlin.ir.expressions.IrConstKind;
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
import org.jetbrains.kotlin.ir.expressions.IrTypeOperator;
import org.jetbrains.kotlin.ir.expressions.IrTypeOperatorCall;
import org.jetbrains.kotlin.ir.expressions.IrValueAccessExpression;
import org.jetbrains.kotlin.ir.expressions.IrVararg;
import org.jetbrains.kotlin.ir.expressions.IrWhen;
import org.jetbrains.kotlin.ir.expressions.IrWhileLoop;
import org.jetbrains.kotlin.ir.expressions.impl.IrCallImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrClassReferenceImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrConstructorCallImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrGetFieldImpl;
import org.jetbrains.kotlin.ir.symbols.IrFunctionSymbol;
import org.jetbrains.kotlin.ir.symbols.IrPropertySymbol;
import org.jetbrains.kotlin.ir.symbols.IrSimpleFunctionSymbol;
import org.jetbrains.kotlin.ir.symbols.IrValueSymbol;
import org.jetbrains.kotlin.ir.symbols.IrVariableSymbol;
import org.jetbrains.kotlin.ir.types.IrSimpleType;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.ir.types.IrTypeUtilsKt;
import org.jetbrains.kotlin.ir.types.IrTypesKt;
import org.jetbrains.kotlin.ir.util.AdditionalIrUtilsKt;
import org.jetbrains.kotlin.ir.util.DumpIrTreeKt;
import org.jetbrains.kotlin.ir.util.DumpIrTreeOptions;
import org.jetbrains.kotlin.ir.util.IrFakeOverrideUtilsKt;
import org.jetbrains.kotlin.ir.util.IrUtilsKt;
import org.jetbrains.kotlin.ir.util.RenderIrElementKt;
import org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid;
import org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoidKt;
import org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid;
import org.jetbrains.kotlin.load.java.JavaDescriptorVisibilities;
import org.jetbrains.kotlin.load.java.JvmAbi;
import org.jetbrains.kotlin.load.kotlin.FacadeClassSource;
import org.jetbrains.kotlin.load.kotlin.JvmPackagePartSource;
import org.jetbrains.kotlin.metadata.ProtoBuf;
import org.jetbrains.kotlin.metadata.jvm.deserialization.JvmProtoBufUtil;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.JvmNames;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.psi.KtFile;
import org.jetbrains.kotlin.resolve.DescriptorUtils;
import org.jetbrains.kotlin.resolve.jvm.AsmTypes;
import org.jetbrains.kotlin.resolve.multiplatform.OptionalAnnotationUtil;
import org.jetbrains.kotlin.resolve.source.PsiSourceElement;
import org.jetbrains.kotlin.serialization.deserialization.descriptors.DeserializedContainerSource;
import org.jetbrains.kotlin.utils.DFS;
import org.jetbrains.org.objectweb.asm.Type;
import org.jetbrains.org.objectweb.asm.commons.Method;

/* JADX INFO: compiled from: JvmIrUtils.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000 \u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u000b\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u000e\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a%\u0010)\u001a\u0004\u0018\u00010\u000f2\u0006\u0010\u0000\u001a\u00020\u00012\u0006\u0010*\u001a\u00020\u001c2\u0006\u0010+\u001a\u00020,¢\u0006\u0002\u0010-\u001a\u001e\u0010.\u001a\u00020/2\u0006\u00100\u001a\u00020/2\u0006\u00101\u001a\u0002022\u0006\u00103\u001a\u000204\u001a\u000e\u00105\u001a\u0002062\u0006\u00103\u001a\u000204\u001a\u001e\u00107\u001a\u0002022\u0006\u0010*\u001a\u0002022\u0006\u00108\u001a\u00020\u000f2\u0006\u00109\u001a\u00020:\u001a\u0016\u0010;\u001a\u00020<2\u0006\u0010=\u001a\u0002022\u0006\u0010>\u001a\u000202\u001a\u001a\u0010?\u001a\u00020@*\u00020\u00022\u0006\u0010A\u001a\u00020,2\u0006\u0010B\u001a\u00020\u0002\u001a\u0010\u0010C\u001a\b\u0012\u0002\b\u0003\u0018\u00010D*\u00020@\u001a\u0012\u0010E\u001a\u00020F*\u0002022\u0006\u0010G\u001a\u000202\u001a&\u0010H\u001a\u00020F*\b\u0012\u0004\u0012\u00020I0&2\f\u00100\u001a\b\u0012\u0004\u0012\u00020I0&2\u0006\u00103\u001a\u000204\u001a\u0012\u0010J\u001a\u00020K*\u00020\u001c2\u0006\u0010L\u001a\u00020M\u001a\u0012\u0010N\u001a\u000202*\u00020\u00022\u0006\u0010+\u001a\u00020,\u001a\u0016\u0010O\u001a\u00020\u000f*\u0006\u0012\u0002\b\u00030&2\u0006\u0010P\u001a\u00020Q\u001a\u0012\u0010R\u001a\u000206*\u00020S2\u0006\u0010+\u001a\u00020,\u001a\n\u0010T\u001a\u00020Q*\u00020\u0007\u001a\u0016\u0010U\u001a\u00020Q*\u0006\u0012\u0002\b\u00030&2\u0006\u0010P\u001a\u00020Q\u001a\u001d\u0010V\u001a\u0004\u0018\u00010Q*\u0006\u0012\u0002\b\u00030&2\u0006\u0010P\u001a\u00020Q¢\u0006\u0002\u0010W\u001a\f\u0010X\u001a\u0004\u0018\u00010Y*\u00020\u0006\u001a\f\u0010Z\u001a\u0004\u0018\u00010K*\u00020\u0007\u001a\n\u0010[\u001a\u00020\\*\u00020\u001c\u001a\f\u0010]\u001a\u0004\u0018\u00010^*\u00020\u0006\u001a\f\u0010_\u001a\u0004\u0018\u000102*\u00020\u0002\u001a\u0016\u0010`\u001a\u00020K*\u0006\u0012\u0002\b\u00030&2\u0006\u0010P\u001a\u00020Q\u001a\u0012\u0010a\u001a\u00020\u000f*\u00020\u00022\u0006\u0010+\u001a\u00020,\u001a&\u0010b\u001a\u00020\u000f*\u00020c2\u0014\b\u0004\u0010d\u001a\u000e\u0012\u0004\u0012\u00020c\u0012\u0004\u0012\u00020\u000f0eH\u0086\bø\u0001\u0000\u001a\f\u0010f\u001a\u00020\u000f*\u00020\u0002H\u0002\u001a\n\u0010g\u001a\u00020\u000f*\u00020\u001c\u001a\n\u0010h\u001a\u00020\u000f*\u00020\u0002\u001a\n\u0010i\u001a\u00020\u000f*\u00020\u0002\u001a\n\u0010j\u001a\u00020\u000f*\u00020\u001c\u001a\u0012\u0010k\u001a\u00020\u000f*\u00020@2\u0006\u0010+\u001a\u00020,\u001a\n\u0010l\u001a\u00020\u000f*\u00020\u001c\u001a\u0012\u0010m\u001a\u00020\u000f*\u0002022\u0006\u00109\u001a\u00020:\u001a\n\u0010n\u001a\u00020\u000f*\u00020\u0002\u001a\f\u0010o\u001a\u00020\u000f*\u000202H\u0002\u001a\n\u0010p\u001a\u00020\u000f*\u00020\u0007\u001a\u0012\u0010q\u001a\u00020\u000f*\u0002022\u0006\u00109\u001a\u00020:\u001a\f\u0010r\u001a\u00020\u000f*\u000202H\u0002\u001a\n\u0010s\u001a\u00020\u000f*\u00020t\u001a\u0012\u0010u\u001a\u00020\u000f*\u00020\u001c2\u0006\u00109\u001a\u00020:\u001a\u0012\u0010v\u001a\u00020\u000f*\u00020w2\u0006\u0010+\u001a\u00020,\u001a\u0012\u0010x\u001a\u00020/*\u00020y2\u0006\u0010z\u001a\u000206\u001a\u0012\u0010{\u001a\u00020|*\u00020}2\u0006\u0010z\u001a\u000206\u001a\u0012\u0010~\u001a\u00020/*\u00020y2\u0006\u0010{\u001a\u00020w\u001a\r\u0010\u007f\u001a\u00030\u0080\u0001*\u00020,H\u0002\u001a\u0015\u0010\u0081\u0001\u001a\u00020\u000f*\u00030\u0082\u00012\u0007\u0010\u0083\u0001\u001a\u000202\u001a\u0013\u0010\u0084\u0001\u001a\u000206*\u00020\u00022\u0006\u0010+\u001a\u00020,\u001a\u0013\u0010\u0085\u0001\u001a\t\u0012\u0004\u0012\u00020w0\u0086\u0001*\u00030\u0087\u0001\u001a(\u0010\u0088\u0001\u001a\u00020F*\u00020c2\b\u0010\u0089\u0001\u001a\u00030\u008a\u00012\u0007\u0010\u008b\u0001\u001a\u00020\u00022\b\u0010\u008c\u0001\u001a\u00030\u008d\u0001\u001a\u000b\u0010\u008e\u0001\u001a\u00020\u001c*\u00020\u001c\u001a\u000b\u0010\u008e\u0001\u001a\u000202*\u000202\"\u001a\u0010\u0000\u001a\u0004\u0018\u00010\u0001*\u00020\u00028BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u0003\u0010\u0004\"\u0015\u0010\u0005\u001a\u00020\u0006*\u00020\u00078F¢\u0006\u0006\u001a\u0004\b\b\u0010\t\"\u001d\u0010\n\u001a\u0004\u0018\u00010\u0006*\u00020\u00078F¢\u0006\f\u0012\u0004\b\u000b\u0010\f\u001a\u0004\b\r\u0010\t\"\u0015\u0010\u000e\u001a\u00020\u000f*\u00020\u00028F¢\u0006\u0006\u001a\u0004\b\u000e\u0010\u0010\"\u0015\u0010\u0011\u001a\u00020\u000f*\u00020\u00028F¢\u0006\u0006\u001a\u0004\b\u0011\u0010\u0010\"\u0015\u0010\u0012\u001a\u00020\u000f*\u00020\u00078F¢\u0006\u0006\u001a\u0004\b\u0012\u0010\u0013\"\u0015\u0010\u0014\u001a\u00020\u000f*\u00020\u00078F¢\u0006\u0006\u001a\u0004\b\u0014\u0010\u0013\"\u0015\u0010\u0015\u001a\u00020\u000f*\u00020\u00078F¢\u0006\u0006\u001a\u0004\b\u0015\u0010\u0013\"\u0015\u0010\u0016\u001a\u00020\u000f*\u00020\u00028F¢\u0006\u0006\u001a\u0004\b\u0016\u0010\u0010\"\u0017\u0010\u0017\u001a\u0004\u0018\u00010\u0001*\u00020\u00188F¢\u0006\u0006\u001a\u0004\b\u0019\u0010\u001a\"\u0015\u0010\u001b\u001a\u00020\u0007*\u00020\u001c8F¢\u0006\u0006\u001a\u0004\b\u001d\u0010\u001e\"\u0017\u0010\u001f\u001a\u0004\u0018\u00010 *\u00020!8F¢\u0006\u0006\u001a\u0004\b\"\u0010#\"\u001d\u0010\u001f\u001a\u0004\u0018\u00010 *\u00020\u00078F¢\u0006\f\u0012\u0004\b$\u0010\f\u001a\u0004\b\"\u0010%\"!\u0010\u001f\u001a\u0004\u0018\u00010 *\u0006\u0012\u0002\b\u00030&8F¢\u0006\f\u0012\u0004\b$\u0010'\u001a\u0004\b\"\u0010(\u0082\u0002\u0007\n\u0005\b\u009920\u0001¨\u0006\u008f\u0001"}, d2 = {"classId", "Lorg/jetbrains/kotlin/name/ClassId;", "Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "getClassId", "(Lorg/jetbrains/kotlin/ir/declarations/IrClass;)Lorg/jetbrains/kotlin/name/ClassId;", "fileParent", "Lorg/jetbrains/kotlin/ir/declarations/IrFile;", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;", "getFileParent", "(Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;)Lorg/jetbrains/kotlin/ir/declarations/IrFile;", "fileParentOrNull", "getFileParentOrNull$annotations", "(Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;)V", "getFileParentOrNull", "isJvmInterface", "", "(Lorg/jetbrains/kotlin/ir/declarations/IrClass;)Z", "isOptionalAnnotationClass", "isStaticInlineClassReplacement", "(Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;)Z", "isStaticMultiFieldValueClassReplacement", "isStaticValueClassReplacement", "isSyntheticSingleton", "parentClassId", "Lorg/jetbrains/kotlin/ir/declarations/IrMemberWithContainerSource;", "getParentClassId", "(Lorg/jetbrains/kotlin/ir/declarations/IrMemberWithContainerSource;)Lorg/jetbrains/kotlin/name/ClassId;", "propertyIfAccessor", "Lorg/jetbrains/kotlin/ir/declarations/IrFunction;", "getPropertyIfAccessor", "(Lorg/jetbrains/kotlin/ir/declarations/IrFunction;)Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;", "psiElement", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptorWithSource;", "getPsiElement", "(Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptorWithSource;)Lcom/intellij/psi/PsiElement;", "getPsiElement$annotations", "(Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;)Lcom/intellij/psi/PsiElement;", "Lorg/jetbrains/kotlin/ir/expressions/IrMemberAccessExpression;", "(Lorg/jetbrains/kotlin/ir/expressions/IrMemberAccessExpression;)V", "(Lorg/jetbrains/kotlin/ir/expressions/IrMemberAccessExpression;)Lcom/intellij/psi/PsiElement;", "classFileContainsMethod", "function", "context", "Lorg/jetbrains/kotlin/backend/jvm/JvmBackendContext;", "(Lorg/jetbrains/kotlin/name/ClassId;Lorg/jetbrains/kotlin/ir/declarations/IrFunction;Lorg/jetbrains/kotlin/backend/jvm/JvmBackendContext;)Ljava/lang/Boolean;", "createDelegatingCallWithPlaceholderTypeArguments", "Lorg/jetbrains/kotlin/ir/expressions/IrCall;", "existingCall", "redirectTarget", "Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;", "irBuiltIns", "Lorg/jetbrains/kotlin/ir/IrBuiltIns;", "createPlaceholderAnyNType", "Lorg/jetbrains/kotlin/ir/types/IrType;", "findSuperDeclaration", "isSuperCall", "jvmDefaultMode", "Lorg/jetbrains/kotlin/config/JvmDefaultMode;", "firstSuperMethodFromKotlin", "Lorg/jetbrains/kotlin/ir/symbols/IrSimpleFunctionSymbol;", "override", "implementation", "buildAssertionsDisabledField", "Lorg/jetbrains/kotlin/ir/declarations/IrField;", "backendContext", "topLevelClass", "constantValue", "Lorg/jetbrains/kotlin/ir/expressions/IrConst;", "copyCorrespondingPropertyFrom", "", FirebaseAnalytics.Param.SOURCE, "copyFromWithPlaceholderTypeArguments", "Lorg/jetbrains/kotlin/ir/symbols/IrFunctionSymbol;", "extensionReceiverName", "", "state", "Lorg/jetbrains/kotlin/codegen/state/GenerationState;", "findEnumValuesFunction", "getBooleanConstArgument", ASTExpr.DEFAULT_INDEX_VARIABLE_NAME, "", "getCallableReferenceOwnerKClassType", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationParent;", "getCallableReferenceTopLevelFlag", "getIntConstArgument", "getIntConstArgumentOrNull", "(Lorg/jetbrains/kotlin/ir/expressions/IrMemberAccessExpression;I)Ljava/lang/Integer;", "getIoFile", "Ljava/io/File;", "getJvmNameFromAnnotation", "getJvmVisibilityOfDefaultArgumentStub", "Lorg/jetbrains/kotlin/descriptors/DescriptorVisibility;", "getKtFile", "Lorg/jetbrains/kotlin/psi/KtFile;", "getSingleAbstractMethod", "getStringConstArgument", "hasAssertionsDisabledField", "hasChild", "Lorg/jetbrains/kotlin/ir/IrElement;", "block", "Lkotlin/Function1;", "hasEnumEntriesFunction", "hasJvmDefault", "hasJvmDefaultNoCompatibilityAnnotation", "hasJvmDefaultWithCompatibilityAnnotation", "hasPlatformDependent", "isAssertionsDisabledField", "isBridge", "isCompiledToJvmDefault", "isEnumClassWhichRequiresExternalEntries", "isGetEntries", "isInCurrentModule", "isJvmAbstract", "isOrOverridesDefaultParameterStub", "isRawType", "Lorg/jetbrains/kotlin/ir/types/IrSimpleType;", "isSimpleFunctionCompiledToJvmDefault", "isSmartcastFromHigherThanNullable", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "javaClassReference", "Lorg/jetbrains/kotlin/backend/jvm/ir/JvmIrBuilder;", "classType", "kClassReference", "Lorg/jetbrains/kotlin/ir/expressions/IrClassReference;", "Lorg/jetbrains/kotlin/ir/builders/IrBuilderWithScope;", "kClassToJavaClass", "makeRawTypeAnnotation", "Lorg/jetbrains/kotlin/ir/expressions/impl/IrConstructorCallImpl;", "needsAccessor", "Lorg/jetbrains/kotlin/ir/declarations/IrProperty;", Implementation.Context.Default.ACCESSOR_METHOD_SUFFIX, "rawType", "receiverAndArgs", "", "Lorg/jetbrains/kotlin/ir/expressions/IrFunctionAccessExpression;", "replaceThisByStaticReference", "cachedFields", "Lorg/jetbrains/kotlin/backend/jvm/CachedFieldsForObjectInstances;", "irClass", "oldThisReceiverParameter", "Lorg/jetbrains/kotlin/ir/declarations/IrValueParameter;", "suspendFunctionOriginal", "backend.jvm"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class JvmIrUtilsKt {
    public static final IrFile getFileParent(IrDeclaration irDeclaration) {
        Intrinsics.checkNotNullParameter(irDeclaration, "<this>");
        IrFile fileParentOrNull = getFileParentOrNull(irDeclaration);
        if (fileParentOrNull != null) {
            return fileParentOrNull;
        }
        throw new IllegalStateException(("No file parent: " + irDeclaration).toString());
    }

    public static /* synthetic */ void getFileParentOrNull$annotations(IrDeclaration irDeclaration) {
    }

    public static /* synthetic */ void getPsiElement$annotations(IrDeclaration irDeclaration) {
    }

    public static /* synthetic */ void getPsiElement$annotations(IrMemberAccessExpression irMemberAccessExpression) {
    }

    public static final String getJvmNameFromAnnotation(IrDeclaration irDeclaration) {
        Intrinsics.checkNotNullParameter(irDeclaration, "<this>");
        FqName JVM_NAME = DescriptorUtils.JVM_NAME;
        Intrinsics.checkNotNullExpressionValue(JVM_NAME, "JVM_NAME");
        IrConstructorCall annotation = IrUtilsKt.getAnnotation(irDeclaration, JVM_NAME);
        IrExpression valueArgument = annotation != null ? annotation.getValueArgument(0) : null;
        IrConst irConst = valueArgument instanceof IrConst ? (IrConst) valueArgument : null;
        if (irConst == null) {
            return null;
        }
        Object value = irConst.getValue();
        String str = value instanceof String ? (String) value : null;
        if (str == null) {
            return null;
        }
        IrDeclarationOrigin origin = irDeclaration.getOrigin();
        if (Intrinsics.areEqual(origin, IrDeclarationOrigin.FUNCTION_FOR_DEFAULT_PARAMETER.INSTANCE)) {
            return str + "$default";
        }
        return Intrinsics.areEqual(origin, JvmLoweredDeclarationOrigin.FOR_INLINE_STATE_MACHINE_TEMPLATE.INSTANCE) ? true : Intrinsics.areEqual(origin, JvmLoweredDeclarationOrigin.FOR_INLINE_STATE_MACHINE_TEMPLATE_CAPTURES_CROSSINLINE.INSTANCE) ? str + CoroutineTransformerKt.FOR_INLINE_SUFFIX : str;
    }

    public static final IrDeclaration getPropertyIfAccessor(IrFunction irFunction) {
        IrPropertySymbol correspondingPropertySymbol;
        IrProperty owner;
        Intrinsics.checkNotNullParameter(irFunction, "<this>");
        IrSimpleFunction irSimpleFunction = irFunction instanceof IrSimpleFunction ? (IrSimpleFunction) irFunction : null;
        return (irSimpleFunction == null || (correspondingPropertySymbol = irSimpleFunction.getCorrespondingPropertySymbol()) == null || (owner = correspondingPropertySymbol.getOwner()) == null) ? irFunction : owner;
    }

    public static final boolean isSimpleFunctionCompiledToJvmDefault(IrFunction irFunction, JvmDefaultMode jvmDefaultMode) {
        Intrinsics.checkNotNullParameter(irFunction, "<this>");
        Intrinsics.checkNotNullParameter(jvmDefaultMode, "jvmDefaultMode");
        IrSimpleFunction irSimpleFunction = irFunction instanceof IrSimpleFunction ? (IrSimpleFunction) irFunction : null;
        return irSimpleFunction != null && isCompiledToJvmDefault(irSimpleFunction, jvmDefaultMode);
    }

    public static final boolean isCompiledToJvmDefault(IrSimpleFunction irSimpleFunction, JvmDefaultMode jvmDefaultMode) {
        Intrinsics.checkNotNullParameter(irSimpleFunction, "<this>");
        Intrinsics.checkNotNullParameter(jvmDefaultMode, "jvmDefaultMode");
        if (!irSimpleFunction.getIsFakeOverride() && IrUtilsKt.isInterface(IrUtilsKt.getParentAsClass(irSimpleFunction))) {
            irSimpleFunction.getModality();
            Modality modality = Modality.ABSTRACT;
        }
        if (Intrinsics.areEqual(irSimpleFunction.getOrigin(), IrDeclarationOrigin.IR_EXTERNAL_JAVA_DECLARATION_STUB.INSTANCE)) {
            return false;
        }
        if (hasJvmDefault(irSimpleFunction)) {
            return true;
        }
        IrElement parentAsClass = IrUtilsKt.getParentAsClass(irSimpleFunction);
        if (parentAsClass instanceof IrLazyClass) {
            ProtoBuf.Class classProto = ((IrLazyClass) parentAsClass).getClassProto();
            if (classProto != null) {
                return JvmProtoBufUtil.isNewPlaceForBodyGeneration(classProto);
            }
        } else if (parentAsClass instanceof IrMaybeDeserializedClass) {
            return ((IrMaybeDeserializedClass) parentAsClass).isNewPlaceForBodyGeneration();
        }
        return jvmDefaultMode.getForAllMethodsWithBody();
    }

    public static final boolean hasJvmDefault(IrFunction irFunction) {
        Intrinsics.checkNotNullParameter(irFunction, "<this>");
        return IrUtilsKt.hasAnnotation(getPropertyIfAccessor(irFunction), JvmNames.INSTANCE.getJVM_DEFAULT_FQ_NAME());
    }

    public static final boolean hasJvmDefaultNoCompatibilityAnnotation(IrClass irClass) {
        Intrinsics.checkNotNullParameter(irClass, "<this>");
        return IrUtilsKt.hasAnnotation(irClass, JvmNames.INSTANCE.getJVM_DEFAULT_NO_COMPATIBILITY_FQ_NAME());
    }

    public static final boolean hasJvmDefaultWithCompatibilityAnnotation(IrClass irClass) {
        Intrinsics.checkNotNullParameter(irClass, "<this>");
        return IrUtilsKt.hasAnnotation(irClass, JvmNames.INSTANCE.getJVM_DEFAULT_WITH_COMPATIBILITY_FQ_NAME());
    }

    public static final boolean hasPlatformDependent(IrFunction irFunction) {
        Intrinsics.checkNotNullParameter(irFunction, "<this>");
        return IrUtilsKt.hasAnnotation(getPropertyIfAccessor(irFunction), PlatformDependentDeclarationFilterKt.getPLATFORM_DEPENDENT_ANNOTATION_FQ_NAME());
    }

    public static final DescriptorVisibility getJvmVisibilityOfDefaultArgumentStub(IrFunction irFunction) {
        Intrinsics.checkNotNullParameter(irFunction, "<this>");
        DescriptorVisibility descriptorVisibility = (DescriptorVisibilities.isPrivate(irFunction.getVisibility()) || JvmIrInlineUtilsKt.isInlineOnly(irFunction)) ? JavaDescriptorVisibilities.PACKAGE_VISIBILITY : DescriptorVisibilities.PUBLIC;
        Intrinsics.checkNotNull(descriptorVisibility);
        return descriptorVisibility;
    }

    public static final boolean isInCurrentModule(IrDeclaration irDeclaration) {
        Intrinsics.checkNotNullParameter(irDeclaration, "<this>");
        return IrUtilsKt.getPackageFragment(irDeclaration) instanceof IrFile;
    }

    public static final boolean isSmartcastFromHigherThanNullable(IrExpression irExpression, JvmBackendContext context) {
        IrVariable owner;
        IrExpression initializer;
        Intrinsics.checkNotNullParameter(irExpression, "<this>");
        Intrinsics.checkNotNullParameter(context, "context");
        if (irExpression instanceof IrTypeOperatorCall) {
            IrTypeOperatorCall irTypeOperatorCall = (IrTypeOperatorCall) irExpression;
            if (irTypeOperatorCall.getOperator() == IrTypeOperator.IMPLICIT_CAST && !IrTypeUtilsKt.isSubtypeOf(irTypeOperatorCall.getArgument().getType(), IrTypesKt.makeNullable(irExpression.getType()), context.getTypeSystem())) {
                return true;
            }
        } else if (irExpression instanceof IrGetValue) {
            IrValueSymbol symbol = ((IrGetValue) irExpression).getSymbol();
            IrVariableSymbol irVariableSymbol = symbol instanceof IrVariableSymbol ? (IrVariableSymbol) symbol : null;
            if (irVariableSymbol == null || (owner = irVariableSymbol.getOwner()) == null) {
                return false;
            }
            if (!owner.getIsVar() && (initializer = owner.getInitializer()) != null && isSmartcastFromHigherThanNullable(initializer, context)) {
                return true;
            }
        }
        return false;
    }

    public static final void replaceThisByStaticReference(IrElement irElement, final CachedFieldsForObjectInstances cachedFields, final IrClass irClass, final IrValueParameter oldThisReceiverParameter) {
        Intrinsics.checkNotNullParameter(irElement, "<this>");
        Intrinsics.checkNotNullParameter(cachedFields, "cachedFields");
        Intrinsics.checkNotNullParameter(irClass, "irClass");
        Intrinsics.checkNotNullParameter(oldThisReceiverParameter, "oldThisReceiverParameter");
        IrElementTransformerVoidKt.transformChildrenVoid(irElement, new IrElementTransformerVoid() { // from class: org.jetbrains.kotlin.backend.jvm.ir.JvmIrUtilsKt.replaceThisByStaticReference.1
            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
            public IrExpression visitGetValue(IrGetValue expression) {
                Intrinsics.checkNotNullParameter(expression, "expression");
                if (Intrinsics.areEqual(expression.getSymbol(), oldThisReceiverParameter.getSymbol())) {
                    return new IrGetFieldImpl(expression.getStartOffset(), expression.getEndOffset(), cachedFields.getPrivateFieldForObjectInstance(irClass).getSymbol(), IrUtilsKt.getDefaultType(irClass), null, null, 48, null);
                }
                return super.visitGetValue(expression);
            }
        });
    }

    public static final IrType createPlaceholderAnyNType(IrBuiltIns irBuiltIns) {
        Intrinsics.checkNotNullParameter(irBuiltIns, "irBuiltIns");
        return irBuiltIns.getAnyNType();
    }

    public static final IrCall createDelegatingCallWithPlaceholderTypeArguments(IrCall existingCall, IrSimpleFunction redirectTarget, IrBuiltIns irBuiltIns) {
        Intrinsics.checkNotNullParameter(existingCall, "existingCall");
        Intrinsics.checkNotNullParameter(redirectTarget, "redirectTarget");
        Intrinsics.checkNotNullParameter(irBuiltIns, "irBuiltIns");
        IrCallImpl irCallImpl = new IrCallImpl(existingCall.getStartOffset(), existingCall.getEndOffset(), existingCall.getType(), redirectTarget.getSymbol(), redirectTarget.getTypeParameters().size(), redirectTarget.getValueParameters().size(), existingCall.getOrigin(), null, 128, null);
        copyFromWithPlaceholderTypeArguments(irCallImpl, existingCall, irBuiltIns);
        return irCallImpl;
    }

    public static final void copyFromWithPlaceholderTypeArguments(IrMemberAccessExpression<IrFunctionSymbol> irMemberAccessExpression, IrMemberAccessExpression<IrFunctionSymbol> existingCall, IrBuiltIns irBuiltIns) {
        Intrinsics.checkNotNullParameter(irMemberAccessExpression, "<this>");
        Intrinsics.checkNotNullParameter(existingCall, "existingCall");
        Intrinsics.checkNotNullParameter(irBuiltIns, "irBuiltIns");
        IrUtilsKt.copyValueArgumentsFrom(irMemberAccessExpression, existingCall, ((IrFunctionSymbol) irMemberAccessExpression.getSymbol()).getOwner(), true, false);
        int i = 0;
        for (IrTypeParameter irTypeParameter : IrUtilsKt.getParentAsClass(((IrFunctionSymbol) existingCall.getSymbol()).getOwner()).getTypeParameters()) {
            irMemberAccessExpression.putTypeArgument(i, createPlaceholderAnyNType(irBuiltIns));
            i++;
        }
        int typeArgumentsCount = existingCall.getTypeArgumentsCount();
        for (int i2 = 0; i2 < typeArgumentsCount; i2++) {
            irMemberAccessExpression.putTypeArgument(i2 + i, existingCall.getTypeArgument(i2));
        }
    }

    public static final boolean isJvmAbstract(IrSimpleFunction irSimpleFunction, JvmDefaultMode jvmDefaultMode) {
        Intrinsics.checkNotNullParameter(irSimpleFunction, "<this>");
        Intrinsics.checkNotNullParameter(jvmDefaultMode, "jvmDefaultMode");
        if (irSimpleFunction.getModality() == Modality.ABSTRACT) {
            return true;
        }
        boolean z = false;
        if (!isJvmInterface(IrUtilsKt.getParentAsClass(irSimpleFunction))) {
            return false;
        }
        IrSimpleFunction irSimpleFunction2 = (IrSimpleFunction) IrFakeOverrideUtilsKt.resolveFakeOverride$default(irSimpleFunction, false, null, 3, null);
        if (irSimpleFunction2 != null && (isCompiledToJvmDefault(irSimpleFunction2, jvmDefaultMode) || hasPlatformDependent(irSimpleFunction2))) {
            z = true;
        }
        return !z;
    }

    public static final IrSimpleFunctionSymbol firstSuperMethodFromKotlin(IrSimpleFunction override, IrSimpleFunction implementation) {
        Object next;
        Intrinsics.checkNotNullParameter(override, "override");
        Intrinsics.checkNotNullParameter(implementation, "implementation");
        Iterator<T> it = override.getOverriddenSymbols().iterator();
        while (true) {
            if (!it.hasNext()) {
                next = null;
                break;
            }
            next = it.next();
            IrSimpleFunction owner = ((IrSimpleFunctionSymbol) next).getOwner();
            if (owner.getModality() != Modality.ABSTRACT && AdditionalIrUtilsKt.overrides(owner, implementation)) {
                break;
            }
        }
        IrSimpleFunctionSymbol irSimpleFunctionSymbol = (IrSimpleFunctionSymbol) next;
        if (irSimpleFunctionSymbol != null) {
            return irSimpleFunctionSymbol;
        }
        throw new IllegalStateException(("No super method found for: " + RenderIrElementKt.render$default(override, (DumpIrTreeOptions) null, 1, (Object) null)).toString());
    }

    public static final void copyCorrespondingPropertyFrom(IrSimpleFunction irSimpleFunction, IrSimpleFunction source) {
        IrProperty owner;
        Intrinsics.checkNotNullParameter(irSimpleFunction, "<this>");
        Intrinsics.checkNotNullParameter(source, "source");
        IrPropertySymbol correspondingPropertySymbol = source.getCorrespondingPropertySymbol();
        if (correspondingPropertySymbol == null || (owner = correspondingPropertySymbol.getOwner()) == null) {
            return;
        }
        IrFactory factory = irSimpleFunction.getFactory();
        IrPropertyBuilder irPropertyBuilder = new IrPropertyBuilder();
        irPropertyBuilder.setName(owner.getName());
        irPropertyBuilder.updateFrom(owner);
        IrProperty irPropertyBuildProperty = DeclarationBuildersKt.buildProperty(factory, irPropertyBuilder);
        irPropertyBuildProperty.setParent(irSimpleFunction.getParent());
        irPropertyBuildProperty.setAnnotations(owner.getAnnotations());
        IrSimpleFunction irSimpleFunction2 = source;
        if (AdditionalIrUtilsKt.isGetter(irSimpleFunction2)) {
            irPropertyBuildProperty.setGetter(irSimpleFunction);
        } else {
            if (!AdditionalIrUtilsKt.isSetter(irSimpleFunction2)) {
                throw new IllegalStateException(("Orphaned property getter/setter: " + RenderIrElementKt.render$default(source, (DumpIrTreeOptions) null, 1, (Object) null)).toString());
            }
            irPropertyBuildProperty.setSetter(irSimpleFunction);
        }
        irSimpleFunction.setCorrespondingPropertySymbol(irPropertyBuildProperty.getSymbol());
    }

    public static final boolean needsAccessor(IrProperty irProperty, IrSimpleFunction accessor) {
        IrField backingField;
        Intrinsics.checkNotNullParameter(irProperty, "<this>");
        Intrinsics.checkNotNullParameter(accessor, "accessor");
        IrDeclarationParent parent = irProperty.getParent();
        IrClass irClass = parent instanceof IrClass ? (IrClass) parent : null;
        if ((irClass != null ? irClass.getKind() : null) == ClassKind.ANNOTATION_CLASS) {
            return true;
        }
        IrSimpleFunction irSimpleFunction = accessor;
        if (AdditionalIrUtilsKt.isGetter(irSimpleFunction) && accessor.getContextReceiverParametersCount() == 0 && accessor.getExtensionReceiverParameter() == null && MfvcNodeKt.needsMfvcFlattening(accessor.getReturnType())) {
            return true;
        }
        if (AdditionalIrUtilsKt.isSetter(irSimpleFunction) && accessor.getContextReceiverParametersCount() == 0 && accessor.getExtensionReceiverParameter() == null && MfvcNodeKt.needsMfvcFlattening(((IrValueParameter) CollectionsKt.single((List) accessor.getValueParameters())).getType())) {
            return true;
        }
        IrProperty irProperty2 = (IrProperty) IrFakeOverrideUtilsKt.resolveFakeOverride$default(irProperty, false, null, 3, null);
        return (irProperty2 == null || (backingField = irProperty2.getBackingField()) == null || !IrUtilsKt.hasAnnotation(backingField, JvmAbi.JVM_FIELD_ANNOTATION_FQ_NAME)) && !(Intrinsics.areEqual(accessor.getOrigin(), IrDeclarationOrigin.DEFAULT_PROPERTY_ACCESSOR.INSTANCE) && DescriptorVisibilities.isPrivate(accessor.getVisibility()));
    }

    public static final boolean isStaticInlineClassReplacement(IrDeclaration irDeclaration) {
        Intrinsics.checkNotNullParameter(irDeclaration, "<this>");
        return Intrinsics.areEqual(irDeclaration.getOrigin(), JvmLoweredDeclarationOrigin.STATIC_INLINE_CLASS_REPLACEMENT.INSTANCE) || Intrinsics.areEqual(irDeclaration.getOrigin(), JvmLoweredDeclarationOrigin.STATIC_INLINE_CLASS_CONSTRUCTOR.INSTANCE);
    }

    public static final boolean isStaticMultiFieldValueClassReplacement(IrDeclaration irDeclaration) {
        Intrinsics.checkNotNullParameter(irDeclaration, "<this>");
        return Intrinsics.areEqual(irDeclaration.getOrigin(), JvmLoweredDeclarationOrigin.STATIC_MULTI_FIELD_VALUE_CLASS_REPLACEMENT.INSTANCE) || Intrinsics.areEqual(irDeclaration.getOrigin(), JvmLoweredDeclarationOrigin.STATIC_MULTI_FIELD_VALUE_CLASS_CONSTRUCTOR.INSTANCE);
    }

    public static final boolean isStaticValueClassReplacement(IrDeclaration irDeclaration) {
        Intrinsics.checkNotNullParameter(irDeclaration, "<this>");
        return isStaticMultiFieldValueClassReplacement(irDeclaration) || isStaticInlineClassReplacement(irDeclaration);
    }

    private static final IrConstructorCallImpl makeRawTypeAnnotation(JvmBackendContext jvmBackendContext) {
        IrConstructorCallImpl.Companion companion = IrConstructorCallImpl.INSTANCE;
        IrConstructor rawTypeAnnotationConstructor = jvmBackendContext.getGeneratorExtensions().getRawTypeAnnotationConstructor();
        Intrinsics.checkNotNull(rawTypeAnnotationConstructor);
        IrType constructedClassType = IrUtilsKt.getConstructedClassType(rawTypeAnnotationConstructor);
        IrConstructor rawTypeAnnotationConstructor2 = jvmBackendContext.getGeneratorExtensions().getRawTypeAnnotationConstructor();
        Intrinsics.checkNotNull(rawTypeAnnotationConstructor2);
        return IrConstructorCallImpl.Companion.fromSymbolOwner$default(companion, constructedClassType, rawTypeAnnotationConstructor2.getSymbol(), null, 4, null);
    }

    public static final IrType rawType(IrClass irClass, JvmBackendContext context) {
        Intrinsics.checkNotNullParameter(irClass, "<this>");
        Intrinsics.checkNotNullParameter(context, "context");
        return IrTypesKt.addAnnotations(IrUtilsKt.getDefaultType(irClass), CollectionsKt.listOf(makeRawTypeAnnotation(context)));
    }

    public static final boolean isRawType(IrSimpleType irSimpleType) {
        Intrinsics.checkNotNullParameter(irSimpleType, "<this>");
        return IrUtilsKt.hasAnnotation(irSimpleType, JvmSymbols.INSTANCE.getRAW_TYPE_ANNOTATION_FQ_NAME());
    }

    public static final boolean isJvmInterface(IrClass irClass) {
        Intrinsics.checkNotNullParameter(irClass, "<this>");
        return IrUtilsKt.isAnnotationClass(irClass) || IrUtilsKt.isInterface(irClass);
    }

    public static final IrSimpleFunction getSingleAbstractMethod(IrClass irClass) {
        Intrinsics.checkNotNullParameter(irClass, "<this>");
        IrSimpleFunction irSimpleFunction = null;
        boolean z = false;
        IrSimpleFunction irSimpleFunction2 = null;
        for (IrSimpleFunction irSimpleFunction3 : IrUtilsKt.getFunctions(irClass)) {
            if (irSimpleFunction3.getModality() == Modality.ABSTRACT) {
                if (z) {
                    return irSimpleFunction;
                }
                z = true;
                irSimpleFunction2 = irSimpleFunction3;
            }
        }
        if (z) {
            irSimpleFunction = irSimpleFunction2;
        }
        return irSimpleFunction;
    }

    public static final KtFile getKtFile(IrFile irFile) {
        Intrinsics.checkNotNullParameter(irFile, "<this>");
        IrFileEntry fileEntry = irFile.getFileEntry();
        PsiIrFileEntry psiIrFileEntry = fileEntry instanceof PsiIrFileEntry ? (PsiIrFileEntry) fileEntry : null;
        return (KtFile) (psiIrFileEntry != null ? psiIrFileEntry.getPsiFile() : null);
    }

    public static final File getIoFile(IrFile irFile) {
        String path;
        Intrinsics.checkNotNullParameter(irFile, "<this>");
        IrFileEntry fileEntry = irFile.getFileEntry();
        if (!(fileEntry instanceof PsiIrFileEntry)) {
            return new File(fileEntry.getName());
        }
        VirtualFile virtualFile = ((PsiIrFileEntry) fileEntry).getPsiFile().getVirtualFile();
        if (virtualFile == null || (path = virtualFile.getPath()) == null) {
            return null;
        }
        return new File(path);
    }

    public static final boolean hasChild(IrElement irElement, final Function1<? super IrElement, Boolean> block) {
        Intrinsics.checkNotNullParameter(irElement, "<this>");
        Intrinsics.checkNotNullParameter(block, "block");
        final Ref.BooleanRef booleanRef = new Ref.BooleanRef();
        irElement.acceptChildren(new IrElementVisitorVoid() { // from class: org.jetbrains.kotlin.backend.jvm.ir.JvmIrUtilsKt.hasChild.1
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
            /* JADX INFO: renamed from: visitClass */
            public void mo6112visitClass(IrClass irClass) {
                IrElementVisitorVoid.DefaultImpls.visitClass(this, irClass);
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
            public /* bridge */ /* synthetic */ Object visitElement2(IrElement irElement2, Object obj) {
                visitElement(irElement2, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitElement(IrElement irElement2, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitElement(this, irElement2, r2);
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
            public /* bridge */ /* synthetic */ Object visitScript2(IrScript irScript, Object obj) {
                visitScript(irScript, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitScript(IrScript irScript) {
                IrElementVisitorVoid.DefaultImpls.visitScript(this, irScript);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitScript(IrScript irScript, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitScript(this, irScript, r2);
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
            public /* bridge */ /* synthetic */ Object visitValueParameter2(IrValueParameter irValueParameter, Object obj) {
                visitValueParameter(irValueParameter, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitValueParameter(IrValueParameter irValueParameter) {
                IrElementVisitorVoid.DefaultImpls.visitValueParameter(this, irValueParameter);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitValueParameter(IrValueParameter irValueParameter, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitValueParameter(this, irValueParameter, r2);
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
                if (booleanRef.element) {
                    return;
                }
                if (block.invoke(element).booleanValue()) {
                    booleanRef.element = true;
                } else {
                    element.acceptChildren(this, null);
                }
            }
        }, null);
        return booleanRef.element;
    }

    public static final boolean isSyntheticSingleton(IrClass irClass) {
        Intrinsics.checkNotNullParameter(irClass, "<this>");
        if (Intrinsics.areEqual(irClass.getOrigin(), JvmLoweredDeclarationOrigin.LAMBDA_IMPL.INSTANCE) || Intrinsics.areEqual(irClass.getOrigin(), JvmLoweredDeclarationOrigin.FUNCTION_REFERENCE_IMPL.INSTANCE) || Intrinsics.areEqual(irClass.getOrigin(), JvmLoweredDeclarationOrigin.GENERATED_PROPERTY_REFERENCE.INSTANCE)) {
            IrConstructor primaryConstructor = IrUtilsKt.getPrimaryConstructor(irClass);
            Intrinsics.checkNotNull(primaryConstructor);
            if (primaryConstructor.getValueParameters().isEmpty()) {
                return true;
            }
        }
        return false;
    }

    public static final IrSimpleFunction suspendFunctionOriginal(IrSimpleFunction irSimpleFunction) {
        Intrinsics.checkNotNullParameter(irSimpleFunction, "<this>");
        if (!irSimpleFunction.getIsSuspend()) {
            return irSimpleFunction;
        }
        IrSimpleFunction irSimpleFunction2 = irSimpleFunction;
        if (isStaticValueClassReplacement(irSimpleFunction2) || isOrOverridesDefaultParameterStub(irSimpleFunction)) {
            return irSimpleFunction;
        }
        IrClass parentClassOrNull = IrUtilsKt.getParentClassOrNull(irSimpleFunction2);
        if (Intrinsics.areEqual(parentClassOrNull != null ? parentClassOrNull.getOrigin() : null, JvmLoweredDeclarationOrigin.DEFAULT_IMPLS.INSTANCE)) {
            return irSimpleFunction;
        }
        IrAttributeContainer attributeOwnerId = irSimpleFunction.getAttributeOwnerId();
        Intrinsics.checkNotNull(attributeOwnerId, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.declarations.IrSimpleFunction");
        return (IrSimpleFunction) attributeOwnerId;
    }

    public static final IrFunction suspendFunctionOriginal(IrFunction irFunction) {
        IrSimpleFunction irSimpleFunctionSuspendFunctionOriginal;
        Intrinsics.checkNotNullParameter(irFunction, "<this>");
        IrSimpleFunction irSimpleFunction = irFunction instanceof IrSimpleFunction ? (IrSimpleFunction) irFunction : null;
        return (irSimpleFunction == null || (irSimpleFunctionSuspendFunctionOriginal = suspendFunctionOriginal(irSimpleFunction)) == null) ? irFunction : irSimpleFunctionSuspendFunctionOriginal;
    }

    private static final boolean isOrOverridesDefaultParameterStub(IrSimpleFunction irSimpleFunction) {
        Boolean boolIfAny = DFS.ifAny(CollectionsKt.listOf(irSimpleFunction), new DFS.Neighbors() { // from class: org.jetbrains.kotlin.backend.jvm.ir.JvmIrUtilsKt$$ExternalSyntheticLambda0
            @Override // org.jetbrains.kotlin.utils.DFS.Neighbors
            public final Iterable getNeighbors(Object obj) {
                return JvmIrUtilsKt.isOrOverridesDefaultParameterStub$lambda$10((IrSimpleFunction) obj);
            }
        }, new Function1<IrSimpleFunction, Boolean>() { // from class: org.jetbrains.kotlin.backend.jvm.ir.JvmIrUtilsKt.isOrOverridesDefaultParameterStub.2
            @Override // kotlin.jvm.functions.Function1
            public final Boolean invoke(IrSimpleFunction irSimpleFunction2) {
                return Boolean.valueOf(Intrinsics.areEqual(irSimpleFunction2.getOrigin(), IrDeclarationOrigin.FUNCTION_FOR_DEFAULT_PARAMETER.INSTANCE));
            }
        });
        Intrinsics.checkNotNullExpressionValue(boolIfAny, "ifAny(...)");
        return boolIfAny.booleanValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Iterable isOrOverridesDefaultParameterStub$lambda$10(IrSimpleFunction irSimpleFunction) {
        List<IrSimpleFunctionSymbol> overriddenSymbols = irSimpleFunction.getOverriddenSymbols();
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(overriddenSymbols, 10));
        Iterator<T> it = overriddenSymbols.iterator();
        while (it.hasNext()) {
            arrayList.add(((IrSimpleFunctionSymbol) it.next()).getOwner());
        }
        return arrayList;
    }

    public static final IrField buildAssertionsDisabledField(IrClass irClass, JvmBackendContext backendContext, IrClass topLevelClass) {
        Intrinsics.checkNotNullParameter(irClass, "<this>");
        Intrinsics.checkNotNullParameter(backendContext, "backendContext");
        Intrinsics.checkNotNullParameter(topLevelClass, "topLevelClass");
        IrFactory factory = irClass.getFactory();
        IrFieldBuilder irFieldBuilder = new IrFieldBuilder();
        Name nameIdentifier = Name.identifier(AssertCodegenUtilKt.ASSERTIONS_DISABLED_FIELD_NAME);
        Intrinsics.checkNotNullExpressionValue(nameIdentifier, "identifier(...)");
        irFieldBuilder.setName(nameIdentifier);
        irFieldBuilder.setOrigin(JvmLoweredDeclarationOrigin.GENERATED_ASSERTION_ENABLED_FIELD.INSTANCE);
        DescriptorVisibility PACKAGE_VISIBILITY = JavaDescriptorVisibilities.PACKAGE_VISIBILITY;
        Intrinsics.checkNotNullExpressionValue(PACKAGE_VISIBILITY, "PACKAGE_VISIBILITY");
        irFieldBuilder.setVisibility(PACKAGE_VISIBILITY);
        irFieldBuilder.setType(backendContext.getIrBuiltIns().getBooleanType());
        irFieldBuilder.setFinal(true);
        irFieldBuilder.setStatic(true);
        IrField irFieldBuildField = DeclarationBuildersKt.buildField(factory, irFieldBuilder);
        irFieldBuildField.setParent(irClass);
        JvmIrBuilder jvmIrBuilderCreateJvmIrBuilder$default = JvmIrBuilderKt.createJvmIrBuilder$default(backendContext, irClass.getSymbol(), 0, 0, 6, null);
        LowerUtilsKt.at(jvmIrBuilderCreateJvmIrBuilder$default, irFieldBuildField);
        JvmIrBuilder jvmIrBuilder = jvmIrBuilderCreateJvmIrBuilder$default;
        IrCall irCall = ExpressionHelpersKt.irCall((IrBuilderWithScope) jvmIrBuilder, jvmIrBuilderCreateJvmIrBuilder$default.getIrSymbols().getDesiredAssertionStatus());
        irCall.setDispatchReceiver(javaClassReference(jvmIrBuilderCreateJvmIrBuilder$default, IrUtilsKt.getDefaultType(topLevelClass)));
        Unit unit = Unit.INSTANCE;
        irFieldBuildField.setInitializer(ExpressionHelpersKt.irExprBody(jvmIrBuilder, LowerUtilsKt.irNot(jvmIrBuilder, irCall)));
        return irFieldBuildField;
    }

    public static final boolean isAssertionsDisabledField(IrField irField, JvmBackendContext context) {
        Intrinsics.checkNotNullParameter(irField, "<this>");
        Intrinsics.checkNotNullParameter(context, "context");
        return Intrinsics.areEqual(irField.getName().asString(), AssertCodegenUtilKt.ASSERTIONS_DISABLED_FIELD_NAME) && Intrinsics.areEqual(irField.getType(), context.getIrBuiltIns().getBooleanType()) && irField.getIsStatic();
    }

    public static final boolean hasAssertionsDisabledField(IrClass irClass, JvmBackendContext context) {
        Intrinsics.checkNotNullParameter(irClass, "<this>");
        Intrinsics.checkNotNullParameter(context, "context");
        Iterator<IrField> it = IrUtilsKt.getFields(irClass).iterator();
        while (it.hasNext()) {
            if (isAssertionsDisabledField(it.next(), context)) {
                return true;
            }
        }
        return false;
    }

    public static final IrConst<?> constantValue(IrField irField) {
        IrPropertySymbol correspondingPropertySymbol;
        IrProperty owner;
        Intrinsics.checkNotNullParameter(irField, "<this>");
        IrExpressionBody initializer = irField.getInitializer();
        IrExpression expression = initializer != null ? initializer.getExpression() : null;
        IrConst<?> irConst = expression instanceof IrConst ? (IrConst) expression : null;
        if (irConst == null) {
            return null;
        }
        if (!(irField.getIsFinal() && irField.getIsStatic() && Intrinsics.areEqual(irField.getOrigin(), IrDeclarationOrigin.IR_EXTERNAL_JAVA_DECLARATION_STUB.INSTANCE)) && ((correspondingPropertySymbol = irField.getCorrespondingPropertySymbol()) == null || (owner = correspondingPropertySymbol.getOwner()) == null || !owner.getIsConst())) {
            return null;
        }
        return irConst;
    }

    public static final IrClassReference kClassReference(IrBuilderWithScope irBuilderWithScope, IrType classType) {
        Intrinsics.checkNotNullParameter(irBuilderWithScope, "<this>");
        Intrinsics.checkNotNullParameter(classType, "classType");
        return new IrClassReferenceImpl(irBuilderWithScope.getStartOffset(), irBuilderWithScope.getEndOffset(), IrTypesKt.getStarProjectedType(irBuilderWithScope.getContext().getIrBuiltIns().getKClassClass()), irBuilderWithScope.getContext().getIrBuiltIns().getKClassClass(), classType);
    }

    public static final IrCall kClassToJavaClass(JvmIrBuilder jvmIrBuilder, IrExpression kClassReference) {
        Intrinsics.checkNotNullParameter(jvmIrBuilder, "<this>");
        Intrinsics.checkNotNullParameter(kClassReference, "kClassReference");
        IrCall irCallIrGet = ExpressionHelpersKt.irGet(jvmIrBuilder, IrTypesKt.getStarProjectedType(jvmIrBuilder.getIrSymbols().getJavaLangClass()), null, jvmIrBuilder.getIrSymbols().getKClassJavaPropertyGetter().getSymbol());
        irCallIrGet.setExtensionReceiver(kClassReference);
        return irCallIrGet;
    }

    public static final IrCall javaClassReference(JvmIrBuilder jvmIrBuilder, IrType classType) {
        Intrinsics.checkNotNullParameter(jvmIrBuilder, "<this>");
        Intrinsics.checkNotNullParameter(classType, "classType");
        return kClassToJavaClass(jvmIrBuilder, kClassReference(jvmIrBuilder, classType));
    }

    public static final IrType getCallableReferenceOwnerKClassType(IrDeclarationParent irDeclarationParent, JvmBackendContext context) {
        Intrinsics.checkNotNullParameter(irDeclarationParent, "<this>");
        Intrinsics.checkNotNullParameter(context, "context");
        return irDeclarationParent instanceof IrClass ? IrUtilsKt.getDefaultType((IrClass) irDeclarationParent) : IrTypesKt.getDefaultType(context.getIr().getSymbols().getIntrinsicsKotlinClass());
    }

    public static final int getCallableReferenceTopLevelFlag(IrDeclaration irDeclaration) {
        Intrinsics.checkNotNullParameter(irDeclaration, "<this>");
        IrDeclarationParent parent = irDeclaration.getParent();
        return ((parent instanceof IrClass) && IrUtilsKt.isFileClass((IrDeclaration) parent)) ? 1 : 0;
    }

    public static final IrSimpleFunction findSuperDeclaration(IrSimpleFunction function, boolean z, JvmDefaultMode jvmDefaultMode) {
        Object next;
        IrSimpleFunction irSimpleFunction;
        Intrinsics.checkNotNullParameter(function, "function");
        Intrinsics.checkNotNullParameter(jvmDefaultMode, "jvmDefaultMode");
        while (function.getIsFakeOverride()) {
            Iterator<T> it = function.getOverriddenSymbols().iterator();
            do {
                if (!it.hasNext()) {
                    next = null;
                    break;
                }
                next = it.next();
            } while (IrUtilsKt.isInterface(IrUtilsKt.getParentAsClass(((IrSimpleFunctionSymbol) next).getOwner())));
            IrSimpleFunctionSymbol irSimpleFunctionSymbol = (IrSimpleFunctionSymbol) next;
            IrSimpleFunction owner = irSimpleFunctionSymbol != null ? irSimpleFunctionSymbol.getOwner() : null;
            if (owner == null) {
                if (z && !IrUtilsKt.isInterface(IrUtilsKt.getParentAsClass(function)) && (irSimpleFunction = (IrSimpleFunction) IrFakeOverrideUtilsKt.resolveFakeOverride$default(function, false, null, 3, null)) != null && (IrUtilsKt.isMethodOfAny(irSimpleFunction) || !isCompiledToJvmDefault(irSimpleFunction, jvmDefaultMode))) {
                    return function;
                }
                IrSimpleFunctionSymbol irSimpleFunctionSymbol2 = (IrSimpleFunctionSymbol) CollectionsKt.firstOrNull((List) function.getOverriddenSymbols());
                if (irSimpleFunctionSymbol2 == null || (owner = irSimpleFunctionSymbol2.getOwner()) == null) {
                    throw new IllegalStateException(("Fake override should have at least one overridden descriptor: " + RenderIrElementKt.render$default(function, (DumpIrTreeOptions) null, 1, (Object) null)).toString());
                }
            }
            function = owner;
        }
        return function;
    }

    public static final Integer getIntConstArgumentOrNull(IrMemberAccessExpression<?> irMemberAccessExpression, int i) {
        Intrinsics.checkNotNullParameter(irMemberAccessExpression, "<this>");
        IrExpression valueArgument = irMemberAccessExpression.getValueArgument(i);
        if (valueArgument == null || !(valueArgument instanceof IrConst)) {
            return null;
        }
        IrConst irConst = (IrConst) valueArgument;
        if (!Intrinsics.areEqual(irConst.getKind(), IrConstKind.Int.INSTANCE)) {
            return null;
        }
        Object value = irConst.getValue();
        Intrinsics.checkNotNull(value, "null cannot be cast to non-null type kotlin.Int");
        return (Integer) value;
    }

    public static final int getIntConstArgument(IrMemberAccessExpression<?> irMemberAccessExpression, int i) {
        Intrinsics.checkNotNullParameter(irMemberAccessExpression, "<this>");
        Integer intConstArgumentOrNull = getIntConstArgumentOrNull(irMemberAccessExpression, i);
        if (intConstArgumentOrNull != null) {
            return intConstArgumentOrNull.intValue();
        }
        throw new AssertionError("Value argument #" + i + " should be an Int const: " + DumpIrTreeKt.dump$default(irMemberAccessExpression, null, 1, null));
    }

    /* JADX WARN: Code duplicated, block: B:9:0x002a  */
    public static final String getStringConstArgument(IrMemberAccessExpression<?> irMemberAccessExpression, int i) {
        String str;
        Intrinsics.checkNotNullParameter(irMemberAccessExpression, "<this>");
        IrExpression valueArgument = irMemberAccessExpression.getValueArgument(i);
        if (valueArgument != null) {
            if (valueArgument instanceof IrConst) {
                IrConst irConst = (IrConst) valueArgument;
                if (Intrinsics.areEqual(irConst.getKind(), IrConstKind.String.INSTANCE)) {
                    Object value = irConst.getValue();
                    Intrinsics.checkNotNull(value, "null cannot be cast to non-null type kotlin.String");
                    str = (String) value;
                } else {
                    str = null;
                }
            } else {
                str = null;
            }
            if (str != null) {
                return str;
            }
        }
        throw new AssertionError("Value argument #" + i + " should be a String const: " + DumpIrTreeKt.dump$default(irMemberAccessExpression, null, 1, null));
    }

    /* JADX WARN: Code duplicated, block: B:9:0x002a  */
    public static final boolean getBooleanConstArgument(IrMemberAccessExpression<?> irMemberAccessExpression, int i) {
        Boolean bool;
        Intrinsics.checkNotNullParameter(irMemberAccessExpression, "<this>");
        IrExpression valueArgument = irMemberAccessExpression.getValueArgument(i);
        if (valueArgument != null) {
            if (valueArgument instanceof IrConst) {
                IrConst irConst = (IrConst) valueArgument;
                if (Intrinsics.areEqual(irConst.getKind(), IrConstKind.Boolean.INSTANCE)) {
                    Object value = irConst.getValue();
                    Intrinsics.checkNotNull(value, "null cannot be cast to non-null type kotlin.Boolean");
                    bool = (Boolean) value;
                } else {
                    bool = null;
                }
            } else {
                bool = null;
            }
            if (bool != null) {
                return bool.booleanValue();
            }
        }
        throw new AssertionError("Value argument #" + i + " should be a Boolean const: " + DumpIrTreeKt.dump$default(irMemberAccessExpression, null, 1, null));
    }

    public static final IrFile getFileParentOrNull(IrDeclaration irDeclaration) {
        Intrinsics.checkNotNullParameter(irDeclaration, "<this>");
        IrDeclarationParent parent = irDeclaration.getParent();
        if (parent instanceof IrFile) {
            return (IrFile) parent;
        }
        if (parent instanceof IrDeclaration) {
            return getFileParentOrNull((IrDeclaration) parent);
        }
        return null;
    }

    public static final ClassId getParentClassId(IrMemberWithContainerSource irMemberWithContainerSource) {
        IrPropertySymbol correspondingPropertySymbol;
        IrProperty owner;
        Intrinsics.checkNotNullParameter(irMemberWithContainerSource, "<this>");
        IrSimpleFunction irSimpleFunction = irMemberWithContainerSource instanceof IrSimpleFunction ? (IrSimpleFunction) irMemberWithContainerSource : null;
        if (irSimpleFunction != null && (correspondingPropertySymbol = irSimpleFunction.getCorrespondingPropertySymbol()) != null && (owner = correspondingPropertySymbol.getOwner()) != null) {
            irMemberWithContainerSource = owner;
        }
        DeserializedContainerSource containerSource = irMemberWithContainerSource.getContainerSource();
        if (containerSource instanceof JvmPackagePartSource) {
            return ((JvmPackagePartSource) containerSource).getClassId();
        }
        if (containerSource instanceof FacadeClassSource) {
            return ClassId.topLevel(((FacadeClassSource) containerSource).getClassName().getFqNameForClassNameWithoutDollars());
        }
        IrDeclarationParent parent = irMemberWithContainerSource.getParent();
        IrClass irClass = parent instanceof IrClass ? (IrClass) parent : null;
        if (irClass != null) {
            return getClassId(irClass);
        }
        return null;
    }

    private static final ClassId getClassId(IrClass irClass) {
        ClassId classId;
        IrDeclarationParent parent = irClass.getParent();
        if (parent instanceof IrExternalPackageFragment) {
            return new ClassId(((IrExternalPackageFragment) parent).getPackageFqName(), irClass.getName());
        }
        if (!(parent instanceof IrClass) || (classId = getClassId((IrClass) parent)) == null) {
            return null;
        }
        return classId.createNestedClassId(irClass.getName());
    }

    public static final boolean isOptionalAnnotationClass(IrClass irClass) {
        Intrinsics.checkNotNullParameter(irClass, "<this>");
        return irClass.getKind() == ClassKind.ANNOTATION_CLASS && irClass.getIsExpect() && IrUtilsKt.hasAnnotation(irClass, OptionalAnnotationUtil.INSTANCE.getOPTIONAL_EXPECTATION_FQ_NAME());
    }

    public static final List<IrExpression> receiverAndArgs(IrFunctionAccessExpression irFunctionAccessExpression) {
        Intrinsics.checkNotNullParameter(irFunctionAccessExpression, "<this>");
        int i = 0;
        ArrayList arrayListArrayListOf = CollectionsKt.arrayListOf(irFunctionAccessExpression.getDispatchReceiver(), irFunctionAccessExpression.getExtensionReceiver());
        List<IrValueParameter> valueParameters = irFunctionAccessExpression.getSymbol().getOwner().getValueParameters();
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(valueParameters, 10));
        for (Object obj : valueParameters) {
            int i2 = i + 1;
            if (i < 0) {
                CollectionsKt.throwIndexOverflow();
            }
            arrayList.add(irFunctionAccessExpression.getValueArgument(i));
            i = i2;
        }
        return CollectionsKt.filterNotNull(CollectionsKt.plus((Collection) arrayListArrayListOf, (Iterable) arrayList));
    }

    public static final Boolean classFileContainsMethod(ClassId classId, IrFunction function, JvmBackendContext context) {
        Intrinsics.checkNotNullParameter(classId, "classId");
        Intrinsics.checkNotNullParameter(function, "function");
        Intrinsics.checkNotNullParameter(context, "context");
        Method methodMapAsmMethod = context.getDefaultMethodSignatureMapper().mapAsmMethod(function);
        String descriptor = methodMapAsmMethod.getDescriptor();
        if (AdditionalIrUtilsKt.isSuspend(function)) {
            StringBuilder sb = new StringBuilder();
            SpreadBuilder spreadBuilder = new SpreadBuilder(2);
            Type[] argumentTypes = Type.getArgumentTypes(descriptor);
            Intrinsics.checkNotNullExpressionValue(argumentTypes, "getArgumentTypes(...)");
            spreadBuilder.addSpread(argumentTypes);
            spreadBuilder.add(Type.getObjectType("kotlin/coroutines/Continuation"));
            descriptor = sb.append(CollectionsKt.joinToString$default(CollectionsKt.listOf(spreadBuilder.toArray(new Type[spreadBuilder.size()])), "", "(", Constant.AFTER_QUTO, 0, null, null, 56, null)).append(AsmTypes.OBJECT_TYPE).toString();
        }
        return InlineClassesCodegenUtilKt.classFileContainsMethod(classId, context.getState(), new Method(methodMapAsmMethod.getName(), descriptor));
    }

    public static final PsiElement getPsiElement(DeclarationDescriptorWithSource declarationDescriptorWithSource) {
        Intrinsics.checkNotNullParameter(declarationDescriptorWithSource, "<this>");
        SourceElement source = declarationDescriptorWithSource.getSourceElement();
        PsiSourceElement psiSourceElement = source instanceof PsiSourceElement ? (PsiSourceElement) source : null;
        if (psiSourceElement != null) {
            return psiSourceElement.getPsi();
        }
        return null;
    }

    public static final PsiElement getPsiElement(IrDeclaration irDeclaration) {
        Intrinsics.checkNotNullParameter(irDeclaration, "<this>");
        DeclarationDescriptor descriptor = irDeclaration.getDescriptor();
        DeclarationDescriptorWithSource declarationDescriptorWithSource = descriptor instanceof DeclarationDescriptorWithSource ? (DeclarationDescriptorWithSource) descriptor : null;
        if (declarationDescriptorWithSource != null) {
            return getPsiElement(declarationDescriptorWithSource);
        }
        return null;
    }

    public static final PsiElement getPsiElement(IrMemberAccessExpression<?> irMemberAccessExpression) {
        Intrinsics.checkNotNullParameter(irMemberAccessExpression, "<this>");
        DeclarationDescriptor original = irMemberAccessExpression.getSymbol().getDescriptor().getOriginal();
        DeclarationDescriptorWithSource declarationDescriptorWithSource = original instanceof DeclarationDescriptorWithSource ? (DeclarationDescriptorWithSource) original : null;
        if (declarationDescriptorWithSource != null) {
            return getPsiElement(declarationDescriptorWithSource);
        }
        return null;
    }

    public static final String extensionReceiverName(IrFunction irFunction, GenerationState state) {
        Name name2;
        IrPropertySymbol correspondingPropertySymbol;
        IrProperty owner;
        Intrinsics.checkNotNullParameter(irFunction, "<this>");
        Intrinsics.checkNotNullParameter(state, "state");
        if (!state.getLanguageVersionSettings().supportsFeature(LanguageFeature.NewCapturedReceiverFieldNamingConvention)) {
            return AsmUtil.RECEIVER_PARAMETER_NAME;
        }
        IrValueParameter extensionReceiverParameter = irFunction.getExtensionReceiverParameter();
        if (extensionReceiverParameter != null) {
            String strAsString = extensionReceiverParameter.getName().asString();
            Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
            if (StringsKt.startsWith$default(strAsString, AsmUtil.LABELED_THIS_PARAMETER, false, 2, (Object) null)) {
                String strAsString2 = extensionReceiverParameter.getName().asString();
                Intrinsics.checkNotNullExpressionValue(strAsString2, "asString(...)");
                return strAsString2;
            }
        }
        IrSimpleFunction irSimpleFunction = irFunction instanceof IrSimpleFunction ? (IrSimpleFunction) irFunction : null;
        if (irSimpleFunction == null || (correspondingPropertySymbol = irSimpleFunction.getCorrespondingPropertySymbol()) == null || (owner = correspondingPropertySymbol.getOwner()) == null || (name2 = owner.getName()) == null) {
            name2 = irFunction.getName();
        }
        if (name2.isSpecial() || !Name.isValidIdentifier(name2.asString())) {
            return AsmUtil.RECEIVER_PARAMETER_NAME;
        }
        StringBuilder sb = new StringBuilder(AsmUtil.LABELED_THIS_PARAMETER);
        String strAsString3 = name2.asString();
        Intrinsics.checkNotNullExpressionValue(strAsString3, "asString(...)");
        return sb.append(CommonVariableAsmNameManglingUtils.mangleNameIfNeeded(strAsString3)).toString();
    }

    public static final boolean isBridge(IrFunction irFunction) {
        Intrinsics.checkNotNullParameter(irFunction, "<this>");
        return Intrinsics.areEqual(irFunction.getOrigin(), IrDeclarationOrigin.BRIDGE.INSTANCE) || Intrinsics.areEqual(irFunction.getOrigin(), IrDeclarationOrigin.BRIDGE_SPECIAL.INSTANCE);
    }

    public static final boolean isEnumClassWhichRequiresExternalEntries(IrClass irClass) {
        Intrinsics.checkNotNullParameter(irClass, "<this>");
        return IrUtilsKt.isEnumClass(irClass) && (IrUtilsKt.isFromJava(irClass) || !hasEnumEntriesFunction(irClass));
    }

    private static final boolean hasEnumEntriesFunction(IrClass irClass) {
        Iterator<IrSimpleFunction> it = IrUtilsKt.getFunctions(irClass).iterator();
        while (it.hasNext()) {
            if (isGetEntries(it.next())) {
                return true;
            }
        }
        Iterator<IrProperty> it2 = IrUtilsKt.getProperties(irClass).iterator();
        while (it2.hasNext()) {
            IrSimpleFunction getter = it2.next().getGetter();
            if (getter != null && isGetEntries(getter)) {
                if (isInCurrentModule(irClass)) {
                    return true;
                }
            }
        }
        return false;
    }

    private static final boolean isGetEntries(IrSimpleFunction irSimpleFunction) {
        return Intrinsics.areEqual(irSimpleFunction.getName().toString(), "<get-entries>") && irSimpleFunction.getDispatchReceiverParameter() == null && irSimpleFunction.getExtensionReceiverParameter() == null && irSimpleFunction.getValueParameters().isEmpty();
    }

    public static final IrSimpleFunction findEnumValuesFunction(IrClass irClass, JvmBackendContext context) {
        Intrinsics.checkNotNullParameter(irClass, "<this>");
        Intrinsics.checkNotNullParameter(context, "context");
        IrSimpleFunction irSimpleFunction = null;
        boolean z = false;
        for (IrSimpleFunction irSimpleFunction2 : IrUtilsKt.getFunctions(irClass)) {
            IrSimpleFunction irSimpleFunction3 = irSimpleFunction2;
            if (Intrinsics.areEqual(irSimpleFunction3.getName().toString(), CompoundEntry.NAME_values) && irSimpleFunction3.getDispatchReceiverParameter() == null && irSimpleFunction3.getExtensionReceiverParameter() == null && irSimpleFunction3.getValueParameters().isEmpty() && IrTypeUtilsKt.isBoxedArray(irSimpleFunction3.getReturnType()) && Intrinsics.areEqual(IrTypesKt.getClassOrNull(IrTypeUtilsKt.getArrayElementType(irSimpleFunction3.getReturnType(), context.getIrBuiltIns())), irClass.getSymbol())) {
                if (z) {
                    throw new IllegalArgumentException("Sequence contains more than one matching element.");
                }
                z = true;
                irSimpleFunction = irSimpleFunction2;
            }
        }
        if (!z) {
            throw new NoSuchElementException("Sequence contains no element matching the predicate.");
        }
        return irSimpleFunction;
    }
}
