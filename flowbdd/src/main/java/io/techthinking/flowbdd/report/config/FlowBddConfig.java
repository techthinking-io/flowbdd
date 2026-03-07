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

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

public class FlowBddConfig {
    private static final String DEFAULT_BANNER =
        "  ______ _                 ____  _____  _____  \n" +
        " |  ____| |               |  _ \\|  __ \\|  __ \\ \n" +
        " | |__  | | _____      __ | |_) | |  | | |  | |\n" +
        " |  __| | |/ _ \\ \\ /\\ / / |  _ <| |  | | |  | |\n" +
        " | |    | | (_) \\ V  V /  | |_) | |__| | |__| |\n" +
        " |_|    |_|\\___/ \\_/\\_/   |____/|_____/|_____/ \n";

    private static final String DEFAULT_BASE_NAME = "flowbdd";
    private static final String DEFAULT_DATA_DIR = "data";
    private static final String DEFAULT_REPORT_DIR = "report";
    private static final String DEFAULT_EXTRA_DIRS = "extra";
    private static final String DEFAULT_AI_PROMPT =
        "You are Flow BDD AI, an expert assistant for the Flow BDD testing framework.\n" +
        "Your goal is to help developers understand their BDD test results based on the provided data.\n\n" +
        "Context:\n" +
        "- Flow BDD translates code-based tests into human-readable BDD scenarios (Gherkin style).\n" +
        "- A 'TestSuite' contains multiple 'TestCase' objects.\n" +
        "- Each 'TestCase' has a 'wordify' field representing the human-readable scenario, a 'status' (SUCCESS, FAILURE, IGNORED), and details about the underlying 'method'.\n" +
        "- If a test fails, the 'cause' field contains the error details.\n\n" +
        "User Question: %s\n\n" +
        "Test Suite Data:\n" +
        "%s\n\n" +
        "Please provide a clear, concise, and helpful answer. If the question is about a failure, explain what went wrong and point to the specific scenario.";

    private static final java.util.Properties properties = new java.util.Properties();

    static {
        try (java.io.InputStream is = FlowBddConfig.class.getClassLoader().getResourceAsStream("flowbdd.properties")) {
            if (is != null) {
                properties.load(is);
            }
        } catch (java.io.IOException e) {
            // Log or handle error: failed to load properties file
        }
    }
    /**
     * Set basepath, for testing on another file system you set this
     */
    private static Path overriddenBasePath = null;

    /**
     * Resolves the base directory.
     * Priority: System Property {@literal ->} Environment Variable {@literal ->} Properties File {@literal ->} Temp Dir
     * @return The base path.
     */
    public static Path getBasePath() {
        if (overriddenBasePath != null) return overriddenBasePath;

        String property = System.getProperty("flowbdd.base.dir");
        if (property != null) return Paths.get(property);

        String env = System.getenv("FLOWBDD_BASE_DIR");
        if (env != null) return Paths.get(env);

        String fileProp = properties.getProperty("flowbdd.base.dir");
        if (fileProp != null) return Paths.get(fileProp);

        return Paths.get(System.getProperty("java.io.tmpdir"), DEFAULT_BASE_NAME);
    }

    private static String getProperty(String key, String defaultValue) {
        String systemProp = System.getProperty(key);
        if (systemProp != null) return systemProp;
        return properties.getProperty(key, defaultValue);
    }

    /**
     * Gets the data path.
     * @return The data path.
     */
    public static Path getDataPath() {
        return getBasePath().resolve(getProperty("flowbdd.data.dir", DEFAULT_DATA_DIR));
    }

    /**
     * Gets the report path.
     * @return The report path.
     */
    public static Path getReportPath() {
        return getBasePath().resolve(getProperty("flowbdd.report.dir", DEFAULT_REPORT_DIR));
    }

    /**
     * Gets the extra information directories.
     * @return The extra information directories path.
     */
    public static Path getExtraDirsPath() {
        return getBasePath().resolve(getProperty("flowbdd.extra.dir", DEFAULT_EXTRA_DIRS));
    }

    /**
     * Gets the banner.
     * @return The banner string.
     */
    public static String getBanner() {
        return getProperty("flowbdd.banner", DEFAULT_BANNER);
    }

    //TODO maybe setBasePathProperty?
//    public static void setBasePath(String path) {
//        System.setProperty("flowbdd.base.dir", path);
//    }
//
//    public static void setBasePath(Path path) {
//        System.setProperty("flowbdd.base.dir", path.toString());
//    }

    /**
     * Overrides the base path.
     * @param path The path to override with.
     */
    public static void overrideBasePath(Path path) {
        overriddenBasePath = path;
    }

    /**
     * Gets the overridden base path.
     * @return An Optional containing the overridden base path if present.
     */
    public static Optional<Path> getOverriddenBasePath() {
        return Optional.ofNullable(overriddenBasePath);
    }

    /**
     * Whether to optimize JSON output for AI agents (strips timings, metadata).
     * @return True if AI optimized, false otherwise.
     */
    public static boolean isAiOptimized() {
        return Boolean.parseBoolean(getProperty("flowbdd.ai.optimized", "false"));
    }

    /**
     * Define what level of detail AI should receive: "SUMMARY", "FULL", "STEPS_ONLY"
     * @return The AI detail level.
     */
    public static String getAiDetailLevel() {
        return getProperty("flowbdd.ai.detail.level", "FULL");
    }

    /**
     * Gets the AI prompt template.
     * @return The AI prompt template.
     */
    public static String getAiPrompt() {
        return getProperty("flowbdd.ai.prompt", DEFAULT_AI_PROMPT);
    }
}
