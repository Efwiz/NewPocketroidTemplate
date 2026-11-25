package org.jetbrains.kotlin.resolve.calls.components;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.config.LanguageFeature;
import org.jetbrains.kotlin.config.LanguageVersionSettings;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.descriptors.CallableMemberDescriptor;
import org.jetbrains.kotlin.descriptors.ValueParameterDescriptor;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.resolve.calls.model.ArgumentPassedTwice;
import org.jetbrains.kotlin.resolve.calls.model.KotlinCall;
import org.jetbrains.kotlin.resolve.calls.model.KotlinCallArgument;
import org.jetbrains.kotlin.resolve.calls.model.KotlinCallDiagnostic;
import org.jetbrains.kotlin.resolve.calls.model.MixingNamedAndPositionArguments;
import org.jetbrains.kotlin.resolve.calls.model.NameForAmbiguousParameter;
import org.jetbrains.kotlin.resolve.calls.model.NameNotFound;
import org.jetbrains.kotlin.resolve.calls.model.NamedArgumentNotAllowed;
import org.jetbrains.kotlin.resolve.calls.model.NamedArgumentReference;
import org.jetbrains.kotlin.resolve.calls.model.NoValueForParameter;
import org.jetbrains.kotlin.resolve.calls.model.NonVarargSpread;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedCallArgument;
import org.jetbrains.kotlin.resolve.calls.model.TooManyArguments;
import org.jetbrains.kotlin.resolve.calls.model.VarargArgumentOutsideParentheses;
import org.jetbrains.kotlin.utils.addToStdlib.AddToStdlibKt;

/* JADX INFO: compiled from: ArgumentsToParametersMapper.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u0001:\u0002\u0014\u0015B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J&\u0010\u000b\u001a\u00020\u00062\f\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u000e0\r2\b\u0010\u000f\u001a\u0004\u0018\u00010\u000e2\u0006\u0010\u0010\u001a\u00020\u0011J\u0016\u0010\u000b\u001a\u00020\u00062\u0006\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0010\u001a\u00020\u0011R\u0011\u0010\u0005\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u000e\u0010\t\u001a\u00020\nX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0016"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/components/ArgumentsToParametersMapper;", "", "languageVersionSettings", "Lorg/jetbrains/kotlin/config/LanguageVersionSettings;", "(Lorg/jetbrains/kotlin/config/LanguageVersionSettings;)V", "EmptyArgumentMapping", "Lorg/jetbrains/kotlin/resolve/calls/components/ArgumentsToParametersMapper$ArgumentMapping;", "getEmptyArgumentMapping", "()Lorg/jetbrains/kotlin/resolve/calls/components/ArgumentsToParametersMapper$ArgumentMapping;", "allowMixedNamedAndPositionArguments", "", "mapArguments", "argumentsInParenthesis", "", "Lorg/jetbrains/kotlin/resolve/calls/model/KotlinCallArgument;", "externalArgument", "descriptor", "Lorg/jetbrains/kotlin/descriptors/CallableDescriptor;", "call", "Lorg/jetbrains/kotlin/resolve/calls/model/KotlinCall;", "ArgumentMapping", "CallArgumentProcessor", "resolution"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ArgumentsToParametersMapper {
    private final ArgumentMapping EmptyArgumentMapping;
    private final boolean allowMixedNamedAndPositionArguments;

    public ArgumentsToParametersMapper(LanguageVersionSettings languageVersionSettings) {
        Intrinsics.checkNotNullParameter(languageVersionSettings, "languageVersionSettings");
        this.allowMixedNamedAndPositionArguments = languageVersionSettings.supportsFeature(LanguageFeature.MixedNamedArgumentsInTheirOwnPosition);
        this.EmptyArgumentMapping = new ArgumentMapping(MapsKt.emptyMap(), CollectionsKt.emptyList());
    }

    /* JADX INFO: compiled from: ArgumentsToParametersMapper.kt */
    @Metadata(d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B'\u0012\u0012\u0010\u0002\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00050\u0003\u0012\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u0007¢\u0006\u0002\u0010\tJ\u0015\u0010\u000e\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00050\u0003HÆ\u0003J\u000f\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\b0\u0007HÆ\u0003J/\u0010\u0010\u001a\u00020\u00002\u0014\b\u0002\u0010\u0002\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00050\u00032\u000e\b\u0002\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u0007HÆ\u0001J\u0013\u0010\u0011\u001a\u00020\u00122\b\u0010\u0013\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0014\u001a\u00020\u0015HÖ\u0001J\t\u0010\u0016\u001a\u00020\u0017HÖ\u0001R\u0017\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u0007¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u001d\u0010\u0002\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00050\u0003¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\r¨\u0006\u0018"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/components/ArgumentsToParametersMapper$ArgumentMapping;", "", "parameterToCallArgumentMap", "", "Lorg/jetbrains/kotlin/descriptors/ValueParameterDescriptor;", "Lorg/jetbrains/kotlin/resolve/calls/model/ResolvedCallArgument;", "diagnostics", "", "Lorg/jetbrains/kotlin/resolve/calls/model/KotlinCallDiagnostic;", "(Ljava/util/Map;Ljava/util/List;)V", "getDiagnostics", "()Ljava/util/List;", "getParameterToCallArgumentMap", "()Ljava/util/Map;", "component1", "component2", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "hashCode", "", "toString", "", "resolution"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final /* data */ class ArgumentMapping {
        private final List<KotlinCallDiagnostic> diagnostics;
        private final Map<ValueParameterDescriptor, ResolvedCallArgument> parameterToCallArgumentMap;

        /* JADX WARN: Multi-variable type inference failed */
        public static /* synthetic */ ArgumentMapping copy$default(ArgumentMapping argumentMapping, Map map, List list, int i, Object obj) {
            if ((i & 1) != 0) {
                map = argumentMapping.parameterToCallArgumentMap;
            }
            if ((i & 2) != 0) {
                list = argumentMapping.diagnostics;
            }
            return argumentMapping.copy(map, list);
        }

        public final Map<ValueParameterDescriptor, ResolvedCallArgument> component1() {
            return this.parameterToCallArgumentMap;
        }

        public final List<KotlinCallDiagnostic> component2() {
            return this.diagnostics;
        }

        public final ArgumentMapping copy(Map<ValueParameterDescriptor, ? extends ResolvedCallArgument> parameterToCallArgumentMap, List<? extends KotlinCallDiagnostic> diagnostics) {
            Intrinsics.checkNotNullParameter(parameterToCallArgumentMap, "parameterToCallArgumentMap");
            Intrinsics.checkNotNullParameter(diagnostics, "diagnostics");
            return new ArgumentMapping(parameterToCallArgumentMap, diagnostics);
        }

        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof ArgumentMapping)) {
                return false;
            }
            ArgumentMapping argumentMapping = (ArgumentMapping) other;
            return Intrinsics.areEqual(this.parameterToCallArgumentMap, argumentMapping.parameterToCallArgumentMap) && Intrinsics.areEqual(this.diagnostics, argumentMapping.diagnostics);
        }

        public int hashCode() {
            return (this.parameterToCallArgumentMap.hashCode() * 31) + this.diagnostics.hashCode();
        }

        public String toString() {
            return "ArgumentMapping(parameterToCallArgumentMap=" + this.parameterToCallArgumentMap + ", diagnostics=" + this.diagnostics + ')';
        }

        /* JADX WARN: Multi-variable type inference failed */
        public ArgumentMapping(Map<ValueParameterDescriptor, ? extends ResolvedCallArgument> parameterToCallArgumentMap, List<? extends KotlinCallDiagnostic> diagnostics) {
            Intrinsics.checkNotNullParameter(parameterToCallArgumentMap, "parameterToCallArgumentMap");
            Intrinsics.checkNotNullParameter(diagnostics, "diagnostics");
            this.parameterToCallArgumentMap = parameterToCallArgumentMap;
            this.diagnostics = diagnostics;
        }

        public final Map<ValueParameterDescriptor, ResolvedCallArgument> getParameterToCallArgumentMap() {
            return this.parameterToCallArgumentMap;
        }

        public final List<KotlinCallDiagnostic> getDiagnostics() {
            return this.diagnostics;
        }
    }

    public final ArgumentMapping getEmptyArgumentMapping() {
        return this.EmptyArgumentMapping;
    }

    public final ArgumentMapping mapArguments(KotlinCall call, CallableDescriptor descriptor) {
        Intrinsics.checkNotNullParameter(call, "call");
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        return mapArguments(call.getArgumentsInParenthesis(), call.getExternalArgument(), descriptor);
    }

    public final ArgumentMapping mapArguments(List<? extends KotlinCallArgument> argumentsInParenthesis, KotlinCallArgument externalArgument, CallableDescriptor descriptor) {
        Intrinsics.checkNotNullParameter(argumentsInParenthesis, "argumentsInParenthesis");
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        if (argumentsInParenthesis.isEmpty() && externalArgument == null && descriptor.getValueParameters().isEmpty()) {
            return this.EmptyArgumentMapping;
        }
        CallArgumentProcessor callArgumentProcessor = new CallArgumentProcessor(descriptor, this.allowMixedNamedAndPositionArguments);
        callArgumentProcessor.processArgumentsInParenthesis(argumentsInParenthesis);
        if (externalArgument != null) {
            callArgumentProcessor.processExternalArgument(externalArgument);
        }
        callArgumentProcessor.processDefaultsAndRunChecks();
        return new ArgumentMapping(AddToStdlibKt.compactIfPossible(callArgumentProcessor.getResult()), callArgumentProcessor.getDiagnostics());
    }

    /* JADX INFO: compiled from: ArgumentsToParametersMapper.kt */
    @Metadata(d1 = {"\u0000b\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\b\u0003\n\u0002\u0010%\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0012\b\u0002\u0018\u00002\u00020\u0001:\u00013B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\u0010\u0010!\u001a\u00020\"2\u0006\u0010#\u001a\u00020\rH\u0002J\u0010\u0010$\u001a\u00020\"2\u0006\u0010%\u001a\u00020 H\u0002J\b\u0010&\u001a\u00020\"H\u0002J\u001a\u0010'\u001a\u0004\u0018\u00010\u00132\u0006\u0010%\u001a\u00020 2\u0006\u0010(\u001a\u00020\u0012H\u0002J\f\u0010)\u001a\b\u0012\u0004\u0012\u00020\r0\u0015J\u0012\u0010*\u001a\u0004\u0018\u00010\u00132\u0006\u0010(\u001a\u00020\u0012H\u0002J\u0014\u0010+\u001a\u00020\"2\f\u0010,\u001a\b\u0012\u0004\u0012\u00020 0\u0015J\u0006\u0010-\u001a\u00020\"J\u000e\u0010.\u001a\u00020\"2\u0006\u0010/\u001a\u00020 J\u0018\u00100\u001a\u00020\"2\u0006\u0010%\u001a\u00020 2\u0006\u0010(\u001a\u00020\u0012H\u0002J\u0010\u00101\u001a\u00020\u00052\u0006\u0010%\u001a\u00020 H\u0002J\u000e\u00102\u001a\u0004\u0018\u00010\u0013*\u00020\u0013H\u0002R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u000e¢\u0006\u0002\n\u0000R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0016\u0010\u000b\u001a\n\u0012\u0004\u0012\u00020\r\u0018\u00010\fX\u0082\u000e¢\u0006\u0002\n\u0000R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u001c\u0010\u0010\u001a\u0010\u0012\u0004\u0012\u00020\u0012\u0012\u0004\u0012\u00020\u0013\u0018\u00010\u0011X\u0082\u000e¢\u0006\u0002\n\u0000R\u001a\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00130\u00158BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u0016\u0010\u0017R\u001d\u0010\u0018\u001a\u000e\u0012\u0004\u0012\u00020\u0013\u0012\u0004\u0012\u00020\u001a0\u0019¢\u0006\b\n\u0000\u001a\u0004\b\u001b\u0010\u001cR\u000e\u0010\u001d\u001a\u00020\u001eX\u0082\u000e¢\u0006\u0002\n\u0000R\u0016\u0010\u001f\u001a\n\u0012\u0004\u0012\u00020 \u0018\u00010\fX\u0082\u000e¢\u0006\u0002\n\u0000¨\u00064"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/components/ArgumentsToParametersMapper$CallArgumentProcessor;", "", "descriptor", "Lorg/jetbrains/kotlin/descriptors/CallableDescriptor;", "languageSettingsAllowMixedNamedAndPositionArguments", "", "(Lorg/jetbrains/kotlin/descriptors/CallableDescriptor;Z)V", "currentPositionedParameterIndex", "", "getDescriptor", "()Lorg/jetbrains/kotlin/descriptors/CallableDescriptor;", "diagnostics", "", "Lorg/jetbrains/kotlin/resolve/calls/model/KotlinCallDiagnostic;", "getLanguageSettingsAllowMixedNamedAndPositionArguments", "()Z", "nameToParameter", "", "Lorg/jetbrains/kotlin/name/Name;", "Lorg/jetbrains/kotlin/descriptors/ValueParameterDescriptor;", "parameters", "", "getParameters", "()Ljava/util/List;", "result", "", "Lorg/jetbrains/kotlin/resolve/calls/model/ResolvedCallArgument;", "getResult", "()Ljava/util/Map;", "state", "Lorg/jetbrains/kotlin/resolve/calls/components/ArgumentsToParametersMapper$CallArgumentProcessor$State;", "varargArguments", "Lorg/jetbrains/kotlin/resolve/calls/model/KotlinCallArgument;", "addDiagnostic", "", "diagnostic", "addVarargArgument", "argument", "completeVarargPositionArguments", "findParameterByName", "name", "getDiagnostics", "getParameterByName", "processArgumentsInParenthesis", "arguments", "processDefaultsAndRunChecks", "processExternalArgument", "externalArgument", "processNamedArgument", "processPositionArgument", "getOverriddenParameterWithOtherName", "State", "resolution"}, k = 1, mv = {1, 9, 0}, xi = 48)
    private static final class CallArgumentProcessor {
        private int currentPositionedParameterIndex;
        private final CallableDescriptor descriptor;
        private List<KotlinCallDiagnostic> diagnostics;
        private final boolean languageSettingsAllowMixedNamedAndPositionArguments;
        private Map<Name, ? extends ValueParameterDescriptor> nameToParameter;
        private final Map<ValueParameterDescriptor, ResolvedCallArgument> result;
        private State state;
        private List<KotlinCallArgument> varargArguments;

        /* JADX INFO: compiled from: ArgumentsToParametersMapper.kt */
        @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0005\b\u0082\u0081\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004j\u0002\b\u0005¨\u0006\u0006"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/components/ArgumentsToParametersMapper$CallArgumentProcessor$State;", "", "(Ljava/lang/String;I)V", "POSITION_ARGUMENTS", "VARARG_POSITION", "NAMED_ONLY_ARGUMENTS", "resolution"}, k = 1, mv = {1, 9, 0}, xi = 48)
        private enum State {
            POSITION_ARGUMENTS,
            VARARG_POSITION,
            NAMED_ONLY_ARGUMENTS;

            private static final /* synthetic */ EnumEntries $ENTRIES = EnumEntriesKt.enumEntries(values());

            public static EnumEntries<State> getEntries() {
                return $ENTRIES;
            }
        }

        public CallArgumentProcessor(CallableDescriptor descriptor, boolean z) {
            Intrinsics.checkNotNullParameter(descriptor, "descriptor");
            this.descriptor = descriptor;
            this.languageSettingsAllowMixedNamedAndPositionArguments = z;
            this.result = new LinkedHashMap();
            this.state = State.POSITION_ARGUMENTS;
        }

        public final CallableDescriptor getDescriptor() {
            return this.descriptor;
        }

        public final boolean getLanguageSettingsAllowMixedNamedAndPositionArguments() {
            return this.languageSettingsAllowMixedNamedAndPositionArguments;
        }

        public final Map<ValueParameterDescriptor, ResolvedCallArgument> getResult() {
            return this.result;
        }

        private final List<ValueParameterDescriptor> getParameters() {
            List<ValueParameterDescriptor> valueParameters = this.descriptor.getValueParameters();
            Intrinsics.checkNotNullExpressionValue(valueParameters, "getValueParameters(...)");
            return valueParameters;
        }

        private final void addDiagnostic(KotlinCallDiagnostic diagnostic) {
            if (this.diagnostics == null) {
                this.diagnostics = new ArrayList();
            }
            List<KotlinCallDiagnostic> list = this.diagnostics;
            Intrinsics.checkNotNull(list);
            list.add(diagnostic);
        }

        public final List<KotlinCallDiagnostic> getDiagnostics() {
            List<KotlinCallDiagnostic> list = this.diagnostics;
            return list == null ? CollectionsKt.emptyList() : list;
        }

        private final ValueParameterDescriptor getParameterByName(Name name2) {
            if (this.nameToParameter == null) {
                List<ValueParameterDescriptor> parameters = getParameters();
                LinkedHashMap linkedHashMap = new LinkedHashMap(RangesKt.coerceAtLeast(MapsKt.mapCapacity(CollectionsKt.collectionSizeOrDefault(parameters, 10)), 16));
                for (Object obj : parameters) {
                    Name name3 = ((ValueParameterDescriptor) obj).getShortName();
                    Intrinsics.checkNotNullExpressionValue(name3, "getName(...)");
                    linkedHashMap.put(name3, obj);
                }
                this.nameToParameter = linkedHashMap;
            }
            Map<Name, ? extends ValueParameterDescriptor> map = this.nameToParameter;
            Intrinsics.checkNotNull(map);
            return map.get(name2);
        }

        private final void addVarargArgument(KotlinCallArgument argument) {
            if (this.varargArguments == null) {
                this.varargArguments = new ArrayList();
            }
            List<KotlinCallArgument> list = this.varargArguments;
            Intrinsics.checkNotNull(list);
            list.add(argument);
        }

        private final void completeVarargPositionArguments() {
            State state = State.VARARG_POSITION;
            ValueParameterDescriptor valueParameterDescriptor = getParameters().get(this.currentPositionedParameterIndex);
            Map<ValueParameterDescriptor, ResolvedCallArgument> map = this.result;
            ValueParameterDescriptor original = valueParameterDescriptor.getOriginal();
            List<KotlinCallArgument> list = this.varargArguments;
            Intrinsics.checkNotNull(list);
            map.put(original, new ResolvedCallArgument.VarargArgument(list));
        }

        private final boolean processPositionArgument(KotlinCallArgument argument) {
            if (this.state == State.NAMED_ONLY_ARGUMENTS) {
                addDiagnostic(new MixingNamedAndPositionArguments(argument));
                return false;
            }
            ValueParameterDescriptor valueParameterDescriptor = (ValueParameterDescriptor) CollectionsKt.getOrNull(getParameters(), this.currentPositionedParameterIndex);
            if (valueParameterDescriptor == null) {
                addDiagnostic(new TooManyArguments(argument, this.descriptor));
                return false;
            }
            if (!ArgumentsUtilsKt.isVararg(valueParameterDescriptor)) {
                this.currentPositionedParameterIndex++;
                this.result.put(valueParameterDescriptor.getOriginal(), new ResolvedCallArgument.SimpleArgument(argument));
                return false;
            }
            addVarargArgument(argument);
            return true;
        }

        private final void processNamedArgument(KotlinCallArgument argument, Name name2) {
            if (!this.descriptor.hasStableParameterNames()) {
                addDiagnostic(new NamedArgumentNotAllowed(argument, this.descriptor));
            }
            boolean z = this.state != State.NAMED_ONLY_ARGUMENTS;
            this.state = State.NAMED_ONLY_ARGUMENTS;
            ValueParameterDescriptor valueParameterDescriptorFindParameterByName = findParameterByName(argument, name2);
            if (valueParameterDescriptorFindParameterByName == null) {
                return;
            }
            addDiagnostic(new NamedArgumentReference(argument, valueParameterDescriptorFindParameterByName));
            ResolvedCallArgument resolvedCallArgument = this.result.get(valueParameterDescriptorFindParameterByName.getOriginal());
            if (resolvedCallArgument != null) {
                addDiagnostic(new ArgumentPassedTwice(argument, valueParameterDescriptorFindParameterByName, resolvedCallArgument));
                return;
            }
            this.result.put(valueParameterDescriptorFindParameterByName.getOriginal(), new ResolvedCallArgument.SimpleArgument(argument));
            if (z && this.languageSettingsAllowMixedNamedAndPositionArguments) {
                ValueParameterDescriptor valueParameterDescriptor = (ValueParameterDescriptor) CollectionsKt.getOrNull(getParameters(), this.currentPositionedParameterIndex);
                if (Intrinsics.areEqual(valueParameterDescriptor != null ? valueParameterDescriptor.getOriginal() : null, valueParameterDescriptorFindParameterByName.getOriginal())) {
                    this.state = State.POSITION_ARGUMENTS;
                    this.currentPositionedParameterIndex++;
                }
            }
        }

        private final ValueParameterDescriptor getOverriddenParameterWithOtherName(ValueParameterDescriptor valueParameterDescriptor) {
            Object next;
            Iterator<T> it = valueParameterDescriptor.getOverriddenDescriptors().iterator();
            while (it.hasNext()) {
                next = it.next();
                ValueParameterDescriptor valueParameterDescriptor2 = (ValueParameterDescriptor) next;
                if (valueParameterDescriptor2.getContainingDeclaration().hasStableParameterNames() && !Intrinsics.areEqual(valueParameterDescriptor2.getShortName(), valueParameterDescriptor.getShortName())) {
                    return (ValueParameterDescriptor) next;
                }
            }
            next = null;
            return (ValueParameterDescriptor) next;
        }

        private final ValueParameterDescriptor findParameterByName(KotlinCallArgument argument, Name name2) {
            Object next;
            ValueParameterDescriptor parameterByName = getParameterByName(name2);
            CallableDescriptor callableDescriptor = this.descriptor;
            if ((callableDescriptor instanceof CallableMemberDescriptor) && ((CallableMemberDescriptor) callableDescriptor).getKind() == CallableMemberDescriptor.Kind.FAKE_OVERRIDE) {
                if (parameterByName == null) {
                    for (ValueParameterDescriptor valueParameterDescriptor : ((CallableMemberDescriptor) this.descriptor).getValueParameters()) {
                        Iterator<T> it = valueParameterDescriptor.getOverriddenDescriptors().iterator();
                        while (true) {
                            if (!it.hasNext()) {
                                next = null;
                                break;
                            }
                            next = it.next();
                            ValueParameterDescriptor valueParameterDescriptor2 = (ValueParameterDescriptor) next;
                            if (valueParameterDescriptor2.getContainingDeclaration().hasStableParameterNames() && Intrinsics.areEqual(valueParameterDescriptor2.getShortName(), name2)) {
                                break;
                            }
                        }
                        ValueParameterDescriptor valueParameterDescriptor3 = (ValueParameterDescriptor) next;
                        if (valueParameterDescriptor3 != null) {
                            Intrinsics.checkNotNull(valueParameterDescriptor);
                            addDiagnostic(new NamedArgumentReference(argument, valueParameterDescriptor));
                            addDiagnostic(new NameForAmbiguousParameter(argument, valueParameterDescriptor, valueParameterDescriptor3));
                            return valueParameterDescriptor;
                        }
                    }
                } else {
                    ValueParameterDescriptor overriddenParameterWithOtherName = getOverriddenParameterWithOtherName(parameterByName);
                    if (overriddenParameterWithOtherName != null) {
                        addDiagnostic(new NameForAmbiguousParameter(argument, parameterByName, overriddenParameterWithOtherName));
                    }
                }
            }
            if (parameterByName == null) {
                addDiagnostic(new NameNotFound(argument, this.descriptor));
            }
            return parameterByName;
        }

        public final void processArgumentsInParenthesis(List<? extends KotlinCallArgument> arguments) {
            Intrinsics.checkNotNullParameter(arguments, "arguments");
            for (KotlinCallArgument kotlinCallArgument : arguments) {
                Name argumentName = kotlinCallArgument.getArgumentName();
                if (argumentName == null) {
                    if (processPositionArgument(kotlinCallArgument)) {
                        this.state = State.VARARG_POSITION;
                    }
                } else {
                    if (this.state == State.VARARG_POSITION) {
                        completeVarargPositionArguments();
                    }
                    processNamedArgument(kotlinCallArgument, argumentName);
                }
            }
            if (this.state == State.VARARG_POSITION) {
                completeVarargPositionArguments();
            }
        }

        public final void processExternalArgument(KotlinCallArgument externalArgument) {
            Intrinsics.checkNotNullParameter(externalArgument, "externalArgument");
            ValueParameterDescriptor valueParameterDescriptor = (ValueParameterDescriptor) CollectionsKt.lastOrNull((List) getParameters());
            if (valueParameterDescriptor == null) {
                addDiagnostic(new TooManyArguments(externalArgument, this.descriptor));
                return;
            }
            if (ArgumentsUtilsKt.isVararg(valueParameterDescriptor)) {
                addDiagnostic(new VarargArgumentOutsideParentheses(externalArgument, valueParameterDescriptor));
            } else if (this.result.get(valueParameterDescriptor.getOriginal()) != null) {
                addDiagnostic(new TooManyArguments(externalArgument, this.descriptor));
            } else {
                this.result.put(valueParameterDescriptor.getOriginal(), new ResolvedCallArgument.SimpleArgument(externalArgument));
            }
        }

        public final void processDefaultsAndRunChecks() {
            for (Map.Entry<ValueParameterDescriptor, ResolvedCallArgument> entry : this.result.entrySet()) {
                ValueParameterDescriptor key = entry.getKey();
                ResolvedCallArgument value = entry.getValue();
                if (!ArgumentsUtilsKt.isVararg(key)) {
                    if (!(value instanceof ResolvedCallArgument.SimpleArgument)) {
                        throw new IllegalStateException(("Incorrect resolved argument for parameter " + key + " :" + value).toString());
                    }
                    ResolvedCallArgument.SimpleArgument simpleArgument = (ResolvedCallArgument.SimpleArgument) value;
                    if (simpleArgument.getCallArgument().isSpread()) {
                        addDiagnostic(new NonVarargSpread(simpleArgument.getCallArgument()));
                    }
                }
            }
            for (ValueParameterDescriptor valueParameterDescriptor : getParameters()) {
                if (!this.result.containsKey(valueParameterDescriptor.getOriginal())) {
                    if (ArgumentsUtilsKt.hasDefaultValue(valueParameterDescriptor)) {
                        this.result.put(valueParameterDescriptor.getOriginal(), ResolvedCallArgument.DefaultArgument.INSTANCE);
                    } else if (ArgumentsUtilsKt.isVararg(valueParameterDescriptor)) {
                        this.result.put(valueParameterDescriptor.getOriginal(), new ResolvedCallArgument.VarargArgument(CollectionsKt.emptyList()));
                    } else {
                        addDiagnostic(new NoValueForParameter(valueParameterDescriptor, this.descriptor));
                    }
                }
            }
        }
    }
}
