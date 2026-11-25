package org.jetbrains.kotlin.scripting.definitions;

import kotlin.Metadata;
import kotlin.sequences.Sequence;
import org.jetbrains.kotlin.utils.PathUtil;

/* JADX INFO: compiled from: ScriptDefinitionsSource.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\bf\u0018\u00002\u00020\u0001R\u0018\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0005\u0010\u0006ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\u0007À\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/scripting/definitions/ScriptDefinitionsSource;", "", "definitions", "Lkotlin/sequences/Sequence;", "Lorg/jetbrains/kotlin/scripting/definitions/ScriptDefinition;", "getDefinitions", "()Lkotlin/sequences/Sequence;", PathUtil.KOTLIN_SCRIPTING_COMPILER_IMPL_NAME}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface ScriptDefinitionsSource {
    Sequence<ScriptDefinition> getDefinitions();
}
