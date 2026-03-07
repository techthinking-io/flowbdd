/*
 * Flow BDD - The most productive way to test.
 * Copyright (C) 2021-2025 James Bayliss.
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

package io.techthinking.flowbdd.report.config;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class FlowBddConfigTest {

    @AfterEach
    void tearDown() {
        System.clearProperty("flowbdd.banner");
        System.clearProperty("flowbdd.ai.optimized");
        System.clearProperty("flowbdd.ai.detail.level");
    }

    @Test
    void isAiOptimized_returnsDefaultFalse() {
        assertThat(FlowBddConfig.isAiOptimized()).isFalse();
    }

    @Test
    void isAiOptimized_returnsConfiguredValue() {
        System.setProperty("flowbdd.ai.optimized", "true");
        assertThat(FlowBddConfig.isAiOptimized()).isTrue();
    }

    @Test
    void getAiDetailLevel_returnsDefaultFull() {
        assertThat(FlowBddConfig.getAiDetailLevel()).isEqualTo("FULL");
    }

    @Test
    void getAiDetailLevel_returnsConfiguredValue() {
        System.setProperty("flowbdd.ai.detail.level", "SUMMARY");
        assertThat(FlowBddConfig.getAiDetailLevel()).isEqualTo("SUMMARY");
    }

    @Test
    void getBanner_returnsDefaultBanner() {
        String banner = FlowBddConfig.getBanner();
        assertThat(banner).contains("______");
        assertThat(banner).contains("|  _ \\");
    }

    @Test
    void getBanner_returnsConfiguredBanner() {
        String customBanner = "Custom Banner";
        System.setProperty("flowbdd.banner", customBanner);
        
        String banner = FlowBddConfig.getBanner();
        assertThat(banner).isEqualTo(customBanner);
    }
}
