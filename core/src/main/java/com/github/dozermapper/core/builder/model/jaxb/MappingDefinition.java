/*
 * Copyright 2005-2019 Dozer Project
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
package com.github.dozermapper.core.builder.model.jaxb;

import java.util.ArrayList;
import java.util.List;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlElements;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlTransient;
import jakarta.xml.bind.annotation.XmlType;
import com.github.dozermapper.core.classmap.ClassMap;
import com.github.dozermapper.core.classmap.Configuration;
import com.github.dozermapper.core.classmap.MappingDirection;
import com.github.dozermapper.core.classmap.RelationshipType;
import com.github.dozermapper.core.config.BeanContainer;
import com.github.dozermapper.core.factory.DestBeanCreator;
import com.github.dozermapper.core.fieldmap.FieldMap;
import com.github.dozermapper.core.propertydescriptor.PropertyDescriptorFactory;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * Specifies a custom mapping definition between two classes(data types). All Mapping definitions are
 * bi-directional by default.
 * Global configuration element values are inherited
 * <p>
 * Required Attributes:
 * <p>
 * Optional Attributes:
 * <p>
 * date-format The string format of Date fields. This is used for field mapping between Strings and Dates
 * <p>
 * stop-on-errors Indicates whether Dozer should stop mapping fields and throw the Exception if an error is
 * encountered while performing a field mapping. It is recommended that this is set to "true".
 * If set to "false", Dozer will trap the exception, log the error, and then continue mapping subsequent fields
 * The default value is "true"
 * <p>
 * wildcard Indicates whether Dozer automatically map fields that have the same name. The default value is "true"
 * <p>
 * wildcard-case-insensitive Indicates whether Dozer should ignore the case of field names when applying wildcard mapping.
 * The default value is "false"
 * <p>
 * trim-strings Indicates whether Dozer automatically trims String values prior to setting the destination value.
 * The default value is "false"
 * <p>
 * map-null Indicates whether null values are mapped. The default value is "true"
 * <p>
 * map-empty-string Indicates whether empty string values are mapped. The default value is "true"
 * <p>
 * bean-factory The factory class to create data objects. This typically will not be specified.
 * By default Dozer constructs new instances of data objects by invoking the no-arg constructor
 * <p>
 * type Indicates whether this mapping is bi-directional or only one-way. Typically this will be set to
 * bi-directional. The default is "bi-directional".
 * <p>
 * map-id The id that uniquely identifies this mapping definition. This typically will not be specified.
 * You would only need to specify this for only need this for special context based mapping
 * and when mapping between Map objects and Custom Data Objects.
 * <p>
 * relationship-type Indications whether collections are mapped cumulative or non-cumulative. cumulative indicates
 * the element is added to the collection.
 * <p>
 * non-cumulative indicates the element will be added or an existing entry will be updated.
 */
@ToString
@EqualsAndHashCode
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "")
@XmlRootElement(name = "mapping")
public class MappingDefinition {

    @XmlTransient
    private final MappingsDefinition parent;

    @XmlElement(name = "class-a", required = true)
    protected ClassDefinition classA;

    @XmlElement(name = "class-b", required = true)
    protected ClassDefinition classB;

    @XmlElements({
            @XmlElement(name = "field", type = FieldDefinition.class),
            @XmlElement(name = "field-exclude", type = FieldExcludeDefinition.class)
    })
    protected List<Object> fieldOrFieldExclude;

    @XmlTransient
    protected List<FieldDefinition> fields;

    @XmlTransient
    protected List<FieldExcludeDefinition> fieldExcludes;

    @XmlAttribute(name = "date-format")
    protected String dateFormat;

    @XmlAttribute(name = "stop-on-errors")
    protected Boolean stopOnErrors;

    @XmlAttribute(name = "wildcard")
    protected Boolean wildcard;

    @XmlAttribute(name = "wildcard-case-insensitive")
    protected Boolean wildcardCaseInsensitive;

    @XmlAttribute(name = "trim-strings")
    protected Boolean trimStrings;

    @XmlAttribute(name = "map-null")
    protected Boolean mapNull;

    @XmlAttribute(name = "map-empty-string")
    protected Boolean mapEmptyString;

    @XmlAttribute(name = "bean-factory")
    protected String beanFactory;

    @XmlAttribute(name = "type")
    protected Type type;

    @XmlAttribute(name = "relationship-type")
    protected Relationship relationshipType;

    @XmlAttribute(name = "map-id")
    protected String mapId;

    public MappingDefinition() {
        this(null);
    }

    public MappingDefinition(MappingsDefinition parent) {
        this.parent = parent;
    }

    // Fluent API
    //-------------------------------------------------------------------------
    public ClassDefinition withClassA() {
        ClassDefinition classA = new ClassDefinition(this, null);
        setClassA(classA);

        return classA;
    }

    public ClassDefinition withClassB() {
        ClassDefinition classB = new ClassDefinition(this, null);
        setClassB(classB);

        return classB;
    }

    public FieldDefinition withField() {
        if (getFields() == null) {
            setFields(new ArrayList<>());
        }

        FieldDefinition field = new FieldDefinition(this);
        getFields().add(field);

        return field;
    }

    public FieldExcludeDefinition withFieldExclude() {
        if (getFieldExcludes() == null) {
            setFieldExcludes(new ArrayList<>());
        }

        FieldExcludeDefinition fieldExclude = new FieldExcludeDefinition(this);
        getFieldExcludes().add(fieldExclude);

        return fieldExclude;
    }

    public MappingDefinition withDateFormat(String dateFormat) {
        setDateFormat(dateFormat);

        return this;
    }

    public MappingDefinition withStopOnErrors(Boolean stopOnErrors) {
        setStopOnErrors(stopOnErrors);

        return this;
    }

    public MappingDefinition withWildcard(Boolean wildcard) {
        setWildcard(wildcard);

        return this;
    }

    public MappingDefinition withWildcardCaseInsensitive(Boolean wildcardCaseInsensitive) {
        setWildcardCaseInsensitive(wildcardCaseInsensitive);

        return this;
    }

    public MappingDefinition withTrimStrings(Boolean trimStrings) {
        setTrimStrings(trimStrings);

        return this;
    }

    public MappingDefinition withMapNull(Boolean mapNull) {
        setMapNull(mapNull);

        return this;
    }

    public MappingDefinition withMapEmptyString(Boolean mapEmptyString) {
        setMapEmptyString(mapEmptyString);

        return this;
    }

    public MappingDefinition withBeanFactory(String beanFactory) {
        setBeanFactory(beanFactory);

        return this;
    }

    public MappingDefinition withType(Type type) {
        setType(type);

        return this;
    }

    public MappingDefinition withRelationshipType(Relationship relationshipType) {
        setRelationshipType(relationshipType);

        return this;
    }

    public MappingDefinition withMapId(String mapId) {
        setMapId(mapId);

        return this;
    }

    public MappingsDefinition end() {
        return parent;
    }

    public ClassMap build(Configuration configuration, BeanContainer beanContainer, DestBeanCreator destBeanCreator, PropertyDescriptorFactory propertyDescriptorFactory) {
        separateFieldOrFieldExclude();

        ClassMap current = new ClassMap(configuration);
        current.setSrcClass(classA.build(beanContainer));
        current.setDestClass(classB.build(beanContainer));
        current.setType(MappingDirection.valueOf(type == null ? Type.BI_DIRECTIONAL.value() : type.value()));
        current.setDateFormat(dateFormat);
        current.setBeanFactory(beanFactory);

        if (mapNull != null) {
            current.setMapNull(mapNull);
        }

        if (mapEmptyString != null) {
            current.setMapEmptyString(mapEmptyString);
        }

        current.setWildcard(wildcard);
        current.setWildcardCaseInsensitive(wildcardCaseInsensitive);
        current.setStopOnErrors(stopOnErrors);
        current.setTrimStrings(trimStrings);
        current.setMapId(mapId);
        current.setRelationshipType(RelationshipType.valueOf(relationshipType == null ? Relationship.CUMULATIVE.value() : relationshipType.value()));
        current.setFieldMaps(convertFieldMap(current, beanContainer, destBeanCreator, propertyDescriptorFactory));

        return current;
    }

    private void separateFieldOrFieldExclude() {
        if (fields == null) {
            fields = new ArrayList<>();
        }

        if (fieldExcludes == null) {
            fieldExcludes = new ArrayList<>();
        }

        if (fieldOrFieldExclude != null) {
            for (Object current : fieldOrFieldExclude) {
                if (current instanceof FieldDefinition) {
                    fields.add((FieldDefinition)current);
                } else if (current instanceof FieldExcludeDefinition) {
                    fieldExcludes.add((FieldExcludeDefinition)current);
                } else {
                    throw new IllegalStateException("Unexpected type found; " + current.getClass().getCanonicalName());
                }
            }
        }
    }

    private List<FieldMap> convertFieldMap(ClassMap classMap, BeanContainer beanContainer, DestBeanCreator destBeanCreator, PropertyDescriptorFactory propertyDescriptorFactory) {
        List<FieldMap> answer = new ArrayList<>();
        if (fields != null) {
            for (FieldDefinition current : fields) {
                answer.add(current.build(classMap, beanContainer, destBeanCreator, propertyDescriptorFactory));
            }
        }

        if (fieldExcludes != null) {
            for (FieldExcludeDefinition current : fieldExcludes) {
                answer.add(current.build(classMap, beanContainer, destBeanCreator, propertyDescriptorFactory));
            }
        }

        return answer;
    }

    public MappingsDefinition getParent() {
        return parent;
    }

    public ClassDefinition getClassA() {
        return classA;
    }

    protected void setClassA(ClassDefinition classA) {
        this.classA = classA;
    }

    public ClassDefinition getClassB() {
        return classB;
    }

    protected void setClassB(ClassDefinition classB) {
        this.classB = classB;
    }

    public List<Object> getFieldOrFieldExclude() {
        return fieldOrFieldExclude;
    }

    protected void setFieldOrFieldExclude(List<Object> fieldOrFieldExclude) {
        this.fieldOrFieldExclude = fieldOrFieldExclude;
    }

    public List<FieldDefinition> getFields() {
        return fields;
    }

    protected void setFields(List<FieldDefinition> fields) {
        this.fields = fields;
    }

    public List<FieldExcludeDefinition> getFieldExcludes() {
        return fieldExcludes;
    }

    protected void setFieldExcludes(List<FieldExcludeDefinition> fieldExcludes) {
        this.fieldExcludes = fieldExcludes;
    }

    public String getDateFormat() {
        return dateFormat;
    }

    protected void setDateFormat(String dateFormat) {
        this.dateFormat = dateFormat;
    }

    public Boolean getStopOnErrors() {
        return stopOnErrors;
    }

    protected void setStopOnErrors(Boolean stopOnErrors) {
        this.stopOnErrors = stopOnErrors;
    }

    public Boolean getWildcard() {
        return wildcard;
    }

    protected void setWildcard(Boolean wildcard) {
        this.wildcard = wildcard;
    }

    public Boolean getWildcardCaseInsensitive() {
        return wildcardCaseInsensitive;
    }

    protected void setWildcardCaseInsensitive(Boolean wildcardCaseInsensitive) {
        this.wildcardCaseInsensitive = wildcardCaseInsensitive;
    }

    public Boolean getTrimStrings() {
        return trimStrings;
    }

    protected void setTrimStrings(Boolean trimStrings) {
        this.trimStrings = trimStrings;
    }

    public Boolean getMapNull() {
        return mapNull;
    }

    protected void setMapNull(Boolean mapNull) {
        this.mapNull = mapNull;
    }

    public Boolean getMapEmptyString() {
        return mapEmptyString;
    }

    protected void setMapEmptyString(Boolean mapEmptyString) {
        this.mapEmptyString = mapEmptyString;
    }

    public String getBeanFactory() {
        return beanFactory;
    }

    protected void setBeanFactory(String beanFactory) {
        this.beanFactory = beanFactory;
    }

    public Type getType() {
        return type;
    }

    protected void setType(Type type) {
        this.type = type;
    }

    public Relationship getRelationshipType() {
        return relationshipType;
    }

    protected void setRelationshipType(Relationship relationshipType) {
        this.relationshipType = relationshipType;
    }

    public String getMapId() {
        return mapId;
    }

    protected void setMapId(String mapId) {
        this.mapId = mapId;
    }
}
