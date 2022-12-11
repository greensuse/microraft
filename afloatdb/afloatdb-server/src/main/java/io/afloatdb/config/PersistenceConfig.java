/*
 * Copyright (c) 2020, AfloatDB.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.afloatdb.config;

import com.typesafe.config.Config;
import io.afloatdb.AfloatDBException;

import javax.annotation.Nonnull;

import static java.util.Objects.requireNonNull;

public class PersistenceConfig {

    private String sqliteFilePath;

    private PersistenceConfig() {
    }

    public static PersistenceConfig from(@Nonnull Config config) {
        requireNonNull(config);
        try {
            PersistenceConfigBuilder builder = new PersistenceConfigBuilder();

            if (config.hasPath("sqlite-file-path")) {
                builder.setSqliteFilePath(config.getString("sqlite-file-path"));
            }

            return builder.build();
        } catch (Exception e) {
            throw new AfloatDBException("Invalid configuration: " + config, e);
        }
    }

    public static PersistenceConfigBuilder newBuilder() {
        return new PersistenceConfigBuilder();
    }

    public String getSqliteFilePath() {
        return sqliteFilePath;
    }

    @Override
    public String toString() {
        return "PersistenceConfig{" + "sqliteFilePath=" + sqliteFilePath + '}';
    }

    public static class PersistenceConfigBuilder {

        private PersistenceConfig config = new PersistenceConfig();

        private PersistenceConfigBuilder() {
        }

        public PersistenceConfigBuilder setSqliteFilePath(String path) {
            path = requireNonNull(path).trim();
            if (path.isEmpty()) {
                throw new IllegalArgumentException("Cannot pass empty sqlite file path");
            }
            config.sqliteFilePath = path;
            return this;
        }

        public PersistenceConfig build() {
            PersistenceConfig config = this.config;
            this.config = null;
            return config;
        }

    }

}
