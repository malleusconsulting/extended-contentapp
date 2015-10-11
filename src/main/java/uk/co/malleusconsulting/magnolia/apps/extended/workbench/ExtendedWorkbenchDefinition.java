package uk.co.malleusconsulting.magnolia.apps.extended.workbench;

import info.magnolia.ui.workbench.definition.WorkbenchDefinition;

import java.util.Map;

public interface ExtendedWorkbenchDefinition extends WorkbenchDefinition {

    public Map<String, Object> getDropConstraintParameters();
}