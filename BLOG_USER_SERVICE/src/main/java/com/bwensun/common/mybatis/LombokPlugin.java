package com.bwensun.common.mybatis;

import com.sun.org.apache.bcel.internal.generic.IF_ACMPEQ;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.*;
import org.mybatis.generator.internal.util.StringUtility;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * lombok插件
 *
 * @author 郑建雄
 * @date 2019/12/16
 */
public class LombokPlugin extends PluginAdapter {

    private static final String PLUGIN_DATE_FORMAT = "dateFormat";
    private static final String PLUGIN_SWAGGER2 = "swagger2";
    private static final String PLUGIN_LOMBOK_DATA = "data";
    private static final String PLUGIN_LOMBOK_BUILDER = "builder";
    private static final String PLUGIN_AUTHOR = "author";


    @Override
    public boolean validate(List<String> warnings) {
        return true;
    }

    @Override
    public boolean clientGenerated(Interface interfaze, TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        SimpleDateFormat dateFormatter = new SimpleDateFormat(properties.getProperty(PLUGIN_DATE_FORMAT, "yyyy/MM/dd"));
        //接口添加注释
        interfaze.addJavaDocLine("/**");
                interfaze.addJavaDocLine(" * " + introspectedTable.getRemarks());
        interfaze.addJavaDocLine(" *");
        interfaze.addJavaDocLine(" * @author " + properties.getProperty(PLUGIN_AUTHOR));
        interfaze.addJavaDocLine(" * @date " + dateFormatter.format(new Date()));
        interfaze.addJavaDocLine(" */");
        //dao添加注解
        addAnnotations(interfaze, "org.springframework.stereotype.Repository", "@Repository");
        return true;
    }

    @Override
    public boolean modelBaseRecordClassGenerated(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        if (StringUtility.isTrue(this.properties.getProperty(PLUGIN_LOMBOK_DATA, "false"))) {
            addAnnotations(topLevelClass, "lombok.Data", "@Data");
        } else {
            addAnnotations(topLevelClass, "lombok.Getter", "@Getter");
            addAnnotations(topLevelClass, "lombok.Setter", "@Setter");
            addAnnotations(topLevelClass, "lombok.ToString", "@ToString");
        }
        if (StringUtility.isTrue(this.properties.getProperty(PLUGIN_LOMBOK_BUILDER))) {
            addAnnotations(topLevelClass, "lombok.Builder", "@Builder");
        }
        return true;
    }

    /**
     * 关闭get方法生成
     */
    @Override
    public boolean modelGetterMethodGenerated(Method method, TopLevelClass topLevelClass, IntrospectedColumn introspectedColumn, IntrospectedTable introspectedTable, ModelClassType modelClassType) {
        return false;
    }

    /**
     * 关闭set方法生成
     */
    @Override
    public boolean modelSetterMethodGenerated(Method method, TopLevelClass topLevelClass, IntrospectedColumn introspectedColumn, IntrospectedTable introspectedTable, ModelClassType modelClassType) {
        return false;
    }

    @Override
    public boolean modelFieldGenerated(Field field, TopLevelClass topLevelClass, IntrospectedColumn introspectedColumn, IntrospectedTable introspectedTable, ModelClassType modelClassType) {
        if (StringUtility.isTrue(properties.getProperty(PLUGIN_SWAGGER2, "true"))){
            topLevelClass.addImportedType(new FullyQualifiedJavaType("io.swagger.annotations.ApiModelProperty"));
            String remarks = introspectedColumn.getRemarks();
            field.addAnnotation(this.getSwaggerAnnotation(field.getName(), remarks));
        }
        return true;
    }

    /**
     * 设置类注解
     *
     * @param importedType 导包类型
     * @param annotation   注解
     */
    private void addAnnotations(TopLevelClass topLevelClass, String importedType, String annotation) {
        topLevelClass.addImportedType(importedType);
        topLevelClass.addAnnotation(annotation);
    }

    /**
     * 设置接口注解
     *
     * @param importedType 导包类型
     * @param annotation   注解
     */
    private void addAnnotations(Interface interfaze, String importedType, String annotation) {
        interfaze.addImportedType(new FullyQualifiedJavaType(importedType));
        interfaze.addAnnotation(annotation);
    }

    /**
     * 获取 swagger 注解信息
     *
     * @param name    字段名
     * @param remarks 注释信息
     * @return 返回 swagger 注释字符串
     */
    private String getSwaggerAnnotation(String name, String remarks) {
        StringBuilder builder = new StringBuilder("@ApiModelProperty(");
        builder.append("name = \"");
        builder.append(name);
        if (StringUtility.stringHasValue(remarks)) {
            builder.append("\", value = \"");
            builder.append(remarks);
        }
        builder.append("\")");
        return builder.toString();
    }
}
