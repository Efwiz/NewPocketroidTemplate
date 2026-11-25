package org.jetbrains.kotlin.renderer;

import com.huawei.hms.framework.common.hianalytics.CrashHianalyticsData;
import com.huawei.hms.ml.language.common.utils.Constant;
import io.netty.util.internal.StringUtil;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
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
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.CharsKt;
import kotlin.text.StringsKt;
import net.bytebuddy.description.type.TypeDescription;
import org.eclipse.jgit.transport.RefSpec;
import org.jetbrains.kotlin.builtins.FunctionTypesKt;
import org.jetbrains.kotlin.builtins.KotlinBuiltIns;
import org.jetbrains.kotlin.builtins.StandardNames;
import org.jetbrains.kotlin.cli.common.arguments.PreprocessCommandLineArgumentsKt;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.com.intellij.psi.PsiKeyword;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.descriptors.CallableMemberDescriptor;
import org.jetbrains.kotlin.descriptors.ClassConstructorDescriptor;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.ClassKind;
import org.jetbrains.kotlin.descriptors.ClassifierDescriptor;
import org.jetbrains.kotlin.descriptors.ClassifierDescriptorWithTypeParameters;
import org.jetbrains.kotlin.descriptors.ConstructorDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptorVisitor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptorWithSource;
import org.jetbrains.kotlin.descriptors.DescriptorVisibilities;
import org.jetbrains.kotlin.descriptors.DescriptorVisibility;
import org.jetbrains.kotlin.descriptors.FieldDescriptor;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.descriptors.MemberDescriptor;
import org.jetbrains.kotlin.descriptors.Modality;
import org.jetbrains.kotlin.descriptors.ModuleDescriptor;
import org.jetbrains.kotlin.descriptors.NotFoundClasses;
import org.jetbrains.kotlin.descriptors.PackageFragmentDescriptor;
import org.jetbrains.kotlin.descriptors.PackageViewDescriptor;
import org.jetbrains.kotlin.descriptors.PossiblyInnerType;
import org.jetbrains.kotlin.descriptors.PropertyAccessorDescriptor;
import org.jetbrains.kotlin.descriptors.PropertyDescriptor;
import org.jetbrains.kotlin.descriptors.PropertyGetterDescriptor;
import org.jetbrains.kotlin.descriptors.PropertySetterDescriptor;
import org.jetbrains.kotlin.descriptors.ReceiverParameterDescriptor;
import org.jetbrains.kotlin.descriptors.ScriptDescriptor;
import org.jetbrains.kotlin.descriptors.TypeAliasDescriptor;
import org.jetbrains.kotlin.descriptors.TypeParameterDescriptor;
import org.jetbrains.kotlin.descriptors.TypeParameterUtilsKt;
import org.jetbrains.kotlin.descriptors.ValueParameterDescriptor;
import org.jetbrains.kotlin.descriptors.VariableDescriptor;
import org.jetbrains.kotlin.descriptors.annotations.Annotated;
import org.jetbrains.kotlin.descriptors.annotations.AnnotationDescriptor;
import org.jetbrains.kotlin.descriptors.annotations.AnnotationUseSiteTarget;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.load.java.JvmAbi;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.FqNameUnsafe;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.name.SpecialNames;
import org.jetbrains.kotlin.resolve.DescriptorUtils;
import org.jetbrains.kotlin.resolve.constants.AnnotationValue;
import org.jetbrains.kotlin.resolve.constants.ArrayValue;
import org.jetbrains.kotlin.resolve.constants.ConstantValue;
import org.jetbrains.kotlin.resolve.constants.KClassValue;
import org.jetbrains.kotlin.resolve.descriptorUtil.DescriptorUtilsKt;
import org.jetbrains.kotlin.types.AbbreviatedType;
import org.jetbrains.kotlin.types.DefinitelyNotNullType;
import org.jetbrains.kotlin.types.FlexibleType;
import org.jetbrains.kotlin.types.IntersectionTypeConstructor;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.KotlinTypeKt;
import org.jetbrains.kotlin.types.SimpleType;
import org.jetbrains.kotlin.types.SpecialTypesKt;
import org.jetbrains.kotlin.types.StubTypeForBuilderInference;
import org.jetbrains.kotlin.types.TypeConstructor;
import org.jetbrains.kotlin.types.TypeProjection;
import org.jetbrains.kotlin.types.TypeUtils;
import org.jetbrains.kotlin.types.UnwrappedType;
import org.jetbrains.kotlin.types.Variance;
import org.jetbrains.kotlin.types.WrappedType;
import org.jetbrains.kotlin.types.error.ErrorType;
import org.jetbrains.kotlin.types.error.ErrorTypeConstructor;
import org.jetbrains.kotlin.types.error.ErrorUtils;
import org.jetbrains.kotlin.types.typeUtil.TypeUtilsKt;
import org.jetbrains.kotlin.util.capitalizeDecapitalize.CapitalizeDecapitalizeKt;

/* JADX INFO: compiled from: DescriptorRendererImpl.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u0092\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\b\t\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0002\b\u001a\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b-\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u001d\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u000e\n\u0002\u0010\u001e\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0000\u0018\u00002\u00020\u00012\u00020\u0002:\u0002ç\u0002B\r\u0012\u0006\u0010\u0003\u001a\u00020\u0004¢\u0006\u0002\u0010\u0005J\t\u0010É\u0001\u001a\u00020-H\u0002J\u0012\u0010Ê\u0001\u001a\u00020-2\u0007\u0010Ë\u0001\u001a\u00020-H\u0002J\t\u0010Ì\u0001\u001a\u00020-H\u0002J\t\u0010Í\u0001\u001a\u00020-H\u0002J\u0013\u0010Î\u0001\u001a\u00020\u00072\b\u0010Ï\u0001\u001a\u00030Ð\u0001H\u0002J\u0013\u0010Ñ\u0001\u001a\u00020-2\b\u0010Ò\u0001\u001a\u00030Ó\u0001H\u0016J#\u0010Ô\u0001\u001a\u00030Õ\u00012\b\u0010Ö\u0001\u001a\u00030×\u00012\r\u0010Ø\u0001\u001a\b0Ù\u0001j\u0003`Ú\u0001H\u0002J#\u0010Û\u0001\u001a\u00030Õ\u00012\b\u0010Ü\u0001\u001a\u00030Ý\u00012\r\u0010Ø\u0001\u001a\b0Ù\u0001j\u0003`Ú\u0001H\u0002J\u0019\u0010Þ\u0001\u001a\t\u0012\u0004\u0012\u00020-0ß\u00012\u0007\u0010Ö\u0001\u001a\u00020\u0017H\u0002J\u001e\u0010à\u0001\u001a\u00020-2\u0007\u0010á\u0001\u001a\u00020\u00172\n\u0010â\u0001\u001a\u0005\u0018\u00010ã\u0001H\u0016J#\u0010ä\u0001\u001a\u00030Õ\u00012\b\u0010å\u0001\u001a\u00030æ\u00012\r\u0010Ø\u0001\u001a\b0Ù\u0001j\u0003`Ú\u0001H\u0002J#\u0010ç\u0001\u001a\u00030Õ\u00012\b\u0010è\u0001\u001a\u00030é\u00012\r\u0010Ø\u0001\u001a\b0Ù\u0001j\u0003`Ú\u0001H\u0002J#\u0010ê\u0001\u001a\u00030Õ\u00012\b\u0010è\u0001\u001a\u00030é\u00012\r\u0010Ø\u0001\u001a\b0Ù\u0001j\u0003`Ú\u0001H\u0002J\u0013\u0010ë\u0001\u001a\u00020-2\b\u0010è\u0001\u001a\u00030ì\u0001H\u0016J\"\u0010z\u001a\u00030Õ\u00012\b\u0010Ö\u0001\u001a\u00030Ó\u00012\r\u0010Ø\u0001\u001a\b0Ù\u0001j\u0003`Ú\u0001H\u0002J\u0018\u0010í\u0001\u001a\u0004\u0018\u00010-2\u000b\u0010î\u0001\u001a\u0006\u0012\u0002\b\u00030tH\u0002J#\u0010ï\u0001\u001a\u00030Õ\u00012\b\u0010ð\u0001\u001a\u00030ñ\u00012\r\u0010Ø\u0001\u001a\b0Ù\u0001j\u0003`Ú\u0001H\u0002J*\u0010ò\u0001\u001a\u00030Õ\u00012\u000f\u0010ó\u0001\u001a\n\u0012\u0005\u0012\u00030ô\u00010ß\u00012\r\u0010Ø\u0001\u001a\b0Ù\u0001j\u0003`Ú\u0001H\u0002J\u0012\u0010õ\u0001\u001a\u00020-2\u0007\u0010ö\u0001\u001a\u00020-H\u0002J%\u0010÷\u0001\u001a\u00020-2\u0007\u0010ø\u0001\u001a\u00020-2\u0007\u0010ù\u0001\u001a\u00020-2\b\u0010ú\u0001\u001a\u00030û\u0001H\u0016J\u001a\u0010ü\u0001\u001a\u00020-2\u000f\u0010ý\u0001\u001a\n\u0012\u0005\u0012\u00030þ\u00010ß\u0001H\u0002J\u0013\u0010ü\u0001\u001a\u00020-2\b\u0010ÿ\u0001\u001a\u00030\u0080\u0002H\u0016J#\u0010\u0081\u0002\u001a\u00030Õ\u00012\b\u0010\u0082\u0002\u001a\u00030Ý\u00012\r\u0010Ø\u0001\u001a\b0Ù\u0001j\u0003`Ú\u0001H\u0002J#\u0010\u0083\u0002\u001a\u00030Õ\u00012\b\u0010\u0084\u0002\u001a\u00030\u0085\u00022\r\u0010Ø\u0001\u001a\b0Ù\u0001j\u0003`Ú\u0001H\u0002J\u0012\u0010\u0086\u0002\u001a\u00020-2\u0007\u0010ö\u0001\u001a\u00020-H\u0002J#\u0010\u0087\u0002\u001a\u00030Õ\u00012\b\u0010\u0088\u0002\u001a\u00030Ð\u00012\r\u0010Ø\u0001\u001a\b0Ù\u0001j\u0003`Ú\u0001H\u0002J#\u0010\u0089\u0002\u001a\u00030Õ\u00012\b\u0010Ö\u0001\u001a\u00030\u008a\u00022\r\u0010Ø\u0001\u001a\b0Ù\u0001j\u0003`Ú\u0001H\u0002J\u0012\u0010\u008b\u0002\u001a\u00020-2\u0007\u0010\u008c\u0002\u001a\u00020-H\u0016J-\u0010\u008d\u0002\u001a\u00030Õ\u00012\b\u0010\u008e\u0002\u001a\u00030\u008f\u00022\r\u0010Ø\u0001\u001a\b0Ù\u0001j\u0003`Ú\u00012\b\u0010\u0090\u0002\u001a\u00030\u008f\u0002H\u0002J#\u0010\u0091\u0002\u001a\u00030Õ\u00012\b\u0010Ï\u0001\u001a\u00030Ð\u00012\r\u0010Ø\u0001\u001a\b0Ù\u0001j\u0003`Ú\u0001H\u0002J+\u0010\u0092\u0002\u001a\u00030Õ\u00012\r\u0010Ø\u0001\u001a\b0Ù\u0001j\u0003`Ú\u00012\u0007\u0010î\u0001\u001a\u00020\u00072\u0007\u0010\u0093\u0002\u001a\u00020-H\u0002J,\u0010\u0094\u0002\u001a\u00030Õ\u00012\b\u0010Ö\u0001\u001a\u00030Ó\u00012\r\u0010Ø\u0001\u001a\b0Ù\u0001j\u0003`Ú\u00012\u0007\u0010\u0095\u0002\u001a\u00020\u0007H\u0002J\u001c\u0010\u0094\u0002\u001a\u00020-2\b\u0010\u0096\u0002\u001a\u00030þ\u00012\u0007\u0010\u0095\u0002\u001a\u00020\u0007H\u0016J#\u0010\u0097\u0002\u001a\u00030Õ\u00012\b\u0010\u0088\u0002\u001a\u00030Ð\u00012\r\u0010Ø\u0001\u001a\b0Ù\u0001j\u0003`Ú\u0001H\u0002J#\u0010\u0098\u0002\u001a\u00030Õ\u00012\b\u0010\u0099\u0002\u001a\u00030\u009a\u00022\r\u0010Ø\u0001\u001a\b0Ù\u0001j\u0003`Ú\u0001H\u0002J+\u0010\u009b\u0002\u001a\u00030Õ\u00012\u0007\u0010ÿ\u0001\u001a\u0002082\u0007\u0010\u009c\u0002\u001a\u00020-2\r\u0010Ø\u0001\u001a\b0Ù\u0001j\u0003`Ú\u0001H\u0002J#\u0010\u009d\u0002\u001a\u00030Õ\u00012\b\u0010\u009e\u0002\u001a\u00030\u009f\u00022\r\u0010Ø\u0001\u001a\b0Ù\u0001j\u0003`Ú\u0001H\u0002J#\u0010 \u0002\u001a\u00030Õ\u00012\b\u0010¡\u0002\u001a\u00030¢\u00022\r\u0010Ø\u0001\u001a\b0Ù\u0001j\u0003`Ú\u0001H\u0002J#\u0010£\u0002\u001a\u00030Õ\u00012\b\u0010¡\u0002\u001a\u00030¢\u00022\r\u0010Ø\u0001\u001a\b0Ù\u0001j\u0003`Ú\u0001H\u0002J#\u0010¤\u0002\u001a\u00030Õ\u00012\b\u0010¥\u0002\u001a\u00030¦\u00022\r\u0010Ø\u0001\u001a\b0Ù\u0001j\u0003`Ú\u0001H\u0002J#\u0010§\u0002\u001a\u00030Õ\u00012\b\u0010¥\u0002\u001a\u00030¦\u00022\r\u0010Ø\u0001\u001a\b0Ù\u0001j\u0003`Ú\u0001H\u0002J\u0019\u0010¨\u0002\u001a\u00030Õ\u00012\r\u0010Ø\u0001\u001a\b0Ù\u0001j\u0003`Ú\u0001H\u0002J#\u0010©\u0002\u001a\u00030Õ\u00012\b\u0010è\u0001\u001a\u00030é\u00012\r\u0010Ø\u0001\u001a\b0Ù\u0001j\u0003`Ú\u0001H\u0002J#\u0010ª\u0002\u001a\u00030Õ\u00012\b\u0010Ü\u0001\u001a\u00030Ý\u00012\r\u0010Ø\u0001\u001a\b0Ù\u0001j\u0003`Ú\u0001H\u0002J\u0013\u0010«\u0002\u001a\u00020-2\b\u0010¬\u0002\u001a\u00030¨\u0001H\u0016J#\u0010\u00ad\u0002\u001a\u00030Õ\u00012\b\u0010®\u0002\u001a\u00030¯\u00022\r\u0010Ø\u0001\u001a\b0Ù\u0001j\u0003`Ú\u0001H\u0002J\u001a\u0010°\u0002\u001a\u00020-2\u000f\u0010±\u0002\u001a\n\u0012\u0005\u0012\u00030²\u00020ß\u0001H\u0016J\u0013\u0010³\u0002\u001a\u00020-2\b\u0010´\u0002\u001a\u00030µ\u0002H\u0016J,\u0010¶\u0002\u001a\u00030Õ\u00012\b\u0010·\u0002\u001a\u00030¸\u00022\r\u0010Ø\u0001\u001a\b0Ù\u0001j\u0003`Ú\u00012\u0007\u0010¹\u0002\u001a\u00020\u0007H\u0002J*\u0010º\u0002\u001a\u00030Õ\u00012\r\u0010Ø\u0001\u001a\b0Ù\u0001j\u0003`Ú\u00012\u000f\u0010»\u0002\u001a\n\u0012\u0005\u0012\u00030¸\u00020ß\u0001H\u0002J3\u0010¼\u0002\u001a\u00030Õ\u00012\u000f\u0010»\u0002\u001a\n\u0012\u0005\u0012\u00030¸\u00020ß\u00012\r\u0010Ø\u0001\u001a\b0Ù\u0001j\u0003`Ú\u00012\u0007\u0010½\u0002\u001a\u00020\u0007H\u0002J\u0013\u0010¾\u0002\u001a\u00020-2\b\u0010¿\u0002\u001a\u00030²\u0002H\u0016J.\u0010À\u0002\u001a\u00030Õ\u00012\b\u0010\u0084\u0002\u001a\u00030\u0085\u00022\r\u0010Ø\u0001\u001a\b0Ù\u0001j\u0003`Ú\u00012\t\b\u0002\u0010Á\u0002\u001a\u00020\u0007H\u0002J4\u0010Â\u0002\u001a\u00030Õ\u00012\u0007\u0010Ã\u0002\u001a\u00020,2\u0007\u0010Ä\u0002\u001a\u00020\u00072\r\u0010Ø\u0001\u001a\b0Ù\u0001j\u0003`Ú\u00012\u0007\u0010¹\u0002\u001a\u00020\u0007H\u0002J\"\u0010Å\u0002\u001a\u00020-2\u000e\u0010Æ\u0002\u001a\t\u0012\u0004\u0012\u00020,0Ç\u00022\u0007\u0010È\u0002\u001a\u00020\u0007H\u0016J2\u0010Å\u0002\u001a\u00030Õ\u00012\u000e\u0010Æ\u0002\u001a\t\u0012\u0004\u0012\u00020,0Ç\u00022\u0007\u0010È\u0002\u001a\u00020\u00072\r\u0010Ø\u0001\u001a\b0Ù\u0001j\u0003`Ú\u0001H\u0002J@\u0010É\u0002\u001a\u00030Õ\u00012\b\u0010\u0084\u0002\u001a\u00030\u0085\u00022\u0007\u0010Ä\u0002\u001a\u00020\u00072\r\u0010Ø\u0001\u001a\b0Ù\u0001j\u0003`Ú\u00012\u0007\u0010¹\u0002\u001a\u00020\u00072\t\b\u0002\u0010Á\u0002\u001a\u00020\u0007H\u0002J\"\u0010Ê\u0002\u001a\u00020\u00072\b\u0010Ë\u0002\u001a\u00030Ì\u00022\r\u0010Ø\u0001\u001a\b0Ù\u0001j\u0003`Ú\u0001H\u0002J*\u0010Í\u0002\u001a\u00030Õ\u00012\u000f\u0010»\u0002\u001a\n\u0012\u0005\u0012\u00030¸\u00020ß\u00012\r\u0010Ø\u0001\u001a\b0Ù\u0001j\u0003`Ú\u0001H\u0002J\u0013\u0010Î\u0002\u001a\u00020\u00072\b\u0010¬\u0002\u001a\u00030¨\u0001H\u0002J\u0012\u0010Ï\u0002\u001a\u00020\u00072\u0007\u0010È\u0002\u001a\u00020\u0007H\u0002J\u001e\u0010Ð\u0002\u001a\u00030Õ\u0001*\b0Ù\u0001j\u0003`Ú\u00012\b\u0010Ö\u0001\u001a\u00030Ó\u0001H\u0002J%\u0010Ñ\u0002\u001a\u00030Õ\u0001*\b0Ù\u0001j\u0003`Ú\u00012\u000f\u0010Ò\u0002\u001a\n\u0012\u0005\u0012\u00030²\u00020ß\u0001H\u0002J\u000e\u0010Ó\u0002\u001a\u00020\u0007*\u00030¨\u0001H\u0002J\u000f\u0010Ô\u0002\u001a\u00030\u008f\u0002*\u00030\u008a\u0002H\u0002J\r\u0010Õ\u0002\u001a\u00020\u0007*\u00020\u0017H\u0002J\u001e\u0010Ö\u0002\u001a\u00030Õ\u0001*\b0Ù\u0001j\u0003`Ú\u00012\b\u0010×\u0002\u001a\u00030Ø\u0002H\u0002J,\u0010Ù\u0002\u001a\u00030Õ\u0001*\b0Ù\u0001j\u0003`Ú\u00012\b\u0010Ú\u0002\u001a\u00030Û\u00022\f\b\u0002\u0010â\u0001\u001a\u0005\u0018\u00010ã\u0001H\u0002J\u001e\u0010Ü\u0002\u001a\u00030Õ\u0001*\b0Ù\u0001j\u0003`Ú\u00012\b\u0010¬\u0002\u001a\u00030¨\u0001H\u0002J\u000e\u0010Ý\u0002\u001a\u00020-*\u00030¨\u0001H\u0002J\u001e\u0010Þ\u0002\u001a\u00030Õ\u0001*\b0Ù\u0001j\u0003`Ú\u00012\b\u0010¬\u0002\u001a\u00030¨\u0001H\u0002J\u001e\u0010ß\u0002\u001a\u00030Õ\u0001*\b0Ù\u0001j\u0003`Ú\u00012\b\u0010¬\u0002\u001a\u00030¨\u0001H\u0002J\u001e\u0010à\u0002\u001a\u00030Õ\u0001*\b0Ù\u0001j\u0003`Ú\u00012\b\u0010¬\u0002\u001a\u00030¨\u0001H\u0002J\u001e\u0010á\u0002\u001a\u00030Õ\u0001*\b0Ù\u0001j\u0003`Ú\u00012\b\u0010â\u0002\u001a\u00030ã\u0002H\u0002J\u001e\u0010ä\u0002\u001a\u00030Õ\u0001*\b0Ù\u0001j\u0003`Ú\u00012\b\u0010¬\u0002\u001a\u00030å\u0002H\u0002J*\u0010æ\u0002\u001a\u00030Õ\u0001*\b0Ù\u0001j\u0003`Ú\u00012\b\u0010¬\u0002\u001a\u00030¨\u00012\n\b\u0002\u0010´\u0002\u001a\u00030µ\u0002H\u0002R\u0018\u0010\u0006\u001a\u00020\u0007X\u0096\u000f¢\u0006\f\u001a\u0004\b\b\u0010\t\"\u0004\b\n\u0010\u000bR\u0018\u0010\f\u001a\u00020\u0007X\u0096\u000f¢\u0006\f\u001a\u0004\b\r\u0010\t\"\u0004\b\u000e\u0010\u000bR\u0018\u0010\u000f\u001a\u00020\u0010X\u0096\u000f¢\u0006\f\u001a\u0004\b\u0011\u0010\u0012\"\u0004\b\u0013\u0010\u0014R&\u0010\u0015\u001a\u0010\u0012\u0004\u0012\u00020\u0017\u0012\u0004\u0012\u00020\u0007\u0018\u00010\u0016X\u0096\u000f¢\u0006\f\u001a\u0004\b\u0018\u0010\u0019\"\u0004\b\u001a\u0010\u001bR\u0018\u0010\u001c\u001a\u00020\u0007X\u0096\u000f¢\u0006\f\u001a\u0004\b\u001d\u0010\t\"\u0004\b\u001e\u0010\u000bR\u0018\u0010\u001f\u001a\u00020\u0007X\u0096\u000f¢\u0006\f\u001a\u0004\b \u0010\t\"\u0004\b!\u0010\u000bR\u0018\u0010\"\u001a\u00020#X\u0096\u000f¢\u0006\f\u001a\u0004\b$\u0010%\"\u0004\b&\u0010'R\u0018\u0010(\u001a\u00020\u0007X\u0096\u000f¢\u0006\f\u001a\u0004\b)\u0010\t\"\u0004\b*\u0010\u000bR&\u0010+\u001a\u0010\u0012\u0004\u0012\u00020,\u0012\u0004\u0012\u00020-\u0018\u00010\u0016X\u0096\u000f¢\u0006\f\u001a\u0004\b.\u0010\u0019\"\u0004\b/\u0010\u001bR\u0018\u00100\u001a\u00020\u0007X\u0096\u000f¢\u0006\f\u001a\u0004\b1\u0010\t\"\u0004\b2\u0010\u000bR\u0018\u00103\u001a\u00020\u0007X\u0096\u000f¢\u0006\f\u001a\u0004\b4\u0010\t\"\u0004\b5\u0010\u000bR\u001e\u00106\u001a\b\u0012\u0004\u0012\u00020807X\u0096\u000f¢\u0006\f\u001a\u0004\b9\u0010:\"\u0004\b;\u0010<R\u001e\u0010=\u001a\b\u0012\u0004\u0012\u00020807X\u0096\u000f¢\u0006\f\u001a\u0004\b>\u0010:\"\u0004\b?\u0010<R\u001b\u0010@\u001a\u00020\u00008BX\u0082\u0084\u0002¢\u0006\f\n\u0004\bC\u0010D\u001a\u0004\bA\u0010BR\u0018\u0010E\u001a\u00020\u0007X\u0096\u000f¢\u0006\f\u001a\u0004\bF\u0010\t\"\u0004\bG\u0010\u000bR\u0014\u0010H\u001a\u00020\u00078VX\u0096\u0005¢\u0006\u0006\u001a\u0004\bI\u0010\tR\u0014\u0010J\u001a\u00020\u00078VX\u0096\u0005¢\u0006\u0006\u001a\u0004\bK\u0010\tR\u0018\u0010L\u001a\u00020\u0007X\u0096\u000f¢\u0006\f\u001a\u0004\bM\u0010\t\"\u0004\bN\u0010\u000bR\u0018\u0010O\u001a\u00020\u0007X\u0096\u000f¢\u0006\f\u001a\u0004\bP\u0010\t\"\u0004\bQ\u0010\u000bR\u001e\u0010R\u001a\b\u0012\u0004\u0012\u00020S07X\u0096\u000f¢\u0006\f\u001a\u0004\bT\u0010:\"\u0004\bU\u0010<R\u0018\u0010V\u001a\u00020\u0007X\u0096\u000f¢\u0006\f\u001a\u0004\bW\u0010\t\"\u0004\bX\u0010\u000bR\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\bY\u0010ZR\u0018\u0010[\u001a\u00020\\X\u0096\u000f¢\u0006\f\u001a\u0004\b]\u0010^\"\u0004\b_\u0010`R\u0018\u0010a\u001a\u00020bX\u0096\u000f¢\u0006\f\u001a\u0004\bc\u0010d\"\u0004\be\u0010fR\u0018\u0010g\u001a\u00020\u0007X\u0096\u000f¢\u0006\f\u001a\u0004\bh\u0010\t\"\u0004\bi\u0010\u000bR\u0018\u0010j\u001a\u00020\u0007X\u0096\u000f¢\u0006\f\u001a\u0004\bk\u0010\t\"\u0004\bl\u0010\u000bR\u0018\u0010m\u001a\u00020nX\u0096\u000f¢\u0006\f\u001a\u0004\bo\u0010p\"\u0004\bq\u0010rR,\u0010s\u001a\u0016\u0012\b\u0012\u0006\u0012\u0002\b\u00030t\u0012\u0006\u0012\u0004\u0018\u00010-\u0018\u00010\u0016X\u0096\u000f¢\u0006\f\u001a\u0004\bu\u0010\u0019\"\u0004\bv\u0010\u001bR\u0018\u0010w\u001a\u00020\u0007X\u0096\u000f¢\u0006\f\u001a\u0004\bx\u0010\t\"\u0004\by\u0010\u000bR\u0018\u0010z\u001a\u00020\u0007X\u0096\u000f¢\u0006\f\u001a\u0004\b{\u0010\t\"\u0004\b|\u0010\u000bR\u0018\u0010}\u001a\u00020\u0007X\u0096\u000f¢\u0006\f\u001a\u0004\b~\u0010\t\"\u0004\b\u007f\u0010\u000bR\u001b\u0010\u0080\u0001\u001a\u00020\u0007X\u0096\u000f¢\u0006\u000e\u001a\u0005\b\u0081\u0001\u0010\t\"\u0005\b\u0082\u0001\u0010\u000bR\u001b\u0010\u0083\u0001\u001a\u00020\u0007X\u0096\u000f¢\u0006\u000e\u001a\u0005\b\u0084\u0001\u0010\t\"\u0005\b\u0085\u0001\u0010\u000bR\u001b\u0010\u0086\u0001\u001a\u00020\u0007X\u0096\u000f¢\u0006\u000e\u001a\u0005\b\u0087\u0001\u0010\t\"\u0005\b\u0088\u0001\u0010\u000bR\u001b\u0010\u0089\u0001\u001a\u00020\u0007X\u0096\u000f¢\u0006\u000e\u001a\u0005\b\u008a\u0001\u0010\t\"\u0005\b\u008b\u0001\u0010\u000bR\u001b\u0010\u008c\u0001\u001a\u00020\u0007X\u0096\u000f¢\u0006\u000e\u001a\u0005\b\u008d\u0001\u0010\t\"\u0005\b\u008e\u0001\u0010\u000bR\u001b\u0010\u008f\u0001\u001a\u00020\u0007X\u0096\u000f¢\u0006\u000e\u001a\u0005\b\u0090\u0001\u0010\t\"\u0005\b\u0091\u0001\u0010\u000bR\u001b\u0010\u0092\u0001\u001a\u00020\u0007X\u0096\u000f¢\u0006\u000e\u001a\u0005\b\u0093\u0001\u0010\t\"\u0005\b\u0094\u0001\u0010\u000bR\u001b\u0010\u0095\u0001\u001a\u00020\u0007X\u0096\u000f¢\u0006\u000e\u001a\u0005\b\u0096\u0001\u0010\t\"\u0005\b\u0097\u0001\u0010\u000bR\u001b\u0010\u0098\u0001\u001a\u00020\u0007X\u0096\u000f¢\u0006\u000e\u001a\u0005\b\u0099\u0001\u0010\t\"\u0005\b\u009a\u0001\u0010\u000bR\u001b\u0010\u009b\u0001\u001a\u00020\u0007X\u0096\u000f¢\u0006\u000e\u001a\u0005\b\u009c\u0001\u0010\t\"\u0005\b\u009d\u0001\u0010\u000bR\u001b\u0010\u009e\u0001\u001a\u00020\u0007X\u0096\u000f¢\u0006\u000e\u001a\u0005\b\u009f\u0001\u0010\t\"\u0005\b \u0001\u0010\u000bR\u001e\u0010¡\u0001\u001a\u00030¢\u0001X\u0096\u000f¢\u0006\u0010\u001a\u0006\b£\u0001\u0010¤\u0001\"\u0006\b¥\u0001\u0010¦\u0001R)\u0010§\u0001\u001a\u0010\u0012\u0005\u0012\u00030¨\u0001\u0012\u0005\u0012\u00030¨\u00010\u0016X\u0096\u000f¢\u0006\u000e\u001a\u0005\b©\u0001\u0010\u0019\"\u0005\bª\u0001\u0010\u001bR\u001b\u0010«\u0001\u001a\u00020\u0007X\u0096\u000f¢\u0006\u000e\u001a\u0005\b¬\u0001\u0010\t\"\u0005\b\u00ad\u0001\u0010\u000bR\u001b\u0010®\u0001\u001a\u00020\u0007X\u0096\u000f¢\u0006\u000e\u001a\u0005\b¯\u0001\u0010\t\"\u0005\b°\u0001\u0010\u000bR\u001e\u0010±\u0001\u001a\u00030²\u0001X\u0096\u000f¢\u0006\u0010\u001a\u0006\b³\u0001\u0010´\u0001\"\u0006\bµ\u0001\u0010¶\u0001R\u001b\u0010·\u0001\u001a\u00020\u0007X\u0096\u000f¢\u0006\u000e\u001a\u0005\b¸\u0001\u0010\t\"\u0005\b¹\u0001\u0010\u000bR\u001b\u0010º\u0001\u001a\u00020\u0007X\u0096\u000f¢\u0006\u000e\u001a\u0005\b»\u0001\u0010\t\"\u0005\b¼\u0001\u0010\u000bR\u001b\u0010½\u0001\u001a\u00020\u0007X\u0096\u000f¢\u0006\u000e\u001a\u0005\b¾\u0001\u0010\t\"\u0005\b¿\u0001\u0010\u000bR\u001b\u0010À\u0001\u001a\u00020\u0007X\u0096\u000f¢\u0006\u000e\u001a\u0005\bÁ\u0001\u0010\t\"\u0005\bÂ\u0001\u0010\u000bR\u001b\u0010Ã\u0001\u001a\u00020\u0007X\u0096\u000f¢\u0006\u000e\u001a\u0005\bÄ\u0001\u0010\t\"\u0005\bÅ\u0001\u0010\u000bR\u001b\u0010Æ\u0001\u001a\u00020\u0007X\u0096\u000f¢\u0006\u000e\u001a\u0005\bÇ\u0001\u0010\t\"\u0005\bÈ\u0001\u0010\u000b¨\u0006è\u0002"}, d2 = {"Lorg/jetbrains/kotlin/renderer/DescriptorRendererImpl;", "Lorg/jetbrains/kotlin/renderer/DescriptorRenderer;", "Lorg/jetbrains/kotlin/renderer/DescriptorRendererOptions;", "options", "Lorg/jetbrains/kotlin/renderer/DescriptorRendererOptionsImpl;", "(Lorg/jetbrains/kotlin/renderer/DescriptorRendererOptionsImpl;)V", "actualPropertiesInPrimaryConstructor", "", "getActualPropertiesInPrimaryConstructor", "()Z", "setActualPropertiesInPrimaryConstructor", "(Z)V", "alwaysRenderModifiers", "getAlwaysRenderModifiers", "setAlwaysRenderModifiers", "annotationArgumentsRenderingPolicy", "Lorg/jetbrains/kotlin/renderer/AnnotationArgumentsRenderingPolicy;", "getAnnotationArgumentsRenderingPolicy", "()Lorg/jetbrains/kotlin/renderer/AnnotationArgumentsRenderingPolicy;", "setAnnotationArgumentsRenderingPolicy", "(Lorg/jetbrains/kotlin/renderer/AnnotationArgumentsRenderingPolicy;)V", "annotationFilter", "Lkotlin/Function1;", "Lorg/jetbrains/kotlin/descriptors/annotations/AnnotationDescriptor;", "getAnnotationFilter", "()Lkotlin/jvm/functions/Function1;", "setAnnotationFilter", "(Lkotlin/jvm/functions/Function1;)V", "boldOnlyForNamesInHtml", "getBoldOnlyForNamesInHtml", "setBoldOnlyForNamesInHtml", "classWithPrimaryConstructor", "getClassWithPrimaryConstructor", "setClassWithPrimaryConstructor", "classifierNamePolicy", "Lorg/jetbrains/kotlin/renderer/ClassifierNamePolicy;", "getClassifierNamePolicy", "()Lorg/jetbrains/kotlin/renderer/ClassifierNamePolicy;", "setClassifierNamePolicy", "(Lorg/jetbrains/kotlin/renderer/ClassifierNamePolicy;)V", "debugMode", "getDebugMode", "setDebugMode", "defaultParameterValueRenderer", "Lorg/jetbrains/kotlin/descriptors/ValueParameterDescriptor;", "", "getDefaultParameterValueRenderer", "setDefaultParameterValueRenderer", "eachAnnotationOnNewLine", "getEachAnnotationOnNewLine", "setEachAnnotationOnNewLine", "enhancedTypes", "getEnhancedTypes", "setEnhancedTypes", "excludedAnnotationClasses", "", "Lorg/jetbrains/kotlin/name/FqName;", "getExcludedAnnotationClasses", "()Ljava/util/Set;", "setExcludedAnnotationClasses", "(Ljava/util/Set;)V", "excludedTypeAnnotationClasses", "getExcludedTypeAnnotationClasses", "setExcludedTypeAnnotationClasses", "functionTypeAnnotationsRenderer", "getFunctionTypeAnnotationsRenderer", "()Lorg/jetbrains/kotlin/renderer/DescriptorRendererImpl;", "functionTypeAnnotationsRenderer$delegate", "Lkotlin/Lazy;", "includeAdditionalModifiers", "getIncludeAdditionalModifiers", "setIncludeAdditionalModifiers", "includeAnnotationArguments", "getIncludeAnnotationArguments", "includeEmptyAnnotationArguments", "getIncludeEmptyAnnotationArguments", "includePropertyConstant", "getIncludePropertyConstant", "setIncludePropertyConstant", "informativeErrorType", "getInformativeErrorType", "setInformativeErrorType", "modifiers", "Lorg/jetbrains/kotlin/renderer/DescriptorRendererModifier;", "getModifiers", "setModifiers", "normalizedVisibilities", "getNormalizedVisibilities", "setNormalizedVisibilities", "getOptions", "()Lorg/jetbrains/kotlin/renderer/DescriptorRendererOptionsImpl;", "overrideRenderingPolicy", "Lorg/jetbrains/kotlin/renderer/OverrideRenderingPolicy;", "getOverrideRenderingPolicy", "()Lorg/jetbrains/kotlin/renderer/OverrideRenderingPolicy;", "setOverrideRenderingPolicy", "(Lorg/jetbrains/kotlin/renderer/OverrideRenderingPolicy;)V", "parameterNameRenderingPolicy", "Lorg/jetbrains/kotlin/renderer/ParameterNameRenderingPolicy;", "getParameterNameRenderingPolicy", "()Lorg/jetbrains/kotlin/renderer/ParameterNameRenderingPolicy;", "setParameterNameRenderingPolicy", "(Lorg/jetbrains/kotlin/renderer/ParameterNameRenderingPolicy;)V", "parameterNamesInFunctionalTypes", "getParameterNamesInFunctionalTypes", "setParameterNamesInFunctionalTypes", "presentableUnresolvedTypes", "getPresentableUnresolvedTypes", "setPresentableUnresolvedTypes", "propertyAccessorRenderingPolicy", "Lorg/jetbrains/kotlin/renderer/PropertyAccessorRenderingPolicy;", "getPropertyAccessorRenderingPolicy", "()Lorg/jetbrains/kotlin/renderer/PropertyAccessorRenderingPolicy;", "setPropertyAccessorRenderingPolicy", "(Lorg/jetbrains/kotlin/renderer/PropertyAccessorRenderingPolicy;)V", "propertyConstantRenderer", "Lorg/jetbrains/kotlin/resolve/constants/ConstantValue;", "getPropertyConstantRenderer", "setPropertyConstantRenderer", "receiverAfterName", "getReceiverAfterName", "setReceiverAfterName", "renderCompanionObjectName", "getRenderCompanionObjectName", "setRenderCompanionObjectName", "renderConstructorDelegation", "getRenderConstructorDelegation", "setRenderConstructorDelegation", "renderConstructorKeyword", "getRenderConstructorKeyword", "setRenderConstructorKeyword", "renderDefaultAnnotationArguments", "getRenderDefaultAnnotationArguments", "setRenderDefaultAnnotationArguments", "renderDefaultModality", "getRenderDefaultModality", "setRenderDefaultModality", "renderDefaultVisibility", "getRenderDefaultVisibility", "setRenderDefaultVisibility", "renderFunctionContracts", "getRenderFunctionContracts", "setRenderFunctionContracts", "renderPrimaryConstructorParametersAsProperties", "getRenderPrimaryConstructorParametersAsProperties", "setRenderPrimaryConstructorParametersAsProperties", "renderTypeExpansions", "getRenderTypeExpansions", "setRenderTypeExpansions", "renderUnabbreviatedType", "getRenderUnabbreviatedType", "setRenderUnabbreviatedType", "secondaryConstructorsAsPrimary", "getSecondaryConstructorsAsPrimary", "setSecondaryConstructorsAsPrimary", "startFromDeclarationKeyword", "getStartFromDeclarationKeyword", "setStartFromDeclarationKeyword", "startFromName", "getStartFromName", "setStartFromName", "textFormat", "Lorg/jetbrains/kotlin/renderer/RenderingFormat;", "getTextFormat", "()Lorg/jetbrains/kotlin/renderer/RenderingFormat;", "setTextFormat", "(Lorg/jetbrains/kotlin/renderer/RenderingFormat;)V", "typeNormalizer", "Lorg/jetbrains/kotlin/types/KotlinType;", "getTypeNormalizer", "setTypeNormalizer", "uninferredTypeParameterAsName", "getUninferredTypeParameterAsName", "setUninferredTypeParameterAsName", "unitReturnType", "getUnitReturnType", "setUnitReturnType", "valueParametersHandler", "Lorg/jetbrains/kotlin/renderer/DescriptorRenderer$ValueParametersHandler;", "getValueParametersHandler", "()Lorg/jetbrains/kotlin/renderer/DescriptorRenderer$ValueParametersHandler;", "setValueParametersHandler", "(Lorg/jetbrains/kotlin/renderer/DescriptorRenderer$ValueParametersHandler;)V", "verbose", "getVerbose", "setVerbose", "withDefinedIn", "getWithDefinedIn", "setWithDefinedIn", "withSourceFileForTopLevel", "getWithSourceFileForTopLevel", "setWithSourceFileForTopLevel", "withoutReturnType", "getWithoutReturnType", "setWithoutReturnType", "withoutSuperTypes", "getWithoutSuperTypes", "setWithoutSuperTypes", "withoutTypeParameters", "getWithoutTypeParameters", "setWithoutTypeParameters", "arrow", "escape", "string", "gt", "lt", "overridesSomething", "callable", "Lorg/jetbrains/kotlin/descriptors/CallableMemberDescriptor;", "render", "declarationDescriptor", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "renderAccessorModifiers", "", "descriptor", "Lorg/jetbrains/kotlin/descriptors/PropertyAccessorDescriptor;", "builder", "Ljava/lang/StringBuilder;", "Lkotlin/text/StringBuilder;", "renderAdditionalModifiers", "functionDescriptor", "Lorg/jetbrains/kotlin/descriptors/FunctionDescriptor;", "renderAndSortAnnotationArguments", "", "renderAnnotation", "annotation", "target", "Lorg/jetbrains/kotlin/descriptors/annotations/AnnotationUseSiteTarget;", "renderCapturedTypeParametersIfRequired", "classifier", "Lorg/jetbrains/kotlin/descriptors/ClassifierDescriptorWithTypeParameters;", "renderClass", "klass", "Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;", "renderClassKindPrefix", "renderClassifierName", "Lorg/jetbrains/kotlin/descriptors/ClassifierDescriptor;", "renderConstant", "value", "renderConstructor", JvmAbi.ERASED_INLINE_CONSTRUCTOR_NAME, "Lorg/jetbrains/kotlin/descriptors/ConstructorDescriptor;", "renderContextReceivers", "contextReceivers", "Lorg/jetbrains/kotlin/descriptors/ReceiverParameterDescriptor;", "renderError", "keyword", "renderFlexibleType", "lowerRendered", "upperRendered", "builtIns", "Lorg/jetbrains/kotlin/builtins/KotlinBuiltIns;", "renderFqName", "pathSegments", "Lorg/jetbrains/kotlin/name/Name;", "fqName", "Lorg/jetbrains/kotlin/name/FqNameUnsafe;", "renderFunction", "function", "renderInitializer", "variable", "Lorg/jetbrains/kotlin/descriptors/VariableDescriptor;", "renderKeyword", "renderMemberKind", "callableMember", "renderMemberModifiers", "Lorg/jetbrains/kotlin/descriptors/MemberDescriptor;", "renderMessage", CrashHianalyticsData.MESSAGE, "renderModality", "modality", "Lorg/jetbrains/kotlin/descriptors/Modality;", "defaultModality", "renderModalityForCallable", "renderModifier", "modifier", "renderName", "rootRenderedElement", "name", "renderOverride", "renderPackageFragment", "fragment", "Lorg/jetbrains/kotlin/descriptors/PackageFragmentDescriptor;", "renderPackageHeader", "fragmentOrView", "renderPackageView", "packageView", "Lorg/jetbrains/kotlin/descriptors/PackageViewDescriptor;", "renderProperty", "property", "Lorg/jetbrains/kotlin/descriptors/PropertyDescriptor;", "renderPropertyAnnotations", "renderReceiver", "callableDescriptor", "Lorg/jetbrains/kotlin/descriptors/CallableDescriptor;", "renderReceiverAfterName", "renderSpaceIfNeeded", "renderSuperTypes", "renderSuspendModifier", "renderType", ModuleXmlParser.TYPE, "renderTypeAlias", "typeAlias", "Lorg/jetbrains/kotlin/descriptors/TypeAliasDescriptor;", "renderTypeArguments", "typeArguments", "Lorg/jetbrains/kotlin/types/TypeProjection;", "renderTypeConstructor", "typeConstructor", "Lorg/jetbrains/kotlin/types/TypeConstructor;", "renderTypeParameter", "typeParameter", "Lorg/jetbrains/kotlin/descriptors/TypeParameterDescriptor;", "topLevel", "renderTypeParameterList", "typeParameters", "renderTypeParameters", "withSpace", "renderTypeProjection", "typeProjection", "renderValVarPrefix", "isInPrimaryConstructor", "renderValueParameter", "valueParameter", "includeName", "renderValueParameters", "parameters", "", "synthesizedParameterNames", "renderVariable", "renderVisibility", "visibility", "Lorg/jetbrains/kotlin/descriptors/DescriptorVisibility;", "renderWhereSuffix", "shouldRenderAsPrettyFunctionType", "shouldRenderParameterNames", "appendDefinedIn", "appendTypeProjections", "typeProjections", "hasModifiersOrAnnotations", "implicitModalityWithoutExtensions", "isParameterName", "renderAbbreviatedTypeExpansion", "abbreviated", "Lorg/jetbrains/kotlin/types/AbbreviatedType;", "renderAnnotations", "annotated", "Lorg/jetbrains/kotlin/descriptors/annotations/Annotated;", "renderDefaultType", "renderForReceiver", "renderFunctionType", "renderNormalizedType", "renderNormalizedTypeAsIs", "renderPossiblyInnerType", "possiblyInnerType", "Lorg/jetbrains/kotlin/descriptors/PossiblyInnerType;", "renderSimpleType", "Lorg/jetbrains/kotlin/types/SimpleType;", "renderTypeConstructorAndArguments", "RenderDeclarationDescriptorVisitor", "descriptors"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class DescriptorRendererImpl extends DescriptorRenderer implements DescriptorRendererOptions {

    /* JADX INFO: renamed from: functionTypeAnnotationsRenderer$delegate, reason: from kotlin metadata */
    private final Lazy functionTypeAnnotationsRenderer;
    private final DescriptorRendererOptionsImpl options;

    /* JADX INFO: compiled from: DescriptorRendererImpl.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;
        public static final /* synthetic */ int[] $EnumSwitchMapping$1;

        static {
            int[] iArr = new int[RenderingFormat.values().length];
            try {
                iArr[RenderingFormat.PLAIN.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[RenderingFormat.HTML.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            $EnumSwitchMapping$0 = iArr;
            int[] iArr2 = new int[ParameterNameRenderingPolicy.values().length];
            try {
                iArr2[ParameterNameRenderingPolicy.ALL.ordinal()] = 1;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                iArr2[ParameterNameRenderingPolicy.ONLY_NON_SYNTHESIZED.ordinal()] = 2;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                iArr2[ParameterNameRenderingPolicy.NONE.ordinal()] = 3;
            } catch (NoSuchFieldError unused5) {
            }
            $EnumSwitchMapping$1 = iArr2;
        }
    }

    @Override // org.jetbrains.kotlin.renderer.DescriptorRendererOptions
    public boolean getActualPropertiesInPrimaryConstructor() {
        return this.options.getActualPropertiesInPrimaryConstructor();
    }

    @Override // org.jetbrains.kotlin.renderer.DescriptorRendererOptions
    public boolean getAlwaysRenderModifiers() {
        return this.options.getAlwaysRenderModifiers();
    }

    @Override // org.jetbrains.kotlin.renderer.DescriptorRendererOptions
    public AnnotationArgumentsRenderingPolicy getAnnotationArgumentsRenderingPolicy() {
        return this.options.getAnnotationArgumentsRenderingPolicy();
    }

    @Override // org.jetbrains.kotlin.renderer.DescriptorRendererOptions
    public Function1<AnnotationDescriptor, Boolean> getAnnotationFilter() {
        return this.options.getAnnotationFilter();
    }

    @Override // org.jetbrains.kotlin.renderer.DescriptorRendererOptions
    public boolean getBoldOnlyForNamesInHtml() {
        return this.options.getBoldOnlyForNamesInHtml();
    }

    @Override // org.jetbrains.kotlin.renderer.DescriptorRendererOptions
    public boolean getClassWithPrimaryConstructor() {
        return this.options.getClassWithPrimaryConstructor();
    }

    @Override // org.jetbrains.kotlin.renderer.DescriptorRendererOptions
    public ClassifierNamePolicy getClassifierNamePolicy() {
        return this.options.getClassifierNamePolicy();
    }

    @Override // org.jetbrains.kotlin.renderer.DescriptorRendererOptions
    public boolean getDebugMode() {
        return this.options.getDebugMode();
    }

    @Override // org.jetbrains.kotlin.renderer.DescriptorRendererOptions
    public Function1<ValueParameterDescriptor, String> getDefaultParameterValueRenderer() {
        return this.options.getDefaultParameterValueRenderer();
    }

    @Override // org.jetbrains.kotlin.renderer.DescriptorRendererOptions
    public boolean getEachAnnotationOnNewLine() {
        return this.options.getEachAnnotationOnNewLine();
    }

    @Override // org.jetbrains.kotlin.renderer.DescriptorRendererOptions
    public boolean getEnhancedTypes() {
        return this.options.getEnhancedTypes();
    }

    @Override // org.jetbrains.kotlin.renderer.DescriptorRendererOptions
    public Set<FqName> getExcludedAnnotationClasses() {
        return this.options.getExcludedAnnotationClasses();
    }

    @Override // org.jetbrains.kotlin.renderer.DescriptorRendererOptions
    public Set<FqName> getExcludedTypeAnnotationClasses() {
        return this.options.getExcludedTypeAnnotationClasses();
    }

    @Override // org.jetbrains.kotlin.renderer.DescriptorRendererOptions
    public boolean getIncludeAdditionalModifiers() {
        return this.options.getIncludeAdditionalModifiers();
    }

    @Override // org.jetbrains.kotlin.renderer.DescriptorRendererOptions
    public boolean getIncludeAnnotationArguments() {
        return this.options.getIncludeAnnotationArguments();
    }

    @Override // org.jetbrains.kotlin.renderer.DescriptorRendererOptions
    public boolean getIncludeEmptyAnnotationArguments() {
        return this.options.getIncludeEmptyAnnotationArguments();
    }

    @Override // org.jetbrains.kotlin.renderer.DescriptorRendererOptions
    public boolean getIncludePropertyConstant() {
        return this.options.getIncludePropertyConstant();
    }

    @Override // org.jetbrains.kotlin.renderer.DescriptorRendererOptions
    public boolean getInformativeErrorType() {
        return this.options.getInformativeErrorType();
    }

    @Override // org.jetbrains.kotlin.renderer.DescriptorRendererOptions
    public Set<DescriptorRendererModifier> getModifiers() {
        return this.options.getModifiers();
    }

    @Override // org.jetbrains.kotlin.renderer.DescriptorRendererOptions
    public boolean getNormalizedVisibilities() {
        return this.options.getNormalizedVisibilities();
    }

    @Override // org.jetbrains.kotlin.renderer.DescriptorRendererOptions
    public OverrideRenderingPolicy getOverrideRenderingPolicy() {
        return this.options.getOverrideRenderingPolicy();
    }

    @Override // org.jetbrains.kotlin.renderer.DescriptorRendererOptions
    public ParameterNameRenderingPolicy getParameterNameRenderingPolicy() {
        return this.options.getParameterNameRenderingPolicy();
    }

    @Override // org.jetbrains.kotlin.renderer.DescriptorRendererOptions
    public boolean getParameterNamesInFunctionalTypes() {
        return this.options.getParameterNamesInFunctionalTypes();
    }

    @Override // org.jetbrains.kotlin.renderer.DescriptorRendererOptions
    public boolean getPresentableUnresolvedTypes() {
        return this.options.getPresentableUnresolvedTypes();
    }

    @Override // org.jetbrains.kotlin.renderer.DescriptorRendererOptions
    public PropertyAccessorRenderingPolicy getPropertyAccessorRenderingPolicy() {
        return this.options.getPropertyAccessorRenderingPolicy();
    }

    @Override // org.jetbrains.kotlin.renderer.DescriptorRendererOptions
    public Function1<ConstantValue<?>, String> getPropertyConstantRenderer() {
        return this.options.getPropertyConstantRenderer();
    }

    @Override // org.jetbrains.kotlin.renderer.DescriptorRendererOptions
    public boolean getReceiverAfterName() {
        return this.options.getReceiverAfterName();
    }

    @Override // org.jetbrains.kotlin.renderer.DescriptorRendererOptions
    public boolean getRenderCompanionObjectName() {
        return this.options.getRenderCompanionObjectName();
    }

    @Override // org.jetbrains.kotlin.renderer.DescriptorRendererOptions
    public boolean getRenderConstructorDelegation() {
        return this.options.getRenderConstructorDelegation();
    }

    @Override // org.jetbrains.kotlin.renderer.DescriptorRendererOptions
    public boolean getRenderConstructorKeyword() {
        return this.options.getRenderConstructorKeyword();
    }

    @Override // org.jetbrains.kotlin.renderer.DescriptorRendererOptions
    public boolean getRenderDefaultAnnotationArguments() {
        return this.options.getRenderDefaultAnnotationArguments();
    }

    @Override // org.jetbrains.kotlin.renderer.DescriptorRendererOptions
    public boolean getRenderDefaultModality() {
        return this.options.getRenderDefaultModality();
    }

    @Override // org.jetbrains.kotlin.renderer.DescriptorRendererOptions
    public boolean getRenderDefaultVisibility() {
        return this.options.getRenderDefaultVisibility();
    }

    @Override // org.jetbrains.kotlin.renderer.DescriptorRendererOptions
    public boolean getRenderFunctionContracts() {
        return this.options.getRenderFunctionContracts();
    }

    @Override // org.jetbrains.kotlin.renderer.DescriptorRendererOptions
    public boolean getRenderPrimaryConstructorParametersAsProperties() {
        return this.options.getRenderPrimaryConstructorParametersAsProperties();
    }

    @Override // org.jetbrains.kotlin.renderer.DescriptorRendererOptions
    public boolean getRenderTypeExpansions() {
        return this.options.getRenderTypeExpansions();
    }

    @Override // org.jetbrains.kotlin.renderer.DescriptorRendererOptions
    public boolean getRenderUnabbreviatedType() {
        return this.options.getRenderUnabbreviatedType();
    }

    @Override // org.jetbrains.kotlin.renderer.DescriptorRendererOptions
    public boolean getSecondaryConstructorsAsPrimary() {
        return this.options.getSecondaryConstructorsAsPrimary();
    }

    @Override // org.jetbrains.kotlin.renderer.DescriptorRendererOptions
    public boolean getStartFromDeclarationKeyword() {
        return this.options.getStartFromDeclarationKeyword();
    }

    @Override // org.jetbrains.kotlin.renderer.DescriptorRendererOptions
    public boolean getStartFromName() {
        return this.options.getStartFromName();
    }

    @Override // org.jetbrains.kotlin.renderer.DescriptorRendererOptions
    public RenderingFormat getTextFormat() {
        return this.options.getTextFormat();
    }

    @Override // org.jetbrains.kotlin.renderer.DescriptorRendererOptions
    public Function1<KotlinType, KotlinType> getTypeNormalizer() {
        return this.options.getTypeNormalizer();
    }

    @Override // org.jetbrains.kotlin.renderer.DescriptorRendererOptions
    public boolean getUninferredTypeParameterAsName() {
        return this.options.getUninferredTypeParameterAsName();
    }

    @Override // org.jetbrains.kotlin.renderer.DescriptorRendererOptions
    public boolean getUnitReturnType() {
        return this.options.getUnitReturnType();
    }

    @Override // org.jetbrains.kotlin.renderer.DescriptorRendererOptions
    public DescriptorRenderer.ValueParametersHandler getValueParametersHandler() {
        return this.options.getValueParametersHandler();
    }

    @Override // org.jetbrains.kotlin.renderer.DescriptorRendererOptions
    public boolean getVerbose() {
        return this.options.getVerbose();
    }

    @Override // org.jetbrains.kotlin.renderer.DescriptorRendererOptions
    public boolean getWithDefinedIn() {
        return this.options.getWithDefinedIn();
    }

    @Override // org.jetbrains.kotlin.renderer.DescriptorRendererOptions
    public boolean getWithSourceFileForTopLevel() {
        return this.options.getWithSourceFileForTopLevel();
    }

    @Override // org.jetbrains.kotlin.renderer.DescriptorRendererOptions
    public boolean getWithoutReturnType() {
        return this.options.getWithoutReturnType();
    }

    @Override // org.jetbrains.kotlin.renderer.DescriptorRendererOptions
    public boolean getWithoutSuperTypes() {
        return this.options.getWithoutSuperTypes();
    }

    @Override // org.jetbrains.kotlin.renderer.DescriptorRendererOptions
    public boolean getWithoutTypeParameters() {
        return this.options.getWithoutTypeParameters();
    }

    @Override // org.jetbrains.kotlin.renderer.DescriptorRendererOptions
    public void setActualPropertiesInPrimaryConstructor(boolean z) {
        this.options.setActualPropertiesInPrimaryConstructor(z);
    }

    @Override // org.jetbrains.kotlin.renderer.DescriptorRendererOptions
    public void setAlwaysRenderModifiers(boolean z) {
        this.options.setAlwaysRenderModifiers(z);
    }

    @Override // org.jetbrains.kotlin.renderer.DescriptorRendererOptions
    public void setAnnotationArgumentsRenderingPolicy(AnnotationArgumentsRenderingPolicy annotationArgumentsRenderingPolicy) {
        Intrinsics.checkNotNullParameter(annotationArgumentsRenderingPolicy, "<set-?>");
        this.options.setAnnotationArgumentsRenderingPolicy(annotationArgumentsRenderingPolicy);
    }

    @Override // org.jetbrains.kotlin.renderer.DescriptorRendererOptions
    public void setAnnotationFilter(Function1<? super AnnotationDescriptor, Boolean> function1) {
        this.options.setAnnotationFilter(function1);
    }

    @Override // org.jetbrains.kotlin.renderer.DescriptorRendererOptions
    public void setBoldOnlyForNamesInHtml(boolean z) {
        this.options.setBoldOnlyForNamesInHtml(z);
    }

    @Override // org.jetbrains.kotlin.renderer.DescriptorRendererOptions
    public void setClassWithPrimaryConstructor(boolean z) {
        this.options.setClassWithPrimaryConstructor(z);
    }

    @Override // org.jetbrains.kotlin.renderer.DescriptorRendererOptions
    public void setClassifierNamePolicy(ClassifierNamePolicy classifierNamePolicy) {
        Intrinsics.checkNotNullParameter(classifierNamePolicy, "<set-?>");
        this.options.setClassifierNamePolicy(classifierNamePolicy);
    }

    @Override // org.jetbrains.kotlin.renderer.DescriptorRendererOptions
    public void setDebugMode(boolean z) {
        this.options.setDebugMode(z);
    }

    @Override // org.jetbrains.kotlin.renderer.DescriptorRendererOptions
    public void setDefaultParameterValueRenderer(Function1<? super ValueParameterDescriptor, String> function1) {
        this.options.setDefaultParameterValueRenderer(function1);
    }

    @Override // org.jetbrains.kotlin.renderer.DescriptorRendererOptions
    public void setEachAnnotationOnNewLine(boolean z) {
        this.options.setEachAnnotationOnNewLine(z);
    }

    @Override // org.jetbrains.kotlin.renderer.DescriptorRendererOptions
    public void setEnhancedTypes(boolean z) {
        this.options.setEnhancedTypes(z);
    }

    @Override // org.jetbrains.kotlin.renderer.DescriptorRendererOptions
    public void setExcludedAnnotationClasses(Set<FqName> set) {
        Intrinsics.checkNotNullParameter(set, "<set-?>");
        this.options.setExcludedAnnotationClasses(set);
    }

    @Override // org.jetbrains.kotlin.renderer.DescriptorRendererOptions
    public void setExcludedTypeAnnotationClasses(Set<FqName> set) {
        Intrinsics.checkNotNullParameter(set, "<set-?>");
        this.options.setExcludedTypeAnnotationClasses(set);
    }

    @Override // org.jetbrains.kotlin.renderer.DescriptorRendererOptions
    public void setIncludeAdditionalModifiers(boolean z) {
        this.options.setIncludeAdditionalModifiers(z);
    }

    @Override // org.jetbrains.kotlin.renderer.DescriptorRendererOptions
    public void setIncludePropertyConstant(boolean z) {
        this.options.setIncludePropertyConstant(z);
    }

    @Override // org.jetbrains.kotlin.renderer.DescriptorRendererOptions
    public void setInformativeErrorType(boolean z) {
        this.options.setInformativeErrorType(z);
    }

    @Override // org.jetbrains.kotlin.renderer.DescriptorRendererOptions
    public void setModifiers(Set<? extends DescriptorRendererModifier> set) {
        Intrinsics.checkNotNullParameter(set, "<set-?>");
        this.options.setModifiers(set);
    }

    @Override // org.jetbrains.kotlin.renderer.DescriptorRendererOptions
    public void setNormalizedVisibilities(boolean z) {
        this.options.setNormalizedVisibilities(z);
    }

    @Override // org.jetbrains.kotlin.renderer.DescriptorRendererOptions
    public void setOverrideRenderingPolicy(OverrideRenderingPolicy overrideRenderingPolicy) {
        Intrinsics.checkNotNullParameter(overrideRenderingPolicy, "<set-?>");
        this.options.setOverrideRenderingPolicy(overrideRenderingPolicy);
    }

    @Override // org.jetbrains.kotlin.renderer.DescriptorRendererOptions
    public void setParameterNameRenderingPolicy(ParameterNameRenderingPolicy parameterNameRenderingPolicy) {
        Intrinsics.checkNotNullParameter(parameterNameRenderingPolicy, "<set-?>");
        this.options.setParameterNameRenderingPolicy(parameterNameRenderingPolicy);
    }

    @Override // org.jetbrains.kotlin.renderer.DescriptorRendererOptions
    public void setParameterNamesInFunctionalTypes(boolean z) {
        this.options.setParameterNamesInFunctionalTypes(z);
    }

    @Override // org.jetbrains.kotlin.renderer.DescriptorRendererOptions
    public void setPresentableUnresolvedTypes(boolean z) {
        this.options.setPresentableUnresolvedTypes(z);
    }

    @Override // org.jetbrains.kotlin.renderer.DescriptorRendererOptions
    public void setPropertyAccessorRenderingPolicy(PropertyAccessorRenderingPolicy propertyAccessorRenderingPolicy) {
        Intrinsics.checkNotNullParameter(propertyAccessorRenderingPolicy, "<set-?>");
        this.options.setPropertyAccessorRenderingPolicy(propertyAccessorRenderingPolicy);
    }

    @Override // org.jetbrains.kotlin.renderer.DescriptorRendererOptions
    public void setPropertyConstantRenderer(Function1<? super ConstantValue<?>, String> function1) {
        this.options.setPropertyConstantRenderer(function1);
    }

    @Override // org.jetbrains.kotlin.renderer.DescriptorRendererOptions
    public void setReceiverAfterName(boolean z) {
        this.options.setReceiverAfterName(z);
    }

    @Override // org.jetbrains.kotlin.renderer.DescriptorRendererOptions
    public void setRenderCompanionObjectName(boolean z) {
        this.options.setRenderCompanionObjectName(z);
    }

    @Override // org.jetbrains.kotlin.renderer.DescriptorRendererOptions
    public void setRenderConstructorDelegation(boolean z) {
        this.options.setRenderConstructorDelegation(z);
    }

    @Override // org.jetbrains.kotlin.renderer.DescriptorRendererOptions
    public void setRenderConstructorKeyword(boolean z) {
        this.options.setRenderConstructorKeyword(z);
    }

    @Override // org.jetbrains.kotlin.renderer.DescriptorRendererOptions
    public void setRenderDefaultAnnotationArguments(boolean z) {
        this.options.setRenderDefaultAnnotationArguments(z);
    }

    @Override // org.jetbrains.kotlin.renderer.DescriptorRendererOptions
    public void setRenderDefaultModality(boolean z) {
        this.options.setRenderDefaultModality(z);
    }

    @Override // org.jetbrains.kotlin.renderer.DescriptorRendererOptions
    public void setRenderDefaultVisibility(boolean z) {
        this.options.setRenderDefaultVisibility(z);
    }

    @Override // org.jetbrains.kotlin.renderer.DescriptorRendererOptions
    public void setRenderFunctionContracts(boolean z) {
        this.options.setRenderFunctionContracts(z);
    }

    @Override // org.jetbrains.kotlin.renderer.DescriptorRendererOptions
    public void setRenderPrimaryConstructorParametersAsProperties(boolean z) {
        this.options.setRenderPrimaryConstructorParametersAsProperties(z);
    }

    @Override // org.jetbrains.kotlin.renderer.DescriptorRendererOptions
    public void setRenderTypeExpansions(boolean z) {
        this.options.setRenderTypeExpansions(z);
    }

    @Override // org.jetbrains.kotlin.renderer.DescriptorRendererOptions
    public void setRenderUnabbreviatedType(boolean z) {
        this.options.setRenderUnabbreviatedType(z);
    }

    @Override // org.jetbrains.kotlin.renderer.DescriptorRendererOptions
    public void setSecondaryConstructorsAsPrimary(boolean z) {
        this.options.setSecondaryConstructorsAsPrimary(z);
    }

    @Override // org.jetbrains.kotlin.renderer.DescriptorRendererOptions
    public void setStartFromDeclarationKeyword(boolean z) {
        this.options.setStartFromDeclarationKeyword(z);
    }

    @Override // org.jetbrains.kotlin.renderer.DescriptorRendererOptions
    public void setStartFromName(boolean z) {
        this.options.setStartFromName(z);
    }

    @Override // org.jetbrains.kotlin.renderer.DescriptorRendererOptions
    public void setTextFormat(RenderingFormat renderingFormat) {
        Intrinsics.checkNotNullParameter(renderingFormat, "<set-?>");
        this.options.setTextFormat(renderingFormat);
    }

    @Override // org.jetbrains.kotlin.renderer.DescriptorRendererOptions
    public void setTypeNormalizer(Function1<? super KotlinType, ? extends KotlinType> function1) {
        Intrinsics.checkNotNullParameter(function1, "<set-?>");
        this.options.setTypeNormalizer(function1);
    }

    @Override // org.jetbrains.kotlin.renderer.DescriptorRendererOptions
    public void setUninferredTypeParameterAsName(boolean z) {
        this.options.setUninferredTypeParameterAsName(z);
    }

    @Override // org.jetbrains.kotlin.renderer.DescriptorRendererOptions
    public void setUnitReturnType(boolean z) {
        this.options.setUnitReturnType(z);
    }

    @Override // org.jetbrains.kotlin.renderer.DescriptorRendererOptions
    public void setValueParametersHandler(DescriptorRenderer.ValueParametersHandler valueParametersHandler) {
        Intrinsics.checkNotNullParameter(valueParametersHandler, "<set-?>");
        this.options.setValueParametersHandler(valueParametersHandler);
    }

    @Override // org.jetbrains.kotlin.renderer.DescriptorRendererOptions
    public void setVerbose(boolean z) {
        this.options.setVerbose(z);
    }

    @Override // org.jetbrains.kotlin.renderer.DescriptorRendererOptions
    public void setWithDefinedIn(boolean z) {
        this.options.setWithDefinedIn(z);
    }

    @Override // org.jetbrains.kotlin.renderer.DescriptorRendererOptions
    public void setWithSourceFileForTopLevel(boolean z) {
        this.options.setWithSourceFileForTopLevel(z);
    }

    @Override // org.jetbrains.kotlin.renderer.DescriptorRendererOptions
    public void setWithoutReturnType(boolean z) {
        this.options.setWithoutReturnType(z);
    }

    @Override // org.jetbrains.kotlin.renderer.DescriptorRendererOptions
    public void setWithoutSuperTypes(boolean z) {
        this.options.setWithoutSuperTypes(z);
    }

    @Override // org.jetbrains.kotlin.renderer.DescriptorRendererOptions
    public void setWithoutTypeParameters(boolean z) {
        this.options.setWithoutTypeParameters(z);
    }

    public final DescriptorRendererOptionsImpl getOptions() {
        return this.options;
    }

    public DescriptorRendererImpl(DescriptorRendererOptionsImpl options) {
        Intrinsics.checkNotNullParameter(options, "options");
        this.options = options;
        options.getIsLocked();
        this.functionTypeAnnotationsRenderer = LazyKt.lazy(new Function0<DescriptorRendererImpl>() { // from class: org.jetbrains.kotlin.renderer.DescriptorRendererImpl$functionTypeAnnotationsRenderer$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final DescriptorRendererImpl invoke() {
                DescriptorRenderer descriptorRendererWithOptions = this.this$0.withOptions(new Function1<DescriptorRendererOptions, Unit>() { // from class: org.jetbrains.kotlin.renderer.DescriptorRendererImpl$functionTypeAnnotationsRenderer$2.1
                    @Override // kotlin.jvm.functions.Function1
                    public /* bridge */ /* synthetic */ Unit invoke(DescriptorRendererOptions descriptorRendererOptions) {
                        invoke2(descriptorRendererOptions);
                        return Unit.INSTANCE;
                    }

                    /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                    public final void invoke2(DescriptorRendererOptions withOptions) {
                        Intrinsics.checkNotNullParameter(withOptions, "$this$withOptions");
                        withOptions.setExcludedTypeAnnotationClasses(SetsKt.plus((Set) withOptions.getExcludedTypeAnnotationClasses(), (Iterable) CollectionsKt.listOf((Object[]) new FqName[]{StandardNames.FqNames.extensionFunctionType, StandardNames.FqNames.contextFunctionTypeParams})));
                    }
                });
                Intrinsics.checkNotNull(descriptorRendererWithOptions, "null cannot be cast to non-null type org.jetbrains.kotlin.renderer.DescriptorRendererImpl");
                return (DescriptorRendererImpl) descriptorRendererWithOptions;
            }
        });
    }

    private final DescriptorRendererImpl getFunctionTypeAnnotationsRenderer() {
        return (DescriptorRendererImpl) this.functionTypeAnnotationsRenderer.getValue();
    }

    private final String renderKeyword(String keyword) {
        int i = WhenMappings.$EnumSwitchMapping$0[getTextFormat().ordinal()];
        if (i == 1) {
            return keyword;
        }
        if (i == 2) {
            return getBoldOnlyForNamesInHtml() ? keyword : "<b>" + keyword + "</b>";
        }
        throw new NoWhenBranchMatchedException();
    }

    private final String renderError(String keyword) {
        int i = WhenMappings.$EnumSwitchMapping$0[getTextFormat().ordinal()];
        if (i == 1) {
            return keyword;
        }
        if (i == 2) {
            return "<font color=red><b>" + keyword + "</b></font>";
        }
        throw new NoWhenBranchMatchedException();
    }

    private final String escape(String string) {
        return getTextFormat().escape(string);
    }

    private final String lt() {
        return escape("<");
    }

    private final String gt() {
        return escape(">");
    }

    private final String arrow() {
        int i = WhenMappings.$EnumSwitchMapping$0[getTextFormat().ordinal()];
        if (i == 1) {
            return escape("->");
        }
        if (i == 2) {
            return "&rarr;";
        }
        throw new NoWhenBranchMatchedException();
    }

    @Override // org.jetbrains.kotlin.renderer.DescriptorRenderer
    public String renderMessage(String message) {
        Intrinsics.checkNotNullParameter(message, "message");
        int i = WhenMappings.$EnumSwitchMapping$0[getTextFormat().ordinal()];
        if (i == 1) {
            return message;
        }
        if (i == 2) {
            return "<i>" + message + "</i>";
        }
        throw new NoWhenBranchMatchedException();
    }

    @Override // org.jetbrains.kotlin.renderer.DescriptorRenderer
    public String renderName(Name name2, boolean rootRenderedElement) {
        Intrinsics.checkNotNullParameter(name2, "name");
        String strEscape = escape(RenderingUtilsKt.render(name2));
        return (getBoldOnlyForNamesInHtml() && getTextFormat() == RenderingFormat.HTML && rootRenderedElement) ? "<b>" + strEscape + "</b>" : strEscape;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void renderName(DeclarationDescriptor descriptor, StringBuilder builder, boolean rootRenderedElement) {
        Name name2 = descriptor.getShortName();
        Intrinsics.checkNotNullExpressionValue(name2, "getName(...)");
        builder.append(renderName(name2, rootRenderedElement));
    }

    private final void renderCompanionObjectName(DeclarationDescriptor descriptor, StringBuilder builder) {
        if (getRenderCompanionObjectName()) {
            if (getStartFromName()) {
                builder.append("companion object");
            }
            renderSpaceIfNeeded(builder);
            DeclarationDescriptor containingModule = descriptor.getContainingModule();
            if (containingModule != null) {
                builder.append("of ");
                Name name2 = containingModule.getShortName();
                Intrinsics.checkNotNullExpressionValue(name2, "getName(...)");
                builder.append(renderName(name2, false));
            }
        }
        if (getVerbose() || !Intrinsics.areEqual(descriptor.getShortName(), SpecialNames.DEFAULT_NAME_FOR_COMPANION_OBJECT)) {
            if (!getStartFromName()) {
                renderSpaceIfNeeded(builder);
            }
            Name name3 = descriptor.getShortName();
            Intrinsics.checkNotNullExpressionValue(name3, "getName(...)");
            builder.append(renderName(name3, true));
        }
    }

    @Override // org.jetbrains.kotlin.renderer.DescriptorRenderer
    public String renderFqName(FqNameUnsafe fqName) {
        Intrinsics.checkNotNullParameter(fqName, "fqName");
        List<Name> listPathSegments = fqName.pathSegments();
        Intrinsics.checkNotNullExpressionValue(listPathSegments, "pathSegments(...)");
        return renderFqName(listPathSegments);
    }

    private final String renderFqName(List<Name> pathSegments) {
        return escape(RenderingUtilsKt.renderFqName(pathSegments));
    }

    @Override // org.jetbrains.kotlin.renderer.DescriptorRenderer
    public String renderClassifierName(ClassifierDescriptor klass) {
        Intrinsics.checkNotNullParameter(klass, "klass");
        if (ErrorUtils.isError(klass)) {
            return klass.getTypeConstructor().toString();
        }
        return getClassifierNamePolicy().renderClassifier(klass, this);
    }

    @Override // org.jetbrains.kotlin.renderer.DescriptorRenderer
    public String renderType(KotlinType type) {
        Intrinsics.checkNotNullParameter(type, "type");
        StringBuilder sb = new StringBuilder();
        renderNormalizedType(sb, getTypeNormalizer().invoke(type));
        String string = sb.toString();
        Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
        return string;
    }

    private final void renderNormalizedType(StringBuilder sb, KotlinType kotlinType) {
        UnwrappedType unwrappedTypeUnwrap = kotlinType.unwrap();
        AbbreviatedType abbreviatedType = unwrappedTypeUnwrap instanceof AbbreviatedType ? (AbbreviatedType) unwrappedTypeUnwrap : null;
        if (abbreviatedType != null) {
            if (getRenderTypeExpansions()) {
                renderNormalizedTypeAsIs(sb, abbreviatedType.getExpandedType());
                return;
            }
            renderNormalizedTypeAsIs(sb, abbreviatedType.getAbbreviation());
            if (getRenderUnabbreviatedType()) {
                renderAbbreviatedTypeExpansion(sb, abbreviatedType);
                return;
            }
            return;
        }
        renderNormalizedTypeAsIs(sb, kotlinType);
    }

    private final void renderAbbreviatedTypeExpansion(StringBuilder sb, AbbreviatedType abbreviatedType) {
        if (getTextFormat() == RenderingFormat.HTML) {
            sb.append("<font color=\"808080\"><i>");
        }
        sb.append(" /* = ");
        renderNormalizedTypeAsIs(sb, abbreviatedType.getExpandedType());
        sb.append(" */");
        if (getTextFormat() == RenderingFormat.HTML) {
            sb.append("</i></font>");
        }
    }

    private final void renderNormalizedTypeAsIs(StringBuilder sb, KotlinType kotlinType) {
        if ((kotlinType instanceof WrappedType) && getDebugMode() && !((WrappedType) kotlinType).isComputed()) {
            sb.append("<Not computed yet>");
            return;
        }
        UnwrappedType unwrappedTypeUnwrap = kotlinType.unwrap();
        if (unwrappedTypeUnwrap instanceof FlexibleType) {
            sb.append(((FlexibleType) unwrappedTypeUnwrap).render(this, this));
        } else if (unwrappedTypeUnwrap instanceof SimpleType) {
            renderSimpleType(sb, (SimpleType) unwrappedTypeUnwrap);
        }
    }

    private final void renderSimpleType(StringBuilder sb, SimpleType simpleType) {
        if (!Intrinsics.areEqual(simpleType, TypeUtils.CANNOT_INFER_FUNCTION_PARAM_TYPE)) {
            SimpleType simpleType2 = simpleType;
            if (!TypeUtils.isDontCarePlaceholder(simpleType2)) {
                if (ErrorUtils.isUninferredTypeVariable(simpleType2)) {
                    if (getUninferredTypeParameterAsName()) {
                        TypeConstructor constructor = simpleType.getConstructor();
                        Intrinsics.checkNotNull(constructor, "null cannot be cast to non-null type org.jetbrains.kotlin.types.error.ErrorTypeConstructor");
                        sb.append(renderError(((ErrorTypeConstructor) constructor).getParam(0)));
                        return;
                    }
                    sb.append("???");
                    return;
                }
                if (KotlinTypeKt.isError(simpleType2)) {
                    renderDefaultType(sb, simpleType2);
                    return;
                } else if (shouldRenderAsPrettyFunctionType(simpleType2)) {
                    renderFunctionType(sb, simpleType2);
                    return;
                } else {
                    renderDefaultType(sb, simpleType2);
                    return;
                }
            }
        }
        sb.append("???");
    }

    private final boolean shouldRenderAsPrettyFunctionType(KotlinType type) {
        if (FunctionTypesKt.isBuiltinFunctionalType(type)) {
            List<TypeProjection> arguments = type.getArguments();
            if (!(arguments instanceof Collection) || !arguments.isEmpty()) {
                Iterator<T> it = arguments.iterator();
                while (it.hasNext()) {
                    if (((TypeProjection) it.next()).isStarProjection()) {
                    }
                }
            }
            return true;
        }
        return false;
    }

    @Override // org.jetbrains.kotlin.renderer.DescriptorRenderer
    public String renderFlexibleType(String lowerRendered, String upperRendered, KotlinBuiltIns builtIns) {
        Intrinsics.checkNotNullParameter(lowerRendered, "lowerRendered");
        Intrinsics.checkNotNullParameter(upperRendered, "upperRendered");
        Intrinsics.checkNotNullParameter(builtIns, "builtIns");
        if (RenderingUtilsKt.typeStringsDifferOnlyInNullability(lowerRendered, upperRendered)) {
            if (StringsKt.startsWith$default(upperRendered, "(", false, 2, (Object) null)) {
                return "(" + lowerRendered + ")!";
            }
            return lowerRendered + '!';
        }
        ClassifierNamePolicy classifierNamePolicy = getClassifierNamePolicy();
        ClassDescriptor collection = builtIns.getCollection();
        Intrinsics.checkNotNullExpressionValue(collection, "getCollection(...)");
        DescriptorRendererImpl descriptorRendererImpl = this;
        String strSubstringBefore$default = StringsKt.substringBefore$default(classifierNamePolicy.renderClassifier(collection, descriptorRendererImpl), "Collection", (String) null, 2, (Object) null);
        String strReplacePrefixesInTypeRepresentations = RenderingUtilsKt.replacePrefixesInTypeRepresentations(lowerRendered, strSubstringBefore$default + "Mutable", upperRendered, strSubstringBefore$default, strSubstringBefore$default + "(Mutable)");
        if (strReplacePrefixesInTypeRepresentations != null) {
            return strReplacePrefixesInTypeRepresentations;
        }
        String strReplacePrefixesInTypeRepresentations2 = RenderingUtilsKt.replacePrefixesInTypeRepresentations(lowerRendered, strSubstringBefore$default + "MutableMap.MutableEntry", upperRendered, strSubstringBefore$default + "Map.Entry", strSubstringBefore$default + "(Mutable)Map.(Mutable)Entry");
        if (strReplacePrefixesInTypeRepresentations2 != null) {
            return strReplacePrefixesInTypeRepresentations2;
        }
        ClassifierNamePolicy classifierNamePolicy2 = getClassifierNamePolicy();
        ClassDescriptor array = builtIns.getArray();
        Intrinsics.checkNotNullExpressionValue(array, "getArray(...)");
        String strSubstringBefore$default2 = StringsKt.substringBefore$default(classifierNamePolicy2.renderClassifier(array, descriptorRendererImpl), "Array", (String) null, 2, (Object) null);
        String strReplacePrefixesInTypeRepresentations3 = RenderingUtilsKt.replacePrefixesInTypeRepresentations(lowerRendered, strSubstringBefore$default2 + escape("Array<"), upperRendered, strSubstringBefore$default2 + escape("Array<out "), strSubstringBefore$default2 + escape("Array<(out) "));
        return strReplacePrefixesInTypeRepresentations3 != null ? strReplacePrefixesInTypeRepresentations3 : "(" + lowerRendered + ".." + upperRendered + ')';
    }

    @Override // org.jetbrains.kotlin.renderer.DescriptorRenderer
    public String renderTypeArguments(List<? extends TypeProjection> typeArguments) {
        Intrinsics.checkNotNullParameter(typeArguments, "typeArguments");
        if (typeArguments.isEmpty()) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        sb.append(lt());
        appendTypeProjections(sb, typeArguments);
        sb.append(gt());
        String string = sb.toString();
        Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
        return string;
    }

    private final void renderDefaultType(StringBuilder sb, KotlinType kotlinType) {
        renderAnnotations$default(this, sb, kotlinType, null, 2, null);
        DefinitelyNotNullType definitelyNotNullType = kotlinType instanceof DefinitelyNotNullType ? (DefinitelyNotNullType) kotlinType : null;
        SimpleType original = definitelyNotNullType != null ? definitelyNotNullType.getOriginal() : null;
        if (KotlinTypeKt.isError(kotlinType)) {
            if (TypeUtilsKt.isUnresolvedType(kotlinType) && getPresentableUnresolvedTypes()) {
                sb.append(renderError(ErrorUtils.INSTANCE.unresolvedTypeAsItIs(kotlinType)));
            } else {
                if ((kotlinType instanceof ErrorType) && !getInformativeErrorType()) {
                    sb.append(((ErrorType) kotlinType).getDebugMessage());
                } else {
                    sb.append(kotlinType.getConstructor().toString());
                }
                sb.append(renderTypeArguments(kotlinType.getArguments()));
            }
        } else if (kotlinType instanceof StubTypeForBuilderInference) {
            sb.append(((StubTypeForBuilderInference) kotlinType).getOriginalTypeVariable().toString());
        } else if (original instanceof StubTypeForBuilderInference) {
            sb.append(((StubTypeForBuilderInference) original).getOriginalTypeVariable().toString());
        } else {
            renderTypeConstructorAndArguments$default(this, sb, kotlinType, null, 2, null);
        }
        if (kotlinType.getIsMarkedNullable()) {
            sb.append(TypeDescription.Generic.OfWildcardType.SYMBOL);
        }
        if (SpecialTypesKt.isDefinitelyNotNullType(kotlinType)) {
            sb.append(" & Any");
        }
    }

    static /* synthetic */ void renderTypeConstructorAndArguments$default(DescriptorRendererImpl descriptorRendererImpl, StringBuilder sb, KotlinType kotlinType, TypeConstructor typeConstructor, int i, Object obj) {
        if ((i & 2) != 0) {
            typeConstructor = kotlinType.getConstructor();
        }
        descriptorRendererImpl.renderTypeConstructorAndArguments(sb, kotlinType, typeConstructor);
    }

    private final void renderTypeConstructorAndArguments(StringBuilder sb, KotlinType kotlinType, TypeConstructor typeConstructor) {
        PossiblyInnerType possiblyInnerTypeBuildPossiblyInnerType = TypeParameterUtilsKt.buildPossiblyInnerType(kotlinType);
        if (possiblyInnerTypeBuildPossiblyInnerType == null) {
            sb.append(renderTypeConstructor(typeConstructor));
            sb.append(renderTypeArguments(kotlinType.getArguments()));
        } else {
            renderPossiblyInnerType(sb, possiblyInnerTypeBuildPossiblyInnerType);
        }
    }

    /* JADX WARN: Code duplicated, block: B:6:0x0026  */
    private final void renderPossiblyInnerType(StringBuilder sb, PossiblyInnerType possiblyInnerType) {
        PossiblyInnerType outerType = possiblyInnerType.getOuterType();
        if (outerType != null) {
            renderPossiblyInnerType(sb, outerType);
            sb.append('.');
            Name name2 = possiblyInnerType.getClassifierDescriptor().getShortName();
            Intrinsics.checkNotNullExpressionValue(name2, "getName(...)");
            if (sb.append(renderName(name2, false)) == null) {
                TypeConstructor typeConstructor = possiblyInnerType.getClassifierDescriptor().getTypeConstructor();
                Intrinsics.checkNotNullExpressionValue(typeConstructor, "getTypeConstructor(...)");
                sb.append(renderTypeConstructor(typeConstructor));
            }
        } else {
            TypeConstructor typeConstructor2 = possiblyInnerType.getClassifierDescriptor().getTypeConstructor();
            Intrinsics.checkNotNullExpressionValue(typeConstructor2, "getTypeConstructor(...)");
            sb.append(renderTypeConstructor(typeConstructor2));
        }
        sb.append(renderTypeArguments(possiblyInnerType.getArguments()));
    }

    @Override // org.jetbrains.kotlin.renderer.DescriptorRenderer
    public String renderTypeConstructor(TypeConstructor typeConstructor) {
        Intrinsics.checkNotNullParameter(typeConstructor, "typeConstructor");
        ClassifierDescriptor this$0 = typeConstructor.getThis$0();
        if (this$0 instanceof TypeParameterDescriptor ? true : this$0 instanceof ClassDescriptor ? true : this$0 instanceof TypeAliasDescriptor) {
            return renderClassifierName(this$0);
        }
        if (this$0 == null) {
            if (typeConstructor instanceof IntersectionTypeConstructor) {
                return ((IntersectionTypeConstructor) typeConstructor).makeDebugNameForIntersectionType(new Function1<KotlinType, Object>() { // from class: org.jetbrains.kotlin.renderer.DescriptorRendererImpl.renderTypeConstructor.1
                    @Override // kotlin.jvm.functions.Function1
                    public final Object invoke(KotlinType it) {
                        Intrinsics.checkNotNullParameter(it, "it");
                        return it instanceof StubTypeForBuilderInference ? ((StubTypeForBuilderInference) it).getOriginalTypeVariable() : it;
                    }
                });
            }
            return typeConstructor.toString();
        }
        throw new IllegalStateException(("Unexpected classifier: " + this$0.getClass()).toString());
    }

    @Override // org.jetbrains.kotlin.renderer.DescriptorRenderer
    public String renderTypeProjection(TypeProjection typeProjection) {
        Intrinsics.checkNotNullParameter(typeProjection, "typeProjection");
        StringBuilder sb = new StringBuilder();
        appendTypeProjections(sb, CollectionsKt.listOf(typeProjection));
        String string = sb.toString();
        Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
        return string;
    }

    private final void appendTypeProjections(StringBuilder sb, List<? extends TypeProjection> list) {
        CollectionsKt.joinTo(list, sb, (112 & 2) != 0 ? ", " : ", ", (112 & 4) != 0 ? "" : null, (112 & 8) != 0 ? "" : null, (112 & 16) != 0 ? -1 : 0, (112 & 32) != 0 ? "..." : null, (112 & 64) != 0 ? null : new Function1<TypeProjection, CharSequence>() { // from class: org.jetbrains.kotlin.renderer.DescriptorRendererImpl.appendTypeProjections.1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final CharSequence invoke(TypeProjection it) {
                Intrinsics.checkNotNullParameter(it, "it");
                if (it.isStarProjection()) {
                    return "*";
                }
                DescriptorRendererImpl descriptorRendererImpl = DescriptorRendererImpl.this;
                KotlinType nullableAnyType = it.getNullableAnyType();
                Intrinsics.checkNotNullExpressionValue(nullableAnyType, "getType(...)");
                String strRenderType = descriptorRendererImpl.renderType(nullableAnyType);
                if (it.getProjectionKind() != Variance.INVARIANT) {
                    strRenderType = it.getProjectionKind() + StringUtil.SPACE + strRenderType;
                }
                return strRenderType;
            }
        });
    }

    private final void renderFunctionType(StringBuilder sb, KotlinType kotlinType) {
        Name nameExtractParameterNameFromFunctionTypeArgument;
        int length = sb.length();
        renderAnnotations$default(getFunctionTypeAnnotationsRenderer(), sb, kotlinType, null, 2, null);
        boolean z = sb.length() != length;
        KotlinType receiverTypeFromFunctionType = FunctionTypesKt.getReceiverTypeFromFunctionType(kotlinType);
        List<KotlinType> contextReceiverTypesFromFunctionType = FunctionTypesKt.getContextReceiverTypesFromFunctionType(kotlinType);
        if (!contextReceiverTypesFromFunctionType.isEmpty()) {
            sb.append("context(");
            Iterator<KotlinType> it = contextReceiverTypesFromFunctionType.subList(0, CollectionsKt.getLastIndex(contextReceiverTypesFromFunctionType)).iterator();
            while (it.hasNext()) {
                renderNormalizedType(sb, it.next());
                sb.append(", ");
            }
            renderNormalizedType(sb, (KotlinType) CollectionsKt.last((List) contextReceiverTypesFromFunctionType));
            sb.append(") ");
        }
        boolean zIsSuspendFunctionType = FunctionTypesKt.isSuspendFunctionType(kotlinType);
        boolean isMarkedNullable = kotlinType.getIsMarkedNullable();
        boolean z2 = isMarkedNullable || (z && receiverTypeFromFunctionType != null);
        if (z2) {
            if (zIsSuspendFunctionType) {
                sb.insert(length, '(');
            } else {
                if (z) {
                    StringBuilder sb2 = sb;
                    CharsKt.isWhitespace(StringsKt.last(sb2));
                    if (sb.charAt(StringsKt.getLastIndex(sb2) - 1) != ')') {
                        sb.insert(StringsKt.getLastIndex(sb2), "()");
                    }
                }
                sb.append("(");
            }
        }
        renderModifier(sb, zIsSuspendFunctionType, "suspend");
        if (receiverTypeFromFunctionType != null) {
            boolean z3 = (shouldRenderAsPrettyFunctionType(receiverTypeFromFunctionType) && !receiverTypeFromFunctionType.getIsMarkedNullable()) || hasModifiersOrAnnotations(receiverTypeFromFunctionType) || (receiverTypeFromFunctionType instanceof DefinitelyNotNullType);
            if (z3) {
                sb.append("(");
            }
            renderNormalizedType(sb, receiverTypeFromFunctionType);
            if (z3) {
                sb.append(Constant.AFTER_QUTO);
            }
            sb.append(".");
        }
        sb.append("(");
        if (FunctionTypesKt.isBuiltinExtensionFunctionalType(kotlinType) && kotlinType.getArguments().size() <= 1) {
            sb.append("???");
        } else {
            int i = 0;
            for (TypeProjection typeProjection : FunctionTypesKt.getValueParameterTypesFromFunctionType(kotlinType)) {
                int i2 = i + 1;
                if (i > 0) {
                    sb.append(", ");
                }
                if (getParameterNamesInFunctionalTypes()) {
                    KotlinType nullableAnyType = typeProjection.getNullableAnyType();
                    Intrinsics.checkNotNullExpressionValue(nullableAnyType, "getType(...)");
                    nameExtractParameterNameFromFunctionTypeArgument = FunctionTypesKt.extractParameterNameFromFunctionTypeArgument(nullableAnyType);
                } else {
                    nameExtractParameterNameFromFunctionTypeArgument = null;
                }
                if (nameExtractParameterNameFromFunctionTypeArgument != null) {
                    sb.append(renderName(nameExtractParameterNameFromFunctionTypeArgument, false));
                    sb.append(": ");
                }
                sb.append(renderTypeProjection(typeProjection));
                i = i2;
            }
        }
        sb.append(") ").append(arrow()).append(" ");
        renderNormalizedType(sb, FunctionTypesKt.getReturnTypeFromFunctionType(kotlinType));
        if (z2) {
            sb.append(Constant.AFTER_QUTO);
        }
        if (isMarkedNullable) {
            sb.append(TypeDescription.Generic.OfWildcardType.SYMBOL);
        }
    }

    private final boolean hasModifiersOrAnnotations(KotlinType kotlinType) {
        return FunctionTypesKt.isSuspendFunctionType(kotlinType) || !kotlinType.getAnnotations().isEmpty();
    }

    private final void appendDefinedIn(StringBuilder sb, DeclarationDescriptor declarationDescriptor) {
        DeclarationDescriptor containingModule;
        String name_;
        if ((declarationDescriptor instanceof PackageFragmentDescriptor) || (declarationDescriptor instanceof PackageViewDescriptor) || (containingModule = declarationDescriptor.getContainingModule()) == null || (containingModule instanceof ModuleDescriptor)) {
            return;
        }
        sb.append(" ").append(renderMessage("defined in")).append(" ");
        FqNameUnsafe fqName = DescriptorUtils.getFqName(containingModule);
        Intrinsics.checkNotNullExpressionValue(fqName, "getFqName(...)");
        sb.append(fqName.isRoot() ? "root package" : renderFqName(fqName));
        if (getWithSourceFileForTopLevel() && (containingModule instanceof PackageFragmentDescriptor) && (declarationDescriptor instanceof DeclarationDescriptorWithSource) && (name_ = ((DeclarationDescriptorWithSource) declarationDescriptor).getSourceElement().getContainingFile().getName_()) != null) {
            sb.append(" ").append(renderMessage("in file")).append(" ").append(name_);
        }
    }

    static /* synthetic */ void renderAnnotations$default(DescriptorRendererImpl descriptorRendererImpl, StringBuilder sb, Annotated annotated, AnnotationUseSiteTarget annotationUseSiteTarget, int i, Object obj) {
        if ((i & 2) != 0) {
            annotationUseSiteTarget = null;
        }
        descriptorRendererImpl.renderAnnotations(sb, annotated, annotationUseSiteTarget);
    }

    private final void renderAnnotations(StringBuilder sb, Annotated annotated, AnnotationUseSiteTarget annotationUseSiteTarget) {
        if (getModifiers().contains(DescriptorRendererModifier.ANNOTATIONS)) {
            Set<FqName> excludedTypeAnnotationClasses = annotated instanceof KotlinType ? getExcludedTypeAnnotationClasses() : getExcludedAnnotationClasses();
            Function1<AnnotationDescriptor, Boolean> annotationFilter = getAnnotationFilter();
            for (AnnotationDescriptor annotationDescriptor : annotated.getAnnotations()) {
                if (!CollectionsKt.contains(excludedTypeAnnotationClasses, annotationDescriptor.getFqName()) && !isParameterName(annotationDescriptor) && (annotationFilter == null || annotationFilter.invoke(annotationDescriptor).booleanValue())) {
                    sb.append(renderAnnotation(annotationDescriptor, annotationUseSiteTarget));
                    if (getEachAnnotationOnNewLine()) {
                        Intrinsics.checkNotNullExpressionValue(sb.append('\n'), "append(...)");
                    } else {
                        sb.append(" ");
                    }
                }
            }
        }
    }

    private final boolean isParameterName(AnnotationDescriptor annotationDescriptor) {
        return Intrinsics.areEqual(annotationDescriptor.getFqName(), StandardNames.FqNames.parameterName);
    }

    @Override // org.jetbrains.kotlin.renderer.DescriptorRenderer
    public String renderAnnotation(AnnotationDescriptor annotation, AnnotationUseSiteTarget target) {
        Intrinsics.checkNotNullParameter(annotation, "annotation");
        StringBuilder sb = new StringBuilder();
        sb.append('@');
        if (target != null) {
            sb.append(target.getRenderName() + ':');
        }
        KotlinType type = annotation.getType();
        sb.append(renderType(type));
        if (getIncludeAnnotationArguments()) {
            List<String> listRenderAndSortAnnotationArguments = renderAndSortAnnotationArguments(annotation);
            if (getIncludeEmptyAnnotationArguments() || !listRenderAndSortAnnotationArguments.isEmpty()) {
                CollectionsKt.joinTo(listRenderAndSortAnnotationArguments, sb, (112 & 2) != 0 ? ", " : ", ", (112 & 4) != 0 ? "" : "(", (112 & 8) != 0 ? "" : Constant.AFTER_QUTO, (112 & 16) != 0 ? -1 : 0, (112 & 32) != 0 ? "..." : null, (112 & 64) != 0 ? null : null);
            }
        }
        if (getVerbose() && (KotlinTypeKt.isError(type) || (type.getConstructor().getThis$0() instanceof NotFoundClasses.MockClassDescriptor))) {
            sb.append(" /* annotation class not found */");
        }
        String string = sb.toString();
        Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
        return string;
    }

    private final List<String> renderAndSortAnnotationArguments(AnnotationDescriptor descriptor) {
        ClassConstructorDescriptor classConstructorDescriptorMo6347getUnsubstitutedPrimaryConstructor;
        List<ValueParameterDescriptor> valueParameters;
        Map<Name, ConstantValue<?>> allValueArguments = descriptor.getAllValueArguments();
        ArrayList arrayListEmptyList = null;
        ClassDescriptor annotationClass = getRenderDefaultAnnotationArguments() ? DescriptorUtilsKt.getAnnotationClass(descriptor) : null;
        if (annotationClass != null && (classConstructorDescriptorMo6347getUnsubstitutedPrimaryConstructor = annotationClass.mo6347getUnsubstitutedPrimaryConstructor()) != null && (valueParameters = classConstructorDescriptorMo6347getUnsubstitutedPrimaryConstructor.getValueParameters()) != null) {
            ArrayList arrayList = new ArrayList();
            for (Object obj : valueParameters) {
                if (((ValueParameterDescriptor) obj).declaresDefaultValue()) {
                    arrayList.add(obj);
                }
            }
            ArrayList arrayList2 = arrayList;
            ArrayList arrayList3 = new ArrayList(CollectionsKt.collectionSizeOrDefault(arrayList2, 10));
            Iterator it = arrayList2.iterator();
            while (it.hasNext()) {
                arrayList3.add(((ValueParameterDescriptor) it.next()).getShortName());
            }
            arrayListEmptyList = arrayList3;
        }
        if (arrayListEmptyList == null) {
            arrayListEmptyList = CollectionsKt.emptyList();
        }
        ArrayList arrayList4 = new ArrayList();
        for (Object obj2 : arrayListEmptyList) {
            Name name2 = (Name) obj2;
            Intrinsics.checkNotNull(name2);
            if (!allValueArguments.containsKey(name2)) {
                arrayList4.add(obj2);
            }
        }
        ArrayList arrayList5 = arrayList4;
        ArrayList arrayList6 = new ArrayList(CollectionsKt.collectionSizeOrDefault(arrayList5, 10));
        Iterator it2 = arrayList5.iterator();
        while (it2.hasNext()) {
            arrayList6.add(((Name) it2.next()).asString() + " = ...");
        }
        ArrayList arrayList7 = arrayList6;
        Set<Map.Entry<Name, ConstantValue<?>>> setEntrySet = allValueArguments.entrySet();
        ArrayList arrayList8 = new ArrayList(CollectionsKt.collectionSizeOrDefault(setEntrySet, 10));
        Iterator<T> it3 = setEntrySet.iterator();
        while (it3.hasNext()) {
            Map.Entry entry = (Map.Entry) it3.next();
            Name name3 = (Name) entry.getKey();
            arrayList8.add(name3.asString() + " = " + (!arrayListEmptyList.contains(name3) ? renderConstant((ConstantValue) entry.getValue()) : "..."));
        }
        return CollectionsKt.sorted(CollectionsKt.plus((Collection) arrayList7, (Iterable) arrayList8));
    }

    private final String renderConstant(ConstantValue<?> value) {
        Function1<ConstantValue<?>, String> propertyConstantRenderer = this.options.getPropertyConstantRenderer();
        if (propertyConstantRenderer != null) {
            return propertyConstantRenderer.invoke(value);
        }
        if (!(value instanceof ArrayValue)) {
            if (value instanceof AnnotationValue) {
                return StringsKt.removePrefix(DescriptorRenderer.renderAnnotation$default(this, ((AnnotationValue) value).getValue(), null, 2, null), (CharSequence) PreprocessCommandLineArgumentsKt.ARGFILE_ARGUMENT);
            }
            if (value instanceof KClassValue) {
                KClassValue.Value value2 = ((KClassValue) value).getValue();
                if (value2 instanceof KClassValue.Value.LocalClass) {
                    return ((KClassValue.Value.LocalClass) value2).getType() + "::class";
                }
                if (!(value2 instanceof KClassValue.Value.NormalClass)) {
                    throw new NoWhenBranchMatchedException();
                }
                KClassValue.Value.NormalClass normalClass = (KClassValue.Value.NormalClass) value2;
                String strAsString = normalClass.getClassId().asSingleFqName().asString();
                Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
                for (int i = 0; i < normalClass.getArrayDimensions(); i++) {
                    strAsString = "kotlin.Array<" + strAsString + '>';
                }
                return strAsString + "::class";
            }
            return value.toString();
        }
        List<? extends ConstantValue<?>> value3 = ((ArrayValue) value).getValue();
        ArrayList arrayList = new ArrayList();
        Iterator<T> it = value3.iterator();
        while (it.hasNext()) {
            String strRenderConstant = renderConstant((ConstantValue) it.next());
            if (strRenderConstant != null) {
                arrayList.add(strRenderConstant);
            }
        }
        return CollectionsKt.joinToString$default(arrayList, ", ", "{", "}", 0, null, null, 56, null);
    }

    private final boolean renderVisibility(DescriptorVisibility visibility, StringBuilder builder) {
        if (!getModifiers().contains(DescriptorRendererModifier.VISIBILITY)) {
            return false;
        }
        if (getNormalizedVisibilities()) {
            visibility = visibility.normalize();
        }
        if (!getRenderDefaultVisibility() && Intrinsics.areEqual(visibility, DescriptorVisibilities.DEFAULT_VISIBILITY)) {
            return false;
        }
        builder.append(renderKeyword(visibility.getInternalDisplayName())).append(" ");
        return true;
    }

    private final void renderModality(Modality modality, StringBuilder builder, Modality defaultModality) {
        if (getRenderDefaultModality() || modality != defaultModality) {
            renderModifier(builder, getModifiers().contains(DescriptorRendererModifier.MODALITY), CapitalizeDecapitalizeKt.toLowerCaseAsciiOnly(modality.name()));
        }
    }

    private final Modality implicitModalityWithoutExtensions(MemberDescriptor memberDescriptor) {
        if (memberDescriptor instanceof ClassDescriptor) {
            return ((ClassDescriptor) memberDescriptor).getClassKind() == ClassKind.INTERFACE ? Modality.ABSTRACT : Modality.FINAL;
        }
        DeclarationDescriptor containingDeclaration = memberDescriptor.getContainingModule();
        ClassDescriptor classDescriptor = containingDeclaration instanceof ClassDescriptor ? (ClassDescriptor) containingDeclaration : null;
        if (classDescriptor != null && (memberDescriptor instanceof CallableMemberDescriptor)) {
            CallableMemberDescriptor callableMemberDescriptor = (CallableMemberDescriptor) memberDescriptor;
            Collection<? extends CallableMemberDescriptor> overriddenDescriptors = callableMemberDescriptor.getOverriddenDescriptors();
            Intrinsics.checkNotNullExpressionValue(overriddenDescriptors, "getOverriddenDescriptors(...)");
            if (!overriddenDescriptors.isEmpty() && classDescriptor.getModality() != Modality.FINAL) {
                return Modality.OPEN;
            }
            if (classDescriptor.getClassKind() != ClassKind.INTERFACE || Intrinsics.areEqual(callableMemberDescriptor.getVisibility(), DescriptorVisibilities.PRIVATE)) {
                return Modality.FINAL;
            }
            return callableMemberDescriptor.getModality() == Modality.ABSTRACT ? Modality.ABSTRACT : Modality.OPEN;
        }
        return Modality.FINAL;
    }

    private final void renderModalityForCallable(CallableMemberDescriptor callable, StringBuilder builder) {
        if (DescriptorUtils.isTopLevelDeclaration(callable) && callable.getModality() == Modality.FINAL) {
            return;
        }
        if (getOverrideRenderingPolicy() == OverrideRenderingPolicy.RENDER_OVERRIDE && callable.getModality() == Modality.OPEN && overridesSomething(callable)) {
            return;
        }
        Modality modality = callable.getModality();
        Intrinsics.checkNotNullExpressionValue(modality, "getModality(...)");
        renderModality(modality, builder, implicitModalityWithoutExtensions(callable));
    }

    private final void renderOverride(CallableMemberDescriptor callableMember, StringBuilder builder) {
        if (getModifiers().contains(DescriptorRendererModifier.OVERRIDE) && overridesSomething(callableMember) && getOverrideRenderingPolicy() != OverrideRenderingPolicy.RENDER_OPEN) {
            renderModifier(builder, true, "override");
            if (getVerbose()) {
                builder.append(RefSpec.WILDCARD_SUFFIX).append(callableMember.getOverriddenDescriptors().size()).append("*/ ");
            }
        }
    }

    private final void renderMemberKind(CallableMemberDescriptor callableMember, StringBuilder builder) {
        if (getModifiers().contains(DescriptorRendererModifier.MEMBER_KIND) && getVerbose() && callableMember.getKind() != CallableMemberDescriptor.Kind.DECLARATION) {
            builder.append(RefSpec.WILDCARD_SUFFIX).append(CapitalizeDecapitalizeKt.toLowerCaseAsciiOnly(callableMember.getKind().name())).append("*/ ");
        }
    }

    private final void renderModifier(StringBuilder builder, boolean value, String modifier) {
        if (value) {
            builder.append(renderKeyword(modifier));
            builder.append(" ");
        }
    }

    private final void renderMemberModifiers(MemberDescriptor descriptor, StringBuilder builder) {
        renderModifier(builder, descriptor.isExternal(), "external");
        renderModifier(builder, getModifiers().contains(DescriptorRendererModifier.EXPECT) && descriptor.get$isExpect(), "expect");
        renderModifier(builder, getModifiers().contains(DescriptorRendererModifier.ACTUAL) && descriptor.getIsActual(), "actual");
    }

    /* JADX WARN: Code duplicated, block: B:15:0x003b  */
    /* JADX WARN: Code duplicated, block: B:29:0x0072 A[EDGE_INSN: B:29:0x0072->B:30:0x0073 BREAK  A[LOOP:1: B:22:0x0059->B:37:?]] */
    private final void renderAdditionalModifiers(FunctionDescriptor functionDescriptor, StringBuilder builder) {
        boolean z;
        boolean z2 = true;
        if (functionDescriptor.isOperator()) {
            Collection<? extends FunctionDescriptor> overriddenDescriptors = functionDescriptor.getOverriddenDescriptors();
            Intrinsics.checkNotNullExpressionValue(overriddenDescriptors, "getOverriddenDescriptors(...)");
            Collection<? extends FunctionDescriptor> collection = overriddenDescriptors;
            if (!collection.isEmpty()) {
                Iterator<T> it = collection.iterator();
                while (true) {
                    if (it.hasNext()) {
                        if (((FunctionDescriptor) it.next()).isOperator()) {
                            if (!getAlwaysRenderModifiers()) {
                                z = false;
                            }
                        }
                    }
                }
            }
            z = true;
        } else {
            z = false;
        }
        if (!functionDescriptor.isInfix()) {
            z2 = false;
            break;
        }
        Collection<? extends FunctionDescriptor> overriddenDescriptors2 = functionDescriptor.getOverriddenDescriptors();
        Intrinsics.checkNotNullExpressionValue(overriddenDescriptors2, "getOverriddenDescriptors(...)");
        Collection<? extends FunctionDescriptor> collection2 = overriddenDescriptors2;
        if (!collection2.isEmpty()) {
            Iterator<T> it2 = collection2.iterator();
            while (it2.hasNext()) {
                if (((FunctionDescriptor) it2.next()).isInfix()) {
                    if (!getAlwaysRenderModifiers()) {
                        z2 = false;
                        break;
                    }
                    break;
                }
            }
        }
        renderModifier(builder, functionDescriptor.isTailrec(), "tailrec");
        renderSuspendModifier(functionDescriptor, builder);
        renderModifier(builder, functionDescriptor.isInline(), "inline");
        renderModifier(builder, z2, "infix");
        renderModifier(builder, z, "operator");
    }

    private final void renderSuspendModifier(FunctionDescriptor functionDescriptor, StringBuilder builder) {
        renderModifier(builder, functionDescriptor.isSuspend(), "suspend");
    }

    @Override // org.jetbrains.kotlin.renderer.DescriptorRenderer
    public String render(DeclarationDescriptor declarationDescriptor) {
        Intrinsics.checkNotNullParameter(declarationDescriptor, "declarationDescriptor");
        StringBuilder sb = new StringBuilder();
        declarationDescriptor.accept(new RenderDeclarationDescriptorVisitor(), sb);
        if (getWithDefinedIn()) {
            appendDefinedIn(sb, declarationDescriptor);
        }
        String string = sb.toString();
        Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
        return string;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void renderTypeParameter(TypeParameterDescriptor typeParameter, StringBuilder builder, boolean topLevel) {
        if (topLevel) {
            builder.append(lt());
        }
        if (getVerbose()) {
            builder.append(RefSpec.WILDCARD_SUFFIX).append(typeParameter.getIndex()).append("*/ ");
        }
        renderModifier(builder, typeParameter.isReified(), "reified");
        String label = typeParameter.getVariance().getLabel();
        boolean z = true;
        renderModifier(builder, label.length() > 0, label);
        renderAnnotations$default(this, builder, typeParameter, null, 2, null);
        renderName(typeParameter, builder, topLevel);
        int size = typeParameter.getUpperBounds().size();
        if ((size > 1 && !topLevel) || size == 1) {
            KotlinType next = typeParameter.getUpperBounds().iterator().next();
            if (!KotlinBuiltIns.isDefaultBound(next)) {
                StringBuilder sbAppend = builder.append(" : ");
                Intrinsics.checkNotNull(next);
                sbAppend.append(renderType(next));
            }
        } else if (topLevel) {
            for (KotlinType kotlinType : typeParameter.getUpperBounds()) {
                if (!KotlinBuiltIns.isDefaultBound(kotlinType)) {
                    if (z) {
                        builder.append(" : ");
                    } else {
                        builder.append(" & ");
                    }
                    Intrinsics.checkNotNull(kotlinType);
                    builder.append(renderType(kotlinType));
                    z = false;
                }
            }
        }
        if (topLevel) {
            builder.append(gt());
        }
    }

    private final void renderTypeParameters(List<? extends TypeParameterDescriptor> typeParameters, StringBuilder builder, boolean withSpace) {
        if (getWithoutTypeParameters() || typeParameters.isEmpty()) {
            return;
        }
        builder.append(lt());
        renderTypeParameterList(builder, typeParameters);
        builder.append(gt());
        if (withSpace) {
            builder.append(" ");
        }
    }

    private final void renderTypeParameterList(StringBuilder builder, List<? extends TypeParameterDescriptor> typeParameters) {
        Iterator<? extends TypeParameterDescriptor> it = typeParameters.iterator();
        while (it.hasNext()) {
            renderTypeParameter(it.next(), builder, false);
            if (it.hasNext()) {
                builder.append(", ");
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void renderFunction(FunctionDescriptor function, StringBuilder builder) {
        if (!getStartFromName()) {
            if (!getStartFromDeclarationKeyword()) {
                renderAnnotations$default(this, builder, function, null, 2, null);
                List<ReceiverParameterDescriptor> contextReceiverParameters = function.getContextReceiverParameters();
                Intrinsics.checkNotNullExpressionValue(contextReceiverParameters, "getContextReceiverParameters(...)");
                renderContextReceivers(contextReceiverParameters, builder);
                DescriptorVisibility visibility = function.getVisibility();
                Intrinsics.checkNotNullExpressionValue(visibility, "getVisibility(...)");
                renderVisibility(visibility, builder);
                FunctionDescriptor functionDescriptor = function;
                renderModalityForCallable(functionDescriptor, builder);
                if (getIncludeAdditionalModifiers()) {
                    renderMemberModifiers(function, builder);
                }
                renderOverride(functionDescriptor, builder);
                if (getIncludeAdditionalModifiers()) {
                    renderAdditionalModifiers(function, builder);
                } else {
                    renderSuspendModifier(function, builder);
                }
                renderMemberKind(functionDescriptor, builder);
                if (getVerbose()) {
                    if (function.isHiddenToOvercomeSignatureClash()) {
                        builder.append("/*isHiddenToOvercomeSignatureClash*/ ");
                    }
                    if (function.isHiddenForResolutionEverywhereBesideSupercalls()) {
                        builder.append("/*isHiddenForResolutionEverywhereBesideSupercalls*/ ");
                    }
                }
            }
            builder.append(renderKeyword("fun")).append(" ");
            List<TypeParameterDescriptor> typeParameters = function.getTypeParameters();
            Intrinsics.checkNotNullExpressionValue(typeParameters, "getTypeParameters(...)");
            renderTypeParameters(typeParameters, builder, true);
            renderReceiver(function, builder);
        }
        renderName(function, builder, true);
        List<ValueParameterDescriptor> valueParameters = function.getValueParameters();
        Intrinsics.checkNotNullExpressionValue(valueParameters, "getValueParameters(...)");
        renderValueParameters(valueParameters, function.hasSynthesizedParameterNames(), builder);
        renderReceiverAfterName(function, builder);
        KotlinType returnType = function.getReturnType();
        if (!getWithoutReturnType() && (getUnitReturnType() || returnType == null || !KotlinBuiltIns.isUnit(returnType))) {
            builder.append(": ").append(returnType == null ? "[NULL]" : renderType(returnType));
        }
        List<TypeParameterDescriptor> typeParameters2 = function.getTypeParameters();
        Intrinsics.checkNotNullExpressionValue(typeParameters2, "getTypeParameters(...)");
        renderWhereSuffix(typeParameters2, builder);
    }

    private final void renderReceiverAfterName(CallableDescriptor callableDescriptor, StringBuilder builder) {
        ReceiverParameterDescriptor extensionReceiverParameter;
        if (getReceiverAfterName() && (extensionReceiverParameter = callableDescriptor.getExtensionReceiverParameter()) != null) {
            StringBuilder sbAppend = builder.append(" on ");
            KotlinType type = extensionReceiverParameter.getType();
            Intrinsics.checkNotNullExpressionValue(type, "getType(...)");
            sbAppend.append(renderType(type));
        }
    }

    private final String renderForReceiver(KotlinType kotlinType) {
        String strRenderType = renderType(kotlinType);
        return ((!shouldRenderAsPrettyFunctionType(kotlinType) || TypeUtils.isNullableType(kotlinType)) && !(kotlinType instanceof DefinitelyNotNullType)) ? strRenderType : "(" + strRenderType + ')';
    }

    private final void renderContextReceivers(List<? extends ReceiverParameterDescriptor> contextReceivers, StringBuilder builder) {
        if (contextReceivers.isEmpty()) {
            return;
        }
        builder.append("context(");
        int i = 0;
        for (ReceiverParameterDescriptor receiverParameterDescriptor : contextReceivers) {
            int i2 = i + 1;
            renderAnnotations(builder, receiverParameterDescriptor, AnnotationUseSiteTarget.RECEIVER);
            KotlinType type = receiverParameterDescriptor.getType();
            Intrinsics.checkNotNullExpressionValue(type, "getType(...)");
            builder.append(renderForReceiver(type));
            if (i == CollectionsKt.getLastIndex(contextReceivers)) {
                builder.append(") ");
            } else {
                builder.append(", ");
            }
            i = i2;
        }
    }

    private final void renderReceiver(CallableDescriptor callableDescriptor, StringBuilder builder) {
        ReceiverParameterDescriptor extensionReceiverParameter = callableDescriptor.getExtensionReceiverParameter();
        if (extensionReceiverParameter != null) {
            renderAnnotations(builder, extensionReceiverParameter, AnnotationUseSiteTarget.RECEIVER);
            KotlinType type = extensionReceiverParameter.getType();
            Intrinsics.checkNotNullExpressionValue(type, "getType(...)");
            builder.append(renderForReceiver(type)).append(".");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Code duplicated, block: B:9:0x0039  */
    public final void renderConstructor(ConstructorDescriptor constructor, StringBuilder builder) {
        boolean z;
        ClassConstructorDescriptor classConstructorDescriptorMo6347getUnsubstitutedPrimaryConstructor;
        renderAnnotations$default(this, builder, constructor, null, 2, null);
        if (this.options.getRenderDefaultVisibility() || constructor.getConstructedClass().getModality() != Modality.SEALED) {
            DescriptorVisibility visibility = constructor.getVisibility();
            Intrinsics.checkNotNullExpressionValue(visibility, "getVisibility(...)");
            if (renderVisibility(visibility, builder)) {
                z = true;
            } else {
                z = false;
            }
        } else {
            z = false;
        }
        renderMemberKind(constructor, builder);
        boolean z2 = getRenderConstructorKeyword() || !constructor.isPrimary() || z;
        if (z2) {
            builder.append(renderKeyword(JvmAbi.ERASED_INLINE_CONSTRUCTOR_NAME));
        }
        ClassifierDescriptorWithTypeParameters containingModule = constructor.getContainingModule();
        Intrinsics.checkNotNullExpressionValue(containingModule, "getContainingDeclaration(...)");
        if (getSecondaryConstructorsAsPrimary()) {
            if (z2) {
                builder.append(" ");
            }
            renderName(containingModule, builder, true);
            List<TypeParameterDescriptor> typeParameters = constructor.getTypeParameters();
            Intrinsics.checkNotNullExpressionValue(typeParameters, "getTypeParameters(...)");
            renderTypeParameters(typeParameters, builder, false);
        }
        List<ValueParameterDescriptor> valueParameters = constructor.getValueParameters();
        Intrinsics.checkNotNullExpressionValue(valueParameters, "getValueParameters(...)");
        renderValueParameters(valueParameters, constructor.hasSynthesizedParameterNames(), builder);
        if (getRenderConstructorDelegation() && !constructor.isPrimary() && (containingModule instanceof ClassDescriptor) && (classConstructorDescriptorMo6347getUnsubstitutedPrimaryConstructor = ((ClassDescriptor) containingModule).mo6347getUnsubstitutedPrimaryConstructor()) != null) {
            List<ValueParameterDescriptor> valueParameters2 = classConstructorDescriptorMo6347getUnsubstitutedPrimaryConstructor.getValueParameters();
            Intrinsics.checkNotNullExpressionValue(valueParameters2, "getValueParameters(...)");
            ArrayList arrayList = new ArrayList();
            for (Object obj : valueParameters2) {
                ValueParameterDescriptor valueParameterDescriptor = (ValueParameterDescriptor) obj;
                if (!valueParameterDescriptor.declaresDefaultValue() && valueParameterDescriptor.getVarargElementType() == null) {
                    arrayList.add(obj);
                }
            }
            ArrayList arrayList2 = arrayList;
            if (!arrayList2.isEmpty()) {
                builder.append(" : ").append(renderKeyword("this"));
                builder.append(CollectionsKt.joinToString$default(arrayList2, ", ", "(", Constant.AFTER_QUTO, 0, null, new Function1<ValueParameterDescriptor, CharSequence>() { // from class: org.jetbrains.kotlin.renderer.DescriptorRendererImpl.renderConstructor.1
                    @Override // kotlin.jvm.functions.Function1
                    public final CharSequence invoke(ValueParameterDescriptor valueParameterDescriptor2) {
                        return "";
                    }
                }, 24, null));
            }
        }
        if (getSecondaryConstructorsAsPrimary()) {
            List<TypeParameterDescriptor> typeParameters2 = constructor.getTypeParameters();
            Intrinsics.checkNotNullExpressionValue(typeParameters2, "getTypeParameters(...)");
            renderWhereSuffix(typeParameters2, builder);
        }
    }

    private final void renderWhereSuffix(List<? extends TypeParameterDescriptor> typeParameters, StringBuilder builder) {
        if (getWithoutTypeParameters()) {
            return;
        }
        ArrayList arrayList = new ArrayList(0);
        for (TypeParameterDescriptor typeParameterDescriptor : typeParameters) {
            List<KotlinType> upperBounds = typeParameterDescriptor.getUpperBounds();
            Intrinsics.checkNotNullExpressionValue(upperBounds, "getUpperBounds(...)");
            for (KotlinType kotlinType : CollectionsKt.drop(upperBounds, 1)) {
                StringBuilder sb = new StringBuilder();
                Name name2 = typeParameterDescriptor.getShortName();
                Intrinsics.checkNotNullExpressionValue(name2, "getName(...)");
                StringBuilder sbAppend = sb.append(renderName(name2, false)).append(" : ");
                Intrinsics.checkNotNull(kotlinType);
                arrayList.add(sbAppend.append(renderType(kotlinType)).toString());
            }
        }
        if (arrayList.isEmpty()) {
            return;
        }
        builder.append(" ").append(renderKeyword("where")).append(" ");
        CollectionsKt.joinTo(arrayList, builder, (112 & 2) != 0 ? ", " : ", ", (112 & 4) != 0 ? "" : null, (112 & 8) != 0 ? "" : null, (112 & 16) != 0 ? -1 : 0, (112 & 32) != 0 ? "..." : null, (112 & 64) != 0 ? null : null);
    }

    @Override // org.jetbrains.kotlin.renderer.DescriptorRenderer
    public String renderValueParameters(Collection<? extends ValueParameterDescriptor> parameters, boolean synthesizedParameterNames) {
        Intrinsics.checkNotNullParameter(parameters, "parameters");
        StringBuilder sb = new StringBuilder();
        renderValueParameters(parameters, synthesizedParameterNames, sb);
        String string = sb.toString();
        Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
        return string;
    }

    private final void renderValueParameters(Collection<? extends ValueParameterDescriptor> parameters, boolean synthesizedParameterNames, StringBuilder builder) {
        boolean zShouldRenderParameterNames = shouldRenderParameterNames(synthesizedParameterNames);
        int size = parameters.size();
        getValueParametersHandler().appendBeforeValueParameters(size, builder);
        int i = 0;
        for (ValueParameterDescriptor valueParameterDescriptor : parameters) {
            getValueParametersHandler().appendBeforeValueParameter(valueParameterDescriptor, i, size, builder);
            renderValueParameter(valueParameterDescriptor, zShouldRenderParameterNames, builder, false);
            getValueParametersHandler().appendAfterValueParameter(valueParameterDescriptor, i, size, builder);
            i++;
        }
        getValueParametersHandler().appendAfterValueParameters(size, builder);
    }

    private final boolean shouldRenderParameterNames(boolean synthesizedParameterNames) {
        int i = WhenMappings.$EnumSwitchMapping$1[getParameterNameRenderingPolicy().ordinal()];
        if (i == 1) {
            return true;
        }
        if (i != 2) {
            if (i != 3) {
                throw new NoWhenBranchMatchedException();
            }
        } else if (!synthesizedParameterNames) {
            return true;
        }
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Code duplicated, block: B:17:0x0063  */
    public final void renderValueParameter(ValueParameterDescriptor valueParameter, boolean includeName, StringBuilder builder, boolean topLevel) {
        boolean z;
        if (topLevel) {
            builder.append(renderKeyword("value-parameter")).append(" ");
        }
        if (getVerbose()) {
            builder.append(RefSpec.WILDCARD_SUFFIX).append(valueParameter.getIndex()).append("*/ ");
        }
        renderAnnotations$default(this, builder, valueParameter, null, 2, null);
        renderModifier(builder, valueParameter.getIsCrossinline(), "crossinline");
        renderModifier(builder, valueParameter.getIsNoinline(), "noinline");
        if (getRenderPrimaryConstructorParametersAsProperties()) {
            CallableDescriptor containingModule = valueParameter.getContainingModule();
            ClassConstructorDescriptor classConstructorDescriptor = containingModule instanceof ClassConstructorDescriptor ? (ClassConstructorDescriptor) containingModule : null;
            if (classConstructorDescriptor != null) {
                z = classConstructorDescriptor.isPrimary();
            }
        }
        boolean z2 = z;
        if (z2) {
            renderModifier(builder, getActualPropertiesInPrimaryConstructor(), "actual");
        }
        renderVariable(valueParameter, includeName, builder, topLevel, z2);
        if (getDefaultParameterValueRenderer() != null) {
            if (getDebugMode() ? valueParameter.declaresDefaultValue() : DescriptorUtilsKt.declaresOrInheritsDefaultValue(valueParameter)) {
                StringBuilder sb = new StringBuilder(" = ");
                Function1<ValueParameterDescriptor, String> defaultParameterValueRenderer = getDefaultParameterValueRenderer();
                Intrinsics.checkNotNull(defaultParameterValueRenderer);
                builder.append(sb.append(defaultParameterValueRenderer.invoke(valueParameter)).toString());
            }
        }
    }

    static /* synthetic */ void renderValVarPrefix$default(DescriptorRendererImpl descriptorRendererImpl, VariableDescriptor variableDescriptor, StringBuilder sb, boolean z, int i, Object obj) {
        if ((i & 4) != 0) {
            z = false;
        }
        descriptorRendererImpl.renderValVarPrefix(variableDescriptor, sb, z);
    }

    private final void renderValVarPrefix(VariableDescriptor variable, StringBuilder builder, boolean isInPrimaryConstructor) {
        if (isInPrimaryConstructor || !(variable instanceof ValueParameterDescriptor)) {
            builder.append(renderKeyword(variable.isVar() ? PsiKeyword.VAR : "val")).append(" ");
        }
    }

    static /* synthetic */ void renderVariable$default(DescriptorRendererImpl descriptorRendererImpl, VariableDescriptor variableDescriptor, boolean z, StringBuilder sb, boolean z2, boolean z3, int i, Object obj) {
        if ((i & 16) != 0) {
            z3 = false;
        }
        descriptorRendererImpl.renderVariable(variableDescriptor, z, sb, z2, z3);
    }

    private final void renderVariable(VariableDescriptor variable, boolean includeName, StringBuilder builder, boolean topLevel, boolean isInPrimaryConstructor) {
        KotlinType type = variable.getType();
        Intrinsics.checkNotNullExpressionValue(type, "getType(...)");
        ValueParameterDescriptor valueParameterDescriptor = variable instanceof ValueParameterDescriptor ? (ValueParameterDescriptor) variable : null;
        KotlinType varargElementType = valueParameterDescriptor != null ? valueParameterDescriptor.getVarargElementType() : null;
        KotlinType kotlinType = varargElementType == null ? type : varargElementType;
        renderModifier(builder, varargElementType != null, "vararg");
        if (isInPrimaryConstructor || (topLevel && !getStartFromName())) {
            renderValVarPrefix(variable, builder, isInPrimaryConstructor);
        }
        if (includeName) {
            renderName(variable, builder, topLevel);
            builder.append(": ");
        }
        builder.append(renderType(kotlinType));
        renderInitializer(variable, builder);
        if (!getVerbose() || varargElementType == null) {
            return;
        }
        builder.append(" /*").append(renderType(type)).append("*/");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void renderProperty(PropertyDescriptor property, StringBuilder builder) {
        if (!getStartFromName()) {
            if (!getStartFromDeclarationKeyword()) {
                renderPropertyAnnotations(property, builder);
                List<ReceiverParameterDescriptor> contextReceiverParameters = property.getContextReceiverParameters();
                Intrinsics.checkNotNullExpressionValue(contextReceiverParameters, "getContextReceiverParameters(...)");
                renderContextReceivers(contextReceiverParameters, builder);
                DescriptorVisibility visibility = property.getVisibility();
                Intrinsics.checkNotNullExpressionValue(visibility, "getVisibility(...)");
                renderVisibility(visibility, builder);
                boolean z = false;
                renderModifier(builder, getModifiers().contains(DescriptorRendererModifier.CONST) && property.isConst(), PsiKeyword.CONST);
                renderMemberModifiers(property, builder);
                PropertyDescriptor propertyDescriptor = property;
                renderModalityForCallable(propertyDescriptor, builder);
                renderOverride(propertyDescriptor, builder);
                if (getModifiers().contains(DescriptorRendererModifier.LATEINIT) && property.isLateInit()) {
                    z = true;
                }
                renderModifier(builder, z, "lateinit");
                renderMemberKind(propertyDescriptor, builder);
            }
            renderValVarPrefix$default(this, property, builder, false, 4, null);
            List<TypeParameterDescriptor> typeParameters = property.getTypeParameters();
            Intrinsics.checkNotNullExpressionValue(typeParameters, "getTypeParameters(...)");
            renderTypeParameters(typeParameters, builder, true);
            renderReceiver(property, builder);
        }
        renderName(property, builder, true);
        StringBuilder sbAppend = builder.append(": ");
        KotlinType type = property.getType();
        Intrinsics.checkNotNullExpressionValue(type, "getType(...)");
        sbAppend.append(renderType(type));
        renderReceiverAfterName(property, builder);
        renderInitializer(property, builder);
        List<TypeParameterDescriptor> typeParameters2 = property.getTypeParameters();
        Intrinsics.checkNotNullExpressionValue(typeParameters2, "getTypeParameters(...)");
        renderWhereSuffix(typeParameters2, builder);
    }

    private final void renderPropertyAnnotations(PropertyDescriptor property, StringBuilder builder) {
        if (getModifiers().contains(DescriptorRendererModifier.ANNOTATIONS)) {
            renderAnnotations$default(this, builder, property, null, 2, null);
            FieldDescriptor backingField = property.getBackingField();
            if (backingField != null) {
                renderAnnotations(builder, backingField, AnnotationUseSiteTarget.FIELD);
            }
            FieldDescriptor delegateField = property.getDelegateField();
            if (delegateField != null) {
                renderAnnotations(builder, delegateField, AnnotationUseSiteTarget.PROPERTY_DELEGATE_FIELD);
            }
            if (getPropertyAccessorRenderingPolicy() == PropertyAccessorRenderingPolicy.NONE) {
                PropertyGetterDescriptor getter = property.getGetter();
                if (getter != null) {
                    renderAnnotations(builder, getter, AnnotationUseSiteTarget.PROPERTY_GETTER);
                }
                PropertySetterDescriptor setter = property.getSetter();
                if (setter != null) {
                    renderAnnotations(builder, setter, AnnotationUseSiteTarget.PROPERTY_SETTER);
                    List<ValueParameterDescriptor> valueParameters = setter.getValueParameters();
                    Intrinsics.checkNotNullExpressionValue(valueParameters, "getValueParameters(...)");
                    ValueParameterDescriptor valueParameterDescriptor = (ValueParameterDescriptor) CollectionsKt.single((List) valueParameters);
                    Intrinsics.checkNotNull(valueParameterDescriptor);
                    renderAnnotations(builder, valueParameterDescriptor, AnnotationUseSiteTarget.SETTER_PARAMETER);
                }
            }
        }
    }

    private final void renderInitializer(VariableDescriptor variable, StringBuilder builder) {
        ConstantValue<?> constantValueMo6754getCompileTimeInitializer;
        String strRenderConstant;
        if (!getIncludePropertyConstant() || (constantValueMo6754getCompileTimeInitializer = variable.mo6754getCompileTimeInitializer()) == null || (strRenderConstant = renderConstant(constantValueMo6754getCompileTimeInitializer)) == null) {
            return;
        }
        builder.append(" = ").append(escape(strRenderConstant));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void renderTypeAlias(TypeAliasDescriptor typeAlias, StringBuilder builder) {
        renderAnnotations$default(this, builder, typeAlias, null, 2, null);
        DescriptorVisibility visibility = typeAlias.getVisibility();
        Intrinsics.checkNotNullExpressionValue(visibility, "getVisibility(...)");
        renderVisibility(visibility, builder);
        renderMemberModifiers(typeAlias, builder);
        builder.append(renderKeyword("typealias")).append(" ");
        renderName(typeAlias, builder, true);
        List<TypeParameterDescriptor> declaredTypeParameters = typeAlias.getDeclaredTypeParameters();
        Intrinsics.checkNotNullExpressionValue(declaredTypeParameters, "getDeclaredTypeParameters(...)");
        renderTypeParameters(declaredTypeParameters, builder, false);
        renderCapturedTypeParametersIfRequired(typeAlias, builder);
        builder.append(" = ").append(renderType(typeAlias.getUnderlyingType()));
    }

    private final void renderCapturedTypeParametersIfRequired(ClassifierDescriptorWithTypeParameters classifier, StringBuilder builder) {
        List<TypeParameterDescriptor> declaredTypeParameters = classifier.getDeclaredTypeParameters();
        Intrinsics.checkNotNullExpressionValue(declaredTypeParameters, "getDeclaredTypeParameters(...)");
        List<TypeParameterDescriptor> parameters = classifier.getTypeConstructor().getParameters();
        Intrinsics.checkNotNullExpressionValue(parameters, "getParameters(...)");
        if (getVerbose() && classifier.getIsInner() && parameters.size() > declaredTypeParameters.size()) {
            builder.append(" /*captured type parameters: ");
            renderTypeParameterList(builder, parameters.subList(declaredTypeParameters.size(), parameters.size()));
            builder.append("*/");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void renderClass(ClassDescriptor klass, StringBuilder builder) {
        ClassConstructorDescriptor classConstructorDescriptorMo6347getUnsubstitutedPrimaryConstructor;
        boolean z = klass.getClassKind() == ClassKind.ENUM_ENTRY;
        if (!getStartFromName()) {
            renderAnnotations$default(this, builder, klass, null, 2, null);
            List<ReceiverParameterDescriptor> contextReceivers = klass.getContextReceivers();
            Intrinsics.checkNotNullExpressionValue(contextReceivers, "getContextReceivers(...)");
            renderContextReceivers(contextReceivers, builder);
            if (!z) {
                DescriptorVisibility visibility = klass.getVisibility();
                Intrinsics.checkNotNullExpressionValue(visibility, "getVisibility(...)");
                renderVisibility(visibility, builder);
            }
            if ((klass.getClassKind() != ClassKind.INTERFACE || klass.getModality() != Modality.ABSTRACT) && (!klass.getClassKind().isSingleton() || klass.getModality() != Modality.FINAL)) {
                Modality modality = klass.getModality();
                Intrinsics.checkNotNullExpressionValue(modality, "getModality(...)");
                renderModality(modality, builder, implicitModalityWithoutExtensions(klass));
            }
            renderMemberModifiers(klass, builder);
            renderModifier(builder, getModifiers().contains(DescriptorRendererModifier.INNER) && klass.getIsInner(), "inner");
            renderModifier(builder, getModifiers().contains(DescriptorRendererModifier.DATA) && klass.isData(), "data");
            renderModifier(builder, getModifiers().contains(DescriptorRendererModifier.INLINE) && klass.isInline(), "inline");
            renderModifier(builder, getModifiers().contains(DescriptorRendererModifier.VALUE) && klass.isValue(), "value");
            renderModifier(builder, getModifiers().contains(DescriptorRendererModifier.FUN) && klass.isFun(), "fun");
            renderClassKindPrefix(klass, builder);
        }
        ClassDescriptor classDescriptor = klass;
        if (!DescriptorUtils.isCompanionObject(classDescriptor)) {
            if (!getStartFromName()) {
                renderSpaceIfNeeded(builder);
            }
            renderName(classDescriptor, builder, true);
        } else {
            renderCompanionObjectName(classDescriptor, builder);
        }
        if (z) {
            return;
        }
        List<TypeParameterDescriptor> declaredTypeParameters = klass.getDeclaredTypeParameters();
        Intrinsics.checkNotNullExpressionValue(declaredTypeParameters, "getDeclaredTypeParameters(...)");
        renderTypeParameters(declaredTypeParameters, builder, false);
        renderCapturedTypeParametersIfRequired(klass, builder);
        if (!klass.getClassKind().isSingleton() && getClassWithPrimaryConstructor() && (classConstructorDescriptorMo6347getUnsubstitutedPrimaryConstructor = klass.mo6347getUnsubstitutedPrimaryConstructor()) != null) {
            builder.append(" ");
            renderAnnotations$default(this, builder, classConstructorDescriptorMo6347getUnsubstitutedPrimaryConstructor, null, 2, null);
            DescriptorVisibility visibility2 = classConstructorDescriptorMo6347getUnsubstitutedPrimaryConstructor.getVisibility();
            Intrinsics.checkNotNullExpressionValue(visibility2, "getVisibility(...)");
            renderVisibility(visibility2, builder);
            builder.append(renderKeyword(JvmAbi.ERASED_INLINE_CONSTRUCTOR_NAME));
            List<ValueParameterDescriptor> valueParameters = classConstructorDescriptorMo6347getUnsubstitutedPrimaryConstructor.getValueParameters();
            Intrinsics.checkNotNullExpressionValue(valueParameters, "getValueParameters(...)");
            renderValueParameters(valueParameters, classConstructorDescriptorMo6347getUnsubstitutedPrimaryConstructor.hasSynthesizedParameterNames(), builder);
        }
        renderSuperTypes(klass, builder);
        renderWhereSuffix(declaredTypeParameters, builder);
    }

    private final void renderSuperTypes(ClassDescriptor klass, StringBuilder builder) {
        if (getWithoutSuperTypes() || KotlinBuiltIns.isNothing(klass.getDefaultType())) {
            return;
        }
        Collection<KotlinType> collectionMo7438getSupertypes = klass.getTypeConstructor().mo7438getSupertypes();
        Intrinsics.checkNotNullExpressionValue(collectionMo7438getSupertypes, "getSupertypes(...)");
        if (collectionMo7438getSupertypes.isEmpty()) {
            return;
        }
        if (collectionMo7438getSupertypes.size() == 1 && KotlinBuiltIns.isAnyOrNullableAny(collectionMo7438getSupertypes.iterator().next())) {
            return;
        }
        renderSpaceIfNeeded(builder);
        builder.append(": ");
        CollectionsKt.joinTo(collectionMo7438getSupertypes, builder, (112 & 2) != 0 ? ", " : ", ", (112 & 4) != 0 ? "" : null, (112 & 8) != 0 ? "" : null, (112 & 16) != 0 ? -1 : 0, (112 & 32) != 0 ? "..." : null, (112 & 64) != 0 ? null : new Function1<KotlinType, CharSequence>() { // from class: org.jetbrains.kotlin.renderer.DescriptorRendererImpl.renderSuperTypes.1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final CharSequence invoke(KotlinType kotlinType) {
                DescriptorRendererImpl descriptorRendererImpl = DescriptorRendererImpl.this;
                Intrinsics.checkNotNull(kotlinType);
                return descriptorRendererImpl.renderType(kotlinType);
            }
        });
    }

    private final void renderClassKindPrefix(ClassDescriptor klass, StringBuilder builder) {
        builder.append(renderKeyword(DescriptorRenderer.INSTANCE.getClassifierKindPrefix(klass)));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void renderPackageView(PackageViewDescriptor packageView, StringBuilder builder) {
        renderPackageHeader(packageView.getFqName(), "package", builder);
        if (getDebugMode()) {
            builder.append(" in context of ");
            renderName(packageView.getModuleDescriptor(), builder, false);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void renderPackageFragment(PackageFragmentDescriptor fragment, StringBuilder builder) {
        renderPackageHeader(fragment.getFqName(), "package-fragment", builder);
        if (getDebugMode()) {
            builder.append(" in ");
            renderName(fragment.getContainingModule(), builder, false);
        }
    }

    private final void renderPackageHeader(FqName fqName, String fragmentOrView, StringBuilder builder) {
        builder.append(renderKeyword(fragmentOrView));
        FqNameUnsafe unsafe = fqName.toUnsafe();
        Intrinsics.checkNotNullExpressionValue(unsafe, "toUnsafe(...)");
        String strRenderFqName = renderFqName(unsafe);
        if (strRenderFqName.length() > 0) {
            builder.append(" ");
            builder.append(strRenderFqName);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void renderAccessorModifiers(PropertyAccessorDescriptor descriptor, StringBuilder builder) {
        renderMemberModifiers(descriptor, builder);
    }

    /* JADX INFO: compiled from: DescriptorRendererImpl.kt */
    @Metadata(d1 = {"\u0000\u0082\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0082\u0004\u0018\u00002\u0012\u0012\u0004\u0012\u00020\u0002\u0012\b\u0012\u00060\u0003j\u0002`\u00040\u0001B\u0005¢\u0006\u0002\u0010\u0005J\u001c\u0010\u0006\u001a\u00020\u00022\u0006\u0010\u0007\u001a\u00020\b2\n\u0010\t\u001a\u00060\u0003j\u0002`\u0004H\u0016J\u001c\u0010\n\u001a\u00020\u00022\u0006\u0010\u000b\u001a\u00020\f2\n\u0010\t\u001a\u00060\u0003j\u0002`\u0004H\u0016J\u001c\u0010\r\u001a\u00020\u00022\u0006\u0010\u0007\u001a\u00020\u000e2\n\u0010\t\u001a\u00060\u0003j\u0002`\u0004H\u0016J\u001c\u0010\u000f\u001a\u00020\u00022\u0006\u0010\u0007\u001a\u00020\u00102\n\u0010\t\u001a\u00060\u0003j\u0002`\u0004H\u0016J\u001c\u0010\u0011\u001a\u00020\u00022\u0006\u0010\u0007\u001a\u00020\u00122\n\u0010\t\u001a\u00060\u0003j\u0002`\u0004H\u0016J\u001c\u0010\u0013\u001a\u00020\u00022\u0006\u0010\u0007\u001a\u00020\u00142\n\u0010\t\u001a\u00060\u0003j\u0002`\u0004H\u0016J$\u0010\u0015\u001a\u00020\u00022\u0006\u0010\u0007\u001a\u00020\u00162\n\u0010\t\u001a\u00060\u0003j\u0002`\u00042\u0006\u0010\u0017\u001a\u00020\u0018H\u0002J\u001c\u0010\u0019\u001a\u00020\u00022\u0006\u0010\u0007\u001a\u00020\u001a2\n\u0010\t\u001a\u00060\u0003j\u0002`\u0004H\u0016J\u001c\u0010\u001b\u001a\u00020\u00022\u0006\u0010\u0007\u001a\u00020\u001c2\n\u0010\t\u001a\u00060\u0003j\u0002`\u0004H\u0016J\u001c\u0010\u001d\u001a\u00020\u00022\u0006\u0010\u0007\u001a\u00020\u001e2\n\u0010\t\u001a\u00060\u0003j\u0002`\u0004H\u0016J\u001c\u0010\u001f\u001a\u00020\u00022\u0006\u0010\u0007\u001a\u00020 2\n\u0010\t\u001a\u00060\u0003j\u0002`\u0004H\u0016J\u001c\u0010!\u001a\u00020\u00022\u0006\u0010\"\u001a\u00020#2\n\u0010\t\u001a\u00060\u0003j\u0002`\u0004H\u0016J\u001c\u0010$\u001a\u00020\u00022\u0006\u0010\u0007\u001a\u00020%2\n\u0010\t\u001a\u00060\u0003j\u0002`\u0004H\u0016J\u001c\u0010&\u001a\u00020\u00022\u0006\u0010\u0007\u001a\u00020'2\n\u0010\t\u001a\u00060\u0003j\u0002`\u0004H\u0016J\u001c\u0010(\u001a\u00020\u00022\u0006\u0010\u0007\u001a\u00020)2\n\u0010\t\u001a\u00060\u0003j\u0002`\u0004H\u0016J\u001c\u0010*\u001a\u00020\u00022\u0006\u0010\u0007\u001a\u00020+2\n\u0010\t\u001a\u00060\u0003j\u0002`\u0004H\u0016¨\u0006,"}, d2 = {"Lorg/jetbrains/kotlin/renderer/DescriptorRendererImpl$RenderDeclarationDescriptorVisitor;", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptorVisitor;", "", "Ljava/lang/StringBuilder;", "Lkotlin/text/StringBuilder;", "(Lorg/jetbrains/kotlin/renderer/DescriptorRendererImpl;)V", "visitClassDescriptor", "descriptor", "Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;", "builder", "visitConstructorDescriptor", "constructorDescriptor", "Lorg/jetbrains/kotlin/descriptors/ConstructorDescriptor;", "visitFunctionDescriptor", "Lorg/jetbrains/kotlin/descriptors/FunctionDescriptor;", "visitModuleDeclaration", "Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;", "visitPackageFragmentDescriptor", "Lorg/jetbrains/kotlin/descriptors/PackageFragmentDescriptor;", "visitPackageViewDescriptor", "Lorg/jetbrains/kotlin/descriptors/PackageViewDescriptor;", "visitPropertyAccessorDescriptor", "Lorg/jetbrains/kotlin/descriptors/PropertyAccessorDescriptor;", Namer.METADATA_CLASS_KIND, "", "visitPropertyDescriptor", "Lorg/jetbrains/kotlin/descriptors/PropertyDescriptor;", "visitPropertyGetterDescriptor", "Lorg/jetbrains/kotlin/descriptors/PropertyGetterDescriptor;", "visitPropertySetterDescriptor", "Lorg/jetbrains/kotlin/descriptors/PropertySetterDescriptor;", "visitReceiverParameterDescriptor", "Lorg/jetbrains/kotlin/descriptors/ReceiverParameterDescriptor;", "visitScriptDescriptor", "scriptDescriptor", "Lorg/jetbrains/kotlin/descriptors/ScriptDescriptor;", "visitTypeAliasDescriptor", "Lorg/jetbrains/kotlin/descriptors/TypeAliasDescriptor;", "visitTypeParameterDescriptor", "Lorg/jetbrains/kotlin/descriptors/TypeParameterDescriptor;", "visitValueParameterDescriptor", "Lorg/jetbrains/kotlin/descriptors/ValueParameterDescriptor;", "visitVariableDescriptor", "Lorg/jetbrains/kotlin/descriptors/VariableDescriptor;", "descriptors"}, k = 1, mv = {1, 9, 0}, xi = 48)
    private final class RenderDeclarationDescriptorVisitor implements DeclarationDescriptorVisitor<Unit, StringBuilder> {

        /* JADX INFO: compiled from: DescriptorRendererImpl.kt */
        @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
        public /* synthetic */ class WhenMappings {
            public static final /* synthetic */ int[] $EnumSwitchMapping$0;

            static {
                int[] iArr = new int[PropertyAccessorRenderingPolicy.values().length];
                try {
                    iArr[PropertyAccessorRenderingPolicy.PRETTY.ordinal()] = 1;
                } catch (NoSuchFieldError unused) {
                }
                try {
                    iArr[PropertyAccessorRenderingPolicy.DEBUG.ordinal()] = 2;
                } catch (NoSuchFieldError unused2) {
                }
                try {
                    iArr[PropertyAccessorRenderingPolicy.NONE.ordinal()] = 3;
                } catch (NoSuchFieldError unused3) {
                }
                $EnumSwitchMapping$0 = iArr;
            }
        }

        public RenderDeclarationDescriptorVisitor() {
        }

        @Override // org.jetbrains.kotlin.descriptors.DeclarationDescriptorVisitor
        public /* bridge */ /* synthetic */ Unit visitClassDescriptor(ClassDescriptor classDescriptor, StringBuilder sb) {
            visitClassDescriptor2(classDescriptor, sb);
            return Unit.INSTANCE;
        }

        @Override // org.jetbrains.kotlin.descriptors.DeclarationDescriptorVisitor
        public /* bridge */ /* synthetic */ Unit visitConstructorDescriptor(ConstructorDescriptor constructorDescriptor, StringBuilder sb) {
            visitConstructorDescriptor2(constructorDescriptor, sb);
            return Unit.INSTANCE;
        }

        @Override // org.jetbrains.kotlin.descriptors.DeclarationDescriptorVisitor
        public /* bridge */ /* synthetic */ Unit visitFunctionDescriptor(FunctionDescriptor functionDescriptor, StringBuilder sb) {
            visitFunctionDescriptor2(functionDescriptor, sb);
            return Unit.INSTANCE;
        }

        @Override // org.jetbrains.kotlin.descriptors.DeclarationDescriptorVisitor
        public /* bridge */ /* synthetic */ Unit visitModuleDeclaration(ModuleDescriptor moduleDescriptor, StringBuilder sb) {
            visitModuleDeclaration2(moduleDescriptor, sb);
            return Unit.INSTANCE;
        }

        @Override // org.jetbrains.kotlin.descriptors.DeclarationDescriptorVisitor
        public /* bridge */ /* synthetic */ Unit visitPackageFragmentDescriptor(PackageFragmentDescriptor packageFragmentDescriptor, StringBuilder sb) {
            visitPackageFragmentDescriptor2(packageFragmentDescriptor, sb);
            return Unit.INSTANCE;
        }

        @Override // org.jetbrains.kotlin.descriptors.DeclarationDescriptorVisitor
        public /* bridge */ /* synthetic */ Unit visitPackageViewDescriptor(PackageViewDescriptor packageViewDescriptor, StringBuilder sb) {
            visitPackageViewDescriptor2(packageViewDescriptor, sb);
            return Unit.INSTANCE;
        }

        @Override // org.jetbrains.kotlin.descriptors.DeclarationDescriptorVisitor
        public /* bridge */ /* synthetic */ Unit visitPropertyDescriptor(PropertyDescriptor propertyDescriptor, StringBuilder sb) {
            visitPropertyDescriptor2(propertyDescriptor, sb);
            return Unit.INSTANCE;
        }

        @Override // org.jetbrains.kotlin.descriptors.DeclarationDescriptorVisitor
        public /* bridge */ /* synthetic */ Unit visitPropertyGetterDescriptor(PropertyGetterDescriptor propertyGetterDescriptor, StringBuilder sb) {
            visitPropertyGetterDescriptor2(propertyGetterDescriptor, sb);
            return Unit.INSTANCE;
        }

        @Override // org.jetbrains.kotlin.descriptors.DeclarationDescriptorVisitor
        public /* bridge */ /* synthetic */ Unit visitPropertySetterDescriptor(PropertySetterDescriptor propertySetterDescriptor, StringBuilder sb) {
            visitPropertySetterDescriptor2(propertySetterDescriptor, sb);
            return Unit.INSTANCE;
        }

        @Override // org.jetbrains.kotlin.descriptors.DeclarationDescriptorVisitor
        public /* bridge */ /* synthetic */ Unit visitReceiverParameterDescriptor(ReceiverParameterDescriptor receiverParameterDescriptor, StringBuilder sb) {
            visitReceiverParameterDescriptor2(receiverParameterDescriptor, sb);
            return Unit.INSTANCE;
        }

        @Override // org.jetbrains.kotlin.descriptors.DeclarationDescriptorVisitor
        public /* bridge */ /* synthetic */ Unit visitScriptDescriptor(ScriptDescriptor scriptDescriptor, StringBuilder sb) {
            visitScriptDescriptor2(scriptDescriptor, sb);
            return Unit.INSTANCE;
        }

        @Override // org.jetbrains.kotlin.descriptors.DeclarationDescriptorVisitor
        public /* bridge */ /* synthetic */ Unit visitTypeAliasDescriptor(TypeAliasDescriptor typeAliasDescriptor, StringBuilder sb) {
            visitTypeAliasDescriptor2(typeAliasDescriptor, sb);
            return Unit.INSTANCE;
        }

        @Override // org.jetbrains.kotlin.descriptors.DeclarationDescriptorVisitor
        public /* bridge */ /* synthetic */ Unit visitTypeParameterDescriptor(TypeParameterDescriptor typeParameterDescriptor, StringBuilder sb) {
            visitTypeParameterDescriptor2(typeParameterDescriptor, sb);
            return Unit.INSTANCE;
        }

        @Override // org.jetbrains.kotlin.descriptors.DeclarationDescriptorVisitor
        public /* bridge */ /* synthetic */ Unit visitValueParameterDescriptor(ValueParameterDescriptor valueParameterDescriptor, StringBuilder sb) {
            visitValueParameterDescriptor2(valueParameterDescriptor, sb);
            return Unit.INSTANCE;
        }

        @Override // org.jetbrains.kotlin.descriptors.DeclarationDescriptorVisitor
        public /* bridge */ /* synthetic */ Unit visitVariableDescriptor(VariableDescriptor variableDescriptor, StringBuilder sb) {
            visitVariableDescriptor2(variableDescriptor, sb);
            return Unit.INSTANCE;
        }

        /* JADX INFO: renamed from: visitValueParameterDescriptor, reason: avoid collision after fix types in other method */
        public void visitValueParameterDescriptor2(ValueParameterDescriptor descriptor, StringBuilder builder) {
            Intrinsics.checkNotNullParameter(descriptor, "descriptor");
            Intrinsics.checkNotNullParameter(builder, "builder");
            DescriptorRendererImpl.this.renderValueParameter(descriptor, true, builder, true);
        }

        /* JADX INFO: renamed from: visitVariableDescriptor, reason: avoid collision after fix types in other method */
        public void visitVariableDescriptor2(VariableDescriptor descriptor, StringBuilder builder) {
            Intrinsics.checkNotNullParameter(descriptor, "descriptor");
            Intrinsics.checkNotNullParameter(builder, "builder");
            DescriptorRendererImpl.renderVariable$default(DescriptorRendererImpl.this, descriptor, true, builder, true, false, 16, null);
        }

        /* JADX INFO: renamed from: visitPropertyDescriptor, reason: avoid collision after fix types in other method */
        public void visitPropertyDescriptor2(PropertyDescriptor descriptor, StringBuilder builder) {
            Intrinsics.checkNotNullParameter(descriptor, "descriptor");
            Intrinsics.checkNotNullParameter(builder, "builder");
            DescriptorRendererImpl.this.renderProperty(descriptor, builder);
        }

        /* JADX INFO: renamed from: visitPropertyGetterDescriptor, reason: avoid collision after fix types in other method */
        public void visitPropertyGetterDescriptor2(PropertyGetterDescriptor descriptor, StringBuilder builder) {
            Intrinsics.checkNotNullParameter(descriptor, "descriptor");
            Intrinsics.checkNotNullParameter(builder, "builder");
            visitPropertyAccessorDescriptor(descriptor, builder, "getter");
        }

        /* JADX INFO: renamed from: visitPropertySetterDescriptor, reason: avoid collision after fix types in other method */
        public void visitPropertySetterDescriptor2(PropertySetterDescriptor descriptor, StringBuilder builder) {
            Intrinsics.checkNotNullParameter(descriptor, "descriptor");
            Intrinsics.checkNotNullParameter(builder, "builder");
            visitPropertyAccessorDescriptor(descriptor, builder, "setter");
        }

        private final void visitPropertyAccessorDescriptor(PropertyAccessorDescriptor descriptor, StringBuilder builder, String kind) {
            int i = WhenMappings.$EnumSwitchMapping$0[DescriptorRendererImpl.this.getPropertyAccessorRenderingPolicy().ordinal()];
            if (i != 1) {
                if (i != 2) {
                    return;
                }
                visitFunctionDescriptor2((FunctionDescriptor) descriptor, builder);
            } else {
                DescriptorRendererImpl.this.renderAccessorModifiers(descriptor, builder);
                builder.append(kind + " for ");
                DescriptorRendererImpl descriptorRendererImpl = DescriptorRendererImpl.this;
                PropertyDescriptor correspondingProperty = descriptor.getCorrespondingProperty();
                Intrinsics.checkNotNullExpressionValue(correspondingProperty, "getCorrespondingProperty(...)");
                descriptorRendererImpl.renderProperty(correspondingProperty, builder);
            }
        }

        /* JADX INFO: renamed from: visitFunctionDescriptor, reason: avoid collision after fix types in other method */
        public void visitFunctionDescriptor2(FunctionDescriptor descriptor, StringBuilder builder) {
            Intrinsics.checkNotNullParameter(descriptor, "descriptor");
            Intrinsics.checkNotNullParameter(builder, "builder");
            DescriptorRendererImpl.this.renderFunction(descriptor, builder);
        }

        /* JADX INFO: renamed from: visitReceiverParameterDescriptor, reason: avoid collision after fix types in other method */
        public void visitReceiverParameterDescriptor2(ReceiverParameterDescriptor descriptor, StringBuilder builder) {
            Intrinsics.checkNotNullParameter(descriptor, "descriptor");
            Intrinsics.checkNotNullParameter(builder, "builder");
            builder.append(descriptor.getShortName());
        }

        /* JADX INFO: renamed from: visitConstructorDescriptor, reason: avoid collision after fix types in other method */
        public void visitConstructorDescriptor2(ConstructorDescriptor constructorDescriptor, StringBuilder builder) {
            Intrinsics.checkNotNullParameter(constructorDescriptor, "constructorDescriptor");
            Intrinsics.checkNotNullParameter(builder, "builder");
            DescriptorRendererImpl.this.renderConstructor(constructorDescriptor, builder);
        }

        /* JADX INFO: renamed from: visitTypeParameterDescriptor, reason: avoid collision after fix types in other method */
        public void visitTypeParameterDescriptor2(TypeParameterDescriptor descriptor, StringBuilder builder) {
            Intrinsics.checkNotNullParameter(descriptor, "descriptor");
            Intrinsics.checkNotNullParameter(builder, "builder");
            DescriptorRendererImpl.this.renderTypeParameter(descriptor, builder, true);
        }

        /* JADX INFO: renamed from: visitPackageFragmentDescriptor, reason: avoid collision after fix types in other method */
        public void visitPackageFragmentDescriptor2(PackageFragmentDescriptor descriptor, StringBuilder builder) {
            Intrinsics.checkNotNullParameter(descriptor, "descriptor");
            Intrinsics.checkNotNullParameter(builder, "builder");
            DescriptorRendererImpl.this.renderPackageFragment(descriptor, builder);
        }

        /* JADX INFO: renamed from: visitPackageViewDescriptor, reason: avoid collision after fix types in other method */
        public void visitPackageViewDescriptor2(PackageViewDescriptor descriptor, StringBuilder builder) {
            Intrinsics.checkNotNullParameter(descriptor, "descriptor");
            Intrinsics.checkNotNullParameter(builder, "builder");
            DescriptorRendererImpl.this.renderPackageView(descriptor, builder);
        }

        /* JADX INFO: renamed from: visitModuleDeclaration, reason: avoid collision after fix types in other method */
        public void visitModuleDeclaration2(ModuleDescriptor descriptor, StringBuilder builder) {
            Intrinsics.checkNotNullParameter(descriptor, "descriptor");
            Intrinsics.checkNotNullParameter(builder, "builder");
            DescriptorRendererImpl.this.renderName(descriptor, builder, true);
        }

        /* JADX INFO: renamed from: visitScriptDescriptor, reason: avoid collision after fix types in other method */
        public void visitScriptDescriptor2(ScriptDescriptor scriptDescriptor, StringBuilder builder) {
            Intrinsics.checkNotNullParameter(scriptDescriptor, "scriptDescriptor");
            Intrinsics.checkNotNullParameter(builder, "builder");
            visitClassDescriptor2((ClassDescriptor) scriptDescriptor, builder);
        }

        /* JADX INFO: renamed from: visitClassDescriptor, reason: avoid collision after fix types in other method */
        public void visitClassDescriptor2(ClassDescriptor descriptor, StringBuilder builder) {
            Intrinsics.checkNotNullParameter(descriptor, "descriptor");
            Intrinsics.checkNotNullParameter(builder, "builder");
            DescriptorRendererImpl.this.renderClass(descriptor, builder);
        }

        /* JADX INFO: renamed from: visitTypeAliasDescriptor, reason: avoid collision after fix types in other method */
        public void visitTypeAliasDescriptor2(TypeAliasDescriptor descriptor, StringBuilder builder) {
            Intrinsics.checkNotNullParameter(descriptor, "descriptor");
            Intrinsics.checkNotNullParameter(builder, "builder");
            DescriptorRendererImpl.this.renderTypeAlias(descriptor, builder);
        }
    }

    private final void renderSpaceIfNeeded(StringBuilder builder) {
        int length = builder.length();
        if (length == 0 || builder.charAt(length - 1) != ' ') {
            builder.append(StringUtil.SPACE);
        }
    }

    private final boolean overridesSomething(CallableMemberDescriptor callable) {
        return !callable.getOverriddenDescriptors().isEmpty();
    }
}
