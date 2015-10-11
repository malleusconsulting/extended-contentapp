package uk.co.malleusconsulting.magnolia.apps.extended.workbench.tree;

import info.magnolia.event.EventBus;
import info.magnolia.objectfactory.ComponentProvider;
import info.magnolia.ui.vaadin.integration.contentconnector.ContentConnector;
import info.magnolia.ui.workbench.definition.WorkbenchDefinition;
import info.magnolia.ui.workbench.tree.MoveHandler;
import info.magnolia.ui.workbench.tree.TreePresenter;
import info.magnolia.ui.workbench.tree.TreeView;
import info.magnolia.ui.workbench.tree.drop.DropConstraint;

import java.lang.reflect.InvocationTargetException;

import javax.inject.Inject;

import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.co.malleusconsulting.magnolia.apps.extended.workbench.ExtendedWorkbenchDefinition;

public class TreePresenterWithConfiguredDropConstraint extends TreePresenter implements TreeView.Listener {

    private static final Logger LOG = LoggerFactory.getLogger(TreePresenterWithConfiguredDropConstraint.class);

    @Inject
    public TreePresenterWithConfiguredDropConstraint(TreeView view, ComponentProvider componentProvider) {
        super(view, componentProvider);
    }

    @Override
    public TreeView start(WorkbenchDefinition workbenchDefinition, EventBus eventBus, String viewTypeName,
            ContentConnector contentConnector) {
        TreeView view = super.start(workbenchDefinition, eventBus, viewTypeName, contentConnector);

        ExtendedWorkbenchDefinition extendedWorkbenchDefinition = (ExtendedWorkbenchDefinition) workbenchDefinition;

        // Overwrite the drop constraint set originally with the new, configurable one
        Class<? extends DropConstraint> dropConstraintClass = workbenchDefinition.getDropConstraintClass();
        if (dropConstraintClass != null) {

            ComponentProvider componentProvider = getComponentProvider();

            final DropConstraint constraint = componentProvider.newInstance(dropConstraintClass);
            for (String key : extendedWorkbenchDefinition.getDropConstraintParameters().keySet()) {
                try {
                    BeanUtils.setProperty(constraint, key, extendedWorkbenchDefinition.getDropConstraintParameters()
                            .get(key));
                } catch (InvocationTargetException | IllegalAccessException e) {
                    LOG.error("Unable to set value of " + key + " as "
                            + extendedWorkbenchDefinition.getDropConstraintParameters().get(key), e);
                }
            }

            final MoveHandler moveHandler = getComponentProvider().newInstance(MoveHandler.class,
                    view.asVaadinComponent(), constraint);
            view.setDragAndDropHandler(moveHandler.asDropHandler());
            LOG.debug("Set following drop container {} to the treeTable", dropConstraintClass.getName());
        }
        return view;
    }
}
