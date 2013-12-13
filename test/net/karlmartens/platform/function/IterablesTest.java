/**
 *   Copyright 2013 Karl Martens
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *       
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 *
 *   net.karlmartens.platform, is a library of shared basic utility classes
 */

package net.karlmartens.platform.function;

import static java.util.Collections.emptyList;
import static net.karlmartens.platform.function.Iterables.foldLeft;
import static net.karlmartens.platform.function.Iterables.foldRight;
import static net.karlmartens.platform.function.Iterables.map;
import static net.karlmartens.platform.function.Iterables.max;
import static net.karlmartens.platform.function.Iterables.min;
import static net.karlmartens.platform.function.Iterables.reverse;
import static net.karlmartens.platform.function.Iterables.select;
import static net.karlmartens.platform.function.Numbers.even;
import static net.karlmartens.platform.function.Numbers.multiply;
import static net.karlmartens.platform.function.Numbers.range;
import static net.karlmartens.platform.function.Strings.intersperse;
import static org.junit.Assert.assertEquals;

import java.util.NoSuchElementException;

import org.junit.Test;
/**
 * @author kmartens
 *
 */
public class IterablesTest {
  
  @Test
  public void testMapOnEmpty() throws Exception {
    final Iterable<Integer> list = emptyList();
    final Iterable<Integer> result = map(list, multiply(2));
    assertEquals("", intersperse(result, ","));    
  }
  
  @Test
  public void testMapOnSingle() throws Exception {
    final Iterable<Integer> list = range(1,1);
    final Iterable<Integer> result = map(list, multiply(2));
    assertEquals("2", intersperse(result, ","));
  }
  
  @Test
  public void testMapOnMulitple() throws Exception {
    final Iterable<Integer> list = range(1,5);
    final Iterable<Integer> result = map(list, multiply(2));
    assertEquals("2,4,6,8,10", intersperse(result, ","));
  }
  
  @Test
  public void testReserseOnEmpty() throws Exception {
    final Iterable<Integer> list = emptyList();
    final Iterable<Integer> result = reverse(list);
    assertEquals("", intersperse(result, ","));    
  }
  
  @Test
  public void testReserseOnSingle() throws Exception {
    final Iterable<Integer> list = range(1,1);
    final Iterable<Integer> result = reverse(list);
    assertEquals("1", intersperse(result, ","));    
  }
  
  @Test
  public void testReserseOnMultiple() throws Exception {
    final Iterable<Integer> list = range(1,5);
    final Iterable<Integer> result = reverse(list);
    assertEquals("5,4,3,2,1", intersperse(result, ","));    
  }
  
  @Test
  public void testSelectOnEmpty() throws Exception {
    final Iterable<Integer> list = emptyList();
    final Iterable<Integer> result = select(list, even());
    assertEquals("", intersperse(result, ","));    
  }
  
  @Test
  public void testSelectOnSingleMatched() throws Exception {
    final Iterable<Integer> list = range(2,2);
    final Iterable<Integer> result = select(list, even());
    assertEquals("2", intersperse(result, ","));    
  }
  
  @Test
  public void testSelectOnSingleUnmatched() throws Exception {
    final Iterable<Integer> list = range(1,1);
    final Iterable<Integer> result = select(list, even());
    assertEquals("", intersperse(result, ","));    
  }
  
  @Test
  public void testSelectOnMulitple() throws Exception {
    final Iterable<Integer> list = range(1,10);
    final Iterable<Integer> result = select(list, even());
    assertEquals("2,4,6,8,10", intersperse(result, ","));
  }

  @Test
  public void testFoldLeftOnEmpty() throws Exception { 
    final Iterable<Integer> list = emptyList();
    final Integer result = foldLeft(list, 1, Numbers.<Integer>multiply());
    assertEquals(Integer.valueOf(1), result);

  }
  
  @Test
  public void testFoldLeftOnSingle() throws Exception {
    final Iterable<Integer> list = range(2,2);
    final Integer result = foldLeft(list, 1, Numbers.<Integer>multiply());
    assertEquals(Integer.valueOf(2), result);
  }
  
  @Test
  public void testFoldLeftOnMultiple() throws Exception {
    final Iterable<Integer> list = range(1,5);
    final Integer result = foldLeft(list, 1, Numbers.<Integer>multiply());
    assertEquals(Integer.valueOf(120), result);
  }
  
  @Test
  public void testFoldLeftOnMultipleOpOrder() throws Exception {
    final Iterable<Integer> list = range(1,5);
    final Integer result = foldLeft(list, 0, Numbers.<Integer>subtract());
    assertEquals(Integer.valueOf(-15), result);
  }

  @Test
  public void testFoldRightOnEmpty() throws Exception {
    final Iterable<Integer> list = emptyList();
    final Integer result = foldRight(list, 0, Numbers.<Integer>subtract());
    assertEquals(Integer.valueOf(0), result);
  }

  @Test
  public void testFoldRightOnSingle() throws Exception {
    final Iterable<Integer> list = range(5,5);
    final Integer result = foldRight(list, 0, Numbers.<Integer>subtract());
    assertEquals(Integer.valueOf(5), result);
  }
  
  @Test
  public void testFoldRightOnMultiple() throws Exception {
    final Iterable<Integer> list = range(1,5);
    final Integer result = foldRight(list, 1, Numbers.<Integer>multiply());
    assertEquals(Integer.valueOf(120), result);
  }
  
  @Test
  public void testFoldRightOnMultipleOpOrder() throws Exception {
    final Iterable<Integer> list = range(1,5);
    final Integer result = foldRight(list, 0, Numbers.<Integer>subtract());
    assertEquals(Integer.valueOf(3), result);
  }
  
  @Test(expected=NoSuchElementException.class)
  public void testMaxOnEmpty() throws Exception {
    final Iterable<Integer> list = emptyList();
    max(list);
  }
  
  @Test
  public void testMaxOnSingle() throws Exception {
    final Iterable<Integer> list = range(1,1);
    final Integer result = max(list);
    assertEquals(Integer.valueOf(1), result);
  }
  
  @Test
  public void testMaxOnMultiple() throws Exception {
    final Iterable<Integer> list = range(1,5);
    final Integer result = max(list);
    assertEquals(Integer.valueOf(5), result);
  }
  
  @Test(expected=NoSuchElementException.class)
  public void testMinOnEmpty() throws Exception {
    final Iterable<Integer> list = emptyList();
    min(list);
  }
  
  @Test
  public void testMinOnSingle() throws Exception {
    final Iterable<Integer> list = range(1,1);
    final Integer result = min(list);
    assertEquals(Integer.valueOf(1), result);
  }
  
  @Test
  public void testMinOnMultiple() throws Exception {
    final Iterable<Integer> list = range(1,5);
    final Integer result = min(list);
    assertEquals(Integer.valueOf(1), result);
  }
}
