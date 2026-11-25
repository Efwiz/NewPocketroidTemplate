package org.jetbrains.kotlin.resolve.calls.smartcasts;

import java.util.Set;
import javaslang.collection.Map;
import kotlin.Metadata;
import org.antlr.stringtemplate.language.ASTExpr;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.config.LanguageVersionSettings;
import org.jetbrains.kotlin.load.java.JvmAnnotationNames;
import org.jetbrains.kotlin.types.KotlinType;

/* JADX INFO: compiled from: DataFlowInfo.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000F\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0010\"\n\u0002\b\u0005\bf\u0018\u0000 $2\u00020\u0001:\u0001$J\u0010\u0010\u000e\u001a\u00020\u00002\u0006\u0010\u000f\u001a\u00020\u0000H&J \u0010\u0010\u001a\u00020\u00002\u0006\u0010\u0011\u001a\u00020\u00042\u0006\u0010\u0012\u001a\u00020\u00042\u0006\u0010\u0013\u001a\u00020\u0014H&J\u0018\u0010\u0015\u001a\u00020\u00002\u0006\u0010\u0016\u001a\u00020\u00042\u0006\u0010\u0013\u001a\u00020\u0014H&J \u0010\u0017\u001a\u00020\u00002\u0006\u0010\u0011\u001a\u00020\u00042\u0006\u0010\u0012\u001a\u00020\u00042\u0006\u0010\u0013\u001a\u00020\u0014H&J(\u0010\u0018\u001a\u00020\u00002\u0006\u0010\u0011\u001a\u00020\u00042\u0006\u0010\u0012\u001a\u00020\u00042\u0006\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u0013\u001a\u00020\u0014H&J \u0010\u001b\u001a\u00020\u00002\u0006\u0010\u0016\u001a\u00020\u00042\u0006\u0010\u001c\u001a\u00020\u000b2\u0006\u0010\u0013\u001a\u00020\u0014H&J\u0010\u0010\u001d\u001a\u00020\u00052\u0006\u0010\u001e\u001a\u00020\u0004H&J\u001e\u0010\u001f\u001a\b\u0012\u0004\u0012\u00020\u000b0 2\u0006\u0010\u001e\u001a\u00020\u00042\u0006\u0010\u0013\u001a\u00020\u0014H&J\u0010\u0010!\u001a\u00020\u00052\u0006\u0010\u001e\u001a\u00020\u0004H&J\u001e\u0010\"\u001a\b\u0012\u0004\u0012\u00020\u000b0 2\u0006\u0010\u001e\u001a\u00020\u00042\u0006\u0010\u0013\u001a\u00020\u0014H&J\u0010\u0010#\u001a\u00020\u00002\u0006\u0010\u000f\u001a\u00020\u0000H&R.\u0010\u0002\u001a\u001e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00050\u0003j\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u0005`\u0006X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0007\u0010\bRD\u0010\t\u001a4\u0012\u0004\u0012\u00020\u0004\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000b0\n0\u0003j\u001e\u0012\u0004\u0012\u00020\u0004\u0012\u0014\u0012\u0012\u0012\u0004\u0012\u00020\u000b0\nj\b\u0012\u0004\u0012\u00020\u000b`\f`\u0006X¦\u0004¢\u0006\u0006\u001a\u0004\b\r\u0010\bø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006%À\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/smartcasts/DataFlowInfo;", "", "completeNullabilityInfo", "Ljavaslang/collection/Map;", "Lorg/jetbrains/kotlin/resolve/calls/smartcasts/DataFlowValue;", "Lorg/jetbrains/kotlin/resolve/calls/smartcasts/Nullability;", "Lorg/jetbrains/kotlin/util/javaslang/ImmutableMap;", "getCompleteNullabilityInfo", "()Ljavaslang/collection/Map;", "completeTypeInfo", "Ljavaslang/collection/Set;", "Lorg/jetbrains/kotlin/types/KotlinType;", "Lorg/jetbrains/kotlin/util/javaslang/ImmutableSet;", "getCompleteTypeInfo", "and", "other", "assign", "a", JvmAnnotationNames.SERIALIZED_IR_BYTES_FIELD_NAME, "languageVersionSettings", "Lorg/jetbrains/kotlin/config/LanguageVersionSettings;", "clearValueInfo", "value", "disequate", "equate", "identityEquals", "", "establishSubtyping", ModuleXmlParser.TYPE, "getCollectedNullability", ASTExpr.DEFAULT_MAP_KEY_NAME, "getCollectedTypes", "", "getStableNullability", "getStableTypes", "or", "Companion", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface DataFlowInfo {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = Companion.$$INSTANCE;

    DataFlowInfo and(DataFlowInfo other);

    DataFlowInfo assign(DataFlowValue a, DataFlowValue b, LanguageVersionSettings languageVersionSettings);

    DataFlowInfo clearValueInfo(DataFlowValue value, LanguageVersionSettings languageVersionSettings);

    DataFlowInfo disequate(DataFlowValue a, DataFlowValue b, LanguageVersionSettings languageVersionSettings);

    DataFlowInfo equate(DataFlowValue a, DataFlowValue b, boolean identityEquals, LanguageVersionSettings languageVersionSettings);

    DataFlowInfo establishSubtyping(DataFlowValue value, KotlinType type, LanguageVersionSettings languageVersionSettings);

    Nullability getCollectedNullability(DataFlowValue key);

    Set<KotlinType> getCollectedTypes(DataFlowValue key, LanguageVersionSettings languageVersionSettings);

    Map<DataFlowValue, Nullability> getCompleteNullabilityInfo();

    Map<DataFlowValue, javaslang.collection.Set<KotlinType>> getCompleteTypeInfo();

    Nullability getStableNullability(DataFlowValue key);

    Set<KotlinType> getStableTypes(DataFlowValue key, LanguageVersionSettings languageVersionSettings);

    DataFlowInfo or(DataFlowInfo other);

    /* JADX INFO: compiled from: DataFlowInfo.kt */
    @Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/smartcasts/DataFlowInfo$Companion;", "", "()V", "EMPTY", "Lorg/jetbrains/kotlin/resolve/calls/smartcasts/DataFlowInfo;", "getEMPTY", "()Lorg/jetbrains/kotlin/resolve/calls/smartcasts/DataFlowInfo;", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        static final /* synthetic */ Companion $$INSTANCE = new Companion();
        private static final DataFlowInfo EMPTY = DataFlowInfoFactory.EMPTY;

        private Companion() {
        }

        public final DataFlowInfo getEMPTY() {
            return EMPTY;
        }
    }
}
