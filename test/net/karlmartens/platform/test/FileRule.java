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

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

/**
 * @author kmartens
 *
 */
public class FileRule implements TestRule {

    private final Path _path;

    private FileRule(Path path) {
        _path = path;
    }

    public Path path() {
        return _path;
    }

    @Override
    public Statement apply(final Statement base, Description description) {
        return new Statement() {
            @Override
            public void evaluate() throws Throwable {
                try {
                    base.evaluate();
                } finally {
                    Files.deleteIfExists(_path);
                }
            }
        };
    }

    public static FileRule name(String prefix, String postfix) {
        try {
            return new FileRule(Files.createTempFile(prefix, postfix));
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

}
