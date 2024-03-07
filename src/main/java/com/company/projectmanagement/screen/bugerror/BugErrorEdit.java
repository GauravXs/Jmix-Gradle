package com.company.projectmanagement.screen.bugerror;

import io.jmix.ui.screen.*;
import com.company.projectmanagement.entity.BugError;

@UiController("BugError.edit")
@UiDescriptor("bug-error-edit.xml")
@EditedEntityContainer("bugErrorDc")
public class BugErrorEdit extends StandardEditor<BugError> {
}