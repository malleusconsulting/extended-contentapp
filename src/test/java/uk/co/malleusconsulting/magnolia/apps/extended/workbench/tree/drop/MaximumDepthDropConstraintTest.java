package uk.co.malleusconsulting.magnolia.apps.extended.workbench.tree.drop;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import info.magnolia.test.mock.jcr.MockNode;
import info.magnolia.ui.vaadin.integration.jcr.JcrNodeAdapter;

import javax.jcr.RepositoryException;

import org.junit.Before;
import org.junit.Test;

public class MaximumDepthDropConstraintTest {

    private MaximumDepthDropConstraint maximumDepthDropConstraint;

    @Before
    public void setUp() {
        maximumDepthDropConstraint = new MaximumDepthDropConstraint();
        maximumDepthDropConstraint.setMaximumAllowedDepth(3);
    }

    @Test
    public void constraintAllowsNodeBeingMovedBeneathNodeToMeetMaximumDepth() throws RepositoryException {
        MockNode sourceNode = new MockNode();
        JcrNodeAdapter sourceItem = mock(JcrNodeAdapter.class);
        when(sourceItem.applyChanges()).thenReturn(sourceNode);
        when(sourceItem.getJcrItem()).thenReturn(sourceNode);

        MockNode targetNode = new MockNode();
        targetNode.setParent(new MockNode());
        ((MockNode) targetNode.getParent()).setParent(new MockNode());
        JcrNodeAdapter targetItem = mock(JcrNodeAdapter.class);
        when(targetItem.applyChanges()).thenReturn(targetNode);
        when(targetItem.getJcrItem()).thenReturn(targetNode);

        assertThat(maximumDepthDropConstraint.allowedAsChild(sourceItem, targetItem), is(true));
    }

    @Test
    public void constraintPreventsNodeBeingMovedBeneathNodeAtMaximumDepth() throws RepositoryException {
        MockNode sourceNode = new MockNode();
        JcrNodeAdapter sourceItem = mock(JcrNodeAdapter.class);
        when(sourceItem.applyChanges()).thenReturn(sourceNode);
        when(sourceItem.getJcrItem()).thenReturn(sourceNode);

        MockNode targetNode = new MockNode();
        targetNode.setParent(new MockNode());
        ((MockNode) targetNode.getParent()).setParent(new MockNode());
        ((MockNode) targetNode.getParent().getParent()).setParent(new MockNode());
        JcrNodeAdapter targetItem = mock(JcrNodeAdapter.class);
        when(targetItem.applyChanges()).thenReturn(targetNode);
        when(targetItem.getJcrItem()).thenReturn(targetNode);

        assertThat(maximumDepthDropConstraint.allowedAsChild(sourceItem, targetItem), is(false));
    }

    @Test
    public void constraintAllowsMultipleNodesBeingMovedBeneathNodeToMeetMaximumDepth() throws RepositoryException {
        MockNode sourceNode = new MockNode();
        sourceNode.addNode("child");
        JcrNodeAdapter sourceItem = mock(JcrNodeAdapter.class);
        when(sourceItem.applyChanges()).thenReturn(sourceNode);
        when(sourceItem.getJcrItem()).thenReturn(sourceNode);

        MockNode targetNode = new MockNode();
        targetNode.setParent(new MockNode());
        JcrNodeAdapter targetItem = mock(JcrNodeAdapter.class);
        when(targetItem.applyChanges()).thenReturn(targetNode);
        when(targetItem.getJcrItem()).thenReturn(targetNode);

        assertThat(maximumDepthDropConstraint.allowedAsChild(sourceItem, targetItem), is(true));
    }

    @Test
    public void constraintPreventsMultipleNodesBeingMovedBeneathNodeToExceedMaximumDepth() throws RepositoryException {
        MockNode sourceNode = new MockNode();
        sourceNode.addNode("child");
        sourceNode.getNode("child").addNode("grandchild");
        JcrNodeAdapter sourceItem = mock(JcrNodeAdapter.class);
        when(sourceItem.applyChanges()).thenReturn(sourceNode);
        when(sourceItem.getJcrItem()).thenReturn(sourceNode);

        MockNode targetNode = new MockNode();
        targetNode.setParent(new MockNode());
        JcrNodeAdapter targetItem = mock(JcrNodeAdapter.class);
        when(targetItem.applyChanges()).thenReturn(targetNode);
        when(targetItem.getJcrItem()).thenReturn(targetNode);

        assertThat(maximumDepthDropConstraint.allowedAsChild(sourceItem, targetItem), is(false));
    }
}
