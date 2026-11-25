package org.jetbrains.kotlin.scripting.definitions;

import kotlin.Metadata;
import kotlin.jvm.internal.Reflection;
import kotlin.script.templates.standard.ScriptTemplateWithArgs;
import org.jetbrains.kotlin.utils.PathUtil;

/* JADX INFO: compiled from: KotlinScriptDefinition.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/scripting/definitions/StandardScriptDefinition;", "Lorg/jetbrains/kotlin/scripting/definitions/KotlinScriptDefinition;", "()V", PathUtil.KOTLIN_SCRIPTING_COMPILER_IMPL_NAME}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class StandardScriptDefinition extends KotlinScriptDefinition {
    public static final StandardScriptDefinition INSTANCE = new StandardScriptDefinition();

    private StandardScriptDefinition() {
        super(Reflection.getOrCreateKotlinClass(ScriptTemplateWithArgs.class));
    }
}
