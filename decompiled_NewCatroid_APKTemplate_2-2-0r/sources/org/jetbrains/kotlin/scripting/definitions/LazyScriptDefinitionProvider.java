package org.jetbrains.kotlin.scripting.definitions;

import java.net.URI;
import java.util.HashMap;
import java.util.concurrent.locks.ReentrantLock;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.script.experimental.api.SourceCode;
import kotlin.script.experimental.host.ScriptingHostConfiguration;
import kotlin.script.experimental.jvm.JvmScriptingHostConfigurationKt;
import kotlin.sequences.Sequence;
import kotlin.sequences.SequencesKt;
import kotlin.text.StringsKt;
import org.eclipse.jdt.internal.compiler.util.SuffixConstants;
import org.jetbrains.kotlin.com.intellij.psi.PsiTreeChangeEvent;
import org.jetbrains.kotlin.idea.KotlinFileType;
import org.jetbrains.kotlin.utils.PathUtil;

/* JADX INFO: compiled from: LazyScriptDefinitionProvider.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000Z\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0005\b&\u0018\u0000 '2\u00020\u0001:\u0001'B\u0005¢\u0006\u0002\u0010\u0002J\b\u0010\u0015\u001a\u00020\u0016H\u0004J\b\u0010\u0017\u001a\u00020\u0016H\u0014J\u0012\u0010\u0018\u001a\u0004\u0018\u00010\u00052\u0006\u0010\u0019\u001a\u00020\u001aH\u0016J\u0012\u0010\u001b\u001a\u0004\u0018\u00010\u001c2\u0006\u0010\u001d\u001a\u00020\u001eH\u0016J\b\u0010\u001f\u001a\u00020\u0005H\u0016J\b\u0010 \u001a\u00020\u001cH\u0016J\u000e\u0010!\u001a\b\u0012\u0004\u0012\u00020\u001e0\u0004H\u0016J\b\u0010\"\u001a\u00020#H\u0014J\u0010\u0010$\u001a\u00020\u000e2\u0006\u0010\u0019\u001a\u00020\u001aH\u0016J\u0010\u0010%\u001a\u00020\u000e2\u0006\u0010&\u001a\u00020\u001eH\u0014R\u0016\u0010\u0003\u001a\n\u0012\u0004\u0012\u00020\u0005\u0018\u00010\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u001a\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00050\u00048BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u0007\u0010\bR\u000e\u0010\t\u001a\u00020\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u0018\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X¤\u0004¢\u0006\u0006\u001a\u0004\b\f\u0010\bR\u000e\u0010\r\u001a\u00020\u000eX\u0082\u000e¢\u0006\u0002\n\u0000R0\u0010\u000f\u001a\u001e\u0012\u0004\u0012\u00020\u0011\u0012\u0004\u0012\u00020\u00050\u0010j\u000e\u0012\u0004\u0012\u00020\u0011\u0012\u0004\u0012\u00020\u0005`\u0012X\u0084\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0014¨\u0006("}, d2 = {"Lorg/jetbrains/kotlin/scripting/definitions/LazyScriptDefinitionProvider;", "Lorg/jetbrains/kotlin/scripting/definitions/ScriptDefinitionProvider;", "()V", "_cachedDefinitions", "Lkotlin/sequences/Sequence;", "Lorg/jetbrains/kotlin/scripting/definitions/ScriptDefinition;", "cachedDefinitions", "getCachedDefinitions", "()Lkotlin/sequences/Sequence;", "cachedDefinitionsLock", "Ljava/util/concurrent/locks/ReentrantLock;", "currentDefinitions", "getCurrentDefinitions", "disposed", "", "fixedDefinitions", "Ljava/util/HashMap;", "Ljava/net/URI;", "Lkotlin/collections/HashMap;", "getFixedDefinitions", "()Ljava/util/HashMap;", "clearCache", "", "dispose", "findDefinition", "script", "Lkotlin/script/experimental/api/SourceCode;", "findScriptDefinition", "Lorg/jetbrains/kotlin/scripting/definitions/KotlinScriptDefinition;", PsiTreeChangeEvent.PROP_FILE_NAME, "", "getDefaultDefinition", "getDefaultScriptDefinition", "getKnownFilenameExtensions", "getScriptingHostConfiguration", "Lkotlin/script/experimental/host/ScriptingHostConfiguration;", "isScript", "nonScriptId", "locationId", "Companion", PathUtil.KOTLIN_SCRIPTING_COMPILER_IMPL_NAME}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class LazyScriptDefinitionProvider implements ScriptDefinitionProvider {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private static final String[] nonScriptFilenameSuffixes = {KotlinFileType.DOT_DEFAULT_EXTENSION, SuffixConstants.SUFFIX_STRING_java};
    private volatile Sequence<? extends ScriptDefinition> _cachedDefinitions;
    private volatile boolean disposed;
    private final ReentrantLock cachedDefinitionsLock = new ReentrantLock();
    private final HashMap<URI, ScriptDefinition> fixedDefinitions = new HashMap<>();

    protected abstract Sequence<ScriptDefinition> getCurrentDefinitions();

    protected ScriptingHostConfiguration getScriptingHostConfiguration() {
        return JvmScriptingHostConfigurationKt.getDefaultJvmScriptingHostConfiguration();
    }

    @Override // org.jetbrains.kotlin.scripting.definitions.ScriptDefinitionProvider
    public ScriptDefinition getDefaultDefinition() {
        return ScriptDefinition.INSTANCE.getDefault(getScriptingHostConfiguration());
    }

    protected final HashMap<URI, ScriptDefinition> getFixedDefinitions() {
        return this.fixedDefinitions;
    }

    /* JADX WARN: Multi-variable type inference failed */
    private final Sequence<ScriptDefinition> getCachedDefinitions() {
        Sequence sequence = this._cachedDefinitions;
        if (sequence != null) {
            return sequence;
        }
        this.cachedDefinitionsLock.getHoldCount();
        Sequence sequenceConstrainOnce = SequencesKt.constrainOnce(getCurrentDefinitions());
        ReentrantLock reentrantLock = this.cachedDefinitionsLock;
        reentrantLock.lock();
        try {
            Sequence sequenceEmptySequence = this._cachedDefinitions;
            if (sequenceEmptySequence == null) {
                if (!this.disposed) {
                    Sequence cachingSequence = new CachingSequence(sequenceConstrainOnce);
                    this._cachedDefinitions = cachingSequence;
                    sequenceEmptySequence = cachingSequence;
                } else {
                    sequenceEmptySequence = SequencesKt.emptySequence();
                }
            }
            return sequenceEmptySequence;
        } finally {
            reentrantLock.unlock();
        }
    }

    protected final void clearCache() {
        ReentrantLock reentrantLock = this.cachedDefinitionsLock;
        reentrantLock.lock();
        try {
            this._cachedDefinitions = null;
            Unit unit = Unit.INSTANCE;
        } finally {
            reentrantLock.unlock();
        }
    }

    protected void dispose() {
        this.disposed = true;
        clearCache();
    }

    protected boolean nonScriptId(String locationId) {
        Intrinsics.checkNotNullParameter(locationId, "locationId");
        for (String str : nonScriptFilenameSuffixes) {
            if (StringsKt.endsWith(locationId, str, true)) {
                return true;
            }
        }
        return false;
    }

    @Override // org.jetbrains.kotlin.scripting.definitions.ScriptDefinitionProvider
    public ScriptDefinition findDefinition(SourceCode script) {
        Intrinsics.checkNotNullParameter(script, "script");
        ScriptDefinition scriptDefinition = null;
        if (script.getLocationId() == null) {
            return null;
        }
        String locationId = script.getLocationId();
        Intrinsics.checkNotNull(locationId);
        if (nonScriptId(locationId)) {
            return null;
        }
        for (ScriptDefinition scriptDefinition2 : getCachedDefinitions()) {
            if (scriptDefinition2.isScript(script)) {
                scriptDefinition = scriptDefinition2;
                break;
            }
        }
        return scriptDefinition;
    }

    @Override // org.jetbrains.kotlin.scripting.definitions.ScriptDefinitionProvider
    public KotlinScriptDefinition findScriptDefinition(String fileName) {
        Intrinsics.checkNotNullParameter(fileName, "fileName");
        Object obj = null;
        if (nonScriptId(fileName)) {
            return null;
        }
        for (Object obj2 : SequencesKt.map(getCachedDefinitions(), new Function1<ScriptDefinition, KotlinScriptDefinition>() { // from class: org.jetbrains.kotlin.scripting.definitions.LazyScriptDefinitionProvider.findScriptDefinition.1
            @Override // kotlin.jvm.functions.Function1
            public final KotlinScriptDefinition invoke(ScriptDefinition it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return it.getLegacyDefinition();
            }
        })) {
            if (((KotlinScriptDefinition) obj2).isScript(fileName)) {
                obj = obj2;
                break;
            }
        }
        return (KotlinScriptDefinition) obj;
    }

    @Override // org.jetbrains.kotlin.scripting.definitions.ScriptDefinitionProvider
    public boolean isScript(SourceCode script) {
        Intrinsics.checkNotNullParameter(script, "script");
        return findDefinition(script) != null;
    }

    @Override // org.jetbrains.kotlin.scripting.definitions.ScriptDefinitionProvider
    public Sequence<String> getKnownFilenameExtensions() {
        return SequencesKt.map(getCachedDefinitions(), new Function1<ScriptDefinition, String>() { // from class: org.jetbrains.kotlin.scripting.definitions.LazyScriptDefinitionProvider.getKnownFilenameExtensions.1
            @Override // kotlin.jvm.functions.Function1
            public final String invoke(ScriptDefinition it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return it.getFileExtension();
            }
        });
    }

    @Override // org.jetbrains.kotlin.scripting.definitions.ScriptDefinitionProvider
    public KotlinScriptDefinition getDefaultScriptDefinition() {
        return getDefaultDefinition().getLegacyDefinition();
    }

    /* JADX INFO: compiled from: LazyScriptDefinitionProvider.kt */
    @Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0011\n\u0002\u0010\u000e\n\u0002\b\u0004\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u001c\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0084\u0004¢\u0006\n\n\u0002\u0010\b\u001a\u0004\b\u0006\u0010\u0007¨\u0006\t"}, d2 = {"Lorg/jetbrains/kotlin/scripting/definitions/LazyScriptDefinitionProvider$Companion;", "", "()V", "nonScriptFilenameSuffixes", "", "", "getNonScriptFilenameSuffixes", "()[Ljava/lang/String;", "[Ljava/lang/String;", PathUtil.KOTLIN_SCRIPTING_COMPILER_IMPL_NAME}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        protected final String[] getNonScriptFilenameSuffixes() {
            return LazyScriptDefinitionProvider.nonScriptFilenameSuffixes;
        }
    }
}
