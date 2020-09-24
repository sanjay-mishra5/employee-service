/**
 * 
 */
package com.tesco.finance.corestockvaluation.domain;

import java.io.Serializable;

/**
 * @author BARATH
 *
 */
public interface Identifiable<T extends Serializable> {

	T getId();
}
