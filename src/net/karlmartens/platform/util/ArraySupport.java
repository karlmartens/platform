/**
 *  net.karlmartens.platform, is a library of shared basic utility classes
 *  Copyright (C) 2011
 *  Karl Martens
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU Lesser General Public License as 
 *  published by the Free Software Foundation, either version 3 of the 
 *  License, or (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU Lesser General Public License for more details.
 *
 *  You should have received a copy of the GNU Lesser General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */
package net.karlmartens.platform.util;

import java.util.BitSet;

public final class ArraySupport {

	public static int[] minus(int[] first, int[] second) {
		final BitSet set = new BitSet();
		for (int i : first)
			set.set(i);
		
		for (int i : second)
			set.flip(i);
		
		final int[] result = new int[set.cardinality()];
		int i=0;
		for (int value=set.nextSetBit(0); value>=0; value=set.nextSetBit(value+1))
			result[i++] = value;
		
		return result;
	}
	
}