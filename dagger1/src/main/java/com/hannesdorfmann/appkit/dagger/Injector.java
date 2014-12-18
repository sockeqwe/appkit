package com.hannesdorfmann.appkit.dagger;

import dagger.ObjectGraph;

/**
 * Injector will be passed around in the application to get access to the {@link
 * dagger.ObjectGraph}
 *
 * @author Hannes Dorfmann
 */
public interface Injector {

  /**
   * Get the object graph
   */
  public ObjectGraph getObjectGraph();
}
