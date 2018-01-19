package io.happium.happium_core.persistence;

import javax.persistence.Entity;

/**
 * Selenium Grid Node Entity class
 *
 * <p>
 *     Selenium grid2 operates on the concept of a grid "hub", which
 *     grid "nodes" register to. "Nodes" host platform environment
 *     instances (e.g. Windows, MacOS, Linux, iOS, Android) and are reused
 *     for any sessions whose capabilities conform the capability
 *     requirements of the given "node". In other words, an "iOS"
 *     node will only be reusable for iOS test sessions.
 */
@Entity
public class SeleniumGridNode {
}
