package uk.co.malleusconsulting.magnolia.apps.extended.ui.framework.availability;

import info.magnolia.objectfactory.ComponentProvider;
import info.magnolia.ui.api.availability.AvailabilityChecker;
import info.magnolia.ui.api.availability.AvailabilityDefinition;
import info.magnolia.ui.api.availability.AvailabilityRule;
import info.magnolia.ui.api.availability.AvailabilityRuleDefinition;
import info.magnolia.ui.framework.availability.AvailabilityCheckerImpl;
import info.magnolia.ui.vaadin.integration.contentconnector.ContentConnector;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.co.malleusconsulting.magnolia.apps.extended.ui.availability.ExtendedAvailabilityRuleDefinition;

public class ExtendedAvailabilityCheckerImpl extends AvailabilityCheckerImpl
		implements AvailabilityChecker {

	private static final Logger LOG = LoggerFactory
			.getLogger(ExtendedAvailabilityCheckerImpl.class);

	private ComponentProvider componentProvider;

	public ExtendedAvailabilityCheckerImpl(ComponentProvider componentProvider,
			ContentConnector contentConnector) {
		super(componentProvider, contentConnector);
		this.componentProvider = componentProvider;
	}

	@Override
	public boolean isAvailable(AvailabilityDefinition definition,
			List<Object> idsToCheck) {
		// Test via superclass first. Then run configured rules.
		if (super.isAvailable(definition, idsToCheck)) {

			boolean isAvailable = true;
			Iterator<AvailabilityRule> ruleIterator = prepareRules(definition)
					.iterator();
			while (isAvailable && ruleIterator.hasNext()) {
				AvailabilityRule rule = ruleIterator.next();
				boolean ruleHolds = rule.isAvailable(idsToCheck);
				isAvailable &= ruleHolds;
			}
			return isAvailable;
		}
		return false;
	}

	private List<AvailabilityRule> prepareRules(
			AvailabilityDefinition definition) {

		List<AvailabilityRule> rules = new ArrayList<AvailabilityRule>();
		for (AvailabilityRuleDefinition ruleDefinition : definition.getRules()) {

			if (ruleDefinition instanceof ExtendedAvailabilityRuleDefinition) {

				ExtendedAvailabilityRuleDefinition extendedRuleDefinition = (ExtendedAvailabilityRuleDefinition) ruleDefinition;

				AvailabilityRule rule = componentProvider.newInstance(
						extendedRuleDefinition.getImplementationClass(),
						extendedRuleDefinition);

				for (String key : extendedRuleDefinition.getParameters()
						.keySet()) {
					try {
						BeanUtils.setProperty(rule, key, extendedRuleDefinition
								.getParameters().get(key));
					} catch (InvocationTargetException | IllegalAccessException e) {
						LOG.error(
								"Unable to set value of "
										+ key
										+ " as "
										+ extendedRuleDefinition
												.getParameters().get(key), e);
					}
				}
				rules.add(rule);
			}
		}
		return rules;
	}
}
