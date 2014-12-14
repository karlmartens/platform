/**
 *   Copyright 2014 Karl Martens
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

package net.karlmartens.platform.test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import org.junit.Assert;
import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

/**
 * @author kmartens
 *
 */
public class ExpectedSummary implements TestRule {
	
	private final List<String> _expected = new ArrayList<>();
	private final List<String> _actual = new ArrayList<>();
	
	private ExpectedSummary() {
		// Reduced visibility
	}
	
	public ExpectedSummary expect(String... lines) {
		_expected.addAll(Arrays.asList(lines));
		return this;
	}
	
	public ExpectedSummary actual(String fmt, Object... args) {
		_actual.add(String.format(Locale.US, fmt, args));
		return this;
	}

	@Override
	public Statement apply(final Statement base, Description description) {
		return new Statement(){
			@Override
			public void evaluate() throws Throwable {
				base.evaluate();
				
				String expected = asString(_expected);
				String actual = asString(_actual);
				Assert.assertEquals("", expected, actual);
			}
		};
	}

	private String asString(List<String> list) {
		StringBuilder sb = new StringBuilder();
		for (String str : list) {
			sb.append(str).append("\n");
		}
		return sb.toString();
	}			

	public static ExpectedSummary none() {
		return new ExpectedSummary();
	}

}
