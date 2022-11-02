package com.mycompany.app;

import software.constructs.Construct;

import com.hashicorp.cdktf.TerraformStack;
public class SpannerInstance extends TerraformStack
{
    public SpannerInstance(final Construct scope, final String id) {
        super(scope, id);

        // define resources here
    }
}