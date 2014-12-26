package com.hannesdorfmann.appkit.dagger1;

import dagger.ObjectGraph;

/**
 * Injector will be passed around in the application to get access to the {@link
 * dagger.ObjectGraph}
 *
 * @author Hannes Dorfmann
 */
public interface Dagger1Injector {

  /**
   * Get the object graph
   */
  public ObjectGraph getObjectGraph();
}
