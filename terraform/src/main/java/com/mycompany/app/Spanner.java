package com.mycompany.app;

import com.hashicorp.cdktf.providers.google.spanner_database.SpannerDatabase;
import com.hashicorp.cdktf.providers.google.spanner_instance.SpannerInstance;
import software.constructs.Construct;

import com.hashicorp.cdktf.TerraformStack;
public class Spanner extends TerraformStack
{
    public Spanner(final Construct scope, final String id) {
        super(scope, id);
        // the arguments provided to spannerInstance need to be parameterised
        SpannerInstance spannerInstance = SpannerInstance.Builder.create(this, "abc")
            .config("lorem")
            .name("epsum")
            .processingUnits(1000)
            .build();

        SpannerDatabase spannerDatabase = SpannerDatabase.Builder.create(this, "def")
            .instance("lorem")
            .name("epsum")
            .deletionProtection(true)
            .versionRetentionPeriod("1h")
            .build();
    }
}