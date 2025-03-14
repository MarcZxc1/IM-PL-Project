module com.dev.marc.fitnesstrackingapplication {
	requires javafx.controls;
	requires javafx.fxml;
	requires javafx.web;

	requires org.controlsfx.controls;
	requires com.dlsc.formsfx;
	requires net.synedra.validatorfx;
	requires org.kordamp.ikonli.javafx;
	requires org.kordamp.bootstrapfx.core;
	requires eu.hansolo.tilesfx;
	requires com.almasb.fxgl.all;

	requires google.api.client;
	requires com.google.api.client;
	requires com.google.api.client.json.jackson2;
	requires com.google.api.client.json.gson;
	requires jdk.httpserver;
	requires com.google.api.client.extensions.jetty.auth;
	requires com.google.api.client.auth;
	requires com.google.api.client.extensions.java6.auth;
	requires java.sql;
	requires org.json;
	requires org.apache.httpcomponents.client5.httpclient5;
	requires org.apache.httpcomponents.core5.httpcore5;

	opens com.dev.marc.fitnesstrackingapplication to javafx.fxml;
	exports com.dev.marc.fitnesstrackingapplication;
	exports com.dev.marc.fitnesstrackingapplication.controller;
	opens com.dev.marc.fitnesstrackingapplication.controller to javafx.fxml;
}