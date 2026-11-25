package org.jetbrains.kotlin.fir.analysis.checkers.config;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function2;
import org.jetbrains.kotlin.fir.analysis.checkers.context.CheckerContext;

/* JADX INFO: compiled from: FirLanguageVersionSettingsChecker.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0002\u0010\u000e\n\u0000\b&\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J*\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0018\u0010\u0007\u001a\u0014\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\u00040\bH&¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/fir/analysis/checkers/config/FirLanguageVersionSettingsChecker;", "", "()V", "check", "", "context", "Lorg/jetbrains/kotlin/fir/analysis/checkers/context/CheckerContext;", "rawReport", "Lkotlin/Function2;", "", "", "checkers"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class FirLanguageVersionSettingsChecker {
    public abstract void check(CheckerContext context, Function2<? super Boolean, ? super String, Unit> rawReport);
}
