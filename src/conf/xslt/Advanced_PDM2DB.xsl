<xsl:stylesheet version="1.0"
exclude-result-prefixes="a c o"
xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
xmlns:a="attribute" xmlns:c="collection" xmlns:o="object"
>
<!--doctype-public="-//OFBiz//DTD Entity Model//EN" doctype-system="http://www.ofbiz.org/dtds/entitymodel.dtd"-->
  <xsl:output method="xml"  indent="yes"  encoding="GB2312" />
  <xsl:variable name="vGlobal_Generated_Tables" select="//o:Table[@Id and not(a:Generated)]"/>
  <xsl:variable name="vGlobal_Tables" select="//o:Table[@Id]"/>
  <xsl:variable name="vGlobal_Columns" select="//o:Column[@Id]"/>
  <xsl:variable name="vGlobal_Pks" select="//o:Key[@Id]"/>
  <xsl:variable name="vGlobal_Reference" select="//o:Reference[@Id]"/>

  <xsl:variable name="vGlobal_Generated_Views" select="//o:View[@Id and not(a:Generated)]"/>
  <xsl:variable name="vGlobal_Views" select="//o:View[@Id]"/>
  <xsl:variable name="vGlobal_ViewColumns" select="//o:ViewColumn[@Id]"/>

  <!-- <xsl:variable name="vConst_PackageName" select="'default-package'"/> -->
  <xsl:variable name="vConst_PackageName" select="'com.xiaoran27.db.entity.pojo.'"/>
  <xsl:template match="Model">
    <entitymodel>
      <xsl:variable name="vModel" select="o:RootObject/c:Children/o:Model"/>
      <title>
        <xsl:value-of select="$vModel/a:Name"/>
      </title>
      <description>
        <xsl:value-of select="$vModel/a:Comment"/>
        <xsl:text> Entity Table's Count: </xsl:text><xsl:value-of select="count($vGlobal_Generated_Tables)"/>
        <xsl:text> Entity View's Count: </xsl:text><xsl:value-of select="count($vGlobal_Generated_Views)"/>
      </description>
<!--
      <copyright>Copyright (c) 2002 The Open For Business Project - www.ofbiz.org</copyright>
-->
      <author>
        <xsl:value-of select="$vModel/a:Author"/>
      </author>
      <version>
        <xsl:value-of select="$vModel/a:Version"/>
      </version>
      <xsl:apply-templates select="$vGlobal_Generated_Tables"/>
      <xsl:apply-templates select="$vGlobal_Generated_Views"/>
    </entitymodel>
  </xsl:template>
  <xsl:template match="o:Table">
    <xsl:variable name="v_Table" select="."/>
    <entity>
      <xsl:attribute name="entity-name">
        <xsl:value-of select="a:Code"/>
      </xsl:attribute>
      <xsl:attribute name="package-name"><xsl:value-of select="$vConst_PackageName"/></xsl:attribute>
      <xsl:attribute name="comment"><xsl:value-of select="a:Comment"/></xsl:attribute>
        <xsl:attribute name="title">
          <xsl:value-of select="a:Name"/>
        </xsl:attribute>
<xsl:attribute name="description"><xsl:value-of select="a:Comment"/></xsl:attribute>
      <xsl:for-each select="c:Columns/o:Column[a:Code]">
        <field name="{a:Code}" type="{a:DataType}">
          <xsl:attribute name="java">
            <xsl:call-template name="javatype">
              <xsl:with-param name="pType" select="a:DataType"/>
            </xsl:call-template>
          </xsl:attribute>
        <xsl:attribute name="length">
          <xsl:value-of select="a:Length"/>
        </xsl:attribute>
        <xsl:attribute name="title">
          <xsl:value-of select="a:Name"/>
        </xsl:attribute>
<xsl:attribute name="description"><xsl:value-of select="a:Comment"/></xsl:attribute>
    <xsl:if test="a:Mandatory='1'">
          <xsl:attribute name="mandatory">true</xsl:attribute>
    </xsl:if>
    <xsl:attribute name="value">
          <xsl:value-of select="a:DefaultValue"/>
        </xsl:attribute>
        </field>
      </xsl:for-each>
      <!--for ref column-->
      <xsl:for-each select="c:Columns/o:Column[@Ref]">
        <xsl:variable name="the_column" select="."/>
        <xsl:variable name="refColumn" select="$vGlobal_Columns[@Id=$the_column/@Ref]"/>
        <field name="{$refColumn/a:Code}" type="{$refColumn/a:DataType}">
          <xsl:attribute name="java">
            <xsl:call-template name="javatype">
              <xsl:with-param name="pType" select="$refColumn/a:DataType"/>
            </xsl:call-template>
          </xsl:attribute>
        <xsl:attribute name="title">
          <xsl:value-of select="$refColumn/a:Name"/>
        </xsl:attribute>
    <xsl:if test="$refColumn/a:Comment">
          <xsl:attribute name="description">
        <xsl:value-of select="$refColumn/a:Comment"/>
          </xsl:attribute>
    </xsl:if>
        </field>
      </xsl:for-each>

      <xsl:variable name="vPk_field" select="c:PrimaryKey/o:Key/@Ref"/>
      <xsl:choose>
      <xsl:when test="c:Keys/o:Key[@Id=$vPk_field]/c:Key.Columns/o:Column"><!-- PrimaryKey -->
        <xsl:for-each select="c:Keys/o:Key[@Id=$vPk_field]/c:Key.Columns/o:Column">
          <prim-key>
            <xsl:attribute name="field">
              <xsl:variable name="vPk_field_column" select="@Ref"/>
              <xsl:value-of select="$v_Table/c:Columns/o:Column[@Id=$vPk_field_column]/a:Code"/>
            </xsl:attribute>
          </prim-key>
        </xsl:for-each>
    </xsl:when>
    <xsl:otherwise>
        <xsl:for-each select="$vGlobal_Pks[@Id=$vPk_field]/c:Key.Columns/o:Column">
          <prim-key>
            <xsl:attribute name="field">
              <xsl:value-of select="a:Code"/>
            </xsl:attribute>
          </prim-key>
        </xsl:for-each>
    </xsl:otherwise>
    </xsl:choose>

    <xsl:for-each select="c:Indexes/o:Index">
       <xsl:if test="a:Unique='1'">

         <uniqueindex>
           <xsl:attribute name="name">
              <xsl:value-of select="a:Code"/>
           </xsl:attribute>

           <xsl:attribute name="fields">
           <xsl:for-each select="c:IndexColumns/o:IndexColumn">
              <xsl:variable name="the_column" select="c:Column/o:Column"/>
              <xsl:variable name="refColumn" select="$vGlobal_Columns[@Id=$the_column/@Ref]"/>
              <xsl:value-of select="$refColumn/a:Code"/><xsl:if test="position()!=last()">,</xsl:if>
           </xsl:for-each>
           </xsl:attribute>
         </uniqueindex>
       </xsl:if>
    </xsl:for-each>

      <xsl:for-each select="$vGlobal_Reference[c:Object2/o:Table/@Ref=$v_Table/@Id]">
        <relation>
          <xsl:call-template name="call_relation_parent"/>
          <xsl:variable name="vRel_Table_Id" select="c:Object1/o:Table/@Ref"/>
          <xsl:attribute name="rel-entity-name">
            <xsl:value-of select="//o:Table[@Id][@Id=$vRel_Table_Id]/a:Code"/>
          </xsl:attribute>
          <xsl:for-each select="c:Joins/o:ReferenceJoin">
            <key-map>
              <xsl:variable name="vField_Id" select="c:Object2/o:Column/@Ref"/>
              <xsl:variable name="vField_Name" select="$vGlobal_Columns[@Id=$vField_Id]/a:Code"/>
              <xsl:variable name="vRel_Field_Id" select="c:Object1/o:Column/@Ref"/>
              <xsl:variable name="vRel_Field_Name" select="$vGlobal_Columns[@Id=$vRel_Field_Id]/a:Code"/>
              <xsl:attribute name="field-name">
                <xsl:value-of select="$vField_Name"/>
              </xsl:attribute>
              <xsl:if test="$vField_Name!=$vRel_Field_Name">
                <xsl:attribute name="rel-field-name">
                  <xsl:value-of select="$vRel_Field_Name"/>
                </xsl:attribute>
              </xsl:if>
            </key-map>
          </xsl:for-each>
        </relation>
      </xsl:for-each>
      <xsl:for-each select="$vGlobal_Reference[c:Object1/o:Table/@Ref=$v_Table/@Id]">
        <relation>
          <xsl:call-template name="call_relation_child"/>
          <xsl:variable name="vRel_Table_Id" select="c:Object2/o:Table/@Ref"/>
          <xsl:attribute name="rel-entity-name">
           <xsl:value-of select="//o:Table[@Id][@Id=$vRel_Table_Id]/a:Code"/>
          </xsl:attribute>
          <xsl:for-each select="c:Joins/o:ReferenceJoin">
            <key-map>
              <xsl:variable name="vField_Id" select="c:Object1/o:Column/@Ref"/>
              <xsl:variable name="vField_Name" select="$vGlobal_Columns[@Id=$vField_Id]/a:Code"/>
              <xsl:variable name="vRel_Field_Id" select="c:Object2/o:Column/@Ref"/>
              <xsl:variable name="vRel_Field_Name" select="$vGlobal_Columns[@Id=$vRel_Field_Id]/a:Code"/>
              <xsl:attribute name="field-name">
                <xsl:value-of select="$vField_Name"/>
              </xsl:attribute>
              <xsl:if test="$vField_Name!=$vRel_Field_Name">
                <xsl:attribute name="rel-field-name">
                  <xsl:value-of select="$vRel_Field_Name"/>
                </xsl:attribute>
              </xsl:if>
            </key-map>
          </xsl:for-each>
        </relation>
      </xsl:for-each>
    </entity>
  </xsl:template>
  <xsl:template match="o:View">
    <xsl:variable name="v_View" select="."/>
    <entity>
      <xsl:attribute name="entity-name">
        <xsl:value-of select="a:Code"/>
      </xsl:attribute>
      <xsl:attribute name="package-name"><xsl:value-of select="$vConst_PackageName"/></xsl:attribute>
      <xsl:attribute name="comment"><xsl:value-of select="a:Comment"/></xsl:attribute>
        <xsl:attribute name="title">
          <xsl:value-of select="a:Name"/>
        </xsl:attribute>
<xsl:attribute name="description"><xsl:value-of select="a:Comment"/></xsl:attribute>
      <xsl:for-each select="c:View.Columns/o:ViewColumn[a:Name]">
        <field name="{a:Name}" type="{a:DataType}">
          <xsl:attribute name="java">
            <xsl:call-template name="javatype">
              <xsl:with-param name="pType" select="a:DataType"/>
            </xsl:call-template>
          </xsl:attribute>
        <xsl:attribute name="length">
          <xsl:value-of select="a:Length"/>
        </xsl:attribute>
        <xsl:attribute name="title">
          <xsl:value-of select="a:Name"/>
        </xsl:attribute>
<xsl:attribute name="description"><xsl:value-of select="a:Comment"/></xsl:attribute>
    <xsl:if test="a:Mandatory='1'">
          <xsl:attribute name="mandatory">true</xsl:attribute>
    </xsl:if>
    <xsl:attribute name="value">
          <xsl:value-of select="a:DefaultValue"/>
        </xsl:attribute>
        </field>
      </xsl:for-each>

    </entity>
  </xsl:template>
  <xsl:template name="call_relation_parent">
    <xsl:choose>
      <xsl:when test="a:Cardinality='0..1'">
        <xsl:attribute name="type">one</xsl:attribute>
        <xsl:attribute name="fk-name">
          <xsl:value-of select="a:Code"/>
        </xsl:attribute>
        <xsl:attribute name="title">
          <xsl:value-of select="a:Name"/>
        </xsl:attribute>
<xsl:attribute name="description"><xsl:value-of select="a:Comment"/></xsl:attribute>
      </xsl:when>
      <xsl:otherwise>
        <xsl:attribute name="type">one</xsl:attribute>
        <xsl:attribute name="fk-name">
          <xsl:value-of select="a:Code"/>
        </xsl:attribute>
        <xsl:attribute name="title">
          <xsl:value-of select="a:Name"/>
        </xsl:attribute>
<xsl:attribute name="description"><xsl:value-of select="a:Comment"/></xsl:attribute>
      </xsl:otherwise>
    </xsl:choose>
  </xsl:template>
  <xsl:template name="call_relation_child">
    <xsl:choose>
      <xsl:when test="a:Cardinality='0..1'">
        <xsl:attribute name="type">one-nofk</xsl:attribute>
      </xsl:when>
      <xsl:otherwise>
        <xsl:attribute name="type">many</xsl:attribute>
      </xsl:otherwise>
    </xsl:choose>
  </xsl:template>
  <xsl:template name="lower">
    <xsl:param name="word"/>
    <xsl:variable name="lowercase" select="'abcdefghijklmnopqrstuvwxyz'" />
    <xsl:variable name="uppercase" select="'ABCDEFGHIJKLMNOPQRSTUVWXYZ'" />
    <xsl:value-of select="translate($word, $uppercase, $lowercase)"/>
  </xsl:template>
  <xsl:template name="javatype">
    <xsl:param name="pType"/>
    <xsl:variable name="type">
  <xsl:call-template name="lower">
    <xsl:with-param name="word" select="$pType"/>
  </xsl:call-template>
  </xsl:variable>
    <xsl:choose>
      <xsl:when test="starts-with($type,'char')">
        <xsl:text>String</xsl:text>
      </xsl:when>
      <xsl:when test="starts-with($type,'datetime')">
        <xsl:text>java.sql.Timestamp</xsl:text><!--java.sql.Timestamp -->
      </xsl:when>
      <xsl:when test="starts-with($type,'timestamp')">
        <xsl:text>java.sql.Timestamp</xsl:text>
      </xsl:when>
      <xsl:when test="starts-with($type,'date')">
        <xsl:text>java.sql.Date</xsl:text><!--java.sql.Date -->
      </xsl:when>
      <xsl:when test="starts-with($type,'varchar')">
        <xsl:text>String</xsl:text>
      </xsl:when>
      <xsl:when test="starts-with($type,'decimal')">
        <xsl:if test="starts-with(substring-after($type,','),'0')">
          <xsl:text>long</xsl:text>
        </xsl:if>
        <xsl:if test="starts-with(substring-after($type,','),'2')">
          <xsl:text>double</xsl:text>
        </xsl:if>
      </xsl:when>
      <xsl:when test="starts-with($type,'number')">
        <xsl:if test="starts-with(substring-after($type,','),'0')">
          <xsl:text>long</xsl:text>
        </xsl:if>
        <xsl:if test="starts-with(substring-after($type,','),'2')">
          <xsl:text>double</xsl:text>
        </xsl:if>
      </xsl:when>
      <xsl:when test="starts-with($type,'integer')">
        <xsl:text>int</xsl:text>
      </xsl:when>
      <xsl:when test="starts-with($type,'bigint')">
        <xsl:text>long</xsl:text>
      </xsl:when>
      <xsl:when test="starts-with($type,'smallint')">
        <xsl:text>int</xsl:text>
      </xsl:when>
      <xsl:when test="starts-with($type,'blob')">
        <xsl:text>Object</xsl:text>
      </xsl:when>
      <xsl:when test="starts-with($type,'float')">
        <xsl:text>float</xsl:text>
      </xsl:when>
      <xsl:when test="$type='numeric'">
        <xsl:text>long</xsl:text>
      </xsl:when>
      <xsl:when test="$type='int'">
        <xsl:text>int</xsl:text>
      </xsl:when>
      <xsl:when test="starts-with($type,'numeric')">
        <xsl:if test="starts-with(substring-after($type,','),'0')">
          <xsl:text>long</xsl:text>
        </xsl:if>
        <xsl:if test="starts-with(substring-after($type,','),'2')">
          <xsl:text>double</xsl:text>
        </xsl:if>
      </xsl:when>
      <xsl:when test="starts-with($type,'&lt;undefined&gt;')">
        <xsl:text>String</xsl:text>
        <xsl:message>Can not recognize type <xsl:value-of select="$type"/>, The String instead</xsl:message>
      </xsl:when>
      <xsl:otherwise>
        <xsl:text>unknow</xsl:text>
        <xsl:message>Can not recognize type <xsl:value-of select="$type"/></xsl:message>
      </xsl:otherwise>
    </xsl:choose>
  </xsl:template>
</xsl:stylesheet>