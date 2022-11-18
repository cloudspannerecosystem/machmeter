package com.google.cloud.machmeter.plugins;

import com.google.cloud.machmeter.model.ConfigInterface;
import com.google.cloud.machmeter.model.SetupConfig;

public interface PluginInterface {

  /** @return return the unique name of the plugin. */
  String getName();

  /** Executes the underlying plugin */
  void execute(ConfigInterface configInterface);
}
