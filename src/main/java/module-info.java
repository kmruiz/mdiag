module mdiag {
    requires javafx.controls;
    requires org.mongodb.bson;
    requires org.mongodb.driver.sync.client;
    requires org.mongodb.driver.core;
    requires java.logging;

    requires com.fasterxml.jackson.databind;
    requires com.fasterxml.jackson.datatype.jsr310;

    opens cat.kmruiz.mdiag to javafx.graphics;
    opens cat.kmruiz.mdiag.common to javafx.graphics, com.fasterxml.jackson.databind;
    opens cat.kmruiz.mdiag.ui.viewmodel to javafx.base;
    opens cat.kmruiz.mdiag.overview to com.fasterxml.jackson.databind;
    opens cat.kmruiz.mdiag.overview.topology.sharded to com.fasterxml.jackson.databind;
}