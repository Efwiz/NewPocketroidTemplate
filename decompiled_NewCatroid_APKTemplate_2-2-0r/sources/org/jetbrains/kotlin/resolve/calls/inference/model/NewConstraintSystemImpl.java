package org.jetbrains.kotlin.resolve.calls.inference.model;

import androidx.core.app.NotificationCompat;
import com.reandroid.arsc.container.SpecTypePair;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.Predicate;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function4;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import kotlin.reflect.KAnnotatedElement;
import kotlin.reflect.KFunction;
import org.apache.xerces.impl.xs.SchemaSymbols;
import org.jetbrains.kotlin.builtins.functions.FunctionTypeKind;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.config.LanguageFeature;
import org.jetbrains.kotlin.config.LanguageVersionSettings;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.load.java.JvmAbi;
import org.jetbrains.kotlin.load.java.JvmAnnotationNames;
import org.jetbrains.kotlin.resolve.calls.components.PostponedArgumentsAnalyzerContext;
import org.jetbrains.kotlin.resolve.calls.inference.ConstraintSystemBuilder;
import org.jetbrains.kotlin.resolve.calls.inference.ConstraintSystemTransaction;
import org.jetbrains.kotlin.resolve.calls.inference.InferenceUtilsKt;
import org.jetbrains.kotlin.resolve.calls.inference.NewConstraintSystem;
import org.jetbrains.kotlin.resolve.calls.inference.components.ConstraintInjector;
import org.jetbrains.kotlin.resolve.calls.inference.components.ConstraintSystemCompletionContext;
import org.jetbrains.kotlin.resolve.calls.inference.components.ConstraintSystemUtilContext;
import org.jetbrains.kotlin.resolve.calls.inference.components.ResultTypeResolver;
import org.jetbrains.kotlin.resolve.calls.inference.components.VariableFixationFinderKt;
import org.jetbrains.kotlin.resolve.checkers.EmptyIntersectionTypeInfo;
import org.jetbrains.kotlin.types.AbstractTypeApproximator;
import org.jetbrains.kotlin.types.AbstractTypeChecker;
import org.jetbrains.kotlin.types.TypeApproximatorConfiguration;
import org.jetbrains.kotlin.types.TypeCheckerState;
import org.jetbrains.kotlin.types.model.AnnotationMarker;
import org.jetbrains.kotlin.types.model.CaptureStatus;
import org.jetbrains.kotlin.types.model.CapturedTypeConstructorMarker;
import org.jetbrains.kotlin.types.model.CapturedTypeMarker;
import org.jetbrains.kotlin.types.model.DefinitelyNotNullTypeMarker;
import org.jetbrains.kotlin.types.model.DynamicTypeMarker;
import org.jetbrains.kotlin.types.model.FlexibleTypeMarker;
import org.jetbrains.kotlin.types.model.IntersectionTypeConstructorMarker;
import org.jetbrains.kotlin.types.model.KotlinTypeMarker;
import org.jetbrains.kotlin.types.model.ObsoleteTypeKind;
import org.jetbrains.kotlin.types.model.SimpleTypeMarker;
import org.jetbrains.kotlin.types.model.StubTypeMarker;
import org.jetbrains.kotlin.types.model.TypeArgumentListMarker;
import org.jetbrains.kotlin.types.model.TypeArgumentMarker;
import org.jetbrains.kotlin.types.model.TypeConstructorMarker;
import org.jetbrains.kotlin.types.model.TypeParameterMarker;
import org.jetbrains.kotlin.types.model.TypeSubstitutorMarker;
import org.jetbrains.kotlin.types.model.TypeSystemInferenceExtensionContext;
import org.jetbrains.kotlin.types.model.TypeVariableMarker;
import org.jetbrains.kotlin.types.model.TypeVariableTypeConstructorMarker;
import org.jetbrains.kotlin.types.model.TypeVariance;
import org.jetbrains.kotlin.utils.SmartList;
import org.jetbrains.kotlin.utils.SmartSet;
import org.jetbrains.kotlin.utils.addToStdlib.AddToStdlibKt;

/* JADX INFO: compiled from: NewConstraintSystemImpl.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u009c\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010%\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010#\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0010\u0011\n\u0002\b\u000e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000e\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\r\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\bZ\n\u0002\u0010(\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u000f\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\r\u0018\u00002\u00020\u00012\u00020\u00022\u00020\u00032\u00020\u00042\u00020\u00052\u00020\u00062\u00020\u0007:\u0004õ\u0002ö\u0002B\u001d\u0012\u0006\u0010\b\u001a\u00020\t\u0012\u0006\u0010\n\u001a\u00020\u0002\u0012\u0006\u0010\u000b\u001a\u00020\f¢\u0006\u0002\u0010\rJ \u0010S\u001a\u00020A2\u0006\u0010T\u001a\u00020+2\u0006\u0010U\u001a\u00020+2\u0006\u0010V\u001a\u00020WH\u0016J\u0010\u0010X\u001a\u00020A2\u0006\u0010Y\u001a\u00020'H\u0016J\u0010\u0010Z\u001a\u00020A2\u0006\u0010[\u001a\u00020\\H\u0016J*\u0010]\u001a\u00020A2\u0006\u0010V\u001a\u00020\u001d2\u0018\u0010^\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u0011\u0012\u0004\u0012\u00020 0\u001c0\u001bH\u0016J\u0010\u0010_\u001a\u00020A2\u0006\u0010`\u001a\u00020aH\u0016J \u0010b\u001a\u00020A2\u0006\u0010c\u001a\u00020+2\u0006\u0010d\u001a\u00020+2\u0006\u0010V\u001a\u00020WH\u0016J\t\u0010e\u001a\u00020fH\u0096\u0001J8\u0010g\u001a\u00020\u00152&\u0010h\u001a\"\u0012\u001a\u0012\u0018\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u0011\u0012\u0004\u0012\u00020 0\u001c0\u001fj\u0002`!0\u001ej\u0002`\"2\u0006\u0010V\u001a\u00020\u001dH\u0002J\u0019\u0010i\u001a\u00020\u00152\u0006\u0010j\u001a\u00020\u00102\u0006\u0010k\u001a\u00020\u0010H\u0096\u0001J\b\u0010l\u001a\u00020\u0000H\u0016J\b\u0010m\u001a\u00020\u0000H\u0016J\b\u0010n\u001a\u00020aH\u0016J\u0014\u0010o\u001a\u000e\u0012\u0004\u0012\u00020\u0011\u0012\u0004\u0012\u00020p0\u000fH\u0016J\b\u0010q\u001a\u00020rH\u0016J\u001c\u0010q\u001a\u00020r2\u0012\u0010s\u001a\u000e\u0012\u0004\u0012\u00020\u0010\u0012\u0004\u0012\u00020p0\u000fH\u0016J\b\u0010t\u001a\u00020rH\u0016J\u0010\u0010u\u001a\u00020\u00152\u0006\u0010v\u001a\u00020+H\u0016J\u001b\u0010w\u001a\u0004\u0018\u00010f2\u0006\u0010v\u001a\u00020f2\u0006\u0010x\u001a\u00020yH\u0096\u0001J\u0013\u0010z\u001a\u0004\u0018\u00010+2\u0006\u0010v\u001a\u00020+H\u0096\u0001J\b\u0010{\u001a\u0004\u0018\u00010'J\u0018\u0010|\u001a\u00020A2\u0006\u0010}\u001a\u00020\u00112\u0006\u0010~\u001a\u00020+H\u0002J\b\u0010\u007f\u001a\u00020AH\u0002J\u001a\u0010\u0080\u0001\u001a\u00020A2\u0007\u0010\u0081\u0001\u001a\u00020;2\u0006\u0010~\u001a\u00020+H\u0002J%\u0010\u0082\u0001\u001a\u00020A2\u0014\u0010\u0083\u0001\u001a\u000b\u0012\u0006\b\u0001\u0012\u00020F0\u0084\u0001\"\u00020FH\u0002¢\u0006\u0003\u0010\u0085\u0001J\u0011\u0010\u0082\u0001\u001a\u00020A2\u0006\u0010T\u001a\u00020FH\u0002J\u0019\u0010\u0082\u0001\u001a\u00020A2\u0006\u0010T\u001a\u00020F2\u0006\u0010U\u001a\u00020FH\u0002J\"\u0010\u0082\u0001\u001a\u00020A2\u0006\u0010T\u001a\u00020F2\u0006\u0010U\u001a\u00020F2\u0007\u0010\u0086\u0001\u001a\u00020FH\u0002J+\u0010\u0082\u0001\u001a\u00020A2\u0006\u0010T\u001a\u00020F2\u0006\u0010U\u001a\u00020F2\u0007\u0010\u0086\u0001\u001a\u00020F2\u0007\u0010\u0087\u0001\u001a\u00020FH\u0002J\u001b\u0010\u0088\u0001\u001a\u00020A2\u0007\u0010\u0089\u0001\u001a\u00020F2\u0007\u0010\u008a\u0001\u001a\u000204H\u0002J\u001b\u0010\u008b\u0001\u001a\u0004\u0018\u0001012\r\u0010\u008c\u0001\u001a\b\u0012\u0004\u0012\u00020+00H\u0096\u0001J\u0011\u0010\u008d\u0001\u001a\u00020\u00152\u0006\u0010v\u001a\u00020+H\u0016J\u0011\u0010\u008e\u0001\u001a\u00020\u00152\u0006\u0010v\u001a\u00020+H\u0016J\b\u0010%\u001a\u00020\u0015H\u0016J$\u0010\u008f\u0001\u001a\u0004\u0018\u00010f2\u0007\u0010\u0090\u0001\u001a\u00020P2\r\u0010\u0091\u0001\u001a\b\u0012\u0004\u0012\u00020+0\u001eH\u0096\u0001J7\u0010\u0092\u0001\u001a\u00030\u0093\u00012\b\u0010\u0094\u0001\u001a\u00030\u0095\u00012\r\u0010\u0096\u0001\u001a\b\u0012\u0004\u0012\u00020+0\u001e2\b\u0010c\u001a\u0004\u0018\u00010+2\u0007\u0010\u0097\u0001\u001a\u00020yH\u0096\u0001J\n\u0010\u0098\u0001\u001a\u00020rH\u0096\u0001J\u0014\u0010\u0099\u0001\u001a\u00020f2\b\u0010\u009a\u0001\u001a\u00030\u009b\u0001H\u0096\u0001J\u001c\u0010\u009c\u0001\u001a\u00020+2\u0007\u0010\u009d\u0001\u001a\u00020f2\u0007\u0010\u009e\u0001\u001a\u00020fH\u0096\u0001JG\u0010\u009f\u0001\u001a\u00020f2\u0007\u0010 \u0001\u001a\u00020\u00102\u000e\u0010¡\u0001\u001a\t\u0012\u0005\u0012\u00030\u0095\u00010\u001e2\u0007\u0010¢\u0001\u001a\u00020\u00152\u0007\u0010£\u0001\u001a\u00020\u00152\u0010\u0010¤\u0001\u001a\u000b\u0012\u0005\u0012\u00030¥\u0001\u0018\u00010\u001eH\u0096\u0001J\u0013\u0010¦\u0001\u001a\u00030\u0095\u00012\u0006\u0010N\u001a\u00020OH\u0096\u0001J\u0013\u0010§\u0001\u001a\u00020p2\u0007\u0010\u0090\u0001\u001a\u00020\u0011H\u0096\u0001J\u0013\u0010¨\u0001\u001a\u00020p2\u0007\u0010\u0090\u0001\u001a\u00020\u0011H\u0096\u0001J\u0015\u0010©\u0001\u001a\u0004\u0018\u00010r2\u0007\u0010ª\u0001\u001a\u00020+H\u0096\u0001J\u001d\u0010«\u0001\u001a\u00030\u0095\u00012\u0006\u0010v\u001a\u00020+2\b\u0010¬\u0001\u001a\u00030\u00ad\u0001H\u0096\u0001J\u001c\u0010®\u0001\u001a\u00020+2\u0007\u0010¯\u0001\u001a\u00020+2\u0007\u0010°\u0001\u001a\u00020+H\u0096\u0001J\u0013\u0010±\u0001\u001a\u00020+2\u0007\u0010 \u0001\u001a\u00020\u0010H\u0096\u0001J\t\u0010²\u0001\u001a\u00020aH\u0016J\t\u0010³\u0001\u001a\u00020AH\u0002J\u001b\u0010´\u0001\u001a\u0004\u0018\u00010f2\r\u0010µ\u0001\u001a\b\u0012\u0004\u0012\u00020f0\u001eH\u0096\u0001J&\u0010¶\u0001\u001a\u00020A2\u0006\u0010}\u001a\u00020\u00112\u0006\u0010~\u001a\u00020+2\u000b\u0010V\u001a\u0007\u0012\u0002\b\u00030·\u0001H\u0016J\t\u0010¸\u0001\u001a\u00020\u0000H\u0016J\u0014\u0010¹\u0001\u001a\u0004\u0018\u00010+2\u0007\u0010º\u0001\u001a\u00020\u0010H\u0016J/\u0010¹\u0001\u001a\u0004\u0018\u00010+2\u0007\u0010»\u0001\u001a\u00020\u00102\u0019\u0010¼\u0001\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u0010\u0012\u0004\u0012\u0002040\u001c0\u001eH\u0016J\u001a\u0010½\u0001\u001a\u0004\u0018\u0001012\r\u0010\u008c\u0001\u001a\b\u0012\u0004\u0012\u00020+00H\u0016J\u001d\u0010¾\u0001\u001a\u00020\u00102\u0007\u0010¿\u0001\u001a\u0002042\b\u0010À\u0001\u001a\u00030Á\u0001H\u0096\u0001J\u0017\u0010Â\u0001\u001a\b\u0012\u0004\u0012\u00020\u00100\u001e2\u0006\u0010v\u001a\u00020+H\u0016J\u001d\u0010Ã\u0001\u001a\u00020\u00102\u0007\u0010¿\u0001\u001a\u0002042\b\u0010À\u0001\u001a\u00030Á\u0001H\u0096\u0001J\u0011\u0010Ä\u0001\u001a\u00020\u00152\u0006\u0010v\u001a\u00020+H\u0016J\u001a\u0010Å\u0001\u001a\u00020\u00152\u0006\u0010T\u001a\u00020f2\u0006\u0010U\u001a\u00020fH\u0096\u0001J\u0019\u0010Æ\u0001\u001a\u00020+2\r\u0010\u008c\u0001\u001a\b\u0012\u0004\u0012\u00020+0\u001eH\u0096\u0001J\u0019\u0010Æ\u0001\u001a\u00020f2\r\u0010\u008c\u0001\u001a\b\u0012\u0004\u0012\u00020f0\u001eH\u0096\u0001J\u0012\u0010Ç\u0001\u001a\u00020\u00152\u0007\u0010\u0090\u0001\u001a\u00020\u0011H\u0016J\u0011\u0010È\u0001\u001a\u00020\u00152\u0006\u0010v\u001a\u00020+H\u0016J\u0011\u0010É\u0001\u001a\u00020\u00152\u0006\u0010v\u001a\u00020+H\u0002J\u0011\u0010Ê\u0001\u001a\u00020\u00152\u0006\u0010}\u001a\u00020\u0011H\u0016J\u0011\u0010Ë\u0001\u001a\u00020\u00152\u0006\u0010v\u001a\u00020+H\u0016J\t\u0010Ì\u0001\u001a\u00020AH\u0016J\u0011\u0010Í\u0001\u001a\u00020A2\u0006\u0010}\u001a\u00020\u0011H\u0016J\u001d\u0010Î\u0001\u001a\u00030Ï\u00012\u0007\u0010Ð\u0001\u001a\u00020\u00152\u0007\u0010Ñ\u0001\u001a\u00020\u0015H\u0096\u0001J\n\u0010Ò\u0001\u001a\u00020fH\u0096\u0001J\n\u0010Ó\u0001\u001a\u00020fH\u0096\u0001J\n\u0010Ô\u0001\u001a\u00020fH\u0096\u0001J\n\u0010Õ\u0001\u001a\u00030Ö\u0001H\u0016J6\u0010×\u0001\u001a\u00020A2\u0007\u0010»\u0001\u001a\u00020\u00102\u0019\u0010¼\u0001\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u0010\u0012\u0004\u0012\u0002040\u001c0\u001e2\u0007\u0010Ø\u0001\u001a\u00020+H\u0016J\u001b\u0010×\u0001\u001a\u00020A2\u0007\u0010º\u0001\u001a\u00020\u00102\u0007\u0010Ø\u0001\u001a\u00020+H\u0016J\u0011\u0010Ù\u0001\u001a\u00020A2\u0006\u0010}\u001a\u00020\u0011H\u0016J\u0017\u0010Ú\u0001\u001a\u00020A2\f\u0010B\u001a\b\u0012\u0004\u0012\u00020\u00100\u001fH\u0016J\t\u0010Û\u0001\u001a\u00020AH\u0016J\t\u0010Ü\u0001\u001a\u00020AH\u0016J\u0012\u0010Ý\u0001\u001a\u00020A2\u0007\u0010Þ\u0001\u001a\u00020rH\u0016J\t\u0010ß\u0001\u001a\u00020AH\u0002J\u0013\u0010à\u0001\u001a\u00030á\u00012\u0006\u0010v\u001a\u00020fH\u0096\u0001J\u0011\u0010â\u0001\u001a\u00020A2\u0006\u0010}\u001a\u00020\u0011H\u0002J\u001f\u0010ã\u0001\u001a\u00020r2\u0013\u0010ä\u0001\u001a\u000e\u0012\u0004\u0012\u00020\u0010\u0012\u0004\u0012\u00020+0\u000fH\u0096\u0001J \u0010å\u0001\u001a\t\u0012\u0005\u0012\u00030¥\u00010\u001e2\r\u0010\u008c\u0001\u001a\b\u0012\u0004\u0012\u00020+0\u001eH\u0096\u0001J\u0011\u0010æ\u0001\u001a\u00020A2\u0006\u0010}\u001a\u00020\u0011H\u0016J\n\u0010ç\u0001\u001a\u00020\u0015H\u0096\u0001J$\u0010è\u0001\u001a\u00020\u0015*\u00020+2\u0014\u0010é\u0001\u001a\u000f\u0012\u0004\u0012\u00020f\u0012\u0004\u0012\u00020\u00150ê\u0001H\u0096\u0001J\u000e\u0010ë\u0001\u001a\u000204*\u00020+H\u0096\u0001J\u000f\u0010ì\u0001\u001a\u00030í\u0001*\u00020fH\u0096\u0001J\u0011\u0010î\u0001\u001a\u0005\u0018\u00010\u0093\u0001*\u00020fH\u0096\u0001J\u0011\u0010ï\u0001\u001a\u0005\u0018\u00010ð\u0001*\u00020fH\u0096\u0001J\u0012\u0010ñ\u0001\u001a\u0005\u0018\u00010ò\u0001*\u00030ó\u0001H\u0096\u0001J\u0011\u0010ô\u0001\u001a\u0005\u0018\u00010ó\u0001*\u00020+H\u0096\u0001J\u0010\u0010õ\u0001\u001a\u0004\u0018\u00010f*\u00020+H\u0096\u0001J\u000f\u0010ö\u0001\u001a\u00030\u0095\u0001*\u00020+H\u0096\u0001J\u000e\u0010÷\u0001\u001a\u00020\u0015*\u00020+H\u0096\u0001J\u000f\u0010\u0097\u0001\u001a\u00020y*\u00030\u0093\u0001H\u0096\u0001J$\u0010ø\u0001\u001a\u00020\u0015*\u00020+2\u0014\u0010é\u0001\u001a\u000f\u0012\u0004\u0012\u00020+\u0012\u0004\u0012\u00020\u00150ê\u0001H\u0096\u0001J\u000e\u0010ù\u0001\u001a\u00020+*\u00020+H\u0096\u0001J\u000e\u0010ú\u0001\u001a\u00020f*\u00020\u0011H\u0096\u0001J\u000e\u0010û\u0001\u001a\u00020+*\u00020+H\u0096\u0001J\u0014\u0010ü\u0001\u001a\b\u0012\u0004\u0012\u00020+0\u001e*\u00020+H\u0096\u0001J\u0014\u0010ý\u0001\u001a\b\u0012\u0004\u0012\u00020O0\u001f*\u00020+H\u0096\u0001J\u0014\u0010þ\u0001\u001a\b\u0012\u0004\u0012\u00020P0\u001f*\u00020+H\u0096\u0001J\u001f\u0010ÿ\u0001\u001a\n\u0012\u0004\u0012\u00020f\u0018\u00010\u001e*\u00020f2\u0007\u0010 \u0001\u001a\u00020\u0010H\u0096\u0001J\u000e\u0010\u0080\u0002\u001a\u00020\u0010*\u00020\u0011H\u0096\u0001J\u0011\u0010\u0081\u0002\u001a\u0005\u0018\u00010Á\u0001*\u00020+H\u0096\u0001J\u0019\u0010\u0082\u0002\u001a\u00030\u0095\u0001*\u00030í\u00012\u0007\u0010\u0083\u0002\u001a\u000204H\u0096\u0003J\u000e\u0010\u0084\u0002\u001a\u00020+*\u00020\u0010H\u0096\u0001J\u0018\u0010\u0085\u0002\u001a\u00030\u0095\u0001*\u00020+2\u0007\u0010\u0083\u0002\u001a\u000204H\u0096\u0001J\u001a\u0010\u0086\u0002\u001a\u0005\u0018\u00010\u0095\u0001*\u00020f2\u0007\u0010\u0083\u0002\u001a\u000204H\u0096\u0001J\u0015\u0010\u0087\u0002\u001a\t\u0012\u0005\u0012\u00030\u0095\u00010\u001e*\u00020+H\u0096\u0001J\u0015\u0010\u0088\u0002\u001a\t\u0012\u0005\u0012\u00030¥\u00010\u001e*\u00020+H\u0096\u0001J\u0015\u0010\u0089\u0002\u001a\t\u0012\u0005\u0012\u00030¥\u00010\u001e*\u00020+H\u0096\u0001J\u000e\u0010\u008a\u0002\u001a\u00020+*\u00020+H\u0096\u0001J\u000e\u0010\u008b\u0002\u001a\u00020P*\u00020pH\u0096\u0001J\u0017\u0010\u008c\u0002\u001a\u00020O*\u00020\u00102\u0007\u0010\u0083\u0002\u001a\u000204H\u0096\u0001J\u0014\u0010\u008d\u0002\u001a\b\u0012\u0004\u0012\u00020O0\u001e*\u00020\u0010H\u0096\u0001J\u000f\u0010\u008e\u0002\u001a\u00020+*\u00030\u0095\u0001H\u0096\u0001J\u000e\u0010\u008f\u0002\u001a\u00020\u0010*\u00020OH\u0096\u0001J\u0010\u0010\u0090\u0002\u001a\u0004\u0018\u00010O*\u00020\u0010H\u0096\u0001J\u0017\u0010\u0091\u0002\u001a\u00020+*\u00020O2\u0007\u0010\u0083\u0002\u001a\u000204H\u0096\u0001J\u0014\u0010\u0092\u0002\u001a\b\u0012\u0004\u0012\u00020+0\u001e*\u00020OH\u0096\u0001J\u0010\u0010\u0093\u0002\u001a\u00030\u00ad\u0001*\u00030\u0095\u0001H\u0096\u0001J\u000f\u0010\u0093\u0002\u001a\u00030\u00ad\u0001*\u00020OH\u0096\u0001J\u000e\u0010\u0094\u0002\u001a\u00020\u0015*\u00020+H\u0096\u0001J\u000e\u0010\u0095\u0002\u001a\u00020\u0015*\u00020+H\u0096\u0001J\u000e\u0010\u0096\u0002\u001a\u00020\u0015*\u00020+H\u0096\u0001J\u000e\u0010\u0097\u0002\u001a\u00020\u0015*\u00020+H\u0096\u0001J\u0019\u0010\u0098\u0002\u001a\u00020\u0015*\u00020O2\t\u0010\u0099\u0002\u001a\u0004\u0018\u00010\u0010H\u0096\u0001J\u000e\u0010\u009a\u0002\u001a\u00020\u0015*\u00020\u0010H\u0096\u0001J\u000e\u0010\u009b\u0002\u001a\u00020\u0015*\u00020\u0010H\u0096\u0001J\u000e\u0010\u009c\u0002\u001a\u00020\u0015*\u00020+H\u0096\u0001J\u000e\u0010\u009d\u0002\u001a\u00020\u0015*\u00020+H\u0096\u0001J\u000e\u0010\u009e\u0002\u001a\u00020\u0015*\u00020+H\u0096\u0001J\u000e\u0010\u009f\u0002\u001a\u00020\u0015*\u00020\u0010H\u0096\u0001J\u000e\u0010 \u0002\u001a\u00020\u0015*\u00020fH\u0096\u0001J\u000e\u0010¡\u0002\u001a\u00020\u0015*\u00020\u0010H\u0096\u0001J\u000e\u0010¢\u0002\u001a\u00020\u0015*\u00020\u0010H\u0096\u0001J\u000e\u0010£\u0002\u001a\u00020\u0015*\u00020PH\u0096\u0001J\u000e\u0010¤\u0002\u001a\u00020\u0015*\u00020\u0010H\u0096\u0001J\u000e\u0010¥\u0002\u001a\u00020\u0015*\u00020+H\u0096\u0001J\u000e\u0010¦\u0002\u001a\u00020\u0015*\u00020\u0010H\u0096\u0001J\u000e\u0010§\u0002\u001a\u00020\u0015*\u00020+H\u0096\u0001J\u000e\u0010¨\u0002\u001a\u00020\u0015*\u00020+H\u0096\u0001J\u000e\u0010¨\u0002\u001a\u00020\u0015*\u00020\u0010H\u0096\u0001J\u000e\u0010£\u0001\u001a\u00020\u0015*\u00020fH\u0096\u0001J\u000e\u0010©\u0002\u001a\u00020\u0015*\u00020+H\u0096\u0001J\u000e\u0010ª\u0002\u001a\u00020\u0015*\u00020\u0010H\u0096\u0001J\u000e\u0010«\u0002\u001a\u00020\u0015*\u00020+H\u0096\u0001J\u000e\u0010¬\u0002\u001a\u00020\u0015*\u00020+H\u0096\u0001J\u000e\u0010\u00ad\u0002\u001a\u00020\u0015*\u00020+H\u0096\u0001J\u000e\u0010®\u0002\u001a\u00020\u0015*\u00020\u0010H\u0096\u0001J\u000e\u0010¯\u0002\u001a\u00020\u0015*\u00020\u0010H\u0096\u0001J\u000e\u0010°\u0002\u001a\u00020\u0015*\u00020fH\u0096\u0001J\u000e\u0010±\u0002\u001a\u00020\u0015*\u00020\u0010H\u0096\u0001J\u000e\u0010²\u0002\u001a\u00020\u0015*\u00020\u0010H\u0096\u0001J\u000e\u0010³\u0002\u001a\u00020\u0015*\u00020\u0010H\u0096\u0001J\u000e\u0010´\u0002\u001a\u00020\u0015*\u00020\u0010H\u0096\u0001J\u000e\u0010µ\u0002\u001a\u00020\u0015*\u00020+H\u0096\u0001J\u000e\u0010µ\u0002\u001a\u00020\u0015*\u00020fH\u0096\u0001J\u000e\u0010¶\u0002\u001a\u00020\u0015*\u00020+H\u0097\u0001J\u000e\u0010·\u0002\u001a\u00020\u0015*\u00020+H\u0096\u0001J\u000e\u0010¸\u0002\u001a\u00020\u0015*\u00020\u0010H\u0096\u0001J\u000e\u0010¹\u0002\u001a\u00020\u0015*\u00020+H\u0096\u0001J\u000e\u0010º\u0002\u001a\u00020\u0015*\u00020+H\u0096\u0001J\u000e\u0010»\u0002\u001a\u00020\u0015*\u00020+H\u0096\u0001J\u000f\u0010¼\u0002\u001a\u00020\u0015*\u00030\u0093\u0001H\u0096\u0001J\u000e\u0010½\u0002\u001a\u00020\u0015*\u00020fH\u0096\u0001J\u000f\u0010¾\u0002\u001a\u00020\u0015*\u00030\u0093\u0001H\u0096\u0001J\u000e\u0010¿\u0002\u001a\u00020\u0015*\u00020+H\u0096\u0001J\u000e\u0010À\u0002\u001a\u00020\u0015*\u00020+H\u0096\u0001J\u000e\u0010Á\u0002\u001a\u00020\u0015*\u00020+H\u0096\u0001J\u000e\u0010Â\u0002\u001a\u00020\u0015*\u00020fH\u0096\u0001J\u000e\u0010Ã\u0002\u001a\u00020\u0015*\u00020+H\u0096\u0001J\u000f\u0010Ä\u0002\u001a\u00020\u0015*\u00030\u0095\u0001H\u0096\u0001J\u000e\u0010Å\u0002\u001a\u00020\u0015*\u00020fH\u0096\u0001J\u000e\u0010Æ\u0002\u001a\u00020\u0015*\u00020fH\u0096\u0001J\u000e\u0010Ç\u0002\u001a\u00020\u0015*\u00020fH\u0096\u0001J\u000e\u0010È\u0002\u001a\u00020\u0015*\u00020\u0010H\u0096\u0001J\u000e\u0010Ë\u0001\u001a\u00020\u0015*\u00020\u0010H\u0096\u0001J\u000e\u0010É\u0002\u001a\u00020\u0015*\u00020+H\u0096\u0001J\u000e\u0010Ê\u0002\u001a\u00020\u0015*\u00020+H\u0096\u0001J\u000e\u0010Ë\u0002\u001a\u00020\u0015*\u00020+H\u0096\u0001J\u000e\u0010Ì\u0002\u001a\u00020\u0015*\u00020\u0010H\u0096\u0001J\u0017\u0010Í\u0002\u001a\n\u0012\u0005\u0012\u00030\u0095\u00010Î\u0002*\u00030í\u0001H\u0096\u0003J\u000f\u0010\u009d\u0001\u001a\u00020f*\u00030ó\u0001H\u0096\u0001J\u000e\u0010Ï\u0002\u001a\u00020f*\u00020+H\u0096\u0001J\u0010\u0010c\u001a\u0004\u0018\u00010+*\u00030\u0093\u0001H\u0096\u0001J\u000e\u0010Ð\u0002\u001a\u00020+*\u00020+H\u0096\u0001J\u000e\u0010Ñ\u0002\u001a\u00020f*\u00020fH\u0096\u0001J\u000f\u0010Ò\u0002\u001a\u00020f*\u00030ð\u0001H\u0096\u0001J\u000e\u0010Ó\u0002\u001a\u00020f*\u00020fH\u0096\u0001J\u000e\u0010Ô\u0002\u001a\u000204*\u00020\u0010H\u0096\u0001J\u0014\u0010Õ\u0002\u001a\b\u0012\u0004\u0012\u00020+00*\u00020fH\u0096\u0001J \u0010Ö\u0002\u001a\u00020A*\u00020M2\t\u0010\u0081\u0001\u001a\u0004\u0018\u00010;2\u0006\u0010~\u001a\u00020+H\u0002J\u0010\u0010×\u0002\u001a\u00030\u0095\u0001*\u00030Ø\u0002H\u0096\u0001J\u000e\u0010Ù\u0002\u001a\u00020+*\u00020+H\u0096\u0001J\u000e\u0010Ú\u0002\u001a\u00020+*\u00020+H\u0096\u0001J&\u0010Û\u0002\u001a\u00020+*\u00020+2\u0016\u0010Ü\u0002\u001a\u0011\u0012\u0005\u0012\u00030\u0095\u0001\u0012\u0005\u0012\u00030\u0095\u00010ê\u0001H\u0096\u0001J&\u0010Û\u0002\u001a\u00020f*\u00020f2\u0016\u0010Ü\u0002\u001a\u0011\u0012\u0005\u0012\u00030\u0095\u0001\u0012\u0005\u0012\u00030\u0095\u00010ê\u0001H\u0096\u0001J\u001e\u0010Û\u0002\u001a\u00020f*\u00020f2\u000e\u0010Ý\u0002\u001a\t\u0012\u0005\u0012\u00030\u0095\u00010\u001eH\u0096\u0001J&\u0010Þ\u0002\u001a\u00020+*\u00020+2\u0016\u0010Ü\u0002\u001a\u0011\u0012\u0005\u0012\u00030\u0095\u0001\u0012\u0005\u0012\u00030\u0095\u00010ê\u0001H\u0096\u0001J&\u0010Þ\u0002\u001a\u00020f*\u00020f2\u0016\u0010Ü\u0002\u001a\u0011\u0012\u0005\u0012\u00030\u0095\u0001\u0012\u0005\u0012\u00030\u0095\u00010ê\u0001H\u0096\u0001J\u001e\u0010ß\u0002\u001a\u00020+*\u00020+2\u000e\u0010à\u0002\u001a\t\u0012\u0005\u0012\u00030¥\u00010\u001eH\u0096\u0001J\u0019\u0010á\u0002\u001a\u00030\u0095\u0001*\u00030\u0095\u00012\u0007\u0010â\u0002\u001a\u00020+H\u0096\u0001J\u0016\u0010ã\u0002\u001a\u00020+*\u00020r2\u0006\u0010v\u001a\u00020+H\u0096\u0001J\u0016\u0010ä\u0002\u001a\u0004\u0018\u00010+*\b\u0012\u0004\u0012\u00020+00H\u0096\u0001J\u000f\u0010å\u0002\u001a\u000204*\u00030í\u0001H\u0096\u0001J*\u0010æ\u0002\u001a\u00020+*\u00020+2\u0007\u0010Þ\u0001\u001a\u00020r2\b\u0010ç\u0002\u001a\u00030è\u00022\b\u0010é\u0002\u001a\u00030ê\u0002H\u0002J\u0014\u0010ë\u0002\u001a\b\u0012\u0004\u0012\u00020+00*\u00020\u0010H\u0096\u0001J\u000e\u0010ì\u0002\u001a\u00020f*\u00020\u0010H\u0096\u0001J\u0010\u0010í\u0002\u001a\u00030Ø\u0002*\u00030\u0093\u0001H\u0096\u0001J\u000e\u0010í\u0002\u001a\u00020\u0010*\u00020+H\u0096\u0001J\u000e\u0010í\u0002\u001a\u00020\u0010*\u00020fH\u0096\u0001J\u0010\u0010î\u0002\u001a\u00030\u0095\u0001*\u00030\u0093\u0001H\u0096\u0001J\u000e\u0010ï\u0002\u001a\u000204*\u00020+H\u0096\u0001J\u000e\u0010ï\u0002\u001a\u000204*\u00020fH\u0096\u0001J\u0010\u0010N\u001a\u0004\u0018\u00010O*\u00030\u0093\u0001H\u0096\u0001J\u000e\u0010ð\u0002\u001a\u00020\u0010*\u00020\u0010H\u0096\u0001J\u000f\u0010\u009e\u0001\u001a\u00020f*\u00030ó\u0001H\u0096\u0001J\u000e\u0010ñ\u0002\u001a\u000204*\u00020OH\u0096\u0001J\u000e\u0010ò\u0002\u001a\u00020f*\u00020+H\u0096\u0001J\u000f\u0010ó\u0002\u001a\u00020+*\u00030\u0093\u0001H\u0096\u0001J\u0017\u0010ô\u0002\u001a\u00020+*\u00020+2\u0007\u0010¢\u0001\u001a\u00020\u0015H\u0096\u0001J\u0017\u0010ô\u0002\u001a\u00020f*\u00020f2\u0007\u0010¢\u0001\u001a\u00020\u0015H\u0096\u0001R \u0010\u000e\u001a\u000e\u0012\u0004\u0012\u00020\u0010\u0012\u0004\u0012\u00020\u00110\u000f8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0012\u0010\u0013R\u001a\u0010\u0014\u001a\u00020\u0015X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0016\u0010\u0017\"\u0004\b\u0018\u0010\u0019R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n\u0000RF\u0010\u001a\u001a4\u00120\u0012.\u0012\u0004\u0012\u00020\u001d\u0012$\u0012\"\u0012\u001a\u0012\u0018\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u0011\u0012\u0004\u0012\u00020 0\u001c0\u001fj\u0002`!0\u001ej\u0002`\"0\u001c0\u001b8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b#\u0010$R\u000e\u0010%\u001a\u00020\u0015X\u0082\u000e¢\u0006\u0002\n\u0000R\u001a\u0010&\u001a\b\u0012\u0004\u0012\u00020'0\u001e8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b(\u0010$R \u0010)\u001a\u000e\u0012\u0004\u0012\u00020\u0010\u0012\u0004\u0012\u00020+0*8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b,\u0010\u0013R\u0014\u0010-\u001a\u00020\u00158VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b.\u0010\u0017R\"\u0010/\u001a\u0016\u0012\n\u0012\b\u0012\u0004\u0012\u00020+00\u0012\u0006\u0012\u0004\u0018\u0001010*X\u0082\u0004¢\u0006\u0002\n\u0000R\u0012\u00102\u001a\u00020\u0015X\u0096\u0005¢\u0006\u0006\u001a\u0004\b2\u0010\u0017R\u000e\u0010\u000b\u001a\u00020\fX\u0082\u0004¢\u0006\u0002\n\u0000R$\u00105\u001a\u0002042\u0006\u00103\u001a\u0002048V@VX\u0096\u000e¢\u0006\f\u001a\u0004\b6\u00107\"\u0004\b8\u00109R \u0010:\u001a\u000e\u0012\u0004\u0012\u00020\u0010\u0012\u0004\u0012\u00020;0*8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b<\u0010\u0013R\u0014\u0010=\u001a\b\u0012\u0004\u0012\u00020+0>X\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010?\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020A0@0\u001bX\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010B\u001a\b\u0012\u0004\u0012\u00020\u00110\u001e8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\bC\u0010$R\u0014\u0010D\u001a\b\u0012\u0004\u0012\u00020+0>X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010E\u001a\u00020FX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010G\u001a\u00020HX\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\n\u001a\u00020\u0002¢\u0006\b\n\u0000\u001a\u0004\bI\u0010JR\u0014\u0010K\u001a\b\u0012\u0004\u0012\u00020\u00110\u001bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010L\u001a\u00020MX\u0082\u0004¢\u0006\u0002\n\u0000R\u0018\u0010N\u001a\u0004\u0018\u00010O*\u00020PX\u0096\u0005¢\u0006\u0006\u001a\u0004\bQ\u0010R¨\u0006÷\u0002"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/inference/model/NewConstraintSystemImpl;", "Lorg/jetbrains/kotlin/resolve/calls/inference/components/ConstraintSystemCompletionContext;", "Lorg/jetbrains/kotlin/types/model/TypeSystemInferenceExtensionContext;", "Lorg/jetbrains/kotlin/resolve/calls/inference/NewConstraintSystem;", "Lorg/jetbrains/kotlin/resolve/calls/inference/ConstraintSystemBuilder;", "Lorg/jetbrains/kotlin/resolve/calls/inference/components/ConstraintInjector$Context;", "Lorg/jetbrains/kotlin/resolve/calls/inference/components/ResultTypeResolver$Context;", "Lorg/jetbrains/kotlin/resolve/calls/components/PostponedArgumentsAnalyzerContext;", "constraintInjector", "Lorg/jetbrains/kotlin/resolve/calls/inference/components/ConstraintInjector;", "typeSystemContext", "languageVersionSettings", "Lorg/jetbrains/kotlin/config/LanguageVersionSettings;", "(Lorg/jetbrains/kotlin/resolve/calls/inference/components/ConstraintInjector;Lorg/jetbrains/kotlin/types/model/TypeSystemInferenceExtensionContext;Lorg/jetbrains/kotlin/config/LanguageVersionSettings;)V", "allTypeVariables", "", "Lorg/jetbrains/kotlin/types/model/TypeConstructorMarker;", "Lorg/jetbrains/kotlin/types/model/TypeVariableMarker;", "getAllTypeVariables", "()Ljava/util/Map;", "atCompletionState", "", "getAtCompletionState", "()Z", "setAtCompletionState", "(Z)V", "constraintsFromAllForkPoints", "", "Lkotlin/Pair;", "Lorg/jetbrains/kotlin/resolve/calls/inference/model/IncorporationConstraintPosition;", "", "", "Lorg/jetbrains/kotlin/resolve/calls/inference/model/Constraint;", "Lorg/jetbrains/kotlin/resolve/calls/inference/ForkPointBranchDescription;", "Lorg/jetbrains/kotlin/resolve/calls/inference/ForkPointData;", "getConstraintsFromAllForkPoints", "()Ljava/util/List;", "couldBeResolvedWithUnrestrictedBuilderInference", "errors", "Lorg/jetbrains/kotlin/resolve/calls/inference/model/ConstraintSystemError;", "getErrors", "fixedTypeVariables", "", "Lorg/jetbrains/kotlin/types/model/KotlinTypeMarker;", "getFixedTypeVariables", "hasContradiction", "getHasContradiction", "intersectionTypesCache", "", "Lorg/jetbrains/kotlin/resolve/checkers/EmptyIntersectionTypeInfo;", "isK2", "value", "", "maxTypeDepthFromInitialConstraints", "getMaxTypeDepthFromInitialConstraints", "()I", "setMaxTypeDepthFromInitialConstraints", "(I)V", "notFixedTypeVariables", "Lorg/jetbrains/kotlin/resolve/calls/inference/model/MutableVariableWithConstraints;", "getNotFixedTypeVariables", "notProperTypesCache", "", "postponedComputationsAfterAllVariablesAreFixed", "Lkotlin/Function0;", "", "postponedTypeVariables", "getPostponedTypeVariables", "properTypesCache", "state", "Lorg/jetbrains/kotlin/resolve/calls/inference/model/NewConstraintSystemImpl$State;", "storage", "Lorg/jetbrains/kotlin/resolve/calls/inference/model/MutableConstraintStorage;", "getTypeSystemContext", "()Lorg/jetbrains/kotlin/types/model/TypeSystemInferenceExtensionContext;", "typeVariablesTransaction", "utilContext", "Lorg/jetbrains/kotlin/resolve/calls/inference/components/ConstraintSystemUtilContext;", "typeParameter", "Lorg/jetbrains/kotlin/types/model/TypeParameterMarker;", "Lorg/jetbrains/kotlin/types/model/TypeVariableTypeConstructorMarker;", "getTypeParameter", "(Lorg/jetbrains/kotlin/types/model/TypeVariableTypeConstructorMarker;)Lorg/jetbrains/kotlin/types/model/TypeParameterMarker;", "addEqualityConstraint", "a", JvmAnnotationNames.SERIALIZED_IR_BYTES_FIELD_NAME, "position", "Lorg/jetbrains/kotlin/resolve/calls/inference/model/ConstraintPosition;", "addError", "error", "addInitialConstraint", "initialConstraint", "Lorg/jetbrains/kotlin/resolve/calls/inference/model/InitialConstraint;", "addMissedConstraints", "constraints", "addOtherSystem", "otherSystem", "Lorg/jetbrains/kotlin/resolve/calls/inference/model/ConstraintStorage;", "addSubtypeConstraint", "lowerType", "upperType", SchemaSymbols.ATTVAL_ANYTYPE, "Lorg/jetbrains/kotlin/types/model/SimpleTypeMarker;", "applyConstraintsFromFirstSuccessfulBranchOfTheFork", "forkPointData", "areEqualTypeConstructors", "c1", "c2", "asConstraintSystemCompleterContext", "asPostponedArgumentsAnalyzerContext", "asReadOnlyStorage", "bindingStubsForPostponedVariables", "Lorg/jetbrains/kotlin/types/model/StubTypeMarker;", "buildCurrentSubstitutor", "Lorg/jetbrains/kotlin/types/model/TypeSubstitutorMarker;", "additionalBindings", "buildNotFixedVariablesToStubTypesSubstitutor", "canBeProper", ModuleXmlParser.TYPE, "captureFromArguments", NotificationCompat.CATEGORY_STATUS, "Lorg/jetbrains/kotlin/types/model/CaptureStatus;", "captureFromExpression", "checkIfForksMightBeSuccessfullyResolved", "checkInferredEmptyIntersection", "variable", "resultType", "checkMissedConstraints", "checkOnlyInputTypesAnnotation", "variableWithConstraints", "checkState", "allowedState", "", "([Lorg/jetbrains/kotlin/resolve/calls/inference/model/NewConstraintSystemImpl$State;)V", "c", "d", "closeTransaction", "beforeState", "beforeTypeVariables", "computeEmptyIntersectionTypeKind", SpecTypePair.NAME_types, "containsOnlyFixedOrPostponedVariables", "containsOnlyFixedVariables", "createCapturedStarProjectionForSelfType", "typeVariable", "typesForRecursiveTypeParameters", "createCapturedType", "Lorg/jetbrains/kotlin/types/model/CapturedTypeMarker;", "constructorProjection", "Lorg/jetbrains/kotlin/types/model/TypeArgumentMarker;", "constructorSupertypes", "captureStatus", "createEmptySubstitutor", "createErrorType", "debugName", "", "createFlexibleType", "lowerBound", "upperBound", "createSimpleType", JvmAbi.ERASED_INLINE_CONSTRUCTOR_NAME, "arguments", "nullable", "isExtensionFunction", "attributes", "Lorg/jetbrains/kotlin/types/model/AnnotationMarker;", "createStarProjection", "createStubTypeForBuilderInference", "createStubTypeForTypeVariablesInSubtyping", "createSubstitutorForSuperTypes", "baseType", "createTypeArgument", "variance", "Lorg/jetbrains/kotlin/types/model/TypeVariance;", "createTypeWithAlternativeForIntersectionResult", "firstCandidate", "secondCandidate", "createUninferredType", "currentStorage", "doPostponedComputationsIfAllVariablesAreFixed", "findCommonIntegerLiteralTypesSuperType", "explicitSupertypes", "fixVariable", "Lorg/jetbrains/kotlin/resolve/calls/inference/model/FixVariableConstraintPosition;", "getBuilder", "getBuiltFunctionalExpectedTypeForPostponedArgument", "expectedTypeVariable", "topLevelVariable", "pathToExpectedType", "getEmptyIntersectionTypeKind", "getNonReflectFunctionTypeConstructor", "parametersNumber", Namer.METADATA_CLASS_KIND, "Lorg/jetbrains/kotlin/builtins/functions/FunctionTypeKind;", "getProperSuperTypeConstructors", "getReflectFunctionTypeConstructor", "hasUpperOrEqualUnitConstraint", "identicalArguments", "intersectTypes", "isPostponedTypeVariable", "isProperType", "isProperTypeImpl", "isReified", "isTypeVariable", "markCouldBeResolvedWithUnrestrictedBuilderInference", "markPostponedVariable", "newTypeCheckerState", "Lorg/jetbrains/kotlin/types/TypeCheckerState;", "errorTypesEqualToAnything", "stubTypesEqualToAnything", "nothingType", "nullableAnyType", "nullableNothingType", "prepareTransaction", "Lorg/jetbrains/kotlin/resolve/calls/inference/ConstraintSystemTransaction;", "putBuiltFunctionalExpectedTypeForPostponedArgument", "builtFunctionalType", "registerVariable", "removePostponedTypeVariablesFromConstraints", "removePostponedVariables", "resolveForkPointsConstraints", "substituteFixedVariables", "substitutor", "substituteMissedConstraints", "substitutionSupertypePolicy", "Lorg/jetbrains/kotlin/types/TypeCheckerState$SupertypesPolicy;", "transactionRegisterVariable", "typeSubstitutorByTypeConstructor", "map", "unionTypeAttributes", "unmarkPostponedVariable", "useRefinedBoundsForTypeVariableInFlexiblePosition", "anySuperTypeConstructor", "predicate", "Lkotlin/Function1;", "argumentsCount", "asArgumentList", "Lorg/jetbrains/kotlin/types/model/TypeArgumentListMarker;", "asCapturedType", "asDefinitelyNotNullType", "Lorg/jetbrains/kotlin/types/model/DefinitelyNotNullTypeMarker;", "asDynamicType", "Lorg/jetbrains/kotlin/types/model/DynamicTypeMarker;", "Lorg/jetbrains/kotlin/types/model/FlexibleTypeMarker;", "asFlexibleType", "asSimpleType", "asTypeArgument", "canHaveUndefinedNullability", "contains", "convertToNonRaw", "defaultType", "eraseContainingTypeParameters", "extractArgumentsForFunctionTypeOrSubtype", "extractTypeParameters", "extractTypeVariables", "fastCorrespondingSupertypes", "freshTypeConstructor", "functionTypeKind", "get", "index", "getApproximatedIntegerLiteralType", "getArgument", "getArgumentOrNull", "getArguments", "getAttributes", "getCustomAttributes", "getFunctionTypeFromSupertypes", "getOriginalTypeVariable", "getParameter", "getParameters", "getType", "getTypeConstructor", "getTypeParameterClassifier", "getUpperBound", "getUpperBounds", "getVariance", "hasCustomAttributes", "hasExactAnnotation", "hasFlexibleNullability", "hasNoInferAnnotation", "hasRecursiveBounds", "selfConstructor", "isAnonymous", "isAnyConstructor", "isBuiltinFunctionTypeOrSubtype", "isCapturedDynamic", "isCapturedType", "isCapturedTypeConstructor", "isClassType", "isClassTypeConstructor", "isCommonFinalClassConstructor", "isContainedInInvariantOrContravariantPositions", "isDefinitelyClassTypeConstructor", "isDefinitelyNotNullType", "isDenotable", "isDynamic", "isError", "isExtensionFunctionType", "isFinalClassConstructor", "isFlexible", "isFlexibleNothing", "isFunctionOrKFunctionWithAnySuspendability", "isIntegerConstantOperatorTypeConstructor", "isIntegerLiteralConstantTypeConstructor", "isIntegerLiteralType", "isIntegerLiteralTypeConstructor", "isInterface", "isIntersection", "isLocalType", "isMarkedNullable", "isNotNullTypeParameter", "isNothing", "isNothingConstructor", "isNullableAny", "isNullableNothing", "isNullableType", "isOldCapturedType", "isPrimitiveType", "isProjectionNotNull", "isRawType", "isSignedOrUnsignedNumberType", "isSimpleType", "isSingleClassifierType", "isSpecial", "isStarProjection", "isStubType", "isStubTypeForBuilderInference", "isStubTypeForVariableInSubtyping", "isTypeParameterTypeConstructor", "isTypeVariableType", "isUninferredParameter", "isUnit", "isUnitTypeConstructor", "iterator", "", "lowerBoundIfFlexible", "makeDefinitelyNotNullOrNotNull", "makeSimpleTypeDefinitelyNotNullOrNotNull", "original", "originalIfDefinitelyNotNullable", "parametersCount", "possibleIntegerTypes", "postponeOnlyInputTypesCheck", "projection", "Lorg/jetbrains/kotlin/types/model/CapturedTypeConstructorMarker;", "removeAnnotations", "removeExactAnnotation", "replaceArguments", "replacement", "newArguments", "replaceArgumentsDeeply", "replaceCustomAttributes", "newAttributes", "replaceType", "newType", "safeSubstitute", "singleBestRepresentative", "size", "substituteAndApproximateIfNecessary", "approximator", "Lorg/jetbrains/kotlin/types/AbstractTypeApproximator;", "constraintKind", "Lorg/jetbrains/kotlin/resolve/calls/inference/model/ConstraintKind;", "supertypes", "toErrorType", "typeConstructor", "typeConstructorProjection", "typeDepth", "unwrapStubTypeVariableConstructor", "upperBoundCount", "upperBoundIfFlexible", "withNotNullProjection", "withNullability", "State", "TransactionState", "resolution.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class NewConstraintSystemImpl extends ConstraintSystemCompletionContext implements PostponedArgumentsAnalyzerContext, ConstraintSystemBuilder, NewConstraintSystem, ConstraintInjector.Context, ResultTypeResolver.Context, TypeSystemInferenceExtensionContext {
    private boolean atCompletionState;
    private final ConstraintInjector constraintInjector;
    private boolean couldBeResolvedWithUnrestrictedBuilderInference;
    private final Map<Collection<KotlinTypeMarker>, EmptyIntersectionTypeInfo> intersectionTypesCache;
    private final LanguageVersionSettings languageVersionSettings;
    private final Set<KotlinTypeMarker> notProperTypesCache;
    private final List<Function0<Unit>> postponedComputationsAfterAllVariablesAreFixed;
    private final Set<KotlinTypeMarker> properTypesCache;
    private State state;
    private final MutableConstraintStorage storage;
    private final TypeSystemInferenceExtensionContext typeSystemContext;
    private final List<TypeVariableMarker> typeVariablesTransaction;
    private final ConstraintSystemUtilContext utilContext;

    /* JADX INFO: compiled from: NewConstraintSystemImpl.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0006\b\u0082\u0081\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004j\u0002\b\u0005j\u0002\b\u0006¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/inference/model/NewConstraintSystemImpl$State;", "", "(Ljava/lang/String;I)V", "BUILDING", "TRANSACTION", "FREEZED", "COMPLETION", "resolution.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    private enum State {
        BUILDING,
        TRANSACTION,
        FREEZED,
        COMPLETION;

        private static final /* synthetic */ EnumEntries $ENTRIES = EnumEntriesKt.enumEntries(values());

        public static EnumEntries<State> getEntries() {
            return $ENTRIES;
        }
    }

    /* JADX INFO: compiled from: NewConstraintSystemImpl.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[ConstraintKind.values().length];
            try {
                iArr[ConstraintKind.LOWER.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[ConstraintKind.UPPER.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[ConstraintKind.EQUALITY.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemCommonSuperTypesContext
    public boolean anySuperTypeConstructor(KotlinTypeMarker kotlinTypeMarker, Function1<? super SimpleTypeMarker, Boolean> predicate) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        Intrinsics.checkNotNullParameter(predicate, "predicate");
        return this.typeSystemContext.anySuperTypeConstructor(kotlinTypeMarker, predicate);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemBuiltInsContext
    public SimpleTypeMarker anyType() {
        return this.typeSystemContext.anyType();
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean areEqualTypeConstructors(TypeConstructorMarker c1, TypeConstructorMarker c2) {
        Intrinsics.checkNotNullParameter(c1, "c1");
        Intrinsics.checkNotNullParameter(c2, "c2");
        return this.typeSystemContext.areEqualTypeConstructors(c1, c2);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public int argumentsCount(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        return this.typeSystemContext.argumentsCount(kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public TypeArgumentListMarker asArgumentList(SimpleTypeMarker simpleTypeMarker) {
        Intrinsics.checkNotNullParameter(simpleTypeMarker, "<this>");
        return this.typeSystemContext.asArgumentList(simpleTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public CapturedTypeMarker asCapturedType(SimpleTypeMarker simpleTypeMarker) {
        Intrinsics.checkNotNullParameter(simpleTypeMarker, "<this>");
        return this.typeSystemContext.asCapturedType(simpleTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public DefinitelyNotNullTypeMarker asDefinitelyNotNullType(SimpleTypeMarker simpleTypeMarker) {
        Intrinsics.checkNotNullParameter(simpleTypeMarker, "<this>");
        return this.typeSystemContext.asDefinitelyNotNullType(simpleTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public DynamicTypeMarker asDynamicType(FlexibleTypeMarker flexibleTypeMarker) {
        Intrinsics.checkNotNullParameter(flexibleTypeMarker, "<this>");
        return this.typeSystemContext.asDynamicType(flexibleTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public FlexibleTypeMarker asFlexibleType(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        return this.typeSystemContext.asFlexibleType(kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public SimpleTypeMarker asSimpleType(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        return this.typeSystemContext.asSimpleType(kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public TypeArgumentMarker asTypeArgument(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        return this.typeSystemContext.asTypeArgument(kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemCommonSuperTypesContext
    public boolean canHaveUndefinedNullability(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        return this.typeSystemContext.canHaveUndefinedNullability(kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public SimpleTypeMarker captureFromArguments(SimpleTypeMarker type, CaptureStatus status) {
        Intrinsics.checkNotNullParameter(type, "type");
        Intrinsics.checkNotNullParameter(status, "status");
        return this.typeSystemContext.captureFromArguments(type, status);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public KotlinTypeMarker captureFromExpression(KotlinTypeMarker type) {
        Intrinsics.checkNotNullParameter(type, "type");
        return this.typeSystemContext.captureFromExpression(type);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public CaptureStatus captureStatus(CapturedTypeMarker capturedTypeMarker) {
        Intrinsics.checkNotNullParameter(capturedTypeMarker, "<this>");
        return this.typeSystemContext.captureStatus(capturedTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemInferenceExtensionContext
    public EmptyIntersectionTypeInfo computeEmptyIntersectionTypeKind(Collection<? extends KotlinTypeMarker> types) {
        Intrinsics.checkNotNullParameter(types, "types");
        return this.typeSystemContext.computeEmptyIntersectionTypeKind(types);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemInferenceExtensionContext
    public boolean contains(KotlinTypeMarker kotlinTypeMarker, Function1<? super KotlinTypeMarker, Boolean> predicate) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        Intrinsics.checkNotNullParameter(predicate, "predicate");
        return this.typeSystemContext.contains(kotlinTypeMarker, predicate);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemInferenceExtensionContext
    public KotlinTypeMarker convertToNonRaw(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        return this.typeSystemContext.convertToNonRaw(kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemInferenceExtensionContext
    public SimpleTypeMarker createCapturedStarProjectionForSelfType(TypeVariableTypeConstructorMarker typeVariable, List<? extends KotlinTypeMarker> typesForRecursiveTypeParameters) {
        Intrinsics.checkNotNullParameter(typeVariable, "typeVariable");
        Intrinsics.checkNotNullParameter(typesForRecursiveTypeParameters, "typesForRecursiveTypeParameters");
        return this.typeSystemContext.createCapturedStarProjectionForSelfType(typeVariable, typesForRecursiveTypeParameters);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemInferenceExtensionContext
    public CapturedTypeMarker createCapturedType(TypeArgumentMarker constructorProjection, List<? extends KotlinTypeMarker> constructorSupertypes, KotlinTypeMarker lowerType, CaptureStatus captureStatus) {
        Intrinsics.checkNotNullParameter(constructorProjection, "constructorProjection");
        Intrinsics.checkNotNullParameter(constructorSupertypes, "constructorSupertypes");
        Intrinsics.checkNotNullParameter(captureStatus, "captureStatus");
        return this.typeSystemContext.createCapturedType(constructorProjection, constructorSupertypes, lowerType, captureStatus);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public TypeSubstitutorMarker createEmptySubstitutor() {
        return this.typeSystemContext.createEmptySubstitutor();
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemTypeFactoryContext
    public SimpleTypeMarker createErrorType(String debugName) {
        Intrinsics.checkNotNullParameter(debugName, "debugName");
        return this.typeSystemContext.createErrorType(debugName);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemTypeFactoryContext
    public KotlinTypeMarker createFlexibleType(SimpleTypeMarker lowerBound, SimpleTypeMarker upperBound) {
        Intrinsics.checkNotNullParameter(lowerBound, "lowerBound");
        Intrinsics.checkNotNullParameter(upperBound, "upperBound");
        return this.typeSystemContext.createFlexibleType(lowerBound, upperBound);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemTypeFactoryContext
    public SimpleTypeMarker createSimpleType(TypeConstructorMarker constructor, List<? extends TypeArgumentMarker> arguments, boolean nullable, boolean isExtensionFunction, List<? extends AnnotationMarker> attributes) {
        Intrinsics.checkNotNullParameter(constructor, "constructor");
        Intrinsics.checkNotNullParameter(arguments, "arguments");
        return this.typeSystemContext.createSimpleType(constructor, arguments, nullable, isExtensionFunction, attributes);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemTypeFactoryContext
    public TypeArgumentMarker createStarProjection(TypeParameterMarker typeParameter) {
        Intrinsics.checkNotNullParameter(typeParameter, "typeParameter");
        return this.typeSystemContext.createStarProjection(typeParameter);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemInferenceExtensionContext
    public StubTypeMarker createStubTypeForBuilderInference(TypeVariableMarker typeVariable) {
        Intrinsics.checkNotNullParameter(typeVariable, "typeVariable");
        return this.typeSystemContext.createStubTypeForBuilderInference(typeVariable);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemInferenceExtensionContext
    public StubTypeMarker createStubTypeForTypeVariablesInSubtyping(TypeVariableMarker typeVariable) {
        Intrinsics.checkNotNullParameter(typeVariable, "typeVariable");
        return this.typeSystemContext.createStubTypeForTypeVariablesInSubtyping(typeVariable);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemInferenceExtensionContext
    public TypeSubstitutorMarker createSubstitutorForSuperTypes(KotlinTypeMarker baseType) {
        Intrinsics.checkNotNullParameter(baseType, "baseType");
        return this.typeSystemContext.createSubstitutorForSuperTypes(baseType);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemTypeFactoryContext
    public TypeArgumentMarker createTypeArgument(KotlinTypeMarker type, TypeVariance variance) {
        Intrinsics.checkNotNullParameter(type, "type");
        Intrinsics.checkNotNullParameter(variance, "variance");
        return this.typeSystemContext.createTypeArgument(type, variance);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemInferenceExtensionContext
    public KotlinTypeMarker createTypeWithAlternativeForIntersectionResult(KotlinTypeMarker firstCandidate, KotlinTypeMarker secondCandidate) {
        Intrinsics.checkNotNullParameter(firstCandidate, "firstCandidate");
        Intrinsics.checkNotNullParameter(secondCandidate, "secondCandidate");
        return this.typeSystemContext.createTypeWithAlternativeForIntersectionResult(firstCandidate, secondCandidate);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemTypeFactoryContext
    public KotlinTypeMarker createUninferredType(TypeConstructorMarker constructor) {
        Intrinsics.checkNotNullParameter(constructor, "constructor");
        return this.typeSystemContext.createUninferredType(constructor);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemInferenceExtensionContext
    public SimpleTypeMarker defaultType(TypeVariableMarker typeVariableMarker) {
        Intrinsics.checkNotNullParameter(typeVariableMarker, "<this>");
        return this.typeSystemContext.defaultType(typeVariableMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemInferenceExtensionContext
    public KotlinTypeMarker eraseContainingTypeParameters(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        return this.typeSystemContext.eraseContainingTypeParameters(kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemInferenceExtensionContext
    public List<KotlinTypeMarker> extractArgumentsForFunctionTypeOrSubtype(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        return this.typeSystemContext.extractArgumentsForFunctionTypeOrSubtype(kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemInferenceExtensionContext
    public Set<TypeParameterMarker> extractTypeParameters(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        return this.typeSystemContext.extractTypeParameters(kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemInferenceExtensionContext
    public Set<TypeVariableTypeConstructorMarker> extractTypeVariables(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        return this.typeSystemContext.extractTypeVariables(kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public List<SimpleTypeMarker> fastCorrespondingSupertypes(SimpleTypeMarker simpleTypeMarker, TypeConstructorMarker constructor) {
        Intrinsics.checkNotNullParameter(simpleTypeMarker, "<this>");
        Intrinsics.checkNotNullParameter(constructor, "constructor");
        return this.typeSystemContext.fastCorrespondingSupertypes(simpleTypeMarker, constructor);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemCommonSuperTypesContext
    public SimpleTypeMarker findCommonIntegerLiteralTypesSuperType(List<? extends SimpleTypeMarker> explicitSupertypes) {
        Intrinsics.checkNotNullParameter(explicitSupertypes, "explicitSupertypes");
        return this.typeSystemContext.findCommonIntegerLiteralTypesSuperType(explicitSupertypes);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemInferenceExtensionContext
    public TypeConstructorMarker freshTypeConstructor(TypeVariableMarker typeVariableMarker) {
        Intrinsics.checkNotNullParameter(typeVariableMarker, "<this>");
        return this.typeSystemContext.freshTypeConstructor(typeVariableMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemInferenceExtensionContext
    public FunctionTypeKind functionTypeKind(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        return this.typeSystemContext.functionTypeKind(kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public TypeArgumentMarker get(TypeArgumentListMarker typeArgumentListMarker, int i) {
        Intrinsics.checkNotNullParameter(typeArgumentListMarker, "<this>");
        return this.typeSystemContext.get(typeArgumentListMarker, i);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemInferenceExtensionContext
    public KotlinTypeMarker getApproximatedIntegerLiteralType(TypeConstructorMarker typeConstructorMarker) {
        Intrinsics.checkNotNullParameter(typeConstructorMarker, "<this>");
        return this.typeSystemContext.getApproximatedIntegerLiteralType(typeConstructorMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public TypeArgumentMarker getArgument(KotlinTypeMarker kotlinTypeMarker, int i) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        return this.typeSystemContext.getArgument(kotlinTypeMarker, i);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public TypeArgumentMarker getArgumentOrNull(SimpleTypeMarker simpleTypeMarker, int i) {
        Intrinsics.checkNotNullParameter(simpleTypeMarker, "<this>");
        return this.typeSystemContext.getArgumentOrNull(simpleTypeMarker, i);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public List<TypeArgumentMarker> getArguments(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        return this.typeSystemContext.getArguments(kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public List<AnnotationMarker> getAttributes(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        return this.typeSystemContext.getAttributes(kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public List<AnnotationMarker> getCustomAttributes(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        return this.typeSystemContext.getCustomAttributes(kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemInferenceExtensionContext
    public KotlinTypeMarker getFunctionTypeFromSupertypes(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        return this.typeSystemContext.getFunctionTypeFromSupertypes(kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemInferenceExtensionContext
    public TypeConstructorMarker getNonReflectFunctionTypeConstructor(int parametersNumber, FunctionTypeKind kind) {
        Intrinsics.checkNotNullParameter(kind, "kind");
        return this.typeSystemContext.getNonReflectFunctionTypeConstructor(parametersNumber, kind);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemInferenceExtensionContext
    public TypeVariableTypeConstructorMarker getOriginalTypeVariable(StubTypeMarker stubTypeMarker) {
        Intrinsics.checkNotNullParameter(stubTypeMarker, "<this>");
        return this.typeSystemContext.getOriginalTypeVariable(stubTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public TypeParameterMarker getParameter(TypeConstructorMarker typeConstructorMarker, int i) {
        Intrinsics.checkNotNullParameter(typeConstructorMarker, "<this>");
        return this.typeSystemContext.getParameter(typeConstructorMarker, i);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public List<TypeParameterMarker> getParameters(TypeConstructorMarker typeConstructorMarker) {
        Intrinsics.checkNotNullParameter(typeConstructorMarker, "<this>");
        return this.typeSystemContext.getParameters(typeConstructorMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemInferenceExtensionContext
    public TypeConstructorMarker getReflectFunctionTypeConstructor(int parametersNumber, FunctionTypeKind kind) {
        Intrinsics.checkNotNullParameter(kind, "kind");
        return this.typeSystemContext.getReflectFunctionTypeConstructor(parametersNumber, kind);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public KotlinTypeMarker getType(TypeArgumentMarker typeArgumentMarker) {
        Intrinsics.checkNotNullParameter(typeArgumentMarker, "<this>");
        return this.typeSystemContext.getType(typeArgumentMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public TypeConstructorMarker getTypeConstructor(TypeParameterMarker typeParameterMarker) {
        Intrinsics.checkNotNullParameter(typeParameterMarker, "<this>");
        return this.typeSystemContext.getTypeConstructor(typeParameterMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public TypeParameterMarker getTypeParameter(TypeVariableTypeConstructorMarker typeVariableTypeConstructorMarker) {
        Intrinsics.checkNotNullParameter(typeVariableTypeConstructorMarker, "<this>");
        return this.typeSystemContext.getTypeParameter(typeVariableTypeConstructorMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public TypeParameterMarker getTypeParameterClassifier(TypeConstructorMarker typeConstructorMarker) {
        Intrinsics.checkNotNullParameter(typeConstructorMarker, "<this>");
        return this.typeSystemContext.getTypeParameterClassifier(typeConstructorMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public KotlinTypeMarker getUpperBound(TypeParameterMarker typeParameterMarker, int i) {
        Intrinsics.checkNotNullParameter(typeParameterMarker, "<this>");
        return this.typeSystemContext.getUpperBound(typeParameterMarker, i);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public List<KotlinTypeMarker> getUpperBounds(TypeParameterMarker typeParameterMarker) {
        Intrinsics.checkNotNullParameter(typeParameterMarker, "<this>");
        return this.typeSystemContext.getUpperBounds(typeParameterMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public TypeVariance getVariance(TypeArgumentMarker typeArgumentMarker) {
        Intrinsics.checkNotNullParameter(typeArgumentMarker, "<this>");
        return this.typeSystemContext.getVariance(typeArgumentMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public TypeVariance getVariance(TypeParameterMarker typeParameterMarker) {
        Intrinsics.checkNotNullParameter(typeParameterMarker, "<this>");
        return this.typeSystemContext.getVariance(typeParameterMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean hasCustomAttributes(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        return this.typeSystemContext.hasCustomAttributes(kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemInferenceExtensionContext
    public boolean hasExactAnnotation(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        return this.typeSystemContext.hasExactAnnotation(kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean hasFlexibleNullability(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        return this.typeSystemContext.hasFlexibleNullability(kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemInferenceExtensionContext
    public boolean hasNoInferAnnotation(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        return this.typeSystemContext.hasNoInferAnnotation(kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean hasRecursiveBounds(TypeParameterMarker typeParameterMarker, TypeConstructorMarker typeConstructorMarker) {
        Intrinsics.checkNotNullParameter(typeParameterMarker, "<this>");
        return this.typeSystemContext.hasRecursiveBounds(typeParameterMarker, typeConstructorMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemOptimizationContext
    public boolean identicalArguments(SimpleTypeMarker a, SimpleTypeMarker b) {
        Intrinsics.checkNotNullParameter(a, "a");
        Intrinsics.checkNotNullParameter(b, "b");
        return this.typeSystemContext.identicalArguments(a, b);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public KotlinTypeMarker intersectTypes(List<? extends KotlinTypeMarker> types) {
        Intrinsics.checkNotNullParameter(types, "types");
        return this.typeSystemContext.intersectTypes(types);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    /* JADX INFO: renamed from: intersectTypes */
    public SimpleTypeMarker mo6107intersectTypes(List<? extends SimpleTypeMarker> types) {
        Intrinsics.checkNotNullParameter(types, "types");
        return this.typeSystemContext.intersectTypes((List<? extends KotlinTypeMarker>) types);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isAnonymous(TypeConstructorMarker typeConstructorMarker) {
        Intrinsics.checkNotNullParameter(typeConstructorMarker, "<this>");
        return this.typeSystemContext.isAnonymous(typeConstructorMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isAnyConstructor(TypeConstructorMarker typeConstructorMarker) {
        Intrinsics.checkNotNullParameter(typeConstructorMarker, "<this>");
        return this.typeSystemContext.isAnyConstructor(typeConstructorMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemInferenceExtensionContext
    public boolean isBuiltinFunctionTypeOrSubtype(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        return this.typeSystemContext.isBuiltinFunctionTypeOrSubtype(kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isCapturedDynamic(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        return this.typeSystemContext.isCapturedDynamic(kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isCapturedType(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        return this.typeSystemContext.isCapturedType(kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemInferenceExtensionContext
    public boolean isCapturedTypeConstructor(TypeConstructorMarker typeConstructorMarker) {
        Intrinsics.checkNotNullParameter(typeConstructorMarker, "<this>");
        return this.typeSystemContext.isCapturedTypeConstructor(typeConstructorMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isClassType(SimpleTypeMarker simpleTypeMarker) {
        Intrinsics.checkNotNullParameter(simpleTypeMarker, "<this>");
        return this.typeSystemContext.isClassType(simpleTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isClassTypeConstructor(TypeConstructorMarker typeConstructorMarker) {
        Intrinsics.checkNotNullParameter(typeConstructorMarker, "<this>");
        return this.typeSystemContext.isClassTypeConstructor(typeConstructorMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isCommonFinalClassConstructor(TypeConstructorMarker typeConstructorMarker) {
        Intrinsics.checkNotNullParameter(typeConstructorMarker, "<this>");
        return this.typeSystemContext.isCommonFinalClassConstructor(typeConstructorMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemInferenceExtensionContext
    public boolean isContainedInInvariantOrContravariantPositions(TypeVariableTypeConstructorMarker typeVariableTypeConstructorMarker) {
        Intrinsics.checkNotNullParameter(typeVariableTypeConstructorMarker, "<this>");
        return this.typeSystemContext.isContainedInInvariantOrContravariantPositions(typeVariableTypeConstructorMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isDefinitelyClassTypeConstructor(TypeConstructorMarker typeConstructorMarker) {
        Intrinsics.checkNotNullParameter(typeConstructorMarker, "<this>");
        return this.typeSystemContext.isDefinitelyClassTypeConstructor(typeConstructorMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isDefinitelyNotNullType(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        return this.typeSystemContext.isDefinitelyNotNullType(kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isDenotable(TypeConstructorMarker typeConstructorMarker) {
        Intrinsics.checkNotNullParameter(typeConstructorMarker, "<this>");
        return this.typeSystemContext.isDenotable(typeConstructorMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isDynamic(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        return this.typeSystemContext.isDynamic(kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isError(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        return this.typeSystemContext.isError(kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isError(TypeConstructorMarker typeConstructorMarker) {
        Intrinsics.checkNotNullParameter(typeConstructorMarker, "<this>");
        return this.typeSystemContext.isError(typeConstructorMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemCommonSuperTypesContext
    public boolean isExtensionFunction(SimpleTypeMarker simpleTypeMarker) {
        Intrinsics.checkNotNullParameter(simpleTypeMarker, "<this>");
        return this.typeSystemContext.isExtensionFunction(simpleTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemInferenceExtensionContext
    public boolean isExtensionFunctionType(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        return this.typeSystemContext.isExtensionFunctionType(kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemInferenceExtensionContext
    public boolean isFinalClassConstructor(TypeConstructorMarker typeConstructorMarker) {
        Intrinsics.checkNotNullParameter(typeConstructorMarker, "<this>");
        return this.typeSystemContext.isFinalClassConstructor(typeConstructorMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isFlexible(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        return this.typeSystemContext.isFlexible(kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isFlexibleNothing(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        return this.typeSystemContext.isFlexibleNothing(kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemInferenceExtensionContext
    public boolean isFunctionOrKFunctionWithAnySuspendability(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        return this.typeSystemContext.isFunctionOrKFunctionWithAnySuspendability(kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isIntegerConstantOperatorTypeConstructor(TypeConstructorMarker typeConstructorMarker) {
        Intrinsics.checkNotNullParameter(typeConstructorMarker, "<this>");
        return this.typeSystemContext.isIntegerConstantOperatorTypeConstructor(typeConstructorMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isIntegerLiteralConstantTypeConstructor(TypeConstructorMarker typeConstructorMarker) {
        Intrinsics.checkNotNullParameter(typeConstructorMarker, "<this>");
        return this.typeSystemContext.isIntegerLiteralConstantTypeConstructor(typeConstructorMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isIntegerLiteralType(SimpleTypeMarker simpleTypeMarker) {
        Intrinsics.checkNotNullParameter(simpleTypeMarker, "<this>");
        return this.typeSystemContext.isIntegerLiteralType(simpleTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isIntegerLiteralTypeConstructor(TypeConstructorMarker typeConstructorMarker) {
        Intrinsics.checkNotNullParameter(typeConstructorMarker, "<this>");
        return this.typeSystemContext.isIntegerLiteralTypeConstructor(typeConstructorMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isInterface(TypeConstructorMarker typeConstructorMarker) {
        Intrinsics.checkNotNullParameter(typeConstructorMarker, "<this>");
        return this.typeSystemContext.isInterface(typeConstructorMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isIntersection(TypeConstructorMarker typeConstructorMarker) {
        Intrinsics.checkNotNullParameter(typeConstructorMarker, "<this>");
        return this.typeSystemContext.isIntersection(typeConstructorMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemInferenceExtensionContext
    public boolean isK2() {
        return this.typeSystemContext.isK2();
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isLocalType(TypeConstructorMarker typeConstructorMarker) {
        Intrinsics.checkNotNullParameter(typeConstructorMarker, "<this>");
        return this.typeSystemContext.isLocalType(typeConstructorMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isMarkedNullable(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        return this.typeSystemContext.isMarkedNullable(kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isMarkedNullable(SimpleTypeMarker simpleTypeMarker) {
        Intrinsics.checkNotNullParameter(simpleTypeMarker, "<this>");
        return this.typeSystemContext.isMarkedNullable(simpleTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    @ObsoleteTypeKind
    public boolean isNotNullTypeParameter(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        return this.typeSystemContext.isNotNullTypeParameter(kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isNothing(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        return this.typeSystemContext.isNothing(kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isNothingConstructor(TypeConstructorMarker typeConstructorMarker) {
        Intrinsics.checkNotNullParameter(typeConstructorMarker, "<this>");
        return this.typeSystemContext.isNothingConstructor(typeConstructorMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isNullableAny(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        return this.typeSystemContext.isNullableAny(kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isNullableNothing(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        return this.typeSystemContext.isNullableNothing(kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isNullableType(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        return this.typeSystemContext.isNullableType(kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isOldCapturedType(CapturedTypeMarker capturedTypeMarker) {
        Intrinsics.checkNotNullParameter(capturedTypeMarker, "<this>");
        return this.typeSystemContext.isOldCapturedType(capturedTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isPrimitiveType(SimpleTypeMarker simpleTypeMarker) {
        Intrinsics.checkNotNullParameter(simpleTypeMarker, "<this>");
        return this.typeSystemContext.isPrimitiveType(simpleTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isProjectionNotNull(CapturedTypeMarker capturedTypeMarker) {
        Intrinsics.checkNotNullParameter(capturedTypeMarker, "<this>");
        return this.typeSystemContext.isProjectionNotNull(capturedTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isRawType(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        return this.typeSystemContext.isRawType(kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemInferenceExtensionContext
    public boolean isSignedOrUnsignedNumberType(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        return this.typeSystemContext.isSignedOrUnsignedNumberType(kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isSimpleType(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        return this.typeSystemContext.isSimpleType(kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isSingleClassifierType(SimpleTypeMarker simpleTypeMarker) {
        Intrinsics.checkNotNullParameter(simpleTypeMarker, "<this>");
        return this.typeSystemContext.isSingleClassifierType(simpleTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemInferenceExtensionContext
    public boolean isSpecial(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        return this.typeSystemContext.isSpecial(kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isStarProjection(TypeArgumentMarker typeArgumentMarker) {
        Intrinsics.checkNotNullParameter(typeArgumentMarker, "<this>");
        return this.typeSystemContext.isStarProjection(typeArgumentMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isStubType(SimpleTypeMarker simpleTypeMarker) {
        Intrinsics.checkNotNullParameter(simpleTypeMarker, "<this>");
        return this.typeSystemContext.isStubType(simpleTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isStubTypeForBuilderInference(SimpleTypeMarker simpleTypeMarker) {
        Intrinsics.checkNotNullParameter(simpleTypeMarker, "<this>");
        return this.typeSystemContext.isStubTypeForBuilderInference(simpleTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isStubTypeForVariableInSubtyping(SimpleTypeMarker simpleTypeMarker) {
        Intrinsics.checkNotNullParameter(simpleTypeMarker, "<this>");
        return this.typeSystemContext.isStubTypeForVariableInSubtyping(simpleTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isTypeParameterTypeConstructor(TypeConstructorMarker typeConstructorMarker) {
        Intrinsics.checkNotNullParameter(typeConstructorMarker, "<this>");
        return this.typeSystemContext.isTypeParameterTypeConstructor(typeConstructorMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemInferenceExtensionContext
    public boolean isTypeVariable(TypeConstructorMarker typeConstructorMarker) {
        Intrinsics.checkNotNullParameter(typeConstructorMarker, "<this>");
        return this.typeSystemContext.isTypeVariable(typeConstructorMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isTypeVariableType(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        return this.typeSystemContext.isTypeVariableType(kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isUninferredParameter(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        return this.typeSystemContext.isUninferredParameter(kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemInferenceExtensionContext
    public boolean isUnit(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        return this.typeSystemContext.isUnit(kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemInferenceExtensionContext
    public boolean isUnitTypeConstructor(TypeConstructorMarker typeConstructorMarker) {
        Intrinsics.checkNotNullParameter(typeConstructorMarker, "<this>");
        return this.typeSystemContext.isUnitTypeConstructor(typeConstructorMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public Iterator<TypeArgumentMarker> iterator(TypeArgumentListMarker typeArgumentListMarker) {
        Intrinsics.checkNotNullParameter(typeArgumentListMarker, "<this>");
        return this.typeSystemContext.iterator(typeArgumentListMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public SimpleTypeMarker lowerBound(FlexibleTypeMarker flexibleTypeMarker) {
        Intrinsics.checkNotNullParameter(flexibleTypeMarker, "<this>");
        return this.typeSystemContext.lowerBound(flexibleTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public SimpleTypeMarker lowerBoundIfFlexible(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        return this.typeSystemContext.lowerBoundIfFlexible(kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public KotlinTypeMarker lowerType(CapturedTypeMarker capturedTypeMarker) {
        Intrinsics.checkNotNullParameter(capturedTypeMarker, "<this>");
        return this.typeSystemContext.lowerType(capturedTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public KotlinTypeMarker makeDefinitelyNotNullOrNotNull(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        return this.typeSystemContext.makeDefinitelyNotNullOrNotNull(kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public SimpleTypeMarker makeSimpleTypeDefinitelyNotNullOrNotNull(SimpleTypeMarker simpleTypeMarker) {
        Intrinsics.checkNotNullParameter(simpleTypeMarker, "<this>");
        return this.typeSystemContext.makeSimpleTypeDefinitelyNotNullOrNotNull(simpleTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeCheckerProviderContext
    public TypeCheckerState newTypeCheckerState(boolean errorTypesEqualToAnything, boolean stubTypesEqualToAnything) {
        return this.typeSystemContext.newTypeCheckerState(errorTypesEqualToAnything, stubTypesEqualToAnything);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemBuiltInsContext
    public SimpleTypeMarker nothingType() {
        return this.typeSystemContext.nothingType();
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemBuiltInsContext
    public SimpleTypeMarker nullableAnyType() {
        return this.typeSystemContext.nullableAnyType();
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemBuiltInsContext
    public SimpleTypeMarker nullableNothingType() {
        return this.typeSystemContext.nullableNothingType();
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public SimpleTypeMarker original(DefinitelyNotNullTypeMarker definitelyNotNullTypeMarker) {
        Intrinsics.checkNotNullParameter(definitelyNotNullTypeMarker, "<this>");
        return this.typeSystemContext.original(definitelyNotNullTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public SimpleTypeMarker originalIfDefinitelyNotNullable(SimpleTypeMarker simpleTypeMarker) {
        Intrinsics.checkNotNullParameter(simpleTypeMarker, "<this>");
        return this.typeSystemContext.originalIfDefinitelyNotNullable(simpleTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public int parametersCount(TypeConstructorMarker typeConstructorMarker) {
        Intrinsics.checkNotNullParameter(typeConstructorMarker, "<this>");
        return this.typeSystemContext.parametersCount(typeConstructorMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public Collection<KotlinTypeMarker> possibleIntegerTypes(SimpleTypeMarker simpleTypeMarker) {
        Intrinsics.checkNotNullParameter(simpleTypeMarker, "<this>");
        return this.typeSystemContext.possibleIntegerTypes(simpleTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public TypeArgumentMarker projection(CapturedTypeConstructorMarker capturedTypeConstructorMarker) {
        Intrinsics.checkNotNullParameter(capturedTypeConstructorMarker, "<this>");
        return this.typeSystemContext.projection(capturedTypeConstructorMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemInferenceExtensionContext
    public KotlinTypeMarker removeAnnotations(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        return this.typeSystemContext.removeAnnotations(kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemInferenceExtensionContext
    public KotlinTypeMarker removeExactAnnotation(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        return this.typeSystemContext.removeExactAnnotation(kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemInferenceExtensionContext
    public KotlinTypeMarker replaceArguments(KotlinTypeMarker kotlinTypeMarker, Function1<? super TypeArgumentMarker, ? extends TypeArgumentMarker> replacement) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        Intrinsics.checkNotNullParameter(replacement, "replacement");
        return this.typeSystemContext.replaceArguments(kotlinTypeMarker, replacement);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemInferenceExtensionContext
    public SimpleTypeMarker replaceArguments(SimpleTypeMarker simpleTypeMarker, List<? extends TypeArgumentMarker> newArguments) {
        Intrinsics.checkNotNullParameter(simpleTypeMarker, "<this>");
        Intrinsics.checkNotNullParameter(newArguments, "newArguments");
        return this.typeSystemContext.replaceArguments(simpleTypeMarker, newArguments);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemInferenceExtensionContext
    public SimpleTypeMarker replaceArguments(SimpleTypeMarker simpleTypeMarker, Function1<? super TypeArgumentMarker, ? extends TypeArgumentMarker> replacement) {
        Intrinsics.checkNotNullParameter(simpleTypeMarker, "<this>");
        Intrinsics.checkNotNullParameter(replacement, "replacement");
        return this.typeSystemContext.replaceArguments(simpleTypeMarker, replacement);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemInferenceExtensionContext
    public KotlinTypeMarker replaceArgumentsDeeply(KotlinTypeMarker kotlinTypeMarker, Function1<? super TypeArgumentMarker, ? extends TypeArgumentMarker> replacement) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        Intrinsics.checkNotNullParameter(replacement, "replacement");
        return this.typeSystemContext.replaceArgumentsDeeply(kotlinTypeMarker, replacement);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemInferenceExtensionContext
    public SimpleTypeMarker replaceArgumentsDeeply(SimpleTypeMarker simpleTypeMarker, Function1<? super TypeArgumentMarker, ? extends TypeArgumentMarker> replacement) {
        Intrinsics.checkNotNullParameter(simpleTypeMarker, "<this>");
        Intrinsics.checkNotNullParameter(replacement, "replacement");
        return this.typeSystemContext.replaceArgumentsDeeply(simpleTypeMarker, replacement);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemCommonSuperTypesContext
    public KotlinTypeMarker replaceCustomAttributes(KotlinTypeMarker kotlinTypeMarker, List<? extends AnnotationMarker> newAttributes) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        Intrinsics.checkNotNullParameter(newAttributes, "newAttributes");
        return this.typeSystemContext.replaceCustomAttributes(kotlinTypeMarker, newAttributes);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public TypeArgumentMarker replaceType(TypeArgumentMarker typeArgumentMarker, KotlinTypeMarker newType) {
        Intrinsics.checkNotNullParameter(typeArgumentMarker, "<this>");
        Intrinsics.checkNotNullParameter(newType, "newType");
        return this.typeSystemContext.replaceType(typeArgumentMarker, newType);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public KotlinTypeMarker safeSubstitute(TypeSubstitutorMarker typeSubstitutorMarker, KotlinTypeMarker type) {
        Intrinsics.checkNotNullParameter(typeSubstitutorMarker, "<this>");
        Intrinsics.checkNotNullParameter(type, "type");
        return this.typeSystemContext.safeSubstitute(typeSubstitutorMarker, type);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemInferenceExtensionContext
    public KotlinTypeMarker singleBestRepresentative(Collection<? extends KotlinTypeMarker> collection) {
        Intrinsics.checkNotNullParameter(collection, "<this>");
        return this.typeSystemContext.singleBestRepresentative(collection);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public int size(TypeArgumentListMarker typeArgumentListMarker) {
        Intrinsics.checkNotNullParameter(typeArgumentListMarker, "<this>");
        return this.typeSystemContext.size(typeArgumentListMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public TypeCheckerState.SupertypesPolicy substitutionSupertypePolicy(SimpleTypeMarker type) {
        Intrinsics.checkNotNullParameter(type, "type");
        return this.typeSystemContext.substitutionSupertypePolicy(type);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public Collection<KotlinTypeMarker> supertypes(TypeConstructorMarker typeConstructorMarker) {
        Intrinsics.checkNotNullParameter(typeConstructorMarker, "<this>");
        return this.typeSystemContext.supertypes(typeConstructorMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemCommonSuperTypesContext
    public SimpleTypeMarker toErrorType(TypeConstructorMarker typeConstructorMarker) {
        Intrinsics.checkNotNullParameter(typeConstructorMarker, "<this>");
        return this.typeSystemContext.toErrorType(typeConstructorMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public CapturedTypeConstructorMarker typeConstructor(CapturedTypeMarker capturedTypeMarker) {
        Intrinsics.checkNotNullParameter(capturedTypeMarker, "<this>");
        return this.typeSystemContext.typeConstructor(capturedTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public TypeConstructorMarker typeConstructor(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        return this.typeSystemContext.typeConstructor(kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public TypeConstructorMarker typeConstructor(SimpleTypeMarker simpleTypeMarker) {
        Intrinsics.checkNotNullParameter(simpleTypeMarker, "<this>");
        return this.typeSystemContext.typeConstructor(simpleTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemInferenceExtensionContext
    public TypeArgumentMarker typeConstructorProjection(CapturedTypeMarker capturedTypeMarker) {
        Intrinsics.checkNotNullParameter(capturedTypeMarker, "<this>");
        return this.typeSystemContext.typeConstructorProjection(capturedTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemCommonSuperTypesContext
    public int typeDepth(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        return this.typeSystemContext.typeDepth(kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemCommonSuperTypesContext
    public int typeDepth(SimpleTypeMarker simpleTypeMarker) {
        Intrinsics.checkNotNullParameter(simpleTypeMarker, "<this>");
        return this.typeSystemContext.typeDepth(simpleTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemInferenceExtensionContext
    public TypeParameterMarker typeParameter(CapturedTypeMarker capturedTypeMarker) {
        Intrinsics.checkNotNullParameter(capturedTypeMarker, "<this>");
        return this.typeSystemContext.typeParameter(capturedTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public TypeSubstitutorMarker typeSubstitutorByTypeConstructor(Map<TypeConstructorMarker, ? extends KotlinTypeMarker> map) {
        Intrinsics.checkNotNullParameter(map, "map");
        return this.typeSystemContext.typeSubstitutorByTypeConstructor(map);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemCommonSuperTypesContext
    public List<AnnotationMarker> unionTypeAttributes(List<? extends KotlinTypeMarker> types) {
        Intrinsics.checkNotNullParameter(types, "types");
        return this.typeSystemContext.unionTypeAttributes(types);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public TypeConstructorMarker unwrapStubTypeVariableConstructor(TypeConstructorMarker typeConstructorMarker) {
        Intrinsics.checkNotNullParameter(typeConstructorMarker, "<this>");
        return this.typeSystemContext.unwrapStubTypeVariableConstructor(typeConstructorMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public SimpleTypeMarker upperBound(FlexibleTypeMarker flexibleTypeMarker) {
        Intrinsics.checkNotNullParameter(flexibleTypeMarker, "<this>");
        return this.typeSystemContext.upperBound(flexibleTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public int upperBoundCount(TypeParameterMarker typeParameterMarker) {
        Intrinsics.checkNotNullParameter(typeParameterMarker, "<this>");
        return this.typeSystemContext.upperBoundCount(typeParameterMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public SimpleTypeMarker upperBoundIfFlexible(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        return this.typeSystemContext.upperBoundIfFlexible(kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemInferenceExtensionContext
    public boolean useRefinedBoundsForTypeVariableInFlexiblePosition() {
        return this.typeSystemContext.useRefinedBoundsForTypeVariableInFlexiblePosition();
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemInferenceExtensionContext
    public KotlinTypeMarker withNotNullProjection(CapturedTypeMarker capturedTypeMarker) {
        Intrinsics.checkNotNullParameter(capturedTypeMarker, "<this>");
        return this.typeSystemContext.withNotNullProjection(capturedTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public KotlinTypeMarker withNullability(KotlinTypeMarker kotlinTypeMarker, boolean z) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        return this.typeSystemContext.withNullability(kotlinTypeMarker, z);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public SimpleTypeMarker withNullability(SimpleTypeMarker simpleTypeMarker, boolean z) {
        Intrinsics.checkNotNullParameter(simpleTypeMarker, "<this>");
        return this.typeSystemContext.withNullability(simpleTypeMarker, z);
    }

    public final TypeSystemInferenceExtensionContext getTypeSystemContext() {
        return this.typeSystemContext;
    }

    public NewConstraintSystemImpl(ConstraintInjector constraintInjector, TypeSystemInferenceExtensionContext typeSystemContext, LanguageVersionSettings languageVersionSettings) {
        Intrinsics.checkNotNullParameter(constraintInjector, "constraintInjector");
        Intrinsics.checkNotNullParameter(typeSystemContext, "typeSystemContext");
        Intrinsics.checkNotNullParameter(languageVersionSettings, "languageVersionSettings");
        this.constraintInjector = constraintInjector;
        this.typeSystemContext = typeSystemContext;
        this.languageVersionSettings = languageVersionSettings;
        this.utilContext = constraintInjector.getConstraintIncorporator().getUtilContext();
        this.postponedComputationsAfterAllVariablesAreFixed = new ArrayList();
        this.storage = new MutableConstraintStorage();
        this.state = State.BUILDING;
        this.typeVariablesTransaction = new SmartList();
        this.properTypesCache = SmartSet.INSTANCE.create();
        this.notProperTypesCache = SmartSet.INSTANCE.create();
        this.intersectionTypesCache = new LinkedHashMap();
    }

    @Override // org.jetbrains.kotlin.resolve.calls.inference.components.ConstraintInjector.Context
    public boolean getAtCompletionState() {
        return this.atCompletionState;
    }

    public void setAtCompletionState(boolean z) {
        this.atCompletionState = z;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void checkState(State a) {
        if (AbstractTypeChecker.RUN_SLOW_ASSERTIONS) {
            checkState(a);
        }
    }

    private final void checkState(State a, State b) {
        if (AbstractTypeChecker.RUN_SLOW_ASSERTIONS) {
            checkState(a, b);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void checkState(State a, State b, State c) {
        if (AbstractTypeChecker.RUN_SLOW_ASSERTIONS) {
            checkState(a, b, c);
        }
    }

    private final void checkState(State a, State b, State c, State d) {
        if (AbstractTypeChecker.RUN_SLOW_ASSERTIONS) {
            checkState(a, b, c, d);
        }
    }

    private final void checkState(State... allowedState) {
        if (AbstractTypeChecker.RUN_SLOW_ASSERTIONS) {
            ArraysKt.contains(allowedState, this.state);
        }
    }

    @Override // org.jetbrains.kotlin.resolve.calls.inference.ConstraintSystemOperation, org.jetbrains.kotlin.resolve.calls.inference.NewConstraintSystem
    public List<ConstraintSystemError> getErrors() {
        return this.storage.getErrors();
    }

    @Override // org.jetbrains.kotlin.resolve.calls.inference.components.ConstraintSystemCompletionContext, org.jetbrains.kotlin.resolve.calls.components.PostponedArgumentsAnalyzerContext, org.jetbrains.kotlin.resolve.calls.inference.NewConstraintSystem
    public NewConstraintSystemImpl getBuilder() {
        checkState(State.BUILDING, State.COMPLETION, State.TRANSACTION);
        return this;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.inference.NewConstraintSystem
    public ConstraintStorage asReadOnlyStorage() {
        checkState(State.BUILDING, State.FREEZED);
        this.state = State.FREEZED;
        return this.storage;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.inference.components.ConstraintInjector.Context
    public void addMissedConstraints(IncorporationConstraintPosition position, List<Pair<TypeVariableMarker, Constraint>> constraints) {
        Intrinsics.checkNotNullParameter(position, "position");
        Intrinsics.checkNotNullParameter(constraints, "constraints");
        this.storage.getMissedConstraints().add(TuplesKt.to(position, constraints));
    }

    @Override // org.jetbrains.kotlin.resolve.calls.inference.NewConstraintSystem
    public NewConstraintSystemImpl asConstraintSystemCompleterContext() {
        checkState(State.BUILDING);
        setAtCompletionState(true);
        return this;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.inference.NewConstraintSystem
    public NewConstraintSystemImpl asPostponedArgumentsAnalyzerContext() {
        checkState(State.BUILDING);
        return this;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.inference.ConstraintSystemOperation
    public void registerVariable(TypeVariableMarker variable) {
        Intrinsics.checkNotNullParameter(variable, "variable");
        checkState(State.BUILDING, State.COMPLETION, State.TRANSACTION);
        transactionRegisterVariable(variable);
        TypeVariableMarker typeVariableMarkerPut = this.storage.getAllTypeVariables().put(freshTypeConstructor(variable), variable);
        if (typeVariableMarkerPut == null) {
            this.notProperTypesCache.clear();
            this.storage.getNotFixedTypeVariables().put(freshTypeConstructor(variable), new MutableVariableWithConstraints(this, variable));
            return;
        }
        throw new IllegalStateException(("Type variable already registered: old: " + typeVariableMarkerPut + ", new: " + variable).toString());
    }

    @Override // org.jetbrains.kotlin.resolve.calls.inference.ConstraintSystemOperation
    public void markPostponedVariable(TypeVariableMarker variable) {
        Intrinsics.checkNotNullParameter(variable, "variable");
        this.storage.getPostponedTypeVariables().add(variable);
    }

    @Override // org.jetbrains.kotlin.resolve.calls.inference.ConstraintSystemOperation
    public void markCouldBeResolvedWithUnrestrictedBuilderInference() {
        this.couldBeResolvedWithUnrestrictedBuilderInference = true;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.inference.components.ConstraintSystemCompletionContext
    /* JADX INFO: renamed from: couldBeResolvedWithUnrestrictedBuilderInference, reason: from getter */
    public boolean getCouldBeResolvedWithUnrestrictedBuilderInference() {
        return this.couldBeResolvedWithUnrestrictedBuilderInference;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.inference.ConstraintSystemOperation
    public void unmarkPostponedVariable(TypeVariableMarker variable) {
        Intrinsics.checkNotNullParameter(variable, "variable");
        this.storage.getPostponedTypeVariables().remove(variable);
    }

    @Override // org.jetbrains.kotlin.resolve.calls.inference.ConstraintSystemOperation
    public void removePostponedVariables() {
        this.storage.getPostponedTypeVariables().clear();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final KotlinTypeMarker substituteFixedVariables$lambda$5(Function2 tmp0, Object obj, Object obj2) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        return (KotlinTypeMarker) tmp0.invoke(obj, obj2);
    }

    @Override // org.jetbrains.kotlin.resolve.calls.inference.ConstraintSystemOperation
    public void substituteFixedVariables(final TypeSubstitutorMarker substitutor) {
        Intrinsics.checkNotNullParameter(substitutor, "substitutor");
        Map<TypeConstructorMarker, KotlinTypeMarker> fixedTypeVariables = this.storage.getFixedTypeVariables();
        final Function2<TypeConstructorMarker, KotlinTypeMarker, KotlinTypeMarker> function2 = new Function2<TypeConstructorMarker, KotlinTypeMarker, KotlinTypeMarker>() { // from class: org.jetbrains.kotlin.resolve.calls.inference.model.NewConstraintSystemImpl.substituteFixedVariables.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(2);
            }

            @Override // kotlin.jvm.functions.Function2
            public final KotlinTypeMarker invoke(TypeConstructorMarker typeConstructorMarker, KotlinTypeMarker type) {
                Intrinsics.checkNotNullParameter(typeConstructorMarker, "<anonymous parameter 0>");
                Intrinsics.checkNotNullParameter(type, "type");
                return NewConstraintSystemImpl.this.safeSubstitute(substitutor, type);
            }
        };
        fixedTypeVariables.replaceAll(new BiFunction() { // from class: org.jetbrains.kotlin.resolve.calls.inference.model.NewConstraintSystemImpl$$ExternalSyntheticLambda1
            @Override // java.util.function.BiFunction
            public final Object apply(Object obj, Object obj2) {
                return NewConstraintSystemImpl.substituteFixedVariables$lambda$5(function2, obj, obj2);
            }
        });
    }

    @Override // org.jetbrains.kotlin.resolve.calls.inference.ConstraintSystemOperation
    public void putBuiltFunctionalExpectedTypeForPostponedArgument(TypeConstructorMarker topLevelVariable, List<? extends Pair<? extends TypeConstructorMarker, Integer>> pathToExpectedType, KotlinTypeMarker builtFunctionalType) {
        Intrinsics.checkNotNullParameter(topLevelVariable, "topLevelVariable");
        Intrinsics.checkNotNullParameter(pathToExpectedType, "pathToExpectedType");
        Intrinsics.checkNotNullParameter(builtFunctionalType, "builtFunctionalType");
        this.storage.getBuiltFunctionalTypesForPostponedArgumentsByTopLevelTypeVariables().put(TuplesKt.to(topLevelVariable, pathToExpectedType), builtFunctionalType);
    }

    @Override // org.jetbrains.kotlin.resolve.calls.inference.ConstraintSystemOperation
    public void putBuiltFunctionalExpectedTypeForPostponedArgument(TypeConstructorMarker expectedTypeVariable, KotlinTypeMarker builtFunctionalType) {
        Intrinsics.checkNotNullParameter(expectedTypeVariable, "expectedTypeVariable");
        Intrinsics.checkNotNullParameter(builtFunctionalType, "builtFunctionalType");
        this.storage.getBuiltFunctionalTypesForPostponedArgumentsByExpectedTypeVariables().put(expectedTypeVariable, builtFunctionalType);
    }

    @Override // org.jetbrains.kotlin.resolve.calls.inference.ConstraintSystemOperation
    public KotlinTypeMarker getBuiltFunctionalExpectedTypeForPostponedArgument(TypeConstructorMarker topLevelVariable, List<? extends Pair<? extends TypeConstructorMarker, Integer>> pathToExpectedType) {
        Intrinsics.checkNotNullParameter(topLevelVariable, "topLevelVariable");
        Intrinsics.checkNotNullParameter(pathToExpectedType, "pathToExpectedType");
        return this.storage.getBuiltFunctionalTypesForPostponedArgumentsByTopLevelTypeVariables().get(TuplesKt.to(topLevelVariable, pathToExpectedType));
    }

    @Override // org.jetbrains.kotlin.resolve.calls.inference.ConstraintSystemOperation
    public KotlinTypeMarker getBuiltFunctionalExpectedTypeForPostponedArgument(TypeConstructorMarker expectedTypeVariable) {
        Intrinsics.checkNotNullParameter(expectedTypeVariable, "expectedTypeVariable");
        return this.storage.getBuiltFunctionalTypesForPostponedArgumentsByExpectedTypeVariables().get(expectedTypeVariable);
    }

    @Override // org.jetbrains.kotlin.resolve.calls.inference.ConstraintSystemOperation
    public void addSubtypeConstraint(KotlinTypeMarker lowerType, KotlinTypeMarker upperType, ConstraintPosition position) {
        Intrinsics.checkNotNullParameter(lowerType, "lowerType");
        Intrinsics.checkNotNullParameter(upperType, "upperType");
        Intrinsics.checkNotNullParameter(position, "position");
        ConstraintInjector constraintInjector = this.constraintInjector;
        checkState(State.BUILDING, State.COMPLETION, State.TRANSACTION);
        constraintInjector.addInitialSubtypeConstraint(this, lowerType, upperType, position);
    }

    @Override // org.jetbrains.kotlin.resolve.calls.inference.ConstraintSystemOperation
    public void addEqualityConstraint(KotlinTypeMarker a, KotlinTypeMarker b, ConstraintPosition position) {
        Intrinsics.checkNotNullParameter(a, "a");
        Intrinsics.checkNotNullParameter(b, "b");
        Intrinsics.checkNotNullParameter(position, "position");
        ConstraintInjector constraintInjector = this.constraintInjector;
        checkState(State.BUILDING, State.COMPLETION, State.TRANSACTION);
        constraintInjector.addInitialEqualityConstraint(this, a, b, position);
    }

    @Override // org.jetbrains.kotlin.resolve.calls.inference.ConstraintSystemOperation
    public List<TypeConstructorMarker> getProperSuperTypeConstructors(KotlinTypeMarker type) {
        Intrinsics.checkNotNullParameter(type, "type");
        checkState(State.BUILDING, State.COMPLETION, State.TRANSACTION);
        MutableVariableWithConstraints mutableVariableWithConstraints = getNotFixedTypeVariables().get(typeConstructor(type));
        if (mutableVariableWithConstraints == null) {
            return CollectionsKt.listOf(typeConstructor(type));
        }
        List<Constraint> constraints = mutableVariableWithConstraints.getConstraints();
        ArrayList arrayList = new ArrayList();
        for (Constraint constraint : constraints) {
            TypeConstructorMarker typeConstructorMarker = null;
            if (constraint.getKind() != ConstraintKind.LOWER) {
                TypeConstructorMarker typeConstructorMarkerTypeConstructor = typeConstructor(constraint.getType());
                if (!getAllTypeVariables().containsKey(typeConstructorMarkerTypeConstructor)) {
                    typeConstructorMarker = typeConstructorMarkerTypeConstructor;
                }
            }
            if (typeConstructorMarker != null) {
                arrayList.add(typeConstructorMarker);
            }
        }
        return arrayList;
    }

    private final void transactionRegisterVariable(TypeVariableMarker variable) {
        if (this.state != State.TRANSACTION) {
            return;
        }
        this.typeVariablesTransaction.add(variable);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void closeTransaction(State beforeState, int beforeTypeVariables) {
        checkState(State.TRANSACTION);
        AddToStdlibKt.trimToSize(this.typeVariablesTransaction, beforeTypeVariables);
        this.state = beforeState;
    }

    /* JADX INFO: compiled from: NewConstraintSystemImpl.kt */
    @Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0002\b\u0082\u0004\u0018\u00002\u00020\u0001BQ\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0005\u0012\u0006\u0010\u0007\u001a\u00020\u0005\u0012\u0006\u0010\b\u001a\u00020\u0005\u0012\u0006\u0010\t\u001a\u00020\u0005\u0012\u0012\u0010\n\u001a\u000e\u0012\u0004\u0012\u00020\f\u0012\u0004\u0012\u00020\u00050\u000b\u0012\u0006\u0010\r\u001a\u00020\u0005¢\u0006\u0002\u0010\u000eJ\b\u0010\u000f\u001a\u00020\u0010H\u0016J\b\u0010\u0011\u001a\u00020\u0010H\u0016R\u001a\u0010\n\u001a\u000e\u0012\u0004\u0012\u00020\f\u0012\u0004\u0012\u00020\u00050\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0012"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/inference/model/NewConstraintSystemImpl$TransactionState;", "Lorg/jetbrains/kotlin/resolve/calls/inference/ConstraintSystemTransaction;", "beforeState", "Lorg/jetbrains/kotlin/resolve/calls/inference/model/NewConstraintSystemImpl$State;", "beforeInitialConstraintCount", "", "beforeErrorsCount", "beforeMaxTypeDepthFromInitialConstraints", "beforeTypeVariablesTransactionSize", "beforeMissedConstraintsCount", "beforeConstraintCountByVariables", "", "Lorg/jetbrains/kotlin/types/model/TypeConstructorMarker;", "beforeConstraintsFromAllForks", "(Lorg/jetbrains/kotlin/resolve/calls/inference/model/NewConstraintSystemImpl;Lorg/jetbrains/kotlin/resolve/calls/inference/model/NewConstraintSystemImpl$State;IIIIILjava/util/Map;I)V", "closeTransaction", "", "rollbackTransaction", "resolution.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    private final class TransactionState extends ConstraintSystemTransaction {
        private final Map<TypeConstructorMarker, Integer> beforeConstraintCountByVariables;
        private final int beforeConstraintsFromAllForks;
        private final int beforeErrorsCount;
        private final int beforeInitialConstraintCount;
        private final int beforeMaxTypeDepthFromInitialConstraints;
        private final int beforeMissedConstraintsCount;
        private final State beforeState;
        private final int beforeTypeVariablesTransactionSize;
        final /* synthetic */ NewConstraintSystemImpl this$0;

        public TransactionState(NewConstraintSystemImpl newConstraintSystemImpl, State beforeState, int i, int i2, int i3, int i4, int i5, Map<TypeConstructorMarker, Integer> beforeConstraintCountByVariables, int i6) {
            Intrinsics.checkNotNullParameter(beforeState, "beforeState");
            Intrinsics.checkNotNullParameter(beforeConstraintCountByVariables, "beforeConstraintCountByVariables");
            this.this$0 = newConstraintSystemImpl;
            this.beforeState = beforeState;
            this.beforeInitialConstraintCount = i;
            this.beforeErrorsCount = i2;
            this.beforeMaxTypeDepthFromInitialConstraints = i3;
            this.beforeTypeVariablesTransactionSize = i4;
            this.beforeMissedConstraintsCount = i5;
            this.beforeConstraintCountByVariables = beforeConstraintCountByVariables;
            this.beforeConstraintsFromAllForks = i6;
        }

        @Override // org.jetbrains.kotlin.resolve.calls.inference.ConstraintSystemTransaction
        public void closeTransaction() {
            this.this$0.checkState(State.TRANSACTION);
            AddToStdlibKt.trimToSize(this.this$0.typeVariablesTransaction, this.beforeTypeVariablesTransactionSize);
            this.this$0.state = this.beforeState;
        }

        @Override // org.jetbrains.kotlin.resolve.calls.inference.ConstraintSystemTransaction
        public void rollbackTransaction() {
            for (TypeVariableMarker typeVariableMarker : this.this$0.typeVariablesTransaction.subList(this.beforeTypeVariablesTransactionSize, this.this$0.typeVariablesTransaction.size())) {
                this.this$0.storage.getAllTypeVariables().remove(this.this$0.freshTypeConstructor(typeVariableMarker));
                this.this$0.storage.getNotFixedTypeVariables().remove(this.this$0.freshTypeConstructor(typeVariableMarker));
            }
            this.this$0.storage.setMaxTypeDepthFromInitialConstraints(this.beforeMaxTypeDepthFromInitialConstraints);
            AddToStdlibKt.trimToSize(this.this$0.storage.getErrors(), this.beforeErrorsCount);
            AddToStdlibKt.trimToSize(this.this$0.storage.getMissedConstraints(), this.beforeMissedConstraintsCount);
            AddToStdlibKt.trimToSize(this.this$0.storage.getConstraintsFromAllForkPoints(), this.beforeConstraintsFromAllForks);
            List<InitialConstraint> listSubList = this.this$0.storage.getInitialConstraints().subList(this.beforeInitialConstraintCount, this.this$0.storage.getInitialConstraints().size());
            for (MutableVariableWithConstraints mutableVariableWithConstraints : this.this$0.storage.getNotFixedTypeVariables().values()) {
                Integer num = this.beforeConstraintCountByVariables.get(this.this$0.freshTypeConstructor(mutableVariableWithConstraints.getTypeVariable()));
                if (num != null) {
                    mutableVariableWithConstraints.removeLastConstraints$resolution_common(num.intValue());
                }
            }
            listSubList.clear();
            this.this$0.closeTransaction(this.beforeState, this.beforeTypeVariablesTransactionSize);
        }
    }

    @Override // org.jetbrains.kotlin.resolve.calls.inference.ConstraintSystemBuilder
    public ConstraintSystemTransaction prepareTransaction() {
        checkState(State.BUILDING, State.COMPLETION, State.TRANSACTION);
        State state = this.state;
        int size = this.storage.getInitialConstraints().size();
        int size2 = this.storage.getErrors().size();
        int maxTypeDepthFromInitialConstraints = this.storage.getMaxTypeDepthFromInitialConstraints();
        int size3 = this.typeVariablesTransaction.size();
        int size4 = this.storage.getMissedConstraints().size();
        Map<TypeConstructorMarker, MutableVariableWithConstraints> notFixedTypeVariables = this.storage.getNotFixedTypeVariables();
        LinkedHashMap linkedHashMap = new LinkedHashMap(MapsKt.mapCapacity(notFixedTypeVariables.size()));
        Iterator<T> it = notFixedTypeVariables.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            linkedHashMap.put(entry.getKey(), Integer.valueOf(((MutableVariableWithConstraints) entry.getValue()).getRawConstraintsCount()));
        }
        TransactionState transactionState = new TransactionState(this, state, size, size2, maxTypeDepthFromInitialConstraints, size3, size4, linkedHashMap, this.storage.getConstraintsFromAllForkPoints().size());
        this.state = State.TRANSACTION;
        return transactionState;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.inference.ConstraintSystemOperation, org.jetbrains.kotlin.resolve.calls.inference.NewConstraintSystem
    public boolean getHasContradiction() {
        boolean hasContradiction = this.storage.getHasContradiction();
        checkState(State.FREEZED, State.BUILDING, State.COMPLETION, State.TRANSACTION);
        return hasContradiction;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.components.PostponedArgumentsAnalyzerContext, org.jetbrains.kotlin.resolve.calls.inference.ConstraintSystemOperation
    public void addOtherSystem(ConstraintStorage otherSystem) {
        Intrinsics.checkNotNullParameter(otherSystem, "otherSystem");
        if (!otherSystem.getAllTypeVariables().isEmpty()) {
            Iterator<Map.Entry<TypeConstructorMarker, TypeVariableMarker>> it = otherSystem.getAllTypeVariables().entrySet().iterator();
            while (it.hasNext()) {
                transactionRegisterVariable(it.next().getValue());
            }
            this.storage.getAllTypeVariables().putAll(otherSystem.getAllTypeVariables());
            this.notProperTypesCache.clear();
        }
        for (Map.Entry<TypeConstructorMarker, VariableWithConstraints> entry : otherSystem.getNotFixedTypeVariables().entrySet()) {
            getNotFixedTypeVariables().put(entry.getKey(), new MutableVariableWithConstraints(this, entry.getValue()));
        }
        this.storage.getInitialConstraints().addAll(otherSystem.getInitialConstraints());
        MutableConstraintStorage mutableConstraintStorage = this.storage;
        mutableConstraintStorage.setMaxTypeDepthFromInitialConstraints(Math.max(mutableConstraintStorage.getMaxTypeDepthFromInitialConstraints(), otherSystem.getMaxTypeDepthFromInitialConstraints()));
        this.storage.getErrors().addAll(otherSystem.getErrors());
        this.storage.getFixedTypeVariables().putAll(otherSystem.getFixedTypeVariables());
        this.storage.getPostponedTypeVariables().addAll(otherSystem.getPostponedTypeVariables());
        this.storage.getConstraintsFromAllForkPoints().addAll(otherSystem.getConstraintsFromAllForkPoints());
    }

    @Override // org.jetbrains.kotlin.resolve.calls.inference.components.ResultTypeResolver.Context, org.jetbrains.kotlin.resolve.calls.inference.ConstraintSystemOperation
    public boolean isProperType(KotlinTypeMarker type) {
        Intrinsics.checkNotNullParameter(type, "type");
        checkState(State.BUILDING, State.COMPLETION, State.TRANSACTION);
        if (this.storage.getAllTypeVariables().isEmpty()) {
            return true;
        }
        if (this.notProperTypesCache.contains(type)) {
            return false;
        }
        if (this.properTypesCache.contains(type)) {
            return true;
        }
        boolean zIsProperTypeImpl = isProperTypeImpl(type);
        (zIsProperTypeImpl ? this.properTypesCache : this.notProperTypesCache).add(type);
        return zIsProperTypeImpl;
    }

    private final boolean isProperTypeImpl(KotlinTypeMarker type) {
        return !contains(type, new Function1<KotlinTypeMarker, Boolean>() { // from class: org.jetbrains.kotlin.resolve.calls.inference.model.NewConstraintSystemImpl.isProperTypeImpl.1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final Boolean invoke(KotlinTypeMarker it) {
                Intrinsics.checkNotNullParameter(it, "it");
                SimpleTypeMarker simpleTypeMarkerAsSimpleType = NewConstraintSystemImpl.this.asSimpleType(it);
                CapturedTypeMarker capturedTypeMarkerAsCapturedType = simpleTypeMarkerAsSimpleType != null ? NewConstraintSystemImpl.this.asCapturedType(simpleTypeMarkerAsSimpleType) : null;
                if (capturedTypeMarkerAsCapturedType != null && NewConstraintSystemImpl.this.captureStatus(capturedTypeMarkerAsCapturedType) == CaptureStatus.FROM_EXPRESSION) {
                    TypeArgumentMarker typeArgumentMarkerTypeConstructorProjection = NewConstraintSystemImpl.this.typeConstructorProjection(capturedTypeMarkerAsCapturedType);
                    if (NewConstraintSystemImpl.this.isStarProjection(typeArgumentMarkerTypeConstructorProjection)) {
                        typeArgumentMarkerTypeConstructorProjection = null;
                    }
                    it = typeArgumentMarkerTypeConstructorProjection != null ? NewConstraintSystemImpl.this.getType(typeArgumentMarkerTypeConstructorProjection) : null;
                }
                if (it != null) {
                    return Boolean.valueOf(NewConstraintSystemImpl.this.storage.getAllTypeVariables().containsKey(NewConstraintSystemImpl.this.typeConstructor(it)));
                }
                return false;
            }
        });
    }

    @Override // org.jetbrains.kotlin.resolve.calls.inference.ConstraintSystemOperation
    public boolean isTypeVariable(KotlinTypeMarker type) {
        Intrinsics.checkNotNullParameter(type, "type");
        checkState(State.BUILDING, State.COMPLETION, State.TRANSACTION);
        return getNotFixedTypeVariables().containsKey(typeConstructor(type));
    }

    @Override // org.jetbrains.kotlin.resolve.calls.inference.ConstraintSystemOperation
    public boolean isPostponedTypeVariable(TypeVariableMarker typeVariable) {
        Intrinsics.checkNotNullParameter(typeVariable, "typeVariable");
        checkState(State.BUILDING, State.COMPLETION, State.TRANSACTION);
        return getPostponedTypeVariables().contains(typeVariable);
    }

    @Override // org.jetbrains.kotlin.resolve.calls.inference.components.ConstraintSystemCompletionContext, org.jetbrains.kotlin.resolve.calls.inference.components.ConstraintInjector.Context
    public Map<TypeConstructorMarker, TypeVariableMarker> getAllTypeVariables() {
        checkState(State.BUILDING, State.COMPLETION, State.TRANSACTION);
        return this.storage.getAllTypeVariables();
    }

    @Override // org.jetbrains.kotlin.resolve.calls.inference.components.ConstraintInjector.Context
    public int getMaxTypeDepthFromInitialConstraints() {
        return this.storage.getMaxTypeDepthFromInitialConstraints();
    }

    @Override // org.jetbrains.kotlin.resolve.calls.inference.components.ConstraintInjector.Context
    public void setMaxTypeDepthFromInitialConstraints(int i) {
        checkState(State.BUILDING, State.COMPLETION, State.TRANSACTION);
        this.storage.setMaxTypeDepthFromInitialConstraints(i);
    }

    @Override // org.jetbrains.kotlin.resolve.calls.inference.components.ConstraintInjector.Context
    public void addInitialConstraint(InitialConstraint initialConstraint) {
        Intrinsics.checkNotNullParameter(initialConstraint, "initialConstraint");
        checkState(State.BUILDING, State.COMPLETION, State.TRANSACTION);
        this.storage.getInitialConstraints().add(initialConstraint);
    }

    @Override // org.jetbrains.kotlin.resolve.calls.inference.components.ConstraintSystemCompletionContext, org.jetbrains.kotlin.resolve.calls.inference.components.VariableFixationFinder.Context
    public Map<TypeConstructorMarker, MutableVariableWithConstraints> getNotFixedTypeVariables() {
        checkState(State.BUILDING, State.COMPLETION, State.TRANSACTION);
        return this.storage.getNotFixedTypeVariables();
    }

    @Override // org.jetbrains.kotlin.resolve.calls.inference.components.ConstraintSystemCompletionContext, org.jetbrains.kotlin.resolve.calls.inference.components.VariableFixationFinder.Context
    public Map<TypeConstructorMarker, KotlinTypeMarker> getFixedTypeVariables() {
        checkState(State.BUILDING, State.COMPLETION, State.TRANSACTION);
        return this.storage.getFixedTypeVariables();
    }

    @Override // org.jetbrains.kotlin.resolve.calls.inference.components.ConstraintSystemCompletionContext, org.jetbrains.kotlin.resolve.calls.inference.components.VariableFixationFinder.Context
    public List<TypeVariableMarker> getPostponedTypeVariables() {
        checkState(State.BUILDING, State.COMPLETION, State.TRANSACTION);
        return this.storage.getPostponedTypeVariables();
    }

    @Override // org.jetbrains.kotlin.resolve.calls.inference.components.VariableFixationFinder.Context, org.jetbrains.kotlin.resolve.calls.inference.components.ConstraintInjector.Context
    public List<Pair<IncorporationConstraintPosition, List<Set<Pair<TypeVariableMarker, Constraint>>>>> getConstraintsFromAllForkPoints() {
        checkState(State.BUILDING, State.COMPLETION, State.TRANSACTION);
        return this.storage.getConstraintsFromAllForkPoints();
    }

    @Override // org.jetbrains.kotlin.resolve.calls.inference.components.ConstraintSystemCompletionContext, org.jetbrains.kotlin.resolve.calls.components.PostponedArgumentsAnalyzerContext, org.jetbrains.kotlin.resolve.calls.inference.NewConstraintSystem, org.jetbrains.kotlin.resolve.calls.inference.components.ConstraintInjector.Context
    public void resolveForkPointsConstraints() {
        if (getConstraintsFromAllForkPoints().isEmpty()) {
            return;
        }
        List<Pair> list = CollectionsKt.toList(getConstraintsFromAllForkPoints());
        getConstraintsFromAllForkPoints().clear();
        for (Pair pair : list) {
            IncorporationConstraintPosition incorporationConstraintPosition = (IncorporationConstraintPosition) pair.component1();
            if (!applyConstraintsFromFirstSuccessfulBranchOfTheFork((List) pair.component2(), incorporationConstraintPosition)) {
                addError(new NoSuccessfulFork(incorporationConstraintPosition));
            }
        }
    }

    public final ConstraintSystemError checkIfForksMightBeSuccessfullyResolved() {
        NoSuccessfulFork noSuccessfulFork = null;
        if (getConstraintsFromAllForkPoints().isEmpty()) {
            return null;
        }
        List<Pair> list = CollectionsKt.toList(getConstraintsFromAllForkPoints());
        getConstraintsFromAllForkPoints().clear();
        ConstraintSystemTransaction constraintSystemTransactionPrepareTransaction = prepareTransaction();
        for (Pair pair : list) {
            IncorporationConstraintPosition incorporationConstraintPosition = (IncorporationConstraintPosition) pair.component1();
            if (!applyConstraintsFromFirstSuccessfulBranchOfTheFork((List) pair.component2(), incorporationConstraintPosition)) {
                noSuccessfulFork = new NoSuccessfulFork(incorporationConstraintPosition);
                break;
            }
        }
        constraintSystemTransactionPrepareTransaction.rollbackTransaction();
        getConstraintsFromAllForkPoints().addAll(list);
        return noSuccessfulFork;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final boolean applyConstraintsFromFirstSuccessfulBranchOfTheFork(List<? extends Set<? extends Pair<? extends TypeVariableMarker, Constraint>>> forkPointData, IncorporationConstraintPosition position) {
        List<? extends Set<? extends Pair<? extends TypeVariableMarker, Constraint>>> list = forkPointData;
        if ((list instanceof Collection) && list.isEmpty()) {
            return false;
        }
        Iterator<T> it = list.iterator();
        while (it.hasNext()) {
            Set set = (Set) it.next();
            NewConstraintSystemImpl newConstraintSystemImpl = this;
            ConstraintSystemTransaction constraintSystemTransactionPrepareTransaction = newConstraintSystemImpl.prepareTransaction();
            NewConstraintSystemImpl newConstraintSystemImpl2 = newConstraintSystemImpl;
            ConstraintInjector constraintInjector = this.constraintInjector;
            checkState(State.BUILDING, State.COMPLETION, State.TRANSACTION);
            constraintInjector.processGivenForkPointBranchConstraints(this, set, position);
            if (!getConstraintsFromAllForkPoints().isEmpty()) {
                resolveForkPointsConstraints();
            }
            if (!newConstraintSystemImpl2.getHasContradiction()) {
                constraintSystemTransactionPrepareTransaction.closeTransaction();
                return true;
            }
            constraintSystemTransactionPrepareTransaction.rollbackTransaction();
        }
        return false;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.inference.components.ConstraintSystemCompletionContext, org.jetbrains.kotlin.resolve.calls.inference.components.ConstraintInjector.Context
    public void addError(ConstraintSystemError error) {
        Intrinsics.checkNotNullParameter(error, "error");
        checkState(State.BUILDING, State.COMPLETION, State.TRANSACTION);
        this.storage.getErrors().add(error);
    }

    @Override // org.jetbrains.kotlin.resolve.calls.inference.components.ConstraintSystemCompletionContext
    public void fixVariable(TypeVariableMarker variable, KotlinTypeMarker resultType, FixVariableConstraintPosition<?> position) {
        Intrinsics.checkNotNullParameter(variable, "variable");
        Intrinsics.checkNotNullParameter(resultType, "resultType");
        Intrinsics.checkNotNullParameter(position, "position");
        ConstraintSystemUtilContext constraintSystemUtilContext = this.utilContext;
        checkState(State.BUILDING, State.COMPLETION);
        checkInferredEmptyIntersection(variable, resultType);
        this.constraintInjector.addInitialEqualityConstraint(this, defaultType(variable), resultType, position);
        checkMissedConstraints();
        final TypeConstructorMarker typeConstructorMarkerFreshTypeConstructor = freshTypeConstructor(variable);
        MutableVariableWithConstraints mutableVariableWithConstraintsRemove = getNotFixedTypeVariables().remove(typeConstructorMarkerFreshTypeConstructor);
        Iterator<MutableVariableWithConstraints> it = getNotFixedTypeVariables().values().iterator();
        while (it.hasNext()) {
            it.next().removeConstrains$resolution_common(new Function1<Constraint, Boolean>() { // from class: org.jetbrains.kotlin.resolve.calls.inference.model.NewConstraintSystemImpl$fixVariable$1$1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public final Boolean invoke(Constraint it2) {
                    Intrinsics.checkNotNullParameter(it2, "it");
                    return Boolean.valueOf(VariableFixationFinderKt.containsTypeVariable(this.this$0, it2.getType(), typeConstructorMarkerFreshTypeConstructor));
                }
            });
        }
        this.storage.getFixedTypeVariables().put(typeConstructorMarkerFreshTypeConstructor, resultType);
        substituteMissedConstraints();
        postponeOnlyInputTypesCheck(constraintSystemUtilContext, mutableVariableWithConstraintsRemove, resultType);
        doPostponedComputationsIfAllVariablesAreFixed();
    }

    @Override // org.jetbrains.kotlin.resolve.calls.inference.NewConstraintSystem
    public EmptyIntersectionTypeInfo getEmptyIntersectionTypeKind(Collection<? extends KotlinTypeMarker> types) {
        Intrinsics.checkNotNullParameter(types, "types");
        if (this.intersectionTypesCache.containsKey(types)) {
            return (EmptyIntersectionTypeInfo) MapsKt.getValue(this.intersectionTypesCache, types);
        }
        EmptyIntersectionTypeInfo emptyIntersectionTypeInfoComputeEmptyIntersectionTypeKind = computeEmptyIntersectionTypeKind(types);
        this.intersectionTypesCache.put(types, emptyIntersectionTypeInfoComputeEmptyIntersectionTypeKind);
        return emptyIntersectionTypeInfoComputeEmptyIntersectionTypeKind;
    }

    private final void checkInferredEmptyIntersection(TypeVariableMarker variable, KotlinTypeMarker resultType) {
        KAnnotatedElement kAnnotatedElement;
        TypeConstructorMarker typeConstructorMarkerTypeConstructor = typeConstructor(resultType);
        if (!(typeConstructorMarkerTypeConstructor instanceof IntersectionTypeConstructorMarker)) {
            typeConstructorMarkerTypeConstructor = null;
        }
        if (typeConstructorMarkerTypeConstructor == null) {
            return;
        }
        Collection<KotlinTypeMarker> collectionSupertypes = supertypes(typeConstructorMarkerTypeConstructor);
        if (collectionSupertypes.size() > 1) {
            List<ConstraintSystemError> errors = this.storage.getErrors();
            if (!(errors instanceof Collection) || !errors.isEmpty()) {
                for (Object obj : errors) {
                    if ((obj instanceof InferredEmptyIntersection) && Intrinsics.areEqual(((InferredEmptyIntersection) obj).getIncompatibleTypes(), collectionSupertypes)) {
                        return;
                    }
                }
            }
            EmptyIntersectionTypeInfo emptyIntersectionTypeKind = getEmptyIntersectionTypeKind(collectionSupertypes);
            if (emptyIntersectionTypeKind == null) {
                return;
            }
            List<ConstraintSystemError> errors2 = this.storage.getErrors();
            final AnonymousClass2 anonymousClass2 = new Function1<ConstraintSystemError, Boolean>() { // from class: org.jetbrains.kotlin.resolve.calls.inference.model.NewConstraintSystemImpl.checkInferredEmptyIntersection.2
                @Override // kotlin.jvm.functions.Function1
                public final Boolean invoke(ConstraintSystemError it) {
                    Intrinsics.checkNotNullParameter(it, "it");
                    return Boolean.valueOf(it instanceof InferredEmptyIntersection);
                }
            };
            errors2.removeIf(new Predicate() { // from class: org.jetbrains.kotlin.resolve.calls.inference.model.NewConstraintSystemImpl$$ExternalSyntheticLambda0
                @Override // java.util.function.Predicate
                public final boolean test(Object obj2) {
                    return NewConstraintSystemImpl.checkInferredEmptyIntersection$lambda$23(anonymousClass2, obj2);
                }
            });
            boolean zSupportsFeature = this.languageVersionSettings.supportsFeature(LanguageFeature.ForbidInferringTypeVariablesIntoEmptyIntersection);
            if (emptyIntersectionTypeKind.getKind().getIsDefinitelyEmpty() && zSupportsFeature) {
                kAnnotatedElement = (KFunction) NewConstraintSystemImpl$checkInferredEmptyIntersection$errorFactory$1.INSTANCE;
            } else {
                kAnnotatedElement = (KFunction) NewConstraintSystemImpl$checkInferredEmptyIntersection$errorFactory$2.INSTANCE;
            }
            addError((ConstraintSystemError) ((Function4) kAnnotatedElement).invoke(CollectionsKt.toList(collectionSupertypes), ArraysKt.toList(emptyIntersectionTypeKind.getCasingTypes()), variable, emptyIntersectionTypeKind.getKind()));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean checkInferredEmptyIntersection$lambda$23(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        return ((Boolean) tmp0.invoke(obj)).booleanValue();
    }

    private final void checkMissedConstraints() {
        List listCreateListBuilder = CollectionsKt.createListBuilder();
        NewConstraintSystemImpl newConstraintSystemImpl = this;
        ConstraintSystemTransaction constraintSystemTransactionPrepareTransaction = newConstraintSystemImpl.prepareTransaction();
        NewConstraintSystemImpl newConstraintSystemImpl2 = newConstraintSystemImpl;
        for (Pair<IncorporationConstraintPosition, List<Pair<TypeVariableMarker, Constraint>>> pair : this.storage.getMissedConstraints()) {
            IncorporationConstraintPosition incorporationConstraintPositionComponent1 = pair.component1();
            List<Pair<TypeVariableMarker, Constraint>> listComponent2 = pair.component2();
            ArrayList arrayList = new ArrayList();
            for (Object obj : listComponent2) {
                if (getNotFixedTypeVariables().containsKey(freshTypeConstructor((TypeVariableMarker) ((Pair) obj).component1()))) {
                    arrayList.add(obj);
                }
            }
            this.constraintInjector.processMissedConstraints(this, incorporationConstraintPositionComponent1, arrayList);
        }
        List<ConstraintSystemError> errors = newConstraintSystemImpl2.getErrors();
        ArrayList arrayList2 = new ArrayList();
        for (Object obj2 : errors) {
            if (obj2 instanceof NewConstraintError) {
                arrayList2.add(obj2);
            }
        }
        Iterator it = arrayList2.iterator();
        while (it.hasNext()) {
            listCreateListBuilder.add((NewConstraintError) it.next());
        }
        constraintSystemTransactionPrepareTransaction.rollbackTransaction();
        List listBuild = CollectionsKt.build(listCreateListBuilder);
        List<ConstraintSystemError> errors2 = getErrors();
        ArrayList arrayList3 = new ArrayList();
        for (Object obj3 : errors2) {
            if (obj3 instanceof NewConstraintError) {
                arrayList3.add(obj3);
            }
        }
        if (arrayList3.isEmpty()) {
            Iterator it2 = listBuild.iterator();
            while (it2.hasNext()) {
                addError(ConstraintPositionAndErrorsKt.transformToWarning((NewConstraintError) it2.next()));
            }
        }
    }

    private final void substituteMissedConstraints() {
        TypeSubstitutorMarker typeSubstitutorMarkerBuildCurrentSubstitutor = buildCurrentSubstitutor();
        Iterator<Pair<IncorporationConstraintPosition, List<Pair<TypeVariableMarker, Constraint>>>> it = this.storage.getMissedConstraints().iterator();
        while (it.hasNext()) {
            List<Pair<TypeVariableMarker, Constraint>> listComponent2 = it.next().component2();
            int i = 0;
            for (Pair<TypeVariableMarker, Constraint> pair : listComponent2) {
                TypeVariableMarker typeVariableMarkerComponent1 = pair.component1();
                Constraint constraintComponent2 = pair.component2();
                listComponent2.set(i, TuplesKt.to(typeVariableMarkerComponent1, ConstraintStorageKt.replaceType(constraintComponent2, safeSubstitute(typeSubstitutorMarkerBuildCurrentSubstitutor, constraintComponent2.getType()))));
                i++;
            }
        }
    }

    private final void postponeOnlyInputTypesCheck(ConstraintSystemUtilContext constraintSystemUtilContext, final MutableVariableWithConstraints mutableVariableWithConstraints, final KotlinTypeMarker kotlinTypeMarker) {
        if (mutableVariableWithConstraints == null || !constraintSystemUtilContext.hasOnlyInputTypesAttribute(mutableVariableWithConstraints.getTypeVariable())) {
            return;
        }
        this.postponedComputationsAfterAllVariablesAreFixed.add(new Function0<Unit>() { // from class: org.jetbrains.kotlin.resolve.calls.inference.model.NewConstraintSystemImpl.postponeOnlyInputTypesCheck.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public /* bridge */ /* synthetic */ Unit invoke() {
                invoke2();
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2() {
                NewConstraintSystemImpl.this.checkOnlyInputTypesAnnotation(mutableVariableWithConstraints, kotlinTypeMarker);
            }
        });
    }

    private final void doPostponedComputationsIfAllVariablesAreFixed() {
        if (getNotFixedTypeVariables().isEmpty()) {
            Iterator<T> it = this.postponedComputationsAfterAllVariablesAreFixed.iterator();
            while (it.hasNext()) {
                ((Function0) it.next()).invoke();
            }
        }
    }

    private final KotlinTypeMarker substituteAndApproximateIfNecessary(KotlinTypeMarker kotlinTypeMarker, TypeSubstitutorMarker typeSubstitutorMarker, AbstractTypeApproximator abstractTypeApproximator, ConstraintKind constraintKind) {
        KotlinTypeMarker kotlinTypeMarkerApproximateToSuperType;
        if (contains(kotlinTypeMarker, new Function1<KotlinTypeMarker, Boolean>() { // from class: org.jetbrains.kotlin.resolve.calls.inference.model.NewConstraintSystemImpl$substituteAndApproximateIfNecessary$doesInputTypeContainsOtherVariables$1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final Boolean invoke(KotlinTypeMarker it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return Boolean.valueOf(this.this$0.typeConstructor(it) instanceof TypeVariableTypeConstructorMarker);
            }
        })) {
            kotlinTypeMarker = safeSubstitute(typeSubstitutorMarker, kotlinTypeMarker);
        }
        int i = WhenMappings.$EnumSwitchMapping$0[constraintKind.ordinal()];
        if (i == 1) {
            kotlinTypeMarkerApproximateToSuperType = abstractTypeApproximator.approximateToSuperType(kotlinTypeMarker, TypeApproximatorConfiguration.InternalTypesApproximation.INSTANCE);
        } else if (i == 2) {
            kotlinTypeMarkerApproximateToSuperType = abstractTypeApproximator.approximateToSubType(kotlinTypeMarker, TypeApproximatorConfiguration.InternalTypesApproximation.INSTANCE);
        } else {
            if (i != 3) {
                throw new NoWhenBranchMatchedException();
            }
            kotlinTypeMarkerApproximateToSuperType = kotlinTypeMarker;
        }
        return kotlinTypeMarkerApproximateToSuperType == null ? kotlinTypeMarker : kotlinTypeMarkerApproximateToSuperType;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void checkOnlyInputTypesAnnotation(MutableVariableWithConstraints variableWithConstraints, KotlinTypeMarker resultType) {
        TypeSubstitutorMarker typeSubstitutorMarkerBuildCurrentSubstitutor = buildCurrentSubstitutor();
        AbstractTypeApproximator typeApproximator = this.constraintInjector.getTypeApproximator();
        Collection<Pair<KotlinTypeMarker, ConstraintKind>> projectedInputCallTypes = variableWithConstraints.getProjectedInputCallTypes(this.utilContext);
        if (!(projectedInputCallTypes instanceof Collection) || !projectedInputCallTypes.isEmpty()) {
            Iterator<T> it = projectedInputCallTypes.iterator();
            while (it.hasNext()) {
                Pair pair = (Pair) it.next();
                KotlinTypeMarker kotlinTypeMarker = (KotlinTypeMarker) pair.component1();
                ConstraintKind constraintKind = (ConstraintKind) pair.component2();
                TypeConstructorMarker typeConstructorMarkerTypeConstructor = typeConstructor(kotlinTypeMarker);
                KotlinTypeMarker kotlinTypeMarkerSubstituteAndApproximateIfNecessary = substituteAndApproximateIfNecessary(kotlinTypeMarker, typeSubstitutorMarkerBuildCurrentSubstitutor, typeApproximator, constraintKind);
                NewConstraintSystemImpl newConstraintSystemImpl = this;
                if (AbstractTypeChecker.equalTypes$default(AbstractTypeChecker.INSTANCE, newConstraintSystemImpl, resultType, kotlinTypeMarkerSubstituteAndApproximateIfNecessary, false, 8, null)) {
                    return;
                }
                if (isIntersection(typeConstructorMarkerTypeConstructor)) {
                    Collection<KotlinTypeMarker> collectionSupertypes = supertypes(typeConstructorMarkerTypeConstructor);
                    if (!(collectionSupertypes instanceof Collection) || !collectionSupertypes.isEmpty()) {
                        Iterator<T> it2 = collectionSupertypes.iterator();
                        while (it2.hasNext()) {
                            if (AbstractTypeChecker.equalTypes$default(AbstractTypeChecker.INSTANCE, newConstraintSystemImpl, resultType, substituteAndApproximateIfNecessary((KotlinTypeMarker) it2.next(), typeSubstitutorMarkerBuildCurrentSubstitutor, typeApproximator, constraintKind), false, 8, null)) {
                                return;
                            }
                        }
                    }
                }
            }
        }
        addError(new OnlyInputTypesDiagnostic(variableWithConstraints.getTypeVariable()));
    }

    @Override // org.jetbrains.kotlin.resolve.calls.inference.components.ConstraintSystemCompletionContext, org.jetbrains.kotlin.resolve.calls.components.PostponedArgumentsAnalyzerContext
    public boolean canBeProper(KotlinTypeMarker type) {
        Intrinsics.checkNotNullParameter(type, "type");
        checkState(State.BUILDING, State.COMPLETION);
        return !contains(type, new Function1<KotlinTypeMarker, Boolean>() { // from class: org.jetbrains.kotlin.resolve.calls.inference.model.NewConstraintSystemImpl.canBeProper.1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final Boolean invoke(KotlinTypeMarker it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return Boolean.valueOf(NewConstraintSystemImpl.this.storage.getNotFixedTypeVariables().containsKey(NewConstraintSystemImpl.this.typeConstructor(it)));
            }
        });
    }

    @Override // org.jetbrains.kotlin.resolve.calls.inference.components.ConstraintSystemCompletionContext
    public boolean containsOnlyFixedOrPostponedVariables(KotlinTypeMarker type) {
        Intrinsics.checkNotNullParameter(type, "type");
        checkState(State.BUILDING, State.COMPLETION);
        return !contains(type, new Function1<KotlinTypeMarker, Boolean>() { // from class: org.jetbrains.kotlin.resolve.calls.inference.model.NewConstraintSystemImpl.containsOnlyFixedOrPostponedVariables.1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final Boolean invoke(KotlinTypeMarker it) {
                Intrinsics.checkNotNullParameter(it, "it");
                TypeConstructorMarker typeConstructorMarkerTypeConstructor = NewConstraintSystemImpl.this.typeConstructor(it);
                MutableVariableWithConstraints mutableVariableWithConstraints = NewConstraintSystemImpl.this.storage.getNotFixedTypeVariables().get(typeConstructorMarkerTypeConstructor);
                return Boolean.valueOf(!CollectionsKt.contains(NewConstraintSystemImpl.this.storage.getPostponedTypeVariables(), mutableVariableWithConstraints != null ? mutableVariableWithConstraints.getTypeVariable() : null) && NewConstraintSystemImpl.this.storage.getNotFixedTypeVariables().containsKey(typeConstructorMarkerTypeConstructor));
            }
        });
    }

    @Override // org.jetbrains.kotlin.resolve.calls.inference.components.ConstraintSystemCompletionContext
    public boolean containsOnlyFixedVariables(KotlinTypeMarker type) {
        Intrinsics.checkNotNullParameter(type, "type");
        checkState(State.BUILDING, State.COMPLETION);
        return !contains(type, new Function1<KotlinTypeMarker, Boolean>() { // from class: org.jetbrains.kotlin.resolve.calls.inference.model.NewConstraintSystemImpl.containsOnlyFixedVariables.1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final Boolean invoke(KotlinTypeMarker it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return Boolean.valueOf(NewConstraintSystemImpl.this.storage.getNotFixedTypeVariables().containsKey(NewConstraintSystemImpl.this.typeConstructor(it)));
            }
        });
    }

    @Override // org.jetbrains.kotlin.resolve.calls.inference.ConstraintSystemBuilder
    public TypeSubstitutorMarker buildCurrentSubstitutor() {
        checkState(State.BUILDING, State.COMPLETION, State.TRANSACTION);
        return buildCurrentSubstitutor(MapsKt.emptyMap());
    }

    @Override // org.jetbrains.kotlin.resolve.calls.components.PostponedArgumentsAnalyzerContext
    public TypeSubstitutorMarker buildCurrentSubstitutor(Map<TypeConstructorMarker, ? extends StubTypeMarker> additionalBindings) {
        Intrinsics.checkNotNullParameter(additionalBindings, "additionalBindings");
        checkState(State.BUILDING, State.COMPLETION, State.TRANSACTION);
        return InferenceUtilsKt.buildCurrentSubstitutor(this.storage, this, additionalBindings);
    }

    @Override // org.jetbrains.kotlin.resolve.calls.inference.components.ResultTypeResolver.Context, org.jetbrains.kotlin.resolve.calls.components.PostponedArgumentsAnalyzerContext
    public TypeSubstitutorMarker buildNotFixedVariablesToStubTypesSubstitutor() {
        checkState(State.BUILDING, State.COMPLETION, State.TRANSACTION);
        return InferenceUtilsKt.buildNotFixedVariablesToNonSubtypableTypesSubstitutor(this.storage, this);
    }

    @Override // org.jetbrains.kotlin.resolve.calls.inference.components.ResultTypeResolver.Context, org.jetbrains.kotlin.resolve.calls.inference.components.VariableFixationFinder.Context
    public boolean isReified(TypeVariableMarker variable) {
        Intrinsics.checkNotNullParameter(variable, "variable");
        return this.utilContext.isReified(variable);
    }

    @Override // org.jetbrains.kotlin.resolve.calls.components.PostponedArgumentsAnalyzerContext
    public Map<TypeVariableMarker, StubTypeMarker> bindingStubsForPostponedVariables() {
        checkState(State.BUILDING, State.COMPLETION);
        List<TypeVariableMarker> postponedTypeVariables = this.storage.getPostponedTypeVariables();
        LinkedHashMap linkedHashMap = new LinkedHashMap(RangesKt.coerceAtLeast(MapsKt.mapCapacity(CollectionsKt.collectionSizeOrDefault(postponedTypeVariables, 10)), 16));
        for (Object obj : postponedTypeVariables) {
            linkedHashMap.put(obj, createStubTypeForBuilderInference((TypeVariableMarker) obj));
        }
        return linkedHashMap;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.inference.ConstraintSystemBuilder
    public ConstraintStorage currentStorage() {
        checkState(State.BUILDING, State.COMPLETION, State.TRANSACTION);
        return this.storage;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.components.PostponedArgumentsAnalyzerContext
    public boolean hasUpperOrEqualUnitConstraint(KotlinTypeMarker type) {
        List<Constraint> constraints;
        Intrinsics.checkNotNullParameter(type, "type");
        checkState(State.BUILDING, State.COMPLETION, State.FREEZED);
        MutableVariableWithConstraints mutableVariableWithConstraints = this.storage.getNotFixedTypeVariables().get(typeConstructor(type));
        if (mutableVariableWithConstraints == null || (constraints = mutableVariableWithConstraints.getConstraints()) == null) {
            return false;
        }
        List<Constraint> list = constraints;
        if ((list instanceof Collection) && list.isEmpty()) {
            return false;
        }
        for (Constraint constraint : list) {
            if (constraint.getKind() == ConstraintKind.UPPER || constraint.getKind() == ConstraintKind.EQUALITY) {
                if (isUnit(lowerBoundIfFlexible(constraint.getType()))) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.components.PostponedArgumentsAnalyzerContext
    public void removePostponedTypeVariablesFromConstraints(final Set<? extends TypeConstructorMarker> postponedTypeVariables) {
        Intrinsics.checkNotNullParameter(postponedTypeVariables, "postponedTypeVariables");
        Iterator<Map.Entry<TypeConstructorMarker, MutableVariableWithConstraints>> it = this.storage.getNotFixedTypeVariables().entrySet().iterator();
        while (it.hasNext()) {
            it.next().getValue().removeConstrains$resolution_common(new Function1<Constraint, Boolean>() { // from class: org.jetbrains.kotlin.resolve.calls.inference.model.NewConstraintSystemImpl.removePostponedTypeVariablesFromConstraints.1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                /* JADX WARN: Multi-variable type inference failed */
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public final Boolean invoke(Constraint constraint) {
                    Intrinsics.checkNotNullParameter(constraint, "constraint");
                    NewConstraintSystemImpl newConstraintSystemImpl = NewConstraintSystemImpl.this;
                    KotlinTypeMarker type = constraint.getType();
                    final Set<TypeConstructorMarker> set = postponedTypeVariables;
                    final NewConstraintSystemImpl newConstraintSystemImpl2 = NewConstraintSystemImpl.this;
                    return Boolean.valueOf(newConstraintSystemImpl.contains(type, new Function1<KotlinTypeMarker, Boolean>() { // from class: org.jetbrains.kotlin.resolve.calls.inference.model.NewConstraintSystemImpl.removePostponedTypeVariablesFromConstraints.1.1
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        /* JADX WARN: Multi-variable type inference failed */
                        {
                            super(1);
                        }

                        @Override // kotlin.jvm.functions.Function1
                        public final Boolean invoke(KotlinTypeMarker it2) {
                            Intrinsics.checkNotNullParameter(it2, "it");
                            return Boolean.valueOf((it2 instanceof StubTypeMarker) && set.contains(newConstraintSystemImpl2.getOriginalTypeVariable((StubTypeMarker) it2)));
                        }
                    }));
                }
            });
        }
    }
}
