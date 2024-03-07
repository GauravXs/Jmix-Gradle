package com.company.projectmanagement.screen.timeentry;

import io.jmix.ui.screen.*;
import com.company.projectmanagement.entity.TimeEntry;

@UiController("TimeEntry.edit")
@UiDescriptor("time-entry-edit.xml")
@EditedEntityContainer("timeEntryDc")
public class TimeEntryEdit extends StandardEditor<TimeEntry> {
}