package net.karlmartens.platform.test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import org.junit.Assert;
import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

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
