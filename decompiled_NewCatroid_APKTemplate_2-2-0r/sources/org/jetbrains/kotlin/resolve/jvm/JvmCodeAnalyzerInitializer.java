package org.jetbrains.kotlin.resolve.jvm;

import kotlin.Metadata;
import org.jetbrains.kotlin.config.JvmTarget;
import org.jetbrains.kotlin.config.LanguageVersionSettings;
import org.jetbrains.kotlin.descriptors.ModuleDescriptor;
import org.jetbrains.kotlin.resolve.BindingTrace;
import org.jetbrains.kotlin.resolve.CodeAnalyzerInitializer;
import org.jetbrains.kotlin.resolve.lazy.KotlinCodeAnalyzer;

/* JADX INFO: compiled from: JvmCodeAnalyzerInitializer.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b&\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J0\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000eH&¨\u0006\u000f"}, d2 = {"Lorg/jetbrains/kotlin/resolve/jvm/JvmCodeAnalyzerInitializer;", "Lorg/jetbrains/kotlin/resolve/CodeAnalyzerInitializer;", "()V", "initialize", "", "trace", "Lorg/jetbrains/kotlin/resolve/BindingTrace;", "module", "Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;", "codeAnalyzer", "Lorg/jetbrains/kotlin/resolve/lazy/KotlinCodeAnalyzer;", "languageVersionSettings", "Lorg/jetbrains/kotlin/config/LanguageVersionSettings;", "jvmTarget", "Lorg/jetbrains/kotlin/config/JvmTarget;", "frontend.java"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class JvmCodeAnalyzerInitializer implements CodeAnalyzerInitializer {
    public abstract void initialize(BindingTrace trace, ModuleDescriptor module, KotlinCodeAnalyzer codeAnalyzer, LanguageVersionSettings languageVersionSettings, JvmTarget jvmTarget);
}
