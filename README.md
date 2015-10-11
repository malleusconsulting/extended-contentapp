# extended-magnolia-content-apps
Extended classes for adding configuration parameters to drop constraints in Magnolia content apps.

For an example, build the module and install with sample content bootstrapped.

The module will reconfigure the example Contacts app to no longer allow drag and drop of nodes beyond a depth of three folders. The responsible configuration is:

```xml
<sv:node sv:name="workbench">
  <sv:property sv:name="class" sv:type="String">
    <sv:value>uk.co.malleusconsulting.magnolia.apps.extended.workbench.ExtendedWorkbench</sv:value>
  </sv:property>
  <sv:property sv:name="dropConstraintClass" sv:type="String">
    <sv:value>uk.co.malleusconsulting.magnolia.apps.extended.workbench.tree.drop.MaximumDepthDropConstraint</sv:value>
  </sv:property>
```
"class" replaces the default info.magnolia.ui.workbench.definition.ConfiguredWorkbenchDefinition with the extended version.
"dropConstraintClass" is updated to use the provided example of a configurable drop constraint.

```xml
<sv:node sv:name="dropConstraintParameters">
  <sv:property sv:name="maximumAllowedDepth" sv:type="String">
    <sv:value>3</sv:value>
  </sv:property>
</node>
```
Parameters are set, as a map, for the named drop constraint.

```xml
<sv:node sv:name="tree">
  <sv:property sv:name="class" sv:type="String">
     <sv:value>uk.co.malleusconsulting.magnolia.apps.extended.workbench.tree.TreePresenterWithConfiguredDropConstraintDefinition</sv:value>
  </sv:property>
```
The presenter used to represent the tree view within the browser app is replaced with the extended class.

The code is very simple and works with provided Magnolia code as much as possible. ExtendedWorkbenchDefinition provides the additional property, dropConstraintParameters, that will be populated via Node2Bean.

When the app UI is rendered, the extended presenter, TreePresenterWithConfiguredDropConstraint, overrides the start() method of the default, TreePresenter, to replace the MoveHandler registered in the tree's view. This is simply another of the same class as originally set but after using the Apache Commons' BeanUtils to set, via reflection, all properties referred to as keys of the parameter map.