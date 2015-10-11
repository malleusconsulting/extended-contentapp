package uk.co.malleusconsulting.magnolia.apps.extended.workbench.tree.drop;

import java.util.Collection;

import info.magnolia.jcr.util.NodeUtil;
import info.magnolia.ui.vaadin.integration.jcr.JcrNodeAdapter;
import info.magnolia.ui.workbench.tree.drop.AlwaysTrueDropConstraint;
import info.magnolia.ui.workbench.tree.drop.DropConstraint;

import javax.jcr.Node;
import javax.jcr.RepositoryException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gwt.thirdparty.guava.common.collect.Lists;
import com.vaadin.data.Item;

public class MaximumDepthDropConstraint extends AlwaysTrueDropConstraint implements DropConstraint {

    public static final String PARAMETER_MAXIMUM_ALLOWED_DEPTH = "maximumAllowedDepth";

    private static final Logger LOG = LoggerFactory.getLogger(MaximumDepthDropConstraint.class);

    private int maximumAllowedDepth = -1;

    public void setMaximumAllowedDepth(Integer maximumAllowedDepth) {
        this.maximumAllowedDepth = maximumAllowedDepth;
    }

    public MaximumDepthDropConstraint() {
        super();
    }

    public boolean allowedAsChild(Item sourceItem, Item targetItem) {
        try {
            JcrNodeAdapter target = (JcrNodeAdapter) targetItem;
            JcrNodeAdapter source = (JcrNodeAdapter) sourceItem;

            int depthOfTarget = target.getJcrItem().getDepth();
            int depthOfNodesBeingMoved = findDeepestDescendentDepth(source.getJcrItem());
            if (maximumAllowedDepth > 0 && depthOfNodesBeingMoved + depthOfTarget <= maximumAllowedDepth) {
                return true;
            }
        }
        catch (RepositoryException e) {
            LOG.error(
                    "Unable to find maximum depth of descendents of node "
                            + NodeUtil.getNodePathIfPossible((Node) sourceItem), e);
        }

        return false;
    }

    private int findDeepestDescendentDepth(Node node) throws RepositoryException {
        return findDeepestDescendentDepth(Lists.newArrayList(node), 1);
    }

    private int findDeepestDescendentDepth(Collection<Node> nodes, int depth) throws RepositoryException {
        int maxDepthFound = 0;
        for (Node node : nodes) {
            if (node.hasNodes()) {
                int latestDepth = findDeepestDescendentDepth(NodeUtil.getCollectionFromNodeIterator(node.getNodes()),
                        depth);
                if (latestDepth > maxDepthFound) {
                    maxDepthFound = latestDepth;
                }
            }
        }
        return depth + maxDepthFound;
    }
}
