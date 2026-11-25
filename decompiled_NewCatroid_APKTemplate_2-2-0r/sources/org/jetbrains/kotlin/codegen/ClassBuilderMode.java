package org.jetbrains.kotlin.codegen;

/* JADX INFO: loaded from: classes3.dex */
public class ClassBuilderMode {
    public final boolean generateBodies;
    public final boolean generateMetadata;
    public final boolean generateMultiFileFacadePartClasses;
    public final boolean generateSourceRetentionAnnotations;
    public final boolean mightBeIncorrectCode;
    public static final ClassBuilderMode FULL = new ClassBuilderMode(true, true, false, true, false);
    public static final ClassBuilderMode LIGHT_CLASSES = new ClassBuilderMode(false, false, true, false, true);
    public static final ClassBuilderMode KAPT3 = new ClassBuilderMode(false, true, true, true, true);
    private static final ClassBuilderMode LIGHT_ANALYSIS_FOR_TESTS = new ClassBuilderMode(false, true, false, true, true);

    private ClassBuilderMode(boolean z, boolean z2, boolean z3, boolean z4, boolean z5) {
        this.generateBodies = z;
        this.generateMetadata = z2;
        this.generateSourceRetentionAnnotations = z3;
        this.generateMultiFileFacadePartClasses = z4;
        this.mightBeIncorrectCode = z5;
    }

    public static ClassBuilderMode getLightAnalysisForTests() {
        return LIGHT_ANALYSIS_FOR_TESTS;
    }
}
