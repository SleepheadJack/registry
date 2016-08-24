/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package com.hortonworks.registries.schemaregistry.serde.pull;

import com.hortonworks.iotas.common.exception.ParserException;
import com.hortonworks.registries.schemaregistry.Resourceable;

/**
 * This is a pull based deserializer which deserializes the given payload and user can call {@link #hasNext()} and
 * {@link #next()} methods to retrieve the parsed content.
 *
 * @param <S> type to represent schema related information.
 * @param <F> type to represent a field in a schema.
 */
public interface PullDeserializer<S, F> extends Resourceable {

    enum State {
        /**
         * Represents start of processing the given payload.
         */
        START_DESERIALIZE,

        /**
         * Represents start of the record
         */
        START_RECORD,

        /**
         * Represents processing of field in the current event
         */
        PROCESS_FIELDS,

        /**
         * Represents start of a field
         */
        START_FIELD,

        /**
         * Represents reading the current field value
         */
        READ_FIELD_VALUE,

        /**
         * Represents end of the current field
         */
        END_FIELD,

        /**
         * Represents end of the current record.
         */
        END_RECORD,

        /**
         * Represents end of parsing the given payload and parser can be closed.
         */
        END_DESERIALIZE
    }

    /**
     * Returns true if it contain next record/field in the given payload.
     * Returns false when it reaches end of the payload.
     *
     * @return
     */
    boolean hasNext() throws ParserException;

    /**
     * Returns the next record/field's {@link PullEventContext} in the payload given to the deserializer.
     *
     * @return
     */
    PullEventContext<F> next() throws ParserException;

    /**
     * @return S which is about schema related information
     */
    S schema();
}