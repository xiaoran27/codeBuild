<?xml version="1.0" encoding="gb2312" ?>
<xsl:transform xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
<xsl:output method="text" indent="no" omit-xml-declaration="yes" encoding="gb2312" media-type="gb2312"/>
<xsl:template match="/">
	<xsl:apply-templates select="//entity|//view-entity">
		<xsl:sort select="@entity-name"/>
	</xsl:apply-templates>##set($Tables = [<xsl:for-each select="//entity|//view-entity"><xsl:sort select="@entity-name"/>"<xsl:value-of select="@entity-name"/>"<xsl:if test="position()!=last()">,</xsl:if></xsl:for-each>])<xsl:text>
</xsl:text>##set($Comments = [<xsl:for-each select="//entity|//view-entity"><xsl:sort select="@entity-name"/>"<xsl:value-of select="@comment"/>"<xsl:if test="position()!=last()">,</xsl:if></xsl:for-each>])<xsl:text>
</xsl:text>
</xsl:template>

<xsl:template match="entity | view-entity">#if ($TABLE == "<xsl:call-template name="upperAll"><xsl:with-param name="word" select="@entity-name"/></xsl:call-template>")
#set ($Package = "<xsl:call-template name="upperAll"><xsl:with-param name="word" select="@package-name"/><xsl:with-param name="upper" select="'false'"/></xsl:call-template>")
#set ($Comment = "<xsl:call-template name="upperAll"><xsl:with-param name="word" select="@comment"/></xsl:call-template>")
<xsl:call-template name="entity-column">
		<xsl:with-param name="fields" select="field/@name"/>
		<xsl:with-param name="upper" select="'false'"/>
		<xsl:with-param name="title" select="'#set ($ColumnCodes =['"/>
	</xsl:call-template>
	<xsl:call-template name="entity-column">
		<xsl:with-param name="fields" select="field/@description"/>
		<xsl:with-param name="upper" select="'false'"/>
		<xsl:with-param name="title" select="'#set ($ColumnComments =['"/>
	</xsl:call-template>
	<!-- 
	<xsl:call-template name="entity-label">
		<xsl:with-param name="fields" select="field/@name"/>
		<xsl:with-param name="table" select="concat(@entity-name,'.')"/>		
		<xsl:with-param name="title" select="'#set ($ColumnLabels =['"/>
	</xsl:call-template>
	-->
	<xsl:call-template name="entity-column">
		<xsl:with-param name="fields" select="field/@java"/>
		<xsl:with-param name="upper" select="'false'"/>
		<xsl:with-param name="title" select="'#set ($ColumnTypes =['"/>
	</xsl:call-template>
	<xsl:call-template name="entity-column-length">
		<xsl:with-param name="fields" select="field/@length"/>
		<xsl:with-param name="title" select="'#set ($ColumnLength =['"/>
	</xsl:call-template>
	<xsl:call-template name="entity-column-mandatory">
		<xsl:with-param name="fields" select="field"/>
		<xsl:with-param name="title" select="'#set ($ColumnMandatory =['"/>
	</xsl:call-template>
	<xsl:call-template name="entity-column-value">
		<xsl:with-param name="fields" select="field/@value"/>
		<xsl:with-param name="title" select="'#set ($ColumnValues =['"/>
	</xsl:call-template>
	<xsl:call-template name="entity-column-reference">
		<xsl:with-param name="fields" select="field"/>
		<xsl:with-param name="relations" select="relation[@type='one']"/>
		<xsl:with-param name="title" select="'#set ($ComlumnReference =['"/>
	</xsl:call-template>
	<xsl:call-template name="entity-column">
		<xsl:with-param name="fields" select="prim-key/@field"/>
		<xsl:with-param name="title" select="'#set ($TablePKS =['"/>
		<xsl:with-param name="upper" select="'false'"/>
	</xsl:call-template>
	<xsl:call-template name="entity-column">
		<xsl:with-param name="fields" select="uniqueindex/@fields"/>
		<xsl:with-param name="title" select="'#set ($TableINXS =['"/>
		<xsl:with-param name="upper" select="'false'"/>
	</xsl:call-template>	
#end
</xsl:template>

<xsl:template name="entity-column">
	<xsl:param name="fields"/>
	<xsl:param name="title"/>
	<xsl:param name="upper" select="'true'"/>
	<xsl:param name="table" select="''"/>
<xsl:value-of select="$title"/><xsl:for-each select="$fields">"<xsl:value-of select="$table"/><xsl:call-template name="upperAll"><xsl:with-param name="word" select="."/><xsl:with-param name="upper" select="$upper"/></xsl:call-template>"<xsl:if test="position()!=last()">,</xsl:if></xsl:for-each>])
</xsl:template>

<xsl:template name="entity-label">
	<xsl:param name="fields"/>
	<xsl:param name="title"/>
	<xsl:param name="table" select="''"/>
<xsl:value-of select="$title"/><xsl:for-each select="$fields">"@<xsl:value-of select="$table"/><xsl:value-of select="."/>@"<xsl:if test="position()!=last()">,</xsl:if></xsl:for-each>])
</xsl:template>

<xsl:template name="entity-column-length">
	<xsl:param name="fields"/>
	<xsl:param name="title"/>
<xsl:value-of select="$title"/><xsl:for-each select="$fields">"<xsl:value-of select="."/>"<xsl:if test="position()!=last()">,</xsl:if></xsl:for-each>])
</xsl:template>

<xsl:template name="entity-column-mandatory">
	<xsl:param name="fields"/>
	<xsl:param name="title"/>
<xsl:value-of select="$title"/><xsl:for-each select="$fields"><xsl:choose><xsl:when test="@mandatory='true'">"true"</xsl:when><xsl:otherwise>"false"</xsl:otherwise></xsl:choose><xsl:if test="position()!=last()">,</xsl:if></xsl:for-each>])
</xsl:template>

<xsl:template name="entity-column-value">
	<xsl:param name="fields"/>
	<xsl:param name="title"/>
<xsl:value-of select="$title"/><xsl:for-each select="$fields">"<xsl:value-of select="."/>"<xsl:if test="position()!=last()">,</xsl:if></xsl:for-each>])
</xsl:template>

<xsl:template name="entity-column-reference">
	<xsl:param name="fields"/>
	<xsl:param name="title"/>
	<xsl:param name="relations"/>
<xsl:value-of select="$title"/><xsl:for-each select="$fields"><xsl:variable name="this" select="."/><xsl:choose><xsl:when test="$relations/key-map/@field-name=$this/@name">"<xsl:value-of select="$relations[key-map/@field-name=$this/@name]/@rel-entity-name"/>"</xsl:when><xsl:otherwise>""</xsl:otherwise></xsl:choose><xsl:if test="position()!=last()">,</xsl:if></xsl:for-each>])
</xsl:template>

<xsl:template name="upperAll">
	<xsl:param name="word"/>
	<xsl:param name="upper" select="'true'"/>
	<xsl:variable name="lowercase" select="'abcdefghijklmnopqrstuvwxyz'" />
	<xsl:variable name="uppercase" select="'ABCDEFGHIJKLMNOPQRSTUVWXYZ'" />
	<xsl:choose>
		<xsl:when test="$upper='true'">
			<xsl:value-of select="translate($word, $lowercase, $uppercase)"/>
		</xsl:when>
		<xsl:otherwise>
			<xsl:value-of select="$word"/>
		</xsl:otherwise>
	</xsl:choose>
</xsl:template>

</xsl:transform>