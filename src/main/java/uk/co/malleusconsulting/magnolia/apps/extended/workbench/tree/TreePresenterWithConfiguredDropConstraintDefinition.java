package uk.co.malleusconsulting.magnolia.apps.extended.workbench.tree;

import info.magnolia.ui.workbench.tree.TreePresenterDefinition;

public class TreePresenterWithConfiguredDropConstraintDefinition extends TreePresenterDefinition {

    public TreePresenterWithConfiguredDropConstraintDefinition() {
        super();
        setImplementationClass(TreePresenterWithConfiguredDropConstraint.class);
    }
}
