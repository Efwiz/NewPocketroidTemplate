package org.eclipse.jdt.internal.compiler.apt.dispatch;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ServiceConfigurationError;
import java.util.ServiceLoader;
import javax.annotation.processing.Processor;
import javax.tools.StandardLocation;
import org.eclipse.jdt.internal.compiler.CompilationResult;
import org.eclipse.jdt.internal.compiler.batch.Main;
import org.eclipse.jdt.internal.compiler.problem.AbortCompilation;

/* JADX INFO: loaded from: classes14.dex */
public class BatchAnnotationProcessorManager extends BaseAnnotationProcessorManager {
    private static final boolean VERBOSE_PROCESSOR_DISCOVERY = true;
    private List<String> _commandLineProcessors;
    private ClassLoader _procLoader;
    private Iterator<Processor> _serviceLoaderIter;
    private List<Processor> _setProcessors = null;
    private Iterator<Processor> _setProcessorIter = null;
    private Iterator<String> _commandLineProcessorIter = null;
    private ServiceLoader<Processor> _serviceLoader = null;
    private boolean _printProcessorDiscovery = false;

    @Override // org.eclipse.jdt.internal.compiler.apt.dispatch.BaseAnnotationProcessorManager, org.eclipse.jdt.internal.compiler.AbstractAnnotationProcessorManager
    public void configure(Object obj, String[] strArr) {
        if (this._processingEnv != null) {
            throw new IllegalStateException("Calling configure() more than once on an AnnotationProcessorManager is not supported");
        }
        BatchProcessingEnvImpl batchProcessingEnvImpl = new BatchProcessingEnvImpl(this, (Main) obj, strArr);
        this._processingEnv = batchProcessingEnvImpl;
        this._procLoader = batchProcessingEnvImpl.getFileManager().getClassLoader(StandardLocation.ANNOTATION_PROCESSOR_PATH);
        parseCommandLine(strArr);
        this._round = 0;
    }

    private void parseCommandLine(String[] strArr) {
        ArrayList arrayList;
        int i = 0;
        while (true) {
            if (i >= strArr.length) {
                arrayList = null;
                break;
            }
            String str = strArr[i];
            if ("-XprintProcessorInfo".equals(str)) {
                this._printProcessorInfo = true;
                this._printProcessorDiscovery = true;
            } else if ("-XprintRounds".equals(str)) {
                this._printRounds = true;
            } else if ("-processor".equals(str)) {
                ArrayList arrayList2 = new ArrayList();
                for (String str2 : strArr[i + 1].split(",")) {
                    arrayList2.add(str2);
                }
                arrayList = arrayList2;
                break;
            }
            i++;
        }
        this._commandLineProcessors = arrayList;
        if (arrayList != null) {
            this._commandLineProcessorIter = arrayList.iterator();
        }
    }

    @Override // org.eclipse.jdt.internal.compiler.apt.dispatch.IProcessorProvider
    public ProcessorInfo discoverNextProcessor() {
        if (this._setProcessors != null) {
            if (!this._setProcessorIter.hasNext()) {
                return null;
            }
            Processor next = this._setProcessorIter.next();
            next.init(this._processingEnv);
            ProcessorInfo processorInfo = new ProcessorInfo(next);
            this._processors.add(processorInfo);
            if (this._printProcessorDiscovery && this._out != null) {
                this._out.println("API specified processor: " + processorInfo);
            }
            return processorInfo;
        }
        if (this._commandLineProcessors != null) {
            if (!this._commandLineProcessorIter.hasNext()) {
                return null;
            }
            try {
                Processor processor = (Processor) this._procLoader.loadClass(this._commandLineProcessorIter.next()).newInstance();
                processor.init(this._processingEnv);
                ProcessorInfo processorInfo2 = new ProcessorInfo(processor);
                this._processors.add(processorInfo2);
                if (this._printProcessorDiscovery && this._out != null) {
                    this._out.println("Command line specified processor: " + processorInfo2);
                }
                return processorInfo2;
            } catch (Exception e) {
                throw new AbortCompilation((CompilationResult) null, e);
            }
        }
        if (this._serviceLoader == null) {
            ServiceLoader<Processor> serviceLoaderLoad = ServiceLoader.load(Processor.class, this._procLoader);
            this._serviceLoader = serviceLoaderLoad;
            this._serviceLoaderIter = serviceLoaderLoad.iterator();
        }
        try {
            if (!this._serviceLoaderIter.hasNext()) {
                return null;
            }
            Processor next2 = this._serviceLoaderIter.next();
            next2.init(this._processingEnv);
            ProcessorInfo processorInfo3 = new ProcessorInfo(next2);
            this._processors.add(processorInfo3);
            if (this._printProcessorDiscovery && this._out != null) {
                this._out.println("Discovered processor service " + processorInfo3 + "\n  supporting " + processorInfo3.getSupportedAnnotationTypesAsString() + "\n  in " + getProcessorLocation(next2));
            }
            return processorInfo3;
        } catch (ServiceConfigurationError e2) {
            throw new AbortCompilation((CompilationResult) null, e2);
        }
    }

    private String getProcessorLocation(Processor processor) {
        Class<?> enclosingClass = processor.getClass();
        StringBuilder sb = new StringBuilder();
        boolean z = false;
        while (enclosingClass.isMemberClass()) {
            sb.insert(0, enclosingClass.getSimpleName());
            sb.insert(0, '$');
            enclosingClass = enclosingClass.getEnclosingClass();
            z = true;
        }
        String strReplace = enclosingClass.getName().replace('.', '/');
        if (z) {
            strReplace = String.valueOf(strReplace) + ((Object) sb);
        }
        String str = String.valueOf(strReplace) + ".class";
        String string = this._procLoader.getResource(str).toString();
        return string.endsWith(str) ? string.substring(0, string.length() - str.length()) : string;
    }

    @Override // org.eclipse.jdt.internal.compiler.apt.dispatch.IProcessorProvider
    public void reportProcessorException(Processor processor, Exception exc) {
        throw new AbortCompilation((CompilationResult) null, exc);
    }

    @Override // org.eclipse.jdt.internal.compiler.apt.dispatch.BaseAnnotationProcessorManager, org.eclipse.jdt.internal.compiler.AbstractAnnotationProcessorManager
    public void setProcessors(Object[] objArr) {
        if (!this._isFirstRound) {
            throw new IllegalStateException("setProcessors() cannot be called after processing has begun");
        }
        this._setProcessors = new ArrayList(objArr.length);
        for (Object obj : objArr) {
            this._setProcessors.add((Processor) obj);
        }
        this._setProcessorIter = this._setProcessors.iterator();
        this._commandLineProcessors = null;
        this._commandLineProcessorIter = null;
    }
}
