package org.jetbrains.kotlin.codegen.state;

import com.android.apksig.internal.apk.AndroidBinXmlParser;
import com.gaurav.avnc.vnc.XKeySym;
import com.huawei.hms.ml.language.common.utils.Constant;
import com.reandroid.dex.sections.Marker;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.Sequence;
import kotlin.sequences.SequencesKt;
import kotlin.text.StringsKt;
import net.bytebuddy.description.method.MethodDescription;
import org.jetbrains.kotlin.backend.common.SamType;
import org.jetbrains.kotlin.builtins.BuiltInsPackageFragment;
import org.jetbrains.kotlin.builtins.KotlinBuiltIns;
import org.jetbrains.kotlin.builtins.functions.FunctionClassDescriptor;
import org.jetbrains.kotlin.builtins.functions.FunctionTypeKind;
import org.jetbrains.kotlin.builtins.jvm.JavaToKotlinClassMap;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.codegen.AccessorForCallableDescriptor;
import org.jetbrains.kotlin.codegen.AccessorForCompanionObjectInstanceFieldDescriptor;
import org.jetbrains.kotlin.codegen.AccessorForConstructorDescriptor;
import org.jetbrains.kotlin.codegen.AccessorForPropertyDescriptor;
import org.jetbrains.kotlin.codegen.CallableMethod;
import org.jetbrains.kotlin.codegen.ClassBuilderMode;
import org.jetbrains.kotlin.codegen.CodegenUtilKt;
import org.jetbrains.kotlin.codegen.DescriptorAsmUtil;
import org.jetbrains.kotlin.codegen.JvmCodegenUtil;
import org.jetbrains.kotlin.codegen.OwnerKind;
import org.jetbrains.kotlin.codegen.PropertyCodegen;
import org.jetbrains.kotlin.codegen.SamCodegenUtil;
import org.jetbrains.kotlin.codegen.StackValue;
import org.jetbrains.kotlin.codegen.binding.CodegenBinding;
import org.jetbrains.kotlin.codegen.binding.MutableClosure;
import org.jetbrains.kotlin.codegen.coroutines.CoroutineCodegenUtilKt;
import org.jetbrains.kotlin.codegen.inline.FictitiousArrayConstructor;
import org.jetbrains.kotlin.codegen.signature.AsmTypeFactory;
import org.jetbrains.kotlin.codegen.signature.BothSignatureWriter;
import org.jetbrains.kotlin.codegen.signature.JvmSignatureWriter;
import org.jetbrains.kotlin.com.intellij.openapi.util.text.StringUtil;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.config.JvmAnalysisFlags;
import org.jetbrains.kotlin.config.JvmDefaultMode;
import org.jetbrains.kotlin.config.JvmTarget;
import org.jetbrains.kotlin.config.LanguageFeature;
import org.jetbrains.kotlin.config.LanguageVersionSettings;
import org.jetbrains.kotlin.config.LanguageVersionSettingsImpl;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.descriptors.CallableMemberDescriptor;
import org.jetbrains.kotlin.descriptors.ClassConstructorDescriptor;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.ClassKind;
import org.jetbrains.kotlin.descriptors.ClassifierDescriptor;
import org.jetbrains.kotlin.descriptors.ClassifierDescriptorWithTypeParameters;
import org.jetbrains.kotlin.descriptors.ConstructorDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.DescriptorVisibilities;
import org.jetbrains.kotlin.descriptors.DescriptorVisibility;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.descriptors.Modality;
import org.jetbrains.kotlin.descriptors.PackageFragmentDescriptor;
import org.jetbrains.kotlin.descriptors.PossiblyInnerType;
import org.jetbrains.kotlin.descriptors.PropertyAccessorDescriptor;
import org.jetbrains.kotlin.descriptors.PropertyDescriptor;
import org.jetbrains.kotlin.descriptors.PropertyGetterDescriptor;
import org.jetbrains.kotlin.descriptors.ReceiverParameterDescriptor;
import org.jetbrains.kotlin.descriptors.ScriptDescriptor;
import org.jetbrains.kotlin.descriptors.SimpleFunctionDescriptor;
import org.jetbrains.kotlin.descriptors.TypeParameterDescriptor;
import org.jetbrains.kotlin.descriptors.TypeParameterUtilsKt;
import org.jetbrains.kotlin.descriptors.ValueParameterDescriptor;
import org.jetbrains.kotlin.descriptors.VariableDescriptor;
import org.jetbrains.kotlin.descriptors.impl.LocalVariableAccessorDescriptor;
import org.jetbrains.kotlin.descriptors.impl.LocalVariableDescriptor;
import org.jetbrains.kotlin.descriptors.impl.TypeAliasConstructorDescriptor;
import org.jetbrains.kotlin.fileClasses.JvmFileClassInfo;
import org.jetbrains.kotlin.fileClasses.JvmFileClassUtil;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.load.java.BuiltinMethodsWithSpecialGenericSignature;
import org.jetbrains.kotlin.load.java.JvmAbi;
import org.jetbrains.kotlin.load.java.SpecialBuiltinMembers;
import org.jetbrains.kotlin.load.java.SpecialGenericSignatures;
import org.jetbrains.kotlin.load.java.descriptors.JavaCallableMemberDescriptor;
import org.jetbrains.kotlin.load.java.descriptors.JavaClassDescriptor;
import org.jetbrains.kotlin.load.java.descriptors.JavaMethodDescriptor;
import org.jetbrains.kotlin.load.java.descriptors.UtilKt;
import org.jetbrains.kotlin.load.java.lazy.descriptors.LazyJavaPackageFragment;
import org.jetbrains.kotlin.load.kotlin.DescriptorBasedTypeSignatureMappingKt;
import org.jetbrains.kotlin.load.kotlin.MethodSignatureMappingKt;
import org.jetbrains.kotlin.load.kotlin.ModuleNameKt;
import org.jetbrains.kotlin.load.kotlin.TypeMappingMode;
import org.jetbrains.kotlin.load.kotlin.TypeMappingModeExtensionsKt;
import org.jetbrains.kotlin.load.kotlin.incremental.IncrementalPackageFragmentProvider;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.FqNameUnsafe;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.name.NameUtils;
import org.jetbrains.kotlin.name.SpecialNames;
import org.jetbrains.kotlin.psi.KtBinaryExpressionWithTypeRHS;
import org.jetbrains.kotlin.psi.KtElement;
import org.jetbrains.kotlin.psi.KtExpression;
import org.jetbrains.kotlin.psi.KtFile;
import org.jetbrains.kotlin.psi.KtFunctionLiteral;
import org.jetbrains.kotlin.psi.KtLambdaExpression;
import org.jetbrains.kotlin.psi.KtQualifiedExpression;
import org.jetbrains.kotlin.psi.ValueArgument;
import org.jetbrains.kotlin.psi.psiUtil.KtPsiUtilKt;
import org.jetbrains.kotlin.resolve.BindingContext;
import org.jetbrains.kotlin.resolve.BindingContextUtils;
import org.jetbrains.kotlin.resolve.DescriptorToSourceUtils;
import org.jetbrains.kotlin.resolve.DescriptorUtils;
import org.jetbrains.kotlin.resolve.FunctionImportedFromObject;
import org.jetbrains.kotlin.resolve.ImportedFromObjectCallableDescriptor;
import org.jetbrains.kotlin.resolve.InlineClassDescriptorResolver;
import org.jetbrains.kotlin.resolve.InlineClassesUtilsKt;
import org.jetbrains.kotlin.resolve.annotations.AnnotationUtilKt;
import org.jetbrains.kotlin.resolve.bindingContextUtil.BindingContextUtilsKt;
import org.jetbrains.kotlin.resolve.calls.model.DefaultValueArgument;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedCall;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedValueArgument;
import org.jetbrains.kotlin.resolve.calls.model.VarargValueArgument;
import org.jetbrains.kotlin.resolve.descriptorUtil.DescriptorUtilsKt;
import org.jetbrains.kotlin.resolve.jvm.AsmTypes;
import org.jetbrains.kotlin.resolve.jvm.JdkClassesKt;
import org.jetbrains.kotlin.resolve.jvm.JvmClassName;
import org.jetbrains.kotlin.resolve.jvm.annotations.JvmAnnotationUtilKt;
import org.jetbrains.kotlin.resolve.jvm.jvmSignature.JvmMethodGenericSignature;
import org.jetbrains.kotlin.resolve.jvm.jvmSignature.JvmMethodParameterKind;
import org.jetbrains.kotlin.resolve.jvm.jvmSignature.JvmMethodParameterSignature;
import org.jetbrains.kotlin.resolve.jvm.jvmSignature.JvmMethodSignature;
import org.jetbrains.kotlin.serialization.deserialization.descriptors.DescriptorWithContainerSource;
import org.jetbrains.kotlin.serialization.deserialization.descriptors.DeserializedCallableMemberDescriptor;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.SimpleType;
import org.jetbrains.kotlin.types.TypeProjection;
import org.jetbrains.kotlin.types.TypeSystemCommonBackendContext;
import org.jetbrains.kotlin.types.TypeUtils;
import org.jetbrains.kotlin.types.UtilsKt;
import org.jetbrains.kotlin.types.Variance;
import org.jetbrains.kotlin.types.checker.ClassicTypeSystemContextKt;
import org.jetbrains.kotlin.types.checker.SimpleClassicTypeSystemContext;
import org.jetbrains.kotlin.types.expressions.ExpressionTypingUtils;
import org.jetbrains.kotlin.types.model.KotlinTypeMarker;
import org.jetbrains.kotlin.types.model.TypeArgumentMarker;
import org.jetbrains.kotlin.types.model.TypeConstructorMarker;
import org.jetbrains.kotlin.types.model.TypeParameterMarker;
import org.jetbrains.kotlin.types.model.TypeSystemContext;
import org.jetbrains.kotlin.types.model.TypeVariance;
import org.jetbrains.kotlin.util.OperatorNameConventions;
import org.jetbrains.kotlin.utils.addToStdlib.AddToStdlibKt;
import org.jetbrains.org.objectweb.asm.Type;
import org.jetbrains.org.objectweb.asm.commons.Method;

/* JADX INFO: compiled from: KotlinTypeMapper.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000£\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u000b*\u0001#\u0018\u0000 \u009f\u00012\u00020\u0001:\u0006\u009f\u0001 \u0001¡\u0001Bw\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u0012\u0006\u0010\n\u001a\u00020\u000b\u0012\b\b\u0002\u0010\f\u001a\u00020\r\u0012\b\b\u0002\u0010\u000e\u001a\u00020\u000b\u0012\u0018\b\u0002\u0010\u000f\u001a\u0012\u0012\u0004\u0012\u00020\u0011\u0012\u0006\u0012\u0004\u0018\u00010\u0011\u0018\u00010\u0010\u0012\u0018\b\u0002\u0010\u0012\u001a\u0012\u0012\u0004\u0012\u00020\u0013\u0012\u0006\u0012\u0004\u0018\u00010\u0007\u0018\u00010\u0010¢\u0006\u0002\u0010\u0014J\u000e\u0010/\u001a\u00020\u00072\u0006\u00100\u001a\u00020\u0013J\u001a\u00101\u001a\b\u0012\u0004\u0012\u00020\u0011022\n\u00103\u001a\u0006\u0012\u0002\b\u000304H\u0002J$\u00105\u001a\u0012\u0012\u0006\u0012\u0004\u0018\u00010\u0011\u0012\u0006\u0012\u0004\u0018\u000107062\n\u00103\u001a\u0006\u0012\u0002\b\u000304H\u0002J\u0018\u00108\u001a\n\u0012\u0004\u0012\u000209\u0018\u0001042\u0006\u0010:\u001a\u000209H\u0002J\u0010\u0010;\u001a\u00020\u000b2\u0006\u0010:\u001a\u00020<H\u0002J\u0010\u0010=\u001a\u00020\u00072\u0006\u0010:\u001a\u00020>H\u0002J\u0010\u0010?\u001a\u0004\u0018\u0001072\u0006\u0010:\u001a\u00020@J\u0010\u0010A\u001a\u00020\u000b2\u0006\u0010:\u001a\u00020<H\u0002J\u0010\u0010B\u001a\u00020\u000b2\u0006\u0010C\u001a\u00020\u0011H\u0002J\"\u0010D\u001a\u00020\u00072\u0006\u0010E\u001a\u00020\u00072\u0006\u0010:\u001a\u00020>2\b\u0010F\u001a\u0004\u0018\u00010GH\u0002J\u000e\u0010H\u001a\u00020\u00072\u0006\u0010:\u001a\u00020IJ\u000e\u0010J\u001a\u00020K2\u0006\u0010:\u001a\u00020IJ\u000e\u0010L\u001a\u00020M2\u0006\u0010:\u001a\u00020<J\u0016\u0010L\u001a\u00020M2\u0006\u0010:\u001a\u00020<2\u0006\u0010F\u001a\u00020GJ\u0010\u0010N\u001a\u0002072\u0006\u0010O\u001a\u00020PH\u0016J\u0016\u0010Q\u001a\u00020M2\u0006\u0010R\u001a\u00020<2\u0006\u0010F\u001a\u00020GJ\u0018\u0010S\u001a\u0004\u0018\u00010\u00072\u0006\u0010T\u001a\u00020\u00112\u0006\u0010U\u001a\u00020IJ\u0018\u0010V\u001a\u00020\u00072\u0006\u0010:\u001a\u00020<2\b\u0010F\u001a\u0004\u0018\u00010GJ\u000e\u0010W\u001a\u0002072\u0006\u0010:\u001a\u00020@J\u000e\u0010X\u001a\u0002072\u0006\u0010:\u001a\u00020@J\u0018\u0010X\u001a\u0002072\u0006\u0010:\u001a\u00020@2\u0006\u0010Y\u001a\u00020\u000bH\u0002J\u000e\u0010Z\u001a\u00020M2\u0006\u0010:\u001a\u00020<J\u001c\u0010[\u001a\u0002072\u0006\u0010:\u001a\u00020\\2\n\b\u0002\u0010]\u001a\u0004\u0018\u00010^H\u0007J\"\u0010[\u001a\u0002072\u0006\u0010:\u001a\u00020\\2\b\u0010]\u001a\u0004\u0018\u00010^2\u0006\u0010_\u001a\u00020\u0011H\u0002J\u000e\u0010`\u001a\u00020a2\u0006\u0010b\u001a\u00020cJ\u0010\u0010d\u001a\u00020K2\u0006\u0010e\u001a\u00020<H\u0002J \u0010d\u001a\u00020K2\u0006\u0010e\u001a\u00020<2\u0006\u0010F\u001a\u00020G2\u0006\u0010f\u001a\u00020\u000bH\u0002J6\u0010d\u001a\u00020K2\u0006\u0010e\u001a\u00020<2\f\u00103\u001a\b\u0012\u0002\b\u0003\u0018\u0001042\u0006\u0010F\u001a\u00020G2\u0006\u0010f\u001a\u00020\u000b2\u0006\u0010g\u001a\u00020\u000bH\u0002J\u000e\u0010h\u001a\u00020K2\u0006\u0010e\u001a\u00020<J\u000e\u0010i\u001a\u00020a2\u0006\u0010e\u001a\u00020<J\u000e\u0010j\u001a\u00020K2\u0006\u0010e\u001a\u00020<J\u001a\u0010k\u001a\u00020a2\u0006\u0010e\u001a\u00020<2\b\b\u0002\u0010F\u001a\u00020GH\u0007J,\u0010l\u001a\u00020K2\u0006\u0010e\u001a\u00020<2\u0006\u0010F\u001a\u00020G2\f\u0010m\u001a\b\u0012\u0004\u0012\u00020o0n2\u0006\u0010f\u001a\u00020\u000bJJ\u0010l\u001a\u00020K2\u0006\u0010e\u001a\u00020<2\u0006\u0010F\u001a\u00020G2\f\u0010p\u001a\b\u0012\u0004\u0012\u00020\u0011022\b\u0010q\u001a\u0004\u0018\u00010\u00112\b\u0010r\u001a\u0004\u0018\u0001072\u0006\u0010f\u001a\u00020\u000b2\u0006\u0010g\u001a\u00020\u000bH\u0002J\u0016\u0010s\u001a\u00020K2\u0006\u0010e\u001a\u00020<2\u0006\u0010F\u001a\u00020GJ\u001e\u0010s\u001a\u00020K2\u0006\u0010e\u001a\u00020<2\u0006\u0010F\u001a\u00020G2\u0006\u0010g\u001a\u00020\u000bJ\u0018\u0010t\u001a\u0002072\u0006\u0010u\u001a\u00020\u00112\b\u0010v\u001a\u0004\u0018\u00010^J\u000e\u0010w\u001a\u00020M2\u0006\u0010:\u001a\u00020IJ4\u0010x\u001a\u00020y2\u0006\u0010:\u001a\u00020<2\u0006\u0010z\u001a\u00020\u000b2\n\b\u0002\u0010F\u001a\u0004\u0018\u00010G2\u000e\b\u0002\u00103\u001a\b\u0012\u0002\b\u0003\u0018\u000104H\u0007J\u000e\u0010{\u001a\u0002072\u0006\u0010:\u001a\u00020\\J\u000e\u0010{\u001a\u0002072\u0006\u0010:\u001a\u00020PJ&\u0010{\u001a\u0002072\u0006\u0010u\u001a\u00020\u00112\n\b\u0002\u0010v\u001a\u0004\u0018\u00010^2\b\b\u0002\u0010|\u001a\u00020}H\u0007J\u0018\u0010~\u001a\u0002072\u0006\u0010u\u001a\u00020\u00112\b\u0010v\u001a\u0004\u0018\u00010^J\u000e\u0010\u007f\u001a\u0002072\u0006\u0010:\u001a\u00020\\J\u000e\u0010\u007f\u001a\u0002072\u0006\u0010C\u001a\u00020\u0011J\u001a\u0010\u0080\u0001\u001a\u0002072\u0007\u0010u\u001a\u00030\u0081\u00012\u0006\u0010|\u001a\u00020}H\u0016J\u001b\u0010\u0082\u0001\u001a\u00030\u0083\u00012\u0007\u0010:\u001a\u00030\u0084\u00012\u0006\u0010]\u001a\u00020^H\u0002J\"\u0010\u0085\u0001\u001a\u00030\u0083\u00012\u0006\u0010T\u001a\u00020\u00112\b\u0010\u0086\u0001\u001a\u00030\u0087\u00012\u0006\u0010]\u001a\u00020^J \u0010\u0088\u0001\u001a\u00030\u0083\u00012\u000e\u0010\u0089\u0001\u001a\t\u0012\u0005\u0012\u00030\u008a\u00010n2\u0006\u0010]\u001a\u00020^J:\u0010\u008b\u0001\u001a\u00030\u0083\u00012\u0006\u0010v\u001a\u00020^2\u000e\u0010\u008c\u0001\u001a\t\u0012\u0005\u0012\u00030\u008d\u00010n2\u000e\u0010\u008e\u0001\u001a\t\u0012\u0005\u0012\u00030\u008a\u00010n2\u0006\u0010|\u001a\u00020}H\u0002J#\u0010\u008b\u0001\u001a\u00030\u0083\u00012\u0006\u0010v\u001a\u00020^2\u0007\u0010u\u001a\u00030\u008f\u00012\u0006\u0010|\u001a\u00020}H\u0002J-\u0010\u0090\u0001\u001a\u00030\u0083\u00012\u0006\u0010u\u001a\u00020\u00112\u0007\u0010\u0091\u0001\u001a\u0002072\b\u0010v\u001a\u0004\u0018\u00010^2\u0006\u0010|\u001a\u00020}H\u0002J4\u0010\u0092\u0001\u001a\u00030\u0083\u00012\u000e\u0010\u0093\u0001\u001a\t\u0012\u0005\u0012\u00030\u008f\u00010n2\u0006\u0010v\u001a\u00020^2\u0006\u0010|\u001a\u00020}2\b\u0010\u0094\u0001\u001a\u00030\u0095\u0001H\u0002J.\u0010\u0096\u0001\u001a\u00030\u0083\u00012\u0006\u0010]\u001a\u00020^2\u0007\u0010F\u001a\u00030\u0097\u00012\u0006\u0010u\u001a\u00020\u00112\t\u0010\u0098\u0001\u001a\u0004\u0018\u00010\\H\u0002J%\u0010\u0096\u0001\u001a\u00030\u0083\u00012\u0006\u0010]\u001a\u00020^2\u0006\u0010u\u001a\u00020\u00112\t\u0010\u0098\u0001\u001a\u0004\u0018\u00010\\H\u0002J#\u0010\u0099\u0001\u001a\u00030\u0083\u00012\u0006\u0010]\u001a\u00020^2\u0006\u0010u\u001a\u00020\u00112\t\u0010\u0098\u0001\u001a\u0004\u0018\u00010\\J2\u0010\u009a\u0001\u001a\u00030\u0083\u00012\u0006\u0010]\u001a\u00020^2\u0007\u0010:\u001a\u00030\u0084\u00012\f\u0010z\u001a\b\u0012\u0004\u0012\u000209042\u0007\u0010\u009b\u0001\u001a\u00020\u000bH\u0002J\r\u0010\u009c\u0001\u001a\u00020\u000b*\u00020\u0013H\u0002J\r\u0010\u009d\u0001\u001a\u00020\u000b*\u00020>H\u0002J\r\u0010\u009e\u0001\u001a\u00020\u000b*\u00020>H\u0002R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0016R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0018R\u000e\u0010\u000e\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\u0019\u001a\u00020\u001a¢\u0006\b\n\u0000\u001a\u0004\b\u001b\u0010\u001cR\u0011\u0010\f\u001a\u00020\r¢\u0006\b\n\u0000\u001a\u0004\b\u001d\u0010\u001eR\u0011\u0010\b\u001a\u00020\t¢\u0006\b\n\u0000\u001a\u0004\b\u001f\u0010 R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u001e\u0010\u0012\u001a\u0012\u0012\u0004\u0012\u00020\u0013\u0012\u0006\u0012\u0004\u0018\u00010\u0007\u0018\u00010\u0010X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010!\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\"\u001a\u00020#X\u0082\u0004¢\u0006\u0004\n\u0002\u0010$R\u001e\u0010\u000f\u001a\u0012\u0012\u0004\u0012\u00020\u0011\u0012\u0006\u0012\u0004\u0018\u00010\u0011\u0018\u00010\u0010X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010%\u001a\u00020&8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b'\u0010(R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R$\u0010*\u001a\u00020\u000b2\u0006\u0010)\u001a\u00020\u000b@FX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b+\u0010,\"\u0004\b-\u0010.¨\u0006¢\u0001"}, d2 = {"Lorg/jetbrains/kotlin/codegen/state/KotlinTypeMapper;", "Lorg/jetbrains/kotlin/codegen/state/KotlinTypeMapperBase;", "bindingContext", "Lorg/jetbrains/kotlin/resolve/BindingContext;", "classBuilderMode", "Lorg/jetbrains/kotlin/codegen/ClassBuilderMode;", "moduleName", "", "languageVersionSettings", "Lorg/jetbrains/kotlin/config/LanguageVersionSettings;", "useOldInlineClassesManglingScheme", "", "jvmTarget", "Lorg/jetbrains/kotlin/config/JvmTarget;", "isIrBackend", "typePreprocessor", "Lkotlin/Function1;", "Lorg/jetbrains/kotlin/types/KotlinType;", "namePreprocessor", "Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;", "(Lorg/jetbrains/kotlin/resolve/BindingContext;Lorg/jetbrains/kotlin/codegen/ClassBuilderMode;Ljava/lang/String;Lorg/jetbrains/kotlin/config/LanguageVersionSettings;ZLorg/jetbrains/kotlin/config/JvmTarget;ZLkotlin/jvm/functions/Function1;Lkotlin/jvm/functions/Function1;)V", "getBindingContext", "()Lorg/jetbrains/kotlin/resolve/BindingContext;", "getClassBuilderMode", "()Lorg/jetbrains/kotlin/codegen/ClassBuilderMode;", "jvmDefaultMode", "Lorg/jetbrains/kotlin/config/JvmDefaultMode;", "getJvmDefaultMode", "()Lorg/jetbrains/kotlin/config/JvmDefaultMode;", "getJvmTarget", "()Lorg/jetbrains/kotlin/config/JvmTarget;", "getLanguageVersionSettings", "()Lorg/jetbrains/kotlin/config/LanguageVersionSettings;", "shouldMangleByReturnType", "typeMappingConfiguration", "org/jetbrains/kotlin/codegen/state/KotlinTypeMapper$typeMappingConfiguration$1", "Lorg/jetbrains/kotlin/codegen/state/KotlinTypeMapper$typeMappingConfiguration$1;", "typeSystem", "Lorg/jetbrains/kotlin/types/TypeSystemCommonBackendContext;", "getTypeSystem", "()Lorg/jetbrains/kotlin/types/TypeSystemCommonBackendContext;", "value", "useOldManglingRulesForFunctionAcceptingInlineClass", "getUseOldManglingRulesForFunctionAcceptingInlineClass", "()Z", "setUseOldManglingRulesForFunctionAcceptingInlineClass", "(Z)V", "classInternalName", "classDescriptor", "extractPolymorphicParameterTypes", "Lkotlin/sequences/Sequence;", "resolvedCall", "Lorg/jetbrains/kotlin/resolve/calls/model/ResolvedCall;", "extractPolymorphicReturnType", "Lkotlin/Pair;", "Lorg/jetbrains/org/objectweb/asm/Type;", "findFirstDelegatingSuperCall", "Lorg/jetbrains/kotlin/descriptors/ConstructorDescriptor;", "descriptor", "forceBoxedReturnType", "Lorg/jetbrains/kotlin/descriptors/FunctionDescriptor;", "getModuleName", "Lorg/jetbrains/kotlin/descriptors/CallableMemberDescriptor;", "getSharedVarType", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "isBoxMethodForInlineClass", "isJvmPrimitive", "kotlinType", "mangleMemberNameIfRequired", "name", Namer.METADATA_CLASS_KIND, "Lorg/jetbrains/kotlin/codegen/OwnerKind;", "mapAnnotationParameterName", "Lorg/jetbrains/kotlin/descriptors/PropertyDescriptor;", "mapAnnotationParameterSignature", "Lorg/jetbrains/kotlin/resolve/jvm/jvmSignature/JvmMethodGenericSignature;", "mapAsmMethod", "Lorg/jetbrains/org/objectweb/asm/commons/Method;", "mapClass", "classifier", "Lorg/jetbrains/kotlin/descriptors/ClassifierDescriptor;", "mapDefaultMethod", "functionDescriptor", "mapFieldSignature", "backingFieldType", "propertyDescriptor", "mapFunctionName", "mapImplementationOwner", "mapOwner", "publicFacade", "mapPropertyReferenceSignature", "mapReturnType", "Lorg/jetbrains/kotlin/descriptors/CallableDescriptor;", "sw", "Lorg/jetbrains/kotlin/codegen/signature/JvmSignatureWriter;", "returnType", "mapScriptSignature", "Lorg/jetbrains/kotlin/resolve/jvm/jvmSignature/JvmMethodSignature;", "script", "Lorg/jetbrains/kotlin/descriptors/ScriptDescriptor;", "mapSignature", "f", "skipGenericSignature", "hasSpecialBridge", "mapSignatureForBoxMethodOfInlineClass", "mapSignatureForInlineErasedClassSkipGeneric", "mapSignatureForSpecializedEqualsOfInlineClass", "mapSignatureSkipGeneric", "mapSignatureWithCustomParameters", "valueParameters", "", "Lorg/jetbrains/kotlin/descriptors/ValueParameterDescriptor;", "valueParameterTypes", "customReturnType", "customReturnAsmType", "mapSignatureWithGeneric", "mapSupertype", ModuleXmlParser.TYPE, "signatureVisitor", "mapSyntheticMethodForPropertyAnnotations", "mapToCallableMethod", "Lorg/jetbrains/kotlin/codegen/CallableMethod;", "superCall", "mapType", "mode", "Lorg/jetbrains/kotlin/load/kotlin/TypeMappingMode;", "mapTypeArgument", "mapTypeAsDeclaration", "mapTypeCommon", "Lorg/jetbrains/kotlin/types/model/KotlinTypeMarker;", "writeAdditionalConstructorParameters", "", "Lorg/jetbrains/kotlin/descriptors/ClassConstructorDescriptor;", "writeFieldSignature", "variableDescriptor", "Lorg/jetbrains/kotlin/descriptors/VariableDescriptor;", "writeFormalTypeParameters", "typeParameters", "Lorg/jetbrains/kotlin/descriptors/TypeParameterDescriptor;", "writeGenericArguments", "arguments", "Lorg/jetbrains/kotlin/types/TypeProjection;", "parameters", "Lorg/jetbrains/kotlin/descriptors/PossiblyInnerType;", "writeGenericType", "asmType", "writeInnerParts", "innerTypesAsList", "index", "", "writeParameter", "Lorg/jetbrains/kotlin/resolve/jvm/jvmSignature/JvmMethodParameterKind;", "callableDescriptor", "writeParameterType", "writeSuperConstructorCallParameters", "hasOuter", "hasJavaLangRecordSupertype", "isPropertyWithGetterSignaturePresent", "overridesJvmDefault", "Companion", "ContainingClassesInfo", "InternalNameMapper", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class KotlinTypeMapper extends KotlinTypeMapperBase {
    private final BindingContext bindingContext;
    private final ClassBuilderMode classBuilderMode;
    private final boolean isIrBackend;
    private final JvmDefaultMode jvmDefaultMode;
    private final JvmTarget jvmTarget;
    private final LanguageVersionSettings languageVersionSettings;
    private final String moduleName;
    private final Function1<ClassDescriptor, String> namePreprocessor;
    private final boolean shouldMangleByReturnType;
    private final KotlinTypeMapper$typeMappingConfiguration$1 typeMappingConfiguration;
    private final Function1<KotlinType, KotlinType> typePreprocessor;
    private final boolean useOldInlineClassesManglingScheme;
    private boolean useOldManglingRulesForFunctionAcceptingInlineClass;

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private static final LanguageVersionSettings LANGUAGE_VERSION_SETTINGS_DEFAULT = LanguageVersionSettingsImpl.DEFAULT;
    private static final ClassId FAKE_CLASS_ID_FOR_BUILTINS = new ClassId(new FqName("kotlin.jvm.internal"), new FqName("Intrinsics.Kotlin"), false);
    public static final String BOX_JVM_METHOD_NAME = InlineClassDescriptorResolver.BOX_METHOD_NAME.toString() + JvmAbi.IMPL_SUFFIX_FOR_INLINE_CLASS_MEMBERS;
    public static final String UNBOX_JVM_METHOD_NAME = InlineClassDescriptorResolver.UNBOX_METHOD_NAME.toString() + JvmAbi.IMPL_SUFFIX_FOR_INLINE_CLASS_MEMBERS;

    /* JADX INFO: compiled from: KotlinTypeMapper.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[OwnerKind.values().length];
            try {
                iArr[OwnerKind.DEFAULT_IMPLS.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[OwnerKind.ERASED_INLINE_CLASS.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public KotlinTypeMapper(BindingContext bindingContext, ClassBuilderMode classBuilderMode, String moduleName, LanguageVersionSettings languageVersionSettings, boolean z) {
        this(bindingContext, classBuilderMode, moduleName, languageVersionSettings, z, null, false, null, null, 480, null);
        Intrinsics.checkNotNullParameter(bindingContext, "bindingContext");
        Intrinsics.checkNotNullParameter(classBuilderMode, "classBuilderMode");
        Intrinsics.checkNotNullParameter(moduleName, "moduleName");
        Intrinsics.checkNotNullParameter(languageVersionSettings, "languageVersionSettings");
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public KotlinTypeMapper(BindingContext bindingContext, ClassBuilderMode classBuilderMode, String moduleName, LanguageVersionSettings languageVersionSettings, boolean z, JvmTarget jvmTarget) {
        this(bindingContext, classBuilderMode, moduleName, languageVersionSettings, z, jvmTarget, false, null, null, XKeySym.XK_Racute, null);
        Intrinsics.checkNotNullParameter(bindingContext, "bindingContext");
        Intrinsics.checkNotNullParameter(classBuilderMode, "classBuilderMode");
        Intrinsics.checkNotNullParameter(moduleName, "moduleName");
        Intrinsics.checkNotNullParameter(languageVersionSettings, "languageVersionSettings");
        Intrinsics.checkNotNullParameter(jvmTarget, "jvmTarget");
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public KotlinTypeMapper(BindingContext bindingContext, ClassBuilderMode classBuilderMode, String moduleName, LanguageVersionSettings languageVersionSettings, boolean z, JvmTarget jvmTarget, boolean z2) {
        this(bindingContext, classBuilderMode, moduleName, languageVersionSettings, z, jvmTarget, z2, null, null, AndroidBinXmlParser.Chunk.RES_XML_TYPE_RESOURCE_MAP, null);
        Intrinsics.checkNotNullParameter(bindingContext, "bindingContext");
        Intrinsics.checkNotNullParameter(classBuilderMode, "classBuilderMode");
        Intrinsics.checkNotNullParameter(moduleName, "moduleName");
        Intrinsics.checkNotNullParameter(languageVersionSettings, "languageVersionSettings");
        Intrinsics.checkNotNullParameter(jvmTarget, "jvmTarget");
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public KotlinTypeMapper(BindingContext bindingContext, ClassBuilderMode classBuilderMode, String moduleName, LanguageVersionSettings languageVersionSettings, boolean z, JvmTarget jvmTarget, boolean z2, Function1<? super KotlinType, ? extends KotlinType> function1) {
        this(bindingContext, classBuilderMode, moduleName, languageVersionSettings, z, jvmTarget, z2, function1, null, 256, null);
        Intrinsics.checkNotNullParameter(bindingContext, "bindingContext");
        Intrinsics.checkNotNullParameter(classBuilderMode, "classBuilderMode");
        Intrinsics.checkNotNullParameter(moduleName, "moduleName");
        Intrinsics.checkNotNullParameter(languageVersionSettings, "languageVersionSettings");
        Intrinsics.checkNotNullParameter(jvmTarget, "jvmTarget");
    }

    @JvmStatic
    public static final boolean isAccessor(CallableMemberDescriptor callableMemberDescriptor) {
        return INSTANCE.isAccessor(callableMemberDescriptor);
    }

    @JvmStatic
    public static final boolean isStaticAccessor(CallableMemberDescriptor callableMemberDescriptor) {
        return INSTANCE.isStaticAccessor(callableMemberDescriptor);
    }

    @JvmStatic
    public static final String mapDefaultFieldName(PropertyDescriptor propertyDescriptor, boolean z) {
        return INSTANCE.mapDefaultFieldName(propertyDescriptor, z);
    }

    @JvmStatic
    public static final Type mapUnderlyingTypeOfInlineClassType(KotlinTypeMarker kotlinTypeMarker, KotlinTypeMapperBase kotlinTypeMapperBase) {
        return INSTANCE.mapUnderlyingTypeOfInlineClassType(kotlinTypeMarker, kotlinTypeMapperBase);
    }

    @JvmStatic
    public static final void writeFormalTypeParameter(TypeSystemCommonBackendContext typeSystemCommonBackendContext, TypeParameterMarker typeParameterMarker, JvmSignatureWriter jvmSignatureWriter, Function2<? super KotlinTypeMarker, ? super TypeMappingMode, Type> function2) {
        INSTANCE.writeFormalTypeParameter(typeSystemCommonBackendContext, typeParameterMarker, jvmSignatureWriter, function2);
    }

    public final Type mapReturnType(CallableDescriptor descriptor) {
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        return mapReturnType$default(this, descriptor, null, 2, null);
    }

    public final JvmMethodSignature mapSignatureSkipGeneric(FunctionDescriptor f) {
        Intrinsics.checkNotNullParameter(f, "f");
        return mapSignatureSkipGeneric$default(this, f, null, 2, null);
    }

    public final CallableMethod mapToCallableMethod(FunctionDescriptor descriptor, boolean z) {
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        return mapToCallableMethod$default(this, descriptor, z, null, null, 12, null);
    }

    public final CallableMethod mapToCallableMethod(FunctionDescriptor descriptor, boolean z, OwnerKind ownerKind) {
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        return mapToCallableMethod$default(this, descriptor, z, ownerKind, null, 8, null);
    }

    public final Type mapType(KotlinType type) {
        Intrinsics.checkNotNullParameter(type, "type");
        return mapType$default(this, type, null, null, 6, null);
    }

    public final Type mapType(KotlinType type, JvmSignatureWriter jvmSignatureWriter) {
        Intrinsics.checkNotNullParameter(type, "type");
        return mapType$default(this, type, jvmSignatureWriter, null, 4, null);
    }

    public final BindingContext getBindingContext() {
        return this.bindingContext;
    }

    public final ClassBuilderMode getClassBuilderMode() {
        return this.classBuilderMode;
    }

    public final LanguageVersionSettings getLanguageVersionSettings() {
        return this.languageVersionSettings;
    }

    public /* synthetic */ KotlinTypeMapper(BindingContext bindingContext, ClassBuilderMode classBuilderMode, String str, LanguageVersionSettings languageVersionSettings, boolean z, JvmTarget jvmTarget, boolean z2, Function1 function1, Function1 function2, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(bindingContext, classBuilderMode, str, languageVersionSettings, z, (i & 32) != 0 ? JvmTarget.DEFAULT : jvmTarget, (i & 64) != 0 ? false : z2, (i & 128) != 0 ? null : function1, (i & 256) != 0 ? null : function2);
    }

    public final JvmTarget getJvmTarget() {
        return this.jvmTarget;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public KotlinTypeMapper(BindingContext bindingContext, ClassBuilderMode classBuilderMode, String moduleName, LanguageVersionSettings languageVersionSettings, boolean z, JvmTarget jvmTarget, boolean z2, Function1<? super KotlinType, ? extends KotlinType> function1, Function1<? super ClassDescriptor, String> function2) {
        Intrinsics.checkNotNullParameter(bindingContext, "bindingContext");
        Intrinsics.checkNotNullParameter(classBuilderMode, "classBuilderMode");
        Intrinsics.checkNotNullParameter(moduleName, "moduleName");
        Intrinsics.checkNotNullParameter(languageVersionSettings, "languageVersionSettings");
        Intrinsics.checkNotNullParameter(jvmTarget, "jvmTarget");
        this.bindingContext = bindingContext;
        this.classBuilderMode = classBuilderMode;
        this.moduleName = moduleName;
        this.languageVersionSettings = languageVersionSettings;
        this.useOldInlineClassesManglingScheme = z;
        this.jvmTarget = jvmTarget;
        this.isIrBackend = z2;
        this.typePreprocessor = function1;
        this.namePreprocessor = function2;
        this.jvmDefaultMode = (JvmDefaultMode) languageVersionSettings.getFlag(JvmAnalysisFlags.getJvmDefaultMode());
        this.useOldManglingRulesForFunctionAcceptingInlineClass = z;
        this.typeMappingConfiguration = new KotlinTypeMapper$typeMappingConfiguration$1(this);
        this.shouldMangleByReturnType = languageVersionSettings.supportsFeature(LanguageFeature.MangleClassMembersReturningInlineClasses);
    }

    public final JvmDefaultMode getJvmDefaultMode() {
        return this.jvmDefaultMode;
    }

    public final boolean getUseOldManglingRulesForFunctionAcceptingInlineClass() {
        return this.useOldManglingRulesForFunctionAcceptingInlineClass;
    }

    public final void setUseOldManglingRulesForFunctionAcceptingInlineClass(boolean z) {
        if (this.useOldInlineClassesManglingScheme) {
            throw new IllegalArgumentException("Failed requirement.".toString());
        }
        this.useOldManglingRulesForFunctionAcceptingInlineClass = z;
    }

    @Override // org.jetbrains.kotlin.codegen.state.KotlinTypeMapperBase
    public TypeSystemCommonBackendContext getTypeSystem() {
        return SimpleClassicTypeSystemContext.INSTANCE;
    }

    public final Type mapOwner(DeclarationDescriptor descriptor) {
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        return mapOwner(descriptor, true);
    }

    public final Type mapImplementationOwner(DeclarationDescriptor descriptor) {
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        return mapOwner(descriptor, false);
    }

    private final Type mapOwner(DeclarationDescriptor descriptor, boolean publicFacade) {
        if (ExpressionTypingUtils.isLocalFunction(descriptor)) {
            BindingContext bindingContext = this.bindingContext;
            Intrinsics.checkNotNull(descriptor, "null cannot be cast to non-null type org.jetbrains.kotlin.descriptors.FunctionDescriptor");
            Type typeAsmTypeForAnonymousClass = CodegenBinding.asmTypeForAnonymousClass(bindingContext, CoroutineCodegenUtilKt.unwrapInitialDescriptorForSuspendFunction((FunctionDescriptor) descriptor));
            Intrinsics.checkNotNullExpressionValue(typeAsmTypeForAnonymousClass, "asmTypeForAnonymousClass(...)");
            return typeAsmTypeForAnonymousClass;
        }
        if (descriptor instanceof ConstructorDescriptor) {
            ClassDescriptor constructedClass = ((ConstructorDescriptor) descriptor).getConstructedClass();
            Intrinsics.checkNotNullExpressionValue(constructedClass, "getConstructedClass(...)");
            return mapClass(constructedClass);
        }
        DeclarationDescriptor containingDeclaration = descriptor.getContainingDeclaration();
        if (containingDeclaration instanceof PackageFragmentDescriptor) {
            Companion companion = INSTANCE;
            Intrinsics.checkNotNull(descriptor, "null cannot be cast to non-null type org.jetbrains.kotlin.descriptors.CallableMemberDescriptor");
            Type objectType = Type.getObjectType(companion.internalNameForPackageMemberOwner((CallableMemberDescriptor) descriptor, publicFacade));
            Intrinsics.checkNotNull(objectType);
            return objectType;
        }
        if (containingDeclaration instanceof ClassDescriptor) {
            return mapClass((ClassDescriptor) containingDeclaration);
        }
        throw new UnsupportedOperationException("Don't know how to map owner for " + descriptor);
    }

    /* JADX INFO: compiled from: KotlinTypeMapper.kt */
    @Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\u0018\u0000 \t2\u00020\u0001:\u0001\tB\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003¢\u0006\u0002\u0010\u0005R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u0011\u0010\u0004\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\u0007¨\u0006\n"}, d2 = {"Lorg/jetbrains/kotlin/codegen/state/KotlinTypeMapper$ContainingClassesInfo;", "", "facadeClassId", "Lorg/jetbrains/kotlin/name/ClassId;", "implClassId", "(Lorg/jetbrains/kotlin/name/ClassId;Lorg/jetbrains/kotlin/name/ClassId;)V", "getFacadeClassId", "()Lorg/jetbrains/kotlin/name/ClassId;", "getImplClassId", "Companion", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class ContainingClassesInfo {

        /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
        public static final Companion INSTANCE = new Companion(null);
        private final ClassId facadeClassId;
        private final ClassId implClassId;

        public ContainingClassesInfo(ClassId facadeClassId, ClassId implClassId) {
            Intrinsics.checkNotNullParameter(facadeClassId, "facadeClassId");
            Intrinsics.checkNotNullParameter(implClassId, "implClassId");
            this.facadeClassId = facadeClassId;
            this.implClassId = implClassId;
        }

        public final ClassId getFacadeClassId() {
            return this.facadeClassId;
        }

        public final ClassId getImplClassId() {
            return this.implClassId;
        }

        /* JADX INFO: compiled from: KotlinTypeMapper.kt */
        @Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0015\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0000¢\u0006\u0002\b\u0007J\u001d\u0010\b\u001a\u00020\u00042\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\nH\u0000¢\u0006\u0002\b\f¨\u0006\r"}, d2 = {"Lorg/jetbrains/kotlin/codegen/state/KotlinTypeMapper$ContainingClassesInfo$Companion;", "", "()V", "forClassMember", "Lorg/jetbrains/kotlin/codegen/state/KotlinTypeMapper$ContainingClassesInfo;", "classId", "Lorg/jetbrains/kotlin/name/ClassId;", "forClassMember$backend", "forPackageMember", "facadeName", "Lorg/jetbrains/kotlin/resolve/jvm/JvmClassName;", "partName", "forPackageMember$backend", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
        public static final class Companion {
            public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
                this();
            }

            private Companion() {
            }

            public final ContainingClassesInfo forPackageMember$backend(JvmClassName facadeName, JvmClassName partName) {
                Intrinsics.checkNotNullParameter(facadeName, "facadeName");
                Intrinsics.checkNotNullParameter(partName, "partName");
                ClassId classId = ClassId.topLevel(facadeName.getFqNameForTopLevelClassMaybeWithDollars());
                Intrinsics.checkNotNullExpressionValue(classId, "topLevel(...)");
                ClassId classId2 = ClassId.topLevel(partName.getFqNameForTopLevelClassMaybeWithDollars());
                Intrinsics.checkNotNullExpressionValue(classId2, "topLevel(...)");
                return new ContainingClassesInfo(classId, classId2);
            }

            public final ContainingClassesInfo forClassMember$backend(ClassId classId) {
                Intrinsics.checkNotNullParameter(classId, "classId");
                return new ContainingClassesInfo(classId, classId);
            }
        }
    }

    public static /* synthetic */ Type mapReturnType$default(KotlinTypeMapper kotlinTypeMapper, CallableDescriptor callableDescriptor, JvmSignatureWriter jvmSignatureWriter, int i, Object obj) {
        if ((i & 2) != 0) {
            jvmSignatureWriter = null;
        }
        return kotlinTypeMapper.mapReturnType(callableDescriptor, jvmSignatureWriter);
    }

    public final Type mapReturnType(CallableDescriptor descriptor, JvmSignatureWriter sw) {
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        KotlinType returnType = descriptor.getReturnType();
        if (returnType == null) {
            throw new IllegalStateException(("Function has no return type: " + descriptor).toString());
        }
        if (descriptor instanceof ConstructorDescriptor) {
            Type VOID_TYPE = Type.VOID_TYPE;
            Intrinsics.checkNotNullExpressionValue(VOID_TYPE, "VOID_TYPE");
            return VOID_TYPE;
        }
        if (CoroutineCodegenUtilKt.isSuspendFunctionNotSuspensionView(descriptor)) {
            return mapReturnType(CoroutineCodegenUtilKt.getOrCreateJvmSuspendFunctionView$default((SimpleFunctionDescriptor) descriptor, null, 2, null), sw);
        }
        if (DescriptorBasedTypeSignatureMappingKt.hasVoidReturnType(descriptor)) {
            if (sw != null) {
                sw.writeAsmType(Type.VOID_TYPE);
            }
            Type VOID_TYPE2 = Type.VOID_TYPE;
            Intrinsics.checkNotNullExpressionValue(VOID_TYPE2, "VOID_TYPE");
            return VOID_TYPE2;
        }
        if ((descriptor instanceof FunctionDescriptor) && forceBoxedReturnType((FunctionDescriptor) descriptor)) {
            KotlinType returnType2 = descriptor.getReturnType();
            Intrinsics.checkNotNull(returnType2);
            return mapType(returnType2, sw, TypeMappingMode.RETURN_TYPE_BOXED);
        }
        return mapReturnType(descriptor, sw, returnType);
    }

    private final Type mapReturnType(CallableDescriptor descriptor, JvmSignatureWriter sw, KotlinType returnType) {
        boolean zIsAnnotationClass = DescriptorUtils.isAnnotationClass(descriptor.getContainingDeclaration());
        if (sw == null || sw.skipGenericSignature()) {
            return mapType(returnType, sw, TypeMappingMode.INSTANCE.getModeForReturnTypeNoGeneric(zIsAnnotationClass));
        }
        TypeMappingMode typeMappingModeExtractTypeMappingModeFromAnnotation = TypeMappingUtil.extractTypeMappingModeFromAnnotation(descriptor, returnType, zIsAnnotationClass, false);
        if (typeMappingModeExtractTypeMappingModeFromAnnotation != null) {
            return mapType(returnType, sw, typeMappingModeExtractTypeMappingModeFromAnnotation);
        }
        return mapType(returnType, sw, TypeMappingModeExtensionsKt.getOptimalModeForReturnType(getTypeSystem(), returnType, zIsAnnotationClass));
    }

    public final Type mapSupertype(KotlinType type, JvmSignatureWriter signatureVisitor) {
        Intrinsics.checkNotNullParameter(type, "type");
        return mapType(type, signatureVisitor, TypeMappingMode.SUPER_TYPE);
    }

    public final Type mapTypeArgument(KotlinType type, JvmSignatureWriter signatureVisitor) {
        Intrinsics.checkNotNullParameter(type, "type");
        return mapType(type, signatureVisitor, TypeMappingMode.GENERIC_ARGUMENT);
    }

    @Override // org.jetbrains.kotlin.codegen.state.KotlinTypeMapperBase
    public Type mapClass(ClassifierDescriptor classifier) {
        Intrinsics.checkNotNullParameter(classifier, "classifier");
        SimpleType defaultType = classifier.getDefaultType();
        Intrinsics.checkNotNullExpressionValue(defaultType, "getDefaultType(...)");
        return mapType(defaultType, null, TypeMappingMode.CLASS_DECLARATION);
    }

    @Override // org.jetbrains.kotlin.codegen.state.KotlinTypeMapperBase
    public Type mapTypeCommon(KotlinTypeMarker type, TypeMappingMode mode) {
        Intrinsics.checkNotNullParameter(type, "type");
        Intrinsics.checkNotNullParameter(mode, "mode");
        return mapType((KotlinType) type, null, mode);
    }

    public final Type mapTypeAsDeclaration(KotlinType kotlinType) {
        Intrinsics.checkNotNullParameter(kotlinType, "kotlinType");
        return mapType(kotlinType, null, TypeMappingMode.CLASS_DECLARATION);
    }

    public final Type mapType(CallableDescriptor descriptor) {
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        KotlinType returnType = descriptor.getReturnType();
        Intrinsics.checkNotNull(returnType);
        return mapType$default(this, returnType, null, null, 6, null);
    }

    public final Type mapTypeAsDeclaration(CallableDescriptor descriptor) {
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        KotlinType returnType = descriptor.getReturnType();
        Intrinsics.checkNotNull(returnType);
        return mapTypeAsDeclaration(returnType);
    }

    public final JvmMethodGenericSignature mapAnnotationParameterSignature(PropertyDescriptor descriptor) {
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        BothSignatureWriter bothSignatureWriter = new BothSignatureWriter(BothSignatureWriter.Mode.METHOD);
        bothSignatureWriter.writeReturnType();
        KotlinType type = descriptor.getType();
        Intrinsics.checkNotNullExpressionValue(type, "getType(...)");
        mapType(type, bothSignatureWriter, TypeMappingMode.VALUE_FOR_ANNOTATION);
        bothSignatureWriter.writeReturnTypeEnd();
        JvmMethodGenericSignature jvmMethodGenericSignatureMakeJvmMethodSignature = bothSignatureWriter.makeJvmMethodSignature(mapAnnotationParameterName(descriptor));
        Intrinsics.checkNotNullExpressionValue(jvmMethodGenericSignatureMakeJvmMethodSignature, "makeJvmMethodSignature(...)");
        return jvmMethodGenericSignatureMakeJvmMethodSignature;
    }

    public final String mapAnnotationParameterName(PropertyDescriptor descriptor) {
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        PropertyGetterDescriptor getter = descriptor.getGetter();
        if (getter != null) {
            return mapFunctionName(getter, OwnerKind.IMPLEMENTATION);
        }
        String strAsString = descriptor.getShortName().asString();
        Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
        return strAsString;
    }

    public final Type mapType(ClassifierDescriptor descriptor) {
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        SimpleType defaultType = descriptor.getDefaultType();
        Intrinsics.checkNotNullExpressionValue(defaultType, "getDefaultType(...)");
        return mapType$default(this, defaultType, null, null, 6, null);
    }

    public static /* synthetic */ Type mapType$default(KotlinTypeMapper kotlinTypeMapper, KotlinType kotlinType, JvmSignatureWriter jvmSignatureWriter, TypeMappingMode typeMappingMode, int i, Object obj) {
        if ((i & 2) != 0) {
            jvmSignatureWriter = null;
        }
        if ((i & 4) != 0) {
            typeMappingMode = TypeMappingMode.DEFAULT;
        }
        return kotlinTypeMapper.mapType(kotlinType, jvmSignatureWriter, typeMappingMode);
    }

    public final Type mapType(KotlinType type, final JvmSignatureWriter signatureVisitor, TypeMappingMode mode) {
        Intrinsics.checkNotNullParameter(type, "type");
        Intrinsics.checkNotNullParameter(mode, "mode");
        if (this.isIrBackend) {
            throw new AssertionError("IR backend shouldn't call KotlinTypeMapper.mapType: " + type);
        }
        return (Type) DescriptorBasedTypeSignatureMappingKt.mapType(type, AsmTypeFactory.INSTANCE, mode, this.typeMappingConfiguration, signatureVisitor, new Function3<KotlinType, Type, TypeMappingMode, Unit>() { // from class: org.jetbrains.kotlin.codegen.state.KotlinTypeMapper.mapType.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(3);
            }

            @Override // kotlin.jvm.functions.Function3
            public /* bridge */ /* synthetic */ Unit invoke(KotlinType kotlinType, Type type2, TypeMappingMode typeMappingMode) {
                invoke2(kotlinType, type2, typeMappingMode);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(KotlinType ktType, Type asmType, TypeMappingMode typeMappingMode) {
                Intrinsics.checkNotNullParameter(ktType, "ktType");
                Intrinsics.checkNotNullParameter(asmType, "asmType");
                Intrinsics.checkNotNullParameter(typeMappingMode, "typeMappingMode");
                KotlinTypeMapper.this.writeGenericType(ktType, asmType, signatureVisitor, typeMappingMode);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void writeGenericType(KotlinType type, Type asmType, JvmSignatureWriter signatureVisitor, TypeMappingMode mode) {
        if (signatureVisitor == null) {
            return;
        }
        if (signatureVisitor.skipGenericSignature() || INSTANCE.hasNothingInNonContravariantPosition(type) || type.getArguments().isEmpty()) {
            signatureVisitor.writeAsmType(asmType);
            return;
        }
        PossiblyInnerType possiblyInnerTypeBuildPossiblyInnerType = TypeParameterUtilsKt.buildPossiblyInnerType(type);
        if (possiblyInnerTypeBuildPossiblyInnerType == null) {
            throw new IllegalStateException("possiblyInnerType with arguments should not be null".toString());
        }
        List<PossiblyInnerType> listSegments = possiblyInnerTypeBuildPossiblyInnerType.segments();
        Iterator<PossiblyInnerType> it = listSegments.iterator();
        int i = 0;
        while (true) {
            if (!it.hasNext()) {
                i = -1;
                break;
            } else if (!it.next().getArguments().isEmpty()) {
                break;
            } else {
                i++;
            }
        }
        if (i < 0 || listSegments.size() == 1) {
            signatureVisitor.writeClassBegin(asmType);
            writeGenericArguments(signatureVisitor, possiblyInnerTypeBuildPossiblyInnerType, mode);
        } else {
            PossiblyInnerType possiblyInnerType = listSegments.get(i);
            signatureVisitor.writeOuterClassBegin(asmType, mapType(possiblyInnerType.getClassDescriptor()).getInternalName());
            writeGenericArguments(signatureVisitor, possiblyInnerType, mode);
            writeInnerParts(listSegments, signatureVisitor, mode, i + 1);
        }
        signatureVisitor.writeClassEnd();
    }

    private final void writeInnerParts(List<PossiblyInnerType> innerTypesAsList, JvmSignatureWriter signatureVisitor, TypeMappingMode mode, int index) {
        for (PossiblyInnerType possiblyInnerType : innerTypesAsList.subList(index, innerTypesAsList.size())) {
            signatureVisitor.writeInnerClass(INSTANCE.getJvmShortName(possiblyInnerType.getClassDescriptor()));
            writeGenericArguments(signatureVisitor, possiblyInnerType, mode);
        }
    }

    private final void writeGenericArguments(JvmSignatureWriter signatureVisitor, PossiblyInnerType type, TypeMappingMode mode) {
        ClassDescriptor classDescriptor = type.getClassDescriptor();
        List<TypeParameterDescriptor> declaredTypeParameters = classDescriptor.getDeclaredTypeParameters();
        Intrinsics.checkNotNullExpressionValue(declaredTypeParameters, "getDeclaredTypeParameters(...)");
        List<TypeProjection> arguments = type.getArguments();
        if (classDescriptor instanceof FunctionClassDescriptor) {
            FunctionClassDescriptor functionClassDescriptor = (FunctionClassDescriptor) classDescriptor;
            if (functionClassDescriptor.hasBigArity() || Intrinsics.areEqual(functionClassDescriptor.getFunctionTypeKind(), FunctionTypeKind.KFunction.INSTANCE) || Intrinsics.areEqual(functionClassDescriptor.getFunctionTypeKind(), FunctionTypeKind.KSuspendFunction.INSTANCE)) {
                writeGenericArguments(signatureVisitor, CollectionsKt.listOf(CollectionsKt.last((List) arguments)), CollectionsKt.listOf(CollectionsKt.last((List) declaredTypeParameters)), mode);
                return;
            }
        }
        writeGenericArguments(signatureVisitor, arguments, declaredTypeParameters, mode);
    }

    private final void writeGenericArguments(JvmSignatureWriter signatureVisitor, List<? extends TypeProjection> arguments, List<? extends TypeParameterDescriptor> parameters, TypeMappingMode mode) {
        INSTANCE.writeGenericArguments(SimpleClassicTypeSystemContext.INSTANCE, signatureVisitor, arguments, parameters, mode, new Function3<KotlinTypeMarker, JvmSignatureWriter, TypeMappingMode, Type>() { // from class: org.jetbrains.kotlin.codegen.state.KotlinTypeMapper$writeGenericArguments$1$1
            {
                super(3);
            }

            @Override // kotlin.jvm.functions.Function3
            public final Type invoke(KotlinTypeMarker type, JvmSignatureWriter sw, TypeMappingMode mode2) {
                Intrinsics.checkNotNullParameter(type, "type");
                Intrinsics.checkNotNullParameter(sw, "sw");
                Intrinsics.checkNotNullParameter(mode2, "mode");
                return this.this$0.mapType((KotlinType) type, sw, mode2);
            }
        });
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ CallableMethod mapToCallableMethod$default(KotlinTypeMapper kotlinTypeMapper, FunctionDescriptor functionDescriptor, boolean z, OwnerKind ownerKind, ResolvedCall resolvedCall, int i, Object obj) {
        if ((i & 4) != 0) {
            ownerKind = null;
        }
        if ((i & 8) != 0) {
            resolvedCall = null;
        }
        return kotlinTypeMapper.mapToCallableMethod(functionDescriptor, z, ownerKind, resolvedCall);
    }

    private static final Function0<Method> mapToCallableMethod$mapDefaultCallback(final KotlinTypeMapper kotlinTypeMapper, final FunctionDescriptor functionDescriptor, final OwnerKind ownerKind) {
        if (kotlinTypeMapper.useOldManglingRulesForFunctionAcceptingInlineClass && !kotlinTypeMapper.useOldInlineClassesManglingScheme) {
            return new Function0<Method>() { // from class: org.jetbrains.kotlin.codegen.state.KotlinTypeMapper$mapToCallableMethod$mapDefaultCallback$1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final Method invoke() {
                    boolean useOldManglingRulesForFunctionAcceptingInlineClass = this.this$0.getUseOldManglingRulesForFunctionAcceptingInlineClass();
                    this.this$0.setUseOldManglingRulesForFunctionAcceptingInlineClass(true);
                    Method methodMapDefaultMethod = this.this$0.mapDefaultMethod(functionDescriptor, ownerKind);
                    this.this$0.setUseOldManglingRulesForFunctionAcceptingInlineClass(useOldManglingRulesForFunctionAcceptingInlineClass);
                    return methodMapDefaultMethod;
                }
            };
        }
        return new Function0<Method>() { // from class: org.jetbrains.kotlin.codegen.state.KotlinTypeMapper$mapToCallableMethod$mapDefaultCallback$2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Method invoke() {
                return this.this$0.mapDefaultMethod(functionDescriptor, ownerKind);
            }
        };
    }

    /* JADX WARN: Code duplicated, block: B:140:0x034b  */
    /* JADX WARN: Code duplicated, block: B:141:0x034e  */
    /* JADX WARN: Code duplicated, block: B:144:0x035a  */
    /* JADX WARN: Code duplicated, block: B:145:0x036e  */
    /* JADX WARN: Code duplicated, block: B:85:0x0225 A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:86:0x0227  */
    /* JADX WARN: Code duplicated, block: B:88:0x022e A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:89:0x0230  */
    /* JADX WARN: Code duplicated, block: B:90:0x0237  */
    /* JADX WARN: Code duplicated, block: B:95:0x0247  */
    public final CallableMethod mapToCallableMethod(FunctionDescriptor descriptor, boolean superCall, OwnerKind kind, ResolvedCall<?> resolvedCall) {
        JvmMethodSignature jvmMethodSignatureMapSignatureSkipGeneric$default;
        KotlinType returnType;
        Type typeMapClass;
        Type type;
        Type type2;
        SimpleType defaultType;
        int i;
        boolean z;
        boolean z2;
        boolean z3;
        Type type3;
        ReceiverParameterDescriptor extensionReceiverParameter;
        Type typeMapType$default;
        KotlinType kotlinType;
        Type typeMapClass2;
        int i2;
        boolean z4;
        FunctionDescriptor original;
        JvmMethodGenericSignature jvmMethodGenericSignatureMapSignature;
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        if ((descriptor instanceof ConstructorDescriptor) && kind != OwnerKind.ERASED_INLINE_CLASS) {
            ConstructorDescriptor constructorDescriptor = (ConstructorDescriptor) descriptor;
            ConstructorDescriptor original2 = constructorDescriptor.getOriginal();
            Intrinsics.checkNotNullExpressionValue(original2, "getOriginal(...)");
            JvmMethodSignature jvmMethodSignatureMapSignatureSkipGeneric$default2 = mapSignatureSkipGeneric$default(this, original2, null, 2, null);
            Type typeMapOwner = mapOwner(descriptor);
            ConstructorDescriptor original3 = constructorDescriptor.getOriginal();
            Intrinsics.checkNotNullExpressionValue(original3, "getOriginal(...)");
            return new CallableMethod(typeMapOwner, typeMapOwner, mapToCallableMethod$mapDefaultCallback(this, original3, OwnerKind.IMPLEMENTATION), jvmMethodSignatureMapSignatureSkipGeneric$default2, 183, null, null, null, null, null, original3.getReturnType(), false, false, false);
        }
        if (descriptor instanceof LocalVariableAccessorDescriptor) {
            ResolvedCall resolvedCall2 = (ResolvedCall) this.bindingContext.get(BindingContext.DELEGATED_PROPERTY_RESOLVED_CALL, descriptor);
            Intrinsics.checkNotNull(resolvedCall2);
            CallableDescriptor resultingDescriptor = resolvedCall2.getResultingDescriptor();
            Intrinsics.checkNotNullExpressionValue(resultingDescriptor, "getResultingDescriptor(...)");
            return mapToCallableMethod$default(this, (FunctionDescriptor) resultingDescriptor, false, null, null, 12, null);
        }
        DeclarationDescriptor containingDeclaration = descriptor.getOriginal().getContainingDeclaration();
        Intrinsics.checkNotNullExpressionValue(containingDeclaration, "getContainingDeclaration(...)");
        Companion companion = INSTANCE;
        FunctionDescriptor original4 = descriptor.getOriginal();
        Intrinsics.checkNotNullExpressionValue(original4, "getOriginal(...)");
        FunctionDescriptor functionDescriptorFindSuperDeclaration = companion.findSuperDeclaration(original4, superCall, this.jvmDefaultMode);
        if (containingDeclaration instanceof ClassDescriptor) {
            FunctionDescriptor functionDescriptorFindAnyDeclaration$backend = companion.findAnyDeclaration$backend(functionDescriptorFindSuperDeclaration);
            DeclarationDescriptor containingDeclaration2 = functionDescriptorFindAnyDeclaration$backend.getContainingDeclaration();
            Intrinsics.checkNotNull(containingDeclaration2, "null cannot be cast to non-null type org.jetbrains.kotlin.descriptors.ClassDescriptor");
            ClassDescriptor classDescriptor = (ClassDescriptor) containingDeclaration2;
            boolean zIsJvmInterface = JvmCodegenUtil.isJvmInterface(classDescriptor);
            boolean zIsJvmInterface2 = JvmCodegenUtil.isJvmInterface(containingDeclaration);
            boolean z5 = true;
            boolean z6 = zIsJvmInterface2 && zIsJvmInterface;
            functionDescriptorFindSuperDeclaration = companion.findBaseDeclaration(functionDescriptorFindSuperDeclaration).getOriginal();
            Intrinsics.checkNotNullExpressionValue(functionDescriptorFindSuperDeclaration, "getOriginal(...)");
            DeclarationDescriptor containingDeclaration3 = functionDescriptorFindSuperDeclaration.getContainingDeclaration();
            Intrinsics.checkNotNull(containingDeclaration3, "null cannot be cast to non-null type org.jetbrains.kotlin.descriptors.ClassDescriptor");
            ClassDescriptor classDescriptor2 = (ClassDescriptor) containingDeclaration3;
            ClassDescriptor classDescriptor3 = classDescriptor2;
            boolean z7 = JvmCodegenUtil.isJvmInterface(classDescriptor3) && JvmAnnotationUtilKt.isCompiledToJvmDefault(functionDescriptorFindSuperDeclaration, this.jvmDefaultMode);
            if (JvmCodegenUtil.isJvmInterface(classDescriptor3) && !JvmAnnotationUtilKt.isCompiledToJvmDefault(functionDescriptorFindSuperDeclaration, this.jvmDefaultMode)) {
                typeMapClass2 = mapDefaultImpls(classDescriptor2);
            } else {
                typeMapClass2 = mapClass(classDescriptor2);
            }
            if (z6 && (superCall || Intrinsics.areEqual(descriptor.getVisibility(), DescriptorVisibilities.PRIVATE) || companion.isAccessor(descriptor))) {
                ClassifierDescriptor classifierDescriptor = (ClassifierDescriptor) containingDeclaration;
                Type typeMapClass3 = mapClass(classifierDescriptor);
                ClassDescriptor classDescriptor4 = (ClassDescriptor) containingDeclaration;
                SimpleType defaultType2 = classDescriptor4.getDefaultType();
                if ((classDescriptor instanceof JavaClassDescriptor) || (JvmAnnotationUtilKt.isCompiledToJvmDefault(functionDescriptorFindAnyDeclaration$backend, this.jvmDefaultMode) && !companion.isAccessor(descriptor))) {
                    JvmMethodSignature jvmMethodSignatureMapSignatureSkipGeneric$default3 = mapSignatureSkipGeneric$default(this, functionDescriptorFindSuperDeclaration, null, 2, null);
                    returnType = functionDescriptorFindSuperDeclaration.getReturnType();
                    jvmMethodSignatureMapSignatureSkipGeneric$default = jvmMethodSignatureMapSignatureSkipGeneric$default3;
                    type = typeMapClass2;
                    z2 = z7;
                    typeMapClass = typeMapClass3;
                    type2 = typeMapClass;
                    defaultType = defaultType2;
                    z = true;
                    i = 183;
                } else {
                    FunctionDescriptor original5 = descriptor.getOriginal();
                    Intrinsics.checkNotNullExpressionValue(original5, "getOriginal(...)");
                    JvmMethodSignature jvmMethodSignatureMapSignatureSkipGeneric = mapSignatureSkipGeneric(original5, OwnerKind.DEFAULT_IMPLS);
                    KotlinType returnType2 = original5.getReturnType();
                    if ((descriptor instanceof AccessorForCallableDescriptor) && JvmAnnotationUtilKt.isCompiledToJvmDefault(((AccessorForCallableDescriptor) descriptor).getCalleeDescriptor(), this.jvmDefaultMode)) {
                        typeMapClass = mapClass(classifierDescriptor);
                        returnType = returnType2;
                        jvmMethodSignatureMapSignatureSkipGeneric$default = jvmMethodSignatureMapSignatureSkipGeneric;
                        type = typeMapClass2;
                        z2 = z7;
                        type2 = typeMapClass3;
                        defaultType = defaultType2;
                        z = true;
                        i = 184;
                    } else {
                        typeMapClass = mapDefaultImpls(classDescriptor4);
                        returnType = returnType2;
                        jvmMethodSignatureMapSignatureSkipGeneric$default = jvmMethodSignatureMapSignatureSkipGeneric;
                        type = typeMapClass2;
                        z2 = z7;
                        type2 = typeMapClass3;
                        defaultType = defaultType2;
                        i = 184;
                        z = false;
                    }
                }
            } else {
                boolean z8 = InlineClassesUtilsKt.isInlineClass(containingDeclaration) && (!companion.isAccessor(functionDescriptorFindSuperDeclaration) || TypeMapperUtilsKt.isInlineClassConstructorAccessor(functionDescriptorFindSuperDeclaration));
                FunctionDescriptor functionDescriptor = z8 ? descriptor : functionDescriptorFindSuperDeclaration;
                boolean z9 = z8 && functionDescriptor.getKind() == CallableMemberDescriptor.Kind.FAKE_OVERRIDE && overridesJvmDefault(functionDescriptor);
                if (z9) {
                    if (z6) {
                        i2 = 185;
                    } else if (z9) {
                        z4 = true;
                        z5 = false;
                        i2 = 182;
                    } else {
                        if (superCall) {
                            i2 = 183;
                        } else {
                            i2 = 183;
                        }
                        z5 = false;
                    }
                    z4 = false;
                } else {
                    FunctionDescriptor functionDescriptor2 = functionDescriptor;
                    if ((DescriptorUtils.isStaticDeclaration(functionDescriptor2) && !(functionDescriptor instanceof ImportedFromObjectCallableDescriptor)) || companion.isStaticAccessor(functionDescriptor) || CodegenUtilKt.isJvmStaticInObjectOrClassOrInterface(functionDescriptor2) || z8) {
                        z5 = zIsJvmInterface2 && (containingDeclaration instanceof JavaClassDescriptor);
                        i2 = 184;
                    } else if (z6) {
                        i2 = 185;
                    } else if (z9) {
                        z4 = true;
                        z5 = false;
                        i2 = 182;
                    } else {
                        if (superCall || DescriptorVisibilities.isPrivate(functionDescriptor.getVisibility())) {
                            i2 = 183;
                        } else {
                            i2 = 182;
                        }
                        z5 = false;
                    }
                    z4 = false;
                }
                FunctionDescriptor functionDescriptor3 = (FunctionDescriptor) SpecialBuiltinMembers.getOverriddenBuiltinReflectingJvmDescriptor(functionDescriptor.getOriginal());
                if (functionDescriptor3 != null && !superCall && !z8) {
                    original = functionDescriptor3.getOriginal();
                } else {
                    original = functionDescriptor.getOriginal();
                }
                FunctionDescriptor functionDescriptor4 = original;
                Intrinsics.checkNotNull(functionDescriptor4);
                if (z8 && !z9) {
                    jvmMethodGenericSignatureMapSignature = mapSignatureForInlineErasedClassSkipGeneric(functionDescriptor4);
                } else {
                    jvmMethodGenericSignatureMapSignature = mapSignature(functionDescriptor4, resolvedCall, OwnerKind.IMPLEMENTATION, true, false);
                }
                JvmMethodSignature jvmMethodSignature = jvmMethodGenericSignatureMapSignature;
                KotlinType nullableAnyType = (functionDescriptor4.isSuspend() && CoroutineCodegenUtilKt.originalReturnTypeOfSuspendFunctionReturningUnboxedInlineClass(functionDescriptor4, this) == null) ? DescriptorUtilsKt.getBuiltIns(functionDescriptor).getNullableAnyType() : functionDescriptor4.getReturnType();
                if ((!zIsJvmInterface2 || zIsJvmInterface) && !(containingDeclaration instanceof FunctionClassDescriptor)) {
                    classDescriptor = (ClassDescriptor) containingDeclaration;
                }
                typeMapClass = mapClass(classDescriptor);
                type2 = typeMapClass;
                returnType = nullableAnyType;
                jvmMethodSignatureMapSignatureSkipGeneric$default = jvmMethodSignature;
                type = typeMapClass2;
                z2 = z7;
                functionDescriptorFindSuperDeclaration = functionDescriptor;
                defaultType = classDescriptor.getDefaultType();
                z = z5;
                i = i2;
                z3 = z4;
            }
            if (ExpressionTypingUtils.isLocalFunction(functionDescriptorFindSuperDeclaration)) {
                type3 = typeMapClass;
            } else {
                type3 = null;
            }
            extensionReceiverParameter = functionDescriptorFindSuperDeclaration.getOriginal().getExtensionReceiverParameter();
            if (extensionReceiverParameter != null) {
                KotlinType type4 = extensionReceiverParameter.getType();
                typeMapType$default = mapType$default(this, type4, null, null, 6, null);
                kotlinType = type4;
            } else {
                typeMapType$default = null;
                kotlinType = null;
            }
            return new CallableMethod(typeMapClass, type, mapToCallableMethod$mapDefaultCallback(this, functionDescriptorFindSuperDeclaration, companion.getKindForDefaultImplCall(functionDescriptorFindSuperDeclaration)), jvmMethodSignatureMapSignatureSkipGeneric$default, i, type2, defaultType, typeMapType$default, kotlinType, type3, returnType, z, z2, z3);
        }
        FunctionDescriptor original6 = functionDescriptorFindSuperDeclaration.getOriginal();
        Intrinsics.checkNotNullExpressionValue(original6, "getOriginal(...)");
        jvmMethodSignatureMapSignatureSkipGeneric$default = mapSignatureSkipGeneric$default(this, original6, null, 2, null);
        returnType = original6.getReturnType();
        Type typeMapOwner2 = mapOwner(functionDescriptorFindSuperDeclaration);
        if (containingDeclaration instanceof PackageFragmentDescriptor) {
            type2 = null;
            defaultType = null;
            typeMapClass = typeMapOwner2;
            type = typeMapClass;
            i = 184;
        } else if (functionDescriptorFindSuperDeclaration instanceof ConstructorDescriptor) {
            type2 = null;
            defaultType = null;
            typeMapClass = typeMapOwner2;
            type = typeMapClass;
            i = 183;
        } else {
            DeclarationDescriptor containingDeclaration4 = functionDescriptorFindSuperDeclaration.getContainingDeclaration();
            Intrinsics.checkNotNullExpressionValue(containingDeclaration4, "getContainingDeclaration(...)");
            ClassDescriptor classDescriptor5 = containingDeclaration4 instanceof ClassDescriptor ? (ClassDescriptor) containingDeclaration4 : null;
            typeMapClass = typeMapOwner2;
            type = typeMapClass;
            type2 = type;
            defaultType = classDescriptor5 != null ? classDescriptor5.getDefaultType() : null;
            i = 182;
        }
        z = false;
        z2 = false;
        z3 = false;
        if (ExpressionTypingUtils.isLocalFunction(functionDescriptorFindSuperDeclaration)) {
            type3 = typeMapClass;
        } else {
            type3 = null;
        }
        extensionReceiverParameter = functionDescriptorFindSuperDeclaration.getOriginal().getExtensionReceiverParameter();
        if (extensionReceiverParameter != null) {
            KotlinType type5 = extensionReceiverParameter.getType();
            typeMapType$default = mapType$default(this, type5, null, null, 6, null);
            kotlinType = type5;
        } else {
            typeMapType$default = null;
            kotlinType = null;
        }
        return new CallableMethod(typeMapClass, type, mapToCallableMethod$mapDefaultCallback(this, functionDescriptorFindSuperDeclaration, companion.getKindForDefaultImplCall(functionDescriptorFindSuperDeclaration)), jvmMethodSignatureMapSignatureSkipGeneric$default, i, type2, defaultType, typeMapType$default, kotlinType, type3, returnType, z, z2, z3);
    }

    private final boolean overridesJvmDefault(CallableMemberDescriptor callableMemberDescriptor) {
        if (callableMemberDescriptor.getKind() == CallableMemberDescriptor.Kind.FAKE_OVERRIDE) {
            Collection<? extends CallableMemberDescriptor> overriddenDescriptors = callableMemberDescriptor.getOverriddenDescriptors();
            Intrinsics.checkNotNullExpressionValue(overriddenDescriptors, "getOverriddenDescriptors(...)");
            Collection<? extends CallableMemberDescriptor> collection = overriddenDescriptors;
            if (collection.isEmpty()) {
                return false;
            }
            for (CallableMemberDescriptor callableMemberDescriptor2 : collection) {
                Intrinsics.checkNotNull(callableMemberDescriptor2);
                if (overridesJvmDefault(callableMemberDescriptor2)) {
                    return true;
                }
            }
            return false;
        }
        if (JvmAnnotationUtilKt.isCompiledToJvmDefault(callableMemberDescriptor, this.jvmDefaultMode)) {
            return true;
        }
        DeclarationDescriptor containingDeclaration = callableMemberDescriptor.getContainingDeclaration();
        JavaClassDescriptor javaClassDescriptor = containingDeclaration instanceof JavaClassDescriptor ? (JavaClassDescriptor) containingDeclaration : null;
        return (javaClassDescriptor != null ? javaClassDescriptor.getKind() : null) == ClassKind.INTERFACE && callableMemberDescriptor.getModality() != Modality.ABSTRACT;
    }

    public final String mapFunctionName(FunctionDescriptor descriptor, OwnerKind kind) {
        SamType samType;
        String strAsString;
        String str;
        String jvmName;
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        if (!(descriptor instanceof JavaCallableMemberDescriptor) && (jvmName = DescriptorUtils.getJvmName(descriptor)) != null) {
            return jvmName;
        }
        FunctionDescriptor functionDescriptor = descriptor;
        String jvmMethodNameIfSpecial = SpecialBuiltinMembers.getJvmMethodNameIfSpecial(functionDescriptor);
        if (jvmMethodNameIfSpecial != null) {
            return jvmMethodNameIfSpecial;
        }
        if (descriptor instanceof PropertyAccessorDescriptor) {
            PropertyDescriptor correspondingProperty = ((PropertyAccessorDescriptor) descriptor).getCorrespondingProperty();
            Intrinsics.checkNotNullExpressionValue(correspondingProperty, "getCorrespondingProperty(...)");
            DeclarationDescriptor containingDeclaration = correspondingProperty.getContainingDeclaration();
            Intrinsics.checkNotNullExpressionValue(containingDeclaration, "getContainingDeclaration(...)");
            if (DescriptorUtils.isAnnotationClass(containingDeclaration) && !AnnotationUtilKt.hasJvmStaticAnnotation(correspondingProperty) && !AnnotationUtilKt.hasJvmStaticAnnotation(descriptor)) {
                String strAsString2 = correspondingProperty.getShortName().asString();
                Intrinsics.checkNotNullExpressionValue(strAsString2, "asString(...)");
                return strAsString2;
            }
            ClassDescriptor classDescriptor = containingDeclaration instanceof ClassDescriptor ? (ClassDescriptor) containingDeclaration : null;
            if (classDescriptor != null && hasJavaLangRecordSupertype(classDescriptor)) {
                String strAsString3 = correspondingProperty.getShortName().asString();
                Intrinsics.checkNotNullExpressionValue(strAsString3, "asString(...)");
                return strAsString3;
            }
            boolean z = correspondingProperty instanceof AccessorForPropertyDescriptor;
            if (z) {
                strAsString = ((AccessorForPropertyDescriptor) correspondingProperty).getAccessorSuffix();
            } else {
                strAsString = correspondingProperty.getShortName().asString();
                Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
            }
            if (descriptor instanceof PropertyGetterDescriptor) {
                str = JvmAbi.getterName(strAsString);
            } else {
                str = JvmAbi.setterName(strAsString);
            }
            if (z) {
                str = "access$" + str;
            }
            return mangleMemberNameIfRequired(str, functionDescriptor, kind);
        }
        FunctionDescriptor functionDescriptor2 = descriptor;
        if (ExpressionTypingUtils.isFunctionLiteral(functionDescriptor2)) {
            PsiElement sourceFromDescriptor = DescriptorToSourceUtils.getSourceFromDescriptor(functionDescriptor2);
            if (sourceFromDescriptor instanceof KtFunctionLiteral) {
                PsiElement parent = ((KtFunctionLiteral) sourceFromDescriptor).getLightParent();
                if ((parent instanceof KtLambdaExpression) && (samType = (SamType) this.bindingContext.get(CodegenBinding.SAM_VALUE, (KtExpression) parent)) != null) {
                    String strAsString4 = samType.getOriginalAbstractMethod().getShortName().asString();
                    Intrinsics.checkNotNullExpressionValue(strAsString4, "asString(...)");
                    return strAsString4;
                }
            }
            String strAsString5 = OperatorNameConventions.INVOKE.asString();
            Intrinsics.checkNotNull(strAsString5);
            return strAsString5;
        }
        if (ExpressionTypingUtils.isLocalFunction(functionDescriptor2) || ExpressionTypingUtils.isFunctionExpression(functionDescriptor2)) {
            String strAsString6 = OperatorNameConventions.INVOKE.asString();
            Intrinsics.checkNotNullExpressionValue(strAsString6, "asString(...)");
            return strAsString6;
        }
        String strAsString7 = descriptor.getShortName().asString();
        Intrinsics.checkNotNullExpressionValue(strAsString7, "asString(...)");
        return mangleMemberNameIfRequired(strAsString7, functionDescriptor, kind);
    }

    private final boolean hasJavaLangRecordSupertype(ClassDescriptor classDescriptor) {
        Collection<KotlinType> collectionMo7438getSupertypes = classDescriptor.getTypeConstructor().mo7438getSupertypes();
        Intrinsics.checkNotNullExpressionValue(collectionMo7438getSupertypes, "getSupertypes(...)");
        Collection<KotlinType> collection = collectionMo7438getSupertypes;
        if (collection.isEmpty()) {
            return false;
        }
        Iterator<T> it = collection.iterator();
        while (it.hasNext()) {
            if (KotlinBuiltIns.isConstructedFromGivenClass((KotlinType) it.next(), JdkClassesKt.getJAVA_LANG_RECORD_FQ_NAME())) {
                return true;
            }
        }
        return false;
    }

    private final String mangleMemberNameIfRequired(String name2, CallableMemberDescriptor descriptor, OwnerKind kind) {
        String partSimpleNameForMangling;
        DeclarationDescriptor containingDeclaration = descriptor.getContainingDeclaration();
        Intrinsics.checkNotNullExpressionValue(containingDeclaration, "getContainingDeclaration(...)");
        if ((containingDeclaration instanceof ScriptDescriptor) && (descriptor instanceof PropertyDescriptor)) {
            return name2;
        }
        if (InlineClassDescriptorResolver.isSynthesizedBoxMethod(descriptor)) {
            return BOX_JVM_METHOD_NAME;
        }
        if (InlineClassDescriptorResolver.isSynthesizedUnboxMethod(descriptor)) {
            return UNBOX_JVM_METHOD_NAME;
        }
        if (InlineClassDescriptorResolver.isSpecializedEqualsMethod(descriptor)) {
            return name2;
        }
        boolean z = descriptor instanceof ConstructorDescriptor;
        if (z) {
            if (kind != OwnerKind.ERASED_INLINE_CLASS) {
                return name2;
            }
            name2 = JvmAbi.ERASED_INLINE_CONSTRUCTOR_NAME;
        }
        if (kind != OwnerKind.PROPERTY_REFERENCE_SIGNATURE || isPropertyWithGetterSignaturePresent(descriptor)) {
            String manglingSuffixBasedOnKotlinSignature = InlineClassManglingUtilsKt.getManglingSuffixBasedOnKotlinSignature(descriptor, this.shouldMangleByReturnType, this.useOldManglingRulesForFunctionAcceptingInlineClass);
            if (manglingSuffixBasedOnKotlinSignature != null) {
                name2 = name2 + manglingSuffixBasedOnKotlinSignature;
            } else if (kind == OwnerKind.ERASED_INLINE_CLASS) {
                name2 = name2 + JvmAbi.IMPL_SUFFIX_FOR_INLINE_CLASS_MEMBERS;
            }
        }
        String strSanitizeNameIfNeeded = JvmCodegenUtil.sanitizeNameIfNeeded(name2, this.languageVersionSettings);
        Intrinsics.checkNotNullExpressionValue(strSanitizeNameIfNeeded, "sanitizeNameIfNeeded(...)");
        CallableMemberDescriptor callableMemberDescriptor = descriptor;
        if (DescriptorUtils.isTopLevelDeclaration(callableMemberDescriptor)) {
            return (!DescriptorVisibilities.isPrivate(descriptor.getVisibility()) || z || Intrinsics.areEqual(MethodDescription.TYPE_INITIALIZER_INTERNAL_NAME, strSanitizeNameIfNeeded) || (partSimpleNameForMangling = INSTANCE.getPartSimpleNameForMangling(descriptor)) == null) ? strSanitizeNameIfNeeded : strSanitizeNameIfNeeded + '$' + partSimpleNameForMangling;
        }
        return (z || descriptor.getVisibility() != DescriptorVisibilities.INTERNAL || DescriptorUtilsKt.isPublishedApi(callableMemberDescriptor)) ? strSanitizeNameIfNeeded : InternalNameMapper.INSTANCE.mangleInternalName(strSanitizeNameIfNeeded, getModuleName(descriptor));
    }

    private final boolean isPropertyWithGetterSignaturePresent(CallableMemberDescriptor callableMemberDescriptor) {
        PropertyDescriptor correspondingProperty;
        if (callableMemberDescriptor instanceof PropertyDescriptor) {
            correspondingProperty = (PropertyDescriptor) callableMemberDescriptor;
        } else {
            if (!(callableMemberDescriptor instanceof PropertyAccessorDescriptor)) {
                return false;
            }
            correspondingProperty = ((PropertyAccessorDescriptor) callableMemberDescriptor).getCorrespondingProperty();
            Intrinsics.checkNotNullExpressionValue(correspondingProperty, "getCorrespondingProperty(...)");
        }
        return PropertyCodegen.isReferenceablePropertyWithGetter(correspondingProperty);
    }

    private final String getModuleName(CallableMemberDescriptor descriptor) {
        String jvmModuleNameForDeserializedDescriptor = ModuleNameKt.getJvmModuleNameForDeserializedDescriptor(descriptor);
        return jvmModuleNameForDeserializedDescriptor == null ? this.moduleName : jvmModuleNameForDeserializedDescriptor;
    }

    public final Method mapAsmMethod(FunctionDescriptor descriptor) {
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        Method asmMethod = mapSignature(descriptor).getAsmMethod();
        Intrinsics.checkNotNullExpressionValue(asmMethod, "getAsmMethod(...)");
        return asmMethod;
    }

    public final Method mapPropertyReferenceSignature(FunctionDescriptor descriptor) {
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        Method asmMethod = mapSignature(descriptor, OwnerKind.PROPERTY_REFERENCE_SIGNATURE, true).getAsmMethod();
        Intrinsics.checkNotNullExpressionValue(asmMethod, "getAsmMethod(...)");
        return asmMethod;
    }

    public final Method mapAsmMethod(FunctionDescriptor descriptor, OwnerKind kind) {
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        Intrinsics.checkNotNullParameter(kind, "kind");
        Method asmMethod = mapSignature(descriptor, kind, true).getAsmMethod();
        Intrinsics.checkNotNullExpressionValue(asmMethod, "getAsmMethod(...)");
        return asmMethod;
    }

    private final JvmMethodGenericSignature mapSignature(FunctionDescriptor f) {
        return mapSignature(f, OwnerKind.IMPLEMENTATION, true);
    }

    public final JvmMethodSignature mapSignatureForInlineErasedClassSkipGeneric(FunctionDescriptor f) {
        Intrinsics.checkNotNullParameter(f, "f");
        return mapSignatureSkipGeneric(f, OwnerKind.ERASED_INLINE_CLASS);
    }

    public final JvmMethodGenericSignature mapSignatureForBoxMethodOfInlineClass(FunctionDescriptor f) {
        Intrinsics.checkNotNullParameter(f, "f");
        return mapSignature(f, OwnerKind.IMPLEMENTATION, true);
    }

    public final JvmMethodGenericSignature mapSignatureForSpecializedEqualsOfInlineClass(FunctionDescriptor f) {
        Intrinsics.checkNotNullParameter(f, "f");
        return mapSignature(f, OwnerKind.IMPLEMENTATION, true);
    }

    public static /* synthetic */ JvmMethodSignature mapSignatureSkipGeneric$default(KotlinTypeMapper kotlinTypeMapper, FunctionDescriptor functionDescriptor, OwnerKind ownerKind, int i, Object obj) {
        if ((i & 2) != 0) {
            ownerKind = OwnerKind.IMPLEMENTATION;
        }
        return kotlinTypeMapper.mapSignatureSkipGeneric(functionDescriptor, ownerKind);
    }

    public final JvmMethodSignature mapSignatureSkipGeneric(FunctionDescriptor f, OwnerKind kind) {
        Intrinsics.checkNotNullParameter(f, "f");
        Intrinsics.checkNotNullParameter(kind, "kind");
        return mapSignature(f, kind, true);
    }

    public final JvmMethodGenericSignature mapSignatureWithGeneric(FunctionDescriptor f, OwnerKind kind) {
        Intrinsics.checkNotNullParameter(f, "f");
        Intrinsics.checkNotNullParameter(kind, "kind");
        return mapSignature(f, kind, false);
    }

    public final JvmMethodGenericSignature mapSignatureWithGeneric(FunctionDescriptor f, OwnerKind kind, boolean hasSpecialBridge) {
        Intrinsics.checkNotNullParameter(f, "f");
        Intrinsics.checkNotNullParameter(kind, "kind");
        return mapSignature(f, null, kind, false, hasSpecialBridge);
    }

    private final JvmMethodGenericSignature mapSignature(FunctionDescriptor f, OwnerKind kind, boolean skipGenericSignature) {
        return mapSignature(f, null, kind, skipGenericSignature, false);
    }

    private final JvmMethodGenericSignature mapSignature(FunctionDescriptor f, ResolvedCall<?> resolvedCall, OwnerKind kind, boolean skipGenericSignature, boolean hasSpecialBridge) {
        Sequence<KotlinType> map;
        Pair<KotlinType, Type> pairExtractPolymorphicReturnType;
        if (f.getInitialSignatureDescriptor() != null && !Intrinsics.areEqual(f, f.getInitialSignatureDescriptor()) && (SpecialBuiltinMembers.getOverriddenBuiltinReflectingJvmDescriptor(f) == null || (f.getContainingDeclaration().getOriginal() instanceof JavaClassDescriptor))) {
            FunctionDescriptor initialSignatureDescriptor = f.getInitialSignatureDescriptor();
            Intrinsics.checkNotNull(initialSignatureDescriptor);
            return mapSignature(initialSignatureDescriptor, kind, skipGenericSignature);
        }
        if (f instanceof TypeAliasConstructorDescriptor) {
            return mapSignature(((TypeAliasConstructorDescriptor) f).getUnderlyingConstructorDescriptor().getOriginal(), kind, skipGenericSignature);
        }
        if (f instanceof FunctionImportedFromObject) {
            return mapSignature(((FunctionImportedFromObject) f).getCallableFromObject(), kind, skipGenericSignature);
        }
        if (CoroutineCodegenUtilKt.isSuspendFunctionNotSuspensionView(f)) {
            return mapSignature(CoroutineCodegenUtilKt.getOrCreateJvmSuspendFunctionView$default(f, null, 2, null), kind, skipGenericSignature);
        }
        FunctionDescriptor functionDescriptor = f;
        if (JvmCodegenUtil.isDeclarationOfBigArityFunctionInvoke(functionDescriptor) || JvmCodegenUtil.isDeclarationOfBigArityCreateCoroutineMethod(functionDescriptor)) {
            KotlinBuiltIns builtIns = DescriptorUtilsKt.getBuiltIns(functionDescriptor);
            SimpleType arrayType = builtIns.getArrayType(Variance.INVARIANT, builtIns.getNullableAnyType());
            Intrinsics.checkNotNullExpressionValue(arrayType, "getArrayType(...)");
            return mapSignatureWithCustomParameters(f, kind, SequencesKt.sequenceOf(arrayType), null, null, false, false);
        }
        if (!this.languageVersionSettings.supportsFeature(LanguageFeature.PolymorphicSignature) || !JvmCodegenUtil.isPolymorphicSignature(f)) {
            List<ValueParameterDescriptor> valueParameters = f.getValueParameters();
            Intrinsics.checkNotNullExpressionValue(valueParameters, "getValueParameters(...)");
            map = SequencesKt.map(CollectionsKt.asSequence(valueParameters), new Function1<ValueParameterDescriptor, KotlinType>() { // from class: org.jetbrains.kotlin.codegen.state.KotlinTypeMapper.mapSignature.1
                @Override // kotlin.jvm.functions.Function1
                public final KotlinType invoke(ValueParameterDescriptor valueParameterDescriptor) {
                    return valueParameterDescriptor.getType();
                }
            });
            pairExtractPolymorphicReturnType = TuplesKt.to(null, null);
        } else {
            if (resolvedCall == null) {
                throw new UnsupportedOperationException("Cannot determine polymorphic signature without a resolved call: " + f);
            }
            map = extractPolymorphicParameterTypes(resolvedCall);
            pairExtractPolymorphicReturnType = extractPolymorphicReturnType(resolvedCall);
        }
        return mapSignatureWithCustomParameters(f, kind, map, pairExtractPolymorphicReturnType.component1(), pairExtractPolymorphicReturnType.component2(), skipGenericSignature, hasSpecialBridge);
    }

    public final JvmMethodGenericSignature mapSignatureWithCustomParameters(FunctionDescriptor f, OwnerKind kind, List<? extends ValueParameterDescriptor> valueParameters, boolean skipGenericSignature) {
        Intrinsics.checkNotNullParameter(f, "f");
        Intrinsics.checkNotNullParameter(kind, "kind");
        Intrinsics.checkNotNullParameter(valueParameters, "valueParameters");
        return mapSignatureWithCustomParameters(f, kind, SequencesKt.map(CollectionsKt.asSequence(valueParameters), new Function1<ValueParameterDescriptor, KotlinType>() { // from class: org.jetbrains.kotlin.codegen.state.KotlinTypeMapper.mapSignatureWithCustomParameters.1
            @Override // kotlin.jvm.functions.Function1
            public final KotlinType invoke(ValueParameterDescriptor it) {
                Intrinsics.checkNotNullParameter(it, "it");
                KotlinType type = it.getType();
                Intrinsics.checkNotNullExpressionValue(type, "getType(...)");
                return type;
            }
        }), null, null, skipGenericSignature, false);
    }

    private final JvmMethodGenericSignature mapSignatureWithCustomParameters(FunctionDescriptor f, OwnerKind kind, Sequence<? extends KotlinType> valueParameterTypes, KotlinType customReturnType, Type customReturnAsmType, boolean skipGenericSignature, boolean hasSpecialBridge) {
        BothSignatureWriter jvmSignatureWriter;
        SimpleType receiverType;
        SimpleType defaultType;
        if (skipGenericSignature || (f instanceof AccessorForCallableDescriptor)) {
            jvmSignatureWriter = new JvmSignatureWriter();
        } else {
            jvmSignatureWriter = new BothSignatureWriter(BothSignatureWriter.Mode.METHOD);
        }
        if (f instanceof ClassConstructorDescriptor) {
            jvmSignatureWriter.writeParametersStart();
            ClassConstructorDescriptor classConstructorDescriptor = (ClassConstructorDescriptor) f;
            writeAdditionalConstructorParameters(classConstructorDescriptor, jvmSignatureWriter);
            Iterator<? extends KotlinType> it = valueParameterTypes.iterator();
            while (it.hasNext()) {
                writeParameter(jvmSignatureWriter, it.next(), f);
            }
            if (f instanceof AccessorForConstructorDescriptor) {
                Companion companion = INSTANCE;
                JvmMethodParameterKind jvmMethodParameterKind = JvmMethodParameterKind.CONSTRUCTOR_MARKER;
                Type DEFAULT_CONSTRUCTOR_MARKER = AsmTypes.DEFAULT_CONSTRUCTOR_MARKER;
                Intrinsics.checkNotNullExpressionValue(DEFAULT_CONSTRUCTOR_MARKER, "DEFAULT_CONSTRUCTOR_MARKER");
                companion.writeParameter(jvmSignatureWriter, jvmMethodParameterKind, DEFAULT_CONSTRUCTOR_MARKER);
            }
            if (kind == OwnerKind.ERASED_INLINE_CLASS) {
                jvmSignatureWriter.writeReturnType();
                jvmSignatureWriter.writeAsmType(mapType(classConstructorDescriptor.getContainingDeclaration()));
                jvmSignatureWriter.writeReturnTypeEnd();
            } else {
                INSTANCE.writeVoidReturn(jvmSignatureWriter);
            }
        } else {
            FunctionDescriptor functionDescriptor = f;
            CallableMemberDescriptor directMember = DescriptorUtils.getDirectMember(functionDescriptor);
            Intrinsics.checkNotNullExpressionValue(directMember, "getDirectMember(...)");
            int i = WhenMappings.$EnumSwitchMapping$0[kind.ordinal()];
            if (i == 1) {
                ReceiverTypeAndTypeParameters receiverTypeAndTypeParametersPatchTypeParametersForDefaultImplMethod = TypeMapperUtilsKt.patchTypeParametersForDefaultImplMethod(directMember);
                List<TypeParameterDescriptor> typeParameters = receiverTypeAndTypeParametersPatchTypeParametersForDefaultImplMethod.getTypeParameters();
                List<TypeParameterDescriptor> typeParameters2 = directMember.getTypeParameters();
                Intrinsics.checkNotNullExpressionValue(typeParameters2, "getTypeParameters(...)");
                writeFormalTypeParameters(CollectionsKt.plus((Collection) typeParameters, (Iterable) typeParameters2), jvmSignatureWriter);
                receiverType = receiverTypeAndTypeParametersPatchTypeParametersForDefaultImplMethod.getReceiverType();
            } else if (i == 2) {
                List<TypeParameterDescriptor> typeParameters3 = directMember.getTypeParameters();
                Intrinsics.checkNotNullExpressionValue(typeParameters3, "getTypeParameters(...)");
                writeFormalTypeParameters(typeParameters3, jvmSignatureWriter);
                DeclarationDescriptor containingDeclaration = directMember.getContainingDeclaration();
                Intrinsics.checkNotNull(containingDeclaration, "null cannot be cast to non-null type org.jetbrains.kotlin.descriptors.ClassDescriptor");
                receiverType = ((ClassDescriptor) containingDeclaration).getDefaultType();
            } else {
                List<TypeParameterDescriptor> typeParameters4 = directMember.getTypeParameters();
                Intrinsics.checkNotNullExpressionValue(typeParameters4, "getTypeParameters(...)");
                writeFormalTypeParameters(typeParameters4, jvmSignatureWriter);
                if (!INSTANCE.isAccessor(functionDescriptor) || f.mo6753getDispatchReceiverParameter() == null) {
                    defaultType = null;
                } else {
                    DeclarationDescriptor containingDeclaration2 = f.getContainingDeclaration();
                    Intrinsics.checkNotNull(containingDeclaration2, "null cannot be cast to non-null type org.jetbrains.kotlin.descriptors.ClassDescriptor");
                    defaultType = ((ClassDescriptor) containingDeclaration2).getDefaultType();
                }
                receiverType = defaultType;
            }
            jvmSignatureWriter.writeParametersStart();
            if (receiverType != null) {
                writeParameter(jvmSignatureWriter, JvmMethodParameterKind.THIS, receiverType, f);
            }
            for (ReceiverParameterDescriptor receiverParameterDescriptor : f.getContextReceiverParameters()) {
                JvmMethodParameterKind jvmMethodParameterKind2 = JvmMethodParameterKind.CONTEXT_RECEIVER;
                KotlinType type = receiverParameterDescriptor.getType();
                Intrinsics.checkNotNullExpressionValue(type, "getType(...)");
                writeParameter(jvmSignatureWriter, jvmMethodParameterKind2, type, f);
            }
            ReceiverParameterDescriptor extensionReceiverParameter = f.getExtensionReceiverParameter();
            if (extensionReceiverParameter != null) {
                JvmMethodParameterKind jvmMethodParameterKind3 = JvmMethodParameterKind.RECEIVER;
                KotlinType type2 = extensionReceiverParameter.getType();
                Intrinsics.checkNotNullExpressionValue(type2, "getType(...)");
                writeParameter(jvmSignatureWriter, jvmMethodParameterKind3, type2, f);
            }
            for (KotlinType kotlinTypeMakeNullable : valueParameterTypes) {
                FunctionDescriptor functionDescriptor2 = f;
                if (MethodSignatureMappingKt.forceSingleValueParameterBoxing(functionDescriptor2)) {
                    kotlinTypeMakeNullable = TypeUtils.makeNullable(kotlinTypeMakeNullable);
                }
                Intrinsics.checkNotNull(kotlinTypeMakeNullable);
                writeParameter(jvmSignatureWriter, kotlinTypeMakeNullable, functionDescriptor2);
            }
            jvmSignatureWriter.writeReturnType();
            if (customReturnAsmType != null) {
                jvmSignatureWriter.writeAsmType(customReturnAsmType);
            } else if (customReturnType != null) {
                mapReturnType(f, jvmSignatureWriter, customReturnType);
            } else {
                mapReturnType(f, jvmSignatureWriter);
            }
            jvmSignatureWriter.writeReturnTypeEnd();
        }
        JvmMethodGenericSignature jvmMethodGenericSignatureMakeJvmMethodSignature = jvmSignatureWriter.makeJvmMethodSignature(mapFunctionName(f, kind));
        Intrinsics.checkNotNullExpressionValue(jvmMethodGenericSignatureMakeJvmMethodSignature, "makeJvmMethodSignature(...)");
        if (kind != OwnerKind.DEFAULT_IMPLS && kind != OwnerKind.ERASED_INLINE_CLASS && !hasSpecialBridge) {
            BuiltinMethodsWithSpecialGenericSignature builtinMethodsWithSpecialGenericSignature = BuiltinMethodsWithSpecialGenericSignature.INSTANCE;
            SpecialGenericSignatures.SpecialSignatureInfo specialSignatureInfo = BuiltinMethodsWithSpecialGenericSignature.getSpecialSignatureInfo(f);
            if (specialSignatureInfo != null) {
                return new JvmMethodGenericSignature(jvmMethodGenericSignatureMakeJvmMethodSignature.getAsmMethod(), jvmMethodGenericSignatureMakeJvmMethodSignature.getValueParameters(), CodegenUtilKt.replaceValueParametersIn(specialSignatureInfo, jvmMethodGenericSignatureMakeJvmMethodSignature.getGenericsSignature()));
            }
        }
        return jvmMethodGenericSignatureMakeJvmMethodSignature;
    }

    private final Sequence<KotlinType> extractPolymorphicParameterTypes(final ResolvedCall<?> resolvedCall) {
        List<ResolvedValueArgument> valueArgumentsByIndex = resolvedCall.getValueArgumentsByIndex();
        ResolvedValueArgument resolvedValueArgument = valueArgumentsByIndex != null ? (ResolvedValueArgument) CollectionsKt.singleOrNull((List) valueArgumentsByIndex) : null;
        VarargValueArgument varargValueArgument = resolvedValueArgument instanceof VarargValueArgument ? (VarargValueArgument) resolvedValueArgument : null;
        if (varargValueArgument == null) {
            throw new UnsupportedOperationException("Polymorphic signature is only supported for methods with one vararg argument: " + resolvedCall.getResultingDescriptor());
        }
        List<ValueArgument> arguments = varargValueArgument.getArguments();
        Intrinsics.checkNotNullExpressionValue(arguments, "getArguments(...)");
        return SequencesKt.map(CollectionsKt.asSequence(arguments), new Function1<ValueArgument, KotlinType>() { // from class: org.jetbrains.kotlin.codegen.state.KotlinTypeMapper.extractPolymorphicParameterTypes.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final KotlinType invoke(ValueArgument valueArgument) {
                KtExpression argumentExpression = valueArgument.getArgumentExpression();
                if (argumentExpression == null) {
                    throw new UnsupportedOperationException("Polymorphic signature argument must be an expression: " + resolvedCall.getResultingDescriptor());
                }
                KotlinType type = this.getBindingContext().getType(argumentExpression);
                Intrinsics.checkNotNull(type);
                return type;
            }
        });
    }

    private final Pair<KotlinType, Type> extractPolymorphicReturnType(ResolvedCall<?> resolvedCall) {
        KotlinType returnType = resolvedCall.getResultingDescriptor().getReturnType();
        if (returnType != null && !KotlinBuiltIns.isAny(returnType)) {
            return TuplesKt.to(null, null);
        }
        KtElement callElement = resolvedCall.getCall().getCallElement();
        KtQualifiedExpression ktQualifiedExpression = callElement instanceof KtExpression ? (KtExpression) callElement : null;
        if (ktQualifiedExpression == null) {
            throw new UnsupportedOperationException("Polymorphic signature method call must be an expression: " + resolvedCall.getResultingDescriptor());
        }
        while (true) {
            KtQualifiedExpression qualifiedExpressionForSelector = KtPsiUtilKt.getQualifiedExpressionForSelector(ktQualifiedExpression);
            if (qualifiedExpressionForSelector == null) {
                break;
            }
            ktQualifiedExpression = qualifiedExpressionForSelector;
        }
        KtExpression outermostParenthesizerOrThis = KtPsiUtilKt.getOutermostParenthesizerOrThis(ktQualifiedExpression);
        if (!(outermostParenthesizerOrThis.getLightParent() instanceof KtBinaryExpressionWithTypeRHS)) {
            return BindingContextUtilsKt.isUsedAsExpression(outermostParenthesizerOrThis, this.bindingContext) ? TuplesKt.to(null, AsmTypes.OBJECT_TYPE) : TuplesKt.to(null, Type.VOID_TYPE);
        }
        BindingContext bindingContext = this.bindingContext;
        PsiElement parent = outermostParenthesizerOrThis.getLightParent();
        Intrinsics.checkNotNull(parent, "null cannot be cast to non-null type org.jetbrains.kotlin.psi.KtExpression");
        return TuplesKt.to(bindingContext.getType((KtExpression) parent), null);
    }

    public final Method mapDefaultMethod(FunctionDescriptor functionDescriptor, OwnerKind kind) {
        Intrinsics.checkNotNullParameter(functionDescriptor, "functionDescriptor");
        Intrinsics.checkNotNullParameter(kind, "kind");
        Method methodMapAsmMethod = mapAsmMethod(functionDescriptor, kind);
        Type typeMapOwner = mapOwner(functionDescriptor);
        Companion companion = INSTANCE;
        boolean zIsConstructor = companion.isConstructor(methodMapAsmMethod);
        return new Method(zIsConstructor ? "<init>" : methodMapAsmMethod.getName() + "$default", companion.getDefaultDescriptor(methodMapAsmMethod, (DescriptorAsmUtil.isStaticMethod(kind, functionDescriptor) || zIsConstructor) ? null : typeMapOwner.getDescriptor(), CodegenUtilKt.unwrapFrontendVersion(functionDescriptor), 0));
    }

    private final boolean forceBoxedReturnType(FunctionDescriptor descriptor) {
        if (isBoxMethodForInlineClass(descriptor)) {
            return true;
        }
        KotlinType returnType = descriptor.getReturnType();
        Intrinsics.checkNotNull(returnType);
        FunctionDescriptor functionDescriptor = descriptor;
        if ((ExpressionTypingUtils.isFunctionExpression(functionDescriptor) || ExpressionTypingUtils.isFunctionLiteral(functionDescriptor)) && InlineClassesUtilsKt.isInlineClassType(returnType)) {
            return true;
        }
        if (isJvmPrimitive(returnType)) {
            Set allOverriddenDescriptors = DescriptorUtils.getAllOverriddenDescriptors(descriptor);
            Intrinsics.checkNotNullExpressionValue(allOverriddenDescriptors, "getAllOverriddenDescriptors(...)");
            Set set = allOverriddenDescriptors;
            if (!(set instanceof Collection) || !set.isEmpty()) {
                Iterator it = set.iterator();
                while (it.hasNext()) {
                    KotlinType returnType2 = ((FunctionDescriptor) it.next()).getReturnType();
                    Intrinsics.checkNotNull(returnType2);
                    if (!isJvmPrimitive(returnType2)) {
                        return true;
                    }
                }
            }
        }
        return InlineClassesUtilsKt.isInlineClassType(returnType) && (descriptor instanceof JavaMethodDescriptor);
    }

    private final boolean isJvmPrimitive(KotlinType kotlinType) {
        return KotlinBuiltIns.isPrimitiveType(kotlinType);
    }

    private final boolean isBoxMethodForInlineClass(FunctionDescriptor descriptor) {
        DeclarationDescriptor containingDeclaration = descriptor.getContainingDeclaration();
        Intrinsics.checkNotNullExpressionValue(containingDeclaration, "getContainingDeclaration(...)");
        return InlineClassesUtilsKt.isInlineClass(containingDeclaration) && descriptor.getKind() == CallableMemberDescriptor.Kind.SYNTHESIZED && Intrinsics.areEqual(descriptor.getShortName(), InlineClassDescriptorResolver.BOX_METHOD_NAME);
    }

    public final String mapFieldSignature(KotlinType backingFieldType, PropertyDescriptor propertyDescriptor) {
        Intrinsics.checkNotNullParameter(backingFieldType, "backingFieldType");
        Intrinsics.checkNotNullParameter(propertyDescriptor, "propertyDescriptor");
        BothSignatureWriter bothSignatureWriter = new BothSignatureWriter(BothSignatureWriter.Mode.TYPE);
        writeFieldSignature(backingFieldType, propertyDescriptor, bothSignatureWriter);
        return bothSignatureWriter.makeJavaGenericSignature();
    }

    public final void writeFieldSignature(KotlinType backingFieldType, VariableDescriptor variableDescriptor, JvmSignatureWriter sw) {
        Intrinsics.checkNotNullParameter(backingFieldType, "backingFieldType");
        Intrinsics.checkNotNullParameter(variableDescriptor, "variableDescriptor");
        Intrinsics.checkNotNullParameter(sw, "sw");
        if (!variableDescriptor.isVar()) {
            mapReturnType(variableDescriptor, sw, backingFieldType);
        } else {
            writeParameterType(sw, backingFieldType, variableDescriptor);
        }
    }

    public final void writeFormalTypeParameters(List<? extends TypeParameterDescriptor> typeParameters, final JvmSignatureWriter sw) {
        Intrinsics.checkNotNullParameter(typeParameters, "typeParameters");
        Intrinsics.checkNotNullParameter(sw, "sw");
        if (sw.skipGenericSignature()) {
            return;
        }
        for (TypeParameterDescriptor typeParameterDescriptor : typeParameters) {
            if (this.classBuilderMode.generateBodies || !typeParameterDescriptor.getShortName().isSpecial()) {
                INSTANCE.writeFormalTypeParameter(SimpleClassicTypeSystemContext.INSTANCE, typeParameterDescriptor, sw, new Function2<KotlinTypeMarker, TypeMappingMode, Type>() { // from class: org.jetbrains.kotlin.codegen.state.KotlinTypeMapper.writeFormalTypeParameters.1
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(2);
                    }

                    @Override // kotlin.jvm.functions.Function2
                    public final Type invoke(KotlinTypeMarker type, TypeMappingMode mode) {
                        Intrinsics.checkNotNullParameter(type, "type");
                        Intrinsics.checkNotNullParameter(mode, "mode");
                        return KotlinTypeMapper.this.mapType((KotlinType) type, sw, mode);
                    }
                });
            }
        }
    }

    private final void writeParameter(JvmSignatureWriter sw, KotlinType type, CallableDescriptor callableDescriptor) {
        writeParameter(sw, JvmMethodParameterKind.VALUE, type, callableDescriptor);
    }

    private final void writeParameter(JvmSignatureWriter sw, JvmMethodParameterKind kind, KotlinType type, CallableDescriptor callableDescriptor) {
        sw.writeParameterType(kind);
        writeParameterType(sw, type, callableDescriptor);
        sw.writeParameterTypeEnd();
    }

    public final void writeParameterType(JvmSignatureWriter sw, KotlinType type, CallableDescriptor callableDescriptor) {
        Intrinsics.checkNotNullParameter(sw, "sw");
        Intrinsics.checkNotNullParameter(type, "type");
        if (sw.skipGenericSignature()) {
            if (InlineClassesUtilsKt.isInlineClassType(type) && (callableDescriptor instanceof JavaMethodDescriptor)) {
                mapType(type, sw, TypeMappingMode.GENERIC_ARGUMENT);
                return;
            } else {
                mapType(type, sw, TypeMappingMode.DEFAULT);
                return;
            }
        }
        TypeMappingMode typeMappingModeExtractTypeMappingModeFromAnnotation = TypeMappingUtil.extractTypeMappingModeFromAnnotation(callableDescriptor, type, false, false);
        if (typeMappingModeExtractTypeMappingModeFromAnnotation == null) {
            if (TypeMappingUtil.isMethodWithDeclarationSiteWildcards(callableDescriptor) && !type.getArguments().isEmpty()) {
                typeMappingModeExtractTypeMappingModeFromAnnotation = TypeMappingMode.GENERIC_ARGUMENT;
            } else {
                typeMappingModeExtractTypeMappingModeFromAnnotation = TypeMappingModeExtensionsKt.getOptimalModeForValueParameter(getTypeSystem(), type);
            }
        }
        mapType(type, sw, typeMappingModeExtractTypeMappingModeFromAnnotation);
    }

    private final void writeAdditionalConstructorParameters(ClassConstructorDescriptor descriptor, JvmSignatureWriter sw) {
        ResolvedCall<ConstructorDescriptor> resolvedCallFindFirstDelegatingSuperCall;
        DeclarationDescriptor declarationDescriptor;
        Type typeAsmTypeForAnonymousClass;
        boolean z = descriptor.getKind() == CallableMemberDescriptor.Kind.SYNTHESIZED;
        MutableClosure mutableClosure = (MutableClosure) this.bindingContext.get(CodegenBinding.CLOSURE, descriptor.getContainingDeclaration());
        ClassConstructorDescriptor classConstructorDescriptor = descriptor;
        ClassDescriptor dispatchReceiverParameterForConstructorCall = JvmCodegenUtil.getDispatchReceiverParameterForConstructorCall(classConstructorDescriptor, mutableClosure);
        if (!z && dispatchReceiverParameterForConstructorCall != null) {
            JvmMethodParameterKind jvmMethodParameterKind = JvmMethodParameterKind.OUTER;
            SimpleType defaultType = dispatchReceiverParameterForConstructorCall.getDefaultType();
            Intrinsics.checkNotNullExpressionValue(defaultType, "getDefaultType(...)");
            writeParameter(sw, jvmMethodParameterKind, defaultType, descriptor);
        }
        KotlinType capturedReceiverFromOuterContext = mutableClosure != null ? mutableClosure.getCapturedReceiverFromOuterContext() : null;
        if (capturedReceiverFromOuterContext != null) {
            writeParameter(sw, JvmMethodParameterKind.RECEIVER, capturedReceiverFromOuterContext, descriptor);
        }
        ClassDescriptor containingDeclaration = descriptor.getContainingDeclaration();
        if (!z && (containingDeclaration.getKind() == ClassKind.ENUM_CLASS || containingDeclaration.getKind() == ClassKind.ENUM_ENTRY)) {
            JvmMethodParameterKind jvmMethodParameterKind2 = JvmMethodParameterKind.ENUM_NAME_OR_ORDINAL;
            ClassConstructorDescriptor classConstructorDescriptor2 = descriptor;
            SimpleType stringType = DescriptorUtilsKt.getBuiltIns(classConstructorDescriptor2).getStringType();
            Intrinsics.checkNotNullExpressionValue(stringType, "getStringType(...)");
            ClassConstructorDescriptor classConstructorDescriptor3 = descriptor;
            writeParameter(sw, jvmMethodParameterKind2, stringType, classConstructorDescriptor3);
            JvmMethodParameterKind jvmMethodParameterKind3 = JvmMethodParameterKind.ENUM_NAME_OR_ORDINAL;
            SimpleType intType = DescriptorUtilsKt.getBuiltIns(classConstructorDescriptor2).getIntType();
            Intrinsics.checkNotNullExpressionValue(intType, "getIntType(...)");
            writeParameter(sw, jvmMethodParameterKind3, intType, classConstructorDescriptor3);
        }
        if (mutableClosure == null) {
            return;
        }
        for (DeclarationDescriptor declarationDescriptor2 : mutableClosure.getCaptureVariables().keySet()) {
            if ((declarationDescriptor2 instanceof VariableDescriptor) && !(declarationDescriptor2 instanceof PropertyDescriptor)) {
                typeAsmTypeForAnonymousClass = getSharedVarType(declarationDescriptor2);
                if (typeAsmTypeForAnonymousClass != null) {
                    declarationDescriptor = declarationDescriptor2;
                } else if (JvmCodegenUtil.isDelegatedLocalVariable(declarationDescriptor2)) {
                    KotlinType propertyDelegateType = JvmCodegenUtil.getPropertyDelegateType((LocalVariableDescriptor) declarationDescriptor2, this.bindingContext);
                    if (propertyDelegateType == null) {
                        throw new IllegalStateException(("Local delegated property type should not be null: " + declarationDescriptor2).toString());
                    }
                    declarationDescriptor = declarationDescriptor2;
                    typeAsmTypeForAnonymousClass = mapType$default(this, propertyDelegateType, null, null, 6, null);
                } else {
                    declarationDescriptor = declarationDescriptor2;
                    KotlinType type = ((VariableDescriptor) declarationDescriptor).getType();
                    Intrinsics.checkNotNullExpressionValue(type, "getType(...)");
                    typeAsmTypeForAnonymousClass = mapType$default(this, type, null, null, 6, null);
                }
            } else {
                declarationDescriptor = declarationDescriptor2;
                if (ExpressionTypingUtils.isLocalFunction(declarationDescriptor)) {
                    BindingContext bindingContext = this.bindingContext;
                    Intrinsics.checkNotNull(declarationDescriptor, "null cannot be cast to non-null type org.jetbrains.kotlin.descriptors.FunctionDescriptor");
                    typeAsmTypeForAnonymousClass = CodegenBinding.asmTypeForAnonymousClass(bindingContext, (FunctionDescriptor) declarationDescriptor);
                } else {
                    typeAsmTypeForAnonymousClass = null;
                }
            }
            if (typeAsmTypeForAnonymousClass != null) {
                mutableClosure.setCapturedParameterOffsetInConstructor(declarationDescriptor, sw.getCurrentSignatureSize() + 1);
                INSTANCE.writeParameter(sw, JvmMethodParameterKind.CAPTURED_LOCAL_VARIABLE, typeAsmTypeForAnonymousClass);
            }
        }
        if (!this.classBuilderMode.generateBodies || (resolvedCallFindFirstDelegatingSuperCall = findFirstDelegatingSuperCall(classConstructorDescriptor)) == null) {
            return;
        }
        writeSuperConstructorCallParameters(sw, descriptor, resolvedCallFindFirstDelegatingSuperCall, dispatchReceiverParameterForConstructorCall != null);
    }

    private final void writeSuperConstructorCallParameters(JvmSignatureWriter sw, ClassConstructorDescriptor descriptor, ResolvedCall<ConstructorDescriptor> superCall, boolean hasOuter) {
        ConstructorDescriptor constructorDescriptor = (ConstructorDescriptor) SamCodegenUtil.resolveSamAdapter((FunctionDescriptor) superCall.getResultingDescriptor());
        Map<ValueParameterDescriptor, ResolvedValueArgument> valueArguments = superCall.getValueArguments();
        Intrinsics.checkNotNullExpressionValue(valueArguments, "getValueArguments(...)");
        ConstructorDescriptor original = constructorDescriptor.getOriginal();
        Intrinsics.checkNotNullExpressionValue(original, "getOriginal(...)");
        List<JvmMethodParameterSignature> valueParameters = mapSignatureSkipGeneric$default(this, original, null, 2, null).getValueParameters();
        Intrinsics.checkNotNullExpressionValue(valueParameters, "getValueParameters(...)");
        int size = valueParameters.size() - valueArguments.size();
        for (int i = 0; i < size; i++) {
            JvmMethodParameterSignature jvmMethodParameterSignature = valueParameters.get(i);
            JvmMethodParameterKind kind = jvmMethodParameterSignature.getKind();
            Intrinsics.checkNotNullExpressionValue(kind, "getKind(...)");
            if (kind != JvmMethodParameterKind.ENUM_NAME_OR_ORDINAL && (!hasOuter || kind != JvmMethodParameterKind.OUTER)) {
                Companion companion = INSTANCE;
                JvmMethodParameterKind jvmMethodParameterKind = JvmMethodParameterKind.SUPER_CALL_PARAM;
                Type asmType = jvmMethodParameterSignature.getAsmType();
                Intrinsics.checkNotNullExpressionValue(asmType, "getAsmType(...)");
                companion.writeParameter(sw, jvmMethodParameterKind, asmType);
            }
        }
        if (DescriptorUtils.isAnonymousObject(descriptor.getContainingDeclaration())) {
            for (Map.Entry<ValueParameterDescriptor, ResolvedValueArgument> entry : valueArguments.entrySet()) {
                ValueParameterDescriptor key = entry.getKey();
                if (!(entry.getValue() instanceof DefaultValueArgument)) {
                    JvmMethodParameterSignature jvmMethodParameterSignature2 = valueParameters.get(key.getIndex() + size);
                    Companion companion2 = INSTANCE;
                    JvmMethodParameterKind jvmMethodParameterKind2 = JvmMethodParameterKind.SUPER_CALL_PARAM;
                    Type asmType2 = jvmMethodParameterSignature2.getAsmType();
                    Intrinsics.checkNotNullExpressionValue(asmType2, "getAsmType(...)");
                    companion2.writeParameter(sw, jvmMethodParameterKind2, asmType2);
                }
            }
        }
    }

    private final ResolvedCall<ConstructorDescriptor> findFirstDelegatingSuperCall(ConstructorDescriptor descriptor) {
        ClassifierDescriptorWithTypeParameters containingDeclaration = descriptor.getContainingDeclaration();
        Intrinsics.checkNotNullExpressionValue(containingDeclaration, "getContainingDeclaration(...)");
        HashSet hashSet = new HashSet();
        hashSet.add(descriptor);
        while (true) {
            ResolvedCall<ConstructorDescriptor> delegationConstructorCall = BindingContextUtils.getDelegationConstructorCall(this.bindingContext, descriptor);
            if (delegationConstructorCall == null) {
                return null;
            }
            ConstructorDescriptor original = ((ConstructorDescriptor) delegationConstructorCall.getResultingDescriptor()).getOriginal();
            Intrinsics.checkNotNullExpressionValue(original, "getOriginal(...)");
            if (!hashSet.add(original)) {
                return null;
            }
            if (!Intrinsics.areEqual(original.getContainingDeclaration(), containingDeclaration)) {
                return delegationConstructorCall;
            }
            descriptor = original;
        }
    }

    public final Method mapSyntheticMethodForPropertyAnnotations(PropertyDescriptor descriptor) {
        String strAsString;
        String string;
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        ReceiverParameterDescriptor extensionReceiverParameter = descriptor.getExtensionReceiverParameter();
        if (this.languageVersionSettings.supportsFeature(LanguageFeature.UseGetterNameForPropertyAnnotationsMethodOnJvm)) {
            PropertyGetterDescriptor getter = descriptor.getGetter();
            Intrinsics.checkNotNull(getter);
            strAsString = mapFunctionName(getter, OwnerKind.IMPLEMENTATION);
        } else {
            strAsString = descriptor.getShortName().asString();
            Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
        }
        String syntheticMethodNameForAnnotatedProperty = JvmAbi.getSyntheticMethodNameForAnnotatedProperty(strAsString);
        if (extensionReceiverParameter == null) {
            string = "()V";
        } else {
            StringBuilder sb = new StringBuilder("(");
            KotlinType type = extensionReceiverParameter.getType();
            Intrinsics.checkNotNullExpressionValue(type, "getType(...)");
            string = sb.append(mapType$default(this, type, null, null, 6, null)).append(")V").toString();
        }
        return new Method(syntheticMethodNameForAnnotatedProperty, string);
    }

    public final JvmMethodSignature mapScriptSignature(ScriptDescriptor script) {
        Intrinsics.checkNotNullParameter(script, "script");
        BothSignatureWriter bothSignatureWriter = new BothSignatureWriter(BothSignatureWriter.Mode.METHOD);
        bothSignatureWriter.writeParametersStart();
        Iterator<ValueParameterDescriptor> it = script.mo6347getUnsubstitutedPrimaryConstructor().getValueParameters().iterator();
        while (it.hasNext()) {
            KotlinType type = it.next().getType();
            Intrinsics.checkNotNullExpressionValue(type, "getType(...)");
            writeParameter(bothSignatureWriter, type, null);
        }
        INSTANCE.writeVoidReturn(bothSignatureWriter);
        JvmMethodGenericSignature jvmMethodGenericSignatureMakeJvmMethodSignature = bothSignatureWriter.makeJvmMethodSignature("<init>");
        Intrinsics.checkNotNullExpressionValue(jvmMethodGenericSignatureMakeJvmMethodSignature, "makeJvmMethodSignature(...)");
        return jvmMethodGenericSignatureMakeJvmMethodSignature;
    }

    public final Type getSharedVarType(DeclarationDescriptor descriptor) {
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        if ((descriptor instanceof SimpleFunctionDescriptor) && (((SimpleFunctionDescriptor) descriptor).getContainingDeclaration() instanceof FunctionDescriptor)) {
            return CodegenBinding.asmTypeForAnonymousClass(this.bindingContext, (FunctionDescriptor) descriptor);
        }
        if ((descriptor instanceof PropertyDescriptor) || (descriptor instanceof FunctionDescriptor)) {
            ReceiverParameterDescriptor extensionReceiverParameter = ((CallableDescriptor) descriptor).getExtensionReceiverParameter();
            if (extensionReceiverParameter == null) {
                throw new IllegalStateException(("Callable should have a receiver parameter: " + descriptor).toString());
            }
            KotlinType type = extensionReceiverParameter.getType();
            Intrinsics.checkNotNullExpressionValue(type, "getType(...)");
            return StackValue.sharedTypeForType(mapType$default(this, type, null, null, 6, null));
        }
        if (((descriptor instanceof LocalVariableDescriptor) && ((LocalVariableDescriptor) descriptor).isDelegated()) || !(descriptor instanceof VariableDescriptor) || !BindingContextUtils.isBoxedLocalCapturedInClosure(this.bindingContext, descriptor)) {
            return null;
        }
        KotlinType type2 = ((VariableDescriptor) descriptor).getType();
        Intrinsics.checkNotNullExpressionValue(type2, "getType(...)");
        return StackValue.sharedTypeForType(mapType$default(this, type2, null, null, 6, null));
    }

    public final String classInternalName(ClassDescriptor classDescriptor) {
        Intrinsics.checkNotNullParameter(classDescriptor, "classDescriptor");
        Type predefinedTypeForClass2 = this.typeMappingConfiguration.getPredefinedTypeForClass(classDescriptor);
        String internalName = predefinedTypeForClass2 != null ? predefinedTypeForClass2.getInternalName() : null;
        return internalName == null ? DescriptorBasedTypeSignatureMappingKt.computeInternalName(classDescriptor, this.typeMappingConfiguration) : internalName;
    }

    /* JADX INFO: compiled from: KotlinTypeMapper.kt */
    @Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0006\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000e\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006J\u0010\u0010\u0007\u001a\u0004\u0018\u00010\u00062\u0006\u0010\u0005\u001a\u00020\u0006J\u0010\u0010\b\u001a\u0004\u0018\u00010\u00062\u0006\u0010\u0005\u001a\u00020\u0006J\u0010\u0010\t\u001a\u0004\u0018\u00010\u00062\u0006\u0010\u0005\u001a\u00020\u0006J\u0016\u0010\n\u001a\u00020\u00062\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u000b\u001a\u00020\u0006¨\u0006\f"}, d2 = {"Lorg/jetbrains/kotlin/codegen/state/KotlinTypeMapper$InternalNameMapper;", "", "()V", "canBeMangledInternalName", "", "name", "", "demangleInternalName", "getModuleNameSuffix", "internalNameWithoutModuleSuffix", "mangleInternalName", "moduleName", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class InternalNameMapper {
        public static final InternalNameMapper INSTANCE = new InternalNameMapper();

        private InternalNameMapper() {
        }

        public final String mangleInternalName(String name2, String moduleName) {
            Intrinsics.checkNotNullParameter(name2, "name");
            Intrinsics.checkNotNullParameter(moduleName, "moduleName");
            return name2 + '$' + NameUtils.sanitizeAsJavaIdentifier(moduleName);
        }

        public final boolean canBeMangledInternalName(String name2) {
            Intrinsics.checkNotNullParameter(name2, "name");
            return StringsKt.contains$default((CharSequence) name2, '$', false, 2, (Object) null);
        }

        public final String demangleInternalName(String name2) {
            Intrinsics.checkNotNullParameter(name2, "name");
            int iIndexOf$default = StringsKt.indexOf$default((CharSequence) name2, '$', 0, false, 6, (Object) null);
            if (iIndexOf$default < 0) {
                return null;
            }
            String strSubstring = name2.substring(0, iIndexOf$default);
            Intrinsics.checkNotNullExpressionValue(strSubstring, "substring(...)");
            return strSubstring;
        }

        public final String getModuleNameSuffix(String name2) {
            Intrinsics.checkNotNullParameter(name2, "name");
            int iIndexOf$default = StringsKt.indexOf$default((CharSequence) name2, '$', 0, false, 6, (Object) null);
            if (iIndexOf$default < 0) {
                return null;
            }
            String strSubstring = name2.substring(iIndexOf$default + 1);
            Intrinsics.checkNotNullExpressionValue(strSubstring, "substring(...)");
            return strSubstring;
        }

        public final String internalNameWithoutModuleSuffix(String name2) {
            Intrinsics.checkNotNullParameter(name2, "name");
            String strDemangleInternalName = demangleInternalName(name2);
            if (strDemangleInternalName != null) {
                return strDemangleInternalName + '$';
            }
            return null;
        }
    }

    /* JADX INFO: compiled from: KotlinTypeMapper.kt */
    @Metadata(d1 = {"\u0000æ\u0001\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0015\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\rH\u0000¢\u0006\u0002\b\u000fJ\u0010\u0010\u0010\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\rH\u0002J \u0010\u0011\u001a\u00020\r2\u0006\u0010\u0012\u001a\u00020\r2\u0006\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u0016H\u0002J\u001d\u0010\u0017\u001a\u00020\u00042\u0006\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u0012\u001a\u00020\u001aH\u0000¢\u0006\u0002\b\u001bJ\u0010\u0010\u001c\u001a\u00020\u00062\u0006\u0010\u001d\u001a\u00020\u001eH\u0002J\u000e\u0010\u001f\u001a\u00020 2\u0006\u0010!\u001a\u00020\"J*\u0010#\u001a\u00020\u00042\u0006\u0010$\u001a\u00020%2\b\u0010&\u001a\u0004\u0018\u00010\u00042\u0006\u0010'\u001a\u00020(2\u0006\u0010)\u001a\u00020*H\u0002J\u0010\u0010+\u001a\u00020\u00042\u0006\u0010,\u001a\u00020\u001eH\u0002J\u0010\u0010-\u001a\u00020.2\u0006\u0010/\u001a\u00020\rH\u0002J\u0012\u00100\u001a\u0004\u0018\u00010 2\u0006\u0010\u0012\u001a\u00020\"H\u0002J\u001a\u00101\u001a\u0004\u0018\u00010\u00042\u0006\u0010\u0012\u001a\u00020\"2\u0006\u00102\u001a\u00020\u0014H\u0002J\u0012\u00103\u001a\u0004\u0018\u00010\u00042\u0006\u0010'\u001a\u000204H\u0002J\u001e\u00105\u001a\u0002062\u0006\u00107\u001a\u0002082\u0006\u00109\u001a\u00020:2\u0006\u0010;\u001a\u00020<J\u0010\u0010=\u001a\u00020\u00142\u0006\u0010>\u001a\u00020\u0019H\u0002J\u0018\u0010?\u001a\u00020\u00042\u0006\u0010'\u001a\u0002042\u0006\u00102\u001a\u00020\u0014H\u0002J\u0012\u0010@\u001a\u00020\u00142\b\u0010\u0012\u001a\u0004\u0018\u000104H\u0007J\u0010\u0010A\u001a\u00020\u00142\u0006\u0010$\u001a\u00020%H\u0002J\u0010\u0010B\u001a\u00020\u00142\u0006\u0010'\u001a\u00020(H\u0002J\u0010\u0010C\u001a\u00020\u00142\u0006\u0010\u0012\u001a\u000204H\u0002J\u0012\u0010D\u001a\u00020\u00142\b\u0010\u0012\u001a\u0004\u0018\u000104H\u0007J\u0018\u0010E\u001a\u00020\u00042\u0006\u0010F\u001a\u00020G2\u0006\u0010H\u001a\u00020\u0014H\u0007J\u0018\u0010I\u001a\u00020J2\u0006\u0010>\u001a\u00020K2\u0006\u0010L\u001a\u00020MH\u0007J \u0010N\u001a\u00020O2\u0006\u0010P\u001a\u00020Q2\u0006\u0010R\u001a\u00020S2\u0006\u0010\u0018\u001a\u00020JH\u0002J\u0010\u0010T\u001a\u00020O2\u0006\u0010P\u001a\u00020QH\u0002J&\u00105\u001a\u000206*\u00020U2\b\u00107\u001a\u0004\u0018\u00010V2\u0006\u00109\u001a\u00020W2\u0006\u0010;\u001a\u00020<H\u0002J\u0012\u0010=\u001a\u00020\u0014*\u00020X2\u0006\u0010\u0018\u001a\u00020KJ6\u0010Y\u001a\u00020O*\u00020U2\u0006\u0010Z\u001a\u00020V2\u0006\u0010P\u001a\u00020Q2\u0018\u0010[\u001a\u0014\u0012\u0004\u0012\u00020K\u0012\u0004\u0012\u00020<\u0012\u0004\u0012\u00020J0\\H\u0007JV\u0010]\u001a\u00020O*\u00020U2\u0006\u0010^\u001a\u00020Q2\f\u0010_\u001a\b\u0012\u0004\u0012\u00020W0`2\f\u0010a\u001a\b\u0012\u0004\u0012\u00020V0`2\u0006\u0010;\u001a\u00020<2\u001e\u0010[\u001a\u001a\u0012\u0004\u0012\u00020K\u0012\u0004\u0012\u00020Q\u0012\u0004\u0012\u00020<\u0012\u0004\u0012\u00020J0bR\u0010\u0010\u0003\u001a\u00020\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\u0007\u001a\u00020\b¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0010\u0010\u000b\u001a\u00020\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000¨\u0006c"}, d2 = {"Lorg/jetbrains/kotlin/codegen/state/KotlinTypeMapper$Companion;", "", "()V", "BOX_JVM_METHOD_NAME", "", "FAKE_CLASS_ID_FOR_BUILTINS", "Lorg/jetbrains/kotlin/name/ClassId;", "LANGUAGE_VERSION_SETTINGS_DEFAULT", "Lorg/jetbrains/kotlin/config/LanguageVersionSettings;", "getLANGUAGE_VERSION_SETTINGS_DEFAULT", "()Lorg/jetbrains/kotlin/config/LanguageVersionSettings;", "UNBOX_JVM_METHOD_NAME", "findAnyDeclaration", "Lorg/jetbrains/kotlin/descriptors/FunctionDescriptor;", "function", "findAnyDeclaration$backend", "findBaseDeclaration", "findSuperDeclaration", "descriptor", "isSuperCall", "", "jvmDefaultMode", "Lorg/jetbrains/kotlin/config/JvmDefaultMode;", "generateErrorMessageForErrorType", ModuleXmlParser.TYPE, "Lorg/jetbrains/kotlin/types/KotlinType;", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "generateErrorMessageForErrorType$backend", "getContainerClassIdForClassDescriptor", "classDescriptor", "Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;", "getContainingClassesForDeserializedCallable", "Lorg/jetbrains/kotlin/codegen/state/KotlinTypeMapper$ContainingClassesInfo;", "deserializedDescriptor", "Lorg/jetbrains/kotlin/serialization/deserialization/descriptors/DescriptorWithContainerSource;", "getDefaultDescriptor", "method", "Lorg/jetbrains/org/objectweb/asm/commons/Method;", "dispatchReceiverDescriptor", "callableDescriptor", "Lorg/jetbrains/kotlin/descriptors/CallableDescriptor;", "extraArgsShift", "", "getJvmShortName", "klass", "getKindForDefaultImplCall", "Lorg/jetbrains/kotlin/codegen/OwnerKind;", "baseMethodDescriptor", "getPackageMemberContainingClassesInfo", "getPackageMemberOwnerInternalName", "publicFacade", "getPartSimpleNameForMangling", "Lorg/jetbrains/kotlin/descriptors/CallableMemberDescriptor;", "getVarianceForWildcard", "Lorg/jetbrains/kotlin/types/Variance;", "parameter", "Lorg/jetbrains/kotlin/descriptors/TypeParameterDescriptor;", "projection", "Lorg/jetbrains/kotlin/types/TypeProjection;", "mode", "Lorg/jetbrains/kotlin/load/kotlin/TypeMappingMode;", "hasNothingInNonContravariantPosition", "kotlinType", "internalNameForPackageMemberOwner", "isAccessor", "isConstructor", "isInlineClassConstructor", "isNonConstProperty", "isStaticAccessor", "mapDefaultFieldName", "propertyDescriptor", "Lorg/jetbrains/kotlin/descriptors/PropertyDescriptor;", "isDelegated", "mapUnderlyingTypeOfInlineClassType", "Lorg/jetbrains/org/objectweb/asm/Type;", "Lorg/jetbrains/kotlin/types/model/KotlinTypeMarker;", "typeMapper", "Lorg/jetbrains/kotlin/codegen/state/KotlinTypeMapperBase;", "writeParameter", "", "sw", "Lorg/jetbrains/kotlin/codegen/signature/JvmSignatureWriter;", Namer.METADATA_CLASS_KIND, "Lorg/jetbrains/kotlin/resolve/jvm/jvmSignature/JvmMethodParameterKind;", "writeVoidReturn", "Lorg/jetbrains/kotlin/types/TypeSystemCommonBackendContext;", "Lorg/jetbrains/kotlin/types/model/TypeParameterMarker;", "Lorg/jetbrains/kotlin/types/model/TypeArgumentMarker;", "Lorg/jetbrains/kotlin/types/model/TypeSystemContext;", "writeFormalTypeParameter", "typeParameter", "mapType", "Lkotlin/Function2;", "writeGenericArguments", "signatureVisitor", "arguments", "", "parameters", "Lkotlin/Function3;", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final LanguageVersionSettings getLANGUAGE_VERSION_SETTINGS_DEFAULT() {
            return KotlinTypeMapper.LANGUAGE_VERSION_SETTINGS_DEFAULT;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final String internalNameForPackageMemberOwner(CallableMemberDescriptor callableDescriptor, boolean publicFacade) {
            boolean z;
            String packageMemberOwnerInternalName;
            if (callableDescriptor instanceof AccessorForCallableDescriptor) {
                callableDescriptor = ((AccessorForCallableDescriptor) callableDescriptor).getCalleeDescriptor();
                z = true;
            } else {
                z = false;
            }
            KtFile containingFile = DescriptorToSourceUtils.getContainingFile(callableDescriptor);
            if (containingFile != null) {
                DescriptorVisibility visibility = callableDescriptor.getVisibility();
                Intrinsics.checkNotNullExpressionValue(visibility, "getVisibility(...)");
                if (!publicFacade || isNonConstProperty(callableDescriptor) || DescriptorVisibilities.isPrivate(visibility) || z) {
                    return JvmFileClassUtil.getFileClassInternalName(containingFile);
                }
                return JvmFileClassUtil.getFacadeClassInternalName(containingFile);
            }
            CallableMemberDescriptor directMember = DescriptorUtils.getDirectMember(callableDescriptor);
            Intrinsics.checkNotNullExpressionValue(directMember, "getDirectMember(...)");
            if ((directMember instanceof DescriptorWithContainerSource) && (packageMemberOwnerInternalName = getPackageMemberOwnerInternalName((DescriptorWithContainerSource) directMember, publicFacade)) != null) {
                return packageMemberOwnerInternalName;
            }
            if (directMember instanceof FictitiousArrayConstructor) {
                return "kotlin.Array";
            }
            throw new RuntimeException("Could not find package member for " + callableDescriptor + " in package fragment " + callableDescriptor.getContainingDeclaration());
        }

        private final boolean isNonConstProperty(CallableMemberDescriptor descriptor) {
            return (descriptor instanceof PropertyDescriptor) && !((PropertyDescriptor) descriptor).isConst();
        }

        public final ContainingClassesInfo getContainingClassesForDeserializedCallable(DescriptorWithContainerSource deserializedDescriptor) {
            ContainingClassesInfo containingClassesInfoForClassMember$backend;
            Intrinsics.checkNotNullParameter(deserializedDescriptor, "deserializedDescriptor");
            DeclarationDescriptor containingDeclaration = deserializedDescriptor.getContainingDeclaration();
            Intrinsics.checkNotNullExpressionValue(containingDeclaration, "getContainingDeclaration(...)");
            if (containingDeclaration instanceof PackageFragmentDescriptor) {
                containingClassesInfoForClassMember$backend = getPackageMemberContainingClassesInfo(deserializedDescriptor);
            } else {
                containingClassesInfoForClassMember$backend = ContainingClassesInfo.INSTANCE.forClassMember$backend(getContainerClassIdForClassDescriptor((ClassDescriptor) containingDeclaration));
            }
            if (containingClassesInfoForClassMember$backend != null) {
                return containingClassesInfoForClassMember$backend;
            }
            throw new IllegalStateException("Couldn't find container for " + deserializedDescriptor.getShortName());
        }

        private final ClassId getContainerClassIdForClassDescriptor(ClassDescriptor classDescriptor) {
            ClassId classId = DescriptorUtilsKt.getClassId((ClassifierDescriptor) classDescriptor);
            if (classId == null) {
                throw new IllegalStateException(("Deserialized class should have a ClassId: " + classDescriptor).toString());
            }
            String str = DescriptorUtils.isInterface(classDescriptor) ? JvmAbi.DEFAULT_IMPLS_SUFFIX : null;
            if (str != null) {
                return new ClassId(classId.getPackageFqName(), Name.identifier(classId.getRelativeClassName().shortName().asString() + str));
            }
            return classId;
        }

        private final String getPackageMemberOwnerInternalName(DescriptorWithContainerSource descriptor, boolean publicFacade) {
            ClassId implClassId;
            DeclarationDescriptor containingDeclaration = descriptor.getContainingDeclaration();
            Intrinsics.checkNotNullExpressionValue(containingDeclaration, "getContainingDeclaration(...)");
            boolean z = containingDeclaration instanceof PackageFragmentDescriptor;
            ContainingClassesInfo packageMemberContainingClassesInfo = getPackageMemberContainingClassesInfo(descriptor);
            if (packageMemberContainingClassesInfo == null) {
                return null;
            }
            if (publicFacade) {
                implClassId = packageMemberContainingClassesInfo.getFacadeClassId();
            } else {
                implClassId = packageMemberContainingClassesInfo.getImplClassId();
            }
            return JvmClassName.byClassId(implClassId).getInternalName();
        }

        private final ContainingClassesInfo getPackageMemberContainingClassesInfo(DescriptorWithContainerSource descriptor) {
            JvmClassName facadeName;
            DeclarationDescriptor containingDeclaration = descriptor.getContainingDeclaration();
            Intrinsics.checkNotNullExpressionValue(containingDeclaration, "getContainingDeclaration(...)");
            if (containingDeclaration instanceof BuiltInsPackageFragment) {
                return new ContainingClassesInfo(KotlinTypeMapper.FAKE_CLASS_ID_FOR_BUILTINS, KotlinTypeMapper.FAKE_CLASS_ID_FOR_BUILTINS);
            }
            JvmClassName implClassNameForDeserialized = UtilKt.getImplClassNameForDeserialized(descriptor);
            if (implClassNameForDeserialized == null) {
                throw new IllegalStateException(("No implClassName for " + descriptor).toString());
            }
            if (containingDeclaration instanceof LazyJavaPackageFragment) {
                facadeName = ((LazyJavaPackageFragment) containingDeclaration).getFacadeNameForPartName(implClassNameForDeserialized);
                if (facadeName == null) {
                    return null;
                }
            } else if (containingDeclaration instanceof IncrementalPackageFragmentProvider.IncrementalMultifileClassPackageFragment) {
                facadeName = ((IncrementalPackageFragmentProvider.IncrementalMultifileClassPackageFragment) containingDeclaration).getFacadeName();
            } else {
                if (!(containingDeclaration instanceof PackageFragmentDescriptor)) {
                    throw new AssertionError("Unexpected package fragment for " + descriptor + ": " + containingDeclaration + " (" + containingDeclaration.getClass().getSimpleName() + ')');
                }
                facadeName = implClassNameForDeserialized;
            }
            return ContainingClassesInfo.INSTANCE.forPackageMember$backend(facadeName, implClassNameForDeserialized);
        }

        @JvmStatic
        public final Type mapUnderlyingTypeOfInlineClassType(KotlinTypeMarker kotlinType, KotlinTypeMapperBase typeMapper) {
            Intrinsics.checkNotNullParameter(kotlinType, "kotlinType");
            Intrinsics.checkNotNullParameter(typeMapper, "typeMapper");
            KotlinTypeMarker unsubstitutedUnderlyingType = typeMapper.getTypeSystem().getUnsubstitutedUnderlyingType(kotlinType);
            if (unsubstitutedUnderlyingType == null) {
                throw new IllegalStateException("There should be underlying type for inline class type: " + kotlinType);
            }
            return typeMapper.mapTypeCommon(unsubstitutedUnderlyingType, TypeMappingMode.DEFAULT);
        }

        public final String generateErrorMessageForErrorType$backend(KotlinType type, DeclarationDescriptor descriptor) {
            Intrinsics.checkNotNullParameter(type, "type");
            Intrinsics.checkNotNullParameter(descriptor, "descriptor");
            PsiElement psiElementDescriptorToDeclaration = DescriptorToSourceUtils.descriptorToDeclaration(descriptor);
            if (psiElementDescriptorToDeclaration == null) {
                return "Error type encountered: " + type + " (" + type.getClass().getSimpleName() + ").";
            }
            DeclarationDescriptor containingDeclaration = descriptor.getContainingDeclaration();
            PsiElement psiElementDescriptorToDeclaration2 = containingDeclaration != null ? DescriptorToSourceUtils.descriptorToDeclaration(containingDeclaration) : null;
            String str = String.format("Error type encountered: %s (%s). Descriptor: %s. For declaration %s:%s in %s:%s", Arrays.copyOf(new Object[]{type, type.getClass().getSimpleName(), descriptor, psiElementDescriptorToDeclaration, psiElementDescriptorToDeclaration.getText(), psiElementDescriptorToDeclaration2, psiElementDescriptorToDeclaration2 != null ? psiElementDescriptorToDeclaration2.getText() : "null"}, 7));
            Intrinsics.checkNotNullExpressionValue(str, "format(...)");
            return str;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final String getJvmShortName(ClassDescriptor klass) {
            Name shortClassName;
            JavaToKotlinClassMap javaToKotlinClassMap = JavaToKotlinClassMap.INSTANCE;
            FqNameUnsafe fqName = DescriptorUtils.getFqName(klass);
            Intrinsics.checkNotNullExpressionValue(fqName, "getFqName(...)");
            ClassId classIdMapKotlinToJava = javaToKotlinClassMap.mapKotlinToJava(fqName);
            String strAsString = (classIdMapKotlinToJava == null || (shortClassName = classIdMapKotlinToJava.getShortClassName()) == null) ? null : shortClassName.asString();
            if (strAsString != null) {
                return strAsString;
            }
            String identifier = SpecialNames.safeIdentifier(klass.getShortName()).getIdentifier();
            Intrinsics.checkNotNullExpressionValue(identifier, "getIdentifier(...)");
            return identifier;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final boolean hasNothingInNonContravariantPosition(KotlinType kotlinType) {
            return hasNothingInNonContravariantPosition(SimpleClassicTypeSystemContext.INSTANCE, kotlinType);
        }

        public final boolean hasNothingInNonContravariantPosition(TypeSystemContext typeSystemContext, KotlinTypeMarker type) {
            Intrinsics.checkNotNullParameter(typeSystemContext, "<this>");
            Intrinsics.checkNotNullParameter(type, "type");
            TypeConstructorMarker typeConstructorMarkerTypeConstructor = typeSystemContext.typeConstructor(type);
            int iArgumentsCount = typeSystemContext.argumentsCount(type);
            for (int i = 0; i < iArgumentsCount; i++) {
                TypeArgumentMarker argument = typeSystemContext.getArgument(type, i);
                if (!typeSystemContext.isStarProjection(argument)) {
                    KotlinTypeMarker type2 = typeSystemContext.getType(argument);
                    if (typeSystemContext.isNullableNothing(type2)) {
                        return true;
                    }
                    if (typeSystemContext.isNothing(type2) && typeSystemContext.getVariance(typeSystemContext.getParameter(typeConstructorMarkerTypeConstructor, i)) != TypeVariance.IN) {
                        return true;
                    }
                }
            }
            return false;
        }

        public final Variance getVarianceForWildcard(TypeParameterDescriptor parameter, TypeProjection projection, TypeMappingMode mode) {
            Intrinsics.checkNotNullParameter(parameter, "parameter");
            Intrinsics.checkNotNullParameter(projection, "projection");
            Intrinsics.checkNotNullParameter(mode, "mode");
            return getVarianceForWildcard(SimpleClassicTypeSystemContext.INSTANCE, parameter, projection, mode);
        }

        private final Variance getVarianceForWildcard(TypeSystemCommonBackendContext typeSystemCommonBackendContext, TypeParameterMarker typeParameterMarker, TypeArgumentMarker typeArgumentMarker, TypeMappingMode typeMappingMode) {
            Variance varianceConvertVariance;
            TypeVariance variance;
            Variance varianceConvertVariance2 = ClassicTypeSystemContextKt.convertVariance(typeSystemCommonBackendContext.getVariance(typeArgumentMarker));
            if (typeParameterMarker == null || (variance = typeSystemCommonBackendContext.getVariance(typeParameterMarker)) == null || (varianceConvertVariance = ClassicTypeSystemContextKt.convertVariance(variance)) == null) {
                varianceConvertVariance = Variance.INVARIANT;
            }
            if (varianceConvertVariance == Variance.INVARIANT) {
                return varianceConvertVariance2;
            }
            if (typeMappingMode.getSkipDeclarationSiteWildcards()) {
                return Variance.INVARIANT;
            }
            if (varianceConvertVariance2 == Variance.INVARIANT || varianceConvertVariance2 == varianceConvertVariance) {
                if (typeMappingMode.getSkipDeclarationSiteWildcardsIfPossible() && !typeSystemCommonBackendContext.isStarProjection(typeArgumentMarker)) {
                    if (varianceConvertVariance == Variance.OUT_VARIANCE && TypeMappingUtil.isMostPreciseCovariantArgument(typeSystemCommonBackendContext, typeSystemCommonBackendContext.getType(typeArgumentMarker))) {
                        return Variance.INVARIANT;
                    }
                    if (varianceConvertVariance == Variance.IN_VARIANCE && TypeMappingUtil.isMostPreciseContravariantArgument(typeSystemCommonBackendContext, typeSystemCommonBackendContext.getType(typeArgumentMarker))) {
                        return Variance.INVARIANT;
                    }
                }
                return varianceConvertVariance;
            }
            return Variance.OUT_VARIANCE;
        }

        public final void writeGenericArguments(TypeSystemCommonBackendContext typeSystemCommonBackendContext, JvmSignatureWriter signatureVisitor, List<? extends TypeArgumentMarker> arguments, List<? extends TypeParameterMarker> parameters, TypeMappingMode mode, Function3<? super KotlinTypeMarker, ? super JvmSignatureWriter, ? super TypeMappingMode, Type> mapType) {
            Variance varianceConvertVariance;
            TypeVariance variance;
            Intrinsics.checkNotNullParameter(typeSystemCommonBackendContext, "<this>");
            Intrinsics.checkNotNullParameter(signatureVisitor, "signatureVisitor");
            Intrinsics.checkNotNullParameter(arguments, "arguments");
            Intrinsics.checkNotNullParameter(parameters, "parameters");
            Intrinsics.checkNotNullParameter(mode, "mode");
            Intrinsics.checkNotNullParameter(mapType, "mapType");
            for (Pair pair : AddToStdlibKt.zipWithNulls(parameters, arguments)) {
                TypeParameterMarker typeParameterMarker = (TypeParameterMarker) pair.component1();
                TypeArgumentMarker typeArgumentMarker = (TypeArgumentMarker) pair.component2();
                if (typeArgumentMarker == null) {
                    return;
                }
                if (!typeSystemCommonBackendContext.isStarProjection(typeArgumentMarker)) {
                    if (typeSystemCommonBackendContext.isNothing(typeSystemCommonBackendContext.getType(typeArgumentMarker))) {
                        if ((typeParameterMarker != null ? typeSystemCommonBackendContext.getVariance(typeParameterMarker) : null) == TypeVariance.IN) {
                        }
                    }
                    TypeMappingMode typeMappingModeUpdateArgumentModeFromAnnotations = TypeMappingUtil.updateArgumentModeFromAnnotations(mode, typeSystemCommonBackendContext.getType(typeArgumentMarker), typeSystemCommonBackendContext);
                    signatureVisitor.writeTypeArgument(getVarianceForWildcard(typeSystemCommonBackendContext, typeParameterMarker, typeArgumentMarker, typeMappingModeUpdateArgumentModeFromAnnotations));
                    if (typeParameterMarker == null || (variance = typeSystemCommonBackendContext.getVariance(typeParameterMarker)) == null || (varianceConvertVariance = ClassicTypeSystemContextKt.convertVariance(variance)) == null) {
                        varianceConvertVariance = Variance.INVARIANT;
                    }
                    mapType.invoke(typeSystemCommonBackendContext.getType(typeArgumentMarker), signatureVisitor, TypeMappingMode.toGenericArgumentMode$default(typeMappingModeUpdateArgumentModeFromAnnotations, UtilsKt.getEffectiveVariance(varianceConvertVariance, ClassicTypeSystemContextKt.convertVariance(typeSystemCommonBackendContext.getVariance(typeArgumentMarker))), false, 2, null));
                    signatureVisitor.writeTypeArgumentEnd();
                }
                signatureVisitor.writeUnboundedWildcard();
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final FunctionDescriptor findSuperDeclaration(FunctionDescriptor descriptor, boolean isSuperCall, JvmDefaultMode jvmDefaultMode) {
            Object next;
            while (descriptor.getKind() == CallableMemberDescriptor.Kind.FAKE_OVERRIDE) {
                Collection<? extends FunctionDescriptor> overriddenDescriptors = descriptor.getOverriddenDescriptors();
                Intrinsics.checkNotNullExpressionValue(overriddenDescriptors, "getOverriddenDescriptors(...)");
                Iterator<T> it = overriddenDescriptors.iterator();
                do {
                    if (!it.hasNext()) {
                        next = null;
                        break;
                    }
                    next = it.next();
                } while (DescriptorUtils.isInterface(((FunctionDescriptor) next).getContainingDeclaration()));
                FunctionDescriptor functionDescriptor = (FunctionDescriptor) next;
                if (functionDescriptor == null) {
                    if (isSuperCall && !JvmAnnotationUtilKt.isCompiledToJvmDefault(descriptor, jvmDefaultMode) && !DescriptorUtils.isInterface(descriptor.getContainingDeclaration())) {
                        return descriptor;
                    }
                    Collection<? extends FunctionDescriptor> overriddenDescriptors2 = descriptor.getOverriddenDescriptors();
                    Intrinsics.checkNotNullExpressionValue(overriddenDescriptors2, "getOverriddenDescriptors(...)");
                    functionDescriptor = (FunctionDescriptor) CollectionsKt.firstOrNull(overriddenDescriptors2);
                    if (functionDescriptor == null) {
                        throw new IllegalStateException(("Fake override should have at least one overridden descriptor: " + descriptor).toString());
                    }
                }
                descriptor = functionDescriptor;
            }
            return descriptor;
        }

        @JvmStatic
        public final boolean isAccessor(CallableMemberDescriptor descriptor) {
            return (descriptor instanceof AccessorForCallableDescriptor) || (descriptor instanceof AccessorForCompanionObjectInstanceFieldDescriptor);
        }

        @JvmStatic
        public final boolean isStaticAccessor(CallableMemberDescriptor descriptor) {
            if (descriptor instanceof AccessorForConstructorDescriptor) {
                return false;
            }
            return isAccessor(descriptor);
        }

        public final FunctionDescriptor findAnyDeclaration$backend(FunctionDescriptor function) {
            Intrinsics.checkNotNullParameter(function, "function");
            return function.getKind() == CallableMemberDescriptor.Kind.DECLARATION ? function : findBaseDeclaration(function);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final FunctionDescriptor findBaseDeclaration(FunctionDescriptor function) {
            if (function.getOverriddenDescriptors().isEmpty()) {
                return function;
            }
            FunctionDescriptor next = function.getOverriddenDescriptors().iterator().next();
            Intrinsics.checkNotNullExpressionValue(next, "next(...)");
            return findBaseDeclaration(next);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final OwnerKind getKindForDefaultImplCall(FunctionDescriptor baseMethodDescriptor) {
            DeclarationDescriptor containingDeclaration = baseMethodDescriptor.getContainingDeclaration();
            Intrinsics.checkNotNullExpressionValue(containingDeclaration, "getContainingDeclaration(...)");
            if (containingDeclaration instanceof PackageFragmentDescriptor) {
                return OwnerKind.PACKAGE;
            }
            if (DescriptorUtils.isInterface(containingDeclaration)) {
                return OwnerKind.DEFAULT_IMPLS;
            }
            return InlineClassesUtilsKt.isInlineClass(containingDeclaration) ? OwnerKind.ERASED_INLINE_CLASS : OwnerKind.IMPLEMENTATION;
        }

        @JvmStatic
        public final String mapDefaultFieldName(PropertyDescriptor propertyDescriptor, boolean isDelegated) {
            String strAsString;
            Intrinsics.checkNotNullParameter(propertyDescriptor, "propertyDescriptor");
            if (propertyDescriptor instanceof AccessorForPropertyDescriptor) {
                strAsString = ((AccessorForPropertyDescriptor) propertyDescriptor).getCalleeDescriptor().getShortName().asString();
                Intrinsics.checkNotNull(strAsString);
            } else {
                strAsString = propertyDescriptor.getShortName().asString();
                Intrinsics.checkNotNull(strAsString);
            }
            return isDelegated ? strAsString + JvmAbi.DELEGATED_PROPERTY_NAME_SUFFIX : strAsString;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final String getPartSimpleNameForMangling(CallableMemberDescriptor callableDescriptor) {
            KtFile containingFile = DescriptorToSourceUtils.getContainingFile(callableDescriptor);
            if (containingFile != null) {
                JvmFileClassInfo fileClassInfoNoResolve = JvmFileClassUtil.getFileClassInfoNoResolve(containingFile);
                if (fileClassInfoNoResolve.getWithJvmMultifileClass()) {
                    return fileClassInfoNoResolve.getFileClassFqName().shortName().asString();
                }
                return null;
            }
            CallableMemberDescriptor directMember = DescriptorUtils.getDirectMember(callableDescriptor);
            Intrinsics.checkNotNullExpressionValue(directMember, "getDirectMember(...)");
            boolean z = directMember instanceof DeserializedCallableMemberDescriptor;
            ContainingClassesInfo containingClassesForDeserializedCallable = getContainingClassesForDeserializedCallable((DeserializedCallableMemberDescriptor) directMember);
            String strAsString = containingClassesForDeserializedCallable.getFacadeClassId().getShortClassName().asString();
            Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
            String strAsString2 = containingClassesForDeserializedCallable.getImplClassId().getShortClassName().asString();
            Intrinsics.checkNotNullExpressionValue(strAsString2, "asString(...)");
            if (Intrinsics.areEqual(strAsString, strAsString2)) {
                return null;
            }
            return strAsString2;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final String getDefaultDescriptor(Method method, String dispatchReceiverDescriptor, CallableDescriptor callableDescriptor, int extraArgsShift) {
            Type type;
            String descriptor = method.getDescriptor();
            int size = ((callableDescriptor.getValueParameters().size() - extraArgsShift) + 31) / 32;
            if (isConstructor(method) || isInlineClassConstructor(callableDescriptor)) {
                type = AsmTypes.DEFAULT_CONSTRUCTOR_MARKER;
            } else {
                type = AsmTypes.OBJECT_TYPE;
            }
            String str = StringUtil.repeat(Type.INT_TYPE.getDescriptor(), size) + type.getDescriptor();
            Intrinsics.checkNotNull(descriptor);
            String strReplace$default = StringsKt.replace$default(descriptor, Constant.AFTER_QUTO, str + ')', false, 4, (Object) null);
            return (dispatchReceiverDescriptor == null || isConstructor(method)) ? strReplace$default : StringsKt.replace$default(strReplace$default, "(", "(" + dispatchReceiverDescriptor, false, 4, (Object) null);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final boolean isConstructor(Method method) {
            return Intrinsics.areEqual("<init>", method.getName());
        }

        private final boolean isInlineClassConstructor(CallableDescriptor callableDescriptor) {
            return (callableDescriptor instanceof ClassConstructorDescriptor) && InlineClassesUtilsKt.isInlineClass(((ClassConstructorDescriptor) callableDescriptor).getContainingDeclaration());
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final void writeVoidReturn(JvmSignatureWriter sw) {
            sw.writeReturnType();
            sw.writeAsmType(Type.VOID_TYPE);
            sw.writeReturnTypeEnd();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final void writeParameter(JvmSignatureWriter sw, JvmMethodParameterKind kind, Type type) {
            sw.writeParameterType(kind);
            sw.writeAsmType(type);
            sw.writeParameterTypeEnd();
        }

        @JvmStatic
        public final void writeFormalTypeParameter(TypeSystemCommonBackendContext typeSystemCommonBackendContext, TypeParameterMarker typeParameter, JvmSignatureWriter sw, Function2<? super KotlinTypeMarker, ? super TypeMappingMode, Type> mapType) {
            Intrinsics.checkNotNullParameter(typeSystemCommonBackendContext, "<this>");
            Intrinsics.checkNotNullParameter(typeParameter, "typeParameter");
            Intrinsics.checkNotNullParameter(sw, "sw");
            Intrinsics.checkNotNullParameter(mapType, "mapType");
            sw.writeFormalTypeParameter(typeSystemCommonBackendContext.getName(typeParameter).asString());
            sw.writeClassBound();
            int iUpperBoundCount = typeSystemCommonBackendContext.upperBoundCount(typeParameter);
            for (int i = 0; i < iUpperBoundCount; i++) {
                KotlinTypeMarker upperBound = typeSystemCommonBackendContext.getUpperBound(typeParameter, i);
                if (typeSystemCommonBackendContext.getTypeParameterClassifier(typeSystemCommonBackendContext.typeConstructor(upperBound)) == null && !typeSystemCommonBackendContext.isInterfaceOrAnnotationClass(upperBound)) {
                    mapType.invoke(upperBound, TypeMappingMode.GENERIC_ARGUMENT);
                    break;
                }
            }
            sw.writeClassBoundEnd();
            int iUpperBoundCount2 = typeSystemCommonBackendContext.upperBoundCount(typeParameter);
            for (int i2 = 0; i2 < iUpperBoundCount2; i2++) {
                KotlinTypeMarker upperBound2 = typeSystemCommonBackendContext.getUpperBound(typeParameter, i2);
                if (typeSystemCommonBackendContext.getTypeParameterClassifier(typeSystemCommonBackendContext.typeConstructor(upperBound2)) != null || typeSystemCommonBackendContext.isInterfaceOrAnnotationClass(upperBound2)) {
                    sw.writeInterfaceBound();
                    mapType.invoke(upperBound2, TypeMappingMode.GENERIC_ARGUMENT);
                    sw.writeInterfaceBoundEnd();
                }
            }
        }
    }
}
