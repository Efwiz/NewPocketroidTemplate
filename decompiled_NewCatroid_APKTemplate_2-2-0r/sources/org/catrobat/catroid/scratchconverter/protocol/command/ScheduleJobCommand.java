package org.catrobat.catroid.scratchconverter.protocol.command;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/scratchconverter/protocol/command/ScheduleJobCommand.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class ScheduleJobCommand extends Command {
    public ScheduleJobCommand(final long jobID, final boolean force, final boolean verbose) {
        super(Command.Type.SCHEDULE_JOB);
        addArgument(Command.ArgumentType.JOB_ID, Long.valueOf(jobID));
        addArgument(Command.ArgumentType.FORCE, Boolean.valueOf(force));
        addArgument(Command.ArgumentType.VERBOSE, Boolean.valueOf(verbose));
    }
}
