package uk.co.malleusconsulting.magnolia.apps.extended.workbench;

import info.magnolia.ui.workbench.definition.ConfiguredWorkbenchDefinition;

import java.util.Map;

public class ExtendedWorkbench extends ConfiguredWorkbenchDefinition implements ExtendedWorkbenchDefinition {

    private static final long serialVersionUID = 383414123200694340L;

    private Map<String, Object> dropConstraintParameters;

    public ExtendedWorkbench() {
        super();
    }

    public Map<String, Object> getDropConstraintParameters() {
        return dropConstraintParameters;
    }

    public void setDropConstraintParameters(Map<String, Object> dropConstraintParameters) {
        this.dropConstraintParameters = dropConstraintParameters;
    }
}