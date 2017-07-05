/*
 * Copyright 2005-2017 Dozer Project
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
package org.dozer.stats;

import java.util.concurrent.atomic.AtomicLong;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Internal class that represents one entry in the statistic. Holds the statistic value and unique key for lookup.
 * Entry counter is based on AtomicLong and is Thread Safe.
 *
 * Only intended for internal use.
 * 
 * @author tierney.matt
 * @author dmitry.buzdin
 */
@Deprecated
public class StatisticEntry {

  private final Object key;
  private final AtomicLong value = new AtomicLong();

  public StatisticEntry(Object key) {
    this.key = key;
  }

  public Object getKey() {
    return key;
  }

  public long getValue() {
    return value.get();
  }

  public void increment(long value) {
    this.value.addAndGet(value);
  }

  @Override
  public boolean equals(Object object) {
    if (this == object) {
      return true;
    }

    if (!(object instanceof StatisticEntry)) {
      return false;
    }

    StatisticEntry entry = (StatisticEntry) object;
    return new EqualsBuilder().append(this.getKey(), entry.getKey()).isEquals();
  }

  @Override
  public int hashCode() {
    return key.hashCode();
  }

  @Override
  public String toString() {
    return ReflectionToStringBuilder.toString(this, ToStringStyle.MULTI_LINE_STYLE);
  }
  
}
