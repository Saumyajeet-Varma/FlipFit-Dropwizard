package com.flipfit;

import io.dropwizard.Configuration;
import io.dropwizard.db.DataSourceFactory;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.Valid;
import javax.validation.constraints.*;

public class FlipFitConfiguration extends Configuration {

    @Valid
    @NotNull
    private AppConfig app;

    @Valid
    @NotNull
    private DataSourceFactory database = new DataSourceFactory();

    @JsonProperty("app")
    public AppConfig getApp() {
        return app;
    }

    @JsonProperty("app")
    public void setApp(AppConfig app) {
        this.app = app;
    }

    @JsonProperty("database")
    public DataSourceFactory getDataSourceFactory() {
        return this.database;
    }

    @JsonProperty("database")
    public void setDataSourceFactory(DataSourceFactory database) {
        this.database = database;
    }

    public static class AppConfig {

        @NotEmpty
        private String name;

        @NotEmpty
        private String version;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getVersion() {
            return version;
        }

        public void setVersion(String version) {
            this.version = version;
        }
    }
}
