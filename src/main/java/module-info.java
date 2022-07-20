module mdiag {
    requires javafx.controls;
    requires eu.hansolo.fx.charts;
    requires org.mongodb.bson;
    requires org.mongodb.driver.sync.client;
    requires org.mongodb.driver.core;
    requires java.logging;

    opens cat.kmruiz.mdiag to javafx.graphics;
    opens cat.kmruiz.mdiag.common to javafx.graphics;
}