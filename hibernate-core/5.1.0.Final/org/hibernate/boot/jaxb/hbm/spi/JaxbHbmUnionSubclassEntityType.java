//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.11 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2016.02.10 at 11:20:59 AM CST 
//


package org.hibernate.boot.jaxb.hbm.spi;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlType;


/**
 * 
 *                 Union subclasses are used for the table-per-concrete-class mapping strategy
 *                 See the note on the class element regarding <pojo/> vs. @name usage...
 *             
 * 
 * <p>Java class for UnionSubclassEntityType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="UnionSubclassEntityType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://www.hibernate.org/xsd/orm/hbm}SubclassEntityBaseDefinition"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="tuplizer" type="{http://www.hibernate.org/xsd/orm/hbm}tuplizer-type" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="subselect" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="synchronize" type="{http://www.hibernate.org/xsd/orm/hbm}synchronize-type" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="comment" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;choice maxOccurs="unbounded" minOccurs="0"&gt;
 *           &lt;element name="idbag" type="{http://www.hibernate.org/xsd/orm/hbm}IdBagCollectionType"/&gt;
 *           &lt;group ref="{http://www.hibernate.org/xsd/orm/hbm}SingularAttributeGroup"/&gt;
 *         &lt;/choice&gt;
 *         &lt;element name="union-subclass" type="{http://www.hibernate.org/xsd/orm/hbm}UnionSubclassEntityType" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="loader" type="{http://www.hibernate.org/xsd/orm/hbm}loader-type" minOccurs="0"/&gt;
 *         &lt;group ref="{http://www.hibernate.org/xsd/orm/hbm}CustomSqlDmlGroup" minOccurs="0"/&gt;
 *         &lt;element name="fetch-profile" type="{http://www.hibernate.org/xsd/orm/hbm}FetchProfileType" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="resultset" type="{http://www.hibernate.org/xsd/orm/hbm}ResultSetMappingType" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;choice maxOccurs="unbounded" minOccurs="0"&gt;
 *           &lt;group ref="{http://www.hibernate.org/xsd/orm/hbm}NamedQueryGroup"/&gt;
 *         &lt;/choice&gt;
 *       &lt;/sequence&gt;
 *       &lt;attGroup ref="{http://www.hibernate.org/xsd/orm/hbm}table-information-group"/&gt;
 *       &lt;attribute name="check" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "UnionSubclassEntityType", propOrder = {
    "tuplizer",
    "subselect",
    "synchronize",
    "comment",
    "attributes",
    "unionSubclass",
    "loader",
    "sqlInsert",
    "sqlUpdate",
    "sqlDelete",
    "fetchProfile",
    "resultset",
    "query",
    "sqlQuery"
})
public class JaxbHbmUnionSubclassEntityType
    extends JaxbHbmSubclassEntityBaseDefinition
    implements Serializable, SubEntityInfo, TableInformationContainer, ToolingHintContainer
{

    protected List<JaxbHbmTuplizerType> tuplizer;
    protected String subselect;
    protected List<JaxbHbmSynchronizeType> synchronize;
    protected String comment;
    @XmlElements({
        @XmlElement(name = "idbag", type = JaxbHbmIdBagCollectionType.class),
        @XmlElement(name = "property", type = JaxbHbmBasicAttributeType.class),
        @XmlElement(name = "many-to-one", type = JaxbHbmManyToOneType.class),
        @XmlElement(name = "one-to-one", type = JaxbHbmOneToOneType.class),
        @XmlElement(name = "component", type = JaxbHbmCompositeAttributeType.class),
        @XmlElement(name = "dynamic-component", type = JaxbHbmDynamicComponentType.class),
        @XmlElement(name = "properties", type = JaxbHbmPropertiesType.class),
        @XmlElement(name = "any", type = JaxbHbmAnyAssociationType.class),
        @XmlElement(name = "map", type = JaxbHbmMapType.class),
        @XmlElement(name = "set", type = JaxbHbmSetType.class),
        @XmlElement(name = "list", type = JaxbHbmListType.class),
        @XmlElement(name = "bag", type = JaxbHbmBagCollectionType.class),
        @XmlElement(name = "array", type = JaxbHbmArrayType.class),
        @XmlElement(name = "primitive-array", type = JaxbHbmPrimitiveArrayType.class)
    })
    protected List<Serializable> attributes;
    @XmlElement(name = "union-subclass")
    protected List<JaxbHbmUnionSubclassEntityType> unionSubclass;
    protected JaxbHbmLoaderType loader;
    @XmlElement(name = "sql-insert")
    protected JaxbHbmCustomSqlDmlType sqlInsert;
    @XmlElement(name = "sql-update")
    protected JaxbHbmCustomSqlDmlType sqlUpdate;
    @XmlElement(name = "sql-delete")
    protected JaxbHbmCustomSqlDmlType sqlDelete;
    @XmlElement(name = "fetch-profile")
    protected List<JaxbHbmFetchProfileType> fetchProfile;
    protected List<JaxbHbmResultSetMappingType> resultset;
    protected List<JaxbHbmNamedQueryType> query;
    @XmlElement(name = "sql-query")
    protected List<JaxbHbmNamedNativeQueryType> sqlQuery;
    @XmlAttribute(name = "check")
    protected String check;
    @XmlAttribute(name = "schema")
    protected String schema;
    @XmlAttribute(name = "catalog")
    protected String catalog;
    @XmlAttribute(name = "table")
    protected String table;
    @XmlAttribute(name = "subselect")
    protected String subselectAttribute;

    /**
     * Gets the value of the tuplizer property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the tuplizer property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getTuplizer().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link JaxbHbmTuplizerType }
     * 
     * 
     */
    public List<JaxbHbmTuplizerType> getTuplizer() {
        if (tuplizer == null) {
            tuplizer = new ArrayList<JaxbHbmTuplizerType>();
        }
        return this.tuplizer;
    }

    /**
     * Gets the value of the subselect property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSubselect() {
        return subselect;
    }

    /**
     * Sets the value of the subselect property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSubselect(String value) {
        this.subselect = value;
    }

    /**
     * Gets the value of the synchronize property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the synchronize property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSynchronize().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link JaxbHbmSynchronizeType }
     * 
     * 
     */
    public List<JaxbHbmSynchronizeType> getSynchronize() {
        if (synchronize == null) {
            synchronize = new ArrayList<JaxbHbmSynchronizeType>();
        }
        return this.synchronize;
    }

    /**
     * Gets the value of the comment property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getComment() {
        return comment;
    }

    /**
     * Sets the value of the comment property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setComment(String value) {
        this.comment = value;
    }

    /**
     * Gets the value of the attributes property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the attributes property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAttributes().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link JaxbHbmIdBagCollectionType }
     * {@link JaxbHbmBasicAttributeType }
     * {@link JaxbHbmManyToOneType }
     * {@link JaxbHbmOneToOneType }
     * {@link JaxbHbmCompositeAttributeType }
     * {@link JaxbHbmDynamicComponentType }
     * {@link JaxbHbmPropertiesType }
     * {@link JaxbHbmAnyAssociationType }
     * {@link JaxbHbmMapType }
     * {@link JaxbHbmSetType }
     * {@link JaxbHbmListType }
     * {@link JaxbHbmBagCollectionType }
     * {@link JaxbHbmArrayType }
     * {@link JaxbHbmPrimitiveArrayType }
     * 
     * 
     */
    public List<Serializable> getAttributes() {
        if (attributes == null) {
            attributes = new ArrayList<Serializable>();
        }
        return this.attributes;
    }

    /**
     * Gets the value of the unionSubclass property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the unionSubclass property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getUnionSubclass().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link JaxbHbmUnionSubclassEntityType }
     * 
     * 
     */
    public List<JaxbHbmUnionSubclassEntityType> getUnionSubclass() {
        if (unionSubclass == null) {
            unionSubclass = new ArrayList<JaxbHbmUnionSubclassEntityType>();
        }
        return this.unionSubclass;
    }

    /**
     * Gets the value of the loader property.
     * 
     * @return
     *     possible object is
     *     {@link JaxbHbmLoaderType }
     *     
     */
    public JaxbHbmLoaderType getLoader() {
        return loader;
    }

    /**
     * Sets the value of the loader property.
     * 
     * @param value
     *     allowed object is
     *     {@link JaxbHbmLoaderType }
     *     
     */
    public void setLoader(JaxbHbmLoaderType value) {
        this.loader = value;
    }

    /**
     * Gets the value of the sqlInsert property.
     * 
     * @return
     *     possible object is
     *     {@link JaxbHbmCustomSqlDmlType }
     *     
     */
    public JaxbHbmCustomSqlDmlType getSqlInsert() {
        return sqlInsert;
    }

    /**
     * Sets the value of the sqlInsert property.
     * 
     * @param value
     *     allowed object is
     *     {@link JaxbHbmCustomSqlDmlType }
     *     
     */
    public void setSqlInsert(JaxbHbmCustomSqlDmlType value) {
        this.sqlInsert = value;
    }

    /**
     * Gets the value of the sqlUpdate property.
     * 
     * @return
     *     possible object is
     *     {@link JaxbHbmCustomSqlDmlType }
     *     
     */
    public JaxbHbmCustomSqlDmlType getSqlUpdate() {
        return sqlUpdate;
    }

    /**
     * Sets the value of the sqlUpdate property.
     * 
     * @param value
     *     allowed object is
     *     {@link JaxbHbmCustomSqlDmlType }
     *     
     */
    public void setSqlUpdate(JaxbHbmCustomSqlDmlType value) {
        this.sqlUpdate = value;
    }

    /**
     * Gets the value of the sqlDelete property.
     * 
     * @return
     *     possible object is
     *     {@link JaxbHbmCustomSqlDmlType }
     *     
     */
    public JaxbHbmCustomSqlDmlType getSqlDelete() {
        return sqlDelete;
    }

    /**
     * Sets the value of the sqlDelete property.
     * 
     * @param value
     *     allowed object is
     *     {@link JaxbHbmCustomSqlDmlType }
     *     
     */
    public void setSqlDelete(JaxbHbmCustomSqlDmlType value) {
        this.sqlDelete = value;
    }

    /**
     * Gets the value of the fetchProfile property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the fetchProfile property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getFetchProfile().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link JaxbHbmFetchProfileType }
     * 
     * 
     */
    public List<JaxbHbmFetchProfileType> getFetchProfile() {
        if (fetchProfile == null) {
            fetchProfile = new ArrayList<JaxbHbmFetchProfileType>();
        }
        return this.fetchProfile;
    }

    /**
     * Gets the value of the resultset property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the resultset property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getResultset().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link JaxbHbmResultSetMappingType }
     * 
     * 
     */
    public List<JaxbHbmResultSetMappingType> getResultset() {
        if (resultset == null) {
            resultset = new ArrayList<JaxbHbmResultSetMappingType>();
        }
        return this.resultset;
    }

    /**
     * Gets the value of the query property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the query property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getQuery().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link JaxbHbmNamedQueryType }
     * 
     * 
     */
    public List<JaxbHbmNamedQueryType> getQuery() {
        if (query == null) {
            query = new ArrayList<JaxbHbmNamedQueryType>();
        }
        return this.query;
    }

    /**
     * Gets the value of the sqlQuery property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the sqlQuery property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSqlQuery().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link JaxbHbmNamedNativeQueryType }
     * 
     * 
     */
    public List<JaxbHbmNamedNativeQueryType> getSqlQuery() {
        if (sqlQuery == null) {
            sqlQuery = new ArrayList<JaxbHbmNamedNativeQueryType>();
        }
        return this.sqlQuery;
    }

    /**
     * Gets the value of the check property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCheck() {
        return check;
    }

    /**
     * Sets the value of the check property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCheck(String value) {
        this.check = value;
    }

    /**
     * Gets the value of the schema property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSchema() {
        return schema;
    }

    /**
     * Sets the value of the schema property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSchema(String value) {
        this.schema = value;
    }

    /**
     * Gets the value of the catalog property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCatalog() {
        return catalog;
    }

    /**
     * Sets the value of the catalog property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCatalog(String value) {
        this.catalog = value;
    }

    /**
     * Gets the value of the table property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTable() {
        return table;
    }

    /**
     * Sets the value of the table property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTable(String value) {
        this.table = value;
    }

    /**
     * Gets the value of the subselectAttribute property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSubselectAttribute() {
        return subselectAttribute;
    }

    /**
     * Sets the value of the subselectAttribute property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSubselectAttribute(String value) {
        this.subselectAttribute = value;
    }

}
