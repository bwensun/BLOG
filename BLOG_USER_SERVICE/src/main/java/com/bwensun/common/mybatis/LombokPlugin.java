package com.bwensun.common.mybatis;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.Interface;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.TopLevelClass;

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

    @Override
    public boolean validate(List<String> warnings) {
        return true;
    }

    @Override
    public boolean clientGenerated(Interface interfaze, TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        SimpleDateFormat dateFormatter = new SimpleDateFormat(properties.getProperty("dateFormat", "yyyy-MM-dd"));
        //接口添加注释
        interfaze.addJavaDocLine("/**");
                interfaze.addJavaDocLine(" * " + introspectedTable.getRemarks());
        interfaze.addJavaDocLine(" *");
        interfaze.addJavaDocLine(" * @author " + properties.getProperty("author"));
        interfaze.addJavaDocLine(" * @date " + dateFormatter.format(new Date()));
        interfaze.addJavaDocLine(" */");
        //dao添加注解
        addAnnotations(interfaze, "org.springframework.stereotype.Repository", "@Repository");


        return true;
    }

    @Override
    public boolean modelBaseRecordClassGenerated(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        if (Boolean.parseBoolean(this.properties.getProperty("data"))) {
            addAnnotations(topLevelClass, "lombok.Data", "@Data");
        } else {
            addAnnotations(topLevelClass, "lombok.Getter", "@Getter");
            addAnnotations(topLevelClass, "lombok.Setter", "@Setter");
            addAnnotations(topLevelClass, "lombok.ToString", "@ToString");
        }
        if (Boolean.parseBoolean(this.properties.getProperty("builder"))) {
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

    public void addSwaggerAnnotation(){

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

    private String date2Str(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
        return format.format(date);
    }
}
