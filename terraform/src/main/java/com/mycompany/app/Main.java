package com.mycompany.app;

import com.hashicorp.cdktf.App;


public class Main
{
    public static void main(String[] args) {
        final App app = new App();
        new SpannerInstance(app, "terraform");
        app.synth();
    }
}