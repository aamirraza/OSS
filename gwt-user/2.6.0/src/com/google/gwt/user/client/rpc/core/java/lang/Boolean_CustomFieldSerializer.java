/*
 * Copyright 2006 Google Inc.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.google.gwt.user.client.rpc.core.java.lang;

import com.google.gwt.user.client.rpc.CustomFieldSerializer;
import com.google.gwt.user.client.rpc.SerializationException;
import com.google.gwt.user.client.rpc.SerializationStreamReader;
import com.google.gwt.user.client.rpc.SerializationStreamWriter;

/**
 * Custom field serializer for {@link java.lang.Boolean}.
 */
public final class Boolean_CustomFieldSerializer extends
    CustomFieldSerializer<Boolean> {

  @SuppressWarnings("unused")
  public static void deserialize(SerializationStreamReader streamReader,
      Boolean instance) {
    // No fields
  }

  public static Boolean instantiate(SerializationStreamReader streamReader)
      throws SerializationException {
    return Boolean.valueOf(streamReader.readBoolean());
  }

  public static void serialize(SerializationStreamWriter streamWriter,
      Boolean instance) throws SerializationException {
    streamWriter.writeBoolean(instance.booleanValue());
  }
  
  @Override
  public void deserializeInstance(SerializationStreamReader streamReader,
      Boolean instance) throws SerializationException {
    deserialize(streamReader, instance);
  }

  @Override
  public boolean hasCustomInstantiateInstance() {
    return true;
  }

  @Override
  public Boolean instantiateInstance(SerializationStreamReader streamReader)
      throws SerializationException {
    return instantiate(streamReader);
  }
  
  @Override
  public void serializeInstance(SerializationStreamWriter streamWriter,
      Boolean instance) throws SerializationException {
    serialize(streamWriter, instance);
  }
}
