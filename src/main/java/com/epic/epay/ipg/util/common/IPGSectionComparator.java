/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.epay.ipg.util.common;

import com.epic.epay.ipg.util.mapping.Ipgsection;

import java.io.Serializable;
import java.util.Comparator;

/**
 *
 * @author chanuka
 */
public class IPGSectionComparator implements Comparator<Ipgsection>,Serializable{
    
        /**
	 * 
	 */
	private static final long serialVersionUID = -7152725929315945887L;

		public int compare(Ipgsection _first, Ipgsection _second) {
            return _first.getSortkey().compareTo(_second.getSortkey());
    }

}
