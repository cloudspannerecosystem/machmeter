package com.google.cloud.machmeter.plugins;

import com.google.cloud.machmeter.model.MachmeterConfig;

public interface PluginInterface {

  /**
   *
   * @return return the unique name of the plugin.
   */
  String getName();

  /**
   * Executes the underlying plugin
   */
  void execute(MachmeterConfig config);
}