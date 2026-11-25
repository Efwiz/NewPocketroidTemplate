package org.jetbrains.kotlin.js.translate.intrinsic.functions.factories;

import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.js.translate.context.TranslationContext;
import org.jetbrains.kotlin.js.translate.intrinsic.functions.basic.FunctionIntrinsic;

/* JADX INFO: loaded from: classes5.dex */
public interface FunctionIntrinsicFactory {
    FunctionIntrinsic getIntrinsic(FunctionDescriptor functionDescriptor, TranslationContext translationContext);
}
