package org.jetbrains.kotlin.ir.declarations;

import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.List;
import kotlin.Deprecated;
import kotlin.DeprecationLevel;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.descriptors.ClassKind;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.DescriptorVisibility;
import org.jetbrains.kotlin.descriptors.Modality;
import org.jetbrains.kotlin.descriptors.SourceElement;
import org.jetbrains.kotlin.ir.IrStatement;
import org.jetbrains.kotlin.ir.expressions.IrBlockBody;
import org.jetbrains.kotlin.ir.expressions.IrExpression;
import org.jetbrains.kotlin.ir.expressions.IrExpressionBody;
import org.jetbrains.kotlin.ir.symbols.IrAnonymousInitializerSymbol;
import org.jetbrains.kotlin.ir.symbols.IrClassSymbol;
import org.jetbrains.kotlin.ir.symbols.IrConstructorSymbol;
import org.jetbrains.kotlin.ir.symbols.IrEnumEntrySymbol;
import org.jetbrains.kotlin.ir.symbols.IrFieldSymbol;
import org.jetbrains.kotlin.ir.symbols.IrLocalDelegatedPropertySymbol;
import org.jetbrains.kotlin.ir.symbols.IrPropertySymbol;
import org.jetbrains.kotlin.ir.symbols.IrSimpleFunctionSymbol;
import org.jetbrains.kotlin.ir.symbols.IrTypeAliasSymbol;
import org.jetbrains.kotlin.ir.symbols.IrTypeParameterSymbol;
import org.jetbrains.kotlin.ir.symbols.IrValueParameterSymbol;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.serialization.deserialization.descriptors.DeserializedContainerSource;
import org.jetbrains.kotlin.types.Variance;
import org.mozilla.javascript.NativeSymbol;

/* JADX INFO: compiled from: IrFactory.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\u009c\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\bf\u0018\u00002\u00020\u0001J2\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\t2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000e2\b\b\u0002\u0010\u000f\u001a\u00020\u0010H&J\u0018\u0010\u0011\u001a\u00020\u00122\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\tH&J&\u0010\u0011\u001a\u00020\u00122\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\t2\f\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00150\u0014H\u0017J1\u0010\u0011\u001a\u00020\u00122\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\t2\u0017\u0010\u0016\u001a\u0013\u0012\u0004\u0012\u00020\u0012\u0012\u0004\u0012\u00020\u00180\u0017¢\u0006\u0002\b\u0019H\u0017J\u0098\u0001\u0010\u001a\u001a\u00020\u001b2\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\t2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u001c2\u0006\u0010\u001d\u001a\u00020\u001e2\u0006\u0010\u001f\u001a\u00020 2\u0006\u0010!\u001a\u00020\"2\u0006\u0010#\u001a\u00020$2\b\b\u0002\u0010%\u001a\u00020\u00102\b\b\u0002\u0010&\u001a\u00020\u00102\b\b\u0002\u0010'\u001a\u00020\u00102\b\b\u0002\u0010(\u001a\u00020\u00102\b\b\u0002\u0010)\u001a\u00020\u00102\b\b\u0002\u0010*\u001a\u00020\u00102\b\b\u0002\u0010+\u001a\u00020\u00102\b\b\u0002\u0010,\u001a\u00020-H\u0017J\u0098\u0001\u0010\u001a\u001a\u00020\u001b2\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\t2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\u001d\u001a\u00020\u001e2\u0006\u0010!\u001a\u00020\"2\u0006\u0010\r\u001a\u00020\u001c2\u0006\u0010\u001f\u001a\u00020 2\u0006\u0010#\u001a\u00020$2\b\b\u0002\u0010(\u001a\u00020\u00102\b\b\u0002\u0010%\u001a\u00020\u00102\b\b\u0002\u0010&\u001a\u00020\u00102\b\b\u0002\u0010'\u001a\u00020\u00102\b\b\u0002\u0010)\u001a\u00020\u00102\b\b\u0002\u0010*\u001a\u00020\u00102\b\b\u0002\u0010+\u001a\u00020\u00102\b\b\u0002\u0010,\u001a\u00020-H&Jl\u0010.\u001a\u00020/2\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\t2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u0002002\u0006\u0010\u001d\u001a\u00020\u001e2\u0006\u0010!\u001a\u00020\"2\u0006\u00101\u001a\u0002022\u0006\u00103\u001a\u00020\u00102\u0006\u0010(\u001a\u00020\u00102\u0006\u00104\u001a\u00020\u00102\u0006\u0010*\u001a\u00020\u00102\n\b\u0002\u00105\u001a\u0004\u0018\u000106H\u0017Jn\u0010.\u001a\u00020/2\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\t2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\u001d\u001a\u00020\u001e2\u0006\u0010!\u001a\u00020\"2\u0006\u00103\u001a\u00020\u00102\u0006\u0010*\u001a\u00020\u00102\u0006\u00101\u001a\u0002022\u0006\u0010\r\u001a\u0002002\u0006\u00104\u001a\u00020\u00102\b\b\u0002\u0010(\u001a\u00020\u00102\n\b\u0002\u00105\u001a\u0004\u0018\u000106H&J0\u00107\u001a\u0002082\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\t2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u0002092\u0006\u0010\u001d\u001a\u00020\u001eH\u0017J0\u00107\u001a\u0002082\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\t2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\u001d\u001a\u00020\u001e2\u0006\u0010\r\u001a\u000209H&J$\u0010:\u001a\u00020;2\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\t2\n\b\u0002\u0010<\u001a\u0004\u0018\u00010=H&J \u0010>\u001a\u00020?2\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\t2\u0006\u0010@\u001a\u00020AH&J\u0010\u0010>\u001a\u00020?2\u0006\u0010@\u001a\u00020AH\u0017JX\u0010B\u001a\u00020C2\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\t2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020D2\u0006\u0010\u001d\u001a\u00020\u001e2\u0006\u0010E\u001a\u0002022\u0006\u0010!\u001a\u00020\"2\u0006\u0010F\u001a\u00020\u00102\u0006\u0010(\u001a\u00020\u00102\u0006\u0010\u000f\u001a\u00020\u0010H\u0017JZ\u0010B\u001a\u00020C2\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\t2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\u001d\u001a\u00020\u001e2\u0006\u0010!\u001a\u00020\"2\u0006\u0010\r\u001a\u00020D2\u0006\u0010E\u001a\u0002022\u0006\u0010F\u001a\u00020\u00102\u0006\u0010\u000f\u001a\u00020\u00102\b\b\u0002\u0010(\u001a\u00020\u0010H&J\u0096\u0001\u0010G\u001a\u00020H2\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\t2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020I2\u0006\u0010\u001d\u001a\u00020\u001e2\u0006\u0010!\u001a\u00020\"2\u0006\u0010#\u001a\u00020$2\u0006\u00101\u001a\u0002022\u0006\u00103\u001a\u00020\u00102\u0006\u0010(\u001a\u00020\u00102\u0006\u0010J\u001a\u00020\u00102\u0006\u0010K\u001a\u00020\u00102\u0006\u0010L\u001a\u00020\u00102\u0006\u0010M\u001a\u00020\u00102\u0006\u0010*\u001a\u00020\u00102\b\b\u0002\u0010N\u001a\u00020\u00102\n\b\u0002\u00105\u001a\u0004\u0018\u000106H\u0017J\u0084\u0001\u0010O\u001a\u00020P2\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\t2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\u001d\u001a\u00020\u001e2\u0006\u0010!\u001a\u00020\"2\u0006\u00103\u001a\u00020\u00102\u0006\u0010*\u001a\u00020\u00102\u0006\u00101\u001a\u0002022\u0006\u0010#\u001a\u00020$2\u0006\u0010J\u001a\u00020\u00102\u0006\u0010K\u001a\u00020\u00102\u0006\u0010L\u001a\u00020\u00102\u0006\u0010M\u001a\u00020\u00102\b\b\u0002\u0010(\u001a\u00020\u00102\b\b\u0002\u0010N\u001a\u00020\u0010H&Jx\u0010O\u001a\u00020H2\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\t2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\u001d\u001a\u00020\u001e2\u0006\u0010!\u001a\u00020\"2\u0006\u0010#\u001a\u00020$2\u0006\u00101\u001a\u0002022\u0006\u00103\u001a\u00020\u00102\u0006\u0010(\u001a\u00020\u00102\u0006\u0010J\u001a\u00020\u00102\u0006\u0010K\u001a\u00020\u00102\u0006\u0010L\u001a\u00020\u00102\u0006\u0010M\u001a\u00020\u00102\u0006\u0010*\u001a\u00020\u0010H\u0017J@\u0010Q\u001a\u00020R2\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\t2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020S2\u0006\u0010\u001d\u001a\u00020\u001e2\u0006\u0010E\u001a\u0002022\u0006\u0010T\u001a\u00020\u0010H\u0017J@\u0010Q\u001a\u00020R2\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\t2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\u001d\u001a\u00020\u001e2\u0006\u0010\r\u001a\u00020S2\u0006\u0010E\u001a\u0002022\u0006\u0010T\u001a\u00020\u0010H&J\u0088\u0001\u0010U\u001a\u00020V2\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\t2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020W2\u0006\u0010\u001d\u001a\u00020\u001e2\u0006\u0010!\u001a\u00020\"2\u0006\u0010#\u001a\u00020$2\u0006\u0010T\u001a\u00020\u00102\u0006\u0010X\u001a\u00020\u00102\u0006\u0010Y\u001a\u00020\u00102\u0006\u0010Z\u001a\u00020\u00102\u0006\u0010(\u001a\u00020\u00102\b\b\u0002\u0010*\u001a\u00020\u00102\b\b\u0002\u0010N\u001a\u00020\u00102\n\b\u0002\u00105\u001a\u0004\u0018\u000106H\u0017J\u008a\u0001\u0010U\u001a\u00020V2\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\t2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\u001d\u001a\u00020\u001e2\u0006\u0010!\u001a\u00020\"2\u0006\u0010#\u001a\u00020$2\u0006\u0010\r\u001a\u00020W2\u0006\u0010T\u001a\u00020\u00102\u0006\u0010X\u001a\u00020\u00102\u0006\u0010Y\u001a\u00020\u00102\u0006\u0010Z\u001a\u00020\u00102\b\b\u0002\u0010(\u001a\u00020\u00102\n\b\u0002\u00105\u001a\u0004\u0018\u0001062\b\b\u0002\u0010*\u001a\u00020\u00102\b\b\u0002\u0010N\u001a\u00020\u0010H&Jh\u0010[\u001a\u00020V2\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\t2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\u001d\u001a\u00020\u001e2\u0006\u0010!\u001a\u00020\"2\u0006\u0010#\u001a\u00020$2\u0006\u0010T\u001a\u00020\u00102\u0006\u0010X\u001a\u00020\u00102\u0006\u0010Y\u001a\u00020\u00102\u0006\u0010Z\u001a\u00020\u00102\u0006\u0010(\u001a\u00020\u00102\u0006\u0010*\u001a\u00020\u0010H\u0017Jv\u0010[\u001a\u00020\\2\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\t2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\u001d\u001a\u00020\u001e2\u0006\u0010!\u001a\u00020\"2\u0006\u0010#\u001a\u00020$2\u0006\u0010T\u001a\u00020\u00102\u0006\u0010X\u001a\u00020\u00102\u0006\u0010Y\u001a\u00020\u00102\u0006\u0010Z\u001a\u00020\u00102\b\b\u0002\u0010(\u001a\u00020\u00102\b\b\u0002\u0010*\u001a\u00020\u00102\b\b\u0002\u0010N\u001a\u00020\u0010H&J\u0098\u0001\u0010]\u001a\u00020H2\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\t2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\u001d\u001a\u00020\u001e2\u0006\u0010!\u001a\u00020\"2\u0006\u00103\u001a\u00020\u00102\u0006\u0010*\u001a\u00020\u00102\u0006\u00101\u001a\u0002022\u0006\u0010#\u001a\u00020$2\u0006\u0010\r\u001a\u00020I2\u0006\u0010J\u001a\u00020\u00102\u0006\u0010K\u001a\u00020\u00102\u0006\u0010L\u001a\u00020\u00102\u0006\u0010M\u001a\u00020\u00102\b\b\u0002\u0010(\u001a\u00020\u00102\n\b\u0002\u00105\u001a\u0004\u0018\u0001062\b\b\u0002\u0010N\u001a\u00020\u0010H&JH\u0010^\u001a\u00020_2\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\t2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\u001d\u001a\u00020\u001e2\u0006\u0010!\u001a\u00020\"2\u0006\u0010\r\u001a\u00020`2\u0006\u0010a\u001a\u00020\u00102\u0006\u0010b\u001a\u000202H&JH\u0010^\u001a\u00020_2\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\t2\u0006\u0010\r\u001a\u00020`2\u0006\u0010\u001d\u001a\u00020\u001e2\u0006\u0010!\u001a\u00020\"2\u0006\u0010b\u001a\u0002022\u0006\u0010a\u001a\u00020\u00102\u0006\u0010\u000b\u001a\u00020\fH\u0017JH\u0010c\u001a\u00020d2\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\t2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020e2\u0006\u0010\u001d\u001a\u00020\u001e2\u0006\u0010f\u001a\u00020\t2\u0006\u0010g\u001a\u00020\u00102\u0006\u0010h\u001a\u00020iH\u0017JH\u0010c\u001a\u00020d2\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\t2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\u001d\u001a\u00020\u001e2\u0006\u0010\r\u001a\u00020e2\u0006\u0010h\u001a\u00020i2\u0006\u0010f\u001a\u00020\t2\u0006\u0010g\u001a\u00020\u0010H&Jj\u0010j\u001a\u00020k2\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\t2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020l2\u0006\u0010\u001d\u001a\u00020\u001e2\u0006\u0010f\u001a\u00020\t2\u0006\u0010E\u001a\u0002022\b\u0010m\u001a\u0004\u0018\u0001022\u0006\u0010n\u001a\u00020\u00102\u0006\u0010o\u001a\u00020\u00102\u0006\u0010p\u001a\u00020\u00102\u0006\u0010q\u001a\u00020\u0010H\u0017Jj\u0010j\u001a\u00020k2\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\t2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\u001d\u001a\u00020\u001e2\u0006\u0010E\u001a\u0002022\u0006\u0010q\u001a\u00020\u00102\u0006\u0010\r\u001a\u00020l2\u0006\u0010f\u001a\u00020\t2\b\u0010m\u001a\u0004\u0018\u0001022\u0006\u0010n\u001a\u00020\u00102\u0006\u0010o\u001a\u00020\u00102\u0006\u0010p\u001a\u00020\u0010H&R\u0012\u0010\u0002\u001a\u00020\u0003X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0004\u0010\u0005¨\u0006r"}, d2 = {"Lorg/jetbrains/kotlin/ir/declarations/IrFactory;", "", "stageController", "Lorg/jetbrains/kotlin/ir/declarations/StageController;", "getStageController", "()Lorg/jetbrains/kotlin/ir/declarations/StageController;", "createAnonymousInitializer", "Lorg/jetbrains/kotlin/ir/declarations/IrAnonymousInitializer;", "startOffset", "", "endOffset", "origin", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationOrigin;", NativeSymbol.TYPE_NAME, "Lorg/jetbrains/kotlin/ir/symbols/IrAnonymousInitializerSymbol;", "isStatic", "", "createBlockBody", "Lorg/jetbrains/kotlin/ir/expressions/IrBlockBody;", "statements", "", "Lorg/jetbrains/kotlin/ir/IrStatement;", "initializer", "Lkotlin/Function1;", "", "Lkotlin/ExtensionFunctionType;", "createClass", "Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "Lorg/jetbrains/kotlin/ir/symbols/IrClassSymbol;", "name", "Lorg/jetbrains/kotlin/name/Name;", Namer.METADATA_CLASS_KIND, "Lorg/jetbrains/kotlin/descriptors/ClassKind;", "visibility", "Lorg/jetbrains/kotlin/descriptors/DescriptorVisibility;", "modality", "Lorg/jetbrains/kotlin/descriptors/Modality;", "isCompanion", "isInner", "isData", "isExternal", "isValue", "isExpect", "isFun", FirebaseAnalytics.Param.SOURCE, "Lorg/jetbrains/kotlin/descriptors/SourceElement;", "createConstructor", "Lorg/jetbrains/kotlin/ir/declarations/IrConstructor;", "Lorg/jetbrains/kotlin/ir/symbols/IrConstructorSymbol;", "returnType", "Lorg/jetbrains/kotlin/ir/types/IrType;", "isInline", "isPrimary", "containerSource", "Lorg/jetbrains/kotlin/serialization/deserialization/descriptors/DeserializedContainerSource;", "createEnumEntry", "Lorg/jetbrains/kotlin/ir/declarations/IrEnumEntry;", "Lorg/jetbrains/kotlin/ir/symbols/IrEnumEntrySymbol;", "createErrorDeclaration", "Lorg/jetbrains/kotlin/ir/declarations/IrErrorDeclaration;", "descriptor", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "createExpressionBody", "Lorg/jetbrains/kotlin/ir/expressions/IrExpressionBody;", "expression", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "createField", "Lorg/jetbrains/kotlin/ir/declarations/IrField;", "Lorg/jetbrains/kotlin/ir/symbols/IrFieldSymbol;", ModuleXmlParser.TYPE, "isFinal", "createFunction", "Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;", "Lorg/jetbrains/kotlin/ir/symbols/IrSimpleFunctionSymbol;", "isTailrec", "isSuspend", "isOperator", "isInfix", "isFakeOverride", "createFunctionWithLateBinding", "Lorg/jetbrains/kotlin/ir/declarations/IrFunctionWithLateBinding;", "createLocalDelegatedProperty", "Lorg/jetbrains/kotlin/ir/declarations/IrLocalDelegatedProperty;", "Lorg/jetbrains/kotlin/ir/symbols/IrLocalDelegatedPropertySymbol;", "isVar", "createProperty", "Lorg/jetbrains/kotlin/ir/declarations/IrProperty;", "Lorg/jetbrains/kotlin/ir/symbols/IrPropertySymbol;", "isConst", "isLateinit", "isDelegated", "createPropertyWithLateBinding", "Lorg/jetbrains/kotlin/ir/declarations/IrPropertyWithLateBinding;", "createSimpleFunction", "createTypeAlias", "Lorg/jetbrains/kotlin/ir/declarations/IrTypeAlias;", "Lorg/jetbrains/kotlin/ir/symbols/IrTypeAliasSymbol;", "isActual", "expandedType", "createTypeParameter", "Lorg/jetbrains/kotlin/ir/declarations/IrTypeParameter;", "Lorg/jetbrains/kotlin/ir/symbols/IrTypeParameterSymbol;", "index", "isReified", "variance", "Lorg/jetbrains/kotlin/types/Variance;", "createValueParameter", "Lorg/jetbrains/kotlin/ir/declarations/IrValueParameter;", "Lorg/jetbrains/kotlin/ir/symbols/IrValueParameterSymbol;", "varargElementType", "isCrossinline", "isNoinline", "isHidden", "isAssignable", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface IrFactory {
    IrAnonymousInitializer createAnonymousInitializer(int startOffset, int endOffset, IrDeclarationOrigin origin, IrAnonymousInitializerSymbol symbol, boolean isStatic);

    IrBlockBody createBlockBody(int startOffset, int endOffset);

    @Deprecated(level = DeprecationLevel.HIDDEN, message = "This method was moved to an extension. This variant of the method will be removed when the 2024.2 IntelliJ platform is shipped (see KTIJ-26314).")
    /* synthetic */ IrBlockBody createBlockBody(int startOffset, int endOffset, List statements);

    @Deprecated(level = DeprecationLevel.HIDDEN, message = "This method was moved to an extension. This variant of the method will be removed when the 2024.2 IntelliJ platform is shipped (see KTIJ-26314).")
    /* synthetic */ IrBlockBody createBlockBody(int startOffset, int endOffset, Function1 initializer);

    @Deprecated(level = DeprecationLevel.HIDDEN, message = "The method's parameters were reordered. This variant of the method will be removed when the 2024.2 IntelliJ platform is shipped (see KTIJ-26314).")
    /* synthetic */ IrClass createClass(int startOffset, int endOffset, IrDeclarationOrigin origin, IrClassSymbol symbol, Name name2, ClassKind kind, DescriptorVisibility visibility, Modality modality, boolean isCompanion, boolean isInner, boolean isData, boolean isExternal, boolean isValue, boolean isExpect, boolean isFun, SourceElement source);

    IrClass createClass(int startOffset, int endOffset, IrDeclarationOrigin origin, Name name2, DescriptorVisibility visibility, IrClassSymbol symbol, ClassKind kind, Modality modality, boolean isExternal, boolean isCompanion, boolean isInner, boolean isData, boolean isValue, boolean isExpect, boolean isFun, SourceElement source);

    @Deprecated(level = DeprecationLevel.HIDDEN, message = "The method's parameters were reordered. This variant of the method will be removed when the 2024.2 IntelliJ platform is shipped (see KTIJ-26314).")
    /* synthetic */ IrConstructor createConstructor(int startOffset, int endOffset, IrDeclarationOrigin origin, IrConstructorSymbol symbol, Name name2, DescriptorVisibility visibility, IrType returnType, boolean isInline, boolean isExternal, boolean isPrimary, boolean isExpect, DeserializedContainerSource containerSource);

    IrConstructor createConstructor(int startOffset, int endOffset, IrDeclarationOrigin origin, Name name2, DescriptorVisibility visibility, boolean isInline, boolean isExpect, IrType returnType, IrConstructorSymbol symbol, boolean isPrimary, boolean isExternal, DeserializedContainerSource containerSource);

    @Deprecated(level = DeprecationLevel.HIDDEN, message = "The method's parameters were reordered. This variant of the method will be removed when the 2024.2 IntelliJ platform is shipped (see KTIJ-26314).")
    /* synthetic */ IrEnumEntry createEnumEntry(int startOffset, int endOffset, IrDeclarationOrigin origin, IrEnumEntrySymbol symbol, Name name2);

    IrEnumEntry createEnumEntry(int startOffset, int endOffset, IrDeclarationOrigin origin, Name name2, IrEnumEntrySymbol symbol);

    IrErrorDeclaration createErrorDeclaration(int startOffset, int endOffset, DeclarationDescriptor descriptor);

    IrExpressionBody createExpressionBody(int startOffset, int endOffset, IrExpression expression);

    @Deprecated(level = DeprecationLevel.HIDDEN, message = "This method was moved to an extension. This variant of the method will be removed when the 2024.2 IntelliJ platform is shipped (see KTIJ-26314).")
    /* synthetic */ IrExpressionBody createExpressionBody(IrExpression expression);

    @Deprecated(level = DeprecationLevel.HIDDEN, message = "The method's parameters were reordered. This variant of the method will be removed when the 2024.2 IntelliJ platform is shipped (see KTIJ-26314).")
    /* synthetic */ IrField createField(int startOffset, int endOffset, IrDeclarationOrigin origin, IrFieldSymbol symbol, Name name2, IrType type, DescriptorVisibility visibility, boolean isFinal, boolean isExternal, boolean isStatic);

    IrField createField(int startOffset, int endOffset, IrDeclarationOrigin origin, Name name2, DescriptorVisibility visibility, IrFieldSymbol symbol, IrType type, boolean isFinal, boolean isStatic, boolean isExternal);

    @Deprecated(level = DeprecationLevel.HIDDEN, message = "The method has been renamed, and its parameters were reordered. This variant of the method will be removed when the 2024.2 IntelliJ platform is shipped (see KTIJ-26314).")
    /* synthetic */ IrSimpleFunction createFunction(int startOffset, int endOffset, IrDeclarationOrigin origin, IrSimpleFunctionSymbol symbol, Name name2, DescriptorVisibility visibility, Modality modality, IrType returnType, boolean isInline, boolean isExternal, boolean isTailrec, boolean isSuspend, boolean isOperator, boolean isInfix, boolean isExpect, boolean isFakeOverride, DeserializedContainerSource containerSource);

    IrFunctionWithLateBinding createFunctionWithLateBinding(int startOffset, int endOffset, IrDeclarationOrigin origin, Name name2, DescriptorVisibility visibility, boolean isInline, boolean isExpect, IrType returnType, Modality modality, boolean isTailrec, boolean isSuspend, boolean isOperator, boolean isInfix, boolean isExternal, boolean isFakeOverride);

    @Deprecated(level = DeprecationLevel.HIDDEN, message = "The method's parameters were reordered. This variant of the method will be removed when the 2024.2 IntelliJ platform is shipped (see KTIJ-26314).")
    /* synthetic */ IrSimpleFunction createFunctionWithLateBinding(int startOffset, int endOffset, IrDeclarationOrigin origin, Name name2, DescriptorVisibility visibility, Modality modality, IrType returnType, boolean isInline, boolean isExternal, boolean isTailrec, boolean isSuspend, boolean isOperator, boolean isInfix, boolean isExpect);

    @Deprecated(level = DeprecationLevel.HIDDEN, message = "The method's parameters were reordered. This variant of the method will be removed when the 2024.2 IntelliJ platform is shipped (see KTIJ-26314).")
    /* synthetic */ IrLocalDelegatedProperty createLocalDelegatedProperty(int startOffset, int endOffset, IrDeclarationOrigin origin, IrLocalDelegatedPropertySymbol symbol, Name name2, IrType type, boolean isVar);

    IrLocalDelegatedProperty createLocalDelegatedProperty(int startOffset, int endOffset, IrDeclarationOrigin origin, Name name2, IrLocalDelegatedPropertySymbol symbol, IrType type, boolean isVar);

    @Deprecated(level = DeprecationLevel.HIDDEN, message = "The method's parameters were reordered. This variant of the method will be removed when the 2024.2 IntelliJ platform is shipped (see KTIJ-26314).")
    /* synthetic */ IrProperty createProperty(int startOffset, int endOffset, IrDeclarationOrigin origin, IrPropertySymbol symbol, Name name2, DescriptorVisibility visibility, Modality modality, boolean isVar, boolean isConst, boolean isLateinit, boolean isDelegated, boolean isExternal, boolean isExpect, boolean isFakeOverride, DeserializedContainerSource containerSource);

    IrProperty createProperty(int startOffset, int endOffset, IrDeclarationOrigin origin, Name name2, DescriptorVisibility visibility, Modality modality, IrPropertySymbol symbol, boolean isVar, boolean isConst, boolean isLateinit, boolean isDelegated, boolean isExternal, DeserializedContainerSource containerSource, boolean isExpect, boolean isFakeOverride);

    @Deprecated(level = DeprecationLevel.HIDDEN, message = "The method's parameters were reordered. This variant of the method will be removed when the 2024.2 IntelliJ platform is shipped (see KTIJ-26314).")
    /* synthetic */ IrProperty createPropertyWithLateBinding(int startOffset, int endOffset, IrDeclarationOrigin origin, Name name2, DescriptorVisibility visibility, Modality modality, boolean isVar, boolean isConst, boolean isLateinit, boolean isDelegated, boolean isExternal, boolean isExpect);

    IrPropertyWithLateBinding createPropertyWithLateBinding(int startOffset, int endOffset, IrDeclarationOrigin origin, Name name2, DescriptorVisibility visibility, Modality modality, boolean isVar, boolean isConst, boolean isLateinit, boolean isDelegated, boolean isExternal, boolean isExpect, boolean isFakeOverride);

    IrSimpleFunction createSimpleFunction(int startOffset, int endOffset, IrDeclarationOrigin origin, Name name2, DescriptorVisibility visibility, boolean isInline, boolean isExpect, IrType returnType, Modality modality, IrSimpleFunctionSymbol symbol, boolean isTailrec, boolean isSuspend, boolean isOperator, boolean isInfix, boolean isExternal, DeserializedContainerSource containerSource, boolean isFakeOverride);

    IrTypeAlias createTypeAlias(int startOffset, int endOffset, IrDeclarationOrigin origin, Name name2, DescriptorVisibility visibility, IrTypeAliasSymbol symbol, boolean isActual, IrType expandedType);

    @Deprecated(level = DeprecationLevel.HIDDEN, message = "The method's parameters were reordered. This variant of the method will be removed when the 2024.2 IntelliJ platform is shipped (see KTIJ-26314).")
    /* synthetic */ IrTypeAlias createTypeAlias(int startOffset, int endOffset, IrTypeAliasSymbol symbol, Name name2, DescriptorVisibility visibility, IrType expandedType, boolean isActual, IrDeclarationOrigin origin);

    @Deprecated(level = DeprecationLevel.HIDDEN, message = "The method's parameters were reordered. This variant of the method will be removed when the 2024.2 IntelliJ platform is shipped (see KTIJ-26314).")
    /* synthetic */ IrTypeParameter createTypeParameter(int startOffset, int endOffset, IrDeclarationOrigin origin, IrTypeParameterSymbol symbol, Name name2, int index, boolean isReified, Variance variance);

    IrTypeParameter createTypeParameter(int startOffset, int endOffset, IrDeclarationOrigin origin, Name name2, IrTypeParameterSymbol symbol, Variance variance, int index, boolean isReified);

    @Deprecated(level = DeprecationLevel.HIDDEN, message = "The method's parameters were reordered. This variant of the method will be removed when the 2024.2 IntelliJ platform is shipped (see KTIJ-26314).")
    /* synthetic */ IrValueParameter createValueParameter(int startOffset, int endOffset, IrDeclarationOrigin origin, IrValueParameterSymbol symbol, Name name2, int index, IrType type, IrType varargElementType, boolean isCrossinline, boolean isNoinline, boolean isHidden, boolean isAssignable);

    IrValueParameter createValueParameter(int startOffset, int endOffset, IrDeclarationOrigin origin, Name name2, IrType type, boolean isAssignable, IrValueParameterSymbol symbol, int index, IrType varargElementType, boolean isCrossinline, boolean isNoinline, boolean isHidden);

    StageController getStageController();

    /* JADX INFO: compiled from: IrFactory.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    public static final class DefaultImpls {
        public static /* synthetic */ IrAnonymousInitializer createAnonymousInitializer$default(IrFactory irFactory, int i, int i2, IrDeclarationOrigin irDeclarationOrigin, IrAnonymousInitializerSymbol irAnonymousInitializerSymbol, boolean z, int i3, Object obj) {
            if (obj != null) {
                throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: createAnonymousInitializer");
            }
            if ((i3 & 16) != 0) {
                z = false;
            }
            return irFactory.createAnonymousInitializer(i, i2, irDeclarationOrigin, irAnonymousInitializerSymbol, z);
        }

        public static /* synthetic */ IrClass createClass$default(IrFactory irFactory, int i, int i2, IrDeclarationOrigin irDeclarationOrigin, Name name2, DescriptorVisibility descriptorVisibility, IrClassSymbol irClassSymbol, ClassKind classKind, Modality modality, boolean z, boolean z2, boolean z3, boolean z4, boolean z5, boolean z6, boolean z7, SourceElement sourceElement, int i3, Object obj) {
            SourceElement sourceElement2;
            if (obj != null) {
                throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: createClass");
            }
            boolean z8 = (i3 & 256) != 0 ? false : z;
            boolean z9 = (i3 & 512) != 0 ? false : z2;
            boolean z10 = (i3 & 1024) != 0 ? false : z3;
            boolean z11 = (i3 & 2048) != 0 ? false : z4;
            boolean z12 = (i3 & 4096) != 0 ? false : z5;
            boolean z13 = (i3 & 8192) != 0 ? false : z6;
            boolean z14 = (i3 & 16384) != 0 ? false : z7;
            if ((i3 & 32768) != 0) {
                SourceElement NO_SOURCE = SourceElement.NO_SOURCE;
                Intrinsics.checkNotNullExpressionValue(NO_SOURCE, "NO_SOURCE");
                sourceElement2 = NO_SOURCE;
            } else {
                sourceElement2 = sourceElement;
            }
            return irFactory.createClass(i, i2, irDeclarationOrigin, name2, descriptorVisibility, irClassSymbol, classKind, modality, z8, z9, z10, z11, z12, z13, z14, sourceElement2);
        }

        public static /* synthetic */ IrConstructor createConstructor$default(IrFactory irFactory, int i, int i2, IrDeclarationOrigin irDeclarationOrigin, Name name2, DescriptorVisibility descriptorVisibility, boolean z, boolean z2, IrType irType, IrConstructorSymbol irConstructorSymbol, boolean z3, boolean z4, DeserializedContainerSource deserializedContainerSource, int i3, Object obj) {
            if (obj == null) {
                return irFactory.createConstructor(i, i2, irDeclarationOrigin, name2, descriptorVisibility, z, z2, irType, irConstructorSymbol, z3, (i3 & 1024) != 0 ? false : z4, (i3 & 2048) != 0 ? null : deserializedContainerSource);
            }
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: createConstructor");
        }

        public static /* synthetic */ IrErrorDeclaration createErrorDeclaration$default(IrFactory irFactory, int i, int i2, DeclarationDescriptor declarationDescriptor, int i3, Object obj) {
            if (obj != null) {
                throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: createErrorDeclaration");
            }
            if ((i3 & 4) != 0) {
                declarationDescriptor = null;
            }
            return irFactory.createErrorDeclaration(i, i2, declarationDescriptor);
        }

        public static /* synthetic */ IrField createField$default(IrFactory irFactory, int i, int i2, IrDeclarationOrigin irDeclarationOrigin, Name name2, DescriptorVisibility descriptorVisibility, IrFieldSymbol irFieldSymbol, IrType irType, boolean z, boolean z2, boolean z3, int i3, Object obj) {
            if (obj == null) {
                return irFactory.createField(i, i2, irDeclarationOrigin, name2, descriptorVisibility, irFieldSymbol, irType, z, z2, (i3 & 512) != 0 ? false : z3);
            }
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: createField");
        }

        public static /* synthetic */ IrFunctionWithLateBinding createFunctionWithLateBinding$default(IrFactory irFactory, int i, int i2, IrDeclarationOrigin irDeclarationOrigin, Name name2, DescriptorVisibility descriptorVisibility, boolean z, boolean z2, IrType irType, Modality modality, boolean z3, boolean z4, boolean z5, boolean z6, boolean z7, boolean z8, int i3, Object obj) {
            if (obj == null) {
                return irFactory.createFunctionWithLateBinding(i, i2, irDeclarationOrigin, name2, descriptorVisibility, z, z2, irType, modality, z3, z4, z5, z6, (i3 & 8192) != 0 ? false : z7, (i3 & 16384) != 0 ? Intrinsics.areEqual(irDeclarationOrigin, IrDeclarationOrigin.FAKE_OVERRIDE.INSTANCE) : z8);
            }
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: createFunctionWithLateBinding");
        }

        public static /* synthetic */ IrProperty createProperty$default(IrFactory irFactory, int i, int i2, IrDeclarationOrigin irDeclarationOrigin, Name name2, DescriptorVisibility descriptorVisibility, Modality modality, IrPropertySymbol irPropertySymbol, boolean z, boolean z2, boolean z3, boolean z4, boolean z5, DeserializedContainerSource deserializedContainerSource, boolean z6, boolean z7, int i3, Object obj) {
            if (obj == null) {
                return irFactory.createProperty(i, i2, irDeclarationOrigin, name2, descriptorVisibility, modality, irPropertySymbol, z, z2, z3, z4, (i3 & 2048) != 0 ? false : z5, (i3 & 4096) != 0 ? null : deserializedContainerSource, (i3 & 8192) != 0 ? false : z6, (i3 & 16384) != 0 ? Intrinsics.areEqual(irDeclarationOrigin, IrDeclarationOrigin.FAKE_OVERRIDE.INSTANCE) : z7);
            }
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: createProperty");
        }

        public static /* synthetic */ IrPropertyWithLateBinding createPropertyWithLateBinding$default(IrFactory irFactory, int i, int i2, IrDeclarationOrigin irDeclarationOrigin, Name name2, DescriptorVisibility descriptorVisibility, Modality modality, boolean z, boolean z2, boolean z3, boolean z4, boolean z5, boolean z6, boolean z7, int i3, Object obj) {
            if (obj == null) {
                return irFactory.createPropertyWithLateBinding(i, i2, irDeclarationOrigin, name2, descriptorVisibility, modality, z, z2, z3, z4, (i3 & 1024) != 0 ? false : z5, (i3 & 2048) != 0 ? false : z6, (i3 & 4096) != 0 ? Intrinsics.areEqual(irDeclarationOrigin, IrDeclarationOrigin.FAKE_OVERRIDE.INSTANCE) : z7);
            }
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: createPropertyWithLateBinding");
        }

        public static /* synthetic */ IrSimpleFunction createSimpleFunction$default(IrFactory irFactory, int i, int i2, IrDeclarationOrigin irDeclarationOrigin, Name name2, DescriptorVisibility descriptorVisibility, boolean z, boolean z2, IrType irType, Modality modality, IrSimpleFunctionSymbol irSimpleFunctionSymbol, boolean z3, boolean z4, boolean z5, boolean z6, boolean z7, DeserializedContainerSource deserializedContainerSource, boolean z8, int i3, Object obj) {
            if (obj == null) {
                return irFactory.createSimpleFunction(i, i2, irDeclarationOrigin, name2, descriptorVisibility, z, z2, irType, modality, irSimpleFunctionSymbol, z3, z4, z5, z6, (i3 & 16384) != 0 ? false : z7, (32768 & i3) != 0 ? null : deserializedContainerSource, (i3 & 65536) != 0 ? Intrinsics.areEqual(irDeclarationOrigin, IrDeclarationOrigin.FAKE_OVERRIDE.INSTANCE) : z8);
            }
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: createSimpleFunction");
        }

        @Deprecated(level = DeprecationLevel.HIDDEN, message = "This method was moved to an extension. This variant of the method will be removed when the 2024.2 IntelliJ platform is shipped (see KTIJ-26314).")
        public static /* synthetic */ IrBlockBody createBlockBody(IrFactory irFactory, int i, int i2, Function1 initializer) {
            Intrinsics.checkNotNullParameter(initializer, "initializer");
            return IrFactoryHelpersKt.createBlockBody(irFactory, i, i2, (Function1<? super IrBlockBody, Unit>) initializer);
        }

        @Deprecated(level = DeprecationLevel.HIDDEN, message = "This method was moved to an extension. This variant of the method will be removed when the 2024.2 IntelliJ platform is shipped (see KTIJ-26314).")
        public static /* synthetic */ IrBlockBody createBlockBody(IrFactory irFactory, int i, int i2, List statements) {
            Intrinsics.checkNotNullParameter(statements, "statements");
            return IrFactoryHelpersKt.createBlockBody(irFactory, i, i2, (List<? extends IrStatement>) statements);
        }

        public static /* synthetic */ IrClass createClass$default(IrFactory irFactory, int i, int i2, IrDeclarationOrigin irDeclarationOrigin, IrClassSymbol irClassSymbol, Name name2, ClassKind classKind, DescriptorVisibility descriptorVisibility, Modality modality, boolean z, boolean z2, boolean z3, boolean z4, boolean z5, boolean z6, boolean z7, SourceElement sourceElement, int i3, Object obj) {
            SourceElement sourceElement2;
            if (obj != null) {
                throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: createClass");
            }
            boolean z8 = (i3 & 256) != 0 ? false : z;
            boolean z9 = (i3 & 512) != 0 ? false : z2;
            boolean z10 = (i3 & 1024) != 0 ? false : z3;
            boolean z11 = (i3 & 2048) != 0 ? false : z4;
            boolean z12 = (i3 & 4096) != 0 ? false : z5;
            boolean z13 = (i3 & 8192) != 0 ? false : z6;
            boolean z14 = (i3 & 16384) != 0 ? false : z7;
            if ((i3 & 32768) != 0) {
                SourceElement NO_SOURCE = SourceElement.NO_SOURCE;
                Intrinsics.checkNotNullExpressionValue(NO_SOURCE, "NO_SOURCE");
                sourceElement2 = NO_SOURCE;
            } else {
                sourceElement2 = sourceElement;
            }
            return irFactory.createClass(i, i2, irDeclarationOrigin, irClassSymbol, name2, classKind, descriptorVisibility, modality, z8, z9, z10, z11, z12, z13, z14, sourceElement2);
        }

        @Deprecated(level = DeprecationLevel.HIDDEN, message = "The method's parameters were reordered. This variant of the method will be removed when the 2024.2 IntelliJ platform is shipped (see KTIJ-26314).")
        public static /* synthetic */ IrClass createClass(IrFactory irFactory, int i, int i2, IrDeclarationOrigin origin, IrClassSymbol symbol, Name name2, ClassKind kind, DescriptorVisibility visibility, Modality modality, boolean z, boolean z2, boolean z3, boolean z4, boolean z5, boolean z6, boolean z7, SourceElement source) {
            Intrinsics.checkNotNullParameter(origin, "origin");
            Intrinsics.checkNotNullParameter(symbol, "symbol");
            Intrinsics.checkNotNullParameter(name2, "name");
            Intrinsics.checkNotNullParameter(kind, "kind");
            Intrinsics.checkNotNullParameter(visibility, "visibility");
            Intrinsics.checkNotNullParameter(modality, "modality");
            Intrinsics.checkNotNullParameter(source, "source");
            return irFactory.createClass(i, i2, origin, name2, visibility, symbol, kind, modality, z4, z, z2, z3, z5, z6, z7, source);
        }

        public static /* synthetic */ IrConstructor createConstructor$default(IrFactory irFactory, int i, int i2, IrDeclarationOrigin irDeclarationOrigin, IrConstructorSymbol irConstructorSymbol, Name name2, DescriptorVisibility descriptorVisibility, IrType irType, boolean z, boolean z2, boolean z3, boolean z4, DeserializedContainerSource deserializedContainerSource, int i3, Object obj) {
            if (obj == null) {
                return irFactory.createConstructor(i, i2, irDeclarationOrigin, irConstructorSymbol, name2, descriptorVisibility, irType, z, z2, z3, z4, (i3 & 2048) != 0 ? null : deserializedContainerSource);
            }
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: createConstructor");
        }

        @Deprecated(level = DeprecationLevel.HIDDEN, message = "The method's parameters were reordered. This variant of the method will be removed when the 2024.2 IntelliJ platform is shipped (see KTIJ-26314).")
        public static /* synthetic */ IrConstructor createConstructor(IrFactory irFactory, int i, int i2, IrDeclarationOrigin origin, IrConstructorSymbol symbol, Name name2, DescriptorVisibility visibility, IrType returnType, boolean z, boolean z2, boolean z3, boolean z4, DeserializedContainerSource deserializedContainerSource) {
            Intrinsics.checkNotNullParameter(origin, "origin");
            Intrinsics.checkNotNullParameter(symbol, "symbol");
            Intrinsics.checkNotNullParameter(name2, "name");
            Intrinsics.checkNotNullParameter(visibility, "visibility");
            Intrinsics.checkNotNullParameter(returnType, "returnType");
            return irFactory.createConstructor(i, i2, origin, name2, visibility, z, z4, returnType, symbol, z3, z2, deserializedContainerSource);
        }

        @Deprecated(level = DeprecationLevel.HIDDEN, message = "The method's parameters were reordered. This variant of the method will be removed when the 2024.2 IntelliJ platform is shipped (see KTIJ-26314).")
        public static /* synthetic */ IrEnumEntry createEnumEntry(IrFactory irFactory, int i, int i2, IrDeclarationOrigin origin, IrEnumEntrySymbol symbol, Name name2) {
            Intrinsics.checkNotNullParameter(origin, "origin");
            Intrinsics.checkNotNullParameter(symbol, "symbol");
            Intrinsics.checkNotNullParameter(name2, "name");
            return irFactory.createEnumEntry(i, i2, origin, name2, symbol);
        }

        @Deprecated(level = DeprecationLevel.HIDDEN, message = "This method was moved to an extension. This variant of the method will be removed when the 2024.2 IntelliJ platform is shipped (see KTIJ-26314).")
        public static /* synthetic */ IrExpressionBody createExpressionBody(IrFactory irFactory, IrExpression expression) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            return IrFactoryHelpersKt.createExpressionBody(irFactory, expression);
        }

        @Deprecated(level = DeprecationLevel.HIDDEN, message = "The method's parameters were reordered. This variant of the method will be removed when the 2024.2 IntelliJ platform is shipped (see KTIJ-26314).")
        public static /* synthetic */ IrField createField(IrFactory irFactory, int i, int i2, IrDeclarationOrigin origin, IrFieldSymbol symbol, Name name2, IrType type, DescriptorVisibility visibility, boolean z, boolean z2, boolean z3) {
            Intrinsics.checkNotNullParameter(origin, "origin");
            Intrinsics.checkNotNullParameter(symbol, "symbol");
            Intrinsics.checkNotNullParameter(name2, "name");
            Intrinsics.checkNotNullParameter(type, "type");
            Intrinsics.checkNotNullParameter(visibility, "visibility");
            return irFactory.createField(i, i2, origin, name2, visibility, symbol, type, z, z3, z2);
        }

        @Deprecated(level = DeprecationLevel.HIDDEN, message = "The method's parameters were reordered. This variant of the method will be removed when the 2024.2 IntelliJ platform is shipped (see KTIJ-26314).")
        public static /* synthetic */ IrSimpleFunction createFunctionWithLateBinding(IrFactory irFactory, int i, int i2, IrDeclarationOrigin origin, Name name2, DescriptorVisibility visibility, Modality modality, IrType returnType, boolean z, boolean z2, boolean z3, boolean z4, boolean z5, boolean z6, boolean z7) {
            Intrinsics.checkNotNullParameter(origin, "origin");
            Intrinsics.checkNotNullParameter(name2, "name");
            Intrinsics.checkNotNullParameter(visibility, "visibility");
            Intrinsics.checkNotNullParameter(modality, "modality");
            Intrinsics.checkNotNullParameter(returnType, "returnType");
            return createFunctionWithLateBinding$default(irFactory, i, i2, origin, name2, visibility, z, z7, returnType, modality, z3, z4, z5, z6, z2, false, 16384, null);
        }

        @Deprecated(level = DeprecationLevel.HIDDEN, message = "The method's parameters were reordered. This variant of the method will be removed when the 2024.2 IntelliJ platform is shipped (see KTIJ-26314).")
        public static /* synthetic */ IrLocalDelegatedProperty createLocalDelegatedProperty(IrFactory irFactory, int i, int i2, IrDeclarationOrigin origin, IrLocalDelegatedPropertySymbol symbol, Name name2, IrType type, boolean z) {
            Intrinsics.checkNotNullParameter(origin, "origin");
            Intrinsics.checkNotNullParameter(symbol, "symbol");
            Intrinsics.checkNotNullParameter(name2, "name");
            Intrinsics.checkNotNullParameter(type, "type");
            return irFactory.createLocalDelegatedProperty(i, i2, origin, name2, symbol, type, z);
        }

        public static /* synthetic */ IrProperty createProperty$default(IrFactory irFactory, int i, int i2, IrDeclarationOrigin irDeclarationOrigin, IrPropertySymbol irPropertySymbol, Name name2, DescriptorVisibility descriptorVisibility, Modality modality, boolean z, boolean z2, boolean z3, boolean z4, boolean z5, boolean z6, boolean z7, DeserializedContainerSource deserializedContainerSource, int i3, Object obj) {
            if (obj == null) {
                return irFactory.createProperty(i, i2, irDeclarationOrigin, irPropertySymbol, name2, descriptorVisibility, modality, z, z2, z3, z4, z5, (i3 & 4096) != 0 ? false : z6, (i3 & 8192) != 0 ? Intrinsics.areEqual(irDeclarationOrigin, IrDeclarationOrigin.FAKE_OVERRIDE.INSTANCE) : z7, (i3 & 16384) != 0 ? null : deserializedContainerSource);
            }
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: createProperty");
        }

        @Deprecated(level = DeprecationLevel.HIDDEN, message = "The method's parameters were reordered. This variant of the method will be removed when the 2024.2 IntelliJ platform is shipped (see KTIJ-26314).")
        public static /* synthetic */ IrProperty createProperty(IrFactory irFactory, int i, int i2, IrDeclarationOrigin origin, IrPropertySymbol symbol, Name name2, DescriptorVisibility visibility, Modality modality, boolean z, boolean z2, boolean z3, boolean z4, boolean z5, boolean z6, boolean z7, DeserializedContainerSource deserializedContainerSource) {
            Intrinsics.checkNotNullParameter(origin, "origin");
            Intrinsics.checkNotNullParameter(symbol, "symbol");
            Intrinsics.checkNotNullParameter(name2, "name");
            Intrinsics.checkNotNullParameter(visibility, "visibility");
            Intrinsics.checkNotNullParameter(modality, "modality");
            return irFactory.createProperty(i, i2, origin, name2, visibility, modality, symbol, z, z2, z3, z4, z5, deserializedContainerSource, z6, z7);
        }

        @Deprecated(level = DeprecationLevel.HIDDEN, message = "The method's parameters were reordered. This variant of the method will be removed when the 2024.2 IntelliJ platform is shipped (see KTIJ-26314).")
        public static /* synthetic */ IrProperty createPropertyWithLateBinding(IrFactory irFactory, int i, int i2, IrDeclarationOrigin origin, Name name2, DescriptorVisibility visibility, Modality modality, boolean z, boolean z2, boolean z3, boolean z4, boolean z5, boolean z6) {
            Intrinsics.checkNotNullParameter(origin, "origin");
            Intrinsics.checkNotNullParameter(name2, "name");
            Intrinsics.checkNotNullParameter(visibility, "visibility");
            Intrinsics.checkNotNullParameter(modality, "modality");
            return createPropertyWithLateBinding$default(irFactory, i, i2, origin, name2, visibility, modality, z, z2, z3, z4, z5, z6, false, 4096, null);
        }

        public static /* synthetic */ IrSimpleFunction createFunction$default(IrFactory irFactory, int i, int i2, IrDeclarationOrigin irDeclarationOrigin, IrSimpleFunctionSymbol irSimpleFunctionSymbol, Name name2, DescriptorVisibility descriptorVisibility, Modality modality, IrType irType, boolean z, boolean z2, boolean z3, boolean z4, boolean z5, boolean z6, boolean z7, boolean z8, DeserializedContainerSource deserializedContainerSource, int i3, Object obj) {
            if (obj == null) {
                return irFactory.createFunction(i, i2, irDeclarationOrigin, irSimpleFunctionSymbol, name2, descriptorVisibility, modality, irType, z, z2, z3, z4, z5, z6, z7, (i3 & 32768) != 0 ? Intrinsics.areEqual(irDeclarationOrigin, IrDeclarationOrigin.FAKE_OVERRIDE.INSTANCE) : z8, (i3 & 65536) != 0 ? null : deserializedContainerSource);
            }
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: createFunction");
        }

        @Deprecated(level = DeprecationLevel.HIDDEN, message = "The method has been renamed, and its parameters were reordered. This variant of the method will be removed when the 2024.2 IntelliJ platform is shipped (see KTIJ-26314).")
        public static /* synthetic */ IrSimpleFunction createFunction(IrFactory irFactory, int i, int i2, IrDeclarationOrigin origin, IrSimpleFunctionSymbol symbol, Name name2, DescriptorVisibility visibility, Modality modality, IrType returnType, boolean z, boolean z2, boolean z3, boolean z4, boolean z5, boolean z6, boolean z7, boolean z8, DeserializedContainerSource deserializedContainerSource) {
            Intrinsics.checkNotNullParameter(origin, "origin");
            Intrinsics.checkNotNullParameter(symbol, "symbol");
            Intrinsics.checkNotNullParameter(name2, "name");
            Intrinsics.checkNotNullParameter(visibility, "visibility");
            Intrinsics.checkNotNullParameter(modality, "modality");
            Intrinsics.checkNotNullParameter(returnType, "returnType");
            return irFactory.createSimpleFunction(i, i2, origin, name2, visibility, z, z7, returnType, modality, symbol, z3, z4, z5, z6, z2, deserializedContainerSource, z8);
        }

        @Deprecated(level = DeprecationLevel.HIDDEN, message = "The method's parameters were reordered. This variant of the method will be removed when the 2024.2 IntelliJ platform is shipped (see KTIJ-26314).")
        public static /* synthetic */ IrTypeAlias createTypeAlias(IrFactory irFactory, int i, int i2, IrTypeAliasSymbol symbol, Name name2, DescriptorVisibility visibility, IrType expandedType, boolean z, IrDeclarationOrigin origin) {
            Intrinsics.checkNotNullParameter(symbol, "symbol");
            Intrinsics.checkNotNullParameter(name2, "name");
            Intrinsics.checkNotNullParameter(visibility, "visibility");
            Intrinsics.checkNotNullParameter(expandedType, "expandedType");
            Intrinsics.checkNotNullParameter(origin, "origin");
            return irFactory.createTypeAlias(i, i2, origin, name2, visibility, symbol, z, expandedType);
        }

        @Deprecated(level = DeprecationLevel.HIDDEN, message = "The method's parameters were reordered. This variant of the method will be removed when the 2024.2 IntelliJ platform is shipped (see KTIJ-26314).")
        public static /* synthetic */ IrTypeParameter createTypeParameter(IrFactory irFactory, int i, int i2, IrDeclarationOrigin origin, IrTypeParameterSymbol symbol, Name name2, int i3, boolean z, Variance variance) {
            Intrinsics.checkNotNullParameter(origin, "origin");
            Intrinsics.checkNotNullParameter(symbol, "symbol");
            Intrinsics.checkNotNullParameter(name2, "name");
            Intrinsics.checkNotNullParameter(variance, "variance");
            return irFactory.createTypeParameter(i, i2, origin, name2, symbol, variance, i3, z);
        }

        @Deprecated(level = DeprecationLevel.HIDDEN, message = "The method's parameters were reordered. This variant of the method will be removed when the 2024.2 IntelliJ platform is shipped (see KTIJ-26314).")
        public static /* synthetic */ IrValueParameter createValueParameter(IrFactory irFactory, int i, int i2, IrDeclarationOrigin origin, IrValueParameterSymbol symbol, Name name2, int i3, IrType type, IrType irType, boolean z, boolean z2, boolean z3, boolean z4) {
            Intrinsics.checkNotNullParameter(origin, "origin");
            Intrinsics.checkNotNullParameter(symbol, "symbol");
            Intrinsics.checkNotNullParameter(name2, "name");
            Intrinsics.checkNotNullParameter(type, "type");
            return irFactory.createValueParameter(i, i2, origin, name2, type, z4, symbol, i3, irType, z, z2, z3);
        }
    }
}
