package org.jetbrains.kotlin.backend.wasm;

import com.gaurav.avnc.vnc.XKeySym;
import com.google.android.gms.cast.MediaTrack;
import java.util.Iterator;
import java.util.Set;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.SetsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.backend.common.FileLoweringPass;
import org.jetbrains.kotlin.backend.common.LoggingContext;
import org.jetbrains.kotlin.backend.common.LowerKt;
import org.jetbrains.kotlin.backend.common.lower.DefaultArgumentStubGenerator;
import org.jetbrains.kotlin.backend.common.lower.DefaultParameterInjector;
import org.jetbrains.kotlin.backend.common.lower.InlineClassLowering;
import org.jetbrains.kotlin.backend.common.lower.InnerClassConstructorCallsLowering;
import org.jetbrains.kotlin.backend.common.lower.InnerClassesLowering;
import org.jetbrains.kotlin.backend.common.lower.InnerClassesMemberBodyLowering;
import org.jetbrains.kotlin.backend.common.lower.LocalDelegatedPropertiesLowering;
import org.jetbrains.kotlin.backend.common.lower.MaskedDefaultArgumentFunctionFactory;
import org.jetbrains.kotlin.backend.common.lower.PropertiesLowering;
import org.jetbrains.kotlin.backend.common.lower.inline.FunctionInlining;
import org.jetbrains.kotlin.backend.common.lower.inline.LocalClassesExtractionFromInlineFunctionsLowering;
import org.jetbrains.kotlin.backend.common.phaser.AbstractNamedCompilerPhase;
import org.jetbrains.kotlin.backend.common.phaser.DumperVerifierKt;
import org.jetbrains.kotlin.backend.common.phaser.NamedCompilerPhase;
import org.jetbrains.kotlin.backend.common.phaser.PhaseBuildersKt;
import org.jetbrains.kotlin.backend.common.phaser.PhaseConfigurationService;
import org.jetbrains.kotlin.backend.common.phaser.PhaserState;
import org.jetbrains.kotlin.backend.common.phaser.SameTypeCompilerPhase;
import org.jetbrains.kotlin.backend.wasm.lower.ExcludeDeclarationsFromCodegenKt;
import org.jetbrains.kotlin.backend.wasm.lower.TestGeneratorKt;
import org.jetbrains.kotlin.backend.wasm.lower.WasmInlineFunctionResolver;
import org.jetbrains.kotlin.ir.backend.js.lower.AutoboxingTransformer;
import org.jetbrains.kotlin.ir.backend.js.lower.inline.RemoveInlineDeclarationsWithReifiedTypeParametersLowering;
import org.jetbrains.kotlin.ir.declarations.IrModuleFragment;
import org.jetbrains.kotlin.ir.util.PatchDeclarationParentsKt;

/* JADX INFO: compiled from: WasmLoweringPhases.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000B\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u001c\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\bX\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\u001ax\u0010\\\u001a*\u0012\u0004\u0012\u00020\u0002\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00040\u00030\u0001j\u0014\u0012\u0004\u0012\u00020\u0002\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00040\u0003`\u00052\u0018\u0010]\u001a\u0014\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020_0^2\u0006\u0010`\u001a\u00020a2\u0006\u0010b\u001a\u00020a2\u001c\b\u0002\u0010c\u001a\u0016\u0012\u0012\u0012\u0010\u0012\u0004\u0012\u00020\u0002\u0012\u0002\b\u0003\u0012\u0002\b\u00030e0dH\u0002\u001ar\u0010f\u001a*\u0012\u0004\u0012\u00020\u0002\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00040\u00030\u0001j\u0014\u0012\u0004\u0012\u00020\u0002\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00040\u0003`\u00052\u0012\u0010g\u001a\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020i0h2\u0006\u0010b\u001a\u00020a2\u0006\u0010`\u001a\u00020a2\u001c\b\u0002\u0010c\u001a\u0016\u0012\u0012\u0012\u0010\u0012\u0004\u0012\u00020\u0002\u0012\u0002\b\u0003\u0012\u0002\b\u00030e0dH\u0002\"6\u0010\u0000\u001a*\u0012\u0004\u0012\u00020\u0002\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00040\u00030\u0001j\u0014\u0012\u0004\u0012\u00020\u0002\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00040\u0003`\u0005X\u0082\u0004¢\u0006\u0002\n\u0000\"6\u0010\u0006\u001a*\u0012\u0004\u0012\u00020\u0002\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00040\u00030\u0001j\u0014\u0012\u0004\u0012\u00020\u0002\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00040\u0003`\u0005X\u0082\u0004¢\u0006\u0002\n\u0000\"6\u0010\u0007\u001a*\u0012\u0004\u0012\u00020\u0002\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00040\u00030\u0001j\u0014\u0012\u0004\u0012\u00020\u0002\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00040\u0003`\u0005X\u0082\u0004¢\u0006\u0002\n\u0000\"6\u0010\b\u001a*\u0012\u0004\u0012\u00020\u0002\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00040\u00030\u0001j\u0014\u0012\u0004\u0012\u00020\u0002\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00040\u0003`\u0005X\u0082\u0004¢\u0006\u0002\n\u0000\"6\u0010\t\u001a*\u0012\u0004\u0012\u00020\u0002\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00040\u00030\u0001j\u0014\u0012\u0004\u0012\u00020\u0002\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00040\u0003`\u0005X\u0082\u0004¢\u0006\u0002\n\u0000\"6\u0010\n\u001a*\u0012\u0004\u0012\u00020\u0002\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00040\u00030\u0001j\u0014\u0012\u0004\u0012\u00020\u0002\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00040\u0003`\u0005X\u0082\u0004¢\u0006\u0002\n\u0000\"6\u0010\u000b\u001a*\u0012\u0004\u0012\u00020\u0002\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00040\u00030\u0001j\u0014\u0012\u0004\u0012\u00020\u0002\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00040\u0003`\u0005X\u0082\u0004¢\u0006\u0002\n\u0000\"6\u0010\f\u001a*\u0012\u0004\u0012\u00020\u0002\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00040\u00030\u0001j\u0014\u0012\u0004\u0012\u00020\u0002\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00040\u0003`\u0005X\u0082\u0004¢\u0006\u0002\n\u0000\"6\u0010\r\u001a*\u0012\u0004\u0012\u00020\u0002\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00040\u00030\u0001j\u0014\u0012\u0004\u0012\u00020\u0002\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00040\u0003`\u0005X\u0082\u0004¢\u0006\u0002\n\u0000\"6\u0010\u000e\u001a*\u0012\u0004\u0012\u00020\u0002\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00040\u00030\u0001j\u0014\u0012\u0004\u0012\u00020\u0002\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00040\u0003`\u0005X\u0082\u0004¢\u0006\u0002\n\u0000\"6\u0010\u000f\u001a*\u0012\u0004\u0012\u00020\u0002\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00040\u00030\u0001j\u0014\u0012\u0004\u0012\u00020\u0002\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00040\u0003`\u0005X\u0082\u0004¢\u0006\u0002\n\u0000\"6\u0010\u0010\u001a*\u0012\u0004\u0012\u00020\u0002\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00040\u00030\u0001j\u0014\u0012\u0004\u0012\u00020\u0002\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00040\u0003`\u0005X\u0082\u0004¢\u0006\u0002\n\u0000\"6\u0010\u0011\u001a*\u0012\u0004\u0012\u00020\u0002\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00040\u00030\u0001j\u0014\u0012\u0004\u0012\u00020\u0002\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00040\u0003`\u0005X\u0082\u0004¢\u0006\u0002\n\u0000\"6\u0010\u0012\u001a*\u0012\u0004\u0012\u00020\u0002\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00040\u00030\u0001j\u0014\u0012\u0004\u0012\u00020\u0002\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00040\u0003`\u0005X\u0082\u0004¢\u0006\u0002\n\u0000\"6\u0010\u0013\u001a*\u0012\u0004\u0012\u00020\u0002\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00040\u00030\u0001j\u0014\u0012\u0004\u0012\u00020\u0002\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00040\u0003`\u0005X\u0082\u0004¢\u0006\u0002\n\u0000\"6\u0010\u0014\u001a*\u0012\u0004\u0012\u00020\u0002\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00040\u00030\u0001j\u0014\u0012\u0004\u0012\u00020\u0002\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00040\u0003`\u0005X\u0082\u0004¢\u0006\u0002\n\u0000\"6\u0010\u0015\u001a*\u0012\u0004\u0012\u00020\u0002\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00040\u00030\u0001j\u0014\u0012\u0004\u0012\u00020\u0002\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00040\u0003`\u0005X\u0082\u0004¢\u0006\u0002\n\u0000\"6\u0010\u0016\u001a*\u0012\u0004\u0012\u00020\u0002\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00040\u00030\u0001j\u0014\u0012\u0004\u0012\u00020\u0002\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00040\u0003`\u0005X\u0082\u0004¢\u0006\u0002\n\u0000\"6\u0010\u0017\u001a*\u0012\u0004\u0012\u00020\u0002\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00040\u00030\u0001j\u0014\u0012\u0004\u0012\u00020\u0002\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00040\u0003`\u0005X\u0082\u0004¢\u0006\u0002\n\u0000\"6\u0010\u0018\u001a*\u0012\u0004\u0012\u00020\u0002\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00040\u00030\u0001j\u0014\u0012\u0004\u0012\u00020\u0002\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00040\u0003`\u0005X\u0082\u0004¢\u0006\u0002\n\u0000\"6\u0010\u0019\u001a*\u0012\u0004\u0012\u00020\u0002\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00040\u00030\u0001j\u0014\u0012\u0004\u0012\u00020\u0002\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00040\u0003`\u0005X\u0082\u0004¢\u0006\u0002\n\u0000\"6\u0010\u001a\u001a*\u0012\u0004\u0012\u00020\u0002\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00040\u00030\u0001j\u0014\u0012\u0004\u0012\u00020\u0002\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00040\u0003`\u0005X\u0082\u0004¢\u0006\u0002\n\u0000\"6\u0010\u001b\u001a*\u0012\u0004\u0012\u00020\u0002\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00040\u00030\u0001j\u0014\u0012\u0004\u0012\u00020\u0002\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00040\u0003`\u0005X\u0082\u0004¢\u0006\u0002\n\u0000\"6\u0010\u001c\u001a*\u0012\u0004\u0012\u00020\u0002\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00040\u00030\u0001j\u0014\u0012\u0004\u0012\u00020\u0002\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00040\u0003`\u0005X\u0082\u0004¢\u0006\u0002\n\u0000\"6\u0010\u001d\u001a*\u0012\u0004\u0012\u00020\u0002\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00040\u00030\u0001j\u0014\u0012\u0004\u0012\u00020\u0002\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00040\u0003`\u0005X\u0082\u0004¢\u0006\u0002\n\u0000\"6\u0010\u001e\u001a*\u0012\u0004\u0012\u00020\u0002\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00040\u00030\u0001j\u0014\u0012\u0004\u0012\u00020\u0002\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00040\u0003`\u0005X\u0082\u0004¢\u0006\u0002\n\u0000\"6\u0010\u001f\u001a*\u0012\u0004\u0012\u00020\u0002\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00040\u00030\u0001j\u0014\u0012\u0004\u0012\u00020\u0002\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00040\u0003`\u0005X\u0082\u0004¢\u0006\u0002\n\u0000\"6\u0010 \u001a*\u0012\u0004\u0012\u00020\u0002\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00040\u00030\u0001j\u0014\u0012\u0004\u0012\u00020\u0002\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00040\u0003`\u0005X\u0082\u0004¢\u0006\u0002\n\u0000\"6\u0010!\u001a*\u0012\u0004\u0012\u00020\u0002\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00040\u00030\u0001j\u0014\u0012\u0004\u0012\u00020\u0002\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00040\u0003`\u0005X\u0082\u0004¢\u0006\u0002\n\u0000\"6\u0010\"\u001a*\u0012\u0004\u0012\u00020\u0002\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00040\u00030\u0001j\u0014\u0012\u0004\u0012\u00020\u0002\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00040\u0003`\u0005X\u0082\u0004¢\u0006\u0002\n\u0000\"6\u0010#\u001a*\u0012\u0004\u0012\u00020\u0002\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00040\u00030\u0001j\u0014\u0012\u0004\u0012\u00020\u0002\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00040\u0003`\u0005X\u0082\u0004¢\u0006\u0002\n\u0000\"6\u0010$\u001a*\u0012\u0004\u0012\u00020\u0002\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00040\u00030\u0001j\u0014\u0012\u0004\u0012\u00020\u0002\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00040\u0003`\u0005X\u0082\u0004¢\u0006\u0002\n\u0000\"6\u0010%\u001a*\u0012\u0004\u0012\u00020\u0002\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00040\u00030\u0001j\u0014\u0012\u0004\u0012\u00020\u0002\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00040\u0003`\u0005X\u0082\u0004¢\u0006\u0002\n\u0000\"6\u0010&\u001a*\u0012\u0004\u0012\u00020\u0002\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00040\u00030\u0001j\u0014\u0012\u0004\u0012\u00020\u0002\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00040\u0003`\u0005X\u0082\u0004¢\u0006\u0002\n\u0000\"6\u0010'\u001a*\u0012\u0004\u0012\u00020\u0002\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00040\u00030\u0001j\u0014\u0012\u0004\u0012\u00020\u0002\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00040\u0003`\u0005X\u0082\u0004¢\u0006\u0002\n\u0000\"6\u0010(\u001a*\u0012\u0004\u0012\u00020\u0002\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00040\u00030\u0001j\u0014\u0012\u0004\u0012\u00020\u0002\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00040\u0003`\u0005X\u0082\u0004¢\u0006\u0002\n\u0000\"6\u0010)\u001a*\u0012\u0004\u0012\u00020\u0002\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00040\u00030\u0001j\u0014\u0012\u0004\u0012\u00020\u0002\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00040\u0003`\u0005X\u0082\u0004¢\u0006\u0002\n\u0000\"6\u0010*\u001a*\u0012\u0004\u0012\u00020\u0002\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00040\u00030\u0001j\u0014\u0012\u0004\u0012\u00020\u0002\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00040\u0003`\u0005X\u0082\u0004¢\u0006\u0002\n\u0000\"6\u0010+\u001a*\u0012\u0004\u0012\u00020\u0002\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00040\u00030\u0001j\u0014\u0012\u0004\u0012\u00020\u0002\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00040\u0003`\u0005X\u0082\u0004¢\u0006\u0002\n\u0000\"6\u0010,\u001a*\u0012\u0004\u0012\u00020\u0002\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00040\u00030\u0001j\u0014\u0012\u0004\u0012\u00020\u0002\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00040\u0003`\u0005X\u0082\u0004¢\u0006\u0002\n\u0000\"6\u0010-\u001a*\u0012\u0004\u0012\u00020\u0002\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00040\u00030\u0001j\u0014\u0012\u0004\u0012\u00020\u0002\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00040\u0003`\u0005X\u0082\u0004¢\u0006\u0002\n\u0000\"6\u0010.\u001a*\u0012\u0004\u0012\u00020\u0002\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00040\u00030\u0001j\u0014\u0012\u0004\u0012\u00020\u0002\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00040\u0003`\u0005X\u0082\u0004¢\u0006\u0002\n\u0000\"6\u0010/\u001a*\u0012\u0004\u0012\u00020\u0002\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00040\u00030\u0001j\u0014\u0012\u0004\u0012\u00020\u0002\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00040\u0003`\u0005X\u0082\u0004¢\u0006\u0002\n\u0000\"6\u00100\u001a*\u0012\u0004\u0012\u00020\u0002\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00040\u00030\u0001j\u0014\u0012\u0004\u0012\u00020\u0002\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00040\u0003`\u0005X\u0082\u0004¢\u0006\u0002\n\u0000\"6\u00101\u001a*\u0012\u0004\u0012\u00020\u0002\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00040\u00030\u0001j\u0014\u0012\u0004\u0012\u00020\u0002\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00040\u0003`\u0005X\u0082\u0004¢\u0006\u0002\n\u0000\"6\u00102\u001a*\u0012\u0004\u0012\u00020\u0002\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00040\u00030\u0001j\u0014\u0012\u0004\u0012\u00020\u0002\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00040\u0003`\u0005X\u0082\u0004¢\u0006\u0002\n\u0000\"6\u00103\u001a*\u0012\u0004\u0012\u00020\u0002\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00040\u00030\u0001j\u0014\u0012\u0004\u0012\u00020\u0002\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00040\u0003`\u0005X\u0082\u0004¢\u0006\u0002\n\u0000\"6\u00104\u001a*\u0012\u0004\u0012\u00020\u0002\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00040\u00030\u0001j\u0014\u0012\u0004\u0012\u00020\u0002\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00040\u0003`\u0005X\u0082\u0004¢\u0006\u0002\n\u0000\"6\u00105\u001a*\u0012\u0004\u0012\u00020\u0002\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00040\u00030\u0001j\u0014\u0012\u0004\u0012\u00020\u0002\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00040\u0003`\u0005X\u0082\u0004¢\u0006\u0002\n\u0000\"6\u00106\u001a*\u0012\u0004\u0012\u00020\u0002\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00040\u00030\u0001j\u0014\u0012\u0004\u0012\u00020\u0002\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00040\u0003`\u0005X\u0082\u0004¢\u0006\u0002\n\u0000\"6\u00107\u001a*\u0012\u0004\u0012\u00020\u0002\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00040\u00030\u0001j\u0014\u0012\u0004\u0012\u00020\u0002\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00040\u0003`\u0005X\u0082\u0004¢\u0006\u0002\n\u0000\"6\u00108\u001a*\u0012\u0004\u0012\u00020\u0002\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00040\u00030\u0001j\u0014\u0012\u0004\u0012\u00020\u0002\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00040\u0003`\u0005X\u0082\u0004¢\u0006\u0002\n\u0000\"6\u00109\u001a*\u0012\u0004\u0012\u00020\u0002\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00040\u00030\u0001j\u0014\u0012\u0004\u0012\u00020\u0002\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00040\u0003`\u0005X\u0082\u0004¢\u0006\u0002\n\u0000\"6\u0010:\u001a*\u0012\u0004\u0012\u00020\u0002\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00040\u00030\u0001j\u0014\u0012\u0004\u0012\u00020\u0002\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00040\u0003`\u0005X\u0082\u0004¢\u0006\u0002\n\u0000\"6\u0010;\u001a*\u0012\u0004\u0012\u00020\u0002\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00040\u00030\u0001j\u0014\u0012\u0004\u0012\u00020\u0002\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00040\u0003`\u0005X\u0082\u0004¢\u0006\u0002\n\u0000\"6\u0010<\u001a*\u0012\u0004\u0012\u00020\u0002\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00040\u00030\u0001j\u0014\u0012\u0004\u0012\u00020\u0002\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00040\u0003`\u0005X\u0082\u0004¢\u0006\u0002\n\u0000\"6\u0010=\u001a*\u0012\u0004\u0012\u00020\u0002\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00040\u00030\u0001j\u0014\u0012\u0004\u0012\u00020\u0002\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00040\u0003`\u0005X\u0082\u0004¢\u0006\u0002\n\u0000\"6\u0010>\u001a*\u0012\u0004\u0012\u00020\u0002\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00040\u00030\u0001j\u0014\u0012\u0004\u0012\u00020\u0002\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00040\u0003`\u0005X\u0082\u0004¢\u0006\u0002\n\u0000\"6\u0010?\u001a*\u0012\u0004\u0012\u00020\u0002\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00040\u00030\u0001j\u0014\u0012\u0004\u0012\u00020\u0002\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00040\u0003`\u0005X\u0082\u0004¢\u0006\u0002\n\u0000\"6\u0010@\u001a*\u0012\u0004\u0012\u00020\u0002\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00040\u00030\u0001j\u0014\u0012\u0004\u0012\u00020\u0002\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00040\u0003`\u0005X\u0082\u0004¢\u0006\u0002\n\u0000\"6\u0010A\u001a*\u0012\u0004\u0012\u00020\u0002\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00040\u00030\u0001j\u0014\u0012\u0004\u0012\u00020\u0002\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00040\u0003`\u0005X\u0082\u0004¢\u0006\u0002\n\u0000\"6\u0010B\u001a*\u0012\u0004\u0012\u00020\u0002\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00040\u00030\u0001j\u0014\u0012\u0004\u0012\u00020\u0002\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00040\u0003`\u0005X\u0082\u0004¢\u0006\u0002\n\u0000\"6\u0010C\u001a*\u0012\u0004\u0012\u00020\u0002\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00040\u00030\u0001j\u0014\u0012\u0004\u0012\u00020\u0002\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00040\u0003`\u0005X\u0082\u0004¢\u0006\u0002\n\u0000\"6\u0010D\u001a*\u0012\u0004\u0012\u00020\u0002\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00040\u00030\u0001j\u0014\u0012\u0004\u0012\u00020\u0002\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00040\u0003`\u0005X\u0082\u0004¢\u0006\u0002\n\u0000\"6\u0010E\u001a*\u0012\u0004\u0012\u00020\u0002\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00040\u00030\u0001j\u0014\u0012\u0004\u0012\u00020\u0002\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00040\u0003`\u0005X\u0082\u0004¢\u0006\u0002\n\u0000\"6\u0010F\u001a*\u0012\u0004\u0012\u00020\u0002\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00040\u00030\u0001j\u0014\u0012\u0004\u0012\u00020\u0002\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00040\u0003`\u0005X\u0082\u0004¢\u0006\u0002\n\u0000\"6\u0010G\u001a*\u0012\u0004\u0012\u00020\u0002\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00040\u00030\u0001j\u0014\u0012\u0004\u0012\u00020\u0002\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00040\u0003`\u0005X\u0082\u0004¢\u0006\u0002\n\u0000\"6\u0010H\u001a*\u0012\u0004\u0012\u00020\u0002\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00040\u00030\u0001j\u0014\u0012\u0004\u0012\u00020\u0002\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00040\u0003`\u0005X\u0082\u0004¢\u0006\u0002\n\u0000\"6\u0010I\u001a*\u0012\u0004\u0012\u00020\u0002\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00040\u00030\u0001j\u0014\u0012\u0004\u0012\u00020\u0002\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00040\u0003`\u0005X\u0082\u0004¢\u0006\u0002\n\u0000\"6\u0010J\u001a*\u0012\u0004\u0012\u00020\u0002\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00040\u00030\u0001j\u0014\u0012\u0004\u0012\u00020\u0002\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00040\u0003`\u0005X\u0082\u0004¢\u0006\u0002\n\u0000\"6\u0010K\u001a*\u0012\u0004\u0012\u00020\u0002\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00040\u00030\u0001j\u0014\u0012\u0004\u0012\u00020\u0002\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00040\u0003`\u0005X\u0082\u0004¢\u0006\u0002\n\u0000\"6\u0010L\u001a*\u0012\u0004\u0012\u00020\u0002\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00040\u00030\u0001j\u0014\u0012\u0004\u0012\u00020\u0002\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00040\u0003`\u0005X\u0082\u0004¢\u0006\u0002\n\u0000\"6\u0010M\u001a*\u0012\u0004\u0012\u00020\u0002\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00040\u00030\u0001j\u0014\u0012\u0004\u0012\u00020\u0002\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00040\u0003`\u0005X\u0082\u0004¢\u0006\u0002\n\u0000\"6\u0010N\u001a*\u0012\u0004\u0012\u00020\u0002\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00040\u00030\u0001j\u0014\u0012\u0004\u0012\u00020\u0002\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00040\u0003`\u0005X\u0082\u0004¢\u0006\u0002\n\u0000\"6\u0010O\u001a*\u0012\u0004\u0012\u00020\u0002\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00040\u00030\u0001j\u0014\u0012\u0004\u0012\u00020\u0002\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00040\u0003`\u0005X\u0082\u0004¢\u0006\u0002\n\u0000\"6\u0010P\u001a*\u0012\u0004\u0012\u00020\u0002\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00040\u00030\u0001j\u0014\u0012\u0004\u0012\u00020\u0002\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00040\u0003`\u0005X\u0082\u0004¢\u0006\u0002\n\u0000\"6\u0010Q\u001a*\u0012\u0004\u0012\u00020\u0002\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00040\u00030\u0001j\u0014\u0012\u0004\u0012\u00020\u0002\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00040\u0003`\u0005X\u0082\u0004¢\u0006\u0002\n\u0000\"6\u0010R\u001a*\u0012\u0004\u0012\u00020\u0002\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00040\u00030\u0001j\u0014\u0012\u0004\u0012\u00020\u0002\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00040\u0003`\u0005X\u0082\u0004¢\u0006\u0002\n\u0000\"6\u0010S\u001a*\u0012\u0004\u0012\u00020\u0002\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00040\u00030\u0001j\u0014\u0012\u0004\u0012\u00020\u0002\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00040\u0003`\u0005X\u0082\u0004¢\u0006\u0002\n\u0000\"6\u0010T\u001a*\u0012\u0004\u0012\u00020\u0002\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00040\u00030\u0001j\u0014\u0012\u0004\u0012\u00020\u0002\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00040\u0003`\u0005X\u0082\u0004¢\u0006\u0002\n\u0000\"6\u0010U\u001a*\u0012\u0004\u0012\u00020\u0002\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00040\u00030\u0001j\u0014\u0012\u0004\u0012\u00020\u0002\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00040\u0003`\u0005X\u0082\u0004¢\u0006\u0002\n\u0000\"9\u0010V\u001a*\u0012\u0004\u0012\u00020\u0002\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00040\u00030\u0001j\u0014\u0012\u0004\u0012\u00020\u0002\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00040\u0003`\u0005¢\u0006\b\n\u0000\u001a\u0004\bW\u0010X\"6\u0010Y\u001a*\u0012\u0004\u0012\u00020\u0002\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00040\u00030\u0001j\u0014\u0012\u0004\u0012\u00020\u0002\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00040\u0003`\u0005X\u0082\u0004¢\u0006\u0002\n\u0000\"6\u0010Z\u001a*\u0012\u0004\u0012\u00020\u0002\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00040\u00030\u0001j\u0014\u0012\u0004\u0012\u00020\u0002\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00040\u0003`\u0005X\u0082\u0004¢\u0006\u0002\n\u0000\"6\u0010[\u001a*\u0012\u0004\u0012\u00020\u0002\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00040\u00030\u0001j\u0014\u0012\u0004\u0012\u00020\u0002\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00040\u0003`\u0005X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006j"}, d2 = {"addContinuationToFunctionCallsLoweringPhase", "Lorg/jetbrains/kotlin/backend/common/phaser/NamedCompilerPhase;", "Lorg/jetbrains/kotlin/backend/wasm/WasmBackendContext;", "", "Lorg/jetbrains/kotlin/ir/declarations/IrModuleFragment;", "Lorg/jetbrains/kotlin/backend/common/phaser/SameTypeNamedCompilerPhase;", "addContinuationToNonLocalSuspendFunctionsLoweringPhase", "addMainFunctionCallsLowering", "arrayConstructorPhase", "arrayConstructorReferencePhase", "associatedObjectsLowering", "autoboxingTransformerPhase", "bridgesConstructionPhase", "builtInsLoweringPhase", "builtInsLoweringPhase0", "callableReferencePhase", "classReferenceLoweringPhase", "complexExternalDeclarationsToTopLevelFunctionsLowering", "complexExternalDeclarationsUsagesLowering", "defaultArgumentPatchOverridesPhase", "defaultArgumentStubGeneratorPhase", "defaultParameterCleanerPhase", "defaultParameterInjectorPhase", "delegateToPrimaryConstructorLoweringPhase", "enumClassConstructorBodyLoweringPhase", "enumClassConstructorLoweringPhase", "enumClassCreateInitializerLoweringPhase", "enumEntryCreateGetInstancesFunsLoweringPhase", "enumEntryInstancesBodyLoweringPhase", "enumEntryInstancesLoweringPhase", "enumEntryRemovalLoweringPhase", "enumSyntheticFunsLoweringPhase", "enumUsageLoweringPhase", "eraseVirtualDispatchReceiverParametersTypes", "excludeDeclarationsFromCodegenPhase", "expectDeclarationsRemovingPhase", "explicitlyCastExternalTypesPhase", "expressionBodyTransformer", "fieldInitializersLoweringPhase", "forLoopsLoweringPhase", "functionInliningPhase", "generateTests", "genericReturnTypeLowering", "initializersCleanupLoweringPhase", "initializersLoweringPhase", "inlineClassDeclarationLoweringPhase", "inlineClassUsageLoweringPhase", "inlineObjectsWithPureInitializationLoweringPhase", "innerClassConstructorCallsLoweringPhase", "innerClassesLoweringPhase", "innerClassesMemberBodyLoweringPhase", "jsCodeCallsLowering", "jsInteropFunctionCallsLowering", "jsInteropFunctionsLowering", "lateinitDeclarationLoweringPhase", "lateinitNullableFieldsPhase", "lateinitUsageLoweringPhase", "localClassExtractionPhase", "localClassesExtractionFromInlineFunctionsPhase", "localClassesInInlineFunctionsPhase", "localClassesInInlineLambdasPhase", "localDeclarationsLoweringPhase", "localDelegatedPropertiesLoweringPhase", "objectDeclarationLoweringPhase", "objectUsageLoweringPhase", "primaryConstructorLoweringPhase", "propertiesLoweringPhase", "propertyAccessorInlinerLoweringPhase", "propertyLazyInitLoweringPhase", "propertyReferenceLowering", "purifyObjectInstanceGettersLoweringPhase", "removeInitializersForLazyProperties", "removeInlineDeclarationsWithReifiedTypeParametersLoweringPhase", "sharedVariablesLoweringPhase", "singleAbstractMethodPhase", "staticMembersLoweringPhase", "stringConcatenationLowering", "suspendFunctionsLoweringPhase", "tailrecLoweringPhase", "tryCatchCanonicalization", "typeOperatorLoweringPhase", "unhandledExceptionLowering", "unitToVoidLowering", "validateIrAfterLowering", "validateIrBeforeLowering", "virtualDispatchReceiverExtractionPhase", "wasmPhases", "getWasmPhases", "()Lorg/jetbrains/kotlin/backend/common/phaser/NamedCompilerPhase;", "wasmStringSwitchOptimizerLowering", "wasmVarargExpressionLoweringPhase", "wrapInlineDeclarationsWithReifiedTypeParametersPhase", "makeCustomWasmModulePhase", "op", "Lkotlin/Function2;", "", MediaTrack.ROLE_DESCRIPTION, "", "name", "prerequisite", "", "Lorg/jetbrains/kotlin/backend/common/phaser/AbstractNamedCompilerPhase;", "makeWasmModulePhase", "lowering", "Lkotlin/Function1;", "Lorg/jetbrains/kotlin/backend/common/FileLoweringPass;", "backend.wasm"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class WasmLoweringPhasesKt {
    private static final NamedCompilerPhase<WasmBackendContext, Iterable<IrModuleFragment>> addContinuationToFunctionCallsLoweringPhase;
    private static final NamedCompilerPhase<WasmBackendContext, Iterable<IrModuleFragment>> addContinuationToNonLocalSuspendFunctionsLoweringPhase;
    private static final NamedCompilerPhase<WasmBackendContext, Iterable<IrModuleFragment>> addMainFunctionCallsLowering;
    private static final NamedCompilerPhase<WasmBackendContext, Iterable<IrModuleFragment>> arrayConstructorPhase;
    private static final NamedCompilerPhase<WasmBackendContext, Iterable<IrModuleFragment>> arrayConstructorReferencePhase;
    private static final NamedCompilerPhase<WasmBackendContext, Iterable<IrModuleFragment>> associatedObjectsLowering;
    private static final NamedCompilerPhase<WasmBackendContext, Iterable<IrModuleFragment>> autoboxingTransformerPhase;
    private static final NamedCompilerPhase<WasmBackendContext, Iterable<IrModuleFragment>> bridgesConstructionPhase;
    private static final NamedCompilerPhase<WasmBackendContext, Iterable<IrModuleFragment>> builtInsLoweringPhase;
    private static final NamedCompilerPhase<WasmBackendContext, Iterable<IrModuleFragment>> builtInsLoweringPhase0;
    private static final NamedCompilerPhase<WasmBackendContext, Iterable<IrModuleFragment>> callableReferencePhase;
    private static final NamedCompilerPhase<WasmBackendContext, Iterable<IrModuleFragment>> classReferenceLoweringPhase;
    private static final NamedCompilerPhase<WasmBackendContext, Iterable<IrModuleFragment>> complexExternalDeclarationsToTopLevelFunctionsLowering;
    private static final NamedCompilerPhase<WasmBackendContext, Iterable<IrModuleFragment>> complexExternalDeclarationsUsagesLowering;
    private static final NamedCompilerPhase<WasmBackendContext, Iterable<IrModuleFragment>> defaultArgumentPatchOverridesPhase;
    private static final NamedCompilerPhase<WasmBackendContext, Iterable<IrModuleFragment>> defaultArgumentStubGeneratorPhase;
    private static final NamedCompilerPhase<WasmBackendContext, Iterable<IrModuleFragment>> defaultParameterCleanerPhase;
    private static final NamedCompilerPhase<WasmBackendContext, Iterable<IrModuleFragment>> defaultParameterInjectorPhase;
    private static final NamedCompilerPhase<WasmBackendContext, Iterable<IrModuleFragment>> delegateToPrimaryConstructorLoweringPhase;
    private static final NamedCompilerPhase<WasmBackendContext, Iterable<IrModuleFragment>> enumClassConstructorBodyLoweringPhase;
    private static final NamedCompilerPhase<WasmBackendContext, Iterable<IrModuleFragment>> enumClassConstructorLoweringPhase;
    private static final NamedCompilerPhase<WasmBackendContext, Iterable<IrModuleFragment>> enumClassCreateInitializerLoweringPhase;
    private static final NamedCompilerPhase<WasmBackendContext, Iterable<IrModuleFragment>> enumEntryCreateGetInstancesFunsLoweringPhase;
    private static final NamedCompilerPhase<WasmBackendContext, Iterable<IrModuleFragment>> enumEntryInstancesBodyLoweringPhase;
    private static final NamedCompilerPhase<WasmBackendContext, Iterable<IrModuleFragment>> enumEntryInstancesLoweringPhase;
    private static final NamedCompilerPhase<WasmBackendContext, Iterable<IrModuleFragment>> enumEntryRemovalLoweringPhase;
    private static final NamedCompilerPhase<WasmBackendContext, Iterable<IrModuleFragment>> enumSyntheticFunsLoweringPhase;
    private static final NamedCompilerPhase<WasmBackendContext, Iterable<IrModuleFragment>> enumUsageLoweringPhase;
    private static final NamedCompilerPhase<WasmBackendContext, Iterable<IrModuleFragment>> eraseVirtualDispatchReceiverParametersTypes;
    private static final NamedCompilerPhase<WasmBackendContext, Iterable<IrModuleFragment>> excludeDeclarationsFromCodegenPhase;
    private static final NamedCompilerPhase<WasmBackendContext, Iterable<IrModuleFragment>> expectDeclarationsRemovingPhase;
    private static final NamedCompilerPhase<WasmBackendContext, Iterable<IrModuleFragment>> explicitlyCastExternalTypesPhase;
    private static final NamedCompilerPhase<WasmBackendContext, Iterable<IrModuleFragment>> expressionBodyTransformer;
    private static final NamedCompilerPhase<WasmBackendContext, Iterable<IrModuleFragment>> fieldInitializersLoweringPhase;
    private static final NamedCompilerPhase<WasmBackendContext, Iterable<IrModuleFragment>> forLoopsLoweringPhase;
    private static final NamedCompilerPhase<WasmBackendContext, Iterable<IrModuleFragment>> functionInliningPhase;
    private static final NamedCompilerPhase<WasmBackendContext, Iterable<IrModuleFragment>> generateTests;
    private static final NamedCompilerPhase<WasmBackendContext, Iterable<IrModuleFragment>> genericReturnTypeLowering;
    private static final NamedCompilerPhase<WasmBackendContext, Iterable<IrModuleFragment>> initializersCleanupLoweringPhase;
    private static final NamedCompilerPhase<WasmBackendContext, Iterable<IrModuleFragment>> initializersLoweringPhase;
    private static final NamedCompilerPhase<WasmBackendContext, Iterable<IrModuleFragment>> inlineClassDeclarationLoweringPhase;
    private static final NamedCompilerPhase<WasmBackendContext, Iterable<IrModuleFragment>> inlineClassUsageLoweringPhase;
    private static final NamedCompilerPhase<WasmBackendContext, Iterable<IrModuleFragment>> inlineObjectsWithPureInitializationLoweringPhase;
    private static final NamedCompilerPhase<WasmBackendContext, Iterable<IrModuleFragment>> innerClassConstructorCallsLoweringPhase;
    private static final NamedCompilerPhase<WasmBackendContext, Iterable<IrModuleFragment>> innerClassesLoweringPhase;
    private static final NamedCompilerPhase<WasmBackendContext, Iterable<IrModuleFragment>> innerClassesMemberBodyLoweringPhase;
    private static final NamedCompilerPhase<WasmBackendContext, Iterable<IrModuleFragment>> jsCodeCallsLowering;
    private static final NamedCompilerPhase<WasmBackendContext, Iterable<IrModuleFragment>> jsInteropFunctionCallsLowering;
    private static final NamedCompilerPhase<WasmBackendContext, Iterable<IrModuleFragment>> jsInteropFunctionsLowering;
    private static final NamedCompilerPhase<WasmBackendContext, Iterable<IrModuleFragment>> lateinitDeclarationLoweringPhase;
    private static final NamedCompilerPhase<WasmBackendContext, Iterable<IrModuleFragment>> lateinitNullableFieldsPhase;
    private static final NamedCompilerPhase<WasmBackendContext, Iterable<IrModuleFragment>> lateinitUsageLoweringPhase;
    private static final NamedCompilerPhase<WasmBackendContext, Iterable<IrModuleFragment>> localClassExtractionPhase;
    private static final NamedCompilerPhase<WasmBackendContext, Iterable<IrModuleFragment>> localClassesExtractionFromInlineFunctionsPhase;
    private static final NamedCompilerPhase<WasmBackendContext, Iterable<IrModuleFragment>> localClassesInInlineFunctionsPhase;
    private static final NamedCompilerPhase<WasmBackendContext, Iterable<IrModuleFragment>> localClassesInInlineLambdasPhase;
    private static final NamedCompilerPhase<WasmBackendContext, Iterable<IrModuleFragment>> localDeclarationsLoweringPhase;
    private static final NamedCompilerPhase<WasmBackendContext, Iterable<IrModuleFragment>> localDelegatedPropertiesLoweringPhase;
    private static final NamedCompilerPhase<WasmBackendContext, Iterable<IrModuleFragment>> objectDeclarationLoweringPhase;
    private static final NamedCompilerPhase<WasmBackendContext, Iterable<IrModuleFragment>> objectUsageLoweringPhase;
    private static final NamedCompilerPhase<WasmBackendContext, Iterable<IrModuleFragment>> primaryConstructorLoweringPhase;
    private static final NamedCompilerPhase<WasmBackendContext, Iterable<IrModuleFragment>> propertiesLoweringPhase;
    private static final NamedCompilerPhase<WasmBackendContext, Iterable<IrModuleFragment>> propertyAccessorInlinerLoweringPhase;
    private static final NamedCompilerPhase<WasmBackendContext, Iterable<IrModuleFragment>> propertyLazyInitLoweringPhase;
    private static final NamedCompilerPhase<WasmBackendContext, Iterable<IrModuleFragment>> propertyReferenceLowering;
    private static final NamedCompilerPhase<WasmBackendContext, Iterable<IrModuleFragment>> purifyObjectInstanceGettersLoweringPhase;
    private static final NamedCompilerPhase<WasmBackendContext, Iterable<IrModuleFragment>> removeInitializersForLazyProperties;
    private static final NamedCompilerPhase<WasmBackendContext, Iterable<IrModuleFragment>> removeInlineDeclarationsWithReifiedTypeParametersLoweringPhase;
    private static final NamedCompilerPhase<WasmBackendContext, Iterable<IrModuleFragment>> sharedVariablesLoweringPhase;
    private static final NamedCompilerPhase<WasmBackendContext, Iterable<IrModuleFragment>> singleAbstractMethodPhase;
    private static final NamedCompilerPhase<WasmBackendContext, Iterable<IrModuleFragment>> staticMembersLoweringPhase;
    private static final NamedCompilerPhase<WasmBackendContext, Iterable<IrModuleFragment>> stringConcatenationLowering;
    private static final NamedCompilerPhase<WasmBackendContext, Iterable<IrModuleFragment>> suspendFunctionsLoweringPhase;
    private static final NamedCompilerPhase<WasmBackendContext, Iterable<IrModuleFragment>> tailrecLoweringPhase;
    private static final NamedCompilerPhase<WasmBackendContext, Iterable<IrModuleFragment>> tryCatchCanonicalization;
    private static final NamedCompilerPhase<WasmBackendContext, Iterable<IrModuleFragment>> typeOperatorLoweringPhase;
    private static final NamedCompilerPhase<WasmBackendContext, Iterable<IrModuleFragment>> unhandledExceptionLowering;
    private static final NamedCompilerPhase<WasmBackendContext, Iterable<IrModuleFragment>> unitToVoidLowering;
    private static final NamedCompilerPhase<WasmBackendContext, Iterable<IrModuleFragment>> validateIrAfterLowering;
    private static final NamedCompilerPhase<WasmBackendContext, Iterable<IrModuleFragment>> validateIrBeforeLowering;
    private static final NamedCompilerPhase<WasmBackendContext, Iterable<IrModuleFragment>> virtualDispatchReceiverExtractionPhase;
    private static final NamedCompilerPhase<WasmBackendContext, Iterable<IrModuleFragment>> wasmPhases;
    private static final NamedCompilerPhase<WasmBackendContext, Iterable<IrModuleFragment>> wasmStringSwitchOptimizerLowering;
    private static final NamedCompilerPhase<WasmBackendContext, Iterable<IrModuleFragment>> wasmVarargExpressionLoweringPhase;
    private static final NamedCompilerPhase<WasmBackendContext, Iterable<IrModuleFragment>> wrapInlineDeclarationsWithReifiedTypeParametersPhase;

    static /* synthetic */ NamedCompilerPhase makeWasmModulePhase$default(Function1 function1, String str, String str2, Set set, int i, Object obj) {
        if ((i & 8) != 0) {
            set = SetsKt.emptySet();
        }
        return makeWasmModulePhase(function1, str, str2, set);
    }

    private static final NamedCompilerPhase<WasmBackendContext, Iterable<IrModuleFragment>> makeWasmModulePhase(final Function1<? super WasmBackendContext, ? extends FileLoweringPass> function1, String str, String str2, Set<? extends AbstractNamedCompilerPhase<? super WasmBackendContext, ?, ?>> set) {
        return makeCustomWasmModulePhase(new Function2<WasmBackendContext, IrModuleFragment, Unit>() { // from class: org.jetbrains.kotlin.backend.wasm.WasmLoweringPhasesKt.makeWasmModulePhase.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            /* JADX WARN: Multi-variable type inference failed */
            {
                super(2);
            }

            @Override // kotlin.jvm.functions.Function2
            public /* bridge */ /* synthetic */ Unit invoke(WasmBackendContext wasmBackendContext, IrModuleFragment irModuleFragment) {
                invoke2(wasmBackendContext, irModuleFragment);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(WasmBackendContext context, IrModuleFragment modules) {
                Intrinsics.checkNotNullParameter(context, "context");
                Intrinsics.checkNotNullParameter(modules, "modules");
                LowerKt.lower(function1.invoke(context), modules);
            }
        }, str2, str, set);
    }

    static /* synthetic */ NamedCompilerPhase makeCustomWasmModulePhase$default(Function2 function2, String str, String str2, Set set, int i, Object obj) {
        if ((i & 8) != 0) {
            set = SetsKt.emptySet();
        }
        return makeCustomWasmModulePhase(function2, str, str2, set);
    }

    private static final NamedCompilerPhase<WasmBackendContext, Iterable<IrModuleFragment>> makeCustomWasmModulePhase(final Function2<? super WasmBackendContext, ? super IrModuleFragment, Unit> function2, String str, String str2, Set<? extends AbstractNamedCompilerPhase<? super WasmBackendContext, ?, ?>> set) {
        return new NamedCompilerPhase<>(str2, str, set, new SameTypeCompilerPhase<WasmBackendContext, Iterable<? extends IrModuleFragment>>() { // from class: org.jetbrains.kotlin.backend.wasm.WasmLoweringPhasesKt.makeCustomWasmModulePhase.1
            @Override // org.jetbrains.kotlin.backend.common.phaser.CompilerPhase
            public /* bridge */ /* synthetic */ Object invoke(PhaseConfigurationService phaseConfigurationService, PhaserState phaserState, LoggingContext loggingContext, Object obj) {
                return invoke(phaseConfigurationService, (PhaserState<Iterable<IrModuleFragment>>) phaserState, (WasmBackendContext) loggingContext, (Iterable<? extends IrModuleFragment>) obj);
            }

            /* JADX WARN: Multi-variable type inference failed */
            public Iterable<IrModuleFragment> invoke(PhaseConfigurationService phaseConfig, PhaserState<Iterable<IrModuleFragment>> phaserState, WasmBackendContext context, Iterable<? extends IrModuleFragment> input) {
                Intrinsics.checkNotNullParameter(phaseConfig, "phaseConfig");
                Intrinsics.checkNotNullParameter(phaserState, "phaserState");
                Intrinsics.checkNotNullParameter(context, "context");
                Intrinsics.checkNotNullParameter(input, "input");
                Function2<WasmBackendContext, IrModuleFragment, Unit> function3 = function2;
                Iterator it = input.iterator();
                while (it.hasNext()) {
                    function3.invoke(context, (IrModuleFragment) it.next());
                }
                return input;
            }
        }, null, null, null, SetsKt.setOf((Object[]) new Function3[]{LowerKt.toMultiModuleAction(DumperVerifierKt.getDefaultDumper()), LowerKt.toMultiModuleAction(DumperVerifierKt.getValidationAction())}), 0, 368, null);
    }

    static {
        NamedCompilerPhase<WasmBackendContext, Iterable<IrModuleFragment>> namedCompilerPhaseMakeCustomWasmModulePhase$default = makeCustomWasmModulePhase$default(new Function2<WasmBackendContext, IrModuleFragment, Unit>() { // from class: org.jetbrains.kotlin.backend.wasm.WasmLoweringPhasesKt$validateIrBeforeLowering$1
            @Override // kotlin.jvm.functions.Function2
            public /* bridge */ /* synthetic */ Unit invoke(WasmBackendContext wasmBackendContext, IrModuleFragment irModuleFragment) {
                invoke2(wasmBackendContext, irModuleFragment);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(WasmBackendContext context, IrModuleFragment module) {
                Intrinsics.checkNotNullParameter(context, "context");
                Intrinsics.checkNotNullParameter(module, "module");
                DumperVerifierKt.validationCallback$default(context, module, false, 4, null);
            }
        }, "Validate IR before lowering", "ValidateIrBeforeLowering", null, 8, null);
        validateIrBeforeLowering = namedCompilerPhaseMakeCustomWasmModulePhase$default;
        NamedCompilerPhase<WasmBackendContext, Iterable<IrModuleFragment>> namedCompilerPhaseMakeCustomWasmModulePhase$default2 = makeCustomWasmModulePhase$default(new Function2<WasmBackendContext, IrModuleFragment, Unit>() { // from class: org.jetbrains.kotlin.backend.wasm.WasmLoweringPhasesKt$validateIrAfterLowering$1
            @Override // kotlin.jvm.functions.Function2
            public /* bridge */ /* synthetic */ Unit invoke(WasmBackendContext wasmBackendContext, IrModuleFragment irModuleFragment) {
                invoke2(wasmBackendContext, irModuleFragment);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(WasmBackendContext context, IrModuleFragment module) {
                Intrinsics.checkNotNullParameter(context, "context");
                Intrinsics.checkNotNullParameter(module, "module");
                DumperVerifierKt.validationCallback$default(context, module, false, 4, null);
            }
        }, "Validate IR after lowering", "ValidateIrAfterLowering", null, 8, null);
        validateIrAfterLowering = namedCompilerPhaseMakeCustomWasmModulePhase$default2;
        NamedCompilerPhase<WasmBackendContext, Iterable<IrModuleFragment>> namedCompilerPhaseMakeCustomWasmModulePhase$default3 = makeCustomWasmModulePhase$default(new Function2<WasmBackendContext, IrModuleFragment, Unit>() { // from class: org.jetbrains.kotlin.backend.wasm.WasmLoweringPhasesKt$generateTests$1
            @Override // kotlin.jvm.functions.Function2
            public /* bridge */ /* synthetic */ Unit invoke(WasmBackendContext wasmBackendContext, IrModuleFragment irModuleFragment) {
                invoke2(wasmBackendContext, irModuleFragment);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(WasmBackendContext context, IrModuleFragment module) {
                Intrinsics.checkNotNullParameter(context, "context");
                Intrinsics.checkNotNullParameter(module, "module");
                TestGeneratorKt.generateWasmTests(context, module);
            }
        }, "Generates code to execute kotlin.test cases", "GenerateTests", null, 8, null);
        generateTests = namedCompilerPhaseMakeCustomWasmModulePhase$default3;
        NamedCompilerPhase<WasmBackendContext, Iterable<IrModuleFragment>> namedCompilerPhaseMakeWasmModulePhase$default = makeWasmModulePhase$default(WasmLoweringPhasesKt$expectDeclarationsRemovingPhase$1.INSTANCE, "ExpectDeclarationsRemoving", "Remove expect declaration from module fragment", null, 8, null);
        expectDeclarationsRemovingPhase = namedCompilerPhaseMakeWasmModulePhase$default;
        NamedCompilerPhase<WasmBackendContext, Iterable<IrModuleFragment>> namedCompilerPhaseMakeWasmModulePhase$default2 = makeWasmModulePhase$default(WasmLoweringPhasesKt$stringConcatenationLowering$1.INSTANCE, "StringConcatenation", "String concatenation lowering", null, 8, null);
        stringConcatenationLowering = namedCompilerPhaseMakeWasmModulePhase$default2;
        NamedCompilerPhase<WasmBackendContext, Iterable<IrModuleFragment>> namedCompilerPhaseMakeWasmModulePhase$default3 = makeWasmModulePhase$default(WasmLoweringPhasesKt$lateinitNullableFieldsPhase$1.INSTANCE, "LateinitNullableFields", "Create nullable fields for lateinit properties", null, 8, null);
        lateinitNullableFieldsPhase = namedCompilerPhaseMakeWasmModulePhase$default3;
        NamedCompilerPhase<WasmBackendContext, Iterable<IrModuleFragment>> namedCompilerPhaseMakeWasmModulePhase$default4 = makeWasmModulePhase$default(WasmLoweringPhasesKt$lateinitDeclarationLoweringPhase$1.INSTANCE, "LateinitDeclarations", "Reference nullable fields from properties and getters + insert checks", null, 8, null);
        lateinitDeclarationLoweringPhase = namedCompilerPhaseMakeWasmModulePhase$default4;
        NamedCompilerPhase<WasmBackendContext, Iterable<IrModuleFragment>> namedCompilerPhaseMakeWasmModulePhase$default5 = makeWasmModulePhase$default(WasmLoweringPhasesKt$lateinitUsageLoweringPhase$1.INSTANCE, "LateinitUsage", "Insert checks for lateinit field references", null, 8, null);
        lateinitUsageLoweringPhase = namedCompilerPhaseMakeWasmModulePhase$default5;
        NamedCompilerPhase<WasmBackendContext, Iterable<IrModuleFragment>> namedCompilerPhaseMakeWasmModulePhase$default6 = makeWasmModulePhase$default(WasmLoweringPhasesKt$arrayConstructorReferencePhase$1.INSTANCE, "ArrayConstructorReference", "Transform `::Array` into a ::create#Array", null, 8, null);
        arrayConstructorReferencePhase = namedCompilerPhaseMakeWasmModulePhase$default6;
        NamedCompilerPhase<WasmBackendContext, Iterable<IrModuleFragment>> namedCompilerPhaseMakeWasmModulePhase = makeWasmModulePhase(WasmLoweringPhasesKt$arrayConstructorPhase$1.INSTANCE, "ArrayConstructor", "Transform `Array(size) { index -> value }` into create#Array { index -> value } call", SetsKt.setOf(namedCompilerPhaseMakeWasmModulePhase$default6));
        arrayConstructorPhase = namedCompilerPhaseMakeWasmModulePhase;
        NamedCompilerPhase<WasmBackendContext, Iterable<IrModuleFragment>> namedCompilerPhaseMakeWasmModulePhase2 = makeWasmModulePhase(WasmLoweringPhasesKt$sharedVariablesLoweringPhase$1.INSTANCE, "SharedVariablesLowering", "Box captured mutable variables", SetsKt.setOf((Object[]) new NamedCompilerPhase[]{namedCompilerPhaseMakeWasmModulePhase$default4, namedCompilerPhaseMakeWasmModulePhase$default5}));
        sharedVariablesLoweringPhase = namedCompilerPhaseMakeWasmModulePhase2;
        NamedCompilerPhase<WasmBackendContext, Iterable<IrModuleFragment>> namedCompilerPhaseMakeWasmModulePhase$default7 = makeWasmModulePhase$default(WasmLoweringPhasesKt$localClassesInInlineLambdasPhase$1.INSTANCE, "LocalClassesInInlineLambdasPhase", "Extract local classes from inline lambdas", null, 8, null);
        localClassesInInlineLambdasPhase = namedCompilerPhaseMakeWasmModulePhase$default7;
        NamedCompilerPhase<WasmBackendContext, Iterable<IrModuleFragment>> namedCompilerPhaseMakeWasmModulePhase$default8 = makeWasmModulePhase$default(WasmLoweringPhasesKt$localClassesInInlineFunctionsPhase$1.INSTANCE, "LocalClassesInInlineFunctionsPhase", "Extract local classes from inline functions", null, 8, null);
        localClassesInInlineFunctionsPhase = namedCompilerPhaseMakeWasmModulePhase$default8;
        NamedCompilerPhase<WasmBackendContext, Iterable<IrModuleFragment>> namedCompilerPhaseMakeWasmModulePhase3 = makeWasmModulePhase(new Function1<WasmBackendContext, FileLoweringPass>() { // from class: org.jetbrains.kotlin.backend.wasm.WasmLoweringPhasesKt$localClassesExtractionFromInlineFunctionsPhase$1
            @Override // kotlin.jvm.functions.Function1
            public final FileLoweringPass invoke(WasmBackendContext context) {
                Intrinsics.checkNotNullParameter(context, "context");
                return new LocalClassesExtractionFromInlineFunctionsLowering(context, null, 2, null);
            }
        }, "localClassesExtractionFromInlineFunctionsPhase", "Move local classes from inline functions into nearest declaration container", SetsKt.setOf(namedCompilerPhaseMakeWasmModulePhase$default8));
        localClassesExtractionFromInlineFunctionsPhase = namedCompilerPhaseMakeWasmModulePhase3;
        NamedCompilerPhase<WasmBackendContext, Iterable<IrModuleFragment>> namedCompilerPhaseMakeWasmModulePhase$default9 = makeWasmModulePhase$default(WasmLoweringPhasesKt$wrapInlineDeclarationsWithReifiedTypeParametersPhase$1.INSTANCE, "WrapInlineDeclarationsWithReifiedTypeParametersPhase", "Wrap inline declarations with reified type parameters", null, 8, null);
        wrapInlineDeclarationsWithReifiedTypeParametersPhase = namedCompilerPhaseMakeWasmModulePhase$default9;
        NamedCompilerPhase<WasmBackendContext, Iterable<IrModuleFragment>> namedCompilerPhaseMakeCustomWasmModulePhase = makeCustomWasmModulePhase(new Function2<WasmBackendContext, IrModuleFragment, Unit>() { // from class: org.jetbrains.kotlin.backend.wasm.WasmLoweringPhasesKt$functionInliningPhase$1
            @Override // kotlin.jvm.functions.Function2
            public /* bridge */ /* synthetic */ Unit invoke(WasmBackendContext wasmBackendContext, IrModuleFragment irModuleFragment) {
                invoke2(wasmBackendContext, irModuleFragment);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(WasmBackendContext context, IrModuleFragment module) {
                Intrinsics.checkNotNullParameter(context, "context");
                Intrinsics.checkNotNullParameter(module, "module");
                new FunctionInlining(context, new WasmInlineFunctionResolver(context), context.getInnerClassesSupport(), true, false, false, false, false, false, XKeySym.XK_dstroke, null).inline(module);
                PatchDeclarationParentsKt.patchDeclarationParents$default(module, null, 1, null);
            }
        }, "Perform function inlining", "FunctionInliningPhase", SetsKt.setOf((Object[]) new NamedCompilerPhase[]{namedCompilerPhaseMakeWasmModulePhase$default, namedCompilerPhaseMakeWasmModulePhase$default9, namedCompilerPhaseMakeWasmModulePhase$default7, namedCompilerPhaseMakeWasmModulePhase$default8}));
        functionInliningPhase = namedCompilerPhaseMakeCustomWasmModulePhase;
        NamedCompilerPhase<WasmBackendContext, Iterable<IrModuleFragment>> namedCompilerPhaseMakeWasmModulePhase4 = makeWasmModulePhase(new Function1<WasmBackendContext, FileLoweringPass>() { // from class: org.jetbrains.kotlin.backend.wasm.WasmLoweringPhasesKt$removeInlineDeclarationsWithReifiedTypeParametersLoweringPhase$1
            @Override // kotlin.jvm.functions.Function1
            public final FileLoweringPass invoke(WasmBackendContext it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return new RemoveInlineDeclarationsWithReifiedTypeParametersLowering();
            }
        }, "RemoveInlineFunctionsWithReifiedTypeParametersLowering", "Remove Inline functions with reified parameters from context", SetsKt.setOf(namedCompilerPhaseMakeCustomWasmModulePhase));
        removeInlineDeclarationsWithReifiedTypeParametersLoweringPhase = namedCompilerPhaseMakeWasmModulePhase4;
        NamedCompilerPhase<WasmBackendContext, Iterable<IrModuleFragment>> namedCompilerPhaseMakeWasmModulePhase$default10 = makeWasmModulePhase$default(WasmLoweringPhasesKt$tailrecLoweringPhase$1.INSTANCE, "TailrecLowering", "Replace `tailrec` call sites with equivalent loop", null, 8, null);
        tailrecLoweringPhase = namedCompilerPhaseMakeWasmModulePhase$default10;
        NamedCompilerPhase<WasmBackendContext, Iterable<IrModuleFragment>> namedCompilerPhaseMakeWasmModulePhase$default11 = makeWasmModulePhase$default(WasmLoweringPhasesKt$wasmStringSwitchOptimizerLowering$1.INSTANCE, "WasmStringSwitchOptimizerLowering", "Replace when with constant string cases to binary search by string hashcodes", null, 8, null);
        wasmStringSwitchOptimizerLowering = namedCompilerPhaseMakeWasmModulePhase$default11;
        NamedCompilerPhase<WasmBackendContext, Iterable<IrModuleFragment>> namedCompilerPhaseMakeWasmModulePhase$default12 = makeWasmModulePhase$default(WasmLoweringPhasesKt$jsCodeCallsLowering$1.INSTANCE, "JsCodeCallsLowering", "Lower calls to js('code') into @JsFun", null, 8, null);
        jsCodeCallsLowering = namedCompilerPhaseMakeWasmModulePhase$default12;
        NamedCompilerPhase<WasmBackendContext, Iterable<IrModuleFragment>> namedCompilerPhaseMakeWasmModulePhase$default13 = makeWasmModulePhase$default(WasmLoweringPhasesKt$complexExternalDeclarationsToTopLevelFunctionsLowering$1.INSTANCE, "ComplexExternalDeclarationsToTopLevelFunctionsLowering", "Lower complex external declarations to top-level functions", null, 8, null);
        complexExternalDeclarationsToTopLevelFunctionsLowering = namedCompilerPhaseMakeWasmModulePhase$default13;
        NamedCompilerPhase<WasmBackendContext, Iterable<IrModuleFragment>> namedCompilerPhaseMakeWasmModulePhase$default14 = makeWasmModulePhase$default(WasmLoweringPhasesKt$complexExternalDeclarationsUsagesLowering$1.INSTANCE, "ComplexExternalDeclarationsUsageLowering", "Lower usages of complex external declarations", null, 8, null);
        complexExternalDeclarationsUsagesLowering = namedCompilerPhaseMakeWasmModulePhase$default14;
        NamedCompilerPhase<WasmBackendContext, Iterable<IrModuleFragment>> namedCompilerPhaseMakeWasmModulePhase$default15 = makeWasmModulePhase$default(WasmLoweringPhasesKt$jsInteropFunctionsLowering$1.INSTANCE, "JsInteropFunctionsLowering", "Create delegates for JS interop", null, 8, null);
        jsInteropFunctionsLowering = namedCompilerPhaseMakeWasmModulePhase$default15;
        NamedCompilerPhase<WasmBackendContext, Iterable<IrModuleFragment>> namedCompilerPhaseMakeWasmModulePhase$default16 = makeWasmModulePhase$default(WasmLoweringPhasesKt$jsInteropFunctionCallsLowering$1.INSTANCE, "JsInteropFunctionCallsLowering", "Replace calls to delegates", null, 8, null);
        jsInteropFunctionCallsLowering = namedCompilerPhaseMakeWasmModulePhase$default16;
        NamedCompilerPhase<WasmBackendContext, Iterable<IrModuleFragment>> namedCompilerPhaseMakeWasmModulePhase$default17 = makeWasmModulePhase$default(WasmLoweringPhasesKt$enumClassConstructorLoweringPhase$1.INSTANCE, "EnumClassConstructorLowering", "Transform Enum Class into regular Class", null, 8, null);
        enumClassConstructorLoweringPhase = namedCompilerPhaseMakeWasmModulePhase$default17;
        NamedCompilerPhase<WasmBackendContext, Iterable<IrModuleFragment>> namedCompilerPhaseMakeWasmModulePhase$default18 = makeWasmModulePhase$default(WasmLoweringPhasesKt$enumClassConstructorBodyLoweringPhase$1.INSTANCE, "EnumClassConstructorBodyLowering", "Transform Enum Class into regular Class", null, 8, null);
        enumClassConstructorBodyLoweringPhase = namedCompilerPhaseMakeWasmModulePhase$default18;
        NamedCompilerPhase<WasmBackendContext, Iterable<IrModuleFragment>> namedCompilerPhaseMakeWasmModulePhase5 = makeWasmModulePhase(WasmLoweringPhasesKt$enumEntryInstancesLoweringPhase$1.INSTANCE, "EnumEntryInstancesLowering", "Create instance variable for each enum entry initialized with `null`", SetsKt.setOf(namedCompilerPhaseMakeWasmModulePhase$default17));
        enumEntryInstancesLoweringPhase = namedCompilerPhaseMakeWasmModulePhase5;
        NamedCompilerPhase<WasmBackendContext, Iterable<IrModuleFragment>> namedCompilerPhaseMakeWasmModulePhase6 = makeWasmModulePhase(WasmLoweringPhasesKt$enumEntryInstancesBodyLoweringPhase$1.INSTANCE, "EnumEntryInstancesBodyLowering", "Insert enum entry field initialization into corresponding class constructors", SetsKt.setOf(namedCompilerPhaseMakeWasmModulePhase5));
        enumEntryInstancesBodyLoweringPhase = namedCompilerPhaseMakeWasmModulePhase6;
        NamedCompilerPhase<WasmBackendContext, Iterable<IrModuleFragment>> namedCompilerPhaseMakeWasmModulePhase7 = makeWasmModulePhase(WasmLoweringPhasesKt$enumClassCreateInitializerLoweringPhase$1.INSTANCE, "EnumClassCreateInitializerLowering", "Create initializer for enum entries", SetsKt.setOf(namedCompilerPhaseMakeWasmModulePhase$default17));
        enumClassCreateInitializerLoweringPhase = namedCompilerPhaseMakeWasmModulePhase7;
        NamedCompilerPhase<WasmBackendContext, Iterable<IrModuleFragment>> namedCompilerPhaseMakeWasmModulePhase8 = makeWasmModulePhase(WasmLoweringPhasesKt$enumEntryCreateGetInstancesFunsLoweringPhase$1.INSTANCE, "EnumEntryCreateGetInstancesFunsLowering", "Create enumEntry_getInstance functions", SetsKt.setOf(namedCompilerPhaseMakeWasmModulePhase$default17));
        enumEntryCreateGetInstancesFunsLoweringPhase = namedCompilerPhaseMakeWasmModulePhase8;
        NamedCompilerPhase<WasmBackendContext, Iterable<IrModuleFragment>> namedCompilerPhaseMakeWasmModulePhase9 = makeWasmModulePhase(WasmLoweringPhasesKt$enumSyntheticFunsLoweringPhase$1.INSTANCE, "EnumSyntheticFunctionsAndPropertiesLowering", "Implement `valueOf`, `values` and `entries`", SetsKt.setOf((Object[]) new NamedCompilerPhase[]{namedCompilerPhaseMakeWasmModulePhase$default17, namedCompilerPhaseMakeWasmModulePhase7, namedCompilerPhaseMakeWasmModulePhase8}));
        enumSyntheticFunsLoweringPhase = namedCompilerPhaseMakeWasmModulePhase9;
        NamedCompilerPhase<WasmBackendContext, Iterable<IrModuleFragment>> namedCompilerPhaseMakeWasmModulePhase10 = makeWasmModulePhase(WasmLoweringPhasesKt$enumUsageLoweringPhase$1.INSTANCE, "EnumUsageLowering", "Replace enum access with invocation of corresponding function", SetsKt.setOf(namedCompilerPhaseMakeWasmModulePhase8));
        enumUsageLoweringPhase = namedCompilerPhaseMakeWasmModulePhase10;
        NamedCompilerPhase<WasmBackendContext, Iterable<IrModuleFragment>> namedCompilerPhaseMakeWasmModulePhase11 = makeWasmModulePhase(WasmLoweringPhasesKt$enumEntryRemovalLoweringPhase$1.INSTANCE, "EnumEntryRemovalLowering", "Replace enum entry with corresponding class", SetsKt.setOf(namedCompilerPhaseMakeWasmModulePhase10));
        enumEntryRemovalLoweringPhase = namedCompilerPhaseMakeWasmModulePhase11;
        NamedCompilerPhase<WasmBackendContext, Iterable<IrModuleFragment>> namedCompilerPhaseMakeWasmModulePhase$default19 = makeWasmModulePhase$default(WasmLoweringPhasesKt$propertyReferenceLowering$1.INSTANCE, "WasmPropertyReferenceLowering", "Lower property references", null, 8, null);
        propertyReferenceLowering = namedCompilerPhaseMakeWasmModulePhase$default19;
        NamedCompilerPhase<WasmBackendContext, Iterable<IrModuleFragment>> namedCompilerPhaseMakeWasmModulePhase$default20 = makeWasmModulePhase$default(WasmLoweringPhasesKt$callableReferencePhase$1.INSTANCE, "WasmCallableReferenceLowering", "Handle callable references", null, 8, null);
        callableReferencePhase = namedCompilerPhaseMakeWasmModulePhase$default20;
        NamedCompilerPhase<WasmBackendContext, Iterable<IrModuleFragment>> namedCompilerPhaseMakeWasmModulePhase$default21 = makeWasmModulePhase$default(WasmLoweringPhasesKt$singleAbstractMethodPhase$1.INSTANCE, "SingleAbstractMethod", "Replace SAM conversions with instances of interface-implementing classes", null, 8, null);
        singleAbstractMethodPhase = namedCompilerPhaseMakeWasmModulePhase$default21;
        NamedCompilerPhase<WasmBackendContext, Iterable<IrModuleFragment>> namedCompilerPhaseMakeWasmModulePhase$default22 = makeWasmModulePhase$default(new Function1<WasmBackendContext, FileLoweringPass>() { // from class: org.jetbrains.kotlin.backend.wasm.WasmLoweringPhasesKt$localDelegatedPropertiesLoweringPhase$1
            @Override // kotlin.jvm.functions.Function1
            public final FileLoweringPass invoke(WasmBackendContext it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return new LocalDelegatedPropertiesLowering();
            }
        }, "LocalDelegatedPropertiesLowering", "Transform Local Delegated properties", null, 8, null);
        localDelegatedPropertiesLoweringPhase = namedCompilerPhaseMakeWasmModulePhase$default22;
        NamedCompilerPhase<WasmBackendContext, Iterable<IrModuleFragment>> namedCompilerPhaseMakeWasmModulePhase12 = makeWasmModulePhase(WasmLoweringPhasesKt$localDeclarationsLoweringPhase$1.INSTANCE, "LocalDeclarationsLowering", "Move local declarations into nearest declaration container", SetsKt.setOf((Object[]) new NamedCompilerPhase[]{namedCompilerPhaseMakeWasmModulePhase2, namedCompilerPhaseMakeWasmModulePhase$default22}));
        localDeclarationsLoweringPhase = namedCompilerPhaseMakeWasmModulePhase12;
        NamedCompilerPhase<WasmBackendContext, Iterable<IrModuleFragment>> namedCompilerPhaseMakeWasmModulePhase13 = makeWasmModulePhase(WasmLoweringPhasesKt$localClassExtractionPhase$1.INSTANCE, "LocalClassExtractionPhase", "Move local declarations into nearest declaration container", SetsKt.setOf(namedCompilerPhaseMakeWasmModulePhase12));
        localClassExtractionPhase = namedCompilerPhaseMakeWasmModulePhase13;
        NamedCompilerPhase<WasmBackendContext, Iterable<IrModuleFragment>> namedCompilerPhaseMakeWasmModulePhase$default23 = makeWasmModulePhase$default(new Function1<WasmBackendContext, FileLoweringPass>() { // from class: org.jetbrains.kotlin.backend.wasm.WasmLoweringPhasesKt$innerClassesLoweringPhase$1
            @Override // kotlin.jvm.functions.Function1
            public final FileLoweringPass invoke(WasmBackendContext context) {
                Intrinsics.checkNotNullParameter(context, "context");
                return new InnerClassesLowering(context, context.getInnerClassesSupport());
            }
        }, "InnerClassesLowering", "Capture outer this reference to inner class", null, 8, null);
        innerClassesLoweringPhase = namedCompilerPhaseMakeWasmModulePhase$default23;
        NamedCompilerPhase<WasmBackendContext, Iterable<IrModuleFragment>> namedCompilerPhaseMakeWasmModulePhase14 = makeWasmModulePhase(new Function1<WasmBackendContext, FileLoweringPass>() { // from class: org.jetbrains.kotlin.backend.wasm.WasmLoweringPhasesKt$innerClassesMemberBodyLoweringPhase$1
            @Override // kotlin.jvm.functions.Function1
            public final FileLoweringPass invoke(WasmBackendContext context) {
                Intrinsics.checkNotNullParameter(context, "context");
                return new InnerClassesMemberBodyLowering(context, context.getInnerClassesSupport());
            }
        }, "InnerClassesMemberBody", "Replace `this` with 'outer this' field references", SetsKt.setOf(namedCompilerPhaseMakeWasmModulePhase$default23));
        innerClassesMemberBodyLoweringPhase = namedCompilerPhaseMakeWasmModulePhase14;
        NamedCompilerPhase<WasmBackendContext, Iterable<IrModuleFragment>> namedCompilerPhaseMakeWasmModulePhase$default24 = makeWasmModulePhase$default(new Function1<WasmBackendContext, FileLoweringPass>() { // from class: org.jetbrains.kotlin.backend.wasm.WasmLoweringPhasesKt$innerClassConstructorCallsLoweringPhase$1
            @Override // kotlin.jvm.functions.Function1
            public final FileLoweringPass invoke(WasmBackendContext context) {
                Intrinsics.checkNotNullParameter(context, "context");
                return new InnerClassConstructorCallsLowering(context, context.getInnerClassesSupport());
            }
        }, "InnerClassConstructorCallsLowering", "Replace inner class constructor invocation", null, 8, null);
        innerClassConstructorCallsLoweringPhase = namedCompilerPhaseMakeWasmModulePhase$default24;
        NamedCompilerPhase<WasmBackendContext, Iterable<IrModuleFragment>> namedCompilerPhaseMakeWasmModulePhase$default25 = makeWasmModulePhase$default(WasmLoweringPhasesKt$suspendFunctionsLoweringPhase$1.INSTANCE, "SuspendFunctionsLowering", "Transform suspend functions into CoroutineImpl instance and build state machine", null, 8, null);
        suspendFunctionsLoweringPhase = namedCompilerPhaseMakeWasmModulePhase$default25;
        NamedCompilerPhase<WasmBackendContext, Iterable<IrModuleFragment>> namedCompilerPhaseMakeWasmModulePhase$default26 = makeWasmModulePhase$default(WasmLoweringPhasesKt$addContinuationToNonLocalSuspendFunctionsLoweringPhase$1.INSTANCE, "AddContinuationToNonLocalSuspendFunctionsLowering", "Add explicit continuation as last parameter of suspend functions", null, 8, null);
        addContinuationToNonLocalSuspendFunctionsLoweringPhase = namedCompilerPhaseMakeWasmModulePhase$default26;
        NamedCompilerPhase<WasmBackendContext, Iterable<IrModuleFragment>> namedCompilerPhaseMakeWasmModulePhase15 = makeWasmModulePhase(WasmLoweringPhasesKt$addContinuationToFunctionCallsLoweringPhase$1.INSTANCE, "AddContinuationToFunctionCallsLowering", "Replace suspend function calls with calls with continuation", SetsKt.setOf(namedCompilerPhaseMakeWasmModulePhase$default26));
        addContinuationToFunctionCallsLoweringPhase = namedCompilerPhaseMakeWasmModulePhase15;
        NamedCompilerPhase<WasmBackendContext, Iterable<IrModuleFragment>> namedCompilerPhaseMakeCustomWasmModulePhase$default4 = makeCustomWasmModulePhase$default(WasmLoweringPhasesKt$addMainFunctionCallsLowering$1.INSTANCE, "Generate main function calls into start function", "GenerateMainFunctionCalls", null, 8, null);
        addMainFunctionCallsLowering = namedCompilerPhaseMakeCustomWasmModulePhase$default4;
        NamedCompilerPhase<WasmBackendContext, Iterable<IrModuleFragment>> namedCompilerPhaseMakeWasmModulePhase$default27 = makeWasmModulePhase$default(new Function1<WasmBackendContext, FileLoweringPass>() { // from class: org.jetbrains.kotlin.backend.wasm.WasmLoweringPhasesKt$defaultArgumentStubGeneratorPhase$1
            @Override // kotlin.jvm.functions.Function1
            public final FileLoweringPass invoke(WasmBackendContext context) {
                Intrinsics.checkNotNullParameter(context, "context");
                WasmBackendContext wasmBackendContext = context;
                return new DefaultArgumentStubGenerator(wasmBackendContext, new MaskedDefaultArgumentFunctionFactory(wasmBackendContext), false, true, false, 20, null);
            }
        }, "DefaultArgumentStubGenerator", "Generate synthetic stubs for functions with default parameter values", null, 8, null);
        defaultArgumentStubGeneratorPhase = namedCompilerPhaseMakeWasmModulePhase$default27;
        NamedCompilerPhase<WasmBackendContext, Iterable<IrModuleFragment>> namedCompilerPhaseMakeWasmModulePhase16 = makeWasmModulePhase(WasmLoweringPhasesKt$defaultArgumentPatchOverridesPhase$1.INSTANCE, "DefaultArgumentsPatchOverrides", "Patch overrides for fake override dispatch functions", SetsKt.setOf(namedCompilerPhaseMakeWasmModulePhase$default27));
        defaultArgumentPatchOverridesPhase = namedCompilerPhaseMakeWasmModulePhase16;
        NamedCompilerPhase<WasmBackendContext, Iterable<IrModuleFragment>> namedCompilerPhaseMakeWasmModulePhase17 = makeWasmModulePhase(new Function1<WasmBackendContext, FileLoweringPass>() { // from class: org.jetbrains.kotlin.backend.wasm.WasmLoweringPhasesKt$defaultParameterInjectorPhase$1
            @Override // kotlin.jvm.functions.Function1
            public final FileLoweringPass invoke(WasmBackendContext context) {
                Intrinsics.checkNotNullParameter(context, "context");
                WasmBackendContext wasmBackendContext = context;
                return new DefaultParameterInjector(wasmBackendContext, new MaskedDefaultArgumentFunctionFactory(wasmBackendContext), false, true, false, 20, null);
            }
        }, "DefaultParameterInjector", "Replace call site with default parameters with corresponding stub function", SetsKt.setOf(namedCompilerPhaseMakeWasmModulePhase$default23));
        defaultParameterInjectorPhase = namedCompilerPhaseMakeWasmModulePhase17;
        NamedCompilerPhase<WasmBackendContext, Iterable<IrModuleFragment>> namedCompilerPhaseMakeWasmModulePhase$default28 = makeWasmModulePhase$default(WasmLoweringPhasesKt$defaultParameterCleanerPhase$1.INSTANCE, "DefaultParameterCleaner", "Clean default parameters up", null, 8, null);
        defaultParameterCleanerPhase = namedCompilerPhaseMakeWasmModulePhase$default28;
        NamedCompilerPhase<WasmBackendContext, Iterable<IrModuleFragment>> namedCompilerPhaseMakeWasmModulePhase$default29 = makeWasmModulePhase$default(new Function1<WasmBackendContext, FileLoweringPass>() { // from class: org.jetbrains.kotlin.backend.wasm.WasmLoweringPhasesKt$propertiesLoweringPhase$1
            @Override // kotlin.jvm.functions.Function1
            public final FileLoweringPass invoke(WasmBackendContext it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return new PropertiesLowering();
            }
        }, "PropertiesLowering", "Move fields and accessors out from its property", null, 8, null);
        propertiesLoweringPhase = namedCompilerPhaseMakeWasmModulePhase$default29;
        NamedCompilerPhase<WasmBackendContext, Iterable<IrModuleFragment>> namedCompilerPhaseMakeWasmModulePhase$default30 = makeWasmModulePhase$default(WasmLoweringPhasesKt$primaryConstructorLoweringPhase$1.INSTANCE, "PrimaryConstructorLowering", "Creates primary constructor if it doesn't exist", null, 8, null);
        primaryConstructorLoweringPhase = namedCompilerPhaseMakeWasmModulePhase$default30;
        NamedCompilerPhase<WasmBackendContext, Iterable<IrModuleFragment>> namedCompilerPhaseMakeWasmModulePhase18 = makeWasmModulePhase(WasmLoweringPhasesKt$delegateToPrimaryConstructorLoweringPhase$1.INSTANCE, "DelegateToSyntheticPrimaryConstructor", "Delegates to synthetic primary constructor", SetsKt.setOf(namedCompilerPhaseMakeWasmModulePhase$default30));
        delegateToPrimaryConstructorLoweringPhase = namedCompilerPhaseMakeWasmModulePhase18;
        NamedCompilerPhase<WasmBackendContext, Iterable<IrModuleFragment>> namedCompilerPhaseMakeWasmModulePhase19 = makeWasmModulePhase(WasmLoweringPhasesKt$initializersLoweringPhase$1.INSTANCE, "InitializersLowering", "Merge init block and field initializers into [primary] constructor", SetsKt.setOf((Object[]) new NamedCompilerPhase[]{namedCompilerPhaseMakeWasmModulePhase$default30, namedCompilerPhaseMakeWasmModulePhase13}));
        initializersLoweringPhase = namedCompilerPhaseMakeWasmModulePhase19;
        NamedCompilerPhase<WasmBackendContext, Iterable<IrModuleFragment>> namedCompilerPhaseMakeWasmModulePhase20 = makeWasmModulePhase(WasmLoweringPhasesKt$initializersCleanupLoweringPhase$1.INSTANCE, "InitializersCleanupLowering", "Remove non-static anonymous initializers and field init expressions", SetsKt.setOf(namedCompilerPhaseMakeWasmModulePhase19));
        initializersCleanupLoweringPhase = namedCompilerPhaseMakeWasmModulePhase20;
        NamedCompilerPhase<WasmBackendContext, Iterable<IrModuleFragment>> namedCompilerPhaseMakeCustomWasmModulePhase$default5 = makeCustomWasmModulePhase$default(new Function2<WasmBackendContext, IrModuleFragment, Unit>() { // from class: org.jetbrains.kotlin.backend.wasm.WasmLoweringPhasesKt$excludeDeclarationsFromCodegenPhase$1
            @Override // kotlin.jvm.functions.Function2
            public /* bridge */ /* synthetic */ Unit invoke(WasmBackendContext wasmBackendContext, IrModuleFragment irModuleFragment) {
                invoke2(wasmBackendContext, irModuleFragment);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(WasmBackendContext context, IrModuleFragment module) {
                Intrinsics.checkNotNullParameter(context, "context");
                Intrinsics.checkNotNullParameter(module, "module");
                ExcludeDeclarationsFromCodegenKt.excludeDeclarationsFromCodegen(context, module);
            }
        }, "Move excluded declarations to separate place", "ExcludeDeclarationsFromCodegen", null, 8, null);
        excludeDeclarationsFromCodegenPhase = namedCompilerPhaseMakeCustomWasmModulePhase$default5;
        NamedCompilerPhase<WasmBackendContext, Iterable<IrModuleFragment>> namedCompilerPhaseMakeWasmModulePhase21 = makeWasmModulePhase(WasmLoweringPhasesKt$tryCatchCanonicalization$1.INSTANCE, "TryCatchCanonicalization", "Transforms try/catch statements into canonical form supported by the wasm codegen", SetsKt.setOf(namedCompilerPhaseMakeCustomWasmModulePhase));
        tryCatchCanonicalization = namedCompilerPhaseMakeWasmModulePhase21;
        NamedCompilerPhase<WasmBackendContext, Iterable<IrModuleFragment>> namedCompilerPhaseMakeWasmModulePhase$default31 = makeWasmModulePhase$default(WasmLoweringPhasesKt$bridgesConstructionPhase$1.INSTANCE, "BridgesConstruction", "Generate bridges", null, 8, null);
        bridgesConstructionPhase = namedCompilerPhaseMakeWasmModulePhase$default31;
        NamedCompilerPhase<WasmBackendContext, Iterable<IrModuleFragment>> namedCompilerPhaseMakeWasmModulePhase$default32 = makeWasmModulePhase$default(new Function1<WasmBackendContext, FileLoweringPass>() { // from class: org.jetbrains.kotlin.backend.wasm.WasmLoweringPhasesKt$inlineClassDeclarationLoweringPhase$1
            @Override // kotlin.jvm.functions.Function1
            public final FileLoweringPass invoke(WasmBackendContext it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return new InlineClassLowering(it).getInlineClassDeclarationLowering();
            }
        }, "InlineClassDeclarationLowering", "Handle inline class declarations", null, 8, null);
        inlineClassDeclarationLoweringPhase = namedCompilerPhaseMakeWasmModulePhase$default32;
        NamedCompilerPhase<WasmBackendContext, Iterable<IrModuleFragment>> namedCompilerPhaseMakeWasmModulePhase$default33 = makeWasmModulePhase$default(new Function1<WasmBackendContext, FileLoweringPass>() { // from class: org.jetbrains.kotlin.backend.wasm.WasmLoweringPhasesKt$inlineClassUsageLoweringPhase$1
            @Override // kotlin.jvm.functions.Function1
            public final FileLoweringPass invoke(WasmBackendContext it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return new InlineClassLowering(it).getInlineClassUsageLowering();
            }
        }, "InlineClassUsageLowering", "Handle inline class usages", null, 8, null);
        inlineClassUsageLoweringPhase = namedCompilerPhaseMakeWasmModulePhase$default33;
        NamedCompilerPhase<WasmBackendContext, Iterable<IrModuleFragment>> namedCompilerPhaseMakeWasmModulePhase$default34 = makeWasmModulePhase$default(new Function1<WasmBackendContext, FileLoweringPass>() { // from class: org.jetbrains.kotlin.backend.wasm.WasmLoweringPhasesKt$autoboxingTransformerPhase$1
            @Override // kotlin.jvm.functions.Function1
            public final FileLoweringPass invoke(WasmBackendContext context) {
                Intrinsics.checkNotNullParameter(context, "context");
                return new AutoboxingTransformer(context, false, 2, null);
            }
        }, "AutoboxingTransformer", "Insert box/unbox intrinsics", null, 8, null);
        autoboxingTransformerPhase = namedCompilerPhaseMakeWasmModulePhase$default34;
        NamedCompilerPhase<WasmBackendContext, Iterable<IrModuleFragment>> namedCompilerPhaseMakeWasmModulePhase$default35 = makeWasmModulePhase$default(WasmLoweringPhasesKt$staticMembersLoweringPhase$1.INSTANCE, "StaticMembersLowering", "Move static member declarations to top-level", null, 8, null);
        staticMembersLoweringPhase = namedCompilerPhaseMakeWasmModulePhase$default35;
        NamedCompilerPhase<WasmBackendContext, Iterable<IrModuleFragment>> namedCompilerPhaseMakeWasmModulePhase$default36 = makeWasmModulePhase$default(WasmLoweringPhasesKt$classReferenceLoweringPhase$1.INSTANCE, "ClassReferenceLowering", "Handle class references", null, 8, null);
        classReferenceLoweringPhase = namedCompilerPhaseMakeWasmModulePhase$default36;
        NamedCompilerPhase<WasmBackendContext, Iterable<IrModuleFragment>> namedCompilerPhaseMakeWasmModulePhase$default37 = makeWasmModulePhase$default(WasmLoweringPhasesKt$wasmVarargExpressionLoweringPhase$1.INSTANCE, "WasmVarargExpressionLowering", "Lower varargs", null, 8, null);
        wasmVarargExpressionLoweringPhase = namedCompilerPhaseMakeWasmModulePhase$default37;
        NamedCompilerPhase<WasmBackendContext, Iterable<IrModuleFragment>> namedCompilerPhaseMakeWasmModulePhase$default38 = makeWasmModulePhase$default(WasmLoweringPhasesKt$fieldInitializersLoweringPhase$1.INSTANCE, "FieldInitializersLowering", "Move field initializers to start function", null, 8, null);
        fieldInitializersLoweringPhase = namedCompilerPhaseMakeWasmModulePhase$default38;
        NamedCompilerPhase<WasmBackendContext, Iterable<IrModuleFragment>> namedCompilerPhaseMakeWasmModulePhase$default39 = makeWasmModulePhase$default(WasmLoweringPhasesKt$builtInsLoweringPhase0$1.INSTANCE, "BuiltInsLowering0", "Lower IR builtins 0", null, 8, null);
        builtInsLoweringPhase0 = namedCompilerPhaseMakeWasmModulePhase$default39;
        NamedCompilerPhase<WasmBackendContext, Iterable<IrModuleFragment>> namedCompilerPhaseMakeWasmModulePhase$default40 = makeWasmModulePhase$default(WasmLoweringPhasesKt$builtInsLoweringPhase$1.INSTANCE, "BuiltInsLowering", "Lower IR builtins", null, 8, null);
        builtInsLoweringPhase = namedCompilerPhaseMakeWasmModulePhase$default40;
        NamedCompilerPhase<WasmBackendContext, Iterable<IrModuleFragment>> namedCompilerPhaseMakeWasmModulePhase22 = makeWasmModulePhase(WasmLoweringPhasesKt$associatedObjectsLowering$1.INSTANCE, "AssociatedObjectsLowering", "Load associated object init body", SetsKt.setOf(namedCompilerPhaseMakeWasmModulePhase13));
        associatedObjectsLowering = namedCompilerPhaseMakeWasmModulePhase22;
        NamedCompilerPhase<WasmBackendContext, Iterable<IrModuleFragment>> namedCompilerPhaseMakeWasmModulePhase23 = makeWasmModulePhase(WasmLoweringPhasesKt$objectDeclarationLoweringPhase$1.INSTANCE, "ObjectDeclarationLowering", "Create lazy object instance generator functions", SetsKt.setOf(namedCompilerPhaseMakeWasmModulePhase7));
        objectDeclarationLoweringPhase = namedCompilerPhaseMakeWasmModulePhase23;
        NamedCompilerPhase<WasmBackendContext, Iterable<IrModuleFragment>> namedCompilerPhaseMakeWasmModulePhase$default41 = makeWasmModulePhase$default(WasmLoweringPhasesKt$objectUsageLoweringPhase$1.INSTANCE, "ObjectUsageLowering", "Transform IrGetObjectValue into instance generator call", null, 8, null);
        objectUsageLoweringPhase = namedCompilerPhaseMakeWasmModulePhase$default41;
        NamedCompilerPhase<WasmBackendContext, Iterable<IrModuleFragment>> namedCompilerPhaseMakeWasmModulePhase$default42 = makeWasmModulePhase$default(WasmLoweringPhasesKt$explicitlyCastExternalTypesPhase$1.INSTANCE, "ExplicitlyCastExternalTypesLowering", "Add explicit casts when converting between external and non-external types", null, 8, null);
        explicitlyCastExternalTypesPhase = namedCompilerPhaseMakeWasmModulePhase$default42;
        NamedCompilerPhase<WasmBackendContext, Iterable<IrModuleFragment>> namedCompilerPhaseMakeWasmModulePhase$default43 = makeWasmModulePhase$default(WasmLoweringPhasesKt$typeOperatorLoweringPhase$1.INSTANCE, "TypeOperatorLowering", "Lower IrTypeOperator with corresponding logic", null, 8, null);
        typeOperatorLoweringPhase = namedCompilerPhaseMakeWasmModulePhase$default43;
        NamedCompilerPhase<WasmBackendContext, Iterable<IrModuleFragment>> namedCompilerPhaseMakeWasmModulePhase$default44 = makeWasmModulePhase$default(WasmLoweringPhasesKt$genericReturnTypeLowering$1.INSTANCE, "GenericReturnTypeLowering", "Cast calls to functions with generic return types", null, 8, null);
        genericReturnTypeLowering = namedCompilerPhaseMakeWasmModulePhase$default44;
        NamedCompilerPhase<WasmBackendContext, Iterable<IrModuleFragment>> namedCompilerPhaseMakeWasmModulePhase$default45 = makeWasmModulePhase$default(WasmLoweringPhasesKt$eraseVirtualDispatchReceiverParametersTypes$1.INSTANCE, "EraseVirtualDispatchReceiverParametersTypes", "Erase types of virtual dispatch receivers to Any", null, 8, null);
        eraseVirtualDispatchReceiverParametersTypes = namedCompilerPhaseMakeWasmModulePhase$default45;
        NamedCompilerPhase<WasmBackendContext, Iterable<IrModuleFragment>> namedCompilerPhaseMakeWasmModulePhase$default46 = makeWasmModulePhase$default(WasmLoweringPhasesKt$virtualDispatchReceiverExtractionPhase$1.INSTANCE, "VirtualDispatchReceiverExtraction", "Eliminate side-effects in dispatch receivers of virtual function calls", null, 8, null);
        virtualDispatchReceiverExtractionPhase = namedCompilerPhaseMakeWasmModulePhase$default46;
        NamedCompilerPhase<WasmBackendContext, Iterable<IrModuleFragment>> namedCompilerPhaseMakeWasmModulePhase$default47 = makeWasmModulePhase$default(WasmLoweringPhasesKt$forLoopsLoweringPhase$1.INSTANCE, "ForLoopsLowering", "[Optimization] For loops lowering", null, 8, null);
        forLoopsLoweringPhase = namedCompilerPhaseMakeWasmModulePhase$default47;
        NamedCompilerPhase<WasmBackendContext, Iterable<IrModuleFragment>> namedCompilerPhaseMakeWasmModulePhase$default48 = makeWasmModulePhase$default(WasmLoweringPhasesKt$propertyLazyInitLoweringPhase$1.INSTANCE, "PropertyLazyInitLowering", "Make property init as lazy", null, 8, null);
        propertyLazyInitLoweringPhase = namedCompilerPhaseMakeWasmModulePhase$default48;
        NamedCompilerPhase<WasmBackendContext, Iterable<IrModuleFragment>> namedCompilerPhaseMakeWasmModulePhase$default49 = makeWasmModulePhase$default(WasmLoweringPhasesKt$removeInitializersForLazyProperties$1.INSTANCE, "RemoveInitializersForLazyProperties", "Remove property initializers if they was initialized lazily", null, 8, null);
        removeInitializersForLazyProperties = namedCompilerPhaseMakeWasmModulePhase$default49;
        NamedCompilerPhase<WasmBackendContext, Iterable<IrModuleFragment>> namedCompilerPhaseMakeWasmModulePhase$default50 = makeWasmModulePhase$default(WasmLoweringPhasesKt$unhandledExceptionLowering$1.INSTANCE, "UnhandledExceptionLowering", "Wrap JsExport functions with try-catch to convert unhandled Wasm exception into Js exception", null, 8, null);
        unhandledExceptionLowering = namedCompilerPhaseMakeWasmModulePhase$default50;
        NamedCompilerPhase<WasmBackendContext, Iterable<IrModuleFragment>> namedCompilerPhaseMakeWasmModulePhase$default51 = makeWasmModulePhase$default(WasmLoweringPhasesKt$propertyAccessorInlinerLoweringPhase$1.INSTANCE, "PropertyAccessorInlineLowering", "[Optimization] Inline property accessors", null, 8, null);
        propertyAccessorInlinerLoweringPhase = namedCompilerPhaseMakeWasmModulePhase$default51;
        NamedCompilerPhase<WasmBackendContext, Iterable<IrModuleFragment>> namedCompilerPhaseMakeWasmModulePhase$default52 = makeWasmModulePhase$default(WasmLoweringPhasesKt$expressionBodyTransformer$1.INSTANCE, "ExpressionBodyTransformer", "Replace IrExpressionBody with IrBlockBody", null, 8, null);
        expressionBodyTransformer = namedCompilerPhaseMakeWasmModulePhase$default52;
        NamedCompilerPhase<WasmBackendContext, Iterable<IrModuleFragment>> namedCompilerPhaseMakeWasmModulePhase$default53 = makeWasmModulePhase$default(WasmLoweringPhasesKt$unitToVoidLowering$1.INSTANCE, "UnitToVoidLowering", "Replace some Unit's with Void's", null, 8, null);
        unitToVoidLowering = namedCompilerPhaseMakeWasmModulePhase$default53;
        NamedCompilerPhase<WasmBackendContext, Iterable<IrModuleFragment>> namedCompilerPhaseMakeWasmModulePhase24 = makeWasmModulePhase(WasmLoweringPhasesKt$purifyObjectInstanceGettersLoweringPhase$1.INSTANCE, "PurifyObjectInstanceGettersLowering", "[Optimization] Make object instance getter functions pure whenever it's possible", SetsKt.setOf((Object[]) new NamedCompilerPhase[]{namedCompilerPhaseMakeWasmModulePhase23, namedCompilerPhaseMakeWasmModulePhase$default41}));
        purifyObjectInstanceGettersLoweringPhase = namedCompilerPhaseMakeWasmModulePhase24;
        NamedCompilerPhase<WasmBackendContext, Iterable<IrModuleFragment>> namedCompilerPhaseMakeWasmModulePhase25 = makeWasmModulePhase(WasmLoweringPhasesKt$inlineObjectsWithPureInitializationLoweringPhase$1.INSTANCE, "InlineObjectsWithPureInitializationLowering", "[Optimization] Inline object instance fields getters whenever it's possible", SetsKt.setOf(namedCompilerPhaseMakeWasmModulePhase24));
        inlineObjectsWithPureInitializationLoweringPhase = namedCompilerPhaseMakeWasmModulePhase25;
        wasmPhases = new NamedCompilerPhase<>("IrModuleLowering", "IR module lowering", null, PhaseBuildersKt.then(PhaseBuildersKt.then(PhaseBuildersKt.then(PhaseBuildersKt.then(PhaseBuildersKt.then(PhaseBuildersKt.then(PhaseBuildersKt.then(PhaseBuildersKt.then(PhaseBuildersKt.then(PhaseBuildersKt.then(PhaseBuildersKt.then(PhaseBuildersKt.then(PhaseBuildersKt.then(PhaseBuildersKt.then(PhaseBuildersKt.then(PhaseBuildersKt.then(PhaseBuildersKt.then(PhaseBuildersKt.then(PhaseBuildersKt.then(PhaseBuildersKt.then(PhaseBuildersKt.then(PhaseBuildersKt.then(PhaseBuildersKt.then(PhaseBuildersKt.then(PhaseBuildersKt.then(PhaseBuildersKt.then(PhaseBuildersKt.then(PhaseBuildersKt.then(PhaseBuildersKt.then(PhaseBuildersKt.then(PhaseBuildersKt.then(PhaseBuildersKt.then(PhaseBuildersKt.then(PhaseBuildersKt.then(PhaseBuildersKt.then(PhaseBuildersKt.then(PhaseBuildersKt.then(PhaseBuildersKt.then(PhaseBuildersKt.then(PhaseBuildersKt.then(PhaseBuildersKt.then(PhaseBuildersKt.then(PhaseBuildersKt.then(PhaseBuildersKt.then(PhaseBuildersKt.then(PhaseBuildersKt.then(PhaseBuildersKt.then(PhaseBuildersKt.then(PhaseBuildersKt.then(PhaseBuildersKt.then(PhaseBuildersKt.then(PhaseBuildersKt.then(PhaseBuildersKt.then(PhaseBuildersKt.then(PhaseBuildersKt.then(PhaseBuildersKt.then(PhaseBuildersKt.then(PhaseBuildersKt.then(PhaseBuildersKt.then(PhaseBuildersKt.then(PhaseBuildersKt.then(PhaseBuildersKt.then(PhaseBuildersKt.then(PhaseBuildersKt.then(PhaseBuildersKt.then(PhaseBuildersKt.then(PhaseBuildersKt.then(PhaseBuildersKt.then(PhaseBuildersKt.then(PhaseBuildersKt.then(PhaseBuildersKt.then(PhaseBuildersKt.then(PhaseBuildersKt.then(PhaseBuildersKt.then(PhaseBuildersKt.then(PhaseBuildersKt.then(PhaseBuildersKt.then(PhaseBuildersKt.then(PhaseBuildersKt.then(PhaseBuildersKt.then(PhaseBuildersKt.then(PhaseBuildersKt.then(PhaseBuildersKt.then(namedCompilerPhaseMakeCustomWasmModulePhase$default, namedCompilerPhaseMakeWasmModulePhase$default12), namedCompilerPhaseMakeCustomWasmModulePhase$default3), namedCompilerPhaseMakeCustomWasmModulePhase$default5), namedCompilerPhaseMakeWasmModulePhase$default), namedCompilerPhaseMakeWasmModulePhase$default3), namedCompilerPhaseMakeWasmModulePhase$default4), namedCompilerPhaseMakeWasmModulePhase$default5), namedCompilerPhaseMakeWasmModulePhase$default6), namedCompilerPhaseMakeWasmModulePhase), namedCompilerPhaseMakeWasmModulePhase2), namedCompilerPhaseMakeWasmModulePhase$default7), namedCompilerPhaseMakeWasmModulePhase$default8), namedCompilerPhaseMakeWasmModulePhase3), namedCompilerPhaseMakeWasmModulePhase$default9), namedCompilerPhaseMakeCustomWasmModulePhase), namedCompilerPhaseMakeWasmModulePhase4), namedCompilerPhaseMakeWasmModulePhase$default10), namedCompilerPhaseMakeWasmModulePhase$default17), namedCompilerPhaseMakeWasmModulePhase$default18), namedCompilerPhaseMakeWasmModulePhase5), namedCompilerPhaseMakeWasmModulePhase6), namedCompilerPhaseMakeWasmModulePhase7), namedCompilerPhaseMakeWasmModulePhase8), namedCompilerPhaseMakeWasmModulePhase9), namedCompilerPhaseMakeWasmModulePhase$default19), namedCompilerPhaseMakeWasmModulePhase$default20), namedCompilerPhaseMakeWasmModulePhase$default21), namedCompilerPhaseMakeWasmModulePhase$default22), namedCompilerPhaseMakeWasmModulePhase12), namedCompilerPhaseMakeWasmModulePhase13), namedCompilerPhaseMakeWasmModulePhase$default23), namedCompilerPhaseMakeWasmModulePhase14), namedCompilerPhaseMakeWasmModulePhase$default24), namedCompilerPhaseMakeWasmModulePhase$default29), namedCompilerPhaseMakeWasmModulePhase$default30), namedCompilerPhaseMakeWasmModulePhase18), namedCompilerPhaseMakeWasmModulePhase$default11), namedCompilerPhaseMakeWasmModulePhase$default13), namedCompilerPhaseMakeWasmModulePhase$default14), namedCompilerPhaseMakeWasmModulePhase$default15), namedCompilerPhaseMakeWasmModulePhase$default16), namedCompilerPhaseMakeWasmModulePhase10), namedCompilerPhaseMakeWasmModulePhase11), namedCompilerPhaseMakeWasmModulePhase$default25), namedCompilerPhaseMakeWasmModulePhase19), namedCompilerPhaseMakeWasmModulePhase20), namedCompilerPhaseMakeWasmModulePhase$default26), namedCompilerPhaseMakeWasmModulePhase15), namedCompilerPhaseMakeCustomWasmModulePhase$default4), namedCompilerPhaseMakeWasmModulePhase$default50), namedCompilerPhaseMakeWasmModulePhase21), namedCompilerPhaseMakeWasmModulePhase$default47), namedCompilerPhaseMakeWasmModulePhase$default48), namedCompilerPhaseMakeWasmModulePhase$default49), namedCompilerPhaseMakeWasmModulePhase$default51), namedCompilerPhaseMakeWasmModulePhase$default2), namedCompilerPhaseMakeWasmModulePhase$default27), namedCompilerPhaseMakeWasmModulePhase16), namedCompilerPhaseMakeWasmModulePhase17), namedCompilerPhaseMakeWasmModulePhase$default28), namedCompilerPhaseMakeWasmModulePhase$default36), namedCompilerPhaseMakeWasmModulePhase$default37), namedCompilerPhaseMakeWasmModulePhase$default32), namedCompilerPhaseMakeWasmModulePhase$default33), namedCompilerPhaseMakeWasmModulePhase$default52), namedCompilerPhaseMakeWasmModulePhase$default45), namedCompilerPhaseMakeWasmModulePhase$default31), namedCompilerPhaseMakeWasmModulePhase22), namedCompilerPhaseMakeWasmModulePhase23), namedCompilerPhaseMakeWasmModulePhase$default44), namedCompilerPhaseMakeWasmModulePhase$default53), namedCompilerPhaseMakeWasmModulePhase$default39), namedCompilerPhaseMakeWasmModulePhase$default34), namedCompilerPhaseMakeWasmModulePhase$default41), namedCompilerPhaseMakeWasmModulePhase24), namedCompilerPhaseMakeWasmModulePhase$default38), namedCompilerPhaseMakeWasmModulePhase$default42), namedCompilerPhaseMakeWasmModulePhase$default43), namedCompilerPhaseMakeWasmModulePhase$default40), namedCompilerPhaseMakeWasmModulePhase$default46), namedCompilerPhaseMakeWasmModulePhase$default35), namedCompilerPhaseMakeWasmModulePhase25), namedCompilerPhaseMakeCustomWasmModulePhase$default2), null, null, null, SetsKt.setOf(LowerKt.toMultiModuleAction(DumperVerifierKt.getDefaultDumper())), 0, 372, null);
    }

    public static final NamedCompilerPhase<WasmBackendContext, Iterable<IrModuleFragment>> getWasmPhases() {
        return wasmPhases;
    }
}
