package org.jetbrains.kotlin.scripting.compiler.plugin.definitions;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.Sequence;
import kotlin.sequences.SequencesKt;
import org.jetbrains.kotlin.scripting.definitions.LazyScriptDefinitionProvider;
import org.jetbrains.kotlin.scripting.definitions.ScriptDefinition;
import org.jetbrains.kotlin.scripting.definitions.ScriptDefinitionsSource;
import org.jetbrains.kotlin.utils.PathUtil;

/* JADX INFO: compiled from: CliScriptDefinitionProvider.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010!\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010 \n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b\u0016\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\b\u0010\u000e\u001a\u00020\u0005H\u0016J\u0014\u0010\u000f\u001a\u00020\u00102\f\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00050\u0012J\u0014\u0010\u0013\u001a\u00020\u00102\f\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00150\u0012R\u001a\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u00048TX\u0094\u0004¢\u0006\u0006\u001a\u0004\b\u0006\u0010\u0007R\u0010\u0010\b\u001a\u0004\u0018\u00010\u0005X\u0082\u000e¢\u0006\u0002\n\u0000R\u0014\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00050\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\u000b\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\u00040\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\rX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0016"}, d2 = {"Lorg/jetbrains/kotlin/scripting/compiler/plugin/definitions/CliScriptDefinitionProvider;", "Lorg/jetbrains/kotlin/scripting/definitions/LazyScriptDefinitionProvider;", "()V", "currentDefinitions", "Lkotlin/sequences/Sequence;", "Lorg/jetbrains/kotlin/scripting/definitions/ScriptDefinition;", "getCurrentDefinitions", "()Lkotlin/sequences/Sequence;", "defaultDefinition", "definitions", "", "definitionsFromSources", "definitionsLock", "Ljava/util/concurrent/locks/ReentrantLock;", "getDefaultDefinition", "setScriptDefinitions", "", "newDefinitions", "", "setScriptDefinitionsSources", "newSources", "Lorg/jetbrains/kotlin/scripting/definitions/ScriptDefinitionsSource;", PathUtil.KOTLIN_SCRIPTING_COMPILER_PLUGIN_NAME}, k = 1, mv = {1, 9, 0}, xi = 48)
public class CliScriptDefinitionProvider extends LazyScriptDefinitionProvider {
    private ScriptDefinition defaultDefinition;
    private final ReentrantLock definitionsLock = new ReentrantLock();
    private final List<Sequence<ScriptDefinition>> definitionsFromSources = new ArrayList();
    private final List<ScriptDefinition> definitions = new ArrayList();

    @Override // org.jetbrains.kotlin.scripting.definitions.LazyScriptDefinitionProvider
    protected Sequence<ScriptDefinition> getCurrentDefinitions() {
        return SequencesKt.plus((Sequence<? extends ScriptDefinition>) SequencesKt.plus(CollectionsKt.asSequence(this.definitions), SequencesKt.flatMap(CollectionsKt.asSequence(this.definitionsFromSources), new Function1<Sequence<? extends ScriptDefinition>, Sequence<? extends ScriptDefinition>>() { // from class: org.jetbrains.kotlin.scripting.compiler.plugin.definitions.CliScriptDefinitionProvider$currentDefinitions$base$1
            /* JADX WARN: Multi-variable type inference failed */
            @Override // kotlin.jvm.functions.Function1
            public final Sequence<ScriptDefinition> invoke(Sequence<? extends ScriptDefinition> it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return it;
            }
        })), getDefaultDefinition());
    }

    public final void setScriptDefinitions(List<? extends ScriptDefinition> newDefinitions) {
        Intrinsics.checkNotNullParameter(newDefinitions, "newDefinitions");
        ReentrantLock reentrantLock = this.definitionsLock;
        reentrantLock.lock();
        try {
            this.definitions.clear();
            ArrayList arrayList = new ArrayList();
            ArrayList arrayList2 = new ArrayList();
            for (Object obj : newDefinitions) {
                if (!((ScriptDefinition) obj).getIsDefault()) {
                    arrayList.add(obj);
                } else {
                    arrayList2.add(obj);
                }
            }
            Pair pair = new Pair(arrayList, arrayList2);
            List list = (List) pair.component1();
            List list2 = (List) pair.component2();
            this.definitions.addAll(list);
            this.defaultDefinition = (ScriptDefinition) CollectionsKt.firstOrNull(list2);
            clearCache();
            Unit unit = Unit.INSTANCE;
        } finally {
            reentrantLock.unlock();
        }
    }

    public final void setScriptDefinitionsSources(List<? extends ScriptDefinitionsSource> newSources) {
        Intrinsics.checkNotNullParameter(newSources, "newSources");
        ReentrantLock reentrantLock = this.definitionsLock;
        reentrantLock.lock();
        try {
            this.definitionsFromSources.clear();
            Iterator<? extends ScriptDefinitionsSource> it = newSources.iterator();
            while (it.hasNext()) {
                this.definitionsFromSources.add(SequencesKt.constrainOnce(it.next().getDefinitions()));
            }
            clearCache();
            Unit unit = Unit.INSTANCE;
        } finally {
            reentrantLock.unlock();
        }
    }

    @Override // org.jetbrains.kotlin.scripting.definitions.LazyScriptDefinitionProvider, org.jetbrains.kotlin.scripting.definitions.ScriptDefinitionProvider
    public ScriptDefinition getDefaultDefinition() {
        ScriptDefinition scriptDefinition = this.defaultDefinition;
        return scriptDefinition == null ? super.getDefaultDefinition() : scriptDefinition;
    }
}
