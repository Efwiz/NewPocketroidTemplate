package org.jetbrains.kotlin.ir.backend.js;

import com.google.android.gms.cast.MediaTrack;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.backend.common.BodyLoweringPass;
import org.jetbrains.kotlin.backend.common.DeclarationTransformer;
import org.jetbrains.kotlin.backend.common.FileLoweringPass;
import org.jetbrains.kotlin.backend.common.LoggingContext;
import org.jetbrains.kotlin.backend.common.LowerKt;
import org.jetbrains.kotlin.backend.common.lower.ConstEvaluationLowering;
import org.jetbrains.kotlin.backend.common.lower.InlineClassLowering;
import org.jetbrains.kotlin.backend.common.lower.InnerClassConstructorCallsLowering;
import org.jetbrains.kotlin.backend.common.lower.InnerClassesLowering;
import org.jetbrains.kotlin.backend.common.lower.InnerClassesMemberBodyLowering;
import org.jetbrains.kotlin.backend.common.lower.LocalClassPopupLowering;
import org.jetbrains.kotlin.backend.common.lower.LocalDeclarationsLowering;
import org.jetbrains.kotlin.backend.common.lower.LocalDelegatedPropertiesLowering;
import org.jetbrains.kotlin.backend.common.lower.PropertiesLowering;
import org.jetbrains.kotlin.backend.common.lower.StripTypeAliasDeclarationsLowering;
import org.jetbrains.kotlin.backend.common.lower.inline.FunctionInlining;
import org.jetbrains.kotlin.backend.common.lower.inline.LocalClassesExtractionFromInlineFunctionsLowering;
import org.jetbrains.kotlin.backend.common.phaser.AbstractNamedCompilerPhase;
import org.jetbrains.kotlin.backend.common.phaser.CompilerPhase;
import org.jetbrains.kotlin.backend.common.phaser.DumperVerifierKt;
import org.jetbrains.kotlin.backend.common.phaser.NamedCompilerPhase;
import org.jetbrains.kotlin.backend.common.phaser.PhaseBuildersKt;
import org.jetbrains.kotlin.backend.common.phaser.PhaseConfigurationService;
import org.jetbrains.kotlin.backend.common.phaser.PhaserState;
import org.jetbrains.kotlin.backend.common.phaser.SameTypeCompilerPhase;
import org.jetbrains.kotlin.ir.backend.js.lower.AutoboxingTransformer;
import org.jetbrains.kotlin.ir.backend.js.lower.ThrowableLowering;
import org.jetbrains.kotlin.ir.backend.js.lower.cleanup.CleanupLowering;
import org.jetbrains.kotlin.ir.backend.js.lower.inline.JsInlineFunctionResolver;
import org.jetbrains.kotlin.ir.backend.js.lower.inline.RemoveInlineDeclarationsWithReifiedTypeParametersLowering;
import org.jetbrains.kotlin.ir.declarations.IrModuleFragment;
import org.jetbrains.kotlin.ir.interpreter.IrInterpreterConfiguration;
import org.jetbrains.kotlin.platform.js.JsPlatforms;

/* JADX INFO: compiled from: JsLoweringPhases.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\u008c\u0001\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u000f\n\u0002\u0018\u0002\n\u0002\b5\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u001c\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0010\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\"\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u001aF\u0010\u0089\u0001\u001a\u00020\u00012\u0015\u0010\u008a\u0001\u001a\u0010\u0012\u0004\u0012\u00020K\u0012\u0005\u0012\u00030\u008c\u00010\u008b\u00012\b\u0010\u008d\u0001\u001a\u00030\u008e\u00012\b\u0010\u008f\u0001\u001a\u00030\u008e\u00012\u0010\b\u0002\u0010\u0090\u0001\u001a\t\u0012\u0004\u0012\u00020`0\u0091\u0001H\u0002\u001a\u0083\u0001\u0010\u0092\u0001\u001a*\u0012\u0004\u0012\u00020K\u0012\n\u0012\b\u0012\u0004\u0012\u00020M0L0Jj\u0014\u0012\u0004\u0012\u00020K\u0012\n\u0012\b\u0012\u0004\u0012\u00020M0L`N2\u001b\u0010\u0093\u0001\u001a\u0016\u0012\u0004\u0012\u00020K\u0012\u0004\u0012\u00020M\u0012\u0005\u0012\u00030\u0095\u00010\u0094\u00012\b\u0010\u008f\u0001\u001a\u00030\u008e\u00012\b\u0010\u008d\u0001\u001a\u00030\u008e\u00012\u001f\b\u0002\u0010\u0090\u0001\u001a\u0018\u0012\u0013\u0012\u0011\u0012\u0004\u0012\u00020K\u0012\u0002\b\u0003\u0012\u0002\b\u00030\u0096\u00010\u0091\u0001H\u0002\u001aF\u0010\u0097\u0001\u001a\u00020\u00042\u0015\u0010\u008a\u0001\u001a\u0010\u0012\u0004\u0012\u00020K\u0012\u0005\u0012\u00030\u0098\u00010\u008b\u00012\b\u0010\u008d\u0001\u001a\u00030\u008e\u00012\b\u0010\u008f\u0001\u001a\u00030\u008e\u00012\u0010\b\u0002\u0010\u0090\u0001\u001a\t\u0012\u0004\u0012\u00020`0\u0091\u0001H\u0002\u001a}\u0010\u0099\u0001\u001a*\u0012\u0004\u0012\u00020K\u0012\n\u0012\b\u0012\u0004\u0012\u00020M0L0Jj\u0014\u0012\u0004\u0012\u00020K\u0012\n\u0012\b\u0012\u0004\u0012\u00020M0L`N2\u0015\u0010\u008a\u0001\u001a\u0010\u0012\u0004\u0012\u00020K\u0012\u0005\u0012\u00030\u009a\u00010\u008b\u00012\b\u0010\u008d\u0001\u001a\u00030\u008e\u00012\b\u0010\u008f\u0001\u001a\u00030\u008e\u00012\u001f\b\u0002\u0010\u0090\u0001\u001a\u0018\u0012\u0013\u0012\u0011\u0012\u0004\u0012\u00020K\u0012\u0002\b\u0003\u0012\u0002\b\u00030\u0096\u00010\u0091\u0001H\u0002\u001a\u0018\u0010\u009b\u0001\u001a\u00030\u0095\u0001*\u00030\u009c\u00012\u0007\u0010\u009d\u0001\u001a\u00020MH\u0002\u001a\u001f\u0010\u009b\u0001\u001a\u00030\u0095\u0001*\u00030\u009e\u00012\u000e\u0010\u009f\u0001\u001a\t\u0012\u0005\u0012\u00030 \u00010LH\u0002\u001a2\u0010¡\u0001\u001a!\u0012\u0004\u0012\u00020K\u0012\n\u0012\b\u0012\u0004\u0012\u00020M0L\u0012\n\u0012\b\u0012\u0004\u0012\u00020M0L0¢\u0001*\b\u0012\u0004\u0012\u00020`0_H\u0002\u001a3\u0010£\u0001\u001a\u00020\u0014**\u0012\u0004\u0012\u00020K\u0012\n\u0012\b\u0012\u0004\u0012\u00020M0L0Jj\u0014\u0012\u0004\u0012\u00020K\u0012\n\u0012\b\u0012\u0004\u0012\u00020M0L`N\"\u000e\u0010\u0000\u001a\u00020\u0001X\u0082\u0004¢\u0006\u0002\n\u0000\"\u000e\u0010\u0002\u001a\u00020\u0001X\u0082\u0004¢\u0006\u0002\n\u0000\"\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000\"\u000e\u0010\u0005\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000\"\u000e\u0010\u0006\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000\"\u000e\u0010\u0007\u001a\u00020\u0001X\u0082\u0004¢\u0006\u0002\n\u0000\"\u000e\u0010\b\u001a\u00020\u0001X\u0082\u0004¢\u0006\u0002\n\u0000\"\u000e\u0010\t\u001a\u00020\u0001X\u0082\u0004¢\u0006\u0002\n\u0000\"\u000e\u0010\n\u001a\u00020\u0001X\u0082\u0004¢\u0006\u0002\n\u0000\"\u000e\u0010\u000b\u001a\u00020\u0001X\u0082\u0004¢\u0006\u0002\n\u0000\"\u000e\u0010\f\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000\"\u000e\u0010\r\u001a\u00020\u0001X\u0082\u0004¢\u0006\u0002\n\u0000\"\u000e\u0010\u000e\u001a\u00020\u0001X\u0082\u0004¢\u0006\u0002\n\u0000\"\u000e\u0010\u000f\u001a\u00020\u0001X\u0082\u0004¢\u0006\u0002\n\u0000\"\u000e\u0010\u0010\u001a\u00020\u0001X\u0082\u0004¢\u0006\u0002\n\u0000\"\u000e\u0010\u0011\u001a\u00020\u0001X\u0082\u0004¢\u0006\u0002\n\u0000\"\u000e\u0010\u0012\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000\"\u000e\u0010\u0013\u001a\u00020\u0014X\u0082\u0004¢\u0006\u0002\n\u0000\"\u0011\u0010\u0015\u001a\u00020\u0014¢\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0017\"\u000e\u0010\u0018\u001a\u00020\u0001X\u0082\u0004¢\u0006\u0002\n\u0000\"\u000e\u0010\u0019\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000\"\u000e\u0010\u001a\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000\"\u0011\u0010\u001b\u001a\u00020\u0014¢\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\u0017\"\u000e\u0010\u001d\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000\"\u000e\u0010\u001e\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000\"\u000e\u0010\u001f\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000\"\u000e\u0010 \u001a\u00020\u0001X\u0082\u0004¢\u0006\u0002\n\u0000\"\u000e\u0010!\u001a\u00020\u0001X\u0082\u0004¢\u0006\u0002\n\u0000\"\u000e\u0010\"\u001a\u00020\u0001X\u0082\u0004¢\u0006\u0002\n\u0000\"\u000e\u0010#\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000\"\u000e\u0010$\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000\"\u000e\u0010%\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000\"\u000e\u0010&\u001a\u00020\u0001X\u0082\u0004¢\u0006\u0002\n\u0000\"\u000e\u0010'\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000\"\u000e\u0010(\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000\"\u000e\u0010)\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000\"\u000e\u0010*\u001a\u00020\u0001X\u0082\u0004¢\u0006\u0002\n\u0000\"\u000e\u0010+\u001a\u00020\u0014X\u0082\u0004¢\u0006\u0002\n\u0000\"\u000e\u0010,\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000\"\u000e\u0010-\u001a\u00020\u0001X\u0082\u0004¢\u0006\u0002\n\u0000\"\u000e\u0010.\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000\"\u000e\u0010/\u001a\u00020\u0001X\u0082\u0004¢\u0006\u0002\n\u0000\"\u000e\u00100\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000\"\u000e\u00101\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000\"\u000e\u00102\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000\"\u000e\u00103\u001a\u00020\u0001X\u0082\u0004¢\u0006\u0002\n\u0000\"\u000e\u00104\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000\"\u000e\u00105\u001a\u00020\u0001X\u0082\u0004¢\u0006\u0002\n\u0000\"\u000e\u00106\u001a\u00020\u0001X\u0082\u0004¢\u0006\u0002\n\u0000\"\u000e\u00107\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000\"\u000e\u00108\u001a\u00020\u0001X\u0082\u0004¢\u0006\u0002\n\u0000\"\u000e\u00109\u001a\u00020\u0001X\u0082\u0004¢\u0006\u0002\n\u0000\"\u000e\u0010:\u001a\u00020\u0001X\u0082\u0004¢\u0006\u0002\n\u0000\"\u000e\u0010;\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000\"\u000e\u0010<\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000\"\u000e\u0010=\u001a\u00020\u0001X\u0082\u0004¢\u0006\u0002\n\u0000\"\u000e\u0010>\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000\"\u000e\u0010?\u001a\u00020\u0001X\u0082\u0004¢\u0006\u0002\n\u0000\"\u000e\u0010@\u001a\u00020\u0001X\u0082\u0004¢\u0006\u0002\n\u0000\"\u000e\u0010A\u001a\u00020\u0001X\u0082\u0004¢\u0006\u0002\n\u0000\"\u000e\u0010B\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000\"\u000e\u0010C\u001a\u00020\u0001X\u0082\u0004¢\u0006\u0002\n\u0000\"\u000e\u0010D\u001a\u00020\u0001X\u0082\u0004¢\u0006\u0002\n\u0000\"\u000e\u0010E\u001a\u00020\u0014X\u0082\u0004¢\u0006\u0002\n\u0000\"\u000e\u0010F\u001a\u00020\u0001X\u0082\u0004¢\u0006\u0002\n\u0000\"\u000e\u0010G\u001a\u00020\u0001X\u0082\u0004¢\u0006\u0002\n\u0000\"\u000e\u0010H\u001a\u00020\u0001X\u0082\u0004¢\u0006\u0002\n\u0000\"9\u0010I\u001a*\u0012\u0004\u0012\u00020K\u0012\n\u0012\b\u0012\u0004\u0012\u00020M0L0Jj\u0014\u0012\u0004\u0012\u00020K\u0012\n\u0012\b\u0012\u0004\u0012\u00020M0L`N¢\u0006\b\n\u0000\u001a\u0004\bO\u0010P\"9\u0010Q\u001a*\u0012\u0004\u0012\u00020K\u0012\n\u0012\b\u0012\u0004\u0012\u00020M0L0Jj\u0014\u0012\u0004\u0012\u00020K\u0012\n\u0012\b\u0012\u0004\u0012\u00020M0L`N¢\u0006\b\n\u0000\u001a\u0004\bR\u0010P\"\u000e\u0010S\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000\"\u000e\u0010T\u001a\u00020\u0001X\u0082\u0004¢\u0006\u0002\n\u0000\"\u000e\u0010U\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000\"\u000e\u0010V\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000\"\u000e\u0010W\u001a\u00020\u0001X\u0082\u0004¢\u0006\u0002\n\u0000\"\u000e\u0010X\u001a\u00020\u0001X\u0082\u0004¢\u0006\u0002\n\u0000\"\u000e\u0010Y\u001a\u00020\u0001X\u0082\u0004¢\u0006\u0002\n\u0000\"\u000e\u0010Z\u001a\u00020\u0001X\u0082\u0004¢\u0006\u0002\n\u0000\"\u000e\u0010[\u001a\u00020\u0001X\u0082\u0004¢\u0006\u0002\n\u0000\"\u000e\u0010\\\u001a\u00020\u0001X\u0082\u0004¢\u0006\u0002\n\u0000\"\u000e\u0010]\u001a\u00020\u0001X\u0082\u0004¢\u0006\u0002\n\u0000\"\u0017\u0010^\u001a\b\u0012\u0004\u0012\u00020`0_¢\u0006\b\n\u0000\u001a\u0004\ba\u0010b\"\u000e\u0010c\u001a\u00020\u0001X\u0082\u0004¢\u0006\u0002\n\u0000\"\u000e\u0010d\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000\"\u000e\u0010e\u001a\u00020\u0001X\u0082\u0004¢\u0006\u0002\n\u0000\"\u0017\u0010f\u001a\b\u0012\u0004\u0012\u00020`0_¢\u0006\b\n\u0000\u001a\u0004\bg\u0010b\"\u000e\u0010h\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000\"\u000e\u0010i\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000\"\u000e\u0010j\u001a\u00020\u0001X\u0082\u0004¢\u0006\u0002\n\u0000\"\u000e\u0010k\u001a\u00020\u0001X\u0082\u0004¢\u0006\u0002\n\u0000\"\u000e\u0010l\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000\"\u000e\u0010m\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000\"\u000e\u0010n\u001a\u00020\u0001X\u0082\u0004¢\u0006\u0002\n\u0000\"\u000e\u0010o\u001a\u00020\u0001X\u0082\u0004¢\u0006\u0002\n\u0000\"\u000e\u0010p\u001a\u00020\u0001X\u0082\u0004¢\u0006\u0002\n\u0000\"\u000e\u0010q\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000\"\u000e\u0010r\u001a\u00020\u0001X\u0082\u0004¢\u0006\u0002\n\u0000\"\u000e\u0010s\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000\"\u000e\u0010t\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000\"\u000e\u0010u\u001a\u00020\u0001X\u0082\u0004¢\u0006\u0002\n\u0000\"\u000e\u0010v\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000\"\u0011\u0010w\u001a\u00020\u0014¢\u0006\b\n\u0000\u001a\u0004\bx\u0010\u0017\"\u000e\u0010y\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000\"\u000e\u0010z\u001a\u00020\u0001X\u0082\u0004¢\u0006\u0002\n\u0000\"\u000e\u0010{\u001a\u00020\u0001X\u0082\u0004¢\u0006\u0002\n\u0000\"\u000e\u0010|\u001a\u00020\u0001X\u0082\u0004¢\u0006\u0002\n\u0000\"\u000e\u0010}\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000\"\u000e\u0010~\u001a\u00020\u0014X\u0082\u0004¢\u0006\u0002\n\u0000\"\u000e\u0010\u007f\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000\"\u000f\u0010\u0080\u0001\u001a\u00020\u0001X\u0082\u0004¢\u0006\u0002\n\u0000\"\u000f\u0010\u0081\u0001\u001a\u00020\u0001X\u0082\u0004¢\u0006\u0002\n\u0000\"\u000f\u0010\u0082\u0001\u001a\u00020\u0001X\u0082\u0004¢\u0006\u0002\n\u0000\"\u000f\u0010\u0083\u0001\u001a\u00020\u0001X\u0082\u0004¢\u0006\u0002\n\u0000\"\u000f\u0010\u0084\u0001\u001a\u00020\u0001X\u0082\u0004¢\u0006\u0002\n\u0000\"\u000f\u0010\u0085\u0001\u001a\u00020\u0014X\u0082\u0004¢\u0006\u0002\n\u0000\"\u000f\u0010\u0086\u0001\u001a\u00020\u0014X\u0082\u0004¢\u0006\u0002\n\u0000\"\u000f\u0010\u0087\u0001\u001a\u00020\u0001X\u0082\u0004¢\u0006\u0002\n\u0000\"\u000f\u0010\u0088\u0001\u001a\u00020\u0001X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006¤\u0001"}, d2 = {"addContinuationToFunctionCallsLoweringPhase", "Lorg/jetbrains/kotlin/ir/backend/js/BodyLowering;", "addContinuationToLocalSuspendFunctionsLoweringPhase", "addContinuationToNonLocalSuspendFunctionsLoweringPhase", "Lorg/jetbrains/kotlin/ir/backend/js/DeclarationLowering;", "annotationConstructorLowering", "annotationInstantiationLowering", "arrayConstructorPhase", "arrayConstructorReferencePhase", "autoboxingTransformerPhase", "blockDecomposerLoweringPhase", "booleanPropertyInExternalLowering", "bridgesConstructionPhase", "callableReferenceLowering", "callsLoweringPhase", "captureStackTraceInThrowablesPhase", "classReferenceLoweringPhase", "cleanupLoweringPhase", "collectClassDefaultConstructorsPhase", "collectClassIdentifiersLowering", "Lorg/jetbrains/kotlin/ir/backend/js/ModuleLowering;", "constEvaluationPhase", "getConstEvaluationPhase", "()Lorg/jetbrains/kotlin/ir/backend/js/ModuleLowering;", "constLoweringPhase", "copyInlineFunctionBodyLoweringPhase", "copyPropertyAccessorBodiesLoweringPass", "createScriptFunctionsPhase", "getCreateScriptFunctionsPhase", "defaultArgumentPatchOverridesPhase", "defaultArgumentStubGeneratorPhase", "defaultParameterCleanerPhase", "defaultParameterInjectorPhase", "delegateToPrimaryConstructorLoweringPhase", "enumClassConstructorBodyLoweringPhase", "enumClassConstructorLoweringPhase", "enumClassCreateInitializerLoweringPhase", "enumEntryCreateGetInstancesFunsLoweringPhase", "enumEntryInstancesBodyLoweringPhase", "enumEntryInstancesLoweringPhase", "enumEntryRemovalLoweringPhase", "enumSyntheticFunsLoweringPhase", "enumUsageLoweringPhase", "enumWhenPhase", "errorDeclarationLoweringPhase", "errorExpressionLoweringPhase", "es6AddBoxParameterToConstructorsLowering", "es6BoxParameterOptimization", "es6CollectConstructorsWhichNeedBoxParameterLowering", "es6CollectPrimaryConstructorsWhichCouldBeOptimizedLowering", "es6ConstructorLowering", "es6ConstructorUsageLowering", "es6PrimaryConstructorOptimizationLowering", "es6PrimaryConstructorUsageOptimizationLowering", "escapedIdentifiersLowering", "expectDeclarationsRemovingPhase", "externalEnumUsageLoweringPhase", "forLoopsLoweringPhase", "functionInliningPhase", "implicitlyExportedDeclarationsMarkingLowering", "initializersCleanupLoweringPhase", "initializersLoweringPhase", "inlineClassDeclarationLoweringPhase", "inlineClassUsageLoweringPhase", "inlineObjectsWithPureInitialization", "innerClassConstructorCallsLoweringPhase", "innerClassesLoweringPhase", "innerClassesMemberBodyLoweringPhase", "interopCallableReferenceLoweringPhase", "inventNamesForLocalClassesPhase", "invokeStaticInitializersPhase", "jsClassUsageInReflectionPhase", "jsCodeOutliningPhase", "jsOptimizationPhases", "Lorg/jetbrains/kotlin/backend/common/phaser/NamedCompilerPhase;", "Lorg/jetbrains/kotlin/ir/backend/js/JsIrBackendContext;", "", "Lorg/jetbrains/kotlin/ir/declarations/IrModuleFragment;", "Lorg/jetbrains/kotlin/backend/common/phaser/SameTypeNamedCompilerPhase;", "getJsOptimizationPhases", "()Lorg/jetbrains/kotlin/backend/common/phaser/NamedCompilerPhase;", "jsPhases", "getJsPhases", "jsSuspendArityStorePhase", "kotlinNothingValueExceptionPhase", "lateinitDeclarationLoweringPhase", "lateinitNullableFieldsPhase", "lateinitUsageLoweringPhase", "localClassExtractionPhase", "localClassesExtractionFromInlineFunctionsPhase", "localClassesInInlineFunctionsPhase", "localClassesInInlineLambdasPhase", "localDeclarationsLoweringPhase", "localDelegatedPropertiesLoweringPhase", "loweringList", "", "Lorg/jetbrains/kotlin/ir/backend/js/Lowering;", "getLoweringList", "()Ljava/util/List;", "multipleCatchesLoweringPhase", "objectDeclarationLoweringPhase", "objectUsageLoweringPhase", "optimizationLoweringList", "getOptimizationLoweringList", "preventExportOfSyntheticDeclarationsLowering", "primaryConstructorLoweringPhase", "primitiveCompanionLoweringPhase", "privateMemberUsagesLoweringPhase", "privateMembersLoweringPhase", "propertiesLoweringPhase", "propertyAccessorInlinerLoweringPhase", "propertyLazyInitLoweringPhase", "propertyReferenceLoweringPhase", "purifyObjectInstanceGetters", "rangeContainsLoweringPhase", "removeInitializersForLazyProperties", "removeInlineDeclarationsWithReifiedTypeParametersLoweringPhase", "returnableBlockLoweringPhase", "saveInlineFunctionsBeforeInlining", "scriptRemoveReceiverLowering", "getScriptRemoveReceiverLowering", "secondaryConstructorLoweringPhase", "secondaryFactoryInjectorLoweringPhase", "sharedVariablesLoweringPhase", "singleAbstractMethodPhase", "staticMembersLoweringPhase", "stringConcatenationLoweringPhase", "stripTypeAliasDeclarationsPhase", "suspendFunctionsLoweringPhase", "syntheticAccessorLoweringPhase", "tailrecLoweringPhase", "throwableSuccessorsLoweringPhase", "typeOperatorLoweringPhase", "validateIrAfterLowering", "validateIrBeforeLowering", "varargLoweringPhase", "wrapInlineDeclarationsWithReifiedTypeParametersLowering", "makeBodyLoweringPhase", "lowering", "Lkotlin/Function1;", "Lorg/jetbrains/kotlin/backend/common/BodyLoweringPass;", "name", "", MediaTrack.ROLE_DESCRIPTION, "prerequisite", "", "makeCustomJsModulePhase", "op", "Lkotlin/Function2;", "", "Lorg/jetbrains/kotlin/backend/common/phaser/AbstractNamedCompilerPhase;", "makeDeclarationTransformerPhase", "Lorg/jetbrains/kotlin/backend/common/DeclarationTransformer;", "makeJsModulePhase", "Lorg/jetbrains/kotlin/backend/common/FileLoweringPass;", "runOnFilesPostfix", "Lorg/jetbrains/kotlin/backend/common/ClassLoweringPass;", "moduleFragment", "Lorg/jetbrains/kotlin/backend/common/DeclarationContainerLoweringPass;", "files", "Lorg/jetbrains/kotlin/ir/declarations/IrFile;", "toCompilerPhase", "Lorg/jetbrains/kotlin/backend/common/phaser/CompilerPhase;", "toModuleLowering", "backend.js"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class JsLoweringPhasesKt {
    private static final BodyLowering addContinuationToFunctionCallsLoweringPhase;
    private static final BodyLowering addContinuationToLocalSuspendFunctionsLoweringPhase;
    private static final DeclarationLowering addContinuationToNonLocalSuspendFunctionsLoweringPhase;
    private static final DeclarationLowering annotationConstructorLowering;
    private static final DeclarationLowering annotationInstantiationLowering;
    private static final BodyLowering arrayConstructorPhase;
    private static final BodyLowering arrayConstructorReferencePhase;
    private static final BodyLowering autoboxingTransformerPhase;
    private static final BodyLowering blockDecomposerLoweringPhase;
    private static final BodyLowering booleanPropertyInExternalLowering;
    private static final DeclarationLowering bridgesConstructionPhase;
    private static final BodyLowering callableReferenceLowering;
    private static final BodyLowering callsLoweringPhase;
    private static final BodyLowering captureStackTraceInThrowablesPhase;
    private static final BodyLowering classReferenceLoweringPhase;
    private static final BodyLowering cleanupLoweringPhase;
    private static final DeclarationLowering collectClassDefaultConstructorsPhase;
    private static final ModuleLowering collectClassIdentifiersLowering;
    private static final ModuleLowering constEvaluationPhase;
    private static final BodyLowering constLoweringPhase;
    private static final DeclarationLowering copyInlineFunctionBodyLoweringPhase;
    private static final DeclarationLowering copyPropertyAccessorBodiesLoweringPass;
    private static final ModuleLowering createScriptFunctionsPhase;
    private static final DeclarationLowering defaultArgumentPatchOverridesPhase;
    private static final DeclarationLowering defaultArgumentStubGeneratorPhase;
    private static final DeclarationLowering defaultParameterCleanerPhase;
    private static final BodyLowering defaultParameterInjectorPhase;
    private static final BodyLowering delegateToPrimaryConstructorLoweringPhase;
    private static final BodyLowering enumClassConstructorBodyLoweringPhase;
    private static final DeclarationLowering enumClassConstructorLoweringPhase;
    private static final DeclarationLowering enumClassCreateInitializerLoweringPhase;
    private static final DeclarationLowering enumEntryCreateGetInstancesFunsLoweringPhase;
    private static final BodyLowering enumEntryInstancesBodyLoweringPhase;
    private static final DeclarationLowering enumEntryInstancesLoweringPhase;
    private static final DeclarationLowering enumEntryRemovalLoweringPhase;
    private static final DeclarationLowering enumSyntheticFunsLoweringPhase;
    private static final BodyLowering enumUsageLoweringPhase;
    private static final ModuleLowering enumWhenPhase;
    private static final DeclarationLowering errorDeclarationLoweringPhase;
    private static final BodyLowering errorExpressionLoweringPhase;
    private static final DeclarationLowering es6AddBoxParameterToConstructorsLowering;
    private static final BodyLowering es6BoxParameterOptimization;
    private static final DeclarationLowering es6CollectConstructorsWhichNeedBoxParameterLowering;
    private static final DeclarationLowering es6CollectPrimaryConstructorsWhichCouldBeOptimizedLowering;
    private static final DeclarationLowering es6ConstructorLowering;
    private static final BodyLowering es6ConstructorUsageLowering;
    private static final DeclarationLowering es6PrimaryConstructorOptimizationLowering;
    private static final BodyLowering es6PrimaryConstructorUsageOptimizationLowering;
    private static final BodyLowering escapedIdentifiersLowering;
    private static final DeclarationLowering expectDeclarationsRemovingPhase;
    private static final BodyLowering externalEnumUsageLoweringPhase;
    private static final BodyLowering forLoopsLoweringPhase;
    private static final BodyLowering functionInliningPhase;
    private static final DeclarationLowering implicitlyExportedDeclarationsMarkingLowering;
    private static final DeclarationLowering initializersCleanupLoweringPhase;
    private static final BodyLowering initializersLoweringPhase;
    private static final DeclarationLowering inlineClassDeclarationLoweringPhase;
    private static final BodyLowering inlineClassUsageLoweringPhase;
    private static final BodyLowering inlineObjectsWithPureInitialization;
    private static final BodyLowering innerClassConstructorCallsLoweringPhase;
    private static final DeclarationLowering innerClassesLoweringPhase;
    private static final BodyLowering innerClassesMemberBodyLoweringPhase;
    private static final BodyLowering interopCallableReferenceLoweringPhase;
    private static final ModuleLowering inventNamesForLocalClassesPhase;
    private static final BodyLowering invokeStaticInitializersPhase;
    private static final BodyLowering jsClassUsageInReflectionPhase;
    private static final BodyLowering jsCodeOutliningPhase;
    private static final NamedCompilerPhase<JsIrBackendContext, Iterable<IrModuleFragment>> jsOptimizationPhases;
    private static final NamedCompilerPhase<JsIrBackendContext, Iterable<IrModuleFragment>> jsPhases;
    private static final DeclarationLowering jsSuspendArityStorePhase;
    private static final BodyLowering kotlinNothingValueExceptionPhase;
    private static final DeclarationLowering lateinitDeclarationLoweringPhase;
    private static final DeclarationLowering lateinitNullableFieldsPhase;
    private static final BodyLowering lateinitUsageLoweringPhase;
    private static final BodyLowering localClassExtractionPhase;
    private static final BodyLowering localClassesExtractionFromInlineFunctionsPhase;
    private static final BodyLowering localClassesInInlineFunctionsPhase;
    private static final BodyLowering localClassesInInlineLambdasPhase;
    private static final BodyLowering localDeclarationsLoweringPhase;
    private static final BodyLowering localDelegatedPropertiesLoweringPhase;
    private static final List<Lowering> loweringList;
    private static final BodyLowering multipleCatchesLoweringPhase;
    private static final DeclarationLowering objectDeclarationLoweringPhase;
    private static final BodyLowering objectUsageLoweringPhase;
    private static final List<Lowering> optimizationLoweringList;
    private static final DeclarationLowering preventExportOfSyntheticDeclarationsLowering;
    private static final DeclarationLowering primaryConstructorLoweringPhase;
    private static final BodyLowering primitiveCompanionLoweringPhase;
    private static final BodyLowering privateMemberUsagesLoweringPhase;
    private static final DeclarationLowering privateMembersLoweringPhase;
    private static final DeclarationLowering propertiesLoweringPhase;
    private static final BodyLowering propertyAccessorInlinerLoweringPhase;
    private static final BodyLowering propertyLazyInitLoweringPhase;
    private static final BodyLowering propertyReferenceLoweringPhase;
    private static final DeclarationLowering purifyObjectInstanceGetters;
    private static final BodyLowering rangeContainsLoweringPhase;
    private static final DeclarationLowering removeInitializersForLazyProperties;
    private static final DeclarationLowering removeInlineDeclarationsWithReifiedTypeParametersLoweringPhase;
    private static final BodyLowering returnableBlockLoweringPhase;
    private static final DeclarationLowering saveInlineFunctionsBeforeInlining;
    private static final ModuleLowering scriptRemoveReceiverLowering;
    private static final DeclarationLowering secondaryConstructorLoweringPhase;
    private static final BodyLowering secondaryFactoryInjectorLoweringPhase;
    private static final BodyLowering sharedVariablesLoweringPhase;
    private static final BodyLowering singleAbstractMethodPhase;
    private static final DeclarationLowering staticMembersLoweringPhase;
    private static final ModuleLowering stringConcatenationLoweringPhase;
    private static final DeclarationLowering stripTypeAliasDeclarationsPhase;
    private static final BodyLowering suspendFunctionsLoweringPhase;
    private static final BodyLowering syntheticAccessorLoweringPhase;
    private static final BodyLowering tailrecLoweringPhase;
    private static final BodyLowering throwableSuccessorsLoweringPhase;
    private static final BodyLowering typeOperatorLoweringPhase;
    private static final ModuleLowering validateIrAfterLowering;
    private static final ModuleLowering validateIrBeforeLowering;
    private static final BodyLowering varargLoweringPhase;
    private static final BodyLowering wrapInlineDeclarationsWithReifiedTypeParametersLowering;

    private static final CompilerPhase<JsIrBackendContext, Iterable<IrModuleFragment>, Iterable<IrModuleFragment>> toCompilerPhase(List<? extends Lowering> list) {
        List<? extends Lowering> list2 = list;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list2, 10));
        Iterator<T> it = list2.iterator();
        while (it.hasNext()) {
            NamedCompilerPhase<JsIrBackendContext, Iterable<IrModuleFragment>> modulePhase = ((Lowering) it.next()).getModulePhase();
            Intrinsics.checkNotNull(modulePhase, "null cannot be cast to non-null type org.jetbrains.kotlin.backend.common.phaser.CompilerPhase<org.jetbrains.kotlin.ir.backend.js.JsIrBackendContext, kotlin.collections.Iterable<org.jetbrains.kotlin.ir.declarations.IrModuleFragment>, kotlin.collections.Iterable<org.jetbrains.kotlin.ir.declarations.IrModuleFragment>>");
            arrayList.add(modulePhase);
        }
        Iterator it2 = arrayList.iterator();
        if (!it2.hasNext()) {
            throw new UnsupportedOperationException("Empty collection can't be reduced.");
        }
        Object next = it2.next();
        while (it2.hasNext()) {
            next = PhaseBuildersKt.then((CompilerPhase) next, (CompilerPhase) it2.next());
        }
        return (CompilerPhase) next;
    }

    static /* synthetic */ NamedCompilerPhase makeJsModulePhase$default(Function1 function1, String str, String str2, Set set, int i, Object obj) {
        if ((i & 8) != 0) {
            set = SetsKt.emptySet();
        }
        return makeJsModulePhase(function1, str, str2, set);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final NamedCompilerPhase<JsIrBackendContext, Iterable<IrModuleFragment>> makeJsModulePhase(final Function1<? super JsIrBackendContext, ? extends FileLoweringPass> function1, String str, String str2, Set<? extends AbstractNamedCompilerPhase<? super JsIrBackendContext, ?, ?>> set) {
        return makeCustomJsModulePhase(new Function2<JsIrBackendContext, IrModuleFragment, Unit>() { // from class: org.jetbrains.kotlin.ir.backend.js.JsLoweringPhasesKt.makeJsModulePhase.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            /* JADX WARN: Multi-variable type inference failed */
            {
                super(2);
            }

            @Override // kotlin.jvm.functions.Function2
            public /* bridge */ /* synthetic */ Unit invoke(JsIrBackendContext jsIrBackendContext, IrModuleFragment irModuleFragment) {
                invoke2(jsIrBackendContext, irModuleFragment);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(JsIrBackendContext context, IrModuleFragment modules) {
                Intrinsics.checkNotNullParameter(context, "context");
                Intrinsics.checkNotNullParameter(modules, "modules");
                LowerKt.lower(function1.invoke(context), modules);
            }
        }, str2, str, set);
    }

    static /* synthetic */ NamedCompilerPhase makeCustomJsModulePhase$default(Function2 function2, String str, String str2, Set set, int i, Object obj) {
        if ((i & 8) != 0) {
            set = SetsKt.emptySet();
        }
        return makeCustomJsModulePhase(function2, str, str2, set);
    }

    private static final NamedCompilerPhase<JsIrBackendContext, Iterable<IrModuleFragment>> makeCustomJsModulePhase(final Function2<? super JsIrBackendContext, ? super IrModuleFragment, Unit> function2, String str, String str2, Set<? extends AbstractNamedCompilerPhase<? super JsIrBackendContext, ?, ?>> set) {
        return new NamedCompilerPhase<>(str2, str, set, new SameTypeCompilerPhase<JsIrBackendContext, Iterable<? extends IrModuleFragment>>() { // from class: org.jetbrains.kotlin.ir.backend.js.JsLoweringPhasesKt.makeCustomJsModulePhase.1
            @Override // org.jetbrains.kotlin.backend.common.phaser.CompilerPhase
            public /* bridge */ /* synthetic */ Object invoke(PhaseConfigurationService phaseConfigurationService, PhaserState phaserState, LoggingContext loggingContext, Object obj) {
                return invoke(phaseConfigurationService, (PhaserState<Iterable<IrModuleFragment>>) phaserState, (JsIrBackendContext) loggingContext, (Iterable<? extends IrModuleFragment>) obj);
            }

            /* JADX WARN: Multi-variable type inference failed */
            public Iterable<IrModuleFragment> invoke(PhaseConfigurationService phaseConfig, PhaserState<Iterable<IrModuleFragment>> phaserState, JsIrBackendContext context, Iterable<? extends IrModuleFragment> input) {
                Intrinsics.checkNotNullParameter(phaseConfig, "phaseConfig");
                Intrinsics.checkNotNullParameter(phaserState, "phaserState");
                Intrinsics.checkNotNullParameter(context, "context");
                Intrinsics.checkNotNullParameter(input, "input");
                Function2<JsIrBackendContext, IrModuleFragment, Unit> function3 = function2;
                Iterator it = input.iterator();
                while (it.hasNext()) {
                    function3.invoke(context, (IrModuleFragment) it.next());
                }
                return input;
            }
        }, null, null, null, SetsKt.setOf((Object[]) new Function3[]{LowerKt.toMultiModuleAction(DumperVerifierKt.getDefaultDumper()), LowerKt.toMultiModuleAction(DumperVerifierKt.getValidationAction())}), 0, 368, null);
    }

    static /* synthetic */ DeclarationLowering makeDeclarationTransformerPhase$default(Function1 function1, String str, String str2, Set set, int i, Object obj) {
        if ((i & 8) != 0) {
            set = SetsKt.emptySet();
        }
        return makeDeclarationTransformerPhase(function1, str, str2, set);
    }

    private static final DeclarationLowering makeDeclarationTransformerPhase(Function1<? super JsIrBackendContext, ? extends DeclarationTransformer> function1, String str, String str2, Set<? extends Lowering> set) {
        Set<? extends Lowering> set2 = set;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(set2, 10));
        Iterator<T> it = set2.iterator();
        while (it.hasNext()) {
            arrayList.add(((Lowering) it.next()).getModulePhase());
        }
        return new DeclarationLowering(str, str2, CollectionsKt.toSet(arrayList), function1);
    }

    static /* synthetic */ BodyLowering makeBodyLoweringPhase$default(Function1 function1, String str, String str2, Set set, int i, Object obj) {
        if ((i & 8) != 0) {
            set = SetsKt.emptySet();
        }
        return makeBodyLoweringPhase(function1, str, str2, set);
    }

    private static final BodyLowering makeBodyLoweringPhase(Function1<? super JsIrBackendContext, ? extends BodyLoweringPass> function1, String str, String str2, Set<? extends Lowering> set) {
        Set<? extends Lowering> set2 = set;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(set2, 10));
        Iterator<T> it = set2.iterator();
        while (it.hasNext()) {
            arrayList.add(((Lowering) it.next()).getModulePhase());
        }
        return new BodyLowering(str, str2, CollectionsKt.toSet(arrayList), function1);
    }

    public static final ModuleLowering toModuleLowering(NamedCompilerPhase<? super JsIrBackendContext, Iterable<IrModuleFragment>> namedCompilerPhase) {
        Intrinsics.checkNotNullParameter(namedCompilerPhase, "<this>");
        return new ModuleLowering(namedCompilerPhase.getName(), namedCompilerPhase);
    }

    static {
        ModuleLowering moduleLowering = toModuleLowering(makeCustomJsModulePhase$default(new Function2<JsIrBackendContext, IrModuleFragment, Unit>() { // from class: org.jetbrains.kotlin.ir.backend.js.JsLoweringPhasesKt$validateIrBeforeLowering$1
            @Override // kotlin.jvm.functions.Function2
            public /* bridge */ /* synthetic */ Unit invoke(JsIrBackendContext jsIrBackendContext, IrModuleFragment irModuleFragment) {
                invoke2(jsIrBackendContext, irModuleFragment);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(JsIrBackendContext context, IrModuleFragment module) {
                Intrinsics.checkNotNullParameter(context, "context");
                Intrinsics.checkNotNullParameter(module, "module");
                DumperVerifierKt.validationCallback$default(context, module, false, 4, null);
            }
        }, "Validate IR before lowering", "ValidateIrBeforeLowering", null, 8, null));
        validateIrBeforeLowering = moduleLowering;
        ModuleLowering moduleLowering2 = toModuleLowering(makeCustomJsModulePhase$default(new Function2<JsIrBackendContext, IrModuleFragment, Unit>() { // from class: org.jetbrains.kotlin.ir.backend.js.JsLoweringPhasesKt$validateIrAfterLowering$1
            @Override // kotlin.jvm.functions.Function2
            public /* bridge */ /* synthetic */ Unit invoke(JsIrBackendContext jsIrBackendContext, IrModuleFragment irModuleFragment) {
                invoke2(jsIrBackendContext, irModuleFragment);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(JsIrBackendContext context, IrModuleFragment module) {
                Intrinsics.checkNotNullParameter(context, "context");
                Intrinsics.checkNotNullParameter(module, "module");
                DumperVerifierKt.validationCallback$default(context, module, false, 4, null);
            }
        }, "Validate IR after lowering", "ValidateIrAfterLowering", null, 8, null));
        validateIrAfterLowering = moduleLowering2;
        DeclarationLowering declarationLoweringMakeDeclarationTransformerPhase$default = makeDeclarationTransformerPhase$default(JsLoweringPhasesKt$collectClassDefaultConstructorsPhase$1.INSTANCE, "CollectClassDefaultConstructorsLowering", "Collect classes default constructors to add it to metadata on code generating phase", null, 8, null);
        collectClassDefaultConstructorsPhase = declarationLoweringMakeDeclarationTransformerPhase$default;
        DeclarationLowering declarationLoweringMakeDeclarationTransformerPhase$default2 = makeDeclarationTransformerPhase$default(JsLoweringPhasesKt$preventExportOfSyntheticDeclarationsLowering$1.INSTANCE, "ExcludeSyntheticDeclarationsFromExportLowering", "Exclude synthetic declarations which we don't want to export such as `Enum.entries` or `DataClass::componentN`", null, 8, null);
        preventExportOfSyntheticDeclarationsLowering = declarationLoweringMakeDeclarationTransformerPhase$default2;
        ModuleLowering moduleLowering3 = toModuleLowering(makeJsModulePhase$default(JsLoweringPhasesKt$scriptRemoveReceiverLowering$1.INSTANCE, "ScriptRemoveReceiver", "Remove receivers for declarations in script", null, 8, null));
        scriptRemoveReceiverLowering = moduleLowering3;
        ModuleLowering moduleLowering4 = toModuleLowering(makeJsModulePhase$default(JsLoweringPhasesKt$createScriptFunctionsPhase$1.INSTANCE, "CreateScriptFunctionsPhase", "Create functions for initialize and evaluate script", null, 8, null));
        createScriptFunctionsPhase = moduleLowering4;
        ModuleLowering moduleLowering5 = toModuleLowering(makeJsModulePhase$default(JsLoweringPhasesKt$collectClassIdentifiersLowering$1.INSTANCE, "CollectClassIdentifiersLowering", "Save classId before all the lowerings", null, 8, null));
        collectClassIdentifiersLowering = moduleLowering5;
        ModuleLowering moduleLowering6 = toModuleLowering(makeJsModulePhase$default(JsLoweringPhasesKt$inventNamesForLocalClassesPhase$1.INSTANCE, "InventNamesForLocalClasses", "Invent names for local classes and anonymous objects", null, 8, null));
        inventNamesForLocalClassesPhase = moduleLowering6;
        DeclarationLowering declarationLoweringMakeDeclarationTransformerPhase$default3 = makeDeclarationTransformerPhase$default(JsLoweringPhasesKt$annotationInstantiationLowering$1.INSTANCE, "AnnotationImplementation", "Create synthetic annotations implementations and use them in annotations constructor calls", null, 8, null);
        annotationInstantiationLowering = declarationLoweringMakeDeclarationTransformerPhase$default3;
        DeclarationLowering declarationLoweringMakeDeclarationTransformerPhase$default4 = makeDeclarationTransformerPhase$default(JsLoweringPhasesKt$expectDeclarationsRemovingPhase$1.INSTANCE, "ExpectDeclarationsRemoving", "Remove expect declaration from module fragment", null, 8, null);
        expectDeclarationsRemovingPhase = declarationLoweringMakeDeclarationTransformerPhase$default4;
        ModuleLowering moduleLowering7 = toModuleLowering(makeJsModulePhase$default(JsLoweringPhasesKt$stringConcatenationLoweringPhase$1.INSTANCE, "JsStringConcatenationLowering", "Call toString() for values of some types when concatenating strings", null, 8, null));
        stringConcatenationLoweringPhase = moduleLowering7;
        DeclarationLowering declarationLoweringMakeDeclarationTransformerPhase$default5 = makeDeclarationTransformerPhase$default(JsLoweringPhasesKt$lateinitNullableFieldsPhase$1.INSTANCE, "LateinitNullableFields", "Create nullable fields for lateinit properties", null, 8, null);
        lateinitNullableFieldsPhase = declarationLoweringMakeDeclarationTransformerPhase$default5;
        DeclarationLowering declarationLoweringMakeDeclarationTransformerPhase$default6 = makeDeclarationTransformerPhase$default(JsLoweringPhasesKt$lateinitDeclarationLoweringPhase$1.INSTANCE, "LateinitDeclarations", "Reference nullable fields from properties and getters + insert checks", null, 8, null);
        lateinitDeclarationLoweringPhase = declarationLoweringMakeDeclarationTransformerPhase$default6;
        BodyLowering bodyLoweringMakeBodyLoweringPhase$default = makeBodyLoweringPhase$default(JsLoweringPhasesKt$lateinitUsageLoweringPhase$1.INSTANCE, "LateinitUsage", "Insert checks for lateinit field references", null, 8, null);
        lateinitUsageLoweringPhase = bodyLoweringMakeBodyLoweringPhase$default;
        BodyLowering bodyLoweringMakeBodyLoweringPhase$default2 = makeBodyLoweringPhase$default(JsLoweringPhasesKt$kotlinNothingValueExceptionPhase$1.INSTANCE, "KotlinNothingValueException", "Throw proper exception for calls returning value of type 'kotlin.Nothing'", null, 8, null);
        kotlinNothingValueExceptionPhase = bodyLoweringMakeBodyLoweringPhase$default2;
        DeclarationLowering declarationLoweringMakeDeclarationTransformerPhase$default7 = makeDeclarationTransformerPhase$default(new Function1<JsIrBackendContext, DeclarationTransformer>() { // from class: org.jetbrains.kotlin.ir.backend.js.JsLoweringPhasesKt$stripTypeAliasDeclarationsPhase$1
            @Override // kotlin.jvm.functions.Function1
            public final DeclarationTransformer invoke(JsIrBackendContext it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return new StripTypeAliasDeclarationsLowering();
            }
        }, "StripTypeAliasDeclarations", "Strip typealias declarations", null, 8, null);
        stripTypeAliasDeclarationsPhase = declarationLoweringMakeDeclarationTransformerPhase$default7;
        BodyLowering bodyLoweringMakeBodyLoweringPhase$default3 = makeBodyLoweringPhase$default(JsLoweringPhasesKt$jsCodeOutliningPhase$1.INSTANCE, "JsCodeOutliningLowering", "Outline js() calls where JS code references Kotlin locals", null, 8, null);
        jsCodeOutliningPhase = bodyLoweringMakeBodyLoweringPhase$default3;
        BodyLowering bodyLoweringMakeBodyLoweringPhase$default4 = makeBodyLoweringPhase$default(JsLoweringPhasesKt$arrayConstructorReferencePhase$1.INSTANCE, "ArrayConstructorReference", "Transform `::Array` into a lambda", null, 8, null);
        arrayConstructorReferencePhase = bodyLoweringMakeBodyLoweringPhase$default4;
        BodyLowering bodyLoweringMakeBodyLoweringPhase = makeBodyLoweringPhase(JsLoweringPhasesKt$arrayConstructorPhase$1.INSTANCE, "ArrayConstructor", "Transform `Array(size) { index -> value }` into a loop", SetsKt.setOf(bodyLoweringMakeBodyLoweringPhase$default4));
        arrayConstructorPhase = bodyLoweringMakeBodyLoweringPhase;
        BodyLowering bodyLoweringMakeBodyLoweringPhase2 = makeBodyLoweringPhase(JsLoweringPhasesKt$sharedVariablesLoweringPhase$1.INSTANCE, "SharedVariablesLowering", "Box captured mutable variables", SetsKt.setOf((Object[]) new Lowering[]{declarationLoweringMakeDeclarationTransformerPhase$default6, bodyLoweringMakeBodyLoweringPhase$default}));
        sharedVariablesLoweringPhase = bodyLoweringMakeBodyLoweringPhase2;
        BodyLowering bodyLoweringMakeBodyLoweringPhase3 = makeBodyLoweringPhase(JsLoweringPhasesKt$localClassesInInlineLambdasPhase$1.INSTANCE, "LocalClassesInInlineLambdasPhase", "Extract local classes from inline lambdas", SetsKt.setOf(moduleLowering6));
        localClassesInInlineLambdasPhase = bodyLoweringMakeBodyLoweringPhase3;
        BodyLowering bodyLoweringMakeBodyLoweringPhase4 = makeBodyLoweringPhase(JsLoweringPhasesKt$localClassesInInlineFunctionsPhase$1.INSTANCE, "LocalClassesInInlineFunctionsPhase", "Extract local classes from inline functions", SetsKt.setOf(moduleLowering6));
        localClassesInInlineFunctionsPhase = bodyLoweringMakeBodyLoweringPhase4;
        BodyLowering bodyLoweringMakeBodyLoweringPhase5 = makeBodyLoweringPhase(new Function1<JsIrBackendContext, BodyLoweringPass>() { // from class: org.jetbrains.kotlin.ir.backend.js.JsLoweringPhasesKt$localClassesExtractionFromInlineFunctionsPhase$1
            /* JADX WARN: Multi-variable type inference failed */
            @Override // kotlin.jvm.functions.Function1
            public final BodyLoweringPass invoke(JsIrBackendContext context) {
                Intrinsics.checkNotNullParameter(context, "context");
                return new LocalClassesExtractionFromInlineFunctionsLowering(context, null, 2, 0 == true ? 1 : 0);
            }
        }, "localClassesExtractionFromInlineFunctionsPhase", "Move local classes from inline functions into nearest declaration container", SetsKt.setOf(bodyLoweringMakeBodyLoweringPhase4));
        localClassesExtractionFromInlineFunctionsPhase = bodyLoweringMakeBodyLoweringPhase5;
        BodyLowering bodyLoweringMakeBodyLoweringPhase$default5 = makeBodyLoweringPhase$default(JsLoweringPhasesKt$syntheticAccessorLoweringPhase$1.INSTANCE, "syntheticAccessorLoweringPhase", "Wrap top level inline function to access through them from inline functions", null, 8, null);
        syntheticAccessorLoweringPhase = bodyLoweringMakeBodyLoweringPhase$default5;
        BodyLowering bodyLoweringMakeBodyLoweringPhase$default6 = makeBodyLoweringPhase$default(JsLoweringPhasesKt$wrapInlineDeclarationsWithReifiedTypeParametersLowering$1.INSTANCE, "WrapInlineDeclarationsWithReifiedTypeParametersLowering", "Wrap inline declarations with reified type parameters", null, 8, null);
        wrapInlineDeclarationsWithReifiedTypeParametersLowering = bodyLoweringMakeBodyLoweringPhase$default6;
        DeclarationLowering declarationLoweringMakeDeclarationTransformerPhase = makeDeclarationTransformerPhase(JsLoweringPhasesKt$saveInlineFunctionsBeforeInlining$1.INSTANCE, "SaveInlineFunctionsBeforeInlining", "Save inline function before inlining", SetsKt.setOf((Object[]) new Lowering[]{declarationLoweringMakeDeclarationTransformerPhase$default4, bodyLoweringMakeBodyLoweringPhase2, bodyLoweringMakeBodyLoweringPhase3, bodyLoweringMakeBodyLoweringPhase5, bodyLoweringMakeBodyLoweringPhase$default5, bodyLoweringMakeBodyLoweringPhase$default6}));
        saveInlineFunctionsBeforeInlining = declarationLoweringMakeDeclarationTransformerPhase;
        BodyLowering bodyLoweringMakeBodyLoweringPhase6 = makeBodyLoweringPhase(new Function1<JsIrBackendContext, BodyLoweringPass>() { // from class: org.jetbrains.kotlin.ir.backend.js.JsLoweringPhasesKt$functionInliningPhase$1
            @Override // kotlin.jvm.functions.Function1
            public final BodyLoweringPass invoke(JsIrBackendContext it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return new FunctionInlining(it, new JsInlineFunctionResolver(it), it.getInnerClassesSupport(), false, true, false, true, true, true, 40, null);
            }
        }, "FunctionInliningPhase", "Perform function inlining", SetsKt.setOf(declarationLoweringMakeDeclarationTransformerPhase));
        functionInliningPhase = bodyLoweringMakeBodyLoweringPhase6;
        DeclarationLowering declarationLoweringMakeDeclarationTransformerPhase2 = makeDeclarationTransformerPhase(JsLoweringPhasesKt$copyInlineFunctionBodyLoweringPhase$1.INSTANCE, "CopyInlineFunctionBody", "Copy inline function body", SetsKt.setOf(bodyLoweringMakeBodyLoweringPhase6));
        copyInlineFunctionBodyLoweringPhase = declarationLoweringMakeDeclarationTransformerPhase2;
        DeclarationLowering declarationLoweringMakeDeclarationTransformerPhase3 = makeDeclarationTransformerPhase(new Function1<JsIrBackendContext, DeclarationTransformer>() { // from class: org.jetbrains.kotlin.ir.backend.js.JsLoweringPhasesKt$removeInlineDeclarationsWithReifiedTypeParametersLoweringPhase$1
            @Override // kotlin.jvm.functions.Function1
            public final DeclarationTransformer invoke(JsIrBackendContext it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return new RemoveInlineDeclarationsWithReifiedTypeParametersLowering();
            }
        }, "RemoveInlineFunctionsWithReifiedTypeParametersLowering", "Remove Inline functions with reified parameters from context", SetsKt.setOf(bodyLoweringMakeBodyLoweringPhase6));
        removeInlineDeclarationsWithReifiedTypeParametersLoweringPhase = declarationLoweringMakeDeclarationTransformerPhase3;
        BodyLowering bodyLoweringMakeBodyLoweringPhase$default7 = makeBodyLoweringPhase$default(JsLoweringPhasesKt$captureStackTraceInThrowablesPhase$1.INSTANCE, "CaptureStackTraceInThrowables", "Capture stack trace in Throwable constructors", null, 8, null);
        captureStackTraceInThrowablesPhase = bodyLoweringMakeBodyLoweringPhase$default7;
        BodyLowering bodyLoweringMakeBodyLoweringPhase7 = makeBodyLoweringPhase(new Function1<JsIrBackendContext, BodyLoweringPass>() { // from class: org.jetbrains.kotlin.ir.backend.js.JsLoweringPhasesKt$throwableSuccessorsLoweringPhase$1
            @Override // kotlin.jvm.functions.Function1
            public final BodyLoweringPass invoke(JsIrBackendContext context) {
                Intrinsics.checkNotNullParameter(context, "context");
                return new ThrowableLowering(context, context.getEs6mode() ? context.getSetPropertiesToThrowableInstanceSymbol() : context.getExtendThrowableSymbol());
            }
        }, "ThrowableLowering", "Link kotlin.Throwable and JavaScript Error together to provide proper interop between language and platform exceptions", SetsKt.setOf(bodyLoweringMakeBodyLoweringPhase$default7));
        throwableSuccessorsLoweringPhase = bodyLoweringMakeBodyLoweringPhase7;
        BodyLowering bodyLoweringMakeBodyLoweringPhase$default8 = makeBodyLoweringPhase$default(JsLoweringPhasesKt$tailrecLoweringPhase$1.INSTANCE, "TailrecLowering", "Replace `tailrec` callsites with equivalent loop", null, 8, null);
        tailrecLoweringPhase = bodyLoweringMakeBodyLoweringPhase$default8;
        DeclarationLowering declarationLoweringMakeDeclarationTransformerPhase$default8 = makeDeclarationTransformerPhase$default(JsLoweringPhasesKt$enumClassConstructorLoweringPhase$1.INSTANCE, "EnumClassConstructorLowering", "Transform Enum Class into regular Class", null, 8, null);
        enumClassConstructorLoweringPhase = declarationLoweringMakeDeclarationTransformerPhase$default8;
        BodyLowering bodyLoweringMakeBodyLoweringPhase$default9 = makeBodyLoweringPhase$default(JsLoweringPhasesKt$enumClassConstructorBodyLoweringPhase$1.INSTANCE, "EnumClassConstructorBodyLowering", "Transform Enum Class into regular Class", null, 8, null);
        enumClassConstructorBodyLoweringPhase = bodyLoweringMakeBodyLoweringPhase$default9;
        DeclarationLowering declarationLoweringMakeDeclarationTransformerPhase4 = makeDeclarationTransformerPhase(JsLoweringPhasesKt$enumEntryInstancesLoweringPhase$1.INSTANCE, "EnumEntryInstancesLowering", "Create instance variable for each enum entry initialized with `null`", SetsKt.setOf(declarationLoweringMakeDeclarationTransformerPhase$default8));
        enumEntryInstancesLoweringPhase = declarationLoweringMakeDeclarationTransformerPhase4;
        BodyLowering bodyLoweringMakeBodyLoweringPhase8 = makeBodyLoweringPhase(JsLoweringPhasesKt$enumEntryInstancesBodyLoweringPhase$1.INSTANCE, "EnumEntryInstancesBodyLowering", "Insert enum entry field initialization into correxposnding class constructors", SetsKt.setOf(declarationLoweringMakeDeclarationTransformerPhase4));
        enumEntryInstancesBodyLoweringPhase = bodyLoweringMakeBodyLoweringPhase8;
        DeclarationLowering declarationLoweringMakeDeclarationTransformerPhase5 = makeDeclarationTransformerPhase(JsLoweringPhasesKt$enumClassCreateInitializerLoweringPhase$1.INSTANCE, "EnumClassCreateInitializerLowering", "Create initializer for enum entries", SetsKt.setOf(declarationLoweringMakeDeclarationTransformerPhase$default8));
        enumClassCreateInitializerLoweringPhase = declarationLoweringMakeDeclarationTransformerPhase5;
        DeclarationLowering declarationLoweringMakeDeclarationTransformerPhase6 = makeDeclarationTransformerPhase(JsLoweringPhasesKt$enumEntryCreateGetInstancesFunsLoweringPhase$1.INSTANCE, "EnumEntryCreateGetInstancesFunsLowering", "Create enumEntry_getInstance functions", SetsKt.setOf(declarationLoweringMakeDeclarationTransformerPhase$default8));
        enumEntryCreateGetInstancesFunsLoweringPhase = declarationLoweringMakeDeclarationTransformerPhase6;
        DeclarationLowering declarationLoweringMakeDeclarationTransformerPhase7 = makeDeclarationTransformerPhase(JsLoweringPhasesKt$enumSyntheticFunsLoweringPhase$1.INSTANCE, "EnumSyntheticFunctionsAndPropertiesLowering", "Implement `valueOf, `values` and `entries`", SetsKt.setOf((Object[]) new DeclarationLowering[]{declarationLoweringMakeDeclarationTransformerPhase$default8, declarationLoweringMakeDeclarationTransformerPhase5, declarationLoweringMakeDeclarationTransformerPhase6}));
        enumSyntheticFunsLoweringPhase = declarationLoweringMakeDeclarationTransformerPhase7;
        BodyLowering bodyLoweringMakeBodyLoweringPhase9 = makeBodyLoweringPhase(JsLoweringPhasesKt$enumUsageLoweringPhase$1.INSTANCE, "EnumUsageLowering", "Replace enum access with invocation of corresponding function", SetsKt.setOf(declarationLoweringMakeDeclarationTransformerPhase6));
        enumUsageLoweringPhase = bodyLoweringMakeBodyLoweringPhase9;
        BodyLowering bodyLoweringMakeBodyLoweringPhase$default10 = makeBodyLoweringPhase$default(JsLoweringPhasesKt$externalEnumUsageLoweringPhase$1.INSTANCE, "ExternalEnumUsagesLowering", "Replace external enum entry accesses with field accesses", null, 8, null);
        externalEnumUsageLoweringPhase = bodyLoweringMakeBodyLoweringPhase$default10;
        DeclarationLowering declarationLoweringMakeDeclarationTransformerPhase8 = makeDeclarationTransformerPhase(JsLoweringPhasesKt$enumEntryRemovalLoweringPhase$1.INSTANCE, "EnumEntryRemovalLowering", "Replace enum entry with corresponding class", SetsKt.setOf(bodyLoweringMakeBodyLoweringPhase9));
        enumEntryRemovalLoweringPhase = declarationLoweringMakeDeclarationTransformerPhase8;
        BodyLowering bodyLoweringMakeBodyLoweringPhase10 = makeBodyLoweringPhase(JsLoweringPhasesKt$callableReferenceLowering$1.INSTANCE, "CallableReferenceLowering", "Build a lambda/callable reference class", SetsKt.setOf((Object[]) new BodyLowering[]{bodyLoweringMakeBodyLoweringPhase6, bodyLoweringMakeBodyLoweringPhase$default6}));
        callableReferenceLowering = bodyLoweringMakeBodyLoweringPhase10;
        BodyLowering bodyLoweringMakeBodyLoweringPhase11 = makeBodyLoweringPhase(JsLoweringPhasesKt$returnableBlockLoweringPhase$1.INSTANCE, "JsReturnableBlockLowering", "Introduce temporary variable for result and change returnable block's type to Unit", SetsKt.setOf(bodyLoweringMakeBodyLoweringPhase6));
        returnableBlockLoweringPhase = bodyLoweringMakeBodyLoweringPhase11;
        BodyLowering bodyLoweringMakeBodyLoweringPhase$default11 = makeBodyLoweringPhase$default(JsLoweringPhasesKt$rangeContainsLoweringPhase$1.INSTANCE, "RangeContainsLowering", "[Optimization] Optimizes calls to contains() for ClosedRanges", null, 8, null);
        rangeContainsLoweringPhase = bodyLoweringMakeBodyLoweringPhase$default11;
        BodyLowering bodyLoweringMakeBodyLoweringPhase$default12 = makeBodyLoweringPhase$default(JsLoweringPhasesKt$forLoopsLoweringPhase$1.INSTANCE, "ForLoopsLowering", "[Optimization] For loops lowering", null, 8, null);
        forLoopsLoweringPhase = bodyLoweringMakeBodyLoweringPhase$default12;
        ModuleLowering moduleLowering8 = toModuleLowering(makeJsModulePhase$default(JsLoweringPhasesKt$enumWhenPhase$1.INSTANCE, "EnumWhenLowering", "Replace `when` subjects of enum types with their ordinals", null, 8, null));
        enumWhenPhase = moduleLowering8;
        BodyLowering bodyLoweringMakeBodyLoweringPhase$default13 = makeBodyLoweringPhase$default(JsLoweringPhasesKt$propertyLazyInitLoweringPhase$1.INSTANCE, "PropertyLazyInitLowering", "Make property init as lazy", null, 8, null);
        propertyLazyInitLoweringPhase = bodyLoweringMakeBodyLoweringPhase$default13;
        DeclarationLowering declarationLoweringMakeDeclarationTransformerPhase$default9 = makeDeclarationTransformerPhase$default(JsLoweringPhasesKt$removeInitializersForLazyProperties$1.INSTANCE, "RemoveInitializersForLazyProperties", "Remove property initializers if they was initialized lazily", null, 8, null);
        removeInitializersForLazyProperties = declarationLoweringMakeDeclarationTransformerPhase$default9;
        BodyLowering bodyLoweringMakeBodyLoweringPhase$default14 = makeBodyLoweringPhase$default(JsLoweringPhasesKt$propertyAccessorInlinerLoweringPhase$1.INSTANCE, "PropertyAccessorInlineLowering", "[Optimization] Inline property accessors", null, 8, null);
        propertyAccessorInlinerLoweringPhase = bodyLoweringMakeBodyLoweringPhase$default14;
        DeclarationLowering declarationLoweringMakeDeclarationTransformerPhase9 = makeDeclarationTransformerPhase(JsLoweringPhasesKt$copyPropertyAccessorBodiesLoweringPass$1.INSTANCE, "CopyAccessorBodyLowering", "Copy accessor bodies so that ist can be safely read in PropertyAccessorInlineLowering", SetsKt.setOf(bodyLoweringMakeBodyLoweringPhase$default14));
        copyPropertyAccessorBodiesLoweringPass = declarationLoweringMakeDeclarationTransformerPhase9;
        BodyLowering bodyLoweringMakeBodyLoweringPhase$default15 = makeBodyLoweringPhase$default(JsLoweringPhasesKt$booleanPropertyInExternalLowering$1.INSTANCE, "BooleanPropertyInExternalLowering", "Lowering which wrap boolean in external declarations with Boolean() call and add diagnostic for such cases", null, 8, null);
        booleanPropertyInExternalLowering = bodyLoweringMakeBodyLoweringPhase$default15;
        BodyLowering bodyLoweringMakeBodyLoweringPhase$default16 = makeBodyLoweringPhase$default(new Function1<JsIrBackendContext, BodyLoweringPass>() { // from class: org.jetbrains.kotlin.ir.backend.js.JsLoweringPhasesKt$localDelegatedPropertiesLoweringPhase$1
            @Override // kotlin.jvm.functions.Function1
            public final BodyLoweringPass invoke(JsIrBackendContext it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return new LocalDelegatedPropertiesLowering();
            }
        }, "LocalDelegatedPropertiesLowering", "Transform Local Delegated properties", null, 8, null);
        localDelegatedPropertiesLoweringPhase = bodyLoweringMakeBodyLoweringPhase$default16;
        BodyLowering bodyLoweringMakeBodyLoweringPhase12 = makeBodyLoweringPhase(new Function1<JsIrBackendContext, BodyLoweringPass>() { // from class: org.jetbrains.kotlin.ir.backend.js.JsLoweringPhasesKt$localDeclarationsLoweringPhase$1
            @Override // kotlin.jvm.functions.Function1
            public final BodyLoweringPass invoke(JsIrBackendContext context) {
                Intrinsics.checkNotNullParameter(context, "context");
                return new LocalDeclarationsLowering(context, null, null, false, false, false, null, 118, null);
            }
        }, "LocalDeclarationsLowering", "Move local declarations into nearest declaration container", SetsKt.setOf((Object[]) new BodyLowering[]{bodyLoweringMakeBodyLoweringPhase2, bodyLoweringMakeBodyLoweringPhase$default16}));
        localDeclarationsLoweringPhase = bodyLoweringMakeBodyLoweringPhase12;
        BodyLowering bodyLoweringMakeBodyLoweringPhase13 = makeBodyLoweringPhase(new Function1<JsIrBackendContext, BodyLoweringPass>() { // from class: org.jetbrains.kotlin.ir.backend.js.JsLoweringPhasesKt$localClassExtractionPhase$1
            @Override // kotlin.jvm.functions.Function1
            public final BodyLoweringPass invoke(JsIrBackendContext context) {
                Intrinsics.checkNotNullParameter(context, "context");
                return new LocalClassPopupLowering(context, null, 2, null);
            }
        }, "LocalClassExtractionPhase", "Move local declarations into nearest declaration container", SetsKt.setOf(bodyLoweringMakeBodyLoweringPhase12));
        localClassExtractionPhase = bodyLoweringMakeBodyLoweringPhase13;
        DeclarationLowering declarationLoweringMakeDeclarationTransformerPhase$default10 = makeDeclarationTransformerPhase$default(new Function1<JsIrBackendContext, DeclarationTransformer>() { // from class: org.jetbrains.kotlin.ir.backend.js.JsLoweringPhasesKt$innerClassesLoweringPhase$1
            @Override // kotlin.jvm.functions.Function1
            public final DeclarationTransformer invoke(JsIrBackendContext context) {
                Intrinsics.checkNotNullParameter(context, "context");
                return new InnerClassesLowering(context, context.getInnerClassesSupport());
            }
        }, "InnerClassesLowering", "Capture outer this reference to inner class", null, 8, null);
        innerClassesLoweringPhase = declarationLoweringMakeDeclarationTransformerPhase$default10;
        BodyLowering bodyLoweringMakeBodyLoweringPhase14 = makeBodyLoweringPhase(new Function1<JsIrBackendContext, BodyLoweringPass>() { // from class: org.jetbrains.kotlin.ir.backend.js.JsLoweringPhasesKt$innerClassesMemberBodyLoweringPhase$1
            @Override // kotlin.jvm.functions.Function1
            public final BodyLoweringPass invoke(JsIrBackendContext context) {
                Intrinsics.checkNotNullParameter(context, "context");
                return new InnerClassesMemberBodyLowering(context, context.getInnerClassesSupport());
            }
        }, "InnerClassesMemberBody", "Replace `this` with 'outer this' field references", SetsKt.setOf(declarationLoweringMakeDeclarationTransformerPhase$default10));
        innerClassesMemberBodyLoweringPhase = bodyLoweringMakeBodyLoweringPhase14;
        BodyLowering bodyLoweringMakeBodyLoweringPhase$default17 = makeBodyLoweringPhase$default(new Function1<JsIrBackendContext, BodyLoweringPass>() { // from class: org.jetbrains.kotlin.ir.backend.js.JsLoweringPhasesKt$innerClassConstructorCallsLoweringPhase$1
            @Override // kotlin.jvm.functions.Function1
            public final BodyLoweringPass invoke(JsIrBackendContext context) {
                Intrinsics.checkNotNullParameter(context, "context");
                return new InnerClassConstructorCallsLowering(context, context.getInnerClassesSupport());
            }
        }, "InnerClassConstructorCallsLowering", "Replace inner class constructor invocation", null, 8, null);
        innerClassConstructorCallsLoweringPhase = bodyLoweringMakeBodyLoweringPhase$default17;
        BodyLowering bodyLoweringMakeBodyLoweringPhase$default18 = makeBodyLoweringPhase$default(JsLoweringPhasesKt$suspendFunctionsLoweringPhase$1.INSTANCE, "SuspendFunctionsLowering", "Transform suspend functions into CoroutineImpl instance and build state machine", null, 8, null);
        suspendFunctionsLoweringPhase = bodyLoweringMakeBodyLoweringPhase$default18;
        DeclarationLowering declarationLoweringMakeDeclarationTransformerPhase$default11 = makeDeclarationTransformerPhase$default(JsLoweringPhasesKt$addContinuationToNonLocalSuspendFunctionsLoweringPhase$1.INSTANCE, "AddContinuationToNonLocalSuspendFunctionsLowering", "Add explicit continuation as last parameter of non-local suspend functions", null, 8, null);
        addContinuationToNonLocalSuspendFunctionsLoweringPhase = declarationLoweringMakeDeclarationTransformerPhase$default11;
        BodyLowering bodyLoweringMakeBodyLoweringPhase$default19 = makeBodyLoweringPhase$default(JsLoweringPhasesKt$addContinuationToLocalSuspendFunctionsLoweringPhase$1.INSTANCE, "AddContinuationToLocalSuspendFunctionsLowering", "Add explicit continuation as last parameter of local suspend functions", null, 8, null);
        addContinuationToLocalSuspendFunctionsLoweringPhase = bodyLoweringMakeBodyLoweringPhase$default19;
        BodyLowering bodyLoweringMakeBodyLoweringPhase15 = makeBodyLoweringPhase(JsLoweringPhasesKt$addContinuationToFunctionCallsLoweringPhase$1.INSTANCE, "AddContinuationToFunctionCallsLowering", "Replace suspend function calls with calls with continuation", SetsKt.setOf((Object[]) new Lowering[]{bodyLoweringMakeBodyLoweringPhase$default19, declarationLoweringMakeDeclarationTransformerPhase$default11}));
        addContinuationToFunctionCallsLoweringPhase = bodyLoweringMakeBodyLoweringPhase15;
        DeclarationLowering declarationLoweringMakeDeclarationTransformerPhase$default12 = makeDeclarationTransformerPhase$default(JsLoweringPhasesKt$privateMembersLoweringPhase$1.INSTANCE, "PrivateMembersLowering", "Extract private members from classes", null, 8, null);
        privateMembersLoweringPhase = declarationLoweringMakeDeclarationTransformerPhase$default12;
        BodyLowering bodyLoweringMakeBodyLoweringPhase$default20 = makeBodyLoweringPhase$default(JsLoweringPhasesKt$privateMemberUsagesLoweringPhase$1.INSTANCE, "PrivateMemberUsagesLowering", "Rewrite the private member usages", null, 8, null);
        privateMemberUsagesLoweringPhase = bodyLoweringMakeBodyLoweringPhase$default20;
        BodyLowering bodyLoweringMakeBodyLoweringPhase$default21 = makeBodyLoweringPhase$default(JsLoweringPhasesKt$propertyReferenceLoweringPhase$1.INSTANCE, "PropertyReferenceLowering", "Transform property references", null, 8, null);
        propertyReferenceLoweringPhase = bodyLoweringMakeBodyLoweringPhase$default21;
        BodyLowering bodyLoweringMakeBodyLoweringPhase16 = makeBodyLoweringPhase(JsLoweringPhasesKt$interopCallableReferenceLoweringPhase$1.INSTANCE, "InteropCallableReferenceLowering", "Interop layer for function references and lambdas", SetsKt.setOf((Object[]) new BodyLowering[]{bodyLoweringMakeBodyLoweringPhase$default18, bodyLoweringMakeBodyLoweringPhase12, bodyLoweringMakeBodyLoweringPhase$default16, bodyLoweringMakeBodyLoweringPhase10}));
        interopCallableReferenceLoweringPhase = bodyLoweringMakeBodyLoweringPhase16;
        DeclarationLowering declarationLoweringMakeDeclarationTransformerPhase$default13 = makeDeclarationTransformerPhase$default(JsLoweringPhasesKt$defaultArgumentStubGeneratorPhase$1.INSTANCE, "DefaultArgumentStubGenerator", "Generate synthetic stubs for functions with default parameter values", null, 8, null);
        defaultArgumentStubGeneratorPhase = declarationLoweringMakeDeclarationTransformerPhase$default13;
        DeclarationLowering declarationLoweringMakeDeclarationTransformerPhase10 = makeDeclarationTransformerPhase(JsLoweringPhasesKt$defaultArgumentPatchOverridesPhase$1.INSTANCE, "DefaultArgumentsPatchOverrides", "Patch overrides for fake override dispatch functions", SetsKt.setOf(declarationLoweringMakeDeclarationTransformerPhase$default13));
        defaultArgumentPatchOverridesPhase = declarationLoweringMakeDeclarationTransformerPhase10;
        BodyLowering bodyLoweringMakeBodyLoweringPhase17 = makeBodyLoweringPhase(JsLoweringPhasesKt$defaultParameterInjectorPhase$1.INSTANCE, "DefaultParameterInjector", "Replace callsite with default parameters with corresponding stub function", SetsKt.setOf((Object[]) new Lowering[]{bodyLoweringMakeBodyLoweringPhase16, declarationLoweringMakeDeclarationTransformerPhase$default10}));
        defaultParameterInjectorPhase = bodyLoweringMakeBodyLoweringPhase17;
        DeclarationLowering declarationLoweringMakeDeclarationTransformerPhase$default14 = makeDeclarationTransformerPhase$default(JsLoweringPhasesKt$defaultParameterCleanerPhase$1.INSTANCE, "DefaultParameterCleaner", "Clean default parameters up", null, 8, null);
        defaultParameterCleanerPhase = declarationLoweringMakeDeclarationTransformerPhase$default14;
        BodyLowering bodyLoweringMakeBodyLoweringPhase18 = makeBodyLoweringPhase(JsLoweringPhasesKt$varargLoweringPhase$1.INSTANCE, "VarargLowering", "Lower vararg arguments", SetsKt.setOf(bodyLoweringMakeBodyLoweringPhase16));
        varargLoweringPhase = bodyLoweringMakeBodyLoweringPhase18;
        DeclarationLowering declarationLoweringMakeDeclarationTransformerPhase$default15 = makeDeclarationTransformerPhase$default(new Function1<JsIrBackendContext, DeclarationTransformer>() { // from class: org.jetbrains.kotlin.ir.backend.js.JsLoweringPhasesKt$propertiesLoweringPhase$1
            @Override // kotlin.jvm.functions.Function1
            public final DeclarationTransformer invoke(JsIrBackendContext it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return new PropertiesLowering();
            }
        }, "PropertiesLowering", "Move fields and accessors out from its property", null, 8, null);
        propertiesLoweringPhase = declarationLoweringMakeDeclarationTransformerPhase$default15;
        DeclarationLowering declarationLoweringMakeDeclarationTransformerPhase11 = makeDeclarationTransformerPhase(JsLoweringPhasesKt$primaryConstructorLoweringPhase$1.INSTANCE, "PrimaryConstructorLowering", "Creates primary constructor if it doesn't exist", SetsKt.setOf(declarationLoweringMakeDeclarationTransformerPhase$default8));
        primaryConstructorLoweringPhase = declarationLoweringMakeDeclarationTransformerPhase11;
        BodyLowering bodyLoweringMakeBodyLoweringPhase19 = makeBodyLoweringPhase(JsLoweringPhasesKt$delegateToPrimaryConstructorLoweringPhase$1.INSTANCE, "DelegateToSyntheticPrimaryConstructor", "Delegates to synthetic primary constructor", SetsKt.setOf(declarationLoweringMakeDeclarationTransformerPhase11));
        delegateToPrimaryConstructorLoweringPhase = bodyLoweringMakeBodyLoweringPhase19;
        DeclarationLowering declarationLoweringMakeDeclarationTransformerPhase$default16 = makeDeclarationTransformerPhase$default(JsLoweringPhasesKt$annotationConstructorLowering$1.INSTANCE, "AnnotationConstructorLowering", "Generate annotation constructor body", null, 8, null);
        annotationConstructorLowering = declarationLoweringMakeDeclarationTransformerPhase$default16;
        BodyLowering bodyLoweringMakeBodyLoweringPhase20 = makeBodyLoweringPhase(JsLoweringPhasesKt$initializersLoweringPhase$1.INSTANCE, "InitializersLowering", "Merge init block and field initializers into [primary] constructor", SetsKt.setOf((Object[]) new Lowering[]{declarationLoweringMakeDeclarationTransformerPhase$default8, declarationLoweringMakeDeclarationTransformerPhase11, declarationLoweringMakeDeclarationTransformerPhase$default16, bodyLoweringMakeBodyLoweringPhase13}));
        initializersLoweringPhase = bodyLoweringMakeBodyLoweringPhase20;
        DeclarationLowering declarationLoweringMakeDeclarationTransformerPhase12 = makeDeclarationTransformerPhase(JsLoweringPhasesKt$initializersCleanupLoweringPhase$1.INSTANCE, "InitializersCleanupLowering", "Remove non-static anonymous initializers and field init expressions", SetsKt.setOf(bodyLoweringMakeBodyLoweringPhase20));
        initializersCleanupLoweringPhase = declarationLoweringMakeDeclarationTransformerPhase12;
        BodyLowering bodyLoweringMakeBodyLoweringPhase$default22 = makeBodyLoweringPhase$default(JsLoweringPhasesKt$multipleCatchesLoweringPhase$1.INSTANCE, "MultipleCatchesLowering", "Replace multiple catches with single one", null, 8, null);
        multipleCatchesLoweringPhase = bodyLoweringMakeBodyLoweringPhase$default22;
        BodyLowering bodyLoweringMakeBodyLoweringPhase21 = makeBodyLoweringPhase(JsLoweringPhasesKt$errorExpressionLoweringPhase$1.INSTANCE, "errorExpressionLoweringPhase", "Transform error expressions into simple ir code", SetsKt.setOf(bodyLoweringMakeBodyLoweringPhase$default22));
        errorExpressionLoweringPhase = bodyLoweringMakeBodyLoweringPhase21;
        DeclarationLowering declarationLoweringMakeDeclarationTransformerPhase$default17 = makeDeclarationTransformerPhase$default(JsLoweringPhasesKt$errorDeclarationLoweringPhase$1.INSTANCE, "errorDeclarationLoweringPhase", "Transform error declarations into simple ir code", null, 8, null);
        errorDeclarationLoweringPhase = declarationLoweringMakeDeclarationTransformerPhase$default17;
        DeclarationLowering declarationLoweringMakeDeclarationTransformerPhase13 = makeDeclarationTransformerPhase(JsLoweringPhasesKt$bridgesConstructionPhase$1.INSTANCE, "BridgesConstruction", "Generate bridges", SetsKt.setOf(bodyLoweringMakeBodyLoweringPhase$default18));
        bridgesConstructionPhase = declarationLoweringMakeDeclarationTransformerPhase13;
        BodyLowering bodyLoweringMakeBodyLoweringPhase$default23 = makeBodyLoweringPhase$default(JsLoweringPhasesKt$singleAbstractMethodPhase$1.INSTANCE, "SingleAbstractMethod", "Replace SAM conversions with instances of interface-implementing classes", null, 8, null);
        singleAbstractMethodPhase = bodyLoweringMakeBodyLoweringPhase$default23;
        BodyLowering bodyLoweringMakeBodyLoweringPhase22 = makeBodyLoweringPhase(JsLoweringPhasesKt$typeOperatorLoweringPhase$1.INSTANCE, "TypeOperatorLowering", "Lower IrTypeOperator with corresponding logic", SetsKt.setOf((Object[]) new Lowering[]{declarationLoweringMakeDeclarationTransformerPhase13, declarationLoweringMakeDeclarationTransformerPhase3, bodyLoweringMakeBodyLoweringPhase$default23, bodyLoweringMakeBodyLoweringPhase21, bodyLoweringMakeBodyLoweringPhase16}));
        typeOperatorLoweringPhase = bodyLoweringMakeBodyLoweringPhase22;
        DeclarationLowering declarationLoweringMakeDeclarationTransformerPhase14 = makeDeclarationTransformerPhase(JsLoweringPhasesKt$secondaryConstructorLoweringPhase$1.INSTANCE, "SecondaryConstructorLoweringPhase", "Generate static functions for each secondary constructor", SetsKt.setOf(declarationLoweringMakeDeclarationTransformerPhase$default10));
        secondaryConstructorLoweringPhase = declarationLoweringMakeDeclarationTransformerPhase14;
        BodyLowering bodyLoweringMakeBodyLoweringPhase23 = makeBodyLoweringPhase(JsLoweringPhasesKt$secondaryFactoryInjectorLoweringPhase$1.INSTANCE, "SecondaryFactoryInjectorLoweringPhase", "Replace usage of secondary constructor with corresponding static function", SetsKt.setOf(declarationLoweringMakeDeclarationTransformerPhase$default10));
        secondaryFactoryInjectorLoweringPhase = bodyLoweringMakeBodyLoweringPhase23;
        BodyLowering bodyLoweringMakeBodyLoweringPhase$default24 = makeBodyLoweringPhase$default(JsLoweringPhasesKt$constLoweringPhase$1.INSTANCE, "ConstLowering", "Wrap Long and Char constants into constructor invocation", null, 8, null);
        constLoweringPhase = bodyLoweringMakeBodyLoweringPhase$default24;
        DeclarationLowering declarationLoweringMakeDeclarationTransformerPhase$default18 = makeDeclarationTransformerPhase$default(new Function1<JsIrBackendContext, DeclarationTransformer>() { // from class: org.jetbrains.kotlin.ir.backend.js.JsLoweringPhasesKt$inlineClassDeclarationLoweringPhase$1
            @Override // kotlin.jvm.functions.Function1
            public final DeclarationTransformer invoke(JsIrBackendContext it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return new InlineClassLowering(it).getInlineClassDeclarationLowering();
            }
        }, "InlineClassDeclarationLowering", "Handle inline class declarations", null, 8, null);
        inlineClassDeclarationLoweringPhase = declarationLoweringMakeDeclarationTransformerPhase$default18;
        BodyLowering bodyLoweringMakeBodyLoweringPhase24 = makeBodyLoweringPhase(new Function1<JsIrBackendContext, BodyLoweringPass>() { // from class: org.jetbrains.kotlin.ir.backend.js.JsLoweringPhasesKt$inlineClassUsageLoweringPhase$1
            @Override // kotlin.jvm.functions.Function1
            public final BodyLoweringPass invoke(JsIrBackendContext it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return new InlineClassLowering(it).getInlineClassUsageLowering();
            }
        }, "InlineClassUsageLowering", "Handle inline class usages", SetsKt.setOf(bodyLoweringMakeBodyLoweringPhase$default24));
        inlineClassUsageLoweringPhase = bodyLoweringMakeBodyLoweringPhase24;
        BodyLowering bodyLoweringMakeBodyLoweringPhase$default25 = makeBodyLoweringPhase$default(new Function1<JsIrBackendContext, BodyLoweringPass>() { // from class: org.jetbrains.kotlin.ir.backend.js.JsLoweringPhasesKt$autoboxingTransformerPhase$1
            @Override // kotlin.jvm.functions.Function1
            public final BodyLoweringPass invoke(JsIrBackendContext it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return new AutoboxingTransformer(it, true);
            }
        }, "AutoboxingTransformer", "Insert box/unbox intrinsics", null, 8, null);
        autoboxingTransformerPhase = bodyLoweringMakeBodyLoweringPhase$default25;
        BodyLowering bodyLoweringMakeBodyLoweringPhase25 = makeBodyLoweringPhase(JsLoweringPhasesKt$blockDecomposerLoweringPhase$1.INSTANCE, "BlockDecomposerLowering", "Transform statement-like-expression nodes into pure-statement to make it easily transform into JS", SetsKt.setOf((Object[]) new BodyLowering[]{bodyLoweringMakeBodyLoweringPhase22, bodyLoweringMakeBodyLoweringPhase$default18}));
        blockDecomposerLoweringPhase = bodyLoweringMakeBodyLoweringPhase25;
        BodyLowering bodyLoweringMakeBodyLoweringPhase26 = makeBodyLoweringPhase(JsLoweringPhasesKt$jsClassUsageInReflectionPhase$1.INSTANCE, "JsClassUsageInReflectionLowering", "[Optimization] Eliminate ClassReference and GetClassExpression usages in a simple case of usage raw js constructor", SetsKt.setOf(bodyLoweringMakeBodyLoweringPhase6));
        jsClassUsageInReflectionPhase = bodyLoweringMakeBodyLoweringPhase26;
        BodyLowering bodyLoweringMakeBodyLoweringPhase27 = makeBodyLoweringPhase(JsLoweringPhasesKt$classReferenceLoweringPhase$1.INSTANCE, "ClassReferenceLowering", "Handle class references", SetsKt.setOf(bodyLoweringMakeBodyLoweringPhase26));
        classReferenceLoweringPhase = bodyLoweringMakeBodyLoweringPhase27;
        BodyLowering bodyLoweringMakeBodyLoweringPhase$default26 = makeBodyLoweringPhase$default(JsLoweringPhasesKt$primitiveCompanionLoweringPhase$1.INSTANCE, "PrimitiveCompanionLowering", "Replace common companion object access with platform one", null, 8, null);
        primitiveCompanionLoweringPhase = bodyLoweringMakeBodyLoweringPhase$default26;
        BodyLowering bodyLoweringMakeBodyLoweringPhase$default27 = makeBodyLoweringPhase$default(JsLoweringPhasesKt$callsLoweringPhase$1.INSTANCE, "CallsLowering", "Handle intrinsics", null, 8, null);
        callsLoweringPhase = bodyLoweringMakeBodyLoweringPhase$default27;
        staticMembersLoweringPhase = makeDeclarationTransformerPhase$default(JsLoweringPhasesKt$staticMembersLoweringPhase$1.INSTANCE, "StaticMembersLowering", "Move static member declarations to top-level", null, 8, null);
        DeclarationLowering declarationLoweringMakeDeclarationTransformerPhase15 = makeDeclarationTransformerPhase(JsLoweringPhasesKt$objectDeclarationLoweringPhase$1.INSTANCE, "ObjectDeclarationLowering", "Create lazy object instance generator functions", SetsKt.setOf(declarationLoweringMakeDeclarationTransformerPhase5));
        objectDeclarationLoweringPhase = declarationLoweringMakeDeclarationTransformerPhase15;
        BodyLowering bodyLoweringMakeBodyLoweringPhase28 = makeBodyLoweringPhase(JsLoweringPhasesKt$invokeStaticInitializersPhase$1.INSTANCE, "IntroduceStaticInitializersLowering", "Invoke companion object's initializers from companion object in object constructor", SetsKt.setOf(declarationLoweringMakeDeclarationTransformerPhase15));
        invokeStaticInitializersPhase = bodyLoweringMakeBodyLoweringPhase28;
        DeclarationLowering declarationLoweringMakeDeclarationTransformerPhase$default19 = makeDeclarationTransformerPhase$default(JsLoweringPhasesKt$es6AddBoxParameterToConstructorsLowering$1.INSTANCE, "ES6AddBoxParameterToConstructorsLowering", "Add box parameter to a constructor if needed", null, 8, null);
        es6AddBoxParameterToConstructorsLowering = declarationLoweringMakeDeclarationTransformerPhase$default19;
        DeclarationLowering declarationLoweringMakeDeclarationTransformerPhase16 = makeDeclarationTransformerPhase(JsLoweringPhasesKt$es6ConstructorLowering$1.INSTANCE, "ES6ConstructorLowering", "Lower constructors declarations to support ES classes", SetsKt.setOf(declarationLoweringMakeDeclarationTransformerPhase$default19));
        es6ConstructorLowering = declarationLoweringMakeDeclarationTransformerPhase16;
        BodyLowering bodyLoweringMakeBodyLoweringPhase29 = makeBodyLoweringPhase(JsLoweringPhasesKt$es6ConstructorUsageLowering$1.INSTANCE, "ES6ConstructorCallLowering", "Lower constructor usages to support ES classes", SetsKt.setOf(declarationLoweringMakeDeclarationTransformerPhase16));
        es6ConstructorUsageLowering = bodyLoweringMakeBodyLoweringPhase29;
        BodyLowering bodyLoweringMakeBodyLoweringPhase30 = makeBodyLoweringPhase(JsLoweringPhasesKt$objectUsageLoweringPhase$1.INSTANCE, "ObjectUsageLowering", "Transform IrGetObjectValue into instance generator call", SetsKt.setOf(declarationLoweringMakeDeclarationTransformerPhase11));
        objectUsageLoweringPhase = bodyLoweringMakeBodyLoweringPhase30;
        BodyLowering bodyLoweringMakeBodyLoweringPhase$default28 = makeBodyLoweringPhase$default(JsLoweringPhasesKt$escapedIdentifiersLowering$1.INSTANCE, "EscapedIdentifiersLowering", "Convert global variables with invalid names access to globalThis member expression", null, 8, null);
        escapedIdentifiersLowering = bodyLoweringMakeBodyLoweringPhase$default28;
        DeclarationLowering declarationLoweringMakeDeclarationTransformerPhase$default20 = makeDeclarationTransformerPhase$default(JsLoweringPhasesKt$implicitlyExportedDeclarationsMarkingLowering$1.INSTANCE, "ImplicitlyExportedDeclarationsMarkingLowering", "Add @JsImplicitExport annotation to declarations which are not exported but are used inside other exported declarations as a type", null, 8, null);
        implicitlyExportedDeclarationsMarkingLowering = declarationLoweringMakeDeclarationTransformerPhase$default20;
        BodyLowering bodyLoweringMakeBodyLoweringPhase$default29 = makeBodyLoweringPhase$default(new Function1<JsIrBackendContext, BodyLoweringPass>() { // from class: org.jetbrains.kotlin.ir.backend.js.JsLoweringPhasesKt$cleanupLoweringPhase$1
            @Override // kotlin.jvm.functions.Function1
            public final BodyLoweringPass invoke(JsIrBackendContext it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return new CleanupLowering();
            }
        }, "CleanupLowering", "Clean up IR before codegen", null, 8, null);
        cleanupLoweringPhase = bodyLoweringMakeBodyLoweringPhase$default29;
        DeclarationLowering declarationLoweringMakeDeclarationTransformerPhase$default21 = makeDeclarationTransformerPhase$default(JsLoweringPhasesKt$jsSuspendArityStorePhase$1.INSTANCE, "JsSuspendArityStoreLowering", "Store arity for suspend functions to not remove it during DCE", null, 8, null);
        jsSuspendArityStorePhase = declarationLoweringMakeDeclarationTransformerPhase$default21;
        ModuleLowering moduleLowering9 = toModuleLowering(makeJsModulePhase$default(new Function1<JsIrBackendContext, FileLoweringPass>() { // from class: org.jetbrains.kotlin.ir.backend.js.JsLoweringPhasesKt$constEvaluationPhase$1
            @Override // kotlin.jvm.functions.Function1
            public final FileLoweringPass invoke(JsIrBackendContext context) {
                Intrinsics.checkNotNullParameter(context, "context");
                return new ConstEvaluationLowering(context, false, new IrInterpreterConfiguration(JsPlatforms.INSTANCE.getDefaultJsPlatform(), 0, 0, false, true, false, false, 46, null), 2, null);
            }
        }, "ConstEvaluationLowering", "Evaluate functions that are marked as `IntrinsicConstEvaluation`", null, 8, null));
        constEvaluationPhase = moduleLowering9;
        List<Lowering> listListOf = CollectionsKt.listOf((Object[]) new Lowering[]{moduleLowering3, moduleLowering, declarationLoweringMakeDeclarationTransformerPhase$default2, moduleLowering6, moduleLowering5, declarationLoweringMakeDeclarationTransformerPhase$default3, declarationLoweringMakeDeclarationTransformerPhase$default4, declarationLoweringMakeDeclarationTransformerPhase$default7, bodyLoweringMakeBodyLoweringPhase$default3, bodyLoweringMakeBodyLoweringPhase$default4, bodyLoweringMakeBodyLoweringPhase, declarationLoweringMakeDeclarationTransformerPhase$default5, declarationLoweringMakeDeclarationTransformerPhase$default6, bodyLoweringMakeBodyLoweringPhase$default, bodyLoweringMakeBodyLoweringPhase2, bodyLoweringMakeBodyLoweringPhase3, bodyLoweringMakeBodyLoweringPhase4, bodyLoweringMakeBodyLoweringPhase5, bodyLoweringMakeBodyLoweringPhase$default5, bodyLoweringMakeBodyLoweringPhase$default6, declarationLoweringMakeDeclarationTransformerPhase, bodyLoweringMakeBodyLoweringPhase6, moduleLowering9, declarationLoweringMakeDeclarationTransformerPhase2, declarationLoweringMakeDeclarationTransformerPhase3, moduleLowering4, moduleLowering7, bodyLoweringMakeBodyLoweringPhase10, bodyLoweringMakeBodyLoweringPhase$default23, bodyLoweringMakeBodyLoweringPhase$default8, declarationLoweringMakeDeclarationTransformerPhase$default8, bodyLoweringMakeBodyLoweringPhase$default9, bodyLoweringMakeBodyLoweringPhase$default16, bodyLoweringMakeBodyLoweringPhase12, bodyLoweringMakeBodyLoweringPhase13, declarationLoweringMakeDeclarationTransformerPhase$default10, bodyLoweringMakeBodyLoweringPhase14, bodyLoweringMakeBodyLoweringPhase$default17, bodyLoweringMakeBodyLoweringPhase26, declarationLoweringMakeDeclarationTransformerPhase$default15, declarationLoweringMakeDeclarationTransformerPhase11, bodyLoweringMakeBodyLoweringPhase19, declarationLoweringMakeDeclarationTransformerPhase$default16, bodyLoweringMakeBodyLoweringPhase20, declarationLoweringMakeDeclarationTransformerPhase12, bodyLoweringMakeBodyLoweringPhase$default2, declarationLoweringMakeDeclarationTransformerPhase$default, moduleLowering8, declarationLoweringMakeDeclarationTransformerPhase4, bodyLoweringMakeBodyLoweringPhase8, declarationLoweringMakeDeclarationTransformerPhase5, declarationLoweringMakeDeclarationTransformerPhase6, declarationLoweringMakeDeclarationTransformerPhase7, bodyLoweringMakeBodyLoweringPhase9, bodyLoweringMakeBodyLoweringPhase$default10, declarationLoweringMakeDeclarationTransformerPhase8, bodyLoweringMakeBodyLoweringPhase$default18, bodyLoweringMakeBodyLoweringPhase$default21, bodyLoweringMakeBodyLoweringPhase16, declarationLoweringMakeDeclarationTransformerPhase$default21, declarationLoweringMakeDeclarationTransformerPhase$default11, bodyLoweringMakeBodyLoweringPhase$default19, bodyLoweringMakeBodyLoweringPhase15, bodyLoweringMakeBodyLoweringPhase11, bodyLoweringMakeBodyLoweringPhase$default11, bodyLoweringMakeBodyLoweringPhase$default12, bodyLoweringMakeBodyLoweringPhase$default26, bodyLoweringMakeBodyLoweringPhase$default13, declarationLoweringMakeDeclarationTransformerPhase$default9, bodyLoweringMakeBodyLoweringPhase$default14, declarationLoweringMakeDeclarationTransformerPhase9, bodyLoweringMakeBodyLoweringPhase$default15, declarationLoweringMakeDeclarationTransformerPhase$default12, bodyLoweringMakeBodyLoweringPhase$default20, declarationLoweringMakeDeclarationTransformerPhase$default13, declarationLoweringMakeDeclarationTransformerPhase10, bodyLoweringMakeBodyLoweringPhase17, declarationLoweringMakeDeclarationTransformerPhase$default14, bodyLoweringMakeBodyLoweringPhase$default7, bodyLoweringMakeBodyLoweringPhase7, bodyLoweringMakeBodyLoweringPhase18, bodyLoweringMakeBodyLoweringPhase$default22, bodyLoweringMakeBodyLoweringPhase21, declarationLoweringMakeDeclarationTransformerPhase$default17, declarationLoweringMakeDeclarationTransformerPhase13, bodyLoweringMakeBodyLoweringPhase22, declarationLoweringMakeDeclarationTransformerPhase14, bodyLoweringMakeBodyLoweringPhase23, bodyLoweringMakeBodyLoweringPhase27, bodyLoweringMakeBodyLoweringPhase$default24, declarationLoweringMakeDeclarationTransformerPhase$default18, bodyLoweringMakeBodyLoweringPhase24, bodyLoweringMakeBodyLoweringPhase$default25, declarationLoweringMakeDeclarationTransformerPhase15, bodyLoweringMakeBodyLoweringPhase25, bodyLoweringMakeBodyLoweringPhase28, bodyLoweringMakeBodyLoweringPhase30, declarationLoweringMakeDeclarationTransformerPhase$default19, declarationLoweringMakeDeclarationTransformerPhase16, bodyLoweringMakeBodyLoweringPhase29, bodyLoweringMakeBodyLoweringPhase$default27, bodyLoweringMakeBodyLoweringPhase$default28, declarationLoweringMakeDeclarationTransformerPhase$default20, bodyLoweringMakeBodyLoweringPhase$default29, moduleLowering2});
        loweringList = listListOf;
        jsPhases = new NamedCompilerPhase<>("IrModuleLowering", "IR module lowering", null, toCompilerPhase(listListOf), null, null, null, SetsKt.setOf((Object[]) new Function3[]{LowerKt.toMultiModuleAction(DumperVerifierKt.getDefaultDumper()), LowerKt.toMultiModuleAction(DumperVerifierKt.getValidationAction())}), 1, 116, null);
        DeclarationLowering declarationLoweringMakeDeclarationTransformerPhase$default22 = makeDeclarationTransformerPhase$default(JsLoweringPhasesKt$es6CollectConstructorsWhichNeedBoxParameterLowering$1.INSTANCE, "ES6CollectConstructorsWhichNeedBoxParameters", "[Optimization] Collect all of the constructors which requires box parameter", null, 8, null);
        es6CollectConstructorsWhichNeedBoxParameterLowering = declarationLoweringMakeDeclarationTransformerPhase$default22;
        BodyLowering bodyLoweringMakeBodyLoweringPhase31 = makeBodyLoweringPhase(JsLoweringPhasesKt$es6BoxParameterOptimization$1.INSTANCE, "ES6ConstructorBoxParameterOptimizationLowering", "[Optimization] Remove box parameter from the constructors which don't require box parameter", SetsKt.setOf(declarationLoweringMakeDeclarationTransformerPhase$default22));
        es6BoxParameterOptimization = bodyLoweringMakeBodyLoweringPhase31;
        DeclarationLowering declarationLoweringMakeDeclarationTransformerPhase$default23 = makeDeclarationTransformerPhase$default(JsLoweringPhasesKt$es6CollectPrimaryConstructorsWhichCouldBeOptimizedLowering$1.INSTANCE, "ES6CollectPrimaryConstructorsWhichCouldBeOptimizedLowering", "[Optimization] Collect all of the constructors which could be translated into a regular constructor", null, 8, null);
        es6CollectPrimaryConstructorsWhichCouldBeOptimizedLowering = declarationLoweringMakeDeclarationTransformerPhase$default23;
        DeclarationLowering declarationLoweringMakeDeclarationTransformerPhase17 = makeDeclarationTransformerPhase(JsLoweringPhasesKt$es6PrimaryConstructorOptimizationLowering$1.INSTANCE, "ES6PrimaryConstructorOptimizationLowering", "[Optimization] Replace synthetically generated static fabric method with a plain old ES6 constructors whenever it's possible", SetsKt.setOf(declarationLoweringMakeDeclarationTransformerPhase$default23));
        es6PrimaryConstructorOptimizationLowering = declarationLoweringMakeDeclarationTransformerPhase17;
        BodyLowering bodyLoweringMakeBodyLoweringPhase32 = makeBodyLoweringPhase(JsLoweringPhasesKt$es6PrimaryConstructorUsageOptimizationLowering$1.INSTANCE, "ES6PrimaryConstructorUsageOptimizationLowering", "[Optimization] Replace usage of synthetically generated static fabric method with a plain old ES6 constructors whenever it's possible", SetsKt.setOf((Object[]) new Lowering[]{bodyLoweringMakeBodyLoweringPhase31, declarationLoweringMakeDeclarationTransformerPhase17}));
        es6PrimaryConstructorUsageOptimizationLowering = bodyLoweringMakeBodyLoweringPhase32;
        DeclarationLowering declarationLoweringMakeDeclarationTransformerPhase$default24 = makeDeclarationTransformerPhase$default(JsLoweringPhasesKt$purifyObjectInstanceGetters$1.INSTANCE, "PurifyObjectInstanceGettersLowering", "[Optimization] Make object instance getter functions pure whenever it's possible", null, 8, null);
        purifyObjectInstanceGetters = declarationLoweringMakeDeclarationTransformerPhase$default24;
        BodyLowering bodyLoweringMakeBodyLoweringPhase33 = makeBodyLoweringPhase(JsLoweringPhasesKt$inlineObjectsWithPureInitialization$1.INSTANCE, "InlineObjectsWithPureInitializationLowering", "[Optimization] Inline object instance fields getters whenever it's possible", SetsKt.setOf(declarationLoweringMakeDeclarationTransformerPhase$default24));
        inlineObjectsWithPureInitialization = bodyLoweringMakeBodyLoweringPhase33;
        List<Lowering> listListOf2 = CollectionsKt.listOf((Object[]) new Lowering[]{declarationLoweringMakeDeclarationTransformerPhase$default22, declarationLoweringMakeDeclarationTransformerPhase$default23, bodyLoweringMakeBodyLoweringPhase31, declarationLoweringMakeDeclarationTransformerPhase17, bodyLoweringMakeBodyLoweringPhase32, declarationLoweringMakeDeclarationTransformerPhase$default24, bodyLoweringMakeBodyLoweringPhase33});
        optimizationLoweringList = listListOf2;
        jsOptimizationPhases = new NamedCompilerPhase<>("IrModuleOptimizationLowering", "IR module optimization lowering", null, toCompilerPhase(listListOf2), null, null, null, SetsKt.setOf((Object[]) new Function3[]{LowerKt.toMultiModuleAction(DumperVerifierKt.getDefaultDumper()), LowerKt.toMultiModuleAction(DumperVerifierKt.getValidationAction())}), 1, 116, null);
    }

    public static final ModuleLowering getScriptRemoveReceiverLowering() {
        return scriptRemoveReceiverLowering;
    }

    public static final ModuleLowering getCreateScriptFunctionsPhase() {
        return createScriptFunctionsPhase;
    }

    public static final ModuleLowering getConstEvaluationPhase() {
        return constEvaluationPhase;
    }

    public static final List<Lowering> getLoweringList() {
        return loweringList;
    }

    public static final NamedCompilerPhase<JsIrBackendContext, Iterable<IrModuleFragment>> getJsPhases() {
        return jsPhases;
    }

    public static final List<Lowering> getOptimizationLoweringList() {
        return optimizationLoweringList;
    }

    public static final NamedCompilerPhase<JsIrBackendContext, Iterable<IrModuleFragment>> getJsOptimizationPhases() {
        return jsOptimizationPhases;
    }
}
