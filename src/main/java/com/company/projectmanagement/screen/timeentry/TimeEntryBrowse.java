package com.company.projectmanagement.screen.timeentry;

import io.jmix.ui.screen.*;
import com.company.projectmanagement.entity.TimeEntry;

@UiController("TimeEntry.browse")
@UiDescriptor("time-entry-browse.xml")
@LookupComponent("timeEntriesTable")
public class TimeEntryBrowse extends StandardLookup<TimeEntry> {
}