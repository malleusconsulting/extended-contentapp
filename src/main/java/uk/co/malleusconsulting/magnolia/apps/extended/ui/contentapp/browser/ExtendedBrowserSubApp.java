package uk.co.malleusconsulting.magnolia.apps.extended.ui.contentapp.browser;

import javax.inject.Named;

import info.magnolia.event.EventBus;
import info.magnolia.ui.api.action.ActionExecutor;
import info.magnolia.ui.api.app.SubAppContext;
import info.magnolia.ui.api.availability.AvailabilityChecker;
import info.magnolia.ui.contentapp.ContentSubAppView;
import info.magnolia.ui.contentapp.browser.BrowserPresenter;
import info.magnolia.ui.contentapp.browser.BrowserSubApp;
import info.magnolia.ui.vaadin.integration.contentconnector.ContentConnector;

public class ExtendedBrowserSubApp extends BrowserSubApp {

    public ExtendedBrowserSubApp(ActionExecutor actionExecutor, SubAppContext subAppContext, ContentSubAppView view,
            BrowserPresenter browser, EventBus subAppEventBus, EventBus adminCentralEventBus,
            ContentConnector contentConnector, @Named("extendedAvailabilityChecker") AvailabilityChecker checker) {
        super(actionExecutor, subAppContext, view, browser, subAppEventBus, adminCentralEventBus, contentConnector,
                checker);
    }

}
