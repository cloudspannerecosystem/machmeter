package com.mycompany.app;

import com.hashicorp.cdktf.TerraformStack;
import com.hashicorp.cdktf.providers.kubernetes.namespace.Namespace;
import com.hashicorp.cdktf.providers.kubernetes.namespace.NamespaceConfig;
import com.hashicorp.cdktf.providers.kubernetes.namespace.NamespaceMetadata;
import com.hashicorp.cdktf.providers.kubernetes.provider.KubernetesProvider;
import org.jetbrains.annotations.NotNull;
import software.constructs.Construct;

public class GKEStack extends TerraformStack {
  public GKEStack(@NotNull Construct scope, @NotNull String id) {
    super(scope, id);
    //https://github.com/hashicorp/terraform-cdk/blob/main/examples/java/kubernetes/src/main/java/com/mycompany/app/Main.java
    KubernetesProvider.Builder.create(this, "abc").build();
    Namespace exampleNamespace = new Namespace(this, "machmeter-namespace",
        NamespaceConfig.builder()
            .metadata(
                NamespaceMetadata.builder()
                    .name("machmeter")
                    .build()
            )
            .build());
  }
}
