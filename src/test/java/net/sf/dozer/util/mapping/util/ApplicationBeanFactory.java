/*
 * Copyright 2005-2007 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.sf.dozer.util.mapping.util;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.access.BeanFactoryLocator;
import org.springframework.context.access.ContextSingletonBeanFactoryLocator;

/**
 * @author garsombke.franz
 * @author sullins.ben
 * @author tierney.matt
 * 
 */
public class ApplicationBeanFactory {

  private static BeanFactoryLocator bfl = ContextSingletonBeanFactoryLocator.getInstance();
  private static BeanFactory beanFactory = bfl.useBeanFactory("beanfactory").getFactory();

  private ApplicationBeanFactory() {
  }

  public static Object getBean(Class beanClass) {
    return beanFactory.getBean(beanClass.getName());
  }

  public static Object getBean(String beanName) {
    return beanFactory.getBean(beanName);
  }

}