package uk.co.malleusconsulting.magnolia.apps.extended.ui.availability;

import info.magnolia.ui.api.availability.AvailabilityRuleDefinition;

import java.util.Map;

public interface ExtendedAvailabilityRuleDefinition extends AvailabilityRuleDefinition {

    public Map<String, Object> getParameters();
}
