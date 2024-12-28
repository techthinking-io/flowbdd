/*
 * Smart BDD - The smart way to do behavior-driven development.
 * Copyright (C)  2021  James Bayliss
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package io.bitsmart.wordify.sourcecode;

import com.thoughtworks.qdox.model.JavaParameter;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class JavaSourceWrapperGetParamsTest {

    public void methodWithParams(String param1) {
    }

    @Test
    void returnsMethodsFromClass() throws IOException {
        JavaSourceWrapper javaSourceWrapper = new JavaSourceWrapper(this.getClass());
        List<JavaParameter> params = javaSourceWrapper.getParams("methodWithParams");
        assertThat(params).hasSize(1);
        assertThat(params.get(0).getName()).isEqualTo("param1");
    }
}