package org.jetbrains.kotlin.cli.common.arguments;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import kotlin.DeprecationLevel;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Triple;
import kotlin.Unit;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.cli.common.messages.CompilerMessageSeverity;
import org.jetbrains.kotlin.cli.common.messages.MessageCollector;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.com.intellij.util.xmlb.annotations.Transient;
import org.jetbrains.kotlin.config.AnalysisFlag;
import org.jetbrains.kotlin.config.AnalysisFlags;
import org.jetbrains.kotlin.config.ApiVersion;
import org.jetbrains.kotlin.config.ExplicitApiMode;
import org.jetbrains.kotlin.config.LanguageFeature;
import org.jetbrains.kotlin.config.LanguageOrApiVersion;
import org.jetbrains.kotlin.config.LanguageVersion;
import org.jetbrains.kotlin.config.LanguageVersionSettings;
import org.jetbrains.kotlin.config.LanguageVersionSettingsImpl;
import org.jetbrains.kotlin.config.LanguageVersionSettingsKt;
import org.jetbrains.kotlin.utils.IDEAPlatforms;
import org.jetbrains.kotlin.utils.IDEAPluginsCompatibilityAPI;

/* JADX INFO: compiled from: CommonCompilerArguments.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000\u007f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u000b\n\u0002\u0010\u000e\n\u0002\b\u0015\n\u0002\u0010\u0011\n\u0003\bÐ\u0001\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010%\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010$\n\u0002\b\u0005\b&\u0018\u0000 \u009c\u00022\u00020\u0001:\u0006\u009c\u0002\u009d\u0002\u009e\u0002B\u0005¢\u0006\u0002\u0010\u0002J&\u0010ö\u0001\u001a\u00030÷\u00012\u0007\u0010y\u001a\u00030ø\u00012\u0007\u0010\u0011\u001a\u00030ù\u00012\b\u0010ú\u0001\u001a\u00030û\u0001H\u0002J\u001e\u0010ü\u0001\u001a\u00030÷\u00012\b\u0010ý\u0001\u001a\u00030þ\u00012\b\u0010ú\u0001\u001a\u00030û\u0001H\u0014J\u001b\u0010ÿ\u0001\u001a\u00030÷\u00012\u0007\u0010y\u001a\u00030ø\u00012\b\u0010ú\u0001\u001a\u00030û\u0001J(\u0010\u0080\u0002\u001a\u00030÷\u00012\b\u0010\u0081\u0002\u001a\u00030ø\u00012\b\u0010\u0082\u0002\u001a\u00030ù\u00012\b\u0010ú\u0001\u001a\u00030û\u0001H\u0002J\u001e\u0010\u0083\u0002\u001a\u00030÷\u00012\b\u0010ý\u0001\u001a\u00030þ\u00012\b\u0010ú\u0001\u001a\u00030û\u0001H\u0014J\u001d\u0010\u0084\u0002\u001a\u00030÷\u00012\u0007\u0010y\u001a\u00030ø\u00012\b\u0010ú\u0001\u001a\u00030û\u0001H\u0002J/\u0010\u0085\u0002\u001a\u0015\u0012\t\u0012\u0007\u0012\u0002\b\u00030\u0087\u0002\u0012\u0005\u0012\u00030\u0088\u00020\u0086\u00022\b\u0010ú\u0001\u001a\u00030û\u00012\u0007\u0010y\u001a\u00030ø\u0001H\u0016J5\u0010\u0089\u0002\u001a\u00030÷\u00012)\u0010\u008a\u0002\u001a$\u0012\u0005\u0012\u00030\u008c\u0002\u0012\u0005\u0012\u00030\u008d\u00020\u008b\u0002j\u0011\u0012\u0005\u0012\u00030\u008c\u0002\u0012\u0005\u0012\u00030\u008d\u0002`\u008e\u0002H\u0014J\"\u0010\u008f\u0002\u001a\u0011\u0012\u0005\u0012\u00030\u008c\u0002\u0012\u0005\u0012\u00030\u008d\u00020\u0086\u00022\b\u0010ú\u0001\u001a\u00030û\u0001H\u0016J\u0014\u0010\u0090\u0002\u001a\u00030ø\u00012\b\u0010ú\u0001\u001a\u00030û\u0001H\u0014J7\u0010\u0091\u0002\u001a\u001c\u0012\u0005\u0012\u00030\u0093\u0002\u0012\u0007\u0012\u0005\u0018\u00010\u0093\u0002\u0012\u0005\u0012\u00030\u0094\u0002\u0018\u00010\u0092\u00022\b\u0010\u0081\u0002\u001a\u00030ø\u00012\b\u0010\u0082\u0002\u001a\u00030ù\u0001H\u0002J\u0014\u0010\u0095\u0002\u001a\u00030ø\u00012\b\u0010ú\u0001\u001a\u00030û\u0001H\u0002J)\u0010\u0096\u0002\u001a\u0005\u0018\u00010ø\u00012\b\u0010ú\u0001\u001a\u00030û\u00012\b\u0010\u0003\u001a\u0004\u0018\u00010\u00102\u0007\u0010\u0097\u0002\u001a\u00020\u0010H\u0002J\u0012\u0010\u0098\u0002\u001a\u00030þ\u00012\b\u0010ú\u0001\u001a\u00030û\u0001J.\u0010\u0098\u0002\u001a\u00030þ\u00012\b\u0010ú\u0001\u001a\u00030û\u00012\u001a\u0010\u0099\u0002\u001a\u0015\u0012\t\u0012\u0007\u0012\u0002\b\u00030\u0087\u0002\u0012\u0005\u0012\u00030\u0088\u00020\u009a\u0002J:\u0010\u009b\u0002\u001a\u00030÷\u0001*$\u0012\u0005\u0012\u00030\u008c\u0002\u0012\u0005\u0012\u00030\u008d\u00020\u008b\u0002j\u0011\u0012\u0005\u0012\u00030\u008c\u0002\u0012\u0005\u0012\u00030\u008d\u0002`\u008e\u00022\b\u0010ú\u0001\u001a\u00030û\u0001H\u0002R&\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u00048\u0006@FX\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0006\u0010\u0007\"\u0004\b\b\u0010\tR&\u0010\n\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u00048\u0006@FX\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\u0007\"\u0004\b\f\u0010\tR&\u0010\r\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u00048\u0006@FX\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000e\u0010\u0007\"\u0004\b\u000f\u0010\tR0\u0010\u0011\u001a\u0004\u0018\u00010\u00102\b\u0010\u0003\u001a\u0004\u0018\u00010\u00108\u0006@FX\u0087\u000e¢\u0006\u0014\n\u0000\u0012\u0004\b\u0012\u0010\u0002\u001a\u0004\b\u0013\u0010\u0014\"\u0004\b\u0015\u0010\u0016R&\u0010\u0017\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u00048G@FX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0018\u0010\u0007\"\u0004\b\u0019\u0010\tR&\u0010\u001a\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u00048G@FX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001b\u0010\u0007\"\u0004\b\u001c\u0010\tR&\u0010\u001d\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u00048\u0006@FX\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001e\u0010\u0007\"\u0004\b\u001f\u0010\tR&\u0010 \u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u00048\u0006@FX\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b!\u0010\u0007\"\u0004\b\"\u0010\tR&\u0010#\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u00048\u0006@FX\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b$\u0010\u0007\"\u0004\b%\u0010\tR8\u0010'\u001a\n\u0012\u0004\u0012\u00020\u0010\u0018\u00010&2\u000e\u0010\u0003\u001a\n\u0012\u0004\u0012\u00020\u0010\u0018\u00010&8\u0006@FX\u0087\u000e¢\u0006\u0010\n\u0002\u0010,\u001a\u0004\b(\u0010)\"\u0004\b*\u0010+R&\u0010-\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u00048\u0006@FX\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b.\u0010\u0007\"\u0004\b/\u0010\tR&\u00100\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u00048\u0006@FX\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b1\u0010\u0007\"\u0004\b2\u0010\tR8\u00103\u001a\n\u0012\u0004\u0012\u00020\u0010\u0018\u00010&2\u000e\u0010\u0003\u001a\n\u0012\u0004\u0012\u00020\u0010\u0018\u00010&8\u0006@FX\u0087\u000e¢\u0006\u0010\n\u0002\u0010,\u001a\u0004\b4\u0010)\"\u0004\b5\u0010+R&\u00106\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u00048\u0006@FX\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b7\u0010\u0007\"\u0004\b8\u0010\tR*\u00109\u001a\u0004\u0018\u00010\u00102\b\u0010\u0003\u001a\u0004\u0018\u00010\u00108\u0006@FX\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b:\u0010\u0014\"\u0004\b;\u0010\u0016R*\u0010<\u001a\u0004\u0018\u00010\u00102\b\u0010\u0003\u001a\u0004\u0018\u00010\u00108\u0006@FX\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b=\u0010\u0014\"\u0004\b>\u0010\u0016R*\u0010?\u001a\u0004\u0018\u00010\u00102\b\u0010\u0003\u001a\u0004\u0018\u00010\u00108\u0006@FX\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b@\u0010\u0014\"\u0004\bA\u0010\u0016R&\u0010B\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u00048\u0006@FX\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bC\u0010\u0007\"\u0004\bD\u0010\tR&\u0010E\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u00048\u0006@FX\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bF\u0010\u0007\"\u0004\bG\u0010\tR&\u0010H\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u00048\u0006@FX\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bI\u0010\u0007\"\u0004\bJ\u0010\tR&\u0010K\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u00048\u0006@FX\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bL\u0010\u0007\"\u0004\bM\u0010\tR&\u0010N\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u00048\u0006@FX\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bO\u0010\u0007\"\u0004\bP\u0010\tR.\u0010Q\u001a\n\u0012\u0004\u0012\u00020\u0010\u0018\u00010&8\u0006@\u0006X\u0087\u000e¢\u0006\u0016\n\u0002\u0010,\u0012\u0004\bR\u0010\u0002\u001a\u0004\bS\u0010)\"\u0004\bT\u0010+R&\u0010U\u001a\u00020\u00102\u0006\u0010\u0003\u001a\u00020\u00108\u0006@FX\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bV\u0010\u0014\"\u0004\bW\u0010\u0016R&\u0010X\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u00048\u0006@FX\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bY\u0010\u0007\"\u0004\bZ\u0010\tR8\u0010[\u001a\n\u0012\u0004\u0012\u00020\u0010\u0018\u00010&2\u000e\u0010\u0003\u001a\n\u0012\u0004\u0012\u00020\u0010\u0018\u00010&8\u0006@FX\u0087\u000e¢\u0006\u0010\n\u0002\u0010,\u001a\u0004\b\\\u0010)\"\u0004\b]\u0010+R8\u0010^\u001a\n\u0012\u0004\u0012\u00020\u0010\u0018\u00010&2\u000e\u0010\u0003\u001a\n\u0012\u0004\u0012\u00020\u0010\u0018\u00010&8\u0006@FX\u0087\u000e¢\u0006\u0010\n\u0002\u0010,\u001a\u0004\b_\u0010)\"\u0004\b`\u0010+R(\u0010a\u001a\n\u0012\u0004\u0012\u00020\u0010\u0018\u00010&8\u0006@\u0006X\u0087\u000e¢\u0006\u0010\n\u0002\u0010,\u001a\u0004\bb\u0010)\"\u0004\bc\u0010+R&\u0010d\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u00048\u0006@FX\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\be\u0010\u0007\"\u0004\bf\u0010\tR,\u0010g\u001a\u0004\u0018\u00010\u00042\b\u0010\u0003\u001a\u0004\u0018\u00010\u00048\u0006@FX\u0087\u000e¢\u0006\u0010\n\u0002\u0010l\u001a\u0004\bh\u0010i\"\u0004\bj\u0010kR&\u0010m\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u00048\u0006@FX\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bn\u0010\u0007\"\u0004\bo\u0010\tR&\u0010p\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u00048\u0006@FX\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bq\u0010\u0007\"\u0004\br\u0010\tR*\u0010s\u001a\u0004\u0018\u00010\u00102\b\u0010\u0003\u001a\u0004\u0018\u00010\u00108\u0006@FX\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bt\u0010\u0014\"\u0004\bu\u0010\u0016R*\u0010v\u001a\u0004\u0018\u00010\u00102\b\u0010\u0003\u001a\u0004\u0018\u00010\u00108\u0006@FX\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bw\u0010\u0014\"\u0004\bx\u0010\u0016R0\u0010y\u001a\u0004\u0018\u00010\u00102\b\u0010\u0003\u001a\u0004\u0018\u00010\u00108\u0006@FX\u0087\u000e¢\u0006\u0014\n\u0000\u0012\u0004\bz\u0010\u0002\u001a\u0004\b{\u0010\u0014\"\u0004\b|\u0010\u0016R&\u0010}\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u00048\u0006@FX\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b~\u0010\u0007\"\u0004\b\u007f\u0010\tR)\u0010\u0080\u0001\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u00048\u0006@FX\u0087\u000e¢\u0006\u0010\n\u0000\u001a\u0005\b\u0081\u0001\u0010\u0007\"\u0005\b\u0082\u0001\u0010\tR-\u0010\u0083\u0001\u001a\u0004\u0018\u00010\u00102\b\u0010\u0003\u001a\u0004\u0018\u00010\u00108\u0006@FX\u0087\u000e¢\u0006\u0010\n\u0000\u001a\u0005\b\u0084\u0001\u0010\u0014\"\u0005\b\u0085\u0001\u0010\u0016R)\u0010\u0086\u0001\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u00048\u0006@FX\u0087\u000e¢\u0006\u0010\n\u0000\u001a\u0005\b\u0087\u0001\u0010\u0007\"\u0005\b\u0088\u0001\u0010\tR)\u0010\u0089\u0001\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u00048\u0006@FX\u0087\u000e¢\u0006\u0010\n\u0000\u001a\u0005\b\u008a\u0001\u0010\u0007\"\u0005\b\u008b\u0001\u0010\tR)\u0010\u008c\u0001\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u00048\u0006@FX\u0087\u000e¢\u0006\u0010\n\u0000\u001a\u0005\b\u008d\u0001\u0010\u0007\"\u0005\b\u008e\u0001\u0010\tR)\u0010\u008f\u0001\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u00048\u0006@FX\u0087\u000e¢\u0006\u0010\n\u0000\u001a\u0005\b\u0090\u0001\u0010\u0007\"\u0005\b\u0091\u0001\u0010\tR)\u0010\u0092\u0001\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u00048\u0006@FX\u0087\u000e¢\u0006\u0010\n\u0000\u001a\u0005\b\u0093\u0001\u0010\u0007\"\u0005\b\u0094\u0001\u0010\tRB\u0010\u0095\u0001\u001a\n\u0012\u0004\u0012\u00020\u0010\u0018\u00010&2\u000e\u0010\u0003\u001a\n\u0012\u0004\u0012\u00020\u0010\u0018\u00010&8\u0006@FX\u0087\u000e¢\u0006\u0019\n\u0002\u0010,\u0012\u0005\b\u0096\u0001\u0010\u0002\u001a\u0005\b\u0097\u0001\u0010)\"\u0005\b\u0098\u0001\u0010+R;\u0010\u0099\u0001\u001a\n\u0012\u0004\u0012\u00020\u0010\u0018\u00010&2\u000e\u0010\u0003\u001a\n\u0012\u0004\u0012\u00020\u0010\u0018\u00010&8\u0006@FX\u0087\u000e¢\u0006\u0012\n\u0002\u0010,\u001a\u0005\b\u009a\u0001\u0010)\"\u0005\b\u009b\u0001\u0010+R;\u0010\u009c\u0001\u001a\n\u0012\u0004\u0012\u00020\u0010\u0018\u00010&2\u000e\u0010\u0003\u001a\n\u0012\u0004\u0012\u00020\u0010\u0018\u00010&8\u0006@FX\u0087\u000e¢\u0006\u0012\n\u0002\u0010,\u001a\u0005\b\u009d\u0001\u0010)\"\u0005\b\u009e\u0001\u0010+R;\u0010\u009f\u0001\u001a\n\u0012\u0004\u0012\u00020\u0010\u0018\u00010&2\u000e\u0010\u0003\u001a\n\u0012\u0004\u0012\u00020\u0010\u0018\u00010&8\u0006@FX\u0087\u000e¢\u0006\u0012\n\u0002\u0010,\u001a\u0005\b \u0001\u0010)\"\u0005\b¡\u0001\u0010+R;\u0010¢\u0001\u001a\n\u0012\u0004\u0012\u00020\u0010\u0018\u00010&2\u000e\u0010\u0003\u001a\n\u0012\u0004\u0012\u00020\u0010\u0018\u00010&8\u0006@FX\u0087\u000e¢\u0006\u0012\n\u0002\u0010,\u001a\u0005\b£\u0001\u0010)\"\u0005\b¤\u0001\u0010+R;\u0010¥\u0001\u001a\n\u0012\u0004\u0012\u00020\u0010\u0018\u00010&2\u000e\u0010\u0003\u001a\n\u0012\u0004\u0012\u00020\u0010\u0018\u00010&8\u0006@FX\u0087\u000e¢\u0006\u0012\n\u0002\u0010,\u001a\u0005\b¦\u0001\u0010)\"\u0005\b§\u0001\u0010+R;\u0010¨\u0001\u001a\n\u0012\u0004\u0012\u00020\u0010\u0018\u00010&2\u000e\u0010\u0003\u001a\n\u0012\u0004\u0012\u00020\u0010\u0018\u00010&8\u0006@FX\u0087\u000e¢\u0006\u0012\n\u0002\u0010,\u001a\u0005\b©\u0001\u0010)\"\u0005\bª\u0001\u0010+R;\u0010«\u0001\u001a\n\u0012\u0004\u0012\u00020\u0010\u0018\u00010&2\u000e\u0010\u0003\u001a\n\u0012\u0004\u0012\u00020\u0010\u0018\u00010&8\u0006@FX\u0087\u000e¢\u0006\u0012\n\u0002\u0010,\u001a\u0005\b¬\u0001\u0010)\"\u0005\b\u00ad\u0001\u0010+R;\u0010®\u0001\u001a\n\u0012\u0004\u0012\u00020\u0010\u0018\u00010&2\u000e\u0010\u0003\u001a\n\u0012\u0004\u0012\u00020\u0010\u0018\u00010&8\u0006@FX\u0087\u000e¢\u0006\u0012\n\u0002\u0010,\u001a\u0005\b¯\u0001\u0010)\"\u0005\b°\u0001\u0010+R;\u0010±\u0001\u001a\n\u0012\u0004\u0012\u00020\u0010\u0018\u00010&2\u000e\u0010\u0003\u001a\n\u0012\u0004\u0012\u00020\u0010\u0018\u00010&8\u0006@FX\u0087\u000e¢\u0006\u0012\n\u0002\u0010,\u001a\u0005\b²\u0001\u0010)\"\u0005\b³\u0001\u0010+R)\u0010´\u0001\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u00048\u0006@FX\u0087\u000e¢\u0006\u0010\n\u0000\u001a\u0005\bµ\u0001\u0010\u0007\"\u0005\b¶\u0001\u0010\tR0\u0010·\u0001\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u00048\u0006@FX\u0087\u000e¢\u0006\u0017\n\u0000\u0012\u0005\b¸\u0001\u0010\u0002\u001a\u0005\b¹\u0001\u0010\u0007\"\u0005\bº\u0001\u0010\tR)\u0010»\u0001\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u00048\u0006@FX\u0087\u000e¢\u0006\u0010\n\u0000\u001a\u0005\b¼\u0001\u0010\u0007\"\u0005\b½\u0001\u0010\tR)\u0010¾\u0001\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u00048\u0006@FX\u0087\u000e¢\u0006\u0010\n\u0000\u001a\u0005\b¿\u0001\u0010\u0007\"\u0005\bÀ\u0001\u0010\tR;\u0010Á\u0001\u001a\n\u0012\u0004\u0012\u00020\u0010\u0018\u00010&2\u000e\u0010\u0003\u001a\n\u0012\u0004\u0012\u00020\u0010\u0018\u00010&8\u0006@FX\u0087\u000e¢\u0006\u0012\n\u0002\u0010,\u001a\u0005\bÂ\u0001\u0010)\"\u0005\bÃ\u0001\u0010+R)\u0010Ä\u0001\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u00048\u0006@FX\u0087\u000e¢\u0006\u0010\n\u0000\u001a\u0005\bÅ\u0001\u0010\u0007\"\u0005\bÆ\u0001\u0010\tR)\u0010Ç\u0001\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u00048\u0006@FX\u0087\u000e¢\u0006\u0010\n\u0000\u001a\u0005\bÈ\u0001\u0010\u0007\"\u0005\bÉ\u0001\u0010\tR)\u0010Ê\u0001\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u00048\u0006@FX\u0087\u000e¢\u0006\u0010\n\u0000\u001a\u0005\bË\u0001\u0010\u0007\"\u0005\bÌ\u0001\u0010\tR)\u0010Í\u0001\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u00048\u0006@FX\u0087\u000e¢\u0006\u0010\n\u0000\u001a\u0005\bÎ\u0001\u0010\u0007\"\u0005\bÏ\u0001\u0010\tR)\u0010Ð\u0001\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u00048\u0006@FX\u0087\u000e¢\u0006\u0010\n\u0000\u001a\u0005\bÑ\u0001\u0010\u0007\"\u0005\bÒ\u0001\u0010\tR)\u0010Ó\u0001\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u00048\u0006@FX\u0087\u000e¢\u0006\u0010\n\u0000\u001a\u0005\bÔ\u0001\u0010\u0007\"\u0005\bÕ\u0001\u0010\tR)\u0010Ö\u0001\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u00048\u0006@FX\u0087\u000e¢\u0006\u0010\n\u0000\u001a\u0005\b×\u0001\u0010\u0007\"\u0005\bØ\u0001\u0010\tR)\u0010Ù\u0001\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u00048\u0006@FX\u0087\u000e¢\u0006\u0010\n\u0000\u001a\u0005\bÚ\u0001\u0010\u0007\"\u0005\bÛ\u0001\u0010\tR)\u0010Ü\u0001\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u00048\u0006@FX\u0087\u000e¢\u0006\u0010\n\u0000\u001a\u0005\bÝ\u0001\u0010\u0007\"\u0005\bÞ\u0001\u0010\tRB\u0010ß\u0001\u001a\n\u0012\u0004\u0012\u00020\u0010\u0018\u00010&2\u000e\u0010\u0003\u001a\n\u0012\u0004\u0012\u00020\u0010\u0018\u00010&8\u0006@FX\u0087\u000e¢\u0006\u0019\n\u0002\u0010,\u0012\u0005\bà\u0001\u0010\u0002\u001a\u0005\bá\u0001\u0010)\"\u0005\bâ\u0001\u0010+R)\u0010ã\u0001\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u00048\u0006@FX\u0087\u000e¢\u0006\u0010\n\u0000\u001a\u0005\bä\u0001\u0010\u0007\"\u0005\bå\u0001\u0010\tR)\u0010æ\u0001\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u00048\u0006@FX\u0087\u000e¢\u0006\u0010\n\u0000\u001a\u0005\bç\u0001\u0010\u0007\"\u0005\bè\u0001\u0010\tR)\u0010é\u0001\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u00048\u0006@FX\u0087\u000e¢\u0006\u0010\n\u0000\u001a\u0005\bê\u0001\u0010\u0007\"\u0005\bë\u0001\u0010\tR0\u0010ì\u0001\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u00048\u0006@FX\u0087\u000e¢\u0006\u0017\n\u0000\u0012\u0005\bí\u0001\u0010\u0002\u001a\u0005\bî\u0001\u0010\u0007\"\u0005\bï\u0001\u0010\tR)\u0010ð\u0001\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u00048\u0006@FX\u0087\u000e¢\u0006\u0010\n\u0000\u001a\u0005\bñ\u0001\u0010\u0007\"\u0005\bò\u0001\u0010\tR;\u0010ó\u0001\u001a\n\u0012\u0004\u0012\u00020\u0010\u0018\u00010&2\u000e\u0010\u0003\u001a\n\u0012\u0004\u0012\u00020\u0010\u0018\u00010&8\u0006@FX\u0087\u000e¢\u0006\u0012\n\u0002\u0010,\u001a\u0005\bô\u0001\u0010)\"\u0005\bõ\u0001\u0010+¨\u0006\u009f\u0002"}, d2 = {"Lorg/jetbrains/kotlin/cli/common/arguments/CommonCompilerArguments;", "Lorg/jetbrains/kotlin/cli/common/arguments/CommonToolArguments;", "()V", "value", "", "allowAnyScriptsInSourceRoots", "getAllowAnyScriptsInSourceRoots", "()Z", "setAllowAnyScriptsInSourceRoots", "(Z)V", "allowKotlinPackage", "getAllowKotlinPackage", "setAllowKotlinPackage", "allowResultReturnType", "getAllowResultReturnType", "setAllowResultReturnType", "", "apiVersion", "getApiVersion$annotations", "getApiVersion", "()Ljava/lang/String;", "setApiVersion", "(Ljava/lang/String;)V", "autoAdvanceApiVersion", "getAutoAdvanceApiVersion", "setAutoAdvanceApiVersion", "autoAdvanceLanguageVersion", "getAutoAdvanceLanguageVersion", "setAutoAdvanceLanguageVersion", "builtInsFromSources", "getBuiltInsFromSources", "setBuiltInsFromSources", "checkPhaseConditions", "getCheckPhaseConditions", "setCheckPhaseConditions", "checkStickyPhaseConditions", "getCheckStickyPhaseConditions", "setCheckStickyPhaseConditions", "", ModuleXmlParser.COMMON_SOURCES, "getCommonSources", "()[Ljava/lang/String;", "setCommonSources", "([Ljava/lang/String;)V", "[Ljava/lang/String;", "contextReceivers", "getContextReceivers", "setContextReceivers", "disableDefaultScriptingPlugin", "getDisableDefaultScriptingPlugin", "setDisableDefaultScriptingPlugin", "disablePhases", "getDisablePhases", "setDisablePhases", "disableUltraLightClasses", "getDisableUltraLightClasses", "setDisableUltraLightClasses", "dumpDirectory", "getDumpDirectory", "setDumpDirectory", "dumpOnlyFqName", "getDumpOnlyFqName", "setDumpOnlyFqName", "dumpPerf", "getDumpPerf", "setDumpPerf", "effectSystem", "getEffectSystem", "setEffectSystem", "enableBuilderInference", "getEnableBuilderInference", "setEnableBuilderInference", "enableSignatureClashChecks", "getEnableSignatureClashChecks", "setEnableSignatureClashChecks", "expectActualClasses", "getExpectActualClasses", "setExpectActualClasses", "expectActualLinker", "getExpectActualLinker", "setExpectActualLinker", "experimental", "getExperimental$annotations", "getExperimental", "setExperimental", "explicitApi", "getExplicitApi", "setExplicitApi", "extendedCompilerChecks", "getExtendedCompilerChecks", "setExtendedCompilerChecks", "fragmentRefines", "getFragmentRefines", "setFragmentRefines", "fragmentSources", "getFragmentSources", "setFragmentSources", "fragments", "getFragments", "setFragments", "ignoreConstOptimizationErrors", "getIgnoreConstOptimizationErrors", "setIgnoreConstOptimizationErrors", "incrementalCompilation", "getIncrementalCompilation", "()Ljava/lang/Boolean;", "setIncrementalCompilation", "(Ljava/lang/Boolean;)V", "Ljava/lang/Boolean;", "inferenceCompatibility", "getInferenceCompatibility", "setInferenceCompatibility", "inlineClasses", "getInlineClasses", "setInlineClasses", "intellijPluginRoot", "getIntellijPluginRoot", "setIntellijPluginRoot", "kotlinHome", "getKotlinHome", "setKotlinHome", "languageVersion", "getLanguageVersion$annotations", "getLanguageVersion", "setLanguageVersion", "legacySmartCastAfterTry", "getLegacySmartCastAfterTry", "setLegacySmartCastAfterTry", "listPhases", "getListPhases", "setListPhases", "metadataVersion", "getMetadataVersion", "setMetadataVersion", "multiPlatform", "getMultiPlatform", "setMultiPlatform", "newInference", "getNewInference", "setNewInference", "noCheckActual", "getNoCheckActual", "setNoCheckActual", "noInline", "getNoInline", "setNoInline", "normalizeAbsolutePath", "getNormalizeAbsolutePath", "setNormalizeAbsolutePath", "optIn", "getOptIn$annotations", "getOptIn", "setOptIn", "phasesToDump", "getPhasesToDump", "setPhasesToDump", "phasesToDumpAfter", "getPhasesToDumpAfter", "setPhasesToDumpAfter", "phasesToDumpBefore", "getPhasesToDumpBefore", "setPhasesToDumpBefore", "phasesToValidate", "getPhasesToValidate", "setPhasesToValidate", "phasesToValidateAfter", "getPhasesToValidateAfter", "setPhasesToValidateAfter", "phasesToValidateBefore", "getPhasesToValidateBefore", "setPhasesToValidateBefore", "pluginClasspaths", "getPluginClasspaths", "setPluginClasspaths", "pluginConfigurations", "getPluginConfigurations", "setPluginConfigurations", "pluginOptions", "getPluginOptions", "setPluginOptions", "profilePhases", "getProfilePhases", "setProfilePhases", "progressiveMode", "getProgressiveMode$annotations", "getProgressiveMode", "setProgressiveMode", "properIeee754Comparisons", "getProperIeee754Comparisons", "setProperIeee754Comparisons", "readDeserializedContracts", "getReadDeserializedContracts", "setReadDeserializedContracts", "relativePathBases", "getRelativePathBases", "setRelativePathBases", "renderInternalDiagnosticNames", "getRenderInternalDiagnosticNames", "setRenderInternalDiagnosticNames", "reportOutputFiles", "getReportOutputFiles", "setReportOutputFiles", "reportPerf", "getReportPerf", "setReportPerf", "script", "getScript", "setScript", "selfUpperBoundInference", "getSelfUpperBoundInference", "setSelfUpperBoundInference", "skipMetadataVersionCheck", "getSkipMetadataVersionCheck", "setSkipMetadataVersionCheck", "skipPrereleaseCheck", "getSkipPrereleaseCheck", "setSkipPrereleaseCheck", "suppressVersionWarnings", "getSuppressVersionWarnings", "setSuppressVersionWarnings", "unrestrictedBuilderInference", "getUnrestrictedBuilderInference", "setUnrestrictedBuilderInference", "useExperimental", "getUseExperimental$annotations", "getUseExperimental", "setUseExperimental", "useFirExtendedCheckers", "getUseFirExtendedCheckers", "setUseFirExtendedCheckers", "useFirIC", "getUseFirIC", "setUseFirIC", "useFirLT", "getUseFirLT", "setUseFirLT", "useK2", "getUseK2$annotations", "getUseK2", "setUseK2", "useMixedNamedArguments", "getUseMixedNamedArguments", "setUseMixedNamedArguments", "verbosePhases", "getVerbosePhases", "setVerbosePhases", "checkApiVersionIsNotGreaterThenLanguageVersion", "", "Lorg/jetbrains/kotlin/config/LanguageVersion;", "Lorg/jetbrains/kotlin/config/ApiVersion;", "collector", "Lorg/jetbrains/kotlin/cli/common/messages/MessageCollector;", "checkIrSupport", "languageVersionSettings", "Lorg/jetbrains/kotlin/config/LanguageVersionSettings;", "checkLanguageVersionIsStable", "checkOutdatedVersions", "language", "api", "checkPlatformSpecificSettings", "checkProgressiveMode", "configureAnalysisFlags", "", "Lorg/jetbrains/kotlin/config/AnalysisFlag;", "", "configureExtraLanguageFeatures", "map", "Ljava/util/HashMap;", "Lorg/jetbrains/kotlin/config/LanguageFeature;", "Lorg/jetbrains/kotlin/config/LanguageFeature$State;", "Lkotlin/collections/HashMap;", "configureLanguageFeatures", "defaultLanguageVersion", "findOutdatedVersion", "Lkotlin/Triple;", "Lorg/jetbrains/kotlin/config/LanguageOrApiVersion;", "Lorg/jetbrains/kotlin/cli/common/arguments/CommonCompilerArguments$VersionKind;", "parseOrConfigureLanguageVersion", "parseVersion", "versionOf", "toLanguageVersionSettings", "additionalAnalysisFlags", "", "configureLanguageFeaturesFromInternalArgs", "Companion", "DummyImpl", "VersionKind", "cli-common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class CommonCompilerArguments extends CommonToolArguments {
    public static final String DEFAULT = "default";
    public static final String ENABLE = "enable";
    public static final String ERROR = "error";
    public static final String PLUGIN_DECLARATION_FORMAT = "<path>[=<optionName>=<value>]";
    public static final String PLUGIN_OPTION_FORMAT = "plugin:<pluginId>:<optionName>=<value>";
    public static final String WARN = "warn";
    private static final long serialVersionUID = 0;

    @Argument(description = "Allow to compile any scripts along with regular Kotlin sources", value = "-Xallow-any-scripts-in-source-roots")
    private boolean allowAnyScriptsInSourceRoots;

    @Argument(description = "Allow compiling code in package 'kotlin' and allow not requiring kotlin.stdlib in module-info", value = "-Xallow-kotlin-package")
    private boolean allowKotlinPackage;

    @Argument(description = "Allow compiling code when `kotlin.Result` is used as a return type", value = "-Xallow-result-return-type")
    private boolean allowResultReturnType;

    @Argument(description = "Allow using declarations only from the specified version of bundled libraries", value = "-api-version", valueDescription = "<version>")
    private String apiVersion;

    @Argument(description = "Compile builtIns from sources", value = "-Xbuiltins-from-sources")
    private boolean builtInsFromSources;

    @Argument(description = "Check pre- and postconditions on phases", value = "-Xcheck-phase-conditions")
    private boolean checkPhaseConditions;

    @Argument(description = "Run sticky condition checks on subsequent phases as well. Implies -Xcheck-phase-conditions", value = "-Xcheck-sticky-phase-conditions")
    private boolean checkStickyPhaseConditions;

    @Argument(description = "Sources of the common module that need to be compiled together with this module in the multi-platform mode.\nShould be a subset of sources passed as free arguments", value = "-Xcommon-sources", valueDescription = "<path>")
    private String[] commonSources;

    @Argument(description = "Enable experimental context receivers", value = "-Xcontext-receivers")
    private boolean contextReceivers;

    @Argument(description = "Do not enable scripting plugin by default", value = "-Xdisable-default-scripting-plugin")
    private boolean disableDefaultScriptingPlugin;

    @Argument(description = "Disable backend phases", value = "-Xdisable-phases")
    private String[] disablePhases;

    @Argument(description = "Do not use the ultra light classes implementation", value = "-Xdisable-ultra-light-classes")
    private boolean disableUltraLightClasses;

    @Argument(description = "Dump backend state into directory", value = "-Xdump-directory")
    private String dumpDirectory;

    @Argument(description = "FqName of declaration that should be dumped", value = "-Xdump-fqname")
    private String dumpOnlyFqName;

    @Argument(description = "Dump detailed performance statistics to the specified file", value = "-Xdump-perf", valueDescription = "<path>")
    private String dumpPerf;

    @Argument(description = "Enable experimental language feature: effect system", value = "-Xeffect-system")
    private boolean effectSystem;

    @Argument(description = "Use the builder inference by default, for all calls with lambdas which can't be resolved without it.\nThe corresponding calls' declarations may not be marked with @BuilderInference.", value = "-Xenable-builder-inference")
    private boolean enableBuilderInference;

    @Argument(description = "'expect'/'actual' classes (including interfaces, objects, annotations, enums, and 'actual' typealiases) are in Beta.\nKotlin reports a warning every time you use them. You can use this flag to mute the warning.", value = "-Xexpect-actual-classes")
    private boolean expectActualClasses;

    @Argument(description = "Enable experimental expect/actual linker", value = "-Xexpect-actual-linker")
    private boolean expectActualLinker;
    private String[] experimental;

    @Argument(description = "Enable additional compiler checks that might provide verbose diagnostic information for certain errors.\nWarning: this mode is not backward-compatible and might cause compilation errors in previously compiled code.", value = "-Xextended-compiler-checks")
    private boolean extendedCompilerChecks;

    @Argument(description = "Declares that <fromModuleName> refines <onModuleName> with dependsOn/refines relation", value = "-Xfragment-refines", valueDescription = "<fromModuleName>:<onModuleName>")
    private String[] fragmentRefines;

    @Argument(description = "Adds sources to a specific fragment of a multiplatform compilation", value = "-Xfragment-sources", valueDescription = "<fragment name>:<path>")
    private String[] fragmentSources;

    @Argument(description = "Declares all known fragments of a multiplatform compilation", value = "-Xfragments", valueDescription = "<fragment name>")
    private String[] fragments;

    @Argument(description = "Ignore all compilation exceptions while optimizing some constant expressions.", value = "-Xignore-const-optimization-errors")
    private boolean ignoreConstOptimizationErrors;

    @Argument(description = "Enable incremental compilation", value = "-Xenable-incremental-compilation")
    private Boolean incrementalCompilation;

    @Argument(description = "Enable compatibility changes for generic type inference algorithm", value = "-Xinference-compatibility")
    private boolean inferenceCompatibility;

    @Argument(description = "Enable experimental inline classes", value = "-Xinline-classes")
    private boolean inlineClasses;

    @Argument(description = "Path to the kotlin-compiler.jar or directory where IntelliJ configuration files can be found", value = "-Xintellij-plugin-root", valueDescription = "<path>")
    private String intellijPluginRoot;

    @Argument(description = "Path to the home directory of Kotlin compiler used for discovery of runtime libraries", value = "-kotlin-home", valueDescription = "<path>")
    private String kotlinHome;

    @Argument(description = "Provide source compatibility with the specified version of Kotlin", value = "-language-version", valueDescription = "<version>")
    private String languageVersion;

    @Argument(description = "Allow var smart casts despite assignment in try block", value = "-Xlegacy-smart-cast-after-try")
    private boolean legacySmartCastAfterTry;

    @Argument(description = "List backend phases", value = "-Xlist-phases")
    private boolean listPhases;

    @Argument(description = "Change metadata version of the generated binary files", value = "-Xmetadata-version")
    private String metadataVersion;

    @Argument(description = "Enable language support for multi-platform projects", value = "-Xmulti-platform")
    private boolean multiPlatform;

    @Argument(description = "Enable new experimental generic type inference algorithm", value = "-Xnew-inference")
    private boolean newInference;

    @Argument(description = "Do not check presence of 'actual' modifier in multi-platform projects", value = "-Xno-check-actual")
    private boolean noCheckActual;

    @Argument(description = "Disable method inlining", value = "-Xno-inline")
    private boolean noInline;

    @Argument(description = "Normalize absolute paths in klibs", value = "-Xklib-normalize-absolute-path")
    private boolean normalizeAbsolutePath;

    @Argument(deprecatedName = "-Xopt-in", description = "Enable usages of API that requires opt-in with an opt-in requirement marker with the given fully qualified name", value = "-opt-in", valueDescription = "<fq.name>")
    private String[] optIn;

    @Argument(description = "Dump backend state both before and after these phases", value = "-Xphases-to-dump")
    private String[] phasesToDump;

    @Argument(description = "Dump backend state after these phases", value = "-Xphases-to-dump-after")
    private String[] phasesToDumpAfter;

    @Argument(description = "Dump backend state before these phases", value = "-Xphases-to-dump-before")
    private String[] phasesToDumpBefore;

    @Argument(description = "Validate backend state both before and after these phases", value = "-Xphases-to-validate")
    private String[] phasesToValidate;

    @Argument(description = "Validate backend state after these phases", value = "-Xphases-to-validate-after")
    private String[] phasesToValidateAfter;

    @Argument(description = "Validate backend state before these phases", value = "-Xphases-to-validate-before")
    private String[] phasesToValidateBefore;

    @Argument(description = "Load plugins from the given classpath", value = "-Xplugin", valueDescription = "<path>")
    private String[] pluginClasspaths;

    @Argument(delimiter = "", description = "Register compiler plugin", value = "-Xcompiler-plugin", valueDescription = "<path1>,<path2>:<optionName>=<value>,<optionName>=<value>")
    private String[] pluginConfigurations;

    @Argument(description = "Pass an option to a plugin", value = "-P", valueDescription = PLUGIN_OPTION_FORMAT)
    private String[] pluginOptions;

    @Argument(description = "Profile backend phases", value = "-Xprofile-phases")
    private boolean profilePhases;

    @Argument(deprecatedName = "-Xprogressive", description = "Enable progressive compiler mode.\nIn this mode, deprecations and bug fixes for unstable code take effect immediately,\ninstead of going through a graceful migration cycle.\nCode written in the progressive mode is backward compatible; however, code written in\nnon-progressive mode may cause compilation errors in the progressive mode.", value = "-progressive")
    private boolean progressiveMode;

    @Argument(description = "Generate proper IEEE 754 comparisons in all cases if values are statically known to be of primitive numeric types", value = "-Xproper-ieee754-comparisons")
    private boolean properIeee754Comparisons;

    @Argument(description = "Enable reading of contracts from metadata", value = "-Xread-deserialized-contracts")
    private boolean readDeserializedContracts;

    @Argument(description = "Provide a base paths to compute source's relative paths in klib (default is empty)", value = "-Xklib-relative-path-base")
    private String[] relativePathBases;

    @Argument(description = "Render internal names of warnings and errors", value = "-Xrender-internal-diagnostic-names")
    private boolean renderInternalDiagnosticNames;

    @Argument(description = "Report source to output files mapping", value = "-Xreport-output-files")
    private boolean reportOutputFiles;

    @Argument(description = "Report detailed performance statistics", value = "-Xreport-perf")
    private boolean reportPerf;

    @Argument(description = "Evaluate the given Kotlin script (*.kts) file", value = "-script")
    private boolean script;

    @Argument(description = "Support inferring type arguments based on only self upper bounds of the corresponding type parameters", value = "-Xself-upper-bound-inference")
    private boolean selfUpperBoundInference;

    @Argument(description = "Allow to load classes with bad metadata version and pre-release classes", value = "-Xskip-metadata-version-check")
    private boolean skipMetadataVersionCheck;

    @Argument(description = "Allow to load pre-release classes", value = "-Xskip-prerelease-check")
    private boolean skipPrereleaseCheck;

    @Argument(description = "Suppress warnings about outdated, inconsistent or experimental language or API versions", value = "-Xsuppress-version-warnings")
    private boolean suppressVersionWarnings;

    @Argument(description = "Eliminate builder inference restrictions like allowance of returning type variables of a builder inference call", value = "-Xunrestricted-builder-inference")
    private boolean unrestrictedBuilderInference;
    private String[] useExperimental;

    @Argument(description = "Use extended analysis mode based on Front-end IR. Warning: this feature is far from being production-ready", value = "-Xuse-fir-extended-checkers")
    private boolean useFirExtendedCheckers;

    @Argument(description = "Compile using Front-end IR internal incremental compilation cycle. Warning: this feature is far from being production-ready", value = "-Xuse-fir-ic")
    private boolean useFirIC;

    @Argument(deprecatedName = "-Xuse-fir", description = "Compile using experimental K2. K2 is a new compiler pipeline, no compatibility guarantees are yet provided", value = "-Xuse-k2")
    private boolean useK2;

    @Argument(description = "Enable Support named arguments in their own position even if the result appears as mixed", value = "-Xuse-mixed-named-arguments")
    private boolean useMixedNamedArguments;

    @Argument(description = "Be verbose while performing these backend phases", value = "-Xverbose-phases")
    private String[] verbosePhases;
    private boolean autoAdvanceLanguageVersion = true;
    private boolean autoAdvanceApiVersion = true;

    @Argument(description = "Compile using LightTree parser with Front-end IR", value = "-Xuse-fir-lt")
    private boolean useFirLT = true;

    @Argument(description = "Force compiler to report errors on all public API declarations without explicit visibility or return type.\nUse 'warning' level to issue warnings instead of errors.", value = "-Xexplicit-api", valueDescription = "{strict|warning|disable}")
    private String explicitApi = ExplicitApiMode.DISABLED.getState();

    @Argument(description = "Enable the checks on uniqueness of signatures", value = "-Xklib-enable-signature-clash-checks")
    private boolean enableSignatureClashChecks = true;

    /* JADX INFO: compiled from: CommonCompilerArguments.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[LanguageFeature.values().length];
            try {
                iArr[LanguageFeature.SamConversionPerArgument.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[LanguageFeature.FunctionReferenceWithDefaultValueAsOtherType.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    @GradleOption(gradleInputType = GradleInputTypes.INPUT, shouldGenerateDeprecatedKotlinOptions = true, value = DefaultValue.API_VERSIONS)
    public static /* synthetic */ void getApiVersion$annotations() {
    }

    @IDEAPluginsCompatibilityAPI(message = "Please migrate to -opt-in", plugins = "Android", usedIn = {IDEAPlatforms._212, IDEAPlatforms._213})
    public static /* synthetic */ void getExperimental$annotations() {
    }

    @GradleOption(gradleInputType = GradleInputTypes.INPUT, shouldGenerateDeprecatedKotlinOptions = true, value = DefaultValue.LANGUAGE_VERSIONS)
    public static /* synthetic */ void getLanguageVersion$annotations() {
    }

    @GradleOption(gradleInputType = GradleInputTypes.INPUT, value = DefaultValue.EMPTY_STRING_ARRAY_DEFAULT)
    public static /* synthetic */ void getOptIn$annotations() {
    }

    @GradleOption(gradleInputType = GradleInputTypes.INPUT, value = DefaultValue.BOOLEAN_FALSE_DEFAULT)
    public static /* synthetic */ void getProgressiveMode$annotations() {
    }

    @IDEAPluginsCompatibilityAPI(message = "Please migrate to -opt-in", plugins = "Android", usedIn = {IDEAPlatforms._212, IDEAPlatforms._213})
    public static /* synthetic */ void getUseExperimental$annotations() {
    }

    @GradleDeprecatedOption(level = DeprecationLevel.WARNING, message = "Compiler flag -Xuse-k2 is deprecated; please use language version 2.0 instead", removeAfter = "2.0.0")
    @GradleOption(gradleInputType = GradleInputTypes.INPUT, shouldGenerateDeprecatedKotlinOptions = true, value = DefaultValue.BOOLEAN_FALSE_DEFAULT)
    public static /* synthetic */ void getUseK2$annotations() {
    }

    protected void checkIrSupport(LanguageVersionSettings languageVersionSettings, MessageCollector collector) {
        Intrinsics.checkNotNullParameter(languageVersionSettings, "languageVersionSettings");
        Intrinsics.checkNotNullParameter(collector, "collector");
    }

    protected void checkPlatformSpecificSettings(LanguageVersionSettings languageVersionSettings, MessageCollector collector) {
        Intrinsics.checkNotNullParameter(languageVersionSettings, "languageVersionSettings");
        Intrinsics.checkNotNullParameter(collector, "collector");
    }

    protected void configureExtraLanguageFeatures(HashMap<LanguageFeature, LanguageFeature.State> map) {
        Intrinsics.checkNotNullParameter(map, "map");
    }

    @Transient
    public final boolean getAutoAdvanceLanguageVersion() {
        return this.autoAdvanceLanguageVersion;
    }

    public final void setAutoAdvanceLanguageVersion(boolean z) {
        checkFrozen();
        this.autoAdvanceLanguageVersion = z;
    }

    public final String getLanguageVersion() {
        return this.languageVersion;
    }

    public final void setLanguageVersion(String str) {
        checkFrozen();
        String str2 = str;
        if (str2 == null || str2.length() == 0) {
            str = null;
        }
        this.languageVersion = str;
    }

    @Transient
    public final boolean getAutoAdvanceApiVersion() {
        return this.autoAdvanceApiVersion;
    }

    public final void setAutoAdvanceApiVersion(boolean z) {
        checkFrozen();
        this.autoAdvanceApiVersion = z;
    }

    public final String getApiVersion() {
        return this.apiVersion;
    }

    public final void setApiVersion(String str) {
        checkFrozen();
        String str2 = str;
        if (str2 == null || str2.length() == 0) {
            str = null;
        }
        this.apiVersion = str;
    }

    public final String getKotlinHome() {
        return this.kotlinHome;
    }

    public final void setKotlinHome(String str) {
        checkFrozen();
        String str2 = str;
        if (str2 == null || str2.length() == 0) {
            str = null;
        }
        this.kotlinHome = str;
    }

    public final boolean getProgressiveMode() {
        return this.progressiveMode;
    }

    public final void setProgressiveMode(boolean z) {
        checkFrozen();
        this.progressiveMode = z;
    }

    public final boolean getScript() {
        return this.script;
    }

    public final void setScript(boolean z) {
        checkFrozen();
        this.script = z;
    }

    public final String[] getOptIn() {
        return this.optIn;
    }

    public final void setOptIn(String[] strArr) {
        checkFrozen();
        this.optIn = strArr;
    }

    public final boolean getNoInline() {
        return this.noInline;
    }

    public final void setNoInline(boolean z) {
        checkFrozen();
        this.noInline = z;
    }

    public final boolean getSkipMetadataVersionCheck() {
        return this.skipMetadataVersionCheck;
    }

    public final void setSkipMetadataVersionCheck(boolean z) {
        checkFrozen();
        this.skipMetadataVersionCheck = z;
    }

    public final boolean getSkipPrereleaseCheck() {
        return this.skipPrereleaseCheck;
    }

    public final void setSkipPrereleaseCheck(boolean z) {
        checkFrozen();
        this.skipPrereleaseCheck = z;
    }

    public final boolean getAllowKotlinPackage() {
        return this.allowKotlinPackage;
    }

    public final void setAllowKotlinPackage(boolean z) {
        checkFrozen();
        this.allowKotlinPackage = z;
    }

    public final boolean getReportOutputFiles() {
        return this.reportOutputFiles;
    }

    public final void setReportOutputFiles(boolean z) {
        checkFrozen();
        this.reportOutputFiles = z;
    }

    public final String[] getPluginClasspaths() {
        return this.pluginClasspaths;
    }

    public final void setPluginClasspaths(String[] strArr) {
        checkFrozen();
        this.pluginClasspaths = strArr;
    }

    public final String[] getPluginOptions() {
        return this.pluginOptions;
    }

    public final void setPluginOptions(String[] strArr) {
        checkFrozen();
        this.pluginOptions = strArr;
    }

    public final String[] getPluginConfigurations() {
        return this.pluginConfigurations;
    }

    public final void setPluginConfigurations(String[] strArr) {
        checkFrozen();
        this.pluginConfigurations = strArr;
    }

    public final boolean getMultiPlatform() {
        return this.multiPlatform;
    }

    public final void setMultiPlatform(boolean z) {
        checkFrozen();
        this.multiPlatform = z;
    }

    public final boolean getNoCheckActual() {
        return this.noCheckActual;
    }

    public final void setNoCheckActual(boolean z) {
        checkFrozen();
        this.noCheckActual = z;
    }

    public final String getIntellijPluginRoot() {
        return this.intellijPluginRoot;
    }

    public final void setIntellijPluginRoot(String str) {
        checkFrozen();
        String str2 = str;
        if (str2 == null || str2.length() == 0) {
            str = null;
        }
        this.intellijPluginRoot = str;
    }

    public final boolean getNewInference() {
        return this.newInference;
    }

    public final void setNewInference(boolean z) {
        checkFrozen();
        this.newInference = z;
    }

    public final boolean getInlineClasses() {
        return this.inlineClasses;
    }

    public final void setInlineClasses(boolean z) {
        checkFrozen();
        this.inlineClasses = z;
    }

    public final boolean getLegacySmartCastAfterTry() {
        return this.legacySmartCastAfterTry;
    }

    public final void setLegacySmartCastAfterTry(boolean z) {
        checkFrozen();
        this.legacySmartCastAfterTry = z;
    }

    public final boolean getEffectSystem() {
        return this.effectSystem;
    }

    public final void setEffectSystem(boolean z) {
        checkFrozen();
        this.effectSystem = z;
    }

    public final boolean getReadDeserializedContracts() {
        return this.readDeserializedContracts;
    }

    public final void setReadDeserializedContracts(boolean z) {
        checkFrozen();
        this.readDeserializedContracts = z;
    }

    public final String[] getExperimental() {
        return this.experimental;
    }

    public final void setExperimental(String[] strArr) {
        this.experimental = strArr;
    }

    public final String[] getUseExperimental() {
        return this.useExperimental;
    }

    public final void setUseExperimental(String[] strArr) {
        checkFrozen();
        this.useExperimental = strArr;
    }

    public final boolean getProperIeee754Comparisons() {
        return this.properIeee754Comparisons;
    }

    public final void setProperIeee754Comparisons(boolean z) {
        checkFrozen();
        this.properIeee754Comparisons = z;
    }

    public final boolean getReportPerf() {
        return this.reportPerf;
    }

    public final void setReportPerf(boolean z) {
        checkFrozen();
        this.reportPerf = z;
    }

    public final String getDumpPerf() {
        return this.dumpPerf;
    }

    public final void setDumpPerf(String str) {
        checkFrozen();
        String str2 = str;
        if (str2 == null || str2.length() == 0) {
            str = null;
        }
        this.dumpPerf = str;
    }

    public final String getMetadataVersion() {
        return this.metadataVersion;
    }

    public final void setMetadataVersion(String str) {
        checkFrozen();
        this.metadataVersion = str;
    }

    public final String[] getCommonSources() {
        return this.commonSources;
    }

    public final void setCommonSources(String[] strArr) {
        checkFrozen();
        this.commonSources = strArr;
    }

    public final boolean getAllowResultReturnType() {
        return this.allowResultReturnType;
    }

    public final void setAllowResultReturnType(boolean z) {
        checkFrozen();
        this.allowResultReturnType = z;
    }

    public final boolean getListPhases() {
        return this.listPhases;
    }

    public final void setListPhases(boolean z) {
        checkFrozen();
        this.listPhases = z;
    }

    public final String[] getDisablePhases() {
        return this.disablePhases;
    }

    public final void setDisablePhases(String[] strArr) {
        checkFrozen();
        this.disablePhases = strArr;
    }

    public final String[] getVerbosePhases() {
        return this.verbosePhases;
    }

    public final void setVerbosePhases(String[] strArr) {
        checkFrozen();
        this.verbosePhases = strArr;
    }

    public final String[] getPhasesToDumpBefore() {
        return this.phasesToDumpBefore;
    }

    public final void setPhasesToDumpBefore(String[] strArr) {
        checkFrozen();
        this.phasesToDumpBefore = strArr;
    }

    public final String[] getPhasesToDumpAfter() {
        return this.phasesToDumpAfter;
    }

    public final void setPhasesToDumpAfter(String[] strArr) {
        checkFrozen();
        this.phasesToDumpAfter = strArr;
    }

    public final String[] getPhasesToDump() {
        return this.phasesToDump;
    }

    public final void setPhasesToDump(String[] strArr) {
        checkFrozen();
        this.phasesToDump = strArr;
    }

    public final String getDumpDirectory() {
        return this.dumpDirectory;
    }

    public final void setDumpDirectory(String str) {
        checkFrozen();
        this.dumpDirectory = str;
    }

    public final String getDumpOnlyFqName() {
        return this.dumpOnlyFqName;
    }

    public final void setDumpOnlyFqName(String str) {
        checkFrozen();
        this.dumpOnlyFqName = str;
    }

    public final String[] getPhasesToValidateBefore() {
        return this.phasesToValidateBefore;
    }

    public final void setPhasesToValidateBefore(String[] strArr) {
        checkFrozen();
        this.phasesToValidateBefore = strArr;
    }

    public final String[] getPhasesToValidateAfter() {
        return this.phasesToValidateAfter;
    }

    public final void setPhasesToValidateAfter(String[] strArr) {
        checkFrozen();
        this.phasesToValidateAfter = strArr;
    }

    public final String[] getPhasesToValidate() {
        return this.phasesToValidate;
    }

    public final void setPhasesToValidate(String[] strArr) {
        checkFrozen();
        this.phasesToValidate = strArr;
    }

    public final boolean getProfilePhases() {
        return this.profilePhases;
    }

    public final void setProfilePhases(boolean z) {
        checkFrozen();
        this.profilePhases = z;
    }

    public final boolean getCheckPhaseConditions() {
        return this.checkPhaseConditions;
    }

    public final void setCheckPhaseConditions(boolean z) {
        checkFrozen();
        this.checkPhaseConditions = z;
    }

    public final boolean getCheckStickyPhaseConditions() {
        return this.checkStickyPhaseConditions;
    }

    public final void setCheckStickyPhaseConditions(boolean z) {
        checkFrozen();
        this.checkStickyPhaseConditions = z;
    }

    public final boolean getUseK2() {
        return this.useK2;
    }

    public final void setUseK2(boolean z) {
        checkFrozen();
        this.useK2 = z;
    }

    public final boolean getUseFirExtendedCheckers() {
        return this.useFirExtendedCheckers;
    }

    public final void setUseFirExtendedCheckers(boolean z) {
        checkFrozen();
        this.useFirExtendedCheckers = z;
    }

    public final boolean getUseFirIC() {
        return this.useFirIC;
    }

    public final void setUseFirIC(boolean z) {
        checkFrozen();
        this.useFirIC = z;
    }

    public final boolean getUseFirLT() {
        return this.useFirLT;
    }

    public final void setUseFirLT(boolean z) {
        checkFrozen();
        this.useFirLT = z;
    }

    public final boolean getDisableUltraLightClasses() {
        return this.disableUltraLightClasses;
    }

    public final void setDisableUltraLightClasses(boolean z) {
        checkFrozen();
        this.disableUltraLightClasses = z;
    }

    public final boolean getUseMixedNamedArguments() {
        return this.useMixedNamedArguments;
    }

    public final void setUseMixedNamedArguments(boolean z) {
        checkFrozen();
        this.useMixedNamedArguments = z;
    }

    public final boolean getExpectActualLinker() {
        return this.expectActualLinker;
    }

    public final void setExpectActualLinker(boolean z) {
        checkFrozen();
        this.expectActualLinker = z;
    }

    public final boolean getDisableDefaultScriptingPlugin() {
        return this.disableDefaultScriptingPlugin;
    }

    public final void setDisableDefaultScriptingPlugin(boolean z) {
        checkFrozen();
        this.disableDefaultScriptingPlugin = z;
    }

    public final String getExplicitApi() {
        return this.explicitApi;
    }

    public final void setExplicitApi(String value) {
        Intrinsics.checkNotNullParameter(value, "value");
        checkFrozen();
        this.explicitApi = value;
    }

    public final boolean getInferenceCompatibility() {
        return this.inferenceCompatibility;
    }

    public final void setInferenceCompatibility(boolean z) {
        checkFrozen();
        this.inferenceCompatibility = z;
    }

    public final boolean getSuppressVersionWarnings() {
        return this.suppressVersionWarnings;
    }

    public final void setSuppressVersionWarnings(boolean z) {
        checkFrozen();
        this.suppressVersionWarnings = z;
    }

    public final boolean getExtendedCompilerChecks() {
        return this.extendedCompilerChecks;
    }

    public final void setExtendedCompilerChecks(boolean z) {
        checkFrozen();
        this.extendedCompilerChecks = z;
    }

    public final boolean getBuiltInsFromSources() {
        return this.builtInsFromSources;
    }

    public final void setBuiltInsFromSources(boolean z) {
        checkFrozen();
        this.builtInsFromSources = z;
    }

    public final boolean getExpectActualClasses() {
        return this.expectActualClasses;
    }

    public final void setExpectActualClasses(boolean z) {
        checkFrozen();
        this.expectActualClasses = z;
    }

    public final boolean getUnrestrictedBuilderInference() {
        return this.unrestrictedBuilderInference;
    }

    public final void setUnrestrictedBuilderInference(boolean z) {
        checkFrozen();
        this.unrestrictedBuilderInference = z;
    }

    public final boolean getEnableBuilderInference() {
        return this.enableBuilderInference;
    }

    public final void setEnableBuilderInference(boolean z) {
        checkFrozen();
        this.enableBuilderInference = z;
    }

    public final boolean getSelfUpperBoundInference() {
        return this.selfUpperBoundInference;
    }

    public final void setSelfUpperBoundInference(boolean z) {
        checkFrozen();
        this.selfUpperBoundInference = z;
    }

    public final boolean getContextReceivers() {
        return this.contextReceivers;
    }

    public final void setContextReceivers(boolean z) {
        checkFrozen();
        this.contextReceivers = z;
    }

    public final String[] getRelativePathBases() {
        return this.relativePathBases;
    }

    public final void setRelativePathBases(String[] strArr) {
        checkFrozen();
        this.relativePathBases = strArr;
    }

    public final boolean getNormalizeAbsolutePath() {
        return this.normalizeAbsolutePath;
    }

    public final void setNormalizeAbsolutePath(boolean z) {
        checkFrozen();
        this.normalizeAbsolutePath = z;
    }

    public final boolean getEnableSignatureClashChecks() {
        return this.enableSignatureClashChecks;
    }

    public final void setEnableSignatureClashChecks(boolean z) {
        checkFrozen();
        this.enableSignatureClashChecks = z;
    }

    public final Boolean getIncrementalCompilation() {
        return this.incrementalCompilation;
    }

    public final void setIncrementalCompilation(Boolean bool) {
        checkFrozen();
        this.incrementalCompilation = bool;
    }

    public final boolean getRenderInternalDiagnosticNames() {
        return this.renderInternalDiagnosticNames;
    }

    public final void setRenderInternalDiagnosticNames(boolean z) {
        checkFrozen();
        this.renderInternalDiagnosticNames = z;
    }

    public final boolean getAllowAnyScriptsInSourceRoots() {
        return this.allowAnyScriptsInSourceRoots;
    }

    public final void setAllowAnyScriptsInSourceRoots(boolean z) {
        checkFrozen();
        this.allowAnyScriptsInSourceRoots = z;
    }

    public final String[] getFragments() {
        return this.fragments;
    }

    public final void setFragments(String[] strArr) {
        this.fragments = strArr;
    }

    public final String[] getFragmentSources() {
        return this.fragmentSources;
    }

    public final void setFragmentSources(String[] strArr) {
        checkFrozen();
        this.fragmentSources = strArr;
    }

    public final String[] getFragmentRefines() {
        return this.fragmentRefines;
    }

    public final void setFragmentRefines(String[] strArr) {
        checkFrozen();
        this.fragmentRefines = strArr;
    }

    public final boolean getIgnoreConstOptimizationErrors() {
        return this.ignoreConstOptimizationErrors;
    }

    public final void setIgnoreConstOptimizationErrors(boolean z) {
        checkFrozen();
        this.ignoreConstOptimizationErrors = z;
    }

    /* JADX WARN: Code duplicated, block: B:30:0x00be  */
    public Map<AnalysisFlag<?>, Object> configureAnalysisFlags(MessageCollector collector, LanguageVersion languageVersion) {
        Intrinsics.checkNotNullParameter(collector, "collector");
        Intrinsics.checkNotNullParameter(languageVersion, "languageVersion");
        HashMap map = new HashMap();
        map.put(AnalysisFlags.getSkipMetadataVersionCheck(), Boolean.valueOf(this.skipMetadataVersionCheck));
        map.put(AnalysisFlags.getSkipPrereleaseCheck(), Boolean.valueOf(this.skipPrereleaseCheck || this.skipMetadataVersionCheck));
        map.put(AnalysisFlags.getMultiPlatformDoNotCheckActual(), Boolean.valueOf(this.noCheckActual));
        String[] strArr = this.useExperimental;
        List list = strArr != null ? ArraysKt.toList(strArr) : null;
        if (list == null) {
            list = CollectionsKt.emptyList();
        }
        List list2 = list;
        if (!list2.isEmpty()) {
            MessageCollector.report$default(collector, CompilerMessageSeverity.WARNING, "'-Xuse-experimental' is deprecated and will be removed in a future release, please use -opt-in instead", null, 4, null);
        }
        AnalysisFlag<List<String>> optIn = AnalysisFlags.getOptIn();
        String[] strArr2 = this.optIn;
        List list3 = strArr2 != null ? ArraysKt.toList(strArr2) : null;
        if (list3 == null) {
            list3 = CollectionsKt.emptyList();
        }
        map.put(optIn, CollectionsKt.plus((Collection) list2, (Iterable) list3));
        map.put(AnalysisFlags.getExpectActualLinker(), Boolean.valueOf(this.expectActualLinker));
        map.put(AnalysisFlags.getExplicitApiVersion(), Boolean.valueOf(this.apiVersion != null));
        map.put(AnalysisFlags.getAllowResultReturnType(), Boolean.valueOf(this.allowResultReturnType));
        ExplicitApiMode explicitApiModeFromString = ExplicitApiMode.INSTANCE.fromString(this.explicitApi);
        if (explicitApiModeFromString != null) {
            map.put(AnalysisFlags.getExplicitApiMode(), explicitApiModeFromString);
            if (explicitApiModeFromString == null) {
                MessageCollector.report$default(collector, CompilerMessageSeverity.ERROR, "Unknown value for parameter -Xexplicit-api: '" + this.explicitApi + "'. Value should be one of " + ExplicitApiMode.INSTANCE.availableValues(), null, 4, null);
                Unit unit = Unit.INSTANCE;
            }
        } else {
            MessageCollector.report$default(collector, CompilerMessageSeverity.ERROR, "Unknown value for parameter -Xexplicit-api: '" + this.explicitApi + "'. Value should be one of " + ExplicitApiMode.INSTANCE.availableValues(), null, 4, null);
            Unit unit2 = Unit.INSTANCE;
        }
        map.put(AnalysisFlags.getExtendedCompilerChecks(), Boolean.valueOf(this.extendedCompilerChecks));
        map.put(AnalysisFlags.getAllowKotlinPackage(), Boolean.valueOf(this.allowKotlinPackage));
        map.put(AnalysisFlags.getBuiltInsFromSources(), Boolean.valueOf(this.builtInsFromSources));
        map.put(AnalysisFlags.getMuteExpectActualClassesWarning(), Boolean.valueOf(this.expectActualClasses));
        map.put(AnalysisFlags.getAllowFullyQualifiedNameInKClass(), true);
        return map;
    }

    public Map<LanguageFeature, LanguageFeature.State> configureLanguageFeatures(MessageCollector collector) {
        Intrinsics.checkNotNullParameter(collector, "collector");
        HashMap<LanguageFeature, LanguageFeature.State> map = new HashMap<>();
        if (this.multiPlatform) {
            map.put(LanguageFeature.MultiPlatformProjects, LanguageFeature.State.ENABLED);
        }
        if (this.unrestrictedBuilderInference) {
            map.put(LanguageFeature.UnrestrictedBuilderInference, LanguageFeature.State.ENABLED);
        }
        if (this.enableBuilderInference) {
            map.put(LanguageFeature.UseBuilderInferenceWithoutAnnotation, LanguageFeature.State.ENABLED);
        }
        if (this.selfUpperBoundInference) {
            map.put(LanguageFeature.TypeInferenceOnCallsWithSelfTypes, LanguageFeature.State.ENABLED);
        }
        if (this.newInference) {
            map.put(LanguageFeature.NewInference, LanguageFeature.State.ENABLED);
            map.put(LanguageFeature.SamConversionPerArgument, LanguageFeature.State.ENABLED);
            map.put(LanguageFeature.FunctionReferenceWithDefaultValueAsOtherType, LanguageFeature.State.ENABLED);
            map.put(LanguageFeature.DisableCompatibilityModeForNewInference, LanguageFeature.State.ENABLED);
        }
        if (this.contextReceivers) {
            map.put(LanguageFeature.ContextReceivers, LanguageFeature.State.ENABLED);
        }
        if (this.inlineClasses) {
            map.put(LanguageFeature.InlineClasses, LanguageFeature.State.ENABLED);
        }
        if (this.legacySmartCastAfterTry) {
            map.put(LanguageFeature.SoundSmartCastsAfterTry, LanguageFeature.State.DISABLED);
        }
        if (this.effectSystem) {
            map.put(LanguageFeature.UseCallsInPlaceEffect, LanguageFeature.State.ENABLED);
            map.put(LanguageFeature.UseReturnsEffect, LanguageFeature.State.ENABLED);
        }
        if (this.readDeserializedContracts) {
            map.put(LanguageFeature.ReadDeserializedContracts, LanguageFeature.State.ENABLED);
        }
        if (this.properIeee754Comparisons) {
            map.put(LanguageFeature.ProperIeee754Comparisons, LanguageFeature.State.ENABLED);
        }
        if (this.useMixedNamedArguments) {
            map.put(LanguageFeature.MixedNamedArgumentsInTheirOwnPosition, LanguageFeature.State.ENABLED);
        }
        if (this.inferenceCompatibility) {
            map.put(LanguageFeature.InferenceCompatibility, LanguageFeature.State.ENABLED);
        }
        if (this.progressiveMode) {
            LanguageFeature[] languageFeatureArrValues = LanguageFeature.values();
            ArrayList<LanguageFeature> arrayList = new ArrayList();
            for (LanguageFeature languageFeature : languageFeatureArrValues) {
                if (languageFeature.getKind().getEnabledInProgressiveMode()) {
                    arrayList.add(languageFeature);
                }
            }
            for (LanguageFeature languageFeature2 : arrayList) {
                if (!map.containsKey(languageFeature2)) {
                    map.put(languageFeature2, LanguageFeature.State.ENABLED);
                }
            }
        }
        if (this.useK2) {
            map.put(LanguageFeature.SkipStandaloneScriptsInSourceRoots, LanguageFeature.State.ENABLED);
        } else if (this.allowAnyScriptsInSourceRoots) {
            map.put(LanguageFeature.SkipStandaloneScriptsInSourceRoots, LanguageFeature.State.DISABLED);
        }
        if (!getInternalArguments().isEmpty()) {
            configureLanguageFeaturesFromInternalArgs(map, collector);
        }
        configureExtraLanguageFeatures(map);
        return map;
    }

    private final void configureLanguageFeaturesFromInternalArgs(HashMap<LanguageFeature, LanguageFeature.State> map, MessageCollector messageCollector) {
        LanguageVersion sinceVersion;
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        List<InternalArgument> internalArguments = getInternalArguments();
        ArrayList<ManualLanguageFeatureSetting> arrayList3 = new ArrayList();
        for (Object obj : internalArguments) {
            if (obj instanceof ManualLanguageFeatureSetting) {
                arrayList3.add(obj);
            }
        }
        boolean z = false;
        boolean z2 = false;
        for (ManualLanguageFeatureSetting manualLanguageFeatureSetting : arrayList3) {
            LanguageFeature languageFeature = manualLanguageFeatureSetting.getLanguageFeature();
            LanguageFeature.State state = manualLanguageFeatureSetting.getState();
            map.put(languageFeature, state);
            if (state == LanguageFeature.State.ENABLED && LanguageVersionSettingsKt.forcesPreReleaseBinariesIfEnabled(languageFeature)) {
                arrayList.add(languageFeature);
            }
            if (state == LanguageFeature.State.DISABLED && (sinceVersion = languageFeature.getSinceVersion()) != null && sinceVersion.isUnsupported()) {
                arrayList2.add(languageFeature);
            }
            int i = WhenMappings.$EnumSwitchMapping$0[languageFeature.ordinal()];
            if (i == 1) {
                z = true;
            } else if (i == 2) {
                z2 = true;
            }
        }
        if (map.get(LanguageFeature.NewInference) == LanguageFeature.State.ENABLED) {
            if (!z) {
                map.put(LanguageFeature.SamConversionPerArgument, LanguageFeature.State.ENABLED);
            }
            if (!z2) {
                map.put(LanguageFeature.FunctionReferenceWithDefaultValueAsOtherType, LanguageFeature.State.ENABLED);
            }
            map.put(LanguageFeature.DisableCompatibilityModeForNewInference, LanguageFeature.State.ENABLED);
        }
        if (!arrayList.isEmpty()) {
            MessageCollector.report$default(messageCollector, CompilerMessageSeverity.STRONG_WARNING, "Following manually enabled features will force generation of pre-release binaries: " + CollectionsKt.joinToString$default(arrayList, null, null, null, 0, null, null, 63, null), null, 4, null);
        }
        if (arrayList2.isEmpty()) {
            return;
        }
        MessageCollector.report$default(messageCollector, CompilerMessageSeverity.ERROR, "The following features cannot be disabled manually, because the version they first appeared in is no longer supported:\n" + CollectionsKt.joinToString$default(arrayList2, null, null, null, 0, null, null, 63, null), null, 4, null);
    }

    public final LanguageVersionSettings toLanguageVersionSettings(MessageCollector collector) {
        Intrinsics.checkNotNullParameter(collector, "collector");
        return toLanguageVersionSettings(collector, MapsKt.emptyMap());
    }

    public final LanguageVersionSettings toLanguageVersionSettings(MessageCollector collector, Map<AnalysisFlag<?>, ? extends Object> additionalAnalysisFlags) {
        Intrinsics.checkNotNullParameter(collector, "collector");
        Intrinsics.checkNotNullParameter(additionalAnalysisFlags, "additionalAnalysisFlags");
        LanguageVersion orConfigureLanguageVersion = parseOrConfigureLanguageVersion(collector);
        ApiVersion.Companion companion = ApiVersion.INSTANCE;
        LanguageVersion version = parseVersion(collector, this.apiVersion, "API");
        if (version == null) {
            version = orConfigureLanguageVersion;
        }
        ApiVersion apiVersionCreateByLanguageVersion = companion.createByLanguageVersion(version);
        checkApiVersionIsNotGreaterThenLanguageVersion(orConfigureLanguageVersion, apiVersionCreateByLanguageVersion, collector);
        LanguageVersionSettingsImpl languageVersionSettingsImpl = new LanguageVersionSettingsImpl(orConfigureLanguageVersion, apiVersionCreateByLanguageVersion, MapsKt.plus(configureAnalysisFlags(collector, orConfigureLanguageVersion), additionalAnalysisFlags), configureLanguageFeatures(collector));
        checkLanguageVersionIsStable(orConfigureLanguageVersion, collector);
        checkOutdatedVersions(orConfigureLanguageVersion, apiVersionCreateByLanguageVersion, collector);
        checkProgressiveMode(orConfigureLanguageVersion, collector);
        LanguageVersionSettingsImpl languageVersionSettingsImpl2 = languageVersionSettingsImpl;
        checkIrSupport(languageVersionSettingsImpl2, collector);
        checkPlatformSpecificSettings(languageVersionSettingsImpl2, collector);
        return languageVersionSettingsImpl2;
    }

    private final void checkApiVersionIsNotGreaterThenLanguageVersion(LanguageVersion languageVersion, ApiVersion apiVersion, MessageCollector collector) {
        if (apiVersion.compareTo(ApiVersion.INSTANCE.createByLanguageVersion(languageVersion)) > 0) {
            MessageCollector.report$default(collector, CompilerMessageSeverity.ERROR, "-api-version (" + apiVersion.getVersionString() + ") cannot be greater than -language-version (" + languageVersion.getVersionString() + ')', null, 4, null);
        }
    }

    public final void checkLanguageVersionIsStable(LanguageVersion languageVersion, MessageCollector collector) {
        Intrinsics.checkNotNullParameter(languageVersion, "languageVersion");
        Intrinsics.checkNotNullParameter(collector, "collector");
        if (languageVersion.isStable() || this.suppressVersionWarnings) {
            return;
        }
        MessageCollector.report$default(collector, CompilerMessageSeverity.STRONG_WARNING, "Language version " + languageVersion.getVersionString() + " is experimental, there are no backwards compatibility guarantees for new language and library features", null, 4, null);
    }

    private final void checkOutdatedVersions(LanguageVersion language, ApiVersion api, MessageCollector collector) {
        Triple<LanguageOrApiVersion, LanguageOrApiVersion, VersionKind> tripleFindOutdatedVersion = findOutdatedVersion(language, api);
        if (tripleFindOutdatedVersion == null) {
            return;
        }
        LanguageOrApiVersion languageOrApiVersionComponent1 = tripleFindOutdatedVersion.component1();
        LanguageOrApiVersion languageOrApiVersionComponent2 = tripleFindOutdatedVersion.component2();
        VersionKind versionKindComponent3 = tripleFindOutdatedVersion.component3();
        if (languageOrApiVersionComponent1.isUnsupported()) {
            CompilerMessageSeverity compilerMessageSeverity = CompilerMessageSeverity.ERROR;
            StringBuilder sbAppend = new StringBuilder().append(versionKindComponent3.getText()).append(" version ").append(languageOrApiVersionComponent1.getVersionString()).append(" is no longer supported; please, use version ");
            Intrinsics.checkNotNull(languageOrApiVersionComponent2);
            MessageCollector.report$default(collector, compilerMessageSeverity, sbAppend.append(languageOrApiVersionComponent2.getVersionString()).append(" or greater.").toString(), null, 4, null);
            return;
        }
        if (!languageOrApiVersionComponent1.isDeprecated() || this.suppressVersionWarnings) {
            return;
        }
        MessageCollector.report$default(collector, CompilerMessageSeverity.STRONG_WARNING, versionKindComponent3.getText() + " version " + languageOrApiVersionComponent1.getVersionString() + " is deprecated and its support will be removed in a future version of Kotlin", null, 4, null);
    }

    private final Triple<LanguageOrApiVersion, LanguageOrApiVersion, VersionKind> findOutdatedVersion(LanguageVersion language, ApiVersion api) {
        Triple<LanguageOrApiVersion, LanguageOrApiVersion, VersionKind> triple;
        if (language.isUnsupported()) {
            return new Triple<>(language, LanguageVersion.FIRST_SUPPORTED, VersionKind.LANGUAGE);
        }
        if (api.isUnsupported()) {
            triple = new Triple<>(api, ApiVersion.FIRST_SUPPORTED, VersionKind.API);
        } else {
            if (language.isDeprecated()) {
                return new Triple<>(language, null, VersionKind.LANGUAGE);
            }
            if (!api.isDeprecated()) {
                return null;
            }
            triple = new Triple<>(api, null, VersionKind.API);
        }
        return triple;
    }

    private final void checkProgressiveMode(LanguageVersion languageVersion, MessageCollector collector) {
        if (!this.progressiveMode || languageVersion.compareTo(LanguageVersion.LATEST_STABLE) >= 0 || this.suppressVersionWarnings) {
            return;
        }
        MessageCollector.report$default(collector, CompilerMessageSeverity.STRONG_WARNING, "'-progressive' is meaningful only for the latest language version (" + LanguageVersion.LATEST_STABLE + "), while this build uses " + languageVersion + "\nCompiler behavior in such mode is undefined; please, consider moving to the latest stable version or turning off progressive mode.", null, 4, null);
    }

    protected LanguageVersion defaultLanguageVersion(MessageCollector collector) {
        Intrinsics.checkNotNullParameter(collector, "collector");
        return LanguageVersion.LATEST_STABLE;
    }

    /* JADX INFO: compiled from: CommonCompilerArguments.kt */
    @Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0006\b\u0082\u0081\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u000f\b\u0002\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006j\u0002\b\u0007j\u0002\b\b¨\u0006\t"}, d2 = {"Lorg/jetbrains/kotlin/cli/common/arguments/CommonCompilerArguments$VersionKind;", "", "text", "", "(Ljava/lang/String;ILjava/lang/String;)V", "getText", "()Ljava/lang/String;", "LANGUAGE", "API", "cli-common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    private enum VersionKind {
        LANGUAGE("Language"),
        API("API");

        private static final /* synthetic */ EnumEntries $ENTRIES = EnumEntriesKt.enumEntries(values());
        private final String text;

        public static EnumEntries<VersionKind> getEntries() {
            return $ENTRIES;
        }

        VersionKind(String str) {
            this.text = str;
        }

        public final String getText() {
            return this.text;
        }
    }

    private final LanguageVersion parseOrConfigureLanguageVersion(MessageCollector collector) {
        String str;
        LanguageVersion version = parseVersion(collector, this.languageVersion, "language");
        LanguageVersion languageVersionDefaultLanguageVersion = version == null ? defaultLanguageVersion(collector) : version;
        if (this.useK2) {
            Boolean boolValueOf = version != null ? Boolean.valueOf(version.getUsesK2()) : null;
            if (Intrinsics.areEqual((Object) boolValueOf, (Object) true)) {
                str = "Deprecated compiler flag -Xuse-k2 is redundant because of \"-language-version " + version + "\" and should be removed";
            } else if (Intrinsics.areEqual((Object) boolValueOf, (Object) false)) {
                str = "Deprecated compiler flag -Xuse-k2 overrides \"-language-version " + version + "\" to 2.0; please remove -Xuse-k2 and use -language-version to select either " + version + " or 2.0";
            } else if (boolValueOf == null) {
                str = "Compiler flag -Xuse-k2 is deprecated; please use \"-language-version 2.0\" instead";
            } else {
                throw new NoWhenBranchMatchedException();
            }
            MessageCollector.report$default(collector, CompilerMessageSeverity.STRONG_WARNING, str, null, 4, null);
        }
        return (!this.useK2 || languageVersionDefaultLanguageVersion.getUsesK2()) ? languageVersionDefaultLanguageVersion : LanguageVersion.KOTLIN_2_0;
    }

    private final LanguageVersion parseVersion(MessageCollector collector, String value, String versionOf) {
        if (value == null) {
            return null;
        }
        LanguageVersion languageVersionFromVersionString = LanguageVersion.INSTANCE.fromVersionString(value);
        if (languageVersionFromVersionString != null) {
            return languageVersionFromVersionString;
        }
        LanguageVersion[] languageVersionArrValues = LanguageVersion.values();
        ArrayList arrayList = new ArrayList();
        for (LanguageVersion languageVersion : languageVersionArrValues) {
            if (!languageVersion.isUnsupported()) {
                arrayList.add(languageVersion);
            }
        }
        ArrayList arrayList2 = arrayList;
        ArrayList arrayList3 = new ArrayList(CollectionsKt.collectionSizeOrDefault(arrayList2, 10));
        Iterator it = arrayList2.iterator();
        while (it.hasNext()) {
            arrayList3.add(((LanguageVersion) it.next()).getDescription());
        }
        collector.report(CompilerMessageSeverity.ERROR, "Unknown " + versionOf + " version: " + value + "\nSupported " + versionOf + " versions: " + CollectionsKt.joinToString$default(arrayList3, ", ", null, null, 0, null, null, 62, null), null);
        return null;
    }

    /* JADX INFO: compiled from: CommonCompilerArguments.kt */
    @Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\b\u0010\u0003\u001a\u00020\u0004H\u0014¨\u0006\u0005"}, d2 = {"Lorg/jetbrains/kotlin/cli/common/arguments/CommonCompilerArguments$DummyImpl;", "Lorg/jetbrains/kotlin/cli/common/arguments/CommonCompilerArguments;", "()V", "copyOf", "Lorg/jetbrains/kotlin/cli/common/arguments/Freezable;", "cli-common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class DummyImpl extends CommonCompilerArguments {
        @Override // org.jetbrains.kotlin.cli.common.arguments.Freezable
        protected Freezable copyOf() {
            return CommonCompilerArgumentsCopyGeneratedKt.copyCommonCompilerArguments(this, new DummyImpl());
        }
    }
}
