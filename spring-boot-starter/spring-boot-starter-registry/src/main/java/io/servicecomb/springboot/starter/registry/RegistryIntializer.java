/*
 * Copyright 2017 Huawei Technologies Co., Ltd
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.servicecomb.springboot.starter.registry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.netflix.config.DynamicPropertyFactory;

import io.servicecomb.serviceregistry.RegistryUtils;

/**
* Initialize and Register the services with service center 
*/
public class RegistryIntializer {
  private static final Logger LOG = LoggerFactory.getLogger(RegistryIntializer.class);

  private RegistryIntializer() {

  }

  public static void initRegistry() {
    String address = DynamicPropertyFactory.getInstance().getStringProperty("cse.rest.address", null).get();
    if (null != address) {
      try {
        RegistryUtils.init();
        RegistryUtils.getMicroserviceInstance().getEndpoints().add(RegistryUtils.getPublishAddress("rest", address));
        RegistryUtils.run();
      } catch (Exception e) {
        LOG.error("init registry error.", e);
      }
    } else {
      LOG.info("rest address is null.Service is not registered to service center");
    }
  }
}
